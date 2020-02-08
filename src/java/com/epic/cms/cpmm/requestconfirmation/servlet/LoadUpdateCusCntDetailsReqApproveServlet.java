/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.cpmm.requestconfirmation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.callcenter.customer.bean.CustomerContactDetailsChngeHolderBean;
import com.epic.cms.callcenter.customer.businesslogic.CallCenterCustomerOperationManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AreaBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.cpmm.requestconfirmation.bean.CustomerContactDetailsChangeBean;
import com.epic.cms.cpmm.requestconfirmation.businesslogic.RequestConfirmationManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class LoadUpdateCusCntDetailsReqApproveServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    HttpSession sessionObj = null;
    private CallCenterCustomerOperationManager customerManager = null;
    private String customerId = null;
    private List<TypeMgtBean> notAssignedTxnList = null;
    private List<TypeMgtBean> assignedTxnList = null;

    private String section = PageVarList.CUSTOMER_CHANGE_CONTACT_DETAILS;
    private String url = "/cpmm/requestconfirm/approvechangeaddressandcontactdetails.jsp";
    private Integer requestId;
    private List<AreaBean> areaList;
    
    private CustomerContactDetailsChangeBean customerContactDetailsChangeBean = null;
    private CustomerContactDetailsChngeHolderBean changeTracker;
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            sessionObj = request.getSession(false);
            systemUserManager = new SystemUserManager();
            sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
            sessionUser = sessionVarlist.getCMSSessionUser();

            try {

                if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                    throw new NewLoginSessionException();
                }

            } catch (NewLoginSessionException nlex) {
                throw new NewLoginSessionException();

            }

            try {
                //set page code and task codes
                String pageCode = PageVarList.CUSCONTACTCHANGEREQUESTAPPROVE;
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whethre userrole have an access for this page and task
                if (!this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();
                }

                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);

            } catch (AccessDeniedException adex) {
                throw adex;
            }
            
            
            requestId =Integer.parseInt(request.getParameter("reqId"));
            getCustomerContactDetails(request);
            
            this.getAllArealist();
            sessionVarlist.setAreaList(areaList);
            
            rd = getServletContext().getRequestDispatcher(url);

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);

        } catch (AccessDeniedException adex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url + section);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url + section);
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
    
    
    private void getCustomerContactDetails(HttpServletRequest request) throws Exception {
        try {
           
            RequestConfirmationManager reqConfMgr = new RequestConfirmationManager();
            List dataList=reqConfMgr.loadChangedCustomerContactDetaisList(requestId);
            customerContactDetailsChangeBean= (CustomerContactDetailsChangeBean)  dataList.get(0);
            changeTracker= (CustomerContactDetailsChngeHolderBean)  dataList.get(1);
            
            request.setAttribute("personalBean", customerContactDetailsChangeBean);
            setcustomerContactDetailChnges(request);
            
            
            
            
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

    private void getAllArealist() throws Exception{
        try {

            CaptureDataManager manager = new CaptureDataManager();
            areaList = manager.getAllArealist();
        } catch (Exception ex) {
            throw ex;
        }    
    }
    
    
    private void setcustomerContactDetailChnges(HttpServletRequest request) {
        
         request.setAttribute("isPermanentAddressChanged" ,changeTracker.isPermanentAddressChanged() );
         request.setAttribute("isResidenceAddressChanged" , changeTracker.isResidenceAddressChanged());
         request.setAttribute("isBillingAddressChanged" , changeTracker.isBillingAddressChanged());
         request.setAttribute("isCompanyAddressChanged" , changeTracker.isCompanyAddressChanged());
         request.setAttribute("isResPhoneNoChanged"  , changeTracker.isResPhoneNoChanged());
         request.setAttribute("isMobilePhoneNoChanged"  , changeTracker.isMobilePhoneNoChanged());
         request.setAttribute("isOfficePhoneNoChanged"  , changeTracker.isOfficePhoneNoChanged());
         request.setAttribute("isEstMobContactNoChanged"  , changeTracker.isEstMobContactNoChanged());
         request.setAttribute("isEstLandContactNoChanged"  , changeTracker.isEstLandContactNoChanged());
         request.setAttribute("isFaxNoChanged"  , changeTracker.isFaxNoChanged());
         
    }

}
