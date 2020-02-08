/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.StandingOrderTypesManager;
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
 * @author asitha_l
 */
public class LoadBankBranchServlet extends HttpServlet {

    private StandingOrderTypesManager standingOrderTypesManager = null;
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
            String bankCode = request.getParameter("bankCode");
            String commonParam = request.getParameter("commonParam");
            
            Map<String, String> branchList = new HashMap<String, String>();
            Map<String, String> payTypeList = new HashMap<String, String>();
            try {
                standingOrderTypesManager = new StandingOrderTypesManager();               
                JSONObject jCombo = new JSONObject();// if there are several combo box to load
                
                JSONObject jObject1 = new JSONObject();
                JSONObject jObject2 = new JSONObject();
                
                branchList = standingOrderTypesManager.getBranchList(bankCode);
                payTypeList=standingOrderTypesManager.getPayTypeList(bankCode,commonParam);
                for (Map.Entry<String, String> entry : branchList.entrySet()) {
                    jObject1.put(entry.getKey(), entry.getValue());                   
                } 
                jObject2.put("", "---------SELECT----------");
                for (Map.Entry<String, String> entry : payTypeList.entrySet()) {
                    jObject2.put(entry.getKey(), entry.getValue());                   
                } 
                
               jCombo.put("combo1", jObject1); 
               jCombo.put("combo2", jObject2); 
               out.print(jCombo);
            } catch (Exception e) {             
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
