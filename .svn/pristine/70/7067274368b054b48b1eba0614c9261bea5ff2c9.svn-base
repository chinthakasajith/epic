/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantlocation.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.bean.MerchantLocManualTxnBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.businesslogic.MerchantLocationManager;
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
public class UpdateMerchantLocationManualTxnServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private HttpSession sessionObj = null;
    private SystemAuditBean systemAuditBean = null;
    private String errorMessage = null;
    private List<RiskProfileBean> riskProfileList = null;
    private List<MerchantCategoryBean> merchantCategoryList = null;
    private List<CurrencyBean> currencyList = null;
    private List<TypeMgtBean> notAssignedTxnTypeList = null;
    private List<TypeMgtBean> assignedTxnTypeList = null;
    private MerchantLocationManager merchantLocManager = null;
    private MerchantLocManualTxnBean manualBean = null;
    private String[] assignTxnTypeList = null;
    private String url = "/mtmm/merchantmgt/manualtxnhome.jsp";

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
                String pageCode = PageVarList.MERCHANT_LOCATION;
                String taskCode = TaskVarList.UPDATE;

                ///////////////////////////////////////////////////////////////////////////

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        setUserInputToBean(request);

                        String merchantId = sessionVarlist.getMerchantId();

                        merchantLocManager = new MerchantLocationManager();

                        riskProfileList = new ArrayList<RiskProfileBean>();
                        riskProfileList = merchantLocManager.getTerminalRiskProfile(StatusVarList.TERMINAL_RISK_PROFILE, merchantId);
                        ///////////////////////////////////////////////////////////////

                        merchantCategoryList = new ArrayList<MerchantCategoryBean>();
                        merchantCategoryList = merchantLocManager.getMccOfMerchant(merchantId);

                        currencyList = new ArrayList<CurrencyBean>();
                        currencyList = merchantLocManager.getCurrencyOfMerchant(merchantId);

                        assignedTxnTypeList = new ArrayList<TypeMgtBean>();
                        assignedTxnTypeList = merchantLocManager.getAssignTxnTypeOfMerchantTerminal(merchantId);

                        notAssignedTxnTypeList = new ArrayList<TypeMgtBean>();
                        notAssignedTxnTypeList = merchantLocManager.getNotAssignTxnTypeOfMerchantTerminal(merchantId);

                        request.setAttribute("riskProfileList", riskProfileList);
                        request.setAttribute("merchantCategoryList", merchantCategoryList);
                        request.setAttribute("currencyList", currencyList);
                        request.setAttribute("notAssignedTxnTypeList", notAssignedTxnTypeList);
                        request.setAttribute("assignedTxnTypeList", assignedTxnTypeList);

                        if (validateUserInput(manualBean)) {

                            // this.setAudittraceValue(request);

                            try {
                                boolean success = updateMerchantLocationManualTxn(manualBean, assignTxnTypeList, systemAuditBean);

                                if (success) {
                                    request.setAttribute("successMsg", MessageVarList.MERCHANT_LOCATION_MANUAL_TERMINAL_UPDATE);
                                }
                                rd = getServletContext().getRequestDispatcher("/LoadMerchantLocationServlet");

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("errorMsg", oraMessage);
                                request.setAttribute("operationtype", "UPDATE");
                                request.setAttribute("manualBean", manualBean);
                                rd = getServletContext().getRequestDispatcher(url);
                            }

                        } else {
                            request.setAttribute("operationtype", "UPDATE");
                            request.setAttribute("manualBean", manualBean);
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "UPDATE");
                        request.setAttribute("manualBean", manualBean);
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


        } catch (Exception ex) {
            request.setAttribute("operationtype", "UPDATE");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean isSet = true;
        try {

            manualBean = new MerchantLocManualTxnBean();

            manualBean.setMerchantId(sessionVarlist.getMerchantId());
            manualBean.setRiskProfCode(request.getParameter("riskProfCode").trim());
            manualBean.setCurrency(request.getParameter("currency").trim());
            manualBean.setMcc(request.getParameter("mcc").trim());

            assignTxnTypeList = request.getParameterValues("assignTxnTypeList");

            manualBean.setLastUpdatedUser(sessionUser.getUserName());

        } catch (Exception e) {
            isSet = false;
            throw e;

        }

        return isSet;
    }

    public boolean validateUserInput(MerchantLocManualTxnBean manualBean) throws Exception {
        boolean isValidate = true;

        //////validate user inputs

        try {

            if (manualBean.getRiskProfCode().contentEquals("") || manualBean.getRiskProfCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.RISK_PROFILE_NULL;

            } else if (manualBean.getMcc().contentEquals("") || manualBean.getMcc().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MCC_NULL;

            } else if (manualBean.getCurrency().contentEquals("") || manualBean.getCurrency().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.CURRENCY_NULL;

            } else if (assignTxnTypeList == null) {
                isValidate = false;

                errorMessage = MessageVarList.TRANSACTION_NULL;

            }
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public boolean updateMerchantLocationManualTxn(MerchantLocManualTxnBean manualBean, String[] assignTxnTypeList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            /// call insertMerchantLocation Method in MerchantLocationManager class

            merchantLocManager = new MerchantLocationManager();
            success = merchantLocManager.updateMerchantLocationManualTxn(manualBean, assignTxnTypeList, systemAuditBean);

        } catch (SQLException ex) {
            throw ex;
        }
        return success;
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
