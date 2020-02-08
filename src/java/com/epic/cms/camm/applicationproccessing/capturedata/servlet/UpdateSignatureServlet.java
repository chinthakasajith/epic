/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardExpensesBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardIncomeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerEmploymentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.camm.applicationproccessing.capturedata.util.LoadApplicationStatus;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
public class UpdateSignatureServlet extends HttpServlet {

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
    private SystemAuditBean systemAuditBean;
    private String url = "/camm/capturedata/applicationDataModify.jsp";
    private String urlSup = "/camm/capturedata/modifySupplementaryInputCaptureData.jsp";
    private String urlCardagainstFD = "/camm/capturedata/modifyCardagainstFDInputCaptureData.jsp";
    private String urlEstablishment = "/camm/capturedata/modifyEstablishmentInputCaptureData.jsp";
    private String urlCorporate = "/camm/capturedata/modifyCorporateInputCaptureData.jsp";

    private ApplicationCheckingManager checkingManager;
    private CardApplicationStatusBean appStatusBean = null;
    private CustomerPersonalBean customerPersonalBean = null;
    private CustomerEmploymentBean employmentBean = null;
    private List<CardIncomeBean> incomeBeanList = null;
    private CardExpensesBean expensesBean = null;
    private List<CardBankDetailsBean> bankDetailsBeanLst = null;
    private List<DocumentUploadBean> documentDetailsBeanLst = null;
    private SupplementaryApplicationBean customerSuplimentoryPersonalBean = null;
    private CustomerPersonalBean recDetailsBean = null;
    private String cardAppCategoryCode = null;
    private String recName = null;
    private String recCardno = null;
    private String recPhoneNo = null;
    private String recDate = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            try {
                sessionObj = request.getSession(false);

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

            if (sessionVarlist.getApplicationTypeCode() != null && sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_AGAINST_FD_CODE)) {
                url = urlCardagainstFD;
            } else if (sessionVarlist.getApplicationTypeCode() != null && sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_APP_CATEGORY_ESTABLISMENT_CODE)) {
                url = urlEstablishment;
            } else if (sessionVarlist.getApplicationTypeCode() != null && sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
                url = urlCorporate;
            } else if (sessionVarlist.getApplicationTypeCode() != null && sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)) {
                url = urlSup;
            }

            String remark = "";
            remark = request.getParameter("remark6");
            //get recommendation details
            if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_MAIN)) {
                recName = request.getParameter("recName").trim();
                recCardno = request.getParameter("recCreditCardNum").trim();
                recPhoneNo = request.getParameter("recTpNum").trim();
                recDate = request.getParameter("recDate");

                //set recommendation details to bean
                recDetailsBean = new CustomerPersonalBean();

                recDetailsBean.setRecName(recName);
                recDetailsBean.setRecCreditCardnum(recCardno);
                recDetailsBean.setRecPhone(recPhoneNo);
                recDetailsBean.setRecDate(recDate);
            } else {
                recDetailsBean = null;
            }

            //validate
            CustomerPersonalBean invalidRecBean = new CustomerPersonalBean();
            invalidRecBean = this.isRecommendationDetailsValid(recDetailsBean);

            if (invalidRecBean == null) {
                request.setAttribute("recDetailsBean", recDetailsBean);
                this.setAudittraceValue(request, sessionVarlist.getApplicationId());
                manager = new CaptureDataManager();
                int isAdd = manager.updateSignature(sessionVarlist.getApplicationId(), sessionVarlist.getApplicationId() + "_SIGNATURE", systemAuditBean, recDetailsBean);

                if (isAdd == 1) {
                    checkingManager = new ApplicationCheckingManager();
                    checkingManager.addRemarks(sessionVarlist.getApplicationId(), "6", remark, sessionUser.getUserName());
                    if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_AGAINST_FD_CODE)) {
                        LoadApplicationStatus.loadDefaultCardagainstFDApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, 3, false);

                    } else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
                        LoadApplicationStatus.loadDefaultCorporateApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, 2, false);

                    } else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_ESTABLISHMENT)) {
                        LoadApplicationStatus.loadDefaultEstablishmentApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null, 4, false);

                    } else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_MAIN)) {
                        this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);

                    } else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)) {
                        this.loadDefaultSuplimentoryApplicationStatus(sessionVarlist.getApplicationId(), request);
                    }
                    request.setAttribute("successMsg", "Sucssesfully updated the signature.");
                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);

                } else {
                    if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_AGAINST_FD_CODE)) {
                        LoadApplicationStatus.loadDefaultCardagainstFDApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, 3, false);

                    } else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
                        LoadApplicationStatus.loadDefaultCorporateApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, 2, false);

                    } else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_ESTABLISHMENT)) {
                        LoadApplicationStatus.loadDefaultEstablishmentApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null, 4, false);

                    } else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_MAIN)) {
                        this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
                    } else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)) {
                        this.loadDefaultSuplimentoryApplicationStatus(sessionVarlist.getApplicationId(), request);
                    }
                    request.setAttribute("errorMsg", "Failed updating the signature.");
                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);
                }
            } else {
                request.setAttribute("recDetailsBean", recDetailsBean);
                request.setAttribute("invalidRecBean", invalidRecBean);
                if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_MAIN)) {
                    this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
                    rd = getServletContext().getRequestDispatcher(url);
                }
                rd.forward(request, response);
            }

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
            request.setAttribute("errorMsg", "Error in action");
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);

        } finally {
            out.close();
        }
    }

    private void loadDefaultApplicationStatus(String appliactionId, HttpServletRequest request) throws Exception {

        this.getAllApplicationStatus(appliactionId);
        String selectedTab = "5";

        if (appStatusBean != null) {

            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_CHECKIN) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_ONHOLD)) {

                this.getAllDetailsCustomer(appliactionId);
                request.setAttribute("personalBean", customerPersonalBean);
                sessionVarlist.setPersonalBean(customerPersonalBean);

                this.getAllEmpDetails(appliactionId);
                request.setAttribute("employmentBean", employmentBean);
                sessionVarlist.setEmploymentBean(employmentBean);

                this.getAllIncomeDetails(appliactionId);
                this.getAllExpenseDetails(appliactionId);
                request.setAttribute("expenseBean", expensesBean);

                sessionVarlist.setExpensesBean(expensesBean);
                sessionVarlist.setSessionIncomeList(incomeBeanList);

                this.getAllBankDetails(appliactionId);
                sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);

                this.getAllDocumentDetails(appliactionId);
                sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);

                request.setAttribute("selectedtab", selectedTab);

            } else {
                request.setAttribute("errorMsg", "This application is not in modification status");

            }

        }
    }

    private void loadDefaultSuplimentoryApplicationStatus(String appliactionId, HttpServletRequest request) throws Exception {
        this.getAllApplicationStatus(appliactionId);
        String selectedTab = "2";

        if (appStatusBean != null) {

            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_CHECKIN) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_ONHOLD)) {

                this.getAllDetailsSuplimentoryCustomer(appliactionId);
                request.setAttribute("personalBean", customerSuplimentoryPersonalBean);
                sessionVarlist.setSuplimentoryPersonalBean(customerSuplimentoryPersonalBean);

                this.getAllDocumentDetails(appliactionId);
                sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);
                request.setAttribute("selectedtab", selectedTab);
            } else {
                request.setAttribute("errorMsg", "This application is not in modification status");
            }
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

    private void getAllApplicationStatus(String appliactionId) throws Exception {

        try {
            manager = new CaptureDataManager();
            appStatusBean = manager.getAllApplicationStatus(appliactionId);

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

    //suplementary
    private void getAllDetailsSuplimentoryCustomer(String appliactionId) throws Exception {
        try {
            manager = new CaptureDataManager();
            customerSuplimentoryPersonalBean = manager.getAllDetailsSuplimentoryCustomer(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setAudittraceValue(HttpServletRequest request, String applicationId) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Signature And Complete Application in Application ID : '" + applicationId + "' by :" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.APPLICATIONASSIGN);
            systemAuditBean.setPageCode(PageVarList.CAMMDATAASSIGN);
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

    //validate recommendation details
    private CustomerPersonalBean isRecommendationDetailsValid(CustomerPersonalBean bean) throws Exception {
        UserInputValidator validObject = new UserInputValidator();
        CustomerPersonalBean invalidMsgBean = new CustomerPersonalBean();
        int msg = 0;

        try {
            if (bean != null) {
                if (!bean.getRecName().isEmpty()) {
                    if (!validObject.isNonNumericNonSpecialString(bean.getRecName())) {
                        invalidMsgBean.setRecName("Invalid");
                        msg = 1;
                    }
                }
                if (!bean.getRecCreditCardnum().isEmpty()) {
                    if (!validObject.isNumeric(bean.getRecCreditCardnum())) {
                        invalidMsgBean.setRecCreditCardnum("Invalid");
                        msg = 1;
                    }
                }
                if (!bean.getRecPhone().isEmpty()) {
                    if (!validObject.isPhoneNumber(bean.getRecPhone())) {
                        invalidMsgBean.setRecPhone("Invalid");
                        msg = 1;
                    }
                }
                if (msg == 0) {
                    invalidMsgBean = null;
                }
            }else{
                invalidMsgBean=null;
            }
            return invalidMsgBean;

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
}
