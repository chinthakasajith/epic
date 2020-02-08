/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.application.servlet.login;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
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
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author janaka_h
 */
public class ApplicationLogin extends HttpServlet {

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
    private Map<SectionBean, List<PageBean>> sectionPageList;
    HttpSession sessionObj = null;
    private SystemAuditBean systemAuditBean;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            try {

                HttpSession sessionObj = request.getSession(false);

                sessionVarList = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                CMSSessionUser = sessionVarList.getCMSSessionUser();
            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            String applicationType = request.getParameter("appCode");


            this.getSectionPage(CMSSessionUser.getUserRole(), applicationType);
            CMSSessionUser.setSectionPageList(sectionPageList);
            sessionVarList.setCMSSessionUser(CMSSessionUser);
            sessionVarList.setLoggedApplicationCode(applicationType);
            

//            try {
//                userManager = new SystemUserManager();
//                this.setAudittraceValue(request, applicationType);
//                userManager.insertAuditWhenLogin(systemAuditBean);
//
//            } catch (Exception ex) {
//                
//            }

            if (applicationType.equals(ApplicationVarList.CUSTOMER_SERVICE)) {
                response.sendRedirect(request.getContextPath() + "/LoadCallCenterMgtServlet");
            }else if(applicationType.equals(ApplicationVarList.AQ_CUSTOMER_SERVIVE)){
                response.sendRedirect(request.getContextPath() + "/LoadAccquirCallCenterMgtServlet");
            } else {
                response.sendRedirect(request.getContextPath() + "/administrator/controlpanel/controlpanelhome.jsp");
            }

        } catch (SesssionExpException sex) {
            //redirect to login page
             response.sendRedirect(request.getContextPath() +"/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        }           
         catch (Exception ex) {
            System.out.println(">>>>>>>>>>>>>>" + ex);
        } finally {
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
            systemAuditBean.setDescription("Login to the apllication: '" + applicationCode + "' by :" + sessionVarList.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(applicationCode);
            systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.SYSTEMUSER);
            systemAuditBean.setTaskCode(TaskVarList.LOGIN);
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

    private Map<SectionBean, List<PageBean>> getSectionPage(String userRole, String applicationType) throws Exception {


        userManager = new SystemUserManager();
        sectionPageList = userManager.getSectionPage(userRole, applicationType);
        return sectionPageList;
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
