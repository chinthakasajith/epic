/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.TaskMgtManager;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterDetailsBean;
import com.epic.cms.backoffice.eodlettergeneration.businesslogic.LetterGenerationManager;
import com.epic.cms.camm.applicationconfirmation.bean.RecommendSchemBean;
import com.epic.cms.camm.applicationconfirmation.businesslogic.ApplicationConfirmationManager;
import com.epic.cms.camm.applicationconfirmation.businesslogic.DebitApplicationConfirmationManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardnumbergeneration.businesslogic.BulkCardNumberGenerationManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mahesh_m
 */
public class ConfirmServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private TaskMgtManager taskManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private boolean successInsert = false;
    private String applicationid = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private ApplicationCheckingManager checkingmanager;
    private ApplicationConfirmationManager confirmManager;
    private String creditLimit = null;
    private String cardProduct = null;
    private String productionMode = null;
    private String binProfile = null;
    private String binprofileCode = null;
    private String customerTemplateId = null;
    private String staffStatus = null;
    private String accountTemId = null;
    private String cardTempId = null;
    private String identificationNumber = null;
    private String cifNumber = null;
    private ApplicationHistoryBean historybean = null;
    private String url = "/camm/applicationconfirmation/confirmationtableview.jsp";
    private List<CardBinBean> cardKeyList = new ArrayList<CardBinBean>();
    private DebitApplicationConfirmationManager debitApplicationConfirmationManager;
    private LetterGenerationManager letterGenerationManager=null;
    private LetterDetailsBean letterDetailsBean=null;

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
                String pageCode = PageVarList.APPLICATONAPPROVE;
                String taskCode = TaskVarList.APPROVE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    try {
                        applicationid = request.getParameter("applicationid");
                        //get identification number
                        identificationNumber = request.getParameter("identificationNum");
                        checkingmanager = new ApplicationCheckingManager();
                        letterGenerationManager=new LetterGenerationManager();
                        this.setApplicationHistoryBean();
                        if (validateUserInput(request)) {
                            //get cif number
                            cifNumber = checkingmanager.getCifNumberToIdentificationNumber(identificationNumber);
                            if (cifNumber != null) {
                                //set value to leterdetails bean
                                this.setLetterDetailsValues(request);
                                //set audit values
                                this.setAudittraceValue(request);
                                String sysRecomendedCreditLimit = request.getParameter("sysRecomendedCreditLimit");
                                String sysRecomendedCardProduct = request.getParameter("cardProduct");
                                String currency = request.getParameter("currency");

                                String productionModeCode = null;
                                if (productionMode.equals(StatusVarList.EMVC_CHIP_CARD_CODE)) {
                                    productionMode = StatusVarList.EMVC_CHIP_CARD;
                                    productionModeCode = StatusVarList.EMVC_CHIP_CARD_CODE;
                                } else if (productionMode.equals(StatusVarList.MAGNATIC_STRIPE_CARD_CODE)) {
                                    productionMode = StatusVarList.MAGNATIC_STRIPE_CARD;
                                    productionModeCode = StatusVarList.MAGNATIC_STRIPE_CARD_CODE;
                                }

                                // added by asela 20/06/2013
                                String cardKey = this.getCardKey(binprofileCode, cardProduct, productionModeCode);
                                System.out.println("card key " + cardKey);
                                checkingmanager.updateCardApplication(applicationid, sysRecomendedCreditLimit, sysRecomendedCardProduct, creditLimit, cardProduct, productionMode, cardKey, binprofileCode, customerTemplateId, accountTemId, cardTempId, currency,cifNumber);
                                checkingmanager.UpdateCardApplicationStatus(applicationid, StatusVarList.APP_APPROVE_COMPLETE, systemAuditBean, historybean);
                                letterGenerationManager.saveLetterDetailsHaveToBeGenerated(letterDetailsBean);
                                out.print("success," + "Application id " + applicationid + " " + MessageVarList.APPROVE_ACCEPTED_SUCESS);
                                request.setAttribute("successMsg", "Application id " + applicationid + " " + MessageVarList.APPROVE_ACCEPTED_SUCESS);
                            }else{
                                out.print(MessageVarList.CIF_NOT_EXIST);
                            }

                        } else {

                            out.print(errorMessage);

                        }

                    } catch (Exception e) {
//                        request.setAttribute("selectedtab", "0");
//                        request.setAttribute("applicationid", "applicationid");
//                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
//                        rd = getServletContext().getRequestDispatcher("/LoadApproveDetailsServlet");
//                        rd.forward(request, response);
                        out.print(MessageVarList.UNKNOW_ERROR);
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
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);

        } finally {
            out.close();
        }
    }

    public boolean validateUserInput(HttpServletRequest request) throws Exception {
        boolean isValidate = true;

        creditLimit = request.getParameter("creditLimit");
        cardProduct = request.getParameter("confirmCardProduct");
        productionMode = request.getParameter("productionMode");
        binprofileCode = request.getParameter("binProfile");
        customerTemplateId = request.getParameter("customerTemplate");
        accountTemId = request.getParameter("accountTemplates");
        cardTempId = request.getParameter("cardtemplate");
        staffStatus = request.getParameter("staff");
        String cardCategoryCode = request.getParameter("cardCategoryCode");
        String businessRegNo = request.getParameter("businessRegNo");
        String establishmentCreditLimit = request.getParameter("establishmentCreditLimit");

        String mainCreditLimit = request.getParameter("mainCreditLimit");
        String mainCardNumber = request.getParameter("primaryCardNumber");
        String mainApplicationId = request.getParameter("mainApplicationId");
        String hasPrimaryCard = request.getParameter("hasPrimCard");

        //validate user Role code
        try {
            if (cardProduct == null || cardProduct.contentEquals("") || cardProduct.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APPROVE_CARDPRODUCT_INVALID;
            } else if (creditLimit.contentEquals("") || creditLimit.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APPROVE_CREDITLIMIT_NULL;
            } else if (!UserInputValidator.isNumeric(creditLimit)) {
                isValidate = false;
                errorMessage = MessageVarList.APPROVE_CREDITLIMIT_INVALID;
            } else if (binprofileCode == null || binprofileCode.contentEquals("") || binprofileCode.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APPROVE_BINPROFILE_INVALID;
            } else if (productionMode == null || productionMode.contentEquals("") || productionMode.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APPROVE_PRODUCTIONMODE_INVALID;
            } else if (customerTemplateId == null || customerTemplateId.contentEquals("") || customerTemplateId.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APPROVE_CUSTOMERTEMPLATE_INVALID;
            } else if (accountTemId == null || accountTemId.contentEquals("") || accountTemId.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APPROVE_ACCOUNTEMPLATE_INVALID;
            } else if (cardTempId == null || cardTempId.contentEquals("") || cardTempId.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APPROVE_CARDTEMPLATE_INVALID;
            } else if (cardCategoryCode != null && cardCategoryCode.equals(StatusVarList.CARD_CATEGORY_COPORATE) && !validateExceedEstablishmentCreditLimit(businessRegNo, establishmentCreditLimit, creditLimit)) {
                errorMessage = MessageVarList.APPROVE_EXCEED_ESTABLISHMENT_CREDITLIMIT;
                isValidate = false;
            } else if (cardCategoryCode != null && cardCategoryCode.equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)
                    && Boolean.parseBoolean(hasPrimaryCard)
                    && !validateExceedMainCreditLimit(mainCreditLimit, mainCardNumber, mainApplicationId, creditLimit, applicationid)) {
                errorMessage = MessageVarList.APPROVE_EXCEED_MAIN_CREDITLIMIT;
                isValidate = false;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public boolean validateExceedEstablishmentCreditLimit(String businessRegNo, String establishmentCreditLimitStr, String requestCreditLimitStr) throws Exception {
        boolean validate = true;
        try {
            Double establishmentCreditLimit = Double.parseDouble(establishmentCreditLimitStr);
            Double requestCreditLimit = Double.parseDouble(requestCreditLimitStr);
            confirmManager = new ApplicationConfirmationManager();
            validate = confirmManager.validateExceedEstablishmentCreditLimit(businessRegNo, establishmentCreditLimit, requestCreditLimit);
        } catch (Exception ex) {
            throw ex;
        }

        return validate;

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
            systemAuditBean.setDescription("Update " + uniqueId + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.APPLICATIONAPPROVE);
            systemAuditBean.setPageCode(PageVarList.APPLICATONAPPROVE);
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
    //set values to letterdetails bean
    private void setLetterDetailsValues(HttpServletRequest request) throws Exception{
        try {
            letterDetailsBean=new LetterDetailsBean();
            
            letterDetailsBean.setId(request.getParameter("identificationNum"));
            letterDetailsBean.setIdType(request.getParameter("identificationType"));
            letterDetailsBean.setTmpCode(StatusVarList.APP_CONF_LETTER_TMP);
            letterDetailsBean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean updateTask(TaskBean task, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            taskManager = new TaskMgtManager();
            success = taskManager.updateTask(task, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
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

    public void getCardKeyByProductionMode(String productionMode) throws Exception {
        try {
            debitApplicationConfirmationManager = new DebitApplicationConfirmationManager();
            cardKeyList = debitApplicationConfirmationManager.getCardKeyListByProductionMode(productionMode);
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

    private boolean validateExceedMainCreditLimit(String mainCreditLimitStr, String mainCardNumber, String mainApplicationId, String creditLimitStr, String appId) throws Exception {
        boolean validate = true;
        try {
            Double mainCreditLimit = Double.parseDouble(mainCreditLimitStr);
            Double requestCreditLimit = Double.parseDouble(creditLimitStr);
            confirmManager = new ApplicationConfirmationManager();
            validate = confirmManager.validateExceedMainCreditLimit(mainApplicationId, mainCreditLimit, requestCreditLimit, mainCardNumber, appId);

        } catch (Exception ex) {
            throw ex;
        }

        return validate;
    }
}
