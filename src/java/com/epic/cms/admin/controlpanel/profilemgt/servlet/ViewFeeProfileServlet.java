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
 *
 * @author ayesh
 */
public class ViewFeeProfileServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    HttpSession sessionObj = null;
    private FeeProfileBean feeProfBean;
    List<FeeBean> feeBeanList;
    private List<FeeBean> feePFeeBeanList;
    List<FeeBean> txnFeeBeanList;
    List<FeeBean> serviceFeeBeanList;
    private FeeProfileManager feeProfMgr;
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
                String id = request.getParameter("id");

                feeProfMgr = new FeeProfileManager();
                txnFeeBeanList = new ArrayList<FeeBean>();
                serviceFeeBeanList = new ArrayList<FeeBean>();
                //retrieve a record from feeprofile table
                feeProfBean = feeProfMgr.viewOneFeeProfile(id);
                //load all fee data according to the fee cateogry
                feeBeanList = feeProfMgr.getAllFeeDetail(feeProfBean.getFeeCategory());
                //retrieve records from feeprofilefee table
                feePFeeBeanList = feeProfMgr.viewFeeProfileFee(id);


                //divide feeprofilefee data according to the fee type
                for (int i = 0; i < feePFeeBeanList.size(); i++) {
                    for (int j = 0; j < feeBeanList.size(); j++) {
                        if (feeBeanList.get(j).getFeeType().equals("SER")) {
                            if (feePFeeBeanList.get(i).getFeeCode().equals(feeBeanList.get(j).getFeeCode())) {
                                serviceFeeBeanList.add(feeBeanList.get(j));
                            }
                        } else if (feeBeanList.get(j).getFeeType().equals("TXN")) {
                            if (feePFeeBeanList.get(i).getFeeCode().equals(feeBeanList.get(j).getFeeCode())) {
                                txnFeeBeanList.add(feeBeanList.get(j));
                            }
                        }
                    }
                }
                if (feeProfBean != null) {
                    request.setAttribute("operationtype", "view");
                    request.setAttribute("feeProfBean", feeProfBean);
                    request.setAttribute("txnFeeBeanList", txnFeeBeanList);
                    request.setAttribute("serviceFeeBeanList", serviceFeeBeanList);
                    request.setAttribute("feeProfileFeeBeanList", feePFeeBeanList);


                    rd = getServletContext().getRequestDispatcher(url);
                }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ViewFeeProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ViewFeeProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
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
