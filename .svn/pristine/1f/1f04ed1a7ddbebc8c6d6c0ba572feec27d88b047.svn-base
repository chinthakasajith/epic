/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.cardembossing.persistance;

import com.epic.cms.cpmm.cardembossing.bean.CardQualityMgtBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author nalin
 */
public class CardQualityMgtPersistance {

    private ResultSet rs;

    public CardQualityMgtBean getCardDetails(Connection con, String cardNumber) throws Exception {
        PreparedStatement getCardStat = null;
        CardQualityMgtBean cardBean = new CardQualityMgtBean();
        cardBean.setCardNumber("NOVALUE");

        try {
            getCardStat = con.prepareStatement("SELECT C.NAMEONCARD,C.EXPIERYDATE,"
                    + " CP.DESCRIPTION AS CPDESCRIPTION, CT.DESCRIPTION AS CTDESCRIPTION, CD.DESCRIPTION AS CDDES"
                    + " FROM CARD C, CARDPRODUCT CP, CARDTYPE CT, CARDDOMAIN CD WHERE CP.PRODUCTCODE=C.CARDPRODUCT"
                    + " AND CT.CARDTYPECODE=C.CARDTYPE AND CD.DOMAINID=C.CARDDOMAIN AND C.CARDNUMBER=? "
                    + " AND C.BATCHID IS NULL ");

            getCardStat.setString(1, cardNumber);

            rs = getCardStat.executeQuery();

            while (rs.next()) {

                cardBean.setCardNumber(cardNumber);
                cardBean.setCardType(rs.getString("CTDESCRIPTION"));
                cardBean.setCardProduct(rs.getString("CPDESCRIPTION"));
                cardBean.setNameOnCard(rs.getString("NAMEONCARD"));
                cardBean.setCardExpDate(rs.getString("EXPIERYDATE"));
                cardBean.setCardDomain(rs.getString("CDDES"));

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getCardStat.close();

        }

        return cardBean;
    }
    //getBulkCardDetails
     public CardQualityMgtBean getBulkCardDetails(Connection con, String cardNumber) throws Exception {
        PreparedStatement getCardStat = null;
        CardQualityMgtBean cardBean = new CardQualityMgtBean();
        cardBean.setCardNumber("NOVALUE");

        try {
            getCardStat = con.prepareStatement("SELECT C.EXPIERYDATE,"
                    + " CP.DESCRIPTION AS CPDESCRIPTION, CT.DESCRIPTION AS CTDESCRIPTION, CD.DESCRIPTION AS CDDES"
                    + " FROM CARD C, CARDPRODUCT CP, CARDTYPE CT, CARDDOMAIN CD, BULKCARDREQUEST BR "
                    + " WHERE CP.PRODUCTCODE=C.CARDPRODUCT AND CT.CARDTYPECODE=C.CARDTYPE AND CD.DOMAINID=C.CARDDOMAIN "
                    + " AND C.BATCHID = BR.BATCHID AND C.CARDNUMBER=? ");

            getCardStat.setString(1, cardNumber);

            rs = getCardStat.executeQuery();

            while (rs.next()) {

                cardBean.setCardNumber(cardNumber);
                cardBean.setCardType(rs.getString("CTDESCRIPTION"));
                cardBean.setCardProduct(rs.getString("CPDESCRIPTION"));
                //cardBean.setNameOnCard(rs.getString("NAMEONCARD"));
                cardBean.setCardExpDate(rs.getString("EXPIERYDATE"));
                cardBean.setCardDomain(rs.getString("CDDES"));

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getCardStat.close();

        }

        return cardBean;
    }

    public boolean failCardQuality(Connection con, CardQualityMgtBean cardBean) throws Exception {
        boolean qualityFail = false;
        PreparedStatement updateStat = null;
        try {

            updateStat = con.prepareStatement("UPDATE CARD SET"
                    + " EMBOSSSTATUS=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE"
                    + " WHERE CARDNUMBER=?");

            updateStat.setString(1, StatusVarList.NOSTATUS);
            updateStat.setString(2, cardBean.getLastUpdatedUser());
            updateStat.setString(3, cardBean.getCardNumber());

            updateStat.executeUpdate();
            qualityFail = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return qualityFail;
    }

    public boolean insertQualityFailDetail(Connection con, CardQualityMgtBean cardBean) throws Exception {
        boolean successInsert = false;
        PreparedStatement insertStat = null;
        try {

            insertStat = con.prepareStatement("INSERT INTO CARDQUALITYFAILLIST(CARDNUMBER,REMARKS,"
                    + " LASTUPDATEDUSER,LASTUPDATEDTIME,CRATEDTIME) "
                    + " VALUES(?,?,?,SYSDATE,SYSDATE)");

            
            insertStat.setString(1, cardBean.getCardNumber());
            insertStat.setString(2, cardBean.getRemarks());
            insertStat.setString(3, cardBean.getLastUpdatedUser());

            insertStat.executeUpdate();
            successInsert = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return successInsert;
    }
}
