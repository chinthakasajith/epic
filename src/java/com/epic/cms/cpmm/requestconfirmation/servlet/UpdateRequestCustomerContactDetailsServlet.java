/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.cpmm.requestconfirmation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.callcenter.callcentersearch.businesslogic.CallCenterMgtManager;
import com.epic.cms.callcenter.customer.bean.CustomerContactDetailsChngeHolderBean;
import com.epic.cms.callcenter.customer.businesslogic.CallCenterCustomerOperationManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AreaBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.cpmm.requestconfirmation.businesslogic.CardChangeApproveManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
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
public class UpdateRequestCustomerContactDetailsServlet extends HttpServlet {

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
    private String applicationId;
    private String cardCategory;
    private List<AreaBean> areaList;
    private CardChangeApproveManager cdChngMgr;

    private CustomerPersonalBean customerPersonalBean = null;
    private CustomerPersonalBean invalidMsgBean;
    private CustomerContactDetailsChngeHolderBean changeTracker;
    private String action;
    private String requestId;

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

            applicationId = request.getParameter("applicationId");
            cardCategory = request.getParameter("cardCategory");
            requestId  = request.getParameter("requestId");
            action =request.getParameter("action");
            
            
            getcustomerContactDetailChnges(request);
            getCustomerContactDetails(request);
            
           
            if(action!=null && !action.isEmpty()){
                switch (action) {
                    case "approve":
                        if (validateCustomerContactDetails()) {
                            //insert dasta to req table
                            cdChngMgr = new CardChangeApproveManager();
                            cdChngMgr.updateApprovedCustomerDetails(customerPersonalBean, changeTracker, cardCategory,requestId);
                            request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.SUCCESS_UPDATE);
                            
                        } else {
                            setcustomerContactDetailChnges(request);
                            request.setAttribute("invalidMsgBean", invalidMsgBean);
                            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.FORM_VALIDATION_GENERIC_ERR_MSG);
                        }   break;
                    case "reject":
                         String remark  = request.getParameter("remark");
                         cdChngMgr.updateRejectCardCustomerPersonalDetails(sessionVarlist.getCMSSessionUser().getUserName(), remark,requestId);
                        
