/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.pingenarationandmail.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingSearchBean;
import com.epic.cms.cpmm.cardembossing.businesslogic.CardEmbossingMgtManager;
import com.epic.cms.cpmm.pingenarationandmail.busneslogic.PinGenerationAndmailManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mahesh_m
 */
public class GetCardProductByTypePinGen extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CardEmbossingMgtManager cardEmbossingManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private HashMap<String, String> cardProductList = null;
    private CardEmbossingSearchBean searchBean = null;
    private String embossType = "";
    private HashMap<String, String> categoryMap;
    private PinGenerationAndmailManager pinGenerationAndmailManager;
    private String url = "/cpmm/pingenarateandmail/pingenerateandmail.jsp";

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

            String cardType = request.getParameter("cardtype");

            try {

                embossType = request.getParameter("embosstype");

            } catch (NullPointerException e) {
            }


            this.setUserInputsToBean(request);
            request.setAttribute("searchembossingbean", searchBean);

            this.getAllCardProduct(cardType);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);

            this.getCardCategoryList();
            request.setAttribute("categoryMap", categoryMap);

            rd = getServletContext().getRequestDispatcher(url);


        }//catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_CARD_PRODUCTS);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void setUserInputsToBean(HttpServletRequest request) throws Exception {
        try {

            String cardNo = request.getParameter("cardnumber");
            String identityType = request.getParameter("identitytype");
            String identityNo = request.getParameter("identityno");
            String priorityLevel = request.getParameter("prioritycode");
            String cardType = request.getParameter("cardtypecode");
            String cardProduct = request.getParameter("cardproductcode");
            String status = request.getParameter("statuscode");
            String generatedUser = request.getParameter("generateduser");


            searchBean = new CardEmbossingSearchBean();

            searchBean.setCardNumber(cardNo);
            searchBean.setIdentityType(identityType);
            searchBean.setIdentityNo(identityNo);
            searchBean.setPriorityLevel(priorityLevel);
            searchBean.setCardtype(cardType);
            searchBean.setCardProduct(cardProduct);
            searchBean.setStatus(status);
            searchBean.setGeneratedUser(generatedUser);
            searchBean.setEmbossType(embossType);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllCardProduct(String cardType) throws Exception {
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            cardProductList = cardEmbossingManager.getAllCardProduct(cardType);
            sessionVarlist.setCardProductList(cardProductList);

        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private void getCardCategoryList() throws Exception {
        try {
            pinGenerationAndmailManager = new PinGenerationAndmailManager();
            categoryMap = pinGenerationAndmailManager.getCardCategoryList();
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
