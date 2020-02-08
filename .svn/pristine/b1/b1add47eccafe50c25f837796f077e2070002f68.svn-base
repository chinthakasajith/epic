/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardExpensesBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardIncomeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerEmploymentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EmploymentNatureBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EmploymentTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.OccupationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 *
 * @author janaka_h
 */
public class AddEmploymentDetailsServlet extends HttpServlet {

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
    private CaptureDataManager manager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private List<StatusBean> statusList = null;
    private List<EmploymentTypeBean> empTypeList = null;
    private List<OccupationBean> occupationList = null;
    private List<EmploymentNatureBean> natureList = null;
    private SystemAuditBean systemAuditBean;
    private String url = "/camm/capturedata/inputCaptureData.jsp";
    private ApplicationCheckingManager checkingManager;
    private CardApplicationStatusBean appStatusBean = null;
    private CustomerPersonalBean customerPersonalBean = null;
    private CustomerEmploymentBean employmentBean = null;
    private List<CardIncomeBean> incomeBeanList = null;
    private CardExpensesBean expensesBean = null;
    private List<CardBankDetailsBean> bankDetailsBeanLst = null;
    private List<DocumentUploadBean> documentDetailsBeanLst = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        CustomerEmploymentBean employmentBean = new CustomerEmploymentBean();
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

            sessionVarlist.setCMSSessionUser(sessionUser);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

            String applicationId = sessionVarlist.getApplicationId();
            request.setAttribute("applicationId", applicationId);
            request.setAttribute("operationtype", "add");

//           
//
            employmentBean.setApplicationId(applicationId);
            employmentBean.setEmploymentStatus(request.getParameter("empStatus"));
            employmentBean.setEmploymentType(request.getParameter("empType"));
            employmentBean.setOtherEmploymentType(request.getParameter("otherEmpType"));
            employmentBean.setCompanyName(request.getParameter("companyName"));
            employmentBean.setAdress1(request.getParameter("address1"));
            employmentBean.setAdress2(request.getParameter("address2"));
            employmentBean.setAdress3(request.getParameter("address3"));
            employmentBean.setOfficePhone(request.getParameter("officeTelphone"));
            employmentBean.setOccupation(request.getParameter("occupation"));
            employmentBean.setDesignation(request.getParameter("designation"));
            employmentBean.setBusinessNature(request.getParameter("businessNature"));
            employmentBean.setOtherBusinessNature(request.getParameter("otherBusinessNature"));
            employmentBean.setNumberOfEmployees(request.getParameter("noOfEmployeers"));
            employmentBean.setSelfEmpCompanyname(request.getParameter("selfemployeecompanyname"));
            employmentBean.setSelfEmpNoOfEmployee(request.getParameter("selfemployeeNoofemployee"));
            employmentBean.setNetProfit(request.getParameter("netprofit"));
            employmentBean.setAnnualTurnOver(request.getParameter("annualturnover"));
            employmentBean.setOtherEmploymentType(request.getParameter("otherEmpType"));
            employmentBean.setOtherBusinessNature(request.getParameter("otherBusinessNature"));
            employmentBean.setServiceYear(request.getParameter("seviceYear"));
            employmentBean.setServiceMonth(request.getParameter("seviceMonth"));
            employmentBean.setStaffStatus(request.getParameter("staff"));
            //new by chinthaka
            employmentBean.setAgeOfCompany(request.getParameter("ageOfCompany"));
            employmentBean.setEmploymentDurationInYears(request.getParameter("durationInYears"));
            employmentBean.setEmploymentDurationInMonths(request.getParameter("durationInMonths"));
            employmentBean.setpEmployerName(request.getParameter("nameOfPreviouEmployer"));
            employmentBean.setpEmployerAddress1(request.getParameter("pEmployerAddress1"));
            employmentBean.setpEmployerAddress2(request.getParameter("pEmployerAddress2"));
            employmentBean.setpEmployerAddress3(request.getParameter("pEmployerAddress3"));
            employmentBean.setpEmploymentDurationInYears(request.getParameter("pEmployementDurationInYears"));
            employmentBean.setpEmploymentDurationInMonths(request.getParameter("pEmployementDurationInMonths"));
//
//
            CustomerEmploymentBean invalidEmpBean = new CustomerEmploymentBean();
//
            invalidEmpBean = this.isValiedEmploymentInfo(employmentBean);

