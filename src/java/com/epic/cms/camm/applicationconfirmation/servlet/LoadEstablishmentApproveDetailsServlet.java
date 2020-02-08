/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ProductionModeBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.camm.applicationconfirmation.bean.RecommendSchemBean;
import com.epic.cms.camm.applicationconfirmation.bean.RejectReasonBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
 * @author prageeth_s
 */
public class LoadEstablishmentApproveDetailsServlet extends HttpServlet {

private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private ApplicationCheckingManager checkingmanager;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private CardApplicationBean cardApplicationList;
    private List<String> userTaskList;
    private List<RecommendSchemBean> cardTypes = null;
    private List<RejectReasonBean> rejectReasons=null;
    private List<ProductionModeBean> productionModeListt=null;
    private List<CardProductBean> cardProducts=null;
    private String baseCurrency;
    private List<CustomerTempBean> staffCusList=null;   
    private EstablishmentDetailsBean establishmentDetailsBean = null;

    
    
    private String url = "/camm/applicationconfirmation/establishmentapplicationapproveview.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            //call to existing session
            ////////////////////////////////////////////////////////////////////////////////
            HttpSession sessionObj = request.getSession(false);
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

                try {
                    //set page code and task codes
                    String pageCode = PageVarList.APPLICATONAPPROVE;
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

            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }
            /////////////////////////////////////////////////////////////////////
            request.setAttribute("selectedtab", "0");
            try {
                SystemRecomendedCredit systemRecomended = new SystemRecomendedCredit();
                String applicationId = request.getParameter("applicationid");

                request.setAttribute("selectedtab", "0");

                if (applicationId == null) {
                    applicationId = request.getAttribute("applicationid").toString();
                }
                this.getCardApplicationDetails(applicationId);
                this.getAllDetailsEstablishment(applicationId);
                this.getRejectReasonList();
                this.getProductionMode();
                this.getBaseCurrency();
                this.getAllCardProductsInCreditCardDomain();
                
                

                List<RecommendSchemBean> cardProductList = new ArrayList<RecommendSchemBean>();

                RecommendSchemBean sysRecomended =  new RecommendSchemBean();
                if (cardApplicationList.getCreditScore() != null) {
                    checkingmanager = new ApplicationCheckingManager();
                    int creditLimitTemp = systemRecomended.getSystemRecomendedCreditLimit(Integer.parseInt(cardApplicationList.getCreditScore()), establishmentDetailsBean.getCardType());
                    Double creditLimit;
                    String currencyAlphaCode = checkingmanager.getCurrencyAlphaCode(establishmentDetailsBean.getCardCurrency());

                    request.setAttribute("currencyAlphaCode", currencyAlphaCode);
                    if (baseCurrency.equals(establishmentDetailsBean.getCardCurrency())) {
                        creditLimit = Double.parseDouble(Integer.toString(creditLimitTemp));
                    } else {

                        String sellingRate = checkingmanager.getSellingRate(establishmentDetailsBean.getCardCurrency());

//                        creditLimit = creditLimitTemp/Double.parseDouble(sellingRate);
                        creditLimit = Double.parseDouble(new DecimalFormat("#.##").format(creditLimitTemp / Double.parseDouble(sellingRate)));
                    }

//                    MaskFormatter format = new MaskFormatter("#,##,###");
//                    format.setValueContainsLiteralCharacters(false);
//                    String creditLimitFomatted = format.valueToString(creditLimit);
////                    System.out.println(format.valueToString(creditLimit));


                    DecimalFormat df = new DecimalFormat();
                    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                    dfs.setGroupingSeparator(',');

                    df.setDecimalFormatSymbols(dfs);
                    String creditLimitFomatted = df.format((double) creditLimit);


                    request.setAttribute("creditLimit", Math.round(creditLimit));
                    request.setAttribute("creditLimitFomatted", creditLimitFomatted);

                    cardProductList = systemRecomended.getRecommendedCardTypes(establishmentDetailsBean.getCardType(), Double.toString(creditLimitTemp));
                    request.setAttribute("cardProductList", cardProductList);
                    request.setAttribute("listsize", cardProductList.size());
                }

                this.getStaffCusTemplates(cardApplicationList.getStaffStatus(), establishmentDetailsBean.getCardCurrency());

                DecimalFormat df = new DecimalFormat();
                DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                dfs.setGroupingSeparator(',');

                df.setDecimalFormatSymbols(dfs);
                String requestedCreditLimit = df.format((double) Double.parseDouble(establishmentDetailsBean.getCreditLimit()));


                request.setAttribute("cardApplicationList", cardApplicationList);
                request.setAttribute("rejectReasons", rejectReasons);
                request.setAttribute("productionModeListt", productionModeListt);
                request.setAttribute("staffCusList", staffCusList);
                request.setAttribute("requestedCreditLimit", requestedCreditLimit);
                request.setAttribute("establishmentDetailsBean", establishmentDetailsBean);
                request.setAttribute("cardProducts", cardProducts);


                rd = request.getRequestDispatcher(url);
                rd.forward(request, response);


            } catch (Exception e) {
                request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            }

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

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            out.close();
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



