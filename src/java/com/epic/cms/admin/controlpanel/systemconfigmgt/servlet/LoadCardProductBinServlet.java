/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.CardProductMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import java.util.List;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author asela
 */
public class LoadCardProductBinServlet extends HttpServlet {

    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionVarList sessionVarlist;
    private SystemUserManager systemUserManager;
    String cardType = "";
    String cardDomain = "";
    String prodCode = "";
    String cardProductCode = "";
    private CardProductMgtManager cardProductMgtManager;
    CardProductBean bean = null;
    private List<CardProductBean> assignBinList;
    private List<CardProductBean> notAssignBinList;
    private List<CardProductBean> allBinList;
    private String[] notAssignedBin;
    private String[] assignedBin;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            sessionObj = request.getSession(false);
            systemUserManager = new SystemUserManager();
            sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
            String operationType = request.getParameter("operationType");
            String productCode = request.getParameter("productCode");
            String binDes = request.getParameter("binDes");
            String cardProductionModeDes = request.getParameter("cardProductionModeDes");
            String cardKeyDes = request.getParameter("cardKeyDes");
            String assignType = request.getParameter("assignType");

            assignedBin = request.getParameterValues("assignlist");
            notAssignedBin = request.getParameterValues("notassignlist");

            this.setUserInputToBean(request, assignedBin, notAssignedBin);
            request.setAttribute("isBack", "yes");
            request.setAttribute("AssignbinList", assignBinList);
            request.setAttribute("NotAssignbinList", notAssignBinList);

            if (null != sessionVarlist.getCardProductBinBeanLst() && sessionVarlist.getCardProductBinBeanLst().size() > 0) {
                List<CardProductBean> list = new ArrayList<CardProductBean>();
                list.addAll(sessionVarlist.getCardProductBinBeanLst());
                for (CardProductBean cardProductBean : list) {

                    if (cardProductBean.getProductCode().equals(productCode) && cardProductBean.getBinDes().equals(binDes) && cardProductBean.getCardProductionModeDes().equals(cardProductionModeDes) && cardProductBean.getCardKeyDes().equals(cardKeyDes)) {
                        sessionVarlist.getCardProductBinBeanLst().remove(cardProductBean);
                    }
                }
            }

            String remove = "remove";
            if (operationType.equals("add")) {
                request.setAttribute("RemoveOpertation", remove);
                rd = getServletContext().getRequestDispatcher("/LoadCardProductServlet?operationType=" + operationType + "&RemoveOpertation=" + remove + "&assignType=" + assignType);
            } else if (operationType.equals("update")) {
                request.setAttribute("RemoveOpertation", remove);
                rd = getServletContext().getRequestDispatcher("/UpdateCardProductFormServlet?cardProductCode=" + productCode + "&RemoveOpertation=" + remove);
            }

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    public void setUserInputToBean(HttpServletRequest request, String[] assign, String[] notAssign) throws Exception {



        cardProductMgtManager = new CardProductMgtManager();
        allBinList = cardProductMgtManager.getCardProductBins(request.getParameter("cardtype"), request.getParameter("carddomain"));

//        //put the values in the given arrays into lists
//        int k = 0;
//        AssignBinList = new ArrayList<String>();
//        if (assign.length != 0) {
//            while (assign.length > k) {
//
//                AssignBinList.add(assign[k]);
//                k++;
//            }
//        }
//        int l = 0;
//        NotAssignBinList = new ArrayList<String>();
//        while (notAssign.length > l) {
//
//            NotAssignBinList.add(notAssign[l]);
//            l++;
//        }

        if (null != assign) {
            int k = 0;
            assignBinList = new ArrayList<CardProductBean>();
            if (assign.length != 0) {
                while (assign.length > k) {
                    bean = new CardProductBean();
                    bean.setBin(assign[k]);
                    for (int i = 0; i < allBinList.size(); i++) {
                        if (bean.getBin().equals(allBinList.get(i).getBin())) {
                            bean.setBinDes(allBinList.get(i).getBinDes());
                        }
                    }
                    assignBinList.add(bean);
                    k++;
                }
            }
        }
        if (null != notAssign) {
            int l = 0;
            notAssignBinList = new ArrayList<CardProductBean>();
            while (notAssign.length > l) {
                bean = new CardProductBean();
                bean.setBin(notAssign[l]);
                for (int i = 0; i < allBinList.size(); i++) {
                    if (bean.getBin().equals(allBinList.get(i).getBin())) {
                        bean.setBinDes(allBinList.get(i).getBinDes());
                    }
                }
                notAssignBinList.add(bean);
                l++;
            }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoadCardProductBinServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoadCardProductBinServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
