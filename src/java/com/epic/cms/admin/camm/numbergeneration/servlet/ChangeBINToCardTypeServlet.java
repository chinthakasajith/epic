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
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
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
 * @author nisansala
 */
public class ChangeBINToCardTypeServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private List<String> userTaskList;
    private HashMap<String, String> productModes = null;
    private HashMap<String, String> preAssignBinList = null;
    //private HashMap<String, String> AssignBinList = null;
    private HashMap<String, String> NotAssignBinList = null;
    private HashMap<String, String> cardProductBinList = null;
    private List<NumberFormatFieldBean> formatFieldBeans;
    private List<NumberFormatFieldBean> formatCodeBeans;
    private List<CardTypeMgtBean> cardTypeMgtBeanLst;
    private NumberGeneFormatBusinessLogic formatBusinessLogic = null;
    private NumberGenerationFormatBean formatBean = null;
    private String url = "/administrator/controlpanel/systemconfigmgt/numbergenformat.jsp";

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
                String pageCode = PageVarList.CARDPRODUCT;
                String taskCode = TaskVarList.ACCESSPAGE;

                //check whethre userrole have an access for this page and task
                if (!this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();
                }

                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);

            } catch (AccessDeniedException adex) {
                throw adex;
            }
            
            //set the operation type
            if (request.getParameter("operationtype").equals("add")) {
                request.setAttribute("operationtype", "add");
            } else if (request.getParameter("operationtype").equals("update")) {
                request.setAttribute("operationtype", "update");
            }

            formatBean = new NumberGenerationFormatBean();

            //retrieve all the card types
            this.getCardTypeDetails();
            //retrieve all the number format details
            this.getAllNumberFormatCodeDetails();
            //retrieve production modes
            this.getProductionModes();
            //set values to the session
            this.setValuesToSessionWithDropDownChanges(request);
            //retrieve all the number format fields
            this.getAllNumberFormatFields();
            //retrieve the BINs relevent to given card type
            if (request.getParameter("operationtype").equals("add")) {
                this.getBinToCardType(request.getParameter("cardTypeVar"), request.getParameter("prodMode"));
            } else if (request.getParameter("operationtype").equals("update")) {
                if(sessionVarlist.getNumFormat().getProductMode().equals(request.getParameter("prodMode"))){
                    this.getAssignBinListOnCardTypeReset(request.getParameter("numberFormatCode"));
                }
                else{
                    preAssignBinList = new HashMap<String, String>();
                    if(!preAssignBinList.isEmpty()){
                    preAssignBinList.clear();
                }}
                
                this.getBinToCardType(sessionVarlist.getNumFormat().getCardType(), request.getParameter("prodMode"));
            }  
            request.setAttribute("AssignbinList", preAssignBinList);
            request.setAttribute("disable", "no");
            request.setAttribute("formatBean", formatBean);
            request.setAttribute("formatFieldBeans", formatFieldBeans);
            request.setAttribute("formatCodeBeans", formatCodeBeans);
            request.setAttribute("cardTypeMgtBeanLst", cardTypeMgtBeanLst);
            request.setAttribute("productModes", productModes);
            request.setAttribute("NotAssignbinList", NotAssignBinList);
            request.setAttribute("formatCodeBeans", formatCodeBeans);
            request.setAttribute("formatFieldBeans", formatFieldBeans);
            request.setAttribute("formatCodeBeans", formatCodeBeans);
            request.setAttribute("cardTypeMgtBeanLst", cardTypeMgtBeanLst);


            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
            rd = getServletContext().getRequestDispatcher(url);

        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        }//catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_NUM_GEN);
            rd = request.getRequestDispatcher(url);

        } finally {
            //rd.forward(request, response);
            rd.include(request, response);
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

    private void setValuesToSessionWithDropDownChanges(HttpServletRequest request) throws Exception {
        try {

            request.setAttribute("numberFormatCode", request.getParameter("numberFormatCode"));
            formatBean.setFormatCode(request.getParameter("numberFormatCode"));
            request.setAttribute("numberFormatDesc", request.getParameter("numberFormatDesc"));
            formatBean.setDescription(request.getParameter("numberFormatDesc"));
            if (request.getParameter("operationtype").equals("add")) {
                request.setAttribute("cardTypeVar", request.getParameter("cardTypeVar"));
                formatBean.setCardType(request.getParameter("cardTypeVar"));

            } else if (request.getParameter("operationtype").equals("update")) {
                request.setAttribute("cardTypeVar", sessionVarlist.getNumFormat().getCardType());
                formatBean.setCardType(sessionVarlist.getNumFormat().getCardType());
            }

//            request.setAttribute("cardTypeVar", request.getParameter("cardTypeVar"));
//            formatBean.setCardType(request.getParameter("cardTypeVar"));
//            if (!request.getParameter("productionMode").equals("")) {
//                request.setAttribute("productionMode", request.getParameter("productionMode"));
//                formatBean.setProductMode(request.getParameter("productionMode"));
//            }

            if (request.getParameter("operationtype").equals("add")) {
                //request.setAttribute("numberLength", request.getParameter("numberLength"));
            } else {

                formatBean.setCheckDigit(Integer.parseInt(request.getParameter("numberLength")));
                //System.out.println(Integer.toString(formatBean.getCheckDigit()));

            }
            request.setAttribute("numberLength", request.getParameter("numberLength"));
            formatBean.setCardNumberLength(request.getParameter("numberLength"));
            request.setAttribute("fromOne", request.getParameter("fromOne"));
            formatBean.setFromOne(Integer.parseInt(request.getParameter("fromOne")));
            request.setAttribute("toOne", request.getParameter("toOne"));
            formatBean.setToOne(Integer.parseInt(request.getParameter("toOne")));
            request.setAttribute("fromTwo", request.getParameter("fromTwo"));
            formatBean.setFromTwo(Integer.parseInt(request.getParameter("fromTwo")));
            if (!request.getParameter("toTwo").equals("")) {
                request.setAttribute("toTwo", request.getParameter("toTwo"));
                formatBean.setToTwo(Integer.parseInt(request.getParameter("toTwo")));
            }

            String formatTwo = request.getParameter("formatTwo");
            String formatThree = request.getParameter("formatThree");
            String formatFour = request.getParameter("formatFour");
            String formatFive = request.getParameter("formatFive");
            String formatSix = request.getParameter("formatSix");
            String formatSeven = request.getParameter("formatSeven");
            formatBean.setFormatTwo(request.getParameter("formatTwo"));
            formatBean.setFormatThree(request.getParameter("formatThree"));
            formatBean.setFormatFour(request.getParameter("formatFour"));
            formatBean.setFormatFive(request.getParameter("formatFive"));
            formatBean.setFormatSix(request.getParameter("formatSix"));
            //formatBean.setFormatSeven(request.getParameter("formatSeven"));//added and commented

//            formatBean.setFormatThree(request.getParameter("formatFour"));            
//            formatBean.setFormatFour(request.getParameter("formatFive"));            
//            formatBean.setFormatFive(request.getParameter("formatSix"));            
//            formatBean.setFormatSix(request.getParameter("formatSeven"));//commented

            int minusLastDigit = 0;

            if (!request.getParameter("lastDigit").equals("")) {
                minusLastDigit = (Integer.parseInt(request.getParameter("lastDigit")) - 1);
            }
            String endBeforeLastDigit = String.valueOf(minusLastDigit);

            if (!request.getParameter("toTwo").equals(endBeforeLastDigit) && !request.getParameter("toTwo").equals("")) {
                if (!request.getParameter("fromThree").equals("")) {
                    request.setAttribute("fromThree", request.getParameter("fromThree"));
                    formatBean.setFromThree(Integer.parseInt(request.getParameter("fromThree")));
                }
                if (!request.getParameter("toThree").equals("")) {
                    request.setAttribute("toThree", request.getParameter("toThree"));
                    formatBean.setToThree(Integer.parseInt(request.getParameter("toThree")));
                }

                if (!request.getParameter("toThree").equals(endBeforeLastDigit) && !request.getParameter("toThree").equals("")) {

                    if (!request.getParameter("fromFour").equals("")) {
                        request.setAttribute("fromFour", request.getParameter("fromFour"));
                        formatBean.setFromFour(Integer.parseInt(request.getParameter("fromFour")));
                    }
                    if (!request.getParameter("toFour").equals("")) {
                        request.setAttribute("toFour", request.getParameter("toFour"));
                        formatBean.setToFour(Integer.parseInt(request.getParameter("toFour")));
                    }
                    if (!request.getParameter("toFour").equals(endBeforeLastDigit) && !request.getParameter("toFour").equals("")) {

                        if (!request.getParameter("fromFive").equals("")) {
                            request.setAttribute("fromFive", request.getParameter("fromFive"));
                            formatBean.setFromFive(Integer.parseInt(request.getParameter("fromFive")));
                        }
                        if (!request.getParameter("toFive").equals("")) {
                            request.setAttribute("toFive", request.getParameter("toFive"));
                            formatBean.setToFive(Integer.parseInt(request.getParameter("toFive")));
                        }
                        if (!request.getParameter("toFive").equals(endBeforeLastDigit) && !request.getParameter("toFive").equals("")) {
                            if (!request.getParameter("fromSix").equals("")) {
                                request.setAttribute("fromSix", request.getParameter("fromSix"));
                                formatBean.setFromSix(Integer.parseInt(request.getParameter("fromSix")));
                            }
                            if (!request.getParameter("toSix").equals("")) {
                                request.setAttribute("toSix", request.getParameter("toSix"));
                                formatBean.setToSix(Integer.parseInt(request.getParameter("toSix")));
                            }
                            if (!request.getParameter("toSix").equals(endBeforeLastDigit) && !request.getParameter("toSix").equals("")) {

                                if (!request.getParameter("fromSeven").equals("")) {
                                    request.setAttribute("fromSeven", request.getParameter("fromSeven"));
                                    formatBean.setFromSeven(Integer.parseInt(request.getParameter("fromSeven")));
                                }
                                if (!request.getParameter("toSeven").equals("")) {
                                    request.setAttribute("toSeven", request.getParameter("toSeven"));
                                    formatBean.setToSeven(Integer.parseInt(request.getParameter("toSeven")));
                                }
                            }
                        }
                    }
                }
            }


            //set error message to the user
            request.setAttribute("errorMsg", errorMessage);




        } catch (Exception ex) {
            ex.printStackTrace();
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

    private void getBinToCardType(String cardType, String productionMode) throws Exception {
        try {

            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            NotAssignBinList = formatBusinessLogic.getBinToCardType(cardType);

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

    public void getProductionModes() throws Exception {

        try {
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            productModes = formatBusinessLogic.getProductionModes();
        } catch (SQLException ex) {
            throw ex;
        }

    }
    
    private void getAssignBinListOnCardTypeReset(String numFormatCode) throws Exception {
        try {
            
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            preAssignBinList = formatBusinessLogic.getAssignBinListOnCardTypeReset(numFormatCode);
            
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
