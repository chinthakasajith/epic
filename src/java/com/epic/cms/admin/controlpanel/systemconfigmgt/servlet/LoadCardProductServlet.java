/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardProductMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
public class LoadCardProductServlet extends HttpServlet {

    //initializing variables
    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private List<StatusBean> statusList;
    private SessionVarList sessionVarlist;
    private SystemUserManager systemUserManager;
    private List<String> NotAssignBinList = null;
    private List<String> cardProductBinList = null;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> cardDomainList = null;
    private List<CardProductBean> cardProductBeanLst = null;
    private CardProductMgtManager cardProductMgtManager;
    private String errorMessage = null;
    private String url = "/administrator/controlpanel/systemconfigmgt/cardproductmgthome.jsp";
    //------
    private List<CardProductBean> allBinList;
    List<CardProductBean> BinList = null;
    private String[] assignedBinList;
    private String[] notAssignedBin;
    private String[] assignedCardList;
    private String[] notAssignedCard;
    private String assignType;
    private CardProductBean cardProductBean;
    private List<CardProductBean> assignBinList;
    private List<CardProductBean> notAssignBinList;
    List<CardProductBean> CardKeyList = null;
    List<CardProductBean> assignCardKeyList = null;
    List<CardProductBean> assignCardKeyList1 = null;
    List<CardProductBean> notAssignCardKeyList = null;
    List<CardProductBean> notAssignCardKeyList1 = null;
    List<CardProductBean> AllCardKeyList = null;
    private String assignedBin = null;
    private CardProductBean bean;
    private String resetType = null;
    private String operationType = null;
    private List<CardProductBean> cardProductionModeList = null;
    private List<CardProductBean> productionModeCardList = null;
    private List<CardProductBean> sessionList = new ArrayList<CardProductBean>();
    private CardProductBean cardProductBeans = null;

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

            try {
                //set page code and task codes
                String pageCode = PageVarList.CARDPRODUCT;
                String taskCode = TaskVarList.ACCESSPAGE;


                //check whether userrole have an access for this page and task
                if (!this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }

                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                throw adex;

            }

            //---------------------------------------------------------------------------------------------------------------------------------------------------//          
            String cardType = "";
            String cardDomain = "";
            if (request.getAttribute("isBack") != null) {
                cardType = request.getParameter("cardtype");
                cardDomain = request.getParameter("carddomain");
            }
//            else {
//                cardType = "VISA";
//            }

            String RemoveOpertation = null;
            RemoveOpertation = request.getParameter("RemoveOpertation");
            if (null != RemoveOpertation) {
                sessionList.clear();
                sessionList.addAll(sessionVarlist.getCardProductBinBeanLst());
                setUserInputToBean(request);
            }


            assignType = request.getParameter("assignType");
            operationType = request.getParameter("operationType");
            assignedBinList = request.getParameterValues("assignlist");
            notAssignedBin = request.getParameterValues("notassignlist");

            errorMessage = (String) request.getAttribute("errorMsg");

            resetType = request.getParameter("resetType");

            if (resetType != null && resetType.equals("reset")) {
                sessionVarlist.setCardProductBinBeanLst(null);
            }

            if (null != sessionVarlist.getIsBack() && sessionVarlist.getIsBack().equals("updateCardProduct") && null == assignType) {
                sessionVarlist.setCardProductBinBeanLst(null);
                sessionVarlist.setIsBack(null);
            }

            assignedCardList = request.getParameterValues("assigncardkeylist");
            notAssignedCard = request.getParameterValues("notassigncardkeylist");

            assignedBin = request.getParameter("assignedBin");
            StringBuffer assignedBinBuf = new StringBuffer();
            StringBuffer notAssignedBuf = new StringBuffer();

            List<String> assignBinList = new ArrayList<String>();
            List<String> notAssignList = new ArrayList<String>();

            List<Integer> assignCardList = new ArrayList<Integer>();
            List<Integer> notAssignCardList = new ArrayList<Integer>();


