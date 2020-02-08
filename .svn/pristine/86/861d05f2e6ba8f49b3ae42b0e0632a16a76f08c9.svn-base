/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ExChangeRateBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.ExchangeRateManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.RequestVarList;
import java.io.IOException;
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
 * @author ayesh
 */
public class UpdateExchangeRateViewServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SessionVarList sessionVarlist = null;
    private SessionUser sessionUser = null;
    private String url = "/administrator/controlpanel/transactionMgt/exchangeratemgthome.jsp";
    private SystemUserManager systemUserManager = null;
    private List<CurrencyBean> currencyList = null;
    private List<ExChangeRateBean> ecList = null;

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
        int from = -1, to = -1;
        float rate = 0f;
        String falg = "N";
        ExChangeRateBean exBean = null;
        ExChangeRateBean inBean = null;
        try {
            try {
                request.setAttribute("operationtype", "update");
                HttpSession sessionObj = request.getSession(false);
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
            currencyList = ExchangeRateManager.getExchangeRateManager().getAllCurrencyDetails();
            sessionVarlist.setExchangeCurrencyList(currencyList);
            ecList = ExchangeRateManager.getExchangeRateManager().getAllExchangeRateDetails();

            String getPara = request.getParameter("id");

            String store[] = getPara.split("@");
            try {
                from = Integer.parseInt(store[0]);
                to = Integer.parseInt(store[1]);
                rate = Float.parseFloat(store[2]);
            } catch (Exception e) {
            }


            for (ExChangeRateBean bean : ExchangeRateManager.getExchangeRateManager().getAllExchangeRateDetails()) {
                if (bean.getFromCurrency() == from && bean.getToCurrency() == to) {
                    exBean = bean;
                }
                if (bean.getFromCurrency() == to && bean.getToCurrency() == from) {
                    inBean = bean;
                }
            }

            if (exBean != null) {
                request.setAttribute("exBean", exBean);
                ExChangeRateBean bean = new ExChangeRateBean();
                bean.setFromCurrency(from);
                bean.setToCurrency(to);
                bean.setRate(String.valueOf(rate));
                sessionVarlist.setExChangeRateBean(bean);

            } else {
                request.setAttribute("operationtype", "add");
            }

            if (inBean == null) {
                falg = "Y";
            } else {
                falg = "N";
            }
            request.setAttribute("flag", falg);

            rd = request.getRequestDispatcher(url);
        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            request.setAttribute(RequestVarList.EXCHANGE_CURRENCY, currencyList);
            request.setAttribute(RequestVarList.EXCHANGE_DETAILS, ecList);
            rd.forward(request, response);
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
