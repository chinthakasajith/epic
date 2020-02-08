/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.generalledgermgtmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.AssignTxnBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.ChannelLisnrTypeBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.GeneralLedgerMgtBean;
import com.epic.cms.backoffice.generalledgermgtmgt.businesslogic.AssignTxnTypesToGLAccountManager;
import com.epic.cms.backoffice.generalledgermgtmgt.businesslogic.GeneralLedgerMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author badrika
 */
public class GetChannelListenerListServlet extends HttpServlet {

    private AssignTxnTypesToGLAccountManager assignManager;
    private HttpSession sessionObj;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private String url = "/backoffice/generalledgermgt/assigntxntoglaccount.jsp";
    private RequestDispatcher rd;
    private List<String> userTaskList;
    private AssignTxnBean assBean;
    private GeneralLedgerMgtManager glmanager;
    private List<GeneralLedgerMgtBean> allGLAccList;
    private List<AssignTxnBean> allOnlineGLAccList;

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
            sessionObj = request.getSession(false);

            try {
                request.setAttribute("operationtype", "add");

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
                try {
                    //set page code and task codes
                    String pageCode = PageVarList.ASSIGN_TXN_TO_GL;
                    String taskCode = TaskVarList.ACCESSPAGE;


                    //check whether userrole can access this page and task
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        try {


                            String accNo = request.getParameter("glacc");//credit
                            String drAccNo=request.getParameter("drglacc");//debit 
                            
                            String chOrLs = request.getParameter("chanOrLisn");

                            assBean = new AssignTxnBean();

                            assBean.setGlAccNo(accNo);//credit
                            assBean.setDrGlAccNo(drAccNo);//debit
                            
                            assBean.setChanekOrLisnr(chOrLs);

                            request.setAttribute("assignBean", assBean);


                            List<ChannelLisnrTypeBean> chanelLisnrTypeList = new ArrayList<ChannelLisnrTypeBean>();
                            assignManager = new AssignTxnTypesToGLAccountManager();
                            chanelLisnrTypeList = assignManager.getChanelLisnrTypeList(chOrLs);
                            request.setAttribute("chlsList", chanelLisnrTypeList);


                            glmanager = new GeneralLedgerMgtManager();

                            allGLAccList = glmanager.getAllGlAccounts();
                            request.setAttribute("accList", allGLAccList);

                            allOnlineGLAccList = assignManager.getOnlineGlAccounts();
                            request.setAttribute("allOnlineGLAccList", allOnlineGLAccList);

                            rd = getServletContext().getRequestDispatcher(url);

                        } catch (Exception e) {
                            request.setAttribute("operationtype", "add");
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
            rd.forward(request, response);
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
