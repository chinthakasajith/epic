/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.customertemplate.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.TemplateBean;
import com.epic.cms.admin.templatemgt.customertemplate.businesslogic.CustomerTemplateManager;
import com.epic.cms.admin.templatemgt.customertemplate.businesslogic.TemplateManager;
import com.epic.cms.application.common.bean.StatusBean;
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
public class SearchCustomerTempalteServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private CustomerTemplateManager templateManager;
    private SystemAuditBean systemAuditBean;
    private List<StatusBean> statusList=null ;
    private List<CustomerTempBean> searchList=null ;
    private String url = "/administrator/templatemgt/customertemplate/customertemplatemgthome.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        TemplateBean templateBean = new TemplateBean();
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

            
           
            sessionVarlist.setCMSSessionUser(sessionUser);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
            
            
            
            templateBean.setTemplateCatagoryCode("CUST");
            templateBean.setDescription(request.getParameter("templateName"));
            templateBean.setStatus(request.getParameter("status"));
            
            this.getStatusList();
            this.setAudittraceValue(request, templateBean);
            
            templateManager = new CustomerTemplateManager();
            //searchList = templateManager.searchCustomerTemplate(templateBean,systemAuditBean);
            
            request.setAttribute("statusLst", statusList);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);







        } catch (SQLException ex) {

            String errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", errorMessage);
            rd = getServletContext().getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", "Error when searching");
            rd = request.getRequestDispatcher(url); 

        } finally {
            out.close();
        }
    }
    
    
    private void setAudittraceValue(HttpServletRequest request,TemplateBean templateBean) throws Exception{
        
        
        try{
        systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
        String uniqueId = request.getParameter("");
       
        systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
        systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
        //set unique id
        systemAuditBean.setUniqueId(uniqueId);
        //set description 
        systemAuditBean.setDescription("Add Template '"+ templateBean.getDescription()+ "' by :" +sessionVarlist.getCMSSessionUser().getUserName());      
        systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
        systemAuditBean.setSectionCode(SectionVarList.TEMPLATEMGT);
        systemAuditBean.setPageCode(PageVarList.CUSTEMPLATE);
        systemAuditBean.setTaskCode(TaskVarList.SEARCH);
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
        }}
      
     //get StatusList
    private List<StatusBean> getStatusList() throws Exception {
        systemUserManager = new SystemUserManager();
        statusList = systemUserManager.getStatusByUserRole("GENR");
        return statusList;
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
