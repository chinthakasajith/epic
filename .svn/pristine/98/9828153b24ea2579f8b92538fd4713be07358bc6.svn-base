/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.manualtxn.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.mtmm.manualtxn.bean.SaleTxnBean;
import com.epic.cms.mtmm.manualtxn.businesslogic.SaleTxnManager;
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
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nalin
 */
public class LoadCashAdvancedTxnInfoServlet extends HttpServlet {
    
     private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private SaleTxnBean cashAdvancedBean = null;
    private HashMap<String, String> terminalList = null;
    private String errorMessage = null;
    private SaleTxnManager saleTxnManager = null;
    private List<SaleTxnBean> cashAdvancedList = null;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> currencyList = null;
    private String url = "/mtmm/manualtxn/cashadvancedtxnhome.jsp";

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
                    String pageCode = PageVarList.CASH_ADVANCED_TRANSACTION;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                        //////////////////////////////////////////////
                        try {

                            setUserInputToBean(request);

                            this.getActiveManualTerminalOfMerchant(cashAdvancedBean.getMerchantID());

                            if (this.validateUserInput(cashAdvancedBean.getMerchantID())) {

                                this.getActiveManualTerminalOfMerchant(cashAdvancedBean.getMerchantID());
                                this.getTerminalTxnInfo(cashAdvancedBean.getTerminalID(),cashAdvancedBean.getMerchantID());
                                this.getCardTypes();
                                this.getCurrency();

                                request.setAttribute("cashAdvancedBean", cashAdvancedBean);
                                request.setAttribute("terminalList", terminalList);
                                request.setAttribute("cashAdvancedList", cashAdvancedList);
                                request.setAttribute("cardTypeList", cardTypeList);
                                request.setAttribute("currencyList", currencyList);

                            } else {

                                request.setAttribute("errorMsg", errorMessage);
                                request.setAttribute("terminalList", terminalList);

                            }


                        } catch (Exception ex) {
                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            request.setAttribute("terminalList", terminalList);
                            rd = getServletContext().getRequestDispatcher(url);
                        }


                        //////////////////////////////////////////////
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

    private HashMap<String, String> getActiveManualTerminalOfMerchant(String merchantID) throws Exception {

        try {
            terminalList = new HashMap<String, String>();
            saleTxnManager = new SaleTxnManager();

            terminalList = saleTxnManager.getActiveManualTerminalOfMerchant(merchantID);

        } catch (Exception ex) {
            throw ex;
        }
        return terminalList;

    }

    private List<SaleTxnBean> getTerminalTxnInfo(String terminalID,String merchantId) throws Exception {

        try {
            cashAdvancedList = new ArrayList<SaleTxnBean>();
            saleTxnManager = new SaleTxnManager();

            cashAdvancedList = saleTxnManager.getTerminalTxnInfo(terminalID,merchantId);

        } catch (Exception ex) {
            throw ex;
        }
        return cashAdvancedList;

    }

    private HashMap<String, String> getCardTypes() throws Exception {

        try {

            cardTypeList = new HashMap<String, String>();
            saleTxnManager = new SaleTxnManager();

            cardTypeList = saleTxnManager.getCardTypes();

        } catch (Exception ex) {
            throw ex;
        }

        return cardTypeList;
    }

    private HashMap<String, String> getCurrency() throws Exception {

        try {

            currencyList = new HashMap<String, String>();
            saleTxnManager = new SaleTxnManager();

            currencyList = saleTxnManager.getCurrency();

        } catch (Exception ex) {
            throw ex;
        }

        return currencyList;
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = true;
        try {

            cashAdvancedBean = new SaleTxnBean();

            cashAdvancedBean.setMerchantID(request.getParameter("merchantID"));
            cashAdvancedBean.setMerchantName(request.getParameter("merchantName"));
            cashAdvancedBean.setTerminalID(request.getParameter("terminalID"));


        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    public boolean validateUserInput(String merchantID) throws Exception {
        boolean isValidate = true;

        //////validate merchantID

        if (merchantID.contentEquals("") || merchantID.length() <= 0) {
            isValidate = false;

            errorMessage = MessageVarList.MERCHANT_LOCATION_ID_NULL;

        } else if (!UserInputValidator.isNumeric(merchantID) || merchantID.length() != 15) {
            isValidate = false;

            errorMessage = MessageVarList.MERCHANT_LOCATION_ID_INVALID;

        }

        try {
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    /**
     * 
     * @param userrole
     * @param pagecode
     * @param task
     * @return
     * @throws Exception 
     */
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
