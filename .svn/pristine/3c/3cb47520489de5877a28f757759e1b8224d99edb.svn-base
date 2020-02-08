package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.TerminalBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.TerminalMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
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
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jeevan
 */
public class UpdateTerminalManufacServlet extends HttpServlet {

    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean;
    private SessionUser sessionUser = null;
    private List<String> userTaskList;
    private String url = "/administrator/controlpanel/systemconfigmgt/terminalmanufacturehome.jsp";
    private RequestDispatcher rd;
    private List<StatusBean> statusList = null;
    private TerminalMgtManager terminalMgtManager;
    private TerminalBean bean;
    private String newValue;
    private String errorMessage = null;
    private List<TerminalBean> terminalList;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessionObj = request.getSession(false);
        try {
            try {
                request.setAttribute("operationtype", "update");
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

                try {

                    String pageCode = PageVarList.TERMINAL_MANUFACTURE;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    } else {
                        throw new AccessDeniedException();
                    }

                    sessionVarlist.setUserPageTaskList(userTaskList);

                } catch (AccessDeniedException adex) {
                    throw adex;
                }

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            ////
            this.getTerminalDetails();
            request.setAttribute("terminalList", terminalList);

            String oldValue = request.getParameter("oldvalue");
            this.setValuesToBean(request);

            int success = -1;
            if (this.validateUserInput(bean)) {
                this.setAudittraceValue(request, oldValue, newValue);

                try {
                    success = this.updateTerminalInfo();
                } catch (Exception ex) {
                    throw ex;
                }
            } else {
                request.setAttribute("terminalBean", bean);
                request.setAttribute("errorMsg", errorMessage);
            }

            if (success > 0) {
                this.getTerminalDetails();
                request.setAttribute("terminalList", terminalList);

                rd = request.getRequestDispatcher("/LoadterminalManufacServlet");
                request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.TER_MANUFAC_UPDATE_SUCCESS + " Code : " + bean.getManufactureCode());
            } else {
                request.setAttribute("terminalBean", bean);
                rd = request.getRequestDispatcher(url);
            }
            sessionVarlist.setStatusList(statusList);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);


        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadEODFileInfoMgtServlet");

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

    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {

        boolean isValidTaskAccess = false;

        try {
            systemUserManager = new SystemUserManager();
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

    private void getTerminalDetails() throws Exception {
        try {

            terminalMgtManager = new TerminalMgtManager();
            terminalList = terminalMgtManager.getTerminalDetails();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setValuesToBean(HttpServletRequest request) throws Exception {
        try {
            bean = new TerminalBean();
            bean.setManufactureCode(request.getParameter("terminalcode"));
            bean.setDescription(request.getParameter("terminaldis"));

            newValue = bean.getManufactureCode() + "|" + bean.getDescription();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean validateUserInput(TerminalBean bean) throws Exception {
        boolean isValidate = true;

        try {

            if (bean.getManufactureCode().contentEquals("") || bean.getManufactureCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.TER_CODE_EMPTY;

            } else if (!UserInputValidator.isAlphaNumeric(bean.getManufactureCode())) {
                isValidate = false;
                errorMessage = MessageVarList.TER_DESC_INVALID;
            } else if (bean.getDescription().contentEquals("") || bean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.TER_DESC_EMPTY;

            } else if (!UserInputValidator.isDescription(bean.getDescription())) {
                isValidate = false;
                errorMessage = MessageVarList.TER_DESC_INVALID;
            }

        } catch (Exception e) {
            throw e;
        }
        return isValidate;
    }

    private void setAudittraceValue(HttpServletRequest request, String oldValue, String newValue) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("terminalcode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Update Terminal Manufacture. Code: " + uniqueId + "; by:  " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.TERMINAL_MANUFACTURE);
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

    private int updateTerminalInfo() throws Exception {
        try {
            terminalMgtManager = new TerminalMgtManager();
            int i = terminalMgtManager.updateTerminalInfo(systemAuditBean, bean);
            return i;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
