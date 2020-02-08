package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfirmation.bean.RejectReasonBean;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationPinGenAndMailBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationPinGenAndMailManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
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
 * @author jeevan
 */
public class SearchApplicationPinGenAndMailServlet extends HttpServlet {

    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String errorMessage = null;
    private SessionVarList sessionVarlist;
    private SystemUserManager systemUserManager;
    //----------------------------------------------------
    private List<RejectReasonBean> rejectReasons;
    private ApplicationPinGenAndMailBean inputBean;
    private ApplicationPinGenAndMailManager appPinMgr;
    private ApplicationPinGenAndMailManager pinManager;
    private HashMap<String, String> bnkBranches = null;
    private HashMap<String, String> bnkCardNo = null; //
    private HashMap<String, String> fromdate = null; //
    private HashMap<String, String> todate = null; //
    private HashMap<String, String> appStatusList = null;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> cardDomainList = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> cardPinMethodList = null; //
    private HashMap<String, String> cardProductList = null;
    private List<ApplicationPinGenAndMailBean> searchList = null;
    private List<CardProductBean> cardProduct = null;
    private ApplicationAssignManager appAssignManager;
    private List<String> usersList = null;
    private String url = "/reportexp/cardapplication/applicationpingenandmailhome.jsp";

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
                String pageCode = PageVarList.APPLICATION_REJECT;
                String taskCode = TaskVarList.SEARCH;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    this.setUserInputToBean(request);
                    request.setAttribute("appBean", inputBean);

                    this.getCardPinMethod();
                    request.setAttribute("bnkCardPinMethodList", cardPinMethodList);

                    this.getBranchNames();
                    request.setAttribute("bnkBranchList", bnkBranches);

                    this.getPriorityLevels();

                    this.getCardDomains();
                    request.setAttribute("cardDomainList", cardDomainList);

                    this.getCardType();
                    request.setAttribute("cardTypeList", cardTypeList);

                    this.getCardPinMethod();
                    request.setAttribute("bnkCardPinMethodList", cardPinMethodList);

//                    this.getCardProduct(inputBean.getCardType());
//                    request.setAttribute("bulkCProductList", cardProductList);
                    this.getCardProductList();
                    request.setAttribute("bulkCProductList", cardProduct);
                    
                    this.getAllUserList();
                    request.setAttribute("usersList", usersList);

                    if (isValidate(inputBean)) {

                        this.getPinGenAndMailDetails(inputBean);
                    } else {
                        request.setAttribute("errorMsg", errorMessage);
                    }
                    request.setAttribute("appBean", inputBean);

                    request.setAttribute("searchList", searchList);
                    sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                    rd = getServletContext().getRequestDispatcher(url);

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

        } catch (Exception ex) {
            request.setAttribute("operationtype", "search");
            request.setAttribute("errorMsg", MessageVarList.ERROR_APP_REJECT);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * check valid access to the page to search
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

    /**
     * assign user given values to the bean for searching
     *
     * @param request
     */
    public void setUserInputToBean(HttpServletRequest request) {

        inputBean = new ApplicationPinGenAndMailBean();

        inputBean.setUser(request.getParameter("generateduser"));
        inputBean.setNic(request.getParameter("nic"));
        inputBean.setPassport(request.getParameter("passport"));
        //inputBean.setLicence(request.getParameter("licence"));
        inputBean.setCardType(request.getParameter("card_ty"));
        inputBean.setCardProduct(request.getParameter("cardProduct"));
        inputBean.setCardNo(request.getParameter("cardNo"));
//        inputBean.setRejReason(request.getParameter("rejectReason"));
        inputBean.setBranchCode(request.getParameter("branch"));
        inputBean.setPinMethod(request.getParameter("card_pin"));
        inputBean.setPriorityLevelCode(request.getParameter("priority"));
        inputBean.setDomainCode(request.getParameter("domain"));
        inputBean.setFromDate(request.getParameter("fromdate"));
        inputBean.setToDate(request.getParameter("todate"));

        //***********************************************************************************************************************************
        //sessionVarlist.setTermBean(terminalBean);
        //sessionVarlist.setSearchBean(terminalBean);
        //***********************************************************************************************************************************
    }
//

    private void getPinGenAndMailDetails(ApplicationPinGenAndMailBean inputBean) throws SQLException, Exception {
        pinManager = new ApplicationPinGenAndMailManager();
        searchList = pinManager.getPinGenAndMailDetails(inputBean);
        //sessionVarlist.setTerminalDataBeanList(searchList);
    }

    private void getBranchNames() throws Exception {
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();
            bnkBranches = appPinMgr.getBranchNames();
        } catch (Exception ex) {
            throw ex;
        }

    }

    private void getCardDomains() throws Exception {
        try {

            appPinMgr = new ApplicationPinGenAndMailManager();
            cardDomainList = appPinMgr.getCardDomains();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardType() throws Exception {
        try {

            appPinMgr = new ApplicationPinGenAndMailManager();
            cardTypeList = appPinMgr.getCardType();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardPinMethod() throws Exception {
        try {

            appPinMgr = new ApplicationPinGenAndMailManager();
            cardPinMethodList = appPinMgr.getCardPinMethod();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardProduct(String cardType) throws Exception {
        try {

            appPinMgr = new ApplicationPinGenAndMailManager();
            cardProductList = appPinMgr.getCardProduct(cardType);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getPriorityLevels() throws Exception {
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();
            priorityLevelList = appPinMgr.getPriorityLevel();
            sessionVarlist.setPriorityLevelList(priorityLevelList);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getFromDate() throws Exception {
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();
            fromdate = appPinMgr.getCardNo();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getToDate() throws Exception {
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();
            todate = appPinMgr.getCardNo();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardNo() throws Exception {
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();
            bnkCardNo = appPinMgr.getCardNo();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardProductList() throws Exception {
        cardProduct = new ArrayList<CardProductBean>();
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();

            cardProduct = appPinMgr.getCardProductList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllUserList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            usersList = appAssignManager.getAllUserList(PageVarList.PIN_GEN_AND_MAIL);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean isValidate(ApplicationPinGenAndMailBean inputBean) throws Exception {

        boolean isValidate = true;

        if (!inputBean.getNic().equals("")) {
            if (!UserInputValidator.isAlphaNumeric(inputBean.getNic())) {
                isValidate = false;
                errorMessage = MessageVarList.INVALID_NIC;
            }
        }
        if (!inputBean.getPassport().equals("")) {
            if (!UserInputValidator.isAlphaNumeric(inputBean.getPassport())) {
                isValidate = false;
                errorMessage = MessageVarList.ERROR_PAS_NUMBER;
            }
        }
//        if (!inputBean.getLicence().equals("")) {
//            if (!UserInputValidator.isAlphaNumeric(inputBean.getLicence())) {
//                isValidate = false;
//                errorMessage = MessageVarList.ERROR_DRL_NUMBER;
//            }
//        }
        if (!inputBean.getCardNo().equals("")) {
            if (!UserInputValidator.isNumeric(inputBean.getCardNo())) {
                isValidate = false;
                errorMessage = MessageVarList.CARDNUMBER_INVALID;
            }
        }

        return isValidate;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
