package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.OnLineBaseDataBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.OnLineCountryCodeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.OnLineCurrencyCodeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.OnlineBaseManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
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
 * @author jeevan
 */
public class SaveOnlineBaseServlet extends HttpServlet {

    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;
    private SystemAuditBean systemAuditBeanInsert = null;
//    private SystemAuditBean systemAuditBeanInserData = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private List<String> userTaskList;
    private String url = "/administrator/controlpanel/systemconfigmgt/onlinebasehome.jsp";
    private RequestDispatcher rd;
    private OnlineBaseManager onlineManager;
    private List<OnLineCurrencyCodeBean> onlineCurrencyBeanList;
    private List<OnLineBaseDataBean> onlineBaseData;
    private List<OnLineCountryCodeBean> onLineCountryBeanList;
    private OnLineBaseDataBean onLineBaseDataBeans;
    private OnLineBaseDataBean beanForDB;
    private List<StatusBean> statusList = null;
    private String errorMessage = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessionObj = request.getSession(false);
        try {
            try {
//                request.setAttribute("operationtype", "add");
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }
                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();
                }

                try {

                    String pageCode = PageVarList.BASE_CONFIG;
                    String taskCode = TaskVarList.ADD;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    } else {
                        throw new AccessDeniedException();
                    }

                    sessionVarlist.setUserPageTaskList(userTaskList);

                } catch (AccessDeniedException adex) {
                    throw adex;
                }

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            ////////////////////////////

            this.getCurrenceDescription();
            request.setAttribute("onlineCurrencyBeanList", onlineCurrencyBeanList);

            this.getCountryName();
            request.setAttribute("onLineCountryBeanList", onLineCountryBeanList);

            this.getOnLineBaseData();
            request.setAttribute("baseData", onLineBaseDataBeans);

            this.setValuestToBean(request);

            if (this.validateUserInput(beanForDB)) {
                boolean status = false;

                if (this.isRecordAvailable()) {
                    this.setAuditTraceValue(request);

                    status = this.updateConfigData();
                    request.setAttribute("successMsg", MessageVarList.BANK_CURRENCY_UPDATE);

                } else {
                    this.insertAuditTraceValue(request);

                    status = this.saveConfigData();
                    request.setAttribute("successMsg", MessageVarList.BANK_CURRENCY_INSERT);
                }
                sessionVarlist.setStatusList(statusList);
                sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                rd = request.getRequestDispatcher("/LoadOnlineBaseServlet");
            } else {
                request.setAttribute("operationtype", "add");
                request.setAttribute("baseData", beanForDB);
                request.setAttribute("errorMsg", errorMessage);
//                request.setAttribute("baseData", onLineBaseDataBeans);


                rd = getServletContext().getRequestDispatcher(url);
            }

            //////////////////////////

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
        }
    }

    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {

        boolean isValidTaskAccess = false;

        try {
            systemUserManager = new SystemUserManager();
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

    private void setAuditTraceValue(HttpServletRequest request) throws Exception {
        try {

            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("currencyCode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
//            systemAuditBean.setDescription("Update Base Configuration " + uniqueId + " by  " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setDescription("Update Base Configuration by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.BASE_CONFIG);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue("");
            systemAuditBean.setNewValue("");

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void insertAuditTraceValue(HttpServletRequest request) throws Exception {
        try {

            systemAuditBeanInsert = new SystemAuditBean();
            String uniqueId = request.getParameter("currencyCode");

            systemAuditBeanInsert.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBeanInsert.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBeanInsert.setUniqueId(uniqueId);
//            systemAuditBean.setDescription("Update Base Configuration " + uniqueId + " by  " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBeanInsert.setDescription("Insert Base Configuration by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBeanInsert.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBeanInsert.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBeanInsert.setPageCode(PageVarList.BASE_CONFIG);
            systemAuditBeanInsert.setTaskCode(TaskVarList.ADD);
            systemAuditBeanInsert.setIp(request.getRemoteAddr());
            systemAuditBeanInsert.setRemarks(uniqueId);
            systemAuditBeanInsert.setFieldName("");
            systemAuditBeanInsert.setOldValue("");
            systemAuditBeanInsert.setNewValue("");

            systemAuditBeanInsert.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to take all bank details, call the method in BankMgtManager
     * @throws Exception 
     */
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

    private void getCurrenceDescription() throws Exception {
        try {
            onlineManager = new OnlineBaseManager();
            onlineCurrencyBeanList = onlineManager.getCurrenceDesc();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCountryName() throws Exception {
        try {
            onlineManager = new OnlineBaseManager();
            onLineCountryBeanList = onlineManager.getBaseCountry();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getOnLineBaseData() throws Exception {
        try {
            onlineManager = new OnlineBaseManager();
            onLineBaseDataBeans = onlineManager.getOnLineBaseData();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean saveConfigData() throws Exception {
        boolean status = false;
        try {
            onlineManager = new OnlineBaseManager();
            status = onlineManager.saveConfigData(systemAuditBeanInsert, beanForDB);
//            status = onlineManager.saveConfigData(beanForDB);

        } catch (Exception e) {
            throw e;
        }
        return status;
    }

    private boolean updateConfigData() throws Exception {
        boolean status = false;
        try {
            onlineManager = new OnlineBaseManager();
            status = onlineManager.updateConfigData(systemAuditBean, beanForDB);
        } catch (Exception e) {
            throw e;
        }
        return status;
    }

    boolean isRecordAvailable() throws Exception {
        Boolean status = false;
        try {
            onlineManager = new OnlineBaseManager();
            status = onlineManager.isRecordAvailable();

        } catch (Exception e) {
            throw e;
        }
        return status;
    }

    private void setValuestToBean(HttpServletRequest request) throws Exception {
        try {
            beanForDB = new OnLineBaseDataBean();

            beanForDB.setCurrencyCode(request.getParameter("currencyCode"));
            beanForDB.setCountryCode(request.getParameter("country"));
            beanForDB.setMerchantId(request.getParameter("merchant_id"));
            beanForDB.setTerminalId(request.getParameter("terminal_id"));
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean validateUserInput(OnLineBaseDataBean bean) throws Exception {
        boolean flag = true;

        try {

            if (bean.getCurrencyCode().contentEquals("") || bean.getCurrencyCode().length() <= 0) {
                errorMessage = MessageVarList.BASE_CONFIG_CURRENCY_EMPTY;
                flag = false;

            } else if (bean.getCountryCode().contentEquals("") || bean.getCountryCode().length() <= 0) {
                errorMessage = MessageVarList.BASE_CONFIG_COUNTRY_EMPTY;
                flag = false;

            } else if (bean.getTerminalId().contentEquals("") || bean.getTerminalId().length() <= 0) {
                errorMessage = MessageVarList.BASE_CONFIG_TERMINALID_EMPTY;
                flag = false;

            } else if (!UserInputValidator.isNumeric(bean.getTerminalId())) {
                errorMessage = MessageVarList.BASE_TERMINAL_ID_INVALID;
                flag = false;

            } else if (bean.getMerchantId().contentEquals("") || bean.getMerchantId().length() <= 0) {
                errorMessage = MessageVarList.BASE_CONFIG_MERCHANTID_EMPTY;
                flag = false;
                
            } else if (!UserInputValidator.isNumeric(bean.getMerchantId())) {
                errorMessage = MessageVarList.BASE_MERCHANT_ID_INVALID;
                flag = false;
            }

        } catch (Exception e) {
            flag = false;
            throw e;
        }
        return flag;
    }
}
