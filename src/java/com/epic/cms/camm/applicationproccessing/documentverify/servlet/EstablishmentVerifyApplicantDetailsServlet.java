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
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerificationPointsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.DocumentVerifyManager;
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
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
public class EstablishmentVerifyApplicantDetailsServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private DocumentVerifyManager documentVerifyManager = null;
    private CheckedApplicantDetailsBean checkedApplicantBean = null;
    private ApplicationHistoryBean appHistoryBean = null;
    private CheckedApplicantDetailsBean bankVerifyBean = null;
    private String errorMessage = null;
    private boolean success = false;
    private String cribFile = null;
    private String selectedTab = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private String url = "/camm/documentverification/establishmentdocumentviewhome.jsp";

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
                String taskCode = TaskVarList.VERIFY;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                    // all parameters are submited as mutipart format..
                    ServletFileUpload uploadData = new ServletFileUpload(new DiskFileItemFactory());
                    List formData = uploadData.parseRequest(request);
                    HashMap<String, FileItem> formMap = this.convertToMaps(formData);


                    this.setUserInputToBean(formMap);
                    request.setAttribute("checkedApplicantBean", checkedApplicantBean);
                    cribFile = this.isCribFileUpload(checkedApplicantBean.getApplicationId());
                    bankVerifyBean = sessionVarlist.getBankVerifyBean();

                    Map<String, Boolean> verificationPointValidationMap= configVerificationPointValidationMap("E");
                    
                    if (this.validateUserInput(checkedApplicantBean,verificationPointValidationMap)) {

                        //check for the record existance for this application id..if exist then update the details
                        if (this.isExistCardApplication(checkedApplicantBean.getApplicationId())) {

                            this.setAudittraceValue(request);
                            this.updateVerifyDetailsOfApplication(checkedApplicantBean, StatusVarList.APP_VERIFY_COMPELTE,appHistoryBean, systemAuditBean);
                            request.setAttribute("successMsg", MessageVarList.SUCCES_DOC_VERIFY);
                            rd = request.getRequestDispatcher("/LoadDocumentVerifySearchServlet");

                        } else {

                            //check for the record existance for this application id..if doesn't exist then insert the details

                            this.setAudittraceValue(request);
                            this.insertVerifyDetailsOfApplication(checkedApplicantBean, StatusVarList.APP_VERIFY_COMPELTE, appHistoryBean,systemAuditBean);

                            //request.setAttribute("selectedtab", "2");
                            //request.setAttribute("successMsg4", MessageVarList.SUCCES_DOC_VERIFY);
                            //rd = request.getRequestDispatcher(url);
                            request.setAttribute("successMsg", MessageVarList.SUCCES_DOC_VERIFY);
                            rd = request.getRequestDispatcher("/LoadDocumentVerifySearchServlet");

                        }


                    } else {

                        // show the error message within the relevent tab
                        request.setAttribute("selectedtab", selectedTab);
                        if (selectedTab.equals("0")) {
                            request.setAttribute("errorMsg", errorMessage);
                        } else if (selectedTab.equals("1")) {
                            request.setAttribute("errorMsg2", errorMessage);
                        } else if (selectedTab.equals("2")) {
                            request.setAttribute("errorMsg3", errorMessage);
                        }

                        rd = getServletContext().getRequestDispatcher(url);


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
            request.setAttribute("errorMsg3", checkedApplicantBean.getApplicationId() + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("selectedtab", "2");
            request.setAttribute("errorMsg4", MessageVarList.ERROR_VERIFY_DOC_VERIFY);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    public void setUserInputToBean(HashMap<String, FileItem> formMap) throws Exception {



        String empDetails, salaryConfirm, empNic, lengthService, empStatus, fullName, nic, homeTelNo,
                mailingAddress, referenceDetails, addressFromResidence, mobileNo, owenership, placeOfWork,
                detailsOfReference, vehicle, personalDetailsFromResidence, relationship, residence, workPlace,
                personalDetailsAtreferee, addressFromreferee, docIdentification, docConfirmationLetter,
                docUtiityBill, docMarriageCertificate, docBirthCertificate,docSalarySlip1,docSalarySlip2,docStaffDecForm,docBankStatement,collateralConfirmationLetter,
                docbusinessRegForm,docboardResolutionForm;


        try {

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
            if (formMap.get("checkempnic") != null) {
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
            
            if (formMap.get("checksalarycopy2") != null) {
                docSalarySlip2 = "YES";
            } else {
                docSalarySlip2 = "NO";
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

            if (formMap.get("checkbusinessRegForm") != null) {
                docbusinessRegForm = "YES";
            } else {
                docbusinessRegForm = "NO";
            }

            if (formMap.get("checkboardResolutionForm") != null) {
                docboardResolutionForm = "YES";
            } else {
                docboardResolutionForm = "NO";
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
            checkedApplicantBean.setBusinessRegForm(docbusinessRegForm);
            checkedApplicantBean.setBoardResolutionForm(docboardResolutionForm);	


            appHistoryBean.setApplicationId(appliactionId);
            appHistoryBean.setApplicationLevel("VERI");
            appHistoryBean.setStatus("HCOM");
            appHistoryBean.setRemarks("Application is verified");
            appHistoryBean.setLastUpdatedUser(sessionUser.getUserName());

        } catch (Exception e) {
            throw e;
        }

    }

    private HashMap<String, FileItem> convertToMaps(List<FileItem> aFileItems) {
        HashMap<String, FileItem> fFileParams = new HashMap<String, FileItem>();
        for (FileItem item : aFileItems) {
            fFileParams.put(item.getFieldName(), item);
        }
        return fFileParams;
    }

    public boolean validateUserInput(CheckedApplicantDetailsBean checkedBean ,Map<String, Boolean> verificationPointValidationMap) throws Exception {
        boolean isValid = true;
        //validate user Role code
        if(verificationPointValidationMap==null){
            verificationPointValidationMap= new LinkedHashMap<String, Boolean>();
        }
        try {
            
            if (verificationPointValidationMap.get("DOCBIRTHCERTIFICATE")!=null && verificationPointValidationMap.get("DOCBIRTHCERTIFICATE")) {
                if (checkedBean.getDocBirthCertificate().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.BIRTHCERTIFICATE_VERIFY_NOTMATCH;
                    selectedTab = "1";
                }
            }
            if (verificationPointValidationMap.get("DOCMARRIAGECERTIFICATE")!=null && verificationPointValidationMap.get("DOCMARRIAGECERTIFICATE")) {
                if (checkedBean.getDocMarriageCertificate().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.MARRIAGECERTIFICATE_VERIFY_NOTMATCH;
                    selectedTab = "1";
                }
            }
            if (verificationPointValidationMap.get("DOCUTILITYBILL")!=null && verificationPointValidationMap.get("DOCUTILITYBILL")) {
                if (checkedBean.getDocUtilityBill().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.UTILITYBILL_VERIFY_NOTMATCH;
                    selectedTab = "1";
                }
            }
            if (verificationPointValidationMap.get("DOCCONFIRMATIONOFEMPLOYEE")!=null && verificationPointValidationMap.get("DOCCONFIRMATIONOFEMPLOYEE")) {
                if (checkedBean.getDocConfirmationLetter().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.CONFIMLETTER_VERIFY_NOTMATCH;
                    selectedTab = "1";
                }
            }
            if (verificationPointValidationMap.get("DOCSALARYSLIP1")!=null && verificationPointValidationMap.get("DOCSALARYSLIP1")) {
                if (checkedBean.getDocSalarySlip1().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.SALARYSLIP1_VERIFY_NOTMATCH;
                    selectedTab = "1";
                }
            }
            
            if (verificationPointValidationMap.get("DOCSALARYSLIP2")!=null && verificationPointValidationMap.get("DOCSALARYSLIP2")) {
                if (checkedBean.getDocSalarySlip1().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.SALARYSLIP2_VERIFY_NOTMATCH;
                    selectedTab = "1";
                }
            }
                        
            if (verificationPointValidationMap.get("DOCBANKSTATEMENT")!=null && verificationPointValidationMap.get("DOCBANKSTATEMENT")) {
                if (checkedBean.getDocBankStatement().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.BANK_STATEMENT_VERIFY_NOTMATCH;
                    selectedTab = "1";
                }
            }
            
            
            if (verificationPointValidationMap.get("DOCCONFIRMATIONOFEMPLOYEE")!=null && verificationPointValidationMap.get("DOCCONFIRMATIONOFEMPLOYEE")) {
                if (checkedBean.getDocStaffDecForm().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.STAFF_DEC_VERIFY_NOTMATCH;
                    selectedTab = "1";
                }
            }
            
            
            
            
            if (verificationPointValidationMap.get("DOCIDENTIFICATION")!=null && verificationPointValidationMap.get("DOCIDENTIFICATION")) {
                if (checkedBean.getDocIdentification().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.COPYIDENTIFICATION_VERIFY_NOTMATCH;
                    selectedTab = "1";
                }
            }
            if (verificationPointValidationMap.get("ADDRESSFROMREF")!=null && verificationPointValidationMap.get("ADDRESSFROMREF")) {
                if (checkedBean.getAddressFromReferee().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.APPLICANTADDRESSFROMREFEREE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("PERSONALDETAILSATREFREE")!=null && verificationPointValidationMap.get("PERSONALDETAILSATREFREE")) {
                if (checkedBean.getPersonalDetailsReferee().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.APPLICANTPERSONALFROMREFEREE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("WORKPLACE")!=null && verificationPointValidationMap.get("WORKPLACE")) {
                if (checkedBean.getWorkPlace().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.WORKPLACE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("RESIDENCE")!=null && verificationPointValidationMap.get("RESIDENCE")) {
                if (checkedBean.getResidence().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.RESIDENCE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("RELATIONSHIP")!=null && verificationPointValidationMap.get("RELATIONSHIP")) {
                if (checkedBean.getRelatioship().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.RELATIONSHIP_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("PERSONALDETAILSATRESIDENT")!=null && verificationPointValidationMap.get("PERSONALDETAILSATRESIDENT")) {
                if (checkedBean.getApplicantPersonal().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.APPPERSONAL_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("VEHICLE")!=null && verificationPointValidationMap.get("VEHICLE")) {
                if (checkedBean.getVehicle().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.VEHICLE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("DETAILSOFREFERENCE")!=null && verificationPointValidationMap.get("DETAILSOFREFERENCE")) {
                if (checkedBean.getDetailsOfReference().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.DETAILSOFREFERENCE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("PLACEOFWORK")!=null && verificationPointValidationMap.get("PLACEOFWORK")) {
                if (checkedBean.getPlaceOfWork().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.PLACEOFWORK_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("OWNERSHIP")!=null && verificationPointValidationMap.get("OWNERSHIP")) {
                if (checkedBean.getOwenership().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.OWNERSHIP_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("MOBILENO")!=null && verificationPointValidationMap.get("MOBILENO")) {
                if (checkedBean.getMobileNo().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.MOBILENUMBER_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("ADDRESSFROMRESI")!=null && verificationPointValidationMap.get("ADDRESSFROMRESI")) {
                if (checkedBean.getAddressFromResidence().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.ADDFROMRESIDENCE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("REFERENCEDETAILS")!=null && verificationPointValidationMap.get("REFERENCEDETAILS")) {
                if (checkedBean.getReferenceDetails().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.REFERENCE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("MAILINGADDRESS")!=null && verificationPointValidationMap.get("MAILINGADDRESS")) {
                if (checkedBean.getMailingAddress().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.MAILINGADD_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("HOMETELEPHONENO")!=null && verificationPointValidationMap.get("HOMETELEPHONENO")) {
                if (checkedBean.getHomeTelNo().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.HOMETEL_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("NICNUMBER")!=null && verificationPointValidationMap.get("NICNUMBER")) {
                if (checkedBean.getNic().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.NICNO_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("FULLNAME")!=null && verificationPointValidationMap.get("FULLNAME")) {
                if (checkedBean.getFullname().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.FULLNAME_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("EMPLOYMENTSTATUS")!=null && verificationPointValidationMap.get("EMPLOYMENTSTATUS")) {
                if (checkedBean.getEmpStatus().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.EMPSTATUS_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("LENGTHOFSERVICE")!=null && verificationPointValidationMap.get("LENGTHOFSERVICE")) {
                if (checkedBean.getServiceLength().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.SEVICELENGTH_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("NICNUMBERFROMEMP")!=null && verificationPointValidationMap.get("NICNUMBERFROMEMP")) {
                if (checkedBean.getEmpNic().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.EMPNIC_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("SALARYCONFIRMATION")!=null && verificationPointValidationMap.get("SALARYCONFIRMATION")) {
                if (checkedBean.getSalaryConfirm().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.SALARYCONFIRM_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            if (verificationPointValidationMap.get("EMPLOYMENTDETAILS")!=null && verificationPointValidationMap.get("EMPLOYMENTDETAILS")) {
                if (checkedBean.getEmpDetails().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.EMPLOYEEDETAILS_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            }
            
            
            if (verificationPointValidationMap.get("BOARDRESOLUTION") != null && verificationPointValidationMap.get("BOARDRESOLUTION")) {
                if (checkedBean.getBoardResolutionForm().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.BOARDRESOLUTION_VERIFY_NOTMATCH;
                    selectedTab = "1";
                }
            }

            if (verificationPointValidationMap.get("BUSINESSREGCERTIFICATE") != null && verificationPointValidationMap.get("BUSINESSREGCERTIFICATE")) {
                if (checkedBean.getBusinessRegForm().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.BUSINESSREGCERTIFICATE_VERIFY_NOTMATCH;
                    selectedTab = "1";
                }
            }

        } catch (Exception ex) {
            isValid = false;

        }

        return isValid;
    }

    private boolean insertVerifyDetailsOfApplication(CheckedApplicantDetailsBean checkedApplicantBean, String applicationStatus,ApplicationHistoryBean historyBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            success = documentVerifyManager.insertVerifyDetailsOfApplication(checkedApplicantBean, applicationStatus,historyBean, systemAuditBean);
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

    private String isCribFileUpload(String applicationId) throws Exception {
        String flag = null;
        try {

            documentVerifyManager = new DocumentVerifyManager();
            flag = documentVerifyManager.isCribFileUpload(applicationId);
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
            systemAuditBean.setDescription("Verify credit card application, application id: " + checkedApplicantBean.getApplicationId() + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.DOCUMENTVERIFY);
            systemAuditBean.setPageCode(PageVarList.DOCUMENTVERIFYHOME);
            systemAuditBean.setTaskCode(TaskVarList.VERIFY);
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
    
    private Map<String, Boolean> configVerificationPointValidationMap(String cardCategoryCode) throws Exception{
        Map<String, Boolean> map = new LinkedHashMap<>();
        try{    
            documentVerifyManager = new DocumentVerifyManager();
            List<VerificationPointsBean> verificationPointsBeanList = documentVerifyManager.getVerificationPoints(cardCategoryCode);
            for(VerificationPointsBean bean : verificationPointsBeanList){
                map.put(bean.getFieldName(), bean.getIsMandatory());          
            }
        } catch (Exception ex) {
            throw ex;
        }
        return map;    
            
        
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
