/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.terminalmgt.terminaldata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.businesslogic.MerchantLocationManager;
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
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * to add new terminal data
 *
 * @author nisansala
 */
public class AddTerminalDataCaptureServlet extends HttpServlet {

    //initializing variables
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private String errorMessage = null;
    private Boolean successInsert;
    private String url = "/mtmm/terminalmgt/terminalmgthome.jsp";
    private String url1 = "/mtmm/terminalmgt/createterminal.jsp";
    TerminalDataCaptureManager terminalManager;
    HashMap<String, String> difManufactermModels = null;
    private HashMap<String, String> allocateList = null;
    private HashMap<String, String> terminalStatusList = null;
    private HashMap<String, String> manufacturerList = null;
    private TerminalDataCaptureBean terminalBean = null;
    String manufacturer = null;
    private String newValue;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");

        try {
            //call to existing session
            HttpSession sessionObj = request.getSession(false);

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

            try {
                //set page code and task codes
                String pageCode = PageVarList.TRMINAL_MGT;
                String taskCode = TaskVarList.ADD;

                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    //the manufacturer user has selected
                    manufacturer = request.getParameter("manufacturer");
                    terminalManager = new TerminalDataCaptureManager();
                    //retrieve terminal models according to the manufacturer
                    difManufactermModels = terminalManager.getModelsToManufacturer(manufacturer);
                    this.getAllManufacturers();
                    this.getAllocationStatus();
                    this.getTerminalStatus();

                    try {
                        //assign user input to the bean
                        setUserInputToBean(request);

                        //validate user inputs
                        if (validateUserInput(terminalBean)) {

                            this.setAudittraceValue(request);

                            try {
                                request.setAttribute("operationtype", "search");
                                successInsert = insertTerminal(terminalBean);
                                if (successInsert) {
                                    request.setAttribute("successMsg", MessageVarList.TERMINAL_MGT_SUCCESS_ADD + " Terminal Code " + terminalBean.getTerminalID());
                                }

                                rd = getServletContext().getRequestDispatcher(url);

                            } catch (SQLException e) {
                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("manufacturerList", manufacturerList);
                                request.setAttribute("difManufactermModels", difManufactermModels);
                                request.setAttribute("allocateList", allocateList);
                                request.setAttribute("terminalStatusList", terminalStatusList);

                                request.setAttribute("operationtype", "create");
                                request.setAttribute("terminalBean", terminalBean);
                                request.setAttribute("errorMsg", oraMessage);

                                rd = getServletContext().getRequestDispatcher(url1);
                            }

                        } else {
                            request.setAttribute("operationtype", "create");

                            request.setAttribute("manufacturerList", manufacturerList);
                            request.setAttribute("difManufactermModels", difManufactermModels);
                            request.setAttribute("allocateList", allocateList);
                            request.setAttribute("terminalStatusList", terminalStatusList);
                            request.setAttribute("terminalBean", terminalBean);
                            request.setAttribute("errorMsg", errorMessage);

                            rd = getServletContext().getRequestDispatcher(url1);
                        }
                        rd.forward(request, response);

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "create");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url1);
                        rd.forward(request, response);
                    }

                } else {
                    throw new AccessDeniedException();
                }

                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                //if user_role doesn't have required access to the page throw Access Denied exception
                throw adex;
            }

        } finally {
        }
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

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            terminalBean = new TerminalDataCaptureBean();

            terminalBean.setTerminalID(request.getParameter("terminalid").trim());
            terminalBean.setName(request.getParameter("name").trim());
            terminalBean.setSerialNo(request.getParameter("serialnumber").trim());
            terminalBean.setManufacturer(request.getParameter("manufacturer").trim());
            terminalBean.setModel(request.getParameter("model").trim());
            terminalBean.setInstallationDate(request.getParameter("installationDate").trim());
            terminalBean.setAllocationStatus(StatusVarList.ALLOCATION_NO);
            terminalBean.setTerminalStatus(StatusVarList.DEACTIVE_STATUS);

            terminalBean.setLastUpdateUser(sessionUser.getUserName());

            newValue = terminalBean.getTerminalID() + "|" + terminalBean.getName() + "|" + terminalBean.getSerialNo() + "|"
                    + terminalBean.getManufacturer() + "|" + terminalBean.getModel() + "|" + terminalBean.getInstallationDate() + "|"
                    + terminalBean.getAllocationStatus() + "|" + terminalBean.getTerminalStatus() + "|" + terminalBean.getLastUpdateUser();

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

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
            systemAuditBean.setDescription("Added Terminal" + " Terminal ID : " + terminalBean.getTerminalID() + "; by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.MERCHANT_TERMINAL_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TERMINALMGT);
            systemAuditBean.setPageCode(PageVarList.TRMINAL_MGT);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
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

    public boolean validateUserInput(TerminalDataCaptureBean terminalBean) throws Exception {
        boolean isValidate = true;
        try {
            if (terminalBean.getTerminalID().contentEquals("") || terminalBean.getTerminalID().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_TRMNLID_NULL;
            } else if (!UserInputValidator.isNumeric(terminalBean.getTerminalID())) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_TRMNLID_INVALID;

            } else if (terminalBean.getTerminalID().length() != 8) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_TRMNLID_INVALID;

            } else if (terminalBean.getTerminalID().equals(this.getCommonManualTerminal())) {
                isValidate = false;
                errorMessage = MessageVarList.DEFAULT_TERMINAL_CANNOT_INSERT;

            } else if (terminalBean.getName().contentEquals("") || terminalBean.getName().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_TRMNLNAME_NULL;
            } else if (!UserInputValidator.isDescription(terminalBean.getName()) || terminalBean.getName().contentEquals("'") || terminalBean.getName().contentEquals("'''")) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_TRMNLNAME_INVALID;
            } else if (terminalBean.getSerialNo().contentEquals("") || terminalBean.getSerialNo().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_SERIALNO_NULL;
            } else if (!UserInputValidator.isAlphaNumeric(terminalBean.getSerialNo())) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_SERIALNO_INVALID;
            } else if (terminalBean.getManufacturer().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_MANUFACT_NULL;
            } else if (terminalBean.getModel().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_MODEL_NULL;
            } else if (terminalBean.getInstallationDate().contentEquals("") || terminalBean.getInstallationDate().isEmpty()) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_INSTAL_DATE_NULL;
            } else if (terminalBean.getAllocationStatus().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_ALLO_STATUS_NULL;
            } else if (terminalBean.getTerminalStatus().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.TERMINAL_MGT_TRMINAL_STATUS_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    /**
     * to insert a new terminal
     *
     * @param terminalBean
     * @return
     * @throws Exception
     */
    public Boolean insertTerminal(TerminalDataCaptureBean terminalBean) throws Exception {
        boolean success = false;
        try {
            terminalManager = new TerminalDataCaptureManager();
            success = terminalManager.insertTerminal(terminalBean, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }

    /**
     * to get all the terminal allocation status
     */
    private void getAllocationStatus() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            allocateList = terminalManager.getAllocationStatus();

        } catch (Exception e) {
        }
    }

    /**
     * to get all the terminal status
     */
    private void getTerminalStatus() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            terminalStatusList = terminalManager.getTerminalStatus();
        } catch (Exception e) {
        }

    }

    /**
     * to retrieve all the terminal manufacturers
     */
    private void getAllManufacturers() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            manufacturerList = terminalManager.getAllManufacturers();
        } catch (Exception e) {
        }

    }

    private String getCommonManualTerminal() throws Exception {
        String manualTid = null;
        try {

            MerchantLocationManager manager = new MerchantLocationManager();
            manualTid = manager.getCommonManualTerminal();

        } catch (Exception ex) {
            throw ex;
        }
        return manualTid;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddTerminalDataCaptureServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddTerminalDataCaptureServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
