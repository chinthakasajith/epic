/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.logmanagement.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.switchcontrol.chanelconfig.bean.StatusTypeBean;
import com.epic.cms.switchcontrol.listenerconfig.businesslogic.ListenerConfigurationManager;
import com.epic.cms.switchcontrol.logmanagement.bean.LogLevelBean;
import com.epic.cms.switchcontrol.logmanagement.bean.LogMgtBean;
import com.epic.cms.switchcontrol.logmanagement.businesslogic.LogMgtManager;
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
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class UpdateLogMgtServlet extends HttpServlet {

    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemAuditBean systemAuditBean = null;
    private String errorMessage = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private RequestDispatcher rd;
    private LogMgtBean logBean;
    private LogMgtManager logManager;
    private List<LogLevelBean> logLevelList = null;
    private String url = "/switch/logmanagement/logmanagementhome.jsp";
    private String oldValue;
    private String newValue;
    private ListenerConfigurationManager listenerManager;
    private List<StatusTypeBean> statusTypeList;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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
                String pageCode = PageVarList.LOGMGT;
                String taskCode = TaskVarList.UPDATE;


                //check whether user_role have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    this.getLogLevel();
                    request.setAttribute(RequestVarList.LOGLEVEL_LIST, logLevelList);
                    try {
                        if (this.setUserInputToBean(request)) {

                            //validate user input
                            if (validateUserInput(logBean)) {
                                request.setAttribute("operationtype", "ADD");
                                //set values to the audit trace
                                this.setAudittraceValue(request);
                                try {
                                    logManager = new LogMgtManager();
                                    boolean success = logManager.updateLogManagementDetails(logBean, systemAuditBean);

                                    if (success == true) {
                                        request.setAttribute("successMsg", MessageVarList.LOG_MANAGEMENT_SUCCESS_UPDATE);
                                    }
                                    if (success == false) {
                                        request.setAttribute("errorMsg", "Failed update");
                                    }
                                } catch (SQLException e) {

                                    OracleMessage message = new OracleMessage();
                                    String oraMessage = message.getMessege(e.getMessage());
                                    request.setAttribute("errorMsg", oraMessage);
                                    request.setAttribute("operationtype", "ADD");
                                    request.setAttribute("logBean", logBean);
                                    rd = getServletContext().getRequestDispatcher(url);
                                    rd.forward(request, response);
                                }
                                rd = getServletContext().getRequestDispatcher("/LoadLogMgtServlet");
                                rd.include(request, response);
                            } else {
                                request.setAttribute("operationtype", "ADD");
                                request.setAttribute("logBean", logBean);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);
                            }
                        } else {

                            request.setAttribute("operationtype", "ADD");
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }



                    } catch (Exception e) {

                        request.setAttribute("operationtype", "ADD");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        request.setAttribute("logBean", logBean);
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

    private List<LogLevelBean> getLogLevel() throws Exception {

        try {

            logLevelList = new ArrayList<LogLevelBean>();
            logManager = new LogMgtManager();
            logLevelList = logManager.getLogLevel();

        } catch (Exception e) {

            throw e;
        }
        return logLevelList;
    }

    /**
     * check if user has valid access to the page
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

    /**
     * set user input to bean
     *
     * @param request
     * @return
     * @throws Exception
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            logBean = new LogMgtBean();


            logBean.setLogLevel(request.getParameter("logLevel").trim());
            logBean.setLogFileName(request.getParameter("logFileName").trim());
            logBean.setLogBackUpPath(request.getParameter("logBackUpPath").trim());
            logBean.setLogBackUpStatus(request.getParameter("logBackUpStatus").trim());
            logBean.setSynStatus(request.getParameter("synStatus").trim());
            logBean.setSynPeriod(request.getParameter("synPeriod").trim());
            logBean.setNumOfLogFile(request.getParameter("numOfLogFile").trim());

            this.setStatusToSession();
            String newlogBackupStatus = "";
            String newSynStatus = "";

            String oldlogBackupStatus = "";
            String oldSynStatus = "";
            for (StatusTypeBean bean : statusTypeList) {
                if (bean.getCode().equals(logBean.getLogBackUpStatus())) {
                    newlogBackupStatus = bean.getDescription();
                }
            }
            for (StatusTypeBean bean : statusTypeList) {
                if (bean.getCode().equals(logBean.getSynStatus())) {
                    newSynStatus = bean.getDescription();
                }
            }

            newValue = logBean.getLogLevel() + "|" + logBean.getLogFileName() + "|" + logBean.getLogBackUpPath() + "|"
                    + newlogBackupStatus + "|" + newSynStatus + "|" + logBean.getSynPeriod() + "|" + logBean.getNumOfLogFile();

            LogMgtBean bean = this.getLogManagementData();

            for (StatusTypeBean bean1 : statusTypeList) {
                if (bean1.getCode().equals(bean.getLogBackUpStatus())) {
                    oldlogBackupStatus = bean1.getDescription();
                }
            }
            for (StatusTypeBean bean1 : statusTypeList) {
                if (bean1.getCode().equals(bean.getSynStatus())) {
                    oldSynStatus = bean1.getDescription();
                }
            }

            oldValue = bean.getLogLevel() + "|" + bean.getLogFileName() + "|" + bean.getLogBackUpPath() + "|"
                    + oldlogBackupStatus + "|" + oldSynStatus + "|" + bean.getSynPeriod() + "|" + bean.getNumOfLogFile();

        } catch (Exception e) {
            status = false;
            throw e;

        }

        return status;
    }

    /**
     * set values for audit trace
     *
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
            systemAuditBean.setDescription("Updated Log by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.SWITCH_CONTROL_PANEL);
            systemAuditBean.setPageCode(PageVarList.LOGMGT);
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
     *
     * @param serverConfigBean
     * @return
     * @throws Exception
     */
    public boolean validateUserInput(LogMgtBean logBean) throws Exception {
        boolean isValidate = true;

        try {
            if (logBean.getLogLevel().contentEquals("") || logBean.getLogLevel().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.LOG_LEVEL_NULL;

            } else if (logBean.getLogFileName().contentEquals("") || logBean.getLogFileName().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.LOG_FILE_NAME_NULL;

            } else if (!UserInputValidator.isDescription(logBean.getLogFileName())) {
                isValidate = false;
                errorMessage = MessageVarList.LOG_FILE_NAME_INVALID;

            } else if (logBean.getLogBackUpPath().contentEquals("") || logBean.getLogBackUpPath().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.LOG_BACKUP_PATH_NULL;

            } else if (!UserInputValidator.isValidFolderName(logBean.getLogBackUpPath())) {
                isValidate = false;
                errorMessage = MessageVarList.LOG_BACKUP_PATH_INVALID;

            } else if (logBean.getLogBackUpStatus().contentEquals("") || logBean.getLogBackUpStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.LOG_BACKUP_STATUS_NULL;

            } else if (logBean.getSynStatus().contentEquals("") || logBean.getSynStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SYN_STATUS_NULL;

            } else if (logBean.getSynPeriod().contentEquals("") || logBean.getSynPeriod().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SYN_PERIOD_NULL;

            } else if (!UserInputValidator.isNumeric(logBean.getSynPeriod())) {
                isValidate = false;
                errorMessage = MessageVarList.SYN_PERIOD_INVALID;

            } else if (!UserInputValidator.isNumeric(logBean.getNumOfLogFile())) {
                isValidate = false;
                errorMessage = MessageVarList.NUM_OF_LOG_FILE_INVALID;

            }
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    private LogMgtBean getLogManagementData() throws Exception {
        LogMgtBean oldBean = new LogMgtBean();
        try {


            //srvrConfigBean = new ServerMainConfigBean();
            logManager = new LogMgtManager();
            //retrieve merchant details
            oldBean = logManager.getLogManagementData();

        } catch (Exception e) {
            throw e;
        }
        return oldBean;
    }

    private void setStatusToSession() throws Exception {
        try {
            listenerManager = new ListenerConfigurationManager();
            statusTypeList = new ArrayList<StatusTypeBean>();
            statusTypeList = listenerManager.getStatusType();
        } catch (Exception e) {
            throw e;
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
