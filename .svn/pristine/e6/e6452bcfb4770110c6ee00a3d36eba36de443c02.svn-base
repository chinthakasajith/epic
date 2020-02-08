/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserRoleManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BankBranchBean;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.StatusVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author janaka_h
 */
public class ManageSystemUserServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SystemUserRoleManager userRoalManager;
    HttpSession sessionObj;
    private String getContextPath = null;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private List<SystemUserBean> userList = null;
    private List<UserRoleBean> userRoleList = null;
    private List<StatusBean> statusList = null;
    private List<BankBranchBean> branchList;
    private String url = "/LoadSystemUserServlet";
    private String bankName = "";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            sessionObj = request.getSession(false);
            getContextPath = request.getContextPath();
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

            String operationType = (request.getParameter("operation") != null) ? request.getParameter("operation") : "deleteReq";
            if (request.getAttribute("operation") != null) {
                operationType = (String) request.getAttribute("operation");
            }

            if (operationType.equals("add")) {

                this.getAllUserRole();

                this.getBranchList();
                this.getBankName();
                SystemUserBean editBean;
                editBean = (SystemUserBean) request.getAttribute("userBean");
                if (null == editBean) {
                    editBean = new SystemUserBean();
                    editBean.setBankname(bankName);
                } else {
                    editBean.setBankname(bankName);
                }
                request.setAttribute("userBean", editBean);
                request.setAttribute("operationtype", "add");
                request.setAttribute("userRoleLst", userRoleList);
                request.setAttribute("statusLst", statusList);
                request.setAttribute("branchList", branchList);
                rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemusermgt/usermanagement.jsp");

            }
            if (operationType.equals("edit")) {

                String userName = request.getParameter("username");
                String statusOrRequestType = request.getParameter("status");
                this.getAllSysUser(sessionUser);
                List<SystemUserBean> editBeanLists = new ArrayList<SystemUserBean>();
                List<SystemUserBean> editBeanListTemp = new ArrayList<SystemUserBean>();
                SystemUserBean editBean = new SystemUserBean();

                for (SystemUserBean systemUserBean : userList) {
                    if (systemUserBean.getUserName().equals(userName) && StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT.equals(systemUserBean.getStatusCode()) || systemUserBean.getUserName().equals(userName) && StatusVarList.DA_REQUSET_INITIATE.equals(systemUserBean.getStatusCode()) || systemUserBean.getUserName().equals(userName) && StatusVarList.ACTIVE_STATUS.equals(systemUserBean.getStatusCode()) || systemUserBean.getUserName().equals(userName) && StatusVarList.DEACTIVE_STATUS.equals(systemUserBean.getStatusCode())) {
                        editBeanLists.add(systemUserBean);
                    }
                }

                if (editBeanLists.size() == 2) {
                    for (Iterator<SystemUserBean> iterator = editBeanLists.iterator(); iterator.hasNext();) {
                        SystemUserBean next = iterator.next();
                        if (StatusVarList.DA_REQUSET_INITIATE.equals(next.getStatusCode())) {
                            editBeanListTemp.add(0, next);
                        } else {
                            editBeanListTemp.add(1, next);
                        }

                    }
                } else {
                    editBeanListTemp.addAll(editBeanLists);
                }

                editBean = editBeanLists.get(0);

                //lock for dual authentication
                if (statusOrRequestType.equals(StatusVarList.ACTIVE_STATUS) || statusOrRequestType.equals(StatusVarList.DEACTIVE_STATUS) || statusOrRequestType.equals(StatusVarList.DA_REQUSET_INITIATE) || StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT.equals(statusOrRequestType)) {

                    this.getAllUserRole();

                    if (editBean.getStatusCode().equals(StatusVarList.DA_REQUSET_INITIATE)) {
                        //get status list
                        this.getStatusList(StatusVarList.DATH_CATEGORY, StatusVarList.GENESTATUCAT);
                    } else if (editBean.getStatusCode().equals(StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT)) {
                        //get status list
                        this.getStatusList(StatusVarList.USER_CATEGORY);
                    } else {
                        //get status list
                        this.getStatusList(StatusVarList.GENESTATUCAT);
                    }

                    this.getBranchList();

                    request.setAttribute("userBeans", editBeanListTemp);
                    request.setAttribute("userRoleLst", userRoleList);
                    request.setAttribute("statusLst", statusList);
                    request.setAttribute("operationtype", "edit");
                    request.setAttribute("branchList", branchList);
                    rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemusermgt/usermanagement.jsp");

                } else {

                    request.setAttribute("errorMsg", MessageVarList.DUAL_AUTH_LOCK + generateRequestLink(editBean, statusOrRequestType, getContextPath));
                    rd = getServletContext().getRequestDispatcher(url);

                }

            }
            if (operationType.equals("delete")) {

                String userName = request.getParameter("username");
                String status = request.getParameter("status");
                String userrole = request.getParameter("userrole");
                String statusCode = request.getParameter("statusCode");

                SystemUserBean deleteBean = new SystemUserBean();

                deleteBean.setUserName(userName);
                deleteBean.setStatusDes(status);
                deleteBean.setUserRoleDes(userrole);
                deleteBean.setStatusCode(statusCode);

                //lock for dual authentication
                if (deleteBean.getStatusCode().equals(StatusVarList.ACTIVE_STATUS) || deleteBean.getStatusCode().equals(StatusVarList.DEACTIVE_STATUS) || deleteBean.getStatusCode().equals(StatusVarList.DELETE_SYSTEMUSER_REQUEST_SENT)) {

                    request.setAttribute("userBean", deleteBean);
                    request.setAttribute("operationtype", "delete");
                    rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemusermgt/usermanagement.jsp");

                } else {

                    request.setAttribute("errorMsg", MessageVarList.DUAL_AUTH_LOCK + generateRequestLink(deleteBean, deleteBean.getStatusCode(), getContextPath));
                    rd = getServletContext().getRequestDispatcher(url);

                }

            }
            if (operationType.equals("deleteReq")) {
                //successfully delete request rejected
                //redirected to search list
                rd = getServletContext().getRequestDispatcher(url);
            }

        } catch (SQLException ex) {
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", "Error in action");
            rd = getServletContext().getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void getAllSysUser(SessionUser sessionUser) throws Exception {
        systemUserManager = new SystemUserManager();
        userList = systemUserManager.getAllSysUsersExceptDel(sessionUser);

    }

    //get All user roles
    private List<UserRoleBean> getAllUserRole() throws Exception {
        userRoalManager = new SystemUserRoleManager();
        userRoleList = userRoalManager.getAllUserRole();
        return userRoleList;
    }

    //get StatusList
    private List<StatusBean> getStatusList(String statusCategory) throws Exception {
        systemUserManager = new SystemUserManager();
        statusList = systemUserManager.getStatusByUserRole(statusCategory);
        return statusList;
    }

    private String generateRequestLink(SystemUserBean sysBean, String statusType, String getContextPath) {
        String anchorTag = null;
        if (StatusVarList.PASSWORD_RESET_REQUEST_SENT.equals(statusType)) {
            anchorTag = "<a class=\"link\" href=\"javascript:void(0);\" onclick=\"setUserDetails('" + sysBean.getUserName() + "','" + sysBean.getUserRoleDes() + "','" + sysBean.getUserRoleCode() + "','" + sysBean.getIsEmailSent() + "','" + StatusVarList.PASSWORD_RESET_REQUEST_SENT + "','" + sysBean.getEmail() + "','" + sysBean.getDualUserRoleCode() + "')\">" + "Change Password Request" + "</a>";
        } else if (StatusVarList.DELETE_SYSTEMUSER_REQUEST_SENT.equals(statusType)) {
            anchorTag = "<a class=\"link\" href='" + getContextPath + "/ManageSystemUserServlet?username=" + sysBean.getUserName() + "&operation=delete&status=" + StatusVarList.DELETE_SYSTEMUSER_REQUEST_SENT_DES + "&userrole=" + sysBean.getUserRoleDes() + "&statusCode=" + statusType + "'>" + "Delete Password Request" + "</a>";
        } else if (StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT.equals(statusType) || StatusVarList.DA_REQUSET_INITIATE.equals(statusType)) {
            anchorTag = "<a class=\"link\" href='" + getContextPath + "/ManageSystemUserServlet?username=" + sysBean.getUserName() + "&status=" + sysBean.getStatusCode() + "&operation=edit'>" + "Update Request Initiated" + "</a>";

        }
        return anchorTag;
    }

    private void getBranchList() {

        branchList = new ArrayList<BankBranchBean>();
        try {
            systemUserManager = new SystemUserManager();
            branchList = systemUserManager.getBranchLst();
            concatBranchNameWithCode(branchList);
        } catch (Exception ex) {
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageSystemUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageSystemUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private void getBankName() {

        try {
            systemUserManager = new SystemUserManager();
            bankName = systemUserManager.getBankName();

        } catch (Exception ex) {
        }
    }

    private void concatBranchNameWithCode(List<BankBranchBean> branchList) {
        if (branchList != null) {
            for (BankBranchBean branch : branchList) {
                if (branch.getBranchCode() != null && branch.getDescription() != null) {
                    branch.setDescription(branch.getBranchCode() + " - " + branch.getDescription());
                }
            }
        }
    }

    private void getStatusList(String dath, String genr) throws SQLException, Exception {
        systemUserManager = new SystemUserManager();
        statusList = systemUserManager.getStatusByUserRole(dath, genr);
    }
}
