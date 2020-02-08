package com.epic.cms.camm.applicationproccessing.cooperate.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CurrencyMgtManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
//import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DebitPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.AreaBeanCoCustomer;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.CustomerCorporateBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.DocumentUploadCorporateBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.IdCoCustomerBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.VerificationCategoryCorporateBean;
import com.epic.cms.camm.applicationproccessing.cooperate.businesslogic.CorporateCustomerManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
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
 * @author jeevan 
 */
public class LoadCorporateCustomerServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    String url = "/camm/capturedata/cooperatecutomer.jsp";
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private CorporateCustomerManager manager = null;
    private List<CustomerCorporateBean> bankDetailsBeanLst = null;
    private List<DocumentUploadCorporateBean> documentDetailsBeanLst = null;
    private CustomerCorporateBean customerCorporateBean = null;
    private List<AreaBeanCoCustomer> areaList = null;
    private List<CardTypeMgtBean> cardTypeList = null;
    private ApplicationAssignManager appAssignManager = null;
    private HashMap<String, String> identificationList = null;
    private HashMap<String, String> accountTypeList = null;
    private String applicationId = null;
    private String cardCategory = null;
    private CaptureDataManager captureDataManager = null;
    private List<CardProductBean> cardProductMgtList = null;
    private List<CurrencyBean> currencyDetailList = null;
    private List<String> nationalityList;
    private List<CustomerCorporateBean> bankDetailsCoCustBeanLst = null;
    private List<VerificationCategoryCorporateBean> verificationCatCoList = null;
    private DebitPersonalBean debitPersonalBean = null;
    private CurrencyMgtManager currencyObj = null;
    private String cardCategoryCode = null;
    private Boolean checkOutStatus = false;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            //call to existing session
            ////////////////////////////////////////////////////////////////////////////////
            HttpSession sessionObj = request.getSession(false);
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

                try {
                    //set page code and task codes
                    String pageCode = PageVarList.CO_CUSTOMER;
                    String taskCode = TaskVarList.ACCESSPAGE;


                    //check whethre userrole have an access for this page and task          
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        // is valid acess nothing to do
                    } else {

                        //if invalid throw accessdenied exception
                        throw new AccessDeniedException();

                    }

                    //set tasks to the session
                    sessionVarlist.setUserPageTaskList(userTaskList);


                } catch (AccessDeniedException adex) {
                    throw adex;

                }

            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }
            /////////////////////////////////////////////////////////////////////

            try {
//                String applicationId = request.getParameter("applicationid");
//                this.setAudittraceValue(request);
//                this.updateCardApplicationStatus(applicationId);


                applicationId = request.getParameter("applicationid");
                cardCategory = request.getParameter("cardcategory");

                IdCoCustomerBean idBean = new IdCoCustomerBean();
                idBean = this.getIdentifyDetails(applicationId);


                customerCorporateBean = new CustomerCorporateBean();
                customerCorporateBean.setIdentificationCode(idBean.getIdCode());
                customerCorporateBean.setIdentificationNumber(idBean.getIdNumber());
                request.setAttribute("personalBean", customerCorporateBean);

                this.getAllArealist();
                this.getAllIdentificationType();
                this.getAllNationality();
                this.getAllBankDetails();
                this.getVerificationCatCoList();
                this.getAllDocumentDetails(applicationId);

                sessionVarlist.setAreaListCoCustomer(areaList);
                sessionVarlist.setIdentityTypeListCoCustomer(identificationList);
                sessionVarlist.setNationalityCoCustomerList(nationalityList);
                sessionVarlist.setSessionBankDetailCoCustList(bankDetailsBeanLst);
                sessionVarlist.setVerificatioCatCoList(verificationCatCoList);
                sessionVarlist.setSessionDocumentCoList(documentDetailsBeanLst);

                

//                request.setAttribute("bankList", bankDetailsCoCustBeanLst);

                sessionVarlist.setApplicationId(applicationId);
                sessionVarlist.setCardCategory(cardCategory);
                
                // request.setAttribute("personalBean", debitPersonalBean);

                request.setAttribute("selectedtab", "0");               
//                request.setAttribute("selectedtab", "1");
                request.setAttribute("loadTabIndex", "1,2");

                
                
//                for(VerificationCategoryCorporateBean bean:sessionVarlist.getVerificatioCatCoList()){
//                    System.out.println("bean value : " + bean.getCategoryName());
//                }
                
                rd = request.getRequestDispatcher(url);
                rd.forward(request, response);


            } catch (Exception e) {
                request.setAttribute("operationtype", "default");
                request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            }

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
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
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

    private void getAllArealist() throws SQLException, Exception {
        try {

            manager = new CorporateCustomerManager();
            areaList = manager.getAllArealist();
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllIdentificationType() throws SQLException, Exception {
        try {
            manager = new CorporateCustomerManager();
            identificationList = manager.getAllIdentificationType();

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllNationality() throws SQLException, Exception {
        try {

            manager = new CorporateCustomerManager();
            nationalityList = manager.getAllNationality();
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private IdCoCustomerBean getIdentifyDetails(String applicationId) throws SQLException, Exception{
        IdCoCustomerBean idCustBean = new IdCoCustomerBean();
        try {

            manager = new CorporateCustomerManager();
            idCustBean = manager.getIdentifyDetails(applicationId);
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
        return idCustBean;
    }

    private void getAllBankDetails() throws SQLException, Exception{
        try {
            manager = new CorporateCustomerManager();
            bankDetailsBeanLst = manager.getCardBankDetailsDetails();

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getVerificationCatCoList() throws SQLException, Exception {
        try {
            manager = new CorporateCustomerManager();
            verificationCatCoList = manager.getVerificationCatCoList();

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllDocumentDetails(String applicationId) throws SQLException, Exception {
        try {
             manager = new CorporateCustomerManager();
            documentDetailsBeanLst = manager.getAllDocumentDetails(applicationId);

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
