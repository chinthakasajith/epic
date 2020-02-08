/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardExpensesBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardIncomeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerEmploymentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.camm.applicationproccessing.capturedata.util.LoadApplicationStatus;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.cpmm.cardembossing.businesslogic.CardEmbossingMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.fileencrypt.CryptoException;
import com.epic.cms.system.util.fileencrypt.CryptoUtils;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.StatusVarList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author janaka_h
 */
public class UploadDocumentServlet extends HttpServlet {

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
    private String url = "/camm/capturedata/inputCaptureData.jsp";
    private String ur2 = "/camm/capturedata/suplimentoryapplication.jsp";
    private String urlCardagainstFD = "/camm/capturedata/cardagainstFDInputCaptureData.jsp";
    private String urlCorporate = "/camm/capturedata/corporateInputCaptureData.jsp";
    private String urlEstablishment = "/camm/capturedata/establishmentInputCaptureData.jsp";
    private ApplicationCheckingManager checkingManager;
    private CardApplicationStatusBean appStatusBean = null;
    private CustomerPersonalBean customerPersonalBean = null;
    private SupplementaryApplicationBean customerSuplimentoryPersonalBean = null;
    private CustomerEmploymentBean employmentBean = null;
    private List<CardIncomeBean> incomeBeanList = null;
    private SystemAuditBean systemAuditBean;
    private CardExpensesBean expensesBean = null;
    private List<CardBankDetailsBean> bankDetailsBeanLst = null;
    private List<DocumentUploadBean> documentDetailsBeanLst = null;
    private CommonFilePathBean commonFilePathBean = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
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

            sessionVarlist.setCMSSessionUser(sessionUser);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
            
