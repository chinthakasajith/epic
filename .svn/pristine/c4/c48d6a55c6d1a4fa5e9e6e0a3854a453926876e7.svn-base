/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.terminalmgt.terminaldata.servlet;

import com.epic.cms.mtmm.terminalmgt.terminaldata.businesslogic.TerminalDataCaptureManager;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mahesh_m
 */
public class LoadmerchantSearchServlet extends HttpServlet {

    private RequestDispatcher rd;
    private String url = "/mtmm/terminalmgt/merchantsearch.jsp";
    private String url2 = "/mtmm/terminalmgt/terminalallocationdeallocation.jsp";
    TerminalDataCaptureManager terminalManager;
    private TerminalDataCaptureManager allocationmanager = null;
    private HashMap<String, String> terminalStatusList = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String terminalId = request.getParameter("id");
            this.getTerminalStatus();

            request.setAttribute("terminalStatusList", terminalStatusList);

            request.setAttribute("terminalId", terminalId);

            if (isTerminalActive(terminalId)) {
                request.setAttribute("errorMsg", MessageVarList.TERMINAL_ACTIVE);
                rd = getServletContext().getRequestDispatcher(url2);
            } else {
                rd = getServletContext().getRequestDispatcher(url);
            }

            
            rd.forward(request, response);
        } finally {
            out.close();
        }
    }

    private void getTerminalStatus() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            terminalStatusList = terminalManager.getTerminalStatus();
        } catch (Exception e) {
        }

    }

     public Boolean isTerminalActive(String terminalId) throws Exception {
        allocationmanager = new TerminalDataCaptureManager();
        Boolean status = false;

        try {
            status = allocationmanager.isTerminalActive(terminalId);
        } catch (Exception e) {
            throw e;
        }

        return status;
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoadmerchantSearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoadmerchantSearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
