/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBinBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.RiskProfileManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CountryMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
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
public class LoadUpdateRiskProfileMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private RiskProfileManager riskManager = null;
    private List<RiskProfileBean> riskProfList = null;
    private RiskProfileBean riskProfBean = null;
    private RiskProfileBean riskBean = null;
//    private List<MerchantCategoryBean> notAssignedMccList = null;
//    private List<MerchantCategoryBean> assigndnedMerchantCatogoryList = null;
//    private List<TypeMgtBean> notAssigndnedTxnTypeList = null;
//    private List<TypeMgtBean> assigndnedTxnTypeList = null;
    private String errorMessage = null;
    private List<String> userTaskList = null;
    private List<RiskProfileBean> selectedRiskProfList = null;
    private List<CountryMgtBean> assignedCountryList = null;
    private List<TypeMgtBean> assignedTypeList = null;
    private List<MerchantCategoryBean> assignedMerchantCategoryList = null;
    private List<CurrencyBean> assignedCurrencyList = null;
    private List<RiskProfileBinBean> assignedBinList = null;
    private List<CountryMgtBean> notAssignedCountryList = null;
    private List<TypeMgtBean> notAssignedTypeList = null;
    private List<MerchantCategoryBean> notAssignedMerchantCategoryList = null;
    private List<CurrencyBean> notAssignedCurrencyList = null;
    private List<RiskProfileBinBean> notAssignedBinList = null;
    private String url = "/administrator/controlpanel/profilemgt/riskprofileadd.jsp";

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
                String pageCode = PageVarList.RISKPROFILE;
                String taskCode = TaskVarList.UPDATE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        String riskProfCode = request.getParameter("riskProfCode");
                        riskManager = new RiskProfileManager();
                        riskProfBean = new RiskProfileBean();
                        riskProfList = new ArrayList<RiskProfileBean>();
                        riskProfList = riskManager.getRiskProfileDetails();
                        for (RiskProfileBean newBean : riskProfList) {

                            if (newBean.getRiskProfCode().equals(riskProfCode)) {
                                riskProfBean = newBean;
                            }
                        }

                        this.setOldUpdateValue(riskProfBean);
                        riskManager = new RiskProfileManager();
                        riskBean = new RiskProfileBean();

                        assignedCountryList = new ArrayList<CountryMgtBean>();
                        notAssignedCountryList = new ArrayList<CountryMgtBean>();
                        assignedTypeList = new ArrayList<TypeMgtBean>();
                        notAssignedTypeList = new ArrayList<TypeMgtBean>();
                        assignedMerchantCategoryList = new ArrayList<MerchantCategoryBean>();
                        notAssignedMerchantCategoryList = new ArrayList<MerchantCategoryBean>();
                        assignedCurrencyList = new ArrayList<CurrencyBean>();
                        notAssignedCurrencyList = new ArrayList<CurrencyBean>();
                        assignedBinList = new ArrayList<RiskProfileBinBean>();
                        notAssignedBinList = new ArrayList<RiskProfileBinBean>();

                        //get the blocked list according to account/card/merchant profile type 

                        if (riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.MERCHANT_RISK_PROFILE)) {

                            assignedCountryList = riskManager.getSelectedCountry(riskProfCode);
                            notAssignedCountryList = riskManager.getNotBlockedCountry(riskProfCode);
                            assignedTypeList = riskManager.getSelectedTxnType(riskProfCode);
                            notAssignedTypeList = riskManager.getNotBlockedTxnType(riskProfCode);
                            assignedMerchantCategoryList = riskManager.getSelectedMcc(riskProfCode);
                            notAssignedMerchantCategoryList = riskManager.getNotBlockedMcc(riskProfCode);
                            assignedCurrencyList = riskManager.getSelectedCurrency(riskProfCode);
                            notAssignedCurrencyList = riskManager.getNotBlockedCurrency(riskProfCode);
                            assignedBinList = riskManager.getSelectedBin(riskProfCode);
                            notAssignedBinList = riskManager.getNotBlockedBin(riskProfCode);

                        } else if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE)) {

                            riskManager = new RiskProfileManager();
                            assignedCountryList = riskManager.getSelectedCountry(riskProfCode);
                            notAssignedCountryList = riskManager.getNotBlockedCountry(riskProfCode);
                            assignedTypeList = riskManager.getSelectedTxnType(riskProfCode);
                            notAssignedTypeList = riskManager.getNotBlockedTxnType(riskProfCode);
                            assignedMerchantCategoryList = riskManager.getSelectedMcc(riskProfCode);
                            notAssignedMerchantCategoryList = riskManager.getNotBlockedMcc(riskProfCode);
                            assignedCurrencyList = riskManager.getSelectedCurrency(riskProfCode);
                            notAssignedCurrencyList = riskManager.getNotBlockedCurrency(riskProfCode);
                            riskBean.setSelectedRiskProfCode(riskProfBean.getAccountProfCode());
                            riskBean = riskManager.getSelectedProfileData(riskBean);

                        }
                        if (riskProfBean.getProfileType().equals(StatusVarList.ACCOUNT_RISK_PROFILE)) {

                            riskManager = new RiskProfileManager();
                            assignedCountryList = riskManager.getSelectedCountry(riskProfCode);
                            notAssignedCountryList = riskManager.getNotBlockedCountry(riskProfCode);
                            assignedTypeList = riskManager.getSelectedTxnType(riskProfCode);
                            notAssignedTypeList = riskManager.getNotBlockedTxnType(riskProfCode);
                            assignedMerchantCategoryList = riskManager.getSelectedMcc(riskProfCode);
                            notAssignedMerchantCategoryList = riskManager.getNotBlockedMcc(riskProfCode);
                            assignedCurrencyList = riskManager.getSelectedCurrency(riskProfCode);
                            notAssignedCurrencyList = riskManager.getNotBlockedCurrency(riskProfCode);
                            riskBean.setSelectedRiskProfCode(riskProfBean.getCustomerProfCode());
                            riskBean = riskManager.getSelectedProfileData(riskBean);

                        }
                        if (riskProfBean.getProfileType().equals(StatusVarList.TERMINAL_RISK_PROFILE)) {

                            riskManager = new RiskProfileManager();
                            assignedCountryList = riskManager.getSelectedCountry(riskProfCode);
                            notAssignedCountryList = riskManager.getNotBlockedCountry(riskProfCode);
                            assignedTypeList = riskManager.getSelectedTxnType(riskProfCode);
                            notAssignedTypeList = riskManager.getNotBlockedTxnType(riskProfCode);
                            assignedMerchantCategoryList = riskManager.getSelectedMcc(riskProfCode);
                            notAssignedMerchantCategoryList = riskManager.getNotBlockedMcc(riskProfCode);
                            assignedCurrencyList = riskManager.getSelectedCurrency(riskProfCode);
                            notAssignedCurrencyList = riskManager.getNotBlockedCurrency(riskProfCode);
                            riskBean.setSelectedRiskProfCode(riskProfBean.getMerchantProfCode());
                            assignedBinList = riskManager.getSelectedBin(riskProfCode);
                            notAssignedBinList = riskManager.getNotBlockedBin(riskProfCode);
                            riskBean = riskManager.getSelectedProfileData(riskBean);

                        }
                        riskManager = new RiskProfileManager();
                        selectedRiskProfList = new ArrayList<RiskProfileBean>();

                        if (!(riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.MERCHANT_RISK_PROFILE) || riskProfBean.getProfileType().equals(""))) {
                            selectedRiskProfList = riskManager.getSelectedProfileTypes(riskProfBean.getProfileType());
                        }


                        if (riskProfBean != null) {

                            request.setAttribute("operationtype", "update");


                            if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE)) {

                                request.setAttribute("profileType", "card");

                            }
                            if (riskProfBean.getProfileType().equals(StatusVarList.ACCOUNT_RISK_PROFILE)) {

                                request.setAttribute("profileType", "account");

                            }
                            if (riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE)) {

                                request.setAttribute("profileType", "customer");

                            }
                            if (riskProfBean.getProfileType().equals(StatusVarList.TERMINAL_RISK_PROFILE)) {

                                request.setAttribute("profileType", "terminal");

                            }
                            if (riskProfBean.getProfileType().equals(StatusVarList.TERMINAL_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.MERCHANT_RISK_PROFILE)) {

                                request.setAttribute("bin", "bin");

                            }
                            request.setAttribute("otherRecord", "true");
                            request.setAttribute("selectedtab", "0");
                            request.setAttribute("assignedCountryList", assignedCountryList);
                            request.setAttribute("assignedTypeList", assignedTypeList);
                            request.setAttribute("assignedMerchantCategoryList", assignedMerchantCategoryList);
                            request.setAttribute("assignedCurrencyList", assignedCurrencyList);
                            request.setAttribute("assignedBinList", assignedBinList);
                            request.setAttribute("notAssignedCountryList", notAssignedCountryList);
                            request.setAttribute("notAssignedTypeList", notAssignedTypeList);
                            request.setAttribute("notAssignedMerchantCategoryList", notAssignedMerchantCategoryList);
                            request.setAttribute("notAssignedCurrencyList", notAssignedCurrencyList);
                            request.setAttribute("notAssignedBinList", notAssignedBinList);
                            request.setAttribute("selectedRiskProfList", selectedRiskProfList);
                            request.setAttribute("riskProfBean", riskProfBean);
                            request.setAttribute("riskBean", riskBean);
                            request.setAttribute("selectedtab", "0");
                            rd = request.getRequestDispatcher(url);
                        }

                        rd.forward(request, response);
                    } catch (Exception e) {
                        //request.setAttribute("operationtype", "search");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher("/LoadRiskProfileMgtServlet");
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
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);

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

    private void setOldUpdateValue(RiskProfileBean oldBean) throws Exception {
        String oldValue = null;

        String maxPinCount = null;
        String cusProfCode = null;
        String accProfCode = null;
        String mercProfCode = null;
        try {

            if (oldBean.getMaxPinCount() != null) {

                maxPinCount = oldBean.getMaxPinCount();
            } else {
                maxPinCount = "-";
            }


            if (oldBean.getCustomerProfCode() != null) {

                cusProfCode = oldBean.getCustomerProfCode();
            } else {
                cusProfCode = "-";
            }


            if (oldBean.getAccountProfCode() != null) {

                accProfCode = oldBean.getAccountProfCode();
            } else {
                accProfCode = "-";
            }


            if (oldBean.getMerchantProfCode() != null) {

                mercProfCode = oldBean.getMerchantProfCode();
            } else {
                mercProfCode = "-";
            }

            oldValue = oldBean.getRiskProfCode() + "|" + oldBean.getDescription() + "|" + oldBean.getStatus() + "|"
                    + cusProfCode + "|" + accProfCode + "|" + mercProfCode + "|"
                    + oldBean.getPeroid() + "|" + oldBean.getMinSingleTxnLimit() + "|" + oldBean.getMaxSingleTxnLimit() + "|"
                    + oldBean.getMinSingleCashLimit() + "|" + oldBean.getMaxSingleCashLimit() + "|" + oldBean.getMaxTxnCount() + "|"
                    + oldBean.getMaxCashCount() + "|" + oldBean.getMaxTotTxnAmount() + "|" + oldBean.getMaxTotCashTxnAmount() + "|"
                    + oldBean.getExtraAuthLimit() + "|" + maxPinCount;

            riskProfBean.setOldValue(oldValue);
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
