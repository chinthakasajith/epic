/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ChannelIncomeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.OnlineTxnTypeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.persistance.TxnTypeMgtPersistance;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
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
public class TxnTypeMgtManager {

    private static TxnTypeMgtManager setInstance;
    private TxnTypeMgtPersistance txnTypePer;
    private Connection CMSCon;
    private Connection CMSOnline;
    private SystemAuditManager sysAuditManager;
    private List<TypeMgtBean> txnTypeDetailList = null;
    private List<OnlineTxnTypeBean> onlineTxnTypeList = null;
    private List<ChannelTypeBean> onlineChannelList = null;
    private List<ChannelIncomeBean> incomeChannelList = null;

    /**
     * 
     * @return 
     */
    public synchronized static TxnTypeMgtManager getTxnTypeManager() {
        if (setInstance == null) {
            setInstance = new TxnTypeMgtManager();
        }
        return setInstance;
    }

    /**
     * 
     * @return
     * @throws Exception 
     */
    public List<TypeMgtBean> getTxnTypeDetails() throws Exception {
        try {
            txnTypeDetailList = new ArrayList<TypeMgtBean>();
            txnTypePer = new TxnTypeMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            txnTypeDetailList = txnTypePer.getTxnTypeDetails(CMSCon);


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
        return txnTypeDetailList;
    }

    /**
     * 
     * @param transactionTypeCode
     * @return
     * @throws Exception 
     */
    public List<ChannelIncomeBean> getChannelToUpdate(String transactionTypeCode) throws Exception {

        try {

            incomeChannelList = new ArrayList<ChannelIncomeBean>();
            txnTypePer = new TxnTypeMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            incomeChannelList = txnTypePer.getChannelToUpdate(CMSCon, transactionTypeCode);

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

        return incomeChannelList;
    }

    /**
     * 
     * @return
     * @throws Exception 
     */
    public List<OnlineTxnTypeBean> getOnlineTxnType() throws Exception {

        try {
            onlineTxnTypeList = new ArrayList<OnlineTxnTypeBean>();
            txnTypePer = new TxnTypeMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSOnline.setAutoCommit(false);
            onlineTxnTypeList = txnTypePer.getOnlineTxnType(CMSOnline);


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
        return onlineTxnTypeList;
    }

    /**
     * 
     * @return
     * @throws Exception 
     */
    public List<ChannelTypeBean> getOnlineChannel() throws Exception {

        try {
            onlineChannelList = new ArrayList<ChannelTypeBean>();
            txnTypePer = new TxnTypeMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSOnline.setAutoCommit(false);
            onlineChannelList = txnTypePer.getOnlineChannel(CMSOnline);


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

    /**
     * 
     * @param type
     * @return
     * @throws Exception 
     */
    public boolean insertTxntypeDetails(TypeMgtBean type, List<ChannelIncomeBean> channelList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        boolean isSuccess = false;
        boolean isOnlineSuccess = false;
        boolean allSuccess = false;

        try {
            txnTypePer = new TxnTypeMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            CMSOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);


            success = txnTypePer.insertTxnType(CMSCon, type);
            //isSuccess = txnTypePer.insertChannel(CMSCon, channelList);

//            for (int i = 0; i < channelList.size(); i++) {
//                if (channelList.get(i) != null) {
//
//                    if ((channelList.get(i).getStatus()).equals("ACT")) {
//                        channelList.get(i).setStatusToOnline(1);
//                    } else if ((channelList.get(i).getStatus()).equals("DEA")) {
//                        channelList.get(i).setStatusToOnline(0);
//                    }
//                }
//            }
//            if (!channelList.isEmpty()) {
//                String description = txnTypePer.getTxnDescriptionByCode(CMSOnline, channelList.get(0).getOnlineTxnCode());
//
//                if (description != null) {
//                    for (int i = 0; i < channelList.size(); i++) {
//
//                        channelList.get(i).setTxnDescription(description);
//                    }
//                }
//            }
//            isOnlineSuccess = txnTypePer.insertChannelToOnline(CMSOnline, channelList);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        if (success) {
            allSuccess = true;
        }
        return allSuccess;
    }

    /**
     * 
     * @param type
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean updateTxnTypeDetails(TypeMgtBean type, List<ChannelIncomeBean> channelList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
//        boolean successDlt = false;
//        boolean successDltOnline = false;

        try {
            txnTypePer = new TxnTypeMgtPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            success = txnTypePer.updateTxnType(CMSCon, type);


//            successDlt = txnTypePer.deleteChannel(CMSCon, type.getTransactionTypeCode());
//
//            if (successDlt) {
//
//                txnTypePer.insertChannel(CMSCon, channelList);
//
//            }
//
//            successDltOnline = txnTypePer.deleteChannelOnline(CMSOnline, type.getOnlineTxnCode());
//
//            if (successDltOnline) {
//
//                for (int i = 0; i < channelList.size(); i++) {
//                    if (channelList.get(i) != null) {
//
//                        if ((channelList.get(i).getStatus()).equals("ACT")) {
//                            channelList.get(i).setStatusToOnline(1);
//                        } else if ((channelList.get(i).getStatus()).equals("DEA")) {
//                            channelList.get(i).setStatusToOnline(0);
//                        }
//                    }
//                }
//                String description = txnTypePer.getTxnDescriptionByCode(CMSOnline, type.getOnlineTxnCode());
//
//                if (description != null) {
//                    for (int i = 0; i < channelList.size(); i++) {
//
//                        channelList.get(i).setTxnDescription(description);
//                    }
//                }
//
//                txnTypePer.insertChannelToOnline(CMSOnline, channelList);
//
//            }


            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
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
    public boolean deleteTxnTypeDetails(TypeMgtBean txnType, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            txnTypePer = new TxnTypeMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = txnTypePer.deleteTxnType(CMSCon, txnType);
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
    public boolean checkDataExsistence(ChannelIncomeBean incomeBean) throws Exception{
    boolean success = false;
    
        try {
            
            txnTypePer = new TxnTypeMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSOnline.setAutoCommit(false);
            
            success = txnTypePer.checkDataExsistence(CMSOnline, incomeBean);
            
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
    return success;
    
    }
}
