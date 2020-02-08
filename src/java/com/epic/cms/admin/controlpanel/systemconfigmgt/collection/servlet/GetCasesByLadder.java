/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.collection.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.CaseTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.businesslogic.LadderManager;
import com.epic.cms.system.util.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ruwan_e
 */
public class GetCasesByLadder extends HttpServlet {

    private LadderManager ladderManager;
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
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
            String ladderCode = request.getParameter("ladderCode");

            List<CaseTypeBean> cases;
            try {
                ladderManager = new LadderManager();
                JSONObject jObject = new JSONObject();
                cases = ladderManager.getCasesByLadderCode(ladderCode);

                for (Iterator<CaseTypeBean> it = cases.iterator(); it.hasNext();) {
                    CaseTypeBean caseTypeBean = it.next();
                    jObject.put(caseTypeBean.getCaseTypeCode(), caseTypeBean.getDescription());                    
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
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
}
