/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CountryMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.PostalCodeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CurrencyMgtManager;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.PostalCodeManager;
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
 * @author mahesh_m
 */
public class AddPostalCodeServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private PostalCodeManager postalCodeManager;
    private List<CurrencyBean> currencyBeanList;
    private String errorMessage = null;
    private PostalCodeBean postalCodeBean;
    private boolean successInsert = false;
    private List<String> userTaskList;
    private List<CountryMgtBean> countryCodeList;
    private SystemAuditBean systemAuditBean = null;
    private String url = "/administrator/controlpanel/transactionMgt/Postalcodesmgt.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //call to existing session
            /////////////////////////////////////////////////////////////////////
            HttpSession sessionObj = request.getSession(false);
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
            /////////////////////////////////////////////////////////////////////

   
            try {
                //set page code and task codes
                String pageCode = PageVarList.POSTALCODE;
                String taskCode = TaskVarList.ADD;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        this.getCountryCodeList();
                        setUserInputToBean(request);

                        if (validateUserInput(postalCodeBean)) {
                            request.setAttribute("operationtype", "default");
                            
                            this.setAudittraceValue(request);

                            try {
                                successInsert = inserPostalCode(postalCodeBean, systemAuditBean);
                                request.setAttribute("successMsg", MessageVarList.POSTAL_SUCCESS_ADD);
                                rd = getServletContext().getRequestDispatcher("/LoadPostalCodeMgtServlet");

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());

                                request.setAttribute("operationtype", "default");
                                request.setAttribute("countryCodeList", countryCodeList);
                                request.setAttribute("errorMsg", oraMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);

                            }

                        } else {
                            request.setAttribute("operationtype", "default");
                            request.setAttribute("countryCodeList", countryCodeList);
                            request.setAttribute("postalCodeBean", postalCodeBean);
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);

                        }




                        rd.forward(request, response);

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "default");
                        request.setAttribute("countryCodeList", countryCodeList);
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }


                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }


                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                throw adex;

            }


           



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

        }catch(AccessDeniedException adex){
             //redirect to login page
             rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
             rd.forward(request, response);
            
        } catch (Exception ex) {
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            out.close();
        }
    }

    public void setUserInputToBean(HttpServletRequest request) throws Exception {

        try {

            postalCodeBean = new PostalCodeBean();

            String countryCode = request.getParameter("countryCode").trim();
            String city = request.getParameter("city").trim();
            String postalCode = request.getParameter("postalCode").trim();


            postalCodeBean.setCountryCode(countryCode);
            postalCodeBean.setCity(city);
            postalCodeBean.setPostalCode(postalCode);
            postalCodeBean.setLastUpdateduser(sessionUser.getUserName());

        } catch (Exception e) {
            throw e;

        }

    }

    public boolean validateUserInput(PostalCodeBean postal) throws Exception {
        boolean isValidate = true;

        //validate user Role code
        try {

            if (postal.getPostalCode().contentEquals("") || postal.getPostalCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.POSTAL_NULL_POSTALCODE;

            } else if (!UserInputValidator.isNumeric(postal.getPostalCode())) {
                isValidate = false;
                errorMessage = MessageVarList.POSTAL_INVALID_POSTALCODE;
            }else if (postal.getCountryCode().contentEquals("") || postal.getCountryCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.POSTAL_INVALID_COUNTRYCODE;
            }else if (postal.getCity().contentEquals("") || postal.getCity().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.POSTAL_INVALID_CITY;
            } else if (!UserInputValidator.isAlphaNumeric(postal.getCity())) {
                isValidate = false;
                errorMessage = MessageVarList.POSTAL_INVAL_CITY;
            }


        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public boolean inserPostalCode(PostalCodeBean postal,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            postalCodeManager = new PostalCodeManager();
            success = postalCodeManager.inserPostalCode(postal,systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(postalCodeBean.getPostalCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add " + postalCodeBean.getPostalCode() + " Postal Code by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
            systemAuditBean.setPageCode(PageVarList.POSTALCODE);
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
    
      private boolean isValidAccess(String userrole,String pagecode,String task) throws Exception{
       boolean isValidTaskAccess=false;
        
        try{
          systemUserManager=new SystemUserManager();
         
          //get all tasks for userrole for this page
          userTaskList=  systemUserManager.getTasksByPageCodeAndUserRole(userrole, pagecode);
            
          for(String usertask:userTaskList){
              
              if(task.equals(usertask)){
                 isValidTaskAccess=true; 
              }
              
              
          }
          
           
          return isValidTaskAccess;
        }
        catch(Exception ex){
            throw  ex;
        }
        
    }
      
      private void getCountryCodeList() throws Exception{
         postalCodeManager = new PostalCodeManager();
         countryCodeList = postalCodeManager.getCountryCodeList();
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
