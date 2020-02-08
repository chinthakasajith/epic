/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.BillingStatementProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.BillingStatementProfileManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CardScoreManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.exception.ValidateException;
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
 * @author badrika
 */
public class AddBillingStatementServlet extends HttpServlet {

    private String url = "/administrator/controlpanel/profilemgt/billingstatementprofilemgt.jsp";
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private RequestDispatcher rd;
    private BillingStatementProfileManager billingStatementProfileManager;
    private List<BillingStatementProfileBean> billingStatementList;
    private BillingStatementProfileBean billingStmtProfbean;
    private String errorMessage;
    private SystemAuditBean systemAuditBean;
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

        request.setAttribute("operationtype", "add");
        try {
            try {
                HttpSession sessionObj = request.getSession(false);
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();
                try {
                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(),
                            sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }
                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();

                }
            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.ADD)) {
                throw new AccessDeniedException();
            }

            //////////////////////////////////////////////

            this.getBillingStatementProfDetails();
            request.setAttribute("billingStatementList", billingStatementList);



            this.setValuesToBean(request);



            // // // validation 
            int success = -1;

            if (this.validateUserInput(billingStmtProfbean)) {

                this.setAudittraceValue(request);

                try {
                    success = this.addBillingStatementProfile();


                } catch (Exception ex) {
                    throw ex;
                }

            } else {
                request.setAttribute("billingStmtProfbean", billingStmtProfbean);
                request.setAttribute("errorMsg", errorMessage);
            }

            // // // 

            if (success > 0) {

                this.getBillingStatementProfDetails();
                request.setAttribute("billingStatementList", billingStatementList);

                request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.PROFILE_ADD_SUCCESS);
            }

            rd = request.getRequestDispatcher(url);

            /////////////////////////////////////////////////////////////////////
        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadBillingStatementServlet");

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (ValidateException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR,
                    CardScoreManager.getScoreCardInstance().getErrorMsg());
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
        }
    }

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

    private void getBillingStatementProfDetails() throws Exception {
        try {

            billingStatementProfileManager = new BillingStatementProfileManager();
            billingStatementList = billingStatementProfileManager.getBillingStatementProfDetails();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setValuesToBean(HttpServletRequest request) throws Exception {
        try {
            billingStmtProfbean = new BillingStatementProfileBean();

            billingStmtProfbean.setProfileCode(request.getParameter("profileCode"));
            billingStmtProfbean.setDescription(request.getParameter("description"));
            billingStmtProfbean.setGracePeroid(request.getParameter("gracePeroid"));
            billingStmtProfbean.setMinimumDueFlatAmount(request.getParameter("flat"));
            billingStmtProfbean.setMinimumDuePercentage(request.getParameter("percentage"));
            billingStmtProfbean.setMinimumDueCombination(request.getParameter("combination"));
            billingStmtProfbean.setStatementGenerationStatus(request.getParameter("statement"));
            billingStmtProfbean.setConditionalBalance(request.getParameter("balance"));
            billingStmtProfbean.setConditionalCrOrDr(request.getParameter("CrOrDr"));
            billingStmtProfbean.setNumberOfActivity(request.getParameter("activity"));
            billingStmtProfbean.setStatus(request.getParameter("statuscode"));


            billingStmtProfbean.setLastUpdatedTime(new Date());
            billingStmtProfbean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            
            newValue = billingStmtProfbean.getProfileCode() + "|" + billingStmtProfbean.getDescription() + "|" + billingStmtProfbean.getGracePeroid() + "|"
                     + billingStmtProfbean.getMinimumDueFlatAmount() + "|" + billingStmtProfbean.getMinimumDuePercentage() +  "|"
                    + billingStmtProfbean.getMinimumDueCombination() + "|" + billingStmtProfbean.getStatementGenerationStatus() + "|"
                    + billingStmtProfbean.getStatus() + "|" + billingStmtProfbean.getLastUpdatedTime() + "|" + billingStmtProfbean.getLastUpdatedUser();
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean validateUserInput(BillingStatementProfileBean bean) throws Exception {
        boolean isValidate = true;

        try {

            if (bean.getProfileCode().contentEquals("") || bean.getProfileCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.PROFILE_CODE_EMPTY;
            } else if (!UserInputValidator.isAlphaNumeric(bean.getProfileCode())) {
                isValidate = false;
                errorMessage = MessageVarList.PROFILE_CODE_ALPHANUMERIC;
            } else if (bean.getProfileCode().contentEquals("") || bean.getProfileCode().length() > 8) {
                isValidate = false;
                errorMessage = MessageVarList.PROFILE_CODE_LENGTH;
            } else if (bean.getDescription().contentEquals("") || bean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.PROFILE_DESCRIPTION_EMPTY;
            } else if (!UserInputValidator.isDescription(bean.getDescription())) {
                isValidate = false;
                errorMessage = MessageVarList.PROFILE_DES_INVALID;
            } else if (bean.getGracePeroid().contentEquals("") || bean.getGracePeroid().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.GRACE_PERIOD_EMPTY;
            } else if (!UserInputValidator.isNumeric(bean.getGracePeroid())) {
                isValidate = false;
                errorMessage = MessageVarList.GRACE_PERIOD_NUM;
            } else if (bean.getMinimumDueFlatAmount().contentEquals("") || bean.getMinimumDueFlatAmount().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.MIN_DUE_FLAT_AMT_EMPTY;
            } else if (!UserInputValidator.isDecimalOrNumeric(bean.getMinimumDueFlatAmount(), "8", "2")) {
                isValidate = false;
                errorMessage = MessageVarList.BILLING_STMT_MIN_DUE_FLAT_INVALID;
            } else if (bean.getMinimumDuePercentage().contentEquals("") || bean.getMinimumDuePercentage().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.MIN_DUE_PERCENTAGE_EMPTY;
            } else if (!UserInputValidator.isDecimalOrNumeric(bean.getMinimumDuePercentage(), "2", "2")) {
                isValidate = false;
                errorMessage = MessageVarList.BILLING_STMT_MIN_DUE_PERC_INVALID;
            } else if (Double.parseDouble(bean.getMinimumDuePercentage()) <= 0 || Double.parseDouble(bean.getMinimumDuePercentage()) > 100) {
                isValidate = false;
                errorMessage = MessageVarList.BILLING_STMT_MIN_DUE_PERC_100;
            } else if (bean.getMinimumDueCombination() == null) {
                isValidate = false;
                errorMessage = MessageVarList.COMBINATION_EMPTY;
            } else if (bean.getStatementGenerationStatus() == null) {
                isValidate = false;
                errorMessage = MessageVarList.STATEMENT_STATUS_EMPTY;
            } else if (bean.getStatementGenerationStatus().equals("COND")) {
                if (bean.getConditionalBalance().equals("")) {
                    isValidate = false;
                    errorMessage = MessageVarList.BALANCE_EMPTY;
                } else if (!UserInputValidator.isDecimalOrNumeric(bean.getConditionalBalance(), "8", "2")) {
                    isValidate = false;
                    errorMessage = MessageVarList.BILLING_STMT_BALANCE_INVALID;
                } else if (bean.getConditionalCrOrDr().equals("")) {
                    isValidate = false;
                    errorMessage = MessageVarList.CR_DR_EMPTY;
                } else if (bean.getNumberOfActivity().equals("")) {
                    isValidate = false;
                    errorMessage = MessageVarList.ACTIVITY_EMPTY;
                } else if (!UserInputValidator.isNumeric(bean.getNumberOfActivity())) {
                    isValidate = false;
                    errorMessage = MessageVarList.ACTIVITY_ISNUM;
                } else if (bean.getStatus().equals("") || bean.getStatus().length() <= 0) {
                    isValidate = false;
                    errorMessage = MessageVarList.PROFILE_STATUS_EMPTY;
                }
            } else if (bean.getStatus().equals("") || bean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.PROFILE_STATUS_EMPTY;
            }

        } catch (Exception ex) {
            isValidate = false;
        }
        return isValidate;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("profileCode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Add Billing Statement Profile. Profile Code: " + uniqueId + "; by:  " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.PROFILEMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.BILLINGSTATEMENTPROFILE);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue("");
            systemAuditBean.setNewValue(newValue);

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    public int addBillingStatementProfile() throws Exception {
        try {
            billingStatementProfileManager = new BillingStatementProfileManager();
            int i = billingStatementProfileManager.addBillingStatementProfile(systemAuditBean, billingStmtProfbean);
            return i;
        } catch (Exception e) {
            throw e;
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
