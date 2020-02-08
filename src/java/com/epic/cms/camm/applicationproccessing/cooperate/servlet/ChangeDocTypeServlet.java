package com.epic.cms.camm.applicationproccessing.cooperate.servlet;

import com.epic.cms.camm.applicationproccessing.cooperate.bean.DocTypeBean;
import com.epic.cms.camm.applicationproccessing.cooperate.businesslogic.CorporateCustomerManager;
import com.epic.cms.system.util.json.JSONException;
import com.epic.cms.system.util.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jeevan
 */
public class ChangeDocTypeServlet extends HttpServlet {

    private CorporateCustomerManager manager = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
                String vCategory = request.getParameter("verificationCategory1");
                List<DocTypeBean> docTypeList = new ArrayList<DocTypeBean>();
                
            try {

                manager = new CorporateCustomerManager();
                JSONObject jCombo = new JSONObject();
                JSONObject jObject = new JSONObject();
                //cardBinList = manager.changeBinProfile(productCode);
                docTypeList = manager.changeDocTypeList(vCategory);
                
                for (int i = 0; i < docTypeList.size(); i++ ) {
                    jObject.put(docTypeList.get(i).getTypeCode(), docTypeList.get(i).getDescription());
                }
                
                jCombo.put("combo1", jObject);
                out.println(jCombo);

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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(ChangeDocTypeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (JSONException ex) {
            Logger.getLogger(ChangeDocTypeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
