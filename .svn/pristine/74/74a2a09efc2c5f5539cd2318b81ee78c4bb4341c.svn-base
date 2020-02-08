/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.recovery.callcenter.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.recovery.callcenter.bean.CollectionBean;
import com.epic.cms.recovery.callcenter.businesslogic.CollectionAssignmentManager;
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
 *
 * @author nalin
 */
public class CollectionAssignProcessServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private String errorMessage = null;
    private CollectionAssignmentManager collectionManager = null;
    private HashMap<String, String> userList = null;
    private List<CollectionBean> collectionList = null;
    private String[] assignList = null;
    private String queueId = null;
    private String assignUser = null;
    private String url = "/recovery/collectionassignment/collectionassignhome.jsp";

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
                String pageCode = PageVarList.COLLECTION_ASSIGNMENT;
                String taskCode = TaskVarList.UPDATE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                } else {
                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();
                }
                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);
            } catch (AccessDeniedException adex) {
                throw adex;
            }

            queueId = request.getParameter("queue");
            assignUser = request.getParameter("user");
            String checkType = request.getParameter("checkType");


            this.getSelectedCollectionList(queueId);
            this.getAssignUserList(queueId);

            assignList = request.getParameterValues("checkedVelue");
            if (validateUserInputs(queueId, assignUser)) {
                this.setAudittraceValue(request);
                if (checkType.equals("1")) {


                    if (assignList != null) {
                        this.assignCollection(assignList, assignUser, systemAuditBean);
                        request.setAttribute("successMsg", MessageVarList.COLLECTION_USER_ASSIGNED_SUCCESS);
                    } else {
                        request.setAttribute("queueId", queueId);
                        request.setAttribute("assignUser", assignUser);
                        request.setAttribute("collectionList", collectionList);
                        request.setAttribute("userList", userList);
                        request.setAttribute("errorMsg", MessageVarList.COLLECTION_LIST_NULL);
                    }
                } else if (checkType.equals("2")) {

                    this.setCollectionUserList(collectionList);
                    if (assignList != null) {
                        this.assignCollection(assignList, assignUser, systemAuditBean);
                        request.setAttribute("successMsg", MessageVarList.COLLECTION_USER_ASSIGNED_SUCCESS);
                    } else {
                        request.setAttribute("queueId", queueId);
                        request.setAttribute("assignUser", assignUser);
                        request.setAttribute("collectionList", collectionList);
                        request.setAttribute("userList", userList);
                        request.setAttribute("errorMsg", MessageVarList.ALL_COLLECTION_LIST_NULL);
                    }
                }
            } else {
                this.setCheckedValueToList();
                request.setAttribute("queueId", queueId);
                request.setAttribute("assignUser", assignUser);
                request.setAttribute("collectionList", collectionList);
                request.setAttribute("userList", userList);
                request.setAttribute("errorMsg", errorMessage);
            }

            rd = getServletContext().getRequestDispatcher(url);

        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } //catch session exception
        catch (NewLoginSessionException nlex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
        } catch (Exception ex) {
            request.setAttribute("queueId", queueId);
            request.setAttribute("assignUser", assignUser);
            request.setAttribute("collectionList", collectionList);
            request.setAttribute("userList", userList);
        } finally {
            rd.forward(request, response);
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

    private void getSelectedCollectionList(String queueId) throws Exception {
        try {
            collectionManager = new CollectionAssignmentManager();
            collectionList = new ArrayList<CollectionBean>();
            collectionList = collectionManager.getSelectedCollectionList(queueId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAssignUserList(String queueId) throws Exception {
        try {
            collectionManager = new CollectionAssignmentManager();
            userList = new HashMap<String, String>();
            userList = collectionManager.getAssignUserList(queueId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean assignCollection(String[] assignList, String assignUser, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            collectionManager = new CollectionAssignmentManager();
            success = collectionManager.assignCollection(assignList, assignUser, systemAuditBean);

        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private void setCollectionUserList(List<CollectionBean> collectionList) throws Exception {
        try {
            for (int i = 0; i < collectionList.size(); i++) {
                assignList[i] = collectionList.get(i).getCollectionId();
            }

        } catch (Exception ex) {
            throw ex;
        }

    }

    private void setCheckedValueToList() throws Exception {
        try {
            if (assignList != null) {
                for (int i = 0; i < assignList.length; i++) {
                    for (int j = 0; j < collectionList.size(); j++) {
                        if (assignList[i].equals(collectionList.get(j).getCollectionId())) {
                            collectionList.get(j).setChecked(true);
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {
        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");
            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update ASSIGNEDUSER Field by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.RECOVERY_MODULE);
            systemAuditBean.setSectionCode(SectionVarList.DISTRIBUTION);
            systemAuditBean.setPageCode(PageVarList.COLLECTION_ASSIGNMENT);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("ASSIGNEDUSER");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue("");
            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean validateUserInputs(String queueId, String assignUser) throws Exception {
        boolean isValidate = true;
        try {

            if (queueId.contentEquals("") || queueId.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.QUEUE_ID_NULL;
            } else if (assignUser.contentEquals("") || assignUser.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.USER_NULL;
            }

        } catch (Exception ex) {
            throw ex;
        }
        return isValidate;

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
