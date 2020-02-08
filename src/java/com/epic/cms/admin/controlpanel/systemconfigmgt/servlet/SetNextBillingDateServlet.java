/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.BillingCycleMgtManager;
import com.epic.cms.system.util.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author nalin
 */
public class SetNextBillingDateServlet extends HttpServlet {

    private BillingCycleMgtManager billCycleMgr;

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

            try {

                String billDate = request.getParameter("billDate");
                String holidayAct = request.getParameter("holidayAct");

                String nextbillingDate = null;

                JSONObject jCombo = new JSONObject();
                // if there are several combo box to load
                //load combo box data
                //JSONObject jObject = new JSONObject();


                if (Integer.parseInt(billDate) > Integer.parseInt(this.getDateDigit())) {
                    // if billing date will be this month

                    nextbillingDate = this.createThisMonthBillingDate(billDate);

                    if (holidayAct != null) {
                        while (this.isHoliday(nextbillingDate)) {

                            if (holidayAct.equals("0")) {
                                nextbillingDate = this.decrementDate(nextbillingDate);
                            } else if (holidayAct.equals("2")) {
                                nextbillingDate = this.incrementDate(nextbillingDate);
                            } else {
                                break;
                            }
                        }
                    }

                } else {
                    // if billing date will be next month

                    nextbillingDate = this.createNextMonthBillingDate(billDate);

                    if (holidayAct != null) {
                        while (this.isHoliday(nextbillingDate)) {

                            if (holidayAct.equals("0")) {
                                nextbillingDate = this.decrementDate(nextbillingDate);
                            } else if (holidayAct.equals("2")) {
                                nextbillingDate = this.incrementDate(nextbillingDate);
                            } else {
                                break;
                            }
                        }
                    }
                }

                jCombo.put("combo1", nextbillingDate);
                out.print(jCombo);
            } catch (Exception ex) {
            }

        } finally {
            out.close();
        }
    }

    private String getSysDate() throws Exception {
        String currentDate = null;
        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //get current date time with Date()
            Date date = new Date();

            currentDate = dateFormat.format(date);

        } catch (Exception ex) {
            throw ex;
        }
        return currentDate;
    }

    /**
     * This method gets date digit of the current date
     * @return
     * @throws Exception 
     */
    private String getDateDigit() throws Exception {
        String dateDigit = null;
        try {

            DateFormat dateFormat = new SimpleDateFormat("dd");
            Date date = new Date();

            dateDigit = dateFormat.format(date);
        } catch (Exception ex) {
            throw ex;
        }
        return dateDigit;
    }

    /**
     * This method create billing date, if exist current month
     * @param nxtDate
     * @return
     * @throws Exception 
     */
    private String createThisMonthBillingDate(String nxtDate) throws Exception {
        String nxtBillingDate = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            Date date = new Date();

            nxtBillingDate = dateFormat.format(date) + "-" + StringUtils.leftPad(nxtDate, 2, '0');

        } catch (Exception ex) {
            throw ex;
        }
        return nxtBillingDate;
    }

    /**
     *  This method create billing date, if exist next month
     * @param nxtdate
     * @return
     * @throws Exception 
     */
    private String createNextMonthBillingDate(String nxtdate) throws Exception {
        String nxtBillingDate = null;
        try {
            DateFormat ymFormat = new SimpleDateFormat("yyyy-MM");
            Date date = new Date();

            Calendar c = Calendar.getInstance();
            c.setTime(ymFormat.parse(ymFormat.format(date)));
            c.add(Calendar.MONTH, 1);
            nxtBillingDate = ymFormat.format(c.getTime()) + "-" + StringUtils.leftPad(nxtdate, 2, '0');

        } catch (Exception ex) {
            throw ex;
        }
        return nxtBillingDate;
    }

    /**
     * This method check given date is holiday
     * @return
     * @throws Exception 
     */
    private boolean isHoliday(String nextbillingDate) throws Exception {
        boolean isHoliday = false;
        try {
            billCycleMgr = new BillingCycleMgtManager();
            isHoliday = billCycleMgr.isHoliday(nextbillingDate);

        } catch (Exception ex) {
            throw ex;
        }
        return isHoliday;
    }

    /**
     * This method increment a one day
     * @param nextbillingDate
     * @return
     * @throws Exception 
     */
    private String incrementDate(String nxtDate) throws Exception {
        String nxtBillingDate = null;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(nxtDate));
            c.add(Calendar.DATE, 1);  // number of days to add
            nxtBillingDate = sdf.format(c.getTime());


        } catch (Exception ex) {
            throw ex;
        }
        return nxtBillingDate;
    }

    /**
     * This method decrement a one day
     * @param nextbillingDate
     * @return
     * @throws Exception 
     */
    private String decrementDate(String nxtDate) throws Exception {
        String nxtBillingDate = null;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(nxtDate));
            c.add(Calendar.DATE, -1);  // number of days to decrese 
            nxtBillingDate = sdf.format(c.getTime());

        } catch (Exception ex) {
            throw ex;
        }
        return nxtBillingDate;
    }

//    private String getHolidayAction() throws Exception {
//        String holidayAction = null;
//        try {
//
//            billCycleMgr = new BillingCycleMgtManager();
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//        return holidayAction;
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
