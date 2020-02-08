/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.EmbossFileFormatBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.EmbossFileFormatDetailBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.EmbossFileFormatMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author badrika
 */
public class LoadEmbossFileFormatMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private String url = "/administrator/controlpanel/systemconfigmgt/embossfileformat.jsp";
    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private List<String> userTaskList;
    private EmbossFileFormatMgtManager effmManager;
    private List<EmbossFileFormatBean> formatList = null;
    private List<StatusBean> statusList = null;    
    private List<EmbossFileFormatDetailBean> formatDetailList;
    private List<CardTypeMgtBean> cardTypeList;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessionObj = request.getSession(false);
        try {

            try {
                request.setAttribute("operationtype", "add");
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }
                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();
                }

                try {

                    String pageCode = PageVarList.EMBOSSFILEFORMAT;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    } else {
                        throw new AccessDeniedException();
                    }

                    sessionVarlist.setUserPageTaskList(userTaskList);

                } catch (AccessDeniedException adex) {
                    throw adex;
                }

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            ///////////////////
            this.getAllEmbossFileFormats();
            this.getAllCardTypeList();
            this.getStatus();
            this.getEmbossFileFormatDetail();
            
            request.setAttribute("formatList", formatList);
            
            sessionVarlist.setStatusList(statusList);
            sessionVarlist.setCdTypeList(cardTypeList);
            sessionVarlist.setFormatDetailList(formatDetailList);
            
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

            rd = request.getRequestDispatcher(url);


            //////////////////////


        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
        }
    }

    /**
     * to check the access is valid
     * @param userrole
     * @param pagecode
     * @param task
     * @return
     * @throws Exception 
     */
    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {

        boolean isValidTaskAccess = false;

        try {
            systemUserManager = new SystemUserManager();
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

    /**
     * to get all emboss file formats to user view
     * @throws Exception 
     */
    private void getAllEmbossFileFormats() throws Exception {
        try {

            effmManager = new EmbossFileFormatMgtManager();
            formatList = effmManager.getAllEmbossFileFormats();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to get status list to drop down
     * @throws Exception 
     */
    private void getStatus() throws Exception {
        try {

            effmManager = new EmbossFileFormatMgtManager();
            statusList = effmManager.getStatusList();

        } catch (Exception ex) {
            throw ex;
        }
    }
    
    /**
     * to get emboss file format details to drop down lists
     * @throws Exception 
     */
    private void getEmbossFileFormatDetail() throws Exception{
        try {

            effmManager = new EmbossFileFormatMgtManager();
            formatDetailList = effmManager.getEmbossFileFormatDetailList();

        } catch (Exception ex) {
            throw ex;
        }
    
    
    }
    
    /**
     * to get all card type list to drop down list
     * @throws Exception 
     */
    private void getAllCardTypeList() throws Exception {
        try {

            effmManager = new EmbossFileFormatMgtManager();
            cardTypeList = effmManager.getAllCardTypeList();

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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoadEmbossFileFormatMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoadEmbossFileFormatMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
