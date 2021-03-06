/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AquireBinBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AquireBinFormBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.AquireBinManagementManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
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
import java.util.HashMap;
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
public class LoadAquireBinManagement extends HttpServlet {

    private HttpSession sessionObj;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private RequestDispatcher rd;
    private String url = "/administrator/controlpanel/systemconfigmgt/aquirebinmgt.jsp";
    private List<String> userTaskList;
    private AquireBinManagementManager AquireBinManager;
    private List<AquireBinBean> aquireBinBeanList;
    private List<AquireBinFormBean> ChannelList, cardTypeList, entryModeList, statusList;
     private HashMap<String, String> cardKeyList = null;
    //   private List<AquireBinFormBean> cardTypeList;
    //   private List<AquireBinFormBean> entryModeList,statusList;

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
                //set page code and task codes
                String pageCode = PageVarList.AQUIREBIN;
                String taskCode = TaskVarList.ACCESSPAGE;


                //check whethre userrole have an access for this page and task
                if (!this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();
                }

                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                throw adex;

            }
            /////////////
            request.setAttribute("operationtype", "add");

            this.getAllAquireBinDetailList();
            this.getChannelList();
           // this.getCardTypeList();
          //  this.getEntryModeList();//del
            this.getStatusList();
            this.getCardKeyList();


            sessionVarlist.setAquireChannelList(ChannelList);
            //sessionVarlist.setAquireCardTypeList(cardTypeList);
           // sessionVarlist.setAquireEntryModeList(entryModeList);
            sessionVarlist.setAquireStatusList(statusList);
            sessionVarlist.setCardKeyList(cardKeyList);



            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);


            request.setAttribute("aquireBinBeanList", aquireBinBeanList);

            rd = getServletContext().getRequestDispatcher(url);

            ///////////////


        } catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        }//catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);


        } catch (Exception ex) {
            request.setAttribute("errorMsg", "Error occurs");
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * to get card type list
     * @throws Exception 
     */
    private void getCardTypeList() throws Exception {
        try {

            AquireBinManager = new AquireBinManagementManager();
            cardTypeList = AquireBinManager.getCardTypeList();

        } catch (Exception ex) {
            throw ex;
        }
    }
    
     private void getCardKeyList() throws Exception {
        try {

            AquireBinManager = new AquireBinManagementManager();
            cardKeyList = new HashMap<String, String>();
            cardKeyList = AquireBinManager.getCardKeyList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to get channel list
     * @throws Exception 
     */
    private void getChannelList() throws Exception {
        try {

            AquireBinManager = new AquireBinManagementManager();
            ChannelList = AquireBinManager.getChannelList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to get status list
     * @throws Exception 
     */
    private void getStatusList() throws Exception {
        try {

            AquireBinManager = new AquireBinManagementManager();
            statusList = AquireBinManager.getStatusList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to get entry mode list
     * @throws Exception 
     */
//    private void getEntryModeList() throws Exception {//del
//        try {
//
//            AquireBinManager = new AquireBinManagementManager();
//            entryModeList = AquireBinManager.getEntryModeList();
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }

    /**
     * to get all aquire bin detail list
     * @throws Exception 
     */
    private void getAllAquireBinDetailList() throws Exception {
        try {

            AquireBinManager = new AquireBinManagementManager();
            aquireBinBeanList = AquireBinManager.getAllAquireBinDetailList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to check access is valid
     * @param userrole
     * @param pagecode
     * @param task
     * @return
     * @throws Exception 
     */
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
