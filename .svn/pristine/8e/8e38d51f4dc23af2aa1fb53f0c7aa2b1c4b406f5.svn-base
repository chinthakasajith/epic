/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.communicationkeys.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.switchcontrol.communicationkeys.bean.KeyBean;
import com.epic.cms.switchcontrol.communicationkeys.bean.LcsStatusBean;
import com.epic.cms.switchcontrol.communicationkeys.businesslogic.KeyManager;
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
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author asela
 */
public class UpdateCommunicationKeyServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private String url = "/switch/communicationkeys/communicationkeyshome.jsp";
    HttpSession sessionObj = null;
    private List<String> userTaskList;
    private KeyManager keyManager;
    private List<KeyBean> keyBeanList;
    private KeyBean keybean = null;
    private SystemAuditBean systemAuditBean = null;
    private String errorMessage;
    private List<LcsStatusBean> lcsStatusBeans;
    private Boolean successInsert;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();


        try {


            try {
                sessionObj = request.getSession(false);

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


            //set page code and task codes
            String pageCode = PageVarList.COMMUNICATION_KEYS;
            String taskCode = TaskVarList.UPDATE;

            try {

                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    KeyBean bean = new KeyBean();

                    this.setUserInputToBean(request);
                    this.getKeyBeanList();
                    this.getLcsStatusBeanList();
                    if (validateUserInput(keybean)) {
                        this.setAudittraceValue(request);
                        try {
                            successInsert = updateCommunicationKeyDetails(keybean);
                            if (successInsert) {
                                this.getKeyBeanList();
                                for (KeyBean keyBean : keyBeanList) {
                                    if (keyBean.getId().equals(keybean.getId())) {
                                        bean = keyBean;
                                        if (null != bean) {
                                            request.setAttribute("keybean", bean);
                                        }
                                    }
                                }
                                rd = getServletContext().getRequestDispatcher("/switch/communicationkeys/communicationkeyupdate.jsp");
                                request.setAttribute("successMsg", MessageVarList.SUCCESS_UPDATE_COMMON + "'" + keybean.getId() + "' Card Key Id ");
                            } else {
                                throw new Exception();
                            }

                        } catch (SQLException e) {
                            //show the messages which has thrown when inserting

                            rd = getServletContext().getRequestDispatcher(url);
                            request.setAttribute("operationtype", "update");
                            request.setAttribute("keybeanList", keyBeanList);
                            //   keybean.setId("");
                            request.setAttribute("keybean", keybean);
                            request.setAttribute("lcsStatusBeans", lcsStatusBeans);
                            OracleMessage message = new OracleMessage();
                            String oraMessage = message.getMessege(e.getMessage());
                            request.setAttribute("errorMsg", oraMessage);
                        }
                    } else {
                        //if the user inputs are invalid go to the home page with an error message
                        rd = getServletContext().getRequestDispatcher(url);
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("keybeanList", keyBeanList);
                        //  keybean.setId("");
                        request.setAttribute("keybean", keybean);
                        request.setAttribute("lcsStatusBeans", lcsStatusBeans);
                        request.setAttribute("errorMsg", errorMessage);

                    }

                } else {
                    throw new AccessDeniedException();

                }
            } catch (AccessDeniedException adex) {
                throw adex;

            }
        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);

        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
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

    private void getKeyBeanList() throws Exception {
        try {
            keyManager = new KeyManager();
            keyBeanList = keyManager.getKeyBeanList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            keybean = new KeyBean();

            keybean.setId(request.getParameter("keyid"));
            keybean.setDescription(request.getParameter("description"));
            keybean.setCommunicationType(request.getParameter("lcstype"));

        } catch (Exception e) {
            status = false;
            throw e;

        }
        return status;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(keybean.getId());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Card Keys. Key Id : '" + uniqueId + "' by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.SWITCH_CONTROL_PANEL);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.COMMUNICATION_KEYS);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
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

    public boolean validateUserInput(KeyBean keybean) throws Exception {

        boolean isValidate = true;
        try {
            if (keybean.getId().contentEquals("") || keybean.getId() == null) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_KEY_ID_NULL;
            } else if (!UserInputValidator.isNumeric(keybean.getId())) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_KEY_ID__INVALID;
            } else if (keybean.getDescription().contentEquals("") || keybean.getDescription() == null) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_KEY_DESCRIPTION_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public boolean updateCommunicationKeyDetails(KeyBean keyBean) throws Exception {
        boolean success = false;
        try {
            keyManager = new KeyManager();
            success = keyManager.updateCommunicationKeyDetails(keyBean, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }

    private void getLcsStatusBeanList() throws Exception {
        try {
            keyManager = new KeyManager();
            lcsStatusBeans = keyManager.getLcsStatusBeanList();
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
