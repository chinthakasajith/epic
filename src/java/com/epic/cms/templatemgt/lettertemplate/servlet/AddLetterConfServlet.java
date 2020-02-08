/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.templatemgt.lettertemplate.servlet;

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
import com.epic.cms.templatemgt.lettertamplate.bean.LetterBean;
import com.epic.cms.templatemgt.lettertamplate.bean.LetterFieldBean;
import com.epic.cms.templatemgt.lettertemplate.businesslogic.LetterConfMgtManager;
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
 * @author sajith_g
 */
public class AddLetterConfServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private LetterConfMgtManager letterconfManager = null;
    private int success = 0;
    private List<String> userTaskList;
    private LetterBean letterBean;
    private String url = "/administrator/templatemgt/lettertemplate/letteradd.jsp";
    private LetterConfMgtManager domainManager = null;
    private List<LetterFieldBean> letterFieldList=null;
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
            String pageCode = PageVarList.LETTERHOME;
            String taskCode = TaskVarList.ADD;

            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                this.setUserInputToBean(request);
                request.setAttribute("letterBean", letterBean);
                
                //set letter field set to insert letter body
                domainManager=new LetterConfMgtManager();
                letterFieldList=domainManager.getLetterFieldDetailsList();

                if (this.validateUserInput(letterBean, request)) {

                    if (this.insertLetterConfDomain(letterBean) >= 0) {

                        request.setAttribute("successMsg", MessageVarList.SUCCESS_ADD_LETTERCONF + " Letter Template " + letterBean.getTemplateCode());
                        rd = getServletContext().getRequestDispatcher("/LoadLetterConfTempMgtServlet");
                    } else {
                        request.setAttribute("letterFieldList", letterFieldList);
                        request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_LETTERCONF);
                        rd = getServletContext().getRequestDispatcher(url);
                    }
                } else {
                    request.setAttribute("emailBean", letterBean);
                    request.setAttribute("errorMsg", errorMessage);
                    request.setAttribute("letterFieldList", letterFieldList);
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
            request.setAttribute("errorMsg", letterBean.getTemplateCode() + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_LETTERCONF);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    public void setUserInputToBean(HttpServletRequest request) {

        String templateCode=request.getParameter("templateCode").trim();
        String description=request.getParameter("description").trim();
        String title = request.getParameter("title").trim();
        String body = request.getParameter("body");
        String status = request.getParameter("status").trim();

        letterBean = new LetterBean();

        letterBean.setTemplateCode(templateCode);
        letterBean.setDescription(description);
        letterBean.setTitle(title);
        letterBean.setBody(body);
        letterBean.setStatus(status);
        letterBean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());

        newValue = letterBean.getTemplateCode()+"|"+letterBean.getDescription()+"|"+letterBean.getTitle()+ "|" + letterBean.getBody() + "|" + letterBean.getStatus();
    }

    public boolean validateUserInput(LetterBean letterBean, HttpServletRequest request) throws Exception {
        boolean isValidate = true;

        try {

            if (letterBean.getTemplateCode().contentEquals("") || letterBean.getTemplateCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.LETTER_TEMPLATECODE_NULL;
            } else if (!UserInputValidator.isCorrectString(letterBean.getTemplateCode())) {
                isValidate = false;
                errorMessage = MessageVarList.LETTER_CODE_INVALID;
            } else if (letterBean.getDescription().contentEquals("") || letterBean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.LETTER_DESCRIPTION_NULL;
            } else if (letterBean.getTitle().contentEquals("") || letterBean.getTitle().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.LETTER_TEMPLATETITLE_NULL;
            } else if (letterBean.getBody().contentEquals("<br>") || letterBean.getBody().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.LETTER_TEMPLATEBODY_NULL;
            } else if (letterBean.getStatus().contentEquals("") || letterBean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.LETTER_TEMPLATE_STATUS_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;

        }

        return isValidate;
    }

    private int insertLetterConfDomain(LetterBean domainBean) throws Exception {

        try {

            letterconfManager = new LetterConfMgtManager();
            success = letterconfManager.addNewLetterConf(domainBean);
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
