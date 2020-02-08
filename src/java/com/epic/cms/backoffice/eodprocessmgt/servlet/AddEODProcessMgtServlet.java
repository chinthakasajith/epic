/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodprocessmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.eodprocessmgt.bean.EODProcessMgtBean;
import com.epic.cms.backoffice.eodprocessmgt.businesslogic.EODProcessMgtManager;
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
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
 *
 * @author nisansala
 */
public class AddEODProcessMgtServlet extends HttpServlet {

    //initializing variables
    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private Boolean successInsert;
    private String errorMessage = "";
    private SessionUser sessionUser = null;
    private List<String> userTaskList = null;
    private SystemUserManager sysUserMgr = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    //-----------------------------------------------------------------------------
    private EODProcessMgtManager eodMgr;
    private EODProcessMgtBean process;
    HashMap<String, String> processCategory;
    private List<EODProcessMgtBean> processList;
    private String url = "/backoffice/eodprocessmgt/eodprocessmgthome.jsp";

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            /////////////////////////////////////////////////////////////////////

            try {
                sessionObj = request.getSession(false);
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
                String pageCode = PageVarList.EOD_PROCESS;
                String taskCode = TaskVarList.ADD;


                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    
                    try {
                        //assign user input to the bean
                        setUserInputToBean(request);
                        
                        
                        this.getProcessCategoryList();
                        request.setAttribute("processCategory", processCategory);

                        //validate user inputs
                        if (validateUserInput(process)) {//*******************************

                            request.setAttribute("operationtype", "add");
                            this.setAudittraceValue(request);
                            
                            try {
                                //insert the user given values to the MCC table
                                successInsert = insertNewEODProcess(process);
                                if (successInsert) {
                                    request.setAttribute("successMsg", MessageVarList.SUCCESS_ADD);
                                }

                                //if a merchant is successfully added redirect to the default page
                                rd = getServletContext().getRequestDispatcher(url);
                                
                            } catch (SQLException e) {
                                //show the messages which has thown when inserting
                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());

                                //go to the home page with the error message
                                request.setAttribute("operationtype", "add");
                                request.setAttribute("errorMsg", oraMessage);
                                
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);
                            }
                            
                        } else {
                            //if the user inputs are invalid go to the home page with an error message
                            request.setAttribute("operationtype", "add");
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute("EODbean", process);
                            rd = getServletContext().getRequestDispatcher(url);
                        }
                        this.getAllEODProcessData();
                        request.setAttribute("eodBeanList", processList);
                        rd.forward(request, response);
                        
                    } catch (Exception e) {
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
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
        } catch (SesssionExpException sex) {
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
            
            process = new EODProcessMgtBean();
            
            process.setDescription(request.getParameter("description"));
            process.setCriticalStatus(request.getParameter("criticalstatus"));
            process.setRollbackStatus(request.getParameter("rollbackstatus"));
            process.setScheduledDate(request.getParameter("scheduledate"));
            process.setScheduledTime(request.getParameter("scheduletime"));
            process.setFrequencyType(request.getParameter("frequencytype"));
            
            if (process.getFrequencyType().equals("1")) {
                process.setContinueosFreqType(request.getParameter("continuesfrequencytype"));
                process.setContinueosFrequency(request.getParameter("continuesfrequency"));
            } else {
                process.setContinueosFreqType(null);
                process.setContinueosFrequency(null);
            }
            process.setHolidayAction(request.getParameter("holidayAct"));
            process.setMultiCycleStatus(request.getParameter("multiplecyclestatus"));
            process.setProcessCatId(request.getParameter("processcategory"));
            process.setDependencyStatus(request.getParameter("dependencystatus"));
            process.setOnMain(request.getParameter("main"));
            process.setOnSub(request.getParameter("sub"));
            process.setProcessType(request.getParameter("processtype"));
            process.setStatus(request.getParameter("status"));
            process.setLastUpdateduser(sessionUser.getUserName());
            
            process.setLetterGenStatus(request.getParameter("letterGenStatus"));
            
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
            systemAuditBean.setUniqueId("");
            //set description 
            systemAuditBean.setDescription("");
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.EOD_PROCESS_MGT);
            systemAuditBean.setPageCode(PageVarList.EOD_PROCESS);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
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
    
    public Boolean insertNewEODProcess(EODProcessMgtBean merchant) throws Exception {
        boolean success = false;
        try {
            eodMgr = new EODProcessMgtManager();
            success = eodMgr.insertNewEODProcess(merchant, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }

    /**
     * validate the given input
     * @param merchant
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(EODProcessMgtBean process) throws Exception {
        boolean isValidate = true;
        
        try {
            if (process.getDescription().contentEquals("") || process.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.DESCRIPTION_NULL;
            } else if (!UserInputValidator.isDescription(process.getDescription())) {
                isValidate = false;
                errorMessage = MessageVarList.DESCRIPTION_INVALID;
            } else if (process.getCriticalStatus().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.CRITICAL_STATUS_NULL;
            } else if (process.getRollbackStatus().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.ROLLBACK_STATUS_NULL;
            } else if (process.getScheduledDate().isEmpty()) {
                isValidate = false;
                errorMessage = MessageVarList.DATE_NULL;
            } else if (process.getScheduledTime().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.TIME_NULL;
            } else if (process.getFrequencyType().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.FREQ_TYPE_NULL;
            } else if (process.getFrequencyType().equals("1")) {
                if (process.getContinueosFreqType().contentEquals("")) {
                    isValidate = false;
                    errorMessage = MessageVarList.CON_FREQ_TYPE_NULL;
                } else if (process.getContinueosFrequency().contentEquals("") || process.getContinueosFrequency().length() <= 0) {
                    isValidate = false;
                    errorMessage = MessageVarList.CONT_FREQ_NULL;
                } else if (!UserInputValidator.isNumeric(process.getContinueosFrequency())) {
                    isValidate = false;
                    errorMessage = MessageVarList.CON_FREQ_INVALID;
                } else if (process.getContinueosFreqType().equals("2")) {
                    if (Integer.parseInt(process.getContinueosFrequency()) > 999 || Integer.parseInt(process.getContinueosFrequency()) < 1) {
                        isValidate = false;
                        errorMessage = MessageVarList.CON_FREQ_INVALID;
                    }
                }
                if (process.getContinueosFreqType().equals("1") && isValidate) {
                    if (Integer.parseInt(process.getContinueosFrequency()) > 23 || Integer.parseInt(process.getContinueosFrequency()) < 0) {
                        isValidate = false;
                        errorMessage = MessageVarList.CON_FREQ_INVALID;
                    }
                }
                if (process.getContinueosFreqType().equals("0") && isValidate) {
                    if (Integer.parseInt(process.getContinueosFrequency()) > 59 || Integer.parseInt(process.getContinueosFrequency()) < 0) {
                        isValidate = false;
                        errorMessage = MessageVarList.CON_FREQ_INVALID;
                    }
                }
            }
            if (process.getHolidayAction() == null && isValidate) {
                isValidate = false;
                errorMessage = MessageVarList.BILL_CYCLE_MGT_HOLIACT_NULL;
            } else if (process.getMultiCycleStatus().contentEquals("") && isValidate) {
                isValidate = false;
                errorMessage = MessageVarList.MULTI_CYCLE_STATUS_NULL;
            } else if (process.getProcessCatId().contentEquals("") && isValidate) {
                isValidate = false;
                errorMessage = MessageVarList.PROCESS_CAT_NULL;
            } else if (process.getDependencyStatus().contentEquals("") && isValidate) {
                isValidate = false;
                errorMessage = MessageVarList.DEPENDENCY_STATUS_NULL;
            } else if (process.getOnMain().contentEquals("") && isValidate) {
                isValidate = false;
                errorMessage = MessageVarList.ON_MAIN_NULL;
            } else if (process.getOnSub().contentEquals("") && isValidate) {
                isValidate = false;
                errorMessage = MessageVarList.ON_SUB_NULL;
            } else if (process.getProcessType().contentEquals("") && isValidate) {
                isValidate = false;
                errorMessage = MessageVarList.PROCESS_TYPE_NULL;
            } else if (process.getOnSub().equals("0") && process.getOnMain().equals("0") && isValidate) {
                isValidate = false;
                errorMessage = MessageVarList.BOTH_NO;
            } else if (process.getStatus().contentEquals("") && isValidate) {
                isValidate = false;
                errorMessage = MessageVarList.STATUS_NULL;
            } else if (process.getLetterGenStatus().contentEquals("") && isValidate ){
                isValidate = false;
                errorMessage = MessageVarList.LETTER_GEN_STATUS;
            }
            
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }
    
    private void getAllEODProcessData() throws Exception {
        try {
            
            processList = new ArrayList<EODProcessMgtBean>();
            eodMgr = new EODProcessMgtManager();
            //retrieve merchant details
            processList = eodMgr.getAllEODProcessData();
        } catch (Exception e) {
            throw e;
        }
    }
    
    private void getProcessCategoryList() throws Exception {
        try {
            
            eodMgr = new EODProcessMgtManager();
            //retrieve merchant details
            processCategory = eodMgr.getProcessCategoryList();
        } catch (Exception e) {
            throw e;
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
        try {
            processRequest(request, response);
            
            
        } catch (Exception ex) {
            Logger.getLogger(AddEODProcessMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
            
            
        } catch (Exception ex) {
            Logger.getLogger(AddEODProcessMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
