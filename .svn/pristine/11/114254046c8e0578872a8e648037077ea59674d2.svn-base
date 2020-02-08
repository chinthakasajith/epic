/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AreaBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.DebitCheckBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.DebitUploadDocsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.DebitVerifyBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.SignatureUploadedBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerifyAccountDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.DocumentVerifyManager;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.SupplementaryVerifyManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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
public class LoadUpdateDebitSecondaryVerifyFormServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CaptureDataManager areaManager;
    private List<AreaBean> areaList;
    private SessionVarList sessionVarlist;
    private SupplementaryVerifyManager supplementaryManager = null;
    private DocumentVerifyManager documentVerifyManager = null;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private DebitCheckBean debitBean = null;
    private ApplicationAssignManager appAssignManager = null;
    private HashMap<String, String> identificationList = null;
    private DebitVerifyBean debitVerifyBean = null;
    private List<DocumentUploadBean> uploadedDocumentTypeList = null;
    private List<VerifyAccountDetailsBean> accoutBeanLst = null;
    private SignatureUploadedBean signBean;
    private DebitUploadDocsBean debitUploadDocsBean;
    private String url = "/camm/documentverification/debitsecondaryviewhome.jsp";

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

            request.setAttribute("selectedtab", "0");

            String applicationId = request.getParameter("applicationid");
//            String identificationNo = request.getParameter("identificationNo");

            this.getAllArealist();
            this.getAllIdentificationType();
            this.getuploadedDocumentsType(applicationId);
            this.getAllAccountDetails(applicationId);
            this.getAllDebitVerifyDetails(applicationId);
            this.getuploadedSignatures(applicationId);


            debitUploadDocsBean = new DebitUploadDocsBean();
            debitUploadDocsBean.setApplicationId(applicationId);
            debitUploadDocsBean.setIsPrimaryNicUploaded("NO");
            debitUploadDocsBean.setIsJointNicUpload("NO");


            for (int i = 0; i < uploadedDocumentTypeList.size(); i++) {

                if (uploadedDocumentTypeList.get(i).getDocumentType().equals("NIC")) {

                    if (uploadedDocumentTypeList.get(i).getFileName().length() == 28) { //primary nic

                        debitUploadDocsBean.setIsPrimaryNicUploaded("YES");
                        debitUploadDocsBean.setPrimaryNicFileName(uploadedDocumentTypeList.get(i).getFileName());

                    }
                    if (uploadedDocumentTypeList.get(i).getFileName().length() == 30) { //joint nic

                        debitUploadDocsBean.setIsJointNicUpload("YES");
                        debitUploadDocsBean.setJoinNicFileName(uploadedDocumentTypeList.get(i).getFileName());

                    }


                }
            }

            if (signBean.getPrimarySignature() != null) {

                debitUploadDocsBean.setIsPrimarySignatureUpload("YES");
                debitUploadDocsBean.setPrimarySignFileName(signBean.getPrimarySignature());

            } else {

                debitUploadDocsBean.setIsPrimarySignatureUpload("NO");
            }

            if (signBean.getJointSignature() != null) {

                debitUploadDocsBean.setIsJointSignatureUpload("YES");
                debitUploadDocsBean.setJoinSignFileName(signBean.getJointSignature());

            } else {

                debitUploadDocsBean.setIsJointSignatureUpload("NO");
            }


            sessionVarlist.setDebitUploadDocsBean(debitUploadDocsBean);

            sessionVarlist.setAreaList(areaList);
            sessionVarlist.setIdentityTypeList(identificationList);
            request.setAttribute("verifyBean", debitVerifyBean);

            DocumentVerifyManager obj = new DocumentVerifyManager();
            debitBean = obj.getDebitDetailBean(applicationId);

            sessionVarlist.setDebitDetailsBean(debitBean);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

            rd = getServletContext().getRequestDispatcher(url);








        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } catch (Exception ex) {
            request.setAttribute("selectedtab", "0");
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_ASSIGN_APP);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void getAllArealist() throws Exception {
        try {

            areaManager = new CaptureDataManager();
            areaList = areaManager.getAllArealist();
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

    private void getAllIdentificationType() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            identificationList = new HashMap<String, String>();
            identificationList = appAssignManager.getAllIdentificationType();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllAccountDetails(String applicationId) throws Exception {

        try {

            supplementaryManager = new SupplementaryVerifyManager();
            accoutBeanLst = supplementaryManager.getAllAccountDetails(applicationId);
            sessionVarlist.setVerifyAccoutBeanLst(accoutBeanLst);

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void getAllDebitVerifyDetails(String applicationId) throws Exception {

        try {

            supplementaryManager = new SupplementaryVerifyManager();
            debitVerifyBean = supplementaryManager.getAllDebitVerifyDetails(applicationId);

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void getuploadedSignatures(String applcationId) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            signBean = documentVerifyManager.getuploadedSignatures(applcationId);

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
