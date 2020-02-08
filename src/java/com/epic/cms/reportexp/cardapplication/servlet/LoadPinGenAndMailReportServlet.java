/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
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
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

/**
 *
 * @author jeevan
 */
public class LoadPinGenAndMailReportServlet extends HttpServlet {
    private ApplicationPinGenAndMailManager numberGenerationReportManager;
   // private NumberGenerationReportBean numberGenerationReportBean;
  //  private NumberGenerationReportBean nmberGenerationReportBean;
    
    
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private ApplicationPinGenAndMailManager appPinMgr;//
    private List<ApplicationPinGenAndMailBean> searchList;
//    private ApplicationPinGenAndMailBean numberGenerationReportBean;
    private ApplicationPinGenAndMailBean andMailBean;
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
    private List<CardProductBean> cardProduct = null;
    private ApplicationAssignManager appAssignManager;
    private List<String> usersList = null;

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
                String pageCode = PageVarList.NUMBER_GENERATION_RPT;    ////
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    
                    String path = "/resources/reports/applicationreport/PinGenAndMailReport.jasper";
                    this.setUserInputsToBean(request);
                    
                    if (null != request.getParameter("cardType") && !request.getParameter("cardType").equals("")) {
                        request.setAttribute("cardType", request.getParameter("cardType"));
                        
                    }
                    //this.setUserInputsToBean(request);
                    ///this.getAllBranchListMap();
                   // request.setAttribute("branchListMap", branchListMap);
                  //  this.getAllCardProductList();
                /*    if (null != request.getParameter("cardType") && !request.getParameter("cardType").equals("")) {
                        sortcardProductList = new ArrayList<NumberGenerationReportBean>();
                        for (NumberGenerationReportBean bean : cardProductList) {
                            if (bean.getCardType().equals(request.getParameter("cardType"))) {
                                sortcardProductList.add(bean);
                            }
                        }
                    } else {
                        sortcardProductList = new ArrayList<NumberGenerationReportBean>();
                        for (NumberGenerationReportBean bean : cardProductList) {
                            sortcardProductList.add(bean);
                        }
                    }*/
                    
                    this.getBranchNames();
                    request.setAttribute("bnkBranchList", bnkBranches);

                    this.getPriorityLevels();

                    this.getCardDomains();
                    request.setAttribute("cardDomainList", cardDomainList);
                    
                    this.getCardType();
                    request.setAttribute("cardTypeList", cardTypeList);
                    
                    this.getCardPinMethod();
                    request.setAttribute("bnkCardPinMethodList", cardPinMethodList);
                    
//                    this.getCardProduct(andMailBean.getCardType());
//                    request.setAttribute("bnkCardProductList", cardProductList);
                    this.getCardProductList(); 
                    request.setAttribute("bulkCProductList", cardProduct);
                    
                    this.getAllUserList();
                    request.setAttribute("usersList", usersList);
                    
                    this.getPinGenTime();
                    request.setAttribute("fromdate", pinGenTime);
                    
                    rd = getServletContext().getRequestDispatcher(url);
                    
                    this.getAllSearchDetails();
                    System.out.println("size : " + searchBeanList.size());
                    andMailBean = new ApplicationPinGenAndMailBean();
                    
                    if (searchBeanList.size() > 0) {
                        andMailBean = searchBeanList.get(0);
                    } else {
                        request.setAttribute("errorMsg", MessageVarList.EMPTY_VALUE_MSG);  ///////////
                    }
                    
                    Map map = new HashMap();
                    
                    if (null != request.getParameter("cardNo") && !request.getParameter("cardNo").equals("")) {
                        map.put("cardNo", andMailBean.getApplicationId());
                    } else {
                        map.put("cardNo", " ");
                    }
                    if (null != request.getParameter("nic") && !request.getParameter("nic").equals("")) {
                        map.put("nic", andMailBean.getNic());
                    } else {
                        map.put("nic", " ");
                    }
                    if (null != request.getParameter("passport") && !request.getParameter("passport").equals("")) {
                        map.put("passport", andMailBean.getPassport());
                    } else {
                        map.put("passport", " ");
                    }
                    if (null != request.getParameter("licence") && !request.getParameter("licence").equals("")) {
                        map.put("licence", andMailBean.getLicence());
                    } else {
                        map.put("licence", " ");
                    }
                    if (null != request.getParameter("user") && !request.getParameter("user").equals("")) {
                        map.put("user", andMailBean.getBranchName());
                    } else {
                        map.put("user", "All");
                    }
                    if (null != request.getParameter("domainDes") && !request.getParameter("domainDes").equals("")) {
                        map.put("domainDes", andMailBean.getPriorityLevelDes());
                    } else {
                        map.put("domainDes", "All");
                    }
                    if (null != request.getParameter("cardType") && !request.getParameter("cardType").equals("")) {
                        map.put("cardType", andMailBean.getCardTypeDes());
                    } else {
                        map.put("cardType", "All");
                    }
                    if (null != request.getParameter("cardProduct") && !request.getParameter("cardProduct").equals("")) {
                        map.put("cardProduct", andMailBean.getCardProduct());
                    } else {
                        map.put("cardProduct", "All");
                    }
                    if (null != request.getParameter("fromDate") && !request.getParameter("fromDate").equals("")) {
                        map.put("fromDate", andMailBean.getFromDate());
                    } else {
                        map.put("fromDate", " ");
                    }
                    if (null != request.getParameter("toDate") && !request.getParameter("toDate").equals("")) {
                        map.put("toDate", andMailBean.getToDate());
                    } else {
                        map.put("toDate", " ");
                    }
                    if (null != request.getParameter("pinMethod") && !request.getParameter("pinMethod").equals("")) {
                        map.put("pinMethod", andMailBean.getUser());
                    } else {
                        map.put("pinMethod", "All");
                    }
                    if (null != request.getParameter("branchName") && !request.getParameter("branchName").equals("")) {
                        map.put("branchName", andMailBean.getBranchName());
                    } else {
                        map.put("branchName", "All");
                    }
                    
