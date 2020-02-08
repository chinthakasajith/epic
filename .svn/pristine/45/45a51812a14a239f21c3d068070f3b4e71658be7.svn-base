/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.terminalmgt.terminaldata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.TerminalDataCaptureBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.businesslogic.TerminalDataCaptureManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
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
 *  this class load stored values in the database before update the record
 * @author nisansala
 */
public class LoadUpdateTerminalDataServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    TerminalDataCaptureBean terminalBean = null;
    List<TerminalDataCaptureBean> assignedTxn;
    List<TerminalDataCaptureBean> notAssignedTxn;
    TerminalDataCaptureManager terminalManager;
    private String url1 = "/mtmm/terminalmgt/createterminal.jsp";
    private String url = "/mtmm/terminalmgt/terminalmgthome.jsp";
    private HashMap<String, String> modelList = null;
    private HashMap<String, String> allocateList = null;
    private HashMap<String, String> terminalStatusList = null;
    private HashMap<String, String> manufacturerList = null;
    HashMap<String, String> difManufactermModels;

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
                String pageCode = PageVarList.TRMINAL_MGT;
                String taskCode = TaskVarList.UPDATE;

                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        //load values for the drop down lists
                        this.getAllManufacturers();
                        this.getAllocationStatus();
                        this.getTerminalStatus();

                        //mid to load transactions assigned to merchants
                        String mid = request.getParameter("mid");
                        String tid = request.getParameter("id");
                        String Manufacturer = "";
                        terminalManager = new TerminalDataCaptureManager();

                        //load transactions assigned for the terminal
                        assignedTxn = terminalManager.getAssignedTransactions(tid);
                        //load transactions assigned for the merchant
                        notAssignedTxn = terminalManager.getNotAssignedTransactions(mid, tid);

                        request.setAttribute("manufacturerList", manufacturerList);
                        request.setAttribute("allocateList", allocateList);
                        request.setAttribute("terminalStatusList", terminalStatusList);
                        request.setAttribute("assignedTxn", assignedTxn);
                        request.setAttribute("notAssignedTxn", notAssignedTxn);


                        String terminalStatus = request.getParameter("status");

                        //if terminal is already deleted
                        if (terminalStatus.equals(StatusVarList.DELETE_STATUS)) {
                            request.setAttribute("operationtype", "search");
                            request.setAttribute("isBack", "back");
                            request.setAttribute("errorMsg", MessageVarList.TERMINAL_MGT_TERMINAL_ALREADY_DELETED);
                            rd = getServletContext().getRequestDispatcher("/SearchTerminalDataCaptureServlet");
                            rd.include(request, response);

                        }//if terminal is blocked
                        if (terminalStatus.equals(StatusVarList.BLOCK_STATUS)) {
                            request.setAttribute("operationtype", "search");
                            request.setAttribute("isBack", "back");
                            request.setAttribute("errorMsg", MessageVarList.TERMINAL_MGT_TERMINAL_BLOCKED);
                            rd = getServletContext().getRequestDispatcher("/SearchTerminalDataCaptureServlet");
                            rd.include(request, response);

                        } else {
                            terminalBean = new TerminalDataCaptureBean();
                            terminalManager = new TerminalDataCaptureManager();
                            //load values for the given terminal id
                            terminalBean = terminalManager.viewOneTerminalData(tid);

                            if (request.getParameter("isDefault").equals("yes")) {
                                Manufacturer = request.getParameter("manu");
                                sessionVarlist.setTermBean(terminalBean);
                            } else if (request.getParameter("isDefault").equals("no")) {
                                Manufacturer = sessionVarlist.getTermBean().getManufacturer();

                            }

                            //load terminal models according to the manufacturer
                            difManufactermModels = terminalManager.getModelsToManufacturer(Manufacturer);
                            
                            request.setAttribute("difManufactermModels", difManufactermModels);

                            if (terminalBean != null) {

                                request.setAttribute("operationtype", "update");
                                request.setAttribute("isAllocate", terminalBean.getAllocationStatus());
                                request.setAttribute("terminalBean", terminalBean);

                                rd = getServletContext().getRequestDispatcher(url1);
                            }
                            rd.forward(request, response);

                        }

                    } catch (Exception e) {

                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url1);
                        rd.forward(request, response);
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
        } finally {
            out.close();
        }
    }

    /**
     * check whether user has access to update terminals
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
     * get descriptions of terminal allocation status
     */
    private void getAllocationStatus() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            allocateList = terminalManager.getAllocationStatus();
        } catch (Exception e) {
        }
    }

    /**
     * get descriptions of terminal status
     */
    private void getTerminalStatus() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            terminalStatusList = terminalManager.getTerminalStatus();
        } catch (Exception e) {
        }
    }

    /**
     * get descriptions of manufacturers
     */
    private void getAllManufacturers() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            manufacturerList = terminalManager.getAllManufacturers();
        } catch (Exception e) {
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
