/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardrequest.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BankBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.BankMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.backoffice.installement.btpayment.bean.BTPaymentPlanBean;
import com.epic.cms.backoffice.installement.btpayment.businesslogic.BTPaymentPlanManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BTPaymentRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.businesslogic.BTPaymentRequestManager;
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
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sajith_g
 */
public class AddBTPaymentRequestServlet extends HttpServlet {

    //initialize variables
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private SystemUserManager sysUserMgr;
    private SystemAuditBean systemAuditBean = null;
    String errorMessage = "";
    List<BulkCardRequestBean> reqList = null;
    List<CurrencyBean> currency = null;
    HashMap<String, String> cdProduct = null;
    private boolean successInsert;
    private SystemUserBean sysUsrBean = null;
    private BTPaymentRequestBean btPaymentRequestBean;
    private HashMap<String, String> cardDomain;
    private BTPaymentRequestManager btPaymentRequestManager = null;
    private BTPaymentPlanManager btpaymentPlanManager = null;
    private List<BTPaymentPlanBean> btPaymentPlanList;
    private BankMgtManager bankMgtManager;
    private List<BankBean> banklist;
    private String newValue = null;
    String url = "/callcenter/card/balancetranferpaymentrequest.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            HttpSession sessionObj = request.getSession(false);
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
                String pageCode = PageVarList.BALANCE_TRANSFER_PAYMENT_REQUEST;
                String taskCode = TaskVarList.ADD;

                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        request.setAttribute("operationtype", "add");
                        //assign user input to the bean
                        setUserInputToBean(request);

                        request.setAttribute("tempBean", btPaymentRequestBean);
                        this.getAllActivePaymentPlan();
                        this.getBankDetails();

                        request.setAttribute("banklist", banklist);

