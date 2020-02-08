/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.camm.applicationproccessing.documentverify.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedApplicantDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.PreviousApplicationDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerificationPointsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerifyApplicantDetailBean;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.DocumentVerifyManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author prageeth_s
 */
public class LoadCorporateVerifyFormServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private VerifyApplicantDetailBean verifyCusDetailsBean;
    private CheckedApplicantDetailsBean bankapplicationVerifyBean = null;
    private DocumentVerifyManager documentVerifyManager = null;
    private List<PreviousApplicationDetailsBean> previousDetailsBean = null;
    private List<VerificationPointsBean> verificationPointsBeanList = null;
    private List<DocumentUploadBean> uploadedDocumentTypeList = null;
    private CheckedApplicantDetailsBean previousCheckedBean = null;
    private String url = "/camm/documentverification/corporatedocumentviewhome.jsp";

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

            String applicationId = request.getParameter("applicationid");
            String identyficationNo = request.getParameter("identificationNo");

            // set the application id to session for further usage..
            sessionVarlist.setVerifyingAppId(applicationId);

            // get all previously checked details
            this.getAllPreviousCheckedDetails(applicationId);

            // get the verification point details,where documents should be displayed
            this.getVerificationPoints("C");

            // get all uploaded documents details
            this.getuploadedDocumentsType(applicationId);

            //get all necessary details about applicant
            this.getAllDetailsCustomer(applicationId,"C");

            //get all details about the applicant by his identification number
            this.getPreviousCustomerDetails(identyficationNo);

            // get the bank reccomended document details to verify
            this.getBankApplicationVerifyDetails("C");
            sessionVarlist.setBankVerifyBean(bankapplicationVerifyBean);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

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

            request.setAttribute("previousCheckedBean", previousCheckedBean);

            // set all the points details to the session
            sessionVarlist.setVerificationPointsBeanList(verificationPointsBeanList);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

            //set the initial selected tab
            request.setAttribute("selectedtab", "0");
            rd = getServletContext().getRequestDispatcher(url);

        }//catch session exception
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

        } catch (Exception ex) {
            request.setAttribute("selectedtab", "0");
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_DOC_VERIFY);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void getAllDetailsCustomer(String applicationId,String applicationCode) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            verifyCusDetailsBean = documentVerifyManager.getAllVerifyCustomerDetails(applicationId,applicationCode);
            sessionVarlist.setVerifyCustomerBean(verifyCusDetailsBean);

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void getAllPreviousCheckedDetails(String applicationId) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            previousCheckedBean = documentVerifyManager.getAllPreviousCheckedDetails(applicationId);

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void getPreviousCustomerDetails(String identificationNo) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            previousDetailsBean = documentVerifyManager.getPreviousCustomerDetails(identificationNo);
            sessionVarlist.setPreviousDetailsBeanList(previousDetailsBean);

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

    private void getBankApplicationVerifyDetails(String cardType) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            bankapplicationVerifyBean = documentVerifyManager.getBankApplicationVerifyDetails(cardType);
        } catch (Exception ex) {
            throw ex;
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
