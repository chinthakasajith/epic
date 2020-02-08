/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.callcentersearch.servlet;

import com.epic.cms.callcenter.callcentersearch.bean.TransactionBean;
import com.epic.cms.callcenter.callcentersearch.businesslogic.CallCenterMgtManager;
import com.epic.cms.system.util.datatable.DataTableRequestParam;
import com.epic.cms.system.util.datatable.DataTablesParamUtility;
import com.epic.cms.system.util.json.JSONArray;
import com.epic.cms.system.util.json.JSONObject;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author nalin
 */
public class LoadMerchantLocationTxnExpServlet extends HttpServlet {

    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private String merchantId;

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

            sessionObj = request.getSession(false);
            sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
            sessionUser = sessionVarlist.getCMSSessionUser();

            merchantId = sessionVarlist.getMerchantId();

            // Column names of the table view
            String[] cols = {"OL.TXNID","TT.DESCRYPTION", "BIN", "CARDNO", "EXPIRYDATE", "TXNCURRENCY", "TXNAMOUNT", "SETTLEMENTDATE",
                "TID", "MID", "AUTHCODE", "TRACENO", "TXNDATE", "ST.DESCRIPTION", "TXNTIME", "CREATETIME"};

            final DataTableRequestParam param = DataTablesParamUtility.getParam(request);
            final String sEcho = param.sEcho;
            int iTotalRecords = 0; // total number of records (unfiltered)
            int iTotalDisplayRecords = 0;//value will be set when code filters data by keyword
//            JSONArray data = new JSONArray(); //data that will be shown in the table

            CallCenterMgtManager obj = new CallCenterMgtManager();


//            List<String> sArray = new ArrayList<String>();

            // SQL binding parameters
            List<Object> sqlParams = new ArrayList<Object>();

            // Global Search
            String searchSQL = "";
            String globalSearch = " (UPPER(TT.DESCRYPTION) LIKE '%" + param.sSearchKeyword.toUpperCase() + "%'"
                    + " OR BIN LIKE '%" + param.sSearchKeyword + "%'"
                    + " OR CARDNO LIKE '%" + param.sSearchKeyword + "%'"
                    + " OR EXPIRYDATE LIKE '%" + param.sSearchKeyword + "%'"
                   // + " OR TXNCURRENCY LIKE '%" + param.sSearchKeyword + "%'"
                    + " OR TXNAMOUNT LIKE '%" + param.sSearchKeyword + "%'"
                   // + " OR SETTLEMENTDATE LIKE '%" + param.sSearchKeyword + "%'"
                   // + " OR TID LIKE '%" + param.sSearchKeyword + "%'"
                   // + " OR MID LIKE '%" + param.sSearchKeyword + "%'"
                    //+ " OR AUTHCODE LIKE '%" + param.sSearchKeyword + "%'"
                   // + " OR TRACENO LIKE '%" + param.sSearchKeyword + "%'"
                    + " OR TXNDATE LIKE '%" + param.sSearchKeyword + "%'"
                    + " OR UPPER(ST.DESCRIPTION) LIKE '%" + param.sSearchKeyword.toUpperCase() + "%'"
                    + " OR OL.TXNID LIKE '%" + param.sSearchKeyword + "%'"
                    + " OR TXNTIME LIKE '%" + param.sSearchKeyword + "%')";

            if (!param.sSearchKeyword.equals("")) {
                searchSQL = globalSearch;
            } else {
                searchSQL = "1=1";
                sqlParams.clear();
            }

            if (param.iDisplayStart < 0) {
                param.iDisplayStart = 0;
            }

            if (param.iDisplayLength < 10 || param.iDisplayLength > 100) {
                param.iDisplayLength = 10;
            }

            // Ordering and limiting for pagination
            searchSQL += " ORDER BY " + cols[param.iSortCol[0]] + " " + (param.sSortDir[0].equalsIgnoreCase("asc") ? "desc" : "asc");
//            searchSQL += " LIMIT " + param.iDisplayStart + ", " + param.iDisplayLength;

            iTotalRecords = obj.getMerchantTxnCount(searchSQL, merchantId);
            List<TransactionBean> samples = new ArrayList<TransactionBean>();
            try {
                int end = param.iDisplayLength + param.iDisplayStart;
                samples = obj.getMerchantTxnExpDetails(searchSQL, param.iDisplayStart, end, merchantId);
                if (!param.sSearchKeyword.equals("")) {
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
            for (TransactionBean t : samples) {

                JSONObject object = new JSONObject();

                object.put("txnid", t.getTxnId());
                object.put("txntype", t.getTxnTypeDes());
                object.put("bin", t.getBin());
                object.put("cardno", t.getCardNumber());
                object.put("expirydate", t.getExpiryDate());
                object.put("txncurrency", t.getTxnCurrency());
                object.put("txnamount", t.getTxnAmount());
                object.put("settledate", t.getSettlementDate());
                object.put("terminalid", t.getTerminalId());
                object.put("merchantid", t.getMerchantId());
                object.put("authcode", t.getAuthCode());
                object.put("traceno", t.getTraceNum());
                object.put("txndate", t.getTxnDate());
                object.put("statusdes", t.getTxnStatusSDes());
                object.put("txntime", t.getTxnTime());

                object.put("view", "<a href='#' onclick=invokeTransactionHistory('" + t.getTxnId() + "')>View</a>");

                if (t.getTxnStatus().equals("5")) {
                    object.put("DT_RowClass", "txngradeA");
                } else if (t.getTxnStatus().equals("6")) {
                    object.put("DT_RowClass", "txngradeB");
                } else if (t.getTxnStatus().equals("7")) {
                    object.put("DT_RowClass", "txngradeC");
                } else if (t.getTxnStatus().equals("8")) {
                    object.put("DT_RowClass", "txngradeD");
                } else if (t.getTxnStatus().equals("9")) {
                    object.put("DT_RowClass", "txngradeE");
                } else if (t.getTxnStatus().equals("10")) {
                    object.put("DT_RowClass", "txngradeF");
                } else if (t.getTxnStatus().equals("11")) {
                    object.put("DT_RowClass", "txngradeG");
                } else if (t.getTxnStatus().equals("12")) {
                    object.put("DT_RowClass", "txngradeH");
                } else if (t.getTxnStatus().equals("13")) {
                    object.put("DT_RowClass", "txngradeI");
                } else if (t.getTxnStatus().equals("16")) {
                    object.put("DT_RowClass", "txngradeJ");
                } else if (t.getTxnStatus().equals("17")) {
                    object.put("DT_RowClass", "txngradeK");
                } else if (t.getTxnStatus().equals("18")) {
                    object.put("DT_RowClass", "txngradeL");
                } else if (t.getTxnStatus().equals("19")) {
                    object.put("DT_RowClass", "txngradeM");
                } else if (t.getTxnStatus().equals("20")) {
                    object.put("DT_RowClass", "txngradeN");
                }

                row.put(object);
            }
            jsonResponse.put("aaData", row);

            response.setContentType("application/json");
            response.getWriter().print(jsonResponse.toString());

        } catch (Exception ex) {
            Logger.getLogger(LoadMerchantLocationTxnExpServlet.class.getName()).log(Level.SEVERE, null, ex);
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
