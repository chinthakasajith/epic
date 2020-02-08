/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

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
 * @author janaka_h
 */
public class AddCustomerPersonalInfoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
    private String url = "/camm/capturedata/inputCaptureData.jsp";
    private String urlFD = "/camm/capturedata/cardagainstFDInputCaptureData.jsp";
    private String urlCorporate = "/camm/capturedata/corporateInputCaptureData.jsp";

    private ApplicationCheckingManager checkingManager;
    private CardApplicationStatusBean appStatusBean = null;
    private CustomerPersonalBean customerPersonalBean = null;
    private CustomerEmploymentBean employmentBean = null;
    private List<CardIncomeBean> incomeBeanList = null;
    private CardExpensesBean expensesBean = null;
    private List<CardBankDetailsBean> bankDetailsBeanLst = null;
    private List<DocumentUploadBean> documentDetailsBeanLst = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        CustomerPersonalBean personalBean = new CustomerPersonalBean();
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

            //set page code and task codes
//            String pageCode = PageVarList.ACCTEMPLATE;
//            String taskCode = TaskVarList.ADD;
            //check whethre userrole have an access for this page and task
            // if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
            String applicationId = sessionVarlist.getApplicationId();
            request.setAttribute("applicationId", applicationId);
            request.setAttribute("operationtype", "add");
            String cardAppCategoryCode = sessionVarlist.getApplicationTypeCode();
            if (cardAppCategoryCode != null && cardAppCategoryCode.equals("F")) {
                url = urlFD;
            } else if (cardAppCategoryCode != null && cardAppCategoryCode.equals("C")) {
                url = urlCorporate;
            }

            String idType = request.getParameter("hidIdType");
            personalBean.setIdentificationType(idType);
            personalBean.setIdentificationNumber(request.getParameter("hidIdNumber"));
            if (idType.equals("NIC")) {
                personalBean.setNic(request.getParameter("hidIdNumber"));
            } else if (idType.equals("PAS") || idType.equals("PAS_FXD")) {
                personalBean.setPassportNumber(request.getParameter("hidIdNumber"));
                personalBean.setPassportExpdate(request.getParameter("hideExpDate"));
            }

            personalBean.setApplicationId(applicationId);
            personalBean.setTitle(request.getParameter("title"));
            personalBean.setGender(request.getParameter("gender"));

            if (cardAppCategoryCode != null && cardAppCategoryCode.equals("C")) {
                personalBean.setFirstName(request.getParameter("firstName"));
                personalBean.setLastName(request.getParameter("middleName"));
                personalBean.setMiddleName(request.getParameter("lastName"));
            } else {
                personalBean.setFirstName(request.getParameter("firstname"));
                personalBean.setLastName(request.getParameter("surname"));
                personalBean.setMiddleName(request.getParameter("othername"));
            }
            personalBean.setNameOncard(request.getParameter("nameoncard"));
            personalBean.setMaritalStatus(request.getParameter("marital"));
            personalBean.setBirthday(request.getParameter("birthday"));
            personalBean.setNationality(request.getParameter("nationality"));
            personalBean.setAddress1(request.getParameter("resAddress1"));
            personalBean.setHomeTelNumber(request.getParameter("resPhoneNo"));
            personalBean.setPlaceOfbirth(request.getParameter("placeOfBirth"));
            personalBean.setAddress2(request.getParameter("resAddress2"));
            personalBean.setBloodgroup(request.getParameter("bloodGroup"));
            personalBean.setOfficeTelNumber(request.getParameter("officePhoneNo"));
            personalBean.setAddress3(request.getParameter("resAddress3"));
            personalBean.setCity(request.getParameter("resCity"));
            personalBean.setRelDistrict(request.getParameter("relDistrict"));
            personalBean.setRelProvince(request.getParameter("relProvince"));
            personalBean.setResidenceType(request.getParameter("restype"));
            personalBean.setResDistrict(request.getParameter("resDistrict"));
            personalBean.setResProvince(request.getParameter("resProvince"));
            personalBean.setMobileNumber(request.getParameter("mobileNo"));
            personalBean.setEmail(request.getParameter("email"));
            //personalBean.setReligion(request.getParameter("religion"));
            personalBean.setSpouseName(request.getParameter("spouseName"));
            personalBean.setSpouseNameofEmployee(request.getParameter("spouseemployer"));
            personalBean.setSpouseDesignation(request.getParameter("spouseDesignation"));
            personalBean.setSpousecompanyAddress(request.getParameter("spouseCompanyAddress"));
            personalBean.setSpouseNic(request.getParameter("spouseNic"));
            personalBean.setSpousePhone(request.getParameter("spouseOfficephone"));
            personalBean.setSpouseMail(request.getParameter("spouseMail"));
            personalBean.setSpouseMonthlyIncome(request.getParameter("spouseMonthIncome"));
            personalBean.setRelName(request.getParameter("relName"));
            personalBean.setRelationship(request.getParameter("relationship"));
            personalBean.setRelCompany(request.getParameter("relemployer"));
            personalBean.setRelAddress1(request.getParameter("reladdress1"));
            personalBean.setRelResidencePhone(request.getParameter("relResphoneno"));
            personalBean.setRelAddress2(request.getParameter("reladdress2"));
            personalBean.setRelOfficeNumber(request.getParameter("relOfficephoneNumber"));
            personalBean.setRelAddress3(request.getParameter("reladdress3"));
            personalBean.setRelMobile(request.getParameter("relmobile"));
            personalBean.setRelCity(request.getParameter("relcity"));
            personalBean.setRelEmail(request.getParameter("relmail"));
            personalBean.setMothersMaidenName(request.getParameter("mothersMadden"));
            personalBean.setAcedemicQualifications(request.getParameter("academicQualify"));
            personalBean.setProfessionalQualifications(request.getParameter("personalQualify"));
            personalBean.setVehicalType(request.getParameter("vehicleType"));
            personalBean.setVehicalNo(request.getParameter("vehicleNumber"));
            personalBean.setOwnership(request.getParameter("ownership"));
            personalBean.setCardType(request.getParameter("cardType"));
            personalBean.setCardProduct(request.getParameter("cardProduct"));
            personalBean.setCreditLimit(request.getParameter("creditLimit"));
            personalBean.setNameWithInitials(request.getParameter("nameWithInitials"));
            personalBean.setPermentAddress1(request.getParameter("address1"));
            personalBean.setPermentAddress2(request.getParameter("address2"));
            personalBean.setPermentAddress3(request.getParameter("address3"));
            personalBean.setPermentCity(request.getParameter("city"));
            personalBean.setDistrict(request.getParameter("district"));
            personalBean.setProvince(request.getParameter("province"));
            personalBean.setBillAddress1(request.getParameter("billAddress1"));
            personalBean.setBillAddress2(request.getParameter("billAddress2"));
            personalBean.setBillAddress3(request.getParameter("billAddress3"));
            personalBean.setBillCity(request.getParameter("billCity"));
            personalBean.setBillDistrict(request.getParameter("billDistrict"));
            personalBean.setBillProvince(request.getParameter("billProvince"));
            personalBean.setDurationofTheAddress(request.getParameter("addressyear"));
            personalBean.setCardCurrency(request.getParameter("cardCurency"));
            //new by chinthaka
            personalBean.setFullName(request.getParameter("fullname"));
            personalBean.setNoOfDependens(request.getParameter("noOfDependents"));
            personalBean.setSmsAlertStatus(request.getParameter("smsAlertStatus"));

            personalBean.setBusinessRegNumber(request.getParameter("businessRegNumber"));
            personalBean.setDesignation(request.getParameter("designation"));

            personalBean.setLastUpdateUser(sessionUser.getUserName());

            CustomerPersonalBean invalidMsgBean = new CustomerPersonalBean();

            invalidMsgBean = this.isValiedPersonalInfo(personalBean, cardAppCategoryCode);

            if (invalidMsgBean == null) {

                manager = new CaptureDataManager();
                this.setAudittraceValue(request, personalBean);
                int isAdd = manager.insertPersonalData(personalBean, systemAuditBean);

                if (isAdd == 1) {

                    this.loadDefaultApplicationStatus(applicationId, request);
//                    

                    request.setAttribute("successMsg", MessageVarList.ADD_PERSONAL_SUCCESS);

                    request.setAttribute("applicationId", applicationId);
                    request.setAttribute("operationtype", "add");
                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);
                } else {

                    this.loadDefaultApplicationStatus(applicationId, request);

                    request.setAttribute("errorMsg", MessageVarList.ADD_PERSONAL_ERROR);

                    request.setAttribute("applicationId", applicationId);
                    request.setAttribute("personalBean", personalBean);
//                    request.setAttribute("operationtype", "add");

                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);
                }

            } else {
                this.loadDefaultApplicationStatus(applicationId, request);

                request.setAttribute("applicationId", applicationId);
                request.setAttribute("personalBean", personalBean);
                request.setAttribute("invalidMsgBean", invalidMsgBean);

                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            }

