/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.securityquesmgt.servlet;

import com.epic.cms.admin.controlpanel.securityquesmgt.bean.SecurityQuestionBean;
import com.epic.cms.admin.controlpanel.securityquesmgt.businesslogic.SecurityQuestionManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * to load the table values according to the card category
 * @author nisansala
 */
public class SetTableNameDropDownServlet extends HttpServlet {

    //initializing variables
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    HashMap<String, String> tables;
    List<SecurityQuestionBean> questionList = new ArrayList<SecurityQuestionBean>();
    private SecurityQuestionBean questionBean;
    SecurityQuestionManager securityQuesMgr;
    private String url = "/administrator/controlpanel/securityquesmgt/securityquestionmgt.jsp";

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
              
        try {
            //call to existing session
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
            try {
                securityQuesMgr = new SecurityQuestionManager();
                //retrieve tables according to the category
                tables = securityQuesMgr.tablesToCardCategory(request.getParameter("id"));
                //set operation type
                if (request.getParameter("opType").equals("update")) {
                    request.setAttribute("operationtype", "update");
                } else if(request.getParameter("opType").equals("add")) {
                    request.setAttribute("operationtype", "add");
                }
                try {
                    //assign user input to the bean
                    setUserInputToBean(request);
                    
                    this.getAllSecurityQuestions();
                    request.setAttribute("questionList", questionList);
                    request.setAttribute("tableList", tables);
                    request.setAttribute("questionBean", questionBean);
                                        
                } catch (Exception e) {
                    
                    request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);                    
                }
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
                
                
            } catch (Exception e) {
            }
        } finally {
        }
    }
    /**
     * to set user inputs to the bean
     * @param request
     * @return
     * @throws Exception 
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            questionBean = new SecurityQuestionBean();
            
            questionBean.setQuestionNo(request.getParameter("questionid").trim());
            questionBean.setQuestion(request.getParameter("question").trim());
            questionBean.setPriorityLevel(request.getParameter("prioritycode"));
            questionBean.setIssueAcquireStatus(request.getParameter("type"));
            questionBean.setQuestionType(request.getParameter("qtype"));
            questionBean.setCardCategory(request.getParameter("cardcategory"));
            //questionBean.setTableName(request.getParameter("tablename"));
            //questionBean.setField1(request.getParameter("field1"));
            //questionBean.setField2(request.getParameter("field2"));
            //questionBean.setField3(request.getParameter("field3"));
            //questionBean.setField4(request.getParameter("field4"));
            questionBean.setStatus(request.getParameter("status"));
                        
        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }
    /**
     * to get all the security questions
     * @throws Exception 
     */
    private void getAllSecurityQuestions() throws Exception {
        try {
            
            questionList = new ArrayList<SecurityQuestionBean>();
            securityQuesMgr = new SecurityQuestionManager();
            questionList = securityQuesMgr.getAllSecurityQuestions();
        } catch (Exception e) {
            throw e;
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SetTableNameDropDownServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SetTableNameDropDownServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
