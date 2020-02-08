/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.keymgt.maestro.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.switchcontrol.keymgt.businesslogic.KeyManagementManager;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.socket.ConnectionToSecurityModule;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
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
public class UpdateMaestroIMKPanelServlet extends HttpServlet {

   private RequestDispatcher rd;
    private ConnectionToSecurityModule securityModule = null;
    private String KVC;
    private KeyManagementManager keyMgtmanager;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private String url = "/switch/keymanagement/visakeymanagement.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnknownHostException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession sessionObj = request.getSession(false);
        try {
            systemUserManager = new SystemUserManager();
            sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
            sessionUser = sessionVarlist.getCMSSessionUser();
            String imk_ac = request.getParameter("imk_ac");

            if (imk_ac.length() == 32) {
                if (UserInputValidator.isHexxx(imk_ac)) {
                    securityModule = new ConnectionToSecurityModule();
                    try {
                        String KVC = securityModule.getServerMsg(StatusVarList.IMK_AC_PREFIX + imk_ac).substring(4);
                        keyMgtmanager = new KeyManagementManager();
                        this.setAudittraceValue(request);

                        String chanelId = keyMgtmanager.getChanelId(StatusVarList.CHANEL_TYPE_MAESTRO);

                        if (chanelId == null) {
                            out.print(MessageVarList.CHANEL_NOT_AVAILABLE);
                        } else {
                            if (keyMgtmanager.isChanelAvailable(chanelId)) {
                                keyMgtmanager.updateimk_ac(imk_ac, KVC, chanelId, systemAuditBean);
                            } else {
                                keyMgtmanager.addimk_ac(imk_ac, KVC, chanelId, systemAuditBean);
                            }
                            out.print("success," + KVC);
                        }


                        
                    } catch (Exception e) {
                        out.print(MessageVarList.COMUNICATION_ERROR);
                    } finally {
                    }

                } else {
//                    request.setAttribute("errorMsgzmk", MessageVarList.INVALID_ZMK);
                    out.print(MessageVarList.INVALID_ZMK);
//                    rd = request.getRequestDispatcher(url);
//                    rd.forward(request, response);
                }
            } else {
                out.print(MessageVarList.INVALID_ZMK);
//                request.setAttribute("errorMsgzmk", MessageVarList.INVALID_ZMK_LENGTH);
//                rd = request.getRequestDispatcher(url);
//                rd.forward(request, response);
            }

        } finally {
            out.close();
        }
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update MAESTRO DMK_AC and KVC by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.SWITCH_CONTROL_PANEL);
            systemAuditBean.setSectionCode(SectionVarList.KEY_MAMAGEMENT);
            systemAuditBean.setPageCode(PageVarList.VISA_KEY_MGT);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
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
