/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.cardtemplate.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.carddomaintemplate.businesslogic.CardDomainMgtManager;
import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.admin.templatemgt.cardtemplate.businesslogic.CardTemplateMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
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
 * @author nalin
 */
public class AddCardTemplateProfileServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CardTemplateMgtManager cardTemplateManager;
    private CardDomainMgtManager domainManager = null;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String errorMessage = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private CardTemplateBean cardBean;
    boolean success = false;
    private HashMap<String, String> currencyList = null;
    private HashMap<String, String> productList = null;
    private HashMap<String, String> interestProfileList = null;
    private HashMap<String, String> cardHolderFeeProfileList = null;
    private HashMap<String, String> accountTemplateList = null;
    private HashMap<String, String> customerTemplateList = null;
    private List<CardTemplateBean> searchList = null;
    private String url = "/administrator/templatemgt/cardtemplate/cardtemplateadd.jsp";

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

            request.setAttribute("selectedtab", "1");

            //set page code and task codes
            String pageCode = PageVarList.CARDTEMPLATEHOME;
            String taskCode = TaskVarList.ADD;

            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                this.setUserInputToBean(request);
                request.setAttribute("cardBean", cardBean);


                this.getAllCurrencyList();
                this.getAllAccountTemplateList();

                request.setAttribute("currencyList", currencyList);
                request.setAttribute("accountTemplateList", accountTemplateList);





                if (this.validateUserInput(cardBean, request)) {


                    this.setAudittraceValue(request);

                    if (this.insertCardTemplate(cardBean, systemAuditBean)) {

                        this.searchCardDomainMgt();

                        request.setAttribute("searchList", searchList);

                        request.setAttribute("successMsg", MessageVarList.SUCCESS_ADD_CARDDOMAIN_COMLETE);
                        rd = getServletContext().getRequestDispatcher("/LoadCardTemplateMgtServlet");
                   
                    } else {
                        this.searchCardDomainMgt();

                        request.setAttribute("searchList", searchList);
                        request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_CARDDOMAIN);
                        rd = getServletContext().getRequestDispatcher(url);
                    }
                } else {
                    this.searchCardDomainMgt();

                    request.setAttribute("searchList", searchList);
                    request.setAttribute("errorMsg", errorMessage);
                    rd = getServletContext().getRequestDispatcher(url);
                }

            } else {

                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }


        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher("/administrator/templatemgt/cardtemplate/cardtemplatehome.jsp");


        } catch (SQLException ex) {
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", cardBean.getTemplateCode() + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_CARDTEMPLATE);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
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

    public void setUserInputToBean(HttpServletRequest request) throws Exception {

        cardBean = new CardTemplateBean();

        cardBean = sessionVarlist.getCardBean();

        cardBean.setFeeProfCode(request.getParameter("feeProfileSelect").trim());
        cardBean.setRiskProfCode(request.getParameter("riskProfileDelect").trim());
        cardBean.setTxnProfCode(request.getParameter("txnProfileSelect").trim());

        try {
        } catch (Exception ex) {
            throw ex;
        }

    }
    
     public boolean validateUserInput(CardTemplateBean cardBean, HttpServletRequest request) throws Exception {
        boolean isValidate = true;


        //validate user Role code
        try {
            
            if (cardBean.getFeeProfCode().contentEquals("") || cardBean.getFeeProfCode().length() <= 0) {
                isValidate = false;
                
                errorMessage = MessageVarList.FEE_PROFILE_NULL;
            } else if (cardBean.getRiskProfCode().contentEquals("") || cardBean.getRiskProfCode().length() <= 0) {
                isValidate = false;
                
                errorMessage = MessageVarList.RISK_PROFILE_NULL;
            } else if (cardBean.getTxnProfCode().contentEquals("") || cardBean.getTxnProfCode().length() <= 0) {
                isValidate = false;
                
                errorMessage = MessageVarList.TRANSACTION_PROFILE_NULL;
            }



        } catch (Exception ex) {
            isValidate = false;

        }

        return isValidate;
    }
      

    private boolean insertCardTemplate(CardTemplateBean cardBean, SystemAuditBean systemAuditBean) throws Exception {

        try {

            cardTemplateManager = new CardTemplateMgtManager();
            success = cardTemplateManager.insertCardTemplate(cardBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(cardBean.getTemplateCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Add " + cardBean.getTemplateCode() + " User by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TEMPLATEMGT);
            systemAuditBean.setPageCode(PageVarList.CARDTEMPLATEHOME);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue("");


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }



    }

    private void searchCardDomainMgt() throws Exception {

        try {


            cardTemplateManager = new CardTemplateMgtManager();
            searchList = cardTemplateManager.getAllCardDomainSearchList();


        } catch (Exception ex) {
            throw ex;

        }
    }

    private void getAllCurrencyList() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            currencyList = domainManager.getAllCurrencyList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllAccountTemplateList() throws Exception {

        try {

            domainManager = new CardDomainMgtManager();
            accountTemplateList = domainManager.getAllAccountTemplateList();
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
