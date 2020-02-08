/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardnumbergeneration.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardNumberFormateBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardTempBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.NumberFormateFieldTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineCardTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.businesslogic.NumberGenarationManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardnumbergeneration.bean.BulkCardNumberFormatBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardnumbergeneration.businesslogic.BulkCardNumberGenerationManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.logs.LogFileCreator;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jpos.iso.ISOUtil;

/**
 *
 * @author nalin
 */
public class StartBulkCardNumberGenerationServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private BulkCardRequestBean numberGenBean = null;
    private CardTempBean cardTempBean = null;
    private CardBean cardBean = null;
    //private CardAccountBean cardAccBean = null;
    private OnlineCardTableBean onlineBean = null;
    private AccountTempBean accountTempBean = null;
    //private OnlineAccountTableBean onlineAccountBean = null;
    private BulkCardNumberFormatBean BKNumFormatBean = null;
    private BulkCardNumberGenerationManager bulkNumGenManager = null;
    private NumberGenarationManager numGenmanager;
    private List<NumberFormateFieldTableBean> numberFormateFieldList;
    private String batchID = null;
    private String url = "/LoadBulkCardNumberGenerationServlet";

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
                String pageCode = PageVarList.BULK_CARD_NUMBER_GENERATION;
                String taskCode = TaskVarList.NUMBERGENERATION;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
/////////////////////////////////////////////////////////////////////////////////////////////////////////

                    batchID = request.getParameter("batchID");
                    this.getBulkCardBatchDetails(batchID);
                    int successCount = 0;
                    int failedCount = 0;
                    try {
                        for (int i = 0; i < Integer.parseInt(numberGenBean.getApprvNumOfCds()); i++) {

                            boolean isSuccess = this.startNumberGenerationProcess(batchID);

                            if (isSuccess == true) {
                                successCount += 1;

                            } else {
                                failedCount += 1;

                            }
                        }
                        if (successCount == Integer.parseInt(numberGenBean.getApprvNumOfCds())) {

                            this.setNumberGenerationStatus(batchID, StatusVarList.BULK_CARD_NUMBER_GENERATION_COMPLETE, sessionVarlist.getCMSSessionUser().getUserName());

                        } else {

                            this.setNumberGenerationStatus(batchID, StatusVarList.BULK_CARD_NUMBER_GENERATION_PENDING, sessionVarlist.getCMSSessionUser().getUserName());
                        }

                    } catch (Exception ee) {
                        throw ee;
                    }

                    request.setAttribute("successMsg", "Successfully number generated for " + successCount + " Bulk Cards, and failed " + failedCount + " Bulk Cards ");
                    rd = request.getRequestDispatcher(url);

