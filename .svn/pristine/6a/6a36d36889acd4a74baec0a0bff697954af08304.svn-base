/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.sysmsg.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;

import com.epic.cms.switchcontrol.sysmsg.bean.ViewFailAlertBean;
import com.epic.cms.switchcontrol.sysmsg.businesslogic.ViewAlertManager;
import com.epic.cms.switchcontrol.sysmsg.businesslogic.ViewFailAlertManager;
import com.epic.cms.system.util.datatable.DataTableRequestParam;
import com.epic.cms.system.util.datatable.DataTablesParamUtility;
import com.epic.cms.system.util.json.JSONArray;
import com.epic.cms.system.util.json.JSONObject;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nisansala
 */
public class SearchViewFailAlertServlet extends HttpServlet {

    //initializing variables
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SystemAuditBean systemAuditBean = null;
    private SessionVarList sessionVarlist;
    private String errorMessage = null;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    ViewFailAlertManager vwfailAlertMgr;
    private ViewFailAlertBean inputBean;
    HashMap<String, String> riskCategory = null;
    private List<ViewFailAlertBean> searchAlertList;
    private ViewAlertManager viewalertmgr;
    String url = "/switch/sysmsg/viewfailalert/viewfailalerthome.jsp";

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
            //columns of the table view
            String[] cols = {"txnID", "readStatus", "errorInfo", "riskCategory"};

            final DataTableRequestParam param = DataTablesParamUtility.getParam(request);
            final String sEcho = param.sEcho;
            int iTotalRecords = 0; // total number of records (unfiltered)
            int iTotalDisplayRecords = 0;//value will be set when code filters data by keyword
            JSONArray data = new JSONArray(); //data that will be shown in the table

            vwfailAlertMgr = new ViewFailAlertManager();
            List<String> sArray = new ArrayList<String>();
            // SQL binding parameters
            List<Object> sqlParams = new ArrayList<Object>();

            // Global Search
            String searchSQL = "";
            String globalSearch = "";
            if (!param.sSearchKeyword.contains("'") && !param.sSearchKeyword.contains("\"")) {
                globalSearch = " (F.TXNID LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR F.STATUS LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR F.ERRORINFORMATION LIKE '%" + param.sSearchKeyword + "%'"
                        + " OR F.RISKCATERGORY LIKE '%" + param.sSearchKeyword + "%')";

            } else {
                globalSearch = "1=1";
            }

            if (!param.sSearchKeyword.equals("") && validateSearchValue(param.sSearchKeyword)) {
                searchSQL = globalSearch;
            } else {
                searchSQL = "1=1";
                sqlParams.clear();
            }

            String txnID = request.getParameter("txnid");
            String fromDate = request.getParameter("fromdate");
            String toDate = request.getParameter("todate");
            String riskCat = request.getParameter("riskCategory");
            String readStatus = request.getParameter("readstatus");


            if (!txnID.contentEquals("") && !txnID.contains("'") && !txnID.contains("\"") && txnID.length() > 0) {

                searchSQL += "AND F.TXNID = '" + txnID + "'";
            }
            if (!readStatus.contentEquals("") || readStatus.length() > 0) {

                searchSQL += "AND F.STATUS = '" + readStatus + "'";

            }
            if (!riskCat.contentEquals("") || riskCat.length() > 0) {

                searchSQL += "AND F.RISKCATERGORY = '" + riskCat + "'";
            }
            if (!fromDate.contentEquals("") && !fromDate.contentEquals("")) {

                searchSQL += "AND F.DATETIME >= to_Date('" + this.stringTodate(fromDate) + "','yy-mm-dd') AND F.DATETIME <= to_Date('" + this.stringTodate(fromDate) + "','yy-mm-dd')";
            } else {

                if (!fromDate.contentEquals("")) {

                    searchSQL += "AND F.DATETIME >= to_Date('" + this.stringTodate(fromDate) + "','yy-mm-dd')";
                }
                if (!toDate.contentEquals("")) {
                    if (!searchSQL.equals("")) {
                        searchSQL += " AND ";
                    }
                    searchSQL += "AND F.DATETIME <= to_Date('" + this.stringTodate(toDate) + "','yy-mm-dd')";
                }
            }

            if (param.iDisplayStart < 0) {
                param.iDisplayStart = 0;
            }

            if (param.iDisplayLength < 10 || param.iDisplayLength > 100) {
                param.iDisplayLength = 10;
            }

