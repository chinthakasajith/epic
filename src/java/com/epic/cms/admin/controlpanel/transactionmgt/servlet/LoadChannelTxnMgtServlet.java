/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ChannelIncomeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.OnlineTxnTypeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.ChannelTxnMgtManager;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.TxnTypeMgtManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
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
 * @author nisansala
 */
public class LoadChannelTxnMgtServlet extends HttpServlet {

    //initializing variables
    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private List<StatusBean> statusList;
    private SessionUser sessionUser = null;
    private List<String> userTaskList = null;
    private SystemUserManager sysUserMgr = null;
    private SessionVarList sessionVarlist = null;
    private SystemUserManager systemUserManager = null;
    //------------------------------------------------
    private TxnTypeMgtManager txntm = null;
    private ChannelTxnMgtManager channelMgr;
    private List<ChannelIncomeBean> beanList = null;
    private String url = "/administrator/controlpanel/transactionMgt/channeltxnhome.jsp";

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
        sessionObj = request.getSession(false);

        try {
            //--------------------------------------------------------------------------------------------------//
            try {
                request.setAttribute("operationType", "add");

                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                //check system user is in the same session or not
                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        //user not in same session.
                        throw new NewLoginSessionException();

                    }

                } catch (NewLoginSessionException nlex) {
                    //throw lst login close exception
                    throw new NewLoginSessionException();

                }
                
                
                System.out.println("*******************"+request.getAttribute("successMsg"));
                
                
                request.setAttribute("successMsg", request.getAttribute("successMsg"));
                
                try {
                    //set page code and task codes
                    String pageCode = PageVarList.CHANNEL_TXN;
                    String taskCode = TaskVarList.ACCESSPAGE;


                    //check whether userrole can access this page and task
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        request.setAttribute("operationtype", "add");
                        try {
                            txntm = new TxnTypeMgtManager();
                            channelMgr = new ChannelTxnMgtManager();
                            //load all channel types and set to session
                            List<ChannelTypeBean> channelList = channelMgr.getOnlineChannel();
                            sessionVarlist.setChannelTypeList(channelList);
                            //load all txn types and set to session
                            List<OnlineTxnTypeBean> onlineTxnTypeList = txntm.getOnlineTxnType();
                            sessionVarlist.setOnlineTxnTypeList(onlineTxnTypeList);
                            //load all channel txn                            
                            beanList = channelMgr.getAllChannelTxn();
                            request.setAttribute("beanList", beanList);
                            //load unused channels
                            List<ChannelTypeBean> unusedChannelList = channelMgr.getUnusedChannels();
                            sessionVarlist.setUnusedChannelList(unusedChannelList);

                            this.getAllStatus(StatusVarList.GENESTATUSCAT);
                            sessionVarlist.setStatusList(statusList);

                            //sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                            rd = getServletContext().getRequestDispatcher(url);

                        } catch (SQLException e) {
                            //show the messages which has thown when inserting
                            OracleMessage message = new OracleMessage();
                            String oraMessage = message.getMessege(e.getMessage());
                            request.setAttribute("errorMsg", oraMessage);
                            rd = getServletContext().getRequestDispatcher(url);



                        } catch (Exception e) {

                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            rd = getServletContext().getRequestDispatcher(url);

                        }


                    } else {

                        //if invalid throw access denied exception
                        throw new AccessDeniedException();

                    }

                    //set tasks to the session
                    sessionVarlist.setUserPageTaskList(userTaskList);


                } catch (AccessDeniedException adex) {
                    throw adex;
                }
            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }

            //------------------------------------------------------------------------------------------------------------//
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);


        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);

        } finally {
            rd.include(request, response);

        }
    }

    private void getAllStatus(String categoryCode) throws Exception {
        try {
            sysUserMgr = new SystemUserManager();
            statusList = sysUserMgr.getStatusByUserRole(categoryCode);
        } catch (Exception e) {
            throw e;
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
