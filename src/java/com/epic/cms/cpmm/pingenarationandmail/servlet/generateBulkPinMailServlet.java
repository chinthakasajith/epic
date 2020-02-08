/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.pingenarationandmail.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardMainCustomerDetailsBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardSuplimentoryCustomerBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingCardDetailsBean;
import com.epic.cms.cpmm.pingenarationandmail.been.CardKeyBean;
import com.epic.cms.cpmm.pingenarationandmail.busneslogic.PinGenerationAndmailManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.switchcontrol.keymgt.bean.ChanelKeysBean;
import com.epic.cms.switchcontrol.keymgt.businesslogic.KeyManagementManager;
import com.epic.cms.system.util.logs.LogFileCreator;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.socket.ConnectionToSecurityModule;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mahesh_m
 */
public class generateBulkPinMailServlet extends HttpServlet {

    private RequestDispatcher rd;
    private PinGenerationAndmailManager pinGenerationManager = null;
    private String offset = null;
    private String ANB = null;
    private String ppk_spec = null;
    private CardEmbossingCardDetailsBean cardbean = null;
    private String PVVK_Spec = null;
    private ConnectionToSecurityModule connectionToSM = null;
    private String responseFromSM = null;
    private String ePPKPin = null;
    private String PVV = null;
    private String TSP12 = null;
    private String VAL_Data = null;
    private String cardNumber = null;
    private String CVV = null;
    private String PAN = null;
    private String NAME = null;
    private String NAME_LEN = null;
    private String ADD1 = null;
    private String ADD1_LEN = null;
    private String ADD2 = null;
    private String ADD2_LEN = null;
//    private ChanelKeysBean keyDetails;
    private String cardCategory;
    private CardMainCustomerDetailsBean cardMainCusBean;
    private CardSuplimentoryCustomerBean cardSuppCusBean;
    private String customerId;
    private KeyManagementManager keyMgtmanager;
    HttpSession sessionObj;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private ApplicationHistoryBean historybean = null;
    private String batchId = null;
    private int succesPinGenCount = 0;
    private List<String> carNumbers = null;
    private BulkCardRequestBean bulkCardReq = null;
    private String url = "/cpmm/pingenarateandmail/bulkpinmail.jsp";
    private SystemAuditBean systemAuditBean = null;
    private String cardkeyId = null;
    private CardKeyBean cardkeyBean = null;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            sessionObj = request.getSession(false);
            sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
            sessionUser = sessionVarlist.getCMSSessionUser();

            connectionToSM = new ConnectionToSecurityModule();
            pinGenerationManager = new PinGenerationAndmailManager();
            keyMgtmanager = new KeyManagementManager();
//            keyDetails = new ChanelKeysBean();


            batchId = request.getParameter("batchNo");
            succesPinGenCount = 0;
            this.getBulkCardList(batchId);

            this.getBulkCardReqDetails(batchId);



