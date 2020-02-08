/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CreditConfirmationSchemaBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CreditConfirmationSchemaManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.PageMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * @author chinthaka_r
 */
public class AddCreditConfirmationSchemaMgtServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager pageMgt;
    HttpSession sessionObj = null;
    private List<StatusBean> statusList;
    private CreditConfirmationSchemaBean schemaBean = null;
    private SystemUserManager systemUserManager = null;
    private CreditConfirmationSchemaManager creditConfirmationSchemaManager;
    private List<String> userTaskList;
    private String errorMessage = null;
    private SystemAuditBean systemAuditBean = null;
    private String url = "/administrator/controlpanel/systemconfigmgt/creditconfirmationschemamgt.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //call to existing session
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

            try {
                //set page code and task codes
                String pageCode = PageVarList.CREDITCONFIRMSCEMAMGT;
                String taskCode = TaskVarList.ADD;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    try {
                        this.setUserInputToBean(request);
                        if (this.validateUserInput(schemaBean)) {
                            request.setAttribute("operationtype", "default");
                            this.setAudittraceValue(request);
                            try {
                                this.insertSchema(schemaBean, systemAuditBean);
                                request.setAttribute("successMsg", schemaBean.getSchemaCode()+" "+MessageVarList.CREDIT_CONFIRM_SCHEMA_ADD_SUCESS);
                                rd = getServletContext().getRequestDispatcher("/LoadCreditConfirmationSchemaMgtServlet");
                            } catch (SQLException e) {
                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                
                                request.setAttribute("schemaBean", schemaBean);
                                request.setAttribute("operationtype", "default");
                                request.setAttribute("errorMsg", oraMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);

                            }
                        } else {
                            request.setAttribute("operationtype", "default");
                            request.setAttribute("schemaBean", schemaBean);
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }
                        rd.forward(request, response);
                        
                    } catch (Exception e) {
                        request.setAttribute("operationtype", "default");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }

                } else {
                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }
                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);

            } catch (AccessDeniedException adex) {
                throw adex;
            }
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);

        } //catch session exception
        catch (NewLoginSessionException nlex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

            //catch accessdenied exception
        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);

        } finally {
            out.close();
        }
    }

    public void setUserInputToBean(HttpServletRequest request) throws Exception {
        try {
            schemaBean = new CreditConfirmationSchemaBean();
            schemaBean.setSchemaCode(request.getParameter("txtScemaCode").trim());
            schemaBean.setDescription(request.getParameter("txtDescription").trim());
            schemaBean.setMinLimit(request.getParameter("txtMinLimit").trim());
            schemaBean.setMaxLimit(request.getParameter("txtMaxLimit").trim());
            schemaBean.setLastUpdatedUser(sessionUser.getUserName());
            schemaBean.setCreatedDate(new Date());

        } catch (Exception e) {
            throw e;
        }
    }

    public boolean validateUserInput(CreditConfirmationSchemaBean bean) throws Exception {
        boolean isValidate = true;
        try {
            if (bean.getSchemaCode().isEmpty() || bean.getSchemaCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CREDIT_CONFIRM_SCHEMA_CODE_EMPTY;
            }else if(!UserInputValidator.isAlphaNumeric(bean.getSchemaCode())){
                isValidate=false;
                errorMessage=MessageVarList.CREDIT_CONFIRM_SCHEMA_CODE_INVALID;
            } else if (bean.getDescription().isEmpty() || bean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CREDIT_CONFIRM_SCHEMA_DESCRIPTION_EMPTY;
            } else if (!UserInputValidator.isDescription(bean.getDescription())) {
                isValidate = false;
                errorMessage = MessageVarList.CREDIT_CONFIRM_SCHEMA_DESCRIPTION_INVALID;
            } else if (bean.getMinLimit().isEmpty() || bean.getMinLimit().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CREDIT_CONFIRM_SCHEMA_MINLIMIT_EMPTY;
            } else if (!UserInputValidator.isNumeric(bean.getMinLimit())) {
                isValidate = false;
                errorMessage = MessageVarList.CREDIT_CONFIRM_SCHEMA_MINLIMIT_INVALID;
            } else if (bean.getMaxLimit().isEmpty() || bean.getMaxLimit().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CREDIT_CONFIRM_SCHEMA_MAXLIMIT_EMPTY;
            } else if (!UserInputValidator.isNumeric(bean.getMaxLimit())) {
                isValidate = false;
                errorMessage = MessageVarList.CREDIT_CONFIRM_SCHEMA_MAXLIMIT_INVALID;
            } else if(Long.parseLong(bean.getMinLimit())>Long.parseLong(bean.getMaxLimit())){
                isValidate=false;
                errorMessage=MessageVarList.CREDIT_CONFIRM_SCHEMA_LIMIT_DEFINE_ERROR;
            }
        } catch (Exception e) {
            isValidate = false;
            throw e;

        }
        return isValidate;
    }
    public boolean insertSchema(CreditConfirmationSchemaBean schemaBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            creditConfirmationSchemaManager = new CreditConfirmationSchemaManager();
            success = creditConfirmationSchemaManager.insertSchema(schemaBean, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }
    
    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("txtScemaCode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Credit Confirmation Schema, schema code : " + schemaBean.getSchemaCode() + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.CREDITCONFIRMSCEMAMGT);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
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
