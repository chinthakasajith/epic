/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.StandingOrderTypesBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.StandingOrderTypesManager;
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
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
public class UpdateStandingOrderTypesServlet extends HttpServlet {

    String oldValue = "";
    String newValue = "";
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private String errorMessage = null;
    private Boolean successUpdate;

    private StandingOrderTypesBean standingOrderTypesBean;
    private String url = "/administrator/controlpanel/systemconfigmgt/standingordertypeshome.jsp";
    private StandingOrderTypesManager standingOrderTypesManager = null;
    private List<StandingOrderTypesBean> standingOrderList;

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
                String pageCode = PageVarList.STANDING_ORDER_TYPES;
                String taskCode = TaskVarList.UPDATE;

                //check whether user_role have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    request.setAttribute("operationtype", "update");
                    try {
                        if (setUserInputToBean(request)) {
                            //validate user input
                            if (validateUserInput(standingOrderTypesBean)) {

                                //set values to the audit trace
                                this.setAudittraceValue(request);
                                //update standing order type
                                successUpdate = this.updateStandingOrderType(standingOrderTypesBean, systemAuditBean);
                                if (successUpdate) {
                                    request.setAttribute("operationtype", "add");
                                    request.setAttribute("successMsg", "Successfully updated Standing Order ID '" + standingOrderTypesBean.getOrderID() + "'");
                                } else {
                                    throw new Exception();
                                }

                            } else {
                                request.setAttribute("standingOrderTypesBean", standingOrderTypesBean);
                                request.setAttribute("errorMsg", errorMessage);
                                request.setAttribute("operationtype", "update");
                            }

                        } else {

                            request.setAttribute("operationtype", "update");
                            request.setAttribute("standingOrderTypesBean", standingOrderTypesBean);
                            request.setAttribute("errorMsg", errorMessage);

                        }

                        this.getAllStandingOrderData();
                        request.setAttribute("standingOrderList", standingOrderList);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);

                    } catch (Exception e) {

                        request.setAttribute("operationtype", "update");
                        this.getAllStandingOrderData();
                        request.setAttribute("standingOrderList", standingOrderList);
                        request.setAttribute("standingOrderTypesBean", standingOrderTypesBean);
                        request.setAttribute("addValidation", "fail");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);

                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
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
            request.setAttribute("operationtype", "update");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            out.close();
        }
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

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            standingOrderTypesBean = new StandingOrderTypesBean();

            standingOrderTypesBean.setOrderID(request.getParameter("orderid"));
            standingOrderTypesBean.setOrderName(request.getParameter("orderName"));
            //standingOrderTypesBean.setFlatFee(request.getParameter("flatFee"));
            //standingOrderTypesBean.setFlatFeePercentage(request.getParameter("percentage"));
            //standingOrderTypesBean.setFeeOption(request.getParameter("feeOption"));
            standingOrderTypesBean.setCurrencyType(request.getParameter("currency"));
            standingOrderTypesBean.setMaxTransactionAmt(request.getParameter("maxTranAmt"));
            standingOrderTypesBean.setMinTransactionAmt(request.getParameter("minTranAmt"));
            standingOrderTypesBean.setStatusCode(request.getParameter("standingOrderStatus"));
            standingOrderTypesBean.setBankCode(request.getParameter("bank"));
            standingOrderTypesBean.setBranchCode(request.getParameter("branchNames"));
            standingOrderTypesBean.setAccNumber(request.getParameter("accNum"));
            //.setPaymenyType(request.getParameter("paymentType"));
            //standingOrderTypesBean.setAddress1(request.getParameter("address1"));
            //standingOrderTypesBean.setAddress2(request.getParameter("address2"));
            //standingOrderTypesBean.setCity(request.getParameter("city")); 
            standingOrderTypesBean.setContactPerson(request.getParameter("contactPerson"));
            standingOrderTypesBean.setContactNumber(request.getParameter("contactNo"));
            standingOrderTypesBean.setCategory(request.getParameter("category"));
            standingOrderTypesBean.setUtilityProvider(request.getParameter("utilityProvider"));
            standingOrderTypesBean.setFeeType(request.getParameter("feeType"));
            standingOrderTypesBean.setLastUpdatedUser(sessionUser.getUserName());

            oldValue = request.getParameter("oldValue");
            newValue = standingOrderTypesBean.getOrderID() + "|" + standingOrderTypesBean.getOrderName() + "|"
                    + "" + standingOrderTypesBean.getCategory() + "|" + standingOrderTypesBean.getUtilityProvider() + "|"
                    + "" + standingOrderTypesBean.getAccNumber() + "|" + standingOrderTypesBean.getBankCode() + "|"
                    + "" + standingOrderTypesBean.getBranchCode() + "|" + standingOrderTypesBean.getContactPerson() + "|"
                    + "" + standingOrderTypesBean.getContactNumber() + "|" + standingOrderTypesBean.getFeeType() + "|"
                    + "" + standingOrderTypesBean.getCurrencyType() + "|" + standingOrderTypesBean.getMinTransactionAmt() + "|"
                    + "" + standingOrderTypesBean.getMaxTransactionAmt() + "|" + standingOrderTypesBean.getStatusCode();

        } catch (Exception e) {
            status = false;
            throw e;

        }

        return status;
    }

    public boolean validateUserInput(StandingOrderTypesBean bean) throws Exception {

        boolean isValidate = true;
        try {
            if (bean.getOrderID().contentEquals("") || bean.getOrderID().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.STANDING_ORDER_ID_NULL;
            } else if (!UserInputValidator.isAlphaNumeric(bean.getOrderID())) {
                isValidate = false;
                errorMessage = MessageVarList.INVALID_ORDER_ID;
            } else if (bean.getOrderName().contentEquals("") || bean.getOrderName().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.STANDING_ORDER_NAME_NULL;
            } else if (!UserInputValidator.isAlphaNumericWithSpace(bean.getOrderName())) {
                isValidate = false;
                errorMessage = MessageVarList.INVALID_ORDER_NAME;
            } else if (bean.getCategory().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.STANDING_ORDER_CATEGORY_NULL;
            } else if (bean.getCategory().equals("UTILITY")) {
                if (bean.getUtilityProvider().contentEquals("")) {
                    isValidate = false;
                    errorMessage = MessageVarList.UTILITY_PROVIDER_NULL;
                } else if (bean.getAccNumber().contentEquals("") || bean.getAccNumber().length() <= 0) {
                    isValidate = false;
                    errorMessage = MessageVarList.STANDING_ORDER_ACC_NUMBER;
                } else if (!UserInputValidator.isAlphaNumeric(bean.getAccNumber())) {
                    isValidate = false;
                    errorMessage = MessageVarList.INVALID_ACC_NUMBER;
                } else if (bean.getBranchCode().contentEquals("")) {
                    isValidate = false;
                    errorMessage = MessageVarList.STANDING_ORDER_BANK_BRANCH_NAME_NULL;
                } else if (!bean.getContactPerson().isEmpty() && !UserInputValidator.isAlpha(bean.getContactPerson())) {
                    isValidate = false;
                    errorMessage = MessageVarList.STANDING_ORDER_CONTACT_PERSON_ALPHA;
                } else if (!bean.getContactNumber().isEmpty() && !UserInputValidator.isPhoneNumber(bean.getContactNumber())) {
                    isValidate = false;
                    errorMessage = MessageVarList.STANDING_ORDER_CONTACT_PERSON_NUMBER_LENGHT;
                } else if (bean.getFeeType().contentEquals("")) {
                    isValidate = false;
                    errorMessage = MessageVarList.STANDING_ORDER_FEE_TYPE_NULL;
                } else if (bean.getCurrencyType().contentEquals("")) {
                    isValidate = false;
                    errorMessage = MessageVarList.CURRENCY_TYPE_NULL;
                } else if (bean.getMinTransactionAmt().contentEquals("") || bean.getMinTransactionAmt().length() <= 0) {
                    isValidate = false;
                    errorMessage = MessageVarList.MIN_TRANSACTION_AMOUNT_NULL;
                } else if (!UserInputValidator.isDecimal_Numeric(bean.getMinTransactionAmt())) {
                    isValidate = false;
                    errorMessage = MessageVarList.MIN_TRANSACTION_AMOUNT_DECIMAL_NUMERIC;
                } else if (bean.getMaxTransactionAmt().contentEquals("") || bean.getMaxTransactionAmt().length() <= 0) {
                    isValidate = false;
                    errorMessage = MessageVarList.MAX_TRANSACTION_AMOUNT_NULL;
                } else if (!UserInputValidator.isDecimal_Numeric(bean.getMaxTransactionAmt())) {
                    isValidate = false;
                    errorMessage = MessageVarList.MAX_TRANSACTION_AMOUNT_DECIMAL_NUMERIC;
                } else if (bean.getStatusCode().contentEquals("")) {
                    isValidate = false;
                    errorMessage = MessageVarList.STATUS_EMPTY;
                } else if (Double.parseDouble(bean.getMaxTransactionAmt()) < Double.parseDouble(bean.getMinTransactionAmt())) {
                    isValidate = false;
                    errorMessage = MessageVarList.STANDING_ORDER_MAX_MIN_TRANSACTION_AMT_DIFF_ERROR;
                }
            } else if (bean.getCategory().equals("CRDPYMNT")) {
                if (bean.getFeeType().contentEquals("")) {
                    isValidate = false;
                    errorMessage = MessageVarList.STANDING_ORDER_FEE_TYPE_NULL;
                } else if (bean.getCurrencyType().contentEquals("")) {
                    isValidate = false;
                    errorMessage = MessageVarList.CURRENCY_TYPE_NULL;
                } else if (bean.getMinTransactionAmt().contentEquals("") || bean.getMinTransactionAmt().length() <= 0) {
                    isValidate = false;
                    errorMessage = MessageVarList.MIN_TRANSACTION_AMOUNT_NULL;
                } else if (!UserInputValidator.isDecimal_Numeric(bean.getMinTransactionAmt())) {
                    isValidate = false;
                    errorMessage = MessageVarList.MIN_TRANSACTION_AMOUNT_DECIMAL_NUMERIC;
                } else if (bean.getMaxTransactionAmt().contentEquals("") || bean.getMaxTransactionAmt().length() <= 0) {
                    isValidate = false;
                    errorMessage = MessageVarList.MAX_TRANSACTION_AMOUNT_NULL;
                } else if (!UserInputValidator.isDecimal_Numeric(bean.getMaxTransactionAmt())) {
                    isValidate = false;
                    errorMessage = MessageVarList.MAX_TRANSACTION_AMOUNT_DECIMAL_NUMERIC;
                } else if (bean.getStatusCode().contentEquals("")) {
                    isValidate = false;
                    errorMessage = MessageVarList.STATUS_EMPTY;
                } else if (Double.parseDouble(bean.getMaxTransactionAmt()) < Double.parseDouble(bean.getMinTransactionAmt())) {
                    isValidate = false;
                    errorMessage = MessageVarList.STANDING_ORDER_MAX_MIN_TRANSACTION_AMT_DIFF_ERROR;
                }
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = standingOrderTypesBean.getOrderID();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update standing order type. Standing order type ID: '" + standingOrderTypesBean.getOrderID() + "' by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.STANDING_ORDER_TYPES);
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

    private Boolean updateStandingOrderType(StandingOrderTypesBean standingOrderTypesBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;

        standingOrderTypesManager = new StandingOrderTypesManager();
        success = standingOrderTypesManager.updateStandingOrderType(standingOrderTypesBean, systemAuditBean);

        return success;
    }

    private void getAllStandingOrderData() throws Exception {
        try {
            standingOrderTypesManager = new StandingOrderTypesManager();
            standingOrderList = standingOrderTypesManager.getAllStandingOrderData();

        } catch (Exception ex) {
            throw ex;
        }
    }
}
