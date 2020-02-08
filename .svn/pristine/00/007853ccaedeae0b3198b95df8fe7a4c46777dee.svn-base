package com.epic.cms.camm.applicationproccessing.cooperate.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CurrencyMgtManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
//import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DebitPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.camm.applicationproccessing.capturedata.servlet.UploadDocumentServlet;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.AreaBeanCoCustomer;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.CustomerCorporateBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.DocumentUploadCorporateBean;
import com.epic.cms.camm.applicationproccessing.cooperate.businesslogic.CorporateCustomerManager;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.cpmm.cardembossing.businesslogic.CardEmbossingMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
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
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.IdCoCustomerBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.VerificationCategoryCorporateBean;

/**
 *
 * @author jeevan
 */
public class UploadCorporateDocumentServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SystemAuditBean systemAuditBean;
    String url = "/camm/capturedata/cooperatecutomer.jsp";
    private String searchUrl = "/camm/capturedata/searchcorporatecardapplication.jsp";
    private SessionVarList sessionVarlist;
    private List<CustomerCorporateBean> bankDetailsBeanLst = null;
    private List<VerificationCategoryCorporateBean> verificationCatCoList = null;
    private List<String> userTaskList;
    private CorporateCustomerManager manager = null;
    private CustomerCorporateBean customerCorporateBean = null;
    private List<AreaBeanCoCustomer> areaList = null;
    private List<CardTypeMgtBean> cardTypeList = null;
    private ApplicationAssignManager appAssignManager = null;
    private HashMap<String, String> identificationList = null;
    private HashMap<String, String> accountTypeList = null;
    private String applicationId = null;
    private String cardCategory = null;
    private CaptureDataManager captureDataManager = null;
    private List<CardProductBean> cardProductMgtList = null;
    private List<CurrencyBean> currencyDetailList = null;
    private List<String> nationalityList;
    private DebitPersonalBean debitPersonalBean = null;
    private CurrencyMgtManager currencyObj = null;
    private String cardCategoryCode = null;
    private Boolean checkOutStatus = false;
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
                        //Handle Uploaded files.
//                        System.out.println("Field Name = " + item.getFieldName());
//                        System.out.println("File Name = " + item.getName());
//                        System.out.println("Content type = " + item.getContentType());
//                        System.out.println("File Size = " + item.getSize() / 1024 + "kB");

                        DocumentUploadCorporateBean uploadBean = new DocumentUploadCorporateBean();
