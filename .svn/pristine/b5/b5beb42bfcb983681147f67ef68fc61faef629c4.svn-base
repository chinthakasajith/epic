/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ChannelIncomeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.OnlineTxnTypeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.ChannelTxnMgtManager;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
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
public class UpdateChannelTxnMgtServlet extends HttpServlet {

    //initializing variables
    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private Boolean successInsert;
    private String errorMessage = "";
    private SessionUser sessionUser = null;
    private List<String> userTaskList = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    //------------------------------------------------
    private String[] assignedList = null;
    private OnlineTxnTypeBean bean = null;
    private String[] notassignedList = null;
    private ChannelIncomeBean commonBean = null;
    private ChannelTxnMgtManager channelMgr = null;
    private List<ChannelIncomeBean> beanList = null;
    private List<OnlineTxnTypeBean> assignBeanList = null;
    private List<OnlineTxnTypeBean> notAssignBeanList = null;
    private String url = "/administrator/controlpanel/transactionMgt/channeltxnhome.jsp";
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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
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
                String pageCode = PageVarList.CHANNEL_TXN;
                String taskCode = TaskVarList.UPDATE;


                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    request.setAttribute("operationtype", "add");
                    try {
                        assignedList = request.getParameterValues("assignlist");
                        notassignedList = request.getParameterValues("notassignlist");
                        String channelId = request.getParameter("Channel");

                        commonBean = new ChannelIncomeBean();
                        commonBean.setChannelId(channelId);

                        //assign user input to the bean
                        setUserInputToBean(request, channelId, assignedList, notassignedList);
                        //validate user inputs
                        if (validateUserInput(channelId, assignedList)) {


                            this.setAudittraceValue(request);

                            try {
                                //insert the user given values
                                channelMgr = new ChannelTxnMgtManager();
                                successInsert = channelMgr.updateChannelTxn(channelId, assignedList, systemAuditBean);
                                if (successInsert) {
                                    request.setAttribute("successMsg", MessageVarList.SUCCESS_UPDATE);
                                    rd = getServletContext().getRequestDispatcher("/LoadChannelTxnMgtServlet");
                                } else {
                                    rd = getServletContext().getRequestDispatcher(url);
                                }

                            } catch (SQLException e) {
                                request.setAttribute("operationtype", "add");
                                //show the messages which has thown when inserting
                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());

                                //go to the home page with the error message

                                request.setAttribute("errorMsg", oraMessage);

                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);
                            }

                        } else {
                            //if the user inputs are invalid go to the home page with an error message
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute("operationtype", "add");
                            request.setAttribute("incomeBean", commonBean);
                            rd = getServletContext().getRequestDispatcher(url);
                        }

                        channelMgr = new ChannelTxnMgtManager();
                        beanList = channelMgr.getAllChannelTxn();
                        request.setAttribute("beanList", beanList);
                        rd.forward(request, response);

                    } catch (Exception e) {

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

    public Boolean setUserInputToBean(HttpServletRequest request, String channelId, String[] assignedList, String[] notassignedList) throws Exception {
        Boolean status = true;
        try {
            assignBeanList = new ArrayList<OnlineTxnTypeBean>();
            notAssignBeanList = new ArrayList<OnlineTxnTypeBean>();
            if (assignedList != null) {
                for (int i = 0; i < assignedList.length; i++) {
                    bean = new OnlineTxnTypeBean();
                    bean.setTxnCode(assignedList[i]);
                    assignBeanList.add(bean);
                }
            }

            for (int i = 0; i < notassignedList.length; i++) {
                bean = new OnlineTxnTypeBean();
                bean.setTxnCode(notassignedList[i]);
                notAssignBeanList.add(bean);
            }

            String var = "";
            channelMgr = new ChannelTxnMgtManager();
            for (OnlineTxnTypeBean bean : assignBeanList) {
                for (ChannelTypeBean bean1 : channelMgr.getAllTxnType()) {
                    if (bean.getTxnCode().equals(bean1.getTxnType())) {
                        var += bean1.getTxnTypeDes() + ",";
                    }
                }

            }
            newValue = channelId + "|" + var + "|" + sessionVarlist.getCMSSessionUser().getUserName();

            List<OnlineTxnTypeBean> assignList =  channelMgr.getAssignTxn(channelId);
            String oldVar = "";
            for (OnlineTxnTypeBean onlineTxnTypeBean : assignList) {
                oldVar += onlineTxnTypeBean.getDescription() + "," ;
            }

            oldValue = channelId + "|" + oldVar + "|" + sessionVarlist.getCMSSessionUser().getUserName();

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
            String uniqueId = commonBean.getChannelId();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Channel Txn. Channel Txn Code: '" + uniqueId + "' by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
            systemAuditBean.setPageCode(PageVarList.CHANNEL_TXN);
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

    public boolean validateUserInput(String channel, String[] assignList) throws Exception {
        boolean isValidate = true;

        try {

            if (channel.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.CHANNEL_TYPE_NULL;
            } else if (assignList == null) {
                isValidate = false;
                errorMessage = MessageVarList.TRANSACTION_TYPE_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
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
