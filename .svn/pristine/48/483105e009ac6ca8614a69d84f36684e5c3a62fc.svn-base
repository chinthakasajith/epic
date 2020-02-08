/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.reportexp.cardapplication.bean.BankBranchBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationVerificationSearchBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationVerificationReportManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.SectionVarList;

/**
 *
 * @author asela
 */
public class ViewApplicationVerificationReportServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private ApplicationVerificationReportManager applicationVerificationReportManager;
    private List<ApplicationVerificationSearchBean> searchList;
    private ApplicationVerificationSearchBean applicationVerificationSearchBean;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private List<ApplicationVerificationSearchBean> verificationList;
    private ApplicationVerificationSearchBean verificationBean;
    private Map<String, String> priorityLevelMap;
    private Map<String, String> applicationDomainMap;
    private String verificationCodeList;
    private List<BankBranchBean> bankBranchList;
    private List<ApplicationVerificationSearchBean> applicationVerificationReportList;
    private List<StatusBean> statusList;
    private String errorMessage;
    private SystemAuditBean systemAuditBean = null;
    private String url = "/reportexp/cardapplication/applicationverificationreport.jsp";
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
        // PrintWriter out = response.getWriter();
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
                String pageCode = PageVarList.APPLICATION_VERIFICATION_RPT;
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        String path = "/resources/reports/applicationreport/ApplicationVerificationReport.jasper";
                        this.setUserInputsToBean(request);
                        if (isValidate(applicationVerificationSearchBean)) {
                            verificationList = this.getAllSearchDetails();


                        verificationBean = new ApplicationVerificationSearchBean();

                        String currentDate = this.getCurrentDate();

                        this.getVerificationReportDetails();
                        verificationCodeList = StatusVarList.APP_VERIFY_COMPELTE;
                        verificationCodeList += "','";
                        verificationCodeList += StatusVarList.APP_ONHOLD;
                        verificationCodeList += "','";
                        verificationCodeList += StatusVarList.APP_VERIFY_REJECT;


                        this.getAllStatus(verificationCodeList);
                        this.getAllBranchList();
                        this.getAllPriorityLevelMap();
                        this.getAllApplicationDomainMap();

                        request.setAttribute("searchbean", applicationVerificationSearchBean);
                        request.setAttribute("bankBranchList", bankBranchList);
                        request.setAttribute("applicationDomainMap", applicationDomainMap);
                        request.setAttribute("priorityLevelMap", priorityLevelMap);

                        request.setAttribute("searchBeanList", applicationVerificationReportList);
                        request.setAttribute("currentDate", currentDate);


                        if (verificationList.size() > 0) {
                            //verificationBean = verificationList.get(0);
                        } else {
                            request.setAttribute("errorMsg", MessageVarList.EMPTY_VALUE_MSG);
                        }

                        Map map = new HashMap();

                        if (null != request.getParameter("applicationId") && !request.getParameter("applicationId").equals("")) {
                            map.put("applicationId", request.getParameter("applicationId"));
                        } else {
                            map.put("applicationId", " ");
                        }
                        if (null != request.getParameter("nic") && !request.getParameter("nic").equals("")) {
                            map.put("nic", request.getParameter("nic"));
                        } else {
                            map.put("nic", " ");
                        }
                        if (null != request.getParameter("passport") && !request.getParameter("passport").equals("")) {
                            map.put("passport", request.getParameter("passport"));
                        } else {
                            map.put("passport", " ");
                        }
//                        if (null != request.getParameter("drivingLicence") && !request.getParameter("drivingLicence").equals("")) {
//                            map.put("drivingLicence", request.getParameter("drivingLicence"));
//                        } else {
//                            map.put("drivingLicence", " ");
//                        }
                        map.put("drivingLicence", " ");
                        if (null != request.getParameter("branch") && !request.getParameter("branch").equals("")) {
                            for (BankBranchBean bean : bankBranchList) {
                                if (bean.getBranchCode().equals(request.getParameter("branch"))) {
                                    map.put("branchDescription", bean.getDescription());
                                    break;
                                }

                            }

                        } else {
                            map.put("branchDescription", "All");
                        }
                        if (null != request.getParameter("priorityLevel") && !request.getParameter("priorityLevel").equals("")) {
                            map.put("priorityLevelDes", priorityLevelMap.get(request.getParameter("priorityLevel")));
                        } else {
                            map.put("priorityLevelDes", "All");
                        }
                        if (null != request.getParameter("verificationStatus") && !request.getParameter("verificationStatus").equals("")) {
                            for (StatusBean bean : statusList) {
                                if (bean.getStatusCode().equals(request.getParameter("verificationStatus"))) {
                                    map.put("verificationStatusDes", bean.getDescription());
                                    break;
                                }
                            }


                        } else {
                            map.put("verificationStatusDes", "All");
                        }
                        if (null != request.getParameter("applicationDomain") && !request.getParameter("applicationDomain").equals("")) {
                            map.put("domain", applicationDomainMap.get(request.getParameter("applicationDomain")));
                        } else {
                            map.put("domain", "All");
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
                        ApplicationVerificationReportManager.getInstance().insertAuditValues(systemAuditBean);

                        response.setContentType("application/pdf");
                        InputStream inputStream = new FileInputStream(getServletContext().getRealPath(path));
                        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(this.getAllSearchDetails());
                        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, map, ds);

                        JRPdfExporter exp = new JRPdfExporter();
                        exp.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
                        exp.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                        exp.exportReport();
                        
                        } else {
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);                            
                        }
                        
                    } catch (Exception ex) {
                        request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_APPLICATION_VERIFICATION_REPORT);
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
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_APPLICATION_VERIFICATION_REPORT);
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

    public List<ApplicationVerificationSearchBean> getAllSearchDetails() throws Exception {
        try {
            applicationVerificationReportManager = new ApplicationVerificationReportManager();
            searchList = applicationVerificationReportManager.getVerificationReportDetails(applicationVerificationSearchBean);
        } catch (Exception ex) {
            throw ex;
        }
        return searchList;
    }

    private void setUserInputsToBean(HttpServletRequest request) throws Exception {
        try {

            String NIC = request.getParameter("nic").trim();
            String branch = request.getParameter("branch");
            String passport = request.getParameter("passport").trim();
            String priorityLevel = request.getParameter("priorityLevel");
            //String drivingLicence = request.getParameter("drivingLicence").trim();
            String verificationStatus = request.getParameter("verificationStatus");
            String applicationId = request.getParameter("applicationId").trim();
            String applicationDomain = request.getParameter("applicationDomain");
            String fromdate = request.getParameter("fromdate");
            String todate = request.getParameter("todate");

            applicationVerificationSearchBean = new ApplicationVerificationSearchBean();

            applicationVerificationSearchBean.setNic(NIC);
            applicationVerificationSearchBean.setBranchCode(branch);
            applicationVerificationSearchBean.setPassport(passport);
            applicationVerificationSearchBean.setPriorityLevel(priorityLevel);
            //applicationVerificationSearchBean.setDrivingLicence(drivingLicence);
            applicationVerificationSearchBean.setVerificationStatus(verificationStatus);
            applicationVerificationSearchBean.setApplicationId(applicationId);
            applicationVerificationSearchBean.setDomain(applicationDomain);
            applicationVerificationSearchBean.setFromDate(fromdate);
            applicationVerificationSearchBean.setToDate(todate);


        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean isValidate(ApplicationVerificationSearchBean inputBean) throws Exception {

        boolean isValidate = true;
        try {
            if (!inputBean.getNic().equals("")) {
                if (!UserInputValidator.isAlphaNumeric(inputBean.getNic())) {
                    isValidate = false;
                    errorMessage = MessageVarList.INVALID_NIC;
                }
            }
            if (!inputBean.getPassport().equals("")) {
                if (!UserInputValidator.isAlphaNumeric(inputBean.getPassport())) {
                    isValidate = false;
                    errorMessage = MessageVarList.ERROR_PAS_NUMBER;
                }
            }
//            if (!inputBean.getDrivingLicence().equals("")) {
//                if (!UserInputValidator.isAlphaNumeric(inputBean.getDrivingLicence())) {
//                    isValidate = false;
//                    errorMessage = MessageVarList.ERROR_DRL_NUMBER;
//                }
//            }
            if (!inputBean.getApplicationId().equals("")) {
                if (!UserInputValidator.isNumeric(inputBean.getApplicationId())) {
                    isValidate = false;
                    errorMessage = MessageVarList.APPASSIGN_APPLICATIONCODE_INVALID;
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return isValidate;
    }

    private void getVerificationReportDetails() throws Exception {
        try {

            applicationVerificationReportManager = new ApplicationVerificationReportManager();
            applicationVerificationReportList = applicationVerificationReportManager.getVerificationReportDetails(applicationVerificationSearchBean);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private String getCurrentDate() throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return dateFormat.format(date);

    }

    private void getAllStatus(String categoryCodeList) throws Exception {
        try {
            systemUserManager = new SystemUserManager();
            statusList = systemUserManager.getStatusByStatusCodeList(categoryCodeList);
        } catch (Exception e) {
            throw e;
        }
    }

    private void getAllBranchList() throws Exception {
        try {
            applicationVerificationReportManager = new ApplicationVerificationReportManager();
            bankBranchList = applicationVerificationReportManager.getCommenBankBranchList();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getAllPriorityLevelMap() throws Exception {
        try {
            applicationVerificationReportManager = new ApplicationVerificationReportManager();
            priorityLevelMap = applicationVerificationReportManager.getAllPriorityLevelMap();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getAllApplicationDomainMap() throws Exception {
        try {
            applicationVerificationReportManager = new ApplicationVerificationReportManager();
            applicationDomainMap = applicationVerificationReportManager.getAllApplicationDomainMap();
        } catch (Exception e) {
            throw e;
        }
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set description 
            systemAuditBean.setDescription("Generated Application Verification Report By " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.REPORT_EXPLORER);
            systemAuditBean.setSectionCode(SectionVarList.CARD_APPLICATION_REPORT);
            systemAuditBean.setPageCode(PageVarList.APPLICATION_VERIFICATION_RPT);
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
}
