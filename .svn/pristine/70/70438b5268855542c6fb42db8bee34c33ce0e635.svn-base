/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.cardstandingorder.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.backoffice.cardstandingorder.bean.CardDetailsBean;
import com.epic.cms.backoffice.cardstandingorder.bean.CardStandingOrderBean;
import com.epic.cms.backoffice.cardstandingorder.businesslogic.CardStandingOrderManager;
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
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @author badrika
 */
public class LoadCardStandingOrderServlet extends HttpServlet {

    private String url = "/backoffice/standingorder/standingorder.jsp";
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private HashMap<String, String> currencyList;
    private HashMap<String, String> orderTypeList;
    private String paramet = "";
    private String cardnum = "";
    private CardDetailsBean cardbean = null;
    private int orderId;
    private List<CardStandingOrderBean> orderList;

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
            //request.setAttribute("operationtype", "fill");
            HttpSession sessionObj = request.getSession(false);

            try {
                request.setAttribute("operationtype", "add");
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
                    String pageCode = PageVarList.CARD_STADING_ORDER;
                    String taskCode = TaskVarList.ACCESSPAGE;
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
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

            /////////////////

            CardStandingOrderManager manager = new CardStandingOrderManager();
//            currencyList = manager.getAllCurrencyList();
//            request.setAttribute("currencyList", currencyList);

            orderTypeList = manager.getAllStandingOrderTypeListOfCardPaymentCategory();
            request.setAttribute("orderTypeList", orderTypeList);

            orderId = manager.getOrderId();
            request.setAttribute("orderId", orderId);

            Date date = new Date();

            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            ft.format(date);

            request.setAttribute("date", ft.format(date));

            orderList = manager.getAllOrderLists();
            request.setAttribute("orderList", orderList);

            ///// get Status list
            SystemUserManager sysUserObj = new SystemUserManager();
            List<StatusBean> statusList = sysUserObj.getStatusByUserRole("GENR");
            sessionVarlist.setStatusList(statusList);

            paramet = request.getParameter("paramet");
            cardnum = request.getParameter("cardNum");


            if (paramet != null && paramet.equals("details")) {

                if (cardnum != null) {

                    cardbean = manager.getCardDetails(cardnum);

                    if (cardbean.getCardnum() != null) {
                        request.setAttribute("cardexist", "yes");
                        request.setAttribute("cardbean", cardbean);
                        request.setAttribute("cardnum", cardnum);
                    } else {
                        request.setAttribute("cardexist", "no");
                        request.setAttribute("errorMsg", MessageVarList.STANDING_ORDER_CARD_NUMBER_INVALID);
                        request.setAttribute("cardnum", cardnum);
                    }
                }

                // request.setAttribute("cardexist", "yes");

            }





            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
            rd = request.getRequestDispatcher(url);
            /////////////

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);

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
