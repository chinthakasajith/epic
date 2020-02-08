/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.listenerconfig.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.switchcontrol.listenerconfig.bean.ListenerConfigurationBean;
import com.epic.cms.switchcontrol.listenerconfig.businesslogic.ListenerConfigurationManager;
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
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
public class UpdateListenerConfigurationServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private HttpSession sessionObj = null;
    private boolean successInsert = false;
    private String errorMessage = null;
    private ListenerConfigurationBean listenerBean = null;
    private List<ListenerConfigurationBean> listenerList = null;
    private ListenerConfigurationManager listenerManager = null;
    private String url = "/switch/listenerconfig/listenerconfig/listenerconfighome.jsp";
    private String oldValue;
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
                String pageCode = PageVarList.LISTENER;
                String taskCode = TaskVarList.UPDATE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                    try {

                        if (setUserInputToBean(request)) {
                            if (validateUserInput(listenerBean)) {
                                this.setAudittraceValue(request);
                                successInsert = listenerUpdate(listenerBean, systemAuditBean);

                                if (successInsert) {
                                    request.setAttribute("successMsg", MessageVarList.LISTENER_SUCCESS_UPDATE + " " + listenerBean.getListenerId());
                                }
                                request.setAttribute("operationtype", "ADD");

                            } else {
                                request.setAttribute("operationtype", "UPDATE");
                                request.setAttribute("listenerBean", listenerBean);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }
                        } else {
                            request.setAttribute("operationtype", "UPDATE");
                            request.setAttribute("listenerBean", listenerBean);
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }

                        /// get Data for table view

                        listenerList = new ArrayList<ListenerConfigurationBean>();
                        listenerList = listenerManager.getAllListenerDetails();
                        request.setAttribute("listenerList", listenerList);
                        rd = getServletContext().getRequestDispatcher(url);

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "UPDATE");
                        request.setAttribute("listenerBean", listenerBean);
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);

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
            rd.forward(request, response);
            out.close();

        }
    }

    /**
     * 
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = true;
        try {

            listenerBean = new ListenerConfigurationBean();

            String listenerId = request.getParameter("listenerId").trim();
            String listenerType = request.getParameter("listenerType").trim();
            String description = request.getParameter("description");
            // String stationId = request.getParameter("stationId").trim();
            String status = request.getParameter("status").trim();


            listenerBean.setListenerId(listenerId);
            listenerBean.setListenerType(listenerType);
            listenerBean.setDescription(description);
            // listenerBean.setStationId(stationId);
            listenerBean.setStatus(status);

            listenerBean.setStablishPort(request.getParameter("stablishPort").trim());
            listenerBean.setTerminatedPort(request.getParameter("terminatedPort").trim());
            listenerBean.setKeyExchangeStatus(request.getParameter("keyExchangeStatus").trim());
            listenerBean.setNoOfConnection(request.getParameter("noOfConnection").trim());
            listenerBean.setTimeout(request.getParameter("timeout").trim());
            listenerBean.setRuningMode(request.getParameter("runingMode").trim());

            listenerBean.setKeyId(request.getParameter("keyId").trim());
            listenerBean.setSequirtyStatus(request.getParameter("sequirtyStatus").trim());
            listenerBean.setStatusOfAquiring(request.getParameter("statusOfAquiring").trim());

            listenerBean.setLastUpdateUser(sessionUser.getUserName());

            newValue = listenerBean.getListenerType() + "|" + listenerBean.getDescription() + "|" + listenerBean.getStatus() + "|"
                    + listenerBean.getStablishPort() + "|" + listenerBean.getTerminatedPort() + "|" + listenerBean.getKeyExchangeStatus() + "|"
                    + listenerBean.getNoOfConnection() + "|" + listenerBean.getTimeout() + "|" + listenerBean.getRuningMode() + "|"
                    + listenerBean.getKeyId() + "|" + listenerBean.getSequirtyStatus() + "|" + listenerBean.getStatusOfAquiring();


            listenerList = new ArrayList<ListenerConfigurationBean>();
            listenerManager = new ListenerConfigurationManager();
            listenerList = listenerManager.getAllListenerDetails();


            for (ListenerConfigurationBean newListenerBean : listenerList) {

                if (newListenerBean.getListenerId().equals(listenerId)) {

                    oldValue = newListenerBean.getListenerType() + "|" + newListenerBean.getDescription() + "|" + newListenerBean.getStatus() + "|"
                            + newListenerBean.getStablishPort() + "|" + newListenerBean.getTerminatedPort() + "|" + newListenerBean.getKeyExchangeStatus() + "|"
                            + newListenerBean.getNoOfConnection() + "|" + newListenerBean.getTimeout() + "|" + newListenerBean.getRuningMode() + "|"
                            + newListenerBean.getKeyId() + "|" + newListenerBean.getSequirtyStatus() + "|" + newListenerBean.getStatusOfAquiring();


                }

            }


        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    /**
     * 
     * @param txnType
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(ListenerConfigurationBean listenerBean) throws Exception {
        boolean isValidate = true;

        //validate user Role code
        try {
            if (listenerBean.getListenerId().contentEquals("") || listenerBean.getListenerId().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.LISTENERID_NULL;

            } else if (!UserInputValidator.isNumeric(listenerBean.getListenerId())) {
                isValidate = false;

                errorMessage = MessageVarList.LISTENERID_INVALID;

            } else if (listenerBean.getListenerType().contentEquals("") || listenerBean.getListenerType().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.LISTENER_TYPE_NULL;

            } else if (listenerBean.getKeyId().contentEquals("") || listenerBean.getKeyId().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.KEY_ID_NULL;

            } else if (listenerBean.getDescription().contentEquals("") || listenerBean.getDescription().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.DESCRIPTION_NULL;

            } else if (!UserInputValidator.isDescription(listenerBean.getDescription())) {
                isValidate = false;

                errorMessage = MessageVarList.DESCRIPTION_INVALID;

            } else if (listenerBean.getStablishPort().contentEquals("") || listenerBean.getStablishPort().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.PER_PORT_NULL;

            } else if (!UserInputValidator.isNumeric(listenerBean.getStablishPort())) {
                isValidate = false;

                errorMessage = MessageVarList.PER_PORT_INVALID;

            } else if (listenerBean.getTerminatedPort().contentEquals("") || listenerBean.getTerminatedPort().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.TER_PORT_NULL;

            } else if (!UserInputValidator.isNumeric(listenerBean.getTerminatedPort())) {
                isValidate = false;

                errorMessage = MessageVarList.TER_PORT_INVALID;

            } else if (listenerBean.getKeyExchangeStatus().contentEquals("") || listenerBean.getKeyExchangeStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.KEY_EXCHANGE_STATUS_NULL;

            } else if (listenerBean.getSequirtyStatus().contentEquals("") || listenerBean.getSequirtyStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.SECURITY_STATUS_NULL;

            } else if (listenerBean.getNoOfConnection().contentEquals("") || listenerBean.getNoOfConnection().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.NUMBER_OF_CONNECTION_NULL;

            } else if (!UserInputValidator.isNumeric(listenerBean.getNoOfConnection())) {
                isValidate = false;

                errorMessage = MessageVarList.NUMBER_OF_CONNECTION_INVALID;

            } else if (listenerBean.getTimeout().contentEquals("") || listenerBean.getTimeout().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.TIMEOUT_NULL;

            } else if (!UserInputValidator.isNumeric(listenerBean.getTimeout())) {
                isValidate = false;

                errorMessage = MessageVarList.TIMEOUT_INVALID;

            } else if (listenerBean.getRuningMode().contentEquals("") || listenerBean.getRuningMode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.RUNNING_MODE_NULL;

            } else if (listenerBean.getStatusOfAquiring().contentEquals("") || listenerBean.getStatusOfAquiring().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.STATUS_OF_ACUIRING_NULL;

            } else if (listenerBean.getStatus().contentEquals("") || listenerBean.getStatus().length() <= 0) {
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
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = listenerBean.getListenerId();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Listener. Listener Id : " + listenerBean.getListenerId() + "; by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.SWITCH_CONTROL_PANEL);
            systemAuditBean.setSectionCode(SectionVarList.LISTENERCONFIG);
            systemAuditBean.setPageCode(PageVarList.LISTENER);
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
     * 
     * @param txnType
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean listenerUpdate(ListenerConfigurationBean listenerBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            listenerManager = new ListenerConfigurationManager();
            success = listenerManager.listenerUpdate(listenerBean, systemAuditBean);
        } catch (Exception ex) {
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
