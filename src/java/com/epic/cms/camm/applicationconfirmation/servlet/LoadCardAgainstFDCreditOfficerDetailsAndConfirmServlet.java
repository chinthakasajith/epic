/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ProductionModeBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.camm.applicationconfirmation.bean.RejectReasonBean;
import com.epic.cms.camm.applicationconfirmation.businesslogic.ApplicationConfirmationManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerificationPointsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.DocumentVerifyManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
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
 * @author chinthaka_r
 */
public class LoadCardAgainstFDCreditOfficerDetailsAndConfirmServlet extends HttpServlet {

    private RequestDispatcher rd = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private ApplicationCheckingManager checkingmanager = null;
    private DocumentVerifyManager documentVerifyManager = null;
    private ApplicationConfirmationManager confirmationManager = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private List<String> userTaskList;
    private CardApplicationStatusBean appStatusBean = null;
    private CardApplicationBean cardApplicationList;
    private CustomerPersonalBean personalDetail;
    private List<RejectReasonBean> rejectReasons;
    private List<ProductionModeBean> productionModeList;
    private List<CardProductBean> productList;
    private List<CustomerTempBean> staffCusList;
    private List<DocumentUploadBean> uploadedDocumentTypeList = null;
    private List<VerificationPointsBean> verificationPointsBeanList = null;
    private List<CardProductBean> cardProducts = null;
    private String currencyAlphaCode = null;
    private String url = "/camm/applicationconfirmation/cardagainstfdcreditofficerreviewandconfirm.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //call to existing session
            ////////////////////////////////////////////////////////////////////////////////
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

