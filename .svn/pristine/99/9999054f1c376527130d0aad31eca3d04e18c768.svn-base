/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.accounttemplate.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.admin.templatemgt.accounttemplate.businesslogic.AccountTemplateManager;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.admin.templatemgt.customertemplate.businesslogic.CustomerTemplateManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
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
 * @author janaka_h
 */
public class ManageAccountTempalteServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private AccountTemplateManager templateManager;
    private CustomerTemplateManager custemplateManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<CurrencyBean> currencyList;
    private SessionVarList sessionVarlist;
    private List<StatusBean> statusList = null;
    private List<AccountTempBean> templateList = null;
    private List<CustomerTempBean> cusTemplateList = null;
    private HashMap<String, String> productType = null;
    private HashMap<String, String> cardType = null;
    private HashMap<String, String> interestProf = null;
    private HashMap<String, String> billingCycle = null;
    private HashMap<String, String> billStatementProf = null;
    private HashMap<String, String> accountRskProf = null;
    private String oldValue;
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
            //used when redirects from SetAccountRiskProfileServlet
            String operationType = "";
            if (request.getAttribute("isBack") != null) {

                request.setAttribute("riskProfLst", request.getAttribute("riskProfLst"));
                operationType = (String) request.getAttribute("operation");
            }
            operationType = request.getParameter("operation");
            if (request.getAttribute("operation") != null) {
                operationType = (String) request.getAttribute("operation");
            }

            if (operationType.equals("add")) {
                AccountTempBean addBean = new AccountTempBean();
                addBean = (AccountTempBean) request.getAttribute("templateBean");
                //get status list
                this.getStatusList();
                //get currency list
                this.getAllCurrencyList();
                //get card types
                this.getAllCardType();
                //get interest profiles
                this.getAllInterestProf();
                //get billing cycles
                this.getAllBillingCycle();
                //get billing statement profiles
                this.getAllBillStatementProf();

                request.setAttribute("operationtype", "add");
                request.setAttribute("templateBean", addBean); 
                request.setAttribute("currencyList", currencyList);
                request.setAttribute("cardType", cardType);
                request.setAttribute("interestProf", interestProf);
                request.setAttribute("productType", productType);
                request.setAttribute("billList", billingCycle);
                request.setAttribute("statementList", billStatementProf);
                request.setAttribute("statusLst", statusList);

                rd = getServletContext().getRequestDispatcher("/administrator/templatemgt/accounttemplate/accouonttemplatehome.jsp");
                rd.forward(request, response);


            }
            if (operationType.equals("edit")) {

                String templateCode = request.getParameter("templateCode");
                String customerTemplate = request.getParameter("cusTemplate");
                if (request.getAttribute("isBack") == null) {
                    templateManager = new AccountTemplateManager();
                    accountRskProf = templateManager.getAllAccountRskProf(customerTemplate);
                    request.setAttribute("riskProfLst", accountRskProf);
                }
                this.getAllAccTemplateList();
                AccountTempBean editBean = new AccountTempBean();

                editBean = (AccountTempBean) request.getAttribute("templateBean");
                if (editBean == null) {

                    for (int i = 0; i < templateList.size(); i++) {
                        if (templateList.get(i).getTemplateCode().equals(templateCode)) {
                            editBean = templateList.get(i);
                            break;
                        }
                    }
                }

                oldValue = editBean.getTemplateCode() + "|" + editBean.getTemplateName() + "|"
                        + "" + editBean.getValiedFrom() + "|" + editBean.getValiedTo() + "|"
                        + "" + editBean.getTotalCreditLimit() + "|" + editBean.getStaff() + "|"
                        + "" + editBean.getAccRskProf() + "|"  + editBean.getCardType() + "|"
                        + "" + editBean.getCurrencyCode() + "|" + editBean.getCustomerTemplateCode() + "|"
                        + "" + editBean.getBillCycle() + "|"  + editBean.getInterestprofileCode() + "|"
                        + "" + editBean.getStatementProf()  + "|" + editBean.getCardType() + "|"
                        + "" + editBean.getStatus();                  
                
                this.getStatusList();
                this.getAllCurrencyList();
                //this.getAllCusTemplateList();
                templateManager = new AccountTemplateManager();
                cusTemplateList = templateManager.getCustomerTemplates(editBean.getStaff());
                this.getAllProductType();
                this.getAllCardType();
                //this.getAllAccountRskProf();
                this.getAllInterestProf();
                this.getAllBillingCycle();
                this.getAllBillStatementProf();

                request.setAttribute("templateBean", editBean);
                request.setAttribute("statusLst", statusList);
                request.setAttribute("templateList", templateList);
                request.setAttribute("cusTemplateList", cusTemplateList);
                request.setAttribute("currencyList", currencyList);

                request.setAttribute("interestProf", interestProf);
                request.setAttribute("cardType", cardType);
                request.setAttribute("productType", productType);
                request.setAttribute("billList", billingCycle);
                request.setAttribute("statementList", billStatementProf);
                request.setAttribute("operationtype", "edit");
                request.setAttribute("oldValue", oldValue);
                

                rd = getServletContext().getRequestDispatcher("/administrator/templatemgt/accounttemplate/accouonttemplatehome.jsp");
                rd.forward(request, response);

            }





        } catch (SQLException ex) {
//            request.setAttribute("operationtype", "add");
//            errorMessage = OracleMessage.getMessege(ex.getMessage());
//            request.setAttribute("errorMsg", errorMessage);
//            rd = getServletContext().getRequestDispatcher(url);
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_USERROLE);
            //rd = request.getRequestDispatcher(url); 

        } finally {
            out.close();
        }
    }

    //get StatusList
    private List<StatusBean> getStatusList() throws Exception {
        systemUserManager = new SystemUserManager();
        statusList = systemUserManager.getStatusByUserRole("GENR");
        return statusList;
    }

    private List<AccountTempBean> getAllAccTemplateList() throws Exception {
        try {
            templateManager = new AccountTemplateManager();
            templateList = templateManager.getAllTemplateLst();
            return templateList;
        } catch (Exception ex) {
            throw ex;
        }
    }

