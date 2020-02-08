/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.camm.applicationconfirmation.businesslogic.DebitApplicationConfirmationManager;
import com.epic.cms.system.util.json.JSONObject;
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
 * @author badrika
 */
public class LoadDebitCardTemplatesServlet extends HttpServlet {
    private DebitApplicationConfirmationManager debitAppConfirmManager;

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
            

            String productCode = request.getParameter("pValue");
            String accountTemId = request.getParameter("aValue");
           
            List<CardTemplateBean> cardTemplateList = new ArrayList<CardTemplateBean>();

            try {
                debitAppConfirmManager = new DebitApplicationConfirmationManager();
                JSONObject jCombo = new JSONObject();// if there are several combo box to load
                //load combo box data
                JSONObject jObject = new JSONObject();
                cardTemplateList = debitAppConfirmManager.getDebitCardTemplates(productCode, accountTemId);

                jObject.put("", "--SELECT--");
                for (int i = 0; i < cardTemplateList.size(); i++) {
                    jObject.put(cardTemplateList.get(i).getTemplateCode(), cardTemplateList.get(i).getTemplateName());
                }

                jCombo.put("combo1", jObject);
                out.print(jCombo);
            } catch (Exception e) {
                 // no need to do anything
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
