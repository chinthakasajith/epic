/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantlocation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.bean.MerchantLocationBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.businesslogic.MerchantLocationManager;
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
 * @author nalin
 */
public class DeleteMerchantLocationServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private MerchantLocationManager merchantLocManager;
    private String merchantId;
    private MerchantLocationBean merchantLocBean;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private String url = "/mtmm/merchantmgt/merchantlocationhome.jsp";
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
            /////////////////////////////////////////////////////////////////////

            try {
                //set page code and task codes
                String pageCode = PageVarList.MERCHANT_LOCATION;
                String taskCode = TaskVarList.DELETE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    merchantId = request.getParameter("merchantId");
                    merchantLocBean = new MerchantLocationBean();
                    merchantLocBean.setMerchantId(merchantId);

            merchantLocManager = new MerchantLocationManager();
            List<MerchantLocationBean> beanList = merchantLocManager.getMerchantLocationDetails();
            for (MerchantLocationBean newMerchantLocBean : beanList) {

                if (newMerchantLocBean.getMerchantId().equals(merchantId)) {

                    oldValue = newMerchantLocBean.getMerchantId() + "|" + newMerchantLocBean.getMerchantCustomerNumber() + "|" + newMerchantLocBean.getDescription() + "|" + newMerchantLocBean.getAddress1() + "|" + newMerchantLocBean.getAddress2() + "|" + newMerchantLocBean.getAddress3() + "|"
                            + newMerchantLocBean.getArea() + "|" + newMerchantLocBean.getCountry() + "|" + newMerchantLocBean.getCpTitle() + "|" + newMerchantLocBean.getCpFirstName() + "|" + newMerchantLocBean.getCpMiddleName() + "|" + newMerchantLocBean.getCpLastName() + "|" + newMerchantLocBean.getCpPosotion() + "|"
                            + newMerchantLocBean.getTpNumber() + "|" + newMerchantLocBean.getFax() + "|" + newMerchantLocBean.geteMail() + "|" + newMerchantLocBean.getFeeProfile() + "|" + newMerchantLocBean.getCommissionProfile() + "|" + newMerchantLocBean.getRiskProfile() + "|" + newMerchantLocBean.getActivationDate() + "|" + newMerchantLocBean.getStatus() + "|" + newMerchantLocBean.getBankCode() + "|"
                            + newMerchantLocBean.getBranchCode() + "|" + newMerchantLocBean.getAccountNumber() + "|" + newMerchantLocBean.getAccountType() + "|" + newMerchantLocBean.getAccountName() + "|" + newMerchantLocBean.getPaymentMode() + "|" + newMerchantLocBean.getPaymentCycle() + "|" + newMerchantLocBean.getStatementCycle() + "|" + newMerchantLocBean.getStatementStatus() + "|" + newMerchantLocBean.getCurrencyType() + "|"
                            + newMerchantLocBean.getRedempoint() + "|" + newMerchantLocBean.getLastUpdatedUser();


                }
            }                    
                    
                    this.setAudittraceValue(request);

                    try {
                        boolean delResult = deleteMerchantLocation(merchantLocBean, systemAuditBean);

                        if (delResult) {
                            request.setAttribute("successMsg", MessageVarList.MERCHANT_LOCATION_SUCCESS_DELETE + " " + merchantId);
                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                            request.setAttribute("operationtype", "search");
                            rd = getServletContext().getRequestDispatcher("/LoadMerchantLocationServlet");
                        }
                        rd.forward(request, response);

                    } catch (SQLException e) {
                        OracleMessage message = new OracleMessage();
                        String oraMessage = message.getMessege(e.getMessage());

                        request.setAttribute("operationtype", "search");
                        request.setAttribute("errorMsg", oraMessage);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    } catch (Exception ee) {
                        request.setAttribute("operationtype", "search");
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

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {
        } finally {
            out.close();
        }
    }

    /**
     *  do audit trace
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------

            String uniqueId = request.getParameter(merchantLocBean.getMerchantId());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            //systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Delete Merchant Location. Merchant Location Id: " + merchantLocBean.getMerchantId() + " ; by : " + sessionVarlist.getCMSSessionUser().getUserName());

            systemAuditBean.setApplicationCode(ApplicationVarList.MERCHANT_TERMINAL_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.MERCHANTMGT);
            systemAuditBean.setPageCode(PageVarList.MERCHANT_LOCATION);
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

    /**
     * 
     * @param txnType
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean deleteMerchantLocation(MerchantLocationBean merchantLocBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            merchantLocManager = new MerchantLocationManager();
            success = merchantLocManager.deleteMerchantLocation(merchantLocBean, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }

    /**
     * 
     * @param userrole
     * @param pagecode
     * @param task
     * @return
     * @throws Exception 
     */
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
