/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantlocation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.bean.MerchantLocationBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.businesslogic.MerchantLocationManager;
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
public class ViewMerchantLocationServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private MerchantLocationManager merchantLocManager;
    private List<MerchantLocationBean> beanList;
    private MerchantLocationBean merchantLocBean = null;
    private List<MerchantCategoryBean> notAssigndnedMerchantCatogoryList = null;
    private List<MerchantCategoryBean> assigndnedMerchantCatogoryList = null;
    private List<TypeMgtBean> notAssigndnedTxnTypeList = null;
    private List<TypeMgtBean> assigndnedTxnTypeList = null;
    private List<CurrencyBean> notAssignCurrencyList = null;
    private List<CurrencyBean> assignCurrencyList = null;
    private String url = "/mtmm/merchantmgt/merchantlocationhome.jsp";

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
                String merchantId = request.getParameter("merchantId");

                merchantLocManager = new MerchantLocationManager();


                beanList = new ArrayList<MerchantLocationBean>();
                beanList = merchantLocManager.getMerchantLocationDetails();

                merchantLocBean = new MerchantLocationBean();


                for (MerchantLocationBean newMerchantLocBean : beanList) {

                    if (newMerchantLocBean.getMerchantId().equals(merchantId)) {
                        merchantLocBean = newMerchantLocBean;
                    }
                }

                //////////get Not Assigned Merchannt Category List

                notAssigndnedMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                notAssigndnedMerchantCatogoryList = merchantLocManager.getNotAssignedMccList(merchantId, merchantLocBean.getMerchantCustomerNumber());

                ////////////get Assigned Category List

                assigndnedMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                assigndnedMerchantCatogoryList = merchantLocManager.getAssignedMccList(merchantId);

                /////////////get Not Assigned Transaction Type List

                notAssigndnedTxnTypeList = new ArrayList<TypeMgtBean>();
                notAssigndnedTxnTypeList = merchantLocManager.getNotAssignedTxnTypeList(merchantId, merchantLocBean.getMerchantCustomerNumber());

                /////////////get Assigned Transaction Type List

                assigndnedTxnTypeList = new ArrayList<TypeMgtBean>();
                assigndnedTxnTypeList = merchantLocManager.getAssignedTxnTypeList(merchantId);

                ////////////get Not Assigned Currency List

                notAssignCurrencyList = new ArrayList<CurrencyBean>();
                notAssignCurrencyList = merchantLocManager.getNotAssignedCurrencyList(merchantId, merchantLocBean.getMerchantCustomerNumber());

                ///////////get Assigned Currency List
                assignCurrencyList = new ArrayList<CurrencyBean>();
                assignCurrencyList = merchantLocManager.getAssignedCurrencyList(merchantId);

                if (merchantLocBean != null) {
                    request.setAttribute("operationtype", "view");
                    request.setAttribute("selectedtab", "0");
                    request.setAttribute("merchantLocBean", merchantLocBean);
                    request.setAttribute("notAssigndnedMerchantCatogoryList", notAssigndnedMerchantCatogoryList);
                    request.setAttribute("assigndnedMerchantCatogoryList", assigndnedMerchantCatogoryList);
                    request.setAttribute("notAssigndnedTxnTypeList", notAssigndnedTxnTypeList);
                    request.setAttribute("assigndnedTxnTypeList", assigndnedTxnTypeList);
                    request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                    request.setAttribute("assignCurrencyList", assignCurrencyList);
                    //request.setAttribute("beanList", beanList);
                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);
                }

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
