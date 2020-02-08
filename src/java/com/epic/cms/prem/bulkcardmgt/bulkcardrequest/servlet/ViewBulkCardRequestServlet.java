/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardrequest.servlet;

import com.epic.cms.admin.camm.numbergeneration.businesslogic.NumberGeneFormatBusinessLogic;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.templatemgt.accounttemplate.businesslogic.AccountTemplateManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.businesslogic.BulkCardRequestManager;
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
public class ViewBulkCardRequestServlet extends HttpServlet {
        //initialize variables
    
    
    HttpSession sessionObj;
    private SystemUserBean sysUsrBean = null;
    private RequestDispatcher rd;
    private SessionUser sessionUser;    
    private SystemUserManager sysUserMgr;  
    private SessionVarList sessionVarlist;    
    //----------------------------------
    private List<BulkCardRequestBean> reqList = null; 
    private BulkCardRequestManager bulkCdReqMgr = null;   
    private String url = "/prem/bulkcardmgt/bulkcardrequesthome.jsp";

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
            /////////////////////////////////////////////////////////////////////
            sessionObj = request.getSession(false);
            try {
                sysUserMgr = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                //check system user is in same session or not
                try {

                    if (!sysUserMgr.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
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
                
                //get the user given merchant code he wants to view the relevant data
                String batchId = request.getParameter("id");
                //retrieve merchant data from MCC table and assign to the bean for the default table
                this.getUserDetails(sessionUser.getUserName());
                this.getAllBulkCdReq(sysUsrBean);
                request.setAttribute("bulkCdReqList", reqList);
                BulkCardRequestBean bean = new BulkCardRequestBean();
                //create a bean and assign relevant data one by one for the given id
                for (BulkCardRequestBean allBean : reqList) {
                    if (allBean.getBatchID().equals(batchId)) {   
                       bean = allBean;
                    }
                }
                if (bean != null) {
                    request.setAttribute("operationtype", request.getParameter("opType"));
                    request.setAttribute("exchangeBean", bean); 
                    if(request.getParameter("opType").equals("receive")){
                        if(!bean.getStatus().equals("BCDC")){
                            request.setAttribute("operationtype", "add");
                            request.setAttribute("errorMsg", MessageVarList.NO_RECEIVE_CONFIRM);
                            rd = getServletContext().getRequestDispatcher("/LoadBulkCardRequestServlet");
                            
                        
                        }else{
                        rd = getServletContext().getRequestDispatcher(url);
                        }
                    }else{
                        rd = getServletContext().getRequestDispatcher(url);
                    }

                    
                }

                rd.forward(request, response);

            } catch (Exception e) {
                request.setAttribute("operationtype", "add");
                request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            }


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
        try {
            
        } finally {            
            out.close();
        }
    }
    
    private void getAllBulkCdReq(SystemUserBean sysUsrBean) throws Exception {
        try {

            bulkCdReqMgr = new BulkCardRequestManager();
            reqList = bulkCdReqMgr.getAllBulkCdReq(sysUsrBean);
        } catch (SQLException ex) {
            throw ex;
        }

    }
    
    private void getUserDetails(String username) throws Exception {
        try {

            bulkCdReqMgr = new BulkCardRequestManager();
            sysUsrBean = bulkCdReqMgr.getUserDetails(username);
        } catch (SQLException ex) {
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ViewBulkCardRequestServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ViewBulkCardRequestServlet.class.getName()).log(Level.SEVERE, null, ex);
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
