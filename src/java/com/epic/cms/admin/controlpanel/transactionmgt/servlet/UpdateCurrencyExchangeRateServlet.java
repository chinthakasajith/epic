/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyExchangeRateBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CurrencyExchangeRateManager;
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
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nisansala
 */
public class UpdateCurrencyExchangeRateServlet extends HttpServlet {

    //initializing variables
    String oldValue = "";
    String newValue = "";
    HttpSession sessionObj;
    private RequestDispatcher rd;
    private List<String> userTaskList;
    private SessionUser sessionUser = null;
    private SessionVarList sessionVarlist = null; 
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    //--------------------------------------------------
    private String errorMessage = null;
    private boolean successUpdate = false;
    private List<CurrencyBean> currencyList = null;
    private CurrencyExchangeRateManager currXRateMgr;
    private CurrencyExchangeRateBean exchangeBean = null;
    private List<CurrencyExchangeRateBean> exchangeRateList = null;
    private String url = "/administrator/controlpanel/transactionMgt/currencyexchangeratehome.jsp";

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
                String pageCode = PageVarList.CURRENCY_EXCHANGE_RATE;
                String taskCode = TaskVarList.UPDATE;

                //check whether user_role have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        if (setUserInputToBean(request)) {
                            currXRateMgr = new CurrencyExchangeRateManager();

                            //validate user input
                            if (validateUserInput(exchangeBean)) {

                                try {
                                    //set values to the audit trace
                                    request.setAttribute("operationtype", "add");
                                    //set values to the audit trace
                                    this.setAudittraceValue(request);
                                    //update the record
                                    successUpdate = currXRateMgr.updateCurrencyExchangeRate(exchangeBean, systemAuditBean);

                                    if (successUpdate) {

                                        currencyList = currXRateMgr.getUnusedCurrencyDetails();
                                        request.setAttribute("successMsg", " " + MessageVarList.SUCCESS_UPDATE + "currency exchange rate " + exchangeBean.getCurrencyCode());
                                        rd = getServletContext().getRequestDispatcher("/LoadCurrencyExchangeRateServlet");

                                    }
                                } catch (SQLException e) {

                                    request.setAttribute("operationtype", "update");
                                    currencyList = currXRateMgr.getUnusedCurrencyDetails();
                                    OracleMessage message = new OracleMessage();
                                    String oraMessage = message.getMessege(e.getMessage());
                                    request.setAttribute("errorMsg", oraMessage);
                                    request.setAttribute("currencyList", currencyList);
                                    request.setAttribute("exchangeRateList", exchangeRateList);
                                    rd = getServletContext().getRequestDispatcher(url);
                                    rd.forward(request, response);

                                }

                            } else {
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("errorMsg", errorMessage);
                                request.setAttribute("exchangeBean", exchangeBean);

                                currencyList = currXRateMgr.getAllCurrencyDetails();
                                request.setAttribute("currencyList", currencyList);

                                rd = getServletContext().getRequestDispatcher(url);

                            }
                        } else {
                            request.setAttribute("operationtype", "add");
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);

                        }
                        exchangeRateList = currXRateMgr.getAllCurrencyExchangeRates();
                        request.setAttribute("exchangeRateList", exchangeRateList);
                    } catch (Exception e) {

                        request.setAttribute("operationtype", "add");

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
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } //catch session exception
        catch (NewLoginSessionException nlex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);

        } catch (Exception ex) {
        } finally {
            rd.include(request, response);
        }
    }

    /**
     * check valid access
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

    /**
     * set values to the audit trace
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(exchangeBean.getCurrencyCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Currency Exchange Rate. Currency Code '" + exchangeBean.getCurrencyCode() + "'; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
            systemAuditBean.setPageCode(PageVarList.CURRENCY_EXCHANGE_RATE);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
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

    /**
     * to set input to the bean
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            exchangeBean = new CurrencyExchangeRateBean();

            exchangeBean.setCurrencyCode(sessionVarlist.getExchangeRate().getCurrencyCode());
            exchangeBean.setSellRate(request.getParameter("sellrate").trim());
            exchangeBean.setBuyRate(request.getParameter("buyrate").trim());
            exchangeBean.setLastUpdateUser(sessionUser.getUserName());

            oldValue = request.getParameter("oldValue");
            newValue = exchangeBean.getCurrencyCode() + "|" + exchangeBean.getSellRate() + "|" + exchangeBean.getBuyRate();

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    /**
     * to validate user input
     * @param exchange
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(CurrencyExchangeRateBean exchange) throws Exception {
        boolean isValidate = true;

        try {
            if (exchange.getSellRate().contentEquals("") || exchange.getSellRate().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SELL_RATE_NULL;
            } else if (!UserInputValidator.isDecimal_Numeric(exchange.getSellRate())) {
                isValidate = false;
                errorMessage = MessageVarList.SELL_RATE_INVALID;
            }
            if (exchange.getBuyRate().contentEquals("") || exchange.getBuyRate().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BUY_RATE_NULL;
            } else if (!UserInputValidator.isDecimal_Numeric(exchange.getBuyRate())) {
                isValidate = false;
                errorMessage = MessageVarList.BUY_RATE_INVALID;
            }
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
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
