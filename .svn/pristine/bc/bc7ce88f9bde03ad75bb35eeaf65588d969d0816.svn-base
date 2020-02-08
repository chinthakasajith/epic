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
public class OnChangeCreateRiskProfileMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private RiskProfileBean riskProfBean = null;
    private RiskProfileBean riskBean = null;
    private List<RiskProfileBean> riskProfList = null;
    private List<RiskProfileBean> selectedRiskProfList = null;
    private RiskProfileManager riskManager = null;
    private String opType = null;
    private String checkProfileType = null;
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
            try {
                HttpSession sessionObj = request.getSession(false);
                request.setAttribute("operationtype", "add");

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
                    String pageCode = PageVarList.RISKPROFILE;
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
            try {

                // get values from url
                opType = request.getParameter("value");
                checkProfileType = request.getParameter("profileType");

                setUserInputToBean(request);

                String profileType = riskProfBean.getProfileType();
                ///////////////////////////////////////////////////////

                riskManager = new RiskProfileManager();
                selectedRiskProfList = new ArrayList<RiskProfileBean>();
                if (!(profileType.equals(StatusVarList.CUSTOMER_RISK_PROFILE) || profileType.equals(StatusVarList.MERCHANT_RISK_PROFILE) || profileType.equals(""))) {

                    //get the account/card/merchant profile type according to the Risk profile type 
                    selectedRiskProfList = riskManager.getSelectedProfileTypes(profileType);

                }
                if (selectedRiskProfList.isEmpty() && profileType.equals(StatusVarList.ACCOUNT_RISK_PROFILE)) {

                    request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.CREATE_CUSTOMER_RISK_PROFILE);

                } else if (selectedRiskProfList.isEmpty() && profileType.equals(StatusVarList.CARD_RISK_PROFILE)) {

                    request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.CREATE_ACCOUNT_RISK_PROFILE);

                } else if (selectedRiskProfList.isEmpty() && profileType.equals(StatusVarList.TERMINAL_RISK_PROFILE)) {

                    request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.CREATE_MERCHANT_RISK_PROFILE);

                } else {
                    //////////////// get country list//////////////////////////////////////////
                    CountryMgtManager comgtObj = new CountryMgtManager();
                    List<CountryMgtBean> notAssignedCountryList = comgtObj.getAllCountryInfo();

                    ///////////////////////get transaction Type List////////////////////////
                    TxnTypeMgtManager txnObj = new TxnTypeMgtManager();
                    List<TypeMgtBean> notAssignedTypeList = txnObj.getTxnTypeDetails();

                    /////////////////////get merchant category List//////////////////////////////
                    MerchantCategoryManager mcmObj = new MerchantCategoryManager();
                    List<MerchantCategoryBean> notAssignedMerchantCategoryList = mcmObj.getAllMerchntCatgryDetails();

                    //////////////////////get Currency List//////////////////////////////////
                    CurrencyMgtManager curmgtObj = new CurrencyMgtManager();
                    List<CurrencyBean> notAssignedCurrencyList = curmgtObj.getCurrencyDetails();


                    ///////////////////////////////////////////////////////////////
                    RiskProfileManager riskObj = new RiskProfileManager();
                    List<RiskProfileBinBean> notAssignedBinList = riskObj.getAllBin();

                    ////////////////////////////////////////////////////////////////
                    if (opType.equals("add")) {
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("disableTabIndex", "1");
                    } else if (opType.equals("update")) {
                        request.setAttribute("operationtype", "update");
                    }

                    if (!profileType.equals("")) {

                        request.setAttribute("otherRecord", "true");

                        if (profileType.equals(StatusVarList.CARD_RISK_PROFILE)) {

                            request.setAttribute("profileType", "card");

                        }
                        if (profileType.equals(StatusVarList.ACCOUNT_RISK_PROFILE)) {

                            request.setAttribute("profileType", "account");

                        }
                        if (profileType.equals(StatusVarList.CUSTOMER_RISK_PROFILE)) {

                            request.setAttribute("profileType", "customer");

                        }
                        if (profileType.equals(StatusVarList.TERMINAL_RISK_PROFILE)) {

                            request.setAttribute("profileType", "terminal");

                        }

                        if (profileType.equals(StatusVarList.TERMINAL_RISK_PROFILE) || profileType.equals(StatusVarList.MERCHANT_RISK_PROFILE)) {

                            request.setAttribute("bin", "bin");

                        }
                        if (profileType.equals(StatusVarList.CUSTOMER_RISK_PROFILE) || profileType.equals(StatusVarList.MERCHANT_RISK_PROFILE)) {

                            request.setAttribute("notAssignedCountryList", notAssignedCountryList);
                            request.setAttribute("notAssignedTypeList", notAssignedTypeList);
                            request.setAttribute("notAssignedMerchantCategoryList", notAssignedMerchantCategoryList);
                            request.setAttribute("notAssignedCurrencyList", notAssignedCurrencyList);

                        }
                        if (profileType.equals(StatusVarList.MERCHANT_RISK_PROFILE) || profileType.equals(StatusVarList.TERMINAL_RISK_PROFILE)) {

                            request.setAttribute("notAssignedBinList", notAssignedBinList);
                        }

                        request.setAttribute("selectedtab", "0");
                        request.setAttribute("selectedRiskProfList", selectedRiskProfList);

                    }
                    request.setAttribute("riskProfBean", riskProfBean);
                }
                request.setAttribute("selectedtab", "0");
                request.setAttribute("disableTabIndex", "1");
                rd = request.getRequestDispatcher(url);

            } catch (Exception e) {
                throw e;
            }

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
            rd.forward(request, response);

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            request.setAttribute("selectedtab", "0");
            rd.forward(request, response);
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            riskProfBean = new RiskProfileBean();
            // set value to bean from jsp
            riskProfBean.setRiskProfCode(request.getParameter("riskProfCode").trim());
            riskProfBean.setDescription(request.getParameter("description").trim());
            riskProfBean.setStatus(request.getParameter("selectstatuscode").trim());
            riskProfBean.setProfileType(request.getParameter("selectRiskProfType").trim());

            if (opType.equals("update")) {
                if (!(riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.MERCHANT_RISK_PROFILE))) {

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
                if (!checkProfileType.equals("")) {

                    riskProfBean.setPeroid(request.getParameter("peroid").trim());
                    riskProfBean.setMinSingleTxnLimit(request.getParameter("minimumSingleTxnLimit").trim());
                    riskProfBean.setMaxSingleTxnLimit(request.getParameter("maxSingleTxnLimit").trim());
                    riskProfBean.setMinSingleCashLimit(request.getParameter("minSingleCashLimit").trim());
                    riskProfBean.setMaxSingleCashLimit(request.getParameter("maxSingleCashLimit").trim());
                    riskProfBean.setMaxTxnCount(request.getParameter("maxTxnCount").trim());
                    riskProfBean.setMaxTotTxnAmount(request.getParameter("maxTotalTxnAmount").trim());
                    riskProfBean.setMaxCashCount(request.getParameter("maxCashCount").trim());
                    riskProfBean.setMaxTotCashTxnAmount(request.getParameter("maxTotalCashAmount").trim());


                    if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE)||riskProfBean.getProfileType().equals(StatusVarList.ACCOUNT_RISK_PROFILE)||riskProfBean.getProfileType().equals(StatusVarList.CUSTOMER_RISK_PROFILE)) {
                        riskProfBean.setExtraAuthLimit(request.getParameter("extraAuthLimit").trim());
                    }
                    
                    // check profile type whether it is previously card or anything else
                    if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE) && checkProfileType.equals(StatusVarList.CARD_RISK_PROFILE)) {

                        riskProfBean.setMaxPinCount(request.getParameter("maxPinCount").trim());

                    }

                    riskProfBean.setLastUpdatedUser(sessionUser.getUserName());
                }
            } else {

                riskProfBean.setPeroid("0");
                riskProfBean.setMinSingleTxnLimit("0");
                riskProfBean.setMaxSingleTxnLimit("0");
                riskProfBean.setMinSingleCashLimit("0");
                riskProfBean.setMaxSingleCashLimit("0");
                riskProfBean.setMaxTxnCount("0");
                riskProfBean.setMaxTotTxnAmount("0");
                riskProfBean.setMaxCashCount("0");
                riskProfBean.setMaxTotCashTxnAmount("0");
                riskProfBean.setExtraAuthLimit("0");

                if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE)) {

                    riskProfBean.setMaxPinCount("0");

                }
            }
        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
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
