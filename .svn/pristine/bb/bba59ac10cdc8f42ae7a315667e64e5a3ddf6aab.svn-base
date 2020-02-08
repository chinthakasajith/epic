/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.ActivatedCardReportBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ActivatedCardReportManager;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author asela
 */
public class SearchActivatedCardReportServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private Map<String, String> cardTypesMap;
    private Map<String, String> priorityLevelMap;
    private Map<String, String> branchListMap;
    private List<ActivatedCardReportBean> cardProductList;
    private List<ActivatedCardReportBean> sortcardProductList;
    private ActivatedCardReportBean activatedCardReportBean;
    private ActivatedCardReportManager activatedCardReportManager;
    private List<String> userList;
    private List<ActivatedCardReportBean> searchBeanList;
    private String url = "/reportexp/cardapplication/activatedcardreport.jsp";
    private String errorMessage;

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
                String pageCode = PageVarList.ACTIVATED_CARD_RPT;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        if (null != request.getParameter("cardType") && !request.getParameter("cardType").equals("")) {
                            request.setAttribute("cardType", request.getParameter("cardType"));

                        }
                        this.setUserInputsToBean(request);
                        this.getAllBranchListMap();
                        request.setAttribute("branchListMap", branchListMap);
                        this.getAllCardProductList();
                        if (null != request.getParameter("cardType") && !request.getParameter("cardType").equals("")) {
                            sortcardProductList = new ArrayList<ActivatedCardReportBean>();
                            for (ActivatedCardReportBean bean : cardProductList) {
                                if (bean.getCardType().equals(request.getParameter("cardType"))) {
                                    sortcardProductList.add(bean);
                                }
                            }
                        } else {
                            sortcardProductList = new ArrayList<ActivatedCardReportBean>();
                            for (ActivatedCardReportBean bean : cardProductList) {
                                sortcardProductList.add(bean);
                            }
                        }

                        request.setAttribute("searchbean", activatedCardReportBean);
                        request.setAttribute("cardProductList", sortcardProductList);
                        this.getAllCardTypesMap();
                        request.setAttribute("cardTypesMap", cardTypesMap);
                        this.getAllPriorityLevelMap();
                        request.setAttribute("priorityLevelMap", priorityLevelMap);
                        this.getUserList();
                        if (isValidate(activatedCardReportBean)) {
                            this.getActivatedCardReportDetails();
                        } else {
                            request.setAttribute("errorMsg", errorMessage);
                        }
                        request.setAttribute("userList", userList);
                        request.setAttribute("searchBeanList", searchBeanList);

                        rd = getServletContext().getRequestDispatcher(url);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_ACTIVATED_CARD_REPORT);

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

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);


        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_ACTIVATED_CARD_REPORT);
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

    private void setUserInputsToBean(HttpServletRequest request) throws Exception {
        try {

            String NIC = request.getParameter("nic").trim();
            String branch = request.getParameter("branch");
            String passport = request.getParameter("passport").trim();
            String priorityLevel = request.getParameter("priorityLevel");
           // String drivingLicence = request.getParameter("drivingLicence").trim();
            String cardType = request.getParameter("cardType");
            String applicationId = request.getParameter("applicationId").trim();
            String cardProduct = request.getParameter("cardProduct");
            String fromdate = request.getParameter("fromdate");
            String todate = request.getParameter("todate");
            String cardNumber = request.getParameter("cardNumber").trim();
            String users = request.getParameter("users");

            activatedCardReportBean = new ActivatedCardReportBean();

            activatedCardReportBean.setNic(NIC);
            activatedCardReportBean.setBranch(branch);
            activatedCardReportBean.setPassport(passport);
            activatedCardReportBean.setPriorityLevel(priorityLevel);
            //activatedCardReportBean.setDrivingLicence(drivingLicence);
            activatedCardReportBean.setCardType(cardType);
            activatedCardReportBean.setApplicationId(applicationId);
            activatedCardReportBean.setCardProduct(cardProduct);
            activatedCardReportBean.setFromDate(fromdate);
            activatedCardReportBean.setTodate(todate);
            activatedCardReportBean.setCardNumber(cardNumber);
            activatedCardReportBean.setUser(users);



        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean isValidate(ActivatedCardReportBean inputBean) throws Exception {

        boolean isValidate = true;
        try {
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
//            if (!inputBean.getDrivingLicence().equals("")) {
//                if (!UserInputValidator.isAlphaNumeric(inputBean.getDrivingLicence())) {
//                    isValidate = false;
//                    errorMessage = MessageVarList.ERROR_DRL_NUMBER;
//                }
//            }
            if (!inputBean.getApplicationId().equals("")) {
                if (!UserInputValidator.isNumeric(inputBean.getApplicationId())) {
                    isValidate = false;
                    errorMessage = MessageVarList.APPASSIGN_APPLICATIONCODE_INVALID;
                }
            }
            if (!inputBean.getCardNumber().equals("")) {
                if (!UserInputValidator.isNumeric(inputBean.getCardNumber())) {
                    isValidate = false;
                    errorMessage = MessageVarList.CARDNUMBER_INVALID;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return isValidate;
    }

    private String getCurrentDate() throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return dateFormat.format(date);

    }

    private void getAllPriorityLevelMap() throws Exception {
        try {
            activatedCardReportManager = new ActivatedCardReportManager();
            priorityLevelMap = activatedCardReportManager.getAllPriorityLevelMap();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getAllCardTypesMap() throws Exception {
        try {
            activatedCardReportManager = new ActivatedCardReportManager();
            cardTypesMap = activatedCardReportManager.getAllCardTypesMap();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getAllBranchListMap() throws Exception {
        try {
            activatedCardReportManager = new ActivatedCardReportManager();
            branchListMap = activatedCardReportManager.getAllBranchListMap();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getAllCardProductList() throws Exception {
        try {
            activatedCardReportManager = new ActivatedCardReportManager();
            cardProductList = activatedCardReportManager.getAllCardProductList();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getUserList() throws Exception {
        try {
            activatedCardReportManager = new ActivatedCardReportManager();
            userList = activatedCardReportManager.getAllUserList();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getActivatedCardReportDetails() throws Exception {
        try {
            activatedCardReportManager = new ActivatedCardReportManager();
            searchBeanList = activatedCardReportManager.getActivatedCardReportDetails(activatedCardReportBean);
        } catch (Exception e) {
            throw e;
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
