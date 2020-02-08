/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfirmation.businesslogic.ApplicationConfirmationManager;
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
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mahesh_m
 */
public class LoadSignatureForCheckingServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private String applicationId;
    private ApplicationConfirmationManager confirmManager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // PrintWriter out = response.getWriter();
        try {
            applicationId = request.getParameter("applicationId");
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

            confirmManager = new ApplicationConfirmationManager();
            String osType = getOS_Type();
            String signatureHomeDir = confirmManager.getSignatureDirectory(osType);

            File encryptedFile = new File(signatureHomeDir + applicationId + File.separator + "SIGNATURE" + File.separator + applicationId + "_SIGNATURE.JPG");
            System.out.println(encryptedFile.exists() + "!!");
            //decrypt for view
            String key = "Mary has one cat";
            File imageFile = new File(signatureHomeDir + applicationId + File.separator + "SIGNATURE" + File.separator + applicationId + "_SIGNATURE.JPG");
            try {
                CryptoUtils.decrypt(key, encryptedFile, imageFile);
            } catch (CryptoException ex) {
                throw ex;
            }
            FileInputStream fis = new FileInputStream(imageFile);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                    //no doubt here is 0
                /*Writes len bytes from the specified byte array starting at offset 
                     off to this byte array output stream.*/
                    System.out.println("read " + readNum + " bytes,");
                }
            } catch (IOException ex) {
//                                    Logger.getLogger(ConvertImage.class.getName()).log(Level.SEVERE, null, ex);
            }
            byte[] bytes = bos.toByteArray();

            //-----------------------------------------------------
            response.setContentType("image/jpg");
            ServletOutputStream o = response.getOutputStream();
            o.write(bytes);
            o.flush();
            o.close();
            //encrypt
            try {
                CryptoUtils.encrypt(key, imageFile, encryptedFile);
            } catch (CryptoException ex) {
                throw ex;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //out.close();
        }
    }

    public static String getOS_Type() throws Exception {

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
