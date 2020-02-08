/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.collection.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.CaseTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.LadderBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.businesslogic.LadderManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.ApplicationModuleMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.ApplicationSectionPageManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ruwan_e
 */
public class LoadLadderManagementServlet extends HttpServlet {

    private RequestDispatcher rd = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String url = "/administrator/controlpanel/systemconfigmgt/laddermanagementhome.jsp";
    private LadderManager ladderManager;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
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
            try {
                sessionObj = request.getSession(false);
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                try {
                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }

                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();

                }

                try {
                    //set page code and task codes
                    String pageCode = PageVarList.STANDING_ORDER_TYPES;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        sessionVarlist.setUserPageTaskList(userTaskList);

                        request.setAttribute("operationtype", "add");

                        final ArrayList<CaseTypeBean> cases = getCases();
                        request.setAttribute("caseList", cases);

                        final Map<String, String> status = getStatus();
                        request.setAttribute("statusList", status);

                        final Map<String, String> cardTypes = getCardTypes();
                        request.setAttribute("cardTypeList", cardTypes);

                        final ArrayList<LadderBean> ladders = getLadders();
                        request.setAttribute("ladders", ladders);


//                        this.getCurrency();
//                        this.getAreas();
//                        this.getBanks();
//                        this.getCommonparam();

                        rd = request.getRequestDispatcher(url);

                    } else {
                        throw new AccessDeniedException();

                    }

                } catch (AccessDeniedException adex) {
                    throw adex;

                }

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }
        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);

        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.ACCESS_DENIED_TASK);
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

    private ArrayList<CaseTypeBean> getCases() throws Exception {
        try {

            ladderManager = new LadderManager();
            ArrayList<CaseTypeBean> cases;
            cases = ladderManager.getAllCases();
            return cases;

        } catch (Exception ex) {
            throw ex;
        }
    }

    private Map<String, String> getStatus() throws Exception {
        try {

            ladderManager = new LadderManager();
            Map<String, String> cases;
            cases = ladderManager.getStatus();
            return cases;

        } catch (Exception ex) {
            throw ex;
        }
    }

    private Map<String, String> getCardTypes() throws Exception {
        try {
            ladderManager = new LadderManager();
            Map<String, String> cardTypes ;
            cardTypes = ladderManager.getCardTypes();
            return cardTypes;

        } catch (Exception ex) {
            throw ex;
        }
    }

    private ArrayList<LadderBean> getLadders() throws Exception {
        try {
            ladderManager = new LadderManager();
            ArrayList<LadderBean> ladders ;
            ladders = ladderManager.getAllLadders();
            return ladders;

        } catch (Exception ex) {
            throw ex;
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
