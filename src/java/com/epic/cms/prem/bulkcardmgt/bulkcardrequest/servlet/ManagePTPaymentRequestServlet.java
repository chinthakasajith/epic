/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.prem.bulkcardmgt.bulkcardrequest.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.ECMSOnlineTransBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.businesslogic.BTPaymentRequestManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.businesslogic.EasyPaymentRequestManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sajith_g
 */
public class ManagePTPaymentRequestServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private SystemUserManager sysUserMgr;
    private BTPaymentRequestManager btPaymentRequestManager;
    String errorMessage = "";
    List<ECMSOnlineTransBean> easyPaymentReqList = null;
    List<BulkCardRequestBean> reqList = null;
    List<CurrencyBean> currency = null;
    private boolean successConfirm;
    private ECMSOnlineTransBean eCMSOnlineTransBean;
    String url = "/callcenter/card/balancetranferpaymentrequest.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
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
            //call to existing session
            sessionObj = request.getSession(false);
            try {
                sysUserMgr = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();
                //check system user is in same session or not
                try {
                    if (!sysUserMgr.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        //useer not in same session.
                        throw new NewLoginSessionException();
                    }
                } catch (NewLoginSessionException nlex) {
                    //throw lst login close exception
                    throw new NewLoginSessionException();
                }
            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }
            try {
                //set page code and task codes
                String pageCode = PageVarList.BALANCE_TRANSFER_PAYMENT_REQUEST;
                String taskCode = TaskVarList.SEARCH;

                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        request.setAttribute("operationtype", "search");
                        //assign user input to the bean
                        setUserInputToBean(request);

                        //validate user inputs
                        if (validateUserInput(eCMSOnlineTransBean)) {

                            try {

                                this.getAllCardTrnByCardNo(eCMSOnlineTransBean);

                                request.setAttribute("tempBean", eCMSOnlineTransBean);
                                request.setAttribute("easyPaymentReqList", easyPaymentReqList);

                                rd = getServletContext().getRequestDispatcher(url);
                                rd.include(request, response);

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("errorMsg", oraMessage);
                                request.setAttribute("tempBean", eCMSOnlineTransBean);
                                request.setAttribute("amountTo", eCMSOnlineTransBean.getToAmount());
                                request.setAttribute("amountFrom", eCMSOnlineTransBean.getFromAmount());
                                request.setAttribute("easyPaymentReqList", easyPaymentReqList);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);

                            }
                        } else {
                            request.setAttribute("tempBean", eCMSOnlineTransBean);
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute("toAmount", eCMSOnlineTransBean.getToAmount());
                            request.setAttribute("fromAmount", eCMSOnlineTransBean.getFromAmount());
                            request.setAttribute("easyPaymentReqList", easyPaymentReqList);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);

                        }
                    } catch (Exception e) {
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("easyPaymentReqList", easyPaymentReqList);
                        request.setAttribute("tempBean", eCMSOnlineTransBean);
                        request.setAttribute("amountTo", eCMSOnlineTransBean.getToAmount());
                        request.setAttribute("amountFrom", eCMSOnlineTransBean.getFromAmount());
                        request.setAttribute("easyPaymentReqList", easyPaymentReqList);
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }
                } else {
                    throw new AccessDeniedException();
                }
                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);
            } catch (AccessDeniedException adex) {
                //if user_role doesn't have required access to the page throw Access Denied exception
                throw adex;
            }
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);

        } //catch session exception
        catch (NewLoginSessionException nlex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);
        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (Exception ex) {
        }
    }

    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {
        boolean isValidTaskAccess = false;

        try {
            sysUserMgr = new SystemUserManager();
            //get all tasks for userrole for this page
            userTaskList = sysUserMgr.getTasksByPageCodeAndUserRole(userrole, pagecode);

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

    private void getAllCardTrnByCardNo(ECMSOnlineTransBean eCMSOnlineTransBean) throws Exception {
        try {
            btPaymentRequestManager = new BTPaymentRequestManager();
            easyPaymentReqList = btPaymentRequestManager.getOnlineTransReq(eCMSOnlineTransBean);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean validateUserInput(ECMSOnlineTransBean eCMSOnlineTransBean) throws Exception {
        boolean isValidate = true;

        try {
            if (eCMSOnlineTransBean.getToAmount().contentEquals("") && !eCMSOnlineTransBean.getFromAmount().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.BTPR_AMOUNT_NULL;
            }
            if (!eCMSOnlineTransBean.getToAmount().contentEquals("") && eCMSOnlineTransBean.getFromAmount().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.BTPR_AMOUNT_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            eCMSOnlineTransBean = new ECMSOnlineTransBean();
            eCMSOnlineTransBean.setCardNumber(sessionVarlist.getCardNumber());
            if (request.getParameter("amountTo") != null) {
                eCMSOnlineTransBean.setToAmount(request.getParameter("amountTo"));
            }
            if (request.getParameter("amountFrom") != null) {
                eCMSOnlineTransBean.setFromAmount(request.getParameter("amountFrom"));
            }
            if (request.getParameter("fromdate1") != null) {
                eCMSOnlineTransBean.setFromDate(request.getParameter("fromdate1").replaceAll("\\-", ""));
            }

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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

}
