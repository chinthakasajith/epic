/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.EmbossFileFormatBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.EmbossFileFormatMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author badrika
 */
public class DeleteEmbossFileFormatMgtServlet extends HttpServlet {

    private SystemUserManager systemUserManager=null;
    private SessionVarList sessionVarlist=null;
    private SessionUser sessionUser=null;    
    private RequestDispatcher rd;
    private String url = "/administrator/controlpanel/systemconfigmgt/embossfileformat.jsp";
    private EmbossFileFormatMgtManager effmManager;
    private List<EmbossFileFormatBean> formatList=null; 
    private SystemAuditBean systemAuditBean;
    private String id;
    private String oldValue;
    private EmbossFileFormatBean embean = null;                

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
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
            
            //////////////////////////////////////////////////////////
            
            id = request.getParameter("id");
            
            effmManager = new EmbossFileFormatMgtManager();            
            embean = effmManager.viewSelectedEmbossFileFormat(id);
            if (embean != null) {

                String rfold = embean.getRecordFormat().replace("|", ",");

                 oldValue = embean.getFormatCode() + "|" + embean.getDescription() + "|" + embean.getRecordCount() + "|"
                        + embean.getStatus() + "|" + rfold;

               // request.setAttribute("oldval", oldValue);
            }            
            
            this.setAudittraceValue(request);
            
            int success = deleteEmbossFileFomat(id);
            
            if (success > 0) {
                
                request.setAttribute(MessageVarList.JSP_SUCCESS,MessageVarList.FORMAT_DELETE_SUCCESS);
            }
            
            this.getAllEmbossFileFormats();
            request.setAttribute("formatList", formatList);
            rd = request.getRequestDispatcher(url);                         
                                              
            //////////////////////////////////////////////////////////
                                    
        }catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadEmbossFileFormatMgtServlet");
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
        }
        
        finally {
            rd.forward(request, response);            
        }
    }
             
    /**
     * to check task to user validity
     * @param userTaskList
     * @param task
     * @return
     * @throws Exception 
     */
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
    
    /**
     * to delete a record
     * @param id
     * @return
     * @throws Exception 
     */
    public int deleteEmbossFileFomat(String id) throws Exception {
        try {
            effmManager = new EmbossFileFormatMgtManager();
            int i = effmManager.deleteEmbossFileFomat(id,systemAuditBean);
            return i;
        } catch (Exception e) {
            throw e;
        }

    }
    
    /**
     * to get all emboss file format details to the user view
     * @throws Exception 
     */
    private void getAllEmbossFileFormats() throws Exception {
        try {

            effmManager = new EmbossFileFormatMgtManager();
            formatList = effmManager.getAllEmbossFileFormats();

        } catch (Exception ex) {
            throw ex;
        }
    }
    
    /**
     * to set audit trace values
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(id);
            systemAuditBean.setDescription("Delete File Format. Format Code: " + id + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.EMBOSSFILEFORMAT);
            systemAuditBean.setTaskCode(TaskVarList.DELETE);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(id);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue(oldValue);
            systemAuditBean.setNewValue("");

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(DeleteEmbossFileFormatMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(DeleteEmbossFileFormatMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
