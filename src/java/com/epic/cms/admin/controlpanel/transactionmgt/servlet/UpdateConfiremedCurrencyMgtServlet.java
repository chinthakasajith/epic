/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CurrencyMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
public class UpdateConfiremedCurrencyMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private CurrencyMgtManager currencyManager;
    private List<CurrencyBean> currencyBeanList;
    private String errorMessage = null;
    //private CurrencyBean currency;
    private CurrencyBean currencyBean;
    private SystemAuditBean systemAuditBean = null;
    private boolean successInsert = false;
    private List<String> userTaskList;
    private String url = "/administrator/controlpanel/transactionMgt/currencycodemgt.jsp";
    private String newValue = null;
    private String oldValue = null;

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
                String pageCode = PageVarList.CURRENCY;
                String taskCode = TaskVarList.UPDATE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    oldValue = request.getParameter("oldvalue");

                    try {
                        if (setUserInputToBean(request)) {
                            if (validateUserInput(currencyBean)) {
                                request.setAttribute("operationtype", "default");
                                this.setAudittraceValue(request, oldValue, newValue);
                                successInsert = updateCurrency(currencyBean, systemAuditBean);

                                if (successInsert) {
                                    request.setAttribute("successMsg", MessageVarList.CURRENCY_SUCCESS_UPDATE+" code: "+currencyBean.getCurrencyAlphaCode());
                                    rd = getServletContext().getRequestDispatcher("/LoadCurrencyMgtServlet");
                                }

                            } else {
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("currency", currencyBean);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }
                        } else {
                            request.setAttribute("operationtype", "default");
                            request.setAttribute("currencyBean", currencyBean);
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }
                        rd.forward(request, response);
                    } catch (Exception e) {
                        request.setAttribute("operationtype", "default");
                        request.setAttribute("currencyBean", currencyBean);
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

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {
        } finally {
            out.close();
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            currencyBean = new CurrencyBean();

            String currencyNumCode = request.getParameter("currencyCode").trim();
            String currencyAlphaCode = request.getParameter("currencyCodeAlpha").trim();
            String exponent = request.getParameter("exponent").trim();
            String description = request.getParameter("description").trim();
            String stat = request.getParameter("statuscode").trim();

            currencyBean.setCurrencyCode(currencyNumCode);
            currencyBean.setCurrencyAlphaCode(currencyAlphaCode);
            currencyBean.setExponent(exponent);
            currencyBean.setCurrencyDes(description);
            currencyBean.setLastUpdatedUser(sessionUser.getUserName());
            currencyBean.setStatus(stat);

            newValue = currencyNumCode + "|" + currencyAlphaCode + "|" + exponent + "|" + description + "|" + stat;

        } catch (Exception e) {
            status = false;
            throw e;

        }

        return status;
    }

    public boolean validateUserInput(CurrencyBean currency) throws Exception {
        boolean isValidate = true;

        //validate user Role code
        try {
            if (currency.getCurrencyCode().contentEquals("") || currency.getCurrencyCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.CURRENCY_CURRENCYCODE_NULL;
            } else if (!UserInputValidator.isNumeric(currency.getCurrencyCode())) {
                isValidate = false;
                errorMessage = MessageVarList.CURRENCY_CURRENCYCODE_INVALID;
            } else if (currency.getCurrencyAlphaCode().contentEquals("") || currency.getCurrencyAlphaCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CURRENCY_CURRENCYALPHA_NULL;

            } else if (!UserInputValidator.isAlpha(currency.getCurrencyAlphaCode())) {
                isValidate = false;
                errorMessage = MessageVarList.CURRENCY_ALPHA_INVALID;
            } else if (currency.getExponent().contentEquals("") || currency.getExponent().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CURRENCY_EXPONENT_NULL;
            } else if (!UserInputValidator.isNumeric(currency.getExponent())) {
                isValidate = false;
                errorMessage = MessageVarList.CURRENCY_EXPONENT_INVALID;

            } else if (currency.getCurrencyDes().contentEquals("") || currency.getCurrencyDes().length() <= 0) {

                isValidate = false;
                errorMessage = MessageVarList.CURRENCY_Description_NULL;
            } else if (!UserInputValidator.isDescription(currency.getCurrencyDes())) {
                isValidate = false;
                errorMessage = MessageVarList.CURRENCY_DES_INVALID;

            } else if (currency.getStatus().equals("") || currency.getStatus().length() <= 0) {

                isValidate = false;
                errorMessage = MessageVarList.CURRENCY_STATUS_NULL;
            }


        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    private void setAudittraceValue(HttpServletRequest request, String oldV, String newV) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(currencyBean.getCurrencyCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Currency. Currency Code: " + currencyBean.getCurrencyDes() + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
            systemAuditBean.setPageCode(PageVarList.CURRENCY);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(oldV);
            //set new value of change if required
            systemAuditBean.setNewValue(newV);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }

    }

    public boolean updateCurrency(CurrencyBean currency, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            currencyManager = new CurrencyMgtManager();
            success = currencyManager.updateCurrency(currency, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
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
