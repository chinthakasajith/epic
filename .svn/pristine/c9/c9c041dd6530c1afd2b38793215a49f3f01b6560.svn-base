/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardTypeMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AreaBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BankBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BankBranchBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardCategoryBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerEmploymentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EmploymentNatureBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EmploymentTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.IncomeTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.OccupationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.VerificationCategoryBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.DataVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
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
 * @author prageeth_s
 */
public class LoadCorporateCaptureDataServlet extends HttpServlet {

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
    private CardTypeMgtManager cardTypeManager;
    private ApplicationAssignManager appAssignManager;
    private CaptureDataManager manager = new CaptureDataManager();;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private List<StatusBean> statusList = null;
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
    private List<CurrencyBean> currencyList = null;
    private HashMap<String, String> identificationList = null;
    private List<BankBranchBean> bankBranchList = null;
    private List<EstablishmentDetailsBean> companyList;
    private EstablishmentDetailsBean assignedCompany;


    private String url = "/camm/capturedata/corporateInputCaptureData.jsp";

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

            sessionVarlist.setCMSSessionUser(sessionUser);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

            //set page code and task codes
            String pageCode = PageVarList.USERDATACAPTURE;
            String taskCode = TaskVarList.ACCESSPAGE;

            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                CustomerPersonalBean personalBean = new CustomerPersonalBean();
                CustomerEmploymentBean employmentBean = new CustomerEmploymentBean();

                personalBean = (CustomerPersonalBean) request.getAttribute("personalBean");
                employmentBean = (CustomerEmploymentBean) request.getAttribute("employmentBean");

                this.getEmploymentStatusList();
                this.getEmploymentTypeList();
                this.getOccupationTypeList();
                this.getNatureList();
                this.getIncomeTypeList();
                this.getBankList();
                this.getBranchList();
                this.getCurrencyList();
                this.getAllCardTypeList();
                this.getAllArealist();
                this.getAllNationality();
                this.getverificationCategoryList();
                this.getAllIdentificationType();
                this.getCompanyList();

                sessionVarlist.setEmpTypeList(empTypeList);
                sessionVarlist.setOccupationList(occupationList);
                sessionVarlist.setNatureList(natureList);
                sessionVarlist.setIncomeTypeList(incomeTypeList);
                sessionVarlist.setBankList(bankList);
                sessionVarlist.setBranchList(branchList);//chinthaka
                sessionVarlist.setCurrencyDetailList(currencyList);
                sessionVarlist.setCardTypeList(cardTypeLst);
                sessionVarlist.setAreaList(areaList);
                sessionVarlist.setNationalityList(nationalityList);
                sessionVarlist.setVerificationCateLst(verificationCateLst);
                sessionVarlist.setIdentityTypeList(identificationList);
                sessionVarlist.setBankBranchList(getBankBranchList(DataVarList.DFCC_BANK_CODE));
                sessionVarlist.setEstablishedCompanyList(companyList);

                String applicationId = sessionVarlist.getApplicationId();

                request.setAttribute("empList", statusList);

                request.setAttribute("applicationId", applicationId);
                request.setAttribute("personalBean", personalBean);
                request.setAttribute("employmentBean", employmentBean);
                request.setAttribute("operationtype", "add");
//                request.setAttribute("selectedtab", request.getAttribute("selectedTab"));

                // request.setAttribute("loadTabIndex", request.getAttribute("loadTabIndex"));                
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);

            } else {

                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }

        } catch (SQLException ex) {
//            request.setAttribute("operationtype", "add");
//            errorMessage = OracleMessage.getMessege(ex.getMessage());
//            request.setAttribute("errorMsg", errorMessage);
//            rd = getServletContext().getRequestDispatcher(url);
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
            request.setAttribute("selectedtab", "0");
            request.setAttribute("errorMsg", "Error in action");
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);

        } finally {
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

    //get Employment StatusList
    private List<StatusBean> getEmploymentStatusList() throws Exception {
        systemUserManager = new SystemUserManager();
        statusList = systemUserManager.getStatusByUserRole("EMPL");
        return statusList;
    }

    //get Employment type list
    private List<EmploymentTypeBean> getEmploymentTypeList() throws Exception {
        
        empTypeList = manager.getEmpTypelist();
        return empTypeList;
    }

    //get occupation list
    private List<OccupationBean> getOccupationTypeList() throws Exception {
        
        occupationList = manager.getOccupationlist();
        return occupationList;
    }

    //get occupation list
    private List<EmploymentNatureBean> getNatureList() throws Exception {
        
        natureList = manager.getNaturelist();
        return natureList;
    }
    //get occupation list

    private List<IncomeTypeBean> getIncomeTypeList() throws Exception {
        
        incomeTypeList = manager.getIncomeTypeLst();
        return incomeTypeList;
    }
    //get occupation list

    private List<BankBean> getBankList() throws Exception {
        
        bankList = manager.getBankByBankCode(DataVarList.DFCC_BANK_CODE);
        return bankList;
    }
    //get occupation list

    private List<BankBranchBean> getBranchList() throws Exception {
        
        branchList = manager.getAllBranchList();
        return branchList;
    }
    
    public List<BankBranchBean> getBankBranchList(String bankCode) throws Exception {
        bankBranchList = manager.getBankBranchList(bankCode);
        return bankBranchList;
    }

    public void setBankBranchList(List<BankBranchBean> bankBranchList) {
        this.bankBranchList = bankBranchList;
    }    
   
    private void getAllCardTypeList() throws Exception {
        try {

            cardTypeManager = CardTypeMgtManager.getInctance();
            cardTypeLst = cardTypeManager.getAllCardType();
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

    private void getAllArealist() throws Exception {
        try {

            
            areaList = manager.getAllArealist();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllNationality() throws Exception {
        try {

            
            nationalityList = manager.getAllNationality();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getverificationCategoryList() throws Exception {
        try {

            
            verificationCateLst = manager.getAllVerificationCategory();
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

    private void getCurrencyList() throws Exception {
        try {

            
            currencyList = manager.getCurrencyList();
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    
    public List<EstablishmentDetailsBean> getCompanyList() throws Exception {
        try {

            companyList = manager.getEstablishedCompanyList();
        } catch (Exception ex) {
            throw ex;
        }
        return companyList;
    }

    public void setCompanyList(List<EstablishmentDetailsBean> companyList) {
        this.companyList = companyList;
    }

    public EstablishmentDetailsBean getAssignedCompany() {
        return assignedCompany;
    }

    public void setAssignedCompany(EstablishmentDetailsBean assignedCompany) {
        this.assignedCompany = assignedCompany;
    }

}
