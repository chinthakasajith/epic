/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.filemgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.filemgt.bean.FileInfoBean;
import com.epic.cms.backoffice.filemgt.bean.FileTypeBean;
import com.epic.cms.backoffice.filemgt.businesslogic.FileInfoManager;
import com.epic.cms.backoffice.filemgt.businesslogic.FileTypeManager;
//import com.epic.cms.backoffice.filemgt.persistance.FileInfoManager;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.FileVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.io.FileUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author jeevan
 */
public class AddFileUploadServletInfoServlet extends HttpServlet {

    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private FileInfoBean fileInfoBean = null;
    private FileTypeManager rECFileTypeManager;
    private FileInfoManager FileInfoManger;
    private List<String> recordStringLst;
    private List<String> userTaskList;
    private List<FileTypeBean> fileTypeList;
    private FileInfoBean fileTypeBean; //******
    private String url = "/backoffice/filemgt/fileuploadhome.jsp";
    private CommonFilePathBean commonFilePathBean = null;
    public List<FileInfoBean> filellList;
    private SystemAuditBean systemAuditBean;
    List paramList;
    String message = null;
    private String rootPathOriginal = "";
    private String rootPathBackup = "";
    private String errorMessage = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        paramList = new ArrayList();
        try {
            //call the existing session
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


            try {
                //set page code and task codes 
                String pageCode = PageVarList.INCOMING_FILE_UPLOAD;
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whether user has an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    this.getAllFileTypes();

                    request.setAttribute("fileTypelist", fileTypeList);
//                    request.setAttribute("fileInfoFileType", fileInfoFileType);
                    request.setAttribute("operationtype", "default");

                    rd = getServletContext().getRequestDispatcher(url);
                    /*
                    
                     */

                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();
                }
                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);
            } catch (AccessDeniedException adex) {

                throw adex;
            }

            int fsize = 1024 * 20;
            File file = null, fileBackup = null;

            boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            if (isMultipart) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(1 * 1024 * 1024);
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(fsize * 1024);
                try {
                    List items = upload.parseRequest(request);
                    Iterator itr = items.iterator();
                    while (itr.hasNext()) {
                        FileItem item = (FileItem) itr.next();

                        if (item.isFormField()) {

                            paramList.add(item.getString());


                        } else {
                            //Handle Uploaded files.
                            paramList.add(item.getName());
                            if (!(item.getSize() > fsize * 1024)) {

                                if (paramList.get(0) != null && !paramList.get(0).equals("")) {

                                    List filePath = this.findByFileType(paramList.get(0).toString());

                                    file = new File(filePath.get(0).toString());
                                    fileBackup = new File(filePath.get(1).toString());
                                }

//                                request.setAttribute("fileTypeBean", fileTypeBean); //***
                                fileInfoBean = this.setUserInputToBean(request, paramList);

                                if (this.validateUserInput(fileInfoBean)) {
                                    

                                    if (this.processUploadedFile(request, paramList)) {

                                        if (!this.isRecordAvailable(fileInfoBean.getFileName())) {

                                            String osType = this.getOS_Type();

                                            if (osType.equals("WINDOWS")) {

                                                String basePath = this.getFilePath(osType, "VBIN");
//                                    rootPath = basePath + "\\"+filePath;
                                                rootPathOriginal = basePath + "\\" + file;
                                                rootPathBackup = basePath + "\\" + fileBackup;

                                                this.createDirectory(rootPathOriginal);
                                                this.createDirectory(rootPathBackup);

                                                File file1 = new File(rootPathOriginal, item.getName());
                                                file1.createNewFile();
                                                File filebackup2 = new File(rootPathBackup, item.getName());
                                                filebackup2.createNewFile();

                                                PrintWriter pw = new PrintWriter(new FileOutputStream(file1, true));
                                                //pw.print();
                                                pw.flush();

                                                item.write(file1);
                                                FileUtils.copyFile(file1, filebackup2);

                                                pw.close();

                                            } else if (osType.equals("LINUX")) {
                                                String basePath = this.getFilePath(osType, "VBIN");
                                                rootPathOriginal = basePath + "\\" + file;
                                                rootPathBackup = basePath + "\\" + fileBackup;

                                                this.createDirectory(rootPathOriginal);
                                                this.createDirectory(rootPathBackup);

                                                File file1 = new File(rootPathOriginal, item.getName());
                                                file1.createNewFile();
                                                File filebackup2 = new File(rootPathBackup, item.getName());
                                                filebackup2.createNewFile();

                                                PrintWriter pw = new PrintWriter(new FileOutputStream(file1, true));
                                                //pw.print();
                                                pw.flush();

                                                item.write(file1);
                                                FileUtils.copyFile(file1, filebackup2);

                                                pw.close();
                                            }


                                            request.setAttribute("successMsg", MessageVarList.FILE_UPLOAD_SUCCESS);
                                            rd = getServletContext().getRequestDispatcher("/LoadIncomingFileUploadServlet");
                                            this.setAudittraceValue(request);
                                            this.insertFileInfo(fileInfoBean);

                                        } else {
                                            request.setAttribute("errorMsg", MessageVarList.FILE_EXISTS);
                                            rd = getServletContext().getRequestDispatcher("/LoadIncomingFileUploadServlet");
//                                            request.setAttribute("errorMsg", MessageVarList.FILE_EXISTS);

                                        }

                                    } else {
                                        request.setAttribute("fileInfoBean", fileInfoBean);
                                        rd = getServletContext().getRequestDispatcher("/LoadIncomingFileUploadServlet");
                                    }

                                } else {
                                    request.setAttribute("fileInfoBean", fileInfoBean);
                                    request.setAttribute("errorMsg", errorMessage);
                                    rd = getServletContext().getRequestDispatcher("/LoadIncomingFileUploadServlet");
                                }


                            } else {
//                                request.setAttribute("fileTypelist", fileTypeList);//*****
                                rd = getServletContext().getRequestDispatcher("/LoadIncomingFileUploadServlet");
                                request.setAttribute("errorMsg", MessageVarList.INVALID_FILE_SIZE);

                            }

                        }

                    }

                } catch (FileUploadException ex) {
//                    request.setAttribute("fileTypelist", fileTypeList);//******
                    rd = getServletContext().getRequestDispatcher("/LoadIncomingFileUploadServlet");
                    request.setAttribute("errorMsg", MessageVarList.INVALID_FILE_SIZE);
//                    throw ex;

                } catch (Exception e) {
                    rd = getServletContext().getRequestDispatcher("/LoadIncomingFileUploadServlet");
                    request.setAttribute("errorMsg", MessageVarList.ERROR_FILE_UPLOAD);
                    e.printStackTrace();
                }

            }


        } //catch session exception
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
            request.setAttribute("errorMsg", MessageVarList.ERROR_FILE_UPLOAD);
            rd = request.getRequestDispatcher("/LoadIncomingFileUploadServlet");
            ex.printStackTrace();
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * check users valid access to the page
     * @param userrole
     * @param pagecode
     * @param task
     * @return
     * @throws Exception 
     */
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

    public List findByFileType(String fileType) throws Exception {
        List filePath = null;

        try {
            FileInfoManger = new FileInfoManager();
            filePath = FileInfoManger.findByFileType(fileType);

        } catch (Exception ex) {
            throw ex;
        }
        return filePath;
    }

    private boolean processUploadedFile(HttpServletRequest request, List fileDataList) throws Exception {
        FileTypeBean fileTypeBean = new FileTypeBean();
        rECFileTypeManager = new FileTypeManager();

        boolean isValid = false;
        try {

            if (fileDataList.get(0).toString().equals(FileVarList.VISABINFILE)) {
                fileDataList.get(1).toString();

                String fileName = fileDataList.get(1).toString();

                //check file format is ok

                //get file prefix and extensions
                fileTypeBean = rECFileTypeManager.getFileTypeByFileCode(FileVarList.VISABINFILE);

                if (fileName.startsWith(fileTypeBean.getFileNamePrefix().toString())) {

                    //  if(fileName.startsWith("INCTF_DEBIT")){


                    if (fileName.endsWith(fileTypeBean.getFileExtension())) {

                        isValid = true;


                    } else {
                        request.setAttribute("errorMsg", MessageVarList.INVALID_FILE_EXTENSION);

                    }


                } else {
                    request.setAttribute("errorMsg", MessageVarList.INVALID_FILE_PREFIX);

                }

            } else {

                //  message="Invalid file type.Please select a file type";
                isValid = true;
            }

        } catch (Exception ex) {
            throw ex;

        }

        return isValid;

    }

    private int insertFileInfo(FileInfoBean fileInfoBean) throws Exception {
        int resultId = -1;
        try {

            FileInfoManger = new FileInfoManager();
            resultId = FileInfoManger.insertFileInfo(systemAuditBean, fileInfoBean);

        } catch (Exception ex) {
            throw ex;
        }
        return resultId;
    }

    private boolean isExpierDate(String postDate) {
        boolean isExpier = false;
        int expierDays = 14;
        try {

            Calendar curDate = Calendar.getInstance();
            curDate.add(Calendar.DATE, -14);

            //Date date=new Date(System.currentTimeMillis()-14*24*60*60*1000);

            int year = curDate.getTime().getYear() + 1900;
            int month = curDate.getTime().getMonth() + 1;
            int day = curDate.getTime().getDate();

            System.out.println(year);
            System.out.println(month);
            System.out.println(day);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            // Get Date 1
            Date postDat = df.parse(postDate);

            // Get Date 2
            Date expDate = df.parse(year + "-" + month + "-" + day);

            String relation;
            if (postDat.equals(expDate)) {
                relation = "the same date as";
                isExpier = false;
            } else if (postDat.before(expDate)) {
                relation = "before";
                isExpier = false;
            } else {
                relation = "after";
                isExpier = true;
            }
            System.out.println(relation);
        } catch (Exception e) {
        }
        return isExpier;
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

    private void getAllFileTypes() throws Exception {
        try {
            rECFileTypeManager = new FileTypeManager();
            fileTypeList = rECFileTypeManager.getAllFileTypes();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private String getFilePath(String osType, String fileType) throws Exception {
        String filePath = "";
        try {

            FileInfoManger = new FileInfoManager();
            commonFilePathBean = FileInfoManger.getFilePaths(osType);

            if (fileType.equals("VBIN")) {
                filePath = commonFilePathBean.getIncomingFile();
            }
            return filePath;

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void createDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private FileInfoBean setUserInputToBean(HttpServletRequest request, List fileInfoList) {
        FileInfoBean bean = null;
        String fileType = "", fileName = "";

        if (fileInfoList.get(0) != null && !fileInfoList.get(0).equals("")) {
            fileType = fileInfoList.get(0).toString();
        }
        if (fileInfoList.get(1) != null && !fileInfoList.get(1).equals("")) {
            fileName = fileInfoList.get(1).toString();
        }



        bean = new FileInfoBean();

        bean.setFileType(request.getParameter("fileInfoFileType"));
        bean.setFileName(request.getParameter("fileInfoFilePath"));

        request.setAttribute("fileTypeBean", bean);

        bean.setFileType(fileType);
        bean.setFileName(fileName);
        bean.setStatus(StatusVarList.FINIT);
        bean.setLastUpdatedUser(sessionUser.getUserName());

        return bean;
    }

    private boolean validateUserInput(FileInfoBean fileInfoBean) {
        boolean isValidate = true;

        try {

            if (fileInfoBean.getFileType().contentEquals("") || fileInfoBean.getFileType().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FILE_TYPE_EMPTY;
            } else if (fileInfoBean.getFileName().contentEquals("") || fileInfoBean.getFileName().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FILE_NAME_EMPTY;
            }

        } catch (Exception e) {
            isValidate = false;
        }
        return isValidate;
    }

    private boolean isRecordAvailable(String fileName) throws Exception {
        Boolean status = false;
        try {
            rECFileTypeManager = new FileTypeManager();
            status = rECFileTypeManager.isRecordAvailable(fileName);
        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

//    private void setAudittraceValue(HttpServletRequest request, String oldV, String newV) throws Exception {
    private void setAudittraceValue(HttpServletRequest request) throws Exception {
        try {

            systemAuditBean = new SystemAuditBean();
            String uniqueId = fileInfoBean.getFileName();


            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Upload File. File Type Code: " + uniqueId + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.EOD_PROCESS_MGT);
            systemAuditBean.setPageCode(PageVarList.INCOMING_FILE_UPLOAD);
            systemAuditBean.setTaskCode(TaskVarList.UPLOAD);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue("");
            systemAuditBean.setNewValue("");

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }

    }
}
