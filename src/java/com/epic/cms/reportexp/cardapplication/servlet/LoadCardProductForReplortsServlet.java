/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.reportexp.cardapplication.bean.CardprdctBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationPinGenAndMailManager;
import com.epic.cms.system.util.json.JSONObject;
import com.epic.cms.system.util.logs.LogFileCreator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jeevan
 */
public class LoadCardProductForReplortsServlet extends HttpServlet {

    List<CardprdctBean> cardProdctList = null;
    private ApplicationPinGenAndMailManager appPinMgr;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();
            String cardType = request.getParameter("cardType");
            
            cardProdctList = new ArrayList<CardprdctBean>();
            
            
            JSONObject jCombo = new JSONObject();// if there are several combo box to load
                //load combo box data
                JSONObject jObject = new JSONObject();
                cardProdctList = appPinMgr.getCardProductsByType(cardType);
                     jObject.put("", "-------------ALL-------------");
                for (int i = 0; i < cardProdctList.size(); i++) {
                    jObject.put(cardProdctList.get(i).getProductCode(), cardProdctList.get(i).getDescription());
                }

                jCombo.put("combo1", jObject);
                out.print(jCombo);

        } catch (Exception e) {
            LogFileCreator.writeErrorToLog(e);
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
