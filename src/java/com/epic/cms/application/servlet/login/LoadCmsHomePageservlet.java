/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.application.servlet.login;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author janaka_h
 */
public class LoadCmsHomePageservlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private SystemUserManager userManager = null;
    private SessionUser CMSSessionUser = new SessionUser();
    private SessionVarList sessionVarList = new SessionVarList();
    private Map<SectionBean, List<PageBean>> sectionPageList;
    private List<ApplicationModuleBean> applicationList;
    HttpSession sessionObj = null;
    private SystemAuditBean systemAuditBean;
    private RequestDispatcher rd;
    private String url = "/oracleview.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            try {
                sessionObj = request.getSession(false);
                sessionVarList = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                CMSSessionUser = sessionVarList.getCMSSessionUser();
            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

//            String applicationType = request.getParameter("appCode");

            applicationList = (List<ApplicationModuleBean>) request.getAttribute("applicationLst");

            for (int i = 0; i < applicationList.size(); i++) {

                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.ADMINISTRATION_APPLICATION)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setAdminSectionPageList(sectionPageList);
                }


                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.CAMM_APPLICATION)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setCardmgtSectionPageList(sectionPageList);
                }


                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.CPMM_APPLICATION)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setCardembossSectionPageList(sectionPageList);
                }


                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.SWITCH_CONTROL_PANEL)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setSwitchSectionPageList(sectionPageList);
                }


                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.CALL_CENTER_APPLICATION)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setCallcenterSectionPageList(sectionPageList);
                }


                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.ACQUIRE_CALL_CENTER_APPLICATION)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setAcqcallSectionPageList(sectionPageList);
                }


                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.MERCHANT_TERMINAL_APPLICATION)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setMerterSectionPageList(sectionPageList);
                }


                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.BACK_OFFICE)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setBackofficeSectionPageList(sectionPageList);
                }


                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.PRE_PERSONAL)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setPersonalSectionPageList(sectionPageList);
                }


                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.REPORT_EXPLORER)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setReportexpSectionPageList(sectionPageList);
                }
                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.ISSUING_ADMIN)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setIssuingAdminSectionPageList(sectionPageList);
                }
                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.APPLICATION_PROCCESING)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setApplicationProcessingSectionPageList(sectionPageList);
                }
                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.ISSUING_PERSONALIZATION)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setIssuingPersonalizeSectionPageList(sectionPageList);
                }
                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.PRODUCT_SERVICES)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setProductServicesSectionPageList(sectionPageList);
                }
                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.SECURITY_SERVICES)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setSecurityServicesSectionPageList(sectionPageList);
                }
                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.TRAFFIC_FINANCIAL)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setTransactionFinanceSectionPageList(sectionPageList);
                }
                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.MONITOR_REPORT)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setMonitoringReportingSectionPageList(sectionPageList);
                }
                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.COLLECTION_RECOVERY)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setCollectionRecoverySectionPageList(sectionPageList);
                }
                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.CUSTOMER_SERVICE)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setCustomerServiceSectionPageList(sectionPageList);
                }
                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.ISSUER_SERVICE)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setIssuerServiceSectionPageList(sectionPageList);
                }
                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.BACK_OFFICEE)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setBackOfficeSectionPageList(sectionPageList);
                }
                if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.COMMON_CONFIG)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setCommonConfigSectionPageList(sectionPageList);
                }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                 if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.MERCHANT_MANAGEMENT)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setMerchantMgtSectionPageList(sectionPageList);
                }
                 if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.TERMINAL_MANAGEMENT)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setTerminalMgtSectionPageList(sectionPageList);
                }
                 if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.AQ_PRODUCT_SERVICES)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setAqProductServicesPageList(sectionPageList);
                }
                 if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.AQ_CUSTOMER_SERVIVE)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setAqCustomerServiceSectionPageList(sectionPageList);
                }
                 if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.MERCHANT_SERVICE)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setMerchantServiceSectionPageList(sectionPageList);
                }
                 if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.AQ_TRANSACTION_FINANCE)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setAqtransactionFinanceSectionPageList(sectionPageList);
                }
                 if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.AQ_BACK_OFFICE)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setAqBackOfficeSectionPageList(sectionPageList);
                }
                 if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.AQ_SECURITY_SERVICE)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setAqSecurityServiceSectionPageList(sectionPageList);
                }
                 if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.AQ_MONITOR_REPORT)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setAqMonitoringReportingSectionPageList(sectionPageList);
                }
                 if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.SWITCH_CONTROL)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setSwitchControlSectionPageList(sectionPageList);
                }
                 if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.AQUIRER_CONFIG)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setAquirerConfigSectionPageList(sectionPageList);
                }
                 if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.TRANSACTION_PROCCESING)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setTransactionProccesingSectionPageList(sectionPageList);
                }
                 if (applicationList.get(i).getApplicationCode().equals(ApplicationVarList.RECOVERY_MODULE)) {
                    this.getSectionPage(CMSSessionUser.getUserRole(), applicationList.get(i).getApplicationCode());
                    sessionVarList.setRecoverySectionPageList(sectionPageList);
                }
                
                
            }

//            CMSSessionUser.setSectionPageList(sectionPageList);
            sessionVarList.setCMSSessionUser(CMSSessionUser);
//            sessionVarList.setLoggedApplicationCode(applicationType);
            rd = request.getRequestDispatcher(url);





        } catch (SesssionExpException sex) {
            //redirect to login page
            response.sendRedirect(request.getContextPath() + "/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (Exception ex) {
            System.out.println(">>>>>>>>>>>>>>" + ex);
        } finally {
            rd.forward(request, response);
            out.close();
        }




    }

    private Map<SectionBean, List<PageBean>> getSectionPage(String userRole, String applicationType) throws Exception {


        userManager = new SystemUserManager();
        sectionPageList = userManager.getSectionPage(userRole, applicationType);
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
