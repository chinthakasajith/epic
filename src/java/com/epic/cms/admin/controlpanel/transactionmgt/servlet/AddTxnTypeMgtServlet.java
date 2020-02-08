/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
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
public class AddTxnTypeMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private TxnTypeMgtManager txnTypeManager;
    private String errorMessage = null;
    private TypeMgtBean txnTypeBean;
    private boolean successInsert = false;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private ChannelIncomeBean channelBean = null;
    private List<ChannelIncomeBean> channelList = null;
    private String url = "/administrator/controlpanel/transactionMgt/txntypemgthome.jsp";
    private String newValue;
    

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
        channelList = new ArrayList<ChannelIncomeBean>();
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
                String pageCode = PageVarList.TXNTYPE;
                String taskCode = TaskVarList.ADD;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    TxnTypeMgtManager txntm = new TxnTypeMgtManager();
                    //get transaction type to table details
                    typeList = txntm.getTxnTypeDetails();

                    try {
                        setUserInputToBean(request);
                        if (validateUserInput(txnTypeBean)) {
                            request.setAttribute("operationtype", "add");

                            this.setAudittraceValue(request);

                            try {
                                successInsert = txnTypeInsert(txnTypeBean, channelList, systemAuditBean);
                                if (successInsert) {
                                    request.setAttribute("successMsg", txnTypeBean.getTransactionTypeCode() + " " + MessageVarList.TRANSACTION_TYPE_CODE_SUCCESS_ADD);
                                }
                                if (sessionVarlist.getIncomeChannelList() != null & successInsert) {
                                    sessionVarlist.getIncomeChannelList().clear();
                                }

                                if (txnTypeBean != null) {
                                    txnTypeBean = new TypeMgtBean();
                                }

                                typeList = txntm.getTxnTypeDetails();

                                request.setAttribute("txnTypeBean", txnTypeBean);
                                request.setAttribute(RequestVarList.TXNTYPEMGT_LIST, typeList);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd = request.getRequestDispatcher(url);
                                rd.forward(request, response);

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());

                                request.setAttribute("operationtype", "add");
                                request.setAttribute("txnTypeBean", txnTypeBean);
                                request.setAttribute(RequestVarList.TXNTYPEMGT_LIST, typeList);
                                request.setAttribute("errorMsg", oraMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);

                            }

                        } else {
                            request.setAttribute("operationtype", "add");
                            request.setAttribute("txnTypeBean", txnTypeBean);
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute(RequestVarList.TXNTYPEMGT_LIST, typeList);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }


                    } catch (Exception e) {
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("txnTypeBean", txnTypeBean);
                        request.setAttribute(RequestVarList.TXNTYPEMGT_LIST, typeList);
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

            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {

            out.close();
        }
    }

    /**
     * 
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            txnTypeBean = new TypeMgtBean();

            String transactionTypeCode = request.getParameter("transactionTypeCode").trim();
            String description = request.getParameter("description").trim();
            String financialStatus = request.getParameter("financialStatus");
            String visaCode = request.getParameter("visaCode").trim();
            String masterCode = request.getParameter("masterCode").trim();
            String amexCode = request.getParameter("amexCode").trim();
            String onlineTxnCode = request.getParameter("selectOnlineTransactionType");
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
            
            newValue = txnTypeBean.getTransactionTypeCode() + "|" + txnTypeBean.getDescription() + "|"
                    + txnTypeBean.getFinancialStatus() + "|" + txnTypeBean.getVisaCode() + "|" + txnTypeBean.getMasterCode() + "|"
                    + txnTypeBean.getAmexCode() + "|" + txnTypeBean.getOnlineTxnCode() + "|" + txnTypeBean.getTrueStatusCode() + "|" + txnTypeBean.getLastUpdateUser();
            

//            for (int j = 0; j < sessionVarlist.getIncomeChannelList().size(); j++) {
//                channelBean = new ChannelIncomeBean();
//                channelBean = sessionVarlist.getIncomeChannelList().get(j);
//                channelBean.setTransactionTypeCode(transactionTypeCode);
//                channelBean.setOnlineTxnCode(onlineTxnCode);
//                channelBean.setStatus(selectstatuscode);
//
//
//                channelList.add(channelBean);
//
//            }

        } catch (Exception e) {
            status = false;
            throw e;

        }

        return status;
    }

    /**
     * Validate the New User Entry
     * @param txnTypeBean
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(TypeMgtBean txnTypeBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code

        try {

            if (txnTypeBean.getTransactionTypeCode().contentEquals("") || txnTypeBean.getTransactionTypeCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.TRANSACTION_TYPECODE_NULL;

            } else if (!UserInputValidator.isAlphaNumeric(txnTypeBean.getTransactionTypeCode())) {
                isValidate = false;

                errorMessage = MessageVarList.TRANSACTION_TRANSACTIONCODE_INVALID;

            } else if (txnTypeBean.getDescription().contentEquals("") || txnTypeBean.getDescription().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.TRANSACTION_DESCRIPTION_NULL;

            } else if (!UserInputValidator.isDescription(txnTypeBean.getDescription())) {
                isValidate = false;

                errorMessage = MessageVarList.DESCRIPTION_INVALID;

            } else if (txnTypeBean.getVisaCode().contentEquals("") || txnTypeBean.getVisaCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.TRANSACTION_VISACODE_NULL;

            } else if (!UserInputValidator.isAlphaNumeric(txnTypeBean.getVisaCode())) {
                isValidate = false;

                errorMessage = MessageVarList.TRANSACTION_VISACODE_INVALID;

            } else if (txnTypeBean.getMasterCode().contentEquals("") || txnTypeBean.getMasterCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.TRANSACTION_MASTERCODE_NULL;

            } else if (!UserInputValidator.isAlphaNumeric(txnTypeBean.getMasterCode())) {
                isValidate = false;

                errorMessage = MessageVarList.TRANSACTION_MASTERCODE_INVALID;

            } else if (txnTypeBean.getAmexCode().contentEquals("") || txnTypeBean.getAmexCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.TRANSACTION_AMEXCODE_NULL;

            } else if (!UserInputValidator.isAlphaNumeric(txnTypeBean.getAmexCode())) {
                isValidate = false;

                errorMessage = MessageVarList.TRANSACTION_AMEXCODE_INVALID;


            }else if (txnTypeBean.getOnlineTxnCode().contentEquals("") || txnTypeBean.getOnlineTxnCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.TRANSACTION_TYPE_NULL;

            }else if (txnTypeBean.getTrueStatusCode().contentEquals("") || txnTypeBean.getTrueStatusCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.STATUS_NULL;

            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    /**
     * 
     * @param type
     * @return
     * @throws Exception 
     */
    public boolean txnTypeInsert(TypeMgtBean type, List<ChannelIncomeBean> channelList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            /// call insertTxntypeDetails method in TxnTypeMgtManager class

            txnTypeManager = new TxnTypeMgtManager();
            success = txnTypeManager.insertTxntypeDetails(type, channelList, systemAuditBean);
        } catch (SQLException ex) {
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
            String uniqueId = txnTypeBean.getTransactionTypeCode();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Transaction Type Transaction Type Code : " + txnTypeBean.getTransactionTypeCode() + ";  by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
            systemAuditBean.setPageCode(PageVarList.TXNTYPE);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue(newValue);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

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
