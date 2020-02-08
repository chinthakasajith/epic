package com.epic.cms.camm.applicationconfirmation.servlet;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.camm.applicationconfirmation.businesslogic.DebitApplicationConfirmationManager;
import com.epic.cms.system.util.json.JSONException;
import com.epic.cms.system.util.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author jeevan
 */
public class ChangeProductionModeServlet extends HttpServlet {

    private DebitApplicationConfirmationManager manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");
         PrintWriter out = response.getWriter();
        try {

            String binProfile = request.getParameter("binProfile_");
            String productCode = request.getParameter("productCode_");

         List<CardBinBean> productionModeList = new ArrayList<CardBinBean>();
            List<CardBinBean> productionModeList1 = new ArrayList<CardBinBean>();
           
           
            List<CardBinBean> cardKeyList = new ArrayList<CardBinBean>();
           
            try {
                manager = new DebitApplicationConfirmationManager();
                JSONObject jCombo = new JSONObject();// if there are several combo box to load
                //load combo box data
                JSONObject jObject = new JSONObject();
             //   cardBinList = confirmationManager.getBinList(cardProductVal);
                cardKeyList = manager.getCardKeyList(productCode, binProfile);
               
                if(null != cardKeyList && cardKeyList.size() > 0 ){
                    for (CardBinBean cardBinBean : cardKeyList) {
                        productionModeList = manager.getProductionModeList(cardBinBean.getCardKey());
                        if(null != productionModeList && productionModeList.size() > 0 ){
                        productionModeList1.addAll(productionModeList);
                        }
                    }
                }
               
                 //    jObject.put("", "-------------SELECT-------------");
                for (int i = 0; i < productionModeList1.size(); i++) {
                    jObject.put(productionModeList1.get(i).getProductionMode(), productionModeList1.get(i).getProductionModeDes());
                }

                jCombo.put("combo1", jObject);
                out.print(jCombo);
            } catch (Exception e) {
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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(LoadBinProfilesServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (JSONException ex) {
            Logger.getLogger(LoadBinProfilesServlet.class.getName()).log(Level.SEVERE, null, ex);
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
