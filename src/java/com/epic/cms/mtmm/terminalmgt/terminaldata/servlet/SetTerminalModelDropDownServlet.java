/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.terminalmgt.terminaldata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.TerminalDataCaptureBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.businesslogic.TerminalDataCaptureManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.StatusVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
 * to load terminal models according to the manufacturer
 * @author nisansala
 */
public class SetTerminalModelDropDownServlet extends HttpServlet {

    //initializing variables
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private SystemUserManager systemUserManager = null;
    String manufacturer = null;
    private TerminalDataCaptureBean terminalBean = null;
    private TerminalDataCaptureManager terminalManager;
    private HashMap<String, String> difManufactermModels;
    private HashMap<String, String> allocateList = null;
    private HashMap<String, String> terminalStatusList = null;
    private HashMap<String, String> manufacturerList = null;
    List<TerminalDataCaptureBean> assignedList;
    List<TerminalDataCaptureBean> notAssignedList;
    private List<TypeMgtBean> txnTypes = null;
    List<TerminalDataCaptureBean> assignedTxnList;
    List<TerminalDataCaptureBean> notAssignedTxnList;
    TerminalDataCaptureBean assignedBean;
    TerminalDataCaptureBean notAssignedBean;
    private String url = "/mtmm/terminalmgt/createterminal.jsp";

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
try{
        try {
            //call to existing session
            HttpSession sessionObj = request.getSession(false);

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
            String[] assignedTxn;
            String[] notAssignedTxn;

            //get the manufacturer to load relavant terminal models
            manufacturer = request.getParameter("manufacturer");

            terminalManager = new TerminalDataCaptureManager();


            try {
                //retrieve terminal models according to the manufacturer
                difManufactermModels = terminalManager.getModelsToManufacturer(manufacturer);
                //load values for the drop down lists
                this.getAllManufacturers();
                this.getAllocationStatus();
                this.getTerminalStatus();
                this.getAllTxnTypes();

                if (request.getParameter("opType").equals("update")) {
                    request.setAttribute("operationtype", "update");
                    //arrays should be initialized anyhow
                    if (request.getParameter("isAllocate").equals("NO")) {
                        assignedTxn = new String[0];
                        notAssignedTxn = new String[0];
                    } else {
                        //----------------------------------
                        if (request.getParameter("assigned").equals("0")) {
                            assignedTxn = new String[0];
                        } else {
                            assignedTxn = request.getParameterValues("assignlist");
                        }

                        if (request.getParameter("notassigned").equals("0")) {
                            notAssignedTxn = new String[0];
                        } else {
                            notAssignedTxn = request.getParameterValues("notassignlist");
                        }
                        //-----------------------------------
//                    //array which holds the assigned transactions
//                    assignedTxn = request.getParameterValues("assignlist");
//                    //array which holds the not assigned transactions
//                    notAssignedTxn = request.getParameterValues("notassignlist");
                    }
                    setUserInputToBean(request, assignedTxn, notAssignedTxn);

                } else if (request.getParameter("opType").equals("create")) {

                    assignedTxn = new String[0];
                    notAssignedTxn = new String[0];
                    request.setAttribute("operationtype", "create");
                    setUserInputToBean(request, assignedTxn, notAssignedTxn);
                }
                request.setAttribute("manufacturerList", manufacturerList);
                request.setAttribute("allocateList", allocateList);
                request.setAttribute("terminalStatusList", terminalStatusList);
                request.setAttribute("difManufactermModels", difManufactermModels);

//                if (request.getParameter("opType").equals("update")) {
//                    request.setAttribute("operationtype", "update");
//                    //arrays should be initialized anyhow
//                    if (request.getParameter("isAllocate").equals("NO")) {
//                        assignedTxn = new String[0];
//                        notAssignedTxn = new String[0];
//                    } else {
//                        //array which holds the assigned transactions
//                        assignedTxn = request.getParameterValues("assignlist");
//                        //array which holds the not assigned transactions
//                        notAssignedTxn = request.getParameterValues("notassignlist");
//                    }
//                    setUserInputToBean(request, assignedTxn, notAssignedTxn);
//
//                } else if (request.getParameter("opType").equals("create")) {
//
//                    assignedTxn = new String[0];
//                    notAssignedTxn = new String[0];
//                    request.setAttribute("operationtype", "create");
//                    setUserInputToBean(request, assignedTxn, notAssignedTxn);
//                }

                try {
                    //to hide the assign  box if terminal is not allocated
                    request.setAttribute("isAllocate", terminalBean.getAllocationStatus());
                    request.setAttribute("assignedTxn", assignedTxnList);
                    request.setAttribute("notAssignedTxn", notAssignedTxnList);
                    request.setAttribute("terminalBean", terminalBean);
                    rd = getServletContext().getRequestDispatcher(url);

                } catch (Exception e) {
                    OracleMessage message = new OracleMessage();
                    String oraMessage = message.getMessege(e.getMessage());

                    request.setAttribute("errorMsg", oraMessage);
                    rd = getServletContext().getRequestDispatcher(url);
                }

            } catch (Exception e) {

                request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
                rd = getServletContext().getRequestDispatcher(url);

            }
            rd.forward(request, response);

        } catch (Exception e) {
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

        } catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {
        } finally {

            out.close();
        }
    }

