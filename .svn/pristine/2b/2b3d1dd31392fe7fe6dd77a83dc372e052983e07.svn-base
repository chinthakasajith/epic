package com.epic.cms.camm.applicationproccessing.cooperate.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CurrencyMgtManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
//import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DebitPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.AreaBeanCoCustomer;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.CustomerCorporateBean;
import com.epic.cms.camm.applicationproccessing.cooperate.businesslogic.CorporateCustomerManager;
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
public class AddCorporateCustomerServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SystemAuditBean systemAuditBean;
    String url = "/camm/capturedata/cooperatecutomer.jsp";
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private CorporateCustomerManager manager = null;
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
    private DebitPersonalBean debitPersonalBean = null;
    private CurrencyMgtManager currencyObj = null;
    private String cardCategoryCode = null;
    private Boolean checkOutStatus = false;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        CustomerCorporateBean personalBean = new CustomerCorporateBean();
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
//            String pageCode = PageVarList.ACCTEMPLATE;
//            String taskCode = TaskVarList.ADD;


            //check whethre userrole have an access for this page and task
            // if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


            String applicationId = sessionVarlist.getApplicationId();
            request.setAttribute("applicationId", applicationId);
            request.setAttribute("operationtype", "add");

            String idType = request.getParameter("hidIdType");
            String idNumber = request.getParameter("hidIdNumber");
          //  personalBean.setIdentificationType(idType);
         //   personalBean.setIdentificationNumber(request.getParameter("hidIdNumber"));
