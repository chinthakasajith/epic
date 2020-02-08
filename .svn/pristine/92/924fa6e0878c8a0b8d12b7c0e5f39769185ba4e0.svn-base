package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.CardDistributionReportBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationCreditScoreBreakDownManager;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationDetailsManager;
import com.epic.cms.reportexp.cardapplication.businesslogic.CardDistributionReportManager;
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
import java.util.ArrayList;
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
 * @author nalin  
 */
public class GenerateReportApplicationReportServlet extends HttpServlet {

    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String errorMessage = null;
    private SessionVarList sessionVarlist;
    private SystemUserManager systemUserManager;
    private SystemAuditBean systemAuditBean = null;
    private String url = "/reportexp/cardapplication/carddistributionreporthome.jsp";
    private NumberGenerationReportManager numberGenerationReportManager;
    //********************************
    private CardDistributionReportManager distributeManager = null;
    private ApplicationDetailsManager appdetailsManager = null;
    private ApplicationCreditScoreBreakDownManager crditScoreManager = null;
    private HashMap<String, String> branchList = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> domainList = null;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> cardProductList = null;
    private HashMap<String, String> csUserList = null;
    private CardDistributionReportBean distributeBean = null;
    private List<CardDistributionReportBean> distributeList = null;

    //********************************
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
                String pageCode = PageVarList.CARD_DISTRIBUTION_REPORT;
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {

                        String path = "/resources/reports/applicationreport/DistributionReport.jasper";

                        this.getBranchNames();
                        this.getAllPriorityLevelList();
                        this.getAllDomainList();
                        this.getCardType();
                        this.getCardProduct();
                        this.getCreditScoreUserList();

                        request.setAttribute("branchList", branchList);
                        request.setAttribute("priorityLevelList", priorityLevelList);
                        request.setAttribute("domainList", domainList);
                        request.setAttribute("cardTypeList", cardTypeList);
                        request.setAttribute("cardProductList", cardProductList);
                        request.setAttribute("csUserList", csUserList);

                        this.setUserInputToBean(request);
                        request.setAttribute("distributeBean", distributeBean);

                        this.searchCardDistributeReport(distributeBean);
                        request.setAttribute("distributeList", distributeList);

                        if (isValidate(distributeBean)) {
                            distributeBean = new CardDistributionReportBean();

                            if (distributeList.size() > 0) {
                                distributeBean = distributeList.get(0);
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
                            if (null != request.getParameter("cardNumber") && !request.getParameter("cardNumber").equals("")) {
                                map.put("cardNumber", request.getParameter("cardNumber"));
                            } else {
                                map.put("cardNumber", " ");
                            }
                            if (null != request.getParameter("cardProduct") && !request.getParameter("cardProduct").equals("")) {
                                map.put("cardProductDesc", request.getParameter("cardProduct"));
                            } else {
                                map.put("cardProductDesc", " ");
                            }
                            if (null != request.getParameter("cardType") && !request.getParameter("cardType").equals("")) {
                                map.put("cardTypeDes", request.getParameter("cardType"));
                            } else {
                                map.put("cardTypeDes", " ");
                            }
                            if (null != request.getParameter("branch") && !request.getParameter("branch").equals("")) {
                                map.put("branchName", branchList.get(request.getParameter("branch")));
                            } else {
                                map.put("branchName", "All");
                            }
                            if (null != request.getParameter("priorityLevel") && !request.getParameter("priorityLevel").equals("")) {
                                map.put("priorityLevelDes", priorityLevelList.get(request.getParameter("priorityLevel")));
                            } else {
                                map.put("priorityLevelDes", "All");
                            }
                            if (null != request.getParameter("domain") && !request.getParameter("domain").equals("")) {
                                map.put("domainCode", domainList.get(request.getParameter("domain")));
                            } else {
                                map.put("domainCode", "All");
                            }
                            if (null != request.getParameter("distributedUser") && !request.getParameter("distributedUser").equals("")) {
                                map.put("user", request.getParameter("distributedUser"));
                            } else {
                                map.put("user", "All");
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
                            numberGenerationReportManager.getInstance().insertAuditValues(systemAuditBean);

                            this.setAudittraceValue(request);
                            appdetailsManager.getInstance().insertAuditValues(systemAuditBean);

                            response.setContentType("application/pdf");
                            InputStream inputStream = new FileInputStream(getServletContext().getRealPath(path));
                            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(distributeList);
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
                        request.setAttribute("errorMsg", MessageVarList.ERROR_APP_DISTRIBUTION_REPORT_GEN);
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
            distributeBean = new CardDistributionReportBean();

            distributeBean.setNic(request.getParameter("nic"));
            distributeBean.setPassport(request.getParameter("passport"));
            //distributeBean.setDrivingLicence(request.getParameter("drivingLicence"));
            distributeBean.setApplicationID(request.getParameter("applicationID"));
            distributeBean.setCardNumber(request.getParameter("cardNumber"));
            distributeBean.setBranch(request.getParameter("branch"));
            distributeBean.setPriorityLevel(request.getParameter("priorityLevel"));
            distributeBean.setDomain(request.getParameter("domain"));
            distributeBean.setDistributedUser(request.getParameter("distributedUser"));
            distributeBean.setFromDate(request.getParameter("fromDate"));
            distributeBean.setToDate(request.getParameter("toDate"));
            distributeBean.setCardType(request.getParameter("cardType"));
            distributeBean.setCardProduct(request.getParameter("cardProduct"));


            success = true;

        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    public boolean isValidate(CardDistributionReportBean distributeBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code

        try {
            if (!(distributeBean.getNic().equals("")) && !UserInputValidator.isAlphaNumeric(distributeBean.getNic())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!(distributeBean.getPassport().equals("")) && !UserInputValidator.isAlphaNumeric(distributeBean.getPassport())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

//            } else if (!(distributeBean.getDrivingLicence().equals("")) && !UserInputValidator.isAlphaNumeric(distributeBean.getDrivingLicence())) {
//                isValidate = false;
//
//                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!(distributeBean.getApplicationID().equals("")) && !UserInputValidator.isAlphaNumeric(distributeBean.getApplicationID())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set description 
            systemAuditBean.setDescription("Generated Application Distribution Report By " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.REPORT_EXPLORER);
            systemAuditBean.setSectionCode(SectionVarList.CARD_APPLICATION_REPORT);
            systemAuditBean.setPageCode(PageVarList.CARD_DISTRIBUTION_REPORT);
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

    private void getBranchNames() throws Exception {
        try {
            appdetailsManager = new ApplicationDetailsManager();
            branchList = new HashMap<String, String>();
            branchList = appdetailsManager.getBranchNames();
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

    private void getAllDomainList() throws Exception {
        try {
            appdetailsManager = new ApplicationDetailsManager();
            domainList = new HashMap<String, String>();
            domainList = appdetailsManager.getAllDomainList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardType() throws Exception {
        try {
            distributeManager = new CardDistributionReportManager();
            cardTypeList = new HashMap<String, String>();
            cardTypeList = distributeManager.getCardType();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardProduct() throws Exception {
        try {
            distributeManager = new CardDistributionReportManager();
            cardProductList = new HashMap<String, String>();
            cardProductList = distributeManager.getCardProduct();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCreditScoreUserList() throws Exception {
        try {
            crditScoreManager = new ApplicationCreditScoreBreakDownManager();
            csUserList = new HashMap<String, String>();
            csUserList = crditScoreManager.getCreditScoreUserList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void searchCardDistributeReport(CardDistributionReportBean distributeBean) throws Exception {

        try {

            distributeManager = new CardDistributionReportManager();
            distributeList = new ArrayList<CardDistributionReportBean>();
            distributeList = distributeManager.searchCardDistributeReport(distributeBean);

        } catch (Exception ex) {
            throw ex;
        }

    }
}