//            } else {
//
//                //if invalid throw accessdenied exception
//                throw new AccessDeniedException();
//
//            }
        } catch (SQLException ex) {
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
            request.setAttribute("personalBean", personalBean);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
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
            request.setAttribute("personalBean", personalBean);
            this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
            request.setAttribute("errorMsg", "Error in action");
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            out.close();
        }
    }

    private void loadDefaultApplicationStatus(String appliactionId, HttpServletRequest request) throws Exception {

        this.getAllApplicationStatus(appliactionId);
        String loadTabIndex = "";
        String selectedTab = "0";

        if (appStatusBean != null) {

            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_INITIATE) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_PROCESS)) {

                IdBean idbean = new IdBean();
                idbean = this.getIdentifyDetails(appliactionId);

                if (appStatusBean.getTableOne().equals("1")) {
                    this.getAllDetailsCustomer(appliactionId);
                    request.setAttribute("personalBean", customerPersonalBean);
                    sessionVarlist.setPersonalBean(customerPersonalBean);
                    selectedTab = "1";

                } else {
                    customerPersonalBean = new CustomerPersonalBean();
                    customerPersonalBean.setIdentificationType(idbean.getIdType());
                    customerPersonalBean.setIdentificationNumber(idbean.getIdNumber());
                    request.setAttribute("personalBean", customerPersonalBean);
                    loadTabIndex = "0";
                }
                if (appStatusBean.getTableTwo().equals("1")) {
                    this.getAllEmpDetails(appliactionId);
                    request.setAttribute("employmentBean", employmentBean);
                    sessionVarlist.setEmploymentBean(employmentBean);
                    selectedTab = "2";
                } else {
                    loadTabIndex = loadTabIndex + "," + "1";
                }
                if (appStatusBean.getTableThree().equals("1")) {
                    this.getAllIncomeDetails(appliactionId);
                    this.getAllExpenseDetails(appliactionId);
                    request.setAttribute("expenseBean", expensesBean);

                    sessionVarlist.setExpensesBean(expensesBean);
                    sessionVarlist.setSessionIncomeList(incomeBeanList);
                    selectedTab = "3";

                } else {
                    loadTabIndex = loadTabIndex + "," + "2";
                }
                if (appStatusBean.getTableFore().equals("1")) {
                    this.getAllBankDetails(appliactionId);
                    sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);
                    selectedTab = "4";
                } else {

                    loadTabIndex = loadTabIndex + "," + "3";
                }
                if (appStatusBean.getTableFive().equals("1")) {
                    this.getAllDocumentDetails(appliactionId);
                    sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);
                    selectedTab = "5";

                } else {

                    loadTabIndex = loadTabIndex + "," + "4";
                }
                if (appStatusBean.getTableSix().equals("1")) {
                } else {

                    loadTabIndex = loadTabIndex + "," + "5";
                }

            }
