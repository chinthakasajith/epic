/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodlettergeneration.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterDetailsBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterFieldDetailsBean;
import com.epic.cms.backoffice.eodlettergeneration.businesslogic.LetterGenerationManager;
import com.epic.cms.backoffice.eodlettergeneration.service.HtmlToPdf;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CardScoreManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.exception.ValidateException;
import com.epic.cms.system.util.json.JSONObject;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import com.epic.cms.templatemgt.lettertamplate.bean.LetterBean;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chinthaka_r
 */
public class LetterGenerationServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private Document document = null;
    private FileOutputStream fs = null;
    private LetterGenerationManager letterGenerationManager;
    // private List<String>letterList=null;
    private Map<String, String> letterList = null;
    private List<LetterDetailsBean> searchResultList = null;
    private String tmpCode = null;
    private String identificationNum = null;
    private List<String> fieldNames = null;
    private String actualValue = null;

    private String url = "/backoffice/lettergeneration/eodlettergenerationhome.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            sessionObj = request.getSession(false);
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
            // rd = getServletContext().getRequestDispatcher(url);
            // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String[] searchAray = request.getParameterValues("searchArray[]");
            // String RefNumArray[] = searchAray.split(",");
            // letterList=new ArrayList<String>();
            letterList = new HashMap<>();
            JSONObject jCombo = new JSONObject();// if there are several combo box to load
            //load combo box data
            JSONObject jObject = new JSONObject();
            //get All LetterField Names
            this.getAllFieldNames();
            String tableName = null;
            String fieldName = null;
            String idColumnName = null;
            Map<String, String> param = new HashMap<>();
            for (int i = 0; i < searchAray.length; i++) {
                //get parameter values 
                //LetterFieldDetailsBean paramBean = this.getLetterParams(searchAray[i]);
                //get card details if exist from card table
                //get letter template code and id from letter ref number
                List<String> tmpCodeAndId = this.getLetterTmpCodeAndIdFromRefnumber(searchAray[i]);
                tmpCode = tmpCodeAndId.get(0);
                identificationNum = tmpCodeAndId.get(1);
                //get letter conf values
                LetterBean letterBean = this.getLetterConfValues(tmpCode);
                String[] tmpElements = letterBean.getBody().split("\\|");
                for (String tmpElement : tmpElements) {
                    //check if valid field
                    if (this.checkForValidColumn(tmpElement, fieldNames)) {
                        if (tmpElement.equals("TODAY")) {
                            actualValue = formatDate(new Date());
                        } else {
                            List<String> dyNamicValue = this.getTableValues(tmpElement);
                            if (dyNamicValue.size() > 0) {
                                tableName = dyNamicValue.get(0);
                                fieldName = dyNamicValue.get(1);
                                idColumnName = dyNamicValue.get(2);

                                //get dynamic values
                                actualValue = this.getDynamicValue(tableName, fieldName, idColumnName, identificationNum);
                            } else {
                                actualValue = tmpElement;
                            }
                        }

                        //set param to hashMap
                        if (actualValue != null) {
                            param.put(tmpElement, actualValue);
                        } else {
                            param.put(tmpElement, tmpElement);
                        }

                    }

                }
                String finalLetter = letterGenerationManager.generateLetter(letterBean, "\\|", param);
                //set param to hasmap
//                Map<String, String> param = new HashMap<>();
//                param.put("TODAY", paramBean.getDate());
//                param.put("LETTERREF", paramBean.getRefNo());
//                param.put("NAME", paramBean.getName());
//                param.put("ADDRESS1", paramBean.getAddress1());
//                param.put("ADDRESS2", paramBean.getAddress2());
//                param.put("ADDRESS3", paramBean.getAddress3());
//                param.put("CARDNO", paramBean.getCardNo());
//                param.put("CREDITLIMIT", paramBean.getCerditLimit());

                //String finalLetter = letterGenerationManager.generateLetter(letterBean, "\\|", param);
//                HtmlToPdf htmlToPdf = new HtmlToPdf();
//                htmlToPdf.savePdf(finalLetter);
//                Document document = new Document();
//                try {
//                    response.setContentType("application/pdf");
//                    PdfWriter.getInstance(document, response.getOutputStream());
//                    document.open();
//                    HTMLWorker htmlWorker = new HTMLWorker(document);
//                    htmlWorker.parse(new StringReader(finalLetter));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                document.close();
                //return null;
                //update letter details table
                System.out.println(finalLetter);
                letterList.put(searchAray[i], finalLetter);
                jObject.put(searchAray[i], finalLetter);
            }
            if (letterList != null) {
                this.saveGeneratedLetters(letterList);
                this.getAllLetterDetails();
            }
            jCombo.put("combo1", jObject);
            out.print(jCombo);

            //request.setAttribute("searchList", searchResultList);
            //request.setAttribute("letterList", letterList);
            //rd = request.getRequestDispatcher(url);
            //out.print(letterList);
        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadLetterGenerationServlet");
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
            // rd.forward(request, response);
            out.close();
        }
    }

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

    //get param values
    private LetterFieldDetailsBean getLetterParams(String letterRefId) throws Exception {
        letterGenerationManager = new LetterGenerationManager();
        try {
            return letterGenerationManager.getLetterParamValues(letterRefId);
        } catch (Exception ex) {
            throw ex;
        }
    }

    //get letter conf values
    private LetterBean getLetterConfValues(String tmpCode) throws Exception {
        letterGenerationManager = new LetterGenerationManager();
        try {
            return letterGenerationManager.viewSelectedLetterConf(tmpCode);
        } catch (Exception ex) {
            throw ex;
        }
    }

    //save generated letters in letterdetails table
    private int saveGeneratedLetters(Map<String, String> letterList) throws Exception {
        int count = 0;
        letterGenerationManager = new LetterGenerationManager();
        try {
            count = letterGenerationManager.saveGeneratedLetters(letterList);
        } catch (Exception ex) {
            throw ex;
        }
        return count;

    }

    //get all generated letter details
    private void getAllLetterDetails() throws Exception {
        letterGenerationManager = new LetterGenerationManager();
        try {
            searchResultList = letterGenerationManager.getAllLetterDetails();
        } catch (Exception ex) {
            throw ex;
        }
    }

    //get letter template code from ref number
    private List<String> getLetterTmpCodeAndIdFromRefnumber(String refNum) throws Exception {
        letterGenerationManager = new LetterGenerationManager();
        try {
            return letterGenerationManager.getTmpCodeAndIdFromRefNumber(refNum);
        } catch (Exception ex) {
            throw ex;
        }
    }

    //check for valid field
    private boolean checkForValidColumn(String columnName, List<String> fieldNames) throws SQLException, Exception {
        boolean isColumnName = false;
        try {
            for (String str : fieldNames) {
                if (str.equals(columnName)) {
                    isColumnName = true;
                    // break;
                }
            }

        } catch (Exception ex) {
            throw ex;
        }
        return isColumnName;
    }

    private List<String> getTableValues(String dyNamicValue) throws SQLException, Exception {

        List<String> dyNamicValueList = null;
        try {
            letterGenerationManager = new LetterGenerationManager();
            dyNamicValueList = letterGenerationManager.getTableValues(dyNamicValue);

            return dyNamicValueList;

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        }

    }

    //get all letterFieldNames from letterFieldDetails table
    private void getAllFieldNames() throws Exception {
        try {
            letterGenerationManager = new LetterGenerationManager();
            fieldNames = new ArrayList<>();
            fieldNames = letterGenerationManager.getAllFieldNamesFromLetterFieldDetails();
        } catch (Exception ex) {
            throw ex;
        }
    }

    //get dynamic values
    private String getDynamicValue(String tableName, String fieldName, String idColumnName, String identificationNum) throws SQLException, Exception {
        try {
            String dynamicValue = null;
            letterGenerationManager = new LetterGenerationManager();
            dynamicValue = letterGenerationManager.getDynamicValue(tableName, fieldName, idColumnName, identificationNum);
            return dynamicValue;
        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        }
    }

    //format date

    private String formatDate(Date date) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(date);
        } catch (Exception ex) {
            throw ex;
        }
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
