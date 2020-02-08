/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.manualtxn.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.mtmm.manualtxn.bean.SaleTxnBean;
import com.epic.cms.mtmm.manualtxn.businesslogic.SaleTxnManager;
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
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import epic.cms.util.APIConfig;
import epic.cms.util.TxnBean;
import epic.cms.util.TxnHelp;
import epic.cms.util.TxnTypeCode;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * @author nalin
 */
public class ProceedCashAdvancedServlet extends HttpServlet {

    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private SaleTxnBean cashAdvancedBean = null;
    private HashMap<String, String> terminalList = null;
    private String errorMessage = null;
    private SaleTxnManager saleTxnManager = null;
    private List<SaleTxnBean> cashAdvancedList = null;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> currencyList = null;
    private TxnBean txnBean = null;
    private String url = "/mtmm/manualtxn/cashadvancedtxnhome.jsp";

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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
            try {

                sessionObj = request.getSession(false);
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }

                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();

                }

                try {
                    //set page code and task codes
                    String pageCode = PageVarList.CASH_ADVANCED_TRANSACTION;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                        //////////////////////////////////////////////
                        try {

                            setUserInputToBean(request);

                            this.getActiveTerminalOfMerchant(cashAdvancedBean.getMerchantID());
                            this.getTerminalTxnInfo(cashAdvancedBean.getTerminalID(), cashAdvancedBean.getMerchantID());
                            this.getCardTypes();
                            this.getCurrency();

                            request.setAttribute("cardTypeList", cardTypeList);
                            request.setAttribute("currencyList", currencyList);

                            if (this.validateUserInput(cashAdvancedBean)) {


                                APIConfig.initAPI(StatusVarList.HOST_IP, StatusVarList.NIT, StatusVarList.HOST_PORT, StatusVarList.TIMEOUT, StatusVarList.DEBUGLEVEL);
                                this.setDataToCommunicationBean(cashAdvancedBean);
                                this.setDataToCashAdvancedBean(txnBean);
                                this.setAudittraceValue(request);
                                this.insertToManualTxnTable(cashAdvancedBean, systemAuditBean);


                                APIConfig.call(txnBean);

                                this.getDateFromCommunicationBean(txnBean);


                                if (txnBean.getRESPONSECODE().equals(StatusVarList.MANUAL_TXN_SUCCESS_STATUS)) {
                                    request.setAttribute("successMsg2", MessageVarList.TRANSACTION_SUCCESS);
                                } else {
                                    request.setAttribute("errorMsg2", MessageVarList.TRANSACTION_ERROR + this.getResponsemessage(txnBean.getRESPONSECODE()));
                                }

                                this.updateManualTxnTable(cashAdvancedBean, systemAuditBean);

                                this.getTerminalTxnInfo(cashAdvancedBean.getTerminalID(), cashAdvancedBean.getMerchantID());

                                request.setAttribute("cashAdvancedBean", cashAdvancedBean);
                                request.setAttribute("terminalList", terminalList);
                                request.setAttribute("cardTypeList", cardTypeList);
                                request.setAttribute("cashAdvancedList", cashAdvancedList);

                            } else {

                                request.setAttribute("errorMsg", errorMessage);
                                request.setAttribute("cashAdvancedBean", cashAdvancedBean);
                                request.setAttribute("terminalList", terminalList);
                                request.setAttribute("cashAdvancedList", cashAdvancedList);
                                rd = getServletContext().getRequestDispatcher(url);
                            }

                        } catch (ConnectException ex) {

                            request.setAttribute("errorMsg2", MessageVarList.COMMUNICATION_ERROR);
                            request.setAttribute("cashAdvancedBean", cashAdvancedBean);
                            request.setAttribute("terminalList", terminalList);
                            request.setAttribute("cashAdvancedList", cashAdvancedList);
                            rd = getServletContext().getRequestDispatcher(url);

                        } catch (SocketTimeoutException socex) {
                            request.setAttribute("errorMsg", MessageVarList.COMMUNICATION_ERROR);
                            request.setAttribute("cashAdvancedBean", cashAdvancedBean);
                            request.setAttribute("terminalList", terminalList);
                            request.setAttribute("cashAdvancedList", cashAdvancedList);
                            rd = getServletContext().getRequestDispatcher(url);

                        } catch (Exception ex) {
                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            request.setAttribute("cashAdvancedBean", cashAdvancedBean);
                            request.setAttribute("terminalList", terminalList);
                            request.setAttribute("cashAdvancedList", cashAdvancedList);
                            rd = getServletContext().getRequestDispatcher(url);
                        }


                        //////////////////////////////////////////////
                    } else {
                        throw new AccessDeniedException();

                    }

                    sessionVarlist.setUserPageTaskList(userTaskList);

                } catch (AccessDeniedException adex) {
                    throw adex;

                }

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            rd = request.getRequestDispatcher(url);


        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);

        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.ACCESS_DENIED_TASK);

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private HashMap<String, String> getActiveTerminalOfMerchant(String merchantID) throws Exception {

        try {
            terminalList = new HashMap<String, String>();
            saleTxnManager = new SaleTxnManager();

            terminalList = saleTxnManager.getActiveManualTerminalOfMerchant(merchantID);

        } catch (Exception ex) {
            throw ex;
        }
        return terminalList;

    }

    private List<SaleTxnBean> getTerminalTxnInfo(String terminalID, String merchantId) throws Exception {

        try {
            cashAdvancedList = new ArrayList<SaleTxnBean>();
            saleTxnManager = new SaleTxnManager();

            cashAdvancedList = saleTxnManager.getTerminalTxnInfo(terminalID, merchantId);

        } catch (Exception ex) {
            throw ex;
        }
        return cashAdvancedList;

    }

    private HashMap<String, String> getCardTypes() throws Exception {

        try {

            cardTypeList = new HashMap<String, String>();
            saleTxnManager = new SaleTxnManager();

            cardTypeList = saleTxnManager.getCardTypes();

        } catch (Exception ex) {
            throw ex;
        }

        return cardTypeList;
    }

    private HashMap<String, String> getCurrency() throws Exception {

        try {

            currencyList = new HashMap<String, String>();
            saleTxnManager = new SaleTxnManager();

            currencyList = saleTxnManager.getCurrency();

        } catch (Exception ex) {
            throw ex;
        }

        return currencyList;
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = true;
        try {

            cashAdvancedBean = new SaleTxnBean();

            cashAdvancedBean.setMerchantID(request.getParameter("merchantID").trim());
            cashAdvancedBean.setMerchantName(request.getParameter("merchantName").trim());
            cashAdvancedBean.setTerminalID(request.getParameter("terminalID").trim());
            cashAdvancedBean.setCardNumber(request.getParameter("cardNumber").trim());
            cashAdvancedBean.setCardType(request.getParameter("cardType").trim());
            cashAdvancedBean.setExpiryDate(request.getParameter("expiryDate").trim());
            cashAdvancedBean.setCvvValue(request.getParameter("cvvValue").trim());
            cashAdvancedBean.setCurrencyType(request.getParameter("currencyType").trim());
            cashAdvancedBean.setTxnAmount(request.getParameter("txnAmount").trim());


        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    public boolean validateUserInput(SaleTxnBean cashAdvancedBean) throws Exception {
        boolean isValidate = true;

        //////validate merchantID

        if (cashAdvancedBean.getMerchantID().contentEquals("") || cashAdvancedBean.getMerchantID().length() <= 0) {
            isValidate = false;

            errorMessage = MessageVarList.MERCHANT_LOCATION_ID_NULL;

        } else if (!UserInputValidator.isNumeric(cashAdvancedBean.getMerchantID()) || cashAdvancedBean.getMerchantID().length() != 15) {
            isValidate = false;

            errorMessage = MessageVarList.MERCHANT_LOCATION_ID_INVALID;

        } else if (cashAdvancedBean.getTerminalID().contentEquals("") || cashAdvancedBean.getTerminalID().length() <= 0) {
            isValidate = false;

            errorMessage = MessageVarList.TERMINAL_ID_NULL;

        } else if (cashAdvancedBean.getCardNumber().contentEquals("") || cashAdvancedBean.getCardNumber().length() <= 0) {
            isValidate = false;

            errorMessage = MessageVarList.CARD_NUMBER_NULL;

        } else if (!UserInputValidator.isNumeric(cashAdvancedBean.getCardNumber()) || cashAdvancedBean.getCardNumber().length() != 16) {
            isValidate = false;

            errorMessage = MessageVarList.CARDNUMBER_INVALID;

        } else if (cashAdvancedBean.getCardType().contentEquals("") || cashAdvancedBean.getCardType().length() <= 0) {
            isValidate = false;

            errorMessage = MessageVarList.CARD_TYPE_EPMTY;

        } else if (cashAdvancedBean.getExpiryDate().contentEquals("") || cashAdvancedBean.getExpiryDate().length() <= 0) {
            isValidate = false;

            errorMessage = MessageVarList.EXPIRY_DATE_NULL;

        } else if (cashAdvancedBean.getCvvValue().contentEquals("") || cashAdvancedBean.getCvvValue().length() <= 0) {
            isValidate = false;

            errorMessage = MessageVarList.CVV_VALUE_NULL;

        } else if (!UserInputValidator.isNumeric(cashAdvancedBean.getCvvValue())) {
            isValidate = false;

            errorMessage = MessageVarList.CVV_VALUE_INVALID;

        } else if (cashAdvancedBean.getCurrencyType().contentEquals("") || cashAdvancedBean.getCurrencyType().length() <= 0) {
            isValidate = false;

            errorMessage = MessageVarList.CURRENCY_NULL;

        } else if (cashAdvancedBean.getTxnAmount().contentEquals("") || cashAdvancedBean.getTxnAmount().length() <= 0) {
            isValidate = false;

            errorMessage = MessageVarList.TRANSACTION_AMOUNT_NULL;

        } else if (!UserInputValidator.isDecimalOrNumeric(cashAdvancedBean.getTxnAmount(), "10", "2")) {
            isValidate = false;

            errorMessage = MessageVarList.TRANSACTION_AMOUNT_INVALID;

        }

        try {
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
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
            String uniqueId = cashAdvancedBean.getRrn();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add: '" + cashAdvancedBean.getRrn() + "' by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.MERCHANT_TERMINAL_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.MANUAL_TXN);
            systemAuditBean.setPageCode(PageVarList.CASH_ADVANCED_TRANSACTION);
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

    private void setDataToCommunicationBean(SaleTxnBean saleBean) throws Exception {

        try {
            txnBean = new TxnBean();

            txnBean.setCARDNUMBER(saleBean.getCardNumber());
            txnBean.setAMOUNT(saleBean.getTxnAmount());
            txnBean.setTRACENO(TxnHelp.getTraceNo());
            txnBean.setINVOICENO(TxnHelp.getInoviceNo());
            txnBean.setTID(saleBean.getTerminalID());
            txnBean.setMID(saleBean.getMerchantID());
            txnBean.setTXNTYPE(TxnTypeCode.TXN_SALE);

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void getDateFromCommunicationBean(TxnBean txnBean) throws Exception {

        try {
            cashAdvancedBean.setRrn(txnBean.getRRN());
            cashAdvancedBean.setAuthCode(txnBean.getAUTHCODE());
            cashAdvancedBean.setResponceCode(txnBean.getRESPONSECODE());
            cashAdvancedBean.setTxnTime(txnBean.getTIME());
            cashAdvancedBean.setTxnDate(txnBean.getDATE());

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void setDataToCashAdvancedBean(TxnBean txnBean) throws Exception {

        try {


            cashAdvancedBean.setTraceNo(txnBean.getTRACENO());
            cashAdvancedBean.setInvoiceNo(txnBean.getINVOICENO());
            cashAdvancedBean.setTxntype(TxnTypeCode.TXN_SALE);
            cashAdvancedBean.setTxnID(this.generateTxnId());

        } catch (Exception ex) {
            throw ex;
        }

    }

    private String getResponsemessage(String responseCode) throws Exception {
        String responseMsg = null;
        try {

            saleTxnManager = new SaleTxnManager();

            responseMsg = saleTxnManager.getResponsemessage(responseCode);

        } catch (Exception ex) {
            throw ex;
        }
        return responseMsg;

    }

    private boolean insertToManualTxnTable(SaleTxnBean cashAdvancedBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            saleTxnManager = new SaleTxnManager();
            success = saleTxnManager.insertToManualTxnTable(cashAdvancedBean, systemAuditBean);

        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private boolean updateManualTxnTable(SaleTxnBean cashAdvancedBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            saleTxnManager = new SaleTxnManager();
            success = saleTxnManager.updateManualTxnTable(cashAdvancedBean, systemAuditBean);

        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private String generateTxnId() throws Exception {
        String txnId = null;
        try {

            saleTxnManager = new SaleTxnManager();
            txnId = saleTxnManager.generateTxnId();

            if (txnId == null) {
                txnId = "1";
            } else {
                txnId = Integer.toString(Integer.parseInt(txnId) + 1);

            }

            txnId = String.format("%020d", Integer.parseInt(txnId));

        } catch (Exception ex) {
            throw ex;
        }
        return txnId;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
