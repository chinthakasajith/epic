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
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CountryMgtManager;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CurrencyMgtManager;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.MerchantCategoryManager;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.TxnTypeMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
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
public class AddRiskProfileMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private String errorMessage = null;
    private String successInsert = null;
    private RiskProfileManager riskManager = null;
    private SystemAuditBean systemAuditBean = null;
    private String[] mcc = null;
    private String[] txn = null;
    private String[] country = null;
    private String[] currency = null;
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
    private RiskProfileBean checkDataBean = null;
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
                String taskCode = TaskVarList.ADD;

                ///////////////////////////////////////////////////////////////////

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    country = request.getParameterValues("assignCountryList");
                    mcc = request.getParameterValues("assignMcclist");
                    txn = request.getParameterValues("assignTxnTypeList");
                    currency = request.getParameterValues("assignCurrencyList");

                    try {
                        setUserInputToBean(request);
                        String profileType = riskProfBean.getProfileType();

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

                        checkDataBean = new RiskProfileBean();

                        if (profileType.equals(StatusVarList.CUSTOMER_RISK_PROFILE) || profileType.equals(StatusVarList.MERCHANT_RISK_PROFILE)) {

                            //////////////// get country list//////////////////////////////////////////
                            CountryMgtManager comgtObj = new CountryMgtManager();
                            notAssignedCountryList = comgtObj.getAllCountryInfo();

                            ///////////////////////get transaction Type List////////////////////////
                            TxnTypeMgtManager txnObj = new TxnTypeMgtManager();
                            notAssignedTypeList = txnObj.getTxnTypeDetails();

                            /////////////////////get merchant category List//////////////////////////////
                            MerchantCategoryManager mcmObj = new MerchantCategoryManager();
                            notAssignedMerchantCategoryList = mcmObj.getAllMerchntCatgryDetails();

                            //////////////////////get Currency List//////////////////////////////////
                            CurrencyMgtManager curmgtObj = new CurrencyMgtManager();
                            notAssignedCurrencyList = curmgtObj.getCurrencyDetails();

                            /////////////////////////////////////////////////////////////////////////
                            RiskProfileManager riskObj = new RiskProfileManager();
                            notAssignedBinList = riskObj.getAllBin();

                            /////////////////////////////////////////////////////////////////////////

                        } //get the blocked list according to account/card/merchant profile type 
                        else if (profileType.equals(StatusVarList.CARD_RISK_PROFILE)) {

                            riskManager = new RiskProfileManager();
                            assignedCountryList = riskManager.getSelectedCountry(riskProfBean.getAccountProfCode());
                            notAssignedCountryList = riskManager.getNotBlockedCountry(riskProfBean.getAccountProfCode());
                            assignedTypeList = riskManager.getSelectedTxnType(riskProfBean.getAccountProfCode());
                            notAssignedTypeList = riskManager.getNotBlockedTxnType(riskProfBean.getAccountProfCode());
                            assignedMerchantCategoryList = riskManager.getSelectedMcc(riskProfBean.getAccountProfCode());
                            notAssignedMerchantCategoryList = riskManager.getNotBlockedMcc(riskProfBean.getAccountProfCode());
                            assignedCurrencyList = riskManager.getSelectedCurrency(riskProfBean.getAccountProfCode());
                            notAssignedCurrencyList = riskManager.getNotBlockedCurrency(riskProfBean.getAccountProfCode());

                            checkDataBean.setSelectedRiskProfCode(riskProfBean.getAccountProfCode());

                        } else if (profileType.equals(StatusVarList.ACCOUNT_RISK_PROFILE)) {

                            riskManager = new RiskProfileManager();
                            assignedCountryList = riskManager.getSelectedCountry(riskProfBean.getCustomerProfCode());
                            notAssignedCountryList = riskManager.getNotBlockedCountry(riskProfBean.getCustomerProfCode());
                            assignedTypeList = riskManager.getSelectedTxnType(riskProfBean.getCustomerProfCode());
                            notAssignedTypeList = riskManager.getNotBlockedTxnType(riskProfBean.getCustomerProfCode());
                            assignedMerchantCategoryList = riskManager.getSelectedMcc(riskProfBean.getCustomerProfCode());
                            notAssignedMerchantCategoryList = riskManager.getNotBlockedMcc(riskProfBean.getCustomerProfCode());
                            assignedCurrencyList = riskManager.getSelectedCurrency(riskProfBean.getCustomerProfCode());
                            notAssignedCurrencyList = riskManager.getNotBlockedCurrency(riskProfBean.getCustomerProfCode());

                            checkDataBean.setSelectedRiskProfCode(riskProfBean.getCustomerProfCode());

                        } else if (profileType.equals(StatusVarList.TERMINAL_RISK_PROFILE)) {

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

                            checkDataBean.setSelectedRiskProfCode(riskProfBean.getMerchantProfCode());

                        }



                        riskManager = new RiskProfileManager();
                        selectedRiskProfList = new ArrayList<RiskProfileBean>();
                        if (!(profileType.equals(StatusVarList.CUSTOMER_RISK_PROFILE) || profileType.equals(StatusVarList.MERCHANT_RISK_PROFILE))) {

                            selectedRiskProfList = riskManager.getSelectedProfileTypes(profileType);

                        }

                        if (validateUserInput(riskProfBean)) {
                            request.setAttribute("operationtype", "add");
                            request.setAttribute("selectedtab", "0");

                            ///////////////////////////////////////////////////////

                            this.setAudittraceValue(request);

                            try {
                                // if (profileType.equals("RPCRD") || profileType.equals("RPACT") || profileType.equals("RPTER")) {}

                                successInsert = insertRiskProfile(riskProfBean, systemAuditBean);


                                if (successInsert.equals("add")) {

                                    request.setAttribute("successMsg", riskProfBean.getRiskProfCode() + " " + MessageVarList.RISK_PROFILE_SUCCESS_ADD);

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
                                    request.setAttribute("riskProfBean", riskProfBean);
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
                                    request.setAttribute("readOnly", "true");
                                    rd = getServletContext().getRequestDispatcher(url);
                                    rd.forward(request, response);

                                }

                                if (successInsert.equals("update")) {

                                    request.setAttribute("successMsg", riskProfBean.getRiskProfCode() + " " + MessageVarList.RISK_PROFILE_SUCCESS_UPDATE);

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
                                    request.setAttribute("riskProfBean", riskProfBean);
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
                                    request.setAttribute("readOnly", "true");
                                    rd = getServletContext().getRequestDispatcher(url);
                                    rd.forward(request, response);

                                }

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("operationtype", "add");

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
                                request.setAttribute("errorMsg", oraMessage);
                                request.setAttribute("riskProfBean", riskProfBean);
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
                                request.setAttribute("selectedtab", "0");
                                request.setAttribute("disableTabIndex", "1");
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);


                            }

                        } else {

                            request.setAttribute("operationtype", "add");
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
                            request.setAttribute("riskProfBean", riskProfBean);
                            request.setAttribute("errorMsg", errorMessage);
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
                            request.setAttribute("selectedtab", "0");
                            request.setAttribute("disableTabIndex", "1");
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);

                        }

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "add");
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
                        request.setAttribute("riskProfBean", riskProfBean);
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
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
                        request.setAttribute("selectedtab", "0");
                        request.setAttribute("disableTabIndex", "1");
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
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

        } catch (Exception ex) {
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            out.close();
        }
    }

    /**
     * 
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean isSet = true;
        try {

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
            }else{
            riskProfBean.setExtraAuthLimit("");
            }
            if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE)) {

                riskProfBean.setMaxPinCount(request.getParameter("maxPinCount").trim());
            }

            riskProfBean.setLastUpdatedUser(sessionUser.getUserName());

        } catch (Exception e) {
            isSet = false;
            throw e;

        }

        return isSet;
    }

//    public boolean insertToMcc(String riskProfCode, String[] assignMerchantCategorylist) throws Exception {
//        boolean success = false;
//        try {
//            riskManager = new RiskProfileManager();
//            success = riskManager.insertToMcc(riskProfCode, assignMerchantCategorylist);
//        } catch (Exception ex) {
//            throw ex;
//        }
//        return success;
//    }
//
//    public boolean insertToTxnType(String riskProfCode, String[] assignTxnTypeList) throws Exception {
//        boolean success = false;
//        try {
//            riskManager = new RiskProfileManager();
//            success = riskManager.insertToTxnType(riskProfCode, assignTxnTypeList);
//        } catch (Exception ex) {
//            throw ex;
//        }
//        return success;
//    }
//
//    public boolean insertToCountry(String riskProfCode, String[] assignCountryList) throws Exception {
//        boolean success = false;
//        try {
//            riskManager = new RiskProfileManager();
//            success = riskManager.insertToCountry(riskProfCode, assignCountryList);
//        } catch (Exception ex) {
//            throw ex;
//        }
//        return success;
//    }
//
//    public boolean insertToCurrency(String riskProfCode, String[] assignCurrencyList) throws Exception {
//        boolean success = false;
//        try {
//            riskManager = new RiskProfileManager();
//            success = riskManager.insertToCurrency(riskProfCode, assignCurrencyList);
//        } catch (Exception ex) {
//            throw ex;
//        }
//        return success;
//    }
    /**
     * Validate the New User Entry
     * @param txnTypeBean
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(RiskProfileBean riskProfBean) throws Exception {
        boolean isValidate = true;

        //////validate user inputs

        try {

            if (riskProfBean.getRiskProfCode().contentEquals("") || riskProfBean.getRiskProfCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.RISK_PROFILE_CODE_NULL;

            } else if (!UserInputValidator.isAlphaNumeric(riskProfBean.getRiskProfCode())) {
                isValidate = false;

                errorMessage = MessageVarList.RISK_PROFILE_CODE_INVALID;

            } else if (riskProfBean.getDescription().contentEquals("") || riskProfBean.getDescription().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.DESCRIPTION_NULL;

            } else if (!UserInputValidator.isDescription(riskProfBean.getDescription())) {
                isValidate = false;

                errorMessage = MessageVarList.DESCRIPTION_INVALID;

            } else if (riskProfBean.getStatus().contentEquals("") || riskProfBean.getStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.STATUS_NULL;

            } else if (riskProfBean.getProfileType().contentEquals("") || riskProfBean.getProfileType().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.PROFILE_TYPE_NULL;

            } else if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE) && (riskProfBean.getAccountProfCode().contentEquals("") || riskProfBean.getAccountProfCode().length() <= 0)) {
                isValidate = false;

                errorMessage = MessageVarList.ACCOUNT_PROFILE_CODE_NULL;

            } else if (riskProfBean.getProfileType().equals(StatusVarList.ACCOUNT_RISK_PROFILE) && (riskProfBean.getCustomerProfCode().contentEquals("") || riskProfBean.getCustomerProfCode().length() <= 0)) {
                isValidate = false;

                errorMessage = MessageVarList.CUSTOMER_PROFILE_CODE_NULL;

            } else if (riskProfBean.getProfileType().equals(StatusVarList.TERMINAL_RISK_PROFILE) && (riskProfBean.getMerchantProfCode().contentEquals("") || riskProfBean.getMerchantProfCode().length() <= 0)) {
                isValidate = false;

                errorMessage = MessageVarList.MERCHANT_PROFILE_CODE_NULL;

            } else if (riskProfBean.getPeroid().contentEquals("") || riskProfBean.getPeroid().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.PERIOD_NULL;

            } else if (!UserInputValidator.isNumeric(riskProfBean.getPeroid())) {
                isValidate = false;

                errorMessage = MessageVarList.PERIOD_INVALID;

            } else if (riskProfBean.getMinSingleTxnLimit().contentEquals("") || riskProfBean.getMinSingleTxnLimit().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MINIMUM_SINGLE_TXN_LIMIT_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(riskProfBean.getMinSingleTxnLimit(), "7", "2")) {
                isValidate = false;

                errorMessage = MessageVarList.MINIMUM_SINGLE_TXN_LIMIT_INVALID;

            } else if (riskProfBean.getMaxSingleTxnLimit().contentEquals("") || riskProfBean.getMaxSingleTxnLimit().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_SINGLE_TXN_LIMIT_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(riskProfBean.getMaxSingleTxnLimit(), "7", "2")) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_SINGLE_TXN_LIMIT_INVALID;

            } else if (Double.parseDouble(riskProfBean.getMinSingleTxnLimit()) > Double.parseDouble(riskProfBean.getMaxSingleTxnLimit())) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_SING_TXN_LIMIT_LESS_MIN_SING_TXN_LIMIT;

            } else if (riskProfBean.getMinSingleCashLimit().contentEquals("") || riskProfBean.getMinSingleCashLimit().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MINIMUM_SINGLE_CASH_LIMIT_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(riskProfBean.getMinSingleCashLimit(), "7", "2")) {
                isValidate = false;

                errorMessage = MessageVarList.MINIMUM_SINGLE_CASH_LIMIT_INVALID;

            } else if (riskProfBean.getMaxSingleCashLimit().contentEquals("") || riskProfBean.getMaxSingleCashLimit().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_SINGLE_CASH_LIMIT_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(riskProfBean.getMaxSingleCashLimit(), "7", "2")) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_SINGLE_CASH_LIMIT_INVALID;

            } else if (Double.parseDouble(riskProfBean.getMinSingleCashLimit()) > Double.parseDouble(riskProfBean.getMaxSingleCashLimit())) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_SING_CASH_LIMIT_LESS_MIN_SING_CASH_LIMIT;

//            } else if (riskProfBean.getMaxTxnCount().contentEquals("") || riskProfBean.getMaxTxnCount().length() <= 0) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.MAX_TXN_COUNT_NULL;
//
//            } else if (!UserInputValidator.isNumeric(riskProfBean.getMaxTxnCount())) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.MAX_TXN_COUNT_INVALID;
//
//            } else if (riskProfBean.getMaxCashCount().contentEquals("") || riskProfBean.getMaxCashCount().length() <= 0) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.MAX_CASH_COUNT_NULL;
//
//            } else if (!UserInputValidator.isNumeric(riskProfBean.getMaxCashCount())) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.MAX_CASH_COUNT_INVALID;
//
//            } else if (riskProfBean.getMaxTotTxnAmount().contentEquals("") || riskProfBean.getMaxTotTxnAmount().length() <= 0) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.MAX_TOT_TXN_AMOUNT_NULL;
//
//            } else if (!UserInputValidator.isDecimalOrNumeric(riskProfBean.getMaxTotTxnAmount(), "7", "2")) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.MAX_TOT_TXN_AMOUNT_INVALID;
//
//            } else if (riskProfBean.getMaxTotCashTxnAmount().contentEquals("") || riskProfBean.getMaxTotCashTxnAmount().length() <= 0) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.MAX_TOT_CASH_TXN_AMOUNT_NULL;
//
//            } else if (!UserInputValidator.isDecimalOrNumeric(riskProfBean.getMaxTotCashTxnAmount(), "7", "2")) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.MAX_TOT_CASH_TXN_AMOUNT_INVALID;

            } else if ((riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.ACCOUNT_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE)) && (riskProfBean.getExtraAuthLimit().contentEquals("") || riskProfBean.getExtraAuthLimit().length() <= 0)) {
                isValidate = false;

                errorMessage = MessageVarList.EXTRA_AUTH_LIMIT_NULL;

            } else if ((riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.ACCOUNT_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE)) && (!UserInputValidator.isDecimalOrNumeric(riskProfBean.getExtraAuthLimit(), "3", "2") || (Double.parseDouble(riskProfBean.getExtraAuthLimit()) > 100))) {
                isValidate = false;

                errorMessage = MessageVarList.EXTRA_AUTH_LIMIT_INVALID;

            } else if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE) && (riskProfBean.getMaxPinCount().contentEquals("") || riskProfBean.getMaxPinCount().length() <= 0)) {

                isValidate = false;

                errorMessage = MessageVarList.MAX_PIN_COUNT_NULL;

            } else if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE) && (!UserInputValidator.isNumeric(riskProfBean.getMaxPinCount()))) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_PIN_COUNT_INVALID;

            } else if ((!riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE)) && (!riskProfBean.getProfileType().equals(StatusVarList.MERCHANT_RISK_PROFILE)) && !(this.validateAmoutAndCount(riskProfBean))) {

                isValidate = false;

            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public boolean validateAmoutAndCount(RiskProfileBean riskProfBean) throws Exception {
        boolean isValid = true;
        try {
            riskManager.getSelectedProfileData(checkDataBean);

            if (Integer.parseInt(checkDataBean.getPeroid()) < Integer.parseInt(riskProfBean.getPeroid())) {

                isValid = false;

                errorMessage = MessageVarList.PERIOD_LESS_THAN + checkDataBean.getPeroid();

            } else if (Double.parseDouble(checkDataBean.getMinSingleTxnLimit()) > Double.parseDouble(riskProfBean.getMinSingleTxnLimit())) {

                isValid = false;

                errorMessage = MessageVarList.MIN_SINGLE_TXN_LIMIT_GREATER_THAN + checkDataBean.getMinSingleTxnLimit();

            } else if (Double.parseDouble(checkDataBean.getMaxSingleTxnLimit()) < Double.parseDouble(riskProfBean.getMaxSingleTxnLimit())) {

                isValid = false;

                errorMessage = MessageVarList.MAX_SINGLE_TXN_LIMIT_LESS_THAN + checkDataBean.getMaxSingleTxnLimit();

            } else if (Double.parseDouble(checkDataBean.getMinSingleCashLimit()) > Double.parseDouble(riskProfBean.getMinSingleCashLimit())) {

                isValid = false;

                errorMessage = MessageVarList.MIN_SINGLE_CASH_LIMIT_GREATER_THAN + checkDataBean.getMinSingleCashLimit();

            } else if (Double.parseDouble(checkDataBean.getMaxSingleCashLimit()) < Double.parseDouble(riskProfBean.getMaxSingleCashLimit())) {

                isValid = false;

                errorMessage = MessageVarList.MAX_SINGLE_CASH_LIMIT_LESS_THAN + checkDataBean.getMaxSingleCashLimit();

            } else if (Integer.parseInt(checkDataBean.getMaxTxnCount()) < Integer.parseInt(riskProfBean.getMaxTxnCount())) {

                isValid = false;

                errorMessage = MessageVarList.MAX_TXN_COUNT_LESS_THAN + checkDataBean.getMaxTxnCount();

            } else if (Double.parseDouble(checkDataBean.getMaxCashCount()) < Double.parseDouble(riskProfBean.getMaxCashCount())) {

                isValid = false;

                errorMessage = MessageVarList.MAX_CASH_COUNT_LESS_THAN + checkDataBean.getMaxCashCount();

            } else if (Double.parseDouble(checkDataBean.getMaxTotTxnAmount()) < Double.parseDouble(riskProfBean.getMaxTotTxnAmount())) {

                isValid = false;

                errorMessage = MessageVarList.TOT_MAX_TXN_AMOUNT_LESS_THAN + checkDataBean.getMaxTotTxnAmount();

            } else if (Double.parseDouble(checkDataBean.getMaxTotCashTxnAmount()) < Double.parseDouble(riskProfBean.getMaxTotCashTxnAmount())) {

                isValid = false;

                errorMessage = MessageVarList.TOT_MAX_CASH_AMOUNT_LESS_THAN + checkDataBean.getMaxTotCashTxnAmount();

            } else if ((riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.ACCOUNT_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE)) && Double.parseDouble(checkDataBean.getExtraAuthLimit()) < Double.parseDouble(riskProfBean.getExtraAuthLimit())) {

                isValid = false;

                errorMessage = MessageVarList.EXTRA_AUTH_LIMIT_LESS_THAN + checkDataBean.getExtraAuthLimit();

            }


        } catch (Exception ex) {
            throw ex;
        }


        return isValid;
    }

    /**
     * 
     * @param type
     * @return
     * @throws Exception 
     */
    public String insertRiskProfile(RiskProfileBean riskProfBean, SystemAuditBean systemAuditBean) throws Exception {
        String success;
        try {

            /// call insertTxntypeDetails method in TxnTypeMgtManager class

            riskManager = new RiskProfileManager();
            success = riskManager.insertRiskProfile(riskProfBean, systemAuditBean);

        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }

    /**
     * 
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = riskProfBean.getRiskProfCode();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Risk Profile Risk Profile Code :" + riskProfBean.getRiskProfCode() + "; by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.PROFILEMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.RISKPROFILE);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue("");


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
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
