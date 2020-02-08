/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.reportexp.cardapplication.bean.ApplicationSummaryBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationSummaryManager;
import java.sql.SQLException;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.businesslogic.NumberGenerationReportManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

/**
 *
 * @author nisansala
 */
public class ApplicationSummaryReportPdfServlet extends HttpServlet {

    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String errorMessage = null;
    private SessionVarList sessionVarlist;
    private SystemUserManager systemUserManager;
    private SystemAuditBean systemAuditBean = null;
    private ApplicationSummaryManager applicationSummaryManager;
    private ApplicationSummaryBean summeryBean;
    private ApplicationSummaryBean inputBean;
    private ApplicationSummaryManager appSumMgr;
    private HashMap<String, String> bnkBranches = null;
    private HashMap<String, String> appStatusList = null;
    private HashMap<String, String> cardDomainList = null;
    private List<ApplicationSummaryBean> searchList = null;
    private String url = "/reportexp/cardapplication/applicationsummaryhome.jsp";
    private NumberGenerationReportManager numberGenerationReportManager;
    private List<String> userList;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // response.setContentType("text/html;charset=UTF-8");
        //  PrintWriter out = response.getWriter();
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
                String pageCode = PageVarList.APPLICATION_SUMMARY;
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {

                        String path = "/resources/reports/applicationreport/application_summary.jasper";


//                        if (null != request.getParameter("cardType") && !request.getParameter("cardType").equals("")) {
//                            request.setAttribute("cardType", request.getParameter("cardType"));
//
//                        }
//                        this.getAllBranchListMap();
//                        request.setAttribute("branchListMap", branchListMap);
//                        this.getAllCardProductList();
//                        if (null != request.getParameter("cardType") && !request.getParameter("cardType").equals("")) {
//                            sortcardProductList = new ArrayList<NumberGenerationReportBean>();
//                            for (NumberGenerationReportBean bean : cardProductList) {
//                                if (bean.getCardType().equals(request.getParameter("cardType"))) {
//                                    sortcardProductList.add(bean);
//                                }
//                            }
//                        } else {
//                            sortcardProductList = new ArrayList<NumberGenerationReportBean>();
//                            for (NumberGenerationReportBean bean : cardProductList) {
//                                sortcardProductList.add(bean);
//                            }
//                        }

                        this.getUserList();
                        request.setAttribute("userList", userList);

                        this.getBranchNames();
                        request.setAttribute("bnkBranchList", bnkBranches);

                        this.getCardDomains();
                        request.setAttribute("cardDomainList", cardDomainList);

                        this.getApplicationStatus();
                        request.setAttribute("appStatusList", appStatusList);

                        this.setUserInputToBean(request);
                        request.setAttribute("terminalBean", summeryBean);


                        this.getApplicationSummary(summeryBean);
                        request.setAttribute("appBean", summeryBean);

                        request.setAttribute("searchList", searchList);
                        this.getApplicationSummary(summeryBean);

                        summeryBean = new ApplicationSummaryBean();

                        if (searchList.size() > 0) {
                            summeryBean = searchList.get(0);
                        } else {
                            request.setAttribute("errorMsg", MessageVarList.EMPTY_VALUE_MSG);
                            rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                        }

                        HashMap map = new HashMap();

                        /*
                        if (null != request.getParameter("priorityLevel") && !request.getParameter("priorityLevel").equals("")) {
                        map.put("priorityLevelDes", priorityLevelMap.get(request.getParameter("priorityLevel")));
                        } else {
                        map.put("priorityLevelDes", "All");
                        }
                         */

                        if (null != request.getParameter("branch") && !request.getParameter("branch").equals("")) {
                            map.put("branchName", bnkBranches.get(request.getParameter("branch")));
                        } else {
                            map.put("branchName", "All");
                        }

                        ////////
//                        if (null != request.getParameter("branch") && request.getParameter("branch").equals("")) {
//                            map.put("branch", summeryBean.getBranchName());
//                        } else {
//                            map.put("branch", "All");
//                        }
                        if (null != request.getParameter("priority") && !request.getParameter("priority").equals("")) {
                            map.put("priorityLevelDes", summeryBean.getPriorityLevelDes());
                        } else {
                            map.put("priorityLevelDes", "All");
                        }
                        if (null != request.getParameter("domain") && !request.getParameter("domain").equals("")) {
                            map.put("domainCode", cardDomainList.get(request.getParameter("domain")));
                        } else {
                            map.put("domainCode", "All");
                        }
                        if (null != request.getParameter("status") && !request.getParameter("status").equals("")) {
                            map.put("statusDes", appStatusList.get(request.getParameter("status")));
                        } else {
                            map.put("statusDes", "All");
                        }
                        if (null != request.getParameter("fromdate") && !request.getParameter("fromdate").equals("")) {
                            map.put("fromDate", request.getParameter("fromdate"));
                        } else {
                            map.put("fromDate", " ");
                        }
                        if (null != request.getParameter("todate") && !request.getParameter("todate").equals("")) {
                            map.put("toDate", request.getParameter("todate"));
                        } else {
                            map.put("toDate", " ");
                        }

                        this.setAudittraceValue(request);
                        appSumMgr.getInstance().insertAuditValues(systemAuditBean);


                        response.setContentType("application/pdf");
                        InputStream inputStream = new FileInputStream(getServletContext().getRealPath(path));
                        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(searchList);
                        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, map, ds);

