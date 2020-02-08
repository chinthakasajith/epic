/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.assigndata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
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
 * @author chanuka
 */
public class LoadAssignDataSearchServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private ApplicationAssignManager appAssignManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private HashMap<String, String> priorityLevelList = null;
//    private HashMap<String, String> applicationDomainList;
    private List<String> usersList = null;
    private List<StatusBean> statusList;
    private String url = "/camm/assigningdata/appassignupdate.jsp";
    private String errorMessage = null;

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

            try {
                //set page code and task codes
                String pageCode = PageVarList.DATAASSIGNUPDATE;
                String taskCode = TaskVarList.ACCESSPAGE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {



                    this.getAllUserList();
                    this.getAllPriorityLevelList();
                    this.getAllStatus("ASGN");
//                    this.getAllApplicationDoamin();

                    Collections.sort(usersList);

                    request.setAttribute("usersList", usersList);
                    request.setAttribute("priorityLevelList", priorityLevelList);
//                    request.setAttribute("applicationDomainList", applicationDomainList);


                    sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                    rd = getServletContext().getRequestDispatcher(url);



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

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);


        }  catch (SQLException ex) {
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        }catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_ASSIGN_APP);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    ///////////////////////////
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

    private void getAllUserList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            usersList = appAssignManager.getAllUserList(PageVarList.DATAASSIGNUPDATE);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllPriorityLevelList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            priorityLevelList = appAssignManager.getAllPriorityLevels();
        } catch (Exception ex) {
            throw ex;
        }
    }

//    private void getAllBranchesDetailsList() throws Exception {
//        try {
//            appAssignManager = new ApplicationAssignManager();
//            branchesDeatilsList = appAssignManager.getAllBranchesDetails();
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
    private void getAllStatus(String categoryCode) throws Exception {
        try {
            systemUserManager = new SystemUserManager();
            statusList = systemUserManager.getStatusByUserRole(categoryCode);
            sessionVarlist.setStatusList(statusList);
        } catch (Exception ex) {
            throw ex;
        }
    }

//    private void getAllApplicationDoamin() throws Exception {
//        try {
//            appAssignManager = new ApplicationAssignManager();
//            applicationDomainList = appAssignManager.getAllApplicationDoamin();
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
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