            if (invalidEmpBean == null) {

                manager = new CaptureDataManager();
                this.setAudittraceValue(request, employmentBean);
                int isAdd = manager.insertEmploymentData(employmentBean, systemAuditBean);

                if (isAdd == 1) {

                    this.loadDefaultApplicationStatus(applicationId, request);

                    request.setAttribute("successMsg", MessageVarList.ADD_EMPLOY_SUCCESS);

                    request.setAttribute("applicationId", applicationId);
                    request.setAttribute("employmentBean", employmentBean);
                    request.setAttribute("operationtype", "add");

                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);
                } else {

                    this.loadDefaultApplicationStatus(applicationId, request);
                    request.setAttribute("errorMsg", MessageVarList.ADD_EMPLOY_ERROR);

                    request.setAttribute("applicationId", applicationId);
                    request.setAttribute("employmentBean", employmentBean);
                    request.setAttribute("operationtype", "add");

                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);
                }

            } else {

                this.loadDefaultApplicationStatus(applicationId, request);

                request.setAttribute("applicationId", applicationId);
                request.setAttribute("employmentBean", employmentBean);
                request.setAttribute("operationtype", "add");
                request.setAttribute("invalidEmpBean", invalidEmpBean);

                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            }

//          
        } catch (SQLException ex) {
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);

        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {
            this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
            request.setAttribute("errorMsg", "Error in action");
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);

        } finally {
            out.close();
        }
    }

    private void loadDefaultApplicationStatus(String appliactionId, HttpServletRequest request) throws Exception {

        this.getAllApplicationStatus(appliactionId);
        String loadTabIndex = "";
        String selectedTab = "";

        if (appStatusBean != null) {

            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_INITIATE) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_PROCESS)) {

                if (appStatusBean.getTableOne().equals("1")) {
                    this.getAllDetailsCustomer(appliactionId);
                    request.setAttribute("personalBean", customerPersonalBean);
                    sessionVarlist.setPersonalBean(customerPersonalBean);
                    selectedTab = "1";

                } else {
                    loadTabIndex = "0";
                }
                if (appStatusBean.getTableTwo().equals("1")) {
                    this.getAllEmpDetails(appliactionId);
                    request.setAttribute("employmentBean", employmentBean);
                    sessionVarlist.setEmploymentBean(employmentBean);
                    selectedTab = "2";
                } else {
                    loadTabIndex = loadTabIndex + "," + "1";
                }
                if (appStatusBean.getTableThree().equals("1")) {
                    this.getAllIncomeDetails(appliactionId);
                    this.getAllExpenseDetails(appliactionId);
                    request.setAttribute("expenseBean", expensesBean);

                    sessionVarlist.setExpensesBean(expensesBean);
                    sessionVarlist.setSessionIncomeList(incomeBeanList);
                    selectedTab = "3";

                } else {
                    loadTabIndex = loadTabIndex + "," + "2";
                }
                if (appStatusBean.getTableFore().equals("1")) {
                    this.getAllBankDetails(appliactionId);
                    sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);
                    selectedTab = "4";
                } else {

                    loadTabIndex = loadTabIndex + "," + "3";
                }
                if (appStatusBean.getTableFive().equals("1")) {
                    this.getAllDocumentDetails(appliactionId);
                    sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);
                    selectedTab = "5";

                } else {

                    loadTabIndex = loadTabIndex + "," + "4";
                }
                if (appStatusBean.getTableSix().equals("1")) {
                } else {

                    loadTabIndex = loadTabIndex + "," + "5";
                }

            }
