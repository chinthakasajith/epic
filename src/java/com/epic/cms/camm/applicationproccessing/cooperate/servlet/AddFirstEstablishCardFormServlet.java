/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.cooperate.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardExpensesBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardIncomeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerEmploymentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EmploymentNatureBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EmploymentTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.IdBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.OccupationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.EstCardManagerDetailsBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.EstablishCardApplicationBean;
import com.epic.cms.camm.applicationproccessing.cooperate.businesslogic.CorporateDataCaptureManager;
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
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AddFirstEstablishCardFormServlet extends HttpServlet {
    
    
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CaptureDataManager manager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private List<StatusBean> statusList = null;
    private List<EmploymentTypeBean> empTypeList = null;
    private List<OccupationBean> occupationList = null;
    private List<EmploymentNatureBean> natureList = null;
    private SystemAuditBean systemAuditBean;
    private String url = "/camm/capturedata/establishcarddatacapture.jsp";
    private ApplicationCheckingManager checkingManager;
    private CardApplicationStatusBean appStatusBean = null;
    private CustomerPersonalBean customerPersonalBean = null;
    private CustomerEmploymentBean employmentBean = null;
    private List<CardIncomeBean> incomeBeanList = null;
    private CardExpensesBean expensesBean = null;
    private List<CardBankDetailsBean> bankDetailsBeanLst = null;
    private List<DocumentUploadBean> documentDetailsBeanLst = null;
    private CorporateDataCaptureManager cdcmanager;
    private EstablishCardApplicationBean companyBean;
    private List<EstCardManagerDetailsBean> managerBeanList;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        companyBean = new EstablishCardApplicationBean();
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



            sessionVarlist.setCMSSessionUser(sessionUser);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

            String applicationId = sessionVarlist.getApplicationId();
            request.setAttribute("applicationId", applicationId);
            request.setAttribute("operationtype", "add");
            
            String ggg = request.getParameter("companyName");


            companyBean.setAppId(applicationId);
            companyBean.setCompanyName(request.getParameter("companyName"));
            companyBean.setAddress1(request.getParameter("address1"));
            companyBean.setAddress2(request.getParameter("address2"));
            companyBean.setAddress3(request.getParameter("address3"));
            companyBean.setArea(request.getParameter("city1"));
            companyBean.setDistrict(request.getParameter("district"));
            companyBean.setProvince(request.getParameter("province"));
            companyBean.setOfficePhone1(request.getParameter("officeNumber1"));
            companyBean.setOfficePhone2(request.getParameter("officeNumber2"));
            companyBean.setOfficeFax(request.getParameter("officeFax"));
            companyBean.setOfficeEmail(request.getParameter("officeEmail"));
            companyBean.setContactPerson(request.getParameter("contactPerson"));
            companyBean.setContactPerPosition(request.getParameter("conPerPosition"));
            companyBean.setBrcNumber(request.getParameter("regNo"));
            companyBean.setRegisterDate(request.getParameter("regDate"));
            companyBean.setRegisterPlace(request.getParameter("regPlace"));
            companyBean.setCommenceDate(request.getParameter("commenceDate"));
            companyBean.setTypeOfCompany(request.getParameter("compType"));
            companyBean.setOtherTypeOfCompany(request.getParameter("othercompType"));
            companyBean.setBusinessNature(request.getParameter("businessNature"));
            companyBean.setOtherBusinessNature(request.getParameter("otherBusinessNature"));
            companyBean.setNumberOfEmployees(request.getParameter("numberOfEmployees"));
            companyBean.setCapitalInvested(request.getParameter("capitalInvested"));
            companyBean.setAnnualTurnover(request.getParameter("anualTurnover"));
            companyBean.setAnnualTurnoverYear(request.getParameter("anualTurnoverYear"));
            //income details
            
            //audit details
            companyBean.setAuditName(request.getParameter("auditName"));
            companyBean.setAuditAddress1(request.getParameter("auditAddress1"));
            companyBean.setAuditArea(request.getParameter("audCity"));
            companyBean.setAuditAddress2(request.getParameter("auditAddress2"));
            companyBean.setAuditDistrict(request.getParameter("audDistrict"));
            companyBean.setAuditAddress3(request.getParameter("auditAddress3"));
            companyBean.setAuditProvince(request.getParameter("audProvince"));
            companyBean.setAuditOficePhone1(request.getParameter("audPhoNumber1"));
            companyBean.setAuditOficePhone2(request.getParameter("audPhoNumber2"));
            companyBean.setAuditContactPerson(request.getParameter("audContactPerson"));
            companyBean.setAuditContactPerPosition(request.getParameter("audConPerPosition"));
            companyBean.setAuditFax(request.getParameter("audFax"));
            companyBean.setAuditEmail(request.getParameter("audEmail"));
            
            companyBean.setLastUpdatedUser(sessionUser.getUserName());
            
            EstablishCardApplicationBean invalidMsgBean = new EstablishCardApplicationBean();

            invalidMsgBean = this.isValiedPersonalInfo(companyBean);

            if (invalidMsgBean == null) {


                cdcmanager = new CorporateDataCaptureManager();
                this.setAudittraceValue(request, companyBean);
                int isAdd = cdcmanager.insertCompanyData(companyBean, systemAuditBean);


                if (isAdd == 1) {

                    this.loadDefaultApplicationStatus(applicationId, request);
//                    

                    request.setAttribute("successMsg", MessageVarList.ADD_COMPANY_SUCCESS);

                    request.setAttribute("applicationId", applicationId);
                    request.setAttribute("operationtype", "add");


                    rd = getServletContext().getRequestDispatcher(url);
                  //  rd.forward(request, response);
                } else {

                    this.loadDefaultApplicationStatus(applicationId, request);


                    request.setAttribute("errorMsg", MessageVarList.ADD_COMPANY_ERROR);

                    request.setAttribute("applicationId", applicationId);
                    request.setAttribute("companyBean", companyBean);
//                    request.setAttribute("operationtype", "add");



                    rd = getServletContext().getRequestDispatcher(url);
                  //  rd.forward(request, response);
                }

            } else {
                this.loadDefaultApplicationStatus(applicationId, request);

                request.setAttribute("applicationId", applicationId);
                request.setAttribute("companyBean", companyBean);
                request.setAttribute("invalidMsgBean", invalidMsgBean);



                rd = getServletContext().getRequestDispatcher(url);
              //  rd.forward(request, response);
            }


        } catch (SQLException ex) {
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
            request.setAttribute("companyBean", companyBean);
            rd = getServletContext().getRequestDispatcher(url);
          //  rd.forward(request, response);
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
          //  rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
          //  rd.forward(request, response);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
           // rd.forward(request, response);


        } catch (Exception ex) {
            request.setAttribute("companyBean", companyBean);
            this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
            request.setAttribute("errorMsg", "Error in action");
            rd = request.getRequestDispatcher(url); 
           // rd.forward(request, response);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void loadDefaultApplicationStatus(String appliactionId, HttpServletRequest request) throws Exception {

        this.getAllApplicationStatus(appliactionId);
        String loadTabIndex = "";
        String selectedTab = "0";

        if (appStatusBean != null) {

            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_INITIATE) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_PROCESS)) {
                       
                if (appStatusBean.getTableOne().equals("1")) {
                    this.getAllDetailsCompany(appliactionId);
                    request.setAttribute("companyBean", companyBean);
                    sessionVarlist.setCompanyBean(companyBean);
                    selectedTab = "1";

                } else {

                    loadTabIndex = "0";
                }
                 if (appStatusBean.getTableTwo().equals("1")) { 
                    this.getManagerDetails(appliactionId);
                    sessionVarlist.setSessionManagerDetailList(managerBeanList);
                    
                    selectedTab = "2";
                } else {
                    loadTabIndex = loadTabIndex + "," + "1";
                }
                
                if (appStatusBean.getTableThree().equals("1")) {
                    this.getAllBankDetails(appliactionId);
                    sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);
                    
                    selectedTab = "3";

                } else {
                    loadTabIndex = loadTabIndex + "," + "2";
                }
                

            }

        }



        request.setAttribute("loadTabIndex", loadTabIndex);
        request.setAttribute("selectedtab", selectedTab);

    }
    
    private void getAllDetailsCompany(String appliactionId) throws Exception {
        try {
            cdcmanager = new CorporateDataCaptureManager();
            companyBean = cdcmanager.getAllDetailsCompany(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }
    
      private void getManagerDetails(String appliactionId) throws Exception {
        try {
            cdcmanager = new CorporateDataCaptureManager();
            managerBeanList = cdcmanager.getManagerDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }
    
       private void getAllBankDetails(String appliactionId) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            bankDetailsBeanLst = checkingManager.getCardBankDetailsDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }
      
     private void getAllApplicationStatus(String appliactionId) throws Exception {

        try {
            manager = new CaptureDataManager();
            appStatusBean = manager.getAllApplicationStatus(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }
    
       private EstablishCardApplicationBean isValiedPersonalInfo(EstablishCardApplicationBean companyBean) throws Exception {
        UserInputValidator validObject = new UserInputValidator();
        EstablishCardApplicationBean invalidMsgBean = new EstablishCardApplicationBean();
        int msg = 0;

        try {
            //----------------------------------------------------------------------------           


                  
            if (companyBean.getCompanyName().equals("")) {
                invalidMsgBean.setCompanyName("Requierd");
                msg = 1;
            }else if (!validObject.isCorrectString(companyBean.getCompanyName())) {
                invalidMsgBean.setCompanyName("Invalid");
                msg = 1;
            }
            if (companyBean.getAddress1().equals("")) {
                invalidMsgBean.setAddress1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getAddress1())) {
                invalidMsgBean.setAddress1("Invalid");
                msg = 1;
            }
            if (companyBean.getAddress2().equals("")) {
                invalidMsgBean.setAddress2("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getAddress2())) {
                invalidMsgBean.setAddress2("Invalid");
                msg = 1;
            }
            if (!companyBean.getAddress3().equals("") && !validObject.isCorrectString(companyBean.getAddress3())) {
                invalidMsgBean.setAddress3("Invalid");
                msg = 1;
            }
            if (companyBean.getArea().equals("")) {
                invalidMsgBean.setArea("Requierd");
                msg = 1;
            }
            if (companyBean.getOfficePhone1().equals("")) {
                invalidMsgBean.setOfficePhone1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getOfficePhone1())) {
                invalidMsgBean.setOfficePhone1("Invalid");
                msg = 1;
            }
            if (companyBean.getOfficePhone2().equals("")) {
                invalidMsgBean.setOfficePhone2("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getOfficePhone2())) {
                invalidMsgBean.setOfficePhone2("Invalid");
                msg = 1;
            }
            if (companyBean.getOfficeFax().equals("")) {
                invalidMsgBean.setOfficeFax("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getOfficeFax())) {
                invalidMsgBean.setOfficeFax("Invalid");
                msg = 1;
            }
            if (companyBean.getOfficeEmail().equals("")) {
                invalidMsgBean.setOfficeEmail("Requierd");
                msg = 1;
            } else if (!validObject.isValidEmail(companyBean.getOfficeEmail())) {
                invalidMsgBean.setOfficeEmail("Invalid");
                msg = 1;
            }
            if (companyBean.getContactPerson().equals("")) {
                invalidMsgBean.setContactPerson("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getContactPerson())) {
                invalidMsgBean.setContactPerson("Invalid");
                msg = 1;
            }
            if (companyBean.getContactPerPosition() == null || companyBean.getContactPerPosition().equals("")) {
                invalidMsgBean.setContactPerPosition("Requierd");
                msg = 1;
            }  
            
            //==========Company Details=========================
            
            if (companyBean.getBrcNumber().equals("")) {
                invalidMsgBean.setBrcNumber("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getBrcNumber())) {
                invalidMsgBean.setBrcNumber("Invalid");
                msg = 1;
            }
            if (companyBean.getRegisterDate().equals("")) {
                invalidMsgBean.setRegisterDate("Requierd");
                msg = 1;
            }
            if (companyBean.getRegisterPlace().equals("")) {
                invalidMsgBean.setRegisterPlace("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getRegisterPlace())) {
                invalidMsgBean.setRegisterPlace("Invalid");
                msg = 1;
            }
            if (companyBean.getCommenceDate().equals("")) {
                invalidMsgBean.setCommenceDate("Requierd");
                msg = 1;
            }
            if (companyBean.getTypeOfCompany()== null || companyBean.getTypeOfCompany().equals("")) {
                invalidMsgBean.setTypeOfCompany("Requierd");
                msg = 1;
            }
            if (companyBean.getTypeOfCompany().equals("EMPOTH") && companyBean.getOtherTypeOfCompany().equals("")) {
                invalidMsgBean.setOtherTypeOfCompany("Requierd");
                msg = 1;
            }else if (!validObject.isCorrectString(companyBean.getOtherTypeOfCompany())) {
                invalidMsgBean.setOtherTypeOfCompany("Invalid");
                msg = 1;
            }
            if (companyBean.getBusinessNature()== null || companyBean.getBusinessNature().equals("")) {
                invalidMsgBean.setBusinessNature("Requierd");
                msg = 1;
            }
            if (companyBean.getBusinessNature().equals("OTHER") && companyBean.getOtherBusinessNature().equals("")) {
                invalidMsgBean.setOtherBusinessNature("Requierd");
                msg = 1;
            }else if (!validObject.isCorrectString(companyBean.getOtherBusinessNature())) {
                invalidMsgBean.setOtherBusinessNature("Invalid");
                msg = 1;
            }
            if (companyBean.getNumberOfEmployees().equals("")) {
                invalidMsgBean.setNumberOfEmployees("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(companyBean.getNumberOfEmployees())) {
                invalidMsgBean.setNumberOfEmployees("Invalid");
                msg = 1;
            }
            if (companyBean.getCapitalInvested().equals("")) {
                invalidMsgBean.setCapitalInvested("Requierd");
                msg = 1;
            } else if (!validObject.isDecimalOrNumeric(companyBean.getCapitalInvested(),"7","2")) {
                invalidMsgBean.setCapitalInvested("Invalid");
                msg = 1;
            }
            if (companyBean.getAnnualTurnover().equals("")) {
                invalidMsgBean.setAnnualTurnover("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getAnnualTurnover())) {
                invalidMsgBean.setAnnualTurnover("Invalid");
                msg = 1;
            }
            if (companyBean.getAnnualTurnoverYear().equals("")) {
                invalidMsgBean.setCapitalInvested("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getCapitalInvested())) {
                invalidMsgBean.setAnnualTurnoverYear("Invalid");
                msg = 1;
            }
            
            //===========Company Auditor Details=============
            if (companyBean.getAuditName().equals("")) {
                invalidMsgBean.setAuditName("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getAuditName())) {
                invalidMsgBean.setAuditName("Invalid");
                msg = 1;
            }
            if (companyBean.getAuditArea().equals("")) {
                invalidMsgBean.setAuditArea("Requierd");
                msg = 1;
            }
            if (companyBean.getAuditAddress1().equals("")) {
                invalidMsgBean.setAuditAddress1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getAuditAddress1())) {
                invalidMsgBean.setAuditAddress1("Invalid");
                msg = 1;
            }
            if (companyBean.getAuditAddress2().equals("")) {
                invalidMsgBean.setAuditAddress2("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getAuditAddress2())) {
                invalidMsgBean.setAuditAddress2("Invalid");
                msg = 1;
            }
            if (!companyBean.getAuditAddress3().equals("") && !validObject.isCorrectString(companyBean.getAuditAddress3())) {
                invalidMsgBean.setAuditAddress3("Invalid");
                msg = 1;
            }
            if (companyBean.getAuditContactPerson().equals("")) {
                invalidMsgBean.setAuditContactPerson("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getAuditContactPerson())) {
                invalidMsgBean.setAuditContactPerson("Invalid");
                msg = 1;
            }
            if (companyBean.getAuditContactPerPosition()== null || companyBean.getAuditContactPerPosition().equals("")) {
                invalidMsgBean.setAuditContactPerPosition("Requierd");
                msg = 1;
            } 
            if (companyBean.getAuditOficePhone1().equals("")) {
                invalidMsgBean.setAuditOficePhone1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getAuditOficePhone1())) {
                invalidMsgBean.setAuditOficePhone1("Invalid");
                msg = 1;
            }
            if (companyBean.getAuditOficePhone2().equals("")) {
                invalidMsgBean.setAuditOficePhone2("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getAuditOficePhone2())) {
                invalidMsgBean.setAuditOficePhone2("Invalid");
                msg = 1;
            }
            if (companyBean.getAuditFax().equals("")) {
                invalidMsgBean.setAuditFax("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(companyBean.getAuditFax())) {
                invalidMsgBean.setAuditFax("Invalid");
                msg = 1;
            }
            if (companyBean.getAuditEmail().equals("")) {
                invalidMsgBean.setAuditEmail("Requierd");
                msg = 1;
            } else if (!validObject.isValidEmail(companyBean.getAuditEmail())) {
                invalidMsgBean.setAuditEmail("Invalid");
                msg = 1;
            }
            
            
            
            
            if (msg == 0) {
                invalidMsgBean = null;
            }

            return invalidMsgBean;



        } catch (Exception ex) {
            throw ex;
        }
    }
       
        private void setAudittraceValue(HttpServletRequest request, EstablishCardApplicationBean bean) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
             systemAuditBean.setDescription("Add Company Details in to Application ID : '" + bean.getAppId() + "' by :" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CAPTURE_DATA);
            systemAuditBean.setPageCode(PageVarList.CORPORATE_CARD_DATA);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddFirstEstablishCardFormServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddFirstEstablishCardFormServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
