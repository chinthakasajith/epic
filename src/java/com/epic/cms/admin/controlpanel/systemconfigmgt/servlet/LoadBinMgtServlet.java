/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AquireBinFormBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardDomainsBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ProductionModeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.AquireBinManagementManager;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.BankMgtManager;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardBinMgtManager;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardProductMgtManager;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.StandingOrderTypesManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CurrencyMgtManager;
import com.epic.cms.application.common.bean.StatusBean;
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
 * @author asitha_l
 */
public class LoadBinMgtServlet extends HttpServlet {
    
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CardProductMgtManager cardProductMgtManager;
    private CardBinMgtManager cardBinMgtManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private HashMap<String, String> cardTypeList = null;
    private List<CardBinBean> cardBinBeanLst = null;
    private String url = "/administrator/controlpanel/systemconfigmgt/binmanagementhome.jsp";
    private BankMgtManager bankMgtManager;
    private List<StatusBean> statusList;
    private List<CardDomainsBean> cardDomainsList;//cardDomainsList
    private AquireBinManagementManager AquireBinManager;
    private List<AquireBinFormBean> ChannelList;
    private HashMap<String, String> currencyMap;

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
                String pageCode = PageVarList.BIN_MGT;
                String taskCode = TaskVarList.ACCESSPAGE;


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


            request.setAttribute("operationtype", "add");
            
            this.getAllCardTypeList();
            this.getCardDomainsList();
            this.getCurrency();
            this.getStatus();
            this.getChannelList();
            this.getAllBinDetailsList();

            sessionVarlist.setCardDomainsList(cardDomainsList);
            sessionVarlist.setCurrencyMap(currencyMap);
            sessionVarlist.setStatusList(statusList);
            sessionVarlist.setAquireChannelList(ChannelList);

            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);


            request.setAttribute("cardTypeList", cardTypeList);
            request.setAttribute("cardBinBeanLst", cardBinBeanLst);


            rd = getServletContext().getRequestDispatcher(url);



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
            rd.forward(request, response);


        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_CARD_PRODUCT);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void getAllCardTypeList() throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            cardTypeList = cardProductMgtManager.getAllCardTypeList();

        } catch (Exception ex) {
            throw ex;
        }
    }    

    //card domains
    private void getCardDomainsList() throws Exception {
        try {

            cardBinMgtManager = new CardBinMgtManager();
            cardDomainsList = cardBinMgtManager.getCardDomainsList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCurrency() throws Exception {
        try {

            cardBinMgtManager = new CardBinMgtManager();
            currencyMap = new HashMap<String, String>();
            currencyMap = cardBinMgtManager.getCurrency();

        } catch (Exception ex) {
            throw ex;
        }
    }
    

    //status
    /**
     * to get list of general status (active and de active)
     * @throws Exception 
     */
    private void getStatus() throws Exception {
        try {

            bankMgtManager = new BankMgtManager();
            statusList = bankMgtManager.getStatusList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllBinDetailsList() throws Exception {
        try {

            cardBinMgtManager = new CardBinMgtManager();
            cardBinBeanLst = cardBinMgtManager.getAllBinDetailsList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to get channel list
     * @throws Exception 
     */
    private void getChannelList() throws Exception {
        try {

            AquireBinManager = new AquireBinManagementManager();
            ChannelList = AquireBinManager.getChannelList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    ///////////////////////////
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
