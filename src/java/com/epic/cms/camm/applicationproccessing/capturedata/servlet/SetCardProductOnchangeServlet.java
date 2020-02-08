/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.system.util.json.JSONObject;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nalin
 */
public class SetCardProductOnchangeServlet extends HttpServlet {

    private RequestDispatcher rd;
    private CaptureDataManager captureDataManager;
    private List<CardProductBean> cardProductList = null;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;

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
        sessionObj = request.getSession(false);
        try {
            sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
            String cardTypeCode = request.getParameter("cardTypeCode");

            cardProductList = new ArrayList<CardProductBean>();
            try {
                captureDataManager = new CaptureDataManager();
                JSONObject jCombo = new JSONObject();// if there are several combo box to load
                //load combo box data
                JSONObject jObject = new JSONObject();
                cardProductList = captureDataManager.getAllCardProductByCardType(cardTypeCode);
                sessionVarlist.setCardProductMgtList(cardProductList);
                //jObject.put("", "");

                // System.out.println("   ******* SETCARDPRODUCTONCHANGE ******");

                for (int i = 0; i < cardProductList.size(); i++) {
                    jObject.put(cardProductList.get(i).getProductCode(), cardProductList.get(i).getDescription());
                }

                jCombo.put("combo1", jObject);
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
