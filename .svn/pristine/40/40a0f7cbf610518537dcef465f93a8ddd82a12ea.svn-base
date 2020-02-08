/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ApplicationRejectReasonBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.ApplicationRejectreasonOersistence;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * this manager class use to wrote all the manager that relate to application reject 
 * @author ayesh
 */
public class ApplicationRejectReasonManager {

    private static ApplicationRejectReasonManager setInstance;
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private ApplicationRejectreasonOersistence perObj;
    private String errorMsg;

    private ApplicationRejectReasonManager() {
    }

    /**
     * get ApplicationRejectReasonManager class instance 
     * @return ApplicationRejectReasonManager
     */
    public synchronized static ApplicationRejectReasonManager getInstance() {
        if (setInstance == null) {
            setInstance = new ApplicationRejectReasonManager();
        }
        return setInstance;
    }

    /**
     * get all available reason list
     * @return List<ApplicationRejectReasonBean>
     * @throws SQLException
     * @throws Exception 
     */
    public List<ApplicationRejectReasonBean> getAllReason() throws SQLException, Exception {

        try {
            List<ApplicationRejectReasonBean> reasonList = null;
            perObj = new ApplicationRejectreasonOersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
//----
            reasonList = perObj.getAllReasobList(CMSCon);
            CMSCon.commit();
            return reasonList;
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
     * add new application reject reason  manager
     * @param systemAuditBean
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int addAppRejectReason(SystemAuditBean systemAuditBean, ApplicationRejectReasonBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new ApplicationRejectreasonOersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            rowCount = perObj.addAppRejectReason(CMSCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            return rowCount;
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
     * update application reject reason manager
     * @param systemAuditBean
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int updateRejectReason(SystemAuditBean systemAuditBean, ApplicationRejectReasonBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new ApplicationRejectreasonOersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            rowCount = perObj.updateRejectReason(CMSCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            return rowCount;
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
     * delete selected application reject reason
     * @param systemAuditBean
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteAppRejectReason(SystemAuditBean systemAuditBean, String id) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new ApplicationRejectreasonOersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            rowCount = perObj.deleteAppReject(CMSCon, id);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            return rowCount;
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
     * get selected reason manager
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public ApplicationRejectReasonBean getSelectedRejectReason(String id) throws SQLException, Exception {

        try {
            ApplicationRejectReasonBean bean;
            perObj = new ApplicationRejectreasonOersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bean = perObj.getSelectedReason(CMSCon, id);

            CMSCon.commit();
            return bean;
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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

    //----------------------------------------------
    /*
     * chaeck given input valid to process -if ok return true else return false
     */
    public boolean isValidToProcess(ApplicationRejectReasonBean bean) throws Exception {
        boolean flag = true;
        errorMsg = "";
        try {
            if (bean.getReasonCode().isEmpty()) {
                flag = false;
                if (errorMsg.isEmpty()) {
                    errorMsg = MessageVarList.APP_REJECT_CODE_EMPTY;
                }
            } else if (bean.getDescription().isEmpty()) {
                flag = false;
                if (errorMsg.isEmpty()) {
                    errorMsg = MessageVarList.APP_REJECT_DESCRIPTION_EMPTY;
                }
            } else if (!UserInputValidator.isDescription(bean.getDescription())) {
                flag = false;

                errorMsg = MessageVarList.APP_REJECT_DESCRIPTION_INVALID;

            }

        } catch (Exception e) {
            throw e;
        }
        return flag;


    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
