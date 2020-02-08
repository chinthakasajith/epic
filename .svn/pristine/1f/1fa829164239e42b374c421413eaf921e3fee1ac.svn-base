/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.keymailer.servlet;

import com.epic.cms.switchcontrol.keymailer.bean.KeyBean;
import com.epic.cms.switchcontrol.keymailer.businesslogic.TerminalKeyMailerManager;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.socket.ConnectionToSecurityModule;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nalin
 */
public class GenerateTPKKeyServlet extends HttpServlet {
    
     private SessionVarList sessionVarlist = null;
    HttpSession sessionObj = null;
    private SessionUser sessionUser = null;
    private TerminalKeyMailerManager keyMailerManager = null;
    private KeyBean keyBean = null;
    private ConnectionToSecurityModule connectionToSM = null;
    private String responseFromSM = null;
    // String url = "/switch/keymailer/keymailer/terminalkeymailerview.jsp";

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



            String LMK_KTM = request.getParameter("tmk");
           

            if (LMK_KTM.length()==32) {

                connectionToSM = new ConnectionToSecurityModule();
                keyBean = new KeyBean();

                String requestToSM = StatusVarList.WORKING_KEY_PRIFIX + LMK_KTM;


                try {
                    responseFromSM = connectionToSM.getServerMsg(requestToSM);

                    if (responseFromSM != null) {

                        keyBean.setELMK_KEY(responseFromSM.substring(4, 36));
                        keyBean.setTpk(responseFromSM.substring(36, 68));
                        keyBean.setTpkKVC(responseFromSM.substring(68));
                        keyBean.settId(sessionVarlist.getTerminalId());

                        this.updateTPKKey(keyBean);

                        out.print("TPKsuccess," + keyBean.getTpk() + "," + keyBean.getTpkKVC());

                    }
                } catch (Exception e) {
                    out.print(MessageVarList.COMUNICATION_ERROR);
                }

            } else {
                out.print("LMK_KTM_LENGTH_ERROR");
            }

        } finally {
            out.close();
        }
    }
    
     private boolean updateTPKKey(KeyBean keyBean) throws Exception {
        boolean isSuccess = false;
        try {
            keyMailerManager = new TerminalKeyMailerManager();

            keyBean.setLastUpdatedUser(sessionUser.getUserName());
            isSuccess = keyMailerManager.updateTPKKey(keyBean);

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
