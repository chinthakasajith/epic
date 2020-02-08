/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.generalledgermgtmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.AssignTxnBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.ChannelLisnrTypeBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.GeneralLedgerMgtBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.TxnTypesBean;
import com.epic.cms.backoffice.generalledgermgtmgt.businesslogic.AssignTxnTypesToGLAccountManager;
import com.epic.cms.backoffice.generalledgermgtmgt.businesslogic.GeneralLedgerMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
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
public class AddTxnTypesToGLAccount extends HttpServlet {

    private HttpSession sessionObj;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private AssignTxnTypesToGLAccountManager assignmanager;
    private GeneralLedgerMgtManager glmanager;
    private List<GeneralLedgerMgtBean> allGLAccList;
    private String url = "/backoffice/generalledgermgt/assigntxntoglaccount.jsp";
    private RequestDispatcher rd;
    private List<String> userTaskList;
    private String[] assignedList;
    private AssignTxnBean assBean;
    private String errorMessage;
    private SystemAuditBean systemAuditBean;
    private List<AssignTxnBean> allOnlineGLAccList;
    private List<TxnTypesBean> txnTypeslist;
    private List<TxnTypesBean> assinedtxnTypeslist;
    private List<ChannelLisnrTypeBean> chanelLisnrTypeList;

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
            sessionObj = request.getSession(false);

            try {
                request.setAttribute("operationtype", "add");

                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                //check system user is in the same session or not
                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        //user not in same session.
                        throw new NewLoginSessionException();

                    }

                } catch (NewLoginSessionException nlex) {
                    //throw lst login close exception
                    throw new NewLoginSessionException();

                }
                try {
                    //set page code and task codes
                    String pageCode = PageVarList.ASSIGN_TXN_TO_GL;
                    String taskCode = TaskVarList.ADD;


                    //check whether userrole can access this page and task
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        try {
                            assignmanager = new AssignTxnTypesToGLAccountManager();
                            glmanager = new GeneralLedgerMgtManager();

                            allGLAccList = glmanager.getAllGlAccounts();
                            request.setAttribute("accList", allGLAccList);



                            this.setValuesToBean(request);
                            assignedList = request.getParameterValues("assignlist");

                            if(assBean.getChanekOrLisnr() != null){
                            chanelLisnrTypeList = assignmanager.getChanelLisnrTypeList(assBean.getChanekOrLisnr());
                            }
                            request.setAttribute("chlsList", chanelLisnrTypeList);

                            if(assBean.getChOrLsType() != null){
                                txnTypeslist = assignmanager.getTxnTypes(assBean.getChanekOrLisnr(), assBean.getChOrLsType(),assBean.getGlAccNo(),assBean.getDrGlAccNo());
                            }                            
                            request.setAttribute("txnTypeslist", txnTypeslist);

                            if(assBean.getChanekOrLisnr() != null && assBean.getChOrLsType() != null){
                            assinedtxnTypeslist = assignmanager.getassinedTxnTypes(assBean.getChanekOrLisnr(), assBean.getChOrLsType(),assBean.getGlAccNo(),assBean.getDrGlAccNo());
                            }
                            request.setAttribute("assTxnTypeslist", assinedtxnTypeslist);

                            int success = -1;

                            if (this.validateUserInput(assBean, assignedList)) {

                                this.setAudittraceValue(request);

                                try {
                                    success = assignmanager.assignTxns(systemAuditBean, assBean, assignedList);

                                } catch (SQLException e) {

                                    OracleMessage message = new OracleMessage();
                                    String oraMessage = message.getMessege(e.getMessage());
                                    request.setAttribute("errorMsg", oraMessage);
                                    rd = getServletContext().getRequestDispatcher(url);
                                    rd.forward(request, response);

                                } catch (Exception ex) {
                                    throw ex;
                                }

                                if (success > 0) {

//                                allOnlineGLAccList = assignmanager.getOnlineGlAccounts();
//                                request.setAttribute("allOnlineGLAccList", allOnlineGLAccList);

                                    request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.GL_TXNS_SUCCESS);
                                    //rd = getServletContext().getRequestDispatcher("/LoadAssignTxnTypesToGLAccount");
                                    txnTypeslist = null;
                                    request.setAttribute("txnTypeslist", txnTypeslist);
                                    assinedtxnTypeslist = null;
                                    request.setAttribute("assTxnTypeslist", assinedtxnTypeslist);

                                } else {
                                    request.setAttribute("assignBean", assBean);
                                    request.setAttribute(MessageVarList.JSP_ERROR, "Transaction Types are not Changed");
                                }

                            } else {
                                request.setAttribute("assignBean", assBean);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }


                            allOnlineGLAccList = assignmanager.getOnlineGlAccounts();
                            request.setAttribute("allOnlineGLAccList", allOnlineGLAccList);

                            rd = getServletContext().getRequestDispatcher(url);
                            //rd = getServletContext().getRequestDispatcher("/LoadAssignTxnTypesToGLAccount");

                        } catch (Exception e) {
                            request.setAttribute("operationtype", "add");
                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            rd = getServletContext().getRequestDispatcher(url);

                        }


                    } else {

                        //if invalid throw access denied exception
                        throw new AccessDeniedException();

                    }

                    //set tasks to the session
                    sessionVarlist.setUserPageTaskList(userTaskList);


                } catch (AccessDeniedException adex) {
                    throw adex;
                }
            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }

            //------------------------------------------------------------------------------------------------------------//
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);


        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
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

    private void setValuesToBean(HttpServletRequest request) throws Exception {
        try {
            assBean = new AssignTxnBean();//assignBean

            assBean.setGlAccNo(request.getParameter("glacc"));//credit
            assBean.setDrGlAccNo(request.getParameter("drglacc"));//debit
            assBean.setChanekOrLisnr(request.getParameter("chanOrLisn"));
            assBean.setChOrLsType(request.getParameter("cltype"));

        } catch (Exception e) {
            throw e;
        }
    }

    public boolean validateUserInput(AssignTxnBean bean, String[] assigned) throws Exception {
        boolean isValidate = true;

        try {

            if (bean.getGlAccNo().contentEquals("") || bean.getGlAccNo().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.GL_ACC_EMPTY;//Select a GL Account
            }else if(bean.getDrGlAccNo().contentEquals("") || bean.getDrGlAccNo().length()<=0){
                isValidate=false;
                errorMessage=MessageVarList.DR_GL_ACC_EMPTY;
            }else if (bean.getChanekOrLisnr() == null) {
                isValidate = false;
                errorMessage = MessageVarList.GL_STAT_EMPTY;// Select Listner or Chanel Status
            } else if (bean.getChOrLsType().contentEquals("") || bean.getChOrLsType().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.GL_CHLS_TYPE_EMPTY;// Select a Channel or Listner Type
            } else if (assigned == null || assigned.length <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.GL_TXNS_EMPTY;//Select Transactions
            }


        } catch (Exception ex) {
            isValidate = false;
        }
        return isValidate;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("glacc");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Assign transactions to GL Account. GL Account Code: " + uniqueId + "; by:  " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.GL_LEDGER_MGT);
            systemAuditBean.setPageCode(PageVarList.ASSIGN_TXN_TO_GL);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
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
