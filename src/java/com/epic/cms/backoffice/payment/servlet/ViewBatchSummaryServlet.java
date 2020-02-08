/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.payment.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.payment.bean.BatchSummaryRecord;
import com.epic.cms.backoffice.payment.bean.PaymentBatchBean;
import com.epic.cms.backoffice.payment.businesslogic.BatchManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
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
public class ViewBatchSummaryServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    
    private PaymentBatchBean paymentBatchBean;
    ArrayList<BatchSummaryRecord> batchSummary = new ArrayList<BatchSummaryRecord>();
    private BatchManager batchManager;
    private int chequeTxnCount;
    private int cashTxnCount;
    private BigDecimal chequeTxnAmount;
    private BigDecimal cashTxnAmount;
    
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
            HttpSession sessionObj = request.getSession(false);
           try {               
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

            /////////////////////////////////////////
            String batchId = request.getParameter("batchId");
            this.getBatchDetails(batchId);
            
            if (paymentBatchBean != null) {
                
                sessionObj.setAttribute("paymentBatchBean", paymentBatchBean);
                sessionObj.setAttribute("chequeTxnCount", chequeTxnCount);
                sessionObj.setAttribute("cashTxnCount", cashTxnCount);
                sessionObj.setAttribute("chequeTxnAmount", chequeTxnAmount);
                sessionObj.setAttribute("cashTxnAmount", cashTxnAmount);
                sessionObj.setAttribute("batchSummary", batchSummary);
                
            }
            out.print("success");
           
        } catch (SesssionExpException sex) {
             out.print("session");
        } catch (NewLoginSessionException nlex) {
            out.print("session");
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/ViewBatchServlet");
            rd.include(request, response);
        } catch (SQLException ex) {
            out.print("error");
        } catch (Exception ex) {
            out.print("error");
        } finally {
             out.close();
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
