/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.OperatorBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreRuleBean;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CreditScoreFieldManager;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CreditScoreRuleDefineManagement;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
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
 * @author upul
 */
public class UpdateCreditScoreRuleDefineServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/camm/applicationconfig/creditscore/creditscoreruledef.jsp";
    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;
    private CreditScoreRuleDefineManagement defineManagement;
    private List<String> userTaskList;
    private List<CreditScoreRuleBean> creditScoreRuleBeanLst;
    private List<OperatorBean> operatorBeansLst;
    private CreditScoreRuleBean scoreRuleBean;
    private String errorMessage = "";
    private List<CreditScoreRuleBean> creditScoreRuleActiveBeanLst;
    private String newValue;
    private String oldValue;
    private CreditScoreRuleBean creditScoreRuleBean;    
    
    /** 
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
           
            this.operatorList();
            request.setAttribute("conditions", operatorBeansLst);
            this.setValuesToBean(request);
            
            if(scoreRuleBean.getCondition().equals("7")){
            if (!(scoreRuleBean.getRuleNoOne().contentEquals("") || scoreRuleBean.getRuleNoOne().length() <= 0)) {

                request.setAttribute("ruleNoOne", "OK");

            }
            }else if(scoreRuleBean.getCondition().equals("6")){
            request.setAttribute("conditionVar", "6");
            }
            request.setAttribute("scoreRuleBean", scoreRuleBean);



            if (scoreRuleBean.getCondition().equals("7")) {

                this.getAllDistinctActiveCreditScoreRules();
                request.setAttribute("activeScoreRuleLst", creditScoreRuleActiveBeanLst);

            }
            
            if(this.validateCreditScoreValues()){
            
            
              //set valuest to audit bean
            this.setAudittraceValue(request);
            
              //check whethre userrole have an access for this page and task

            String taskCode = TaskVarList.UPDATE;
            
            if (this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), taskCode)) {

                if (this.updateCreditScoreRule(scoreRuleBean) == 1) {
                    
                    

                    request.setAttribute(MessageVarList.JSP_SUCCESS,scoreRuleBean.getRuleCode()+" "+ MessageVarList.UPDATE_CREDITSCORE_RULE_SUCCESS);
                    scoreRuleBean = new CreditScoreRuleBean();
                    request.setAttribute("scoreRuleBean", scoreRuleBean);
                    request.setAttribute("operationtype", "add");
                    this.getAllCreditScoreRuleDefDetails();
                    request.setAttribute("creditScoreRuleLst", creditScoreRuleBeanLst);
                    //response.sendRedirect(request.getContextPath() + "/controlpanel/systemusermgt/viewsectionpage.jsp");
                    rd = getServletContext().getRequestDispatcher(url);
//                    rd.forward(request, response);
                } else {

                    request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UPDATE_CREDITSCORE_RULE_ERROR);
                    //response.sendRedirect(request.getContextPath() + "/controlpanel/systemusermgt/viewsectionpage.jsp");
                    rd = getServletContext().getRequestDispatcher("/LoadCreditScoreRuleDefServlet");
              //      rd.include(request, response);



                }

            }
             else {

                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }
            
            }else{
                
                    request.setAttribute(MessageVarList.JSP_ERROR, errorMessage);
                    //response.sendRedirect(request.getContextPath() + "/controlpanel/systemusermgt/viewsectionpage.jsp");
                    rd = getServletContext().getRequestDispatcher("/LoadCreditScoreRuleDefServlet");
                    //rd.include(request, response);
                
                
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
            rd.forward(request, response);
        }
    }
     
    
    
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
       
       private void getAllCreditScoreRuleDefDetails() throws Exception {
        try {
            defineManagement = new CreditScoreRuleDefineManagement();
            creditScoreRuleBeanLst = defineManagement.getAllCreditScoreRuleDefDetails();
        } catch (Exception ex) {
            throw ex;
        }
       }
    ////////////////////////////////////////////////////////////////////////////   
        
    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = scoreRuleBean.getRuleCode();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update creditScore rule. Rule code: " + uniqueId + " by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CREDITSCOREMGT);
            systemAuditBean.setPageCode(PageVarList.CREDITSCORERULEDEF);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
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
    
    /**
     * setValuesToBean
     * @param request 
     */
      private void setValuesToBean(HttpServletRequest request){
        
        try{
            scoreRuleBean=new CreditScoreRuleBean();
            scoreRuleBean.setRuleCode(request.getParameter("ruleCode"));
            scoreRuleBean.setRuleName(request.getParameter("ruleName"));
            scoreRuleBean.setFieldName(request.getParameter("fieldName"));
            scoreRuleBean.setCondition(request.getParameter("conditionVar"));
            scoreRuleBean.setValue(request.getParameter("value"));
            scoreRuleBean.setMaxValue(request.getParameter("maxValue"));
            scoreRuleBean.setRuleNoOne(request.getParameter("ruleNoOne"));
            scoreRuleBean.setRuleNoTwo(request.getParameter("ruleNoTwo"));
            scoreRuleBean.setScore(request.getParameter("score"));
            scoreRuleBean.setStatus(request.getParameter("selectstatuscode"));

            newValue = scoreRuleBean.getRuleCode() + "|" + scoreRuleBean.getRuleName() +"|" + scoreRuleBean.getFieldName() + "|" 
                    +  scoreRuleBean.getScore() + "|" + scoreRuleBean.getStatus() ;
            
            this.getAllCreditScoreRuleDefDetails(scoreRuleBean.getRuleCode());  

            oldValue = creditScoreRuleBean.getRuleCode() + "|" + creditScoreRuleBean.getRuleName() +"|" + creditScoreRuleBean.getFieldName() + "|" 
                    +  creditScoreRuleBean.getScore() + "|" + creditScoreRuleBean.getStatus() ;
            
        }
        catch(Exception ex){
            
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
     * 
     * @param managementBean
     * @return
     * @throws Exception 
     */
     private boolean validateCreditScoreValues() throws Exception{
          boolean isValidate = true;
          try{
              
          
          
              //rulecode
              if (scoreRuleBean.getRuleCode().contentEquals("") || scoreRuleBean.getRuleCode().length() <= 0) {
                  isValidate = false;
                  errorMessage = MessageVarList.CREDITSCORE_RULECODE__EMPTY;
              } else if (!UserInputValidator.isCorrectString(scoreRuleBean.getRuleCode())) {
                  isValidate = false;
                  errorMessage = MessageVarList.CREDITSCORE_RULECODE_INVALID;

              }
              //rulename
              else if (scoreRuleBean.getRuleName().contentEquals("") || scoreRuleBean.getRuleName().length() <= 0) {
                  isValidate = false;
                  errorMessage = MessageVarList.CREDITSCORE_RULENAME__EMPTY;

              } else if (!UserInputValidator.isCorrectString(scoreRuleBean.getRuleName())) {
                  isValidate = false;
                  errorMessage = MessageVarList.CREDITSCORE_RULENAME_INVALID;

              }
              
              
              //fieldname
              else if (scoreRuleBean.getFieldName().contentEquals("") || scoreRuleBean.getFieldName().length() <= 0) {
                  isValidate = false;
                  errorMessage = MessageVarList.CREDITSCORE_FIELDNAME__EMPTY;

              } else if (!UserInputValidator.isCorrectString(scoreRuleBean.getFieldName())) {
                  isValidate = false;
                  errorMessage = MessageVarList.CREDITSCORE_FIELDNAME_INVALID;

              }
              
              
              //condition
               else if (scoreRuleBean.getCondition().contentEquals("") || scoreRuleBean.getCondition().length() <= 0) {
                  isValidate = false;
                  errorMessage = MessageVarList.CREDITSCORE_CONDITION__EMPTY;

              } else if (!UserInputValidator.isNumeric(scoreRuleBean.getCondition())) {
                  isValidate = false;
                  errorMessage = MessageVarList.CREDITSCORE_CONDITION_INVALID;

                 
              }
              
              
             
              else{
                  
                 ///////// //get the data type of the filed to validation
                 CreditScoreFieldManager fieldManager=new CreditScoreFieldManager();
                 
                 String dataType=   fieldManager.getCreditScoreFieldDataType(scoreRuleBean.getFieldName());
                 /////////////// ///////////////////////////////
                  
                  
                  
              
                //value   get value 
               if (!(scoreRuleBean.getCondition().contentEquals("7") )) {
                  //value
                  if (scoreRuleBean.getValue().contentEquals("") || scoreRuleBean.getValue().length() <= 0) {
                      isValidate = false;
                      errorMessage = MessageVarList.CREDITSCORE_VALUE_EMPTY;
                      
                      return false;

                  } else if (!UserInputValidator.isValidValueForCreditScoreRuleInCms(dataType,scoreRuleBean.getValue())) {
                      isValidate = false;
                      errorMessage =  MessageVarList.CREDITSCORE_VALUE_INVALID+" "+dataType;
                      
                       return false;

                  }
              }
              

              
               if (scoreRuleBean.getCondition().contentEquals("6") ) {
                  
                  if (scoreRuleBean.getMaxValue().contentEquals("") || scoreRuleBean.getMaxValue().length() <= 0) {
                      isValidate = false;
                      errorMessage =  MessageVarList.CREDITSCORE_MAX_VALUE_EMPTY;
                      
                       return false;

                  } else if (!UserInputValidator.isDecimalOrNumeric(scoreRuleBean.getMaxValue(),"61","2")) {
                      isValidate = false;
                      errorMessage =  MessageVarList.CREDITSCORE_MAX_VALUE_INVALID ;
                      
                       return false;

                  }
              }
         
     ////////////////////////////////////////////////              
              
              
                //get rule no one only if condition is and
               if (scoreRuleBean.getCondition().contentEquals("7") ) {
                  
                  if (scoreRuleBean.getRuleNoOne().contentEquals("") || scoreRuleBean.getRuleNoOne().length() <= 0) {
                      isValidate = false;
                      errorMessage = MessageVarList.CREDITSCORE_RULEONE_EMPTY;
                       return false;

                  } else if (!UserInputValidator.isCorrectString(scoreRuleBean.getRuleNoOne())) {
                      isValidate = false;
                      errorMessage = MessageVarList.CREDITSCORE_RULEONE_EMPTY ;
                       return false;

                  }
              }
              
                  //get rule no one only if condition is and
               if (scoreRuleBean.getCondition().contentEquals("7") && !(scoreRuleBean.getRuleNoOne().contentEquals("") || scoreRuleBean.getRuleNoOne().length() <= 0)) {
                  
                  if (scoreRuleBean.getRuleNoTwo().contentEquals("") || scoreRuleBean.getRuleNoTwo().length() <= 0) {
                      isValidate = false;
                      errorMessage =  MessageVarList.CREDITSCORE_RULETWO_EMPTY;
                       return false;

                  } else if (!UserInputValidator.isCorrectString(scoreRuleBean.getRuleNoTwo())) {
                      isValidate = false;
                      errorMessage = MessageVarList.CREDITSCORE_RULEONE_EMPTY ;
                       return false;

                  }
              }
              
                //score
               if (scoreRuleBean.getScore().contentEquals("") || scoreRuleBean.getScore().length() <= 0) {
                  isValidate = false;
                  errorMessage = MessageVarList.CREDITSCORE_SCORE__EMPTY;
                   return false;

              } else if (!(UserInputValidator.isNumeric(scoreRuleBean.getScore())||UserInputValidator.isDecimalNumeric(scoreRuleBean.getScore()))) {
                  isValidate = false;
                  errorMessage =  MessageVarList.CREDITSCORE_SCORE_INVALID;
                   return false;

              }
              
              
              
                //status
              if (scoreRuleBean.getStatus().contentEquals("") || scoreRuleBean.getStatus().length() <= 0) {
                  isValidate = false;
                  errorMessage = MessageVarList.CREDITSCORE_STATUS_INVALID;
                   return false;

              } else if (!UserInputValidator.isCorrectString(scoreRuleBean.getStatus())) {
                  isValidate = false;
                  errorMessage =  MessageVarList.CREDITSCORE_STATUS_INVALID;
                   return false;

              }
              
              
              
              
              }
          }
          catch(Exception ex){
              throw ex;
          }
          return isValidate;
          
      }
     
     
     
     
     /**
      * insertCreditScoreRule
      * @param creditScoreRuleBean
      * @return
      * @throws Exception 
      */
      
        public int updateCreditScoreRule(CreditScoreRuleBean creditScoreRuleBean) throws Exception {
        int  success = -1;;
        try {
            
            defineManagement = new CreditScoreRuleDefineManagement();
            success = defineManagement.updateCreditScoreRuleDetail(creditScoreRuleBean,systemAuditBean);
            
            
        } catch (SQLException ex) {
            throw ex;
        }  catch (Exception ex) {
            throw ex;
        }
        return success;
    } 
        
        
        
   
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
