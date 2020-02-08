/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBinBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.RiskProfileManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
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
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
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
public class UpdateRiskProfileMgtAssignDataServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private RiskProfileManager riskManager = null;
    private String errorMessage = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private boolean successInsert = false;
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
    private RiskProfileBean riskProfBean = null;
    private String[] country;
    private String[] mcc;
    private String[] txn;
    private String[] currency;
    private String[] bin;
    private String url = "/administrator/controlpanel/profilemgt/riskprofileadd.jsp";
    private String oldValue;
    private String newValue;
    private List<RiskProfileBean> riskProfList = null;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
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

            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }
            /////////////////////////////////////////////////////////////////////

            try {
                //set page code and task codes
                String pageCode = PageVarList.RISKPROFILE;
                String taskCode = TaskVarList.UPDATE;

                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                    try {

                        if (setUserInputToBean(request)) {

                            country = request.getParameterValues("assignCountryList");
                            mcc = request.getParameterValues("assignMcclist");
                            txn = request.getParameterValues("assignTxnTypeList");
                            currency = request.getParameterValues("assignCurrencyList");
                            if (riskProfBean.getProfileType().equals(StatusVarList.TERMINAL_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.MERCHANT_RISK_PROFILE)) {
                                bin = request.getParameterValues("assignBinList");
                            }
                            riskManager = new RiskProfileManager();

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

                            if (riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.MERCHANT_RISK_PROFILE)) {

                                assignedCountryList = riskManager.getSelectedCountry(riskProfBean.getRiskProfCode());
                                notAssignedCountryList = riskManager.getNotBlockedCountry(riskProfBean.getRiskProfCode());
                                assignedTypeList = riskManager.getSelectedTxnType(riskProfBean.getRiskProfCode());
                                notAssignedTypeList = riskManager.getNotBlockedTxnType(riskProfBean.getRiskProfCode());
                                assignedMerchantCategoryList = riskManager.getSelectedMcc(riskProfBean.getRiskProfCode());
                                notAssignedMerchantCategoryList = riskManager.getNotBlockedMcc(riskProfBean.getRiskProfCode());
                                assignedCurrencyList = riskManager.getSelectedCurrency(riskProfBean.getRiskProfCode());
                                notAssignedCurrencyList = riskManager.getNotBlockedCurrency(riskProfBean.getRiskProfCode());
                                assignedBinList = riskManager.getSelectedBin(riskProfBean.getRiskProfCode());
                                notAssignedBinList = riskManager.getNotBlockedBin(riskProfBean.getRiskProfCode());

                            } else if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE)) {

                                riskManager = new RiskProfileManager();
                                assignedCountryList = riskManager.getSelectedCountry(riskProfBean.getAccountProfCode());
                                notAssignedCountryList = riskManager.getNotBlockedCountry(riskProfBean.getAccountProfCode());
                                assignedTypeList = riskManager.getSelectedTxnType(riskProfBean.getAccountProfCode());
                                notAssignedTypeList = riskManager.getNotBlockedTxnType(riskProfBean.getAccountProfCode());
                                assignedMerchantCategoryList = riskManager.getSelectedMcc(riskProfBean.getAccountProfCode());
                                notAssignedMerchantCategoryList = riskManager.getNotBlockedMcc(riskProfBean.getAccountProfCode());
                                assignedCurrencyList = riskManager.getSelectedCurrency(riskProfBean.getAccountProfCode());
                                notAssignedCurrencyList = riskManager.getNotBlockedCurrency(riskProfBean.getAccountProfCode());

                            } else if (riskProfBean.getProfileType().equals(StatusVarList.ACCOUNT_RISK_PROFILE)) {

                                riskManager = new RiskProfileManager();
                                assignedCountryList = riskManager.getSelectedCountry(riskProfBean.getCustomerProfCode());
                                notAssignedCountryList = riskManager.getNotBlockedCountry(riskProfBean.getCustomerProfCode());
                                assignedTypeList = riskManager.getSelectedTxnType(riskProfBean.getCustomerProfCode());
                                notAssignedTypeList = riskManager.getNotBlockedTxnType(riskProfBean.getCustomerProfCode());
                                assignedMerchantCategoryList = riskManager.getSelectedMcc(riskProfBean.getCustomerProfCode());
                                notAssignedMerchantCategoryList = riskManager.getNotBlockedMcc(riskProfBean.getCustomerProfCode());
                                assignedCurrencyList = riskManager.getSelectedCurrency(riskProfBean.getCustomerProfCode());
                                notAssignedCurrencyList = riskManager.getNotBlockedCurrency(riskProfBean.getCustomerProfCode());

                            } else if (riskProfBean.getProfileType().equals(StatusVarList.TERMINAL_RISK_PROFILE)) {

                                riskManager = new RiskProfileManager();
                                assignedCountryList = riskManager.getSelectedCountry(riskProfBean.getMerchantProfCode());
                                notAssignedCountryList = riskManager.getNotBlockedCountry(riskProfBean.getMerchantProfCode());
                                assignedTypeList = riskManager.getSelectedTxnType(riskProfBean.getMerchantProfCode());
                                notAssignedTypeList = riskManager.getNotBlockedTxnType(riskProfBean.getMerchantProfCode());
                                assignedMerchantCategoryList = riskManager.getSelectedMcc(riskProfBean.getMerchantProfCode());
                                notAssignedMerchantCategoryList = riskManager.getNotBlockedMcc(riskProfBean.getMerchantProfCode());
                                assignedCurrencyList = riskManager.getSelectedCurrency(riskProfBean.getMerchantProfCode());
                                notAssignedCurrencyList = riskManager.getNotBlockedCurrency(riskProfBean.getMerchantProfCode());
                                assignedBinList = riskManager.getSelectedBin(riskProfBean.getMerchantProfCode());
                                notAssignedBinList = riskManager.getNotBlockedBin(riskProfBean.getMerchantProfCode());

                            }

                            riskManager = new RiskProfileManager();
                            selectedRiskProfList = new ArrayList<RiskProfileBean>();

                            if (!(riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.MERCHANT_RISK_PROFILE) || riskProfBean.getProfileType().equals(""))) {
                                selectedRiskProfList = riskManager.getSelectedProfileTypes(riskProfBean.getProfileType());
                            }


                            request.setAttribute("operationtype", "update");
                            request.setAttribute("selectedtab", "0");
                            this.setAudittraceValue(request);



                            try {

//                                if ((mcc == null) && (txn == null) && (country == null) && (currency == null) && (bin == null)) {

//                                    request.setAttribute("successMsg", riskProfBean.getDescription() + " " + MessageVarList.RISK_PROFILE_SUCCESS_UPDATE);
//                                    rd = getServletContext().getRequestDispatcher("/LoadRiskProfileMgtServlet");

//                                } else {
                                successInsert = updateRiskProfileAssignData(riskProfBean, country, mcc, txn, currency, bin, systemAuditBean);

                                if (successInsert) {
                                    request.setAttribute("successMsg", riskProfBean.getDescription() + " " + MessageVarList.RISK_PROFILE_SUCCESS_UPDATE);
                                    rd = getServletContext().getRequestDispatcher("/LoadRiskProfileMgtServlet");
                                }
//                                }

                            } catch (SQLException e) {
                                String resultMessage = null;
                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());

                                if (oraMessage.equals(message.INTE_CHILD)) {
                                    resultMessage = "You cannot remove previously used Tasks.";
                                    request.setAttribute("errorMsg", resultMessage);
                                } else {
                                    request.setAttribute("errorMsg", oraMessage);
                                }

                                request.setAttribute("operationtype", "update");
                                request.setAttribute("otherRecord", "true");
                                request.setAttribute("riskProfBean", riskProfBean);

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

                                request.setAttribute("selectedtab", "1");
                                rd = getServletContext().getRequestDispatcher(url);

                            }




                        } else {
                            request.setAttribute("operationtype", "update");
                            request.setAttribute("otherRecord", "true");
                            request.setAttribute("selectedtab", "1");
                            request.setAttribute("riskProfBean", riskProfBean);


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
                            request.setAttribute("selectedtab", "1");
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
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }

