/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.keymailer.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.mtmm.terminalmgt.terminaldata.businesslogic.TerminalDataCaptureManager;
import com.epic.cms.switchcontrol.keymailer.bean.TerminalKeyMailerBean;
import com.epic.cms.switchcontrol.keymailer.businesslogic.TerminalKeyMailerManager;
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
public class SearchTerminalKeyMailerServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    HttpSession sessionObj = null;
    private SessionUser sessionUser = null;
    private List<String> userTaskList = null;
    private String errorMessage = null;
    private TerminalDataCaptureManager terminalManager = null;
    private TerminalKeyMailerManager keyMailerManager = null;
    private HashMap<String, String> modelList = null;
    private TerminalKeyMailerBean keyMailerBean = null;
    private List<TerminalKeyMailerBean> searchList = null;
    private String url = "/switch/keymailer/keymailer/terminalsearch.jsp";

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

            this.getAllModelList();

            request.setAttribute("modelList", modelList);


            try {
                //set page code and task codes
                String pageCode = PageVarList.KEY_MAILER;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    this.setUserInputToBean(request);
                    request.setAttribute("keyMailerBean", keyMailerBean);

                    if (this.validateUserInput(keyMailerBean, request)) {

                        this.searchTerminal(keyMailerBean);

                        request.setAttribute("searchList", searchList);


                    } else {

                        request.setAttribute("errorMsg", errorMessage);
                    }
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

            request.setAttribute("errorMsg", MessageVarList.ERROR_SEARCH_KEY_MAILER);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void getAllModelList() throws Exception {
        try {
            terminalManager = new TerminalDataCaptureManager();
            modelList = terminalManager.getAllModelList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void searchTerminal(TerminalKeyMailerBean keyMailerBean) throws Exception, Exception {

        keyMailerManager = new TerminalKeyMailerManager();
        searchList = keyMailerManager.searchTerminal(keyMailerBean);

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

    public void setUserInputToBean(HttpServletRequest request) {

        keyMailerBean = new TerminalKeyMailerBean();

        keyMailerBean.setTerminalID(request.getParameter("terminalID"));
        keyMailerBean.setMerchantID(request.getParameter("merchantID"));
        keyMailerBean.setTerminalName(request.getParameter("terminalName"));
        keyMailerBean.setSerialNo(request.getParameter("serialNo"));
        keyMailerBean.setModel(request.getParameter("model"));


        //sessionVarlist.setTermBean(terminalBean);
        //sessionVarlist.setSearchBean(terminalBean);


    }

    public boolean validateUserInput(TerminalKeyMailerBean keyMailerBean, HttpServletRequest request) throws Exception {
        boolean isValid = true;


        //validate user Role code
        try {

            if (!UserInputValidator.isAlphaNumeric(keyMailerBean.getTerminalID())) {
                isValid = false;
                errorMessage = MessageVarList.TERMINAL_MGT_TRMNLID_INVALID;
            } else if (!UserInputValidator.isAlphaNumeric(keyMailerBean.getMerchantID())) {
                isValid = false;
                errorMessage = MessageVarList.TERMINAL_ALLOCATION_INVALID_MERCHANTID;
            } else if (!UserInputValidator.isAlphaNumeric(keyMailerBean.getTerminalName())) {
                isValid = false;
                errorMessage = MessageVarList.TERMINAL_MGT_TRMNLNAME_INVALID;
            } else if (!UserInputValidator.isAlphaNumeric(keyMailerBean.getSerialNo())) {
                isValid = false;
                errorMessage = MessageVarList.TERMINAL_MGT_SERIALNO_INVALID;
            }


        } catch (Exception ex) {
            isValid = false;

        }

        return isValid;
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
