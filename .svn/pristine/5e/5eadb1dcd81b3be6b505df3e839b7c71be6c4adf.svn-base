/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ChannelIncomeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ListenerTxnBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.OnlineTxnTypeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.persistance.ChannelTxnMgtPersistance;
import com.epic.cms.admin.controlpanel.transactionmgt.persistance.ListenerTxnMgtPersistance;
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
public class ListenerTxnMgtManager {
    
    private static TxnTypeMgtManager setInstance;
    private Connection CMSCon;
    private Connection CMSOnline;
    private SystemAuditManager sysAuditManager;
    private ListenerTxnMgtPersistance listenPer;
    private List<OnlineTxnTypeBean> onlineTxnTypeList = null;
    private List<ChannelTypeBean> onlineChannelList = null;
    private List<ChannelIncomeBean> incomeChannelList = null;
    private List<ListenerTxnBean> listenList = null;
    private List<OnlineTxnTypeBean> assignList;
    private List<OnlineTxnTypeBean> notAssignList;
    
    public List<ListenerTxnBean> getAllListenerTypes() throws Exception {
        
        try {
            onlineChannelList = new ArrayList<ChannelTypeBean>();
            listenPer = new ListenerTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSOnline.setAutoCommit(false);
            listenList = listenPer.getAllListenerTypes(CMSOnline);
            
            
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
        return listenList;
    }
    
    public List<ListenerTxnBean> getUnusedListeners() throws Exception {
        
        try {
            onlineChannelList = new ArrayList<ChannelTypeBean>();
            listenPer = new ListenerTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSOnline.setAutoCommit(false);
            listenList = listenPer.getUnusedListeners(CMSOnline);            
            
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
        return listenList;
    }
    
    public List<ListenerTxnBean> getUnusedListenersUpdate(String listenerId) throws Exception {
        
        try {
            onlineChannelList = new ArrayList<ChannelTypeBean>();
            listenPer = new ListenerTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSOnline.setAutoCommit(false);
            listenList = listenPer.getUnusedListenersUpdate(CMSOnline,listenerId);            
            
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
        return listenList;
    }
    
    public Boolean insertListenerTxn(String listener, String[] assignList, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {

            //if the insert become success row will return 1
            int success1 = -1;
            boolean success2 = false;
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            listenPer = new ListenerTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            
            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);
            
            success2 = listenPer.insertListenerTxn(listener, assignList, CMSOnline);
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
                    DBConnection.dbConnectionClose(CMSCon);
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (CMSOnline != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
                } catch (SQLException e) {
                    throw e;
                }
            }
            return success;
        }
    }
    
    public boolean updateListenerTxn(String listener, String[] assignList, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {

            //if the insert become success row will return 1
            int success1 = -1;
            boolean success2 = false;
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            listenPer = new ListenerTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            
            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);
            
            success2 = listenPer.updateListenerTxn(listener, assignList, CMSOnline);
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
    
    public int deleteListenerTxn(String channel, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int success = -1;
        try {

            //if the insert become success row will return 1
            int success1, success2 = -1;
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            listenPer = new ListenerTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            
            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);
            
            success2 = listenPer.deleteListenerTxn(CMSOnline, channel);
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
                    DBConnection.dbConnectionClose(CMSCon);
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (CMSOnline != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
                } catch (SQLException e) {
                    throw e;
                }
            }
            return success;
        }
    }
    
    public List<ListenerTxnBean> getAllListenerTxn() throws SQLException, Exception {
        List<ListenerTxnBean> beanList = null;
        
        try {
            listenPer = new ListenerTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            
            
            CMSOnline.setAutoCommit(false);
            
            beanList = listenPer.getAllListenerTxn(CMSOnline);
            
            
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
                   DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
                } catch (SQLException e) {
                    throw e;
                }
            }
            return beanList;
        }
    }
    
    public List<OnlineTxnTypeBean> getAssignTxn(String listenerId) throws SQLException {
        
        
        try {
            listenPer = new ListenerTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            
            CMSOnline.setAutoCommit(false);
            
            assignList = listenPer.getAssignTxn(CMSOnline, listenerId);
            
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
    
    public List<OnlineTxnTypeBean> getNotAssignTxn(String listenerId) throws SQLException {
        try {
            listenPer = new ListenerTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            
            CMSOnline.setAutoCommit(false);
            
            notAssignList = listenPer.getNotAssignTxn(CMSOnline, listenerId);
            
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

    public List<ChannelTypeBean> getAllTxnType() throws SQLException, Exception {


        try {
            listenPer = new ListenerTxnMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSOnline.setAutoCommit(false);

            onlineChannelList = listenPer.getAllTxnType(CMSOnline);

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
