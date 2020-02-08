package com.epic.cms.admin.controlpanel.systemconfigmgt.servlet;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AreaBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.AreaMgtManager;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationconfig.creditscore.businesslogic.CardScoreManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.exception.ValidateException;
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
import java.sql.SQLException;
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
 * @author jeevan
 */
public class AddAreaMgtServlet extends HttpServlet {

    private SystemUserManager systemUserManager = null;
    private SessionVarList sessionVarlist = null;
    private SystemAuditBean systemAuditBean;
    private SessionUser sessionUser = null;
    private List<String> userTaskList;
    private String url = "/administrator/controlpanel/systemconfigmgt/areamgthome.jsp";
    private RequestDispatcher rd;
    private List<StatusBean> statusList;
    private List<AreaBean> areaBeanList;
    private AreaMgtManager areaMgtManager;
    private AreaBean bean;
    private HashMap<String, String> districtList = null;
    private String errorMessage = null;
    private String newValue;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessionObj = request.getSession(false);
        try {
            try {
                request.setAttribute("operationtype", "add");
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

                try {

                    String pageCode = PageVarList.AREA_MGT;
                    String taskCode = TaskVarList.ADD;

                    if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {
                    } else {
                        throw new AccessDeniedException();
                    }

                    sessionVarlist.setUserPageTaskList(userTaskList);

                } catch (AccessDeniedException adex) {
                    throw adex;
                }

            } catch (NullPointerException ex) {
                throw new SesssionExpException();
            }

            String provinceType = request.getParameter("province");//

            this.getAreaList();
            request.setAttribute("arealList", areaBeanList);

            this.getAllProvinces();
            request.setAttribute("provinceList", areaBeanList);

            this.getAllDistricts();
            request.setAttribute("districtList", areaBeanList);

            this.setValuesToBean(request);
            int success = -1;
            if (this.validateUserInput(bean)) {
                this.setAudittraceValue(request);

                try {
                    bean = getDistrictCode(bean);
//                    System.out.println("********************************* "+bean.getAreaCode()+" "+bean.getAreaDescription()+" "+bean.getProvinceName()+" "+bean.getDistriceName()+" "+bean.getDistrictCode());
                    success = this.inserAreaData();

                } catch (SQLException ex) {
                    request.setAttribute("areaBean", bean);
                    request.setAttribute("errorMsg", errorMessage);

                    this.getProvinceType(request.getParameter("provinceType"));
                    request.setAttribute("districtList", areaBeanList);

                    throw ex;
                } catch (Exception ex) {
                    throw ex;
                }
            } else {
                request.setAttribute("areaBean", bean);
                request.setAttribute("errorMsg", errorMessage);

                this.getProvinceType(request.getParameter("provinceType"));
                request.setAttribute("districtList", areaBeanList);
            }

            if (success > 0) {
                this.getAreaList();
                request.setAttribute("arealList", areaBeanList);

                this.getAllProvinces();
                request.setAttribute("provinceList", areaBeanList);

                this.getAllDistricts();
                request.setAttribute("districtList", areaBeanList);

                request.setAttribute(MessageVarList.JSP_SUCCESS, MessageVarList.AREA_CODE_ADD_SUCCESS + " code " + bean.getAreaCode());
            }
            rd = getServletContext().getRequestDispatcher(url);

        } catch (SesssionExpException sex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.SESSION_EXPIRED);
        } catch (NewLoginSessionException nlex) {
            rd = getServletContext().getRequestDispatcher(MessageVarList.SESSION_EXPIRED_URL
                    + MessageVarList.LAST_SESSION_CLOSE);
        } catch (AccessDeniedException adex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.ACCESS_DENIED_PAGETASK);
            rd = getServletContext().getRequestDispatcher("/LoadAreaMgtServlet");

        } catch (SQLException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, OracleMessage.getMessege(ex.getMessage()));
            rd = request.getRequestDispatcher(url);
        } catch (ValidateException ex) {
            request.setAttribute(MessageVarList.JSP_ERROR,
                    CardScoreManager.getScoreCardInstance().getErrorMsg());
            rd = request.getRequestDispatcher(url);
        } catch (Exception ex) {
            request.setAttribute(MessageVarList.JSP_ERROR, MessageVarList.UNKNOW_ERROR);
            rd = request.getRequestDispatcher(url);
        } finally {
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

    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {

        boolean isValidTaskAccess = false;

        try {
            systemUserManager = new SystemUserManager();
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

    private void getAreaList() throws Exception {
        try {

            areaMgtManager = new AreaMgtManager();
            areaBeanList = areaMgtManager.getAreaList();

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllProvinces() throws Exception {
        try {
            areaMgtManager = new AreaMgtManager();
            areaBeanList = areaMgtManager.getAllProvinces();

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void getAllDistricts() throws Exception {
        try {

            areaMgtManager = new AreaMgtManager();
            areaBeanList = areaMgtManager.getAllDistricats();

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void setValuesToBean(HttpServletRequest request) throws Exception {
        try {

            bean = new AreaBean();
            bean.setAreaCode(request.getParameter("areacode"));
            bean.setAreaDescription(request.getParameter("areadis"));
            bean.setProvinceName(request.getParameter("provinceType"));
            bean.setDistriceName(request.getParameter("districeType"));

            bean.setLastUpdatedTime(new Date());
            bean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());

            newValue = bean.getAreaCode() + "|" + bean.getAreaDescription() + "|" + bean.getProvinceName() + "|" + bean.getDistriceName();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean validateUserInput(AreaBean bean) throws Exception {
        boolean isValidate = true;

        try {

            if (bean.getAreaCode().contentEquals("") || bean.getAreaCode().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.AREA_CODE_EMPTY;

            } else if (!UserInputValidator.isAlphaNumeric(bean.getAreaCode())) {
                isValidate = false;
                errorMessage = MessageVarList.AREA_CODE_INVALID;

            } else if (bean.getAreaDescription().contentEquals("") || bean.getAreaDescription().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.AREA_DESC_EMPTY;

            } else if (!UserInputValidator.isDescription(bean.getAreaDescription())) {
                isValidate = false;
                errorMessage = MessageVarList.AREA_DESC_INVALID;

            } else if (bean.getProvinceName().contentEquals("") || bean.getProvinceName().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.AREA_PROVINCE_EMPTY;

            } else if (bean.getDistriceName().contentEquals("") || bean.getDistriceName().length() <= 0) {
                isValidate = false;
                errorMessage = MessageVarList.AREA_DISTRICT_EMPTY;
            }

        } catch (Exception ex) {
            throw ex;
        }
        return isValidate;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            String uniqueId = request.getParameter("areacode");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setUniqueId(uniqueId);
            systemAuditBean.setDescription("Add Area Management. Code: " + uniqueId + "; by:  " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.ADMINISTRATION_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.SYSTEMCONFIGMANAGEMENT);
            systemAuditBean.setPageCode(PageVarList.AREA_MGT);
            systemAuditBean.setTaskCode(TaskVarList.ADD);
            systemAuditBean.setIp(request.getRemoteAddr());
            systemAuditBean.setRemarks(uniqueId);
            systemAuditBean.setFieldName("");
            systemAuditBean.setOldValue("");
            systemAuditBean.setNewValue(newValue);

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    private int inserAreaData() throws Exception {
        try {

            areaMgtManager = new AreaMgtManager();
            int i = areaMgtManager.insertAreaData(systemAuditBean, bean);
            return i;

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private AreaBean getDistrictCode(AreaBean areaBean) throws Exception {
        String areaCode;
        try {
            areaMgtManager = new AreaMgtManager();
            areaCode = areaMgtManager.getDistrictCode(areaBean);
            bean.setDistrictCode(areaCode);
        } catch (Exception ex) {
            throw ex;
        }
        return bean;
    }

    private void getProvinceType(String provinceType) throws Exception {
        try {

            areaMgtManager = new AreaMgtManager();
            areaBeanList = areaMgtManager.getProvinceType(provinceType);

        } catch (Exception ex) {
            throw ex;
        }
    }
}
