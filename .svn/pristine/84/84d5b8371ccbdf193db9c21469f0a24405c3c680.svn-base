/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.responcecodemgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.switchcontrol.responcecodemgt.bean.ResponceCodeBean;
import com.epic.cms.switchcontrol.responcecodemgt.businesslogic.ResponceCodeMgtManager;
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
public class AddResponceCodeMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemAuditBean systemAuditBean = null;
    private String errorMessage = null;
    private boolean successInsert = false;
    private SystemUserManager systemUserManager = null;
    private ResponceCodeMgtManager responceManager = null;
    private List<ResponceCodeBean> responceList = null;
    private ResponceCodeBean responceBean = null;
    private List<String> userTaskList;
    private String url = "/switch/responcecodemgt/responcecodemgt/responcecodemgthome.jsp";
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
                        //user not in same session.
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
                String pageCode = PageVarList.RESPONCE_CODE_MGT;
                String taskCode = TaskVarList.ADD;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {

                        setUserInputToBean(request);
                        if (validateUserInput(responceBean)) {
                            request.setAttribute("operationtype", "ADD");

                            this.setAudittraceValue(request);

                            try {
                                successInsert = this.insertResponceCode(responceBean, systemAuditBean);

                                if (successInsert) {

                                    request.setAttribute("successMsg", MessageVarList.RESPONCE_CODE_SUCCESS_ADD + " " + responceBean.getResponceCode());

                                }

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());

                                request.setAttribute("operationtype", "ADD");
                                request.setAttribute("errorMsg", oraMessage);
                                request.setAttribute("responceBean", responceBean);
                                rd = getServletContext().getRequestDispatcher(url);


                            }

                        } else {
                            request.setAttribute("operationtype", "ADD");
                            request.setAttribute("responceBean", responceBean);
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);

                        }

                        ///////////////get Responce Code Details fot table view////////////////////////////////

                        responceManager = new ResponceCodeMgtManager();
                        responceList = new ArrayList<ResponceCodeBean>();
                        responceList = responceManager.getResponceCodes();

                        request.setAttribute(RequestVarList.RESPONCE_LIST, responceList);
                        rd = request.getRequestDispatcher(url);

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "ADD");
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
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * set uesr input from jsp to bean
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = true;
        try {

            responceBean = new ResponceCodeBean();

            responceBean.setResponceCode(request.getParameter("responceCode").trim());
            responceBean.setDescription(request.getParameter("description").trim());
            responceBean.setLastUpdatedUser(sessionUser.getUserName());
            
            newValue = responceBean.getResponceCode() + "|" + responceBean.getDescription() + "|" + responceBean.getLastUpdatedUser();

        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    /**
     * Validate the New User Entry
     * @param txnTypeBean
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(ResponceCodeBean responceBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code

        try {

            if (responceBean.getResponceCode().contentEquals("") || responceBean.getResponceCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.RESPONCE_CODE_NULL;

            } else if (!UserInputValidator.isNumeric(responceBean.getResponceCode())) {
                isValidate = false;

                errorMessage = MessageVarList.RESPONCE_CODE_INVALID;

            } else if (responceBean.getDescription().contentEquals("") || responceBean.getDescription().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.DESCRIPTION_NULL;

            } else if (!UserInputValidator.isDescription(responceBean.getDescription())) {
                isValidate = false;

                errorMessage = MessageVarList.DESCRIPTION_INVALID;



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
    public boolean insertResponceCode(ResponceCodeBean responceBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            /// call insertTxntypeDetails method in TxnTypeMgtManager class

            responceManager = new ResponceCodeMgtManager();
            success = responceManager.insertResponceCode(responceBean, systemAuditBean);
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
            String uniqueId = responceBean.getResponceCode();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Responce Code. Responce Code :" + responceBean.getResponceCode() + "; by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.SWITCH_CONTROL_PANEL);
            systemAuditBean.setSectionCode(SectionVarList.RESPONCE_CODE_MGT);
            systemAuditBean.setPageCode(PageVarList.RESPONCE_CODE_MGT);
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
