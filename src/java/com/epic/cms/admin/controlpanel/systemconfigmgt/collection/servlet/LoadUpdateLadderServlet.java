/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.collection.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.StandingOrderTypesBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.StandingOrderTypesManager;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.CaseTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.LadderBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.businesslogic.LadderManager;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.taglibs.standard.extra.spath.Step;

/**
 *
 * @author ruwan_e
 */
public class LoadUpdateLadderServlet extends HttpServlet {

    private RequestDispatcher rd;
    private List<String> userTaskList;
    private SessionUser sessionUser = null;
    private SessionVarList sessionVarlist = null;
    private SystemUserManager systemUserManager = null;
    private LadderManager ladderManager = null;
    private ArrayList<LadderBean> ladders = null;
    private List<CaseTypeBean> caseTypeBeanList = null;
    private List<CaseTypeBean> assignedCaseTypeBeans = null;
    LadderBean ladderBean;
    private String url = "/administrator/controlpanel/systemconfigmgt/laddermanagementhome.jsp";

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
            //call to existing session
            HttpSession sessionObj = request.getSession(false);
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
                String pageCode = PageVarList.LADDER_MANAGEMENT;
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        String code = request.getParameter("code");
                        ladders = getLadders();

                        //find the relevent record
                        for (LadderBean bean : ladders) {
                            if (bean.getCode().equals(code)) {
                                ladderBean = bean;
                            }
                        }
                        ladderManager = new LadderManager();
                        
                        caseTypeBeanList = ladderManager.getNotAssignedCaseTypeList(ladderBean.getCode());

                        assignedCaseTypeBeans = ladderManager.getAssignedCaseTypeList(ladderBean.getCode());
                        
                        if (ladderBean != null) {
                            request.setAttribute("operationtype", "update");
                            request.setAttribute("ladderBean", ladderBean);

                            

                            request.setAttribute("caseList", caseTypeBeanList);
                            request.setAttribute("assignedCaseList", assignedCaseTypeBeans);
//                            final ArrayList<CaseTypeBean> cases = getCases();
                            

                            String oldValue = ladderBean.getCode();
                            request.setAttribute("oldValue", oldValue);

                            final Map<String, String> status = getStatus();
                            request.setAttribute("statusList", status);

                            final Map<String, String> cardTypes = getCardTypes();
                            request.setAttribute("cardTypeList", cardTypes);

                            request.setAttribute("ladders", ladders);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        request.setAttribute("ladders", ladders);
                    }
                    rd = getServletContext().getRequestDispatcher(url);
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
        } //catch session exception
        catch (NewLoginSessionException nlex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
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
            return new LadderManager().getAllCases();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private Map<String, String> getStatus() throws Exception {
        try {
            return new LadderManager().getStatus();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private Map<String, String> getCardTypes() throws Exception {
        try {
            return new LadderManager().getCardTypes();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private ArrayList<LadderBean> getLadders() throws Exception {
        try {

            return new LadderManager().getAllLadders();

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
