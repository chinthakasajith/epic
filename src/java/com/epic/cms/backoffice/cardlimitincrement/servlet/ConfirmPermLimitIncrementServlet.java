/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.cardlimitincrement.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.FeeMgtManager;
import com.epic.cms.backoffice.cardlimitincrement.bean.PermLimitIncrementBean;
import com.epic.cms.backoffice.cardlimitincrement.businesslogic.PermLimitIncrementManager;
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
public class ConfirmPermLimitIncrementServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private RequestDispatcher rd;
    private String url = "/backoffice/cardlimitincrement/permlimitincrement.jsp";
    private PermLimitIncrementManager incrementManager;
    private String cardNumber, incType, amount, status, creditLimit, cashLimit, avlCreditLimit, avlCashLimit, tempCreditAmt, tempCashAmt;
    private double amountD, creditLimitD, cashLimitD, avlCreditLimitD, avlCashLimitD;
    private double newCreditLImitD, newCashLimitD, newAvlCreditLimitD, newAvlCashLimitD;
    private PermLimitIncrementBean permbean;
    boolean successInc = false;
    private SystemAuditBean systemAuditBean;
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
            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.ACCESSPAGE)) {
                throw new AccessDeniedException();
            }

            ///////////            
            try {
                cardNumber = request.getParameter("cardNumber");
                incType = request.getParameter("incType");
                amount = request.getParameter("amount");
                status = request.getParameter("status");
                creditLimit = request.getParameter("onlineCreditLimit");
                cashLimit = request.getParameter("onlineCashLimit");
                avlCreditLimit = request.getParameter("onlineAvlCreditLimit");
                avlCashLimit = request.getParameter("onlineAvlCashLimit");
                tempCreditAmt = request.getParameter("onlineTempCreditAmt");
                tempCashAmt = request.getParameter("onlineTempCashAmt");

                amountD = Double.parseDouble(amount);
                creditLimitD = Double.parseDouble(creditLimit);
                cashLimitD = Double.parseDouble(cashLimit);
                avlCreditLimitD = Double.parseDouble(avlCreditLimit);
                avlCashLimitD = Double.parseDouble(avlCashLimit);

                permbean = new PermLimitIncrementBean();




                if (status.equals(StatusVarList.ACTIVE_STATUS)) {
                    permbean.setCardNumber(cardNumber);
                    permbean.setIncType(incType);
                    permbean.setOnlineCreditLimit(creditLimit);
                    permbean.setOnlineCashLimit(cashLimit);
                    permbean.setOnlineAvlCreditLimit(avlCreditLimit);
                    permbean.setOnlineAvlCashLimit(avlCashLimit);
                    permbean.setApprovedUser(sessionVarlist.getCMSSessionUser().getUserName());
                    permbean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());


                    if (incType.equals("CREDIT")) {
                        newCreditLImitD = creditLimitD + amountD;
                        newAvlCreditLimitD = avlCreditLimitD + amountD;

                        permbean.setOnlineCreditLimit(String.valueOf(newCreditLImitD));
                        permbean.setOnlineAvlCreditLimit(String.valueOf(newAvlCreditLimitD));



                    } else if (incType.equals("CASH")) {
                        if (cashLimitD + amountD <= creditLimitD) {
                            newCashLimitD = cashLimitD + amountD;

                            if (avlCashLimitD + amountD <= avlCreditLimitD) {
                                newAvlCashLimitD = cashLimitD + amountD;
                            } else if (avlCashLimitD + amountD > avlCreditLimitD) {
                                newAvlCashLimitD = newAvlCreditLimitD;
                            }

                        }
                        permbean.setOnlineCashLimit(String.valueOf(newCashLimitD));
                        permbean.setOnlineAvlCashLimit(String.valueOf(newAvlCashLimitD));

                    }
                    this.setAudittraceValue(request);
                    boolean succes = this.limitIncrement();

                    this.setCardFeeCount(cardNumber);
                    feeMgtManager = new FeeMgtManager();
                    int successfeecount = feeMgtManager.feeCountMgt(feeBean);

                    if (succes) {
                        if (successfeecount == 1) {
                            request.setAttribute("operationtype", "search");
                            request.setAttribute("successMsg", "Permanent Limit incremented Succesfully");
                        }
                    }

                    rd = getServletContext().getRequestDispatcher(url);


                } else {
                    request.setAttribute("errorMsg", "This card cannot be limit incremented");
                    rd = getServletContext().getRequestDispatcher(url);
                }



                //rd.forward(request, response);

            } catch (Exception e) {
                request.setAttribute("operationtype", "search");
                request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            }

            ///////////////


        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadPermLimitIncrementServlet");
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

    private boolean limitIncrement() throws Exception {
        try {
            incrementManager = new PermLimitIncrementManager();
            successInc = incrementManager.limitIncrement(permbean, systemAuditBean);
        } catch (Exception e) {
            throw e;
        }
        return successInc;
    }

    private void setCardFeeCount(String cardnum) throws Exception {

        try {
            feeBean = new FeeBean();

            //feeBean.setCardNo(request.getParameter("cardNumber"));
            feeBean.setCardNo(cardnum);
            feeBean.setFeeCode("FEC010");
            feeBean.setStatus(StatusVarList.ACTIVE_STATUS);
            feeBean.setLastUpdateUser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("cardNum");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Approve Permenant Limit Increment. Card Number: " + uniqueId + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.LIMIT_INCREMENT);
            systemAuditBean.setPageCode(PageVarList.APPROVE_PERM_LIMIT_INC);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue("");
            systemAuditBean.setNewValue("");

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

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
