/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.camm.numbergeneration.servlet;

import com.epic.cms.admin.camm.numbergeneration.bean.NumberFormatBean;
import com.epic.cms.admin.camm.numbergeneration.bean.NumberFormatFieldBean;
import com.epic.cms.admin.camm.numbergeneration.bean.NumberGenerationFormatBean;
import com.epic.cms.admin.camm.numbergeneration.businesslogic.NumberGeneFormatBusinessLogic;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
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
 * @author upul
 */
public class DeleteNumberFormatServlet extends HttpServlet {

    private RequestDispatcher rd = null;
    private SessionUser sessionUser = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    //-------------------------------------------------    
    private NumberGenerationFormatBean formatBean;
    private List<NumberFormatFieldBean> formatCodeBeans;
    private NumberGeneFormatBusinessLogic formatBusinessLogic = null;
    private String oldValue;

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
        PrintWriter out = response.getWriter();
        try {
            //call to existing session
            /////////////////////////////////////////////////////////////////////
            HttpSession sessionObj = request.getSession(false);
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

            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            formatBean = new NumberGenerationFormatBean();

            //number format code which is to be deleted
            String formatCode = request.getParameter("formatCode");
            String taskCode = TaskVarList.DELETE;

            if (this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), taskCode)) {

                this.getAllNumberFormatCodeDetails();

                for (NumberFormatFieldBean bean : formatCodeBeans) {
                    if (bean.getFormatCode().equals(formatCode)) {
                        oldValue = bean.getFormatCode() + "|" + bean.getDescription() + "|"
                                + "" + bean.getCardType() + "|"
                                + "" + bean.getNumberLength();
                    }
                }

                this.setAudittraceValue(request);

                int success = formatBusinessLogic.deleteNumberFormatCodeDetail(systemAuditBean, formatCode);

                if (success == 1) {
                    request.setAttribute("isBack", "yes");
                    request.setAttribute("successMsg", MessageVarList.CARD_NUMBER_FORMAT_DELETE_SUCCESS);
                }
            } else {

                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }

            rd = getServletContext().getRequestDispatcher("/LoadNumberFormatGeneServlet");
            rd.include(request, response);




            //////////////////////////////////////////////////////////////////
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp");
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            String errMessage = MessageVarList.ACCESS_DENIED_PAGETASK;
            request.setAttribute(MessageVarList.JSP_ERROR, errMessage);
            rd = getServletContext().getRequestDispatcher("/LoadNumberFormatGeneServlet");
            rd.include(request, response);


        } catch (SQLException e) {
            //show the messages which has thown when inserting
            OracleMessage message = new OracleMessage();
            String oraMessage = message.getMessege(e.getMessage());

            //go to the home page with the error message
            request.setAttribute("errorMsg", oraMessage);


            rd = getServletContext().getRequestDispatcher("/LoadNumberFormatGeneServlet");
            rd.include(request, response);

        } catch (Exception ex) {
            rd = getServletContext().getRequestDispatcher("/LoadNumberFormatGeneServlet");
            rd.include(request, response);
        } finally {
            out.close();
        }
    }

    ////////////////////////////////////////////////////////////////////////////    
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
    ////////////////////////////////////////////////////////////////////////////   

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("numberFormatCode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Delete Card Number Format. Card Number Format Code: '" + uniqueId + "'  by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.CARDNUMBERGENE);
            systemAuditBean.setTaskCode(TaskVarList.DELETE);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue(oldValue);
            systemAuditBean.setNewValue("");

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * getAllNumberFormatCodeDetails
     * @return
     * @throws Exception 
     */
    public void getAllNumberFormatCodeDetails() throws Exception {

        try {
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            formatCodeBeans = formatBusinessLogic.getNumberFormatCodeDetails();
        } catch (SQLException ex) {
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
