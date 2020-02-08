/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.collection.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.CaseTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.LadderBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.QueueBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.businesslogic.LadderManager;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.businesslogic.QueueManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ruwan_e
 */
public class UpdateQueueServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    HttpSession sessionObj = null;
    private List<String> userTaskList;
    private QueueManager queueManager;
    private LadderManager ladderManager;
    private QueueBean queueBean = null;
    private String errorMessage = null;
    private String[] assignedList;    
    private SystemAuditBean systemAuditBean = null;

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            //call to existing session
            /////////////////////////////////////////////////////////////////////
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

            /////////////////////////////////////////////////////////////////////

            try {
                //set page code and task codes
                //set page code and task codes
                String pageCode = PageVarList.QUEUE_MANAGEMENT;
                String taskCode = TaskVarList.UPDATE;

                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    sessionVarlist.setUserPageTaskList(userTaskList);

                    request.setAttribute("operationtype", "add");

                    final ArrayList<CaseTypeBean> cases = getCases();
                    request.setAttribute("caseList", cases);

                    final Map<String, String> status = getStatus();
                    request.setAttribute("statusList", status);

                    final Map<String, String> userRoles = getUserRoles();
                    request.setAttribute("userRoleList", userRoles);

                    final ArrayList<LadderBean> ladders = getLadders();
                    request.setAttribute("ladders", ladders);

                    final ArrayList<QueueBean> queues = getQueues();
                    request.setAttribute("queues", queues);

                    //assign user input to the bean
                    setUserInputToBean(request);

                    if (validateUserInput(queueBean)) {
                        //this.setAudittraceValue(request);
                        if (updateQueue(queueBean)) {
                            out.print("success," + MessageVarList.QUEUE_MGT_SUCCESS_UPDATE + " Queue ID " + queueBean.getCode());
                        }
                    } else {
                        out.print(errorMessage);
                    }

                    sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                } else {
                    throw new AccessDeniedException();

                }

            } catch (AccessDeniedException adex) {
                throw adex;

            }



        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/pagemanagement.jsp?errorMsg=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/pagemanagement.jsp?errorMsg=" + MessageVarList.LAST_SESSION_CLOSE);
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

    private ArrayList<CaseTypeBean> getCases() throws Exception {
        try {

            queueManager = new QueueManager();
            ArrayList<CaseTypeBean> cases;
            cases = queueManager.getAllCases();
            return cases;

        } catch (Exception ex) {
            throw ex;
        }
    }

    private Map<String, String> getStatus() throws Exception {
        try {

            queueManager = new QueueManager();
            Map<String, String> cases;
            cases = queueManager.getStatus();
            return cases;

        } catch (Exception ex) {
            throw ex;
        }
    }

    private ArrayList<LadderBean> getLadders() throws Exception {
        try {
            ladderManager = new LadderManager();
            ArrayList<LadderBean> ladders;
            ladders = ladderManager.getAllLadders();
            return ladders;

        } catch (Exception ex) {
            throw ex;
        }
    }

    private Map<String, String> getUserRoles() throws Exception {
        try {
            return new QueueManager().getAllUserRoles();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private ArrayList<QueueBean> getQueues() throws Exception {
        try {
            return new QueueManager().getAllQueues();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            queueBean = new QueueBean();

            queueBean.setCode(request.getParameter("queueid").trim());
            queueBean.setDescription(request.getParameter("queuedescription").trim());
            queueBean.setStatus(request.getParameter("selectstatuscode").trim());
            queueBean.setLadderCode(request.getParameter("ladder").trim());
            queueBean.setAssignUserRole(request.getParameter("userrole"));
            queueBean.setLastUpdatedUser(sessionUser.getUserName());

            assignedList = request.getParameterValues("assignsectionlist");

            if (assignedList != null) {
                Map<String, String> caseMap = getCaseDescriptionMap(assignedList);
                queueBean.setCases(caseMap);
            } else {
                queueBean.setCases(new HashMap<String, String>());
            }

            String newValue = queueBean.toString();

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    private boolean validateUserInput(QueueBean queueBean) throws Exception {
        boolean isValid = true;

        try {
            if (queueBean.getCode() == null || queueBean.getCode().contentEquals("")) {
                isValid = false;
                errorMessage = MessageVarList.QUEUE_CODE_EMPTY;

            } else if (queueBean.getDescription() == null || queueBean.getDescription().contentEquals("")) {
                isValid = false;
                errorMessage = MessageVarList.QUEUE_DESCRIPTION_EMPTY;

            } else if (queueBean.getStatus().contentEquals("")) {
                isValid = false;

                errorMessage = MessageVarList.QUEUE_STATUS_EMPTY;

//            } else if (ladderBean.getCardType().contentEquals("") || ladderBean.getCardType().length() <= 0) {
//                isValid = false;
//                errorMessage = MessageVarList.LADDER_CARD_TYPE_EMPTY;

            } else if (queueBean.getCases().size() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.LADDER_NO_CAESE_ASSIGNED;
            }
        } catch (Exception ex) {
            isValid = false;
            throw ex;
        }
        return isValid;
    }

    public boolean updateQueue(QueueBean queue) throws Exception {
        boolean success = false;
        try {
            queueManager = new QueueManager();
            success = queueManager.updateQueue(queue, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }
    
    private Map<String, String> getCaseDescriptionMap(String[] cases) throws SQLException, Exception {
        try {
            return new LadderManager().getCaseDescription(cases);
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(queueBean.getCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Queue. Queue code: " + queueBean.getCode() + " by: " + sessionVarlist.getCMSSessionUser().getUserName());
//            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
//            systemAuditBean.setSectionCode(SectionVarList.PROFILEMANAGEMENT);
//            systemAuditBean.setPageCode(PageVarList.INTERESTPROFILE);
//            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
//            systemAuditBean.setOldValue(oldValue);
            //set new value of change if required
//            systemAuditBean.setNewValue(newValue);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
