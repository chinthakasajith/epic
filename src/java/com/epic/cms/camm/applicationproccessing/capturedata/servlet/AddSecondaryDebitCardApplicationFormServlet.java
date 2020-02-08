/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DebitPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.system.util.datetime.SystemDateTime;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author nalin
 */
public class AddSecondaryDebitCardApplicationFormServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    //private String errorMsg = null;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean = null;
    private DebitPersonalBean personalBean = null;
    private boolean successInsert = false;
    private CaptureDataManager captureDataManager = null;
    private DebitPersonalBean debitPersonalBean = null;
    private String url = "/camm/capturedata/debitcardapplicationform.jsp";

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
                        //user not in same session.
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
                String pageCode = PageVarList.DEBITDATACAPTURE;
                String taskCode = TaskVarList.ADD;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {

                        DebitPersonalBean invalidMsgBean = new DebitPersonalBean();

                        int fsize = 1024;

                        DiskFileItemFactory factory = new DiskFileItemFactory();
                        factory.setSizeThreshold(1 * 1024 * 1024);
                        ServletFileUpload upload = new ServletFileUpload(factory);
                        upload.setSizeMax(fsize * 1024);

                        List items = upload.parseRequest(request);

                        HashMap<String, FileItem> formMap = this.convertToMaps(items);

                        this.setUserInputToBean(formMap);



                        this.checkCardStatus(personalBean.getApplicationId());
                        invalidMsgBean = validateUserInput(personalBean);
                        if (invalidMsgBean == null) {

                            this.setAudittraceValue(request);

                            try {
                                successInsert = this.insertDebitCardSecondaryApplication(personalBean, systemAuditBean);

                                if (successInsert) {
                                    for (int i = 0; i < sessionVarlist.getUserAssignDebitAppList().size(); i++) {

                                        if (sessionVarlist.getUserAssignDebitAppList().get(i).getApplicationId().equals(personalBean.getApplicationId())) {

                                            sessionVarlist.getUserAssignDebitAppList().remove(i);
                                        }
                                    }
                                    request.setAttribute("successMsg", MessageVarList.APPLICATION_SUCCESS_ADD + " " + personalBean.getApplicationId());
                                    rd = getServletContext().getRequestDispatcher("/LoadDebitCardApplicationServlet");

                                }

                            } catch (SQLException e) {

                                OracleMessage message = new OracleMessage();
                                String oraMessage = message.getMessege(e.getMessage());
                                request.setAttribute("selectedtab", "1");
                                request.setAttribute("sPersonalBean", personalBean);
                                request.setAttribute("personalBean", debitPersonalBean);
                                request.setAttribute("errorMsg", oraMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                                rd.forward(request, response);
                            }

                        } else {

                            request.setAttribute("personalBean", debitPersonalBean);
                            request.setAttribute("sPersonalBean", personalBean);
                            request.setAttribute("selectedtab", "1");
                            request.setAttribute("invalidMsgBeanS", invalidMsgBean);
                            rd = getServletContext().getRequestDispatcher(url);

                        }

                    } catch (Exception e) {

                        request.setAttribute("personalBean", debitPersonalBean);
                        request.setAttribute("sPersonalBean", personalBean);
                        request.setAttribute("selectedtab", "1");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);

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
            request.setAttribute("operationtype", "default");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    public Boolean setUserInputToBean(HashMap<String, FileItem> formMap) throws Exception {
        Boolean success = true;
        try {

            personalBean = new DebitPersonalBean();

            personalBean.setApplicationId(sessionVarlist.getApplicationId());

            personalBean.setIdentificationType(formMap.get("identificationTypeS").getString());
            personalBean.setIdentificationNumber(formMap.get("identificationNumberS").getString());
            personalBean.setTitle(formMap.get("titleS").getString());
            personalBean.setBirthday(formMap.get("birthdayS").getString());
            personalBean.setFirstName(formMap.get("firstNameS").getString());
            personalBean.setMiddleName(formMap.get("middleNameS").getString());
            personalBean.setLastName(formMap.get("lastNameS").getString());
            personalBean.setAddress1(formMap.get("address1S").getString());
            personalBean.setAddress2(formMap.get("address2S").getString());
            personalBean.setAddress3(formMap.get("address3S").getString());
            personalBean.setNameOncard(formMap.get("nameOncardS").getString());
            personalBean.setCity(formMap.get("cityS").getString());
            personalBean.setMothersMaidenName(formMap.get("mothersMaidenNameS").getString());
            personalBean.setMobileNumber(formMap.get("mobileNumberS").getString());
            personalBean.setHomeTelNumber(formMap.get("homeTelNumberS").getString());
            personalBean.setOfficeTelNumber(formMap.get("officeTelNumberS").getString());

            personalBean.setAccountNo(formMap.get("accountNo").getString());
            personalBean.setAccNo1Type(formMap.get("accNo1Type").getString());
            personalBean.setAccNo1Currency(formMap.get("accNo1Currency").getString());

            personalBean.setAccountNo3(formMap.get("accountNo3").getString());
            personalBean.setAccNo3Type(formMap.get("accNo3Type").getString());
            personalBean.setAccNo3Currency(formMap.get("accNo3Currency").getString());

            personalBean.setSignatureFileName(sessionVarlist.getSecondrySignatureFileName());
            personalBean.setNicFileName(sessionVarlist.getSecondryNicFileName());
            personalBean.setJoin(sessionVarlist.getCardCategoryCode());

            personalBean.setLastUpdateUser(sessionUser.getUserName());


        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    public DebitPersonalBean validateUserInput(DebitPersonalBean personalBean) throws Exception {
        boolean isValidate = true;

        DebitPersonalBean invalidMsgBean = new DebitPersonalBean();

//        --------------------validate user Role code -------------------------------

        try {

            if (personalBean.getIdentificationType().contentEquals("") || personalBean.getIdentificationType().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setIdentificationType(MessageVarList.REQUIRED);

            }

            if (personalBean.getIdentificationNumber().contentEquals("") || personalBean.getIdentificationNumber().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setIdentificationNumber(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isAlphaNumeric(personalBean.getIdentificationNumber())) {
                isValidate = false;

                invalidMsgBean.setIdentificationNumber(MessageVarList.INVALID);
            }

            if (personalBean.getTitle().contentEquals("") || personalBean.getTitle().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setTitle(MessageVarList.REQUIRED);

            }
            if (personalBean.getBirthday().contentEquals("") || personalBean.getBirthday().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setBirthday(MessageVarList.REQUIRED);

            } else if (this.stringToDate(personalBean.getBirthday()).after(SystemDateTime.getSystemDataAndTime())) {
                isValidate = false;

                invalidMsgBean.setBirthday(MessageVarList.INVALID);
            }

            if (personalBean.getFirstName().contentEquals("") || personalBean.getFirstName().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setFirstName(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isAlpha(personalBean.getFirstName())) {
                isValidate = false;

                invalidMsgBean.setFirstName(MessageVarList.INVALID);
//            }
//
//            if (personalBean.getMiddleName().contentEquals("") || personalBean.getMiddleName().length() <= 0) {
//                isValidate = false;
//
//                invalidMsgBean.setMiddleName(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isPersonName(personalBean.getMiddleName())) {
                isValidate = false;

                invalidMsgBean.setMiddleName(MessageVarList.INVALID);
            }

            if (personalBean.getLastName().contentEquals("") || personalBean.getLastName().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setLastName(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isPersonName(personalBean.getLastName())) {
                isValidate = false;

                invalidMsgBean.setLastName(MessageVarList.INVALID);
            }


            if (personalBean.getAddress1().contentEquals("") || personalBean.getAddress1().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setAddress1(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isCorrectAddress(personalBean.getAddress1())) {
                isValidate = false;

                invalidMsgBean.setAddress1(MessageVarList.INVALID);
            }

            if (personalBean.getAddress2().contentEquals("") || personalBean.getAddress2().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setAddress2(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isCorrectAddress(personalBean.getAddress2())) {
                isValidate = false;

                invalidMsgBean.setAddress2(MessageVarList.INVALID);
//            }
//
//            if (personalBean.getAddress3().contentEquals("") || personalBean.getAddress3().length() <= 0) {
//                isValidate = false;
//
//                invalidMsgBean.setAddress3(MessageVarList.REQUIRED);

            } else if (!(personalBean.getAddress3().contentEquals("") || personalBean.getAddress3().length() <= 0) && !UserInputValidator.isCorrectAddress(personalBean.getAddress3())) {
                isValidate = false;

                invalidMsgBean.setAddress3(MessageVarList.INVALID);
            }

            if (personalBean.getNameOncard().contentEquals("") || personalBean.getNameOncard().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setNameOncard(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isPersonName(personalBean.getNameOncard())) {
                isValidate = false;

                invalidMsgBean.setNameOncard(MessageVarList.INVALID);
            }

            if (personalBean.getMothersMaidenName().contentEquals("") || personalBean.getMothersMaidenName().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setMothersMaidenName(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isPersonName(personalBean.getMothersMaidenName())) {
                isValidate = false;

                invalidMsgBean.setMothersMaidenName(MessageVarList.INVALID);
            }

            if (personalBean.getCity().contentEquals("") || personalBean.getCity().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setCity(MessageVarList.REQUIRED);

            }
            if (personalBean.getMobileNumber().contentEquals("") || personalBean.getMobileNumber().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setMobileNumber(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isPhoneNumber(personalBean.getMobileNumber())) {
                isValidate = false;

                invalidMsgBean.setMobileNumber(MessageVarList.INVALID);
            }
            if (personalBean.getHomeTelNumber().contentEquals("") || personalBean.getHomeTelNumber().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setHomeTelNumber(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isPhoneNumber(personalBean.getHomeTelNumber())) {
                isValidate = false;

                invalidMsgBean.setHomeTelNumber(MessageVarList.INVALID);
            }
            if (personalBean.getOfficeTelNumber().contentEquals("") || personalBean.getOfficeTelNumber().length() <= 0) {
                isValidate = false;

                invalidMsgBean.setOfficeTelNumber(MessageVarList.REQUIRED);

            } else if (!UserInputValidator.isPhoneNumber(personalBean.getOfficeTelNumber())) {
                isValidate = false;

                invalidMsgBean.setOfficeTelNumber(MessageVarList.INVALID);
            }

            if (!UserInputValidator.isAccountNumber(personalBean.getAccountNo3()) && !personalBean.getAccountNo3().contentEquals("")) {
                isValidate = false;

                invalidMsgBean.setAccountNo3(MessageVarList.INVALID);

            }else  if (personalBean.getAccountNo3().length()!=12 && !personalBean.getAccountNo3().contentEquals("")) {
                isValidate = false;

                invalidMsgBean.setAccountNo3(MessageVarList.INVALID);

            }

            if (personalBean.getAccNo3Type().contentEquals("") && !personalBean.getAccountNo3().contentEquals("")) {
                // isValidate = false;

                invalidMsgBean.setAccNo2Type(MessageVarList.REQUIRED);

            }

            if (personalBean.getAccNo3Currency().contentEquals("") && !personalBean.getAccountNo3().contentEquals("")) {
                //isValidate = false;

                invalidMsgBean.setAccNo2Currency(MessageVarList.REQUIRED);

            }

            if (personalBean.getSignatureFileName() == null) {
                isValidate = false;

                invalidMsgBean.setSignatureFileName(MessageVarList.SIGNATURE_FILE_NULL);

            } 
            if (personalBean.getNicFileName() == null) {

                isValidate = false;

                invalidMsgBean.setNicFileName(MessageVarList.NIC_FILE_NULL);

            }


        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }

        if (isValidate) {
            invalidMsgBean = null;
        }
        return invalidMsgBean;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = personalBean.getApplicationId();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add " + personalBean.getApplicationId() + "  By " + sessionVarlist.getCMSSessionUser().getUserName());
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

    private HashMap<String, FileItem> convertToMaps(List<FileItem> aFileItems) {
        HashMap<String, FileItem> fFileParams = new HashMap<String, FileItem>();
        for (FileItem item : aFileItems) {
            fFileParams.put(item.getFieldName(), item);
        }
        return fFileParams;
    }

    private boolean insertDebitCardSecondaryApplication(DebitPersonalBean personalBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            captureDataManager = new CaptureDataManager();
            success = captureDataManager.insertDebitCardSecondaryApplication(personalBean, systemAuditBean);

        } catch (Exception e) {
        }
        return success;

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

    public Date stringToDate(String dt) throws Exception {
        DateFormat formatter;
        Date date = null;
        try {

            formatter = new SimpleDateFormat("yyyy-mm-dd");
            date = (Date) formatter.parse(dt);

        } catch (Exception ex) {
            throw ex;
        }

        return date;
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
