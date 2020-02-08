/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.NumberGenerationReportBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.NumberGenerationReportManager;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class LoadNumberGenerationReportServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private NumberGenerationReportManager numberGenerationReportManager;
    private Map<String, String> cardTypesMap;
    private Map<String, String> priorityLevelMap;
    private Map<String, String> branchListMap;
    private List<NumberGenerationReportBean> cardProductList;
    private List<NumberGenerationReportBean> sortcardProductList;
    private List<String> userList;
    private NumberGenerationReportBean numberGenerationReportBean;
    private String url = "/reportexp/cardapplication/numbergenerationreport.jsp";

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
                String pageCode = PageVarList.NUMBER_GENERATION_RPT;
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    try {
                        this.setUserInputsToBean(request);
                        this.getAllBranchListMap();
                        request.setAttribute("branchListMap", branchListMap);
                        this.getAllCardProductList();

                        request.setAttribute("searchbean", numberGenerationReportBean);
                        request.setAttribute("cardProductList", cardProductList);
                        this.getAllCardTypesMap();
                        request.setAttribute("cardTypesMap", cardTypesMap);
                        this.getAllPriorityLevelMap();
                        request.setAttribute("priorityLevelMap", priorityLevelMap);
                        this.getUserList();
                        request.setAttribute("userList", userList);

                        rd = getServletContext().getRequestDispatcher(url);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_NUMBER_GENERATION_REPORT);

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
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_NUMBER_GENERATION_REPORT);
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

    private void getAllPriorityLevelMap() throws Exception {
        try {
            numberGenerationReportManager = new NumberGenerationReportManager();
            priorityLevelMap = numberGenerationReportManager.getAllPriorityLevelMap();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getAllCardTypesMap() throws Exception {
        try {
            numberGenerationReportManager = new NumberGenerationReportManager();
            cardTypesMap = numberGenerationReportManager.getAllCardTypesMap();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getAllBranchListMap() throws Exception {
        try {
            numberGenerationReportManager = new NumberGenerationReportManager();
            branchListMap = numberGenerationReportManager.getAllBranchListMap();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getAllCardProductList() throws Exception {
        try {
            numberGenerationReportManager = new NumberGenerationReportManager();
            cardProductList = numberGenerationReportManager.getAllCardProductList();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getUserList() throws Exception {
        try {
            numberGenerationReportManager = new NumberGenerationReportManager();
            userList = numberGenerationReportManager.getAllUserList();
        } catch (Exception e) {
            throw e;
        }
    }

    private void setUserInputsToBean(HttpServletRequest request) throws Exception {
        try {

            String NIC = request.getParameter("nic");
            String branch = request.getParameter("branch");
            String passport = request.getParameter("passport");
            String priorityLevel = request.getParameter("priorityLevel");
            String drivingLicence = request.getParameter("drivingLicence");
            String cardType = request.getParameter("cardType");
            String applicationId = request.getParameter("applicationId");
            String cardProduct = request.getParameter("cardProduct");
            String fromdate = request.getParameter("fromdate");
            String todate = request.getParameter("todate");
            String cardNumber = request.getParameter("cardNumber");
            String users = request.getParameter("users");

            numberGenerationReportBean = new NumberGenerationReportBean();

            numberGenerationReportBean.setNic(NIC);
            numberGenerationReportBean.setBranch(branch);
            numberGenerationReportBean.setPassport(passport);
            numberGenerationReportBean.setPriorityLevel(priorityLevel);
            numberGenerationReportBean.setDrivingLicence(drivingLicence);
            numberGenerationReportBean.setCardType(cardType);
            numberGenerationReportBean.setApplicationId(applicationId);
            numberGenerationReportBean.setCardProduct(cardProduct);
            numberGenerationReportBean.setFromDate(fromdate);
            numberGenerationReportBean.setTodate(todate);
            numberGenerationReportBean.setCardNumber(cardNumber);
            numberGenerationReportBean.setUser(users);
            numberGenerationReportBean.setFromDate(getCurrentDate());
            numberGenerationReportBean.setTodate(getCurrentDate());


        } catch (Exception ex) {
            throw ex;
        }
    }

    private String getCurrentDate() throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return dateFormat.format(date);

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
