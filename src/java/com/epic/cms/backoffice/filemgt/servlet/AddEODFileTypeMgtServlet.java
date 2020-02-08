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
public class AddEODFileTypeMgtServlet extends HttpServlet {

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
    private String newValue;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {

            try {
                request.setAttribute("operationtype", "add");
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

            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.ADD)) {
                throw new AccessDeniedException();
            }



            this.getFileInfoDetails();
            request.setAttribute("fileList", fileList);

            this.getCardTypeDetails();
            request.setAttribute("cardTypeList", fileList);

            this.getCardDomainDetails();
            request.setAttribute("cardDomainList", fileList);

            this.getSendingChannel();
            request.setAttribute("channelList", fileList);

            String oldValue = request.getParameter("oldvalue");
            this.setValuesToBean(request);

            //Validation
            int success = -1;
            if (this.validateUserInput(bean)) {
                this.setAudittraceValue(request, oldValue, newValue);

                try {
                    success = this.insertNewFileType();
                } catch (Exception ex) {
                    throw ex;
                }
            } else {
                request.setAttribute("fileBean", bean);
                request.setAttribute("errorMsg", errorMessage);
            }

            if (success > 0) {
                this.getFileInfoDetails();
                request.setAttribute("fileList", fileList);
                request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.FILE_TYPE_ADD_SUCCESS);
            }

            rd = request.getRequestDispatcher(url);

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadEODFileTypeMgtServlet");

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

    private void getFileInfoDetails() throws Exception {
        try {
            fileTypeManager = new FileTypeManager();
            fileList = fileTypeManager.getAllFileTypes();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setValuesToBean(HttpServletRequest request) throws Exception {
        try {

            bean = new FileTypeBean();
            bean.setFileTypeCode(request.getParameter("filetypecode"));
            bean.setDescription(request.getParameter("description"));
            bean.setFileNamePrefix(request.getParameter("filenameprefix"));
            bean.setFileNamePostfix(request.getParameter("filenamepostfix"));
            bean.setFileExtension(request.getParameter("extension"));
            bean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());

//            bean.setCardTypeDescription(request.getParameter("cardType"));
//            bean.setDomainDescription(request.getParameter("cardDomain"));
//            bean.setChannelDescription(request.getParameter("sendingChannle"));
            
            bean.setCardTypeCode(request.getParameter("cardType"));
            bean.setDomainId(request.getParameter("cardDomain"));
            bean.setChannelType(request.getParameter("sendingChannle"));

        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean validateUserInput(FileTypeBean bean) throws Exception {
        boolean isValidate = true;

        try {
            if (bean.getFileTypeCode().contentEquals("") || bean.getFileTypeCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FILE_TYPE_EMPTY;
                
            } else if (bean.getDescription().contentEquals("") || bean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FILE_DESC_EMPTY;
                
            } else if (bean.getFileNamePrefix().contentEquals("") || bean.getFileNamePrefix().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FILE_NAME_PREFIX_EMPTY;
                
            } else if (bean.getFileNamePostfix().contentEquals("") || bean.getFileNamePostfix().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FILE_NAME_POSTFIX_EMPTY;
                
            } else if (bean.getFileExtension().contentEquals("") || bean.getFileExtension().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FILE_EXTENSION_EMPTY;
                
            } else if (bean.getCardTypeCode().contentEquals("") || bean.getCardTypeCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_TYPE_VALUE_EMPTY;
                
            } else if (bean.getDomainId().contentEquals("") || bean.getDomainId().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.DOMAIN_TYPE_VALUE_EMPTY;
                
            } else if (bean.getChannelType().contentEquals("") || bean.getChannelType().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CHANNEL_TYPE_VALUE_EMPTY;
            }
            
        } catch (Exception ex) {
            isValidate = false;
        }
        return isValidate;
    }

    private int insertNewFileType() throws Exception {
        try {

            fileTypeManager = new FileTypeManager();
            int i = fileTypeManager.addNewFileType(systemAuditBean, bean);
            return i;

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setAudittraceValue(HttpServletRequest request, String oldV, String newV) throws Exception {
        try {

            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("filetypecode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Add File Type. File Type Code: " + uniqueId + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.EOD_PROCESS_MGT);
            systemAuditBean.setPageCode(PageVarList.FILE_TYPE);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
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
