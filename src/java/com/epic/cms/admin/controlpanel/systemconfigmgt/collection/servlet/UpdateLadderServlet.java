/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.collection.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.InterestprofileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionTypeBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.InterestProfileManager;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.CaseTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.LadderBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.businesslogic.LadderManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ruwan_e
 */
public class UpdateLadderServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager pageMgt;
    HttpSession sessionObj = null;
    private List<StatusBean> statusList;
    private SystemUserManager systemUserManager = null;
    private List<TaskBean> taskbeanList = null;
    private InterestProfileManager interestManager;
    private List<PageBean> pageBeanList = null;
    private PageBean pageBean;
    private String errorMessage = null;
    private String[] assignedList = null;
    private SystemAuditBean systemAuditBean = null;
    private boolean successInsert = false;
    private PageBean page;
    private boolean successInsertToInterestprofileTransaction;
    List<String> existTaskList = null;
    private List<TaskBean> assignedTaskDescriptions;
    private List<String> userTaskList;
    private InterestprofileBean interestProfileBean;
    private List<TransactionTypeBean> notAssignedTransactionTypes;
    private InterestprofileBean interestBean = null;
    private List<TransactionTypeBean> assignedTransactions;
    private String url = "/administrator/controlpanel/profilemgt/interestprofilehome.jsp";
    private String oldValue;
    private String newValue;
    private LadderBean ladderBean = null;
    private LadderManager ladderManager = null;

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

            /////////////////////////////////////////////////////////////////////

            try {
                //set page code and task codes
                //set page code and task codes
                String pageCode = PageVarList.LADDER_MANAGEMENT;
                String taskCode = TaskVarList.UPDATE;

                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    sessionVarlist.setUserPageTaskList(userTaskList);

                    request.setAttribute("operationtype", "add");

                    final ArrayList<CaseTypeBean> cases = getCases();
                    request.setAttribute("caseList", cases);

                    final Map<String, String> status = getStatus();
                    request.setAttribute("statusList", status);

                    final Map<String, String> cardTypes = getCardTypes();
                    request.setAttribute("cardTypeList", cardTypes);

                    final ArrayList<LadderBean> ladders = getLadders();
                    request.setAttribute("ladders", ladders);

                    //assign user input to the bean
                    setUserInputToBean(request);

                    if (validateUserInput(ladderBean)) {
                        //this.setAudittraceValue(request);
                        if (updateLadder(ladderBean)) {
                            out.print("success," + MessageVarList.LADDER_MGT_SUCCESS_UPDATE + " Ladder ID " + ladderBean.getCode());
                        }
                    } else {
                        out.print(errorMessage);
                    }

                    sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                } else {
                    throw new AccessDeniedException();

                }

            } catch (AccessDeniedException adex) {
                throw adex;

            }



        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/pagemanagement.jsp?errorMsg=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/pagemanagement.jsp?errorMsg=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {
        } finally {
            out.close();
        }
    }

    public boolean updateLadder(LadderBean ladder) throws Exception {
        boolean success = false;
        try {
            ladderManager = new LadderManager();
            success = ladderManager.updateLadder(ladder, systemAuditBean);
        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }

    public boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Old Code">
//        Boolean status = true;
//        try {
//            //---vars
//            String cardtype = request.getParameter("cardtype").trim();
//            String cardproduct = request.getParameter("cardproduct").trim();
//            boolean cardTypesAll = "ALL".equals(cardtype);
//            boolean cardProductsAll = "ALL".equals(cardproduct);
//            //---endvars
//            ladderBean = new LadderBean();
//
//            ladderBean.setCode(request.getParameter("laddercode").trim());
//            ladderBean.setDescription(request.getParameter("ladderdescription").trim());
//            ladderBean.setStatus(request.getParameter("selectstatuscode").trim());
//            ladderBean.setCardType(request.getParameter("cardtype").trim());
//            ladderBean.setCardProduct(request.getParameter("cardproduct").trim());
//            ladderBean.setAllCardProducts("ALL".equals(request.getParameter("cardproduct").trim()));
//            ladderBean.setAllCardTypes("ALL".equals(request.getParameter("cardtype").trim()));
//            ladderBean.setLastUpdatedUser(sessionUser.getUserName());
//
//            assignedList = request.getParameterValues("assignsectionlist");
//
//            if (assignedList != null) {
//                Map<String, String> caseMap = getCaseDescriptionMap(assignedList);
//                ladderBean.setCaseTypes(caseMap);
//            } else {
//                ladderBean.setCaseTypes(new HashMap<String, String>());
//            }
//
//            String newValue = ladderBean.toString();
//
//        } catch (Exception e) {
//            status = false;
//            throw e;
//        }
//        return status;
        // </editor-fold>

        Boolean status = true;
        try {
            //---vars
            String cardtype = request.getParameter("cardtype").trim();
            String cardproduct = request.getParameter("cardproduct").trim();
            boolean cardTypesAll = "ALL".equals(cardtype);
            boolean cardProductsAll = "ALL".equals(cardproduct);
            //---endvars

            ladderBean = new LadderBean();

            ladderBean.setCode(request.getParameter("laddercode").trim());
            ladderBean.setDescription(request.getParameter("ladderdescription").trim());
            ladderBean.setStatus(request.getParameter("selectstatuscode").trim());

            ladderBean.setCardType(cardtype);
            ladderBean.setCardProduct(cardproduct);

            if (cardTypesAll) {
                ladderBean.setCardType(null);
                ladderBean.setCardProduct(null);
            }
            if (cardProductsAll) {
                ladderBean.setCardProduct(null);
            }

            ladderBean.setAllCardProducts(cardProductsAll);
            ladderBean.setAllCardTypes(cardTypesAll);

            ladderBean.setLastUpdatedUser(sessionUser.getUserName());

            assignedList = request.getParameterValues("assignsectionlist");

            if (assignedList != null) {
                Map<String, String> caseMap = getCaseDescriptionMap(assignedList);
                ladderBean.setCaseTypes(caseMap);
            } else {
                ladderBean.setCaseTypes(new HashMap<String, String>());
            }

            String newValue = ladderBean.toString();

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;

    }

    private boolean validateUserInput(LadderBean ladderBean) throws Exception {
        boolean isValid = true;

        try {
            if (ladderBean.getCode() == null || ladderBean.getCode().contentEquals("")) {
                isValid = false;
                errorMessage = MessageVarList.LADDER_CODE_EMPTY;

            } else if (ladderBean.getDescription() == null || ladderBean.getDescription().contentEquals("")) {
                isValid = false;
                errorMessage = MessageVarList.LADDER_DESCRIPTION_EMPTY;

            } else if (ladderBean.getStatus().contentEquals("")) {
                isValid = false;

                errorMessage = MessageVarList.LADDER_STATUS_EMPTY;

            } else if (ladderBean.getCardType().contentEquals("") || ladderBean.getCardType().length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.LADDER_CARD_TYPE_EMPTY;

            } else if (ladderBean.getCaseTypes().size() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.LADDER_NO_CAESE_ASSIGNED;
            }
        } catch (Exception ex) {
            isValid = false;
            throw ex;
        }
        return isValid;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(interestProfileBean.getInterestFrofileCode());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update interest profile. Profile code: " + interestProfileBean.getInterestFrofileCode() + " by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.PROFILEMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.INTERESTPROFILE);
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

    private Map<String, String> getCaseDescriptionMap(String[] cases) throws SQLException, Exception {
        try {
            return new LadderManager().getCaseDescription(cases);
        } catch (SQLException ex) {
            throw ex;
        }
    }

    private ArrayList<CaseTypeBean> getCases() throws Exception {
        try {
            return new LadderManager().getAllCases();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private Map<String, String> getStatus() throws Exception {
        try {
            return new LadderManager().getStatus();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private Map<String, String> getCardTypes() throws Exception {
        try {
            return new LadderManager().getCardTypes();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private ArrayList<LadderBean> getLadders() throws Exception {
        try {
            return new LadderManager().getAllLadders();

        } catch (Exception ex) {
            throw ex;
        }
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
