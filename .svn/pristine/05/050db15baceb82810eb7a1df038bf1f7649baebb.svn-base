/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
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
public class GetpagesBySectionServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SystemUserRoleManager systemUserRoleManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    String id = null;
    private List<ApplicationModuleBean> userRoleAppliacationList = null;
    private List<SectionBean> sectionList = null;
    private List<PageBean> toPageList = null;
    private List<PageBean> fromPageList = null;
    private String url1 = "/administrator/controlpanel/systemusermgt/userrolepageassign.jsp";
    private String url2 = "/administrator/controlpanel/systemusermgt/userroletaskassign.jsp";

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
            id = request.getParameter("id");

            if (id.equals("one")) {

//                this.getApplicationListByUserRole(userRoleCode);
//                this.getSectionbyApplicationAndUserRoleCode(userRoleCode, applicationCode);
                request.setAttribute("userRole", userRoleCode);
                request.setAttribute("selectedapplication", applicationCode);
                request.setAttribute("selectedsection", sectionCode);

                this.getPagebySectionAndUserRoleCode(userRoleCode, sectionCode);
                this.getPageBySectionAndUserRoleNotAssign(userRoleCode, sectionCode);
                sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);



//                
//                request.setAttribute("applicationList", userRoleAppliacationList);
//                request.setAttribute("sectionList", sectionList);
//                request.setAttribute("notassigned", fromPageList);
//                request.setAttribute("assigned", toPageList);
                rd = getServletContext().getRequestDispatcher(url1);

            }
            if (id.equals("two")) {

//                this.getApplicationListByUserRole(userRoleCode);

                request.setAttribute("userRole", userRoleCode);
                request.setAttribute("selectedapplication", applicationCode);
                request.setAttribute("selectedsection", sectionCode);

                this.getSectionbyApplicationAndUserRoleCode(userRoleCode, applicationCode);
                this.getPagebySectionAndUserRoleCode(userRoleCode, sectionCode);

                sessionVarlist.setAssignUserRoleTasks(null);
                sessionVarlist.setNotAssignUserRoleTasks(null);
                sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                rd = getServletContext().getRequestDispatcher(url2);

            }




        } catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/controlpanelhome.jsp?message=" + MessageVarList.SESSION_EXPIRED);


        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/controlpanelhome.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } catch (Exception ex) {

            if (id.equals("one")) {
                rd = request.getRequestDispatcher(url1);
            } else {
                rd = request.getRequestDispatcher(url2);
            }

            request.setAttribute("errorMsg", MessageVarList.ERROR_VIEW_USERROLE);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * 
     * @param userRoleCode
     * @throws Exception 
     */
    private void getApplicationListByUserRole(String userRoleCode) throws Exception {

        try {

            systemUserRoleManager = new SystemUserRoleManager();
            userRoleAppliacationList = systemUserRoleManager.getUserRoleApplicationByUserRole(userRoleCode);

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 
     * @param userRoleCode
     * @param applicationCode
     * @throws Exception 
     */
    private void getSectionbyApplicationAndUserRoleCode(String userRoleCode, String applicationCode) throws Exception {

        try {

            systemUserRoleManager = new SystemUserRoleManager();
            sectionList = systemUserRoleManager.getSectionByApplicationAndUserRoleCode(userRoleCode, applicationCode);
            sessionVarlist.setAssignUserRoleSections(sectionList);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getPagebySectionAndUserRoleCode(String userRoleCode, String sectionCode) throws Exception {

        try {

            systemUserRoleManager = new SystemUserRoleManager();
            toPageList = systemUserRoleManager.getPageBySectionAndUserRoleCode(userRoleCode, sectionCode);
            sessionVarlist.setAssignUserRolePages(toPageList);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getPageBySectionAndUserRoleNotAssign(String userRoleCode, String sectionCode) throws Exception {

        try {

            systemUserRoleManager = new SystemUserRoleManager();
            fromPageList = systemUserRoleManager.getPageBySectionAndUserRoleNotAssign(userRoleCode, sectionCode);
            sessionVarlist.setNotAssignUserRolePages(fromPageList);
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
