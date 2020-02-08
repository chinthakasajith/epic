package com.epic.cms.switchcontrol.keymgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.switchcontrol.keymgt.bean.ListnerKeyMailBean;
import com.epic.cms.switchcontrol.keymgt.businesslogic.ListnerKeyMailingManager;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.socket.ConnectionToSecurityModule;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jeevan
 */
public class GenerateZMKServlet extends HttpServlet {

    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean;
    private SessionUser sessionUser = null;
    private List<String> userTaskList;
    private String url = "/switch/keymanagement/listnerkeymailingview.jsp";
    private RequestDispatcher rd;
    private List<StatusBean> statusList;
    private String errorMessage = null;
    private List<ListnerKeyMailBean> listnerList;
    private ListnerKeyMailBean listenerBean = null;
    private ListnerKeyMailingManager listnerManager;
    private ListnerKeyMailBean inputBean = null;
    private ConnectionToSecurityModule connectionToSM = null;
    private ListnerKeyMailBean keyBean = null;
    private String responseFormSM = null;
    String LISTENER_NAME = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sessionObj = request.getSession(false);
        try {

            sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
            sessionUser = sessionVarlist.getCMSSessionUser();

            String commKeyId = request.getParameter("comkeyid");

            //this.setUserInputToBean(request);
            String description = commKeyId;
            System.out.println("Description****** " + description);
//            request.setAttribute("comkeyid", commKeyId);
            try {
                LISTENER_NAME = this.getListernerName();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            String requestToSM = StatusVarList.KEY_MAILING_PREFIX + "LISTNER - " + LISTENER_NAME;

            try {
                connectionToSM = new ConnectionToSecurityModule();
                keyBean = new ListnerKeyMailBean();
                responseFormSM = connectionToSM.getServerMsg(requestToSM);

                if (responseFormSM != null) {
                    String zmk = responseFormSM.substring(4, 36);
                    String zmkKvc = responseFormSM.substring(36, 42);

                    String value = responseFormSM.substring(2, 4);
                    if (value.equals("00")) {
                        keyBean.setZmk(zmk);
                        keyBean.setZmk_kvc(zmkKvc);

                        this.setAuditTraceValue(request);
                        this.updateZMKKey(keyBean);

                        out.print("ZMKSuccess," + zmk + "," + zmkKvc);

                    }
                }

            } catch (Exception e) {
                out.print(MessageVarList.COMUNICATION_ERROR);
            }


        } finally {
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

    private boolean updateZMKKey(ListnerKeyMailBean keyBean) throws SQLException, Exception {
        boolean isSuccess = false;
        try {

            listnerManager = new ListnerKeyMailingManager();
            keyBean.setLastUpdatedUser(sessionUser.getUserName());

            isSuccess = listnerManager.updateZMKKey(systemAuditBean, keyBean);

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        }
        return isSuccess;
    }

    private void setUserInputToBean(HttpServletRequest request) {
        inputBean = new ListnerKeyMailBean();

        inputBean.setListnerTypeDesc(request.getParameter("listnername"));
        inputBean.setListnerTypeCode(request.getParameter("listnerType"));
        inputBean.setComKeyId(request.getParameter("comkeyType"));
    }

    private String getListernerName() throws SQLException, Exception {
        String listernerName = null;
        try {

            listnerManager = new ListnerKeyMailingManager();
            listernerName = listnerManager.getListernerName();

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        }
        return listernerName;
    }

    private void setAuditTraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("comkeyid");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Generated listener key mailing. communication key id: " + uniqueId + "; by:  " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.SWITCH_CONTROL_PANEL);
            systemAuditBean.setSectionCode(SectionVarList.KEY_MAMAGEMENT);
            systemAuditBean.setPageCode(PageVarList.LISTNER_KEY_MAILING);
//            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue("");
            systemAuditBean.setNewValue("");

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }
}
