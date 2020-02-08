/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ChannelIncomeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.AcquireTxnTypeManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
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
 * @author nisansala
 */
public class UpdateAcquireTxnTypeServlet extends HttpServlet {
    //initializing variables

    private String oldValue = "";
    private String newValue = "";
    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private String errorMessage = "";
    private SessionUser sessionUser = null;
    private List<String> userTaskList = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    //-------------------------------------------------
    private String successMessage = "";
    private ChannelIncomeBean txn;
    private List<ChannelIncomeBean> txnList;
    private AcquireTxnTypeManager acquirerMgr;
    private String url = "/administrator/controlpanel/transactionMgt/acquiretxntypehome.jsp";

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
                String pageCode = PageVarList.ACQUIRE_TXN_TYPE;
                String taskCode = TaskVarList.UPDATE;

                //check whether user_role have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        if (setUserInputToBean(request)) {


                            //validate user input
                            if (validateUserInput(txn)) {
                                //set values to the audit trace
                                this.setAudittraceValue(request);

                                int successUpdate = updateAcquirerTxnTypes(txn, systemAuditBean);

                                if (successUpdate == 1) {
                                    successMessage = MessageVarList.ACQ_TXN_TYPE_SUCCESS_UPDATE + " ";
                                    //request.setAttribute("successMsg", MessageVarList.ACQ_TXN_TYPE_SUCCESS_UPDATE );

                                } else {
                                    request.setAttribute("errorMsg", " " + "Acquirer transaction " + MessageVarList.SECURITY_QUES_MGT_QUESTION_NO_UNSUCCESS_UPDATE);


                                }
                                this.getAllAcquirerTxnTypeData();
                                request.setAttribute("eodBeanList", txnList);

                                for (ChannelIncomeBean bean : txnList) {
                                    if (request.getParameter("mti").equals(bean.getMti()) && request.getParameter("processingCode").equals(bean.getProcessingCode())) {
                                        successMessage = "'" + bean.getTxnDescription() + "' " +successMessage ;
                                    }
                                }
                                request.setAttribute("successMsg", successMessage);

                                rd = getServletContext().getRequestDispatcher("/LoadAcquireTxnTypeServlet");
                                rd.forward(request, response);

                            } else {
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("EODbean", txn);
                                request.setAttribute("errorMsg", errorMessage);
                                this.getAllAcquirerTxnTypeData();
                                request.setAttribute("eodBeanList", txnList);


                                rd = getServletContext().getRequestDispatcher(url);

                            }

                        } else {
                            request.setAttribute("operationtype", "add");
                            request.setAttribute("errorMsg", errorMessage);

                            rd = getServletContext().getRequestDispatcher(url);

                        }
                    } catch (Exception e) {

                        request.setAttribute("operationtype", "add");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);


                        rd = getServletContext().getRequestDispatcher(url);

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


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);


        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);


        } catch (Exception ex) {
        } finally {
            rd.forward(request, response);
        }
    }

    /**
     * check user has the access to the page for the required task
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

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            txn = new ChannelIncomeBean();

            txn.setTransactionTypeCode(request.getParameter("txntype"));
            txn.setMti(request.getParameter("mti"));
            txn.setProcessingCode(request.getParameter("processingCode"));
            if (!request.getParameter("status").isEmpty()) {
                txn.setStatusToOnline(Integer.parseInt(request.getParameter("status")));
            } else {
                txn.setStatusToOnline(Integer.parseInt("-1"));
            }

            oldValue = request.getParameter("oldValue");
            newValue = txn.getTransactionTypeCode() + "|" + txn.getMti() + "|" + "" + txn.getProcessingCode() + "|" + txn.getStatus();

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    /**
     * insert relevant data to the audit trace table
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Acquirer Txn Type. Acquirer Txn Type Code: '" + request.getParameter("mti") + "+" + request.getParameter("processingCode") + "'  by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
            systemAuditBean.setPageCode(PageVarList.ACQUIRE_TXN_TYPE);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(oldValue);
            //set new value of change if required
            systemAuditBean.setNewValue(newValue);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean validateUserInput(ChannelIncomeBean txn) throws Exception {
        boolean isValidate = true;

        try {
            if (txn.getTransactionTypeCode().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.DESCRIPTION_NULL;
            } else if (txn.getMti().contentEquals("") || txn.getMti().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.MTI_NULL;
            } else if (!UserInputValidator.isNumeric(txn.getMti())) {
                isValidate = false;
                errorMessage = MessageVarList.MTI_INVALID;
            } else if (txn.getProcessingCode().contentEquals("") || txn.getProcessingCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.PROCESSING_CODE_NULL;
            } else if (!UserInputValidator.isNumeric(txn.getProcessingCode())) {
                isValidate = false;
                errorMessage = MessageVarList.PROCESSING_CODE_INVALID;
            } else if (String.valueOf(txn.getStatusToOnline()).equals("-1")) {
                isValidate = false;
                errorMessage = MessageVarList.STATUS_NULL;
            }
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    private void getAllAcquirerTxnTypeData() throws Exception {
        try {

            txnList = new ArrayList<ChannelIncomeBean>();
            acquirerMgr = new AcquireTxnTypeManager();
            //retrieve merchant details
            txnList = acquirerMgr.getAllAcquirerTxnTypeData();
        } catch (Exception e) {
            throw e;
        }
    }

    public int updateAcquirerTxnTypes(ChannelIncomeBean process, SystemAuditBean systemAuditBean) throws Exception {
        int success;
        try {
            acquirerMgr = new AcquireTxnTypeManager();
            success = acquirerMgr.updateAcquirerTxnTypes(process, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
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
