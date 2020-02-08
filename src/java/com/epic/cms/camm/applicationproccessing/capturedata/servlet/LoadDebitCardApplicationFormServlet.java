/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CreditScoreRecommandManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CurrencyMgtManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AreaBean;
//import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DebitPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.IdBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class LoadDebitCardApplicationFormServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String url = "/camm/capturedata/debitcardapplicationform.jsp";
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private CaptureDataManager manager = null;
    private List<AreaBean> areaList = null;
    private List<CardTypeMgtBean> cardTypeList = null;
    private ApplicationAssignManager appAssignManager = null;
    private HashMap<String, String> identificationList = null;
    private HashMap<String, String> accountTypeList = null;
    private String applicationId = null;
    private String cardCategory = null;
    private IdBean idBean = null;
    private CaptureDataManager captureDataManager = null;
    private List<CardProductBean> cardProductMgtList = null;
    private List<CurrencyBean> currencyDetailList = null;
    private DebitPersonalBean debitPersonalBean = null;
    private CurrencyMgtManager currencyObj = null;
    private String cardCategoryCode = null;
    
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
                
                try {
                    //set page code and task codes
                    String pageCode = PageVarList.DEBITDATACAPTURE;
                    String taskCode = TaskVarList.ACCESSPAGE;
                    
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    } else {
                        throw new AccessDeniedException();
                        
                    }
                    
                    sessionVarlist.setUserPageTaskList(userTaskList);
                    
                } catch (AccessDeniedException adex) {
                    throw adex;
                    
                }
                
            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }
            
            applicationId = request.getParameter("applicationid");
            cardCategory = request.getParameter("cardcategory");
            sessionVarlist.setPrimerySignatureFileName(null);
            sessionVarlist.setPrimeryNicFileName(null);
            sessionVarlist.setCardProductMgtList(null);
            
            
            this.getAllArealist();
            this.getAllIdentificationType();
            this.getIdentifyDetails();
            this.getAllCardType();
            // this.getAllCardProduct();
            this.checkCardStatus(applicationId);
            this.getAllAccountType();
            this.getCurrencyDetails();
            this.getCardCategory(applicationId);
            
            sessionVarlist.setAreaList(areaList);
            sessionVarlist.setIdentityTypeList(identificationList);
            sessionVarlist.setCardTypeList(cardTypeList);
            //sessionVarlist.setCardProductMgtList(cardProductMgtList);
            sessionVarlist.setApplicationId(applicationId);
            sessionVarlist.setCardCategory(cardCategory);
            sessionVarlist.setIdBean(idBean);
            sessionVarlist.setAccountTypeList(accountTypeList);
            sessionVarlist.setCurrencyDetailList(currencyDetailList);
            sessionVarlist.setCardCategoryCode(cardCategoryCode);
            
            
            
            if (debitPersonalBean == null) {
                
                debitPersonalBean = new DebitPersonalBean();
                debitPersonalBean.setAccNo1Currency(this.getCommonCurrency());
                debitPersonalBean.setAccNo2Currency(this.getCommonCurrency());
                
                request.setAttribute("personalBean", debitPersonalBean);
                request.setAttribute("selectedtab", "0");
                request.setAttribute("disableTabIndex", "1");
            } else if (debitPersonalBean != null) {
                
                this.getAllCardProductByCardType(debitPersonalBean.getCardType());
                sessionVarlist.setCardProductMgtList(cardProductMgtList);
                
                request.setAttribute("personalBean", debitPersonalBean);
                sessionVarlist.setPrimerySignatureFileName(debitPersonalBean.getSignatureFileName());
                sessionVarlist.setPrimeryNicFileName(debitPersonalBean.getNicFileName());
                request.setAttribute("selectedtab", "1");
            }
            
            rd = getServletContext().getRequestDispatcher(url);
            
        } catch (SQLException ex) {
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            
            rd = getServletContext().getRequestDispatcher(url);
            
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            
            
            
        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            
            
        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            
            
            
        } catch (Exception ex) {
            
            request.setAttribute("errorMsg", "Error in action");
            rd = request.getRequestDispatcher(url);
            
        } finally {
            rd.forward(request, response);
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
    
    private void getAllArealist() throws Exception {
        try {
            
            manager = new CaptureDataManager();
            areaList = new ArrayList<AreaBean>();
            areaList = manager.getAllArealist();
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private void getAllIdentificationType() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            identificationList = new HashMap<String, String>();
            identificationList = appAssignManager.getAllIdentificationType();
            
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private void getIdentifyDetails() throws Exception {
        try {
            captureDataManager = new CaptureDataManager();
            idBean = new IdBean();
            
            idBean = captureDataManager.getIdentifyDetails(applicationId);
            
            
        } catch (Exception ex) {
            throw ex;
        }
        
    }
    
    private void getAllCardType() throws Exception {
        try {
            cardTypeList = new ArrayList<CardTypeMgtBean>();
            cardTypeList = CreditScoreRecommandManager.getInstance().getAllCardType();
        } catch (Exception ex) {
            throw ex;
        }
        
    }
    
    private void getAllCardProductByCardType(String cardType) throws Exception {
        try {
            cardProductMgtList = new ArrayList<CardProductBean>();
            captureDataManager = new CaptureDataManager();
            
            cardProductMgtList = captureDataManager.getAllCardProductByCardType(cardType);
        } catch (Exception ex) {
            throw ex;
        }
        
    }
    
    private void getAllAccountType() throws Exception {
        try {
            accountTypeList = new HashMap<String, String>();
            captureDataManager = new CaptureDataManager();
            
            accountTypeList = captureDataManager.getAllAccountType();
        } catch (Exception ex) {
            throw ex;
        }
        
    }
    
    private void checkCardStatus(String applicationId) throws Exception {
        
        try {
            
            captureDataManager = new CaptureDataManager();
            debitPersonalBean = new DebitPersonalBean();
            debitPersonalBean = captureDataManager.checkCardApplicationStatus(applicationId);
            
            
        } catch (Exception ex) {
            throw ex;
        }
        
    }
    
    private void getCurrencyDetails() throws Exception {
        
        try {
            
            currencyObj = new CurrencyMgtManager();
            currencyDetailList = new ArrayList<CurrencyBean>();
            /////////get currency list and set to session
            currencyDetailList = currencyObj.getCurrencyDetails();
            
            
        } catch (Exception ex) {
            throw ex;
        }
        
    }
    
    private void getCardCategory(String appID) throws Exception {
        
        try {
            
            captureDataManager = new CaptureDataManager();
            cardCategoryCode = captureDataManager.getCardCategory(appID);
        } catch (Exception ex) {
            throw ex;
        }
        
    }
    
    private String getCommonCurrency() throws Exception {
        String comminCurrency = null;
        try {
            captureDataManager = new CaptureDataManager();
            comminCurrency = captureDataManager.getCommonCurrency();
            
            
        } catch (Exception ex) {
            throw ex;
        }
        return comminCurrency;
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
}
