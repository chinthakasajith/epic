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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mahesh_m
 */
public class DeAllocateTerminalServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    private SystemAuditBean systemAuditBean = null;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private RequestDispatcher rd;
    private String terminalid = null;
    private TerminalDataCaptureManager allocationmanager = null;
    private String url = "/mtmm/terminalmgt/terminalallocationdeallocation.jsp";
    private String newValue;
    private String oldValue;
    private List<TerminalDataCaptureBean> list;    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, AccessDeniedException, NewLoginSessionException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //call to existing session
            /////////////////////////////////////////////////////////////////////

            try {
                sessionObj = request.getSession(false);
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
                String pageCode = PageVarList.TRMINAL_ALLOCATION_DEALLOCATION;
                String taskCode = TaskVarList.DEALLOCATION;

                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    try {
                        terminalid = request.getParameter("terminalId");
                        
                    this.getTerminalDataFromTable(terminalid);
                    
                    for(TerminalDataCaptureBean bean : list){
                        if(bean.getTerminalID().equals(terminalid)){
                            oldValue = bean.getAllocationStatus() + "|" + bean.getMerchantID() + "|" + bean.getLastUpdateUser();
                        }
                    }
                    
                    newValue = StatusVarList.ALLOCATION_NO + "|"  + sessionVarlist.getCMSSessionUser().getUserName();                         
                        
                        this.setAudittraceValue(request);

                        if (isTerminalActive(terminalid)) {
                            request.setAttribute("errorMsg", MessageVarList.TERMINAL_ACTIVE);

                        } else {
                            if (isTerminalAllocated(terminalid)) { //not allocated
                                request.setAttribute("errorMsg", MessageVarList.TERMINAL_ALREADY_NOT_ALLOCATED);
                            } else {//prevoiusly allocated
                                if (updateterminalAllocationStatus(terminalid)) {
                                    request.setAttribute("successMsg", MessageVarList.TERMINAL_SUCSESS_DALLOCATED);
                                } else {
                                    request.setAttribute("errorMsg", MessageVarList.TERMINAL_ERROR_DALLOCATED);
                                }
                            }
                            rd = getServletContext().getRequestDispatcher(url);
                        }

                    } catch (SQLException e) {
                        OracleMessage message = new OracleMessage();
                        String oraMessage = message.getMessege(e.getMessage());
                        request.setAttribute("errorMsg", oraMessage);
                        rd = getServletContext().getRequestDispatcher(url);
                    }

                    rd = getServletContext().getRequestDispatcher(url);
                   
                } else {
                    throw new AccessDeniedException();
                }
                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);

            } catch (AccessDeniedException adex) {
                //if user_role doesn't have required access to the page throw Access Denied exception
                throw adex;
            }

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
            String uniqueId = request.getParameter(terminalid);

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Deallocated Terminal." + " Terminal ID : " + terminalid + "; by :" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.MERCHANT_TERMINAL_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TERMINALMGT);
            systemAuditBean.setPageCode(PageVarList.TRMINAL_ALLOCATION_DEALLOCATION);
            systemAuditBean.setTaskCode(TaskVarList.DEALLOCATION);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(oldValue);
            //set new value of change if required
            systemAuditBean.setNewValue(newValue);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
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

    public Boolean updateterminalAllocationStatus(String terminalID) throws Exception {
        allocationmanager = new TerminalDataCaptureManager();        
        Boolean status = false;
        try {
            allocationmanager = new TerminalDataCaptureManager();
            status = allocationmanager.updateterminalAllocationStatus(terminalID, systemAuditBean);
        } catch (Exception e) {
            throw e;
        }
        return status;
    }
    
    public void getTerminalDataFromTable(String terminalID) throws Exception {
        allocationmanager = new TerminalDataCaptureManager();       
        try {
            list = allocationmanager.getTerminalDataFromOnlineTable(terminalID);
        } catch (Exception e) {
            throw e;
        }
    }  
    
     private String convertStringDate() {
 
         Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return (dateFormat.format(date));
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
        try {
            processRequest(request, response);
        } catch (AccessDeniedException ex) {
            Logger.getLogger(DeAllocateTerminalServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NewLoginSessionException ex) {
            Logger.getLogger(DeAllocateTerminalServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DeAllocateTerminalServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (AccessDeniedException ex) {
            Logger.getLogger(DeAllocateTerminalServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NewLoginSessionException ex) {
            Logger.getLogger(DeAllocateTerminalServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DeAllocateTerminalServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