//            if (idType.equals("NIC")) {
//                personalBean.setNic(request.getParameter("hidIdNumber"));
//            } else if (idType.equals("PAS")) {
//                personalBean.setPassportNumber(request.getParameter("hidIdNumber"));
//            }
                
            personalBean.setApplicationId(applicationId);
            personalBean.setIdentificationCode(idType);
            personalBean.setIdentificationNumber(idNumber);
            
            personalBean.setTitle(request.getParameter("title"));
            personalBean.setFirstName(request.getParameter("firstname"));
            personalBean.setGender(request.getParameter("gender"));
            personalBean.setLastName(request.getParameter("lastName"));
            personalBean.setMiddleName(request.getParameter("othername"));
            personalBean.setNameoncard(request.getParameter("nameoncard"));
            personalBean.setBirthday(request.getParameter("birthday"));
            personalBean.setJobtitle(request.getParameter("jobtitle"));
            personalBean.setPermentCity(request.getParameter("city"));
            personalBean.setNationality(request.getParameter("nationality"));
            personalBean.setNic(request.getParameter("nic"));
            personalBean.setAddress1(request.getParameter("address1"));
            personalBean.setAddress2(request.getParameter("address2"));
            personalBean.setAddress3(request.getParameter("address3"));
            personalBean.setBasicannualsalary(request.getParameter("basicannualsalary"));
            personalBean.setFixedallowance(request.getParameter("fixedallowance"));
            personalBean.setTelphone(request.getParameter("telphone"));
            personalBean.setBloodGroup(request.getParameter("bloodGroup"));
            personalBean.setReligion(request.getParameter("religion"));
            personalBean.setAcedemicQualifications(request.getParameter("academicQualify"));
            personalBean.setDistrict(request.getParameter("district"));
            personalBean.setEmpDuration(request.getParameter("empDuration"));
            personalBean.setProvince(request.getParameter("province"));
            personalBean.setMothersMadden(request.getParameter("mothersMadden"));
            personalBean.setLastUpdatedUser(sessionUser.getUserName());
            

            CustomerCorporateBean invalidMsgBean = new CustomerCorporateBean();

            invalidMsgBean = this.isValiedPersonalInfo(personalBean);

            if (invalidMsgBean == null) {


                manager = new CorporateCustomerManager();
                this.setAudittraceValue(request, personalBean);
                int isAdd = manager.insertPersonalData(personalBean, systemAuditBean);

                if (isAdd == 1) {
                    request.setAttribute("successMsg", MessageVarList.ADD_CO_CUSTOMER_SUCCESS);

                    request.setAttribute("selectedtab", 1);
                    request.setAttribute("loadTabIndex", 2);
                    
                    request.setAttribute("applicationId", applicationId);
                    request.setAttribute("operationtype", "add");


                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);
                } else {

                    request.setAttribute("errorMsg", MessageVarList.ADD_CO_CUSTOMER_ERROR);

                    request.setAttribute("selectedtab", 0);
                    request.setAttribute("loadTabIndex", 1);
                    
                    request.setAttribute("applicationId", applicationId);
                    request.setAttribute("personalBean", personalBean);
//                    request.setAttribute("operationtype", "add");



                    rd = getServletContext().getRequestDispatcher(url);
                    rd.forward(request, response);
                }

            } else {
                //this.loadDefaultApplicationStatus(applicationId, request);
                request.setAttribute("selectedtab", 0);

                request.setAttribute("applicationId", applicationId);
                request.setAttribute("personalBean", personalBean);
                request.setAttribute("invalidMsgBean", invalidMsgBean);



                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            }

        } catch (SQLException ex) {
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            //this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
             request.setAttribute("selectedtab", 0);
             request.setAttribute("loadTabIndex", 1);
            request.setAttribute("personalBean", personalBean);
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
            request.setAttribute("personalBean", personalBean);
            //         this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
            request.setAttribute("errorMsg", "Error in action");
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            out.close();
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
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
            //Logger.getLogger(AddCustomerPersonalInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private CustomerCorporateBean isValiedPersonalInfo(CustomerCorporateBean personalBean) throws Exception {
        UserInputValidator validObject = new UserInputValidator();
        CustomerCorporateBean invalidMsgBean = new CustomerCorporateBean();
        int msg = 0;

        try {
            //----------------------------------------------------------------------------           



            if (personalBean.getTitle().isEmpty()) {
                invalidMsgBean.setTitle("Requierd");
                msg = 1;
            }
            if (personalBean.getNationality().isEmpty()) {
                invalidMsgBean.setNationality("Requierd");
                msg = 1;
            }
            if (personalBean.getPermentCity().isEmpty()) {
                invalidMsgBean.setPermentCity("Requierd");
                msg = 1;
            }
            
            
            if (personalBean.getEmpDuration().isEmpty()) {
                invalidMsgBean.setEmpDuration("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(personalBean.getEmpDuration())) {
                invalidMsgBean.setEmpDuration("Invalid");
                msg = 1;
            }
            
            
            if (personalBean.getFixedallowance().isEmpty()) {
                invalidMsgBean.setFixedallowance("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(personalBean.getFixedallowance())) {
                invalidMsgBean.setFixedallowance("Invalid");
                msg = 1;
            }
            
            if (personalBean.getNic().isEmpty()) {
                invalidMsgBean.setNic("Requierd");
                msg = 1;
            } else if (!validObject.checkNIC(personalBean.getNic())) {
                invalidMsgBean.setNic("Invalid");
                msg = 1;
            }
            
            if (personalBean.getReligion().isEmpty()) {
                invalidMsgBean.setReligion("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(personalBean.getReligion())) {
                invalidMsgBean.setReligion("Invalid");
                msg = 1;
            } else if (!validObject.isSpecialCharacter(personalBean.getReligion())) {
                invalidMsgBean.setReligion("Invalid");
                msg = 1;
            }
            
            if (personalBean.getNameoncard().isEmpty()) {
                invalidMsgBean.setNameoncard("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(personalBean.getNameoncard())) {
                invalidMsgBean.setNameoncard("Invalid");
                msg = 1;
            } else if (!validObject.isSpecialCharacter(personalBean.getNameoncard())) {
                invalidMsgBean.setNameoncard("Invalid");
                msg = 1;
            }
            
            if (personalBean.getBasicannualsalary().isEmpty()) {
                invalidMsgBean.setBasicannualsalary("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(personalBean.getBasicannualsalary())) {
                invalidMsgBean.setBasicannualsalary("Invalid");
                msg = 1;
            }
            
            if (personalBean.getJobtitle().isEmpty()) {
                invalidMsgBean.setJobtitle("Requierd");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(personalBean.getJobtitle())) {
                invalidMsgBean.setJobtitle("Invalid");
                msg = 1;
            } else if (!validObject.isSpecialCharacter(personalBean.getJobtitle())) {
                invalidMsgBean.setJobtitle("Invalid");
                msg = 1;
            }
            
            
            if (personalBean.getFirstName().isEmpty()) {
                invalidMsgBean.setFirstName("Requierd");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(personalBean.getFirstName())) {
                invalidMsgBean.setFirstName("Invalid");
                msg = 1;
            }  else if (!validObject.isSpecialCharacter(personalBean.getFirstName())) {
                invalidMsgBean.setFirstName("Invalid");
                msg = 1;
            }
            
            
            if (personalBean.getLastName().isEmpty()) {
                invalidMsgBean.setLastName("Requierd");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(personalBean.getLastName())) {
                invalidMsgBean.setLastName("Invalid");
                msg = 1;
            } else if (!validObject.isSpecialCharacter(personalBean.getLastName())) {
                invalidMsgBean.setLastName("Invalid");
                msg = 1;
            }
            
            
            if (personalBean.getMiddleName().isEmpty()) {
//                invalidMsgBean.setMiddleName("Requierd");
//                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(personalBean.getMiddleName())) {
                invalidMsgBean.setMiddleName("Invalid");
                msg = 1;
            } else if (!validObject.isSpecialCharacter(personalBean.getMiddleName())) {
                invalidMsgBean.setMiddleName("Invalid");
                msg = 1;
            }
            
            
            if (personalBean.getBloodGroup().isEmpty()) {
                invalidMsgBean.setBloodGroup("Requierd");
                msg = 1;
            }

            if (personalBean.getBirthday().isEmpty()) {
                invalidMsgBean.setBirthday("Requierd");
                msg = 1;
            }
            if (personalBean.getGender().isEmpty()) {
                invalidMsgBean.setGender("Requierd");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(personalBean.getGender())) {
                invalidMsgBean.setGender("Invalid");
                msg = 1;
            }
            //-----------------============================================================================================================================-------------------         
            if (personalBean.getTelphone().isEmpty()) {
                invalidMsgBean.setTelphone("Requierd");
                msg = 1;
            } else if (!validObject.isNumeric(personalBean.getTelphone())) {
                invalidMsgBean.setTelphone("Invalid");
                msg = 1;
            }
//            if (personalBean.getEmail().isEmpty()) {
//                invalidMsgBean.setEmail("Requierd");
//                msg = 1;
//            } else if (!validObject.isValidEmail(personalBean.getEmail())) {
//                invalidMsgBean.setEmail("Invalid");
//                msg = 1;
//            }
            if (personalBean.getAddress1().isEmpty()) {
                invalidMsgBean.setAddress1("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(personalBean.getAddress1())) {
                invalidMsgBean.setAddress1("Invalid");
                msg = 1;
            }
            if (personalBean.getAddress2().isEmpty()) {
                invalidMsgBean.setAddress2("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(personalBean.getAddress2())) {
                invalidMsgBean.setAddress2("Invalid");
                msg = 1;
            }
            if (personalBean.getAddress3().isEmpty()) {
                invalidMsgBean.setAddress3("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectAddress(personalBean.getAddress3())) {
                invalidMsgBean.setAddress3("Invalid");
                msg = 1;
            }
//            if (personalBean.getCity().isEmpty()) {
//                invalidMsgBean.setCity("Requierd");
//                msg = 1;
//            } else if (!validObject.isCorrectString(personalBean.getCity())) {
//                invalidMsgBean.setCity("Invalid");
//                msg = 1;
//            }

//=======================================================================================================================================================================
//           
//             if (personalBean.getRelationship().isEmpty()) {
//                invalidMsgBean.setRelationship("Requierd");
//                msg = 1;
//            } else if (!validObject.isNonNumericNonSpecialString(personalBean.getRelationship())) {
//                invalidMsgBean.setRelationship("Invalid");
//                msg = 1;
//            }
//==================================================================================================================================================

            if (personalBean.getMothersMadden().isEmpty()) {
                invalidMsgBean.setMothersMadden("Requierd");
                msg = 1;
            } else if (!validObject.isNonNumericNonSpecialString(personalBean.getMothersMadden())) {
                invalidMsgBean.setMothersMadden("Invalid");
                msg = 1;
            } else if (!validObject.isSpecialCharacter(personalBean.getMothersMadden())) {
                invalidMsgBean.setMothersMadden("Invalid");
                msg = 1;
            }
            
            
            if (personalBean.getAcedemicQualifications().isEmpty()) {
                invalidMsgBean.setAcedemicQualifications("Requierd");
                msg = 1;
            } else if (!validObject.isCorrectString(personalBean.getAcedemicQualifications())) {
                invalidMsgBean.setAcedemicQualifications("Invalid");
                msg = 1;
            }

            if (msg == 0) {
                invalidMsgBean = null;
            }

            return invalidMsgBean;



        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setAudittraceValue(HttpServletRequest request, CustomerCorporateBean bean) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Customer Corporate Details in to Application ID : '" + bean.getApplicationId() + "' by :" + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CAPTURE_DATA);
            systemAuditBean.setPageCode(PageVarList.CO_CUSTOMER);
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
//     private IdBean getIdentifyDetails(String appliactionId) throws Exception {
//        IdBean bean = new IdBean();
//         try {
//            manager = new CaptureDataManager();
//            bean = manager.getIdentifyDetails(appliactionId);
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//         return bean;
//    }
}
