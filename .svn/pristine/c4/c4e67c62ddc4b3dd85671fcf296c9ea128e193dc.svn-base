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
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *  update the MCC table after confirmation
 * @author nisansala
 */
public class UpdateConfirmedMerchantCategoryServlet extends HttpServlet {

    HttpSession sessionObj;
    private RequestDispatcher rd;
    private String oldValue = "";
    private String newValue = "";   
    private List<String> userTaskList;
    private SessionUser sessionUser = null;
    private SessionVarList sessionVarlist = null;  
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    //-------------------------------------------------
    private String errorMessage = null;
    private boolean successUpdate = false;
    private MerchantCategoryBean mrchntBean;
    private MerchantCategoryManager merchCatgryMngr;
    private List<MerchantCategoryBean> mrchntCatList = null; 
    private String url = "/administrator/controlpanel/transactionMgt/merchantcategoryhome.jsp";

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
                String pageCode = PageVarList.MERCHANTCC;
                String taskCode = TaskVarList.UPDATE;


                //check whether user_role have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                    try {
                        if (setUserInputToBean(request)) {

                            //validate user input
                            if (validateUserInput(mrchntBean)) {
                                request.setAttribute("operationtype", "add");
                                // request.setAttribute("mrchntCatList", mrchntCatList);
                                //set values to the audit trace
                                this.setAudittraceValue(request);
                                successUpdate = updateMerchant(mrchntBean, systemAuditBean);
                                request.setAttribute("successMsg", " " + MessageVarList.MRCHNTCATMGT_MERCHANT_SUCCESS_UPDATE + "Merchant Code " + mrchntBean.getmCCCode());
                                rd = getServletContext().getRequestDispatcher(url);
                            } else {
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("mrchntBean", mrchntBean);
                                //request.setAttribute("mrchntCatList", mrchntCatList);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }
                        } else {
                            request.setAttribute("operationtype", "add");
                            //request.setAttribute("mrchntCatList", mrchntCatList);
                            request.setAttribute("mrchntBean", mrchntBean);
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }
                        this.getDataFromMCCTable();
                        request.setAttribute("mrchntCatList", mrchntCatList);
                        rd.forward(request, response);
                    } catch (Exception e) {
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("mrchntBean", mrchntBean);
                        request.setAttribute("mrchntCatList", mrchntCatList);
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        this.getDataFromMCCTable();
                        request.setAttribute("mrchntCatList", mrchntCatList);
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
     * set the user input to the bean
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            mrchntBean = new MerchantCategoryBean();

            String mCCCode = request.getParameter("MCCCode").trim();
            String description = request.getParameter("mName").trim();
            String mClass = request.getParameter("mClass").trim();
            String mStatus = request.getParameter("status").trim();

            mrchntBean.setmCCCode(mCCCode);
            mrchntBean.setDescription(description);
            mrchntBean.setmClass(mClass);
            mrchntBean.setStatus(mStatus);
            mrchntBean.setLastUpdateUser(sessionUser.getUserName());

            oldValue = request.getParameter("oldValue");
            newValue = mrchntBean.getmCCCode() + "|" + mrchntBean.getDescription() + "|" + mrchntBean.getmClass() + "|" + mrchntBean.getStatus() + "|";


        } catch (Exception e) {
            status = false;
            throw e;

        }

        return status;
    }

    /**
     * validate the given user input
     * @param mrchntBean
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(MerchantCategoryBean mrchntBean) throws Exception {
        boolean isValidate = true;

        //validate user Role code
        try {
            if (mrchntBean.getmCCCode().contentEquals("") || mrchntBean.getmCCCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MRCHNTCATMGT_MCCCODE_NULL;
            } else if (!UserInputValidator.isAlphaNumeric(mrchntBean.getmCCCode())) {
                isValidate = false;
                errorMessage = MessageVarList.MRCHNTCATMGT_MCCCODE_INVALID;
            } else if (mrchntBean.getDescription().contentEquals("") || mrchntBean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.MRCHNTCATMGT_DESCRIPTION_NULL;
            } else if (!UserInputValidator.isDescription(mrchntBean.getDescription())) {
                isValidate = false;
                errorMessage = MessageVarList.MRCHNTCATMGT_DESCRIPTION_INVALID;
            } else if (mrchntBean.getmClass().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.MRCHNTCATMGT_MCLASS_NULL;

            } else if (mrchntBean.getStatus().contentEquals("")) {

                isValidate = false;
                errorMessage = MessageVarList.MRCHNTCATMGT_STATUS_NULL;
            }


        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    /**
     * pass relevant data to audit trace table
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
            systemAuditBean.setDescription("Update Merchant Category. :Merchant Category Code '" + mrchntBean.getmCCCode() + "'  by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
            systemAuditBean.setPageCode(PageVarList.MERCHANTCC);
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
     * update the merchant
     * @param mrchntBean
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean updateMerchant(MerchantCategoryBean mrchntBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            merchCatgryMngr = new MerchantCategoryManager();
            success = merchCatgryMngr.updateMerchntCatgryDetails(mrchntBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
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

    /**
     * retrieve data from MCC table to view in the default table
     * @throws Exception 
     */
    private void getDataFromMCCTable() throws Exception {
        try {

            mrchntCatList = new ArrayList<MerchantCategoryBean>();
            merchCatgryMngr = new MerchantCategoryManager();
            //retrieve merchant details
            mrchntCatList = merchCatgryMngr.getAllMerchntCatgryDetails();
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
