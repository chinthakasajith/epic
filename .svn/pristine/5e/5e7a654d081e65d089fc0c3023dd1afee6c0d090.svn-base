/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.assigndata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.SearchAssignAppBean;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
import com.epic.cms.system.util.comparator.MapValueSort;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chanuka
 */
public class LoadUpdateAssignDataFormServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private ApplicationAssignManager appAssignManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private ApplicationAssignBean assignAppBean;
    private List<String> usersList = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> branchesDeatilsList = null;
    private List<ApplicationAssignBean> searchList = null;
    private HashMap<String, String> identificationList = new LinkedHashMap<String, String>();
    private HashMap<String,String> userMap=null;
    private String url = "/camm/assigningdata/appassignhome.jsp";
    private String errorMessage = null;
    private String categoryCode=null;

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


            request.setAttribute("operationtype", "update");
            String applicationId = request.getParameter("applicationid").trim();

            this.getCardCategoryByApplicationId(applicationId);
            this.loadCardCaptureUserListToApplicationType(categoryCode);
            this.getAllPriorityLevelList();
            this.getAllBranchesDetailsList();

           // Collections.sort(usersList);

            SortedMap sortedData = new TreeMap(new MapValueSort.ValueComparer(branchesDeatilsList));
            sortedData.putAll(branchesDeatilsList);

            this.searchAssignApplication(sessionVarlist.getSearchAssignAppBean());
//            this.getAllApplicationDoamin();

            request.setAttribute("searchList", searchList);
            request.setAttribute("usersList", userMap);
            request.setAttribute("priorityLevelList", priorityLevelList);
            request.setAttribute("branchesDeatilsList", sortedData);
//            request.setAttribute("applicationDomainList", applicationDomainList);


            for (ApplicationAssignBean assignBean : searchList) {
                if (assignBean.getApplicationId().equals(applicationId)) {
                    assignAppBean = assignBean;
                }
            }

            if (assignAppBean != null) {
                request.setAttribute("assignappbean", assignAppBean);
                String oldValue = assignAppBean.getApplicationDomain() + "|" + assignAppBean.getIdentityType() + "|" + assignAppBean.getIdentityNo() + "|" + assignAppBean.getPriorityLevel()+"|" + assignAppBean.getReferralEmpNo()+"|" + assignAppBean.getReferralBranchId()+"|" + assignAppBean.getAssignUser();
                this.getAllIdentificationType(assignAppBean.getCardCategory());

                request.setAttribute("oldval", oldValue);
                rd = getServletContext().getRequestDispatcher(url);
            } else {
                rd = getServletContext().getRequestDispatcher(url);
            }
            request.setAttribute("identificationList", identificationList);


        } //catch session exception
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

        } catch (SQLException ex) {
            request.setAttribute("operationtype", "update");
            errorMessage = OracleMessage.getMessege(ex.getMessage());
            request.setAttribute("errorMsg", errorMessage);
            rd = getServletContext().getRequestDispatcher(url);

        } catch (Exception ex) {
            request.setAttribute("operationtype", "update");
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_ASSIGN_APP);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void loadCardCaptureUserListToApplicationType(String cat) throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            userMap = appAssignManager.getAssignUserList(PageVarList.USERDATACAPTURE,cat);
          
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllPriorityLevelList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            priorityLevelList = appAssignManager.getAllPriorityLevels();

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllBranchesDetailsList() throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            branchesDeatilsList = appAssignManager.getAllBranchesDetails();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllIdentificationType(String cardCategory) throws Exception {
        try {
            appAssignManager = new ApplicationAssignManager();
            identificationList = appAssignManager.getIdentificationTypeByCardCategory(cardCategory,StatusVarList.ID_VERIFICATION_CATEGORY);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private void searchAssignApplication(SearchAssignAppBean searchBean) throws Exception {

        try {

            appAssignManager = new ApplicationAssignManager();
            searchList = appAssignManager.getAllSearchList(searchBean);

        } catch (Exception ex) {
            throw ex;
        }
    }
    //get card catogory to application id
    private void getCardCategoryByApplicationId(String id) throws Exception{
        try {
            appAssignManager = new ApplicationAssignManager();
            categoryCode=appAssignManager.getCardCategoryByApplicationId(id);
            
        } catch (Exception ex) {
            throw ex;
        }
    }

//    private void getAllApplicationDoamin() throws Exception {
//        try {
//            appAssignManager = new ApplicationAssignManager();
//            applicationDomainList = appAssignManager.getAllApplicationDoamin();
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
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
