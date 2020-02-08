/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionProfileTransactionBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.TransactionProfileManager;
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
import java.sql.Timestamp;
import java.util.ArrayList;
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
public class UpdateTransactionProfileServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private RequestDispatcher rd;
    private String url = "/administrator/controlpanel/profilemgt/transactionprofilemgt.jsp";
    private TransactionProfileManager transactionProfileManager;
    private List<TransactionProfileBean> tranProfileList;
    private TransactionProfileBean transactionProfilebean;
    private String[] assignedList;
    private String errorMessage;
    private SystemAuditBean systemAuditBean;
    private List<TransactionProfileTransactionBean> assignedTransList, notassignedTransList;
    private String oldValue;
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
            this.getTransactionProfileDetails();
            request.setAttribute("tranProfileList", tranProfileList);

            assignedList = request.getParameterValues("assignlist");

            this.setValuesToBean(request);

            int success = -1;
            int successtran = -1;
            boolean successdel = false;

            String id = request.getParameter("profileCode");

            assignedTransList = new ArrayList<TransactionProfileTransactionBean>();
            notassignedTransList = new ArrayList<TransactionProfileTransactionBean>();

            transactionProfileManager = new TransactionProfileManager();

            assignedTransList = transactionProfileManager.getTransactionsByProfileCode(id);
            notassignedTransList = transactionProfileManager.getNotAssignedTrans(id);




            if (this.validateUserInput(transactionProfilebean, assignedList)) {

                this.setAudittraceValue(request);

                try {
                    success = this.updateProfile();
                    successdel = this.deleteTrans(transactionProfilebean.getProfileCode());
                    successtran = this.addTransactions();

                } catch (Exception ex) {
                    throw ex;
                }

            } else {
                request.setAttribute("transactionProfilebean", transactionProfilebean);
                request.setAttribute("assignedTransList", assignedTransList);
                request.setAttribute("notassignedTransList", notassignedTransList);
                request.setAttribute("errorMsg", errorMessage);
            }


            if (success > 0 && successtran > 0 && successdel) {

                this.getTransactionProfileDetails();
                request.setAttribute("tranProfileList", tranProfileList);

                request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.PROFILE_UPDATE_SUCCESS);
            }

            rd = request.getRequestDispatcher(url);




            //////////////////////

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadTransactionProfileServlet");
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

    private void getTransactionProfileDetails() throws Exception {

        try {
            transactionProfileManager = new TransactionProfileManager();
            tranProfileList = transactionProfileManager.getTransactionProfileDetails();

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void setValuesToBean(HttpServletRequest request) throws Exception {
        try {
            transactionProfilebean = new TransactionProfileBean();
            transactionProfilebean.setProfileCode(request.getParameter("profileCode"));
            transactionProfilebean.setDescription(request.getParameter("description"));
            transactionProfilebean.setStatus(request.getParameter("statuscode"));

            transactionProfilebean.setLastUpdatedTime(new Date());
            transactionProfilebean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());

            newValue = transactionProfilebean.getProfileCode() + "|" + transactionProfilebean.getDescription() + "|" + transactionProfilebean.getStatus() + "|" + new Timestamp(transactionProfilebean.getLastUpdatedTime().getTime()) + "|" + transactionProfilebean.getLastUpdatedUser();

            this.getTransactionProfileDetails();

            for (TransactionProfileBean bean : tranProfileList) {
                if (bean.getProfileCode().equals(transactionProfilebean.getProfileCode())) {
                    oldValue = bean.getProfileCode() + "|" + bean.getDescription() + "|" + bean.getStatus() + "|" + bean.getLastUpdatedTime() + "|" + transactionProfilebean.getLastUpdatedUser();
                }
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public boolean validateUserInput(TransactionProfileBean bean, String[] assigned) throws Exception {
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
            } else if (!UserInputValidator.isCorrectString(bean.getDescription())) {
                isValidate = false;
                errorMessage = MessageVarList.PROFILE_DES_INVALID;
            } else if (bean.getStatus().contentEquals("") || bean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.PROFILE_STATUS_EMPTY;
            } else if (assigned == null || assigned.length <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.TRAN_ASSIGN_LIST_EMPTY;
            }


        } catch (Exception ex) {
            isValidate = false;
        }
        return isValidate;
    }

    public int updateProfile() throws Exception {
        try {
            transactionProfileManager = new TransactionProfileManager();
            int i = transactionProfileManager.updateProfile(systemAuditBean, transactionProfilebean, assignedList);
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean deleteTrans(String id) throws Exception {
        try {
            transactionProfileManager = new TransactionProfileManager();
            boolean i = transactionProfileManager.deleteTrans(id);
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    public int addTransactions() throws Exception {
        try {
            transactionProfileManager = new TransactionProfileManager();
            int i = transactionProfileManager.addTransactions(transactionProfilebean.getProfileCode(), assignedList);
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("profileCode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Update Transaction Profile. Profile Code: " + uniqueId + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.PROFILEMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.TRANSACTIONPROFILE);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue(oldValue);
            systemAuditBean.setNewValue(newValue);

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());


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
