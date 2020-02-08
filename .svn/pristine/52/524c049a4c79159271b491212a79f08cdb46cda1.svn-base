/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationCustomerEmploymentBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationCustomerPersonalBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationDetailsBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationIncomeBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationDetailsManager;
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
public class ViewApplicationDetailsServlet extends HttpServlet {

    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private ApplicationDetailsManager appdetailsManager = null;
    private ApplicationDetailsBean applicationBean = null;
    private ApplicationCustomerPersonalBean personalBean = null;
    private ApplicationCustomerEmploymentBean employeeBean = null;
    private List<ApplicationIncomeBean> incomeDetailList = null;
    private String url = "/reportexp/cardapplication/viewallapplicationdetails.jsp";

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
                    String pageCode = PageVarList.APPLICATION_DETAILS_RPT;
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

            ///////////////////////////////////////////////

            String applicationID = request.getParameter("applicationID");

            this.getApplicationCardDetails(applicationID);
            this.getPersonalDetails(applicationID);

            request.setAttribute("applicationBean", applicationBean);
            request.setAttribute("personalBean", personalBean);

            rd = request.getRequestDispatcher(url);

            /////////////////////////////////////////////////
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
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void getApplicationCardDetails(String applicationId) throws Exception {
        try {
            appdetailsManager = new ApplicationDetailsManager();
            applicationBean = appdetailsManager.getApplicationCardDetails(applicationId);

            if (applicationBean.getApplicationID() == null) {
                applicationBean.setApplicationID("-");
            }

            if (applicationBean.getAssignStatus() == null) {
                applicationBean.setAssignStatus("-");
            }

            if (applicationBean.getAssignUser() == null) {
                applicationBean.setAssignUser("-");
            }

            if (applicationBean.getBranch() == null) {
                applicationBean.setBranch("-");
            }

            if (applicationBean.getCreditScore() == null) {
                applicationBean.setCreditScore("-");
            }

            if (applicationBean.getIdentificationNo() == null) {
                applicationBean.setIdentificationNo("-");
            }

            if (applicationBean.getIdentificationType() == null) {
                applicationBean.setIdentificationType("-");
            }

            if (applicationBean.getRefEmpNo() == null) {
                applicationBean.setRefEmpNo("-");
            }

        } catch (Exception e) {
            throw e;
        }
    }

