/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.payment.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.payment.bean.BatchSummaryRecord;
import com.epic.cms.backoffice.payment.bean.PaymentBatchBean;
import com.epic.cms.backoffice.payment.bean.PaymentBean;
import com.epic.cms.backoffice.payment.businesslogic.BatchManager;
import com.epic.cms.backoffice.payment.businesslogic.PaymentManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.exception.ValidateException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
 * @author ruwan_e
 */
public class ViewBatchServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private RequestDispatcher rd;
    private String url = "/backoffice/payment/viewBatch.jsp";
    private PaymentManager paymentManager;
    private BatchManager batchManager;
    private List<PaymentBatchBean> paymentBatchList;
    private List<PaymentBean> paymentList;
    private PaymentBatchBean paymentBatchBean;
    private List<String> userTaskList;
    private BigDecimal tot = null;
    private String total = "";
    private int chequeTxnCount = 0;
    private int cashTxnCount = 0;
    private BigDecimal chequeTxnAmount = null;
    private BigDecimal cashTxnAmount = null;
    ArrayList<BatchSummaryRecord> batchSummary = new ArrayList<BatchSummaryRecord>();

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
            // request.setAttribute("operationtype", "view");

            try {

                HttpSession sessionObj = request.getSession(false);
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();
                try {
                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(),
                            sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }
                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();

                }
            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            String pageCode = PageVarList.BATCH_MANAGEMENT;
            String taskCode = TaskVarList.VIEW;

            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                throw new AccessDeniedException();
            }
            /////////////////////////////////////////
            String id = request.getParameter("id");

            this.getBatchDetails(id);
            this.getPaymentDetails(id);

            request.setAttribute("paymentBatchList", paymentBatchList);

            if (paymentBatchBean != null) {

                request.setAttribute("operationtype", "view");
                request.setAttribute("paymentBatchBean", paymentBatchBean);
                request.setAttribute("paymentList", paymentList);
                request.setAttribute("total", tot);
                request.setAttribute("chequeTxnCount", chequeTxnCount);
                request.setAttribute("cashTxnCount", cashTxnCount);
                request.setAttribute("chequeTxnAmount", chequeTxnAmount);
                request.setAttribute("cashTxnAmount", cashTxnAmount);
                request.setAttribute("batchSummary", batchSummary);
                rd = getServletContext().getRequestDispatcher(url);
            }


            ///////////////////////////////////////////

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadBatchManagementServlet");
            //rd.include(request, response);
        } catch (SQLException ex) {
            //ex.printStackTrace();
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } //        catch (ValidateException ex) {
        //            request.setAttribute(MessageVarList.JSP_ERROR,
        //                    CardScoreManager.getScoreCardInstance().getErrorMsg());
        //            rd = request.getRequestDispatcher(url);
        //        }
        catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
        }
    }

    private void getBatchDetails(String id) throws Exception {
        try {

            batchManager = new BatchManager();
            paymentBatchBean = batchManager.getBatchById(id);
            
            batchSummary = batchManager.getBatchSummary(id);
            chequeTxnCount = batchManager.getTotalChequeTxnCount(id);
            cashTxnCount = batchManager.getTotalCashTxnCount(id);
            chequeTxnAmount = BigDecimal.valueOf(batchManager.getChequeTxnAmount(id));
            cashTxnAmount = BigDecimal.valueOf(batchManager.getCashTxnAmount(id));

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getPaymentDetails(String id) throws Exception {
        try {

            batchManager = new BatchManager();
            paymentList = batchManager.getAllPaymentDetailsByBatchId(id);

            tot = BigDecimal.valueOf(batchManager.getTotalTxnAmount(id));

//            NumberFormat formatter = new DecimalFormat();
//            total = formatter.format(tot);

//            batchManager.getTotalCashTxnCount(id);
//            batchManager.getTotalChequeTxnCount(id);

        } catch (Exception ex) {
            throw ex;
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
