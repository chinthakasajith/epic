/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
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
public class AddSuplimentoryPersonalInfoServlet extends HttpServlet {

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
    private SystemAuditBean systemAuditBean;
    private String url = "/camm/capturedata/suplimentoryapplication.jsp";
    private ApplicationCheckingManager checkingManager;
    private CardApplicationStatusBean appStatusBean = null;
    private SupplementaryApplicationBean customerPersonalBean = null;
    private List<DocumentUploadBean> documentDetailsBeanLst = null;
    private String pCardCrdtLimit = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        SupplementaryApplicationBean personalBean = new SupplementaryApplicationBean();
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

            String idType = request.getParameter("hidIdType");
            String priIdType = request.getParameter("priIdType");
            personalBean.setIdentificationType(idType);
            
            personalBean.setIdentificationNumber(request.getParameter("hidIdNumber"));
            if (idType.contains("NIC")) {
                //System.out.println("primary identification number is"+request.getParameter("hidIdNumber"));
                personalBean.setNic(request.getParameter("hidIdNumber"));
            } else if (idType.contains("PAS")) {
                personalBean.setPassportNumber(request.getParameter("hidIdNumber"));
            }

            String primaryId = request.getParameter("primaryIdentifyNumber").trim();
            String primaryCardNo = request.getParameter("PrimaryCardNumber").trim();
            String primaryApplicationId = request.getParameter("primaryApplicationId").trim();

            //if primary card num is empty
            if ((primaryCardNo == null) || primaryCardNo.isEmpty()) {
                primaryCardNo = this.getPrimaryCardNo(primaryApplicationId);
            }
            //if primary application id is empty,then get it using card number
            if ((primaryApplicationId == null || primaryApplicationId.isEmpty())) {
                primaryApplicationId = this.getPrimaryApplicationId(primaryCardNo);
            }

            //if primary id is empty 
            if (primaryId == null || primaryId.isEmpty()) {
                String primaryIdData = this.getPrimaryIdDetails(primaryApplicationId);
                if (!primaryIdData.isEmpty()) {
                    primaryId = primaryIdData.split(":")[0];
                    priIdType = primaryIdData.split(":")[1];
                }

            }

            personalBean.setApplicationId(applicationId);
            personalBean.setPrimaryCardNumber(primaryCardNo);
            personalBean.setPrimaryCardApplicationId(primaryApplicationId);
            personalBean.setPrimaryId(primaryId);
            personalBean.setPrimaryIdType(priIdType);
            //set primary id
            // personalBean.setPrimaryId(request.getParameter("primaryIdentifyNumber").trim());

            personalBean.setTitle(request.getParameter("title"));
            personalBean.setFirstName(request.getParameter("firstname").trim());
            personalBean.setGender(request.getParameter("gender"));
            personalBean.setLastName(request.getParameter("lastName").trim());
            personalBean.setMiddleName(request.getParameter("middlename").trim());
            personalBean.setRelationShip(request.getParameter("relationship").trim());
            personalBean.setNameOncard(request.getParameter("nameoncard").trim());
            personalBean.setNameWithinitials(request.getParameter("nameWithInitials").trim());
            personalBean.setBirthday(request.getParameter("birthday"));
            personalBean.setNationality(request.getParameter("nationality"));
            personalBean.setAddress1(request.getParameter("resAddress1").trim());
            personalBean.setHomeTelNumber(request.getParameter("resPhoneNo"));
            personalBean.setAddress2(request.getParameter("resAddress2").trim());
            personalBean.setAddress3(request.getParameter("resAddress3").trim());
            personalBean.setBillingAdress1(request.getParameter("billAddress1").trim());
            personalBean.setBillingAdress2(request.getParameter("billAddress2").trim());
            personalBean.setBillingAdress3(request.getParameter("billAddress3").trim());
            personalBean.setBillingCity(request.getParameter("billCity"));
            personalBean.setBillDistrict(request.getParameter("billDistrict"));
            personalBean.setBillProvince(request.getParameter("billProvince"));
            personalBean.setCity(request.getParameter("resCity"));
            personalBean.setResDistrict(request.getParameter("resDistrict"));
            personalBean.setResProvince(request.getParameter("resProvince"));
            personalBean.setMobileNumber(request.getParameter("mobileNo"));
            personalBean.setEmployementType(request.getParameter("empType"));
            personalBean.setOccupation(request.getParameter("occupation"));
            personalBean.setCardType(request.getParameter("cardType"));
            personalBean.setCardProduct(request.getParameter("cardProduct"));
            //personalBean.setCreditLimit(request.getParameter("creditLimit").trim());
            personalBean.setLastUpdateUser(sessionUser.getUserName());
            personalBean.setClimitReqType(request.getParameter("climitReqType"));

