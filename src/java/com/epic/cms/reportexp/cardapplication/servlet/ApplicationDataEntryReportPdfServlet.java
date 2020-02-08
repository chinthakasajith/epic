package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.reportexp.cardapplication.bean.ApplicationSummaryBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationSummaryManager;
import java.sql.SQLException;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationDataEntryBean;
import com.epic.cms.reportexp.cardapplication.bean.EmbossEncodeCardBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationDataEntryManager;
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
public class ApplicationDataEntryReportPdfServlet extends HttpServlet {

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
    private String url = "/reportexp/cardapplication/applicationdataentryhome.jsp";
    private NumberGenerationReportManager numberGenerationReportManager;
    private List<String> userList;
    private HashMap<String, String> priorityLevelList = null;
    private EmbossAndEncodeCardReportManager emEnMgr;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> cardProductList = null;
//    private EmbossEncodeCardBean inputBean = null;
    
    //*************************
    private ApplicationDataEntryBean inputBean;
    private ApplicationDataEntryManager dataEntryMgr;
    private List<ApplicationDataEntryBean> searchList = null;

    //*************************
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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
                String pageCode = PageVarList.APPLICATION_DATA_ENTRY;
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {

                        String path = "/resources/reports/applicationreport/DataEntry.jasper";

                        this.getUserList();
                        request.setAttribute("userList", userList);

                        this.getBranchNames();
                        request.setAttribute("bnkBranchList", bnkBranches);

                        this.getCardDomains();
                        request.setAttribute("cardDomainList", cardDomainList);//****

                        //*******************************
                        this.setUserInputToBean(request);
                        request.setAttribute("appBean", inputBean);

                        this.getApplicationDataEntry(inputBean);
                        request.setAttribute("searchList", searchList);
                        sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                        //*****************************

                        inputBean = new ApplicationDataEntryBean();

                        if (searchList.size() > 0) {
                            inputBean = searchList.get(0);
                        } else {
                            request.setAttribute("errorMsg", MessageVarList.EMPTY_VALUE_MSG);
                            rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                        }

                        HashMap map = new HashMap();
                        
                        
                        
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

                        if (null != request.getParameter("domain") && !request.getParameter("domain").equals("")) {
                            map.put("appDomain", cardDomainList.get(request.getParameter("domain")));
                        } else {
                            map.put("appDomain", "All");
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


                    } catch (Exception ex) {
                        ex.printStackTrace();
                        request.setAttribute("errorMsg", MessageVarList.ERROR_DATA_ENTRY_REPORT_GEN);

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

        inputBean = new ApplicationDataEntryBean();

        inputBean.setBranchCode(request.getParameter("branch"));
        inputBean.setDomainCode(request.getParameter("domain"));
        inputBean.setUser(request.getParameter("user"));
        inputBean.setFromDate(request.getParameter("fromdate"));
        inputBean.setToDate(request.getParameter("todate"));
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

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set description 
            systemAuditBean.setDescription("Generated Data Entry Report By " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.REPORT_EXPLORER);
            systemAuditBean.setSectionCode(SectionVarList.CARD_APPLICATION_REPORT);
            systemAuditBean.setPageCode(PageVarList.APPLICATION_DATA_ENTRY);
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

    private void getApplicationDataEntry(ApplicationDataEntryBean inputBean) throws SQLException, Exception {
        dataEntryMgr = new ApplicationDataEntryManager();
        searchList = dataEntryMgr.getApplicationDataEntry(inputBean);
        //sessionVarlist.setTerminalDataBeanList(searchList);
    }
}