//                       

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "update");
                        request.setAttribute("otherRecord", "true");
                        request.setAttribute("selectedtab", "1");
                        request.setAttribute("riskProfBean", riskProfBean);


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
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);

                    }

                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }


                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                throw adex;

            }



        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);



        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);


        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);


        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
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

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean isSet = true;
        try {
            riskManager = new RiskProfileManager();
            riskProfBean = new RiskProfileBean();

            riskProfBean.setRiskProfCode(request.getParameter("riskProfCode").trim());
            riskProfBean.setDescription(request.getParameter("description").trim());
            riskProfBean.setStatus(request.getParameter("selectstatuscode").trim());
            riskProfBean.setProfileType(request.getParameter("selectRiskProfType").trim());

            if (!riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE)) {

                if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE)) {

                    riskProfBean.setAccountProfCode(request.getParameter("selectAccountProfType"));
                }
                if (riskProfBean.getProfileType().equals(StatusVarList.ACCOUNT_RISK_PROFILE)) {

                    riskProfBean.setCustomerProfCode(request.getParameter("selectCustomerProfType"));
                }
                if (riskProfBean.getProfileType().equals(StatusVarList.TERMINAL_RISK_PROFILE)) {

                    riskProfBean.setMerchantProfCode(request.getParameter("selectMerchantProfType"));
                }

            }
            riskProfBean.setPeroid(request.getParameter("peroid").trim());
            riskProfBean.setMinSingleTxnLimit(request.getParameter("minimumSingleTxnLimit").trim());
            riskProfBean.setMaxSingleTxnLimit(request.getParameter("maxSingleTxnLimit").trim());
            riskProfBean.setMinSingleCashLimit(request.getParameter("minSingleCashLimit").trim());
            riskProfBean.setMaxSingleCashLimit(request.getParameter("maxSingleCashLimit").trim());
            riskProfBean.setMaxTxnCount(request.getParameter("maxTxnCount").trim());
            riskProfBean.setMaxTotTxnAmount(request.getParameter("maxTotalTxnAmount").trim());
            riskProfBean.setMaxCashCount(request.getParameter("maxCashCount").trim());
            riskProfBean.setMaxTotCashTxnAmount(request.getParameter("maxTotalCashAmount").trim());
            if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.ACCOUNT_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE)) {
                riskProfBean.setExtraAuthLimit(request.getParameter("extraAuthLimit").trim());
            } else {
                riskProfBean.setExtraAuthLimit("");
            }
            if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE)) {

                riskProfBean.setMaxPinCount(request.getParameter("maxPinCount").trim());
            }

            riskProfBean.setLastUpdatedUser(sessionUser.getUserName());

            newValue = riskProfBean.getRiskProfCode() + "|" + riskProfBean.getDescription() + "|" + riskProfBean.getStatus() + "|"
                    + riskProfBean.getProfileType() + "|" + riskProfBean.getMerchantProfCode() + "|" + riskProfBean.getPeroid() + "|"
                    + riskProfBean.getMinSingleTxnLimit() + "|" + riskProfBean.getMinSingleCashLimit() + "|" + riskProfBean.getMaxSingleCashLimit() + "|"
                    + riskProfBean.getMaxSingleTxnLimit() + "|" + riskProfBean.getMaxTxnCount() + "|" + riskProfBean.getMaxTotTxnAmount() + "|"
                    + riskProfBean.getMaxCashCount() + "|" + riskProfBean.getMaxTotCashTxnAmount() + "|" + riskProfBean.getExtraAuthLimit();

            riskProfList = new ArrayList<RiskProfileBean>();
            riskProfList = riskManager.getRiskProfileDetails();
            for (RiskProfileBean newBean : riskProfList) {

                if (newBean.getRiskProfCode().equals(riskProfBean.getRiskProfCode())) {

                    oldValue = newBean.getRiskProfCode() + "|" + newBean.getDescription() + "|" + newBean.getStatus() + "|"
                            + newBean.getProfileType() + "|" + newBean.getMerchantProfCode() + "|" + newBean.getPeroid() + "|"
                            + newBean.getMinSingleTxnLimit() + "|" + newBean.getMinSingleCashLimit() + "|" + newBean.getMaxSingleCashLimit() + "|"
                            + newBean.getMaxSingleTxnLimit() + "|" + newBean.getMaxTxnCount() + "|" + newBean.getMaxTotTxnAmount() + "|"
                            + newBean.getMaxCashCount() + "|" + newBean.getMaxTotCashTxnAmount() + "|" + newBean.getExtraAuthLimit();

                }
            }
        } catch (Exception e) {
            isSet = false;
            throw e;

        }

        return isSet;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(riskProfBean.getRiskProfCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Assign Data to : '" + riskProfBean.getRiskProfCode() + "' Risk Profile by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.PROFILEMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.RISKPROFILE);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(oldValue);
            //set new value of change if required
            systemAuditBean.setNewValue(newValue);

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }

    }

    public boolean updateRiskProfileAssignData(RiskProfileBean riskProfBean, String[] country, String[] mcc, String[] txn, String[] currency, String[] bin, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            riskManager = new RiskProfileManager();
            success = riskManager.updateRiskProfileAssignData(riskProfBean, country, mcc, txn, currency, bin, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
