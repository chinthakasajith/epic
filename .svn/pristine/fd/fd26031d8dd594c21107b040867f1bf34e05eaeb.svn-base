/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.cardembossing.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.cpmm.cardembossing.businesslogic.CardEmbossingMgtManager;
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
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chanuka
 */
public class BulkDownloadCardEmbossingFileServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CardEmbossingMgtManager cardEmbossingManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private SessionUser sessionUser;
    private String fileType = null;
    private String fileName = null;
    private String cardType = null;
    private String cardProduct = null;
    private CommonFilePathBean commonFilePathBean = null;
    private String url = "/cpmm/cardembossing/bulkcardembossingfiledownload.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
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
                String pageCode = PageVarList.BULK_EMBOSS;
                String taskCode = TaskVarList.DOWNLOAD;


                //check whethre userrole have an access for this page and task
                if (!this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();
                }

            } catch (AccessDeniedException adex) {
                throw adex;

            }


            fileType = request.getParameter("filetype");
            cardType = request.getParameter("cardtype");
            cardProduct = request.getParameter("cardproduct");
            fileName = request.getParameter("filename");

            if (this.getCardEmbossFileDowloadStatus(fileName).equals("NO")) {


                BufferedInputStream buf = null;
                ServletOutputStream myOut = null;

                try {


                    File myfile = null;

                    String osType = this.getOS_Type();

                    if (osType.equals("WINDOWS")) {
                        String rootPath = this.getFilePaths(osType, fileType);
                        myfile = new File(rootPath + cardType + "/" + cardProduct + "/" + fileName);

                    } else if (osType.equals("LINUX")) {

                        String rootPath = this.getFilePaths(osType, fileType);
                        myfile = new File(rootPath + cardType + "/" + cardProduct + "/" + fileName);

                    }





                    FileInputStream input = new FileInputStream(myfile);

                    myOut = response.getOutputStream();

                    //set response headers
                    response.setContentType("text/plain");

                    response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

                    response.setContentLength((int) myfile.length());


                    buf = new BufferedInputStream(input);
                    int readBytes = 0;

                    //read from the file; write to the ServletOutputStream
                    while ((readBytes = buf.read()) != -1) {
                        myOut.write(readBytes);
                    }

                    this.setAudittraceValue(request, cardType, cardProduct, fileName);
                    this.updateCardEmbossFileDowloadStatus(systemAuditBean, fileName);



                } catch (IOException ioe) {

                    throw new ServletException(ioe.getMessage());

                } catch (Exception ee) {
                    throw ee;
                } finally {

                    if (myOut != null) {
                        myOut.close();
                    }
                    if (buf != null) {
                        buf.close();
                    }
                }

            } else {

                request.setAttribute("errorMsg", MessageVarList.ALREADY_DOWNLOAD_EMBOSS_FILE);
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
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
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (ServletException ioe) {

            request.setAttribute("errorMsg", MessageVarList.NO_FILE_DOWNLOAD);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);

        } catch (Exception ex) {

            request.setAttribute("errorMsg", MessageVarList.ERROR_DOWNLOAD_EMBOSS_FILE);
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);

        } finally {
//            rd.forward(request, response);
//            out.close();
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

    private boolean updateCardEmbossFileDowloadStatus(SystemAuditBean systemAuditBean, String fileName) throws Exception {
        boolean flag = false;
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            flag = cardEmbossingManager.updateCardEmbossFileDowloadStatus(fileName, systemAuditBean);
            return flag;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private String getCardEmbossFileDowloadStatus(String fileName) throws Exception {
        String downloadStatus = "";
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            downloadStatus = cardEmbossingManager.getCardEmbossFileDowloadStatus(fileName);
            return downloadStatus;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private String getFilePaths(String osType, String fileType) throws Exception {

        String filePath = "";
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            commonFilePathBean = cardEmbossingManager.getFilePaths(osType);


            if (fileType.equals("EMBOSS")) {
                filePath = commonFilePathBean.getEmbossFile();
            }
            if (fileType.equals("EMV")) {
                filePath = commonFilePathBean.getEmvFile();
            }
            if (fileType.equals("ENCODE")) {
                filePath = commonFilePathBean.getEncodefile();
            }
            if (fileType.equals("STATEMENT")) {
                filePath = commonFilePathBean.getStatement();
            }
            if (fileType.equals("LETTERS")) {
                filePath = commonFilePathBean.getLetters();
            }
            if (fileType.equals("EOD")) {
                filePath = commonFilePathBean.getEodFile();
            }
            if (fileType.equals("MOD")) {
                filePath = commonFilePathBean.getModFile();
            }
            if (fileType.equals("SCANNEDAPPLICATION")) {
                filePath = commonFilePathBean.getScanApplication();
            }
            if (fileType.equals("SCANNEDDOCUMENT")) {
                filePath = commonFilePathBean.getScandocument();
            }


            return filePath;

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setAudittraceValue(HttpServletRequest request, String cardType, String cardProduct, String fileName) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Download bulk card emboss file, file name: " + fileName + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CPMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.EMBOSSINGMGT);
            systemAuditBean.setPageCode(PageVarList.BULK_EMBOSS);
            systemAuditBean.setTaskCode(TaskVarList.DOWNLOAD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(fileName);
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
