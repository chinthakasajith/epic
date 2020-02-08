package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationSummaryManager;
import java.sql.SQLException;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationCreditScoreBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.CardStatusReportBean;
import com.epic.cms.reportexp.cardapplication.bean.EmbossEncodeCardBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationCreditScoreBreakDownManager;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationDetailsManager;
import com.epic.cms.reportexp.cardapplication.businesslogic.CardStatusReportManager;
import com.epic.cms.reportexp.cardapplication.businesslogic.EmbossAndEncodeCardReportManager;
import com.epic.cms.reportexp.cardapplication.businesslogic.NumberGenerationReportManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
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
import java.util.ArrayList;
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
 * @author jeevan
 */
public class ApplicationCreditScoreReportPdfServlet extends HttpServlet {

    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String errorMessage = null;
    private SessionVarList sessionVarlist;
    private SystemUserManager systemUserManager;
    private SystemAuditBean systemAuditBean = null;
    private ApplicationSummaryManager applicationSummaryManager;
    private ApplicationSummaryManager appSumMgr;
//    private HashMap<String, String> bnkBranches = null;
    private HashMap<String, String> branchList = null;
    private HashMap<String, String> appStatusList = null;
    private HashMap<String, String> cardDomainList = null;
    private HashMap<String, String> status = null;
    private CardStatusReportManager cdStatusMgr = null;
    private List<CardStatusReportBean> searchList;
    private String url = "/reportexp/cardapplication/applicationcreditscorebreakdownhome.jsp";
    private NumberGenerationReportManager numberGenerationReportManager;
    private HashMap<String, String> csUserList = null;
    ;
//    private List<String> userList;
    
