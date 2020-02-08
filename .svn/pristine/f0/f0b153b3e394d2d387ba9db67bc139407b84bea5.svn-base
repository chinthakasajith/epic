/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.templatemgt.lettertemplate.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.templatemgt.lettertamplate.bean.LetterBean;
import com.epic.cms.templatemgt.lettertamplate.bean.LetterFieldBean;
import com.epic.cms.templatemgt.lettertemplate.businesslogic.LetterConfMgtManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sajith_g
 */
public class LoadUpdateLetterConfFormServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private LetterConfMgtManager domainManager = null;
    private List<LetterBean> searchList = null;
    private LetterBean letterBean;
    private List<LetterFieldBean> letterFieldList = null;
    private List<LetterFieldBean> selectedletterfieldList = null;
    private String url = "/administrator/templatemgt/lettertemplate/letterupdate.jsp";

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

            String templateCode = request.getParameter("templatecode").trim();
            request.setAttribute("selectedtab", "0");
            //set letter field set
            domainManager = new LetterConfMgtManager();
            letterFieldList = domainManager.getLetterFieldDetailsList();
            selectedletterfieldList = new ArrayList<LetterFieldBean>();

            this.searchLetterConfMgt();

            for (LetterBean DomainBean : searchList) {
                if (DomainBean.getTemplateCode().equals(templateCode)) {
                    letterBean = DomainBean;
                }
            }

            if (letterBean != null) {
                
                //filtering selected parameter names
                this.setLetterParameters(letterBean.getBody());

                String oldValue = letterBean.getTemplateCode() + "|" + letterBean.getStatus() + "|" + letterBean.getSubject() + "|";
                request.setAttribute("oldValue", oldValue);
                request.setAttribute("letterBean", letterBean);
                request.setAttribute("letterFieldList", letterFieldList);
                request.setAttribute("selectedletterfieldList", selectedletterfieldList);
                rd = getServletContext().getRequestDispatcher(url);
            } else {
                request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_LETTERCONF);
                request.setAttribute("letterFieldList", letterFieldList);
                rd = getServletContext().getRequestDispatcher(url);
            }

        } //catch session exception //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } catch (Exception ex) {
            request.setAttribute("errorMsg", MessageVarList.ERROR_LOAD_LETTERCONF);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    private void searchLetterConfMgt() throws Exception {

        try {

            domainManager = new LetterConfMgtManager();
            searchList = domainManager.getLetterConfs();

        } catch (Exception ex) {
            throw ex;

        }
    }

    //filtering selected parameter names
    private void setLetterParameters(String stringArrayList) {
        
        String[] stringArray = stringArrayList.split("\\|");
        for (int i = 0; i < stringArray.length; i++) {
            String string = stringArray[i];
            for (int j = 0; j < letterFieldList.size(); j++) {
                LetterFieldBean letterFieldBean = letterFieldList.get(j);
                if (string != null && string.equals(letterFieldBean.getFieldName())) {
                    selectedletterfieldList.add(letterFieldBean);
                    letterFieldList.remove(letterFieldBean);
                }

            }
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

}
