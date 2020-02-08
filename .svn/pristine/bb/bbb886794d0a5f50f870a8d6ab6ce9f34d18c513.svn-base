/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.serverconfig.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.persistance.ChannelTxnMgtPersistance;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
import com.epic.cms.switchcontrol.serverconfig.bean.ServerMainConfigBean;
import com.epic.cms.switchcontrol.serverconfig.persistance.ServerMainConfigOnlinePersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class handles logic which is relevant to ECMS_ONLINE_CONFIG table
 * @author nisansala
 */
public class ServerMainConfigManager {
    
    //declaring variables
    private Connection CMSCon;
    private Connection onlineCMSCon;
    ServerMainConfigOnlinePersistance serverMainCnfgOnlinePer;
    private SystemAuditManager sysAuditManager;
    ServerMainConfigBean serverConfigBean;
    
    private HashMap<String, String> pinbFormat = null;
    private HashMap<String, String> emvVerifyMethod = null;
    private HashMap<String, String> encrMode = null;
    private HashMap<String, String> enableMode = null;
    private List<ChannelTypeBean> onlineChannelList = null;
    private Map<String,String> channelStatusMap;

    public ServerMainConfigBean getAllServerMainConfigDetails() throws SQLException, Exception {
        try {
            
            //create a connection to the backend database
            CMSCon = DBConnection.getConnection();
            //create a connection to the online database
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            
            serverMainCnfgOnlinePer = new ServerMainConfigOnlinePersistance();
            //serverMainCnfgPer = new ServerMainConfigPersistance();
            serverConfigBean = new ServerMainConfigBean();
            onlineCMSCon.setAutoCommit(false);            
            //call the persistance class to retrieve data
            serverConfigBean = serverMainCnfgOnlinePer.getAllServerMainConfigData(onlineCMSCon);
            onlineCMSCon.commit();
            return serverConfigBean;
            
        } catch (Exception ex) {
            
            
            //
            try {
                onlineCMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
            
        } finally {
            
            if (onlineCMSCon != null) {
                try {
                    onlineCMSCon.close();
                } catch (SQLException sqx) {
                    throw sqx;
                }
            }
            
        }
    }
    /**
     * update the server main configuration data
     * @param serverConfigeBean
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public Boolean insertNewServerMainConfigDetails(ServerMainConfigBean serverConfigeBean,SystemAuditBean systemAuditBean) throws SQLException, Exception{
        try{
            
            Boolean success = false;
            CMSCon = DBConnection.getConnection();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            
            serverMainCnfgOnlinePer = new ServerMainConfigOnlinePersistance();
            CMSCon.setAutoCommit(false);
            onlineCMSCon.setAutoCommit(false);
            
            success = serverMainCnfgOnlinePer.updateServerMainConfigData(serverConfigeBean,  onlineCMSCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            onlineCMSCon.commit();
        
            return success;
        
        } catch (SQLException ex) {
            
            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                onlineCMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (onlineCMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    onlineCMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
    
    public HashMap<String, String> getPinbFormat() throws Exception {

        try {
            serverMainCnfgOnlinePer = new ServerMainConfigOnlinePersistance();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            onlineCMSCon.setAutoCommit(false);

            pinbFormat = serverMainCnfgOnlinePer.getPinbFormat(onlineCMSCon);

            onlineCMSCon.commit();

        } catch (SQLException ex) {
            try {
                onlineCMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                onlineCMSCon.rollback();
                throw ex;
            }
        } finally {
            onlineCMSCon.close();
        }
        return pinbFormat;
    }
    
    public HashMap<String, String> getEmvVerifyMethod() throws Exception {

        try {
            serverMainCnfgOnlinePer = new ServerMainConfigOnlinePersistance();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            onlineCMSCon.setAutoCommit(false);

            emvVerifyMethod = serverMainCnfgOnlinePer.getEmvVerifyMethod(onlineCMSCon);

            onlineCMSCon.commit();

        } catch (SQLException ex) {
            try {
                onlineCMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                onlineCMSCon.rollback();
                throw ex;
            }
        } finally {
            onlineCMSCon.close();
        }
        return emvVerifyMethod;
    }
    
    public HashMap<String, String> getEncrMode() throws Exception {

        try {
            serverMainCnfgOnlinePer = new ServerMainConfigOnlinePersistance();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            onlineCMSCon.setAutoCommit(false);

            encrMode = serverMainCnfgOnlinePer.getEncrMode(onlineCMSCon);

            onlineCMSCon.commit();

        } catch (SQLException ex) {
            try {
                onlineCMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                onlineCMSCon.rollback();
                throw ex;
            }
        } finally {
            onlineCMSCon.close();
        }
        return encrMode;
    }
    
    public HashMap<String, String> getEnableMode() throws Exception {

        try {
            serverMainCnfgOnlinePer = new ServerMainConfigOnlinePersistance();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            onlineCMSCon.setAutoCommit(false);

            enableMode = serverMainCnfgOnlinePer.getEnableMode(onlineCMSCon);

            onlineCMSCon.commit();

        } catch (SQLException ex) {
            try {
                onlineCMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                onlineCMSCon.rollback();
                throw ex;
            }
        } finally {
            onlineCMSCon.close();
        }
        return enableMode;
    }
    
    /**
     * load all active channels
     * @return
     * @throws Exception 
     */
    public List<ChannelTypeBean> getOnlineChannel() throws Exception {

        try {
            onlineChannelList = new ArrayList<ChannelTypeBean>();
            serverMainCnfgOnlinePer = new ServerMainConfigOnlinePersistance();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            onlineCMSCon.setAutoCommit(false);
            onlineChannelList = serverMainCnfgOnlinePer.getOnlineChannel(onlineCMSCon);
            onlineCMSCon.commit();

        } catch (SQLException ex) {
            try {
                onlineCMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                onlineCMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(onlineCMSCon);
        }
        return onlineChannelList;
    }
    public Map<String,String> getOnlineChannelStatusMap() throws Exception {

        try {
            onlineChannelList = new ArrayList<ChannelTypeBean>();
            serverMainCnfgOnlinePer = new ServerMainConfigOnlinePersistance();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            onlineCMSCon.setAutoCommit(false);
            channelStatusMap = serverMainCnfgOnlinePer.getOnlineChannelStatusMap(onlineCMSCon);
            onlineCMSCon.commit();

        } catch (SQLException ex) {
            try {
                onlineCMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                onlineCMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(onlineCMSCon);
        }
        return channelStatusMap;
    }    
}
