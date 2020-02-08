/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.debitcardtemplate.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.debitcardtemplate.bean.DebitCardTemplateBean;
import com.epic.cms.admin.templatemgt.debitcardtemplate.businesslogic.DebitCardTemplateMgtManager;
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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.epic.cms.system.util.json.JSONObject;
/**
 *
 * @author badrika
 */
public class GetCardDomainAndTypeServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private DebitCardTemplateMgtManager manager;
    private String urladd = "/administrator/templatemgt/debitcardtemplate/debitcardadd.jsp";
    private String urlupdate = "/administrator/templatemgt/debitcardtemplate/debitcardupdate.jsp";
    private RequestDispatcher rd;
    private String addorupdate = null;

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
        HttpSession sessionObj = request.getSession(false);
        try {
            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        throw new NewLoginSessionException();
                    }
                } catch (NewLoginSessionException nlex) {
                    throw new NewLoginSessionException();
                }

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            ////////////////////////////
            manager = new DebitCardTemplateMgtManager();

            addorupdate = request.getParameter("addorupdate");
            String accTemplte = request.getParameter("code");
            
            
            
            //String cardType = request.getParameter("cType");


            //List<cardProductsBean> cardProducts = new ArrayList<cardProductsBean>();

            DebitCardTemplateBean tempBean = new DebitCardTemplateBean();

            tempBean = manager.getDomainAndType(accTemplte);

            request.setAttribute("tempBean", tempBean);
            

//                out.print(jCombo2);
            if(!accTemplte.equals(null)){
           String data =tempBean.getCardDomain()+","+ tempBean.getCardDomainDes()+","+tempBean.getCardTypeCode()+","+ tempBean.getCardTypeDes();
                out.print(data);
            }else{
             
               
            }
//       

    

        } catch (Exception ex) {
         

        } finally {
            
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
