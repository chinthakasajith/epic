/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardExpensesBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardIncomeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerEmploymentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentAssetsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentLiabilityBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.camm.applicationproccessing.capturedata.util.LoadApplicationStatus;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * @author prageeth_s
 */
public class RemoveEstablishmentAssetLiabilityFromSessionServlet extends HttpServlet {

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
    private CaptureDataManager manager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private List<String> userTaskList;
    private SystemAuditBean systemAuditBean;
    private String url = "/camm/capturedata/establishmentInputCaptureData.jsp";
    private String editUrl = "/camm/capturedata/modifyEstablishmentInputCaptureData.jsp";

    private ApplicationCheckingManager checkingManager;
    private CardApplicationStatusBean appStatusBean = null;
    private CustomerPersonalBean customerPersonalBean = null;
    private SupplementaryApplicationBean customerSuplimentoryPersonalBean = null;
    private CustomerEmploymentBean employmentBean = null;
    private List<CardIncomeBean> incomeBeanList = null;
    private CardExpensesBean expensesBean = null;
    private List<CardBankDetailsBean> bankDetailsBeanLst = null;
    private List<DocumentUploadBean> documentDetailsBeanLst = null;
    private String mode = null; //Track wheter "Add" or "Edit"

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

            CardIncomeBean incomeBean = new CardIncomeBean();
//
            incomeBean.setApplicationId(sessionVarlist.getApplicationId());
            String assetCode, assetTypeCode, type;
            assetCode = request.getParameter("assetCode");
            assetTypeCode = request.getParameter("assetTypeCode");
            type = request.getParameter("type");
            mode = request.getParameter("mode");
            if (mode != null && mode.equals("edit")) {
                url = editUrl;
            } else {
                url = "/camm/capturedata/establishmentInputCaptureData.jsp";
            }

            if (type.equals("asset")) {

                Iterator<EstablishmentAssetsBean> it = sessionVarlist.getEstablishmentAssetList().iterator();
                while (it.hasNext()) {
                    EstablishmentAssetsBean bean = it.next();
                    if (bean.getAssetCode().equals(assetCode) && bean.getAssetTypeCode().equals(assetTypeCode)) {
                        it.remove();
                        break;
                    }
                }

            }

            if (type.equals("liability")) {

                Iterator<EstablishmentLiabilityBean> it = sessionVarlist.getEstablishmentLiabilityList().iterator();
                while (it.hasNext()) {
                    EstablishmentLiabilityBean bean = it.next();
                    if (bean.getLiabilityCode().equals(assetCode) && bean.getLiabilityTypeCode().equals(assetTypeCode)) {
                        it.remove();
                        break;
                    }
                }

            }

            if (mode != null && mode.equals("edit")) {
                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null, 1, true);
            } else {
                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatus(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null, null);
            }

            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);

//            sessionVarlist.setCMSSessionUser(sessionUser);
//            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
        } catch (SQLException ex) {
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            if (mode != null && mode.equals("edit")) {
                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null, 1, false);

            } else {
                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatus(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null, null);
            }
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
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
            request.setAttribute("errorMsg", "Error in action");
            if (mode != null && mode.equals("edit")) {
                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null, 1, false);
            } else {
                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatus(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null, null);
            }
            rd = getServletContext().getRequestDispatcher(url);

            rd.forward(request, response);
        } finally {
            out.close();
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
            Logger.getLogger(RemoveDocumentDataFromSessionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RemoveDocumentDataFromSessionServlet.class.getName()).log(Level.SEVERE, null, ex);
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

}
