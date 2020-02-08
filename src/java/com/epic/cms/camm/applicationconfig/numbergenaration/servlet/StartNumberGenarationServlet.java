/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.numbergenaration.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardAccountBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardAccountCustomerBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardApplicationBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardCorporateCusDetailBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardCustomerBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardEstablishmentCustomerDetailsBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardFDCustomerDetailsBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardMainCustomerDetailsBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardNumberFormateBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardSuplimentoryCustomerBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardTempBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardTypeCustomerDetailsBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.NumberFormateFieldTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.NumberGenarationDataBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineAccountTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineCardTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineCustomerTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.businesslogic.NumberGenarationManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.logs.LogFileCreator;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.DataTypeVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Exception;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jpos.iso.ISOUtil;

/**
 *
 * @author janaka_h
 */
@WebServlet(name = "StartNumberGenarationServlet", urlPatterns = {"/StartNumberGenarationServlet"})
public class StartNumberGenarationServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private ApplicationAssignManager appAssignManager;
    private NumberGenarationManager manager;
    private CaptureDataManager captureDataManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private List<CardNumberFormateBean> numberFormateList;
    private List<NumberFormateFieldTableBean> numberFormateFieldList;
    private String errorMessage = null;
    private HashMap<String, String> priorityLevelList = null;
    //private SearchUserAssignAppBean searchBean = null;
    private List<NumberGenarationDataBean> searchList = null;
    private String maxCustomerId = null;
    CardBean cardBean = null;
    CardBean replacecardBean = null;
    CardCustomerBean cardCustomerBean = null;
    CardMainCustomerDetailsBean cardMainCusDetailBean = null;
    CardSuplimentoryCustomerBean cardSupCusDetailBean = null;
    CardEstablishmentCustomerDetailsBean cardEstablishmentCustomerDetailsBean = null;
    CardCorporateCusDetailBean cardCorporateCusDetailBean=null;
    CardFDCustomerDetailsBean cardFDCustomerDetailsBean = null;
    CardTypeCustomerDetailsBean cardTypeCustomerDetailsBean = null;
    
    
    CardAccountBean cardAccBean = null;
    OnlineCardTableBean onlineBean = null;
    OnlineCardTableBean onlineReplaceCard = null;
    OnlineAccountTableBean onlineAccountBean = null;
    OnlineCustomerTableBean onlineCustomerBean = null;
    CardTempBean cardTempBean = null;
    AccountTempBean accountTempBean = null;
    CustomerTempBean customerTempBean = null;
    CardAccountCustomerBean cardAccountCustomerBean=null;

    //private String url = "/camm/numbergenaration/numbergenarationhistory.jsp";
    private String url2 = "/LoadCardNumberGenarationServlet";

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
                String pageCode = PageVarList.NUMBERGEN;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    String idArray[] = null;
                    String idList = "";
                    idList = request.getParameter("hiddenarray");

                    if (!idList.equals("")) {
                        idArray = idList.split(",");

                    } else {

                        searchList = sessionVarlist.getNumberGenaSearchLst();
                        idArray = new String[searchList.size()];
                        for (int j = 0; j < searchList.size(); j++) {
                            idArray[j] = searchList.get(j).getApplicationId();
                        }
                    }
                    //System.out.println(idArray[0]+">>>>>>"+idArray[1]);

                    if (idArray != null) {
                        sessionVarlist.setIdArray(idArray);
                        int successCount = 0;
                        int failedCount = 0;
                        try {
                            for (int i = 0; i < idArray.length; i++) {
                                String accno = "";

                                accno = this.setNewAccountNumber();

                                ApplicationHistoryBean historyBean = new ApplicationHistoryBean();

                                historyBean.setApplicationId(idArray[i]);
                                historyBean.setApplicationLevel("NGEN");
                                historyBean.setLastUpdatedUser(sessionUser.getUserName());

                                boolean isprocess = this.startNumbergenarationProcess(idArray[i], accno);

                                if (isprocess == true) {
                                    successCount += 1;
                                    historyBean.setRemarks(idArray[i] + ": Application number genarated sucsess");
                                    historyBean.setStatus("HCOM");

                                } else {
                                    failedCount += 1;
                                    historyBean.setRemarks(idArray[i] + ": Application number genarated fail");
                                    historyBean.setStatus("HFAL");
                                }

                                appAssignManager = new ApplicationAssignManager();
                                appAssignManager.insertApplicationHistory(historyBean);

                            }
                        } catch (Exception ee) {
                        }
                        if(successCount==0){
                        request.setAttribute("errorMsg", "Number genaration failed,see the log files for more informations ");
                        
                        }else{
                         request.setAttribute("successMsg", "Successfully number genarated for " + successCount + " application and failed "+failedCount +" application");
                        
                        }

                      
                    } else {
                        request.setAttribute("errorMsg", "Select atleas one application before genarate");
                    }

                    //request.setAttribute("numberFormateLst", numberFormateList);

                    rd = request.getRequestDispatcher(url2);
                    this.getNumberFormatesByCardType(sessionVarlist.getNumberGenaSearchBean().getCardType());
                    request.setAttribute("numberFormateLst", numberFormateList);


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
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);


        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.ERROR_CREDIT_NUM_GEN);
            rd = request.getRequestDispatcher(url2);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private boolean startNumbergenarationProcess(String applicationId, String accno) throws Exception {

        boolean success = false;
        this.getMaxFromCardApplication();
        String customerId = this.setCustomerId(maxCustomerId);

        String customerTemCode = ""; //ok
        String accountTemCode = ""; //ok
        String cardTemCode = ""; //ok
        String staffastatus = ""; //ok
        String productionMode = ""; //ok
        String cashAdvancedRate = ""; //ok
        String cashLimit = ""; //ok

        String numberFormateAndSeq = "";
        String numberFormate = "";
        String sequence = "";
        String binNumber = "";
        String cno = "";

        CustomerPersonalBean mainBean = new CustomerPersonalBean();
        SupplementaryApplicationBean suplimentoryBean = new SupplementaryApplicationBean();
        EstablishmentDetailsBean establishmentDetailsBean = new EstablishmentDetailsBean();


        CardApplicationBean applicationBean = new CardApplicationBean();
        manager = new NumberGenarationManager();
        applicationBean = manager.getApplicationDetailsFromID(applicationId);   //get card application info

        customerTemCode = applicationBean.getCusTempalte();// get template details from CARDAPPLICATION TABLE
        accountTemCode = applicationBean.getAcctemplate();
        cardTemCode = applicationBean.getCardtemplate();
        binNumber = applicationBean.getBinprofile();

        customerTempBean = this.getCustomerTemplateDetails(customerTemCode);
        accountTempBean = this.getAccountTemplateDetails(accountTemCode);
        cardTempBean = this.getCardTemplateDetails(cardTemCode);

        staffastatus = applicationBean.getStaffStatus();
//      productionMode =applicationBean.get();
        cashAdvancedRate = cardTempBean.getCashAdvancedRate();
        cashLimit = this.calculateCreditLimit(applicationBean.getConfirmCreditLimit(), cashAdvancedRate);

        numberFormateAndSeq = this.getNumberFormateAndSequenceBinNumber(binNumber);
        try {
            String[] array = numberFormateAndSeq.split(",");
            numberFormate = array[0];
            sequence = array[1];

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }

        cno = this.genarateCardNumber(applicationId, numberFormate, binNumber, sequence);
        //Prevent insert duplicate card numbers. Specially In Random number generation.
        while (isCardExist(cno)){ 
            cno = this.genarateCardNumber(applicationId, numberFormate, binNumber, sequence);
        }
        
        applicationBean.setCardNumber(cno);
        
        //check whether the application coming from replace category(CARD REQUEST TABLE)
        String replaceCard = "";
        replaceCard = this.isReplaceAppication(applicationId);
        cardTypeCustomerDetailsBean = new CardTypeCustomerDetailsBean();
        if (applicationBean.getCardCategory().equals(StatusVarList.CARD_CATEGORY_MAIN)) {

            if (replaceCard.equals("")) {

                captureDataManager = new CaptureDataManager();
                mainBean = captureDataManager.getAllDetailsCustomer(applicationId);

                CardCustomerBean primarycardCusBean = manager.getCustomerCardDetailsById(mainBean.getIdentificationNumber());
                boolean cardCus = false;
                if (primarycardCusBean.getCustomerId() == null) {
                    //create new customer id
                    cardCus = true;
                } else {
                    //use old customer id
                    customerId = primarycardCusBean.getCustomerId();
                    cardCus = false;
                }
                
                this.setCardBean(customerId, cno, cno, accno, applicationBean, mainBean.getNameOncard(), cardTempBean, cashLimit,mainBean.getAddress1(),mainBean.getAddress2(),mainBean.getAddress3(),mainBean.getCity());
                this.setCardCustomerBean(customerId, applicationBean, mainBean.getNameOncard());
                this.setCardMainCustomerBean(customerId, mainBean, applicationBean.getConfirmCreditLimit());
                this.setCardAccountBean(customerId, accno, applicationBean, cashLimit);
                this.setOnlineCardBean(customerId, cno, accno, applicationBean, cardTempBean, cashLimit);
                this.setOnlineAccountBean(customerId, cno, accno, applicationBean, accountTempBean, cashLimit);
                this.setOnlineCustomerBean(customerId, cno, accno, applicationBean, customerTempBean, cashLimit);
                this.setCardAccountCustomerBean(accno ,cno,customerId, StatusVarList.CARD_ACCOUNT_CUSTOMER_IS_PRIMARY_CODE_YES);
                cardTypeCustomerDetailsBean.setCardMainCusDetailBean(cardMainCusDetailBean);
                success = manager.insertIntoCardTables(cardCustomerBean, cardTypeCustomerDetailsBean, cardAccBean, cardBean, onlineBean, onlineAccountBean, onlineCustomerBean, cardCus, binNumber, sequence, applicationId ,cardAccountCustomerBean);


            } else {
                this.getBackendCard(replaceCard);
                this.getOnlineCard(replaceCard);
            }



        }
        if (applicationBean.getCardCategory().equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)) {

            if (replaceCard.equals("")) {
                captureDataManager = new CaptureDataManager();
                suplimentoryBean = captureDataManager.getAllDetailsSuplimentoryCustomer(applicationId);
                String primaryId = suplimentoryBean.getPrimaryId();

                CardCustomerBean primarycardCusBean = manager.getCustomerCardDetailsById(primaryId);

                if (primarycardCusBean.getCustomerId() == null) {
                    success = false;
                } else {
                    //get primary accont no

                    accno = this.getPrimaryAccountNumberFromCardAccont(primarycardCusBean.getCustomerId());
                    CardCustomerBean supcardCusBean = manager.getCustomerCardDetailsById(suplimentoryBean.getIdentificationNumber());
                    boolean cardCus = false;
                    if (supcardCusBean.getCustomerId() == null) {
                        //genarate new customer id
                        cardCus = true;
                    } else {
                        //use old customer id
                        customerId = supcardCusBean.getCustomerId();
                        cardCus = false;
                    }
                    
                    String primaryCardNo = captureDataManager.getPrimaryCardNoFromApplication(primaryId);
                    suplimentoryBean.setPrimaryCardNumber(primaryCardNo);
                    
                    this.setCardBean(customerId, cno, suplimentoryBean.getPrimaryCardNumber(), accno, applicationBean, suplimentoryBean.getNameOncard(), cardTempBean, cashLimit,suplimentoryBean.getAddress1(),suplimentoryBean.getAddress2(),suplimentoryBean.getAddress3(),suplimentoryBean.getCity());
                    this.setCardCustomerBean(customerId, applicationBean, suplimentoryBean.getNameOncard());  // do only if customer not exsist
                    this.setCardSuplimentoryCustomerBean(customerId, suplimentoryBean, applicationBean);
                    this.setCardAccountBean(customerId, accno, applicationBean, cashLimit );
                    this.setOnlineCardBean(customerId, cno, accno, applicationBean, cardTempBean, cashLimit);
                    this.setOnlineAccountBean(customerId, cno, accno, applicationBean, accountTempBean, cashLimit);
                    this.setOnlineCustomerBean(customerId, cno, accno, applicationBean, customerTempBean, cashLimit);
                    this.setCardAccountCustomerBean(accno ,cno,customerId, StatusVarList.CARD_ACCOUNT_CUSTOMER_IS_PRIMARY_CODE_NO);
                    cardTypeCustomerDetailsBean.setCardSupCusDetailBean(cardSupCusDetailBean);
                    success = manager.insertIntoCardTables(cardCustomerBean, cardTypeCustomerDetailsBean, cardAccBean, cardBean, onlineBean, onlineAccountBean, onlineCustomerBean, cardCus, binNumber, sequence, applicationId,cardAccountCustomerBean);
                }


            } else {
                this.getBackendCard(replaceCard);
                this.getOnlineCard(replaceCard);

            }




        }
        
        
        if (applicationBean.getCardCategory().equals(StatusVarList.CARD_CATEGORY_ESTABLISHMENT)) {

            if (replaceCard.equals("")) {

                captureDataManager = new CaptureDataManager();
                establishmentDetailsBean = captureDataManager.getAllDetailsEstablishment(applicationId);
                
                CardCustomerBean primarycardCusBean = manager.getCustomerCardDetailsById(establishmentDetailsBean.getIdentificationNumber());
                boolean cardCus = false;
                if (primarycardCusBean.getCustomerId() == null) {
                    //create new customer id
                    cardCus = true;
                } else {
                    //use old customer id
                    customerId = primarycardCusBean.getCustomerId();
                    cardCus = false;
                }
                
                this.setCardBean(customerId, cno, cno, accno, applicationBean, "", cardTempBean, cashLimit,mainBean.getAddress1(),mainBean.getAddress2(),mainBean.getAddress3(),mainBean.getCity());
                this.setCardCustomerBean(customerId, applicationBean, "");
                this.setCardEstablishmentCustomerBean(customerId, establishmentDetailsBean, applicationBean.getConfirmCreditLimit());
                this.setCardAccountBean(customerId, accno, applicationBean, cashLimit);
                this.setOnlineCardBean(customerId, cno, accno, applicationBean, cardTempBean, cashLimit);
                this.setOnlineAccountBean(customerId, cno, accno, applicationBean, accountTempBean, cashLimit);
                this.setOnlineCustomerBean(customerId, cno, accno, applicationBean, customerTempBean, cashLimit);
                this.setCardAccountCustomerBean(accno ,cno,customerId, StatusVarList.CARD_ACCOUNT_CUSTOMER_IS_PRIMARY_CODE_YES);
                cardTypeCustomerDetailsBean.setCardEstablishmentCustomerDetailsBean(cardEstablishmentCustomerDetailsBean);
                success = manager.insertIntoCardTables(cardCustomerBean, cardTypeCustomerDetailsBean, cardAccBean, cardBean, onlineBean, onlineAccountBean, onlineCustomerBean, cardCus, binNumber, sequence, applicationId ,cardAccountCustomerBean);


            } else {
                this.getBackendCard(replaceCard);
                this.getOnlineCard(replaceCard);
            }
        }
        
        if (applicationBean.getCardCategory().equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
            
            if (replaceCard.equals("")) {

                captureDataManager = new CaptureDataManager();
                CustomerPersonalBean coporateBean = captureDataManager.getAllDetailsCustomer(applicationId);
                CardBean establishmentCardBean= manager.getEstablishmentCardBeanByBusinssRegNo(coporateBean.getBusinessRegNumber());
                String primaryCardNumber=establishmentCardBean.getCardNumber();

                    
                CardCustomerBean primarycardCusBean = manager.getCustomerCardDetailsById(coporateBean.getIdentificationNumber());
                
                boolean cardCus = false;
                if (primarycardCusBean.getCustomerId() == null) {
                    //create new customer id
                    cardCus = true;
                } else {
                    //use old customer id
                    customerId = primarycardCusBean.getCustomerId();
                    cardCus = false;
                }
                
                this.setCardBean(customerId, cno, primaryCardNumber, accno, applicationBean, coporateBean.getNameOncard(), cardTempBean, cashLimit,coporateBean.getAddress1(),coporateBean.getAddress2(),coporateBean.getAddress3(),coporateBean.getCity());
                this.setCardCustomerBean(customerId, applicationBean, coporateBean.getNameOncard());
                this.setCardCorporateCustomerBean(customerId, coporateBean, applicationBean.getConfirmCreditLimit());
                this.setCardAccountBean(customerId, accno, applicationBean, cashLimit);
                this.setOnlineCardBean(customerId, cno, accno, applicationBean, cardTempBean, cashLimit);
                this.setOnlineAccountBean(customerId, cno, accno, applicationBean, accountTempBean, cashLimit);
                this.setOnlineCustomerBean(customerId, cno, accno, applicationBean, customerTempBean, cashLimit);
                this.setCardAccountCustomerBean(accno ,cno,customerId, StatusVarList.CARD_ACCOUNT_CUSTOMER_IS_PRIMARY_CODE_YES);
                cardTypeCustomerDetailsBean.setCardCorporateCusDetailBean(cardCorporateCusDetailBean);
                success = manager.insertIntoCardTables(cardCustomerBean, cardTypeCustomerDetailsBean, cardAccBean, cardBean, onlineBean, onlineAccountBean, onlineCustomerBean, cardCus, binNumber, sequence, applicationId ,cardAccountCustomerBean);


            } else {
                this.getBackendCard(replaceCard);
                this.getOnlineCard(replaceCard);
            }            
            
            
        }

        
        if (applicationBean.getCardCategory().equals(StatusVarList.CARD_AGAINST_FD_CODE)) {
            
            if (replaceCard.equals("")) {

                captureDataManager = new CaptureDataManager();
                CustomerPersonalBean cardFDBean = captureDataManager.getAllDetailsCustomer(applicationId);

                CardCustomerBean primarycardCusBean = manager.getCustomerCardDetailsById(cardFDBean.getIdentificationNumber());
                boolean cardCus = false;
                if (primarycardCusBean.getCustomerId() == null) {
                    //create new customer id
                    cardCus = true;
                } else {
                    //use old customer id
                    customerId = primarycardCusBean.getCustomerId();
                    cardCus = false;
                }
                
                this.setCardBean(customerId, cno, cno, accno, applicationBean, cardFDBean.getNameOncard(), cardTempBean, cashLimit,cardFDBean.getAddress1(),cardFDBean.getAddress2(),cardFDBean.getAddress3(),cardFDBean.getCity());
                this.setCardCustomerBean(customerId, applicationBean, cardFDBean.getNameOncard());
                this.setCardFDCustomerBean(customerId, cardFDBean, applicationBean.getConfirmCreditLimit());
                this.setCardAccountBean(customerId, accno, applicationBean, cashLimit);
                this.setOnlineCardBean(customerId, cno, accno, applicationBean, cardTempBean, cashLimit);
                this.setOnlineAccountBean(customerId, cno, accno, applicationBean, accountTempBean, cashLimit);
                this.setOnlineCustomerBean(customerId, cno, accno, applicationBean, customerTempBean, cashLimit);
                this.setCardAccountCustomerBean(accno ,cno,customerId, StatusVarList.CARD_ACCOUNT_CUSTOMER_IS_PRIMARY_CODE_YES);
                cardTypeCustomerDetailsBean.setCardFDCustomerDetailsBean(cardFDCustomerDetailsBean);
                success = manager.insertIntoCardTables(cardCustomerBean, cardTypeCustomerDetailsBean, cardAccBean, cardBean, onlineBean, onlineAccountBean, onlineCustomerBean, cardCus, binNumber, sequence, applicationId ,cardAccountCustomerBean);


            } else {
                this.getBackendCard(replaceCard);
                this.getOnlineCard(replaceCard);
            }            
            
            
        }
        


        return success;
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

    private void getNumberFormatesByCardType(String cardType) throws Exception {
        try {
            manager = new NumberGenarationManager();
            numberFormateList = manager.getNumberFormatesByCardType(cardType);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private String genarateCardNumber(String applicationId, String numberFormate, String binNumber, String sequence) throws Exception {
        String cardNumber = "";
        String temp = "";

        try {
            String bin = binNumber; //ok
            String accountNumber = "";
            String branchCode = "";  //ok
            String sequenceNumber = ""; //ok
            String randomNumber = "";
            String cardProduct = "";  //ok
            String checkDigite = "";
            String cardNumberLenght = ""; //ok


            manager = new NumberGenarationManager();
            CardNumberFormateBean bean = new CardNumberFormateBean();
            CardApplicationBean applicationBean = new CardApplicationBean();

            bean = manager.getNumberFormateDetailsFromCode(numberFormate); //get naumber format deatils form NUMBERFORMAT TABLE by FORMATCODE
            applicationBean = manager.getApplicationDetailsFromID(applicationId);//get CARDaPPLICATION TABLE details from APPLICATIONID
            //Error handling for other nonsequence didit formats
            if(sequence!=null && !sequence.trim().equals("") && !sequence.trim().equals("null")){
                int seq = Integer.parseInt(sequence) + 1;
                sequenceNumber = this.getZeropadSeq(Integer.toString(seq), sequence.length());
            }
            cardNumberLenght = bean.getCardNumberLenght();

            cardProduct = applicationBean.getCardProductdisplayNo();
            branchCode = applicationBean.getBranchCode();



            numberFormateFieldList = manager.getNumberFormateFieldsFromCode(numberFormate);

            for (int c = 0; c < numberFormateFieldList.size(); c++) {

                String fieldCode = numberFormateFieldList.get(c).getFieldTypeCode();
                int from = numberFormateFieldList.get(c).getFromDigit();
                int to = numberFormateFieldList.get(c).getToDigit();

                if (fieldCode.equals("FBIN")) {
                    boolean isBinMatch = this.isBinMatch(bin, from, to);
                    if (isBinMatch == true) {
                        temp += bin;
                    } else {
                        throw new Exception();
                    }

                } else if (fieldCode.equals("FACC")) {
                    boolean isAccMatch = this.isAccMatch(accountNumber, from, to);
                    if (isAccMatch == true) {
                        temp += accountNumber;
                    } else {
                        throw new Exception();
                    }

                } else if (fieldCode.equals("FBRC")) {
                    boolean isBranchMatch = this.isBranchMatch(branchCode, from, to);
                    if (isBranchMatch == true) {
                        temp += branchCode;
                    } else {
                        throw new Exception();
                    }

                } else if (fieldCode.equals("FSEC")) {
                    boolean isSequMatch = this.isSequMatch(sequenceNumber, from, to);
                    if (isSequMatch == true) {
                        temp += sequenceNumber;
                    } else {
                        throw new Exception();
                    }

                } else if (fieldCode.equals("FRAN")) {
                    randomNumber = this.setRandomNumber(from, to);
                    temp += randomNumber;
//                   
                } else if (fieldCode.equals("FCPR")) {
                    boolean isProductMatch = this.isProductMatch(cardProduct, from, to);
                    if (isProductMatch == true) {
                        temp += cardProduct;
                    } else {
                        throw new Exception();
                    }

                } else if (fieldCode.equals("FCHD")) {
                    int checkDigit = this.setCheckDigit(temp);
                    //System.out.println("********************************------------->>>>>" + checkDigit);
                    temp += checkDigit;

                }

            }

            if (temp.length() == Integer.parseInt(cardNumberLenght)) {
                cardNumber = temp;
                // System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*********************************card no" + cardNumber);

            }




        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }

        return cardNumber;
    }

    private boolean isBinMatch(String bin, int from, int to) {
        boolean isBinMatch = false;
        try {
            int lenght = to - from + 1;
            if (bin.length() == lenght) {
                if (UserInputValidator.isNumeric(bin)) {
                    isBinMatch = true;
                }
            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }

        return isBinMatch;
    }

    private boolean isAccMatch(String accountNumber, int from, int to) {
        boolean isAccMatch = false;

        try {
            int lenght = to - from + 1;
            if (accountNumber.length() == lenght) {
                if (UserInputValidator.isNumeric(accountNumber)) {
                    isAccMatch = true;
                }
            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
        return isAccMatch;
    }

    private boolean isSequMatch(String sequenceNumber, int from, int to) {
        boolean isSequMatch = false;

        try {
            int lenght = to - from + 1;
            if (sequenceNumber.length() == lenght) {
                if (UserInputValidator.isNumeric(sequenceNumber)) {
                    isSequMatch = true;
                }
            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
        return isSequMatch;
    }

    private boolean isBranchMatch(String branchCode, int from, int to) {
        boolean isBranchMatch = false;

        try {
            int lenght = to - from + 1;
            if (branchCode.length() == lenght) {
                if (UserInputValidator.isNumeric(branchCode)) {
                    isBranchMatch = true;
                }
            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
        return isBranchMatch;
    }

    private boolean isProductMatch(String cardProduct, int from, int to) {
        boolean isProductMatch = false;

        try {
            int lenght = to - from + 1;
            if (cardProduct.length() == lenght) {
                if (UserInputValidator.isNumeric(cardProduct)) {
                    isProductMatch = true;
                }
            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
        return isProductMatch;
    }

    public int calculate(String cno, int callMethod) {
        //this method use for both calculate
        //check digit and validate card number.
        //if callMethod=0 then it is isValid method(this one not consider)
        //if callMethod=1 then it is setCheckDigit method(this one consider)
        int add = 0;
        int first = 0;
        int second = 0;
        int answaer = 0;
        char[] cnumberChar = new char[cno.length()];// read string one by one and put into this array.
        int[] cnumberInt = new int[cno.length()];//to store char value into int value
        int n = cno.length();
        cnumberChar = cno.toCharArray();
        for (int i = 0; i < n; i = i + 2) {
            if ((i + 1) == n && callMethod == 1) {
            } else {
                cnumberInt[i + 1] = Integer.parseInt(String.valueOf(cnumberChar[i + 1]));
            }
            int temp = Integer.parseInt(String.valueOf(cnumberChar[i])) * 2;
            if (temp >= 10) {
                String strtemp = String.valueOf(temp);
                first = Integer.parseInt(String.valueOf(strtemp.charAt(0)));
                second = Integer.parseInt(String.valueOf(strtemp.charAt(1)));
                add = first + second;
                cnumberInt[i] = add;
            } else {
                cnumberInt[i] = temp;
            }
        }
        for (int i = 0; i < n; i++) {
            answaer = answaer + cnumberInt[i];
        }
        //System.out.println(answaer);
        return answaer;
    }

    public int setCheckDigit(String cno) {
        String partOfCal = "";
        int checkDigit;
//        if (!isCorrectNumber(cno, 0)) {
//            System.out.println("Not a correct number");
//            //System.exit(0);
//        }
        partOfCal = String.valueOf(calculate(cno, 1));
        if (partOfCal.length() == 1) {
            checkDigit = (10 - (Integer.parseInt(partOfCal)));
        } else {
            int temp = Integer.parseInt(String.valueOf(partOfCal.charAt(1)));
            if (temp == 0) {
                checkDigit = 0;
            } else {
                checkDigit = 10 - temp;
            }
        }

        return checkDigit;
    }
    
    public String setRandomNumber(int from, int to) throws Exception {

        long random;
        int length = to - from + 1;
        long tLen;
        String retVal = "";
        
        try {
            
            //Random rn = new Random(lenght);
            //random = rn.nextInt();
            tLen = (long) Math.pow(10, length - 1) * 9;
            random = (long) (Math.random() * tLen) + (long) Math.pow(10, length - 1) * 1;
            retVal = random + "";
            
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }

        return retVal;
    }
    
    private String getZeropadSeq(String seq, int length) {
        String padLine = "";
        String referanceNo = "";

        if (seq.length() < length) {
            for (int x = 0; x < length - seq.length(); x++) {
                padLine += "0";
            }
            referanceNo = padLine + seq;
        } else if (seq.length() == length) {
            referanceNo = seq;
        }



        return referanceNo;

    }

    private void getMaxFromCardApplication() throws Exception {
        try {
            manager = new NumberGenarationManager();
            maxCustomerId = manager.getMaxCusIDFromCardCustomer();

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
    }

    public String setCustomerId(String maxId) throws Exception {

        String padLine = "";

        padLine = ISOUtil.zeropad((Integer.parseInt(maxId) + 1) + "", 10);

        System.out.println("padLine &&&&&&&&& "+padLine);
        return padLine;
    }

    private void setCardBean(String customerId, String cno, String mainCardNo, String accno, CardApplicationBean applicationBean, String name, CardTempBean cardTempBean, String cashLimit,String ad1,String ad2,String ad3,String ad4) {
        cardBean = new CardBean();
        try {


            cardBean.setAccountNumber(accno);
            cardBean.setAvailableBalence(applicationBean.getConfirmCreditLimit());
            cardBean.setCardNumber(cno);
            cardBean.setCardNumberPrintedOnCard(this.getFormatedCardNumber(cno));
            cardBean.setExprioryDate(this.getExpdate(cardTempBean.getExpiryPeriod()));
            cardBean.setIssueDate(this.getIssuedate());
            cardBean.setCardProduct(applicationBean.getConfirmCardProduct());
            cardBean.setCardCategory(applicationBean.getCardCategory());
            cardBean.setCardType(applicationBean.getCardType());
            cardBean.setCreditLimit(applicationBean.getConfirmCreditLimit());
            cardBean.setCashLimit(cashLimit);
            cardBean.setCashAvailableBalence(cashLimit);
            cardBean.setCustomerId(customerId);
            cardBean.setEmbossStatus(StatusVarList.NOSTATUS);
            cardBean.setEncodeStatus(StatusVarList.NOSTATUS);
            cardBean.setIdNumber(applicationBean.getIdNumber());
            cardBean.setIdType(applicationBean.getIdType());
            cardBean.setMainCardNumber(mainCardNo);
            cardBean.setNameOnCard(name);
            cardBean.setNoGenaratedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardBean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardBean.setPinStatus(StatusVarList.NOSTATUS);
            cardBean.setPinmailStatus(StatusVarList.NOSTATUS);
            cardBean.setCardStatus(StatusVarList.INACTIVE_STATUS);
            cardBean.setPriorityLevel(applicationBean.getPriorityLevelCode());
            cardBean.setTempotbAmount("0");
            cardBean.setLoyalityPoint("0");
            cardBean.setRedeemPoint("0");
            cardBean.setServiceCode(cardTempBean.getServiceCode());
            cardBean.setCurenncyCode(cardTempBean.getCurrencyNumCode());
            cardBean.setTxnProfileCode(cardTempBean.getTxnProfCode());
            cardBean.setApplicationId(applicationBean.getApplicationId());
            cardBean.setReissueTresholPeriod(cardTempBean.getReissueTresholPeriod());
            cardBean.setAddress1(ad1);
            cardBean.setAddress2(ad2);
            cardBean.setAddress3(ad3);
            cardBean.setAddress4(ad4);
            cardBean.setProductionMode(applicationBean.getProductionMode());
            cardBean.setCardDomain(applicationBean.getCardDomain());
            cardBean.setCardKey(applicationBean.getCardKey());
            cardBean.setRiskProfile(cardTempBean.getRiskProfileCode());
            
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }


    }

    private void setCardCustomerBean(String customerId, CardApplicationBean applicationBean, String name) {
        cardCustomerBean = new CardCustomerBean();
        try {
            cardCustomerBean.setCustomerId(customerId);
            cardCustomerBean.setCustomerName(name);
            cardCustomerBean.setCustomerStatus(StatusVarList.CARD_INIT);
            cardCustomerBean.setCustomerType(applicationBean.getCardCategory());
            cardCustomerBean.setIdNumber(applicationBean.getIdNumber());
            cardCustomerBean.setIdType(applicationBean.getIdType());
            cardCustomerBean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardCustomerBean.setRiskProfileCode(customerTempBean.getCusRiskProfile());
            cardCustomerBean.setTxnProfileCode(customerTempBean.getTxnProfCode());
            cardCustomerBean.setCurrencyCode(customerTempBean.getCurrencyCode());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
    }

    private void setCardEstablishmentCustomerBean(String customerId, EstablishmentDetailsBean establishmentDetailsBean, String cLimit) {
        
        cardEstablishmentCustomerDetailsBean = new CardEstablishmentCustomerDetailsBean(establishmentDetailsBean);

        try {
            cardEstablishmentCustomerDetailsBean.setCustomerId(customerId);            
            cardEstablishmentCustomerDetailsBean.setCreditLimit(cLimit);
            cardEstablishmentCustomerDetailsBean.setLastUpdateUser(sessionVarlist.getCMSSessionUser().getUserName());
            
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
    }

    
    
    private void setCardMainCustomerBean(String customerId, CustomerPersonalBean mainBean, String cLimit) {
        cardMainCusDetailBean = new CardMainCustomerDetailsBean();
        try {

            cardMainCusDetailBean.setCustomerId(customerId);
            cardMainCusDetailBean.setBillingAddress1(mainBean.getBillAddress1());
            cardMainCusDetailBean.setBillingAddress2(mainBean.getBillAddress2());
            cardMainCusDetailBean.setBillingAddress3(mainBean.getBillAddress3());
            cardMainCusDetailBean.setCity(mainBean.getCity());
            //cardMainCusDetailBean.setCompanyName("");
            cardMainCusDetailBean.setContactNumber(mainBean.getMobileNumber());
            cardMainCusDetailBean.setCreditLimit(cLimit);
            //cardMainCusDetailBean.setCreditScore("");
            cardMainCusDetailBean.setDateOfBirth(mainBean.getBirthday());
            //cardMainCusDetailBean.setDesignation("");////////////////////////////////////////not fill
            cardMainCusDetailBean.setEmail(mainBean.getEmail());
            //cardMainCusDetailBean.setFirstName(mainBean.getFirstName());
            cardMainCusDetailBean.setCustomerName(mainBean.getNameOncard());
            cardMainCusDetailBean.setGender(mainBean.getGender());
            cardMainCusDetailBean.setHomePhone(mainBean.getHomeTelNumber());
            cardMainCusDetailBean.setInitials(mainBean.getInitials());
            //cardMainCusDetailBean.setLastName(mainBean.getLastName());
            cardMainCusDetailBean.setMaritalStatus(mainBean.getMaritalStatus());
            //cardMainCusDetailBean.setMiddleName(mainBean.getMiddleName());
            cardMainCusDetailBean.setMobile(mainBean.getMobileNumber());
            cardMainCusDetailBean.setNationality(mainBean.getNationality());
            //cardMainCusDetailBean.setOccupation("");/////////////////////////////////////////not fill
            cardMainCusDetailBean.setOfficePhone(mainBean.getOfficeTelNumber());
            cardMainCusDetailBean.setPermenentAddress1(mainBean.getPermentAddress1());
            cardMainCusDetailBean.setPermenentAddress2(mainBean.getPermentAddress2());
            cardMainCusDetailBean.setPermenentAddress3(mainBean.getPermentAddress3());
            cardMainCusDetailBean.setLastupdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
    }
     
    
    private void setCardFDCustomerBean(String customerId, CustomerPersonalBean mainBean, String cLimit) {
        cardFDCustomerDetailsBean = new CardFDCustomerDetailsBean();
        try {

            cardFDCustomerDetailsBean.setCustomerId(customerId);
            cardFDCustomerDetailsBean.setBillingAddress1(mainBean.getBillAddress1());
            cardFDCustomerDetailsBean.setBillingAddress2(mainBean.getBillAddress2());
            cardFDCustomerDetailsBean.setBillingAddress3(mainBean.getBillAddress3());
            cardFDCustomerDetailsBean.setCity(mainBean.getCity());
            //cardFDCustomerDetailsBean.setCompanyName("");
            cardFDCustomerDetailsBean.setContactNumber(mainBean.getMobileNumber());
            cardFDCustomerDetailsBean.setCreditLimit(cLimit);
            //cardFDCustomerDetailsBean.setCreditScore("");
            cardFDCustomerDetailsBean.setDateOfBirth(mainBean.getBirthday());
            //cardFDCustomerDetailsBean.setDesignation("");////////////////////////////////////////not fill
            cardFDCustomerDetailsBean.setEmail(mainBean.getEmail());
            //cardFDCustomerDetailsBean.setFirstName(mainBean.getFirstName());
            cardFDCustomerDetailsBean.setCustomerName(mainBean.getNameOncard());
            cardFDCustomerDetailsBean.setGender(mainBean.getGender());
            cardFDCustomerDetailsBean.setHomePhone(mainBean.getHomeTelNumber());
            cardFDCustomerDetailsBean.setInitials(mainBean.getInitials());
            //cardFDCustomerDetailsBean.setLastName(mainBean.getLastName());
            cardFDCustomerDetailsBean.setMaritalStatus(mainBean.getMaritalStatus());
            //cardFDCustomerDetailsBean.setMiddleName(mainBean.getMiddleName());
            cardFDCustomerDetailsBean.setMobile(mainBean.getMobileNumber());
            cardFDCustomerDetailsBean.setNationality(mainBean.getNationality());
            //cardFDCustomerDetailsBean.setOccupation("");/////////////////////////////////////////not fill
            cardFDCustomerDetailsBean.setOfficePhone(mainBean.getOfficeTelNumber());
            cardFDCustomerDetailsBean.setPermenentAddress1(mainBean.getPermentAddress1());
            cardFDCustomerDetailsBean.setPermenentAddress2(mainBean.getPermentAddress2());
            cardFDCustomerDetailsBean.setPermenentAddress3(mainBean.getPermentAddress3());
            cardFDCustomerDetailsBean.setLastupdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
    }
    
    private void setCardCorporateCustomerBean(String customerId, CustomerPersonalBean copBean, String cLimit) {
        cardCorporateCusDetailBean = new CardCorporateCusDetailBean();
        try {

            cardCorporateCusDetailBean.setCustomerId(customerId);
            cardCorporateCusDetailBean.setBillingAddress1(copBean.getBillAddress1());
            cardCorporateCusDetailBean.setBillingAddress2(copBean.getBillAddress2());
            cardCorporateCusDetailBean.setBillingAddress3(copBean.getBillAddress3());
            cardCorporateCusDetailBean.setCity(copBean.getCity());
            cardCorporateCusDetailBean.setCompanyName("");
            cardCorporateCusDetailBean.setContactNumber(copBean.getMobileNumber());
            cardCorporateCusDetailBean.setCreditLimit(cLimit);
            cardCorporateCusDetailBean.setBusinessRegumber(copBean.getBusinessRegNumber());
            cardCorporateCusDetailBean.setDateOfBirth(copBean.getBirthday());
            //cardCorporateCusDetailBean.setDesignation("");////////////////////////////////////////not fill
            cardCorporateCusDetailBean.setEmail(copBean.getEmail());
            //cardCorporateCusDetailBean.setFirstName(copBean.getFirstName());
            cardCorporateCusDetailBean.setCustomerName(copBean.getNameOncard());
            cardCorporateCusDetailBean.setGender(copBean.getGender());
            cardCorporateCusDetailBean.setHomePhone(copBean.getHomeTelNumber());
            cardCorporateCusDetailBean.setInitials(copBean.getInitials());
            //cardCorporateCusDetailBean.setLastName(copBean.getLastName());
            cardCorporateCusDetailBean.setMaritalStatus(copBean.getMaritalStatus());
            //cardCorporateCusDetailBean.setMiddleName(copBean.getMiddleName());
            cardCorporateCusDetailBean.setMobile(copBean.getMobileNumber());
            cardCorporateCusDetailBean.setNationality(copBean.getNationality());
            //cardCorporateCusDetailBean.setOccupation("");/////////////////////////////////////////not fill
            cardCorporateCusDetailBean.setOfficePhone(copBean.getOfficeTelNumber());
            cardCorporateCusDetailBean.setPermenentAddress1(copBean.getPermentAddress1());
            cardCorporateCusDetailBean.setPermenentAddress2(copBean.getPermentAddress2());
            cardCorporateCusDetailBean.setPermenentAddress3(copBean.getPermentAddress3());
            cardCorporateCusDetailBean.setLastupdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
    }
    
    
    private void setCardAccountBean(String customerId, String accno, CardApplicationBean applicationBean, String cashLimit) {
        cardAccBean = new CardAccountBean();
        try {

            cardAccBean.setAccountNumber(accno);
            cardAccBean.setCardNumber(applicationBean.getCardNumber());
            cardAccBean.setBillingId(accountTempBean.getBillCycle());
            cardAccBean.setRiskProfileCode(accountTempBean.getAccRskProf());
            cardAccBean.setCreditLimit(applicationBean.getConfirmCreditLimit());
            cardAccBean.setCashAdvanceLimit(cashLimit);
            cardAccBean.setCustomerId(customerId);
            cardAccBean.setAccountOwner(DataTypeVarList.ACC_OWNER_BANK);
            cardAccBean.setPrimaryStatus(StatusVarList.YESSTATUS);
            cardAccBean.setCardDomain(DataTypeVarList.CARD_DOMAIN_CREDIT);
            cardAccBean.setCurrencyCode(accountTempBean.getCurrencyCode());
            cardAccBean.setTxnProfileCode(accountTempBean.getTxnProfCode());
            cardAccBean.setLastupdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardAccBean.setStatus(StatusVarList.ACTIVE_STATUS);
            
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
    }

    private void setCardSuplimentoryCustomerBean(String customerId, SupplementaryApplicationBean suplimentoryBean, CardApplicationBean applicationBean) {

        cardSupCusDetailBean = new CardSuplimentoryCustomerBean();
        try {

            cardSupCusDetailBean.setCustomerId(customerId);
            cardSupCusDetailBean.setFirstName(suplimentoryBean.getFirstName());
            cardSupCusDetailBean.setLastName(suplimentoryBean.getLastName());
            cardSupCusDetailBean.setMiddleName(suplimentoryBean.getMiddleName());
            cardSupCusDetailBean.setBirthday(suplimentoryBean.getBirthday());
            cardSupCusDetailBean.setGender(suplimentoryBean.getGender());
            cardSupCusDetailBean.setNic(suplimentoryBean.getNic());
            cardSupCusDetailBean.setPassportNumber(suplimentoryBean.getPassportNumber());
            cardSupCusDetailBean.setNationality(suplimentoryBean.getNationality());
            cardSupCusDetailBean.setRelationShip(suplimentoryBean.getBirthday());
            cardSupCusDetailBean.setNameOncard(suplimentoryBean.getNameOncard());
            cardSupCusDetailBean.setAddress1(suplimentoryBean.getAddress1());
            cardSupCusDetailBean.setAddress2(suplimentoryBean.getAddress2());
            cardSupCusDetailBean.setAddress3(suplimentoryBean.getAddress3());
            cardSupCusDetailBean.setCity(suplimentoryBean.getCity());
            cardSupCusDetailBean.setHomeTelNumber(suplimentoryBean.getHomeTelNumber());
            cardSupCusDetailBean.setMobileNumber(suplimentoryBean.getMobileNumber());
            cardSupCusDetailBean.setOccupation(suplimentoryBean.getOccupation());
            cardSupCusDetailBean.setEmployementType(suplimentoryBean.getEmployementType());
            cardSupCusDetailBean.setCardType(applicationBean.getCardType());
            cardSupCusDetailBean.setCardProduct(applicationBean.getConfirmCardProduct());
            cardSupCusDetailBean.setCreditLimit(applicationBean.getConfirmCreditLimit());
            cardSupCusDetailBean.setStatementStatus(suplimentoryBean.getStatementStatus());
            cardSupCusDetailBean.setBillingAdress1(suplimentoryBean.getBillingAdress1());
            cardSupCusDetailBean.setBillingAdress2(suplimentoryBean.getBillingAdress2());
            cardSupCusDetailBean.setBillingAdress3(suplimentoryBean.getBillingAdress3());
            cardSupCusDetailBean.setBillingCity(suplimentoryBean.getBillingCity());
            cardSupCusDetailBean.setLastUpdateUser(sessionVarlist.getCMSSessionUser().getUserName());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
    }

    private void setOnlineCardBean(String customerid, String cno, String accno, CardApplicationBean applicationBean, CardTempBean cardTempBean, String cashLimit) {
        try {
            onlineBean = new OnlineCardTableBean();

            onlineBean.setCardNumber(cno);
            onlineBean.setAccountNumber(accno);
            onlineBean.setCustomerid(customerid);
            onlineBean.setEffectdate("");
            onlineBean.setExpiryDate(this.getExpdate(cardTempBean.getExpiryPeriod()));//get from CARD template
            onlineBean.setRiskProfileCode(cardTempBean.getRiskProfileCode());//get from CARD template
            onlineBean.setCurrencyCode(this.getZeroPadvalue(cardTempBean.getCurrencyNumCode()));//get from CARD template and zero pad
            onlineBean.setTxnCount("0");
            onlineBean.setPinCount("0");
            onlineBean.setCashTxnCount("0");
            onlineBean.setTotalTxnAmount("0");
            onlineBean.setTotalCashTxnAmount("0");
            onlineBean.setTempCashAmount("0");
            onlineBean.setTempCreditAmount("0");
            onlineBean.setTempCashLimitNcr("0");
            onlineBean.setTempCreditLimitNcr("0");
            onlineBean.setCreditLimit(applicationBean.getConfirmCreditLimit());
            onlineBean.setCashlimit(cashLimit);// catculate value
            onlineBean.setOtbCash(cashLimit);// catculate value
            onlineBean.setOtbCredit(applicationBean.getConfirmCreditLimit());
//            onlineBean.setPayMode("1");
            onlineBean.setEod("1");
            onlineBean.setTliStatus("2");
            onlineBean.setStatus("1");
            onlineBean.setLastUpdatedUser(sessionUser.getUserName());
            onlineBean.setServiceCode(cardTempBean.getServiceCode());
            onlineBean.setEmvStatus("1");
            onlineBean.setCardBin(applicationBean.getBinprofile());
            onlineBean.setCardDomain("1");
            onlineBean.setCardKey(applicationBean.getCardKey());
            
//
//            if (applicationBean.getCardType().equals("VISA")) {
//                onlineBean.setCardType("1");
//            }
//            if (applicationBean.getCardType().equals("MAST")) {
//                onlineBean.setCardType("2");
//            }
//            if (applicationBean.getCardType().equals("AMEX")) {
//                onlineBean.setCardType("3");
//            }
//            if (applicationBean.getCardType().equals("PLUS")) {
//                onlineBean.setCardType("4");
//            }
//            if (applicationBean.getCardType().equals("MSTR")) {
//                onlineBean.setCardType("5");
//            }
//            if (applicationBean.getCardType().equals("CARG")) {
//                onlineBean.setCardType("6");
//            }
//            if (applicationBean.getCardType().equals("CIRR")) {
//                onlineBean.setCardType("7");
//            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
    }

    private void setOnlineAccountBean(String customerid, String cno, String accno, CardApplicationBean applicationBean, AccountTempBean accountTempBean, String cashLimit) {
        try {
            onlineAccountBean = new OnlineAccountTableBean();

            onlineAccountBean.setCurrencyCode(this.getZeroPadvalue(accountTempBean.getCurrencyCode()));//get from account template and zeero pad
            onlineAccountBean.setAccountNumber(accno);
            onlineAccountBean.setCustomerid(customerid);
            onlineAccountBean.setRiskProfileCode(accountTempBean.getAccRskProf());//get from account template
            onlineAccountBean.setTxnCount("0");
            onlineAccountBean.setCashTxnCount("0");
            onlineAccountBean.setCreditLimit(applicationBean.getConfirmCreditLimit());
            onlineAccountBean.setCashLimit(cashLimit);// catculate value
            onlineAccountBean.setTotalTxnAmount("0");
            onlineAccountBean.setTotalCashTxnAmount("0");// catculate value
            onlineAccountBean.setOtbCash(cashLimit);// catculate value
            onlineAccountBean.setOtbCredit(applicationBean.getConfirmCreditLimit());
            onlineAccountBean.setStatus("1");
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
    }

    private void setOnlineCustomerBean(String customerid, String cno, String accno, CardApplicationBean applicationBean, CustomerTempBean customerTempBean, String cashLimit) {
        try {
            onlineCustomerBean = new OnlineCustomerTableBean();

            onlineCustomerBean.setCurrencyCode(this.getZeroPadvalue(customerTempBean.getCurrencyCode()));//get from customer template and zero pad
            onlineCustomerBean.setCustomerid(customerid);
            onlineCustomerBean.setRiskProfileCode(customerTempBean.getCusRiskProfile());//get from customer template
            onlineCustomerBean.setTxnCount("0");
            onlineCustomerBean.setCashTxnCount("0");
            onlineCustomerBean.setCreditLimit(applicationBean.getConfirmCreditLimit());
            onlineCustomerBean.setCashLimit(cashLimit);// catculate value
            onlineCustomerBean.setTotalTxnAmount("0");
            onlineCustomerBean.setTotalCashTxnAmount("0");
            onlineCustomerBean.setOtbCash(cashLimit);// catculate value
            onlineCustomerBean.setOtbCredit(applicationBean.getConfirmCreditLimit());
            onlineCustomerBean.setStatus("1");
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
    }

    private String setNewAccountNumber() throws Exception {
        String newAccontno = "";
        try {
            manager = new NumberGenarationManager();
            String accno = manager.getMaxAccnoFromCardAccount();


            if (accno.length() < 12) {
                newAccontno = ISOUtil.zeropad((Integer.parseInt(accno) + 1) + "", 12);
            } else {
                newAccontno = String.valueOf((Integer.parseInt(accno) + 1));
            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
        return newAccontno;
    }

    private String getPrimaryAccountNumberFromCardAccont(String customerId) throws Exception {
        String PAccontno = "";
        try {
            manager = new NumberGenarationManager();
            PAccontno = manager.getPrimaryAccountNumberFromCardAccont(customerId);




        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
        return PAccontno;
    }

    private CustomerTempBean getCustomerTemplateDetails(String customerTemCode) throws Exception {
        try {
            manager = new NumberGenarationManager();
            customerTempBean = manager.getCustomerTemplateDetails(customerTemCode);

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
        return customerTempBean;
    }

    private AccountTempBean getAccountTemplateDetails(String accountTemCode) throws Exception {
        try {
            manager = new NumberGenarationManager();
            accountTempBean = manager.getAccountTemplateDetails(accountTemCode);

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
        return accountTempBean;
    }

    private CardTempBean getCardTemplateDetails(String cardTemCode) throws Exception {
        try {
            manager = new NumberGenarationManager();
            cardTempBean = manager.getCardTemplateDetails(cardTemCode);

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
        return cardTempBean;
    }

    private String calculateCreditLimit(String confirmCreditLimit, String cashAdvancedRate) throws Exception {
        String value = "";
        // System.out.println("confirmCreditLimit>>>>>>>>>>>>>>>>>>>>>>>>>>" + confirmCreditLimit);
        try {

            BigDecimal credit = new BigDecimal(confirmCreditLimit);
            BigDecimal rate = new BigDecimal(cashAdvancedRate);
            rate = rate.divide(new BigDecimal("100"));
            BigDecimal total = credit.multiply(rate);

            value = total.toString();


        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
        return value;
    }

    private String getZeroPadvalue(String currencyNumCode) throws Exception {
        String value = "";
        try {
            if (currencyNumCode.length() == 3) {
                value = currencyNumCode;
            } else if (currencyNumCode.length() == 2) {
                value = "0" + currencyNumCode;
            } else if (currencyNumCode.length() == 1) {
                value = "00" + currencyNumCode;
            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
        return value;
    }

    private String getNumberFormateAndSequenceBinNumber(String binNumber) throws Exception {
        String value = "";
        try {
            manager = new NumberGenarationManager();
            value = manager.getNumberFormateBinNumber(binNumber);
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
        return value;
    }

    private String getExpdate(String expiryPeriod) throws Exception {

        String exp = "";
        try {
//            Calendar currentDate = Calendar.getInstance();
//            SimpleDateFormat formatter =
//                    new SimpleDateFormat("yy/MM ");
//            sysdate = formatter.format(currentDate.getTime());
//            System.out.println("Now the date is :=>  " + sysdate);

            Calendar date = Calendar.getInstance();
            date.setTime(new Date());
            Format f = new SimpleDateFormat("yyMM");

            date.add(Calendar.YEAR, Integer.parseInt(expiryPeriod));
            exp = f.format(date.getTime());

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
        return exp;
    }

    private String getIssuedate() throws Exception {
        String issue = "";
        try {
            Calendar date = Calendar.getInstance();
            date.setTime(new Date());
            Format f = new SimpleDateFormat("yyMM");
            issue = f.format(date.getTime());

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }

        return issue;
    }

    private String getFormatedCardNumber(String cno) throws Exception {
        String number = "";
        try {
            if (cno.length() == 16) {
                number = cno.substring(0, 4).concat(" ").concat(cno.substring(4, 8).concat(" ").concat(cno.substring(8, 12)).concat(" ").concat(cno.substring(12, 16)));
            } else if (cno.length() == 19) {
                number = cno.substring(0, 4).concat(" ").concat(cno.substring(4, 8).concat(" ").concat(cno.substring(8, 12)).concat(" ").concat(cno.substring(12, 16).concat(" ").concat(cno.substring(16, 19))));
            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
        return number;
    }

    private String isReplaceAppication(String applicationId) throws Exception {
        try {
            manager = new NumberGenarationManager();
            return manager.isReplaceAppication(applicationId);
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
    }

    private void getBackendCard(String replaceCard) throws Exception {
         try {
            manager = new NumberGenarationManager();
            replacecardBean= manager.getBackendCard(replaceCard);
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
    }

    private void getOnlineCard(String replaceCard) throws Exception {
        try {
            manager = new NumberGenarationManager();
            onlineReplaceCard= manager.getOnlineCard(replaceCard);
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
    }

    private void setCardAccountCustomerBean(String accno, String cno, String customerId, String isPrimary) {
        cardAccountCustomerBean = new CardAccountCustomerBean();
        cardAccountCustomerBean.setAccountNumber(accno);
        cardAccountCustomerBean.setCardNumber(cno);
        cardAccountCustomerBean.setCustomerId(customerId);
        cardAccountCustomerBean.setPrimaryStatus(isPrimary);   
    }

    private boolean isCardExist(String cno) throws Exception {
        
        try {
            manager = new NumberGenarationManager();
            return manager.isCardExist(cno);
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
    }
}