            if(sessionVarlist.getApplicationTypeCode()!=null && sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_AGAINST_FD_CODE)){
                url=urlCardagainstFD;
            }else if(sessionVarlist.getApplicationTypeCode()!=null && sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_COPORATE)){
                url=urlCorporate;
            }else if(sessionVarlist.getApplicationTypeCode()!=null && sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_APP_CATEGORY_ESTABLISMENT_CODE)){
                url=urlEstablishment;
            }
                    

            int fsize = 1024;
            String documentRoot = "DOCUMENT";
            String rootPath = "";
            String applicationId = sessionVarlist.getApplicationId();

            boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            if (isMultipart) {
                // Create a factory for disk-based file items
                DiskFileItemFactory factory = new DiskFileItemFactory();
                // Set factory constraints
                factory.setSizeThreshold(1 * 1024 * 1024);
                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);

                // Set overall request size constraint
                upload.setSizeMax(fsize * 1024);
                // Parse the request
                List items = upload.parseRequest(request);

                HashMap<String, FileItem> formMap = this.convertToMaps(items);

                Iterator itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();

                    if (item.isFormField()) {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                    } else {
                        //Handle Uploaded files.
                        // System.out.println("Field Name = " + item.getFieldName());
                        System.out.println("File Name = " + item.getName());
                        // System.out.println("Content type = " + item.getContentType());
                        //  System.out.println("File Size = " + item.getSize() / 1024 + "kB");

                        DocumentUploadBean uploadBean = new DocumentUploadBean();
                        uploadBean.setApplicationId(sessionVarlist.getApplicationId());
                        uploadBean.setVerificationCategory(formMap.get("vCategory").getString());
                        uploadBean.setDocumentType(formMap.get("dType").getString());
                        uploadBean.setFileName(this.getGenerateFileName(item.getName(), uploadBean));

                        int msg = 0;
                        if (uploadBean.getVerificationCategory() == null || uploadBean.getVerificationCategory().isEmpty()) {
                            request.setAttribute("verificationCategory", "Requierd");
                            msg = 1;
                        } else {

                            if (sessionVarlist.getSessionDocumentList() != null) {
                                for (int x = 0; x < sessionVarlist.getSessionDocumentList().size(); x++) {

                                    if (uploadBean.getVerificationCategory().equals(sessionVarlist.getSessionDocumentList().get(x).getVerificationCategory()) && uploadBean.getDocumentType().equals(sessionVarlist.getSessionDocumentList().get(x).getDocumentType())) {
                                        request.setAttribute("docType", "Exist");
                                        msg = 1;
                                    }

                                }

                            }

                        }
                        if (uploadBean.getDocumentType() == null || uploadBean.getDocumentType().isEmpty()) {
                            request.setAttribute("docType", "Requierd");
                            msg = 1;
                        }

                        if (msg == 0) {

                            if (!(item.getSize() > fsize * 1024)) {

                                String osType = this.getOS_Type().toUpperCase();

                                CardEmbossingMgtManager cardEmbossingManager = new CardEmbossingMgtManager();
                                commonFilePathBean = cardEmbossingManager.getFilePaths(osType);

                                if (osType.equals("WINDOWS")) {
                                    rootPath = commonFilePathBean.getScandocument() + "/" + applicationId + "/" + uploadBean.getVerificationCategory() + "/" + uploadBean.getDocumentType();
                                    //System.out.println("path is "+rootPath);
                                    this.createDirectory(rootPath);

                                } else if (osType.equals("LINUX")) {
                                    rootPath = commonFilePathBean.getScandocument() + "/" + applicationId + "/" + uploadBean.getVerificationCategory() + "/" + uploadBean.getDocumentType();
                                    this.createDirectory(rootPath);
                                }

                                File file = new File(rootPath, uploadBean.getFileName());
                                //CryptoUtils.encrypt("aaaa", item.getName(), file);
                                item.write(file);

                                //get input file for encrypt
                                File inputFile = new File(rootPath, uploadBean.getFileName());
                                //output file
                                File encryptedFile = new File(rootPath, uploadBean.getFileName());
                                String key = "Mary has one cat";
                                try {
                                    CryptoUtils.encrypt(key, inputFile, encryptedFile);
                                } catch (CryptoException ex) {
                                    throw ex;
                                }
                                if (sessionVarlist.getSessionDocumentList() != null) {
                                    sessionVarlist.getSessionDocumentList().add(uploadBean);
                                } else {
                                    List<DocumentUploadBean> list = new ArrayList<DocumentUploadBean>();

                                    list.add(uploadBean);
                                    sessionVarlist.setSessionDocumentList(list);

                                }

                                if (sessionVarlist.getCardCategory().equals(StatusVarList.MAIN)) {
                                    this.loadDefaultApplicationStatus(applicationId, request);
                                    rd = getServletContext().getRequestDispatcher(url);
                                } else if (sessionVarlist.getCardCategory().equals(StatusVarList.SUPLIMENTORY)) {
                                    this.loadDefaultSuplimentoryApplicationStatus(applicationId, request);
                                    rd = getServletContext().getRequestDispatcher(ur2);
                                }else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_AGAINST_FD_CODE)){
                                    LoadApplicationStatus.loadDefaultCardagainstFDApplicationStatus(applicationId, sessionVarlist, request,false,2);
                                    rd = getServletContext().getRequestDispatcher(urlCardagainstFD);
                                }else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_COPORATE)){
                                    LoadApplicationStatus.loadDefaultCorporateApplicationStatus(applicationId, sessionVarlist, request,false,1);
                                    rd = getServletContext().getRequestDispatcher(urlCorporate);
                                }else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_APP_CATEGORY_ESTABLISMENT_CODE)){
                                    LoadApplicationStatus.loadDefaultEstablishmentApplicationStatus(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null,3);
                                    rd = getServletContext().getRequestDispatcher(urlEstablishment);
                                }
                                

                                rd.forward(request, response);
                            } else {

                                if (sessionVarlist.getCardCategory().equals(StatusVarList.MAIN)) {
                                    this.loadDefaultApplicationStatus(applicationId, request);
                                    rd = getServletContext().getRequestDispatcher(url);
                                } else if (sessionVarlist.getCardCategory().equals(StatusVarList.SUPLIMENTORY)) {
                                    this.loadDefaultSuplimentoryApplicationStatus(applicationId, request);
                                    rd = getServletContext().getRequestDispatcher(ur2);
                                }else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_AGAINST_FD_CODE)){
                                    LoadApplicationStatus.loadDefaultCardagainstFDApplicationStatus(applicationId, sessionVarlist, request,false,2);
                                    rd = getServletContext().getRequestDispatcher(urlCardagainstFD);
                                }else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_COPORATE)){
                                    LoadApplicationStatus.loadDefaultCorporateApplicationStatus(applicationId, sessionVarlist, request,false,1);
                                    rd = getServletContext().getRequestDispatcher(urlCorporate);
                                }else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_APP_CATEGORY_ESTABLISMENT_CODE)){
                                    LoadApplicationStatus.loadDefaultEstablishmentApplicationStatus(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null,3);
                                    rd = getServletContext().getRequestDispatcher(urlEstablishment);
                                }
                                rd.forward(request, response);
                            }

                        } else {
                            request.setAttribute("category", uploadBean.getVerificationCategory());
                            request.setAttribute("type", uploadBean.getDocumentType());
                                if (sessionVarlist.getCardCategory().equals(StatusVarList.MAIN)) {
                                    this.loadDefaultApplicationStatus(applicationId, request);
                                    rd = getServletContext().getRequestDispatcher(url);
                                } else if (sessionVarlist.getCardCategory().equals(StatusVarList.SUPLIMENTORY)) {
                                    this.loadDefaultSuplimentoryApplicationStatus(applicationId, request);
                                    rd = getServletContext().getRequestDispatcher(ur2);
                                }else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_AGAINST_FD_CODE)){
                                    LoadApplicationStatus.loadDefaultCardagainstFDApplicationStatus(applicationId, sessionVarlist, request,false,2);
                                    rd = getServletContext().getRequestDispatcher(urlCardagainstFD);
                                }else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_COPORATE)){
                                    LoadApplicationStatus.loadDefaultCorporateApplicationStatus(applicationId, sessionVarlist, request,false,1);
                                    rd = getServletContext().getRequestDispatcher(urlCorporate);
                                }else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_APP_CATEGORY_ESTABLISMENT_CODE)){
                                    LoadApplicationStatus.loadDefaultEstablishmentApplicationStatus(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null,3);
                                    rd = getServletContext().getRequestDispatcher(urlEstablishment);
                                }
                            rd.forward(request, response);

                        }
                    }
                }

            }

        } catch (SQLException ex) {

            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            if (sessionVarlist.getCardCategory().equals(StatusVarList.MAIN)) {
                this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
                rd = getServletContext().getRequestDispatcher(url);
            } else if (sessionVarlist.getCardCategory().equals(StatusVarList.SUPLIMENTORY)) {
                this.loadDefaultSuplimentoryApplicationStatus(sessionVarlist.getApplicationId(), request);
                rd = getServletContext().getRequestDispatcher(ur2);
            }else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_AGAINST_FD_CODE)){
                LoadApplicationStatus.loadDefaultCardagainstFDApplicationStatus(sessionVarlist.getApplicationId(), sessionVarlist, request,false,2);
                rd = getServletContext().getRequestDispatcher(urlCardagainstFD);            
            }else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
                LoadApplicationStatus.loadDefaultCorporateApplicationStatus(sessionVarlist.getApplicationId(), sessionVarlist, request, false,1);
                rd = getServletContext().getRequestDispatcher(urlCorporate);
            }else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_APP_CATEGORY_ESTABLISMENT_CODE)){
                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatus(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null,3);
                rd = getServletContext().getRequestDispatcher(urlEstablishment);
            }
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

            request.setAttribute("errorMsg", "Error in action");
            if (sessionVarlist.getCardCategory().equals(StatusVarList.MAIN)) {
                this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
                rd = getServletContext().getRequestDispatcher(url);
            } else if (sessionVarlist.getCardCategory().equals(StatusVarList.SUPLIMENTORY)) {
                this.loadDefaultSuplimentoryApplicationStatus(sessionVarlist.getApplicationId(), request);
                rd = getServletContext().getRequestDispatcher(ur2);
            }else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_AGAINST_FD_CODE)){
                LoadApplicationStatus.loadDefaultCardagainstFDApplicationStatus(sessionVarlist.getApplicationId(), sessionVarlist, request,false,2);
                rd = getServletContext().getRequestDispatcher(urlCardagainstFD);
            }else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
                LoadApplicationStatus.loadDefaultCorporateApplicationStatus(sessionVarlist.getApplicationId(), sessionVarlist, request, false,1);
                rd = getServletContext().getRequestDispatcher(urlCorporate);
            }else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_APP_CATEGORY_ESTABLISMENT_CODE)){
                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatus(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null,3);
                rd = getServletContext().getRequestDispatcher(urlEstablishment);
            }
            rd.forward(request, response);
        } finally {
            out.close();
        }
    }

    private void loadDefaultApplicationStatus(String appliactionId, HttpServletRequest request) throws Exception {

        this.getAllApplicationStatus(appliactionId);
        String loadTabIndex = "";
        String selectedTab = "";

        if (appStatusBean != null) {

            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_INITIATE) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_PROCESS)) {

                if (appStatusBean.getTableOne().equals("1")) {
                    this.getAllDetailsCustomer(appliactionId);
                    request.setAttribute("personalBean", customerPersonalBean);
                    sessionVarlist.setPersonalBean(customerPersonalBean);
                    selectedTab = "1";

                } else {
                    loadTabIndex = "0";
                }
                if (appStatusBean.getTableTwo().equals("1")) {
                    this.getAllEmpDetails(appliactionId);
                    request.setAttribute("employmentBean", employmentBean);
                    sessionVarlist.setEmploymentBean(employmentBean);
                    selectedTab = "2";
                } else {
                    loadTabIndex = loadTabIndex + "," + "1";
                }
                if (appStatusBean.getTableThree().equals("1")) {
                    this.getAllIncomeDetails(appliactionId);
                    this.getAllExpenseDetails(appliactionId);
                    request.setAttribute("expenseBean", expensesBean);

                    sessionVarlist.setExpensesBean(expensesBean);
                    sessionVarlist.setSessionIncomeList(incomeBeanList);
                    selectedTab = "3";

                } else {
                    loadTabIndex = loadTabIndex + "," + "2";
                }
                if (appStatusBean.getTableFore().equals("1")) {
                    this.getAllBankDetails(appliactionId);
                    sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);
                    selectedTab = "4";
                } else {

                    loadTabIndex = loadTabIndex + "," + "3";
                }
                if (appStatusBean.getTableFive().equals("1")) {
//                    this.getAllDocumentDetails(appliactionId);
//                    sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);
                    selectedTab = "5";

                } else {

                    loadTabIndex = loadTabIndex + "," + "4";
                }
                if (appStatusBean.getTableSix().equals("1")) {
                } else {

                    loadTabIndex = loadTabIndex + "," + "5";
                }

            }
