/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodprocessmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.MerchantCategoryManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.backoffice.eodprocessmgt.bean.EODProcessMgtBean;
import com.epic.cms.backoffice.eodprocessmgt.businesslogic.EODProcessMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.util.ArrayList;
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
 * @author nisansala
 */
public class LoadEODProcessMgtServlet extends HttpServlet {

    
    
    //initializing variables
    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private SessionUser sessionUser = null;
    private List<String> userTaskList = null;
    private SystemUserManager sysUserMgr = null;
    private SessionVarList sessionVarlist = null;
    private SystemUserManager systemUserManager = null;
    private List<StatusBean> statusList;
    //-----------------------------------------------------------------------------
    private EODProcessMgtManager eodMgr;
    private EODProcessMgtBean process;
    HashMap<String, String> processCategory;
    private List<EODProcessMgtBean> processList;
    private String url = "/backoffice/eodprocessmgt/eodprocessmgthome.jsp";

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
        sessionObj = request.getSession(false);

        try {

            //--------------------------------------------------------------------------------------------------//

            try {
                request.setAttribute("operationType", "add");

                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                //check system user is in the same session or not
                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        //user not in same session.
                        throw new NewLoginSessionException();

                    }

                } catch (NewLoginSessionException nlex) {
                    //throw lst login close exception
                    throw new NewLoginSessionException();

                }
                try {
                    //set page code and task codes
                    String pageCode = PageVarList.EOD_PROCESS;
                    String taskCode = TaskVarList.ACCESSPAGE;


                    //check whether userrole can access this page and task
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        try {
                            this.getAllEODProcessData();
                            this.getProcessCategoryList();
                            request.setAttribute("processCategory", processCategory);
                            request.setAttribute("EODbean", process);
                            request.setAttribute("eodBeanList", processList);
                            request.setAttribute("operationtype", "add");
                            this.getAllStatus(StatusVarList.GENESTATUSCAT);
                            sessionVarlist.setStatusList(statusList);
                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                            rd = getServletContext().getRequestDispatcher(url);

                        } catch (Exception e) {
                            request.setAttribute("operationtype", "add");
                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            rd = getServletContext().getRequestDispatcher(url);

                        }


                    } else {

                        //if invalid throw access denied exception
                        throw new AccessDeniedException();

                    }

                    //set tasks to the session
                    sessionVarlist.setUserPageTaskList(userTaskList);


                } catch (AccessDeniedException adex) {
                    throw adex;
                }
            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }

            //------------------------------------------------------------------------------------------------------------//
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);


        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
        }
    }

    private void getAllStatus(String categoryCode) throws Exception {
        try {
            sysUserMgr = new SystemUserManager();
            statusList = sysUserMgr.getStatusByUserRole(categoryCode);
        } catch (Exception e) {
            throw e;
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

    private void getAllEODProcessData() throws Exception {
        try {

            processList = new ArrayList<EODProcessMgtBean>();
            eodMgr = new EODProcessMgtManager();
            //retrieve merchant details
            processList = eodMgr.getAllEODProcessData();
        } catch (Exception e) {
            throw e;
        }
    }
    
    private void getProcessCategoryList() throws Exception {
        try {
            
            eodMgr = new EODProcessMgtManager();
            //retrieve merchant details
            processCategory = eodMgr.getProcessCategoryList();
        } catch (Exception e) {
            throw e;
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
