/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.FeeProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.FeeProfileManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * this servlet use to add new fee profile 
 * @author nisansala
 */
public class LoadFeeProfileAddServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private List<String> userTaskList;
    private List<StatusBean> statusList;
    FeeProfileManager feeProfMgr;
    List<FeeBean> feeBeanList;
    List<FeeBean> txnFeeBeanList;
    List<FeeBean> serviceFeeBeanList;
    FeeProfileBean feeProfBean;
    private String url = "/administrator/controlpanel/profilemgt/createfeeprofile.jsp";

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

        HttpSession sessionObj = request.getSession(false);

        try {

            //--------------------------------------------------------------------------------------------------//

            try {
                request.setAttribute("operationType", "add");

                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                //check system user is in the same session or not
                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        //user not in same session.
                        throw new NewLoginSessionException();

                    }

                } catch (NewLoginSessionException nlex) {
                    //throw lst login close exception
                    throw new NewLoginSessionException();

                }
                try {
                    //set page code and task codes
                    String pageCode = PageVarList.FEEPROFILE;
                    String taskCode = TaskVarList.ACCESSPAGE;


                    //check whether userrole can access this page and task
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        try {
                            //otherwise it will load the existing values in the session to the input fields in 'add' mode also
                            if(request.getParameter("isDefault").equals("yes")){
                            sessionVarlist.setFeeBean(null);
                            }

                            request.setAttribute("operationtype", "add");
                            
                            //load the status Active Deactive
                            this.getAllStatus(StatusVarList.GENESTATUSCAT);
                            //set the status into the session
                            sessionVarlist.setStatusList(statusList);
                            //when it comes to this servlet for the first time
                            if (request.getParameter("isDefault").equals("yes")) {
                                //load all the fee details according to the fee category
                                this.getAllFeeDetails("CRD");
                                txnFeeBeanList = new ArrayList<FeeBean>();
                                serviceFeeBeanList = new ArrayList<FeeBean>();
                                //divide the loaded fee list into two according to the fee type
                                for (int i = 0; i < feeBeanList.size(); i++) {
                                    if (feeBeanList.get(i).getFeeType().equals("SER")) {
                                        serviceFeeBeanList.add(feeBeanList.get(i));
                                    } else if (feeBeanList.get(i).getFeeType().equals("TXN")) {
                                        txnFeeBeanList.add(feeBeanList.get(i));
                                    }
                                }
                                //set the two lists into the session
                                sessionVarlist.setTxnFeeList(txnFeeBeanList);
                                sessionVarlist.setServiceFeeList(serviceFeeBeanList);
                            }
                            //if user tries to remove a fee table record and cancel then...
                            if (request.getParameter("remove").equals("cancel")) {
                                
                                setUserInputToBean(request);
                                //if feeprofile data don't come with the request 
                                if(feeProfBean.getFeeProCode() == null)
                                    //get data from session
                                    feeProfBean = sessionVarlist.getFeeBean();
                                sessionVarlist.setFeeBean(feeProfBean);
                            }                            
                            
                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

                            rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);

                        } catch (Exception e) {
                            request.setAttribute("operationtype", "add");
                            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                            rd = getServletContext().getRequestDispatcher(url);
                            rd.forward(request, response);
                        }
                    } else {

                        //if invalid throw access denied exception
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

            //------------------------------------------------------------------------------------------------------------//
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
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);
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

    public Boolean setUserInputToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            feeProfBean = new FeeProfileBean();

            feeProfBean.setFeeProCode(request.getParameter("feeProCode"));
            feeProfBean.setFeeProDes(request.getParameter("feeProDes"));
            feeProfBean.setFeeCategory(request.getParameter("category"));
            feeProfBean.setEffectiveDate(request.getParameter("effectiveday"));
            feeProfBean.setStaus(request.getParameter("feeProStatus"));
            feeProfBean.setLastUpdateUser(sessionUser.getUserName());

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    private void getAllStatus(String categoryCode) throws Exception {
        try {
            systemUserManager = new SystemUserManager();
            statusList = systemUserManager.getStatusByUserRole(categoryCode);
        } catch (Exception e) {
            throw e;
        }
    }

    private void getAllFeeDetails(String feeCategory) throws SQLException, Exception {
        feeProfMgr = new FeeProfileManager();
        feeBeanList = feeProfMgr.getAllFeeDetail(feeCategory);


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
