/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantlocation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantCustomerBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.businesslogic.MerchantCustomerManager;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.bean.MerchantLocationBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.businesslogic.MerchantLocationManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.RequestVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nalin
 */
public class SetValueToMerchantLocationServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private List<MerchantCategoryBean> notAssigndnedMerchantCatogoryList = null;
    private List<MerchantCategoryBean> assigndnedMerchantCatogoryList = null;
    private List<TypeMgtBean> notAssigndnedTxnTypeList = null;
    private List<TypeMgtBean> assigndnedTxnTypeList = null;
    private List<CurrencyBean> notAssignCurrencyList = null;
    private List<CurrencyBean> assignCurrencyList = null;
    private List<MerchantCustomerBean> merchantCustomerList = null;
    private List<String> userTaskList;
    private MerchantLocationBean merchantLocBean;
    private List<TypeMgtBean> typeList = null;
    private List<MerchantCategoryBean> merchantCategoryList = null;
    private List<CurrencyBean> currencyList = null;
    private String url = "/mtmm/merchantmgt/createmerchantlocation.jsp";

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
            //call to existing session
            /////////////////////////////////////////////////////////////////////
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
            /////////////////////////////////////////////////////////////////////

            try {
                //set page code and task codes
                String pageCode = PageVarList.MERCHANT_LOCATION;
                String taskCode = TaskVarList.ASSIGN;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    MerchantCustomerManager ccm = new MerchantCustomerManager();
                    merchantCustomerList = new ArrayList<MerchantCustomerBean>();
                    //get  MerchantCustomer Name details
                    merchantCustomerList = ccm.getMerchantCustomerDetails();


                    try {
                        setUserInputToBean(request);

                        String merchantId = merchantLocBean.getMerchantId();
                        String merchantCustomerNumber = merchantLocBean.getMerchantCustomerNumber();
                        String opType = request.getParameter("opType");

                        MerchantLocationManager merchantLocManager = new MerchantLocationManager();

                        if (opType.equals("ADD")) {

                            merchantLocManager = new MerchantLocationManager();


                            merchantCategoryList = new ArrayList<MerchantCategoryBean>();
                            merchantCategoryList = merchantLocManager.getMccByMerchantCustomerNumber(merchantCustomerNumber);


                            typeList = new ArrayList<TypeMgtBean>();
                            typeList = merchantLocManager.getTxnTypeByMerchantCustomerNumber(merchantCustomerNumber);


                            currencyList = new ArrayList<CurrencyBean>();
                            currencyList = merchantLocManager.getCurrencyByMerchantCustomerNumber(merchantCustomerNumber);

                            if (merchantLocBean != null) {

                                request.setAttribute("operationtype", "ADD");
                                request.setAttribute("selectedtab", "0");
                                request.setAttribute("merchantLocBean", merchantLocBean);
                                request.setAttribute(RequestVarList.TXNTYPEMGT_LIST, typeList);
                                request.setAttribute(RequestVarList.MERCHANTCATEGORY_LIST, merchantCategoryList);
                                request.setAttribute(RequestVarList.CURRENCY_LIST, currencyList);
                                request.setAttribute(RequestVarList.MERCHANTCUSTOMER_LIST, merchantCustomerList);
                                rd = getServletContext().getRequestDispatcher(url);

                            }

                        }

                        if (opType.equals("UPDATE")) {

                            merchantLocManager = new MerchantLocationManager();

                            //////////get Not Assigned Merchannt Category List

                            notAssigndnedMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                            notAssigndnedMerchantCatogoryList = merchantLocManager.getNotAssignedMccList(merchantId, merchantCustomerNumber);

                            ////////////get Assigned Category List

                            assigndnedMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
                            assigndnedMerchantCatogoryList = merchantLocManager.getAssignedMccList(merchantId);

                            /////////////get Not Assigned Transaction Type List

                            notAssigndnedTxnTypeList = new ArrayList<TypeMgtBean>();
                            notAssigndnedTxnTypeList = merchantLocManager.getNotAssignedTxnTypeList(merchantId, merchantCustomerNumber);

                            /////////////get Assigned Transaction Type List

                            assigndnedTxnTypeList = new ArrayList<TypeMgtBean>();
                            assigndnedTxnTypeList = merchantLocManager.getAssignedTxnTypeList(merchantId);

                            ////////////get Not Assigned Currency List

                            notAssignCurrencyList = new ArrayList<CurrencyBean>();
                            notAssignCurrencyList = merchantLocManager.getNotAssignedCurrencyList(merchantId, merchantCustomerNumber);

                            ///////////get Assigned Currency List
                            assignCurrencyList = new ArrayList<CurrencyBean>();
                            assignCurrencyList = merchantLocManager.getAssignedCurrencyList(merchantId);

                            if (merchantLocBean != null) {

                                request.setAttribute("operationtype", "update");
                                request.setAttribute("selectedtab", "0");
                                request.setAttribute("merchantLocBean", merchantLocBean);
                                request.setAttribute("notAssigndnedMerchantCatogoryList", notAssigndnedMerchantCatogoryList);
                                request.setAttribute("assigndnedMerchantCatogoryList", assigndnedMerchantCatogoryList);
                                request.setAttribute("notAssigndnedTxnTypeList", notAssigndnedTxnTypeList);
                                request.setAttribute("assigndnedTxnTypeList", assigndnedTxnTypeList);
                                request.setAttribute("notAssignCurrencyList", notAssignCurrencyList);
                                request.setAttribute("assignCurrencyList", assignCurrencyList);
                                request.setAttribute(RequestVarList.MERCHANTCUSTOMER_LIST, merchantCustomerList);
                                rd = getServletContext().getRequestDispatcher(url);
                            }

                        }

                        rd.forward(request, response);

                    } catch (Exception e) {
                        request.setAttribute("operationtype", "search");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher("/LoadMerchantLocationServlet");
                        rd.forward(request, response);
                    }

                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }


                //set tasks to the session
