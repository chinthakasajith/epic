/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.numbergenaration.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardTypeMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardBinBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardNumberFormateBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.NumberGenarationDataBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.businesslogic.NumberGenarationManager;
import com.epic.cms.camm.applicationproccessing.assigndata.businesslogic.ApplicationAssignManager;
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
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author janaka_h
 */
public class LoadNumberGenarationHistoryServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CardTypeMgtManager cardTypeManager;
    private NumberGenarationManager manager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private List<CardNumberFormateBean> numberFormateList;
    private List<CardBinBean> binList;
    private String errorMessage = null;
    private HashMap<String, String> priorityLevelList = null;
    private List<CardTypeMgtBean> cardTypeLst;
    //private SearchUserAssignAppBean searchBean = null;
    private List<NumberGenarationDataBean> searchList = null;
    private String url = "/camm/numbergenaration/numbergenarationhome.jsp";
    private String url2 = "/camm/numbergenaration/numbergenarationhistory.jsp";
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
                String pageCode = PageVarList.NUMBERGEN;
                String taskCode = TaskVarList.SEARCH;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {

                    String idArray[] = null;
                    String idList = "";
                    idList = request.getParameter("hiddenarray");

                    if (!idList.equals("")) {
                        idArray = idList.split(",");

                    } else {
                        
                        searchList = sessionVarlist.getNumberGenaSearchLst();
                        idArray = new String[searchList.size()];
                        for (int j = 0; j < searchList.size(); j++) {
                            idArray[j] = searchList.get(j).getApplicationId();
                        }
                    }
                    //System.out.println(idArray[0]+">>>>>>"+idArray[1]);
                    
                    
                    if(idArray!=null){
                        
                        sessionVarlist.setIdArray(idArray);
                        this.getNumberFormatesByCardType(sessionVarlist.getNumberGenaSearchBean().getCardType());
                        this.getBinNumberByCardType(sessionVarlist.getNumberGenaSearchBean().getCardType());
                        this.getAllCardTypeList();
                        for(int k=0;k<cardTypeLst.size();k++){
                            if(sessionVarlist.getNumberGenaSearchBean().getCardType().equals(cardTypeLst.get(k).getCardTypeCode())){
                                request.setAttribute("cardType", cardTypeLst.get(k).getDescription());
                                break;
                            }
                        
                        }
                        request.setAttribute("binList", binList);
                        request.setAttribute("numberFormateLst", numberFormateList);
                        request.setAttribute("numberofIDs", idArray.length);
                        
                        rd = request.getRequestDispatcher(url2);
                        
                        
                    }else{
                    rd = request.getRequestDispatcher(url);
                    }
                    

                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }


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
            ex.printStackTrace();
            request.setAttribute("operationtype", "add");
            request.setAttribute("errorMsg", MessageVarList.ERROR_ADD_ASSIGN_APPLICATION);
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
     
     private void getNumberFormatesByCardType(String cardType) throws Exception {
        try {
            manager = new NumberGenarationManager();
            numberFormateList = manager.getNumberFormatesByCardType(cardType);
        } catch (Exception ex) {
            throw ex;
        }
    }
     
     private void getAllCardTypeList() throws Exception {
        try {

            cardTypeManager = CardTypeMgtManager.getInctance();
            cardTypeLst = cardTypeManager.getAllCardType();
        } catch (Exception ex) {
            throw ex;
        }
    }
     
     private void getBinNumberByCardType(String cardType) throws Exception {
         try {
            manager = new NumberGenarationManager();
            binList = manager.getBinNumberByCardType(cardType);
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
