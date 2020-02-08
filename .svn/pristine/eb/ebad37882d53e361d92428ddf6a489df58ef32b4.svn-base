/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.terminalmgt.terminaldata.servlet;

import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.MerchantSearchBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.businesslogic.TerminalDataCaptureManager;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mahesh_m
 */
public class SearchmerchantServlet extends HttpServlet {

    private RequestDispatcher rd;
    private String url = "/mtmm/terminalmgt/merchantsearch.jsp";
    private TerminalDataCaptureManager terminalManager=null;
    private HashMap<String, String> terminalStatusList = null;
    private String terminalId = null;
    private String merchantName = null;
    private String merchantId = null;
    private String merchantLocation = null;
    private String merchantStatus = null;
    private MerchantSearchBean searchBean = null;
    private TerminalDataCaptureManager merchantManager = null;
    private List<MerchantSearchBean> searchList = null;
    private String errorMessage = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            terminalId = request.getParameter("id");
            merchantName = request.getParameter("merchantName");
            merchantId = request.getParameter("merchantId");
            merchantLocation = request.getParameter("merchantLocation");
            merchantStatus = request.getParameter("merchantStatus");

            this.setUserInputToBean();
            this.getTerminalStatus();

            request.setAttribute("terminalId", terminalId);
            request.setAttribute("terminalStatusList", terminalStatusList);
            request.setAttribute("searchBean", searchBean);
            if (validateUserInput(searchBean)) {

                this.searchMerchants(searchBean);
                request.setAttribute("searchList", searchList);

            } else {
                request.setAttribute("errorMsg", errorMessage);
            }
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception e) {
        } finally {
            out.close();
        }
    }

    private void getTerminalStatus() {
        try {
            terminalManager = new TerminalDataCaptureManager();
            terminalStatusList = terminalManager.getTerminalStatus();
        } catch (Exception e) {
        }

    }

    public void setUserInputToBean() {
        searchBean = new MerchantSearchBean();

        searchBean.setMerchantId(merchantId);
        searchBean.setMerchantname(merchantName);
        searchBean.setMerchantLocation(merchantLocation);
        searchBean.setMerchantStatus(merchantStatus);
    }

    public void searchMerchants(MerchantSearchBean searchBean) throws Exception {

        merchantManager = new TerminalDataCaptureManager();
        searchList = new ArrayList<MerchantSearchBean>();

        try {
            searchList = merchantManager.searchMerchants(searchBean);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean validateUserInput(MerchantSearchBean searchBean) throws Exception {
        boolean isValidate = true;

        //////validate user inputs

        try {

            if (!UserInputValidator.isDescription(searchBean.getMerchantname())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!UserInputValidator.isAlphaNumeric(searchBean.getMerchantId())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            } else if (!UserInputValidator.isDescription(searchBean.getMerchantLocation())) {
                isValidate = false;

                errorMessage = MessageVarList.INVALID_SEARCH_LETTERS;

            }
        } catch (Exception ex) {
            isValidate = false;
            throw ex;
        }
        return isValidate;
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
