/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.callcentersearch.servlet;

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
public class ProceedCallCenterVoidTxnServlet extends HttpServlet {
    
     private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private SaleTxnBean voidBean = null;
    private HashMap<String, String> terminalList = null;
    private String errorMessage = null;
    private SaleTxnManager saleTxnManager = null;
    private List<SaleTxnBean> voidList = null;
    private TxnBean txnBean = null;
    private String url = "/aquirecallcenter/acquirevoidtxnhome.jsp";

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
                    String pageCode = PageVarList.CALLCENTER_VOID_TRANSACTION;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                        //////////////////////////////////////////////
                        try {

                            setUserInputToBean(request);

                            //this.getActiveTerminalOfMerchant(voidBean.getMerchantID());
                            this.getManualTerminalTxnInfoToVoid(voidBean.getTerminalID(), voidBean.getMerchantID());

                            if (this.validateUserInput(voidBean)) {

                                this.getOriginalTxnData(voidBean);

                                APIConfig.initAPI(StatusVarList.HOST_IP, StatusVarList.NIT, StatusVarList.HOST_PORT, StatusVarList.TIMEOUT, StatusVarList.DEBUGLEVEL);
                                this.setDataToCommunicationBean(voidBean);

                                APIConfig.call(txnBean);

                                //voidBean = new SaleTxnBean();

                                voidBean.setRrn(txnBean.getRRN());
                                voidBean.setAuthCode(txnBean.getAUTHCODE());
                                voidBean.setResponceCode(txnBean.getRESPONSECODE());

                                if (txnBean.getRESPONSECODE().equals(StatusVarList.MANUAL_TXN_SUCCESS_STATUS)) {
                                    request.setAttribute("successMsg2", MessageVarList.TRANSACTION_SUCCESS);
                                    this.voidSelectedTransaction(voidBean, systemAuditBean);
                                } else {
                                    request.setAttribute("errorMsg2", MessageVarList.TRANSACTION_ERROR + this.getResponsemessage(txnBean.getRESPONSECODE()));
                                }

                                this.getManualTerminalTxnInfoToVoid(voidBean.getTerminalID(), voidBean.getMerchantID());

                                request.setAttribute("voidBean", voidBean);
                                //setAttribute("terminalList", terminalList);
                                request.setAttribute("voidList", voidList);
                                rd = getServletContext().getRequestDispatcher(url);

                            } else {

                                request.setAttribute("errorMsg", errorMessage);
                                request.setAttribute("voidBean", voidBean);
                                //request.setAttribute("terminalList", terminalList);
                                request.setAttribute("voidList", voidList);
                                rd = getServletContext().getRequestDispatcher(url);
                            }
                        } catch (ConnectException ex) {

                            request.setAttribute("errorMsg2", MessageVarList.COMMUNICATION_ERROR);
                            request.setAttribute("voidBean", voidBean);
                            //setAttribute("terminalList", terminalList);
                            request.setAttribute("voidList", voidList);
                            rd = getServletContext().getRequestDispatcher(url);


                        } catch (Exception ex) {
                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            request.setAttribute("voidBean", voidBean);
                           // request.setAttribute("terminalList", terminalList);
                            request.setAttribute("voidList", voidList);
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
            rd.forward(request, response);

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
            String uniqueId = voidBean.getRrn();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add: '" + voidBean.getRrn() + "' by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.MERCHANT_TERMINAL_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.MANUAL_TXN);
            systemAuditBean.setPageCode(PageVarList.CALLCENTER_VOID_TRANSACTION);
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

    private void setDataToCommunicationBean(SaleTxnBean voidBean) throws Exception {

        try {
            txnBean = new TxnBean();

            txnBean.setCARDNUMBER(voidBean.getCardNumber());
            txnBean.setAMOUNT(voidBean.getTxnAmount());
            txnBean.setTRACENO(TxnHelp.getTraceNo());
            txnBean.setINVOICENO(voidBean.getInvoiceNo());
            txnBean.setTID(voidBean.getTerminalID());
            txnBean.setMID(voidBean.getMerchantID());
            txnBean.setTXNTYPE(TxnTypeCode.TXN_VOID);
            txnBean.setTIME(voidBean.getTxnTime());
            txnBean.setDATE(voidBean.getTxnDate());

        } catch (Exception ex) {
            throw ex;
        }

    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = true;
        try {

            voidBean = new SaleTxnBean();

            voidBean.setTxnID(request.getParameter("txnId").trim());
            voidBean.setMerchantID(sessionVarlist.getMerchantId());
            voidBean.setTerminalID(this.getManualTerminal(sessionVarlist.getMerchantId()));

        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
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

    private List<SaleTxnBean> getManualTerminalTxnInfoToVoid(String terminalID, String merchantId) throws Exception {

        try {
            voidList = new ArrayList<SaleTxnBean>();
            saleTxnManager = new SaleTxnManager();

            voidList = saleTxnManager.getManualTerminalTxnInfoToVoid(terminalID, merchantId);

        } catch (Exception ex) {
            throw ex;
        }
        return voidList;

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

    public boolean validateUserInput(SaleTxnBean saleTxnBean) throws Exception {
        boolean isValidate = true;

        //////validate merchantID

        if (saleTxnBean.getMerchantID().contentEquals("") || saleTxnBean.getMerchantID().length() <= 0) {
            isValidate = false;

            errorMessage = MessageVarList.MERCHANT_LOCATION_ID_NULL;

        } else if (!UserInputValidator.isNumeric(saleTxnBean.getMerchantID()) || saleTxnBean.getMerchantID().length() != 15) {
            isValidate = false;

            errorMessage = MessageVarList.MERCHANT_LOCATION_ID_INVALID;

        } else if (saleTxnBean.getTerminalID().contentEquals("") || saleTxnBean.getTerminalID().length() <= 0) {
            isValidate = false;

            errorMessage = MessageVarList.TERMINAL_ID_NULL;

        }

        try {
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    private SaleTxnBean getOriginalTxnData(SaleTxnBean bean) throws Exception {

        try {
            voidBean = new SaleTxnBean();
            saleTxnManager = new SaleTxnManager();

            voidBean = saleTxnManager.getOriginalTxnData(bean);

        } catch (Exception ex) {
            throw ex;
        }
        return voidBean;
    }

    private boolean voidSelectedTransaction(SaleTxnBean voidBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;

        try {
            saleTxnManager = new SaleTxnManager();
            success = saleTxnManager.voidSelectedTransaction(voidBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }
    
    private String getManualTerminal(String merchantId) throws Exception {
        String tId = null;
        try {
            saleTxnManager = new SaleTxnManager();
            tId = saleTxnManager.getManualTerminal(merchantId);
            
        } catch (Exception ex) {
            throw ex;
        }
        return tId;
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
