/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardBinMgtManager;
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
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author asitha_l
 */
public class AddBinMgtServlet extends HttpServlet {
    
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CardProductMgtManager cardProductMgtManager;
    private CardBinMgtManager cardBinMgtManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private HashMap<String, String> cardTypeList = null;
    private List<CardBinBean> cardBinBeanLst = null;
    private String errorMessage = null;
    private SystemAuditBean systemAuditBean = null;
    private CardBinBean cardBinBean;
    private String url = "/administrator/controlpanel/systemconfigmgt/binmanagementhome.jsp";
    private String newValue;

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
                String pageCode = PageVarList.BIN_MGT;
                String taskCode = TaskVarList.ADD;


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
            
            request.setAttribute("operationtype", "add");
            this.getAllCardTypeList();
            this.getAllBinDetailsList();

            request.setAttribute("cardTypeList", cardTypeList);
            request.setAttribute("cardBinBeanLst", cardBinBeanLst);

            this.setUserInputToBean(request);

            if (this.validateUserInput(cardBinBean)) {

                this.setAudittraceValue(request);
                this.insertBin(cardBinBean, systemAuditBean);

                request.setAttribute("successMsg", cardBinBean.getBinNumber() + " " + MessageVarList.CARD_BIN_SUCCESS_ADD);
                rd = getServletContext().getRequestDispatcher("/LoadBinMgtServlet");

            } else {

                request.setAttribute("cardBinBean", cardBinBean);
                request.setAttribute("errorMsg", errorMessage);
                rd = getServletContext().getRequestDispatcher(url);

            }

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
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemconfigmgt/binmanagementhome.jsp?errorMsg=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);


        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_CARD_BIN);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void getAllCardTypeList() throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            cardTypeList = cardProductMgtManager.getAllCardTypeList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllBinDetailsList() throws Exception {
        try {

            cardBinMgtManager = new CardBinMgtManager();
            cardBinBeanLst = cardBinMgtManager.getAllBinDetailsList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean insertBin(CardBinBean cardBinBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            cardBinMgtManager = new CardBinMgtManager();
            success = cardBinMgtManager.insertBin(cardBinBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    public void setUserInputToBean(HttpServletRequest request) {

        String binNumber = request.getParameter("binnumber").trim();
        String description = request.getParameter("binnumberdes").trim();
        String cardType = request.getParameter("cardtype").trim();

        cardBinBean = new CardBinBean();

        cardBinBean.setBinNumber(binNumber);
        cardBinBean.setDescription(description);

        cardBinBean.setOnus(request.getParameter("onus"));
        cardBinBean.setBinType(request.getParameter("bintype"));

        cardBinBean.setCardType(cardType);
        cardBinBean.setCurrTypeCode(request.getParameter("currType"));

        cardBinBean.setStatus(request.getParameter("status"));
        
        cardBinBean.setSendingChannel(request.getParameter("sendchanel"));
        cardBinBean.setLastupdateUser(sessionUser.getUserName());
                
        newValue = cardBinBean.getBinNumber() + "|" + cardBinBean.getDescription() + "|" + cardBinBean.getOnus() + "|"
                + cardBinBean.getBinType() + "|" + cardBinBean.getCardType() + "|" + cardBinBean.getCurrTypeCode() + "|"
                +cardBinBean.getStatus() + "|" + cardBinBean.getSendingChannel() + "|" + cardBinBean.getLastupdateUser();
    }

    public boolean validateUserInput(CardBinBean cardBinBean) throws Exception {
        boolean isValidate = true;

        //validate user Role code
        try {

            if (cardBinBean.getBinNumber().contentEquals("") || cardBinBean.getBinNumber().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_BINCODE_NULL;
            } else if (!UserInputValidator.isNumeric(cardBinBean.getBinNumber())|| cardBinBean.getBinNumber().length() != 6) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_BINCODE_INVALID;
            } else if (cardBinBean.getDescription().contentEquals("") || cardBinBean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_BINDESCRIPTION_NULL;
            } else if (!UserInputValidator.isDescription(cardBinBean.getDescription())) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_DES_INVALID;
            } else if (cardBinBean.getOnus() == null) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_ONUS_NULL;
            } else if (cardBinBean.getCardType().contentEquals("") || cardBinBean.getCardType().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_CARDTYPE_NULL;
            } else if (cardBinBean.getBinType().contentEquals("") || cardBinBean.getBinType().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_BINTYPE_NULL;
            } else if (cardBinBean.getCurrTypeCode().contentEquals("") || cardBinBean.getCurrTypeCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_CURRCODE_NULL;
            } else if (cardBinBean.getSendingChannel().equals("") || cardBinBean.getSendingChannel().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SENDING_CHANNEL_EMPTY;
            } else if (cardBinBean.getStatus().contentEquals("") || cardBinBean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_STATUS_NULL;
            }

        } catch (Exception ex) {
            isValidate = false;

        }

        return isValidate;
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

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(cardBinBean.getBinNumber());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add Bin. Bin Number: " + cardBinBean.getBinNumber() + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.BIN_MGT);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue(newValue);


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
