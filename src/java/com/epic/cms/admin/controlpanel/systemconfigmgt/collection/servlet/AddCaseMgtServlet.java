/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.collection.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.CaseTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.businesslogic.CaseMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author asitha_l
 */
public class AddCaseMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String url = "/administrator/controlpanel/systemconfigmgt/collection/casemanagement.jsp";
    private CaseMgtManager caseMgtManager;
    private Map<String, String> statusMap;
    private Map<String, String> currencyMap;
    private Map<String, String> cardStatusMap;
    private CaseTypeBean caseTypeBean;
    private String errorMessage;
    private List<CaseTypeBean> caseList;
    private SystemAuditBean systemAuditBean = null;

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
                sessionObj = request.getSession(false);
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

            try {
                //set page code and task codes
                String pageCode = PageVarList.CASE_MANAGEMENT;
                String taskCode = TaskVarList.ADD;

                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    sessionVarlist.setUserPageTaskList(userTaskList);

                    request.setAttribute("operationtype", "add");
                    this.getStatus();
                    this.getCurrencyTypes();
                    this.getCardStatus();
                    this.getAllCases();

                    request.setAttribute("statusMap", statusMap);
                    request.setAttribute("currencyMap", currencyMap);
                    request.setAttribute("cardStatusMap", cardStatusMap);
                    request.setAttribute("caseList", caseList);

                    this.userInputToBean(request);

                    if (this.validateUserInput(caseTypeBean)) {
                        this.setAudittraceValue(request);
                        if (this.insertCaseType(caseTypeBean)) {
                            request.setAttribute("successMsg", MessageVarList.SUCCESS_ADD + "Case type code '" + caseTypeBean.getCaseTypeCode() + "'");
                            rd = request.getRequestDispatcher("/LoadCaseMgtServlet");
                        }
                    } else {
                        request.setAttribute("caseTypeBean", caseTypeBean);
                        request.setAttribute("addValidation", "fail");
                        request.setAttribute("errorMsg", errorMessage);
                        rd = request.getRequestDispatcher(url);
                    }

                    sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);                    

                } else {
                    throw new AccessDeniedException();

                }

            } catch (AccessDeniedException adex) {
                throw adex;
            }

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);

        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.ACCESS_DENIED_TASK);
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

    private void getStatus() throws Exception {
        caseMgtManager = new CaseMgtManager();
        statusMap = new HashMap<String, String>();
        statusMap = caseMgtManager.getStatus();
    }

    private void getCurrencyTypes() throws Exception {
        caseMgtManager = new CaseMgtManager();
        currencyMap = new HashMap<String, String>();
        currencyMap = caseMgtManager.getCurrencyTypes();
    }

    private void getCardStatus() throws Exception {
        caseMgtManager = new CaseMgtManager();
        cardStatusMap = new HashMap<String, String>();
        cardStatusMap = caseMgtManager.getCardStatus();
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

    private void userInputToBean(HttpServletRequest request) {

        caseTypeBean = new CaseTypeBean();

        caseTypeBean.setCaseTypeCode(request.getParameter("casetypecode"));
        caseTypeBean.setDescription(request.getParameter("casetypedes"));
        caseTypeBean.setCurrencyTypeCode(request.getParameter("currency"));
        caseTypeBean.setStatusCode(request.getParameter("status"));
        caseTypeBean.setSeverityValue(request.getParameter("severityval"));
        caseTypeBean.setEntryCriteriaCode(request.getParameter("entrycriteria"));
        caseTypeBean.setEntryOverDuePeriodCode(request.getParameter("entryoverdueperiod"));
        caseTypeBean.setOverDueAmountType(request.getParameter("entryoverduetype"));
        if (caseTypeBean.getOverDueAmountType() == null) {
            caseTypeBean.setOverDueAmountType("");
        }
        caseTypeBean.setEntryOverDueAmount(request.getParameter("entryoverdueamount"));
        caseTypeBean.setOverLimitAmountType(request.getParameter("entryoverlimittype"));
        if (caseTypeBean.getOverLimitAmountType() == null) {
            caseTypeBean.setOverLimitAmountType("");
        }
        caseTypeBean.setEntryOverLimitAmount(request.getParameter("entryoverlimitamount"));
        caseTypeBean.setEntryAccStatusCode(request.getParameter("entryaccstatus"));
        caseTypeBean.setEntryCardStatusCode(request.getParameter("entrycardstatus"));
        caseTypeBean.setExitOverDueAmount(request.getParameter("exitoverdueamount"));
        caseTypeBean.setExitOverLimitAmount(request.getParameter("exitoverlimitamount"));
        caseTypeBean.setExitOverDuePeriodCode(request.getParameter("exitoverdueperiod"));
        caseTypeBean.setExitAccStatusCode(request.getParameter("exitaccstatus"));
        caseTypeBean.setExitCardStatusCode(request.getParameter("exitcardstatus"));        
        caseTypeBean.setProcessAutomatedStatus(request.getParameter("processautomated"));
        if(caseTypeBean.getProcessAutomatedStatus() == null){
            caseTypeBean.setProcessAutomatedStatus("");
        }
        caseTypeBean.setProcessManualStatus(request.getParameter("processmanual"));
        if(caseTypeBean.getProcessManualStatus() == null){
            caseTypeBean.setProcessManualStatus("");
        }
        caseTypeBean.setRemarks(request.getParameter("remarks"));

    }

    private boolean validateUserInput(CaseTypeBean caseTypeBean) throws Exception {
        try {
            boolean isValidate = true;
            final String caseCode = caseTypeBean.getCaseTypeCode();
            final String description = caseTypeBean.getDescription();
            final String currencyTypeCode = caseTypeBean.getCurrencyTypeCode();
            final String statusCode = caseTypeBean.getStatusCode();
            final String severityValue = caseTypeBean.getSeverityValue();
            final String entryCriteriaCode = caseTypeBean.getEntryCriteriaCode();
            final String entryOverDuePeriodCode = caseTypeBean.getEntryOverDuePeriodCode();
            final String overDueAmountType = caseTypeBean.getOverDueAmountType();
            final String entryOverDueAmount = caseTypeBean.getEntryOverDueAmount();
            final String entryAccStatusCode = caseTypeBean.getEntryAccStatusCode();
            final String entryCardStatusCode = caseTypeBean.getEntryCardStatusCode();
            final String overLimitAmountType = caseTypeBean.getOverLimitAmountType();
            final String entryOverLimitAmount = caseTypeBean.getEntryOverLimitAmount();
            final String exitOverDueAmount = caseTypeBean.getExitOverDueAmount();
            final String exitOverDuePeriodCode = caseTypeBean.getExitOverDuePeriodCode();
            final String exitOverLimitAmount = caseTypeBean.getExitOverLimitAmount();
            final String exitAccStatusCode = caseTypeBean.getExitAccStatusCode();
            final String exitCardStatusCode = caseTypeBean.getExitCardStatusCode();
            final String processAutomatedStatus = caseTypeBean.getProcessAutomatedStatus();
            final String processManualStatus = caseTypeBean.getProcessManualStatus();

            if (caseCode.contentEquals("") || caseCode.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CASE_TYPE_CODE_EMPTY;
            } else if (!UserInputValidator.isAlphaNumeric(caseCode)) {
                isValidate = false;
                errorMessage = MessageVarList.CASE_TYPE_CODE_INVALID;
            } else if (description.contentEquals("") || description.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CASE_DESCRIPTION_EMPTY;
            } else if (!UserInputValidator.isAlphaNumericWithSpace(description)) {
                isValidate = false;
                errorMessage = MessageVarList.CASE_DESCRIPTION_INVALID;
            } else if (currencyTypeCode.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.CASE_CURRENCY_TYPE_NULL;
            } else if (statusCode.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.CASE_STATUS_NULL;
            } else if (severityValue.contentEquals("") || severityValue.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SEVERITY_VALUE_NULL;
            } else if (!UserInputValidator.isNumeric(severityValue)) {
                isValidate = false;
                errorMessage = MessageVarList.SEVERITY_VALUE_NUMERIC;
            } else if (Integer.parseInt(severityValue) > 100) {
                isValidate = false;
                errorMessage = MessageVarList.SEVERITY_VALUE_LESSTHAN_100;
            } else if (entryCriteriaCode.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.CRITERIA_EMPTY;
            } else if (entryCriteriaCode.equals("OVERDUE") && entryOverDuePeriodCode.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_OVERDUE_PERIOD_EMPTY;
            } else if (entryCriteriaCode.equals("OVERDUE") && overDueAmountType.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.OVERDUE_AMOUNT_TYPE_EMPTY;
            } else if (entryCriteriaCode.equals("OVERDUE") && (entryOverDueAmount.contentEquals("") || entryOverDueAmount.length() <= 0)) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_OVERDUE_AMOUNT_EMPTY;
            } else if (entryCriteriaCode.equals("OVERDUE") && !UserInputValidator.isDecimalOrNumeric(entryOverDueAmount, "10", "2")) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_OVERDUE_AMOUNT_NUMERIC;
            } else if (entryCriteriaCode.equals("OVERDUE") && (overDueAmountType.equals("PERCENTA") && (Double.parseDouble(entryOverDueAmount) > 100))) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_OVERDUE_PERCENTAGE_INVALID;
            } else if (entryCriteriaCode.equals("OVERLIMIT") && overLimitAmountType.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.OVER_LIMIT_AMOUNT_TYPE_EMPTY;
            } else if (entryCriteriaCode.equals("OVERLIMIT") && (entryOverLimitAmount.contentEquals("") || entryOverLimitAmount.length() <= 0)) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_OVER_LIMIT_AMOUNT_EMPTY;
            } else if (entryCriteriaCode.equals("OVERLIMIT") && !UserInputValidator.isDecimalOrNumeric(entryOverLimitAmount, "10", "2")) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_OVER_LIMIT_AMOUNT_NUMERIC;
            } else if (entryCriteriaCode.equals("OVERLIMIT") && (overLimitAmountType.equals("PERCENTA") && (Double.parseDouble(entryOverLimitAmount) > 100))) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_OVER_LIMIT_PERCENTAGE_INVALID;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && entryOverDuePeriodCode.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_OVERDUE_PERIOD_EMPTY;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && overDueAmountType.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.OVERDUE_AMOUNT_TYPE_EMPTY;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && (entryOverDueAmount.contentEquals("") || entryOverDueAmount.length() <= 0)) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_OVERDUE_AMOUNT_EMPTY;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && !UserInputValidator.isDecimalOrNumeric(entryOverDueAmount,"10","2")) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_OVERDUE_AMOUNT_NUMERIC;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && (overDueAmountType.equals("PERCENTA") && (Double.parseDouble(entryOverDueAmount) > 100))) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_OVERDUE_PERCENTAGE_INVALID;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && overLimitAmountType.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.OVER_LIMIT_AMOUNT_TYPE_EMPTY;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && (entryOverLimitAmount.contentEquals("") || entryOverLimitAmount.length() <= 0)) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_OVER_LIMIT_AMOUNT_EMPTY;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && !UserInputValidator.isDecimalOrNumeric(entryOverLimitAmount, "10", "2")) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_OVER_LIMIT_AMOUNT_NUMERIC;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && (overLimitAmountType.equals("PERCENTA") && (Double.parseDouble(entryOverLimitAmount) > 100))) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_OVER_LIMIT_PERCENTAGE_INVALID;
            } else if (entryAccStatusCode.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_ACCOUNT_STATUS_EMPTY;
            } else if (entryCardStatusCode.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_ACCOUNT_STATUS_EMPTY;
            } else if (entryCriteriaCode.equals("OVERDUE") && (exitOverDueAmount.contentEquals("") || exitOverDueAmount.length() <= 0)) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_OVERDUE_AMOUNT_EMPTY;
            } else if (entryCriteriaCode.equals("OVERDUE") && !UserInputValidator.isDecimalOrNumeric(exitOverDueAmount, "10", "2")) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_OVERDUE_AMOUNT_NUMERIC;
            } else if (entryCriteriaCode.equals("OVERDUE") && (overDueAmountType.equals("PERCENTA") && (Double.parseDouble(exitOverDueAmount) > 100))) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_OVERDUE_PERCENTAGE_INVALID;
            } else if (entryCriteriaCode.equals("OVERDUE") && exitOverDuePeriodCode.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_OVERDUE_PERIOD_EMPTY;
            } else if (entryCriteriaCode.equals("OVERLIMIT") && (exitOverLimitAmount.contentEquals("") || exitOverLimitAmount.length() <= 0)) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_OVER_LIMIT_AMOUNT_EMPTY;
            } else if (entryCriteriaCode.equals("OVERLIMIT") && !UserInputValidator.isDecimalOrNumeric(exitOverLimitAmount, "10", "2")) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_OVER_LIMIT_AMOUNT_NUMERIC;
            } else if (entryCriteriaCode.equals("OVERLIMIT") && (overLimitAmountType.equals("PERCENTA") && (Double.parseDouble(exitOverLimitAmount) > 100))) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_OVER_LIMIT_PERCENTAGE_INVALID;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && (exitOverDueAmount.contentEquals("") || exitOverDueAmount.length() <= 0)) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_OVERDUE_AMOUNT_EMPTY;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && !UserInputValidator.isDecimalOrNumeric(exitOverDueAmount,"10","2")) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_OVERDUE_AMOUNT_NUMERIC;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && (overDueAmountType.equals("PERCENTA") && (Double.parseDouble(exitOverDueAmount) > 100))) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_OVERDUE_PERCENTAGE_INVALID;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && exitOverDuePeriodCode.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_OVERDUE_PERIOD_EMPTY;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && (exitOverLimitAmount.contentEquals("") || exitOverLimitAmount.length() <= 0)) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_OVER_LIMIT_AMOUNT_EMPTY;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && !UserInputValidator.isDecimalOrNumeric(exitOverLimitAmount,"10","2")) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_OVER_LIMIT_AMOUNT_NUMERIC;
            } else if ((entryCriteriaCode.equals("OVERDUEANDOVERLIMIT")|| entryCriteriaCode.equals("OVERDUEOROVERLIMIT")) && (overLimitAmountType.equals("PERCENTA") && (Double.parseDouble(exitOverLimitAmount) > 100))) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_OVER_LIMIT_PERCENTAGE_INVALID;
            } else if (exitAccStatusCode.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_ACCOUNT_STATUS_EMPTY;
            } else if (exitCardStatusCode.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.EXIT_CARD_STATUS_EMPTY;
            } else if (processAutomatedStatus.contentEquals("") && processManualStatus.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.ENTRY_ACCOUNT_STATUS_EMPTY;
            }

            return isValidate;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean insertCaseType(CaseTypeBean caseTypeBean) throws Exception {
        caseMgtManager = new CaseMgtManager();
        boolean success;
        success = caseMgtManager.insertCaseType(caseTypeBean,systemAuditBean);
        return success;
    }
    
    private void getAllCases() throws Exception {
        caseMgtManager = new CaseMgtManager ();
        caseList = new ArrayList<CaseTypeBean>();
        caseList = caseMgtManager.getAllCases();
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
            String uniqueId = request.getParameter(caseTypeBean.getCaseTypeCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add case type. Case Type Code: '" + caseTypeBean.getCaseTypeCode() + "' by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.CASE_MANAGEMENT);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
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
}
