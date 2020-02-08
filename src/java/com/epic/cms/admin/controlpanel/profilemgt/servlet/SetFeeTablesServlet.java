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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * to load fee details according to the fee category to be shown on two tables(TRANSACTION, SERVICE)
 * @author nisansala
 */
public class SetFeeTablesServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    private FeeProfileManager feeProfMgr;
    List<FeeBean> txnFeeList = null;
    List<FeeBean> serviceFeeList = null;
    private FeeProfileBean feeProfBean;
    List<FeeBean> txnFeeBeanList;
    List<FeeBean> serviceFeeBeanList;
    List<FeeBean> feeBeanList;
    private List<FeeBean> unusedtxnFeeList = null;
    private List<FeeBean> unusedServiceFeeList = null;
    private String url = "/administrator/controlpanel/profilemgt/createfeeprofile.jsp";

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //call to existing session
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
            try {
                feeProfMgr = new FeeProfileManager();

                //set operation type
                if (request.getParameter("opType").equals("update")) {
                    request.setAttribute("operationtype", "update");
                } else if (request.getParameter("opType").equals("add")) {
                    request.setAttribute("operationtype", "add");
                }
                try {
                    //load all the fee details according to the fee category(MERCHANT or CARD)
                    this.getAllFeeDetails(request.getParameter("category"));

                    txnFeeBeanList = new ArrayList<FeeBean>();
                    serviceFeeBeanList = new ArrayList<FeeBean>();

                    unusedtxnFeeList = new ArrayList<FeeBean>();
                    unusedServiceFeeList = new ArrayList<FeeBean>();

                    //divide the loaded data into two lists according to the fee type(SERVICE OR TRANSACTION)
                    for (int i = 0; i < feeBeanList.size(); i++) {
                        if (feeBeanList.get(i).getFeeType().equals("SER")) {
                            unusedServiceFeeList.add(feeBeanList.get(i));
                            serviceFeeBeanList.add(feeBeanList.get(i));
                        } else if (feeBeanList.get(i).getFeeType().equals("TXN")) {
                            unusedtxnFeeList.add(feeBeanList.get(i));
                            txnFeeBeanList.add(feeBeanList.get(i));
                        }
                    }
                    if (request.getParameter("opType").equals("update")) {
                        //set the two lists into the session
                        sessionVarlist.setTxnFeeList(null);
                        sessionVarlist.setServiceFeeList(null);

                        request.setAttribute("unusedServiceFeeList", unusedServiceFeeList);
                        request.setAttribute("unusedtxnFeeList", unusedtxnFeeList);
                        
                    } else if (request.getParameter("opType").equals("add")) {
                        
                        sessionVarlist.setTxnFeeList(txnFeeBeanList);
                        sessionVarlist.setServiceFeeList(serviceFeeBeanList);
                    }



                    //assign user input to the bean
                    setUserInputToBean(request);
                    sessionVarlist.setFeeBean(feeProfBean);


                } catch (Exception e) {

                    request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                }
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);


            } catch (Exception e) {
            }
        } catch (SesssionExpException sex) {
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
            out.close();
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

            txnFeeList = new ArrayList<FeeBean>();
            serviceFeeList = new ArrayList<FeeBean>();
            txnFeeList = sessionVarlist.getTxnFeeList();
            serviceFeeList = sessionVarlist.getServiceFeeList();

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SetFeeTablesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SetFeeTablesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
