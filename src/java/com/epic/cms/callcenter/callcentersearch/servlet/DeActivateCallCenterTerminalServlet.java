/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.callcentersearch.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.mtmm.terminalmgt.terminaldata.businesslogic.TerminalDataCaptureManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
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
 * @author nalin
 */
public class DeActivateCallCenterTerminalServlet extends HttpServlet {

    private RequestDispatcher rd;
    private TerminalDataCaptureManager allocationmanager = null;
    HttpSession sessionObj = null;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String merchantId = null;
    private List<String> userTaskList;
    private String terminalId = null;
    private String url = "/aquirecallcenter/terminaldeactivation.jsp";
    private String backUrl = "/ViewMerchantMgtServlet?section=ACCTER&id=";

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
                String pageCode = PageVarList.CALLCENTER_TERMINAL_DEACTIVATION;
                String taskCode = TaskVarList.DEACTIVATION;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    terminalId = sessionVarlist.getTerminalId();
                    //this.setAudittraceValue(request);

                    if (!this.isTransactionsSettled(merchantId, terminalId)) {
                        request.setAttribute("errorMsg", MessageVarList.TRANSACTION_NOT_SETTLED);
                    } else {//no assigned transactions for this terminal
                        if (isTerminalActive(terminalId)) {//terminal is acitve
                            if (isTerminalAllocated(terminalId)) {//terminal Id previously not allocated
                                request.setAttribute("errorMsg", MessageVarList.CANTDEACTIVATE);
                                rd = getServletContext().getRequestDispatcher(backUrl + terminalId);
                            } else {//prevoiusly allocated
                                this.setAudittraceValue(request);
                                if (updateTerminalStatus(terminalId)) {
                                    request.setAttribute("successMsg", MessageVarList.TERMINAL_SUCSESS_DEACTIVATED);
                                    merchantId = sessionVarlist.getMerchantId();
                                    rd = getServletContext().getRequestDispatcher(backUrl + terminalId + "&mid=" + merchantId);
                                } else {
                                    request.setAttribute("errorMsg", MessageVarList.TERMINAL_DEACTIVATION_FAIL);
                                    merchantId = sessionVarlist.getMerchantId();
                                    rd = getServletContext().getRequestDispatcher(backUrl + terminalId + "&mid=" + merchantId);
                                }
                            }
                        } else {
                            request.setAttribute("errorMsg", MessageVarList.TERMINAL_NOTACTIVE);
                            merchantId = sessionVarlist.getMerchantId();
                            rd = getServletContext().getRequestDispatcher(backUrl + terminalId + "&mid=" + merchantId);
                        }
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
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);



        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);


        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);


        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
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

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(terminalId);

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Deactivated Terminal." + " Terminal ID : " + terminalId + "; by :" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ACQUIRE_CALL_CENTER_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.ACQ_CALL_CENTER_TERMINAL);
            systemAuditBean.setPageCode(PageVarList.CALLCENTER_TERMINAL_DEACTIVATION);
            systemAuditBean.setTaskCode(TaskVarList.DEACTIVATION);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue("");


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    public Boolean isTerminalAllocated(String terminalId) throws Exception {
        allocationmanager = new TerminalDataCaptureManager();
        Boolean status = false;

        try {
            status = allocationmanager.isTerminalAllocated(terminalId);
        } catch (Exception e) {
            throw e;
        }

        return status;
    }

    public Boolean isTerminalActive(String terminalId) throws Exception {
        allocationmanager = new TerminalDataCaptureManager();
        Boolean status = false;

        try {
            status = allocationmanager.isTerminalActive(terminalId);
        } catch (Exception e) {
            throw e;
        }

        return status;
    }

    public Boolean isMerchantActive(String merchantId) throws Exception {
        allocationmanager = new TerminalDataCaptureManager();
        Boolean status = false;

        try {
            status = allocationmanager.isMerchantActive(merchantId);
        } catch (Exception e) {
            throw e;
        }

        return status;
    }

    public Boolean updateTerminalTable() throws Exception {
        Boolean status = false;
        try {
            status = allocationmanager.updateTerminalTable(terminalId, merchantId, systemAuditBean);
        } catch (Exception e) {
            throw e;
        }
        return status;
    }

    public Boolean updateTerminalStatus(String terminalID) throws Exception {
        Boolean status = false;
        try {
            status = allocationmanager.updateTerminalStatus(terminalID, systemAuditBean, StatusVarList.STATUS_DEACTIVE, "2");
        } catch (Exception e) {
            throw e;
        }
        return status;
    }
    
     public boolean isTransactionsSettled(String merchantId, String terminalID) throws Exception {
        boolean isSettled = false;
        try {
            allocationmanager = new TerminalDataCaptureManager();
            isSettled = allocationmanager.isTransactionsSettled(merchantId, terminalID);
        } catch (Exception e) {
            throw e;
        }
        return isSettled;
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
