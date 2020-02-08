/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantlocation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantCustomerBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.businesslogic.MerchantCustomerManager;
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
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class AddMerchantLocationAssignDataServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;
    private boolean successInsertToMcc = true;
    private boolean successInsertToTxnType = true;
    private boolean successInsertToCurrency = true;
    private List<String> userTaskList;
    private String[] assignMerchantCategorylist;
    private String[] assignTxnTypeList = null;
    private String[] assignCurrencyList = null;
    private List<TypeMgtBean> typeList = null;
    private List<MerchantCategoryBean> merchantCategoryList = null;
    private MerchantLocationManager merchantLocManager = null;
    private List<MerchantCustomerBean> merchantCustomerList = null;
    private List<CurrencyBean> currencyList = null;
    private String merchantId = null;
    private String merchantCustomerNumber = null;
    private String url = "/mtmm/merchantmgt/createmerchantlocation.jsp";

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
                String taskCode = TaskVarList.ADD;

                ///////////////////////////////////////////////////////////////////////////
                MerchantCustomerManager ccm = new MerchantCustomerManager();
                merchantCustomerList = new ArrayList<MerchantCustomerBean>();
                //get  MerchantCustomer Name details
                merchantCustomerList = ccm.getMerchantCustomerDetails();

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    merchantId = request.getParameter("merchantId").trim();
                    merchantCustomerNumber = request.getParameter("selectMerchantCustomerName").trim();

                    assignMerchantCategorylist = request.getParameterValues("assignMerchantCategorylist");
                    assignTxnTypeList = request.getParameterValues("assignTxnTypeList");
                    assignCurrencyList = request.getParameterValues("assignCurrencyList");
                    try {

                        request.setAttribute("operationtype", "ADD");

                        this.setAudittraceValue(request);

                        try {

                            successInsertToMcc = insertToMcc(merchantId, assignMerchantCategorylist, systemAuditBean);
                            successInsertToTxnType = insertToTxnType(merchantId, assignTxnTypeList, systemAuditBean);
                            successInsertToCurrency = insertToCurrency(merchantId, assignCurrencyList, systemAuditBean);


                            if (sessionVarlist.getManualTxnStatus().equals("NO")) {
                                request.setAttribute("successMsg", MessageVarList.MERCHANT_LOCATION_ASSIGN_SUCCESS);
                                rd = getServletContext().getRequestDispatcher("/LoadCreateMerchantLocationServlet");
                                request.setAttribute("selectedtab", "0");
                            } else if (sessionVarlist.getManualTxnStatus().equals("YES")) {
                                sessionVarlist.setMerchantId(merchantId);
                                request.setAttribute("successMsg", MessageVarList.MERCHANT_LOCATION_ASSIGN_SUCCESS);
                                rd = getServletContext().getRequestDispatcher("/LoadMerchantLocationManualTxnServlet");
                            }
                            rd.forward(request, response);

                        } catch (SQLException e) {

                            OracleMessage message = new OracleMessage();
                            String oraMessage = message.getMessege(e.getMessage());
                            request.setAttribute("operationtype", "ADD");
                            request.setAttribute("errorMsg", oraMessage);
                            this.setValueToAssignBox(merchantCustomerNumber);
                            request.setAttribute(RequestVarList.TXNTYPEMGT_LIST, typeList);
                            request.setAttribute(RequestVarList.MERCHANTCATEGORY_LIST, merchantCategoryList);
                            request.setAttribute(RequestVarList.CURRENCY_LIST, currencyList);
                            request.setAttribute(RequestVarList.MERCHANTCUSTOMER_LIST, merchantCustomerList);
                            rd = getServletContext().getRequestDispatcher(url);
                            request.setAttribute("selectedtab", "1");
                            request.setAttribute("readOnly", "true");
                            rd.forward(request, response);
                        }

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "ADD");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        this.setValueToAssignBox(merchantCustomerNumber);
                        request.setAttribute(RequestVarList.TXNTYPEMGT_LIST, typeList);
                        request.setAttribute(RequestVarList.MERCHANTCATEGORY_LIST, merchantCategoryList);
                        request.setAttribute(RequestVarList.CURRENCY_LIST, currencyList);
                        request.setAttribute(RequestVarList.MERCHANTCUSTOMER_LIST, merchantCustomerList);
                        rd = getServletContext().getRequestDispatcher(url);
                        request.setAttribute("selectedtab", "1");
                        request.setAttribute("readOnly", "true");
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
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            out.close();
        }
    }

    public boolean insertToMcc(String merchantCustomerNumber, String[] assignMerchantCategorylist, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            merchantLocManager = new MerchantLocationManager();
            success = merchantLocManager.insertToMcc(merchantCustomerNumber, assignMerchantCategorylist, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    public boolean insertToTxnType(String merchantCustomerNumber, String[] assignTxnTypeList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            merchantLocManager = new MerchantLocationManager();
            success = merchantLocManager.insertToTxnType(merchantCustomerNumber, assignTxnTypeList, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    public boolean insertToCurrency(String merchantCustomerNumber, String[] assignCurrencyList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            merchantLocManager = new MerchantLocationManager();
            success = merchantLocManager.insertToCurrency(merchantCustomerNumber, assignCurrencyList, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    /**
     * 
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = merchantId;

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Assign Data to: '" + merchantId + "' Merchant Location ID by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.MERCHANT_TERMINAL_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.MERCHANTMGT);
            systemAuditBean.setPageCode(PageVarList.MERCHANT_LOCATION);
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

    private void setValueToAssignBox(String merchantCustomerNumber) throws Exception {

        try {

            merchantLocManager = new MerchantLocationManager();


            merchantCategoryList = new ArrayList<MerchantCategoryBean>();
            merchantCategoryList = merchantLocManager.getMccByMerchantCustomerNumber(merchantCustomerNumber);


            typeList = new ArrayList<TypeMgtBean>();
            typeList = merchantLocManager.getTxnTypeByMerchantCustomerNumber(merchantCustomerNumber);


            currencyList = new ArrayList<CurrencyBean>();
            currencyList = merchantLocManager.getCurrencyByMerchantCustomerNumber(merchantCustomerNumber);


        } catch (Exception ex) {
            throw ex;
        }

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