//            this.getAllDocumentDetails(appliactionId);

        }

        request.setAttribute("loadTabIndex", loadTabIndex);
        request.setAttribute("selectedtab", selectedTab);

    }

    private CustomerEmploymentBean isValiedEmploymentInfo(CustomerEmploymentBean employmentBean) throws Exception {
        UserInputValidator validObject = new UserInputValidator();
        CustomerEmploymentBean invalidMsgBean = new CustomerEmploymentBean();
        int msg = 0;

        try {
            //----------------------------------------------------------------------------           

            //----------------------------------------------------------------------------            
            if (employmentBean.getEmploymentType().equals("EMPOTH")) {
                if (employmentBean.getOtherEmploymentType().isEmpty()) {
                    invalidMsgBean.setOtherEmploymentType("Requierd");
                    msg = 1;
                } else if (!employmentBean.getOtherEmploymentType().isEmpty()) {
                    if (!validObject.isCorrectString(employmentBean.getOtherEmploymentType())) {
                        invalidMsgBean.setOtherEmploymentType("Invalid");
                        msg = 1;
                    }
                }
//            }
////            
//            if (employmentBean.getCompanyName().isEmpty()) {
//                invalidMsgBean.setCompanyName("Requierd");
//                msg = 1;
            } else if (!validObject.isDescription(employmentBean.getOtherEmploymentType())) {
                invalidMsgBean.setOtherEmploymentType("Invalid");
                msg = 1;
            }
            if (!validObject.isDescription(employmentBean.getCompanyName())) {
                invalidMsgBean.setCompanyName("Invalid");
                msg = 1;

                // }
//            if (employmentBean.getAdress1().isEmpty()) {
//                invalidMsgBean.setAdress1("Requierd");
//                msg = 1;
//            }
//            if (!employmentBean.getAdress3().isEmpty() && employmentBean.getAdress2().isEmpty()) {
//                invalidMsgBean.setAdress2("Requierd");
//                msg = 1;
//            }
//            if (employmentBean.getOfficePhone().isEmpty()) {
//                invalidMsgBean.setOfficePhone("Requierd");
//                msg = 1;
            } else if (!employmentBean.getOfficePhone().isEmpty()) {
                if (!validObject.isNumeric(employmentBean.getOfficePhone())) {
                    invalidMsgBean.setOfficePhone("Invalid");
                    msg = 1;
                }

//            }
//            if (employmentBean.getDesignation().isEmpty()) {
//                invalidMsgBean.setDesignation("Requierd");
//                msg = 1;
            } else if (!validObject.isDescription(employmentBean.getDesignation())) {
                invalidMsgBean.setDesignation("Invalid");
                msg = 1;
//            }
//            if (employmentBean.getAnnualTurnOver().isEmpty()) {
//                //invalidMsgBean.setAnnualTurnOver("Requierd");
//                //msg = 1;
            } else if (!employmentBean.getAnnualTurnOver().isEmpty()) {
                if (!validObject.isNumeric(employmentBean.getAnnualTurnOver())) {
                    invalidMsgBean.setAnnualTurnOver("Invalid");
                    msg = 1;
                }
//            }
//            if (employmentBean.getNetProfit().isEmpty()) {
//                //invalidMsgBean.setNetProfit("Requierd");
//                //msg = 1;
            } else if (!employmentBean.getNetProfit().isEmpty()) {
                if (!validObject.isNumeric(employmentBean.getNetProfit())) {
                    invalidMsgBean.setNetProfit("Invalid");
                    msg = 1;
                }
//            }
//            if (employmentBean.getSelfEmpCompanyname().isEmpty()) {
//                //invalidMsgBean.setSelfEmpCompanyname("Requierd");
//                // msg = 1;
            } else if (!validObject.isDescription(employmentBean.getSelfEmpCompanyname())) {
                invalidMsgBean.setSelfEmpCompanyname("Invalid");
                msg = 1;

            }
            if (employmentBean.getBusinessNature().equals("OTHER")) {
                if (employmentBean.getOtherBusinessNature().isEmpty()) {
                    invalidMsgBean.setOtherBusinessNature("Requierd");
                    msg = 1;
                } else if (!employmentBean.getOtherBusinessNature().isEmpty()) {
                    if (!validObject.isCorrectString(employmentBean.getOtherBusinessNature())) {
                        invalidMsgBean.setOtherBusinessNature("Invalid");
                        msg = 1;
                    }
                }
//            }
//            //ADDED BUY CHINTHAKA
//            if (employmentBean.getAgeOfCompany().isEmpty()) {
//                invalidMsgBean.setAgeOfCompany("Requierd");
//                msg = 1;
            } else if (!validObject.isDescription(employmentBean.getOtherBusinessNature())) {
                invalidMsgBean.setOtherBusinessNature("Invalid");
                msg = 1;

            }
            if (!employmentBean.getAgeOfCompany().isEmpty()) {
                if (!validObject.isNumeric(employmentBean.getAgeOfCompany())) {
                    invalidMsgBean.setAgeOfCompany("Invalid");
                    msg = 1;
                }
            }
//            if (employmentBean.getEmploymentDurationInYears().equals("0") && employmentBean.getEmploymentDurationInMonths().equals("0")) {
//                invalidMsgBean.setEmploymentDurationInMonths("Requierd");
//                msg = 1;
//            }
            if (!validObject.isDescription(employmentBean.getpEmployerName())) {
                invalidMsgBean.setpEmployerName("Invalid");
                msg = 1;
            }
            if (!employmentBean.getpEmployerAddress1().isEmpty()) {
                if (!validObject.isCorrectAddress(employmentBean.getpEmployerAddress1())) {
                    invalidMsgBean.setpEmployerAddress1("Invalid");
                    msg = 1;
                }
            }
            if (!employmentBean.getpEmployerAddress2().isEmpty()) {
                if (!validObject.isCorrectAddress(employmentBean.getpEmployerAddress2())) {
                    invalidMsgBean.setpEmployerAddress2("Invalid");
                    msg = 1;
                }
            }
            if (!employmentBean.getpEmployerAddress3().isEmpty()) {
                if (!validObject.isCorrectAddress(employmentBean.getpEmployerAddress3())) {
                    invalidMsgBean.setpEmployerAddress3("Invalid");
                    msg = 1;
                }
            }
            if (msg == 0) {
                invalidMsgBean = null;
            }

            return invalidMsgBean;

        } catch (Exception ex) {
            throw ex;
        }
    }

    //get Employment StatusList
    private List<StatusBean> getEmploymentStatusList() throws Exception {
        systemUserManager = new SystemUserManager();
        statusList = systemUserManager.getStatusByUserRole("EMPL");
        return statusList;
    }

    private void getAllApplicationStatus(String appliactionId) throws Exception {

        try {
            manager = new CaptureDataManager();
            appStatusBean = manager.getAllApplicationStatus(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllDetailsCustomer(String appliactionId) throws Exception {
        try {
            manager = new CaptureDataManager();
            customerPersonalBean = manager.getAllDetailsCustomer(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllEmpDetails(String appliactionId) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            employmentBean = checkingManager.getCardEmployementDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllIncomeDetails(String appliactionId) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            incomeBeanList = checkingManager.getCardIncomeDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllExpenseDetails(String appliactionId) throws Exception {

        try {
            checkingManager = new ApplicationCheckingManager();
            expensesBean = checkingManager.getExpensesDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllBankDetails(String appliactionId) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            bankDetailsBeanLst = checkingManager.getCardBankDetailsDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllDocumentDetails(String appliactionId) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            documentDetailsBeanLst = checkingManager.getCardDocumentDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setAudittraceValue(HttpServletRequest request, CustomerEmploymentBean employmentBean) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Employment Details in to Application ID : '" + employmentBean.getApplicationId() + "' by :" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CAPTURE_DATA);
            systemAuditBean.setPageCode(PageVarList.USERDATACAPTURE);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
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
     *
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
            Logger.getLogger(AddEmploymentDetailsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddEmploymentDetailsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
