/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.customertemplate.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.TransactionProfileManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.TemplateBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.TemplateCatagoryBean;
import com.epic.cms.admin.templatemgt.customertemplate.businesslogic.CustomerTemplateManager;
import com.epic.cms.admin.templatemgt.customertemplate.businesslogic.TemplateManager;
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
 * @author janaka_h
 */
public class ManageTemplateMgtServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CustomerTemplateManager templateManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<CurrencyBean> currencyList;
    private SessionVarList sessionVarlist;
    private List<StatusBean> statusList = null;
    private TransactionProfileManager transactionManager = null;
    private List<TransactionProfileBean> transactionProfileList = null;
    private List<TemplateCatagoryBean> catagoryList = null;
    private List<CustomerTempBean> templateList = null;
    private HashMap<String, String> productType = null;
    private HashMap<String, String> cusRskProf = null;

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

            String operationType = request.getParameter("operation");
            if (request.getAttribute("operation") != null) {
                operationType = (String) request.getAttribute("operation");
            }

            if (operationType.equals("add")) {
                CustomerTempBean addBean = new CustomerTempBean();
                addBean = (CustomerTempBean) request.getAttribute("templateBean");
                //get status list
                this.getStatusList();
                this.getAllCurrencyList();
                this.getAllProductType();
                this.getAllCustomerRskProf();   
                //this.getTransactionProfile();

                request.setAttribute("templateBean", addBean);
                request.setAttribute("operationtype", "add");
                request.setAttribute("productType", productType);
                request.setAttribute("riskProfLst", cusRskProf);
                request.setAttribute("currencyList", currencyList);
                request.setAttribute("statusLst", statusList);

                rd = getServletContext().getRequestDispatcher("/administrator/templatemgt/customertemplate/templatemanagementhome.jsp");
                rd.forward(request, response);


            }
            if (operationType.equals("edit")) {

                String templateCode = request.getParameter("templateCode");
                this.getAllTemplateList();
                CustomerTempBean editBean = new CustomerTempBean();

                editBean = (CustomerTempBean) request.getAttribute("templateBean");
                if (editBean == null) {

                    for (int i = 0; i < templateList.size(); i++) {
                        if (templateList.get(i).getTemplateCode().equals(templateCode)) {
                            editBean = templateList.get(i);
                            break;
                        }
                    }
                }

                this.getStatusList();
                this.getAllCurrencyList();
                this.getAllProductType();
                this.getAllCustomerRskProf();
                //load the tx profiles and set to session
                

                request.setAttribute("templateBean", editBean);
                request.setAttribute("statusLst", statusList);
                request.setAttribute("templateList", templateList);
                request.setAttribute("currencyList", currencyList);
                request.setAttribute("productType", productType);
                request.setAttribute("riskProfLst", cusRskProf);
                request.setAttribute("operationtype", "edit");
                rd = getServletContext().getRequestDispatcher("/administrator/templatemgt/customertemplate/templatemanagementhome.jsp");
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

    private List<CurrencyBean> getAllCurrencyList() throws Exception {

        try {

            templateManager = new CustomerTemplateManager();
            currencyList = templateManager.getAllCurrencyLst();
            return currencyList;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private HashMap<String, String> getAllProductType() throws Exception {
        templateManager = new CustomerTemplateManager();
        productType = templateManager.getAllProductType();
        return productType;

    }

    private List<CustomerTempBean> getAllTemplateList() throws Exception {
        templateManager = new CustomerTemplateManager();
        templateList = templateManager.getAllTemplateLst();
        return templateList;
    }
    
    
    private HashMap<String, String> getAllCustomerRskProf() throws Exception {
        templateManager = new CustomerTemplateManager();
        cusRskProf = templateManager.getAllCustomerRskProf();
        return cusRskProf;

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
