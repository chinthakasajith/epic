/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.cardtemplate.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.admin.templatemgt.cardtemplate.businesslogic.CardTemplateMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
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
 * @author chanuka
 */
public class DeleteCardTemplateServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CardTemplateMgtManager cardTemplateManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private String templateCode;
    boolean success = false;
    private String url = "/administrator/templatemgt/cardtemplate/cardtemplatehome.jsp";
    private String oldValue;
    private List<CardTemplateBean> searchList = null;

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

            //set page code and task codes
            String pageCode = PageVarList.CARDTEMPLATEHOME;
            String taskCode = TaskVarList.DELETE;

            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                templateCode = request.getParameter("templatecode").trim();

                if (templateCode != null) {

                    this.searchCardDomainMgt();

                    for (CardTemplateBean cardBean : searchList) {
                        if (cardBean.getTemplateCode().equals(templateCode)) {
                            oldValue = cardBean.getTemplateCode() + "|" + cardBean.getTemplateName() + "|" + cardBean.getValidFrom() + "|" + cardBean.getValidTo() + "|" + cardBean.getProductCode() + "|"
                                    + cardBean.getCurrencyCode() + "|" + cardBean.getTotalCreditLimit() + "|" + cardBean.getStatus() + "|" + cardBean.getStaffStatus() + "|"
                                    + cardBean.getAccounttemplateCode() + "|" + cardBean.getServiceCode() + "|" + cardBean.getExpiryPeriod() + "|" + cardBean.getRenewRequired() + cardBean.getReissuThrshPeriod() + "|"
                                    + cardBean.getCashAdvanceRate() + "|" + cardBean.getFeeProfCode() + "|" + cardBean.getRiskProfCode() + "|" + cardBean.getTxnProfCode() + "|" + cardBean.getLastUpdatedTime() + "|" + sessionUser.getUserName();

                        }
                    }

                    this.setAudittraceValue(request);

                    if (this.deleteCardTemplate(templateCode, systemAuditBean)) {
                        request.setAttribute("successMsg", MessageVarList.SUCCESS_DELETE_CARDTEMPLATE);
                        rd = getServletContext().getRequestDispatcher("/LoadCardTemplateMgtServlet");


                    } else {
                        rd = getServletContext().getRequestDispatcher(url);
                        request.setAttribute("errorMsg", MessageVarList.ERROR_DELETE_CARDTEMPLATE);

                    }
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
            rd = getServletContext().getRequestDispatcher("/administrator/templatemgt/cardtemplate/cardtemplatehome.jsp");


        } catch (SQLException ex) {
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", templateCode + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_DELETE_CARDTEMPLATE);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private boolean deleteCardTemplate(String templateCode, SystemAuditBean systemAuditBean) throws Exception {

        try {

            cardTemplateManager = new CardTemplateMgtManager();
            success = cardTemplateManager.deleteCardTemplate(templateCode, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(templateCode);

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Delete Card Template. Template Code: " + templateCode + "; by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TEMPLATEMGT);
            systemAuditBean.setPageCode(PageVarList.CARDTEMPLATEHOME);
            systemAuditBean.setTaskCode(TaskVarList.DELETE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(oldValue);
            //set new value of change if required
            systemAuditBean.setNewValue("");


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }



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

    private void searchCardDomainMgt() throws Exception {

        try {


            cardTemplateManager = new CardTemplateMgtManager();
            searchList = cardTemplateManager.getAllCardDomainSearchList();


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
