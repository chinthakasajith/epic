/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.chanelconfig.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelAdminMessagesBean;

import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelConfigBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ConnectionTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.StatusTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.businesslogic.ChannelAdminMessageManager;
import com.epic.cms.switchcontrol.chanelconfig.businesslogic.ChannelConfigManager;
import com.epic.cms.switchcontrol.listenerconfig.businesslogic.ListenerConfigurationManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
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
public class LoadChannelAdminMessagesServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    //private ChannelConfigManager channelManager;
    private List<ChannelConfigBean> channelList;
    private List<String> userTaskList;
    private ListenerConfigurationManager listenerManager = null;
    private String url = "/switch/channelconfig/channelconfig/channeladminmessages.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sessionObj = request.getSession(false);
        // List<ChannelConfigBean> typeList = null;
        try {
            try {
                request.setAttribute("operationtype", "ADD");

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
                    //set page code and task codes
                    String pageCode = PageVarList.CHANNELADMINMESSAGE;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        ChannelAdminMessagesBean channelAdminMessageInfo = new ChannelAdminMessagesBean();
                        ChannelAdminMessageManager serverDetailManager = new ChannelAdminMessageManager();
//                        ConnectionToETMRouter connectionToETM = new ConnectionToETMRouter();


                        try {
                            channelAdminMessageInfo = serverDetailManager.getServerInfo();


//                            serverStatus = connectionToETM.getServerMsg(StatusVarList.SERVER_STATUS);
//                            String status = serverStatus.substring(8, 10);
//
//                            if (status.equals("00")) {
//                                request.setAttribute("status", "1");
//                            } else {
//                                request.setAttribute("status", "0");
//                            }

                            request.setAttribute("channelAdminMessageInfo", channelAdminMessageInfo);

                        } catch (Exception e) {
                            request.setAttribute("channelAdminMessageInfo", channelAdminMessageInfo);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }

                        //rd = getServletContext().getRequestDispatcher(url);
                        //rd.forward(request, response);

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

            ///////////////////////////////////////////////
            ///// get Connection Status list
            ChannelConfigManager chanObj1 = new ChannelConfigManager();
            List<ConnectionTypeBean> connectionTypeList = chanObj1.getConnectionTypeStatus();
            sessionVarlist.setConnectionTypeList(connectionTypeList);

            ///// get Channel Status list
            ChannelConfigManager chanObj2 = new ChannelConfigManager();
            List<ChannelTypeBean> channelTypeList = chanObj2.getChannelTypeStatus();
            sessionVarlist.setChannelTypeList(channelTypeList);

            ///// get  Statustype list
            ChannelConfigManager chanObj3 = new ChannelConfigManager();
            List<StatusTypeBean> statusTypeList = chanObj3.getStatusType();
            sessionVarlist.setStatusTypeList(statusTypeList);

            // get Enable Disable Status 
            ChannelConfigManager chanObj4 = new ChannelConfigManager();
            List<StatusTypeBean> statusEDTypeList = chanObj4.getStatusEDType();
            request.setAttribute(RequestVarList.STATUS_EDTYPE_LIST, statusEDTypeList);
            //rd = request.getRequestDispatcher(url);

            //// get key Id List
            listenerManager = new ListenerConfigurationManager();
            HashMap<String, String> keyId = listenerManager.getKeyId(StatusVarList.CHANNEL_COMMUNICATION_TYPE);
            sessionVarlist.setKeyId(keyId);

            ChannelConfigManager ccm = new ChannelConfigManager();

            //get transaction type details
            channelList = ccm.getChannel();
            request.setAttribute(RequestVarList.CHANNEL_LIST, channelList);
            rd = request.getRequestDispatcher(url);

            /////////////////////////////////////////////////
        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);

        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.ACCESS_DENIED_TASK);
            //rd.forward(request, response);

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
     *
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
