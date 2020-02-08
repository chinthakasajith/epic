/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.card.businesslogic;

import com.epic.cms.callcenter.card.bean.CardBean;
import com.epic.cms.callcenter.card.persistance.OnlineBalancePersistance;
import com.epic.cms.reportexp.transactionexp.bean.TxnExpDetailsBean;
import com.epic.cms.reportexp.transactionexp.bean.TxnExpHistoryViewBean;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author badrika
 */
public class OnlineBalanceManager {

    private OnlineBalancePersistance perObj;
    private Connection onlineCon;
    private List<TxnExpDetailsBean> txnExpDetailBeanList;
    private List<TxnExpHistoryViewBean> txnExpHistoryViewBeanList;

    public CardBean getOnlineBalance(String cardNum) throws SQLException, Exception {

        try {
            CardBean cbean = new CardBean();
            perObj = new OnlineBalancePersistance();
            onlineCon = DBConnectionToOnlineDB.getConnection();
            onlineCon.setAutoCommit(false);
            cbean = perObj.getOnlineBalance(onlineCon, cardNum);
            onlineCon.commit();
            return cbean;
        } catch (Exception e) {
            try {
                onlineCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (onlineCon != null) {
                try {
                    onlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
    
     public int getCountTxn(String condition) throws Exception {
        int count;
        try {
            perObj = new OnlineBalancePersistance();
            onlineCon = DBConnectionToOnlineDB.getConnection();
            onlineCon.setAutoCommit(false);
            count = perObj.getCountTxn(onlineCon, condition);
            onlineCon.commit();
        } catch (Exception ex) {
            try {
                onlineCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (onlineCon != null) {
                try {
                    onlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return count;
    }
     
         public List<TxnExpDetailsBean> getTxnExpDetails(String condition, int start, int end) throws Exception {
        try {
            perObj = new OnlineBalancePersistance();
            onlineCon = DBConnectionToOnlineDB.getConnection();
            onlineCon.setAutoCommit(false);
            txnExpDetailBeanList = perObj.getTxnExpDetails(onlineCon, condition, start, end);
            onlineCon.commit();
        } catch (Exception ex) {
            try {
                onlineCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (onlineCon != null) {
                try {
                    onlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return txnExpDetailBeanList;
    }
         
           public List<TxnExpHistoryViewBean> getAllTxnHistoryViewDetails(String txnId) throws Exception {
        try {
            perObj = new OnlineBalancePersistance();
            onlineCon = DBConnectionToOnlineDB.getConnection();
            onlineCon.setAutoCommit(false);
            txnExpHistoryViewBeanList = perObj.getAllTxnHistoryViewDetails(onlineCon, txnId);
            onlineCon.commit();
        } catch (Exception ex) {
            try {
                onlineCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (onlineCon != null) {
                try {
                    onlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return txnExpHistoryViewBeanList;
    }
     
}
