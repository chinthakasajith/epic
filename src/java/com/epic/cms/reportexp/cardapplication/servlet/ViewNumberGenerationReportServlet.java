/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.NumberGenerationReportBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.NumberGenerationReportManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * @author asela
 */
public class ViewNumberGenerationReportServlet extends HttpServlet {
    
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private NumberGenerationReportManager numberGenerationReportManager;
    private List<NumberGenerationReportBean> searchList;
    private NumberGenerationReportBean numberGenerationReportBean;
    private NumberGenerationReportBean nmberGenerationReportBean;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private List<NumberGenerationReportBean> numberGenerationReportBeanList;
    private Map<String, String> cardTypesMap;
    private Map<String, String> priorityLevelMap;
    private Map<String, String> branchListMap;
    private List<NumberGenerationReportBean> cardProductList;
    private List<String> userList;
    private List<NumberGenerationReportBean> sortcardProductList;
    private List<NumberGenerationReportBean> searchBeanList;
    private String errorMessage;
    private SystemAuditBean systemAuditBean = null;
    private JasperPrint jasperPrint;
    private String url = "/reportexp/cardapplication/numbergenerationreport.jsp";

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
                String pageCode = PageVarList.NUMBER_GENERATION_RPT;
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    
                    try {
                        
                        String path = "/resources/reports/applicationreport/NumberGenerationReport.jasper";
                        
                        this.setUserInputsToBean(request);
                        
                        if (null != request.getParameter("cardType") && !request.getParameter("cardType").equals("")) {
                            request.setAttribute("cardType", request.getParameter("cardType"));
                            
                        }
                        this.getAllBranchListMap();
                        request.setAttribute("branchListMap", branchListMap);
                        this.getAllCardProductList();
                        if (null != request.getParameter("cardType") && !request.getParameter("cardType").equals("")) {
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
                        }
                        
                        request.setAttribute("searchbean", numberGenerationReportBean);
                        request.setAttribute("cardProductList", sortcardProductList);
                        this.getAllCardTypesMap();
                        request.setAttribute("cardTypesMap", cardTypesMap);
                        this.getAllPriorityLevelMap();
                        request.setAttribute("priorityLevelMap", priorityLevelMap);
                        this.getUserList();
                        request.setAttribute("userList", userList);
                        request.setAttribute("searchBeanList", searchBeanList);
                        
                        if (isValidate(numberGenerationReportBean)) {
                            this.getAllSearchDetails();
                            
                            nmberGenerationReportBean = new NumberGenerationReportBean();
                            
                            if (searchBeanList.size() > 0) {
                                nmberGenerationReportBean = searchBeanList.get(0);
                            } else {
                                request.setAttribute("errorMsg", MessageVarList.EMPTY_VALUE_MSG);
                            }
                            
                            HashMap map = new HashMap();
                            
                            if (null != request.getParameter("applicationId") && !request.getParameter("applicationId").equals("")) {
                                map.put("applicationId", request.getParameter("applicationId"));
                            } else {
                                map.put("applicationId", "---");
                            }
                            if (null != request.getParameter("nic") && !request.getParameter("nic").equals("")) {
                                map.put("nic", request.getParameter("nic"));
                            } else {
                                map.put("nic", "---");
                            }
                            if (null != request.getParameter("passport") && !request.getParameter("passport").equals("")) {
                                map.put("passport", request.getParameter("passport"));
                            } else {
                                map.put("passport", "---");
                            }
//                            if (null != request.getParameter("drivingLicence") && !request.getParameter("drivingLicence").equals("")) {
//                                map.put("drivingLicence", request.getParameter("drivingLicence"));
//                            } else {
//                                map.put("drivingLicence", "---");
//                            }
                            map.put("drivingLicence", "---");
                            if (null != request.getParameter("branch") && !request.getParameter("branch").equals("")) {
                                map.put("branchDes", branchListMap.get(request.getParameter("branch")));
                            } else {
                                map.put("branchDes", "All");
                            }
                            if (null != request.getParameter("priorityLevel") && !request.getParameter("priorityLevel").equals("")) {
                                map.put("priorityLevelDes", priorityLevelMap.get(request.getParameter("priorityLevel")));
                            } else {
                                map.put("priorityLevelDes", "All");
                            }
                            if (null != request.getParameter("cardType") && !request.getParameter("cardType").equals("")) {
                                map.put("cardTypeDes", cardTypesMap.get(request.getParameter("cardType")));
                            } else {
                                map.put("cardTypeDes", "All");
                            }
                            if (null != request.getParameter("cardProduct") && !request.getParameter("cardProduct").equals("")) {
                                
                                for (NumberGenerationReportBean bean : cardProductList) {
                                    if (bean.getCardProduct().equals(request.getParameter("cardProduct"))) {
                                        map.put("cardProductDesc", bean.getCardProductDesc());
                                    }
                                    break;
                                }
                                
                            } else {
                                map.put("cardProductDesc", "All");
                            }
                            if (null != request.getParameter("fromdate") && !request.getParameter("fromdate").equals("")) {
                                map.put("fromDate", request.getParameter("fromdate"));
                            } else {
                                map.put("fromDate", " ");
                            }
                            if (null != request.getParameter("todate") && !request.getParameter("todate").equals("")) {
                                map.put("todate", request.getParameter("todate"));
                            } else {
                                map.put("todate", " ");
                            }
                            if (null != request.getParameter("users") && !request.getParameter("users").equals("")) {
                                map.put("user", request.getParameter("users"));
                            } else {
                                map.put("user", "All");
                            }
                            if (null != request.getParameter("cardNumber") && !request.getParameter("cardNumber").equals("")) {
                                map.put("cardNumber", request.getParameter("cardNumber"));
                            } else {
                                map.put("cardNumber", "---");
                            }
                            
                            this.setAudittraceValue(request);
                            numberGenerationReportManager.getInstance().insertAuditValues(systemAuditBean);
                            
                            
                            response.setContentType("application/pdf");
                            InputStream inputStream = new FileInputStream(getServletContext().getRealPath(path));
                            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(this.getAllSearchDetails());
                            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, map, ds);
                            
                            JRPdfExporter exp = new JRPdfExporter();
                            exp.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
                            exp.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                            exp.exportReport();
                            
                            
                        } else {
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_NUMBER_GENERATION_REPORT);
                        
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
        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);
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
    
    public List<NumberGenerationReportBean> getAllSearchDetails() throws Exception {
        try {
            numberGenerationReportManager = new NumberGenerationReportManager();
            searchBeanList = numberGenerationReportManager.getNumberGenerationReportDetails(numberGenerationReportBean);
        } catch (Exception ex) {
            throw ex;
        }
        return searchBeanList;
    }
    
    private void setUserInputsToBean(HttpServletRequest request) throws Exception {
        try {
            
            String NIC = request.getParameter("nic").trim();
            String branch = request.getParameter("branch");
            String passport = request.getParameter("passport").trim();
            String priorityLevel = request.getParameter("priorityLevel");
            //String drivingLicence = request.getParameter("drivingLicence");
            String cardType = request.getParameter("cardType");
            String applicationId = request.getParameter("applicationId");
            String cardProduct = request.getParameter("cardProduct");
            String fromdate = request.getParameter("fromdate");
            String todate = request.getParameter("todate");
            String cardNumber = request.getParameter("cardNumber");
            String users = request.getParameter("users");
            
            numberGenerationReportBean = new NumberGenerationReportBean();
            
            numberGenerationReportBean.setNic(NIC);
            numberGenerationReportBean.setBranch(branch);
            numberGenerationReportBean.setPassport(passport);
            numberGenerationReportBean.setPriorityLevel(priorityLevel);
            //numberGenerationReportBean.setDrivingLicence(drivingLicence);
            numberGenerationReportBean.setCardType(cardType);
            numberGenerationReportBean.setApplicationId(applicationId);
            numberGenerationReportBean.setCardProduct(cardProduct);
            numberGenerationReportBean.setFromDate(fromdate);
            numberGenerationReportBean.setTodate(todate);
            numberGenerationReportBean.setCardNumber(cardNumber);
            numberGenerationReportBean.setUser(users);
            
            
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private boolean isValidate(NumberGenerationReportBean inputBean) throws Exception {
        
        boolean isValidate = true;
        try {
            
            if (!inputBean.getNic().equals("")) {
                if (!UserInputValidator.isAlphaNumeric(inputBean.getNic())) {
                    isValidate = false;
                    errorMessage = MessageVarList.INVALID_NIC;
                }
            }
            if (!inputBean.getPassport().equals("")) {
                if (!UserInputValidator.isAlphaNumeric(inputBean.getPassport())) {
                    isValidate = false;
                    errorMessage = MessageVarList.ERROR_PAS_NUMBER;
                }
            }
//            if (!inputBean.getDrivingLicence().equals("")) {
//                if (!UserInputValidator.isAlphaNumeric(inputBean.getDrivingLicence())) {
//                    isValidate = false;
//                    errorMessage = MessageVarList.ERROR_DRL_NUMBER;
//                }
//            }
            if (!inputBean.getApplicationId().equals("")) {
                if (!UserInputValidator.isNumeric(inputBean.getApplicationId())) {
                    isValidate = false;
                    errorMessage = MessageVarList.APPASSIGN_APPLICATIONCODE_INVALID;
                }
            }
            if (!inputBean.getCardNumber().equals("")) {
                if (!UserInputValidator.isNumeric(inputBean.getCardNumber())) {
                    isValidate = false;
                    errorMessage = MessageVarList.CARDNUMBER_INVALID;
                }
            }
            
        } catch (Exception e) {
            throw e;
        }
        return isValidate;
    }
    
    private String getCurrentDate() throws ParseException {
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        
        return dateFormat.format(date);
        
    }
    
    private void getAllPriorityLevelMap() throws Exception {
        try {
            numberGenerationReportManager = new NumberGenerationReportManager();
            priorityLevelMap = numberGenerationReportManager.getAllPriorityLevelMap();
        } catch (Exception e) {
            throw e;
        }
    }
    
    private void getAllCardTypesMap() throws Exception {
        try {
            numberGenerationReportManager = new NumberGenerationReportManager();
            cardTypesMap = numberGenerationReportManager.getAllCardTypesMap();
        } catch (Exception e) {
            throw e;
        }
    }
    
    private void getAllBranchListMap() throws Exception {
        try {
            numberGenerationReportManager = new NumberGenerationReportManager();
            branchListMap = numberGenerationReportManager.getAllBranchListMap();
        } catch (Exception e) {
            throw e;
        }
    }
    
    private void getAllCardProductList() throws Exception {
        try {
            numberGenerationReportManager = new NumberGenerationReportManager();
            cardProductList = numberGenerationReportManager.getAllCardProductList();
        } catch (Exception e) {
            throw e;
        }
    }
    
    private void getUserList() throws Exception {
        try {
            numberGenerationReportManager = new NumberGenerationReportManager();
            userList = numberGenerationReportManager.getAllUserList();
        } catch (Exception e) {
            throw e;
        }
    }
    
    private void setAudittraceValue(HttpServletRequest request) throws Exception {
        
        try {
            systemAuditBean = new SystemAuditBean();
            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set description 
            systemAuditBean.setDescription("Generated Number Generation Report By " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.REPORT_EXPLORER);
            systemAuditBean.setSectionCode(SectionVarList.CARD_APPLICATION_REPORT);
            systemAuditBean.setPageCode(PageVarList.NUMBER_GENERATION_RPT);
            systemAuditBean.setTaskCode(TaskVarList.SEARCH);
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
