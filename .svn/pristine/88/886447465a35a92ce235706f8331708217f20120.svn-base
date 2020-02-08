/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.persistance;

import com.epic.cms.reportexp.cardapplication.bean.OnlineCardBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author badrika
 */
public class OnlineCardInfoPersistance {
    private ResultSet rs;
    private OnlineCardBean bean=null;
    
    public OnlineCardBean getOnlineCard(Connection con, String cardNum) throws Exception {
        PreparedStatement ps = null;
        try {
            bean = new OnlineCardBean();
            
            String query = "SELECT C.CARDNUMBER,C.EXPDATE,C.CURRENCYNOCODE,C.STATUS,C.CARDDOMAIN,C.CARDTYPE, "
                    + "CUR.DESCRIPTION as currdes,ST.DESCRIPTION as stdes,CD.DESCRIPTION as cddes,CT.DESCRIPTION as ctdes, "
                    + "C.EFFECTDATE,C.NEWEXPDATE,C.PINISSUEDDATE,C.PINCOUNT,C.TXNCOUNT,C.CASHTXNCOUNT,C.TOTALTXNAMOUNT, "
                    + "C.TOTALCASHTXNAMOUNT,C.CREDITLIMIT,C.CASHLIMIT,C.OTBCREDIT,C.OTBCASH,C.TEMPCREDITAMOUNT, "
                    + "C.TEMPCASHAMOUNT,C.TEMPCREDITLIMITINCR,C.TEMPCASHLIMITINCR,C.TLISTARTDATE,C.TLIENDDATE, "
                    + "C.TLISTATUS,C.PAYTYPE,C.SERVICECODE,C.RISKPROFILECODE,C.IBMOFFSET,C.PINMETHOD,C.PVV,C.CREATETIME, "
                    + "C.LASTUPDATETIME,C.LASTUPDATEUSER,C.CARDVERIFICATIONMETHOD,C.EMVMETHOD "
                    + "FROM ECMS_ONLINE_CARD C,ECMS_ONLINE_CURRENCY_CODE CUR,ECMS_ONLINE_STATUS ST,ECMS_ONLINE_CARD_DOMAIN CD,"
                    + "ECMS_ONLINE_CARD_TYPE CT "
                    + "WHERE CARDNUMBER=? and C.CURRENCYNOCODE=CUR.NOCODE and C.STATUS=ST.CODE and C.CARDDOMAIN=CD.DOMAINID "
                    + "and C.CARDTYPE=CT.CODE ";

            ps = con.prepareStatement(query);
            
            ps.setString(1, cardNum);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {                            
                
                bean.setCardNum(rs.getString("CARDNUMBER"));
                bean.setExpDate(rs.getString("EXPDATE"));
                bean.setCurrencyCode(rs.getString("CURRENCYNOCODE"));
                bean.setCurrencyCodeDes(rs.getString("currdes"));
                bean.setCardStatus(rs.getString("STATUS"));
                bean.setCardStatusDes(rs.getString("stdes"));
                bean.setCardDomain(rs.getString("CARDDOMAIN"));
                bean.setCardDomainDes(rs.getString("cddes"));
                bean.setCardType(rs.getString("CARDTYPE"));
                bean.setCardTypeDes(rs.getString("ctdes"));
                
                bean.setEffrctDate(rs.getString("EFFECTDATE"));
                bean.setNewExpiryDate(rs.getString("NEWEXPDATE"));
                bean.setPinIssueSDate(rs.getString("PINISSUEDDATE"));
                bean.setPinCount(rs.getString("PINCOUNT"));
                bean.setTxnCount(rs.getString("TXNCOUNT"));
                bean.setCashTxnCount(rs.getString("CASHTXNCOUNT"));
                bean.setTotTxnAmount(rs.getString("TOTALTXNAMOUNT"));
                bean.setTotCashTxnAmount(rs.getString("TOTALCASHTXNAMOUNT"));
                bean.setCreditLimit(rs.getString("CREDITLIMIT"));
                bean.setCashLimit(rs.getString("CASHLIMIT"));
                bean.setOtdCredit(rs.getString("OTBCREDIT"));
                bean.setOtdCash(rs.getString("OTBCASH"));
                bean.setTempCreditAmount(rs.getString("TEMPCREDITAMOUNT"));
                bean.setTempCashAmount(rs.getString("TEMPCASHAMOUNT"));
                bean.setTempCreditLimitIncrment(rs.getString("TEMPCREDITLIMITINCR"));
                bean.setTempCashLimitIncrement(rs.getString("TEMPCASHLIMITINCR"));
                bean.setTleStartDate(rs.getString("TLISTARTDATE"));
                bean.setTleEndDate(rs.getString("TLIENDDATE"));
                bean.setTleStatus(rs.getString("TLISTATUS"));
                bean.setPayType(rs.getString("PAYTYPE"));
               // bean.setPayTypeDes(rs.getString("PAYTYPE"));
                bean.setServiceCode(rs.getString("SERVICECODE"));
                //des
                bean.setRiskProfCode(rs.getString("RISKPROFILECODE"));
                //des
                bean.setIbmOffset(rs.getString("IBMOFFSET"));
                bean.setPinMethod(rs.getString("PINMETHOD"));
                bean.setPvv(rs.getString("PVV"));
                bean.setCreateTime(rs.getString("CREATETIME"));
                bean.setLastUpdatedTime(rs.getString("LASTUPDATETIME"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEUSER"));
                bean.setCardVerificationMethod(rs.getString("CARDVERIFICATIONMETHOD"));
                bean.setEmvMethod(rs.getString("EMVMETHOD"));
               // bean.setCardNum(rs.getString(""));
                
                

            }


            return bean;
            
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
    
}
