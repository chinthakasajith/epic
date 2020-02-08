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
import com.epic.cms.system.util.json.JSONObject;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.socket.ConnectionToSecurityModule;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
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
public class GenerateTMKKeyServlet extends HttpServlet {

    private SessionVarList sessionVarlist = null;
    HttpSession sessionObj = null;
    private SessionUser sessionUser = null;
    private TerminalKeyMailerManager keyMailerManager = null;
    private KeyBean keyBean = null;
    private ConnectionToSecurityModule connectionToSM = null;
    private String responseFromSM = null;
    //private String url = "/switch/keymailer/keymailer/terminalkeymailerview.jsp";

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

        sessionObj = request.getSession(false);
        try {

            sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
            sessionUser = sessionVarlist.getCMSSessionUser();

            String terminalID = request.getParameter("tId");
            String merchantID = request.getParameter("mId");

            String description = terminalID+"|"+ merchantID;

            sessionVarlist.setTerminalId(terminalID);

            if (description.length() <= 30) {

                connectionToSM = new ConnectionToSecurityModule();
                keyBean = new KeyBean();

                String requestToSM = StatusVarList.KEY_MAILER_PRIFIX + StatusVarList.KEY_TYPE_KTM + description;
                
               System.out.println("REQUEST: "+requestToSM);

                try {
                    responseFromSM = connectionToSM.getServerMsg(requestToSM);

                    if (responseFromSM != null) {

                        keyBean.setTmk(responseFromSM.substring(4, 36));
                        keyBean.setTmkKVC(responseFromSM.substring(36));
                        keyBean.settId(terminalID);

                        this.updateTMKKey(keyBean);

                        out.print("TMKsuccess," + keyBean.getTmk() + "," + keyBean.getTmkKVC());

                    }
                } catch (Exception e) {
                    out.print(MessageVarList.COMUNICATION_ERROR);
                }

            } else {
                out.print("INCORRECT_TERMINAL_OR_MERCHANT_ID");
            }

        } finally {
            out.close();
        }
    }

    private boolean updateTMKKey(KeyBean keyBean) throws Exception {
        boolean isSuccess = false;
        try {
            keyMailerManager = new TerminalKeyMailerManager();

            keyBean.setLastUpdatedUser(sessionUser.getUserName());
            isSuccess = keyMailerManager.updateTMKKey(keyBean);

        } catch (Exception ex) {
            throw ex;
        }
        return isSuccess;
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
