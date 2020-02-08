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
import com.epic.cms.system.util.variable.OracleMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class UpdateCardProductFormServlet extends HttpServlet {

    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private SystemUserManager systemUserManager;
    String cardType = "";
    String cardDomain = "";
    String prodCode = "";
    String cardProductCode = "";
    private String errorMessage = null;
    private List<CardProductBean> assignBinList;
    private List<CardProductBean> notAssignBinList;
    private CardProductBean cardProductBean;
    private List<String> AssignBinList = null;
    private List<String> NotAssignBinList = null;
    private HashMap<String, String> cardTypeList = null;
    private CardProductMgtManager cardProductMgtManager;
    private List<CardProductBean> cardProductBeanLst = null;
    private HashMap<String, String> cardDomainList = null;
    private String url = "/administrator/controlpanel/systemconfigmgt/cardproductmgthome.jsp";
    private List<CardProductBean> allCardProductBinList;
    private List<CardProductBean> assignCardKeyList;
    private List<CardProductBean> notAssignCardKeyList = null;
    private List<CardProductBean> productionModeList = null;
    private List<CardProductBean> cardProductionModeList = null;
    private List<CardProductBean> sessionList = null;

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
            String RemoveOpertation = null;
            RemoveOpertation = (String) request.getAttribute("RemoveOpertation");
            if (null != RemoveOpertation) {
                sessionList = new ArrayList<CardProductBean>();
                sessionList.addAll(sessionVarlist.getCardProductBinBeanLst());
            }
            this.getAllCardTypeList();
            this.getAllCardDomainList();
            this.getAllCardProductDetailsList();
            this.getAllProductionModeList();

            sessionVarlist.setIsBack("updateCardProduct");

            request.setAttribute("cardTypeList", cardTypeList);
            request.setAttribute("cardDomainList", cardDomainList);
            request.setAttribute("cardProductBeanLst", cardProductBeanLst);
            request.setAttribute("cardProductionModeList", cardProductionModeList);

            if (request.getAttribute("isBack") != null) {

                cardType = request.getParameter("cardtype");
                cardProductCode = request.getParameter("productcode");
                cardDomain = request.getParameter("carddomain");
                setUserInputToBean(request);

            } else {

                cardProductCode = request.getParameter("cardProductCode");
                for (CardProductBean CMScardProductBean : cardProductBeanLst) {
                    if (CMScardProductBean.getProductCode().equals(cardProductCode)) {
                        cardProductBean = CMScardProductBean;
                        cardType = cardProductBean.getCardType();
                        prodCode = cardProductBean.getProductCode();
                        cardDomain = cardProductBean.getDomainCode();
                    }
                }
            }

            if (request.getAttribute("isBack") != null) {

                notAssignBinList = (List<CardProductBean>) request.getAttribute("NotAssignbinList");
                assignBinList = (List<CardProductBean>) request.getAttribute("AssignbinList");

            } else {

                this.getNotAssignBinList(cardType, cardDomain, prodCode);
                this.getAssignBinList(prodCode);

            }
            request.setAttribute("AssignbinList", assignBinList);
            request.setAttribute("NotAssignbinList", notAssignBinList);

            if (cardProductBean != null) {
                String oldValue = cardProductBean.getProductCode() + "|" + cardProductBean.getDescription() + "|"
                        + "" + cardProductBean.getDomainCode() + "|" + cardProductBean.getCardType() + "|"
                        + "" + cardProductBean.getValidityPeriod() + "|" + cardProductBean.getDisplayDigit() + "|" + cardProductBean.getStatus();
                request.setAttribute("oldValue", oldValue);
                request.setAttribute("operationtype", "update");
                sessionVarlist.setCardProductBinBeanLst(null);
                request.setAttribute("cardProductBean", cardProductBean);
                //*******************//  test
                this.getAllCardProductBinList();
                List<CardProductBean> cardProductBeansList = new ArrayList<CardProductBean>();
                if (null != allCardProductBinList && allCardProductBinList.size() > 0) {

                    for (CardProductBean cardProductBean : allCardProductBinList) {
                        if (cardProductBean.getProductCode().equals(cardProductCode)) {
                            this.getAllAssignCardKeyList(Integer.parseInt(cardProductBean.getCardKey()));
                            CardProductBean bean = new CardProductBean();
                            bean.setProductCode(cardProductBean.getProductCode());
                            bean.setBin(cardProductBean.getBin());
                            for (CardProductBean binBean : assignBinList) {
                                if (binBean.getBin().equals(cardProductBean.getBin())) {
                                    bean.setBinDes(binBean.getBinDes());
                                }
                            }
                            bean.setCardKey(cardProductBean.getCardKey());
                            if (null != assignCardKeyList && assignCardKeyList.size() > 0) {
                                bean.setCardKeyDes(assignCardKeyList.get(0).getCardKeyDes());
                            }

                            if (null != bean.getCardKey() && !bean.getCardKey().equals("")) {
                                this.getProductionModeList(bean.getCardKey());
                                if (null != productionModeList && productionModeList.size() > 0) {
                                    bean.setCardProductionMode(productionModeList.get(0).getCardProductionMode());
                                    bean.setCardProductionModeDes(productionModeList.get(0).getCardProductionModeDes());
                                }
                            }


                            cardProductBeansList.add(bean);
                        }
                    }
                    // request.setAttribute("AssignCardKeyList", cardProductBeansList);
                    List<CardProductBean> notAssignCardList = new ArrayList<CardProductBean>();
                    for (CardProductBean cardProductBean : cardProductBeansList) {
                        this.getAllNotAssignCardKeyList(Integer.parseInt(cardProductBean.getCardKey()));
                        if (null != notAssignCardKeyList && notAssignCardKeyList.size() > 0) {
                            notAssignCardList.add(notAssignCardKeyList.get(0));
                        }
                    }
                    //   request.setAttribute("NotAssignCardKeyList", notAssignCardList);                    

                }
                if (RemoveOpertation == null) {
                    if (cardProductBeansList.size() > 0) {
                        sessionVarlist.setCardProductBinBeanLst(cardProductBeansList);
                    }
                } else if (RemoveOpertation.equals("remove")) {
                    sessionVarlist.setCardProductBinBeanLst(sessionList);
                }
                sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                rd = getServletContext().getRequestDispatcher(url);
                //***************************************// test             
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

        } catch (SQLException ex) {
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", cardProductBean.getProductCode() + " " + errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_CARD_PRODUCT);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * to get all the card types
     * @throws Exception 
     */
    private void getAllCardTypeList() throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            cardTypeList = cardProductMgtManager.getAllCardTypeList();

        } catch (Exception ex) {
            throw ex;
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
    private void getNotAssignBinList(String type, String domain, String prodCode) throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            notAssignBinList = cardProductMgtManager.getNotAssignBinList(type, domain, prodCode);

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to retrieve the assigned BINs for the given card product
     * @param category
     * @throws Exception 
     */
    private void getAssignBinList(String category) throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            assignBinList = cardProductMgtManager.getAssignBinList(category);

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void setUserInputToBean(HttpServletRequest request) {

        cardProductBean = new CardProductBean();


        cardProductBean.setProductCode(request.getParameter("productcode").trim());
        cardProductBean.setDescription(request.getParameter("productcodedes").trim());
        cardProductBean.setCardType(request.getParameter("cardtype").trim());
        cardProductBean.setDomainCode(request.getParameter("carddomain"));
        cardProductBean.setValidityPeriod(request.getParameter("validity"));
        cardProductBean.setStatus(request.getParameter("statuscode").trim());
        cardProductBean.setDisplayDigit(request.getParameter("displayDigit").trim());

    }

    private void getAllCardDomainList() throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            cardDomainList = cardProductMgtManager.getAllCardDomainList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllCardProductBinList() throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            //  BinList = cardProductMgtManager.getCardProductBinsLIst(assignedBinList);
            allCardProductBinList = cardProductMgtManager.getAllCardProductBinList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllAssignCardKeyList(int cardKeyId) throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            //  BinList = cardProductMgtManager.getCardProductBinsLIst(assignedBinList);
            assignCardKeyList = cardProductMgtManager.getAllAssignCardKeyList(cardKeyId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllNotAssignCardKeyList(int cardKeyId) throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            //  BinList = cardProductMgtManager.getCardProductBinsLIst(assignedBinList);
            notAssignCardKeyList = cardProductMgtManager.getAllNotAssignCardKeyList(cardKeyId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getProductionModeList(String cardKeyId) throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            //  BinList = cardProductMgtManager.getCardProductBinsLIst(assignedBinList);
            productionModeList = cardProductMgtManager.getProductionModeList(cardKeyId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllProductionModeList() throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            //  BinList = cardProductMgtManager.getCardProductBinsLIst(assignedBinList);
            cardProductionModeList = cardProductMgtManager.getAllProductionModeList();

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
