/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationCreditScoreBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationCreditScoreBreakDownManager;
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
public class SearchApplicationCreditScoreBreakDownServlet extends HttpServlet {

    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private String errorMessage = null;
    private ApplicationDetailsManager appdetailsManager = null;
    private ApplicationCreditScoreBreakDownManager crditScoreManager = null;
    private HashMap<String, String> branchList = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> csUserList = null;
    private ApplicationCreditScoreBean creditScoreBean = null;
    private List<ApplicationCreditScoreBean> creditScoreList = null;
    private String url = "/reportexp/cardapplication/applicationcreditscorebreakdownhome.jsp";

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
                    String pageCode = PageVarList.APPLICATION_CREDIT_SCORE_BREAKDOWN_RPT;
                    String taskCode = TaskVarList.SEARCH;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        String id=request.getParameter("id");

                        try {
                            this.getBranchNames();
                            this.getAllPriorityLevelList();
                            this.getCreditScoreUserList();



                            request.setAttribute("branchList", branchList);
                            request.setAttribute("priorityLevelList", priorityLevelList);
                            request.setAttribute("csUserList", csUserList);
                            if(id.equals("0")){
                            setUserInputToBean(request);
                            }

                            if (validateUserInput(creditScoreBean)) {

                                // this.setAudittraceValue(request);

                                try {

                                    this.searchApplicationCreditScoreReport(creditScoreBean);

                                    request.setAttribute("creditScoreList", creditScoreList);
                                    request.setAttribute("creditScoreBean", creditScoreBean);

                                } catch (SQLException e) {

                                    OracleMessage message = new OracleMessage();
                                    String oraMessage = message.getMessege(e.getMessage());
                                    request.setAttribute("errorMsg", oraMessage);
                                    request.setAttribute("creditScoreBean", creditScoreBean);
                                    rd = getServletContext().getRequestDispatcher(url);
                                }

                            } else {

                                request.setAttribute("creditScoreBean", creditScoreBean);
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

    private void searchApplicationCreditScoreReport(ApplicationCreditScoreBean creditScoreBean) throws Exception {

        try {

            crditScoreManager = new ApplicationCreditScoreBreakDownManager();
            creditScoreList = new ArrayList<ApplicationCreditScoreBean>();

            creditScoreList = crditScoreManager.searchApplicationCreditScoreReport(creditScoreBean);

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

    private void getCreditScoreUserList() throws Exception {
        try {
            crditScoreManager = new ApplicationCreditScoreBreakDownManager();
            csUserList = new HashMap<String, String>();
            csUserList = crditScoreManager.getCreditScoreUserList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = false;
        try {
            creditScoreBean = new ApplicationCreditScoreBean();

            creditScoreBean.setNic(request.getParameter("nic").trim());
            creditScoreBean.setPassport(request.getParameter("passport").trim());
            //creditScoreBean.setDrivingLicence(request.getParameter("drivingLicence").trim());
            creditScoreBean.setApplicationID(request.getParameter("applicationID").trim());
            creditScoreBean.setBranch(request.getParameter("branch").trim());
            creditScoreBean.setPriorityLevel(request.getParameter("priorityLevel").trim());
            creditScoreBean.setCreditScoreUser(request.getParameter("creditScoreUser").trim());
            creditScoreBean.setFromDate(request.getParameter("fromDate"));
            creditScoreBean.setToDate(request.getParameter("toDate"));

            success = true;

        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    public boolean validateUserInput(ApplicationCreditScoreBean creditScoreBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code

        try {
            if (!(creditScoreBean.getNic().equals("")) && !UserInputValidator.isAlphaNumeric(creditScoreBean.getNic())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!(creditScoreBean.getPassport().equals("")) && !UserInputValidator.isAlphaNumeric(creditScoreBean.getPassport())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

//            } else if (!(creditScoreBean.getDrivingLicence().equals("")) && !UserInputValidator.isAlphaNumeric(creditScoreBean.getDrivingLicence())) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!(creditScoreBean.getApplicationID().equals("")) && !UserInputValidator.isAlphaNumeric(creditScoreBean.getApplicationID())) {
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
//            String uniqueId = creditScoreBean.getApplicationID();
//
//            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
//            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
//            //set unique id
//            systemAuditBean.setUniqueId(uniqueId);
//            //set description 
//            systemAuditBean.setDescription("Search Application Credit Score Application ID: " + creditScoreBean.getApplicationID() + "; By " + sessionVarlist.getCMSSessionUser().getUserName());
//            systemAuditBean.setApplicationCode(ApplicationVarList.REPORT_EXPLORER);
//            systemAuditBean.setSectionCode(SectionVarList.CARD_APPLICATION_REPORT);
//            systemAuditBean.setPageCode(PageVarList.APPLICATION_CREDIT_SCORE_BREAKDOWN_RPT);
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
