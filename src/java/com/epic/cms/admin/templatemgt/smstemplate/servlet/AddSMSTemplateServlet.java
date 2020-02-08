/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.smstemplate.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.smstemplate.bean.SMSBean;
import com.epic.cms.admin.templatemgt.smstemplate.businesslogic.SMSConfMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
public class AddSMSTemplateServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private SMSConfMgtManager smsconfManager = null;
    private int success = 0;
    private List<String> userTaskList;
    private SMSBean smsBean;
    private String url = "/administrator/templatemgt/smstemplate/smsadd.jsp";
    private String newValue;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            sessionObj = request.getSession(false);
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

            request.setAttribute("selectedtab", "0");

            //set page code and task codes
            String pageCode = PageVarList.SMSTEMPLATE;
            String taskCode = TaskVarList.ADD;

            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                this.setUserInputToBean(request);
                request.setAttribute("smsBean", smsBean);

                if (this.validateUserInput(smsBean, request)) {

                    if (this.insertSMSConfDomain(smsBean) >= 0) {

                        request.setAttribute("successMsg", MessageVarList.SUCCESS_ADD_EMAILCONF + " Email Template " + smsBean.getTemplateCode());
                        rd = getServletContext().getRequestDispatcher("/LoadSMSTempMgtServlet");
                    } else {
                        request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_SMSCONF);
                        rd = getServletContext().getRequestDispatcher(url);
                    }
                } else {
                    request.setAttribute("smsBean", smsBean);
                    request.setAttribute("errorMsg", errorMessage);
                    rd = getServletContext().getRequestDispatcher(url);
                }

            } else {

                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }

        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {

            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher("/administrator/templatemgt/emailtemplate/emailhome.jsp");

        } catch (SQLException ex) {
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", smsBean.getTemplateCode() + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_SMSCONF);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    public void setUserInputToBean(HttpServletRequest request) {

        String templateCode=request.getParameter("templateCode").trim();
        String description=request.getParameter("description").trim();
        String messagebody = request.getParameter("messageBody");
        String status = request.getParameter("status").trim();

        smsBean = new SMSBean();

        smsBean.setTemplateCode(templateCode);
        smsBean.setDescription(description);
        smsBean.setMessageBody(messagebody);
        smsBean.setStatus(status);
        smsBean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());

        newValue = smsBean.getTemplateCode()+"|"+smsBean.getDescription() + "|" + smsBean.getMessageBody()+ "|" + smsBean.getStatus();
    }

    public boolean validateUserInput(SMSBean smsBean, HttpServletRequest request) throws Exception {
        boolean isValidate = true;

        try {

            if (smsBean.getTemplateCode().contentEquals("") || smsBean.getTemplateCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SMS_TEMPLATECODE_NULL;
            } else if (!UserInputValidator.isCorrectString(smsBean.getTemplateCode())) {
                isValidate = false;
                errorMessage = MessageVarList.SMS_CODE_INVALID;
            } else if (smsBean.getDescription().contentEquals("") || smsBean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SMS_DESCRIPTION_NULL;
            } else if (smsBean.getMessageBody().contentEquals("") || smsBean.getMessageBody().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SMS_TEMPLATEBODY_NULL;
            } else if (smsBean.getStatus().contentEquals("") || smsBean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SMS_TEMPLATE_STATUS_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;

        }

        return isValidate;
    }

    private int insertSMSConfDomain(SMSBean domainBean) throws Exception {

        try {

            smsconfManager = new SMSConfMgtManager();
            success = smsconfManager.addNewSMSConf(domainBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
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
}
