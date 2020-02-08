/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantlocation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantCustomerBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.businesslogic.MerchantCustomerManager;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.bean.MerchantLocationBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.businesslogic.MerchantLocationManager;
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
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
public class UpdateMerchantLocationServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private MerchantLocationManager merchantLocManager;
    private String errorMessage = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private MerchantLocationBean merchantLocBean;
    private boolean successInsert = false;
    List<String> existMccList = null;
    List<String> existTxnList = null;
    List<String> existCurrencyList = null;
    private List<MerchantCustomerBean> merchantCustomerList = null;
    private List<MerchantCategoryBean> notAssigndnedMerchantCatogoryList = null;
    private List<MerchantCategoryBean> assigndnedMerchantCatogoryList = null;
    private List<TypeMgtBean> notAssigndnedTxnTypeList = null;
    private List<TypeMgtBean> assigndnedTxnTypeList = null;
    private List<CurrencyBean> notAssignCurrencyList = null;
    private List<CurrencyBean> assignCurrencyList = null;
    private String url = "/mtmm/merchantmgt/createmerchantlocation.jsp";
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
                String pageCode = PageVarList.MERCHANT_LOCATION;
                String taskCode = TaskVarList.UPDATE;

                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    MerchantCustomerManager ccm = new MerchantCustomerManager();
                    merchantCustomerList = new ArrayList<MerchantCustomerBean>();
                    //get  MerchantCustomer Name details
                    merchantCustomerList = ccm.getMerchantCustomerDetails();

                    try {
                        merchantLocBean = new MerchantLocationBean();

                        if (setUserInputToBean(request)) {

                            merchantLocManager = new MerchantLocationManager();
                            //////////get Not Assigned Merchannt Category List

                            notAssigndnedMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                            notAssigndnedMerchantCatogoryList = merchantLocManager.getNotAssignedMccList(merchantLocBean.getMerchantId(), merchantLocBean.getMerchantCustomerNumber());

                            ////////////get Assigned Category List

                            assigndnedMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                            assigndnedMerchantCatogoryList = merchantLocManager.getAssignedMccList(merchantLocBean.getMerchantId());

                            /////////////get Not Assigned Transaction Type List

                            notAssigndnedTxnTypeList = new ArrayList<TypeMgtBean>();
                            notAssigndnedTxnTypeList = merchantLocManager.getNotAssignedTxnTypeList(merchantLocBean.getMerchantId(), merchantLocBean.getMerchantCustomerNumber());

                            /////////////get Assigned Transaction Type List

                            assigndnedTxnTypeList = new ArrayList<TypeMgtBean>();
                            assigndnedTxnTypeList = merchantLocManager.getAssignedTxnTypeList(merchantLocBean.getMerchantId());

                            ////////////get Not Assigned Currency List

                            notAssignCurrencyList = new ArrayList<CurrencyBean>();
                            notAssignCurrencyList = merchantLocManager.getNotAssignedCurrencyList(merchantLocBean.getMerchantId(), merchantLocBean.getMerchantCustomerNumber());

                            ///////////get Assigned Currency List
                            assignCurrencyList = new ArrayList<CurrencyBean>();
                            assignCurrencyList = merchantLocManager.getAssignedCurrencyList(merchantLocBean.getMerchantId());


                            if (validateUserInput(merchantLocBean)) {
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("selectedtab", "0");
                                this.setAudittraceValue(request);

                                try {

                                    successInsert = updateMerchantLocation(merchantLocBean, systemAuditBean);

                                } catch (SQLException e) {
                                    String resultMessage = null;
                                    OracleMessage message = new OracleMessage();
                                    String oraMessage = message.getMessege(e.getMessage());

                                    if (oraMessage.equals(message.INTE_CHILD)) {
                                        oraMessage = "You cannot remove previously used Tasks.";

                                    }

                                    request.setAttribute("operationtype", "update");
                                    request.setAttribute("selectedtab", "0");
                                    request.setAttribute("merchantLocBean", merchantLocBean);
                                    request.setAttribute("notAssigndnedMerchantCatogoryList", notAssigndnedMerchantCatogoryList);
                                    request.setAttribute("assigndnedMerchantCatogoryList", assigndnedMerchantCatogoryList);
                                    request.setAttribute("notAssigndnedTxnTypeList", notAssigndnedTxnTypeList);
                                    request.setAttribute("assigndnedTxnTypeList", assigndnedTxnTypeList);
                                    request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                                    request.setAttribute("assignCurrencyList", assignCurrencyList);
                                    request.setAttribute(RequestVarList.MERCHANTCUSTOMER_LIST, merchantCustomerList);
                                    request.setAttribute("operationtype", "update");
                                    request.setAttribute("errorMsg", oraMessage);
                                    rd = getServletContext().getRequestDispatcher(url);
                                    rd.forward(request, response);
                                }

                                if (successInsert) {
                                    request.setAttribute("successMsg", merchantLocBean.getDescription() + " " + MessageVarList.MERCHANT_LOCATION_SUCCESS_UPDATE);
                                    request.setAttribute("merchantLocBean", merchantLocBean);
                                    request.setAttribute("notAssigndnedMerchantCatogoryList", notAssigndnedMerchantCatogoryList);
                                    request.setAttribute("assigndnedMerchantCatogoryList", assigndnedMerchantCatogoryList);
                                    request.setAttribute("notAssigndnedTxnTypeList", notAssigndnedTxnTypeList);
                                    request.setAttribute("assigndnedTxnTypeList", assigndnedTxnTypeList);
                                    request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                                    request.setAttribute("assignCurrencyList", assignCurrencyList);
                                    request.setAttribute(RequestVarList.MERCHANTCUSTOMER_LIST, merchantCustomerList);
                                    request.setAttribute("selectedtab", "1");
                                    rd = getServletContext().getRequestDispatcher(url);
                                }
                            } else {
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("selectedtab", "0");
                                request.setAttribute("merchantLocBean", merchantLocBean);
                                request.setAttribute("notAssigndnedMerchantCatogoryList", notAssigndnedMerchantCatogoryList);
                                request.setAttribute("assigndnedMerchantCatogoryList", assigndnedMerchantCatogoryList);
                                request.setAttribute("notAssigndnedTxnTypeList", notAssigndnedTxnTypeList);
                                request.setAttribute("assigndnedTxnTypeList", assigndnedTxnTypeList);
                                request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                                request.setAttribute("assignCurrencyList", assignCurrencyList);
                                request.setAttribute(RequestVarList.MERCHANTCUSTOMER_LIST, merchantCustomerList);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }
                        } else {
                            request.setAttribute("operationtype", "update");
                            request.setAttribute("selectedtab", "0");
                            request.setAttribute("merchantLocBean", merchantLocBean);
                            request.setAttribute("notAssigndnedMerchantCatogoryList", notAssigndnedMerchantCatogoryList);
                            request.setAttribute("assigndnedMerchantCatogoryList", assigndnedMerchantCatogoryList);
                            request.setAttribute("notAssigndnedTxnTypeList", notAssigndnedTxnTypeList);
                            request.setAttribute("assigndnedTxnTypeList", assigndnedTxnTypeList);
                            request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                            request.setAttribute("assignCurrencyList", assignCurrencyList);
                            request.setAttribute(RequestVarList.MERCHANTCUSTOMER_LIST, merchantCustomerList);
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "update");
                        request.setAttribute("selectedtab", "0");
                        request.setAttribute("merchantLocBean", merchantLocBean);
                        request.setAttribute("notAssigndnedMerchantCatogoryList", notAssigndnedMerchantCatogoryList);
                        request.setAttribute("assigndnedMerchantCatogoryList", assigndnedMerchantCatogoryList);
                        request.setAttribute("notAssigndnedTxnTypeList", notAssigndnedTxnTypeList);
                        request.setAttribute("assigndnedTxnTypeList", assigndnedTxnTypeList);
                        request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                        request.setAttribute("assignCurrencyList", assignCurrencyList);
                        request.setAttribute(RequestVarList.MERCHANTCUSTOMER_LIST, merchantCustomerList);
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
            rd.forward(request, response);
            out.close();
        }
    }

    /*
     * get user inputs from jsp for updates
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean isSet = true;
        try {

            merchantLocBean = new MerchantLocationBean();

            String merchantId = request.getParameter("merchantId").trim();
            String merchantCustomerNumber = request.getParameter("selectMerchantCustomerName").trim();
            String description = request.getParameter("description").trim();
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
            String cpPosotion = request.getParameter("cpPosotion").trim();
            String feeProfile = request.getParameter("selectFeeProfile").trim();
            String commissionProfile = request.getParameter("selectCommisionProfile").trim();
            String riskProfile = request.getParameter("selectRiskProfile").trim();
            String activationDate = request.getParameter("selectActivationDate").trim();
            String status = request.getParameter("selectStates").trim();

            String bankCode = request.getParameter("selectBank").trim();
            String branchCode = request.getParameter("selectBankBranch").trim();
            String accountNumber = request.getParameter("accountNumber").trim();
            String accountType = request.getParameter("selectAccountType").trim();
            String accountName = request.getParameter("accountName").trim();


            String paymentMode = request.getParameter("selectPaymentMode").trim();
            String paymentCycle = request.getParameter("selectPaymentCycle").trim();
            String statementCycle = request.getParameter("selectStatementCycle").trim();

            String statementStatus = request.getParameter("statementStatus").trim();
            String currencyType = request.getParameter("currencyType").trim();
            //String paymentStatus = request.getParameter("paymentStatus").trim();



            merchantLocBean.setMerchantId(merchantId);
            merchantLocBean.setMerchantCustomerNumber(merchantCustomerNumber);
            merchantLocBean.setDescription(description);
            merchantLocBean.setAddress1(address1);
            merchantLocBean.setAddress2(address2);
            merchantLocBean.setAddress3(address3);
            merchantLocBean.setArea(area);
//            merchantLocBean.setPostalCode(postalCode);
            merchantLocBean.setCountry(country);
            merchantLocBean.setCpTitle(cpTitle);
            merchantLocBean.setCpFirstName(cpFirstName);
            merchantLocBean.setCpMiddleName(cpMiddleName);
            merchantLocBean.setCpLastName(cpLastName);
            merchantLocBean.setCpPosotion(cpPosotion);
            merchantLocBean.setTpNumber(tpNumber);
            merchantLocBean.setFax(fax);
            merchantLocBean.seteMail(eMail);
            merchantLocBean.setFeeProfile(feeProfile);
            merchantLocBean.setCommissionProfile(commissionProfile);
            merchantLocBean.setRiskProfile(riskProfile);
            merchantLocBean.setActivationDate(activationDate);
            merchantLocBean.setStatus(status);

            merchantLocBean.setBankCode(bankCode);
            merchantLocBean.setBranchCode(branchCode);
            merchantLocBean.setAccountNumber(accountNumber);
            merchantLocBean.setAccountType(accountType);
            merchantLocBean.setAccountName(accountName);

            merchantLocBean.setPaymentMode(paymentMode);
            merchantLocBean.setPaymentCycle(paymentCycle);
            merchantLocBean.setStatementCycle(statementCycle);
            merchantLocBean.setStatementStatus(statementStatus);
            merchantLocBean.setCurrencyType(currencyType);
            merchantLocBean.setRedempoint(request.getParameter("redempoint"));
            merchantLocBean.setManualTxnStatus(request.getParameter("manualTxnStatus"));
            sessionVarlist.setManualTxnStatus(merchantLocBean.getManualTxnStatus());

            merchantLocBean.setLastUpdatedUser(sessionUser.getUserName());

            newValue = merchantLocBean.getMerchantId() + "|" + merchantLocBean.getMerchantCustomerNumber() + "|" + merchantLocBean.getDescription() + "|" + merchantLocBean.getAddress1() + "|" + merchantLocBean.getAddress2() + "|" + merchantLocBean.getAddress3() + "|"
                    + merchantLocBean.getArea() + "|" + merchantLocBean.getCountry() + "|" + merchantLocBean.getCpTitle() + "|" + merchantLocBean.getCpFirstName() + "|" + merchantLocBean.getCpMiddleName() + "|" + merchantLocBean.getCpLastName() + "|" + merchantLocBean.getCpPosotion() + "|"
                    + merchantLocBean.getTpNumber() + "|" + merchantLocBean.getFax() + "|" + merchantLocBean.geteMail() + "|" + merchantLocBean.getFeeProfile() + "|" + merchantLocBean.getCommissionProfile() + "|" + merchantLocBean.getRiskProfile() + "|" + merchantLocBean.getActivationDate() + "|" + merchantLocBean.getStatus() + "|" + merchantLocBean.getBankCode() + "|"
                    + merchantLocBean.getBranchCode() + "|" + merchantLocBean.getAccountNumber() + "|" + merchantLocBean.getAccountType() + "|" + merchantLocBean.getAccountName() + "|" + merchantLocBean.getPaymentMode() + "|" + merchantLocBean.getPaymentCycle() + "|" + merchantLocBean.getStatementCycle() + "|" + merchantLocBean.getStatementStatus() + "|" + merchantLocBean.getCurrencyType() + "|"
                    + merchantLocBean.getRedempoint() + "|"  + merchantLocBean.getLastUpdatedUser();

            merchantLocManager = new MerchantLocationManager();
            List<MerchantLocationBean> beanList = merchantLocManager.getMerchantLocationDetails();
            for (MerchantLocationBean newMerchantLocBean : beanList) {

                if (newMerchantLocBean.getMerchantId().equals(merchantId)) {

                    oldValue = newMerchantLocBean.getMerchantId() + "|" + newMerchantLocBean.getMerchantCustomerNumber() + "|" + newMerchantLocBean.getDescription() + "|" + newMerchantLocBean.getAddress1() + "|" + newMerchantLocBean.getAddress2() + "|" + newMerchantLocBean.getAddress3() + "|"
                            + newMerchantLocBean.getArea() + "|" + newMerchantLocBean.getCountry() + "|" + newMerchantLocBean.getCpTitle() + "|" + newMerchantLocBean.getCpFirstName() + "|" + newMerchantLocBean.getCpMiddleName() + "|" + newMerchantLocBean.getCpLastName() + "|" + newMerchantLocBean.getCpPosotion() + "|"
                            + newMerchantLocBean.getTpNumber() + "|" + newMerchantLocBean.getFax() + "|" + newMerchantLocBean.geteMail() + "|" + newMerchantLocBean.getFeeProfile() + "|" + newMerchantLocBean.getCommissionProfile() + "|" + newMerchantLocBean.getRiskProfile() + "|" + newMerchantLocBean.getActivationDate() + "|" + newMerchantLocBean.getStatus() + "|" + newMerchantLocBean.getBankCode() + "|"
                            + newMerchantLocBean.getBranchCode() + "|" + newMerchantLocBean.getAccountNumber() + "|" + newMerchantLocBean.getAccountType() + "|" + newMerchantLocBean.getAccountName() + "|" + newMerchantLocBean.getPaymentMode() + "|" + newMerchantLocBean.getPaymentCycle() + "|" + newMerchantLocBean.getStatementCycle() + "|" + newMerchantLocBean.getStatementStatus() + "|" + newMerchantLocBean.getCurrencyType() + "|"
                            + newMerchantLocBean.getRedempoint() + "|" + newMerchantLocBean.getLastUpdatedUser();


                }
            }


        } catch (Exception e) {
            isSet = false;
            throw e;

        }

        return isSet;
    }

    public boolean validateUserInput(MerchantLocationBean merchantLocBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code

        try {

            if (merchantLocBean.getMerchantId().contentEquals("") || merchantLocBean.getMerchantId().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MERCHANT_LOCATION_ID_NULL;


            } else if (!UserInputValidator.isAlphaNumeric(merchantLocBean.getMerchantId())) {
                isValidate = false;

                errorMessage = MessageVarList.MERCHANT_LOCATION_ID_INVALID;

            } else if (merchantLocBean.getMerchantId().length() != 15) {
                isValidate = false;

                errorMessage = MessageVarList.MERCHANT_LOCATION_ID_INVALID;

            } else if (merchantLocBean.getDescription().contentEquals("") || merchantLocBean.getDescription().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MERCHANT_LOCATION_DESCRIPTION_NULL;

            } else if (!UserInputValidator.isDescription(merchantLocBean.getDescription())) {
                isValidate = false;

                errorMessage = MessageVarList.MERCHANT_LOCATION_DESCRIPTION_INVALIED;

            } else if (merchantLocBean.getMerchantCustomerNumber().contentEquals("") || merchantLocBean.getMerchantCustomerNumber().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MERCHANT_CUSTOMER_NAME_SELECT_NULL;

            } else if (merchantLocBean.getAddress1().contentEquals("") || merchantLocBean.getAddress1().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ADDRESS1_NULL;

            } else if (!UserInputValidator.isCorrectAddress(merchantLocBean.getAddress1())) {
                isValidate = false;

                errorMessage = MessageVarList.ADDRESS_FIELD1_INVALID;

            } else if (merchantLocBean.getAddress2().contentEquals("") || merchantLocBean.getAddress2().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ADDRESS1_NULL;

            } else if (!UserInputValidator.isCorrectAddress(merchantLocBean.getAddress2())) {
                isValidate = false;

                errorMessage = MessageVarList.ADDRESS_FIELD2_INVALID;

            } else if (!(merchantLocBean.getAddress3().contentEquals("") || merchantLocBean.getAddress3().length() <= 0) && !UserInputValidator.isCorrectAddress(merchantLocBean.getAddress3())) {
                isValidate = false;

                errorMessage = MessageVarList.ADDRESS_FIELD3_INVALID;

            } else if (merchantLocBean.getArea().contentEquals("") || merchantLocBean.getArea().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.AREA_NULL;

//            } else if (merchantLocBean.getPostalCode().contentEquals("") || merchantLocBean.getPostalCode().length() <= 0) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.POSTAL_CODE_NULL;

            } else if (merchantLocBean.getCountry().contentEquals("") || merchantLocBean.getCountry().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.COUNTRY_NULL;

            } else if (merchantLocBean.getCpTitle().contentEquals("") || merchantLocBean.getCpTitle().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.TITLE_NULL;

            } else if (merchantLocBean.getCpFirstName().contentEquals("") || merchantLocBean.getCpFirstName().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.FNAME_NULL;

            } else if (!UserInputValidator.isAlpha(merchantLocBean.getCpFirstName())) {
                isValidate = false;

                errorMessage = MessageVarList.FNAME_INVALID;

            } else if (!UserInputValidator.isAlpha(merchantLocBean.getCpMiddleName())) {
                isValidate = false;

                errorMessage = MessageVarList.MNAME_INVALID;

            } else if (merchantLocBean.getCpLastName().contentEquals("") || merchantLocBean.getCpLastName().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.LNAME_NULL;

            } else if (!UserInputValidator.isPersonName(merchantLocBean.getCpLastName())) {
                isValidate = false;

                errorMessage = MessageVarList.LNAME_INVALID;

            } else if (merchantLocBean.getCpPosotion().contentEquals("") || merchantLocBean.getCpPosotion().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.POSITION_NULL;

            } else if (!UserInputValidator.isDescription(merchantLocBean.getCpPosotion())) {
                isValidate = false;

                errorMessage = MessageVarList.POSITION_INVALID;

            } else if (merchantLocBean.getTpNumber().contentEquals("") || merchantLocBean.getTpNumber().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.TP_NUMBER_NULL;

            } else if (!UserInputValidator.isPhoneNumber(merchantLocBean.getTpNumber())) {
                isValidate = false;

                errorMessage = MessageVarList.TP_NUMBER_INVALID;

            } else if (merchantLocBean.getFax().contentEquals("") || merchantLocBean.getFax().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.FAX_NULL;

            } else if (!UserInputValidator.isPhoneNumber(merchantLocBean.getFax())) {
                isValidate = false;

                errorMessage = MessageVarList.FAX_NUMBER_INVALID;

            } else if (merchantLocBean.geteMail().contentEquals("") || merchantLocBean.geteMail().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.EMAIL_NULL;

            } else if (!UserInputValidator.isValidEmail(merchantLocBean.geteMail())) {
                isValidate = false;

                errorMessage = MessageVarList.EMAIL_INVALID;

            } else if (merchantLocBean.getFeeProfile().contentEquals("") || merchantLocBean.getFeeProfile().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.FEE_PROFILE_NULL;

            } else if (merchantLocBean.getCommissionProfile().contentEquals("") || merchantLocBean.getCommissionProfile().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.COMMISION_PROFILE_NULL;

            } else if (merchantLocBean.getRiskProfile().contentEquals("") || merchantLocBean.getRiskProfile().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.RISK_PROFILE_NULL;

            } else if (merchantLocBean.getStatementCycle().contentEquals("") || merchantLocBean.getStatementCycle().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.STATEMENT_CYCLE_NULL;

            } else if (merchantLocBean.getPaymentCycle().contentEquals("") || merchantLocBean.getPaymentCycle().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.PAYMENT_CYCLE_NULL;

            } else if (merchantLocBean.getPaymentMode().contentEquals("") || merchantLocBean.getPaymentMode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.PAYMENT_MODE_NULL;

            } else if (merchantLocBean.getStatementStatus().contentEquals("") || merchantLocBean.getStatementStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.STATEMENT_STATUS_EMPTY;

            } else if (merchantLocBean.getCurrencyType().contentEquals("") || merchantLocBean.getCurrencyType().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.CURRENCY_NULL;

            } else if (merchantLocBean.getRedempoint().contentEquals("") || merchantLocBean.getRedempoint().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.REDEM_POINT_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(merchantLocBean.getRedempoint(), "20", "2")) {
                isValidate = false;

                errorMessage = MessageVarList.REDEM_POINT_INVALID;

            } else if (merchantLocBean.getBankCode().contentEquals("") || merchantLocBean.getBankCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.BANK_NAME_NULL;

            } else if (merchantLocBean.getAccountNumber().contentEquals("") || merchantLocBean.getAccountNumber().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ACCOUNT_NUMBER_NULL;

            } else if (!UserInputValidator.isAccountNumber(merchantLocBean.getAccountNumber())) {
                isValidate = false;

                errorMessage = MessageVarList.ACCOUNT_NUMBER_INVALID;

            } else if (merchantLocBean.getAccountName().contentEquals("") || merchantLocBean.getAccountName().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ACCOUNT_NAME_NULL;

            } else if (!UserInputValidator.isDescription(merchantLocBean.getAccountName())) {
                isValidate = false;

                errorMessage = MessageVarList.ACCOUNT_NAME_INVALID;

            } else if (merchantLocBean.getBranchCode().contentEquals("") || merchantLocBean.getBranchCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.BRANCH_NAME_NULL;

            } else if (merchantLocBean.getAccountType().contentEquals("") || merchantLocBean.getAccountType().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ACCOUNT_TYPE_NULL;

            } else if (merchantLocBean.getActivationDate().contentEquals("") || merchantLocBean.getActivationDate().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ACTIVATION_DATE_NULL;

            } else if (merchantLocBean.getStatus().contentEquals("") || merchantLocBean.getStatus().length() <= 0) {
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
            String uniqueId = request.getParameter(merchantLocBean.getMerchantId());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Merchant Location. Merchant Location Id: " + merchantLocBean.getMerchantId() + " ; by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.MERCHANT_TERMINAL_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.MERCHANTMGT);
            systemAuditBean.setPageCode(PageVarList.MERCHANT_LOCATION);
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
     * call updateMerchantLocation method
     * @param merchantLocBean
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean updateMerchantLocation(MerchantLocationBean merchantLocBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            merchantLocManager = new MerchantLocationManager();
            success = merchantLocManager.updateMerchantLocation(merchantLocBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
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
