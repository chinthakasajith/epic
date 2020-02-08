/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.FeeProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.FeeProfileManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
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
 * to update fee profile data
 * @author ayesh
 */
public class UpdateFeeProfileMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionUser sessionUser = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    //------------------------------------------------
    private int row = -1;
    private String errorMsg;
    private List<FeeBean> feeBeanList;
    private List<String> userTaskList;
    private FeeProfileBean feeProfBean;
    private FeeProfileManager feeProfMgr;
    private List<FeeBean> txnFeeList = null;
    private List<FeeBean> serviceFeeList = null;
    private List<FeeBean> txnFeeBeanList;
    private List<FeeBean> serviceFeeBeanList;
    private List<FeeProfileBean> feeProfBeanList;
    private String url = "/administrator/controlpanel/profilemgt/createfeeprofile.jsp";
    private String oldValue;
    private String newValue;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, AccessDeniedException, Exception {
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
                String pageCode = PageVarList.FEEPROFILE;
                String taskCode = TaskVarList.UPDATE;

                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    //the manufacturer user has selected



                    try {
                        //assign user input to the bean
                        setUserInputToBean(request);

                        //validate user inputs
                        if (isValidate(feeProfBean)) {

                            this.setAudittraceValue(request);

                            try {
                                request.setAttribute("operationtype", "update");
                                this.getAllFeeProfileDetail();
                                //update the fee profile
                                row = updateFeeProfile(feeProfBean, txnFeeList, serviceFeeList);

                                //load fee details according to the fee category CARD
                                this.getAllFeeDetails("CRD");
                                txnFeeBeanList = new ArrayList<FeeBean>();
                                serviceFeeBeanList = new ArrayList<FeeBean>();
                                //divide the loaded fee details into two lists according to the fee type
                                for (int i = 0; i < feeBeanList.size(); i++) {
                                    if (feeBeanList.get(i).getFeeType().equals("SER")) {
                                        serviceFeeBeanList.add(feeBeanList.get(i));
                                    } else if (feeBeanList.get(i).getFeeType().equals("TXN")) {
                                        txnFeeBeanList.add(feeBeanList.get(i));
                                    }
                                }
                                //set the two lists into the session
                                sessionVarlist.setTxnFeeList(txnFeeBeanList);
                                sessionVarlist.setServiceFeeList(serviceFeeBeanList);
                                if (row == 1) {
                                    //otherwise input fields will not be empty
                                    //sessionVarlist.setFeeBean(null);

                                    request.setAttribute("feeProfList", feeProfBeanList);
                                    request.setAttribute("successMsg", feeProfBean.getFeeProCode() + " " + MessageVarList.FEEPROFILE_UPDATE_SUCCESS);
                                    rd = getServletContext().getRequestDispatcher("/LoadFeeProfileMgtServlet");
                                }

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("errorMsg", oraMessage);

                                rd = getServletContext().getRequestDispatcher(url);
                            }

                        } else {

                            request.setAttribute("operationtype", "update");
                            request.setAttribute("errorMsg", errorMsg);
                            sessionVarlist.setFeeBean(feeProfBean);

                            rd = getServletContext().getRequestDispatcher(url);
                        }
                        rd.forward(request, response);

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "update");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }

                } else {
                    throw new AccessDeniedException();
                }

                //set tasks to the session
                //sessionVarlist.setUserPageTaskList(userTaskList);

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
        } finally {
        }
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("feeProCode");


            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Update Fee Profile. Fee Profile Code: '" + uniqueId + "'; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.PROFILEMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.FEEPROFILE);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());

            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue(oldValue);
            systemAuditBean.setNewValue(newValue);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());


        } catch (Exception ex) {
            throw ex;
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

            feeProfBean = new FeeProfileBean();


            feeProfBean.setFeeProCode(request.getParameter("feeProCode"));
            feeProfBean.setFeeProDes(request.getParameter("feeProDes"));
            feeProfBean.setFeeCategory(request.getParameter("category"));
            feeProfBean.setEffectiveDate(request.getParameter("effectiveday"));
            feeProfBean.setStaus(request.getParameter("feeProStatus"));
            feeProfBean.setLastUpdateUser(sessionUser.getUserName());

            txnFeeList = new ArrayList<FeeBean>();
            serviceFeeList = new ArrayList<FeeBean>();
            txnFeeList = sessionVarlist.getTxnFeeList();
            serviceFeeList = sessionVarlist.getServiceFeeList();


            newValue = feeProfBean.getFeeProCode() + "|" + feeProfBean.getFeeProDes() + "|" + feeProfBean.getFeeCategory() + "|"
                    + feeProfBean.getStaus() + "|" + feeProfBean.getLastUpdateUser();

            this.getAllFeeProfileDetail();

            for (FeeProfileBean bean : feeProfBeanList) {
                if (bean.getFeeProCode().equals(feeProfBean.getFeeProCode())) {
                    oldValue = bean.getFeeProCode() + "|" + bean.getFeeProDes() + "|" + bean.getFeeCategory() + "|"
                            + bean.getStaus() + "|" + bean.getLastUpdateUser();
                }
            }


        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    private int updateFeeProfile(FeeProfileBean feeProf, List<FeeBean> txnList, List<FeeBean> serviceList) throws Exception {
        int success = -1;
        try {
            feeProfMgr = new FeeProfileManager();
            success = feeProfMgr.updateFeeProfile(feeProf, txnList, serviceList, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;

    }

    private void getAllFeeDetails(String feeCategory) throws SQLException, Exception {
        feeProfMgr = new FeeProfileManager();
        feeBeanList = feeProfMgr.getAllFeeDetail(feeCategory);


    }

    private void getAllFeeProfileDetail() throws SQLException, Exception {
        feeProfMgr = new FeeProfileManager();
        feeProfBeanList = feeProfMgr.getAllFeeProfileDetail();
    }

    /**
     * check whether given request parameters are valid
     * @return boolean
     * 
     */
    private boolean isValidate(FeeProfileBean feeProfBean) throws Exception {

        boolean flag = true;
        errorMsg = "";


        if (feeProfBean.getFeeProCode().isEmpty()) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.FEEPROFILE_CODE_EMPTY;
            }
            flag = false;
        }//--------------------------------------------
        if (!UserInputValidator.isAlphaNumeric(feeProfBean.getFeeProCode())) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.FEEPROFILE_CODE_INVALID;
            }
            flag = false;
        }
        //----------------------------------------------------------
        if (feeProfBean.getFeeProDes().isEmpty()) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.FEEPROFILE_DESCRIPTION_EMPTY;
            }
            flag = false;
        }     //--------------------------------------------
        if (!UserInputValidator.isDescription(feeProfBean.getFeeProDes())) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.FEEPROFILE_DES_INVALID;
            }
            flag = false;
        }
        //----------------------------------------

        if (feeProfBean.getFeeCategory() == null) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.FEEPROFILE_CATEGORY_EMPTY;
                //errorMsg = "cat empty";
            }
            flag = false;
        }
        //----------------------------------------   

        if (feeProfBean.getEffectiveDate()== null) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.FEEPROFILE_EFFECTIVEDAY_EMPTY;
            }
            flag = false;
        }
        //---------------------------------------- 

        if (feeProfBean.getStaus().isEmpty()) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.FEEPROFILE_STATUS_EMPTY;
            }
            flag = false;
        }
        //----------------------------------------



        return flag;
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
        } catch (AccessDeniedException ex) {
            Logger.getLogger(UpdateFeeProfileMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UpdateFeeProfileMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (AccessDeniedException ex) {
            Logger.getLogger(UpdateFeeProfileMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UpdateFeeProfileMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
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
