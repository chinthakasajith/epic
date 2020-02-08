/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedApplicantDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.DocumentVerifyManager;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.cpmm.cardembossing.businesslogic.CardEmbossingMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.fileencrypt.CryptoException;
import com.epic.cms.system.util.fileencrypt.CryptoUtils;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author chanuka
 */
public class UploadCribFileServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private CheckedApplicantDetailsBean checkedApplicantBean = null;
    private DocumentVerifyManager documentVerifyManager = null;
    private SystemAuditBean systemAuditBean = null;
    private boolean success = false;
    private List<String> userTaskList;
    private String url = "/camm/documentverification/documentviewhome.jsp";
    private String key = "Mary has one cat";
    private File file,encryptedFile;

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

//
//            ServletFileUpload uploadData = new ServletFileUpload(new DiskFileItemFactory());
//            List formData = uploadData.parseRequest(request);
//            HashMap<String, FileItem> formMap = this.convertToMaps(formData);
//
//
//            this.setUserInputToBean(formMap);
//            request.setAttribute("checkedApplicantBean", checkedApplicantBean);
//
////            int count = 0;
////            Iterator it = formMap.entrySet().iterator();
////            while (it.hasNext()) {
////                Map.Entry pairs = (Map.Entry) it.next();
////            }
//
//            //File Upload .....................................
//            FileItem uploadItem = formMap.get("cribfile");
//
/////////     uploadItem.write(new File("C:\\DOCUMENT\\" + sessionVarlist.getVerifyingAppId() + "\\CRIB_FILE\\" + formMap.get("cribfile").getName()));
//
//            uploadItem.write(new File("C:\\DOCUMENT\\" + formMap.get("cribfile").getName()));
//
//
//            this.updateCardApplicationCrib(sessionVarlist.getVerifyingAppId(), formMap.get("cribfile").getName(), sessionUser.getUserName());
//
//
//            request.setAttribute("successMsg", "File successfully uploaded!");
//
//            request.setAttribute("selectedtab", "2");
//            rd = getServletContext().getRequestDispatcher(url);
//            rd.include(request, response);
//
//
//
//
            try {
                //set page code and task codes
                String pageCode = PageVarList.DOCUMENTVERIFYHOME;
                String taskCode = TaskVarList.UPLOAD;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    int fsize = 1024;
                    String documentRoot = "DOCUMENT";
                    String rootPath = "";
                    String applicationId = sessionVarlist.getVerifyingAppId();

                    // check for the submited value are in Multipart format
                    boolean isMultipart = ServletFileUpload.isMultipartContent(request);

                    if (isMultipart) {

                        DiskFileItemFactory factory = new DiskFileItemFactory();
                        List items;
                        factory.setSizeThreshold(1 * 1024 * 1024);
                        ServletFileUpload upload = new ServletFileUpload(factory);
//                      upload.setSizeMax(fsize * 1024);

                        try {

                            //all parameter push to a list
                            items = upload.parseRequest(request);

                            //filter all names and value to a hash map
                            HashMap<String, FileItem> formMap = this.convertToMaps(items);

                            this.setUserInputToBean(formMap);
                            request.setAttribute("checkedApplicantBean", checkedApplicantBean);

                            Iterator itr = items.iterator();
                            while (itr.hasNext()) {
                                FileItem item = (FileItem) itr.next();

                                if (item.isFormField()) {
                                } else {

                                    // check for is file selected for upload
                                    if (!(item.getSize() <= 0)) {

                                        String fileName = this.getGenerateFileName(item.getName(), applicationId);

                                        // check for maximum file size
                                        if (!(item.getSize() > fsize * 1024 * 9)) {

                                            String osType = this.getOS_Type();
                                            CardEmbossingMgtManager filePathMgt = new CardEmbossingMgtManager();
                                            CommonFilePathBean commonFilePathBean = filePathMgt.getFilePaths(osType);

                                            if (osType.equals("WINDOWS")) {
                                                rootPath = commonFilePathBean.getScandocument() + applicationId + "\\" + "CRIB";

                                                this.createDirectory(rootPath);

                                            } else if (osType.equals("LINUX")) {
                                                rootPath = commonFilePathBean.getScandocument() + applicationId + "/" + "CRIB";
                                                this.createDirectory(rootPath);
                                            }

                                            file = new File(rootPath, fileName);
                                            item.write(file);
                                            //encrypt
                                            encryptedFile = new File(rootPath, fileName);
                                            try {
                                                CryptoUtils.encrypt(key, file, encryptedFile);
                                            } catch (CryptoException ex) {
                                                throw ex;
                                            }

                                            this.setAudittraceValue(request, fileName);
                                            this.updateCardApplicationCrib(sessionVarlist.getVerifyingAppId(), formMap.get("cribfile").getName(), sessionUser.getUserName(), systemAuditBean);

                                            request.setAttribute("successMsg3", "File successfully uploaded!");

                                            request.setAttribute("selectedtab", "2");
                                            rd = getServletContext().getRequestDispatcher(url);
                                            rd.forward(request, response);

                                        } else {
                                            request.setAttribute("errorMsg3", "Invalid file size");
                                            request.setAttribute("selectedtab", "2");
                                            rd = getServletContext().getRequestDispatcher(url);
                                            rd.forward(request, response);
                                        }
                                    } else {
                                        request.setAttribute("errorMsg3", "Please select a file");
                                        request.setAttribute("selectedtab", "2");
                                        rd = getServletContext().getRequestDispatcher(url);
                                        rd.forward(request, response);
                                    }

                                }
                            }

                        } catch (SizeLimitExceededException ex) {
                            throw ex;
                        }
                    }

                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }

            } catch (AccessDeniedException adex) {
                throw adex;

            } catch (SizeLimitExceededException ex) {
                throw ex;
            }

        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);
        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);
        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            request.setAttribute("selectedtab", "2");
            request.setAttribute("errorMsg3", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (SizeLimitExceededException ex) {
            request.setAttribute("errorMsg3", "Invalid file size");
            request.setAttribute("selectedtab", "2");
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("selectedtab", "2");
            request.setAttribute("errorMsg3", MessageVarList.ERROR_UPLOAD_CRIB_FILE);
            rd = request.getRequestDispatcher(url);
            ex.printStackTrace();
            rd.forward(request, response);
        } finally {
            out.close();
        }
    }

    private HashMap<String, FileItem> convertToMaps(List<FileItem> aFileItems) {
        HashMap<String, FileItem> fFileParams = new HashMap<String, FileItem>();
        for (FileItem item : aFileItems) {
            fFileParams.put(item.getFieldName(), item);
        }
        return fFileParams;
    }

    public void setUserInputToBean(HashMap<String, FileItem> formMap) {

        String empDetails, salaryConfirm, empNic, lengthService, empStatus, fullName, nic, homeTelNo,
                mailingAddress, referenceDetails, addressFromResidence, mobileNo, owenership, placeOfWork,
                detailsOfReference, vehicle, personalDetailsFromResidence, relationship, residence, workPlace,
                personalDetailsAtreferee, addressFromreferee, docIdentification, docConfirmationLetter,
                docUtiityBill, docMarriageCertificate, docBirthCertificate,docSalarySlip1,docSalarySlip2,docStaffDecForm,docBankStatement;

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
        checkedApplicantBean = new CheckedApplicantDetailsBean();

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

        checkedApplicantBean.setLastUpdatedTime(new Date());
        checkedApplicantBean.setLastUpdatedUser(sessionUser.getUserName());
        

    }

    private boolean updateCardApplicationCrib(String applicationId, String cribFileName, String user, SystemAuditBean systemAuditBean) throws Exception {

        try {

            documentVerifyManager = new DocumentVerifyManager();
            success = documentVerifyManager.updateCardApplicationCrib(applicationId, cribFileName, user, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private String getGenerateFileName(String name, String id) {
        String fileName = "";
        String extention = "";

        String[] temp;
        try {
            temp = name.split("\\.");
            if (temp.length > 0) {
                extention = temp[temp.length - 1];
            }
        } catch (Exception ex) {
        }
        fileName = id + "_CRIB" + "." + extention;

        return fileName;
    }

    public String getOS_Type() throws Exception {

        String osType = "";
        String osName = "";
        osName = System.getProperty("os.name", "").toLowerCase();

        // For WINDOWS
        if (osName.contains("windows")) {
            osType = "WINDOWS";
        } else {
            // For LINUX
            if (osName.contains("linux")) {
                osType = "LINUX";
            } else {
                throw new Exception("Cannot identify the Operating System.");
            }
        }

        return osType;
    }

    private void createDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
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

    private void setAudittraceValue(HttpServletRequest request, String fileName) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Upload CRIB file,file name: " + fileName + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.DOCUMENTVERIFY);
            systemAuditBean.setPageCode(PageVarList.DOCUMENTVERIFYHOME);
            systemAuditBean.setTaskCode(TaskVarList.UPLOAD);
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
