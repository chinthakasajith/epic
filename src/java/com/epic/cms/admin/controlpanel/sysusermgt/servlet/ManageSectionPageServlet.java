/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.PageMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SectionManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.ApplicationSectionPageManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
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
 * @author upul
 */
public class ManageSectionPageServlet extends HttpServlet {

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
    private ApplicationSectionPageManager sectionPageManager=null;
    private PageMgtManager  pageMgtManager=null;
    private SectionManager  sectionManager=null;
    private SystemUserManager systemUserManager=null;
    private List<PageBean> sectionPageList;
    private List<PageBean> remainingPageList=null;
    private List<SectionBean> applicationSectionList=null;
    
  
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
            
            //get parameters from form
            remainingPageList=new ArrayList<PageBean>();
            String applicationCode = request.getParameter("applicationcodefield").trim();
            String sectionCode = request.getParameter("sectioncode").trim();
            
              System.out.println("sectionCode:"+sectionCode.length());
          
            
              
              if(sectionCode.length()>0){
           
                 this.getPageListNotAssignBySection();
            
               }
              
            
            this.getPageListBySection(sectionCode);
            this.getAllSectionListByApplication(applicationCode);

            request.setAttribute("applicationCode", applicationCode);
            request.setAttribute("selectedsection", sectionCode);
            request.setAttribute("notassigned", remainingPageList );
            request.setAttribute("assigned", sectionPageList);
            request.setAttribute("sectionList", applicationSectionList);

            
           rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemusermgt/sectionpagehome.jsp");
           rd.forward(request, response);

            
            //////////////////////////////////////////////////////////////////////
            }
     //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp");
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            //  rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp");
            rd.forward(request, response);

        } catch (NullPointerException ex) {
            request.setAttribute("errorMsg", ex.getMessage());
            rd = getServletContext().getRequestDispatcher("/LoadSectionPageMgtServlet");
            rd.forward(request, response);
        } catch (Exception ex) {
        }
        finally {            
            out.close();
        }
    }
    
    
    
    
    
  
    
    /**
     * getsectionListByUserRole
     * @param userRoleCode
     * @throws Exception 
     */
      private void getPageListBySection(String sectioncode)throws Exception{
        try{
        pageMgtManager =new PageMgtManager();
        sectionPageList=pageMgtManager.getAllPagesBySection(sectioncode);
        }catch(Exception ex){
            throw  ex;
        }
    }
     
      
      
      
       private void getPageListNotAssignBySection()throws Exception{
        try{
            
       pageMgtManager =new PageMgtManager();
       remainingPageList=pageMgtManager.getAllRemainingPages();
      
        }catch(Exception ex){
            throw  ex;
        }
    }
       
       
       
       
       private void getAllSectionListByApplication(String applicationCode)throws Exception{
        try{
        sectionManager =new SectionManager();
        applicationSectionList=sectionManager.getAllSectionByApplication(applicationCode);
        }catch(Exception ex){
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
