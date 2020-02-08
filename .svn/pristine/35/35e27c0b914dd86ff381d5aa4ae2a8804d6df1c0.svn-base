/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.persistance.TaskManagementPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class TaskMgtManager {
    

    private TaskManagementPersistance taskPer;
    private Connection CMSCon;
    private List<TaskBean> taskDetails;
    private SystemAuditManager sysAuditManager;
    
     public List<TaskBean> getTaskDetails() throws Exception {
        try {
            taskPer = new TaskManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            taskDetails = taskPer.gettaskDetails(CMSCon);


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
        return taskDetails;
    }
    
     
    public boolean inserTask(TaskBean task, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            sysAuditManager = new SystemAuditManager();
            taskPer = new TaskManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = taskPer.insertTask(CMSCon, task);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }
    
        public boolean updateTask(TaskBean task ,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
           taskPer = new TaskManagementPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = taskPer.updateTask(CMSCon, task);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }
     
     
   public boolean deleteTask(TaskBean task ,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            taskPer = new TaskManagementPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = taskPer.deleteTask(CMSCon, task);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }
}

