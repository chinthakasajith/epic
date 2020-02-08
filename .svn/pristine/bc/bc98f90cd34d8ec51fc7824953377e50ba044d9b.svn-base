/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.TaskMgtManager;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterDetailsBean;
import com.epic.cms.backoffice.eodlettergeneration.businesslogic.LetterGenerationManager;
import com.epic.cms.camm.applicationconfirmation.businesslogic.ApplicationConfirmationManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
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
import com.epic.cms.system.util.variable.StatusVarList;
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
 * @author mahesh_m
 */
public class ApplicationRejectServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private TaskMgtManager taskManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private boolean successInsert = false;
    private String applicationid = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private ApplicationCheckingManager checkingmanager;
    private String rejectReason = null;
    private String remarks = null;
    private ApplicationConfirmationManager confirmationManager = null;
    private ApplicationConfirmationManager confirmManager;
    private ApplicationHistoryBean historybean = null;
    private boolean isOfficerReviewAndConfirm = false;
    private LetterGenerationManager letterGenerationManager=null;
    private LetterDetailsBean letterDetailsBean=null;

    private String url = "/camm/applicationconfirmation/confirmationtableview.jsp";

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
                String pageCode = PageVarList.APPLICATONAPPROVE;
                String taskCode = TaskVarList.REJECT;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    try {
                        applicationid = request.getParameter("applicationid");
                        String officerReviewAndConfirm = request.getParameter("officerReviewAndConfirm");
                        if (officerReviewAndConfirm != null && officerReviewAndConfirm.equals("YES")) {
                            isOfficerReviewAndConfirm = true;
                        }else{
                            isOfficerReviewAndConfirm=false;
                        }
                        checkingmanager = new ApplicationCheckingManager();
                        confirmationManager = new ApplicationConfirmationManager();
                        letterGenerationManager=new LetterGenerationManager();
                        this.setApplicationHistoryBean();

                        if (validateUserInput(request)) {
                            //set values to letterDetailsBean
                            this.setLetterDetailsValues(request);
                            //set audit values
                            this.setAudittraceValue(request);
                            
                            confirmationManager.insertRejectReasonAndRemarks(rejectReason, remarks, applicationid);
                            checkingmanager.UpdateCardApplicationStatus(applicationid, StatusVarList.APP_APPROVE_REJECT, systemAuditBean, historybean);
                            //save letter generation details
                            letterGenerationManager.saveLetterDetailsHaveToBeGenerated(letterDetailsBean);
                            request.setAttribute("successMsg", applicationid + " " + MessageVarList.APPROVE_REJECT_SUCESS);
                            if(isOfficerReviewAndConfirm){
                                rd = getServletContext().getRequestDispatcher("/LoadCreditOfficerReviewAndConfirmServlet");
                            }else{
                                rd = getServletContext().getRequestDispatcher("/LoadApplicationApproveTableServlet");
                            }
                            
                        } else {
                            request.setAttribute("applicationid", applicationid);
                            request.setAttribute("rejectReason", rejectReason);
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute("selectedtab", "0");
                            confirmManager = new ApplicationConfirmationManager();

                            String cardCategory = confirmManager.getApplicationCategory(applicationid);

                            if (cardCategory.equals(StatusVarList.CARD_CATEGORY_MAIN)) {
                                if(isOfficerReviewAndConfirm){
                                     rd = getServletContext().getRequestDispatcher("/LoadCreditofficerDetailsAndConfirmServlet");
                                }else{
                                     rd = getServletContext().getRequestDispatcher("/LoadApproveDetailsServlet");
                                }
                               
                            } else if (cardCategory.equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)) {
                                if(isOfficerReviewAndConfirm){
                                    rd = getServletContext().getRequestDispatcher("/LoadSupplementaryCreditofficerDetailsAndConfirmServlet");
                                }else{
                                    rd = getServletContext().getRequestDispatcher("/LoadSupplementaryApproveDetailsServlet");
                                }  
                            } else if (cardCategory.equals(StatusVarList.CARD_AGAINST_FD_CODE)) {
                                if(isOfficerReviewAndConfirm){
                                    rd = getServletContext().getRequestDispatcher("/LoadCardAgainstFDCreditOfficerDetailsAndConfirmServlet");
                                }else{
                                    rd = getServletContext().getRequestDispatcher("/LoadCardAgainstFDApproveDetailsServlet");
                                }
                            }else if(cardCategory.equals(StatusVarList.ESTABLISHMENT_CAT)){
                                if(isOfficerReviewAndConfirm){
                                    rd = getServletContext().getRequestDispatcher("/LoadEstablishmentCreditOfficerDetailsAndConfirmServlet");
                                }else{
                                    rd = getServletContext().getRequestDispatcher("/LoadEstablishmentApproveDetailsServlet");
                                }
                            
                            } else if (cardCategory.equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
                                if(isOfficerReviewAndConfirm){
                                    rd = getServletContext().getRequestDispatcher("/LoadCorporateCreditOfficerDetailsAndConfirmServlet");
                                }else{
                                    rd = getServletContext().getRequestDispatcher("/LoadCorporateApproveDetailsServlet");
                                }
                                
                            }
                        }

                        rd.forward(request, response);

                    } catch (Exception e) {
                        request.setAttribute("applicationid", "applicationid");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        request.setAttribute("selectedtab", "0");
                        confirmManager = new ApplicationConfirmationManager();

                        String cardCategory = confirmManager.getApplicationCategory(applicationid);

                        if (cardCategory.equals(StatusVarList.CARD_CATEGORY_MAIN)) {
                            rd = getServletContext().getRequestDispatcher("/LoadApproveDetailsServlet");
                        } else if (cardCategory.equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)) {
                            rd = getServletContext().getRequestDispatcher("/LoadSupplementaryApproveDetailsServlet");
                        } else if (cardCategory.equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
                        }

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

    public boolean validateUserInput(HttpServletRequest request) throws Exception {
        boolean isValidate = true;

        rejectReason = request.getParameter("rejectReason");
        remarks = request.getParameter("remark");

        //validate user Role code
        try {

            if (rejectReason.contentEquals("") || rejectReason.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APPROVE_REJECTREASON_INVALID;
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
            String uniqueId = request.getParameter("applicationid");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update " + uniqueId + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.APPLICATIONAPPROVE);
            systemAuditBean.setPageCode(PageVarList.APPLICATONAPPROVE);
            systemAuditBean.setTaskCode(TaskVarList.REJECT);
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
    //set values to letterdetails bean
    private void setLetterDetailsValues(HttpServletRequest request) throws Exception{
        try {
            letterDetailsBean=new LetterDetailsBean();
            
            letterDetailsBean.setId(request.getParameter("identificationNum"));
            letterDetailsBean.setIdType(request.getParameter("identificationType"));
            letterDetailsBean.setTmpCode(StatusVarList.APP_REJECT_LETTER_TMP);
            letterDetailsBean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean updateTask(TaskBean task, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            taskManager = new TaskMgtManager();
            success = taskManager.updateTask(task, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
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

    public void setApplicationHistoryBean() {
        historybean = new ApplicationHistoryBean();

        historybean.setApplicationId(applicationid);
        historybean.setApplicationLevel("CONF");
        historybean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
        historybean.setRemarks("Application Rejected");
        historybean.setStatus(StatusVarList.HISTORY_INCOMPLETE);
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
