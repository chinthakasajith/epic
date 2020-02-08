/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ListenerTxnBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.OnlineTxnTypeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.ListenerTxnMgtManager;
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
public class AddListenerTxnMgtServlet extends HttpServlet {

    //initializing variables
    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private boolean successInsert;
    private SessionUser sessionUser = null;
    private List<String> userTaskList = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    private String errorMessage = "";
    //------------------------------------------------
    private String[] assignedList = null;
    private OnlineTxnTypeBean bean = null;
    private String[] notassignedList = null;
    private ListenerTxnBean commonBean = null;
    private List<ListenerTxnBean> beanList = null;
    private ListenerTxnMgtManager listenMgr = null;
    private List<OnlineTxnTypeBean> assignBeanList = null;
    private List<OnlineTxnTypeBean> notAssignBeanList = null;
    private List<ListenerTxnBean> unusedListenerList = null;
    private String url = "/administrator/controlpanel/transactionMgt/listenertxnhome.jsp";
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
                String pageCode = PageVarList.LISTEN_TXN;
                String taskCode = TaskVarList.ADD;


                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    request.setAttribute("operationtype", "add");
                    try {
                        listenMgr = new ListenerTxnMgtManager();
                        assignedList = request.getParameterValues("assignlist");
                        notassignedList = request.getParameterValues("notassignlist");
                        String listenerId = request.getParameter("listener");
                        commonBean = new ListenerTxnBean();
                        commonBean.setLitenerId(listenerId);
                        //assign user input to the bean
                        setUserInputToBean(request, listenerId,assignedList, notassignedList);
                        //validate user inputs
                        if (validateUserInput(listenerId, assignedList)) {


                            this.setAudittraceValue(request);

                            try {
                                //insert the user given values

                                successInsert = listenMgr.insertListenerTxn(listenerId, assignedList, systemAuditBean);
                                if (successInsert) {
                                    request.setAttribute("successMsg", MessageVarList.SUCCESS_ADD);
                                    //load unused listeners
                                    unusedListenerList = listenMgr.getUnusedListeners();
                                    sessionVarlist.setUnusedListenerList(unusedListenerList);

                                } else {
                                    request.setAttribute("errorMsg", "Errorroorororoororor");

                                }

                            } catch (SQLException e) {
                                //show the messages which has thown when inserting
                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("errorMsg", oraMessage);


                            }

                        } else {
                            //if the user inputs are invalid go to the home page with an error message
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute("incomeBean", commonBean);
                        }

                        //load all istener txn
                        beanList = listenMgr.getAllListenerTxn();
                        request.setAttribute("beanList", beanList);

                        rd = getServletContext().getRequestDispatcher(url);


                    } catch (Exception e) {

                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);

                    }
                } else {
                    throw new AccessDeniedException();
                }

            } catch (AccessDeniedException adex) {
                //if user_role doesn't have required access to the page throw Access Denied exception
                throw adex;
            }
        } catch (SesssionExpException sex) {
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
            request.setAttribute("operationtype", "add");
            rd = getServletContext().getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
            out.close();

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

    public Boolean setUserInputToBean(HttpServletRequest request,String listenerId, String[] assignedList, String[] notassignedList) throws Exception {
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
            if (notassignedList != null) {
                for (int i = 0; i < notassignedList.length; i++) {
                    bean = new OnlineTxnTypeBean();
                    bean.setTxnCode(notassignedList[i]);
                    notAssignBeanList.add(bean);
                }
            }
            
            String var = "" ;
            listenMgr = new ListenerTxnMgtManager();
            for(OnlineTxnTypeBean bean : assignBeanList){
               for(ChannelTypeBean bean1 : listenMgr.getAllTxnType()){
                   if(bean.getTxnCode().equals(bean1.getTxnType())){
                   var += bean1.getTxnTypeDes() + ",";
                   }
               }
                
            }
            newValue = listenerId + "|" + var + "|" + sessionVarlist.getCMSSessionUser().getUserName();             

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
            String uniqueId = commonBean.getLitenerId();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Listener Txn. Listener Txn Code: '" + uniqueId + "'  by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
            systemAuditBean.setPageCode(PageVarList.LISTEN_TXN);
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

    public boolean validateUserInput(String listenerId, String[] assignList) throws Exception {
        boolean isValidate = true;

        try {

            if (listenerId.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.LISTENER_TYPE_NULL;
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
