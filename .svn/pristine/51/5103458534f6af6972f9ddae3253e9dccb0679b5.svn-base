/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.card.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.cardlimitincrement.bean.CommonCardParameterBean;
import com.epic.cms.backoffice.cardlimitincrement.bean.TempLimitIncrementBean;
import com.epic.cms.callcenter.callcentersearch.bean.CallHistoryBean;
import com.epic.cms.callcenter.card.businesslogic.PermLimitIncrementRequestManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ManagePermLimitIncrementRequestServlet extends HttpServlet {

    private TempLimitIncrementBean tempBean = null;
    private String errorMessage;
    private SessionUser sessionUser = null;
    private HttpSession sessionObj;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private RequestDispatcher rd;
    private PermLimitIncrementRequestManager permIncrementManager;
    private CommonCardParameterBean limitBean;
    private SystemAuditBean systemAuditBean;
    private String url = "/callcenter/card/permlimitincrementrequest.jsp";
    private CallHistoryBean callhistoryBean;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            //call to existing session
            /////////////////////////////////////////////////////////////////////
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
            /////////////////////////////////////////////////////////////////////

            this.setUserInputToBean(request);

            if (tempBean.getCreditOrCash().equals("CREDIT")) {

                request.setAttribute("selectedtab", "0");

            } else if (tempBean.getCreditOrCash().equals("CASH")) {

                request.setAttribute("selectedtab", "1");
            }

            if (!tempBean.getStatus().equals(StatusVarList.ACTIVE_STATUS)) {
                request.setAttribute("errorMsg", "This card cannot be limit incremented");
                rd = getServletContext().getRequestDispatcher("/ViewCustomerMgtServlet?section=CCCARD");

            } else if (this.validateUserInput(tempBean)) {
                boolean flag = false;
                String rate, amount = "", creditLimit, cashLimit, creditAmountS, cashAmountS;
                double rateD = 0.0, amountD = 0.0, creditLimitD = 0.0, cashLimitD = 0.0, creditAmount = 0.0, cashAmount = 0.0;

                creditLimit = tempBean.getCreditLimit();
                cashLimit = tempBean.getCashLimit();

                creditLimitD = Double.parseDouble(creditLimit);
                cashLimitD = Double.parseDouble(cashLimit);

                if (!tempBean.getRate().equals("")) {
                    rate = tempBean.getRate();
                    rateD = Double.parseDouble(rate);

                    if (tempBean.getCreditOrCash().equals("CREDIT")) {
                        creditAmount = rateD * creditLimitD / 100;
                        creditAmountS = String.valueOf(creditAmount);
                        tempBean.setAmount(creditAmountS);
                    }
                    if (tempBean.getCreditOrCash().equals("CASH")) {
                        cashAmount = rateD * cashLimitD / 100;
                        cashAmountS = String.valueOf(cashAmount);
                        tempBean.setAmount(cashAmountS);
                    }

                }

                if (!tempBean.getAmount().equals("")) {
                    amount = tempBean.getAmount();
                    amountD = Double.parseDouble(amount);
                    tempBean.setAmount(amount);
                }

                this.getLimitDetails();

                if (tempBean.getCreditOrCash().equals("CREDIT")) {

                    if (!amount.equals("")) {

                        if (limitBean.getMaxPermCreditLimitAmount() > amountD) {
                            flag = true;//insert()
                        } else {
                            request.setAttribute("tempBean", tempBean);
                            request.setAttribute("errorMsg", "The maximum credit increment amount is " + limitBean.getMaxPermCreditLimitAmount());
                        }
                    } else {
                        //tempBean.setAmount(String.valueOf(creditAmount));
                        if (limitBean.getMaxPermCreditLimitAmount() > creditAmount) {
                            flag = true;//insert()
                        } else {
                            request.setAttribute("tempBean", tempBean);
                            request.setAttribute("errorMsg", "The maximum credit increment amount is " + limitBean.getMaxPermCreditLimitAmount());
                        }
                    }
                } else if (tempBean.getCreditOrCash().equals("CASH")) {
                    if (!amount.equals("")) {

                        if (limitBean.getMaxPermCashLimitAmount() > amountD) {
                            flag = true;//insert()
                        } else {
                            request.setAttribute("tempBean", tempBean);
                            request.setAttribute("errorMsg", "The maximum cash increment amount is " + limitBean.getMaxPermCashLimitAmount());
                        }
                    } else {
                        tempBean.setAmount(String.valueOf(cashAmount));
                        if (limitBean.getMaxPermCashLimitAmount() > cashAmount) {
                            flag = true;//insert()
                        } else {
                            request.setAttribute("tempBean", tempBean);
                            request.setAttribute("errorMsg", "The maximum cash increment amount is " + limitBean.getMaxPermCashLimitAmount());
                        }
                    }
                }
                if (flag) {
                    this.setAudittraceValue(request);
                    this.setCallHistoryRecord(request);
                    int i = this.insertLimitIncrement();//insert
                    if (i == 1) {
                        request.setAttribute(MessageVarList.JSP_SUCCESS, "Request Sent Successfully");
                    }
                    rd = getServletContext().getRequestDispatcher("/ViewCustomerMgtServlet?section=CCCARD");

                } else {
                    rd = request.getRequestDispatcher(url);
                }

            } else {

                request.setAttribute("tempBean", tempBean);
                request.setAttribute("errorMsg", errorMessage);
                rd = request.getRequestDispatcher(url);

            }

            //////////////////////////////////////////////////////////////////////
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);

        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {
        } finally {
            rd.forward(request, response);
            out.close();

        }
    }

    public int insertLimitIncrement() throws Exception {
        try {
            permIncrementManager = new PermLimitIncrementRequestManager();
            int i = permIncrementManager.insertLimitIncrement(systemAuditBean, tempBean, callhistoryBean);
            //int i = permIncrementManager.insertLimitIncrement(systemAuditBean, tempBean, callhistoryBean);
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    private CommonCardParameterBean getLimitDetails() throws Exception {
        try {
            limitBean = new CommonCardParameterBean();
            permIncrementManager = new PermLimitIncrementRequestManager();
            limitBean = permIncrementManager.getLimitDetails();
        } catch (Exception ex) {
            throw ex;
        }
        return limitBean;

    }

    public boolean validateUserInput(TempLimitIncrementBean bean) throws Exception {
        boolean isValidate = true;

        try {

            if (bean.getRate().equals("") && bean.getAmount().equals("")) {
                isValidate = false;
                errorMessage = "Rate Or Amount Should be Entered";
            } else if (!bean.getRate().equals("") && !bean.getAmount().equals("")) {
                isValidate = false;
                errorMessage = "Enter only rate or amount";
            } else if (!bean.getAmount().equals("") && (!UserInputValidator.isNumeric(tempBean.getAmount())) && !UserInputValidator.isDecimalNumeric(tempBean.getAmount())) {
                isValidate = false;
                errorMessage = MessageVarList.AMOUNT_INVALID;
            } else if (!bean.getRate().equals("") && (!UserInputValidator.isNumeric(tempBean.getRate()) && !UserInputValidator.isDecimalNumeric(tempBean.getRate()))) {
                isValidate = false;
                errorMessage = MessageVarList.RATE_INVALID;
            } else if (!bean.getRate().equals("") && Double.parseDouble(bean.getRate()) > 100.0) {
                isValidate = false;
                errorMessage = "Rate Cannot be exceeded 100%";
            } else if (!bean.getRemark().equals("") && !UserInputValidator.isCorrectString(tempBean.getRemark())) {
                isValidate = false;
                errorMessage = "Enter a valid remark";
            }

//            else if (bean.getRemark().equals("")) {
//                isValidate = false;
//                errorMessage = "Remark Should be Entered";
//            }
        } catch (Exception ex) {
            isValidate = false;
        }
        return isValidate;
    }

    private void setUserInputToBean(HttpServletRequest request) throws Exception {
        try {

            tempBean = new TempLimitIncrementBean();

            tempBean.setCardNumber(request.getParameter("cardNumber").trim());
            tempBean.setCreditLimit(request.getParameter("creditLimit").trim());
            tempBean.setCashLimit(request.getParameter("cashLimit").trim());
            tempBean.setAvlCreditLimit(request.getParameter("avlCreditLimit").trim());
            tempBean.setAvlCashLimit(request.getParameter("avlCashLimit").trim());
            tempBean.setStatus(request.getParameter("status").trim());
            tempBean.setTempCrediAmt(request.getParameter("tempCrediAmt").trim());
            tempBean.setTempCashAmt(request.getParameter("tempCashAmt").trim());

            tempBean.setOnlineCreditLimit(request.getParameter("onlineCreditLimit").trim());
            tempBean.setOnlineCashLimit(request.getParameter("onlineCashLimit").trim());
            tempBean.setOnlineAvlCreditLimit(request.getParameter("onlineAvlCreditLimit").trim());
            tempBean.setOnlineAvlCashLimit(request.getParameter("onlineAvlCashLimit").trim());
            tempBean.setOnlineStatus(request.getParameter("onlineStatus").trim());
            tempBean.setOnlineTempCrediAmt(request.getParameter("onlineTempCrediAmt").trim());
            tempBean.setOnlineTempCashAmt(request.getParameter("onlineTempCashAmt").trim());

            tempBean.setCreditOrCash(request.getParameter("type").trim());
            tempBean.setAmount(request.getParameter("amount").trim());
            tempBean.setRate(request.getParameter("rate").trim());
            tempBean.setRemark(request.getParameter("remark").trim());
            tempBean.setLastUpdatedUser(sessionUser.getUserName());

        } catch (Exception e) {
            throw e;

        }
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("cardNumber");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Add Permenant Limit Increment Request. Card Number: " + uniqueId + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CALL_CENTER_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CALL_CENTER_CARD);
            systemAuditBean.setPageCode(PageVarList.PERM_LIMIT_INC_REQ);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
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

    private void setCallHistoryRecord(HttpServletRequest request) throws Exception {

        try {
            callhistoryBean = new CallHistoryBean();

            callhistoryBean.setCallLogId(sessionVarlist.getCallLogId());
            callhistoryBean.setOperation(TaskVarList.ADD);//task code
            callhistoryBean.setRemarks("");
            callhistoryBean.setCardNo(sessionVarlist.getCardNumber());
            callhistoryBean.setApplicationId(sessionVarlist.getApplicationId());
            callhistoryBean.setCustomerId(sessionVarlist.getCustomerId());
            callhistoryBean.setAccountNo(sessionVarlist.getAccountId());
            callhistoryBean.setPageCode(PageVarList.PERM_LIMIT_INC_REQ);
            callhistoryBean.setOldValue("");
            callhistoryBean.setNewValue("");
            callhistoryBean.setTid("");
            callhistoryBean.setMid("");

            callhistoryBean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
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
