/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.securityquesmgt.servlet;

import com.epic.cms.admin.controlpanel.securityquesmgt.bean.SecurityQuestionBean;
import com.epic.cms.admin.controlpanel.securityquesmgt.businesslogic.SecurityQuestionManager;
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
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
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
 * @author badrika
 */
public class UpdateAcquireSecurityQuestionServlet extends HttpServlet {

    //initializing variables
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private String errorMessage = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    SecurityQuestionManager securityQuesMgr;
    List<SecurityQuestionBean> questionList = new ArrayList<SecurityQuestionBean>();
    private SecurityQuestionBean questionBean;
    HashMap<String, String> tables;
    List<String> fields;
    //HashMap<String, String> fields;
    private String url = "/administrator/controlpanel/securityquesmgt/acquiresecurityquestionmgt.jsp";
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
                String pageCode = PageVarList.ACQ_SECURITY_QUES_MGT;
                String taskCode = TaskVarList.UPDATE;

                //check whether user_role have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        if (setUserInputToBean(request)) {

                            //validate user input
                            if (validateUserInput(questionBean)) {
                                //set values to the audit trace
                                this.setAudittraceValue(request);

                                boolean successUpdate = updateSecurityQuestion(questionBean, systemAuditBean);

                                if (successUpdate) {
                                    request.setAttribute("successMsg", " " + MessageVarList.SECURITY_QUES_MGT_QUESTION_NO_SUCCESS_UPDATE + "Security Question No " + questionBean.getQuestionNo());
                                    rd = getServletContext().getRequestDispatcher("/LoadAcquireSecurityQuestionServlet");
                                    rd.include(request, response);
                                } else {
                                    request.setAttribute("errorMsg", " " + MessageVarList.SECURITY_QUES_MGT_QUESTION_NO_UNSUCCESS_UPDATE + "Security Question No " + questionBean.getQuestionNo());
                                    rd = getServletContext().getRequestDispatcher("/LoadAcquireSecurityQuestionServlet");
                                    rd.include(request, response);
                                }

                            } else {
                                securityQuesMgr = new SecurityQuestionManager();
                                tables = securityQuesMgr.tablesToAcquireQuestions(request.getParameter("qtype"));
                                fields = securityQuesMgr.getColumnName(request.getParameter("table"));

                                this.getAllSecurityQuestions();
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("errorMsg", errorMessage);
                                request.setAttribute("tableList", tables);
                                request.setAttribute("fieldList", fields);
                                request.setAttribute("questionBean", questionBean);
                                request.setAttribute("questionList", questionList);

                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);
                            }
                        } else {

                            this.getAllSecurityQuestions();
                            request.setAttribute("questionList", questionList);
                            request.setAttribute("operationtype", "add");
                            request.setAttribute("questionBean", questionBean);
                            request.setAttribute("errorMsg", errorMessage);

                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }
                    } catch (Exception e) {
                        this.getAllSecurityQuestions();
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("questionBean", questionBean);
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        request.setAttribute("questionList", questionList);

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
        }
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

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(questionBean.getQuestionNo());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Acquire Sequrity Question. Question No: " + questionBean.getQuestionNo() + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SECURITYQUESTIONMGT);
            systemAuditBean.setPageCode(PageVarList.ACQ_SECURITY_QUES_MGT);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
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

    private void getAllSecurityQuestions() throws Exception {
        try {

            questionList = new ArrayList<SecurityQuestionBean>();
            securityQuesMgr = new SecurityQuestionManager();
            //retrieve merchant details
            questionList = securityQuesMgr.getAllACQSecurityQuestions();
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean validateUserInput(SecurityQuestionBean question) throws Exception {
        boolean isValidate = true;


        try {
            if (question.getQuestionNo().contentEquals("") || question.getQuestionNo().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SECURITY_QUES_MGT_QUESTION_NO_NULL;
            } else if (!UserInputValidator.isNumeric(question.getQuestionNo())) {
                isValidate = false;
                errorMessage = MessageVarList.SECURITY_QUES_MGT_QUESTION_NO_INVALID;
            } else if (question.getQuestion().contentEquals("") || question.getQuestion().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SECURITY_QUES_MGT_QUESTION_NULL;
            } else if (!UserInputValidator.isQuestion(question.getQuestion()) || question.getQuestion().contentEquals("'") || question.getQuestion().contentEquals("'''")) {
                isValidate = false;
                errorMessage = MessageVarList.SECURITY_QUES_MGT_QUESTION_INVALID;
            } else if (question.getPriorityLevel().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.SECURITY_QUES_MGT_PRIORITY_LEVEL_NULL;
            } else if (question.getQuestionType() == null) {
                isValidate = false;
                errorMessage = MessageVarList.SECURITY_QUES_MGT_QTYPE_NULL;
            } else if (question.getTableName().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.SECURITY_QUES_MGT_TABLE_NAME_NULL;
            } else if (question.getField1().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.SECURITY_QUES_MGT_FIELD_1_NULL;
            } else if (question.getStatus().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.SECURITY_QUES_MGT_QUESTION_STATUS_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            questionBean = new SecurityQuestionBean();

            questionBean.setQuestionNo(request.getParameter("questionid").trim());
            questionBean.setQuestion(request.getParameter("question").trim());
            questionBean.setPriorityLevel(request.getParameter("prioritycode"));
            questionBean.setIssueAcquireStatus(request.getParameter("issOracq"));
            questionBean.setQuestionType(request.getParameter("qtype"));
            questionBean.setTableName(request.getParameter("tablename"));
            questionBean.setField1(request.getParameter("field1"));
            questionBean.setField2(request.getParameter("field2"));
            questionBean.setField3(request.getParameter("field3"));
            questionBean.setField4(request.getParameter("field4"));
            questionBean.setStatus(request.getParameter("status"));
            questionBean.setLastUpdatedUser(sessionUser.getUserName());
            
            newValue = questionBean.getQuestionNo() + "|" + questionBean.getQuestion() + "|" + questionBean.getPriorityLevel() + "|"
                    + questionBean.getIssueAcquireStatus() + "|" + questionBean.getQuestionType() + "|" 
                    + questionBean.getTableName() + "|" + questionBean.getField1() + "|" + questionBean.getField2() + "|" + questionBean.getField3() + "|" + questionBean.getField4() + "|" + questionBean.getStatus() + "|" + questionBean.getLastUpdatedUser();            
            securityQuesMgr = new SecurityQuestionManager();
            SecurityQuestionBean   bean = securityQuesMgr.viewOneAcquireSecurityQuestion(questionBean.getQuestionNo());            
            
            oldValue = bean.getQuestionNo() + "|" + bean.getQuestion() + "|" + bean.getPriorityLevel() + "|"
                    + bean.getIssueAcquireStatus() + "|" + bean.getQuestionType() + "|" 
                    + bean.getTableName() + "|" + bean.getField1() + "|" + bean.getField2() + "|" + bean.getField3() + "|" + bean.getField4() + "|" + bean.getStatus() + "|" + bean.getLastUpdatedUser();              

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    public Boolean updateSecurityQuestion(SecurityQuestionBean question, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            securityQuesMgr = new SecurityQuestionManager();
            success = securityQuesMgr.updateSecurityQuestion(question, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
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
