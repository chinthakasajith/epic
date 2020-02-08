/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserLevelBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRolePrivilegeBean;
import com.epic.cms.admin.controlpanel.sysusermgt.persistance.SystemUserLevelPersistance;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.application.common.persistance.StatusPersistence;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author sajith_g
 */
public class SystemUserLevelManager {
    
    private Connection CMSCon;
    private SystemUserLevelPersistance sysUserLevelPerObj=null;
    private SystemAuditManager sysAuditManager;
    private StatusPersistence statusPst;
    private List<StatusBean> statusList;
    private List<UserLevelBean> userLevelList;
    private List<UserRolePrivilegeBean> appSecPageTaskList = null;
    private List<ApplicationModuleBean> userRoleAppliacationList = null;
    private List<SectionBean> sectionList = null;
    private List<PageBean> pageList = null;
    private List<TaskBean> taskList = null;
    private List<UserRoleBean> userRoleListUnassignedToSchema = null;
    private List<UserRoleBean> userRoleListAssignedToSchema = null;
    
    /**
     * this method used to insert the user level
     *
     * @param userLevelBean
     * @param systemAuditBean
     * @return success-boolean
     * @throws Exception
     */
    public boolean insertUserRole(UserLevelBean userLevelBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            sysUserLevelPerObj = new SystemUserLevelPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = sysUserLevelPerObj.insertUserLevel(CMSCon, userLevelBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }
    
    /**
     * this method used to get all the user levels
     *
     * @return userlist
     * @throws Exception
     */
    public List<UserLevelBean> getAllUserLevel() throws Exception {
        try {
            sysUserLevelPerObj = new SystemUserLevelPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userLevelList = sysUserLevelPerObj.getAllUserLevel(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return userLevelList;
    }
    
    /**
     * this method used to get all active user levels
     *
     * @return active user list
     * @throws Exception
     */
    public List<UserLevelBean> getAllActiveUserLevel() throws Exception {
        try {
            sysUserLevelPerObj = new SystemUserLevelPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userLevelList = sysUserLevelPerObj.getAllActiveUserLevel(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return userLevelList;
    }
    
    /**
     * this method used to delete the user level
     *
     * @param userLevelBean
     * @param systemAuditBean
     * @return success-boolean
     * @throws Exception
     */
    public boolean deleteUserLevel(UserLevelBean userLevelBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            sysUserLevelPerObj = new SystemUserLevelPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = sysUserLevelPerObj.deleteUserLevel(CMSCon, userLevelBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }
    
    /**
     * this method used to update the user role
     *
     * @param userLevelBean
     * @param systemAuditBean
     * @return success-boolean
     * @throws Exception
     */
    public boolean updateUserLevel(UserLevelBean userLevelBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            sysUserLevelPerObj = new SystemUserLevelPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = sysUserLevelPerObj.updateUserLevel(CMSCon, userLevelBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }
    
}
