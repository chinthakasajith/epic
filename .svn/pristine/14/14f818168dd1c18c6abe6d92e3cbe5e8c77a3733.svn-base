/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.serverconfig.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
import com.epic.cms.switchcontrol.serverconfig.bean.ServerMainConfigBean;
import com.epic.cms.switchcontrol.serverconfig.businesslogic.ServerMainConfigManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * @author nisansala
 */
public class LoadServermainConfigServlet extends HttpServlet {

    //initializing variables
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager sysUserMgr;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private List<StatusBean> statusList;
    private List<String> userTaskList;
    private RequestDispatcher rd;
    //
    ServerMainConfigBean serverConfigBean;
    ServerMainConfigBean srvrConfigBean;
    ServerMainConfigManager serverMainConfigManager;
    private HashMap<String, String> pinbFormat = null;
    private HashMap<String, String> emvVerifyMethod = null;
    private HashMap<String, String> encrMode = null;
    private HashMap<String, String> enableMode = null;
    private List<ChannelTypeBean> onlineChannelList = null;
    private Map<String, String> channelStatus;
    private String url = "/switch/serverconfig/servermainconfig/servermainconfighome.jsp";

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, AccessDeniedException, SesssionExpException, NewLoginSessionException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            sessionObj = request.getSession(false);
            try {
                request.setAttribute("operationType", "add");

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
                try {
                    //set page code and task codes
                    String pageCode = PageVarList.SERVERMAINCONFIG;
                    String taskCode = TaskVarList.ACCESSPAGE;


                    //check whether userrole has access to this page for required task
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        try {

                            request.setAttribute("operationtype", "add");
                            this.getAllStatus(StatusVarList.GENESTATUSCAT);
                            sessionVarlist.setStatusList(statusList);
                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                            srvrConfigBean = new ServerMainConfigBean();
                            //retrieve data from ECMS_ONLINE_CONFIG table
                            srvrConfigBean = this.getDataFromServerConfigTable();
                            //retrieve all pinb formats
                            this.getPinbFormat();
                            //retrieve all emv verify methods
                            this.getEmvVerifyMethod();
                            //retrieve all encr modes
                            this.getEncrMode();
                            //retrieve enable or disable
                            this.getEnableMode();
                            //retrieve active channels
                            this.getChannels();
                            // retrieve all channel status
                            this.getChannelStatus();


                            if (srvrConfigBean != null) {
                                request.setAttribute("operationtype", "add");
                                request.setAttribute("srvrConfigBean", srvrConfigBean);
                                request.setAttribute("pinbFormat", pinbFormat);
                                request.setAttribute("emvVerifyMethod", emvVerifyMethod);
                                request.setAttribute("encrMode", encrMode);
                                request.setAttribute("enable", enableMode);
                                request.setAttribute("status", channelStatus);
                            }
                            rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);

                        } catch (Exception e) {

                            request.setAttribute("operationtype", "add");
                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);

                        }
                    } else {

                        //if invalid throw access denied exception
                        throw new AccessDeniedException();

                    }

                    //set tasks to the session
                    sessionVarlist.setUserPageTaskList(userTaskList);


                } catch (AccessDeniedException adex) {

                    throw adex;

                }
            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
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

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
        }
    }

    private void getAllStatus(String categoryCode) throws Exception {
        try {
            sysUserMgr = new SystemUserManager();
            statusList = sysUserMgr.getStatusByUserRole(categoryCode);
        } catch (Exception e) {
            throw e;
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

    private ServerMainConfigBean getDataFromServerConfigTable() throws Exception {
        try {

            srvrConfigBean = new ServerMainConfigBean();
            serverMainConfigManager = new ServerMainConfigManager();
            srvrConfigBean = serverMainConfigManager.getAllServerMainConfigDetails();
            return srvrConfigBean;
        } catch (Exception e) {
            throw e;
        }
    }

    private void getPinbFormat() throws Exception {
        try {
            serverMainConfigManager = new ServerMainConfigManager();
            pinbFormat = serverMainConfigManager.getPinbFormat();

        } catch (Exception e) {
            throw e;
        }

    }

    private void getEmvVerifyMethod() throws Exception {
        try {
            serverMainConfigManager = new ServerMainConfigManager();
            emvVerifyMethod = serverMainConfigManager.getEmvVerifyMethod();

        } catch (Exception e) {
            throw e;
        }

    }

    private void getEncrMode() throws Exception {
        try {
            serverMainConfigManager = new ServerMainConfigManager();
            encrMode = serverMainConfigManager.getEncrMode();

        } catch (Exception e) {
            throw e;
        }

    }

    private void getEnableMode() throws Exception {
        try {
            serverMainConfigManager = new ServerMainConfigManager();
            enableMode = serverMainConfigManager.getEnableMode();

        } catch (Exception e) {
            throw e;
        }

    }

    private void getChannels() throws Exception {
        try {
            serverMainConfigManager = new ServerMainConfigManager();
            onlineChannelList = serverMainConfigManager.getOnlineChannel();
            sessionVarlist.setChannelTypeList(onlineChannelList);

        } catch (Exception e) {
            throw e;
        }

    }
    private void getChannelStatus() throws Exception {
        try {
            serverMainConfigManager = new ServerMainConfigManager();
            channelStatus = serverMainConfigManager.getOnlineChannelStatusMap();


        } catch (Exception e) {
            throw e;
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
        } catch (AccessDeniedException ex) {
            Logger.getLogger(LoadServermainConfigServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SesssionExpException ex) {
            Logger.getLogger(LoadServermainConfigServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NewLoginSessionException ex) {
            Logger.getLogger(LoadServermainConfigServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (AccessDeniedException ex) {
            Logger.getLogger(LoadServermainConfigServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SesssionExpException ex) {
            Logger.getLogger(LoadServermainConfigServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NewLoginSessionException ex) {
            Logger.getLogger(LoadServermainConfigServlet.class.getName()).log(Level.SEVERE, null, ex);
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