                        break;
                }
            
            }
            
            request.setAttribute("personalBean", customerPersonalBean);
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

    private void getAllArealist() throws Exception {
        try {

            CaptureDataManager manager = new CaptureDataManager();
            areaList = manager.getAllArealist();
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

    private boolean validateCustomerContactDetails() {
        invalidMsgBean = new CustomerPersonalBean();
        UserInputValidator validObject = new UserInputValidator();
        int msg = 0;

        //Permanent Address
        if (changeTracker.isPermanentAddressChanged()) {
            if (customerPersonalBean.getPermentAddress1().isEmpty()) {
                invalidMsgBean.setPermentAddress1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(customerPersonalBean.getPermentAddress1())) {
                invalidMsgBean.setPermentAddress1("Invalid");
                msg = 1;
            }

            if (customerPersonalBean.getPermentAddress2().isEmpty()) {
                invalidMsgBean.setPermentAddress1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(customerPersonalBean.getPermentAddress2())) {
                invalidMsgBean.setPermentAddress2("Invalid");
                msg = 1;
            }

            if (customerPersonalBean.getPermentAddress3().isEmpty()) {
                invalidMsgBean.setPermentAddress3("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(customerPersonalBean.getPermentAddress3())) {
                invalidMsgBean.setPermentAddress3("Invalid");
                msg = 1;
            }

            if (customerPersonalBean.getPermentCity().isEmpty()) {
                invalidMsgBean.setPermentCity("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(customerPersonalBean.getPermentCity())) {
                invalidMsgBean.setPermentCity("Invalid");
                msg = 1;
            }

        }

        if (changeTracker.isResidenceAddressChanged()) {
            //Res Address
            if (customerPersonalBean.getAddress1().isEmpty()) {
                invalidMsgBean.setAddress1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(customerPersonalBean.getAddress1())) {
                invalidMsgBean.setAddress1("Invalid");
                msg = 1;
            }
            if (customerPersonalBean.getAddress2().isEmpty()) {
                  //  invalidMsgBean.setAddress2("Requierd");
                  //msg = 1;
            } else if (!validObject.isCorrectAddress(customerPersonalBean.getAddress2())) {
                invalidMsgBean.setAddress2("Invalid");
                msg = 1;
            }
            if (customerPersonalBean.getAddress3().isEmpty()) {
                  //invalidMsgBean.setAddress3("Requierd");
                  //msg = 1;
            } else if (!validObject.isCorrectAddress(customerPersonalBean.getAddress3())) {
                invalidMsgBean.setAddress3("Invalid");
                msg = 1;
            }
            if (customerPersonalBean.getCity().isEmpty()) {
                invalidMsgBean.setCity("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(customerPersonalBean.getCity())) {
                invalidMsgBean.setCity("Invalid");
                msg = 1;
            }
        }

        if (changeTracker.isBillingAddressChanged()) {
            //Billing Address
            if (customerPersonalBean.getBillAddress1().isEmpty()) {
                invalidMsgBean.setBillAddress1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(customerPersonalBean.getBillAddress1())) {
                invalidMsgBean.setBillAddress1("Invalid");
                msg = 1;
            }

            if (customerPersonalBean.getBillAddress2().isEmpty()) {
                invalidMsgBean.setBillAddress1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(customerPersonalBean.getBillAddress2())) {
                invalidMsgBean.setBillAddress2("Invalid");
                msg = 1;
            }

            if (customerPersonalBean.getBillAddress3().isEmpty()) {
                invalidMsgBean.setBillAddress3("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(customerPersonalBean.getBillAddress3())) {
                invalidMsgBean.setBillAddress3("Invalid");
                msg = 1;
            }

            if (customerPersonalBean.getBillCity().isEmpty()) {
                invalidMsgBean.setBillCity("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(customerPersonalBean.getBillCity())) {
                invalidMsgBean.setBillCity("Invalid");
                msg = 1;
            }
        }

        if (changeTracker.isCompanyAddressChanged()) {
            //Office address
            if (customerPersonalBean.getCompanyAddress1().isEmpty()) {
                invalidMsgBean.setCompanyAddress1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(customerPersonalBean.getCompanyAddress1())) {
                invalidMsgBean.setCompanyAddress1("Invalid");
                msg = 1;
            }

            if (customerPersonalBean.getCompanyAddress2().isEmpty()) {
                invalidMsgBean.setCompanyAddress1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(customerPersonalBean.getCompanyAddress2())) {
                invalidMsgBean.setCompanyAddress2("Invalid");
                msg = 1;
            }

            if (customerPersonalBean.getCompanyAddress3().isEmpty()) {
                invalidMsgBean.setCompanyAddress3("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(customerPersonalBean.getCompanyAddress3())) {
                invalidMsgBean.setCompanyAddress3("Invalid");
                msg = 1;
            }

            if (customerPersonalBean.getCompanyCity().isEmpty()) {
                invalidMsgBean.setCompanyCity("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(customerPersonalBean.getCompanyCity())) {
                invalidMsgBean.setCompanyCity("Invalid");
                msg = 1;
            }
        }

        //Tel NO
        if (changeTracker.isResPhoneNoChanged()) {
            if (customerPersonalBean.getHomeTelNumber().isEmpty()) {
                invalidMsgBean.setHomeTelNumber("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(customerPersonalBean.getHomeTelNumber())) {
                invalidMsgBean.setHomeTelNumber("Invalid");
                msg = 1;
            }

        }

        if (changeTracker.isMobilePhoneNoChanged()) {
            if (customerPersonalBean.getOfficeTelNumber().isEmpty()) {
                invalidMsgBean.setOfficeTelNumber("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(customerPersonalBean.getOfficeTelNumber())) {
                invalidMsgBean.setOfficeTelNumber("Invalid");
                msg = 1;
            }

        }

        if (changeTracker.isOfficePhoneNoChanged()) {
            if (customerPersonalBean.getMobileNumber().isEmpty()) {
                invalidMsgBean.setMobileNumber("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(customerPersonalBean.getMobileNumber())) {
                invalidMsgBean.setMobileNumber("Invalid");
                msg = 1;
            }
        }

        if (changeTracker.isEstMobContactNoChanged()) {
            //Establishment contact details
            if (customerPersonalBean.getEstMobileNo().isEmpty()) {
                invalidMsgBean.setEstMobileNo("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(customerPersonalBean.getEstMobileNo())) {
                invalidMsgBean.setEstMobileNo("Invalid");
                msg = 1;
            }

        }

        if (changeTracker.isEstLandContactNoChanged()) {
            if (customerPersonalBean.getEstLandNo().isEmpty()) {
                invalidMsgBean.setEstLandNo("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(customerPersonalBean.getEstLandNo())) {
                invalidMsgBean.setEstLandNo("Invalid");
                msg = 1;
            }

        }

        if (changeTracker.isFaxNoChanged()) {
            if (customerPersonalBean.getFaxNo().isEmpty()) {
                invalidMsgBean.setFaxNo("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(customerPersonalBean.getFaxNo())) {
                invalidMsgBean.setFaxNo("Invalid");
                msg = 1;
            }
        }

        return msg != 1;
    }

    private void getCustomerContactDetails(HttpServletRequest request) {
        customerPersonalBean = new CustomerPersonalBean();
        customerPersonalBean.setApplicationId(applicationId);

        customerPersonalBean.setAddress1(request.getParameter("resAddress1"));
        customerPersonalBean.setAddress2(request.getParameter("resAddress2"));
        customerPersonalBean.setAddress3(request.getParameter("resAddress3"));
        customerPersonalBean.setCity(request.getParameter("resCity"));
        customerPersonalBean.setResDistrict(request.getParameter("resDistrict"));
        customerPersonalBean.setResProvince(request.getParameter("resProvince"));

        customerPersonalBean.setPermentAddress1(request.getParameter("address1"));
        customerPersonalBean.setPermentAddress2(request.getParameter("address2"));
        customerPersonalBean.setPermentAddress3(request.getParameter("address3"));
        customerPersonalBean.setPermentCity(request.getParameter("city"));
        customerPersonalBean.setDistrict(request.getParameter("district"));
        customerPersonalBean.setProvince(request.getParameter("province"));

        customerPersonalBean.setBillAddress1(request.getParameter("billAddress1"));
        customerPersonalBean.setBillAddress2(request.getParameter("billAddress2"));
        customerPersonalBean.setBillAddress3(request.getParameter("billAddress3"));
        customerPersonalBean.setBillCity(request.getParameter("billCity"));
        customerPersonalBean.setBillDistrict(request.getParameter("billDistrict"));
        customerPersonalBean.setBillProvince(request.getParameter("billProvince"));

        customerPersonalBean.setCompanyAddress1(request.getParameter("companyAddress1"));
        customerPersonalBean.setCompanyAddress2(request.getParameter("companyAddress2"));
        customerPersonalBean.setCompanyAddress3(request.getParameter("companyAddress3"));
        customerPersonalBean.setCompanyCity(request.getParameter("companyCity"));
        customerPersonalBean.setCompanyDistrict(request.getParameter("companyDistrict"));
        customerPersonalBean.setCompanyProvince(request.getParameter("companyProvince"));

        customerPersonalBean.setMobileNumber(request.getParameter("mobileNo"));
        customerPersonalBean.setHomeTelNumber(request.getParameter("resPhoneNo"));
        customerPersonalBean.setOfficeTelNumber(request.getParameter("officePhoneNo"));
        customerPersonalBean.setEstMobileNo(request.getParameter("estMobileNo"));
        customerPersonalBean.setEstLandNo(request.getParameter("estLandNo"));
        customerPersonalBean.setFaxNo(request.getParameter("faxNo"));

        customerPersonalBean.setLastUpdateUser(sessionVarlist.getCMSSessionUser().getUserName());

    }

    private void getcustomerContactDetailChnges(HttpServletRequest request) {
        changeTracker = new CustomerContactDetailsChngeHolderBean();

        changeTracker.setIsPermanentAddressChanged(Boolean.parseBoolean(request.getParameter("isPermanentAddressChanged")));
        changeTracker.setIsResidenceAddressChanged(Boolean.parseBoolean(request.getParameter("isResidenceAddressChanged")));
        changeTracker.setIsBillingAddressChanged(Boolean.parseBoolean(request.getParameter("isBillingAddressChanged")));
        changeTracker.setIsCompanyAddressChanged(Boolean.parseBoolean(request.getParameter("isCompanyAddressChanged")));
        changeTracker.setIsResPhoneNoChanged(Boolean.parseBoolean(request.getParameter("isResPhoneNoChanged")));
        changeTracker.setIsMobilePhoneNoChanged(Boolean.parseBoolean(request.getParameter("isMobilePhoneNoChanged")));
        changeTracker.setIsOfficePhoneNoChanged(Boolean.parseBoolean(request.getParameter("isOfficePhoneNoChanged")));
        changeTracker.setIsEstMobContactNoChanged(Boolean.parseBoolean(request.getParameter("isEstMobContactNoChanged")));
        changeTracker.setIsEstLandContactNoChanged(Boolean.parseBoolean(request.getParameter("isEstLandContactNoChanged")));
        changeTracker.setIsFaxNoChanged(Boolean.parseBoolean(request.getParameter("isFaxNoChanged")));

    }

    public CustomerPersonalBean getInvalidMsgBean() {
        return invalidMsgBean;
    }

    public void setInvalidMsgBean(CustomerPersonalBean invalidMsgBean) {
        this.invalidMsgBean = invalidMsgBean;
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
