/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ProductionModeBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfirmation.bean.CardBean;
import com.epic.cms.camm.applicationconfirmation.bean.PrimaryAppStatusBean;
import com.epic.cms.camm.applicationconfirmation.bean.PrimaryCardsDetailBean;
import com.epic.cms.camm.applicationconfirmation.bean.RecommendSchemBean;
import com.epic.cms.camm.applicationconfirmation.bean.RejectReasonBean;
import com.epic.cms.camm.applicationconfirmation.businesslogic.ApplicationConfirmationManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
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
public class LoadSupplementaryCreditofficerDetailsAndConfirmServlet extends HttpServlet {

    private RequestDispatcher rd = null;
    HttpSession sessionObj = null;
    private SessionVarList sessionVarlist = null;
    private SystemUserManager systemUserManager = null;
    private ApplicationCheckingManager checkingmanager = null;
    private ApplicationConfirmationManager confirmationManager = null;
    private DocumentVerifyManager documentVerifyManager = null;
    private SupplementaryApplicationBean personalDetail;
    private CardApplicationBean cardApplicationList;
    private CardBean sysRecomendedDetails;
    private SessionUser sessionUser = null;
    private String currencyAlphaCode = null;
    private List<String> userTaskList;
    private List<CardProductBean> cardProduts = null;
    private CardApplicationStatusBean appStatusBean = null;
    private List<RejectReasonBean> rejectReasons;
    private List<ProductionModeBean> productionModeListt;
    private List<CardProductBean> productList;
    private List<PrimaryCardsDetailBean> primaryCardList;
    private PrimaryAppStatusBean primaryAppDetails;
    private List<VerificationPointsBean> verificationPointsBeanList = null;
    private List<DocumentUploadBean> uploadedDocumentTypeList = null;
    private Double sysRecCreditLimit = 0d;
    private String url = "/camm/applicationconfirmation/suplimentaryCreditOfficerReviewAndConfirm.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //call to existing session
            ///////////////////////////////////////////////////////////////////////////////
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
                    //set the page code and task code
                    String pageCode = PageVarList.CREDITOFFICERREVIEWANDCONFIRM;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    //check whethre userrole have an access for this page and task          
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        try {
                            String applicationId = request.getParameter("applicationid");
                            if (applicationId == null) {
                                applicationId = request.getAttribute("applicationid").toString();
                            }
                            // set the application id to session for further usage..
                            sessionVarlist.setVerifyingAppId(applicationId);

                            SystemRecomendedCredit systemRecomended = new SystemRecomendedCredit();

                            this.getCardApplicationDetails(applicationId);
                            this.getDataFromCardApplicationPersonalTable(applicationId);
                            this.getSystemRecomendedDetails(personalDetail.getPrimaryCardNumber(), personalDetail.getCardType(), personalDetail.getPercentageValue(), personalDetail.getCreditLimit());
                            this.getCurrencyAlphaCode(personalDetail.getCardCurrency());
                            this.getCardProductsToRequestedCardType(personalDetail.getCardType());
                            this.loadApplicationStatus(applicationId);
                            // get all uploaded documents details
                            this.getuploadedDocumentsType(applicationId);
                            // get the verification point details,where documents should be displayed
                            this.getVerificationPoints("S");

                            List<RecommendSchemBean> cardProductList = new ArrayList<RecommendSchemBean>();
                            cardProductList = systemRecomended.getRecommendedCardTypes(personalDetail.getCardType(), personalDetail.getCreditLimit());
                            request.setAttribute("cardProductList", cardProductList);
                            request.setAttribute("listsize", cardProductList.size());

                            request.setAttribute("cardApplicationList", cardApplicationList);
                            request.setAttribute("personalDetail", personalDetail);
                            request.setAttribute("sysRecomendedDetails", sysRecomendedDetails);
                            request.setAttribute("currencyAlphaCode", currencyAlphaCode);
                            request.setAttribute("cardProductsList", cardProduts);

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

                            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_CREDIT_OFFICER_REVIEW_COMPLETE)) {
                                this.configConfirmationDetails(request, applicationId);
                            } else {
                                request.setAttribute("isConfirmMode", 0);
                            }
                            rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                        } catch (Exception e) {
                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            rd = getServletContext().getRequestDispatcher(url);
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
            } catch (NullPointerException e) {
                //throw session null exception
                throw new SesssionExpException();
            }
        }//catch session exception
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

    private void getCardApplicationDetails(String applicationId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            cardApplicationList = checkingmanager.getCardInfomationDetails(applicationId);
        } catch (Exception e) {
            throw e;
        }
    }

    private void getDataFromCardApplicationPersonalTable(String applicationId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            personalDetail = checkingmanager.getSupplementaryPersonalDetails(applicationId);

            if (personalDetail.getAddress1() == null) {
                personalDetail.setAddress1("-");
            }

            if (personalDetail.getAddress2() == null) {
                personalDetail.setAddress2("-");
            }

            if (personalDetail.getAddress3() == null) {
                personalDetail.setAddress3("-");
            }

            if (personalDetail.getAdressSameAsPrimary() == null) {
                personalDetail.setAdressSameAsPrimary("-");
            }

            if (personalDetail.getBillDistrict() == null) {
                personalDetail.setBillDistrict("-");
            }

            if (personalDetail.getBillProvince() == null) {
                personalDetail.setBillProvince("-");
            }

            if (personalDetail.getBillingAdress1() == null) {
                personalDetail.setBillingAdress1("-");
            }

            if (personalDetail.getBillingAdress2() == null) {
                personalDetail.setBillingAdress2("-");
            }

            if (personalDetail.getBillingAdress3() == null) {
                personalDetail.setBillingAdress3("-");
            }

            if (personalDetail.getBillingCity() == null) {
                personalDetail.setBillingCity("-");
            }

            if (personalDetail.getBirthday() == null) {
                personalDetail.setBirthday("-");
            }

            if (personalDetail.getCardProduct() == null) {
                personalDetail.setCardProduct("-");
            }

            if (personalDetail.getCardType() == null) {
                personalDetail.setCardType("-");
            }

            if (personalDetail.getCity() == null) {
                personalDetail.setCity("-");
            }
            if (personalDetail.getCityDes() == null) {
                personalDetail.setCityDes("-");
            }

//            if(personalDetail.getCreditLimit() == null){
//                personalDetail.setCreditLimit("-");
//            }
//            
            if (personalDetail.getEmployementType() == null) {
                personalDetail.setEmployementType("-");
            }

            if (personalDetail.getFirstName() == null) {
                personalDetail.setFirstName("-");
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

            if (personalDetail.getLastName() == null) {
                personalDetail.setLastName("-");
            }

            if (personalDetail.getMiddleName() == null) {
                personalDetail.setMiddleName("-");
            }

            if (personalDetail.getMobileNumber() == null) {
                personalDetail.setMobileNumber("-");
            }

            if (personalDetail.getNameOncard() == null) {
                personalDetail.setNameOncard("-");
            }

            if (personalDetail.getNameWithinitials() == null) {
                personalDetail.setNameWithinitials("-");
            }

            if (personalDetail.getNationality() == null) {
                personalDetail.setNationality("-");
            }

            if (personalDetail.getNic() == null) {
                personalDetail.setNic("-");
            }

            if (personalDetail.getOccupation() == null) {
                personalDetail.setOccupation("-");
            }
            if (personalDetail.getOccupationTypeDes() == null) {
                personalDetail.setOccupationTypeDes("-");
            }

            if (personalDetail.getPassportExpdate() == null) {
                personalDetail.setPassportExpdate("-");
            }

            if (personalDetail.getPassportNumber() == null) {
                personalDetail.setPassportNumber("-");
            }

            if (personalDetail.getPostalcode() == null) {
                personalDetail.setPostalcode("-");
            }

            if (personalDetail.getPrimaryCardApplicationId() == null) {
                personalDetail.setPrimaryCardApplicationId("-");
            }

            if (personalDetail.getPrimaryCardNumber() == null) {
                personalDetail.setPrimaryCardNumber("-");
            }

            if (personalDetail.getPrimaryCardType() == null) {
                personalDetail.setPrimaryCardType("-");
            }

            if (personalDetail.getPrimaryId() == null) {
                personalDetail.setPrimaryId("-");
            }

            if (personalDetail.getRelationShip() == null) {
                personalDetail.setRelationShip("-");
            }

            if (personalDetail.getResDistrict() == null) {
                personalDetail.setResDistrict("-");
            }

            if (personalDetail.getResProvince() == null) {
                personalDetail.setResProvince("-");
            }

            if (personalDetail.getStatementStatus() == null) {
                personalDetail.setStatementStatus("-");
            }

            if (personalDetail.getTitle() == null) {
                personalDetail.setTitle("-");
            }
            if (personalDetail.getPercentageValue() != null && personalDetail.getCreditLimit() == null) {
                sysRecCreditLimit = this.getSystemRecCreditLimit(personalDetail.getPercentageValue(), personalDetail.getPrimaryCardApplicationId(), personalDetail.getApplicationId(), personalDetail.getPrimaryCardNumber());
                //set user requested credit limit on bean
                personalDetail.setCreditLimit(String.valueOf(sysRecCreditLimit));
            }

        } catch (Exception e) {
            throw e;
        }
    }

    private void getSystemRecomendedDetails(String primaryCardNo, String requestedCardType, String requestePerValue, String requestedCreditLimit) throws Exception {
        try {
            if (primaryCardNo != null) {
                checkingmanager = new ApplicationCheckingManager();
                sysRecomendedDetails = checkingmanager.getSysRecomendedDetails(primaryCardNo);
            } else {
                sysRecomendedDetails.setCardtype(requestedCardType);
            }
            sysRecomendedDetails.setCreditLimit(requestedCreditLimit);
        } catch (Exception e) {
            throw e;
        }
    }

    private void getCurrencyAlphaCode(String cCode) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            currencyAlphaCode = checkingmanager.getCurrencyAlphaCode(cCode);
        } catch (Exception e) {
            throw e;
        }
    }

    //get card product list to requested card type
    private void getCardProductsToRequestedCardType(String cardType) throws Exception {
        try {
            confirmationManager = new ApplicationConfirmationManager();
            cardProduts = confirmationManager.getCardProductsToRequestedCardType(cardType);
        } catch (Exception e) {
            throw e;
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

    private void getVerificationPoints(String cardTrypeCategory) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            verificationPointsBeanList = documentVerifyManager.getVerificationPoints(cardTrypeCategory);

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void getuploadedDocumentsType(String applcationId) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            uploadedDocumentTypeList = documentVerifyManager.getuploadedDocumentsType(applcationId);

        } catch (Exception ex) {
            throw ex;
        }

    }

    //get confirmation details
    private void configConfirmationDetails(HttpServletRequest request, String applicationId) throws Exception {
        boolean canUserConfirmApplication = checkingmanager.isCreditAmountWithinTheUserConfirmRange(sessionUser.getUserRole(), Double.parseDouble(cardApplicationList.getcOfficerRecCrditLimt()));
        if (canUserConfirmApplication) {
            request.setAttribute("isConfirmMode", 1);
            this.getRejectReasonList();
            this.getProductionMode();
            this.getAllCardProductsInCreditCardDomain();

            //check whether availability of the main card
            if (personalDetail.getPrimaryCardNumber() == null || personalDetail.getPrimaryCardNumber().equals("")) {
                request.setAttribute("primaryCard", "0");
            } else {
                this.getPrimaryCardHoldersCards(personalDetail.getPrimaryCardNumber());
                request.setAttribute("primaryCard", "1");
                request.setAttribute("primaryCardList", primaryCardList);
            }
            this.getPrimaryApplicationStatusDetails(personalDetail.getPrimaryCardApplicationId());
            request.setAttribute("primaryAppDetails", primaryAppDetails);

            request.setAttribute("rejectReasons", rejectReasons);
            request.setAttribute("productionModeListt", productionModeListt);
            request.setAttribute("products", productList);

        } else {
            request.setAttribute("isConfirmMode", 0);
            request.setAttribute("successMsg", MessageVarList.SUCCESS_OFFICER_REVIEW_BUT_CAN_NOT_CONFIRM);
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
        productionModeListt = new ArrayList<ProductionModeBean>();
        try {
            checkingmanager = new ApplicationCheckingManager();
            productionModeListt = checkingmanager.getProductionMode();
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

    private void getPrimaryCardHoldersCards(String primaryCardNumber) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            primaryCardList = checkingmanager.getPrimaryCardHoldersCards(primaryCardNumber);
        } catch (Exception e) {
            throw e;
        }
    }

    private void getPrimaryApplicationStatusDetails(String primaryAppId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            primaryAppDetails = checkingmanager.getPrimaryApplicationStatusDetails(primaryAppId);
        } catch (Exception e) {
            throw e;
        }
    }

    //get system recommended credit limit 
    private Double getSystemRecCreditLimit(String perValue, String primAppId, String supAppId, String primCardNum) throws Exception {
        Double sysRecCreditLimit = 0.0;
        try {
            confirmationManager = new ApplicationConfirmationManager();
            sysRecCreditLimit = confirmationManager.getSystemRecCreditLimit(perValue, primAppId, supAppId, primCardNum);
        } catch (Exception ex) {
            throw ex;
        }
        return sysRecCreditLimit;
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
