/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.camm.numbergeneration.servlet;

import com.epic.cms.admin.camm.numbergeneration.bean.NumberFormatBean;
import com.epic.cms.admin.camm.numbergeneration.bean.NumberFormatFieldBean;
import com.epic.cms.admin.camm.numbergeneration.bean.NumberGenerationFormatBean;
import com.epic.cms.admin.camm.numbergeneration.businesslogic.NumberGeneFormatBusinessLogic;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class LoadUpdateNumberFormatServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //comman for all pages
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SessionUser sessionUser = null;
    private RequestDispatcher rd = null;
    private SystemUserManager systemUserManager = null;
    //////////
    private String errorMessage = null;
    private NumberGeneFormatBusinessLogic formatBusinessLogic = null;
    private NumberFormatFieldBean formatFieldBean = null;
    private List<NumberFormatBean> numberFormatBeanList = null;
    private NumberGenerationFormatBean formatBean = null;
    private List<NumberFormatFieldBean> formatFieldBeans;
    private List<NumberFormatFieldBean> formatCodeBeans;
    private List<CardTypeMgtBean> cardTypeMgtBeanLst;
    HashMap<String, String> NotAssignBinList;
    HashMap<String, String> AssignBinList;
    int sequenceNoLength;
    private HashMap<String, String> UsedBinList = null;
    private String disable = "";
    private HashMap<String, String> productModes = null;

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

            ///////////////////////////////////////////////////////

            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.UPDATE)) {
                throw new AccessDeniedException();
            }
            request.setAttribute("operationtype", "update");
            String formatCode = request.getParameter("formatCode");

            //retrieve the production modes
            this.getProductionModes();
            //retrieve the record for the given format code and set to session
            this.getSelectedNumberFormatCode(formatCode);   
            sessionVarlist.setNumFormat(formatFieldBean);
            //retrieve the number format fields for the given format code
            this.getSelectedNumberFormatFields(formatCode);
            this.setNumberGenerationFormatBeanFormDataList();
            this.getSequnceNumber(formatCode);
            //set the sequence no length to the session
            sessionVarlist.setSequenceNoLength(sequenceNoLength);
            this.getAllNumberFormatFields();
            
            this.getAllNumberFormatCodeDetails();
            this.getCardTypeDetails();
            this.getAssignBinList(formatCode);
            this.getBinToCardType(formatBean.getCardType());
            //this.getNotAssignBinList(formatBean.getCardType(), formatCode);
            

            this.getSeqNoForUsedBINs(formatCode);
            this.disableProductionMode();

            request.setAttribute("disable", disable);

            request.setAttribute("productModes", productModes);
            request.setAttribute("formatBean", formatBean);
            request.setAttribute("formatCodeBeans", formatCodeBeans);
            request.setAttribute("formatFieldBeans", formatFieldBeans);
            request.setAttribute("formatCodeBeans", formatCodeBeans);
            request.setAttribute("cardTypeMgtBeanLst", cardTypeMgtBeanLst);
            request.setAttribute("AssignbinList", AssignBinList);
            request.setAttribute("NotAssignbinList", NotAssignBinList);
            String oldValue = formatBean.getFormatCode() + "|" + formatBean.getDescription() + "|"
                    + "" + formatBean.getCardType() + "|" + formatBean.getProductMode() + "|"
                    + "" + formatBean.getCardNumberLength();
            request.setAttribute("oldValue", oldValue);

            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
            //response.sendRedirect(request.getContextPath() + "/controlpanel/systemusermgt/viewsectionpage.jsp");
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemconfigmgt/numbergenformat.jsp");
            rd.forward(request, response);



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


        } catch (Exception ex) {
        } finally {
            out.close();
        }
    }

    /**
     * getAllNumberFormatCodeDetails
     * @return
     * @throws Exception 
     */
    public void getSelectedNumberFormatCode(String formatCode) throws Exception {

        try {
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            formatFieldBean = formatBusinessLogic.getSelectedNumberFormatCode(formatCode);
        } catch (SQLException ex) {
            throw ex;
        }

    }

    /**
     * getAllNumberFormatCodeDetails
     * @return
     * @throws Exception 
     */
    public void getSelectedNumberFormatFields(String formatCode) throws Exception {

        try {
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            numberFormatBeanList = formatBusinessLogic.getSelectedNumberFormatFields(formatCode);

        } catch (SQLException ex) {
            throw ex;
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

    /**
     * getCardTypeDetails
     * @return
     * @throws Exception 
     */
    public void getCardTypeDetails() throws Exception {

        try {
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            cardTypeMgtBeanLst = formatBusinessLogic.getCardTypeDetails();
        } catch (SQLException ex) {
            throw ex;
        }

    }

    /**
     * getAllNumberFormatFields
     * @return
     * @throws Exception 
     */
    public void getAllNumberFormatFields() throws Exception {

        try {
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            formatFieldBeans = formatBusinessLogic.getNumberFormatFieldDetails();
        } catch (SQLException ex) {
            throw ex;
        }

    }

    private void setNumberGenerationFormatBeanFormDataList() {
        try {
            formatBean = new NumberGenerationFormatBean();
            formatBean.setFormatCode(formatFieldBean.getFormatCode());
            formatBean.setCardType(formatFieldBean.getCardType());
            formatBean.setDescription(formatFieldBean.getDescription());
            formatBean.setCardNumberLength(formatFieldBean.getNumberLength());
            formatBean.setProductMode(formatFieldBean.getProductMode());

            int listSize = numberFormatBeanList.size();


            if (listSize > 0) {

                formatBean.setFromOne(numberFormatBeanList.get(0).getFromDigit());
                formatBean.setToOne(numberFormatBeanList.get(0).getToDigit());
                formatBean.setFormatOne(numberFormatBeanList.get(0).getFieldTypeCode());
                System.out.println("numberFormatBeanList.get(listSize).getFromDigit()" + numberFormatBeanList.get(listSize - 1).getFromDigit());
//                formatBean.setCheckDigit(numberFormatBeanList.get(listSize - 1).getFromDigit());
                formatBean.setCheckDigit(numberFormatBeanList.get(listSize - 1).getToDigit());

            }
            if (listSize > 1) {

                formatBean.setFromTwo(numberFormatBeanList.get(1).getFromDigit());
                formatBean.setToTwo(numberFormatBeanList.get(1).getToDigit());
                formatBean.setFormatTwo(numberFormatBeanList.get(1).getFieldTypeCode());


            }
            if (listSize > 2) {

                formatBean.setFromThree(numberFormatBeanList.get(2).getFromDigit());
                formatBean.setToThree(numberFormatBeanList.get(2).getToDigit());
                formatBean.setFormatThree(numberFormatBeanList.get(2).getFieldTypeCode());

            }
            if (listSize > 3) {

                formatBean.setFromFour(numberFormatBeanList.get(3).getFromDigit());
                formatBean.setToFour(numberFormatBeanList.get(3).getToDigit());
                formatBean.setFormatFour(numberFormatBeanList.get(3).getFieldTypeCode());


            }
            if (listSize > 4) {

                formatBean.setFromFive(numberFormatBeanList.get(4).getFromDigit());
                formatBean.setToFive(numberFormatBeanList.get(4).getToDigit());
                formatBean.setFormatFive(numberFormatBeanList.get(4).getFieldTypeCode());

            }
            if (listSize > 5) {

                formatBean.setFromSix(numberFormatBeanList.get(5).getFromDigit());
                formatBean.setToSix(numberFormatBeanList.get(6).getToDigit());
                formatBean.setFormatSix(numberFormatBeanList.get(6).getFieldTypeCode());


            }
            if (listSize > 6) {

                formatBean.setFromSeven(numberFormatBeanList.get(6).getFromDigit());
                formatBean.setToSeven(numberFormatBeanList.get(6).getToDigit());
                formatBean.setFormatSeven(numberFormatBeanList.get(6).getFieldTypeCode());

            }
            if (listSize > 7) {
////               formatBean.setFromFive(numberFormatBeanList.get(4).getFromDigit());
////               formatBean.setToFive(numberFormatBeanList.get(4).getToDigit());
////               formatBean.setFormatFive(numberFormatBeanList.get(4).getFieldTypeCode());
            }


        } catch (Exception ex) {
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

//    private void getNotAssignBinList(String cardType, String numFormatCode) throws Exception {
//        try {
//            
//            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
//            NotAssignBinList = formatBusinessLogic.getNotAssignBinList(cardType, numFormatCode);
//            
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
    private void getAssignBinList(String numFormatCode) throws Exception {
        try {

            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            AssignBinList = formatBusinessLogic.getAssignBinList(numFormatCode);

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void getSequnceNumber(String formatCode) throws Exception {

        try {
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            sequenceNoLength = formatBusinessLogic.getSequenceNumber(formatCode);
        } catch (SQLException ex) {
            throw ex;
        }

    }

    public void getProductionModes() throws Exception {

        try {
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            productModes = formatBusinessLogic.getProductionModes();
        } catch (SQLException ex) {
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

    private void disableProductionMode() {
        int seqUp = 0;
        disable = "no";
        sessionVarlist.setDisableProdMode(disable);
        for (String key : UsedBinList.keySet()) {
            if (UsedBinList.get(key) != null) {
                seqUp = Integer.parseInt(UsedBinList.get(key));
            }
            if (seqUp != 0) {
                disable = "yes";
                sessionVarlist.setDisableProdMode(disable);
                break;
            }

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
