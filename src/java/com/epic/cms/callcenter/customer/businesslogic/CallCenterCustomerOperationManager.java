/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.customer.businesslogic;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
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
public class CallCenterCustomerOperationManager {

    private CallCenterCustomerOperationPersistance customerPersis = null;
    private List<TypeMgtBean> allTxnList = null;
    private List<TypeMgtBean> assignedTxnList = null;
    private List<String> customerOnlineTxnList = null;
    private Connection CMSCon = null;
    private Connection CMSOnline = null;

    public List<TypeMgtBean> getNotAssignedTxnType(String customerId) throws Exception {
        try {
            allTxnList = new ArrayList<TypeMgtBean>();
            customerOnlineTxnList = new ArrayList<String>();
            customerPersis = new CallCenterCustomerOperationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            allTxnList = customerPersis.getAllTxnType(CMSCon);
            customerOnlineTxnList = customerPersis.getCustomerTxnTypeFromOnline(CMSOnline, customerId);

            CMSCon.commit();
            CMSOnline.commit();

            for (int i = 0; i < customerOnlineTxnList.size(); i++) {

                for (int j = 0; j < allTxnList.size(); j++) {

                    if (allTxnList.get(j).getOnlineTxnCode().equals(customerOnlineTxnList.get(i))) {
                        allTxnList.remove(j);
                        break;
                    }
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
        return allTxnList;
    }

    public List<TypeMgtBean> getAssignedTxnType(String customerId) throws Exception {
        try {
            allTxnList = new ArrayList<TypeMgtBean>();
            customerOnlineTxnList = new ArrayList<String>();
            assignedTxnList = new ArrayList<TypeMgtBean>();
            customerPersis = new CallCenterCustomerOperationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            allTxnList = customerPersis.getAllTxnType(CMSCon);
            customerOnlineTxnList = customerPersis.getCustomerTxnTypeFromOnline(CMSOnline, customerId);

            CMSCon.commit();
            CMSOnline.commit();


            for (String txn : customerOnlineTxnList) {

                for (TypeMgtBean bean : allTxnList) {
                    if (bean.getOnlineTxnCode().equals(txn)) {
                        assignedTxnList.add(bean);
                        break;
                    }
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

    public boolean updateCustomerTxn(String[] assignedTxnTypeList, String customerId) throws Exception {
        boolean success = false;
        try {

            List<String> txnList = new ArrayList<String>();
            List<String> onlineTxnList = new ArrayList<String>();
            customerPersis = new CallCenterCustomerOperationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            txnList.addAll(Arrays.asList(assignedTxnTypeList));

            success = customerPersis.updateCustomerTxn(CMSCon, txnList, customerId);

            for (String txn : txnList) {
                onlineTxnList.add(customerPersis.getOnlineTxnCode(CMSCon, txn));
            }
            success = customerPersis.updateOnlineCustomerTxn(CMSOnline, onlineTxnList, customerId);


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