                    response.setContentType("application/pdf");
                    InputStream inputStream = new FileInputStream(getServletContext().getRealPath(path));
                    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(this.getAllSearchDetails());
                    JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, map, ds);
                    
                    JRPdfExporter exp = new JRPdfExporter();
                    exp.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
                    exp.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                    exp.exportReport();
                    
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
            ex.printStackTrace();
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_NUMBER_GENERATION_REPORT);
            
        } finally {
            //    out.close();
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
            //andMailBean = new ApplicationPinGenAndMailBean();
            numberGenerationReportManager=new ApplicationPinGenAndMailManager();
//            searchBeanList = numberGenerationReportManager.getNumberGenerationReportDetails(numberGenerationReportBean);
            searchBeanList = numberGenerationReportManager.getPinGenAndMailDetails(andMailBean);
        } catch (Exception ex) {
            throw ex;
        }
        return searchBeanList;
    }
    
    private void setUserInputsToBean(HttpServletRequest request) throws Exception {
        try {
            
            String NIC = request.getParameter("nic");//
            String branch = request.getParameter("branch"); //
            String passport = request.getParameter("passport"); //
            String priorityLevel = request.getParameter("priority"); //
            //String drivingLicence = request.getParameter("licence");//
            String cardType = request.getParameter("card_ty"); //
            String cardProduct = request.getParameter("cardProduct"); //
            String fromdate = request.getParameter("fromdate"); //
            String todate = request.getParameter("todate"); //
            String cardNumber = request.getParameter("cardNo");//
            String users = request.getParameter("user"); //
            
            String domain = request.getParameter("domain");
            String pinMethod = request.getParameter("card_pin");
            
            
            andMailBean = new ApplicationPinGenAndMailBean();
            
            andMailBean.setNic(NIC); ///
            andMailBean.setBranchName(branch);
            andMailBean.setPassport(passport); //
            andMailBean.setPriorityLevelDes(priorityLevel);
            //andMailBean.setLicence(drivingLicence); //
            andMailBean.setCardType(cardType); //
            andMailBean.setCardProduct(cardProduct);
            andMailBean.setFromDate(fromdate); //
            andMailBean.setCardNo(cardNumber); //
            andMailBean.setUser(users); //
            
            andMailBean.setDomainDes(domain); //
            andMailBean.setPinMethod(pinMethod); ///
            andMailBean.setBranchName(branch);
  //          numberGenerationReportBean.setUser(users);
            
            
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private String getCurrentDate() throws ParseException {
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        
        return dateFormat.format(date);
        
    }
    
    private void getBranchNames() throws Exception{
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
    
    private void getCardType() throws Exception{
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
        } catch(Exception ex) {
            throw ex;
        }
    }
//    
//    private void getCardProduct(String cardType) throws Exception {
//        try {
//            appPinMgr = new ApplicationPinGenAndMailManager();
//            cardProductList = appPinMgr.getCardProduct(cardType);
//        } catch(Exception ex) {
//            throw ex;
//        }
//    }
    private void getCardProductList() throws Exception {
        cardProduct = new ArrayList<CardProductBean>();
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();

            cardProduct = appPinMgr.getCardProductList();

        } catch (Exception ex) {
            throw ex;
        }
    }
     private void getAllUserList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            usersList = appAssignManager.getAllUserList(PageVarList.PIN_GEN_AND_MAIL);

        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private void getPinGenTime() throws Exception {
        try {
            appPinMgr = new ApplicationPinGenAndMailManager();
            pinGenTime = appPinMgr.getPinGenTime();
        } catch(Exception ex) {
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
