/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.chanelconfig.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelConfigBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ConnectionTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.StatusTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.persistance.ChannelConfigPersistance;
import com.epic.cms.switchcontrol.chanelconfig.persistance.StatusPersistence;
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
public class ChannelConfigManager {

    private static ChannelConfigManager setInstance;
    private ChannelConfigPersistance channeConfigPer;
    private StatusPersistence statusPer;
    private Connection CMSCon;
    private Connection CMSConOnline;
    private SystemAuditManager sysAuditManager;
    private List<ChannelConfigBean> channelList = null;
    private List<ConnectionTypeBean> connectionTypeList = null;
    private List<ChannelTypeBean> channelTypeList = null;
    private List<StatusTypeBean> statusList = null;
    private List<StatusTypeBean> statusEDList = null;

    /**
     * 
     * @return 
     */
    public synchronized static ChannelConfigManager getChannelConfigManager() {
        if (setInstance == null) {
            setInstance = new ChannelConfigManager();
        }
        return setInstance;
    }

    /**
     * 
     * @return
     * @throws Exception 
     */
    public List<ChannelConfigBean> getChannel() throws Exception {
        try {
            channelList = new ArrayList<ChannelConfigBean>();
            channeConfigPer = new ChannelConfigPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            channelList = channeConfigPer.getChannelOnline(CMSConOnline);


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
        return channelList;
    }

    /**
     * 
     * @param type
     * @return
     * @throws Exception 
     */
    public boolean insertChannel(ChannelConfigBean channel, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;

        try {
            channeConfigPer = new ChannelConfigPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = channeConfigPer.insertChannelOnline(CMSConOnline, channel);
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

    /**
     * 
     * @param type
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean updateChannel(ChannelConfigBean channel, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;

        try {
            channeConfigPer = new ChannelConfigPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = channeConfigPer.updateChannelOnline(CMSConOnline, channel);
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

    /**
     * 
     * @param txnType
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean deleteChannel(ChannelConfigBean channel, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            channeConfigPer = new ChannelConfigPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = channeConfigPer.deleteChannelOnline(CMSConOnline, channel);
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

    public List<ConnectionTypeBean> getConnectionTypeStatus() throws Exception {
        try {
            connectionTypeList = new ArrayList<ConnectionTypeBean>();
            statusPer = new StatusPersistence();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            connectionTypeList = statusPer.getConnectionTypeStatus(CMSConOnline);


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
        return connectionTypeList;
    }

    public List<ChannelTypeBean> getChannelTypeStatus() throws Exception {
        try {
            channelTypeList = new ArrayList<ChannelTypeBean>();
            statusPer = new StatusPersistence();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            channelTypeList = statusPer.getChannelTypeStatus(CMSConOnline);


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
        return channelTypeList;
    }

    public List<StatusTypeBean> getStatusType() throws Exception {
        try {
            statusList = new ArrayList<StatusTypeBean>();
            statusPer = new StatusPersistence();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            statusList = statusPer.getStatusType(CMSConOnline);



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

    public List<StatusTypeBean> getStatusEDType() throws Exception {
        try {
            statusEDList = new ArrayList<StatusTypeBean>();
            statusPer = new StatusPersistence();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            statusEDList = statusPer.getStatusEDType(CMSConOnline);



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
        return statusEDList;
    }
}
