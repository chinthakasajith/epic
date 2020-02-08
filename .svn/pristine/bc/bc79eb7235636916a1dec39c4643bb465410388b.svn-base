/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AquireBinBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.AquireBinManagementManager;
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
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author badrika
 */
public class UpdateAquireBinManagementServlet extends HttpServlet {

    private HttpSession sessionObj;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private RequestDispatcher rd;
    private Object errorMessage;
    private String url = "/administrator/controlpanel/systemconfigmgt/aquirebinmgt.jsp";
    private AquireBinManagementManager AquireBinManager;
    private List<AquireBinBean> aquireBinBeanList;
    private AquireBinBean aqbean;
    private SystemAuditBean systemAuditBean;

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



            this.getAllAquireBinDetailList();



            request.setAttribute("aquireBinBeanList", aquireBinBeanList);


            try {
                //set page code and task codes
                String pageCode = PageVarList.AQUIREBIN;
                String taskCode = TaskVarList.UPDATE;


                //check whethre userrole have an access for this page and task
                if (!this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();
                }


                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                throw adex;

            }


            this.setUserInputToBean(request);

            if (this.validateUserInput(aqbean)) {

                request.setAttribute("operationtype", "add");
                this.setAudittraceValue(request);
                this.updateAquireBin(aqbean, systemAuditBean);
                request.setAttribute("successMsg", aqbean.getBinNumber() + " " + MessageVarList.BIN_UPDATE_SUCCESS);
                rd = getServletContext().getRequestDispatcher("/LoadAquireBinManagement");

            } else {

                request.setAttribute("operationtype", "update");
                request.setAttribute("aqbean", aqbean);
                request.setAttribute("errorMsg", errorMessage);
                rd = getServletContext().getRequestDispatcher(url);

            }


        }//catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        }//catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/systemconfigmgt/aquirebinmgt.jsp?errorMsg=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);


        } catch (Exception ex) {
            request.setAttribute("errorMsg", "error occurs");
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * to check access is valid
     * @param userrole
     * @param pagecode
     * @param task
     * @return
     * @throws Exception 
     */
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

    /**
     * to get all aquire bin detail list
     * @throws Exception 
     */
    private void getAllAquireBinDetailList() throws Exception {
        try {

            AquireBinManager = new AquireBinManagementManager();
            aquireBinBeanList = AquireBinManager.getAllAquireBinDetailList();

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * to set user input to bean
     * @param request 
     */
    public void setUserInputToBean(HttpServletRequest request) {

        aqbean = new AquireBinBean();

        aqbean.setBinNumber(request.getParameter("binnumber"));
        aqbean.setOwnership(request.getParameter("ownership"));
        //aqbean.setPayType(request.getParameter("paytype"));
        aqbean.setSendChanel(request.getParameter("sendchanel"));
        //aqbean.setReceiveChanel(request.getParameter("receivechanel"));
        //aqbean.setCardType(request.getParameter("cardtype"));
        //aqbean.setEntryMode(request.getParameter("entrymode"));
        aqbean.setCardKey(request.getParameter("cardKey"));
        aqbean.setStatus(request.getParameter("status"));

        aqbean.setLastUpdatedTime(new Date());
        aqbean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());




    }

    /**
     * to validate user input
     * @param aqBean
     * @return
     * @throws Exception 
     */
    public boolean validateUserInput(AquireBinBean aqBean) throws Exception {
        boolean isValidate = true;


        //validate user Role code
        try {

            if (aqBean.getBinNumber().equals("") || aqBean.getBinNumber().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.BIN_NUM_EMPTY;
            } else if (!UserInputValidator.isNumeric(aqBean.getBinNumber()) || aqBean.getBinNumber().length() != 6) {
                isValidate = false;
                errorMessage = MessageVarList.BIN_NUM_INVALID;

            } else if (aqBean.getOwnership() == null) {
                isValidate = false;

                errorMessage = MessageVarList.OWNERSHIP_EMPTY;
//            } else if (aqBean.getPayType() == null) {
//                isValidate = false;
//                errorMessage = MessageVarList.PAY_TYPE_EMPTY;
            } else if (aqBean.getSendChanel().equals("") || aqBean.getSendChanel().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.SENDING_CHANNEL_EMPTY;
            } //            else if (aqBean.getReceiveChanel().equals("") || aqBean.getReceiveChanel().length() <= 0) {
            //                isValidate = false;
            //                errorMessage = MessageVarList.RECEIVING_CHANNEL_EMPTY;
            //            }
            else if (aqBean.getCardKey().equals("") || aqBean.getCardKey().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.KEY_TYPE_EMPTY;
            } //            else if (aqBean.getEntryMode().equals("") || aqBean.getEntryMode().length() <= 0) {
            //                isValidate = false;
            //                errorMessage = MessageVarList.ENTRY_MODE_EMPTY;
            //            }
            else if (aqBean.getStatus().equals("") || aqBean.getStatus().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.STATUS_EMPTY;
            }





        } catch (Exception ex) {
            isValidate = false;

        }

        return isValidate;
    }

    /**
     * to set audit trace value
     * @param request
     * @throws Exception 
     */
    private void setAudittraceValue(HttpServletRequest request) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter(aqbean.getBinNumber());

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Acquiring Bin. Bin Number: " + aqbean.getBinNumber() + "; by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.AQUIREBIN);
            systemAuditBean.setTaskCode(TaskVarList.UPDATE);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue("");


            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }



    }

    /**
     * to update existing record
     * @param aqBean
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean updateAquireBin(AquireBinBean aqBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            AquireBinManager = new AquireBinManagementManager();
            success = AquireBinManager.updateAquireBin(aqBean, systemAuditBean);
        } catch (Exception ex) {
            throw ex;
        }
        return success;
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
