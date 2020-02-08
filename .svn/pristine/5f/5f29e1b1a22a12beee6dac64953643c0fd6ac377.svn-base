/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationDetailsBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationDetailsManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class SearchApplicationDetailsServlet extends HttpServlet {

    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private String errorMessage = null;
    private ApplicationDetailsManager appdetailsManager = null;
    private HashMap<String, String> branchList = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> applicationStatusList = null;
    private HashMap<String, String> domainList = null;
    private ApplicationDetailsBean summeryBean = null;
    private List<ApplicationDetailsBean> summeryList = null;
    private String url = "/reportexp/cardapplication/applicationdetailshome.jsp";

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
            try {
                sessionObj = request.getSession(false);
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
                    String pageCode = PageVarList.APPLICATION_DETAILS_RPT;
                    String taskCode = TaskVarList.SEARCH;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                        try {
                            String id = request.getParameter("id");
                            this.getBranchNames();
                            this.getAllPriorityLevelList();
                            this.getAllApplicationStatusList(StatusVarList.APPLICATIONSTATUSCAT);
                            this.getAllDomainList();

                            request.setAttribute("branchList", branchList);
                            request.setAttribute("priorityLevelList", priorityLevelList);
                            request.setAttribute("applicationStatusList", applicationStatusList);
                            request.setAttribute("domainList", domainList);
                            if (id.equals("0")) {
                                setUserInputToBean(request);
                            }

                            if (validateUserInput(summeryBean)) {

                                // this.setAudittraceValue(request);
                                try {

                                    sessionVarlist.setDetailsBean(summeryBean);
                                    this.searchApplicationSummeryReport(summeryBean);

                                    request.setAttribute("summeryList", summeryList);
                                    request.setAttribute("summeryBean", summeryBean);

                                } catch (SQLException e) {

                                    OracleMessage message = new OracleMessage();
                                    String oraMessage = message.getMessege(e.getMessage());
                                    request.setAttribute("errorMsg", oraMessage);
                                    request.setAttribute("summeryBean", summeryBean);
                                    rd = getServletContext().getRequestDispatcher(url);
                                }

                            } else {

                                request.setAttribute("summeryBean", summeryBean);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }

                        } catch (Exception ex) {

                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            rd = getServletContext().getRequestDispatcher(url);
                        }

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

            rd = request.getRequestDispatcher(url);

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);

        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void searchApplicationSummeryReport(ApplicationDetailsBean summeryBean) throws Exception {

        try {

            appdetailsManager = new ApplicationDetailsManager();
            summeryList = new ArrayList<ApplicationDetailsBean>();

            summeryList = appdetailsManager.searchApplicationSummeryReport(summeryBean);

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void getBranchNames() throws Exception {
        try {
            appdetailsManager = new ApplicationDetailsManager();
            branchList = new HashMap<String, String>();
            branchList = appdetailsManager.getBranchNames();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllPriorityLevelList() throws Exception {
        try {
            appdetailsManager = new ApplicationDetailsManager();
            priorityLevelList = new HashMap<String, String>();
            priorityLevelList = appdetailsManager.getAllPriorityLevels();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllApplicationStatusList(String category) throws Exception {
        try {
            appdetailsManager = new ApplicationDetailsManager();
            applicationStatusList = new HashMap<String, String>();
            applicationStatusList = appdetailsManager.getStatusList(category);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllDomainList() throws Exception {
        try {
            appdetailsManager = new ApplicationDetailsManager();
            domainList = new HashMap<String, String>();
            domainList = appdetailsManager.getAllDomainList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = false;
        try {
            summeryBean = new ApplicationDetailsBean();

            summeryBean.setNic(request.getParameter("nic").trim());
            summeryBean.setPassport(request.getParameter("passport").trim());
            //summeryBean.setDrivingLicence(request.getParameter("drivingLicence").trim());
            summeryBean.setApplicationID(request.getParameter("applicationID").trim());
            summeryBean.setBranch(request.getParameter("branch").trim());
            summeryBean.setPriorityLevel(request.getParameter("priorityLevel").trim());
            summeryBean.setApplicationStatus(request.getParameter("applicationStatus").trim());
            summeryBean.setDomain(request.getParameter("domain").trim());
            summeryBean.setFromDate(request.getParameter("fromDate").trim());
            summeryBean.setToDate(request.getParameter("toDate").trim());

            success = true;

        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    public boolean validateUserInput(ApplicationDetailsBean summeryBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code
        try {
            if (!(summeryBean.getNic().equals("")) && !UserInputValidator.isAlphaNumeric(summeryBean.getNic())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!(summeryBean.getPassport().equals("")) && !UserInputValidator.isAlphaNumeric(summeryBean.getPassport())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

//            } else if (!(summeryBean.getDrivingLicence().equals("")) && !UserInputValidator.isAlphaNumeric(summeryBean.getDrivingLicence())) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!(summeryBean.getApplicationID().equals("")) && !UserInputValidator.isAlphaNumeric(summeryBean.getApplicationID())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

//    private void setAudittraceValue(HttpServletRequest request) throws Exception {
//
//        try {
//            systemAuditBean = new SystemAuditBean();
//            ////get unique Id from the page.It may be the primary key---------------
//            String uniqueId = summeryBean.getApplicationID();
//
//            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
//            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
//            //set unique id
//            systemAuditBean.setUniqueId(uniqueId);
//            //set description 
//            systemAuditBean.setDescription("Search Application Details Application ID: " + summeryBean.getApplicationID() + "; By " + sessionVarlist.getCMSSessionUser().getUserName());
//            systemAuditBean.setApplicationCode(ApplicationVarList.REPORT_EXPLORER);
//            systemAuditBean.setSectionCode(SectionVarList.CARD_APPLICATION_REPORT);
//            systemAuditBean.setPageCode(PageVarList.APPLICATION_DETAILS_RPT);
//            systemAuditBean.setTaskCode(TaskVarList.SEARCH);
//            systemAuditBean.setIp(request.getRemoteAddr());
//            //add remarks here
//            systemAuditBean.setRemarks("");
//            //set field name which is being changed(only if)
//            systemAuditBean.setFieldName("");
//            //set old value of change if required
//            systemAuditBean.setOldValue("");
//            //set new value of change if required
//            systemAuditBean.setNewValue("");
//
//
//            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
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
