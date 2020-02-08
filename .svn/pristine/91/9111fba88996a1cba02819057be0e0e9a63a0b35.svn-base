/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.terminalmgt.terminaldata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.TerminalDataCaptureBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.businesslogic.TerminalDataCaptureManager;
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
 *  this  class updates terminal data
 * @author nisansala
 */
public class UpdateTerminalDataCaptureServlet extends HttpServlet {

    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;
    private String errorMessage = null;
    private boolean successUpdate = false;
    private List<String> userTaskList;
    private List<TerminalDataCaptureBean> terminalList = null;
    private TerminalDataCaptureManager terminalManager;
    private String url1 = "/mtmm/terminalmgt/createterminal.jsp";
    private String url = "/mtmm/terminalmgt/terminalmgthome.jsp";
    private HashMap<String, String> allocateList = null;
    private HashMap<String, String> terminalStatusList = null;
    private HashMap<String, String> manufacturerList = null;
    private TerminalDataCaptureBean terminalBean = null;
    private List<TypeMgtBean> txnTypes = null;
    List<TerminalDataCaptureBean> assignedTxnList;
    List<TerminalDataCaptureBean> notAssignedTxnList;
    TerminalDataCaptureBean assignedBean;
    TerminalDataCaptureBean notAssignedBean;
    private String newValue;
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
                String pageCode = PageVarList.TRMINAL_MGT;
                String taskCode = TaskVarList.UPDATE;

                //check whether user_role have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        String[] notAssignedTxn;
                        String[] assignedTxn;
                        //if the arrays are not initialized it gives errors
                        if (request.getParameter("assigned").equals("0")) {
                            assignedTxn = new String[0];
                        } else {
                            assignedTxn = request.getParameterValues("assignlist");
                        }

                        if (request.getParameter("notassigned").equals("0")) {
                            notAssignedTxn = new String[0];
                        } else {
                            notAssignedTxn = request.getParameterValues("notassignlist");
                        }


//                        
                        //load values for the drop down lists
                        this.getAllManufacturers();
                        this.getAllocationStatus();
                        this.getTerminalStatus();
                        this.getAllTxnTypes();
                        request.setAttribute("terminalStatusList", terminalStatusList);
                        request.setAttribute("allocateList", allocateList);
                        request.setAttribute("manufacturerList", manufacturerList);
                        
