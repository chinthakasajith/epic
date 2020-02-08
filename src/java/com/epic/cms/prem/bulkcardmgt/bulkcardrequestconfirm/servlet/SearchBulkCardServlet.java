/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardrequestconfirm.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardProductMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.businesslogic.BulkCardRequestManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequestconfirm.businesslogic.BulkCardConfirmManager;
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
 * @author badrika
 */
public class SearchBulkCardServlet extends HttpServlet {

    private HttpSession sessionObj;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private RequestDispatcher rd;
    private String url = "/prem/bulkcardmgt/bulkcardrequestsearch.jsp";
    private BulkCardRequestBean searchBean;
    private CardProductMgtManager cardProductMgtManager;
    private HashMap<String, String> cardTypeList;
    private Object errorMessage;
    private BulkCardRequestManager bulkReqManager;
    private HashMap<String, String> branchList;
    private ApplicationAssignManager appAssignManager;
    private HashMap<String, String> priorityLevelList;
    private List<String> userTaskList;
    private BulkCardConfirmManager bulkConfirmManager;
    private List<BulkCardRequestBean> searchList;

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

            this.getAllCardTypeList();
            this.getBranchNames();
            this.getAllPriorityLevelList();
            request.setAttribute("cardTypeList", cardTypeList);
            request.setAttribute("branchList", branchList); //branchList
            request.setAttribute("priorityLevelList", priorityLevelList);

            try {
                //set page code and task codes
                String pageCode = PageVarList.BULK_CD_CONFIRM;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    if (request.getParameter("back").equals("no")) {
                        this.setUserInputToBean(request);
                    }
                    if (request.getParameter("back").equals("yes")) {
                        searchBean = sessionVarlist.getBulkcardBean();
                    }
                    request.setAttribute("searchBean", searchBean);
                    sessionVarlist.setBulkcardBean(searchBean);

                    if (this.validateUserInput(searchBean, request)) {

                        bulkConfirmManager = new BulkCardConfirmManager();
                        searchList = bulkConfirmManager.searchBulkCard(searchBean);
                        request.setAttribute("searchList", searchList);

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
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);


        } catch (Exception ex) {

            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_ASSIGN_APPLICATION);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void getAllCardTypeList() throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            cardTypeList = cardProductMgtManager.getAllCardTypeList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getBranchNames() throws Exception {
        try {
            bulkReqManager = new BulkCardRequestManager();
            branchList = bulkReqManager.getBranchNames();
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

        searchBean = new BulkCardRequestBean();

        searchBean.setBatchID(request.getParameter("batchID").trim());
        searchBean.setCdDomain(request.getParameter("cardDomain"));
        searchBean.setCdType(request.getParameter("cardType"));
        searchBean.setCdProduct(request.getParameter("cardProduct"));
        searchBean.setBranchCode(request.getParameter("branchName"));
        searchBean.setPriorityLvl(request.getParameter("prioLevel"));
        searchBean.setProductMode(request.getParameter("proMode"));
        searchBean.setFromDate(request.getParameter("fromdate"));
        searchBean.setToDate(request.getParameter("todate"));


    }

    public boolean validateUserInput(BulkCardRequestBean schBean, HttpServletRequest request) throws Exception {
        boolean isValid = true;

        //validate user Role code
        try {
            if (!schBean.getBatchID().equals("") && !UserInputValidator.isCorrectString(schBean.getBatchID())) {
                isValid = false;
                errorMessage = MessageVarList.BULK_BATCH_ID_VALID;//"Enter a Valid Batch ID";
            }
        } catch (Exception ex) {
            isValid = false;
        }
        return isValid;
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
