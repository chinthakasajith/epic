/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.TaskMgtManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardDocumentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardExpensesBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardIncomeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerEmploymentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.OccupationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
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
import com.epic.cms.system.util.variable.StatusVarList;
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
public class LoadApplicationCheckingServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private ApplicationCheckingManager checkingmanager;
    private SessionUser sessionUser = null;
    private SystemUserManager pageMgt;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private List<StatusBean> statusList;
    private CustomerPersonalBean personalDetail;
    private CardApplicationBean cardApplicationList;
    private CustomerEmploymentBean employementDetails;
    private List<CardIncomeBean> incomeDetailslist = null;
    private CardExpensesBean expensesDetails;
    private List<CardBankDetailsBean> cardBankDetails;
    private List<CardDocumentBean> cardDocumentList;
    private List<OccupationBean> occuptionList;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean=null;   
    private Boolean checkOutStatus = false;
    private String url = "/camm/applicationchecking/applicationchecking.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            //call to existing session
            ////////////////////////////////////////////////////////////////////////////////
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

                try {
                    //set page code and task codes
                    String pageCode = PageVarList.APPSEARCH;
                    String taskCode = TaskVarList.ACCESSPAGE;


                    //check whethre userrole have an access for this page and task          
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        // is valid acess nothing to do
                    } else {

                        //if invalid throw accessdenied exception
                        throw new AccessDeniedException();

                    }

                    //set tasks to the session
                    sessionVarlist.setUserPageTaskList(userTaskList);


                } catch (AccessDeniedException adex) {
                    throw adex;

                }

            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }
            /////////////////////////////////////////////////////////////////////

            try {
                String applicationId = request.getParameter("applicationid");
                this.setAudittraceValue(request);
                this.updateCardApplicationStatus(applicationId);
                
                
                if (checkOutStatus) {
                    request.setAttribute("operationtype", "default");
                    String selectedTab = (String) request.getAttribute("selectedtab");

                    if(selectedTab==null){
                        request.setAttribute("selectedtab", "0");
                    }else if(selectedTab.equals("0")){
                        request.setAttribute("selectedtab", "0");
                    }else if(selectedTab.equals("2")){
                        request.setAttribute("selectedtab", "2");
                    }else if(selectedTab.equals("3")){
                        request.setAttribute("selectedtab", "3");
                    }else if(selectedTab.equals("4")){
                        request.setAttribute("selectedtab", "4");
                    }else if(selectedTab.equals("5")){
                        request.setAttribute("selectedtab", "5");
                    }
                    
                    this.getCardApplicationDetails(applicationId);
                    this.getDataFromCardApplicationPersonalTable(applicationId);
                    this.getEmployementDetails(applicationId);
                    this.getCardIncomeDetails(applicationId);
                    this.getCardExpensesDetails(applicationId);
                    this.getCardBankDetails(applicationId);
                    this.getCardApplicationDocument(applicationId);
                    //get all occupation details
                    this.getAllOccupations();

                    request.setAttribute("cardApplicationList", cardApplicationList);
                    request.setAttribute("personalDetail", personalDetail);
                    request.setAttribute("employementDetails", employementDetails);
                    request.setAttribute("incomeDetailslist", incomeDetailslist);
                    request.setAttribute("expensesDetails", expensesDetails);
                    request.setAttribute("cardBankDetails", cardBankDetails);  
                    request.setAttribute("cardBankDetails", cardBankDetails);   
                    request.setAttribute("cardDocumentList", cardDocumentList);
                    request.setAttribute("occuptionList", occuptionList);
                    
                    rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                }

            } catch (Exception e) {
                request.setAttribute("operationtype", "default");
                request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
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

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
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
    
     private void getDataFromCardApplicationPersonalTable(String applicationId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            personalDetail = checkingmanager.getPersonalDetails(applicationId);
            
            if(personalDetail.getAcedemicQualifications() == null){
                personalDetail.setAcedemicQualifications("-");
            }
            
            if(personalDetail.getAddress1() == null){
                personalDetail.setAddress1("-");
            }
            
            if(personalDetail.getAddress2() == null){
                personalDetail.setAddress2("-");
            }
            
            if(personalDetail.getAddress3() == null){
                personalDetail.setAddress3("-");
            }
            
            if(personalDetail.getApplicationId() == null){
                personalDetail.setApplicationId("-");
            }
            
            if(personalDetail.getBillAddress1() == null){
                personalDetail.setBillAddress1("-");
            }
            
            if(personalDetail.getBillAddress2() == null){
                personalDetail.setBillAddress2("-");
            }
            
            if(personalDetail.getBillAddress3() == null){
                personalDetail.setBillAddress3("-");
            }
            
            if(personalDetail.getBillCity() == null){
                personalDetail.setBillCity("-");
            }
            
            if(personalDetail.getBillDistrict() == null){
                personalDetail.setBillDistrict("-");
            }
            
            if(personalDetail.getBillProvince() == null){
                personalDetail.setBillProvince("-");
            }
            
            if(personalDetail.getBirthday() == null){
                personalDetail.setBirthday("-");
            }
            
            if(personalDetail.getBloodgroup() == null){
                personalDetail.setBloodgroup("-");
            }
            
            if(personalDetail.getCardProduct() == null){
                personalDetail.setCardProduct("-");
            }
            
            if(personalDetail.getCardType() == null){
                personalDetail.setCardType("-");
            }
            
            if(personalDetail.getCardTypeDes()== null){
                personalDetail.setCardTypeDes("-");
            }
            
            if(personalDetail.getCity() == null){
                personalDetail.setCity("-");
            }
            
            if(personalDetail.getCreditLimit() == null){
                personalDetail.setCreditLimit("-");
            }
            
            if(personalDetail.getDistrict() == null){
                personalDetail.setDistrict("-");
            }
            
            if(personalDetail.getDurationofTheAddress() == null){
                personalDetail.setDurationofTheAddress("-");
            }
            
            if(personalDetail.getEmail() == null){
                personalDetail.setEmail("-");
            }
            
            if(personalDetail.getEmergencyContactNumber() == null){
                personalDetail.setEmergencyContactNumber("-");
            }
            
//            if(personalDetail.getFirstName() == null){
//                personalDetail.setFirstName("-");
//            }
            
            if(personalDetail.getGender() == null){
                personalDetail.setGender("-");
            }
            
            if(personalDetail.getHomeTelNumber() == null){
                personalDetail.setHomeTelNumber("-");
            }
            
            if(personalDetail.getIdentificationNumber() == null){
                personalDetail.setIdentificationNumber("-");
            }
            
            if(personalDetail.getIdentificationType() == null){
                personalDetail.setIdentificationType("-");
            }
            
            if(personalDetail.getInitials() == null){
                personalDetail.setInitials("-");
            }
            
//            if(personalDetail.getLastName() == null){
//                personalDetail.setLastName("-");
//            }
            
            if(personalDetail.getMaritalStatus() == null){
                personalDetail.setMaritalStatus("-");
            }
            
//            if(personalDetail.getMiddleName() == null){
//                personalDetail.setMiddleName("-");
//            }
            
            if(personalDetail.getMobileNumber() == null){
                personalDetail.setMobileNumber("-");
            }
            
            if(personalDetail.getMonthlyRental() == null){
                personalDetail.setMonthlyRental("-");
            }
            
            if(personalDetail.getMorgageRental() == null){
                personalDetail.setMorgageRental("-");
            }
            
            if(personalDetail.getMothersMaidenName() == null){
                personalDetail.setMothersMaidenName("-");
            }
            
            if(personalDetail.getNameOncard() == null){
                personalDetail.setNameOncard("-");
            }
            
            if(personalDetail.getNameWithInitials() == null){
                personalDetail.setNameWithInitials("-");
            }
            
            if(personalDetail.getNationality() == null){
                personalDetail.setNationality("-");
            }
            
            if(personalDetail.getNic() == null){
                personalDetail.setNic("-");
            }
            
            if(personalDetail.getNumberOfDependance() == null){
                personalDetail.setNumberOfDependance("-");
            }
            
            if(personalDetail.getOfficeTelNumber() == null){
                personalDetail.setOfficeTelNumber("-");
            }
            
            if(personalDetail.getOwnership() == null){
                personalDetail.setOwnership("-");
            }
            
            if(personalDetail.getPlaceOfbirth() == null){
                personalDetail.setPlaceOfbirth("-");
            }
            
            if(personalDetail.getPassportExpdate() == null){
                personalDetail.setPassportExpdate("-");
            }
            
            if(personalDetail.getPassportNumber() == null){
                personalDetail.setPassportNumber(applicationId);
            }
            
            if(personalDetail.getPermentAddress1() == null){
                personalDetail.setPermentAddress1("-");
            }
            
            if(personalDetail.getPermentAddress2() == null){
                personalDetail.setPermentAddress2("-");
            }
            
            if(personalDetail.getPermentAddress3() == null){
                personalDetail.setPermentAddress3("-");
            }
            
            if(personalDetail.getPermentCity() == null){
                personalDetail.setPermentCity("-");
            }
            
            if(personalDetail.getPlaceOfbirth() == null){
                personalDetail.setPlaceOfbirth("-");
            }
            
            if(personalDetail.getPostalcode() == null){
                personalDetail.setPostalcode("-");
            }
            
            if(personalDetail.getProfessionalQualifications() == null){
                personalDetail.setProfessionalQualifications("-");
            }
            
            if(personalDetail.getProvince() == null){
                personalDetail.setProvince("-");
            }
            
            if(personalDetail.getRelAddress1() == null){
                personalDetail.setRelAddress1("-");
            }
            
            if(personalDetail.getRelAddress2() == null){
                personalDetail.setRelAddress2("-");
            }
            
            if(personalDetail.getRelAddress3() == null){
                personalDetail.setRelAddress3("-");
            }
            
            if(personalDetail.getRelCity() == null){
                personalDetail.setRelCity("-");
            }
            
            if(personalDetail.getRelCompany() == null){
                personalDetail.setRelCompany("-");
            }
            
            if(personalDetail.getRelEmail() == null){
                personalDetail.setRelEmail("-");
            }
            
            if(personalDetail.getRelMobile() == null){
                personalDetail.setRelMobile("-");
            }
            
            if(personalDetail.getRelName() == null){
                personalDetail.setRelName("-");
            }
            
            if(personalDetail.getRelOfficeNumber() == null){
                personalDetail.setRelOfficeNumber("-");
            }
            
            if(personalDetail.getRelResidencePhone() == null){
                personalDetail.setRelResidencePhone("-");
            }
            
            if(personalDetail.getRelationship() == null){
                personalDetail.setRelationship("-");
            }
            
            if(personalDetail.getResDistrict() == null){
                personalDetail.setResDistrict("-");
            }
            
            if(personalDetail.getResProvince() == null){
                personalDetail.setResProvince("-");
            }
                             
            if(personalDetail.getResidenceType() == null){
                personalDetail.setResidenceType("-");
            }
            
            if(personalDetail.getSmsalertMobileNumber() == null){
                personalDetail.setSmsalertMobileNumber("-");
            }
            
            if(personalDetail.getSpouseDateofBirth() == null){
                personalDetail.setSpouseDateofBirth("-");
            }
            
            if(personalDetail.getSpouseDesignation() == null){
                personalDetail.setSpouseDesignation("-");
            }
            
            if(personalDetail.getSpouseMail() == null){
                personalDetail.setSpouseMail("-");
            }
            
            if(personalDetail.getSpouseMonthlyIncome() == null){
                personalDetail.setSpouseMonthlyIncome("-");
            }
            
            if(personalDetail.getSpouseName() == null){
                personalDetail.setSpouseName("-");
            }
            
            if(personalDetail.getSpouseNameofEmployee() == null){
                personalDetail.setSpouseNameofEmployee("-");
            }
            
            if(personalDetail.getSpouseNic() == null){
                personalDetail.setSpouseNic("-");
            }
            
            if(personalDetail.getSpousePassportNumber() == null){
                personalDetail.setSpousePassportNumber("-");
            }
            
            if(personalDetail.getSpousePhone() == null){
                personalDetail.setSpousePhone("-");
            }
            
            if(personalDetail.getSpousecompanyAddress() == null){
                personalDetail.setSpousecompanyAddress("-");
            }
            
            if(personalDetail.getTitle() == null){
                personalDetail.setTitle("-");
            }
            
            if(personalDetail.getUseCardOnline() == null){
                personalDetail.setUseCardOnline("-");
            }
            
            if(personalDetail.getVehicalNo() == null){
                personalDetail.setVehicalNo("-");
            }
            
            if(personalDetail.getVehicalType() == null){
                personalDetail.setVehicalType("-");
            }
            
            if(personalDetail.getReligion() == null){
                personalDetail.setReligion("-");
            }
            
        } catch (Exception e) {
            throw e;
        }
    }
     
     private void getCardApplicationDetails(String applicationId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            cardApplicationList = checkingmanager.getCardInfomationDetails(applicationId);
            
            if(cardApplicationList.getApplicationId() == null){
                cardApplicationList.setApplicationId("-");
            }
            
            if(cardApplicationList.getAssignStatus() == null){
                cardApplicationList.setAssignStatus("-");
            }
            
            if(cardApplicationList.getAssignUser() == null){
                cardApplicationList.setAssignUser("-");
            }
            
            if(cardApplicationList.getBranchCode() == null){
                cardApplicationList.setBranchCode("-");
            }
            
            if(cardApplicationList.getCreditScore() == null){
                cardApplicationList.setCreditScore("-");
            }
            
            if(cardApplicationList.getIdentificationNumber() == null){
                cardApplicationList.setIdentificationNumber("-");
            }
            
            if(cardApplicationList.getIdentificationType() == null){
                cardApplicationList.setIdentificationType("-");
            }
            
            if(cardApplicationList.getReferencialEmpNum() == null){
                cardApplicationList.setReferencialEmpNum("-");
            }
            
        } catch (Exception e) {
            throw e;
        }
    }
     
     private void getEmployementDetails(String applicationId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            employementDetails = checkingmanager.getCardEmployementDetails(applicationId);
            
            if(employementDetails.getAdress1() == null){
                employementDetails.setAdress1("-");
            }
            
            if(employementDetails.getAdress2() == null){
                employementDetails.setAdress2("-");
            }
            
            if(employementDetails.getAdress3() == null){
                employementDetails.setAdress3("-");
            }
            
            if(employementDetails.getAnnualTurnOver() == null){
                employementDetails.setAnnualTurnOver("0");
            }
            
            if(employementDetails.getBusinessNature() == null){
                employementDetails.setBusinessNature("-");
            }
            
            if(employementDetails.getBusnessFrom() == null){
                employementDetails.setBusnessFrom("-");
            }
            
            if(employementDetails.getCompanyName() == null){
                employementDetails.setCompanyName("-");
            }
            
            if(employementDetails.getDesignation() == null){
                employementDetails.setDesignation("-");
            }
            
            if(employementDetails.getEmploymentStatus() == null){
                employementDetails.setEmploymentStatus("-");
            }
            
            if(employementDetails.getEmploymentType() == null){
                employementDetails.setEmploymentType("-");
            }
            
            if(employementDetails.getNetProfit() == null){
                employementDetails.setNetProfit("0");
            }
            
            if(employementDetails.getNumberOfEmployees() == null){
                employementDetails.setNumberOfEmployees("-");
            }else{
                if(employementDetails.getNumberOfEmployees().equals("1") ){
                    employementDetails.setNumberOfEmployees("Up to 10");
                }
                
                if(employementDetails.getNumberOfEmployees().equals("2") ){
                    employementDetails.setNumberOfEmployees("11 to 50");
                }
                
                if(employementDetails.getNumberOfEmployees().equals("3") ){
                    employementDetails.setNumberOfEmployees("51 to 100");
                }
                
                if(employementDetails.getNumberOfEmployees().equals("4") ){
                    employementDetails.setNumberOfEmployees("More than 100");
                }
            }
            
            if(employementDetails.getOccupation() == null){
                employementDetails.setOccupation("-");
            }
            
            if(employementDetails.getOfficePhone() == null){
                employementDetails.setOfficePhone("-");
            }
            
            if(employementDetails.getOtherBusinessNature() == null){
                employementDetails.setOtherBusinessNature("-");
            }
            
            if(employementDetails.getOtherEmploymentType() == null){
                employementDetails.setOtherEmploymentType("-");
            }
            
            if(employementDetails.getOtherOccupation() == null){
                employementDetails.setOtherOccupation("-");
            }
            
            if(employementDetails.getSelfEmpCompanyname() == null){
                employementDetails.setSelfEmpCompanyname("-");
            }
            
            if(employementDetails.getSelfEmpNoOfEmployee() == null){
                employementDetails.setSelfEmpNoOfEmployee("-");
            }
            
            
        } catch (Exception e) {
            throw e;
        }
    }
    
     private void getCardIncomeDetails(String applicationId) throws Exception {
        incomeDetailslist = new ArrayList<CardIncomeBean>();
         try {
            checkingmanager = new ApplicationCheckingManager();
            incomeDetailslist = checkingmanager.getCardIncomeDetails(applicationId);
            
            
        } catch (Exception e) {
            throw e;
        }
    } 
     private void getCardExpensesDetails(String applicationId) throws Exception {
        expensesDetails = new CardExpensesBean();
         try {
            checkingmanager = new ApplicationCheckingManager();
            expensesDetails = checkingmanager.getExpensesDetails(applicationId);
            
            if(expensesDetails.getCreditCardbill() == null){
                expensesDetails.setCreditCardbill("-");
            }
            
            if(expensesDetails.getHouseHolderExpenses() == null){
                expensesDetails.setHouseHolderExpenses("-");
            }
            
            if(expensesDetails.getInsuranceInstallment() == null){
                expensesDetails.setInsuranceInstallment("-");
            }
            
            if(expensesDetails.getLeaseAmount() == null){
                expensesDetails.setLeaseAmount("-");
            }
            
            if(expensesDetails.getLoanInstallmentAmount() == null){
                expensesDetails.setLoanInstallmentAmount("-");
            }
            
            if(expensesDetails.getOtherBorrows() == null){
                expensesDetails.setOtherBorrows("-");
            }
            
            if(expensesDetails.getOtherExpenses() == null){
                expensesDetails.setOtherExpenses("-");
            }
            
            if(expensesDetails.getRentAmount() == null){
                expensesDetails.setRentAmount("-");
            }
            
            if(expensesDetails.getTotal() == null){
                expensesDetails.setTotal("-");
            }
            
            
        } catch (Exception e) {
            throw e;
        }
    }
     private void getCardBankDetails(String applicationId) throws Exception {
        cardBankDetails = new ArrayList<CardBankDetailsBean>();
         try {
            checkingmanager = new ApplicationCheckingManager();
            cardBankDetails = checkingmanager.getCardBankDetailsDetails(applicationId);
        } catch (Exception e) {
            throw e;
        }
    } 
    
    private void getCardApplicationDocument(String applicationId) throws Exception {
        cardDocumentList = new ArrayList<CardDocumentBean>();
         try {
            checkingmanager = new ApplicationCheckingManager();
            cardDocumentList = checkingmanager.getCardDocumentDetailsDetails(applicationId);
        } catch (Exception e) {
            throw e;
        }
    } 
    //get all Occupation details from OCCUPATIONTYPE table
    private void getAllOccupations() throws Exception{
        occuptionList=new ArrayList<OccupationBean>();
        try {
            checkingmanager=new ApplicationCheckingManager();
            occuptionList=checkingmanager.getOccupationlist();
        } catch (Exception ex) {
            throw ex;
        }
    }
    
     private void updateCardApplicationStatus(String applicationId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            checkOutStatus = checkingmanager.UpdateCardApplicationStatus(applicationId,StatusVarList.APP_CHECKOUT,systemAuditBean,null);
        } catch (Exception e) {
            throw e;
        }
    }
     
      private void setAudittraceValue(HttpServletRequest request) throws Exception{
        
        
        try{
        systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
        String uniqueId = request.getParameter("applicationid");
       
        systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
        systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
        //set unique id
        systemAuditBean.setUniqueId(uniqueId);
        //set description 
        systemAuditBean.setDescription("Update "+ uniqueId+ " by "+sessionVarlist.getCMSSessionUser().getUserName());      
        systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
        systemAuditBean.setSectionCode(SectionVarList.APPLICATIONAPPROVE);
        systemAuditBean.setPageCode(PageVarList.APPSEARCH);
        systemAuditBean.setTaskCode(TaskVarList.UPDATE);
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
          
        }
        catch(Exception ex){
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
