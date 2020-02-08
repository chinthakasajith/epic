/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.card.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardProductMgtManager;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardTypeMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.callcenter.callcentersearch.bean.CallHistoryBean;
import com.epic.cms.callcenter.card.bean.CardBean;
import com.epic.cms.callcenter.card.businesslogic.CardBlockManager;
import com.epic.cms.callcenter.card.businesslogic.CardRequestManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nisansala
 */
public class ManageCardRequestServlet extends HttpServlet {

    //initializing variables
    private RequestDispatcher rd;
    private String errorMessage = "";
    HttpSession sessionObj = null;
    private List<String> userTaskList;
    private SessionUser sessionUser = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    //----------------------------------------------------
    private int success = 0;
    private String cardStatus = "";
    private CardBean card = null;
    private CardBlockManager cdBlckMgr;
    private CardRequestManager cdReqMgr;
    private HashMap<String, String> cardTypeList = null;
    private ApplicationAssignManager appAssignManager;
    private HashMap<String, String> priorityLevelList = null;
    private CardProductMgtManager cardProductMgtManager;
    private HashMap<String, String> cardCategory;
    private CardTypeMgtManager cardTypeManager;
    private String url = "/callcenter/card/cardrequesthome.jsp";
    private CallHistoryBean callhistoryBean;
//    private String callerLogID = "123";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            sessionObj = request.getSession(false);
            try {
                request.setAttribute("operationType", "replace");

                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                //check system user is in same session or not
                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        //useer not in same session.
                        throw new NewLoginSessionException();

                    }

                } catch (NewLoginSessionException nlex) {
                    //throw lst login close exception
                    throw new NewLoginSessionException();

                }
                try {
                    //set page code and task codes
                    String pageCode = PageVarList.CARDREQUEST;
                    String taskCode = TaskVarList.UPDATE;

                    //check whether userrole has access to this page for required task
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        try {
                            //set the operation type
                            request.setAttribute("operationtype", "replace");
                            String reasonCode = request.getParameter("reasonCode");
//                            String callerLogID = "123";
                            this.getPriorityLevels();
                            //assign user input to the bean
                            setUserInputToBean(request);

                            if (validateUserInput(card, reasonCode)) {

                                //set values to audit trace
                                this.setAudittraceValue(request);
                                this.setCallHistoryRecord(request);
                                //get cardStatus from the session                                
                                cardStatus = sessionVarlist.getCardStatus();

                                //*************************************
                                if (reasonCode.equals("CDRP") || reasonCode.equals("CDRI")) {
                                    success = 0;
                                    if (cardStatus.equals(StatusVarList.BLOCK_STATUS)) {
                                        success = this.replace(reasonCode, systemAuditBean, card, callhistoryBean);
                                    } else {
                                        request.setAttribute("errorMsg", MessageVarList.CARD_NOT_BLK);
                                    }
                                } else if (reasonCode.equals("ACTI")) {
                                    success = 0;
                                    if (cardStatus.equals(StatusVarList.BLOCK_STATUS) || cardStatus.equals(StatusVarList.INACTIVE_STATUS)) {
                                        success = this.replace(reasonCode, systemAuditBean, card, callhistoryBean);
                                    } else {
                                        request.setAttribute("errorMsg", MessageVarList.CARD_CANNOT_ACTIVATED);
                                    }

                                } else if (reasonCode.equals("PIRI")) {
                                    success = this.replace(reasonCode, systemAuditBean, card, callhistoryBean);
                                }
                                //**************************************

                                if (success == 1) {
                                    request.setAttribute("successMsg", MessageVarList.SUCCESS_REPLACE_REQUEST + " for Card " + card.getCardNumber());
                                    request.setAttribute("cardBean", card);
                                    request.setAttribute("priorityLevelList", priorityLevelList);
                                    rd = getServletContext().getRequestDispatcher("/ViewCustomerMgtServlet?id=" + card.getCardNumber() + "&section=CCCARD");
                                    rd.forward(request, response);

                                } else if (success == -2) {
                                    request.setAttribute("errorMsg", "Request has already sent for the card number");
                                    request.setAttribute("priorityLevelList", priorityLevelList);
                                    request.setAttribute("cardBean", card);
                                    rd = request.getRequestDispatcher(url);
                                    rd.forward(request, response);

                                } else {

                                    request.setAttribute("priorityLevelList", priorityLevelList);
                                    request.setAttribute("cardBean", card);
                                    rd = request.getRequestDispatcher(url);
                                    rd.forward(request, response);

                                }
                            } else {
                                request.setAttribute("errorMsg", errorMessage);
                                request.setAttribute("cardBean", card);
                                request.setAttribute("priorityLevelList", priorityLevelList);
                                rd = request.getRequestDispatcher(url);
                                rd.forward(request, response);
                            }

                        } catch (Exception e) {

                            request.setAttribute("cardBean", card);
                            request.setAttribute("operationtype", "replace");
                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }

                        sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                    } else {

                        //if invalid throw access denied exception
                        throw new AccessDeniedException();

                    }

                    //set tasks to the session
                    sessionVarlist.setUserPageTaskList(userTaskList);

                } catch (AccessDeniedException adex) {

                    throw adex;

                }
            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);

        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
        }
    }

    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {
        boolean isValidTaskAccess = false;

        try {
            systemUserManager = new SystemUserManager();
            //get all tasks for userrole for this page
            userTaskList = systemUserManager.getTasksByPageCodeAndUserRole(userrole, pagecode);

            for (String usertask : userTaskList) {

                if (task.equals(usertask)) {
                    isValidTaskAccess = true;
                }
            }
            return isValidTaskAccess;
        } catch (Exception ex) {
            throw ex;
        }

    }

    private void getCardDetails(String cardNo) throws Exception {
        try {
            cdBlckMgr = new CardBlockManager();
            card = cdBlckMgr.getCardDetails(cardNo);
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            card = new CardBean();

            card.setCardNumber(request.getParameter("cardno").trim());
            card.setCardType(request.getParameter("cardtype").trim());
            card.setCardTypeDes(request.getParameter("cardtypedes").trim());
            card.setCardCategory(request.getParameter("cardcategory"));
            card.setCardCatDes(request.getParameter("cardcategorydes"));
            card.setCreditLimit(request.getParameter("creditlimit"));
            card.setCashLimit(request.getParameter("cashlimit"));
            card.setExpDate(request.getParameter("expirydate"));
            card.setRenewEligible(request.getParameter("reneweligible"));
            //********************************************************
            if (request.getParameter("reneweligible").equals(StatusVarList.YESSTATUS)) {
                card.setRenewConfirm(request.getParameter("renewconfirm"));
            } else if (request.getParameter("reneweligible").equals(StatusVarList.NOSTATUS)) {
                card.setRenewConfirm(StatusVarList.NOSTATUS);
            }

            //*********************************************************
            //card.setRenewConfirm(request.getParameter("renewconfirm"));
            card.setPriorityLevel(request.getParameter("prioritycode"));
            card.setRemark(request.getParameter("remarks"));
            card.setLastUpdateduser(sessionUser.getUserName());

            if (!request.getParameter("reasonCode").equals("PIRI")) {
                card.setDistributeStatus(StatusVarList.NOSTATUS);
            }

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(card.getCardNumber());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add: '" + card.getCardNumber() + "' Card Request by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CALL_CENTER_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CALL_CENTER_CARD);
            systemAuditBean.setPageCode(PageVarList.CARDREQUEST);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue("");

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setCallHistoryRecord(HttpServletRequest request) throws Exception {

        try {
            callhistoryBean = new CallHistoryBean();

            callhistoryBean.setCallLogId(sessionVarlist.getCallLogId());
            callhistoryBean.setOperation(TaskVarList.UPDATE);//task code
            callhistoryBean.setRemarks("");
            callhistoryBean.setCardNo(sessionVarlist.getCardNumber());
            callhistoryBean.setApplicationId(sessionVarlist.getApplicationId());
            callhistoryBean.setCustomerId(sessionVarlist.getCustomerId());
            callhistoryBean.setAccountNo(sessionVarlist.getAccountId());
            callhistoryBean.setPageCode(PageVarList.CARDREQUEST);
            callhistoryBean.setOldValue("");
            callhistoryBean.setNewValue("");
            callhistoryBean.setTid("");
            callhistoryBean.setMid("");

            callhistoryBean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getPriorityLevels() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            priorityLevelList = appAssignManager.getAllPriorityLevels();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private int replace(String reasonCode, SystemAuditBean systemAuditBean, CardBean card, CallHistoryBean callhistoryBean) throws Exception {
        int row;
        try {
            cdReqMgr = new CardRequestManager();
            row = cdReqMgr.ReplaceCardOrReissuePIN(reasonCode, systemAuditBean, card, callhistoryBean);
        } catch (Exception ex) {
            throw ex;
        }

        return row;
    }

    public boolean validateUserInput(CardBean card, String reasonCode) throws Exception {
        boolean isValidate = true;

        try {

            if (!reasonCode.equals("PIRI")) {
                if (card.getRenewConfirm() == null) {
                    isValidate = false;
                    errorMessage = MessageVarList.RENEW_CONFIRM_EMPTY;
                }
            }

            if (card.getPriorityLevel().contentEquals("") || card.getPriorityLevel().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.PRIORITY_EMPTY;
            } else if (card.getRemark().contentEquals("") || card.getRemark().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.REMARKS_EMPTY;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
