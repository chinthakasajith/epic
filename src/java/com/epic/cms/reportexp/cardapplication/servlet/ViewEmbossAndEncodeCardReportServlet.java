/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.reportexp.cardapplication.bean.EmbossEncodeCardBean;
import com.epic.cms.reportexp.cardapplication.businesslogic.EmbossAndEncodeCardReportManager;
import com.epic.cms.reportexp.commondata.businesslogic.CommonReportDataManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

/**
 *
 * @author nisansala
 */
public class ViewEmbossAndEncodeCardReportServlet extends HttpServlet {

    private SessionVarList sessionVarlist;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private SystemUserManager systemUserManager = null;
    private EmbossEncodeCardBean inputBean;
    private EmbossAndEncodeCardReportManager emEnMgr;
    private List<EmbossEncodeCardBean> searchList;
    private EmbossEncodeCardBean rejBean = null;
    private List<String> userTaskList;
    HttpSession sessionObj;
    private SystemAuditBean systemAuditBean = null;
    private HashMap<String, String> branchMap = null;
    private List<String> userList = null;
    private HashMap<String, String> priorityLevelMap = null;
    private HashMap<String, String> domainMap = null;
    private HashMap<String, String> cardTypeMap = null;
    private HashMap<String, String> cardProductMap = null;
    private CommonReportDataManager commonManager = null;

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
            this.setValuesToBean(request);
            this.searchEmbossEncodeCardReportDetails(inputBean);
            for(EmbossEncodeCardBean bean:searchList){
                if(request.getParameter("id").equals(bean.getApplicationId())){
                    rejBean=bean;
                }
            }
            sessionObj.setAttribute("rejBean", rejBean);
            out.print("success");
           

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
            rd = getServletContext().getRequestDispatcher("/LoadEmbossAndEncodeCardReportServlet");
            rd.include(request, response);


        } catch (SQLException ex) {
            //when throw an sql exception
            out.print("error");
        } catch (Exception ex) {
            System.out.println(ex);
            out.print("error");
        } finally {
            out.close();
        }
    }

    private void searchEmbossEncodeCardReportDetails(EmbossEncodeCardBean inputBean) throws SQLException, Exception {
        emEnMgr = new EmbossAndEncodeCardReportManager();
        searchList = emEnMgr.searchEmbossEncodeCardReportDetails(inputBean);
        //sessionVarlist.setTerminalDataBeanList(searchList);
    }

    public void setValuesToBean(HttpServletRequest request) throws Exception {
        try {
            inputBean = new EmbossEncodeCardBean();

            inputBean.setNic("");
            inputBean.setPassport("");
            //inputBean.setLicence(request.getParameter(""));
            inputBean.setApplicationId("");
            inputBean.setCardNo("");
            inputBean.setBranchCode("");
            inputBean.setUser("");
            inputBean.setPriorityLevelCode("");
            inputBean.setDomainCode("");
            inputBean.setCardNo("");
            inputBean.setProductCode("");
            inputBean.setTypeCode("");
            inputBean.setStatus("");
            inputBean.setFromDate("");
            inputBean.setToDate("");

        } catch (Exception e) {
            throw e;
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
