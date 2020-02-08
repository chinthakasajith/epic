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
public class UpdateRiskProfileMgtServlet extends HttpServlet {

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
    private RiskProfileBean riskBean = null;
    private String[] country;
    private String[] mcc;
    private String[] txn;
    private String[] currency;
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
                                riskBean.setSelectedRiskProfCode(riskProfBean.getAccountProfCode());
                                riskBean = riskManager.getSelectedProfileData(riskBean);

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
                                riskBean.setSelectedRiskProfCode(riskProfBean.getCustomerProfCode());
                                riskBean = riskManager.getSelectedProfileData(riskBean);

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
                                riskBean.setSelectedRiskProfCode(riskProfBean.getMerchantProfCode());
                                riskBean = riskManager.getSelectedProfileData(riskBean);

                            }

                            riskManager = new RiskProfileManager();
                            selectedRiskProfList = new ArrayList<RiskProfileBean>();

                            if (!(riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.MERCHANT_RISK_PROFILE) || riskProfBean.getProfileType().equals(""))) {
                                selectedRiskProfList = riskManager.getSelectedProfileTypes(riskProfBean.getProfileType());
                            }

                            if (validateUserInput(riskProfBean)) {
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("selectedtab", "0");
                                this.setAudittraceValue(request);



                                try {
                                    successInsert = updateRiskProfile(riskProfBean, systemAuditBean);
                                    if (successInsert) {
                                        request.setAttribute("successMsg", riskProfBean.getDescription() + " " + MessageVarList.RISK_PROFILE_SUCCESS_UPDATE);
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
                                    request.setAttribute("riskBean", riskBean);

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
                                    request.setAttribute("selectedtab", "0");
                                    rd = getServletContext().getRequestDispatcher(url);

                                }



                            } else {
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("otherRecord", "true");

                                request.setAttribute("riskProfBean", riskProfBean);
                                request.setAttribute("riskBean", riskBean);

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
                                request.setAttribute("errorMsg", errorMessage);
                                request.setAttribute("selectedtab", "0");
                                rd = getServletContext().getRequestDispatcher(url);
                            }
                        } else {
                            request.setAttribute("operationtype", "update");
                            request.setAttribute("otherRecord", "true");
                            request.setAttribute("riskProfBean", riskProfBean);
                            request.setAttribute("riskBean", riskBean);


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
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }

