/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.EmbossFileFormatBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.EmbossFileFormatMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CardScoreManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.exception.ValidateException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
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
 * @author badrika
 */
public class UpdateEmbossFileFormatMgtServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private EmbossFileFormatMgtManager effmManager;
    private List<EmbossFileFormatBean> formatList;
    private EmbossFileFormatBean embean = null;
    private String errorMessage;
    private RequestDispatcher rd;
    private String url = "/administrator/controlpanel/systemconfigmgt/embossfileformat.jsp";
    private SystemAuditBean systemAuditBean;
    private String recordFormat = "";
    private String[] fieldArray;
    private int reclen;
    private String newValue = "";

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");

        try {
            request.setAttribute("operationtype", "update");
            try {

                HttpSession sessionObj = request.getSession(false);
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();
                try {
                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(),
                            sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }
                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();

                }

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.UPDATE)) {
                throw new AccessDeniedException();
            }
            //////////////////
            this.getAllEmbossFileFormats();

            request.setAttribute("formatList", formatList);

            String oldValue = request.getParameter("oldvalue");

            this.setValuesToBean(request);

            int success = -1;

            if (this.validateUserInput(embean, request)) {

                try {
                    recordFormat = this.getRecordFormat();

                    //TO TAKE LENGTH
                    fieldArray = this.makeFieldArray();
                    reclen = this.getRecordLength(fieldArray);
                    int finalreclen = reclen + Integer.parseInt(embean.getRecordCount()) / 2;
                    String recLenth = String.valueOf(finalreclen);
                    //

                    String rfnew = recordFormat.replace("|", ",");

                    newValue = embean.getFormatCode() + "|" + embean.getDescription() + "|" + embean.getRecordCount() + "|"
                            + embean.getStatus() + "|" + rfnew;

                    this.setAudittraceValue(request, oldValue, newValue);

                    success = this.updateEmbossFileFormat(recordFormat, recLenth);

                } catch (Exception ex) {
                    throw ex;
                }



            } else {
                request.setAttribute("embean", embean);
                request.setAttribute("errorMsg", errorMessage);
            }
            if (success > 0) {

                this.getAllEmbossFileFormats();
                request.setAttribute("formatList", formatList);
                rd = request.getRequestDispatcher("/LoadEmbossFileFormatMgtServlet");
                request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.FORMAT_UPDATE_SUCCESS);

            } else {
                request.setAttribute("embean", embean);
                rd = request.getRequestDispatcher(url);
            }

            ///////////////////

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadEmbossFileFormatMgtServlet");

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (ValidateException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR,
                    CardScoreManager.getScoreCardInstance().getErrorMsg());
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
        }
    }

    /**
     * to check the validity of a task of logged user
     * @param userTaskList
     * @param task
     * @return
     * @throws Exception 
     */
    private boolean isValidTaskByUser(List<String> userTaskList, String task) throws Exception {
        boolean isValidTask = false;
        try {
            for (String usertask : userTaskList) {
                if (task.equals(usertask)) {
                    isValidTask = true;
                }
            }
            return isValidTask;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to update a record
     * @param recformat
     * @return
     * @throws Exception 
     */
    public int updateEmbossFileFormat(String recformat, String reclength) throws Exception {
        try {
            effmManager = new EmbossFileFormatMgtManager();
            int i = effmManager.updateEmbossFileFormat(systemAuditBean, embean, recformat, reclength);
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * to get all formats details to the user view
     * @throws Exception 
     */
    private void getAllEmbossFileFormats() throws Exception {
        try {

            effmManager = new EmbossFileFormatMgtManager();
            formatList = effmManager.getAllEmbossFileFormats();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to set values to the bean
     * @param request
     * @throws Exception 
     */
    private void setValuesToBean(HttpServletRequest request) throws Exception {
        try {
            embean = new EmbossFileFormatBean();
            embean.setFormatCode(request.getParameter("formatCode"));
            embean.setDescription(request.getParameter("description"));
            embean.setRecordCount(request.getParameter("recordCount"));
            embean.setStatus(request.getParameter("statuscode"));


            //set record format feids data
            embean.setRfFeild1(request.getParameter("1"));
            embean.setRfFeild2(request.getParameter("3"));
            embean.setRfFeild3(request.getParameter("5"));
            embean.setRfFeild4(request.getParameter("7"));
            embean.setRfFeild5(request.getParameter("9"));
            embean.setRfFeild6(request.getParameter("11"));
            embean.setRfFeild7(request.getParameter("13"));
            embean.setRfFeild8(request.getParameter("15"));
            embean.setRfFeild9(request.getParameter("17"));
            embean.setRfFeild10(request.getParameter("19"));
            embean.setRfFeild11(request.getParameter("21"));
            embean.setRfFeild12(request.getParameter("23"));
            embean.setRfFeild13(request.getParameter("25"));
            embean.setRfFeild14(request.getParameter("27"));
            embean.setRfFeild15(request.getParameter("29"));
            embean.setRfFeild16(request.getParameter("31"));
            embean.setRfFeild17(request.getParameter("33"));
            embean.setRfFeild18(request.getParameter("35"));
            embean.setRfFeild19(request.getParameter("37"));
            embean.setRfFeild20(request.getParameter("39"));
            embean.setRfFeild21(request.getParameter("41"));
            embean.setRfFeild22(request.getParameter("43"));
            embean.setRfFeild23(request.getParameter("45"));
            embean.setRfFeild24(request.getParameter("47"));
            embean.setRfFeild25(request.getParameter("49"));
            embean.setRfFeild26(request.getParameter("51"));
            embean.setRfFeild27(request.getParameter("53"));
            embean.setRfFeild28(request.getParameter("55"));
            embean.setRfFeild29(request.getParameter("57"));
            embean.setRfFeild30(request.getParameter("59"));
            embean.setRfFeild31(request.getParameter("61"));
            embean.setRfFeild32(request.getParameter("63"));
            embean.setRfFeild33(request.getParameter("65"));
            embean.setRfFeild34(request.getParameter("67"));
            embean.setRfFeild35(request.getParameter("69"));
            embean.setRfFeild36(request.getParameter("71"));
            embean.setRfFeild37(request.getParameter("73"));
            embean.setRfFeild38(request.getParameter("75"));
            embean.setRfFeild39(request.getParameter("77"));
            embean.setRfFeild40(request.getParameter("79"));
            

            //set record format seperators
            embean.setRfSeperator1(request.getParameter("2"));
            embean.setRfSeperator2(request.getParameter("4"));
            embean.setRfSeperator3(request.getParameter("6"));
            embean.setRfSeperator4(request.getParameter("8"));
            embean.setRfSeperator5(request.getParameter("10"));
            embean.setRfSeperator6(request.getParameter("12"));
            embean.setRfSeperator7(request.getParameter("14"));
            embean.setRfSeperator8(request.getParameter("16"));
            embean.setRfSeperator9(request.getParameter("18"));
            embean.setRfSeperator10(request.getParameter("20"));
            embean.setRfSeperator11(request.getParameter("22"));
            embean.setRfSeperator12(request.getParameter("24"));
            embean.setRfSeperator13(request.getParameter("26"));
            embean.setRfSeperator14(request.getParameter("28"));
            embean.setRfSeperator15(request.getParameter("30"));
            embean.setRfSeperator16(request.getParameter("32"));
            embean.setRfSeperator17(request.getParameter("34"));
            embean.setRfSeperator18(request.getParameter("36"));
            embean.setRfSeperator19(request.getParameter("38"));
            embean.setRfSeperator20(request.getParameter("40"));
            embean.setRfSeperator21(request.getParameter("42"));
            embean.setRfSeperator22(request.getParameter("44"));
            embean.setRfSeperator23(request.getParameter("46"));
            embean.setRfSeperator24(request.getParameter("48"));
            embean.setRfSeperator25(request.getParameter("50"));
            embean.setRfSeperator26(request.getParameter("52"));
            embean.setRfSeperator27(request.getParameter("54"));
            embean.setRfSeperator28(request.getParameter("56"));
            embean.setRfSeperator29(request.getParameter("58"));
            embean.setRfSeperator30(request.getParameter("60"));
            embean.setRfSeperator31(request.getParameter("62"));
            embean.setRfSeperator32(request.getParameter("64"));
            embean.setRfSeperator33(request.getParameter("66"));
            embean.setRfSeperator34(request.getParameter("68"));
            embean.setRfSeperator35(request.getParameter("70"));
            embean.setRfSeperator36(request.getParameter("72"));
            embean.setRfSeperator37(request.getParameter("74"));
            embean.setRfSeperator38(request.getParameter("76"));
            embean.setRfSeperator39(request.getParameter("78"));
            embean.setRfSeperator40(request.getParameter("80"));


            //////////////////////////////////////////
            embean.setLastUpdatedTime(new Date());
            embean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * to get the record format by fields and separators
     * @return 
     */
    public String getRecordFormat() throws Exception {

        String array[] = new String[40];
        String rf = "";
        try {

            array[0] = embean.getRfFeild1() + "|" + embean.getRfSeperator1();
            array[1] = embean.getRfFeild2() + "|" + embean.getRfSeperator2();
            array[2] = embean.getRfFeild3() + "|" + embean.getRfSeperator3();
            array[3] = embean.getRfFeild4() + "|" + embean.getRfSeperator4();
            array[4] = embean.getRfFeild5() + "|" + embean.getRfSeperator5();
            array[5] = embean.getRfFeild6() + "|" + embean.getRfSeperator6();
            array[6] = embean.getRfFeild7() + "|" + embean.getRfSeperator7();
            array[7] = embean.getRfFeild8() + "|" + embean.getRfSeperator8();
            array[8] = embean.getRfFeild9() + "|" + embean.getRfSeperator9();
            array[9] = embean.getRfFeild10() + "|" + embean.getRfSeperator10();
            array[10] = embean.getRfFeild11() + "|" + embean.getRfSeperator11();
            array[11] = embean.getRfFeild12() + "|" + embean.getRfSeperator12();
            array[12] = embean.getRfFeild13() + "|" + embean.getRfSeperator13();
            array[13] = embean.getRfFeild14() + "|" + embean.getRfSeperator14();
            array[14] = embean.getRfFeild15() + "|" + embean.getRfSeperator15();
            array[15] = embean.getRfFeild16() + "|" + embean.getRfSeperator16();
            array[16] = embean.getRfFeild17() + "|" + embean.getRfSeperator17();
            array[17] = embean.getRfFeild18() + "|" + embean.getRfSeperator18();
            array[18] = embean.getRfFeild19() + "|" + embean.getRfSeperator19();
            array[19] = embean.getRfFeild20() + "|" + embean.getRfSeperator20();
            array[20] = embean.getRfFeild21() + "|" + embean.getRfSeperator21();
            array[21] = embean.getRfFeild22() + "|" + embean.getRfSeperator22();
            array[22] = embean.getRfFeild23() + "|" + embean.getRfSeperator23();
            array[23] = embean.getRfFeild24() + "|" + embean.getRfSeperator24();
            array[24] = embean.getRfFeild25() + "|" + embean.getRfSeperator25();
            array[25] = embean.getRfFeild26() + "|" + embean.getRfSeperator26();
            array[26] = embean.getRfFeild27() + "|" + embean.getRfSeperator27();
            array[27] = embean.getRfFeild28() + "|" + embean.getRfSeperator28();
            array[28] = embean.getRfFeild29() + "|" + embean.getRfSeperator29();
            array[29] = embean.getRfFeild30() + "|" + embean.getRfSeperator30();
            array[30] = embean.getRfFeild31() + "|" + embean.getRfSeperator31();
            array[31] = embean.getRfFeild32() + "|" + embean.getRfSeperator32();
            array[32] = embean.getRfFeild33() + "|" + embean.getRfSeperator33();
            array[33] = embean.getRfFeild34() + "|" + embean.getRfSeperator34();
            array[34] = embean.getRfFeild35() + "|" + embean.getRfSeperator35();
            array[35] = embean.getRfFeild36() + "|" + embean.getRfSeperator36();
            array[36] = embean.getRfFeild37() + "|" + embean.getRfSeperator37();
            array[37] = embean.getRfFeild38() + "|" + embean.getRfSeperator38();
            array[38] = embean.getRfFeild39() + "|" + embean.getRfSeperator39();
            array[39] = embean.getRfFeild40() + "|" + embean.getRfSeperator40();

            int num = Integer.parseInt(embean.getRecordCount()) / 2;

            for (int i = 0; i < num; i++) {
                rf = rf + array[i];
                if (i != (num - 1)) {
                    rf = rf + "|";
                }
            }

        } catch (Exception e) {
            throw e;
        }

        return rf;
    }

    /**
     * to make the detail bean to take format field length
     * @param embean
     * @return 
     */
    private String[] makeFieldArray() throws Exception {
        String farr[] = new String[40];

        try {

            farr[0] = embean.getRfFeild1();
            farr[1] = embean.getRfFeild2();
            farr[2] = embean.getRfFeild3();
            farr[3] = embean.getRfFeild4();
            farr[4] = embean.getRfFeild5();
            farr[5] = embean.getRfFeild6();
            farr[6] = embean.getRfFeild7();
            farr[7] = embean.getRfFeild8();
            farr[8] = embean.getRfFeild9();
            farr[9] = embean.getRfFeild10();
            farr[10] = embean.getRfFeild11();
            farr[11] = embean.getRfFeild12();
            farr[12] = embean.getRfFeild13();
            farr[13] = embean.getRfFeild14();
            farr[14] = embean.getRfFeild15();
            farr[15] = embean.getRfFeild16();
            farr[16] = embean.getRfFeild17();
            farr[17] = embean.getRfFeild18();
            farr[18] = embean.getRfFeild19();
            farr[19] = embean.getRfFeild20();
            farr[20] = embean.getRfFeild21();
            farr[21] = embean.getRfFeild22();
            farr[22] = embean.getRfFeild23();
            farr[23] = embean.getRfFeild24();
            farr[24] = embean.getRfFeild25();
            farr[25] = embean.getRfFeild26();
            farr[26] = embean.getRfFeild27();
            farr[27] = embean.getRfFeild28();
            farr[28] = embean.getRfFeild28();
            farr[29] = embean.getRfFeild30();
            farr[30] = embean.getRfFeild31();
            farr[31] = embean.getRfFeild32();
            farr[32] = embean.getRfFeild33();
            farr[33] = embean.getRfFeild34();
            farr[34] = embean.getRfFeild35();
            farr[35] = embean.getRfFeild36();
            farr[36] = embean.getRfFeild37();
            farr[37] = embean.getRfFeild38();
            farr[38] = embean.getRfFeild39();
            farr[39] = embean.getRfFeild40();

        } catch (Exception e) {
            throw e;
        }

        return farr;
    }

    /**
     * do take format length
     * @param arr
     * @return
     * @throws Exception 
     */
    public int getRecordLength(String[] arr) throws Exception {
        int len = 0;
        try {
            effmManager = new EmbossFileFormatMgtManager();
            len = effmManager.getLength(arr);

        } catch (Exception ex) {
            throw ex;
        }
        return len;
    }

    /**
     * to set audit trace values
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request, String oVal, String nVal) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("formatCode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Update File Format. Format Code: " + uniqueId + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.EMBOSSFILEFORMAT);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue(oVal);
            systemAuditBean.setNewValue(nVal);

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to validate user inputs
     * @param embean
     * @param request
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(EmbossFileFormatBean embean, HttpServletRequest request) throws Exception {
        boolean isValidate = true;

        try {

            if (embean.getFormatCode().contentEquals("") || embean.getFormatCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FORMAT_CODE_EMPTY;
            } else if (!UserInputValidator.isAlphaNumeric(embean.getFormatCode())) {
                isValidate = false;
                errorMessage = MessageVarList.FORMAT_CODE_ALPHANUMERIC;
            } else if (embean.getFormatCode().contentEquals("") || embean.getFormatCode().length() > 6) {
                isValidate = false;
                errorMessage = MessageVarList.FORMAT_CODE_LENGTH;
            } else if (embean.getDescription().contentEquals("") || embean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FORMAT_DES_EMPTY;
            } else if (!UserInputValidator.isCorrectString(embean.getDescription())) {
                isValidate = false;
                errorMessage = MessageVarList.FORMAT_DES_VALID;
            } else if (embean.getDescription().contentEquals("") || embean.getDescription().length() > 64) {
                isValidate = false;
                errorMessage = MessageVarList.FORMAT_DES_LENGTH;
            } else if (embean.getRecordCount().contentEquals("") || embean.getRecordCount().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.RECORD_COUNT_EMPTY;

            } else if (embean.getStatus().contentEquals("") || embean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.RECORD_STATUS_EMPTY;
            } else {
                /*
                for (int j = 1; j <= Integer.parseInt(embean.getRecordCount()); j++) {

                    String filledData = request.getParameter(String.valueOf(j));
                    if (filledData == null || filledData.equals("")) {
                        isValidate = false;
                        errorMessage = MessageVarList.FIELD_SEPERATOR_EMPTY;
                    }

                }
                */
            }

        } catch (Exception ex) {
            isValidate = false;
        }
        return isValidate;
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
            Logger.getLogger(UpdateEmbossFileFormatMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UpdateEmbossFileFormatMgtServlet.class.getName()).log(Level.SEVERE, null, ex);
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