//            this.getAllDocumentDetails(appliactionId);

        }

        request.setAttribute("loadTabIndex", loadTabIndex);
        request.setAttribute("selectedtab", selectedTab);

    }

    private void getAllDetailsCustomer(String appliactionId) throws Exception {
        try {
            manager = new CaptureDataManager();
            customerPersonalBean = manager.getAllDetailsCustomer(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }
    //get Employment StatusList

    private List<StatusBean> getEmploymentStatusList() throws Exception {
        systemUserManager = new SystemUserManager();
        statusList = systemUserManager.getStatusByUserRole("EMPL");
        return statusList;
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddCustomerPersonalInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddCustomerPersonalInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private CustomerPersonalBean isValiedPersonalInfo(CustomerPersonalBean personalBean, String cardAppCatCode) throws Exception {
        UserInputValidator validObject = new UserInputValidator();
        CustomerPersonalBean invalidMsgBean = new CustomerPersonalBean();
        int msg = 0;
        if (cardAppCatCode == null) {
            cardAppCatCode = "";
        }

        try {
            //----------------------------------------------------------------------------           

            if (personalBean.getTitle().isEmpty()) {
                invalidMsgBean.setTitle("Requierd");
                msg = 1;
            }
            /*if (personalBean.getReligion().isEmpty()) {
             invalidMsgBean.setReligion("Requierd");
             msg = 1;
             } else if (!validObject.isCorrectString(personalBean.getReligion())) {
             invalidMsgBean.setReligion("Invalid");
             msg = 1;
             }*/
            if (personalBean.getFirstName() != null) {

                if (personalBean.getFirstName().isEmpty()) {
                    invalidMsgBean.setFirstName("Requierd");
                    msg = 1;
                } else if (!validObject.isNonNumericNonSpecialString(personalBean.getFirstName())) {
                    invalidMsgBean.setFirstName("Invalid");
                    msg = 1;
                }
            }

            if (personalBean.getLastName() != null) {

                if (personalBean.getLastName().isEmpty()) {
                    invalidMsgBean.setLastName("Requierd");
                    msg = 1;
                } else if (!validObject.isNonNumericNonSpecialString(personalBean.getLastName())) {
                    invalidMsgBean.setLastName("Invalid");
                    msg = 1;
                }
            }

            if (personalBean.getMiddleName() != null) {

                if (personalBean.getMiddleName().isEmpty()) {
                    //                invalidMsgBean.setMiddleName("Requierd");
                    //                msg = 1;
                } else if (!validObject.isNonNumericNonSpecialString(personalBean.getMiddleName())) {
                    invalidMsgBean.setMiddleName("Invalid");
                    msg = 1;
                }
            }

            if (personalBean.getNameOncard().isEmpty()) {
                invalidMsgBean.setNameOncard("Requierd");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(personalBean.getNameOncard())) {
                invalidMsgBean.setNameOncard("Invalid");
                msg = 1;
            }
//            if (personalBean.getPalceOfBirth().isEmpty()) {
//                invalidMsgBean.setPalceOfBirth("Requierd");
//                msg = 1;
//            } else if (!validObject.isCorrectString(personalBean.getPalceOfBirth())) {
//                invalidMsgBean.setPalceOfBirth("Invalid");
//                msg = 1;
//            }
//             if (personalBean.getBloodgroup().isEmpty()) {
//                invalidMsgBean.setBloodgroup("Requierd");
//                msg = 1;
//            }
            if (personalBean.getNationality().isEmpty()) {
                invalidMsgBean.setNationality("Requierd");
                msg = 1;
            }
            if (personalBean.getCardProduct().isEmpty()) {
                invalidMsgBean.setCardProduct("Requierd");
            }
            if (personalBean.getNameWithInitials().isEmpty()) {
                invalidMsgBean.setNameWithInitials("Requierd");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(personalBean.getNameWithInitials())) {
                invalidMsgBean.setNameWithInitials("Invalid");
                msg = 1;
            }
            if (personalBean.getBirthday().isEmpty()) {
                invalidMsgBean.setBirthday("Requierd");
                msg = 1;
            }
            if (personalBean.getNoOfDependens() != null && !personalBean.getNoOfDependens().isEmpty()) {
                if (!validObject.isNumeric(personalBean.getNoOfDependens())) {
                    invalidMsgBean.setNoOfDependens("Invalid");
                    msg = 1;
                }
            }
//             else if (!validObject.isNonNumericNonSpecialString(personalBean.getBirthday())) {
//                invalidMsgBean.setBirthday("Invalid");
//                msg = 1;
//            }
            //-----------------============================================================================================================================-------------------         
            if (personalBean.getPermentAddress1().isEmpty()) {
                invalidMsgBean.setPermentAddress1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(personalBean.getPermentAddress1())) {
                invalidMsgBean.setPermentAddress1("Invalid");
                msg = 1;
            }
            if (personalBean.getOfficeTelNumber().isEmpty()) {
                invalidMsgBean.setOfficeTelNumber("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(personalBean.getOfficeTelNumber())) {
                invalidMsgBean.setOfficeTelNumber("Invalid");
                msg = 1;
            }
            if (personalBean.getMobileNumber().isEmpty()) {
                invalidMsgBean.setMobileNumber("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(personalBean.getMobileNumber())) {
                invalidMsgBean.setMobileNumber("Invalid");
                msg = 1;
            }
            if (personalBean.getHomeTelNumber().isEmpty()) {
                invalidMsgBean.setHomeTelNumber("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(personalBean.getHomeTelNumber())) {
                invalidMsgBean.setHomeTelNumber("Invalid");
                msg = 1;
            }
            if (personalBean.getEmail().isEmpty()) {
                invalidMsgBean.setEmail("Requierd");
                msg = 1;
            } else if (!validObject.isValidEmail(personalBean.getEmail())) {
                invalidMsgBean.setEmail("Invalid");
                msg = 1;
            }
            if (personalBean.getAddress1().isEmpty()) {
                invalidMsgBean.setAddress1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(personalBean.getAddress1())) {
                invalidMsgBean.setAddress1("Invalid");
                msg = 1;
            }
            if (personalBean.getAddress2().isEmpty()) {
//                invalidMsgBean.setAddress2("Requierd");
//                msg = 1;
            } else if (!validObject.isCorrectAddress(personalBean.getAddress2())) {
                invalidMsgBean.setAddress2("Invalid");
                msg = 1;
            }
            if (personalBean.getAddress3().isEmpty()) {
//                invalidMsgBean.setAddress3("Requierd");
//                msg = 1;
            } else if (!validObject.isCorrectAddress(personalBean.getAddress3())) {
                invalidMsgBean.setAddress3("Invalid");
                msg = 1;
            }
            if (personalBean.getCity().isEmpty()) {
                invalidMsgBean.setCity("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(personalBean.getCity())) {
                invalidMsgBean.setCity("Invalid");
                msg = 1;
            }
            if (personalBean.getSpouseMail() != null && !personalBean.getSpouseMail().isEmpty()) {
                if (!validObject.isValidEmail(personalBean.getSpouseMail())) {
                    invalidMsgBean.setSpouseMail("Invalid");
                    msg = 1;
                }
            }
            if (personalBean.getSpouseMonthlyIncome() != null && !personalBean.getSpouseMonthlyIncome().isEmpty()) {
                if (!validObject.isDecimal_Numeric(personalBean.getSpouseMonthlyIncome())) {
                    invalidMsgBean.setSpouseMonthlyIncome("Invalid");
                    msg = 1;
                }
            }
//            if (personalBean.getResidenceType().isEmpty()) {
//                invalidMsgBean.setResidenceType("Requierd");
//                msg = 1;
//            } else if (!validObject.isCorrectString(personalBean.getResidenceType())) {
//                invalidMsgBean.setResidenceType("Invalid");
//                msg = 1;
//            }

//=======================================================================================================================================================================
            if (!cardAppCatCode.equals("F") && !cardAppCatCode.equals("C")) {
                if (personalBean.getRelName().isEmpty()) {
                    invalidMsgBean.setRelName("Requierd");
                    msg = 1;
                } else if (!validObject.isNonNumericNonSpecialString(personalBean.getRelName())) {
                    invalidMsgBean.setRelName("Invalid");
                    msg = 1;
                }
                if (personalBean.getRelationship().isEmpty()) {
                    invalidMsgBean.setRelationship("Requierd");
                    msg = 1;
                } else if (!validObject.isNonNumericNonSpecialString(personalBean.getRelationship())) {
                    invalidMsgBean.setRelationship("Invalid");
                    msg = 1;
                }
                if (personalBean.getRelCompany().isEmpty()) {
                    invalidMsgBean.setRelCompany("Requierd");
                    msg = 1;
                } else if (!validObject.isCorrectCompanyName(personalBean.getRelCompany())) {
                    invalidMsgBean.setRelCompany("Invalid");
                    msg = 1;
                }
                if (personalBean.getRelAddress1().isEmpty()) {
                    invalidMsgBean.setRelAddress1("Requierd");
                    msg = 1;
                } else if (!validObject.isCorrectAddress(personalBean.getRelAddress1())) {
                    invalidMsgBean.setRelAddress1("Invalid");
                    msg = 1;
                }
                if (personalBean.getRelAddress2().isEmpty()) {

                } else if (!validObject.isCorrectAddress(personalBean.getRelAddress2())) {
                    invalidMsgBean.setRelAddress2("Invalid");
                    msg = 1;
                }
                if (personalBean.getRelAddress3().isEmpty()) {

                } else if (!validObject.isCorrectAddress(personalBean.getRelAddress3())) {
                    invalidMsgBean.setRelAddress3("Invalid");
                    msg = 1;
                }
                if (!personalBean.getRelAddress3().isEmpty() && personalBean.getRelAddress2().isEmpty()) {
                    invalidMsgBean.setRelAddress2("Requierd");
                    msg = 1;
                }
                if (personalBean.getRelCity().isEmpty()) {
                    invalidMsgBean.setRelCity("Requierd");
                    msg = 1;
                } else if (!validObject.isCorrectString(personalBean.getRelCity())) {
                    invalidMsgBean.setRelCity("Invalid");
                    msg = 1;
                }

                if (personalBean.getRelOfficeNumber().isEmpty()) {
                    invalidMsgBean.setRelOfficeNumber("Requierd");
                    msg = 1;
                } else if (!validObject.isNumeric(personalBean.getRelOfficeNumber())) {
                    invalidMsgBean.setRelOfficeNumber("Invalid");
                    msg = 1;
                }
                if (personalBean.getRelMobile().isEmpty()) {
                    invalidMsgBean.setRelMobile("Requierd");
                    msg = 1;
                } else if (!validObject.isNumeric(personalBean.getRelMobile())) {
                    invalidMsgBean.setRelMobile("Invalid");
                    msg = 1;
                }
                if (personalBean.getRelResidencePhone().isEmpty()) {
                    invalidMsgBean.setRelResidencePhone("Requierd");
                    msg = 1;
                } else if (!validObject.isNumeric(personalBean.getRelResidencePhone())) {
                    invalidMsgBean.setRelResidencePhone("Invalid");
                    msg = 1;
                }
                //            if (personalBean.getRelEmail().isEmpty()) {
                //                invalidMsgBean.setRelEmail("Requierd");
                //                msg = 1;
                //            } else if (!validObject.isValidEmail(personalBean.getRelEmail())) {
                //                invalidMsgBean.setRelEmail("Invalid");
                //                msg = 1;
                //            }                
            }

//==================================================================================================================================================
            if (cardAppCatCode.equals("F")) {

                if (personalBean.getMothersMaidenName() != null) {
                    if (personalBean.getMothersMaidenName().isEmpty()) {
                        invalidMsgBean.setMothersMaidenName("Requierd");
                        msg = 1;
                    } else if (!validObject.isNonNumericNonSpecialString(personalBean.getMothersMaidenName())) {
                        invalidMsgBean.setMothersMaidenName("Invalid");
                        msg = 1;
                    }
                }
                if (personalBean.getAcedemicQualifications() != null) {
                    if (personalBean.getAcedemicQualifications().isEmpty()) {
                        invalidMsgBean.setAcedemicQualifications("Requierd");
                        msg = 1;
                    } else if (!validObject.isCorrectString(personalBean.getAcedemicQualifications())) {
                        invalidMsgBean.setAcedemicQualifications("Invalid");
                        msg = 1;
                    }
                }
                if (personalBean.getProfessionalQualifications() != null) {
                    if (personalBean.getProfessionalQualifications().isEmpty()) {
                        invalidMsgBean.setProfessionalQualifications("Requierd");
                        msg = 1;
                    } else if (!validObject.isCorrectString(personalBean.getProfessionalQualifications())) {
                        invalidMsgBean.setProfessionalQualifications("Invalid");
                        msg = 1;
                    }
                }
                if (personalBean.getVehicalType() != null) {
                    if (personalBean.getVehicalType().isEmpty()) {
                        //                invalidMsgBean.setVehicalType("Requierd");
                        //                msg = 1;
                    } else if (!validObject.isCorrectString(personalBean.getVehicalType())) {
                        invalidMsgBean.setVehicalType("Invalid");
                        msg = 1;
                    }
                }

                if (personalBean.getVehicalType() != null) {
                    if (!personalBean.getVehicalType().isEmpty() && personalBean.getVehicalNo().isEmpty()) {
                        invalidMsgBean.setVehicalNo("Requierd");
                        msg = 1;
                    }
                } 
//                else if (personalBean.getVehicalType() != null && personalBean.getVehicalNo()== null && !validObject.isCorrectAddress(personalBean.getVehicalNo())) {
//                    invalidMsgBean.setVehicalNo("Invalid");
//                    msg = 1;
//                }
            }

            if (personalBean.getCreditLimit() != null) {
                if (personalBean.getCreditLimit().isEmpty()) {
                    invalidMsgBean.setCreditLimit("Requierd");
                    msg = 1;
                } else if (!validObject.isDecimalOrNumeric(personalBean.getCreditLimit(), "15", "2")) {
                    invalidMsgBean.setCreditLimit("Invalid");
                    msg = 1;
                }
            }

            if (personalBean.getFullName() != null) {
                //validate full name
                if (personalBean.getFullName().isEmpty()) {
                    invalidMsgBean.setFullName("Requierd");
                    msg = 1;
                } else if (!validObject.isNonNumericNonSpecialString(personalBean.getFullName())) {
                    invalidMsgBean.setFullName("Invalid");
                    msg = 1;
                }
            }
            if (msg == 0) {
                invalidMsgBean = null;
            }

            return invalidMsgBean;

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

    private void getAllEmpDetails(String appliactionId) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            employmentBean = checkingManager.getCardEmployementDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllIncomeDetails(String appliactionId) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            incomeBeanList = checkingManager.getCardIncomeDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllExpenseDetails(String appliactionId) throws Exception {

        try {
            checkingManager = new ApplicationCheckingManager();
            expensesBean = checkingManager.getExpensesDetails(appliactionId);

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

    private void getAllDocumentDetails(String appliactionId) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            documentDetailsBeanLst = checkingManager.getCardDocumentDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setAudittraceValue(HttpServletRequest request, CustomerPersonalBean bean) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Personal Details in to Application ID : '" + bean.getApplicationId() + "' by :" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CAPTURE_DATA);
            systemAuditBean.setPageCode(PageVarList.USERDATACAPTURE);
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

    private IdBean getIdentifyDetails(String appliactionId) throws Exception {
        IdBean bean = new IdBean();
        try {
            manager = new CaptureDataManager();
            bean = manager.getIdentifyDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
        return bean;
    }
}