//    private List<CustomerTempBean> getAllCusTemplateList() throws Exception {
//        custemplateManager = new CustomerTemplateManager();
//        cusTemplateList = custemplateManager.getAllTemplateLst();
//        return cusTemplateList;
//    }
    private List<CurrencyBean> getAllCurrencyList() throws Exception {

        try {

            custemplateManager = new CustomerTemplateManager();
            currencyList = custemplateManager.getAllCurrencyLst();
            return currencyList;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private HashMap<String, String> getAllProductType() throws Exception {
        custemplateManager = new CustomerTemplateManager();
        productType = custemplateManager.getAllProductType();
        return productType;

    }

    private HashMap<String, String> getAllCardType() throws Exception {
        templateManager = new AccountTemplateManager();
        cardType = templateManager.getAllCardType();
        return cardType;

    }

//    private HashMap<String, String> getAllAccountRskProf() throws Exception {
//        templateManager = new AccountTemplateManager();
//        accountRskProf = templateManager.getAllAccountRskProf();
//        return accountRskProf;
//
//    }
    private HashMap<String, String> getAllInterestProf() throws Exception {
        templateManager = new AccountTemplateManager();
        interestProf = templateManager.getAllInterestProf();
        return interestProf;

    }

    private HashMap<String, String> getAllBillingCycle() throws Exception {
        templateManager = new AccountTemplateManager();
        billingCycle = templateManager.getAllBillingCycle();
        return billingCycle;
    }

    private HashMap<String, String> getAllBillStatementProf() throws Exception {
        templateManager = new AccountTemplateManager();
        billStatementProf = templateManager.getAllBillStatementProf();
        return billStatementProf;
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
