/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.businesslogic;

import com.epic.cms.admin.controlpanel.profilemgt.bean.FeeProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.InterestprofileBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SessionValidBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserPasswordBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.persistance.SystemUserPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.persistance.TaskManagementPersistance;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.application.common.persistance.StatusPersistence;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BankBranchBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardCategoryBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.email.service.EmailApplicationService;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.variable.EmailVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author janaka_h
 */
public class SystemUserManager {

    private Map<SectionBean, List<PageBean>> sectionPageList;
    private Connection CMSCon;
    private SystemUserPersistance sysUserPerObj;
    private SystemAuditManager sysAuditManager;
    private StatusPersistence statusPst;
    private TaskManagementPersistance taskPer;
    private List<StatusBean> statusList;
    private List<InterestprofileBean> interestProfileList;
    private List<CardCategoryBean> cardCategoryList;
    private List<UserRoleBean> userRoleList;
    private List<SystemUserBean> userList = new ArrayList<>();
    private List<TaskBean> taskDetails;
    private List<BankBranchBean> branchList;
    private HashMap<String, String> dataCaptureUserList = null;

    public Map<SectionBean, List<PageBean>> getSectionPage(String userRole, String application) throws Exception {

        try {

            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sectionPageList = sysUserPerObj.getSectionPageList(CMSCon, userRole, application);

            return sectionPageList;

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;

        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

//        
    }

    /**
     * get status details according to category code
     *
     * @param categoryCode
     * @return
     * @throws Exception
     */
    public List<StatusBean> getStatusByUserRole(String categoryCode) throws Exception {
        try {
            statusPst = new StatusPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            statusList = statusPst.getStatusByCategoryCode(CMSCon, categoryCode);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {

                CMSCon.rollback();
                throw ex;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return statusList;
    }

    public List<InterestprofileBean> getInterestProfilelist() throws Exception {
        interestProfileList = new ArrayList<InterestprofileBean>();
        try {
            statusPst = new StatusPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            interestProfileList = statusPst.getInterestProfilelist(CMSCon);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {

                CMSCon.rollback();
                throw ex;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return interestProfileList;
    }

    public List<FeeProfileBean> getFeeProfileList() throws Exception {
        List<FeeProfileBean> feeprofileBeanList = new ArrayList<FeeProfileBean>();
        try {
            statusPst = new StatusPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            feeprofileBeanList = statusPst.getFeeProfileList(CMSCon);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {

                CMSCon.rollback();
                throw ex;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return feeprofileBeanList;
    }

    public List<CardCategoryBean> getCardCategoryList() throws Exception {
        try {
            statusPst = new StatusPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardCategoryList = statusPst.getCardCategoryList(CMSCon);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {

                CMSCon.rollback();
                throw ex;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return cardCategoryList;
    }

    public SystemUserBean validateUser(SystemUserBean CMSSysUser) throws Exception {

        SystemUserBean valideUser = null;
        try {
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            valideUser = sysUserPerObj.validateCMSSysUser(CMSCon, CMSSysUser);
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return valideUser;
    }

    /**
     * this method use to validate user session id for active users.<BR/>
     * If user no longer active return false,else if user sessionID can't match
     * with<BR/>
     * current sessionID return false or if it match return ture.
     *
     * @param userName - String
     * @param sessionID -String
     * @return flag-boolean
     */
    public boolean validateUserSession(String userName, String sessionID) throws Exception {
        try {
            boolean flag = false;
            SessionValidBean status = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysUserPerObj = new SystemUserPersistance();
            try {
                status = sysUserPerObj.getUserSatatus(CMSCon, userName);

                if (status.getStatus().equals(StatusVarList.DEACTIVE_STATUS)) {
                    flag = false;
                }
                if (status.getStatus().equals(StatusVarList.ACTIVE_STATUS)) {
                    if (status.getSessionID().equals(sessionID)) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
            } catch (NullPointerException e) {
                return false;
            }
            CMSCon.commit();
            return flag;
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * when system user login update user session id
     *
     * @param sysUser
     * @return
     * @throws Exception
     */
    public int updateSysUserSessionId(SystemUserBean sysUser) throws Exception {
        int result = -1;
        try {
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            result = sysUserPerObj.updateCMSSystemUserSessId(CMSCon, sysUser);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return result;
    }

    public List<ApplicationModuleBean> getApplicationList(String userRoleCode) throws SQLException, Exception {
        try {
            List<ApplicationModuleBean> applicationList = null;
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            applicationList = sysUserPerObj.getApplicationList(CMSCon, userRoleCode);

            return applicationList;

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public List<SystemUserBean> getAllSysUsersExceptDel(SessionUser sessionUser) throws Exception {
        List<SystemUserBean> allUserlist = null;
        List<SystemUserBean> requestUsersList = null;
        List<SystemUserBean> changePasUserReq = null;
        try {
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            //systemuserrequest table
            requestUsersList = sysUserPerObj.getAllSysUsersByStatus(CMSCon, StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT, StatusVarList.DA_REQUSET_INITIATE, StatusVarList.DELETE_SYSTEMUSER_REQUEST_SENT);

            //userpasswordrequest table
            changePasUserReq = sysUserPerObj.getResetPasswordReq(CMSCon);

            //systemuser table
            allUserlist = sysUserPerObj.getAllSysUsersExceptDeleted(CMSCon);

            //add change password requests
            requestUsersList.addAll(changePasUserReq);
            //adding other system users to list
            requestUsersList.addAll(allUserlist);
            //allUserlist.clear();

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return requestUsersList;
    }

    public List<SystemUserBean> getAllSysUsersByStatus(SessionUser sessionUser, String status1, String status2, String status3) throws Exception {
        List<SystemUserBean> allUserlist = null;
        try {
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            allUserlist = sysUserPerObj.getAllSysUsersByStatus(CMSCon, status1, status2, status3);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return allUserlist;
    }

    public int insertSystemUser(SystemUserBean userBean, SystemAuditBean systemAuditBean) throws Exception {

        int isAdd;
        try {
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSCon.setAutoCommit(false);
            isAdd = sysUserPerObj.insertSysUser(CMSCon, userBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return isAdd;
    }

    public int insertSystemUserRequest(SystemUserBean userBean, SystemAuditBean systemAuditBean) throws Exception {

        int isAdd;
        try {
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSCon.setAutoCommit(false);
            isAdd = sysUserPerObj.insertSysUserRequest(CMSCon, userBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {

        }

        return isAdd;
    }

    public int insertUserPasswordRequest(SystemUserBean userBean, SystemAuditBean systemAuditBean) throws Exception {

        int isAdd;
        try {
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSCon.setAutoCommit(false);
            isAdd = sysUserPerObj.insertUserPasswordRequest(CMSCon, userBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {

        }

        return isAdd;
    }

    public List<String> getTasksByPageCodeAndUserRole(String userRoleCode, String pageCode) throws Exception {
        List<String> taskList = new ArrayList<String>();
        try {

            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            taskList = sysUserPerObj.getTasks(CMSCon, userRoleCode, pageCode);

            return taskList;

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;

        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

    }

    public int updateSystemUser(SystemUserBean userBean, SystemAuditBean systemAuditBean) throws Exception {

        int result = -1;
        String isEmailSent = userBean.getIsEmailSent();

        try {
            sysUserPerObj = new SystemUserPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            if (StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT.equals(userBean.getStatusCode())) {
                //new user request handling
                userBean.setInitialloginstatus(StatusVarList.IS_FIRST_LOGIN);
                userBean.setIsEmailSent(StatusVarList.WELCOME_EMAIL_NOT_SENT);
                userBean.setIslockedforauth(StatusVarList.IS_NOT_LOCKED_AUTH);
                userBean.setStatusCode(StatusVarList.ACTIVE_STATUS);
                result = sysUserPerObj.insertSysUser(CMSCon, userBean);
                //update the system user request table
                userBean.setStatusCode(StatusVarList.DA_REQUSET_APPROVE);
                result += sysUserPerObj.updateSystemUserRequest(CMSCon, userBean);
                systemAuditBean.setDescription("New System user added, user name: " + userBean.getUserName() + " by " + userBean.getRequestedUser());
                sysAuditManager.insertAudit(systemAuditBean, CMSCon);
                CMSCon.commit();
            } else if (StatusVarList.DA_REQUSET_INITIATE.equals(userBean.getStatusCode())) {
                //edit existing user request handling
                userBean.setIslockedforauth(StatusVarList.IS_LOCKED_AUTH);
                systemAuditBean.setDescription("System user update request sent, user name: " + userBean.getUserName() + " by " + userBean.getRequestedUser());
                result = this.insertSystemUserRequest(userBean, systemAuditBean);
                result += sysUserPerObj.updateSystemUser(CMSCon, userBean);
                //sysAuditManager.insertAudit(systemAuditBean, CMSCon);
                CMSCon.commit();

            } else if (StatusVarList.DA_REQUSET_REJECT.equals(userBean.getStatusCode())) {
                //request rejection handling
                result = sysUserPerObj.updateSystemUserRequest(CMSCon, userBean);
                systemAuditBean.setDescription("System user update request rejected, user name: " + userBean.getUserName() + " by " + userBean.getAuthorizedUser());
                sysAuditManager.insertAudit(systemAuditBean, CMSCon);
                CMSCon.commit();
            } else {
                //exist user details updated handling
                userBean.setIslockedforauth(StatusVarList.IS_NOT_LOCKED_AUTH);
                //get original status from systemuser table
                //String sysUserStatus=sysUserPerObj.getSystemuserReqStatus(CMSCon, userBean.getUserName());
                //userBean.setUserstatus(sysUserStatus);
                result = sysUserPerObj.updateSystemUser(CMSCon, userBean);
                //update the system user request table
                userBean.setStatusCode(StatusVarList.DA_REQUSET_APPROVE);
                result += sysUserPerObj.updateSystemUserRequest(CMSCon, userBean);
                systemAuditBean.setDescription("System user update request approved, user name: " + userBean.getUserName() + " by " + userBean.getAuthorizedUser());
                sysAuditManager.insertAudit(systemAuditBean, CMSCon);
                CMSCon.commit();
            }

            if (isEmailSent != null && StatusVarList.DA_REQUSET_APPROVE.equals(userBean.getStatusCode()) && StatusVarList.WELCOME_EMAIL_NOT_SENT.equals(userBean.getIsEmailSent())) {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("USERNAME", userBean.getUserName());
                parameters.put("PASSWORD", userBean.getPlainTextPassword());
                EmailApplicationService emailApplicationService = new EmailApplicationService();

                //sending the welcome email 
                emailApplicationService.sendEmail(userBean.getEmail(), EmailVarList.WELCOME_EMAIL_TEMP_CODE, "\\|", parameters, systemAuditBean.getLastUpdateduser());

                // update the email status to system user table
                userBean.setIsEmailSent(StatusVarList.WELCOME_EMAIL_SENT);
                this.updateEmailStatusAndPassword(userBean);

            }

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return result;

    }

    public int deleteSystemUserRequest(SystemUserBean userBean, SystemAuditBean systemAuditBean) throws Exception {
        int isDelete;
        try {
            sysUserPerObj = new SystemUserPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            isDelete = sysUserPerObj.deleteSystemUserRequest(CMSCon, userBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return isDelete;
    }

    public int deleteSystemUser(SystemUserBean userBean) throws Exception {
        int isDelete;
        try {
            sysUserPerObj = new SystemUserPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            isDelete = sysUserPerObj.deleteSystemUser(CMSCon, userBean);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return isDelete;
    }

    public int insertAuditWhenLogin(SystemAuditBean systemAuditBean) throws Exception {

        int result = -1;
        try {

            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager.insertLoginAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return result;

    }

    public int changePassword(SystemUserBean userBean, SystemAuditBean systemAuditBean, boolean isOtherUser) throws Exception {

        int isAdd;
        try {
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSCon.setAutoCommit(false);
            isAdd = sysUserPerObj.changePassword(CMSCon, userBean, true);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return isAdd;
    }

    public int checkPassword(SystemUserBean userBean) throws Exception {

        int isChecked;
        try {
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSCon.setAutoCommit(false);
            isChecked = sysUserPerObj.checkPassword(CMSCon, userBean);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return isChecked;
    }

    public List<BankBranchBean> getBranchLst() throws Exception {
        try {
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            branchList = sysUserPerObj.getBranchLst(CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return branchList;
    }

    public String getBankName() throws Exception {
        String bankName = "";
        try {
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bankName = sysUserPerObj.getBankName(CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return bankName;
    }

    /**
     * get status details according to category code
     *
     * @param categoryCode
     * @return
     * @throws Exception
     */
    public List<StatusBean> getStatusByStatusCodeList(String statusCodeList) throws Exception {
        try {
            statusPst = new StatusPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            statusList = statusPst.getStatusByStatusCodeList(CMSCon, statusCodeList);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {

                CMSCon.rollback();
                throw ex;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return statusList;
    }

    public String getTitle(String pagecode) throws Exception {
        String title = "";
        try {

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            statusPst = new StatusPersistence();
            title = statusPst.getTitle(CMSCon, pagecode);

            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {

                CMSCon.rollback();
                throw ex;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return title;
    }

    public String getapplicationDes(String appcode) throws Exception {
        String des = "";
        try {

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            statusPst = new StatusPersistence();
            des = statusPst.getApplicationDescription(CMSCon, appcode);

            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {

                CMSCon.rollback();
                throw ex;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return des;
    }

    public List<UserPasswordBean> getUserPasswordBeanList(String userName, String password) throws SQLException, Exception {

        List<UserPasswordBean> list = null;
        try {
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            list = sysUserPerObj.getUserPasswordBeanList(CMSCon, userName, password);
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return list;

    }

    public int updateUserInvalidLoginAttempts(SystemUserBean userBean) throws Exception {

        int result = -1;
        try {
            sysUserPerObj = new SystemUserPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            result = sysUserPerObj.updateUserInvalidLoginAttempts(CMSCon, userBean);
            //sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return result;
    }

    public SystemUserBean getLoggedUserDetails(SystemUserBean CMSSysUser, String userName) throws Exception {

        SystemUserBean valideUser = null;
        try {
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            valideUser = sysUserPerObj.getLoggedUserDetails(CMSCon, userName);
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return valideUser;
    }

    //When deactivate user based on other colum behavious this metod can be use. 
    public int deActivateUser(SystemUserBean userBean, List<String> otherColumnNames, List<Object> otherColumnValues) throws Exception {

        int result = -1;
        try {
            sysUserPerObj = new SystemUserPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            result = sysUserPerObj.deActivateUser(CMSCon, userBean, otherColumnNames, otherColumnValues);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return result;
    }

    public int updateEmailStatusAndPassword(SystemUserBean userBean) throws Exception {

        int result = -1;
        try {
            sysUserPerObj = new SystemUserPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            result = sysUserPerObj.updateEmailStatusAndPassword(CMSCon, userBean);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        }
        return result;
    }

    public int updateSystemUserEmailSentAndPassword(SystemUserBean userBean) throws Exception {

        int result = -1;
        try {
            sysUserPerObj = new SystemUserPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            result = sysUserPerObj.updateEmailStatusAndPassword(CMSCon, userBean);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        }
        return result;
    }

    public int updateSystemUserAuthLock(SystemUserBean userBean) throws Exception {

        int result = -1;
        try {
            sysUserPerObj = new SystemUserPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            result = sysUserPerObj.updateSystemAuthLock(CMSCon, userBean);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        }
        return result;
    }

    public int updateSystemUserFirstLoginStatus(SystemUserBean userBean) throws Exception {

        int result = -1;
        try {
            sysUserPerObj = new SystemUserPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            result = sysUserPerObj.updateSystemFirstLoginStatus(CMSCon, userBean);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        }
        return result;
    }

    //used for password change
    public int updateEmailStatusAndPassword(SystemUserBean userBean, SystemAuditBean systemAuditBean) throws Exception {

        int result = -1;
        try {
            sysUserPerObj = new SystemUserPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            result = sysUserPerObj.updateEmailStatusAndPassword(CMSCon, userBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        }
        return result;
    }

    public int updateEmailStatusAndPasswordAndAuth(SystemUserBean userBean) throws Exception {

        int result = -1;
        try {
            sysUserPerObj = new SystemUserPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            result = sysUserPerObj.updateEmailStatusAndPasswordAndAuth(CMSCon, userBean);
            result += sysUserPerObj.insertToUserPassword(CMSCon, userBean);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return result;
    }

    public int updateEmailStatusAndPasswordAndAuthForReject(SystemUserBean userBean) throws Exception {

        int result = -1;
        try {
            sysUserPerObj = new SystemUserPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            result = sysUserPerObj.updateEmailStatusAndPasswordAndAuth(CMSCon, userBean);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return result;
    }

    public int updateSystemRequestDeleteStatus(SystemUserBean userBean) throws Exception {

        int result = -1;
        try {
            sysUserPerObj = new SystemUserPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            result = sysUserPerObj.updateSysUserDelete(CMSCon, userBean);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return result;
    }

    //get dual auth role according to user role
    public HashMap<String, String> getDualRoleUserList(String userRole) throws Exception {
        try {
            sysUserPerObj = new SystemUserPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            dataCaptureUserList = sysUserPerObj.getDualAuthRoleList(CMSCon, userRole);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return dataCaptureUserList;
    }

    public List<StatusBean> getStatusByUserRole(String dath, String genr) throws SQLException, Exception {
        try {
            statusPst = new StatusPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            statusList = statusPst.getStatusByCategoryCode(CMSCon, dath, genr);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {

                CMSCon.rollback();
                throw ex;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return statusList;
    }

}
