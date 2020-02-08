/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.cardtemplate.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.carddomaintemplate.businesslogic.CardDomainMgtManager;
import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.admin.templatemgt.cardtemplate.businesslogic.CardTemplateMgtManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
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
public class GetDetailsByAccountTemplateServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CardDomainMgtManager domainManager = null;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private CardTemplateMgtManager cardTemplateManager;
    private CardTemplateBean cardTemplateBean = null;
    private CardTemplateBean cardBean;
    private HashMap<String, String> currencyList = null;
    private HashMap<String, String> productList = null;
    private HashMap<String, String> interestProfileList = null;
    private HashMap<String, String> cardHolderFeeProfileList = null;
    private HashMap<String, String> accountTemplateList = null;
    private HashMap<String, String> customerTemplateList = null;
    private List<CardProductBean> cardProductMgtList = null;
    private String operation;
    private String url1 = "/administrator/templatemgt/cardtemplate/cardtemplateadd.jsp";
    private String url2 = "/administrator/templatemgt/cardtemplate/cardtemplateupdate.jsp";

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

            this.setUserInputToBean(request);



            this.getAllCurrencyList();
            //this.getAllInterestProfiles();
            //this.getAllCardHolderFeeProfiles();
//            this.getAllProductList();
            // this.getAllCustomerTemplateList();
            this.getAllAccountTemplateList();

            request.setAttribute("currencyList", currencyList);
            // request.setAttribute("interestProfileList", interestProfileList);
            //request.setAttribute("cardHolderFeeProfileList", cardHolderFeeProfileList);
            //request.setAttribute("customerTemplateList", customerTemplateList);
            request.setAttribute("accountTemplateList", accountTemplateList);



            operation = request.getParameter("operation");

            if (operation != null) {

                if (operation.equals("add")) {
                    rd = getServletContext().getRequestDispatcher(url1);

                    this.getAllDetailsAccountTemplate(cardBean);
                    this.getCardProductList(cardTemplateBean.getCardType());

                    request.setAttribute("cardBean", cardTemplateBean);
                    //request.setAttribute("cardProductMgtList", cardProductMgtList);

                }
                if (operation.equals("update")) {
                    rd = getServletContext().getRequestDispatcher(url2);

                    this.getAllDetailsAccountTemplate(cardBean);
                    this.getCardProductList(cardTemplateBean.getCardType());

                    request.setAttribute("cardBean", cardTemplateBean);
                    //request.setAttribute("cardProductMgtList", cardProductMgtList);

                }
            }



        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_CARDTEMPLATE);
            if (operation.equals("add")) {
                rd = request.getRequestDispatcher(url1);
            }
            if (operation.equals("update")) {
                rd = request.getRequestDispatcher(url2);
            }


        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void getAllDetailsAccountTemplate(CardTemplateBean cardBean) throws Exception {

        try {


            cardTemplateManager = new CardTemplateMgtManager();
            cardTemplateBean = new CardTemplateBean();
            cardTemplateBean = cardTemplateManager.getAllDetailsAccountTemplate(cardBean);


        } catch (Exception ex) {
            throw ex;

        }
    }

    public void setUserInputToBean(HttpServletRequest request) {


        String accountTemplateCode = request.getParameter("accounttemplatecode");
        String templateCode = request.getParameter("templatecode").trim();
        String templateName = request.getParameter("templatename").trim();
        String status = request.getParameter("status").trim();
        String staffStatus = request.getParameter("staffStatus").trim();
        String productType = request.getParameter("producttype").trim();
        String currencyType = request.getParameter("currencytype").trim();


        cardBean = new CardTemplateBean();

        cardBean.setAccounttemplateCode(accountTemplateCode);
        cardBean.setTemplateCode(templateCode);
        cardBean.setTemplateName(templateName);
        cardBean.setStatus(status);
        cardBean.setStaffStatus(staffStatus);
        cardBean.setProductCode(productType);
        cardBean.setCurrencyCode(currencyType);

        cardBean.setServiceCode(request.getParameter("serviceCode").trim());
        cardBean.setExpiryPeriod(request.getParameter("expiryPeriod").trim());
        cardBean.setRenewRequired(request.getParameter("renewRequired").trim());
        cardBean.setReissuThrshPeriod(request.getParameter("reissuThrshPeriod").trim());
        cardBean.setCashAdvanceRate(request.getParameter("cashAdvanceRate").trim());

        cardBean.setFeeProfCode(request.getParameter("feeProfileSelect").trim());
        cardBean.setRiskProfCode(request.getParameter("riskProfileDelect").trim());
        cardBean.setTxnProfCode(request.getParameter("txnProfileSelect").trim());


    }

    private void getAllCurrencyList() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            currencyList = domainManager.getAllCurrencyList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllProductList() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            productList = domainManager.getAllProductList();
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

    private void getAllCustomerTemplateList() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            customerTemplateList = domainManager.getAllCustomerTemplateList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllAccountTemplateList() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            accountTemplateList = domainManager.getAllAccountTemplateList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardProductList(String cardType) throws Exception {

        try {
            cardProductMgtList = new ArrayList<CardProductBean>();
            cardTemplateManager = new CardTemplateMgtManager();

            cardProductMgtList = cardTemplateManager.getCardProductList(cardType);
            sessionVarlist.setCardProductMgtList(cardProductMgtList);

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
