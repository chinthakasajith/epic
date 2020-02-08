/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.camm.numbergeneration.servlet;

import com.epic.cms.admin.camm.numbergeneration.bean.NumberFormatBean;
import com.epic.cms.admin.camm.numbergeneration.bean.NumberFormatFieldBean;
import com.epic.cms.admin.camm.numbergeneration.bean.NumberGenerationFormatBean;
import com.epic.cms.admin.camm.numbergeneration.businesslogic.NumberGeneFormatBusinessLogic;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
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

/**
 *
 * @author upul
 */
public class UpdateNumberFormatServlet extends HttpServlet {

    //declaring variables
    private RequestDispatcher rd = null;
    private String errorMessage = null;
    private SessionUser sessionUser = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SystemUserManager systemUserManager = null;
    //--------------------------------------------------
    String oldValue = "";
    String newValue = "";
    String endPoint = null;
    private String removeAllow = "yes";
    private NumberGenerationFormatBean formatBean;
    private HashMap<String, String> UsedBinList = null;
    private HashMap<String, String> productModes = null;
    private HashMap<String, String> AssignBinList = null;
    private HashMap<String, String> NotAssignBinList = null;
    private HashMap<String, String> UsersAssignBinList = null;
    private HashMap<String, String> cardProductBinList = null;
    private HashMap<String, String> UsersNotAssignBinList = null;
    private List<NumberFormatBean> numberFormatBeanLst = null;
    private List<NumberFormatFieldBean> formatCodeBeans = null;
    private NumberGeneFormatBusinessLogic formatBusinessLogic = null;

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
            //call to existing session
            /////////////////////////////////////////////////////////////////////
            HttpSession sessionObj = request.getSession(false);
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

            //-----------------------------------------------------------------------------------------------------------------------------

            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            formatBean = new NumberGenerationFormatBean();

            String[] notAssignedBin;
            String[] assignedBin;
            //if the arrays are not initialized it gives errors
            if (request.getParameter("assigned").equals("0")) {
                assignedBin = new String[0];
            } else {
                assignedBin = request.getParameterValues("assignlist");
            }

            if (request.getParameter("notassigned").equals("0")) {
                notAssignedBin = new String[0];
            } else {
                notAssignedBin = request.getParameterValues("notassignlist");
            }

