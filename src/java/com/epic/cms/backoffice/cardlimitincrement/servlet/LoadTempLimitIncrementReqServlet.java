/*
 * To change this template, choose Tools | Templates
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
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
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
public class LoadTempLimitIncrementReqServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private HttpSession sessionObj = null;
    private TempLimitIncrementBean tempBean = null;
    private TempLimitIncrementBean tempHistoryBean = null;
    private TempLimitIncrementManager tempIncrementManager = null;
    private List<TempLimitIncrementBean> tempHistoryList = null;
    private String cardNumber = null;
    private String section = "CCCARD";
    private String url = "/backoffice/cardlimitincrement/temporarylimitincrementhome.jsp";

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
                //set page code and task codes
                String pageCode = PageVarList.TEMP_CARD_INCREMANT;
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {

                        cardNumber = sessionVarlist.getCardNumber();


//                        if (this.checkLimitIncrementHistory(cardNumber)) { // for check whether a temp limit increment is been requiested before.
                        if (true) {
                            this.getCardDetails(cardNumber);
                            if (tempBean.isFlag()) {
                                request.setAttribute("tempBean", tempBean);
                                request.setAttribute("selectedtab", "0");
                                rd = getServletContext().getRequestDispatcher(url);
                            } else {

                                request.setAttribute("errorMsg", MessageVarList.CARD_NOT_ACTIVE_STATE);
                                rd = getServletContext().getRequestDispatcher("/ViewCustomerMgtServlet?section=" + section);
                            }

                        } else if (tempHistoryBean.getStatus().equals(StatusVarList.ACTIVE_STATUS)) {

                            request.setAttribute("errorMsg", MessageVarList.LIMIT_INCREMENT_ALREADY_TAKEN + " " + tempHistoryBean.getCreditOrCash() + " INCREMENT the amount of " + tempHistoryBean.getIncrementedAmount());
                            rd = getServletContext().getRequestDispatcher("/ViewCustomerMgtServlet?section=" + section);

                        } else if (tempHistoryBean.getStatus().equals(StatusVarList.INITIAL_STATUS)) {

                            request.setAttribute("errorMsg", MessageVarList.LIMIT_INCREMENT_REQUESTED + " " + tempHistoryBean.getCreditOrCash() + " INCREMENT the amount of " + tempHistoryBean.getIncrementedAmount());
                            rd = getServletContext().getRequestDispatcher("/ViewCustomerMgtServlet?section=" + section);
                        }
                    } catch (Exception ex) {
                        OracleMessage message = new OracleMessage();
                        String oraMessage = message.getMessege(ex.getMessage());
                        request.setAttribute("tempBean", tempBean);
                        request.setAttribute("selectedtab", "0");
                        request.setAttribute("errorMsg", oraMessage);
                        rd = getServletContext().getRequestDispatcher(url);

                    }

                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }

                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);

            } catch (AccessDeniedException adex) {
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
            request.setAttribute("selectedtab", "0");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();

        }
    }

    private TempLimitIncrementBean getCardDetails(String cardNumber) throws Exception {
        try {
            tempBean = new TempLimitIncrementBean();
            tempIncrementManager = new TempLimitIncrementManager();
            tempBean = tempIncrementManager.getCardDetails(cardNumber);
        } catch (Exception ex) {
            throw ex;
        }
        return tempBean;

    }

    private boolean checkLimitIncrementHistory(String cardNumber) throws Exception {
        boolean flag = true;
        try {
            tempHistoryList = new ArrayList<TempLimitIncrementBean>();
            tempIncrementManager = new TempLimitIncrementManager();
            tempHistoryList = tempIncrementManager.checkLimitIncrementHistory();
            tempHistoryBean = new TempLimitIncrementBean();

            if (tempHistoryList.isEmpty()) {
                flag = true;
            } else {

                for (TempLimitIncrementBean bean : tempHistoryList) {

                    if (bean.getCardNumber().equals(cardNumber)) {
                        tempHistoryBean = bean;
                        flag = false;
                    } 
                }

            }

        } catch (Exception ex) {
            throw ex;
        }
        return flag;
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