/////////////////////////////////////////////////////////////////////////////////////////////////////////
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
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);


        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.BULK_CARD_NUM_GEN_ERROR);
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

    private boolean startNumberGenerationProcess(String batchID) throws Exception {
        boolean isSucess = false;
        String numberFormate = "";
        String sequence = "";
        String cno = "";
        String binNumber = "";
        //String accno = "";
        String cashLimit = "";

        this.getCardTemplateDetails(numberGenBean.getTemplateCode());
        this.getAccountTemplateDetails(cardTempBean.getAccountTempCode());

        cashLimit = this.calculateCreditLimit(numberGenBean.getCreditLimit(), cardTempBean.getCashAdvancedRate());
        // accno = this.setNewAccountNumber();

        this.getNumberFormateAndSequenceBinNumber(numberGenBean.getCdBin());
        try {

            binNumber = numberGenBean.getCdBin();
            numberFormate = BKNumFormatBean.getNumberFormatCode();
            sequence = BKNumFormatBean.getSequenceNumber();

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
        cno = this.genarateCardNumber(numberFormate, binNumber, sequence);
        System.out.println("cno  ="+cno);

        this.setCardBean(batchID, cno, numberGenBean, cardTempBean, cashLimit);
        this.setOnlineCardBean(cno, numberGenBean, cardTempBean, cashLimit);
        //this.setCardAccountBean(accno, numberGenBean, accountTempBean);
        //this.setOnlineAccountBean(accno, numberGenBean, accountTempBean, cashLimit);

        bulkNumGenManager = new BulkCardNumberGenerationManager();
        isSucess = bulkNumGenManager.insertIntoCardTables(numberGenBean, cardBean, onlineBean, binNumber, sequence);

        return isSucess;
    }

    private void getBulkCardBatchDetails(String batchID) throws Exception {

        try {
            numberGenBean = new BulkCardRequestBean();
            bulkNumGenManager = new BulkCardNumberGenerationManager();

            numberGenBean = bulkNumGenManager.getBulkCardBatchDetails(batchID);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getNumberFormateAndSequenceBinNumber(String binNumber) throws Exception {
        try {
            bulkNumGenManager = new BulkCardNumberGenerationManager();
            BKNumFormatBean = new BulkCardNumberFormatBean();

            BKNumFormatBean = bulkNumGenManager.getNumberFormateBinNumber(binNumber);

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }

    }

    private CardTempBean getCardTemplateDetails(String cardTemCode) throws Exception {
        try {
            bulkNumGenManager = new BulkCardNumberGenerationManager();
            cardTempBean = new CardTempBean();
            cardTempBean = bulkNumGenManager.getCardTemplateDetails(cardTemCode);


        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        return cardTempBean;
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

    private String getFormatedCardNumber(String cno) throws Exception {
        String number = "";
        try {
            if (cno.length() == 16) {
                number = cno.substring(0, 4).concat(" ").concat(cno.substring(4, 8).concat(" ").concat(cno.substring(8, 12)).concat(" ").concat(cno.substring(12, 16)));
            } else if (cno.length() == 19) {
                number = cno.substring(0, 4).concat(" ").concat(cno.substring(4, 8).concat(" ").concat(cno.substring(8, 12)).concat(" ").concat(cno.substring(12, 16).concat(" ").concat(cno.substring(16, 19))));
            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        return number;
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
            LogFileCreator.writeErrorToNumberGenLog(ex);
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
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }

        return issue;
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
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        return value;
    }

    private AccountTempBean getAccountTemplateDetails(String accountTemCode) throws Exception {
        try {
            bulkNumGenManager = new BulkCardNumberGenerationManager();
            accountTempBean = bulkNumGenManager.getAccountTemplateDetails(accountTemCode);

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        return accountTempBean;
    }

    private String getCardKey(String bin, String product, String productionMode) throws Exception {
        String cardKey = null;
        try {
            bulkNumGenManager = new BulkCardNumberGenerationManager();
            cardKey = bulkNumGenManager.getCardKey(bin, product, productionMode);

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        return cardKey;
    }

    private String setNewAccountNumber() throws Exception {
        String newAccontno = "";
        try {
            numGenmanager = new NumberGenarationManager();
            String accno = numGenmanager.getMaxAccnoFromCardAccount();


            newAccontno = ISOUtil.zeropad((Integer.parseInt(accno) + 1) + "", 12);

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        return newAccontno;
    }

    private boolean setNumberGenerationStatus(String batchID, String status, String user) throws Exception {
        boolean isSet = false;
        try {

            bulkNumGenManager = new BulkCardNumberGenerationManager();
            isSet = bulkNumGenManager.setNumberGenerationStatus(batchID, status, user);

        } catch (Exception ex) {
            throw ex;
        }
        return isSet;
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
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        return value;
    }

    private String genarateCardNumber(String numberFormate, String binNumber, String sequence) throws Exception {
        String cardNumber = "";
        String temp = "";

        try {
            String bin = binNumber; //ok
            // String accountNumber = "";
            String branchCode = "";  //ok
            String sequenceNumber = ""; //ok
            String randomNumber = "";
            String cardProduct = "";  //ok
            String cardNumberLenght = ""; //ok


            numGenmanager = new NumberGenarationManager();
            CardNumberFormateBean numFormatBean = new CardNumberFormateBean();
            //CardApplicationBean applicationBean = new CardApplicationBean();

            numFormatBean = numGenmanager.getNumberFormateDetailsFromCode(numberFormate); //get naumber format deatils form NUMBERFORMAT TABLE by FORMATCODE
            // applicationBean = numGenmanager.getApplicationDetailsFromID(applicationId);//get CARDaPPLICATION TABLE details from APPLICATIONID
System.out.println("sequence ="+sequence);
            int seq = Integer.parseInt(sequence) + 1;
            System.out.println("seq ="+seq);
            sequenceNumber = this.getZeropadSeq(Integer.toString(seq), sequence.length());
            System.out.println("sequenceNumber ="+sequenceNumber);
            cardNumberLenght = numFormatBean.getCardNumberLenght();
System.out.println("cardNumberLenght ="+cardNumberLenght);
            cardProduct = numberGenBean.getCdProduct();
            branchCode = numberGenBean.getBranchCode();
System.out.println("cardProduct ="+cardProduct);
System.out.println("branchCode ="+branchCode);

            numberFormateFieldList = new ArrayList<NumberFormateFieldTableBean>();
            numberFormateFieldList = numGenmanager.getNumberFormateFieldsFromCode(numberFormate);

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

//                } else if (fieldCode.equals("FACC")) {
//                    boolean isAccMatch = this.isAccMatch(accountNumber, from, to);
//                    if (isAccMatch == true) {
//                        temp += accountNumber;
//                    } else {
//                        throw new Exception();
//                    }

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

                        System.out.println("temp  ="+temp);
                    } else {
                        throw new Exception();
                    }

                } else if (fieldCode.equals("FRAN")) {

                    int random = this.setRandomNumber(from, to);
                    randomNumber = String.valueOf(random);
                    temp += randomNumber;
                    System.out.println("temp  ="+temp);
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
                    System.out.println("temp  ="+temp);

                }

            }

            if (temp.length() == Integer.parseInt(cardNumberLenght)) {
                cardNumber = temp;
                // System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*********************************card no" + cardNumber);

            }




        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        System.out.println("cardNumber ="+cardNumber);
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
            LogFileCreator.writeErrorToNumberGenLog(ex);
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
            LogFileCreator.writeErrorToNumberGenLog(ex);
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
            LogFileCreator.writeErrorToNumberGenLog(ex);
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
            LogFileCreator.writeErrorToNumberGenLog(ex);
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
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
        return isProductMatch;
    }

    public int setCheckDigit(String cno) {
        String partOfCal = "";
        int checkDigit;
//        if (!isCorrectNumber(cno, 0)) {
//            System.out.println("Not a correct number");
//            //System.exit(0);
//        }
        partOfCal = String.valueOf(this.calculate(cno, 1));
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

    public int setRandomNumber(int from, int to) throws Exception {

        int random;
        int lenght = to - from + 1;
        try {
            Random rn = new Random(lenght);
            random = rn.nextInt();


        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }

        return random;
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

    private void setCardBean(String batchID, String cno, BulkCardRequestBean numGenBean, CardTempBean cardTempBean, String cashLimit) {
        cardBean = new CardBean();
        try {



            cardBean.setCardNumber(cno);
            cardBean.setBatchID(batchID);
            /////////////////////////////
            cardBean.setAvailableBalence(cashLimit);
            cardBean.setCardNumberPrintedOnCard(this.getFormatedCardNumber(cno));
            cardBean.setExprioryDate(this.getExpdate(cardTempBean.getExpiryPeriod()));
            cardBean.setIssueDate(this.getIssuedate());
            cardBean.setCardProduct(numGenBean.getCdProduct());
            //cardBean.setCardCategory(numGenBean.);
            cardBean.setCardType(numGenBean.getCdType());
            cardBean.setCreditLimit(numGenBean.getCreditLimit());
            cardBean.setCashLimit(cashLimit);
            cardBean.setCashAvailableBalence(cashLimit);
            cardBean.setEmbossStatus(StatusVarList.NOSTATUS);
            cardBean.setEncodeStatus(StatusVarList.NOSTATUS);
            cardBean.setMainCardNumber(cno);


            cardBean.setNoGenaratedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardBean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardBean.setPinStatus(StatusVarList.NOSTATUS);
            cardBean.setPinmailStatus(StatusVarList.NOSTATUS);
            cardBean.setCardStatus(StatusVarList.INACTIVE_STATUS);
            cardBean.setPriorityLevel(numGenBean.getPriorityLvl());
            cardBean.setTempotbAmount("0");
            cardBean.setLoyalityPoint("0");
            cardBean.setRedeemPoint("0");
            cardBean.setServiceCode(cardTempBean.getServiceCode());
            cardBean.setCurenncyCode(cardTempBean.getCurrencyNumCode());
            cardBean.setReissueTresholPeriod(cardTempBean.getReissueTresholPeriod());
            cardBean.setProductionMode(numGenBean.getCdProductionMode());
            cardBean.setCardDomain(numGenBean.getCdDomain());
            cardBean.setTxnProfileCode(cardTempBean.getTxnProfCode());
            cardBean.setCardKey(this.getCardKey(numGenBean.getCdBin(), numGenBean.getCdProduct(), this.mapProductionCodes(numGenBean.getCdProductionMode())));

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

    private void setOnlineCardBean(String cno, BulkCardRequestBean numGenBean, CardTempBean cardTempBean, String cashLimit) {
        try {
            onlineBean = new OnlineCardTableBean();

            onlineBean.setCardNumber(cno);
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
            onlineBean.setCreditLimit(numGenBean.getCreditLimit());
            onlineBean.setCashlimit(cashLimit);// catculate value
            onlineBean.setOtbCash(cashLimit);// catculate value
            onlineBean.setOtbCredit(numGenBean.getCreditLimit());
//            onlineBean.setPayMode("1");
            onlineBean.setEod("1");
            onlineBean.setTliStatus("2");
            onlineBean.setStatus("2");
            onlineBean.setLastUpdatedUser(sessionUser.getUserName());
            onlineBean.setServiceCode(cardTempBean.getServiceCode());
            onlineBean.setCardDomain(this.getOnlineCardDomain(numGenBean.getCdDomain()));
            onlineBean.setEmvStatus("1");
            onlineBean.setCardBin(numGenBean.getCdBin());
            onlineBean.setCardKey(this.getCardKey(numGenBean.getCdBin(), numGenBean.getCdProduct(), this.mapProductionCodes(numGenBean.getCdProductionMode())));
//             if (numGenBean.getCdType().equals("VISA")) {
//                onlineBean.setCardType("1");
//            }
//            if (numGenBean.getCdType().equals("MAST")) {
//                onlineBean.setCardType("2");
//            }
//            if (numGenBean.getCdType().equals("AMEX")) {
//                onlineBean.setCardType("3");
//            }
//            if (numGenBean.getCdType().equals("PLUS")) {
//                onlineBean.setCardType("4");
//            }
//            if (numGenBean.getCdType().equals("MSTR")) {
//                onlineBean.setCardType("5");
//            }
//            if (numGenBean.getCdType().equals("CARG")) {
//                onlineBean.setCardType("6");
//            }
//            if (numGenBean.getCdType().equals("CIRR")) {
//                onlineBean.setCardType("7");
//            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

    private String getOnlineCardDomain(String cardDomain) throws Exception {
        String cardDominId = null;
        try {
            bulkNumGenManager = new BulkCardNumberGenerationManager();
            cardDominId = bulkNumGenManager.getOnlineCardDomain(cardDomain);

        } catch (Exception ex) {
            throw ex;
        }
        return cardDominId;
    }

    private String mapProductionCodes(String backEndCode) throws Exception {
        String onlineCode = null;
        try {

            if (backEndCode.equals(StatusVarList.EMVC_CHIP_CARD)) {
                onlineCode = "1";
            } else if (backEndCode.equals(StatusVarList.MAGNATIC_STRIPE_CARD)) {
                onlineCode = "2";
            } else if (backEndCode.equals(StatusVarList.VIRTUAL_CARD)) {
                onlineCode = "3";
            } else if (backEndCode.equals(StatusVarList.CONTACTLESS_CARD)) {
                onlineCode = "4";
            } else if (backEndCode.equals(StatusVarList.EMV_BIOMETRIC_CARD)) {
                onlineCode = "5";
            }

        } catch (Exception ex) {
            throw ex;
        }
        return onlineCode;
    }

//    private void setCardAccountBean(String accno, BulkCardRequestBean numGenBean, AccountTempBean accountTempBean) {
//        cardAccBean = new CardAccountBean();
//        try {
//
//            cardAccBean.setAccountNumber(accno);
//            cardAccBean.setBillingId(accountTempBean.getBillCycle());
//            cardAccBean.setRiskProfileCode(accountTempBean.getAccRskProf());
//            cardAccBean.setCreditLimit(numGenBean.getCreditLimit());
//            cardAccBean.setLastupdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
//            cardAccBean.setStatus("ACT");
//        } catch (Exception ex) {
//            LogFileCreator.writeErrorToNumberGenLog(ex);
//        }
//    }
//    private void setOnlineAccountBean(String accno, BulkCardRequestBean numGenBean, AccountTempBean accountTempBean, String cashLimit) {
//        try {
//            onlineAccountBean = new OnlineAccountTableBean();
//
//            onlineAccountBean.setCurrencyCode(this.getZeroPadvalue(accountTempBean.getCurrencyCode()));//get from account template and zeero pad
//            onlineAccountBean.setAccountNumber(accno);
//            onlineAccountBean.setRiskProfileCode(accountTempBean.getAccRskProf());//get from account template
//            onlineAccountBean.setTxnCount("0");
//            onlineAccountBean.setCashTxnCount("0");
//            onlineAccountBean.setCreditLimit(numGenBean.getCreditLimit());
//            onlineAccountBean.setCashLimit(cashLimit);// catculate value
//            onlineAccountBean.setTotalTxnAmount("0");
//            onlineAccountBean.setTotalCashTxnAmount("0");// catculate value
//            onlineAccountBean.setOtbCash(cashLimit);// catculate value
//            onlineAccountBean.setOtbCredit(numGenBean.getCreditLimit());
//            onlineAccountBean.setStatus("1");
//        } catch (Exception ex) {
//            LogFileCreator.writeErrorToNumberGenLog(ex);
//        }
//    }
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
