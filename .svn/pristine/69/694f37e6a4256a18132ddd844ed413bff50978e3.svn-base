/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.chanelconfig.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelAdminMessagesBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelConfigBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.StatusTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.businesslogic.ChannelAdminMessageManager;
import com.epic.cms.switchcontrol.chanelconfig.businesslogic.ChannelConfigManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
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
 * @author nipun_t
 */
public class SendChannelAdminMessagesServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private ChannelAdminMessageManager channelManager = null;
    private List<ChannelConfigBean> channelList;
    private String errorMessage = null;
    private ChannelAdminMessagesBean channelBean;
    private int successInsert ;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private String url = "/switch/channelconfig/channelconfig/channeladminmessages.jsp";
    private String newValue;

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
        try {
            //call to existing session
            /////////////////////////////////////////////////////////////////////
            sessionObj = request.getSession(false);
            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                //check system user is in same session or not
                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        //user not in same session.
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
                String pageCode = PageVarList.CHANNELADMINMESSAGE;
                String taskCode = TaskVarList.ADD;

                /////get value for states
                ChannelConfigManager chanObj = new ChannelConfigManager();
                List<StatusTypeBean> statusEDTypeList = chanObj.getStatusEDType();
                request.setAttribute(RequestVarList.STATUS_EDTYPE_LIST, statusEDTypeList);
                rd = request.getRequestDispatcher(url);

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {

                        setUserInputToBean(request);
                        request.setAttribute("channelAdminMessageInfo", channelBean);
                        if (validateUserInput(channelBean)) {
                            request.setAttribute("operationtype", "ADD");

                            this.setAudittraceValue(request);

                            try {
                                successInsert = channelInsert(channelBean, systemAuditBean);

                                switch (successInsert) {
                                    case 0:
                                        request.setAttribute("successMsg", MessageVarList.CHANNEL_ADMIN_MSG_SUCCESS);
                                        break;
                                    case 1:
                                        request.setAttribute("errorMsg", MessageVarList.CHANNEL_ADMIN_MSG_FAIL);
                                        break;
                                    case 2:
                                        request.setAttribute("errorMsg", MessageVarList.CHANNEL_ADMIN_MSG_INVALID_REQUEST);
                                    case 3:
                                        request.setAttribute("errorMsg", MessageVarList.CHANNEL_ADMIN_MSG_ONLINE_SERVER_NOT_AVAILABLE);
                                        break;
                                    case 4:
                                        request.setAttribute("errorMsg", MessageVarList.CHANNEL_ADMIN_MSG_INVALID_ROUTER_TO_ENGINE);
                                        break;
                                    case 5:
                                        request.setAttribute("errorMsg", MessageVarList.CHANNEL_ADMIN_MSG_INVALID_CONNECTION_TYPE);
                                        break;
                                    case 6:
                                        request.setAttribute("errorMsg", MessageVarList.CHANNEL_ADMIN_MSG_INVALID_REQ_RESPONSE);
                                    case 7:
                                        request.setAttribute("errorMsg", MessageVarList.CHANNEL_ADMIN_MSG_INVALID_SOURSE_TYPE);
                                        break;
                                    case 8:
                                        request.setAttribute("errorMsg", MessageVarList.CHANNEL_ADMIN_MSG_INVALID_OPERATION_CODE);
                                        break;
                                    case 9:
                                        request.setAttribute("errorMsg", MessageVarList.CHANNEL_ADMIN_MSG_INVALID_WEROUTER_MESSAGE);
                                        break;
                                }
                                rd = getServletContext().getRequestDispatcher(url);

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());

                                request.setAttribute("operationtype", "ADD");
                                request.setAttribute("errorMsg", oraMessage);
                                rd = getServletContext().getRequestDispatcher(url);

                            }

                        } else {
                            request.setAttribute("operationtype", "ADD");
                            request.setAttribute("channelBean", channelBean);
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);

                        }

                        ChannelConfigManager ccm = new ChannelConfigManager();
                        //get transaction type to table details
                        channelList = ccm.getChannel();
                        request.setAttribute(RequestVarList.CHANNEL_LIST, channelList);
                        rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "ADD");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }

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
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            out.close();
        }
    }

    /**
     * set uesr input from jsp to bean
     *
     * @param request
     * @return
     * @throws Exception
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = true;
        try {

            channelBean = new ChannelAdminMessagesBean();
            String ip = request.getParameter("ip");
            String port = request.getParameter("port").trim();
            String timeOut = request.getParameter("timeOut").trim();
            String connectionType = request.getParameter("connectionType").trim();
            String operationType = request.getParameter("selectOperationType").trim();

            channelBean.setIp(ip);
            channelBean.setPort(port);
            channelBean.setTimeOut(timeOut);
            channelBean.setConnectionType(connectionType);
            channelBean.setOperationType(operationType);

            channelBean.setLastUpdateUser(sessionUser.getUserName());

            newValue = channelBean.getIp() + "|"
                    + channelBean.getPort() + "|" + channelBean.getTimeOut() + "|" + channelBean.getConnectionType() + "|" + channelBean.getOperationType();

        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    /**
     * Validate the New User Entry
     *
     * @param txnTypeBean
     * @return
     * @throws Exception
     */
    public boolean validateUserInput(ChannelAdminMessagesBean channelBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code
        try {

            if (channelBean.getIp().contentEquals("") || channelBean.getIp().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.IP_NULL;

            } else if (!UserInputValidator.isCorrectIp(channelBean.getIp())) {
                isValidate = false;

                errorMessage = MessageVarList.IP_INVALID;

            } else if (channelBean.getPort().contentEquals("") || channelBean.getPort().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.PORT_NULL;

            } else if (!UserInputValidator.isCorrectPort(channelBean.getPort())) {
                isValidate = false;

                errorMessage = MessageVarList.PORT_INVALID;

            } else if (channelBean.getTimeOut().contentEquals("") || channelBean.getTimeOut().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.TIMEOUT_NULL;

            } else if (!UserInputValidator.isNumeric(channelBean.getTimeOut())) {
                isValidate = false;

                errorMessage = MessageVarList.TIMEOUT_INVALID;

            } else if (channelBean.getConnectionType().contentEquals("") || channelBean.getConnectionType().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.CONNECTION_TYPE_NULL;

            } else if (channelBean.getOperationType().contentEquals("") || channelBean.getOperationType().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.OPERATION_TYPE_NULL;

            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    /**
     *
     * @param type
     * @return
     * @throws Exception
     */
    public int channelInsert(ChannelAdminMessagesBean channelBean, SystemAuditBean systemAuditBean) throws Exception {
        int success;
        try {

            /// call insertTxntypeDetails method in TxnTypeMgtManager class
            channelManager = new ChannelAdminMessageManager();
            success = channelManager.sendReq(channelBean, systemAuditBean);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return success;
    }

    /**
     *
     * @param request
     * @throws Exception
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set description 
            systemAuditBean.setDescription("Send Operation : " + channelBean.getOperationType() + ";  by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.SWITCH_CONTROL_PANEL);
            systemAuditBean.setSectionCode(SectionVarList.CHANEL_CINFIG);
            systemAuditBean.setPageCode(PageVarList.CHANNELADMINMESSAGE);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue(newValue);

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
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
