/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CommonConfigurationBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BankBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CommonConfigurationManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CountryMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
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
import com.epic.cms.system.util.variable.RequestVarList;
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
 * @author ruwan_e
 */
public class UpdateCommonConfigurationServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String url = "/administrator/controlpanel/systemconfigmgt/commonconfig.jsp";
    private RequestDispatcher rd;
    private CommonConfigurationManager commonConfigurationManager;
    private CommonConfigurationBean bean;
    private SystemAuditBean systemAuditBean;
    private SystemAuditManager systemAuditManager;
    private SystemAuditBean systemAuditBean2;
    private String oldValue;
    private String newValue;
    private String errorMessage;

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

            SystemUserManager sysUserObj = new SystemUserManager();


            try {
                //set page code and task codes
                String pageCode = PageVarList.COMMON_CONFIG;
                String taskCode = TaskVarList.UPDATE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    try {
                        bean = new CommonConfigurationBean();

                        if (validateUserInput(request)) {

                            bean.setBank(request.getParameter(RequestVarList.CC_BANK));
                            bean.setBaseCurrency(request.getParameter(RequestVarList.CC_CURRENCY));
                            bean.setCountry(request.getParameter(RequestVarList.CC_COUNTRY));
                            bean.setAccumulationPointValue(Double.parseDouble(request.getParameter(RequestVarList.CC_ACCUMULATION_POINT_VALUE)));
                            bean.setRedemptionPointValue(Double.parseDouble(request.getParameter(RequestVarList.CC_REDEMPTION_POINT_VALUE)));
                            bean.setBatchTimeout(Integer.parseInt(request.getParameter(RequestVarList.CC_BATCH_TIMEOUT)));
                            bean.setMaxPerPaymentAmount(Double.parseDouble(request.getParameter(RequestVarList.CC_MAX_PER_PAYMENT_AMOUNT)));
                            bean.setMaxPerBatchAmount(Double.parseDouble(request.getParameter(RequestVarList.CC_MAX_PER_BATCH_AMOUNT)));
                            bean.setUserRole(request.getParameter(RequestVarList.CC_USER_ROLE));


                            newValue = bean.getBank() + "|" + bean.getBaseCurrency() + "|" + bean.getCountry() + "|" +bean.getUserRole()
                                    + "|" + bean.getAccumulationPointValue() + "|" + bean.getRedemptionPointValue() + "|" + bean.getBatchTimeout()
                                    + "|" + bean.getMaxPerPaymentAmount() + "|" + bean.getMaxPerBatchAmount();
                            commonConfigurationManager = CommonConfigurationManager.getInstance();
                            log(bean.getBank() + " " + bean.getBaseCurrency() + " " + bean.getCountry() +" "+bean.getUserRole());


                            CommonConfigurationBean commonConfiguration = CommonConfigurationManager.getInstance().getCommonConfiguration();

                            String bankName = "";
                            for (BankBean bean1 : CommonConfigurationManager.getInstance().getBankList()) {
                                if (bean1.getBankCode().equals(bean.getBank())) {
                                    bankName = bean1.getBankName();
                                }
                            }
                            String country = "";
                            for (CountryMgtBean bean1 : CommonConfigurationManager.getInstance().getCountryList()) {
                                if (bean1.getCountryCode().equals(bean.getCountry())) {
                                    country = bean1.getDescription();
                                }
                            }
                            String currency = "";
                            for (CurrencyBean bean1 : CommonConfigurationManager.getInstance().getCurrenyList()) {
                                if (bean1.getCurrencyCode().equals(bean.getBaseCurrency())) {
                                    currency = bean1.getCurrencyDes();
                                }
                            }
                            String dataCapturingRole="";
                            for(UserRoleBean bean1:CommonConfigurationManager.getInstance().getUserrolelist()){
                                if(bean1.getUserRoleCode().equals(bean.getUserRole())){
                                    dataCapturingRole=bean1.getDescription();
                                }
                            }

                            newValue = bankName + "|" + currency + "|" + country + "|"+dataCapturingRole
                                    + "|" + bean.getAccumulationPointValue() + "|" + bean.getRedemptionPointValue() + "|" + bean.getBatchTimeout()
                                    + "|" + bean.getMaxPerPaymentAmount() + "|" + bean.getMaxPerBatchAmount();

                            log(bean.getBank() +" "+bean.getBaseCurrency() + " " + bean.getCountry()+ " " + bean.getUserRole());

                            log(bean.getBank()+" "+bean.getBaseCurrency() + " " + bean.getCountry() + " " + bean.getUserRole());

                            if (commonConfigurationManager.isRecordAvailable()) {


                                String bankName1 = "";
                                for (BankBean bean : CommonConfigurationManager.getInstance().getBankList()) {
                                    if (bean.getBankCode().equals(commonConfiguration.getBank())) {
                                        bankName1 = bean.getBankName();
                                    }
                                }
                                String country1 = "";
                                for (CountryMgtBean bean : CommonConfigurationManager.getInstance().getCountryList()) {
                                    if (bean.getCountryCode().equals(commonConfiguration.getCountry())) {
                                        country1 = bean.getDescription();
                                    }
                                }
                                String currency1 = "";
                                for (CurrencyBean bean : CommonConfigurationManager.getInstance().getCurrenyList()) {
                                    if (bean.getCurrencyCode().equals(commonConfiguration.getBaseCurrency())) {
                                        currency1 = bean.getCurrencyDes();
                                    }
                                }
                                String dataCapturingRole1="";
                                for(UserRoleBean bean:CommonConfigurationManager.getInstance().getUserrolelist()){
                                    if(bean.getUserRoleCode().equals(commonConfiguration.getUserRole())){
                                        dataCapturingRole1=bean.getDescription();
                                    }
                                }

                                oldValue = bankName1 + "|" + currency1 + "|" + country1 + "|" +dataCapturingRole1 + "|"
                                        + commonConfiguration.getAccumulationPointValue() + "|" + commonConfiguration.getRedemptionPointValue() + "|" + commonConfiguration.getBatchTimeout()
                                        + "|" + commonConfiguration.getMaxPerPaymentAmount() + "|" + commonConfiguration.getMaxPerBatchAmount();

                                setAudittraceValueUpdate(request);
                                commonConfigurationManager.updateCommonConfiguration(bean, systemAuditBean);
                                request.setAttribute("successMsg", MessageVarList.SUCCESS_UPDATE_COMMON_PARAMETER);
                            } else {
                                setAudittraceValueInsert(request);
                                commonConfigurationManager.setCommonConfiguration(bean, systemAuditBean2);
                                request.setAttribute("successMsg", MessageVarList.SUCCESS_SET_COMMON_PARAMETER);
                            }

                            rd = getServletContext().getRequestDispatcher("/LoadCommonConfigurationsServlet");


                        } else {
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute(RequestVarList.CC_BANK_LIST, CommonConfigurationManager.getInstance().getBankList());
                            request.setAttribute(RequestVarList.CC_COUNTRY_LIST, CommonConfigurationManager.getInstance().getCountryList());
                            request.setAttribute(RequestVarList.CC_CURRENCY_LIST, CommonConfigurationManager.getInstance().getCurrenyList());
                            request.setAttribute(RequestVarList.COMMON_CONFIGURATION, CommonConfigurationManager.getInstance().getCommonConfiguration());
                            rd = getServletContext().getRequestDispatcher(url);
                        }





//
//                        request.setAttribute(RequestVarList.CC_BANK_LIST, CommonConfigurationManager.getInstance().getBankList());
//                        request.setAttribute(RequestVarList.CC_COUNTRY_LIST, CommonConfigurationManager.getInstance().getCountryList());
//                        request.setAttribute(RequestVarList.CC_CURRENCY_LIST, CommonConfigurationManager.getInstance().getCurrenyList());
//                        request.setAttribute(RequestVarList.COMMON_CONFIGURATION, CommonConfigurationManager.getInstance().getCommonConfiguration());




                        //    rd.forward(request, response);


                    } catch (Exception e) {
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        //  rd.forward(request, response);
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
            //  rd.forward(request, response);

        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            // rd.forward(request, response);

        } //catch access denied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            // rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            //rd.forward(request, response);
        } finally {
            rd.forward(request, response);
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

    private void setAudittraceValueUpdate(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());

            systemAuditBean.setDescription("Updated the COMMONPARAMETER table by " + sessionVarlist.getCMSSessionUser().getUserName());

            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.COMMON_CONFIG);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);

            systemAuditBean.setIp(request.getRemoteAddr());

            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue(oldValue);
            systemAuditBean.setNewValue(newValue);

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setAudittraceValueInsert(HttpServletRequest request) throws Exception {
        try {
            systemAuditBean2 = new SystemAuditBean();

            systemAuditBean2.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean2.setUserName(sessionVarlist.getCMSSessionUser().getUserName());

            systemAuditBean2.setDescription("Inserted the values to the COMMONPARAMETER table by " + sessionVarlist.getCMSSessionUser().getUserName());

            systemAuditBean2.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean2.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean2.setPageCode(PageVarList.COMMON_CONFIG);
            systemAuditBean2.setTaskCode(TaskVarList.ADD);

            systemAuditBean2.setIp(request.getRemoteAddr());

            systemAuditBean2.setFieldName("");
            systemAuditBean2.setOldValue("");
            systemAuditBean2.setNewValue(newValue);

            systemAuditBean2.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean validateUserInput(HttpServletRequest request) throws Exception {
        boolean isValidate = true;

        try {

            if (request.getParameter(RequestVarList.CC_ACCUMULATION_POINT_VALUE).equals("")) {
                isValidate = false;
                errorMessage = "Accumulation point value is required";
            } else if (!UserInputValidator.isDecimalOrNumeric(request.getParameter(RequestVarList.CC_ACCUMULATION_POINT_VALUE), "20", "2")) {
                isValidate = false;
                errorMessage = "Accumulation point value is invalid";

            } else if (request.getParameter(RequestVarList.CC_REDEMPTION_POINT_VALUE).equals("")) {
                isValidate = false;
                errorMessage = "Redemption point value is required";
            } else if (!UserInputValidator.isDecimalOrNumeric(request.getParameter(RequestVarList.CC_REDEMPTION_POINT_VALUE), "20", "2")) {
                isValidate = false;
                errorMessage = "Redemption point value is invalid";

            } else if (request.getParameter(RequestVarList.CC_BATCH_TIMEOUT).equals("")) {
                isValidate = false;
                errorMessage = "Payment batch timeout is required";
            } else if (!UserInputValidator.isNumeric(request.getParameter(RequestVarList.CC_BATCH_TIMEOUT))) {
                isValidate = false;
                errorMessage = "Payment batch timeout is invalid";

            } else if (request.getParameter(RequestVarList.CC_MAX_PER_PAYMENT_AMOUNT).equals("")) {
                isValidate = false;
                errorMessage = "Maximum amount per payment is required";
            } else if (!UserInputValidator.isDecimalOrNumeric(request.getParameter(RequestVarList.CC_MAX_PER_PAYMENT_AMOUNT), "20", "2")) {
                isValidate = false;
                errorMessage = "Maximum amount per payment is invalid";

            } else if (request.getParameter(RequestVarList.CC_MAX_PER_BATCH_AMOUNT).equals("")) {
                isValidate = false;
                errorMessage = "Maximum amount per batch is required";
            } else if (!UserInputValidator.isDecimalOrNumeric(request.getParameter(RequestVarList.CC_MAX_PER_BATCH_AMOUNT), "20", "2")) {
                isValidate = false;
                errorMessage = "Maximum amount per batch is invalid";

            }

        } catch (Exception ex) {
            isValidate = false;
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
