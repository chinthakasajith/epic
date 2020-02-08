/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.generalledgermgtmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.AssignTxnBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.ChannelLisnrTypeBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.GeneralLedgerMgtBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.TxnTypesBean;
import com.epic.cms.backoffice.generalledgermgtmgt.businesslogic.AssignTxnTypesToGLAccountManager;
import com.epic.cms.backoffice.generalledgermgtmgt.businesslogic.GeneralLedgerMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.json.JSONObject;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
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
public class GetTansactionTypesToGLAccountServlet extends HttpServlet {

    private HttpSession sessionObj;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private RequestDispatcher rd;
    private String url = "/backoffice/generalledgermgt/assigntxntoglaccount.jsp";
    private AssignTxnTypesToGLAccountManager assignmanager;
    private List<TxnTypesBean> txnTypeslist;
    private AssignTxnBean assBean;
    private GeneralLedgerMgtManager glmanager;
    private List<GeneralLedgerMgtBean> allGLAccList;
    private List<AssignTxnBean> allOnlineGLAccList;
    private List<TxnTypesBean> assinedtxnTypeslist;

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
            sessionObj = request.getSession(false);

            try {
                request.setAttribute("operationtype", "add");

                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                //check system user is in the same session or not
                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        //user not in same session.
                        throw new NewLoginSessionException();

                    }

                } catch (NewLoginSessionException nlex) {
                    //throw lst login close exception
                    throw new NewLoginSessionException();

                }

                String accNo = request.getParameter("glacc");//credit
                String drAccNo=request.getParameter("drglacc");//debit
                String chOrLs = request.getParameter("chanOrLisn");
                String chOrLsType = request.getParameter("cltype");

                assBean = new AssignTxnBean();

                assBean.setGlAccNo(accNo);//credit
                assBean.setDrGlAccNo(drAccNo);//debit
                assBean.setChanekOrLisnr(chOrLs);
                assBean.setChOrLsType(chOrLsType);




                assignmanager = new AssignTxnTypesToGLAccountManager();

                txnTypeslist = assignmanager.getTxnTypes(chOrLs, chOrLsType,accNo,drAccNo);
                request.setAttribute("txnTypeslist", txnTypeslist);
                
                assinedtxnTypeslist = assignmanager.getassinedTxnTypes(chOrLs, chOrLsType,accNo,drAccNo);
                request.setAttribute("assTxnTypeslist", assinedtxnTypeslist);

                glmanager = new GeneralLedgerMgtManager();

                allGLAccList = glmanager.getAllGlAccounts();
                request.setAttribute("accList", allGLAccList);

                List<ChannelLisnrTypeBean> chanelLisnrTypeList = new ArrayList<ChannelLisnrTypeBean>();

                chanelLisnrTypeList = assignmanager.getChanelLisnrTypeList(chOrLs);
                request.setAttribute("chlsList", chanelLisnrTypeList);

                allOnlineGLAccList = assignmanager.getOnlineGlAccounts();
                request.setAttribute("allOnlineGLAccList", allOnlineGLAccList);


                request.setAttribute("assignBean", assBean);

                rd = getServletContext().getRequestDispatcher(url);

            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }

            //------------------------------------------------------------------------------------------------------------//
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);


        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
            rd = getServletContext().getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
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
