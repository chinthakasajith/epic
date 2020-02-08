/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CommonConfigurationBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardTypeMgtManager;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CommonConfigurationManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AreaBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AssetBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AssetLiabilityTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BankBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BankBranchBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardCategoryBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardExpensesBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardIncomeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardagainstFDApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerEmploymentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EmploymentNatureBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EmploymentTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentAssetsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentLiabilityBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.IncomeTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.LiabilityBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.OccupationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.VerificationCategoryBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.DataVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.StatusVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
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
public class LoadApplicationModifyDataServlet extends HttpServlet {

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
    private CaptureDataManager captureDataManager;
    private CardTypeMgtManager cardTypeManager;
    private ApplicationAssignManager appAssignManager;
    private ApplicationCheckingManager checkingManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private ApplicationAssignBean assignAppBean;
    private CardApplicationStatusBean appStatusBean = null;
    private CustomerPersonalBean customerPersonalBean = null;
    private CustomerEmploymentBean employmentBean = null;
    private List<CardIncomeBean> incomeBeanList = null;
    private CardExpensesBean expensesBean = null;
    private List<CardBankDetailsBean> bankDetailsBeanLst = null;
    private List<DocumentUploadBean> documentDetailsBeanLst = null;
    private String url = "/camm/capturedata/applicationDataModify.jsp";
    private String urlMain = "/camm/capturedata/applicationDataModify.jsp";
    private String urlSup = "/camm/capturedata/modifySupplementaryInputCaptureData.jsp";
    private String urlFD = "/camm/capturedata/modifyCardagainstFDInputCaptureData.jsp";
    private String urlCorporate = "/camm/capturedata/modifyCorporateInputCaptureData.jsp";
    private String urlEstablishment = "/camm/capturedata/modifyEstablishmentInputCaptureData.jsp";

    private List<EmploymentTypeBean> empTypeList = null;
    private List<OccupationBean> occupationList = null;
    private List<EmploymentNatureBean> natureList = null;
    private List<IncomeTypeBean> incomeTypeList = null;
    private List<BankBean> bankList = null;
    private List<BankBranchBean> branchList = null;
    private List<CardTypeMgtBean> cardTypeLst;
    private List<CardCategoryBean> cardCategoryLst;
    private List<AreaBean> areaList;
    private List<String> nationalityList;
    private List<VerificationCategoryBean> verificationCateLst = null;
    private HashMap<String, String> identificationList = null;
    private List<BankBranchBean> bankBranchList = null;
    private List<EstablishmentDetailsBean> companyList;
    private List<AssetLiabilityTypeBean> assetsLiabilityTypeList = null;
    private List<AssetBean> assetsList = null;
    private List<LiabilityBean> liabilityList = null;
    private List<EstablishmentLiabilityBean> establishmentLiabilityList = null;
    private List<EstablishmentAssetsBean> establishmentAssetList = null;
    private SupplementaryApplicationBean customerSuplimentoryPersonalBean = null;
    private CardagainstFDApplicationBean customerCardagainstFDApplicationBean = null;
    private EstablishmentDetailsBean establishmentDetailsBean = null;
    private CommonConfigurationBean comConfigBean = null;

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

            String appliactionId = request.getParameter("applicationid").trim();
            String cardCategory = request.getParameter("cardcategory");
            String applicationTypeCode = request.getParameter("applicationTypeCode");
            
