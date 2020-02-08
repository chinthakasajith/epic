/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.cpmm.cardembossing.businesslogic.CardEmbossingMgtManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.fileencrypt.CryptoException;
import com.epic.cms.system.util.fileencrypt.CryptoUtils;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
public class LoadUploadedImageToCreditOfficerServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private File imageFile = null;
    private FileInputStream fis = null;
    private OutputStream o = null;
    private String categoryCode = null;
    private CardEmbossingMgtManager fileNameManager = null;
    private DocumentUploadBean uploadBean = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//       PrintWriter out = response.getWriter();
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


            String applicationId = request.getParameter("applicationId").trim();
            String docType = request.getParameter("documentType").trim();

            if (docType.equals("nic")) {
                categoryCode = "ID";
            } else if (docType.equals("Salary Slip")) {
                categoryCode = "EMP";
            } else if (docType.equals("Confimation Letter")) {
                categoryCode = "EMP";
            } else if (docType.equals("Copy of Utility Bill")) {
                categoryCode = "RES";
            } else if (docType.equals("Copy Of Marriage Certificate")) {
                categoryCode = "SUP";
            } else if (docType.equals("Copy Of Birth Certificate")) {
                categoryCode = "SUP";
            }

            String osType = this.getOS_Type();
            CardEmbossingMgtManager filePathMgt = new CardEmbossingMgtManager();
            CommonFilePathBean commonFilePathBean = filePathMgt.getFilePaths(osType);
            this.getFileName(applicationId, categoryCode);

            imageFile = new File(commonFilePathBean.getScandocument() + applicationId + "/" + uploadBean.getVerificationCategory() + "/" + uploadBean.getDocumentType() + "/" + uploadBean.getFileName());
            
            try {
                fis = new FileInputStream(imageFile);

            } catch (FileNotFoundException ex) {

                rd = getServletContext().getRequestDispatcher("/LoadCreditofficerDetailsServlet?applicationid=" + applicationId);
                request.setAttribute("errorMsg", "Image not Found");
                rd.forward(request, response);
//                out.close();
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                }
            } catch (IOException ex) {
                throw ex;
            }
            byte[] bytes = bos.toByteArray();



            //-----------------------------------------------------

            try {
                response.setContentType("image/jpg");
                o = response.getOutputStream();
                o.write(bytes);
                o.flush();
                o.close();

            } catch (Exception e) {
//                e.printStackTrace();
            }

        } catch (Exception ex) {
        } finally {
//           out.close();
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

    private void getFileName(String applicationId, String categoryCode) throws Exception {

        try {
            uploadBean = new DocumentUploadBean();
            fileNameManager = new CardEmbossingMgtManager();
            uploadBean = fileNameManager.getFileName(applicationId, categoryCode);

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
