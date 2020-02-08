/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfirmation.bean.RejectReasonBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationRejectReportBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationSummaryBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationRejectReportManager;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationSummaryManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * @author nisansala
 */
public class SearchApplicationRejectReportServlet extends HttpServlet {

    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String errorMessage = null;
    private SessionVarList sessionVarlist;
    private SystemUserManager systemUserManager;
    //----------------------------------------------------
    private List<RejectReasonBean> rejectReasons;
    private ApplicationRejectReportBean inputBean;
    private ApplicationSummaryManager appSumMgr;
    private ApplicationRejectReportManager appRejMgr;
    private HashMap<String, String> bnkBranches = null;
    private HashMap<String, String> appStatusList = null;
    private HashMap<String, String> cardDomainList = null;
    private List<ApplicationRejectReportBean> searchList = null;
    private String url = "/reportexp/cardapplication/applicationrejecthome.jsp";

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

            try {
                //set page code and task codes
                String pageCode = PageVarList.APPLICATION_REJECT;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    this.getBranchNames();
                    request.setAttribute("bnkBranchList", bnkBranches);

                    this.getCardDomains();
                    request.setAttribute("cardDomainList", cardDomainList);

                    this.getApplicationStatus();
                    request.setAttribute("appStatusList", appStatusList);

                    this.getRejectReasons();
                    request.setAttribute("rejectReasons", rejectReasons);

                    this.setUserInputToBean(request);
                    request.setAttribute("appBean", inputBean);
                    if (isValidate(inputBean)) {

                        this.getApplicationRejectDetails(inputBean);
                    } else {
                        request.setAttribute("errorMsg", errorMessage);
                    }
                    request.setAttribute("appBean", inputBean);

                    request.setAttribute("searchList", searchList);
                    sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);


                    rd = getServletContext().getRequestDispatcher(url);

                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }

            } catch (AccessDeniedException adex) {
                throw adex;

            }

        }//catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);


        } catch (Exception ex) {
            request.setAttribute("operationtype", "search");
            request.setAttribute("errorMsg", MessageVarList.ERROR_APP_REJECT);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * check valid access to the page to search
     * @param userrole
     * @param pagecode
     * @param task
     * @return
     * @throws Exception 
     */
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

    /**
     * assign user given values to the bean for searching
     * @param request 
     */
    public void setUserInputToBean(HttpServletRequest request) {

        inputBean = new ApplicationRejectReportBean();

        inputBean.setNic(request.getParameter("nic"));
        inputBean.setPassport(request.getParameter("passport"));
        //inputBean.setLicence(request.getParameter("drivinglicense"));
        inputBean.setApplicationId(request.getParameter("appId"));
        inputBean.setRejReason(request.getParameter("rejectReason"));
        inputBean.setBranchCode(request.getParameter("branch"));
        inputBean.setPriorityLevelCode(request.getParameter("priority"));
        inputBean.setDomainCode(request.getParameter("domain"));
        inputBean.setFromDate(request.getParameter("fromdate"));
        inputBean.setToDate(request.getParameter("todate"));

        //***********************************************************************************************************************************
        //sessionVarlist.setTermBean(terminalBean);
        //sessionVarlist.setSearchBean(terminalBean);
        //***********************************************************************************************************************************


    }

    private void getApplicationRejectDetails(ApplicationRejectReportBean inputBean) throws SQLException, Exception {
        appRejMgr = new ApplicationRejectReportManager();
        searchList = appRejMgr.getApplicationRejectDetails(inputBean);
        //sessionVarlist.setTerminalDataBeanList(searchList);
    }

    private void getBranchNames() {
        try {
            appSumMgr = new ApplicationSummaryManager();
            bnkBranches = appSumMgr.getBranchNames();
        } catch (Exception e) {
        }

    }

    private void getCardDomains() throws Exception {
        try {

            appSumMgr = new ApplicationSummaryManager();
            cardDomainList = appSumMgr.getCardDomains();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getApplicationStatus() throws Exception {
        try {

            appSumMgr = new ApplicationSummaryManager();
            appStatusList = appSumMgr.getApplicationStatus();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean isValidate(ApplicationRejectReportBean inputBean) throws Exception {

        boolean isValidate = true;

        if (!inputBean.getNic().equals("")) {
            if (!UserInputValidator.isAlphaNumeric(inputBean.getNic())) {
                isValidate = false;
                errorMessage = MessageVarList.INVALID_NIC;
            }
        }
        if (!inputBean.getPassport().equals("")) {
            if (!UserInputValidator.isAlphaNumeric(inputBean.getPassport())) {
                isValidate = false;
                errorMessage = MessageVarList.ERROR_PAS_NUMBER;
            }
        }
//        if (!inputBean.getLicence().equals("")) {
//            if (!UserInputValidator.isAlphaNumeric(inputBean.getLicence())) {
//                isValidate = false;
//                errorMessage = MessageVarList.ERROR_DRL_NUMBER;
//            }
//        }
        if (!inputBean.getApplicationId().equals("")) {
            if (!UserInputValidator.isAlphaNumeric(inputBean.getApplicationId())) {
                isValidate = false;
                errorMessage = MessageVarList.APPASSIGN_APPLICATIONCODE_INVALID;
            }
        }

        return isValidate;
    }

    private void getRejectReasons() throws Exception {
        try {

            appRejMgr = new ApplicationRejectReportManager();
            rejectReasons = appRejMgr.getRejectReasons();

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
