/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.CardDistributionReportBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationCreditScoreBreakDownManager;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationDetailsManager;
import com.epic.cms.reportexp.cardapplication.businesslogic.CardDistributionReportManager;
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
import java.text.DateFormat;
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
 * @author nalin
 */
public class LoadCardDistributionReportServlet extends HttpServlet {

    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private CardDistributionReportManager distributeManager = null;
    private ApplicationDetailsManager appdetailsManager = null;
    private ApplicationCreditScoreBreakDownManager crditScoreManager = null;
    private HashMap<String, String> branchList = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> domainList = null;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> cardProductList = null;
    private HashMap<String, String> csUserList = null;
    private CardDistributionReportBean distributeBean = null;
    private String url = "/reportexp/cardapplication/carddistributionreporthome.jsp";

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
                    String pageCode = PageVarList.CARD_DISTRIBUTION_REPORT;
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

            ///////////////////////////////////////////////
            this.getBranchNames();
            this.getAllPriorityLevelList();
            this.getAllDomainList();
            this.getCardType();
            this.getCardProduct();
            this.getCreditScoreUserList();
            
            distributeBean = new CardDistributionReportBean();
            distributeBean.setFromDate(this.getCurrentDate());
            distributeBean.setToDate(this.getCurrentDate());

            request.setAttribute("distributeBean", distributeBean);
            request.setAttribute("branchList", branchList);
            request.setAttribute("priorityLevelList", priorityLevelList);
            request.setAttribute("domainList", domainList);
            request.setAttribute("cardTypeList", cardTypeList);
            request.setAttribute("cardProductList", cardProductList);
            request.setAttribute("csUserList", csUserList);


            rd = request.getRequestDispatcher(url);

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
            out.close();
        }
    }

    private void getBranchNames() throws Exception {
        try {
            appdetailsManager = new ApplicationDetailsManager();
            branchList = new HashMap<String, String>();
            branchList = appdetailsManager.getBranchNames();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllPriorityLevelList() throws Exception {
        try {
            appdetailsManager = new ApplicationDetailsManager();
            priorityLevelList = new HashMap<String, String>();
            priorityLevelList = appdetailsManager.getAllPriorityLevels();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllDomainList() throws Exception {
        try {
            appdetailsManager = new ApplicationDetailsManager();
            domainList = new HashMap<String, String>();
            domainList = appdetailsManager.getAllDomainList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardType() throws Exception {
        try {
            distributeManager = new CardDistributionReportManager();
            cardTypeList = new HashMap<String, String>();
            cardTypeList = distributeManager.getCardType();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardProduct() throws Exception {
        try {
            distributeManager = new CardDistributionReportManager();
            cardProductList = new HashMap<String, String>();
            cardProductList = distributeManager.getCardProduct();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCreditScoreUserList() throws Exception {
        try {
            crditScoreManager = new ApplicationCreditScoreBreakDownManager();
            csUserList = new HashMap<String, String>();
            csUserList = crditScoreManager.getCreditScoreUserList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private String getCurrentDate() throws Exception {
        String currentDate = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            //System.out.println(dateFormat.format(date));
            currentDate = dateFormat.format(date);

        } catch (Exception ex) {
            throw ex;
        }
        return currentDate;
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
