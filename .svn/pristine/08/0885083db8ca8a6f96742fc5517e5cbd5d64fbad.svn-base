/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationPinGenAndMailBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationPinGenAndMailManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import java.util.ArrayList;

/**
 *
 * @author jeevan
 */
public class LoadPinGenAndMailRepServlet extends HttpServlet {

    private ApplicationPinGenAndMailManager pinGenerationReportManager;
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private ApplicationPinGenAndMailManager appPinMgr;//
    private ApplicationAssignManager appAssignManager;
    private List<String> usersList = null;
    private List<CardProductBean> cardProduct = null;
    private List<ApplicationPinGenAndMailBean> searchList;
//    private ApplicationPinGenAndMailBean numberGenerationReportBean;
    private ApplicationPinGenAndMailBean pinMailBean;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String url = "/reportexp/cardapplication/applicationpingenandmailhome.jsp";
    private Map<String, String> cardTypesMap;
    private Map<String, String> priorityLevelMap;
    private Map<String, String> priorityLevel;
    private Map<String, String> branchListMap;
    //private List<NumberGenerationReportBean> cardProductList;
    private List<String> userList;
    private List<ApplicationPinGenAndMailBean> searchBeanList;
    private HashMap<String, String> bnkBranches = null;
    private HashMap<String, String> appStatusList = null;
    private HashMap<String, String> cardDomainList = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> cardPinMethodList = null;
    private HashMap<String, String> cardProductList = null;
    private HashMap<String, String> pinGenTime = null;
    private HashMap<String, String> cardTypeList = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //  PrintWriter out = response.getWriter();
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
                String pageCode = PageVarList.PIN_GEN_AND_MAIL;
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

//                    String path = "/resources/reports/applicationreport/PinGenAndMailReport.jasper";
//                     this.setUserInputsToBean(request);
//
//                    if (null != request.getParameter("cardType") && !request.getParameter("cardType").equals("")) {
//                        request.setAttribute("cardType", request.getParameter("cardType"));
//
//                    }
                    
                    this.getBranchNames();
                    request.setAttribute("bnkBranchList", bnkBranches);

                    this.getPriorityLevels();

                    this.getCardDomains();
                    request.setAttribute("cardDomainList", cardDomainList);

                    this.getCardType();
                    request.setAttribute("cardTypeList", cardTypeList);
//                    request.setAttribute("cardTypeListcardTypeList", cardTypeList);

                    this.getCardPinMethod();
                    request.setAttribute("bnkCardPinMethodList", cardPinMethodList);

                    this.getAllUserList();
                    request.setAttribute("usersList", usersList);

//                    this.getCardProduct(pinMailBean.getCardType());
//                    request.setAttribute("bnkCardProductList", cardProductList);
//
                    this.getCardProductList(); 
                    request.setAttribute("bulkCProductList", cardProduct);

                    this.getPinGenTime();
                    request.setAttribute("fromdate", pinGenTime);
                    
//                    searchBeanList = new ArrayList<ApplicationPinGenAndMailBean>();
//                    this.getAllSearchDetails();
//                    request.setAttribute("searchList", searchBeanList);
                    
                    rd = getServletContext().getRequestDispatcher(url);

                 
//                    System.out.println("size : " + searchBeanList.size());
//                    pinMailBean = new ApplicationPinGenAndMailBean();
//
//                    if (searchBeanList.size() > 0) {
//                        pinMailBean = searchBeanList.get(0);
//                    } else {
//                        request.setAttribute("errorMsg", MessageVarList.EMPTY_VALUE_MSG);
//                    }

                } else {
                    throw new AccessDeniedException();

                }
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
           
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("errorMsg", MessageVarList.ERROR_APP_PIN_GEN_AND_MAIL);
            rd = getServletContext().getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
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

    public List<ApplicationPinGenAndMailBean> getAllSearchDetails() throws Exception {
        try {
            pinGenerationReportManager = new ApplicationPinGenAndMailManager();
            searchBeanList = pinGenerationReportManager.getPinGenAndMailDetails(pinMailBean);
        } catch (Exception ex) {
            throw ex;
        }
        return searchBeanList;
    }

    private void setUserInputsToBean(HttpServletRequest request) throws Exception {
        try {

            String NIC = request.getParameter("nic");
            String branch = request.getParameter("branch"); 
            String passport = request.getParameter("passport"); 
            String priorityLevel = request.getParameter("priority"); 
            String drivingLicence = request.getParameter("licence");
            String cardType = request.getParameter("card_ty"); 
            String cardProduct = request.getParameter("card_product"); 
            String fromdate = request.getParameter("fromdate"); 
            String todate = request.getParameter("todate"); 
            String cardNumber = request.getParameter("cardNo");
            String users = request.getParameter("user"); 

            String domain = request.getParameter("domain");
            String pinMethod = request.getParameter("card_pin");


            pinMailBean = new ApplicationPinGenAndMailBean();

            pinMailBean.setNic(NIC); 
            pinMailBean.setBranchName(branch);
            pinMailBean.setPassport(passport); 
            pinMailBean.setPriorityLevelDes(priorityLevel);
            pinMailBean.setLicence(drivingLicence); 
            pinMailBean.setCardType(cardType); 
            pinMailBean.setCardProduct(cardProduct);
            pinMailBean.setFromDate(fromdate); 
            pinMailBean.setCardNo(cardNumber); 
            pinMailBean.setUser(users); 

            pinMailBean.setDomainDes(domain); 
            pinMailBean.setPinMethod(pinMethod); 
            pinMailBean.setBranchName(branch);


        } catch (Exception ex) {
            throw ex;
        }
    }

    private String getCurrentDate() throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return dateFormat.format(date);

    }

    private void getBranchNames() throws Exception {
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();
            bnkBranches = appPinMgr.getBranchNames();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getPriorityLevels() throws Exception {
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();
            priorityLevelList = appPinMgr.getPriorityLevel();
            sessionVarlist.setPriorityLevelList(priorityLevelList);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardDomains() throws Exception {
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();
            cardDomainList = appPinMgr.getCardDomains();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardType() throws Exception {
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();
            cardTypeList = appPinMgr.getCardType();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardPinMethod() throws Exception {
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();
            cardPinMethodList = appPinMgr.getCardPinMethod();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardProduct(String cardType) throws Exception {
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();
            cardProductList = appPinMgr.getCardProduct(cardType);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getPinGenTime() throws Exception {
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();
            pinGenTime = appPinMgr.getPinGenTime();
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

    private void getAllUserList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            usersList = appAssignManager.getAllUserList(PageVarList.PIN_GEN_AND_MAIL);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getCardProductList() throws Exception {
        cardProduct = new ArrayList<CardProductBean>();
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();

            cardProduct = appPinMgr.getCardProductList();

        } catch (Exception ex) {
            throw ex;
        }
    }
}