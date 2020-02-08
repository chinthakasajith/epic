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
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ViewRiskProfileMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private RiskProfileManager riskManager = null;
    private List<RiskProfileBean> riskProfList = null;
    private RiskProfileBean riskProfBean = null;
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
    private String description = null;
    private String url = "/administrator/controlpanel/profilemgt/riskprofilemgthome.jsp";

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
            ////////////////////////////////////////////////////////////////////

            try {
                String riskProfileCode = request.getParameter("riskProfCode");

                riskManager = new RiskProfileManager();
                riskProfBean = new RiskProfileBean();

                riskProfList = new ArrayList<RiskProfileBean>();
                riskProfList = riskManager.getRiskProfileDetails();


                for (RiskProfileBean newRiskProfBean : riskProfList) {

                    if (newRiskProfBean.getRiskProfCode().equals(riskProfileCode)) {
                        riskProfBean = newRiskProfBean;
                    }
                }
                if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE)) {
                    description = this.getDescription(riskProfBean.getAccountProfCode());
                } else if (riskProfBean.getProfileType().equals(StatusVarList.ACCOUNT_RISK_PROFILE)) {
                    description = this.getDescription(riskProfBean.getCustomerProfCode());
                } else if (riskProfBean.getProfileType().equals(StatusVarList.TERMINAL_RISK_PROFILE)) {
                    description = this.getDescription(riskProfBean.getMerchantProfCode());
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

//get the blocked list according to account/card/merchant profile type 

                assignedCountryList = riskManager.getSelectedCountry(riskProfileCode);
                notAssignedCountryList = riskManager.getNotBlockedCountry(riskProfileCode);
                assignedTypeList = riskManager.getSelectedTxnType(riskProfileCode);
                notAssignedTypeList = riskManager.getNotBlockedTxnType(riskProfileCode);
                assignedMerchantCategoryList = riskManager.getSelectedMcc(riskProfileCode);
                notAssignedMerchantCategoryList = riskManager.getNotBlockedMcc(riskProfileCode);
                assignedCurrencyList = riskManager.getSelectedCurrency(riskProfileCode);
                notAssignedCurrencyList = riskManager.getNotBlockedCurrency(riskProfileCode);
                assignedBinList = riskManager.getSelectedBin(riskProfileCode);
                notAssignedBinList = riskManager.getNotBlockedBin(riskProfileCode);



                if (riskProfBean != null) {

                    if (riskProfBean.getProfileType().equals(StatusVarList.TERMINAL_RISK_PROFILE) || riskProfBean.getProfileType().equals(StatusVarList.MERCHANT_RISK_PROFILE)) {

                        request.setAttribute("bin", "bin");

                    }

                    request.setAttribute("riskProfBean", riskProfBean);
                    request.setAttribute("description", description);
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
                    request.setAttribute("selectedtab", "0");

                    rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/profilemgt/viewriskprofile.jsp");
                }

                rd.forward(request, response);

            } catch (Exception e) {

                request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                request.setAttribute(RequestVarList.RISKMGT_LIST, riskProfList);
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
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

        } catch (Exception ex) {
        } finally {
            out.close();
        }
    }

    private String getDescription(String rpCode) throws Exception {
        String des = null;

        try {
            riskManager = new RiskProfileManager();
            des = riskManager.getDescription(rpCode);

        } catch (Exception ex) {
            throw ex;
        }

        return des;
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
