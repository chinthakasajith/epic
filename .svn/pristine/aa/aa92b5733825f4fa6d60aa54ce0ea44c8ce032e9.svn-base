/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
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
import java.io.IOException;
import java.io.OutputStream;
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
 * @author chanuka
 */
public class LoadUploadedImageServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String url = "/camm/documentverification/documentviewhome.jsp";
    private File imageFile, encryptedFile = null;
    private String key = "Mary has one cat";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // PrintWriter out = response.getWriter();
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
            String documentType = request.getParameter("documentType").trim();
            String verificationCategory = request.getParameter("verificationCategory").trim();
            String fileName = request.getParameter("fileName").trim();

            String osType = this.getOS_Type();
            CardEmbossingMgtManager filePathMgt = new CardEmbossingMgtManager();
            CommonFilePathBean commonFilePathBean = filePathMgt.getFilePaths(osType);

            if (osType.equals("WINDOWS")) {
                // set the dynamic path..
                //decrypt
                encryptedFile = new File(commonFilePathBean.getScandocument() + applicationId + "\\" + verificationCategory + "\\" + documentType + "\\" + fileName);
                imageFile = new File(commonFilePathBean.getScandocument() + applicationId + "\\" + verificationCategory + "\\" + documentType + "\\" + fileName);
                
                try {
                    CryptoUtils.decrypt(key, encryptedFile, imageFile);
                } catch (CryptoException ex) {
                    throw ex;
                }
            } else if (osType.equals("LINUX")) {
                // set the dynamic path..
                //decrypt
                encryptedFile = new File(commonFilePathBean.getScandocument() + applicationId + "/" + verificationCategory + "/" + documentType + "/" + fileName);
                imageFile=new File(commonFilePathBean.getScandocument() + applicationId + "/" + verificationCategory + "/" + documentType + "/" + fileName);
                
                try {
                    CryptoUtils.decrypt(key, encryptedFile, imageFile);
                } catch (CryptoException ex) {
                    throw ex;
                }
            }

            FileInputStream fis = new FileInputStream(imageFile);

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
            response.setContentType("image/jpg");
            OutputStream o = response.getOutputStream();
            o.write(bytes);
            o.flush();
            o.close();
            //encrypt
            try {
                CryptoUtils.encrypt(key, imageFile, encryptedFile);
            } catch (CryptoException ex) {
                throw ex;
            }

        } catch (Exception ex) {
        } finally {
//            out.close();
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
