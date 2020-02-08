/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.accounttemplate.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.admin.templatemgt.accounttemplate.businesslogic.AccountTemplateManager;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nisansala
 */
public class SetAccountRiskProfileDropDownServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private AccountTemplateManager templateManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean;
    private HashMap<String, String> accountRskProf = null;
    private List<CustomerTempBean> cusTemplateList = null;
    
    AccountTempBean inputBean;
    private String url = "/administrator/templatemgt/accounttemplate/accouonttemplatehome.jsp";

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //call to existing session
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
            try {
                templateManager = new AccountTemplateManager();
                accountRskProf = templateManager.getAllAccountRskProf(request.getParameter("id"));
                //set operation type

                try {
                    
                    String opType = request.getParameter("opType");
                    //assign user input to the bean
                    setUserInputToBean(request,opType);
                    
                    templateManager = new AccountTemplateManager();
                    cusTemplateList = templateManager.getCustomerTemplates(request.getParameter("staff"));
                    
                    request.setAttribute("operation", opType);
                    request.setAttribute("isBack", "back");
                    request.setAttribute("operationtype", opType);
                    request.setAttribute("cusTemplateList", cusTemplateList);
                    request.setAttribute("riskProfLst", accountRskProf);
                    request.setAttribute("templateBean", inputBean);

                } catch (Exception e) {

                    request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                }
//                rd = getServletContext().getRequestDispatcher(url);
//                rd.forward(request, response);
                
                rd = getServletContext().getRequestDispatcher("/ManageAccountTempalteServlet");
                rd.include(request, response);


            } catch (Exception e) {
            }
        }         catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);


        }  finally {
            out.close();
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request,String operationType) throws Exception {
        Boolean status = true;
        try {
            inputBean = new AccountTempBean();

            inputBean.setTemplateCode(request.getParameter("tempaltcode"));
            inputBean.setTemplateName(request.getParameter("templatename"));
            inputBean.setValiedFrom(request.getParameter("fromdate"));
            inputBean.setValiedTo(request.getParameter("todate"));
            inputBean.setTotalCreditLimit(request.getParameter("creditlimit"));
            inputBean.setStaff(request.getParameter("staff"));
            inputBean.setProductCode(request.getParameter("product"));
            inputBean.setCardType(request.getParameter("cardType"));
            inputBean.setCurrencyCode(request.getParameter("currency"));
            inputBean.setCustomerTemplateCode(request.getParameter("cusTempCode"));
            inputBean.setStatus(request.getParameter("status"));
            inputBean.setInterestprofileCode(request.getParameter("interestProf"));
            inputBean.setBillCycle(request.getParameter("bill"));
            inputBean.setStatementProf(request.getParameter("statement"));
            inputBean.setLastUpdateduser(sessionUser.getUserName());
            
            if(operationType.equals("edit")){
            inputBean.setAccRskProf(request.getParameter("riskProf"));}


        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SetAccountRiskProfileDropDownServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SetAccountRiskProfileDropDownServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
