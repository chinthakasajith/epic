/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.application.servlet.login;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
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
public class LogoutServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private SystemUserManager userManager = null;
    private SessionUser CMSSessionUser = new SessionUser();
    private SessionVarList sessionVarList = new SessionVarList();
    HttpSession sessionObj = null;
    private SystemAuditBean systemAuditBean;
    private RequestDispatcher rd = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {


            try {
                //invalidate the session
                sessionObj = request.getSession(false);
                sessionVarList = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                CMSSessionUser = sessionVarList.getCMSSessionUser();

                sessionObj.invalidate();
                try {
                    this.setAudittraceValue(request, sessionVarList.getLoggedApplicationCode());
                    userManager = new SystemUserManager();
                    userManager.insertAuditWhenLogin(systemAuditBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp");


            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }



        } catch (SesssionExpException ex) {
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            rd.forward(request, response);
            out.close();
        }
    }

    private void setAudittraceValue(HttpServletRequest request, String applicationCode) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarList.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarList.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
             systemAuditBean.setDescription("Logout from the system systemuser:" + sessionVarList.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.DEFAULTAPPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.DEFAULTSECTION);
            systemAuditBean.setPageCode(PageVarList.DEFAULTPAGE);
            systemAuditBean.setTaskCode(TaskVarList.LOGOUT);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue("");


            systemAuditBean.setLastUpdateduser(sessionVarList.getCMSSessionUser().getUserName());

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
