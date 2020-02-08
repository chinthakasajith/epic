/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.camm.numbergeneration.businesslogic.NumberGeneFormatBusinessLogic;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.accounttemplate.businesslogic.AccountTemplateManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.businesslogic.BulkCardRequestManager;
import com.epic.cms.reportexp.cardapplication.bean.EmbossEncodeCardBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationSummaryManager;
import com.epic.cms.reportexp.cardapplication.businesslogic.EmbossAndEncodeCardReportManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
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
public class ChangeCardProductsServlet extends HttpServlet {

    //initialize variables
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private SystemUserManager sysUserMgr;
    //
    String allowModify = "";
    List<BulkCardRequestBean> reqList = null;
    HashMap<String, String> cdProduct = null;
    private EmbossEncodeCardBean inputBean = null;
    private List<String> embossUser;
    private ApplicationSummaryManager appSumMgr;
    private EmbossAndEncodeCardReportManager emEnMgr;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> bnkBranches = null;
    private HashMap<String, String> cardDomainList = null;
    private String url = "/reportexp/cardapplication/embossencodecardreporthome.jsp";
    private String url1 = "/reportexp/cardapplication/cardstatusreporthome.jsp";

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
                String pageCode = PageVarList.EMBOSS_ENCODE_CD;
                String taskCode = TaskVarList.SEARCH;

                //check whethre userrole have an access for this page and task
                if (!this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();
                }

                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);

            } catch (AccessDeniedException adex) {
                throw adex;
            }

            String reportType = request.getParameter("report");
            
            //set the operation type
            this.getBranchNames();
            request.setAttribute("bnkBranchList", bnkBranches);


            this.getCardDomains();
            request.setAttribute("cardDomainList", cardDomainList);

            this.getCardTypes();
            request.setAttribute("cardTypeList", cardTypeList);

            this.getCardProducts(request.getParameter("type"), request.getParameter("domain"));
            request.setAttribute("cardProductList", cdProduct);

            this.getEmbossUser();
            request.setAttribute("embossUser", embossUser);

            this.setUserInputToBean(request);
            request.setAttribute("appBean", inputBean);


            this.getCardProducts(request.getParameter("type"), request.getParameter("domain"));
            request.setAttribute("cdProductList", cdProduct);

            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
            if(reportType.equals("ENCODE")){
            rd = getServletContext().getRequestDispatcher(url);
            }else if(reportType.equals("STATUS")){
            rd = getServletContext().getRequestDispatcher(url1);
            }
            

        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        }//catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_EM_EN_CD_REPORT);
            rd = request.getRequestDispatcher(url);

        } finally {
            //rd.forward(request, response);
            rd.include(request, response);
            out.close();
        }
    }

    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {
        boolean isValidTaskAccess = false;

        try {
            sysUserMgr = new SystemUserManager();

            //get all tasks for userrole for this page
            userTaskList = sysUserMgr.getTasksByPageCodeAndUserRole(userrole, pagecode);

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

    private void getBranchNames() {
        try {
            appSumMgr = new ApplicationSummaryManager();
            bnkBranches = appSumMgr.getBranchNames();
        } catch (Exception e) {
        }

    }

    private void getCardDomains() throws Exception {
        try {

            appSumMgr = new ApplicationSummaryManager();
            cardDomainList = appSumMgr.getCardDomains();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardTypes() throws Exception {
        try {

            emEnMgr = new EmbossAndEncodeCardReportManager();
            cardTypeList = emEnMgr.getCardTypes();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardProducts(String cdType, String cdDomain) throws Exception {
        try {

            emEnMgr = new EmbossAndEncodeCardReportManager();
            cdProduct = emEnMgr.getCardProducts(cdType, cdDomain);
        } catch (SQLException ex) {
            throw ex;
        }

    }

    private void getEmbossUser() throws Exception {
        try {

            emEnMgr = new EmbossAndEncodeCardReportManager();
            embossUser = emEnMgr.getEmbossUser();

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void setUserInputToBean(HttpServletRequest request) {

        inputBean = new EmbossEncodeCardBean();

        inputBean.setNic(request.getParameter("nic"));
        inputBean.setPassport(request.getParameter("passport"));
        inputBean.setLicence(request.getParameter("drivinglicense"));
        inputBean.setApplicationId(request.getParameter("appId"));
        inputBean.setBranchCode(request.getParameter("branch"));
        inputBean.setPriorityLevelCode(request.getParameter("priority"));
        inputBean.setDomainCode(request.getParameter("domain"));
        inputBean.setFromDate(request.getParameter("fromdate"));
        inputBean.setToDate(request.getParameter("todate"));
        inputBean.setUser(request.getParameter("user"));
        inputBean.setCardNo(request.getParameter("cardnumber"));
        inputBean.setTypeCode(request.getParameter("type"));
        inputBean.setProductCode(request.getParameter("product"));
        inputBean.setStatus(StatusVarList.YESSTATUS);
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
