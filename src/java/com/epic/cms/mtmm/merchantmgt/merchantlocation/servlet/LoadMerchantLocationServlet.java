/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantlocation.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.CommissionProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.FeeProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.FeeProfileManager;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.RiskProfileManager;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BillingCycleMgtBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CountryMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CountryMgtManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AreaBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantBankBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantBankBranchBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantPaymentCycleBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantPaymentModeBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.businesslogic.MerchantCustomerManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
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
 * @author nalin
 */
public class LoadMerchantLocationServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private List<CommissionProfileBean> commissionList = null;
    private List<BillingCycleMgtBean> statementList = null;
    private List<MerchantPaymentCycleBean> paymentList = null;
    private List<MerchantPaymentModeBean> paymentModeList = null;
    private HashMap<String, String> accountTypeList = null;
    private HashMap<String, String> currencyTypeList = null;
    private String url = "/mtmm/merchantmgt/merchantlocationhome.jsp";

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
            try {
                HttpSession sessionObj = request.getSession(false);
                request.setAttribute("operationtype", "search");



                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }

                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();

                }

                try {
                    //set page code and task codes
                    String pageCode = PageVarList.MERCHANT_LOCATION;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    } else {
                        throw new AccessDeniedException();

                    }

                    sessionVarlist.setUserPageTaskList(userTaskList);

                } catch (AccessDeniedException adex) {
                    throw adex;

                }



            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            ///////////////////////////////////////////////

            SystemUserManager sysUserObj = new SystemUserManager();

            ///// get Status list
            List<StatusBean> statusList = sysUserObj.getStatusByUserRole(StatusVarList.GENESTATUSCAT);
            sessionVarlist.setStatusList(statusList);

            //////////////////////////////////////////////////////////

            List<StatusBean> commonStatusList = sysUserObj.getStatusByUserRole(StatusVarList.COMMONSTATUSCAT);
            sessionVarlist.setCommonStatusList(commonStatusList);

            //////////////////////////////////////////////////////////
            CaptureDataManager capdatObj = new CaptureDataManager();

            ///// get area list
            List<AreaBean> areaList = capdatObj.getAllArealist();
            sessionVarlist.setAreaList(areaList);

            /////////////////////////////////////////////////////////////
            CountryMgtManager comgtObj = new CountryMgtManager();

            ///// get country list
            List<CountryMgtBean> countryCodeList = comgtObj.getAllCountryInfo();
            sessionVarlist.setCountryCodeList(countryCodeList);

            ///////////////////////////////////////////////////////////

//            PostalCodeManager postalCodeObj = new PostalCodeManager();
//
//            ///// get postal code list
//            List<PostalCodeBean> postalCodeDetailList = postalCodeObj.getPostalCodeDetails();
//            sessionVarlist.setPostalCodeDetailList(postalCodeDetailList);

            /////////////////////////////////////////////////////////////
            MerchantCustomerManager mrchCus = new MerchantCustomerManager();

            List<FeeProfileBean> feeProfileList = new ArrayList<FeeProfileBean>();

            feeProfileList = mrchCus.getAllFeeProfileDetail(StatusVarList.MERCHANT_FEE_PROFILE);
            sessionVarlist.setFeeProfileList(feeProfileList);
            ///////////////////////////////////////////////////////////////
            RiskProfileManager riskObj = new RiskProfileManager();

            List<RiskProfileBean> riskProfileList = new ArrayList<RiskProfileBean>();
            riskProfileList = riskObj.getGivenTypeRiskProfile(StatusVarList.MERCHANT_RISK_PROFILE);
            sessionVarlist.setRiskProfileList(riskProfileList);

            ///////////////////////////////////////////////////////////
            MerchantCustomerManager mcm = new MerchantCustomerManager();
            commissionList = new ArrayList<CommissionProfileBean>();

            commissionList = mcm.getCommissionProfileDetails();
            sessionVarlist.setCommissionList(commissionList);
            ///////////////////////////////////////////////////////////

            ///// get bank list
            List<MerchantBankBean> merchantBankList = mcm.getAllBankLst();
            sessionVarlist.setMerchantBankList(merchantBankList);

            /////////////////////////////////////////////////////////////

            ///// get bank Branch list
            List<MerchantBankBranchBean> merchantBankBranchList = mcm.getAllBranchList();
            sessionVarlist.setMerchantBankBranchList(merchantBankBranchList);

            ///////////////////////////////////////////////////////
            statementList = new ArrayList<BillingCycleMgtBean>();

            statementList = mcm.getStatementCycle();
            sessionVarlist.setStatementList(statementList);
            ///////////////////////////////////////////////////
            paymentList = new ArrayList<MerchantPaymentCycleBean>();

            paymentList = mcm.getPaymentCycle();
            sessionVarlist.setPaymentList(paymentList);

            /////////////////////////////////////////////////////

            paymentModeList = new ArrayList<MerchantPaymentModeBean>();

            paymentModeList = mcm.getPaymentMode();
            sessionVarlist.setPaymentModeList(paymentModeList);

            ////////////////////////////////////////////////////

            accountTypeList = new HashMap<String, String>();

            accountTypeList = mcm.getAllAccountType();
            sessionVarlist.setAccountTypeList(accountTypeList);

            /////////////////////////////////////////////////////////////
            
             currencyTypeList = new HashMap<String, String>();
             
             currencyTypeList = mcm.getCurrency();
             sessionVarlist.setCurrencyTypeList(currencyTypeList);
            
            /////////////////////////////////////////

            rd = request.getRequestDispatcher(url);

            /////////////////////////////////////////////////
        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);

        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.ACCESS_DENIED_TASK);


        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
        }
    }

    /**
     * 
     * @param userrole
     * @param pagecode
     * @param task
     * @return
     * @throws Exception 
     */
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
