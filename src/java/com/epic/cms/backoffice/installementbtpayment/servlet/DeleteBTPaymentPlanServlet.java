/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.backoffice.installementbtpayment.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.installement.btpayment.bean.BTPaymentPlanBean;
import com.epic.cms.backoffice.installement.btpayment.businesslogic.BTPaymentPlanManager;
import com.epic.cms.backoffice.installementpayment.bean.PaymentPlanBean;
import com.epic.cms.backoffice.installementpayment.businesslogic.PaymentPlanManager;
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
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sajith_g
 */
public class DeleteBTPaymentPlanServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    private BTPaymentPlanManager paymentPlanManager;
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private BTPaymentPlanBean paymentPlanBean = null;
    private SystemAuditBean systemAuditBean = null;
    private String errorMessage = null;
    private List<String> userTaskList;
    String paymentplanCode = null;
    private String url = "/backoffice/installementpayment/btpaymentplanhome.jsp";
    private List<BTPaymentPlanBean> paymentPlanList;    
    private String oldValue;
    
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


            //set page code and task codes
            String pageCode = PageVarList.BTPAYMENTPLAN;
            String taskCode = TaskVarList.DELETE;

            //check whethre userrole have an access for this page and task
            if (!this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                //if invalid throw accessdenied exception
                throw new AccessDeniedException();
            }
            
            paymentplanCode = request.getParameter("paymentplancode");
            paymentPlanBean = new BTPaymentPlanBean();
            paymentPlanBean.setPaymentplancode(paymentplanCode);
            
            this.getAllPaymentPlans();
            
            for(BTPaymentPlanBean bean: paymentPlanList){
                if(bean.getPaymentplancode().equals(paymentplanCode)){
                    oldValue = bean.getPaymentplancode() + "|" + bean.getDescription() + "|"+ bean.getDuration()+ "|"+ bean.getInterestrate()+ "|" + bean.getStatusCode() + "|" + bean.getLastUpdatedUser();
                    break;
                }
            }

            this.setAudittraceValue(request);
            boolean delResult = deletePaymentPlan(paymentPlanBean, systemAuditBean);

            if (delResult) {
                request.setAttribute("successMsg", paymentplanCode + " " + MessageVarList.BTPAYMENTPLAN_SUCCESS_DELETE);
                sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                rd = getServletContext().getRequestDispatcher("/LoadBTPaymentPlanMgtServlet");
            }

        } //catch session exception //catch session exception //catch session exception //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);


        } catch (SQLException ex) {
            request.setAttribute("operationtype", "add");
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", paymentplanCode + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);


        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            rd = request.getRequestDispatcher(url);
            request.setAttribute("errorMsg", MessageVarList.ERROR_DELETE_BTPAYMENTPLAN);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * 
     * @param paymentPlan
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean deletePaymentPlan(BTPaymentPlanBean paymentPlan, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            paymentPlanManager = new BTPaymentPlanManager();
            success = paymentPlanManager.deletePaymentPlan(paymentPlan, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(paymentPlanBean.getPaymentplancode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Delete balance transfer installement payment plan privilage. privilage code :" + paymentPlanBean.getPaymentplancode()+ " by :" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.INSTALLMENTPLAN);
            systemAuditBean.setPageCode(PageVarList.BTPAYMENTPLAN);
            systemAuditBean.setTaskCode(TaskVarList.DELETE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            System.out.println(oldValue);
            systemAuditBean.setOldValue(oldValue);
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

    private void getAllPaymentPlans() throws Exception {
        try {
            paymentPlanManager = new BTPaymentPlanManager();
            paymentPlanList = paymentPlanManager.getAllPaymentPlans();
        } catch (Exception ex) {
            throw ex;
        }
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
