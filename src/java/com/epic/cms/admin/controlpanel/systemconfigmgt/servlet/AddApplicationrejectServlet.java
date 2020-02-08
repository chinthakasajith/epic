/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ApplicationRejectReasonBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.ApplicationRejectReasonManager;
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
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.sql.SQLException;
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
public class AddApplicationrejectServlet extends HttpServlet {

    private RequestDispatcher rd;
    private String errorMessage = null;
    private SystemAuditBean systemAuditBean;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/administrator/controlpanel/systemconfigmgt/applicationrejectreasonhome.jsp";
    private SystemUserManager systemUserManager = null;
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
            request.setAttribute(RequestVarList.APP_REJECT_REASON_ALLLIST, ApplicationRejectReasonManager.getInstance().getAllReason());
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



            ApplicationRejectReasonBean bean = new ApplicationRejectReasonBean();

            bean.setReasonCode(request.getParameter("reason"));
            bean.setDescription(request.getParameter("description"));
            bean.setLastUpdateUser(sessionVarlist.getCMSSessionUser().getUserName());
            
            newValue = bean.getReasonCode() + "|" + bean.getDescription() + "|" + bean.getLastUpdateUser();

            // validate user input
            if (validateUserInput(bean)) {

                if (ApplicationRejectReasonManager.getInstance().isValidToProcess(bean)) {

                    this.setAudittraceValue(request);

                    int row = ApplicationRejectReasonManager.getInstance().addAppRejectReason(systemAuditBean, bean);

                    if (row == 1) {
                        request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.APP_REJECT_ADD_SUCCESS);
                    }
                } else {

                    request.setAttribute(RequestVarList.APP_REJECT_REASON_DATABEAN, bean);
                    request.setAttribute(MessageVarList.JSP_ERROR, ApplicationRejectReasonManager.getInstance().getErrorMsg());
                }

                request.setAttribute(RequestVarList.APP_REJECT_REASON_ALLLIST, ApplicationRejectReasonManager.getInstance().getAllReason());
                rd = request.getRequestDispatcher(url);

            } else {

                request.setAttribute(RequestVarList.APP_REJECT_REASON_DATABEAN, bean);
                //request.setAttribute(MessageVarList.JSP_ERROR, ApplicationRejectReasonManager.getInstance().getErrorMsg());
                request.setAttribute("errorMsg", errorMessage);
                request.setAttribute(RequestVarList.APP_REJECT_REASON_ALLLIST, ApplicationRejectReasonManager.getInstance().getAllReason());
                rd = request.getRequestDispatcher(url);
            }

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadApplicationRejectServlet");
            rd.include(request, response);
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

    public boolean validateUserInput(ApplicationRejectReasonBean Bean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code

        try {

            if (Bean.getReasonCode().contentEquals("") || Bean.getReasonCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.APP_REJECT_CODE_EMPTY;

            } else if (!UserInputValidator.isAlphaNumeric(Bean.getReasonCode())) {
                isValidate = false;

                errorMessage = MessageVarList.APP_REJECT_CODE_INVALID;

            } else if (Bean.getDescription().contentEquals("") || Bean.getDescription().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.APP_REJECT_DESCRIPTION_EMPTY;

            } else if (!UserInputValidator.isDescription(Bean.getDescription())) {
                isValidate = false;

                errorMessage = MessageVarList.APP_REJECT_DESCRIPTION_INVALID;

            }
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
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

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("reason");


            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Add Reject Reason Reason Code :" + uniqueId + ";  By " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.APPREJECT);
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
