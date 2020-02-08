/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.ApplicationSectionPageManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author upul
 */
public class AddUpdateApplicationSectionServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SessionUser sessionUser = null;
    private RequestDispatcher rd = null;
    private SystemUserManager systemUserManager = null;
    private ApplicationSectionPageManager applicationSectionPageManager = null;
    List<String> existSecList = null;
    private boolean isValid = true;
    private String errorMsg = null, application = null;
    String url = "/ManageApplicationSectionFormServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //call to existing session
            /////////////////////////////////////////////////////////////////////
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
            /////////////////////////////////////////////////////////////////////
            isValid = true;
            String[] notToAssign = request.getParameterValues("notassignsectionlist");
            String[] toAssign = request.getParameterValues("assignsectionlist");
            application = request.getParameter("applicationcodefield").trim();

            if (application.contentEquals("") || application.length() <= 0) {
                isValid = false;
                errorMsg = MessageVarList.APPLICATION_NULL;
            }

            if (isValid) {
                errorMsg = null;
                request.setAttribute("applicationcode", application);
                //check whethre userrole have an access for this page and task
                String taskCode = TaskVarList.ASSIGN;
                if (this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), taskCode)) {
                    //set auditrace values
                    this.setAudittraceValue(request);

                    this.assignApplicationSection(application, toAssign, notToAssign, sessionUser.getUserName(), systemAuditBean);

                } else {
                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }

                if (existSecList.size() > 0) {

                    String pageList = "";
                    for (int i = 0; i < existSecList.size(); i++) {

                        pageList = pageList + existSecList.get(i) + ",";
                    }

                    String errMessage = "You cannot remove follwing sections. " + pageList;
                    request.setAttribute("errorMsg", errMessage);
                    rd = getServletContext().getRequestDispatcher("/ManageApplicationSectionFormServlet");
                    rd.include(request, response);

                } else {
                    String successMessage = MessageVarList.APPLICATION_SEC_MNGMNT_SECTION_ASSIGN_SUCCESS;
                    request.setAttribute("successMsg", successMessage);
                    rd = getServletContext().getRequestDispatcher("/ManageApplicationSectionFormServlet");
                    rd.include(request, response);
                }
            } else {

                request.setAttribute("errorMsg", errorMsg);
                rd = getServletContext().getRequestDispatcher("/ManageApplicationSectionFormServlet");
                rd.include(request, response);
            }
            //////////////////////////////////////////////////////////////////
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            String errMessage = MessageVarList.ACCESS_DENIED_PAGETASK;
            request.setAttribute("errorMsg", errMessage);
            rd = getServletContext().getRequestDispatcher("/ManageApplicationSectionFormServlet");
            rd.include(request, response);


        } catch (SQLException ex) {
            //when throw an sql exception
        } catch (Exception ex) {
        } finally {
            out.close();
        }
    }

    private void assignApplicationSection(String applicationCode, String[] assinArray, String[] unAssignArray, String sysUser, SystemAuditBean systemAuditBean) throws Exception {

        try {

            existSecList = new ArrayList<String>();

            applicationSectionPageManager = new ApplicationSectionPageManager();

            existSecList = applicationSectionPageManager.assignApplicationSection(applicationCode, assinArray, unAssignArray, sysUser, systemAuditBean);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("appCode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add sections for application, application code: " + application + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.SECTIONPAGE);
            // systemAuditBean.setTaskCode(TaskVarList.ADD);
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

    ////////////////////////////////////////////////////////////////////////////    
    /**
     * isValidTaskByUser
     * @param userTaskList
     * @param task
     * @return
     * @throws Exception 
     */
    private boolean isValidTaskByUser(List<String> userTaskList, String task) throws Exception {
        boolean isValidTask = false;
        try {

            for (String usertask : userTaskList) {

                if (task.equals(usertask)) {
                    isValidTask = true;
                }


            }
            return isValidTask;



        } catch (Exception ex) {
            throw ex;
        }



    }
    ////////////////////////////////////////////////////////////////////////////       

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
