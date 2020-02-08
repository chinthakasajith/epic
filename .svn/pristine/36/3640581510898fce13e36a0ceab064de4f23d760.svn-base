/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.account.businesslogic;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.callcenter.account.persistance.CallCenterAccountOperationPersistance;
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
public class CallCenterAccountOperationManager {

    private CallCenterAccountOperationPersistance accountPersis = null;
    private CallCenterCustomerOperationPersistance customerPersis = null;
    private List<TypeMgtBean> allTxnList = null;
    private List<TypeMgtBean> notAssignedTxnList = null;
    private List<TypeMgtBean> assignedTxnList = null;
    private List<String> notAssignedOnlineTxnList = null;
    private List<String> accountOnlineTxnList = null;
    private List<String> currentTxnList = null;
    private List<String> deleteTxnList = null;
    private List<String> jointTxnList = null;
    private Connection CMSCon = null;
    private Connection CMSOnline = null;

    public List<TypeMgtBean> getNotAssignedTxnType(String accountId) throws Exception {
        try {
            allTxnList = new ArrayList<TypeMgtBean>();
            notAssignedTxnList = new ArrayList<TypeMgtBean>();
            notAssignedOnlineTxnList = new ArrayList<String>();
            customerPersis = new CallCenterCustomerOperationPersistance();
            accountPersis = new CallCenterAccountOperationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            allTxnList = customerPersis.getAllTxnType(CMSCon);
            notAssignedOnlineTxnList = accountPersis.getNotAssignedAccountTxnTypeFromOnline(CMSOnline, accountId);

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

    public List<TypeMgtBean> getAssignedTxnType(String accountId) throws Exception {
        try {
            allTxnList = new ArrayList<TypeMgtBean>();
            accountOnlineTxnList = new ArrayList<String>();
            assignedTxnList = new ArrayList<TypeMgtBean>();
            customerPersis = new CallCenterCustomerOperationPersistance();
            accountPersis = new CallCenterAccountOperationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            allTxnList = customerPersis.getAllTxnType(CMSCon);
            accountOnlineTxnList = accountPersis.getAccountTxnTypeFromOnline(CMSOnline, accountId);

            CMSCon.commit();
            CMSOnline.commit();

            for (TypeMgtBean bean : allTxnList) {
                if (accountOnlineTxnList.contains(bean.getOnlineTxnCode())) {
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

    public boolean updateAccountTxn(String[] assignedTxnTypeList, String accountNo) throws Exception {
        boolean success = false;
        try {

            List<String> txnList = new ArrayList<String>();
            List<String> onlineTxnList = new ArrayList<String>();
            customerPersis = new CallCenterCustomerOperationPersistance();
            accountPersis = new CallCenterAccountOperationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            txnList.addAll(Arrays.asList(assignedTxnTypeList));

            success = accountPersis.updateAccountTxn(CMSCon, txnList, accountNo);

            for (String txn : txnList) {
                onlineTxnList.add(customerPersis.getOnlineTxnCode(CMSCon, txn));
            }
            success = accountPersis.updateOnlineAccountTxn(CMSOnline, onlineTxnList, accountNo);


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

    public boolean checkCustomerTxnToUpdateAccount(String[] assignedTxnTypeList, String accountNo) throws Exception {
        boolean canUpdate = true;
        try {

            List<String> txnList = new ArrayList<String>();
            currentTxnList = new ArrayList<String>();
            deleteTxnList = new ArrayList<String>();
            jointTxnList = new ArrayList<String>();
            customerPersis = new CallCenterCustomerOperationPersistance();
            accountPersis = new CallCenterAccountOperationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            if (accountPersis.isJointAccount(CMSCon, accountNo)) {
                txnList.addAll(Arrays.asList(assignedTxnTypeList));
                currentTxnList = accountPersis.getAccountTxnTypeFromOnline(CMSOnline, accountNo);

                for (String txn : currentTxnList) {
                    if (!txnList.contains(txn)) {
                        deleteTxnList.add(txn);
                    }
                }

                jointTxnList = accountPersis.getJointAccountTxn(CMSOnline, accountNo);

                for (String txn : jointTxnList) {
                    if (deleteTxnList.contains(txn)) {
                        canUpdate = false;
                        break;
                    }

                }
            }


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
        return canUpdate;
    }
}
