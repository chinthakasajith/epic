/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardProductMgtManager;
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
 * @author chanuka
 */
public class UpdateCardProductServlet extends HttpServlet {

    String oldValue = "";
    String newValue = "";
    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private SessionVarList sessionVarlist;
    private SystemUserManager systemUserManager;
    private SystemAuditBean systemAuditBean = null;
    private CardProductMgtManager cardProductMgtManager;
    private String[] assignedBin;
    private String[] notAssignedBin;
    private String errorMessage = null;
    private CardProductBean cardProductBean;
    private List<String> AssignBinList = null;
    private List<String> NotAssignBinList = null;
    private HashMap<String, String> cardTypeList = null;
    private List<CardProductBean> cardProductBeanLst = null;
    private HashMap<String, String> cardDomainList = null;
    private List<CardProductBean> assignBinList;
    private List<CardProductBean> notAssignBinList;
    private CardProductBean bean = null;
    private List<CardProductBean> allBinList;
    private String url = "/administrator/controlpanel/systemconfigmgt/cardproductmgthome.jsp";

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
                String taskCode = TaskVarList.UPDATE;

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

            this.getAllCardTypeList();
            this.getAllCardDomainList();
            this.getAllCardProductDetailsList();

            request.setAttribute("cardTypeList", cardTypeList);
            request.setAttribute("cardDomainList", cardDomainList);
            request.setAttribute("cardProductBeanLst", cardProductBeanLst);


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

            this.setUserInputToBean(request, assignedBin, notAssignedBin);

            if (this.validateUserInput(cardProductBean, assignedBin)) {

                request.setAttribute("operationtype", "add");
                this.setAudittraceValue(request);
                this.updateCardProduct(cardProductBean, sessionVarlist.getCardProductBinBeanLst(), systemAuditBean, request.getParameter("productcode"));
                request.setAttribute("successMsg", cardProductBean.getProductCode() + " " + MessageVarList.CARD_PRODUCT_SUCCESS_UPDATE);
                rd = getServletContext().getRequestDispatcher("/LoadCardProductServlet");
                sessionVarlist.setCardProductBinBeanLst(null);
            } else {
                request.setAttribute("isBack", "yes");
                request.setAttribute("operationtype", "update");
                request.setAttribute("errorMsg", errorMessage);
                request.setAttribute("AssignbinList", assignBinList);
                request.setAttribute("NotAssignbinList", notAssignBinList);
                request.setAttribute("cardProductBean", cardProductBean);

                rd = getServletContext().getRequestDispatcher("/UpdateCardProductFormServlet");

            }

        }//catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        }//catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemconfigmgt/cardproductmgthome.jsp?errorMsg=" + MessageVarList.ACCESS_DENIED_TASK);



        } catch (SQLException ex) {
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", cardProductBean.getProductCode() + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_UPDATE_CARD_PRODUCT);
            rd = getServletContext().getRequestDispatcher(url);


        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * to retrieve all the card types
     * @throws Exception 
     */
    private void getAllCardTypeList() throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            cardTypeList = cardProductMgtManager.getAllCardTypeList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to retrieve all the card product details
     * @throws Exception 
     */
    private void getAllCardProductDetailsList() throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            cardProductBeanLst = cardProductMgtManager.getAllCardProductDetailsList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * set user input
     * @param request
     * @param assign
     * @param notAssign 
     */
    public void setUserInputToBean(HttpServletRequest request, String[] assign, String[] notAssign) throws Exception {

        cardProductBean = new CardProductBean();

        cardProductBean.setProductCode(request.getParameter("productcode"));
        cardProductBean.setDescription(request.getParameter("productcodedes"));
        cardProductBean.setCardType(request.getParameter("cardtype"));
        cardProductBean.setDomainCode(request.getParameter("carddomain"));
        cardProductBean.setValidityPeriod(request.getParameter("validity"));
        cardProductBean.setStatus(request.getParameter("statuscode"));
        cardProductBean.setDisplayDigit(request.getParameter("displayDigit"));
        cardProductBean.setLastUpdateUser(sessionUser.getUserName());

        //oldValue = request.getParameter("oldValue");
        newValue = cardProductBean.getProductCode() + "|" + cardProductBean.getDescription() + "|"
                + cardProductBean.getDomainCode() + "|" + cardProductBean.getCardType() + "|"
                + cardProductBean.getValidityPeriod() + "|" + cardProductBean.getDisplayDigit() + "|" + cardProductBean.getStatus();

        //put the values in the arays into lists
//        int k = 0;
//        AssignBinList = new ArrayList<String>();
//        if (assign.length != 0) {
//            while (assign.length > k) {
//
//                AssignBinList.add(assign[k]);
//                k++;
//            }
//        }
//        int l = 0;
//        NotAssignBinList = new ArrayList<String>();
//        while (notAssign.length > l) {
//
//            NotAssignBinList.add(notAssign[l]);
//            l++;
//        }

        cardProductMgtManager = new CardProductMgtManager();
        allBinList = cardProductMgtManager.getCardProductBins(request.getParameter("cardtype"), request.getParameter("carddomain"));

        int k = 0;
        assignBinList = new ArrayList<CardProductBean>();
        if (assign.length != 0) {
            while (assign.length > k) {
                bean = new CardProductBean();
                bean.setBin(assign[k]);
                for (int i = 0; i < allBinList.size(); i++) {
                    if (bean.getBin().equals(allBinList.get(i).getBin())) {
                        bean.setBinDes(allBinList.get(i).getBinDes());
                    }
                }
                assignBinList.add(bean);
                k++;
            }
        }

        int l = 0;
        notAssignBinList = new ArrayList<CardProductBean>();
        while (notAssign.length > l) {
            bean = new CardProductBean();
            bean.setBin(notAssign[l]);
            for (int i = 0; i < allBinList.size(); i++) {
                if (bean.getBin().equals(allBinList.get(i).getBin())) {
                    bean.setBinDes(allBinList.get(i).getBinDes());
                }
            }
            notAssignBinList.add(bean);
            l++;
        }


        this.getAllCardProductDetailsList();

        for (CardProductBean bean : cardProductBeanLst) {
            if (bean.getProductCode().equals(cardProductBean.getProductCode())) {
                oldValue = bean.getProductCode() + "|" + bean.getDescription() + "|"
                        + "" + bean.getDomainCode() + "|" + bean.getCardType() + "|"
                        + "" + bean.getValidityPeriod() + "|" + bean.getDisplayDigit() + "|" + bean.getStatus();
            }
        }

    }

    /**
     * to validate user input
     * @param cardProductBean
     * @param assignedBin
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(CardProductBean cardProductBean, String[] assignedBin) throws Exception {
        boolean isValidate = true;

        try {

            if (cardProductBean.getProductCode().contentEquals("") || cardProductBean.getProductCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCTCODE_NULL;
            } else if (!UserInputValidator.isAlphaNumeric(cardProductBean.getProductCode())) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCTCODE_INVALID;
            } else if (cardProductBean.getDescription().contentEquals("") || cardProductBean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCTDESCRIPTION_NULL;
            } else if (!UserInputValidator.isCorrectString(cardProductBean.getDescription()) || cardProductBean.getDescription().contentEquals("'") || cardProductBean.getDescription().contentEquals("'''")) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCTDES_INVALID;
            } else if (cardProductBean.getDomainCode().contentEquals("") || cardProductBean.getDomainCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_CARDDOMAIN_NULL;
            } else if (cardProductBean.getCardType().contentEquals("") || cardProductBean.getCardType().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_CARDTYPE_NULL;
            } else if (cardProductBean.getValidityPeriod().contentEquals("") || cardProductBean.getValidityPeriod().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_VALIDITY_NULL;
            } else if (!UserInputValidator.isAlphaNumeric(cardProductBean.getValidityPeriod())) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_VALIDITY_INVALID;
            } else if (cardProductBean.getStatus().contentEquals("") || cardProductBean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCTSTATUS_NULL;
            /** Hide display digit for DFCC
            } else if (cardProductBean.getDisplayDigit().contentEquals("") || cardProductBean.getDisplayDigit().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCT_DISPLAYDIGIT_NULL;
            } else if (!UserInputValidator.isNumeric(cardProductBean.getDisplayDigit())) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCT_DISPLAYDIGIT_INVALID;
            } else if (!(cardProductBean.getDisplayDigit().length() == 3)) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCT_DISPLAYDIGIT_INVALID_LENGTH;
            **/
            } else if (assignedBin == null || assignedBin.length <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BIN_NULL;
            }


