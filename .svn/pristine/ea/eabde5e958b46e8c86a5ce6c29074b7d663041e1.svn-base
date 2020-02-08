/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.PageMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mahesh_m
 */
public class AddPageMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager pageMgt;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private List<TaskBean> taskbeanList = null;
    private PageMgtManager pageManager = null;
    private List<PageBean> pageBeanList = null;
    private PageBean pageBean;
    private String[] assignedList = null;
    private String errorMessage = null;
    private boolean successInsert = false;
    private boolean successInsertToPageTask;
    private PageMgtManager pagemanager;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private String url = "/administrator/controlpanel/systemusermgt/pagemanagement.jsp";

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
                String pageCode = PageVarList.PAGE;
                String taskCode = TaskVarList.ADD;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {

                        assignedList = request.getParameterValues("assignsectionlist");

                        this.setUserInputToBean(request);

                        if (validateUserInput(pageBean, assignedList)) {
                            request.setAttribute("operationtype", "default");

                            this.setAudittraceValue(request);

                            try {

                                successInsert = inserPage(pageBean, systemAuditBean);
                                successInsertToPageTask = insertToPageTask(pageBean.getPageCode(), assignedList);

                                if (successInsert && successInsertToPageTask) {
                                    request.setAttribute("successMsg", pageBean.getPageCode() + " " + MessageVarList.PAGE_SUCCESS_ADD);
                                    rd = getServletContext().getRequestDispatcher("/LoadPageMgtServlet");
                                } else {
                                    request.setAttribute("operationtype", "default");
                                    request.setAttribute("errorMsg", errorMessage);
                                    rd = getServletContext().getRequestDispatcher("/LoadPageMgtServlet");
                                }

                            } catch (SQLException e) {
                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());

                                request.setAttribute("operationtype", "default");
                                request.setAttribute("errorMsg", oraMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);
                            }


                        } else {
                            request.setAttribute("operationtype", "default");
                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute("pageBean", pageBean);
                            rd = getServletContext().getRequestDispatcher("/LoadPageMgtServlet");

                        }
                        rd.forward(request, response);

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "default");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher("/LoadPageMgtServlet");
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
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
        } finally {
            out.close();
        }
    }

    public void setUserInputToBean(HttpServletRequest request) throws Exception {
        try {

            String pageCode = request.getParameter("pagecode").trim();
            String description = request.getParameter("pagedescription").trim();
            String url = request.getParameter("pageurl").trim();
            String status = request.getParameter("selectstatuscode");
            String sortkey = request.getParameter("pagesortkey").trim();



            pageBean = new PageBean();

            pageBean.setPageCode(pageCode);
            pageBean.setDescription(description);
            pageBean.setUrl(url);
            pageBean.setStatusCode(status);
            pageBean.setSortKey(sortkey);
            pageBean.setLastUpdateduser(sessionUser.getUserName());
            pageBean.setCreatedTime(new Date());

        } catch (Exception e) {
            throw e;
        }
    }

    public boolean validateUserInput(PageBean page, String[] assigned) throws Exception {
        boolean isValidate = true;

        try {

            if (page.getPageCode().contentEquals("") || page.getPageCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.PAGE_PAGECODE_NULL;
            } else if (!UserInputValidator.isCorrectString(page.getPageCode())) {
                isValidate = false;
                errorMessage = MessageVarList.PAGE_PAGECODE_INVALID;

            } else if (page.getDescription().contentEquals("") || page.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.PAGE_DESCRIPTION_NULL;

            } else if (page.getUrl().contentEquals("") || page.getUrl().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.PAGE_URL_NULL;

            } else if (page.getStatusCode().contentEquals("") || page.getStatusCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.PAGE_STATUSCODE_NULL;
            } else if (page.getSortKey().contentEquals("") || page.getSortKey().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.TASK_SORTKEY_NULL;
            } else if (!UserInputValidator.isNumeric(page.getSortKey())) {
                isValidate = false;
                errorMessage = MessageVarList.PAGE_SORTKEY_INVALID;
            } else if (assigned == null || assigned.length <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.PAGE_ASSIGNEDTASK_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public boolean inserPage(PageBean page, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            pagemanager = new PageMgtManager();
            success = pagemanager.inserPage(page, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }

    public boolean insertToPageTask(String pageCode, String[] assigned) throws Exception {
        boolean success = false;
        try {
            pagemanager = new PageMgtManager();
            success = pagemanager.inserToPageTask(pageCode, assigned);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {
        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(pageBean.getPageCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add page, page code : " + pageBean.getPageCode() + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.PAGE);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
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