            // Ordering and limiting for pagination
            searchSQL += " ORDER BY " + cols[param.iSortCol[0]] + " " + param.sSortDir[0];

            iTotalRecords = vwfailAlertMgr.getCountTxn(searchSQL);
            searchAlertList = new ArrayList<ViewFailAlertBean>();
            try {
                int end = param.iDisplayLength + param.iDisplayStart;
                searchAlertList = vwfailAlertMgr.getTxnAlertDetails(searchSQL, param.iDisplayStart, end);
                if (param.sSearchKeyword != "") {
                    iTotalDisplayRecords = iTotalRecords;
                } else {
                    iTotalDisplayRecords = iTotalRecords;
                }
            } catch (Exception e) {//if calling db gives exception
                iTotalDisplayRecords = iTotalRecords;
            }

            JSONObject jsonResponse = new JSONObject();

            jsonResponse.put("sEcho", sEcho);
            jsonResponse.put("iTotalRecords", iTotalRecords);
            jsonResponse.put("iTotalDisplayRecords", iTotalDisplayRecords);

            JSONArray row = new JSONArray();
            for (ViewFailAlertBean t : searchAlertList) {

                JSONObject object = new JSONObject();

                object.put("txnID", t.getTxnID());
                object.put("errorInfo", t.getErrorInfo());
                object.put("riskDes", t.getRiskDes());

                object.put("view", "<a href='#' onclick=invokeView('" + t.getTxnID() + "'," + t.getReadStatus() + ")>Read</a>");

                row.put(object);

            }
            jsonResponse.put("aaData", row);
            response.setContentType("application/json");
            response.getWriter().print(jsonResponse.toString());
        } catch (Exception ex) {
            Logger.getLogger(SearchViewFailAlertServlet.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        } finally {
            out.close();
        }
    }

    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {
        boolean isValidTaskAccess = false;

        try {
            systemUserManager = new SystemUserManager();

            //get all tasks for userrole for this page
            userTaskList = systemUserManager.getTasksByPageCodeAndUserRole(userrole, pagecode);

            for (String usertask : userTaskList) {

                if (task.equals(usertask)) {
                    isValidTaskAccess = true;
                }
            }
            return isValidTaskAccess;
        } catch (Exception ex) {
            throw ex;
        }

    }

//    public void setUserInputToBean(HttpServletRequest request) {
//        //System.out.println(">>>>>>>>>>>>>>>>>>" + request.getParameter("isBack"));
//
//        if (request.getParameter("isBack") != null) {
//            inputBean = new ViewFailAlertBean();
//            if (sessionVarlist.getFailBean() != null) {
//                inputBean = sessionVarlist.getFailBean();
//            }
//
//        } else if (request.getAttribute("isBack") != null) {
//            inputBean = new ViewFailAlertBean();
//            if (sessionVarlist.getFailBean() != null) {
//                inputBean = sessionVarlist.getFailBean();
//            }
//
//        } else {
//            inputBean = new ViewFailAlertBean();
//
//
//            inputBean.setTxnID(request.getParameter("txnid"));
//            inputBean.setFromDate(request.getParameter("fromdate"));
//            inputBean.setToDate(request.getParameter("todate"));
//            inputBean.setReadStatus(request.getParameter("readstatus"));
//            inputBean.setRiskCategory(request.getParameter("riskCategory"));
//
//            sessionVarlist.setFailBean(inputBean);
//
//
//        }
//    }
    public boolean validateUserInput(ViewFailAlertBean inputBean, HttpServletRequest request) throws Exception {
        boolean isValid = true;
        //validate user Role code
        try {

            if (inputBean.getReadStatus().contentEquals("")) {
                isValid = false;
                errorMessage = MessageVarList.VW_ALERT_RD_STATUS_NULL;
            }


        } catch (Exception ex) {
            isValid = false;

        }

        return isValid;
    }

    private void searchFailAlert(ViewFailAlertBean inputBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        vwfailAlertMgr = new ViewFailAlertManager();
        searchAlertList = vwfailAlertMgr.searchAlert(inputBean, systemAuditBean);

    }

    public void getAllRiskCategories() throws Exception {

        try {
            viewalertmgr = new ViewAlertManager();
            riskCategory = viewalertmgr.getAllRiskCategories();
        } catch (SQLException ex) {
            throw ex;
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