            if (personalBean.getClimitReqType().equals("FIXED")) {
                personalBean.setCreditLimit(request.getParameter("creditLimit").trim());
            } else if (personalBean.getClimitReqType().equals("PER")) {
                personalBean.setPercentageValue(request.getParameter("creditLimit").trim());
                //String cLimit = this.getRequestedCreditLimitFromPercentageValue(primaryCardNo, personalBean.getPercentageValue());
                //personalBean.setCreditLimit(cLimit);
            }
            SupplementaryApplicationBean invalidMsgBean = new SupplementaryApplicationBean();

            invalidMsgBean = this.isValiedSuplimentoryPersonalInfo(personalBean);

            if (invalidMsgBean == null) {

                manager = new CaptureDataManager();
                // this.setAudittraceValue(request, personalBean);
                int isAdd = manager.insertSuplimentorryPersonalData(personalBean, systemAuditBean);

                if (isAdd == 1) {

                    this.loadDefaultApplicationStatus(applicationId, request);
//                    

                    request.setAttribute("successMsg", "Sucssesfully added Customer personal details.");

                    request.setAttribute("applicationId", applicationId);
                    request.setAttribute("personalBean", personalBean);
                    request.setAttribute("operationtype", "add");

                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);
                } else {

                    //this.loadDefaultApplicationStatus(applicationId, request);
                    request.setAttribute("errorMsg", "Faild adding Customer personal details.");

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
            Logger.getLogger(AddSuplimentoryPersonalInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AddSuplimentoryPersonalInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private void loadDefaultApplicationStatus(String appliactionId, HttpServletRequest request) throws Exception {
        this.getAllApplicationStatus(appliactionId);
        String loadTabIndex = "";
        String selectedTab = "0";

        if (appStatusBean != null) {

            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_INITIATE) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_PROCESS)) {

                if (appStatusBean.getTableOne().equals("1")) {
                    this.getAllDetailsSuplimentoryCustomer(appliactionId);
                    request.setAttribute("personalBean", customerPersonalBean);
                    sessionVarlist.setSuplimentoryPersonalBean(customerPersonalBean);
                    selectedTab = "1";

                } else {
                    loadTabIndex = "0";
                }

                if (appStatusBean.getTableFive().equals("1")) {
                    this.getAllDocumentDetails(appliactionId);
                    sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);
                    selectedTab = "2";

                } else {

                    loadTabIndex = loadTabIndex + "," + "1";
                }
                if (appStatusBean.getTableSix().equals("1")) {
                } else {

                    loadTabIndex = loadTabIndex + "," + "2";
                }

            }
//            this.getAllDocumentDetails(appliactionId);

        }

        request.setAttribute("loadTabIndex", loadTabIndex);
        request.setAttribute("selectedtab", selectedTab);
    }

    private void getAllApplicationStatus(String appliactionId) throws Exception {

        try {
            manager = new CaptureDataManager();
            appStatusBean = manager.getAllApplicationStatus(appliactionId);

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

    private void getAllDetailsSuplimentoryCustomer(String appliactionId) throws Exception {
        try {
            manager = new CaptureDataManager();
            customerPersonalBean = manager.getAllDetailsSuplimentoryCustomer(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private SupplementaryApplicationBean isValiedSuplimentoryPersonalInfo(SupplementaryApplicationBean personalBean) throws Exception {
        UserInputValidator validObject = new UserInputValidator();
        SupplementaryApplicationBean invalidMsgBean = new SupplementaryApplicationBean();
        int msg = 0;

        try {
            //----------------------------------------------------------------------------           
            if (personalBean.getPrimaryCardNumber().isEmpty() && personalBean.getPrimaryCardApplicationId().isEmpty()) {
                invalidMsgBean.setPrimaryCardApplicationId("Requierd");
                msg = 1;
            }

            //----------------------------------------------------------------------------   
            if (personalBean.getIdentificationType() == null) {
            } else {

                if (personalBean.getIdentificationType().equals("NIC")) {
                    if (personalBean.getNic().isEmpty()) {
                        invalidMsgBean.setNic("Requierd");
                        msg = 1;

                    } else if (!validObject.checkNIC(personalBean.getNic())) {
                        invalidMsgBean.setNic("Invalid");  
                        msg = 1;
                    }

                }
                if (personalBean.getIdentificationType().equals("PASS")) {

                    if (personalBean.getPassportNumber() == null) {
                        invalidMsgBean.setNic("Requierd");
                        msg = 1;
                    } else if (!validObject.isAlphaNumeric(personalBean.getPassportNumber())) {

                        invalidMsgBean.setNic("Invalid");
                        msg = 1;
                    }
                }

            }
            //----------------------------------------------------------------------------            

            //----------------------------------------------------------------------------            
            if (personalBean.getTitle().isEmpty()) {
                invalidMsgBean.setTitle("Requierd");
                msg = 1;
            }

            if (personalBean.getFirstName().isEmpty()) {
                invalidMsgBean.setFirstName("Requierd");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(personalBean.getFirstName())) {
                invalidMsgBean.setFirstName("Invalid");
                msg = 1;
            }
            if (personalBean.getLastName().isEmpty()) {
                invalidMsgBean.setLastName("Requierd");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(personalBean.getLastName())) {
                invalidMsgBean.setLastName("Invalid");
                msg = 1;
            }
            if (personalBean.getMiddleName().isEmpty()) {
                invalidMsgBean.setMiddleName("Requierd");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(personalBean.getMiddleName())) {
                invalidMsgBean.setMiddleName("Invalid");
                msg = 1;
            }
            if (personalBean.getNameOncard().isEmpty()) {
                invalidMsgBean.setNameOncard("Requierd");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(personalBean.getNameOncard())) {
                invalidMsgBean.setNameOncard("Invalid");
                msg = 1;
            }
//          
            //-----------------============================================================================================================================-------------------         

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

            if (personalBean.getAddress1().isEmpty()) {
                invalidMsgBean.setAddress1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(personalBean.getAddress1())) {
                invalidMsgBean.setAddress1("Invalid");
                msg = 1;
            }
            if (personalBean.getAddress2().isEmpty()) {
                invalidMsgBean.setAddress2("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(personalBean.getAddress2())) {
                invalidMsgBean.setAddress2("Invalid");
                msg = 1;
            }
            if (personalBean.getAddress3().isEmpty()) {
                invalidMsgBean.setAddress3("Requierd");
                msg = 1;
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
            if (personalBean.getClimitReqType().equals("FIXED")) {
                if (personalBean.getCreditLimit().isEmpty()) {
                    invalidMsgBean.setCreditLimit("Requierd");
                    msg = 1;
                } else if (!validObject.isDecimalOrNumeric(personalBean.getCreditLimit(), "15", "2")) {
                    invalidMsgBean.setCreditLimit("Invalid");
                    msg = 1;
                }
            }
            if (personalBean.getClimitReqType().equals("PER")) {
                if (personalBean.getPercentageValue().isEmpty()) {
                    invalidMsgBean.setCreditLimit("Requierd");
                    msg = 1;
                } else if (!validObject.isDecimal_Numeric(personalBean.getPercentageValue())) {
                    invalidMsgBean.setCreditLimit("Invalid");
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

    private String getPrimaryCardNo(String primaryApplicationId) throws Exception {
        try {
            manager = new CaptureDataManager();
            return manager.getPrimaryCardNumber(primaryApplicationId);

        } catch (Exception ex) {
            throw ex;
        }

    }

    //get primary appication id from card number

    private String getPrimaryApplicationId(String primaryCardNumber) throws Exception {
        try {
            manager = new CaptureDataManager();
            return manager.getPrimaryApplicationId(primaryCardNumber);
        } catch (Exception e) {
            throw e;
        }
    }

    //get primaryId 

    private String getPrimaryIdDetails(String primaryApplicationId) throws Exception {
        try {
            manager = new CaptureDataManager();
            return manager.getPrimaryIdDetails(primaryApplicationId);
        } catch (Exception e) {
            throw e;
        }
    }

    //get requested credit limit according to the percentage value
//    private String getRequestedCreditLimitFromPercentageValue(String primaryCardNumber, String per) throws Exception {
//        String cLimit = "";
//        try {
//            manager = new CaptureDataManager();
//            cLimit = manager.getRequestedCreditLimitFromPercentageValue(primaryCardNumber, per);
//        } catch (Exception ex) {
//            throw ex;
//        }
//        return cLimit;
//    }
}
