/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedApplicantDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedSupplementaryDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.PreviousApplicationDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.SupplementaryDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerificationPointsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerifyApplicantDetailBean;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.DocumentVerifyManager;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.SupplementaryVerifyManager;
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
 * @author chanuka
 */
public class LoadSupplementaryUpdateVerifyFormServlet extends HttpServlet {
    
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private DocumentVerifyManager documentVerifyManager = null;
    private SupplementaryDetailsBean supplementaryBean = null;
    private SupplementaryVerifyManager supplementaryManager = null;
    private List<PreviousApplicationDetailsBean> previousDetailsBean = null;
    private VerifyApplicantDetailBean mainDetailsBean = null;
    private List<DocumentUploadBean> uploadedDocumentTypeList = null;
    private DocumentUploadBean mctDocumentBean = null;
    private DocumentUploadBean bctDocumentBean = null;
    private CheckedSupplementaryDetailsBean previousCheckedBean = null;


    private CheckedApplicantDetailsBean bankapplicationVerifyBean = null;
    private List<VerificationPointsBean> verificationPointsBeanList = null;
    
    private String url = "/camm/documentverification/supplementaryviewhome.jsp";
    
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
            
            this.getuploadedDocumentsType(applicationId);
            
            for (DocumentUploadBean uploadedDocumentTypeList1 : uploadedDocumentTypeList) {
                if (uploadedDocumentTypeList1.getDocumentType().equals("MCT_SUP")) {
                    mctDocumentBean = uploadedDocumentTypeList1;                    
                }
                if (uploadedDocumentTypeList1.getDocumentType().equals("BCT_SUP")) {
                    bctDocumentBean = uploadedDocumentTypeList1;                    
                }
            }
            sessionVarlist.setMctDocumentBean(mctDocumentBean);
            sessionVarlist.setBctDocumentBean(bctDocumentBean);


            // get all previously checked details
            this.getAllPreviousCheckedDetails(applicationId);
            this.getAllSupplementaryDetails(applicationId);
            this.getAllMainDetails(applicationId);
            this.getVerificationPoints("S");
            //get all details about the applicant by his identification number
            this.getPreviousCustomerDetails(identyficationNo);
            
            
            for (VerificationPointsBean verificationPointsBeanList1 : verificationPointsBeanList) {
                verificationPointsBeanList1.setDocumentExist("NO");
                for (DocumentUploadBean uploadedDocumentTypeList1 : uploadedDocumentTypeList) {
                    if (verificationPointsBeanList1.getDocumentType() != null) {
                        if (verificationPointsBeanList1.getDocumentType().equals(uploadedDocumentTypeList1.getDocumentType())) {
                            verificationPointsBeanList1.setDocumentExist("YES");
                            verificationPointsBeanList1.setVerificationCategory(uploadedDocumentTypeList1.getVerificationCategory());
                            verificationPointsBeanList1.setFileName(uploadedDocumentTypeList1.getFileName());
                            break;
                        }
                    }
                }
            }
            // set all the points details to the session
            sessionVarlist.setVerificationPointsBeanList(verificationPointsBeanList);
            
            // get the bank reccomended document details to verify
            this.getBankApplicationVerifyDetails("S"); 
            sessionVarlist.setBankVerifyBean(bankapplicationVerifyBean);
            
            request.setAttribute("previousCheckedBean", previousCheckedBean);
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
    
    private void getPreviousCustomerDetails(String identificationNo) throws Exception {
        
        try {
            
            documentVerifyManager = new DocumentVerifyManager();
            previousDetailsBean = documentVerifyManager.getPreviousCustomerDetails(identificationNo);
            sessionVarlist.setPreviousDetailsBeanList(previousDetailsBean);
            
        } catch (Exception ex) {
            throw ex;
        }
        
    }
    
    private void getAllSupplementaryDetails(String applicationId) throws Exception {
        
        try {
            
            supplementaryManager = new SupplementaryVerifyManager();
            supplementaryBean = supplementaryManager.getAllSupplementaryDetails(applicationId);
            sessionVarlist.setSupplementaryDetailsBean(supplementaryBean);
            
        } catch (Exception ex) {
            throw ex;
        }
        
    }
    
    private void getAllMainDetails(String applicationId) throws Exception {
        
        try {
            
            supplementaryManager = new SupplementaryVerifyManager();
            mainDetailsBean = supplementaryManager.getAllMainDetails(applicationId);
            sessionVarlist.setMainDetailsBean(mainDetailsBean);
            
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
    
    private void getAllPreviousCheckedDetails(String applicationId) throws Exception {
        
        try {
            
            supplementaryManager = new SupplementaryVerifyManager();
            previousCheckedBean = supplementaryManager.getAllPreviousCheckedDetails(applicationId);
            
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
    
    private void getBankApplicationVerifyDetails(String cardType) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            bankapplicationVerifyBean = documentVerifyManager.getBankApplicationVerifyDetails(cardType);
        } catch (Exception ex) {
            throw ex;
        }

    }
    
    
        public CheckedApplicantDetailsBean getBankapplicationVerifyBean() {
        return bankapplicationVerifyBean;
    }

    public void setBankapplicationVerifyBean(CheckedApplicantDetailsBean bankapplicationVerifyBean) {
        this.bankapplicationVerifyBean = bankapplicationVerifyBean;
    }

    public List<VerificationPointsBean> getVerificationPointsBeanList() {
        return verificationPointsBeanList;
    }

    public void setVerificationPointsBeanList(List<VerificationPointsBean> verificationPointsBeanList) {
        this.verificationPointsBeanList = verificationPointsBeanList;
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
