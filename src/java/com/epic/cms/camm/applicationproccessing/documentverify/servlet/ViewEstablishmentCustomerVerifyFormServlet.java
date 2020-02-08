/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedEstDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CorporateVerifyBean;
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
import com.sun.org.apache.bcel.internal.generic.AALOAD;
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
 * @author nalin
 */
public class ViewEstablishmentCustomerVerifyFormServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    HttpSession sessionObj;
    private SystemAuditBean systemAuditBean = null;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private String cardCategory = null;
    private List<String> userTaskList;
    private DocumentVerifyManager verifyManager = null;
    private CorporateVerifyBean verifyBean = null;
    private CheckedEstDetailsBean previousCheckedBean = null;
    private List<VerificationPointsBean> corporateVerificationPointsList = null;
    private List<DocumentUploadBean> uploadedDocumentTypeList = null;
    private CheckedEstDetailsBean corporateEstVerifyBean = null;
    private String url = "/camm/documentverification/corporateetsablishmentverifyview.jsp";

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

            try {
                //set page code and task codes
                String pageCode = PageVarList.CORPORATE_CARD_VERIFICATION;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {



                    String corporateAppId = request.getParameter("applicationid");
                    String identyficationNo = request.getParameter("identificationNo");

                    sessionVarlist.setCorporateAppId(corporateAppId);

                    // get Data from CARDESTAPPLICATIONDETAILS
                    this.getAllEstablishmentDetails(corporateAppId);

                    // get all previously checked details
                    this.getPreviousEstCheckedDetails(corporateAppId);

                    // get the verification point details,where documents should be displayed
                    this.getCorporateVerificationPoints();

                    // get all uploaded documents details
                    this.getuploadedDocumentsType(corporateAppId);

                    // get the bank reccomended document details to verify
                    this.getCorporateBankApplicationVerifyDetails();


                    // set the details about uploaded document file details (filepath,name,verification category..)
                    for (int i = 0; i < corporateVerificationPointsList.size(); i++) {
                        corporateVerificationPointsList.get(i).setDocumentExist("NO");
                        for (int j = 0; j < uploadedDocumentTypeList.size(); j++) {
                            if (corporateVerificationPointsList.get(i).getDocumentType() != null) {
                                if (corporateVerificationPointsList.get(i).getDocumentType().equals(uploadedDocumentTypeList.get(j).getDocumentType())) {
                                    corporateVerificationPointsList.get(i).setDocumentExist("YES");
                                    corporateVerificationPointsList.get(i).setVerificationCategory(uploadedDocumentTypeList.get(j).getVerificationCategory());
                                    corporateVerificationPointsList.get(i).setFileName(uploadedDocumentTypeList.get(j).getFileName());
                                    break;
                                }
                            }
                        }
                    }

                    request.setAttribute("previousCheckedBean", previousCheckedBean);

                    // set all the points details to the session
                    sessionVarlist.setCorporateVerificationPointsList(corporateVerificationPointsList);
                    sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                    request.setAttribute("selectedtab", "0");
                    rd = getServletContext().getRequestDispatcher(url);


                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }


            } catch (AccessDeniedException adex) {
                throw adex;

            }

        }//catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {

            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);


        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_ASSIGN_APPLICATION);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    ///////////////////////////
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

    private void getAllEstablishmentDetails(String corporateAppId) throws Exception {
        try {

            verifyBean = new CorporateVerifyBean();
            verifyManager = new DocumentVerifyManager();
            verifyBean = verifyManager.getAllEstablishmentDetails(corporateAppId);

            sessionVarlist.setVerifyBean(verifyBean);


        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCorporateVerificationPoints() throws Exception {

        try {

            verifyManager = new DocumentVerifyManager();
            corporateVerificationPointsList = verifyManager.getCorporateVerificationPoints("C");

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void getuploadedDocumentsType(String applcationId) throws Exception {

        try {

            verifyManager = new DocumentVerifyManager();
            uploadedDocumentTypeList = verifyManager.getuploadedDocumentsType(applcationId);

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void getPreviousEstCheckedDetails(String applicationId) throws Exception {

        try {

            verifyManager = new DocumentVerifyManager();
            previousCheckedBean = new CheckedEstDetailsBean();
            previousCheckedBean = verifyManager.getPreviousEstCheckedDetails(applicationId);

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void getCorporateBankApplicationVerifyDetails() throws Exception {

        try {

            verifyManager = new DocumentVerifyManager();
            corporateEstVerifyBean = new CheckedEstDetailsBean();
            corporateEstVerifyBean = verifyManager.getCorporateBankApplicationVerifyDetails();
            sessionVarlist.setEstBankVerifyBean(corporateEstVerifyBean);
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
