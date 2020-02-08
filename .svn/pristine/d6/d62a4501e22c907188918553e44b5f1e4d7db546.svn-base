/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.FeeProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.FeeProfileManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * load values to update
 * @author ayesh
 */
public class ViewFeeProfileMgtUpdateServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    HttpSession sessionObj = null;
    List<FeeProfileBean> feeProfBeanList;
    private List<String> userTaskList;
    private FeeProfileBean feeProfBean;
    private FeeProfileManager feeProfMgr;
    List<FeeBean> feeBeanList;
    List<FeeBean> feePFeeBeanList;
    List<FeeBean> txnFeeBeanList;
    List<FeeBean> serviceFeeBeanList;
    List<FeeBean> unusedtxnFeeList;
    List<FeeBean> unusedServiceFeeList;
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
        PrintWriter out = response.getWriter();


        try {
            //call to existing session
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
                String pageCode = PageVarList.FEEPROFILE;
                String taskCode = TaskVarList.UPDATE;

                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    try {
                        request.setAttribute("operationtype", "update");

                        feeProfMgr = new FeeProfileManager();
                        txnFeeBeanList = new ArrayList<FeeBean>();
                        serviceFeeBeanList = new ArrayList<FeeBean>();
                        unusedtxnFeeList = new ArrayList<FeeBean>();
                        unusedServiceFeeList = new ArrayList<FeeBean>();

                        String feeProfCode = "";
                        if (request.getParameter("isDefault").equals("no")) {
                            feeProfCode = sessionVarlist.getFeeBean().getFeeProCode();
                        } else {
                            feeProfCode = request.getParameter("id");
                        }
                        //load all fee profile data
                        this.getAllFeeProfileDetail();

                        //load one fee profile record 
                        feeProfBean = feeProfMgr.viewOneFeeProfile(feeProfCode);
                        //if new values are loading from the database for update
                        if (request.getParameter("isDefault").equals("yes")) {
                            //load all fee data according to the fee cateogry
                            feeBeanList = feeProfMgr.getAllFeeDetail(feeProfBean.getFeeCategory());
                            //load feeprofilefee data according to the fee prof code 
                            feePFeeBeanList = feeProfMgr.viewFeeProfileFee(feeProfCode);


                            //divide feeprofilefee data according to the fee type
                            for (int i = 0; i < feePFeeBeanList.size(); i++) {
                                int m = 0;
                                int n = 0;
                                for (int j = 0; j < feeBeanList.size(); j++) {
                                    if (feeBeanList.get(j).getFeeType().equals("SER")) {
                                        if (feePFeeBeanList.get(i).getFeeCode().equals(feeBeanList.get(j).getFeeCode())) {
                                            serviceFeeBeanList.add(feePFeeBeanList.get(i));
                                            //fee type is not in the fee profile fee table
                                            serviceFeeBeanList.get(m).setFeeType("SER");
                                            m++;
                                        }
                                    } else if (feeBeanList.get(j).getFeeType().equals("TXN")) {
                                        if (feePFeeBeanList.get(i).getFeeCode().equals(feeBeanList.get(j).getFeeCode())) {
                                            txnFeeBeanList.add(feePFeeBeanList.get(i));
                                            //fee type is not in the fee profile fee table
                                            txnFeeBeanList.get(n).setFeeType("TXN");
                                            n++;
                                        }
                                    }
                                }
                            }

                            //set unused fee records which are not in fee profle fee
                            for (int i = 0; i < feeBeanList.size(); i++) {
                                String feeCode = feeBeanList.get(i).getFeeCode();                               
                                
                                if (feeBeanList.get(i).getFeeType().equals("SER")) {
                                    if (serviceFeeBeanList.isEmpty()) {
                                        unusedServiceFeeList.add(feeBeanList.get(i));
                                    } else {
                                    for (int j = 0; j < serviceFeeBeanList.size(); j++) {
                                        if (serviceFeeBeanList.get(j).getFeeCode().equals(feeCode)) {
                                            break;
                                        } else if (j == serviceFeeBeanList.size() - 1) {
                                            unusedServiceFeeList.add(feeBeanList.get(i));
                                        }
                                    }}

                                } else if (feeBeanList.get(i).getFeeType().equals("TXN")) {
                                    if (txnFeeBeanList.isEmpty()) {
                                        unusedtxnFeeList.add(feeBeanList.get(i));
                                    } else {
                                        for (int j = 0; j < txnFeeBeanList.size(); j++) {

                                            if (txnFeeBeanList.get(j).getFeeCode().equals(feeCode)) {
                                                break;
                                            } else if (j == txnFeeBeanList.size() - 1) {
                                                unusedtxnFeeList.add(feeBeanList.get(i));
                                            }

                                        }
                                    }
                                }
                            }

                            //set fee description in service fee
                            for (int i = 0; i < serviceFeeBeanList.size(); i++) {
                                for (int j = 0; j < feeBeanList.size(); j++) {
                                    if (serviceFeeBeanList.get(i).getFeeCode().equals(feeBeanList.get(j).getFeeCode())) {
                                        serviceFeeBeanList.get(i).setFeeDes(feeBeanList.get(j).getFeeDes());
                                    }
                                }
                            }
                            //set fee description in transaction fee
                            for (int i = 0; i < txnFeeBeanList.size(); i++) {
                                for (int j = 0; j < feeBeanList.size(); j++) {
                                    if (txnFeeBeanList.get(i).getFeeCode().equals(feeBeanList.get(j).getFeeCode())) {
                                        txnFeeBeanList.get(i).setFeeDes(feeBeanList.get(j).getFeeDes());
                                    }
                                }
                            }

                            request.setAttribute("unusedServiceFeeList", unusedServiceFeeList);
                            request.setAttribute("unusedtxnFeeList", unusedtxnFeeList);
                            sessionVarlist.setTxnFeeList(txnFeeBeanList);
                            sessionVarlist.setServiceFeeList(serviceFeeBeanList);
                        } //------------------------------------------------------------------------------------
                        else if (request.getParameter("isDefault").equals("no")) {
                            txnFeeBeanList = sessionVarlist.getTxnFeeList();
                            serviceFeeBeanList = sessionVarlist.getServiceFeeList();

                            //load all fee data according to the fee cateogry
                            feeBeanList = feeProfMgr.getAllFeeDetail(feeProfBean.getFeeCategory());

                            //set unused fee records which are not in fee profle fee
                            for (int i = 0; i < feeBeanList.size(); i++) {
                                String feeCode = feeBeanList.get(i).getFeeCode();
                                if (feeBeanList.get(i).getFeeType().equals("SER")) {
                                    for (int j = 0; j < serviceFeeBeanList.size(); j++) {
                                        if (serviceFeeBeanList.get(j).getFeeCode().equals(feeCode)) {
                                            break;
                                        } else if (j == serviceFeeBeanList.size() - 1) {
                                            unusedServiceFeeList.add(feeBeanList.get(i));
                                        }
                                    }

                                } else if (feeBeanList.get(i).getFeeType().equals("TXN")) {
                                    for (int j = 0; j < txnFeeBeanList.size(); j++) {
                                        if (txnFeeBeanList.get(j).getFeeCode().equals(feeCode)) {
                                            break;
                                        } else if (j == txnFeeBeanList.size() - 1) {
                                            unusedtxnFeeList.add(feeBeanList.get(i));
                                        }
                                    }
                                }
                            }

                            request.setAttribute("unusedServiceFeeList", unusedServiceFeeList);
                            request.setAttribute("unusedtxnFeeList", unusedtxnFeeList);
                        }
                        //------------------------------------------------------------------------------------

                        if (feeProfBean != null) {
                            sessionVarlist.setFeeBean(feeProfBean);
                            request.setAttribute("feeProfBeanList", feeProfBeanList);

                            rd = getServletContext().getRequestDispatcher(url);
                        }
                        rd.forward(request, response);
                    } catch (Exception e) {
                        request.setAttribute("operationtype", "update");
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        //request.setAttribute("feeProfBeanList", feeProfBeanList);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
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

    private void getAllFeeProfileDetail() throws SQLException, Exception {
        feeProfMgr = new FeeProfileManager();
        feeProfBeanList = feeProfMgr.getAllFeeProfileDetail();
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
