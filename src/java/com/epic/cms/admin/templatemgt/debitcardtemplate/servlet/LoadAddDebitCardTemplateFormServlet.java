/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.debitcardtemplate.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ServiceCodeBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.accounttemplate.businesslogic.AccountTemplateManager;
import com.epic.cms.admin.templatemgt.carddomaintemplate.businesslogic.CardDomainMgtManager;
import com.epic.cms.admin.templatemgt.cardtemplate.businesslogic.CardTemplateMgtManager;
import com.epic.cms.admin.templatemgt.debitcardtemplate.bean.DebitCardTemplateBean;
import com.epic.cms.admin.templatemgt.debitcardtemplate.businesslogic.DebitCardTemplateMgtManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.businesslogic.BulkCardRequestManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class LoadAddDebitCardTemplateFormServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    DebitCardTemplateBean debitBean = null;
    private BulkCardRequestManager bulkCdReqMgr = null;
    private HashMap<String, String> cardDomain;
    private CardDomainMgtManager domainManager = null;
    private DebitCardTemplateMgtManager debitCardManager;
    private HashMap<String, String> currencyList = null;
    private HashMap<String, String> productList = null;
    private HashMap<String, String> interestProfileList = null;
    private HashMap<String, String> cardHolderFeeProfileList = null;
    private HashMap<String, String> customerTemplateList = null;
    private HashMap<String, String> debitAccountTemplateList = null;
    private String url = "/administrator/templatemgt/debitcardtemplate/debitcardadd.jsp";
    //modifying
    
    private HashMap<String, String> feeProfiles = null;
    private HashMap<String, String> transactionProfiles = null;
    private HashMap<String, String> riskProfiles = null;
    private AccountTemplateManager templateManager;
    private HashMap<String, String> cardType = null;

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

            request.setAttribute("selectedtab", "0");

            this.getAllCustomerTemplateList();
            this.getAllDebitAccountTemplateList();
            this.getAllCurrencyList();
            this.getFeeProfiles();
            this.getTransactionProfiles();
            this.getRiskProfiles();
            this.getAllCardType();
            this.getCardDomains();
            request.setAttribute("cardDomainList", cardDomain);
            

            request.setAttribute("currencyList", currencyList);
            request.setAttribute("feeProfiles", feeProfiles);
            request.setAttribute("riskProfiles", riskProfiles);
            request.setAttribute("cardType", cardType);
            request.setAttribute("transactionProfiles", transactionProfiles);
            request.setAttribute("customerTemplateList", customerTemplateList);
            request.setAttribute("debitAccountTemplateList", debitAccountTemplateList);
            debitBean = new DebitCardTemplateBean();
            debitBean.setCashAdvanceRate("0");
            request.setAttribute("debitBean", debitBean);

            rd = getServletContext().getRequestDispatcher(url);


        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_DEBIT_CARDTEMPLATE);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void getAllDebitAccountTemplateList() throws Exception {

        try {

            debitCardManager = new DebitCardTemplateMgtManager();
            debitAccountTemplateList = debitCardManager.getAllDebitAccountTemplateList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllCustomerTemplateList() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            customerTemplateList = domainManager.getAllCustomerTemplateList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllCurrencyList() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            currencyList = domainManager.getAllCurrencyList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllInterestProfiles() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            interestProfileList = domainManager.getAllInterestProfiles();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllCardHolderFeeProfiles() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            cardHolderFeeProfileList = domainManager.getAllCardHolderFeeProfiles();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getFeeProfiles() throws Exception {

        try {

            debitCardManager = new DebitCardTemplateMgtManager();
            feeProfiles = debitCardManager.getFeeProfiles();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getRiskProfiles() throws Exception {

        try {

            debitCardManager = new DebitCardTemplateMgtManager();
            riskProfiles = debitCardManager.getRiskProfiles();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getTransactionProfiles() throws Exception {

        try {

            debitCardManager = new DebitCardTemplateMgtManager();
            transactionProfiles = debitCardManager.getTransactionProfiles();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllCardType() throws Exception {
        templateManager = new AccountTemplateManager();
        cardType = templateManager.getAllCardType();


    }

    private void getCardDomains() throws Exception {
        try {

            bulkCdReqMgr = new BulkCardRequestManager();
            cardDomain = bulkCdReqMgr.getCardDomains();
        } catch (SQLException ex) {
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
