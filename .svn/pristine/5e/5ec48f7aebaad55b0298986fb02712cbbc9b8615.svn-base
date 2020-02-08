/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.StandingOrderTypesBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.UtilityProviderBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.StandingOrderTypesManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author asitha_l
 */
public class LoadStandingOrderTypesServlet extends HttpServlet {

    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String url = "/administrator/controlpanel/systemconfigmgt/standingordertypeshome.jsp";
    private StandingOrderTypesManager standingOrderTypesManager = null;
    private List<StatusBean> statusList = null;
    private HashMap<String, String> currencyMap = null;
    private HashMap<String, String> areaMap = null;
    private HashMap<String, String> bankNames = null;
    private String commonParam = null;
    private List<StandingOrderTypesBean> standingOrderList;
    private List<UtilityProviderBean> utilityProviderList = null;
    private List<FeeTypeBean> feeTypeList = null;
    private String commonBankCode=null;
    private String commonCurrencyCode=null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            try {
                sessionObj = request.getSession(false);
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

                try {
                    //set page code and task codes
                    String pageCode = PageVarList.STANDING_ORDER_TYPES;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        sessionVarlist.setUserPageTaskList(userTaskList);

                        this.getAllStandingOrderData();
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("standingOrderList", standingOrderList);

                        this.getStatus();
                        this.getCurrency();
                        this.getAreas();
                        this.getBanks();
                        this.getCommonparam();
                        this.getUtilityProviderList();
                        this.getFeeTypeList();

                        sessionVarlist.setStatusList(statusList);
                        sessionVarlist.setCurrencyMap(currencyMap);
                        sessionVarlist.setBankNames(bankNames);
                        sessionVarlist.setAreaMap(areaMap);
                        //sessionVarlist.setCommonParam(commonParam);
                        sessionVarlist.setUtilityProviderList(utilityProviderList);
                        sessionVarlist.setFeeTypeList(feeTypeList);
                        sessionVarlist.setCommonBankCode(commonBankCode);
                        sessionVarlist.setCommonCurrencyCode(commonCurrencyCode);

                        sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                        rd = request.getRequestDispatcher(url);

                    } else {
                        throw new AccessDeniedException();

                    }

                } catch (AccessDeniedException adex) {
                    throw adex;

                }

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }
        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);

        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
            out.close();
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

    private void getStatus() throws Exception {
        try {

            standingOrderTypesManager = new StandingOrderTypesManager();
            statusList = new ArrayList<StatusBean>();
            statusList = standingOrderTypesManager.getStatus();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCurrency() throws Exception {
        try {

            standingOrderTypesManager = new StandingOrderTypesManager();
            currencyMap = new HashMap<String, String>();
            currencyMap = standingOrderTypesManager.getCurrency();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAreas() throws Exception {
        try {
            standingOrderTypesManager = new StandingOrderTypesManager();
            areaMap = new HashMap<String, String>();
            areaMap = standingOrderTypesManager.getAreas();
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

    private void getBanks() throws Exception {
        try {
            standingOrderTypesManager = new StandingOrderTypesManager();
            bankNames = new HashMap<String, String>();
            bankNames = standingOrderTypesManager.getBanks();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCommonparam() throws Exception {
        try {
            standingOrderTypesManager = new StandingOrderTypesManager();
            commonParam = standingOrderTypesManager.getCommonparam();
            commonBankCode=commonParam.split(",")[0];
            commonCurrencyCode=commonParam.split(",")[1];
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to retrieve all standing order types data
     *
     * @throws Exception
     */
    private void getAllStandingOrderData() throws Exception {
        try {
            standingOrderTypesManager = new StandingOrderTypesManager();
            standingOrderList = standingOrderTypesManager.getAllStandingOrderData();

        } catch (Exception ex) {
            throw ex;
        }
    }

    //get utility provider list

    private void getUtilityProviderList() throws Exception {
        try {
            standingOrderTypesManager = new StandingOrderTypesManager();
            utilityProviderList = standingOrderTypesManager.getUtilityProviderList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    //get Fee Type List

    private void getFeeTypeList() throws Exception {
        try {
            standingOrderTypesManager = new StandingOrderTypesManager();
            feeTypeList = standingOrderTypesManager.getAllFeeTypes();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
