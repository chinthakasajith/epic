/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.numbergenaration.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.admin.templatemgt.debitcardtemplate.bean.DebitCardTemplateBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardAccountBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardAccountCustomerBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardApplicationBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardCustomerBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardMainCustomerDetailsBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardNumberFormateBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardSuplimentoryCustomerBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardTempBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.NumberFormateFieldTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.NumberGenarationDataBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineAccountTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineCardTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineCustomerTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.businesslogic.NumberGenarationManager;
import com.epic.cms.camm.applicationconfirmation.bean.DebitCardAccountTemplateBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DebitPersonalBean;
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
import java.util.ArrayList;
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
public class StartDebitNumberGenarationServlet extends HttpServlet {

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
    private List<CardAccountBean> cardAccBeanLst;
    private String errorMessage = null;
    private HashMap<String, String> priorityLevelList = null;
    //private SearchUserAssignAppBean searchBean = null;
    private List<NumberGenarationDataBean> searchList = null;
    private String maxCustomerId = null;
    CardBean replacecardBean = null;
    CardCustomerBean cardCustomerBean1 = null; //to set backend card customer data
    CardCustomerBean cardCustomerBean2 = null;
    OnlineCustomerTableBean onlineCustomerBean1 = null;//to set online card customer data
    OnlineCustomerTableBean onlineCustomerBean2 = null;
    CardBean cardBean1 = null;// to set backend card data
    CardBean cardBean2 = null;
    OnlineCardTableBean onlineBean1 = null;//to set online card data
    OnlineCardTableBean onlineBean2 = null;
    CardAccountBean cardAccBean1 = null;// to set backend account data
    CardAccountBean cardAccBean2 = null;
    CardAccountBean cardAccBean3 = null;
    CardAccountBean cardAccBean4 = null;
    CardAccountBean cardAccLoyalityBean1 = null;
    CardAccountBean cardAccLoyalityBean2 = null;
    OnlineAccountTableBean onlineAccountBean1 = null;  // to set online account data
    OnlineAccountTableBean onlineAccountBean2 = null;
    OnlineAccountTableBean onlineAccountBean3 = null;
    OnlineAccountTableBean onlineAccountBean4 = null;
    OnlineAccountTableBean onlineAccountLoyalityBean1 = null;
    OnlineAccountTableBean onlineAccountLoyalityBean2 = null;
    private List<CardAccountCustomerBean> cacList1;
    private List<CardAccountCustomerBean> cacList2;
    private List<CardAccountCustomerBean> cacCustomer1List = new ArrayList<CardAccountCustomerBean>();
//    private List<CardAccountCustomerBean> cacCustomer2List = new ArrayList<CardAccountCustomerBean>();
    OnlineCardTableBean onlineReplaceCard = null;
    CustomerTempBean customerTempBean = null;// to store template data
    private DebitCardTemplateBean debitCardTempBean = null;
    private DebitCardAccountTemplateBean debitAccountTempBean = null;
    //private String url = "/camm/numbergenaration/numbergenarationhistory.jsp";
    private String url2 = "/LoadDebitCardNumberGenarationServlet";
    private String loyalityAccNo = "";
    private CardCustomerBean primarycardCusBean = null;//to store application level customer data
    private CardCustomerBean jointcardCusBean = null;
    private boolean useCustomer1oldcard = false;
    private boolean useCustomer2oldcard = false;
    private String customer1CardNo = null;
    private String customer2CardNo = null;
    private String debitApplicationType = "";

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
                String pageCode = PageVarList.DEBIT_NUMBERGEN;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    String idArray[] = null;
                    String idList = "";
                    idList = request.getParameter("hiddenarray");

                    if (idList !=null) {
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


                                ApplicationHistoryBean historyBean = new ApplicationHistoryBean();

                                historyBean.setApplicationId(idArray[i]);
                                historyBean.setApplicationLevel("NGEN");
                                historyBean.setLastUpdatedUser(sessionUser.getUserName());

                                boolean isprocess = this.startNumbergenarationProcess(idArray[i]);

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

                        if (successCount == 0) {
                            request.setAttribute("errorMsg", "Number genaration failed,see the log files for more informations ");

                        } else {
                            request.setAttribute("successMsg", "Successfully number genarated for " + successCount + " application and failed " + failedCount + " application");

                        }
                    } else {
                        request.setAttribute("errorMsg", "Select atleas one application before genarate");
                    }

                    //request.setAttribute("numberFormateLst", numberFormateList);

