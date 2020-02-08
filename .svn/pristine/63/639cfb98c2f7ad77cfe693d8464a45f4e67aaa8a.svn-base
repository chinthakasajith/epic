/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.AssignedTasksBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.AssignedTasksDescriptionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.PageMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class ViewPageMgtServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager pageMgt;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager=null;
    private PageBean page;
    private PageMgtManager pageManager;
    private List<TaskBean> assignedTaskDescriptions;
    private List<TaskBean> taskbeanList = null;
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

                String pageCode = request.getParameter("pagecode");



                for (PageBean pageBean : sessionVarlist.getPageDetails()) {
                    if (pageBean.getPageCode().equals(pageCode)) {
                        page = pageBean;
                    }
                }


                taskbeanList = new ArrayList<TaskBean>();
                pageManager = new PageMgtManager();
                taskbeanList = pageManager.getTaskDescriptionNotAssignedList(page.getPageCode());




                getAssignedTasks(page.getPageCode());

                if (page != null) {
                    request.setAttribute("operationtype", "view");
                    request.setAttribute("page", page);
                    request.setAttribute("taskBean", taskbeanList);
                    request.setAttribute("assigned", assignedTaskDescriptions);

                    rd = getServletContext().getRequestDispatcher(url);
                }

                rd.forward(request, response);
                
            } catch (Exception e) {
               request.setAttribute("operationtype", "default");
               request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
               rd = getServletContext().getRequestDispatcher(url);
               rd.forward(request, response);
            }
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
        catch(Exception ex){
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        }
        
        finally {            
            out.close();
        }
    }

   private void getAssignedTasks(String pageCode) throws Exception{

       

        try {
            assignedTaskDescriptions = new ArrayList<TaskBean>();
            pageManager = new PageMgtManager();
            assignedTaskDescriptions = pageManager.getAssignedtasks(pageCode);

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
