/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ServiceCodeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.ServiceCodeMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
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
public class UpdateServiceCodeMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private String errorMessage = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private boolean successUpdate = false;
    private ServiceCodeBean serviceBean = null;
    private List<ServiceCodeBean> serviceList;
    private ServiceCodeMgtManager serviceManager = null;
    private String url = "/administrator/controlpanel/systemconfigmgt/servicecodemgthome.jsp";

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
                String pageCode = PageVarList.SERVICE_CODE;
                String taskCode = TaskVarList.UPDATE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                    try {

                        //---------get service Code Details for table view
                        serviceManager = new ServiceCodeMgtManager();
                        serviceList = new ArrayList<ServiceCodeBean>();

                        serviceList = serviceManager.getServiceCodeDetails();
                        request.setAttribute(RequestVarList.SERVICE_LIST, serviceList);
                        rd = request.getRequestDispatcher(url);
                        //------------------------------------------------------------

                        if (setUserInputToBean(request)) {
                            if (validateUserInput(serviceBean)) {
                                request.setAttribute("operationtype", "UPDATE");
                                this.setAudittraceValue(request);

                                try {
                                    successUpdate = serviceCodeUpdate(serviceBean, systemAuditBean);

                                    if (successUpdate) {

                                        request.setAttribute("successMsg", MessageVarList.SERVICE_CODE_SUCCESS_UPDATE + " " + serviceBean.getServiceCode());
                                    }

                                    rd = getServletContext().getRequestDispatcher("/LordServiceCodeMgtServlet");

                                } catch (SQLException e) {


                                    OracleMessage message = new OracleMessage();
                                    String oraMessage = message.getMessege(e.getMessage());
                                    request.setAttribute("operationtype", "UPDATE");
                                    request.setAttribute(RequestVarList.SERVICE_LIST, serviceList);
                                    request.setAttribute("serviceBean", serviceBean);
                                    request.setAttribute("errorMsg", oraMessage);
                                    rd = getServletContext().getRequestDispatcher(url);
                                    rd.forward(request, response);
                                }

                            } else {

                                request.setAttribute("operationtype", "UPDATE");
                                request.setAttribute(RequestVarList.SERVICE_LIST, serviceList);
                                request.setAttribute("serviceBean", serviceBean);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }
                        } else {

                            request.setAttribute("operationtype", "UPDATE");
                            request.setAttribute(RequestVarList.SERVICE_LIST, serviceList);
                            request.setAttribute("serviceBean", serviceBean);
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }

                        //---------get service Code Details for table view
                        serviceManager = new ServiceCodeMgtManager();
                        serviceList = new ArrayList<ServiceCodeBean>();

                        serviceList = serviceManager.getServiceCodeDetails();
                        request.setAttribute(RequestVarList.SERVICE_LIST, serviceList);
                        rd = request.getRequestDispatcher(url);
                        //------------------------------------------------------------
                        rd.forward(request, response);

                    } catch (Exception e) {

                        request.setAttribute("operationtype", "UPDATE");
                        request.setAttribute(RequestVarList.SERVICE_LIST, serviceList);
                        request.setAttribute("serviceBean", serviceBean);
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
        } finally {
            out.close();
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = true;
        try {

            serviceBean = new ServiceCodeBean();

            serviceBean.setServiceCode(request.getParameter("serviceCode").trim());
            serviceBean.setDescription(request.getParameter("description").trim());
            serviceBean.setInternationalStatus(request.getParameter("internationalStatus").trim());
            serviceBean.setAtmStatus(request.getParameter("atmStatus").trim());
            serviceBean.setPinStatus(request.getParameter("pinStatus").trim());
            serviceBean.setAuthStatus(request.getParameter("authStatus").trim());
            serviceBean.setStatus(request.getParameter("status").trim());
            serviceBean.setOldValue(request.getParameter("oldValue").trim());

            serviceBean.setLastUpdatedUser(sessionUser.getUserName());

        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    public boolean validateUserInput(ServiceCodeBean serviceBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code

        try {

            if (serviceBean.getServiceCode().contentEquals("") || serviceBean.getServiceCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.SERVICECODE_NULL;

            } else if (!UserInputValidator.isNumeric(serviceBean.getServiceCode())) {
                isValidate = false;

                errorMessage = MessageVarList.SERVICECODE_INVALID;

            } else if (serviceBean.getServiceCode().length() != 3) {
                isValidate = false;

                errorMessage = MessageVarList.SERVICECODE_INVALID;

            } else if (serviceBean.getDescription().contentEquals("") || serviceBean.getDescription().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.DESCRIPTION_NULL;

            } else if (!UserInputValidator.isDescription(serviceBean.getDescription())) {
                isValidate = false;

                errorMessage = MessageVarList.DESCRIPTION_INVALID;

            } else if (serviceBean.getInternationalStatus().contentEquals("") || serviceBean.getInternationalStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.INTERNATIONAL_STATUS_NULL;

            } else if (serviceBean.getAtmStatus().contentEquals("") || serviceBean.getAtmStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.ATM_STATUS_NULL;

            } else if (serviceBean.getPinStatus().contentEquals("") || serviceBean.getPinStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.PIN_STATUS_NULL;

            } else if (serviceBean.getAuthStatus().contentEquals("") || serviceBean.getAuthStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.AUTH_STATUS_NULL;

            } else if (serviceBean.getStatus().contentEquals("") || serviceBean.getStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.STATUS_NULL;

            }



        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public boolean serviceCodeUpdate(ServiceCodeBean serviceBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            serviceManager = new ServiceCodeMgtManager();
            success = serviceManager.serviceCodeUpdate(serviceBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = serviceBean.getServiceCode();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Service Code. Service Code :" + serviceBean.getServiceCode() + "; by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.SERVICE_CODE);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(serviceBean.getOldValue());
            //set new value of change if required
            systemAuditBean.setNewValue(this.setNewUpdateValue(serviceBean));


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    private String setNewUpdateValue(ServiceCodeBean newBean) throws Exception {
        String newValue = null;
        try {

            newValue = newBean.getServiceCode() + "|" + newBean.getDescription() + "|" + newBean.getInternationalStatus() + "|"
                    + newBean.getAtmStatus() + "|" + newBean.getPinStatus() + "|" + newBean.getAuthStatus() + "|"
                    + newBean.getStatus();


        } catch (Exception ex) {
            throw ex;

        }
        return newValue;
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
