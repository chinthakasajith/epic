/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.OperatorBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreRuleBean;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CreditScoreRuleDefineManagement;
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
 * @author ayesh
 */
public class DeleteCreditScoreRuleDefineServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/camm/applicationconfig/creditscore/creditscoreruledef.jsp";
    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;
    private CreditScoreRuleDefineManagement defineManagement;
    private List<String> userTaskList;
    private List<OperatorBean> operatorBeansLst;
    private CreditScoreRuleBean scoreRuleBean;
    private String errorMessage = "";
    private List<CreditScoreRuleBean> creditScoreRuleBeanLst;
    private String oldValue;
    private CreditScoreRuleBean creditScoreRuleBean;   
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
        try {
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
            this.getAllCreditScoreRuleDefDetails();
            request.setAttribute("creditScoreRuleLst", creditScoreRuleBeanLst);
            request.setAttribute("operationtype", "add");
            this.operatorList();
            request.setAttribute("conditions", operatorBeansLst);
            

            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.DELETE)) {
                throw new AccessDeniedException();
            }
            String id = request.getParameter("id");

            this.getAllCreditScoreRuleDefDetails(id);  

            oldValue = creditScoreRuleBean.getRuleCode() + "|" + creditScoreRuleBean.getRuleName() +"|" + creditScoreRuleBean.getFieldName() + "|" 
                    + creditScoreRuleBean.getScore() + "|" + creditScoreRuleBean.getStatus() ;            
            

            int row = -1;
            defineManagement = new CreditScoreRuleDefineManagement();
            this.setAudittraceValue(request);
            row = defineManagement.deleteCreditScoreField(id, systemAuditBean);
            if (row == 1) {
                request.setAttribute(MessageVarList.JSP_SUCCESS,
                      id+" "+  MessageVarList.CREDITSCORE_DELETE_SUCCESS);
            }


            this.getAllCreditScoreRuleDefDetails();
            request.setAttribute("creditScoreRuleLst", creditScoreRuleBeanLst);

            rd = request.getRequestDispatcher(url);

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.toString()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
        }
    }

    /**
     * isValidTaskByUser
     * @param userTaskList
     * @param task
     * @return
     * @throws Exception 
     */
    private boolean isValidTaskByUser(List<String> userTaskList, String task) throws Exception {
        boolean isValidTask = false;
        try {

            for (String usertask : userTaskList) {
                if (task.equals(usertask)) {
                    isValidTask = true;
                }
            }
            return isValidTask;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("id");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Delete CreditScore Rule. Rule code: " + uniqueId + " by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CREDITSCOREMGT);
            systemAuditBean.setPageCode(PageVarList.CREDITSCORERULEDEF);
            systemAuditBean.setTaskCode(TaskVarList.DELETE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(oldValue);
            //set new value of change if required
            systemAuditBean.setNewValue("");


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());





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
