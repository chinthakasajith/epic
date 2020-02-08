package com.epic.cms.switchcontrol.keymgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CardScoreManager;
import com.epic.cms.switchcontrol.keymgt.bean.ListnerKeyMailBean;
import com.epic.cms.switchcontrol.keymgt.businesslogic.ListnerKeyMailingManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.exception.ValidateException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
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
public class SearchListnerKeyMailingServlet extends HttpServlet {

    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean;
    private SessionUser sessionUser = null;
    private List<String> userTaskList;
    private String url = "/switch/keymanagement/listnerkeymailing.jsp";
    private RequestDispatcher rd;
    private List<StatusBean> statusList;
    private String errorMessage = null;
    private List<ListnerKeyMailBean> listnerList;
    private ListnerKeyMailingManager listnerManager;
    private ListnerKeyMailBean inputBean = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessionObj = request.getSession(false);
        try {
            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }
                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }
                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();
                }



                    String pageCode = PageVarList.LISTNER_KEY_MAILING;
                    String taskCode = TaskVarList.SEARCH;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                        this.getAllListnetTypes();
                        request.setAttribute("listnerList", listnerList);

                        this.getAllCommunicationKeys();
                        request.setAttribute("comkeyList", listnerList);

                        this.setUserInputToBean(request);
                        request.setAttribute("listnerBean", inputBean);
                        
                        if (this.validateUserInput(inputBean, request)) {
                            this.searchKeyMailing(inputBean);
                            
                            request.setAttribute("searchList", listnerList);
                            
                        } else {
                            request.setAttribute("errorMsg", errorMessage);
                        }
                         rd = getServletContext().getRequestDispatcher(url);

                    } else {
                        throw new AccessDeniedException();
                    }

                    sessionVarlist.setUserPageTaskList(userTaskList);



        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadListnerKeyMailingServlet");

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

    private void getAllListnetTypes() throws Exception {
        try {

            listnerManager = new ListnerKeyMailingManager();
            listnerList = listnerManager.getAllListnetTypes();

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllCommunicationKeys() throws Exception {
        try {

            listnerManager = new ListnerKeyMailingManager();
            listnerList = listnerManager.getAllCommunicationKeys();

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
     private void searchKeyMailing(ListnerKeyMailBean inputBean) throws SQLException, Exception {
         try {
             
             listnerManager = new ListnerKeyMailingManager();
             listnerList = listnerManager.searchKeyMailing(inputBean);
             
         } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setUserInputToBean(HttpServletRequest request) {
        inputBean = new ListnerKeyMailBean();

        inputBean.setListnerTypeDesc(request.getParameter("listnername"));
        inputBean.setListnerTypeCode(request.getParameter("listnerType"));
        inputBean.setComKeyId(request.getParameter("comkeyType"));
    }

    private boolean validateUserInput(ListnerKeyMailBean inputBean, HttpServletRequest request) throws Exception {
        boolean isValidate = true;
        
        try {
            
//             if (inputBean.getListnerTypeDesc().contentEquals("") || inputBean.getListnerTypeDesc().length() <= 0) {
//                isValidate = false;
//                errorMessage = MessageVarList.LISTENER_NAME_EMPTY;
//                
//            } 
             if (!UserInputValidator.isAlphaNumeric(inputBean.getListnerTypeDesc())) {
                isValidate = false;
                errorMessage = MessageVarList.LISTENER_NAME_INVALID;
                
            } 
       /*   else if (inputBean.getListnerTypeCode().contentEquals("") || inputBean.getListnerTypeCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.LISTENER_TYPR_EMPTY;
                
            } 
                else if (inputBean.getComKeyId().contentEquals("") || inputBean.getComKeyId().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.COM_KEY_EMPTY;
            }
       */
            
        } catch (Exception ex) {
            isValidate = false;
        }
        return isValidate;
    }

   
}