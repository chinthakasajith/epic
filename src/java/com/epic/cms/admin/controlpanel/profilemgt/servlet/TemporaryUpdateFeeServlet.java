/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.servlet;

import com.epic.cms.admin.controlpanel.profilemgt.bean.FeeProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.businesslogic.FeeProfileManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
import com.epic.cms.admin.templatemgt.customertemplate.businesslogic.CustomerTemplateManager;
import com.epic.cms.application.common.bean.StatusBean;
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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * this class will do all the temporary updates in session which is relevant to
 * fee and fee profile fee
 *
 * @author nisansala
 */
public class TemporaryUpdateFeeServlet extends HttpServlet {

    HttpSession sessionObj;
    private String errorMessage;
    private Boolean validate;
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private List<CurrencyBean> currencyList;
    private List<String> userTaskList;
    private List<StatusBean> statusList;
    private CustomerTemplateManager templateManager;
    private SystemUserManager systemUserManager = null;
    private FeeProfileBean feeProfBean;
    private FeeProfileManager feeProfMgr;
    private FeeBean tempUpdateBean = null;
    private FeeBean inputBean = null;
    private List<FeeBean> txnFeeList = null;
    private List<FeeBean> serviceFeeList = null;
    private List<FeeBean> feeBeanList = null;
    private List<FeeBean> unusedtxnFeeList = null;
    private List<FeeBean> unusedServiceFeeList = null;
    private String url1 = "/administrator/controlpanel/profilemgt/temporaryfeeupdate.jsp";
    private String url = "/administrator/controlpanel/profilemgt/createfeeprofile.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            /////////////////////////////////////////////////////////////////////
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

