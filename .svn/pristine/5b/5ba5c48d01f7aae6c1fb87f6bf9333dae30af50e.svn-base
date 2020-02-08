/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BillingCycleMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.BillingCycleMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
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
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * to update a billing data
 * @author nisansala
 */
public class UpdateBillingCycleMgtServlet extends HttpServlet {
    //initializing variables

    String oldValue = "";
    String newValue = "";
    HttpSession sessionObj = null;
    private RequestDispatcher rd;
    private List<String> userTaskList;
    private SessionUser sessionUser = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    //------------------------------------------------
    private String errorMessage = null;
    private BillingCycleMgtBean billBean;
    private boolean successUpdate = false;
    private BillingCycleMgtManager billCycleMgr;
    private List<BillingCycleMgtBean> billingList;
    String url = "/administrator/controlpanel/systemconfigmgt/billingcyclemgt.jsp";

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
                String pageCode = PageVarList.BILLING_CYCLE_MGT;
                String taskCode = TaskVarList.UPDATE;


                //check whether user_role have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    billCycleMgr = new BillingCycleMgtManager();

                    try {
                        if (setUserInputToBean(request)) {

                            //validate user input
                            if (validateUserInput(billBean)) {
                                request.setAttribute("operationtype", "add");
                                // request.setAttribute("mrchntCatList", mrchntCatList);
                                //set values to the audit trace
                                this.setAudittraceValue(request);
                                
                                int count = -1;
                                
                                if(billBean.getBillStatus().equals(StatusVarList.DEACTIVE_STATUS)){
                                    count = billCycleMgr.accountCount(billBean.getBillCycleCode());
                                    if(count > 0){
                                        request.setAttribute("billBean", billBean);
                                        request.setAttribute("errorMsg", "Can't be deactivated, active accounts are there with the billing cycle");
                                        rd = getServletContext().getRequestDispatcher(url);
                                    }
                                
                                } 
                                if(count <= 0){
                                    successUpdate = updateBillCycle(billBean, systemAuditBean);
                                    request.setAttribute("successMsg", " " + MessageVarList.BILL_CYCLE_MGT_BILLING_CODE_SUCCESS_UPDATE + "Billing Cycle Code " + billBean.getBillCycleCode());
                                    rd = getServletContext().getRequestDispatcher(url);
                                }
                                
                            } else {
                                request.setAttribute("operationtype", "update");
                                request.setAttribute("billBean", billBean);
                                //request.setAttribute("mrchntCatList", mrchntCatList);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }
                        } else {
                            request.setAttribute("operationtype", "update");
                            //request.setAttribute("mrchntCatList", mrchntCatList);
                            request.setAttribute("billBean", billBean);
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                        }
                        this.getAllBillingData();
                        request.setAttribute("billingList", billingList);
                        rd.forward(request, response);
                    } catch (Exception e) {
                        request.setAttribute("operationtype", "update");
                        request.setAttribute("billBean", billBean);
                        request.setAttribute("billingList", billingList);
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        this.getAllBillingData();
                        request.setAttribute("billingList", billingList);
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

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(billBean.getBillCycleCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Billing Cycle. Billing Cycle code: '" + billBean.getBillCycleCode() + "' by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.BILLING_CYCLE_MGT);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
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

    private void getAllBillingData() throws Exception {
        try {

            billCycleMgr = new BillingCycleMgtManager();
            billingList = billCycleMgr.getAllBillingData();

        } catch (Exception ex) {
            throw ex;
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            billBean = new BillingCycleMgtBean();

            String billCycleCode = request.getParameter("billCycleCode").trim();
            String billDate = request.getParameter("billDate").trim();
            String billDes = request.getParameter("description");
            String holAct = request.getParameter("holidayAct");
            String stat = request.getParameter("status");

            billBean.setBillCycleCode(billCycleCode);
            billBean.setBillDate(billDate);
            billBean.setBillDescription(billDes);
            billBean.setHolidayAction(holAct);
            billBean.setBillStatus(stat);

            billBean.setNextbillingDate(request.getParameter("nextbillingDate"));

            billBean.setLastUpdatedUser(sessionUser.getUserName());

            oldValue = request.getParameter("oldValue");
            newValue = billBean.getBillCycleCode() + "|" + billBean.getBillDate() + "|"
                    + "" + billBean.getBillDescription() + "|" + billBean.getHolidayAction() + "|" + "|"
                    + billBean.getNextbillingDate() + "" + billBean.getBillStatus();

        } catch (Exception e) {
            status = false;
            throw e;

        }

        return status;
    }

    public boolean validateUserInput(BillingCycleMgtBean bill) throws Exception {
        boolean isValidate = true;


        try {
            if (bill.getBillCycleCode().contentEquals("") || bill.getBillCycleCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BILL_CYCLE_MGT_BILLCODE_NULL;
            } else if (bill.getBillDate().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.BILL_CYCLE_MGT_BILLDATE_NULL;
            } else if (bill.getBillDescription().contentEquals("") || bill.getBillDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BILL_CYCLE_MGT_BILLDESCRIPTION_NULL;
            } else if (bill.getHolidayAction() == null) {
                isValidate = false;
                errorMessage = MessageVarList.BILL_CYCLE_MGT_HOLIACT_NULL;
            } else if (bill.getBillStatus().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.BILL_CYCLE_MGT_STATUS_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public Boolean updateBillCycle(BillingCycleMgtBean bill, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            billCycleMgr = new BillingCycleMgtManager();
            success = billCycleMgr.updateBillCycle(bill, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
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
