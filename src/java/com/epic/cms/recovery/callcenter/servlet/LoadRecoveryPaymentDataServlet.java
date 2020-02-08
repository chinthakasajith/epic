/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.recovery.callcenter.servlet;

import com.epic.cms.recovery.callcenter.bean.RecoveryPaymentDetailsBean;
import com.epic.cms.recovery.callcenter.businesslogic.RecoveryCallCenterManager;
import com.epic.cms.system.util.datatable.DataTableRequestParam;
import com.epic.cms.system.util.datatable.DataTablesParamUtility;
import com.epic.cms.system.util.json.JSONArray;
import com.epic.cms.system.util.json.JSONObject;
import com.epic.cms.system.util.validate.UserInputValidator;
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
 * @author chanuka
 */
public class LoadRecoveryPaymentDataServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            // Column names of the table view
            String[] cols = {"pm.cardnumber","pm.paymentdate", "pm.paymenttype", "pt.description", "pm.currencytype", "cr.description", "pm.amount", "pm.paymentstatus", "st.description"};


            final DataTableRequestParam param = DataTablesParamUtility.getParam(request);
            final String sEcho = param.sEcho;
            int iTotalRecords = 0; // total number of records (unfiltered)
            int iTotalDisplayRecords = 0;//value will be set when code filters data by keyword
//            JSONArray data = new JSONArray(); //data that will be shown in the table

            RecoveryCallCenterManager obj = new RecoveryCallCenterManager();


//            List<String> sArray = new ArrayList<String>();

            // SQL binding parameters
            List<Object> sqlParams = new ArrayList<Object>();

            // Global Search
            String searchSQL = "";
            String globalSearch = "";
            if (!param.sSearchKeyword.contains("'") && !param.sSearchKeyword.contains("\"")) {
                globalSearch = " (st.description LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR pm.cardNumber LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR pm.amount LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR cr.description LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR pt.description LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR pm.paymentdate LIKE '%" + param.sSearchKeyword + "%')";
            } else {
                globalSearch = "1=1";
            }

            if (!param.sSearchKeyword.equals("") && validateSearchValue(param.sSearchKeyword)) {
                searchSQL = globalSearch;
            } else {
                searchSQL = "1=1";
                sqlParams.clear();
            }


            String cardNumber = request.getParameter("cardnumber");


            if (!cardNumber.contentEquals("") && !cardNumber.contentEquals("")) {

                searchSQL += " AND pm.cardnumber = " + cardNumber;

            }



            if (param.iDisplayStart < 0) {
                param.iDisplayStart = 0;
            }

            if (param.iDisplayLength < 10 || param.iDisplayLength > 100) {
                param.iDisplayLength = 10;
            }

            // Ordering and limiting for pagination
            searchSQL += " ORDER BY " + cols[param.iSortCol[0]] + " " + param.sSortDir[0];
//            searchSQL += " LIMIT " + param.iDisplayStart + ", " + param.iDisplayLength;

            iTotalRecords = obj.getCountPayments(searchSQL);
            List<RecoveryPaymentDetailsBean> samples = new ArrayList<RecoveryPaymentDetailsBean>();
            try {
                int end = param.iDisplayLength + param.iDisplayStart;
                samples = obj.getRecoveryPaymentDetails(searchSQL, param.iDisplayStart, end);
                if (param.sSearchKeyword != "") {
                    iTotalDisplayRecords = iTotalRecords;
                } else {
                    iTotalDisplayRecords = iTotalRecords;
                }
            } catch (Exception e) {//if calling db gives exception
                iTotalDisplayRecords = iTotalRecords;
            }
            // Building the JSON response
            JSONObject jsonResponse = new JSONObject();

            jsonResponse.put("sEcho", sEcho);
            jsonResponse.put("iTotalRecords", iTotalRecords);
            jsonResponse.put("iTotalDisplayRecords", iTotalDisplayRecords);

            JSONArray row = new JSONArray();
            for (RecoveryPaymentDetailsBean t : samples) {

                JSONObject object = new JSONObject();

                object.put("cardNumber", t.getCardNumber());
                object.put("paymentTypeDes", t.getPaymentTypeDes());
                object.put("currencyTypeDes", t.getCurrencyTypeDes());
                object.put("amount", t.getAmount());
                object.put("paymentDate", t.getPaymentDate());
                object.put("paymentStatusDes", t.getPaymentStatusDes());


//                object.put("view", "<a href='#' onclick=invokeTransactionHistory('" + t.getTxnAmount() + "')>View</a>");


                row.put(object);
            }

            jsonResponse.put("aaData", row);

            response.setContentType("application/json");
            response.getWriter().print(jsonResponse.toString());

        } catch (Exception ex) {
            Logger.getLogger(LoadRecoveryTransactionDataServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        } finally {
            out.close();
        }
    }

    /**
     * validate user input value
     * @param sParam
     * @return
     * @throws Exception 
     */
    private boolean validateSearchValue(String sParam) {
        boolean flag = true;
        try {

            if (!UserInputValidator.isCorrectString(sParam)) {
                flag = false;
            }

            return flag;

        } catch (Exception e) {
            flag = false;
            return flag;
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
