/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.application.servlet.login;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.PasswordPolicyBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.PasswordPolicyManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserPasswordBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.security.CMSMd5;
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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nisansala
 */
public class UpdateChangePasswordServlet extends HttpServlet {
private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    String errorMsg = "";
    private String url = "/administrator/controlpanel/login/changepassword.jsp";
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            String taskCode = TaskVarList.ADD;


            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                
                //get form values
                userBean.setUserName(sessionUser.getUserName());
                userBean.setCurrentPassword(request.getParameter("current"));
                userBean.setPassword(request.getParameter("new"));
                userBean.setConfirmPassword(request.getParameter("confirm"));
                userBean.setCurrentHashPwd(CMSMd5.cmsMd5(userBean.getCurrentPassword()));
                userBean.setNewHashPwd(CMSMd5.cmsMd5(userBean.getPassword()));
                
                //validate and check password policy
                SystemUserBean invalidMsgBean = this.isValidUser(userBean);

                //if valid
                if (invalidMsgBean == null) {
                    //hash value of the user given current password
                    String currentHashpassword = CMSMd5.cmsMd5(userBean.getCurrentPassword());
                    //hash value of the user given new password
                    String newHashPassword = CMSMd5.cmsMd5(userBean.getPassword());  
                    
                    userBean.setCurrentPassword(currentHashpassword);
                    userBean.setPassword(newHashPassword);

                    systemUserManager = new SystemUserManager();
                    userBean.setLastUpdatedUser(sessionUser.getUserName());
                    this.setAudittraceValue(request, userBean);
                    int isChanged = systemUserManager.changePassword(userBean, systemAuditBean ,false);
                    
                    //update initial login state if this is new user
                    userBean.setInitialloginstatus(StatusVarList.IS_NOT_FIRST_LOGIN);
                    isChanged+=systemUserManager.updateSystemUserFirstLoginStatus(userBean);


                    if (isChanged == 2) {
                        request.setAttribute("successMsg", "Sucssesfully changed password.");
                        rd = getServletContext().getRequestDispatcher("/LogoutServlet");
                        rd.forward(request, response);
                    } else {

                        request.setAttribute("userBean", userBean);
                        request.setAttribute("operation", "add");
                        request.setAttribute("errorMsg", "Password Changed Failed.");
                        rd = getServletContext().getRequestDispatcher("/ViewChangePasswordServlet");
                        rd.forward(request, response);
                    }

                } else {
                    request.setAttribute("userBean", userBean);
                    request.setAttribute("operation", "add");
                    request.setAttribute("invalidMsgBean", invalidMsgBean);
                    rd = getServletContext().getRequestDispatcher("/ViewChangePasswordServlet");
                    rd.forward(request, response);
                }



            } else {

                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }


        } catch (SQLException ex) {
            request.setAttribute("userBean", userBean);
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            rd = getServletContext().getRequestDispatcher("/ViewChangePasswordServlet");
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


        } catch (Exception ex) {
            request.setAttribute("userBean", userBean);
            
            request.setAttribute("errorMsg", "Error occurred when changing password.");
            rd = getServletContext().getRequestDispatcher("/ViewChangePasswordServlet");
            rd.forward(request, response);

        } finally {
            //rd = getServletContext().getRequestDispatcher(url);
            //rd.forward(request, response);
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
            //set description 
            systemAuditBean.setDescription("Change Password for'" + userBean.getUserName() + "' by :" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.SYSTEMUSER);
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
    
    private SystemUserBean isValidUser(SystemUserBean userBean) throws Exception {
        UserInputValidator validObject = new UserInputValidator();
        SystemUserBean invalidMsgBean = new SystemUserBean();
        PasswordPolicyBean passwordPolicyBean = new PasswordPolicyBean();
        int msg = 0;
        try {
            //----------------------------------------------------------------------------           

            if (userBean.getCurrentPassword().isEmpty()) {
                //errorMsg = "Current Password Required";
                invalidMsgBean.setCurrentPassword("Requierd");
                msg = 1;
            } else if (checkCurrentPassword(userBean) == -2) {
                //errorMsg = "Current Password Invalid";
                invalidMsgBean.setCurrentPassword("No such user");
                msg = 1;
            }else if (checkCurrentPassword(userBean) == -3) {
                //errorMsg = "Current Password Invalid";
                invalidMsgBean.setCurrentPassword("The password you gave is incorrect");
                msg = 1;
            }
            //----------------------------------------------------------------------------            
            if (userBean.getPassword().isEmpty()) {
                //errorMsg = "New Password Required";
                invalidMsgBean.setPassword("Requierd");
                msg = 1;
            } else {
                passwordPolicyBean = PasswordPolicyManager.getPasswordPolicyManager().getPasswordPolicyDetails();
                 List<UserPasswordBean> userPasswordBeanList=systemUserManager.getUserPasswordBeanList(userBean.getUserName(),userBean.getPassword()); 
                String sucsses=validObject.CheckPasswordValidity(userBean.getPassword(), passwordPolicyBean,userPasswordBeanList);
                if(sucsses!=null){
                    invalidMsgBean.setPassword(sucsses);
                    msg =1;
                }
            }
            //---------------------------------------------------------------------------- 
            if (userBean.getConfirmPassword().isEmpty()) {
                invalidMsgBean.setConfirmPassword("Requierd");
                msg = 1;
            } 
//            if (!userBean.getPassword().equals(userBean.getConfirmPassword())) {
//                if (userBean.getConfirmPassword() == null) {
//                    invalidMsgBean.setPsWdMatch("Passwords Do not match");
//                    msg = 1;
//                }
//            }else {
//            }
            //---------------------------------------------------------------------------- 
            if (!userBean.getPassword().isEmpty() && !userBean.getConfirmPassword().isEmpty()) {
                if (!userBean.getPassword().equals(userBean.getConfirmPassword())) {
                    invalidMsgBean.setPsWdMatch("Passwords Do not match");
                    msg = 1;
                }
            }
            else {
            }
            

            if (msg == 0) {
                return null;

            } else {
                return invalidMsgBean;
            }


        } catch (Exception ex) {
            throw ex;
        }


    }
    
    public int checkCurrentPassword(SystemUserBean userBean) throws Exception{
        int isChecked;
        systemUserManager = new SystemUserManager();
        isChecked = systemUserManager.checkPassword(userBean);
        return isChecked;
        
        
        
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
