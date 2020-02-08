/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.card.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.callcenter.callcentersearch.bean.CallHistoryBean;
import com.epic.cms.callcenter.callcentersearch.businesslogic.CallCenterMgtManager;
import com.epic.cms.callcenter.card.persistance.CardTxnMgtPersistance;
import com.epic.cms.callcenter.customer.persistance.CallCenterCustomerOperationPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nalin
 */
public class CardTxnMgtManager {
    
    private CardTxnMgtPersistance cardPersis = null;
    private CallCenterCustomerOperationPersistance customerPersis = null;
    private List<TypeMgtBean> allTxnList = null;
    private List<TypeMgtBean> notAssignedTxnList = null;
    private List<TypeMgtBean> assignedTxnList = null;
    private List<String> notAssignedOnlineTxnList = null;
    private List<String> cardOnlineTxnList = null;
    private Connection CMSCon = null;
    private Connection CMSOnline = null;
    private SystemAuditManager sysAuditManager = null;
    private CallCenterMgtManager callCenterManager;
    
    public List<TypeMgtBean> getNotAssignedTxnType(String cardNo) throws Exception {
        try {
            allTxnList = new ArrayList<TypeMgtBean>();
            notAssignedTxnList = new ArrayList<TypeMgtBean>();
            notAssignedOnlineTxnList = new ArrayList<String>();
            customerPersis = new CallCenterCustomerOperationPersistance();
            cardPersis = new CardTxnMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            
            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);
            
            allTxnList = customerPersis.getAllTxnType(CMSCon);
            notAssignedOnlineTxnList = cardPersis.getNotAssignedCardTxnTypeFromOnline(CMSOnline, cardNo);
            
            CMSCon.commit();
            CMSOnline.commit();
            
            for (TypeMgtBean bean : allTxnList) {
                
                if (notAssignedOnlineTxnList.contains(bean.getOnlineTxnCode())) {
                    notAssignedTxnList.add(bean);
                }
            }
            
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
        return notAssignedTxnList;
    }
    
    public List<TypeMgtBean> getAssignedTxnType(String cardNo) throws Exception {
        try {
            allTxnList = new ArrayList<TypeMgtBean>();
            cardOnlineTxnList = new ArrayList<String>();
            assignedTxnList = new ArrayList<TypeMgtBean>();
            customerPersis = new CallCenterCustomerOperationPersistance();
            cardPersis = new CardTxnMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            
            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);
            
            allTxnList = customerPersis.getAllTxnType(CMSCon);
            cardOnlineTxnList = cardPersis.getCardTxnTypeFromOnline(CMSOnline, cardNo);
            
            CMSCon.commit();
            CMSOnline.commit();
            
            for (TypeMgtBean bean : allTxnList) {
                if (cardOnlineTxnList.contains(bean.getOnlineTxnCode())) {
                    assignedTxnList.add(bean);
                }
            }
            
            
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
        return assignedTxnList;
    }
    
    public boolean updateCardTxn(String[] assignedTxnTypeList, String cardNo, SystemAuditBean systemAuditBean, CallHistoryBean callhistoryBean) throws Exception {
        boolean success = false;
        try {
            
            List<String> txnList = new ArrayList<String>();
            List<String> onlineTxnList = new ArrayList<String>();
            customerPersis = new CallCenterCustomerOperationPersistance();
            cardPersis = new CardTxnMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            callCenterManager = new CallCenterMgtManager();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            
            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);
            
            txnList.addAll(Arrays.asList(assignedTxnTypeList));
            
            success = cardPersis.updateCardTxn(CMSCon, txnList, cardNo);
            
            for (String txn : txnList) {
                onlineTxnList.add(customerPersis.getOnlineTxnCode(CMSCon, txn));
            }
            success = cardPersis.updateOnlineCardTxn(CMSOnline, onlineTxnList, cardNo);
            
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            callCenterManager.insertCallHistory(callhistoryBean, CMSCon);
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
}
