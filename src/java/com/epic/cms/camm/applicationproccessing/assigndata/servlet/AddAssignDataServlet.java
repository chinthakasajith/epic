/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.assigndata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.UserAssignApplicationBean;
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
import java.awt.BorderLayout;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
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
import org.jpos.iso.ISOUtil;

/**
 *
 * @author chanuka
 */
public class AddAssignDataServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private ApplicationAssignManager appAssignManager;
    private SystemAuditBean systemAuditBean = null;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private List<String> usersList = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> branchesDeatilsList = null;
    private ApplicationAssignBean appAssignBean = null;
    private ApplicationHistoryBean appHistoryBean = null;
    private List<UserAssignApplicationBean> userAssignApps = null;
    private HashMap<String, String> identificationList = null;
    private List<ApplicationAssignBean> previousApplicationList = null;
    private HashMap<String, String> applicationDomainList;
    private HashMap<String,String> userMap=null;
    private String errorMessage = null;
    private String cardCategory = null;
    private String maxId = null;
    private boolean success = false;
    private String url = "/camm/assigningdata/appassignhome.jsp";

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


           // this.getAllUserList();
            this.getAllPriorityLevelList();
            this.getAllBranchesDetailsList();


            //Collections.sort(usersList);

            SortedMap sortedData = new TreeMap(new MapValueSort.ValueComparer(branchesDeatilsList));
            sortedData.putAll(branchesDeatilsList);

            this.getAllUserAssignAppsDetails(StatusVarList.CREDIT);
           // this.getAllIdentificationType();
            this.getAllApplicationDoamin();

            //request.setAttribute("usersList", userMap);
            request.setAttribute("priorityLevelList", priorityLevelList);
            request.setAttribute("branchesDeatilsList", sortedData);
            request.setAttribute("userAssignApps", userAssignApps);
            //request.setAttribute("identificationList", identificationList);
            request.setAttribute("applicationDomainList", applicationDomainList);


            try {
                //set page code and task codes
                String pageCode = PageVarList.CAMMDATAASSIGN;
                String taskCode = TaskVarList.ADD;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    request.setAttribute("operationtype", "add");

                    try {

                        cardCategory = request.getParameter("cardcategory").trim();
                        if(cardCategory!=null){
                            this.getIdentificationTypeByApplicationType(cardCategory);
                            this.getUserListByApplicationType(cardCategory);
                        }else{
                            this.getAllIdentificationType();
                            this.getAllUserList();
                        }
                        
                        

                    } catch (NullPointerException exception) {
                        this.getAllIdentificationType();
                        this.getAllUserList();
                        cardCategory = "";

                    }
                    request.setAttribute("identificationList", identificationList);
                    request.setAttribute("usersList", userMap);
                    


                    this.setUserInputToBean(request);
                    request.setAttribute("assignappbean", appAssignBean);


                    if (this.validateUserInput(appAssignBean, request)) {

                        if (previousApplicationList != null) {
                            previousApplicationList.clear();
                        }
                        this.getPreviousApplication(appAssignBean.getIdentityNo());

                        //if the customer hasn't previously requested card application..
                        if (!previousApplicationList.isEmpty()) {

                            request.setAttribute("previousApplicationList", previousApplicationList);
                            request.setAttribute("operationtype", "confirm");
                            rd = getServletContext().getRequestDispatcher(url);

                        } else {

                            this.getMaxFromCardApplication(appAssignBean.getApplicationDomain());
                            String prefix = this.getApplicationDomainPrefix(appAssignBean.getApplicationDomain());

                            String applicationId = this.setApplicationId(maxId);

                            if (maxId.equals("0")) {

                                applicationId = prefix.concat("0000000001");
                            }
                            appAssignBean.setApplicationId(applicationId);
                            appHistoryBean.setApplicationId(applicationId);

                            this.setAudittraceValue(request);

                            if (this.insertAssignApplication(appAssignBean, appHistoryBean, systemAuditBean)) {

                                request.setAttribute("successMsg", MessageVarList.SUCCESS_ADD_ASSIGN_APPLICATION + " -ID :" + applicationId);
                                rd = getServletContext().getRequestDispatcher("/LoadAssignDataServlet");

                            } else {

                                request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_ASSIGN_APPLICATION);
                                rd = getServletContext().getRequestDispatcher(url);
                            }

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
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {

            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_ASSIGN_APPLICATION);
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


//        String appliactionId = request.getParameter("appliactionid").trim();
        String identityType = request.getParameter("identityType").trim();
        String expirydate=request.getParameter("expirydate").trim();
        String identityNo = request.getParameter("identityno").trim();
        String priorityCode = request.getParameter("prioritycode").trim();
        String referEmloyeeNo = request.getParameter("referemloyeeno").trim();
        String referBranchCode = request.getParameter("referbranchcode").trim();
        String assignUser = request.getParameter("assignuser").trim();



        appAssignBean = new ApplicationAssignBean();
        appHistoryBean = new ApplicationHistoryBean();

        appAssignBean.setApplicationDomain(StatusVarList.CREDIT);
        appAssignBean.setCardCategory(cardCategory);
        appAssignBean.setAssignUser(assignUser);
        appAssignBean.setIdentityNo(identityNo);
        appAssignBean.setIdentityType(identityType);
        appAssignBean.setPriorityLevel(priorityCode);
        appAssignBean.setReferralBranchId(referBranchCode);
        appAssignBean.setReferralEmpNo(referEmloyeeNo);
        appAssignBean.setExpirydate(expirydate);


        appAssignBean.setLastUpdatedUser(sessionUser.getUserName());

        appHistoryBean.setApplicationLevel("ASGN");
        appHistoryBean.setLastUpdatedUser(sessionUser.getUserName());
        appHistoryBean.setStatus("HCOM");

    }

    public boolean validateUserInput(ApplicationAssignBean assignBean, HttpServletRequest request) throws Exception {
        boolean isValid = true;


        try {

            if (assignBean.getApplicationDomain().contentEquals("") || assignBean.getApplicationDomain().length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.SELECT_ONE_DOMAIN;
            } else if (assignBean.getCardCategory().contentEquals("") || assignBean.getCardCategory().length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.APPASSIGN_APPTYPE_NULL;
            } else if (assignBean.getIdentityType().contentEquals("") || assignBean.getIdentityType().length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.SELECT_ONE_IDENTITY;
            }else if(assignBean.getIdentityType().contentEquals("PAS") && (assignBean.getExpirydate().contentEquals("")||assignBean.getExpirydate().length()<=0) ){
                isValid =false;
                errorMessage=MessageVarList.ERROR_EXPIRY_DATE;
            }else if (assignBean.getIdentityNo().contentEquals("") || assignBean.getIdentityNo().length() > 16) {
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
                if (assignBean.getIdentityType().equals(StatusVarList.NATIONAL_IDENTITY_CARD)
                    ||assignBean.getIdentityType().equals(StatusVarList.NATIONAL_IDENTITY_CARD_FXD)
                        ||assignBean.getIdentityType().equals(StatusVarList.NATIONAL_IDENTITY_CARD_COP)
                        ||assignBean.getIdentityType().equals(StatusVarList.NATIONAL_IDENTITY_CARD_SUP)) {
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

                if(assignBean.getCardCategory().equals(StatusVarList.CARD_APP_CATEGORY_ESTABLISMENT_CODE)){
                    appAssignManager = new ApplicationAssignManager();
                    //get all tasks for userrole for this page
                    boolean isUniqueBRC = appAssignManager.checkUniqueEstablishment(assignBean.getIdentityNo());
                    if (!isUniqueBRC) {
                        isValid = false;
                        errorMessage = MessageVarList.ERROR_BRC_UNIQE;
                    }

                }

            }

        } catch (Exception ex) {
            isValid = false;

        }

        return isValid;
    }

    private boolean insertAssignApplication(ApplicationAssignBean assignBean, ApplicationHistoryBean historyBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            appAssignManager = new ApplicationAssignManager();
            success = appAssignManager.insertAssignApplication(assignBean, historyBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private boolean insertApplicationHistory(ApplicationHistoryBean historyBean) throws Exception {

        try {

            appAssignManager = new ApplicationAssignManager();
            success = appAssignManager.insertApplicationHistory(historyBean);

        } catch (Exception ex) {
            return success;
        }
        return success;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(appAssignBean.getApplicationId());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description
            systemAuditBean.setDescription("Assign application, application id: " + appAssignBean.getApplicationId() + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.APPLICATIONASSIGN);
            systemAuditBean.setPageCode(PageVarList.CAMMDATAASSIGN);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
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

    private void getAllUserList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            usersList = appAssignManager.getAllUserList(PageVarList.USERDATACAPTURE);
            userMap=new HashMap<String,String>();
            for(String i:usersList){
                userMap.put(i, i);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    //get user list by application type
    private void getUserListByApplicationType(String cat) throws Exception{
        try {
             appAssignManager = new ApplicationAssignManager();
             userMap=new HashMap<String,String>();
             userMap=appAssignManager.getAssignUserList(PageVarList.USERDATACAPTURE, cat);
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

    private void getMaxFromCardApplication(String domain) throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            maxId = appAssignManager.getMaxFromCardApplication(domain);

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

    private void getAllUserAssignAppsDetails(String cardDomain) throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            userAssignApps = appAssignManager.getAllUserAssignAppsDetails(cardDomain);


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
    private void getIdentificationTypeByApplicationType(String cat) throws Exception{
        String varifyCategory="ID";
        try {
            appAssignManager=new ApplicationAssignManager();
            identificationList=appAssignManager.getIdentificationTypeByCardCategory(cat, varifyCategory);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllApplicationDoamin() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            applicationDomainList = appAssignManager.getAllApplicationDoamin();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private String getApplicationDomainPrefix(String domainId) throws Exception {
        String prefix;
        try {
            appAssignManager = new ApplicationAssignManager();
            prefix = appAssignManager.getApplicationDomainPrefix(domainId);
            return prefix;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getPreviousApplication(String identificationNo) throws Exception {
        try {


            appAssignManager = new ApplicationAssignManager();
            previousApplicationList = appAssignManager.getPreviousApplication(identificationNo);

        } catch (Exception ex) {
            throw ex;
        }
    }

    public String setApplicationId(String maxId) throws Exception {

        String padLine = "";

        padLine = ISOUtil.zeropad((Long.parseLong(maxId) + 1) + "", 12);

        return padLine;
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
