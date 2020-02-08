package com.epic.cms.backoffice.eodlettergeneration.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.eodlettergeneration.bean.CardRenewalBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterFormatBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterGenerationBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterTemplateBean;
import com.epic.cms.backoffice.eodlettergeneration.businesslogic.GenerateLetterManager;
import com.epic.cms.backoffice.eodlettergeneration.businesslogic.SearchLetterGenerationManager;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CardScoreManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.exception.ValidateException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jeevan
 */
public class GenerateAllLettersServlet extends HttpServlet {

    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private List<String> userTaskList;
    private String url = "/backoffice/lettergeneration/eodlettergenerationhome.jsp";
    private String actualValue = null;
    private String letterInPdf = "";
    private boolean flag = false;
    private RequestDispatcher rd;
    private SearchLetterGenerationManager letterMgr;
    private List<CardRenewalBean> searchResultList = null;
    private List<LetterGenerationBean> processList;
    private CardRenewalBean searchParamBean = null;
    private GenerateLetterManager genLetterMgr;
    private LetterTemplateBean letterTemplate = null;
    private LetterFormatBean letterFormatbean;
    private Document document = null;
    private String isNewLine = "ALYES";
    private String actualTemplateFormat = "";
    private String letterGenaratedCards = "";
    private PdfWriter pdfWriter = null;
    private FileOutputStream fs = null;
    private String processCategoryCode = null;
    private String templateFormat = "";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessionObj = request.getSession(false);
        try {
            //    <editor-fold defaultstate="collapsed" desc=" //session handling............">

            try {
                request.setAttribute("operationtype", "search");
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

                    String pageCode = PageVarList.LETTER_GEN;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
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

            // </editor-fold>  

            rd = getServletContext().getRequestDispatcher(url);

            this.getProcessCategory();
            request.setAttribute("processList", processList);


//
//            if (processList.isEmpty()) {
//                request.setAttribute("errorMsg", "No Results *******");
//            }



            this.setUserInputToBean(request);
            request.setAttribute("letterBean", searchParamBean);

            if (searchParamBean.getProcessCategory().equals("42")) {

                if (null != sessionVarlist.getSearchedLetterBean()) {
                    this.searchLetterGeneration(sessionVarlist.getSearchedLetterBean());
                }

            } else {

                searchResultList = null;
            }
            request.setAttribute("searchList", searchResultList);




            if (searchResultList != null) {

// get serching processing category code from sessionvarlist......as processCategoryCode

                try {
                    processCategoryCode = sessionVarlist.getSearchedLetterBean().getProcessCategory();
                    this.getTemplateFormat(processCategoryCode);
                    templateFormat = letterTemplate.getTemplateFormat();
                } catch (Exception e) {
                    request.setAttribute("errorMsg", "Search letters before generate");
                }



                String[] tempElement = templateFormat.split("~");
                String tableName = null, fieldName = null;

                for (int j = 0; j < tempElement.length; j++) {
                    String singleRecord = tempElement[j];
                    String[] singleRecordData = singleRecord.split("\\|");
                    if (singleRecordData[0].equals("BODY")) {

                        if (j == 0) {
                            actualTemplateFormat += letterTemplate.getBody();
                        } else {
                            actualTemplateFormat += "~" + letterTemplate.getBody();
                        }

                    } else if (singleRecordData[0].equals("TITLE")) {

                        if (j == 0) {
                            actualTemplateFormat += letterTemplate.getTitle();
                        } else {
                            actualTemplateFormat += "~" + letterTemplate.getTitle();
                        }

                    } else {
                        if (j == 0) {
                            actualTemplateFormat += singleRecord;
                        } else {
                            actualTemplateFormat += "~" + singleRecord;
                        }
                    }
                }


                String[] element = actualTemplateFormat.split("~");

                document = new Document();

                this.createDirectory("C:/CMS/LETTERS");

                fs = new FileOutputStream("C:/CMS/LETTERS/" + new java.util.Date().getTime() + ".pdf");

                pdfWriter = PdfWriter.getInstance(document, fs);


                if (searchResultList == null) {
                    request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.NO_LETTERS);
                }
                if (searchResultList != null) {
                    for (int x = 0; x < searchResultList.size(); x++) {

                        if (x == 0) {

                            letterGenaratedCards = searchResultList.get(x).getCardNumber();

                        } else {

                            letterGenaratedCards += "," + searchResultList.get(x).getCardNumber();
                        }



                        for (int i = 0; i < element.length; i++) {
                            String singleRecord = element[i];
                            String[] singleRecordData = singleRecord.split("\\|");
                            String data = singleRecordData[0];
                            // if the column exist
                            if (this.checkForValidColumn(data)) {

                                List<String> dyNamicValue = this.getTableValues(data);
                                if (dyNamicValue.size() > 0) {

                                    tableName = dyNamicValue.get(0);
                                    fieldName = dyNamicValue.get(1);

                                    actualValue = this.getDynamicValue(tableName, fieldName, searchResultList.get(x).getCardNumber());
                                }
                            } else {
                                //actual value is data
                                actualValue = data;
                            }
                            //apply the record format from LETTERFORMATS
                            String recordFormat = singleRecordData[1];
                            //return values to letterFormatbeanList
                            this.getLetterFormat(recordFormat);

                            //return style values from db where format code is recordFormat
                            //
                            Font catFont = null;
                            String fontSize = letterFormatbean.getSize();
                            String fontType = letterFormatbean.getFontType();
                            String fontAlignment = letterFormatbean.getAllignment();
                            String fontStyle = letterFormatbean.getFontStyle();

                            try {
                                if (fontType.equals("COURIER")) {
                                    catFont = new Font(Font.FontFamily.COURIER, Integer.parseInt(fontSize), Integer.parseInt(fontStyle));
                                } else if (fontType.equals("HELVETICA")) {
                                    catFont = new Font(Font.FontFamily.HELVETICA, Integer.parseInt(fontSize), Integer.parseInt(fontStyle));
                                } else if (fontType.equals("TIMES_ROMAN")) {
                                    catFont = new Font(Font.FontFamily.TIMES_ROMAN, Integer.parseInt(fontSize), Integer.parseInt(fontStyle));
                                } else if (fontType.equals("SYMBOL")) {
                                    catFont = new Font(Font.FontFamily.SYMBOL, Integer.parseInt(fontSize), Integer.parseInt(fontStyle));
                                } else if (fontType.equals("ZAPFDINGBATS")) {
                                    catFont = new Font(Font.FontFamily.ZAPFDINGBATS, Integer.parseInt(fontSize), Integer.parseInt(fontStyle));
                                } else if (fontType.equals("UNDEFINED")) {
                                    catFont = new Font(Font.FontFamily.UNDEFINED, Integer.parseInt(fontSize), Integer.parseInt(fontStyle));
                                }
                            } catch (NumberFormatException nfEx) {
                                request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.INVALID_FONT_STYLE);
                                rd = request.getRequestDispatcher(url);
                            }

                            document.open();
                            catFont.setStyle(Font.ITALIC);

                            isNewLine = singleRecordData[2];

                            if (isNewLine.equals("ALYES")) {
                                if (flag) {
                                    flag = false;
                                    letterInPdf += actualValue;

                                    Paragraph senderParagraph = new Paragraph(letterInPdf, catFont);

//                    senderParagraph.setAlignment(Element.ALIGN_RIGHT);
                                    senderParagraph.setAlignment(Integer.parseInt(fontAlignment));

                                    document.add(senderParagraph);
                                    letterInPdf = "";

                                } else {
                                    letterInPdf = actualValue;
                                    Paragraph senderParagraph = new Paragraph(letterInPdf, catFont);

                                    senderParagraph.setAlignment(Integer.parseInt(fontAlignment));
                                    document.add(senderParagraph);
                                    letterInPdf = "";
                                }
                            } else {
                                flag = true;
                                letterInPdf += actualValue;
                                continue;
                            }

                        }
                        document.newPage();
                    }
                }
                /**************************/
                if (!letterGenaratedCards.equals("")) {
                    if (updateAllLetterGeneratedCards(letterGenaratedCards) > 0) {

                        this.searchLetterGeneration(sessionVarlist.getSearchedLetterBean());
                        request.setAttribute("searchList", searchResultList);
                        request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.LETTER_GENERATED_ALL);

                    } else {
                        request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.LETTER_GENERATED_ERROR);
                    }
                } else {
                    request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.NO_LETTER_GENERATE);
                }

            } else {
                request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.SEARCH_ERROR);
            }

            /****/
            if (null != sessionVarlist.getSearchedLetterBean()) {
                sessionVarlist.setSearchedLetterBean(null);
                letterGenaratedCards = "";
            }
            /*****/
        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/GenerateAllLettersServlet");

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (ValidateException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, CardScoreManager.getScoreCardInstance().getErrorMsg());
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {

            actualTemplateFormat = "";
            try {

                if (document.isOpen()) {
                    try {
                        document.close();
                        pdfWriter.close();
                        fs.close();

                    } catch (IOException ee) {
                        request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.FILE_CLOSE_ERROE);
                        rd = request.getRequestDispatcher(url);
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                }
            } catch (NullPointerException ee) {
            }
            rd.forward(request, response);
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

    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {

        boolean isValidTaskAccess = false;

        try {
            systemUserManager = new SystemUserManager();
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

    private void getTemplateFormat(String processCategoryCode) throws SQLException, Exception {
        try {

            genLetterMgr = new GenerateLetterManager();
            letterTemplate = genLetterMgr.getTemplateFormat(processCategoryCode);

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getProcessCategory() throws SQLException, Exception {
        try {

            letterMgr = new SearchLetterGenerationManager();
            processList = letterMgr.getProcessCategory();

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setUserInputToBean(HttpServletRequest request) throws SQLException, Exception {
        try {

            searchParamBean = new CardRenewalBean();

            searchParamBean.setProcessCategory(request.getParameter("processCategory"));
            searchParamBean.setFromDate((request.getParameter("fromdate")));
            searchParamBean.setToDate((request.getParameter("todate")));
        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean checkForValidColumn(String columnName) throws SQLException, Exception {
        try {
            boolean isColumnName = false;

            if (columnName.equals("ADDRESS1") || columnName.equals("ADDRESS2") || columnName.equals("ADDRESS3") || columnName.equals("NAME")
                    || columnName.equals("DATE") || columnName.equals("CARDNUMBER")) {

                isColumnName = true;
            }

            return isColumnName;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private List<String> getTableValues(String dyNamicValue) throws SQLException, Exception {

        List<String> dyNamicValueList = null;
        try {
            genLetterMgr = new GenerateLetterManager();
            dyNamicValueList = genLetterMgr.getTableValues(dyNamicValue);

            return dyNamicValueList;

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        }

    }

    private String getDynamicValue(String tableName, String fieldName, String processCategoryCode) throws SQLException, Exception {
        try {
            String dynamicValue = null;
            genLetterMgr = new GenerateLetterManager();
            dynamicValue = genLetterMgr.getDynamicValue(tableName, fieldName, processCategoryCode);
            return dynamicValue;
        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getLetterFormat(String formatCode) throws Exception {
        try {

            genLetterMgr = new GenerateLetterManager();
            letterFormatbean = genLetterMgr.getLetterFormat(formatCode);

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void searchLetterGeneration(CardRenewalBean searchBean) throws SQLException, Exception {
        try {

            letterMgr = new SearchLetterGenerationManager();
            searchResultList = letterMgr.searchLetterGeneration(searchBean);

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private int updateAllLetterGeneratedCards(String cardNumbers) throws SQLException, Exception {
        try {

            genLetterMgr = new GenerateLetterManager();
            int i = genLetterMgr.updateAllLetterGeneratedCards(cardNumbers);
            return i;
        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void createDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
