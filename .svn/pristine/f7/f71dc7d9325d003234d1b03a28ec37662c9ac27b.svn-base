/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.requestconfirmation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.cpmm.requestconfirmation.bean.RequestConfirmationBean;
import com.epic.cms.cpmm.requestconfirmation.businesslogic.RequestConfirmationManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class SearchRequestApproveServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SystemAuditBean systemAuditBean = null;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String errorMessage = null;
    //------------------------------------
    private HashMap<String, String> approveStatus;
    private HashMap<String, String> reasonCodeList;
    private ApplicationAssignManager appAssignManager;
    private HashMap<String, String> priorityLevelList = null;
    RequestConfirmationBean inputBean = null;
    List<RequestConfirmationBean> searchList = null;
    private RequestConfirmationManager reqConfMgr;
    private String url = "/cpmm/requestconfirm/requestapprovehome.jsp";
    private boolean isValidate;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
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
                String pageCode = PageVarList.REQUESTAPPROVE;
                String taskCode = TaskVarList.SEARCH;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    //set the operation type                    
                    request.setAttribute("operationtype", "search");
                    //set the user inputs to bean
                    this.setUserInputToBean(request);

                    try {
                        //get priority levels
                        this.getPriorityLevels();
                        //get the possible approve status
                        this.getApproveStatus();

                        reasonCodeList = new HashMap<String, String>();
                        reasonCodeList.put(StatusVarList.CARD_ACTIVATION_REQ, "Card Activation");
                        reasonCodeList.put(StatusVarList.CARD_REPLACE, "Card Replace");
                        reasonCodeList.put(StatusVarList.PIN_REISSUE, "PIN Reissue");
                        reasonCodeList.put(StatusVarList.CARD_REISSUE, "Card Reissue");

                        if (this.validateUserInput(inputBean, request)) {

                            this.searchRequests(inputBean);

                            request.setAttribute("reasonCodeList", reasonCodeList);
                            request.setAttribute("approveStatus", approveStatus);
                            request.setAttribute("priorityLevelList", priorityLevelList);
                            request.setAttribute("cardBean", inputBean);
                            request.setAttribute("searchedList", searchList);
                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                        } else {

                            request.setAttribute("operationtype", "search");
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute("approveStatus", approveStatus);
                            request.setAttribute("reasonCodeList", reasonCodeList);
                            request.setAttribute("priorityLevelList", priorityLevelList);
                            request.setAttribute("cardBean", inputBean);
                        }

                    } catch (SQLException e) {
                        OracleMessage message = new OracleMessage();
                        String oraMessage = message.getMessege(e.getMessage());
                        request.setAttribute("errorMsg", oraMessage);
                    }
                    rd = getServletContext().getRequestDispatcher(url);

                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }

            } catch (AccessDeniedException adex) {
                throw adex;

            }

        }//catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "search");
            request.setAttribute("errorMsg", MessageVarList.TRMINAL_MGT_TERMINAL_ASSIGNMNT);
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

    public void setUserInputToBean(HttpServletRequest request) {
        System.out.println(">>>>>>>>>>>>>>>>>>" + request.getParameter("isBack"));

//        if (request.getParameter("isBack") != null) {
//            terminalBean = new TerminalDataCaptureBean();
//            if (sessionVarlist.getSearchBean() != null) {
//                terminalBean = sessionVarlist.getSearchBean();
//            }
//
//        } else if (request.getAttribute("isBack") != null) {
//            terminalBean = new TerminalDataCaptureBean();
//            if (sessionVarlist.getSearchBean() != null) {
//                terminalBean = sessionVarlist.getSearchBean();
//            }
//
//        } else {
        inputBean = new RequestConfirmationBean();

        inputBean.setCardNo(request.getParameter("cardno"));
        inputBean.setPriorityLevel(request.getParameter("prioritycode"));
        inputBean.setStatus(request.getParameter("approvestatus"));
        inputBean.setReasonCode(request.getParameter("reasoncode"));
        inputBean.setLoggeduser(sessionUser.getUserName());

        sessionVarlist.setConfBean(inputBean);

        //sessionVarlist.setTermBean(terminalBean);
        //sessionVarlist.setSearchBean(terminalBean);
        // }
    }

    public boolean validateUserInput(RequestConfirmationBean inputBean, HttpServletRequest request) throws Exception {
        boolean isValid = true;

        //validate user Role code
        try {

            if (!UserInputValidator.isAlphaNumeric(inputBean.getCardNo())) {
                isValid = false;
                errorMessage = MessageVarList.ERROR_SEARCH_PARAMETER;
            } else if (inputBean.getReasonCode().contentEquals("") || inputBean.getReasonCode().isEmpty()) {
                isValid = false;
                errorMessage = MessageVarList.REASON_CODE_EMPTY;
            }

        } catch (Exception ex) {
            isValid = false;

        }

        return isValid;
    }

    private void searchRequests(RequestConfirmationBean inputBean) throws SQLException, Exception {
        reqConfMgr = new RequestConfirmationManager();
        searchList = reqConfMgr.searchRequests(inputBean);
        //sessionVarlist.setTerminalDataBeanList(searchList);
    }

    private void getApproveStatus() throws Exception {
        try {

            reqConfMgr = new RequestConfirmationManager();
            approveStatus = reqConfMgr.getApproveStatus();

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
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
