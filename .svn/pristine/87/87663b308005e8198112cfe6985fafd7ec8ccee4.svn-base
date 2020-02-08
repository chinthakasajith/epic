/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.recovery.callcenter.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.callcenter.callcentersearch.bean.QuestionAnswerBean;
import com.epic.cms.recovery.callcenter.businesslogic.RecoveryCallCenterManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chanuka
 */
public class CallRecoveryCustomerServlet extends HttpServlet {

    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private String memo = "";
    private String param = "";
    private List<QuestionAnswerBean> answerBeanList = null;
    private SystemAuditBean systemAuditBean;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            /////////////////////////////////////////////////////////////////////
            HttpSession sessionObj = request.getSession(false);
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


            memo = request.getParameter("memo");
            param = request.getParameter("param");

            String collectionId = sessionVarlist.getCollectionId();
            String callerId = this.setCollectionCallerId();


            if (param != null && param.equals("verify")) {

                if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.VERIFY)) {
                    throw new AccessDeniedException();
                }

                this.setmemo();
                this.setAudittraceValue(request, callerId, TaskVarList.VERIFY);

            } else if (param != null && param.equals("reject")) {

                if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.REJECT)) {
                    throw new AccessDeniedException();
                }
                //this.setmemo();
                this.setAudittraceValue(request, callerId, TaskVarList.REJECT);
            }


            if (memo != null && !memo.isEmpty()) {



//
//
                RecoveryCallCenterManager.getInstance().updateCollectionStatus("BMCL", collectionId);
                RecoveryCallCenterManager.getInstance().insertCollectionMemo(callerId, collectionId, memo, sessionUser.getUserName(), systemAuditBean);
//
//            
//            sessionVarlist.setRecoveryAnswerBeanList(answerBeanList);
//            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                out.print("success");

            } else {

                out.print("errorvalidate");
            }








        } //catch session exception
        catch (SesssionExpException sex) {
            out.print("session");
        } //catch session exception
        catch (NewLoginSessionException nlex) {
            out.print("session");
        } catch (Exception ex) {
            out.print("error");
        } finally {
            out.close();
        }
    }

    private String setCollectionCallerId() throws Exception {

        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");

        Date today = new Date();
        //  Date dayday = df.parse(day);

        String today1 = df.format(today);

        return today1;

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

    private void setmemo() throws Exception {

        try {

            if (memo != null && !memo.isEmpty()) {
                memo = memo + ",";
            }
            answerBeanList = sessionVarlist.getRecoveryAnswerBeanList();
            for (int i = 0; i < answerBeanList.size(); i++) {
                memo = memo + answerBeanList.get(i).getQuestion() + " | " + answerBeanList.get(i).getAnswer() + " , ";
            }


        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setAudittraceValue(HttpServletRequest request, String callerId, String task) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = callerId;

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            if (task.equals(TaskVarList.VERIFY)) {
                systemAuditBean.setDescription("Verify caller, caller id: '" + uniqueId + "' by: " + sessionVarlist.getCMSSessionUser().getUserName());
            } else if (task.equals(TaskVarList.REJECT)) {
                systemAuditBean.setDescription("Reject caller, caller id: '" + uniqueId + "' by: " + sessionVarlist.getCMSSessionUser().getUserName());
            }

            systemAuditBean.setApplicationCode(ApplicationVarList.COLLECTION_RECOVERY);
            systemAuditBean.setSectionCode(SectionVarList.RECOVERY_CALL_CENTER);
            systemAuditBean.setPageCode(PageVarList.RECOVERY_CALL_CENTER);
            systemAuditBean.setTaskCode(task);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks(uniqueId);
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
