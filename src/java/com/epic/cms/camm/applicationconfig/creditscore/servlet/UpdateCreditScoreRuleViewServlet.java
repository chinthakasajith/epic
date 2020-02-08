/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreRuleBean;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CreditScoreRuleDefineManagement;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
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
 * @author upul
 */
public class UpdateCreditScoreRuleViewServlet extends HttpServlet {

    
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/camm/applicationconfig/creditscore/creditscoreruledef.jsp";
    private SystemUserManager systemUserManager = null;
    private SystemUserManager sysUserOnj;
    private List<StatusBean> statusBean;
    private CreditScoreRuleDefineManagement defineManagement;
    private CreditScoreRuleBean creditScoreRuleBean;
      private List<CreditScoreRuleBean> creditScoreRuleActiveBeanLst;
    
    
    
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

            ///////////////////////////////////////////////////////
             request.setAttribute("operationtype", "update");
              
              //check whethre userrole have an access for this page and task

            String taskCode = TaskVarList.UPDATE;
            
               if (this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), taskCode)) {
                   
                
                   String ruleId=request.getParameter("id");
                   
                   this.getAllCreditScoreRuleDefDetails(ruleId);
                   
                   if (creditScoreRuleBean != null) {

                       if (creditScoreRuleBean.getCondition().equals("7")) {
                           if (!(creditScoreRuleBean.getRuleNoOne().contentEquals("") || creditScoreRuleBean.getRuleNoOne().length() <= 0)) {

                               request.setAttribute("ruleNoOne", "OK");

                           }
                       }else if(creditScoreRuleBean.getCondition().equals("6")){
                       
                        request.setAttribute("conditionVar", "6");
                       }




                       if (creditScoreRuleBean.getCondition().equals("7")) {

                           this.getAllDistinctActiveCreditScoreRules();
                           request.setAttribute("activeScoreRuleLst", creditScoreRuleActiveBeanLst);

                       }

                       request.setAttribute("scoreRuleBean", creditScoreRuleBean);
                       rd = getServletContext().getRequestDispatcher("/LoadCreditScoreRuleDefServlet");

                   } else {
                   }
                   
                   
                   
                   
                   
                   
                   
               }
            
                else {
                request.setAttribute("operationtype", "add");
                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }
            
            
            
            
            
            
            ///////////////////////////////////////////////////////  

            
            
            
            } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
              //catch accessdenied exception
              String errMessage=MessageVarList.ACCESS_DENIED_PAGETASK;
              request.setAttribute(MessageVarList.JSP_ERROR, errMessage);
              rd = getServletContext().getRequestDispatcher("/LoadCreditScoreRuleDefServlet");            
           
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.toString()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.include(request, response);
        }
    }
     
    
    
       ////////////////////////////////////////////////////////////////////////////    
       
    
       ////////////////////////////////////////////////////////////////////////////    
       /**
        * isValidTaskByUser
        * @param userTaskList
        * @param task
        * @return
        * @throws Exception 
        */
       private boolean isValidTaskByUser(List<String> userTaskList,String task) throws Exception{
            boolean isValidTask=false;
           try{
              
            for(String usertask:userTaskList){
              
              if(task.equals(usertask)){
                 isValidTask=true; 
              }
              
              
          }
          return isValidTask;
               
               
               
           }
           catch(Exception ex){
               throw  ex;
           }
           
           
           
       }
    ////////////////////////////////////////////////////////////////////////////   

            /**
        * getAllCreditScoreRuleDefDetails
        * @throws Exception 
        */
       private void getAllDistinctActiveCreditScoreRules() throws Exception {
        try {
            defineManagement = new CreditScoreRuleDefineManagement();
            creditScoreRuleActiveBeanLst = defineManagement.getAllDistinctActiveCreditScoreRules();
        } catch (Exception ex) {
            throw ex;
        }
    }
       
  
       
        
      /**
       * getAllCreditScoreRuleDefDetails
       * @throws Exception 
       */
       private void getAllCreditScoreRuleDefDetails(String ruleId) throws Exception {
        try {
            defineManagement = new CreditScoreRuleDefineManagement();
            creditScoreRuleBean = defineManagement.getCreditScoreRuleDefFromRuleIDDetails(ruleId);
        } catch (Exception ex) {
            throw ex;
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
