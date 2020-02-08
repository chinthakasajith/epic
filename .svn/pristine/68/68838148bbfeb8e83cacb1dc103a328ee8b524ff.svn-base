/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
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
 * @author mahesh_m
 */
public class AddRemarksForSupplemantaryServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private ApplicationCheckingManager checkingmanager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private TaskBean taskBean;
    private String errorMessage = null;
    private boolean successInsert = false;
    private SystemAuditBean systemAuditBean = null;
    private String url = "/camm/applicationchecking/supplementaryapplicationchecking.jsp";
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
            String applicationId = request.getParameter("applicationId");
            try {

                String tab = request.getParameter("tab");
                String remark = null;

                checkingmanager = new ApplicationCheckingManager();

                if (tab.equals("1")) {
                    remark = request.getParameter("remark1");
                    if (remark == null || remark.equals("")) {
                        request.setAttribute("selectedtab", "0");
                        request.setAttribute("errorMsg", MessageVarList.FILE_CHECKEDIN_REMARK_EMPTY);
//                         rd = getServletContext().getRequestDispatcher("/LoadSupllimentaryApplicationCheckingServlet1?applicationid=" + applicationId);
//                        rd.forward(request, response);
                    } else {
                        checkingmanager.addRemarks(applicationId, "1", remark, sessionUser.getUserName());
                        request.setAttribute("selectedtab", "1");
                        request.setAttribute("successMsg", MessageVarList.REMARK);
                    }

                } else if (tab.equals("2")) {
                    remark = request.getParameter("remark2");

                    if (remark == null || remark.equals("")) {
                        request.setAttribute("selectedtab", "1");
                        request.setAttribute("errorMsg", MessageVarList.FILE_CHECKEDIN_REMARK_EMPTY);
//                        rd = getServletContext().getRequestDispatcher("/LoadSupllimentaryApplicationCheckingServlet1?applicationid=" + applicationId);
//                        rd.forward(request, response);
                    } else {
                        checkingmanager.addRemarks(applicationId, "2", remark, sessionUser.getUserName());
                        request.setAttribute("selectedtab", "2");
                        request.setAttribute("successMsg", MessageVarList.REMARK);
                    }

                } else if (tab.equals("3")) {
                    remark = request.getParameter("remark3");
                    if (remark == null || remark.equals("")) {
                        request.setAttribute("selectedtab", "2");
                        request.setAttribute("errorMsg", MessageVarList.FILE_CHECKEDIN_REMARK_EMPTY);
//                        rd = getServletContext().getRequestDispatcher("/LoadSupllimentaryApplicationCheckingServlet1?applicationid=" + applicationId);
//                        rd.forward(request, response);
                    } else {
                        checkingmanager.addRemarks(applicationId, "3", remark, sessionUser.getUserName());
                        request.setAttribute("selectedtab", "3");
                        request.setAttribute("successMsg", MessageVarList.REMARK);
                    }

                }

                rd = getServletContext().getRequestDispatcher("/LoadSupllimentaryApplicationCheckingServlet1?applicationid=" + applicationId);
                rd.forward(request, response);

            } catch (Exception e) {
                request.setAttribute("operationtype", "default");
                request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            }



        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);

        } finally {
            out.close();
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
