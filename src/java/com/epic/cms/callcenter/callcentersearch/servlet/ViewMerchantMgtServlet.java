/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.callcentersearch.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.callcenter.callcentersearch.bean.MerchantSearchBean;
import com.epic.cms.callcenter.callcentersearch.bean.ViewMerchantCustomerBean;
import com.epic.cms.callcenter.callcentersearch.bean.ViewMerchantLocationBean;
import com.epic.cms.callcenter.callcentersearch.bean.ViewTerminalBean;
import com.epic.cms.callcenter.callcentersearch.businesslogic.CallCenterMgtManager;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantCustomerBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.businesslogic.MerchantCustomerManager;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.bean.MerchantLocationBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.businesslogic.MerchantLocationManager;
import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.TerminalDataCaptureBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.businesslogic.TerminalDataCaptureManager;
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
import java.util.List;
import java.util.Map;
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
public class ViewMerchantMgtServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private Map<SectionBean, List<PageBean>> sectionPageList;
    private List<String> userTaskList;
    private String url = "/aquirecallcenter/acquiresearchadvancedview.jsp";
    private RequestDispatcher rd;
    private CallCenterMgtManager callCenterMgtManager;
    private MerchantSearchBean searchbean;
    private List<ViewMerchantCustomerBean> cusList;
    private List<ViewMerchantLocationBean> locList;
    private List<ViewTerminalBean> terList;
    private ViewMerchantCustomerBean cusBean;
    private ViewMerchantLocationBean locBean;
    private ViewTerminalBean terBean;
    private MerchantCustomerManager merchantManager;
    private List<MerchantCategoryBean> notAssigndnMerchantCatogoryList;
    private List<MerchantCategoryBean> assigndnMerchantCatogoryList;
    private List<TypeMgtBean> notAssigndnTxnTypeList;
    private List<TypeMgtBean> assigndnTxnTypeList;
    private List<CurrencyBean> notAssignCurrencyList;
    private List<CurrencyBean> assignCurrencyList;
    private List<MerchantCustomerBean> beanList;
    private MerchantCustomerBean merchantBean;
    private MerchantLocationManager merchantLocManager;
    private List<MerchantLocationBean> beanList2;
    private MerchantLocationBean merchantLocBean;
    private List<MerchantCategoryBean> notAssigndnedMerchantCatogoryList;
    private List<MerchantCategoryBean> assigndnedMerchantCatogoryList;
    private List<TypeMgtBean> notAssigndnedTxnTypeList;
    private List<TypeMgtBean> assigndnedTxnTypeList;
    private TerminalDataCaptureManager terminalManager;
    private List<TerminalDataCaptureBean> notAssignedTxn;
    private List<TerminalDataCaptureBean> assignedTxn;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            HttpSession sessionObj = request.getSession(false);

            String applicationType = "ACCUSE";
            String section = request.getParameter("section");

            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                this.getSectionPage(sessionUser.getUserRole(), applicationType, section);
                sessionUser.setSectionPageList(sectionPageList);
                sessionVarlist.setCMSSessionUser(sessionUser);

                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }
                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();
                }

                try {
                    String pageCode = PageVarList.AQCALLCENTERSEARCH;
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
                throw new SesssionExpException();
            }

            /////////////////

            String viewId = request.getParameter("id");

            searchbean = sessionVarlist.getMerchantSearchBean();

            if (section.equals("ACCMCU")) {

                request.setAttribute("operationtype", "cus");
                //request.setAttribute("cusBean", cusBean);

                /////////////////////////
         
                String merchantCustomerNumber = request.getParameter("id");
                sessionVarlist.setMerchantCustomerNumber(merchantCustomerNumber);
                //String merchantCustomerNumber = request.getParameter("merchantCustomerNumber");

                merchantManager = new MerchantCustomerManager();

                //////////get Not Assigned Merchannt Category List

                notAssigndnMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                notAssigndnMerchantCatogoryList = merchantManager.getNotAssignedMccList(merchantCustomerNumber);

                ////////////get Assigned Category List

                assigndnMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                assigndnMerchantCatogoryList = merchantManager.getAssignedMccList(merchantCustomerNumber);

                /////////////get Not Assigned Transaction Type List

                notAssigndnTxnTypeList = new ArrayList<TypeMgtBean>();
                notAssigndnTxnTypeList = merchantManager.getNotAssignedTxnTypeList(merchantCustomerNumber);

                /////////////get Assigned Transaction Type List

                assigndnTxnTypeList = new ArrayList<TypeMgtBean>();
                assigndnTxnTypeList = merchantManager.getAssignedTxnTypeList(merchantCustomerNumber);

                //////////////get Not Assignd Currency List
                notAssignCurrencyList = new ArrayList<CurrencyBean>();
                notAssignCurrencyList = merchantManager.getNotAssignedCurrencyList(merchantCustomerNumber);

                ///////////////get Assigned Currency List
                assignCurrencyList = new ArrayList<CurrencyBean>();
                assignCurrencyList = merchantManager.getAssignedCurrencyList(merchantCustomerNumber);


                beanList = new ArrayList<MerchantCustomerBean>();
                beanList = merchantManager.getMerchantCustomerDetails();
                merchantBean = new MerchantCustomerBean();

                for (MerchantCustomerBean newMerchantBean : beanList) {

                    if (newMerchantBean.getMerchantCustomerNumber().equals(merchantCustomerNumber)) {
                        merchantBean = newMerchantBean;
                    }
                }
                if (merchantBean != null) {
                    request.setAttribute("operationtype", "cus");
                    request.setAttribute("selectedtab", "0");
                    request.setAttribute("merchantBean", merchantBean);
                    request.setAttribute("notAssigndnMerchantCatogoryList", notAssigndnMerchantCatogoryList);
                    request.setAttribute("assigndnMerchantCatogoryList", assigndnMerchantCatogoryList);
                    request.setAttribute("notAssigndnTxnTypeList", notAssigndnTxnTypeList);
                    request.setAttribute("assigndnTxnTypeList", assigndnTxnTypeList);
                    request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                    request.setAttribute("assignCurrencyList", assignCurrencyList);
                    rd = getServletContext().getRequestDispatcher(url);
                }



                /////////////////////////

                //sessionVarlist.setCardNumber(cardBean.getCardNumber());


            } else if (section.equals("ACCMLO")) {

                request.setAttribute("operationtype", "loc");
                // request.setAttribute("locBean", locBean);

                //String merchantId = request.getParameter("merchantId");
                String merchantId = request.getParameter("id");
                sessionVarlist.setMerchantId(merchantId);
                
                String merCusNo = request.getParameter("merCusNo");//merCusNo
                sessionVarlist.setMerchantCustomerNumber(merCusNo);

                merchantLocManager = new MerchantLocationManager();


                beanList2 = new ArrayList<MerchantLocationBean>();
                beanList2 = merchantLocManager.getMerchantLocationDetails();

                merchantLocBean = new MerchantLocationBean();


                for (MerchantLocationBean newMerchantLocBean : beanList2) {

                    if (newMerchantLocBean.getMerchantId().equals(merchantId)) {
                        merchantLocBean = newMerchantLocBean;
                    }
                }

                //////////get Not Assigned Merchannt Category List

                notAssigndnedMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                notAssigndnedMerchantCatogoryList = merchantLocManager.getNotAssignedMccList(merchantId, merchantLocBean.getMerchantCustomerNumber());

                ////////////get Assigned Category List

                assigndnedMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                assigndnedMerchantCatogoryList = merchantLocManager.getAssignedMccList(merchantId);

                /////////////get Not Assigned Transaction Type List

                notAssigndnedTxnTypeList = new ArrayList<TypeMgtBean>();
                notAssigndnedTxnTypeList = merchantLocManager.getNotAssignedTxnTypeList(merchantId, merchantLocBean.getMerchantCustomerNumber());

                /////////////get Assigned Transaction Type List

                assigndnedTxnTypeList = new ArrayList<TypeMgtBean>();
                assigndnedTxnTypeList = merchantLocManager.getAssignedTxnTypeList(merchantId);

                ////////////get Not Assigned Currency List

                notAssignCurrencyList = new ArrayList<CurrencyBean>();
                notAssignCurrencyList = merchantLocManager.getNotAssignedCurrencyList(merchantId, merchantLocBean.getMerchantCustomerNumber());

                ///////////get Assigned Currency List
                assignCurrencyList = new ArrayList<CurrencyBean>();
                assignCurrencyList = merchantLocManager.getAssignedCurrencyList(merchantId);

                if (merchantLocBean != null) {
                    request.setAttribute("operationtype", "loc");
                    request.setAttribute("selectedtab", "0");
                    request.setAttribute("merchantLocBean", merchantLocBean);
                    request.setAttribute("notAssigndnedMerchantCatogoryList", notAssigndnedMerchantCatogoryList);
                    request.setAttribute("assigndnedMerchantCatogoryList", assigndnedMerchantCatogoryList);
                    request.setAttribute("notAssigndnedTxnTypeList", notAssigndnedTxnTypeList);
                    request.setAttribute("assigndnedTxnTypeList", assigndnedTxnTypeList);
                    request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                    request.setAttribute("assignCurrencyList", assignCurrencyList);
                    //request.setAttribute("beanList", beanList);
                    rd = getServletContext().getRequestDispatcher(url);

                }

            } else if (section.equals("ACCTER")) {

                request.setAttribute("operationtype", "ter");
                //request.setAttribute("terBean", terBean);

                String mid = request.getParameter("mid");
                String tid = request.getParameter("id");
                sessionVarlist.setTerminalId(tid);
                sessionVarlist.setMerchantId(mid);                
                
                terminalManager = new TerminalDataCaptureManager();
                assignedTxn = terminalManager.getAssignedTransactions(tid);
                notAssignedTxn = terminalManager.getNotAssignedTransactions(mid, tid);

                TerminalDataCaptureBean terminalBean = new TerminalDataCaptureBean();

                //retrieve the relevant record
                terminalBean = terminalManager.viewOneTerminalData(request.getParameter("id"));

                if (terminalBean != null) {
                    request.setAttribute("operationtype", "ter");
                    request.setAttribute("trmnlBean", terminalBean);
                    request.setAttribute("assignedTxn", assignedTxn);
                    request.setAttribute("notAssignedTxn", notAssignedTxn);

                    rd = getServletContext().getRequestDispatcher(url);
                }

            }

            rd = getServletContext().getRequestDispatcher(url);

            /////////////

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
            //rd.forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
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

//    private void searchMerchant() throws Exception {
//        try {
//
//            callCenterMgtManager = new CallCenterMgtManager();
//            cusList = callCenterMgtManager.searchMerchantCustomer(searchbean);
//            locList = callCenterMgtManager.searchMerchantLocation(searchbean);
//            terList = callCenterMgtManager.searchTerminal(searchbean);
//        } catch (Exception e) {
//            throw e;
//        }
//
//
//    }
    private Map<SectionBean, List<PageBean>> getSectionPage(String userRole, String applicationType, String sectionCode) throws Exception {

        callCenterMgtManager = new CallCenterMgtManager();
        //userManager = new SystemUserManager();
        sectionPageList = callCenterMgtManager.getSectionPage(userRole, applicationType, sectionCode);
        //sectionPageList = userManager.getSectionPage(userRole, applicationType);
        return sectionPageList;
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
