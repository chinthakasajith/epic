/*
 * To change this template, choose Tools | Templates
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
 * @author janaka_h
 */
public class DeleteSystemUserServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private String url = "/LoadSystemUserServlet";
    private String oldValue = "";
    private List<SystemUserBean> userList;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
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
            String taskCode = TaskVarList.DELETE;

            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                userBean.setUserName(request.getParameter("username"));
                userBean.setUserRoleDes(request.getParameter("userroledes"));
                userBean.setStatusDes(request.getParameter("statusdes"));
                userBean.setRejectRemark(request.getParameter("rejectremark"));
                userBean.setRequestedUser(sessionUser.getUserName());
                userBean.setAuthorizedUser(sessionUser.getUserName());
                userBean.setStatusCode(request.getParameter("statusCode"));
                String isDeleteAceppted = request.getParameter("accept");

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

                if (null != systemUserBean) {
                    StringBuffer buff1 = new StringBuffer();

                    buff1.append(systemUserBean.getUserName()).append("|");
                    buff1.append(systemUserBean.getUserRoleCode()).append("|").append(userBean.getStatusCode()).append("|").append(systemUserBean.getDualUserRoleCode()).append("|");
                    buff1.append(systemUserBean.getTitle()).append("|").append(systemUserBean.getInitials()).append("|").append(systemUserBean.getFirstName()).append("|").append(systemUserBean.getLastName()).append("|");
                    buff1.append(systemUserBean.getBankname()).append("|").append(systemUserBean.getBranchname()).append("|").append(systemUserBean.getDesignation()).append("|").append(systemUserBean.getServiseid()).append("|");
                    buff1.append(systemUserBean.getTelNo()).append("|").append(systemUserBean.getNic()).append("|").append(systemUserBean.getEmail()).append("|").append(systemUserBean.getGender()).append("|").append(systemUserBean.getBirthday()).append("|").append(systemUserBean.getExpiryDateToString());

                    oldValue = buff1.toString();
                }

                if (sessionUser.getUserName().equals(userBean.getUserName())) {
                    //if the deleting user is logging user
                    request.setAttribute("errorMsg", MessageVarList.DELETE_LOGGED_USER);
                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);

                } else if (StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT.equals(userBean.getStatusCode())) {
                    //if new user not approved and deletes
                    this.setAudittraceValue(request, userBean);
                    systemAuditBean.setDescription("Delete System user before approval, user name: " + userBean.getUserName() + " by " + sessionVarlist.getCMSSessionUser().getUserName());
                    int isDelete = systemUserManager.deleteSystemUserRequest(userBean, systemAuditBean);

                    if (isDelete == 1) {
                        request.setAttribute("successMsg", MessageVarList.SUCESS_USER_DELETE);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {

                        request.setAttribute("userBean", userBean);
                        request.setAttribute("operation", "delete");
                        request.setAttribute("errorMsg", MessageVarList.ERROR_USER_DELETE);
                        rd = getServletContext().getRequestDispatcher("/ManageSystemUserServlet");
                        rd.forward(request, response);
                    }
                } else if (StatusVarList.IS_LOCKED_AUTH.equals(userBean.getIslockedforauth()) && !StatusVarList.DELETE_SYSTEMUSER_REQUEST_SENT.equals(userBean.getStatusCode())) {
                    request.setAttribute("errorMsg", MessageVarList.DUAL_AUTH_LOCK);
                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);

                } else if (StatusVarList.DA_REQUEST_APPROVE_DES.equals(isDeleteAceppted)) {

                    //if user accept a delete request
                    systemUserManager = new SystemUserManager();

                    int isDelete;
                    this.setAudittraceValue(request, userBean);
                    if (StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT.equals(userBean.getStatusCode())) {
                        isDelete = systemUserManager.deleteSystemUserRequest(userBean, systemAuditBean);
                        userBean.setRejectRemark("User deleted before user approved or login to system.");
                        userBean.setStatusCode(StatusVarList.SYSTEMUSER_DELETED);
                        systemUserManager.updateSystemRequestDeleteStatus(userBean);

                        if (isDelete == 1) {
                            request.setAttribute("successMsg", MessageVarList.SUCESS_USER_DELETE);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        } else {

                            request.setAttribute("userBean", userBean);
                            request.setAttribute("operation", "delete");
                            request.setAttribute("errorMsg", MessageVarList.ERROR_USER_DELETE);
                            rd = getServletContext().getRequestDispatcher("/ManageSystemUserServlet");
                            rd.forward(request, response);
                        }
                    } else {
                        userBean.setStatusCode(StatusVarList.SYSTEMUSER_DELETED);
                        userBean.setIslockedforauth(StatusVarList.IS_NOT_LOCKED_AUTH);
                        isDelete = systemUserManager.updateSystemUserAuthLock(userBean);
                        //update systemuser request
                        isDelete += systemUserManager.updateSystemRequestDeleteStatus(userBean);
                        isDelete += systemUserManager.deleteSystemUser(userBean);

                        if (isDelete == 3) {
                            request.setAttribute("successMsg", MessageVarList.SUCESS_USER_DELETE);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        } else {

                            request.setAttribute("userBean", userBean);
                            request.setAttribute("operation", "delete");
                            request.setAttribute("errorMsg", MessageVarList.ERROR_USER_DELETE);
                            rd = getServletContext().getRequestDispatcher("/ManageSystemUserServlet");
                            rd.forward(request, response);
                        }
                    }

                } else if (StatusVarList.DA_REQUEST_REJECT_DES.equals(isDeleteAceppted)) {

                    if (userBean.getRejectRemark().isEmpty()) {
                        userBean.setStatusCode(StatusVarList.DELETE_SYSTEMUSER_REQUEST_SENT);
                        userBean.setRejectRemark("Required");
                        request.setAttribute("invalidMsgBean", userBean.getRejectRemark());
                        request.setAttribute("operation", "delete");
                        request.setAttribute("userBean", userBean);
                        rd = getServletContext().getRequestDispatcher("/ManageSystemUserServlet");
                        rd.forward(request, response);
                    } else {
                        //if a user reject a request update reject remark
                        userBean.setStatusCode(StatusVarList.DA_REQUSET_REJECT);
                        int isdeleteRequestSent = systemUserManager.updateSystemRequestDeleteStatus(userBean);
                        userBean.setIslockedforauth(StatusVarList.IS_NOT_LOCKED_AUTH);
                        isdeleteRequestSent += systemUserManager.updateSystemUserAuthLock(userBean);

                        if (isdeleteRequestSent == 2) {
                            request.setAttribute("successMsg", MessageVarList.SUCESS_USER_DELETE_REJECT);
                            rd = getServletContext().getRequestDispatcher("/ManageSystemUserServlet");
                            rd.forward(request, response);
                        } else {

                            request.setAttribute("userBean", userBean);
                            request.setAttribute("operation", "delete");
                            request.setAttribute("errorMsg", MessageVarList.ERROR_USER_DELETE_REJECT);
                            rd = getServletContext().getRequestDispatcher("/ManageSystemUserServlet");
                            rd.forward(request, response);
                        }
                    }

                } else {

                    //if a user want to delete a user and click yes button
                    userBean.setStatusCode(StatusVarList.DELETE_SYSTEMUSER_REQUEST_SENT);
                    userBean.setIslockedforauth(StatusVarList.IS_LOCKED_AUTH);
                    this.setAudittraceValue(request, userBean);
                    int isdeleteRequestSent = systemUserManager.insertSystemUserRequest(userBean, systemAuditBean);
                    isdeleteRequestSent += systemUserManager.updateSystemUserAuthLock(userBean);

                    if (isdeleteRequestSent == 2) {
                        request.setAttribute("successMsg", MessageVarList.SUCESS_USER_DELETE_REQUEST);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {

                        request.setAttribute("userBean", userBean);
                        request.setAttribute("operation", "delete");
                        request.setAttribute("errorMsg", MessageVarList.ERROR_USER_DELETE_REQUEST);
                        rd = getServletContext().getRequestDispatcher("/ManageSystemUserServlet");
                        rd.forward(request, response);
                    }

                }

            } else {

                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }

        } catch (SQLException ex) {

            request.setAttribute("userBean", userBean);
            request.setAttribute("operation", "delete");
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
            request.setAttribute("operation", "delete");
            request.setAttribute("errorMsg", MessageVarList.ERROR_DELETE);
            rd = getServletContext().getRequestDispatcher("/ManageSystemUserServlet");
            rd.forward(request, response);

        } finally {
            out.close();
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
            systemAuditBean.setDescription("Delete System user, user name: " + userBean.getUserName() + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.SYSTEMUSER);
            systemAuditBean.setTaskCode(TaskVarList.DELETE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
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
