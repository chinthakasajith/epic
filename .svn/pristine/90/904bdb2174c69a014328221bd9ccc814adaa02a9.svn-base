/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodfilegeneration.persistance;

import com.epic.cms.backoffice.eodfilegeneration.bean.SettlementFileBean;
import com.epic.cms.backoffice.eodfilegeneration.bean.SettlementFileParamBean;
import com.epic.cms.backoffice.eodfilegeneration.bean.TxnToEodBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class EodFileGenerationMgtPersistance {

    private List<TxnToEodBean> txnToEodBeanlist = null;
    private List<SettlementFileBean> eodFileBeanlist = null;

    public List<TxnToEodBean> getAllTxnToEod(Connection con, String terminalId, String startTime, String endTime) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;

        try {


            strSqlBasic = "select ET.CHANNELTYPE,et.MID,ET.TID,ET.TXNTYPECODE,ET.TXNID,ET.TXNCURRENCY,ET.TRACENO,ET.STATUS,ET.SETTLEMENTDATE,"
                    + "ET.SETTLEMENTCURRENCY,ET.SERVICECODE,ET.MTI,ET.caic,ET.createtime,ET.fiic,ET.lastupdatetime,ET.localdate,ET.localtime,"
                    + "ET.tag5f2a,ET.tag9a,ET.tag9c,ET.tag9f02,ET.tag9f03,ET.tag9f1a,ET.tag9f1e,ET.tag9f27,ET.tag9f33,ET.tag9f34,ET.tag9f35,"
                    + "ET.tag9f41,ET.txndate,ET.txntime,ET.aiic,ET.authcode,ET.batchno,ET.billingamount,ET.billingcurrency,ET.bin,ET.cardno,"
                    + "ET.countrycode,ET.expirydate,ET.fiic,ET.invoiceno,ET.listenertype,ET.mcc,ET.nii,ET.offmti,ET.offprocessingcode,"
                    + "ET.offresponsemti,ET.onoffststus,ET.posconditioncode,ET.posentrymode,ET.processingcode,ET.requestfrom,ET.responsecode,"
                    + "ET.responsemti,ET.rrn,ET.settlementamount,ET.settlementtxncount from ECMS_ONLINE_TRANSACTION ET,ECMS_ONLINE_STATUS st,"
                    + "ECMS_ONLINE_TXNTYPE tt";
//                    +"ECMS_ONLINE_TXN_TYPE_CODE tt";


            String condition = "";


            if (!terminalId.equals("")) {

                if (!condition.equals("")) {
                    condition += " AND ";
                }

                condition += "ET.TID = '" + terminalId + "' ";
            }

            if (!startTime.contentEquals("") && !endTime.contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ET.settlementdate >= to_Date('" + startTime + "','YYYY-MM-DD HH24:MI') AND ET.settlementdate <= to_Date('" + endTime + "','YYYY-MM-DD HH24:MI')";
            } else {

                if (!startTime.contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "ET.settlementdate >= to_Date('" + startTime + "','YYYY-MM-DD HH24:MI')";
                }
                if (!endTime.contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "ET.settlementdate <= to_Date('" + endTime + "','YYYY-MM-DD HH24:MI')";
                }
            }


            if (!condition.equals("")) {
                strSqlBasic += " WHERE st.code = ET.status AND tt.typecode = ET.txntypecode AND ET.STATUS = " + StatusVarList.ONLINE_TXN_SETTLED_STATUS + " AND " + condition;
            } else {
                strSqlBasic += " WHERE st.code = ET.status AND tt.typecode = ET.txntypecode AND ET.STATUS = " + StatusVarList.ONLINE_TXN_SETTLED_STATUS + "";
            }


            getAllMasterCard = con.prepareStatement(strSqlBasic);


            rs = getAllMasterCard.executeQuery();
            txnToEodBeanlist = new ArrayList<TxnToEodBean>();

            while (rs.next()) {

                TxnToEodBean resultBean = new TxnToEodBean();

                resultBean.setAiic((rs.getString("AIIC") == null) ? "-" : rs.getString("AIIC"));
                resultBean.setCaic((rs.getString("CAIC") == null) ? "-" : rs.getString("CAIC"));
                resultBean.setAuthCode((rs.getString("AUTHCODE") == null) ? "-" : rs.getString("AUTHCODE"));
                resultBean.setBatchNo((rs.getString("BATCHNO") == null) ? "-" : rs.getString("BATCHNO"));
                resultBean.setChannelType((rs.getString("CHANNELTYPE") == null) ? "-" : rs.getString("CHANNELTYPE"));
                resultBean.setBillAmount((rs.getString("BILLINGAMOUNT") == null) ? "0" : rs.getString("BILLINGAMOUNT"));
                resultBean.setBin((rs.getString("BIN") == null) ? "-" : rs.getString("BIN"));
                resultBean.setCountryCode((rs.getString("COUNTRYCODE") == null) ? "-" : rs.getString("COUNTRYCODE"));
                resultBean.setCardNo((rs.getString("CARDNO") == null) ? "-" : rs.getString("CARDNO"));
                resultBean.setExpiryDate((rs.getString("EXPIRYDATE") == null) ? "-" : rs.getString("EXPIRYDATE"));
                resultBean.setFiic((rs.getString("FIIC") == null) ? "-" : rs.getString("FIIC"));
                resultBean.setInvoiceNo((rs.getString("INVOICENO") == null) ? "-" : rs.getString("INVOICENO"));
                resultBean.setListenerType((rs.getString("LISTENERTYPE") == null) ? "-" : rs.getString("LISTENERTYPE"));
                resultBean.setLocalDate((rs.getString("LOCALDATE") == null) ? "-" : rs.getString("LOCALDATE"));
                resultBean.setMcc((rs.getString("MCC") == null) ? "-" : rs.getString("MCC"));
                resultBean.setMti((rs.getString("MTI") == null) ? "-" : rs.getString("MTI"));
                resultBean.setmId((rs.getString("MID") == null) ? "-" : rs.getString("MID"));
                resultBean.setNii((rs.getString("NII") == null) ? "-" : rs.getString("NII"));
                resultBean.setOffMti((rs.getString("OFFMTI") == null) ? "-" : rs.getString("OFFMTI"));
                resultBean.setOffProcessingCode((rs.getString("OFFPROCESSINGCODE") == null) ? "-" : rs.getString("OFFPROCESSINGCODE"));
                resultBean.setOffResponseMti((rs.getString("OFFRESPONSEMTI") == null) ? "-" : rs.getString("OFFRESPONSEMTI"));
                resultBean.setOnOffStatus((rs.getString("ONOFFSTSTUS") == null) ? "-" : rs.getString("ONOFFSTSTUS"));
                resultBean.setPostConditionCode((rs.getString("POSCONDITIONCODE") == null) ? "-" : rs.getString("POSCONDITIONCODE"));
                resultBean.setPostEntryMode((rs.getString("POSENTRYMODE") == null) ? "-" : rs.getString("POSENTRYMODE"));
                resultBean.setProcessingCode((rs.getString("PROCESSINGCODE") == null) ? "-" : rs.getString("PROCESSINGCODE"));
                resultBean.setRequestFrom((rs.getString("REQUESTFROM") == null) ? "-" : rs.getString("REQUESTFROM"));
                resultBean.setResponseCode((rs.getString("RESPONSECODE") == null) ? "-" : rs.getString("RESPONSECODE"));
                resultBean.setResponseMti((rs.getString("RESPONSEMTI") == null) ? "-" : rs.getString("RESPONSEMTI"));
                resultBean.setRrn((rs.getString("RRN") == null) ? "-" : rs.getString("RRN"));
                resultBean.setServiceCode((rs.getString("SERVICECODE") == null) ? "-" : rs.getString("SERVICECODE"));
                resultBean.setSettlementAmount((rs.getString("SETTLEMENTAMOUNT") == null) ? "0" : rs.getString("SETTLEMENTAMOUNT"));
                resultBean.setSettlementCurrency((rs.getString("SETTLEMENTCURRENCY") == null) ? "-" : rs.getString("SETTLEMENTCURRENCY"));

                resultBean.setSettlementDate(rs.getTimestamp("SETTLEMENTDATE"));

                resultBean.setSettlementTxnCount((rs.getString("SETTLEMENTTXNCOUNT") == null) ? "-" : rs.getString("SETTLEMENTTXNCOUNT"));
                resultBean.setStatus((rs.getString("STATUS") == null) ? "-" : rs.getString("STATUS"));
                resultBean.setTag5f2a((rs.getString("TAG5F2A") == null) ? "-" : rs.getString("TAG5F2A"));
                resultBean.setTag9a((rs.getString("TAG9A") == null) ? "-" : rs.getString("TAG9A"));
                resultBean.setTag9c((rs.getString("TAG9C") == null) ? "-" : rs.getString("TAG9C"));
                resultBean.setTag9f02((rs.getString("TAG9F02") == null) ? "-" : rs.getString("TAG9F02"));
                resultBean.setTag9f03((rs.getString("TAG9F03") == null) ? "-" : rs.getString("TAG9F03"));
                resultBean.setTag9f1A((rs.getString("TAG9F1A") == null) ? "-" : rs.getString("TAG9F1A"));
                resultBean.setTag9f1E((rs.getString("TAG9F1E") == null) ? "-" : rs.getString("TAG9F1E"));
                resultBean.setTag9f27((rs.getString("TAG9F27") == null) ? "-" : rs.getString("TAG9F27"));
                resultBean.setTag9f33((rs.getString("TAG9F33") == null) ? "-" : rs.getString("TAG9F33"));
                resultBean.setTag9f34((rs.getString("TAG9F34") == null) ? "-" : rs.getString("TAG9F34"));
                resultBean.setTag9f35((rs.getString("TAG9F35") == null) ? "-" : rs.getString("TAG9F35"));
                resultBean.setTag9f41((rs.getString("TAG9F41") == null) ? "-" : rs.getString("TAG9F41"));
                resultBean.setTraceNo((rs.getString("TRACENO") == null) ? "-" : rs.getString("TRACENO"));
                resultBean.setTxnCurrency((rs.getString("TXNCURRENCY") == null) ? "-" : rs.getString("TXNCURRENCY"));
                resultBean.setTxnDate((rs.getString("TXNDATE") == null) ? "-" : rs.getString("TXNDATE"));
                resultBean.setTxnId((rs.getString("TXNID") == null) ? "-" : rs.getString("TXNID"));
                resultBean.setTxnTime((rs.getString("TXNTIME") == null) ? "-" : rs.getString("TXNTIME"));
                resultBean.setTxnTypeCode((rs.getString("TXNTYPECODE") == null) ? "-" : rs.getString("TXNTYPECODE"));
                resultBean.settId((rs.getString("TID") == null) ? "-" : rs.getString("TID"));

                txnToEodBeanlist.add(resultBean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();

        }

        return txnToEodBeanlist;
    }

    public SettlementFileParamBean getSettlementFileParam(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        SettlementFileParamBean resultBean = null;

        try {


            strSqlBasic = "select sp.BATCHTRAILER,sp.FILETRAILER,sp.FILEID,sp.FILEHEADER,sp.BATCHHEADER,sp.TRANSACTIONHEADER,sp.FILEEXTENTION,sp.FILENAMEPRIFIX from SETTLEMENTFILEPARAMETER sp";


            getAllMasterCard = con.prepareStatement(strSqlBasic);


            rs = getAllMasterCard.executeQuery();

            while (rs.next()) {

                resultBean = new SettlementFileParamBean();

                resultBean.setFileId(rs.getString("FILEID"));
                resultBean.setFileHeader(rs.getString("FILEHEADER"));
                resultBean.setBatchHeader(rs.getString("BATCHHEADER"));
                resultBean.setTxnHeader(rs.getString("TRANSACTIONHEADER"));
                resultBean.setFileExtention(rs.getString("FILEEXTENTION"));
                resultBean.setFileNamePrefix(rs.getString("FILENAMEPRIFIX"));
                resultBean.setBatchTailer(rs.getString("BATCHTRAILER"));
                resultBean.setFileTailer(rs.getString("FILETRAILER"));


            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();

        }

        return resultBean;
    }

    public List<String> getAllTeminalDetails(Connection con, String startTime, String endTime) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        List<String> terminalList = null;

        try {


            strSqlBasic = "select DISTINCT ET.TID as terminalid from ECMS_ONLINE_TRANSACTION ET ";


            String condition = "";



            if (!startTime.contentEquals("") && !endTime.contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ET.settlementdate >= to_Date('" + startTime + "','YYYY-MM-DD HH24:MI') AND ET.settlementdate <= to_Date('" + endTime + "','YYYY-MM-DD HH24:MI')";
            } else {

                if (!startTime.contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "ET.settlementdate >= to_Date('" + startTime + "','YYYY-MM-DD HH24:MI')";
                }
                if (!endTime.contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "ET.settlementdate <= to_Date('" + endTime + "','YYYY-MM-DD HH24:MI')";
                }
            }


            if (!condition.equals("")) {
                strSqlBasic += " WHERE ET.Tid is not null AND ET.STATUS = 13 AND  " + condition;
            } else {
                strSqlBasic += " WHERE ET.Tid is not null AND ET.STATUS = 13";
            }



            getAllMasterCard = con.prepareStatement(strSqlBasic);
            terminalList = new ArrayList<String>();

            rs = getAllMasterCard.executeQuery();

            while (rs.next()) {

                terminalList.add(rs.getString("terminalid"));


            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();

        }

        return terminalList;
    }

    public boolean insertSettlementFile(Connection con, SettlementFileBean SFileBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO SETTLEMENTFILE(FILEID,FILENAME,STATUS,LASTUPDATEDUSER,ISTAKENTOEOD,LASTUPDATEDTIME,CREATEDTIME) VALUES(?,?,?,?,?,SYSDATE,SYSDATE) ");

            insertStat.setString(1, SFileBean.getFileId());
            insertStat.setString(2, SFileBean.getFileName());
            insertStat.setString(3, SFileBean.getStatus());
            insertStat.setString(4, SFileBean.getLastUpdatedUser());
            insertStat.setString(5, SFileBean.getIsTakenToEod());

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public List<SettlementFileBean> loadAllGeneratedEodFiles(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;

        try {


            strSqlBasic = "select sf.createdtime,sf.downloadstatus,sf.fileid,sf.filename,sf.istakentoeod,sf.lastupdateduser,"
                    + "sf.recordcount,sf.status,st.DESCRIPTION as statusdes,sf.totalcreditamount,sf.totaldebitamount "
                    + "from SETTLEMENTFILE SF,STATUS st where st.STATUSCODE = sf.status";

            getAllMasterCard = con.prepareStatement(strSqlBasic);


            rs = getAllMasterCard.executeQuery();
            eodFileBeanlist = new ArrayList<SettlementFileBean>();

            while (rs.next()) {

                SettlementFileBean resultBean = new SettlementFileBean();

                resultBean.setFileId(rs.getString("fileid"));
                resultBean.setFileName(rs.getString("filename"));
                resultBean.setStatus(rs.getString("status"));
                resultBean.setStatusDes(rs.getString("statusdes"));
                resultBean.setIsTakenToEod(rs.getString("istakentoeod"));
                resultBean.setLastUpdatedUser(rs.getString("lastupdateduser"));
//                resultBean.setCreatedTime();


                eodFileBeanlist.add(resultBean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();

        }

        return eodFileBeanlist;
    }
}
