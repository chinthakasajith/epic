/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.IdBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.camm.applicationproccessing.capturedata.util.LoadApplicationStatus;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * @author prageeth_s
 */
public class UpdateEstablishmentDetailServlet extends HttpServlet {

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
    private CaptureDataManager manager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean;
    private String url = "/camm/capturedata/modifyEstablishmentInputCaptureData.jsp";
    private EstablishmentDetailsBean establishmentDetailsBean=null;


     
    private ApplicationCheckingManager checkingManager;
    private CardApplicationStatusBean appStatusBean = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        establishmentDetailsBean = new EstablishmentDetailsBean();
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
            String applicationId = sessionVarlist.getApplicationId();
            request.setAttribute("applicationId", applicationId);
            request.setAttribute("operationtype", "add");
            String cardAppCategoryCode = sessionVarlist.getApplicationTypeCode();


            String idType = request.getParameter("hidIdType");
            establishmentDetailsBean.setIdentificationType(idType);
            establishmentDetailsBean.setIdentificationNumber(request.getParameter("hidIdNumber"));
            establishmentDetailsBean.setApplicationId(applicationId);
            establishmentDetailsBean.setAddress1(request.getParameter("address1"));
            establishmentDetailsBean.setAddress2(request.getParameter("address2"));
            establishmentDetailsBean.setAddress3(request.getParameter("address3"));            
            establishmentDetailsBean.setContactPersLandTelNumber(request.getParameter("contactPersLandTelNumber"));
            establishmentDetailsBean.setContactPersMobileNumber(request.getParameter("contactPersMobileNumber"));
            establishmentDetailsBean.setEmail(request.getParameter("email"));
            establishmentDetailsBean.setFaxNo(request.getParameter("faxNo"));            
            establishmentDetailsBean.setCardType(request.getParameter("cardType"));
            establishmentDetailsBean.setCardProduct(request.getParameter("cardProduct"));
            establishmentDetailsBean.setCreditLimit(request.getParameter("creditLimit"));          
            establishmentDetailsBean.setCardCurrency(request.getParameter("cardCurency"));
            
            establishmentDetailsBean.setShareCapital(request.getParameter("shareCapital"));
            establishmentDetailsBean.setNetProfit(request.getParameter("netProfit"));
            establishmentDetailsBean.setNetAssets(request.getParameter("netAssets"));
            establishmentDetailsBean.setAnnualTurnover(request.getParameter("annualTurnover"));
            
            
            establishmentDetailsBean.setCompanyName(request.getParameter("companyName"));
            establishmentDetailsBean.setNatureOfTheBusiness(request.getParameter("natureOfTheBusiness"));
            establishmentDetailsBean.setContactPersonFullName(request.getParameter("contactPersonFullName"));
            establishmentDetailsBean.setRemark(request.getParameter("remark"));
            
            establishmentDetailsBean.setLastUpdateUser(sessionUser.getUserName());

            EstablishmentDetailsBean invalidMsgBean = new EstablishmentDetailsBean();

            invalidMsgBean = this.isValiedPersonalInfo(establishmentDetailsBean, cardAppCategoryCode);

