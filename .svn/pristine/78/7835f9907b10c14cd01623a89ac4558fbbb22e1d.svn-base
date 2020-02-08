/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.SearchDocumentVerifyBean;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.DocumentVerifyManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
 * @author nalin
 */
public class SearchCorporateCardVerifyServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private ApplicationAssignManager appAssignManager;
    private DocumentVerifyManager verifyManager;
    private SystemAuditBean systemAuditBean = null;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SearchDocumentVerifyBean searchBean = null;
    private String errorMessage = null;
    private String cardCategory = null;
    private List<String> userTaskList;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> statusMap = null;
    private List<StatusBean> statusList;
    private List<ApplicationAssignBean> searchList = null;
    private String url = "/camm/documentverification/corporatecardverifyhome.jsp";

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
                String pageCode = PageVarList.CORPORATE_CARD_VERIFICATION;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    statusMap = new HashMap<String, String>();
                    this.getAllPriorityLevelList();
                    this.getAllStatus(StatusVarList.ASSIGN_STATUSCODE);
                    for (int i = 0; i < statusList.size(); i++) {

                        if (statusList.get(i).getStatusCode().equals(StatusVarList.APP_CHECK_COMPLETE) || statusList.get(i).getStatusCode().equals(StatusVarList.APP_ONHOLD)) {

                            statusMap.put(statusList.get(i).getStatusCode(), statusList.get(i).getDescription());

                        }
                    }

                    request.setAttribute("priorityLevelList", priorityLevelList);
                    request.setAttribute("statusMap", statusMap);

                    try {

                        cardCategory = request.getParameter("cardcategory").trim();

                    } catch (NullPointerException exception) {
                        cardCategory = "";
                    }

                    this.setUserInputToBean(request);
                    request.setAttribute("searchBean", searchBean);

                    if (this.validateUserInput(searchBean, request)) {


                        //////this.setAudittraceValue(request);

                        this.searchAssignApplication(searchBean, systemAuditBean);

                        // set seached values to the session
                        sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);


                    } else {

                        request.setAttribute("errorMsg", errorMessage);

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
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {

            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);


        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_ASSIGN_APPLICATION);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
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


        String appliactionId = request.getParameter("appliactionid").trim();
        String priorityCode = request.getParameter("prioritycode").trim();
        String fromDate = request.getParameter("fromdate").trim();
        String toDate = request.getParameter("todate").trim();
        String identityNo = request.getParameter("identityno").trim();
        String status = request.getParameter("statuscode").trim();



        searchBean = new SearchDocumentVerifyBean();

        searchBean.setCardCategory(cardCategory);
        searchBean.setApplicationId(appliactionId);
        searchBean.setIdentityNo(identityNo);
        searchBean.setPriorityLevel(priorityCode);
        searchBean.setStatus(status);
        searchBean.setFromDate(fromDate);
        searchBean.setToDate(toDate);


    }

    public boolean validateUserInput(SearchDocumentVerifyBean searchBean, HttpServletRequest request) throws Exception {
        boolean isValid = true;


        //validate user Role code
        try {

            if (!UserInputValidator.isCorrectString(searchBean.getApplicationId())) {
                isValid = false;
                errorMessage = MessageVarList.APPASSIGN_APPLICATIONCODE_INVALID;
            } else if (!UserInputValidator.isCorrectString(searchBean.getIdentityNo())) {
                isValid = false;
                errorMessage = MessageVarList.IDENTIFICATION_INVALID;
            }


        } catch (Exception ex) {
            isValid = false;

        }

        return isValid;
    }

    private void searchAssignApplication(SearchDocumentVerifyBean searchBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            verifyManager = new DocumentVerifyManager();
            searchList = new ArrayList<ApplicationAssignBean>();
            
            searchList = verifyManager.getCorporateCardVerifyList(searchBean, systemAuditBean);
            sessionVarlist.setCorporateVerifyList(searchList);

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

    private void getAllStatus(String categoryCode) throws Exception {
        try {
            systemUserManager = new SystemUserManager();
            statusList = systemUserManager.getStatusByUserRole(categoryCode);

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
