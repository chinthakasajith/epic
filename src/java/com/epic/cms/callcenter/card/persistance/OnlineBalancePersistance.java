/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.card.persistance;

import com.epic.cms.callcenter.card.bean.CardBean;
import com.epic.cms.reportexp.transactionexp.bean.TxnExpDetailsBean;
import com.epic.cms.reportexp.transactionexp.bean.TxnExpHistoryViewBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author badrika
 */
public class OnlineBalancePersistance {
    private List<TxnExpDetailsBean> txnExpDetailBeanList = null;
    private List<TxnExpHistoryViewBean> txnExpHistoryViewBeanList;

    public CardBean getOnlineBalance(Connection onlineCon, String cardNum) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        CardBean cbean = new CardBean();

        try {
            String query = "select C.CREDITLIMIT,C.CASHLIMIT,C.OTBCREDIT,C.OTBCASH,C.STATUS,C.EXPDATE,"
                    + "S.DESCRIPTION "
                    + "from ECMS_ONLINE_CARD C, ECMS_ONLINE_STATUS S "
                    + "where CARDNUMBER=?";

            ps = onlineCon.prepareStatement(query);
            ps.setString(1, cardNum);

            rs = ps.executeQuery();
            while (rs.next()) {
                cbean.setCreditLimit(rs.getString("CREDITLIMIT"));
                cbean.setCashLimit(rs.getString("CASHLIMIT"));
                cbean.setOtbCredit(rs.getString("OTBCREDIT"));
                cbean.setOtbCash(rs.getString("OTBCASH"));
                cbean.setCardStatus(rs.getString("STATUS"));
                cbean.setStatusDes(rs.getString("DESCRIPTION"));
                cbean.setExpDate(rs.getString("EXPDATE"));

            }

            return cbean;


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

    public int getCountTxn(Connection con, String condition) throws Exception {

        int count = 0;
        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        try {
            strSqlBasic = "SELECT COUNT(*) AS Totaltxn FROM ECMS_ONLINE_TRANSACTION OL,ECMS_ONLINE_TXNTYPE TT,ECMS_ONLINE_STATUS ST  "
                    + "where st.code = ol.status AND tt.typecode=ol.txntypecode AND "+condition;

            System.out.println(strSqlBasic);

            getAllMasterCard = con.prepareStatement(strSqlBasic);

            rs = getAllMasterCard.executeQuery();

            while (rs.next()) {
                count = rs.getInt("Totaltxn");
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();
        }
        return count;
    }
    
     public List<TxnExpDetailsBean> getTxnExpDetails(Connection con, String condition, int start, int end) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;

        try {
            strSqlBasic = " SELECT * FROM ( SELECT * FROM ( SELECT OL.TXNID, OL.authcode, "
                    + " OL.BIN,OL.CARDNO,  OL.EXPIRYDATE,  OL.mid ,  OL.status,  OL.tid,  OL.traceno , "
                    + " OL.txndate, OL.txntime, OL.txntypecode, OL.txncurrency , OL.txnamount, OL.settlementdate, OL.servicecode, "
                    + " st.description AS STATUSDES, tt.descryption AS TXNDES, ROWNUM r FROM ECMS_ONLINE_TRANSACTION OL, "
                    + "ECMS_ONLINE_STATUS ST, ECMS_ONLINE_TXNTYPE TT WHERE st.code = ol.status AND tt.typecode=ol.txntypecode AND " + condition + " )"
                    + " WHERE r <=  " + end + "  ) WHERE r > " + start;

            System.out.println("************" + strSqlBasic);
            getAllMasterCard = con.prepareStatement(strSqlBasic);

            rs = getAllMasterCard.executeQuery();
            txnExpDetailBeanList = new ArrayList<TxnExpDetailsBean>();

            while (rs.next()) {

                TxnExpDetailsBean resultBean = new TxnExpDetailsBean();

                resultBean.setAuthCode((rs.getString("AUTHCODE") == null) ? "--" : rs.getString("AUTHCODE"));
                resultBean.setBin((rs.getString("BIN") == null) ? "--" : rs.getString("BIN"));
                resultBean.setCardNumber((rs.getString("CARDNO") == null) ? "--" : rs.getString("CARDNO"));
                resultBean.setTxnAmount((rs.getString("TXNAMOUNT") == null) ? "--" : rs.getString("TXNAMOUNT"));
                resultBean.setExpiaryDate((rs.getString("EXPIRYDATE") == null) ? "--" : rs.getString("EXPIRYDATE"));
                resultBean.setMerchantId((rs.getString("MID") == null) ? "--" : rs.getString("MID"));
                resultBean.setSettlementDate((rs.getString("SETTLEMENTDATE") == null) ? "--" : rs.getString("SETTLEMENTDATE"));
                resultBean.setTerminalId((rs.getString("TID") == null) ? "--" : rs.getString("TID"));
                resultBean.setTraceNo((rs.getString("TRACENO") == null) ? "--" : rs.getString("TRACENO"));
                resultBean.setTxnCurrency((rs.getString("txncurrency") == null) ? "--" : rs.getString("txncurrency"));
                resultBean.setTxnDate((rs.getString("TXNDATE") == null) ? "--" : rs.getString("TXNDATE"));
                resultBean.setTxnId((rs.getString("TXNID") == null) ? "--" : rs.getString("TXNID"));
                resultBean.setTxnTime((rs.getString("TXNTIME") == null) ? "--" : rs.getString("TXNTIME"));
                resultBean.setTxnTypeCode((rs.getString("TXNTYPECODE") == null) ? "--" : rs.getString("TXNTYPECODE"));
                resultBean.setTxnTypeDes((rs.getString("TXNDES") == null) ? "--" : rs.getString("TXNDES"));
                resultBean.setStatus((rs.getString("status") == null) ? "--" : rs.getString("status"));
                resultBean.setStatusDes((rs.getString("STATUSDES") == null) ? "--" : rs.getString("STATUSDES"));

                txnExpDetailBeanList.add(resultBean);

            }

        } catch (Exception ex) {
             throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();
        }

        return txnExpDetailBeanList;
    }
    
       public List<TxnExpHistoryViewBean> getAllTxnHistoryViewDetails(Connection con, String txnId) throws Exception {
        
        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        
        try {         
            
            strSqlBasic = "select th.txnid,th.createtime,th.description,th.status, st.description as statusdes "
                    + "from ECMS_ONLINE_TRANS_HISTORY th,ECMS_ONLINE_STATUS st where st.code = th.status and th.txnid=? ";
            
            getAllMasterCard = con.prepareStatement(strSqlBasic);
            getAllMasterCard.setString(1, txnId);
                       
            rs = getAllMasterCard.executeQuery();
            
            txnExpHistoryViewBeanList = new ArrayList<TxnExpHistoryViewBean>();
            
            while (rs.next()) {
                
                TxnExpHistoryViewBean resultBean = new TxnExpHistoryViewBean();
                
                resultBean.setTxnId(rs.getString("txnid"));
                resultBean.setDescription(rs.getString("description"));
                resultBean.setStatus(rs.getString("status"));
                resultBean.setStatusDes(rs.getString("statusdes"));
                resultBean.setCreatedTime(rs.getString("createtime"));
                
                txnExpHistoryViewBeanList.add(resultBean);
            }
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();            
        }
        
        return txnExpHistoryViewBeanList;
    }
     
     
}
