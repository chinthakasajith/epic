/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantcustomer.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantCustomerBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.businesslogic.MerchantCustomerManager;
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
import java.sql.Date;
import java.sql.SQLException;
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
 * @author nalin
 */
public class UpdateMerchantCustomerServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private MerchantCustomerManager merchantManager;
    private String errorMessage = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private MerchantCustomerBean merchantBean;
    private boolean successInsert = false;
    List<String> existMccList = null;
    List<String> existTxnList = null;
    List<String> existCurrencyList = null;
    private List<MerchantCategoryBean> notAssigndnMerchantCatogoryList = null;
    private List<MerchantCategoryBean> assigndnMerchantCatogoryList = null;
    private List<TypeMgtBean> notAssigndnTxnTypeList = null;
    private List<TypeMgtBean> assigndnTxnTypeList = null;
    private List<CurrencyBean> notAssignCurrencyList = null;
    private List<CurrencyBean> assignCurrencyList = null;
    private String url = "/mtmm/merchantmgt/createmerchant.jsp";
    private String oldValue;
    private String newValue;

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
                String pageCode = PageVarList.MERCHANT_CUSTOMER;
                String taskCode = TaskVarList.UPDATE;

                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    try {

                        merchantBean = new MerchantCustomerBean();

                        if (setUserInputToBean(request)) {
                            merchantManager = new MerchantCustomerManager();
                            //////////get Not Assigned Merchannt Category List

                            notAssigndnMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                            notAssigndnMerchantCatogoryList = merchantManager.getNotAssignedMccList(merchantBean.getMerchantCustomerNumber());

                            ////////////get Assigned Category List

                            assigndnMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                            assigndnMerchantCatogoryList = merchantManager.getAssignedMccList(merchantBean.getMerchantCustomerNumber());

                            /////////////get Not Assigned Transaction Type List

                            notAssigndnTxnTypeList = new ArrayList<TypeMgtBean>();
                            notAssigndnTxnTypeList = merchantManager.getNotAssignedTxnTypeList(merchantBean.getMerchantCustomerNumber());

                            /////////////get Assigned Transaction Type List

                            assigndnTxnTypeList = new ArrayList<TypeMgtBean>();
                            assigndnTxnTypeList = merchantManager.getAssignedTxnTypeList(merchantBean.getMerchantCustomerNumber());

                            //////////////get Not Assignd Currency List
                            notAssignCurrencyList = new ArrayList<CurrencyBean>();
                            notAssignCurrencyList = merchantManager.getNotAssignedCurrencyList(merchantBean.getMerchantCustomerNumber());

                            ///////////////get Assigned Currency List
                            assignCurrencyList = new ArrayList<CurrencyBean>();
                            assignCurrencyList = merchantManager.getAssignedCurrencyList(merchantBean.getMerchantCustomerNumber());


                            if (validateUserInput(merchantBean)) {
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("selectedtab", "0");
                                this.setAudittraceValue(request);
                                try {

                                    successInsert = updateMerchantCustomer(merchantBean, systemAuditBean);

                                } catch (SQLException e) {
                                    String resultMessage = null;
                                    OracleMessage message = new OracleMessage();
                                    String oraMessage = message.getMessege(e.getMessage());

                                    if (oraMessage.equals(message.INTE_CHILD)) {
                                        oraMessage = "You cannot remove previously used Tasks.";
                                    }
                                    request.setAttribute("operationtype", "update");
                                    request.setAttribute("merchantBean", merchantBean);
                                    request.setAttribute("notAssigndnMerchantCatogoryList", notAssigndnMerchantCatogoryList);
                                    request.setAttribute("assigndnMerchantCatogoryList", assigndnMerchantCatogoryList);
                                    request.setAttribute("notAssigndnTxnTypeList", notAssigndnTxnTypeList);
                                    request.setAttribute("assigndnTxnTypeList", assigndnTxnTypeList);
                                    request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                                    request.setAttribute("assignCurrencyList", assignCurrencyList);
                                    request.setAttribute("operationtype", "update");
                                    request.setAttribute("errorMsg", oraMessage);
                                    rd = getServletContext().getRequestDispatcher(url);
                                    rd.forward(request, response);
                                }


                                if (successInsert) {


                                    request.setAttribute("successMsg", merchantBean.getMerchantName() + " " + MessageVarList.MERCHANT_CUSTOMER_SUCCESS_UPDATE);
                                    request.setAttribute("notAssigndnMerchantCatogoryList", notAssigndnMerchantCatogoryList);
                                    request.setAttribute("assigndnMerchantCatogoryList", assigndnMerchantCatogoryList);
                                    request.setAttribute("notAssigndnTxnTypeList", notAssigndnTxnTypeList);
                                    request.setAttribute("assigndnTxnTypeList", assigndnTxnTypeList);
                                    request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                                    request.setAttribute("assignCurrencyList", assignCurrencyList);
                                    request.setAttribute("merchantBean", merchantBean);
                                    request.setAttribute("selectedtab", "1");
                                    rd = getServletContext().getRequestDispatcher(url);

                                }
                            } else {
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("selectedtab", "0");
                                request.setAttribute("merchantBean", merchantBean);
                                request.setAttribute("notAssigndnMerchantCatogoryList", notAssigndnMerchantCatogoryList);
                                request.setAttribute("assigndnMerchantCatogoryList", assigndnMerchantCatogoryList);
                                request.setAttribute("notAssigndnTxnTypeList", notAssigndnTxnTypeList);
                                request.setAttribute("assigndnTxnTypeList", assigndnTxnTypeList);
                                request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                                request.setAttribute("assignCurrencyList", assignCurrencyList);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }
                        } else {
                            request.setAttribute("operationtype", "update");
                            request.setAttribute("selectedtab", "0");
                            request.setAttribute("merchantBean", merchantBean);
                            request.setAttribute("notAssigndnMerchantCatogoryList", notAssigndnMerchantCatogoryList);
                            request.setAttribute("assigndnMerchantCatogoryList", assigndnMerchantCatogoryList);
                            request.setAttribute("notAssigndnTxnTypeList", notAssigndnTxnTypeList);
                            request.setAttribute("assigndnTxnTypeList", assigndnTxnTypeList);
                            request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                            request.setAttribute("assignCurrencyList", assignCurrencyList);
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "update");
                        request.setAttribute("selectedtab", "0");
                        request.setAttribute("merchantBean", merchantBean);
                        request.setAttribute("notAssigndnMerchantCatogoryList", notAssigndnMerchantCatogoryList);
                        request.setAttribute("assigndnMerchantCatogoryList", assigndnMerchantCatogoryList);
                        request.setAttribute("notAssigndnTxnTypeList", notAssigndnTxnTypeList);
                        request.setAttribute("assigndnTxnTypeList", assigndnTxnTypeList);
                        request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                        request.setAttribute("assignCurrencyList", assignCurrencyList);
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
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
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * 
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean isSet = true;
        try {

            merchantBean = new MerchantCustomerBean();

            String merchantCustomerNumber = request.getParameter("merchantCustomerNumber").trim();
            String merchantName = request.getParameter("merchantName").trim();
            String legalName = request.getParameter("legalName").trim();
            String address1 = request.getParameter("address1").trim();
            String address2 = request.getParameter("address2").trim();
            String address3 = request.getParameter("address3").trim();
            String area = request.getParameter("selectArea").trim();
//            String postalCode = request.getParameter("selectPostalCode").trim();
            String country = request.getParameter("selectCountry").trim();
            String tpNumber = request.getParameter("tpNumber").trim();
            String fax = request.getParameter("fax").trim();
            String eMail = request.getParameter("eMail").trim();
            String cpTitle = request.getParameter("selectTitle").trim();
            String cpFirstName = request.getParameter("cpFirstName").trim();
            String cpMiddleName = request.getParameter("cpMiddleName").trim();
            String cpLastName = request.getParameter("cpLastName").trim();
            String feeProfile = request.getParameter("selectFeeProfile").trim();
            String commissionProfile = request.getParameter("selectCommissionProfile").trim();
//            String riskProfile = request.getParameter("selectRiskProfile").trim();
            String bankCode = request.getParameter("selectBank").trim();
            String branchCode = request.getParameter("selectBankBranch").trim();
            String accountNumber = request.getParameter("accountNumber").trim();
            String accountType = request.getParameter("selectAccountType").trim();
            String accountName = request.getParameter("accountName").trim();
            String applicationDate = request.getParameter("fromdate1").trim();
            String activationDate = request.getParameter("fromdate2").trim();
            String paymentMode = request.getParameter("selectPaymentMode").trim();
            String paymentCycle = request.getParameter("selectPaymentCycle").trim();
            String statementCycle = request.getParameter("selectStatementCycle").trim();
            String status = request.getParameter("selectStates").trim();
            String statementStatus = request.getParameter("statementStatus").trim();
            String paymentStatus = request.getParameter("paymentStatus").trim();
            String currencyType = request.getParameter("currencyType").trim();



            merchantBean.setMerchantCustomerNumber(merchantCustomerNumber);
            merchantBean.setMerchantName(merchantName);
            merchantBean.setLegalName(legalName);
            merchantBean.setAddress1(address1);
            merchantBean.setAddress2(address2);
            merchantBean.setAddress3(address3);
            merchantBean.setArea(area);
//            merchantBean.setPostalCode(postalCode);
            merchantBean.setCountry(country);
            merchantBean.setCpTitle(cpTitle);
            merchantBean.setCpFirstName(cpFirstName);
            merchantBean.setCpMiddleName(cpMiddleName);
            merchantBean.setCpLastName(cpLastName);
            merchantBean.setTpNumber(tpNumber);
            merchantBean.setFax(fax);
            merchantBean.seteMail(eMail);
            merchantBean.setFeeProfile(feeProfile);
            merchantBean.setCommissionProfile(commissionProfile);
//            merchantBean.setRiskProfile(riskProfile);
            merchantBean.setBankCode(bankCode);
            merchantBean.setBranchCode(branchCode);
            merchantBean.setAccountNumber(accountNumber);
            merchantBean.setAccountType(accountType);
            merchantBean.setAccountName(accountName);
            merchantBean.setApplicationDate(applicationDate);
            merchantBean.setActivationDate(activationDate);
            merchantBean.setPaymentMode(paymentMode);
            merchantBean.setPaymentCycle(paymentCycle);
            merchantBean.setStatementCycle(statementCycle);
            merchantBean.setStatus(status);
            merchantBean.setStatementStatus(statementStatus);
            merchantBean.setPaymentStatus(paymentStatus);
            merchantBean.setCurrencyType(currencyType);

            merchantBean.setRedempoint(request.getParameter("redempoint"));
            merchantBean.setLastUpdatedUser(sessionUser.getUserName());

            newValue = merchantBean.getMerchantCustomerNumber() + "|" + merchantBean.getMerchantName() + "|"
                    + merchantBean.getLegalName() + "|" + merchantBean.getAddress1() + "|" + merchantBean.getAddress2() + "|" + merchantBean.getAddress3() + "|"
                    + merchantBean.getArea() + "|" + merchantBean.getCountry() + "|" + merchantBean.getCpTitle() + merchantBean.getCpFirstName() + "|" + merchantBean.getCpMiddleName() + "|" + merchantBean.getCpLastName() + "|"
                    + merchantBean.getTpNumber() + "|" + merchantBean.getFax() + "|" + merchantBean.geteMail() + "|" + merchantBean.getFeeProfile() + "|"
                    + merchantBean.getCommissionProfile() + "|" + merchantBean.getBankCode() + "|" + merchantBean.getBranchCode() + "|" + merchantBean.getAccountNumber() + "|" + merchantBean.getAccountType() + "|" + merchantBean.getAccountName() + "|"
                    + merchantBean.getApplicationDate() + "|" + merchantBean.getActivationDate() + "|" + merchantBean.getPaymentMode() + "|" + merchantBean.getPaymentCycle() + "|" + merchantBean.getStatementCycle() + "|" + merchantBean.getStatus() + "|" + merchantBean.getStatementStatus() + "|" + merchantBean.getPaymentStatus() + "|"
                    + merchantBean.getCurrencyType();

//                    String[] toAssignMcc = request.getParameterValues("assignMerchantCategorylist");
//                    String mcc = "";
//                    for (int i = 0; i < toAssignMcc.length; i++) {
//                        mcc += toAssignMcc[i] + ",";
//                    }
//                    String[] toAssignTxn = request.getParameterValues("assignTxnTypeList");
//                    String txn = "";
//                    for (int i = 0; i < toAssignTxn.length; i++) {
//                        txn += toAssignTxn[i] + ",";
//                    }                    
//                    String[] toAssignCurrency = request.getParameterValues("assignCurrencyList");            
//                    String currency = "";
//                    for (int i = 0; i < toAssignCurrency.length; i++) {
//                        currency += toAssignCurrency[i] + ",";
//                    }                     
//            
//                    newValue +=  "|" + mcc + "|" + txn + "|" + currency;            

            MerchantCustomerManager mcm = new MerchantCustomerManager();
            List<MerchantCustomerBean> beanList = new ArrayList<MerchantCustomerBean>();
            beanList = mcm.getMerchantCustomerDetails();


            for (MerchantCustomerBean newMerchantBean : beanList) {

                if (newMerchantBean.getMerchantCustomerNumber().equals(merchantCustomerNumber)) {

                    oldValue = newMerchantBean.getMerchantCustomerNumber() + "|" + newMerchantBean.getMerchantName() + "|"
                            + newMerchantBean.getLegalName() + "|" + newMerchantBean.getAddress1() + "|" + newMerchantBean.getAddress2() + "|" + newMerchantBean.getAddress3() + "|"
                            + newMerchantBean.getArea() + "|" + newMerchantBean.getCountry() + "|" + newMerchantBean.getCpTitle() + newMerchantBean.getCpFirstName() + "|" + newMerchantBean.getCpMiddleName() + "|" + newMerchantBean.getCpLastName() + "|"
                            + newMerchantBean.getTpNumber() + "|" + newMerchantBean.getFax() + "|" + newMerchantBean.geteMail() + "|" + newMerchantBean.getFeeProfile() + "|"
                            + newMerchantBean.getCommissionProfile() + "|" + newMerchantBean.getBankCode() + "|" + newMerchantBean.getBranchCode() + "|" + newMerchantBean.getAccountNumber() + "|" + newMerchantBean.getAccountType() + "|" + newMerchantBean.getAccountName() + "|"
                            + newMerchantBean.getApplicationDate() + "|" + newMerchantBean.getActivationDate() + "|" + newMerchantBean.getPaymentMode() + "|" + newMerchantBean.getPaymentCycle() + "|" + newMerchantBean.getStatementCycle() + "|" + newMerchantBean.getStatus() + "|" + newMerchantBean.getStatementStatus() + "|" + newMerchantBean.getPaymentStatus() + "|"
                            + newMerchantBean.getCurrencyType();

                }
            }
            
                    List<MerchantCategoryBean> assigndnMerchantCatogoryList1 = new ArrayList<MerchantCategoryBean>();
                    assigndnMerchantCatogoryList1 = mcm.getAssignedMccList(merchantCustomerNumber);

                    String oldcategory = "";
                    for (MerchantCategoryBean bean : assigndnMerchantCatogoryList1) {
                        oldcategory += bean.getDescription() + ",";
                    }


                    List<TypeMgtBean> assigndnTxnTypeList1 = new ArrayList<TypeMgtBean>();
                    assigndnTxnTypeList1 = mcm.getAssignedTxnTypeList(merchantCustomerNumber);

                    String oldTxn = "";
                    for (TypeMgtBean bean : assigndnTxnTypeList1) {
                        oldTxn += bean.getDescription() + ",";
                    }

                    List<CurrencyBean> assignCurrencyList1 = new ArrayList<CurrencyBean>();
                    assignCurrencyList1 = mcm.getAssignedCurrencyList(merchantCustomerNumber);

                    String oldCurrency = "";
                    for (CurrencyBean bean : assignCurrencyList1) {
                        oldCurrency += bean.getCurrencyDes() + ",";
                    }

                    oldValue += "|" + oldcategory + "|" +  oldTxn + "|" + oldCurrency;            

        } catch (Exception e) {
            isSet = false;
            throw e;

        }

        return isSet;
    }

    /**
     * 
     * @param txnType
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(MerchantCustomerBean merchantBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code

        try {


            if (merchantBean.getMerchantCustomerNumber().contentEquals("") || merchantBean.getMerchantCustomerNumber().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MERCHANT_CUSTOMER_NUMBER_NULL;

            } else if (!UserInputValidator.isAlphaNumeric(merchantBean.getMerchantCustomerNumber())) {
                isValidate = false;

                errorMessage = MessageVarList.MERCHANT_CUSTOMER_NUMBER_INVALID;

            } else if (merchantBean.getMerchantName().contentEquals("") || merchantBean.getMerchantName().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MERCHANT_CUSTOMER_NAME_NULL;

            } else if (!UserInputValidator.isDescription(merchantBean.getMerchantName())) {
                isValidate = false;

                errorMessage = MessageVarList.MERCHANT_CUSTOMER_NAME_INVALID;

            } else if (merchantBean.getLegalName().contentEquals("") || merchantBean.getLegalName().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.LEGAL_NAME_NULL;

            } else if (!UserInputValidator.isDescription(merchantBean.getLegalName())) {
                isValidate = false;

                errorMessage = MessageVarList.LEGAL_NAME_INVALID;

            } else if (merchantBean.getAddress1().contentEquals("") || merchantBean.getAddress1().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ADDRESS1_NULL;

            } else if (!UserInputValidator.isCorrectAddress(merchantBean.getAddress1())) {
                isValidate = false;

                errorMessage = MessageVarList.ADDRESS_FIELD1_INVALID;

            } else if (merchantBean.getAddress2().contentEquals("") || merchantBean.getAddress2().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ADDRESS1_NULL;

            } else if (!UserInputValidator.isCorrectAddress(merchantBean.getAddress2())) {
                isValidate = false;

                errorMessage = MessageVarList.ADDRESS_FIELD2_INVALID;

            } else if (!(merchantBean.getAddress3().contentEquals("") || merchantBean.getAddress3().length() <= 0) && !UserInputValidator.isCorrectAddress(merchantBean.getAddress3())) {
                isValidate = false;

                errorMessage = MessageVarList.ADDRESS_FIELD3_INVALID;

            } else if (merchantBean.getArea().contentEquals("") || merchantBean.getArea().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.AREA_NULL;

//            } else if (merchantBean.getPostalCode().contentEquals("") || merchantBean.getPostalCode().length() <= 0) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.POSTAL_CODE_NULL;

            } else if (merchantBean.getCountry().contentEquals("") || merchantBean.getCountry().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.COUNTRY_NULL;

            } else if (merchantBean.getCpTitle().contentEquals("") || merchantBean.getCpTitle().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.TITLE_NULL;

            } else if (merchantBean.getCpFirstName().contentEquals("") || merchantBean.getCpFirstName().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.FNAME_NULL;

            } else if (!UserInputValidator.isAlpha(merchantBean.getCpFirstName())) {
                isValidate = false;

                errorMessage = MessageVarList.FNAME_INVALID;

            } else if (!UserInputValidator.isAlpha(merchantBean.getCpMiddleName())) {
                isValidate = false;

                errorMessage = MessageVarList.MNAME_INVALID;

            } else if (merchantBean.getCpLastName().contentEquals("") || merchantBean.getCpLastName().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.LNAME_NULL;

            } else if (!UserInputValidator.isPersonName(merchantBean.getCpLastName())) {
                isValidate = false;

                errorMessage = MessageVarList.LNAME_INVALID;

            } else if (merchantBean.getTpNumber().contentEquals("") || merchantBean.getTpNumber().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.TP_NUMBER_NULL;

            } else if (!UserInputValidator.isPhoneNumber(merchantBean.getTpNumber())) {
                isValidate = false;

                errorMessage = MessageVarList.TP_NUMBER_INVALID;

            } else if (merchantBean.getFax().contentEquals("") || merchantBean.getFax().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.FAX_NULL;

            } else if (!UserInputValidator.isPhoneNumber(merchantBean.getFax())) {
                isValidate = false;

                errorMessage = MessageVarList.FAX_NUMBER_INVALID;

            } else if (merchantBean.geteMail().contentEquals("") || merchantBean.geteMail().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.EMAIL_NULL;


            } else if (!UserInputValidator.isValidEmail(merchantBean.geteMail())) {
                isValidate = false;

                errorMessage = MessageVarList.EMAIL_INVALID;

            } else if (merchantBean.getFeeProfile().contentEquals("") || merchantBean.getFeeProfile().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.FEE_PROFILE_NULL;

            } else if (merchantBean.getCommissionProfile().contentEquals("") || merchantBean.getCommissionProfile().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.COMMISION_PROFILE_NULL;

//            } else if (merchantBean.getRiskProfile().contentEquals("") || merchantBean.getRiskProfile().length() <= 0) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.RISK_PROFILE_NULL;

            } else if (merchantBean.getStatementCycle().contentEquals("") || merchantBean.getStatementCycle().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.STATEMENT_CYCLE_NULL;

            } else if (merchantBean.getPaymentCycle().contentEquals("") || merchantBean.getPaymentCycle().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.PAYMENT_CYCLE_NULL;

            } else if (merchantBean.getPaymentMode().contentEquals("") || merchantBean.getPaymentMode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.PAYMENT_MODE_NULL;

            } else if (merchantBean.getStatementStatus().contentEquals("") || merchantBean.getStatementStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.REQUIRED;

            } else if (merchantBean.getPaymentStatus().contentEquals("") || merchantBean.getPaymentStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.REQUIRED;

            } else if (merchantBean.getCurrencyType().contentEquals("") || merchantBean.getCurrencyType().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.CURRENCY_NULL;

            } else if (merchantBean.getRedempoint().contentEquals("") || merchantBean.getRedempoint().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.REDEM_POINT_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(merchantBean.getRedempoint(), "20", "2")) {
                isValidate = false;

                errorMessage = MessageVarList.REDEM_POINT_INVALID;

            } else if (merchantBean.getBankCode().contentEquals("") || merchantBean.getBankCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.BANK_NAME_NULL;

            } else if (merchantBean.getAccountNumber().contentEquals("") || merchantBean.getAccountNumber().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ACCOUNT_NUMBER_NULL;

            } else if (!UserInputValidator.isAccountNumber(merchantBean.getAccountNumber())) {
                isValidate = false;

                errorMessage = MessageVarList.ACCOUNT_NUMBER_INVALID;

            } else if (merchantBean.getAccountName().contentEquals("") || merchantBean.getAccountName().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ACCOUNT_NAME_NULL;

            } else if (!UserInputValidator.isDescription(merchantBean.getAccountName())) {
                isValidate = false;

                errorMessage = MessageVarList.ACCOUNT_NAME_INVALID;

            } else if (merchantBean.getBranchCode().contentEquals("") || merchantBean.getBranchCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.BRANCH_NAME_NULL;


            } else if (merchantBean.getAccountType().contentEquals("") || merchantBean.getAccountType().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ACCOUNT_TYPE_NULL;


            } else if (merchantBean.getApplicationDate().contentEquals("") || merchantBean.getApplicationDate().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.APPLICATION_DATE_NULL;

            } else if (merchantBean.getActivationDate().contentEquals("") || merchantBean.getActivationDate().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ACTIVATION_DATE_NULL;

            } else if ((Date.valueOf(merchantBean.getActivationDate())).before(Date.valueOf(merchantBean.getApplicationDate()))) {

                isValidate = false;

                errorMessage = MessageVarList.APPLICATION_DATE_BEFORE_ACTIVATION_DATE;

            } else if (merchantBean.getStatus().contentEquals("") || merchantBean.getStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.STATUS_NULL;

            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    /**
     * 
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(merchantBean.getMerchantCustomerNumber());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Merchant Customer. Merchant Customer Number : " + merchantBean.getMerchantCustomerNumber() + "; by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.MERCHANT_TERMINAL_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.MERCHANTMGT);
            systemAuditBean.setPageCode(PageVarList.MERCHANT_CUSTOMER);
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

    /**
     * 
     * @param txnType
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean updateMerchantCustomer(MerchantCustomerBean merchantBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            merchantManager = new MerchantCustomerManager();
            success = merchantManager.updateMerchantCustomer(merchantBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private void assignMcc(String merchantCustomerNumber, String[] assinArray, String[] UnAssinArray, String sysUser, SystemAuditBean systemAuditBean) throws Exception {

        try {

            merchantManager = new MerchantCustomerManager();
            existMccList = merchantManager.assignMcc(merchantCustomerNumber, assinArray, UnAssinArray, sysUser, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void assignTxn(String merchantCustomerNumber, String[] assinArray, String[] UnAssinArray, String sysUser, SystemAuditBean systemAuditBean) throws Exception {

        try {

            merchantManager = new MerchantCustomerManager();
            existTxnList = merchantManager.assignTxn(merchantCustomerNumber, assinArray, UnAssinArray, sysUser, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void assignCurrency(String merchantCustomerNumber, String[] assinArray, String[] UnAssinArray, String sysUser, SystemAuditBean systemAuditBean) throws Exception {

        try {

            merchantManager = new MerchantCustomerManager();
            existCurrencyList = merchantManager.assignCurrency(merchantCustomerNumber, assinArray, UnAssinArray, sysUser, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 
     * @param userrole
     * @param pagecode
     * @param task
     * @return
     * @throws Exception 
     */
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
