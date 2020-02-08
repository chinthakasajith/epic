/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfirmation.bean.RecommendSchemBean;
import com.epic.cms.camm.applicationconfirmation.businesslogic.ApplicationConfirmationManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.DocumentVerifyManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
public class LoadCreditofficerDetailsServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private ApplicationCheckingManager checkingmanager;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private CustomerPersonalBean personalDetail;
    private CardApplicationBean cardApplicationList;
    private List<String> userTaskList;
    private DocumentVerifyManager documentVerifyManager = null;
    private List<DocumentUploadBean> uploadedDocumentTypeList = null;
    private ApplicationConfirmationManager confirmationManager = null;
    private List<CardProductBean> cardProducts = null;
    private String baseCurrency;
    private String Id = "NO";
    private String salery = "N0";
    private String confirmation = "N0";
    private String bill = "N0";
    private String marriage = "N0";
    private String birth = "N0";
    private String url = "/camm/applicationconfirmation/creditofficerview.jsp";

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
                    String pageCode = PageVarList.CREDITOFFICER;
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

                SystemRecomendedCredit systemRecomended = new SystemRecomendedCredit();

                this.getCardApplicationDetails(applicationId);
                this.getDataFromCardApplicationPersonalTable(applicationId);
                this.getuploadedDocumentsType(applicationId);
                this.getBaseCurrency();
                //get Card products list to requested card type
                this.getCardProductsToRequestedCardType(personalDetail.getCardType());

                List<RecommendSchemBean> cardProductList = new ArrayList<RecommendSchemBean>();

                if (cardApplicationList.getCreditScore() != null) {
//                    int creditLimit = systemRecomended.getSystemRecomendedCreditLimit(Integer.parseInt(cardApplicationList.getCreditScore()), personalDetail.getCardType());
//                    request.setAttribute("creditLimit", creditLimit);

                    checkingmanager = new ApplicationCheckingManager();
                    int creditLimitTemp = systemRecomended.getSystemRecomendedCreditLimit(Integer.parseInt(cardApplicationList.getCreditScore()), personalDetail.getCardType());
                    Double creditLimit;
                    String currencyAlphaCode = checkingmanager.getCurrencyAlphaCode(personalDetail.getCardCurrency());

                    request.setAttribute("currencyAlphaCode", currencyAlphaCode);

                    if (baseCurrency.equals(personalDetail.getCardCurrency())) {
                        creditLimit = Double.parseDouble(Integer.toString(creditLimitTemp));
                    } else {

                        String sellingRate = checkingmanager.getSellingRate(personalDetail.getCardCurrency());

//                        creditLimit = creditLimitTemp/Double.parseDouble(sellingRate);
                        creditLimit = Double.parseDouble(new DecimalFormat("#.##").format(creditLimitTemp / Double.parseDouble(sellingRate)));
                    }

//                    MaskFormatter format = new MaskFormatter("#,##,###");
//                    format.setValueContainsLiteralCharacters(false);
//                    String creditLimitFomatted = format.valueToString(creditLimit);
////                    System.out.println(format.valueToString(creditLimit));
                    DecimalFormat df = new DecimalFormat();
                    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                    dfs.setGroupingSeparator(',');

                    df.setDecimalFormatSymbols(dfs);
                    String creditLimitFomatted = df.format((double) creditLimit);

                    request.setAttribute("creditLimit", creditLimit);
                    request.setAttribute("creditLimitFomatted", creditLimitFomatted);

                    cardProductList = systemRecomended.getRecommendedCardTypes(personalDetail.getCardType(), Double.toString(creditLimitTemp));
                    request.setAttribute("cardProductList", cardProductList);
                    request.setAttribute("listsize", cardProductList.size());
                    request.setAttribute("cardProducts", cardProducts);
//                    cardProductList = systemRecomended.getRecommendedCardTypes(personalDetail.getCardType(), Integer.toString(creditLimit));
//                    request.setAttribute("cardProductList", cardProductList);
//                    request.setAttribute("listsize", cardProductList.size());
                }
                for (int i = 0; i < uploadedDocumentTypeList.size(); i++) {
                    if (uploadedDocumentTypeList.get(i).getDocumentType().equals("NIC")
                            || uploadedDocumentTypeList.get(i).getDocumentType().equals("PAS")
                            || uploadedDocumentTypeList.get(i).getDocumentType().equals("DRL")) {
                        Id = "YES";
                    }
                    if (uploadedDocumentTypeList.get(i).getVerificationCategory().equals("SS1")
                            || uploadedDocumentTypeList.get(i).getVerificationCategory().equals("SS2")) {
                        salery = "YES";
                    }
                    if (uploadedDocumentTypeList.get(i).getVerificationCategory().equals("SCL")) {
                        confirmation = "YES";
                    }
                    if (uploadedDocumentTypeList.get(i).getVerificationCategory().equals("UB1")
                            || uploadedDocumentTypeList.get(i).getVerificationCategory().equals("UB2")) {
                        bill = "YES";
                    }
                    if (uploadedDocumentTypeList.get(i).getVerificationCategory().equals("MCT")) {
                        marriage = "YES";
                    }
                    if (uploadedDocumentTypeList.get(i).getVerificationCategory().equals("BCT")) {
                        birth = "YES";
                    }
                }

                DecimalFormat df = new DecimalFormat();
                DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                dfs.setGroupingSeparator(',');

                df.setDecimalFormatSymbols(dfs);
                String requestedCreditLimit = df.format((double) Double.parseDouble(personalDetail.getCreditLimit()));

                request.setAttribute("cardApplicationList", cardApplicationList);
                request.setAttribute("personalDetail", personalDetail);
                request.setAttribute("Id", Id);
                request.setAttribute("salery", salery);
                request.setAttribute("confirmation", confirmation);
                request.setAttribute("bill", bill);
                request.setAttribute("marriage", marriage);
                request.setAttribute("birth", birth);
                request.setAttribute("requestedCreditLimit", requestedCreditLimit);

                rd = request.getRequestDispatcher(url);
                rd.forward(request, response);

            } catch (Exception e) {
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

//            if (personalDetail.getReligion() == null) {
//                personalDetail.setReligion("-");
//            }
        } catch (Exception e) {
            throw e;
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

            if (cardApplicationList.getCreditScore() == null) {
                cardApplicationList.setCreditScore("-");
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

    private void getuploadedDocumentsType(String applcationId) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            uploadedDocumentTypeList = documentVerifyManager.getuploadedDocumentsType(applcationId);

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void getBaseCurrency() throws Exception {

        try {
            checkingmanager = new ApplicationCheckingManager();
            baseCurrency = checkingmanager.getBaseCurrency();
        } catch (Exception e) {
            throw e;
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
