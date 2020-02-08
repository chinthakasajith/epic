/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfirmation.bean.CardBean;
import com.epic.cms.camm.applicationconfirmation.bean.RecommendSchemBean;
import com.epic.cms.camm.applicationconfirmation.businesslogic.ApplicationConfirmationManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
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
public class LoadSupplementaryCreditofficerDetailsServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private ApplicationCheckingManager checkingmanager;
    private DocumentVerifyManager documentVerifyManager = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private SupplementaryApplicationBean personalDetail;
    private CardApplicationBean cardApplicationList;
    private CardBean sysRecomendedDetails;
    private List<String> userTaskList;
    private String currencyAlphaCode = null;
    private ApplicationConfirmationManager confirmationManager = null;
    private List<CardProductBean> cardProduts = null;
    private List<DocumentUploadBean> uploadedDocumentTypeList = null;
    private List<VerificationPointsBean> verificationPointsBeanList = null;
    private Double sysRecCreditLimit = 0d;
    private String url = "/camm/applicationconfirmation/supplementarycreditofficerview.jsp";

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
                    String pageCode = PageVarList.CREDITOFFICER;
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
                            this.getSystemRecomendedDetails(personalDetail.getPrimaryCardNumber(),personalDetail.getCardType(),personalDetail.getPercentageValue(),personalDetail.getCreditLimit());
                            this.getCurrencyAlphaCode(personalDetail.getCardCurrency());
                            this.getCardProductsToRequestedCardType(personalDetail.getCardType());
                            // get all uploaded documents details
                            this.getuploadedDocumentsType(applicationId);
                            // get the verification point details,where documents should be displayed
                            this.getVerificationPoints("S");

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

                            List<RecommendSchemBean> cardProductList = new ArrayList<RecommendSchemBean>();
                            cardProductList = systemRecomended.getRecommendedCardTypes(personalDetail.getCardType(), personalDetail.getCreditLimit());
                            request.setAttribute("cardProductList", cardProductList);
                            request.setAttribute("listsize", cardProductList.size());

                            request.setAttribute("cardApplicationList", cardApplicationList);
                            request.setAttribute("personalDetail", personalDetail);
                            request.setAttribute("sysRecomendedDetails", sysRecomendedDetails);
                            request.setAttribute("currencyAlphaCode", currencyAlphaCode);
                            request.setAttribute("cardProductsList", cardProduts);
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

            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }
            //////////////////////////////////////////////////////////////////////////

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

//    private void getDataFromCardApplicationPersonalTable(String applicationId) throws Exception {
//        try {
//            checkingmanager = new ApplicationCheckingManager();
//            personalDetail = checkingmanager.getPersonalDetails(applicationId);
//        } catch (Exception e) {
//            throw e;
//        }
//    }
    private void getDataFromCardApplicationPersonalTable(String applicationId) throws Exception {

        try {
            checkingmanager = new ApplicationCheckingManager();
            personalDetail = checkingmanager.getSupplementaryPersonalDetails(applicationId);

            if (personalDetail.getCreditLimit() == null && personalDetail.getPercentageValue() != null) {
                sysRecCreditLimit = this.getSystemRecCreditLimit(personalDetail.getPercentageValue(), personalDetail.getPrimaryCardApplicationId(), personalDetail.getApplicationId(), personalDetail.getPrimaryCardNumber());
                //set user requested credit limit on bean
                personalDetail.setCreditLimit(String.valueOf(sysRecCreditLimit));
            }
        } catch (Exception e) {
            throw e;
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

    private void getSystemRecomendedDetails(String primaryCardNo,String requestedCardType,String requestePerValue,String requestedCreditLimit) throws Exception {
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

    //get currency alpha code 
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

    //get uploaded document type list
    private void getuploadedDocumentsType(String applcationId) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            uploadedDocumentTypeList = documentVerifyManager.getuploadedDocumentsType(applcationId);

        } catch (Exception ex) {
            throw ex;
        }

    }

    //get verification point list
    private void getVerificationPoints(String cardTrypeCategory) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            verificationPointsBeanList = documentVerifyManager.getVerificationPoints(cardTrypeCategory);

        } catch (Exception ex) {
            throw ex;
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
