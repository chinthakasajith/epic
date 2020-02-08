/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationHistoryBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.ApplicationDetailsManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

/**
 *
 * @author nalin
 */
public class ViewApplicationDetailsHistoryServlet extends HttpServlet {

    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    HttpSession sessionObj = null;
    private ApplicationDetailsManager appdetailsManager = null;
    private List<ApplicationHistoryBean> historyList = null;

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

            String applicationID = request.getParameter("applicationID");

            if (applicationID != null) {

                this.getApplicationHistory(applicationID);
            }

            //////////////////////////////////////////////////////////////////////


            //DailyDetailsBean adbS = new DailyDetailsBean();
//            String path = "resources/reports/sltd/DailyDetails.jasper";
//            HashMap map = new HashMap();
//
//            map.put("applicationID", applicationID);
//
//
//            response.setContentType("application/pdf");
//            InputStream inputStream = new FileInputStream(getServletContext().getRealPath(path));
//            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(historyList);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, map, ds);
//
//            JRPdfExporter exp = new JRPdfExporter();
//            exp.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
//            exp.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//            exp.exportReport();







            ////////////////////////////////////////////////////////////////////////////


            sessionObj.setAttribute("applicationID", applicationID);
            sessionObj.setAttribute("historyList", historyList);


            /////////////////////////////////////////////////////////////////////

            out.print("success");

            //////////////////////////////////////////////////////////////////
        } //catch session exception
        catch (SesssionExpException sex) {

            out.print("session");


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            out.print("session");

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            String errMessage = MessageVarList.ACCESS_DENIED_PAGETASK;
            request.setAttribute("errorMsg", errMessage);
            rd = getServletContext().getRequestDispatcher("/LoadSystemAudittraceServlet");
            rd.include(request, response);


        } catch (SQLException ex) {
            //when throw an sql exception
            out.print("error");

        } catch (Exception ex) {
            out.print("error");
        } finally {
            out.close();
        }
    }

    private void getApplicationHistory(String applicationID) throws Exception {
        try {

            appdetailsManager = new ApplicationDetailsManager();
            historyList = new ArrayList<ApplicationHistoryBean>();

            historyList = appdetailsManager.getApplicationHistory(applicationID);

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
