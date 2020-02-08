/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.backoffice.installementbtpayment.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.installement.btpayment.bean.BTPaymentPlanBean;
import com.epic.cms.backoffice.installement.btpayment.businesslogic.BTPaymentPlanManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
public class UpdateBTPaymentPlanServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private BTPaymentPlanManager paymentPlanManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private BTPaymentPlanBean paymentplanBean = null;
    private List<BTPaymentPlanBean> paymentPlanList = null;
    private String errorMessage = null;
    private String url = "/backoffice/installementpayment/btpaymentplanhome.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

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

            String paymentPlanCode = request.getParameter("paymentplancode");
            
            this.getPaymentPlans();

            for (BTPaymentPlanBean paymentplan : paymentPlanList) {
                if (paymentplan.getPaymentplancode().equals(paymentPlanCode)) {
                    paymentplanBean = paymentplan;
                    break;
                }
            }
            if (paymentplanBean != null) {
                
                paymentplanBean.setLastUpdatedUser(sessionUser.getUserName());
                paymentplanBean.setLastUpdatedTime(new Date());

                String oldValue = paymentplanBean.getPaymentplancode()+ "|" + paymentplanBean.getDescription() + "|" +paymentplanBean.getDuration()+"|"+paymentplanBean.getInterestrate()+"|"+paymentplanBean.getStatusCode()+"|"+paymentplanBean.getLastUpdatedUser()+"|"+paymentplanBean.getLastUpdatedTime();

                request.setAttribute("oldval", oldValue);

                request.setAttribute("operationtype", "update");
                request.setAttribute("paymentPlanBean", paymentplanBean);
                request.setAttribute("paymentPlanList", paymentPlanList);
                rd = getServletContext().getRequestDispatcher(url);
            }

        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } catch (Exception ex) {
            rd = request.getRequestDispatcher(url);
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ERROR_VIEW_USERROLE);

        } finally {
            rd.forward(request, response);
            out.close();
        }

    }

    private void getPaymentPlans() throws Exception {
        try {
            paymentPlanManager = new BTPaymentPlanManager();
            paymentPlanList = paymentPlanManager.getAllPaymentPlans();
        } catch (Exception ex) {
            throw ex;
        }
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
