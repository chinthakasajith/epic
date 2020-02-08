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
 * @author nalin
 */
public class AddChanelConfigurationServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private ChannelConfigManager channelManager = null;
    private List<ChannelConfigBean> channelList;
    private String errorMessage = null;
    private ChannelConfigBean channelBean;
    private boolean successInsert = false;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private String url = "/switch/channelconfig/channelconfig/channelconfighome.jsp";
    private String newValue;

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
                String pageCode = PageVarList.CHANNEL;
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
                        if (validateUserInput(channelBean)) {
                            request.setAttribute("operationtype", "ADD");

                            this.setAudittraceValue(request);

                            try {
                                successInsert = channelInsert(channelBean, systemAuditBean);

                                if (successInsert) {

                                    request.setAttribute("successMsg", MessageVarList.CHANNEL_SUCCESS_ADD + " " + channelBean.getChannelId());

                                    rd = getServletContext().getRequestDispatcher(url);

                                }

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
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = true;
        try {

            channelBean = new ChannelConfigBean();

            String channelId = request.getParameter("channelId").trim();
            String channelName = request.getParameter("channelName").trim();
            String ip = request.getParameter("ip");
            String port = request.getParameter("port").trim();
            String timeOut = request.getParameter("timeOut").trim();
            String connectionType = request.getParameter("selectConnectionType").trim();
            String channelType = request.getParameter("selectChannelType").trim();
            String echoStatus = request.getParameter("selectEchoStatus").trim();
            String signonStatus = request.getParameter("selectSingonStatus").trim();
            String dynamicKeyExchangeStatus = request.getParameter("selectDynamicKeyExchangeStatus").trim();
            String dynamicKeyExchangePeriod = request.getParameter("dynamicKeyExchangePeriod").trim();
            String echoPeriod = request.getParameter("echoPeriod").trim();
            String echoDirection = request.getParameter("echoDirection").trim();
            String keyExchangeDirection = request.getParameter("keyExchangeDirection").trim();
            String status = request.getParameter("selectStatusCode").trim();




            channelBean.setChannelId(channelId);
            channelBean.setChannelName(channelName);
            channelBean.setIp(ip);
            channelBean.setPort(port);
            channelBean.setTimeOut(timeOut);
            channelBean.setConnectionType(connectionType);
            channelBean.setChannelType(channelType);
            channelBean.setEchoStatus(echoStatus);
            channelBean.setSingonStatus(signonStatus);
            channelBean.setDynamicKeyExchangeStatus(dynamicKeyExchangeStatus);
            channelBean.setDynamicKeyExchangePeriod(dynamicKeyExchangePeriod);
            channelBean.setEchoPeriod(echoPeriod);
            channelBean.setEchoDirection(echoDirection);
            channelBean.setKeyExchangeDirection(keyExchangeDirection);
            channelBean.setStatus(status);

            channelBean.setKeyId(request.getParameter("keyId").trim());
            channelBean.setHdesId(request.getParameter("hdesId").trim());
            channelBean.setHsrcId(request.getParameter("hsrcId").trim());
            channelBean.setAscii32Id(request.getParameter("ascii32Id").trim());
            channelBean.setFiic33Id(request.getParameter("fiic33Id").trim());


            channelBean.setLastUpdateUser(sessionUser.getUserName());

            newValue = channelBean.getChannelId() + "|" + channelBean.getChannelName() + "|" + channelBean.getIp() + "|"
                    + channelBean.getPort() + "|" + channelBean.getTimeOut() + "|" + channelBean.getConnectionType() + "|" + channelBean.getChannelType() + "|"
                    + channelBean.getEchoStatus() + "|" + channelBean.getSignonStatus() + "|" + channelBean.getDynamicKeyExchangeStatus() + "|"
                    + channelBean.getDynamicKeyExchangePeriod() + "|" + channelBean.getEchoPeriod() + "|" + channelBean.getEchoDirection() + "|"
                    + channelBean.getKeyExchangeDirection() + "|" + channelBean.getStatus() + "|" + channelBean.getKeyId() + "|"
                    + channelBean.getHdesId() + "|" + channelBean.getHsrcId() + "|" + channelBean.getAscii32Id() + "|" + channelBean.getFiic33Id();


        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    /**
     * Validate the New User Entry
     * @param txnTypeBean
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(ChannelConfigBean channelBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code

        try {

            if (channelBean.getChannelId().contentEquals("") || channelBean.getChannelId().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.CHANNEL_ID_NULL;

            } else if (!UserInputValidator.isNumeric(channelBean.getChannelId())) {
                isValidate = false;

                errorMessage = MessageVarList.CHANNEL_ID_INVALID;

            } else if (channelBean.getChannelName().contentEquals("") || channelBean.getChannelName().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.CHANNEL_NAME_NULL;

            } else if (!UserInputValidator.isAlphaNumeric(channelBean.getChannelName()) && !channelBean.getChannelName().contains("-")) {
                isValidate = false;

                errorMessage = MessageVarList.CHANNEL_NAME_INVALID;

            } else if (channelBean.getKeyId().contentEquals("") || channelBean.getKeyId().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.KEY_ID_NULL;

            } else if (channelBean.getIp().contentEquals("") || channelBean.getIp().length() <= 0) {
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


            } else if (channelBean.getChannelType().contentEquals("") || channelBean.getChannelType().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.CHANNEL_TYPE_NULL;


            } else if (channelBean.getEchoStatus().contentEquals("") || channelBean.getEchoStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ECHO_STATUS_NULL;

            } else if (channelBean.getSignonStatus().contentEquals("") || channelBean.getSignonStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.SIGNON_STATUS_NULL;




            } else if (channelBean.getDynamicKeyExchangeStatus().contentEquals("") || channelBean.getDynamicKeyExchangeStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.DYNAMIC_KEY_EXCHANGE_STATUS_NULL;




            } else if (channelBean.getDynamicKeyExchangePeriod().contentEquals("") || channelBean.getDynamicKeyExchangePeriod().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.DYNAMIC_KEY_EXCHANGE_PERIOD_NULL;

            } else if (!UserInputValidator.isNumeric(channelBean.getDynamicKeyExchangePeriod())) {
                isValidate = false;

                errorMessage = MessageVarList.DYNAMIC_KEY_EXCHANGE_PERIOD_INVALID;


            } else if (channelBean.getEchoPeriod().contentEquals("") || channelBean.getEchoPeriod().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ECHO_PERIOD_NULL;

            } else if (!UserInputValidator.isNumeric(channelBean.getEchoPeriod())) {
                isValidate = false;

                errorMessage = MessageVarList.ECHO_PERIOD_INVALID;

            } else if (channelBean.getHdesId().contentEquals("") || channelBean.getHdesId().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.HDES_ID_NULL;

            } else if (!UserInputValidator.isNumeric(channelBean.getHdesId())) {
                isValidate = false;

                errorMessage = MessageVarList.HDES_ID_INVALID;

            } else if (channelBean.getHsrcId().contentEquals("") || channelBean.getHsrcId().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.HSRC_ID_NULL;

            } else if (!UserInputValidator.isNumeric(channelBean.getHsrcId())) {
                isValidate = false;

                errorMessage = MessageVarList.HSRC_ID_INVALID;

            } else if (channelBean.getAscii32Id().contentEquals("") || channelBean.getAscii32Id().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ASCII32_ID_NULL;

            } else if (!UserInputValidator.isNumeric(channelBean.getAscii32Id())) {
                isValidate = false;

                errorMessage = MessageVarList.ASCII32_ID_INVALID;

            } else if (channelBean.getFiic33Id().contentEquals("") || channelBean.getFiic33Id().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.FIIC33_ID_NULL;

            } else if (!UserInputValidator.isNumeric(channelBean.getFiic33Id())) {
                isValidate = false;

                errorMessage = MessageVarList.FIIC33_ID_INVALID;

            } else if (channelBean.getEchoDirection().contentEquals("") || channelBean.getEchoDirection().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ECHO_DIRECTION_NULL;

            } else if (channelBean.getKeyExchangeDirection().contentEquals("") || channelBean.getKeyExchangeDirection().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.KEY_EXCHANGE_DIRECTION_NULL;

            } else if (channelBean.getStatus().contentEquals("") || channelBean.getStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.STATUS_NULL;

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
    public boolean channelInsert(ChannelConfigBean channelBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            /// call insertTxntypeDetails method in TxnTypeMgtManager class

            channelManager = new ChannelConfigManager();
            success = channelManager.insertChannel(channelBean, systemAuditBean);
        } catch (SQLException ex) {
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
            String uniqueId = channelBean.getChannelId();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Channel Channel Id: " + channelBean.getChannelId() + ";  by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.SWITCH_CONTROL_PANEL);
            systemAuditBean.setSectionCode(SectionVarList.CHANEL_CINFIG);
            systemAuditBean.setPageCode(PageVarList.CHANNEL);
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
