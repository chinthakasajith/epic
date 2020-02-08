/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ExChangeRateBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.ExchangeRateManager;
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
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ayesh
 */
public class UpdateExchangeRateServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/administrator/controlpanel/transactionMgt/exchangeratemgthome.jsp";
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    private String errorMsg;
    private ExChangeRateBean exBean;

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
        int row = 0;
        try {
            try {

                HttpSession sessionObj = request.getSession(false);
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
            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }


            String fromCurr = request.getParameter("upfromCur");
            String toCurr = request.getParameter("uptoCur");
            String tmpRate = request.getParameter("uprate");
            String flagRate = request.getParameter("invertRate");
            float rate = 0f;

            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.UPDATE)) {
                throw new AccessDeniedException();
            }


            exBean = new ExChangeRateBean();

            exBean.setFromCurrency(Integer.parseInt(fromCurr));
            exBean.setToCurrency(Integer.parseInt(toCurr));
            if (this.isRateValid(tmpRate)) {
                rate = Float.parseFloat(tmpRate);

                exBean.setLastUpdateUser(sessionUser.getUserName());
                exBean.setLastUpdateDate(new Date());
                try {
                    if (flagRate.equalsIgnoreCase("ON")) {
                        this.setAudittraceValue(request);
                        exBean.setRate(String.valueOf(1 / rate));
                        exBean.setInvetRate(rate);
                        exBean.setFlag(false);
                        exBean.setFromCurrency(Integer.parseInt(toCurr));
                        exBean.setToCurrency(Integer.parseInt(fromCurr));

                        row = row + ExchangeRateManager.getExchangeRateManager().addExchangeRate(exBean, systemAuditBean);
                        row = row + ExchangeRateManager.getExchangeRateManager().updateExchangeRate(exBean, systemAuditBean, false);

                        if (row == 1 || row == 2) {
                            request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.EXCHANGERATE_UPDATE_SUCCESS);
                            request.setAttribute("operationtype", "add");
                        }
                    }

                } catch (NullPointerException e) {
                    exBean.setFromCurrency(Integer.parseInt(fromCurr));
                    exBean.setToCurrency(Integer.parseInt(toCurr));
                    this.setAudittraceValue(request);
                    exBean.setInvetRate(1 / rate);
                    exBean.setRate(String.valueOf(rate));
                    row = row + ExchangeRateManager.getExchangeRateManager().updateExchangeRate(exBean, systemAuditBean, true);

                    if (row == 1 || row == 2) {
                        request.setAttribute("operationtype", "add");
                        request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.EXCHANGERATE_UPDATE_SUCCESS);
                    }
                }


            } else {
                request.setAttribute(MessageVarList.JSP_ERROR, errorMsg);
                request.setAttribute("operationtype", "update");
                request.setAttribute(RequestVarList.EXCHANGE_BEAN, exBean);
            }



            List<CurrencyBean> currencyList = ExchangeRateManager.getExchangeRateManager().getAllCurrencyDetails();
            sessionVarlist.setExchangeCurrencyList(currencyList);
            List<ExChangeRateBean> ecList = ExchangeRateManager.getExchangeRateManager().getAllExchangeRateDetails();


            request.setAttribute(RequestVarList.EXCHANGE_CURRENCY, currencyList);
            request.setAttribute(RequestVarList.EXCHANGE_DETAILS, ecList);

            rd = request.getRequestDispatcher(url);
        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadExchangeRateMgtServlet");
            rd.include(request, response);

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
            request.setAttribute("operationtype", "update");
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
            request.setAttribute("operationtype", "update");
        } finally {
            rd.forward(request, response);
        }
    }

    /**
     * use to validate rate
     * @param tmpRate
     * @return flag-boolean
     */
    private boolean isRateValid(String tmpRate) {
        boolean flag = true;
        errorMsg = "";
        if (tmpRate.isEmpty()) {
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.EXCHANGE_RATE_INVALID;
            }

        } else {
            try {
                exBean.setRate(tmpRate);
                Float.parseFloat(tmpRate);
            } catch (Exception e) {
                if (errorMsg.isEmpty()) {
                    flag = false;
                    errorMsg = MessageVarList.EXCHANGE_RATE_INVALID;
                }
            }


        }
        return flag;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("upfromCur");
            String toCut = request.getParameter("uptoCur");
            String rate = request.getParameter("uprate");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("UPDATE " + uniqueId + " Exchange rate  by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
            systemAuditBean.setPageCode(PageVarList.EXCHANGERATE);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId + "-" + toCut);
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("Exchange rate");
            //set old value of change if required
            systemAuditBean.setOldValue(String.valueOf(sessionVarlist.getExChangeRateBean().getRate()));
            //set new value of change if required
            systemAuditBean.setNewValue(rate);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());


        } catch (Exception ex) {
            throw ex;
        }



    }

    /**
     * isValidTaskByUser
     * @param userTaskList
     * @param task
     * @return
     * @throws Exception 
     */
    private boolean isValidTaskByUser(List<String> userTaskList, String task) throws Exception {
        boolean isValidTask = false;
        try {
            for (String usertask : userTaskList) {
                if (task.equals(usertask)) {
                    isValidTask = true;
                }
            }
            return isValidTask;
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
