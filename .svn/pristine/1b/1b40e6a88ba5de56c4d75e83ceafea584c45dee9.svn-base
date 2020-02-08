/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.cardembossing.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.cpmm.cardembossing.bean.BulkCardEmbossingDetailsBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingCardDetailsBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingSearchBean;
import com.epic.cms.cpmm.cardembossing.businesslogic.CardEmbossingMgtManager;
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
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chanuka
 */
public class BulkSearchCardEmbossingServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CardEmbossingMgtManager cardEmbossingManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private CardEmbossingSearchBean searchBean = null;
    private String embossType = null;
    private String errorMessage = null;
    private List<BulkCardEmbossingDetailsBean> bulkCardEmbossingList = null;
//    private List<String> visaCardsToEmbossList = new ArrayList<String>();
    private String url = "/cpmm/cardembossing/bulkembossingsearchhome.jsp";

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
                String pageCode = PageVarList.BULK_EMBOSS;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {

                        embossType = request.getParameter("embosstype");

                    } catch (NullPointerException e) {
                    }

                    this.setUserInputsToBean(request);
                    request.setAttribute("searchembossingbean", searchBean);

                    //insert searched bean to session
                    sessionVarlist.setEmbossSearchBean(searchBean);

                    if (this.validateUserInput(searchBean, request)) {



                        this.getAllBulkCardrequestToEmboss();


                        sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                        request.setAttribute("bulkCardEmbossingList", bulkCardEmbossingList);

                    } else {

                        request.setAttribute("errorMsg", errorMessage);

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

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_EMBOSS_CARD);
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

    private void setUserInputsToBean(HttpServletRequest request) throws Exception {
        try {

            String batchId = request.getParameter("batchid");
            String identityType = request.getParameter("identitytype");
            String identityNo = request.getParameter("identityno");
            String priorityLevel = request.getParameter("prioritycode");
            String cardType = request.getParameter("cardtypecode");
            String cardProduct = request.getParameter("cardproductcode");
            String status = request.getParameter("statuscode");
            String generatedUser = request.getParameter("generateduser");
            String branchId = request.getParameter("branchid");
            String cardDomain = request.getParameter("domainid");


            searchBean = new CardEmbossingSearchBean();

            searchBean.setBatchId(batchId);
            searchBean.setIdentityType(identityType);
            searchBean.setIdentityNo(identityNo);
            searchBean.setPriorityLevel(priorityLevel);
            searchBean.setCardtype(cardType);
            searchBean.setCardProduct(cardProduct);
            searchBean.setStatus(status);
            searchBean.setGeneratedUser(generatedUser);
            searchBean.setEmbossType(embossType);
            searchBean.setBranchId(branchId);
            searchBean.setDomainId(cardDomain);

        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean validateUserInput(CardEmbossingSearchBean embossBean, HttpServletRequest request) throws Exception {
        boolean isValid = true;


        //validate user Role code
        try {

            if (embossBean.getEmbossType() == null || embossBean.getEmbossType().length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.CARD_EMBOSS_TYPE_NULL;
            }
            else if (!UserInputValidator.isCorrectString(searchBean.getBatchId())) {
                isValid = false;
                errorMessage = MessageVarList.BATCHID_INVALID;
            }

        } catch (Exception ex) {
            isValid = false;

        }

        return isValid;
    }

    private void getAllBulkCardrequestToEmboss() throws Exception {
        try {

            cardEmbossingManager = new CardEmbossingMgtManager();
            bulkCardEmbossingList = cardEmbossingManager.getAllBulkCardrequestToEmboss(searchBean);

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
