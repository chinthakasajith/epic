/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantcustomer.servlet;

import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantCustomerBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.businesslogic.MerchantCustomerManager;
import com.epic.cms.system.util.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nalin
 */
public class LordMerchantCustomerBranchServlet extends HttpServlet {

    private RequestDispatcher rd;
    private MerchantCustomerManager mercusManager;
    private MerchantCustomerBean merchantBean;

    /** 
    
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
            merchantBean = new MerchantCustomerBean();
            merchantBean.setBankCode(bankCode);
            List<MerchantCustomerBean> branchList = new ArrayList<MerchantCustomerBean>();
            try {
                mercusManager = new MerchantCustomerManager();
                JSONObject jCombo = new JSONObject();// if there are several combo box to load
                //load combo box data
                JSONObject jObject = new JSONObject();
                branchList = mercusManager.getBankBranchList(merchantBean);
                //+jObject.put("", "--SELECT--");
                
                for (int i = 0; i < branchList.size(); i++) {
                    jObject.put(branchList.get(i).getBranchCode(), branchList.get(i).getBranchName());
                }

                jCombo.put("combo1", jObject);
                out.print(jCombo);
            } catch (Exception e) {
            }


        } finally {
            out.close();
        }
    }

    //////////////////////////////////////
//                        String BankCode = request.getParameter("selectBank");
//                        mercusManager = new MerchantCustomerManager();
//                        merchantBean = new MerchantCustomerBean();
//                        merchantBean.setMerchantCustomerNumber(BankCode);
//                        branchList = new ArrayList<MerchantCustomerBean>();
//                        branchList = mercusManager.getBankBranchList(merchantBean);
//                        
//                        request.setAttribute( "branchList",branchList);
//                        rd = request.getRequestDispatcher(url);
    /////////////////////////////////////////////////////
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
