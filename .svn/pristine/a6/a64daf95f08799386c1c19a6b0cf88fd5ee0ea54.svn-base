/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.txnadjustment.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.backoffice.txnadjustment.bean.TransactionAdjustment;
import com.epic.cms.backoffice.txnadjustment.bean.TransactionAdjustmentParty;
import com.epic.cms.backoffice.txnadjustment.businesslogic.TransactionAdjustmentManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
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
public class UpdateCardTxnAdjustment extends HttpServlet {

    private TransactionAdjustment tabean;
    private SessionVarList sessionVarlist;
    private SystemUserManager systemUserManager;
    private SessionUser sessionUser;
    private String url = "/backoffice/txnadjustment/cardtxnadjustment.jsp";
    HttpSession sessionObj = null;
    private TransactionAdjustmentManager tamanager;
    private List<StatusBean> statusList;
    private RequestDispatcher rd;
    private List<String> userTaskList;
    private String errorMessage;
    private SystemAuditBean systemAuditBean;
    private Map<String, String> feeTypeMap;
    private List<TransactionAdjustment> allTAList;

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
        request.setAttribute("operationType", "add");
        PrintWriter out = response.getWriter();

        try {
            sessionObj = request.getSession(false);

            //--------------------------------------------------------------------------------------------------//

            try {


                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();


            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }
            //check system user is in the same session or not
            try {

                if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                    //user not in same session.
                    throw new NewLoginSessionException();

                }

            } catch (NewLoginSessionException nlex) {
                //throw lst login close exception
                throw new NewLoginSessionException();

            }
            try {
                //set page code and task codes
                String pageCode = PageVarList.CARD_TXN_ADJUSTMENT;
                String taskCode = TaskVarList.UPDATE;


                //check whether userrole can access this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        tamanager = new TransactionAdjustmentManager();
                        feeTypeMap = tamanager.getFeeTypeMap();
                        request.setAttribute("feeTypeMap", feeTypeMap);
                        request.setAttribute("operation_type", "add");
                        this.setValuesToBean(request);
                        try {
                            if (validateUserInput(tabean)) {
                                tamanager.updateTransactionAdjustment(tabean);
                                request.setAttribute("successMsg", MessageVarList.SUCCESS_UPDATE);
                                request.setAttribute("valid", true);
                            } else {
                                request.setAttribute("taBean", tabean);
                                request.setAttribute("errorMsg", errorMessage);
                                request.setAttribute("valid", false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        request.setAttribute(RequestVarList.TA_CARD_TRANSACTION_ADJUSTMENTS,
                                tamanager.getTransactionAdjustmentsByParty(TransactionAdjustmentParty.CARD));


                        rd = request.getRequestDispatcher(url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                        out.println("<html>");
//                        out.println("<head>");
//                        out.println("<title>Servlet AddCardTxnAdjustment</title>");
//                        out.println("</head>");
//                        out.println("<body>");
//                        out.println("<h1>Servlet AddCardTxnAdjustment at " + request.getContextPath() + "</h1>");
//
//                        Enumeration enParams = request.getParameterNames();
//                        while (enParams.hasMoreElements()) {
//                            String paramName = (String) enParams.nextElement();
//                            out.println("Parameter Name - " + paramName + ", Value - " + request.getParameter(paramName) + "</br>");
//                        }
//
//                        out.println("</body>");
//                        out.println("</html>");


                } else {

                    //if invalid throw access denied exception
                    throw new AccessDeniedException();

                }

                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                throw adex;
            }


            //------------------------------------------------------------------------------------------------------------//
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);


        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
        }
    }

    private void setValuesToBean(HttpServletRequest request) throws Exception {
        try {
            tabean = new TransactionAdjustment();
            tabean.setId(Long.parseLong(request.getParameter("adjustmentID")));
            tabean.setAdjustmentParty(TransactionAdjustmentParty.CARD);
            tabean.setAmount(request.getParameter("amount"));
            tabean.setCurrencyCode(request.getParameter("currency"));
            tabean.setCrOrdr(request.getParameter("crOrdr"));
            tabean.setStatus(request.getParameter("status"));
            tabean.setTraceNo(request.getParameter("traceNumber"));
            tabean.setUniqueId(request.getParameter("cardNumber"));
            tabean.setVerificationValue(request.getParameter("expiryDate"));
            tabean.setUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            tabean.setRemarks(request.getParameter("remarks"));
            tabean.setAdjustmentType(request.getParameter("adjustmentType"));
            tabean.setTransactionType(request.getParameter("tx_type"));
        } catch (Exception e) {
            throw e;
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

    public boolean validateUserInput(TransactionAdjustment ta) throws Exception {
        boolean isValid = true;

        errorMessage = "";
        try {

            if (!UserInputValidator.isEmptyField("Card Number", ta.getUniqueId(), false)) {
                isValid = false;
                errorMessage = MessageVarList.CARD_NUMBER_NULL;
            } else if (tamanager.isCardNumberExist(ta.getUniqueId())) {
                isValid = false;
                errorMessage = MessageVarList.CARD_NUMBER_NOT_EXIST;
            } else if (ta.getVerificationValue().contentEquals("") || ta.getVerificationValue().isEmpty()) {
                isValid = false;
                errorMessage = MessageVarList.EXPIRY_DATE_NULL;
            } else if (ta.getAdjustmentType() == null) {
                isValid = false;
                errorMessage = MessageVarList.ADJUSTMENT_TYPE_NULL;
            } else if (ta.getTransactionType().contentEquals("") || ta.getTransactionType().isEmpty()) {
                isValid = false;
                errorMessage = MessageVarList.TRANSACTION_TYPE_NULL;
            } else if (!UserInputValidator.isEmptyField("Amount", ta.getAmount(), false)) {
                isValid = false;
                errorMessage = MessageVarList.AMOUNT_INVALID;
            } else if (!UserInputValidator.isDecimal_Numeric(ta.getAmount())) {
                isValid = false;
                errorMessage = MessageVarList.AMOUNT_INVALID;
            } else if (ta.getCrOrdr() == null || ta.getCrOrdr().isEmpty()) {
                isValid = false;
                errorMessage = MessageVarList.INVALID_CR_DR;
            } else if (ta.getCurrencyCode() == null || ta.getCurrencyCode().isEmpty()) {
                isValid = false;
                errorMessage = MessageVarList.CURRENCY_NULL;
            } else if (ta.getTraceNo() == null || ta.getTraceNo().isEmpty()) {
                isValid = false;
                errorMessage = MessageVarList.TRACE_NO_NULL;
            }
        } catch (Exception ex) {
            isValid = false;
        }
        return isValid;
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
