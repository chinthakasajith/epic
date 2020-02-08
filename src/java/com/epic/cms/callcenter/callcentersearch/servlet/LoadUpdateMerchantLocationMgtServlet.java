/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.callcentersearch.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.CommissionProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.FeeProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.FeeProfileManager;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.RiskProfileManager;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BillingCycleMgtBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CountryMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CountryMgtManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AreaBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantBankBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantBankBranchBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantCustomerBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantPaymentCycleBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantPaymentModeBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.businesslogic.MerchantCustomerManager;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.bean.MerchantLocationBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.businesslogic.MerchantLocationManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.RequestVarList;
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
 * @author badrika
 */
public class LoadUpdateMerchantLocationMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private List<MerchantLocationBean> beanList = null;
    private List<MerchantCategoryBean> notAssigndnedMerchantCatogoryList = null;
    private List<MerchantCategoryBean> assigndnedMerchantCatogoryList = null;
    private List<TypeMgtBean> notAssigndnedTxnTypeList = null;
    private List<TypeMgtBean> assigndnedTxnTypeList = null;
    private List<CurrencyBean> notAssignCurrencyList = null;
    private List<CurrencyBean> assignCurrencyList = null;
    private List<MerchantCustomerBean> merchantCustomerList = null;
    private List<String> userTaskList;
    private MerchantLocationBean merchantLocBean;
    private String url = "/aquirecallcenter/updatemechantlocation.jsp";
    private HashMap<String, String> currencyTypeList;
    private List<BillingCycleMgtBean> statementList;
    private List<MerchantPaymentCycleBean> paymentList;
    private List<MerchantPaymentModeBean> paymentModeList;
    private HashMap<String, String> accountTypeList;
    private List<CommissionProfileBean> commissionList;

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
            //call to existing session
            /////////////////////////////////////////////////////////////////////
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
            /////////////////////////////////////////////////////////////////////

            try {
                //set page code and task codes
                String pageCode = PageVarList.CALLCENTER_MERCHANT_LOCATION_UPDATE;
                String taskCode = TaskVarList.UPDATE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    MerchantCustomerManager ccm = new MerchantCustomerManager();
                    merchantCustomerList = new ArrayList<MerchantCustomerBean>();
                    //get  MerchantCustomer Name details
                    merchantCustomerList = ccm.getMerchantCustomerDetails();


                    try {
                        //take lists to drop down menus//
                        
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
            FeeProfileManager feeManager = new FeeProfileManager();

            List<FeeProfileBean> feeProfileList = new ArrayList<FeeProfileBean>();

            feeProfileList = feeManager.getAllFeeProfileDetail();
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
                        
                        //end of take lists to drop down menus//
                        
                        
//                        String merchantId = request.getParameter("merchantId");
//                        String merchantCustomerNumber = request.getParameter("merchantCustomerNumber");

                        String merchantId = sessionVarlist.getMerchantId();
                        String merchantCustomerNumber = sessionVarlist.getMerchantCustomerNumber();

                        MerchantLocationManager merchantLocManager = new MerchantLocationManager();
                        merchantLocBean = new MerchantLocationBean();
                        beanList = merchantLocManager.getMerchantLocationDetails();
                        for (MerchantLocationBean newMerchantLocBean : beanList) {

                            if (newMerchantLocBean.getMerchantId().equals(merchantId)) {
                                merchantLocBean = newMerchantLocBean;
                            }
                        }
                        merchantLocManager = new MerchantLocationManager();

                        //////////get Not Assigned Merchannt Category List

                        notAssigndnedMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                        notAssigndnedMerchantCatogoryList = merchantLocManager.getNotAssignedMccList(merchantId, merchantCustomerNumber);

                        ////////////get Assigned Category List

                        assigndnedMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                        assigndnedMerchantCatogoryList = merchantLocManager.getAssignedMccList(merchantId);

                        /////////////get Not Assigned Transaction Type List

                        notAssigndnedTxnTypeList = new ArrayList<TypeMgtBean>();
                        notAssigndnedTxnTypeList = merchantLocManager.getNotAssignedTxnTypeList(merchantId, merchantCustomerNumber);

                        /////////////get Assigned Transaction Type List

                        assigndnedTxnTypeList = new ArrayList<TypeMgtBean>();
                        assigndnedTxnTypeList = merchantLocManager.getAssignedTxnTypeList(merchantId);

                        ////////////get Not Assigned Currency List

                        notAssignCurrencyList = new ArrayList<CurrencyBean>();
                        notAssignCurrencyList = merchantLocManager.getNotAssignedCurrencyList(merchantId, merchantCustomerNumber);

                        ///////////get Assigned Currency List
                        assignCurrencyList = new ArrayList<CurrencyBean>();
                        assignCurrencyList = merchantLocManager.getAssignedCurrencyList(merchantId);

                        if (merchantLocBean != null) {

                            request.setAttribute("operationtype", "update");
                            request.setAttribute("selectedtab", "0");
                            request.setAttribute("merchantLocBean", merchantLocBean);
                            request.setAttribute("notAssigndnedMerchantCatogoryList", notAssigndnedMerchantCatogoryList);
                            request.setAttribute("assigndnedMerchantCatogoryList", assigndnedMerchantCatogoryList);
                            request.setAttribute("notAssigndnedTxnTypeList", notAssigndnedTxnTypeList);
                            request.setAttribute("assigndnedTxnTypeList", assigndnedTxnTypeList);
                            request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                            request.setAttribute("assignCurrencyList", assignCurrencyList);
                            request.setAttribute(RequestVarList.MERCHANTCUSTOMER_LIST, merchantCustomerList);
                            rd = getServletContext().getRequestDispatcher(url);
                        }

                        rd.forward(request, response);

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "search");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher("/LoadMerchantLocationServlet");
                        rd.forward(request, response);
                    }

                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }


                //set tasks to the session
//                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                throw adex;

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

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {
        } finally {
            out.close();
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
