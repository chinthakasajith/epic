/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.email.service.EmailApplicationService;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.security.CMSMd5;
import com.epic.cms.system.util.security.PasswordGenerator;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.EmailVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author prageeth_s
 */
public class UpdateChangedUserPasswordServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    String errorMsg = "";
    private List<SystemUserBean> userList;
    private final String url = "/administrator/controlpanel/systemusermgt/changeuserpassword.jsp";

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
        SystemUserBean userBean = new SystemUserBean();
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
            String pageCode = PageVarList.SYSTEMUSER;
            String taskCode = TaskVarList.UPDATE;

            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                //get form values
                userBean.setUserName(request.getParameter("userName"));
                userBean.setUserRoleDes(request.getParameter("userRole"));
                userBean.setUserRoleCode(request.getParameter("userRoleCode"));
                userBean.setDualUserRoleCode(request.getParameter("dualUserRoleCode"));
                userBean.setStatusCode(request.getParameter("status"));
                userBean.setEmail(request.getParameter("email"));
                userBean.setRejectRemark(request.getParameter("rejectRemark"));
                userBean.setLastUpdatedUser(sessionUser.getUserName());
                String isRequestAccepted = request.getParameter("accept");
                userBean.setAuthorizedUser(sessionUser.getUserName());

                SystemUserBean systemUserBean = null;
                this.getAllSysUsersExceptDel(sessionUser);
                //get is auth lock from system user table
                for (SystemUserBean bean : userList) {
                    if (bean.getUserName().equals(userBean.getUserName()) && bean.getIslockedforauth() != null) {
                        systemUserBean = bean;
                        break;
                    }
                }

                //set users other field to fetch to systemuserrequest 
                userBean.setUserRoleCode(systemUserBean.getUserRoleCode());
                userBean.setExpiryDateToString(systemUserBean.getExpiryDateToString());
                userBean.setDualUserRoleCode(systemUserBean.getDualUserRoleCode());
                userBean.setTitle(systemUserBean.getTitle());
                userBean.setInitials(systemUserBean.getInitials());
                userBean.setFirstName(systemUserBean.getFirstName());
                userBean.setLastName(systemUserBean.getLastName());
                userBean.setBankname(systemUserBean.getBankname());
                userBean.setBranchname(systemUserBean.getBranchname());
                userBean.setDesignation(systemUserBean.getDesignation());
                userBean.setServiseid(systemUserBean.getServiseid());
                userBean.setTelNo(systemUserBean.getTelNo());
                userBean.setNic(systemUserBean.getNic());
                userBean.setGender(systemUserBean.getGender());
                userBean.setBirthday(systemUserBean.getBirthday());
                userBean.setEmail(systemUserBean.getEmail());
                userBean.setIslockedforauth(systemUserBean.getIslockedforauth());
                userBean.setIsEmailSent(systemUserBean.getIsEmailSent());

                if (StatusVarList.IS_LOCKED_AUTH.equals(userBean.getIslockedforauth()) && !StatusVarList.PASSWORD_RESET_REQUEST_SENT.equals(userBean.getStatusCode())) {
                    request.setAttribute("errorMsg", MessageVarList.DUAL_AUTH_LOCK);
                    request.setAttribute("userBean", userBean);
                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);

                } else if (StatusVarList.DA_REQUEST_APPROVE_DES.equals(isRequestAccepted) && !userBean.getIsEmailSent().isEmpty() && StatusVarList.PASSWORD_RESET_REQUEST_SENT.equals(userBean.getStatusCode()) && StatusVarList.WELCOME_EMAIL_SENT.equals(userBean.getIsEmailSent())) {

                    //generate 8 character random generated password
                    PasswordGenerator passwordGenerator = new PasswordGenerator();
                    userBean.setPlainTextPassword(passwordGenerator.getRandomPassword(8));

                    String password = CMSMd5.cmsMd5(userBean.getPlainTextPassword());
                    userBean.setPassword(password);

                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("USERNAME", userBean.getUserName());
                    parameters.put("PASSWORD", userBean.getPlainTextPassword());
                    EmailApplicationService emailApplicationService = new EmailApplicationService();

                    //sending the reset password email
                    boolean success = emailApplicationService.sendEmail(userBean.getEmail(), EmailVarList.CHANGE_PASSWORD_EMAIL_TEMP_CODE, "\\|", parameters, userBean.getLastUpdatedUser());

                    if (success) {
                        // update the email active status to system user table  
                        userBean.setStatusCode(StatusVarList.DA_REQUSET_APPROVE);
                        userBean.setIslockedforauth(StatusVarList.IS_NOT_LOCKED_AUTH);
                        systemUserManager.updateEmailStatusAndPasswordAndAuth(userBean);
                        this.setAudittraceValue(request, userBean);
                        request.setAttribute("successMsg", MessageVarList.SUCCESS_CHANGE_PASSWORD);
                        rd = getServletContext().getRequestDispatcher("/LoadSystemUserServlet");
                        rd.forward(request, response);
                    } else {

                        // if email sending not successfull
                        request.setAttribute("userBean", userBean);
                        request.setAttribute("operation", "add");
                        request.setAttribute("errorMsg", MessageVarList.ERROR_CHANGE_PASSWORD);
                        rd = getServletContext().getRequestDispatcher("/LoadSystemUserServlet");
                        rd.forward(request, response);
                    }

                } else if (StatusVarList.DA_REQUEST_REJECT_DES.equals(isRequestAccepted)) {
                    SystemUserBean invalidMsgBean = new SystemUserBean();

                    //check remark field empty if the user reject the request
                    if (userBean.getRejectRemark().isEmpty()) {
                        invalidMsgBean.setRejectRemark("Requierd");
                        request.setAttribute("invalidMsgBean", invalidMsgBean);
                        rd = getServletContext().getRequestDispatcher("/ViewChangeUserPasswordServlet");
                        rd.forward(request, response);
                    } else {
                        // update the email reject remark to system user table 
                        userBean.setStatusCode(StatusVarList.DA_REQUSET_REJECT);
                        userBean.setIslockedforauth(StatusVarList.IS_NOT_LOCKED_AUTH);
                        systemUserManager.updateEmailStatusAndPasswordAndAuthForReject(userBean);
                        systemUserManager.updateSystemUserAuthLock(userBean);
                        this.setAudittraceValue(request, userBean);
                        request.setAttribute("successMsg", MessageVarList.SUCCESS_CHANGE_PASSWORD_REQ_REJECT);
                        rd = getServletContext().getRequestDispatcher("/LoadSystemUserServlet");
                        rd.forward(request, response);
                    }
                } else if (StatusVarList.WELCOME_EMAIL_SENT.equals(userBean.getIsEmailSent())) {
                    //if want to sent change password request 
                    userBean.setStatusCode(StatusVarList.PASSWORD_RESET_REQUEST_SENT);
                    userBean.setIslockedforauth(StatusVarList.IS_LOCKED_AUTH);
                    userBean.setRequestedUser(sessionUser.getUserName());

                    //systemUserManager.updateSystemUserStatus(userBean);
                    this.setAudittraceValue(request, userBean);
                    userBean.setPassword("requested");
                    systemUserManager.insertUserPasswordRequest(userBean, systemAuditBean);
                    systemUserManager.updateSystemUserAuthLock(userBean);

                    request.setAttribute("successMsg", MessageVarList.SUCCESS_CHANGE_PASSWORD_REQ_SENT);
                    rd = getServletContext().getRequestDispatcher("/LoadSystemUserServlet");
                    rd.forward(request, response);
                } else {
                    request.setAttribute("errorMsg", MessageVarList.USER_NOT_ACTIVATE);
                    rd = getServletContext().getRequestDispatcher("/ViewChangeUserPasswordServlet");
                    rd.forward(request, response);
                }

            } else {

                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }

        } catch (SQLException ex) {
            request.setAttribute("userBean", userBean);
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            rd = getServletContext().getRequestDispatcher("/ViewChangeUserPasswordServlet");
            rd.forward(request, response);
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

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (MessagingException mex) {
            request.setAttribute("errorMsg", MessageVarList.EMAIL_SENT_ERROR);
            rd = getServletContext().getRequestDispatcher("/ViewChangeUserPasswordServlet");
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("userBean", userBean);

            request.setAttribute("errorMsg", MessageVarList.PASSWORD_CHANGE_ERROR);
            rd = getServletContext().getRequestDispatcher("/ViewChangeUserPasswordServlet");
            rd.forward(request, response);

        } finally {
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    private void setAudittraceValue(HttpServletRequest request, SystemUserBean userBean) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            if (userBean.getAuthorizedUser() != null) {
                systemAuditBean.setDescription("Change Password Status '" + userBean.getStatusCode() + "' Authorized for'" + userBean.getUserName() + "' by :" + sessionVarlist.getCMSSessionUser().getUserName());
            } else {
                systemAuditBean.setDescription("Change Password Requested for'" + userBean.getUserName() + "' by :" + sessionVarlist.getCMSSessionUser().getUserName());
            }
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.SYSTEMUSER);
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

    public void getAllSysUsersExceptDel(SessionUser sessionUser) throws Exception {
        systemUserManager = new SystemUserManager();
        userList = systemUserManager.getAllSysUsersExceptDel(sessionUser);
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
