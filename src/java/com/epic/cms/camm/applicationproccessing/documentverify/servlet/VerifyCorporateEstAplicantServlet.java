/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedEstDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.DocumentVerifyManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
public class VerifyCorporateEstAplicantServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private boolean success = false;
    private String selectedTab = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private ApplicationHistoryBean appHistoryBean = null;
    private DocumentVerifyManager verifyManager = null;
    private CheckedEstDetailsBean checkedApplicantBean = null;
    private CheckedEstDetailsBean corporateEstVerifyBean = null;
    private String url = "/camm/documentverification/corporateetsablishmentverifyview.jsp";

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
                String pageCode = PageVarList.CORPORATE_CARD_VERIFICATION;
                String taskCode = TaskVarList.VERIFY;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                    this.setUserInputToBean(request);
                    request.setAttribute("checkedApplicantBean", checkedApplicantBean);

                    corporateEstVerifyBean = sessionVarlist.getEstBankVerifyBean();

                    if (this.validateUserInput(checkedApplicantBean)) {

                        if (this.isExistCardApplication(checkedApplicantBean.getApplicationId())) {

                            this.setAudittraceValue(request);
                            this.updateCorporateEstVerifyDetailsOfApplication(checkedApplicantBean, StatusVarList.APP_VERIFY_COMPELTE, appHistoryBean, systemAuditBean);
                            request.setAttribute("successMsg", MessageVarList.SUCCES_DOC_VERIFY);
                            rd = request.getRequestDispatcher("/LoadCorporateCardVerifySearchServlet");

                        } else {

                            //check for the record existance for this application id..if doesn't exist then insert the details

                            this.setAudittraceValue(request);
                            this.insertCorporateEstVerifyDetailsOfApplication(checkedApplicantBean, StatusVarList.APP_VERIFY_COMPELTE, appHistoryBean, systemAuditBean);
                            request.setAttribute("successMsg", MessageVarList.SUCCES_DOC_VERIFY);
                            rd = request.getRequestDispatcher("/LoadCorporateCardVerifySearchServlet");
                        }

                    } else {
                        // show the error message within the relevent tab
                        request.setAttribute("selectedtab", selectedTab);
                        if (selectedTab.equals("0")) {
                            request.setAttribute("errorMsg", errorMessage);
                        } else if (selectedTab.equals("1")) {
                            request.setAttribute("errorMsg2", errorMessage);
                        }
                        rd = getServletContext().getRequestDispatcher(url);
                    }

                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }


            } catch (AccessDeniedException adex) {
                throw adex;

            }

        }//catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {

            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);


        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_ASSIGN_APPLICATION);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    ///////////////////////////
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

    private void setUserInputToBean(HttpServletRequest request) throws Exception {
        try {

            checkedApplicantBean = new CheckedEstDetailsBean();

            checkedApplicantBean.setApplicationId(sessionVarlist.getCorporateAppId());
            checkedApplicantBean.setCompanyName(request.getParameter("companyName").trim());
            checkedApplicantBean.setBrc(request.getParameter("brc").trim());
            checkedApplicantBean.setTypeOfCompany(request.getParameter("typeOfCompany").trim());
            checkedApplicantBean.setNoOfEmployee(request.getParameter("noOfEmployee").trim());
            checkedApplicantBean.setCapitalInvested(request.getParameter("capitalInvested").trim());
            checkedApplicantBean.setAnnualTurnOver(request.getParameter("annualTurnOver").trim());
            checkedApplicantBean.setDateOfReg(request.getParameter("dateOfReg").trim());
            checkedApplicantBean.setAuditorName(request.getParameter("auditorName").trim());
            checkedApplicantBean.setAuditorAddress(request.getParameter("auditorAddress").trim());
            checkedApplicantBean.setDocBrc(request.getParameter("docBrc").trim());
            
            checkedApplicantBean.setLastUpdatedTime(new Date());
            checkedApplicantBean.setLastUpdatedUser(sessionUser.getUserName());

            appHistoryBean = new ApplicationHistoryBean();
            appHistoryBean.setApplicationId(sessionVarlist.getCorporateAppId());
            appHistoryBean.setApplicationLevel("VERI");
            appHistoryBean.setStatus(StatusVarList.HISTORY_COMPLETE);
            appHistoryBean.setRemarks("Application is verified");
            appHistoryBean.setLastUpdatedUser(sessionUser.getUserName());

        } catch (Exception ex) {
            throw ex;
        }

    }

    public boolean validateUserInput(CheckedEstDetailsBean checkedBean) throws Exception {
        boolean isValid = true;
        //validate user Role code
        try {

            if (corporateEstVerifyBean.getCompanyName().contentEquals("YES")) {
                if (checkedBean.getCompanyName().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.BIRTHCERTIFICATE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            } else if (corporateEstVerifyBean.getBrc().contentEquals("YES")) {
                if (checkedBean.getBrc().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.BIRTHCERTIFICATE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            } else if (corporateEstVerifyBean.getTypeOfCompany().contentEquals("YES")) {
                if (checkedBean.getTypeOfCompany().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.BIRTHCERTIFICATE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            } else if (corporateEstVerifyBean.getNoOfEmployee().contentEquals("YES")) {
                if (checkedBean.getNoOfEmployee().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.BIRTHCERTIFICATE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            } else if (corporateEstVerifyBean.getCapitalInvested().contentEquals("YES")) {
                if (checkedBean.getCapitalInvested().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.BIRTHCERTIFICATE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            } else if (corporateEstVerifyBean.getAnnualTurnOver().contentEquals("YES")) {
                if (checkedBean.getAnnualTurnOver().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.BIRTHCERTIFICATE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            } else if (corporateEstVerifyBean.getDateOfReg().contentEquals("YES")) {
                if (checkedBean.getDateOfReg().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.BIRTHCERTIFICATE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            } else if (corporateEstVerifyBean.getAuditorName().contentEquals("YES")) {
                if (checkedBean.getAuditorName().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.BIRTHCERTIFICATE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            } else if (corporateEstVerifyBean.getAuditorAddress().contentEquals("YES")) {
                if (checkedBean.getAuditorAddress().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.BIRTHCERTIFICATE_VERIFY_NOTMATCH;
                    selectedTab = "0";
                }
            } else if (corporateEstVerifyBean.getDocBrc().contentEquals("YES")) {
                if (checkedBean.getDocBrc().contentEquals("NO")) {
                    isValid = false;
                    errorMessage = MessageVarList.BIRTHCERTIFICATE_VERIFY_NOTMATCH;
                    selectedTab = "1";
                }

            }


        } catch (Exception ex) {
            isValid = false;

        }

        return isValid;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Verify establishment corporate card application, application id: " + checkedApplicantBean.getApplicationId() + " by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.DOCUMENTVERIFY);
            systemAuditBean.setPageCode(PageVarList.CORPORATE_CARD_VERIFICATION);
            systemAuditBean.setTaskCode(TaskVarList.VERIFY);
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

    private boolean isExistCardApplication(String applicationId) throws Exception {
        boolean flag = false;
        try {

            verifyManager = new DocumentVerifyManager();
            flag = verifyManager.isExistCardApplication(applicationId);
        } catch (Exception ex) {
            throw ex;
        }
        return flag;
    }

    private boolean insertCorporateEstVerifyDetailsOfApplication(CheckedEstDetailsBean checkedApplicantBean, String applicationStatus, ApplicationHistoryBean historyBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            verifyManager = new DocumentVerifyManager();
            success = verifyManager.insertCorporateEstVerifyDetailsOfApplication(checkedApplicantBean, applicationStatus, historyBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private boolean updateCorporateEstVerifyDetailsOfApplication(CheckedEstDetailsBean checkedApplicantBean, String applicationStatus, ApplicationHistoryBean historyBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            verifyManager = new DocumentVerifyManager();
            success = verifyManager.updateCorporateEstVerifyDetailsOfApplication(checkedApplicantBean, historyBean, applicationStatus, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
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
