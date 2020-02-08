/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.transactionexp.businesslogic;

import com.epic.cms.reportexp.transactionexp.bean.TxnExpDetailsBean;
import com.epic.cms.reportexp.transactionexp.bean.TxnExpHistoryViewBean;
import com.epic.cms.reportexp.transactionexp.bean.TxnExpSearchBean;
import com.epic.cms.reportexp.transactionexp.persistance.TransactionExpMgtPersistance;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class TransactionExpMgtManager {

    private Connection CMSCon;
    private HashMap<String, String> txnTypeList = null;
    private HashMap<String, String> txnStatusList = null;
    private TransactionExpMgtPersistance txnExpPersist = null;
    private List<TxnExpDetailsBean> txnExpDetailBeanList = null;
    private List<TxnExpHistoryViewBean> txnExpHistoryViewBeanList = null;

    public HashMap<String, String> getAllTransactionTypes() throws Exception {
        try {
            txnExpPersist = new TransactionExpMgtPersistance();
            CMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            txnTypeList = txnExpPersist.getAllTransactionTypes(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
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
        return txnTypeList;
    }

    public HashMap<String, String> getAllTransactionStatus() throws Exception {
        try {
            txnExpPersist = new TransactionExpMgtPersistance();
            CMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            txnStatusList = txnExpPersist.getAllTransactionStatus(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
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
        return txnStatusList;
    }

    public List<TxnExpDetailsBean> getAllTxnExpDetails(TxnExpSearchBean searchBean) throws Exception {
        try {
            txnExpPersist = new TransactionExpMgtPersistance();
            CMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            txnExpDetailBeanList = txnExpPersist.getAllTxnExpDetails(CMSCon, searchBean);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
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
        return txnExpDetailBeanList;
    }

    public List<TxnExpDetailsBean> getTxnExpDetails(String condition, int start, int end) throws Exception {
        try {
            txnExpPersist = new TransactionExpMgtPersistance();
            CMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            txnExpDetailBeanList = txnExpPersist.getTxnExpDetails(CMSCon, condition, start, end);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
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
        return txnExpDetailBeanList;
    }

    public List<TxnExpHistoryViewBean> getAllTxnHistoryViewDetails(String txnId) throws Exception {
        try {
            txnExpPersist = new TransactionExpMgtPersistance();
            CMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            txnExpHistoryViewBeanList = txnExpPersist.getAllTxnHistoryViewDetails(CMSCon, txnId);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
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
        return txnExpHistoryViewBeanList;
    }

    public int getCountTxn(String condition) throws Exception {
        int count;
        try {
            txnExpPersist = new TransactionExpMgtPersistance();
            CMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            count = txnExpPersist.getCountTxn(CMSCon, condition);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
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
        return count;
    }
}
