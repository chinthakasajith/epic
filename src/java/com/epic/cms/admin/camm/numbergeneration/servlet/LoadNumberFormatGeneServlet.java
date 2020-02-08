/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.camm.numbergeneration.servlet;

import com.epic.cms.admin.camm.numbergeneration.bean.NumberFormatFieldBean;
import com.epic.cms.admin.camm.numbergeneration.businesslogic.NumberGeneFormatBusinessLogic;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * @author upul
 */
public class LoadNumberFormatGeneServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private RequestDispatcher rd = null;
    private SessionUser sessionUser = null;
    private List<String> userTaskList = null;
    private SessionVarList sessionVarlist = null;
    private SystemUserManager systemUserManager = null;
    private List<CardTypeMgtBean> cardTypeMgtBeanLst;
    private List<NumberFormatFieldBean> formatCodeBeans;
    private List<NumberFormatFieldBean> formatFieldBeans;
    private HashMap<String, String> productModes = null;
    private HashMap<String, String> AssignBinList = null;
    private HashMap<String, String> NotAssignBinList = null;
    private HashMap<String, String> cardProductBinList = null;
    private NumberGeneFormatBusinessLogic formatBusinessLogic = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //call to existing session
            HttpSession sessionObj = request.getSession(false);
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
                try {
                    //set page code and task codes
                    String pageCode = PageVarList.CARDNUMBERGENE;
                    String taskCode = TaskVarList.ACCESSPAGE;

                    //check whethre userrole have an access for this page and task
                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                        // is valid acess nothing to do
                    } else {
                        //if invalid throw accessdenied exception
                        throw new AccessDeniedException();
                    }

                    //set tasks to the session
                    sessionVarlist.setUserPageTaskList(userTaskList);


                } catch (AccessDeniedException adex) {
                    throw adex;

                }

            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }

            //set the operation type
            if (request.getParameter("operationtype") == null) {
                request.setAttribute("operationtype", "add");
            } else {
                request.setAttribute("operationtype", request.getParameter("operationtype"));
            }
            //if come from jsp
            if (request.getParameter("isBack") != null) {
                request.setAttribute("operationtype", "add");
            }//if come from a servlet
            if (request.getAttribute("isBack") != null) {
                request.setAttribute("operationtype", "add");
            }

            if (request.getAttribute("isFromError") != null) {
                //do nothing
            } else {
                //refresh the session values initially
                sessionVarlist.setNumFormat(null);
            }
            if (request.getAttribute("disable") != null) {
                //do nothing
            } else {
                //production mode should be enable in add
                sessionVarlist.setDisableProdMode("no");
            }

            //retrieve all number format fields
            this.getAllNumberFormatFields();
            //retrieve all number format details for data table
            this.getAllNumberFormatCodeDetails();
            //retriev card type details
            this.getCardTypeDetails();
            //retrieve production modes
            this.getProductionModes();

            request.setAttribute("productModes", productModes);
            request.setAttribute("formatFieldBeans", formatFieldBeans);
            request.setAttribute("formatCodeBeans", formatCodeBeans);
            request.setAttribute("cardTypeMgtBeanLst", cardTypeMgtBeanLst);

            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);
            //response.sendRedirect(request.getContextPath() + "/controlpanel/systemusermgt/viewsectionpage.jsp");
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemconfigmgt/numbergenformat.jsp");
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
        } finally {
            out.close();
        }
    }

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
     * getAllNumberFormatFields
     * @return
     * @throws Exception 
     */
    public void getAllNumberFormatFields() throws Exception {

        try {
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            formatFieldBeans = formatBusinessLogic.getNumberFormatFieldDetails();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    /**
     * getAllNumberFormatCodeDetails
     * @return
     * @throws Exception 
     */
    public void getAllNumberFormatCodeDetails() throws Exception {

        try {
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            formatCodeBeans = formatBusinessLogic.getNumberFormatCodeDetails();
        } catch (SQLException ex) {
            throw ex;
        }

    }

    /**
     * getCardTypeDetails
     * @return
     * @throws Exception 
     */
    public void getCardTypeDetails() throws Exception {

        try {
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            cardTypeMgtBeanLst = formatBusinessLogic.getCardTypeDetails();
        } catch (SQLException ex) {
            throw ex;
        }

    }

    private void getCardProductBins(String category) throws Exception {
        try {

            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            cardProductBinList = formatBusinessLogic.getCardProductBins(category);

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void getProductionModes() throws Exception {

        try {
            formatBusinessLogic = new NumberGeneFormatBusinessLogic();
            productModes = formatBusinessLogic.getProductionModes();
        } catch (SQLException ex) {
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
