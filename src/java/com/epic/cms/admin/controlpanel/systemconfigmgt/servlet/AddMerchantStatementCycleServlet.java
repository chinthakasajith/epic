/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.MerchantStatementCycleBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.MerchantStatementCycleManager;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AddMerchantStatementCycleServlet extends HttpServlet {

    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private Boolean successInsert;
    private List<String> userTaskList; 
    private String errorMessage = null;
    private SessionUser sessionUser = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    //------------------------------------------------------   
    private MerchantStatementCycleBean billBean;
    private MerchantStatementCycleManager cycleMgr;
    private List<MerchantStatementCycleBean> billingList;
    String url = "/administrator/controlpanel/systemconfigmgt/merchantstatementcyclehome.jsp";
    private String newValue;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            try {
                sessionObj = request.getSession(false);
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

            try {
                //set page code and task codes
                String pageCode = PageVarList.MRCHNT_STATEMENT_CYCLE_MGT;
                String taskCode = TaskVarList.ADD;


                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        //assign user input to the bean
                        setUserInputToBean(request);
                        //if user inputs are valid
                        if (validateUserInput(billBean)) {
                            request.setAttribute("operationtype", "add");
                            this.setAudittraceValue(request);
                            try {
                                successInsert = insertNewBillCycle(billBean);
                                if (successInsert) {
                                    request.setAttribute("successMsg", MessageVarList.SUCCESS_ADD + "'" + billBean.getStateCycleCode() + "' Statement Cycle Code ");
                                } else {
                                    throw new Exception();
                                }

                            } catch (SQLException e) {
                                //show the messages which has thrown when inserting
                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("errorMsg", oraMessage);
                            }
                            
                            this.getAllBillingData();
                            request.setAttribute("billingList", billingList);
                            rd = getServletContext().getRequestDispatcher(url);

                        } else {
                            //if the user inputs are invalid go to the home page with an error message
                            this.getAllBillingData();
                            request.setAttribute("billingList", billingList);
                            request.setAttribute("stateBean", billBean);
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute("operationtype", "add");
                            rd = getServletContext().getRequestDispatcher(url);

                        }

                        rd.forward(request, response);

                    } catch (Exception e) {
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }
                } else {

                    throw new AccessDeniedException();
                }
                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);
            } catch (AccessDeniedException adex) {
                //if user_role doesn't have required access to the page throw Access Denied exception
                throw adex;
            }

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
        }
    }

    /**
     * validate user inputs
     * @param bill
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(MerchantStatementCycleBean bill) throws Exception {

        boolean isValidate = true;
        try {
            if (bill.getStateCycleCode().contentEquals("") || bill.getStateCycleCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CYCLE_MGT_BILLCODE_NULL;
            } else if (!UserInputValidator.isAlphaNumeric(bill.getStateCycleCode())) {
                isValidate = false;
                errorMessage = MessageVarList.CYCLE_MGT_BILLCODE_INVALID;
            } else if (bill.getStateOption().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.STATEMENT_OPTION_NULL;
            } else if (!billBean.getStateOption().equals("1")) {

                if (bill.getStateDate().contentEquals("")) {
                    isValidate = false;
                    errorMessage = MessageVarList.BILL_CYCLE_MGT_BILLDATE_NULL;
                }
            } if(bill.getStateDescription().contentEquals("") || bill.getStateDescription().length() <= 0 && isValidate) {
                isValidate = false;
                errorMessage = MessageVarList.BILL_CYCLE_MGT_BILLDESCRIPTION_NULL;
            } else if (bill.getHolidayAction() == null) {
                isValidate = false;
                errorMessage = MessageVarList.BILL_CYCLE_MGT_HOLIACT_NULL;
            } else if (bill.getStateStatus().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.STATUS_EMPTY;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
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
            String uniqueId = request.getParameter(billBean.getStateCycleCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Merchant Statement Cycle. Merchant Statement Cycle Code: '" + billBean.getStateCycleCode() + "' by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.MRCHNT_STATEMENT_CYCLE_MGT);
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
     * to check whether user has required access to the page
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
     * to retrieve all billing cycle data
     * @throws Exception 
     */
    private void getAllBillingData() throws Exception {
        try {

            cycleMgr = new MerchantStatementCycleManager();
            billingList = cycleMgr.getAllBillingData();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to set user input to the bean
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            billBean = new MerchantStatementCycleBean();

            billBean.setStateCycleCode(request.getParameter("billCycleCode"));
            billBean.setStateOption(request.getParameter("option"));
            if (!billBean.getStateOption().equals("1")) {
                billBean.setStateDate(request.getParameter("date"));
            } else {
                billBean.setStateDate(null);
            }
            billBean.setStateDescription(request.getParameter("description"));
            billBean.setHolidayAction(request.getParameter("holidayAct"));
            billBean.setStateStatus(request.getParameter("status"));
            billBean.setLastUpdatedUser(sessionUser.getUserName());

            newValue = billBean.getStateCycleCode() + "|" + billBean.getStateOption() + "|" + 
                    billBean.getStateDescription() + "|" + billBean.getHolidayAction() + "|" + billBean.getStateStatus() + "|"  + billBean.getLastUpdatedUser();
            
        } catch (Exception e) {
            status = false;
            throw e;

        }

        return status;
    }

    /**
     * to insert one billing cycle record
     * @param bill
     * @return
     * @throws Exception 
     */
    public Boolean insertNewBillCycle(MerchantStatementCycleBean bill) throws Exception {
        boolean success = false;
        try {
            cycleMgr = new MerchantStatementCycleManager();
            success = cycleMgr.insertNewBillCycle(bill, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddMerchantStatementCycleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddMerchantStatementCycleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
