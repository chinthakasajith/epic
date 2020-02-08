/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
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
import com.epic.cms.system.util.variable.StatusVarList;
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
 * @author prageeth_s
 */
public class ViewChangeUserPasswordServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private String getContextPath = null;
    private String url = "/administrator/controlpanel/systemusermgt/changeuserpassword.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        SystemUserBean userBean = new SystemUserBean();
        try {
            sessionObj = request.getSession(false);
            //Session User Management
            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();
                getContextPath = request.getContextPath();

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
            //End Session User Management

            //set page code and task codes
            String pageCode = PageVarList.SYSTEMUSER;
            String taskCode = TaskVarList.UPDATE;

            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
            } else {

                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }
            //Set user detais to  the request. 
            setUserDetails(request, userBean);

        } catch (SQLException ex) {
            request.setAttribute("userBean", userBean);

            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            rd = getServletContext().getRequestDispatcher("/ManageSystemUserServlet");
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

        } catch (Exception ex) {
            request.setAttribute("userBean", userBean);
            request.setAttribute("operation", "add");
            request.setAttribute("errorMsg", MessageVarList.PASSWORD_CHANGE_ERROR);
            rd = getServletContext().getRequestDispatcher("/ManageSystemUserServlet");
            rd.forward(request, response);

        } finally {

            //lock for dual authentication
            if (userBean.getStatusCode().equals(StatusVarList.ACTIVE_STATUS) || userBean.getStatusCode().equals(StatusVarList.DEACTIVE_STATUS) || userBean.getStatusCode().equals(StatusVarList.PASSWORD_RESET_REQUEST_SENT)) {

                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);

            } else {

                request.setAttribute("errorMsg", MessageVarList.DUAL_AUTH_LOCK + generateRequestLink(userBean, userBean.getStatusCode(), getContextPath));
                rd = getServletContext().getRequestDispatcher("/LoadSystemUserServlet");
                rd.forward(request, response);

            }

        }
    }

    private String generateRequestLink(SystemUserBean sysBean, String statusType, String getContextPath) {
        String anchorTag = null;
        if (StatusVarList.PASSWORD_RESET_REQUEST_SENT.equals(statusType)) {
            anchorTag = "<a class=\"link\" href=\"javascript:void(0);\" onclick=\"setUserDetails('" + sysBean.getUserName() + "','" + sysBean.getUserRoleDes() + "','" + sysBean.getUserRoleCode() + "','" + sysBean.getIsEmailSent() + "','" + StatusVarList.PASSWORD_RESET_REQUEST_SENT + "','" + sysBean.getEmail() + "','" + sysBean.getDualUserRoleCode() + "')\">" + "Change Password Request" + "</a>";
        } else if (StatusVarList.DELETE_SYSTEMUSER_REQUEST_SENT.equals(statusType)) {
            anchorTag = "<a class=\"link\" href='" + getContextPath + "/ManageSystemUserServlet?username=" + sysBean.getUserName() + "&operation=delete&status=" + StatusVarList.DELETE_SYSTEMUSER_REQUEST_SENT_DES + "&userrole=" + sysBean.getUserRoleDes() + "&statusCode=" + statusType + "'>" + "Delete Password Request" + "</a>";
        } else if (StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT.equals(statusType) || StatusVarList.DA_REQUSET_INITIATE.equals(statusType)) {
            anchorTag = "<a class=\"link\" href='" + getContextPath + "/ManageSystemUserServlet?username=" + sysBean.getUserName() + "&status=" + sysBean.getStatusCode() + "&operation=edit'>" + "Update Request Initiated" + "</a>";

        }
        return anchorTag;
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
            systemAuditBean.setDescription("Password changed : System user '" + userBean.getUserName() + "' by :" + sessionVarlist.getCMSSessionUser().getUserName());
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

    private void setUserDetails(HttpServletRequest request, SystemUserBean userBean) {
        userBean.setUserName(request.getParameter("userName"));
        userBean.setUserRoleDes(request.getParameter("userRole"));
        userBean.setUserRoleCode(request.getParameter("userRoleCode"));
        userBean.setDualUserRoleCode(request.getParameter("dualUserRoleCode"));
        userBean.setIsEmailSent(request.getParameter("isEmailSent"));
        userBean.setStatusCode(request.getParameter("status"));
        userBean.setEmail(request.getParameter("email"));
        request.setAttribute("userBean", userBean);
    }

}
