/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.prem.bulkcardmgt.bulkcardrequest.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.backoffice.installementpayment.bean.LoadOnCardPaymentPlanBean;
import com.epic.cms.backoffice.installementpayment.businesslogic.LoanOnCardPlanManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.LoanOnCardPaymentRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.businesslogic.LoanOnCardRequestManager;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * @author sajith_g
 */
public class AddLoanOnCardPlanRequestServlet extends HttpServlet {

    //initialize variables
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private SystemUserManager sysUserMgr;
    private SystemAuditBean systemAuditBean = null;
    String errorMessage = "";
    List<BulkCardRequestBean> reqList = null;
    List<CurrencyBean> currency = null;
    HashMap<String, String> cdProduct = null;
    private boolean successInsert;
    private SystemUserBean sysUsrBean = null;
    private LoanOnCardPaymentRequestBean loanOnCardPaymentRequestBean;
    private HashMap<String, String> cardDomain;
    private LoanOnCardRequestManager loanOnCardRequestManager = null;
    private LoanOnCardPlanManager paymentPlanManager=null;
    private List<LoadOnCardPaymentPlanBean> paymentPlanList;
    private String newValue=null;
    String url = "/callcenter/card/loanoncardrequest.jsp";
    
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
            HttpSession sessionObj = request.getSession(false);
            try {
                sysUserMgr = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();
                //check system user is in same session or not
                try {
                    if (!sysUserMgr.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
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
            try {
                //set page code and task codes
                String pageCode = PageVarList.LOADONCARD_REQUEST;
                String taskCode = TaskVarList.ADD;

                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        request.setAttribute("operationtype", "add");
                        //assign user input to the bean
                        setUserInputToBean(request);
                                           


                        request.setAttribute("tempBean", loanOnCardPaymentRequestBean);
                        this.getAllActiveLoanPaymentPlan();
                        
                        
                                                
                        //validate user inputs
                        if (validateUserInput(loanOnCardPaymentRequestBean)) {

                            this.setAudittraceValue(request);

                            try {
                                //insert the user given values to loan on card payment request table
                                successInsert = insertLoanOnCardReq(loanOnCardPaymentRequestBean);
                                if (successInsert) {
                                    request.setAttribute("successMsg", MessageVarList.LOANONCARD_REQ_SUCCESS_ADD);
                                    rd = getServletContext().getRequestDispatcher("/LoadLoanOnCardMgtServlet");
                                    rd.include(request, response);
                                }

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("errorMsg", oraMessage);
                                request.setAttribute("tempBean", loanOnCardPaymentRequestBean);
                                request.setAttribute("paymentPlanList", paymentPlanList);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);

                            }
                        } else {
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute("tempBean", loanOnCardPaymentRequestBean);
                            request.setAttribute("paymentPlanList", paymentPlanList);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);

                        }
                    } catch (Exception e) {
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("tempBean", loanOnCardPaymentRequestBean);
                        request.setAttribute("paymentPlanList", paymentPlanList);
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }
                } else {
                    throw new AccessDeniedException();
                }
                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);
            } catch (AccessDeniedException adex) {
                //if user_role doesn't have required access to the page throw Access Denied exception
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
        }
    }

    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {
        boolean isValidTaskAccess = false;

        try {
            sysUserMgr = new SystemUserManager();
            //get all tasks for userrole for this page
            userTaskList = sysUserMgr.getTasksByPageCodeAndUserRole(userrole, pagecode);

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
            //String uniqueId = request.getParameter(loanOnCardPaymentRequestBean.getTxnID());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            //systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Loan on card request Payment added by : "+loanOnCardPaymentRequestBean.getRequestedUser());
            systemAuditBean.setApplicationCode(ApplicationVarList.PRE_PERSONAL);
            systemAuditBean.setSectionCode(SectionVarList.PRE_PERSONAL_CD);
            systemAuditBean.setPageCode(PageVarList.LOADONCARD_REQUEST);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            //systemAuditBean.setRemarks(uniqueId);
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

    public boolean validateUserInput(LoanOnCardPaymentRequestBean loanOnCardPaymentRequestBean) throws Exception {
        boolean isValidate = true;
        try {
            if (loanOnCardPaymentRequestBean.getLoanAmount().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.LOAN_AMOUNT_NULL;
            } else if (!UserInputValidator.isCurrency(loanOnCardPaymentRequestBean.getLoanAmount())) {
                isValidate = false;
                errorMessage = MessageVarList.LOAN_AMOUNT_INVALID;
            } else if (loanOnCardPaymentRequestBean.getPaymentPlan().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.LOAN_PAYMENTPLAN_NULL;
            } else if (loanOnCardPaymentRequestBean.getInstallmentAmount().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.LOAN_INSTALLMENT_AMOUNT_NULL;
            } else if (!UserInputValidator.isDecimalNumeric(loanOnCardPaymentRequestBean.getInstallmentAmount())) {
                isValidate = false;
                errorMessage = MessageVarList.LOAN_INSTALLMENT_AMOUNT_INVALID;
            } 

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            loanOnCardPaymentRequestBean = new LoanOnCardPaymentRequestBean();

            loanOnCardPaymentRequestBean.setCardNumber(sessionVarlist.getCardNumber());
            loanOnCardPaymentRequestBean.setLoanAmount(request.getParameter("loanAmount"));
            loanOnCardPaymentRequestBean.setPaymentPlan(request.getParameter("paymentPlanCode"));
            loanOnCardPaymentRequestBean.setInstallmentAmount(request.getParameter("installmentAmount").trim());
            loanOnCardPaymentRequestBean.setTxnCurrencyCode(StatusVarList.DEFAULT_CURRENCYNUMCODE);
            loanOnCardPaymentRequestBean.setRemarks(request.getParameter("remarks"));
            loanOnCardPaymentRequestBean.setRequestedUser(sessionUser.getUserName());
            loanOnCardPaymentRequestBean.setLastUpdatedUser(sessionUser.getUserName());
            loanOnCardPaymentRequestBean.setStatus(StatusVarList.DA_REQUSET_INITIATE);
            newValue=loanOnCardPaymentRequestBean.getCardNumber()+"|"+loanOnCardPaymentRequestBean.getLoanAmount()+"|"+loanOnCardPaymentRequestBean.getPaymentPlan()+"|"+loanOnCardPaymentRequestBean.getInstallmentAmount()+"|"+loanOnCardPaymentRequestBean.getRequestedUser()+"|"+loanOnCardPaymentRequestBean.getStatus()+"|"+loanOnCardPaymentRequestBean.getLastUpdatedUser()+"|"+loanOnCardPaymentRequestBean.getTxnCurrencyCode()+"|"+loanOnCardPaymentRequestBean.getRemarks();

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    public Boolean insertLoanOnCardReq(LoanOnCardPaymentRequestBean bean) throws Exception {
        boolean success = false;
        try {
            loanOnCardRequestManager = new LoanOnCardRequestManager();
            success = loanOnCardRequestManager.insertLoanOnCardPayReq(bean, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }
    
    private void getAllActiveLoanPaymentPlan() throws Exception {
        try {
            paymentPlanManager = new LoanOnCardPlanManager();
            paymentPlanList = paymentPlanManager.getAllActivePaymentPlans();
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
