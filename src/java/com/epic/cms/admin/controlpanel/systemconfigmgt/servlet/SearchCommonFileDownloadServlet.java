/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.FileDownloadDetailsBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.FileDownloadSearchBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.FileDownloadMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
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
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chanuka
 */
public class SearchCommonFileDownloadServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SystemAuditBean systemAuditBean = null;
    private FileDownloadMgtManager fileDownloadMgtManager = null;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private FileDownloadSearchBean searchBean = null;
    private List<FileDownloadDetailsBean> searchList = null;
    private String errorMessage;
    private String url = "/administrator/controlpanel/systemconfigmgt/commonfiledownloadhome.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

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




            try {
                //set page code and task codes
                String pageCode = PageVarList.COMMONFILEDOWNLOAD;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (!this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }

                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                throw adex;

            }


            this.setUserInputsToBean(request);
            request.setAttribute("searchfilebean", searchBean);


            if (this.validateUserInput(searchBean)) {

                sessionVarlist.setFileDownloadSearchBean(searchBean);
                sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);


                this.setAudittraceValue(request);
                this.searchFilesToDownload(searchBean, systemAuditBean);




                for (int i = 0; i < searchList.size(); i++) {

                    Set set = sessionVarlist.getFileTypeList().entrySet();
                    Iterator itr = set.iterator();

                    while (itr.hasNext()) {


                        Map.Entry me = (Map.Entry) itr.next();

                        if (searchList.get(i).getFileType().equals(me.getKey().toString())) {

                            searchList.get(i).setFileTypeDes(me.getValue().toString());
                        }

                    }

                }

                request.setAttribute("searchList", searchList);

            } else {
                
                request.setAttribute("errorMsg", errorMessage);
            }
            rd = getServletContext().getRequestDispatcher(url);

        }//catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        }//catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_CARD_PRODUCT);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }
    ///////////////////////////

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

    private void setUserInputsToBean(HttpServletRequest request) throws Exception {
        try {

            String fileType = request.getParameter("filetype");
            String fileName = request.getParameter("filename");
            String generateUser = request.getParameter("generateduser");
            String fromDate = request.getParameter("fromdate");
            String toDate = request.getParameter("todate");


            searchBean = new FileDownloadSearchBean();

            searchBean.setFileType(fileType);
            searchBean.setFileName(fileName);
            searchBean.setGenerateUser(generateUser);
            searchBean.setFromDate(fromDate);
            searchBean.setToDate(toDate);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void searchFilesToDownload(FileDownloadSearchBean searchBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            fileDownloadMgtManager = new FileDownloadMgtManager();
            searchList = fileDownloadMgtManager.getAllFilesToDownload(searchBean, systemAuditBean);

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
            systemAuditBean.setDescription("Search files to download User by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.COMMONFILEDOWNLOAD);
            systemAuditBean.setTaskCode(TaskVarList.SEARCH);
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

    public boolean validateUserInput(FileDownloadSearchBean bean) throws Exception {
        boolean isValidate = true;

        try {

            if (bean.getFileName().length() > 0) {

                if (!UserInputValidator.isAllowNumericNonSpecialString(bean.getFileName())) {
                    isValidate = false;
                    errorMessage = MessageVarList.ERROR_COMMON_FILE_NAME;
                }
            }

        } catch (Exception ex) {
            isValidate = false;
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
