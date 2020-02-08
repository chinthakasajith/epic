/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.PageMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
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
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class DeletePageServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private PageMgtManager pagemanager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private PageBean pageBean;
    private SystemAuditBean systemAuditBean=null;
    private List<String> userTaskList;
    private String url = "/administrator/controlpanel/systemusermgt/pagemanagement.jsp";
    
    
    
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

            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }
            /////////////////////////////////////////////////////////////////////

            try {
                //set page code and task codes
                String pageCode = PageVarList.PAGE;
                String taskCode = TaskVarList.ACCESSPAGE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    pageCode = request.getParameter("pageCode");
                    pageBean = new PageBean();
                    pageBean.setPageCode(pageCode);

                    this.setAudittraceValue(request);

                    try {
                        boolean delResult = deletePage(pageBean, systemAuditBean);

                        if (delResult) {
                            request.setAttribute("successMsg", pageCode + " " + MessageVarList.PAGE_SUCCESS_DELETE);
                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                            rd = getServletContext().getRequestDispatcher("/LoadPageMgtServlet");
                        }
                        rd.forward(request, response);
                    } catch (SQLException e) {
                        OracleMessage message = new OracleMessage();
                        String oraMessage = message.getMessege(e.getMessage());

                        request.setAttribute("operationtype", "default");
                        request.setAttribute("errorMsg", oraMessage);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    } catch (Exception ee) {
                        request.setAttribute("operationtype", "default");
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
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/pagemanagement.jsp?errorMsg="+ MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/pagemanagement.jsp?errorMsg="+ MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        }catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", "Error occurddddd");
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);

        } finally {

            out.close();
        }
    }
    
    
    
     private void setAudittraceValue(HttpServletRequest request) throws Exception{
        
        
        try{
        systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
        String uniqueId = request.getParameter("");
       
        systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
        systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
        //set unique id
        systemAuditBean.setUniqueId(uniqueId);
        //set description 
        systemAuditBean.setDescription("Delete page, page code : "+ pageBean.getPageCode()+" by "+sessionVarlist.getCMSSessionUser().getUserName());      
        systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
        systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
        systemAuditBean.setPageCode(PageVarList.PAGE);
        systemAuditBean.setTaskCode(TaskVarList.DELETE);
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
          
        }
        catch(Exception ex){
            throw ex;
        }
  
    }
    
  public boolean deletePage(PageBean page ,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            pagemanager = new PageMgtManager();
            success = pagemanager.deletePage(page ,systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }
  
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
