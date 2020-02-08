/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.chanelconfig.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelConfigBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.StatusTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.businesslogic.ChannelConfigManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
public class DeleteChanelConfigurationServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private ChannelConfigManager channelManager = null;
    private List<ChannelConfigBean> channelList;
    private String errorMessage = null;
    private String channelId;
    private ChannelConfigBean channelBean;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private String url = "/switch/channelconfig/channelconfig/channelconfighome.jsp";
    private String oldValue;

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

            try {
                //set page code and task codes
                String pageCode = PageVarList.CHANNEL;
                String taskCode = TaskVarList.DELETE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                    ChannelConfigManager chanObj = new ChannelConfigManager();
                    List<StatusTypeBean> statusEDTypeList = chanObj.getStatusEDType();
                    request.setAttribute(RequestVarList.STATUS_EDTYPE_LIST, statusEDTypeList);
                    rd = request.getRequestDispatcher(url);

                    channelId = request.getParameter("channelId");
                    channelBean = new ChannelConfigBean();
                    channelBean.setChannelId(channelId);

                    ChannelConfigManager chnl = new ChannelConfigManager();
                    List<ChannelConfigBean> newChannelList = chnl.getChannel();
                    for (ChannelConfigBean newChannelBean : newChannelList) {

                        if (newChannelBean.getChannelId().equals(channelId)) {

                            oldValue = newChannelBean.getChannelId() + "|" + newChannelBean.getChannelName() + "|" + newChannelBean.getIp() + "|"
                                    + newChannelBean.getPort() + "|" + newChannelBean.getTimeOut() + "|" + newChannelBean.getConnectionType() + "|" + newChannelBean.getChannelType() + "|"
                                    + newChannelBean.getEchoStatus() + "|" + newChannelBean.getSignonStatus() + "|" + newChannelBean.getDynamicKeyExchangeStatus() + "|"
                                    + newChannelBean.getDynamicKeyExchangePeriod() + "|" + newChannelBean.getEchoPeriod() + "|" + newChannelBean.getEchoDirection() + "|"
                                    + newChannelBean.getKeyExchangeDirection() + "|" + newChannelBean.getStatus() + "|" + newChannelBean.getKeyId() + "|"
                                    + newChannelBean.getHdesId() + "|" + newChannelBean.getHsrcId() + "|" + newChannelBean.getAscii32Id() + "|" + newChannelBean.getFiic33Id();

                        }

                    }

                    this.setAudittraceValue(request);

                    try {
                        boolean delResult = deleteChannel(channelBean, systemAuditBean);

                        if (delResult) {
                            request.setAttribute("successMsg", MessageVarList.CHANNEL_SUCCESS_DELETE + " " + channelId);
                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                            request.setAttribute("operationtype", "ADD");
                            rd = getServletContext().getRequestDispatcher("/LoadChanelConfigurationServlet");

                        }




                    } catch (SQLException e) {
                        OracleMessage message = new OracleMessage();
                        String oraMessage = message.getMessege(e.getMessage());

                        request.setAttribute("operationtype", "ADD");
                        request.setAttribute("errorMsg", oraMessage);
                        rd = getServletContext().getRequestDispatcher(url);
                        //rd.forward(request, response);
                    } catch (Exception ee) {
                        request.setAttribute("operationtype", "ADD");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        //rd.forward(request, response);
                    }

                    ChannelConfigManager ccm = new ChannelConfigManager();
                    //get transaction type to table details
                    channelList = ccm.getChannel();
                    request.setAttribute(RequestVarList.CHANNEL_LIST, channelList);
                    rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);


                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }


                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                throw adex;

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

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {
        } finally {
            out.close();
        }
    }

    /**
     *  do audit trace
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------

            String uniqueId = request.getParameter(channelBean.getChannelId());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            //systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Delete Channel Channel Id: " + channelBean.getChannelId() + ";  by : " + sessionVarlist.getCMSSessionUser().getUserName());

            systemAuditBean.setApplicationCode(ApplicationVarList.SWITCH_CONTROL_PANEL);
            systemAuditBean.setSectionCode(SectionVarList.CHANEL_CINFIG);
            systemAuditBean.setPageCode(PageVarList.CHANNEL);
            systemAuditBean.setTaskCode(TaskVarList.DELETE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(oldValue);
            //set new value of change if required
            systemAuditBean.setNewValue("");


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }

    }

    /**
     * 
     * @param txnType
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean deleteChannel(ChannelConfigBean channelBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            channelManager = new ChannelConfigManager();
            success = channelManager.deleteChannel(channelBean, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
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
