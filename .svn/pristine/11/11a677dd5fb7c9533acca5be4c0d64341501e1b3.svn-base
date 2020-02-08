/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.ApplicationModuleMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.RequestVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ayesh
 */
public class ViewApplicationMgtServlet extends HttpServlet {

    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private RequestDispatcher rd = null;
    private SystemUserManager systemUserManager = null;
    private SystemUserManager sysUserOnj = null;
    private List<StatusBean> statusBean = null;
    private ApplicationModuleMgtManager appMgrOnj = null;
    List<ApplicationModuleBean> allList = null;
    private String url = "/administrator/controlpanel/systemusermgt/applicationmodulmgthome.jsp";

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
        ApplicationModuleBean appBean1 = null;
        try {
            request.setAttribute("operationtype", "viewData");
            HttpSession sessionObj = request.getSession(false);
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

            ApplicationModuleBean appBean = null;
            String appId = request.getParameter("id");
            appMgrOnj = new ApplicationModuleMgtManager();

            for (ApplicationModuleBean bean : appMgrOnj.getAllApplicationList()) {
                if (bean.getApplicationCode().equals(appId)) {
                    appBean = bean;
                }
            }
            if (appBean != null) {
                request.setAttribute(RequestVarList.APPBEAN, appBean);
            }


//            appMgrOnj = new ApplicationModuleMgtManager();
//            String store[] = request.getParameter("id").split("@");
//
//            for (ApplicationModuleBean bean : appMgrOnj.getAllApplicationList()) {
//                if (bean.getApplicationCode().equals(store[0])) {
//                    appBean1 = bean;
//                }
//            }
//
//            if (appBean1 != null) {
//                sessionVarlist.setAppBean(appBean1);
//                try {
//                    if (store[3].equals("E")) {
//                        sessionVarlist.setOperationtype("edit");
//                    }
//                    if (store[3].equals("U")) {
//                        sessionVarlist.setOperationtype("update");
//                    }
//
//                } catch (Exception e) {
//                    out.print("error");
//                }
//            } else {
//                out.print("error");
//            }

            allList = appMgrOnj.getAllApplicationList();

            sysUserOnj = new SystemUserManager();
            statusBean = sysUserOnj.getStatusByUserRole("GENR");
            //request.setAttribute(RequestVarList.SECTION_STATUS_LIST, statusBean);
            request.setAttribute(RequestVarList.SECTION_DETAILS_LIST, allList);
            sessionVarlist.setSecList(statusBean);
            out.print("success");
            
            rd = request.getRequestDispatcher(url);

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);
        } catch (Exception e) {
            out.print("error");
        } finally {
            rd.forward(request, response);
            out.close();
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
