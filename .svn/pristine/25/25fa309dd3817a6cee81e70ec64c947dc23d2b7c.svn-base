/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ChannelIncomeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.TxnTypeMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.RequestVarList;
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
public class RemoveChannelDataFromSessionServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private List<ChannelIncomeBean> incomeChannelList = null;
    private TypeMgtBean txnTypeBean = null;
    private String opType;
    private String url = "/administrator/controlpanel/transactionMgt/txntypemgthome.jsp";

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
        List<TypeMgtBean> typeList = null;
        try {
            try {

                opType = request.getParameter("operationtype");
                if (opType.equals("add")) {
                    request.setAttribute("operationtype", "add");
                } else {
                    request.setAttribute("operationtype", "update");
                }
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

                try {
                    //set page code and task codes
                    String pageCode = PageVarList.TXNTYPE;
                    String taskCode = TaskVarList.DELETE;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    } else {
                        throw new AccessDeniedException();

                    }

                    sessionVarlist.setUserPageTaskList(userTaskList);

                } catch (AccessDeniedException adex) {
                    throw adex;

                }
            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }
            //////////////////////////////////////////////

            TxnTypeMgtManager txntm = new TxnTypeMgtManager();
            //get transaction type details for table view
            typeList = txntm.getTxnTypeDetails();

            setUserInputToTempBean(request);

            for (int j = 0; j < sessionVarlist.getIncomeChannelList().size(); j++) {
                if (sessionVarlist.getIncomeChannelList().get(j).getChannelId().equals(request.getParameter("channelId"))) {
                    sessionVarlist.getIncomeChannelList().remove(j);
                    break;
                }

            }
            //if (txnTypeBean != null & sessionVarlist.getSessionIncomeList() != null) {

            // request.setAttribute("operationtype", "default");
            if (opType.equals("add")) {
                request.setAttribute("operationtype", "add");
            } else {
                request.setAttribute("operationtype", "update");
            }
            request.setAttribute("txnTypeBean", txnTypeBean);
            request.setAttribute(RequestVarList.TXNTYPEMGT_LIST, typeList);
            // }

            rd = request.getRequestDispatcher(url);

            ////////////////////////////////////////////////

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
        }
    }

    public Boolean setUserInputToTempBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            txnTypeBean = new TypeMgtBean();

            String transactionTypeCode = request.getParameter("transactionTypeCode").trim();
            String description = request.getParameter("description").trim();
            String financialStatus = request.getParameter("financialStatus");
            String visaCode = request.getParameter("visaCode").trim();
            String masterCode = request.getParameter("masterCode").trim();
            String amexCode = request.getParameter("amexCode").trim();
            String onlineTxnCode = request.getParameter("selectOnlineTransactionType").trim();
            String selectstatuscode = request.getParameter("selectstatuscode").trim();

            txnTypeBean.setTransactionTypeCode(transactionTypeCode);
            txnTypeBean.setDescription(description);
            txnTypeBean.setFinancialStatus(financialStatus);
            txnTypeBean.setVisaCode(visaCode);
            txnTypeBean.setMasterCode(masterCode);
            txnTypeBean.setAmexCode(amexCode);
            txnTypeBean.setOnlineTxnCode(onlineTxnCode);
            txnTypeBean.setTrueStatusCode(selectstatuscode);
            txnTypeBean.setLastUpdateUser(sessionUser.getUserName());

        } catch (Exception e) {
            status = false;
            throw e;

        }

        return status;
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