            for (int i = 0; i < carNumbers.size(); i++) {

                try {
                    cardNumber = carNumbers.get(i);
                    
//                    String binNumber = cardNumber.substring(0, 6);
//                    cardkeyId = keyMgtmanager.getCardKeyId(binNumber);
                    
                    cardkeyId = keyMgtmanager.getCardDetails(cardNumber).getCardKeyId();
                    
                    cardkeyBean = new CardKeyBean();
                    cardkeyBean = keyMgtmanager.getCardkeyDetails(cardkeyId);

//                    cardCategory = keyMgtmanager.getCardCategory(cardNumber);
//                    customerId = keyMgtmanager.getCustomerId(cardNumber);

//                    String chanelId = keyMgtmanager.getChanelId(StatusVarList.CHANEL_TYPE_VISA);
//                    keyDetails = keyMgtmanager.getChanelDetails(chanelId);
                    this.getCardTableDetails(cardNumber);

                    ANB = this.getANB(cardNumber);

                    ppk_spec = cardkeyBean.getPpk();

                    PAN = cardNumber;

                    ePPKPin = cardbean.getPinBlock();

                    NAME = bulkCardReq.getCdDomainDes() + " Card";

                    if (NAME.length() < 10) {
                        NAME_LEN = "0" + Integer.toString(NAME.length());
                    } else {
                        NAME_LEN = Integer.toString(NAME.length());
                    }


//                if (cardCategory.equals("M")) {
//                    cardMainCusBean = keyMgtmanager.getCardMainCusDetailbean(customerId);
//
//                    ADD1 = cardMainCusBean.getPermenentAddress1() + " ," + cardMainCusBean.getPermenentAddress2() + " ," + cardMainCusBean.getPermenentAddress3();
//                    ADD2 = cardMainCusBean.getCity() + ".";
//
//                } else if (cardCategory.equals("S")) {
//                    cardSuppCusBean = keyMgtmanager.getSuppCusDetails(customerId);
//
//                    ADD1 = cardSuppCusBean.getAddress1() + " ," + cardSuppCusBean.getAddress2() + " ," + cardSuppCusBean.getAddress3();
//                    ADD2 = cardSuppCusBean.getCity() + ".";
//
//                }

                    ADD1 = "Batch ID : " + batchId;
                    ADD2 = "Card Number : " + cardNumber;


                    System.out.println("add 1 length = " + ADD1.length());
                    if (ADD1.length() < 10) {
                        ADD1_LEN = "0" + Integer.toString(ADD1.length());
                    } else {
                        ADD1_LEN = Integer.toString(ADD1.length());
                    }


                    System.out.println("add 2 length = " + ADD2.length());
                    if (ADD2.length() < 10) {
                        ADD2_LEN = "0" + Integer.toString(ADD2.length());
                    } else {
                        ADD2_LEN = Integer.toString(ADD2.length());
                    }



                    String requestForSM = "0103" + ePPKPin + ppk_spec + ANB + PAN + NAME_LEN + NAME + ADD1_LEN + ADD1 + ADD2_LEN + ADD2;
                    LogFileCreator.writePinMailerInfoToLog("Security module Request from web --> "+requestForSM);
                    try {
                        responseFromSM = connectionToSM.getServerMsg(requestForSM);

                        LogFileCreator.writePinMailerInfoToLog("Response from security module --> "+responseFromSM);
                    } catch (Exception e) {
                        LogFileCreator.writePinMailerErrorToLog(e);
                        request.setAttribute("errorMsg", MessageVarList.COMUNICATION_ERROR);
                    }

                    if (responseFromSM != null && responseFromSM.substring(0, 4).equals("0200")) {
                        this.setAudittraceValue(request, cardNumber);
                        if (updateCardPinmailStatus(cardNumber, systemAuditBean)) {
                            succesPinGenCount = succesPinGenCount + 1;
                            LogFileCreator.writePinMailerInfoToLog(MessageVarList.SUCCES_PIN_MAIL + " for card number : " + cardNumber);
                        } else {
                            updateBulkReqqestTable(StatusVarList.BULK_CARD_PIN_MAIL_PENDING);
                            LogFileCreator.writePinMailerInfoToLog("An error occured while generating pin mailer for card number : " + cardNumber);
                        }

                    } else {
                        updateBulkReqqestTable(StatusVarList.BULK_CARD_PIN_MAIL_PENDING);
                        LogFileCreator.writePinMailerInfoToLog("An error occured while generating pin mailer for card number : " + cardNumber);
                    }
                } catch (Exception e) {
                    LogFileCreator.writePinMailerErrorToLog(e);
                    updateBulkReqqestTable(StatusVarList.BULK_CARD_PIN_MAIL_PENDING);
                    LogFileCreator.writePinMailerInfoToLog("An error occured while generating pin mailer for card number : " + cardNumber);
                }


            }//end of the for loop

            if (succesPinGenCount == carNumbers.size()) {
                updateBulkReqqestTable(StatusVarList.BULK_CARD_PIN_MAIL_COMPLETE);
            }

