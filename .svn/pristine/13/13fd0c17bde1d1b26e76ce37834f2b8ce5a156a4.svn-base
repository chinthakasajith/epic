/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BillingCycleMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.BillingCycleMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
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
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * to delete one billing cycle record
 * @author nisansala
 */
public class DeleteBillingCycleMgtServlet extends HttpServlet {
    //initializing variables

    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private SessionVarList sessionVarlist;
    private SystemUserManager systemUserManager;
    private SystemAuditBean systemAuditBean = null;
    //----------------------------------------------
    private String billingCycleCode;
    private BillingCycleMgtBean billBean;
    private BillingCycleMgtBean bean;
    private BillingCycleMgtManager billCycleMgr;
    private List<BillingCycleMgtBean> billingList;
    String url = "/administrator/controlpanel/systemconfigmgt/billingcyclemgt.jsp";
    private String oldValue;

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
        try {
            //call to existing session
            sessionObj = request.getSession(false);
            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                //check system user is in same session or not
                try {
                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        //if user is not in the same session.
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
                String pageCode = PageVarList.BILLING_CYCLE_MGT;
                String taskCode = TaskVarList.DELETE;

                //if user has access to delete a record 
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    request.setAttribute("operationtype", "add");

                    billingCycleCode = request.getParameter("id");
                    billBean = new BillingCycleMgtBean();
                    billBean.setBillCycleCode(billingCycleCode);

                    billCycleMgr = new BillingCycleMgtManager();
                    //retrieve the relevent record
                    bean = billCycleMgr.viewOneBillingCycle(billingCycleCode);

                    if (bean != null) {
                        oldValue = bean.getBillCycleCode() + "|" + bean.getBillDate() + "|"
                                + "" + bean.getBillDescription() + "|" + bean.getHolidayAction() + "|"
                                + bean.getNextbillingDate() + "|" + "" + bean.getBillStatus();
                    }

                    //set the values for the audit trace
                    this.setAudittraceValue(request);

                    billCycleMgr = new BillingCycleMgtManager();

                    try {
                        int count = -1;
                        boolean delResult = false;

                        count = billCycleMgr.accountCount(billingCycleCode);

                        if (count > 0) {
                            this.getAllBillingData();
                            request.setAttribute("billingList", billingList);
                            request.setAttribute("errorMsg", "Can't be deleted, active accounts are there with the billing cycle");
                            rd = getServletContext().getRequestDispatcher(url);
                        }else{
                            //call the delete merchant method
                            delResult = deleteBillingData(billBean, systemAuditBean);
                        }
                                               

                        //if the deletion is success, proceed
                        if (delResult) {
                            this.getAllBillingData();
                            request.setAttribute("billingList", billingList);
                            request.setAttribute("successMsg", " " + MessageVarList.BILL_CYCLE_MGT_BILLING_CODE_SUCCESS_DELETE + "Billing Cycle code " + billingCycleCode);
                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                            rd = getServletContext().getRequestDispatcher(url);
                        }
                    } catch (SQLException e) {

                        OracleMessage message = new OracleMessage();
                        String oraMessage = message.getMessege(e.getMessage());
                        request.setAttribute("errorMsg", oraMessage);
                        rd = getServletContext().getRequestDispatcher(url);
                    } catch (Exception ee) {

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
        }
    }

    /**
     * to check user has required access to the page
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

    /**
     * to set values to the audit trace
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(billBean.getBillCycleCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Delete Billing Cycle. Billing Cycle code: '" + billBean.getBillCycleCode() + "' by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.BILLING_CYCLE_MGT);
            systemAuditBean.setTaskCode(TaskVarList.DELETE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(oldValue);
            //set new value of change if required
            systemAuditBean.setNewValue("");
            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to get all billing data
     * @throws Exception 
     */
    private void getAllBillingData() throws Exception {

        try {
            billCycleMgr = new BillingCycleMgtManager();
            billingList = billCycleMgr.getAllBillingData();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to delete a billing cycle record
     * @param bill
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean deleteBillingData(BillingCycleMgtBean bill, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            billCycleMgr = new BillingCycleMgtManager();
            success = billCycleMgr.deleteBillingData(billingCycleCode, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
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
