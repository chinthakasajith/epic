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
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
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
public class UpdateMerchantCustomerAssignDataServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private MerchantCustomerManager merchantManager;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
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
    private String[] notToAssignMcc;
    private String[] toAssignMcc;
    private String[] notToAssignTxn;
    private String[] toAssignTxn;
    private String[] notToAssignCurrency;
    private String[] toAssignCurrency;
    private String merchantCustomerNumber = null;
    private MerchantCustomerBean merchantBean;
    private String url = "/mtmm/merchantmgt/createmerchant.jsp";

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
                            notToAssignMcc = request.getParameterValues("notAssigndnedMerchantCatogoryList");
                            toAssignMcc = request.getParameterValues("assigndnedMerchantCatogoryList");
                            notToAssignTxn = request.getParameterValues("notAssigndnedTxnTypeList");
                            toAssignTxn = request.getParameterValues("assigndnedTxnTypeList");
                            notToAssignCurrency = request.getParameterValues("notAssignCurrencyList");
                            toAssignCurrency = request.getParameterValues("assignCurrencyList");

                            merchantCustomerNumber = sessionVarlist.getMerchantCustomerNumber();

                            merchantManager = new MerchantCustomerManager();
                            //////////get Not Assigned Merchannt Category List

                            notAssigndnMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                            notAssigndnMerchantCatogoryList = merchantManager.getNotAssignedMccList(merchantCustomerNumber);

                            ////////////get Assigned Category List

                            assigndnMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                            assigndnMerchantCatogoryList = merchantManager.getAssignedMccList(merchantCustomerNumber);

                            /////////////get Not Assigned Transaction Type List

                            notAssigndnTxnTypeList = new ArrayList<TypeMgtBean>();
                            notAssigndnTxnTypeList = merchantManager.getNotAssignedTxnTypeList(merchantCustomerNumber);

                            /////////////get Assigned Transaction Type List

                            assigndnTxnTypeList = new ArrayList<TypeMgtBean>();
                            assigndnTxnTypeList = merchantManager.getAssignedTxnTypeList(merchantCustomerNumber);

                            //////////////get Not Assignd Currency List
                            notAssignCurrencyList = new ArrayList<CurrencyBean>();
                            notAssignCurrencyList = merchantManager.getNotAssignedCurrencyList(merchantCustomerNumber);

                            ///////////////get Assigned Currency List
                            assignCurrencyList = new ArrayList<CurrencyBean>();
                            assignCurrencyList = merchantManager.getAssignedCurrencyList(merchantCustomerNumber);

                            request.setAttribute("operationtype", "update");
                            request.setAttribute("selectedtab", "1");
                            this.setAudittraceValue(request);

                            try {

                                this.assignMcc(merchantCustomerNumber, toAssignMcc, notToAssignMcc, sessionUser.getUserName(), systemAuditBean);
                                this.assignTxn(merchantCustomerNumber, toAssignTxn, notToAssignTxn, sessionUser.getUserName(), systemAuditBean);
                                this.assignCurrency(merchantCustomerNumber, toAssignCurrency, notToAssignCurrency, sessionUser.getUserName(), systemAuditBean);

                                if (existMccList.size() > 0 || existTxnList.size() > 0 || existCurrencyList.size() > 0) {

                                    merchantManager.deleteMcc(existMccList, merchantCustomerNumber);
                                    merchantManager.deleteTxn(existTxnList, merchantCustomerNumber);
                                    merchantManager.deleteCurrency(existCurrencyList, merchantCustomerNumber);
                                }
                                successInsert = true;

                            } catch (SQLException e) {
                                String resultMessage = null;
                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());

                                if (oraMessage.equals(message.INTE_CHILD)) {
                                    oraMessage = "You cannot remove previously used Tasks.";
                                    
                                }

                                request.setAttribute("operationtype", "update");
                                request.setAttribute("selectedtab", "1");
                                request.setAttribute("notAssigndnMerchantCatogoryList", notAssigndnMerchantCatogoryList);
                                request.setAttribute("assigndnMerchantCatogoryList", assigndnMerchantCatogoryList);
                                request.setAttribute("notAssigndnTxnTypeList", notAssigndnTxnTypeList);
                                request.setAttribute("assigndnTxnTypeList", assigndnTxnTypeList);
                                request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                                request.setAttribute("assignCurrencyList", assignCurrencyList);
                                request.setAttribute("merchantBean", merchantBean);
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("errorMsg", oraMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);
                            }

                            if (successInsert) {

                                request.setAttribute("successMsg", MessageVarList.MERCHANT_CUSTOMER_SUCCESS_UPDATE);
                                rd = getServletContext().getRequestDispatcher("/LoadMerchantCustomerServlet");
                            }

                        } else {
                            request.setAttribute("operationtype", "update");
                            request.setAttribute("selectedtab", "1");
                            request.setAttribute("merchantBean", merchantBean);
                            request.setAttribute("notAssigndnMerchantCatogoryList", notAssigndnMerchantCatogoryList);
                            request.setAttribute("assigndnMerchantCatogoryList", assigndnMerchantCatogoryList);
                            request.setAttribute("notAssigndnTxnTypeList", notAssigndnTxnTypeList);
                            request.setAttribute("assigndnTxnTypeList", assigndnTxnTypeList);
                            request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                            request.setAttribute("assignCurrencyList", assignCurrencyList);
                            request.setAttribute("merchantBean", merchantBean);
                            rd = getServletContext().getRequestDispatcher(url);
                        }
                    } catch (Exception e) {
                        request.setAttribute("operationtype", "update");
                        request.setAttribute("selectedtab", "1");
                        request.setAttribute("notAssigndnMerchantCatogoryList", notAssigndnMerchantCatogoryList);
                        request.setAttribute("assigndnMerchantCatogoryList", assigndnMerchantCatogoryList);
                        request.setAttribute("notAssigndnTxnTypeList", notAssigndnTxnTypeList);
                        request.setAttribute("assigndnTxnTypeList", assigndnTxnTypeList);
                        request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                        request.setAttribute("assignCurrencyList", assignCurrencyList);
                        request.setAttribute("merchantBean", merchantBean);
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

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(merchantCustomerNumber);

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Assign Data to : '" + merchantCustomerNumber + "' Merchant Customer Number by : " + sessionVarlist.getCMSSessionUser().getUserName());
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
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue("");


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }

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

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean isSet = true;
        try {

            merchantBean = new MerchantCustomerBean();

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



            merchantBean.setMerchantCustomerNumber(request.getParameter("merchantCustomerNumber").trim());
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
            merchantBean.setLastUpdatedUser(sessionUser.getUserName());

        } catch (Exception e) {
            isSet = false;
            throw e;

        }

        return isSet;
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
