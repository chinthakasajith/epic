/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.payment.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.payment.bean.PaymentBatchBean;
import com.epic.cms.backoffice.payment.businesslogic.BatchManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ruwan_e
 */
public class SearchBatchesServlet extends HttpServlet {

    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private List<String> userTaskList;
    private String url = "/backoffice/payment/batch_management.jsp";
    private BatchManager batchManager = null;
    private PaymentBatchBean summaryBean;
    private String errorMessage = null;

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
            try {
                sessionObj = request.getSession(false);
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                try {
                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }

                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();

                }
                try {
                    //set page code and task codes
                    String pageCode = PageVarList.BATCH_MANAGEMENT;
                    String taskCode = TaskVarList.SEARCH;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        try {
                            batchManager = new BatchManager();
                            request.setAttribute(RequestVarList.BM_STATUS_LIST, batchManager.getBatchStatusMap());

                            if (validateUserInput(request)) {

                                String batchID = request.getParameter(RequestVarList.BMS_ID);
                                String branch = request.getParameter(RequestVarList.BMS_BRANCH);
                                String username = request.getParameter(RequestVarList.BMS_UNAME);
                                String status = request.getParameter(RequestVarList.BMS_STATUS);
                                String paymentDateFrom = request.getParameter(RequestVarList.BMS_FDATE);
                                String paymentDateTo = request.getParameter(RequestVarList.BMS_TDATE);

                                List<PaymentBatchBean> batchBeans = batchManager.searchBatches(
                                        batchID,
                                        branch,
                                        username,
                                        status,
                                        paymentDateFrom,
                                        paymentDateTo);

                                request.setAttribute(RequestVarList.BM_ALL_BATCHES, batchBeans);
                                batchManager = new BatchManager();
                                request.setAttribute(RequestVarList.BM_STATUS_LIST, batchManager.getBatchStatusMap());
                                request.setAttribute(RequestVarList.BM_BRANCH_LIST, batchManager.getBranchMap());
                                request.setAttribute(RequestVarList.BM_USERS_LIST, batchManager.getAuthenticatedUsers());

                                request.setAttribute(RequestVarList.BMS_ID, batchID);
                                request.setAttribute(RequestVarList.BMS_FDATE, paymentDateFrom);
                                request.setAttribute(RequestVarList.BMS_TDATE, paymentDateTo);
                                request.setAttribute(RequestVarList.BMS_BRANCH, branch);
                                request.setAttribute(RequestVarList.BMS_UNAME, username);
                                request.setAttribute(RequestVarList.BMS_STATUS, status);


                            } else {
                                request.setAttribute("summaryBean", summaryBean);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }

                        } catch (Exception ex) {
                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            rd = getServletContext().getRequestDispatcher(url);
                        }
                    } else {
                        throw new AccessDeniedException();

                    }

                    sessionVarlist.setUserPageTaskList(userTaskList);

                } catch (AccessDeniedException adex) {
                    throw adex;

                }

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            rd = request.getRequestDispatcher(url);

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);

        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
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

//    private boolean setUserInputToBean(HttpServletRequest request) throws Exception {
//        Boolean success = false;
//        try {
//            summaryBean = new PaymentBatchBean();
//
//            summaryBean.setBatchId(request.getParameter("batch_id").trim());
//            summaryBean.setBranch(request.getParameter("branch").trim());
//            summaryBean.set(request.getParameter("username").trim());
//            summaryBean.setStatus(request.getParameter("status").trim());
//            summaryBean.setCreatedDate(request.getParameter("fromDate").trim());
//            summaryBean.setToDate(request.getParameter("toDate").trim());
//
//            success = true;
//
//        } catch (Exception e) {
//            success = false;
//            throw e;
//        }
//        return success;
//    }
    private boolean validateUserInput(ServletRequest request) {
        String batchId = request.getParameter("batch_id");
        if (batchId.trim() == "") {
            return true;
        } else if (UserInputValidator.isNumeric(batchId)) {
            return true;
        } else {
            errorMessage = "Batch ID has to be numeric";
            return false;
        }
    }
}