                        if (setUserInputToBean(request, assignedTxn, notAssignedTxn)) {
                            //validate user input
                            if (validateUserInput(terminalBean)) {
                                request.setAttribute("operationtype", "search");

                                //set values to the audit trace
                                this.setAudittraceValue(request);
                                successUpdate = updateTerminal(terminalBean, systemAuditBean, assignedTxnList, assignedTxn, terminalBean.getTerminalID(),terminalBean.getMerchantID());
                                if (successUpdate) {
                                    request.setAttribute("successMsg", " " + MessageVarList.TERMINAL_MGT_TERMINAL_SUCCESS_UPDATE + "Terminal ID " + terminalBean.getTerminalID());
                                }
                                rd = getServletContext().getRequestDispatcher(url);

                            } else {
                                request.setAttribute("isAllocate", terminalBean.getAllocationStatus());
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("terminalBean", terminalBean);
                                request.setAttribute("assignedTxn", assignedTxnList);
                                request.setAttribute("notAssignedTxn", notAssignedTxnList);
                                request.setAttribute("errorMsg", errorMessage);

                                rd = getServletContext().getRequestDispatcher(url1);
                            }
                        } else {

                            request.setAttribute("operationtype", "update");
                            request.setAttribute("terminalBean", terminalBean);
                            request.setAttribute("assignedTxn", assignedTxnList);
                            request.setAttribute("notAssignedTxn", notAssignedTxnList);
                            request.setAttribute("errorMsg", errorMessage);

                            rd = getServletContext().getRequestDispatcher(url1);
                        }
                        rd.forward(request, response);

                    } catch (Exception e) {

                        request.setAttribute("operationtype", "update");
                        request.setAttribute("terminalBean", terminalBean);
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);

                        rd = getServletContext().getRequestDispatcher(url1);
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
     * check whether user has the requires access to the page
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

    /**
     * set values to the audit trace
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(terminalBean.getTerminalID());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Terminal" + " Terminal ID = " + terminalBean.getTerminalID() + "; by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.MERCHANT_TERMINAL_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TERMINALMGT);
            systemAuditBean.setPageCode(PageVarList.TRMINAL_MGT);
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

    /**
     * assign user given values to the bean to update terminal
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request, String[] assTxn, String[] notAssTxn) throws Exception {
        Boolean status = true;
        try {

            terminalBean = new TerminalDataCaptureBean();

            terminalBean.setTerminalID(request.getParameter("terminalid").trim());
            terminalBean.setName(request.getParameter("name").trim());
            //terminalBean.setMerchantDes(request.getParameter(""));
            terminalBean.setMerchantID(request.getParameter("mid"));
            terminalBean.setMcc(request.getParameter("mcc"));
            terminalBean.setCurrency(request.getParameter("currency"));
            terminalBean.setSerialNo(request.getParameter("serialnumber").trim());
            terminalBean.setManufacturer(request.getParameter("manufacturer").trim());
            terminalBean.setModel(request.getParameter("model").trim());
            terminalBean.setInstallationDate(request.getParameter("installationDate").trim());
            terminalBean.setActivationDate(request.getParameter("activationDate").trim());
            terminalBean.setAllocationStatus(request.getParameter("allocatestatuscode"));
            terminalBean.setAlloStatus(request.getParameter("allocatestatus"));
            terminalBean.setTerminalStatus(request.getParameter("terminalstatuscode"));
            terminalBean.setTerminalStatusDes(request.getParameter("terminalstatus"));

            assignedTxnList = new ArrayList<TerminalDataCaptureBean>();
            notAssignedTxnList = new ArrayList<TerminalDataCaptureBean>();

            

            int k = 0;
            if (assTxn.length != 0) {
                while (assTxn.length > k) {
                    assignedBean = new TerminalDataCaptureBean();

                    assignedBean.setTransactions(assTxn[k]);
                    for (int i = 0; i < txnTypes.size(); i++) {
                        if (assignedBean.getTransactions().equals(txnTypes.get(i).getTransactionTypeCode())) {
                            assignedBean.setTransactionDes(txnTypes.get(i).getDescription());
                            assignedBean.setOnlineTxnCode(txnTypes.get(i).getOnlineTxnCode());
                        }
                    }

                    assignedTxnList.add(assignedBean);
                    k++;
                }
            }
            int l = 0;
            while (notAssTxn.length > l) {
                notAssignedBean = new TerminalDataCaptureBean();

                notAssignedBean.setTransactions(notAssTxn[l]);
                for (int i = 0; i < txnTypes.size(); i++) {
                    if (notAssignedBean.getTransactions().equals(txnTypes.get(i).getTransactionTypeCode())) {
                        notAssignedBean.setTransactionDes(txnTypes.get(i).getDescription());
                        notAssignedBean.setOnlineTxnCode(txnTypes.get(i).getOnlineTxnCode());
                    }
                }
                notAssignedTxnList.add(notAssignedBean);
                l++;
            }


            terminalBean.setLastUpdateUser(sessionUser.getUserName());

            newValue = terminalBean.getTerminalID() + "|" + terminalBean.getName() + "|" + terminalBean.getSerialNo() + "|"
                    + terminalBean.getManufacturer() + "|" + terminalBean.getModel() + "|" + terminalBean.getInstallationDate() + "|" + 
                    terminalBean.getAllocationStatus() + "|" + terminalBean.getTerminalStatus() + "|" + terminalBean.getLastUpdateUser();
            
            String terminalID = request.getParameter("id");
            this.getAllTerminalData();
            //because two disable input fields
            for (TerminalDataCaptureBean terminalBeanBean : terminalList) {
                if (terminalBeanBean.getTerminalID().equals(terminalID)) {

            oldValue = terminalBeanBean.getTerminalID() + "|" + terminalBeanBean.getName() + "|" + terminalBeanBean.getSerialNo() + "|"
                    + terminalBeanBean.getManufacturer() + "|" + terminalBeanBean.getModel() + "|" + terminalBeanBean.getInstallationDate() + "|" + 
                    terminalBeanBean.getAllocationStatus() + "|" + terminalBeanBean.getTerminalStatus() + "|" + terminalBeanBean.getLastUpdateUser();


                }
            }

        } catch (Exception e) {
            status = false;
            throw e;
        }

        return status;
    }

    /**
     * validate the user input before updating
     * @param terminalBean
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(TerminalDataCaptureBean terminalBean) throws Exception {
        boolean isValidate = true;


        try {
            if (terminalBean.getTerminalID().contentEquals("") || terminalBean.getTerminalID().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_TRMNLID_NULL;
            } else if (!UserInputValidator.isNumeric(terminalBean.getTerminalID())) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_TRMNLNAME_NULL;
            } else if (terminalBean.getName().contentEquals("") || terminalBean.getName().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_TRMNLNAME_NULL;

            } else if (!UserInputValidator.isDescription(terminalBean.getName()) || terminalBean.getName().contentEquals("'") || terminalBean.getName().contentEquals("'''")) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_TRMNLNAME_INVALID;

            } else if (terminalBean.getSerialNo().contentEquals("") || terminalBean.getSerialNo().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_SERIALNO_NULL;

            }else if (!UserInputValidator.isAlphaNumeric(terminalBean.getSerialNo())) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_SERIALNO_INVALID;
            } 
            else if (terminalBean.getManufacturer().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_MANUFACT_NULL;

            } else if (terminalBean.getModel().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_MODEL_NULL;

            } else if (terminalBean.getInstallationDate().contentEquals("") || terminalBean.getInstallationDate().isEmpty()) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_INSTAL_DATE_NULL;

            } 
            
//            else if (terminalBean.getActivationDate().contentEquals("") || terminalBean.getActivationDate().isEmpty()) {
//                isValidate = false;
//                errorMessage = MessageVarList.TERMINAL_MGT_ACTIVATE_DATE_NULL;
//
//            }


        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    /**
     * update the terminal
     * @param terminalBean
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean updateTerminal(TerminalDataCaptureBean terminalBean, SystemAuditBean systemAuditBean, List<TerminalDataCaptureBean> assignedBean, String[] transactions, String tid , String mid) throws Exception {
        boolean success = false;
        try {
            terminalManager = new TerminalDataCaptureManager();
            success = terminalManager.updateTerminal(terminalBean, systemAuditBean, assignedBean, transactions, tid,mid);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private void getAllTerminalData() throws Exception {
        try {

            terminalList = new ArrayList<TerminalDataCaptureBean>();
            terminalManager = new TerminalDataCaptureManager();
            terminalList = terminalManager.getAllTerminalData();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getAllocationStatus() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            allocateList = terminalManager.getAllocationStatus();
        } catch (Exception e) {
        }
    }

    /**
     * get descriptions of terminal status
     */
    private void getTerminalStatus() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            terminalStatusList = terminalManager.getTerminalStatus();
        } catch (Exception e) {
        }

    }

    private void getAllManufacturers() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            manufacturerList = terminalManager.getAllManufacturers();
        } catch (Exception e) {
        }

    }

    private void getAllTxnTypes() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            txnTypes = terminalManager.getAllTxnTypes();
        } catch (Exception e) {
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
