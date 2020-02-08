/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.InterestprofileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionTypeBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.InterestProfileManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.PageMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
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
 * @author mahesh_m
 */
public class UpdateConfiremedInterestprofileMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager pageMgt;
    HttpSession sessionObj = null;
    private List<StatusBean> statusList;
    private SystemUserManager systemUserManager = null;
    private List<TaskBean> taskbeanList = null;
    private InterestProfileManager interestManager;
    private List<PageBean> pageBeanList = null;
    private PageBean pageBean;
    private String errorMessage = null;
    private String[] assignedList = null;
    private SystemAuditBean systemAuditBean = null;
    private boolean successInsert = false;
    private PageBean page;
    private boolean successInsertToInterestprofileTransaction;
    List<String> existTaskList = null;
    private List<TaskBean> assignedTaskDescriptions;
    private List<String> userTaskList;
    private InterestprofileBean interestProfileBean;
    private List<TransactionTypeBean> notAssignedTransactionTypes;
    private InterestprofileBean interestBean = null;
    private List<TransactionTypeBean> assignedTransactions;
    private String url = "/administrator/controlpanel/profilemgt/interestprofilehome.jsp";
    private String oldValue;
    private String newValue;

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
                String pageCode = PageVarList.INTERESTPROFILE;
                String taskCode = TaskVarList.UPDATE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {

//                assignedList = request.getParameterValues("assignsectionlist");

                        String[] notToAssign = request.getParameterValues("notassignsectionlist");
                        String[] toAssign = request.getParameterValues("assignsectionlist");


                        this.setUserInputToBean(request);


//                        String interestprofileCode = request.getParameter("interestProfileCode");
//
//                        for (InterestprofileBean interest : sessionVarlist.getInterestProfileDetails()) {
//                            if (interest.getInterestFrofileCode().equals(interestprofileCode)) {
//                                interestBean = interest;
//                            }
//                        }
//
//                        notAssignedTransactionTypes = new ArrayList<TransactionTypeBean>();
//                        interestManager = new InterestProfileManager();
//                        notAssignedTransactionTypes = interestManager.getTransactionTypeDescriptionNotAssignedList(interestBean.getInterestFrofileCode());
//
//
//
//
//                        getAssignedTransactions(interestBean.getInterestFrofileCode());
//




                        if (validateUserInput(interestProfileBean, toAssign)) {
                            request.setAttribute("operationtype", "default");
                            this.setAudittraceValue(request);
                            successInsert = updateInterestProfile(interestProfileBean, systemAuditBean);

                            successInsertToInterestprofileTransaction = insertToInterestProfilrTransaction(interestProfileBean, toAssign);

                            if (successInsert && successInsertToInterestprofileTransaction) {
//                                request.setAttribute("successMsg", interestProfileBean.getInterestFrofileCode() + " " + MessageVarList.TASK_SUCCESS_UPDATE);

                                out.print("success," + interestProfileBean.getInterestFrofileCode() + " " + MessageVarList.TASK_SUCCESS_UPDATE);

//                                rd = getServletContext().getRequestDispatcher("/ViewInterestProfileTableServlet");
                            } else {
//                                request.setAttribute("InterestBean", interestProfileBean);
//                                request.setAttribute("notAssigne", notAssignedTransactionTypes);
//                                request.setAttribute("assigned", assignedTransactions);
//                                request.setAttribute("operationtype", "update");
//                                request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
//                                rd = getServletContext().getRequestDispatcher(url);

                                out.print(errorMessage);
                            }

                        } else {
//                            request.setAttribute("InterestBean", interestProfileBean);
//                            request.setAttribute("notAssigne", notAssignedTransactionTypes);
//                            request.setAttribute("assigned", assignedTransactions);
//                            request.setAttribute("operationtype", "update");
//                            request.setAttribute("errorMsg", errorMessage);
//                            rd = getServletContext().getRequestDispatcher(url);

                            out.print(errorMessage);
                        }
                    } catch (Exception e) {
//                        request.setAttribute("InterestBean", interestProfileBean);
//                        request.setAttribute("notAssigne", notAssignedTransactionTypes);
//                        request.setAttribute("assigned", assignedTransactions);
//                        request.setAttribute("operationtype", "update");
//                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
//                        rd = getServletContext().getRequestDispatcher(url);

                        out.print(errorMessage);
                    }

