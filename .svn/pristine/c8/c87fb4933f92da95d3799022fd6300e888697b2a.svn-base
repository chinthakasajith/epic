package com.epic.cms.callcenter.callcentersearch.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.callcenter.callcentersearch.bean.CallHistoryBean;
import com.epic.cms.callcenter.callcentersearch.bean.CustomerSearchBean;
import com.epic.cms.callcenter.callcentersearch.bean.TransactionBean;
import com.epic.cms.callcenter.callcentersearch.bean.ViewDataBean;
import com.epic.cms.callcenter.callcentersearch.businesslogic.CallCenterMgtManager;
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
 * @author badrika
 */
public class SearchAdvancedServlet extends HttpServlet {

    //private FilledDataBean filledBean = null;
    private CustomerSearchBean adsearchbean = null;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private RequestDispatcher rd;
    private String url = "/callcenter/callcentersearch/advancedsearch.jsp";
    private String errorMessage;
    private SystemAuditBean systemAuditBean;
    private CallCenterMgtManager callCenterMgtManager;
    private ViewDataBean viewBean;
    private List<ViewDataBean> appList, cusList, accList, cardList;//viewlist,
    private List<TransactionBean> onlinetxnList, backendtxnList;
    private List<String> userTaskList;
    private CallHistoryBean callhistoryBean;
    private List<CustomerSearchBean> advancedList;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
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

            //request.setAttribute("operationtype", "search");
            HttpSession sessionObj = request.getSession(false);
            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();
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
            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.SEARCH)) {
                throw new AccessDeniedException();
            }

            ///////////

            String pageCode = PageVarList.CALLCENTERSEARCH;
            String taskCode = TaskVarList.ACCESSPAGE;

            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                //audit trace and call history for verify user
                this.setAudittraceValue(request);

                callCenterMgtManager = new CallCenterMgtManager();

                adsearchbean = new CustomerSearchBean();
                
                adsearchbean.setAdsearchtype(request.getParameter("adsearchtype")); 
                adsearchbean.setAdsearchval(request.getParameter("adsearchval")); 
                
//                searchbean = sessionVarlist.getCustomerSearchBean();
                request.setAttribute("adsearchbean", adsearchbean);

                if (this.validateUserInput(adsearchbean)) {

                    this.searchCustomer();

                    request.setAttribute("operationtype", "search");
                    request.setAttribute("advancedList", advancedList);
                    

                    sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                } else {
                    request.setAttribute("errorMsg", errorMessage);
                    rd = getServletContext().getRequestDispatcher("/LoadCallCenterMgtServlet");
                }

                rd = request.getRequestDispatcher(url);

            } else {
                //if invalid throw accessdenied exception
                throw new AccessDeniedException();
            }

            ///////////////


        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadCallCenterMgtServlet");
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    public boolean validateUserInput(CustomerSearchBean bean) throws Exception {
        boolean isValidate = true;

        try {

            if (bean.getAdsearchval().equals("") ) {
                isValidate = false;
                errorMessage = MessageVarList.AD_FIELD_EMPTY;
            } 
            
//            else if (bean.getCardnumber().length() > 0 && !UserInputValidator.isAlphaNumeric(bean.getCardnumber())) {
//                isValidate = false;
//                errorMessage = "Enter valid card number";
//            } else if (bean.getAccount().length() > 0 && !UserInputValidator.isAlphaNumeric(bean.getAccount())) {
//                isValidate = false;
//                errorMessage = "Enter valid account number";
//            } else if (!bean.getNic().equals("") && !UserInputValidator.checkNIC(bean.getNic())) {
//                isValidate = false;
//                errorMessage = "Enter valid NIC";
//            } else if (!bean.getPassport().equals("") && !UserInputValidator.isAlphaNumeric(bean.getPassport())) {
//                isValidate = false;
//                errorMessage = "Enter Valid Passport Number";
//            } else if (!bean.getCif().equals("") && !UserInputValidator.isAlphaNumeric(bean.getCif())) {
//                isValidate = false;
//                errorMessage = "Enter valid CIF";
//            }
        } catch (Exception ex) {
            isValidate = false;
        }
        return isValidate;
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

    private boolean isValidTaskByUser(List<String> userTaskList, String task) throws Exception {
        boolean isValidTask = false;
        try {
            for (String usertask : userTaskList) {
                if (task.equals(usertask)) {
                    isValidTask = true;
                }
            }
            return isValidTask;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void searchCustomer() throws Exception {
        try {

            callCenterMgtManager = new CallCenterMgtManager();
            advancedList = callCenterMgtManager.searchAdvanced(adsearchbean);


        } catch (Exception e) {
            throw e;
        }
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = sessionVarlist.getCallLogId();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Verify caller, call log id: '" + uniqueId + "' by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CALL_CENTER_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CALL_CENTER_CARD);
            systemAuditBean.setPageCode(PageVarList.CALLCENTERSEARCH);
            systemAuditBean.setTaskCode(TaskVarList.VERIFY);
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

   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

