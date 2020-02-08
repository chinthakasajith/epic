/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.account.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class CallCenterAccountOperationPersistance {

    private ResultSet rs;
    private List<String> notAssignedOnlineTxnList = null;
    private List<String> accountTxnTypeList = null;
    private List<String> jointTxnList = null;

    public List<String> getNotAssignedAccountTxnTypeFromOnline(Connection CMSOnline, String accountNo) throws Exception {
        PreparedStatement getOnlineTxn = null;
        notAssignedOnlineTxnList = new ArrayList<String>();
        try {

            getOnlineTxn = CMSOnline.prepareStatement("SELECT DISTINCT CT.TXNTYPECODE "
                    + " FROM ECMS_ONLINE_CUSTOMER_TXN CT LEFT JOIN ECMS_ONLINE_CARD_ACC_CUS CAC "
                    + " ON CT.CUSTOMERID = CAC.CUSTOMERID WHERE CAC.ACCOUNTNUMBER = ? "
                    + " AND CT.TXNTYPECODE NOT IN "
                    + " (SELECT ACT.TXNTYPECODE FROM ECMS_ONLINE_ACCOUNT_TXN ACT "
                    + " WHERE ACT.ACCOUNTNUMBER=? )");

            getOnlineTxn.setString(1, accountNo);
            getOnlineTxn.setString(2, accountNo);
            rs = getOnlineTxn.executeQuery();

            while (rs.next()) {

                String txn = Integer.toString(rs.getInt("TXNTYPECODE"));
                notAssignedOnlineTxnList.add(txn);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getOnlineTxn.close();

        }
        return notAssignedOnlineTxnList;

    }

    public List<String> getAccountTxnTypeFromOnline(Connection CMSOnline, String accountNo) throws Exception {
        PreparedStatement getAccOnlineTxn = null;
        accountTxnTypeList = new ArrayList<String>();
        try {
            getAccOnlineTxn = CMSOnline.prepareStatement("SELECT AT.TXNTYPECODE"
                    + " FROM ECMS_ONLINE_ACCOUNT_TXN AT"
                    + " WHERE AT.ACCOUNTNUMBER = ?  ");

            getAccOnlineTxn.setString(1, accountNo);
            rs = getAccOnlineTxn.executeQuery();

            while (rs.next()) {

                String txnId = Integer.toString(rs.getInt("TXNTYPECODE"));
                accountTxnTypeList.add(txnId);
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAccOnlineTxn.close();
        }
        return accountTxnTypeList;
    }

    public boolean updateAccountTxn(Connection CMSCon, List<String> assignedTxnTypeList, String accountNo) throws Exception {
        PreparedStatement getTxn = null;
        PreparedStatement deleteTxn = null;
        PreparedStatement updateTxn = null;
        boolean success = false;
        List<String> currentList = new ArrayList<String>();
        List<String> insertList = new ArrayList<String>();
        List<String> deleteList = new ArrayList<String>();

        try {
            getTxn = CMSCon.prepareStatement("SELECT TRANSACTIONCODE "
                    + " FROM ACCOUNTTRANSACTIONTYPE WHERE ACCOUNTNUMBER =?");

            getTxn.setString(1, accountNo);
            rs = getTxn.executeQuery();

            while (rs.next()) {
                String txnCode = rs.getString("TRANSACTIONCODE");
                currentList.add(txnCode);
            }

            for (String txn : assignedTxnTypeList) {
                if (!currentList.contains(txn)) {
                    insertList.add(txn);
                }
            }
            for (String txn : currentList) {
                if (!assignedTxnTypeList.contains(txn)) {
                    deleteList.add(txn);
                }
            }

            for (String txnId : deleteList) {

                deleteTxn = CMSCon.prepareStatement("DELETE FROM ACCOUNTTRANSACTIONTYPE "
                        + " WHERE TRANSACTIONCODE=? AND ACCOUNTNUMBER = ? ");

                deleteTxn.setString(1, txnId);
                deleteTxn.setString(2, accountNo);

                deleteTxn.executeUpdate();

            }
            for (String txnId : insertList) {

                updateTxn = CMSCon.prepareStatement("INSERT INTO ACCOUNTTRANSACTIONTYPE "
                        + "(ACCOUNTNUMBER,TRANSACTIONCODE) VALUES(?,?)");

                updateTxn.setString(1, accountNo);
                updateTxn.setString(2, txnId);

                updateTxn.executeUpdate();

            }
            success = true;

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            if (getTxn != null) {
                getTxn.close();
            }
            if (deleteTxn != null) {
                deleteTxn.close();
            }
            if (updateTxn != null) {
                updateTxn.close();
            }
        }
        return success;
    }

    public boolean updateOnlineAccountTxn(Connection CMSOnline, List<String> assignedTxnTypeList, String accountNo) throws Exception {
        PreparedStatement getTxn = null;
        PreparedStatement deleteTxn = null;
        PreparedStatement updateTxn = null;
        boolean success = false;
        List<String> currentList = new ArrayList<String>();
        List<String> insertList = new ArrayList<String>();
        List<String> deleteList = new ArrayList<String>();

        try {
            getTxn = CMSOnline.prepareStatement("SELECT TXNTYPECODE "
                    + " FROM ECMS_ONLINE_ACCOUNT_TXN WHERE ACCOUNTNUMBER =?");

            getTxn.setString(1, accountNo);
            rs = getTxn.executeQuery();

            while (rs.next()) {
                String txnCode = Integer.toString(rs.getInt("TXNTYPECODE"));
                currentList.add(txnCode);
            }

            for (String txn : assignedTxnTypeList) {
                if (!currentList.contains(txn)) {
                    insertList.add(txn);
                }
            }
            for (String txn : currentList) {
                if (!assignedTxnTypeList.contains(txn)) {
                    deleteList.add(txn);
                }
            }

            for (String txnId : deleteList) {

                deleteTxn = CMSOnline.prepareStatement("DELETE FROM ECMS_ONLINE_ACCOUNT_TXN "
                        + " WHERE TXNTYPECODE=? AND ACCOUNTNUMBER = ? ");

                deleteTxn.setString(1, txnId);
                deleteTxn.setString(2, accountNo);

                deleteTxn.executeUpdate();

            }
            for (String txnId : insertList) {

                updateTxn = CMSOnline.prepareStatement("INSERT INTO ECMS_ONLINE_ACCOUNT_TXN "
                        + "(ACCOUNTNUMBER,TXNTYPECODE) VALUES(?,?)");

                updateTxn.setString(1, accountNo);
                updateTxn.setString(2, txnId);

                updateTxn.executeUpdate();

            }
            success = true;

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            if (getTxn != null) {
                getTxn.close();
            }
            if (deleteTxn != null) {
                deleteTxn.close();
            }
            if (updateTxn != null) {
                updateTxn.close();
            }
        }
        return success;
    }

    public boolean isJointAccount(Connection CMSCon, String accountNo) throws Exception {
        boolean isJoint = false;
        PreparedStatement getJoint = null;
        try {
            getJoint = CMSCon.prepareStatement("SELECT COUNT(CUSTOMERID) AS CUCOUNT "
                    + "FROM CARDACCOUNTCUSTOMER WHERE ACCOUNTNO =?");

            getJoint.setString(1, accountNo);
            rs = getJoint.executeQuery();

            while (rs.next()) {
                if (1 < rs.getInt("CUCOUNT")) {
                    isJoint = true;
                }
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getJoint.close();
        }
        return isJoint;
    }

    public List<String> getJointAccountTxn(Connection CMSOnline, String accountNo) throws Exception {
        PreparedStatement getJointTxn = null;
        jointTxnList = new ArrayList<String>();
        try {
            getJointTxn = CMSOnline.prepareStatement("SELECT  CT.TXNTYPECODE "
                    + " FROM ECMS_ONLINE_CUSTOMER_TXN CT LEFT JOIN ECMS_ONLINE_CARD_ACC_CUS CAC"
                    + " ON CT.CUSTOMERID = CAC.CUSTOMERID WHERE CAC.ACCOUNTNUMBER = ? "
                    + " GROUP BY CT.TXNTYPECODE HAVING ( COUNT(CT.TXNTYPECODE) > 1 )");
            
            getJointTxn.setString(1, accountNo);
            rs = getJointTxn.executeQuery();
            
            while (rs.next()) {  
                
                String txn = rs.getString("TXNTYPECODE");
                jointTxnList.add(txn);
                
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getJointTxn.close();
        }
        return jointTxnList;
    }
//     public boolean checkCustomerTxnToUpdateAccount(Connection CMSOnline, String accountNo)throws Exception{
//         
//         try {
//             
//         } catch (Exception e) {
//         }
// 
//     }
}