            String taskCode = TaskVarList.UPDATE;
            if (this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), taskCode)) {

                //set the operation type
                request.setAttribute("operationtype", "update");
                //retrieve the production modes
                this.getProductionModes();
                //retrieve the BINs for the card type
                this.getCardProductBins(request.getParameter("cardType"));
                //retrive the sequence numbers for the used BINs
                this.getSeqNoForUsedBINs(request.getParameter("numberFormatCode"));

                if (this.validateNumberFormat(request, assignedBin, notAssignedBin, UsedBinList)) {

                    this.setValuesToNumberFormatList(formatBean, assignedBin, notAssignedBin);
                    this.setAudittraceValue(request);

                    int success = formatBusinessLogic.updateNumberFormatCodeDetail(systemAuditBean, formatBean, numberFormatBeanLst, formatBean.getFormatCode(), assignedBin);

                    if (success == 1) {

                        request.setAttribute("successMsg", MessageVarList.CARD_NUMBER_FORMAT_UPDATE_SUCCESS);
                        request.setAttribute("productModes", productModes);
                        request.setAttribute("disable", "no");
                        sessionVarlist.setDisableProdMode("no");
                        request.setAttribute("isBack", "yes");
                        request.setAttribute("formatBean", null);
                    }
                } else {

                    request.setAttribute("errorMsg", errorMessage);
                    request.setAttribute("AssignbinList", UsersAssignBinList);
                    request.setAttribute("NotAssignbinList", UsersNotAssignBinList);
                    //if sequence no has incremented the BINs cannot be removed and two lists should be reset
                    if (removeAllow.equals("no")) {
                        request.setAttribute("AssignbinList", AssignBinList);
                        request.setAttribute("NotAssignbinList", NotAssignBinList);
                    }
                    request.setAttribute("disable", sessionVarlist.getDisableProdMode());
                    request.setAttribute("isFromError", "yes");
                }

                rd = getServletContext().getRequestDispatcher("/LoadNumberFormatGeneServlet");
                rd.forward(request, response);

            } else {
                //if invalid throw accessdenied exception
                throw new AccessDeniedException();
            }
            //////////////////////////////////////////////////////////////////
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp");
            rd.forward(request, response);
        } //catch session exception
        catch (NewLoginSessionException nlex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);
        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            String errMessage = MessageVarList.ACCESS_DENIED_PAGETASK;
            request.setAttribute(MessageVarList.JSP_ERROR, errMessage);
            rd = getServletContext().getRequestDispatcher("/LoadNumberFormatGeneServlet");
            rd.include(request, response);

        } catch (SQLException e) {
            //show the messages which has thown when inserting
            OracleMessage message = new OracleMessage();
            String oraMessage = message.getMessege(e.getMessage());

            //go to the home page with the error message
            request.setAttribute("errorMsg", oraMessage);

            rd = getServletContext().getRequestDispatcher("/LoadNumberFormatGeneServlet");
            rd.include(request, response);

        } catch (Exception ex) {
        } finally {
            out.close();
        }
    }

    ////////////////////////////////////////////////////////////////////////////    
    /**
     * isValidTaskByUser
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
    ////////////////////////////////////////////////////////////////////////////   

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("numberFormatCode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Update Card Number Format. Card Number Format Code: '" + uniqueId + "'  by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.CARDNUMBERGENE);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue(oldValue);
            systemAuditBean.setNewValue(newValue);

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * validateNumberFormat
     * @param request
     * @return 
     */
    private boolean validateNumberFormat(HttpServletRequest request, String assign[], String notAssign[], HashMap<String, String> UsedBinList) throws Exception {
        boolean isValid = true;

        try {

            int k = 0;
            UsersAssignBinList = new HashMap<String, String>();
            if (assign.length != 0) {
                while (assign.length > k) {

                    UsersAssignBinList.put(assign[k], "0");
                    for (String key1 : cardProductBinList.keySet()) {
                        for (String key2 : UsersAssignBinList.keySet()) {
                            if (key1.equals(key2)) {
                                UsersAssignBinList.put(key2, cardProductBinList.get(key2));
                            }
                        }
                    }
                    k++;
                }
            }
            int l = 0;
            UsersNotAssignBinList = new HashMap<String, String>();
            while (notAssign.length > l) {
                UsersNotAssignBinList.put(notAssign[l], "0");
                for (String key1 : cardProductBinList.keySet()) {
                    for (String key2 : UsersNotAssignBinList.keySet()) {
                        if (key1.equals(key2)) {
                            UsersNotAssignBinList.put(key2, cardProductBinList.get(key2));
                        }
                    }
                }

                l++;
            }
            int sequenceNumber = 0;
            for (int i = 0; i < notAssign.length; i++) {
                for (String key : UsedBinList.keySet()) {
                    if (key.equals(notAssign[i])) {
                        if (UsedBinList.get(key) != null) {
                            sequenceNumber = Integer.parseInt(UsedBinList.get(key));
                        }
                        if (sequenceNumber != 0) {
                            isValid = false;
                            errorMessage = MessageVarList.USED_BIN_REMOVE;
                            this.getAssignBinList(request.getParameter("numberFormatCode"));
                            this.getBinToCardType(sessionVarlist.getNumFormat().getCardType());
                            removeAllow = "no";
                        }
                    }
                }

            }

            String formatCode = request.getParameter("numberFormatCode");
            String formatDesc = request.getParameter("numberFormatDesc");
            //card type is disabled
            String cardType = sessionVarlist.getNumFormat().getCardType();
            String numberLength = request.getParameter("numberLength");
            //  String prodMode = "";
            //if production mode is disabled
//            if (sessionVarlist.getDisableProdMode().equals("yes")) {
//                prodMode = sessionVarlist.getNumFormat().getProductMode();
//            }
//            if (sessionVarlist.getDisableProdMode().equals("no")) {
//                prodMode = request.getParameter("productionMode");
//            }

            //oldValue = request.getParameter("oldValue");

            this.getAllNumberFormatCodeDetails();

            for (NumberFormatFieldBean bean : formatCodeBeans) {
                if (bean.getFormatCode().equals(formatCode)) {
                    oldValue = bean.getFormatCode() + "|" + bean.getDescription() + "|"
                            + "" + bean.getCardType() + "|"
                            + "" + bean.getNumberLength();
                }
            }


//            newValue = formatBean.getFormatCode() + "|" + formatBean.getDescription() + "|"
//                    + "" + formatBean.getCardType()  + "|"
//                    + "" + formatBean.getCardNumberLength();

            String fromOne = request.getParameter("fromOne");
            formatBean.setFromOne(Integer.parseInt(fromOne));
            String fromTwo = request.getParameter("fromTwo");
            formatBean.setFromTwo(Integer.parseInt(fromTwo));
            String fromThree = request.getParameter("fromThree");

            request.setAttribute("cardTypeVar", cardType);
            formatBean.setFormatCode(formatCode);
            formatBean.setDescription(formatDesc);
            formatBean.setCardNumberLength(numberLength);
            formatBean.setCardType(cardType);

            newValue = formatBean.getFormatCode() + "|" + formatBean.getDescription() + "|"
                    + "" + formatBean.getCardType() + "|"
                    + "" + formatBean.getCardNumberLength();
            //  formatBean.setProductMode(prodMode);

            request.setAttribute("numberLength", request.getParameter("numberLength"));

            if (fromThree != null) {
                formatBean.setFromThree(Integer.parseInt(fromThree));
            }

            String fromFour = request.getParameter("fromFour");

            if (fromFour != null) {
                formatBean.setFromFour(Integer.parseInt(fromFour));
            }

            String fromFive = request.getParameter("fromFive");
            if (fromFive != null) {
                formatBean.setFromFive(Integer.parseInt(fromFive));
            }


            String fromSix = request.getParameter("fromSix");

            if (fromSix != null) {
                formatBean.setFromSix(Integer.parseInt(fromSix));
            }
            String fromSeven = request.getParameter("fromSeven");

            if (fromSeven != null) {
                formatBean.setFromSeven(Integer.parseInt(fromSeven));
            }

            String toOne = request.getParameter("toOne");
            if (toOne != null) {
                formatBean.setToOne(Integer.parseInt(toOne));
            }
            String toTwo = request.getParameter("toTwo");
            if (toTwo == null || toTwo.equals("")) {
            } else {
                formatBean.setToTwo(Integer.parseInt(toTwo));
            }
            String toThree = request.getParameter("toThree");
            if (toThree == null || toThree.equals("")) {
            } else {
                formatBean.setToThree(Integer.parseInt(toThree));
            }
            String toFour = request.getParameter("toFour");
            if (toFour == null || toFour.equals("")) {
            } else {
                formatBean.setToFour(Integer.parseInt(toFour));
            }
            String toFive = request.getParameter("toFive");
            if (toFive == null || toFive.equals("")) {
            } else {
                formatBean.setToFive(Integer.parseInt(toFive));
            }
            String toSix = request.getParameter("toSix");
            if (toSix == null || toSix.equals("")) {
            } else {
                formatBean.setToSix(Integer.parseInt(toSix));
            }
            String toSeven = request.getParameter("toSeven");
            if (toSeven == null || toSeven.equals("")) {
            } else {
                formatBean.setToSeven(Integer.parseInt(toSeven));
            }

            String checkDigit = request.getParameter("lastDigit");

            if (checkDigit == null || checkDigit.equals("")) {
            } else {
                formatBean.setCheckDigit(Integer.parseInt(checkDigit));
            }




            //set all format codes to bean
            String formatOne = request.getParameter("bin");
            formatBean.setFormatOne(formatOne);
            String formatTwo = request.getParameter("formatTwo");
            formatBean.setFormatTwo(formatTwo);
            String formatThree = request.getParameter("formatThree");
            formatBean.setFormatThree(formatThree);
            String formatFour = request.getParameter("formatFour");
            formatBean.setFormatFour(formatFour);
            String formatFive = request.getParameter("formatFive");
            formatBean.setFormatFive(formatFive);
            String formatSix = request.getParameter("formatSix");
            formatBean.setFormatSix(formatSix);
            String formatSeven = request.getParameter("formatSeven");
            formatBean.setFormatSeven(formatSeven);

//--------------------check for sequence format type errors----------------------------------------
            int minusLastDigit = 0;

            if (!request.getParameter("lastDigit").equals("")) {
                minusLastDigit = (Integer.parseInt(request.getParameter("lastDigit")) - 1);
            }
            String endBeforeLastDigit = String.valueOf(minusLastDigit);

            int previousSeqNoLength = sessionVarlist.getSequenceNoLength();
            int nowSeqNoLength;
            boolean wasSequence = false;

            if (previousSeqNoLength != 0) {
                //if(!request.getParameter("toTwo").equals(endBeforeLastDigit)){
                if (formatTwo.equals("FSEC") && !request.getParameter("toTwo").equals("")) {
                    wasSequence = true;
                    nowSeqNoLength = Integer.parseInt(request.getParameter("toTwo")) + 1 - Integer.parseInt(request.getParameter("fromTwo"));

                    if (previousSeqNoLength != nowSeqNoLength) {
                        isValid = false;
                        errorMessage = MessageVarList.SEQ_FORMAT_ERROR + previousSeqNoLength;
                    }


                }
                if (!request.getParameter("toTwo").equals(endBeforeLastDigit) && !request.getParameter("toTwo").equals("")) {
                    if (formatThree.equals("FSEC") && !request.getParameter("toThree").equals("")) {
                        wasSequence = true;
                        nowSeqNoLength = Integer.parseInt(request.getParameter("toThree")) + 1 - Integer.parseInt(request.getParameter("fromThree"));

                        if (previousSeqNoLength != nowSeqNoLength) {
                            isValid = false;
                            errorMessage = MessageVarList.SEQ_FORMAT_ERROR + previousSeqNoLength;
                        }


                    }
                    if (!request.getParameter("toThree").equals(endBeforeLastDigit) && !request.getParameter("toThree").equals("")) {

                        if (formatFour.equals("FSEC") && !request.getParameter("toFour").equals("")) {
                            wasSequence = true;
                            nowSeqNoLength = Integer.parseInt(request.getParameter("toFour")) + 1 - Integer.parseInt(request.getParameter("fromFour"));

                            if (previousSeqNoLength != nowSeqNoLength) {
                                isValid = false;
                                errorMessage = MessageVarList.SEQ_FORMAT_ERROR + previousSeqNoLength;
                            }


                        }
                        if (!request.getParameter("toFour").equals(endBeforeLastDigit) && !request.getParameter("toFour").equals("")) {
                            if (formatFive.equals("FSEC") && !request.getParameter("toFive").equals("")) {
                                wasSequence = true;
                                nowSeqNoLength = Integer.parseInt(request.getParameter("toFive")) + 1 - Integer.parseInt(request.getParameter("fromFive"));

                                if (previousSeqNoLength != nowSeqNoLength) {
                                    isValid = false;
                                    errorMessage = MessageVarList.SEQ_FORMAT_ERROR + previousSeqNoLength;
                                }


                            }
                            if (!request.getParameter("toFive").equals(endBeforeLastDigit) && !request.getParameter("toFive").equals("")) {
                                if (formatSix.equals("FSEC") && !request.getParameter("toSix").equals("")) {
                                    wasSequence = true;
                                    nowSeqNoLength = Integer.parseInt(request.getParameter("toSix")) + 1 - Integer.parseInt(request.getParameter("fromSix"));

                                    if (previousSeqNoLength != nowSeqNoLength) {
                                        isValid = false;
                                        errorMessage = MessageVarList.SEQ_FORMAT_ERROR + previousSeqNoLength;
                                    }


                                }
                                if (!request.getParameter("toSix").equals(endBeforeLastDigit) && !request.getParameter("toSix").equals("")) {
                                    if (formatSeven.equals("FSEC") && !request.getParameter("toSeven").equals("")) {
                                        wasSequence = true;
                                        nowSeqNoLength = Integer.parseInt(request.getParameter("toSeven")) + 1 - Integer.parseInt(request.getParameter("fromSeven"));

                                        if (previousSeqNoLength != nowSeqNoLength) {
                                            isValid = false;
                                            errorMessage = MessageVarList.SEQ_FORMAT_ERROR + previousSeqNoLength;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (!wasSequence && previousSeqNoLength != 0) {
                isValid = false;
                errorMessage = MessageVarList.NO_SEQ_FORMAT;
            }


            //--------------------
            //---------------------check whether it has two sequence formats-----------------

            minusLastDigit = 0;



            int twoSequenceFormats = 0;
            if (formatTwo.equals("FSEC")) {
                twoSequenceFormats += 1;
            }
            if (!request.getParameter("toTwo").equals(endBeforeLastDigit) && !request.getParameter("toTwo").equals("")) {
                if (formatThree.equals("FSEC")) {
                    twoSequenceFormats += 1;
                }
                if (!request.getParameter("toThree").equals(endBeforeLastDigit) && !request.getParameter("toThree").equals("")) {
                    if (formatFour.equals("FSEC")) {
                        twoSequenceFormats += 1;
                    }
                    if (!request.getParameter("toFour").equals(endBeforeLastDigit) && !request.getParameter("toFour").equals("")) {
                        if (formatFive.equals("FSEC")) {
                            twoSequenceFormats += 1;
                        }
                        if (!request.getParameter("toFive").equals(endBeforeLastDigit) && !request.getParameter("toFive").equals("")) {
                            if (formatSix.equals("FSEC")) {
                                twoSequenceFormats += 1;
                            }
                            if (!request.getParameter("toSix").equals(endBeforeLastDigit) && !request.getParameter("toSix").equals("")) {
                                if (formatSeven.equals("FSEC")) {
                                    twoSequenceFormats += 1;
                                }
                            }
                        }
                    }
                }
            }

            if (twoSequenceFormats > 1) {
                isValid = false;
                errorMessage = MessageVarList.TWO_SEQ_FORMATS;

            }

            //-------------------------------------------------------------------------------





            if (formatCode.contentEquals("") || formatCode.length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.NUMBER_FORMAT_CODE_NULL;
            } else if (!UserInputValidator.isCorrectString(formatCode)) {
                isValid = false;
                errorMessage = MessageVarList.NUMBER_FORMAT_CODE_INVALID;

            } else if (formatDesc.contentEquals("") || formatDesc.length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.NUMBER_FORMAT_DESC_NULL;
            } else if (!UserInputValidator.isCorrectString(formatDesc)) {
                isValid = false;
                errorMessage = MessageVarList.NUMBER_FORMAT_DESC_INVALID;
            } else if (assign == null || assign.length <= 0) {
                isValid = false;
                errorMessage = MessageVarList.ASSIGNED_BIN_NULL;
            } else if (numberLength.contentEquals("") || numberLength.length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.CARD_NUMBER_LENGTH_NULL;
            } else if (!UserInputValidator.isNumeric(numberLength)) {
                isValid = false;
                errorMessage = MessageVarList.CARD_NUMBER_INVALID;

            } else {

                //check the format is in correct format
                if (this.isInCorrectFormat(formatBean)) {


                    if (endPoint.equals("One")) {

                        if (formatOne.contentEquals("") || formatOne.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        }
                    } else if (endPoint.equals("Two")) {


                        if (formatOne.contentEquals("") || formatOne.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatTwo.contentEquals("") || formatTwo.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;
                        }
                    } else if (endPoint.equals("Three")) {

                        if (formatOne.contentEquals("") || formatOne.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatTwo.contentEquals("") || formatTwo.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatThree.contentEquals("") || formatThree.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;
                        }
                    } else if (endPoint.equals("Four")) {

                        if (formatOne.contentEquals("") || formatOne.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatTwo.contentEquals("") || formatTwo.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatThree.contentEquals("") || formatThree.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatFour.contentEquals("") || formatFour.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;
                        }
                    } else if (endPoint.equals("Five")) {

                        if (formatOne.contentEquals("") || formatOne.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatTwo.contentEquals("") || formatTwo.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatThree.contentEquals("") || formatThree.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatFour.contentEquals("") || formatFour.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatFive.contentEquals("") || formatFive.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;
                        }
                    } else if (endPoint.equals("Six")) {
                        if (formatOne.contentEquals("") || formatOne.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatTwo.contentEquals("") || formatTwo.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatThree.contentEquals("") || formatThree.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatFour.contentEquals("") || formatFour.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatFive.contentEquals("") || formatFive.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatSix.contentEquals("") || formatSix.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        }

                    } else if (endPoint.equals("Seven")) {
                        if (formatOne.contentEquals("") || formatOne.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatTwo.contentEquals("") || formatTwo.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatThree.contentEquals("") || formatThree.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatFour.contentEquals("") || formatFour.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatFive.contentEquals("") || formatFive.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatSix.contentEquals("") || formatSix.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;

                        } else if (formatSeven.contentEquals("") || formatSeven.length() <= 0) {
                            isValid = false;
                            errorMessage = MessageVarList.INVALID_FORMAT_TYPE;
                        }
                    } else {
                    }
                } else {
                    isValid = false;
                    errorMessage = "Incorrect Number Format. Please re-arrange in correct format";
                }
            }
            request.setAttribute("formatBean", formatBean);

        } catch (Exception ex) {
            throw ex;
        }

        return isValid;

    }

    /////////////////////////////////   
    /**
     * isInCorrectFormat check format is ok or not
     * @param formatBean
     * @return 
     */
    private boolean isInCorrectFormat(NumberGenerationFormatBean formatBean) {
        boolean isInCorrectFormat = true;
        endPoint = null;
        try {

            if (formatBean.getToTwo() < formatBean.getCheckDigit() - 1) {

                if (formatBean.getToTwo() + 1 == formatBean.getFromThree()) {

                    if (formatBean.getToThree() < formatBean.getCheckDigit() - 1) {

                        if (formatBean.getToThree() + 1 == formatBean.getFromFour()) {

                            if (formatBean.getToFour() < formatBean.getCheckDigit() - 1) {

                                if (formatBean.getToFour() + 1 == formatBean.getFromFive()) {

                                    if (formatBean.getToFive() < formatBean.getCheckDigit() - 1) {

                                        if (formatBean.getToFive() + 1 == formatBean.getFromSix()) {

                                            if (formatBean.getToSix() < formatBean.getCheckDigit() - 1) {

                                                if (formatBean.getToSix() + 1 == formatBean.getFromSeven()) {

                                                    if (formatBean.getToSeven() < formatBean.getCheckDigit() - 1) {

                                                        if (formatBean.getToSeven() + 1 == formatBean.getCheckDigit()) {
                                                        } else {
                                                            //incorrect format
                                                            isInCorrectFormat = false;
                                                        }

                                                    } else {
                                                        endPoint = "Seven";
                                                        formatBean.setEndPoint(7);
                                                    }

                                                } else {
                                                    //incorrect format
                                                    isInCorrectFormat = false;
                                                }

                                            } else {
                                                endPoint = "Six";
                                                formatBean.setEndPoint(6);
                                            }

                                        } else {
                                            //incorrect format
                                            isInCorrectFormat = false;
                                        }


                                    } else {
                                        endPoint = "Five";
                                        formatBean.setEndPoint(5);
                                    }

                                } else {
                                    //incorrect format
                                    isInCorrectFormat = false;
                                }

                            } else {
                                endPoint = "Four";
                                formatBean.setEndPoint(4);
                            }


                        } else {
                            //incorrect format
                            isInCorrectFormat = false;
                        }



                    } else {
                        endPoint = "Three";
                        formatBean.setEndPoint(3);
                    }



                } else {
                    //incorrect format
                    isInCorrectFormat = false;
                }





            } else {
                endPoint = "Two";
                formatBean.setEndPoint(2);
            }



        } catch (Exception ex) {
            System.out.println(ex + "*********************************");
        }
        return isInCorrectFormat;

    }
    /////////////////////////////////////////////////////   

    /**
     * setValuesToNumberFormatList
     * @return 
     */
    private List<NumberFormatBean> setValuesToNumberFormatList(NumberGenerationFormatBean formatBean, String assign[], String notAssign[]) {


        int k = 0;
        AssignBinList = new HashMap<String, String>();
        if (assign.length != 0) {
            while (assign.length > k) {

                AssignBinList.put(assign[k], "0");
                for (String key1 : cardProductBinList.keySet()) {
                    for (String key2 : AssignBinList.keySet()) {
                        if (key1.equals(key2)) {
                            AssignBinList.put(key2, cardProductBinList.get(key2));
                        }
                    }
                }
                k++;
            }
        }
        int l = 0;
        NotAssignBinList = new HashMap<String, String>();
        while (notAssign.length > l) {
            NotAssignBinList.put(notAssign[l], "0");
            for (String key1 : cardProductBinList.keySet()) {
                for (String key2 : NotAssignBinList.keySet()) {
                    if (key1.equals(key2)) {
                        NotAssignBinList.put(key2, cardProductBinList.get(key2));
                    }
                }
            }

            l++;
        }
        NumberFormatBean nfb;
        try {
            numberFormatBeanLst = new ArrayList<NumberFormatBean>();

            if (formatBean.getEndPoint() == 2) {

                this.setFirstFormat(formatBean);
                this.setSecondFormat(formatBean);
                this.setCheckDigit(formatBean);


            }
            if (formatBean.getEndPoint() == 3) {

                this.setFirstFormat(formatBean);
                this.setSecondFormat(formatBean);
                this.setThirdFormat(formatBean);
                this.setCheckDigit(formatBean);


            }
            if (formatBean.getEndPoint() == 4) {

                this.setFirstFormat(formatBean);
                this.setSecondFormat(formatBean);
                this.setThirdFormat(formatBean);
                this.setFourthFormat(formatBean);
                this.setCheckDigit(formatBean);



            }
            if (formatBean.getEndPoint() == 5) {

                this.setFirstFormat(formatBean);
                this.setSecondFormat(formatBean);
                this.setThirdFormat(formatBean);
                this.setFourthFormat(formatBean);
                this.setFifthFormat(formatBean);
                this.setCheckDigit(formatBean);



            }
            if (formatBean.getEndPoint() == 6) {

                this.setFirstFormat(formatBean);
                this.setSecondFormat(formatBean);
                this.setThirdFormat(formatBean);
                this.setFourthFormat(formatBean);
                this.setFifthFormat(formatBean);
                this.setSixthFormat(formatBean);
                this.setCheckDigit(formatBean);


            }
            if (formatBean.getEndPoint() == 7) {

                this.setFirstFormat(formatBean);
                this.setSecondFormat(formatBean);
                this.setThirdFormat(formatBean);
                this.setFourthFormat(formatBean);
                this.setFifthFormat(formatBean);
                this.setSixthFormat(formatBean);
                this.setSeventhFormat(formatBean);
                this.setCheckDigit(formatBean);


            }



        } catch (Exception ex) {
        }
        return numberFormatBeanLst;

    }

    //set first two digits
    private NumberFormatBean setFirstFormat(NumberGenerationFormatBean formatBean) {

        NumberFormatBean nfb = new NumberFormatBean();
        try {
            //get first and set them to list
            nfb = new NumberFormatBean();
            nfb.setFormatCode(formatBean.getFormatCode());
            nfb.setFieldTypeCode(formatBean.getFormatOne());
            nfb.setFromDigit(formatBean.getFromOne());
            nfb.setToDigit(formatBean.getToOne());

            numberFormatBeanLst.add(nfb);



        } catch (Exception ex) {
        }
        return nfb;

    }

    //set Second two digits
    private NumberFormatBean setSecondFormat(NumberGenerationFormatBean formatBean) {

        NumberFormatBean nfb = new NumberFormatBean();
        try {
            //get Two and set them to list
            nfb = new NumberFormatBean();
            nfb.setFormatCode(formatBean.getFormatCode());
            nfb.setFieldTypeCode(formatBean.getFormatTwo());
            nfb.setFromDigit(formatBean.getFromTwo());
            nfb.setToDigit(formatBean.getToTwo());

            numberFormatBeanLst.add(nfb);



        } catch (Exception ex) {
        }
        return nfb;

    }

    //set Third two digits
    private NumberFormatBean setThirdFormat(NumberGenerationFormatBean formatBean) {

        NumberFormatBean nfb = new NumberFormatBean();
        try {

            //get Three and set them to list
            nfb = new NumberFormatBean();
            nfb.setFormatCode(formatBean.getFormatCode());
            nfb.setFieldTypeCode(formatBean.getFormatThree());
            nfb.setFromDigit(formatBean.getFromThree());
            nfb.setToDigit(formatBean.getToThree());

            numberFormatBeanLst.add(nfb);


        } catch (Exception ex) {
        }
        return nfb;

    }

    //set 4th two digits
    private NumberFormatBean setFourthFormat(NumberGenerationFormatBean formatBean) {

        NumberFormatBean nfb = new NumberFormatBean();
        try {

            //get Four and set them to list
            nfb = new NumberFormatBean();
            nfb.setFormatCode(formatBean.getFormatCode());
            nfb.setFieldTypeCode(formatBean.getFormatFour());
            nfb.setFromDigit(formatBean.getFromFour());
            nfb.setToDigit(formatBean.getToFour());

            numberFormatBeanLst.add(nfb);


        } catch (Exception ex) {
        }
        return nfb;

    }

    //set fifth two digits
    private NumberFormatBean setFifthFormat(NumberGenerationFormatBean formatBean) {

        NumberFormatBean nfb = new NumberFormatBean();
        try {

            //get Five and set them to list
            nfb = new NumberFormatBean();
            nfb.setFormatCode(formatBean.getFormatCode());
            nfb.setFieldTypeCode(formatBean.getFormatFive());
            nfb.setFromDigit(formatBean.getFromFive());
            nfb.setToDigit(formatBean.getToFive());

            numberFormatBeanLst.add(nfb);


        } catch (Exception ex) {
        }
        return nfb;

    }

    //set 6th two digits
    private NumberFormatBean setSixthFormat(NumberGenerationFormatBean formatBean) {

        NumberFormatBean nfb = new NumberFormatBean();
        try {

            //get six and set them to list
            nfb = new NumberFormatBean();
            nfb.setFormatCode(formatBean.getFormatCode());
            nfb.setFieldTypeCode(formatBean.getFormatSix());
            nfb.setFromDigit(formatBean.getFromSix());
            nfb.setToDigit(formatBean.getToSix());

            numberFormatBeanLst.add(nfb);



        } catch (Exception ex) {
        }
        return nfb;

    }

    //set seven two digits
    private void setSeventhFormat(NumberGenerationFormatBean formatBean) {

        NumberFormatBean nfb = new NumberFormatBean();
        try {

            //get seven and set them to list
            nfb = new NumberFormatBean();
            nfb.setFormatCode(formatBean.getFormatCode());
            nfb.setFieldTypeCode(formatBean.getFormatSeven());
            nfb.setFromDigit(formatBean.getFromSeven());
            nfb.setToDigit(formatBean.getToSeven());

            numberFormatBeanLst.add(nfb);


        } catch (Exception ex) {
        }


    }

    /**
     * getAllNumberFormatCodeDetails
     * @return
     * @throws Exception 
     */
    public void getAllNumberFormatCodeDetails() throws Exception {

        try {
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            formatCodeBeans = formatBusinessLogic.getNumberFormatCodeDetails();
        } catch (SQLException ex) {
            throw ex;
        }

    }

    //set Second two digits
    private NumberFormatBean setCheckDigit(NumberGenerationFormatBean formatBean) {

        NumberFormatBean nfb = new NumberFormatBean();
        try {
            //get Two and set them to list
            nfb = new NumberFormatBean();
            nfb.setFormatCode(formatBean.getFormatCode());
            nfb.setFieldTypeCode("FCHD");
            nfb.setFromDigit(formatBean.getCheckDigit());
            nfb.setToDigit(formatBean.getCheckDigit());

            numberFormatBeanLst.add(nfb);



        } catch (Exception ex) {
        }
        return nfb;

    }

    public void getProductionModes() throws Exception {

        try {
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            productModes = formatBusinessLogic.getProductionModes();
        } catch (SQLException ex) {
            throw ex;
        }

    }

    private void getCardProductBins(String category) throws Exception {
        try {

            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            cardProductBinList = formatBusinessLogic.getCardProductBins(category);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getSeqNoForUsedBINs(String numFormatCode) throws Exception {
        try {

            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            UsedBinList = formatBusinessLogic.getSeqNoForUsedBINs(numFormatCode);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getBinToCardType(String cardType) throws Exception {
        try {

            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            NotAssignBinList = formatBusinessLogic.getBinToCardType(cardType);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAssignBinList(String numFormatCode) throws Exception {
        try {

            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            AssignBinList = formatBusinessLogic.getAssignBinList(numFormatCode);

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
