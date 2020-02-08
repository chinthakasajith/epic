/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CountryMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CountryMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.exception.ValidateException;
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
public class UpdateCountryServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/administrator/controlpanel/transactionMgt/countrymgthome.jsp";
    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;

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
        List<CountryMgtBean> countryList = null;
        boolean flag = true;
        boolean row = false;
        String errorMsg = "";
        CountryMgtBean countryBean = null;
        CountryMgtBean oldBean = null;
        try {
            try {
                request.setAttribute("operationtype", "add");
                HttpSession sessionObj = request.getSession(false);
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

            String countryCode = request.getParameter("counrtCode");

            for (CountryMgtBean bean : CountryMgtManager.getCountryManager().getAllCountryInfo()) {
                if (String.valueOf(bean.getCountryCode()).equals(countryCode)) {
                    oldBean = bean;
                }
            }

            countryBean = new CountryMgtBean();

            countryBean.setDescription(request.getParameter("description"));
            countryBean.setCountryCode(countryCode);
            countryBean.setAlphaFirst(request.getParameter("AlpaFirstCode"));
            countryBean.setAlphaSecond(request.getParameter("AlpaScndCode"));
            countryBean.setLastUpdateUser(sessionUser.getUserName());
            countryBean.setRegion(request.getParameter("region"));
            countryBean.setFrdVal(request.getParameter("frdValue"));


            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.UPDATE)) {
                throw new AccessDeniedException();
            }

            if (CountryMgtManager.getCountryManager().isValidInput(countryBean)) {
                this.setAudittraceValue(request, countryBean, oldBean);
                row = CountryMgtManager.getCountryManager().updateCountry(countryBean, systemAuditBean);
            }

            if (row) {
                request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.COUNTRYMGT_UPDATE_SUCCESS + " code: " + countryCode );
            } else {
                request.setAttribute("operationtype", "update");
                request.setAttribute("cuBean", countryBean);
                request.setAttribute(MessageVarList.JSP_ERROR, "Update Failed");
                rd = getServletContext().getRequestDispatcher(url);

            }


            rd = request.getRequestDispatcher(url);

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadCountryMgtServlet");
            rd.include(request, response);
        } catch (ValidateException ex) {
            request.setAttribute(MessageVarList.COUNTRYMGT_BEAN, countryBean);
            request.setAttribute("operationtype", "update");
            request.setAttribute(MessageVarList.JSP_ERROR, CountryMgtManager.getCountryManager().getErrorMsg());
            rd = request.getRequestDispatcher(url);

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            try {
                countryList = CountryMgtManager.getCountryManager().getAllCountryInfo();
                request.setAttribute(RequestVarList.COUNTRYMGT_LIST, countryList);
                rd.forward(request, response);
            } catch (Exception ex) {
                request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.COUNTRYMGT_LOADTABLE_FAIL);
                rd = request.getRequestDispatcher(url);
            }
        }
    }

    private void setAudittraceValue(HttpServletRequest request, CountryMgtBean newBean, CountryMgtBean oldBean) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("counrtCode");


            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Country. Country Code: " + uniqueId + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
            systemAuditBean.setPageCode(PageVarList.COUNTRY);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("Country");
            //set old value of change if required
            systemAuditBean.setOldValue(oldBean.getCountryCode() + "|"
                    + oldBean.getAlphaFirst() + "|" + oldBean.getAlphaSecond() + "|"
                    + oldBean.getDescription() + "|" + oldBean.getFrdVal() + "|" + oldBean.getRegion());
            //set new value of change if required
            systemAuditBean.setNewValue(newBean.getCountryCode() + "|"
                    + newBean.getAlphaFirst() + "|" + newBean.getAlphaSecond() + "|"
                    + newBean.getDescription() + "|" + newBean.getFrdVal() + "|" + newBean.getRegion());


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
