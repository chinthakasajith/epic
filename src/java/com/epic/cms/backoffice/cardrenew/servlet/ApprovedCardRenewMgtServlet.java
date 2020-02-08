/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.cardrenew.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.FeeMgtManager;
import com.epic.cms.backoffice.cardrenew.bean.CardRenewBean;
import com.epic.cms.backoffice.cardrenew.bean.CardSearchBean;
import com.epic.cms.backoffice.cardrenew.businesslogic.CardRenewManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author badrika
 */
public class ApprovedCardRenewMgtServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private RequestDispatcher rd;
    private String url = "/backoffice/cardrenew/cardrenewhome.jsp";
    private CardSearchBean cardSearchBean;
    private CardRenewBean cardRenewBean;
    private String errorMessage;
    private List<CardRenewBean> cardList;
    private CardRenewManager cardRenewManager;
    private String[] cardListtorenew = null;
    private SystemAuditBean systemAuditBean;
    private boolean successUpdate = false;
    private boolean successfeecountbol = false;
    private FeeBean feeBean;
    private FeeMgtManager feeMgtManager;

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

            //request.setAttribute("operationtype", "search");
            HttpSession sessionObj = request.getSession(false);
            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();
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
            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.UPDATE)) {
                throw new AccessDeniedException();
            }

            ///////////
            try {
                cardListtorenew = request.getParameterValues("checkedVelue");
                try {
                    if (cardListtorenew.length > 0) {

                        cardRenewBean = new CardRenewBean();
                        cardRenewBean.setLastUpdatedUser(sessionUser.getUserName());

                        cardRenewManager = new CardRenewManager();
                        feeMgtManager = new FeeMgtManager();
//                        successUpdate = cardRenewManager.proceedCardRenew(cardListtorenew, systemAuditBean);



                        for (int i = 0; i < cardListtorenew.length; i++) {

                            this.setAudittraceValue(request, cardListtorenew[i]);
                            successUpdate = cardRenewManager.proceedCardRenew(cardListtorenew[i], systemAuditBean);
                            this.setCardFeeCount(cardListtorenew[i]);
                            int successfeecount = feeMgtManager.feeCountMgt(feeBean);
                            successfeecountbol = false;
                            if (successfeecount == 1) {
                                successfeecountbol = true;
                            }
                        }

                        if (successUpdate && successfeecountbol) {
                            request.setAttribute("successMsg", "Card Renew Proceeded Succesfully");
                        }
                        rd = getServletContext().getRequestDispatcher(url);
                    }

                } catch (NullPointerException ne) {
                    rd = getServletContext().getRequestDispatcher(url);
                }

            } catch (Exception e) {
                request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                rd = getServletContext().getRequestDispatcher(url);
            }


///////////
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

            rd = request.getRequestDispatcher(url);
            ///////////////


        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadCardRenewMgtServlet");
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

    private void setCardFeeCount(String cardnum) throws Exception {

        try {
            feeBean = new FeeBean();

            //feeBean.setCardNo(request.getParameter("cardNumber"));
            feeBean.setCardNo(cardnum);
            feeBean.setFeeCode("FEC008");
            feeBean.setStatus(StatusVarList.ACTIVE_STATUS);
            feeBean.setLastUpdateUser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

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

    private void setAudittraceValue(HttpServletRequest request, String cardnum2) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(cardnum2);
            //set description 
            systemAuditBean.setDescription("Approve Card Renewal. Card Number: " + cardnum2 + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.CARD_RENEW);
            systemAuditBean.setPageCode(PageVarList.CARD_RENEW);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue("");


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