            if (null != assignedBinList && assignedBinList.length > 0) {

                for (int i = 0; i < assignedBinList.length; i++) {
                    assignedBinBuf.append(assignedBinList[i]).append(",");
                    assignBinList.add(assignedBinList[i]);
                }
            }
            if (assignedBinBuf.length() > 0) {
                assignedBinBuf = assignedBinBuf.delete(assignedBinBuf.length() - 1, assignedBinBuf.length());
            }

            if (null != notAssignedBin && notAssignedBin.length > 0) {
                for (int i = 0; i < notAssignedBin.length; i++) {
                    notAssignedBuf.append(notAssignedBin[i]).append(",");
                    notAssignList.add(notAssignedBin[i]);
                }
            }
            if (notAssignedBuf.length() > 0) {
                notAssignedBuf = notAssignedBuf.delete(notAssignedBuf.length() - 1, notAssignedBuf.length());
            }

            if (null != assignedCardList && assignedCardList.length > 0) {
                for (int i = 0; i < assignedCardList.length; i++) {
                    assignCardList.add(Integer.parseInt(assignedCardList[i]));
                }
            }

            if (null != notAssignedCard && notAssignedCard.length > 0) {
                for (int i = 0; i < notAssignedCard.length; i++) {
                    notAssignCardList.add(Integer.parseInt(notAssignedCard[i]));
                }
            }

            if (null != operationType && operationType.equals("add")) {
                request.setAttribute("operationtype", "add");
            } else if (null != operationType && operationType.equals("update")) {
                request.setAttribute("operationtype", "update");
            } else {
                request.setAttribute("operationtype", "add");
            }
            this.getAllCardTypeList();
            this.getAllCardDomainList();
            this.getAllCardProductDetailsList();
            this.getAllStatus(StatusVarList.GENESTATUSCAT);
            this.getCardProductBins(cardType, cardDomain);
            this.getAllProductionModeList();



            request.setAttribute("cardProductBeanLst", cardProductBeanLst);
            request.setAttribute("cardTypeList", cardTypeList);
            request.setAttribute("cardDomainList", cardDomainList);
            request.setAttribute("cardProductionModeList", cardProductionModeList);

            if (request.getAttribute("isBack") != null) {
                NotAssignBinList = (List<String>) request.getAttribute("NotAssignbinList");
                request.setAttribute("NotAssignbinList", NotAssignBinList);
            } else {
                request.setAttribute("NotAssignbinList", BinList);
            }

