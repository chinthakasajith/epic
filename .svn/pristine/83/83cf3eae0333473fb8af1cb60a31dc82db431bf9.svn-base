/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.merchant.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.merchant.bean.MerchantCustomerReportBean;
import com.epic.cms.reportexp.merchant.businesslogic.MerchantCustomerDetailManager;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

/**
 *
 * @author admin
 */
public class GenerateMerchantCustomerReportServlet extends HttpServlet {
    
    private RequestDispatcher rd;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private MerchantCustomerDetailManager merchantCusDetailManager = null;
    private HashMap<String, String> areaList = null;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String url = "/reportexp/merchant/merchantcustomer/merchantcusreporthome.jsp";
    private MerchantCustomerReportBean summeryBean= null;
    private List<MerchantCustomerReportBean> summeryList = null;
    private String errorMessage = null;
    private JasperPrint jasperPrint;
    private SystemAuditBean systemAuditBean = null;
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
                    String pageCode = PageVarList.MERCHANT_CUS_REPORT;
                    String taskCode = TaskVarList.VIEWPDF;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        try {
                            String path = "/resources/reports/applicationreport/MerchantCustomerReport.jasper";
                            this.getAreas();
                            request.setAttribute("areaList", areaList);
                            
                            setUserInputToBean(request);
                            
                            if (validateUserInput(summeryBean)) {
                               summeryList=this.searchApplicationSummeryReport(summeryBean);

                               request.setAttribute("summeryList", summeryList);
                               request.setAttribute("summeryBean", summeryBean);
                               
                               Map map = new HashMap();
                               
                               String merchantCusID=summeryBean.getMerchantCusID();
                               String merchantID=summeryBean.getMerchantID();
                               String merchantCusName=summeryBean.getMerchantCusName();
                               String terminalID=summeryBean.getTerminalID();
                               String merchantCusArea=summeryBean.getMerchantCusArea();
                               String merchantCusStatus=summeryBean.getMerchantCusStatus();
                               String fromDate=summeryBean.getFromDate();
                               String toDate=summeryBean.getToDate();
                               
                              if (merchantCusID!=null && !merchantCusID.equals("")) {
                                    map.put("merchantCusID", merchantCusID);
                              } else {
                                     map.put("merchantCusID", " ");
                              }
                              if (merchantID!=null && !merchantID.equals("")) {
                                    map.put("merchantID", merchantID);
                              } else {
                                     map.put("merchantID", " ");
                              }
                              if (merchantCusName!=null && !merchantCusName.equals("")) {
                                    map.put("merchantCusName", merchantCusName);
                              } else {
                                     map.put("merchantCusName", " ");
                              }
                              if (terminalID!=null && !terminalID.equals("")) {
                                    map.put("terminalID", terminalID);
                              } else {
                                     map.put("terminalID", " ");
                              }
                              if (merchantCusArea!=null && !merchantCusArea.equals("")) {
                                    map.put("merchantCusArea", merchantCusArea);
                              } else {
                                     map.put("merchantCusArea", "All");
                              }
                              if (merchantCusStatus!=null && !merchantCusStatus.equals("")) {
                                    map.put("merchantCusStatus", merchantCusStatus);
                              } else {
                                     map.put("merchantCusStatus", "All");
                              }
                              if (fromDate!=null && !fromDate.equals("")) {
                                    map.put("fromDate", fromDate);
                              } else {
                                     map.put("fromDate", " ");
                              }
                              if (toDate!=null && !toDate.equals("")) {
                                    map.put("toDate", toDate);
                              } else {
                                     map.put("toDate", " ");
                              }
                              
                            response.setContentType("application/pdf");
                            InputStream inputStream = new FileInputStream(getServletContext().getRealPath(path));
                            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(this.searchApplicationSummeryReport(summeryBean));
                            jasperPrint = JasperFillManager.fillReport(inputStream, map, ds);
                        
                            JRPdfExporter exp = new JRPdfExporter();
                            exp.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
                            exp.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                            exp.exportReport();
                            this.setAudittraceValue(request);
                            this.setAuditTrace(systemAuditBean);
                                                 
                            } else {
                                request.setAttribute("summeryBean", summeryBean);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);
                            }
                            
                        } catch (Exception ex) {
                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            rd = getServletContext().getRequestDispatcher(url);
                        }
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
          
           rd = request.getRequestDispatcher(url);
           
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
    
    private void getAreas() throws Exception {
        try {
            merchantCusDetailManager = new MerchantCustomerDetailManager();
            areaList = new HashMap<String, String>();
            areaList = merchantCusDetailManager.getAreas();
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private String getCurrentDate() throws Exception {
        String currentDate = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            currentDate = dateFormat.format(date);
        } catch (Exception ex) {
            throw ex;
        }
        return currentDate;
    }
    
     public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean success = false;
        try {
            summeryBean = new MerchantCustomerReportBean();

            summeryBean.setMerchantCusID(request.getParameter("mcid").trim());
            summeryBean.setMerchantID(request.getParameter("mid").trim());
            summeryBean.setMerchantCusName(request.getParameter("mcname").trim());
            summeryBean.setTerminalID(request.getParameter("tid").trim());
            summeryBean.setMerchantCusArea(request.getParameter("mcarea").trim());
            summeryBean.setMerchantCusStatus(request.getParameter("merchantStatus").trim());
            summeryBean.setFromDate(request.getParameter("fromDate").trim());
            summeryBean.setToDate(request.getParameter("toDate").trim());

            success = true;

        } catch (Exception e) {
            success = false;
            throw e;
        }
        return success;
    }
     
    public boolean validateUserInput(MerchantCustomerReportBean summeryBean) throws Exception {
        boolean isValidate = true;

        //////validate user Role code
        String mcid=summeryBean.getMerchantCusID();
        String mcname=summeryBean.getMerchantCusName();
        String mid=summeryBean.getMerchantID();
        String tid=summeryBean.getTerminalID();
        
        try {
            if (!(mcid.equals("")) && !UserInputValidator.isAlphaNumeric(mcid)) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!(mcname.equals("")) && !UserInputValidator.isAlpha(mcname)) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!(mid.equals("")) && !UserInputValidator.isAlphaNumeric(mid)) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!(tid.equals("")) && !UserInputValidator.isAlphaNumeric(tid)) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;
            }

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    } 
    
    private List<MerchantCustomerReportBean> searchApplicationSummeryReport(MerchantCustomerReportBean summeryBean) throws Exception {

        try {
            merchantCusDetailManager = new MerchantCustomerDetailManager();
            summeryList = new ArrayList<MerchantCustomerReportBean>();

            summeryList = merchantCusDetailManager.searchMerchantCusReport(summeryBean);
            
            return summeryList;
        } catch (Exception ex) {
            throw ex;
        }

    }
    
        private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("secCode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Generate Merchant Customer Report by "
                    + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.USERMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.MERCHANT_CUS_REPORT);
            systemAuditBean.setTaskCode(TaskVarList.VIEWPDF);
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
        
    private void setAuditTrace(SystemAuditBean systemAuditBean) {
        merchantCusDetailManager = new MerchantCustomerDetailManager();
        try {
            merchantCusDetailManager.setAuditTrace(systemAuditBean);
        } catch (SQLException ex) {
            Logger.getLogger(GenerateMerchantCustomerReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GenerateMerchantCustomerReportServlet.class.getName()).log(Level.SEVERE, null, ex);
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
