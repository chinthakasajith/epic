/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.card.persistance;

import com.epic.cms.backoffice.cardlimitincrement.bean.CommonCardParameterBean;
import com.epic.cms.backoffice.cardlimitincrement.bean.TempLimitIncrementBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author badrika
 */
public class PermLimitIncrementRequestPersistance {

    private ResultSet rs;

    public TempLimitIncrementBean getCardDetails(Connection CMSCon, String cardNumber) throws Exception {
        PreparedStatement cardStat = null;
        TempLimitIncrementBean tempBean = new TempLimitIncrementBean();
        try {
            cardStat = CMSCon.prepareStatement("SELECT C.CREDITLIMIT,C.CASHLIMIT,C.OTBCREDIT,C.OTBCASH,C.TEMPCREDITAMOUNT,C.TEMPCASHAMOUNT,C.CARDSTATUS,"
                    + "ST.DESCRIPTION FROM CARD C,STATUS ST WHERE C.CARDNUMBER=? AND C.CARDSTATUS=ST.STATUSCODE ");

            cardStat.setString(1, cardNumber);
            rs = cardStat.executeQuery();

            while (rs.next()) {

                tempBean.setCardNumber(cardNumber);
                tempBean.setCreditLimit(Double.toString(rs.getDouble("CREDITLIMIT")));
                tempBean.setCashLimit(Double.toString(rs.getDouble("CASHLIMIT")));
                tempBean.setAvlCreditLimit(Double.toString(rs.getInt("OTBCREDIT")));
                tempBean.setAvlCashLimit(Double.toString(rs.getInt("OTBCASH")));
                tempBean.setTempCrediAmt(Double.toString(rs.getInt("TEMPCREDITAMOUNT")));
                tempBean.setTempCashAmt(Double.toString(rs.getInt("TEMPCASHAMOUNT")));
                tempBean.setStatus(rs.getString("CARDSTATUS"));
                tempBean.setStatusDes(rs.getString("DESCRIPTION"));


            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    cardStat.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return tempBean;
    }

    public TempLimitIncrementBean getOnlineCardDetails(Connection CMSConOnline, TempLimitIncrementBean tempBean) throws Exception {
        PreparedStatement cardStat = null;

        try {
            cardStat = CMSConOnline.prepareStatement("SELECT C.CREDITLIMIT,C.CASHLIMIT,C.OTBCREDIT,C.OTBCASH,C.TEMPCREDITAMOUNT,C.TEMPCASHAMOUNT,"
                    + "C.STATUS,ST.DESCRIPTION "
                    + " FROM ECMS_ONLINE_CARD C,ECMS_ONLINE_STATUS ST WHERE C.CARDNUMBER=? AND C.STATUS=ST.CODE ");

            cardStat.setString(1, tempBean.getCardNumber());
            rs = cardStat.executeQuery();

            while (rs.next()) {


                tempBean.setOnlineCreditLimit(Double.toString(rs.getDouble("CREDITLIMIT")));
                tempBean.setOnlineCashLimit(Double.toString(rs.getDouble("CASHLIMIT")));
                tempBean.setOnlineAvlCreditLimit(Double.toString(rs.getInt("OTBCREDIT")));
                tempBean.setOnlineAvlCashLimit(Double.toString(rs.getInt("OTBCASH")));
                tempBean.setOnlineTempCrediAmt(rs.getString("TEMPCREDITAMOUNT"));
                tempBean.setOnlineTempCashAmt(rs.getString("TEMPCASHAMOUNT"));
                tempBean.setOnlineStatus(rs.getString("STATUS"));
                tempBean.setOnlineStatusDes(rs.getString("DESCRIPTION"));


            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    cardStat.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return tempBean;
    }

    public CommonCardParameterBean getLimitDetails(Connection CMSCon) throws Exception {
        PreparedStatement limitStat = null;
        CommonCardParameterBean limitBean = new CommonCardParameterBean();
        try {
            limitStat = CMSCon.prepareStatement("SELECT MAXIMUMPERMCREDITLIMITAMOUNT,MAXIMUMPERMCASHLIMITAMOUNT "
                    + " FROM COMMONCARDPARAMETER ");

            rs = limitStat.executeQuery();

            while (rs.next()) {

                limitBean.setMaxPermCreditLimitAmount(rs.getDouble("MAXIMUMPERMCREDITLIMITAMOUNT"));
                limitBean.setMaxPermCashLimitAmount(rs.getDouble("MAXIMUMPERMCASHLIMITAMOUNT"));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    limitStat.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return limitBean;
    }

    public int insertLimitIncrement(Connection CMSCon, TempLimitIncrementBean tempBean) throws Exception {
        int i = -1;
        PreparedStatement insertStat = null;
        try {

            insertStat = CMSCon.prepareStatement("INSERT INTO PERMENANTLIMITINCREMENT(CARDNO, INCREMENTTYPE, AMOUNT, STATUS, REMARK, REQUESTEDUSER, "
                    + "LASTUPDATEDUSER, CREATEDTIME, LASTUPDATEDTIME, ONLINEAVALABLECREDITLIMIT, ONLINEAVALABLECASHLIMIT, ONLINETEMPCREDITAMOUNT, "
                    + "ONLINETEMPCASHAMOUNT, ONLINECREDITLIMIT, ONLINECASHLIMIT ) VALUES(?,?,?,?,?,?,?,SYSDATE,SYSDATE,?,?,?,?,?,?)");

            insertStat.setString(1, tempBean.getCardNumber());
            insertStat.setString(2, tempBean.getCreditOrCash());
            insertStat.setString(3, tempBean.getAmount());
            insertStat.setString(4, tempBean.getStatus());
            insertStat.setString(5, tempBean.getRemark());
            insertStat.setString(6, tempBean.getLastUpdatedUser());
            insertStat.setString(7, tempBean.getLastUpdatedUser());
            insertStat.setString(8, tempBean.getOnlineAvlCreditLimit());
            insertStat.setString(9, tempBean.getOnlineAvlCashLimit());
            insertStat.setString(10, tempBean.getOnlineTempCrediAmt());
            insertStat.setString(11, tempBean.getOnlineTempCashAmt());
            insertStat.setString(12, tempBean.getOnlineCreditLimit());
            insertStat.setString(13, tempBean.getOnlineCashLimit());

            i = insertStat.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (insertStat != null) {
                    insertStat.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return i;
    }
}
