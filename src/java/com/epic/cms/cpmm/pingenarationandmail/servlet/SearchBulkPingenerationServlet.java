/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.pingenarationandmail.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ProductionModeBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingCardDetailsBean;
import com.epic.cms.cpmm.cardembossing.businesslogic.CardEmbossingMgtManager;
import com.epic.cms.cpmm.pingenarationandmail.been.DomainBean;
import com.epic.cms.cpmm.pingenarationandmail.been.SearchBulkPingenBean;
import com.epic.cms.cpmm.pingenarationandmail.busneslogic.PinGenerationAndmailManager;
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
 * @author mahesh_m
 */
public class SearchBulkPingenerationServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private PinGenerationAndmailManager pinAndmailManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private SearchBulkPingenBean searchBean = null;
    private String errorMessage = null;
//    private List<SearchBulkPingenBean> searchList = null;
    private List<SearchBulkPingenBean> bulkSearchList = null;
    private List<CardEmbossingCardDetailsBean> pingMailList = null;
    private List<String> visaCardsToEmbossList = new ArrayList<String>();
    private List<String> masterCardsToEmbossList = new ArrayList<String>();
    private HashMap<String, String> cardTypeList = null;
    private List<ProductionModeBean> productionModeList = null;
    private List<CardProductBean> bulkCProductList = null;
    private HashMap<String, String> identityTypeList = null;
    private List<String> usersList = null;
    private List<StatusBean> statusList;
    private HashMap<String, String> branchesDeatilsList;
    private ApplicationAssignManager appAssignManager;
    private List<DomainBean> bulkCardDomainList = null;
    private CardEmbossingMgtManager cardEmbossingManager;
    private String url = "/cpmm/pingenarateandmail/bulkpingeneration.jsp";

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
                String pageCode = PageVarList.BULK_PIN_GENERATION;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {



                    this.setUserInputsToBean(request);

                    if (validateInputs(searchBean)) {
                        this.getSearchList();

                        request.setAttribute("searchList", bulkSearchList);
                    } else {
                        request.setAttribute("errorMsg", errorMessage);
                    }

                    this.getAllUserList();
                    this.getAllCardType();
                    this.getProductionModeList();
                    this.getBulkCardProductList();
                    this.getAllBranchesDetailsList();
                    this.getBulkCardDomains();

                    request.setAttribute("productionModeList", productionModeList);
                    request.setAttribute("usersList", usersList);
                    request.setAttribute("cardTypeList", cardTypeList);
                    request.setAttribute("bulkCProductList", bulkCProductList);
                    request.setAttribute("branchesDeatilsList", branchesDeatilsList);
                    request.setAttribute("bulkCardDomainList", bulkCardDomainList);
                    request.setAttribute("SearchBulkPingenBean", searchBean);



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

            String batchId = request.getParameter("batchId");
            String cardDomain = request.getParameter("cardDomain");
            String pMode = request.getParameter("productmode");
            String cType = request.getParameter("cardType");
            String cProduct = request.getParameter("cardProduct");
            String genUser = request.getParameter("generateduser");
            String branch = request.getParameter("branch");

            searchBean = new SearchBulkPingenBean();

            searchBean.setBatchId(batchId);
            searchBean.setCardDomain(cardDomain);
            searchBean.setProductionMode(pMode);
            searchBean.setCardType(cType);
            searchBean.setCardProduct(cProduct);
            searchBean.setGenUser(genUser);
            searchBean.setBranchId(branch);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean validateInputs(SearchBulkPingenBean bulkPin) throws Exception {
        boolean isValidate = true;
        try {

            if (!UserInputValidator.isAlphaNumeric(bulkPin.getBatchId())) {
                isValidate = false;

                errorMessage = MessageVarList.BULK_BATCHID_INVALIS;

            } else if (!UserInputValidator.isAllowNumericNonSpecialString(bulkPin.getBatchId())) {
                isValidate = false;

                errorMessage = MessageVarList.BULK_BATCHID_INVALIS;
            }
//            pinAndmailManager = new PinGenerationAndmailManager();
//            cardEmbossingVISAList = pinAndmailManager.getAllPinGenerationList(searchBean,DataTypeVarList.CARD_DOMAIN_CREDIT);

        } catch (Exception ex) {
            throw ex;
        }
        return isValidate;
    }

    private void getSearchList() throws Exception {

        try {

            pinAndmailManager = new PinGenerationAndmailManager();
            bulkSearchList = pinAndmailManager.getBulkSearchList(searchBean);

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void getAllUserList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            usersList = appAssignManager.getAllUserList(PageVarList.BULK_PIN_GENERATION);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllCardType() throws Exception {
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            cardTypeList = cardEmbossingManager.getAllCardType();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getProductionModeList() throws Exception {
        productionModeList = new ArrayList<ProductionModeBean>();
        try {
            ApplicationCheckingManager checkingmanager = new ApplicationCheckingManager();
            productionModeList = checkingmanager.getProductionMode();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getBulkCardProductList() throws Exception {
        bulkCProductList = new ArrayList<CardProductBean>();
        try {
            PinGenerationAndmailManager pinGenManager = new PinGenerationAndmailManager();
            bulkCProductList = pinGenManager.getBulkCardProductList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllBranchesDetailsList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            branchesDeatilsList = appAssignManager.getAllBranchesDetails();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getBulkCardDomains() throws Exception {

        bulkCardDomainList = new ArrayList<DomainBean>();

        try {
            PinGenerationAndmailManager pinGenManager = new PinGenerationAndmailManager();
            bulkCardDomainList = pinGenManager.getBulkCardDomains();

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
