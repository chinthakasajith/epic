/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.logmanagement.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.switchcontrol.logmanagement.bean.LogLevelBean;
import com.epic.cms.switchcontrol.logmanagement.bean.LogMgtBean;
import com.epic.cms.switchcontrol.logmanagement.persistance.LogMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class LogMgtManager {

    private Connection CMSConOnline;
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private LogMgtBean logBean = null;
    private List<LogLevelBean> logLevelList = null;
    private LogMgtPersistance logPer = null;

    public List<LogLevelBean> getLogLevel() throws Exception {
        try {
            logLevelList = new ArrayList<LogLevelBean>();
            logPer = new LogMgtPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            logLevelList = logPer.getLogLevel(CMSConOnline);


            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return logLevelList;
    }

    public LogMgtBean getLogManagementData() throws Exception {
        try {
            logBean = new LogMgtBean();
            logPer = new LogMgtPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            logBean = logPer.getLogManagementData(CMSConOnline);


            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return logBean;
    }

    public boolean updateLogManagementDetails(LogMgtBean logBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            logPer = new LogMgtPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = logPer.updateLogManagementDetails(CMSConOnline, logBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return success;
    }
}
