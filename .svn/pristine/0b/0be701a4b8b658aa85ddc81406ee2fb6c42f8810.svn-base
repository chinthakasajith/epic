/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.installementpayment.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.installementpayment.bean.LoadOnCardPaymentPlanBean;
import com.epic.cms.backoffice.installementpayment.bean.LoanOnCardPayConfirmBean;
import com.epic.cms.backoffice.installementpayment.businesslogic.LoanOnCardPlanConfirmManager;
import com.epic.cms.backoffice.installementpayment.businesslogic.LoanOnCardPlanManager;
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
import epic.cms.util.TxnBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
public class SearchLoanOnCardPayRequestServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private RequestDispatcher rd;
    private String url = "/backoffice/installementpayment/approveloanoncardpay.jsp";
    private LoanOnCardPayConfirmBean bean;
    private List<LoanOnCardPayConfirmBean> requestList;
    private String errorMessage;
    private LoanOnCardPlanConfirmManager manager;
    private LoanOnCardPayConfirmBean permBean2;
    private SystemAuditBean systemAuditBean, systemAuditBean2;
    private LoanOnCardPlanManager loanOnCardPlanManager;
    private List<LoadOnCardPaymentPlanBean> paymentPlanList;
    private TxnBean txnBean;

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

            HttpSession sessionObj = request.getSession(false);
            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();
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

            if (request.getParameter("view").equals("search")) {

                if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.SEARCH)) {
                    throw new AccessDeniedException();
                }
                //////////////////////////////
                request.setAttribute("operationtype", "search");

                this.getAllActivePaymentPlan();
                request.setAttribute("paymentPlanList", paymentPlanList);

                this.setValuesToBean(request);

                if (this.validateUserInput(bean)) {

                    this.searchRequests();
                    sessionVarlist.setLoanoncardreqBeanList(requestList);
                    request.setAttribute("requestList", requestList);

                } else {
                    request.setAttribute("bean", bean);
                    request.setAttribute("errorMsg", errorMessage);
                }
                ///////////////////////////////
            } else if (request.getParameter("view").equals("view")) {
                if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.VIEW)) {
                    throw new AccessDeniedException();
                }

                String reqid = request.getParameter("reqid");

                for (LoanOnCardPayConfirmBean bean1 : sessionVarlist.getLoanoncardreqBeanList()) {

                    if (bean1.getRequestid().equals(reqid)) {
                        permBean2 = bean1;
                    }
                }
                request.setAttribute("requestList", sessionVarlist.getLoanoncardreqBeanList());

                request.setAttribute("operationtype", "view");
                sessionVarlist.setLocreqBean(permBean2);
                request.setAttribute("permBean2", permBean2);

            } else if (request.getParameter("view").equals("approve")) {

                if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.APPROVE)) {
                    throw new AccessDeniedException();
                }
                //////////////////////////////
                request.setAttribute("operationtype", "search");
                request.setAttribute("requestList", sessionVarlist.getLoanoncardreqBeanList());

                LoanOnCardPayConfirmBean confbean = sessionVarlist.getLocreqBean();

                confbean.setRemark(request.getParameter("remarks"));
                confbean.setLastUpdatedUser(sessionUser.getUserName());

                this.setAudittraceValue(request, confbean);
                boolean success = false;
                success = this.approveRequest(confbean, systemAuditBean);

                if (success) {
                    request.setAttribute("successMsg", MessageVarList.LOC_PAYMENT_APPROVED);
                    this.searchRequests();
                    sessionVarlist.setLoanoncardreqBeanList(requestList);
                    request.setAttribute("requestList", requestList);
                } else {
                    request.setAttribute("permBean2", confbean);
                    request.setAttribute("operationtype", "view");
                    request.setAttribute("errorMsg", MessageVarList.LOC_PAYMENT_FAILED);

                }

                ///////////////////////////////
            } else if (request.getParameter("view").equals("reject")) {

                if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.REJECT)) {
                    throw new AccessDeniedException();
                }
                //////////////////////////////
                request.setAttribute("operationtype", "search");
                request.setAttribute("requestList", sessionVarlist.getEasyreqBeanList());

                LoanOnCardPayConfirmBean confbean = sessionVarlist.getLocreqBean();

                if (request.getParameter("remarks").isEmpty()) {
                    request.setAttribute("errorMsg", "Reject remarks cannot be empty");
                    request.setAttribute("permBean2", confbean);
                    request.setAttribute("operationtype", "view");
                } else {
                    confbean.setRemark(request.getParameter("remarks"));
                    confbean.setLastUpdatedUser(sessionUser.getUserName());

                    this.setAudittraceValue2(request, confbean);
                    boolean success = false;
                    success = this.rejectRequest(confbean, systemAuditBean2);

                    if (success) {
                        request.setAttribute("successMsg", MessageVarList.LOC_PAYMENT_REJECTED);
                        this.searchRequests();
                        sessionVarlist.setLoanoncardreqBeanList(requestList);
                        request.setAttribute("requestList", requestList);
                    } else {
                        request.setAttribute("permBean2", confbean);
                        request.setAttribute("operationtype", "view");

                    }
                }

                ///////////////////////////////
            }

            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

            rd = request.getRequestDispatcher(url);
            ///////////////

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadLoanOnCardPayConfirmationServlet");
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

    private boolean isValidTaskByUser(List<String> userTaskList, String task) throws Exception {
        boolean isValidTask = false;
        try {
            for (String usertask : userTaskList) {
                if (task.equals(usertask)) {
                    isValidTask = true;
                }
            }
            return isValidTask;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void searchRequests() throws Exception {
        try {
            manager = new LoanOnCardPlanConfirmManager();
            requestList = manager.searchRequests(bean);
        } catch (Exception e) {
            throw e;
        }
    }

    private void getAllActivePaymentPlan() throws Exception {
        try {
            loanOnCardPlanManager = new LoanOnCardPlanManager();
            paymentPlanList = loanOnCardPlanManager.getAllActivePaymentPlans();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean approveRequest(LoanOnCardPayConfirmBean confbean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            manager = new LoanOnCardPlanConfirmManager();
            success = manager.approveRequest(confbean, systemAuditBean);
            return success;
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean rejectRequest(LoanOnCardPayConfirmBean confbean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            manager = new LoanOnCardPlanConfirmManager();
            success = manager.rejectRequest(confbean, systemAuditBean);
            return success;
        } catch (Exception e) {
            throw e;
        }
    }

    private void setValuesToBean(HttpServletRequest request) throws Exception {
        try {
            bean = new LoanOnCardPayConfirmBean();
            bean.setCardno(request.getParameter("cardno"));
            bean.setPaymentplan(request.getParameter("paymentplan"));
            bean.setLoanamount(request.getParameter("amount"));
            bean.setLoggeduser(sessionUser.getUserName());

            if (!request.getParameter("fromdate").equals("")) {
                bean.setFromdate(Date.valueOf(request.getParameter("fromdate")));
            }
            if (!request.getParameter("todate").equals("")) {
                bean.setTodate(Date.valueOf(request.getParameter("todate")));
            }

        } catch (Exception e) {
            throw e;
        }
    }

    private boolean validateUserInput(LoanOnCardPayConfirmBean bean) {

        boolean isValidate = true;

        try {
            if (bean.getCardno().length() > 0 && !UserInputValidator.isAlphaNumeric(bean.getCardno())) {
                isValidate = false;
                errorMessage = "Enter valid card number";
            } else if (bean.getLoanamount().length() > 0 && (!UserInputValidator.isNumeric(bean.getLoanamount())) && !UserInputValidator.isDecimalNumeric(bean.getLoanamount())) {
                isValidate = false;
                errorMessage = "Enter valid amount";
            }
        } catch (Exception e) {
            isValidate = false;
        }

        return isValidate;

    }

    private void setAudittraceValue(HttpServletRequest request, LoanOnCardPayConfirmBean confbean) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = confbean.getCardno();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Approved " + confbean.getCardno() + confbean.getLoanamount() + " installment payment" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.INSTALLMENTPLAN);
            systemAuditBean.setPageCode(PageVarList.APPROVE_LOANONCARD_PAY);
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

    private void setAudittraceValue2(HttpServletRequest request, LoanOnCardPayConfirmBean confbean) throws Exception {

        try {
            systemAuditBean2 = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = confbean.getCardno();

            systemAuditBean2.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean2.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean2.setUniqueId(uniqueId);
            //set description 
            systemAuditBean2.setDescription("Rejected " + confbean.getCardno() + confbean.getLoanamount() + " installment payment" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean2.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean2.setSectionCode(SectionVarList.INSTALLMENTPLAN);
            systemAuditBean2.setPageCode(PageVarList.APPROVE_LOANONCARD_PAY);
            systemAuditBean2.setTaskCode(TaskVarList.REJECT);
            systemAuditBean2.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean2.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean2.setFieldName("");
            //set old value of change if required
            systemAuditBean2.setOldValue("");
            //set new value of change if required
            systemAuditBean2.setNewValue("");

            systemAuditBean2.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

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
