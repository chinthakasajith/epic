package com.epic.cms.backoffice.filemgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BankBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.BankMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.filemgt.bean.FileTypeBean;
import com.epic.cms.backoffice.filemgt.businesslogic.FileTypeManager;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CardScoreManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.exception.ValidateException;
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
 * @author jeevan
 */
public class DeleteEODFileTypeMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/backoffice/filemgt/filetypemgthome.jsp";
    private SystemUserManager systemUserManager = null;
    private BankMgtManager bankMgtManager;
//    private BankBean bean;
    public List<BankBean> banklist;
    private SystemAuditBean systemAuditBean;
    private String errorMessage = null;
    private FileTypeManager fileTypeManager;
    private List<FileTypeBean> fileList;
    private FileTypeBean bean;
    private String id;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            request.setAttribute("operationtype", "add");
            try {

                HttpSession sessionObj = request.getSession(false);
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();
                try {
                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(),
                            sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }
                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();

                }
            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.DELETE)) {
                throw new AccessDeniedException();
            }

            id = request.getParameter("id");
            this.setAudittraceValue(request);
            try {
                int success = deleteFileType(id);
                System.out.println("****************  " + success);
                if (success > 0) {

                    request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.FILE_TYPE_DELETE_SUCCESS + " Code : " + id);

                    this.getFileInfoDetails();
                    request.setAttribute("fileList", fileList);

                    this.getCardTypeDetails();
                    request.setAttribute("cardTypeList", fileList);

                    this.getCardDomainDetails();
                    request.setAttribute("cardDomainList", fileList);

                    this.getSendingChannel();
                    request.setAttribute("channelList", fileList);

                    rd = request.getRequestDispatcher(url);
                } else {
                    this.getFileInfoDetails();
                    request.setAttribute("fileList", fileList);
                    rd = request.getRequestDispatcher(url);
                }
            } catch (SQLException ex) {
                this.getFileInfoDetails();
                request.setAttribute("fileList", fileList);
                throw ex;
            }



        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadEODFileInfoMgtServlet");
            //rd.include(request, response);
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

    private boolean isValidTaskByUser(List<String> userTaskList, String task) throws Exception {
        boolean isValidTask = false;
        try {
            for (String usertask : userTaskList) {
                if (task.equals(usertask)) {
                    isValidTask = true;
                }
            }
            return isValidTask;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {
        try {

            systemAuditBean = new SystemAuditBean();
//            String uniqueId = request.getParameter("filetypecode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(id);
            systemAuditBean.setDescription("Delete File Type. File Type Code: " + id + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.EOD_PROCESS_MGT);
            systemAuditBean.setPageCode(PageVarList.FILE_TYPE);
            systemAuditBean.setTaskCode(TaskVarList.DELETE);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(id);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue("");
            systemAuditBean.setNewValue("");

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }

    }

    private int deleteFileType(String id) throws Exception {
        try {

            fileTypeManager = new FileTypeManager();
            int i = fileTypeManager.deleteFileType(id, systemAuditBean);
            return i;

        } catch (SQLException ex) {
            throw ex;
        }
    }

    /*
    public int deleteBank(String id) throws Exception {
    try {
    bankMgtManager = new BankMgtManager();
    int i = bankMgtManager.deleteBank(id, systemAuditBean);
    return i;
    } catch (Exception e) {
    throw e;
    }
    
    }
     */
    private void getFileInfoDetails() throws Exception {
        try {
            fileTypeManager = new FileTypeManager();
            fileList = fileTypeManager.getAllFileTypes();
        } catch (Exception ex) {
            throw ex;
        }
    }

 private void getCardTypeDetails() throws SQLException, Exception {
        try {

            fileTypeManager = new FileTypeManager();
            fileList = fileTypeManager.getAllCardTypeDetails();

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardDomainDetails() throws SQLException, Exception {
        try {

            fileTypeManager = new FileTypeManager();
            fileList = fileTypeManager.getCardDomainDetails();

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getSendingChannel() throws SQLException, Exception {
        try {

            fileTypeManager = new FileTypeManager();
            fileList = fileTypeManager.getSendingChannel();

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