    private void getCardApplicationDetails(String applicationId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            cardApplicationList = checkingmanager.getCardInfomationDetails(applicationId);

            if (cardApplicationList.getApplicationId() == null) {
                cardApplicationList.setApplicationId("-");
            }

            if (cardApplicationList.getAssignStatus() == null) {
                cardApplicationList.setAssignStatus("-");
            }

            if (cardApplicationList.getAssignUser() == null) {
                cardApplicationList.setAssignUser("-");
            }

            if (cardApplicationList.getBranchCode() == null) {
                cardApplicationList.setBranchCode("-");
            }

            if (cardApplicationList.getCreditScore() == null) {
                cardApplicationList.setCreditScore("-");
            }

            if (cardApplicationList.getIdentificationNumber() == null) {
                cardApplicationList.setIdentificationNumber("-");
            }

            if (cardApplicationList.getIdentificationType() == null) {
                cardApplicationList.setIdentificationType("-");
            }

            if (cardApplicationList.getReferencialEmpNum() == null) {
                cardApplicationList.setReferencialEmpNum("-");
            }
            if(cardApplicationList.getcOfficerRecCardProduct() == null){
                cardApplicationList.setcOfficerRecCardProduct("-");
            }
            if(cardApplicationList.getcOfficerRecCrditLimt()==null){
                cardApplicationList.setcOfficerRecCrditLimt("-");
            }

        } catch (Exception e) {
            throw e;
        }
    }

    private void getRejectReasonList() throws Exception {
        rejectReasons = new ArrayList<RejectReasonBean>();
        try {
            checkingmanager = new ApplicationCheckingManager();
            rejectReasons = checkingmanager.getRejectReasons();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getProductionMode() throws Exception {
        productionModeListt = new ArrayList<ProductionModeBean>();
        try {
            checkingmanager = new ApplicationCheckingManager();
            productionModeListt = checkingmanager.getProductionMode();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getBaseCurrency() throws Exception {

        try {
            checkingmanager = new ApplicationCheckingManager();
            baseCurrency = checkingmanager.getBaseCurrency();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getStaffCusTemplates(String staffStatus, String currency) throws Exception {
        staffCusList = new ArrayList<CustomerTempBean>();
        try {
            checkingmanager = new ApplicationCheckingManager();
            staffCusList = checkingmanager.getStaffCusTemplates(staffStatus, currency);
        } catch (Exception e) {
            throw e;
        }
    }
    
    
    private void getAllDetailsEstablishment(String appliactionId) throws Exception {
        try {
            CaptureDataManager captureDataManager = new CaptureDataManager();
            establishmentDetailsBean = captureDataManager.getAllDetailsEstablishment(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private void getAllCardProductsInCreditCardDomain() throws Exception{
        try {
            checkingmanager=new ApplicationCheckingManager();
            cardProducts=checkingmanager.getCardProductsToRequestedCardType();
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
