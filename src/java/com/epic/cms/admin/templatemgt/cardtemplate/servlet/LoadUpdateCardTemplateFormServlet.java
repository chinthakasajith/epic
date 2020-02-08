/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.cardtemplate.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chanuka
 */
public class LoadUpdateCardTemplateFormServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CardTemplateMgtManager cardTemplateManager;
    private CardDomainMgtManager domainManager = null;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private HashMap<String, String> currencyList = null;
    private HashMap<String, String> productList = null;
    private HashMap<String, String> interestProfileList = null;
    private HashMap<String, String> cardHolderFeeProfileList = null;
    private HashMap<String, String> accountTemplateList = null;
    private HashMap<String, String> customerTemplateList = null;
    private List<CardTemplateBean> searchList = null;
    private CardTemplateBean cardBean = null;
    boolean success = false;
    private List<CardProductBean> cardProductMgtList = null;
    private String url = "/administrator/templatemgt/cardtemplate/cardtemplateupdate.jsp";

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


            String templateCode = request.getParameter("templatecode").trim();



            //this.getAllInterestProfiles();
            //this.getAllCardHolderFeeProfiles();
//            this.getAllProductList();
            //this.getAllCustomerTemplateList();
            this.getAllAccountTemplateList();
            this.searchCardDomainMgt();
            


            
            // request.setAttribute("interestProfileList", interestProfileList);
            //request.setAttribute("cardHolderFeeProfileList", cardHolderFeeProfileList);
            //request.setAttribute("customerTemplateList", customerTemplateList);
            request.setAttribute("accountTemplateList", accountTemplateList);


            for (CardTemplateBean cardTempBean : searchList) {
                if (cardTempBean.getTemplateCode().equals(templateCode)) {
                    cardBean = cardTempBean;
                }
            }
            if (cardBean != null) {

                this.getUpdateCardProductList(cardBean.getProductCode());
                this.setSessionToInputDate(cardBean);
                this.getCurrencyList(cardBean.getProductCode());
                request.setAttribute("cardBean", cardBean);
                request.setAttribute("currencyList", currencyList);
                rd = getServletContext().getRequestDispatcher(url);
            } else {
                rd = getServletContext().getRequestDispatcher(url);
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
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void getCurrencyList(String productCode) throws Exception {

        try {

            cardTemplateManager = new CardTemplateMgtManager();
            currencyList = cardTemplateManager.getCurrencyList(productCode);

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

    private void searchCardDomainMgt() throws Exception {

        try {


            cardTemplateManager = new CardTemplateMgtManager();
            searchList = cardTemplateManager.getAllCardDomainSearchList();


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

    private boolean setSessionToInputDate(CardTemplateBean cardBean) throws Exception {
        boolean successSet = false;
        try {

            sessionVarlist.setCardBean(cardBean);
            successSet = true;
        } catch (Exception ex) {
            throw ex;
        }
        return successSet;
    }

    private void getUpdateCardProductList(String productCode) throws Exception {

        try {
            cardProductMgtList = new ArrayList<CardProductBean>();
            cardTemplateManager = new CardTemplateMgtManager();

            cardProductMgtList = cardTemplateManager.getUpdateCardProductList(productCode);
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
