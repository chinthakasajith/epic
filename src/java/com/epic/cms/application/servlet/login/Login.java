/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.application.servlet.login;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.PasswordPolicyBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.PasswordPolicyManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserPasswordBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.datetime.DateUtil;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.security.CMSMd5;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Login extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private SystemUserBean CMSSysUser = null;
    private SystemUserBean valideUser = null;
    private SessionUser CMSSessionUser = null;
    private SessionVarList sessionVarList = null;
    private SystemUserManager userManager = null;
    private SystemUserManager systemUserManager;
    private RequestDispatcher rd;
    private String url = "/oracleview.jsp";
    private List<ApplicationModuleBean> applicationList;
    private SystemAuditBean systemAuditBean;
    private List<UserPasswordBean> userPasswordBeanList;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {



//            System.out.println("username ==" + userName + "  password == " + password);

            if (request.getParameter("action") == null) {


                sessionVarList = new SessionVarList();
                String password="";
                String userName = request.getParameter("username");
                if (!"".equals(request.getParameter("password"))) {
                    password = CMSMd5.cmsMd5(request.getParameter("password"));
                }
                
                CMSSysUser = new SystemUserBean();
                CMSSysUser.setUserName(userName);
                CMSSysUser.setPassword(password);

                valideUser = validateUser(CMSSysUser);
                        
                if (valideUser != null) {


                    Date today = new Date();
                    //check expiryDate
                    if (today.before(valideUser.getExpiryDate())) {
                        //check activation
                        if (valideUser.getStatusCode().equalsIgnoreCase("Active")) {

                            CMSSessionUser = new SessionUser();

                            CMSSessionUser.setUserName(valideUser.getUserName());
                            CMSSessionUser.setUserRole(valideUser.getUserRoleCode());
                            CMSSessionUser.setLevelId(valideUser.getLevelId());
                            

                            this.getApplicationTypes(valideUser.getUserRoleCode());



                            if (applicationList != null) {

                                HttpSession sessionObj = request.getSession(true);


                                //System.out.println("new login-  " + sessionObj.getId());
                                sessionVarList.setCMSSessionUser(CMSSessionUser);
                                sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarList);
                                // RECSysUser.setCurrentSessionId(sessionObj.getId());
                                CMSSysUser.setCurrentSessionId(sessionObj.getId());


                                sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarList);
                                this.updateSysUser(CMSSysUser);
                                
                                
                                
                                try {
                                    userManager = new SystemUserManager();
                                    this.setAudittraceValue(request);
                                    userManager.insertAuditWhenLogin(systemAuditBean);

                                } catch (Exception ex) {
                                }
                                
                                validatePasswordPolicies(request,response,valideUser.getUserName(),valideUser.getPassword());
                                
                                PasswordPolicyBean passwdPolBean = PasswordPolicyManager.getPasswordPolicyManager().getPasswordPolicyDetails();
                                
                                //Long noOfDaysSinceCreated = DateUtil.daysBetween(new Date(), valideUser.getCreatedTime());
                                int noOfDaysSinceCreated=this.daysBetween(valideUser.getCreatedTime(), new Date());
                                
                                if (StatusVarList.IS_FIRST_LOGIN.equals(valideUser.getInitialloginstatus()) && noOfDaysSinceCreated <= passwdPolBean.getFirstPassExpiryPeriod()) {
                                    request.setAttribute("applicationLst", applicationList);
                                    rd = request.getRequestDispatcher("/ViewChangePasswordServlet");
                                }else if (StatusVarList.IS_FIRST_LOGIN.equals(valideUser.getInitialloginstatus()) && noOfDaysSinceCreated > passwdPolBean.getFirstPassExpiryPeriod()) {
                                    response.sendRedirect(request.getContextPath() + "/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.FIRST_LOGIN_PASS_EXPIRED);
                                }else{
                                    request.setAttribute("applicationLst", applicationList);
                                    rd = request.getRequestDispatcher("/LoadCmsHomePageservlet");
                                }
                                
                            }                            
                            

                        } else {
                            response.sendRedirect(request.getContextPath() + "/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LOGIN_INACTIVATED);
                        }
                    } else {
                        response.sendRedirect(request.getContextPath() + "/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LOGIN_EXPIRED);
                    }

                } else {
                    SystemUserBean inValideUser = getLoggedUserDetails(userName);
                    if (!validateInValidLoginAttempts(request, response, inValideUser, password)) {
                        response.sendRedirect(request.getContextPath() + "/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.INVALIDE_LOGIN);

                    }
                }

            } else {

                SessionUser sessionUser1 = new SessionUser();
                systemUserManager = new SystemUserManager();
                HttpSession sessionObj = request.getSession(false);
                sessionVarList = new SessionVarList();
                try {
                    sessionVarList = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                    sessionUser1 = sessionVarList.getCMSSessionUser();
                } catch (NullPointerException ex) {
                    //throw session null exception
                    throw new SesssionExpException();
                }

                try {

                    if (!systemUserManager.validateUserSession(sessionUser1.getUserName(), sessionObj.getId())) {
                        //useer not in same session.
                        throw new NewLoginSessionException();

                    }

                } catch (NewLoginSessionException nlex) {
                    //throw lst login close exception
                    throw new NewLoginSessionException();

                }



                this.getApplicationTypes(sessionUser1.getUserRole());
                request.setAttribute("applicationLst", applicationList);
                rd = request.getRequestDispatcher(url);

            }
        } catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);



        } catch (Exception ex) {
            // System.out.println(">>>>>>>>>>>>>>" + ex);
        } finally {
            rd.forward(request, response);
            out.close();
        }

    }

    private SystemUserBean validateUser(SystemUserBean CMSSysUser) throws Exception {
        SystemUserBean valideTempUser = null;
        userManager = new SystemUserManager();
        valideTempUser = userManager.validateUser(CMSSysUser);
        return valideTempUser;
    }
    
    private SystemUserBean getLoggedUserDetails(String userName) throws Exception {
        SystemUserBean valideTempUser = null;
        userManager = new SystemUserManager();
        valideTempUser = userManager.getLoggedUserDetails(CMSSysUser,userName);
        return valideTempUser;
    }

    //update systemuser sessionid
    private int updateSysUser(SystemUserBean user) throws Exception {
        int result = -1;
        try {

            userManager = new SystemUserManager();
            result = userManager.updateSysUserSessionId(user);
        } catch (Exception ex) {
            throw ex;
        }
        return result;
    }

    private void getApplicationTypes(String userRoleCode) throws Exception {
        userManager = new SystemUserManager();
        applicationList = userManager.getApplicationList(userRoleCode);

    }
    
    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarList.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarList.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Login to the system systemuser:" + sessionVarList.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.DEFAULTAPPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.DEFAULTSECTION);
            systemAuditBean.setPageCode(PageVarList.DEFAULTPAGE);
            systemAuditBean.setTaskCode(TaskVarList.LOGIN);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue("");


            systemAuditBean.setLastUpdateduser(sessionVarList.getCMSSessionUser().getUserName());

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
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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

    private void validatePasswordPolicies(HttpServletRequest request, HttpServletResponse response, String userName,String password) throws IOException ,Exception {
        String warnMsg="";
        String errorMsg="";
        boolean valid=true;
        userPasswordBeanList=userManager.getUserPasswordBeanList(userName,password); 
        Long noOfDaysSinceLastPw = DateUtil.daysBetween(userPasswordBeanList.get(0).getLastUpdatedTime(), new Date()); //userPasswordBeanList sorted desc.
        PasswordPolicyBean passwdPolBean = PasswordPolicyManager.getPasswordPolicyManager().getPasswordPolicyDetails();
        Long noOfDaysForExpirePw = Long.parseLong(passwdPolBean.getPasswordExpiryPeriod()) - noOfDaysSinceLastPw;
                
        //Validate Password Expirey Period.        
        if (noOfDaysForExpirePw >= 0) {
            if (noOfDaysForExpirePw < Long.parseLong(passwdPolBean.getPasswordExpNotifyPeriod())) {
                if (noOfDaysSinceLastPw == 0) {
                    warnMsg = MessageVarList.COMMON_WARN_CHANGE_PASSWORD + " today.";
                } else {
                    warnMsg = MessageVarList.COMMON_WARN_CHANGE_PASSWORD + " in " + noOfDaysForExpirePw + " days.";
                }
                request.setAttribute("userInitLoginMessage", warnMsg);
            }
        } else {
            valid=false;
            response.sendRedirect(request.getContextPath() + "/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LOGIN_PASSWORD_EXPIRED);
        }
        
        //Validate Idle Account Expiry Period.
//        if(valid){
//            Long noOfDaysSinceLastLogin = DateUtil.daysBetween(valideUser.getLastLoggedDateTime(), new Date());
//            if (noOfDaysSinceLastLogin < Long.parseLong(passwdPolBean.getAccountExpiryPeriod())) {
//                
//                userManager = new SystemUserManager();
//                valideUser.setStatusCode(StatusVarList.DEACTIVE_STATUS);
//                userManager.deActivateUser(valideUser,null,null);
//                response.sendRedirect(request.getContextPath() + "/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LOGIN_PASSWORD_FAILD_USER_DEACTIVATE);            
//            }  
//        }
    }
    
    
    public List<UserPasswordBean> getUserPasswordBeanList() {
        return userPasswordBeanList;
    }
    
    public int daysBetween(Date d1, Date d2){
             return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
     }

    public void setUserPasswordBeanList(List<UserPasswordBean> userPasswordBeanList) {
        this.userPasswordBeanList = userPasswordBeanList;
    }

    private boolean validateInValidLoginAttempts(HttpServletRequest request, HttpServletResponse response, SystemUserBean inValideUser, String password) throws IOException, Exception {
        String errorMsg="";
        PasswordPolicyBean passwdPolBean = PasswordPolicyManager.getPasswordPolicyManager().getPasswordPolicyDetails();
        boolean validate=false;
        if (inValideUser != null) {
            //Validate invalid login Attempts. 
            if (!password.equals(inValideUser.getPassword())) {
                inValideUser.setNoOfInvalidLoginAtt(inValideUser.getNoOfInvalidLoginAtt()+1);
                inValideUser.setStatusCode(null);
                if(inValideUser.getNoOfInvalidLoginAtt()>Long.parseLong(passwdPolBean.getNoOfInvalidLoginAtmps())){
                    inValideUser.setStatusCode(StatusVarList.DEACTIVE_STATUS);
                    errorMsg=MessageVarList.LOGIN_PASSWORD_FAILD_USER_DEACTIVATE;
                }else{
                    errorMsg=MessageVarList.LOGIN_PASSWORD_FAILD;
                }

                try {
                    userManager = new SystemUserManager();
                    userManager.updateUserInvalidLoginAttempts(inValideUser);
                    validate=true;
                    response.sendRedirect(request.getContextPath() + "/administrator/controlpanel/login/login.jsp?message=" + errorMsg);    

                } catch (Exception ex) {
                    validate=false;
                    throw ex;
                }

            }
        }else{
             validate=false;
             errorMsg=MessageVarList.INVALID_USERNAME;
             response.sendRedirect(request.getContextPath() + "/administrator/controlpanel/login/login.jsp?message=" + errorMsg);    
        }
        return validate;
    }
}
