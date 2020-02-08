/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SectionManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
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
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * @author ayesh
 */
public class UpdateSectionMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SessionUser sessionUser = null;
    private String url = "/administrator/controlpanel/systemusermgt/sectionmgthome.jsp";
    private SectionBean sectionBean = null;
    private String errormsg = "";
    private SystemUserManager systemUserManager = null;
    private List<StatusBean> statusBean = null;
    private List<SectionBean> allSectionList = null;
    private SystemUserManager sysUserOnj = null;

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


            sectionBean = new SectionBean();

            sysUserOnj = new SystemUserManager();

            statusBean = sysUserOnj.getStatusByUserRole("GENR");

            String sortKey = request.getParameter("editSortKey");
            String secCode = request.getParameter("editSecCode");
            String description = request.getParameter("editDescription");
            String status = request.getParameter("editStatus");


            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.UPDATE)) {
                throw new AccessDeniedException();
            }

            sectionBean.setSectionCode(secCode);
            sectionBean.setSortKey(sortKey);
            sectionBean.setStatusCode(status);
            sectionBean.setDescription(description);
            sectionBean.setCreatedTime(new Date());
            sectionBean.setLasUpdatedUser(sessionUser.getUserName());
            sectionBean.setLastUpdatedTime(new Date());

            try {


                if (!this.validateAddValue(sectionBean, request)) {
                    request.setAttribute(MessageVarList.JSP_ERROR, errormsg);
                    request.setAttribute(RequestVarList.SECTION_ADD_BEAN, sectionBean);
                    request.setAttribute("operationtype", "update");
                } else {



                    this.setAudittraceValue(request);

                    int j = SectionManager.getInstance().editSection(sectionBean, systemAuditBean);
                    if (j == 1) {
                        request.setAttribute("operationtype", "view");
                        request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.SECTION_EDIT_SUCCESS);
                    } else {
                        request.setAttribute("operationtype", "update");
                    }

                }
            } catch (ValidateException e) {
                request.setAttribute("operationtype", "update");
                request.setAttribute(RequestVarList.SECTION_ADD_BEAN, sectionBean);
                allSectionList = SectionManager.getInstance().getAllSection();
                request.setAttribute(RequestVarList.SECTION_DETAILS_LIST, allSectionList);
                request.setAttribute(RequestVarList.SECTION_STATUS_LIST, statusBean);
                throw e;
            }

            allSectionList = SectionManager.getInstance().getAllSection();
            request.setAttribute(RequestVarList.SECTION_DETAILS_LIST, allSectionList);
            request.setAttribute(RequestVarList.SECTION_STATUS_LIST, statusBean);

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
            rd = getServletContext().getRequestDispatcher("/LoadSectionMgtServlet");
            rd.include(request, response);
        } catch (ValidateException e) {
            request.setAttribute(MessageVarList.JSP_ERROR, errormsg);
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (NumberFormatException nfe) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.SECTION_SORTKEY_ERROR);
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.toString()));
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
        }
    }

    /**
     * 
     * @param secBean
     * @return
     * @throws Exception 
     */
    private boolean validateAddValue(SectionBean secBean, HttpServletRequest request) throws Exception {
        boolean flag = true;
        try {
            errormsg = "";

            if (request.getParameter("editSortKey").isEmpty()) {
                flag = false;
                if (errormsg.equals("")) {
                    errormsg = MessageVarList.SECTION_SORTKEY_NULL;
                    throw new ValidateException();
                }
            } else {
                try {
                    sectionBean.setSortKey(request.getParameter("editSortKey"));
                    Integer.parseInt(request.getParameter("editSortKey"));

                } catch (Exception e) {
                    flag = false;
                    if (errormsg.equals("")) {
                        errormsg = MessageVarList.SECTION_SORTKEY_ERROR;
                        throw new ValidateException();
                    }
                }
            }


            if (request.getParameter("editDescription").isEmpty()) {
                flag = false;
                if (errormsg.equals("")) {

                    errormsg = MessageVarList.SECTION_DESCRIPTION_NULL;
                    throw new ValidateException();
                }
            } else if (!UserInputValidator.isCorrectString(secBean.getDescription())) {
                flag = false;
                if (errormsg.equals("")) {
                    errormsg = MessageVarList.SECTION_DES_INVALIDE;
                    throw new ValidateException();
                }
            } else {
                sectionBean.setDescription(request.getParameter("editDescription"));
            }

            if (request.getParameter("editStatus").isEmpty()) {
                flag = false;
                if (errormsg.equals("")) {
                    errormsg = MessageVarList.SECTION_STATUS_NULL;
                    throw new ValidateException();
                }
            } else {
                sectionBean.setStatusCode(request.getParameter("editStatus"));
            }


            return flag;
        } catch (Exception e) {
            throw e;
        }
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("editSecCode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update section, section code : " + uniqueId + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.SECTION);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
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
