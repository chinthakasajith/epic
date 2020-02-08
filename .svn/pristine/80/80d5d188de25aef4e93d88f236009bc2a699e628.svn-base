/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.ApplicationModuleMgtManager;
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
public class AddApplicationMgtServlet extends HttpServlet {

    private SystemAuditBean systemAuditBean = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private RequestDispatcher rd = null;
    private SystemUserManager systemUserManager = null;
    private String errormsg;
    private ApplicationModuleMgtManager appMgrOnj = null;
    private String url = "/administrator/controlpanel/systemusermgt/applicationmodulmgthome.jsp";
    private SystemUserManager sysUserOnj = null;
    private List<StatusBean> statusBean = null;

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
        try {
            request.setAttribute("operationtype", "add");

            HttpSession sessionObj = request.getSession(false);
            try {
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
            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            String appCode = request.getParameter("appCode");
            String appDes = request.getParameter("appdes");
            String appCat = request.getParameter("category");
            String appStatus = request.getParameter("appStatus");

            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.ADD)) {
                //set auditrace values
                throw new AccessDeniedException();

            }







            ApplicationModuleBean appBean = new ApplicationModuleBean();

            appBean.setApplicationCode(appCode);
            appBean.setStatus(appStatus);
            appBean.setCat(appCat);
            appBean.setDescription(appDes);
            appBean.setLastUpdateuser(sessionUser.getUserName());

            appMgrOnj = new ApplicationModuleMgtManager();
            if (appMgrOnj.checkExistApp(appBean.getApplicationCode())) {

                if (!this.validateAddValue(appBean)) {
                    request.setAttribute(RequestVarList.APPLICTION_ADD_BEAN, appBean);
                    request.setAttribute(MessageVarList.JSP_ERROR, errormsg);
//                    rd = request.getRequestDispatcher(url);
//                    rd.forward(request, response);

                } else {
                    this.setAudittraceValue(request);
                    int addRow = appMgrOnj.addApplication(appBean, systemAuditBean);
                    if (addRow == 1) {
                        request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.APPLICATION_ADD_SUCCESS);
                        sessionVarlist.setAddAppBean(null);
                    } else {
                        request.setAttribute(RequestVarList.APPLICTION_ADD_BEAN, appBean);
                    }
                }
            } else {
                request.setAttribute(RequestVarList.APPLICTION_ADD_BEAN, appBean);
                request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.APPLICTION_EXIST);
            }
            List<ApplicationModuleBean> allList = appMgrOnj.getAllApplicationList();

            sysUserOnj = new SystemUserManager();
            statusBean = sysUserOnj.getStatusByUserRole("GENR");
            request.setAttribute(RequestVarList.SECTION_STATUS_LIST, statusBean);
            request.setAttribute(RequestVarList.SECTION_DETAILS_LIST, allList);

            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadAppLicationModuleServlet");
            rd.include(request, response);
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.toString()));
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            rd.forward(request, response);
        }
    }

    /**
     * validate add values if validate ok return ture otherwise return false
     * @param appBean - ApplicationModuleBean
     * @return boolean 
     * @throws Exception 
     */
    private boolean validateAddValue(ApplicationModuleBean appBean) throws Exception {
        boolean flag = true;
        try {
            errormsg = "";
            if (appBean.getApplicationCode().contentEquals("") || appBean.getApplicationCode().length() <= 0) {
                flag = false;

                if (errormsg.equals("")) {
                    errormsg = MessageVarList.APPLICTION_APPCODE_NULL;
                }
            } else if (!UserInputValidator.isAlphaNumeric(appBean.getApplicationCode())) {
                flag = false;
                if (errormsg.equals("")) {
                    errormsg = MessageVarList.APPLICTION_APPCODE_INVALIDE;
                }
            } else if (appBean.getDescription().contentEquals("") || appBean.getDescription().length() <= 0) {
                flag = false;
                if (errormsg.equals("")) {
                    errormsg = MessageVarList.APPLICTION_DESCRIPTION_NULL;
                }


            } else if (!UserInputValidator.isDescription(appBean.getDescription())) {
                flag = false;
                if (errormsg.equals("")) {
                    errormsg = MessageVarList.APPLICTION_DESCRIPTION_INVALIDE;
                }


            }else if (appBean.getCat().contentEquals("") || appBean.getCat().length() <= 0) {
                flag = false;
                if (errormsg.equals("")) {
                    errormsg = MessageVarList.APPLICTION_CAT_NULL;
                }
            }
            
            else if (appBean.getStatus().contentEquals("") || appBean.getStatus().length() <= 0) {
                flag = false;
                if (errormsg.equals("")) {
                    errormsg = MessageVarList.APPLICTION_STATUS_NULL;
                }
            }

            return flag;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * set audit trace
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("appCode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add application, application code : " + uniqueId + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.APPLICATIONMGT);
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
