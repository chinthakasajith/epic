/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.LetterBodyFormatBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.LetterTemplateBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.DocumentTypeMgtManager;
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
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chanuka
 */
public class UpdateLetterTemplateMgtServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private LetterTemplateBean tmpLetterBean = null;
    private LetterBodyFormatBean tmpBodyBean = null;
    private String[] titleArray = null;
    private String errorMessage = null;
    private String letterFormat = null;
    private String bodyFormatString = null;
    private String titleFormatString = null;
    private SystemAuditBean systemAuditBean;
    private String url = "/administrator/controlpanel/systemconfigmgt/lettertemplatemgt.jsp";

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


            int success = -1;
            request.setAttribute("operationtype", "update");
            rd = request.getRequestDispatcher(url);

            List<LetterTemplateBean> templateList = DocumentTypeMgtManager.getInstance().getAllLetterTemplateDetailsList();
            request.setAttribute("templateList", templateList);



            this.setLetterFormatBean(request);
            request.setAttribute("tmpLetterBean", tmpLetterBean);
            request.setAttribute("tmpBodyBean", tmpBodyBean);






            if (this.validateUserInput(tmpLetterBean, tmpBodyBean)) {

                this.setAudittraceValue(request);
                createLetterFormat(tmpLetterBean, tmpBodyBean, titleArray);

                tmpLetterBean.setTemplateFormat(letterFormat);

                success = DocumentTypeMgtManager.getInstance().updateLetterTemplates(systemAuditBean, tmpLetterBean);



                if (success > 0) {
                    request.setAttribute("operationtype", "add");
                    rd = request.getRequestDispatcher("/LoadLetterTemplateMgtServlet");
                    request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.LETTER_TEMPLATE_UPDATE_SUCCESS + " Code: " + tmpLetterBean.getTemplateCode());

                } else {

                    request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
                }
            } else {

                request.setAttribute("errorMsg", errorMessage);
            }










        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadLetterTemplateMgtServlet");

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * to set the values taken by user to bean
     * @param request
     * @throws Exception 
     */
    private void setLetterFormatBean(HttpServletRequest request) throws Exception {
        try {


            tmpLetterBean = new LetterTemplateBean();

            tmpLetterBean.setTemplateCode(request.getParameter("templatecode"));
            tmpLetterBean.setDescription(request.getParameter("description"));
            tmpLetterBean.setStatus(request.getParameter("statuscode"));
            tmpLetterBean.setProcessId(request.getParameter("processtype"));
            tmpLetterBean.setLastUpdatedUser(sessionUser.getUserName());
            tmpLetterBean.setLastUpdatedDate(new Date());


//            if (!request.getParameter("fields1").contentEquals("") && request.getParameter("fields1").length() > 0) {

            String[] tempArr1 = new String[3];

            tempArr1[0] = request.getParameter("fields1");
            tempArr1[1] = request.getParameter("letterformat1");
            tempArr1[2] = request.getParameter("status1");
            tmpLetterBean.setFieldSet1(tempArr1);

            if (request.getParameter("fields1").equals("BODY")) {
                this.setLetterBodyFormat(request);
            } else if (request.getParameter("fields1").equals("TITLE")) {

                this.setLetterTitleFormat(request, "1");

            } else if (request.getParameter("fields1").equals("TEXT")) {
                tempArr1 = this.setLetterTextFormat(request, "1");
                tmpLetterBean.setFieldSet1(tempArr1);
            }



//            }
//            if (!request.getParameter("fields2").contentEquals("") && request.getParameter("fields2").length() > 0) {


            String[] tempArr2 = new String[3];

            tempArr2[0] = request.getParameter("fields2");
            tempArr2[1] = request.getParameter("letterformat2");
            tempArr2[2] = request.getParameter("status2");
            tmpLetterBean.setFieldSet2(tempArr2);

            if (request.getParameter("fields2").equals("BODY")) {
                this.setLetterBodyFormat(request);
            } else if (request.getParameter("fields2").equals("TITLE")) {

                this.setLetterTitleFormat(request, "2");

            } else if (request.getParameter("fields2").equals("TEXT")) {
                tempArr2 = this.setLetterTextFormat(request, "2");
                tmpLetterBean.setFieldSet2(tempArr2);
            }

//            }
//            if (!request.getParameter("fields3").contentEquals("") && request.getParameter("fields3").length() > 0) {


            String[] tempArr3 = new String[3];
            tempArr3[0] = request.getParameter("fields3");
            tempArr3[1] = request.getParameter("letterformat3");
            tempArr3[2] = request.getParameter("status3");
            tmpLetterBean.setFieldSet3(tempArr3);
            if (request.getParameter("fields3").equals("BODY")) {
                this.setLetterBodyFormat(request);
            } else if (request.getParameter("fields3").equals("TITLE")) {
                this.setLetterTitleFormat(request, "3");

            } else if (request.getParameter("fields3").equals("TEXT")) {
                tempArr3 = this.setLetterTextFormat(request, "3");
                tmpLetterBean.setFieldSet3(tempArr3);
            }

//            }
//            if (!request.getParameter("fields4").contentEquals("") && request.getParameter("fields4").length() > 0) {

            String[] tempArr4 = new String[3];
            tempArr4[0] = request.getParameter("fields4");
            tempArr4[1] = request.getParameter("letterformat4");
            tempArr4[2] = request.getParameter("status4");
            tmpLetterBean.setFieldSet4(tempArr4);

            if (request.getParameter("fields4").equals("BODY")) {
                this.setLetterBodyFormat(request);
            } else if (request.getParameter("fields4").equals("TITLE")) {
                this.setLetterTitleFormat(request, "4");

            } else if (request.getParameter("fields4").equals("TEXT")) {
                tempArr4 = this.setLetterTextFormat(request, "4");
                tmpLetterBean.setFieldSet4(tempArr4);
            }

//            }
//            if (!request.getParameter("fields5").contentEquals("") && request.getParameter("fields5").length() > 0) {

            String[] tempArr5 = new String[3];
            tempArr5[0] = request.getParameter("fields5");
            tempArr5[1] = request.getParameter("letterformat5");
            tempArr5[2] = request.getParameter("status5");
            tmpLetterBean.setFieldSet5(tempArr5);

            if (request.getParameter("fields5").equals("BODY")) {
                this.setLetterBodyFormat(request);
            } else if (request.getParameter("fields5").equals("TITLE")) {
                this.setLetterTitleFormat(request, "5");

            } else if (request.getParameter("fields5").equals("TEXT")) {
                tempArr5 = this.setLetterTextFormat(request, "5");
                tmpLetterBean.setFieldSet5(tempArr5);
            }

//            }
//            if (!request.getParameter("fields6").contentEquals("") && request.getParameter("fields6").length() > 0) {


            String[] tempArr6 = new String[3];
            tempArr6[0] = request.getParameter("fields6");
            tempArr6[1] = request.getParameter("letterformat6");
            tempArr6[2] = request.getParameter("status6");
            tmpLetterBean.setFieldSet6(tempArr6);

            if (request.getParameter("fields6").equals("BODY")) {
                this.setLetterBodyFormat(request);
            } else if (request.getParameter("fields6").equals("TITLE")) {
                this.setLetterTitleFormat(request, "6");

            } else if (request.getParameter("fields6").equals("TEXT")) {
                tempArr6 = this.setLetterTextFormat(request, "6");
                tmpLetterBean.setFieldSet6(tempArr6);
            }

//            }
//            if (!request.getParameter("fields7").contentEquals("") && request.getParameter("fields7").length() > 0) {


            String[] tempArr7 = new String[3];
            tempArr7[0] = request.getParameter("fields7");
            tempArr7[1] = request.getParameter("letterformat7");
            tempArr7[2] = request.getParameter("status7");
            tmpLetterBean.setFieldSet7(tempArr7);

            if (request.getParameter("fields7").equals("BODY")) {
                this.setLetterBodyFormat(request);
            } else if (request.getParameter("fields7").equals("TITLE")) {
                this.setLetterTitleFormat(request, "7");

            } else if (request.getParameter("fields7").equals("TEXT")) {
                tempArr7 = this.setLetterTextFormat(request, "7");
                tmpLetterBean.setFieldSet7(tempArr7);
            }

//            }



        } catch (Exception e) {
            throw e;
        }
    }

    private void setLetterBodyFormat(HttpServletRequest request) throws Exception {
        try {

            tmpBodyBean = new LetterBodyFormatBean();



            String[] tempArr1 = new String[3];
            tempArr1[0] = request.getParameter("bodyfields1");
            tempArr1[1] = request.getParameter("bodyletterformat1");
            tempArr1[2] = request.getParameter("bodystatus1");
            tmpBodyBean.setFieldSetBody1(tempArr1);

            if (request.getParameter("bodyfields1").equals("TEXT")) {
                tempArr1 = this.setLetterBodyTextFormat(request, "1");
                tmpBodyBean.setFieldSetBody1(tempArr1);
            }




            String[] tempArr2 = new String[3];
            tempArr2[0] = request.getParameter("bodyfields2");
            tempArr2[1] = request.getParameter("bodyletterformat2");
            tempArr2[2] = request.getParameter("bodystatus2");
            tmpBodyBean.setFieldSetBody2(tempArr2);
            if (request.getParameter("bodyfields2").equals("TEXT")) {
                tempArr2 = this.setLetterBodyTextFormat(request, "2");
                tmpBodyBean.setFieldSetBody2(tempArr2);
            }



            String[] tempArr3 = new String[3];
            tempArr3[0] = request.getParameter("bodyfields3");
            tempArr3[1] = request.getParameter("bodyletterformat3");
            tempArr3[2] = request.getParameter("bodystatus3");
            tmpBodyBean.setFieldSetBody3(tempArr3);

            if (request.getParameter("bodyfields3").equals("TEXT")) {
                tempArr3 = this.setLetterBodyTextFormat(request, "3");
                tmpBodyBean.setFieldSetBody3(tempArr3);
            }



            String[] tempArr4 = new String[3];
            tempArr4[0] = request.getParameter("bodyfields4");
            tempArr4[1] = request.getParameter("bodyletterformat4");
            tempArr4[2] = request.getParameter("bodystatus4");
            tmpBodyBean.setFieldSetBody4(tempArr4);

            if (request.getParameter("bodyfields4").equals("TEXT")) {
                tempArr4 = this.setLetterBodyTextFormat(request, "4");
                tmpBodyBean.setFieldSetBody4(tempArr4);
            }




            String[] tempArr5 = new String[3];
            tempArr5[0] = request.getParameter("bodyfields5");
            tempArr5[1] = request.getParameter("bodyletterformat5");
            tempArr5[2] = request.getParameter("bodystatus5");
            tmpBodyBean.setFieldSetBody5(tempArr5);

            if (request.getParameter("bodyfields5").equals("TEXT")) {
                tempArr5 = this.setLetterBodyTextFormat(request, "5");
                tmpBodyBean.setFieldSetBody5(tempArr5);
            }


            sessionVarlist.setLetterBodyFormatBean(tmpBodyBean);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

        } catch (Exception e) {
            throw e;
        }
    }

    private String[] setLetterTitleFormat(HttpServletRequest request, String titleParam) throws Exception {
        try {

            titleArray = new String[3];

            titleArray[0] = request.getParameter("title");
            titleArray[1] = request.getParameter("letterformat" + titleParam);
            titleArray[2] = request.getParameter("status" + titleParam);

            if (titleArray[0] != null) {
                sessionVarlist.setTitleValue(titleArray[0]);
            }

            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);


            return titleArray;

        } catch (Exception e) {
            throw e;
        }
    }

    private String[] setLetterTextFormat(HttpServletRequest request, String titleParam) throws Exception {
        try {

            String[] tempArr = new String[4];

            tempArr[0] = "TEXT";
            tempArr[1] = request.getParameter("letterformat" + titleParam);
            tempArr[2] = request.getParameter("status" + titleParam);
            tempArr[3] = request.getParameter("textcode" + titleParam);

            return tempArr;

        } catch (Exception e) {
            throw e;
        }
    }

    private String[] setLetterBodyTextFormat(HttpServletRequest request, String titleParam) throws Exception {
        try {

            String[] tempArr = new String[4];

            tempArr[0] = "TEXT";
            tempArr[1] = request.getParameter("bodyletterformat" + titleParam);
            tempArr[2] = request.getParameter("bodystatus" + titleParam);
            tempArr[3] = request.getParameter("bodytextcode" + titleParam);

            return tempArr;

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * validate user inputs
     * @param bean
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(LetterTemplateBean letterBean, LetterBodyFormatBean tmpBodyBean) throws Exception {
        boolean isValidate = true;

        try {

            if (letterBean.getTemplateCode().contentEquals("") || letterBean.getTemplateCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.LETTER_TEMPLATE_CODE_EMPTY;
            } else if (!UserInputValidator.isAlphaNumeric(letterBean.getTemplateCode())) {
                isValidate = false;
                errorMessage = MessageVarList.LETTER_TEMPLATE_CODE_ALPHANUMERIC;
            } else if (letterBean.getDescription().contentEquals("") || letterBean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.LETTER_TEMPLATE_DESCRIPTION_EMPTY;
            } else if (letterBean.getStatus().contentEquals("") || letterBean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.LETTER_TEMPLATE_STATUS_EMPTY;
            } else if (letterBean.getProcessId().contentEquals("") || letterBean.getProcessId().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.LETTER_TEMPLATE_PROCESS_EMPTY;
            }



            if (letterBean.getFieldSet1()[0].equals("") || letterBean.getFieldSet1()[1] == null || letterBean.getFieldSet1()[2] == null || letterBean.getFieldSet1()[1].equals("") || letterBean.getFieldSet1()[2].equals("")) {

                if (!letterBean.getFieldSet1()[0].equals("BODY")) {
                    isValidate = false;
                    errorMessage = MessageVarList.LETTER_TEMPLATE_TEMPLATE_EMPTY;
                }
            }
            if (letterBean.getFieldSet2()[0].equals("") || letterBean.getFieldSet2()[1] == null || letterBean.getFieldSet2()[2] == null || letterBean.getFieldSet2()[1].equals("") || letterBean.getFieldSet2()[2].equals("")) {

                if (!letterBean.getFieldSet2()[0].equals("BODY")) {
                    isValidate = false;
                    errorMessage = MessageVarList.LETTER_TEMPLATE_TEMPLATE_EMPTY;
                }
            }
            if (letterBean.getFieldSet3()[0].equals("") || letterBean.getFieldSet3()[1] == null || letterBean.getFieldSet3()[2] == null || letterBean.getFieldSet3()[1].equals("") || letterBean.getFieldSet3()[2].equals("")) {

                if (!letterBean.getFieldSet3()[0].equals("BODY")) {
                    isValidate = false;
                    errorMessage = MessageVarList.LETTER_TEMPLATE_TEMPLATE_EMPTY;
                }
            }
            if (letterBean.getFieldSet4()[0].equals("") || letterBean.getFieldSet4()[1] == null || letterBean.getFieldSet4()[2] == null || letterBean.getFieldSet4()[1].equals("") || letterBean.getFieldSet4()[2].equals("")) {

                if (!letterBean.getFieldSet4()[0].equals("BODY")) {
                    isValidate = false;
                    errorMessage = MessageVarList.LETTER_TEMPLATE_TEMPLATE_EMPTY;
                }
            }
            if (letterBean.getFieldSet5()[0].equals("") || letterBean.getFieldSet5()[1] == null || letterBean.getFieldSet5()[2] == null || letterBean.getFieldSet5()[1].equals("") || letterBean.getFieldSet5()[2].equals("")) {

                if (!letterBean.getFieldSet5()[0].equals("BODY")) {
                    isValidate = false;
                    errorMessage = MessageVarList.LETTER_TEMPLATE_TEMPLATE_EMPTY;
                }
            }
            if (letterBean.getFieldSet6()[0].equals("") || letterBean.getFieldSet6()[1] == null || letterBean.getFieldSet6()[2] == null || letterBean.getFieldSet6()[1].equals("") || letterBean.getFieldSet6()[2].equals("")) {

                if (!letterBean.getFieldSet6()[0].equals("BODY")) {
                    isValidate = false;
                    errorMessage = MessageVarList.LETTER_TEMPLATE_TEMPLATE_EMPTY;
                }
            }
            if (letterBean.getFieldSet7()[0].equals("") || letterBean.getFieldSet7()[1] == null || letterBean.getFieldSet7()[2] == null || letterBean.getFieldSet7()[1].equals("") || letterBean.getFieldSet7()[2].equals("")) {

                if (!letterBean.getFieldSet7()[0].equals("BODY")) {
                    isValidate = false;
                    errorMessage = MessageVarList.LETTER_TEMPLATE_TEMPLATE_EMPTY;
                }
            }





            if (tmpBodyBean.getFieldSetBody1()[0].equals("") || tmpBodyBean.getFieldSetBody1()[1].equals("") || tmpBodyBean.getFieldSetBody1()[2].equals("")) {

                isValidate = false;
                errorMessage = MessageVarList.LETTER_TEMPLATE_BODY_EMPTY;

            } else if (tmpBodyBean.getFieldSetBody2()[0].equals("") || tmpBodyBean.getFieldSetBody2()[1].equals("") || tmpBodyBean.getFieldSetBody2()[2].equals("")) {

                isValidate = false;
                errorMessage = MessageVarList.LETTER_TEMPLATE_BODY_EMPTY;

            } else if (tmpBodyBean.getFieldSetBody3()[0].equals("") || tmpBodyBean.getFieldSetBody3()[1].equals("") || tmpBodyBean.getFieldSetBody3()[2].equals("")) {

                isValidate = false;
                errorMessage = MessageVarList.LETTER_TEMPLATE_BODY_EMPTY;

            } else if (tmpBodyBean.getFieldSetBody4()[0].equals("") || tmpBodyBean.getFieldSetBody4()[1].equals("") || tmpBodyBean.getFieldSetBody4()[2].equals("")) {

                isValidate = false;
                errorMessage = MessageVarList.LETTER_TEMPLATE_BODY_EMPTY;

            } else if (tmpBodyBean.getFieldSetBody5()[0].equals("") || tmpBodyBean.getFieldSetBody5()[1].equals("") || tmpBodyBean.getFieldSetBody5()[2].equals("")) {

                isValidate = false;
                errorMessage = MessageVarList.LETTER_TEMPLATE_BODY_EMPTY;

            }




//
        } catch (Exception ex) {
            isValidate = false;
        }
        return isValidate;
    }

    /**
     * validate user inputs
     * @param bean
     * @return
     * @throws Exception 
     */
    public void createLetterFormat(LetterTemplateBean letterBean, LetterBodyFormatBean tmpBodyBean, String[] titleArray) throws Exception {


        try {

            if (letterBean.getFieldSet1()[0].equals("BODY")) {
                this.createLetterBodyFormat(tmpBodyBean);
            } else if (letterBean.getFieldSet1()[0].equals("TITLE")) {
                this.createLetterTitleFormat(titleArray);
            }

            if (letterBean.getFieldSet1()[0].equals("TEXT")) {

                letterFormat = letterBean.getFieldSet1()[3] + "|" + letterBean.getFieldSet1()[1] + "|" + letterBean.getFieldSet1()[2];
            } else {
                letterFormat = letterBean.getFieldSet1()[0] + "|" + letterBean.getFieldSet1()[1] + "|" + letterBean.getFieldSet1()[2];
            }



            if (letterBean.getFieldSet2()[0].equals("BODY")) {
//                letterFormat += "~";
                this.createLetterBodyFormat(tmpBodyBean);
            } else if (letterBean.getFieldSet2()[0].equals("TITLE")) {
//                letterFormat += "~";
                this.createLetterTitleFormat(titleArray);
            }


            if (letterBean.getFieldSet2()[0].equals("TEXT")) {

                letterFormat += "~" + letterBean.getFieldSet2()[3] + "|" + letterBean.getFieldSet2()[1] + "|" + letterBean.getFieldSet2()[2];

            } else {
                letterFormat += "~" + letterBean.getFieldSet2()[0] + "|" + letterBean.getFieldSet2()[1] + "|" + letterBean.getFieldSet2()[2];
            }


            if (letterBean.getFieldSet3()[0].equals("BODY")) {
//                letterFormat += "~";
                this.createLetterBodyFormat(tmpBodyBean);
            } else if (letterBean.getFieldSet3()[0].equals("TITLE")) {
//                letterFormat += "~";
                this.createLetterTitleFormat(titleArray);
            }

            if (letterBean.getFieldSet3()[0].equals("TEXT")) {
                letterFormat += "~" + letterBean.getFieldSet3()[3] + "|" + letterBean.getFieldSet3()[1] + "|" + letterBean.getFieldSet3()[2];
            } else {
                letterFormat += "~" + letterBean.getFieldSet3()[0] + "|" + letterBean.getFieldSet3()[1] + "|" + letterBean.getFieldSet3()[2];
            }




            if (letterBean.getFieldSet4()[0].equals("BODY")) {
//                letterFormat += "~";
                this.createLetterBodyFormat(tmpBodyBean);
            } else if (letterBean.getFieldSet4()[0].equals("TITLE")) {
//                letterFormat += "~";
                this.createLetterTitleFormat(titleArray);
            }

            if (letterBean.getFieldSet4()[0].equals("TEXT")) {

                letterFormat += "~" + letterBean.getFieldSet4()[3] + "|" + letterBean.getFieldSet4()[1] + "|" + letterBean.getFieldSet4()[2];
            } else {
                letterFormat += "~" + letterBean.getFieldSet4()[0] + "|" + letterBean.getFieldSet4()[1] + "|" + letterBean.getFieldSet4()[2];
            }




            if (letterBean.getFieldSet5()[0].equals("BODY")) {
//                letterFormat += "~";
                this.createLetterBodyFormat(tmpBodyBean);
            } else if (letterBean.getFieldSet5()[0].equals("TITLE")) {
//                letterFormat += "~";
                this.createLetterTitleFormat(titleArray);
            }
            if (letterBean.getFieldSet5()[0].equals("TEXT")) {
                letterFormat += "~" + letterBean.getFieldSet5()[3] + "|" + letterBean.getFieldSet5()[1] + "|" + letterBean.getFieldSet5()[2];
            } else {
                letterFormat += "~" + letterBean.getFieldSet5()[0] + "|" + letterBean.getFieldSet5()[1] + "|" + letterBean.getFieldSet5()[2];
            }




            if (letterBean.getFieldSet6()[0].equals("BODY")) {
//                letterFormat += "~";
                this.createLetterBodyFormat(tmpBodyBean);
            } else if (letterBean.getFieldSet6()[0].equals("TITLE")) {
//                letterFormat += "~";
                this.createLetterTitleFormat(titleArray);
            }
            if (letterBean.getFieldSet6()[0].equals("TEXT")) {
                letterFormat += "~" + letterBean.getFieldSet6()[3] + "|" + letterBean.getFieldSet6()[1] + "|" + letterBean.getFieldSet6()[2];
            } else {
                letterFormat += "~" + letterBean.getFieldSet6()[0] + "|" + letterBean.getFieldSet6()[1] + "|" + letterBean.getFieldSet6()[2];
            }



            if (letterBean.getFieldSet7()[0].equals("BODY")) {
//                letterFormat += "~";
                this.createLetterBodyFormat(tmpBodyBean);
            } else if (letterBean.getFieldSet7()[0].equals("TITLE")) {
//                letterFormat += "~";
                this.createLetterTitleFormat(titleArray);
            }
            if (letterBean.getFieldSet7()[0].equals("TEXT")) {

                letterFormat += "~" + letterBean.getFieldSet7()[3] + "|" + letterBean.getFieldSet7()[1] + "|" + letterBean.getFieldSet7()[2];
            } else {
                letterFormat += "~" + letterBean.getFieldSet7()[0] + "|" + letterBean.getFieldSet7()[1] + "|" + letterBean.getFieldSet7()[2];
            }




        } catch (Exception ex) {
        }

    }

    public void createLetterBodyFormat(LetterBodyFormatBean tmpBodyBean) throws Exception {


        try {


            if (tmpBodyBean.getFieldSetBody1()[0].equals("TEXT")) {

                bodyFormatString = tmpBodyBean.getFieldSetBody1()[3] + "|" + tmpBodyBean.getFieldSetBody1()[1] + "|" + tmpBodyBean.getFieldSetBody1()[2];

            } else {

                bodyFormatString = tmpBodyBean.getFieldSetBody1()[0] + "|" + tmpBodyBean.getFieldSetBody1()[1] + "|" + tmpBodyBean.getFieldSetBody1()[2];
            }

            if (tmpBodyBean.getFieldSetBody2()[0].equals("TEXT")) {

                bodyFormatString += "~" + tmpBodyBean.getFieldSetBody2()[3] + "|" + tmpBodyBean.getFieldSetBody2()[1] + "|" + tmpBodyBean.getFieldSetBody2()[2];

            } else {

                bodyFormatString += "~" + tmpBodyBean.getFieldSetBody2()[0] + "|" + tmpBodyBean.getFieldSetBody2()[1] + "|" + tmpBodyBean.getFieldSetBody2()[2];

            }

            if (tmpBodyBean.getFieldSetBody3()[0].equals("TEXT")) {

                bodyFormatString += "~" + tmpBodyBean.getFieldSetBody3()[3] + "|" + tmpBodyBean.getFieldSetBody3()[1] + "|" + tmpBodyBean.getFieldSetBody3()[2];

            } else {

                bodyFormatString += "~" + tmpBodyBean.getFieldSetBody3()[0] + "|" + tmpBodyBean.getFieldSetBody3()[1] + "|" + tmpBodyBean.getFieldSetBody3()[2];

            }

            if (tmpBodyBean.getFieldSetBody4()[0].equals("TEXT")) {

                bodyFormatString += "~" + tmpBodyBean.getFieldSetBody4()[3] + "|" + tmpBodyBean.getFieldSetBody4()[1] + "|" + tmpBodyBean.getFieldSetBody4()[2];

            } else {

                bodyFormatString += "~" + tmpBodyBean.getFieldSetBody4()[0] + "|" + tmpBodyBean.getFieldSetBody4()[1] + "|" + tmpBodyBean.getFieldSetBody4()[2];

            }

            if (tmpBodyBean.getFieldSetBody5()[0].equals("TEXT")) {

                bodyFormatString += "~" + tmpBodyBean.getFieldSetBody5()[3] + "|" + tmpBodyBean.getFieldSetBody5()[1] + "|" + tmpBodyBean.getFieldSetBody5()[2];

            } else {
                bodyFormatString += "~" + tmpBodyBean.getFieldSetBody5()[0] + "|" + tmpBodyBean.getFieldSetBody5()[1] + "|" + tmpBodyBean.getFieldSetBody5()[2];

            }
            tmpLetterBean.setBodyString(bodyFormatString);
//            letterFormat += bodyFormatString;
        } catch (Exception ex) {
        }

    }

    public void createLetterTitleFormat(String[] titleArray) throws Exception {


        try {


            titleFormatString = titleArray[0] + "|" + titleArray[1] + "|" + titleArray[2];


            tmpLetterBean.setTitleString(titleFormatString);

//            letterFormat += titleFormatString;



        } catch (Exception ex) {
        }

    }

    /**
     * to set the audit trace values
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("templatecode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Update Letter Template. Template Code: " + uniqueId + "; by:  " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.LETTERTEMPLATE);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue("");
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
