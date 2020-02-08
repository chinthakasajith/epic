/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.keymgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.switchcontrol.communicationkeys.bean.KeyBean;
import com.epic.cms.switchcontrol.keymgt.businesslogic.KeyManagementManager;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.socket.ConnectionToSecurityModule;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author asitha_l
 */
public class GenerateZMKKeyServlet extends HttpServlet {

    private SessionVarList sessionVarlist = null;
    HttpSession sessionObj = null;
    private SessionUser sessionUser = null;
    private ConnectionToSecurityModule connectionToSM = null;
    private String responseFromSM = null;
    private KeyBean keyBean = null;
    private KeyManagementManager keyMgtmanager;
    private SystemAuditBean systemAuditBean = null;

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

            String comKeyId = request.getParameter("cId");
            String channelName = request.getParameter("cName");

            connectionToSM = new ConnectionToSecurityModule();
            keyBean = new KeyBean();

            String requestToSM = StatusVarList.KEY_MAILER_PRIFIX + StatusVarList.KEY_TYPE_KTM + "CHANNEL - "+channelName;

            System.out.println("REQUEST: " + requestToSM);

            try {
                responseFromSM = connectionToSM.getServerMsg(requestToSM);
                System.out.println("responseFromSM >> " +responseFromSM);

                if (responseFromSM != null && responseFromSM.substring(2,4).equals("00")) {
                    keyBean.setZmk(responseFromSM.substring(4, 36));
                    keyBean.setZmkkvc(responseFromSM.substring(36));
                    keyBean.setId(comKeyId);

                    this.setAudittraceValue(request,comKeyId);
                    this.updateZMKKey(keyBean);
                    

                    out.print("ZMKsuccess," + keyBean.getZmk() + "," + keyBean.getZmkkvc());

                }
                else{
                    out.print(MessageVarList.UNSUCCESSFUL_RESPONSE);
                }
            } catch (Exception e) {
                out.print(MessageVarList.COMUNICATION_ERROR);
            }

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

    private boolean updateZMKKey(KeyBean keyBean) throws SQLException, Exception {
        
        boolean isSuccess = false;
        keyMgtmanager = new KeyManagementManager();
        isSuccess = keyMgtmanager.updateZMKKey(keyBean,systemAuditBean);
        return isSuccess;
        
    }
    
    /**
     * to set values to the audit trace
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request,String comKeyId) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(comKeyId);

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Generated channel key mailer by: " + sessionVarlist.getCMSSessionUser().getUserName()+".Communication key ID:'" + comKeyId + "'");
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.CHANNEL_KEY_MAILING);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
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
}