            sessionVarlist.setApplicationId(appliactionId);
            sessionVarlist.setCardCategory(cardCategory);
            sessionVarlist.setApplicationTypeCode(applicationTypeCode);
            if (applicationTypeCode != null && applicationTypeCode.equals(StatusVarList.CARD_AGAINST_FD_CODE)) {
                url = urlFD;
            } else if (applicationTypeCode != null && applicationTypeCode.equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
                url = urlCorporate;
            } else if (applicationTypeCode != null && applicationTypeCode.equals(StatusVarList.CARD_CATEGORY_ESTABLISHMENT)) {
                url = urlEstablishment;
            } else if (applicationTypeCode != null && applicationTypeCode.equals(StatusVarList.CARD_CATEGORY_MAIN)) {
                url = urlMain;
            } else if (applicationTypeCode != null && applicationTypeCode.equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)) {
                url = urlSup;
            }

            this.getAllApplicationStatus(appliactionId);

            String selectedTab = "0";

            if (appStatusBean != null) {

                if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_CHECKIN) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_ONHOLD)) {

                    if (applicationTypeCode != null && applicationTypeCode.equals(StatusVarList.CARD_CATEGORY_MAIN)) {

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
                        int sum = 0;
                        for (int i = 0; i < incomeBeanList.size(); i++) {
                            sum = sum + Integer.parseInt((incomeBeanList.get(i).getAmount().toString()));
                        }
                        sessionVarlist.setSumOfIncome(sum);
                        this.getAllBankDetails(appliactionId);
                        sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);
                        this.getAllDocumentDetails(appliactionId);
                        sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);

                    } else if (applicationTypeCode != null && applicationTypeCode.equals(StatusVarList.CARD_AGAINST_FD_CODE)) {
                        this.getAllDetailsCustomer(appliactionId);
                        request.setAttribute("personalBean", customerPersonalBean);
                        sessionVarlist.setPersonalBean(customerPersonalBean);
                        this.getAllEmpDetails(appliactionId);
                        request.setAttribute("employmentBean", employmentBean);
                        sessionVarlist.setEmploymentBean(employmentBean);
                        //this.getAllIncomeDetails(appliactionId);
                        //this.getAllExpenseDetails(appliactionId);
                        //request.setAttribute("expenseBean", expensesBean);
                        //sessionVarlist.setExpensesBean(expensesBean);
                        //sessionVarlist.setSessionIncomeList(incomeBeanList);
                        this.getAllBankDetails(appliactionId);
                        sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);
                        this.getAllDocumentDetails(appliactionId);
                        sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);

                    } else if (applicationTypeCode != null && applicationTypeCode.equals(StatusVarList.CARD_CATEGORY_COPORATE)) {

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

                        //metda data
                        this.getEmploymentTypeList();
                        sessionVarlist.setEstablishedCompanyList(companyList);

                    } else if (applicationTypeCode != null && applicationTypeCode.equals(StatusVarList.CARD_CATEGORY_ESTABLISHMENT)) {

                        this.getAllDetailsEstablishment(appliactionId);
                        //request.setAttribute("personalBean", customerPersonalBean);
                        //sessionVarlist.setPersonalBean(customerPersonalBean);                        
                        //this.getAllEmpDetails(appliactionId);
                        //request.setAttribute("employmentBean", employmentBean);
                        //sessionVarlist.setEmploymentBean(employmentBean);
                        //this.getAllIncomeDetails(appliactionId);
                        //this.getAllExpenseDetails(appliactionId);
                        //request.setAttribute("expenseBean", expensesBean);
                        //sessionVarlist.setExpensesBean(expensesBean);
                        //sessionVarlist.setSessionIncomeList(incomeBeanList);
                        this.getAllAssetAndLiabilityDetails(establishmentDetailsBean.getBusinessRegNumber());
                        sessionVarlist.setEstablishmentAssetList(establishmentAssetList);
                        sessionVarlist.setEstablishmentLiabilityList(establishmentLiabilityList);

                        this.getAllBankDetails(appliactionId);
                        sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);
                        this.getAllDocumentDetails(appliactionId);
                        sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);

                        //meta data
                        this.getAssetsLiabilityTypeList();
                        this.getAssetsList();
                        this.getLiabilityList();
                        sessionVarlist.setAssetsLiabilityTypeList(assetsLiabilityTypeList);
                        sessionVarlist.setAssetsList(assetsList);
                        sessionVarlist.setLiabilityList(liabilityList);
                        request.setAttribute("establishmentDetailsBean", establishmentDetailsBean);
                    } else if (applicationTypeCode != null && applicationTypeCode.equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)) {
                        this.getAllDetailsSuplimentoryCustomer(appliactionId);
                        request.setAttribute("personalBean", customerSuplimentoryPersonalBean);
                        sessionVarlist.setSuplimentoryPersonalBean(customerSuplimentoryPersonalBean);

                        this.getAllDocumentDetails(appliactionId);
                        sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);
                    }

                    this.getEmploymentTypeList();
                    this.getOccupationTypeList();
                    this.getNatureList();
                    this.getIncomeTypeList();
                    this.getBankList();
                    this.getBranchList();
                    this.getAllCardTypeList();
                    this.getAllArealist();
                    this.getAllNationality();
                    this.getverificationCategoryList();
                    this.getAllIdentificationType();
                    //get common parameters
                    this.getCommonParameters();

                    sessionVarlist.setEmpTypeList(empTypeList);
                    sessionVarlist.setOccupationList(occupationList);
                    sessionVarlist.setNatureList(natureList);
                    sessionVarlist.setIncomeTypeList(incomeTypeList);
                    sessionVarlist.setBankList(bankList);
                    sessionVarlist.setBranchList(branchList);
                    sessionVarlist.setCardTypeList(cardTypeLst);
                    sessionVarlist.setAreaList(areaList);
                    sessionVarlist.setNationalityList(nationalityList);
                    sessionVarlist.setVerificationCateLst(verificationCateLst);
                    sessionVarlist.setIdentityTypeList(identificationList);
                    sessionVarlist.setBankBranchList(getBankBranchList(DataVarList.DFCC_BANK_CODE));
                    sessionVarlist.setComConfigBean(comConfigBean);

                    request.setAttribute("selectedtab", selectedTab);

                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);

                } else {
                    request.setAttribute("errorMsg", "This application is not in modification status");
                    rd = getServletContext().getRequestDispatcher("/LoadModifyApplicationSearch");
                    rd.forward(request, response);

                }

