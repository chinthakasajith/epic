/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.distribution.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.cpmm.distribution.bean.BulkCardDistributionBean;
import com.epic.cms.cpmm.distribution.businesslogic.BulkCardandPinDistributionManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.businesslogic.BulkCardRequestManager;
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
import java.util.ArrayList;
import java.util.HashMap;
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
public class SearchBulkCardPinDistributionServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private String errorMessage = null;
    private ApplicationAssignManager appAssignManager;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> cardDomainList;
    private HashMap<String, String> branchList = null;
    private BulkCardRequestManager requestManager = null;
    private BulkCardDistributionBean bulkDistBean = null;
    private List<BulkCardDistributionBean> bulkDistList = null;
    private BulkCardandPinDistributionManager bulkManager = null;
    private String opType = null;
    private String url = "/cpmm/distribution/bulkcardandpindistributionhome.jsp";

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
                String pageCode = PageVarList.BULK_CARD_PIN_DISTRIBUTION;
                String taskCode = TaskVarList.SEARCH;



                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {

                        this.getAllApplicationDoamin();
                        this.getAllPriorityLevelList();
                        this.getBranchNames();

                        request.setAttribute("cardDomainList", cardDomainList);
                        request.setAttribute("priorityLevelList", priorityLevelList);
                        request.setAttribute("branchList", branchList);

                        opType = request.getParameter("opType");


                        if (opType.equals("card")) {

                            request.setAttribute("selectedtab", "0");

                        } else if (opType.equals("pin")) {

                            request.setAttribute("selectedtab", "1");

                        }
                        setUserInputToBean(request);

                        if (validateUserInput(bulkDistBean)) {


                            this.setAudittraceValue(request);

                            try {
                                bulkManager = new BulkCardandPinDistributionManager();
                                bulkDistList = new ArrayList<BulkCardDistributionBean>();
                                if (opType.equals("card")) {

                                    bulkDistList = bulkManager.searchCardDistributionDetails(bulkDistBean, systemAuditBean);
                                    request.setAttribute("bulkCardList", bulkDistList);
                                    request.setAttribute("bulkCardBean", bulkDistBean);


                                } else if (opType.equals("pin")) {

                                    bulkDistList = bulkManager.searchPinDistributionDetails(bulkDistBean, systemAuditBean);
                                    request.setAttribute("bulkPinList", bulkDistList);
                                    request.setAttribute("bulkPinBean", bulkDistBean);

                                }

                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("errorMsg", oraMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);

                            }

                        } else {

                            if (opType.equals("card")) {

                                request.setAttribute("bulkCardBean", bulkDistBean);

                            } else if (opType.equals("pin")) {

                                request.setAttribute("bulkPinBean", bulkDistBean);

                            }
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }



                    } catch (Exception e) {

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
            // request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            out.close();
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = false;
        try {

            bulkDistBean = new BulkCardDistributionBean();

            bulkDistBean.setBatchID(request.getParameter("batchID"));
            bulkDistBean.setCardDomain(request.getParameter("cardDomain"));
            bulkDistBean.setCardType(request.getParameter("cardType"));
            bulkDistBean.setCardProduct(request.getParameter("cardProduct"));
            bulkDistBean.setBranchCode(request.getParameter("branchCode"));
            bulkDistBean.setPriorityLvl(request.getParameter("priorityLvl"));
            bulkDistBean.setFromDate(request.getParameter("fromDate"));
            bulkDistBean.setToDate(request.getParameter("toDate"));
            bulkDistBean.setReqestedUser(request.getParameter("reqestedUser"));
            bulkDistBean.setCreatedTime(request.getParameter("createdTime"));


            success = true;

        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    public boolean validateUserInput(BulkCardDistributionBean bulkDistBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code

        try {
            if (!UserInputValidator.isAlphaNumeric(bulkDistBean.getBatchID())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!UserInputValidator.isAlphaNumeric(bulkDistBean.getReqestedUser())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

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
            String uniqueId = bulkDistBean.getBatchID();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Search " + bulkDistBean.getBatchID() + " like Batch ID " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CPMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.BULK_PERSONALIZATION);
            systemAuditBean.setPageCode(PageVarList.BULK_CARD_PIN_DISTRIBUTION);
            systemAuditBean.setTaskCode(TaskVarList.SEARCH);
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

    private void getAllPriorityLevelList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            priorityLevelList = appAssignManager.getAllPriorityLevels();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllApplicationDoamin() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            cardDomainList = appAssignManager.getAllApplicationDoamin();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getBranchNames() throws Exception {
        try {
            requestManager = new BulkCardRequestManager();
            branchList = requestManager.getBranchNames();
        } catch (Exception ex) {
            throw ex;
        }
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
