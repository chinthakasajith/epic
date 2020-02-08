/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.callcentersearch.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.TerminalDataCaptureBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.businesslogic.TerminalDataCaptureManager;
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
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
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
public class AddCallcenterTerminalServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private String errorMessage = null;
    private List<String> userTaskList;
    private boolean successInsert = false;
    private boolean successInsertToTxnType = false;
    private SystemAuditBean systemAuditBean = null;
    private TerminalDataCaptureManager allocationmanager = null;
    private String[] assignTxnTypeList = null;
    private String[] txnTypeList = null;
    private List<TypeMgtBean> typeList = null;
    private List<TypeMgtBean> assignedTypeList = null;
    private List<TypeMgtBean> notAssignTypeList = null;
    private List<MerchantCategoryBean> merchantCategoryList = null;
    private List<CurrencyBean> currencyList = null;
    private TerminalDataCaptureBean terminalBean = null;
    private TerminalDataCaptureBean newTerminalBean = null;
    String terminalId = null;
    String merchantId = null;
    private List<RiskProfileBean> riskProfileList = null;
    private String url = "/aquirecallcenter/acquireterminalallocation.jsp";
    private String backUrl = "/ViewMerchantMgtServlet?section=ACCTER&id=";

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
                String pageCode = PageVarList.CALLCENTER_TERMINAL_ALLOCATION;
                String taskCode = TaskVarList.ADD;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                    assignTxnTypeList = request.getParameterValues("assignTxnTypeList");
                    txnTypeList = request.getParameterValues("txnTypeList");

                    terminalId = request.getParameter("tId");
                    merchantId = request.getParameter("mId");

                    ///////get Transaction Assigned to Merchant

                    allocationmanager = new TerminalDataCaptureManager();

                    typeList = new ArrayList<TypeMgtBean>();
                    typeList = allocationmanager.getTxnList(merchantId);


                    try {
                        this.getTerminalDetails();
                        setUserInputToBean(request, txnTypeList, assignTxnTypeList);


                        ////////get Mcc List Assigned to Merchant

                        merchantCategoryList = new ArrayList<MerchantCategoryBean>();
                        merchantCategoryList = allocationmanager.getMccList(terminalBean.getMerchantID());

                        ///////get Currency Assigned to Merchant

                        currencyList = new ArrayList<CurrencyBean>();
                        currencyList = allocationmanager.getCurrencyList(terminalBean.getMerchantID());


                        ////// get terminal risk profile code

                        riskProfileList = new ArrayList<RiskProfileBean>();
                        riskProfileList = allocationmanager.getTerminalRiskProfile(merchantId);

                        if (validateUserInput(terminalBean)) {
                            request.setAttribute("operationtype", "ADD");

                            this.setAudittraceValue(request);

                            try {
                                if (isTerminalAllocated(terminalId)) {//terminal Id previously not allocated

                                    if (isMerchantActive(merchantId)) {

                                        if (insertTerminalData(terminalBean, systemAuditBean)) {
                                            successInsertToTxnType = insertToTxnType(terminalBean.getTerminalID(), assignTxnTypeList, merchantId);

                                            request.setAttribute("successMsg", terminalBean.getTerminalID() + " " + MessageVarList.TERMINAL_SUCSESS_ALLOCATED);
                                            rd = getServletContext().getRequestDispatcher(backUrl + terminalId + "&mid=" + merchantId);

                                        } else {
                                            request.setAttribute("errorMsg", MessageVarList.TERMINAL_ERROR_ALLOCATED);
                                            rd = getServletContext().getRequestDispatcher(backUrl + terminalId);
                                        }
                                    } else {
                                        request.setAttribute("errorMsg", MessageVarList.MERCHANT_NOTACTIVE);
                                        rd = getServletContext().getRequestDispatcher(backUrl + terminalId);
                                    }
                                } else {//prevoiusly allocated
                                    request.setAttribute("errorMsg", MessageVarList.TERMINAL_ALREADY_ALLOCATED);
                                    rd = getServletContext().getRequestDispatcher(backUrl + terminalId);
                                }

                                rd.forward(request, response);

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());


                                request.setAttribute("errorMsg", oraMessage);
                                request.setAttribute("merchantId", merchantId);
                                request.setAttribute("terminalId", terminalId);
                                request.setAttribute("terminalBean", terminalBean);
                                request.setAttribute("notAssignTypeList", notAssignTypeList);
                                request.setAttribute("assignedTypeList", assignedTypeList);
                                request.setAttribute(RequestVarList.MERCHANTCATEGORY_LIST, merchantCategoryList);
                                request.setAttribute(RequestVarList.CURRENCY_LIST, currencyList);
                                request.setAttribute(RequestVarList.TERMINAL_RISKPROFILE_LIST, riskProfileList);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);
                            }

                        } else {

                            request.setAttribute("terminalBean", terminalBean);
                            request.setAttribute("merchantId", merchantId);
                            request.setAttribute("terminalId", terminalId);
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute("notAssignTypeList", notAssignTypeList);
                            request.setAttribute("assignedTypeList", assignedTypeList);
                            request.setAttribute(RequestVarList.MERCHANTCATEGORY_LIST, merchantCategoryList);
                            request.setAttribute(RequestVarList.CURRENCY_LIST, currencyList);
                            request.setAttribute(RequestVarList.TERMINAL_RISKPROFILE_LIST, riskProfileList);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }

                    } catch (Exception e) {

                        request.setAttribute("terminalBean", terminalBean);
                        request.setAttribute("merchantId", merchantId);
                        request.setAttribute("terminalId", terminalId);
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        request.setAttribute("notAssignTypeList", notAssignTypeList);
                        request.setAttribute("assignedTypeList", assignedTypeList);
                        request.setAttribute(RequestVarList.MERCHANTCATEGORY_LIST, merchantCategoryList);
                        request.setAttribute(RequestVarList.CURRENCY_LIST, currencyList);
                        request.setAttribute(RequestVarList.TERMINAL_RISKPROFILE_LIST, riskProfileList);
                        rd = getServletContext().getRequestDispatcher(url);
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
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            out.close();
        }
    }

    /**
     * 
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request, String[] txnTypeList, String[] assignTxnTypeList) throws Exception {
        Boolean isSet = true;
        try {

            terminalBean = new TerminalDataCaptureBean();
            TypeMgtBean assignBean = new TypeMgtBean();
            TypeMgtBean notAssignBean = new TypeMgtBean();

            terminalBean.setTerminalID(terminalId);
            terminalBean.setMerchantID(merchantId);
            terminalBean.setMcc(request.getParameter("selectMcc"));
            terminalBean.setCurrency(request.getParameter("selectCurrency"));
            terminalBean.setRiskProfileCode(request.getParameter("selectRiskProfile"));
            terminalBean.setName(newTerminalBean.getName());
            terminalBean.setAlloStatus(StatusVarList.ALLOCATION_YES);

            terminalBean.setLastUpdateUser(sessionUser.getUserName());

            assignedTypeList = new ArrayList<TypeMgtBean>();
            notAssignTypeList = new ArrayList<TypeMgtBean>();


            int k = 0;
            if (assignTxnTypeList != null) {
                if (assignTxnTypeList.length != 0) {
                    while (assignTxnTypeList.length > k) {
                        assignBean = new TypeMgtBean();

                        assignBean.setTransactionTypeCode(assignTxnTypeList[k]);
                        for (int i = 0; i < typeList.size(); i++) {
                            if (assignBean.getTransactionTypeCode().equals(typeList.get(i).getTransactionTypeCode())) {
                                assignBean.setDescription(typeList.get(i).getDescription());
                                assignBean.setTransactionTypeCode(typeList.get(i).getTransactionTypeCode());
                            }
                        }

                        assignedTypeList.add(assignBean);
                        k++;
                    }
                }
            }
            if (txnTypeList != null) {
                int l = 0;
                while (txnTypeList.length > l) {
                    notAssignBean = new TypeMgtBean();

                    notAssignBean.setTransactionTypeCode(txnTypeList[l]);
                    for (int i = 0; i < typeList.size(); i++) {
                        if (notAssignBean.getTransactionTypeCode().equals(typeList.get(i).getTransactionTypeCode())) {
                            notAssignBean.setDescription(typeList.get(i).getDescription());
                            notAssignBean.setTransactionTypeCode(typeList.get(i).getTransactionTypeCode());
                        }
                    }
                    notAssignTypeList.add(notAssignBean);
                    l++;
                }
            }

        } catch (Exception e) {
            isSet = false;
            throw e;
        }
        return isSet;
    }

    /**
     * Validate the New User Entry
     * @param txnTypeBean
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(TerminalDataCaptureBean terminalBean) throws Exception {
        boolean isValidate = true;

        //////validate user inputs

        try {

            if (terminalBean.getMcc().contentEquals("") || terminalBean.getMcc().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MCC_NULL;

            } else if (terminalBean.getCurrency().contentEquals("") || terminalBean.getCurrency().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.CURRENCY_NULL;

            } else if (terminalBean.getRiskProfileCode().contentEquals("") || terminalBean.getRiskProfileCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.RISK_PROFILE_NULL;
            }
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public boolean insertToTxnType(String terminalId, String[] assignTxnTypeList, String merchantId) throws Exception {
        boolean success = false;
        try {
            allocationmanager = new TerminalDataCaptureManager();
            success = allocationmanager.insertToTxnType(terminalId, assignTxnTypeList, merchantId);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    public boolean insertTerminalData(TerminalDataCaptureBean terminalBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            /// call insertTxntypeDetails method in TxnTypeMgtManager class

            allocationmanager = new TerminalDataCaptureManager();
            success = allocationmanager.insertTerminalData(terminalBean, systemAuditBean);

        } catch (SQLException ex) {
            throw ex;
        }
        return success;
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
            String uniqueId = terminalBean.getTerminalID();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Terminal. Terminal ID : " + terminalBean.getTerminalID() + "; by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ACQUIRE_CALL_CENTER_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.ACQ_CALL_CENTER_TERMINAL);
            systemAuditBean.setPageCode(PageVarList.CALLCENTER_TERMINAL_ALLOCATION);
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

    public Boolean isTerminalAllocated(String terminalId) throws Exception {
        allocationmanager = new TerminalDataCaptureManager();
        Boolean status = false;

        try {
            status = allocationmanager.isTerminalAllocated(terminalId);
        } catch (Exception e) {
            throw e;
        }

        return status;
    }

    public Boolean isTerminalActive(String terminalId) throws Exception {
        allocationmanager = new TerminalDataCaptureManager();
        Boolean status = false;

        try {
            status = allocationmanager.isTerminalActive(terminalId);
        } catch (Exception e) {
            throw e;
        }

        return status;
    }

    public Boolean isMerchantActive(String merchantId) throws Exception {
        allocationmanager = new TerminalDataCaptureManager();
        Boolean status = false;

        try {
            status = allocationmanager.isMerchantActive(merchantId);
        } catch (Exception e) {
            throw e;
        }

        return status;
    }

    public void getTerminalDetails() throws Exception {

        newTerminalBean = new TerminalDataCaptureBean();
        allocationmanager = new TerminalDataCaptureManager();

        newTerminalBean = allocationmanager.viewOneTerminalData(terminalId);

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
