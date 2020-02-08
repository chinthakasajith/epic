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
import com.epic.cms.application.common.bean.StatusBean;
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
import java.util.ArrayList;
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
public class UpdateConfiremedPageMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private List<TaskBean> taskbeanList = null;
    private PageMgtManager pageManager = null;
    private PageBean pageBean;
    private String errorMessage = null;
    private SystemAuditBean systemAuditBean = null;
    private boolean successInsert = false;
    private boolean successInsertToPageTask;
    List<String> existTaskList = null;
    private List<TaskBean> assignedTaskDescriptions;
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
                String taskCode = TaskVarList.UPDATE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                   
            try {

//                assignedList = request.getParameterValues("assignsectionlist");

                String[] notToAssign = request.getParameterValues("notassignsectionlist");
                String[] toAssign = request.getParameterValues("assignsectionlist");


                this.setUserInputToBean(request);


                taskbeanList = new ArrayList<TaskBean>();
                pageManager = new PageMgtManager();
                taskbeanList = pageManager.getTaskDescriptionNotAssignedList(pageBean.getPageCode());

                getAssignedTasks(pageBean.getPageCode());


                if (validateUserInput(pageBean, toAssign)) {
                    request.setAttribute("operationtype", "default");
                    this.setAudittraceValue(request);
                    successInsert = updatePage(pageBean, systemAuditBean);

                    this.assignPageTask(pageBean.getPageCode(), toAssign, notToAssign, sessionUser.getUserName(), systemAuditBean);

                    if (existTaskList.size() > 0) {
                        try {
                            pageManager.deletePageTask(existTaskList, pageBean.getPageCode());
                            successInsertToPageTask = true;
                        } catch (SQLException e) {
                            String resultMessage = null;
                            OracleMessage message = new OracleMessage();
                            String oraMessage = message.getMessege(e.getMessage());

                            if (oraMessage.equals(message.INTE_CHILD)) {
                                resultMessage = "You cannot remove previously used Tasks.";
                            }

                            request.setAttribute("operationtype", "default");
                            request.setAttribute("page", pageBean);
                            request.setAttribute("taskBean", taskbeanList);
                            request.setAttribute("assigned", assignedTaskDescriptions);
                            request.setAttribute("operationtype", "update");
                            request.setAttribute("errorMsg", resultMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }

                    }

                    if (successInsert) {
                        request.setAttribute("operationtype", "default");
                        request.setAttribute("successMsg", pageBean.getPageCode() + " " + MessageVarList.PAGE_SUCCESS_UPDATE);
                        rd = getServletContext().getRequestDispatcher("/LoadPageMgtServlet");
                        rd.forward(request, response);
                    } else {
                        request.setAttribute("page", pageBean);
                        request.setAttribute("operationtype", "update");
                        request.setAttribute("errorMsg", errorMessage);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }

                } else {
                    request.setAttribute("page", pageBean);
                    request.setAttribute("taskBean", taskbeanList);
                    request.setAttribute("assigned", assignedTaskDescriptions);
                    request.setAttribute("operationtype", "update");
                    request.setAttribute("errorMsg", errorMessage);
                    rd = getServletContext().getRequestDispatcher(url);
                }
            } catch (Exception e) {
                request.setAttribute("page", pageBean);
                request.setAttribute("operationtype", "update");
                request.setAttribute("errorMsg", errorMessage);
                rd = getServletContext().getRequestDispatcher(url);
            }

            rd.forward(request, response);
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
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/pagemanagement.jsp?errorMsg=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/pagemanagement.jsp?errorMsg=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        }catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (Exception ex) {
        } finally {
            out.close();
        }
    }

    public void setUserInputToBean(HttpServletRequest request) {

        String pageCode = request.getParameter("pagecode").trim();
        String description = request.getParameter("taskdescription").trim();
        String url = request.getParameter("url").trim();
        String sortkey = request.getParameter("pagesortkey").trim();
        String statusCode = request.getParameter("selectstatuscode");

        pageBean = new PageBean();

        pageBean.setPageCode(pageCode);
        pageBean.setDescription(description);
        pageBean.setUrl(url);
        pageBean.setSortKey(sortkey);
        pageBean.setStatusCode(statusCode);
        pageBean.setLastUpdateduser(sessionUser.getUserName());
        pageBean.setCreatedTime(new Date());

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
            systemAuditBean.setDescription("Update page, page code : " + pageBean.getPageCode() + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.PAGE);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
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

    public boolean updatePage(PageBean page, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            pageManager = new PageMgtManager();
            success = pageManager.updatePage(page, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    public boolean insertToPageTask(String pageCode, String[] assigned) throws Exception {
        boolean success = false;
        try {
            pageManager = new PageMgtManager();
            success = pageManager.inserToPageTaskUpdate(pageCode, assigned);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private void assignPageTask(String pageCode, String[] assinArray, String[] UnAssinArray, String sysUser, SystemAuditBean systemAuditBean) throws Exception {

        try {

            pageManager = new PageMgtManager();
            existTaskList = pageManager.assignPageTask(pageCode, assinArray, UnAssinArray, sysUser, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
    }

    void getAssignedTasks(String pageCode) throws Exception {
        assignedTaskDescriptions = new ArrayList<TaskBean>();
        pageManager = new PageMgtManager();
        assignedTaskDescriptions = pageManager.getAssignedtasks(pageCode);
    }

          private boolean isValidAccess(String userrole,String pagecode,String task) throws Exception{
       boolean isValidTaskAccess=false;
        
        try{
          systemUserManager=new SystemUserManager();
         
          //get all tasks for userrole for this page
          userTaskList=  systemUserManager.getTasksByPageCodeAndUserRole(userrole, pagecode);
            
          for(String usertask:userTaskList){
              
              if(task.equals(usertask)){
                 isValidTaskAccess=true; 
              }
              
              
          }
          
           
          return isValidTaskAccess;
        }
        catch(Exception ex){
            throw  ex;
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