                    rd = request.getRequestDispatcher(url2);



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
            LogFileCreator.writeErrorToNumberGenLog(ex);
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.ERROR_DEBIT_NUM_GEN);
            rd = request.getRequestDispatcher(url2);

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

    private boolean startNumbergenarationProcess(String applicationId) throws Exception {

        boolean success = false;
        this.getMaxFromCardApplication();
        String customerId = this.setCustomerId(maxCustomerId);
        String jointCustomerId = "";

        if (customerId != null) {
            int p = Integer.parseInt(customerId);
            jointCustomerId = String.valueOf(p + 1);
        }

        String customerTemCode = ""; //ok
        String accountTemCode = ""; //ok
        String cardTemCode = ""; //ok
        String staffastatus = ""; //ok
        String productionMode = ""; //ok
        String cashAdvancedRate = ""; //ok
        String cashLimit = ""; //ok

        String numberFormateAndSeq = "";
        String numberFormate = "";
        String sequence1 = "";
        String sequence2 = "";
        String binNumber = "";


        DebitPersonalBean mainBean = new DebitPersonalBean();
//        SupplementaryApplicationBean suplimentoryBean = new SupplementaryApplicationBean();


        CardApplicationBean applicationBean = new CardApplicationBean();
        manager = new NumberGenarationManager();
        applicationBean = manager.getApplicationDetailsFromID(applicationId);   //get card application info

        customerTemCode = applicationBean.getCusTempalte();// get template details from CARDAPPLICATION TABLE
        accountTemCode = applicationBean.getAcctemplate();
        cardTemCode = applicationBean.getCardtemplate();
        binNumber = applicationBean.getBinprofile();
        debitApplicationType = applicationBean.getCardCategory();

        customerTempBean = this.getCustomerTemplateDetails(customerTemCode);
        debitAccountTempBean = this.getDebitAccountTemplateDetails(accountTemCode);///  changed
        debitCardTempBean = this.getDebitCardTemplateDetails(cardTemCode);///  changed

        staffastatus = applicationBean.getStaffStatus();
//      productionMode =applicationBean.get();
        cashAdvancedRate = debitCardTempBean.getCashAdvanceRate();
//        cashLimit = this.calculateCreditLimit(applicationBean.getConfirmCreditLimit(), cashAdvancedRate);

        numberFormateAndSeq = this.getNumberFormateAndSequenceBinNumber(binNumber);
        try {
            String[] array = new String[2];
            array = numberFormateAndSeq.split("\\,");
            numberFormate = array[0];

            sequence1 = array[1];

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }

//        cno = this.genarateCardNumber(applicationId, numberFormate, binNumber, sequence1);

        //check whether the application coming from replace category(CARD REQUEST TABLE)
        String replaceCard = "";
        replaceCard = this.isReplaceAppication(applicationId);

//        if (applicationBean.getCardCategory().equals(StatusVarList.CARD_CATEGORY_MAIN)) {



        captureDataManager = new CaptureDataManager();
        mainBean = captureDataManager.getDebitCardApplicationDetails(applicationId);//get data from debit card application table

        primarycardCusBean = new CardCustomerBean();
        jointcardCusBean = new CardCustomerBean();
        primarycardCusBean = manager.getCustomerCardDetailsById(mainBean.getIdentificationNumber());

        if (mainBean.getsIdentificationNumber() != null) {

            jointcardCusBean = manager.getCustomerCardDetailsById(mainBean.getsIdentificationNumber());
        }

        boolean iscardCusExsit = false;
        boolean isJointCusExsit = false;


        if (primarycardCusBean.getCustomerId() == null) {
            //create new customer id
            iscardCusExsit = false;
        } else {
            //use old customer id
            jointCustomerId = customerId;
            customerId = primarycardCusBean.getCustomerId();
            iscardCusExsit = true;
        }

        if (jointcardCusBean.getCustomerId() == null) {
            //create new customer id
            isJointCusExsit = false;
        } else {
            //use old customer id
            jointCustomerId = jointcardCusBean.getCustomerId();
            isJointCusExsit = true;
        }


        if (debitApplicationType.equals("P")) {

            if (replaceCard.equals("")) {

                if (iscardCusExsit == true) {
                    this.getCustomer1DebitCardLst(primarycardCusBean.getCustomerId());

                    if (cacList1 != null) {
                        for (int i = 0; i < cacList1.size(); i++) {
                            if (cacList1.get(i).getCardType().equals(mainBean.getCardType())) {
                                //use custome1 old card    
                                useCustomer1oldcard = true;
                                customer1CardNo = cacList1.get(i).getCardNumber();
                                break;
                            }

                        }
                    }



                }
                this.genarateCardNumbers(applicationId, numberFormate, binNumber, sequence1);

                this.setCardBean(customerId, customer1CardNo, applicationBean, mainBean.getNameOncard(),
                        debitCardTempBean, cashLimit, mainBean.getAddress1(), mainBean.getAddress2(), mainBean.getAddress3(), mainBean.getCity());//addresses should be change
                this.setCardCustomerBean(customerId, applicationBean, mainBean.getNameOncard());
//                this.setDebitCardCustomerBean(customerId, mainBean, applicationBean.getConfirmCreditLimit());
                this.setCardAccountBean(customerId, mainBean, applicationBean);
                this.setOnlineCardBean(customerId, customer1CardNo, mainBean.getAccountNo(), applicationBean, debitCardTempBean);
                this.setOnlineAccountBean(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);
                this.setOnlineCustomerBean(customerId, customer1CardNo, mainBean.getAccountNo(), applicationBean, customerTempBean);

                if (mainBean.getAccountNo2() != null) {
                    this.setCardAccountBean2(customerId, mainBean, applicationBean);
                    this.setOnlineAccountBean2(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);

                    CardAccountCustomerBean cacbean2 = new CardAccountCustomerBean();
                    cacbean2.setAccountNumber(mainBean.getAccountNo2());
                    cacbean2.setCardNumber(customer1CardNo);
                    cacbean2.setCustomerId(customerId);
                    cacbean2.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean2);

                }
                if (mainBean.getLoyalityRequired().equals("YES")) {
                    this.setCardAccountLoyalityBean(customerId, mainBean, applicationBean);
                    this.setOnlineAccountLoyalityBean(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);

                    CardAccountCustomerBean cacbean3 = new CardAccountCustomerBean();
                    cacbean3.setAccountNumber(loyalityAccNo);
                    cacbean3.setCardNumber(customer1CardNo);
                    cacbean3.setCustomerId(customerId);
                    cacbean3.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean3);

                }

                CardAccountCustomerBean cacbean1 = new CardAccountCustomerBean();
                cacbean1.setAccountNumber(mainBean.getAccountNo());
                cacbean1.setCardNumber(customer1CardNo);
                cacbean1.setCustomerId(customerId);
                if (useCustomer1oldcard) {
                    cacbean1.setPrimaryStatus("NO");
                } else {
                    cacbean1.setPrimaryStatus("YES");
                }

                cacCustomer1List.add(cacbean1);

            } else {
                this.genarateCardNumbers(applicationId, numberFormate, binNumber, sequence1);

                this.getBackendCard(replaceCard);
                this.getOnlineCard(replaceCard);

                this.setReplaceCardBean(customer1CardNo);
                this.setReplaceOnlineCardBean(customer1CardNo);


            }


        }


        if (debitApplicationType.equalsIgnoreCase("J")) {


// if both customers exist..........
            if (iscardCusExsit == true && isJointCusExsit == true) {
                this.getCustomer1DebitCardLst(primarycardCusBean.getCustomerId());
                this.getCustomer2DebitCardLst(jointcardCusBean.getCustomerId());

                if (cacList1 != null) {
                    for (int i = 0; i < cacList1.size(); i++) {
                        if (cacList1.get(i).getCardType().equals(mainBean.getCardType())) {
                            //use custome1 old card    
                            useCustomer1oldcard = true;
                            customer1CardNo = cacList1.get(i).getCardNumber();
                            break;
                        }

                    }
                }

                if (cacList2 != null) {
                    for (int i = 0; i < cacList2.size(); i++) {
                        if (cacList2.get(i).getCardType().equals(mainBean.getCardType())) {
                            //use custome2 old card    
                            useCustomer2oldcard = true;
                            customer2CardNo = cacList2.get(i).getCardNumber();
                            break;
                        }

                    }
                }
                this.genarateCardNumbers(applicationId, numberFormate, binNumber, sequence1);


                //////////////////////////////////////
                this.setCardBean(customerId, customer1CardNo, applicationBean, mainBean.getNameOncard(),
                        debitCardTempBean, cashLimit, mainBean.getAddress1(), mainBean.getAddress2(), mainBean.getAddress3(), mainBean.getCity());//addresses should be change
                this.setCardAccountBean(customerId, mainBean, applicationBean);
                this.setCardCustomerBean(customerId, applicationBean, mainBean.getNameOncard());
//                this.setDebitCardCustomerBean(customerId, mainBean, applicationBean.getConfirmCreditLimit());

                this.setOnlineCardBean(customerId, customer1CardNo, mainBean.getAccountNo(), applicationBean, debitCardTempBean);
                this.setOnlineAccountBean(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);
                this.setOnlineCustomerBean(customerId, customer1CardNo, mainBean.getAccountNo(), applicationBean, customerTempBean);

                if (mainBean.getAccountNo2() != null) {
                    this.setCardAccountBean2(customerId, mainBean, applicationBean);
                    this.setOnlineAccountBean2(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean2 = new CardAccountCustomerBean();
                    cacbean2.setAccountNumber(mainBean.getAccountNo2());
                    cacbean2.setCardNumber(customer1CardNo);
                    cacbean2.setCustomerId(customerId);
                    cacbean2.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean2);
                }
                if (mainBean.getLoyalityRequired().equals("YES")) {
                    this.setCardAccountLoyalityBean(customerId, mainBean, applicationBean);
                    this.setOnlineAccountLoyalityBean(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean3 = new CardAccountCustomerBean();
                    cacbean3.setAccountNumber(loyalityAccNo);
                    cacbean3.setCardNumber(customer1CardNo);
                    cacbean3.setCustomerId(customerId);
                    cacbean3.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean3);
                }


                CardAccountCustomerBean cacbean1 = new CardAccountCustomerBean();
                cacbean1.setAccountNumber(mainBean.getAccountNo());
                cacbean1.setCardNumber(customer1CardNo);
                cacbean1.setCustomerId(customerId);
                if (useCustomer1oldcard) {
                    cacbean1.setPrimaryStatus("NO");
                } else {
                    cacbean1.setPrimaryStatus("YES");
                }
                cacCustomer1List.add(cacbean1);
                //////////////////////////////////////

                this.setCardBean2(jointCustomerId, customer2CardNo, applicationBean, mainBean.getsNameOncard(),
                        debitCardTempBean, cashLimit, mainBean.getAddress1(), mainBean.getAddress2(), mainBean.getAddress3(), mainBean.getCity());//addresses should be change
//                this.setCardAccount2Bean(jointCustomerId, mainBean, applicationBean);
                this.setCardCustomerBean2(jointCustomerId, applicationBean, mainBean.getNameOncard());
//                this.setDebitCardCustomerBean(customerId, mainBean, applicationBean.getConfirmCreditLimit());

                this.setOnlineCardBean2(jointCustomerId, customer2CardNo, mainBean.getAccountNo(), applicationBean, debitCardTempBean);
//                this.setOnlineAccount2Bean(jointCustomerId, customer2CardNo, mainBean, applicationBean, debitAccountTempBean);
                this.setOnlineCustomerBean2(jointCustomerId, customer2CardNo, mainBean.getAccountNo(), applicationBean, customerTempBean);

                if (mainBean.getAccountNo3() != null) {
                    this.setCardAccount2Bean2(jointCustomerId, mainBean, applicationBean);
                    this.setOnlineAccount2Bean2(jointCustomerId, customer2CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean5 = new CardAccountCustomerBean();
                    cacbean5.setAccountNumber(mainBean.getAccountNo2());
                    cacbean5.setCardNumber(customer2CardNo);
                    cacbean5.setCustomerId(jointCustomerId);
                    cacbean5.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean5);
                }
                if (mainBean.getLoyalityRequired().equals("YES")) {
//                    this.setCardAccountLoyalityBean2(jointCustomerId, mainBean, applicationBean);
//                    this.setOnlineAccountLoyalityBean2(jointCustomerId, customer2CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean6 = new CardAccountCustomerBean();
                    cacbean6.setAccountNumber(loyalityAccNo);
                    cacbean6.setCardNumber(customer2CardNo);
                    cacbean6.setCustomerId(jointCustomerId);
                    cacbean6.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean6);
                }

                CardAccountCustomerBean cacbean4 = new CardAccountCustomerBean();
                cacbean4.setAccountNumber(mainBean.getAccountNo());
                cacbean4.setCardNumber(customer2CardNo);
                cacbean4.setCustomerId(jointCustomerId);
                if (useCustomer2oldcard) {
                    cacbean4.setPrimaryStatus("NO");
                } else {
                    cacbean4.setPrimaryStatus("YES");
                }
                cacCustomer1List.add(cacbean4);
                //////////////////////////////////////


            }

            // if customers1 exist..........      
            if (iscardCusExsit == true && isJointCusExsit == false) {
                this.getCustomer1DebitCardLst(primarycardCusBean.getCustomerId());

                if (cacList1 != null) {
                    for (int i = 0; i < cacList1.size(); i++) {
                        if (cacList1.get(i).getCardType().equals(mainBean.getCardType())) {
                            //use custome1 old card    
                            useCustomer1oldcard = true;
                            customer1CardNo = cacList1.get(i).getCardNumber();
                            break;
                        }

                    }
                }

                this.genarateCardNumbers(applicationId, numberFormate, binNumber, sequence1);

                //////////////////////////////////////
                this.setCardBean(customerId, customer1CardNo, applicationBean, mainBean.getNameOncard(),
                        debitCardTempBean, cashLimit, mainBean.getAddress1(), mainBean.getAddress2(), mainBean.getAddress3(), mainBean.getCity());//addresses should be change
                this.setCardAccountBean(customerId, mainBean, applicationBean);
                this.setCardCustomerBean(customerId, applicationBean, mainBean.getNameOncard());
//                this.setDebitCardCustomerBean(customerId, mainBean, applicationBean.getConfirmCreditLimit());

                this.setOnlineCardBean(customerId, customer1CardNo, mainBean.getAccountNo(), applicationBean, debitCardTempBean);
                this.setOnlineAccountBean(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);
                this.setOnlineCustomerBean(customerId, customer1CardNo, mainBean.getAccountNo(), applicationBean, customerTempBean);

                if (mainBean.getAccountNo2() != null) {
                    this.setCardAccountBean2(customerId, mainBean, applicationBean);
                    this.setOnlineAccountBean2(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean2 = new CardAccountCustomerBean();
                    cacbean2.setAccountNumber(mainBean.getAccountNo2());
                    cacbean2.setCardNumber(customer1CardNo);
                    cacbean2.setCustomerId(customerId);
                    cacbean2.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean2);
                }
                if (mainBean.getLoyalityRequired().equals("YES")) {
                    this.setCardAccountLoyalityBean(customerId, mainBean, applicationBean);
                    this.setOnlineAccountLoyalityBean(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean3 = new CardAccountCustomerBean();
                    cacbean3.setAccountNumber(loyalityAccNo);
                    cacbean3.setCardNumber(customer1CardNo);
                    cacbean3.setCustomerId(customerId);
                    cacbean3.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean3);
                }


                CardAccountCustomerBean cacbean1 = new CardAccountCustomerBean();
                cacbean1.setAccountNumber(mainBean.getAccountNo());
                cacbean1.setCardNumber(customer1CardNo);
                cacbean1.setCustomerId(customerId);
                if (useCustomer1oldcard) {
                    cacbean1.setPrimaryStatus("NO");
                } else {
                    cacbean1.setPrimaryStatus("YES");
                }
                cacCustomer1List.add(cacbean1);
                //////////////////////////////////////

                this.setCardBean2(jointCustomerId, customer2CardNo, applicationBean, mainBean.getsNameOncard(),
                        debitCardTempBean, cashLimit, mainBean.getAddress1(), mainBean.getAddress2(), mainBean.getAddress3(), mainBean.getCity());//addresses should be change
//                this.setCardAccount2Bean(jointCustomerId, mainBean, applicationBean);
                this.setCardCustomerBean2(jointCustomerId, applicationBean, mainBean.getNameOncard());
//                this.setDebitCardCustomerBean(customerId, mainBean, applicationBean.getConfirmCreditLimit());

                this.setOnlineCardBean2(jointCustomerId, customer2CardNo, mainBean.getAccountNo(), applicationBean, debitCardTempBean);
//                this.setOnlineAccount2Bean(jointCustomerId, customer2CardNo, mainBean, applicationBean, debitAccountTempBean);
                this.setOnlineCustomerBean2(jointCustomerId, customer2CardNo, mainBean.getAccountNo(), applicationBean, customerTempBean);

                if (mainBean.getAccountNo3() != null) {
                    this.setCardAccount2Bean2(jointCustomerId, mainBean, applicationBean);
                    this.setOnlineAccount2Bean2(jointCustomerId, customer2CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean5 = new CardAccountCustomerBean();
                    cacbean5.setAccountNumber(mainBean.getAccountNo2());
                    cacbean5.setCardNumber(customer2CardNo);
                    cacbean5.setCustomerId(jointCustomerId);
                    cacbean5.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean5);
                }
                if (mainBean.getLoyalityRequired().equals("YES")) {
//                    this.setCardAccountLoyalityBean2(jointCustomerId, mainBean, applicationBean);
//                    this.setOnlineAccountLoyalityBean2(jointCustomerId, customer2CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean6 = new CardAccountCustomerBean();
                    cacbean6.setAccountNumber(loyalityAccNo);
                    cacbean6.setCardNumber(customer2CardNo);
                    cacbean6.setCustomerId(jointCustomerId);
                    cacbean6.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean6);
                }

                CardAccountCustomerBean cacbean4 = new CardAccountCustomerBean();
                cacbean4.setAccountNumber(mainBean.getAccountNo());
                cacbean4.setCardNumber(customer2CardNo);
                cacbean4.setCustomerId(jointCustomerId);
                if (useCustomer2oldcard) {
                    cacbean4.setPrimaryStatus("NO");
                } else {
                    cacbean4.setPrimaryStatus("YES");
                }
                cacCustomer1List.add(cacbean4);
                //////////////////////////////////////
                //////////////////////////////////////


            }

            // if customers2 exist..........         
            if (iscardCusExsit == false && isJointCusExsit == true) {

                this.getCustomer2DebitCardLst(jointcardCusBean.getCustomerId());

                if (cacList2 != null) {
                    for (int i = 0; i < cacList2.size(); i++) {
                        if (cacList2.get(i).getCardType().equals(mainBean.getCardType())) {
                            //use custome2 old card    
                            useCustomer2oldcard = true;
                            customer2CardNo = cacList2.get(i).getCardNumber();
                            break;
                        }

                    }
                }
                this.genarateCardNumbers(applicationId, numberFormate, binNumber, sequence1);

                //////////////////////////////////////
                this.setCardBean(customerId, customer1CardNo, applicationBean, mainBean.getNameOncard(),
                        debitCardTempBean, cashLimit, mainBean.getAddress1(), mainBean.getAddress2(), mainBean.getAddress3(), mainBean.getCity());//addresses should be change
                this.setCardAccountBean(customerId, mainBean, applicationBean);
                this.setCardCustomerBean(customerId, applicationBean, mainBean.getNameOncard());
//                this.setDebitCardCustomerBean(customerId, mainBean, applicationBean.getConfirmCreditLimit());

                this.setOnlineCardBean(customerId, customer1CardNo, mainBean.getAccountNo(), applicationBean, debitCardTempBean);
                this.setOnlineAccountBean(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);
                this.setOnlineCustomerBean(customerId, customer1CardNo, mainBean.getAccountNo(), applicationBean, customerTempBean);

                if (mainBean.getAccountNo2() != null) {
                    this.setCardAccountBean2(customerId, mainBean, applicationBean);
                    this.setOnlineAccountBean2(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean2 = new CardAccountCustomerBean();
                    cacbean2.setAccountNumber(mainBean.getAccountNo2());
                    cacbean2.setCardNumber(customer1CardNo);
                    cacbean2.setCustomerId(customerId);
                    cacbean2.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean2);
                }
                if (mainBean.getLoyalityRequired().equals("YES")) {
                    this.setCardAccountLoyalityBean(customerId, mainBean, applicationBean);
                    this.setOnlineAccountLoyalityBean(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean3 = new CardAccountCustomerBean();
                    cacbean3.setAccountNumber(loyalityAccNo);
                    cacbean3.setCardNumber(customer1CardNo);
                    cacbean3.setCustomerId(customerId);
                    cacbean3.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean3);
                }


                CardAccountCustomerBean cacbean1 = new CardAccountCustomerBean();
                cacbean1.setAccountNumber(mainBean.getAccountNo());
                cacbean1.setCardNumber(customer1CardNo);
                cacbean1.setCustomerId(customerId);
                if (useCustomer1oldcard) {
                    cacbean1.setPrimaryStatus("NO");
                } else {
                    cacbean1.setPrimaryStatus("YES");
                }
                cacCustomer1List.add(cacbean1);
                //////////////////////////////////////

                this.setCardBean2(jointCustomerId, customer2CardNo, applicationBean, mainBean.getsNameOncard(),
                        debitCardTempBean, cashLimit, mainBean.getAddress1(), mainBean.getAddress2(), mainBean.getAddress3(), mainBean.getCity());//addresses should be change
//                this.setCardAccount2Bean(jointCustomerId, mainBean, applicationBean);
                this.setCardCustomerBean2(jointCustomerId, applicationBean, mainBean.getNameOncard());
//                this.setDebitCardCustomerBean(customerId, mainBean, applicationBean.getConfirmCreditLimit());

                this.setOnlineCardBean2(jointCustomerId, customer2CardNo, mainBean.getAccountNo(), applicationBean, debitCardTempBean);
//                this.setOnlineAccount2Bean(jointCustomerId, customer2CardNo, mainBean, applicationBean, debitAccountTempBean);
                this.setOnlineCustomerBean2(jointCustomerId, customer2CardNo, mainBean.getAccountNo(), applicationBean, customerTempBean);

                if (mainBean.getAccountNo3() != null) {
                    this.setCardAccount2Bean2(jointCustomerId, mainBean, applicationBean);
                    this.setOnlineAccount2Bean2(jointCustomerId, customer2CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean5 = new CardAccountCustomerBean();
                    cacbean5.setAccountNumber(mainBean.getAccountNo2());
                    cacbean5.setCardNumber(customer2CardNo);
                    cacbean5.setCustomerId(jointCustomerId);
                    cacbean5.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean5);
                }
                if (mainBean.getLoyalityRequired().equals("YES")) {
//                    this.setCardAccountLoyalityBean2(jointCustomerId, mainBean, applicationBean);
//                    this.setOnlineAccountLoyalityBean2(jointCustomerId, customer2CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean6 = new CardAccountCustomerBean();
                    cacbean6.setAccountNumber(loyalityAccNo);
                    cacbean6.setCardNumber(customer2CardNo);
                    cacbean6.setCustomerId(jointCustomerId);
                    cacbean6.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean6);
                }

                CardAccountCustomerBean cacbean4 = new CardAccountCustomerBean();
                cacbean4.setAccountNumber(mainBean.getAccountNo());
                cacbean4.setCardNumber(customer2CardNo);
                cacbean4.setCustomerId(jointCustomerId);
                if (useCustomer2oldcard) {
                    cacbean4.setPrimaryStatus("NO");
                } else {
                    cacbean4.setPrimaryStatus("YES");
                }
                cacCustomer1List.add(cacbean4);
                //////////////////////////////////////
                //////////////////////////////////////


            }

