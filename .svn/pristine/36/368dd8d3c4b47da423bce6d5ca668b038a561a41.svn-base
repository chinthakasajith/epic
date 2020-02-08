/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.HolidayManagementBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.HolidayManagementManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author upul
 */
public class UpdateSingleHolidayFromFormServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //comman for all pages
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean = null;
    private SessionUser sessionUser = null;
    private RequestDispatcher rd = null;
    private SystemUserManager systemUserManager = null;
    private String successUrl = "/LoadHolidayManagementServlet";
    //////////
    private HolidayManagementBean holidayManagementBean = null;
    private HolidayManagementManager holidayManagementManager = null;
    private String errorMessage = null;
    private List<HolidayManagementBean> hmbs;
    private String oldValue;
    private String newValue;
    List<HolidayManagementBean> holidayList = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //call to existing session
            /////////////////////////////////////////////////////////////////////
            HttpSession sessionObj = request.getSession(false);
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

            ///////////////////////////////////////////////////////

            request.setAttribute("operationtype", "selecteddate");

            this.getHolidayListForMonth();
            request.setAttribute("holidayList", hmbs);


            //set values to bean
            this.setholidayValuesToBean(request);
            
            this.getholidayListForYearMonthDate(holidayManagementBean);
            
            if(null != holidayList && !holidayList.isEmpty()){
                oldValue = holidayManagementBean.getHolidayDate() + "|" + holidayList.get(0).getHolidayReason();
            }
            
            //set valuest to audit bean
            this.setAudittraceValue(request);

            //check whethre userrole have an access for this page and task

            String taskCode = TaskVarList.UPDATE;
            if (this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), taskCode)) {

                if (this.validateholidayValues(holidayManagementBean)) {

                    if (this.updateHoliday(holidayManagementBean)) {

                        this.getHolidayListForMonth();
                        request.setAttribute("holidayList", hmbs);
                        request.setAttribute("tstVal", "val");
                        request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.UPDATE_SUCCESS_HOLIDAY);
                        //response.sendRedirect(request.getContextPath() + "/controlpanel/systemusermgt/viewsectionpage.jsp");
                        rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemconfigmgt/holidaymanagementhome.jsp");
                        rd.forward(request, response);


                    } else {

                        request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UPDATE_ERROR_HOLIDAY);
                        //response.sendRedirect(request.getContextPath() + "/controlpanel/systemusermgt/viewsectionpage.jsp");
                        rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemconfigmgt/holidaymanagementhome.jsp");
                        rd.forward(request, response);



                    }


                } else {

                    request.setAttribute(MessageVarList.JSP_ERROR, errorMessage);
                    //response.sendRedirect(request.getContextPath() + "/controlpanel/systemusermgt/viewsectionpage.jsp");
                    rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemconfigmgt/holidaymanagementhome.jsp");
                    rd.forward(request, response);

                }

            } else {

                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }



        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.toString()));
            //response.sendRedirect(request.getContextPath() + "/controlpanel/systemusermgt/viewsectionpage.jsp");
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemconfigmgt/holidaymanagementhome.jsp");
            rd.forward(request, response);

            //////////////////////////////////////////////////////////////////
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp");
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            String errMessage = MessageVarList.ACCESS_DENIED_PAGETASK;
            request.setAttribute(MessageVarList.JSP_ERROR, errMessage);
            rd = getServletContext().getRequestDispatcher("/ManageApplicationPageFormServlet");
            rd.include(request, response);


        } catch (Exception ex) {
        } finally {
            out.close();
        }
    }

    /**
     * setholidayValuesToBean
     * @param request
     * @throws Exception 
     */
    private void setholidayValuesToBean(HttpServletRequest request) throws Exception {
        try {
            boolean isValidate = true;
            String year = null;
            String month = null;
            String day = null;
            holidayManagementBean = new HolidayManagementBean();


            String holidayDay = request.getParameter("holiday");
            if (holidayDay == null || holidayDay.contentEquals("")) {
                isValidate = false;
                errorMessage = MessageVarList.HOLIDAY_DATE_NULL;

            } else {

                year = holidayDay.substring(0, 4);
                month = holidayDay.substring(5, 7);
                day = holidayDay.substring(8, 10);


                //set full date to the ho9iday bean
                holidayManagementBean.setYear(year);
                holidayManagementBean.setMonth(month);
                holidayManagementBean.setDay(day);


                holidayManagementBean.setHolidayDate(year + "-" + month + "-" + day);


            }


            //set reason

            String reason = request.getParameter("description");
            holidayManagementBean.setHolidayReason(reason);


            newValue = holidayManagementBean.getHolidayDate() + "|" + holidayManagementBean.getHolidayReason();







        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * validateholidayValues
     * @param managementBean
     * @return
     * @throws Exception 
     */
    private boolean validateholidayValues(HolidayManagementBean managementBean) throws Exception {
        boolean isValidate = true;
        try {



            if (managementBean.getHolidayDate() == null) {
                isValidate = false;
                errorMessage = MessageVarList.HOLIDAY_DATE_NULL;

            } else if (managementBean.getHolidayDate().contentEquals("") || managementBean.getHolidayDate().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.HOLIDAY_DATE_NULL;


            } else if ((Date.valueOf(managementBean.getHolidayDate())).before(getDate())) {

                isValidate = false;
                errorMessage = MessageVarList.HOLIDAY_DATE_INVALID;

            } else if (managementBean.getHolidayReason().contentEquals("") || managementBean.getHolidayReason().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.HOLIDAY_REASON_NULL;


            } else if (!UserInputValidator.isDescription(managementBean.getHolidayReason())) {

                isValidate = false;
                errorMessage = MessageVarList.HOLIDAY_REASON_INVALID;

            }


        } catch (Exception ex) {
            throw ex;
        }
        return isValidate;

    }

    public boolean updateHoliday(HolidayManagementBean managementBean) throws Exception {
        boolean success = false;
        try {

            holidayManagementManager = new HolidayManagementManager();
            success = holidayManagementManager.updateHoliday(managementBean, systemAuditBean);


        } catch (SQLException ex) {
            throw ex;
        }
        return success;
    }

    ////////////////////////////////////////////////////////////////////////////    
    /**
     * isValidTaskByUser
     * @param userTaskList
     * @param task
     * @return
     * @throws Exception 
     */
    private boolean isValidTaskByUser(List<String> userTaskList, String task) throws Exception {
        boolean isValidTask = false;
        try {

            for (String usertask : userTaskList) {

                if (task.equals(usertask)) {
                    isValidTask = true;
                }


            }
            return isValidTask;



        } catch (Exception ex) {
            throw ex;
        }



    }
    ////////////////////////////////////////////////////////////////////////////   

    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = holidayManagementBean.getHolidayDate();

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Holiday Managment. Date :" + uniqueId + " ; by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.HOLIDAYMGT);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue(oldValue);
            //set new value of change if required
            systemAuditBean.setNewValue(newValue);


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());





        } catch (Exception ex) {
            throw ex;
        }



    }

    /**
     * getHolidayListForMonth
     * @throws Exception 
     */
    private void getHolidayListForMonth() throws Exception {


        try {

            hmbs = new ArrayList<HolidayManagementBean>();
            HolidayManagementBean bean = new HolidayManagementBean();
            Calendar ca = new GregorianCalendar();

            //get year and motnh from calender
            int iTYear = ca.get(Calendar.YEAR);
            int iTMonth = ca.get(Calendar.MONTH);
            String realMonth = null;

            //month have to add 1 for get  real month and have to zero fill for fisrt 9 months
            iTMonth = iTMonth + 1;
            if (iTMonth <= 9) {
                realMonth = "0" + Integer.toString(iTMonth);
                bean.setMonth(realMonth);
            } else {
                bean.setMonth(Integer.toString(iTMonth));
            }



            bean.setYear(Integer.toString(iTYear));

            System.out.println("------------" + bean.getMonth());
            System.out.println("------------" + bean.getYear());

            holidayManagementManager = new HolidayManagementManager();
            hmbs = holidayManagementManager.getholidayListForMonth(bean);



        } catch (Exception ex) {
            throw ex;
        }


    }

    public static Date getDate() {
        java.util.Date today = new java.util.Date();
        java.sql.Date currentDay = new java.sql.Date(today.getTime());

        return currentDay;
    }

    public void getholidayListForYearMonthDate(HolidayManagementBean managementBean) throws Exception {

        try {

            holidayManagementManager = new HolidayManagementManager();
            holidayList = holidayManagementManager.getholidayListForYearMonthDate(managementBean);


        } catch (SQLException ex) {
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