//
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

                                    if (uploadBean.getVerificationCategory().equals(sessionVarlist.getSessionDocumentCoList().get(x).getVerificationCategory()) && uploadBean.getDocumentType().equals(sessionVarlist.getSessionDocumentCoList().get(x).getDocumentType())) {
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

                                CorporateCustomerManager manager = new CorporateCustomerManager();
                                commonFilePathBean = manager.getFilePaths(osType);

                                if (osType.equals("WINDOWS")) {
                                    rootPath = commonFilePathBean.getScandocument() + "/" + applicationId + "/" + uploadBean.getVerificationCategory() + "/" + uploadBean.getDocumentType();

                                    this.createDirectory(rootPath);

                                } else if (osType.equals("LINUX")) {
                                    rootPath = commonFilePathBean.getScandocument() + "/" + applicationId + "/" + uploadBean.getVerificationCategory() + "/" + uploadBean.getDocumentType();
                                    this.createDirectory(rootPath);
                                }

                                File file = new File(rootPath, uploadBean.getFileName());
                                item.write(file);



//
                                if (sessionVarlist.getSessionDocumentList() != null) {
                                    sessionVarlist.getSessionDocumentCoList().add(uploadBean);
                                } else {
                                    List<DocumentUploadCorporateBean> list = new ArrayList<DocumentUploadCorporateBean>();

                                    list.add(uploadBean);
                                    sessionVarlist.setSessionDocumentCoList(list);

                                }



//                                if (sessionVarlist.getCardCategory().equals(StatusVarList.MAIN)) {
//                                    this.loadDefaultApplicationStatus(applicationId, request);
//                                    rd = getServletContext().getRequestDispatcher(url);
//                                } else if (sessionVarlist.getCardCategory().equals(StatusVarList.SUPLIMENTORY)) {
//                                    //this.loadDefaultSuplimentoryApplicationStatus(applicationId, request);
//                                    //rd = getServletContext().getRequestDispatcher(ur2);
//                                }
                                this.loadDefaultApplicationStatus(applicationId, request);
                                
                                request.setAttribute("successMsg", "File Uploaded Successfuly.");
                                
                                rd = getServletContext().getRequestDispatcher(searchUrl);
                                rd.forward(request, response);
                            } else {

                                if (sessionVarlist.getCardCategory().equals(StatusVarList.MAIN)) {
                                    this.loadDefaultApplicationStatus(applicationId, request);
                                    rd = getServletContext().getRequestDispatcher(url);
                                } else if (sessionVarlist.getCardCategory().equals(StatusVarList.SUPLIMENTORY)) {
                                    // this.loadDefaultSuplimentoryApplicationStatus(applicationId, request);
                                    //rd = getServletContext().getRequestDispatcher(ur2);
                                }
                                rd.forward(request, response);
                            }

                        } else {
                            request.setAttribute("category", uploadBean.getVerificationCategory());
                            request.setAttribute("type", uploadBean.getDocumentType());
                            this.loadDefaultApplicationStatus(sessionVarlist.getApplicationId(), request);
                            rd = getServletContext().getRequestDispatcher(url);
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
                // this.loadDefaultSuplimentoryApplicationStatus(sessionVarlist.getApplicationId(), request);
                // rd = getServletContext().getRequestDispatcher(ur2);
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
                // this.loadDefaultSuplimentoryApplicationStatus(sessionVarlist.getApplicationId(), request);
                //rd = getServletContext().getRequestDispatcher(ur2);
            }
            rd.forward(request, response);
        } finally {
            out.close();
        }
    }

    private void loadDefaultApplicationStatus(String appliactionId, HttpServletRequest request) throws Exception {

        appliactionId = request.getParameter("applicationid");
        cardCategory = request.getParameter("cardcategory");

        IdCoCustomerBean idBean = new IdCoCustomerBean();
        idBean = this.getIdentifyDetails(appliactionId);


        customerCorporateBean = new CustomerCorporateBean();
        customerCorporateBean.setIdentificationCode(idBean.getIdCode());
        customerCorporateBean.setIdentificationNumber(idBean.getIdNumber());
        request.setAttribute("personalBean", customerCorporateBean);

        this.getAllArealist();
        this.getAllIdentificationType();
        this.getAllNationality();
        this.getAllBankDetails();
        this.getVerificationCatCoList();

        sessionVarlist.setAreaListCoCustomer(areaList);
        sessionVarlist.setIdentityTypeListCoCustomer(identificationList);
        sessionVarlist.setNationalityCoCustomerList(nationalityList);
        sessionVarlist.setSessionBankDetailCoCustList(bankDetailsBeanLst);
        sessionVarlist.setVerificatioCatCoList(verificationCatCoList);



//                request.setAttribute("bankList", bankDetailsCoCustBeanLst);

        sessionVarlist.setApplicationId(appliactionId);
        sessionVarlist.setCardCategory(cardCategory);
        

        // request.setAttribute("personalBean", debitPersonalBean);

        request.setAttribute("selectedtab", "2");

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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String getGenerateFileName(String name, DocumentUploadCorporateBean uploadBean) {
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

    private void getAllArealist() throws Exception {
        try {

            manager = new CorporateCustomerManager();
            areaList = manager.getAllArealist();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllIdentificationType() throws Exception {
        try {
            manager = new CorporateCustomerManager();
            identificationList = manager.getAllIdentificationType();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllNationality() throws Exception {
        try {

            manager = new CorporateCustomerManager();
            nationalityList = manager.getAllNationality();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private IdCoCustomerBean getIdentifyDetails(String applicationId) throws Exception {
        IdCoCustomerBean idCustBean = new IdCoCustomerBean();
        try {

            manager = new CorporateCustomerManager();
            idCustBean = manager.getIdentifyDetails(applicationId);
        } catch (Exception ex) {
            throw ex;
        }
        return idCustBean;
    }

    private void getAllBankDetails() throws Exception {
        try {
            manager = new CorporateCustomerManager();
            bankDetailsBeanLst = manager.getCardBankDetailsDetails();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getVerificationCatCoList() throws SQLException, Exception {
        try {
            manager = new CorporateCustomerManager();
            verificationCatCoList = manager.getVerificationCatCoList();

        } catch (Exception ex) {
            throw ex;
        }
    }
}