//                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                throw adex;

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

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {
        } finally {
            out.close();
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean isSet = true;
        try {

            merchantLocBean = new MerchantLocationBean();

            String merchantId = request.getParameter("merchantId").trim();
            String merchantCustomerNumber = request.getParameter("selectMerchantCustomerName").trim();
            String description = request.getParameter("description").trim();
            String address1 = request.getParameter("address1").trim();
            String address2 = request.getParameter("address2").trim();
            String address3 = request.getParameter("address3").trim();
            String area = request.getParameter("selectArea").trim();
//            String postalCode = request.getParameter("selectPostalCode").trim();
            String country = request.getParameter("selectCountry").trim();
            String tpNumber = request.getParameter("tpNumber").trim();
            String fax = request.getParameter("fax").trim();
            String eMail = request.getParameter("eMail").trim();
            String cpTitle = request.getParameter("selectTitle").trim();
            String cpFirstName = request.getParameter("cpFirstName").trim();
            String cpMiddleName = request.getParameter("cpMiddleName").trim();
            String cpLastName = request.getParameter("cpLastName").trim();
            String cpPosotion = request.getParameter("cpPosotion").trim();
            String feeProfile = request.getParameter("selectFeeProfile").trim();
            String commissionProfile = request.getParameter("selectCommisionProfile").trim();
            String riskProfile = request.getParameter("selectRiskProfile").trim();
            String activationDate = request.getParameter("selectActivationDate").trim();
            String status = request.getParameter("selectStates").trim();



            merchantLocBean.setMerchantId(merchantId);
            merchantLocBean.setMerchantCustomerNumber(merchantCustomerNumber);
            //merchantLocBean.setMerchantCustomerName(merchantCustomerName);
            merchantLocBean.setDescription(description);
            merchantLocBean.setAddress1(address1);
            merchantLocBean.setAddress2(address2);
            merchantLocBean.setAddress3(address3);
            merchantLocBean.setArea(area);
//            merchantLocBean.setPostalCode(postalCode);
            merchantLocBean.setCountry(country);
            merchantLocBean.setCpTitle(cpTitle);
            merchantLocBean.setCpFirstName(cpFirstName);
            merchantLocBean.setCpMiddleName(cpMiddleName);
            merchantLocBean.setCpLastName(cpLastName);
            merchantLocBean.setCpPosotion(cpPosotion);
            merchantLocBean.setTpNumber(tpNumber);
            merchantLocBean.setFax(fax);
            merchantLocBean.seteMail(eMail);
            merchantLocBean.setFeeProfile(feeProfile);
            merchantLocBean.setCommissionProfile(commissionProfile);
            merchantLocBean.setRiskProfile(riskProfile);
            merchantLocBean.setActivationDate(activationDate);
            merchantLocBean.setStatus(status);
            merchantLocBean.setLastUpdatedUser(sessionUser.getUserName());

        } catch (Exception e) {
            isSet = false;
            throw e;

        }

        return isSet;
    }

    /**
     * 
     * @param userrole
     * @param pagecode
     * @param task
     * @return
     * @throws Exception 
     */
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
}
