/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AlertBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.AlertMgtManager;
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
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
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
 * @author sajith
 */
public class UpdateAlertMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/administrator/controlpanel/systemconfigmgt/alertmgt.jsp";
    private SystemUserManager systemUserManager = null;
    private AlertMgtManager alertMgtManager;
    private List<AlertBean> alertlist;
    private AlertBean bean;
    private SystemAuditBean systemAuditBean;
    private String errorMessage;
    private String newValue;

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
            this.getAlertDetails();
            request.setAttribute("alertlist", alertlist);

            this.setValuesToBean(request);

            ///validation          
            int success = -1;
            if (this.validateUserInput(bean)) {

                try {
                    success = this.updateAlert();

                } catch (Exception ex) {
                    throw ex;
                }

            } else {
                request.setAttribute("alertBean", bean);
                request.setAttribute("errorMsg", errorMessage);
            }
            // // // validation 

            if (success > 0) {

                this.getAlertDetails();
                request.setAttribute("alertlist", alertlist);
                rd = request.getRequestDispatcher("/LoadAlertMgtServlet");
                request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.ALERT_UPDATE_SUCCESS + " type: " + bean.getAlertType());

            } else {
                request.setAttribute("alertBean", bean);
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
            rd = getServletContext().getRequestDispatcher("/LoadAlertMgtServlet");
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
     *
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
     *
     * @throws Exception
     */
    private void getAlertDetails() throws Exception {
        try {

            alertMgtManager = new AlertMgtManager();
            alertlist = alertMgtManager.getAlerts();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to set the values taken by user to bean
     *
     * @param request
     * @throws Exception
     */
    private void setValuesToBean(HttpServletRequest request) throws Exception {
        try {
            bean = new AlertBean();
            bean.setAlertType(request.getParameter("alertType"));
            bean.setDescription(request.getParameter("description"));
            bean.setIp(request.getParameter("ip"));
            bean.setPort(request.getParameter("port"));
            bean.setPortTimeOut(request.getParameter("portTimeOut"));
            bean.setSocketTimeOut(request.getParameter("socketTimeOut"));
            bean.setUserName(request.getParameter("userName"));
            bean.setPassword(request.getParameter("password"));
            bean.setSender(request.getParameter("sender"));
            bean.setStatus(request.getParameter("status"));
            bean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());

            newValue = bean.getAlertType() + "|" + bean.getDescription() + "|" + bean.getStatus();

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * to update alert details
     *
     * @return
     * @throws Exception
     */
    public int updateAlert() throws Exception {
        try {
            alertMgtManager = new AlertMgtManager();
            int i = alertMgtManager.UpdateAlert(bean);
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * to validate user inputs
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public boolean validateUserInput(AlertBean bean) throws Exception {
        boolean isValidate = true;

        try {

            if (bean.getAlertType().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.ALERT_CODE_EMPTY;
            } else if (bean.getDescription().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.ALERT_DES_EMPTY;
            } else if (bean.getIp() != null && !UserInputValidator.isCorrectHost(bean.getIp())) {
                isValidate = false;
                errorMessage = MessageVarList.HOST_INVALID;
            } else if (bean.getPort() != null && !bean.getPort().equals("") && !UserInputValidator.isCorrectPort(bean.getPort())) {
                isValidate = false;
                errorMessage = MessageVarList.ALERT_PORT_NUMERIC;
            } else if (bean.getPortTimeOut() != null && !bean.getPortTimeOut().equals("") && !UserInputValidator.isNumeric(bean.getPortTimeOut())) {
                isValidate = false;
                errorMessage = MessageVarList.ALERT_PORTTIMEOUT_NUMERIC;
            } else if (bean.getSocketTimeOut() != null && !bean.getSocketTimeOut().equals("") && !UserInputValidator.isNumeric(bean.getSocketTimeOut())) {
                isValidate = false;
                errorMessage = MessageVarList.ALERT_SOCKETTIMEOUT_NUMERIC;
            } else if (bean.getSender() != null && !bean.getSender().equals("") && !UserInputValidator.isValidEmail(bean.getSender())) {
                isValidate = false;
                errorMessage = MessageVarList.EMAIL_INVALID;
            }

        } catch (Exception ex) {
            isValidate = false;
        }
        return isValidate;
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UpdateAlertMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UpdateAlertMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
