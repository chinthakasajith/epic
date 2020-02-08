package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationSummaryManager;
import java.sql.SQLException;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.CardStatusReportBean;
import com.epic.cms.reportexp.cardapplication.bean.EmbossEncodeCardBean;
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
public class ApplicationCardStatusReportPdfServlet extends HttpServlet {

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
    
    private HashMap<String, String> bnkBranches = null;
    private HashMap<String, String> appStatusList = null;
    private HashMap<String, String> cardDomainList = null;
    
    private HashMap<String, String> status = null;
    
    private CardStatusReportManager cdStatusMgr = null;
    private List<CardStatusReportBean> searchList;
    private String url = "/reportexp/cardapplication/cardstatusreporthome.jsp";
    private NumberGenerationReportManager numberGenerationReportManager;
    private List<String> userList;
    private HashMap<String, String> priorityLevelList = null;
    private EmbossAndEncodeCardReportManager emEnMgr;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> cardProductList = null;
    private List<String> embossUser;
     private CardStatusReportBean inputBean = null;

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
                String pageCode = PageVarList.CD_STATUS;
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {

                        String path = "/resources/reports/applicationreport/CardStatus.jasper";

                        this.getUserList();
                        request.setAttribute("userList", userList);

                        this.getBranchNames();
                        request.setAttribute("bnkBranchList", bnkBranches);

                        this.getPriorityLevels();

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
                        request.setAttribute("appBean", inputBean);

                        this.searchCardStatusReportDetails(inputBean);
                        request.setAttribute("searchList", searchList);

                        if (isValidate(inputBean)) {
//                            this.getAllSearchDetails();

                            inputBean = new CardStatusReportBean();

                            if (searchList.size() > 0) {
                                inputBean = searchList.get(0);
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
                            if (null != request.getParameter("appId") && !request.getParameter("appId").equals("")) {
                                map.put("applicationId", request.getParameter("appId"));
                            } else {
                                map.put("applicationId", " ");
                            }
                            if (null != request.getParameter("cardnumber") && !request.getParameter("cardnumber").equals("")) {
                                map.put("cardNumber", request.getParameter("cardnumber"));
                            } else {
                                map.put("cardNumber", "All");
                            }
                            if (null != request.getParameter("branch") && !request.getParameter("branch").equals("")) {
                                map.put("branchName", bnkBranches.get(request.getParameter("branch")));
                            } else {
                                map.put("branchName", "All");
                            }
                            if (null != request.getParameter("user") && !request.getParameter("user").equals("")) {
                                map.put("user", request.getParameter("user"));
                            } else {
                                map.put("user", "All");
                            }


                            if (null != request.getParameter("priority") && !request.getParameter("priority").equals("")) {
                                map.put("priorityLevelDes", inputBean.getPriorityLevelDes());
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
                            numberGenerationReportManager.getInstance().insertAuditValues(systemAuditBean);


                            response.setContentType("application/pdf");
                            InputStream inputStream = new FileInputStream(getServletContext().getRealPath(path));
                            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(searchList);
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
                        request.setAttribute("errorMsg", MessageVarList.ERROR_CARD_STATUS_REPORT_GEN);

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

    public void setUserInputToBean(HttpServletRequest request) {

        inputBean = new CardStatusReportBean();

        inputBean.setNic(request.getParameter("nic"));
        inputBean.setPassport(request.getParameter("passport"));
        //inputBean.setLicence(request.getParameter("drivinglicense"));
        inputBean.setApplicationId(request.getParameter("appId"));
        inputBean.setBranchCode(request.getParameter("branch"));
        inputBean.setPriorityLevelCode(request.getParameter("priority"));
        inputBean.setDomainCode(request.getParameter("domain"));
        inputBean.setFromDate(request.getParameter("fromdate"));
        inputBean.setToDate(request.getParameter("todate"));
        inputBean.setUser(request.getParameter("user"));
        inputBean.setCardNo(request.getParameter("cardnumber"));
        inputBean.setTypeCode(request.getParameter("type"));
        inputBean.setProductCode(request.getParameter("product"));
        inputBean.setStatus(request.getParameter("status"));
    }

    private boolean isValidate(CardStatusReportBean inputBean) throws Exception {

        boolean isValidate = true;

        if (!inputBean.getNic().equals("")) {
            if (!UserInputValidator.isAlphaNumeric(inputBean.getNic())) {
                isValidate = false;
                errorMessage = MessageVarList.INVALID_NIC;
            }
        } else if (!inputBean.getPassport().equals("")) {
            if (!UserInputValidator.isAlphaNumeric(inputBean.getPassport())) {
                isValidate = false;
                errorMessage = MessageVarList.ERROR_PAS_NUMBER;
            }
//        } else if (!inputBean.getLicence().equals("")) {
//            if (!UserInputValidator.isAlphaNumeric(inputBean.getLicence())) {
//                isValidate = false;
//                errorMessage = MessageVarList.ERROR_DRL_NUMBER;
//            }
        } else if (!inputBean.getCardNo().equals("")) {
            if (!UserInputValidator.isNumeric(inputBean.getCardNo())) {
                isValidate = false;
                errorMessage = MessageVarList.CARDNUMBER_INVALID;
            }
        } else if (!inputBean.getApplicationId().equals("")) {
            if (!UserInputValidator.isNumeric(inputBean.getApplicationId())) {
                isValidate = false;
                errorMessage = MessageVarList.APPASSIGN_APPLICATIONCODE_INVALID;
            }
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

    private void getPriorityLevels() throws Exception {
        try {
            appSumMgr = new ApplicationSummaryManager();
            priorityLevelList = appSumMgr.getPriorityLevels();
            sessionVarlist.setPriorityLevelList(priorityLevelList);

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

    private boolean isValidate(EmbossEncodeCardBean inputBean) throws Exception {

        boolean isValidate = true;

        if (!inputBean.getNic().equals("")) {
            if (!UserInputValidator.isAlphaNumeric(inputBean.getNic())) {
                isValidate = false;
                errorMessage = MessageVarList.INVALID_NIC;
            }
        } else if (!inputBean.getPassport().equals("")) {
            if (!UserInputValidator.isAlphaNumeric(inputBean.getPassport())) {
                isValidate = false;
                errorMessage = MessageVarList.ERROR_PAS_NUMBER;
            }
        } else if (!inputBean.getLicence().equals("")) {
            if (!UserInputValidator.isAlphaNumeric(inputBean.getLicence())) {
                isValidate = false;
                errorMessage = MessageVarList.ERROR_DRL_NUMBER;
            }
        } else if (!inputBean.getApplicationId().equals("")) {
            if (!UserInputValidator.isNumeric(inputBean.getApplicationId())) {
                isValidate = false;
                errorMessage = MessageVarList.APPASSIGN_APPLICATIONCODE_INVALID;
            }
        } else if (!inputBean.getCardNo().equals("")) {
            if (!UserInputValidator.isNumeric(inputBean.getCardNo())) {
                isValidate = false;
                errorMessage = MessageVarList.CARDNUMBER_INVALID;
            }
        }

        return isValidate;
    }

    private void searchCardStatusReportDetails(CardStatusReportBean inputBean) throws SQLException, Exception {
        cdStatusMgr = new CardStatusReportManager();
        searchList = cdStatusMgr.searchCardStatusReportDetails(inputBean);
        //sessionVarlist.setTerminalDataBeanList(searchList);
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
