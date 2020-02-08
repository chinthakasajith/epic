/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentAssetsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.camm.applicationproccessing.capturedata.util.LoadApplicationStatus;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
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
 * @author prageeth_s
 */
public class SetAssetDataToSessionServlet extends HttpServlet {

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
            
            mode=request.getParameter("mode");
            if (mode != null && mode.equals("edit")) {
                url = editUrl;
            } else {
                url = "/camm/capturedata/establishmentInputCaptureData.jsp";
            }
            EstablishmentAssetsBean establishmentAssetsBean = new EstablishmentAssetsBean();
            EstablishmentAssetsBean invalidEstablishmentAssetsBean = new EstablishmentAssetsBean();
//
            establishmentAssetsBean.setBusinessRegNumber(sessionVarlist.getEstablishmentDetailsBean().getBusinessRegNumber());
            establishmentAssetsBean.setAssetCode(request.getParameter("assetCode"));
            establishmentAssetsBean.setAssetTypeCode(request.getParameter("assetTypeCode"));
            establishmentAssetsBean.setAssetValue(request.getParameter("assetValue"));
            establishmentAssetsBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());
            establishmentAssetsBean.setNameDes(request.getParameter("assetCodeDes"));
            establishmentAssetsBean.setTypeDes(request.getParameter("assetTypeCodeDes"));

            if (validateEstablismentAssets(establishmentAssetsBean, request, invalidEstablishmentAssetsBean)) {

                if (sessionVarlist.getEstablishmentAssetList() != null) {
                    sessionVarlist.getEstablishmentAssetList().add(establishmentAssetsBean);

                } else {
                    List<EstablishmentAssetsBean> list = new ArrayList<EstablishmentAssetsBean>();
                    list.add(establishmentAssetsBean);
                    sessionVarlist.setEstablishmentAssetList(list);
                }
            } else {
                request.setAttribute("invalidEstablishmentAssetsBean", invalidEstablishmentAssetsBean);
                request.setAttribute("establishmentAssetsBean", establishmentAssetsBean);
            }
            if (mode != null && mode.equals("edit")) {
                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null, 1, true);
            } else {
                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatus(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null, null);
            }
            rd = getServletContext().getRequestDispatcher(url);

            rd.forward(request, response);

        } catch (SQLException ex) {
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
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
            if (mode != null && mode.equals("edit")) {
                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatusInUpdate(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null, 1, true);
            } else {
                LoadApplicationStatus.loadDefaultEstablishmentApplicationStatus(sessionVarlist.getApplicationId(), sessionVarlist, request, Boolean.FALSE, null, null);
            }
            request.setAttribute("errorMsg", "Error in action");
            rd = request.getRequestDispatcher(url);
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
            Logger.getLogger(SetAccountDataToSessionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SetAccountDataToSessionServlet.class.getName()).log(Level.SEVERE, null, ex);
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





    private Boolean validateEstablismentAssets(EstablishmentAssetsBean establishmentAssetsBean, HttpServletRequest request, EstablishmentAssetsBean invalidEstablishmentAssetsBean) {
        boolean validate = true;

        if (establishmentAssetsBean.getAssetTypeCode() == null || establishmentAssetsBean.getAssetTypeCode().trim().equals("")) {
            validate = false;
            invalidEstablishmentAssetsBean.setAssetTypeCode("Required");
        } else if (establishmentAssetsBean.getAssetCode() == null || establishmentAssetsBean.getAssetCode().trim().equals("")) {
            validate = false;
            invalidEstablishmentAssetsBean.setAssetCode("Required");
        } else if (establishmentAssetsBean.getAssetValue() == null || establishmentAssetsBean.getAssetValue().trim().equals("")) {
            invalidEstablishmentAssetsBean.setAssetValue("Required");
            validate = false;
        } else if (sessionVarlist.getEstablishmentAssetList() != null) {
            List<EstablishmentAssetsBean> exixtingList = sessionVarlist.getEstablishmentAssetList();
            if (!checkUniqueEstablishmentassets(establishmentAssetsBean, exixtingList)) {
                validate = false;
                request.setAttribute("invalidEstablishmentAssetsCommonError", "Exists");

            }

        }

        return validate;
    }

    private boolean checkUniqueEstablishmentassets(EstablishmentAssetsBean establishmentAssetsBean, List<EstablishmentAssetsBean> exixtingList) {
        boolean validate = true;

        for (EstablishmentAssetsBean bean : exixtingList) {
            if (bean.getAssetCode().equals(establishmentAssetsBean.getAssetCode())
                    && bean.getAssetTypeCode().equals(establishmentAssetsBean.getAssetTypeCode())) {
                validate = false;
                break;

            }
        }
        return validate;
    }

}