            // opType =  add or update
            // activity = remove or update temporary
            try {

                //--------------------------------------------on adding a new fee profile code--------------------------------------------------------
                if (request.getParameter("opType").equals("add")) {
                    request.setAttribute("operationtype", "add");

                    try {
                        setUserInputToBean(request);
                        sessionVarlist.setFeeBean(feeProfBean);

                        //on removing a fee record temporary from the session
                        if (request.getParameter("activity").equals("remove")) {
                            //fee code which is to be removed from the session
                            String feeCode = request.getParameter("feeCode");
                            //remove a fee code with fee type is transaction
                            if (request.getParameter("feeType").equals("TXN")) {
                                txnFeeList = sessionVarlist.getTxnFeeList();
                                for (int i = 0; i < txnFeeList.size(); i++) {
                                    if (txnFeeList.get(i).getFeeCode().equals(feeCode)) {
                                        txnFeeList.remove(i);
                                    }
                                }
                                //set the modified list again to the session
                                sessionVarlist.setTxnFeeList(txnFeeList);
                            }
                            //remove a fee code with fee type is service
                            if (request.getParameter("feeType").equals("SER")) {
                                serviceFeeList = sessionVarlist.getServiceFeeList();
                                for (int i = 0; i < serviceFeeList.size(); i++) {
                                    if (serviceFeeList.get(i).getFeeCode().equals(feeCode)) {
                                        serviceFeeList.remove(i);
                                    }
                                }
                                //set the modified list again to the session
                                sessionVarlist.setServiceFeeList(serviceFeeList);
                            }
                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                            rd = getServletContext().getRequestDispatcher(url);

                        } //on updating a fee record temporary in the session
                        else if (request.getParameter("activity").equals("update")) {
                            //fee code which is to be updated in the session
                            String feeCode = request.getParameter("feeCode");
                            //update the record in the session
                            if (request.getParameter("feeType").equals("TXN")) {
                                txnFeeList = sessionVarlist.getTxnFeeList();
                                for (int i = 0; i < txnFeeList.size(); i++) {
                                    if (txnFeeList.get(i).getFeeCode().equals(feeCode)) {
                                        tempUpdateBean = txnFeeList.get(i);
                                    }
                                    sessionVarlist.setRealFeeBean(tempUpdateBean);
                                    request.setAttribute("feeType", "TXN");
                                }
                            }
                            if (request.getParameter("feeType").equals("SER")) {
                                serviceFeeList = sessionVarlist.getServiceFeeList();
                                for (int i = 0; i < serviceFeeList.size(); i++) {
                                    if (serviceFeeList.get(i).getFeeCode().equals(feeCode)) {
                                        tempUpdateBean = serviceFeeList.get(i);
                                    }
                                    sessionVarlist.setRealFeeBean(tempUpdateBean);
                                    request.setAttribute("feeType", "TXN");
                                }
                            }
                            this.getAllCurrencyList();
                            request.setAttribute("currencyList", currencyList);

                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                            rd = getServletContext().getRequestDispatcher(url1);
                        }
                    } catch (SQLException e) {
                        OracleMessage message = new OracleMessage();
                        String oraMessage = message.getMessege(e.getMessage());

                        request.setAttribute("errorMsg", oraMessage);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    } catch (Exception ex) {
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }
                } //----------------------------------------on updating an existing fee profile code------------------------------------------------
                else if (request.getParameter("opType").equals("update")) {
                    try {
                        feeProfMgr = new FeeProfileManager();
                        unusedtxnFeeList = new ArrayList<FeeBean>();
                        unusedServiceFeeList = new ArrayList<FeeBean>();

                        feeProfBean = sessionVarlist.getFeeBean();
                        sessionVarlist.setFeeBean(feeProfBean);
                        request.setAttribute("operationtype", "update");
                        //on removing a fee record temporary from the session
                        if (request.getParameter("activity").equals("remove")) {

                            //fee code which is to be removed
                            String feeCode = request.getParameter("feeCode");
                            //remove a fee code with fee type is transaction
                            if (request.getParameter("feeType").equals("TXN")) {
                                txnFeeList = sessionVarlist.getTxnFeeList();
                                for (int i = 0; i < txnFeeList.size(); i++) {
                                    if (txnFeeList.get(i).getFeeCode().equals(feeCode)) {
                                        txnFeeList.remove(i);
                                    }
                                }
                                //set the modified list again to the session
                                sessionVarlist.setTxnFeeList(txnFeeList);
                            }
                            //remove a fee code with fee type is service
                            if (request.getParameter("feeType").equals("SER")) {
                                serviceFeeList = sessionVarlist.getServiceFeeList();
                                for (int i = 0; i < serviceFeeList.size(); i++) {
                                    if (serviceFeeList.get(i).getFeeCode().equals(feeCode)) {
                                        serviceFeeList.remove(i);
                                    }
                                }
                                //set the modified list again to the session
                                sessionVarlist.setServiceFeeList(serviceFeeList);
                            }

                            //load all fee data according to the fee cateogry
                            feeBeanList = feeProfMgr.getAllFeeDetail(feeProfBean.getFeeCategory());

                            serviceFeeList = sessionVarlist.getServiceFeeList();
                            txnFeeList = sessionVarlist.getTxnFeeList();
                            //set the fee records which are not used in fee profile fee
                            for (int i = 0; i < feeBeanList.size(); i++) {
                                String fCode = feeBeanList.get(i).getFeeCode();
                                if (feeBeanList.get(i).getFeeType().equals("SER")) {
                                    if (serviceFeeList.isEmpty()) {
                                        unusedServiceFeeList.add(feeBeanList.get(i));
                                    } else {
                                        for (int j = 0; j < serviceFeeList.size(); j++) {
                                            if (serviceFeeList.get(j).getFeeCode().equals(fCode)) {
                                                break;
                                            } else if (j == serviceFeeList.size() - 1) {
                                                unusedServiceFeeList.add(feeBeanList.get(i));
                                            }
                                        }
                                    }
                                } else if (feeBeanList.get(i).getFeeType().equals("TXN")) {
                                    if (txnFeeList.isEmpty()) {
                                        unusedtxnFeeList.add(feeBeanList.get(i));
                                    } else {
                                        for (int j = 0; j < txnFeeList.size(); j++) {
                                            if (txnFeeList.get(j).getFeeCode().equals(fCode)) {
                                                break;
                                            } else if (j == txnFeeList.size() - 1) {
                                                unusedtxnFeeList.add(feeBeanList.get(i));
                                            }
                                        }
                                    }
                                }
                            }

                            request.setAttribute("unusedServiceFeeList", unusedServiceFeeList);
                            request.setAttribute("unusedtxnFeeList", unusedtxnFeeList);

                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                            rd = getServletContext().getRequestDispatcher(url);

                        } //on updating a fee record temporary in the session
                        else if (request.getParameter("activity").equals("update")) {
                            String feeCode = request.getParameter("feeCode");
                            if (request.getParameter("feeType").equals("TXN")) {
                                txnFeeList = sessionVarlist.getTxnFeeList();
                                for (int i = 0; i < txnFeeList.size(); i++) {
                                    if (txnFeeList.get(i).getFeeCode().equals(feeCode)) {
                                        tempUpdateBean = txnFeeList.get(i);
                                        tempUpdateBean.setFeeType("TXN");
                                        tempUpdateBean.setFeeCategory(request.getParameter("category"));
                                    }
                                    sessionVarlist.setRealFeeBean(tempUpdateBean);
                                    request.setAttribute("feeType", "TXN");
                                }
                            }
                            if (request.getParameter("feeType").equals("SER")) {
                                serviceFeeList = sessionVarlist.getServiceFeeList();
                                for (int i = 0; i < serviceFeeList.size(); i++) {
                                    if (serviceFeeList.get(i).getFeeCode().equals(feeCode)) {
                                        tempUpdateBean = serviceFeeList.get(i);
                                        tempUpdateBean.setFeeType("SER");
                                        tempUpdateBean.setFeeCategory(request.getParameter("category"));
                                    }
                                    sessionVarlist.setRealFeeBean(tempUpdateBean);
                                    request.setAttribute("feeType", "TXN");
                                }
                            }
                            this.getAllCurrencyList();
                            request.setAttribute("currencyList", currencyList);

                            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
                            rd = getServletContext().getRequestDispatcher(url1);
                        }

                    } catch (SQLException e) {
                        OracleMessage message = new OracleMessage();
                        String oraMessage = message.getMessege(e.getMessage());

                        request.setAttribute("errorMsg", oraMessage);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    } catch (Exception ex) {
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }

                } //------------------------------------------------to edit fee details temporary---------------------------------------------------
                else if (request.getParameter("opType").equals("edit")) {
                    try {
                        feeProfMgr = new FeeProfileManager();
                        unusedtxnFeeList = new ArrayList<FeeBean>();
                        unusedServiceFeeList = new ArrayList<FeeBean>();
                        tempUpdateBean = new FeeBean();

                        //retrieve currency details
                        this.getAllCurrencyList();
                        request.setAttribute("currencyList", currencyList);

                        String feeCode = request.getParameter("feeCode");

                        sessionVarlist.setFeeBean(feeProfBean);

                        //if it comes here when adding a new fee profile
                        if (request.getParameter("operType").equals("add")) {
                            request.setAttribute("operationtype", "add");

                            if (request.getParameter("feeType").equals("TXN")) {
                                txnFeeList = sessionVarlist.getTxnFeeList();

                                setUserInputFeeToBean(request);

                                validate = validateUserInput(inputBean);

                                if (validate) {

                                    for (int i = 0; i < txnFeeList.size(); i++) {
                                        if (txnFeeList.get(i).getFeeCode().equals(feeCode)) {
                                            txnFeeList.get(i).setCurrency(request.getParameter("currency"));
                                            txnFeeList.get(i).setCrordr(request.getParameter("crordr"));
                                            txnFeeList.get(i).setFlatFee(request.getParameter("flat"));
                                            txnFeeList.get(i).setPercentage(request.getParameter("percentage"));
                                            txnFeeList.get(i).setOption(request.getParameter("option"));
                                            txnFeeList.get(i).setMinAmount(request.getParameter("min"));
                                            txnFeeList.get(i).setMaxAmount(request.getParameter("max"));

                                            //validate = validateUserInput(txnFeeList.get(i));
                                            sessionVarlist.setRealFeeBean(txnFeeList.get(i));//if validation fails
                                        }
                                    }
                                    sessionVarlist.setTxnFeeList(txnFeeList);
                                    rd = getServletContext().getRequestDispatcher(url);
                                } else {
                                    request.setAttribute("currencyList", currencyList);
                                    sessionVarlist.setRealFeeBean(inputBean);
                                    request.setAttribute("errorMsg", errorMessage);
                                    rd = getServletContext().getRequestDispatcher(url1);
                                }

                            } else if (request.getParameter("feeType").equals("SER")) {
                                serviceFeeList = sessionVarlist.getServiceFeeList();

                                setUserInputFeeToBean(request);

                                validate = validateUserInput(inputBean);

                                if (validate) {
                                    for (int i = 0; i < serviceFeeList.size(); i++) {
                                        if (serviceFeeList.get(i).getFeeCode().equals(feeCode)) {
                                            serviceFeeList.get(i).setCurrency(request.getParameter("currency"));
                                            serviceFeeList.get(i).setCrordr(request.getParameter("crordr"));
                                            serviceFeeList.get(i).setFlatFee(request.getParameter("flat"));
                                            serviceFeeList.get(i).setPercentage(request.getParameter("percentage"));
                                            serviceFeeList.get(i).setOption(request.getParameter("option"));
                                            serviceFeeList.get(i).setMinAmount(request.getParameter("min"));
                                            serviceFeeList.get(i).setMaxAmount(request.getParameter("max"));

                                            //validate = validateUserInput(serviceFeeList.get(i));
                                            sessionVarlist.setRealFeeBean(serviceFeeList.get(i));
                                        }
                                    }
                                    sessionVarlist.setServiceFeeList(serviceFeeList);
                                    rd = getServletContext().getRequestDispatcher(url);
                                } else {
                                    request.setAttribute("currencyList", currencyList);
                                    request.setAttribute("errorMsg", errorMessage);
                                    sessionVarlist.setRealFeeBean(inputBean);
                                    rd = getServletContext().getRequestDispatcher(url1);
                                }

                            }
                        }
                        //if it comes here when updating an existing fee profile
                        if (request.getParameter("operType").equals("update")) {
                            request.setAttribute("operationtype", "update");
                            if (request.getParameter("feeType").equals("TXN")) {

                                txnFeeList = sessionVarlist.getTxnFeeList();

                                setUserInputFeeToBean(request);

                                validate = validateUserInput(inputBean);

                                if (validate) {
                                    for (int i = 0; i < txnFeeList.size(); i++) {
                                        if (txnFeeList.get(i).getFeeCode().equals(feeCode)) {
                                            txnFeeList.get(i).setCurrency(request.getParameter("currency"));
                                            txnFeeList.get(i).setCrordr(request.getParameter("crordr"));
                                            txnFeeList.get(i).setFlatFee(request.getParameter("flat"));
                                            txnFeeList.get(i).setPercentage(request.getParameter("percentage"));
                                            txnFeeList.get(i).setOption(request.getParameter("option"));
                                            txnFeeList.get(i).setMinAmount(request.getParameter("min"));
                                            txnFeeList.get(i).setMaxAmount(request.getParameter("max"));

                                            sessionVarlist.setRealFeeBean(txnFeeList.get(i));//if validation fails
                                        }
                                    }
                                    sessionVarlist.setTxnFeeList(txnFeeList);
                                    rd = getServletContext().getRequestDispatcher(url);
                                } else {
                                    request.setAttribute("currencyList", currencyList);
                                    request.setAttribute("errorMsg", errorMessage);
                                    sessionVarlist.setRealFeeBean(inputBean);
                                    rd = getServletContext().getRequestDispatcher(url1);
                                }

                            } else if (request.getParameter("feeType").equals("SER")) {
                                serviceFeeList = sessionVarlist.getServiceFeeList();

                                setUserInputFeeToBean(request);

                                validate = validateUserInput(inputBean);

                                if (validate) {
                                    for (int i = 0; i < serviceFeeList.size(); i++) {
                                        if (serviceFeeList.get(i).getFeeCode().equals(feeCode)) {
                                            serviceFeeList.get(i).setCurrency(request.getParameter("currency"));
                                            serviceFeeList.get(i).setCrordr(request.getParameter("crordr"));
                                            serviceFeeList.get(i).setFlatFee(request.getParameter("flat"));
                                            serviceFeeList.get(i).setPercentage(request.getParameter("percentage"));
                                            serviceFeeList.get(i).setOption(request.getParameter("option"));
                                            serviceFeeList.get(i).setMinAmount(request.getParameter("min"));
                                            serviceFeeList.get(i).setMaxAmount(request.getParameter("max"));

                                            //validate = validateUserInput(serviceFeeList.get(i));
                                            sessionVarlist.setRealFeeBean(serviceFeeList.get(i));
                                        }
                                    }
                                    sessionVarlist.setServiceFeeList(serviceFeeList);
                                    rd = getServletContext().getRequestDispatcher(url);
                                } else {
                                    request.setAttribute("currencyList", currencyList);
                                    request.setAttribute("errorMsg", errorMessage);
                                    sessionVarlist.setRealFeeBean(inputBean);
                                    rd = getServletContext().getRequestDispatcher(url1);
                                }

                            }

                            feeBeanList = feeProfMgr.getAllFeeDetail(feeProfBean.getFeeCategory());

                            serviceFeeList = sessionVarlist.getServiceFeeList();
                            txnFeeList = sessionVarlist.getTxnFeeList();

                            //set unused fee records which are not in fee profle fee
                            for (int i = 0; i < feeBeanList.size(); i++) {
                                String feeCd = feeBeanList.get(i).getFeeCode();
                                if (feeBeanList.get(i).getFeeType().equals("SER")) {
                                    if (serviceFeeList.isEmpty()) {
                                        unusedServiceFeeList.add(feeBeanList.get(i));
                                    } else {
                                        for (int j = 0; j < serviceFeeList.size(); j++) {
                                            if (serviceFeeList.get(j).getFeeCode().equals(feeCd)) {
                                                break;
                                            } else if (j == serviceFeeList.size() - 1) {
                                                unusedServiceFeeList.add(feeBeanList.get(i));
                                            }
                                        }
                                    }

                                } else if (feeBeanList.get(i).getFeeType().equals("TXN")) {
                                    if (txnFeeList.isEmpty()) {
                                        unusedtxnFeeList.add(feeBeanList.get(i));
                                    } else {
                                        for (int j = 0; j < txnFeeList.size(); j++) {
                                            if (txnFeeList.get(j).getFeeCode().equals(feeCd)) {
                                                break;
                                            } else if (j == txnFeeList.size() - 1) {
                                                unusedtxnFeeList.add(feeBeanList.get(i));
                                            }
                                        }
                                    }
                                }
                            }

                            request.setAttribute("unusedServiceFeeList", unusedServiceFeeList);
                            request.setAttribute("unusedtxnFeeList", unusedtxnFeeList);
                        }
                    } catch (SQLException e) {
                        OracleMessage message = new OracleMessage();
                        String oraMessage = message.getMessege(e.getMessage());

                        request.setAttribute("errorMsg", oraMessage);
                        rd = getServletContext().getRequestDispatcher(url1);
                        rd.forward(request, response);
                    } catch (Exception ex) {
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url1);
                        rd.forward(request, response);
                    }

                } //--------------------------------to add a fee record again which are not used in feeprofile fee or removed-----------------------------
                else if (request.getParameter("opType").equals("newadd")) {
                    try {
                        request.setAttribute("operationtype", "update");
                        feeProfMgr = new FeeProfileManager();
                        unusedtxnFeeList = new ArrayList<FeeBean>();
                        unusedServiceFeeList = new ArrayList<FeeBean>();

                        //add a new fee record to the transaction table
                        if (request.getParameter("feeType").equals("TXN")) {
                            String feeCode = request.getParameter("unusedTxnFee");
                            if (!feeCode.equals("")) {
                                //retrieve the record which is going to add to the table
                                tempUpdateBean = feeProfMgr.viewOneFee(feeCode);
                                //append the retrieved record to the relevant list 
                                if (sessionVarlist.getTxnFeeList() != null) {
                                    sessionVarlist.getTxnFeeList().add(tempUpdateBean);
                                } else {
                                    List<FeeBean> txnFeeList = new ArrayList<FeeBean>();
                                    txnFeeList.add(tempUpdateBean);
                                    sessionVarlist.setTxnFeeList(txnFeeList);
                                }

                            } else {
                                request.setAttribute("errorMsg", MessageVarList.NO_TXN_RECORDS);
                            }
                        }
                        //add a new fee record to the service table
                        if (request.getParameter("feeType").equals("SER")) {
                            String feeCode = request.getParameter("unusedServiceFee");
                            if (!feeCode.equals("")) {
                                //retrieve the record which is going to add to the table

                                tempUpdateBean = feeProfMgr.viewOneFee(feeCode);
                                //append the retrieved record to the relevant list 
                                if (sessionVarlist.getServiceFeeList() != null) {
                                    sessionVarlist.getServiceFeeList().add(tempUpdateBean);
                                } else {
                                    List<FeeBean> serviceFeeList = new ArrayList<FeeBean>();
                                    serviceFeeList.add(tempUpdateBean);
                                    sessionVarlist.setServiceFeeList(serviceFeeList);
                                }

                            } else {
                                request.setAttribute("errorMsg", MessageVarList.NO_SER_RECORDS);

                            }
                        }

                        feeBeanList = feeProfMgr.getAllFeeDetail(request.getParameter("category"));
                        serviceFeeList = sessionVarlist.getServiceFeeList();
                        txnFeeList = sessionVarlist.getTxnFeeList();

                        //set unused fee records which are not in fee profle fee or just removed
                        for (int i = 0; i < feeBeanList.size(); i++) {
                            String feeCd = feeBeanList.get(i).getFeeCode();
                            if (feeBeanList.get(i).getFeeType().equals("SER")) {
                                if (serviceFeeList == null) {
                                    unusedServiceFeeList.add(feeBeanList.get(i));
                                } else {
                                    for (int j = 0; j < serviceFeeList.size(); j++) {
                                        if (serviceFeeList.get(j).getFeeCode().equals(feeCd)) {
                                            break;
                                        } else if (j == serviceFeeList.size() - 1) {
                                            unusedServiceFeeList.add(feeBeanList.get(i));
                                        }
                                    }
                                }

                            } else if (feeBeanList.get(i).getFeeType().equals("TXN")) {
                                if (txnFeeList == null) {
                                    unusedtxnFeeList.add(feeBeanList.get(i));
                                } else {
                                    for (int j = 0; j < txnFeeList.size(); j++) {
                                        if (txnFeeList.get(j).getFeeCode().equals(feeCd)) {
                                            break;
                                        } else if (j == txnFeeList.size() - 1) {
                                            unusedtxnFeeList.add(feeBeanList.get(i));
                                        }
                                    }
                                }
                            }
                        }

                        request.setAttribute("unusedServiceFeeList", unusedServiceFeeList);
                        request.setAttribute("unusedtxnFeeList", unusedtxnFeeList);
                        rd = getServletContext().getRequestDispatcher(url);

                    } catch (SQLException e) {
                        OracleMessage message = new OracleMessage();
                        String oraMessage = message.getMessege(e.getMessage());

                        request.setAttribute("errorMsg", oraMessage);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    } catch (Exception ex) {
                        request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    }
                }
                rd.forward(request, response);

            } catch (Exception ex) {
                request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            }

            //set tasks to the session
            sessionVarlist.setUserPageTaskList(userTaskList);

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

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    public Boolean setUserInputFeeToBean(HttpServletRequest request) throws Exception {
        Boolean status = true;
        try {

            inputBean = new FeeBean();
            inputBean.setFeeCode(request.getParameter("feeCode"));
            inputBean.setFeeDes(request.getParameter("feeDes"));
            inputBean.setFeeCategory(request.getParameter("feeCategory"));
            inputBean.setFeeType(request.getParameter("feeType"));
            inputBean.setCurrency(request.getParameter("currency"));
            inputBean.setCrordr(request.getParameter("crordr"));
            inputBean.setFlatFee(request.getParameter("flat"));
            inputBean.setPercentage(request.getParameter("percentage"));
            inputBean.setOption(request.getParameter("option"));
            inputBean.setMinAmount(request.getParameter("min"));
            inputBean.setMaxAmount(request.getParameter("max"));

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    private List<CurrencyBean> getAllCurrencyList() throws Exception {

        try {
            feeProfMgr = new FeeProfileManager();
            currencyList = feeProfMgr.getAllCurrencyLst();
            return currencyList;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean validateUserInput(FeeBean fee) throws Exception {
        boolean isValidate = true;

        try {
            if (fee.getCurrency().contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.CURRENCY_NULL;
            } else if (fee.getCrordr() == null) {
                isValidate = false;
                errorMessage = MessageVarList.CRORDR_NULL;
            } else if (fee.getFlatFee().contentEquals("") || fee.getFlatFee().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.FLAT_FEE_NULL;
            } else if (!UserInputValidator.isDecimalOrNumeric(fee.getFlatFee(), "8", "2")) {
                isValidate = false;
                errorMessage = MessageVarList.FLAT_FEE_INVALID;
            } else if (fee.getPercentage().contentEquals("") || fee.getPercentage().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.PERCENTAGE_NULL;
            } else if (!UserInputValidator.isDecimalOrNumeric(fee.getPercentage(), "3", "2")) {
                isValidate = false;
                errorMessage = MessageVarList.PERCENTAGE_INVALID;
            } else if (fee.getOption() == null) {
                isValidate = false;
                errorMessage = MessageVarList.OPTION_NULL;
            } else if (fee.getMinAmount().contentEquals("") || fee.getMinAmount().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.MIN_AMOUNT_NULL;
            } else if (!UserInputValidator.isNumeric(fee.getMinAmount())) {
                isValidate = false;
                errorMessage = MessageVarList.MIN_AMOUNT_INVALID;
            } else if (fee.getMaxAmount().contentEquals("") || fee.getMaxAmount().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.MAX_AMOUNT_NULL;
            } else if (!UserInputValidator.isNumeric(fee.getMaxAmount())) {
                isValidate = false;
                errorMessage = MessageVarList.MAX_AMOUNT_INVALID;
            }
//            else if (!UserInputValidator.isNumeric(question.getQuestionNo())) {
//                isValidate = false;
//                errorMessage = MessageVarList.SECURITY_QUES_MGT_QUESTION_NO_INVALID;
//            } 

        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
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
        processRequest(request, response);
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
        processRequest(request, response);
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
