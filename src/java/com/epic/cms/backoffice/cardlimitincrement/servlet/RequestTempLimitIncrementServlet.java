/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.cardlimitincrement.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.cardlimitincrement.bean.CommonCardParameterBean;
import com.epic.cms.backoffice.cardlimitincrement.bean.TempLimitIncrementBean;
import com.epic.cms.backoffice.cardlimitincrement.businesslogic.TempLimitIncrementManager;
import com.epic.cms.callcenter.callcentersearch.bean.CallHistoryBean;
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
import java.sql.Date;
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
public class RequestTempLimitIncrementServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private HttpSession sessionObj = null;
    private String errorMessage = null;
    private TempLimitIncrementBean tempBean = null;
    private CommonCardParameterBean commonBean = null;
    private TempLimitIncrementManager tempIncrementManager = null;
    private String url = "/backoffice/cardlimitincrement/temporarylimitincrementhome.jsp";
    private String popUpUrl = "/backoffice/cardlimitincrement/templimitincrementdualauthinvoke.jsp";
    private CallHistoryBean callhistoryBean;
    private boolean success = false;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
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
                String pageCode = PageVarList.TEMP_CARD_INCREMANT;
                String taskCode = TaskVarList.LIMITINCREMENT;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    try {

                        if (setUserInputToBean(request)) {

                            if (tempBean.getCreditOrCash().equals("CREDIT")) {

                                request.setAttribute("selectedtab", "0");

                            } else if (tempBean.getCreditOrCash().equals("CASH")) {

                                request.setAttribute("selectedtab", "1");
                            }

                            if (validateUserInput(tempBean)) {
                                this.setAudittraceValue(request);
                                this.setCallHistoryRecord(request);

                                if (!(tempBean.getStartDate().after(this.getDbDate()))) {

                                    tempBean.setStatus(StatusVarList.ACTIVE_STATUS);
                                    success = this.tempLimitIncrementInsert(tempBean, systemAuditBean, callhistoryBean);
                                }
                                if (tempBean.getStartDate().after(this.getDbDate())) {

                                    tempBean.setStatus(StatusVarList.INITIAL_STATUS);
                                    success = tempLimitIncrementInsert(tempBean, systemAuditBean, callhistoryBean);
                                }

                                sessionVarlist.setTempBean(tempBean);
                                request.setAttribute("tempBean", tempBean);

                                if (success) {
                                    if (tempBean.getCreditOrCash().equals("CREDIT")) {

                                        request.setAttribute("successMsg", MessageVarList.CREDIT_LIMIT_INCREMENT_REQUEST_SUCCESS);
                                    }
                                    if (tempBean.getCreditOrCash().equals("CASH")) {

                                        request.setAttribute("successMsg", MessageVarList.CASH_LIMIT_INCREMENT_REQUEST_SUCCESS);
                                    }

//                                    rd = getServletContext().getRequestDispatcher("/ViewCustomerMgtServlet?section=" + section);
                                    rd = getServletContext().getRequestDispatcher("/ViewCustomerMgtServlet?section=CCCARD");
                                }

//                                rd = getServletContext().getRequestDispatcher(popUpUrl);
                            } else {

                                request.setAttribute("tempBean", tempBean);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }
                        } else {

                            request.setAttribute("tempBean", tempBean);
                            if (tempBean.getCreditOrCash().equals("CREDIT")) {

                                request.setAttribute("selectedtab", "0");

                            } else if (tempBean.getCreditOrCash().equals("CASH")) {

                                request.setAttribute("selectedtab", "1");
                            }
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }

                    } catch (Exception e) {

                        request.setAttribute("tempBean", tempBean);
                        if (tempBean.getCreditOrCash().equals("CREDIT")) {

                            request.setAttribute("selectedtab", "0");

                        } else if (tempBean.getCreditOrCash().equals("CASH")) {

                            request.setAttribute("selectedtab", "1");
                        }
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

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = true;
        try {

            tempBean = new TempLimitIncrementBean();

            tempBean.setCardNumber(request.getParameter("cardNumber").trim());
            tempBean.setCreditLimit(request.getParameter("creditLimit").trim());
            tempBean.setCashLimit(request.getParameter("cashLimit").trim());
            tempBean.setAvlCreditLimit(request.getParameter("avlCreditLimit").trim());
            tempBean.setAvlCashLimit(request.getParameter("avlCashLimit").trim());
            tempBean.setStatusDes(request.getParameter("statusDes").trim());

            tempBean.setOnlineCreditLimit(request.getParameter("onlineCreditLimit").trim());
            tempBean.setOnlineCashLimit(request.getParameter("onlineCashLimit").trim());
            tempBean.setOnlineAvlCreditLimit(request.getParameter("onlineAvlCreditLimit").trim());
            tempBean.setOnlineAvlCashLimit(request.getParameter("onlineAvlCashLimit").trim());

            tempBean.setIncordec(request.getParameter("incordec"));
            tempBean.setCreditOrCash(request.getParameter("type").trim());
            tempBean.setAmount(request.getParameter("amount").trim());
            tempBean.setRate(request.getParameter("rate").trim());
            if (!request.getParameter("fromdate1").contentEquals("")) {
                tempBean.setStartDate(Date.valueOf(request.getParameter("fromdate1")));
            }
            if (!request.getParameter("fromdate2").contentEquals("")) {
                tempBean.setEndDate(Date.valueOf(request.getParameter("fromdate2")));
            }
            tempBean.setRemarks(request.getParameter("remarks").trim());

            tempBean.setLastUpdatedUser(sessionUser.getUserName());

        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    /**
     *
     * @param txnType
     * @return
     * @throws Exception
     */
    public boolean validateUserInput(TempLimitIncrementBean tempBean) throws Exception {
        boolean isValidate = true;

        //validate user Role code
        try {
            if (tempBean.getIncordec() == null || String.valueOf(tempBean.getIncordec()).contentEquals("") || String.valueOf(tempBean.getIncordec()).length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.INCORDEC_NULL;

            } else if ((tempBean.getAmount().contentEquals("") || tempBean.getAmount().length() <= 0) && (tempBean.getRate().contentEquals("") || tempBean.getRate().length() <= 0)) {
                isValidate = false;

                errorMessage = MessageVarList.AMOUNT_RATE_NULL;

            } else if (!(tempBean.getAmount().contentEquals("") || tempBean.getAmount().length() <= 0) && !(tempBean.getRate().contentEquals("") || tempBean.getRate().length() <= 0)) {
                isValidate = false;

                errorMessage = MessageVarList.AMOUNT_RATE_BOTH_ENTERED;

            } else if (!UserInputValidator.isNumeric(tempBean.getAmount()) && !tempBean.getAmount().contentEquals("")) {
                isValidate = false;

                errorMessage = MessageVarList.AMOUNT_INVALID;

            } else if ((!UserInputValidator.isNumeric(tempBean.getRate()) || Integer.parseInt(tempBean.getRate()) > 100) && (!tempBean.getRate().contentEquals(""))) {
                isValidate = false;

                errorMessage = MessageVarList.RATE_INVALID;

            } else if (tempBean.getStartDate() == null || String.valueOf(tempBean.getStartDate()).contentEquals("") || String.valueOf(tempBean.getStartDate()).length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.START_DATE_NULL;

            } else if (tempBean.getStartDate().before(this.getDbDate())) {
                isValidate = false;

                errorMessage = MessageVarList.START_DATE_INVALID;

            } else if (tempBean.getEndDate() == null || String.valueOf(tempBean.getEndDate()).contentEquals("") || String.valueOf(tempBean.getEndDate()).length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.END_DATE_NULL;

            } else if (tempBean.getEndDate().before(this.getDbDate())) {
                isValidate = false;

                errorMessage = MessageVarList.END_DATE_INVALID;

            } else if (tempBean.getStartDate().after(tempBean.getEndDate())) {
                isValidate = false;

                errorMessage = MessageVarList.START_DATE_AFTER_END_DATE;
            } else if (!UserInputValidator.isDescription(tempBean.getRemarks())) {
                isValidate = false;

                errorMessage = MessageVarList.REMARK_INVALID;

            } else if (!incrementValidater()) {
                isValidate = false;
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
            String uniqueId = tempBean.getCardNumber();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Requested " + tempBean.getCardNumber() + tempBean.getCreditOrCash() + "increment by" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CALL_CENTER_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CALL_CENTER_CARD);
            systemAuditBean.setPageCode(PageVarList.TEMP_CARD_INCREMANT);
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

    private void setCallHistoryRecord(HttpServletRequest request) throws Exception {

        try {
            callhistoryBean = new CallHistoryBean();

            callhistoryBean.setCallLogId(sessionVarlist.getCallLogId());
            callhistoryBean.setOperation(TaskVarList.APPROVE);//task code
            callhistoryBean.setRemarks("");
            callhistoryBean.setCardNo(sessionVarlist.getCardNumber());
            callhistoryBean.setApplicationId(sessionVarlist.getApplicationId());
            callhistoryBean.setCustomerId(sessionVarlist.getCustomerId());
            callhistoryBean.setAccountNo(sessionVarlist.getAccountId());
            callhistoryBean.setPageCode(PageVarList.TEMP_CARD_INCREMANT);
            callhistoryBean.setOldValue("");
            callhistoryBean.setNewValue("");
            callhistoryBean.setTid("");
            callhistoryBean.setMid("");

            callhistoryBean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    private Date getDbDate() throws Exception {
        Date currentDate = null;

        try {
            tempIncrementManager = new TempLimitIncrementManager();
            currentDate = tempIncrementManager.getDbDate();

        } catch (Exception ex) {
            throw ex;
        }
        return currentDate;
    }

    private void getCommonParameters() throws Exception {

        try {
            tempIncrementManager = new TempLimitIncrementManager();
            commonBean = new CommonCardParameterBean();
            commonBean = tempIncrementManager.getCommonParameters();
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

    private boolean incrementValidater() throws Exception {
        boolean validIncrement = true;
        try {

            double crdLimit = Double.parseDouble(tempBean.getCreditLimit());
            double cashLimit = Double.parseDouble(tempBean.getCashLimit());
            double onlineAvlCreditLimit = Double.parseDouble(tempBean.getOnlineAvlCreditLimit());
            double onlineAvlCashLimit = Double.parseDouble(tempBean.getOnlineAvlCashLimit());

            double incrementedAmount = 0;
            double amount = 0;
            double rate = 0;

            this.getCommonParameters();

            if (tempBean.getCreditOrCash().equals("CREDIT")) {

                if (!tempBean.getAmount().contentEquals("")) {

                    amount = Double.parseDouble(tempBean.getAmount());
                    incrementedAmount = amount;
                } else if (!tempBean.getRate().contentEquals("")) {

                    rate = Double.parseDouble(tempBean.getRate());
                    incrementedAmount = crdLimit * rate / 100;
                }

                if (tempBean.getIncordec().equals("INC")) {

                    if (incrementedAmount > Double.parseDouble(commonBean.getMaxTempCreditLimitAmount())) {

                        validIncrement = false;
                        errorMessage = MessageVarList.REQUESTED_CREDIT_LIMIT_HIGHERTHAN_COMMON_VALUE + " " + commonBean.getMaxTempCreditLimitAmount();
                    }

                }

                if (tempBean.getIncordec().equals("DEC")) {

                    if (incrementedAmount > onlineAvlCreditLimit) {
                        validIncrement = false;
                        errorMessage = MessageVarList.REQUESTED_CREDIT_LIMIT_DEC_INVALID + " " + onlineAvlCreditLimit;
                    }

                }

            }
            if (tempBean.getCreditOrCash().equals("CASH")) {

                if (!tempBean.getAmount().contentEquals("")) {

                    amount = Double.parseDouble(tempBean.getAmount());
                    incrementedAmount = amount;
                } else if (!tempBean.getRate().contentEquals("")) {

                    rate = Double.parseDouble(tempBean.getRate());
                    incrementedAmount = cashLimit * rate / 100;
                }

                if (tempBean.getIncordec().equals("INC")) {
                    if (incrementedAmount > Double.parseDouble(commonBean.getMaxTempCashLimitAmount())) {

                        validIncrement = false;
                        errorMessage = MessageVarList.REQUESTED_CASH_LIMIT_HIGHERTHAN_COMMON_VALUE + " " + commonBean.getMaxTempCashLimitAmount();

                    } else if (onlineAvlCreditLimit < (onlineAvlCashLimit + incrementedAmount)) {

                        double possibleIncrementAmount = onlineAvlCreditLimit - onlineAvlCashLimit;
                        validIncrement = false;
                        errorMessage = MessageVarList.TOTAL_CASH_LIMIT_HIGHERTHAN_CREDIT_LIMIT + " " + possibleIncrementAmount;
                    }
                }
                if (tempBean.getIncordec().equals("DEC")) {
                    if (incrementedAmount > onlineAvlCashLimit) {
                        validIncrement = false;
                        errorMessage = MessageVarList.REQUESTED_CASH_LIMIT_DEC_INVALID + " " + onlineAvlCashLimit;
                    }
                }

            }

        } catch (Exception ex) {
            throw ex;

        }
        return validIncrement;
    }

    private boolean tempLimitIncrementInsert(TempLimitIncrementBean tempBean, SystemAuditBean systemAuditBean, CallHistoryBean callhistoryBean) throws Exception {
        boolean sucessInsert = false;
        try {

            tempIncrementManager = new TempLimitIncrementManager();
            sucessInsert = tempIncrementManager.tempLimitIncrementInsert(tempBean, systemAuditBean, callhistoryBean);

        } catch (Exception ex) {
            throw ex;
        }
        return sucessInsert;
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
}
