/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.callcentersearch.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
//import com.epic.cms.callcenter.callcentersearch.bean.MerchantSearchBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.MerchantSearchBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.businesslogic.TerminalDataCaptureManager;
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
import java.util.ArrayList;
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
public class SearchCallCenterMerchantSearchServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private TerminalDataCaptureManager terminalManager = null;
    private HashMap<String, String> terminalStatusList = null;
    private String terminalId = null;
    private MerchantSearchBean searchBean = null;
    private TerminalDataCaptureManager merchantManager = null;
    private List<MerchantSearchBean> searchList = null;
    private String errorMessage = null;
    private String url = "/aquirecallcenter/acquiremerchantsearch.jsp";

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
                String pageCode = PageVarList.CALLCENTER_TERMINAL_ALLOCATION;
                String taskCode = TaskVarList.SEARCH;

                //check whether user_role have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                    this.setUserInputToBean(request);
                    this.getTerminalStatus();

                    request.setAttribute("terminalId", terminalId);
                    request.setAttribute("terminalStatusList", terminalStatusList);
                    request.setAttribute("searchBean", searchBean);
                    if (validateUserInput(searchBean)) {

                        this.searchMerchants(searchBean);
                        request.setAttribute("searchList", searchList);

                    } else {
                        request.setAttribute("errorMsg", errorMessage);
                    }
                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);



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
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);
        } //catch session exception
        catch (NewLoginSessionException nlex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);
        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (Exception ex) {
        } finally {
            out.close();
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

    private void getTerminalStatus() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            terminalStatusList = terminalManager.getTerminalStatus();
        } catch (Exception e) {
        }

    }

    public void setUserInputToBean(HttpServletRequest request) throws Exception {
        try {

            terminalId = request.getParameter("id");


            searchBean = new MerchantSearchBean();

            searchBean.setMerchantId(request.getParameter("merchantId").trim());
            searchBean.setMerchantname(request.getParameter("merchantName").trim());
            searchBean.setMerchantLocation(request.getParameter("merchantLocation").trim());
            searchBean.setMerchantStatus(request.getParameter("merchantStatus").trim());
        } catch (Exception ex) {

            throw ex;
        }
    }

    public void searchMerchants(MerchantSearchBean searchBean) throws Exception {

        merchantManager = new TerminalDataCaptureManager();
        searchList = new ArrayList<MerchantSearchBean>();

        try {
            searchList = merchantManager.searchMerchants(searchBean);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean validateUserInput(MerchantSearchBean searchBean) throws Exception {
        boolean isValidate = true;

        //////validate user inputs

        try {

            if (!UserInputValidator.isDescription(searchBean.getMerchantname())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!UserInputValidator.isAlphaNumeric(searchBean.getMerchantId())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!UserInputValidator.isDescription(searchBean.getMerchantLocation())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            }
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
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
