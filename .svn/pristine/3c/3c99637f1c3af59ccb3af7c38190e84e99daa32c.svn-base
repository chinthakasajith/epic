/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.assigndata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.SearchAssignAppBean;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.system.util.comparator.MapValueSort;
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
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
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
public class UpdateDebitAppAssignServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private ApplicationAssignManager appAssignManager;
    private SystemAuditBean systemAuditBean = null;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private ApplicationAssignBean appAssignBean = null;
    private String errorMessage = null;
    private String cardCategory = "";
    private boolean success = false;
    private List<String> usersList = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> branchesDeatilsList = null;
    private List<ApplicationAssignBean> searchList = null;
    private HashMap<String, String> identificationList = null;
//    private HashMap<String, String> applicationDomainList;
    private String url = "/camm/assigningdata/debitappassignhome.jsp";
    private String newValue;

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



            this.getAllUserList();
            this.getAllPriorityLevelList();
            this.getAllBranchesDetailsList();

            Collections.sort(usersList);

            SortedMap sortedData = new TreeMap(new MapValueSort.ValueComparer(branchesDeatilsList));
            sortedData.putAll(branchesDeatilsList);

            this.getAllIdentificationType();
//            this.getAllApplicationDoamin();

            request.setAttribute("usersList", usersList);
            request.setAttribute("priorityLevelList", priorityLevelList);
            request.setAttribute("branchesDeatilsList", sortedData);
            request.setAttribute("identificationList", identificationList);
