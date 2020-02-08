/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardrequest.servlet;

import com.epic.cms.admin.camm.numbergeneration.businesslogic.NumberGeneFormatBusinessLogic;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.templatemgt.accounttemplate.businesslogic.AccountTemplateManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
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
 * @author nisansala
 */
public class AddBulkCardRequestServlet extends HttpServlet {

    //initialize variables
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private SystemUserManager sysUserMgr;
    private SystemAuditBean systemAuditBean = null;
    //
    String allowModify = "";
    String errorMessage = "";
    List<BulkCardRequestBean> reqList = null;
    List<CurrencyBean> currency = null;
    HashMap<String, String> cdProduct = null;
    private boolean successInsert;
    private SystemUserBean sysUsrBean = null;
    private BulkCardRequestBean bulkCdReqbean;
    private HashMap<String, String> cardDomain;
    private HashMap<String, String> bnkBranches = null;
    private HashMap<String, String> cardType = null;
    private AccountTemplateManager accTempMgr = null;
    private BulkCardRequestManager bulkCdReqMgr = null;
    private HashMap<String, String> productModes = null;
    private ApplicationAssignManager appAssignManager = null;
    private HashMap<String, String> priorityLevelList = null;
    private NumberGeneFormatBusinessLogic numFrmtMgr = null;
    String url = "/prem/bulkcardmgt/bulkcardrequesthome.jsp";
    private String branch2 = "";

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
            HttpSession sessionObj = request.getSession(false);
            try {
                sysUserMgr = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();
                //check system user is in same session or not
                try {
                    if (!sysUserMgr.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
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
                String pageCode = PageVarList.BULK_CD_REQ;
                String taskCode = TaskVarList.ADD;

                //check whether userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        request.setAttribute("operationtype", "add");
                        this.getUserDetails(sessionUser.getUserName());
                        //assign user input to the bean
                        setUserInputToBean(request);
                        //get all card types
                        this.getAllCardType();
                        //get all card domains
                        this.getCardDomains();
                        //get all prority levels
                        this.getAllPriorityLevelList();
                        //get bank branches
                        this.getBranchNames();
                        //get all bulk card requests
                        this.getAllBulkCdReq(sysUsrBean);

                        this.getCardProducts(request.getParameter("type"), request.getParameter("domain"));
                        // this.getProductionModes(request.getParameter("currency"), request.getParameter("product"), request.getParameter("type"));

                        //retrieve production modes
                        this.getProductionModes();
                        //request.setAttribute("productModes", productModes);                        


                        request.setAttribute("bulkCdReqbean", bulkCdReqbean);
                        request.setAttribute("allowModify", allowModify);
                        request.setAttribute("cardTypeList", cardType);
                        request.setAttribute("cardDomainList", cardDomain);
                        request.setAttribute("productModes", productModes);
                        request.setAttribute("bnkBranchList", bnkBranches);
                        request.setAttribute("bulkCdReqList", reqList);
                        request.setAttribute("cdProductList", cdProduct);

                        if (request.getParameter("product") != null) {
                            this.getCurrency(request.getParameter("product"));
                            request.setAttribute("currencyList", currency);
                        }

                        branch2 = request.getParameter("branchDes");
                                                
                        //validate user inputs
                        if (validateUserInput(bulkCdReqbean, branch2)) {

                            this.setAudittraceValue(request);

                            try {
                                //insert the user given values to the MCC table
                                successInsert = insertNewBulkCdReq(bulkCdReqbean);
                                if (successInsert) {
                                    request.setAttribute("successMsg", MessageVarList.SECURITY_QUES_MGT_SUCCESS_ADD + " Batch ID");
                                    rd = getServletContext().getRequestDispatcher("/LoadBulkCardRequestServlet");
                                    rd.include(request, response);
                                }

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("errorMsg", oraMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);

                            }
                        } else {
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);

                        }
                    } catch (Exception e) {
                        request.setAttribute("operationtype", "add");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }
                } else {
                    throw new AccessDeniedException();
                }
                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);
            } catch (AccessDeniedException adex) {
                //if user_role doesn't have required access to the page throw Access Denied exception
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
        }
    }

    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {
        boolean isValidTaskAccess = false;

        try {
            sysUserMgr = new SystemUserManager();
            allowModify = "NO";
            //get all tasks for userrole for this page
            userTaskList = sysUserMgr.getTasksByPageCodeAndUserRole(userrole, pagecode);

            for (String usertask : userTaskList) {

                if (task.equals(usertask)) {
                    isValidTaskAccess = true;
                }
                if (usertask.equals("BRMD")) {
                    allowModify = "YES";
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
            String uniqueId = request.getParameter("1");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("");
            systemAuditBean.setApplicationCode(ApplicationVarList.PRE_PERSONAL);
            systemAuditBean.setSectionCode(SectionVarList.PRE_PERSONAL_CD);
            systemAuditBean.setPageCode(PageVarList.BULK_CD_REQ);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
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

    public boolean validateUserInput(BulkCardRequestBean bulkReq, String branch) throws Exception {
        boolean isValidate = true;
        try {
            if (bulkReq.getCdDomain().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.CD_DOMAIN_NULL;
            } else if (bulkReq.getCdType().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.CD_TYPE_NULL;
            } else if (bulkReq.getCdProduct().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.CD_PRODUCT_NULL;
            } else if (bulkReq.getCurrency().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.CD_CURRENCY_NULL;
            } else if (branch.equals("") || branch.length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BRANCH_NULL;
            } else if (bulkReq.getBranchCode().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.BRANCH_NULL;
            } else if (bulkReq.getPriorityLvl().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.PRIO_LVL_NULL;
            } else if (bulkReq.getProductMode().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.PROD_MODE_NULL;
            } else if (bulkReq.getReqNumOfCds().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.NUM_CD_NULL;
            } else if (!UserInputValidator.isNumeric(bulkReq.getReqNumOfCds())) {
                isValidate = false;
                errorMessage = MessageVarList.NUM_CD_INVALID;
            } else if (bulkReq.getReqNumOfCds().length() > 4 && Integer.parseInt(bulkReq.getReqNumOfCds()) > 1000) {
                isValidate = false;
                errorMessage = MessageVarList.MAX_CDS;
            } else if (Integer.parseInt(bulkReq.getReqNumOfCds()) < 1) {
                isValidate = false;
                errorMessage = MessageVarList.MIN_CDS;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            bulkCdReqbean = new BulkCardRequestBean();

            bulkCdReqbean.setCdDomain(request.getParameter("domain").trim());
            bulkCdReqbean.setCdType(request.getParameter("type").trim());
            bulkCdReqbean.setCdProduct(request.getParameter("product").trim());
            bulkCdReqbean.setCurrency(request.getParameter("currency").trim());
//            if (allowModify.equals("NO")) {
//                bulkCdReqbean.setBranchCode(sysUsrBean.getBranchname());
//            } else {
//                bulkCdReqbean.setBranchCode(request.getParameter("branch").trim());
//            }
            bulkCdReqbean.setBranchCode(request.getParameter("bname").trim());
            bulkCdReqbean.setPriorityLvl(request.getParameter("prioritycode").trim());
            bulkCdReqbean.setProductMode(request.getParameter("productionMode").trim());
            bulkCdReqbean.setReqNumOfCds(request.getParameter("reqCards").trim());
            bulkCdReqbean.setReqUser(sessionUser.getUserName());
            bulkCdReqbean.setLastUpdatedUser(sessionUser.getUserName());
//            if (allowModify.equals("NO")) {
//                bulkCdReqbean.setReqBranch(sysUsrBean.getBranchname());
//            } else {
//                bulkCdReqbean.setReqBranch(request.getParameter("branch").trim());
//            }
            bulkCdReqbean.setReqBranch(request.getParameter("bname").trim());
            bulkCdReqbean.setStatus("BCRP");

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    public Boolean insertNewBulkCdReq(BulkCardRequestBean bean) throws Exception {
        boolean success = false;
        try {
            bulkCdReqMgr = new BulkCardRequestManager();
            success = bulkCdReqMgr.insertNewBulkCdReq(bean, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }

    private void getAllCardType() throws Exception {
        try {
            accTempMgr = new AccountTemplateManager();
            cardType = accTempMgr.getAllCardType();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    private void getCardDomains() throws Exception {
        try {

            bulkCdReqMgr = new BulkCardRequestManager();
            cardDomain = bulkCdReqMgr.getCardDomains();
        } catch (SQLException ex) {
            throw ex;
        }

    }

    private void getAllPriorityLevelList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            priorityLevelList = appAssignManager.getAllPriorityLevels();
            sessionVarlist.setPriorityLevelList(priorityLevelList);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getBranchNames() throws Exception {
        try {

            bulkCdReqMgr = new BulkCardRequestManager();
            bnkBranches = bulkCdReqMgr.getBranchNames();
        } catch (SQLException ex) {
            throw ex;
        }

    }

    private void getUserDetails(String username) throws Exception {
        try {

            bulkCdReqMgr = new BulkCardRequestManager();
            sysUsrBean = bulkCdReqMgr.getUserDetails(username);
        } catch (SQLException ex) {
            throw ex;
        }

    }

    private void getCurrency(String cdProduct) throws Exception {
        try {

            bulkCdReqMgr = new BulkCardRequestManager();
            currency = bulkCdReqMgr.getCurrency(cdProduct);
        } catch (SQLException ex) {
            throw ex;
        }

    }

    private void getCardProducts(String cdType, String cdDomain) throws Exception {
        try {

            bulkCdReqMgr = new BulkCardRequestManager();
            cdProduct = bulkCdReqMgr.getCardProducts(cdType, cdDomain);
        } catch (SQLException ex) {
            throw ex;
        }

    }

    private void getAllBulkCdReq(SystemUserBean sysUsrBean) throws Exception {
        try {

            bulkCdReqMgr = new BulkCardRequestManager();
            reqList = bulkCdReqMgr.getAllBulkCdReq(sysUsrBean);
        } catch (SQLException ex) {
            throw ex;
        }

    }

    public void getProductionModes(String currency, String product, String type) throws Exception {

        try {
            bulkCdReqMgr = new BulkCardRequestManager();
            productModes = bulkCdReqMgr.getProductionMode(currency, product, type);
        } catch (SQLException ex) {
            throw ex;
        }

    }

    public void getProductionModes() throws Exception {

        try {
            numFrmtMgr = new NumberGeneFormatBusinessLogic();
            productModes = numFrmtMgr.getProductionModes();
        } catch (SQLException ex) {
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
