/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardrequestconfirm.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequestconfirm.businesslogic.BulkCardConfirmManager;
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
public class ConfirmBulkCardServlet extends HttpServlet {

    private String url = "/prem/bulkcardmgt/bulkcardrequestconfirm.jsp";
    private HttpSession sessionObj;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private RequestDispatcher rd;
    private BulkCardRequestBean confirmBean;
    private String errorMessage;
    private SystemAuditBean systemAuditBean;
    private BulkCardConfirmManager bulkConfirmManager;

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


            try {
                //set page code and task codes
                String pageCode = PageVarList.BULK_CD_CONFIRM;
                String taskCode = TaskVarList.APPROVE;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    sessionVarlist.setUserPageTaskList(userTaskList);

                    this.setUserInputsToBean(request);

                    if (this.validateUserInput(confirmBean)) {
                        this.setAudittraceValue(request);

                        bulkConfirmManager = new BulkCardConfirmManager();
                        //to get approved user branch
                        String branch = bulkConfirmManager.getUserBranch(confirmBean.getApprvUser());
                        confirmBean.setApprvBranch(branch);

                        boolean success = bulkConfirmManager.updateBulkCard(confirmBean, systemAuditBean);
                        if (success) {
                            request.setAttribute("successMsg", "Batch ID: " + confirmBean.getBatchID() + " " + MessageVarList.BULK_CONFIRM_SUCCESS);
                            rd = getServletContext().getRequestDispatcher("/SearchBulkCardServlet?back=yes");
                        }


                    } else {
                        request.setAttribute("errorMsg", errorMessage);
                        request.setAttribute("confirmBean", confirmBean);
                        rd = getServletContext().getRequestDispatcher("/LoadBulkCardDetailsServlet");
                    }

                    // rd = getServletContext().getRequestDispatcher(url);

                } else {
                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();
                }

            } catch (AccessDeniedException adex) {
                throw adex;
            }

        }//catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);


        } catch (SQLException ex) {

            OracleMessage message = new OracleMessage();
            String oraMessage = message.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", oraMessage);
            rd = getServletContext().getRequestDispatcher(url);
//            rd.forward(request, response);

        } catch (Exception ex) {

            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_ASSIGN_APPLICATION);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
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

    private void setUserInputsToBean(HttpServletRequest request) throws Exception {

        try {
            confirmBean = new BulkCardRequestBean();

            confirmBean.setBatchID(request.getParameter("batchID"));
            confirmBean.setApprvNumOfCds(request.getParameter("apprvNumofCards"));
            //reqNumofCards
            confirmBean.setReqNumOfCds(request.getParameter("reqNumofCards"));
            confirmBean.setCdBin(request.getParameter("binProfile"));
            confirmBean.setTemplateCode(request.getParameter("cardtemplate"));
            if (request.getParameter("crditLimit") != null) {
                confirmBean.setCreditLimit(request.getParameter("crditLimit"));
            }
            confirmBean.setLastUpdatedUser(sessionUser.getUserName());
            confirmBean.setApprvUser(sessionUser.getUserName());

        } catch (Exception e) {
            throw e;
        }
    }

    public boolean validateUserInput(BulkCardRequestBean bean) throws Exception {
        boolean isValidate = true;

        try {

            if (bean.getApprvNumOfCds().equals("") || bean.getApprvNumOfCds().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BULK_APPR_NO_OF_CARDS;//"Enter Approved Number of Cards";
            } else if (!UserInputValidator.isNumeric(bean.getApprvNumOfCds())) {
                isValidate = false;
                errorMessage = MessageVarList.BULK_APPR_NO_OF_CARDS_VALID;//"Enter a Valid Number of Cards";
            } else if (Integer.parseInt(bean.getApprvNumOfCds()) > Integer.parseInt(bean.getReqNumOfCds())) {
                isValidate = false;
                errorMessage = MessageVarList.BULK_APPR_NO_OF_CARDS_HIGH;//"Number of Cards Exceeds the Requested Amount";
            } else if (bean.getCdBin() == null || bean.getCdBin().contentEquals("") || bean.getCdBin().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BULK_CARD_BIN_PROF;//"Enter a Card Bin Profile";
            } else if (bean.getTemplateCode() == null || bean.getTemplateCode().contentEquals("") || bean.getTemplateCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BULK_TEMPL_EMPTY;//"Enter a Template ";
            } else if (bean.getCreditLimit() != null && (!UserInputValidator.isDecimalNumeric(bean.getCreditLimit()) || !UserInputValidator.isNumeric(bean.getCreditLimit()))) {
                isValidate = false;
                errorMessage = MessageVarList.BULK_CREDIT_LIMIT_VALID;//"Enter a Valid Credit Limit";
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("batchID");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Confirm Bulk Card. Batch ID: " + uniqueId + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.PRE_PERSONAL);
            systemAuditBean.setSectionCode(SectionVarList.PRE_PERSONAL_CD);
            systemAuditBean.setPageCode(PageVarList.BULK_CD_CONFIRM);
            systemAuditBean.setTaskCode(TaskVarList.APPROVE);
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