// if both customers not exist..........        
            if (iscardCusExsit == false && isJointCusExsit == false) {

                this.genarateCardNumbers(applicationId, numberFormate, binNumber, sequence1);

                //////////////////////////////////////
                this.setCardBean(customerId, customer1CardNo, applicationBean, mainBean.getNameOncard(),
                        debitCardTempBean, cashLimit, mainBean.getAddress1(), mainBean.getAddress2(), mainBean.getAddress3(), mainBean.getCity());//addresses should be change
                this.setCardAccountBean(customerId, mainBean, applicationBean);
                this.setCardCustomerBean(customerId, applicationBean, mainBean.getNameOncard());
//                this.setDebitCardCustomerBean(customerId, mainBean, applicationBean.getConfirmCreditLimit());

                this.setOnlineCardBean(customerId, customer1CardNo, mainBean.getAccountNo(), applicationBean, debitCardTempBean);
                this.setOnlineAccountBean(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);
                this.setOnlineCustomerBean(customerId, customer1CardNo, mainBean.getAccountNo(), applicationBean, customerTempBean);

                if (mainBean.getAccountNo2() != null) {
                    this.setCardAccountBean2(customerId, mainBean, applicationBean);
                    this.setOnlineAccountBean2(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean2 = new CardAccountCustomerBean();
                    cacbean2.setAccountNumber(mainBean.getAccountNo2());
                    cacbean2.setCardNumber(customer1CardNo);
                    cacbean2.setCustomerId(customerId);
                    cacbean2.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean2);
                }
                if (mainBean.getLoyalityRequired().equals("YES")) {
                    this.setCardAccountLoyalityBean(customerId, mainBean, applicationBean);
                    this.setOnlineAccountLoyalityBean(customerId, customer1CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean3 = new CardAccountCustomerBean();
                    cacbean3.setAccountNumber(loyalityAccNo);
                    cacbean3.setCardNumber(customer1CardNo);
                    cacbean3.setCustomerId(customerId);
                    cacbean3.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean3);
                }


                CardAccountCustomerBean cacbean1 = new CardAccountCustomerBean();
                cacbean1.setAccountNumber(mainBean.getAccountNo());
                cacbean1.setCardNumber(customer1CardNo);
                cacbean1.setCustomerId(customerId);
                if (useCustomer1oldcard) {
                    cacbean1.setPrimaryStatus("NO");
                } else {
                    cacbean1.setPrimaryStatus("YES");
                }
                cacCustomer1List.add(cacbean1);
                //////////////////////////////////////

                this.setCardBean2(jointCustomerId, customer2CardNo, applicationBean, mainBean.getsNameOncard(),
                        debitCardTempBean, cashLimit, mainBean.getAddress1(), mainBean.getAddress2(), mainBean.getAddress3(), mainBean.getCity());//addresses should be change
//                this.setCardAccount2Bean(jointCustomerId, mainBean, applicationBean);
                this.setCardCustomerBean2(jointCustomerId, applicationBean, mainBean.getNameOncard());
//                this.setDebitCardCustomerBean(customerId, mainBean, applicationBean.getConfirmCreditLimit());

                this.setOnlineCardBean2(jointCustomerId, customer2CardNo, mainBean.getAccountNo(), applicationBean, debitCardTempBean);
//                this.setOnlineAccount2Bean(jointCustomerId, customer2CardNo, mainBean, applicationBean, debitAccountTempBean);
                this.setOnlineCustomerBean2(jointCustomerId, customer2CardNo, mainBean.getAccountNo(), applicationBean, customerTempBean);

                if (mainBean.getAccountNo3() != null) {
                    this.setCardAccount2Bean2(jointCustomerId, mainBean, applicationBean);
                    this.setOnlineAccount2Bean2(jointCustomerId, customer2CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean5 = new CardAccountCustomerBean();
                    cacbean5.setAccountNumber(mainBean.getAccountNo2());
                    cacbean5.setCardNumber(customer2CardNo);
                    cacbean5.setCustomerId(jointCustomerId);
                    cacbean5.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean5);
                }
                if (mainBean.getLoyalityRequired().equals("YES")) {
//                    this.setCardAccountLoyalityBean2(jointCustomerId, mainBean, applicationBean);
//                    this.setOnlineAccountLoyalityBean2(jointCustomerId, customer2CardNo, mainBean, applicationBean, debitAccountTempBean);
                    CardAccountCustomerBean cacbean6 = new CardAccountCustomerBean();
                    cacbean6.setAccountNumber(loyalityAccNo);
                    cacbean6.setCardNumber(customer2CardNo);
                    cacbean6.setCustomerId(jointCustomerId);
                    cacbean6.setPrimaryStatus("NO");
                    cacCustomer1List.add(cacbean6);
                }

                CardAccountCustomerBean cacbean4 = new CardAccountCustomerBean();
                cacbean4.setAccountNumber(mainBean.getAccountNo());
                cacbean4.setCardNumber(customer2CardNo);
                cacbean4.setCustomerId(jointCustomerId);
                if (useCustomer2oldcard) {
                    cacbean4.setPrimaryStatus("NO");
                } else {
                    cacbean4.setPrimaryStatus("YES");
                }
                cacCustomer1List.add(cacbean4);
                //////////////////////////////////////
                //////////////////////////////////////

            }
        }




