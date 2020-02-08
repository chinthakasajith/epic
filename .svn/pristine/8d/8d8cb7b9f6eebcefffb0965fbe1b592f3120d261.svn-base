/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.payment.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.backoffice.payment.bean.ChequeBean;
import com.epic.cms.backoffice.payment.businesslogic.ChequeRealiseManager;
import com.epic.cms.backoffice.payment.businesslogic.PaymentManager;
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
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.taglibs.standard.lang.jstl.test.Bean2;

/**
 *
 * @author badrika
 */
public class LoadChequeRealizeServlet extends HttpServlet {
    private String url = "/backoffice/payment/chequereturnandrealize.jsp";
    private String url2 = "/backoffice/payment/chequeprocess.jsp";
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private PaymentManager manager;
    private List<CurrencyBean> currencyList;
    private HashMap<String, String> banklist;
    private ChequeRealiseManager crManager;
    private HashMap<String, String> statusList;
    private String param = null;
    private ChequeBean chBean;
    private String errorMessage;
    private List<ChequeBean> cheqList;

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
            //request.setAttribute("operationtype", "fill");
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
                    String pageCode = PageVarList.BO_PAY_CHEQUE_REALISE;
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

            /////////////////

            manager = new PaymentManager();
            crManager = new ChequeRealiseManager();
            
            currencyList = manager.getAllCurrencyLst();
            request.setAttribute("currencyList", currencyList);

            banklist = manager.getAllBankList();
            request.setAttribute("banklist", banklist);
            
            statusList = crManager.getStatusList();
            request.setAttribute("statusList", statusList);
            
            rd = request.getRequestDispatcher(url);
                        
            param = request.getParameter("param");            
            
            
            if(param != null && param.equals("search")){
                
                if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.SEARCH)) {
                    throw new AccessDeniedException();
                }  
                
                chBean = new ChequeBean();

                chBean.setCheqNum(request.getParameter("cheqNum"));
                chBean.setCheqBank(request.getParameter("cheqBank"));
                chBean.setPayDateFrom(request.getParameter("frmDate"));
                chBean.setPayDateTo(request.getParameter("toDate"));
                chBean.setCurType(request.getParameter("curType"));
                chBean.setAmount(request.getParameter("amount"));
                chBean.setStatus(request.getParameter("status"));
                
                sessionVarlist.setChequeSearchBean(chBean);
                
                if (chBean.getCheqNum().length() > 0 && !UserInputValidator.isCorrectString(chBean.getCheqNum())) {
                    errorMessage = "Invalid cheque number";
                    request.setAttribute("chBean", chBean);
                    request.setAttribute(MessageVarList.JSP_ERROR, errorMessage);
                    rd = request.getRequestDispatcher(url);
                    
                } else if (chBean.getAmount().length() > 0 && !UserInputValidator.isDecimalOrNumeric(chBean.getAmount(), "20", "2")) {

                    errorMessage = "Invalid amount";
                    request.setAttribute("chBean", chBean);
                    request.setAttribute(MessageVarList.JSP_ERROR, errorMessage);
                    rd = request.getRequestDispatcher(url);
                } else{
                    cheqList = crManager.searchCheque(chBean);
                    sessionVarlist.setChequeList(cheqList);
                   // request.setAttribute("cheqList", cheqList);
                    request.setAttribute("chBean", chBean); 
                    rd = request.getRequestDispatcher(url);
                }
                
            
            }  
            
            if (param != null && param.equals("inSearch")) {
                chBean = new ChequeBean();
                chBean = sessionVarlist.getChequeSearchBean();
                cheqList = crManager.searchCheque(chBean);
                sessionVarlist.setChequeList(cheqList);
                // request.setAttribute("cheqList", cheqList);
                request.setAttribute("chBean", chBean);
                rd = request.getRequestDispatcher(url);

            }
            
            if(param != null && param.equals("process")){
                
                //cheqnum
                String cheqnum = request.getParameter("cheqnum");
                ChequeBean bean2 = null;
                               
                for (ChequeBean bean1 : cheqList) {
                    if(bean1.getCheqNum().equals(cheqnum)){
                        bean2 = bean1;
                    }
                }
                if(bean2 != null){
                    request.setAttribute("bean2", bean2); 
                    rd = request.getRequestDispatcher(url2);
                }            
            
            }
            
            if (param != null && param.equals("realise")) {
                String checnum = request.getParameter("cheqnum");
                int success = -1;
                success = crManager.chequeRealiseOrReturn(checnum, param);
                
                if(success > 0){
                    request.setAttribute(MessageVarList.JSP_SUCCESS, "Checque number " + checnum + " cheque realised");
                }
                
                rd = request.getRequestDispatcher("/LoadChequeRealizeServlet?param=inSearch");
            }

            if (param != null && param.equals("return")) {
                
                String checnum = request.getParameter("cheqnum");
                int success = -1;
                success = crManager.chequeRealiseOrReturn(checnum, param);
                
                if(success > 0){
                    request.setAttribute(MessageVarList.JSP_SUCCESS, "Checque number " + checnum + " cheque returned");
                }
                rd = request.getRequestDispatcher("/LoadChequeRealizeServlet?param=inSearch");
            }
            
            

            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
            /////////////

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
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