//            this.getAllDocumentDetails(appliactionId);

        }

        request.setAttribute("loadTabIndex", loadTabIndex);
        request.setAttribute("selectedtab", selectedTab);

    }

    private void loadDefaultSuplimentoryApplicationStatus(String appliactionId, HttpServletRequest request) throws Exception {
        this.getAllApplicationStatus(appliactionId);
        String loadTabIndex = "";
        String selectedTab = "0";

        if (appStatusBean != null) {

            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_INITIATE) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_PROCESS)) {

                if (appStatusBean.getTableOne().equals("1")) {
                    this.getAllDetailsSuplimentoryCustomer(appliactionId);
                    request.setAttribute("personalBean", customerSuplimentoryPersonalBean);
                    sessionVarlist.setSuplimentoryPersonalBean(customerSuplimentoryPersonalBean);
                    selectedTab = "1";

                } else {
                    loadTabIndex = "0";
                }

                if (appStatusBean.getTableFive().equals("1")) {
                    //this.getAllDocumentDetails(appliactionId);
                    //sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);
                    selectedTab = "2";

                } else {

                    loadTabIndex = loadTabIndex + "," + "1";
                }
                if (appStatusBean.getTableSix().equals("1")) {
                } else {

                    loadTabIndex = loadTabIndex + "," + "2";
                }

            }