//                    rd.forward(request, response);


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
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/pagemanagement.jsp?errorMsg=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/pagemanagement.jsp?errorMsg=" + MessageVarList.LAST_SESSION_CLOSE);
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

    public void setUserInputToBean(HttpServletRequest request) throws Exception {
        try {

            String interestprofileCode = request.getParameter("profileCode").trim();
            String description = request.getParameter("description").trim();
            String statusCode = request.getParameter("selectstatuscode");
            String interestRate = request.getParameter("interestRate").trim();
            String interestType = request.getParameter("period");
            String effectDateForNewAccount = request.getParameter("effectDateNewAcc");
            String effectDateForExistingAccounts = request.getParameter("effectDateExistingAcc");
            String startingFrom = request.getParameter("startFrom");
            String to = request.getParameter("to");
            String chargeType = request.getParameter("chargeType");
            String customValue = null;
            if (request.getParameter("periodCustom") != null) {
                customValue = request.getParameter("periodCustom").trim();
            }


            interestProfileBean = new InterestprofileBean();

            interestProfileBean.setInterestFrofileCode(interestprofileCode);
            interestProfileBean.setDescription(description);
            interestProfileBean.setStatus(statusCode);
            interestProfileBean.setInterestRate(interestRate);
            interestProfileBean.setInterestPeriodValue(interestType);
            interestProfileBean.setCustomValue(customValue);
            interestProfileBean.setEffectiveDateForNewCustomer(effectDateForNewAccount);
            interestProfileBean.setEffectiveDateForExistCustomer(effectDateForExistingAccounts);
            interestProfileBean.setInterestCalStartFrom(startingFrom);
            interestProfileBean.setInterestCalStartTo(to);
            interestProfileBean.setChargeType(chargeType);
            interestProfileBean.setLastUpdatedUser(sessionUser.getUserName());

            String interestPeriodVal = "";
            if(interestProfileBean.getInterestPeriodValue().equals("daily")){
                interestPeriodVal = "1";
            }else if(interestProfileBean.getInterestPeriodValue().equals("annual")){
                interestPeriodVal  = "365";
            }else if(interestProfileBean.getInterestPeriodValue().equals("custom")){
                interestPeriodVal = interestProfileBean.getCustomValue();
            }            
            
            newValue = interestProfileBean.getInterestFrofileCode() + "|" + interestProfileBean.getDescription() + "|"
                    + interestProfileBean.getStatus() + "|" + interestProfileBean.getInterestRate() + "|" + interestPeriodVal + "|"
                    + interestProfileBean.getEffectiveDateForNewCustomer() + "|" + interestProfileBean.getEffectiveDateForExistCustomer() + "|"
                    + interestProfileBean.getInterestCalStartFrom() + "|" + interestProfileBean.getInterestCalStartTo() + "|" + interestProfileBean.getChargeType() + "|" + interestProfileBean.getLastUpdatedUser();

            for (InterestprofileBean interest : sessionVarlist.getInterestProfileDetails()) {
                if (interest.getInterestFrofileCode().equals(interestprofileCode)) {

                    oldValue = interest.getInterestFrofileCode() + "|" + interest.getDescription() + "|"
                            + interest.getStatus() + "|" + interest.getInterestRate() + "|" + interest.getInterestPeriodValue() + "|"
                            + interest.getEffectiveDateForNewCustomer() + "|" + interest.getEffectiveDateForExistCustomer() + "|"
                            + interest.getInterestCalStartFrom() + "|" + interest.getInterestCalStartTo() + "|" + interest.getChargeType() + "|" + sessionVarlist.getCMSSessionUser().getUserName();

                }
            }


        } catch (Exception e) {
            throw e;
        }

    }

    public boolean validateUserInput(InterestprofileBean interest, String[] assigned) throws Exception {
        boolean isValidate = true;

        try {

//            if (interest.getCustomValue() == null || interest.getCustomValue().contentEquals("")) {
            if (interest.getInterestFrofileCode().contentEquals("") || interest.getInterestFrofileCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_PROFILECODE_NULL;
            } else if (!UserInputValidator.isAlphaNumeric(interest.getInterestFrofileCode())) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_PROFILECODE_INVALID;
            } else if (interest.getDescription().contentEquals("") || interest.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_DESCRIPTION_NULL;
            } else if (!UserInputValidator.isDescription(interest.getDescription())) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_DESCRIPTION_INVALID;
            } else if (interest.getStatus().contentEquals("") || interest.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_STATUS_NULL;
            } else if (interest.getInterestRate().contentEquals("") || interest.getInterestRate().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_INTRESTRATE_NULL;
            } else if (!UserInputValidator.isDecimal_Numeric(interest.getInterestRate()) || (Double.parseDouble(interest.getInterestRate()) > 100.00)) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_INTRESTRATE_INVALID;
            } else if (interest.getInterestPeriodValue() == null || interest.getInterestPeriodValue().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_INTRESTTYPE_NULL;
            } else if (interest.getInterestPeriodValue().equals("custom") && (interest.getCustomValue() == null || interest.getCustomValue().contentEquals(""))) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_PERIOD_NULL;
            } else if (!UserInputValidator.isDecimal_Numeric(interest.getCustomValue())) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_PERIOD_INVALID;
            } else if (interest.getEffectiveDateForNewCustomer() == null || interest.getEffectiveDateForNewCustomer().isEmpty()) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_EFFECTDATENEWACC_NULL;
            } else if (interest.getEffectiveDateForExistCustomer() == null || interest.getEffectiveDateForExistCustomer().isEmpty()) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_EFFECTDATEEXISTACC_NULL;
            } else if (interest.getInterestCalStartFrom() == null || interest.getInterestCalStartFrom().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_FROMDATE_NULL;
            } else if (interest.getInterestCalStartTo() == null || interest.getInterestCalStartTo().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_TODATE_NULL;
            } else if (interest.getChargeType() == null || interest.getChargeType().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_CHARGETYPE_NULL;
            } else if (assigned == null || assigned.length <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.INTEREST_ASSIGNEDTRANSACTIONS_NULL;
            }


        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        if (isValidate) {
            errorMessage = "";
        }
        return isValidate;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(interestProfileBean.getInterestFrofileCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update interest profile. Profile code: " + interestProfileBean.getInterestFrofileCode() + " by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.PROFILEMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.INTERESTPROFILE);
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

    public boolean updateInterestProfile(InterestprofileBean interest, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            interestManager = new InterestProfileManager();
            success = interestManager.updateInterestProfile(interest, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    public boolean insertToInterestProfilrTransaction(InterestprofileBean interest, String[] assigned) throws Exception {
        boolean success = false;
        try {
            interestManager = new InterestProfileManager();
            success = interestManager.updateInterestProfileTransaction(interest, assigned);
        } catch (Exception e) {
            throw e;
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

    private void getAssignedTransactions(String interestProfileCode) throws Exception {

        try {
            assignedTransactions = new ArrayList<TransactionTypeBean>();
            interestManager = new InterestProfileManager();
            assignedTransactions = interestManager.getAssignedTransactions(interestProfileCode);

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
