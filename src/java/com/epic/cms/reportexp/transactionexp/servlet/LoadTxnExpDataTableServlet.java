/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.transactionexp.servlet;

import com.epic.cms.reportexp.transactionexp.bean.TxnExpDetailsBean;
import com.epic.cms.reportexp.transactionexp.businesslogic.TransactionExpMgtManager;
import com.epic.cms.system.util.datatable.DataTableRequestParam;
import com.epic.cms.system.util.datatable.DataTablesParamUtility;
import com.epic.cms.system.util.json.JSONArray;
import com.epic.cms.system.util.json.JSONObject;
import com.epic.cms.system.util.validate.UserInputValidator;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class LoadTxnExpDataTableServlet extends HttpServlet {

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
            // Column names of the table view
            String[] cols = {"tt.descryption", "BIN", "CARDNO", "EXPIRYDATE", "txncurrency", "txnamount", "settlementdate",
                "tid", "mid", "authcode", "traceno", "txndate", "st.description", "txntime", "CREATETIME"};

            final DataTableRequestParam param = DataTablesParamUtility.getParam(request);
            final String sEcho = param.sEcho;
            int iTotalRecords = 0; // total number of records (unfiltered)
            int iTotalDisplayRecords = 0;//value will be set when code filters data by keyword
//            JSONArray data = new JSONArray(); //data that will be shown in the table

            TransactionExpMgtManager obj = new TransactionExpMgtManager();


//            List<String> sArray = new ArrayList<String>();

            // SQL binding parameters
            List<Object> sqlParams = new ArrayList<Object>();

            // Global Search
            String searchSQL = "";
            String globalSearch = "";
            if (!param.sSearchKeyword.contains("'") && !param.sSearchKeyword.contains("\"")) {
                globalSearch = " (tt.descryption LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR bin LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR cardno LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR expirydate LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR txncurrency LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR txnamount LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR settlementdate LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR tid LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR mid LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR authcode LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR traceno LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR txndate LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR st.description LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR txntime LIKE '%" + param.sSearchKeyword + "%')";
            } else {
               globalSearch = "1=1";
            }

            if (!param.sSearchKeyword.equals("") && validateSearchValue(param.sSearchKeyword)) {
                searchSQL = globalSearch;
            } else {
                searchSQL = "1=1";
                sqlParams.clear();
            }


            String fromDate = request.getParameter("fromdate");
            String toDate = request.getParameter("todate");
            String cardNo = request.getParameter("cardno");
            String merchantId = request.getParameter("merchantid");
            String terminalId = request.getParameter("terminalid");
            String bin = request.getParameter("bin");
            String txnType = request.getParameter("txntype");
            String txnStatus = request.getParameter("txnstatus");



            if (!fromDate.contentEquals("") && !toDate.contentEquals("")) {

                searchSQL += " AND CREATETIME >= to_Date('" + fromDate + "','yy-mm-dd') AND CREATETIME <= to_Date('" + toDate + "','yy-mm-dd')";

            } else {

                if (!fromDate.contentEquals("")) {

                    searchSQL += " AND CREATETIME >= to_Date('" + fromDate + "','yy-mm-dd')";
                }
                if (!toDate.contentEquals("")) {

                    searchSQL += " AND CREATETIME <= to_Date('" + toDate + "','yy-mm-dd')";
                }
            }

            if (!cardNo.contentEquals("") && !cardNo.contains("'") && !cardNo.contains("\"") && cardNo.length() > 0) {

                searchSQL += " AND CARDNO = '" + cardNo + "'";

            }
            if (!merchantId.contentEquals("") && !merchantId.contains("'") && !merchantId.contains("\"") && merchantId.length() > 0) {

                searchSQL += " AND mid = '" + merchantId + "'";

            }
            if (!terminalId.contentEquals("") && !terminalId.contains("'") && !terminalId.contains("\"") && terminalId.length() > 0) {

                searchSQL += " AND tid = '" + terminalId + "'";

            }
            if (!bin.contentEquals("") && !bin.contains("'") && !bin.contains("\"") && bin.length() > 0) {

                searchSQL += " AND bin = '" + bin + "'";

            }
            if (!txnType.contentEquals("") || txnType.length() > 0) {

                searchSQL += " AND txntypecode = '" + txnType + "'";

            }
            if (!txnStatus.contentEquals("") || txnStatus.length() > 0) {

                searchSQL += " AND status = '" + txnStatus + "'";

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

            iTotalRecords = obj.getCountTxn(searchSQL);
            List<TxnExpDetailsBean> samples = new ArrayList<TxnExpDetailsBean>();
            try {
                int end = param.iDisplayLength + param.iDisplayStart;
                samples = obj.getTxnExpDetails(searchSQL, param.iDisplayStart, end);
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
            for (TxnExpDetailsBean t : samples) {

                JSONObject object = new JSONObject();

                object.put("txntype", t.getTxnTypeDes());
                object.put("bin", t.getBin());
                object.put("cardno", t.getCardNumber());
                object.put("expirydate", t.getExpiaryDate());
                object.put("txncurrency", t.getTxnCurrency());
                object.put("txnamount", t.getTxnAmount());
                object.put("settledate", t.getSettlementDate());
                object.put("terminalid", t.getTerminalId());
                object.put("merchantid", t.getMerchantId());
                object.put("authcode", t.getAuthCode());
                object.put("traceno", t.getTraceNo());
                object.put("txndate", t.getTxnDate());
                object.put("statusdes", t.getStatusDes());
                object.put("txntime", t.getTxnTime());


                object.put("view", "<a href='#' onclick=invokeTransactionHistory('" + t.getTxnId() + "')>View</a>");


                if (t.getStatus().equals("5")) {
                    object.put("DT_RowClass", "txngradeA");
                } else if (t.getStatus().equals("6")) {
                    object.put("DT_RowClass", "txngradeB");
                } else if (t.getStatus().equals("7")) {
                    object.put("DT_RowClass", "txngradeC");
                } else if (t.getStatus().equals("8")) {
                    object.put("DT_RowClass", "txngradeD");
                } else if (t.getStatus().equals("9")) {
                    object.put("DT_RowClass", "txngradeE");
                } else if (t.getStatus().equals("10")) {
                    object.put("DT_RowClass", "txngradeF");
                } else if (t.getStatus().equals("11")) {
                    object.put("DT_RowClass", "txngradeG");
                } else if (t.getStatus().equals("12")) {
                    object.put("DT_RowClass", "txngradeH");
                } else if (t.getStatus().equals("13")) {
                    object.put("DT_RowClass", "txngradeI");
                } else if (t.getStatus().equals("16")) {
                    object.put("DT_RowClass", "txngradeJ");
                } else if (t.getStatus().equals("17")) {
                    object.put("DT_RowClass", "txngradeK");
                } else if (t.getStatus().equals("18")) {
                    object.put("DT_RowClass", "txngradeL");
                } else if (t.getStatus().equals("19")) {
                    object.put("DT_RowClass", "txngradeM");
                } else if (t.getStatus().equals("20")) {
                    object.put("DT_RowClass", "txngradeN");
                }

                row.put(object);
            }
            jsonResponse.put("aaData", row);

            response.setContentType("application/json");
            response.getWriter().print(jsonResponse.toString());

        } catch (Exception ex) {
            Logger.getLogger(LoadTxnExpDataTableServlet.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        } finally {
            out.close();
        }
    }

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);


        return (dateFormat.format(convertedDate));



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
