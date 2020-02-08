/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

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
import com.epic.cms.system.util.variable.StatusVarList;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
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
public class UploadUpdateSignatureServlet extends HttpServlet {

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
    private String url = "/camm/capturedata/applicationDataModify.jsp";
    private String urlSup="/camm/capturedata/modifySupplementaryInputCaptureData.jsp";
    private String urlCardagainstFD = "/camm/capturedata/modifyCardagainstFDInputCaptureData.jsp";
    private String urlEstablishment = "/camm/capturedata/modifyEstablishmentInputCaptureData.jsp";
    private String urlCorporate = "/camm/capturedata/modifyCorporateInputCaptureData.jsp";

    private ApplicationCheckingManager checkingManager;
    private CardApplicationStatusBean appStatusBean = null;
    private CustomerPersonalBean customerPersonalBean = null;
    private CustomerEmploymentBean employmentBean = null;
    private List<CardIncomeBean> incomeBeanList = null;
    private CardExpensesBean expensesBean = null;
    private List<CardBankDetailsBean> bankDetailsBeanLst = null;
    private List<DocumentUploadBean> documentDetailsBeanLst = null;
    private CommonFilePathBean commonFilePathBean = null;
    private SupplementaryApplicationBean customerSuplimentoryPersonalBean = null;

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

            if (sessionVarlist.getApplicationTypeCode() != null && sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_AGAINST_FD_CODE)) {
                url = urlCardagainstFD;
            } else if (sessionVarlist.getApplicationTypeCode() != null && sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_APP_CATEGORY_ESTABLISMENT_CODE)) {
                url = urlEstablishment;
            } else if (sessionVarlist.getApplicationTypeCode() != null && sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
                url = urlCorporate;
            }else if(sessionVarlist.getApplicationTypeCode() != null && sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)){
                url=urlSup;
            }

            sessionVarlist.setCMSSessionUser(sessionUser);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

            int fsize = 1024;
            String documentRoot = "DOCUMENT";
            String rootPath = "";
            String applicationId = sessionVarlist.getApplicationId();

            boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            if (isMultipart) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(1 * 1024 * 1024);
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(fsize * 1024);

                List items = upload.parseRequest(request);

                HashMap<String, FileItem> formMap = this.convertToMaps(items);

                Iterator itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();

                    if (item.isFormField()) {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                    } else {

                        String fileName = this.getGenerateFileName(item.getName(), applicationId);
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + item.getFieldName());
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + item.getContentType());

                        if (!(item.getSize() > fsize * 1024)) {

                            String osType = this.getOS_Type();
                            CardEmbossingMgtManager cardEmbossingManager = new CardEmbossingMgtManager();
                            commonFilePathBean = cardEmbossingManager.getFilePaths(osType);

                            if (osType.equals("WINDOWS")) {
                                rootPath = commonFilePathBean.getScandocument() + "/" + applicationId + "/" + "SIGNATURE";

                                this.createDirectory(rootPath);

                            } else if (osType.equals("LINUX")) {
                                rootPath = commonFilePathBean.getScandocument() + "/" + applicationId + "/" + "SIGNATURE";
                                this.createDirectory(rootPath);
                            }

                            File file = new File(rootPath, fileName);
                            item.write(file);
                            //encrypt
                            String key = "Mary has one cat";
                            File inputFile=new File(rootPath, fileName);
                            File encryptedFile = new File(rootPath, fileName);

                            try {
                                CryptoUtils.encrypt(key, inputFile, encryptedFile);
                            } catch (CryptoException ex) {
                                throw  ex;
                            }
                            //request.setAttribute("upload", "yes");
                            if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_AGAINST_FD_CODE)) {
                                LoadApplicationStatus.loadDefaultCardagainstFDApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, 3, false);

                            } else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
                                LoadApplicationStatus.loadDefaultCorporateApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, 2, false);

                            } else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_ESTABLISHMENT)) {
                                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null, 4, false);

                            } else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_MAIN)) {
                                this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
                                
                            }else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)){
                                this.loadDefaultSuplimentoryApplicationStatus(sessionVarlist.getApplicationId(), request);
                            }

                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        } else {
                            this.loadDefaultApplicationStatus(applicationId, request);
                            if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_AGAINST_FD_CODE)) {
                                LoadApplicationStatus.loadDefaultCardagainstFDApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, 3, false);

                            } else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
                                LoadApplicationStatus.loadDefaultCorporateApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, 2, false);

                            } else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_ESTABLISHMENT)) {
                                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null, 4, false);

                            } else if (sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_MAIN)) {
                                this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
                            }
                            
                            else if(sessionVarlist.getApplicationTypeCode().equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)){
                                this.loadDefaultSuplimentoryApplicationStatus(sessionVarlist.getApplicationId(), request);
                            }
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }

                    }
                }

            }

        } catch (SQLException ex) {
//            request.setAttribute("operationtype", "add");
//            errorMessage = OracleMessage.getMessege(ex.getMessage());
//            request.setAttribute("errorMsg", errorMessage);
//            rd = getServletContext().getRequestDispatcher(url);
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
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_USERROLE);
            //rd = request.getRequestDispatcher(url); 

        } finally {
            out.close();
        }
    }

    private void createDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void loadDefaultApplicationStatus(String appliactionId, HttpServletRequest request) throws Exception {

        this.getAllApplicationStatus(appliactionId);
        String selectedTab = "5";

        if (appStatusBean != null) {

            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_CHECKIN) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_ONHOLD)) {

                this.getAllDetailsCustomer(appliactionId);
                request.setAttribute("personalBean", customerPersonalBean);
                sessionVarlist.setPersonalBean(customerPersonalBean);

                this.getAllEmpDetails(appliactionId);
                request.setAttribute("employmentBean", employmentBean);
                sessionVarlist.setEmploymentBean(employmentBean);

                this.getAllIncomeDetails(appliactionId);
                this.getAllExpenseDetails(appliactionId);
                request.setAttribute("expenseBean", expensesBean);

                sessionVarlist.setExpensesBean(expensesBean);
                sessionVarlist.setSessionIncomeList(incomeBeanList);

                this.getAllBankDetails(appliactionId);
                sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);

                this.getAllDocumentDetails(appliactionId);
                sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);

                request.setAttribute("selectedtab", selectedTab);

            } else {
                request.setAttribute("errorMsg", "This application is not in modification status");

            }

        }
    }
    private void loadDefaultSuplimentoryApplicationStatus(String appliactionId, HttpServletRequest request) throws Exception {
        this.getAllApplicationStatus(appliactionId);
        String selectedTab = "2";

        if (appStatusBean != null) {

            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_CHECKIN) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_ONHOLD)) {

                this.getAllDetailsSuplimentoryCustomer(appliactionId);
                request.setAttribute("personalBean", customerSuplimentoryPersonalBean);
                sessionVarlist.setSuplimentoryPersonalBean(customerSuplimentoryPersonalBean);

                this.getAllDocumentDetails(appliactionId);
                sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);
                request.setAttribute("selectedtab", selectedTab);
            } else {
                request.setAttribute("errorMsg", "This application is not in modification status");
            }
        }
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
    //suplementary
    private void getAllDetailsSuplimentoryCustomer(String appliactionId) throws Exception {
        try {
            manager = new CaptureDataManager();
            customerSuplimentoryPersonalBean = manager.getAllDetailsSuplimentoryCustomer(appliactionId);

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

    private String getGenerateFileName(String name, String id) {
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
        fileName = id + "_SIGNATURE" + "." + extention;

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
        processRequest(request, response);
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
        processRequest(request, response);
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
}
