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
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
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
 * @author sajith_g
 */
public class AddBTPaymentPlanServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private BTPaymentPlanManager paymentPlanManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private BTPaymentPlanBean paymentPlanBean;
    private String errorMessage = null;
    private List<String> userTaskList;
    private List<BTPaymentPlanBean> paymentPlanList;
    private SystemAuditBean systemAuditBean = null;
    private String url = "/backoffice/installementpayment/btpaymentplanhome.jsp";
    private String newValue = "";
    
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
            String taskCode = TaskVarList.ADD;

            //check whethre userrole have an access for this page and task
            if (!this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }

            this.setUserInputToBean(request);
            this.getAllPaymentPlan();

            if (validateUserInput(paymentPlanBean)) {

                request.setAttribute("operationtype", "add");
                this.setAudittraceValue(request);
                insertPaymentPlan(paymentPlanBean, systemAuditBean);
                request.setAttribute("paymentPlanList", paymentPlanList);
                request.setAttribute("successMsg", paymentPlanBean.getPaymentplancode()+ " " + MessageVarList.PAYMENTPLAN_SUCCESS_ADD);
                rd = getServletContext().getRequestDispatcher("/LoadBTPaymentPlanMgtServlet");

            } else {
                request.setAttribute("operationtype", "add");
                request.setAttribute("paymentPlanBean", paymentPlanBean);
                request.setAttribute("paymentPlanList", paymentPlanList);
                request.setAttribute("errorMsg", errorMessage);
                rd = getServletContext().getRequestDispatcher(url);

            }
        } //catch session exception
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
            request.setAttribute("errorMsg", paymentPlanBean.getPaymentplancode() + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            rd = request.getRequestDispatcher(url);
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_PAYMENTPLAN);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     *
     * @param request
     */
    public void setUserInputToBean(HttpServletRequest request) {

        String paymentPlanCode = request.getParameter("paymentplancode").trim();
        String description = request.getParameter("paymentplandescription").trim();
        String duration = request.getParameter("duration");
        String interestrate = request.getParameter("interestrate");
        String statusCode = request.getParameter("statuscode");
        paymentPlanBean = new BTPaymentPlanBean();
        paymentPlanBean.setPaymentplancode(paymentPlanCode);
        paymentPlanBean.setDescription(description);
        paymentPlanBean.setDuration(duration);
        paymentPlanBean.setInterestrate(interestrate);
        paymentPlanBean.setStatusCode(statusCode);

        paymentPlanBean.setLastUpdatedUser(sessionUser.getUserName());
        paymentPlanBean.setCreatedTime(new Date());
        paymentPlanBean.setLastUpdatedTime(new Date());

        newValue = paymentPlanBean.getPaymentplancode() + "|" + paymentPlanBean.getDescription() + "|" + paymentPlanBean.getDuration() + "|" + paymentPlanBean.getInterestrate() + "|" + paymentPlanBean.getStatusCode() + "|" + paymentPlanBean.getLastUpdatedUser() + "|" + paymentPlanBean.getCreatedTime();

    }

    public boolean validateUserInput(BTPaymentPlanBean paymentPlanBean) throws Exception {
        boolean isValidate = true;

        try {

            if (paymentPlanBean.getPaymentplancode().contentEquals("") || paymentPlanBean.getPaymentplancode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BTPAYMENTPLAN_PAYMENTPLANCODE_NULL;
            } else if (!UserInputValidator.isNumeric(paymentPlanBean.getPaymentplancode())) {
                isValidate = false;
                errorMessage = MessageVarList.BTPAYMENTPLAN_PAYMENTPLANCODE_INVALID;

            } else if (paymentPlanBean.getDescription().contentEquals("") || paymentPlanBean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BTPAYMENTPLAN_DESCRIPTION_NULL;
            } else if (paymentPlanBean.getDuration().contentEquals("") || paymentPlanBean.getDuration().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BTPAYMENTPLAN_DURATION_NULL;
            } else if (!UserInputValidator.isNumeric(paymentPlanBean.getDuration())) {
                isValidate = false;
                errorMessage = MessageVarList.BTPAYMENTPLAN_DURATION_INVALID;
            } else if (paymentPlanBean.getInterestrate().contentEquals("") || paymentPlanBean.getInterestrate().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BTPAYMENTPLAN_INTERESTRATE_NULL;
            } else if (!UserInputValidator.isDecimal_Numeric(paymentPlanBean.getInterestrate())) {
                isValidate = false;
                errorMessage = MessageVarList.BTPAYMENTPLAN_INTERESTRATE_INVALID;
            } else if (paymentPlanBean.getStatusCode().contentEquals("") || paymentPlanBean.getStatusCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BTPAYMENTPLAN_STATUSCODE_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;

        }

        return isValidate;
    }

    /**
     *
     * @param paymentPlan
     * @param systemAuditBean
     * @return
     * @throws Exception
     */
    public boolean insertPaymentPlan(BTPaymentPlanBean paymentPlan, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            paymentPlanManager = new BTPaymentPlanManager();
            success = paymentPlanManager.insertPaymentPlan(paymentPlan, systemAuditBean);
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
            systemAuditBean.setDescription("Add balance transfer installment payment plan privilage. privilage code: " + paymentPlanBean.getPaymentplancode() + " by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.INSTALLMENTPLAN);
            systemAuditBean.setPageCode(PageVarList.BTPAYMENTPLAN);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue(newValue);

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

    private void getAllPaymentPlan() throws Exception {
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
