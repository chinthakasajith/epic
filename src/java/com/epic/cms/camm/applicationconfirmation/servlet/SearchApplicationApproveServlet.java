/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfirmation.bean.ApplicationDetailsbean;
import com.epic.cms.camm.applicationconfirmation.businesslogic.ApplicationConfirmationManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.SearchAssignAppBean;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardCategoryBean;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
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
 * @author mahesh_m
 */
public class SearchApplicationApproveServlet extends HttpServlet {

   private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private ApplicationConfirmationManager confirmationManager;
    private SystemAuditBean systemAuditBean = null;
    private ApplicationAssignManager appAssignManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String errorMessage = null;
    private SearchAssignAppBean searchBean = null;
    private HashMap<String, String> priorityLevelList = null;
    private List<CardCategoryBean> cardCategoryList;
    private List<String> usersList = null;
    private List<ApplicationDetailsbean> searchList = null;
    private String appliactionId = null;
    private String url = "/camm/applicationconfirmation/confirmationtableview.jsp";

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

            this.getAllUserList();
            this.getAllPriorityLevelList();
            this.getCardCategories();

            request.setAttribute("usersList", usersList);
            request.setAttribute("priorityLevelList", priorityLevelList);
            request.setAttribute("cardCategoryList", cardCategoryList);

            try {
                //set page code and task codes
                String pageCode = PageVarList.APPLICATONAPPROVE;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    this.setUserInputToBean(request);
                    request.setAttribute("searchappbean", searchBean);


                    if (this.validateUserInput(searchBean, request)) {

                        confirmationManager = new ApplicationConfirmationManager();
//                        this.setAudittraceValue(request);
                      
                            this.searchAssignApplication(searchBean);
                            request.setAttribute("searchList", searchList);
                       

                        sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);


                    } else {

                        request.setAttribute("errorMsg", errorMessage);

                    }

                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);


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
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_ASSIGN_APPLICATION);
            rd = request.getRequestDispatcher(url);

        } finally {
//            rd.forward(request, response);
            out.close();
        }
    }

    ///////////////////////////
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

        appliactionId = request.getParameter("appliactionid").trim();
        String priorityCode = request.getParameter("prioritycode").trim();
        String fromDate = request.getParameter("fromdate").trim();
        String toDate = request.getParameter("todate").trim();
        String cardCategory = request.getParameter("cardCategory").trim();

        searchBean = new SearchAssignAppBean();

        searchBean.setApplicationId(appliactionId);
        searchBean.setPriorityLevel(priorityCode);
        searchBean.setFromDate(fromDate);
        searchBean.setToDate(toDate);
        searchBean.setCardCategory(cardCategory);
        //set current user role
        searchBean.setCurrentUserRole(sessionUser.getUserRole());

    }

    public boolean validateUserInput(SearchAssignAppBean searchBean, HttpServletRequest request) throws Exception {
        boolean isValid = true;


        //validate user Role code
        try {

            if (!UserInputValidator.isCorrectString(searchBean.getApplicationId())) {
                isValid = false;
                errorMessage = MessageVarList.APPASSIGN_APPLICATIONCODE_INVALID;
            }


        } catch (Exception ex) {
            isValid = false;

        }

        return isValid;
    }

    private void searchAssignApplication(SearchAssignAppBean searchBean) throws Exception {

        try {

            confirmationManager = new ApplicationConfirmationManager();
            searchList = confirmationManager.getConfirmationApplicationList(searchBean);


        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllUserList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            usersList = appAssignManager.getAllUserList(PageVarList.APPLICATONAPPROVE);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllPriorityLevelList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            priorityLevelList = appAssignManager.getAllPriorityLevels();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardCategories() throws Exception {
        try {
            systemUserManager = new SystemUserManager();
            cardCategoryList = systemUserManager.getCardCategoryList();
        } catch (Exception ex) {
            throw ex;
        }
    }

//    private void setAudittraceValue(HttpServletRequest request) throws Exception {
//
//
//        try {
//            systemAuditBean = new SystemAuditBean();
//            ////get unique Id from the page.It may be the primary key---------------
//            String uniqueId = request.getParameter("");
//
//            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
//            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
//            //set unique id
//            systemAuditBean.setUniqueId(uniqueId);
//            //set description 
//            systemAuditBean.setDescription("Search credit officer applications by " + sessionVarlist.getCMSSessionUser().getUserName());
//            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
//            systemAuditBean.setSectionCode(SectionVarList.APPLICATIONAPPROVE);
//            systemAuditBean.setPageCode(PageVarList.CREDITOFFICER);
//            systemAuditBean.setTaskCode(TaskVarList.SEARCH);
//            systemAuditBean.setIp(request.getRemoteAddr());
//            //add remarks here
//            systemAuditBean.setRemarks("");
//            //set field name which is being changed(only if)
//            systemAuditBean.setFieldName("");
//            //set old value of change if required
//            systemAuditBean.setOldValue("");
//            //set new value of change if required
//            systemAuditBean.setNewValue("");
//
//
//            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//
//
//
//    }
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
