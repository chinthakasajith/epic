/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantlocation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CurrencyMgtManager;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.MerchantCategoryManager;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.TxnTypeMgtManager;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantCustomerBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.businesslogic.MerchantCustomerManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author admin
 */
public class LoadCreateMerchantLocationServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    HttpSession sessionObj = null;
    private List<TypeMgtBean> typeList = null;
    private List<MerchantCategoryBean> merchantCategoryList = null;
    private List<MerchantCustomerBean> merchantCustomerList = null;
    private List<CurrencyBean> currencyList = null;
    private String url = "/mtmm/merchantmgt/createmerchantlocation.jsp";

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
                //request.setAttribute("operationtype", "search");



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
                    String pageCode = PageVarList.MERCHANT_LOCATION;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    ///////////////////////get transaction Type List////////////////////////
                    TxnTypeMgtManager txnObj = new TxnTypeMgtManager();
                    typeList = new ArrayList<TypeMgtBean>();

                    typeList = txnObj.getTxnTypeDetails();

                    /////////////////////get merchant category List//////////////////////////////
                    MerchantCategoryManager mcmObj = new MerchantCategoryManager();
                    merchantCategoryList = new ArrayList<MerchantCategoryBean>();

                    merchantCategoryList = mcmObj.getAllMerchntCatgryDetails();

                    ////////////////////get currency List ////////////////////////////////////

                    CurrencyMgtManager curmgtObj = new CurrencyMgtManager();
                    currencyList = new ArrayList<CurrencyBean>();

                    currencyList = curmgtObj.getCurrencyDetails();

                    ///////////////////////////////////////////////////////////////////////////////

                    MerchantCustomerManager ccm = new MerchantCustomerManager();
                    merchantCustomerList = new ArrayList<MerchantCustomerBean>();
                    //get  MerchantCustomer Name details
                    merchantCustomerList = ccm.getMerchantCustomerDetails();


                    //////////////////////////////////////////////////////////////////////////////
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                        request.setAttribute("operationtype", "ADD");
                        request.setAttribute("selectedtab", "0");
                        request.setAttribute("disableTabIndex", "1");
                        request.setAttribute(RequestVarList.TXNTYPEMGT_LIST, typeList);
                        request.setAttribute(RequestVarList.MERCHANTCATEGORY_LIST, merchantCategoryList);
                        request.setAttribute(RequestVarList.CURRENCY_LIST, currencyList);
                        request.setAttribute(RequestVarList.MERCHANTCUSTOMER_LIST, merchantCustomerList);

                        rd = getServletContext().getRequestDispatcher(url);
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
            ///////////////////////////////////////////////


            /////////////////////////////////////////////////
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
