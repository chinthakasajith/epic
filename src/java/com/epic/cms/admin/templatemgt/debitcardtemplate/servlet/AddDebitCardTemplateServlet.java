/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.debitcardtemplate.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.accounttemplate.businesslogic.AccountTemplateManager;
import com.epic.cms.admin.templatemgt.carddomaintemplate.businesslogic.CardDomainMgtManager;
import com.epic.cms.admin.templatemgt.debitcardtemplate.bean.DebitCardTemplateBean;
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
public class AddDebitCardTemplateServlet extends HttpServlet {

    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private SessionVarList sessionVarlist;
    private SystemUserManager systemUserManager;
    private SystemAuditBean systemAuditBean = null;
    private CardDomainMgtManager domainManager = null;
    private DebitCardTemplateMgtManager debitCardManager;
    //---------------------------------------------------    
    boolean success = false;
    private String errorMessage = null;    
    private DebitCardTemplateBean debitBean;
    private HashMap<String, String> currencyList = null;    
    private HashMap<String, String> interestProfileList = null;
    private HashMap<String, String> customerTemplateList = null;
    private HashMap<String, String> debitAccountTemplateList = null;
    private String url = "/administrator/templatemgt/debitcardtemplate/debitcardadd.jsp";
    //modofying
    private BulkCardRequestManager bulkCdReqMgr = null;
    private HashMap<String, String> cardDomain = null;
    private HashMap<String, String> feeProfiles = null;
    private HashMap<String, String> riskProfiles = null;
    private HashMap<String, String> transactionProfiles = null;
    private AccountTemplateManager templateManager;
    private HashMap<String, String> cardType = null;

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
            String pageCode = PageVarList.DEBITCARDHOME;
            String taskCode = TaskVarList.ADD;

            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                this.setUserInputToBean(request);
                request.setAttribute("debitBean", debitBean);

                this.getAllCustomerTemplateList();
                this.getAllDebitAccountTemplateList();
                this.getAllCurrencyList();

                this.getFeeProfiles();
                this.getTransactionProfiles();
                this.getRiskProfiles();
                this.getAllCardType();
                this.getCardDomains();
                request.setAttribute("cardDomainList", cardDomain);

                request.setAttribute("cardType", cardType);
                request.setAttribute("currencyList", currencyList);
                request.setAttribute("feeProfiles", feeProfiles);
                request.setAttribute("riskProfiles", riskProfiles);
                request.setAttribute("transactionProfiles", transactionProfiles);
                request.setAttribute("debitAccountTemplateList", debitAccountTemplateList);