                        JRPdfExporter exp = new JRPdfExporter();
                        exp.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
                        exp.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                        exp.exportReport();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        request.setAttribute("errorMsg", MessageVarList.ERROR_APP_SUMMARY);

                    }
                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }


                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                throw adex;

            }



        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);
        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);
        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);


        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_NUMBER_GENERATION_REPORT);

        } finally {
            //    out.close();
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

    private void getApplicationSummary(ApplicationSummaryBean inputBean) throws SQLException, Exception {
        appSumMgr = new ApplicationSummaryManager();
        searchList = appSumMgr.getApplicationSummary(inputBean);
        //sessionVarlist.setTerminalDataBeanList(searchList);
    }

    public void setUserInputToBean(HttpServletRequest request) {

        summeryBean = new ApplicationSummaryBean();

        summeryBean.setBranchCode(request.getParameter("branch"));
        summeryBean.setPriorityLevelCode(request.getParameter("priority"));
        summeryBean.setDomainCode(request.getParameter("domain"));
        summeryBean.setStatusCode(request.getParameter("status"));
        summeryBean.setFromDate(request.getParameter("fromdate"));
        summeryBean.setToDate(request.getParameter("todate"));
    }

    private boolean isValidate(ApplicationSummaryBean inputBean) throws Exception {

        boolean isValidate = true;
        try {

            if (inputBean.getBranchCode().contentEquals("") || inputBean.getBranchCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BRANCH_EMPTY;

            } else if (inputBean.getPriorityLevelCode().contentEquals("") || inputBean.getPriorityLevelCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.PRIORITY_LEVEL_EMPTY;

            } else if (inputBean.getDomainCode().contentEquals("") || inputBean.getDomainCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APP_DOMAIN_EMPTY;

            } else if (inputBean.getDomainCode().contentEquals("") || inputBean.getDomainCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.APP_STATUS_EMPTY;
            }

        } catch (Exception e) {
            throw e;
        }
        return isValidate;
    }

    private String getCurrentDate() throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return dateFormat.format(date);

    }

    private void getBranchNames() {
        try {
            appSumMgr = new ApplicationSummaryManager();
            bnkBranches = appSumMgr.getBranchNames();
        } catch (Exception e) {
        }

    }

    private void getCardDomains() throws Exception {
        try {

            appSumMgr = new ApplicationSummaryManager();
            cardDomainList = appSumMgr.getCardDomains();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getApplicationStatus() throws Exception {
        try {

            appSumMgr = new ApplicationSummaryManager();
            appStatusList = appSumMgr.getApplicationStatus();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set description 
            systemAuditBean.setDescription("Generated Application Summary Report By " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.REPORT_EXPLORER);
            systemAuditBean.setSectionCode(SectionVarList.CARD_APPLICATION_REPORT);
            systemAuditBean.setPageCode(PageVarList.APPLICATION_SUMMARY);
            systemAuditBean.setTaskCode(TaskVarList.SEARCH);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
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

//    private void setUserInputsToBean(HttpServletRequest request) {
//    }
    private void getUserList() throws Exception {
        try {
            numberGenerationReportManager = new NumberGenerationReportManager();
            userList = numberGenerationReportManager.getAllUserList();
        } catch (Exception e) {
            throw e;
        }
    }
}