//            request.setAttribute("applicationDomainList", applicationDomainList);





            try {
                //set page code and task codes
                String pageCode = PageVarList.DEBITASSIGNUPDATE;
                String taskCode = TaskVarList.UPDATE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    request.setAttribute("operationtype", "update");

                    try {

                        cardCategory = request.getParameter("cardcategory").trim();

                    } catch (NullPointerException exception) {
                        cardCategory = "";
                    }


                    String oldValue = request.getParameter("oldvalue");
                    this.setUserInputToBean(request);
                    this.searchAssignApplication(sessionVarlist.getSearchAssignAppBean());


                    request.setAttribute("searchList", searchList);
                    request.setAttribute("assignappbean", appAssignBean);


                    if (this.validateUserInput(appAssignBean, request)) {


                        this.setAudittraceValue(request, oldValue, newValue);

                        if (this.updateAssignApplication(appAssignBean, systemAuditBean)) {
                            request.setAttribute("operationtype", "add");
                            request.setAttribute("successMsg", MessageVarList.SUCCESS_UPDATE_ASSIGN_APPLICATION);
                            rd = getServletContext().getRequestDispatcher("/LoadDebitAssignAppSearchServlet");
                        } else {
                            request.setAttribute("errorMsg", MessageVarList.ERROR_UPDATE_ASSIGN_APPLICATION);
                            rd = getServletContext().getRequestDispatcher(url);
                        }



                    } else {
                        rd = getServletContext().getRequestDispatcher(url);
                        request.setAttribute("errorMsg", errorMessage);

                    }
                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }


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


        } catch (SQLException ex) {

            request.setAttribute("operationtype", "update");
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "update");
            request.setAttribute("errorMsg", MessageVarList.ERROR_UPDATE_ASSIGN_APPLICATION);
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

    public void setUserInputToBean(HttpServletRequest request) {



        String appliactionId = request.getParameter("appliactionid").trim();
        String identityType = request.getParameter("identityType").trim();
        String identityNo = request.getParameter("identityno").trim();
        String priorityCode = request.getParameter("prioritycode").trim();
        String referEmloyeeNo = request.getParameter("referemloyeeno").trim();
        String referBranchCode = request.getParameter("referbranchcode").trim();
        String assignUser = request.getParameter("assignuser").trim();



        appAssignBean = new ApplicationAssignBean();

        appAssignBean.setApplicationDomain(StatusVarList.DEBIT);
        appAssignBean.setCardCategory(cardCategory);
        appAssignBean.setApplicationId(appliactionId);
        appAssignBean.setAssignUser(assignUser);
        appAssignBean.setIdentityNo(identityNo);
        appAssignBean.setIdentityType(identityType);
        appAssignBean.setPriorityLevel(priorityCode);
        appAssignBean.setReferralBranchId(referBranchCode);
        appAssignBean.setReferralEmpNo(referEmloyeeNo);


        appAssignBean.setLastUpdatedUser(sessionUser.getUserName());
        appAssignBean.setLastUpdatedTime(new Date());
        newValue = appAssignBean.getApplicationDomain() + "|" + appAssignBean.getIdentityType() + "|" + appAssignBean.getIdentityNo() + "|" + appAssignBean.getPriorityLevel() + "|" + appAssignBean.getReferralEmpNo() + "|" + appAssignBean.getReferralBranchId() + "|" + appAssignBean.getAssignUser();


    }

    public boolean validateUserInput(ApplicationAssignBean assignBean, HttpServletRequest request) throws Exception {
        boolean isValid = true;


        //validate user Role code
        try {

            if (assignBean.getCardCategory().contentEquals("") || assignBean.getCardCategory().length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.APPASSIGN_APPTYPE_NULL;
            } //            else if (assignBean.getApplicationId().contentEquals("") || assignBean.getApplicationId().length() <= 0) {
            //                isValid = false;
            //                errorMessage = MessageVarList.APPASSIGN_APPLICATIONCODE_NULL;
            //            } 
            //            else if (!UserInputValidator.isCorrectString(assignBean.getApplicationId())) {
            //                isValid = false;
            //                errorMessage = MessageVarList.APPASSIGN_APPLICATIONCODE_INVALID;
            //
            //            } 
            else if (assignBean.getIdentityType().contentEquals("") || assignBean.getIdentityType().length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.SELECT_ONE_IDENTITY;
            } else if (assignBean.getIdentityNo().contentEquals("") || assignBean.getIdentityNo().length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.APPASSIGN_IDENTITY_NUMBER_NULL;
            } else if (assignBean.getPriorityLevel().contentEquals("") || assignBean.getPriorityLevel().length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.APPASSIGN_PRIORITY_NULL;
            } else if (assignBean.getAssignUser().contentEquals("") || assignBean.getAssignUser().length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.APPASSIGN_ASSIGNUSER_NULL;

            } else {
                if (!assignBean.getReferralEmpNo().equals("") || assignBean.getReferralEmpNo().length() > 0) {
                    if (!UserInputValidator.isAlphaNumeric(assignBean.getReferralEmpNo())) {
                        isValid = false;
                        errorMessage = MessageVarList.ERROR_REFEMP_NUMBER;
                    }
                }
                if (assignBean.getIdentityType().equals(StatusVarList.NATIONAL_IDENTITY_CARD)) {
                    if (!UserInputValidator.checkNIC(assignBean.getIdentityNo())) {
                        isValid = false;
                        errorMessage = MessageVarList.ERROR_NIC_NUMBER;
                    }
                } else if (assignBean.getIdentityType().equals(StatusVarList.PASSPORT)) {
                    if (!UserInputValidator.isAlphaNumeric(assignBean.getIdentityNo())) {
                        isValid = false;
                        errorMessage = MessageVarList.ERROR_PAS_NUMBER;
                    }

                } else if (assignBean.getIdentityType().equals(StatusVarList.DRIVING_LICENSE)) {
                    if (!UserInputValidator.isAlphaNumeric(assignBean.getIdentityNo())) {
                        isValid = false;
                        errorMessage = MessageVarList.ERROR_DRL_NUMBER;
                    }

                }

            }

        } catch (Exception ex) {
            isValid = false;

        }

        return isValid;
    }

    private void getAllUserList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            usersList = appAssignManager.getAllUserList(PageVarList.DEBITASSIGNUPDATE);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllPriorityLevelList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            priorityLevelList = appAssignManager.getAllPriorityLevels();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllBranchesDetailsList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            branchesDeatilsList = appAssignManager.getAllBranchesDetails();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllIdentificationType() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            identificationList = appAssignManager.getAllIdentificationType();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean updateAssignApplication(ApplicationAssignBean assignBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            appAssignManager = new ApplicationAssignManager();
            success = appAssignManager.updateAssignApplication(assignBean, systemAuditBean);

        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private void setAudittraceValue(HttpServletRequest request, String old, String newV) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(appAssignBean.getApplicationId());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update application assigning, application id: " + appAssignBean.getApplicationId() + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.APPLICATIONASSIGN);
            systemAuditBean.setPageCode(PageVarList.DEBITASSIGNUPDATE);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(old);
            systemAuditBean.setNewValue(newV);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }



    }

    private void searchAssignApplication(SearchAssignAppBean searchBean) throws Exception {

        try {

            appAssignManager = new ApplicationAssignManager();
            searchList = appAssignManager.getAllSearchList(searchBean);

        } catch (Exception ex) {
            throw ex;
        }
    }

//    private void getAllApplicationDoamin() throws Exception {
//        try {
//            appAssignManager = new ApplicationAssignManager();
//            applicationDomainList = appAssignManager.getAllApplicationDoamin();
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
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
