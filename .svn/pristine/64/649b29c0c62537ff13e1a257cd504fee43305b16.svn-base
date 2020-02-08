/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.requestconfirmation.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardProductMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
import com.epic.cms.callcenter.card.businesslogic.CardActivationManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.cpmm.requestconfirmation.bean.RequestConfirmationBean;
import com.epic.cms.cpmm.requestconfirmation.businesslogic.RequestConfirmationManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class UpdateRequestApproveServlet extends HttpServlet {

    //initializing variables
    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private List<String> userTaskList;
    private SessionUser sessionUser = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    private FeeBean feeBean = null;
    //----------------------------------------------------
    private HashMap<String, String> approveStatus;
    private RequestConfirmationBean bean = null;
    private RequestConfirmationManager reqConfMgr;
    private HashMap<String, String> cardStatus = null;
    private HashMap<String, String> blockReasons = null;
    private HashMap<String, String> cardCategory;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> reasonCodeList;
    private HashMap<String, String> cardTypeList = null;
    private CardActivationManager cardManager = null;
    private ApplicationAssignManager appAssignManager;
    private CardProductMgtManager cardProductMgtManager;
    String appStatus = "";
    private int successUpdate = -1;
    private int rejectUpdate = -1;
    private boolean successActivation = false;
    private String url = "/cpmm/requestconfirm/requestapprovehome.jsp";

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
            //call to existing session
            sessionObj = request.getSession(false);
            try {
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
            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }
            /////////////////////////////////////////////////////////////////////
            try {
                //set page code and task codes
                String pageCode = PageVarList.REQUESTAPPROVE;
                String taskCode = TaskVarList.APPROVE;

                //check whether user_role have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {

                        reasonCodeList = new HashMap<String, String>();
                        reasonCodeList.put(StatusVarList.CARD_ACTIVATION_REQ, "Card Activation");
                        reasonCodeList.put(StatusVarList.CARD_REPLACE, "Card Replace");
                        reasonCodeList.put(StatusVarList.PIN_REISSUE, "PIN Reissue");
                        reasonCodeList.put(StatusVarList.CARD_REISSUE, "Card Reissue");

                        this.getPriorityLevels();

                        //set the operation type
                        request.setAttribute("operationtype", "update");
                        //set user input to bean
                        setUserInputToBean(request);
                        //set card fee count 
                        this.setCardFeeCount(request);
                        //get the approve status
                        appStatus = request.getParameter("approveStatus");
                        //get the operation(reason)
                        String operation = request.getParameter("operation");
                        //set values to the audit trace
                        this.setAudittraceValue(request);

                        reqConfMgr = new RequestConfirmationManager();

                        successUpdate = -1;
                        rejectUpdate = -1;
                        successActivation = false;

                        //approve or 
                        if (appStatus != null && appStatus.equals("REJ")) {

                            if (bean.getRemark().isEmpty()) {
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("operation", operation);
                                request.setAttribute("cardBean", bean);
                                request.setAttribute("reasonCodeList", reasonCodeList);
                                request.setAttribute("priorityLevelList", priorityLevelList);
                                request.setAttribute("searchedList", sessionVarlist.getRequestList());
                                request.setAttribute("errorMsg", MessageVarList.REMARKS_EMPTY);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);
                            } else {
                                rejectUpdate = reqConfMgr.requsetReject(bean, operation);
                            }

                        } else if (operation.equals("ACTI")) {
                            cardManager = new CardActivationManager();
                            if (bean.getStatus().equals(StatusVarList.BLOCK_STATUS)) {

                                bean = cardManager.getCardBlockDetails(bean);
                                successActivation = cardManager.blockCardActivate(bean, systemAuditBean);//,callhistoryBean

                            } else {
                                successActivation = cardManager.cardActivate(bean, systemAuditBean);//,callhistoryBean

                            }

                            if (successActivation) {

                                successActivation = cardManager.updateRequset(bean, operation);
                            }

                        } else {
                            successUpdate = reqConfMgr.approveRequest(bean, appStatus, operation, feeBean, systemAuditBean);
                        }

                        if (rejectUpdate == 1) {
                            request.setAttribute("successMsg", MessageVarList.SUCCESS_REJECT);
                            rd = getServletContext().getRequestDispatcher("/LoadRequestApproveServlet");
                            rd.forward(request, response);
                            request.setAttribute("operationtype", "search");
                        } else if (successUpdate == 1 || successActivation) {
                            request.setAttribute("successMsg", MessageVarList.SUCCESS_APPROVE);
                            rd = getServletContext().getRequestDispatcher("/LoadRequestApproveServlet");
                            rd.forward(request, response);
                            request.setAttribute("operationtype", "search");
                        } else {
                            request.setAttribute("errorMsg", MessageVarList.FAIL_APPROVE);
                            request.setAttribute("operationtype", "search");
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }

                    } catch (SQLException e) {
                        request.setAttribute("operationtype", "search");
                        OracleMessage message = new OracleMessage();
                        String oraMessage = message.getMessege(e.getMessage());
                        request.setAttribute("errorMsg", oraMessage);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);

                    } catch (Exception ex) {

                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }

                } else {
                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }
                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);

            } catch (AccessDeniedException adex) {
                throw adex;
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

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {
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

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(bean.getCardNo());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Approve Card Request. Card No: '" + bean.getCardNo() + "' by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CPMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.REQUESTCONFIRM);
            systemAuditBean.setPageCode(PageVarList.REQUESTAPPROVE);
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

    private void setCardFeeCount(HttpServletRequest request) throws Exception {

        try {
            feeBean = new FeeBean();

            feeBean.setCardNo(request.getParameter("cardno"));
            if (request.getParameter("operation").equals("CDRP")) {
                feeBean.setFeeCode(StatusVarList.CASH_ADVANCE_FEE);                
            } else if (request.getParameter("operation").equals("CDRI")) {
                feeBean.setFeeCode(StatusVarList.CHEQUE_RETURN_FEE_ON_PAYMENTS_INSUFFICIENT_FUNDS);
            } else if (request.getParameter("operation").equals("PIRI")) {
                feeBean.setFeeCode(StatusVarList.CHEQUE_RETURN_FEE_ON_PAYMENTS_OTHER_REASON);
            }

            feeBean.setStatus(StatusVarList.ACTIVE_STATUS);
            feeBean.setLastUpdateUser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            bean = new RequestConfirmationBean();

            bean.setCardNo(request.getParameter("cardno"));

            bean.setCreditLimit(request.getParameter("creditlimit"));
            bean.setCashLimit(request.getParameter("cashlimit"));
            bean.setExpiryDate(request.getParameter("expirydate"));
            bean.setStatusDes(request.getParameter("status1"));
            bean.setPriorityLevel(request.getParameter("prioritycode1"));
            bean.setReasonCode(request.getParameter("reasoncode1"));
            bean.setRenew(request.getParameter("renew"));

            bean.setPINStatus(StatusVarList.NOSTATUS);
            bean.setPINMailStatus(StatusVarList.NOSTATUS);
            bean.setLastUpdatedUser(sessionUser.getUserName());
            bean.setStatus(request.getParameter("status"));
//            bean.setReqStatus(StatusVarList.DA_REQUSET_APPROVE);
            bean.setRemark(request.getParameter("remarks"));

            if (request.getParameter("operation").equals("CDRI")) {
                bean.setEmbossStatus(StatusVarList.NOSTATUS);
            }

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    private void getPriorityLevels() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            priorityLevelList = appAssignManager.getAllPriorityLevels();

        } catch (Exception ex) {
            throw ex;
        }
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
