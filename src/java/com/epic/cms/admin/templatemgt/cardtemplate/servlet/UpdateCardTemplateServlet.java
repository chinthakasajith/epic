/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.cardtemplate.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.carddomaintemplate.businesslogic.CardDomainMgtManager;
import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.admin.templatemgt.cardtemplate.businesslogic.CardTemplateMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chanuka
 */
public class UpdateCardTemplateServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CardTemplateMgtManager cardTemplateManager;
    private CardDomainMgtManager domainManager = null;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private CardTemplateBean cardBean;
    private HashMap<String, String> currencyList = null;
    private HashMap<String, String> productList = null;
    private HashMap<String, String> interestProfileList = null;
    private HashMap<String, String> cardHolderFeeProfileList = null;
    private HashMap<String, String> accountTemplateList = null;
    private HashMap<String, String> customerTemplateList = null;
    boolean success = false;
    private List<CardTemplateBean> searchList = null;
    private String url = "/administrator/templatemgt/cardtemplate/cardtemplateupdate.jsp";
    private String oldValue;
    private String newValue;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {


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



            //set page code and task codes
            String pageCode = PageVarList.CARDTEMPLATEHOME;
            String taskCode = TaskVarList.UPDATE;

            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                this.setUserInputToBean(request);


                this.getAllCurrencyList();
                this.getAllAccountTemplateList();

                request.setAttribute("currencyList", currencyList);
                request.setAttribute("accountTemplateList", accountTemplateList);



                if (this.validateUserInput(cardBean, request)) {
                    this.setAudittraceValue(request);

                    if (this.updateCardDomain(cardBean, systemAuditBean)) {

                        this.searchCardDomainMgt();


                        request.setAttribute("searchList", searchList);

                        request.setAttribute("successMsg", cardBean.getTemplateName() + " " + MessageVarList.SUCCESS_UPDATE_CARDTEMPLATE);
                        rd = getServletContext().getRequestDispatcher("/LoadCardTemplateMgtServlet");

                    } else {
                        this.searchCardDomainMgt();

                        request.setAttribute("cardBean", cardBean);
                        request.setAttribute("searchList", searchList);
                        request.setAttribute("errorMsg", MessageVarList.ERROR_UPDATE_CARDTEMPLATE);
                        rd = getServletContext().getRequestDispatcher(url);
                    }

                } else {
                    this.searchCardDomainMgt();

                    request.setAttribute("cardBean", cardBean);
                    request.setAttribute("searchList", searchList);
                    request.setAttribute("errorMsg", errorMessage);
                    rd = getServletContext().getRequestDispatcher(url);
                }
            } else {

                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher("/administrator/templatemgt/cardtemplate/cardtemplatehome.jsp");


        } catch (SQLException ex) {
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("cardBean", cardBean);
            request.setAttribute("errorMsg", cardBean.getTemplateCode() + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_UPDATE_CARDTEMPLATE);
            request.setAttribute("cardBean", cardBean);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    public void setUserInputToBean(HttpServletRequest request) throws Exception {



        String templateCode = request.getParameter("templatecode").trim();
        String templateName = request.getParameter("templatename").trim();
        String validFrom = request.getParameter("validFrom").trim();
        String validTo = request.getParameter("validTo").trim();
        String productType = request.getParameter("producttype").trim();
        String currencyType = request.getParameter("currencytype").trim();
        String totalLimit = request.getParameter("totalcreditlimit").trim();
        String status = request.getParameter("status").trim();
        String staffStatus = request.getParameter("staffStatus").trim();
        String accTempCode = request.getParameter("accounttemplatecode").trim();
        String cardCategory = request.getParameter("cardCategory").trim();


        cardBean = new CardTemplateBean();


        cardBean.setTemplateCode(templateCode);
        cardBean.setTemplateName(templateName);
        cardBean.setValidFrom(validFrom);
        cardBean.setValidTo(validTo);
        cardBean.setProductCode(productType);
        cardBean.setCurrencyCode(currencyType);
        cardBean.setTotalCreditLimit(totalLimit);
        cardBean.setStatus(status);
        cardBean.setStaffStatus(staffStatus);
        cardBean.setAccounttemplateCode(accTempCode);

        cardBean.setServiceCode(request.getParameter("serviceCode").trim());
        cardBean.setExpiryPeriod(request.getParameter("expiryPeriod").trim());
        cardBean.setRenewRequired(request.getParameter("renewRequired").trim());
        cardBean.setReissuThrshPeriod(request.getParameter("reissuThrshPeriod").trim());
        cardBean.setCashAdvanceRate(request.getParameter("cashAdvanceRate").trim());

        cardBean.setFeeProfCode(request.getParameter("feeProfileSelect").trim());
        cardBean.setRiskProfCode(request.getParameter("riskProfileDelect").trim());
        cardBean.setTxnProfCode(request.getParameter("txnProfileSelect").trim());

        cardBean.setLastUpdatedTime(new Date());
        cardBean.setLastUpdatedUser(sessionUser.getUserName());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

        cardBean.setCardCategoryCode(cardCategory);



        newValue = cardBean.getTemplateCode() + "|" + cardBean.getTemplateName() + "|" + cardBean.getValidFrom() + "|" + cardBean.getValidTo() + "|" + cardBean.getProductCode() + "|"
                + cardBean.getCurrencyCode() + "|" + cardBean.getTotalCreditLimit() + "|" + cardBean.getStatus() + "|" + cardBean.getStaffStatus() + "|"
                + cardBean.getAccounttemplateCode() + "|" + cardBean.getServiceCode() + "|" + cardBean.getExpiryPeriod() + "|" + cardBean.getRenewRequired() + cardBean.getReissuThrshPeriod() + "|"
                + cardBean.getCashAdvanceRate() + "|" + cardBean.getFeeProfCode() + "|" + cardBean.getRiskProfCode() + "|" + cardBean.getTxnProfCode() + "|" + sdf.format(new Date()) + "|" + cardBean.getCardCategoryCode() + "|" + cardBean.getLastUpdatedUser();

        this.searchCardDomainMgt();

        for (CardTemplateBean cardBean : searchList) {
            if (cardBean.getTemplateCode().equals(templateCode)) {
                oldValue = cardBean.getTemplateCode() + "|" + cardBean.getTemplateName() + "|" + cardBean.getValidFrom() + "|" + cardBean.getValidTo() + "|" + cardBean.getProductCode() + "|"
                        + cardBean.getCurrencyCode() + "|" + cardBean.getTotalCreditLimit() + "|" + cardBean.getStatus() + "|" + cardBean.getStaffStatus() + "|"
                        + cardBean.getAccounttemplateCode() + "|" + cardBean.getServiceCode() + "|" + cardBean.getExpiryPeriod() + "|" + cardBean.getRenewRequired() + cardBean.getReissuThrshPeriod() + "|"
                        + cardBean.getCashAdvanceRate() + "|" + cardBean.getFeeProfCode() + "|" + cardBean.getRiskProfCode() + "|" + cardBean.getTxnProfCode() + "|" + cardBean.getLastUpdatedTime() + "|" + cardBean.getCardCategoryCode() + "|" + sessionUser.getUserName();

            }
        }

    }

    public boolean validateUserInput(CardTemplateBean cardBean, HttpServletRequest request) throws Exception {
        boolean isValidate = true;


        //validate user Role code
        try {

            if (cardBean.getTemplateCode().contentEquals("") || cardBean.getTemplateCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_TEMPLATECODE_NULL;
            } else if (!UserInputValidator.isCorrectString(cardBean.getTemplateCode())) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_CODE_INVALID;

            } else if (cardBean.getTemplateName().contentEquals("") || cardBean.getTemplateName().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_TEMPLATENAME_NULL;
            } else if (!UserInputValidator.isCorrectString(cardBean.getTemplateName())) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_NAME_INVALID;

            } else if (cardBean.getAccounttemplateCode().contentEquals("") || cardBean.getAccounttemplateCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_ACCOUNTTEMP_NULL;
            } else if (cardBean.getValidFrom().contentEquals("") || cardBean.getValidFrom().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_FROM_NULL;
            } else if (cardBean.getValidTo().contentEquals("") || cardBean.getValidTo().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_TO_NULL;

            } else if (cardBean.getTotalCreditLimit().contentEquals("") || cardBean.getTotalCreditLimit().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_TOTALCREDIT_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(cardBean.getTotalCreditLimit(), "10", "2")) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_TOTALLIMIT_INVALID;


            } else if (cardBean.getStaffStatus().contentEquals("") || cardBean.getStaffStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_STAFFSTATUS_NULL;


            } else if (cardBean.getProductCode().contentEquals("") || cardBean.getProductCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_PRODUCT_NULL;


            } else if (cardBean.getCardCategoryCode().contentEquals("") || cardBean.getCardCategoryCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.CARD_CATEGORY_NULL;


            } else if (cardBean.getFeeProfCode().contentEquals("") || cardBean.getFeeProfCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.FEE_PROFILE_NULL;


            } else if (cardBean.getCurrencyCode().contentEquals("") || cardBean.getCurrencyCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_CURRENCY_NULL;


            } else if (cardBean.getRiskProfCode().contentEquals("") || cardBean.getRiskProfCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.RISK_PROFILE_NULL;

            } else if (cardBean.getServiceCode().contentEquals("") || cardBean.getServiceCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SERVICE_CODE_NULL;


            } else if (cardBean.getTxnProfCode().contentEquals("") || cardBean.getTxnProfCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.TRANSACTION_PROFILE_NULL;

            } else if (cardBean.getExpiryPeriod().contentEquals("") || cardBean.getExpiryPeriod().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.EXPIRY_PERIOD_NULL;
            } else if (!UserInputValidator.isNumeric(cardBean.getExpiryPeriod())) {
                isValidate = false;
                errorMessage = MessageVarList.EXPIRY_PERIOD_INVALID;


            } else if (cardBean.getRenewRequired().contentEquals("") || cardBean.getRenewRequired().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.RENEW_REQUIRED_NULL;
            } else if (!UserInputValidator.isNumeric(cardBean.getRenewRequired())) {
                isValidate = false;
                errorMessage = MessageVarList.RENEW_REQUIRED_INVALID;
            } else if (cardBean.getReissuThrshPeriod().contentEquals("") || cardBean.getReissuThrshPeriod().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.REISSUE_THRESHOLD_PERIOD_NULL;
            } else if (!UserInputValidator.isNumeric(cardBean.getReissuThrshPeriod())) {
                isValidate = false;
                errorMessage = MessageVarList.REISSUE_THRESHOLD_PERIOD_INVALID;

            } else if (Double.parseDouble(cardBean.getReissuThrshPeriod()) > Double.parseDouble(cardBean.getRenewRequired())) {

                isValidate = false;

                errorMessage = MessageVarList.RENEW_PERIOD_INVALID;


            } else if (cardBean.getCashAdvanceRate().contentEquals("") || cardBean.getCashAdvanceRate().length() <= 0) {

                isValidate = false;

                errorMessage = MessageVarList.CASH_ADVANCE_RATE_NULL;


            } else if (!UserInputValidator.isNumeric(cardBean.getCashAdvanceRate())) {

                isValidate = false;

                errorMessage = MessageVarList.CASH_ADVANCE_RATE_INVALID;


            } else if (cardBean.getStatus().contentEquals("") || cardBean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_STATUS_NULL;
            }


        } catch (Exception ex) {
            isValidate = false;

        }

        return isValidate;
    }

    private boolean updateCardDomain(CardTemplateBean cardBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            cardTemplateManager = new CardTemplateMgtManager();
            success = cardTemplateManager.updateCardDomain(cardBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(cardBean.getTemplateCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Card Template. Template Code: " + cardBean.getTemplateCode() + "; by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TEMPLATEMGT);
            systemAuditBean.setPageCode(PageVarList.CARDTEMPLATEHOME);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(oldValue);
            //set new value of change if required
            systemAuditBean.setNewValue(newValue);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
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

    private void searchCardDomainMgt() throws Exception {

        try {


            cardTemplateManager = new CardTemplateMgtManager();
            searchList = cardTemplateManager.getAllCardDomainSearchList();


        } catch (Exception ex) {
            throw ex;

        }
    }

    private void getAllCurrencyList() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            currencyList = domainManager.getAllCurrencyList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllAccountTemplateList() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            accountTemplateList = domainManager.getAllAccountTemplateList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean setSessionToInputDate(CardTemplateBean cardBean) throws Exception {
        boolean successSet = false;
        try {

            sessionVarlist.setCardBean(cardBean);
            successSet = true;
        } catch (Exception ex) {
            throw ex;
        }
        return successSet;
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
