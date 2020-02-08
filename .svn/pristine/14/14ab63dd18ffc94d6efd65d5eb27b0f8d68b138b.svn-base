/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.logmanagement.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ruwan_e
 */
public class DownloadLogFile extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private static final int BUFSIZE = 4096;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
        
        ServletOutputStream outStream = null;
        try {
            String filePath = request.getParameter("file");
            File file = new File(filePath);
            int length = 0;
            outStream = response.getOutputStream();
            ServletContext context = getServletConfig().getServletContext();
            String mimetype = context.getMimeType(filePath);

            // sets response content type
            if (mimetype == null) {
                mimetype = "application/octet-stream";
            }
            response.setContentType(mimetype);
            response.setContentLength((int) file.length());
            String fileName = (new File(filePath)).getName();

            // sets HTTP header
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            byte[] byteBuffer = new byte[BUFSIZE];
            DataInputStream in = new DataInputStream(new FileInputStream(file));

            // reads the file's bytes and writes them to the response stream
            while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
                outStream.write(byteBuffer, 0, length);
            }
        } finally {
            outStream.close();
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
