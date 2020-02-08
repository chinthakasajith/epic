/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.OperatorBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreFieldBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreRuleBean;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CreditScoreFieldManager;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CreditScoreRuleDefineManagement;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
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
 *
 * @author upul
 */
public class LoadCreditScoreRuleDefServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/camm/applicationconfig/creditscore/creditscoreruledef.jsp";
    private SystemUserManager systemUserManager = null;
    private CreditScoreFieldManager scoreFieldManager = null;
    private CreditScoreRuleDefineManagement defineManagement;
    private List<CreditScoreRuleBean> creditScoreRuleBeanLst;
    private List<String> userTaskList;
    private List<OperatorBean> operatorBeansLst;
    private List<StatusBean> statusList;
    private List<CreditScoreFieldBean> scoreFieldBeanLst;
   
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
         response.setContentType("text/html;charset=UTF-8");

        try {
           
            HttpSession sessionObj = request.getSession(false);
            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }

                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();

                }

                try {
                    String pageCode = PageVarList.CREDITSCORERULEDEF;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        
                    } else {
                        throw new AccessDeniedException();
                    }
                    sessionVarlist.setUserPageTaskList(userTaskList);


                } catch (AccessDeniedException adex) {
                    throw adex;

                }

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }
          ///////////////////////////////////////////
            
            if(request.getAttribute("operationtype")==null){
               
               request.setAttribute("operationtype", "add");
                
            }
            else{
               
            }
              
         
            this.operatorList();
            this.getAllStatus("GENR");
            this.getAllCreditScoreRuleDefDetails();
            this.getAllActiveCreditScoreFieldName();
            sessionVarlist.setStatusList(statusList);
            sessionVarlist.setFieldBeanLst(scoreFieldBeanLst);

            request.setAttribute("conditions", operatorBeansLst);
            request.setAttribute("creditScoreRuleLst", creditScoreRuleBeanLst);
            rd = request.getRequestDispatcher(url);
            
            
            
            
            
          ///////////////////////////////////////////  
            
            
            
            
        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.toString()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
        }
    }
     
    
    
    
    
     /**
     * check task in valid for this action
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
     * operatorList
     * @return
     * @throws Exception 
     */
    private List<OperatorBean> operatorList() throws Exception{
        operatorBeansLst=new ArrayList<OperatorBean>();
        try{
            
            defineManagement=new CreditScoreRuleDefineManagement();
            operatorBeansLst=defineManagement.getAllOperatorList();
            
        }
        catch(Exception ex){
            throw  ex;
        }
        return operatorBeansLst;
        
    }
   
    
    /**
     * getAllStatus
     * @param categoryCode
     * @throws Exception 
     */
      private void getAllStatus(String categoryCode) throws Exception {
        try {
            systemUserManager = new SystemUserManager();
            statusList = systemUserManager.getStatusByUserRole(categoryCode);
        } catch (Exception ex) {
            throw ex;
        }
    }
      
      
      /**
       * getAllActiveCreditScoreFieldName
       * @param categoryCode
       * @throws Exception 
       */
      private void getAllActiveCreditScoreFieldName() throws Exception {
        try {
            scoreFieldManager = new CreditScoreFieldManager();
            scoreFieldBeanLst=scoreFieldManager.getAllActiveCreditScoreFieldName();
        } catch (Exception ex) {
            throw ex;
        }
    }
      
      
      
      /**
       * getAllCreditScoreRuleDefDetails
       * @throws Exception 
       */
       private void getAllCreditScoreRuleDefDetails() throws Exception {
        try {
            defineManagement = new CreditScoreRuleDefineManagement();
            creditScoreRuleBeanLst = defineManagement.getAllCreditScoreRuleDefDetails();
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
