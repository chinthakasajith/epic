/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfig.creditscore.bean.ApplicationCreditScoreBean;
import com.epic.cms.camm.applicationconfirmation.businesslogic.ApplicationConfirmationManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
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
 * @author mahesh_m
 */
public class ViewBreakDownServlet extends HttpServlet {

    private ApplicationConfirmationManager manager;
    private SystemAuditBean sysAuditInfo;
    private SystemAuditBean searchBean;
    private List<ApplicationCreditScoreBean> breakDownList;
    private SessionVarList sessionVarlist;
    private RequestDispatcher rd;
    private String fromDate;
    private String toDate;
    private SessionUser sessionUser;
    private SystemUserManager systemUserManager = null;
    private SystemAuditManager systemAuditManager = null;
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
            
            String applicationId = request.getParameter("breakDown");  
              
             this.getBreakDownValues(applicationId);
             
             sessionObj.setAttribute("breakDownList", breakDownList);
             

            /////////////////////////////////////////////////////////////////////



            out.print("success");



            //////////////////////////////////////////////////////////////////
        } //catch session exception
        catch (SesssionExpException sex) {
   
            out.print("session");


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            out.print("session");

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            String errMessage = MessageVarList.ACCESS_DENIED_PAGETASK;
            request.setAttribute("errorMsg", errMessage);
            rd = getServletContext().getRequestDispatcher("/LoadSystemAudittraceServlet");
            rd.include(request, response);


        } catch (SQLException ex) {
            //when throw an sql exception
            out.print("error");
        } catch (Exception ex) {
            System.out.println(ex);
            out.print("error");
        } finally {
            out.close();
        }
    }

   

    private void getBreakDownValues(String applicationId) throws Exception {

        try {
            breakDownList = new ArrayList<ApplicationCreditScoreBean>();
            manager = new ApplicationConfirmationManager();
            breakDownList = manager.getBreakDownValues(applicationId);



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
