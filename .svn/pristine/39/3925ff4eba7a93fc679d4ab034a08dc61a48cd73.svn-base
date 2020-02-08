/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.listenerconfig.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.switchcontrol.chanelconfig.bean.StatusTypeBean;
import com.epic.cms.switchcontrol.listenerconfig.bean.ListenerConfigurationBean;
import com.epic.cms.switchcontrol.listenerconfig.bean.ListenerTypeBean;
import com.epic.cms.switchcontrol.listenerconfig.persistance.ListenerConfigurationPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nalin
 */
public class ListenerConfigurationManager {

    private Connection CMSCon;
    private Connection CMSConOnline;
    private SystemAuditManager sysAuditManager;
    private List<ListenerTypeBean> listenerTypeList = null;
    private ListenerConfigurationPersistance listnPer = null;
    private List<StatusTypeBean> statusList = null;
    private List<ListenerConfigurationBean> listenerList = null;
    private HashMap<String, String> runningModeList = null;
    private HashMap<String, String> keyId = null;

    public List<ListenerTypeBean> getListenerType() throws Exception {
        try {
            listenerTypeList = new ArrayList<ListenerTypeBean>();
            listnPer = new ListenerConfigurationPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            listenerTypeList = listnPer.getListenerType(CMSConOnline);

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
        return listenerTypeList;
    }

    public HashMap<String, String> getRunningMode() throws Exception {
        try {
            runningModeList = new HashMap<String, String>();
            listnPer = new ListenerConfigurationPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            runningModeList = listnPer.getRunningMode(CMSConOnline);

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
        return runningModeList;
    }

    public List<StatusTypeBean> getStatusType() throws Exception {
        try {
            statusList = new ArrayList<StatusTypeBean>();
            listnPer = new ListenerConfigurationPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            statusList = listnPer.getStatusType(CMSConOnline);



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
        return statusList;
    }

    public HashMap<String, String> getKeyId(String type) throws Exception {
        try {
            keyId = new HashMap<String, String>();
            listnPer = new ListenerConfigurationPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();

            CMSConOnline.setAutoCommit(false);

            keyId = listnPer.getKeyId(CMSConOnline, type);

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
        return keyId;
    }

    public List<ListenerConfigurationBean> getAllListenerDetails() throws Exception {
        try {
            listenerList = new ArrayList<ListenerConfigurationBean>();
            listnPer = new ListenerConfigurationPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            listenerList = listnPer.getAllListenerDetails(CMSConOnline);


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
        return listenerList;
    }

    public boolean listenerInsert(ListenerConfigurationBean listenerBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;

        try {
            listnPer = new ListenerConfigurationPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = listnPer.listenerInsert(CMSConOnline, listenerBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSConOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    public boolean listenerUpdate(ListenerConfigurationBean listenerBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;

        try {
            listnPer = new ListenerConfigurationPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = listnPer.listenerUpdate(CMSConOnline, listenerBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSConOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    public boolean listenerDelete(ListenerConfigurationBean listenerBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;

        try {
            listnPer = new ListenerConfigurationPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = listnPer.listenerDelete(CMSConOnline, listenerBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSConOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }
}