    private HashMap<String, String> priorityLevelList = null;
    private EmbossAndEncodeCardReportManager emEnMgr;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> cardProductList = null;
    private List<String> embossUser;
    private ApplicationCreditScoreBean creditScoreBean = null;
    private List<ApplicationCreditScoreBean> creditScoreList = null;
    private ApplicationCreditScoreBreakDownManager crditScoreManager = null;
    private ApplicationDetailsManager appdetailsManager = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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
                String pageCode = PageVarList.APPLICATION_CREDIT_SCORE_BREAKDOWN_RPT;
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {

                        String path = "/resources/reports/applicationreport/CreditScore.jasper";


                        this.getCreditScoreUserList();
                        request.setAttribute("csUserList", csUserList);
//                        this.getUserList();
//                        request.setAttribute("userList", userList);

                        this.getBranchNames();
                        request.setAttribute("branchList", branchList);
//                        request.setAttribute("bnkBranchList", bnkBranches);

                        this.getAllPriorityLevelList();
                        request.setAttribute("priorityLevelList", priorityLevelList);

                        this.getCardDomains();
                        request.setAttribute("cardDomainList", cardDomainList);

                        this.getCardTypes();
                        request.setAttribute("cardTypeList", cardTypeList);

                        this.getCardProducts();
                        request.setAttribute("cardProductList", cardProductList);

                        this.getEmbossUser();
                        request.setAttribute("embossUser", embossUser);

                        this.getCardStatus();
                        request.setAttribute("statusList", status);

                        this.setUserInputToBean(request);
                        request.setAttribute("creditScoreBean", creditScoreBean);

                        this.searchApplicationCreditScoreReport(creditScoreBean);
                        request.setAttribute("creditScoreList", creditScoreList);//priorityLevelList

                        if (isValidate(creditScoreBean)) {
//                            this.getAllSearchDetails();

                            creditScoreBean = new ApplicationCreditScoreBean();

                            if (creditScoreList.size() > 0) {
                                creditScoreBean = creditScoreList.get(0);
                            } else {
                                request.setAttribute("errorMsg", MessageVarList.EMPTY_VALUE_MSG);
                                rd = request.getRequestDispatcher(url);
                                rd.forward(request, response);
                            }

                            HashMap map = new HashMap();

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
//                            if (null != request.getParameter("drivingLicence") && !request.getParameter("drivingLicence").equals("")) {
//                                map.put("drivingLicence", request.getParameter("drivingLicence"));
//                            } else {
//                                map.put("drivingLicence", " ");
//                            }
                                map.put("drivingLicence", " ");
                            if (null != request.getParameter("applicationID") && !request.getParameter("applicationID").equals("")) {
                                map.put("applicationId", request.getParameter("applicationID"));
                            } else {
                                map.put("applicationId", " ");
                            }
                            if (null != request.getParameter("cardnumber") && !request.getParameter("cardnumber").equals("")) {
                                map.put("cardNumber", request.getParameter("cardnumber"));
                            } else {
                                map.put("cardNumber", "All");
                            }
                            if (null != request.getParameter("branch") && !request.getParameter("branch").equals("")) {
                                map.put("branchName", branchList.get(request.getParameter("branch")));
//                                map.put("branchName", bnkBranches.get(request.getParameter("branch")));
                            } else {
                                map.put("branchName", "All");
                            }
                            if (null != request.getParameter("creditScoreUser") && !request.getParameter("creditScoreUser").equals("")) {
                                map.put("user", request.getParameter("creditScoreUser"));
                            } else {
                                map.put("user", "All");
                            }


                            if (null != request.getParameter("priorityLevel") && !request.getParameter("priorityLevel").equals("")) {
                                map.put("priorityLevelDes", priorityLevelList.get(request.getParameter("priorityLevel")));
//                                map.put("priorityLevelDes", creditScoreBean.getPriorityLevelDes());
                            } else {
                                map.put("priorityLevelDes", "All");
                            }
                            if (null != request.getParameter("domain") && !request.getParameter("domain").equals("")) {
                                map.put("domainCode", cardDomainList.get(request.getParameter("domain")));
                            } else {
                                map.put("domainCode", "All");
                            }
                            if (null != request.getParameter("type") && !request.getParameter("type").equals("")) {
                                map.put("cardTypeDes", cardTypeList.get(request.getParameter("type")));
                            } else {
                                map.put("cardTypeDes", "All");
                            }

                            if (null != request.getParameter("status") && !request.getParameter("status").equals("")) { //*********
                                map.put("cardStatus", status.get(request.getParameter("status")));
                            } else {
                                map.put("cardStatus", "All");
                            }
                            if (null != request.getParameter("product") && !request.getParameter("product").equals("")) {
                                map.put("cardProductDesc", cardProductList.get(request.getParameter("product")));
                            } else {
                                map.put("cardProductDesc", "All");
                            }

                            if (null != request.getParameter("fromDate") && !request.getParameter("fromDate").equals("")) {
                                map.put("fromDate", request.getParameter("fromDate"));
                            } else {
                                map.put("fromDate", " ");
                            }
                            if (null != request.getParameter("toDate") && !request.getParameter("toDate").equals("")) {
                                map.put("toDate", request.getParameter("toDate"));
                            } else {
                                map.put("toDate", " ");
                            }

                            this.setAudittraceValue(request);
                            crditScoreManager.getInstance().insertAuditValues(systemAuditBean);


                            response.setContentType("application/pdf");
                            InputStream inputStream = new FileInputStream(getServletContext().getRealPath(path));
                            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(creditScoreList);
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
                        ex.printStackTrace();
                        request.setAttribute("errorMsg", MessageVarList.ERROR_CREDIT_SCORE_REPORT_GEN);

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

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = false;
        try {
            creditScoreBean = new ApplicationCreditScoreBean();

            creditScoreBean.setNic(request.getParameter("nic").trim());
            creditScoreBean.setPassport(request.getParameter("passport").trim());
            //creditScoreBean.setDrivingLicence(request.getParameter("drivingLicence").trim());
            creditScoreBean.setApplicationID(request.getParameter("applicationID").trim());
            creditScoreBean.setBranch(request.getParameter("branch").trim());
            creditScoreBean.setPriorityLevel(request.getParameter("priorityLevel").trim());
            creditScoreBean.setCreditScoreUser(request.getParameter("creditScoreUser").trim());
            creditScoreBean.setFromDate(request.getParameter("fromDate"));
            creditScoreBean.setToDate(request.getParameter("toDate"));

            success = true;

        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    public boolean isValidate(ApplicationCreditScoreBean creditScoreBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code

        try {
            if (!(creditScoreBean.getNic().equals("")) && !UserInputValidator.isAlphaNumeric(creditScoreBean.getNic())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!(creditScoreBean.getPassport().equals("")) && !UserInputValidator.isAlphaNumeric(creditScoreBean.getPassport())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

//            } else if (!(creditScoreBean.getDrivingLicence().equals("")) && !UserInputValidator.isAlphaNumeric(creditScoreBean.getDrivingLicence())) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!(creditScoreBean.getApplicationID().equals("")) && !UserInputValidator.isAlphaNumeric(creditScoreBean.getApplicationID())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
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
            branchList = appSumMgr.getBranchNames();
//            bnkBranches = appSumMgr.getBranchNames();
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
            systemAuditBean.setDescription("Generated Credit Score Report By " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.REPORT_EXPLORER);
            systemAuditBean.setSectionCode(SectionVarList.CARD_APPLICATION_REPORT);
            systemAuditBean.setPageCode(PageVarList.APPLICATION_CREDIT_SCORE_BREAKDOWN_RPT);
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
//    private void getUserList() throws Exception {
//        try {
//            numberGenerationReportManager = new NumberGenerationReportManager();
//            csUserList = numberGenerationReportManager.getAllUserList();
////            userList = numberGenerationReportManager.getAllUserList();
//        } catch (Exception e) {
//            throw e;
//        }
//    }
    private void getCreditScoreUserList() throws Exception {
        try {
            crditScoreManager = new ApplicationCreditScoreBreakDownManager();
            csUserList = new HashMap<String, String>();
            csUserList = crditScoreManager.getCreditScoreUserList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllPriorityLevelList() throws Exception {
        try {
            appdetailsManager = new ApplicationDetailsManager();
            priorityLevelList = new HashMap<String, String>();
            priorityLevelList = appdetailsManager.getAllPriorityLevels();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardTypes() throws Exception {
        try {

            emEnMgr = new EmbossAndEncodeCardReportManager();
            cardTypeList = emEnMgr.getCardTypes();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardProducts() throws Exception {
        try {

            emEnMgr = new EmbossAndEncodeCardReportManager();
            cardProductList = emEnMgr.getCardProducts();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getEmbossUser() throws Exception {
        try {

            emEnMgr = new EmbossAndEncodeCardReportManager();
            embossUser = emEnMgr.getEmbossUser();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void searchApplicationCreditScoreReport(ApplicationCreditScoreBean creditScoreBean) throws Exception {
        try {
            crditScoreManager = new ApplicationCreditScoreBreakDownManager();
            creditScoreList = new ArrayList<ApplicationCreditScoreBean>();

            creditScoreList = crditScoreManager.searchApplicationCreditScoreReport(creditScoreBean);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardStatus() throws Exception {
        try {

            cdStatusMgr = new CardStatusReportManager();
            status = cdStatusMgr.getCardStatus();

        } catch (Exception ex) {
            throw ex;
        }
    }
}
