/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.callcentersearch.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.callcenter.callcentersearch.bean.CallLogBean;
import com.epic.cms.callcenter.callcentersearch.bean.CustomerSearchBean;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class LoadQuestionVerifyPageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
    private String url = "/callcenter/callcentersearch/questionpoolhome.jsp";
    private CallLogBean logBean = null;
    private String serachtype, searchvalue, idtype, idnumber;

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
                String pageCode = PageVarList.CALLCENTERSEARCH;
                String taskCode = TaskVarList.SEARCH;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    String questionType = "ISS";
                    String questionCategory = "";
                    String cardNumber = "";
                    String applicationId = "";
                    CustomerSearchBean searchbean = new CustomerSearchBean();

                    serachtype = request.getParameter("serachtype");
                    searchvalue = request.getParameter("searchvalue").trim();
                    idtype = request.getParameter("idtype");
                    idnumber = request.getParameter("idnumber").trim();

                    if (searchvalue.isEmpty() && idnumber.isEmpty()) {
                        request.setAttribute("errorMsg", MessageVarList.ALL_FIELDS_EMPTY);
                        rd = request.getRequestDispatcher("/callcenter/callcentersearch/customersearchbasicview.jsp");

                    } else if (!searchvalue.isEmpty() && serachtype == null) {
                        request.setAttribute("errorMsg", MessageVarList.SEARCH_TYPE_EMPTY);
                        rd = request.getRequestDispatcher("/callcenter/callcentersearch/customersearchbasicview.jsp");

                    } else if (!idnumber.isEmpty() && idtype == null) {
                        request.setAttribute("errorMsg", MessageVarList.ID_TYPE_EMPTY);
                        rd = request.getRequestDispatcher("/callcenter/callcentersearch/customersearchbasicview.jsp");

                    } else {

                        searchbean.setSearchtype(serachtype);
                        searchbean.setSearchvalue(searchvalue);
                        searchbean.setIdtype(idtype);
                        searchbean.setIdnumber(idnumber);

                        searchbean.setCardnumber("");
                        searchbean.setAccount("");
                        searchbean.setNic("");
                        searchbean.setPassport("");
                        searchbean.setCif("");

                        if (!searchvalue.isEmpty()) {

                            if (serachtype.equals("card")) {
                                searchbean.setCardnumber(searchvalue);

                            } else if (serachtype.equals("account")) {
                                searchbean.setAccount(searchvalue);
                            }

                        }

                        if (!idnumber.isEmpty()) {

                            if (idtype.equals("nic")) {
                                searchbean.setNic(idnumber);

                            } else if (idtype.equals("cif")) {
                                searchbean.setCif(idnumber);

                            } else if (idtype.equals("passport")) {
                                searchbean.setPassport(idnumber);
                            }
                        }

//                        searchbean.setCardnumber(request.getParameter("card"));
//                        searchbean.setAccount(request.getParameter("account"));
//                        searchbean.setNic(request.getParameter("nic"));
//                        searchbean.setPassport(request.getParameter("passport"));
//                        searchbean.setLicence(request.getParameter("licence"));
                        //call log ID genarate
                        manager = new CallCenterMgtManager();

                        String day = null;
                        String oldDdigit = null;
                        String newDigit = null;
                        String logId = null;
                        String todayString = null;
                        Date today = null;
                        String oldId = manager.getMaxCallLogId();

                        SimpleDateFormat df = new SimpleDateFormat("yyMMdd");

                        today = new Date();
                        //  Date dayday = df.parse(day);

                        todayString = df.format(today);

                        if (oldId != null) {
                            day = oldId.substring(0, 6);
                            oldDdigit = oldId.substring(6);
                            newDigit = String.valueOf(Integer.parseInt(oldDdigit) + 1);

                        } else {
                            day = todayString;
                            newDigit = "0001";
                        }

                        if (newDigit.length() == 1) {
                            newDigit = "000" + newDigit;
                        } else if (newDigit.length() == 2) {
                            newDigit = "00" + newDigit;
                        } else if (newDigit.length() == 3) {
                            newDigit = "0" + newDigit;
                        }

                        if (day.equals(todayString)) {
                            logId = day + newDigit;

                        } else {
                            logId = todayString + "0001";
                        }

                        //set search key
                        String searchKey = "";

                        if (!searchvalue.isEmpty()) {
                            if (searchKey.equals("")) {
                                searchKey = searchvalue;
                            } else {
                                searchKey += "|" + searchvalue;
                            }
                        }
                        if (!idnumber.isEmpty()) {
                            if (searchKey.equals("")) {
                                searchKey = idnumber;
                            } else {
                                searchKey += "|" + idnumber;
                            }
                        }

                        //set values to call log bean
                        this.setCallLogBean(searchKey, logId);
                        //insert call log
                        int i = manager.insertCallLog(logBean);

                        if (i > 0) {
                            sessionVarlist.setCallLogId(logId);
                        }

                        // end call log                
                        sessionVarlist.setCustomerSearchBean(searchbean);

                        cardNumber = this.isCardExsists(searchbean);

                        if (!cardNumber.equals("")) {
                            questionCategory = "C";
                            this.getQuestionList(questionType, questionCategory, cardNumber, applicationId);
                            fiveAnswerBeanList = new ArrayList<QuestionAnswerBean>();
                            for (int b = 0; b < 5; b++) {

                                if (answerBeanList != null && !answerBeanList.isEmpty()) {

                                    Random random = new Random();
                                    int r = random.nextInt(answerBeanList.size());
                                    fiveAnswerBeanList.add(answerBeanList.get(r));

                                    answerBeanList.remove(r);
                                }
                            }
                            request.setAttribute("cardNumber", cardNumber);
                            request.setAttribute("answerBeanList", answerBeanList);
                            request.setAttribute("fiveAnswerBeanList", fiveAnswerBeanList);

                        } else {

                            applicationId = this.isApplicationsExsists(searchbean);

                            if (!applicationId.equals("")) {
                                questionCategory = "A";
                                this.getQuestionList(questionType, questionCategory, cardNumber, applicationId);
                                fiveAnswerBeanList = new ArrayList<QuestionAnswerBean>();
                                for (int b = 0; b < 5; b++) {
                                    if (answerBeanList != null && !answerBeanList.isEmpty()) {

                                        Random random = new Random();
                                        int r = random.nextInt(answerBeanList.size());
                                        fiveAnswerBeanList.add(answerBeanList.get(r));

                                        answerBeanList.remove(r);
                                    }
                                }
                                request.setAttribute("applicationId", applicationId);
                                request.setAttribute("answerBeanList", answerBeanList);
                                request.setAttribute("fiveAnswerBeanList", fiveAnswerBeanList);
                            } else {
                            }
                        }
                        request.setAttribute("searchbean", searchbean);
                        rd = request.getRequestDispatcher(url);
                    }

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

    private void setCallLogBean(String searchKey, String logId) throws Exception {

        try {
            logBean = new CallLogBean();

            logBean.setCallLogId(logId);

//            //set search key
//            String searchKey = "";
//
//            if (searchbean.getCardnumber() != null) {
//                if (searchKey.equals("")) {
//                    searchKey = searchbean.getCardnumber();
//                } else {
//                    searchKey += "|" + searchbean.getCardnumber();
//                }
//            }
//            if (!searchbean.getAccount().equals("")) {
//                if (searchKey.equals("")) {
//                    searchKey = searchbean.getAccount();
//                } else {
//                    searchKey += "|" + searchbean.getAccount();
//                }
//            }
//            if (!searchbean.getNic().equals("")) {
//                if (searchKey.equals("")) {
//                    searchKey = searchbean.getNic();
//                } else {
//                    searchKey += "|" + searchbean.getNic();
//                }
//            }
//            if (!searchbean.getPassport().equals("")) {
//                if (searchKey.equals("")) {
//                    searchKey = searchbean.getPassport();
//                } else {
//                    searchKey += "|" + searchbean.getPassport();
//                }
//            }
//            if (!searchbean.getCif().equals("")) {
//                if (searchKey.equals("")) {
//                    searchKey = searchbean.getCif();
//                } else {
//                    searchKey += "|" + searchbean.getCif();
//                }
//            }
            logBean.setSearchKey(searchKey);
            logBean.setCallerType("CUSTOMER");
            logBean.setReferType("PHONE");
            logBean.setReferNo("number");
            logBean.setLastUpdatedUser(sessionUser.getUserName());

        } catch (Exception e) {
            throw e;
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

    private void getQuestionList(String questionType, String questionCategory, String cardNumber, String applicationId) {
        try {
            manager = new CallCenterMgtManager();
            answerBeanList = manager.getQuestionList(questionType, questionCategory, cardNumber, applicationId);

        } catch (Exception ex) {
        }
    }

    private String isCardExsists(CustomerSearchBean searchbean) {
        String card = "";
        try {
            manager = new CallCenterMgtManager();
            card = manager.isCardExsists(searchbean);

        } catch (Exception ex) {
        }
        return card;
    }

    private String isApplicationsExsists(CustomerSearchBean searchbean) {
        String application = "";
        try {
            manager = new CallCenterMgtManager();
            application = manager.isApplicationsExsists(searchbean);

        } catch (Exception ex) {
        }
        return application;
    }
}
