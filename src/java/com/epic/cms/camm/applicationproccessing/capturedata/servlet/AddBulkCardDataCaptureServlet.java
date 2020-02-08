/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BulkCardBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.BulkCardDataCaptureManager;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.cpmm.cardembossing.businesslogic.CardEmbossingMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jpos.iso.ISOUtil;

/**
 *
 * @author nalin
 */
public class AddBulkCardDataCaptureServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private BulkCardBean bulkBean = null;
    private boolean successInsert = false;
    private BulkCardDataCaptureManager bulkManager = null;
    private ApplicationAssignManager appAssignManager;
    private String maxId = null;
    private CommonFilePathBean commonFilePathBean = null;
    private boolean uploadSuccess = false;
    private String url = "/camm/capturedata/bulkcarddatacapturehome.jsp";

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
            //call to existing session
            /////////////////////////////////////////////////////////////////////
            sessionObj = request.getSession(false);
            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                //check system user is in same session or not
                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        //user not in same session.
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
            /////////////////////////////////////////////////////////////////////
            try {
                //set page code and task codes
                String pageCode = PageVarList.BULK_CARD_DATA_CAPTURE;
                String taskCode = TaskVarList.ADD;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        commonFilePathBean = new CommonFilePathBean();
                        BulkCardBean invalidMsgBean = new BulkCardBean();

                        int fsize = 1024;

                        DiskFileItemFactory factory = new DiskFileItemFactory();
                        factory.setSizeThreshold(1 * 1024 * 1024 * 2);
                        ServletFileUpload upload = new ServletFileUpload(factory);
                        upload.setSizeMax(fsize * 1024 *2);

                        List items = upload.parseRequest(request);
                        HashMap<String, FileItem> formMap = this.convertToMaps(items);
                        this.setUserInputToBean(formMap);

                        invalidMsgBean = this.validateUserInput(bulkBean);
                        if (invalidMsgBean == null) {

                            try {

                                this.generateApplicationId();
                                uploadSuccess = this.uploadDocument(request, items, fsize, bulkBean.getApplicationID());
                                if (uploadSuccess) {
                                    this.setAudittraceValue(request);

                                    if (this.checkCardNumberIsExist(bulkBean.getCardNumber())) {

                                        if (bulkBean.getBranch().equals(this.checkCardRequestedBranch(bulkBean.getCardNumber()))) {

                                            successInsert = this.insertBulkCardApplicationDetails(bulkBean, systemAuditBean);

                                            if (successInsert) {

                                                request.setAttribute("successMsg", MessageVarList.BULKCARD_APPLICATION_SUCCESS_ADD + " " + bulkBean.getApplicationID());
                                                rd = getServletContext().getRequestDispatcher("/LoadBulkCardDataCpatureServlet");
                                            }
                                        } else {
                                            request.setAttribute("errorMsg", MessageVarList.INVALID_ISSUING_BRANCH);
                                            request.setAttribute("bulkBean", bulkBean);
                                            rd = getServletContext().getRequestDispatcher(url);
                                        }
                                    } else {

                                        request.setAttribute("errorMsg", MessageVarList.CARD_IS_NOT_EXIST);

                                        request.setAttribute("bulkBean", bulkBean);
                                        rd = getServletContext().getRequestDispatcher(url);
                                    }

                                } else {

                                    request.setAttribute("bulkBean", bulkBean);
                                    request.setAttribute("errorMsg", MessageVarList.SIGNATURE_FILE_NULL);
                                    rd = getServletContext().getRequestDispatcher(url);

                                }
                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("bulkBean", bulkBean);
                                request.setAttribute("errorMsg", oraMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }
                        } else {

                            request.setAttribute("bulkBean", bulkBean);
                            request.setAttribute("invalidMsgBean", invalidMsgBean);
                            rd = getServletContext().getRequestDispatcher(url);
                        }

                    } catch (Exception e) {

                        request.setAttribute("bulkBean", bulkBean);
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);

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

        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private boolean uploadDocument(HttpServletRequest request, List items, int fsize, String applicationId) throws Exception {
        boolean isUpload = false;
        try {

            String rootPath = "";

            Iterator itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();

                if (item.isFormField()) {
                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                } else {

                    if (item.getFieldName().equals("signature") && !item.getName().equals("")) {

                        String fileName = this.getGenerateFileName("SIGNATURE", applicationId);
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + item.getFieldName());
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + item.getContentType());

                        if (!(item.getSize() > fsize * 1024)) {

                            String osType = this.getOS_Type();
                            CardEmbossingMgtManager cardEmbossingManager = new CardEmbossingMgtManager();
                            commonFilePathBean = cardEmbossingManager.getFilePaths(osType);

                            if (osType.equals("WINDOWS")) {
                                rootPath = commonFilePathBean.getScandocument() + File.separator + applicationId + File.separator + "SIGNATURE";

                                this.createDirectory(rootPath);

                            } else if (osType.equals("LINUX")) {
                                rootPath = commonFilePathBean.getScandocument() + File.separator + applicationId + File.separator + "SIGNATURE";
                                this.createDirectory(rootPath);
                            }

                            File file = new File(rootPath, fileName);
                            item.write(file);
                            isUpload = true;
                            bulkBean.setSignatureFileName(fileName);
                            //sessionVarlist.setPrimerySignatureFileName(fileName);
                            // request.setAttribute("successMsg", MessageVarList.FILE_UPLOADED);

                        } else {

                            request.setAttribute("errorMsg", MessageVarList.IMAGE_SIZE_TOO_LARGE);
                            break;
                        }
                    }

                    if (item.getFieldName().equals("nic") && !item.getName().equals("")) {

                        String fileName = this.getGenerateFileName("NIC", applicationId);
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + item.getFieldName());
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + item.getContentType());

                        if (!(item.getSize() > fsize * 1024)) {

                            String osType = this.getOS_Type();
                            CardEmbossingMgtManager cardEmbossingManager = new CardEmbossingMgtManager();
                            commonFilePathBean = cardEmbossingManager.getFilePaths(osType);

                            if (osType.equals("WINDOWS")) {
                                rootPath = commonFilePathBean.getScandocument() + File.separator + applicationId + File.separator + "NIC";

                                this.createDirectory(rootPath);

                            } else if (osType.equals("LINUX")) {
                                rootPath = commonFilePathBean.getScandocument() + File.separator + applicationId + File.separator + "NIC";
                                this.createDirectory(rootPath);
                            }

                            File file = new File(rootPath, fileName);
                            item.write(file);
                            bulkBean.setNicFileName(fileName);
                            bulkBean.setFlag(true);
                            //sessionVarlist.setPrimerySignatureFileName(fileName);
                            //request.setAttribute("successMsg", MessageVarList.FILE_UPLOADED);

                        } else {

                            request.setAttribute("errorMsg", MessageVarList.IMAGE_SIZE_TOO_LARGE);
                            break;
                        }
                    }
                }
            }


        } catch (Exception ex) {
            throw ex;
        }
        return isUpload;
    }

    public Boolean setUserInputToBean(HashMap<String, FileItem> formMap) throws Exception {
        Boolean success = true;
        try {

            bulkBean = new BulkCardBean();


            bulkBean.setIdentificationType(formMap.get("identificationType").getString());
            bulkBean.setIdentificationNumber(formMap.get("IdentificationNumber").getString());
            bulkBean.setTitle(formMap.get("title").getString());
            bulkBean.setFirstName(formMap.get("firstName").getString());
            bulkBean.setMiddleName(formMap.get("middleName").getString());
            bulkBean.setLastName(formMap.get("lastName").getString());
            bulkBean.setMothersMaidenName(formMap.get("mothersMaidenName").getString());
            bulkBean.setPrimeryAccNo(formMap.get("primeryAccNo").getString());
            bulkBean.setOtherAccNo1(formMap.get("otherAccNo1").getString());
            bulkBean.setCardNumber(formMap.get("cardNumber").getString());
            bulkBean.setBranch(formMap.get("branch").getString());
            bulkBean.setApplicationDomain(StatusVarList.DEBIT);

            bulkBean.setLsatUpdatedUser(sessionUser.getUserName());


        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    private boolean insertBulkCardApplicationDetails(BulkCardBean bulkCardBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {
            bulkManager = new BulkCardDataCaptureManager();
            flag = bulkManager.insertBulkCardApplicationDetails(bulkCardBean, systemAuditBean);

        } catch (Exception ex) {
            throw ex;
        }
        return flag;

    }

    private String checkCardRequestedBranch(String cardNumber) throws Exception {
        String branch = null;
        try {
            bulkManager = new BulkCardDataCaptureManager();
            branch = bulkManager.checkCardRequestedBranch(cardNumber);

        } catch (Exception ex) {
            throw ex;
        }
        return branch;
    }

    private boolean checkCardNumberIsExist(String cardNumber) throws Exception {
        boolean isExist = false;
        try {
            bulkManager = new BulkCardDataCaptureManager();
            isExist = bulkManager.checkCardNumberIsExist(cardNumber);

        } catch (Exception ex) {
            throw ex;
        }
        return isExist;
    }

    private void getMaxFromCardApplication(String domain) throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            maxId = appAssignManager.getMaxFromCardApplication(domain);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void generateApplicationId() throws Exception {

        try {

            this.getMaxFromCardApplication(bulkBean.getApplicationDomain());
            String prefix = this.getApplicationDomainPrefix(bulkBean.getApplicationDomain());
            String applicationId = this.setApplicationId(maxId);

            if (maxId.equals("0")) {

                applicationId = prefix.concat("0000000001");
            }
            bulkBean.setApplicationID(applicationId);

        } catch (Exception ex) {
            throw ex;
        }

    }

    private String getApplicationDomainPrefix(String domainId) throws Exception {
        String prefix;
        try {
            appAssignManager = new ApplicationAssignManager();
            prefix = appAssignManager.getApplicationDomainPrefix(domainId);
            return prefix;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public BulkCardBean validateUserInput(BulkCardBean bulkCardBean) throws Exception {
        boolean isValidate = true;
        int count = 0;
        BulkCardBean invalidMsgBean = new BulkCardBean();

//        --------------------validate user Role code -------------------------------

        try {

            if (bulkCardBean.getIdentificationType().contentEquals("") || bulkCardBean.getIdentificationType().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setIdentificationType(MessageVarList.REQUIRED);
            }
            if (bulkCardBean.getIdentificationNumber().contentEquals("") || bulkCardBean.getIdentificationNumber().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setIdentificationNumber(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isAlphaNumeric(bulkCardBean.getIdentificationNumber())) {
                isValidate = false;

                invalidMsgBean.setIdentificationNumber(MessageVarList.INVALID);
            }
            if (bulkCardBean.getTitle().contentEquals("") || bulkCardBean.getTitle().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setTitle(MessageVarList.REQUIRED);
            }
            if (bulkCardBean.getFirstName().contentEquals("") || bulkCardBean.getFirstName().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setFirstName(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isAlpha(bulkCardBean.getFirstName())) {
                isValidate = false;

                invalidMsgBean.setFirstName(MessageVarList.INVALID);
            }
            if (!UserInputValidator.isAlpha(bulkCardBean.getMiddleName())) {
                isValidate = false;

                invalidMsgBean.setMiddleName(MessageVarList.INVALID);
            }
            if (bulkCardBean.getLastName().contentEquals("") || bulkCardBean.getLastName().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setLastName(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isAlpha(bulkCardBean.getLastName())) {
                isValidate = false;

                invalidMsgBean.setLastName(MessageVarList.INVALID);
            }
            if (bulkCardBean.getMothersMaidenName().contentEquals("") || bulkCardBean.getMothersMaidenName().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setMothersMaidenName(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isAlpha(bulkCardBean.getMothersMaidenName())) {
                isValidate = false;

                invalidMsgBean.setMothersMaidenName(MessageVarList.INVALID);
            }
            if (bulkCardBean.getPrimeryAccNo().contentEquals("") || bulkCardBean.getPrimeryAccNo().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setPrimeryAccNo(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isAlphaNumeric(bulkCardBean.getPrimeryAccNo())) {
                isValidate = false;

                invalidMsgBean.setPrimeryAccNo(MessageVarList.INVALID);
            }
            if (bulkCardBean.getOtherAccNo1().contentEquals("") || bulkCardBean.getOtherAccNo1().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setOtherAccNo1(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isAlphaNumeric(bulkCardBean.getOtherAccNo1())) {
                isValidate = false;

                invalidMsgBean.setOtherAccNo1(MessageVarList.INVALID);
            }

            if (bulkCardBean.getCardNumber().contentEquals("") || bulkCardBean.getCardNumber().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setCardNumber(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isNumeric(bulkCardBean.getCardNumber()) || bulkBean.getCardNumber().length() != 16) {
                isValidate = false;

                invalidMsgBean.setCardNumber(MessageVarList.INVALID);
            }
            if (bulkCardBean.getBranch().contentEquals("") || bulkCardBean.getBranch().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setBranch(MessageVarList.REQUIRED);
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        if (isValidate) {
            invalidMsgBean = null;
        }
        return invalidMsgBean;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = bulkBean.getApplicationID();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Bulk Card. Bulk Card Id : " + bulkBean.getApplicationID() + ";  By: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CAPTURE_DATA);
            systemAuditBean.setPageCode(PageVarList.BULK_CARD_DATA_CAPTURE);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
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

    private HashMap<String, FileItem> convertToMaps(List<FileItem> aFileItems) {
        HashMap<String, FileItem> fFileParams = new HashMap<String, FileItem>();
        for (FileItem item : aFileItems) {
            fFileParams.put(item.getFieldName(), item);
        }
        return fFileParams;
    }

    public String setApplicationId(String maxId) throws Exception {

        String padLine = "";

        padLine = ISOUtil.zeropad((Long.parseLong(maxId) + 1) + "", 12);

        return padLine;
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

    private String getGenerateFileName(String name, String id) throws Exception {
        String fileName = "";
        String extention = "JPG";

        try {
//            temp = name.split("\\.");
//            if (temp.length > 0) {
//                extention = temp[temp.length - 1];
//          

            fileName = id + "_" + name + "." + extention;

        } catch (Exception ex) {
            throw ex;
        }
        return fileName;
    }

    private void createDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
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