            if (invalidMsgBean == null) {

                manager = new CaptureDataManager();
                this.setAudittraceValue(request, establishmentDetailsBean);
                establishmentDetailsBean.setMode("edit");
                int isAdd = manager.updateEstablishmentDetails(establishmentDetailsBean, systemAuditBean);

                if (isAdd == 1) {
                    checkingManager = new ApplicationCheckingManager();
                    checkingManager.addRemarks(applicationId, "1", establishmentDetailsBean.getRemark(), establishmentDetailsBean.getLastUpdateUser());
                    this.loadDefaultEstablishmentApplicationStatus(applicationId, request);
//                    

                    request.setAttribute("successMsg", MessageVarList.UPDATE_ESTABLISHMENT_SUCCESS);

                    request.setAttribute("applicationId", applicationId);
                    request.setAttribute("operationtype", "add");
                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);
                } else {

                    this.loadDefaultEstablishmentApplicationStatus(applicationId, request);

                    request.setAttribute("errorMsg", MessageVarList.ADD_ESTABLISHMENT_ERROR);

                    request.setAttribute("applicationId", applicationId);
                    request.setAttribute("establishmentDetailsBean", establishmentDetailsBean);
//                    request.setAttribute("operationtype", "add");

                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);
                }

            } else {
                this.loadDefaultEstablishmentApplicationStatus(applicationId, request);

                request.setAttribute("applicationId", applicationId);
                request.setAttribute("establishmentDetailsBean", establishmentDetailsBean);
                request.setAttribute("invalidMsgBean", invalidMsgBean);

                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            }
        } catch (SQLException ex) {
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            this.loadDefaultEstablishmentApplicationStatus(sessionVarlist.getApplicationId(), request);
            request.setAttribute("establishmentDetailsBean", establishmentDetailsBean);
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
            request.setAttribute("establishmentDetailsBean", establishmentDetailsBean);
            this.loadDefaultEstablishmentApplicationStatus(sessionVarlist.getApplicationId(), request);
            request.setAttribute("errorMsg", "Error in action");
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            out.close();
        }
    }

    private void loadDefaultEstablishmentApplicationStatus(String appliactionId, HttpServletRequest request) throws Exception {

        this.getAllApplicationStatus(appliactionId);

        if (appStatusBean != null) {

            //if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_INITIATE) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_PROCESS)) {

                IdBean idbean = new IdBean();
                idbean = this.getIdentifyDetails(appliactionId);
                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatusInUpdate(appliactionId, sessionVarlist, request, Boolean.TRUE, idbean, 0, true);          
           // }

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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddCustomerPersonalInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddCustomerPersonalInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private EstablishmentDetailsBean isValiedPersonalInfo(EstablishmentDetailsBean establishmentDetailsBean, String cardAppCatCode ) throws Exception {
        UserInputValidator validObject = new UserInputValidator();
        EstablishmentDetailsBean invalidMsgBean = new EstablishmentDetailsBean();
        int msg = 0;
        if (cardAppCatCode == null) {
            cardAppCatCode = "";
        }

        try {
            

           
            if (establishmentDetailsBean.getCardProduct().isEmpty()) {
                invalidMsgBean.setCardProduct("Requierd");
            }
            
            //-----------------============================================================================================================================-------------------         
            if(establishmentDetailsBean.getAddress1().isEmpty()){
                invalidMsgBean.setAddress1("Requierd");
                msg=1;
            }else if(!validObject.isCorrectAddress(establishmentDetailsBean.getAddress1())){
                invalidMsgBean.setAddress1("Invalid");
                msg=1;
            }
            if (establishmentDetailsBean.getContactPersLandTelNumber().isEmpty()) {
                invalidMsgBean.setContactPersLandTelNumber("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(establishmentDetailsBean.getContactPersLandTelNumber())) {
                invalidMsgBean.setContactPersLandTelNumber("Invalid");
                msg = 1;
            }
            if (establishmentDetailsBean.getContactPersMobileNumber().isEmpty()) {
                invalidMsgBean.setContactPersMobileNumber("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(establishmentDetailsBean.getContactPersMobileNumber())) {
                invalidMsgBean.setContactPersMobileNumber("Invalid");
                msg = 1;
            }
           
            if (establishmentDetailsBean.getEmail().isEmpty()) {
                invalidMsgBean.setEmail("Requierd");
                msg = 1;
            } else if (!validObject.isValidEmail(establishmentDetailsBean.getEmail())) {
                invalidMsgBean.setEmail("Invalid");
                msg = 1;
            }
            
            if (establishmentDetailsBean.getFaxNo().isEmpty()) {
                invalidMsgBean.setFaxNo("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(establishmentDetailsBean.getFaxNo())) {
                invalidMsgBean.setFaxNo("Invalid");
                msg = 1;
            }

                
            if (establishmentDetailsBean.getCreditLimit() != null) {
                if (establishmentDetailsBean.getCreditLimit().isEmpty()) {
                    invalidMsgBean.setCreditLimit("Requierd");
                    msg = 1;
                } else if (!validObject.isDecimalOrNumeric(establishmentDetailsBean.getCreditLimit(), "15", "2")) {
                    invalidMsgBean.setCreditLimit("Invalid");
                    msg = 1;
                }
            }

            if (establishmentDetailsBean.getContactPersonFullName() != null) {
                //validate full name
                if (establishmentDetailsBean.getContactPersonFullName().isEmpty()) {
                    invalidMsgBean.setContactPersonFullName("Requierd");
                    msg = 1;
                } else if (!validObject.isNonNumericNonSpecialString(establishmentDetailsBean.getContactPersonFullName())) {
                    invalidMsgBean.setContactPersonFullName("Invalid");
                    msg = 1;
                }
            }
            
            
            if (establishmentDetailsBean.getAnnualTurnover() != null) {
                if (establishmentDetailsBean.getAnnualTurnover().isEmpty()) {
                    invalidMsgBean.setCreditLimit("Requierd");
                    msg = 1;
                } else if (!validObject.isDecimalOrNumeric(establishmentDetailsBean.getAnnualTurnover(), "15", "2")) {
                    invalidMsgBean.setAnnualTurnover("Invalid");
                    msg = 1;
                }
            }
            
            if (establishmentDetailsBean.getShareCapital() != null) {
                if (establishmentDetailsBean.getShareCapital().isEmpty()) {
                    invalidMsgBean.setCreditLimit("Requierd");
                    msg = 1;
                } else if (!validObject.isDecimalOrNumeric(establishmentDetailsBean.getShareCapital(), "15", "2")) {
                    invalidMsgBean.setShareCapital("Invalid");
                    msg = 1;
                }
            }

            if (establishmentDetailsBean.getNetProfit() != null) {
                if (establishmentDetailsBean.getNetProfit().isEmpty()) {
                    invalidMsgBean.setCreditLimit("Requierd");
                    msg = 1;
                } else if (!validObject.isDecimalOrNumeric(establishmentDetailsBean.getNetProfit(), "15", "2")) {
                    invalidMsgBean.setNetProfit("Invalid");
                    msg = 1;
                }
            }

            if (establishmentDetailsBean.getNetAssets() != null) {
                if (establishmentDetailsBean.getCreditLimit().isEmpty()) {
                    invalidMsgBean.setNetAssets("Requierd");
                    msg = 1;
                } else if (!validObject.isDecimalOrNumeric(establishmentDetailsBean.getNetAssets(), "15", "2")) {
                    invalidMsgBean.setNetAssets("Invalid");
                    msg = 1;
                }
            }
            
            
            
            
            if (msg == 0) {
                invalidMsgBean = null;
            }

            return invalidMsgBean;

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







    private void setAudittraceValue(HttpServletRequest request, EstablishmentDetailsBean bean) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Personal Details in to Application ID : '" + bean.getApplicationId() + "' by :" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CAPTURE_DATA);
            systemAuditBean.setPageCode(PageVarList.USERDATACAPTURE);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue("");

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    private IdBean getIdentifyDetails(String appliactionId) throws Exception {
        IdBean bean = new IdBean();
        try {
            manager = new CaptureDataManager();
            bean = manager.getIdentifyDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
        return bean;
    }

}
