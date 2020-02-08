/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.payment.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.payment.bean.PaymentBatchBean;
import com.epic.cms.backoffice.payment.bean.PaymentBean;
import com.epic.cms.backoffice.payment.businesslogic.PaymentManager;
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
 *
 * @author badrika
 */
public class VoidPaymentServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private String url = "/backoffice/payment/paymentjsp.jsp";
    private String url2 = "/backoffice/payment/voidlogin.jsp";
    private RequestDispatcher rd;
    private SystemUserBean CMSSysUser;
    private SystemUserBean valideUser;
    private List<String> userTaskList;
    private SystemUserManager userManager;
    private SystemAuditBean systemAuditBean;
    private String param = null;
    private String txnid = null;
    private String batid = null;
    private List<PaymentBean> paymentList;//payBean2
    private PaymentBatchBean batchbean;
    private PaymentBean payBean2 = null;

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
                String pageCode = PageVarList.BO_PAYMENT;
                String taskCode = TaskVarList.UPDATE;

                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    param = request.getParameter("param");
                    txnid = request.getParameter("txnid");//txnid
                    batid = request.getParameter("batid");//batid

                    PaymentManager manager = new PaymentManager();
                    paymentList = manager.getAllPaymentDetails(batid);

                    for (PaymentBean payBean : paymentList) {
                        if (payBean.getTxnId().equals(txnid)) {
                            payBean2 = payBean;
                        }
                    }

                    if (param != null && param.equals("payvoid")) {

                        //view payment details before void

                        if (payBean2 != null) {

                            request.setAttribute("operationtype", "void");
                            request.setAttribute("pay", payBean2);
                            request.setAttribute("txnid", txnid);
                            request.setAttribute("batid", batid);
                            rd = getServletContext().getRequestDispatcher(url2);
                        }


                    } else if (param != null && param.equals("voidconf")) {

                        try {

                            String userName = request.getParameter("username");
                            String password = CMSMd5.cmsMd5(request.getParameter("password"));
                            CMSSysUser = new SystemUserBean();
                            CMSSysUser.setUserName(userName);
                            CMSSysUser.setPassword(password);

                            valideUser = validateUser(CMSSysUser);
                            String dualAuthUserRole = manager.dualAuthUserRole(sessionUser.getUserName());



                            if (valideUser != null) {

                                if (!valideUser.getStatusCode().equals("Active") || !valideUser.getUserRoleCode().equals(dualAuthUserRole) || valideUser.getUserName().equals(sessionUser.getUserName()) ) {
                                    request.setAttribute("operationtype", "void");
                                    request.setAttribute("pay", payBean2);
                                    request.setAttribute("txnid", txnid);
                                    request.setAttribute("batid", batid);
                                    request.setAttribute("errorMsg", "User invalid to do this operation ");

                                    rd = getServletContext().getRequestDispatcher(url2);
                                } else {
                                    this.setAudittraceValue(request);


                                    try {
                                        int successVoid = -1;
                                        successVoid = manager.voidPayment(systemAuditBean, txnid, userName);

                                        if (successVoid > 0) {

                                            request.setAttribute("successMsg", "Successfully void payment ");
                                            batchbean = manager.PaymentsOfBatch(batid);
                                            request.setAttribute("batchbean", batchbean);
                                            rd = getServletContext().getRequestDispatcher("/LoadPaymentAndBatchServlet?param=resume");
                                            // rd.include(request, response);
                                        }
                                        paymentList = manager.getAllPaymentDetails(batid);
                                        request.setAttribute("paymentList", paymentList);

                                    } catch (SQLException e) {

                                        OracleMessage message = new OracleMessage();
                                        String oraMessage = message.getMessege(e.getMessage());
                                        request.setAttribute("errorMsg", oraMessage);
                                        rd = getServletContext().getRequestDispatcher(url);
                                        // rd.forward(request, response);
                                    }

                                }


                            } else {
                                request.setAttribute("operationtype", "void");
                                request.setAttribute("pay", payBean2);
                                request.setAttribute("txnid", txnid);
                                request.setAttribute("batid", batid);
                                request.setAttribute("errorMsg", "User name or password invalid ");
                                // rd = getServletContext().getRequestDispatcher("/LoadPaymentAndBatchServlet?param=resume");

                                rd = getServletContext().getRequestDispatcher(url2);

                            }

                        } catch (Exception e) {
                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            rd = getServletContext().getRequestDispatcher(url);

                        }

                    }//break here

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


        } //catch session exception
        catch (NewLoginSessionException nlex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);

        } catch (Exception ex) {
        } finally {
            rd.forward(request, response);

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

    private SystemUserBean validateUser(SystemUserBean CMSSysUser) throws Exception {
        SystemUserBean valideTempUser = null;
        userManager = new SystemUserManager();
        valideTempUser = userManager.validateUser(CMSSysUser);
        return valideTempUser;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = String.valueOf(txnid);

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description
            systemAuditBean.setDescription("Void Payment. Payment ID: " + uniqueId + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.PAYMENT);
            systemAuditBean.setPageCode(PageVarList.BO_PAYMENT);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
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
