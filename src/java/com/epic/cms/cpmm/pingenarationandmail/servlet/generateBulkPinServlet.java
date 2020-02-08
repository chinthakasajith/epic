/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.pingenarationandmail.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.camm.applicationconfirmation.bean.CardBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.cpmm.pingenarationandmail.been.CardKeyBean;
import com.epic.cms.cpmm.pingenarationandmail.busneslogic.PinGenerationAndmailManager;
import com.epic.cms.switchcontrol.cardkeys.bean.CardKeyProfilebean;
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
public class generateBulkPinServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist;
    private PinGenerationAndmailManager pinGenerationManager = null;
    private String ANB = null;
    private String ppk_spec = null;
    private String PVVK_Spec = null;
    private ConnectionToSecurityModule connectionToSM = null;
    private String responseFromSM = null;
    private String ePPKPin = null;
    private String PVV = null;
    private String OFFSET = null;
    private String TSP12 = null;
    private String VAL_Data = null;
    private char[] ANBArr = null;
    private String cardNumber = null;
    private String CVV = null;
    private String CVV2 = null;
    private String ICVV = null;
//    private ChanelKeysBean keyDetails;
    private KeyManagementManager keyMgtmanager;
    private String PVI;
    private CardBean cardBean = null;
    private String pinMethod = null;
    private SessionUser sessionUser;
    private ApplicationHistoryBean historybean = null;
    HttpSession sessionObj;
    private List<String> carNumbers = null;
    private String batchId = null;
    private int succesPinGenCount = 0;
    private String url = "/cpmm/pingenarateandmail/bulkpingeneration.jsp";
    private SystemAuditBean systemAuditBean = null;
    private String cardkeyId = null;
    private CardKeyBean cardkeyBean = null;
    private CardKeyProfilebean keyProfile = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            sessionObj = request.getSession(false);
            sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
            sessionUser = sessionVarlist.getCMSSessionUser();

            pinGenerationManager = new PinGenerationAndmailManager();
            connectionToSM = new ConnectionToSecurityModule();
            pinGenerationManager = new PinGenerationAndmailManager();
            keyMgtmanager = new KeyManagementManager();
//            keyDetails = new ChanelKeysBean();
            cardBean = new CardBean();

            batchId = request.getParameter("batchNo");
            succesPinGenCount = 0;
            this.getBulkCardList(batchId);

