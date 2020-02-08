/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.MerchantPaymentCycleBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.MerchantPaymentCycleManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.datetime.SystemDateTime;
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
import com.epic.cms.system.util.variable.TaskVarList;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
@WebServlet(name = "UpdateMerchantPaymentCycleServlet", urlPatterns = {"/UpdateMerchantPaymentCycleServlet"})
public class UpdateMerchantPaymentCycleServlet extends HttpServlet {

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
            
            HttpSession sessionObj = request.getSession(false);
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
            
            try {
                //set page code and task codes
                String pageCode = PageVarList.MERCHANT_PAYMENT_CYCLE;
                String taskCode = TaskVarList.UPDATE;


                //check whether user_role have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    
                    
                    try {
                        if (setUserInputToBean(request)) {

                            //validate user input
                            if (validateUserInput(paymentBean)) {
                                request.setAttribute("operationtype", "add");

                                this.getNewValueSet();                                
                                //set values to the audit trace
                                this.setAudittraceValue(request);
                                successInsert = updatePaymentCycle(paymentBean, systemAuditBean);
                                request.setAttribute("successMsg", " " + MessageVarList.PAYMENT_CYCLE_MGT_PAYMENT_CODE_SUCCESS_UPDATE + "'" + paymentBean.getPaymentCycleCode() + "' Payment Cycle Code ");
                                rd = getServletContext().getRequestDispatcher(url);
                            } else {
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("paymentBean", paymentBean);
                                //request.setAttribute("mrchntCatList", mrchntCatList);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }
                        } else {
                            request.setAttribute("operationtype", "add");
                            //request.setAttribute("mrchntCatList", mrchntCatList);
                            request.setAttribute("paymentBean", paymentBean);
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }
                        this.getAllPaymentData();
                        request.setAttribute("paymentList", paymentList);
                        rd.forward(request, response);
                    } catch (Exception e) {
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("paymentBean", paymentBean);
                        request.setAttribute("paymentList", paymentList);
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        this.getAllPaymentData();
                        request.setAttribute("paymentList", paymentList);
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
            out.close();
        }
    }

//**************************************************** //
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
            String uniqueId = request.getParameter(paymentBean.getPaymentCycleCode());
            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Merchant Payment Cycle Payment Cycle Code :" + paymentBean.getPaymentCycleCode() + ";  by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.MERCHANT_PAYMENT_CYCLE);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(paymentBean.getOldValue());
            //set new value of change if required
            systemAuditBean.setNewValue(paymentBean.getNewValue());
            
            
            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());
            
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private void getAllPaymentData() throws Exception {
        try {
            
            cycleMgr = new MerchantPaymentCycleManager();
            paymentList = cycleMgr.getAllPaymentData();
            
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            
            paymentBean = new MerchantPaymentCycleBean();
            
            paymentBean.setPaymentCycleCode(request.getParameter("paymentCycleCode"));
            paymentBean.setPaymentOption(request.getParameter("option"));
            if (!paymentBean.getPaymentOption().equals("1")) {
                paymentBean.setPaymentDate(request.getParameter("date"));
            } else {
                paymentBean.setPaymentDate(null);
            }
            paymentBean.setPaymentDescription(request.getParameter("description"));
            paymentBean.setHolidayAction(request.getParameter("holidayAct"));
            paymentBean.setStatus(request.getParameter("status"));
            paymentBean.setOldValue(request.getParameter("oldValue"));
            paymentBean.setLastUpdatedUser(sessionUser.getUserName());
            paymentBean.setLastUpdatedTime(SystemDateTime.getSystemDataAndTime());
            paymentBean.setCreatedTime(SystemDateTime.getSystemDataAndTime());
            
        } catch (Exception e) {
            status = false;
            throw e;
            
        }
        
        return status;
    }
    
    public boolean validateUserInput(MerchantPaymentCycleBean payment) throws Exception {
        
        boolean isValidate = true;
        try {
            if (payment.getPaymentCycleCode().contentEquals("") || payment.getPaymentCycleCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CYCLE_MGT_PAYMENTCODE_NULL;
            } else if (!UserInputValidator.isAlphaNumeric(payment.getPaymentCycleCode())) {
                isValidate = false;
                errorMessage = MessageVarList.CYCLE_MGT_PAYMENTCODE_INVALID;
            } else if (payment.getPaymentOption().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.PAYMENT_OPTION_NULL;
            } else if (!paymentBean.getPaymentOption().equals("1") && payment.getPaymentDate().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.PAYMENT_CYCLE_MGT_PAYMENTDATE_NULL;
            }
            if (payment.getPaymentDescription().contentEquals("") || payment.getPaymentDescription().length() <= 0 && isValidate) {
                isValidate = false;
                errorMessage = MessageVarList.BILL_CYCLE_MGT_BILLDESCRIPTION_NULL;
            } else if (payment.getHolidayAction() == null) {
                isValidate = false;
                errorMessage = MessageVarList.BILL_CYCLE_MGT_HOLIACT_NULL;
            } else if (payment.getStatus().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.STATUS_EMPTY;
            }
            
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }   
    
    public Boolean updatePaymentCycle(MerchantPaymentCycleBean payment, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            cycleMgr = new MerchantPaymentCycleManager();
            success = cycleMgr.updatePaymentCycle(payment, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }
public void getNewValueSet(){
    
    String newValueSet = paymentBean.getPaymentCycleCode() + " | " + paymentBean.getPaymentOption() + " | "
                + paymentBean.getPaymentDate() + " | " + paymentBean.getPaymentDescription() + " | " + " | "
                + " | " + paymentBean.getHolidayAction() + " | " + paymentBean.getStatus() + " | " + paymentBean.getLastUpdatedUser()
                + " | " + paymentBean.getCreatedTime() + " | " + paymentBean.getLastUpdatedTime(); 
    
    paymentBean.setNewValue(newValueSet);
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
