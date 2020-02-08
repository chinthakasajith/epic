/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.camm.applicationconfirmation.businesslogic.ApplicationConfirmationManager;
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
 * @author mahesh_m
 */
public class LoadAccountTemplatesServlet extends HttpServlet {

    private ApplicationConfirmationManager confirmationManager;
    private String cardType = null;
    private String staffStatus = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            confirmationManager = new ApplicationConfirmationManager();


            String customerTemplateId = request.getParameter("cValue");
            String applicationId = request.getParameter("applicationId");
            String currency = request.getParameter("currency");
            String appType=request.getParameter("cardCategoryCode");
            try {
                        staffStatus = confirmationManager.getStaffStatus(applicationId);
                        cardType = confirmationManager.getCardType(applicationId, appType);
            } catch (Exception e) {
                // no need to do anything
            }


            List<AccountTempBean> accountTemplateList = new ArrayList<AccountTempBean>();

            try {

                JSONObject jCombo = new JSONObject();// if there are several combo box to load
                //load combo box data
                JSONObject jObject = new JSONObject();
                accountTemplateList = confirmationManager.getAccounttemplates(staffStatus, cardType, customerTemplateId,currency);

                //jObject.put("", "-------------SELECT-------------");
                for (int i = 0; i < accountTemplateList.size(); i++) {
                    jObject.put(accountTemplateList.get(i).getTemplateCode(), accountTemplateList.get(i).getTemplateName());
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