    public Boolean setUserInputToBean(HttpServletRequest request, String[] assTxn, String[] notAssTxn) throws Exception {
        Boolean status = true;
        try {

            terminalBean = new TerminalDataCaptureBean();

            terminalBean.setTerminalID(request.getParameter("terminalid").trim());
            terminalBean.setName(request.getParameter("name").trim());

            terminalBean.setSerialNo(request.getParameter("serialnumber").trim());
            terminalBean.setManufacturer(request.getParameter("manufacturer").trim());
            terminalBean.setModel(request.getParameter("model").trim());
            terminalBean.setInstallationDate(request.getParameter("installationDate").trim());
            terminalBean.setAllocationStatus(StatusVarList.ALLOCATION_NO);
            terminalBean.setTerminalStatus(StatusVarList.DEACTIVE_STATUS);

            terminalBean.setLastUpdateUser(sessionUser.getUserName());


            if (request.getParameter("opType").equals("update")) {
                String terminalID = request.getParameter("terminalid").trim();
                terminalManager = new TerminalDataCaptureManager();
                TerminalDataCaptureBean trmnlBean = new TerminalDataCaptureBean();

                trmnlBean = terminalManager.viewOneTerminalData(terminalID);
                terminalBean.setMerchantID(request.getParameter("mid"));
                terminalBean.setMcc(request.getParameter("mcc"));
                terminalBean.setCurrency(request.getParameter("currency"));
                terminalBean.setAlloStatus(request.getParameter("allocatestatus"));
                terminalBean.setTerminalStatusDes(request.getParameter("terminalstatus"));

                terminalBean.setActivationDate(trmnlBean.getActivationDate());
                terminalBean.setAllocationStatus(trmnlBean.getAllocationStatus());
                //terminalBean.setTerminalStatus(trmnlBean.getTerminalStatus());

                assignedTxnList = new ArrayList<TerminalDataCaptureBean>();
                notAssignedTxnList = new ArrayList<TerminalDataCaptureBean>();

                int l = 0;
                while (notAssTxn.length > l) {
                    notAssignedBean = new TerminalDataCaptureBean();

                    notAssignedBean.setTransactions(notAssTxn[l]);
                    for (int i = 0; i < txnTypes.size(); i++) {
                        if (notAssignedBean.getTransactions().equals(txnTypes.get(i).getTransactionTypeCode())) {
                            notAssignedBean.setTransactionDes(txnTypes.get(i).getDescription());
                            notAssignedBean.setOnlineTxnCode(txnTypes.get(i).getOnlineTxnCode());
                        }
                    }
                    notAssignedTxnList.add(notAssignedBean);
                    l++;
                }

                int k = 0;
                while (assTxn.length > k) {
                    assignedBean = new TerminalDataCaptureBean();

                    assignedBean.setTransactions(assTxn[k]);
                    for (int i = 0; i < txnTypes.size(); i++) {
                        if (assignedBean.getTransactions().equals(txnTypes.get(i).getTransactionTypeCode())) {
                            assignedBean.setTransactionDes(txnTypes.get(i).getDescription());
                            assignedBean.setOnlineTxnCode(txnTypes.get(i).getOnlineTxnCode());
                        }
                    }

                    assignedTxnList.add(assignedBean);
                    k++;
                }




            }

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    /**
     * get descriptions of terminal allocation status
     */
    private void getAllocationStatus() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            allocateList = terminalManager.getAllocationStatus();
        } catch (Exception e) {
        }
    }

    /**
     * get descriptions of terminal status
     */
    private void getTerminalStatus() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            terminalStatusList = terminalManager.getTerminalStatus();
        } catch (Exception e) {
        }

    }

    /**
     * get descriptions of manufacturers
     */
    private void getAllManufacturers() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            manufacturerList = terminalManager.getAllManufacturers();
        } catch (Exception e) {
        }

    }

    private void getAllTxnTypes() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            txnTypes = terminalManager.getAllTxnTypes();
        } catch (Exception e) {
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
            Logger.getLogger(SetTerminalModelDropDownServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SetTerminalModelDropDownServlet.class.getName()).log(Level.SEVERE, null, ex);
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
