/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.customer.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.system.util.variable.StatusVarList;
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
public class CallCenterCustomerOperationPersistance {

    private ResultSet rs;
    private List<TypeMgtBean> allTxnList = null;
    private List<String> customerOnlineTxnList = null;

    public List<TypeMgtBean> getAllTxnType(Connection CMSCon) throws Exception {
        PreparedStatement getAllTxn = null;
        allTxnList = new ArrayList<TypeMgtBean>();
        try {
            getAllTxn = CMSCon.prepareStatement("SELECT TXN.TRANSACTIONCODE, "
                    + " TXN.DESCRIPTION,TXN.ONLINETXNTYPE "
                    + " FROM TRANSACTIONTYPE TXN"
                    + " WHERE TXN.STATUS = ?  "
                    + " ORDER BY TXN.DESCRIPTION ASC");


            getAllTxn.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = getAllTxn.executeQuery();

            while (rs.next()) {

                TypeMgtBean type = new TypeMgtBean();

                type.setTransactionTypeCode(rs.getString("TRANSACTIONCODE"));
                type.setDescription(rs.getString("DESCRIPTION"));
                type.setOnlineTxnCode(rs.getString("ONLINETXNTYPE"));

                allTxnList.add(type);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllTxn.close();

        }
        return allTxnList;
    }

    public List<String> getCustomerTxnTypeFromOnline(Connection CMSOnline, String customerId) throws Exception {
        PreparedStatement getCusOnlineTxn = null;
        customerOnlineTxnList = new ArrayList<String>();
        try {
            getCusOnlineTxn = CMSOnline.prepareStatement("SELECT CT.TXNTYPECODE"
                    + " FROM ECMS_ONLINE_CUSTOMER_TXN CT"
                    + " WHERE CT.CUSTOMERID = ?  ");

            getCusOnlineTxn.setString(1, customerId);
            rs = getCusOnlineTxn.executeQuery();

            while (rs.next()) {

                String txnId = Integer.toString(rs.getInt("TXNTYPECODE"));
                customerOnlineTxnList.add(txnId);
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getCusOnlineTxn.close();

        }
        return customerOnlineTxnList;
    }

    public String getOnlineTxnCode(Connection CMSCon, String txnCode) throws Exception {

        PreparedStatement getOnLTxn = null;
        String onlineTxnCode = null;

        try {

            String query = "SELECT ONLINETXNTYPE FROM TRANSACTIONTYPE "
                    + " WHERE TRANSACTIONCODE = ? ";

            getOnLTxn = CMSCon.prepareStatement(query);
            getOnLTxn.setString(1, txnCode);
            rs = getOnLTxn.executeQuery();

            while (rs.next()) {
                onlineTxnCode = rs.getString("ONLINETXNTYPE");
            }


        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getOnLTxn.close();
        }
        return onlineTxnCode;
    }

    public boolean updateCustomerTxn(Connection CMSCon, List<String> assignedTxnTypeList, String customerId) throws Exception {
        PreparedStatement getTxn = null;
        PreparedStatement deleteTxn = null;
        PreparedStatement updateTxn = null;
        boolean success = false;
        List<String> currentList = new ArrayList<String>();
        List<String> insertList = new ArrayList<String>();
        List<String> deleteList = new ArrayList<String>();

        try {
            getTxn = CMSCon.prepareStatement("SELECT TRANSACTIONCODE "
                    + " FROM CUSTOMERTRANSACTIONTYPE WHERE CUSTOMERID =?");

            getTxn.setString(1, customerId);
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

                deleteTxn = CMSCon.prepareStatement("DELETE FROM CUSTOMERTRANSACTIONTYPE "
                        + " WHERE TRANSACTIONCODE=? AND CUSTOMERID = ? ");

                deleteTxn.setString(1, txnId);
                deleteTxn.setString(2, customerId);

                deleteTxn.executeUpdate();

            }
            for (String txnId : insertList) {

                updateTxn = CMSCon.prepareStatement("INSERT INTO CUSTOMERTRANSACTIONTYPE "
                        + "(CUSTOMERID,TRANSACTIONCODE) VALUES(?,?)");

                updateTxn.setString(1, customerId);
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

    public boolean updateOnlineCustomerTxn(Connection CMSOnline, List<String> assignedTxnTypeList, String customerId) throws Exception {
        PreparedStatement getTxn = null;
        PreparedStatement deleteTxn = null;
        PreparedStatement updateTxn = null;
        boolean success = false;
        List<String> currentList = new ArrayList<String>();
        List<String> insertList = new ArrayList<String>();
        List<String> deleteList = new ArrayList<String>();

        try {
            getTxn = CMSOnline.prepareStatement("SELECT TXNTYPECODE "
                    + " FROM ECMS_ONLINE_CUSTOMER_TXN WHERE CUSTOMERID =?");

            getTxn.setString(1, customerId);
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

                deleteTxn = CMSOnline.prepareStatement("DELETE FROM ECMS_ONLINE_CUSTOMER_TXN "
                        + " WHERE TXNTYPECODE=? AND CUSTOMERID = ? ");

                deleteTxn.setString(1, txnId);
                deleteTxn.setString(2, customerId);

                deleteTxn.executeUpdate();

            }
            for (String txnId : insertList) {

                updateTxn = CMSOnline.prepareStatement("INSERT INTO ECMS_ONLINE_CUSTOMER_TXN "
                        + "(CUSTOMERID,TXNTYPECODE) VALUES(?,?)");

                updateTxn.setString(1, customerId);
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
}
