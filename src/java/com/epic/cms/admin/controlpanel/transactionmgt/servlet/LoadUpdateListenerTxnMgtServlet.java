/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ChannelIncomeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ListenerTxnBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.OnlineTxnTypeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.ChannelTxnMgtManager;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.ListenerTxnMgtManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
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
 * @author nisansala
 */
public class LoadUpdateListenerTxnMgtServlet extends HttpServlet {

    //initializing variables
    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private SessionUser sessionUser = null;
    private List<String> userTaskList = null;
    private SessionVarList sessionVarlist = null;
    private SystemUserManager systemUserManager = null;
    //------------------------------------------------
    private ListenerTxnBean commonBean;
    private List<ListenerTxnBean> beanList;
    private ListenerTxnMgtManager listenMgr;
    private List<OnlineTxnTypeBean> assignList;
    private List<OnlineTxnTypeBean> notAssignList;
    private List<ListenerTxnBean> unusedListenerList = null;
    private String url = "/administrator/controlpanel/transactionMgt/listenertxnhome.jsp";

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
            //----------------------------------------------------------------------------------------------------------------------//

            try {
                //set page code and task codes
                String pageCode = PageVarList.LISTEN_TXN;
                String taskCode = TaskVarList.UPDATE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    
                    try {
                        if (request.getParameter("opType").equals("view")) {
                            request.setAttribute("operationtype", "view");
                        } else if (request.getParameter("opType").equals("update")) {
                            request.setAttribute("operationtype", "update");
                        }
                        
                        commonBean = new ListenerTxnBean();
                        commonBean.setLitenerId(request.getParameter("id"));
                        
                        listenMgr = new ListenerTxnMgtManager();
                        //load all istener txn
                        beanList = listenMgr.getAllListenerTxn();
                        request.setAttribute("beanList", beanList);
                        //load assign txn for the listener
                        assignList = listenMgr.getAssignTxn(request.getParameter("id"));
                        request.setAttribute("assignlist", assignList);
                        //load not assigned txn
                        notAssignList = listenMgr.getNotAssignTxn(request.getParameter("id"));
                        request.setAttribute("notAssignList", notAssignList);
                        //load unused listeners exept this listener
                        unusedListenerList = listenMgr.getUnusedListenersUpdate(request.getParameter("id"));
                        sessionVarlist.setUnusedListenerList(unusedListenerList);
                        
                        request.setAttribute("incomeBean", commonBean);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    } catch (Exception e) {
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }
                } else {
                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();
                }
                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);
            } catch (AccessDeniedException adex) {
                throw adex;
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
        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (Exception ex) {
        } finally {
            out.close();
        }
    }
    
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
