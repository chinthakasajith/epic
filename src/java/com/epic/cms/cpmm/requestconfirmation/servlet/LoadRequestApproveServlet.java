/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.requestconfirmation.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardProductMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.callcenter.card.businesslogic.CardBlockManager;
import com.epic.cms.callcenter.card.businesslogic.CardRequestManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.cpmm.requestconfirmation.bean.RequestConfirmationBean;
import com.epic.cms.cpmm.requestconfirmation.businesslogic.RequestConfirmationManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
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
 * @author nisansala
 */
public class LoadRequestApproveServlet extends HttpServlet {
    
    //initializing variables
    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private List<String> userTaskList;    
    private SessionUser sessionUser = null;
    private SessionVarList sessionVarlist = null;    
    private SystemUserManager systemUserManager = null; 
    //----------------------------------------------------
    private CardBlockManager cdBlckMgr;
    private CardRequestManager cdReqMgr;  
    private HashMap<String, String> approveStatus;
    private RequestConfirmationBean card = null;
    private RequestConfirmationManager reqConfMgr;
    private HashMap<String, String> cardStatus = null;
    private HashMap<String, String> blockReasons = null;
    private HashMap<String, String> cardCategory;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> reasonCodeList = null;
    private ApplicationAssignManager appAssignManager;
    private CardProductMgtManager cardProductMgtManager;
    private String url = "/cpmm/requestconfirm/requestapprovehome.jsp";

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
                request.setAttribute("operationType", "search");

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
                try {
                    //set page code and task codes
                    String pageCode = PageVarList.REQUESTAPPROVE;
                    String taskCode = TaskVarList.ACCESSPAGE;


                    //check whether userrole has access to this page for required task
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        try {

                            request.setAttribute("operationtype", "search");                            
                            this.getPriorityLevels();
                            this.getApproveStatus();
                            
                            reasonCodeList = new HashMap<String, String>();
                            reasonCodeList.put(StatusVarList.CARD_ACTIVATION_REQ, "Card Activation");
                            reasonCodeList.put(StatusVarList.CARD_REPLACE, "Card Replace");
                            reasonCodeList.put(StatusVarList.PIN_REISSUE, "PIN Reissue");
                            reasonCodeList.put(StatusVarList.CARD_REISSUE, "Card Reissue");
                            request.setAttribute("reasonCodeList", reasonCodeList);
                            
                            request.setAttribute("cardBean", card);
                            request.setAttribute("operationtype", "search");                            
                            request.setAttribute("cardTypeList", cardTypeList);
                            request.setAttribute("approveStatus", approveStatus);                            
                            request.setAttribute("cardCategoryList", cardCategory);
                            request.setAttribute("priorityLevelList", priorityLevelList);
                            
                            
                            rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);

                        } catch (Exception e) {

                            request.setAttribute("operationtype", "search");
                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);

                        }

                        sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
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

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
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

    private void getCardTypes() throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            cardTypeList = cardProductMgtManager.getAllCardTypeList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getPriorityLevels() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            priorityLevelList = appAssignManager.getAllPriorityLevels();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardCategory() throws Exception {
        try {

            cdReqMgr = new CardRequestManager();
            cardCategory = cdReqMgr.getCardCategory();

        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private void getApproveStatus() throws Exception {
        try {

            reqConfMgr = new RequestConfirmationManager();
            approveStatus = reqConfMgr.getApproveStatus();

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
