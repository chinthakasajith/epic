/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfirmation.businesslogic.DebitApplicationConfirmationManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.json.JSONObject;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
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
 *
 * @author badrika
 */
public class LoadDebitBinProfilesServlet extends HttpServlet {

    private DebitApplicationConfirmationManager debitAppConfirmManager;
    private String url = "/camm/applicationconfirmation/debitapplicationapproveview.jsp";
    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;

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
            

            String productionModeCode = request.getParameter("promode");
            String cardType = request.getParameter("ctyp");

            List<CardBinBean> cardBinList = new ArrayList<CardBinBean>();
            try {
                debitAppConfirmManager = new DebitApplicationConfirmationManager();
                JSONObject jCombo = new JSONObject();// if there are several combo box to load
                //load combo box data
                JSONObject jObject = new JSONObject();
                cardBinList = debitAppConfirmManager.getDebitBinList(productionModeCode,cardType);
                
               // request.setAttribute("cardBinList", cardBinList);
                
                jObject.put("", "--SELECT--");
                for (int i = 0; i < cardBinList.size(); i++) {
                    jObject.put(cardBinList.get(i).getBinNumber(), cardBinList.get(i).getDescription());
                }

                jCombo.put("combo1", jObject);
                out.print(jCombo);
            } catch (Exception e) {
            }


        }
        
        
        finally {
            out.close();                        
        }
        
        
//        try {
//            try {
//
//                HttpSession sessionObj = request.getSession(false);
//                systemUserManager = new SystemUserManager();
//                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
//                sessionUser = sessionVarlist.getCMSSessionUser();
//
//                try {
//
//                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
//                        throw new NewLoginSessionException();
//                    }
//                } catch (NewLoginSessionException nlex) {
//                    throw new NewLoginSessionException();
//                }
//                //////////////////////////
//                request.setAttribute("selectedtab", "0");
//                
//                String productionModeCode = request.getParameter("productionMode");
//                String cardType = request.getParameter("ctyp");
//
//                List<CardBinBean> cardBinList = new ArrayList<CardBinBean>();
//
//                debitAppConfirmManager = new DebitApplicationConfirmationManager();
//
//                cardBinList = debitAppConfirmManager.getDebitBinList(productionModeCode, cardType);
//
//                request.setAttribute("cardBinList", cardBinList);
//
//
//
//
//                /////////////////////////////////////
//
//            } catch (NullPointerException ex) {
//                throw new SesssionExpException();
//            }
//
//            rd = request.getRequestDispatcher(url);
//
//
//        } catch (SesssionExpException sex) {
//            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
//        } catch (NewLoginSessionException nlex) {
//            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
//        } catch (AccessDeniedException adex) {
//            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.ACCESS_DENIED_TASK);
//            rd.forward(request, response);
//        } catch (SQLException ex) {
//            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
//            rd = request.getRequestDispatcher(url);
//        } catch (Exception ex) {
//            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
//            rd = request.getRequestDispatcher(url);
//        } finally {
//            rd.forward(request, response);
//        }


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
