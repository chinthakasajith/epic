/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardProductMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chanuka
 */
public class ViewCardProductServlet extends HttpServlet {

    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    
    private List<CardProductBean> assignBinList;
    private List<CardProductBean> notAssignBinList;
    private CardProductBean cardProductBean;
    private List<String> AssignBinList = null;
    private List<String> NotAssignBinList = null;
    private CardProductMgtManager cardProductMgtManager;
    private List<CardProductBean> cardProductBeanLst = null;
    private String url = "/administrator/controlpanel/systemconfigmgt/cardproductmgthome.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            String cardType = request.getParameter("cardType");
            String cardDomain = request.getParameter("cardDomain");
            String cardProductCode = request.getParameter("cardProductCode");

            //retrieve all card product details
            this.getAllCardProductDetailsList();
            this.getNotAssignBinList(cardType, cardDomain,cardProductCode);
            this.getAssignBinList(cardProductCode);
            request.setAttribute("AssignbinList", assignBinList);
            request.setAttribute("NotAssignbinList", notAssignBinList);
            request.setAttribute("cardProductBeanLst", cardProductBeanLst);

            //select the correct record
            for (CardProductBean CMScardProductBean : cardProductBeanLst) {
                if (CMScardProductBean.getProductCode().equals(cardProductCode)) {
                    cardProductBean = CMScardProductBean;
                }
            }
            if (cardProductBean != null) {
                
                request.setAttribute("operationtype", "view");
                request.setAttribute("cardProductBean", cardProductBean);
                rd = getServletContext().getRequestDispatcher(url);
            }


        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
        }//catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_VIEW_CARD_PRODUCT);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * to retrieve all the card product details
     * @throws Exception 
     */
    private void getAllCardProductDetailsList() throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            cardProductBeanLst = cardProductMgtManager.getAllCardProductDetailsList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to retrieve the BINs in a given card type which are not assigned to the given product
     * @param category
     * @param prodCode
     * @throws Exception 
     */
    private void getNotAssignBinList(String type,String domain ,String prodCode) throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            notAssignBinList = cardProductMgtManager.getNotAssignBinList(type, domain,prodCode);

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to retrieve the assigned BINs for the given card product
     * @param prodCode
     * @throws Exception 
     */
    private void getAssignBinList(String prodCode) throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            assignBinList = cardProductMgtManager.getAssignBinList(prodCode);

        } catch (Exception ex) {
            throw ex;
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
