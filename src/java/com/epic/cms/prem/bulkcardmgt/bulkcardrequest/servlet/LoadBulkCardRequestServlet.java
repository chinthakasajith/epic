/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardrequest.servlet;

import com.epic.cms.admin.camm.numbergeneration.businesslogic.NumberGeneFormatBusinessLogic;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
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
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
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
public class LoadBulkCardRequestServlet extends HttpServlet {

    //initialize variables
    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private SystemUserManager sysUserMgr;
    private SessionVarList sessionVarlist;
    //------------------------------------
    private String allowModify = "";    
    private SystemUserBean sysUsrBean = null;
    private BulkCardRequestBean bulkCdReqbean;
    private HashMap<String, String> cardDomain;    
    private HashMap<String, String> cardType = null;
    private AccountTemplateManager accTempMgr = null;
    private List<BulkCardRequestBean> reqList = null;
    private BulkCardRequestManager bulkCdReqMgr = null;
    private HashMap<String, String> bnkBranches = null;
    private ApplicationAssignManager appAssignManager = null;
    private HashMap<String, String> priorityLevelList = null;
    private String url = "/prem/bulkcardmgt/bulkcardrequesthome.jsp";
    private NumberGeneFormatBusinessLogic numFrmtMgr;
    private HashMap<String , String> productModes;

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
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whether userrole has an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    //set the operation type
                    request.setAttribute("operationtype", "add");
                    request.setAttribute("allowModify", allowModify);
                    //get all card types
                    this.getAllCardType();
                    request.setAttribute("cardTypeList", cardType);
                    //get all card domains
                    this.getCardDomains();
                    request.setAttribute("cardDomainList", cardDomain);
                    //get all prority levels and set to session
                    this.getAllPriorityLevelList();
                    
                    //retrieve production modes
                    this.getProductionModes();
                    request.setAttribute("productModes", productModes);
                    
                    //get bank branches
                    this.getBranchNames();
                    request.setAttribute("bnkBranchList", bnkBranches);
                    this.getUserDetails(sessionUser.getUserName());
                    bulkCdReqbean = new BulkCardRequestBean();
                    bulkCdReqbean.setBranchCode(sysUsrBean.getBranchname());
                    
                    bulkCdReqMgr = new BulkCardRequestManager();
                    String branchDes = bulkCdReqMgr.getBranchDes(bulkCdReqbean.getBranchCode());
                    bulkCdReqbean.setBranchName(branchDes);                    
                    
                    request.setAttribute("bulkCdReqbean", bulkCdReqbean);     
                    this.getAllBulkCdReq(sysUsrBean);
                    request.setAttribute("bulkCdReqList", reqList);

                    sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

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
        } catch (NewLoginSessionException nlex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_BLK_CD_REQ);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * to check users access permissions for the required task and page
     * @param userrole
     * @param pagecode
     * @param task
     * @return
     * @throws Exception 
     */
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

    private void getAllCardType() throws Exception {
        try {
            accTempMgr = new AccountTemplateManager();
            cardType = accTempMgr.getAllCardType();
        } catch (SQLException ex) {
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

    public void getProductionModes() throws Exception {

        try {
            numFrmtMgr = new NumberGeneFormatBusinessLogic();
            productModes = numFrmtMgr.getProductionModes();
        } catch (SQLException ex) {
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
    
    private void getAllBulkCdReq(SystemUserBean sysBean) throws Exception {
        try {

            bulkCdReqMgr = new BulkCardRequestManager();
            reqList = bulkCdReqMgr.getAllBulkCdReq(sysBean);
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
