/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CalculateCreditScoreBean;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CalculateCreditScoreManager;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CardScoreManager;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CreateCreditScore;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CreditScManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.exception.ValidateException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
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
 * @author ayesh
 */
public class ProcessCalCreditScoreServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/camm/applicationconfig/creditscore/calculatecreditscorehome.jsp";
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private SystemUserManager sysUserOnj;
    private List<StatusBean> statusBean;
    private SystemAuditBean systemAuditBean;
    private ApplicationHistoryBean historybean = null;
    private String appID = null;
    private CardScoreManager cardScoreManager=null;
    
    
    
    
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
        request.setAttribute("operationtype", "add");
        try {
            try {
                request.setAttribute(RequestVarList.PRIORITY_LEVEL_DATA_LIST, CalculateCreditScoreManager.getInstance().getPriorityLevel());
                request.setAttribute(RequestVarList.USER_LIST, CalculateCreditScoreManager.getInstance().getSystemUsers());

                HttpSession sessionObj = request.getSession(false);
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

            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.SEARCH)) {
                throw new AccessDeniedException();
            }

//            CalculateCreditScoreBean bean = new CalculateCreditScoreBean();
//
//            bean.setApplicationID(request.getParameter("appID"));
//            bean.setPrioCode(request.getParameter("prioLevel"));
//            bean.setFromDate(request.getParameter("fromdate"));
//            bean.setToDate(request.getParameter("todate"));
//            bean.setAssignUser(request.getParameter("user"));
//
//            request.setAttribute(RequestVarList.DATA_LIST, CalculateCreditScoreManager.getInstance().getSearchList(bean));



            String getdebitArray = request.getParameter("searchArray");
            String appIDArray[] = getdebitArray.split(",");
            int finalRow = 0;

           CreateCreditScore creditScoreObj = new CreateCreditScore();
            
            
            for (int i = 0; i < appIDArray.length; i++) {
                cardScoreManager =CardScoreManager.getScoreCardInstance();
                CardApplicationBean cardApplicationBean =cardScoreManager.getCardApplicationBean(appID); 
                
                appID = appIDArray[i];
                this.setApplicationHistoryBean();
                
                int creditValue = creditScoreObj.getCreditScore(appID,cardApplicationBean);

                finalRow += CalculateCreditScoreManager.getInstance().updateCreditScoreValue(appID, creditValue,historybean);


            }


            if (finalRow == appIDArray.length * 2) {
                System.out.println("ok");
                 
                request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.APP_CREDITSCORE_CALCULATE_SUCCESS); 
            }


            rd = request.getRequestDispatcher(url);

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadCalculateCreditScoreServlet");
            rd.include(request, response);
        } catch (SQLException ex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (ValidateException ex) {
            request.setAttribute("operationtype", "add");
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("operationtype", "add");
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
            String uniqueId = request.getParameter("fieldCode");


            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("SEARCH " + uniqueId + "   " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CREDITSCOREMGT);
            systemAuditBean.setPageCode(PageVarList.CREDISCOREFIELDDIF);
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
    
     public void setApplicationHistoryBean() {
        historybean = new ApplicationHistoryBean();
        
        historybean.setApplicationId(appID);
        historybean.setApplicationLevel("CSCR");
        historybean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
        historybean.setRemarks("Credit score completed");
        historybean.setStatus(StatusVarList.HISTORY_COMPLETE);
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
