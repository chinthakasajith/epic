/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.txnadjustment.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CurrencyMgtManager;
import com.epic.cms.backoffice.txnadjustment.bean.TransactionAdjustment;
import com.epic.cms.backoffice.txnadjustment.bean.TransactionAdjustmentParty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ruwan_e
 */
public class TxnAdjustmentPersistance {

    private ResultSet rs;
    private ArrayList<TransactionAdjustment> txList;

    public HashMap<String, String> getTxTypeMap(Connection con) throws SQLException {

        HashMap<String, String> txTypeMap;
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT TRANSACTIONCODE, DESCRIPTION FROM TRANSACTIONTYPE WHERE FINANCIALSTATUS='YES'");
            rs = ps.executeQuery();
            txTypeMap = new HashMap<String, String>();

            while (rs.next()) {
                txTypeMap.put(rs.getString("TRANSACTIONCODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return txTypeMap;


    }

    private String getTxTypeDesc(Connection con, String txTypeCode) throws SQLException {

        HashMap<String, String> txTypeMap;

        try {
            txTypeMap = getTxTypeMap(con);

        } catch (SQLException ex) {
            throw ex;
        }
        
        String st = txTypeMap.get(txTypeCode);
        
        return st;

    }

    /*private String getCurrencyTypeDesc(Connection con, String currencyTypeCode) throws SQLException, Exception {
    
    HashMap<String, String> currencyTypeMap;
    CurrencyMgtManager currencyManager = new CurrencyMgtManager();
    
    
    List<CurrencyBean> currencyDetails = currencyManager.getCurrencyDetails();
    
    for (Iterator<CurrencyBean> it = currencyDetails.iterator(); it.hasNext();) {
    CurrencyBean currencyBean = it.next();
    
    if (currencyTypeCode == currencyBean.getCurrencyCode()) {
    return currencyBean.getCurrencyDes();
    }
    
    }
    
    return null;
    
    }*/
    public HashMap<String, String> getTransactionAdustmentStatusMap(Connection con) throws SQLException {

        HashMap<String, String> txStatusMap;
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT STATUSCODE, DESCRIPTION FROM STATUS WHERE STATUSCATEGORY='MADJ'");
            rs = ps.executeQuery();
            txStatusMap = new HashMap<String, String>();

            while (rs.next()) {
                txStatusMap.put(rs.getString("STATUSCODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return txStatusMap;


    }

    public HashMap<String, String> getFeeTypeMap(Connection con) throws SQLException {
        PreparedStatement ps = null;
        HashMap<String, String> feeTypeMap;

        try {
            ps = con.prepareStatement("SELECT * FROM FEETYPE");
            rs = ps.executeQuery();
            feeTypeMap = new HashMap<String, String>();

            while (rs.next()) {
                feeTypeMap.put(rs.getString("FEETYPECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return feeTypeMap;
    }

    public List<TransactionAdjustment> getTransactionAdjustmentsByParty(TransactionAdjustmentParty party, Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT AD.ID,AD.AMOUNT,AD.adjustmenttype,ad.approveduser,"
                    + "ad.crdr,ad.remarks,ad.requesteduser,ad.traceno,ad.lastupdateduser,"
                    + "ad.verificationvale,ad.uniqueid, cr.description as CURRENCYTYPEDES,"
                    + "cr.currencynumcode as CURRENCYCODE,st.description as statusdes,"
                    + "st.statuscode as status, ad.transactiontype "
                    + "FROM ADJUSTMENT AD,"
                    + "CURRENCY CR, status st "
                    + "WHERE CARDORMERCHANT = ? AND "
                    + "cr.currencynumcode = ad.currencytype AND st.statuscode = ad.status");

            ps.setString(1, party.getName());

            rs = ps.executeQuery();
            txList = new ArrayList<TransactionAdjustment>();

            TransactionAdjustment tabean;
            while (rs.next()) {
                
                tabean = new TransactionAdjustment();
                tabean.setAdjustmentParty(party);
                tabean.setId(rs.getInt("ID"));
                tabean.setAmount(rs.getString("AMOUNT"));
                tabean.setAdjustmentType(rs.getString("ADJUSTMENTTYPE"));
                tabean.setAdjustmentTypeDes(getAdjustmentDescriptionById(rs.getString("ADJUSTMENTTYPE")));
                tabean.setApprovedUser(rs.getString("APPROVEDUSER"));
                tabean.setCrOrdr(rs.getString("CRDR"));
                tabean.setCurrencyCode(this.zeroPadding(rs.getString("CURRENCYCODE"))); 
                tabean.setCurrencyDes(rs.getString("CURRENCYTYPEDES"));               
                tabean.setRemarks(rs.getString("REMARKS"));
                tabean.setRequestedUser(rs.getString("REQUESTEDUSER"));
                tabean.setStatus(rs.getString("STATUS"));
                tabean.setStatusDes(rs.getString("STATUSDES"));
                tabean.setTraceNo(rs.getString("TRACENO"));
                tabean.setUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                tabean.setVerificationValue(rs.getString("VERIFICATIONVALE"));
                tabean.setUniqueId(rs.getString("UNIQUEID"));
                tabean.setTransactionType(rs.getString("TRANSACTIONTYPE"));

                txList.add(tabean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return txList;
    }
    
    /*
     * to put zeros to the front of code if there is less than 3 digits
     */
    private String zeroPadding(String code) {
        int len = code.length();

        if (len < 3 && len == 2) {
            code = "0" + code;
        } else if (len < 3 && len == 1) {
            code = "00" + code;
        }

        return code;
    }
    
    private String getAdjustmentDescriptionById(String id){
        if(id.equals("1")){
            return "TRANSACTION";
        }
        if(id.equals("2")){
            return "FEE";
        }
        if(id.equals("3")){
            return "INTEREST";
        }
        if(id.equals("4")){
            return "COMMISSION";
        }
        return "";
    }

    public int addTransactionAdjustment(TransactionAdjustment bean, Connection con) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO ADJUSTMENT (CRDR, AMOUNT, APPROVEDUSER, "
                    + "CARDORMERCHANT, CURRENCYTYPE, "
                    + "REMARKS, REQUESTEDUSER, STATUS, TRACENO, UNIQUEID, VERIFICATIONVALE,ADJUSTMENTTYPE,TRANSACTIONTYPE,LASTUPDATEDUSER) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);

            ps.setString(1, bean.getCrOrdr());
            ps.setString(2, bean.getAmount().toString());
            ps.setString(3, bean.getApprovedUser());
            ps.setString(4, bean.getAdjustmentParty().name());
            ps.setString(5, bean.getCurrencyCode());
            ps.setString(6, bean.getRemarks());
            ps.setString(7, bean.getRequestedUser());
            ps.setString(8, bean.getStatus());
            ps.setString(9, bean.getTraceNo());
            ps.setString(10, bean.getUniqueId());
            ps.setString(11, bean.getVerificationValue());
            ps.setString(12, bean.getAdjustmentType());
            ps.setString(13, bean.getTransactionType());
            ps.setString(14, bean.getUpdatedUser());

            i = ps.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return i;
    }

    public ArrayList<TransactionAdjustment> getAllTransactionAdjustments(Connection con) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT * FROM ADJUSTMENT");
            rs = ps.executeQuery();
            txList = new ArrayList<TransactionAdjustment>();

            TransactionAdjustment tabean;
            while (rs.next()) {
                tabean = new TransactionAdjustment();

                tabean.setId(rs.getLong("ID"));
                tabean.setAdjustmentParty(TransactionAdjustmentParty.get(rs.getString("CARDORMERCHANT")));
                tabean.setAmount(rs.getString("AMOUNT"));
                tabean.setApprovedUser(rs.getString("APPROVEDUSER"));
                tabean.setCrOrdr(rs.getString("CRDR"));
                tabean.setCurrencyCode(rs.getString("CURRENCYTYPE"));
                tabean.setRemarks(rs.getString("REMARKS"));
                tabean.setRequestedUser(rs.getString("REQUESTEDUSER"));
                tabean.setStatus(rs.getString("STATUS"));
                tabean.setTraceNo(rs.getString("TRACENO"));
                tabean.setUniqueId(rs.getString("UNIQUEID"));
                tabean.setUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                tabean.setVerificationValue(rs.getString("VERIFICATIONVALE"));
                tabean.setTransactionType(getTxTypeDesc(con, rs.getString("TRANSACTIONTYPE")));
                tabean.setAdjustmentType(rs.getString("ADJUSTMENTTYPE"));

                txList.add(tabean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return txList;
    }

    public TransactionAdjustment getTransactionAdjustmentById(Long txId, Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;
        TransactionAdjustment tabean = null;
        try {
            ps = con.prepareStatement("SELECT AJ.ID,AJ.CARDORMERCHANT,AJ.AMOUNT,AJ.APPROVEDUSER,AJ.CRDR,"
                    + "CURRENCY.DESCRIPTION AS CURRENCY,AJ.REMARKS,AJ.REQUESTEDUSER,"
                    + "STATUS.DESCRIPTION AS STATUS,AJ.TRACENO,AJ.UNIQUEID,AJ.LASTUPDATEDUSER,"
                    + "AJ.VERIFICATIONVALE,AJ.ADJUSTMENTTYPE,AJ.TRANSACTIONTYPE FROM ADJUSTMENT AJ,STATUS,CURRENCY "
                    + "WHERE AJ.STATUS=STATUS.STATUSCODE AND AJ.CURRENCYTYPE=CURRENCY.CURRENCYNUMCODE  AND AJ.ID = " + txId);
            rs = ps.executeQuery();


            while (rs.next()) {
                tabean = new TransactionAdjustment();

                tabean.setId(rs.getLong("ID"));
                tabean.setAdjustmentParty(TransactionAdjustmentParty.get(rs.getString("CARDORMERCHANT")));
                tabean.setAmount(rs.getString("AMOUNT"));
                tabean.setApprovedUser(rs.getString("APPROVEDUSER"));
                tabean.setCrOrdr(rs.getString("CRDR"));
                tabean.setCurrencyCode(rs.getString("CURRENCY"));
                tabean.setRemarks(rs.getString("REMARKS"));
                tabean.setRequestedUser(rs.getString("REQUESTEDUSER"));
                tabean.setStatus(rs.getString("STATUS"));
                tabean.setTraceNo(rs.getString("TRACENO"));
                tabean.setUniqueId(rs.getString("UNIQUEID"));
                tabean.setUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                tabean.setVerificationValue(rs.getString("VERIFICATIONVALE"));
                tabean.setAdjustmentType(rs.getString("ADJUSTMENTTYPE"));
                tabean.setTransactionType(rs.getString("TRANSACTIONTYPE"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return tabean;
    }

    public int updateTransactionAdjustment(TransactionAdjustment adjustment, Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;

        int i = -1;
        ps = null;
        try {
            String query = "UPDATE ADJUSTMENT SET AMOUNT=?,CRDR=?,CURRENCYTYPE=?,REMARKS=?,STATUS=?,TRACENO=?,UNIQUEID=?,VERIFICATIONVALE=?, ADJUSTMENTTYPE=?,TRANSACTIONTYPE=? WHERE ID =?";

            ps = con.prepareStatement(query);
            ps.setString(1, adjustment.getAmount());
            ps.setString(2, adjustment.getCrOrdr());
            ps.setString(3, adjustment.getCurrencyCode());
            ps.setString(4, adjustment.getRemarks());
            ps.setString(5, adjustment.getStatus());
            ps.setString(6, adjustment.getTraceNo());
            ps.setString(7, adjustment.getUniqueId());
            ps.setString(8, adjustment.getVerificationValue());
            ps.setString(9, adjustment.getAdjustmentType());
            ps.setString(10, adjustment.getTransactionType());
            ps.setLong(11, adjustment.getId());



            i = ps.executeUpdate();



        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return i;
    }

    public boolean isCardNumberExist(String cardNumber, Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;

        boolean result = false;
        ps = null;
        rs = null;
        try {
            String queryString = "select cardnumber, expierydate from card "
                    + "WHERE cardnumber=" + cardNumber;
            ps = con.prepareStatement(queryString);
            rs = ps.executeQuery();


            while (rs.next()) {
                return true;
            }

            return false;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public boolean isMerchantExist(String uniqueId, Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;

        ps = null;
        rs = null;
        try {
            String queryString = "select * from MERCHANTLOCATION WHERE merchantid = ?";
            ps = con.prepareStatement(queryString);
            ps.setString(1, uniqueId);
            rs = ps.executeQuery();


            while (rs.next()) {
                return true;
            }

            return false;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public boolean isTerminalExist(String uniqueId,String verificationValue, Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;

        ps = null;
        rs = null;
        try {
            String queryString = "select * from terminal WHERE terminalid = " +verificationValue+ " AND merchantid = "+uniqueId;
            ps = con.prepareStatement(queryString);
            rs = ps.executeQuery();

            while (rs.next()) {
                return true;
            }

            return false;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
