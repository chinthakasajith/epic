/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.FeeMgtManager;
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
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.SectionVarList;
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
public class UpdateFeeMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private String errorMessage = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private FeeBean feeBean = null;
    private FeeMgtManager feeManager = null;
    private List<FeeBean> feeList = null;
    private boolean successUpdate = false;
    private String url = "/administrator/controlpanel/transactionMgt/feemgthome.jsp";

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
                String pageCode = PageVarList.FEEMGT;
                String taskCode = TaskVarList.UPDATE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                    try {

                        /////////get Fee list for table view 
                        feeManager = new FeeMgtManager();
                        feeList = new ArrayList<FeeBean>();
                        feeList = feeManager.getFeeDetails();

                        if (setUserInputToBean(request)) {
                            if (validateUserInput(feeBean)) {
                                request.setAttribute("operationtype", "UPDATE");
                                this.setAudittraceValue(request);
                                successUpdate = feeUpdate(feeBean, systemAuditBean);

                                if (successUpdate) {

                                    request.setAttribute("successMsg", feeBean.getFeeCode() + " " + MessageVarList.FEE_SUCCESS_UPDATE);
                                }

                                rd = getServletContext().getRequestDispatcher("/LoadFeeMgtServlet");

                            } else {
                                this.setDefaultValue(feeBean);
                                request.setAttribute("operationtype", "UPDATE");
                                request.setAttribute(RequestVarList.FEEMGT_LIST, feeList);
                                request.setAttribute("feeBean", feeBean);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }
                        } else {
                            this.setDefaultValue(feeBean);
                            request.setAttribute("operationtype", "UPDATE");
                            request.setAttribute(RequestVarList.FEEMGT_LIST, feeList);
                            request.setAttribute("feeBean", feeBean);
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }

                        /////////get Fee list for table view 
                        feeManager = new FeeMgtManager();
                        feeList = new ArrayList<FeeBean>();
                        feeList = feeManager.getFeeDetails();



                        request.setAttribute(RequestVarList.FEEMGT_LIST, feeList);
                        //request.setAttribute("operationtype", "ADD");
                        //rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);

                    } catch (Exception e) {
                        this.setDefaultValue(feeBean);
                        request.setAttribute("operationtype", "UPDATE");
                        request.setAttribute(RequestVarList.FEEMGT_LIST, feeList);
                        request.setAttribute("feeBean", feeBean);
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

    /**
     * 
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = true;
        try {

            feeBean = new FeeBean();

            feeBean.setFeeCode(request.getParameter("feeCode").trim());
            feeBean.setFeeDes(request.getParameter("feeDes").trim());
            feeBean.setFeeCategory(request.getParameter("feeCategory").trim());
            feeBean.setFeeType(request.getParameter("feeType").trim());
            feeBean.setCurrency(request.getParameter("selectCurrency").trim());
            feeBean.setCrordr(request.getParameter("crordr").trim());
            feeBean.setFlatFee(request.getParameter("flatFee").trim());
            feeBean.setPercentage(request.getParameter("percentage").trim());
            feeBean.setOption(request.getParameter("option").trim());
            feeBean.setMinAmount(request.getParameter("minAmount").trim());
            feeBean.setMaxAmount(request.getParameter("maxAmount").trim());
            feeBean.setStatus(request.getParameter("selectStatusCode").trim());
            feeBean.setOldValue(request.getParameter("oldValue").trim());

            feeBean.setLastUpdateUser(sessionUser.getUserName());


        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    /**
     * 
     * @param txnType
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(FeeBean feeBean) throws Exception {
        boolean isValidate = true;

        //validate user Role code
        try {
            if (feeBean.getFeeCode().contentEquals("") || feeBean.getFeeCode().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.FEE_CODE_NULL;

            } else if (!UserInputValidator.isAlphaNumeric(feeBean.getFeeCode())) {
                isValidate = false;

                errorMessage = MessageVarList.FEE_CODE_INVALID;
//            } else if (feeBean.getFeeDes().length() <= 0) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.FEE_DESCRIPTION_NULL;
//
//            } else if (!UserInputValidator.isDescription(feeBean.getFeeDes())) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.FEE_DESCRIPTION_INVALID;

            } else if (feeBean.getFeeCategory().contentEquals("") || feeBean.getFeeCategory().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.FEE_CATEGORY_NULL;

            } else if (feeBean.getFeeType().contentEquals("") || feeBean.getFeeType().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.FEE_TYPE_NULL;

            } else if (feeBean.getCurrency().contentEquals("") || feeBean.getCurrency().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.CURRENCY_NULL;

            } else if (feeBean.getCrordr().contentEquals("") || feeBean.getCrordr().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.CRORDR_NULL;

            } else if ((feeBean.getFlatFee().contentEquals("") || feeBean.getFlatFee().length() <= 0) && (feeBean.getPercentage().contentEquals("") || feeBean.getPercentage().length() <= 0)) {
                isValidate = false;

                errorMessage = MessageVarList.FLAT_FEE_PERCENTAGE_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(feeBean.getFlatFee(),"8","2")) {
                isValidate = false;

                errorMessage = MessageVarList.FLAT_FEE_INVALID;

//            } else if (feeBean.getPercentage().contentEquals("") || feeBean.getPercentage().length() <= 0) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.PERCENTAGE_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(feeBean.getPercentage(),"3","2")) {
                isValidate = false;

                errorMessage = MessageVarList.PERCENTAGE_INVALID;

            } else if ((Double.parseDouble(feeBean.getPercentage())) > 100) {

                isValidate = false;
                errorMessage = MessageVarList.PERCENTAGE_VALUE_INVALID;

            } else if (feeBean.getOption().contentEquals("") || feeBean.getOption().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.OPTION_NULL;

            } else if (feeBean.getMinAmount().contentEquals("") || feeBean.getMinAmount().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MIN_AMOUNT_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(feeBean.getMinAmount(),"8","2")) {
                isValidate = false;

                errorMessage = MessageVarList.MIN_AMOUNT_INVALID;

            } else if (feeBean.getMaxAmount().contentEquals("") || feeBean.getMaxAmount().length() <= 0) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_AMOUNT_NULL;

            } else if (!UserInputValidator.isDecimalOrNumeric(feeBean.getMaxAmount(),"8","2")) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_AMOUNT_INVALID;

            } else if (Double.parseDouble(feeBean.getMaxAmount()) < Double.parseDouble(feeBean.getMinAmount())) {
                isValidate = false;

                errorMessage = MessageVarList.MAX_AMOUNT_GRATER_MIN_AMOUNT;

            } else if (feeBean.getStatus().contentEquals("") || feeBean.getStatus().length() <= 0) {

                isValidate = false;

                errorMessage = MessageVarList.STATUS_NULL;

            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    /**
     * 
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(feeBean.getFeeCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update: '" + feeBean.getFeeCode() + "' Fee By : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
            systemAuditBean.setPageCode(PageVarList.FEEMGT);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(feeBean.getOldValue());
            //set new value of change if required
            systemAuditBean.setNewValue(this.setNewUpdateValue(feeBean));


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }

    }

    private String setNewUpdateValue(FeeBean newBean) throws Exception {
        String newValue = null;
        try {

            newValue = newBean.getFeeCode() + "|" + newBean.getFeeDes() + "|" + newBean.getFeeCategory() + "|"
                    + newBean.getFeeType() + "|" + newBean.getCurrency() + "|" + newBean.getCrordr() + "|"
                    + newBean.getFlatFee() + "|" + newBean.getPercentage() + "|" + newBean.getMinAmount() + "|"
                    + newBean.getMaxAmount() + "|" + newBean.getStatus();


        } catch (Exception ex) {
            throw ex;

        }
        return newValue;
    }

    /**
     * 
     * @param txnType
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean feeUpdate(FeeBean feeBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            feeManager = new FeeMgtManager();
            success = feeManager.feeUpdate(feeBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    public boolean setDefaultValue(FeeBean feeBean) throws Exception {
        boolean success = true;
        try {

            ////////set Default value to flat fee and percentage////////////

            if (feeBean.getFlatFee().isEmpty()) {
                feeBean.setFlatFee("0.0");

            }
            if (feeBean.getPercentage().isEmpty()) {
                feeBean.setPercentage("0.0");
            }

        } catch (Exception ex) {
            success = false;
            throw ex;
        }
        return success;
    }

    /**
     * 
     * @param userrole
     * @param pagecode
     * @param task
     * @return
     * @throws Exception 
     */
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