//            this.getAllDocumentDetails(appliactionId);

        }

        request.setAttribute("loadTabIndex", loadTabIndex);
        request.setAttribute("selectedtab", selectedTab);
    }
    
    
    private void loadDefaultCardagainstFDApplicationStatus(String appliactionId, HttpServletRequest request) throws Exception {
        this.getAllApplicationStatus(appliactionId);
        String loadTabIndex = "";
        String selectedTab = "0";

        if (appStatusBean != null) {
            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_INITIATE) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_PROCESS)) {

                if (appStatusBean.getTableOne().equals("1")) {
                    this.getAllDetailsCustomer(appliactionId);
                    request.setAttribute("personalBean", customerPersonalBean);
                    sessionVarlist.setPersonalBean(customerPersonalBean);
                    selectedTab = "1";
                } else {
                    loadTabIndex = "0";
                }
                if (appStatusBean.getTableFive().equals("1")) {
                    selectedTab = "2";

                } else {
                    loadTabIndex = loadTabIndex + "," + "1";
                }
                if (appStatusBean.getTableSix().equals("1")) {
                } else {
                    loadTabIndex = loadTabIndex + "," + "2";
                }
            }
        }
        request.setAttribute("loadTabIndex", loadTabIndex);
        request.setAttribute("selectedtab", selectedTab);
    }

    private void getAllApplicationStatus(String appliactionId) throws Exception {

        try {
            manager = new CaptureDataManager();
            appStatusBean = manager.getAllApplicationStatus(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllDetailsCustomer(String appliactionId) throws Exception {
        try {
            manager = new CaptureDataManager();
            customerPersonalBean = manager.getAllDetailsCustomer(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllDetailsSuplimentoryCustomer(String appliactionId) throws Exception {
        try {
            manager = new CaptureDataManager();
            customerSuplimentoryPersonalBean = manager.getAllDetailsSuplimentoryCustomer(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllEmpDetails(String appliactionId) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            employmentBean = checkingManager.getCardEmployementDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllIncomeDetails(String appliactionId) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            incomeBeanList = checkingManager.getCardIncomeDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllExpenseDetails(String appliactionId) throws Exception {

        try {
            checkingManager = new ApplicationCheckingManager();
            expensesBean = checkingManager.getExpensesDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllBankDetails(String appliactionId) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            bankDetailsBeanLst = checkingManager.getCardBankDetailsDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllDocumentDetails(String appliactionId) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            documentDetailsBeanLst = checkingManager.getCardDocumentDetails(appliactionId);

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
            Logger.getLogger(UploadDocumentServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UploadDocumentServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private String getGenerateFileName(String name, DocumentUploadBean uploadBean) {
        String fileName = "";
        String extention = "";

        String[] temp;
        try {
            temp = name.split("\\.");
            if (temp.length > 0) {
                extention = temp[temp.length - 1];
            }
        } catch (Exception ex) {
        }
        fileName = uploadBean.getApplicationId() + "_" + uploadBean.getVerificationCategory() + "_" + uploadBean.getDocumentType() + "." + extention;

        return fileName;
    }

    public String getOS_Type() throws Exception {

        String osType = "";
        String osName = "";
        osName = System.getProperty("os.name", "").toLowerCase();

        // For WINDOWS
        if (osName.contains("windows")) {
            osType = "WINDOWS";
        } else {
            // For LINUX
            if (osName.contains("linux")) {
                osType = "LINUX";
            } else {
                throw new Exception("Cannot identify the Operating System.");
            }
        }

        return osType;
    }

    private void createDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