                if (this.validateUserInput(debitBean, request)) {

                    this.setAudittraceValue(request);

                    if (this.insertDebitCardTemplate(debitBean, systemAuditBean)) {

                        request.setAttribute("successMsg", MessageVarList.SUCCESS_ADD_DEBIT_CARD);
                        rd = getServletContext().getRequestDispatcher("/LoadDebitCardTemplateMgtServlet");
                    } else {
                        request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_DEBIT_CARD);
                        rd = getServletContext().getRequestDispatcher(url);
                    }
                } else {

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
            rd = getServletContext().getRequestDispatcher("/administrator/templatemgt/debitcardtemplate/debitcardhome.jsp");

        } catch (SQLException ex) {
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", debitBean.getTemplateCode() + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_DEBIT_CARD);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void getAllDebitAccountTemplateList() throws Exception {

        try {

            debitCardManager = new DebitCardTemplateMgtManager();
            debitAccountTemplateList = debitCardManager.getAllDebitAccountTemplateList();

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

//    private void getAllProductList() throws Exception {
//
//        try {
//
//            domainManager = new CardDomainMgtManager();
//            productList = domainManager.getAllProductList();
//
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }

//    private void getAllInterestProfiles() throws Exception {
//
//        try {
//
//            domainManager = new CardDomainMgtManager();
//            interestProfileList = domainManager.getAllInterestProfiles();
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }

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

    public void setUserInputToBean(HttpServletRequest request) {

        debitBean = new DebitCardTemplateBean();

        debitBean.setTemplateCode(request.getParameter("templatecode").trim());
        debitBean.setTemplateName(request.getParameter("templatename").trim());
        debitBean.setValidFrom(request.getParameter("validFrom").trim());
        debitBean.setValidTo(request.getParameter("validTo").trim());
        debitBean.setCardTypeCode(request.getParameter("cardType").trim());
        
        debitBean.setProductCode(request.getParameter("cproduct").trim());
        
        debitBean.setCurrencyCode(request.getParameter("currencytype").trim());
        debitBean.setExpPeriod(request.getParameter("expiryPeriod").trim());
        debitBean.setStatus(request.getParameter("status").trim());
        debitBean.setRenewPeriod(request.getParameter("renewPeriod").trim());
        debitBean.setReissueThreshPeriod(request.getParameter("reissuThrshPeriod").trim());
        debitBean.setDebitAccounttemplateCode(request.getParameter("debitaccounttemplatecode").trim());
        debitBean.setCashAdvanceRate(request.getParameter("cashAdvanceRate"));
        debitBean.setCardDomain(request.getParameter("domain").trim());
        debitBean.setServiceCode(request.getParameter("serviceCode").trim());

        debitBean.setFeeProfile(request.getParameter("fee").trim());
        debitBean.setRiskProf(request.getParameter("risk").trim());
        debitBean.setTxnProf(request.getParameter("transaction").trim());

        debitBean.setLastUpdatedTime(new Date());
        debitBean.setLastUpdatedUser(sessionUser.getUserName());

    }

    public boolean validateUserInput(DebitCardTemplateBean debitBean, HttpServletRequest request) throws Exception {
        boolean isValidate = true;


        //validate user Role code
        try {

            if (debitBean.getTemplateCode().contentEquals("") || debitBean.getTemplateCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_TEMPLATECODE_NULL;
            } else if (!UserInputValidator.isCorrectString(debitBean.getTemplateCode())) {
                isValidate = false;
                errorMessage = MessageVarList.TEMP_CODE_INVALID;

            } else if (debitBean.getTemplateName().contentEquals("") || debitBean.getTemplateName().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_TEMPLATENAME_NULL;
            } else if (!UserInputValidator.isCorrectString(debitBean.getTemplateName())) {
                isValidate = false;
                errorMessage = MessageVarList.TEMP_NAME_INVALID;
            } else if (debitBean.getValidFrom().contentEquals("") || debitBean.getValidFrom().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_FROM_NULL;
            } else if (debitBean.getValidTo().contentEquals("") || debitBean.getValidTo().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_TO_NULL;
            }else if (isLargerDate(debitBean.getValidFrom(),debitBean.getValidTo())) {
                isValidate = false;
                errorMessage = MessageVarList.TO_LARGER_FROM;
            }else if (debitBean.getDebitAccounttemplateCode().contentEquals("") || debitBean.getDebitAccounttemplateCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_ACCOUNTTEMP_NULL;
            } else if (debitBean.getCardDomain().contentEquals("") || debitBean.getCardDomain().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CD_DOMAIN_NULL;
            }else if (debitBean.getCardTypeCode().contentEquals("") || debitBean.getCardTypeCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_TYPE_EMPTY;
            }else if (debitBean.getProductCode().contentEquals("") || debitBean.getProductCode().length() <= 0) {
                isValidate = false;
                errorMessage = "Card product empty";
            }
            
            else if (debitBean.getCurrencyCode().contentEquals("") || debitBean.getCurrencyCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_CURRENCY_NULL;
            }  else if (debitBean.getServiceCode().contentEquals("") || debitBean.getServiceCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SERVICECODE_NULL;
            }  else if (debitBean.getExpPeriod().contentEquals("") || debitBean.getExpPeriod().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.EXPIRY_PERIOD_NULL;
            } else if (!UserInputValidator.isNumeric(debitBean.getExpPeriod())) {
                isValidate = false;
                errorMessage = MessageVarList.EXPIRY_PERIOD_INVALID;
            } else if (debitBean.getRenewPeriod().contentEquals("") || debitBean.getRenewPeriod().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.RENEW_PERIOD_NULL;
            } else if (!UserInputValidator.isNumeric(debitBean.getRenewPeriod())) {
                isValidate = false;
                errorMessage = MessageVarList.RENEW_PERIOD_INVALID;
            } else if (debitBean.getReissueThreshPeriod().contentEquals("") || debitBean.getReissueThreshPeriod().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.REISSUE_THRESHOLD_PERIOD_NULL;
            } else if (!UserInputValidator.isNumeric(debitBean.getReissueThreshPeriod())) {
                isValidate = false;
                errorMessage = MessageVarList.REISSUE_THRESHOLD_PERIOD_INVALID;
            } else if (debitBean.getCashAdvanceRate().contentEquals("") || debitBean.getCashAdvanceRate().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CASH_ADVANCE_NULL;
            }else if (!UserInputValidator.isNumeric(debitBean.getCashAdvanceRate())) {
                isValidate = false;
                errorMessage = MessageVarList.CASH_ADVANCE_INVALID;
            }else if (0 > Integer.parseInt(debitBean.getCashAdvanceRate()) || 100 < Integer.parseInt(debitBean.getCashAdvanceRate())) {
                isValidate = false;
                errorMessage = MessageVarList.CASH_ADVANCE_INVALID;
            }else if (debitBean.getFeeProfile().contentEquals("") || debitBean.getFeeProfile().length() <= 0) {
                isValidate = false;                
                errorMessage = MessageVarList.FEE_PROFILE_NULL;
            } else if (debitBean.getRiskProf().contentEquals("") || debitBean.getRiskProf().length() <= 0) {
                isValidate = false;                
                errorMessage = MessageVarList.RISK_PROFILE_NULL;
            } else if (debitBean.getTxnProf().contentEquals("") || debitBean.getTxnProf().length() <= 0) {
                isValidate = false;                
                errorMessage = MessageVarList.TRANSACTION_PROFILE_NULL;
            }else if (debitBean.getStatus().contentEquals("") || debitBean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARDDOMAIN_STATUS_NULL;
            } 
        } catch (Exception ex) {
            isValidate = false;
        }
        return isValidate;
    }

    private boolean insertDebitCardTemplate(DebitCardTemplateBean debitBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            debitCardManager = new DebitCardTemplateMgtManager();
            success = debitCardManager.insertDebitCardTemplate(debitBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(debitBean.getTemplateCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Bulk Card Template. Bulk Card Template Code: '" + debitBean.getTemplateCode() + "' by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TEMPLATEMGT);
            systemAuditBean.setPageCode(PageVarList.DEBITCARDHOME);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
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

    private void getFeeProfiles() throws Exception {

        try {

            debitCardManager = new DebitCardTemplateMgtManager();
            feeProfiles = debitCardManager.getFeeProfiles();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getRiskProfiles() throws Exception {

        try {

            debitCardManager = new DebitCardTemplateMgtManager();
            riskProfiles = debitCardManager.getRiskProfiles();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getTransactionProfiles() throws Exception {

        try {

            debitCardManager = new DebitCardTemplateMgtManager();
            transactionProfiles = debitCardManager.getTransactionProfiles();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllCardType() throws Exception {
        templateManager = new AccountTemplateManager();
        cardType = templateManager.getAllCardType();


    }

    private void getCardDomains() throws Exception {
        try {

            bulkCdReqMgr = new BulkCardRequestManager();
            cardDomain = bulkCdReqMgr.getCardDomains();
        } catch (SQLException ex) {
            throw ex;
        }

    }
    
    private boolean  isLargerDate(String from,String to) throws ParseException{
        boolean isLarge = false;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date from_date = dateFormat.parse(from);
        Date to_date = dateFormat.parse(to);
        
        if(from_date.after(to_date)){
        isLarge = true;
        }
        
        return isLarge; 
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
