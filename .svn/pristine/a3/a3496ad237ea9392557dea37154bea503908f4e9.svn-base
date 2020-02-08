/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.assigndata.servlet;

import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.CorporateAssignManager;
import com.epic.cms.system.util.json.JSONException;
import com.epic.cms.system.util.json.JSONObject;
import com.epic.cms.system.util.variable.StatusVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author asela
 */
public class LoadIdentificationTypeServlet extends HttpServlet {

    private CorporateAssignManager appAssignManager;
    private HashMap<String, String> identificationList;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            String cardcategory = request.getParameter("cardcategory");
            String category = "";
            if (cardcategory.equals("E")) {
                category = StatusVarList.ESTABLISHMENT_CATEGORY;
            }else if(cardcategory.equals("C")){
                category = StatusVarList.CORPORATE_CATEGORY;
            }
            
                JSONObject jCombo = new JSONObject();// if there are several combo box to load
                //load combo box data
                JSONObject jObject = new JSONObject();
                
                   //  jObject.put("", "-------------SELECT-------------");

                this.getAllIdentificationTypeByVerificationCategory(category);
                
                for(Map.Entry<String,String> entry:identificationList.entrySet()){
                    jObject.put(entry.getKey(), entry.getValue());                    
                }

                jCombo.put("combo1", jObject);
                out.print(jCombo);                

        } finally {
            out.close();
        }
    }

    private void getAllIdentificationTypeByVerificationCategory(String cardcategory) throws Exception {
        try {
            appAssignManager = new CorporateAssignManager();
            identificationList = appAssignManager.getAllIdentificationTypeByVerificationCategory(cardcategory);

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
        try {
            try {
                processRequest(request, response);
            } catch (JSONException ex) {
                Logger.getLogger(LoadIdentificationTypeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(LoadIdentificationTypeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            try {
                processRequest(request, response);
            } catch (JSONException ex) {
                Logger.getLogger(LoadIdentificationTypeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(LoadIdentificationTypeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
