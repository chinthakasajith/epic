/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.cardlimitincrement.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
import com.epic.cms.backoffice.cardlimitincrement.bean.TempLimitIncrementBean;
import com.epic.cms.backoffice.cardlimitincrement.businesslogic.TempLimitIncrementManager;
import com.epic.cms.callcenter.callcentersearch.bean.CallHistoryBean;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.security.CMSMd5;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
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
public class DualAuthApprovedTempLimitIncrementServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private HttpSession sessionObj = null;
    private boolean success = false;
    private String errorMessage = null;
    private TempLimitIncrementBean tempBean = null;
    private SystemUserBean CMSSysUser = null;
    private String section = "CCCARD";
    private TempLimitIncrementManager tempIncrementManager = null;
    private String url = "/backoffice/cardlimitincrement/temporarylimitincrementhome.jsp";
    private String popUpUrl = "/backoffice/cardlimitincrement/templimitincrementdualauthinvoke.jsp";
    private FeeBean feeBean = null;
    private CallHistoryBean callhistoryBean;

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
                String pageCode = PageVarList.TEMP_CARD_INCREMANT;
                String taskCode = TaskVarList.LIMITINCREMENT;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    try {

                        if (setUserInputToBean(request)) {
                            tempBean = new TempLimitIncrementBean();
                            tempBean = sessionVarlist.getTempBean();

                            if (tempBean.getCreditOrCash().equals("CREDIT")) {

                                request.setAttribute("selectedtab", "0");

                            } else if (tempBean.getCreditOrCash().equals("CASH")) {

                                request.setAttribute("selectedtab", "1");
                            }
                            if (checkDualAuthorisation(CMSSysUser)) {
                                this.setAudittraceValue(request);
                                this.setCallHistoryRecord(request);// no need for backofiice
                                this.setCardFeeCount(request);//when approved, 


                                if (!(tempBean.getStartDate().after(this.getDbDate()))) {

                                    tempBean.setStatus(StatusVarList.ACTIVE_STATUS);
                                    tempBean = this.tempLimitUpdate(tempBean, systemAuditBean, feeBean, callhistoryBean);
                                    if (tempBean.isFlag()) {
                                        success = this.tempLimitIncrementInsert(tempBean, systemAuditBean, callhistoryBean);//no need for backoffice
                                    }

                                }
                                if (tempBean.getStartDate().after(this.getDbDate())) {

                                    tempBean.setStatus(StatusVarList.INITIAL_STATUS);
                                    tempIncrementManager = new TempLimitIncrementManager();
                                    tempBean = tempIncrementManager.incrementCreater(tempBean);

                                    if (tempBean.isFlag()) {
                                        success = tempLimitIncrementInsert(tempBean, systemAuditBean, callhistoryBean);
                                    }

                                }
                                if (success && tempBean.isFlag() && !(tempBean.getStartDate().after(this.getDbDate()))) {

                                    if (tempBean.getCreditOrCash().equals("CREDIT")) {

                                        request.setAttribute("successMsg", MessageVarList.CREDIT_LIMIT_INCREMENT_SUCCESS);
                                    }
                                    if (tempBean.getCreditOrCash().equals("CASH")) {

                                        request.setAttribute("successMsg", MessageVarList.CASH_LIMIT_INCREMENT_SUCCESS);
                                    }


                                    rd = getServletContext().getRequestDispatcher("/ViewCustomerMgtServlet?section=" + section);

                                } else if (success && tempBean.isFlag() && (tempBean.getStartDate().after(this.getDbDate()))) {

                                    if (tempBean.getCreditOrCash().equals("CREDIT")) {

                                        request.setAttribute("successMsg", MessageVarList.CREDIT_LIMIT_INCREMENT_REQUEST_SUCCESS);
                                    }
                                    if (tempBean.getCreditOrCash().equals("CASH")) {

                                        request.setAttribute("successMsg", MessageVarList.CASH_LIMIT_INCREMENT_REQUEST_SUCCESS);
                                    }

                                    rd = getServletContext().getRequestDispatcher("/ViewCustomerMgtServlet?section=" + section);

                                } else {

                                    request.setAttribute("tempBean", tempBean);
                                    request.setAttribute("errorMsg", tempBean.getErrorMsg());
                                    rd = getServletContext().getRequestDispatcher(url);

                                }

                            } else {

                                request.setAttribute("tempBean", tempBean);
                                request.setAttribute("errorMsg", MessageVarList.DUAL_AUTH_FAIL);
                                rd = getServletContext().getRequestDispatcher(popUpUrl);
                            }

                        } else {

                            request.setAttribute("tempBean", tempBean);
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute("selectedtab", "0");
                            rd = getServletContext().getRequestDispatcher(url);
                        }

                    } catch (SQLException ex) {

                        OracleMessage message = new OracleMessage();
                        String oraMessage = message.getMessege(ex.getMessage());
                        request.setAttribute("tempBean", tempBean);
                        if (tempBean.getCreditOrCash().equals("CASH")) {

                            request.setAttribute("selectedtab", "1");

                        } else {

                            request.setAttribute("selectedtab", "0");
                        }
                        request.setAttribute("errorMsg", oraMessage);
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
        Boolean isSuccess = true;
        try {

            CMSSysUser = new SystemUserBean();

            CMSSysUser.setUserName(request.getParameter("userName"));
            CMSSysUser.setPassword(CMSMd5.cmsMd5(request.getParameter("password")));


        } catch (Exception e) {
            isSuccess = false;
            throw e;

        }

        return isSuccess;
    }

    public boolean checkDualAuthorisation(SystemUserBean newCMSSysUser) throws Exception {
        boolean dualAuthSuccess = false;
        try {

            CMSSysUser = new SystemUserBean();
            tempIncrementManager = new TempLimitIncrementManager();
            CMSSysUser = tempIncrementManager.checkDualAuthorisation(newCMSSysUser);

            if (CMSSysUser.isFlag()) {

                dualAuthSuccess = true;
            }

        } catch (SQLException ex) {
            throw ex;
        }
        return dualAuthSuccess;
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

    private TempLimitIncrementBean tempLimitUpdate(TempLimitIncrementBean tempBean, SystemAuditBean systemAuditBean, FeeBean feeBean, CallHistoryBean callhistoryBean) throws Exception {

        try {

            tempIncrementManager = new TempLimitIncrementManager();
            tempBean = tempIncrementManager.tempLimitUpdate(tempBean, systemAuditBean, feeBean, callhistoryBean);

        } catch (Exception ex) {
            throw ex;
        }
        return tempBean;
    }

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
            systemAuditBean.setDescription("Approved " + tempBean.getCardNumber() + tempBean.getCreditOrCash() + "increment by" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.LIMIT_INCREMENT);
            systemAuditBean.setPageCode(PageVarList.TEMP_CARD_INCREMANT);
            systemAuditBean.setTaskCode(TaskVarList.APPROVE);
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

    private void setCardFeeCount(HttpServletRequest request) throws Exception {

        try {
            feeBean = new FeeBean();

            feeBean.setCardNo(tempBean.getCardNumber());
            feeBean.setFeeCode(StatusVarList.PIN_RESET_FEE);
            feeBean.setStatus(StatusVarList.ACTIVE_STATUS);
            feeBean.setLastUpdateUser(sessionVarlist.getCMSSessionUser().getUserName());

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
