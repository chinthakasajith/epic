/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.txnadjustment.servlet;

import com.epic.cms.backoffice.txnadjustment.businesslogic.TransactionAdjustmentManager;
import com.epic.cms.system.util.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ruwan_e
 */
public class GetAdjustmentTypeListServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    TransactionAdjustmentManager transactionAdjustmentManager = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String adjustmentType = request.getParameter("adjustmentType");

            Map<String, String> txTypeMap = new HashMap<String, String>();
            try {
                transactionAdjustmentManager = new TransactionAdjustmentManager();

                JSONObject jObject = new JSONObject();

                if (adjustmentType.equals("1")) {
                    txTypeMap = transactionAdjustmentManager.getTxTypeMap();
                } else if (adjustmentType.equals("2")) {
                    txTypeMap = transactionAdjustmentManager.getFeeTypeMap();
                } else if (adjustmentType.equals("3")) {
                    txTypeMap = new HashMap<String, String>();
                    txTypeMap.put("test_1", "Test 1");
                    txTypeMap.put("test_2", "Test 2");
                }
                
                for (Map.Entry<String, String> entry : txTypeMap.entrySet()) {
                    jObject.put(entry.getKey(), entry.getValue());
                }

                out.print(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } finally {
            out.close();
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
