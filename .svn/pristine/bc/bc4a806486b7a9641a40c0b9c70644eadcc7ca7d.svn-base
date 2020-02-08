/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserRoleManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
public class GettasksByPageServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SystemUserRoleManager systemUserRoleManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private List<SectionBean> sectionList = null;
    private List<PageBean> pageList = null;
    private List<TaskBean> toTaskList = null;
    private List<TaskBean> fromTaskList = null;
    private List<ApplicationModuleBean> userRoleAppliacationList = null;
    String id = null;
    private String url = "/administrator/controlpanel/systemusermgt/userroletaskassign.jsp";

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

            String userRoleCode = request.getParameter("userrolecode");
            String applicationCode = request.getParameter("applicationcode");
            String sectionCode = request.getParameter("sectioncode");
            String pageCode = request.getParameter("pagecode");
            id = request.getParameter("id");

            if (id.equals("one")) {


                request.setAttribute("userRole", userRoleCode);
                request.setAttribute("selectedapplication", applicationCode);
                request.setAttribute("selectedsection", sectionCode);
                request.setAttribute("selectedpage", pageCode);

//                this.getApplicationListByUserRole(userRoleCode);
//                this.getSectionbyApplicationAndUserRoleCode(userRoleCode, applicationCode);
//                this.getPagebySectionAndUserRoleCode(userRoleCode, sectionCode);
                this.getTaskbyPageAndUserRoleCode(userRoleCode, pageCode);
                this.getTaskByPageAndUserRoleNotAssign(userRoleCode, pageCode);

                sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                rd = getServletContext().getRequestDispatcher(url);

            }




        } catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/controlpanelhome.jsp?message=" + MessageVarList.SESSION_EXPIRED);


        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/controlpanelhome.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } catch (Exception ex) {


            rd = request.getRequestDispatcher(url);


            request.setAttribute("errorMsg", MessageVarList.ERROR_VIEW_USERROLE);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }





    /**
     * 
     * @param userRoleCode
     * @param sectionCode
     * @throws Exception 
     */
    private void getPagebySectionAndUserRoleCode(String userRoleCode, String sectionCode) throws Exception {

        try {

            systemUserRoleManager = new SystemUserRoleManager();
            pageList = systemUserRoleManager.getPageBySectionAndUserRoleCode(userRoleCode, sectionCode);

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 
     * @param userRoleCode
     * @param pageCode
     * @throws Exception 
     */
    private void getTaskbyPageAndUserRoleCode(String userRoleCode, String pageCode) throws Exception {

        try {

            systemUserRoleManager = new SystemUserRoleManager();
            toTaskList = systemUserRoleManager.getTaskByPageAndUserRoleCode(userRoleCode, pageCode);
            sessionVarlist.setAssignUserRoleTasks(toTaskList);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 
     * @param userRoleCode
     * @param pageCode
     * @throws Exception 
     */
    private void getTaskByPageAndUserRoleNotAssign(String userRoleCode, String pageCode) throws Exception {

        try {

            systemUserRoleManager = new SystemUserRoleManager();
            fromTaskList = systemUserRoleManager.getTaskByPageAndUserRoleNotAssign(userRoleCode, pageCode);
            sessionVarlist.setNotAssignUserRoleTasks(fromTaskList);

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
