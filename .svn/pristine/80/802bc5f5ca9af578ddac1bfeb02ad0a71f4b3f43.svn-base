/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.cardstandingorder.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.backoffice.cardstandingorder.bean.CardDetailsBean;
import com.epic.cms.backoffice.cardstandingorder.bean.CardStandingOrderBean;
import com.epic.cms.backoffice.cardstandingorder.businesslogic.CardStandingOrderManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @author badrika
 */
public class AddUpdateCardStandingOrderServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String url = "/backoffice/standingorder/standingorder.jsp";
    private RequestDispatcher rd;
    private HashMap<String, String> currencyList;
    private HashMap<String, String> orderTypeList;
    private String paramet;
    private String cardnum;
    private CardStandingOrderBean bean;
    private String errorMessage;
    private CardDetailsBean cardbean;
    private CardDetailsBean cardbean2;
    private SystemAuditBean systemAuditBean;
    private int orderId;
    private String newV = "";
    private String oldV = "";
    private List<CardStandingOrderBean> orderList;
    private CardStandingOrderBean stdbean;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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
            //request.setAttribute("operationtype", "fill");
            HttpSession sessionObj = request.getSession(false);

            String pageCode = PageVarList.CARD_STADING_ORDER;
            String taskCode = TaskVarList.ADD;

            try {
                request.setAttribute("operationtype", "add");
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

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            /////////////////

            CardStandingOrderManager manager = new CardStandingOrderManager();
            //currencyList = manager.getAllCurrencyList();
            //request.setAttribute("currencyList", currencyList);

            orderTypeList = manager.getAllStandingOrderTypeListOfCardPaymentCategory();
            request.setAttribute("orderTypeList", orderTypeList);

            orderList = manager.getAllOrderLists();
            request.setAttribute("orderList", orderList);

            orderId = manager.getOrderId();
            request.setAttribute("orderId", orderId);

            Date date = new Date();

            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            ft.format(date);

            request.setAttribute("date", ft.format(date));



            paramet = request.getParameter("paramet");
            cardnum = request.getParameter("cardNum");

            cardbean = manager.getCardDetails(cardnum);

            this.setValuesToBean(request);
            //cardbean2 = manager.getMinMaxAmount(bean.getOrderTypeId());


            if (!this.validateUserInput(bean)) {
                request.setAttribute("bean", bean);
                request.setAttribute("cardnum", cardnum);
                request.setAttribute("orderId", orderId);
                request.setAttribute("errorMsg", errorMessage);

                request.setAttribute("date", ft.format(date));

                if (cardnum != null && cardnum.length() > 0) {

                    cardbean = manager.getCardDetails(cardnum);

                    if (cardbean.getCardnum() != null) {
                        request.setAttribute("cardexist", "yes");
                        request.setAttribute("cardbean", cardbean);
                        request.setAttribute("cardnum", cardnum);
                    } else {
                        request.setAttribute("cardexist", "no");
                        request.setAttribute("errorMsg", MessageVarList.STANDING_ORDER_CARD_NUMBER_INVALID);
                        request.setAttribute("cardnum", cardnum);
                    }

                }
                //rd = request.getRequestDispatcher(url);

            } else if (paramet.equals("add")) {

                if (!this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    throw new AccessDeniedException();
                }

                this.setAudittraceValue(request);
                int success = -1;
                success = manager.addNewStandingOrder(systemAuditBean, bean);
                if (success > 0) {
                    request.setAttribute(MessageVarList.JSP_SUCCESS, "Successfully added card standing order " + bean.getStandingOrderId());
                    //rd = request.getRequestDispatcher("/LoadCardStandingOrderServlet");
                    rd = getServletContext().getRequestDispatcher("/LoadCardStandingOrderServlet");
                    rd.include(request, response);
                }
            }


            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
            rd = request.getRequestDispatcher(url);
            /////////////

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
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

    private void setValuesToBean(HttpServletRequest request) throws Exception {
        try {
            bean = new CardStandingOrderBean();

            bean.setStandingOrderId(request.getParameter("orderId"));
            bean.setCardNumber(cardnum);
            bean.setOrderTypeId(request.getParameter("orderType"));
//            bean.setAmmount(request.getParameter("amount"));
//            bean.setCurrencyType(request.getParameter("currType"));
            bean.setStartDate(request.getParameter("startDate"));
            bean.setEndDate(request.getParameter("endDate"));
            bean.setPayDay(request.getParameter("payDate"));
            bean.setFrequency(request.getParameter("frequency"));
            bean.setNextDate(request.getParameter("initPayDate"));
            bean.setDescription(request.getParameter("remark"));
            bean.setStatus(request.getParameter("status"));
            bean.setAccNum(request.getParameter("accNum").trim());

            bean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());


        } catch (Exception e) {
            throw e;
        }
    }

    public boolean validateUserInput(CardStandingOrderBean bean) throws Exception {
        boolean isValidate = true;
        String str0 = "";
        String str1 = "";
        String str2 = "";
//        Date enddate = null;
//        Date expdate = null;

        if (bean.getEndDate().length() > 0 && cardbean.getExpDate() != null) {
            str0 = bean.getEndDate();            
            str1 = str0.substring(2, 4) + str0.substring(5, 7);
            str2 = cardbean.getExpDate();
            
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            DateFormat df2 = new SimpleDateFormat("yyMM");
//            enddate = df.parse(str1);
//            expdate = df2.parse(str2);
        }


        try {

            if (bean.getCardNumber().contentEquals("") || bean.getCardNumber().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.STANDING_ORDER_CARD_NUMBER_EMPTY;
            } else if (bean.getOrderTypeId().contentEquals("") || bean.getOrderTypeId().length() > 6) {
                isValidate = false;
                errorMessage = MessageVarList.STANDING_ORDER_TYPE_EMPTY;
//            } else if (bean.getAmmount().contentEquals("") || bean.getAmmount().length() <= 0) {
//                isValidate = false;
//                errorMessage = "Amount empty";
//            } else if (!UserInputValidator.isDecimalOrNumeric(bean.getAmmount(), "7", "2")) {
//                isValidate = false;
//                errorMessage = "Invalid amount";
//            } else if (Double.valueOf(bean.getAmmount()) > cardbean2.getMax()) {
//                isValidate = false;
//                errorMessage = "Amount exceeds maximum ammount";
//            } else if (Double.valueOf(bean.getAmmount()) < cardbean2.getMin()) {
//                isValidate = false;
//                errorMessage = "Amount below minimum amount";
//            } else if (bean.getCurrencyType().contentEquals("") || bean.getCurrencyType().length() <= 0) {
//                isValidate = false;
//                errorMessage = "Currency type empty";
            }else if(bean.getAccNum().trim().contentEquals("") || bean.getAccNum().length()<=0){
                isValidate=false;
                errorMessage=MessageVarList.STANDING_ORDER_ACCOUNT_NUMBER_EMPTY;
            }else if(!UserInputValidator.isAlphaNumeric(bean.getAccNum())){
                isValidate=false;
                errorMessage=MessageVarList.STANDING_ORDER_ACCOUNT_NUMBER_INVALID;
            } else if (bean.getEndDate().contentEquals("") || bean.getEndDate().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.STANDING_ORDER_END_DATE_EMPTY;
            } else if (Integer.valueOf(str2) < Integer.valueOf(str1)) {
            //else if (expdate.before(enddate)) {
                isValidate = false;
                errorMessage = MessageVarList.STANDING_ORDER_END_DATE_INVALID;
            } else if (bean.getPayDay().contentEquals("") || bean.getPayDay().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.STANDING_ORDER_PAY_DATE_EMPTY;
            } else if (bean.getFrequency().contentEquals("") || bean.getFrequency().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.STANDING_ORDER_FREQUENCY_EMPTY;
            } else if (bean.getNextDate().contentEquals("") || bean.getNextDate().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.STANDING_ORDER_INITIAL_PAYMENT_DATE_EMPTY;
            } else if (bean.getStatus().contentEquals("") || bean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.STANDING_ORDER_STATUS_EMPTY;
            } else if (bean.getDescription().length() > 0 && !UserInputValidator.isDescription(bean.getDescription())) {
                isValidate = false;
                errorMessage = MessageVarList.STANDING_ORDER_REMARK_INVALID;
            }

        } catch (Exception ex) {
            isValidate = false;
        }
        return isValidate;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("orderId");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Add card standing order. Standing order ID: " + uniqueId + "; by:  " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.STANDING_ORDER);
            systemAuditBean.setPageCode(PageVarList.CARD_STADING_ORDER);
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

    private void setAudittraceValueToUpdate(HttpServletRequest request, String newV, String oldV) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("orderId");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Update card standing order. Standing order ID: " + uniqueId + "; by:  " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.STANDING_ORDER);
            systemAuditBean.setPageCode(PageVarList.CARD_STADING_ORDER);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue(oldV);
            systemAuditBean.setNewValue(newV);

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
