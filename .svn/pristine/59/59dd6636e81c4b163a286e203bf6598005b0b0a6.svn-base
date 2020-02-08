/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardDocumentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardExpensesBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardIncomeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerEmploymentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
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
public class LoadSupllimentaryApplicationCheckingServlet1 extends HttpServlet {

     private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private ApplicationCheckingManager checkingmanager;
    private SessionUser sessionUser = null;
    private SystemUserManager pageMgt;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private List<StatusBean> statusList;
    private SupplementaryApplicationBean personalDetail;
    private CardApplicationBean cardApplicationList;
    private CustomerEmploymentBean employementDetails;
    private List<CardIncomeBean> incomeDetailslist = null;
    private CardExpensesBean expensesDetails;
    private List<CardBankDetailsBean> cardBankDetails;
    private List<CardDocumentBean> cardDocumentList;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean=null;   
    private Boolean checkOutStatus = false;
    private String applicationId = null;
    private ApplicationHistoryBean historybean = null;
    private String url = "/camm/applicationchecking/supplementaryapplicationchecking.jsp";
    
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
                applicationId = request.getParameter("applicationid");
                this.setAudittraceValue(request);
                this.updateCardApplicationStatus(applicationId);
                this.setApplicationHistoryBean();
                
                if (checkOutStatus) {
                    request.setAttribute("operationtype", "default");
                    String selectedTab = (String) request.getAttribute("selectedtab");

                    if(selectedTab==null){
                        request.setAttribute("selectedtab", "0");
                    }else if(selectedTab.equals("0")){
                        request.setAttribute("selectedtab", "1");
                    }else if(selectedTab.equals("2")){
                        request.setAttribute("selectedtab", "2");
                    }
                    
                    this.getCardApplicationDetails(applicationId);
                    this.getDataFromCardApplicationPersonalTable(applicationId);
                    this.getCardApplicationDocument(applicationId);
//                    this.getCardApplicationDocument(applicationId);
                    
                    request.setAttribute("cardApplicationList", cardApplicationList);
                    request.setAttribute("personalDetail", personalDetail);
                    request.setAttribute("cardDocumentList", cardDocumentList);
                    request.setAttribute("cardDocumentList", cardDocumentList);
                    
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
            personalDetail = checkingmanager.getSupplementaryPersonalDetails(applicationId);
            
            if(personalDetail.getAddress1() == null){
                personalDetail.setAddress1("-");
            }
            
            if(personalDetail.getAddress2() == null){
                personalDetail.setAddress2("-");
            }
            
            if(personalDetail.getAddress3() == null){
                personalDetail.setAddress3("-");
            }
            
            if(personalDetail.getAdressSameAsPrimary() == null){
                personalDetail.setAdressSameAsPrimary("-");
            }
            
            if(personalDetail.getBillDistrict() == null){
                personalDetail.setBillDistrict("-");
            }
            
            if(personalDetail.getBillProvince() == null){
                personalDetail.setBillProvince("-");
            }
            
            if(personalDetail.getBillingAdress1() == null){
                personalDetail.setBillingAdress1("-");
            }
            
            if(personalDetail.getBillingAdress2() == null){
                personalDetail.setBillingAdress2("-");
            }
            
            if(personalDetail.getBillingAdress3() == null){
                personalDetail.setBillingAdress3("-");
            }
            
            if(personalDetail.getBillingCity() == null){
                personalDetail.setBillingCity("-");
            }
            
            if(personalDetail.getBirthday() == null){
                personalDetail.setBirthday("-");
            }
            
            if(personalDetail.getCardProduct() == null){
                personalDetail.setCardProduct("-");
            }
            
            if(personalDetail.getCardType() == null){
                personalDetail.setCardType("-");
            }
            
            if(personalDetail.getCity() == null){
                personalDetail.setCity("-");
            }
            
            if(personalDetail.getCreditLimit() == null){
                personalDetail.setCreditLimit("-");
            }
            
            if(personalDetail.getEmployementType() == null){
                personalDetail.setEmployementType("-");
            }
            
            if(personalDetail.getFirstName() == null){
                personalDetail.setFirstName("-");
            }
            
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
            
            if(personalDetail.getLastName() == null){
                personalDetail.setLastName("-");
            }
            
            if(personalDetail.getMiddleName() == null){
                personalDetail.setMiddleName("-");
            }
            
            if(personalDetail.getMobileNumber() == null){
                personalDetail.setMobileNumber("-");
            }
            
            if(personalDetail.getNameOncard() == null){
                personalDetail.setNameOncard("-");
            }
            
            if(personalDetail.getNameWithinitials() == null){
                personalDetail.setNameWithinitials("-");
            }
            
            if(personalDetail.getNationality() == null){
                personalDetail.setNationality("-");
            }
            
            if(personalDetail.getNic() == null){
                personalDetail.setNic("-");
            }
            
            if(personalDetail.getOccupation() == null){
                personalDetail.setOccupation("-");
            }
            
            if(personalDetail.getPassportExpdate() == null){
                personalDetail.setPassportExpdate("-");
            }
            
            if(personalDetail.getPassportNumber() == null){
                personalDetail.setPassportNumber("-");
            }
            
            if(personalDetail.getPostalcode() == null){
                personalDetail.setPostalcode("-");
            }
            
            if(personalDetail.getPrimaryCardApplicationId() == null){
                personalDetail.setPrimaryCardApplicationId("-");
            }
            
            if(personalDetail.getPrimaryCardNumber() == null){
                personalDetail.setPrimaryCardNumber("-");
            }
            
            if(personalDetail.getPrimaryCardType() == null){
                personalDetail.setPrimaryCardType("-");
            }
            
            if(personalDetail.getPrimaryId() == null){
                personalDetail.setPrimaryId("-");
            }
            
            if(personalDetail.getRelationShip() == null){
                personalDetail.setRelationShip("-");
            }
            
            if(personalDetail.getResDistrict() == null ){
                personalDetail.setResDistrict("-");
            }
            
            if(personalDetail.getResProvince() == null){
                personalDetail.setResProvince("-");
            }
            
            if(personalDetail.getStatementStatus() == null){
                personalDetail.setStatementStatus("-");
            }
            
            if(personalDetail.getTitle() == null){
                personalDetail.setTitle("-");
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
     
   
    private void getCardApplicationDocument(String applicationId) throws Exception {
        cardDocumentList = new ArrayList<CardDocumentBean>();
         try {
            checkingmanager = new ApplicationCheckingManager();
            cardDocumentList = checkingmanager.getCardDocumentDetailsDetails(applicationId);
        } catch (Exception e) {
            throw e;
        }
    } 
    
     private void updateCardApplicationStatus(String applicationId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            checkOutStatus = checkingmanager.UpdateCardApplicationStatus(applicationId,StatusVarList.APP_CHECKOUT,systemAuditBean,historybean);
        } catch (Exception e) {
            throw e;
        }
    }
     
      private void setAudittraceValue(HttpServletRequest request) throws Exception{
        
        
        try{
        systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
        String uniqueId = request.getParameter(applicationId);
       
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
      
      public void setApplicationHistoryBean() {
        historybean = new ApplicationHistoryBean();
        
        historybean.setApplicationId(applicationId);
        historybean.setApplicationLevel("CHEK");
        historybean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
        historybean.setRemarks("Cheking completed");
        historybean.setStatus(StatusVarList.HISTORY_COMPLETE);
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
