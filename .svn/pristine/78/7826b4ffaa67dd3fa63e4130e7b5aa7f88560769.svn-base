package com.epic.cms.backoffice.eodlettergeneration.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BankBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.BankMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.CardRenewalBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterDetailsBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterGenerationBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterTemplateBean;
import com.epic.cms.backoffice.eodlettergeneration.businesslogic.SearchLetterGenerationManager;
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
import java.sql.Date;
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
 *
 * @author jeevan
 */
public class SearchLetterGenerationServlet extends HttpServlet {

    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private List<String> userTaskList;
    private String url = "/backoffice/lettergeneration/eodlettergenerationhome.jsp";
    private RequestDispatcher rd;
    private BankMgtManager bankMgtManager;
    private List<BankBean> banklist;
    private List<StatusBean> statusList = null;
    private SearchLetterGenerationManager letterMgr;
//    private List<LetterGenerationBean> processList;
//    private CardRenewalBean searchParamBean = null;
//    private List<CardRenewalBean> searchResultList = null;

    private List<LetterTemplateBean> letterTmpList = null;
    private LetterDetailsBean searchParamBean = null;
    private List<LetterDetailsBean> searchResultList = null;
    private String errorMessage = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            HttpSession sessionObj = request.getSession(false);
            try {
                request.setAttribute("operationtype", "search");
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

            this.getAllLetterTemplates();
            request.setAttribute("letterTmpList", letterTmpList);

            try {
                String pageCode = PageVarList.LETTER_GEN;
                String taskCode = TaskVarList.SEARCH;

                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    this.setUserInputToBean(request);
                    request.setAttribute("letterBean", searchParamBean);
                    sessionVarlist.setSearchedLettersBean(searchParamBean);

                    this.searchLettersToGeneration(searchParamBean);
                    request.setAttribute("searchList", searchResultList);

                    rd = getServletContext().getRequestDispatcher(url);

                } else {
                    throw new AccessDeniedException();
                }

            } catch (AccessDeniedException adex) {
                throw adex;
            }

        }//catch session exception
        catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.ERROR_LETTER_GEN);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
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
    //get letter templates list
    private void getAllLetterTemplates() throws Exception {
        try {
            letterMgr = new SearchLetterGenerationManager();
            letterTmpList = letterMgr.getAllLetterTemplates();
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private void setUserInputToBean(HttpServletRequest request) throws SQLException, Exception {
        try {

            searchParamBean = new LetterDetailsBean();

            searchParamBean.setTmpCode(request.getParameter("letterCategory"));
            searchParamBean.setFromDate(request.getParameter("fromdate"));
            searchParamBean.setToDate(request.getParameter("todate"));
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private void searchLettersToGeneration(LetterDetailsBean searchBean) throws Exception{
        try {
            letterMgr=new SearchLetterGenerationManager();
            searchResultList=letterMgr.searchLettersToGenerate(searchBean);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
