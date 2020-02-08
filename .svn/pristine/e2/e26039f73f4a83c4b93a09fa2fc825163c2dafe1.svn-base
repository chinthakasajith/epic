/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BankBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.BankMgtManager;
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
 * @author badrika
 */
public class UpdateBankMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/administrator/controlpanel/systemconfigmgt/bankmgt.jsp";
    private SystemUserManager systemUserManager = null;
    private BankMgtManager bankMgtManager;
    private List<BankBean> banklist;
    private BankBean bean;
    private SystemAuditBean systemAuditBean;
    private String errorMessage;
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

        try {
            request.setAttribute("operationtype", "update");

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

            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.UPDATE)) {
                throw new AccessDeniedException();
            }

            //////////////////////////////////////////
            this.getBankDetails();
            request.setAttribute("banklist", banklist);

            String oldValue = request.getParameter("oldvalue");
            this.setValuesToBean(request);
            

            ///validation          

            int success = -1;
            if (this.validateUserInput(bean)) {

                this.setAudittraceValue(request, oldValue, newValue);

                try {
                    success = this.updateBank();

                } catch (Exception ex) {
                    throw ex;
                }

            } else {
                request.setAttribute("bankBean", bean);
                request.setAttribute("errorMsg", errorMessage);
            }
            // // // validation 

            if (success > 0) {

                this.getBankDetails();
                request.setAttribute("banklist", banklist);
                rd = request.getRequestDispatcher("/LoadBankMgtServlet");
                request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.BANK_UPDATE_SUCCESS + " Code: " + bean.getBankCode());

            } else {
                request.setAttribute("bankBean", bean);
                rd = request.getRequestDispatcher(url);
            }

            //////////////////////

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadBankMgtServlet");
            //rd.include(request, response);
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

    /**
     * to check the validity of the task to the logged user
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

    /**
     * to take all bank details to the user view
     * @throws Exception 
     */
    private void getBankDetails() throws Exception {
        try {

            bankMgtManager = new BankMgtManager();
            banklist = bankMgtManager.getBankNames();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to set the values taken by user to bean
     * @param request
     * @throws Exception 
     */
    private void setValuesToBean(HttpServletRequest request) throws Exception {
        try {
            bean = new BankBean();
            bean.setBankCode(request.getParameter("bankCode"));
            bean.setBankName(request.getParameter("bankName"));
            bean.setStatusDes(request.getParameter("statuscode"));
            //////////////////////////////////////////
            bean.setLastUpdatedDate(new Date());
            bean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());

            newValue = bean.getBankCode() + "|" + bean.getBankName() + "|" + bean.getStatusDes(); 


        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * to update bank details
     * @return
     * @throws Exception 
     */
    public int updateBank() throws Exception {
        try {
            bankMgtManager = new BankMgtManager();
            int i = bankMgtManager.UpdateBank(systemAuditBean, bean);
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * to set audit trace values
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request, String old, String newV) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("bankCode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Update Bank. Bank Code: " + uniqueId + "; by:  " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.BANK);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue(old);
            systemAuditBean.setNewValue(newV);

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());


        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to validate user inputs
     * @param bean
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(BankBean bean) throws Exception {
        boolean isValidate = true;

        try {

            if (bean.getBankCode().contentEquals("") || bean.getBankCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BANK_CODE_EMPTY;
            } else if (!UserInputValidator.isAlphaNumeric(bean.getBankCode())) {
                isValidate = false;
                errorMessage = MessageVarList.BANK_CODE_ALPHANUMERIC;
            } else if (bean.getBankCode().contentEquals("") || bean.getBankCode().length() > 6) {
                isValidate = false;
                errorMessage = MessageVarList.BANK_CODE_LENGTH;
            } else if (bean.getBankName().contentEquals("") || bean.getBankName().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BANK_NAME_EMPTY;
            } else if (!UserInputValidator.isDescription(bean.getBankName())) {
                isValidate = false;
                errorMessage = MessageVarList.BANK_NAME_ALPHANUMERIC;
            } else if (bean.getStatusDes().contentEquals("") || bean.getStatusDes().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BANK_STATUS_EMPTY;
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UpdateBankMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UpdateBankMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
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
