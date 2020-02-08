/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.MerchantPaymentCycleBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.MerchantPaymentCycleManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
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
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
@WebServlet(name = "AddMerchantPaymentCycleServlet", urlPatterns = {"/AddMerchantPaymentCycleServlet"})
public class AddMerchantPaymentCycleServlet extends HttpServlet {

    //initializing variables
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private String errorMessage = null;
    private Boolean successInsert;
    //------------------------------------------------------   
    private MerchantPaymentCycleBean paymentBean;
    private MerchantPaymentCycleManager cycleMgr;
    private List<MerchantPaymentCycleBean> paymentList;
    String url = "/administrator/controlpanel/systemconfigmgt/merchantpaymentcyclehome.jsp";
    private String newValue;
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            try {
                HttpSession sessionObj = request.getSession(false);
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
////////*******************************************************//
            try {
                //set page code and task codes
                String pageCode = PageVarList.MERCHANT_PAYMENT_CYCLE;
                String taskCode = TaskVarList.ADD;


                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        //assign user input to the bean
                        setUserInputToBean(request);
                        //if user inputs are valid
                        if (validateUserInput(paymentBean)) {
                            request.setAttribute("operationtype", "add");
                            this.setAudittraceValue(request);
                            try {
                                successInsert = insertNewPaymentCycle(paymentBean);
                                if (successInsert) {
                                    request.setAttribute("successMsg", MessageVarList.SUCCESS_ADD + "'" + paymentBean.getPaymentCycleCode() + "' Payment Cycle Code ");
                                } else {
                                    throw new Exception();
                                }

                            } catch (SQLException e) {
                                //show the messages which has thrown when inserting
                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("errorMsg", oraMessage);


                            }
                            //retrive all merchant statement cycle records
                            this.getAllPaymentData();
                            request.setAttribute("paymentList", paymentList);
                            rd = getServletContext().getRequestDispatcher(url);

                        } else {
                            //if the user inputs are invalid go to the home page with an error message
                            this.getAllPaymentData();
                            request.setAttribute("paymentList", paymentList);
                            request.setAttribute("paymentBean", paymentBean);
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute("operationtype", "add");
                            rd = getServletContext().getRequestDispatcher(url);

                        }

                        rd.forward(request, response);

                    } catch (Exception e) {
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
///////******************************************************//  

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
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

    /**
     * validate user inputs
     * @param bill
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(MerchantPaymentCycleBean payment) throws Exception {

        boolean isValidate = true;
        try {
            if (payment.getPaymentCycleCode().contentEquals("") || payment.getPaymentCycleCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CYCLE_MGT_PAYMENTCODE_NULL;
            } else if (!UserInputValidator.isAlphaNumeric(payment.getPaymentCycleCode())) {
                isValidate = false;
                errorMessage = MessageVarList.CYCLE_MGT_PAYMENTCODE_INVALID;
            } else if (payment.getPaymentOption().contentEquals("") && (null != payment.getPaymentOption() ) ) {
                isValidate = false;
                errorMessage = MessageVarList.PAYMENT_OPTION_NULL;
            } else if (!paymentBean.getPaymentOption().equals("1") && payment.getPaymentDate().contentEquals("")) {
                    isValidate = false;
                    errorMessage = MessageVarList.PAYMENT_CYCLE_MGT_PAYMENTDATE_NULL;
            } else if ( ((payment.getPaymentDescription().contentEquals("") || payment.getPaymentDescription().length() <= 0 ) ||  payment.getPaymentDescription() == null)) {
                isValidate = false;
                errorMessage = MessageVarList.PAYMENT_CYCLE_MGT_PAYMENTDESCRIPTION_NULL;
            } else if (payment.getHolidayAction() == null) {
                isValidate = false;
                errorMessage = MessageVarList.PAYMENT_CYCLE_MGT_HOLIACT_NULL;
            } else if (payment.getStatus().contentEquals("") || payment.getStatus() == null ) {
                isValidate = false;
                errorMessage = MessageVarList.STATUS_EMPTY;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
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
            String uniqueId = request.getParameter(paymentBean.getPaymentCycleCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add: '" + paymentBean.getPaymentCycleCode() + "' Merchant Payment Cycle Code by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.MERCHANT_PAYMENT_CYCLE);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
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

    /**
     * to retrieve all billing cycle data
     * @throws Exception 
     */
    private void getAllPaymentData() throws Exception {
        try {

            cycleMgr = new MerchantPaymentCycleManager();
            paymentList = cycleMgr.getAllPaymentData();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to set user input to the bean
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            paymentBean = new MerchantPaymentCycleBean();

            paymentBean.setPaymentCycleCode(request.getParameter("paymentCycleCode"));
            paymentBean.setPaymentOption(request.getParameter("paymentOption"));
            if (!paymentBean.getPaymentOption().equals("1")) {
                paymentBean.setPaymentDate(request.getParameter("date"));
            } else {
                paymentBean.setPaymentDate(null);
            }
            paymentBean.setPaymentDescription(request.getParameter("PaymentDescription"));
            paymentBean.setHolidayAction(request.getParameter("holidayAct"));
            paymentBean.setStatus(request.getParameter("status"));
            paymentBean.setLastUpdatedUser(sessionUser.getUserName());
            
            newValue = paymentBean.getPaymentCycleCode() + "|" + paymentBean.getPaymentOption() + "|" + paymentBean.getPaymentDescription() + "|" + paymentBean.getHolidayAction() + "|" + 
                    paymentBean.getStatus() + "|" + paymentBean.getLastUpdatedUser();
            

        } catch (Exception e) {
            status = false;
            throw e;

        }

        return status;
    }

    /**
     * to insert one payment cycle record
     * @param bill
     * @return
     * @throws Exception 
     */
    public Boolean insertNewPaymentCycle(MerchantPaymentCycleBean payment) throws Exception {
        boolean success = false;
        try {
            cycleMgr = new MerchantPaymentCycleManager();
            success = cycleMgr.insertNewPaymentCycle(payment, systemAuditBean);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        }
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
