/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.carddomaintemplate.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.accounttemplate.businesslogic.AccountTemplateManager;
import com.epic.cms.admin.templatemgt.carddomaintemplate.bean.CardDomainBean;
import com.epic.cms.admin.templatemgt.carddomaintemplate.businesslogic.CardDomainMgtManager;
import com.epic.cms.admin.templatemgt.debitcardtemplate.businesslogic.DebitCardTemplateMgtManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.businesslogic.BulkCardRequestManager;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @author chanuka
 */
public class AddCardDomainServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private SystemAuditBean systemAuditBean = null;
    private CardDomainMgtManager domainManager = null;
    private HashMap<String, String> currencyList = null;
    private HashMap<String, String> productList = null;
    private BulkCardRequestManager bulkCdReqMgr = null;
    private HashMap<String, String> interestProfileList = null;
    private HashMap<String, String> customerTemplateList = null;
    private boolean success = false;
    private CardDomainBean cardDomainBean;
    private HashMap<String, String> cardDomain;
    private List<String> userTaskList;
    private String url = "/administrator/templatemgt/carddomaintemplate/carddomainadd.jsp";
    //modifying
    private HashMap<String, String> riskProfiles = null;
    private AccountTemplateManager templateManager;
    private HashMap<String, String> cardType = null;
    private DebitCardTemplateMgtManager debitCardManager;
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


            request.setAttribute("selectedtab", "0");

            //set page code and task codes
            String pageCode = PageVarList.CARDDOMAINHOME;
            String taskCode = TaskVarList.ADD;


            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                this.setUserInputToBean(request);
                request.setAttribute("cardDomainBean", cardDomainBean);

                this.getAllCurrencyList();
                this.getAllInterestProfiles();
                this.getAllCustomerTemplateList();
                this.getRiskProfiles();
                this.getAllCardType();
                this.getCardDomains();

                request.setAttribute("riskProfiles", riskProfiles);
                request.setAttribute("cardType", cardType);

                request.setAttribute("currencyList", currencyList);
                request.setAttribute("interestProfileList", interestProfileList);
                request.setAttribute("customerTemplateList", customerTemplateList);
                request.setAttribute("cardDomainList", cardDomain);



                if (this.validateUserInput(cardDomainBean, request)) {

                    this.setAudittraceValue(request);

                    if (this.insertCardDomain(cardDomainBean, systemAuditBean)) {

                        request.setAttribute("successMsg", MessageVarList.SUCCESS_ADD_CARDDOMAIN + " Card Domain Template " + cardDomainBean.getTemplateCode());
                        rd = getServletContext().getRequestDispatcher("/LoadCardDomainTempMgtServlet");
                    } else {
                        request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_CARDDOMAIN);
                        rd = getServletContext().getRequestDispatcher(url);
                    }
                } else {

                    request.setAttribute("currencyList", currencyList);
                    request.setAttribute("interestProfileList", interestProfileList);

                    request.setAttribute("customerTemplateList", customerTemplateList);
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
            rd = getServletContext().getRequestDispatcher("/administrator/templatemgt/carddomaintemplate/carddomainhome.jsp");


        } catch (SQLException ex) {
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", cardDomainBean.getTemplateCode() + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_CARDDOMAIN);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    public void setUserInputToBean(HttpServletRequest request) {


        String custemplateCode = request.getParameter("custemplatecode").trim();
        String templateCode = request.getParameter("templatecode").trim();
        String templateName = request.getParameter("templatename").trim();
        String validFrom = request.getParameter("validFrom").trim();
        String validTo = request.getParameter("validTo").trim();
        String cardTypes = request.getParameter("cardType").trim();
        String currencyType = request.getParameter("currencytype").trim();
        String status = request.getParameter("status").trim();
        String cardDomains = request.getParameter("domain").trim();

//        String interestFeeCode = request.getParameter("interestfee").trim();
        String riskProf = request.getParameter("risk").trim();

        cardDomainBean = new CardDomainBean();

        cardDomainBean.setCustemplateCode(custemplateCode);
        cardDomainBean.setTemplateCode(templateCode);
        cardDomainBean.setTemplateName(templateName);
        cardDomainBean.setValidFrom(validFrom);
        cardDomainBean.setValidTo(validTo);
        cardDomainBean.setCardTypeCode(cardTypes);
        cardDomainBean.setCurrencyCode(currencyType);
        cardDomainBean.setStatus(status);
        cardDomainBean.setCardDomainCode(cardDomains);

//        cardDomainBean.setInterestProfileCode(interestFeeCode);
        cardDomainBean.setRiskProf(riskProf);
        cardDomainBean.setTxnProfCode(request.getParameter("txnProfile"));
        cardDomainBean.setLastUpdatedUser(sessionUser.getUserName());
        
        newValue = cardDomainBean.getCustemplateCode() + "|" + cardDomainBean.getTemplateCode() + "|" + cardDomainBean.getTemplateName() + "|" + 
                 cardDomainBean.getValidFrom() + "|" + cardDomainBean.getValidTo() + "|" + cardDomainBean.getCardTypeCode() + "|"
                + cardDomainBean.getCurrencyCode() + "|" + cardDomainBean.getStatus() + "|" + cardDomainBean.getCardDomainCode() ;
    }

    public boolean validateUserInput(CardDomainBean domainBean, HttpServletRequest request) throws Exception {
        boolean isValidate = true;


        //validate user Role code
        try {

            if (domainBean.getTemplateCode().contentEquals("") || domainBean.getTemplateCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_TEMPLATECODE_NULL;
            } else if (!UserInputValidator.isCorrectString(domainBean.getTemplateCode())) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_CODE_INVALID;
            } else if (domainBean.getTemplateName().contentEquals("") || domainBean.getTemplateName().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_TEMPLATENAME_NULL;
            } else if (!UserInputValidator.isCorrectString(domainBean.getTemplateName())) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_NAME_INVALID;
            } else if (domainBean.getStatus().contentEquals("") || domainBean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_STATUS_NULL;
            } else if (domainBean.getCustemplateCode().contentEquals("") || domainBean.getCustemplateCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_CUSTOMERTEMPLATE_NULL;
            } else if (domainBean.getValidFrom().contentEquals("") || domainBean.getValidFrom().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_FROM_NULL;
            } else if (domainBean.getValidTo().contentEquals("") || domainBean.getValidTo().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_TO_NULL;
            } else if (isLargerDate(domainBean.getValidFrom(), domainBean.getValidTo())) {
                isValidate = false;
                errorMessage = MessageVarList.TO_LARGER_FROM;
            } else if (domainBean.getCardDomainCode().contentEquals("") || domainBean.getCardDomainCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_CARDDOMAIN_NULL;
            } else if (domainBean.getCardTypeCode().contentEquals("") || domainBean.getCardTypeCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_TYPE_EMPTY;
            } else if (domainBean.getCurrencyCode().contentEquals("") || domainBean.getCurrencyCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_CURRENCY_NULL;
            }  else if (domainBean.getRiskProf().contentEquals("") || domainBean.getRiskProf().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_RISK_NULL;
            } else if (domainBean.getTxnProfCode().contentEquals("") || domainBean.getTxnProfCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.TRANSACTION_PROFILE_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;

        }

        return isValidate;
    }

    private boolean insertCardDomain(CardDomainBean domainBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            success = domainManager.insertCardDomain(domainBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(cardDomainBean.getTemplateCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Card Domain Template. Card Domain Template Code: '" + cardDomainBean.getTemplateCode() + "'  by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TEMPLATEMGT);
            systemAuditBean.setPageCode(PageVarList.CARDDOMAINHOME);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
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

    private void getAllCustomerTemplateList() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            customerTemplateList = domainManager.getAllCustomerTemplateList();

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

    private void getAllInterestProfiles() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            interestProfileList = domainManager.getAllInterestProfiles();

        } catch (Exception ex) {
            throw ex;
        }
    }

//    private void getAllCardHolderFeeProfiles() throws Exception {
//
//        try {
//
//            domainManager = new CardDomainMgtManager();
//            cardHolderFeeProfileList = domainManager.getAllCardHolderFeeProfiles();
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
    private void getAllCardType() throws Exception {
        templateManager = new AccountTemplateManager();
        cardType = templateManager.getAllCardType();


    }

    private void getRiskProfiles() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            riskProfiles = domainManager.getRiskProfiles();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean isLargerDate(String from, String to) throws ParseException {
        boolean isLarge = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date from_date = dateFormat.parse(from);
        Date to_date = dateFormat.parse(to);

        if (from_date.after(to_date)) {
            isLarge = true;
        }

        return isLarge;
    }

    private void getCardDomains() throws Exception {
        try {

            bulkCdReqMgr = new BulkCardRequestManager();
            cardDomain = bulkCdReqMgr.getCardDomains();

        } catch (SQLException ex) {
            throw ex;
        }

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
