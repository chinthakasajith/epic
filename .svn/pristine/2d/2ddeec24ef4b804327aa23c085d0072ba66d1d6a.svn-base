/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.manualtxn.persistance;

import com.epic.cms.mtmm.manualtxn.bean.SaleTxnBean;
import com.epic.cms.mtmm.manualtxn.bean.TxnDetailsBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nalin
 */
public class SaleTxnPersistance {

    private ResultSet rs;
    private SaleTxnBean saleTxnBean = null;
    private HashMap<String, String> terminalList = null;
    private List<SaleTxnBean> saleTxnList = null;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> currencyList = null;
    private TxnDetailsBean detailsBean = null;
    private SaleTxnBean settleBean = null;

    public SaleTxnBean getMerchantDetails(Connection CMSCon, String merchantID) throws Exception {
        saleTxnBean = new SaleTxnBean();
        PreparedStatement getMerchantStst = null;
        try {
            getMerchantStst = CMSCon.prepareStatement("SELECT ML.DESCRIPTION,MC.MERCHANTNAME "
                    + " FROM MERCHANTLOCATION ML, MERCHANTCUSTOMER MC "
                    + " WHERE ML.MERCHANTCUSTOMERNO= MC.MERCHANTCUSTOMERNO AND ML.MERCHANTID=?");

            getMerchantStst.setString(1, merchantID);
            rs = getMerchantStst.executeQuery();

            while (rs.next()) {

                saleTxnBean.setMerchantID(merchantID);
                saleTxnBean.setMerchantName(rs.getString("DESCRIPTION"));
                saleTxnBean.setMerchantCustomerName(rs.getString("MERCHANTNAME"));

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getMerchantStst.close();

        }

        return saleTxnBean;
    }

    public HashMap<String, String> getActiveManualTerminalOfMerchant(Connection CMSCon, String merchantID) throws Exception {

        terminalList = new HashMap<String, String>();
        PreparedStatement getTerminalList = null;
        try {
            getTerminalList = CMSCon.prepareStatement("SELECT T.TERMINALID,T.TERMINALID "
                    + " FROM TERMINAL T WHERE T.MERCHANTID= ? AND T.TERMINALSTATUS=? AND"
                    + " T.TERMINALID = (SELECT TERMINALID FROM COMMONPARAMETER)");

            getTerminalList.setString(1, merchantID);
            getTerminalList.setString(2, StatusVarList.ACTIVE_STATUS);

            rs = getTerminalList.executeQuery();

            while (rs.next()) {

                terminalList.put(rs.getString("TERMINALID"), rs.getString("TERMINALID"));
            }


        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTerminalList.close();

        }

        return terminalList;
    }

    public List<SaleTxnBean> getTerminalTxnInfo(Connection CMSOnline, String terminalID, String merchantId) throws Exception {

        saleTxnList = new ArrayList<SaleTxnBean>();
        PreparedStatement getTxnList = null;
        try {
            getTxnList = CMSOnline.prepareStatement("SELECT T.TXNID, T.TXNCURRENCY,"
                    + " T.TXNAMOUNT,T.AUTHCODE,T.STATUS,C.DESCRIPTION AS CDESCRIPTION,"
                    + " S.DESCRIPTION AS SDESCRIPTION "
                    + " FROM ECMS_ONLINE_TRANSACTION T, ECMS_ONLINE_CURRENCY_CODE C,ECMS_ONLINE_STATUS S "
                    + " WHERE T.TXNCURRENCY = C.NOCODE AND T.STATUS = S.CODE AND T.TID= ? AND T.MID= ? ");

            getTxnList.setString(1, terminalID);
            getTxnList.setString(2, merchantId);

            rs = getTxnList.executeQuery();

            while (rs.next()) {

                SaleTxnBean bean = new SaleTxnBean();

                bean.setTxnID(rs.getString("TXNID"));
                bean.setTerminalID(terminalID);
                bean.setMerchantID(merchantId);
                // bean.setCvvValue(rs.getString(""));
                bean.setCurrencyType(rs.getString("TXNCURRENCY"));
                bean.setCurrencyTypeDes(rs.getString("CDESCRIPTION"));
                bean.setTxnAmount(rs.getString("TXNAMOUNT"));
                bean.setAuthCode(rs.getString("AUTHCODE"));
                bean.setStatus(Integer.toString(rs.getInt("STATUS")));
                bean.setStatusDes(rs.getString("SDESCRIPTION"));

                saleTxnList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTxnList.close();

        }

        return saleTxnList;
    }

    public List<SaleTxnBean> getManualTerminalTxnInfoToVoid(Connection CMSOnline, String terminalID, String merchantId) throws Exception {

        saleTxnList = new ArrayList<SaleTxnBean>();
        PreparedStatement getTxnList = null;
        try {
            getTxnList = CMSOnline.prepareStatement("SELECT T.TXNID, T.TXNCURRENCY,"
                    + " T.TXNAMOUNT,T.AUTHCODE,T.STATUS,C.DESCRIPTION AS CDESCRIPTION,"
                    + " S.DESCRIPTION AS SDESCRIPTION "
                    + " FROM ECMS_ONLINE_MANUAL_TRANSACTION T, ECMS_ONLINE_CURRENCY_CODE C,ECMS_ONLINE_STATUS S "
                    + " WHERE T.TXNCURRENCY = C.NOCODE AND T.STATUS = S.CODE AND T.TID= ? AND T.MID= ?  "
                    + " AND T.STATUS = ? AND T.RESPONSECODE = ?");

            getTxnList.setString(1, terminalID);
            getTxnList.setString(2, merchantId);
            getTxnList.setInt(3, StatusVarList.ONLINE_TXN_COMPLETE_STATUS);
            getTxnList.setString(4, StatusVarList.MANUAL_TXN_SUCCESS_STATUS);

            rs = getTxnList.executeQuery();

            while (rs.next()) {

                SaleTxnBean bean = new SaleTxnBean();

                bean.setTxnID(rs.getString("TXNID"));
                bean.setTerminalID(terminalID);
                bean.setMerchantID(merchantId);
                // bean.setCvvValue(rs.getString(""));
                bean.setCurrencyType(rs.getString("TXNCURRENCY"));
                bean.setCurrencyTypeDes(rs.getString("CDESCRIPTION"));
                bean.setTxnAmount(Double.toString(Double.parseDouble(rs.getString("TXNAMOUNT"))));
                bean.setAuthCode(rs.getString("AUTHCODE"));
                bean.setStatus(Integer.toString(rs.getInt("STATUS")));
                bean.setStatusDes(rs.getString("SDESCRIPTION"));
                // bean.setBatchNo(rs.getString("BATCHNO"));

                saleTxnList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTxnList.close();

        }

        return saleTxnList;
    }

    public List<SaleTxnBean> getManualTerminalTxnInfoToSettle(Connection CMSOnline, String terminalID, String merchantId) throws Exception {

        saleTxnList = new ArrayList<SaleTxnBean>();
        PreparedStatement getTxnList = null;
        try {
            getTxnList = CMSOnline.prepareStatement("SELECT T.TXNID, T.TXNCURRENCY,"
                    + " T.TXNAMOUNT,T.AUTHCODE,T.STATUS,C.DESCRIPTION AS CDESCRIPTION,"
                    + " S.DESCRIPTION AS SDESCRIPTION "
                    + " FROM ECMS_ONLINE_MANUAL_TRANSACTION T, ECMS_ONLINE_CURRENCY_CODE C,ECMS_ONLINE_STATUS S "
                    + " WHERE T.TXNCURRENCY = C.NOCODE AND T.STATUS = S.CODE AND T.TID= ? AND T.MID= ?  "
                    + " AND T.STATUS = ? AND T.RESPONSECODE = ?");

            getTxnList.setString(1, terminalID);
            getTxnList.setString(2, merchantId);
            getTxnList.setInt(3, StatusVarList.ONLINE_TXN_COMPLETE_STATUS);
            getTxnList.setString(4, StatusVarList.MANUAL_TXN_SUCCESS_STATUS);

            rs = getTxnList.executeQuery();

            while (rs.next()) {

                SaleTxnBean bean = new SaleTxnBean();

                bean.setTxnID(rs.getString("TXNID"));
                bean.setTerminalID(terminalID);
                bean.setMerchantID(merchantId);
                // bean.setCvvValue(rs.getString(""));
                bean.setCurrencyType(rs.getString("TXNCURRENCY"));
                bean.setCurrencyTypeDes(rs.getString("CDESCRIPTION"));
                bean.setTxnAmount(Double.toString(Double.parseDouble(rs.getString("TXNAMOUNT"))));
                bean.setAuthCode(rs.getString("AUTHCODE"));
                bean.setStatus(Integer.toString(rs.getInt("STATUS")));
                bean.setStatusDes(rs.getString("SDESCRIPTION"));
                // bean.setBatchNo(rs.getString("BATCHNO"));

                saleTxnList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTxnList.close();

        }

        return saleTxnList;
    }

    public List<SaleTxnBean> getSuccessTerminalTxnInfo(Connection CMSOnline, String terminalID, String merchantId) throws Exception {

        saleTxnList = new ArrayList<SaleTxnBean>();
        PreparedStatement getTxnList = null;
        try {
            getTxnList = CMSOnline.prepareStatement("SELECT T.TXNID, T.TXNCURRENCY,"
                    + " T.TXNAMOUNT,T.AUTHCODE,T.STATUS,C.DESCRIPTION AS CDESCRIPTION,"
                    + " S.DESCRIPTION AS SDESCRIPTION "
                    + " FROM ECMS_ONLINE_TRANSACTION T, ECMS_ONLINE_CURRENCY_CODE C,ECMS_ONLINE_STATUS S "
                    + " WHERE T.TXNCURRENCY = C.NOCODE AND T.STATUS = S.CODE AND T.TID= ? AND T.MID= ?");

            getTxnList.setString(1, terminalID);
            getTxnList.setString(2, merchantId);

            rs = getTxnList.executeQuery();

            while (rs.next()) {

                SaleTxnBean bean = new SaleTxnBean();

                bean.setTxnID(rs.getString("TXNID"));
                // bean.setCvvValue(rs.getString(""));
                bean.setCurrencyType(rs.getString("TXNCURRENCY"));
                bean.setCurrencyTypeDes(rs.getString("CDESCRIPTION"));
                bean.setTxnAmount(Double.toString(Double.parseDouble(rs.getString("TXNAMOUNT"))));
                bean.setAuthCode(rs.getString("AUTHCODE"));
                bean.setStatus(Integer.toString(rs.getInt("STATUS")));
                bean.setStatusDes(rs.getString("SDESCRIPTION"));

                saleTxnList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTxnList.close();

        }

        return saleTxnList;
    }

    public HashMap<String, String> getCardTypes(Connection CMSCon) throws Exception {
        PreparedStatement getCardTypeList = null;
        cardTypeList = new HashMap<String, String>();
        try {
            getCardTypeList = CMSCon.prepareStatement(" SELECT C.CARDTYPECODE ,C.DESCRIPTION "
                    + " FROM CARDTYPE C WHERE C.STATUS = ?");

            getCardTypeList.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = getCardTypeList.executeQuery();

            while (rs.next()) {

                cardTypeList.put(rs.getString("CARDTYPECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getCardTypeList.close();

        }
        return cardTypeList;
    }

    public HashMap<String, String> getCurrency(Connection CMSCon) throws Exception {
        PreparedStatement getcurrencyList = null;
        currencyList = new HashMap<String, String>();
        try {
            getcurrencyList = CMSCon.prepareStatement(" SELECT C.CURRENCYNUMCODE, C.DESCRIPTION "
                    + " FROM CURRENCY C WHERE C.STATUS = ?");

            getcurrencyList.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = getcurrencyList.executeQuery();

            while (rs.next()) {

                currencyList.put(this.zeroPadding(Integer.toString(rs.getInt("CURRENCYNUMCODE"))), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getcurrencyList.close();

        }
        return currencyList;
    }

    public String getResponsemessage(Connection CMSCon, String responseCode) throws Exception {
        PreparedStatement getcurrencyList = null;
        String responseMsg = null;
        try {
            getcurrencyList = CMSCon.prepareStatement("SELECT DESCRIPTION FROM ECMS_ONLINE_RESPONSE_CODE WHERE CODE = ?");

            getcurrencyList.setString(1, responseCode);
            rs = getcurrencyList.executeQuery();

            while (rs.next()) {

                responseMsg = rs.getString("DESCRIPTION");
            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getcurrencyList.close();

        }
        return responseMsg;
    }

    public TxnDetailsBean getTxnDetails(Connection CMSConOnline, String txnId, String tid, String mid) throws Exception {
        PreparedStatement getTxnInfo = null;
        detailsBean = new TxnDetailsBean();
        try {

            getTxnInfo = CMSConOnline.prepareStatement("SELECT T.MTI,T.RESPONSEMTI,T.CARDNO,"
                    + " T.EXPIRYDATE,T.TXNAMOUNT,L.DESCRIPTION AS LDESCRIPTION,C.DESCRIPTION AS CDESCRIPTION,"
                    + " P.DESCRIPTION AS PDESCRIPTION,S.DESCRIPTION AS SDESCRIPTION,CU.DESCRIPTION AS CUDESCRIPTION,"
                    + " ST.DESCRIPTION AS STDESCRIPTION "
                    + " FROM ECMS_ONLINE_TRANSACTION T,ECMS_ONLINE_LISTENER_TYPE L,ECMS_ONLINE_CHANNEL_TYPE C,"
                    + " ECMS_ONLINE_PAY_TYPE P,ECMS_ONLINE_SERVICE_CODE S,ECMS_ONLINE_CURRENCY_CODE CU,"
                    + " ECMS_ONLINE_STATUS ST "
                    + " WHERE T.LISTENERTYPE = L.CODE AND T.CHANNELTYPE = C.CODE "
                    + " AND T.PAYTYPE =P. CODE AND T.TXNCURRENCY =  CU.NOCODE "
                    + " AND T.STATUS = ST.CODE AND T.TXNID = ?  ");

            getTxnInfo.setString(1, txnId);
            rs = getTxnInfo.executeQuery();

            while (rs.next()) {

                detailsBean.setTxnId(txnId);
                detailsBean.setMti(rs.getString("MTI"));
                detailsBean.setResponceMti(rs.getString("RESPONSEMTI"));
                detailsBean.setCardNumber(rs.getString("CARDNO"));
                detailsBean.setExpiryDate(rs.getString("EXPIRYDATE"));
                detailsBean.setTxnAmount(Double.toString(Double.parseDouble(rs.getString("TXNAMOUNT"))));
                detailsBean.setListnerType(rs.getString("LDESCRIPTION"));
                detailsBean.setChannelType(rs.getString("CDESCRIPTION"));
                detailsBean.setPayType(rs.getString("PDESCRIPTION"));
                // detailsBean.setServiceCode(rs.getString("SDESCRIPTION"));
                detailsBean.setTxnCurrency(rs.getString("CUDESCRIPTION"));
                detailsBean.setStatus(rs.getString("STDESCRIPTION"));

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTxnInfo.close();

        }
        return detailsBean;
    }

    public String getMaxBatchId(Connection CMSConOnline, String mid, String tid) throws Exception {
        PreparedStatement getBatchId = null;
        String batchId = null;
        try {

            getBatchId = CMSConOnline.prepareStatement("SELECT MAX(M.BATCHNO) AS BATCHNO "
                    + " FROM ECMS_ONLINE_MANUAL_TRANSACTION M WHERE M.STATUS = ?"
                    + " AND M.MID= ? AND M.TID= ?");

            getBatchId.setInt(1, StatusVarList.ONLINE_TXN_SETTLED_STATUS);
            getBatchId.setString(2, mid);
            getBatchId.setString(3, tid);

            rs = getBatchId.executeQuery();

            while (rs.next()) {

                batchId = rs.getString("BATCHNO");
            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getBatchId.close();
        }
        return batchId;
    }

    public String generateTxnId(Connection CMSConOnline) throws Exception {
        PreparedStatement getTxnId = null;
        String txnId = null;
        try {

            getTxnId = CMSConOnline.prepareStatement("SELECT MAX(M.TXNID) AS TXNID "
                    + " FROM ECMS_ONLINE_MANUAL_TRANSACTION M ");

            rs = getTxnId.executeQuery();

            while (rs.next()) {

                txnId = rs.getString("TXNID");
            }
        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTxnId.close();
        }
        return txnId;
    }

    public boolean insertToManualTxnTable(Connection CMSConOnline, SaleTxnBean bean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {

            insertStat = CMSConOnline.prepareStatement("INSERT INTO ECMS_ONLINE_MANUAL_TRANSACTION(MID,TID,"
                    + " TXNTYPECODE,CARDNO,EXPIRYDATE,TXNCURRENCY,TXNAMOUNT,TRACENO,INVOICENO,"
                    + " STATUS,TXNID,CREATETIME,LASTUPDATETIME)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE)");

            insertStat.setString(1, bean.getMerchantID());
            insertStat.setString(2, bean.getTerminalID());
            insertStat.setInt(3, Integer.parseInt(bean.getTxntype()));
            insertStat.setString(4, bean.getCardNumber());
            insertStat.setString(5, bean.getExpiryDate());
            insertStat.setString(6, bean.getCurrencyType());
            insertStat.setString(7, bean.getTxnAmount());
            insertStat.setString(8, bean.getTraceNo());
            insertStat.setString(9, bean.getInvoiceNo());
            insertStat.setInt(10, StatusVarList.ONLINE_TXN_INITIATE_STATUS);
            insertStat.setString(11, bean.getTxnID());

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }

        return success;
    }

    public boolean updateManualTxnTable(Connection CMSConOnline, SaleTxnBean bean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = CMSConOnline.prepareStatement("UPDATE ECMS_ONLINE_MANUAL_TRANSACTION "
                    + " SET RRN = ?, AUTHCODE = ?, RESPONSECODE = ?, TXNTIME = ?,TXNDATE = ?, STATUS =?,"
                    + " LASTUPDATETIME= SYSDATE"
                    + " WHERE TXNID =? ");

            updateStat.setString(1, bean.getRrn());
            updateStat.setString(2, bean.getAuthCode());
            updateStat.setString(3, bean.getResponceCode());
            updateStat.setString(4, bean.getTxnTime());
            updateStat.setString(5, bean.getTxnDate());
            updateStat.setInt(6, StatusVarList.ONLINE_TXN_COMPLETE_STATUS);
            updateStat.setString(7, bean.getTxnID());

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public SaleTxnBean getOriginalTxnData(Connection CMSConOnline, SaleTxnBean voidBean) throws Exception {
        PreparedStatement getTxnData = null;
        try {

            getTxnData = CMSConOnline.prepareStatement("SELECT T.CARDNO,T.TXNAMOUNT,T.INVOICENO,"
                    + " T.TXNTIME,T.TXNDATE,T.BATCHNO "
                    + " FROM ECMS_ONLINE_MANUAL_TRANSACTION T WHERE T.TXNID = ? AND T.MID = ? AND T.TID = ?");

            getTxnData.setString(1, voidBean.getTxnID());
            getTxnData.setString(2, voidBean.getMerchantID());
            getTxnData.setString(3, voidBean.getTerminalID());

            rs = getTxnData.executeQuery();

            while (rs.next()) {

                voidBean.setCardNumber(rs.getString("CARDNO"));
                voidBean.setTxnAmount(rs.getString("TXNAMOUNT"));
                voidBean.setInvoiceNo(rs.getString("INVOICENO"));
                voidBean.setTxnTime(rs.getString("TXNTIME"));
                voidBean.setTxnDate(rs.getString("TXNDATE"));
                voidBean.setBatchNo(rs.getString("BATCHNO"));
            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTxnData.close();
        }
        return voidBean;
    }

    public boolean voidSelectedTransaction(Connection CMSConOnline, SaleTxnBean voidBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {

            updateStat = CMSConOnline.prepareStatement("UPDATE ECMS_ONLINE_MANUAL_TRANSACTION "
                    + " SET STATUS = ? , LASTUPDATETIME= SYSDATE "
                    + " WHERE TXNID = ?");

            updateStat.setInt(1, StatusVarList.ONLINE_TXN_VOID_STATUS);
            updateStat.setString(2, voidBean.getTxnID());

            updateStat.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public String getTxnAmount(Connection CMSConOnline, String txnId) throws Exception {
        PreparedStatement getTxnAmount = null;
        String txnAmount = null;
        try {
            getTxnAmount = CMSConOnline.prepareStatement("SELECT T.TXNAMOUNT "
                    + " FROM ECMS_ONLINE_MANUAL_TRANSACTION T WHERE T.TXNID = ?");

            getTxnAmount.setString(1, txnId);

            rs = getTxnAmount.executeQuery();

            while (rs.next()) {

                txnAmount = rs.getString("TXNAMOUNT");
            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTxnAmount.close();
        }
        return txnAmount;
    }

    public boolean settledSelectedTransaction(Connection CMSConOnline, SaleTxnBean settleBean, int status, String txnId) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {

            updateStat = CMSConOnline.prepareStatement("UPDATE ECMS_ONLINE_MANUAL_TRANSACTION "
                    + " SET STATUS = ?, BATCHNO=?, SETTLEMENTDATE=?, LASTUPDATETIME = SYSDATE "
                    + " WHERE TXNID = ?");

            updateStat.setInt(1, status);
            updateStat.setString(2, settleBean.getBatchNo());
            updateStat.setString(3, settleBean.getTxnDate());
            updateStat.setString(4, txnId);

            updateStat.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean settleFailedSelectedTransaction(Connection CMSConOnline, int status, String txnId) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {

            updateStat = CMSConOnline.prepareStatement("UPDATE ECMS_ONLINE_MANUAL_TRANSACTION "
                    + " SET STATUS = ?, LASTUPDATETIME= SYSDATE"
                    + " WHERE TXNID = ?");

            updateStat.setInt(1, status);
            updateStat.setString(2, txnId);

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean updateBatchUploadStatus(Connection CMSConOnline, int status, String txnId) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {

            updateStat = CMSConOnline.prepareStatement("UPDATE ECMS_ONLINE_MANUAL_TRANSACTION "
                    + " SET STATUS = ?, LASTUPDATETIME = SYSDATE"
                    + " WHERE TXNID = ?");

            updateStat.setInt(1, status);
            updateStat.setString(2, txnId);

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public SaleTxnBean getOriginalTxnDataToBatchUpload(Connection CMSConOnline, String txmId) throws Exception {
        PreparedStatement getTxnData = null;
        settleBean = new SaleTxnBean();
        try {

            getTxnData = CMSConOnline.prepareStatement("SELECT T.CARDNO,T.TXNAMOUNT,T.INVOICENO,"
                    + " T.TXNTIME,T.TXNDATE,T.MID,T.TID "
                    + " FROM ECMS_ONLINE_MANUAL_TRANSACTION T WHERE T.TXNID = ? ");

            getTxnData.setString(1, txmId);


            rs = getTxnData.executeQuery();

            while (rs.next()) {

                settleBean.setCardNumber(rs.getString("CARDNO"));
                settleBean.setTxnAmount(rs.getString("TXNAMOUNT"));
                settleBean.setInvoiceNo(rs.getString("INVOICENO"));
                settleBean.setTxnTime(rs.getString("TXNTIME"));
                settleBean.setTxnDate(rs.getString("TXNDATE"));
                settleBean.setTerminalID(rs.getString("TID"));
                settleBean.setMerchantID(rs.getString("MID"));

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTxnData.close();
        }
        return settleBean;
    }
    
    public String getManualTerminal(Connection CMSCon, String merchantId) throws Exception {
        PreparedStatement getManTerminal = null;
        String tId = null;
        try {
            getManTerminal = CMSCon.prepareStatement("SELECT TERMINALID FROM COMMONPARAMETER ");

            rs = getManTerminal.executeQuery();

            while (rs.next()) {

                tId = rs.getString("TERMINALID");
            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getManTerminal.close();

        }
        return tId;
    }

    public String zeroPadding(String code) {
        int len = code.length();

        if (len < 3 && len == 2) {
            code = "0" + code;
        } else if (len < 3 && len == 1) {
            code = "00" + code;
        }
        return code;
    }
}
