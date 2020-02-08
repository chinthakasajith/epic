/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserRoleManager;
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
 * @author chinthaka_r
 */
public class ManageUserRoleCreditConfirmationSchemaServlet extends HttpServlet {

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
    private SystemUserRoleManager systemUserRoleManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private List<UserRoleBean> assignedList = null;
    private List<UserRoleBean> unAssignedList = null;
    private String schemaCode = null;
    private String url = "/administrator/controlpanel/systemconfigmgt/creditconfirmationuserroleschemamgt.jsp";
    private String newValue;
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
            String pCode = PageVarList.CREDITCONFIRMSCEMAMGT;
            String taskCode = TaskVarList.ASSIGN;

            //check whethre userrole have an access for this page and task
            if (!this.isValidAccess(sessionUser.getUserRole(), pCode, taskCode)) {
                //if invalid throw accessdenied exception
                throw new AccessDeniedException();
            }
            String[] notToAssign = request.getParameterValues("notAssignedUserRoleList");
            String[] toAssign = request.getParameterValues("assignedUserRoleList");
            schemaCode = request.getParameter("schemaCode");

            if (toAssign != null && toAssign.length > 0) {
                String toAssignVal = "";

                for (int i = 0; i < toAssign.length; i++) {
                    toAssignVal += toAssign[i] + "|";
                }
                newValue = schemaCode + "|" + "UserRoles|" + toAssignVal + sessionVarlist.getCMSSessionUser().getUserName();
                this.getUserRoleListAssignedBySchemaCode(schemaCode);
                String oldAssignVal = "";

                for (UserRoleBean bean : assignedList) {
                    oldAssignVal += bean.getUserRoleCode() + "|";
                }
                oldValue = schemaCode + "|" + "UserRoles|" + oldAssignVal + "|" + sessionVarlist.getCMSSessionUser().getUserName();
                this.setAudittraceValueForAdd(request);

                this.assignUserRolesToSchema(schemaCode, toAssign, sessionUser.getUserName(), systemAuditBean);
            }
            if (notToAssign != null && notToAssign.length > 0) {
                 this.setAudittraceValueForDelete(request);
                 this.unassignUserRolesToSchema(schemaCode, notToAssign, systemAuditBean);
            }
            this.getUserRoleListAssignedBySchemaCode(schemaCode);
            this.getUserRoleListNotAssignedBySchemaCode(schemaCode);
            
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
            request.setAttribute("schemaCode", schemaCode);
            request.setAttribute("successMsg", MessageVarList.SUCCESS_ASSIGN_CREDIT_CONFIRM_SCHEMA);


            rd = getServletContext().getRequestDispatcher(url);
            rd.include(request, response);

        } catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);
        }//catch accessdenied exception
        catch (AccessDeniedException adex) {
            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (SQLException ex) {
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", schemaCode + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception ex) {
            rd = request.getRequestDispatcher(url);
            request.setAttribute("errorMsg", MessageVarList.ERROR_ASSIGN_CREDIT_CONFIRM_SCHEMA);
            rd.forward(request, response);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    //get assigned user role list
    private void getUserRoleListAssignedBySchemaCode(String schema) throws Exception {
        try {
            systemUserRoleManager = new SystemUserRoleManager();
            assignedList = systemUserRoleManager.getAssignedUserRolesToSchema(schema);
            sessionVarlist.setAssignedUserRolesToSchemaList(assignedList);
        } catch (Exception e) {
            throw e;
        }
    }
    //get unassigned user role list
    private void getUserRoleListNotAssignedBySchemaCode(String schema) throws Exception{
        try {
            systemUserRoleManager=new SystemUserRoleManager();
            unAssignedList=systemUserRoleManager.getUnassignedUserRolesToSchema(schema);
            sessionVarlist.setNotAssignedUserRolesToSchemaList(unAssignedList);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *
     * @param schema
     * @param assignArray
     * @param userName
     * @throws Exception
     */
    public void assignUserRolesToSchema(String schema, String[] assignArray, String userName, SystemAuditBean systemAuditBean) throws Exception {
        try {
            systemUserRoleManager = new SystemUserRoleManager();
            systemUserRoleManager.assignUserRolesToSchema(schema, assignArray, userName, systemAuditBean);

        } catch (Exception ex) {
            throw ex;
        }
    }
    /**
     * 
     * @param schema
     * @param unassignArray
     * @throws Exception 
     */
    public void unassignUserRolesToSchema(String schema, String[] unassignArray, SystemAuditBean systemAuditBean) throws Exception {
        try {
            systemUserRoleManager = new SystemUserRoleManager();
            systemUserRoleManager.unassignUserRolesToSchema(schema, unassignArray, systemAuditBean);

        } catch (Exception ex) {
            throw ex;
        }
    }


    private void setAudittraceValueForAdd(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("schemaCode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add " + schemaCode + " User Role by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ISSUING_ADMIN);
            systemAuditBean.setSectionCode(SectionVarList.APPLICATION_PROCESSING_CONFIG);
            systemAuditBean.setPageCode(PageVarList.CREDITCONFIRMSCEMAMGT);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(oldValue);
            //set new value of change if required
            systemAuditBean.setNewValue(newValue);

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }

    }
    private void setAudittraceValueForDelete(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(schemaCode);

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Delete " + schemaCode + " User Role by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.APPLICATION_PROCESSING_CONFIG);
            systemAuditBean.setPageCode(PageVarList.USERPRIVILEGE);
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
