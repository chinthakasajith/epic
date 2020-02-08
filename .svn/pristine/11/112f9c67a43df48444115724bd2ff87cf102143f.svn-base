/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.MerchantPaymentCycleBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.MerchantPaymentCycleManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
/**
 *
 * @author admin
 */
public class LoadMerchantPaymentCycleServlet extends HttpServlet {

    //initializing variables
    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private List<String> userTaskList;
    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private List<StatusBean> statusList;
    private List<String> dayList;
    private List<MerchantPaymentCycleBean> paymentList;
    
    MerchantPaymentCycleManager mManager;
    String url = "/administrator/controlpanel/systemconfigmgt/merchantpaymentcyclehome.jsp";
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            //call the existing session
            sessionObj = request.getSession(false);
            
            try {

                request.setAttribute("operationtype", "add");
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                try {

                    //check system user is in the same session or not
                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        //if user is not in the same session
                        throw new NewLoginSessionException();
                    }
                } catch (NewLoginSessionException nlex) {

                    throw new NewLoginSessionException();
                }
                try {

                    //set page code and task code
                    String pageCode = PageVarList.MERCHANT_PAYMENT_CYCLE;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    //check user has the required access to the page
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    } else {

                        //if user do not have required access
                        throw new AccessDeniedException();
                    }
                    sessionVarlist.setUserPageTaskList(userTaskList);

                } catch (AccessDeniedException adex) {
                    throw adex;
                }
            } catch (NullPointerException ex) {

                throw new SesssionExpException();
            }
            this.getAllPaymentData(); 
            this.getMonthPaymentDayList();
            request.setAttribute("paymentList", paymentList);
            request.setAttribute("monthDayList", dayList);
            this.getAllStatus(StatusVarList.GENESTATUSCAT);
            sessionVarlist.setStatusList(statusList);            
      
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
            rd = request.getRequestDispatcher(url);            
            
        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
                    
        } finally {            
            rd.forward(request, response);
            out.close();
        }
    }

    private void getAllStatus(String categoryCode) throws Exception {
        try {
            systemUserManager = new SystemUserManager();
            statusList = systemUserManager.getStatusByUserRole(categoryCode);
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {

        boolean isValidTaskAccess = false;

        try {

            systemUserManager = new SystemUserManager();
            userTaskList = systemUserManager.getTasksByPageCodeAndUserRole(userrole, pagecode);
            for (String usertask : userTaskList) {
                //if user has the required access to the page
                if (task.equals(usertask)) {
                    isValidTaskAccess = true;
                }
            }
            return isValidTaskAccess;
        } catch (Exception ex) {

            throw ex;
        }

    }
    
    private void getAllPaymentData() throws Exception {
        try {
            mManager = new MerchantPaymentCycleManager();
            paymentList = mManager.getAllPaymentData();
        } catch (Exception ex) {
            throw ex;
        }
    }

public void getMonthPaymentDayList(){
        
        dayList = new ArrayList<String>();
        
        for (int i = 1; i < 29; i++) {
        dayList.add(Integer.toString(i));
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
