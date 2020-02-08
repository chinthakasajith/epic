/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserLevelBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserLevelManager;
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
import java.util.Collections;
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
 * @author sajith_g
 */
public class AddUserLevelServlet extends HttpServlet {

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
    private SystemUserLevelManager systemUserLevelManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private UserLevelBean userLevelBean;
    private String errorMessage = null;
    private List<String> userTaskList;
    private List<UserLevelBean> userLevelList;
    private SystemAuditBean systemAuditBean = null;
    private String url = "/administrator/controlpanel/systemusermgt/userlevelhome.jsp";
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
            String pageCode = PageVarList.USERLEVEL;
            String taskCode = TaskVarList.ADD;

            //check whethre userrole have an access for this page and task
            if (!this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }

            this.setUserInputToBean(request);
            this.getAllUserLevel();
            request.setAttribute("userLevelList", userLevelList);

            if (validateUserInput(userLevelBean)) {

                request.setAttribute("operationtype", "add");
                this.setAudittraceValue(request);
                insertUserRole(userLevelBean, systemAuditBean);

                request.setAttribute("successMsg", userLevelBean.getUserLevelID()+ " " + MessageVarList.USERLEVEL_SUCCESS_ADD);
                rd = getServletContext().getRequestDispatcher("/LoadUserLevelMgtServlet");

            } else {
                request.setAttribute("operationtype", "add");
                request.setAttribute("userLevelBean", userLevelBean);
                request.setAttribute("errorMsg", errorMessage);
                rd = getServletContext().getRequestDispatcher(url);

            }
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);


        } catch (SQLException ex) {
            request.setAttribute("operationtype", "add");
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", userLevelBean.getUserLevelID()+ " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            rd = request.getRequestDispatcher(url);
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_USERLEVEL);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * 
     * @param request 
     */
    public void setUserInputToBean(HttpServletRequest request) {

        String userLevelID = request.getParameter("userlevelcode").trim();
        String description = request.getParameter("userleveldescription").trim();
        String statusCode = request.getParameter("statuscode");
        userLevelBean = new UserLevelBean();
        userLevelBean.setUserLevelID(userLevelID);
        userLevelBean.setDescription(description);
        userLevelBean.setStatusCode(statusCode);

        userLevelBean.setLastUpdatedUser(sessionUser.getUserName());
        userLevelBean.setCreatedTime(new Date());

        newValue = userLevelBean.getUserLevelID()+ "|" + userLevelBean.getDescription() + "|" + userLevelBean.getStatusCode() + "|" + userLevelBean.getLastUpdatedUser()+"|"+userLevelBean.getCreatedTime();

    }
    
    public boolean validateUserInput(UserLevelBean userLevelBean) throws Exception {
        boolean isValidate = true;


        
        try {

            if (userLevelBean.getUserLevelID().contentEquals("") || userLevelBean.getUserLevelID().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.USERLEVEL_USERLEVELCODE_NULL;
            } else if (!UserInputValidator.isNumeric(userLevelBean.getUserLevelID())) {
                isValidate = false;
                errorMessage = MessageVarList.USERLEVEL_USERLEVELID_INVALID;
            } else if (userLevelBean.getDescription().contentEquals("") || userLevelBean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.USERLEVEL_DESCRIPTION_NULL;
            } else if (userLevelBean.getStatusCode().contentEquals("") || userLevelBean.getStatusCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.USERLEVEL_STATUSCODE_NULL;
            }



        } catch (Exception ex) {
            isValidate = false;

        }

        return isValidate;
    }

    /**
     * 
     * @param userLevelBean
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean insertUserRole(UserLevelBean userLevelBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            systemUserLevelManager = new SystemUserLevelManager();
            success = systemUserLevelManager.insertUserRole(userLevelBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(userLevelBean.getUserLevelID());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add user level privilage. privilage code: " + userLevelBean.getUserLevelID()+ " by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.USERLEVEL);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
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
    
    private void getAllUserLevel() throws Exception {
        try {
            systemUserLevelManager = new SystemUserLevelManager();
            userLevelList = systemUserLevelManager.getAllUserLevel();
            Collections.sort(userTaskList);
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
