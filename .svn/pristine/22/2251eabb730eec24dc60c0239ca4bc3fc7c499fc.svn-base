/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.payment.persistance;

import com.epic.cms.backoffice.payment.bean.BatchSummaryRecord;
import com.epic.cms.backoffice.payment.bean.PaymentBatchBean;
import com.epic.cms.backoffice.payment.bean.PaymentBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ruwan_e
 */
public class BatchPersistance {

    ResultSet rs = null;

    public Map<String, String> getBatchStatusMap(Connection cmsCon) throws SQLException {
        PreparedStatement ps = null;
        String query;
        Map<String, String> batchStatusMap = null;

        query = "SELECT STATUSCODE, DESCRIPTION FROM STATUS WHERE STATUSCATEGORY = 'BMGT'";
        try {

            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();

            batchStatusMap = new HashMap<String, String>();
            while (rs.next()) {
                batchStatusMap.put(rs.getString("STATUSCODE"), rs.getString("DESCRIPTION"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {

            rs.close();
            ps.close();
        }

        return batchStatusMap;

    }

    public List<PaymentBatchBean> getAllPaymentBatches(Connection cmsCon) throws SQLException {
        PreparedStatement ps = null;
        String query;
        List<PaymentBatchBean> batchStatusMap = null;

        query = "SELECT * FROM PAYMENTBATCH";
        try {

            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();

            batchStatusMap = new ArrayList<PaymentBatchBean>();
            PaymentBatchBean pbb = null;
            while (rs.next()) {

                pbb = new PaymentBatchBean();
                pbb.setBatchId(rs.getString("BATCHID"));
                pbb.setBatchCreatedDate(rs.getString("BATCHCREATEDDATE"));
                pbb.setBatchClosedDate(rs.getString("BATCHCLOSEDDATE"));
                pbb.setBranch(rs.getString("BRANCH"));
//                pbb.setBranchName(rs.getString());
                pbb.setClosedUser(rs.getString("CLOSEDUSER"));
                pbb.setCreatedDate(rs.getString("CREATEDDATE"));
                pbb.setCreatedUser(rs.getString("CREATEDUSER"));
                pbb.setIp(rs.getString("IP"));
                pbb.setLastUpdatedTime(rs.getString("LASTUPDATEDTIME"));
                pbb.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                pbb.setReconciledUser(rs.getString("RECONCILEDUSER"));
                pbb.setStatus(rs.getString("STATUS"));
//                pbb.setStatusDes(rs.getString());
                pbb.setTotalTxnAmount(rs.getString("TOTALTXNAMOUNT"));
                pbb.setTotalTxnCount(rs.getString("TOTALTXNCOUNT"));

                batchStatusMap.add(pbb);
            }
        } catch (SQLException e) {
            throw e;
        } finally {

            rs.close();
            ps.close();
        }

        return batchStatusMap;
    }

    public int getTotalCashTxnCount(String id, Connection cmsCon) throws SQLException {
        PreparedStatement ps = null;
        String query;
        int count = 0;
        query = "SELECT count(*) FROM PAYMENT where BATCHID = ? and paymenttype='CASH'";
        try {

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            
            while (rs.next()) {

                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw e;
        } finally {

            rs.close();
            ps.close();
        }

        return count;
    }

    public int getTotalChequeTxnCount(String id, Connection cmsCon) throws SQLException {
        PreparedStatement ps = null;
        String query;
        int count = 0;
        query = "SELECT count(*) FROM PAYMENT where BATCHID = ? and paymenttype='CHEQUE'";
        try {

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            
            while (rs.next()) {

                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw e;
        } finally {

            rs.close();
            ps.close();
        }

        return count;
    }

    public List<PaymentBatchBean> getPaymentBatches(Connection cmsCon) throws SQLException {
        PreparedStatement ps = null;
        String query;
        List<PaymentBatchBean> batchStatusMap = null;

        query = "SELECT PB.BATCHID,PB.BATCHCREATEDDATE,PB.BATCHCLOSEDDATE,PB.CREATEDUSER, "
                + "PB.IP,ST.DESCRIPTION AS STATUS, BB.DESCRPTION AS BRANCH, PB.TOTALTXNCOUNT,"
                + "PB.TOTALTXNAMOUNT, PB.CREATEDDATE FROM PAYMENTBATCH PB,BANKBRANCH BB,"
                + "STATUS ST WHERE PB.BRANCH = BB.BRANCHCODE AND ST.STATUSCODE = PB.STATUS "
                + "AND BB.BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER)";
        try {

            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();

            batchStatusMap = new ArrayList<PaymentBatchBean>();
            PaymentBatchBean pbb = null;
            while (rs.next()) {

                pbb = new PaymentBatchBean();

                pbb.setBatchId(rs.getString("BATCHID"));
                pbb.setBatchCreatedDate(rs.getString("BATCHCREATEDDATE"));
                pbb.setBatchClosedDate(rs.getString("BATCHCLOSEDDATE"));
                pbb.setCreatedUser(rs.getString("CREATEDUSER"));
                pbb.setIp(rs.getString("IP"));
                pbb.setStatus(rs.getString("STATUS"));
                pbb.setBranch(rs.getString("BRANCH"));
                pbb.setTotalTxnCount(rs.getString("TOTALTXNCOUNT"));
                pbb.setTotalTxnAmount(rs.getString("TOTALTXNAMOUNT"));
                pbb.setCreatedDate(rs.getString("CREATEDDATE"));
////                pbb.setBranchName(rs.getString());
//                pbb.setClosedUser(rs.getString("CLOSEDUSER"));
//                
//
//                
//                pbb.setLastUpdatedTime(rs.getString("LASTUPDATEDTIME"));
//                pbb.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
//                pbb.setReconciledUser(rs.getString("RECONCILEDUSER"));
//                
////                pbb.setStatusDes(rs.getString());



                batchStatusMap.add(pbb);
            }
        } catch (SQLException e) {
            throw e;
        } finally {

            rs.close();
            ps.close();
        }

        return batchStatusMap;
    }

    public Map<String, String> getBranchMap(Connection CMSCon) throws SQLException {
        PreparedStatement ps = null;
        String query;
        Map<String, String> branchMap = null;

        query = "SELECT BRANCHCODE,DESCRPTION FROM BANKBRANCH WHERE BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER)";
        try {

            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();

            branchMap = new HashMap<String, String>();
            while (rs.next()) {
                branchMap.put(rs.getString("BRANCHCODE"), rs.getString("DESCRPTION"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {

            rs.close();
            ps.close();
        }

        return branchMap;
    }

    public List<String> getUsernames(Connection CMSCon) throws SQLException {
        PreparedStatement ps = null;
        String query;
        List<String> usernames = null;

        query = "SELECT DISTINCT SYSU.USERNAME FROM USERAPPLICATIONSECTION  UASE ,"
                + " SYSTEMUSER SYSU WHERE SECTIONCODE ='BOPAY' AND "
                + "SYSU.USERROLECODE = UASE.USERROLECODE";
        try {

            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();

            usernames = new ArrayList<String>();
            while (rs.next()) {
                usernames.add(rs.getString("USERNAME"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {

            rs.close();
            ps.close();
        }

        return usernames;
    }

    public List<PaymentBatchBean> searchBatches(String batchID,
            String branch,
            String username,
            String status,
            String paymentDateFrom,
            String paymentDateTo,
            Connection CMSCon) throws ParseException, SQLException {

        PreparedStatement ps = null;
        String query;
        List<PaymentBatchBean> paymentBatchBeans = new ArrayList<PaymentBatchBean>();

        try {

            query = "SELECT PB.BATCHID,PB.BATCHCREATEDDATE,PB.BATCHCLOSEDDATE,PB.CREATEDUSER, "
                    + "PB.IP,ST.DESCRIPTION AS STATUS, BB.DESCRPTION AS BRANCH, PB.TOTALTXNCOUNT,"
                    + "PB.TOTALTXNAMOUNT, PB.CREATEDDATE FROM PAYMENTBATCH PB,BANKBRANCH BB,STATUS ST "
                    + "WHERE PB.BRANCH = BB.BRANCHCODE AND ST.STATUSCODE = PB.STATUS "
                    + "AND BB.BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER)";

            String condition = "";

            if (!batchID.contentEquals("") || batchID.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " PB.BATCHID LIKE '%" + batchID + "%'";
            }
            if (!branch.contentEquals("") || branch.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " BRANCH = '" + branch + "' ";
            }
            if (!username.contentEquals("") || username.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " PB.CREATEDUSER = '" + username + "' ";
            }
            if (!status.contentEquals("") || status.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " STATUS = '" + status + "' ";
            }
            if (!paymentDateFrom.contentEquals("") || paymentDateFrom.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " PB.CREATEDDATE >= TO_DATE('" + this.stringTodate(paymentDateFrom) + "','yyyy-MM-dd') ";
            }

            if (!paymentDateTo.contentEquals("") || paymentDateTo.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " PB.CREATEDDATE - 1 <=  TO_DATE('" + this.stringTodate(paymentDateTo) + "','yyyy-MM-dd') ";
            }

            if (!condition.equals("")) {
                query += " AND " + condition + " ORDER BY PB.BATCHID DESC ";
            } else {

                query += " ORDER BY PB.BATCHID DESC ";
            }

            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {

                PaymentBatchBean bean = new PaymentBatchBean();

                bean.setBatchId(rs.getString("BATCHID"));
                bean.setBatchCreatedDate(rs.getString("BATCHCREATEDDATE"));
                bean.setBatchClosedDate(rs.getString("BATCHCLOSEDDATE"));
                bean.setCreatedUser(rs.getString("CREATEDUSER"));
                bean.setIp(rs.getString("IP"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setBranch(rs.getString("BRANCH"));
                bean.setTotalTxnCount(rs.getString("TOTALTXNCOUNT"));
                bean.setTotalTxnAmount(rs.getString("TOTALTXNAMOUNT"));
                bean.setCreatedDate(rs.getString("CREATEDDATE"));

                paymentBatchBeans.add(bean);
            }

        } catch (SQLException e) {
            throw e;
        } finally {

            rs.close();
            ps.close();
        }

        return paymentBatchBeans;
    }

    public PaymentBatchBean getBatchById(String batchID, Connection CMSCon) throws SQLException {
        PreparedStatement ps = null;
        String query;
        PaymentBatchBean pbb = null;

        query = "SELECT PB.BATCHID,PB.BATCHCREATEDDATE, PB.BATCHCLOSEDDATE, "
                + "PB.CREATEDUSER, PB.IP, ST.DESCRIPTION AS STATUS, BB.DESCRPTION AS BRANCH, "
                + "PB.TOTALTXNCOUNT, PB.TOTALTXNAMOUNT, PB.CREATEDDATE FROM PAYMENTBATCH PB,BANKBRANCH BB, STATUS ST "
                + "WHERE PB.BRANCH = BB.BRANCHCODE AND ST.STATUSCODE = PB.STATUS AND PB.BATCHID = ?";
        try {

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, batchID);
            rs = ps.executeQuery();



            while (rs.next()) {

                pbb = new PaymentBatchBean();

                pbb.setBatchId(rs.getString("BATCHID"));
                pbb.setBatchCreatedDate(rs.getString("BATCHCREATEDDATE"));
                pbb.setBatchClosedDate(rs.getString("BATCHCLOSEDDATE"));
                pbb.setCreatedUser(rs.getString("CREATEDUSER"));
                pbb.setIp(rs.getString("IP"));
                pbb.setStatus(rs.getString("STATUS"));
                pbb.setBranch(rs.getString("BRANCH"));
                pbb.setTotalTxnCount(rs.getString("TOTALTXNCOUNT"));
                pbb.setTotalTxnAmount(rs.getString("TOTALTXNAMOUNT"));
                pbb.setCreatedDate(rs.getString("CREATEDDATE"));
////                pbb.setBranchName(rs.getString());
//                pbb.setClosedUser(rs.getString("CLOSEDUSER"));
//                
//
//                
//                pbb.setLastUpdatedTime(rs.getString("LASTUPDATEDTIME"));
//                pbb.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
//                pbb.setReconciledUser(rs.getString("RECONCILEDUSER"));
//                
////                pbb.setStatusDes(rs.getString());




            }

        } catch (SQLException e) {
            throw e;
        } finally {

            rs.close();
            ps.close();
        }

        return pbb;

    }

    public List<PaymentBean> getAllPaymentDetailsByBatchId(Connection con, String batchId) throws Exception {

        List<PaymentBean> list = new ArrayList<PaymentBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT PY.TRANSACTIONID, PY.BATCHID, PY.CARDHOLDERNAME, "
                    + "PY.CARDNUMBER,PY.PAYMENTTYPE, PY.CHEQUENUMBER, PY.CHEQUEBANK,"
                    + "PY.PAYMENTDATE, ST.DESCRIPTION AS STATUS,CY.DESCRIPTION AS CURRENCY, "
                    + "PY.AMOUNT,PY.STATUS, S.DESCRIPTION AS PAYMENTSTATUS FROM "
                    + "PAYMENT PY,CURRENCY CY,STATUS ST,STATUS S WHERE PY.BATCHID = ?	"
                    + " AND CY.CURRENCYNUMCODE = PY.CURRENCYTYPE AND ST.STATUSCODE = PY.STATUS AND PY.PAYMENTSTATUS = S.STATUSCODE";

            ps = con.prepareStatement(query);
            ps.setString(1, batchId);
            rs = ps.executeQuery();

            while (rs.next()) {
                PaymentBean bean2 = new PaymentBean();
                bean2.setTxnId(rs.getString("TRANSACTIONID"));
                bean2.setBatchId(rs.getString("BATCHID"));
                bean2.setCardHolderName(rs.getString("CARDHOLDERNAME"));
                bean2.setCardNumber(rs.getString("CARDNUMBER"));
                bean2.setPaymentType(rs.getString("PAYMENTTYPE"));
                bean2.setChequeNumber(rs.getString("CHEQUENUMBER"));
                bean2.setChequeBank(rs.getString("CHEQUEBANK"));
                bean2.setPaymentDate(rs.getString("PAYMENTDATE"));
                bean2.setCurencyType(rs.getString("CURRENCY"));
                bean2.setAmount(String.format( "%.2f", Double.parseDouble(rs.getString("AMOUNT")) ) );
                bean2.setStatus(rs.getString("STATUS"));
                bean2.setPayStatus(rs.getString("PAYMENTSTATUS"));
                list.add(bean2);
            }

            return list;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }


    }

    public Double getTotalTxnAmount(Connection CMSCon, String batchId) throws SQLException {
        PreparedStatement ps = null;
        String query;
        Double total = null;

        query = "SELECT SUM(AMOUNT) AS TOTAL FROM PAYMENT WHERE "
                + "BATCHID=? AND STATUS != 'PVOD'";
        try {

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, batchId);
            rs = ps.executeQuery();

            while (rs.next()) {
                total = rs.getDouble("TOTAL");
            }
        } catch (SQLException e) {
            throw e;
        } finally {

            rs.close();
            ps.close();
        }

        return total;
    }
    
    public Double getTxnAmountByBatchIdAndType(Connection CMSCon, String batchId, String type) throws SQLException {
        PreparedStatement ps = null;
        String query;
        Double total = null;

        query = "SELECT SUM(AMOUNT) AS TOTAL FROM PAYMENT WHERE BATCHID=? AND"
                + " STATUS != 'PVOD' AND PAYMENTTYPE=?";
        try {

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, batchId);
            ps.setString(2, type);
            rs = ps.executeQuery();

            while (rs.next()) {
                total = rs.getDouble("TOTAL");
            }
        } catch (SQLException e) {
            throw e;
        } finally {

            rs.close();
            ps.close();
        }
        return total;
    }
    
    public ArrayList<BatchSummaryRecord> getBatchSummary(Connection CMSCon, String batchId) throws SQLException{
        
        ArrayList<BatchSummaryRecord> list = new ArrayList<BatchSummaryRecord>();
        PreparedStatement ps = null;
        String query;
//        Double total = null;

        query = "SELECT C.DESCRIPTION AS CURRENCY, P.PAYMENTTYPE, SUM(P.AMOUNT) AS TOTAL "
                + "FROM PAYMENT P, CURRENCY C WHERE P.BATCHID= ? AND C.CURRENCYNUMCODE = P.CURRENCYTYPE "
                + "GROUP BY C.DESCRIPTION, P.PAYMENTTYPE ORDER BY C.DESCRIPTION";
        try {

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, batchId);
            rs = ps.executeQuery();
            while (rs.next()) {
                BatchSummaryRecord bsr = new BatchSummaryRecord();
                bsr.setCurreny(rs.getString("CURRENCY"));
                bsr.setPaymentType(rs.getString("PAYMENTTYPE"));
                bsr.setTotal(String.format( "%.2f", Double.parseDouble(rs.getString("TOTAL")) ) );
                list.add(bsr);
            }
            
        } catch (SQLException e) {
            throw e;
        } finally {

            rs.close();
            ps.close();
        }
        return list;
    }

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));
    }

    public boolean reconcileBatch(Connection CMSCon, String batchId) throws Exception {
        int i;
        boolean result = false;
        PreparedStatement ps = null;
        try {

            String query = "UPDATE PAYMENTBATCH SET STATUS = ? WHERE BATCHID = ?";

            ps = CMSCon.prepareStatement(query);

            ps.setString(1,StatusVarList.BATCH_RECONCILED );
            ps.setString(2,batchId);

            i = ps.executeUpdate();
            
            if(i == 1){
                result = true;
            }

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
        return result;
    }

    
}
