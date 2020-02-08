/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.cardlimitincrement.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.cardlimitincrement.bean.TempLimitIncrementBean;
import com.epic.cms.backoffice.cardlimitincrement.businesslogic.TempLimitIncrementManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
 * @author badrika
 */
public class SearchTempLimitIncrementServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private RequestDispatcher rd;
    private String url = "/backoffice/cardlimitincrement/approvetemplimitincrement.jsp";
    private TempLimitIncrementManager incrementManager;
//    private PermLimitIncrementBean permBean;
    private TempLimitIncrementBean permBean;
    private List<TempLimitIncrementBean> cardList;
    private String errorMessage;

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

            //request.setAttribute("operationtype", "search");
            HttpSession sessionObj = request.getSession(false);
            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();
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
            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.SEARCH)) {
                throw new AccessDeniedException();
            }

            ///////////
            request.setAttribute("operationtype", "search");

            this.setValuesToBean(request);

            if (this.validateUserInput(permBean)) {

                this.searchCards();
                sessionVarlist.setTempBeanList(cardList);
                request.setAttribute("cardList", cardList);

            } else {
                request.setAttribute("permBean", permBean);
                request.setAttribute("errorMsg", errorMessage);
            }

            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

            rd = request.getRequestDispatcher(url);
            ///////////////


        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadTempLimitIncrementServlet");
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

    private boolean isValidTaskByUser(List<String> userTaskList, String task) throws Exception {
        boolean isValidTask = false;
        try {
            for (String usertask : userTaskList) {
                if (task.equals(usertask)) {
                    isValidTask = true;
                }
            }
            return isValidTask;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void searchCards() throws Exception {
        try {
            incrementManager = new TempLimitIncrementManager();
            cardList = incrementManager.searchCards(permBean);
        } catch (Exception e) {
            throw e;
        }
    }

    private void setValuesToBean(HttpServletRequest request) throws Exception {
        try {
            permBean = new TempLimitIncrementBean();
            permBean.setCardNumber(request.getParameter("cardNumber"));
            permBean.setCreditOrCash(request.getParameter("creditOrCash"));
            permBean.setIncordec(request.getParameter("incordec"));
            permBean.setAmount(request.getParameter("amount"));
            permBean.setLoggeduser(sessionUser.getUserName());

            if (!request.getParameter("fromdate1").equals("")) {
                permBean.setFromDate(Date.valueOf(request.getParameter("fromdate1")));
            }
            if (!request.getParameter("fromdate2").equals("")) {
                permBean.setToDate(Date.valueOf(request.getParameter("fromdate2")));
            }

        } catch (Exception e) {
            throw e;
        }
    }

    private boolean validateUserInput(TempLimitIncrementBean bean) {

        boolean isValidate = true;

        try {
            if (bean.getCardNumber().length() > 0 && !UserInputValidator.isAlphaNumeric(bean.getCardNumber())) {
                isValidate = false;
                errorMessage = "Enter valid card number";
            } else if (bean.getAmount().length() > 0 && (!UserInputValidator.isNumeric(bean.getAmount()))&&!UserInputValidator.isDecimalNumeric(bean.getAmount())) {
                isValidate = false;
                errorMessage = "Enter valid amount";
            }
        } catch (Exception e) {
            isValidate = false;
        }

        return isValidate;

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


