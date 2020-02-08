/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.cardtemplate.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.FeeProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.FeeProfileManager;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.RiskProfileManager;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.TransactionProfileManager;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ServiceCodeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardProductMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.carddomaintemplate.businesslogic.CardDomainMgtManager;
import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.admin.templatemgt.cardtemplate.businesslogic.CardTemplateMgtManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.businesslogic.MerchantCustomerManager;
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
 * @author chanuka
 */
public class LoadCardTemplateMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CardDomainMgtManager cardDomainMgtManager;
    private CardTemplateMgtManager cardTemplateManager;
    private RiskProfileManager riskManager;
    private FeeProfileManager feeManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<StatusBean> statusList;
    private List<RiskProfileBean> riskProfileList = null;
    private List<FeeProfileBean> feeProfileList = null;
    private HashMap<String, String> accountTemplateList = null;
    private List<CardTemplateBean> searchList = null;
    private HashMap<String, String> customerTemplateList = null;
    private List<String> userTaskList;
    private List<CardProductBean> cardProductMgtList = null;
    private CardProductMgtManager cardManager = null;
    private TransactionProfileManager transactionManager = null;
    private List<TransactionProfileBean> transactionProfileList = null;
    private List<ServiceCodeBean> serviceCodeList = null;
    private MerchantCustomerManager mrchCus = null;
    
    private HashMap<String , String> categoryMap;
    private String url = "/administrator/templatemgt/cardtemplate/cardtemplatehome.jsp";

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
                String pageCode = PageVarList.CARDTEMPLATEHOME;
                String taskCode = TaskVarList.ACCESSPAGE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    // is valid acess nothing to do
                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }


                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                throw adex;

            }
            this.getRiskProfile();
            this.getFeeProfile();
            this.getAllStatus("GENR");
            this.searchCardDomainMgt();
            this.getCardProduct();
            this.getTransactionProfile();
            this.getActiveServiceCode();
            this.getCardCategoryList();

            request.setAttribute("searchList", searchList);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);


            rd = getServletContext().getRequestDispatcher(url);



        } //catch session exception
        catch (SesssionExpException sex) {
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
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_CARDTEMPLATE);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void getAllCustomerTemplateList() throws Exception {

        try {

            cardDomainMgtManager = new CardDomainMgtManager();
            customerTemplateList = cardDomainMgtManager.getAllCustomerTemplateList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllAccountTemplateList() throws Exception {

        try {

            cardDomainMgtManager = new CardDomainMgtManager();
            accountTemplateList = cardDomainMgtManager.getAllAccountTemplateList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllStatus(String categoryCode) throws Exception {
        try {
            statusList = systemUserManager.getStatusByUserRole(categoryCode);
            sessionVarlist.setStatusList(statusList);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void searchCardDomainMgt() throws Exception {

        try {


            cardTemplateManager = new CardTemplateMgtManager();
            searchList = cardTemplateManager.getAllCardDomainSearchList();


        } catch (Exception ex) {
            throw ex;

        }
    }

    private void getRiskProfile() throws Exception {

        try {
            riskProfileList = new ArrayList<RiskProfileBean>();
            riskManager = new RiskProfileManager();

            riskProfileList = riskManager.getGivenTypeRiskProfile(StatusVarList.CARD_RISK_PROFILE);
            sessionVarlist.setRiskProfileList(riskProfileList);

        } catch (Exception ex) {
            throw ex;

        }


    }

    private void getFeeProfile() throws Exception {

        try {
            feeProfileList = new ArrayList<FeeProfileBean>();
            mrchCus = new MerchantCustomerManager();

            feeProfileList = mrchCus.getAllFeeProfileDetail(StatusVarList.CARD_FEE_PROFILE);
            sessionVarlist.setFeeProfileList(feeProfileList);

        } catch (Exception ex) {
            throw ex;

        }


    }

    private void getTransactionProfile() throws Exception {

        try {
            transactionProfileList = new ArrayList<TransactionProfileBean>();
            transactionManager = new TransactionProfileManager();

            transactionProfileList = transactionManager.getTransactionProfileDetails();
            sessionVarlist.setTransactionProfileList(transactionProfileList);

        } catch (Exception ex) {
            throw ex;

        }


    }

    private void getActiveServiceCode() throws Exception {

        try {

            serviceCodeList = new ArrayList<ServiceCodeBean>();
            cardTemplateManager = new CardTemplateMgtManager();

            serviceCodeList = cardTemplateManager.getActiveServiceCode();
            sessionVarlist.setServiceCodeList(serviceCodeList);

        } catch (Exception ex) {
            throw ex;

        }
    }

    private void getCardProduct() throws Exception {

        try {
            cardProductMgtList = new ArrayList<CardProductBean>();
            cardManager = new CardProductMgtManager();

            cardProductMgtList = cardManager.getAllCardProductDetailsList();
            //sessionVarlist.setCardProductMgtList(cardProductMgtList);

        } catch (Exception ex) {
            throw ex;

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
    
    
    private void getCardCategoryList() throws Exception {
        try {
            cardManager = new CardProductMgtManager();
            categoryMap = cardManager.getCardCategoryList();
            sessionVarlist.setCardCategoryMap(categoryMap);
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
