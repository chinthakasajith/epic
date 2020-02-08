/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.cardkeys.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.switchcontrol.cardkeys.bean.CardKeybean;
import com.epic.cms.switchcontrol.cardkeys.businesslogic.CardKeyManager;
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
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author asela
 */
public class UpdateAtmCardCVK2BPanelServlet extends HttpServlet {

    private RequestDispatcher rd;
    private ConnectionToSecurityModule securityModule = null;
    private String KVC;
    private CardKeyManager cardKeyManager;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private boolean successUpdate;
    private CardKeybean keyBean;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sessionObj = request.getSession(false);        
        try {

            systemUserManager = new SystemUserManager();
            sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
            sessionUser = sessionVarlist.getCMSSessionUser();
            
            String cvk2b = request.getParameter("cvk2b");    
            
            if (cvk2b.length() == 16) {
                if (UserInputValidator.isHexxx(cvk2b)) {
                    securityModule = new ConnectionToSecurityModule();
                    try {
                        String KVC = securityModule.getServerMsg(StatusVarList.CVK_PREFIX + cvk2b).substring(4);                 
                        this.setAudittraceValue(request);

                        String id = request.getParameter("id");
                        keyBean = new CardKeybean();
                        keyBean.setCvk2b(cvk2b);
                        keyBean.setCvk2bKVC(KVC);
                        keyBean.setId(id);

                        successUpdate = this.updateCVK2BKeyDetails(keyBean);
                        if (successUpdate) {
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
                out.print(MessageVarList.INVALID_ZMK_LENGTH);
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
            systemAuditBean.setDescription("Update CVK2B and CVK2B KVC by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.SWITCH_CONTROL_PANEL);
            systemAuditBean.setSectionCode(SectionVarList.KEY_MAMAGEMENT);
            systemAuditBean.setPageCode(PageVarList.ATM_KEY_MGT);
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

    public boolean updateCVK2BKeyDetails(CardKeybean keyBean) throws Exception {
        boolean success = false;
        try {
            cardKeyManager = new CardKeyManager();
            success = cardKeyManager.updateCVK2BKeyDetails(keyBean, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
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
