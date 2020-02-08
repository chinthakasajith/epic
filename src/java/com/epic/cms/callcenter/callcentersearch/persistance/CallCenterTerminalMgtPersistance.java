/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.callcentersearch.persistance;

import com.epic.cms.callcenter.callcentersearch.bean.TransactionBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.TerminalDataCaptureBean;
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
public class CallCenterTerminalMgtPersistance {

    ResultSet rs = null;
    private List<TransactionBean> txnList = null;

    public String getCurrentTerminalStatus(Connection CMSCon, String tid) throws Exception {
        String status = null;
        PreparedStatement getStatus = null;
        try {
            getStatus = CMSCon.prepareStatement("SELECT T.TERMINALSTATUS "
                    + "FROM TERMINAL T WHERE T.TERMINALID = ?");

            getStatus.setString(1, tid);
            rs = getStatus.executeQuery();

            while (rs.next()) {

                status = rs.getString("TERMINALSTATUS");

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    getStatus.close();
                }
            } catch (Exception e) {
                throw e;
            }

        }
        return status;
    }

    public int updateTerminal(TerminalDataCaptureBean terminalBean, String[] transactions, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE TERMINAL SET "
                    + "TERMINALNAME=? WHERE TERMINALID=? ";

            ps = con.prepareStatement(query);


            ps.setString(1, terminalBean.getName());
            ps.setString(2, terminalBean.getTerminalID());

            row = ps.executeUpdate();

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

        return row;
    }

    public int updateTerminalTxn(String[] transactions, Connection con, String tid) throws Exception {
        int k;

        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM TERMINALTXN where TERMINALID =?");

            ps.setString(1, tid);

            k = ps.executeUpdate();

            for (int i = 0; i < transactions.length; i++) {

                ps = con.prepareStatement("INSERT INTO TERMINALTXN "
                        + "(TERMINALID,TXNCODE ) "
                        + " VALUES (?,?) ");

                ps.setString(1, tid);
                ps.setString(2, transactions[i]);

                int j = 0;
                j = ps.executeUpdate();
                if (j == 0) {
                    throw new Exception();
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                ps.close();

            } catch (Exception e) {
                throw e;
            }
        }
        return k;

    }

    public int updateTerminalTxnOnline(String[] transactions, List<TerminalDataCaptureBean> assignedBean, Connection con, String tid) throws Exception {
        int k;

        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM ECMS_ONLINE_TERMINAL_TXN WHERE TID =?");

            ps.setString(1, tid);

            k = ps.executeUpdate();

            for (int i = 0; i < transactions.length; i++) {

                ps = con.prepareStatement("INSERT INTO ECMS_ONLINE_TERMINAL_TXN "
                        + "(TID,TXNTYPECODE ) "
                        + " VALUES (?,?) ");



                ps.setString(1, tid);
                ps.setString(2, assignedBean.get(i).getOnlineTxnCode());

                int j = 0;
                j = ps.executeUpdate();
                if (j == 0) {
                    throw new Exception();
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                ps.close();

            } catch (Exception e) {
                throw e;
            }
        }
        return k;

    }

    public int getCountTxn(Connection con, String condition, String terminalId) throws Exception {

        int count = 0;
        PreparedStatement getTxnCount = null;
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT COUNT(*) AS TOTTXN "
                    + "FROM ECMS_ONLINE_TRANSACTION OL,ECMS_ONLINE_TXNTYPE TT,ECMS_ONLINE_STATUS ST  "
                    + "WHERE ST.CODE = OL.STATUS AND TT.TYPECODE=OL.TXNTYPECODE AND OL.TID = ? AND " + condition;

            //System.out.println(strSqlBasic);

            getTxnCount = con.prepareStatement(strSqlBasic);
            getTxnCount.setString(1, terminalId);

            rs = getTxnCount.executeQuery();

            while (rs.next()) {

                count = rs.getInt("TOTTXN");
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTxnCount.close();
        }
        return count;
    }

    public List<TransactionBean> getTerminalTxnExpDetails(Connection con, String condition, int start, int end, String terminalId) throws Exception {
        PreparedStatement getTxnDt = null;
        String strSqlBasic = null;
        try {
            strSqlBasic = " SELECT * FROM ( SELECT * FROM ( SELECT OL.TXNID,OL.AUTHCODE,"
                    + " OL.BIN,OL.CARDNO,OL.EXPIRYDATE,OL.MID,OL.STATUS,OL.TID,OL.TRACENO,"
                    + " OL.TXNDATE,OL.TXNTIME,OL.TXNTYPECODE,OL.TXNCURRENCY,OL.TXNAMOUNT,"
                    + " OL.SETTLEMENTDATE,OL.SERVICECODE,"
                    + " ST.DESCRIPTION AS STATUSDES,  TT.DESCRYPTION AS TXNDES, ROWNUM R"
                    + " FROM ECMS_ONLINE_TRANSACTION OL,  "
                    + " ECMS_ONLINE_STATUS ST,ECMS_ONLINE_TXNTYPE TT "
                    + " WHERE ST.CODE = OL.STATUS AND TT.TYPECODE=OL.TXNTYPECODE AND OL.TID = ? AND "
                    + condition + "  )  WHERE R <=  " + end + "  ) WHERE R > " + start;

            System.out.println("************" + strSqlBasic);

            getTxnDt = con.prepareStatement(strSqlBasic);
            getTxnDt.setString(1, terminalId);

            rs = getTxnDt.executeQuery();
            txnList = new ArrayList<TransactionBean>();

            while (rs.next()) {

                TransactionBean resultBean = new TransactionBean();

                resultBean.setAuthCode((rs.getString("AUTHCODE") == null) ? "--" : rs.getString("AUTHCODE"));
                resultBean.setBin((rs.getString("BIN") == null) ? "--" : rs.getString("BIN"));
                resultBean.setCardNumber((rs.getString("CARDNO") == null) ? "--" : rs.getString("CARDNO"));
                resultBean.setTxnAmount((rs.getString("TXNAMOUNT") == null) ? "--" : rs.getString("TXNAMOUNT"));
                resultBean.setExpiryDate((rs.getString("EXPIRYDATE") == null) ? "--" : rs.getString("EXPIRYDATE"));
                resultBean.setMerchantId((rs.getString("MID") == null) ? "--" : rs.getString("MID"));
                resultBean.setSettlementDate((rs.getString("SETTLEMENTDATE") == null) ? "--" : rs.getString("SETTLEMENTDATE"));
                resultBean.setTerminalId((rs.getString("TID") == null) ? "--" : rs.getString("TID"));
                resultBean.setTraceNum((rs.getString("TRACENO") == null) ? "--" : rs.getString("TRACENO"));
                resultBean.setTxnCurrency((rs.getString("txncurrency") == null) ? "--" : rs.getString("txncurrency"));
                resultBean.setTxnDate((rs.getString("TXNDATE") == null) ? "--" : rs.getString("TXNDATE"));
                resultBean.setTxnId((rs.getString("TXNID") == null) ? "--" : rs.getString("TXNID"));
                resultBean.setTxnTime((rs.getString("TXNTIME") == null) ? "--" : rs.getString("TXNTIME"));
                resultBean.setTxnType((rs.getString("TXNTYPECODE") == null) ? "--" : rs.getString("TXNTYPECODE"));
                resultBean.setTxnTypeDes((rs.getString("TXNDES") == null) ? "--" : rs.getString("TXNDES"));
                resultBean.setTxnStatus((rs.getString("status") == null) ? "--" : rs.getString("status"));
                resultBean.setTxnStatusSDes((rs.getString("STATUSDES") == null) ? "--" : rs.getString("STATUSDES"));

                txnList.add(resultBean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTxnDt.close();
        }
        return txnList;
    }
}
