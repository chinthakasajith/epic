/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.callcentersearch.servlet;

import com.epic.cms.admin.controlpanel.securityquesmgt.bean.SecurityQuestionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.callcenter.callcentersearch.bean.CustomerSearchBean;
import com.epic.cms.callcenter.callcentersearch.bean.MerchantSearchBean;
import com.epic.cms.callcenter.callcentersearch.bean.QuestionAnswerBean;
import com.epic.cms.callcenter.callcentersearch.businesslogic.CallCenterMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author janaka_h
 */
public class LoadAcquireQuestionVerifyPageServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CallCenterMgtManager manager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    List<QuestionAnswerBean> answerBeanList;
    List<QuestionAnswerBean> fiveAnswerBeanList;
    List<QuestionAnswerBean> advancedAnswerBeanList;
    private String url = "/aquirecallcenter/acquirequestionpoolhome.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
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




            try {
                //set page code and task codes
                String pageCode = PageVarList.AQCALLCENTERSEARCH;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    String questionType = "ACQ";
//                    String cardCategory = "M";
                    String questionCategory = "";
                    String customerId = "";
                    String accountNo = "";
                    String mid = "";
                    String tid = "";
                    
                    MerchantSearchBean searchbean = new MerchantSearchBean();
                    searchbean.setCustomerID(request.getParameter("customerid"));
                    searchbean.setAccountNo(request.getParameter("account"));
                    searchbean.setTid(request.getParameter("tid"));
                    searchbean.setMid(request.getParameter("mid"));
                    
                    
                    //genarate call log ID

                    sessionVarlist.setMerchantSearchBean(searchbean);

                    customerId = this.isCustomerExsists(searchbean);//houold change

                    if (!customerId.equals("")) {
                        questionCategory = "U";
                        this.getQuestionList(questionType, questionCategory, customerId,"customerId");
                        fiveAnswerBeanList = new ArrayList<QuestionAnswerBean>();
                        for (int b = 0; b < 5; b++) {


                            if (answerBeanList != null && !answerBeanList.isEmpty()) {

                                Random random = new Random();
                                int r = random.nextInt(answerBeanList.size());
                                fiveAnswerBeanList.add(answerBeanList.get(r));

                                answerBeanList.remove(r);
                            }
                        }
                        request.setAttribute("customerId", customerId);
                        request.setAttribute("answerBeanList", answerBeanList);
                        request.setAttribute("fiveAnswerBeanList", fiveAnswerBeanList);

                    } else {

                        mid = this.isMidExsists(searchbean);

                        if (!mid.equals("")) {
                            questionCategory = "L";
                            this.getQuestionList(questionType,  questionCategory, mid,"mid");
                            fiveAnswerBeanList = new ArrayList<QuestionAnswerBean>();
                            for (int b = 0; b < 5; b++) {
                                if (answerBeanList != null && !answerBeanList.isEmpty()) {

                                    Random random = new Random();
                                    int r = random.nextInt(answerBeanList.size());
                                    fiveAnswerBeanList.add(answerBeanList.get(r));

                                    answerBeanList.remove(r);
                                }
                            }
                            request.setAttribute("mid", mid);
                            request.setAttribute("answerBeanList", answerBeanList);
                            request.setAttribute("fiveAnswerBeanList", fiveAnswerBeanList);
                        } else {
                            tid = this.isTidExsists(searchbean);


                            if (!tid.equals("")) {
                                questionCategory = "T";
                                this.getQuestionList(questionType,  questionCategory, tid,"tid");
                                fiveAnswerBeanList = new ArrayList<QuestionAnswerBean>();
                                for (int b = 0; b < 5; b++) {
                                    if (answerBeanList != null && !answerBeanList.isEmpty()) {

                                        Random random = new Random();
                                        int r = random.nextInt(answerBeanList.size());
                                        fiveAnswerBeanList.add(answerBeanList.get(r));

                                        answerBeanList.remove(r);
                                    }
                                }
                                request.setAttribute("tid", tid);
                                request.setAttribute("answerBeanList", answerBeanList);
                                request.setAttribute("fiveAnswerBeanList", fiveAnswerBeanList);
                            }

                        }

                    }

                    request.setAttribute("searchbean", searchbean);
                    rd = request.getRequestDispatcher(url);

                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }


            } catch (AccessDeniedException adex) {
                throw adex;

            }



        }//catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);


        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.ERROR_IN_QUESTION);
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

            //get all tasks for userrole for this page
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

    private String isCustomerExsists(MerchantSearchBean searchbean) {
        String customerID = "";
        try {
            manager = new CallCenterMgtManager();
            customerID = manager.isMerchantCustomerExsists(searchbean);

        } catch (Exception ex) {
        }
        return customerID;
    }

    private String isMidExsists(MerchantSearchBean searchbean) {
        String mid = "";
        try {
            manager = new CallCenterMgtManager();
            mid = manager.isMidExsists(searchbean);

        } catch (Exception ex) {
        }
        return mid;
    }

    private String isTidExsists(MerchantSearchBean searchbean) {
      String tid = "";
        try {
            manager = new CallCenterMgtManager();
            tid = manager.isTidExsists(searchbean);

        } catch (Exception ex) {
        }
        return tid;
    }

    private void getQuestionList(String questionType, String questionCategory, String id, String idType) {
        try {
            manager = new CallCenterMgtManager();
            answerBeanList = manager.getAcquireQuestionList(questionType,  questionCategory, id, idType);

        } catch (Exception ex) {
        }
    }
}
