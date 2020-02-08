/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ProductionModeBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfirmation.bean.ConfirmBean;
import com.epic.cms.camm.applicationconfirmation.bean.RecommendSchemBean;
import com.epic.cms.camm.applicationconfirmation.businesslogic.DebitApplicationConfirmationManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardnumbergeneration.businesslogic.BulkCardNumberGenerationManager;
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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author badrika
 */
public class DebitConfirmServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private RequestDispatcher rd;
    private String url = "/camm/applicationconfirmation/debitapplicationapproveview.jsp";//debitcardapplicationsearch
    private List<String> userTaskList;
    private String applicationid;
    private ApplicationHistoryBean historybean;
    private ApplicationCheckingManager checkingmanager;
    private String cardProduct;
    private String productionMode;
    private String binprofileCode;
    private String accountTemId;
    private String cardTempId;
    private String loyalty;
    private String errorMessage;
    private SystemAuditBean systemAuditBean;
    private DebitApplicationConfirmationManager debitAppConfirmManager;
//    private List<ProductionModeBean> productionModeListt;
//    private List<RecommendSchemBean> cardProductList;
    private CustomerPersonalBean personalDetail;//personalDetail
    private ConfirmBean confirmBean;
    private List<CardBinBean> cardKeyList = new ArrayList<CardBinBean>();
    private DebitApplicationConfirmationManager debitApplicationConfirmationManager;
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            /////////////////////////////////////////////////////////////////////
            HttpSession sessionObj = request.getSession(false);
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
                String pageCode = PageVarList.DEBITAPPLICATONAPPROVE;
                String taskCode = TaskVarList.APPROVE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    try {
                        applicationid = request.getParameter("applicationid");
                        debitAppConfirmManager = new DebitApplicationConfirmationManager();
                        checkingmanager = new ApplicationCheckingManager();
                        //this.getCardProducts();
                        //this.getProductionMode();
                        this.setApplicationHistoryBean();
                        if (validateUserInput(request)) {
                            this.setAudittraceValue(request);

                            String productionModeCode = null ;
                            if (productionMode.equals(StatusVarList.EMVC_CHIP_CARD_CODE)) {
                                productionMode = StatusVarList.EMVC_CHIP_CARD;
                                productionModeCode = StatusVarList.EMVC_CHIP_CARD_CODE;
                            } else if (productionMode.equals(StatusVarList.MAGNATIC_STRIPE_CARD_CODE)) {
                                productionMode = StatusVarList.MAGNATIC_STRIPE_CARD;
                                productionModeCode = StatusVarList.MAGNATIC_STRIPE_CARD_CODE;
                            }

                             // added by asela 20/06/2013
                            String cardKey = this.getCardKey(binprofileCode, cardProduct, productionModeCode);
                           
                            debitAppConfirmManager.updateCardApplication(applicationid, cardProduct, productionMode,cardKey, binprofileCode, accountTemId, cardTempId, systemAuditBean, StatusVarList.APP_APPROVE_COMPLETE, historybean, loyalty);
                            //checkingmanager.UpdateCardApplicationStatus(applicationid, StatusVarList.APP_APPROVE_COMPLETE, systemAuditBean, historybean);
                            request.setAttribute("successMsg", "Application ID " + applicationid + " " + MessageVarList.APPROVE_ACCEPTED_SUCESS);

                            rd = getServletContext().getRequestDispatcher("/LoadDebitApproveTableServlet");
                        } else {
                            request.setAttribute("applicationid", applicationid);
                            request.setAttribute("cardType", cardProduct);
                            request.setAttribute("errorMsg", errorMessage);

                            request.setAttribute("productionMode", productionMode);
                            request.setAttribute("binProfile", binprofileCode);
                            request.setAttribute("accountTemplates", accountTemId);
                            request.setAttribute("cardtemplate", cardTempId);
                            request.setAttribute("loyaltycard", loyalty);

                            request.setAttribute("confirmBean", confirmBean);

                            personalDetail = new CustomerPersonalBean();

                            String a = sessionVarlist.getCardType();
                            personalDetail.setCardType(sessionVarlist.getCardType());
                            request.setAttribute("personalDetail", personalDetail);

                            request.setAttribute("selectedtab", "0");
                            rd = getServletContext().getRequestDispatcher("/LoadDebitApproveDetailsServlet");


                        }

                        rd.forward(request, response);

                    } catch (Exception e) {
                        request.setAttribute("selectedtab", "0");
                        request.setAttribute("applicationid", "applicationid");
                        request.setAttribute("errorMsg", errorMessage);
                        rd = getServletContext().getRequestDispatcher("/LoadDebitApproveDetailsServlet");
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

            request.setAttribute("errorMsg", errorMessage);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);

        } finally {
            out.close();
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

    public void setApplicationHistoryBean() {
        historybean = new ApplicationHistoryBean();

        historybean.setApplicationId(applicationid);
        historybean.setApplicationLevel("CONF");
        historybean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
        historybean.setRemarks("Application Confirmed");
        historybean.setStatus(StatusVarList.HISTORY_COMPLETE);
    }

    public boolean validateUserInput(HttpServletRequest request) throws Exception {
        boolean isValidate = true;

        cardProduct = request.getParameter("cardProduct");
        productionMode = request.getParameter("productionMode");
        binprofileCode = request.getParameter("binProfile");
        accountTemId = request.getParameter("accountTemplates");
        cardTempId = request.getParameter("cardtemplate");
        loyalty = request.getParameter("loyaltycard");

        confirmBean = new ConfirmBean();

        confirmBean.setCdProduct(cardProduct);
        confirmBean.setProMode(productionMode);
        confirmBean.setBinProf(binprofileCode);
        confirmBean.setAccTemplt(accountTemId);
        confirmBean.setCdTemplt(cardTempId);
        confirmBean.setLoyalty(loyalty);


        //validate user Role code
        try {

            if (cardProduct == null || cardProduct.contentEquals("") || cardProduct.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APPROVE_CARDTYPE_INVALID;
            } else if (productionMode == null || productionMode.contentEquals("") || productionMode.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APPROVE_PRODUCTIONMODE_INVALID;
            } else if (binprofileCode == null || binprofileCode.contentEquals("") || binprofileCode.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APPROVE_BINPROFILE_INVALID;
            } else if (accountTemId == null || accountTemId.contentEquals("") || accountTemId.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APPROVE_ACCOUNTEMPLATE_INVALID;
            } else if (cardTempId == null || cardTempId.contentEquals("") || cardTempId.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APPROVE_CARDTEMPLATE_INVALID;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("applicationid");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Confirm Debit Card Application. Application ID: " + uniqueId + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.APPLICATIONAPPROVE);
            systemAuditBean.setPageCode(PageVarList.DEBITAPPLICATONAPPROVE);
            systemAuditBean.setTaskCode(TaskVarList.APPROVE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
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
    
    public void getCardKeyByProductionMode(String productionModeCode) throws Exception {
        try {
            debitApplicationConfirmationManager = new DebitApplicationConfirmationManager();
            cardKeyList = debitApplicationConfirmationManager.getCardKeyListByProductionMode(productionModeCode);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private String getCardKey(String bin, String product, String productionMode) throws Exception {
        String cardKey = null;
        try {
            BulkCardNumberGenerationManager bulkNumGenManager = new BulkCardNumberGenerationManager();
            cardKey = bulkNumGenManager.getCardKey(bin, product, productionMode);

        } catch (Exception ex) {
            throw ex;
        }
        return cardKey;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
