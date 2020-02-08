/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.cardembossing.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.EmbossFileFormatDetailBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingCardDetailsBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingFileDownloadBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingSearchBean;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.cpmm.cardembossing.businesslogic.CardEmbossingMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author chanuka
 */
public class ProcessGenerateMasterCardServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CardEmbossingMgtManager cardEmbossingManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SystemAuditBean systemAuditBean = null;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private String cards = "''";
    private String rootPath = "";
    private String embossFileFormat = "";
    private String recordToEmboss = "";
    private String cardsOfProduct = "";
    private String dateNow = "";
    private long makeDate = 0;
    private String fileName = "";
    private String cardProduct = "";
    private Calendar currentDate;
    private EmbossFileFormatDetailBean embossDetailBean = null;
    private HashMap<String, String> cardProductList = null;
    private List<CardEmbossingCardDetailsBean> cardEmbossingVISAList = null;
    private List<CardEmbossingCardDetailsBean> cardEmbossingMASTERList = null;
    private List<EmbossFileFormatDetailBean> fileFormatDetailBeanList = null;
    private List<CardEmbossingFileDownloadBean> cardEmbossingFileList = null;
    private CommonFilePathBean commonFilePathBean = null;
    private String url = "/cpmm/cardembossing/cardembossingsearchhome.jsp";
    private String url2 = "/cpmm/cardembossing/cardembossingfiledownload.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
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
                String pageCode = PageVarList.CPCECE;
                String taskCode = TaskVarList.EMFG;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                    request.setAttribute("searchembossingbean", sessionVarlist.getEmbossSearchBean());

                    cards = request.getParameter("mastercards");

                    if (cards == null || cards.isEmpty()) {

                        cards = "''";


////////                        this.getAllMASTERCardToEmboss(sessionVarlist.getEmbossSearchBean(),StatusVarList.CREDIT);
                        this.getAllVISACardToEmboss(sessionVarlist.getEmbossSearchBean(), StatusVarList.CREDIT);



                        for (int idx = 0; idx < cardEmbossingMASTERList.size(); idx++) {


                            cardEmbossingMASTERList.get(idx).setCardNumberHidden("************".concat(cardEmbossingMASTERList.get(idx).getCardNumber().substring(12, 16)));


                        }
                        for (int idx = 0; idx < cardEmbossingVISAList.size(); idx++) {


                            cardEmbossingVISAList.get(idx).setCardNumberHidden("************".concat(cardEmbossingVISAList.get(idx).getCardNumber().substring(12, 16)));

                        }



                        request.setAttribute("cardEmbossingVISAList", cardEmbossingVISAList);
                        request.setAttribute("cardEmbossingMASTERList", cardEmbossingMASTERList);


                        request.setAttribute("errorMsg", MessageVarList.ERROR_GENERATE_EMBOSS_SELECTED);
                        rd = getServletContext().getRequestDispatcher(url);
                    } else {


                        this.getVisaEmbossingFormat("MAST");
                        this.getAllCardProduct("MAST");
                        this.getEmbossFields();


                        currentDate = Calendar.getInstance();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                        dateNow = formatter.format(currentDate.getTime());
                        makeDate = Long.parseLong(dateNow);

                        Set set = cardProductList.entrySet();
                        Iterator itr = set.iterator();



                        while (itr.hasNext()) {

                            Map.Entry me = (Map.Entry) itr.next();

                            cardProduct = me.getKey().toString();
                            recordToEmboss = "";
                            cardsOfProduct = "";

                            List<CardEmbossingCardDetailsBean> masterProductCardList = this.getAllMASTERCardProductToEmboss(me.getKey().toString(), "MAST");

                            for (int i = 0; i < masterProductCardList.size(); i++) {

                                if (i == (masterProductCardList.size() - 1)) {
                                    cardsOfProduct += masterProductCardList.get(i).getCardNumber();

                                } else {
                                    cardsOfProduct += masterProductCardList.get(i).getCardNumber() + ",";
                                }

                                if (embossFileFormat != null) {

                                    String[] formatedRecord = embossFileFormat.split("\\|");

                                    for (int j = 0; j < formatedRecord.length; j++) {

                                        String valueFromTable = "";

                                        if (j % 2 == 0) {

                                            this.getEmbossFieldsBean(formatedRecord[j]);


                                            if (!embossDetailBean.getMatchedTable().equals("NO")) {

                                                valueFromTable = this.getTableFieldValue(embossDetailBean.getMatchedTable(), embossDetailBean.getMatchedField(), masterProductCardList.get(i).getCardNumber());

                                            }
                                            if (formatedRecord[j].equals("SEQUENCENO")) {

                                                valueFromTable = String.valueOf(i + 1);
                                            }

                                            int maxLength = Integer.parseInt(embossDetailBean.getFieldMaxLength());
                                            String fixedDirection = embossDetailBean.getFixedDirection();
                                            String fixedValue = embossDetailBean.getFixedValue();
                                            String paddingChar = embossDetailBean.getPaddingChar();
                                            String paddingDirection = embossDetailBean.getPaddingDirection();
                                            String paddingStatus = embossDetailBean.getPaddingStatus();



                                            if (fixedValue != null) {

                                                if (fixedDirection.equals("RIGHT")) {
                                                    valueFromTable = valueFromTable + fixedValue;
                                                }
                                                if (fixedDirection.equals("LEFT")) {
                                                    valueFromTable = fixedValue + valueFromTable;
                                                }

                                            }

                                            if (paddingStatus.equals("YES")) {

                                                if (paddingDirection.equals("RIGHT")) {


                                                    valueFromTable = StringUtils.rightPad(valueFromTable, maxLength, paddingChar);

                                                }
                                                if (paddingDirection.equals("LEFT")) {

                                                    valueFromTable = StringUtils.leftPad(valueFromTable, maxLength, paddingChar);
                                                }

                                                if (formatedRecord[j + 1].equals("N")) {

                                                    recordToEmboss += valueFromTable;

                                                } else {

                                                    recordToEmboss += valueFromTable + formatedRecord[j + 1];
                                                }

                                            }
                                            if (paddingStatus.equals("NO")) {

                                                if (formatedRecord[j + 1].equals("N")) {

                                                    recordToEmboss += valueFromTable;

                                                } else {

                                                    recordToEmboss += valueFromTable + formatedRecord[j + 1];

                                                }
                                            }

                                        }

                                    }
                                }

                                recordToEmboss = recordToEmboss + "\n";

                            }



                            if (!recordToEmboss.equals("")) {// write to the file only if records exist

                                dateNow = String.valueOf(makeDate++);
                                String osType = this.getOS_Type();

                                if (osType.equals("WINDOWS")) {

                                    String basePath = this.getFilePaths(osType, "EMBOSS");
                                    rootPath = basePath + "MASTERCARD\\" + me.getValue().toString();

                                    fileName = "M".concat(me.getValue().toString().substring(0, 1)) + dateNow + ".txt";

                                    this.createDirectory(rootPath);

                                    File file = new File(rootPath, fileName);

                                    file.createNewFile();


                                    PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));

                                    pw.print(recordToEmboss);
                                    pw.flush();
                                    pw.close();

                                } else if (osType.equals("LINUX")) {

                                    String basePath = this.getFilePaths(osType, "EMBOSS");
                                    rootPath = basePath + "MASTERCARD/" + me.getValue().toString();

                                    fileName = "M".concat(me.getValue().toString().substring(0, 1)) + dateNow + ".txt";

                                    this.createDirectory(rootPath);

                                    File file = new File(rootPath, fileName);

                                    file.createNewFile();


                                    PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));

                                    pw.print(recordToEmboss);
                                    pw.flush();
                                    pw.close();
                                }


                                // update the card table as emboss files are generates only if file is generated...
                                if (cardsOfProduct.equals("")) {
                                    cardsOfProduct = "''";
                                }
                                this.setAudittraceValue(request, "Master", me.getValue().toString());
                                this.insertCardEmbossFileDetails(fileName, sessionUser.getUserName(), "MAST", cardProduct, currentDate);
                                this.updateCardEmbossStatus(systemAuditBean);

                            }// end of checking records exist (IF)

                        }// end of card product loop (FOR)





                        this.getAllFilesToDownload("EMBOSS");

                        sessionVarlist.setCardEmbossingFileList(cardEmbossingFileList);

                        request.setAttribute("successMsg", MessageVarList.SUCCESS_GENERATE_EMBOSS_SELECTED);

                        rd = getServletContext().getRequestDispatcher(url2);





                    }



                    sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);





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

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);



        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_GENERATE_EMBOSS);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