            if (assignType != null && assignType.equals("binAssign")) {
                this.setUserInputToBean(request, assignBinList, notAssignList, "bin");
                errorMessage = "";
                request.setAttribute("cardProductBean", cardProductBean);

                request.setAttribute("AssignbinList", this.assignBinList);

                request.setAttribute("NotAssignbinList", notAssignBinList);
                if (null != sessionVarlist.getCardProductBinBeanLst() && sessionVarlist.getCardProductBinBeanLst().size() > 0) {
                    List<CardProductBean> list = new ArrayList<CardProductBean>();
                    list.addAll(sessionVarlist.getCardProductBinBeanLst());

                    for (CardProductBean sessionBean : list) {
                        for (String assignList : notAssignList) {
                            if (sessionBean.getBin().equals(assignList)) {
                                sessionVarlist.getCardProductBinBeanLst().remove(sessionBean);
                            }
                        }
                    }
                }

            }
            StringBuffer cardKeyBuf = new StringBuffer();
            if (assignType != null && assignType.equals("cardKeyAssign")) {
                this.setUserInputToBean(request, assignBinList, notAssignList, "bin");
                errorMessage = "";
                request.setAttribute("cardProductBean", cardProductBean);

                request.setAttribute("AssignbinList", this.assignBinList);

                request.setAttribute("NotAssignbinList", notAssignBinList);
                this.getAllCardKeyList(cardProductBean.getProductCode(), assignedBin);

                if (CardKeyList != null && !CardKeyList.isEmpty()) {
                    for (CardProductBean cardProductBean : CardKeyList) {
                        cardKeyBuf.append(cardProductBean.getCardKey()).append(",");
                    }
                }

                if (assignCardList.isEmpty()) {
                    if (CardKeyList != null && !CardKeyList.isEmpty()) {
                        for (CardProductBean cardProductBean : CardKeyList) {
                            assignCardList.add(Integer.parseInt(cardProductBean.getCardKey()));
                        }
                    }
                }
                if (notAssignCardList.isEmpty()) {
                    this.getAllCardKeyList();
                    List<CardProductBean> list = new ArrayList<CardProductBean>();
                    list.addAll(AllCardKeyList);
                    for (CardProductBean listBean : list) {
                        for (Integer integer : assignCardList) {
                            if (listBean.getCardKey().equals(Integer.toString(integer))) {
                                AllCardKeyList.remove(listBean);
                            }
                        }
                    }

                    for (CardProductBean bean : AllCardKeyList) {
                        notAssignCardList.add(Integer.parseInt(bean.getCardKey()));
                    }


                }

//********************************//                
                if (cardKeyBuf.length() > 0) {
                    cardKeyBuf = cardKeyBuf.delete(cardKeyBuf.length() - 1, cardKeyBuf.length());

                    if (!cardProductBean.getBin().equals("")) {
                        if (null != assignCardList && assignCardList.size() > 0) {
                            assignCardKeyList1 = new ArrayList<CardProductBean>();
                            for (Integer integer : assignCardList) {
                                this.getAllAssignCardKeyList(integer);
                                if (null != assignCardKeyList && assignCardKeyList.size() > 0) {
                                    assignCardKeyList1.add(assignCardKeyList.get(0));
                                }
                            }
                            request.setAttribute("AssignCardKeyList", assignCardKeyList1);
                        }
                        if (null != notAssignCardList && notAssignCardList.size() > 0) {
                            notAssignCardKeyList1 = new ArrayList<CardProductBean>();
                            for (Integer integer : notAssignCardList) {
                                this.getAllAssignCardKeyList(integer);
                                if (null != assignCardKeyList && assignCardKeyList.size() > 0) {
                                    notAssignCardKeyList1.add(assignCardKeyList.get(0));
                                }
                            }
                            request.setAttribute("NotAssignCardKeyList", notAssignCardKeyList1);
                        }
                        if (assignCardList.isEmpty() && notAssignCardList.isEmpty()) {
                            this.getAllCardKeyList();
                            request.setAttribute("NotAssignCardKeyList", AllCardKeyList);
                        }
                    } else {
                        request.setAttribute("AssignCardKeyList", null);
                        request.setAttribute("NotAssignCardKeyList", null);
                    }
                } else {
                    this.getAllCardKeyList();
                    assignCardKeyList = new ArrayList<CardProductBean>();

                    request.setAttribute("AssignCardKeyList", assignCardKeyList);
                    request.setAttribute("NotAssignCardKeyList", AllCardKeyList);
                }
            }
            if (assignType != null && assignType.equals("binCardKeyAssign")) {

                List<CardProductBean> bean = new ArrayList<CardProductBean>();
                this.setUserInputToBean(request, assignBinList, notAssignList, "bin");

                request.setAttribute("cardProductBean", cardProductBean);
                request.setAttribute("AssignbinList", this.assignBinList);
                request.setAttribute("NotAssignbinList", notAssignBinList);
                if (!cardProductBean.getBin().equals("")) {
                    this.setUserInputToBean(request, assignCardList, notAssignCardList);
                } else {
                    request.setAttribute("AssignCardKeyList", null);
                    request.setAttribute("NotAssignCardKeyList", null);
                }

                if (validateUserInput(cardProductBean, assignedBinList)) {
                    if (null != cardProductBean) {
                        this.getAllAssignedBinList(cardProductBean.getBin());
                        cardProductBean.setBinDes(BinList.get(0).getBinDes());
                        bean.add(cardProductBean);
                        if (null == sessionVarlist.getCardProductBinBeanLst() || sessionVarlist.getCardProductBinBeanLst().isEmpty()) {
                            if (RemoveOpertation == null) {
                                sessionVarlist.setCardProductBinBeanLst(bean);
                            } else if (RemoveOpertation.equals("remove")) {
                                request.setAttribute("cardProductBean", cardProductBeans);
                                request.setAttribute("AssignbinList", assignBinList);
                                request.setAttribute("NotAssignbinList", notAssignBinList);
                                sessionVarlist.setCardProductBinBeanLst(sessionList);
                            }

                        } else {
                            List<CardProductBean> list = new ArrayList<CardProductBean>();
                            list.addAll(sessionVarlist.getCardProductBinBeanLst());

                            for (CardProductBean cardProductBean1 : list) {
                                if (cardProductBean1.getProductCode().equals(cardProductBean.getProductCode()) && cardProductBean1.getBin().equals(cardProductBean.getBin()) && cardProductBean1.getCardProductionMode().equals(cardProductBean.getCardProductionMode())) {

                                    sessionVarlist.getCardProductBinBeanLst().remove(cardProductBean1);

                                }
                            }
                            if (RemoveOpertation == null) {
                                sessionVarlist.getCardProductBinBeanLst().addAll(bean);
                            } else if (RemoveOpertation.equals("remove")) {
                                request.setAttribute("cardProductBean", cardProductBeans);
                                request.setAttribute("AssignbinList", assignBinList);
                                request.setAttribute("NotAssignbinList", notAssignBinList);
                                sessionVarlist.setCardProductBinBeanLst(sessionList);
                            }
                        }
//
                    }
                }
            }
            if (RemoveOpertation != null) {
                request.setAttribute("errorMsg", "");
            } else {
                request.setAttribute("errorMsg", errorMessage);
            }
//------------------------------------------------------------------------------------------------------------------------------------------------------//
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
            rd = getServletContext().getRequestDispatcher(url);

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
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_CARD_PRODUCT);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * to check whether user can access this page for the required task
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

    /**
     * to retrieve status
     * @param categoryCode
     * @throws Exception 
     */
    private void getAllStatus(String categoryCode) throws Exception {
        try {
            statusList = systemUserManager.getStatusByUserRole(categoryCode);
            sessionVarlist.setStatusList(statusList);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to retrieve all the card types
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

    private void getAllCardDomainList() throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            cardDomainList = cardProductMgtManager.getAllCardDomainList();

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
     * to retrieve all the BINs assigned to the given card type
     * @param category
     * @throws Exception 
     */
    private void getCardProductBins(String category, String cardDomain) throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            BinList = cardProductMgtManager.getCardProductBins(category, cardDomain);

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to retrieve all the BINs assigned to the given card type
     * @param category
     * @throws Exception 
     */
    private void getAllAssignedBinList(String assignedBinList) throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            BinList = cardProductMgtManager.getCardProductBinsLIst(assignedBinList);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllCardKeyList(String cardProductId, String binId) throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            //  BinList = cardProductMgtManager.getCardProductBinsLIst(assignedBinList);
            CardKeyList = cardProductMgtManager.getCardKeyLIst(cardProductId, binId);

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

    private void getAllCardKeyList() throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            //  BinList = cardProductMgtManager.getCardProductBinsLIst(assignedBinList);
            AllCardKeyList = cardProductMgtManager.getCardKeyLIst();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to set the user inputs
     * @param request
     * @param assign
     * @param notAssign 
     */
    public void setUserInputToBean(HttpServletRequest request, List<String> assignList, List<String> notAssignList, String assignType) throws Exception {

        cardProductBean = new CardProductBean();

        cardProductBean.setProductCode(request.getParameter("productcode"));
        cardProductBean.setDescription(request.getParameter("productcodedes"));
        cardProductBean.setDomainCode(request.getParameter("carddomain"));
        cardProductBean.setCardType(request.getParameter("cardtype"));
        cardProductBean.setValidityPeriod(request.getParameter("validity"));
        cardProductBean.setStatus(request.getParameter("statuscode"));
        cardProductBean.setDisplayDigit(request.getParameter("displayDigit"));
        cardProductBean.setLastUpdateUser(sessionUser.getUserName());
        cardProductBean.setBin(request.getParameter("assignedBin"));
        cardProductBean.setCardProductionMode(request.getParameter("cardProductionMode"));
        cardProductBean.setCardKey(request.getParameter("cardKeyList"));



        if (null != assignList && assignList.size() > 0) {
            assignBinList = new ArrayList<CardProductBean>();
            for (String integer : assignList) {
                this.getAllAssignedBinList(integer.toString());
                assignBinList.add(BinList.get(0));
            }
        } else {
            assignBinList = null;
        }
        if (null != notAssignList && notAssignList.size() > 0) {
            notAssignBinList = new ArrayList<CardProductBean>();
            for (String integer : notAssignList) {
                this.getAllAssignedBinList(integer.toString());
                notAssignBinList.add(BinList.get(0));
            }
        } else {
            notAssignBinList = null;
        }

        if (null != cardProductBean.getCardProductionMode() && !cardProductBean.getCardProductionMode().equals("")) {
            this.getAllProductionModeList();
            for (CardProductBean cardProductBean : cardProductionModeList) {
                if (cardProductBean.getCardProductionMode().equals(this.cardProductBean.getCardProductionMode())) {
                    this.cardProductBean.setCardProductionModeDes(cardProductBean.getCardProductionModeDes());
                }
            }

        }

        if (null != cardProductBean.getCardKey() && !cardProductBean.getCardKey().equals("")) {
            this.getAllAssignCardKeyList(Integer.parseInt(cardProductBean.getCardKey()));
            if (assignCardKeyList.get(0) != null) {
                cardProductBean.setCardKeyDes(assignCardKeyList.get(0).getCardKeyDes());
            }
        }

    }

    public void setUserInputToBean(HttpServletRequest request, List<Integer> cardKeyList, List<Integer> notAssignList) throws Exception {

        if (null != cardKeyList && cardKeyList.size() > 0) {
            assignCardKeyList1 = new ArrayList<CardProductBean>();
            for (Integer integer : cardKeyList) {
                this.getAllAssignCardKeyList(integer);
                if (null != assignCardKeyList && assignCardKeyList.size() > 0) {
                    assignCardKeyList1.add(assignCardKeyList.get(0));
                }
            }
            //sessionVarlist.setAssignCardKeyList(assignCardKeyList1);
            request.setAttribute("AssignCardKeyList", assignCardKeyList1);
        }
        if (null != notAssignList && notAssignList.size() > 0) {
            notAssignCardKeyList1 = new ArrayList<CardProductBean>();
            for (Integer integer : notAssignList) {
                this.getAllAssignCardKeyList(integer);
                if (null != assignCardKeyList && assignCardKeyList.size() > 0) {
                    notAssignCardKeyList1.add(assignCardKeyList.get(0));
                }
            }
            //  sessionVarlist.setNotAssignCardKeyList(notAssignCardKeyList1);
            request.setAttribute("NotAssignCardKeyList", notAssignCardKeyList1);
        }

    }

    public boolean validateUserInput(CardProductBean cardProductBean, String[] assignedBin) throws Exception {
        boolean isValidate = true;

        try {

            if (cardProductBean.getProductCode().contentEquals("") || cardProductBean.getProductCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCTCODE_NULL;
            } else if (!UserInputValidator.isAlphaNumeric(cardProductBean.getProductCode())) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCTCODE_INVALID;

            } else if (cardProductBean.getDescription().contentEquals("") || cardProductBean.getDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCTDESCRIPTION_NULL;
            } else if (!UserInputValidator.isCorrectString(cardProductBean.getDescription()) || cardProductBean.getDescription().contentEquals("'") || cardProductBean.getDescription().contentEquals("'''")) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCTDES_INVALID;
            } else if (cardProductBean.getDomainCode().contentEquals("") || cardProductBean.getDomainCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_CARDDOMAIN_NULL;
            } else if (cardProductBean.getCardType().contentEquals("") || cardProductBean.getCardType().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_CARDTYPE_NULL;
            } else if (cardProductBean.getValidityPeriod().contentEquals("") || cardProductBean.getValidityPeriod().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_VALIDITY_NULL;
            } else if (!UserInputValidator.isAlphaNumeric(cardProductBean.getValidityPeriod())) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_VALIDITY_INVALID;
            } else if (cardProductBean.getStatus().contentEquals("") || cardProductBean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCTSTATUS_NULL;
            /** Hide display digit for DFCC
            } else if (cardProductBean.getDisplayDigit().contentEquals("") || cardProductBean.getDisplayDigit().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCT_DISPLAYDIGIT_NULL;
            } else if (!UserInputValidator.isNumeric(cardProductBean.getDisplayDigit())) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCT_DISPLAYDIGIT_INVALID;
            } else if (!(cardProductBean.getDisplayDigit().length() == 3)) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_PRODUCT_DISPLAYDIGIT_INVALID_LENGTH;
            **/
            } else if (assignedBin == null || assignedBin.length <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BIN_NULL;
            } else if (cardProductBean.getCardKey().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.CARD_KEY_NULL;
            }

            if (isValidate) {
                errorMessage = "";
            }

        } catch (Exception ex) {
            isValidate = false;

        }

        return isValidate;
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

    private void getProductionModeBaseCardList(String productionModeCode) throws Exception {
        try {

            cardProductMgtManager = new CardProductMgtManager();
            //  BinList = cardProductMgtManager.getCardProductBinsLIst(assignedBinList);
            productionModeCardList = cardProductMgtManager.getProductionModeBaseCardList(productionModeCode);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setUserInputToBean(HttpServletRequest request) throws Exception {
        cardProductBeans = new CardProductBean();

        cardProductBeans.setProductCode(request.getParameter("productcode"));
        cardProductBeans.setDescription(request.getParameter("productcodedes"));
        cardProductBeans.setDomainCode(request.getParameter("carddomain"));
        cardProductBeans.setCardType(request.getParameter("cardtype"));
        cardProductBeans.setValidityPeriod(request.getParameter("validity"));
        cardProductBeans.setStatus(request.getParameter("statuscode"));
        cardProductBeans.setDisplayDigit(request.getParameter("displayDigit"));
        cardProductBeans.setLastUpdateUser(sessionUser.getUserName());
        cardProductBeans.setBin(request.getParameter("assignedBin"));
        cardProductBeans.setCardProductionMode(request.getParameter("cardProductionMode"));
        cardProductBeans.setCardKey(request.getParameter("cardKeyList"));


        cardProductMgtManager = new CardProductMgtManager();
        allBinList = cardProductMgtManager.getCardProductBins(request.getParameter("cardtype"), request.getParameter("carddomain"));

        assignedBinList = request.getParameterValues("assignlist");
        notAssignedBin = request.getParameterValues("notassignlist");

        int k = 0;
        assignBinList = new ArrayList<CardProductBean>();
        if (assignedBinList.length != 0) {
            while (assignedBinList.length > k) {
                bean = new CardProductBean();
                bean.setBin(assignedBinList[k]);
                for (int i = 0; i < allBinList.size(); i++) {
                    if (bean.getBin().equals(allBinList.get(i).getBin())) {
                        bean.setBinDes(allBinList.get(i).getBinDes());
                    }
                }
                assignBinList.add(bean);
                k++;
            }
        }

        int l = 0;
        notAssignBinList = new ArrayList<CardProductBean>();
        while (notAssignedBin.length > l) {
            bean = new CardProductBean();
            bean.setBin(notAssignedBin[l]);
            for (int i = 0; i < allBinList.size(); i++) {
                if (bean.getBin().equals(allBinList.get(i).getBin())) {
                    bean.setBinDes(allBinList.get(i).getBinDes());
                }
            }
            notAssignBinList.add(bean);
            l++;
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
