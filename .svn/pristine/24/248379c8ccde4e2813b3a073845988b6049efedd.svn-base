/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.transactionexp.persistance;

import com.epic.cms.reportexp.transactionexp.bean.TxnExpDetailsBean;
import com.epic.cms.reportexp.transactionexp.bean.TxnExpHistoryViewBean;
import com.epic.cms.reportexp.transactionexp.bean.TxnExpSearchBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class TransactionExpMgtPersistance {

    private HashMap<String, String> txnTypeList = null;
    private HashMap<String, String> txnStatusList = null;
    private List<TxnExpDetailsBean> txnExpDetailBeanList = null;
    private List<TxnExpHistoryViewBean> txnExpHistoryViewBeanList = null;

    public HashMap<String, String> getAllTransactionTypes(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT TT.TYPECODE,TT.DESCRYPTION FROM ECMS_ONLINE_TXNTYPE TT");

            rs = getAllUserRole.executeQuery();
            txnTypeList = new HashMap<String, String>();

            while (rs.next()) {

                txnTypeList.put(rs.getString("TYPECODE"), rs.getString("DESCRYPTION"));


            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return txnTypeList;
    }

    public HashMap<String, String> getAllTransactionStatus(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT ST.CODE,ST.DESCRIPTION FROM ECMS_ONLINE_STATUS ST WHERE ST.CATEGORYCODE =?");

            getAllUserRole.setInt(1, 1);
            rs = getAllUserRole.executeQuery();
            txnStatusList = new HashMap<String, String>();

            while (rs.next()) {

                txnStatusList.put(rs.getString("CODE"), rs.getString("DESCRIPTION"));


            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return txnStatusList;
    }

    public List<TxnExpDetailsBean> getAllTxnExpDetails(Connection con, TxnExpSearchBean searchBean) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;

        try {


            strSqlBasic = "SELECT OL.TXNID,OL.authcode,OL.BIN,OL.countrycode,OL.CARDNO,OL.EXPIRYDATE,OL.invoiceno,OL.mid,OL.rrn,OL.status,OL.tid,OL.traceno,OL.txndate,"
                    + "OL.txntime,OL.txntypecode,OL.txncurrency,OL.txnamount,OL.settlementdate,OL.servicecode , st.description AS STATUSDES,"
                    + " tt.descryption AS TXNDES FROM ECMS_ONLINE_TRANSACTION OL,ECMS_ONLINE_STATUS ST ,ECMS_ONLINE_TXNTYPE TT ";



            String condition = "";


            if (!searchBean.getBin().contentEquals("") || searchBean.getBin().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " ol.bin = '" + searchBean.getBin() + "'";
            }

            if (!searchBean.getCardNumber().contentEquals("") || searchBean.getCardNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " ol.CARDNO = '" + searchBean.getCardNumber() + "'";
            }


            if (!searchBean.getMerchantId().contentEquals("") || searchBean.getMerchantId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " ol.mid = '" + searchBean.getMerchantId() + "'";
            }
            if (!searchBean.getTerminalId().contentEquals("") || searchBean.getTerminalId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " ol.tid = '" + searchBean.getTerminalId() + "'";
            }
            if (!searchBean.getTxnStatus().contentEquals("") || searchBean.getTxnStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " ol.status = '" + searchBean.getTxnStatus() + "'";
            }
            if (!searchBean.getTxnType().contentEquals("") || searchBean.getTxnType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " ol.txntypecode = '" + searchBean.getTxnType() + "'";
            }




            if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ol.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND ol.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
            } else {

                if (!searchBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "ol.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!searchBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "ol.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
                }
            }






            if (!condition.equals("")) {
                strSqlBasic += " WHERE st.code = ol.status AND tt.typecode = OL.txntypecode AND " + condition;
            } else {
                strSqlBasic += " WHERE st.code = ol.status AND tt.typecode = OL.txntypecode";
            }



            getAllMasterCard = con.prepareStatement(strSqlBasic);


            rs = getAllMasterCard.executeQuery();
            txnExpDetailBeanList = new ArrayList<TxnExpDetailsBean>();


            while (rs.next()) {


                TxnExpDetailsBean resultBean = new TxnExpDetailsBean();

                resultBean.setAuthCode(rs.getString("AUTHCODE"));
                resultBean.setBin(rs.getString("BIN"));
                resultBean.setCardNumber(rs.getString("CARDNO"));
                resultBean.setTxnAmount(rs.getString("TXNAMOUNT"));
                resultBean.setExpiaryDate(rs.getString("EXPIRYDATE"));
                resultBean.setMerchantId(rs.getString("MID"));
                resultBean.setSettlementDate(rs.getString("SETTLEMENTDATE"));
                resultBean.setTerminalId(rs.getString("TID"));
                resultBean.setTraceNo(rs.getString("TRACENO"));
                resultBean.setTxnCurrency(rs.getString("txncurrency"));
                resultBean.setTxnDate(rs.getString("TXNDATE"));
                resultBean.setTxnId(rs.getString("TXNID"));
                resultBean.setTxnTime(rs.getString("TXNTIME"));
                resultBean.setTxnTypeCode(rs.getString("TXNTYPECODE"));
                resultBean.setTxnTypeDes(rs.getString("TXNDES"));
                resultBean.setStatus(rs.getString("status"));
                resultBean.setStatusDes(rs.getString("STATUSDES"));

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
//convert string to Date type

    public List<TxnExpDetailsBean> getTxnExpDetails(Connection con, String condition, int start, int end) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;

        try {


            strSqlBasic = " SELECT * FROM ( SELECT * FROM ( SELECT OL.TXNID             ,  OL.authcode                , "
                    + " OL.BIN,OL.CARDNO,  OL.EXPIRYDATE,  OL.mid ,  OL.status,  OL.tid,  OL.traceno ,"
                    + " OL.txndate,  OL.txntime,  OL.txntypecode,  OL.txncurrency ,  OL.txnamount,  OL.settlementdate,  OL.servicecode             , "
                    + " st.description AS STATUSDES,  tt.descryption AS TXNDES,  ROWNUM r  FROM ECMS_ONLINE_TRANSACTION OL,  "
                    + "ECMS_ONLINE_STATUS ST          ,  ECMS_ONLINE_TXNTYPE TT WHERE st.code = ol.status AND tt.typecode=ol.txntypecode AND " + condition + "  )"
                    + " WHERE r <=  " + end + "  ) WHERE r > " + start;




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

    public int getCountTxn(Connection con ,String condition) throws Exception {

        int count = 0;
        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        try {


            strSqlBasic = "SELECT COUNT(*) AS Totaltxn FROM ECMS_ONLINE_TRANSACTION OL,ECMS_ONLINE_TXNTYPE TT,ECMS_ONLINE_STATUS ST  where st.code = ol.status AND tt.typecode=ol.txntypecode AND "+condition;


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
    
    
    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);


        return (dateFormat.format(convertedDate));



    }
}