////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        if (replaceCard.equals("")) {

            success = manager.insertIntoDebitCardTables(cardCustomerBean1, cardCustomerBean2, cardAccBean1, cardAccBean2, cardAccBean4, cardAccLoyalityBean1, cardAccLoyalityBean2, cardBean1, cardBean2, onlineBean1, onlineBean2,
                    onlineAccountBean1, onlineAccountBean2, onlineAccountBean4, onlineAccountLoyalityBean1, onlineAccountLoyalityBean2, onlineCustomerBean1, onlineCustomerBean2, cacCustomer1List, iscardCusExsit, isJointCusExsit, useCustomer1oldcard, useCustomer2oldcard, binNumber, sequence1, applicationId);

        } else {
            success = manager.insertIntoReplaceDebitCardTables(replacecardBean, onlineReplaceCard);

        }


        return success;
    }

    private void getMaxFromCardApplication() throws Exception {
        try {
            manager = new NumberGenarationManager();
            maxCustomerId = manager.getMaxCusIDFromCardCustomer();

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
    }

    public String setCustomerId(String maxId) throws Exception {

        String padLine = "";

        padLine = ISOUtil.zeropad((Integer.parseInt(maxId) + 1) + "", 10);

        return padLine;
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

            int seq = Integer.parseInt(sequence) + 1;
            sequenceNumber = this.getZeropadSeq(Integer.toString(seq), sequence.length());
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

                    int random = this.setRandomNumber(from, to);
                    randomNumber = String.valueOf(random);
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
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        System.out.println("cardNumber.. "+cardNumber);
        return cardNumber;
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

    public int calculate(String cno, int callMethod) {
        //this method use for both calculate
        //check digit and validate card number.
        //if callMethod=0 then it is isValid method(this one not consider)
        //if callMethod=1 then it is setCheckDigit method(this one consider)
        int add = 0;
        int first = 0;
        int second = 0;
        int answaer = 0;
        String reverse = new StringBuffer(cno).reverse().toString(); //add by janaka..
        char[] cnumberChar = new char[cno.length()];// read string one by one and put into this array.
        int[] cnumberInt = new int[cno.length()];//to store char value into int value
        int n = cno.length();
        cnumberChar = reverse.toCharArray();
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

    private String getNumberFormateAndSequenceBinNumber(String binNumber) throws Exception {
        String value = "";
        try {
            manager = new NumberGenarationManager();
            value = manager.getNumberFormateBinNumber(binNumber);
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        return value;
    }

    private CustomerTempBean getCustomerTemplateDetails(String customerTemCode) throws Exception {
        try {
            manager = new NumberGenarationManager();
            customerTempBean = manager.getCustomerTemplateDetails(customerTemCode);

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        return customerTempBean;
    }

    private DebitCardAccountTemplateBean getDebitAccountTemplateDetails(String accountTemCode) throws Exception {
        try {
            manager = new NumberGenarationManager();
            debitAccountTempBean = manager.getDebitAccountTemplateDetails(accountTemCode);

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        return debitAccountTempBean;
    }

    private DebitCardTemplateBean getDebitCardTemplateDetails(String cardTemCode) throws Exception {
        try {
            manager = new NumberGenarationManager();
            debitCardTempBean = manager.getDebitCardTemplateDetails(cardTemCode);

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        return debitCardTempBean;
    }

    private void setCardBean(String customerId, String cno, CardApplicationBean applicationBean, String name, DebitCardTemplateBean cardTempBean, String cashLimit, String ad1, String ad2, String ad3, String ad4) {
        cardBean1 = new CardBean();
        try {



            cardBean1.setAvailableBalence(applicationBean.getConfirmCreditLimit());
            cardBean1.setCardNumber(cno);
            cardBean1.setCardNumberPrintedOnCard(this.getFormatedCardNumber(cno));
            cardBean1.setExprioryDate(this.getExpdate(cardTempBean.getExpPeriod()));
            cardBean1.setIssueDate(this.getIssuedate());
            cardBean1.setCardProduct(applicationBean.getConfirmCardProduct());
            cardBean1.setCardCategory(applicationBean.getCardCategory());
            cardBean1.setCardType(applicationBean.getCardType());
            cardBean1.setCreditLimit("0");
            cardBean1.setCashLimit("0");
            cardBean1.setCashAvailableBalence("0");
            cardBean1.setCustomerId(customerId);
            cardBean1.setEmbossStatus(StatusVarList.NOSTATUS);
            cardBean1.setEncodeStatus(StatusVarList.NOSTATUS);
            cardBean1.setIdNumber(applicationBean.getIdNumber());
            cardBean1.setIdType(applicationBean.getIdType());
            cardBean1.setMainCardNumber(cno);
            cardBean1.setNameOnCard(name);
            cardBean1.setNoGenaratedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardBean1.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardBean1.setPinStatus(StatusVarList.NOSTATUS);
            cardBean1.setPinmailStatus(StatusVarList.NOSTATUS);
            cardBean1.setCardStatus(StatusVarList.INACTIVE_STATUS);
            cardBean1.setPriorityLevel(applicationBean.getPriorityLevelCode());
            cardBean1.setTempotbAmount("0");
            cardBean1.setLoyalityPoint("0");
            cardBean1.setRedeemPoint("0");
            cardBean1.setServiceCode(cardTempBean.getServiceCode());
            cardBean1.setCurenncyCode(cardTempBean.getCurrencyCode());
            cardBean1.setApplicationId(applicationBean.getApplicationId());
            cardBean1.setReissueTresholPeriod(cardTempBean.getReissueThreshPeriod());
            cardBean1.setAddress1(ad1);
            cardBean1.setAddress2(ad2);
            cardBean1.setAddress3(ad3);
            cardBean1.setAddress4(ad4);
            cardBean1.setProductionMode(applicationBean.getProductionMode());
            cardBean1.setCardDomain(applicationBean.getCardDomain());
            cardBean1.setTxnProfileCode(cardTempBean.getTxnProf());
            cardBean1.setCardKey(applicationBean.getCardKey());

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }


    }

    private void setCardCustomerBean(String customerId, CardApplicationBean applicationBean, String name) {
        cardCustomerBean1 = new CardCustomerBean();
        try {
            cardCustomerBean1.setCustomerId(customerId);
            cardCustomerBean1.setCustomerName(name);
            cardCustomerBean1.setCustomerStatus(StatusVarList.CARD_INIT);
            cardCustomerBean1.setCustomerType(applicationBean.getCardCategory());
            cardCustomerBean1.setIdNumber(applicationBean.getIdNumber());
            cardCustomerBean1.setIdType(applicationBean.getIdType());
            cardCustomerBean1.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardCustomerBean1.setRiskProfileCode(customerTempBean.getCusRiskProfile());
            cardCustomerBean1.setCurrencyCode(customerTempBean.getCurrencyCode());
            cardCustomerBean1.setTxnProfileCode(customerTempBean.getTxnProfCode());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

    private void setCardAccountBean(String customerId, DebitPersonalBean mainBean, CardApplicationBean applicationBean) {
        cardAccBean1 = new CardAccountBean();
        try {

            cardAccBean1.setAccountNumber(mainBean.getAccountNo());
//            cardAccBean.setBillingId(deccountTempBean.getBillCycle());
            cardAccBean1.setRiskProfileCode(debitAccountTempBean.getRiskProfileCode());
//            cardAccBean.setCreditLimit(applicationBean.getConfirmCreditLimit());
            cardAccBean1.setCustomerId(customerId);
            cardAccBean1.setLastupdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardAccBean1.setStatus(StatusVarList.ACTIVE_STATUS);
            cardAccBean1.setAccountType(mainBean.getAccNo1Type());
            cardAccBean1.setAccountOwner(DataTypeVarList.ACC_OWNER_BANK);
            cardAccBean1.setPrimaryStatus(StatusVarList.YESSTATUS);
            cardAccBean1.setCardDomain(DataTypeVarList.CARD_DOMAIN_DEBIT);
            cardAccBean1.setTxnProfileCode(debitAccountTempBean.getTransProfileCode());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

    private void setCardAccountBean2(String customerId, DebitPersonalBean mainBean, CardApplicationBean applicationBean) {
        cardAccBean2 = new CardAccountBean();
        try {

            cardAccBean2.setAccountNumber(mainBean.getAccountNo2());
//            cardAccBean2.setBillingId(deccountTempBean.getBillCycle());
            cardAccBean2.setRiskProfileCode(debitAccountTempBean.getRiskProfileCode());
//            cardAccBean2.setCreditLimit(applicationBean.getConfirmCreditLimit());
            cardAccBean2.setCustomerId(customerId);
            cardAccBean2.setLastupdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardAccBean2.setStatus("ACT");
            cardAccBean2.setAccountType(mainBean.getAccNo2Type());
            cardAccBean2.setAccountOwner("BANK");
            cardAccBean2.setPrimaryStatus("NO");
            cardAccBean2.setCardDomain("DEBIT");
            cardAccBean2.setTxnProfileCode(debitAccountTempBean.getTransProfileCode());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

//    private void setCardAccountBeanLst(String customerId, String accno, CardApplicationBean applicationBean) {
//        cardAccBeanLst = new ArrayList<CardAccountBean>();
//
//        try {
//
//            CardAccountBean bean = new CardAccountBean();
//            cardAccBean.setAccountNumber(accno);
////            cardAccBean.setBillingId(deccountTempBean.getBillCycle());
////            cardAccBean.setRiskProfileCod(debitAccountTempBean.getRiskProfileCode());
////            cardAccBean.setCreditLimit(applicationBean.getConfirmCreditLimit());
//            cardAccBean.setCustomerId(customerId);
//            cardAccBean.setLastupdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
//            cardAccBean.setStatus("ACT");
//            cardAccBean.setStatus("ACT");
//        } catch (Exception ex) {
//            LogFileCreator.writeErrorToNumberGenLog(ex);
//        }
//    }
    private void setCardAccountLoyalityBean(String customerId, DebitPersonalBean mainBean, CardApplicationBean applicationBean) {
        cardAccLoyalityBean1 = new CardAccountBean();
        try {


            loyalityAccNo = this.setNewAccountNumber();

            cardAccLoyalityBean1.setAccountNumber(loyalityAccNo);
//            cardAccBean2.setBillingId(deccountTempBean.getBillCycle());
            cardAccLoyalityBean1.setRiskProfileCode(debitAccountTempBean.getRiskProfileCode());
//            cardAccBean2.setCreditLimit(applicationBean.getConfirmCreditLimit());
            cardAccLoyalityBean1.setCustomerId(customerId);
            cardAccLoyalityBean1.setLastupdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardAccLoyalityBean1.setStatus("ACT");
            cardAccLoyalityBean1.setAccountType("LOYL");
            cardAccLoyalityBean1.setAccountOwner("CMS");
            cardAccLoyalityBean1.setPrimaryStatus("NO");
            cardAccLoyalityBean1.setCardDomain("LOYAL");
            cardAccLoyalityBean1.setCardDomain("LOYAL");
            cardAccLoyalityBean1.setLoyalityPoints("0");
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

    private void setOnlineCardBean(String customerid, String cno, String accno, CardApplicationBean applicationBean, DebitCardTemplateBean cardTempBean) {
        try {
            onlineBean1 = new OnlineCardTableBean();

            onlineBean1.setCardNumber(cno);
            onlineBean1.setAccountNumber(accno);
            onlineBean1.setCustomerid(customerid);
            onlineBean1.setEffectdate("");
            onlineBean1.setExpiryDate(this.getExpdate(cardTempBean.getExpPeriod()));//get from CARD template
            onlineBean1.setRiskProfileCode(cardTempBean.getRiskProf());//get from CARD template
            onlineBean1.setCurrencyCode(this.getZeroPadvalue(cardTempBean.getCurrencyCode()));//get from CARD template and zero pad
            onlineBean1.setTxnCount("0");
            onlineBean1.setPinCount("0");
            onlineBean1.setCashTxnCount("0");
            onlineBean1.setTotalTxnAmount("0");
            onlineBean1.setTotalCashTxnAmount("0");
            onlineBean1.setTempCashAmount("0");
            onlineBean1.setTempCreditAmount("0");
            onlineBean1.setTempCashLimitNcr("0");
            onlineBean1.setTempCreditLimitNcr("0");
            onlineBean1.setCreditLimit("0");
            onlineBean1.setCashlimit("0");// catculate value
            onlineBean1.setOtbCash("0");// catculate value
            onlineBean1.setOtbCredit("0");
//            onlineBean1.setPayMode("1");
            onlineBean1.setEod("1");
            onlineBean1.setTliStatus("2");
            onlineBean1.setStatus("2");
            onlineBean1.setLastUpdatedUser(sessionUser.getUserName());
            onlineBean1.setServiceCode(cardTempBean.getServiceCode());
            onlineBean1.setCardDomain("2");
            onlineBean1.setEmvStatus("1");
            onlineBean1.setCardBin(applicationBean.getBinprofile());
            onlineBean1.setCardKey(applicationBean.getCardKey());

//            if (applicationBean.getCardType().equals("VISA")) {
//                onlineBean1.setCardType("1");
//            }
//            if (applicationBean.getCardType().equals("MAST")) {
//                onlineBean1.setCardType("2");
//            }
//            if (applicationBean.getCardType().equals("AMEX")) {
//                onlineBean1.setCardType("3");
//            }
//            if (applicationBean.getCardType().equals("PLUS")) {
//                onlineBean1.setCardType("4");
//            }
//            if (applicationBean.getCardType().equals("MSTR")) {
//                onlineBean1.setCardType("5");
//            }
//            if (applicationBean.getCardType().equals("CARG")) {
//                onlineBean1.setCardType("6");
//            }
//            if (applicationBean.getCardType().equals("CIRR")) {
//                onlineBean1.setCardType("7");
//            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

    private void setOnlineAccountBean(String customerid, String cno, DebitPersonalBean mainBean, CardApplicationBean applicationBean, DebitCardAccountTemplateBean accountTempBean) {
        try {
            onlineAccountBean1 = new OnlineAccountTableBean();

            onlineAccountBean1.setCurrencyCode(this.getZeroPadvalue(accountTempBean.getCurrencyCode()));//get from account template and zeero pad
            onlineAccountBean1.setAccountNumber(mainBean.getAccountNo());
            onlineAccountBean1.setCustomerid(customerid);
            onlineAccountBean1.setRiskProfileCode(accountTempBean.getRiskProfileCode());//get from account template
            onlineAccountBean1.setTxnCount("0");
            onlineAccountBean1.setCashTxnCount("0");
            onlineAccountBean1.setCreditLimit("0");
            onlineAccountBean1.setCashLimit("0");// catculate value
            onlineAccountBean1.setTotalTxnAmount("0");
            onlineAccountBean1.setTotalCashTxnAmount("0");// catculate value
            onlineAccountBean1.setOtbCash("0");// catculate value
            onlineAccountBean1.setOtbCredit("0");
            onlineAccountBean1.setStatus("1");
            onlineAccountBean1.setAccountType(mainBean.getAccNo1Type());
            onlineAccountBean1.setAccountOwner("BANK");//bank
            onlineAccountBean1.setPrimaryStatus("YES");
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

    private void setOnlineCustomerBean(String customerid, String cno, String accno, CardApplicationBean applicationBean, CustomerTempBean customerTempBean) {
        try {
            onlineCustomerBean1 = new OnlineCustomerTableBean();

            onlineCustomerBean1.setCurrencyCode(this.getZeroPadvalue(customerTempBean.getCurrencyCode()));//get from customer template and zero pad
            onlineCustomerBean1.setCustomerid(customerid);
            onlineCustomerBean1.setRiskProfileCode(customerTempBean.getCusRiskProfile());//get from customer template
            onlineCustomerBean1.setTxnCount("0");
            onlineCustomerBean1.setCashTxnCount("0");
            onlineCustomerBean1.setCreditLimit("0");
            onlineCustomerBean1.setCashLimit("0");// catculate value
            onlineCustomerBean1.setTotalTxnAmount("0");
            onlineCustomerBean1.setTotalCashTxnAmount("0");
            onlineCustomerBean1.setOtbCash("0");// catculate value
            onlineCustomerBean1.setOtbCredit("0");
            onlineCustomerBean1.setStatus("1");
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

    private void setOnlineAccountBean2(String customerid, String cno, DebitPersonalBean mainBean, CardApplicationBean applicationBean, DebitCardAccountTemplateBean accountTempBean) {
        try {
            onlineAccountBean2 = new OnlineAccountTableBean();

            onlineAccountBean2.setCurrencyCode(this.getZeroPadvalue(accountTempBean.getCurrencyCode()));//get from account template and zeero pad
            onlineAccountBean2.setAccountNumber(mainBean.getAccountNo2());
            onlineAccountBean2.setCustomerid(customerid);
            onlineAccountBean2.setRiskProfileCode(accountTempBean.getRiskProfileCode());//get from account template
            onlineAccountBean2.setTxnCount("0");
            onlineAccountBean2.setCashTxnCount("0");
            onlineAccountBean2.setCreditLimit("0");
            onlineAccountBean2.setCashLimit("0");// catculate value
            onlineAccountBean2.setTotalTxnAmount("0");
            onlineAccountBean2.setTotalCashTxnAmount("0");// catculate value
            onlineAccountBean2.setOtbCash("0");// catculate value
            onlineAccountBean2.setOtbCredit("0");
            onlineAccountBean2.setStatus("1");
            onlineAccountBean2.setAccountType(mainBean.getAccNo2Type());
            onlineAccountBean2.setAccountOwner("BANK");//bank
            onlineAccountBean2.setPrimaryStatus("NO");
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

    private void setOnlineAccountLoyalityBean(String customerid, String cno, DebitPersonalBean mainBean, CardApplicationBean applicationBean, DebitCardAccountTemplateBean accountTempBean) {
        try {
            onlineAccountLoyalityBean1 = new OnlineAccountTableBean();

            onlineAccountLoyalityBean1.setCurrencyCode(this.getZeroPadvalue(accountTempBean.getCurrencyCode()));//get from account template and zeero pad
            onlineAccountLoyalityBean1.setAccountNumber(loyalityAccNo);
            onlineAccountLoyalityBean1.setCustomerid(customerid);
            onlineAccountLoyalityBean1.setRiskProfileCode(accountTempBean.getRiskProfileCode());//get from account template
            onlineAccountLoyalityBean1.setTxnCount("0");
            onlineAccountLoyalityBean1.setCashTxnCount("0");
            onlineAccountLoyalityBean1.setCreditLimit("0");
            onlineAccountLoyalityBean1.setCashLimit("0");// catculate value
            onlineAccountLoyalityBean1.setTotalTxnAmount("0");
            onlineAccountLoyalityBean1.setTotalCashTxnAmount("0");// catculate value
            onlineAccountLoyalityBean1.setOtbCash("0");// catculate value
            onlineAccountLoyalityBean1.setOtbCredit("0");
            onlineAccountLoyalityBean1.setStatus("1");
            onlineAccountLoyalityBean1.setAccountType("LOYL");
            onlineAccountLoyalityBean1.setAccountOwner("CMS");//cms
            onlineAccountLoyalityBean1.setPrimaryStatus("NO");
            onlineAccountLoyalityBean1.setLoyalityPoints("0");
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
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

    private String getZeroPadvalue(String currencyNumCode) throws Exception {
        String value = "";
        try {
            System.out.println("currencyNumCode .. "+currencyNumCode);
            if (currencyNumCode.length() == 3) {
                System.out.println("currencyNumCode .. "+currencyNumCode);
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

    private String setNewAccountNumber() throws Exception {
        String newAccontno = "";
        try {
            manager = new NumberGenarationManager();
            String accno = manager.getMaxAccnoFromCardAccount();

            if (accno.length() < 12) {
                newAccontno = ISOUtil.zeropad((Long.parseLong(accno) + 1) + "", 12);
            } else {
                newAccontno = String.valueOf((Long.parseLong(accno) + 1));
            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        return newAccontno;
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

    private void getCustomer1DebitCardLst(String customerId) throws Exception {
        try {
            manager = new NumberGenarationManager();
            cacList1 = manager.getCustomerDebitCardLst(customerId);


        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }

    }

    private void getCustomer2DebitCardLst(String customerId) throws Exception {
        try {
            manager = new NumberGenarationManager();
            cacList2 = manager.getCustomerDebitCardLst(customerId);


        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
    }

    private void genarateCardNumbers(String applicationId, String numberFormate, String binNumber, String sequence1) throws Exception {
        try {
            if (debitApplicationType.equals("P")) {

                if (useCustomer1oldcard == false) {
                    customer1CardNo = this.genarateCardNumber(applicationId, numberFormate, binNumber, sequence1);
                }
                if (replacecardBean != null) {
                    customer1CardNo = this.genarateCardNumber(applicationId, numberFormate, binNumber, sequence1);
                }

            }
            if (debitApplicationType.equals("J")) {
                if (useCustomer1oldcard == false && useCustomer2oldcard == false) {
                    customer1CardNo = this.genarateCardNumber(applicationId, numberFormate, binNumber, sequence1);
                    String sequence2 = this.getZeropadSeq(String.valueOf(Integer.parseInt(sequence1) + 1), sequence1.length());
                    customer2CardNo = this.genarateCardNumber(applicationId, numberFormate, binNumber, sequence2);
//                    customer2CardNo = this.genarateCardNumber(applicationId, numberFormate, binNumber, String.valueOf(Integer.parseInt(sequence1) + 1));
                }
                if (useCustomer1oldcard == true && useCustomer2oldcard == false) {
                    customer2CardNo = this.genarateCardNumber(applicationId, numberFormate, binNumber, sequence1);
                }
                if (useCustomer1oldcard == false && useCustomer2oldcard == true) {
                    customer1CardNo = this.genarateCardNumber(applicationId, numberFormate, binNumber, sequence1);
                }


            }
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
    }

    private void setCardBean2(String jointCustomerId, String customer2CardNo, CardApplicationBean applicationBean, String sNameOncard, DebitCardTemplateBean debitCardTempBean, String cashLimit, String address1, String address2, String address3, String city) {
        cardBean2 = new CardBean();
        try {



            cardBean2.setAvailableBalence(applicationBean.getConfirmCreditLimit());
            cardBean2.setCardNumber(customer2CardNo);
            cardBean2.setCardNumberPrintedOnCard(this.getFormatedCardNumber(customer2CardNo));
            cardBean2.setExprioryDate(this.getExpdate(debitCardTempBean.getExpPeriod()));
            cardBean2.setIssueDate(this.getIssuedate());
            cardBean2.setCardProduct(applicationBean.getConfirmCardProduct());
            cardBean2.setCardCategory(applicationBean.getCardCategory());
            cardBean2.setCardType(applicationBean.getCardType());
            cardBean2.setCreditLimit("0");
            cardBean2.setCashLimit("0");
            cardBean2.setCashAvailableBalence("0");
            cardBean2.setCustomerId(jointCustomerId);
            cardBean2.setEmbossStatus(StatusVarList.NOSTATUS);
            cardBean2.setEncodeStatus(StatusVarList.NOSTATUS);
            cardBean2.setIdNumber(applicationBean.getIdNumber());
            cardBean2.setIdType(applicationBean.getIdType());
            cardBean2.setMainCardNumber(customer2CardNo);
            cardBean2.setNameOnCard(sNameOncard);
            cardBean2.setNoGenaratedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardBean2.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardBean2.setPinStatus(StatusVarList.NOSTATUS);
            cardBean2.setPinmailStatus(StatusVarList.NOSTATUS);
            cardBean2.setCardStatus(StatusVarList.INACTIVE_STATUS);
            cardBean2.setPriorityLevel(applicationBean.getPriorityLevelCode());
            cardBean2.setTempotbAmount("0");
            cardBean2.setLoyalityPoint("0");
            cardBean2.setRedeemPoint("0");
            cardBean2.setServiceCode(debitCardTempBean.getServiceCode());
            cardBean2.setCurenncyCode(debitCardTempBean.getCurrencyCode());
            cardBean2.setApplicationId(applicationBean.getApplicationId());
            cardBean2.setReissueTresholPeriod(debitCardTempBean.getReissueThreshPeriod());
            cardBean2.setAddress1(address1);
            cardBean2.setAddress2(address2);
            cardBean2.setAddress3(address3);
            cardBean2.setAddress4(city);
            cardBean2.setProductionMode(applicationBean.getProductionMode());
            cardBean2.setCardDomain(applicationBean.getCardDomain());
            cardBean2.setTxnProfileCode(debitCardTempBean.getTxnProf());
            cardBean2.setCardKey(applicationBean.getCardKey());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }

    }

//    private void setCardAccount2Bean(String jointCustomerId, DebitPersonalBean mainBean, CardApplicationBean applicationBean) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
    private void setCardCustomerBean2(String jointCustomerId, CardApplicationBean applicationBean, String nameOncard) {
        cardCustomerBean2 = new CardCustomerBean();
        try {
            cardCustomerBean2.setCustomerId(jointCustomerId);
            cardCustomerBean2.setCustomerName(nameOncard);
            cardCustomerBean2.setCustomerStatus(StatusVarList.CARD_INIT);
            cardCustomerBean2.setCustomerType(applicationBean.getCardCategory());
            cardCustomerBean2.setIdNumber(applicationBean.getIdNumber());
            cardCustomerBean2.setIdType(applicationBean.getIdType());
            cardCustomerBean2.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardCustomerBean2.setRiskProfileCode(customerTempBean.getCusRiskProfile());
            cardCustomerBean2.setCurrencyCode(customerTempBean.getCurrencyCode());
            cardCustomerBean2.setTxnProfileCode(customerTempBean.getTxnProfCode());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

    private void setOnlineCardBean2(String jointCustomerId, String customer2CardNo, String accountNo, CardApplicationBean applicationBean, DebitCardTemplateBean debitCardTempBean) {
        try {
            onlineBean2 = new OnlineCardTableBean();

            onlineBean2.setCardNumber(customer2CardNo);
            onlineBean2.setCustomerid(jointCustomerId);
            onlineBean2.setEffectdate("");
            onlineBean2.setExpiryDate(this.getExpdate(debitCardTempBean.getExpPeriod()));//get from CARD template
            onlineBean2.setRiskProfileCode(debitCardTempBean.getRiskProf());//get from CARD template
            onlineBean2.setCurrencyCode(this.getZeroPadvalue(debitCardTempBean.getCurrencyCode()));//get from CARD template and zero pad
            onlineBean2.setTxnCount("0");
            onlineBean2.setPinCount("0");
            onlineBean2.setCashTxnCount("0");
            onlineBean2.setTotalTxnAmount("0");
            onlineBean2.setTotalCashTxnAmount("0");
            onlineBean2.setTempCashAmount("0");
            onlineBean2.setTempCreditAmount("0");
            onlineBean2.setTempCashLimitNcr("0");
            onlineBean2.setTempCreditLimitNcr("0");
            onlineBean2.setCreditLimit("0");
            onlineBean2.setCashlimit("0");// catculate value
            onlineBean2.setOtbCash("0");// catculate value
            onlineBean2.setOtbCredit("0");
//            onlineBean2.setPayMode("1");
            onlineBean2.setEod("1");
            onlineBean2.setTliStatus("2");
            onlineBean2.setStatus("1");
            onlineBean2.setLastUpdatedUser(sessionUser.getUserName());
            onlineBean2.setServiceCode(debitCardTempBean.getServiceCode());
            onlineBean2.setCardDomain("2");
            onlineBean2.setEmvStatus("1");
            onlineBean2.setCardBin(applicationBean.getBinprofile());
            onlineBean2.setCardKey(applicationBean.getCardKey());

//            if (applicationBean.getCardType().equals("VISA")) {
//                onlineBean2.setCardType("1");
//            }
//            if (applicationBean.getCardType().equals("MAST")) {
//                onlineBean2.setCardType("2");
//            }
//            if (applicationBean.getCardType().equals("AMEX")) {
//                onlineBean2.setCardType("3");
//            }
//            if (applicationBean.getCardType().equals("PLUS")) {
//                onlineBean2.setCardType("4");
//            }
//            if (applicationBean.getCardType().equals("MSTR")) {
//                onlineBean2.setCardType("5");
//            }
//            if (applicationBean.getCardType().equals("CARG")) {
//                onlineBean2.setCardType("6");
//            }
//            if (applicationBean.getCardType().equals("CIRR")) {
//                onlineBean2.setCardType("7");
//            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

//    private void setOnlineAccount2Bean(String jointCustomerId, String customer2CardNo, DebitPersonalBean mainBean, CardApplicationBean applicationBean, DebitCardAccountTemplateBean debitAccountTempBean) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
    private void setOnlineCustomerBean2(String jointCustomerId, String customer2CardNo, String accountNo, CardApplicationBean applicationBean, CustomerTempBean customerTempBean) {
        try {
            onlineCustomerBean2 = new OnlineCustomerTableBean();

            onlineCustomerBean2.setCurrencyCode(this.getZeroPadvalue(customerTempBean.getCurrencyCode()));//get from customer template and zero pad
            onlineCustomerBean2.setCustomerid(jointCustomerId);
            onlineCustomerBean2.setRiskProfileCode(customerTempBean.getCusRiskProfile());//get from customer template
            onlineCustomerBean2.setTxnCount("0");
            onlineCustomerBean2.setCashTxnCount("0");
            onlineCustomerBean2.setCreditLimit("0");
            onlineCustomerBean2.setCashLimit("0");// catculate value
            onlineCustomerBean2.setTotalTxnAmount("0");
            onlineCustomerBean2.setTotalCashTxnAmount("0");
            onlineCustomerBean2.setOtbCash("0");// catculate value
            onlineCustomerBean2.setOtbCredit("0");
            onlineCustomerBean2.setStatus("1");
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

    private void setCardAccount2Bean2(String jointCustomerId, DebitPersonalBean mainBean, CardApplicationBean applicationBean) {
        cardAccBean4 = new CardAccountBean();
        try {

            cardAccBean4.setAccountNumber(mainBean.getAccountNo3());
//            cardAccBean4.setBillingId(deccountTempBean.getBillCycle());
            cardAccBean4.setRiskProfileCode(debitAccountTempBean.getRiskProfileCode());
//            cardAccBean4.setCreditLimit(applicationBean.getConfirmCreditLimit());
            cardAccBean4.setCustomerId(jointCustomerId);
            cardAccBean4.setLastupdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
            cardAccBean4.setStatus("ACT");
            cardAccBean4.setAccountType(mainBean.getAccNo3Type());
            cardAccBean4.setAccountOwner("BANK");
            cardAccBean4.setPrimaryStatus("NO");
            cardAccBean4.setCardDomain("DEBIT");
            cardAccBean4.setTxnProfileCode(debitAccountTempBean.getTransProfileCode());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

    private void setOnlineAccount2Bean2(String jointCustomerId, String customer2CardNo, DebitPersonalBean mainBean, CardApplicationBean applicationBean, DebitCardAccountTemplateBean debitAccountTempBean) {
        try {
            onlineAccountBean4 = new OnlineAccountTableBean();

            onlineAccountBean4.setCurrencyCode(this.getZeroPadvalue(debitAccountTempBean.getCurrencyCode()));//get from account template and zeero pad
            onlineAccountBean4.setAccountNumber(mainBean.getAccountNo3());
            onlineAccountBean4.setCustomerid(jointCustomerId);
            onlineAccountBean4.setRiskProfileCode(debitAccountTempBean.getRiskProfileCode());//get from account template
            onlineAccountBean4.setTxnCount("0");
            onlineAccountBean4.setCashTxnCount("0");
            onlineAccountBean4.setCreditLimit("0");
            onlineAccountBean4.setCashLimit("0");// catculate value
            onlineAccountBean4.setTotalTxnAmount("0");
            onlineAccountBean4.setTotalCashTxnAmount("0");// catculate value
            onlineAccountBean4.setOtbCash("0");// catculate value
            onlineAccountBean4.setOtbCredit("0");
            onlineAccountBean4.setStatus("1");
            onlineAccountBean4.setAccountType(mainBean.getAccNo3Type());
            onlineAccountBean4.setAccountOwner("BANK");
            onlineAccountBean4.setPrimaryStatus("NO");
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
        }
    }

    private void getBackendCard(String replaceCard) throws Exception {
        try {
            manager = new NumberGenarationManager();
            replacecardBean = manager.getBackendCard(replaceCard);
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
    }

    private void getOnlineCard(String replaceCard) throws Exception {
        try {
            manager = new NumberGenarationManager();
            onlineReplaceCard = manager.getOnlineCard(replaceCard);
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
    }

//    private void setCardAccountLoyalityBean2(String jointCustomerId, DebitPersonalBean mainBean, CardApplicationBean applicationBean) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    private void setOnlineAccountLoyalityBean2(String jointCustomerId, String customer2CardNo, DebitPersonalBean mainBean, CardApplicationBean applicationBean, DebitCardAccountTemplateBean debitAccountTempBean) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
    private void setReplaceCardBean(String customer1CardNo) {
        replacecardBean.setCardNumber(customer1CardNo);
    }

    private void setReplaceOnlineCardBean(String customer1CardNo) {
        onlineReplaceCard.setCardNumber(customer1CardNo);
    }
}
