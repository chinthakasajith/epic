/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.accounttemplate.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.admin.templatemgt.accounttemplate.businesslogic.AccountTemplateManager;
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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author janaka_h
 */
public class UpdateAccountTempalteServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String oldValue = "";
    String newValue = "";
    HttpSession sessionObj;
    private RequestDispatcher rd;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private SessionVarList sessionVarlist;
    private SystemAuditBean systemAuditBean;
    private SystemUserManager systemUserManager;
    private AccountTemplateManager templateManager;
    private HashMap<String, String> accountRskProf = null;
    private String url = "/LoadAccountTempalteServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        AccountTempBean templateBean = new AccountTempBean();
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

            //set page code and task codes
            String pageCode = PageVarList.ACCTEMPLATE;
            String taskCode = TaskVarList.UPDATE;


            //check whethre userrole have an access for this page and task
            if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {


                templateBean.setTemplateCode(request.getParameter("tempaltcode"));
                templateBean.setTemplateName(request.getParameter("templatename"));
                templateBean.setValiedFrom(request.getParameter("fromdate"));
                templateBean.setValiedTo(request.getParameter("todate"));
                templateBean.setTotalCreditLimit(request.getParameter("creditlimit"));
                templateBean.setStaff(request.getParameter("staff"));
                templateBean.setAccRskProf(request.getParameter("riskProf"));
                templateBean.setCardType(request.getParameter("cardType"));
                templateBean.setCurrencyCode(request.getParameter("currency"));
                templateBean.setCustomerTemplateCode(request.getParameter("cusTempCode"));
                templateBean.setStatus(request.getParameter("status"));
                templateBean.setInterestprofileCode(request.getParameter("interestProf"));
                templateBean.setBillCycle(request.getParameter("bill"));
                templateBean.setStatementProf(request.getParameter("statement"));
                templateBean.setLastUpdateduser(sessionUser.getUserName());
                templateBean.setTxnProfCode(request.getParameter("txnProfile"));

                oldValue = request.getParameter("oldValue");
                newValue = templateBean.getTemplateCode() + "|" + templateBean.getTemplateName() + "|"
                        + "" + templateBean.getValiedFrom() + "|" + templateBean.getValiedTo() + "|"
                        + "" + templateBean.getTotalCreditLimit() + "|" + templateBean.getStaff() + "|"
                        + "" + templateBean.getAccRskProf() + "|"  + templateBean.getCardType() + "|"
                        + "" + templateBean.getCurrencyCode() + "|" + templateBean.getCustomerTemplateCode() + "|"
                        + "" + templateBean.getBillCycle() + "|"  + templateBean.getInterestprofileCode() + "|"
                        + "" + templateBean.getStatementProf()  + "|" + templateBean.getCardType() + "|"
                        + "" + templateBean.getStatus();





                String valied = this.isValiedTemplate(templateBean);

                if (valied == null) {


                    templateManager = new AccountTemplateManager();
                    this.setAudittraceValue(request, templateBean);
                    int isUpdate = templateManager.updateTemplate(templateBean, systemAuditBean);


                    if (isUpdate == 1) {
                        request.setAttribute("successMsg", "Sucssesfully updated account template.");
                        rd = getServletContext().getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {

                        request.setAttribute("templateBean", templateBean);
                        request.setAttribute("operation", "edit");
                        request.setAttribute("errorMsg", "Faild updatting account template.");
                        rd = getServletContext().getRequestDispatcher("/ManageTemplateMgtServlet");
                        rd.forward(request, response);
                    }

                } else {
                    templateManager = new AccountTemplateManager();
                    accountRskProf = templateManager.getAllAccountRskProf(request.getParameter("cusTempCode"));
                    request.setAttribute("riskProfLst", accountRskProf);
                    request.setAttribute("templateBean", templateBean);
                    request.setAttribute("operation", "edit");
                    request.setAttribute("errorMsg", valied);
                    request.setAttribute("isBack", "yes");
//                rd = getServletContext().getRequestDispatcher("/ManageTemplateMgtServlet");
                    rd = getServletContext().getRequestDispatcher("/ManageAccountTempalteServlet");

                    rd.forward(request, response);
                }


            } else {

                //if invalid throw accessdenied exception
                throw new AccessDeniedException();

            }



        } catch (SQLException ex) {
            request.setAttribute("templateBean", templateBean);
            request.setAttribute("operation", "edit");
            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            //rd = getServletContext().getRequestDispatcher("/ManageTemplateMgtServlet");
            rd = getServletContext().getRequestDispatcher("/ManageAccountTempalteServlet");
            rd.forward(request, response);
        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            request.setAttribute("errorMsg", MessageVarList.ACCESS_DENIED_TASK);
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);


        } catch (Exception ex) {
            request.setAttribute("templateBean", templateBean);
            request.setAttribute("operation", "edit");
            request.setAttribute("errorMsg", "Error occurred when updatting account template.");
            //rd = getServletContext().getRequestDispatcher("/ManageTemplateMgtServlet");
            rd = getServletContext().getRequestDispatcher("/ManageAccountTempalteServlet");
            rd.forward(request, response);

        } finally {
            out.close();
        }
    }

    private void setAudittraceValue(HttpServletRequest request, AccountTempBean templateBean) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Update Account Template. Account Template Code: '" + templateBean.getTemplateName() + "' by: " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.TEMPLATEMGT);
            systemAuditBean.setPageCode(PageVarList.ACCTEMPLATE);
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

    private String isValiedTemplate(AccountTempBean templateBean) throws Exception {
        UserInputValidator validObject = new UserInputValidator();

        try {
            //----------------------------------------------------------------------------           
            String msg = "";


            //----------------------------------------------------------------------------   
            if (templateBean.getTemplateCode().isEmpty()) {
                if (msg.equals("")) {
                    msg = "Enter template code";
                } else {
                    msg = msg + ", Enter template code";
                }
            } else if (!validObject.isAlphaNumeric(templateBean.getTemplateCode())) {
                if (msg.equals("")) {
                    msg = "Invalid template code";
                } else {
                    msg = msg + ", Invalid template code";
                }

            } //----------------------------------------------------------------------------   
            else if (templateBean.getTemplateName().isEmpty()) {
                if (msg.equals("")) {
                    msg = "Enter template name";
                } else {
                    msg = msg + ",Enter template name";
                }
            } else if (!validObject.isDescription(templateBean.getTemplateName())) {
                if (msg.equals("")) {
                    msg = "Invalid template name";
                } else {
                    msg = msg + ", Invalid template name";
                }

            } //----------------------------------------------------------------------------   
            else if (templateBean.getValiedFrom().isEmpty()) {
                if (msg.equals("")) {
                    msg = "Enter Valid From";
                } else {
                    msg = msg + ", Enter Valid From";
                }
            } //----------------------------------------------------------------------------   
            else if (templateBean.getValiedTo().isEmpty()) {
                if (msg.equals("")) {
                    msg = "Enter Valid To";
                } else {
                    msg = msg + ", Enter Valid To";
                }
            } //----------------------------------------------------------------------------   
            else if (isLargerDate(templateBean.getValiedFrom(), templateBean.getValiedTo())) {
                if (msg.equals("")) {
                    msg = MessageVarList.TO_LARGER_FROM;
                } else {
                    msg = msg + ", " + MessageVarList.TO_LARGER_FROM;
                }
            } //------------------------------------------------------------------------------
            else if (templateBean.getTotalCreditLimit().isEmpty()) {
                if (msg.equals("")) {
                    msg = "Enter total Credit Limit";
                } else {
                    msg = msg + ", Enter total Credit Limit";
                }
            } else if (!validObject.isDecimalOrNumeric(templateBean.getTotalCreditLimit(), "10", "2")) {
                if (msg.equals("")) {
                    msg = "Invalid total credit limit";
                } else {
                    msg = msg + ", Invalid total credit limit";
                }
            }//----------------------------------------------------------------------------             
            else if (!UserInputValidator.isDecimalNumeric(templateBean.getTotalCreditLimit()) && !UserInputValidator.isNumeric(templateBean.getTotalCreditLimit())) {
                if (msg.equals("")) {
                    msg = "Invalid Credit Limit";
                } else {
                    msg = msg + ", Invalid Credit Limit";
                }
            }//----------------------------------------------------------------------------   
            else if (templateBean.getBillCycle().isEmpty()) {
                if (msg.equals("")) {
                    msg = "Select Billing Cycle";
                } else {
                    msg = msg + ", Select Billing Cycle";
                }
            } //----------------------------------------------------------------------------   
            else if (templateBean.getStaff() == null) {
                if (msg.equals("")) {
                    msg = "Select Staff Status";
                } else {
                    msg = msg + ", Select Staff Status";
                }
            } //----------------------------------------------------------------------------   
            else if (templateBean.getCustomerTemplateCode().isEmpty()) {
                if (msg.equals("")) {
                    msg = "Select customer template";
                } else {
                    msg = msg + ", Select customer template";
                }
            } //----------------------------------------------------------------------------   
            else if (templateBean.getCardType().isEmpty()) {
                if (msg.equals("")) {
                    msg = "Select Card Type";
                } else {
                    msg = msg + ", Select Card Type";
                }
            } //----------------------------------------------------------------------------   
            else if (templateBean.getCurrencyCode().isEmpty()) {
                if (msg.equals("")) {
                    msg = "Select currency Type";
                } else {
                    msg = msg + ", Select currency Type";
                }
            } //----------------------------------------------------------------------------   
            else if (templateBean.getInterestprofileCode().isEmpty()) {
                if (msg.equals("")) {
                    msg = "Select Interest Profile";
                } else {
                    msg = msg + ", Select Interest Profile";
                }
            } //----------------------------------------------------------------------------  
            else if (templateBean.getStatus().isEmpty()) {
                if (msg.equals("")) {
                    msg = "Select status";
                } else {
                    msg = msg + ",Select status";
                }
            }
            //----------------------------------------------------------------------------  
            else if (templateBean.getAccRskProf().isEmpty()) {
                if (msg.equals("")) {
                    msg = "Select Risk Profile";
                } else {
                    msg = msg + ", Select Risk Profile";
                }
            } 
            //---------------------------------------------------------------------
            else if (templateBean.getTxnProfCode().isEmpty()) {
                if (msg.equals("")) {
                    msg = "Select Transaction Profile";
                } else {
                    msg = msg + ", Select Transaction Profile";
                }
            } 
            //---------------------------------------------------------------------
            else if (templateBean.getStatementProf().isEmpty()) {
                if (msg.equals("")) {
                    msg = "Select Billing and Statement Profile";
                } else {
                    msg = msg + ", Select Billing and Statement Profile";
                }
            }//----------------------------------------------------------------------------            
                      

            if (msg.equals("")) {
                return null;

            } else {
                return msg;
            }


        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean isLargerDate(String from, String to) throws ParseException {
        boolean isLarge = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date from_date = dateFormat.parse(from);
        Date to_date = dateFormat.parse(to);

        if (from_date.after(to_date)) {
            isLarge = true;
        }

        return isLarge;
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
