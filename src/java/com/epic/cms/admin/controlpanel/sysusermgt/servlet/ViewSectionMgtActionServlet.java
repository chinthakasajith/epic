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
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
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
public class ViewSectionMgtActionServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SessionUser sessionUser = null;
    private String url = "/administrator/controlpanel/systemusermgt/sectionmgthome.jsp";
    private SectionBean sectionBean = null;
    private SystemUserManager sysUserOnj = null;
    private String errormsg;
    private List<StatusBean> statusBean = null;
    private List<SectionBean> allSectionList = null;
    private SystemUserManager systemUserManager = null;

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

            request.setAttribute("operationtype", "edit");
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
            sysUserOnj = new SystemUserManager();
            statusBean = sysUserOnj.getStatusByUserRole("GENR");
            sectionBean = new SectionBean();

            String sortKey = request.getParameter("editSortKey");
            String secCode = request.getParameter("editSecCode");
            String description = request.getParameter("editDescription");
            String status = request.getParameter("editStatus");

            sectionBean.setSectionCode(secCode);
            try {
                sectionBean.setSortKey(sortKey);
            } catch (Exception e) {
                throw new NumberFormatException();
            }
            sectionBean.setStatusCode(status);
            sectionBean.setDescription(description);
            sectionBean.setCreatedTime(new Date());
            sectionBean.setLasUpdatedUser(sessionUser.getUserName());
            sectionBean.setLastUpdatedTime(new Date());
            if (!this.validateAddValue(sectionBean)) {
                request.setAttribute(MessageVarList.JSP_ERROR, errormsg);
            } else {
                this.setAudittraceValue(request);
                int j = SectionManager.getInstance().editSection(sectionBean, systemAuditBean);
                request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.SECTION_EDIT_SUCCESS);
            }

            allSectionList = SectionManager.getInstance().getAllSection();
            request.setAttribute(RequestVarList.SECTION_DETAILS_LIST, allSectionList);
            request.setAttribute(RequestVarList.SECTION_STATUS_LIST, statusBean);

            rd = request.getRequestDispatcher(url);
        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (NumberFormatException nfe) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.SECTION_SORTKEY_ERROR);
            rd = request.getRequestDispatcher(url);
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.toString()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
        }
    }

    /**
     * 
     * @param secBean
     * @return
     * @throws Exception 
     */
    private boolean validateAddValue(SectionBean secBean) throws Exception {
        boolean flag = true;
        try {
            errormsg = "";
            if (secBean.getDescription().contentEquals("") || secBean.getDescription().length() <= 0) {
                flag = false;
                if (errormsg.equals("")) {
                    errormsg = MessageVarList.SECTION_DESCRIPTION_NULL;
                }
            } else if (secBean.getStatusCode().contentEquals("") || secBean.getStatusCode().length() <= 0) {
                flag = false;
                if (errormsg.equals("")) {
                    errormsg = MessageVarList.SECTION_STATUS_NULL;
                }
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
            systemAuditBean.setDescription("UPDATE " + uniqueId + " Section  by " + sessionVarlist.getCMSSessionUser().getUserName());
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
