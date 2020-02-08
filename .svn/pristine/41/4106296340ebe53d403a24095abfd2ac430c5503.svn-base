/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;


import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ProductionModeBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfirmation.bean.DebitApplicationDetailBean;
import com.epic.cms.camm.applicationconfirmation.bean.DebitCardAccountTemplateBean;
import com.epic.cms.camm.applicationconfirmation.bean.RecommendSchemBean;
import com.epic.cms.camm.applicationconfirmation.bean.RejectReasonBean;
import com.epic.cms.camm.applicationconfirmation.businesslogic.DebitApplicationConfirmationManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
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
import java.util.ArrayList;
import java.util.List;
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
public class LoadDebitApproveDetailsServlet extends HttpServlet {

   
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private RequestDispatcher rd;
    private List<String> userTaskList;
    private String url = "/camm/applicationconfirmation/debitapplicationapproveview.jsp";
    private ApplicationCheckingManager checkingmanager;
    private CardApplicationBean cardApplicationList;
    private DebitApplicationDetailBean personalDetail;
    private List<RejectReasonBean> rejectReasons;
    private List<ProductionModeBean> productionModeListt;
    private List<RecommendSchemBean> cardProductList;
    private DebitApplicationConfirmationManager debitAppConfirmManager;
    private List<DebitCardAccountTemplateBean> accountTempList;
    private DebitApplicationConfirmationManager confirmmanager;

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
            ////////////////////////////////////////////////////////////////////////////////
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

                try {
                    //set page code and task codes
                    String pageCode = PageVarList.DEBITAPPLICATONAPPROVE;
                    String taskCode = TaskVarList.ACCESSPAGE;


                    //check whethre userrole have an access for this page and task          
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        // is valid acess nothing to do
                    } else {

                        //if invalid throw accessdenied exception
                        throw new AccessDeniedException();

                    }

                    //set tasks to the session
                    sessionVarlist.setUserPageTaskList(userTaskList);


                } catch (AccessDeniedException adex) {
                    throw adex;

                }

            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }
            /////////////////////////////////////////////////////////////////////
            request.setAttribute("selectedtab", "0");
            try {

                String applicationId = request.getParameter("applicationid");

                request.setAttribute("selectedtab", "0");

                if (applicationId == null) {
                    applicationId = request.getAttribute("applicationid").toString();
                }

                this.getCardApplicationDetails(applicationId);
                this.getDebitCardApplicationDetails(applicationId);
                this.getRejectReasonList();
                this.getCardProducts();
//                this.getProductionMode();
                this.getAccountTemplates();

               
                
                request.setAttribute("cardApplicationList", cardApplicationList);
                request.setAttribute("personalDetail", personalDetail);
                request.setAttribute("rejectReasons", rejectReasons);
                request.setAttribute("cardProductList", cardProductList);
//                request.setAttribute("productionModeListt", productionModeListt);
                request.setAttribute("accountTempList", accountTempList);

                sessionVarlist.setCardType(personalDetail.getReqCardType());
                

                rd = request.getRequestDispatcher(url);
                rd.forward(request, response);


            } catch (Exception e) {
                request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
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

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
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

    private void getRejectReasonList() throws Exception {
        rejectReasons = new ArrayList<RejectReasonBean>();
        try {
            checkingmanager = new ApplicationCheckingManager();
            rejectReasons = checkingmanager.getRejectReasons();
        } catch (Exception e) {
            throw e;
        }
    }

    private void getProductionMode() throws Exception {
        productionModeListt = new ArrayList<ProductionModeBean>();
        try {
            checkingmanager = new ApplicationCheckingManager();
            productionModeListt = checkingmanager.getProductionMode();
        } catch (Exception e) {
            throw e;
        }
    }
    //getCardProducts

    private void getCardProducts() throws Exception {
        cardProductList = new ArrayList<RecommendSchemBean>();
        try {
            debitAppConfirmManager = new DebitApplicationConfirmationManager();
            cardProductList = debitAppConfirmManager.getCardProducts(personalDetail.getReqCardType());

        } catch (Exception e) {
            throw e;
        }
    }
    //getAccountTemplates

    private void getAccountTemplates() throws Exception {
        accountTempList = new ArrayList<DebitCardAccountTemplateBean>();
        try {
            debitAppConfirmManager = new DebitApplicationConfirmationManager();
            accountTempList = debitAppConfirmManager.getAccountTemplates(personalDetail.getReqCardType());

        } catch (Exception e) {
            throw e;
        }
    }

    private void getCardApplicationDetails(String applicationId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            cardApplicationList = checkingmanager.getCardInfomationDetails(applicationId);

            if (cardApplicationList.getApplicationId() == null) {
                cardApplicationList.setApplicationId("-");
            }

            if (cardApplicationList.getAssignStatus() == null) {
                cardApplicationList.setAssignStatus("-");
            }

            if (cardApplicationList.getAssignUser() == null) {
                cardApplicationList.setAssignUser("-");
            }

            if (cardApplicationList.getBranchCode() == null) {
                cardApplicationList.setBranchCode("-");
            }

            if (cardApplicationList.getCreditScore() == null) {
                cardApplicationList.setCreditScore("-");
            }

            if (cardApplicationList.getIdentificationNumber() == null) {
                cardApplicationList.setIdentificationNumber("-");
            }

            if (cardApplicationList.getIdentificationType() == null) {
                cardApplicationList.setIdentificationType("-");
            }

            if (cardApplicationList.getReferencialEmpNum() == null) {
                cardApplicationList.setReferencialEmpNum("-");
            }

        } catch (Exception e) {
            throw e;
        }
    }

    private void getDebitCardApplicationDetails(String applicationId) throws Exception {
        try {
            confirmmanager = new DebitApplicationConfirmationManager();
            personalDetail = confirmmanager.getDebitCardApplicationDetails(applicationId);

            if (personalDetail.getTitle() == null) {
                personalDetail.setTitle("-");
            }

            if (personalDetail.getAddress1() == null) {
                personalDetail.setAddress1("-");
            }

            if (personalDetail.getAddress2() == null) {
                personalDetail.setAddress2("-");
            }

            if (personalDetail.getAddress3() == null) {
                personalDetail.setAddress3("-");
            }

            if (personalDetail.getApplicationId() == null) {
                personalDetail.setApplicationId("-");
            }

            if (personalDetail.getFirstName() == null) {
                personalDetail.setFirstName("-");
            }

            if (personalDetail.getLastName() == null) {
                personalDetail.setLastName("-");
            }

            if (personalDetail.getNameOnCard() == null) {
                personalDetail.setNameOnCard("-");
            }

            if (personalDetail.getDateOfBirth() == null) {
                personalDetail.setDateOfBirth("-");
            }

            if (personalDetail.getReqCardType() == null) {
                personalDetail.setReqCardType("-");
            }

            if (personalDetail.getArea() == null) {
                personalDetail.setArea("-");
            }

            if (personalDetail.getHomeTele() == null) {
                personalDetail.setHomeTele("-");
            }

            if (personalDetail.getOfficeTele() == null) {
                personalDetail.setOfficeTele("-");
            }

            if (personalDetail.getMobileTele() == null) {
                personalDetail.setMobileTele("-");
            }

        } catch (Exception e) {
            throw e;
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