//******************//
            int count = 0;
            int count1 = 0;
            int count2 = 0;
            for (CardProductBean cardProductBean1 : assignBinList) {
                for (CardProductBean cardProductBean2 : sessionVarlist.getCardProductBinBeanLst()) {
                    if (cardProductBean1.getBin().equals(cardProductBean2.getBin())) {
                        count++;
                    }
                    if (count > 0) {
                        count2 = -1;
                    } else {
                        count = 0;
                        count2 = -2;
                    }
                }
                if (count2 == -1) {
                    count = 0;
                    count1++;
                    count2 = 0;
                }
            }

            if (isValidate && assignBinList.size() != count1) {
                isValidate = false;
                errorMessage = MessageVarList.IS_ADDED_CARD_KEY_FOR_ALL_BIN;
            }


        } catch (Exception ex) {
            isValidate = false;

        }

        return isValidate;
    }

    /**
     * to update existing card product
     * @param cardProductBean
     * @param assignBin
     * @param systemAuditBean
     * @param prodCode
     * @return
     * @throws Exception 
     */
    public boolean updateCardProduct(CardProductBean cardProductBean, List<CardProductBean> assignBinList, SystemAuditBean systemAuditBean, String prodCode) throws Exception {
        boolean success = false;
        int row = -1;
        try {
            cardProductMgtManager = new CardProductMgtManager();
            row = cardProductMgtManager.updateCardProduct(cardProductBean, assignBinList, systemAuditBean, prodCode);
            if (row == 1) {
                success = true;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    /**
     * to check whether user has valid access to the required task
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

    /**
     * to set values to the audit trace
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(cardProductBean.getProductCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Card Product. Card Product Code: '" + cardProductBean.getProductCode() + "' by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.CARDPRODUCT);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(oldValue);
            //set new value of change if required
            systemAuditBean.setNewValue(newValue);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }



    }

    private void getAllCardDomainList() throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            cardDomainList = cardProductMgtManager.getAllCardDomainList();

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
