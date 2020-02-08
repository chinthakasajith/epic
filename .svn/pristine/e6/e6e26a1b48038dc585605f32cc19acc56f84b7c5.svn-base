/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedSupplementaryDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.DocumentVerifyManager;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.SupplementaryVerifyManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
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
public class OnholdSupplementaryDetailsServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private DocumentVerifyManager documentVerifyManager = null;
    private SupplementaryVerifyManager supplementaryVerifyManager = null;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private boolean success = false;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private CheckedSupplementaryDetailsBean checkedSupplementaryBean;
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



            try {
                //set page code and task codes
                String pageCode = PageVarList.DOCUMENTVERIFYHOME;
                String taskCode = TaskVarList.REJECT;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                    this.setUserInputToBean(request);
                    request.setAttribute("checkedApplicantBean", checkedSupplementaryBean);


                    //check for the record existance for this application id..if exist then update the details
                    if (this.isExistCardApplication(checkedSupplementaryBean.getApplicationId())) {

                        if (this.isRejectedCardApplication(checkedSupplementaryBean.getApplicationId())) {

                            request.setAttribute("selectedtab", "2");
                            request.setAttribute("errorMsg3", MessageVarList.ERROR_ALREADY_REJECT);
                            rd = getServletContext().getRequestDispatcher(url);



                        } else {
                            this.setAudittraceValue(request);
                            this.updateVerifyDetailsOfApplication(checkedSupplementaryBean, "VONH", systemAuditBean);


                            request.setAttribute("successMsg", MessageVarList.SUCCES_DOC_ONHOLD);
                            rd = request.getRequestDispatcher("/LoadDocumentVerifySearchServlet");
                        }
                    } else {

                        //check for the record existance for this application id..if doesn't exist then insert the details
                        this.setAudittraceValue(request);
                        this.insertVerifyDetailsOfApplication(checkedSupplementaryBean, "VONH", systemAuditBean);

                        request.setAttribute("selectedtab", "2");
                        request.setAttribute("successMsg3", MessageVarList.SUCCES_DOC_ONHOLD);
                        rd = request.getRequestDispatcher(url);
                    }

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
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);

        } catch (SQLException ex) {
            request.setAttribute("selectedtab", "2");
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg3", checkedSupplementaryBean.getApplicationId() + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("selectedtab", "2");
            request.setAttribute("errorMsg3", MessageVarList.ERROR_REJECT_DOC);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    public void setUserInputToBean(HttpServletRequest request) throws Exception {


        String docBirth = null, mainVerification = null, supplementaryVerification = null, docMarriage = null,docIdentification=null;

        try {



            String appliactionId = request.getParameter("appliactionid").trim();
            if (request.getParameter("checkmain") != null) {
                mainVerification = "YES";
            } else {
                mainVerification = "NO";
            }
            if (request.getParameter("checksupplementary") != null) {
                supplementaryVerification = "YES";
            } else {
                supplementaryVerification = "NO";
            }
            if (request.getParameter("checkmarriagecopy") != null) {
                docMarriage = "YES";
            } else {
                docMarriage = "NO";
            }
            if (request.getParameter("checkbirthcertifycopy") != null) {
                docBirth = "YES";
            } else {
                docBirth = "NO";
            }
            
            if (request.getParameter("checkidentificationcopy") != null) {
                docIdentification = "YES";
            } else {
                docIdentification = "NO";
            }


            checkedSupplementaryBean = new CheckedSupplementaryDetailsBean();

            checkedSupplementaryBean.setApplicationId(appliactionId);
            checkedSupplementaryBean.setMainVerification(mainVerification);
            checkedSupplementaryBean.setSupplentaryVerification(supplementaryVerification);
            checkedSupplementaryBean.setDocMarriageCertificate(docMarriage);
            checkedSupplementaryBean.setDocBirthCertificate(docBirth);
            checkedSupplementaryBean.setLastUpdatedUser(sessionUser.getUserName());
            checkedSupplementaryBean.setLastUpdatedTime(new Date());
            checkedSupplementaryBean.setDocIdentification(docIdentification);
            



        } catch (Exception e) {
            throw e;
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

    private boolean isExistCardApplication(String applicationId) throws Exception {
        boolean flag = false;
        try {

            documentVerifyManager = new DocumentVerifyManager();
            flag = documentVerifyManager.isExistCardApplication(applicationId);
        } catch (Exception ex) {
            throw ex;
        }
        return flag;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Onhold credit card application, application id: " + checkedSupplementaryBean.getApplicationId() + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.DOCUMENTVERIFY);
            systemAuditBean.setPageCode(PageVarList.DOCUMENTVERIFYHOME);
            systemAuditBean.setTaskCode(TaskVarList.ONHOLD);
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

    private boolean insertVerifyDetailsOfApplication(CheckedSupplementaryDetailsBean checkedApplicantBean, String applicationStatus, SystemAuditBean systemAuditBean) throws Exception {

        try {

            supplementaryVerifyManager = new SupplementaryVerifyManager();
            success = supplementaryVerifyManager.insertVerifyDetailsOfApplication(checkedApplicantBean, applicationStatus, systemAuditBean);

        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private boolean updateVerifyDetailsOfApplication(CheckedSupplementaryDetailsBean checkedApplicantBean, String applicationStatus, SystemAuditBean systemAuditBean) throws Exception {

        try {

            supplementaryVerifyManager = new SupplementaryVerifyManager();
            success = supplementaryVerifyManager.updateVerifyDetailsOfApplication(checkedApplicantBean, applicationStatus, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private boolean isRejectedCardApplication(String applicationId) throws Exception {

        boolean flag = false;
        try {

            documentVerifyManager = new DocumentVerifyManager();
            flag = documentVerifyManager.isRejectedCardApplication(applicationId);
        } catch (Exception ex) {
            throw ex;
        }
        return flag;
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
