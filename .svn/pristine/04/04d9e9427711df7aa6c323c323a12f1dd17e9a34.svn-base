/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.OperatorBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreRuleBean;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CreditScoreRuleDefineManagement;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author upul
 */
public class changeOnChangeOperatorServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/camm/applicationconfig/creditscore/creditscoreruledef.jsp";
    private SystemUserManager systemUserManager = null;
    private CreditScoreRuleDefineManagement defineManagement;
    private List<String> userTaskList;
    private List<OperatorBean> operatorBeansLst;
    private CreditScoreRuleBean scoreRuleBean;
    private List<CreditScoreRuleBean> creditScoreRuleBeanLst;
    private List<CreditScoreRuleBean> creditScoreRuleActiveBeanLst;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            HttpSession sessionObj = request.getSession(false);
            try {
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
                    String pageCode = PageVarList.CREDITSCORERULEDEF;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
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
            ///////////////////////////////////////////

            String ruleNo2 = request.getParameter("check");

            String formType = request.getParameter("operationtype");
            this.getAllCreditScoreRuleDefDetails();
            request.setAttribute("creditScoreRuleLst", creditScoreRuleBeanLst);
            this.setValuesToList(request);

            if (ruleNo2.equals("ruleNo2")) {
                if (scoreRuleBean.getCondition().equals("7")) {
                    if (!(scoreRuleBean.getRuleNoOne().contentEquals("") || scoreRuleBean.getRuleNoOne().length() <= 0)) {

                        request.setAttribute("ruleNoOne", "OK");

                    }
                }
            }

            if (scoreRuleBean.getCondition().equals("7")) {

                this.getAllDistinctActiveCreditScoreRules();
                request.setAttribute("activeScoreRuleLst", creditScoreRuleActiveBeanLst);

            }
            if (scoreRuleBean.getCondition().equals("6")) {

                request.setAttribute("conditionVar", "6");

            }


            request.setAttribute("scoreRuleBean", scoreRuleBean);


            request.setAttribute("operationtype", formType);

            this.operatorList();

            request.setAttribute("conditions", operatorBeansLst);
            rd = request.getRequestDispatcher(url);





            ///////////////////////////////////////////  




        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.toString()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.include(request, response);
        }
    }

    /**
     * check task in valid for this action
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
     * operatorList
     * @return
     * @throws Exception 
     */
    private List<OperatorBean> operatorList() throws Exception {
        operatorBeansLst = new ArrayList<OperatorBean>();
        try {

            defineManagement = new CreditScoreRuleDefineManagement();
            operatorBeansLst = defineManagement.getAllOperatorList();

        } catch (Exception ex) {
            throw ex;
        }
        return operatorBeansLst;

    }

    private void setValuesToList(HttpServletRequest request) {

        try {
            scoreRuleBean = new CreditScoreRuleBean();
            scoreRuleBean.setRuleCode(request.getParameter("ruleCode"));
            scoreRuleBean.setRuleName(request.getParameter("ruleName"));
            scoreRuleBean.setFieldName(request.getParameter("fieldName"));
            scoreRuleBean.setCondition(request.getParameter("conditionVar"));
            scoreRuleBean.setValue(request.getParameter("value"));
            scoreRuleBean.setRuleNoOne(request.getParameter("ruleNoOne"));
//            scoreRuleBean.setRuleNoTwo(request.getParameter("ruleNoOne"));
//            scoreRuleBean.setRuleNoTwo(request.getParameter("ruleNoTwo"));
            scoreRuleBean.setRuleNoTwo(request.getParameter("score"));
            scoreRuleBean.setStatus(request.getParameter("selectstatuscode"));

        } catch (Exception ex) {
        }



    }

    /**
     * getAllCreditScoreRuleDefDetails
     * @throws Exception 
     */
    private void getAllCreditScoreRuleDefDetails() throws Exception {
        try {
            defineManagement = new CreditScoreRuleDefineManagement();
            creditScoreRuleBeanLst = defineManagement.getAllCreditScoreRuleDefDetails();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * getAllCreditScoreRuleDefDetails
     * @throws Exception 
     */
    private void getAllDistinctActiveCreditScoreRules() throws Exception {
        try {
            defineManagement = new CreditScoreRuleDefineManagement();
            creditScoreRuleActiveBeanLst = defineManagement.getAllDistinctActiveCreditScoreRules();
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