                        //validate user inputs
                        if (validateUserInput(btPaymentRequestBean)) {

                            this.setAudittraceValue(request);

                            try {
                                //insert the user given values to easy payment request table
                                successInsert = insertBTPayReq(btPaymentRequestBean);
                                if (successInsert) {
                                    request.setAttribute("successMsg", MessageVarList.PAYMENTPLAN_REQ_SUCCESS_ADD);
                                    rd = getServletContext().getRequestDispatcher("/LoadBTPaymentCardMgtServlet");
                                    rd.include(request, response);
                                }

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("errorMsg", oraMessage);
                                request.setAttribute("tempBean", btPaymentRequestBean);
                                request.setAttribute("banklist", banklist);
                                request.setAttribute("paymentPlanList", btPaymentPlanList);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);

                            }
                        } else {
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute("tempBean", btPaymentRequestBean);
                            request.setAttribute("banklist", banklist);
                            request.setAttribute("paymentPlanList", btPaymentPlanList);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);

                        }
                    } catch (Exception e) {
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("tempBean", btPaymentRequestBean);
                        request.setAttribute("paymentPlanList", btPaymentPlanList);
                        request.setAttribute("banklist", banklist);
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
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);

        } //catch session exception
        catch (NewLoginSessionException nlex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);
        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (Exception ex) {
        }
    }

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

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            //sequense id will be set in persistance layer

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());

            //set description 
            systemAuditBean.setDescription("Balance Transfer Payment Request added by : " + btPaymentRequestBean.getRequestedUser());
            systemAuditBean.setApplicationCode(ApplicationVarList.PRE_PERSONAL);
            systemAuditBean.setSectionCode(SectionVarList.PRE_PERSONAL_CD);
            systemAuditBean.setPageCode(PageVarList.BALANCE_TRANSFER_PAYMENT_REQUEST);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
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

    public boolean validateUserInput(BTPaymentRequestBean btPaymentRequestBean) throws Exception {
        boolean isValidate = true;
        try {
            if (btPaymentRequestBean.getOtherbankCardNumber().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.BTINSTALLMENT_CARDNO_NULL;
            } else if (!UserInputValidator.luhnValidate(btPaymentRequestBean.getOtherbankCardNumber())) {
                isValidate = false;
                errorMessage = MessageVarList.BTINSTALLMENT_CARDNO_INVALID;
            } else if (btPaymentRequestBean.getOtherbankCardExpiryMonth().contentEquals("") || btPaymentRequestBean.getOtherbankCardExpiryYear().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.BTINSTALLMENT_OTHERCARD_EXPIRYDATE_NULL;
            } else if (!UserInputValidator.isValidMonth(btPaymentRequestBean.getOtherbankCardExpiryMonth())) {
                isValidate = false;
                errorMessage = MessageVarList.BTINSTALLMENT_OTHERCARD_INVALIDMONTH;
            } else if (btPaymentRequestBean.getOtherbankNameOnCard().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.BTINSTALLMENT_OTHERCARDNAME_NULL;
            } else if (btPaymentRequestBean.getBankcode().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.BTINSTALLMENT_ISSUER_NULL;
            } else if (btPaymentRequestBean.getDueDate().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.BTINSTALLMENT_DUEDATE_NULL;
            } else if (btPaymentRequestBean.getTxnAmount() == null) {
                isValidate = false;
                errorMessage = MessageVarList.BTTXN_AMOUNT_NULL;
            } else if (btPaymentRequestBean.getPaymentPlan().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.BTPAYMENTPLAN_NULL;
            } else if (btPaymentRequestBean.getInstallmentAmount() == null) {
                isValidate = false;
                errorMessage = MessageVarList.BTINSTALLMENT_AMOUNT_NULL;
            } else if (!UserInputValidator.isDecimalNumeric(btPaymentRequestBean.getInstallmentAmount())) {
                isValidate = false;
                errorMessage = MessageVarList.BTINSTALLMENT_AMOUNT_INVALID;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            btPaymentRequestBean = new BTPaymentRequestBean();

            btPaymentRequestBean.setCardNumber(sessionVarlist.getCardNumber());
            btPaymentRequestBean.setOtherbankCardNumber(request.getParameter("othercardno"));
            btPaymentRequestBean.setOtherbankCardExpiryMonth(request.getParameter("expirymonth"));
            btPaymentRequestBean.setOtherbankCardExpiryYear(request.getParameter("expiryyear"));
            btPaymentRequestBean.setOtherbankNameOnCard(request.getParameter("othercardname"));
            btPaymentRequestBean.setBankcode(request.getParameter("bankcode"));
            btPaymentRequestBean.setDueDate(request.getParameter("duedate"));

            //convert txnAmount to two decimals
            DecimalFormat newFormat = new DecimalFormat("#0.00");
            Float tempAmount = null;
            Float txnAmount = null;
            String txnAmountReq = request.getParameter("txnamount");
            if (txnAmountReq != null && !txnAmountReq.contentEquals("")) {
                tempAmount = Float.parseFloat(request.getParameter("txnamount"));
                txnAmount = Float.valueOf(newFormat.format(tempAmount));
            }

            btPaymentRequestBean.setTxnAmount(txnAmount);

            btPaymentRequestBean.setPaymentPlan(request.getParameter("paymentPlanCode"));
            btPaymentRequestBean.setInstallmentAmount(request.getParameter("installmentAmount").trim());
            btPaymentRequestBean.setRequestedUser(sessionUser.getUserName());
            btPaymentRequestBean.setLastUpdatedUser(sessionUser.getUserName());
            btPaymentRequestBean.setStatus(StatusVarList.DA_REQUSET_INITIATE);
            btPaymentRequestBean.setCurrencyNumCode(StatusVarList.DEFAULT_CURRENCYNUMCODE);
            newValue = btPaymentRequestBean.getCardNumber() + "|" + btPaymentRequestBean.getOtherbankCardNumber() + "|" + btPaymentRequestBean.getOtherbankCardExpiryMonth() + "|" + btPaymentRequestBean.getOtherbankCardExpiryYear() + "|" + btPaymentRequestBean.getOtherbankNameOnCard() + "|" + btPaymentRequestBean.getDueDate() + "|" + btPaymentRequestBean.getTxnAmount() + "|" + btPaymentRequestBean.getPaymentPlan() + "|" + btPaymentRequestBean.getInstallmentAmount() + "|" + btPaymentRequestBean.getRequestedUser() + "|" + btPaymentRequestBean.getStatus() + "|" + btPaymentRequestBean.getLastUpdatedUser() + "|" + btPaymentRequestBean.getCurrencyNumCode();

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    public Boolean insertBTPayReq(BTPaymentRequestBean bean) throws Exception {
        boolean success = false;
        try {
            btPaymentRequestManager = new BTPaymentRequestManager();
            success = btPaymentRequestManager.insertBTPayReq(bean, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }

    private void getAllActivePaymentPlan() throws Exception {
        try {
            btpaymentPlanManager = new BTPaymentPlanManager();
            btPaymentPlanList = btpaymentPlanManager.getAllActivePaymentPlans();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getBankDetails() throws Exception {
        try {

            bankMgtManager = new BankMgtManager();
            banklist = bankMgtManager.getBankNames();

        } catch (Exception ex) {
            throw ex;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
