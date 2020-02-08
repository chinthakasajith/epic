/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.HolidayManagementBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.HolidayManagementManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
public class LoadHolidayManagementServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private RequestDispatcher rd=null;
    private SystemUserManager systemUserManager=null;
    private List<String> userTaskList;
    
     private HolidayManagementManager holidayManagementManager=null;
     private  List<HolidayManagementBean> hmbs;
    
    
    
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
                    String pageCode = PageVarList.HOLIDAYMGT;
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
               //////////////////////////////////////////// 
                
                        

            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }
            ///////////////////////////////////////////////////////////////////// 
                
            this.getHolidayListForMonth();
            
            
            request.setAttribute("holidayList", hmbs);
        
            request.setAttribute("operationtype", "selecteddate");
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
            //response.sendRedirect(request.getContextPath() + "/controlpanel/systemusermgt/viewsectionpage.jsp");
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemconfigmgt/holidaymanagementhome.jsp");
            rd.forward(request, response);
            
            
        
            
            /////////////////////////////////////////////////////////////////////
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
     * getHolidayListForMonth
     * @throws Exception 
     */
    private void getHolidayListForMonth() throws Exception{
        
      
        try{
            
            hmbs =new ArrayList<HolidayManagementBean>();
            HolidayManagementBean bean=new HolidayManagementBean();
            Calendar ca = new GregorianCalendar();
           
            //get year and motnh from calender
            int iTYear = ca.get(Calendar.YEAR);
            int iTMonth = ca.get(Calendar.MONTH);
            String realMonth = null;
            
            //month have to add 1 for get  real month and have to zero fill for fisrt 9 months
            iTMonth=iTMonth+1;
            if(iTMonth<=9){
              realMonth="0"+ Integer.toString(iTMonth); 
               bean.setMonth(realMonth);
            }
            else{
                bean.setMonth(Integer.toString(iTMonth));
            }
            
            
            
            bean.setYear(Integer.toString(iTYear));
            
            System.out.println("------------"+bean.getMonth());
            System.out.println("------------"+bean.getYear());
          
            holidayManagementManager = new HolidayManagementManager();
            hmbs = holidayManagementManager.getholidayListForMonth(bean);
            
            
            
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