//                else if(appStatusBean.getApplicationStatus().equals(StatusVarList.APP_FILLING_COMPLETE)){
//                
//                
//                }else if(appStatusBean.getApplicationStatus().equals(StatusVarList.APP_CHECKOUT)){
//                
//                
//                }else if(appStatusBean.getApplicationStatus().equals(StatusVarList.APP_APPROVE_COMPLETE)){
//                
//                
//                }else if(appStatusBean.getApplicationStatus().equals(StatusVarList.APP_INITIATE)){
//                
//                
//                }else if(appStatusBean.getApplicationStatus().equals(StatusVarList.APP_PROCESS)){
//                
//                
//                }else if(appStatusBean.getApplicationStatus().equals(StatusVarList.APP_VERIFY_COMPELTE) ||appStatusBean.getApplicationStatus().equals(StatusVarList.APP_VERIFY_PROCESS)){
//                
//                
//                }
//            this.getAllDocumentDetails(appliactionId);
            }

        } catch (SQLException ex) {
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);

            //catch session exception
        } catch (SesssionExpException sex) {
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
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_USER_ASSIGN_DEFAULT);
            rd = request.getRequestDispatcher(url);

        } finally {

            out.close();
        }
    }

    private void getAllDetailsCustomer(String appliactionId) throws Exception {
        try {
            captureDataManager = new CaptureDataManager();
            customerPersonalBean = captureDataManager.getAllDetailsCustomer(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllApplicationStatus(String appliactionId) throws Exception {

        try {
            captureDataManager = new CaptureDataManager();
            appStatusBean = captureDataManager.getAllApplicationStatus(appliactionId);

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

    //get Employment type list
    private List<EmploymentTypeBean> getEmploymentTypeList() throws Exception {
        captureDataManager = new CaptureDataManager();
        empTypeList = captureDataManager.getEmpTypelist();
        return empTypeList;
    }

    //get occupation list
    private List<OccupationBean> getOccupationTypeList() throws Exception {
        captureDataManager = new CaptureDataManager();
        occupationList = captureDataManager.getOccupationlist();
        return occupationList;
    }

    //get occupation list
    private List<EmploymentNatureBean> getNatureList() throws Exception {
        captureDataManager = new CaptureDataManager();
        natureList = captureDataManager.getNaturelist();
        return natureList;
    }
    //get occupation list

    private List<IncomeTypeBean> getIncomeTypeList() throws Exception {
        captureDataManager = new CaptureDataManager();
        incomeTypeList = captureDataManager.getIncomeTypeLst();
        return incomeTypeList;
    }
    //get occupation list

    private List<BankBean> getBankList() throws Exception {
        captureDataManager = new CaptureDataManager();
        if (sessionVarlist.getApplicationTypeCode() != null
                && sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_AGAINST_FD_CODE)) {
            bankList = captureDataManager.getBankByBankCode(DataVarList.DFCC_BANK_CODE);
        } else {
            bankList = captureDataManager.getAllBankLst();
        }
        return bankList;
    }
    //get occupation list

    private List<BankBranchBean> getBranchList() throws Exception {
        captureDataManager = new CaptureDataManager();
        branchList = captureDataManager.getAllBranchList();
        return branchList;
    }

    private void getAllCardTypeList() throws Exception {
        try {

            cardTypeManager = CardTypeMgtManager.getInctance();
            cardTypeLst = cardTypeManager.getAllCardType();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllArealist() throws Exception {
        try {

            captureDataManager = new CaptureDataManager();
            areaList = captureDataManager.getAllArealist();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllNationality() throws Exception {
        try {

            captureDataManager = new CaptureDataManager();
            nationalityList = captureDataManager.getAllNationality();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getverificationCategoryList() throws Exception {
        try {

            captureDataManager = new CaptureDataManager();
            verificationCateLst = captureDataManager.getAllVerificationCategory();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllIdentificationType() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            identificationList = appAssignManager.getAllIdentificationType();

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

    public List<BankBranchBean> getBankBranchList(String bankCode) throws Exception {
        captureDataManager = new CaptureDataManager();
        bankBranchList = captureDataManager.getBankBranchList(bankCode);
        return bankBranchList;
    }

    public void setBankBranchList(List<BankBranchBean> bankBranchList) {
        this.bankBranchList = bankBranchList;
    }

    public List<EstablishmentDetailsBean> getCompanyList() throws Exception {
        try {
            captureDataManager = new CaptureDataManager();
            companyList = captureDataManager.getEstablishedCompanyList();
        } catch (Exception ex) {
            throw ex;
        }
        return companyList;
    }

    public void setCompanyList(List<EstablishmentDetailsBean> companyList) {
        this.companyList = companyList;
    }

    public List<AssetLiabilityTypeBean> getAssetsLiabilityTypeList() throws Exception {
        try {
            captureDataManager = new CaptureDataManager();
            assetsLiabilityTypeList = captureDataManager.getAssetsLiabilityTypeList();
        } catch (Exception ex) {
            throw ex;
        }
        return assetsLiabilityTypeList;
    }

    public List<AssetBean> getAssetsList() throws Exception {
        try {
            captureDataManager = new CaptureDataManager();
            assetsList = captureDataManager.getAssetsList();
        } catch (Exception ex) {
            throw ex;
        }
        return assetsList;
    }

    public List<LiabilityBean> getLiabilityList() throws Exception {
        try {
            captureDataManager = new CaptureDataManager();
            liabilityList = captureDataManager.getLiabilityList();
        } catch (Exception ex) {
            throw ex;
        }
        return liabilityList;
    }

    private void getAllDetailsEstablishment(String appliactionId) throws Exception {
        try {
            captureDataManager = new CaptureDataManager();
            establishmentDetailsBean = captureDataManager.getAllDetailsEstablishment(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllAssetAndLiabilityDetails(String businessRegNo) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            establishmentAssetList = checkingManager.getAllAssetDetails(businessRegNo);
            establishmentLiabilityList = checkingManager.getAllLiabilityDetails(businessRegNo);
            //establishmentAssetList = new ArrayList<EstablishmentAssetsBean>();
            //establishmentLiabilityList =  new ArrayList<EstablishmentLiabilityBean>();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllDetailsSuplimentoryCustomer(String appliactionId) throws Exception {
        try {
            captureDataManager = new CaptureDataManager();
            customerSuplimentoryPersonalBean = captureDataManager.getAllDetailsSuplimentoryCustomer(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    //get commen parameters
    private void getCommonParameters() throws Exception {
        try {
            comConfigBean = CommonConfigurationManager.getInstance().getCommonConfiguration();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
