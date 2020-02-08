/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.keymgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.switchcontrol.keymgt.bean.ChannelKeyMailingBean;
import com.epic.cms.switchcontrol.keymgt.businesslogic.KeyManagementManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
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
 * @author asitha_l
 */
public class SearchChannelKeyMailingServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    HttpSession sessionObj = null;
    private SessionUser sessionUser = null;
    private List<String> userTaskList = null;
    private String errorMessage = null;
    private HashMap<String, String> channelTypes;
    private HashMap<String, String> communicationKeys;
    private KeyManagementManager keyMgtmanager;
    private String url = "/switch/keymanagement/channelkeymailinghome.jsp";
    private ChannelKeyMailingBean channelKeyMailingBean;
    private List<ChannelKeyMailingBean> searchResultList = null;

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
            try {
                //set page code and task codes
                String pageCode = PageVarList.CHANNEL_KEY_MAILING;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    this.getChannelTypes();
                    this.getCommunicationKeys();
                    request.setAttribute("channelTypes", channelTypes);
                    request.setAttribute("communicationKeys", communicationKeys);

                    this.setUserInputToBean(request);

                    request.setAttribute("channelKeyMailingBean", channelKeyMailingBean);

                    if (this.validateUserInput(channelKeyMailingBean, request)) {

                        this.searchChannelKeyMailing(channelKeyMailingBean);

                        request.setAttribute("searchResultList", searchResultList);

                    } else {
                        request.setAttribute("errorMsg", errorMessage);
                    }
                    rd = getServletContext().getRequestDispatcher(url);

                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }

                sessionVarlist.setUserPageTaskList(userTaskList);

            } catch (AccessDeniedException adex) {
                throw adex;

            }

        }//catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);


        } catch (Exception ex) {

            request.setAttribute("errorMsg", MessageVarList.ERROR_SEARCH_KEY_MAILER);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
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

    private void getChannelTypes() throws SQLException, Exception {
        channelTypes = new HashMap<String, String>();
        keyMgtmanager = new KeyManagementManager();
        channelTypes = keyMgtmanager.getChannelTypes();
    }

    private void getCommunicationKeys() throws SQLException, Exception {
        communicationKeys = new HashMap<String, String>();
        keyMgtmanager = new KeyManagementManager();
        communicationKeys = keyMgtmanager.getCommunicationKeys();
    }

    private void setUserInputToBean(HttpServletRequest request) {
        channelKeyMailingBean = new ChannelKeyMailingBean();

        channelKeyMailingBean.setChannelID(request.getParameter("channelID"));
        channelKeyMailingBean.setChannelName(request.getParameter("channelName"));
        channelKeyMailingBean.setChannelTypeCode(request.getParameter("channelType"));
        channelKeyMailingBean.setCommunicationKeyCode(request.getParameter("communicationKey"));
    }

    public boolean validateUserInput(ChannelKeyMailingBean bean, HttpServletRequest request) throws Exception {
        boolean isValid = true;

        try {
            if (!UserInputValidator.isAlphaNumeric(bean.getChannelID())) {
                isValid = false;
                errorMessage = MessageVarList.INVALID_CHANNEL_ID;
            }else if(!UserInputValidator.isAlphaNumeric(bean.getChannelName())) {
                isValid = false;
                errorMessage = MessageVarList.INVALID_CHANNEL_NAME;
            }
        } catch (Exception ex) {
            isValid = false;
        }
        return isValid;
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

    private void searchChannelKeyMailing(ChannelKeyMailingBean channelKeyMailingBean) throws SQLException, Exception {
        
        keyMgtmanager = new KeyManagementManager();
        searchResultList = keyMgtmanager.searchChannelKeyMailing(channelKeyMailingBean);
        
    }
}
