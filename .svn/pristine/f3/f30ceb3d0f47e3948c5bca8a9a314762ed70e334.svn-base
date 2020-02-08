/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.MerchantCategoryManager;
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
 * this servlet calls the merchant logic manager to insert new merchant details
 * @author nisansala
 */
public class AddMerchantCategoryServlet extends HttpServlet {
    //initialising variables

    private RequestDispatcher rd;
    private List<String> userTaskList;
    private SessionUser sessionUser = null;
    private SessionVarList sessionVarlist = null;  
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;    
    //---------------------------------------------------
    private Boolean  successInsert;
    private String errorMessage = null;    
    private MerchantCategoryBean mrchntBean;
    private MerchantCategoryManager merchMangr;
    private List<MerchantCategoryBean> mrchntCatList = null;    
    private String url = "/administrator/controlpanel/transactionMgt/merchantcategoryhome.jsp";
    private String newValue;
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
                HttpSession sessionObj = request.getSession(false);
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
                String pageCode = PageVarList.MERCHANTCC;
                String taskCode = TaskVarList.ADD;


                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        //assign user input to the bean
                        setUserInputToBean(request);

                        //validate user inputs
                        if (validateUserInput(mrchntBean)) {
                            
                            request.setAttribute("operationtype", "add");                            
                            this.setAudittraceValue(request);

                            try {
                                //insert the user given values to the MCC table
                                successInsert = insertMerchant(mrchntBean);
                                
                                    request.setAttribute("successMsg",MessageVarList.MRCHNTCATMGT_SUCCESS_ADD + " Merchant Code " + mrchntBean.getmCCCode() );
                                    this.getDataFromMCCTable();
                                    request.setAttribute("mrchntCatList", mrchntCatList);
                                    //if a merchant is successfully added redirect to the default page
                                    rd = getServletContext().getRequestDispatcher(url);
                                                                   
                            } catch (SQLException e) {
                                //show the messages which has thown when inserting
                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());

                                //go to the home page with the error message
                                request.setAttribute("operationtype", "add");
                                request.setAttribute("errorMsg", oraMessage);
                                this.getDataFromMCCTable();
                                    request.setAttribute("mrchntCatList", mrchntCatList);
                                rd = getServletContext().getRequestDispatcher(url); 
                            }

                        } else {
                            //if the user inputs are invalid go to the home page with an error message
                            request.setAttribute("operationtype", "add");
                            request.setAttribute("mrchntBean", mrchntBean);
                            request.setAttribute("errorMsg", errorMessage);
                            this.getDataFromMCCTable();
                                    request.setAttribute("mrchntCatList", mrchntCatList);
                            rd = getServletContext().getRequestDispatcher(url);

                        }

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
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
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.ACCESS_DENIED_TASK);
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
     /**
     * validate the given input
     * @param merchant
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(MerchantCategoryBean merchant) throws Exception {
        boolean isValidate = true;

        
        try {
            if (merchant.getmCCCode().contentEquals("") || merchant.getmCCCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.MRCHNTCATMGT_MCCCODE_NULL;
            } else if (!UserInputValidator.isAlphaNumeric(merchant.getmCCCode())) {
                isValidate = false;
                errorMessage = MessageVarList.MRCHNTCATMGT_MCCCODE_INVALID;
            } else if (merchant.getDescription().contentEquals("") || merchant.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.MRCHNTCATMGT_DESCRIPTION_NULL;
            }else if (!UserInputValidator.isDescription(merchant.getDescription())) {
                isValidate = false;
                errorMessage = MessageVarList.MRCHNTCATMGT_DESCRIPTION_INVALID;
            }else if (merchant.getmClass().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.MRCHNTCATMGT_MCLASS_NULL;
            }
            else if (merchant.getStatus().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.MRCHNTCATMGT_STATUS_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public Boolean insertMerchant(MerchantCategoryBean merchant) throws Exception {
        boolean success = false;
        try {
            merchMangr = new MerchantCategoryManager();
            success = merchMangr.insertNewMerchntCatgry(merchant, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
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
            String uniqueId = request.getParameter(mrchntBean.getmCCCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Merchant Category. Merchant Category Code: '" + mrchntBean.getmCCCode() + "'  by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
            systemAuditBean.setPageCode(PageVarList.MERCHANTCC);
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
    /**
     * when adding a new merchant set the user input to the bean
     * @param request
     * @return
     * @throws Exception 
     */
    
    
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            mrchntBean = new MerchantCategoryBean();

            String mrchntCatCode = request.getParameter("MCCCode").trim();
            String description = request.getParameter("mName").trim();
            String mClass = request.getParameter("mClass");
            String mStatus = request.getParameter("status").trim();

            mrchntBean.setmCCCode(mrchntCatCode);
            mrchntBean.setDescription(description);
            mrchntBean.setmClass(mClass);
            mrchntBean.setStatus(mStatus);
            mrchntBean.setLastUpdateUser(sessionUser.getUserName());
            
            newValue = mrchntBean.getmCCCode() + "|" + mrchntBean.getDescription() + "|" + mrchntBean.getmClass() + "|" + mrchntBean.getStatus() + "|" + mrchntBean.getLastUpdateUser();

        } catch (Exception e) {
            status = false;
            throw e;

        }

        return status;
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
    /**
     * retrieve data from MCC table to show in the default table
     * @throws Exception 
     */
    private void getDataFromMCCTable() throws Exception {
        try {
            
            mrchntCatList = new ArrayList<MerchantCategoryBean>();            
            merchMangr = new MerchantCategoryManager();
            //retrieve merchant details
            mrchntCatList = merchMangr.getAllMerchntCatgryDetails();
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
            Logger.getLogger(AddMerchantCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AddMerchantCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
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
