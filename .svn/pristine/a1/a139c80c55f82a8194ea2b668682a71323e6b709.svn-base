/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.HotlistReasonBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.HotlistReasonMgtManager;
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
public class AddHotlistReasonMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/administrator/controlpanel/systemconfigmgt/hotlistreasonmgt.jsp";
    private SystemUserManager systemUserManager = null;
    private HotlistReasonBean reasonBean;
    private List<HotlistReasonBean> reasonList;
    private SystemAuditBean systemAuditBean;
    private String errorMessage = null;
    private HotlistReasonMgtManager reasonMgtManager;
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

            this.getHotlistReasons();
            request.setAttribute("reasonList", reasonList);

            this.setValuesToBean(request);

            // // // validation 
            int success = -1;
            if (this.validateUserInput(reasonBean)) {

                this.setAudittraceValue(request);

                try {
                    success = this.addNewHotlistReason();

                } catch (Exception ex) {
                    throw ex;
                }

            } else {
                request.setAttribute("reasonBean", reasonBean);
                request.setAttribute("errorMsg", errorMessage);
            }
            // // // 

            if (success > 0) {

                this.getHotlistReasons();
                request.setAttribute("reasonList", reasonList);

                request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.REASON_ADD_SUCCESS);
            }

            rd = request.getRequestDispatcher(url);

            ///////////////////////////////

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadHotlistReasonMgtServlet");

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
     * to get hot list reason list
     * @throws Exception 
     */
    private void getHotlistReasons() throws Exception {
        try {

            reasonMgtManager = new HotlistReasonMgtManager();
            reasonList = reasonMgtManager.getHotlistReasons();

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
            reasonBean = new HotlistReasonBean();
            reasonBean.setReasonCode(request.getParameter("reasonCode"));
            reasonBean.setDescription(request.getParameter("description"));
            reasonBean.setStatusDes(request.getParameter("statusdes"));
            reasonBean.setLastUpdatedTime(new Date());
            reasonBean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            
            newValue = reasonBean.getReasonCode() + "|" + reasonBean.getDescription() + "|" + reasonBean.getStatusDes() + 
                    "|" + reasonBean.getLastUpdatedTime() + "|" + reasonBean.getLastUpdatedUser();
        } catch (Exception e) {
            throw e;
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
     * to add new record
     * @return
     * @throws Exception 
     */
    public int addNewHotlistReason() throws Exception {
        try {
            reasonMgtManager = new HotlistReasonMgtManager();
            int i = reasonMgtManager.addNewHotlistReason(systemAuditBean, reasonBean);
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * to validate user inputs
     * @param bean
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(HotlistReasonBean bean) throws Exception {
        boolean isValidate = true;

        try {

            if (bean.getReasonCode().contentEquals("") || bean.getReasonCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.REASON_CODE_EMPTY;
            } else if (!UserInputValidator.isAlphaNumeric(bean.getReasonCode())) {
                isValidate = false;
                errorMessage = MessageVarList.REASON_CODE_ALPHANUMERIC;
            } else if (bean.getReasonCode().contentEquals("") || bean.getReasonCode().length() > 6) {
                isValidate = false;
                errorMessage = MessageVarList.REASON_CODE_LENGTH;
            } else if (bean.getDescription().contentEquals("") || bean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.DESCRIPTION_EMPTY;
            } else if (!UserInputValidator.isDescription(bean.getDescription())) {
                isValidate = false;
                errorMessage = MessageVarList.DESCRIPTION_INVALID;

            } else if (bean.getStatusDes().contentEquals("") || bean.getStatusDes().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.REASON_STATUS_EMPTY;
            }

        } catch (Exception ex) {
            isValidate = false;
        }
        return isValidate;
    }

    /**
     * to set the audit trace values
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("reasonCode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Add Hot List Reason. Reason Code: " + uniqueId + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.HOTLISTREASON);
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
