/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ChannelIncomeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.OnlineTxnTypeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.persistance.ChannelTxnMgtPersistance;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class ChannelTxnMgtManager {

    private Connection CMSCon;
    private Connection CMSOnline;
    private List<ChannelIncomeBean> beanList;
    private SystemAuditManager sysAuditManager;
    private ChannelTxnMgtPersistance channelPer;
    private List<OnlineTxnTypeBean> assignList;
    private List<OnlineTxnTypeBean> notAssignList;
    private List<ChannelTypeBean> onlineChannelList = null;

    public boolean insertChannelTxn(String channel, String[] assignList, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success2, success = false;
        try {

            //if the insert become success row will return 1
            int success1 = -1;

            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            channelPer = new ChannelTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            success2 = channelPer.insertChannelTxn(channel, assignList, CMSOnline);
            success1 = sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

            if (success2 && success1 == 1) {
                success = true;
            }


        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
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
            if (CMSOnline != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            return success;
        }
    }

    public boolean updateChannelTxn(String channel, String[] assignList, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success2, success = false;
        try {

            //if the insert become success row will return 1
            int success1 = -1;

            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            channelPer = new ChannelTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            success2 = channelPer.updateChannelTxn(channel, assignList, CMSOnline);
            success1 = sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

            if (success2 && success1 == 1) {
                success = true;
            }


        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
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
            if (CMSOnline != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            return success;
        }
    }

    public int deleteChannelTxn(String channel, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int success1, success2, success = -1;
        try {

            //if the insert become success row will return 1

            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            channelPer = new ChannelTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            success2 = channelPer.deleteChannelTxn(CMSOnline, channel);
            success1 = sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

            if (success2 == 1 && success1 == 1) {
                success = 1;
            }


        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
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
            if (CMSOnline != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            return success;
        }
    }

    public List<ChannelIncomeBean> getAllChannelTxn() throws SQLException, Exception {
        

        try {
            channelPer = new ChannelTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();


            CMSOnline.setAutoCommit(false);

            beanList = channelPer.getAllChannelTxn(CMSOnline);


            CMSOnline.commit();



        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {

                CMSOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {

            if (CMSOnline != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            return beanList;
        }
    }

    public List<OnlineTxnTypeBean> getAssignTxn(String channelId) throws SQLException, Exception {


        try {
            channelPer = new ChannelTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSOnline.setAutoCommit(false);

            assignList = channelPer.getAssignTxn(CMSOnline, channelId);

            CMSOnline.commit();



        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {

                CMSOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {

            if (CMSOnline != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            return assignList;
        }
    }

    public List<OnlineTxnTypeBean> getNotAssignTxn(String channelId) throws SQLException, Exception {
        try {
            channelPer = new ChannelTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSOnline.setAutoCommit(false);

            notAssignList = channelPer.getNotAssignTxn(CMSOnline, channelId);

            CMSOnline.commit();



        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {

                CMSOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {

            if (CMSOnline != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            return notAssignList;
        }
    }

    public List<ChannelTypeBean> getOnlineChannel() throws Exception {

        try {
            onlineChannelList = new ArrayList<ChannelTypeBean>();
            channelPer = new ChannelTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSOnline.setAutoCommit(false);
            
            onlineChannelList = channelPer.getOnlineChannel(CMSOnline);
            
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return onlineChannelList;
    }
    
    public List<ChannelTypeBean> getUnusedChannels() throws Exception {

        try {
            onlineChannelList = new ArrayList<ChannelTypeBean>();
            channelPer = new ChannelTxnMgtPersistance();
            
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSOnline.setAutoCommit(false);
            
            onlineChannelList = channelPer.getUnusedChannels(CMSOnline);  
            
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return onlineChannelList;
    }
    
    public List<ChannelTypeBean> getUnusedChannelsInUpdate(String channelId) throws Exception {

        try {
            onlineChannelList = new ArrayList<ChannelTypeBean>();
            channelPer = new ChannelTxnMgtPersistance();
            
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSOnline.setAutoCommit(false);
            
            onlineChannelList = channelPer.getUnusedChannelsInUpdate(CMSOnline,channelId);  
            
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return onlineChannelList;
    }
    
    public List<ChannelTypeBean> getAllTxnType() throws SQLException, Exception {


        try {
            channelPer = new ChannelTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSOnline.setAutoCommit(false);

            onlineChannelList = channelPer.getAllTxnType(CMSOnline);

            CMSOnline.commit();



        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {

                CMSOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {

            if (CMSOnline != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            return onlineChannelList;
        }
    }    
    
}
