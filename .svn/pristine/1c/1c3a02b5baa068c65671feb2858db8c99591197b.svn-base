/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

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
 * @author chinthaka_r
 */
public class AddDuplicateUserRoleServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SystemUserRoleManager systemUserRoleManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private UserRoleBean userRoleBean;
    private String errorMessage = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private String url = "/administrator/controlpanel/systemusermgt/userrolehome.jsp";
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
            String pageCode = PageVarList.USERROLE;
            String taskCode = TaskVarList.DUPLICATE;

            //check whethre userrole have an access for this page and task
            if (!this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }
            this.setUserInputToBean(request);

            if (validateUserInput(userRoleBean)) {
                this.setAudittraceValue(request);
                this.insertDuplicateUserRole(userRoleBean,systemAuditBean);
                out.print("success,"+userRoleBean.getOldUserRoleCode()+" "+MessageVarList.USERROLE_SUCCESS_DUPLICATE+" from user role "+userRoleBean.getUserRoleCode());
            } else {
                out.print(errorMessage);

            }

        }//catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            request.setAttribute("operationtype", "view");
            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (SQLException ex) {
            //request.setAttribute("operationtype", "view");
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            //request.setAttribute("errorMsg", userRoleBean.getUserRoleCode() + " " + errorMessage);
            //rd = getServletContext().getRequestDispatcher(url);
            out.print(userRoleBean.getUserRoleCode() + " " + errorMessage);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "view");
            rd = request.getRequestDispatcher(url);
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_USERROLE);
        } finally {
            //rd.forward(request, response);
            out.close();
        }
    }
    //set adittrace values to bean
    private void setAudittraceValue(HttpServletRequest request) throws Exception{
        try {
            systemAuditBean=new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(userRoleBean.getUserRoleCode());
            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Duplicate userrole privilage. privilage code: " + userRoleBean.getUserRoleCode() + " by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.USERROLE);
            systemAuditBean.setTaskCode(TaskVarList.DUPLICATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            System.out.println("$$$ " + newValue);
            systemAuditBean.setNewValue(newValue);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());
            
        } catch (Exception ex) {
            throw ex;
        }
    }
    public boolean insertDuplicateUserRole(UserRoleBean userRoleBean,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            systemUserRoleManager = new SystemUserRoleManager();
            success = systemUserRoleManager.insertDuplicateUserRole(userRoleBean,systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    public void setUserInputToBean(HttpServletRequest request) {
        String userRoleCode = request.getParameter("userrolecode").trim();
        String description = request.getParameter("userroledescription").trim();
        String userlevelcode = request.getParameter("userlevelcode").trim();
        String statusCode = request.getParameter("statuscode");
        String oldUserRoleCode = request.getParameter("oldUserRoleCode").trim();

        userRoleBean = new UserRoleBean();
        userRoleBean.setUserRoleCode(userRoleCode);
        userRoleBean.setDescription(description);
        userRoleBean.setUserLevelID(userlevelcode);
        userRoleBean.setStatusCode(statusCode);
        userRoleBean.setOldUserRoleCode(oldUserRoleCode);
        userRoleBean.setLastUpdatedUser(sessionUser.getUserName());
        userRoleBean.setCreatedTime(new Date());

        newValue = userRoleBean.getUserRoleCode() + "|" + userRoleBean.getDescription() + "|" + userRoleBean.getStatusCode() + "|" + userRoleBean.getLastUpdatedUser()+"|"+userRoleBean.getUserLevelID();
    }

    public boolean validateUserInput(UserRoleBean userRoleBean) throws Exception {
        boolean isValidate = true;

        //validate user Role code
        try {

            if (userRoleBean.getUserRoleCode().contentEquals("") || userRoleBean.getUserRoleCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.USERROLE_USERROLECODE_NULL;
            } else if (!UserInputValidator.isCorrectString(userRoleBean.getUserRoleCode())) {
                isValidate = false;
                errorMessage = MessageVarList.USERROLE_USERROLECODE_INVALID;
            } else if (userRoleBean.getDescription().contentEquals("") || userRoleBean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.USERROLE_DESCRIPTION_NULL;
            } else if (userRoleBean.getUserLevelID().contentEquals("") || userRoleBean.getUserLevelID().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.USERROLE_USERLEVELID_NULL;
            } else if (userRoleBean.getStatusCode().contentEquals("") || userRoleBean.getStatusCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.USERROLE_STATUSCODE_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;

        }

        return isValidate;
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