            if (succesPinGenCount == 0) {
                request.setAttribute("errorMsg", MessageVarList.BULK_PINGEN_ERROR);
            } else {
                request.setAttribute("successMsg", MessageVarList.BULK_PINMAIL_SUCSESS1 + succesPinGenCount + " " + MessageVarList.BULK_PINGEN_SUCSESS2 + carNumbers.size() + " " + MessageVarList.BULK_PINGEN_SUCSESS3);
            }
            

            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception e) {
            LogFileCreator.writePinMailerErrorToLog(e);
            rd = getServletContext().getRequestDispatcher(url);
            updateBulkReqqestTable(StatusVarList.BULK_CARD_PIN_MAIL_PENDING);
            request.setAttribute("errorMsg", MessageVarList.BULK_PINGEN_ERROR);
            rd.forward(request, response);
        } finally {
            out.close();

        }
    }

    public String getANB(String cardNo) throws Exception {
        String ANB = null;
        try {
            String lastDigitRemovedCardNo = cardNo.substring(0, (cardNo.length() - 1));
            String reverse = new StringBuffer(lastDigitRemovedCardNo).reverse().toString();

            String reversedANB = reverse.substring(0, 12);

            ANB = new StringBuffer(reversedANB).reverse().toString();
        } catch (Exception e) {
            LogFileCreator.writePinMailerErrorToLog(e);
            throw e;
        }

        return ANB;
    }

    public void getCardTableDetails(String cardNo) throws Exception {

        try {
            pinGenerationManager = new PinGenerationAndmailManager();
            cardbean = pinGenerationManager.getCardTableDetails(cardNo);
        } catch (Exception e) {
            LogFileCreator.writePinMailerErrorToLog(e);
            throw e;
        }

    }

    public Boolean updateCardPinmailStatus(String cardNo, SystemAuditBean systemAuditBean) throws Exception {
        Boolean status = false;
        this.setApplicationHistoryBean();
        try {
            pinGenerationManager = new PinGenerationAndmailManager();
            status = pinGenerationManager.updateCardPinmailStatus(cardNo, historybean, systemAuditBean);
        } catch (Exception e) {
            LogFileCreator.writePinMailerErrorToLog(e);
            status = false;
            throw e;
        }
        return status;
    }

    public void setApplicationHistoryBean() {
        historybean = new ApplicationHistoryBean();

        historybean.setApplicationId(cardNumber);
        historybean.setApplicationLevel("PMAL");
        historybean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
        historybean.setRemarks("Pin Mail Completed");
        historybean.setStatus(StatusVarList.HISTORY_COMPLETE);
    }

    public void getBulkCardList(String batchId) throws Exception {
        try {
            carNumbers = pinGenerationManager.getBulkPinMailCardList(batchId);
        } catch (Exception e) {
            LogFileCreator.writePinMailerErrorToLog(e);
            throw e;
        }
    }

    public void getBulkCardReqDetails(String batchId) throws Exception {
        bulkCardReq = new BulkCardRequestBean();
        try {
            bulkCardReq = pinGenerationManager.getBulkCardReqDetails(batchId);
        } catch (Exception e) {
            LogFileCreator.writePinMailerErrorToLog(e);
            throw e;
        }
    }

    public void updateBulkReqqestTable(String ststus) throws Exception {
        pinGenerationManager = new PinGenerationAndmailManager();
        try {
            pinGenerationManager.updateBulkReqqestTableForPinMail(ststus, batchId);
        } catch (Exception e) {
            LogFileCreator.writePinMailerErrorToLog(e);
            throw e;
        }

    }

    private void setAudittraceValue(HttpServletRequest request, String cardNumber) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set description 
            systemAuditBean.setDescription("Generated Pin Mail For Bulk Card Number: " + cardNumber + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CPMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CARD_APPLICATION_REPORT);
            systemAuditBean.setPageCode(PageVarList.BULK_PIN_MAIL);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(generateBulkPinMailServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(generateBulkPinMailServlet.class.getName()).log(Level.SEVERE, null, ex);
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
}
