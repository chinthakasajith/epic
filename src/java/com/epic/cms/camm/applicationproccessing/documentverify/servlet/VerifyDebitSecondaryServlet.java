/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.DebitCheckBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.DebitVerifyBean;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.DocumentVerifyManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chanuka
 */
public class VerifyDebitSecondaryServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private DocumentVerifyManager documentVerifyManager = null;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private DebitVerifyBean verifyBean;
    private SystemAuditBean systemAuditBean = null;
    private String errorMessage, option = null;
    private ApplicationHistoryBean appHistoryBean = null;
    private boolean success = false;
    private DebitCheckBean debitBean = null;
    private String applicationId = null;
    private String url = "/camm/documentverification/debitsecondaryviewhome.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {


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



            try {
                //set page code and task codes
                String pageCode = PageVarList.DEBITVERIFYHOME;
                String taskCode = TaskVarList.VERIFY;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                    applicationId = request.getParameter("applicationid");
                    option = request.getParameter("option");

                    this.setUserInputToBean(request);
                    request.setAttribute("verifyBean", verifyBean);

                    if (option.equals("verify")) {

                        if (this.validateUserInput(verifyBean, request)) {

                            this.setHistoryBean("verified");

                            if (this.isExistCardApplication(verifyBean.getApplicationId())) {

                                this.setAudittraceValue(request);
                                this.updateDebitVerifyDetails(verifyBean, "VCOM", appHistoryBean, systemAuditBean);
                                request.setAttribute("successMsg", MessageVarList.SUCCES_DOC_VERIFY);
                                rd = request.getRequestDispatcher("/LoadDebitVerifySearchServlet");

                            } else {

                                //check for the record existance for this application id..if doesn't exist then insert the details

                                this.setAudittraceValue(request);
                                this.insertDebitVerifyDetails(verifyBean, "VCOM", appHistoryBean, systemAuditBean);

                                request.setAttribute("successMsg", MessageVarList.SUCCES_DOC_VERIFY);
                                rd = request.getRequestDispatcher("/LoadDebitVerifySearchServlet");

                            }

                        } else {

                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }


                    }
                    if (option.equals("onhold")) {

                        this.setHistoryBean("sent to onhold status");

                        if (this.isExistCardApplication(verifyBean.getApplicationId())) {

                            this.setAudittraceValue(request);
                            this.updateDebitVerifyDetails(verifyBean, "VONH", appHistoryBean, systemAuditBean);
                            request.setAttribute("successMsg", MessageVarList.SUCCES_DOC_ONHOLD);
                            rd = request.getRequestDispatcher("/LoadDebitVerifySearchServlet");
                        } else {

                            //check for the record existance for this application id..if doesn't exist then insert the details

                            this.setAudittraceValue(request);
                            this.insertDebitVerifyDetails(verifyBean, "VONH", appHistoryBean, systemAuditBean);

                            request.setAttribute("successMsg", MessageVarList.SUCCES_DOC_ONHOLD);
                            rd = request.getRequestDispatcher("/LoadDebitVerifySearchServlet");

                        }




                    }
                    if (option.equals("reject")) {

                        this.setHistoryBean("rejected");

                        if (this.isExistCardApplication(verifyBean.getApplicationId())) {

                            this.setAudittraceValue(request);
                            this.updateDebitVerifyDetails(verifyBean, "VREJ", appHistoryBean, systemAuditBean);
                            request.setAttribute("successMsg", MessageVarList.SUCCES_DOC_REJECT);
                            rd = request.getRequestDispatcher("/LoadDebitVerifySearchServlet");

                        } else {

                            //check for the record existance for this application id..if doesn't exist then insert the details

                            this.setAudittraceValue(request);
                            this.insertDebitVerifyDetails(verifyBean, "VREJ", appHistoryBean, systemAuditBean);

                            request.setAttribute("successMsg", MessageVarList.SUCCES_DOC_REJECT);
                            rd = request.getRequestDispatcher("/LoadDebitVerifySearchServlet");

                        }




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

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);


        } catch (Exception ex) {
            request.setAttribute("selectedtab", "0");
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_ASSIGN_APP);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    ///////////////////////////
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

    public void setUserInputToBean(HttpServletRequest request) throws Exception {
        String docid, id, name, accNo, sName, sAccNo, sId, sNic;
        try {

            docid = request.getParameter("checkdocid");
            id = request.getParameter("checkid");
            name = request.getParameter("checkname");
            accNo = request.getParameter("checkaccno");
            sNic = request.getParameter("checksnic");
            sAccNo = request.getParameter("checksaccno");
            sId = request.getParameter("checksid");
            sName = request.getParameter("checksname");




            verifyBean = new DebitVerifyBean();


            verifyBean.setApplicationId(applicationId);
            verifyBean.setLastUpdatedUser(sessionUser.getUserName());
            verifyBean.setLastUpdatedTime(new Date());

            if (docid != null) {
                verifyBean.setDocId("YES");
            } else {
                verifyBean.setDocId("NO");
            }
            if (accNo != null) {
                verifyBean.setAccontNo("YES");
            } else {
                verifyBean.setAccontNo("NO");
            }

            if (id != null) {
                verifyBean.setId("YES");
            } else {
                verifyBean.setId("NO");
            }

            if (name != null) {
                verifyBean.setName("YES");
            } else {
                verifyBean.setName("NO");
            }
            if (sNic != null) {
                verifyBean.setsNic("YES");
            } else {
                verifyBean.setsNic("NO");
            }
            if (sAccNo != null) {
                verifyBean.setsAccNo("YES");
            } else {
                verifyBean.setsAccNo("NO");
            }
            if (sId != null) {
                verifyBean.setsId("YES");
            } else {
                verifyBean.setsId("NO");
            }
            if (sName != null) {
                verifyBean.setsName("YES");
            } else {
                verifyBean.setsName("NO");
            }

        } catch (Exception ee) {
            throw ee;
        }
    }

    private void setHistoryBean(String option) {

        appHistoryBean = new ApplicationHistoryBean();

        appHistoryBean.setApplicationId(verifyBean.getApplicationId());
        appHistoryBean.setApplicationLevel("VERI");
        appHistoryBean.setStatus("HCOM");
        appHistoryBean.setRemarks("Application is " + option);
        appHistoryBean.setLastUpdatedUser(sessionUser.getUserName());
    }

    public boolean validateUserInput(DebitVerifyBean cardBean, HttpServletRequest request) throws Exception {
        boolean isValidate = true;

        try {
            if (cardBean.getDocId().contentEquals("NO")) {
                isValidate = false;
                request.setAttribute("selectedtab", "0");
                errorMessage = MessageVarList.ERROR_DOCID_NOT_CHECK;

            } else if (cardBean.getAccontNo().contentEquals("NO")) {
                isValidate = false;
                request.setAttribute("selectedtab", "0");
                errorMessage = MessageVarList.ERROR_ACCNO_NOT_CHECK;

            } else if (cardBean.getId().contentEquals("NO")) {
                isValidate = false;
                request.setAttribute("selectedtab", "0");
                errorMessage = MessageVarList.ERROR_ID_NOT_CHECK;
            } else if (cardBean.getName().contentEquals("NO")) {
                isValidate = false;
                request.setAttribute("selectedtab", "0");
                errorMessage = MessageVarList.ERROR_NAME_NOT_CHECK;
            } else if (cardBean.getsNic().contentEquals("NO")) {
                isValidate = false;
                request.setAttribute("selectedtab", "1");
                errorMessage = MessageVarList.ERROR_DOCID_NOT_CHECK;
            } else if (cardBean.getsAccNo().contentEquals("NO")) {
                isValidate = false;
                request.setAttribute("selectedtab", "1");
                errorMessage = MessageVarList.ERROR_ACCNO_NOT_CHECK;
            } else if (cardBean.getsId().contentEquals("NO")) {
                isValidate = false;
                request.setAttribute("selectedtab", "1");
                errorMessage = MessageVarList.ERROR_ID_NOT_CHECK;
            } else if (cardBean.getsName().contentEquals("NO")) {
                isValidate = false;
                request.setAttribute("selectedtab", "1");
                errorMessage = MessageVarList.ERROR_NAME_NOT_CHECK;
            }
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Verify debit card application, application id: " + verifyBean.getApplicationId() + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.DOCUMENTVERIFY);
            systemAuditBean.setPageCode(PageVarList.DEBITVERIFYHOME);
            if ("Verify".equals(option)) {
                systemAuditBean.setTaskCode(TaskVarList.VERIFY);
            }
            if ("Onhold".equals(option)) {
                systemAuditBean.setTaskCode(TaskVarList.ONHOLD);
            }
            if ("Reject".equals(option)) {
                systemAuditBean.setTaskCode(TaskVarList.REJECT);
            }
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue("");


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }



    }

    private boolean isExistCardApplication(String applicationId) throws Exception {
        boolean flag = false;
        try {

            documentVerifyManager = new DocumentVerifyManager();
            flag = documentVerifyManager.isExistCardApplication(applicationId);
        } catch (Exception ex) {
            throw ex;
        }
        return flag;
    }

    private boolean updateDebitVerifyDetails(DebitVerifyBean checkedApplicantBean, String applicationStatus, ApplicationHistoryBean historyBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            success = documentVerifyManager.updateDebitVerifyDetails(checkedApplicantBean, historyBean, applicationStatus, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private boolean insertDebitVerifyDetails(DebitVerifyBean checkedApplicantBean, String applicationStatus, ApplicationHistoryBean historyBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            success = documentVerifyManager.insertDebitVerifyDetails(checkedApplicantBean, applicationStatus, historyBean, systemAuditBean);
        } catch (Exception ex) {
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
