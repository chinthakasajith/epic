/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.ApplicationModuleMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
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
 * @author upul
 */
public class LoadSystemAudittraceServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private List<UserRoleBean> userRoleList;
    private List<SectionBean> sectionList;
    private List<ApplicationModuleBean> applicationLst;
    private List<StatusBean> statusList;
    private List<PageBean> pageList;
    private List<TaskBean> taskList;
    private List<SystemAuditBean> auditList;
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemAuditManager systemAuditManager=null;
    private ApplicationModuleMgtManager applicationModuleMgtManager=null;
    HttpSession sessionObj = null;
    private List<String> userTaskList;
    private SystemUserManager systemUserManager=null;
    private String url = "/administrator/controlpanel/systemusermgt/systemaudittracehome.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            //call to existing session
            /////////////////////////////////////////////////////////////////////
            HttpSession sessionObj = request.getSession(false);
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
                ////////////////////////////////////////
                try {
                    //set page code and task codes
                    String pageCode = PageVarList.SYSTEMAUDIT;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    
                    //check whethre userrole have an access for this page and task
                    if(this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)){
                        
                        // is valid acess nothing to do
                        
                    }else{                        
                        //if invalid throw accessdenied exception
                        throw new AccessDeniedException();
                        
                    }    
                    
                    //set tasks to the session
                    sessionVarlist.setUserPageTaskList(userTaskList);


                } catch (AccessDeniedException adex) {
                    throw  adex;                    
                }
            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }
            
            systemAuditManager = new SystemAuditManager();
          
            //get all audittracse detail            
            this.getPagelist();            
            //get all page lsit for dropdown
            this.getTaskList();            
            //get all userrole list for dropdown
            this.getUserRoleList();            
            //get all section list for dropdown
            this.getsectionList();            
            this.getApplicationList(); 
            
            request.setAttribute("pagelist", pageList);
            sessionVarlist.setPageDetails(pageList);
            request.setAttribute("sectionlist", sectionList);
            sessionVarlist.setAssignUserRoleSections(sectionList);
            request.setAttribute("applicationLst", applicationLst);
            sessionVarlist.setApplicationModuleList(applicationLst);
            request.setAttribute("tasklist", taskList);
            sessionVarlist.setTaskDetails(taskList);
            request.setAttribute("userrolelist", userRoleList);   
            sessionVarlist.setUserRoleList(userRoleList);
            request.setAttribute("auditlist", auditList);

            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
            
        }
        //catch session exception
        catch(SesssionExpException sex){
             //redirect to login page
             rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
             rd.forward(request, response);
        }
        
        //catch session exception
        catch(NewLoginSessionException nlex){
            
            //redirect to login page
             rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);         
             rd.forward(request, response);
            
        }
        
         //catch accessdenied exception
        catch(AccessDeniedException adex){
             //redirect to login page
             rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
             rd.forward(request, response);            
            
        }
        catch(Exception ex){            
        }
        
        finally {            
            out.close();
        }
    }
    
    
    
    
    
   
  /**
     * getUserRoleList
     * @throws Exception 
     */
      private void getUserRoleList() throws Exception {

        try{
        
        userRoleList = systemAuditManager.getAllUserRole();
        }
        catch(Exception ex){
            throw  ex;
        }
    }

      
      
     /**
       * getsectionList
       * @throws Exception 
       */ 
    private void getsectionList() throws Exception {
        try{
       
        sectionList = systemAuditManager.getAllSection();
         }
        catch(Exception ex){
            throw  ex;
        }
    }

    /**
     * getApplicationList
     * @throws Exception 
     */
     private void getApplicationList() throws Exception {
        try{
       applicationModuleMgtManager=new ApplicationModuleMgtManager();
       applicationLst = applicationModuleMgtManager.getAllApplicationList();
         }
        catch(Exception ex){
            throw  ex;
        }
    }
 

    /**
     * getPagelist
     * @throws Exception 
     */
    private void getPagelist() throws Exception {
        try{
       
        pageList = systemAuditManager.getAllPage();
         }
        catch(Exception ex){
            throw  ex;
        }
    }

    
    
    /**
     * getTaskList
     * @throws Exception 
     */
    private void getTaskList() throws Exception {
        try{
       
        taskList = systemAuditManager.getAllTask();
         }
        catch(Exception ex){
            throw  ex;
        }
    }


    
    /**
     * getAuditList
     * @throws Exception 
     */
    private void getAuditList() throws Exception {
        try{
        

        auditList = systemAuditManager.getAllSystemAudit();


        }
        catch(Exception ex){
            throw  ex;
        }
    }
    
    
    
    ///////////////////////////
    private boolean isValidAccess(String userrole,String pagecode,String task) throws Exception{
       boolean isValidTaskAccess=false;
        
        try{
          systemUserManager=new SystemUserManager();
         
          //get all tasks for userrole for this page
          userTaskList=  systemUserManager.getTasksByPageCodeAndUserRole(userrole, pagecode);
            
          for(String usertask:userTaskList){
              
              if(task.equals(usertask)){
                 isValidTaskAccess=true; 
              }
              
              
          }
          
           
          return isValidTaskAccess;
        }
        catch(Exception ex){
            throw  ex;
        }
        
    }
    
    ///////////////////////////////////////

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
