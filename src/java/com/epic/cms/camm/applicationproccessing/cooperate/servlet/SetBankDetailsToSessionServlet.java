/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.cooperate.servlet;

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
import com.epic.cms.camm.applicationproccessing.cooperate.bean.EstCardManagerDetailsBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.EstablishCardApplicationBean;
import com.epic.cms.camm.applicationproccessing.cooperate.businesslogic.CorporateDataCaptureManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.StatusVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * @author badrika
 */
public class SetBankDetailsToSessionServlet extends HttpServlet {
    
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CaptureDataManager manager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean;
    private String url = "/camm/capturedata/establishcarddatacapture.jsp";
    private ApplicationCheckingManager checkingManager;
    private CardApplicationStatusBean appStatusBean = null;
    private CustomerPersonalBean customerPersonalBean = null;
    private CustomerEmploymentBean employmentBean = null;
    private List<CardIncomeBean> incomeBeanList = null;
    private CardExpensesBean expensesBean = null;
    private List<CardBankDetailsBean> bankDetailsBeanLst = null;
    private List<DocumentUploadBean> documentDetailsBeanLst = null;
    private CorporateDataCaptureManager cdcmanager;
    private EstablishCardApplicationBean companyBean;
    private List<EstCardManagerDetailsBean> managerBeanList;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
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


            CardBankDetailsBean bankDetailBean = new CardBankDetailsBean();
//
            bankDetailBean.setApplicationid(sessionVarlist.getApplicationId());
            bankDetailBean.setBankName(request.getParameter("bankName"));
            bankDetailBean.setBankNameDes(this.getbankName(request.getParameter("bankName")));
            bankDetailBean.setBranchName(request.getParameter("brachName"));
            bankDetailBean.setAccountType(request.getParameter("accType"));
            bankDetailBean.setAccountNumber(request.getParameter("accNumber"));
            bankDetailBean.setSinceYear(request.getParameter("year"));
            bankDetailBean.setSinceMonth(request.getParameter("month"));
            bankDetailBean.setAccountSince("Year: " + request.getParameter("year") + " Months: " + request.getParameter("month"));


            if (!bankDetailBean.getAccountNumber().isEmpty()) {
                if (!UserInputValidator.isNumeric(bankDetailBean.getAccountNumber())) {
                    request.setAttribute("invalidAccount", "Invalid");
                    request.setAttribute("bankDetailBean", bankDetailBean);
                } else {

//
                    if (sessionVarlist.getSessionBankDetailList() != null) {
                        sessionVarlist.getSessionBankDetailList().add(bankDetailBean);


                    } else {
                        List<CardBankDetailsBean> list = new ArrayList<CardBankDetailsBean>();
                        list.add(bankDetailBean);

                        sessionVarlist.setSessionBankDetailList(list);


                    }

                }
            } else {
                request.setAttribute("invalidAccount", "Requied");
                request.setAttribute("bankDetailBean", bankDetailBean);
            }



            this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);




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
        String selectedTab = "0";

        if (appStatusBean != null) {

            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_INITIATE) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_PROCESS)) {
                       
                if (appStatusBean.getTableOne().equals("1")) {
                    this.getAllDetailsCompany(appliactionId);
                    request.setAttribute("companyBean", companyBean);
                    sessionVarlist.setCompanyBean(companyBean);
                    selectedTab = "1";

                } else {

                    loadTabIndex = "0";
                }
                if (appStatusBean.getTableTwo().equals("1")) { 
                    this.getManagerDetails(appliactionId);
                    sessionVarlist.setSessionManagerDetailList(managerBeanList);
                    
                    selectedTab = "2";
                } else {
                    loadTabIndex = loadTabIndex + "," + "1";
                }
                
                if (appStatusBean.getTableThree().equals("1")) {
                    this.getAllBankDetails(appliactionId);
                    sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);
                    
                    selectedTab = "3";

                } else {
                    loadTabIndex = loadTabIndex + "," + "2";
                }
                

            }

        }



        request.setAttribute("loadTabIndex", loadTabIndex);
        request.setAttribute("selectedtab", selectedTab);

    }

    private void getAllDetailsCompany(String appliactionId) throws Exception {
        try {
            cdcmanager = new CorporateDataCaptureManager();
            companyBean = cdcmanager.getAllDetailsCompany(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }
    
      private void getManagerDetails(String appliactionId) throws Exception {
        try {
            cdcmanager = new CorporateDataCaptureManager();
            managerBeanList = cdcmanager.getManagerDetails(appliactionId);

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
    
      private String getbankName(String parameter) {

        String bank = "";
        for (int k = 0; k < sessionVarlist.getBankList().size(); k++) {

            if (sessionVarlist.getBankList().get(k).getBankCode().equals(parameter)) {

                bank = sessionVarlist.getBankList().get(k).getBankName();
                break;
            }

        }
        return bank;
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SetBankDetailsToSessionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SetBankDetailsToSessionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
