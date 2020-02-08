/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.installmentlocplan.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.installementpayment.bean.LoadOnCardPaymentPlanBean;
import com.epic.cms.backoffice.installementpayment.businesslogic.LoanOnCardPlanManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ViewLOCPaymentPlanServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private LoanOnCardPlanManager paymentPlanManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private UserRoleBean userRoleBean = null;
    private LoadOnCardPaymentPlanBean paymentPlanBean = null;
    private List<LoadOnCardPaymentPlanBean> paymentPlanList = null;
    private String errorMessage = null;
    private String url = "/backoffice/installementpayment/locplanpayment.jsp";

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

            this.getAllPaymentPlans();

            for (LoadOnCardPaymentPlanBean paymentBean : paymentPlanList) {
                if (paymentBean.getPlancode().equals(paymentPlanCode)) {
                    paymentPlanBean = paymentBean;
                    break;
                }
            }
            if (paymentPlanBean != null) {
                request.setAttribute("operationtype", "view");
                request.setAttribute("paymentPlanBean", paymentPlanBean);
                request.setAttribute("paymentPlanList", paymentPlanList);
                rd = getServletContext().getRequestDispatcher(url);
            }

        }//catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } catch (Exception ex) {
            rd = request.getRequestDispatcher(url);
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ERROR_VIEW_LOCPAYMENTPLAN);

        } finally {
            rd.forward(request, response);
            out.close();

        }
    }

    private void getAllPaymentPlans() throws Exception {
        try {
            paymentPlanManager = new LoanOnCardPlanManager();
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
