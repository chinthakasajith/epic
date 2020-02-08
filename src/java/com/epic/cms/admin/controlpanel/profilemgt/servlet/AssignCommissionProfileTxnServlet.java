/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.CommissionProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.CommissionProfileManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nalin
 */
public class AssignCommissionProfileTxnServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private String errorMessage = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private CommissionProfileManager comisManager = null;
    private List<CommissionProfileBean> comisList = null;
    private List<CommissionProfileBean> comisTxnList = null;
    private CommissionProfileBean comisBean = null;
    private String url = "/administrator/controlpanel/profilemgt/commissionprofilehome.jsp";

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
        try {
            //call to existing session
            /////////////////////////////////////////////////////////////////////
            sessionObj = request.getSession(false);
            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                //check system user is in same session or not
                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        //user not in same session.
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
                String pageCode = PageVarList.COMMISSION_PROFILE;
                String taskCode = TaskVarList.ADD;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    String opType = request.getParameter("opType");
                    if (opType.equals("ADD")) {
                        request.setAttribute("operationtype", "ADD");
                    } else {
                        request.setAttribute("operationtype", "UPDATE");
                    }
                    try {

                        //---------get Commission Profile Details for table view
                        comisManager = new CommissionProfileManager();
                        comisList = new ArrayList<CommissionProfileBean>();

                        comisList = comisManager.getCommissionProfileDetails();
                        request.setAttribute(RequestVarList.COMMISSSION_PROFILE_LIST, comisList);

                        setUserInputToBean(request);
                        if (validateUserInput(comisBean)) {

                            if (opType.equals("ADD")) {
                                this.getCommissionTxnList();
                                sessionVarlist.setCommissionBean(comisBean);
                                this.setValueToTxnList(comisBean);
                                sessionVarlist.setCommissionTxnList(comisTxnList);
                            }
                            if (opType.equals("UPDATE")) {
                                this.getAssignedCommissionTxnList(comisBean.getComProfileCode());
                                this.getNotAssignedCommissionTxnList(comisBean.getComProfileCode(), comisTxnList);
                                sessionVarlist.setCommissionTxnList(comisTxnList);

                            }

                            request.setAttribute("comisBean", comisBean);

                        } else {


                            request.setAttribute("errorMsg", errorMessage);
                            request.setAttribute("comisBean", comisBean);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }

                    } catch (Exception e) {

                        request.setAttribute("comisBean", comisBean);
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }

                    //---------get Commission Profile Details for table view
                    comisManager = new CommissionProfileManager();
                    comisList = new ArrayList<CommissionProfileBean>();

                    comisList = comisManager.getCommissionProfileDetails();
                    request.setAttribute(RequestVarList.COMMISSSION_PROFILE_LIST, comisList);
                    rd = getServletContext().getRequestDispatcher(url);

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
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = true;
        try {

            comisBean = new CommissionProfileBean();



            comisBean.setComProfileCode(request.getParameter("comProfileCode").trim());
            comisBean.setDescription(request.getParameter("description").trim());
            comisBean.setCurrencyCode(request.getParameter("currencyCode").trim());
            comisBean.setStatus(request.getParameter("status").trim());
            comisBean.setCrOrdr(request.getParameter("crOrdr"));
            comisBean.setFlatAmount(request.getParameter("flatAmount").trim());
            comisBean.setPercentage(request.getParameter("percentage").trim());
            comisBean.setCombination(request.getParameter("combination").trim());

        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    public boolean validateUserInput(CommissionProfileBean comisBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code

        try {

            if (comisBean.getComProfileCode().contentEquals("") || comisBean.getComProfileCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.COMMISSION_PROFILE_CODE_NULL;

            } else if (!UserInputValidator.isAlphaNumeric(comisBean.getComProfileCode())) {
                isValidate = false;

                errorMessage = MessageVarList.COMMISSION_PROFILE_CODE_INVALID;

            } else if (comisBean.getDescription().contentEquals("") || comisBean.getDescription().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.DESCRIPTION_NULL;

            } else if (!UserInputValidator.isDescription(comisBean.getDescription())) {
                isValidate = false;

                errorMessage = MessageVarList.DESCRIPTION_INVALID;

            } else if (comisBean.getCurrencyCode().contentEquals("") || comisBean.getCurrencyCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.CURRENCY_NULL;

            } else if (comisBean.getCrOrdr().contentEquals("") || comisBean.getCrOrdr().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.CR_OR_DR_NULL;

            } else if (comisBean.getFlatAmount().contentEquals("") || comisBean.getFlatAmount().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.FLAT_AMOUNT_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(comisBean.getFlatAmount(), "10", "2")) {
                isValidate = false;

                errorMessage = MessageVarList.FLAT_AMOUNT_INVALID;

            } else if (comisBean.getPercentage().contentEquals("") || comisBean.getPercentage().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.PERCENTAGE_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(comisBean.getPercentage(), "3", "2")) {
                isValidate = false;

                errorMessage = MessageVarList.PERCENTAGE_INVALID;
                
            } else if (Double.parseDouble(comisBean.getPercentage()) > 100) {
                isValidate = false;

                errorMessage = MessageVarList.COMMISSION_PERCENTAGE_INVALID;

            } else if (comisBean.getCombination().contentEquals("") || comisBean.getCombination().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.COMBINATION_EMPTY;

            } else if (comisBean.getStatus().contentEquals("") || comisBean.getStatus().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.STATUS_NULL;


            }


        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    private void getCommissionTxnList() throws Exception {

        try {
            comisManager = new CommissionProfileManager();
            comisTxnList = new ArrayList<CommissionProfileBean>();

            comisTxnList = comisManager.getCommissionTxnList();

        } catch (Exception ex) {
            throw ex;
        }


    }

    private void getAssignedCommissionTxnList(String ComisProfileCode) throws Exception {

        try {
            comisManager = new CommissionProfileManager();
            comisTxnList = new ArrayList<CommissionProfileBean>();

            comisTxnList = comisManager.getAssignedCommissionTxnList(ComisProfileCode);

        } catch (Exception ex) {
            throw ex;
        }


    }

    private void getNotAssignedCommissionTxnList(String ComisProfileCode, List<CommissionProfileBean> comTxnList) throws Exception {

        try {
            comisManager = new CommissionProfileManager();

            comisTxnList = comisManager.getNotAssignedCommissionTxnList(ComisProfileCode, comTxnList);

        } catch (Exception ex) {
            throw ex;
        }


    }

    private void setValueToTxnList(CommissionProfileBean comisBean) throws Exception {

        try {
            for (int k = 0; k < comisTxnList.size(); k++) {

                comisTxnList.get(k).setComProfileCode(comisBean.getComProfileCode());
                comisTxnList.get(k).setDescription(comisBean.getDescription());
                comisTxnList.get(k).setCurrencyCode(comisBean.getCurrencyCode());
                comisTxnList.get(k).setCrOrdr(comisBean.getCrOrdr());
                comisTxnList.get(k).setFlatAmount(comisBean.getFlatAmount());
                comisTxnList.get(k).setPercentage(comisBean.getPercentage());
                comisTxnList.get(k).setCombination(comisBean.getCombination());
            }
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
