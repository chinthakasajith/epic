package com.epic.cms.backoffice.filemgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.backoffice.filemgt.bean.FileInfoBean;
import com.epic.cms.backoffice.filemgt.bean.FileTypeBean;
import com.epic.cms.backoffice.filemgt.businesslogic.FileInfoManager;
import com.epic.cms.backoffice.filemgt.businesslogic.FileTypeManager;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CardScoreManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.exception.ValidateException;
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
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jeevan
 */
public class UpdateEODFileInfoMgtServlet extends HttpServlet {

    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean;
    private String url = "/backoffice/filemgt/fileinfomgthome.jsp";
    private RequestDispatcher rd;
    private List<StatusBean> statusList = null;
    private FileTypeManager fileTypeManager;
    private FileInfoManager fileInfoManager;
    private List<FileTypeBean> fileList;
    private List<FileInfoBean> fileList2;
    private FileInfoBean bean;
    private FileTypeBean bean2;
    private String errorMessage = null;
    private String newValue;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessionObj = request.getSession(false);
        try {
            try {
                request.setAttribute("operationtype", "update");
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }
                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();
                }

                try {

                    String pageCode = PageVarList.FILE_INFO;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    } else {
                        throw new AccessDeniedException();
                    }

                    sessionVarlist.setUserPageTaskList(userTaskList);

                } catch (AccessDeniedException adex) {
                    throw adex;
                }

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            //////

            this.getFileInfoDetails();
            request.setAttribute("fileList", fileList2);

            String oldValue = request.getParameter("oldvalue");
            this.setValuesToBean(request);

            int success = -1;

            if (this.validateUsetInput(bean)) {
                this.setAudittraceValue(request, oldValue, newValue);

                try {
                    success = this.updateFileInfo();
                } catch (Exception ex) {
                    throw ex;
                }
            } else {
                request.setAttribute("fileInfoBean", bean);
                request.setAttribute("errorMsg", errorMessage);
            }
            if (success > 0) {
                this.getAllFileTypes();
                request.setAttribute("fileTypelist", fileList);


                this.getFileInfoDetails();
                request.setAttribute("fileList", fileList2);

                rd = request.getRequestDispatcher("/LoadEODFileInfoMgtServlet");
                request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.FILE_INFO_UPDATE_SUCCESS + " Code : " + bean.getFileId());
            } else {
                request.setAttribute("fileInfoBean", bean);
                rd = request.getRequestDispatcher(url);
            }

            sessionVarlist.setStatusList(statusList);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);


        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadEODFileInfoMgtServlet");

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (ValidateException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR,
                    CardScoreManager.getScoreCardInstance().getErrorMsg());
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
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

    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {

        boolean isValidTaskAccess = false;

        try {
            systemUserManager = new SystemUserManager();
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

    private void getFileInfoDetails() throws Exception {
        try {
            fileInfoManager = new FileInfoManager();
            fileList2 = fileInfoManager.getAllFiletypesInfo();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setValuesToBean(HttpServletRequest request) throws Exception {
        try {
            bean = new FileInfoBean();
            bean.setFileId(request.getParameter("fileinfocode"));
//            bean.setFileType(request.getParameter("fileInfoFileType"));
            bean.setFileType(request.getParameter("disabledFileType"));
            bean.setFileName(request.getParameter("description"));
            bean.setFilePath(request.getParameter("filepath"));
            bean.setBackupPath(request.getParameter("backuppath"));

            bean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());

            newValue = bean.getFileId() + "|" + bean.getFileType() + "|" + bean.getFileName() + "|"
                    + bean.getFilePath() + "|" + bean.getBackupPath();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean validateUsetInput(FileInfoBean bean) throws Exception {
        boolean isValidate = true;

        try {

            if (bean.getFileId().contentEquals("") || bean.getFileId().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FILE_INFO_EMPTY;

            } else if (bean.getFileType().contentEquals("") || bean.getFileType().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FILE_TYPE_EMPTY;

            } else if (bean.getFileName().contentEquals("") || bean.getFileName().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FILE_DESC_EMPTY;

            } else if (bean.getFilePath().contentEquals("") || bean.getFilePath().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FILE_PATH_EMPTY;

            } else if (bean.getBackupPath().contentEquals("") || bean.getBackupPath().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FILE_BACKUP_PATH_EMPTY;
                
            } else if (bean.getFilePath().equals(bean.getBackupPath())) {
                isValidate = false;
                errorMessage = MessageVarList.FILE_PATH_ERROR;
            }

        } catch (Exception ex) {
            throw ex;
        }
        return isValidate;
    }

    private void setAudittraceValue(HttpServletRequest request, String oldV, String newV) throws Exception {
        try {

            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("fileinfocode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Update File Info. File Info Code: " + uniqueId + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.EOD_PROCESS_MGT);
            systemAuditBean.setPageCode(PageVarList.FILE_INFO);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue(oldV);
            systemAuditBean.setNewValue(newV);

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }

    }

    private int updateFileInfo() throws Exception {
        try {

            fileInfoManager = new FileInfoManager();
            int i = fileInfoManager.updateFileInfo(systemAuditBean, bean);
            return i;
        } catch (Exception ex) {
            throw ex;
        }
    }

     private void getAllFileTypes() throws Exception {
        try {
            fileTypeManager = new FileTypeManager();
            fileList = fileTypeManager.getAllFileTypes();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