    private void getPersonalDetails(String applicationId) throws Exception {
        try {
            appdetailsManager = new ApplicationDetailsManager();
            personalBean = new ApplicationCustomerPersonalBean();
            personalBean = appdetailsManager.getPersonalDetails(applicationId);

            if (personalBean.getAcedemicQualifications() == null) {
                personalBean.setAcedemicQualifications("-");
            }

            if (personalBean.getAddress1() == null) {
                personalBean.setAddress1("-");
            }

            if (personalBean.getAddress2() == null) {
                personalBean.setAddress2("-");
            }

            if (personalBean.getAddress3() == null) {
                personalBean.setAddress3("-");
            }

            if (personalBean.getApplicationId() == null) {
                personalBean.setApplicationId("-");
            }

            if (personalBean.getBillAddress1() == null) {
                personalBean.setBillAddress1("-");
            }

            if (personalBean.getBillAddress2() == null) {
                personalBean.setBillAddress2("-");
            }

            if (personalBean.getBillAddress3() == null) {
                personalBean.setBillAddress3("-");
            }

            if (personalBean.getBillCity() == null) {
                personalBean.setBillCity("-");
            }

            if (personalBean.getBillDistrict() == null) {
                personalBean.setBillDistrict("-");
            }

            if (personalBean.getBillProvince() == null) {
                personalBean.setBillProvince("-");
            }

            if (personalBean.getBirthday() == null) {
                personalBean.setBirthday("-");
            }

            if (personalBean.getBloodgroup() == null) {
                personalBean.setBloodgroup("-");
            }

            if (personalBean.getCardProduct() == null) {
                personalBean.setCardProduct("-");
            }

            if (personalBean.getCardType() == null) {
                personalBean.setCardType("-");
            }

            if (personalBean.getCity() == null) {
                personalBean.setCity("-");
            }

            if (personalBean.getCreditLimit() == null) {
                personalBean.setCreditLimit("-");
            }

            if (personalBean.getDistrict() == null) {
                personalBean.setDistrict("-");
            }

            if (personalBean.getDurationofTheAddress() == null) {
                personalBean.setDurationofTheAddress("-");
            }

            if (personalBean.getEmail() == null) {
                personalBean.setEmail("-");
            }

            if (personalBean.getEmergencyContactNumber() == null) {
                personalBean.setEmergencyContactNumber("-");
            }

            if (personalBean.getFirstName() == null) {
                personalBean.setFirstName("-");
            }

            if (personalBean.getGender() == null) {
                personalBean.setGender("-");
            }

            if (personalBean.getHomeTelNumber() == null) {
                personalBean.setHomeTelNumber("-");
            }

            if (personalBean.getIdentificationNumber() == null) {
                personalBean.setIdentificationNumber("-");
            }

            if (personalBean.getIdentificationType() == null) {
                personalBean.setIdentificationType("-");
            }

            if (personalBean.getInitials() == null) {
                personalBean.setInitials("-");
            }

            if (personalBean.getLastName() == null) {
                personalBean.setLastName("-");
            }

            if (personalBean.getMaritalStatus() == null) {
                personalBean.setMaritalStatus("-");
            }

            if (personalBean.getMiddleName() == null) {
                personalBean.setMiddleName("-");
            }

            if (personalBean.getMobileNumber() == null) {
                personalBean.setMobileNumber("-");
            }

            if (personalBean.getMonthlyRental() == null) {
                personalBean.setMonthlyRental("-");
            }

            if (personalBean.getMorgageRental() == null) {
                personalBean.setMorgageRental("-");
            }

            if (personalBean.getMothersMaidenName() == null) {
                personalBean.setMothersMaidenName("-");
            }

            if (personalBean.getNameOncard() == null) {
                personalBean.setNameOncard("-");
            }

            if (personalBean.getNameWithInitials() == null) {
                personalBean.setNameWithInitials("-");
            }

            if (personalBean.getNationality() == null) {
                personalBean.setNationality("-");
            }

            if (personalBean.getNic() == null) {
                personalBean.setNic("-");
            }

            if (personalBean.getNumberOfDependance() == null) {
                personalBean.setNumberOfDependance("-");
            }

            if (personalBean.getOfficeTelNumber() == null) {
                personalBean.setOfficeTelNumber("-");
            }

            if (personalBean.getOwnership() == null) {
                personalBean.setOwnership("-");
            }

            if (personalBean.getPlaceOfbirth() == null) {
                personalBean.setPlaceOfbirth("-");
            }

            if (personalBean.getPassportExpdate() == null) {
                personalBean.setPassportExpdate("-");
            }

            if (personalBean.getPassportNumber() == null) {
                personalBean.setPassportNumber(applicationId);
            }

            if (personalBean.getPermentAddress1() == null) {
                personalBean.setPermentAddress1("-");
            }

            if (personalBean.getPermentAddress2() == null) {
                personalBean.setPermentAddress2("-");
            }

            if (personalBean.getPermentAddress3() == null) {
                personalBean.setPermentAddress3("-");
            }

            if (personalBean.getPermentCity() == null) {
                personalBean.setPermentCity("-");
            }

            if (personalBean.getPlaceOfbirth() == null) {
                personalBean.setPlaceOfbirth("-");
            }

            if (personalBean.getPostalcode() == null) {
                personalBean.setPostalcode("-");
            }

            if (personalBean.getProfessionalQualifications() == null) {
                personalBean.setProfessionalQualifications("-");
            }

            if (personalBean.getProvince() == null) {
                personalBean.setProvince("-");
            }

            if (personalBean.getRelAddress1() == null) {
                personalBean.setRelAddress1("-");
            }

            if (personalBean.getRelAddress2() == null) {
                personalBean.setRelAddress2("-");
            }

            if (personalBean.getRelAddress3() == null) {
                personalBean.setRelAddress3("-");
            }

            if (personalBean.getRelCity() == null) {
                personalBean.setRelCity("-");
            }

            if (personalBean.getRelCompany() == null) {
                personalBean.setRelCompany("-");
            }

            if (personalBean.getRelEmail() == null) {
                personalBean.setRelEmail("-");
            }

            if (personalBean.getRelMobile() == null) {
                personalBean.setRelMobile("-");
            }

            if (personalBean.getRelName() == null) {
                personalBean.setRelName("-");
            }

            if (personalBean.getRelOfficeNumber() == null) {
                personalBean.setRelOfficeNumber("-");
            }

            if (personalBean.getRelResidencePhone() == null) {
                personalBean.setRelResidencePhone("-");
            }

            if (personalBean.getRelationship() == null) {
                personalBean.setRelationship("-");
            }

            if (personalBean.getResDistrict() == null) {
                personalBean.setResDistrict("-");
            }

            if (personalBean.getResProvince() == null) {
                personalBean.setResProvince("-");
            }

            if (personalBean.getResidenceType() == null) {
                personalBean.setResidenceType("-");
            }

            if (personalBean.getSmsalertMobileNumber() == null) {
                personalBean.setSmsalertMobileNumber("-");
            }

            if (personalBean.getSpouseDateofBirth() == null) {
                personalBean.setSpouseDateofBirth("-");
            }

            if (personalBean.getSpouseDesignation() == null) {
                personalBean.setSpouseDesignation("-");
            }

            if (personalBean.getSpouseMail() == null) {
                personalBean.setSpouseMail("-");
            }

            if (personalBean.getSpouseMonthlyIncome() == null) {
                personalBean.setSpouseMonthlyIncome("-");
            }

            if (personalBean.getSpouseName() == null) {
                personalBean.setSpouseName("-");
            }

            if (personalBean.getSpouseNameofEmployee() == null) {
                personalBean.setSpouseNameofEmployee("-");
            }

            if (personalBean.getSpouseNic() == null) {
                personalBean.setSpouseNic("-");
            }

            if (personalBean.getSpousePassportNumber() == null) {
                personalBean.setSpousePassportNumber("-");
            }

            if (personalBean.getSpousePhone() == null) {
                personalBean.setSpousePhone("-");
            }

            if (personalBean.getSpousecompanyAddress() == null) {
                personalBean.setSpousecompanyAddress("-");
            }

            if (personalBean.getTitle() == null) {
                personalBean.setTitle("-");
            }

            if (personalBean.getUseCardOnline() == null) {
                personalBean.setUseCardOnline("-");
            }

            if (personalBean.getVehicalNo() == null) {
                personalBean.setVehicalNo("-");
            }

            if (personalBean.getVehicalType() == null) {
                personalBean.setVehicalType("-");
            }

            if (personalBean.getReligion() == null) {
                personalBean.setReligion("-");
            }

        } catch (Exception e) {
            throw e;
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
