package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AlertBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.AlertMgtManager;
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
public class AddAlertMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/administrator/controlpanel/systemconfigmgt/alertmgt.jsp";
    private SystemUserManager systemUserManager = null;
    private AlertMgtManager alertMgtManager;
    private AlertBean bean;
    public List<AlertBean> alertlist;
    private String errorMessage = null;
    private String newValue = "";

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
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
            this.getAlertDetails();
            request.setAttribute("alertlist", alertlist);

            this.setValuesToBean(request);

            // // // validation 
            int success = -1;
            if (this.validateUserInput(bean)) {

                try {
                    success = this.insertNewAlert();

                } catch (Exception ex) {
                    throw ex;
                }

            } else {
                request.setAttribute("alertBean", bean);
                request.setAttribute("errorMsg", errorMessage);
            }
            // // // 

            if (success > 0) {

                this.getAlertDetails();
                request.setAttribute("alertlist", alertlist);

                request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.ALERT_ADD_SUCCESS + " type: " + bean.getAlertType());
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
            rd = getServletContext().getRequestDispatcher("/LoadAlertMgtServlet");

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
     * to insert a new record to alert table
     *
     * @return
     * @throws Exception
     */
    public int insertNewAlert() throws Exception {
        try {
            alertMgtManager = new AlertMgtManager();
            int i = alertMgtManager.addNewAlert(bean);
            return i;
        } catch (Exception e) {
            throw e;
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
            newValue = bean.getAlertType() + "|" + bean.getDescription() + "|" + bean.getStatus() + "|" + bean.getLastUpdatedDate() + "|" + bean.getLastUpdatedUser();

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * take all alert details to the user view
     *
     * @return
     * @throws Exception
     */
    public List<AlertBean> getAlertDetails() throws Exception {
        try {

            alertMgtManager = new AlertMgtManager();
            alertlist = alertMgtManager.getAlerts();
            return alertlist;

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * validate user inputs
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
            } else if (bean.getPort() != null && !UserInputValidator.isCorrectPort(bean.getPort())) {
                isValidate = false;
                errorMessage = MessageVarList.ALERT_PORT_NUMERIC;
            } else if (bean.getPortTimeOut() != null && !UserInputValidator.isNumeric(bean.getPortTimeOut())) {
                isValidate = false;
                errorMessage = MessageVarList.ALERT_PORTTIMEOUT_NUMERIC;
            } else if (bean.getSocketTimeOut() != null && !UserInputValidator.isNumeric(bean.getSocketTimeOut())) {
                isValidate = false;
                errorMessage = MessageVarList.ALERT_SOCKETTIMEOUT_NUMERIC;
            } else if (bean.getSender() != null && !UserInputValidator.isValidEmail(bean.getSender())) {
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
