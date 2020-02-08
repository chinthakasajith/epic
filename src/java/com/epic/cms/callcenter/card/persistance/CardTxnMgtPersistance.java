/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.card.persistance;

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
public class CardTxnMgtPersistance {

    private ResultSet rs;
    private List<String> notAssignedOnlineTxnList = null;
    private List<String> accountTxnTypeList = null;

    public List<String> getNotAssignedCardTxnTypeFromOnline(Connection CMSOnline, String cardNo) throws Exception {
        PreparedStatement getOnlineTxn = null;
        notAssignedOnlineTxnList = new ArrayList<String>();
        try {

            getOnlineTxn = CMSOnline.prepareStatement("SELECT DISTINCT AT.TXNTYPECODE "
                    + " FROM ECMS_ONLINE_ACCOUNT_TXN AT LEFT JOIN ECMS_ONLINE_CARD_ACC_CUS CAC "
                    + " ON AT.ACCOUNTNUMBER = CAC.ACCOUNTNUMBER WHERE CAC.CARDNUMBER = ? "
                    + " AND AT.TXNTYPECODE NOT IN "
                    + " (SELECT CT.TXNTYPECODE FROM ECMS_ONLINE_CARD_TXN CT "
                    + " WHERE CT.CARDNO=? )");

            getOnlineTxn.setString(1, cardNo);
            getOnlineTxn.setString(2, cardNo);
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

    public List<String> getCardTxnTypeFromOnline(Connection CMSOnline, String cardNo) throws Exception {
        PreparedStatement getAccOnlineTxn = null;
        accountTxnTypeList = new ArrayList<String>();
        try {
            getAccOnlineTxn = CMSOnline.prepareStatement("SELECT CT.TXNTYPECODE"
                    + " FROM ECMS_ONLINE_CARD_TXN CT"
                    + " WHERE CT.CARDNO = ?  ");

            getAccOnlineTxn.setString(1, cardNo);
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
    
     public boolean updateCardTxn(Connection CMSCon, List<String> assignedTxnTypeList, String cardNo) throws Exception {
        PreparedStatement getTxn = null;
        PreparedStatement deleteTxn = null;
        PreparedStatement updateTxn = null;
        boolean success = false;
        List<String> currentList = new ArrayList<String>();
        List<String> insertList = new ArrayList<String>();
        List<String> deleteList = new ArrayList<String>();

        try {
            getTxn = CMSCon.prepareStatement("SELECT TRANSACTIONCODE "
                    + " FROM CARDTRANSACTIONTYPE WHERE CARDNUMBER =?");

            getTxn.setString(1, cardNo);
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

                deleteTxn = CMSCon.prepareStatement("DELETE FROM CARDTRANSACTIONTYPE "
                        + " WHERE TRANSACTIONCODE=? AND CARDNUMBER = ? ");

                deleteTxn.setString(1, txnId);
                deleteTxn.setString(2, cardNo);

                deleteTxn.executeUpdate();

            }
            for (String txnId : insertList) {

                updateTxn = CMSCon.prepareStatement("INSERT INTO CARDTRANSACTIONTYPE "
                        + "(CARDNUMBER,TRANSACTIONCODE) VALUES(?,?)");

                updateTxn.setString(1, cardNo);
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
    
     public boolean updateOnlineCardTxn(Connection CMSOnline, List<String> assignedTxnTypeList, String cardNo) throws Exception {
        PreparedStatement getTxn = null;
        PreparedStatement deleteTxn = null;
        PreparedStatement updateTxn = null;
        boolean success = false;
        List<String> currentList = new ArrayList<String>();
        List<String> insertList = new ArrayList<String>();
        List<String> deleteList = new ArrayList<String>();

        try {
            getTxn = CMSOnline.prepareStatement("SELECT TXNTYPECODE "
                    + " FROM ECMS_ONLINE_CARD_TXN WHERE CARDNO =?");

            getTxn.setString(1, cardNo);
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

                deleteTxn = CMSOnline.prepareStatement("DELETE FROM ECMS_ONLINE_CARD_TXN "
                        + " WHERE TXNTYPECODE=? AND CARDNO = ? ");

                deleteTxn.setString(1, txnId);
                deleteTxn.setString(2, cardNo);

                deleteTxn.executeUpdate();

            }
            for (String txnId : insertList) {

                updateTxn = CMSOnline.prepareStatement("INSERT INTO ECMS_ONLINE_CARD_TXN "
                        + "(CARDNO,TXNTYPECODE) VALUES(?,?)");

                updateTxn.setString(1, cardNo);
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
