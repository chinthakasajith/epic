///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.epic.cms.callcenter.card.servlet;
//
//import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
//import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
//import com.epic.cms.callcenter.callcentersearch.bean.CallHistoryBean;
//import com.epic.cms.callcenter.card.bean.CardBean;
//import com.epic.cms.callcenter.card.businesslogic.CardActivationManager;
//import com.epic.cms.cpmm.requestconfirmation.bean.RequestConfirmationBean;
//import com.epic.cms.system.util.exception.AccessDeniedException;
//import com.epic.cms.system.util.exception.NewLoginSessionException;
//import com.epic.cms.system.util.exception.SesssionExpException;
//import com.epic.cms.system.util.session.SessionUser;
//import com.epic.cms.system.util.session.SessionVarList;
//import com.epic.cms.system.util.session.SessionVarName;
//import com.epic.cms.system.util.validate.UserInputValidator;
//import com.epic.cms.system.util.variable.ApplicationVarList;
//import com.epic.cms.system.util.variable.MessageVarList;
//import com.epic.cms.system.util.variable.OracleMessage;
//import com.epic.cms.system.util.variable.PageVarList;
//import com.epic.cms.system.util.variable.SectionVarList;
//import com.epic.cms.system.util.variable.StatusVarList;
//import com.epic.cms.system.util.variable.TaskVarList;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.SQLException;
//import java.util.List;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// *
// * @author nalin
// */
//public class ProceedCardActivationServlet extends HttpServlet {
//
//    private RequestDispatcher rd;
//    private SessionVarList sessionVarlist = null;
//    private SystemAuditBean systemAuditBean = null;
//    private SessionUser sessionUser = null;
//    private SystemUserManager systemUserManager = null;
//    private List<String> userTaskList;
//    private CardActivationManager cardManager = null;
//    private CardBean cardBean = null;
////    private RequestConfirmationBean cardBean = null;
//    private boolean successActivation = false;
//    private int reject = -1;
//    private String section = "CCCARD";
////    private String url = "/callcenter/card/cardactivationhome.jsp";
//    private String url = "/cpmm/requestconfirm/requestapprovehome.jsp";
//    private CallHistoryBean callhistoryBean;
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        try {
//            try {
//                request.setAttribute("operationtype", "LOAD");
//                HttpSession sessionObj = request.getSession(false);
//                systemUserManager = new SystemUserManager();
//                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
//                sessionUser = sessionVarlist.getCMSSessionUser();
//
//                try {
//
//                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
//                        throw new NewLoginSessionException();
//                    }
//
//                } catch (NewLoginSessionException nlex) {
//                    throw new NewLoginSessionException();
//
//                }
//
//                try {
//                    //set page code and task codes
//                    String pageCode = PageVarList.REQUESTAPPROVE;
//                    String taskCode = TaskVarList.ACTIVATION;
//
//                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
//                    } else {
//                        throw new AccessDeniedException();
//
//                    }
//
//                    sessionVarlist.setUserPageTaskList(userTaskList);
//
//                } catch (AccessDeniedException adex) {
//                    throw adex;
//
//                }
//            } catch (NullPointerException ex) {
//                throw new SesssionExpException();
//            }
//
//            ///////////////////////////////////////////////
//            setUserInputToBean(request);
//            String operation = request.getParameter("operation");
//            String appStatus = request.getParameter("approveStatus");
//
////            if (UserInputValidator.isAlphaNumeric(cardBean.getRemark())) {
//            this.setAudittraceValue(request);
//            this.setCallHistoryRecord(request);
//            try {
//                cardManager = new CardActivationManager();
//
//                if (appStatus != null && appStatus.equals("REJ")) {
//
//                    if (cardBean.getRemark().isEmpty()) {
//                        request.setAttribute("errorMsg", " " + MessageVarList.REMARKS_EMPTY);
//                        request.setAttribute("operationtype", "update");
//                        request.setAttribute("operation", "ACTI");
////                        request.setAttribute("cardBean", cardBean);
////                        request.setAttribute("cardNumber", cardBean.getCardNumber());
////                        request.setAttribute("reasonCode", "ACTI");
//                        rd = getServletContext().getRequestDispatcher(url);
//                        rd.forward(request, response);
//                    } else {
//                        reject = cardManager.requsetReject(cardBean, operation);
//                    }
//
//                } else {
//
//                    if (cardBean.getCardStatus().equals(StatusVarList.BLOCK_STATUS)) {
//
//                        cardBean = cardManager.getCardBlockDetails(cardBean);
//                        successActivation = cardManager.blockCardActivate(cardBean, systemAuditBean);//,callhistoryBean
//
//                    } else {
//                        successActivation = cardManager.cardActivate(cardBean, systemAuditBean);//,callhistoryBean
//
//                    }
//
//                    if (successActivation) {
//
//                        successActivation = cardManager.updateRequset(cardBean, operation);
//                    }
//                }
//
//                if (successActivation) {
//
////                        request.setAttribute("successMsg", "Card" + " " + cardBean.getCardNumber() + " " + "is Activated");
////                        rd = getServletContext().getRequestDispatcher("/ViewCustomerMgtServlet?section=" + section);
//                    request.setAttribute("successMsg", " " + MessageVarList.SUCCESS_APPROVE);
//                    rd = getServletContext().getRequestDispatcher("/LoadRequestApproveServlet");
//                    rd.forward(request, response);
//                    request.setAttribute("operationtype", "search");
//                } else if (reject == 1) {
//                    request.setAttribute("successMsg", " " + MessageVarList.SUCCESS_REJECT);
//                    rd = getServletContext().getRequestDispatcher("/LoadRequestApproveServlet");
//                    rd.forward(request, response);
//                    request.setAttribute("operationtype", "search");
//                } else {
//                    request.setAttribute("errorMsg", " " + MessageVarList.FAIL_APPROVE);
//                    rd = getServletContext().getRequestDispatcher(url);
//                    rd.forward(request, response);
//                }
//
//            } catch (SQLException ex) {
//
//                OracleMessage message = new OracleMessage();
//                String oraMessage = message.getMessege(ex.getMessage());
//                request.setAttribute("errorMsg", oraMessage);
//                request.setAttribute("operationtype", "LOAD");
//                rd = getServletContext().getRequestDispatcher(url);
////                    rd.forward(request, response);
//            }
//
////            } else {
////                request.setAttribute("operationtype", "search");
////                request.setAttribute("cardBean", cardBean);
////                request.setAttribute("operationtype", "LOAD");
////                request.setAttribute("errorMsg", MessageVarList.REMARK_INVALID);
////                rd = getServletContext().getRequestDispatcher(url);
//////                rd.forward(request, response);
////            }
//            /////////////////////////////////////////////////
//        } catch (SesssionExpException sex) {
//            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
//                    + MessageVarList.SESSION_EXPIRED);
//
//        } catch (NewLoginSessionException nlex) {
//            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
//                    + MessageVarList.LAST_SESSION_CLOSE);
//
//        } catch (AccessDeniedException adex) {
//            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
//                    + MessageVarList.ACCESS_DENIED_TASK);
////            rd.forward(request, response);
//
//        } catch (SQLException ex) {
//            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
//            rd = request.getRequestDispatcher(url);
//        } catch (Exception ex) {
//            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
//            rd = request.getRequestDispatcher(url);
//        } finally {
//            rd.forward(request, response);
//            out.close();
//        }
//    }
//
//    public Boolean setUserInputToBean2(HttpServletRequest request) throws Exception {
//        Boolean status = true;
//        try {
//
//            cardBean = new CardBean();
//
//            cardBean.setCardNumber(request.getParameter("cardNumber").trim());
//            cardBean.setCardTypeDes(request.getParameter("cardTypeDes").trim());
//            cardBean.setCardCategory(request.getParameter("cardCatDes").trim());
//            cardBean.setExpDate(request.getParameter("expDate").trim());
//            cardBean.setNameofCard(request.getParameter("nameofCard").trim());
//            cardBean.setCreditLimit(request.getParameter("creditLimit").trim());
//            cardBean.setRemark(request.getParameter("remark").trim().trim());
//            cardBean.setCardStatus(request.getParameter("cardStatus").trim());
//
//            cardBean.setLastUpdateduser(sessionUser.getUserName());
//
//        } catch (Exception e) {
//            status = false;
//            throw e;
//
//        }
//
//        return status;
//    }
//
//    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
//        Boolean status = true;
//        try {
//
//            cardBean = new CardBean();
//
//            cardBean.setCardNumber(request.getParameter("cardno"));
//
////            cardBean.setCardTypeDes(request.getParameter("cardTypeDes").trim());
////            cardBean.setCardCategory(request.getParameter("cardCatDes").trim());
//            cardBean.setExpDate(request.getParameter("expitydate"));
////            cardBean.setNameofCard(request.getParameter("nameofCard").trim());
//            cardBean.setCreditLimit(request.getParameter("creditlimit"));
//            cardBean.setRemark(request.getParameter("remarks"));
//
//            cardBean.setCardStatus(request.getParameter("status"));
//
//            cardBean.setLastUpdateduser(sessionUser.getUserName());
//
//        } catch (Exception e) {
//            status = false;
//            throw e;
//
//        }
//
//        return status;
//    }
//
//    private void setAudittraceValue(HttpServletRequest request) throws Exception {
//
//        try {
//            systemAuditBean = new SystemAuditBean();
//            ////get unique Id from the page.It may be the primary key---------------
//            String uniqueId = cardBean.getCardNumber();
//
//            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
//            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
//            //set unique id
//            systemAuditBean.setUniqueId(uniqueId);
//            //set description 
//            systemAuditBean.setDescription("Activate " + cardBean.getCardNumber() + " Card " + sessionVarlist.getCMSSessionUser().getUserName());
//            systemAuditBean.setApplicationCode(ApplicationVarList.CALL_CENTER_APPLICATION);
//            systemAuditBean.setSectionCode(SectionVarList.TRANSACTIONMGT);
//            systemAuditBean.setPageCode(PageVarList.TXNTYPE);
//            systemAuditBean.setTaskCode(TaskVarList.ACTIVATION);
//            systemAuditBean.setIp(request.getRemoteAddr());
//            //add remarks here
//            systemAuditBean.setRemarks("");
//            //set field name which is being changed(only if)
//            systemAuditBean.setFieldName("");
//            //set old value of change if required
//            systemAuditBean.setOldValue("");
//            //set new value of change if required
//            systemAuditBean.setNewValue("");
//
//            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
//
//    private void setCallHistoryRecord(HttpServletRequest request) throws Exception {
//
//        try {
//            callhistoryBean = new CallHistoryBean();
//
//            callhistoryBean.setCallLogId(sessionVarlist.getCallLogId());
//            callhistoryBean.setOperation(TaskVarList.ACTIVATION);//task code
//            callhistoryBean.setRemarks("");
//            callhistoryBean.setCardNo(sessionVarlist.getCardNumber());
//            callhistoryBean.setApplicationId(sessionVarlist.getApplicationId());
//            callhistoryBean.setCustomerId(sessionVarlist.getCustomerId());
//            callhistoryBean.setAccountNo(sessionVarlist.getAccountId());
//            callhistoryBean.setPageCode(PageVarList.TXNTYPE);
//            callhistoryBean.setOldValue("");
//            callhistoryBean.setNewValue("");
//            callhistoryBean.setTid("");
//            callhistoryBean.setMid("");
//
//            callhistoryBean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
//
//    /**
//     *
//     * @param userrole
//     * @param pagecode
//     * @param task
//     * @return
//     * @throws Exception
//     */
//    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {
//        boolean isValidTaskAccess = false;
//
//        try {
//            systemUserManager = new SystemUserManager();
//
//            //get all tasks for userrole for this page
//            userTaskList = systemUserManager.getTasksByPageCodeAndUserRole(userrole, pagecode);
//
//            for (String usertask : userTaskList) {
//
//                if (task.equals(usertask)) {
//                    isValidTaskAccess = true;
//                }
//            }
//
//            return isValidTaskAccess;
//        } catch (Exception ex) {
//            throw ex;
//        }
//
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//}