//            out.close();
        }
    }

    ///////////////////////////
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

    private List<CardEmbossingCardDetailsBean> getAllMASTERCardProductToEmboss(String cardProduct, String cardType) throws Exception {
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            cardEmbossingMASTERList = cardEmbossingManager.getAllCardProductToEmbossByCardType(cards, cardProduct, cardType);

            return cardEmbossingMASTERList;

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllCardProduct(String cardType) throws Exception {
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            cardProductList = cardEmbossingManager.getAllCardProduct(cardType);


        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllFilesToDownload(String fileType) throws Exception {
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            cardEmbossingFileList = cardEmbossingManager.getAllFilesToDownload(fileType, currentDate);


        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getVisaEmbossingFormat(String cardType) throws Exception {
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            embossFileFormat = cardEmbossingManager.getVisaEmbossingFormat(cardType);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getEmbossFields() throws Exception {
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            fileFormatDetailBeanList = cardEmbossingManager.getEmbossFields();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getEmbossFieldsBean(String fieldName) throws Exception {
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            embossDetailBean = cardEmbossingManager.getEmbossFieldsBean(fieldName);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private String getTableFieldValue(String tableName, String fieldName, String cardNumber) throws Exception {

        String tableFieldvalue = null;

        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            tableFieldvalue = cardEmbossingManager.getTableFieldValue(tableName, fieldName, cardNumber);

        } catch (Exception ex) {
            throw ex;
        }
        return tableFieldvalue;
    }

    private boolean updateCardEmbossStatus(SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            flag = cardEmbossingManager.updateCardEmbossStatus(cardsOfProduct, fileName, systemAuditBean);
            return flag;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean insertCardEmbossFileDetails(String fileName, String generateUser, String cardType, String cardProduct, Calendar currentDate) throws Exception {
        boolean flag = false;
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            flag = cardEmbossingManager.insertCardEmbossFileDetails(fileName, generateUser, cardType, cardProduct, currentDate);
            return flag;
        } catch (Exception ex) {
            throw ex;
        }
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

    private void createNewfile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private void getAllVISACardToEmboss(CardEmbossingSearchBean searchBean, String cardDomain) throws Exception {
        try {

            cardEmbossingManager = new CardEmbossingMgtManager();
            cardEmbossingVISAList = cardEmbossingManager.getAllVISACardToEmboss(searchBean, cardDomain);

        } catch (Exception ex) {
            throw ex;
        }
    }

////////    private void getAllMASTERCardToEmboss(CardEmbossingSearchBean searchBean,String cardDomain) throws Exception {
////////        try {
////////
////////            cardEmbossingManager = new CardEmbossingMgtManager();
////////            cardEmbossingMASTERList = cardEmbossingManager.getAllMASTERCardToEmboss(searchBean,cardDomain);
////////
////////        } catch (Exception ex) {
////////            throw ex;
////////        }
////////    }
    private void setAudittraceValue(HttpServletRequest request, String cardType, String cardProduct) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Generate " + cardType + " " + cardProduct + " Emboss file by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CPMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.EMBOSSINGMGT);
            systemAuditBean.setPageCode(PageVarList.CPCECE);
            systemAuditBean.setTaskCode(TaskVarList.EMFG);
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

    private String getFilePaths(String osType, String fileType) throws Exception {

        String filePath = "";
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            commonFilePathBean = cardEmbossingManager.getFilePaths(osType);


            if (fileType.equals("EMBOSS")) {
                filePath = commonFilePathBean.getEmbossFile();
            }
            if (fileType.equals("EMV")) {
                filePath = commonFilePathBean.getEmvFile();
            }
            if (fileType.equals("ENCODE")) {
                filePath = commonFilePathBean.getEncodefile();
            }
            if (fileType.equals("STATEMENT")) {
                filePath = commonFilePathBean.getStatement();
            }
            if (fileType.equals("LETTERS")) {
                filePath = commonFilePathBean.getLetters();
            }
            if (fileType.equals("EOD")) {
                filePath = commonFilePathBean.getEodFile();
            }
            if (fileType.equals("MOD")) {
                filePath = commonFilePathBean.getModFile();
            }
            if (fileType.equals("SCANNEDAPPLICATION")) {
                filePath = commonFilePathBean.getScanApplication();
            }
            if (fileType.equals("SCANNEDDOCUMENT")) {
                filePath = commonFilePathBean.getScandocument();
            }


            return filePath;

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
