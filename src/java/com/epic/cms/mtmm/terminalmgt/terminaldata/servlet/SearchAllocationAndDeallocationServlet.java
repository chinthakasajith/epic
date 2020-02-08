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
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
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
 * @author mahesh_m
 */
public class SearchAllocationAndDeallocationServlet extends HttpServlet {
private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SystemAuditBean systemAuditBean = null;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String errorMessage = null;
    TerminalDataCaptureManager terminalManager;
    private HashMap<String, String> modelList = null;
    private HashMap<String, String> allocateList = null;
    private HashMap<String, String> terminalStatusList = null;
    TerminalDataCaptureBean terminalBean = null;
    private List<TerminalDataCaptureBean> searchList = null;
    private String url = "/mtmm/terminalmgt/terminalallocationdeallocation.jsp";

    
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
            this.getAllocationStatus();
            this.getTerminalStatus();
            request.setAttribute("modelList", modelList);
            request.setAttribute("allocateList", allocateList);
            request.setAttribute("terminalStatusList", terminalStatusList);

            try {
                //set page code and task codes
                String pageCode = PageVarList.TRMINAL_ALLOCATION_DEALLOCATION;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    
                    this.setUserInputToBean(request);
                    request.setAttribute("terminalBean", terminalBean);


                    if (this.validateUserInput(terminalBean)) {

                        this.setAudittraceValue(request);

                        this.searchTerminalData(terminalBean, systemAuditBean);
                        request.setAttribute("searchList", searchList);

                        sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);


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
            request.setAttribute("operationtype", "search");
            request.setAttribute("errorMsg", MessageVarList.TRMINAL_MGT_TERMINAL_ERROR);
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
     * get descriptions of terminal models
     */
    private void getAllModelList() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            modelList = terminalManager.getAllModelList();
        } catch (Exception e) {
        }
    }
    /**
     * assign user given values to the bean for searching
     * @param request 
     */
    public void setUserInputToBean(HttpServletRequest request) {
        
        if(request.getParameter("isBack")!= null){
            terminalBean = new TerminalDataCaptureBean();
            if(sessionVarlist.getTermBean()!= null){
                 terminalBean = sessionVarlist.getTermBean();
            }
           
        
        }else{
        terminalBean = new TerminalDataCaptureBean();

        terminalBean.setTerminalID(request.getParameter("terminalid"));
        terminalBean.setMerchantID(request.getParameter("merchantid"));
        terminalBean.setName(request.getParameter("name"));
        terminalBean.setSerialNo(request.getParameter("serialnumber"));
        terminalBean.setModel(request.getParameter("model"));
        terminalBean.setAllocationStatus(request.getParameter("allocatestatus"));
        terminalBean.setTerminalStatus(request.getParameter("terminalstatus"));
        
        sessionVarlist.setTermBean(terminalBean);
        
        }

        
        
        
        
    }

    

    public boolean validateUserInput(TerminalDataCaptureBean terminalBean) throws Exception {
        boolean isValid = true;

        try {
        
        if (!UserInputValidator.isCorrectString(terminalBean.getTerminalID())) {
        isValid = false;
        errorMessage = MessageVarList.TERMINAL_ALLOCATION_INVALID_TERMINALID;
        }else if(!UserInputValidator.isCorrectString(terminalBean.getMerchantID())){
        isValid = false;
        errorMessage = MessageVarList.TERMINAL_ALLOCATION_INVALID_MERCHANTID;
        }else if(!UserInputValidator.isCorrectString(terminalBean.getName())){
        isValid = false;
        errorMessage = MessageVarList.TERMINAL_ALLOCATION_INVALID_TERMINAL_NAME;
        }else if(!UserInputValidator.isCorrectString(terminalBean.getSerialNo())){
        isValid = false;
        errorMessage = MessageVarList.TERMINAL_ALLOCATION_INVALID_TERMINAL_SERIALNO;
        }
        
        
        } catch (Exception ex) {
        isValid = false;
        
        }

        return isValid;
    }

    /**
     * set values to audit trace
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Searched terminal data capture by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.MERCHANT_TERMINAL_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TERMINALMGT);
            systemAuditBean.setPageCode(PageVarList.TRMINAL_ALLOCATION_DEALLOCATION);
            systemAuditBean.setTaskCode(TaskVarList.SEARCH);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
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

    /**
     * search terminal data to show in the table
     * @param terminalBean
     * @param systemAuditBean
     * @throws SQLException
     * @throws Exception 
     */
    private void searchTerminalData(TerminalDataCaptureBean terminalBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        terminalManager = new TerminalDataCaptureManager();
        searchList = terminalManager.searchTerminalData(terminalBean, systemAuditBean);
        //sessionVarlist.setTerminalDataBeanList(searchList);

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