//                       

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "update");
                        request.setAttribute("otherRecord", "true");
                        request.setAttribute("selectedtab", "0");
                        request.setAttribute("riskProfBean", riskProfBean);
                        request.setAttribute("riskBean", riskBean);


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
            } else {
                riskProfBean.setExtraAuthLimit("");
            }
            if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE)) {

                riskProfBean.setMaxPinCount(request.getParameter("maxPinCount").trim());
            }
            riskProfBean.setOldValue(request.getParameter("oldValue").trim());
            riskProfBean.setLastUpdatedUser(sessionUser.getUserName());

        } catch (Exception e) {
            isSet = false;
            throw e;

        }

        return isSet;
    }

    /**
     * 
     * @param txnType
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(RiskProfileBean riskProfBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code

        try {

            if (riskProfBean.getDescription().contentEquals("") || riskProfBean.getDescription().length() <= 0) {
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

            } else if (riskProfBean.getMaxTxnCount().contentEquals("") || riskProfBean.getMaxTxnCount().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_TXN_COUNT_NULL;

            } else if (!UserInputValidator.isNumeric(riskProfBean.getMaxTxnCount())) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_TXN_COUNT_INVALID;

            } else if (riskProfBean.getMaxCashCount().contentEquals("") || riskProfBean.getMaxCashCount().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_CASH_COUNT_NULL;

            } else if (!UserInputValidator.isNumeric(riskProfBean.getMaxCashCount())) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_CASH_COUNT_INVALID;

            } else if (riskProfBean.getMaxTotTxnAmount().contentEquals("") || riskProfBean.getMaxTotTxnAmount().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_TOT_TXN_AMOUNT_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(riskProfBean.getMaxTotTxnAmount(), "7", "2")) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_TOT_TXN_AMOUNT_INVALID;



            } else if (riskProfBean.getMaxTotCashTxnAmount().contentEquals("") || riskProfBean.getMaxTotCashTxnAmount().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_TOT_CASH_TXN_AMOUNT_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(riskProfBean.getMaxTotCashTxnAmount(), "7", "2")) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_TOT_CASH_TXN_AMOUNT_INVALID;

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


            if (Integer.parseInt(riskBean.getPeroid()) < Integer.parseInt(riskProfBean.getPeroid())) {

                isValid = false;

                errorMessage = MessageVarList.PERIOD_LESS_THAN + riskBean.getPeroid();

            } else if (Double.parseDouble(riskBean.getMinSingleTxnLimit()) > Double.parseDouble(riskProfBean.getMinSingleTxnLimit())) {

                isValid = false;

                errorMessage = MessageVarList.MIN_SINGLE_TXN_LIMIT_GREATER_THAN + riskBean.getMinSingleTxnLimit();

            } else if (Double.parseDouble(riskBean.getMaxSingleTxnLimit()) < Double.parseDouble(riskProfBean.getMaxSingleTxnLimit())) {

                isValid = false;

                errorMessage = MessageVarList.MAX_SINGLE_TXN_LIMIT_LESS_THAN + riskBean.getMaxSingleTxnLimit();

            } else if (Double.parseDouble(riskBean.getMinSingleCashLimit()) > Double.parseDouble(riskProfBean.getMinSingleCashLimit())) {

                isValid = false;

                errorMessage = MessageVarList.MIN_SINGLE_CASH_LIMIT_GREATER_THAN + riskBean.getMinSingleCashLimit();

            } else if (Double.parseDouble(riskBean.getMaxSingleCashLimit()) < Double.parseDouble(riskProfBean.getMaxSingleCashLimit())) {

                isValid = false;

                errorMessage = MessageVarList.MAX_SINGLE_CASH_LIMIT_LESS_THAN + riskBean.getMaxSingleCashLimit();

            } else if (Integer.parseInt(riskBean.getMaxTxnCount()) < Integer.parseInt(riskProfBean.getMaxTxnCount())) {

                isValid = false;

                errorMessage = MessageVarList.MAX_TXN_COUNT_LESS_THAN + riskBean.getMaxTxnCount();

            } else if (Double.parseDouble(riskBean.getMaxCashCount()) < Double.parseDouble(riskProfBean.getMaxCashCount())) {

                isValid = false;

                errorMessage = MessageVarList.MAX_CASH_COUNT_LESS_THAN + riskBean.getMaxCashCount();

            } else if (Double.parseDouble(riskBean.getMaxTotTxnAmount()) < Double.parseDouble(riskProfBean.getMaxTotTxnAmount())) {

                isValid = false;

                errorMessage = MessageVarList.TOT_MAX_TXN_AMOUNT_LESS_THAN + riskBean.getMaxTotTxnAmount();

            } else if (Double.parseDouble(riskBean.getMaxTotCashTxnAmount()) < Double.parseDouble(riskProfBean.getMaxTotCashTxnAmount())) {

                isValid = false;

                errorMessage = MessageVarList.TOT_MAX_CASH_AMOUNT_LESS_THAN + riskBean.getMaxTotCashTxnAmount();

            } else if ((riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.ACCOUNT_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE)) && Double.parseDouble(riskBean.getExtraAuthLimit()) < Double.parseDouble(riskProfBean.getExtraAuthLimit())) {

                isValid = false;

                errorMessage = MessageVarList.EXTRA_AUTH_LIMIT_LESS_THAN + riskBean.getExtraAuthLimit();

            }


        } catch (Exception ex) {
            throw ex;
        }


        return isValid;
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
            String uniqueId = request.getParameter(riskProfBean.getRiskProfCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Risk Profile. Risk Profile Code :" + riskProfBean.getRiskProfCode() + "; by : " + sessionVarlist.getCMSSessionUser().getUserName());
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
            systemAuditBean.setOldValue(riskProfBean.getOldValue());
            //set new value of change if required
            systemAuditBean.setNewValue(this.setNewUpdateValue(riskProfBean));


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }

    }

    private String setNewUpdateValue(RiskProfileBean newBean) throws Exception {
        String newValue = null;
        String maxPinCount = null;
        String cusProfCode = null;
        String accProfCode = null;
        String mercProfCode = null;

        try {

            if (newBean.getMaxPinCount() != null) {

                maxPinCount = newBean.getMaxPinCount();
            } else {
                maxPinCount = "-";
            }


            if (newBean.getCustomerProfCode() != null) {

                cusProfCode = newBean.getCustomerProfCode();
            } else {
                cusProfCode = "-";
            }


            if (newBean.getAccountProfCode() != null) {

                accProfCode = newBean.getAccountProfCode();
            } else {
                accProfCode = "-";
            }


            if (newBean.getMerchantProfCode() != null) {

                mercProfCode = newBean.getMerchantProfCode();
            } else {
                mercProfCode = "-";
            }

            newValue = newBean.getRiskProfCode() + "|" + newBean.getDescription() + "|" + newBean.getStatus() + "|"
                    + cusProfCode + "|" + accProfCode + "|" + mercProfCode + "|"
                    + newBean.getPeroid() + "|" + newBean.getMinSingleTxnLimit() + "|" + newBean.getMaxSingleTxnLimit() + "|"
                    + newBean.getMinSingleCashLimit() + "|" + newBean.getMaxSingleCashLimit() + "|" + newBean.getMaxTxnCount() + "|"
                    + newBean.getMaxCashCount() + "|" + newBean.getMaxTotTxnAmount() + "|" + newBean.getMaxTotCashTxnAmount() + "|"
                    + newBean.getExtraAuthLimit() + "|" + maxPinCount;



        } catch (Exception ex) {
            throw ex;
        }

        return newValue;
    }

    /**
     * 
     * @param txnType
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean updateRiskProfile(RiskProfileBean riskProfBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            riskManager = new RiskProfileManager();
            success = riskManager.updateRiskProfile(riskProfBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
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
