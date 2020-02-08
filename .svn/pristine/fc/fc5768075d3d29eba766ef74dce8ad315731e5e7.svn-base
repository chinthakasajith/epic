/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.accounttemplate.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.admin.templatemgt.accounttemplate.businesslogic.AccountTemplateManager;
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
 * @author janaka_h
 */
public class DeleteAccountTemplateServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private SessionVarList sessionVarlist;
    private SystemAuditBean systemAuditBean;
    private SystemUserManager systemUserManager;
    private AccountTemplateManager templateManager;
    private String url = "/LoadAccountTempalteServlet";    
    private List<AccountTempBean> templateList = null;   
    private String oldValue;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
         AccountTempBean templateBean = new AccountTempBean();
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
            
            
            templateBean.setTemplateCode(request.getParameter("templateCode"));
            templateBean.setLastUpdateduser(sessionUser.getUserName());
           
             //set page code and task codes
            String pageCode = PageVarList.ACCTEMPLATE;
            String taskCode = TaskVarList.DELETE;


            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                
                this.getAllAccTemplateList();                
                AccountTempBean editBean = new AccountTempBean();
                
                    for (int i = 0; i < templateList.size(); i++) {
                        if (templateList.get(i).getTemplateCode().equals(templateBean.getTemplateCode())) {
                            editBean = templateList.get(i);
                            break;
                        }
                    }                
                
                oldValue = editBean.getTemplateCode() + "|" + editBean.getTemplateName() + "|"
                        + "" + editBean.getValiedFrom() + "|" + editBean.getValiedTo() + "|"
                        + "" + editBean.getTotalCreditLimit() + "|" + editBean.getStaff() + "|"
                        + "" + editBean.getAccRskProf() + "|"  + editBean.getCardType() + "|"
                        + "" + editBean.getCurrencyCode() + "|" + editBean.getCustomerTemplateCode() + "|"
                        + "" + editBean.getBillCycle() + "|"  + editBean.getInterestprofileCode() + "|"
                        + "" + editBean.getStatementProf()  + "|" + editBean.getCardType() + "|"
                        + "" + editBean.getStatus();                    
                    
                templateManager = new AccountTemplateManager();
                this.setAudittraceValue(request, templateBean);
                int isDelete = templateManager.deleteTemplate(templateBean,systemAuditBean);
                
                
                if (isDelete == 1) {
                    request.setAttribute("successMsg", "Sucssesfully deleted Account Template.");
                     rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);
                } else {
                    
                    request.setAttribute("templateBean", templateBean);
                    request.setAttribute("operation", "delete");
                    request.setAttribute("errorMsg", "Faild deleting customer template.");
                    rd = getServletContext().getRequestDispatcher("/LoadAccountTempalteServlet");
                    rd.forward(request, response);
                }
                
               
             } else {

                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }
            
        } catch (SQLException ex) {
            request.setAttribute("templateBean", templateBean);
            request.setAttribute("operation", "edit");
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            rd = getServletContext().getRequestDispatcher("/LoadAccountTempalteServlet");
            rd.forward(request, response);
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        }
          //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);


        }
        catch (Exception ex) {
            request.setAttribute("templateBean", templateBean);
            request.setAttribute("operation", "edit");
            request.setAttribute("errorMsg", "Error occurred when deleting template.");
            rd = getServletContext().getRequestDispatcher("/ManageTemplateMgtServlet");
            rd.forward(request, response);
            
        } finally {
            out.close();
        }
    }
    
    
     private void setAudittraceValue(HttpServletRequest request,AccountTempBean templateBean) throws Exception{
        
        
        try{
        systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
        String uniqueId = request.getParameter("");
       
        systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
        systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
        //set unique id
        systemAuditBean.setUniqueId(uniqueId);
        //set description 
        systemAuditBean.setDescription("Delete Account Template. Account Template Code: '"+ templateBean.getTemplateCode()+ "' by: "  +sessionVarlist.getCMSSessionUser().getUserName());      
        systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
        systemAuditBean.setSectionCode(SectionVarList.TEMPLATEMGT);
        systemAuditBean.setPageCode(PageVarList.ACCTEMPLATE);
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
          
        }
        catch(Exception ex){
            throw ex;
        }}
     
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
     
    private List<AccountTempBean> getAllAccTemplateList() throws Exception {
        try {
            templateManager = new AccountTemplateManager();
            templateList = templateManager.getAllTemplateLst();
            return templateList;
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
