/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author prageeth_s
 */
public class LoadPrimaryBillingDetailsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private CaptureDataManager manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                String primaryApplicationId = request.getParameter("primaryApplicationId");
                String primaryCardNumber=request.getParameter("primaryCardNumber");

                if (!primaryApplicationId.equals("")) {
                    String result = getPrimaryBillingDetailsByAppId(primaryApplicationId);
                    if (!result.equals("")) {
                        out.print(result);
                    }
                }else if(!primaryCardNumber.equals("")){
                    String result=getPrimaryBillingDetailsByCardNum(primaryCardNumber);
                    if(!request.equals("")){
                        out.print(result);
                    }
                }

            } finally {
                out.close();
            }

        } catch (Exception e) {

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

    private String getPrimaryBillingDetailsByAppId(String primaryApplicationId) throws Exception {
        String result = "";
        try {
            manager = new CaptureDataManager();
            result = manager.getPrimaryBillingDetailsByAppId(primaryApplicationId);

        } catch (Exception ex) {
            throw ex;
        }
        return result;
    }
    private String getPrimaryBillingDetailsByCardNum(String primaryCardNumber) throws Exception {
        String result = "";
        try {
            manager = new CaptureDataManager();
            result = manager.getPrimaryBillingDetailsByCardNum(primaryCardNumber);

        } catch (Exception ex) {
            throw ex;
        }
        return result;
    }

}