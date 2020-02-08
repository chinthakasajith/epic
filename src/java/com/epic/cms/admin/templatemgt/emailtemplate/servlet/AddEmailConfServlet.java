/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.emailtemplate.servlet;

import com.epic.cms.admin.templatemgt.emailtemplate.bean.EmailBean;
import com.epic.cms.admin.templatemgt.emailtemplate.businesslogic.EmailConfMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
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
public class AddEmailConfServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private EmailConfMgtManager emailconfManager = null;
    private int success = 0;
    private List<String> userTaskList;
    private EmailBean emailBean;
    private String url = "/administrator/templatemgt/emailtemplate/emailadd.jsp";
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
            String pageCode = PageVarList.EMAILHOME;
            String taskCode = TaskVarList.ADD;

            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                this.setUserInputToBean(request);
                request.setAttribute("emailBean", emailBean);

                if (this.validateUserInput(emailBean, request)) {

                    if (this.insertEmailConfDomain(emailBean) >= 0) {

                        request.setAttribute("successMsg", MessageVarList.SUCCESS_ADD_EMAILCONF + " Email Template " + emailBean.getTemplateCode());
                        rd = getServletContext().getRequestDispatcher("/LoadEmailConfTempMgtServlet");
                    } else {
                        request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_EMAILCONF);
                        rd = getServletContext().getRequestDispatcher(url);
                    }
                } else {
                    request.setAttribute("emailBean", emailBean);
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
            request.setAttribute("errorMsg", emailBean.getTemplateCode() + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_EMAILCONF);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    public void setUserInputToBean(HttpServletRequest request) {

        String templateCode=request.getParameter("templateCode").trim();
        String description=request.getParameter("description").trim();
        String subject = request.getParameter("subject").trim();
        String body = request.getParameter("body");
        String status = request.getParameter("status").trim();

        emailBean = new EmailBean();

        emailBean.setTemplateCode(templateCode);
        emailBean.setDescription(description);
        emailBean.setSubject(subject);
        emailBean.setBody(body);
        emailBean.setStatus(status);
        emailBean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());

        newValue = emailBean.getTemplateCode()+"|"+emailBean.getDescription()+"|"+emailBean.getSubject() + "|" + emailBean.getBody() + "|" + emailBean.getStatus();
    }

    public boolean validateUserInput(EmailBean emailBean, HttpServletRequest request) throws Exception {
        boolean isValidate = true;

        try {

            if (emailBean.getTemplateCode().contentEquals("") || emailBean.getTemplateCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.EMAIL_TEMPLATECODE_NULL;
            } else if (!UserInputValidator.isCorrectString(emailBean.getTemplateCode())) {
                isValidate = false;
                errorMessage = MessageVarList.EMAIL_CODE_INVALID;
            } else if (emailBean.getDescription().contentEquals("") || emailBean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.EMAIL_DESCRIPTION_NULL;
            } else if (emailBean.getSubject().contentEquals("") || emailBean.getSubject().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.EMAIL_TEMPLATESUBJECT_NULL;
            } else if (emailBean.getBody().contentEquals("<br>") || emailBean.getBody().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.EMAIL_TEMPLATEBODY_NULL;
            } else if (emailBean.getBody().length() >= 3500) {
                isValidate = false;
                errorMessage = MessageVarList.EMAIL_TEMPLATEBODY_MAX_LENGTH;
            } else if (emailBean.getStatus().contentEquals("") || emailBean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.EMAIL_TEMPLATE_STATUS_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;

        }

        return isValidate;
    }

    private int insertEmailConfDomain(EmailBean domainBean) throws Exception {

        try {

            emailconfManager = new EmailConfMgtManager();
            success = emailconfManager.addNewEmailConf(domainBean);
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
