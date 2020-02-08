/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.merchant.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AreaBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantPaymentModeBean;
//import com.epic.cms.reportexp.merchant.bean.MerchantLocationReportBean;
import com.epic.cms.reportexp.merchant.bean.MerchantLocationSearchBean;
import com.epic.cms.reportexp.merchant.businesslogic.MerchantLocationReportManager;
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
 * @author ruwan_e
 */
public class SearchMerchantLocationReportServlet extends HttpServlet {

    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SystemUserManager systemUserManager;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private MerchantLocationSearchBean searchBean;
    private RequestDispatcher rd;
    private MerchantLocationReportManager merchantLocationManager;
    private List<MerchantPaymentModeBean> paymentModeList;
    private ArrayList<StatusBean> statusList;
    private List<AreaBean> areaList;
    private String url = "/reportexp/merchant/merchantlocation/merchantlocreporthome.jsp";
    private ArrayList<MerchantLocationSearchBean> summaryList;

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
                String pageCode = PageVarList.MERCHANT_LOC_REPORT;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        
                        this.getStatusList();
                        this.getPaymentModeList();
                        this.getAreaList();
                        
                        request.setAttribute("paymentModeList", paymentModeList);
                        request.setAttribute("areaList", areaList);
                        
                        this.setUserInputsToBean(request);
                        this.searchMerchantLocationReport(searchBean);
                        sessionVarlist.setStatusList(statusList);
                        
                        request.setAttribute("summaryList", summaryList);
                        
                        sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

//                        if (isValidate(activatedCardReportBean)) {
//                            this.searchActivatedCardReportDetails();
//                        } else {
//                            request.setAttribute("errorMsg", errorMessage);
//                        }
//                        request.setAttribute("userList", userList);
//                        request.setAttribute("searchBeanList", searchBeanList);

                        rd = getServletContext().getRequestDispatcher(url);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_ACTIVATED_CARD_REPORT);

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

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);


        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_ACTIVATED_CARD_REPORT);
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

    private void setUserInputsToBean(HttpServletRequest request) throws Exception {
        try {
            String merchantId = request.getParameter("merchantId").trim();
            String status = request.getParameter("status").trim();
            String customerName = request.getParameter("customerName").trim();
            String terminalID = request.getParameter("terminalID").trim();
            String area = request.getParameter("area").trim();
            String paymentMode = request.getParameter("payment_mode").trim();
            String actDateFrom = request.getParameter("fromDate").trim();
            String actDateTo = request.getParameter("toDate").trim();

            searchBean = new MerchantLocationSearchBean();
            searchBean.setMerchantId(merchantId);
            searchBean.setStatus(status);
            searchBean.setMerchantName(customerName);
            searchBean.setTerminalID(terminalID);
            searchBean.setArea(area);
            searchBean.setPaymentMode(paymentMode);
            searchBean.setActivationDateFrom(actDateFrom);
            searchBean.setActivationDateTo(actDateTo);



        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private void searchMerchantLocationReport(MerchantLocationSearchBean searchBean) throws Exception {

        try {

            merchantLocationManager = new MerchantLocationReportManager();
            summaryList = new ArrayList<MerchantLocationSearchBean>();

            summaryList = (ArrayList<MerchantLocationSearchBean>) merchantLocationManager.searchMerchantLocationDetails(searchBean);

        } catch (Exception ex) {
            throw ex;
        }

    }
    
    private void getStatusList() throws Exception {
        try {
            merchantLocationManager = new MerchantLocationReportManager();
            statusList = merchantLocationManager.getStatusList();
            sessionVarlist.setStatusList(statusList);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getPaymentModeList() throws Exception {
        try {
            merchantLocationManager = new MerchantLocationReportManager();
            paymentModeList = merchantLocationManager.getPaymentModeList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAreaList() throws Exception {
        try {
            merchantLocationManager = new MerchantLocationReportManager();
            areaList = merchantLocationManager.getAreaList();
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
