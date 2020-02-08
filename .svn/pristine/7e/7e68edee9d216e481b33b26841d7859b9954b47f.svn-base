/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CardScoreBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreRuleBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.ScoreCardAssignRuleBean;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CardScoreManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.exception.ValidateException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
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
 * @author ayesh
 */
public class ViewCardScoreServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/camm/applicationconfig/creditscore/scorecardmgthome.jsp";
    private SystemUserManager systemUserManager = null;
    private SystemUserManager sysUserOnj;
    private List<StatusBean> statusBean;
    private List<CreditScoreRuleBean> ruleList;

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
        try {

            try {

                HttpSession sessionObj = request.getSession(false);
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();
                try {
                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(),
                            sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }
                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();

                }
            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.ACCESSPAGE)) {
                throw new AccessDeniedException();
            }


            sysUserOnj = new SystemUserManager();
            statusBean = sysUserOnj.getStatusByUserRole("GENR");
            ruleList = CardScoreManager.getScoreCardInstance().getAllCreditScoreRules();

            request.setAttribute(RequestVarList.CARDSCORE_DETAILS_LIST,
                    CardScoreManager.getScoreCardInstance().getAllScoreCardDetails());
            request.setAttribute(RequestVarList.CARDSCORE_RULE_LIST, ruleList);
            request.setAttribute("operationtype", "view");
            request.setAttribute(RequestVarList.SECTION_STATUS_LIST, statusBean);
            String id = request.getParameter("id");
            CardScoreBean bean = null;

            for (CardScoreBean scoreBean : CardScoreManager.getScoreCardInstance().getAllScoreCardDetails()) {
                if (scoreBean.getScoreCardCode().equals(id)) {
                    bean = scoreBean;
                }
            }

            List<CreditScoreRuleBean> notAsignRule = new ArrayList<CreditScoreRuleBean>();
            if (bean != null) {
                List<ScoreCardAssignRuleBean> assignedRule = CardScoreManager.getScoreCardInstance().getAssignRule(id);
                List<CreditScoreRuleBean> assignRuleList = new ArrayList<CreditScoreRuleBean>();
                List<Integer> backupList = new ArrayList<Integer>();

                for (int i = 0; i < ruleList.size(); i++) {
                    boolean flag = true;
                    CreditScoreRuleBean creditScBean = ruleList.get(i);

                    for (int j = 0; j < assignedRule.size(); j++) {
                        if (assignedRule.get(j).getRuleCode().equals(creditScBean.getRuleCode())) {
                            assignRuleList.add(ruleList.get(i));
                            flag = false;
                            backupList.add(i);
                        }
                    }
                    if (flag) {
                        notAsignRule.add(creditScBean);
                    }
                }

                request.setAttribute(RequestVarList.CARDSCORE_RULE_LIST, notAsignRule);
                request.setAttribute(RequestVarList.CARDSCORE_ASSIGNEDRULE_LIST, assignRuleList);
                request.setAttribute(RequestVarList.CARDSCORE_BEAN, bean);
            }

            request.setAttribute(RequestVarList.CARDSCORE_DETAILS_LIST,
                    CardScoreManager.getScoreCardInstance().getAllScoreCardDetails());

            rd = request.getRequestDispatcher(url);

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadCardScoreMgtServlet");
            rd.include(request, response);
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (ValidateException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR,
                    CardScoreManager.getScoreCardInstance().getErrorMsg());
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {

            rd.forward(request, response);
        }
    }

    /**
     * isValidTaskByUser
     * @param userTaskList
     * @param task
     * @return
     * @throws Exception 
     */
    private boolean isValidTaskByUser(List<String> userTaskList, String task) throws Exception {
        boolean isValidTask = false;
        try {
            for (String usertask : userTaskList) {
                if (task.equals(usertask)) {
                    isValidTask = true;
                }
            }
            return isValidTask;
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