//            String chanelId = keyMgtmanager.getChanelId(StatusVarList.CHANEL_TYPE_VISA);
//            keyDetails = keyMgtmanager.getChanelDetails(chanelId);

            pinMethod = keyMgtmanager.getPinMethod();//PVV or OFFSET
            PVI = keyMgtmanager.getPVI();

            for (int i = 0; i < carNumbers.size(); i++) {


                cardNumber = carNumbers.get(i);
                cardkeyBean = new CardKeyBean();
                cardBean = keyMgtmanager.getCardDetails(cardNumber);
                
//                String binNumber = cardNumber.substring(0, 6);
                cardkeyId = cardBean.getCardKeyId();
                
                cardkeyBean = keyMgtmanager.getCardkeyDetails(cardkeyId);
                keyProfile = keyMgtmanager.getCardkeyprofileBean(cardkeyBean.getCardkeyprofileid());
                

                if (getPinBlockAndPvv()) {//generate pin block and PVV
                    if (generateCVV()) {//generate CVV
                        if (isCardAvailable(cardNumber)) {//check the availability of card on the online DB
                            this.setApplicationHistoryBean();

//                        Boolean onlineUpdateStatus = updateOnleCard(ePPKPin, PVV, OFFSET, pinMethod, CVV, cardNumber);
                            this.setAudittraceValue(request, cardNumber);
                            Boolean CardUpdateStatus = updateCardTable(ePPKPin, PVV, CVV, CVV2, ICVV, OFFSET, PVI, cardNumber, historybean, pinMethod, systemAuditBean);

                            if (CardUpdateStatus) {
                                succesPinGenCount = succesPinGenCount + 1;
                                this.writeInfoToLog();
                            } else {
                                updateBulkReqqestTable(StatusVarList.BULK_CARD_PIN_GENE_PENDING);
                            }

                        } else {
                            updateBulkReqqestTable(StatusVarList.BULK_CARD_PIN_GENE_PENDING);
                        }
                    } else {
                        updateBulkReqqestTable(StatusVarList.BULK_CARD_PIN_GENE_PENDING);
                    }

                } else {
                    updateBulkReqqestTable(StatusVarList.BULK_CARD_PIN_GENE_PENDING);
                }



            }//end of the for loop


            if (succesPinGenCount == carNumbers.size()) {
                updateBulkReqqestTable(StatusVarList.BULK_CARD_PIN_GENE_COMPLETE);
            }

            if (succesPinGenCount == 0) {
                request.setAttribute("errorMsg", MessageVarList.BULK_PINGEN_ERROR);
            } else {
                request.setAttribute("successMsg", MessageVarList.BULK_PINGEN_SUCSESS1 + succesPinGenCount + " " + MessageVarList.BULK_PINGEN_SUCSESS2 + carNumbers.size() + " " + MessageVarList.BULK_PINGEN_SUCSESS3);
            }


            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);

        } catch (Exception e) {
            try {
                LogFileCreator.writePinGenerationErrorToLog(e);
                request.setAttribute("errorMsg", MessageVarList.BULK_PINGEN_ERROR);
                updateBulkReqqestTable(StatusVarList.BULK_CARD_PIN_GENE_PENDING);
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            } catch (Exception e2) {
            }

        } finally {
            out.close();
        }
    }

    public void getBulkCardList(String batchId) throws Exception {
        try {
            carNumbers = pinGenerationManager.getBulkCardList(batchId);
        } catch (Exception e) {
            LogFileCreator.writePinGenerationErrorToLog(e);
            throw e;
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
            LogFileCreator.writePinGenerationErrorToLog(e);
            throw e;
        }

        return ANB;
    }

    public Boolean generateCVV() {
        try {


            String CVVDataForCvv2, CVVDataIcvv, CVVData, CVVResponse = null, CVV2Request = null, IcvvRequest = null;
            CVVData = cardNumber + cardBean.getExpiryDate() + cardBean.getServiceCode();
            CVVDataForCvv2 = cardNumber + cardBean.getExpiryDate() + "000";
            CVVDataIcvv = cardNumber + cardBean.getExpiryDate() + "999";

            if (keyProfile.getIsCVK1Pair().equals("0")) {

                CVVResponse = "0101" + cardkeyBean.getCvk1() + CVVData;
                CVV2Request = "0101" + cardkeyBean.getCvk2() + CVVDataForCvv2;
                IcvvRequest = "0101" + cardkeyBean.getIcvk() + CVVDataIcvv;
            } else if (keyProfile.getIsCVK1Pair().equals("1")) {

                CVVResponse = "0101" + cardkeyBean.getCvk1a() + cardkeyBean.getCvk1b() + CVVData;
                CVV2Request = "0101" + cardkeyBean.getCvk2a() + cardkeyBean.getCvk2b() + CVVDataForCvv2;
                IcvvRequest = "0101" + cardkeyBean.getIcvka() + cardkeyBean.getIcvkb() + CVVDataIcvv;
            }

//            String CVVData = ANB + expDate;



            LogFileCreator.writePinGenerationInfoToLog("HSM request for generate CVV --> " + CVVResponse);
            responseFromSM = connectionToSM.getServerMsg(CVVResponse);
            LogFileCreator.writePinGenerationInfoToLog("HSM response for generate CVV --> " + responseFromSM);
            CVV = responseFromSM.substring(4, 7);


            LogFileCreator.writePinGenerationInfoToLog("HSM request for generate CVV2 --> " + CVV2Request);
            responseFromSM = connectionToSM.getServerMsg(CVV2Request);
            LogFileCreator.writePinGenerationInfoToLog("HSM response for generate CVV2 --> " + responseFromSM);
            CVV2 = responseFromSM.substring(4, 7);

            if (cardBean.getProductionMode().equals("EMVC")) {
                LogFileCreator.writePinGenerationInfoToLog("HSM request for generate ICVV (EMVC card) --> " + IcvvRequest);
                responseFromSM = connectionToSM.getServerMsg(IcvvRequest);
                LogFileCreator.writePinGenerationInfoToLog("HSM response for generate ICVV (EMVC card) --> " + responseFromSM);
                ICVV = responseFromSM.substring(4, 7);

            } else if (cardBean.getProductionMode().equals("MGTC")) {

                ICVV = "000";

            } else {
            }



            return true;
        } catch (Exception e) {
            LogFileCreator.writePinGenerationErrorToLog(e);
            return false;
        }


    }

    public Boolean getPinBlockAndPvv() {
        try {


            ANB = this.getANB(cardNumber);

            if (keyProfile.getIsCVK1Pair().equals("0")) {
                PVVK_Spec = cardkeyBean.getPvk();
            } else if (keyProfile.getIsCVK1Pair().equals("1")) {
                PVVK_Spec = cardkeyBean.getPvka() + cardkeyBean.getPvkb();
            }



            ppk_spec = cardkeyBean.getPpk();


            if (pinMethod.equals("1")) {
                TSP12 = ANB.substring(0, 11) + PVI;
                VAL_Data = "0000000000000000";
            } else if (pinMethod.equals("2")) {
                TSP12 = "000000000000";
                VAL_Data = ANB + cardBean.getExpiryDate();
            }


            String requestToSM = "0102" + ANB + ppk_spec + PVVK_Spec + TSP12 + VAL_Data + "0" + pinMethod;
            LogFileCreator.writePinGenerationInfoToLog("HSM request for pin block,pvv & offset --> " + requestToSM);

            responseFromSM = connectionToSM.getServerMsg(requestToSM);
            LogFileCreator.writePinGenerationInfoToLog("HSM response for pin block,pvv & offset --> " + responseFromSM);

            ePPKPin = responseFromSM.substring(4, 20);



            if (pinMethod.equals("1")) {
                PVV = responseFromSM.substring(20);
                OFFSET = "000000000000";
            } else if (pinMethod.equals("2")) {
                OFFSET = responseFromSM.substring(20);
                PVV = "0000";
            }


            return true;
        } catch (Exception e) {
            LogFileCreator.writePinGenerationErrorToLog(e);
            return false;
        }
    }

    public Boolean isCardAvailable(String cardNumber) throws Exception {
        pinGenerationManager = new PinGenerationAndmailManager();
        Boolean status = false;
        try {
            status = pinGenerationManager.isCardAvailable(cardNumber);
        } catch (Exception e) {
            LogFileCreator.writePinGenerationErrorToLog(e);
            status = false;
            throw e;
        }
        return status;
    }

    public Boolean updateOnleCard(String eppkpin, String pvv, String offset, String pinMthd, String cvv, String cardNo) {
        pinGenerationManager = new PinGenerationAndmailManager();
        Boolean status = false;
        try {
            status = pinGenerationManager.updateOnlineCard(eppkpin, pvv, offset, pinMthd, cvv, cardNo);
        } catch (Exception e) {
            LogFileCreator.writePinGenerationErrorToLog(e);
            status = false;
        }
        return status;
    }

    public Boolean updateCardTable(String eppkpin, String pvv, String cvv, String cvv2, String icvv, String offset, String pvki, String cardNo, ApplicationHistoryBean historybean, String pinMethod, SystemAuditBean systemAuditBean) {
        pinGenerationManager = new PinGenerationAndmailManager();
        Boolean status = false;
        try {
            status = pinGenerationManager.updateCardTable(eppkpin, pvv, cvv, cvv2, icvv, offset, pvki, cardNo, historybean, pinMethod, systemAuditBean);
        } catch (Exception e) {
            LogFileCreator.writePinGenerationErrorToLog(e);
            status = false;
        }
        return status;
    }

    public void updateBulkReqqestTable(String ststus) throws Exception {
        pinGenerationManager = new PinGenerationAndmailManager();
        try {
            pinGenerationManager.updateBulkReqqestTable(ststus, batchId);
        } catch (Exception e) {
            LogFileCreator.writePinGenerationErrorToLog(e);
            throw e;
        }

    }

    public void setApplicationHistoryBean() {
        historybean = new ApplicationHistoryBean();

        historybean.setApplicationId(cardNumber);
        historybean.setApplicationLevel("PGEN");
        historybean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
        historybean.setRemarks("Pin generation Completed");
        historybean.setStatus(StatusVarList.HISTORY_COMPLETE);
    }

    private void setAudittraceValue(HttpServletRequest request, String cardNumber) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set description 
            systemAuditBean.setDescription("Generated Bulk Pin Card Number: " + cardNumber + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CPMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.CARD_APPLICATION_REPORT);
            systemAuditBean.setPageCode(PageVarList.BULK_PIN_GENERATION);
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

    void writeInfoToLog() {
        String infor = null;

        infor = "\n-------------------------------------- NEW CARD --------------------------------------\r\n\r\n"
                + "Card Number ----- [ " + cardNumber + " ]\r\n"
                + "Card Domain ----- [ " + "BULK" + " ]\r\n"
                + "Batch Number ----- [ " + batchId + " ]\r\n"
                + "Chanel ID ------- [ " + cardkeyId + " ]\r\n"
                + "Pin Method ------ [ " + pinMethod + " ]\r\n"
                + "Production Mode - [ " + cardBean.getProductionMode() + " ]\r\n"
                + "PVI ------------- [ " + PVI + " ]\r\n"
                + "ANB ------------- [ " + ANB + " ]\r\n"
                + "PVVK_Spec ------- [ " + PVVK_Spec + " ]\r\n"
                + "ppk_spec -------- [ " + ppk_spec + " ]\r\n"
                + "TSP12 ----------- [ " + TSP12 + " ]\r\n"
                + "VAL_Data -------- [ " + VAL_Data + " ]\r\n\r\n"
                + "ePPKPin --------- [ " + ePPKPin + " ]\r\n"
                + "PVV ------------- [ " + PVV + " ]\r\n"
                + "OFFSET ---------- [ " + OFFSET + " ]\r\n"
                + "CVV ------------- [ " + CVV + " ]\r\n"
                + "CVV2 ------------ [ " + CVV2 + " ]\r\n"
                + "ICVV ------------ [ " + ICVV + " ]\r\n";

        LogFileCreator.writePinGenerationInfoToLog(infor);

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
