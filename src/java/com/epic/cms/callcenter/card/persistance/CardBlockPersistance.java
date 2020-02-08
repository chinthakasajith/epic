/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.card.persistance;

import com.epic.cms.callcenter.card.bean.CardBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author nisansala
 */
public class CardBlockPersistance {

    ResultSet rs = null;

    public CardBean getCardDetails(Connection con, String cardNo) throws SQLException, Exception {
        PreparedStatement ps = null;
        try {
            CardBean card = new CardBean();
            String query = "SELECT C.CARDNUMBER,C.CARDTYPE,CT.DESCRIPTION AS TYPE_DES,C.CARDCATEGORYCODE,CC.DESCRIPTION AS CAT_DES,C.CARDSTATUS,"
                    + "S.DESCRIPTION AS CUR_STATUS_DES,C.CREDITLIMIT,C.CASHLIMIT,C.EXPIERYDATE,C.CARDDOMAIN "
                    + "FROM CARD C,CARDTYPE CT,CARDCATEGORY CC,STATUS S "
                    + "WHERE CC.CATEGORYCODE=C.CARDCATEGORYCODE AND C.CARDTYPE=CT.CARDTYPECODE  AND C.CARDSTATUS=S.STATUSCODE AND C.CARDNUMBER=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, cardNo);
            rs = ps.executeQuery();

            while (rs.next()) {
                card.setCardNumber(rs.getString("CARDNUMBER"));
                card.setCardType(rs.getString("CARDTYPE"));
                card.setCardTypeDes(rs.getString("TYPE_DES"));
                card.setCardCategory(rs.getString("CARDCATEGORYCODE"));
                card.setCardCatDes(rs.getString("CAT_DES"));
                card.setCreditLimit(rs.getString("CREDITLIMIT"));
                card.setCashLimit(rs.getString("CASHLIMIT"));
                card.setCurrentStatus(rs.getString("CARDSTATUS"));
                card.setStatusDes(rs.getString("CUR_STATUS_DES"));
                card.setExpDate(rs.getString("EXPIERYDATE"));
                card.setCardDomain(rs.getString("CARDDOMAIN"));

            }
            return card;
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public HashMap<String, String> getBlockReasons(Connection con) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> blockReasons = new HashMap<String, String>();

        try {
            ps = con.prepareStatement("SELECT REASONCODE,DESCRIPTION FROM HOTLISTREASON");
            rs = ps.executeQuery();

            while (rs.next()) {
                blockReasons.put(rs.getString("REASONCODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return blockReasons;
    }

    public HashMap<String, String> getCardStatus(Connection con) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> cardStatus = new HashMap<String, String>();

        try {
            ps = con.prepareStatement("SELECT STATUSCODE,DESCRIPTION FROM STATUS WHERE STATUSCATEGORY='TERM'");
            rs = ps.executeQuery();

            while (rs.next()) {
                cardStatus.put(rs.getString("STATUSCODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return cardStatus;
    }

    public int blockCard(Connection con, Connection onlineCon, CardBean card) throws Exception {
        PreparedStatement ps = null;
        int row = -1;

        try {
            String selectQuery = "SELECT CARDNUMBER FROM CARDBLOCK WHERE CARDNUMBER=? AND STATUS=?";
            ps = con.prepareStatement(selectQuery);
            ps.setString(1, card.getCardNumber());
            ps.setString(2, StatusVarList.ACTIVE_STATUS);

            int success = ps.executeUpdate();

            if (success == 0) {
                ps = con.prepareStatement("UPDATE CARD SET CARDSTATUS=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=? WHERE CARDNUMBER=?");
                ps.setString(1, card.getNewStatus());
                ps.setString(2, card.getLastUpdateduser());
                ps.setTimestamp(3, SystemDateTime.getSystemDataAndTime());
                ps.setString(4, card.getCardNumber());
                row = ps.executeUpdate();

                if (row == 0) {
                    throw new Exception();
                } else if (row == 1) {
                    row = -1;
                    ps = onlineCon.prepareStatement("UPDATE ECMS_ONLINE_CARD SET STATUS='3',LASTUPDATEUSER=?,LASTUPDATETIME=? WHERE CARDNUMBER=?");
                    ps.setString(1, card.getLastUpdateduser());
                    ps.setTimestamp(2, SystemDateTime.getSystemDataAndTime());
                    ps.setString(3, card.getCardNumber());
                    row = ps.executeUpdate();
                }

                if (row == 1) {
                    row = -1;
                    String insertQuery = "INSERT INTO CARDBLOCK (CARDNUMBER,OLDSTATUS,NEWSTATUS,BLOCKREASON,LASTUPDATEDUSER,CREATEDTIME,STATUS)VALUES (?,?,?,?,?,?,?)";
                    ps = con.prepareStatement(insertQuery);

                    ps.setString(1, card.getCardNumber());
                    ps.setString(2, card.getCurrentStatus());
                    ps.setString(3, card.getNewStatus());
                    ps.setString(4, card.getBlockReason());
                    ps.setString(5, card.getLastUpdateduser());
                    ps.setTimestamp(6, SystemDateTime.getSystemDataAndTime());
                    ps.setString(7, StatusVarList.ACTIVE_STATUS);

                    row = ps.executeUpdate();

                }

                if (row == 1) {
                    row = -1;
                    String insertQuery = "INSERT INTO ECMS_ONLINE_CARD_BLOCK (CARDNUMBER,BLOCKSTATUS,TIMESTAMP,CREATETIME,LASTUPDATETIME,LASTUPDATEUSER,STATUS)VALUES (?,?,SYSDATE,SYSDATE,SYSDATE,?,?)";
                    ps = onlineCon.prepareStatement(insertQuery);

                    ps.setString(1, card.getCardNumber());
                    ps.setInt(2, StatusVarList.ONLINE_BLOCKED_STATUS);
                    ps.setString(3, card.getLastUpdateduser());
                    ps.setInt(4, StatusVarList.ONLINE_ACTIVE_STATUS);

                    row = ps.executeUpdate();

                }

            } else {
                row = -2;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }

        return row;
    }
}
