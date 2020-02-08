/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.camm.applicationproccessing.documentverify.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedApplicantDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.DocumentVerifyManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author prageeth_s
 */
public class CardagainstFDOnholdApplicationDetailsServlet extends HttpServlet {

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
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private DocumentVerifyManager documentVerifyManager = null;
    private CheckedApplicantDetailsBean checkedApplicantBean = null;
    private CheckedApplicantDetailsBean bankVerifyBean = null;
    private ApplicationHistoryBean appHistoryBean = null;
    private SystemAuditBean systemAuditBean = null;
    private boolean success = false;
    private List<String> userTaskList;
    private String url = "/camm/documentverification/cardAgainstFDdocumentviewhome.jsp";

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
                String taskCode = TaskVarList.ONHOLD;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    // all parameters are submited as mutipart format..
                    ServletFileUpload uploadData = new ServletFileUpload(new DiskFileItemFactory());
                    List formData = uploadData.parseRequest(request);
                    HashMap<String, FileItem> formMap = this.convertToMaps(formData);


                    this.setUserInputToBean(formMap);
                    request.setAttribute("checkedApplicantBean", checkedApplicantBean);
                    bankVerifyBean = sessionVarlist.getBankVerifyBean();


                    //check for the record existance for this application id..if exist then update the details
                    if (this.isExistCardApplication(checkedApplicantBean.getApplicationId())) {

                        if (this.isRejectedCardApplication(checkedApplicantBean.getApplicationId())) {

                            request.setAttribute("selectedtab", "3");
                            request.setAttribute("errorMsg4", MessageVarList.ERROR_ALREADY_REJECT);
                            rd = getServletContext().getRequestDispatcher(url);



                        } else {
                            this.setAudittraceValue(request);
                            this.updateVerifyDetailsOfApplication(checkedApplicantBean, "VONH", appHistoryBean,systemAuditBean);


                            request.setAttribute("successMsg", MessageVarList.SUCCES_DOC_ONHOLD);
                            rd = request.getRequestDispatcher("/LoadDocumentVerifySearchServlet");
                        }
                    } else {

                        //check for the record existance for this application id..if doesn't exist then insert the details
                        this.setAudittraceValue(request);
                        this.insertVerifyDetailsOfApplication(checkedApplicantBean, "VONH", appHistoryBean,systemAuditBean);

                        request.setAttribute("selectedtab", "3");
                        request.setAttribute("successMsg4", MessageVarList.SUCCES_DOC_ONHOLD);
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

        } catch (Exception ex) {
            request.setAttribute("selectedtab", "3");
            request.setAttribute("errorMsg4", MessageVarList.ERROR_ONHOLD_DOC);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    public void setUserInputToBean(HashMap<String, FileItem> formMap) {

        String empDetails, salaryConfirm, empNic, lengthService, empStatus, fullName, nic, homeTelNo,
                mailingAddress, referenceDetails, addressFromResidence, mobileNo, owenership, placeOfWork,
                detailsOfReference, vehicle, personalDetailsFromResidence, relationship, residence, workPlace,
                personalDetailsAtreferee, addressFromreferee, docIdentification, docConfirmationLetter,
                docUtiityBill, docMarriageCertificate, docBirthCertificate,docSalarySlip1,docSalarySlip2,docStaffDecForm,docBankStatement,collateralConfirmationLetter;

        
        String appliactionId = formMap.get("appliactionid").getString();
        if (formMap.get("checkverify") != null) {
            empDetails = "YES";
        } else {
            empDetails = "NO";
        }
        if (formMap.get("checksalary") != null) {
            salaryConfirm = "YES";
        } else {
            salaryConfirm = "NO";
        }
        if (formMap.get("checkapplicantnic") != null) {
            empNic = "YES";
        } else {
            empNic = "NO";
        }
        if (formMap.get("checkservicelength") != null) {
            lengthService = "YES";
        } else {
            lengthService = "NO";
        }
        if (formMap.get("checkemploymentstatus") != null) {
            empStatus = "YES";
        } else {
            empStatus = "NO";
        }
        if (formMap.get("checkapplicantfullname") != null) {
            fullName = "YES";
        } else {
            fullName = "NO";
        }
        if (formMap.get("checkapplicantnic") != null) {
            nic = "YES";
        } else {
            nic = "NO";
        }
        if (formMap.get("checkapplicanthometel") != null) {
            homeTelNo = "YES";
        } else {
            homeTelNo = "NO";
        }
        if (formMap.get("checkapplicantmailing") != null) {
            mailingAddress = "YES";
        } else {
            mailingAddress = "NO";
        }
        if (formMap.get("checkreferencedetail") != null) {
            referenceDetails = "YES";
        } else {
            referenceDetails = "NO";
        }
        if (formMap.get("checkapplicantaddress") != null) {
            addressFromResidence = "YES";
        } else {
            addressFromResidence = "NO";
        }
        if (formMap.get("checkapplicantmobile") != null) {
            mobileNo = "YES";
        } else {
            mobileNo = "NO";
        }
        if (formMap.get("checkownership") != null) {
            owenership = "YES";
        } else {
            owenership = "NO";
        }
        if (formMap.get("checkworkplace") != null) {
            placeOfWork = "YES";
        } else {
            placeOfWork = "NO";
        }
        if (formMap.get("checkrefereedetalis") != null) {
            detailsOfReference = "YES";
        } else {
            detailsOfReference = "NO";
        }
        if (formMap.get("checkvehicletype") != null) {
            vehicle = "YES";
        } else {
            vehicle = "NO";
        }
        if (formMap.get("checkapplicantpersonal") != null) {
            personalDetailsFromResidence = "YES";
        } else {
            personalDetailsFromResidence = "NO";
        }
        if (formMap.get("checkrelationtoapplicant") != null) {
            relationship = "YES";
        } else {
            relationship = "NO";
        }
        if (formMap.get("checkapplicantresidence") != null) {
            residence = "YES";
        } else {
            residence = "NO";
        }
        if (formMap.get("checkrefereeworkplace") != null) {
            workPlace = "YES";
        } else {
            workPlace = "NO";
        }
        if (formMap.get("checkapplicantpersonalbyreferee") != null) {
            personalDetailsAtreferee = "YES";
        } else {
            personalDetailsAtreferee = "NO";
        }
        if (formMap.get("checkapplicantaddressbyreferee") != null) {
            addressFromreferee = "YES";
        } else {
            addressFromreferee = "NO";
        }
        if (formMap.get("checkidentificationcopy") != null) {
            docIdentification = "YES";
        } else {
            docIdentification = "NO";
        }
        if (formMap.get("checksalarycopy1") != null) {
            docSalarySlip1 = "YES";
        } else {
            docSalarySlip1 = "NO";
        }
        if (formMap.get("checkconfirmationcopy") != null) {
            docConfirmationLetter = "YES";
        } else {
            docConfirmationLetter = "NO";
        }
        if (formMap.get("checkutilitybillcopy") != null) {
            docUtiityBill = "YES";
        } else {
            docUtiityBill = "NO";
        }
        if (formMap.get("checkmarriagecopy") != null) {
            docMarriageCertificate = "YES";
        } else {
            docMarriageCertificate = "NO";
        }
        if (formMap.get("checkbirthcertifycopy") != null) {
            docBirthCertificate = "YES";
        } else {
            docBirthCertificate = "NO";
        }

        if (formMap.get("checksalarycopy2") != null) {
            docSalarySlip2 = "YES";
        } else {
            docSalarySlip2 = "NO";
        }

        if (formMap.get("checkStaffDecForm") != null) {
            docStaffDecForm = "YES";
        } else {
            docStaffDecForm = "NO";
        }

        if (formMap.get("checkBankStatement") != null) {
            docBankStatement = "YES";
        } else {
            docBankStatement = "NO";
        }
        
        if (formMap.get("checkcollateralConfirmationLetter") != null) {
            collateralConfirmationLetter = "YES";
        } else {
            collateralConfirmationLetter = "NO";
        }
        



        checkedApplicantBean = new CheckedApplicantDetailsBean();
        appHistoryBean = new ApplicationHistoryBean();

        checkedApplicantBean.setApplicationId(appliactionId);
        checkedApplicantBean.setEmpDetails(empDetails);
        checkedApplicantBean.setSalaryConfirm(salaryConfirm);
        checkedApplicantBean.setEmpNic(empNic);
        checkedApplicantBean.setServiceLength(lengthService);
        checkedApplicantBean.setEmpStatus(empStatus);
        checkedApplicantBean.setFullname(fullName);
        checkedApplicantBean.setNic(nic);
        checkedApplicantBean.setHomeTelNo(homeTelNo);
        checkedApplicantBean.setMailingAddress(mailingAddress);
        checkedApplicantBean.setReferenceDetails(referenceDetails);
        checkedApplicantBean.setAddressFromResidence(addressFromResidence);
        checkedApplicantBean.setMobileNo(mobileNo);
        checkedApplicantBean.setOwenership(owenership);
        checkedApplicantBean.setPlaceOfWork(placeOfWork);
        checkedApplicantBean.setDetailsOfReference(detailsOfReference);
        checkedApplicantBean.setRelatioship(relationship);
        checkedApplicantBean.setResidence(residence);
        checkedApplicantBean.setWorkPlace(workPlace);
        checkedApplicantBean.setPersonalDetailsReferee(personalDetailsAtreferee);
        checkedApplicantBean.setAddressFromReferee(addressFromreferee);
        checkedApplicantBean.setVehicle(vehicle);
        checkedApplicantBean.setApplicantPersonal(personalDetailsFromResidence);

        checkedApplicantBean.setDocIdentification(docIdentification);
        checkedApplicantBean.setDocSalarySlip1(docSalarySlip1);
        checkedApplicantBean.setDocSalarySlip2(docSalarySlip2);
        checkedApplicantBean.setDocStaffDecForm(docStaffDecForm);
        checkedApplicantBean.setDocBankStatement(docBankStatement);
        checkedApplicantBean.setDocConfirmationLetter(docConfirmationLetter);
        checkedApplicantBean.setDocUtilityBill(docUtiityBill);
        checkedApplicantBean.setDocMarriageCertificate(docMarriageCertificate);
        checkedApplicantBean.setDocBirthCertificate(docBirthCertificate);
        checkedApplicantBean.setCollateralConfirmationLetter(collateralConfirmationLetter);
        
        checkedApplicantBean.setLastUpdatedTime(new Date());
        checkedApplicantBean.setLastUpdatedUser(sessionUser.getUserName());

        appHistoryBean.setApplicationId(appliactionId);
        appHistoryBean.setApplicationLevel("VERI");
        appHistoryBean.setStatus("HCOM");
        appHistoryBean.setRemarks("Application is sent to onhold status");
        appHistoryBean.setLastUpdatedUser(sessionUser.getUserName());


    }

    private HashMap<String, FileItem> convertToMaps(List<FileItem> aFileItems) {
        HashMap<String, FileItem> fFileParams = new HashMap<String, FileItem>();
        for (FileItem item : aFileItems) {
            fFileParams.put(item.getFieldName(), item);
        }
        return fFileParams;
    }

//    public boolean validateUserInput(CheckedApplicantDetailsBean checkedBean) throws Exception {
//        boolean isValid = true;
//        //validate user Role code
//        try {
//
//
//            if (bankVerifyBean.getDocBirthCertificate().contentEquals("YES")) {
//                if (checkedBean.getDocBirthCertificate().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.BIRTHCERTIFICATE_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getDocMarriageCertificate().contentEquals("YES")) {
//                if (checkedBean.getDocMarriageCertificate().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.MARRIAGECERTIFICATE_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getDocUtilityBill().contentEquals("YES")) {
//                if (checkedBean.getDocUtilityBill().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.UTILITYBILL_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getDocConfirmationLetter().contentEquals("YES")) {
//                if (checkedBean.getDocConfirmationLetter().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.CONFIMLETTER_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getDocSalarySlip().contentEquals("YES")) {
//                if (checkedBean.getDocSalarySlip().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.SALARYSLIP_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getDocIdentification().contentEquals("YES")) {
//                if (checkedBean.getDocIdentification().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.COPYIDENTIFICATION_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getAddressFromReferee().contentEquals("YES")) {
//                if (checkedBean.getAddressFromReferee().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.APPLICANTADDRESSFROMREFEREE_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getPersonalDetailsReferee().contentEquals("YES")) {
//                if (checkedBean.getPersonalDetailsReferee().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.APPLICANTPERSONALFROMREFEREE_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getWorkPlace().contentEquals("YES")) {
//                if (checkedBean.getWorkPlace().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.WORKPLACE_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getResidence().contentEquals("YES")) {
//                if (checkedBean.getResidence().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.RESIDENCE_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getRelatioship().contentEquals("YES")) {
//                if (checkedBean.getRelatioship().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.RELATIONSHIP_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getApplicantPersonal().contentEquals("YES")) {
//                if (checkedBean.getApplicantPersonal().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.APPPERSONAL_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getVehicle().contentEquals("YES")) {
//                if (checkedBean.getVehicle().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.VEHICLE_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getDetailsOfReference().contentEquals("YES")) {
//                if (checkedBean.getDetailsOfReference().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.DETAILSOFREFERENCE_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getPlaceOfWork().contentEquals("YES")) {
//                if (checkedBean.getPlaceOfWork().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.PLACEOFWORK_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getOwenership().contentEquals("YES")) {
//                if (checkedBean.getOwenership().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.OWNERSHIP_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getMobileNo().contentEquals("YES")) {
//                if (checkedBean.getMobileNo().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.MOBILENUMBER_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getAddressFromResidence().contentEquals("YES")) {
//                if (checkedBean.getAddressFromResidence().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.ADDFROMRESIDENCE_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getReferenceDetails().contentEquals("YES")) {
//                if (checkedBean.getReferenceDetails().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.REFERENCE_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getMailingAddress().contentEquals("YES")) {
//                if (checkedBean.getMailingAddress().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.MAILINGADD_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getHomeTelNo().contentEquals("YES")) {
//                if (checkedBean.getHomeTelNo().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.HOMETEL_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getNic().contentEquals("YES")) {
//                if (checkedBean.getNic().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.NICNO_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getFullname().contentEquals("YES")) {
//                if (checkedBean.getFullname().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.FULLNAME_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getEmpStatus().contentEquals("YES")) {
//                if (checkedBean.getEmpStatus().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.EMPSTATUS_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getServiceLength().contentEquals("YES")) {
//                if (checkedBean.getServiceLength().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.SEVICELENGTH_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getEmpNic().contentEquals("YES")) {
//                if (checkedBean.getEmpNic().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.EMPNIC_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getSalaryConfirm().contentEquals("YES")) {
//                if (checkedBean.getSalaryConfirm().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.SALARYCONFIRM_VERIFY_NOTMATCH;
//                }
//            }
//            if (bankVerifyBean.getEmpDetails().contentEquals("YES")) {
//                if (checkedBean.getEmpDetails().contentEquals("NO")) {
//                    isValid = false;
//                    errorMessage = MessageVarList.EMPLOYEEDETAILS_VERIFY_NOTMATCH;
//                }
//            }
//
//        } catch (Exception ex) {
//            isValid = false;
//
//        }
//
//        return isValid;
//    }
    private boolean insertVerifyDetailsOfApplication(CheckedApplicantDetailsBean checkedApplicantBean, String applicationStatus, ApplicationHistoryBean historyBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            success = documentVerifyManager.insertVerifyDetailsOfApplication(checkedApplicantBean, applicationStatus, historyBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private boolean updateVerifyDetailsOfApplication(CheckedApplicantDetailsBean checkedApplicantBean, String applicationStatus, ApplicationHistoryBean historyBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            success = documentVerifyManager.updateVerifyDetailsOfApplication(checkedApplicantBean, historyBean, applicationStatus, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
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
            systemAuditBean.setDescription("Onhold credit card application, application id: " + checkedApplicantBean.getApplicationId() + " by " + sessionVarlist.getCMSSessionUser().getUserName());
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
