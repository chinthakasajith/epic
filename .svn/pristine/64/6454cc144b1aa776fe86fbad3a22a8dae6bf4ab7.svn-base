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
public class AddCurrencyExchangeRateServlet extends HttpServlet {

    //initialize variables
    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private SystemUserManager sysUserMgr;
    private SessionVarList sessionVarlist;   
    private SystemAuditBean systemAuditBean = null;
    //--------------------------------------------
    private boolean success = false;
    private String errorMessage = null;
    private List<CurrencyBean> currencyList = null;
    private CurrencyExchangeRateManager currXRateMgr;
    private CurrencyExchangeRateBean exchangeBean = null;
    private List<CurrencyExchangeRateBean> exchangeRateList = null;
    private String url = "/administrator/controlpanel/transactionMgt/currencyexchangeratehome.jsp";
    private String newValue;
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
            sessionObj = request.getSession(false);
            try {
                sysUserMgr = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                //check system user is in same session or not
                try {

                    if (!sysUserMgr.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
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
            try {
                //set page code and task codes
                String pageCode = PageVarList.CURRENCY_EXCHANGE_RATE;
                String taskCode = TaskVarList.ADD;

                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    try {
                        request.setAttribute("operationtype", "add");
                        setUserInputToBean(request);

                        currXRateMgr = new CurrencyExchangeRateManager();
                        currencyList = currXRateMgr.getUnusedCurrencyDetails();
                        exchangeRateList = currXRateMgr.getAllCurrencyExchangeRates();

                        if (validateUserInput(exchangeBean)) {
                            //set values to the audit trace
                            this.setAudittraceValue(request);
                            try {
                                //insert a new currency exchange rete record
                                success = currXRateMgr.insertNewCurrencyExchangeRate(exchangeBean, systemAuditBean);
                                if (success) {
                                    request.setAttribute("successMsg", MessageVarList.SUCCESS_ADD + " currency exchange rate " + exchangeBean.getCurrencyCode());
                                    rd = getServletContext().getRequestDispatcher("/LoadCurrencyExchangeRateServlet");
                                    rd.include(request, response);
                                }

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("errorMsg", oraMessage);
                                request.setAttribute("currencyList", currencyList);
                                request.setAttribute("exchangeRateList", exchangeRateList);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);

                            }

                        } else {
                            request.setAttribute("exchangeBean", exchangeBean);
                            request.setAttribute("errorMsg", errorMessage);
                        }

                        request.setAttribute("currencyList", currencyList);
                        request.setAttribute("exchangeRateList", exchangeRateList);
                        sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                    } catch (Exception e) {
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }

                    rd = getServletContext().getRequestDispatcher(url);
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
        } catch (NewLoginSessionException nlex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_CURR_X_RATE);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * to check users access permissions for the required task and page
     * @param userrole
     * @param pagecode
     * @param task
     * @return
     * @throws Exception 
     */
    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {
        boolean isValidTaskAccess = false;

        try {
            sysUserMgr = new SystemUserManager();

            //get all tasks for userrole for this page
            userTaskList = sysUserMgr.getTasksByPageCodeAndUserRole(userrole, pagecode);

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
     * to set values to the audit trace
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
            systemAuditBean.setDescription("Add Currency Exchange Rate. Currency Code '" + exchangeBean.getCurrencyCode() + "';  by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
            systemAuditBean.setPageCode(PageVarList.CURRENCY_EXCHANGE_RATE);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue(newValue);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to set user input to bean
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            exchangeBean = new CurrencyExchangeRateBean();

            exchangeBean.setCurrencyCode(request.getParameter("currencycode").trim());
            exchangeBean.setSellRate(request.getParameter("sellrate").trim());
            exchangeBean.setBuyRate(request.getParameter("buyrate").trim());
            exchangeBean.setLastUpdateUser(sessionUser.getUserName());

            newValue = exchangeBean.getCurrencyCode() + "|" + exchangeBean.getSellRate() + "|" + exchangeBean.getBuyRate() + "|" + exchangeBean.getLastUpdateUser();
                    
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
            if (exchange.getCurrencyCode().contentEquals("") || exchange.getCurrencyCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CURRENCY_CURRENCYCODE_NULL;
            } else if (exchange.getSellRate().contentEquals("") || exchange.getSellRate().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SELL_RATE_NULL;
            }
            else if (!UserInputValidator.isDecimal_Numeric(exchange.getSellRate())) {
                isValidate = false;
                errorMessage = MessageVarList.SELL_RATE_INVALID;
            }
            else if (exchange.getBuyRate().contentEquals("") || exchange.getBuyRate().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BUY_RATE_NULL;
            } 
             else if (!UserInputValidator.isDecimal_Numeric(exchange.getBuyRate())) {
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
