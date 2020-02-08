/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.LimitExceedReportBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.SearchLimitExceedReportManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
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
 * @author asitha_l
 */
public class SearchLimitExceedReportServlet extends HttpServlet {

    private HttpSession sessionObj;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String url = "/reportexp/cardreport/searchlimitexceedreporthome.jsp";
    private RequestDispatcher rd;
    private HashMap<String, String> cardTypeList = null;
    private SearchLimitExceedReportManager searchLimitExceedReportManager;
    private LimitExceedReportBean limitExceedReportBean = null;
    private String errorMessage;
    private List<LimitExceedReportBean> limitExceedReportList = null;

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
            try {
                sessionObj = request.getSession(false);
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                try {
                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }

                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();

                }
                try {
                    //set page code and task codes
                    String pageCode = PageVarList.LIMIT_EXCEED_REPORT;
                    String taskCode = TaskVarList.SEARCH;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        try {
                            this.getAllCardTypeList();
                            request.setAttribute("cardTypeList", cardTypeList);

                            this.setUserInputToBean(request);

                            if (validateUserInput(limitExceedReportBean)) {
                                this.searchLimitExceedReport(limitExceedReportBean);

                                request.setAttribute("limitExceedReportList", limitExceedReportList);
                                request.setAttribute("limitExceedReportBean", limitExceedReportBean);


                            } else {
                                request.setAttribute("limitExceedReportBean", limitExceedReportBean);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }

                        } catch (Exception ex) {
                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            rd = getServletContext().getRequestDispatcher(url);
                        }
                    } else {
                        throw new AccessDeniedException();

                    }

                    sessionVarlist.setUserPageTaskList(userTaskList);

                } catch (AccessDeniedException adex) {
                    throw adex;

                }

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            rd = request.getRequestDispatcher(url);

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);

        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
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

    private void getAllCardTypeList() throws Exception {
        try {

            searchLimitExceedReportManager = new SearchLimitExceedReportManager();
            cardTypeList = searchLimitExceedReportManager.getAllCardTypeList();

        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public void setUserInputToBean(HttpServletRequest request) throws Exception {
        try {
            limitExceedReportBean = new LimitExceedReportBean();

            limitExceedReportBean.setCardNumber(request.getParameter("cardNum").trim());
            limitExceedReportBean.setCardType(request.getParameter("cardtype"));
            limitExceedReportBean.setCashOrCredit(request.getParameter("cashCreditStat"));
            limitExceedReportBean.setUsagePercentage(request.getParameter("usagePercentage").trim());
            

        } catch (Exception e) {
            throw e;
        }
    }
    
    public boolean validateUserInput(LimitExceedReportBean limitExceedReportBean) throws Exception {
        boolean isValidate = true;

        String cashOrCredit=limitExceedReportBean.getCashOrCredit();
        String usagePercentage=limitExceedReportBean.getUsagePercentage();

        
        try {
            if (cashOrCredit == null) {
                isValidate = false;
                errorMessage = MessageVarList.CASH_OR_CREDIT_STATUS_EMPTY;
            }else if (usagePercentage.contentEquals("") || usagePercentage.length() <= 0){
                isValidate = false;
                errorMessage = MessageVarList.USAGE_PERCENTAGE_EMPTY;
            }else if (!UserInputValidator.isDecimalNumeric(usagePercentage) && !UserInputValidator.isNumeric(usagePercentage)){
                isValidate = false;
                errorMessage = MessageVarList.USAGE_PERCENTAGE_NUMERIC;
            } 
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }
    
    private void searchLimitExceedReport(LimitExceedReportBean limitExceedReportBean) throws Exception {

        try {
            searchLimitExceedReportManager = new SearchLimitExceedReportManager();
            limitExceedReportList = new ArrayList<LimitExceedReportBean>();

            limitExceedReportList = searchLimitExceedReportManager.searchLimitExceedReport(limitExceedReportBean);

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
