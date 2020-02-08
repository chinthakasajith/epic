/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.cardembossing.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.cpmm.cardembossing.bean.CardQualityMgtBean;
import com.epic.cms.cpmm.cardembossing.businesslogic.CardQualityMgtManager;
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
public class UpdateQualityMgtServlet extends HttpServlet {

    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    HttpSession sessionObj = null;
    private SystemUserManager systemUserManager = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> userTaskList;
    private RequestDispatcher rd;
    private String errorMessage;
    private CardQualityMgtBean cardBean = null;
    private CardQualityMgtManager cardManager = null;
    private String url = "/cpmm/cardembossing/cardqualitymgthome.jsp";

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
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
            /////////////////////////////////////////////////////////////////////


            try {
                //set page code and task codes
                String pageCode = PageVarList.CARD_QUALITY_MGT;
                String taskCode = TaskVarList.REJECT;


                //check whether user_role have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {



                    try {
                        if (this.setUserInputToBean(request)) {

                            //validate user input
                            if (validateUserInput(cardBean)) {
                                request.setAttribute("operationType", "UPDATE");
                                //set values to the audit trace
                                this.setAudittraceValue(request);

                                try {


                                    cardManager = new CardQualityMgtManager();
                                    boolean success = cardManager.failCardQuality(cardBean, systemAuditBean);

                                    if (success == true) {
                                        request.setAttribute("operationType", "SEARCH");
                                        request.setAttribute("successMsg", MessageVarList.QUALITY_FAIL_SUCCESS);
                                    }

                                    rd = getServletContext().getRequestDispatcher("/LoadQualityMgtServlet");
                                    rd.include(request, response);

                                } catch (SQLException e) {

                                    OracleMessage message = new OracleMessage();
                                    String oraMessage = message.getMessege(e.getMessage());

                                    request.setAttribute("operationType", "UPDATE");
                                    request.setAttribute("errorMsg", oraMessage);
                                    request.setAttribute("cardBean", cardBean);
                                    rd = getServletContext().getRequestDispatcher(url);
                                }
                            } else {
                                request.setAttribute("operationType", "UPDATE");
                                request.setAttribute("cardBean", cardBean);
                                request.setAttribute("errorMsg", errorMessage);
                                rd = getServletContext().getRequestDispatcher(url);

                            }
                        } else {

                            request.setAttribute("operationType", "UPDATE");
                            request.setAttribute("errorMsg", errorMessage);
                            rd = getServletContext().getRequestDispatcher(url);

                        }



                    } catch (Exception e) {

                        request.setAttribute("operationType", "UPDATE");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);

                    }

                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }


                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                throw adex;

            }



        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * check if user has valid access to the page
     *
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
     * set user input to bean
     *
     * @param request
     * @return
     * @throws Exception
     */
    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {
            cardBean = new CardQualityMgtBean();


            cardBean.setCardNumber(request.getParameter("cardNumber").trim());
            cardBean.setCardType(request.getParameter("cardType").trim());
            cardBean.setCardProduct(request.getParameter("cardProduct").trim());
            cardBean.setCardExpDate(request.getParameter("cardExpDate").trim());
            cardBean.setNameOnCard(request.getParameter("nameOnCard").trim());
            cardBean.setRemarks(request.getParameter("remarks").trim());
            cardBean.setLastUpdatedUser(sessionUser.getUserName());




        } catch (Exception e) {
            status = false;
            throw e;

        }

        return status;
    }

    /**
     * set values for audit trace
     *
     * @param request
     * @throws Exception
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = cardBean.getCardNumber();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Quality Managment. Card Number: " + cardBean.getCardNumber() + "; by : " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CPMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.EMBOSSINGMGT);
            systemAuditBean.setPageCode(PageVarList.CARD_QUALITY_MGT);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
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

    /**
     * validate user input
     *
     * @param serverConfigBean
     * @return
     * @throws Exception
     */
    public boolean validateUserInput(CardQualityMgtBean cardBean) throws Exception {
        boolean isValidate = true;

        try {
            if (cardBean.getRemarks().contentEquals("") || cardBean.getRemarks().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.REMARK_NULL;

            } else if (!UserInputValidator.isDescription(cardBean.getRemarks())) {
                isValidate = false;
                errorMessage = MessageVarList.REMARK_INVALID;

            }
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
