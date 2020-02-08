/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantcustomer.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantCustomerBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.businesslogic.MerchantCustomerManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author nalin
 */
public class ViewMerchantCustomerServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private MerchantCustomerManager merchantManager;
    private List<MerchantCustomerBean> beanList;
    private MerchantCustomerBean merchantBean = null;
    private List<MerchantCategoryBean> notAssigndnMerchantCatogoryList = null;
    private List<MerchantCategoryBean> assigndnMerchantCatogoryList = null;
    private List<TypeMgtBean> notAssigndnTxnTypeList = null;
    private List<TypeMgtBean> assigndnTxnTypeList = null;
    private List<CurrencyBean> notAssignCurrencyList = null;
    private List<CurrencyBean> assignCurrencyList = null;
    private String url = "/mtmm/merchantmgt/merchantmgthome.jsp";

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
            //call to existing session
            /////////////////////////////////////////////////////////////////////
            sessionObj = request.getSession(false);
            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                //check system user is in same session or not
                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
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
            /////////////////////////////////////////////////////////////////////

            try {
                String merchantCustomerNumber = request.getParameter("merchantCustomerNumber");

                merchantManager = new MerchantCustomerManager();

                //////////get Not Assigned Merchannt Category List

                notAssigndnMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                notAssigndnMerchantCatogoryList = merchantManager.getNotAssignedMccList(merchantCustomerNumber);

                ////////////get Assigned Category List

                assigndnMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                assigndnMerchantCatogoryList = merchantManager.getAssignedMccList(merchantCustomerNumber);

                /////////////get Not Assigned Transaction Type List

                notAssigndnTxnTypeList = new ArrayList<TypeMgtBean>();
                notAssigndnTxnTypeList = merchantManager.getNotAssignedTxnTypeList(merchantCustomerNumber);

                /////////////get Assigned Transaction Type List

                assigndnTxnTypeList = new ArrayList<TypeMgtBean>();
                assigndnTxnTypeList = merchantManager.getAssignedTxnTypeList(merchantCustomerNumber);

                //////////////get Not Assignd Currency List
                notAssignCurrencyList = new ArrayList<CurrencyBean>();
                notAssignCurrencyList = merchantManager.getNotAssignedCurrencyList(merchantCustomerNumber);

                ///////////////get Assigned Currency List
                assignCurrencyList = new ArrayList<CurrencyBean>();
                assignCurrencyList = merchantManager.getAssignedCurrencyList(merchantCustomerNumber);


                beanList = new ArrayList<MerchantCustomerBean>();
                beanList = merchantManager.getMerchantCustomerDetails();
                merchantBean = new MerchantCustomerBean();

                for (MerchantCustomerBean newMerchantBean : beanList) {

                    if (newMerchantBean.getMerchantCustomerNumber().equals(merchantCustomerNumber)) {
                        merchantBean = newMerchantBean;
                    }
                }
                if (merchantBean != null) {
                    request.setAttribute("operationtype", "view");
                    request.setAttribute("selectedtab", "0");
                    request.setAttribute("merchantBean", merchantBean);
                    request.setAttribute("notAssigndnMerchantCatogoryList", notAssigndnMerchantCatogoryList);
                    request.setAttribute("assigndnMerchantCatogoryList", assigndnMerchantCatogoryList);
                    request.setAttribute("notAssigndnTxnTypeList", notAssigndnTxnTypeList);
                    request.setAttribute("assigndnTxnTypeList", assigndnTxnTypeList);
                    request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                    request.setAttribute("assignCurrencyList", assignCurrencyList);
                    rd = getServletContext().getRequestDispatcher(url);
                }

                rd.forward(request, response);

            } catch (Exception e) {
                request.setAttribute("operationtype", "search");
                request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
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

        } catch (Exception ex) {
        } finally {
            out.close();
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
