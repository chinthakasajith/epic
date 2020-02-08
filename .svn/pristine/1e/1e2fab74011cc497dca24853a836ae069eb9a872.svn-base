/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.serverconfig.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.switchcontrol.serverconfig.bean.ServerMainConfigBean;
import com.epic.cms.switchcontrol.serverconfig.businesslogic.ServerMainConfigManager;
import com.epic.cms.switchcontrol.serverconfig.persistance.ServerMainConfigOnlinePersistance;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
public class UpdateServermainConfigServlet extends HttpServlet {

    //initializing variables
    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private List<String> userTaskList;
    private SessionUser sessionUser = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    //------------------------------------------------
    private String errorMessage = null;
    private ServerMainConfigBean serverConfigBean = null;
    private ServerMainConfigManager serverMainConfigManager;
    private String url = "/switch/serverconfig/servermainconfig/servermainconfighome.jsp";
    private String oldValue;
    private String newValue;
    private ServerMainConfigBean srvrConfigBean;

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

                    //

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
                String pageCode = PageVarList.SERVERMAINCONFIG;
                String taskCode = TaskVarList.UPDATE;


                //check whether user_role have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                    try {
                        if (this.setUserInputToBean(request)) {

                            //validate user input
                            if (validateUserInput(serverConfigBean)) {
                                request.setAttribute("operationtype", "add");
                                //set values to the audit trace
                                this.setAudittraceValue(request);

                                serverMainConfigManager = new ServerMainConfigManager();
                                boolean success = serverMainConfigManager.insertNewServerMainConfigDetails(serverConfigBean, systemAuditBean);

                                if (success == true) {
                                    request.setAttribute("successMsg", MessageVarList.SERVERMAINCONFIG_SUCCESS_UPDATE);
                                }
                                if (success == false) {
                                    request.setAttribute("errorMsg", "Failed update");
                                }

                                rd = getServletContext().getRequestDispatcher("/LoadServermainConfigServlet");
                                rd.include(request, response);
                            } else {
                                request.setAttribute("operationtype", "add");
                                request.setAttribute("srvrConfigBean", serverConfigBean);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);
                            }
                        } else {

                            request.setAttribute("operationtype", "add");
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        request.setAttribute("srvrConfigBean", serverConfigBean);
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
        } finally {
            out.close();
        }
    }

    /**
     * check if user has valid access to the page
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

    /**
     * set user input to bean
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            serverConfigBean = new ServerMainConfigBean();

            String operateSystemType = request.getParameter("opSysType").trim();
//            String runningMode = request.getParameter("runMode").trim();
//            String permanentRunningPort = request.getParameter("runPort").trim();
//            String terminatedRunningPort = request.getParameter("terminateRunPort").trim();
            //   String noOfConnections = request.getParameter("noOfCons").trim();
            String initialVector = request.getParameter("iniVector").trim();
            String reqBufSize = request.getParameter("reqBufSize").trim();
            String resBufSize = request.getParameter("resBufSize").trim();
            String alertStatus = request.getParameter("alertStatus").trim();
            String failStatus = request.getParameter("failStatus").trim();
            //     String tempConnectTimeOut = request.getParameter("tempConTimeOut").trim();
            String maxPoolSize = request.getParameter("maxPoolSize").trim();
            String minPoolSize = request.getParameter("minPoolSize").trim();
            String maxQueueSize = request.getParameter("maxQueueSize").trim();
            String poolBackLog = request.getParameter("poolBackLog").trim();
            //    String maxPINCount = request.getParameter("maxPINCount").trim();
            String pvi = request.getParameter("pvi").trim();
            String hsmType = request.getParameter("hsmType").trim();
            String pinbFormat = request.getParameter("pinb");
            //     String emvVerifyMethod = request.getParameter("emv");
            String encrMode = request.getParameter("encr");
            String channel = request.getParameter("Channel");
            String statusOfDefaultChannel = request.getParameter("chanelStatus");

            serverConfigBean.setOperateSystemType(operateSystemType);
//            serverConfigBean.setRunningMode(runningMode);
//            serverConfigBean.setPermanentRunningPort(permanentRunningPort);
//            serverConfigBean.setTerminatedRunningPort(terminatedRunningPort);
            //      serverConfigBean.setNoOfConnections(noOfConnections);
            serverConfigBean.setInitialVector(initialVector);
            serverConfigBean.setReqBufSize(reqBufSize);
            serverConfigBean.setResBufSize(resBufSize);
            serverConfigBean.setAlertStatus(alertStatus);
            serverConfigBean.setFailStatus(failStatus);
            //   serverConfigBean.setTempConnectTimeOut(tempConnectTimeOut);
            serverConfigBean.setMaxPoolSize(maxPoolSize);
            serverConfigBean.setMinPoolSize(minPoolSize);
            serverConfigBean.setMaxQueueSize(maxQueueSize);
            serverConfigBean.setPoolBackLog(poolBackLog);
            //   serverConfigBean.setMaxPINCount(maxPINCount);
            serverConfigBean.setPvi(pvi);
            serverConfigBean.setHsmType(hsmType);
            serverConfigBean.setPinbFormat(pinbFormat);
            //   serverConfigBean.setEmvVerifyMethod(emvVerifyMethod);
            serverConfigBean.setEnrcMode(encrMode);
            serverConfigBean.setChannelId(channel);
            serverConfigBean.setChannelStatus(statusOfDefaultChannel);

            newValue = serverConfigBean.getOperateSystemType() + "|" + serverConfigBean.getReqBufSize() + "|"
                    + serverConfigBean.getInitialVector() + "|" + serverConfigBean.getResBufSize() + "|"
                    + serverConfigBean.getAlertStatus() + "|" + serverConfigBean.getFailStatus() + "|"
                    + serverConfigBean.getMaxPoolSize() + "|" + serverConfigBean.getMinPoolSize() + "|" + serverConfigBean.getMaxQueueSize() + "|"
                    + serverConfigBean.getPoolBackLog() + "|" + serverConfigBean.getPvi() + "|" + serverConfigBean.getHsmType() + "|"
                    + serverConfigBean.getPinbFormat() + "|" + serverConfigBean.getEnrcMode() + "|" + serverConfigBean.getChannelId() + "|"
                    + serverConfigBean.getChannelStatus();

            srvrConfigBean = new ServerMainConfigBean();
            //retrieve data from ECMS_ONLINE_CONFIG table
            ServerMainConfigBean bean = this.getDataFromServerConfigTable();

            oldValue = bean.getOperateSystemType() + "|" + bean.getReqBufSize() + "|"
                    + bean.getInitialVector() + "|" + bean.getResBufSize() + "|"
                    + bean.getAlertStatus() + "|" + bean.getFailStatus() + "|"
                    + bean.getMaxPoolSize() + "|" + bean.getMinPoolSize() + "|" + bean.getMaxQueueSize() + "|"
                    + bean.getPoolBackLog() + "|" + bean.getPvi() + "|" + bean.getHsmType() + "|"
                    + bean.getPinbFormat() + "|" + bean.getEnrcMode() + "|" + bean.getChannelId() + "|"
                    + bean.getChannelStatus();

        } catch (Exception e) {
            status = false;
            throw e;

        }

        return status;
    }

    /**
     * set values for audit trace
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            //String uniqueId = request.getParameter(mrchntBean.getmCCCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            //systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Server Main Configuration by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            //systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
            systemAuditBean.setPageCode(PageVarList.SERVERMAINCONFIG);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(oldValue);
            //set new value of change if required
            systemAuditBean.setNewValue(newValue);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * validate user input
     * @param serverConfigBean
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(ServerMainConfigBean serverConfigBean) throws Exception {
        boolean isValidate = true;
        int maxValue = 6;
        int minValue = 0;

        try {
            if (serverConfigBean.getOperateSystemType().contentEquals("") || serverConfigBean.getOperateSystemType().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_OPSYSTYPE_NULL;
            } else if (!UserInputValidator.isAlphaNumeric(serverConfigBean.getOperateSystemType())) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_OPSYSTYPE_INVALID;
            } //            else if (serverConfigBean.getRunningMode() == null) {
            //                isValidate = false;
            //                errorMessage = MessageVarList.SERVERMAINCONFIG_RUNNINGMODE_NULL;
            //            } else if (serverConfigBean.getPermanentRunningPort().contentEquals("") || serverConfigBean.getPermanentRunningPort().length() <= 0) {
            //                isValidate = false;
            //                errorMessage = MessageVarList.SERVERMAINCONFIG_PERMANENTRUNNINGPORT_NULL;
            //            } else if (!UserInputValidator.isCorrectPort(serverConfigBean.getPermanentRunningPort())) {
            //                isValidate = false;
            //                errorMessage = MessageVarList.SERVERMAINCONFIG_PERMANENTRUNNINGPORT_INVALID;
            //            } else if (serverConfigBean.getTerminatedRunningPort().contentEquals("") || serverConfigBean.getTerminatedRunningPort().length() <= 0) {
            //                isValidate = false;
            //                errorMessage = MessageVarList.SERVERMAINCONFIG_TERMINATEDRUNNINGPORT_NULL;
            //            } else if (!UserInputValidator.isCorrectPort(serverConfigBean.getTerminatedRunningPort())) {
            //                isValidate = false;
            //                errorMessage = MessageVarList.SERVERMAINCONFIG_TERMINATEDRUNNINGPORT_INVALID;
            //            } 
            //            else if (serverConfigBean.getNoOfConnections().contentEquals("") || serverConfigBean.getNoOfConnections().length() <= 0) {
            //                isValidate = false;
            //                errorMessage = MessageVarList.SERVERMAINCONFIG_NOOFCONNECTIONS_NULL;
            //            } else if (!UserInputValidator.isNumeric(serverConfigBean.getNoOfConnections())) {
            //                isValidate = false;
            //                errorMessage = MessageVarList.SERVERMAINCONFIG_NOOFCONNECTIONS_INVALID;
            //        }
            else if (serverConfigBean.getInitialVector().contentEquals("") || serverConfigBean.getInitialVector().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_INITIALVECTOR_NULL;
            } else if (!UserInputValidator.isNumeric(serverConfigBean.getInitialVector())) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_INITIALVECTOR_INVALID;
            } else if (serverConfigBean.getReqBufSize().contentEquals("") || serverConfigBean.getReqBufSize().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_REQBUFSIZE_NULL;
            } else if (!UserInputValidator.isNumeric(serverConfigBean.getReqBufSize())) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_REQBUFSIZE_INVALID;
            } else if (serverConfigBean.getResBufSize().contentEquals("") || serverConfigBean.getResBufSize().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_RESBUFSIZE_NULL;
            } else if (!UserInputValidator.isNumeric(serverConfigBean.getResBufSize())) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_RESBUFSIZE_INVALID;
            } else if (serverConfigBean.getAlertStatus() == null) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_ALERTSTATUS_NULL;
            } else if (serverConfigBean.getFailStatus() == null) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_FAILSTATUS_NULL;
            } else if (!UserInputValidator.isNumeric(serverConfigBean.getResBufSize())) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_RESBUFSIZE_INVALID;
//            } else if (serverConfigBean.getTempConnectTimeOut().contentEquals("") || serverConfigBean.getTempConnectTimeOut().length() <= 0) {
//                isValidate = false;
//                errorMessage = MessageVarList.SERVERMAINCONFIG_TEMPCONTIMEOUT_NULL;
//            } else if (!UserInputValidator.isNumeric(serverConfigBean.getTempConnectTimeOut())) {
//                isValidate = false;
//                errorMessage = MessageVarList.SERVERMAINCONFIG_TEMPCONTIMEOUT_INVALID;
            } else if (serverConfigBean.getMaxPoolSize().contentEquals("") || serverConfigBean.getMaxPoolSize().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_MAXPOOLSIZE_NULL;
            } else if (!UserInputValidator.isNumeric(serverConfigBean.getMaxPoolSize())) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_MAXPOOLSIZE_INVALID;
            } else if (serverConfigBean.getMinPoolSize().contentEquals("") || serverConfigBean.getMinPoolSize().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_MINPOOLSIZE_NULL;
            } else if (!UserInputValidator.isNumeric(serverConfigBean.getMinPoolSize())) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_MINPOOLSIZE_INVALID;
            } else if (serverConfigBean.getMaxQueueSize().contentEquals("") || serverConfigBean.getMaxQueueSize().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_MAXQUEUESIZE_NULL;
            } else if (!UserInputValidator.isNumeric(serverConfigBean.getMaxQueueSize())) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_MAXQUEUESIZE_INVALID;
            } else if (serverConfigBean.getPoolBackLog().contentEquals("") || serverConfigBean.getPoolBackLog().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_POOLBACKLOG_NULL;
            } else if (!UserInputValidator.isNumeric(serverConfigBean.getPoolBackLog())) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_POOLBACKLOG_INVALID;
//            } else if (serverConfigBean.getMaxPINCount().contentEquals("") || serverConfigBean.getMaxPINCount().length() <= 0) {
//                isValidate = false;
//                errorMessage = MessageVarList.SERVERMAINCONFIG_MAXPINCOUNT_NULL;
//            } else if (!UserInputValidator.isNumeric(serverConfigBean.getMaxPINCount())) {
//                isValidate = false;
//                errorMessage = MessageVarList.SERVERMAINCONFIG_MAXPINCOUNT_INVALID;
            } else if (serverConfigBean.getHsmType() == null) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_HSMTYPE_NULL;
            } else if (Integer.parseInt(serverConfigBean.getPvi()) > maxValue || Integer.parseInt(serverConfigBean.getPvi()) < minValue) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_PVI_INVALID;
            } else if (serverConfigBean.getPinbFormat().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_PINB_NULL;
//            } else if (serverConfigBean.getEmvVerifyMethod().contentEquals("")) {
//                isValidate = false;
//                errorMessage = MessageVarList.SERVERMAINCONFIG_EMV_NULL;
            } else if (serverConfigBean.getEnrcMode().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.SERVERMAINCONFIG_ENCR_NULL;
            } else if (serverConfigBean.getChannelId().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.DEF_CHANNEL_NULL;
            }
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
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
