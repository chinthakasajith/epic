/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.security.CMSMd5;
import com.epic.cms.system.util.security.PasswordGenerator;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author janaka_h
 */
public class UpdateSystemUserServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private SessionVarList sessionVarlist;
    private SystemAuditBean systemAuditBean = null;
    private String url = "/LoadSystemUserServlet";
    private String oldValue = "";
    private String newValue = "";
    private List<SystemUserBean> userList;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        SystemUserBean userBean = new SystemUserBean();
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

            //set page code and task codes
            String pageCode = PageVarList.SYSTEMUSER;
            String taskCode = TaskVarList.UPDATE;

            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                userBean.setUserName(request.getParameter("username"));
                userBean.setUserRoleCode(request.getParameter("userroal"));
                userBean.setStatusCode(request.getParameter("status"));
                userBean.setDualUserRoleCode(request.getParameter("dualUserroal"));
                userBean.setTitle(request.getParameter("title"));
                userBean.setInitials(request.getParameter("initials"));
                userBean.setFirstName(request.getParameter("firstname"));
                userBean.setLastName(request.getParameter("lastname"));
                userBean.setBankname(request.getParameter("bankname"));
                userBean.setBranchname(request.getParameter("branchname"));
                userBean.setDesignation(request.getParameter("designation"));
                userBean.setServiseid(request.getParameter("serviseid"));
                userBean.setTelNo(request.getParameter("telephone"));
                userBean.setNic(request.getParameter("nic"));
                userBean.setEmail(request.getParameter("email"));
                userBean.setGender(request.getParameter("gender"));
                userBean.setBirthday(request.getParameter("birthday"));
                userBean.setExpiryDateToString(request.getParameter("expdate"));
                userBean.setIsEmailSent(request.getParameter("isemailsent"));
                userBean.setRejectRemark(request.getParameter("rejectremark"));
                userBean.setLastUpdatedUser(sessionUser.getUserName());
                userBean.setAuthorizedUser(sessionUser.getUserName());
                userBean.setIslockedforauth(request.getParameter("islockedforauth"));
                userBean.setUserstatus(request.getParameter("userstatus"));

                //identifies if the button click is accept or reject
                String isAcceptted = request.getParameter("accept");
                SystemUserBean invalidMsgBean = this.isValiedUser(userBean, isAcceptted);

                SystemUserBean systemUserBean = null;
                this.getAllSysUsers(sessionUser);
                for (SystemUserBean bean : userList) {
                    if (bean.getUserName().equals(userBean.getUserName()) && bean.getIslockedforauth() != null) {
                        systemUserBean = bean;
                        break;
                    }
                }
                //dual auth flag from system user table
                if (systemUserBean != null) {
                    userBean.setIslockedforauth(systemUserBean.getIslockedforauth());
                }

                if (invalidMsgBean == null) {

                    StringBuffer buff = new StringBuffer();

                    buff.append(userBean.getUserName()).append("|");
                    buff.append(userBean.getUserRoleCode()).append("|").append(userBean.getStatusCode()).append("|").append(userBean.getDualUserRoleCode()).append("|");
                    buff.append(userBean.getTitle()).append("|").append(userBean.getInitials()).append("|").append(userBean.getFirstName()).append("|").append(userBean.getLastName()).append("|");
                    buff.append(userBean.getBankname()).append("|").append(userBean.getBranchname()).append("|").append(userBean.getDesignation()).append("|").append(userBean.getServiseid()).append("|");
                    buff.append(userBean.getTelNo()).append("|").append(userBean.getNic()).append("|").append(userBean.getEmail()).append("|").append(userBean.getGender()).append("|").append(userBean.getBirthday()).append("|").append(userBean.getExpiryDateToString()).append("|");
                    buff.append(userBean.getRequestedUser()).append("|").append(userBean.getAuthorizedUser());

                    newValue = buff.toString();

                    if (null != systemUserBean) {
                        StringBuffer buff1 = new StringBuffer();

                        buff1.append(systemUserBean.getUserName()).append("|");
                        buff1.append(systemUserBean.getUserRoleCode()).append("|").append(systemUserBean.getStatusCode()).append("|").append(systemUserBean.getDualUserRoleCode()).append("|");
                        buff1.append(systemUserBean.getTitle()).append("|").append(systemUserBean.getInitials()).append("|").append(systemUserBean.getFirstName()).append("|").append(systemUserBean.getLastName()).append("|");
                        buff1.append(systemUserBean.getBankname()).append("|").append(systemUserBean.getBranchname()).append("|").append(systemUserBean.getDesignation()).append("|").append(systemUserBean.getServiseid()).append("|");
                        buff1.append(systemUserBean.getTelNo()).append("|").append(systemUserBean.getNic()).append("|").append(systemUserBean.getEmail()).append("|").append(systemUserBean.getGender()).append("|").append(systemUserBean.getBirthday()).append("|").append(systemUserBean.getExpiryDateToString()).append("|");
                        buff1.append(userBean.getRequestedUser()).append("|");
                        buff1.append(userBean.getAuthorizedUser()).append("|").append(systemUserBean.getIsEmailSent());

                        oldValue = buff1.toString();
                    }

                    this.setAudittraceValue(request, userBean);
                    systemUserManager = new SystemUserManager();

                    if (isAcceptted != null) {

                        userBean.setAuthorizedUser(sessionUser.getUserName());
                        //activate or deactivate the user request
                        if (StatusVarList.DA_REQUEST_APPROVE_DES.equals(isAcceptted)) {

                            if (StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT.equals(userBean.getStatusCode())) {
                                //generate 8 character random generated password
                                PasswordGenerator passwordGenerator = new PasswordGenerator();
                                userBean.setPlainTextPassword(passwordGenerator.getRandomPassword(8));

                                String password = CMSMd5.cmsMd5(userBean.getPlainTextPassword());
                                userBean.setPassword(password);

                                systemUserManager.updateSystemUser(userBean, systemAuditBean);

                                //indicate user approval
                                request.setAttribute("successMsg", MessageVarList.SUCCESS_ADD_USER);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);

                            } else if (StatusVarList.DA_REQUSET_INITIATE.equals(userBean.getStatusCode())) {

                                //indicate this is for request accept
                                //replace systemuserRequest table userStatus value to systemuser table -> status
                                userBean.setStatusCode(StatusVarList.ACTIVE_STATUS);

                                systemUserManager.updateSystemUser(userBean, systemAuditBean);

                                //user approval accepted
                                userBean.setInitialloginstatus(StatusVarList.IS_NOT_LOCKED_AUTH);
                                systemUserManager.updateSystemUserAuthLock(userBean);
                                request.setAttribute("successMsg", MessageVarList.SUCCESS_USER_APPROVED);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);

                            }

                        } else if (StatusVarList.DA_REQUEST_REJECT_DES.equals(isAcceptted)) {
                            userBean.setStatusCode(StatusVarList.DA_REQUSET_REJECT);

                            systemUserManager.updateSystemUser(userBean, systemAuditBean);

                            //user approval rejected
                            userBean.setInitialloginstatus(StatusVarList.IS_NOT_LOCKED_AUTH);
                            systemUserManager.updateSystemUserAuthLock(userBean);
                            request.setAttribute("successMsg", MessageVarList.SUCESS_USER_REJECTED);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }
                    } else {
                        //check user already has a dual auth request
                        if (userBean.getIslockedforauth() != null && StatusVarList.IS_LOCKED_AUTH.equals(userBean.getIslockedforauth())) {
                            request.setAttribute("errorMsg", MessageVarList.DUAL_AUTH_LOCK);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                            out.close();
                        } else {
                            //set system user status to user status for use when request acceptted
                            userBean.setUserstatus(userBean.getStatusCode());

                            //indicate user approval request
                            userBean.setStatusCode(StatusVarList.DA_REQUSET_INITIATE);
                            userBean.setRequestedUser(sessionUser.getUserName());

                            systemUserManager.updateSystemUser(userBean, systemAuditBean);

                            request.setAttribute("successMsg", MessageVarList.USER_APPROVAL_SENT);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }

                    }

                } else {
                    //set status to RQIN
                    userBean.setStatusCode(StatusVarList.DA_REQUSET_INITIATE);
                    request.setAttribute("userBean", userBean);
                    request.setAttribute("operation", "edit");
                    request.setAttribute("invalidMsgBean", invalidMsgBean);
                    rd = getServletContext().getRequestDispatcher("/ManageSystemUserServlet");
                    rd.forward(request, response);
                }

            } else {

                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }

        } catch (SQLException ex) {
            request.setAttribute("userBean", userBean);
            request.setAttribute("operation", "edit");
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            rd = getServletContext().getRequestDispatcher("/ManageSystemUserServlet");
            rd.forward(request, response);
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

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);

        } catch (MessagingException mex) {
            request.setAttribute("operation", "edit");
            request.setAttribute("errorMsg", MessageVarList.EMAIL_SENT_ERROR);
            rd = getServletContext().getRequestDispatcher("/ManageSystemUserServlet");
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("userBean", userBean);
            request.setAttribute("operation", "edit");
            request.setAttribute("errorMsg", MessageVarList.ERROR_UPDATE);
            rd = getServletContext().getRequestDispatcher("/ManageSystemUserServlet");
            rd.forward(request, response);

        } finally {
            out.close();
        }
    }

    private void setAudittraceValue(HttpServletRequest request, SystemUserBean userBean) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.SYSTEMUSER);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(oldValue);
            //set new value of change if required
            systemAuditBean.setNewValue(newValue);

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

    private SystemUserBean isValiedUser(SystemUserBean userBean, String isAccepted) throws Exception {
        UserInputValidator validObject = new UserInputValidator();
        SystemUserBean invalidMsgBean = new SystemUserBean();
        int msg = 0;
        try {

            //----------------------------------------------------------------------------   
            if (userBean.getUserRoleCode().isEmpty()) {
                invalidMsgBean.setUserRoleCode("Required");
                msg = 1;
            } else {
            }
            //----------------------------------------------------------------------------            
            if (userBean.getStatusCode().isEmpty()) {
                invalidMsgBean.setStatusCode("Required");
                msg = 1;
            } else {
            }
            //----------------------------------------------------------------------------            
            if (userBean.getDualUserRoleCode().isEmpty()) {
                invalidMsgBean.setDualUserRoleCode("Required");
                msg = 1;
            } else {
            }
            //----------------------------------------------------------------------------           
            if (userBean.getTitle() == null) {
                invalidMsgBean.setTitle("Required");
                msg = 1;
            }
            //----------------------------------------------------------------------------           
            if (userBean.getInitials().isEmpty()) {
                invalidMsgBean.setInitials("Required");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(userBean.getInitials())) {
                invalidMsgBean.setInitials("Invalid");
                msg = 1;
            }
            //----------------------------------------------------------------------------           
            if (userBean.getFirstName().isEmpty()) {
                invalidMsgBean.setFirstName("Required");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(userBean.getFirstName())) {
                invalidMsgBean.setFirstName("Invalid");
                msg = 1;
            }
            //----------------------------------------------------------------------------            
            if (userBean.getLastName().isEmpty()) {
                invalidMsgBean.setLastName("Required");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(userBean.getLastName())) {
                invalidMsgBean.setLastName("Invalid");
                msg = 1;
            }
            //----------------------------------------------------------------------------            
            if (userBean.getBankname().isEmpty()) {
                invalidMsgBean.setBankname("Required");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(userBean.getBankname())) {
                invalidMsgBean.setBankname("Invalid");
                msg = 1;
            }
            //----------------------------------------------------------------------------            
            if (userBean.getBranchname().isEmpty()) {
                invalidMsgBean.setBranchname("Required");
                msg = 1;
            }
            //----------------------------------------------------------------------------            
            if (userBean.getDesignation().isEmpty()) {
                //invalidMsgBean.setDesignation("Required");
                //msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(userBean.getDesignation())) {
                invalidMsgBean.setDesignation("Invalid");
                msg = 1;
            }
            //----------------------------------------------------------------------------            
            if (userBean.getServiseid().isEmpty()) {
                invalidMsgBean.setServiseid("Required");
                msg = 1;
            } else if (!validObject.isAlphaNumeric(userBean.getServiseid())) {
                invalidMsgBean.setServiseid("Invalid");
                msg = 1;
            }
            //----------------------------------------------------------------------------            
            if (userBean.getTelNo().isEmpty()) {
                invalidMsgBean.setTelNo("Required");
                msg = 1;
            } else if (!validObject.isNumeric(userBean.getTelNo())) {
                invalidMsgBean.setTelNo("Invalid");
                msg = 1;
            }
            //----------------------------------------------------------------------------            
            if (userBean.getNic().isEmpty()) {
                invalidMsgBean.setNic("Required");
                msg = 1;
            } else if (!validObject.checkNIC(userBean.getNic())) {
                invalidMsgBean.setNic("Invalid");
                msg = 1;
            }
            //----------------------------------------------------------------------------            
            if (userBean.getEmail().isEmpty()) {
                invalidMsgBean.setEmail("Required");
                msg = 1;
            } else if (!validObject.isValidEmail(userBean.getEmail())) {
                invalidMsgBean.setEmail("Invalid");
                msg = 1;
            }
            //----------------------------------------------------------------------------            
            if (userBean.getGender() == null) {
                invalidMsgBean.setGender("Required");
                msg = 1;
            } else {
            }
            //----------------------------------------------------------------------------            
            if (userBean.getBirthday().isEmpty()) {
                //invalidMsgBean.setBirthday("Required");
                //msg = 1;
            } else {
            }
            //----------------------------------------------------------------------------
            if (userBean.getExpiryDateToString().isEmpty()) {
                invalidMsgBean.setExpiryDateToString("Required");
                msg = 1;
            } else {
            }
            //----------------------------------------------------------------------------
            if ((StatusVarList.DA_REQUSET_INITIATE.equals(userBean.getStatusCode()) || StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT.equals(userBean.getStatusCode())) && StatusVarList.DA_REQUEST_REJECT_DES.equals(isAccepted) && userBean.getRejectRemark().isEmpty()) {
                invalidMsgBean.setRejectRemark("Required");
                msg = 1;
            } else {
            }
            //----------------------------------------------------------------------------

            if (msg == 0) {
                return null;

            } else {
                return invalidMsgBean;
            }

        } catch (NullPointerException ex) {
            return null;
            //throw ex;
        }

    }

    public void getAllSysUsers(SessionUser sessionUser) throws Exception {
        systemUserManager = new SystemUserManager();
        userList = systemUserManager.getAllSysUsersExceptDel(sessionUser);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
            Logger.getLogger(UpdateSystemUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
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
            Logger.getLogger(UpdateSystemUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
