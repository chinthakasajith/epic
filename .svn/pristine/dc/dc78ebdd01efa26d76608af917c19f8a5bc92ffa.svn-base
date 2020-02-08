/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreRuleBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CardScoreBean;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CardScoreManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.exception.ValidateException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.SectionVarList;
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
 *this servlet use for do operation that relate to add card score management 
 * @author ayesh
 */
public class AddCardScoreMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/camm/applicationconfig/creditscore/scorecardmgthome.jsp";
    private SystemUserManager systemUserManager = null;
    private SystemUserManager sysUserOnj;
    private List<StatusBean> statusBean;
    private SystemAuditBean systemAuditBean;
    private String errorMessage = null;
    private List<CreditScoreRuleBean> ruleList;
    private String newValue;

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

            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.ADD)) {
                throw new AccessDeniedException();
            }

            ruleList = CardScoreManager.getScoreCardInstance().getAllCreditScoreRules();
            sysUserOnj = new SystemUserManager();
            statusBean = sysUserOnj.getStatusByUserRole("GENR");

            request.setAttribute(RequestVarList.CARDSCORE_RULE_LIST, ruleList);
            request.setAttribute("operationtype", "add");
            request.setAttribute(RequestVarList.SECTION_STATUS_LIST, statusBean);
            request.setAttribute(RequestVarList.CARDSCORE_DETAILS_LIST,
                    CardScoreManager.getScoreCardInstance().getAllScoreCardDetails());

            String[] assignedList = request.getParameterValues("assignsectionlist");
            CardScoreBean scoreBean = new CardScoreBean();

            scoreBean.setScoreCardCode(request.getParameter("scoreCradCode"));
            scoreBean.setScoreCardName(request.getParameter("scoreCradName"));
            scoreBean.setProduct(request.getParameter("product"));
            scoreBean.setRules(assignedList);
            scoreBean.setMinScoreCard(request.getParameter("minScore"));
            scoreBean.setMaxScoreCard(request.getParameter("maxScore"));
            scoreBean.setStatus(request.getParameter("status"));
            scoreBean.setLastUpdateUser(sessionVarlist.getCMSSessionUser().getUserName());
            
            scoreBean.setCardType(request.getParameter("product"));
            
            
            newValue = scoreBean.getScoreCardCode() + "|" + scoreBean.getScoreCardName() + "|" +
                     scoreBean.getProduct()  + "|" + scoreBean.getMinScoreCard() + "|" + 
                    scoreBean.getMaxScoreCard() + "|" + scoreBean.getStatus() + "|" + scoreBean.getLastUpdateUser();
            
            request.setAttribute(RequestVarList.CARDSCORE_BEAN, scoreBean);

            List<CreditScoreRuleBean> asignRule = new ArrayList<CreditScoreRuleBean>();
            String[] asignedRule = scoreBean.getRules();
            List<CreditScoreRuleBean> notAsignRule = new ArrayList<CreditScoreRuleBean>();

            if (assignedList != null) {

                for (int i = 0; i < ruleList.size(); i++) {
                    boolean flag = true;
                    CreditScoreRuleBean bean = ruleList.get(i);
                    for (int j = 0; j < asignedRule.length; j++) {
                        if (bean.getRuleCode().equals(asignedRule[j])) {
                            asignRule.add(bean);
                            flag = false;
                        }
                    }
                    if (flag) {
                        notAsignRule.add(bean);
                    }
                }
                request.setAttribute(RequestVarList.CARDSCORE_RULE_LIST, notAsignRule);
            } else {
                request.setAttribute(RequestVarList.CARDSCORE_RULE_LIST, ruleList);
            }

            request.setAttribute(RequestVarList.CARDSCORE_ASSIGNEDRULE_LIST, asignRule);

            if (CardScoreManager.getScoreCardInstance().isInputValidate(scoreBean)) {
                this.setAudittraceValue(request);
                int rowCount = CardScoreManager.getScoreCardInstance().insertNewScoreCard(scoreBean, systemAuditBean);

                if (rowCount == asignedRule.length + 1) {
                    request.setAttribute(MessageVarList.JSP_SUCCESS,
                            MessageVarList.SCORECRAD_ADD_SUCCESS);
                    request.setAttribute("operationtype", "default");
                }
            }

            request.setAttribute(RequestVarList.CARDSCORE_DETAILS_LIST,
                    CardScoreManager.getScoreCardInstance().getAllScoreCardDetails());

            rd = request.getRequestDispatcher("/LoadCardScoreMgtServlet");

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
    
    

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("scoreCradCode");


            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Add score card. score card code :" + uniqueId + " by:" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CREDITSCOREMGT);
            systemAuditBean.setPageCode(PageVarList.SCORECARD);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue("");
            systemAuditBean.setNewValue(newValue);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());


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