                try {
                    //set page code and task codes
                    String pageCode = PageVarList.CREDITOFFICERREVIEWANDCONFIRM;
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
            //////////////////////////////////////////////////////////////////////////
            try {
                String applicationId = request.getParameter("applicationid");
                if (applicationId == null) {
                    applicationId = request.getAttribute("applicationid").toString();
                }
                // set the application id to session for further usage..
                sessionVarlist.setVerifyingAppId(applicationId);
                //get and store app status in appsattus bean
                this.loadApplicationStatus(applicationId);
                //get card application details
                this.getCardApplicationDetails(applicationId);
                //get card application personal details
                this.getDataFromCardApplicationPersonalTable(applicationId);
                //get Card products list to requested card type
                this.getCardProductsToRequestedCardType(personalDetail.getCardType());
                //get currency alpha code
                this.getCurrencyAlphaCode(personalDetail.getCardCurrency());
                
                request.setAttribute("cardApplicationList", cardApplicationList);
                request.setAttribute("personalDetail", personalDetail);
                request.setAttribute("cardProducts", cardProducts);
                request.setAttribute("currencyAlphaCode", currencyAlphaCode);
                
                //get uploaded document types
                this.getuploadedDocumentsType(applicationId);
                //get verification points
                this.getVerificationPoints("F");
                
                
                // set the details about uploaded document file details (filepath,name,verification category..)
                for (int i = 0; i < verificationPointsBeanList.size(); i++) {
                    verificationPointsBeanList.get(i).setDocumentExist("NO");
                    for (int j = 0; j < uploadedDocumentTypeList.size(); j++) {
                        if (verificationPointsBeanList.get(i).getDocumentType() != null) {
                            if (verificationPointsBeanList.get(i).getDocumentType().equals(uploadedDocumentTypeList.get(j).getDocumentType())) {
                                verificationPointsBeanList.get(i).setDocumentExist("YES");
                                verificationPointsBeanList.get(i).setVerificationCategory(uploadedDocumentTypeList.get(j).getVerificationCategory());
                                verificationPointsBeanList.get(i).setFileName(uploadedDocumentTypeList.get(j).getFileName());
                                break;
                            }
                        }
                    }
                }
                // set all the points details to the session
                sessionVarlist.setVerificationPointsBeanList(verificationPointsBeanList);
                sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                
                //check whether app in CORC status
                if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_CREDIT_OFFICER_REVIEW_COMPLETE)) {
                    //get confirmation details
                    this.configConfirmationDetails(request, applicationId);
                }else{
                     request.setAttribute("isConfirmMode", 0);
                }

            } catch (Exception e) {
                request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);

            } finally {
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

    //load appication status
    private void loadApplicationStatus(String appliactionId) throws Exception {

        try {
            CaptureDataManager manager = new CaptureDataManager();
            appStatusBean = manager.getAllApplicationStatus(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardApplicationDetails(String applicationId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            cardApplicationList = checkingmanager.getCardInfomationDetails(applicationId);

            if (cardApplicationList.getApplicationId() == null) {
                cardApplicationList.setApplicationId("-");
            }

            if (cardApplicationList.getAssignStatus() == null) {
                cardApplicationList.setAssignStatus("-");
            }

            if (cardApplicationList.getAssignUser() == null) {
                cardApplicationList.setAssignUser("-");
            }

            if (cardApplicationList.getBranchCode() == null) {
                cardApplicationList.setBranchCode("-");
            }
            if (cardApplicationList.getIdentificationNumber() == null) {
                cardApplicationList.setIdentificationNumber("-");
            }

            if (cardApplicationList.getIdentificationType() == null) {
                cardApplicationList.setIdentificationType("-");
            }

            if (cardApplicationList.getReferencialEmpNum() == null) {
                cardApplicationList.setReferencialEmpNum("-");
            }

        } catch (Exception e) {
            throw e;
        }
    }

    //get card app personal details
    private void getDataFromCardApplicationPersonalTable(String applicationId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            personalDetail = checkingmanager.getPersonalDetails(applicationId);

            if (personalDetail.getAcedemicQualifications() == null) {
                personalDetail.setAcedemicQualifications("-");
            }

            if (personalDetail.getAddress1() == null) {
                personalDetail.setAddress1("-");
            }

            if (personalDetail.getAddress2() == null) {
                personalDetail.setAddress2("-");
            }

            if (personalDetail.getAddress3() == null) {
                personalDetail.setAddress3("-");
            }

            if (personalDetail.getApplicationId() == null) {
                personalDetail.setApplicationId("-");
            }

            if (personalDetail.getBillAddress1() == null) {
                personalDetail.setBillAddress1("-");
            }

            if (personalDetail.getBillAddress2() == null) {
                personalDetail.setBillAddress2("-");
            }

            if (personalDetail.getBillAddress3() == null) {
                personalDetail.setBillAddress3("-");
            }

            if (personalDetail.getBillCity() == null) {
                personalDetail.setBillCity("-");
            }

            if (personalDetail.getBillDistrict() == null) {
                personalDetail.setBillDistrict("-");
            }

            if (personalDetail.getBillProvince() == null) {
                personalDetail.setBillProvince("-");
            }

            if (personalDetail.getBirthday() == null) {
                personalDetail.setBirthday("-");
            }

            if (personalDetail.getBloodgroup() == null) {
                personalDetail.setBloodgroup("-");
            }

            if (personalDetail.getCardProduct() == null) {//requested card product
                personalDetail.setCardProduct("-");
            }
            if (personalDetail.getCardProductDes() == null) {//requested card product des
                personalDetail.setCardProductDes("-");
            }

            if (personalDetail.getCardType() == null) {//requested card type
                personalDetail.setCardType("-");
            }
            if (personalDetail.getCardTypeDes() == null) {//requested card type description
                personalDetail.setCardTypeDes("-");
            }

            if (personalDetail.getCity() == null) {
                personalDetail.setCity("-");
            }

            if (personalDetail.getCreditLimit() == null) {
                personalDetail.setCreditLimit("-");
            }

            if (personalDetail.getDistrict() == null) {
                personalDetail.setDistrict("-");
            }

            if (personalDetail.getDurationofTheAddress() == null) {
                personalDetail.setDurationofTheAddress("-");
            }

            if (personalDetail.getEmail() == null) {
                personalDetail.setEmail("-");
            }

            if (personalDetail.getEmergencyContactNumber() == null) {
                personalDetail.setEmergencyContactNumber("-");
            }
            if (personalDetail.getFullName() == null) {
                personalDetail.setFullName("-");
            }

            if (personalDetail.getGender() == null) {
                personalDetail.setGender("-");
            }

            if (personalDetail.getHomeTelNumber() == null) {
                personalDetail.setHomeTelNumber("-");
            }

            if (personalDetail.getIdentificationNumber() == null) {
                personalDetail.setIdentificationNumber("-");
            }

            if (personalDetail.getIdentificationType() == null) {
                personalDetail.setIdentificationType("-");
            }

            if (personalDetail.getInitials() == null) {
                personalDetail.setInitials("-");
            }

//            if (personalDetail.getLastName() == null) {
//                personalDetail.setLastName("-");
//            }
            if (personalDetail.getMaritalStatus() == null) {
                personalDetail.setMaritalStatus("-");
            }

//            if (personalDetail.getMiddleName() == null) {
//                personalDetail.setMiddleName("-");
//            }
            if (personalDetail.getMobileNumber() == null) {
                personalDetail.setMobileNumber("-");
            }

            if (personalDetail.getMonthlyRental() == null) {
                personalDetail.setMonthlyRental("-");
            }

            if (personalDetail.getMorgageRental() == null) {
                personalDetail.setMorgageRental("-");
            }

            if (personalDetail.getMothersMaidenName() == null) {
                personalDetail.setMothersMaidenName("-");
            }

            if (personalDetail.getNameOncard() == null) {
                personalDetail.setNameOncard("-");
            }

            if (personalDetail.getNameWithInitials() == null) {
                personalDetail.setNameWithInitials("-");
            }

            if (personalDetail.getNationality() == null) {
                personalDetail.setNationality("-");
            }

            if (personalDetail.getNic() == null) {
                personalDetail.setNic("-");
            }

            if (personalDetail.getNumberOfDependance() == null) {
                personalDetail.setNumberOfDependance("-");
            }

            if (personalDetail.getOfficeTelNumber() == null) {
                personalDetail.setOfficeTelNumber("-");
            }

            if (personalDetail.getOwnership() == null) {
                personalDetail.setOwnership("-");
            }

            if (personalDetail.getPassportExpdate() == null) {
                personalDetail.setPassportExpdate("-");
            }

            if (personalDetail.getPassportNumber() == null) {
                personalDetail.setPassportNumber(applicationId);
            }

            if (personalDetail.getPermentAddress1() == null) {
                personalDetail.setPermentAddress1("-");
            }

            if (personalDetail.getPermentAddress2() == null) {
                personalDetail.setPermentAddress2("-");
            }

            if (personalDetail.getPermentAddress3() == null) {
                personalDetail.setPermentAddress3("-");
            }

            if (personalDetail.getPermentCity() == null) {
                personalDetail.setPermentCity("-");
            }

            if (personalDetail.getPostalcode() == null) {
                personalDetail.setPostalcode("-");
            }

            if (personalDetail.getProfessionalQualifications() == null) {
                personalDetail.setProfessionalQualifications("-");
            }

            if (personalDetail.getProvince() == null) {
                personalDetail.setProvince("-");
            }

            if (personalDetail.getRelAddress1() == null) {
                personalDetail.setRelAddress1("-");
            }

            if (personalDetail.getRelAddress2() == null) {
                personalDetail.setRelAddress2("-");
            }

            if (personalDetail.getRelAddress3() == null) {
                personalDetail.setRelAddress3("-");
            }

            if (personalDetail.getRelCity() == null) {
                personalDetail.setRelCity("-");
            }

            if (personalDetail.getRelCompany() == null) {
                personalDetail.setRelCompany("-");
            }

            if (personalDetail.getRelEmail() == null) {
                personalDetail.setRelEmail("-");
            }

            if (personalDetail.getRelMobile() == null) {
                personalDetail.setRelMobile("-");
            }

            if (personalDetail.getRelName() == null) {
                personalDetail.setRelName("-");
            }

            if (personalDetail.getRelOfficeNumber() == null) {
                personalDetail.setRelOfficeNumber("-");
            }

            if (personalDetail.getRelResidencePhone() == null) {
                personalDetail.setRelResidencePhone("-");
            }

            if (personalDetail.getRelationship() == null) {
                personalDetail.setRelationship("-");
            }

            if (personalDetail.getResDistrict() == null) {
                personalDetail.setResDistrict("-");
            }

            if (personalDetail.getResProvince() == null) {
                personalDetail.setResProvince("-");
            }

            if (personalDetail.getResidenceType() == null) {
                personalDetail.setResidenceType("-");
            }

            if (personalDetail.getSmsalertMobileNumber() == null) {
                personalDetail.setSmsalertMobileNumber("-");
            }

            if (personalDetail.getSpouseDateofBirth() == null) {
                personalDetail.setSpouseDateofBirth("-");
            }

            if (personalDetail.getSpouseDesignation() == null) {
                personalDetail.setSpouseDesignation("-");
            }

            if (personalDetail.getSpouseMail() == null) {
                personalDetail.setSpouseMail("-");
            }

            if (personalDetail.getSpouseMonthlyIncome() == null) {
                personalDetail.setSpouseMonthlyIncome("-");
            }

            if (personalDetail.getSpouseName() == null) {
                personalDetail.setSpouseName("-");
            }

            if (personalDetail.getSpouseNameofEmployee() == null) {
                personalDetail.setSpouseNameofEmployee("-");
            }

            if (personalDetail.getSpouseNic() == null) {
                personalDetail.setSpouseNic("-");
            }

            if (personalDetail.getSpousePassportNumber() == null) {
                personalDetail.setSpousePassportNumber("-");
            }

            if (personalDetail.getSpousePhone() == null) {
                personalDetail.setSpousePhone("-");
            }

            if (personalDetail.getSpousecompanyAddress() == null) {
                personalDetail.setSpousecompanyAddress("-");
            }

            if (personalDetail.getTitle() == null) {
                personalDetail.setTitle("-");
            }

            if (personalDetail.getUseCardOnline() == null) {
                personalDetail.setUseCardOnline("-");
            }

            if (personalDetail.getVehicalNo() == null) {
                personalDetail.setVehicalNo("-");
            }

            if (personalDetail.getVehicalType() == null) {
                personalDetail.setVehicalType("-");
            }

            if (personalDetail.getReligion() == null) {
                personalDetail.setReligion("-");
            }
            if (personalDetail.getPlaceOfbirth() == null) {
                personalDetail.setPlaceOfbirth("-");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    //get uploaded documents
    private void getuploadedDocumentsType(String applcationId) throws Exception {
        try {

            documentVerifyManager = new DocumentVerifyManager();
            uploadedDocumentTypeList = documentVerifyManager.getuploadedDocumentsType(applcationId);

        } catch (Exception ex) {
            throw ex;
        }

    }

    //get verification points
    private void getVerificationPoints(String cardTrypeCategory) throws Exception {
        try {

            documentVerifyManager = new DocumentVerifyManager();
            verificationPointsBeanList = documentVerifyManager.getVerificationPoints(cardTrypeCategory);

        } catch (Exception ex) {
            throw ex;
        }

    }

    //get card product list to requested card type
    private void getCardProductsToRequestedCardType(String cardType) throws Exception {
        try {
            confirmationManager = new ApplicationConfirmationManager();
            cardProducts = confirmationManager.getCardProductsToRequestedCardType(cardType);
        } catch (Exception e) {
            throw e;
        }
    }
    //get currency alpha code
    private void getCurrencyAlphaCode(String cCode) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            currencyAlphaCode = checkingmanager.getCurrencyAlphaCode(cCode);
        } catch (Exception e) {
            throw e;
        }
    }

    //get confirmation details
    private void configConfirmationDetails(HttpServletRequest request, String applicationId) throws Exception {
        boolean canUserConfirmApplication = checkingmanager.isCreditAmountWithinTheUserConfirmRange(sessionUser.getUserRole(), Double.parseDouble(cardApplicationList.getcOfficerRecCrditLimt()));
        if (canUserConfirmApplication) {
            request.setAttribute("isConfirmMode", 1);
            this.getRejectReasonList();
            this.getProductionMode();
            this.getStaffCusTemplates(cardApplicationList.getStaffStatus(), personalDetail.getCardCurrency());
            this.getAllCardProductsInCreditCardDomain();

            request.setAttribute("rejectReasons", rejectReasons);
            request.setAttribute("productionModeList", productionModeList);
            request.setAttribute("staffCusList", staffCusList);
            request.setAttribute("products", productList);
        } else {
            request.setAttribute("isConfirmMode", 0);
            if(request.getAttribute("errorMsg")==null){
                request.setAttribute("successMsg", MessageVarList.SUCCESS_OFFICER_REVIEW_BUT_CAN_NOT_CONFIRM);
            }
            
        }

    }

    //get rejection reason list
    private void getRejectReasonList() throws Exception {
        rejectReasons = new ArrayList<RejectReasonBean>();
        try {
            checkingmanager = new ApplicationCheckingManager();
            rejectReasons = checkingmanager.getRejectReasons();
        } catch (Exception e) {
            throw e;
        }
    }

    //get production mode list
    private void getProductionMode() throws Exception {
        productionModeList = new ArrayList<ProductionModeBean>();
        try {
            checkingmanager = new ApplicationCheckingManager();
            productionModeList = checkingmanager.getProductionMode();
        } catch (Exception e) {
            throw e;
        }
    }

    //get customer templates
    private void getStaffCusTemplates(String staffStatus, String currency) throws Exception {
        staffCusList = new ArrayList<CustomerTempBean>();
        try {
            checkingmanager = new ApplicationCheckingManager();
            staffCusList = checkingmanager.getStaffCusTemplates(staffStatus, currency);
        } catch (Exception e) {
            throw e;
        }
    }

    //get card products list in credit card domain
    private void getAllCardProductsInCreditCardDomain() throws Exception {
        productList = new ArrayList<CardProductBean>();
        try {
            checkingmanager = new ApplicationCheckingManager();
            productList = checkingmanager.getCardProductsToRequestedCardType();
        } catch (Exception e) {
            throw e;
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
