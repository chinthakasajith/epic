/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.camm.numbergeneration.servlet;

import com.epic.cms.admin.camm.numbergeneration.bean.NumberFormatFieldBean;
import com.epic.cms.admin.camm.numbergeneration.bean.NumberGenerationFormatBean;
import com.epic.cms.admin.camm.numbergeneration.businesslogic.NumberGeneFormatBusinessLogic;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CreditScoreRuleDefineManagement;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
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
public class ChangeOnChangeNumberFormatServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/administrator/controlpanel/systemconfigmgt/numbergenformat.jsp";
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private List<NumberFormatFieldBean> formatFieldBeans;
    private List<NumberFormatFieldBean> formatCodeBeans;
    private List<CardTypeMgtBean> cardTypeMgtBeanLst;
    private NumberGeneFormatBusinessLogic formatBusinessLogic = null;
    private String errorMessage = null;
    private NumberGenerationFormatBean formatBean = null;
    private HashMap<String, String> productModes = null;
    private HashMap<String, String> AssignBinList = null;
    private HashMap<String, String> NotAssignBinList = null;
    private HashMap<String, String> cardProductBinList = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sessionObj = request.getSession(false);
            try {
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
                    String pageCode = PageVarList.CARDNUMBERGENE;
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
            request.setAttribute("operationtype", request.getParameter("operationtype"));

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
//commented last
//            request.setAttribute("AssignbinList", assignedBin);
//            request.setAttribute("NotAssignbinList", notAssignedBin);

            formatBean = new NumberGenerationFormatBean();
            //retrieve production modes
            this.getProductionModes();
            //retrieve bins to get description
            this.getCardProductBins(request.getParameter("cardType"));
            //set values to the session
            this.setValuesToSessionWithDropDownChanges(request, assignedBin, notAssignedBin);
            //retrieve card type details
            this.getCardTypeDetails();
            //retrieve format names
            this.getAllNumberFormatFields();
            //retrive all the number format data
            this.getAllNumberFormatCodeDetails();
            request.setAttribute("formatBean", formatBean);//format bean contains from and to
            request.setAttribute("productModes", productModes);
            request.setAttribute("formatFieldBeans", formatFieldBeans);//contains format names
            request.setAttribute("formatCodeBeans", formatCodeBeans);// format code bean contains entire data set            
            request.setAttribute("cardTypeMgtBeanLst", cardTypeMgtBeanLst);
            request.setAttribute("AssignbinList", AssignBinList);
            request.setAttribute("NotAssignbinList", NotAssignBinList);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
            
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemconfigmgt/numbergenformat.jsp");
            rd.forward(request, response);

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);
        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.toString()));
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            
        }
    }

    /**
     * check task in valid for this action
     * @param userrole
     * @param pagecode
     * @param task
     * @return
     * @throws Exception 
     */
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

    private void setValuesToSessionWithDropDownChanges(HttpServletRequest request, String[] assign, String[] notAssign) throws Exception {
        try {

            //put the BIN values in the given arrays into lists
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
            //-----------------------------------------------------------------------------

            //set the common values to the request and to the bean
            request.setAttribute("numberFormatCode", request.getParameter("numberFormatCode"));
            formatBean.setFormatCode(request.getParameter("numberFormatCode"));
            request.setAttribute("numberFormatDesc", request.getParameter("numberFormatDesc"));
            formatBean.setDescription(request.getParameter("numberFormatDesc"));
            if (request.getParameter("operationtype").equals("add")) {
                request.setAttribute("cardTypeVar", request.getParameter("cardTypeVar"));
                formatBean.setCardType(request.getParameter("cardTypeVar"));
            }
            if (request.getParameter("operationtype").equals("update")) {
                request.setAttribute("cardTypeVar", sessionVarlist.getNumFormat().getCardType());
                formatBean.setCardType(sessionVarlist.getNumFormat().getCardType());
            }
            String disable = "";
            disable = sessionVarlist.getDisableProdMode();
            request.setAttribute("disable", disable);
            if (disable.equals("no")) {
                request.setAttribute("productionMode", request.getParameter("productionMode"));
                formatBean.setProductMode(request.getParameter("productionMode"));
            } else if (disable.equals("yes")) {
                request.setAttribute("productionMode", sessionVarlist.getNumFormat().getProductMode());
                formatBean.setProductMode(sessionVarlist.getNumFormat().getProductMode());
            }
            request.setAttribute("numberLength", request.getParameter("numberLength"));
            formatBean.setCardNumberLength(request.getParameter("numberLength"));
            if (request.getParameter("operationtype").equals("add")) {
            } else {
                formatBean.setCheckDigit(Integer.parseInt(request.getParameter("numberLength")));
                System.out.println(Integer.toString(formatBean.getCheckDigit()));
            }
            //--------------------------------------------------------------------------------------------

            //set the fixed format field values
            request.setAttribute("fromOne", request.getParameter("fromOne"));
            formatBean.setFromOne(Integer.parseInt(request.getParameter("fromOne")));
            request.setAttribute("toOne", request.getParameter("toOne"));
            formatBean.setToOne(Integer.parseInt(request.getParameter("toOne")));
            request.setAttribute("fromTwo", request.getParameter("fromTwo"));
            formatBean.setFromTwo(Integer.parseInt(request.getParameter("fromTwo")));

            //set the changing format field values
            String formatTwo = request.getParameter("formatTwo");
            String formatThree = request.getParameter("formatThree");
            String formatFour = request.getParameter("formatFour");
            String formatFive = request.getParameter("formatFive");
            String formatSix = request.getParameter("formatSix");
            String formatSeven = request.getParameter("formatSeven");

            request.setAttribute("toTwo", request.getParameter("toTwo"));
            if (!request.getParameter("toTwo").isEmpty()) {
                formatBean.setToTwo(Integer.parseInt(request.getParameter("toTwo")));
            }
            formatBean.setFormatTwo(request.getParameter("formatTwo"));
            formatBean.setFormatThree(request.getParameter("formatThree"));
            formatBean.setFormatFour(request.getParameter("formatFour"));
            formatBean.setFormatFive(request.getParameter("formatFive"));
            formatBean.setFormatSix(request.getParameter("formatSix"));
            formatBean.setFormatSeven(request.getParameter("formatSeven"));


//            formatBean.setFormatThree(request.getParameter("formatFour"));
//            String formatFive = request.getParameter("formatFive");
//            formatBean.setFormatFour(request.getParameter("formatFive"));
//            String formatSix = request.getParameter("formatSix");
//            formatBean.setFormatFive(request.getParameter("formatSix"));
//            String formatSeven = request.getParameter("formatSeven");
//            formatBean.setFormatSix(request.getParameter("formatSeven"));
            errorMessage = null;

            if (2 <= (Integer.parseInt(request.getParameter("formatFieldNo")))) {
                try {

                    if (formatBean.getToTwo() != 0) {
                        formatBean.setFormatTwo(request.getParameter("formatTwo"));
                    } else {
                        if (formatTwo != null || !formatTwo.equals("")) {
                            errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
                        }
                    }
                } catch (Exception ex) {
                    if (formatTwo != null || !formatTwo.equals("")) {
                        errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
                    }
                }
            }

            if (3 <= (Integer.parseInt(request.getParameter("formatFieldNo")))) {
                try {

                    if (!request.getParameter("toThree").equals("") && request.getParameter("toThree") != null) {
                        formatBean.setFromThree(Integer.parseInt(request.getParameter("fromThree")));
                        formatBean.setToThree(Integer.parseInt(request.getParameter("toThree")));
                        formatBean.setFormatThree(formatThree);
                    } else {
                        if (formatThree != null || !formatThree.equals("")) {
                            errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
                        }
                    }
                } catch (NullPointerException ex) {
                    if (formatThree != null) {
                        errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
                    }
                }
            }
            if (4 <= (Integer.parseInt(request.getParameter("formatFieldNo")))) {

                try {

                    if (!request.getParameter("toFour").equals("") && request.getParameter("toFour") != null) {
                        formatBean.setFromFour(Integer.parseInt(request.getParameter("fromFour")));
                        formatBean.setToFour(Integer.parseInt(request.getParameter("toFour")));
                        formatBean.setFormatFour(formatFour);
                    } else {
                        if (formatFour != null || !formatFour.equals("")) {
                            errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
                        }
                    }
                } catch (NullPointerException ex) {
                    if (formatFour != null) {
                        errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
                    }
                }
            }
            if (5 <= (Integer.parseInt(request.getParameter("formatFieldNo")))) {

                try {
                    if (!request.getParameter("toFive").equals("") && request.getParameter("toFive") != null) {
                        formatBean.setFromFive(Integer.parseInt(request.getParameter("fromFive")));
                        formatBean.setToFive(Integer.parseInt(request.getParameter("toFive")));
                        formatBean.setFormatFive(formatFive);
                    } else {
                        if (formatFive != null || !formatFive.equals("")) {
                            errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
                        }
                    }
                } catch (NullPointerException ex) {
                    if (formatFive != null) {
                        errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
                    }
                }
            }

            if (6 <= (Integer.parseInt(request.getParameter("formatFieldNo")))) {
                try {
                    if (!request.getParameter("toSix").equals("") && request.getParameter("toSix") != null) {
                        formatBean.setFromSix(Integer.parseInt(request.getParameter("fromSix")));
                        formatBean.setToSix(Integer.parseInt(request.getParameter("toSix")));
                        formatBean.setFormatSix(formatSix);
                    } else {
                        if (formatSix != null || !formatSix.equals("")) {
                            errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
                        }
                    }
                } catch (NullPointerException ex) {
                    if (formatSix != null) {
                        errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
                    }
                }
            }
            if (7 <= (Integer.parseInt(request.getParameter("formatFieldNo")))) {

                try {
                    if (!request.getParameter("toSeven").equals("") && request.getParameter("toSeven") != null) {
                        formatBean.setFromSeven(Integer.parseInt(request.getParameter("fromSeven")));
                        formatBean.setToSeven(Integer.parseInt(request.getParameter("toSeven")));
                        formatBean.setFormatSeven(formatSeven);
                    } else {
                        if (formatSeven != null || !formatSeven.equals("")) {
                            errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
                        }
                    }
                } catch (NullPointerException ex) {
                    if (formatSeven != null) {
                        errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
                    }
                }
            }



//            try {
//
//                if (formatBean.getToTwo() != 0) {
//                    formatBean.setFormatTwo(request.getParameter("formatTwo"));
//                } else {
//                    if (formatTwo != null || !formatTwo.equals("")) {
//                        errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
//                    }
//                }
//            } catch (Exception ex) {
//                if (formatTwo != null || !formatTwo.equals("")) {
//                    errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
//                }
//            }
//            
//            try {
//
//                if (!request.getParameter("toThree").equals("") && request.getParameter("toThree") != null) {
//                    formatBean.setFromThree(Integer.parseInt(request.getParameter("fromThree")));
//                    formatBean.setToThree(Integer.parseInt(request.getParameter("toThree")));
//                    formatBean.setFormatThree(formatThree);
//                } else {
//                    if (formatThree != null || !formatThree.equals("")) {
//                        errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
//                    }
//                }
//            } catch (NullPointerException ex) {
//                if (formatThree != null) {
//                    errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
//                }
//            }
//            
//            try {
//
//                if (!request.getParameter("toFour").equals("") && request.getParameter("toFour") != null) {
//                    formatBean.setFromFour(Integer.parseInt(request.getParameter("fromFour")));
//                    formatBean.setToFour(Integer.parseInt(request.getParameter("toFour")));
//                    formatBean.setFormatFour(formatFour);
//                } else {
//                    if (formatFour != null || !formatFour.equals("")) {
//                        errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
//                    }
//                }
//            } catch (NullPointerException ex) {
//                if (formatFour != null) {
//                    errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
//                }
//            }
//            
//            try {
//                if (!request.getParameter("toFive").equals("") && request.getParameter("toFive") != null) {
//                    formatBean.setFromFive(Integer.parseInt(request.getParameter("fromFive")));
//                    formatBean.setToFive(Integer.parseInt(request.getParameter("toFive")));
//                    formatBean.setFormatFive(formatFive);
//                } else {
//                    if (formatFive != null || !formatFive.equals("")) {
//                        errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
//                    }
//                }
//            } catch (NullPointerException ex) {
//                if (formatFive != null) {
//                    errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
//                }
//            }
//            
//            try {
//                if (!request.getParameter("toSix").equals("") && request.getParameter("toSix") != null) {
//                    formatBean.setFromSix(Integer.parseInt(request.getParameter("fromSix")));
//                    formatBean.setToSix(Integer.parseInt(request.getParameter("toSix")));
//                    formatBean.setFormatSix(formatSix);
//                } else {
//                    if (formatSix != null || !formatSix.equals("")) {
//                        errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
//                    }
//                }
//            } catch (NullPointerException ex) {
//                if (formatSix != null) {
//                    errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
//                }
//            }
//            
//            try {
//                if (!request.getParameter("toSeven").equals("") && request.getParameter("toSeven") != null) {
//                    formatBean.setFromSeven(Integer.parseInt(request.getParameter("fromSeven")));
//                    formatBean.setToSeven(Integer.parseInt(request.getParameter("toSeven")));
//                    formatBean.setFormatSeven(formatSix);
//                } else {
//                    if (formatSeven != null || !formatSeven.equals("")) {
//                        errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
//                    }
//                }
//            } catch (NullPointerException ex) {
//                if (formatSeven != null) {
//                    errorMessage = MessageVarList.NUMBER_FORMAT_ERROR_CAT_CHANGE;
//                }
//            }




            //set error message to the user
            request.setAttribute("errorMsg", errorMessage);




        } catch (Exception ex) {
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
