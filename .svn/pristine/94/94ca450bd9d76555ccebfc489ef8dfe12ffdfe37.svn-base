/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.keymailer.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.switchcontrol.keymailer.bean.KeyBean;
import com.epic.cms.switchcontrol.keymailer.bean.TerminalKeyMailerBean;
import com.epic.cms.switchcontrol.keymailer.businesslogic.TerminalKeyMailerManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
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
 * @author nalin
 */
public class ViewTerminalKeyMailerDataServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    HttpSession sessionObj = null;
    private SessionUser sessionUser = null;
    private List<String> userTaskList = null;
    private TerminalKeyMailerManager keyMailerManager = null;
    private TerminalKeyMailerBean keyMailerBean = null;
    private KeyBean keyBean = null;
    private String url = "/switch/keymailer/keymailer/terminalkeymailerview.jsp";

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
                String pageCode = PageVarList.KEY_MAILER;
                String taskCode = TaskVarList.VERIFY;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {



                    String terminalID = request.getParameter("terminalID");

                    this.verifyTerminalData(terminalID);
                    
                    this.getTMKKey(terminalID);

                    request.setAttribute("keyBean", keyBean);
                    request.setAttribute("keyMailerBean", keyMailerBean);
                    rd = getServletContext().getRequestDispatcher(url);

                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }

            } catch (AccessDeniedException adex) {
                throw adex;

            }

        }//catch session exception
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


        } catch (Exception ex) {

            request.setAttribute("errorMsg", MessageVarList.ERROR_SEARCH_KEY_MAILER);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
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

    private TerminalKeyMailerBean verifyTerminalData(String terminalID) throws Exception {

        try {
            keyMailerBean = new TerminalKeyMailerBean();
            keyMailerManager = new TerminalKeyMailerManager();

            keyMailerBean = keyMailerManager.verifyTerminalData(terminalID);


        } catch (Exception ex) {
            throw ex;
        }
        return keyMailerBean;

    }
    
    private KeyBean getTMKKey(String terminalID) throws Exception {

        try {
            keyBean = new KeyBean();
            keyMailerManager = new TerminalKeyMailerManager();

            keyBean = keyMailerManager.getTMKKey(terminalID);


        } catch (Exception ex) {
            throw ex;
        }
        return keyBean;

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
