/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.LetterBodyFormatBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.LetterTemplateBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.DocumentTypeMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chanuka
 */
public class UpdateLetterTemplateMgtLoadServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private String id = null;
    private LetterTemplateBean letterTemplateBean;
    private LetterBodyFormatBean letterBodyBean;
    private String url = "/administrator/controlpanel/systemconfigmgt/lettertemplatemgt.jsp";

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


            if (!this.isValidTaskByUser(sessionVarlist.getUserPageTaskList(), TaskVarList.UPDATE)) {
                throw new AccessDeniedException();
            }

            String templateCode = request.getParameter("id");
            rd = request.getRequestDispatcher(url);
            request.setAttribute("operationtype", "update");


            List<LetterTemplateBean> templateList = DocumentTypeMgtManager.getInstance().getAllLetterTemplateDetailsList();
            request.setAttribute("templateList", templateList);






            for (LetterTemplateBean letterBean : templateList) {
                if (letterBean.getTemplateCode().equals(templateCode)) {
                    letterTemplateBean = letterBean;
                }
            }

            if (letterTemplateBean != null) {

                letterBodyBean = new LetterBodyFormatBean();
                String[] basicRecords = letterTemplateBean.getTemplateFormat().split("~");

                for (int i = 1; i < basicRecords.length + 1; i++) {
                    String singleRecord = basicRecords[i - 1];

                    String[] data = singleRecord.split("\\|");


                    if (i == 1) {

                        boolean isColumnExist = isCheckForValidColumn(data[0]);
                        if (!isColumnExist) {
                            String[] actualData = new String[4];
                            actualData[0] = "TEXT";
                            actualData[1] = data[1];
                            actualData[2] = data[2];
                            actualData[3] = data[0];

                            letterTemplateBean.setFieldSet1(actualData);
                        } else {
                            letterTemplateBean.setFieldSet1(data);
                        }
                    } else if (i == 2) {
                        boolean isColumnExist = isCheckForValidColumn(data[0]);
                        if (!isColumnExist) {
                            String[] actualData = new String[4];
                            actualData[0] = "TEXT";
                            actualData[1] = data[1];
                            actualData[2] = data[2];
                            actualData[3] = data[0];

                            letterTemplateBean.setFieldSet2(actualData);
                        } else {
                            letterTemplateBean.setFieldSet2(data);
                        }
                    } else if (i == 3) {
                        boolean isColumnExist = isCheckForValidColumn(data[0]);
                        if (!isColumnExist) {
                            String[] actualData = new String[4];
                            actualData[0] = "TEXT";
                            actualData[1] = data[1];
                            actualData[2] = data[2];
                            actualData[3] = data[0];
                            letterTemplateBean.setFieldSet3(actualData);
                        } else {
                            letterTemplateBean.setFieldSet3(data);
                        }
                    } else if (i == 4) {
                        boolean isColumnExist = isCheckForValidColumn(data[0]);
                        if (!isColumnExist) {
                            String[] actualData = new String[4];
                            actualData[0] = "TEXT";
                            actualData[1] = data[1];
                            actualData[2] = data[2];
                            actualData[3] = data[0];
                            letterTemplateBean.setFieldSet4(actualData);
                        } else {
                            letterTemplateBean.setFieldSet4(data);
                        }
                    } else if (i == 5) {
                        boolean isColumnExist = isCheckForValidColumn(data[0]);
                        if (!isColumnExist) {
                            String[] actualData = new String[4];
                            actualData[0] = "TEXT";
                            actualData[1] = data[1];
                            actualData[2] = data[2];
                            actualData[3] = data[0];
                            letterTemplateBean.setFieldSet5(actualData);
                        } else {
                            letterTemplateBean.setFieldSet5(data);
                        }
                    } else if (i == 6) {
                        boolean isColumnExist = isCheckForValidColumn(data[0]);
                        if (!isColumnExist) {
                            String[] actualData = new String[4];
                            actualData[0] = "TEXT";
                            actualData[1] = data[1];
                            actualData[2] = data[2];
                            actualData[3] = data[0];
                            letterTemplateBean.setFieldSet6(actualData);
                        } else {
                            letterTemplateBean.setFieldSet6(data);
                        }
                    } else if (i == 7) {
                        String[] actualData = new String[4];
                        boolean isColumnExist = isCheckForValidColumn(data[0]);
                        if (!isColumnExist) {
                            actualData[0] = "TEXT";
                            actualData[1] = data[1];
                            actualData[2] = data[2];
                            actualData[3] = data[0];
                            letterTemplateBean.setFieldSet7(actualData);
                        } else {
                            letterTemplateBean.setFieldSet7(data);
                        }
                    }


                }








                String[] bodyRecords = letterTemplateBean.getBodyString().split("~");

                for (int i = 1; i < bodyRecords.length + 1; i++) {

                    String singleRecord = bodyRecords[i - 1];

                    String[] data = singleRecord.split("\\|");


                    if (i == 1) {

                        boolean isColumnExist = isCheckForValidColumn(data[0]);
                        if (!isColumnExist) {
                            String[] actualData = new String[4];
                            actualData[0] = "TEXT";
                            actualData[1] = data[1];
                            actualData[2] = data[2];
                            actualData[3] = data[0];
                            letterBodyBean.setFieldSetBody1(actualData);
                        } else {
                            letterBodyBean.setFieldSetBody1(data);
                        }

                    } else if (i == 2) {
                        boolean isColumnExist = isCheckForValidColumn(data[0]);
                        if (!isColumnExist) {
                            String[] actualData = new String[4];
                            actualData[0] = "TEXT";
                            actualData[1] = data[1];
                            actualData[2] = data[2];
                            actualData[3] = data[0];
                            letterBodyBean.setFieldSetBody2(actualData);
                        } else {
                            letterBodyBean.setFieldSetBody2(data);
                        }
                    } else if (i == 3) {
                        boolean isColumnExist = isCheckForValidColumn(data[0]);
                        if (!isColumnExist) {
                            String[] actualData = new String[4];
                            actualData[0] = "TEXT";
                            actualData[1] = data[1];
                            actualData[2] = data[2];
                            actualData[3] = data[0];
                            letterBodyBean.setFieldSetBody3(actualData);
                        } else {
                            letterBodyBean.setFieldSetBody3(data);
                        }
                    } else if (i == 4) {
                        boolean isColumnExist = isCheckForValidColumn(data[0]);
                        if (!isColumnExist) {
                            String[] actualData = new String[4];
                            actualData[0] = "TEXT";
                            actualData[1] = data[1];
                            actualData[2] = data[2];
                            actualData[3] = data[0];
                            letterBodyBean.setFieldSetBody4(actualData);
                        } else {
                            letterBodyBean.setFieldSetBody4(data);
                        }
                    } else if (i == 5) {
                        boolean isColumnExist = isCheckForValidColumn(data[0]);
                        if (!isColumnExist) {
                            String[] actualData = new String[4];
                            actualData[0] = "TEXT";
                            actualData[1] = data[1];
                            actualData[2] = data[2];
                            actualData[3] = data[0];
                            letterBodyBean.setFieldSetBody5(actualData);
                        } else {
                            letterBodyBean.setFieldSetBody5(data);
                        }
                    }


                }










                sessionVarlist.setLetterBodyFormatBean(letterBodyBean);


                if (letterTemplateBean.getTitleString() != null) {
                    sessionVarlist.setTitleValue(letterTemplateBean.getTitleString().split("\\|")[0]);
                }

                sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);


                request.setAttribute("tmpLetterBean", letterTemplateBean);
                rd = getServletContext().getRequestDispatcher(url);
            }




        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadLetterTemplateMgtServlet");

        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    /**
     * to check the validity of the task to the logged user
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

    private boolean isCheckForValidColumn(String column) throws Exception {
        boolean isValidcolumn = false;
        try {

            if (column.equals("ADDRESS1") || column.equals("ADDRESS2") || column.equals("ADDRESS3") || column.equals("NAME")
                    || column.equals("TITLE") || column.equals("DATE") || column.equals("CARDNUMBER") || column.equals("FOOTER")
                    || column.equals("BODY")) {
                isValidcolumn = true;
            }

            return isValidcolumn;
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
