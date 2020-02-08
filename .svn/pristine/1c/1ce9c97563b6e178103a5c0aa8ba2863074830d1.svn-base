/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.terminalmgt.terminaldata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.TerminalDataCaptureBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.businesslogic.TerminalDataCaptureManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * delete a terminal data
 * @author nisansala
 */
public class DeleteTerminalDataCaptureServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SystemAuditBean systemAuditBean = null;
    private String terminalId;
    private List<String> userTaskList;
    TerminalDataCaptureManager terminalManager;
    private String url = "/mtmm/terminalmgt/terminalmgthome.jsp";
    private List<TerminalDataCaptureBean> terminalList = null;
    TerminalDataCaptureBean terminalBean = null;
    private String oldValue;
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
                String pageCode = PageVarList.TRMINAL_MGT;
                String taskCode = TaskVarList.DELETE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    terminalId = request.getParameter("id");
                    String terminalStatus = request.getParameter("status");
                    terminalBean = new TerminalDataCaptureBean();
                    terminalBean.setTerminalID(terminalId);

                    request.setAttribute("operationtype", "search");

            this.getAllTerminalData();
            //because two disable input fields
            for (TerminalDataCaptureBean terminalBeanBean : terminalList) {
                if (terminalBeanBean.getTerminalID().equals(terminalId)) {

            oldValue = terminalBeanBean.getTerminalID() + "|" + terminalBeanBean.getName() + "|" + terminalBeanBean.getSerialNo() + "|"
                    + terminalBeanBean.getManufacturer() + "|" + terminalBeanBean.getModel() + "|" + terminalBeanBean.getInstallationDate() + "|" + 
                    terminalBeanBean.getAllocationStatus() + "|" + terminalBeanBean.getTerminalStatus() + "|" + terminalBeanBean.getLastUpdateUser();


                }
            }                    
                    
                    //set the values for the audit trace
                    this.setAudittraceValue(request);

                    try {
                        boolean delResult = false;
                        //if the terminal status is 'deactivate' then delete
                        if (terminalStatus.equals(StatusVarList.DEACTIVE_STATUS)) {
                            delResult = deleteTerminal(terminalBean, systemAuditBean);
                            this.getAllTerminalData();

                            //if the deletion is success, proceed
                            if (delResult) {
                                request.setAttribute("searchList", terminalList);
                                request.setAttribute("successMsg", " " + MessageVarList.TERMINAL_MGT_TERMINAL_SUCCESS_DELETE + "Terminal ID " + terminalId);
                                sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                            }

                        } else if (terminalStatus.equals(StatusVarList.DELETE_STATUS)) {
                            this.getAllTerminalData();
                            request.setAttribute("searchList", terminalList);
                            request.setAttribute("errorMsg", " " + "Terminal ID " + terminalId + " " + MessageVarList.TERMINAL_MGT_TERMINAL_ALREADY_DELETED);
                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                        }else if (terminalStatus.equals(StatusVarList.BLOCK_STATUS)) {
                            this.getAllTerminalData();
                            request.setAttribute("searchList", terminalList);
                            request.setAttribute("errorMsg", " " + "Terminal ID " + terminalId + " " + MessageVarList.TERMINAL_MGT_TERMINAL_BLOCKED);
                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                        }
                        else {
                            this.getAllTerminalData();
                            request.setAttribute("searchList", terminalList);
                            request.setAttribute("errorMsg", " " + "Terminal ID " + terminalId + " " + MessageVarList.TERMINAL_MGT_TERMINAL_STILL_ACTIVE);
                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                        }
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);

                    } catch (SQLException e) {
                        
                        OracleMessage message = new OracleMessage();
                        String oraMessage = message.getMessege(e.getMessage());
                        request.setAttribute("errorMsg", oraMessage);
                        request.setAttribute("operationtype", "search");
                                                
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                        
                    } catch (Exception ee) { 
                        
                        request.setAttribute("operationtype", "search");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        
                        rd = getServletContext().getRequestDispatcher(url);
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
     * check user has the valid access to delete
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
     * set values to the audit trace
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(terminalBean.getTerminalID());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Delete Terminal" + " Terminal ID = " + terminalBean.getTerminalID() + "; by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.MERCHANT_TERMINAL_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TERMINALMGT);
            systemAuditBean.setPageCode(PageVarList.TRMINAL_MGT);
            systemAuditBean.setTaskCode(TaskVarList.DELETE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue(oldValue);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * delete a terminal
     * @param terminalBean
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean deleteTerminal(TerminalDataCaptureBean terminalBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            terminalManager = new TerminalDataCaptureManager();
            success = terminalManager.deleteTerminal(terminalId, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }

    private void getAllTerminalData() throws Exception {
        try {

            terminalList = new ArrayList<TerminalDataCaptureBean>();
            terminalManager = new TerminalDataCaptureManager();
            //retrieve merchant details
            terminalList = terminalManager.getAllTerminalData();
        } catch (Exception e) {
            throw e;
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
