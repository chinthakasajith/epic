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

/**
 *
 * @author nisansala
 */
public class CardClosePersistance {

    ResultSet rs = null;

    public CardBean getCardDetails(Connection con, String cardNo) throws SQLException, Exception {
        PreparedStatement ps = null;
        try {
            CardBean card = new CardBean();
            String query = "SELECT C.CARDNUMBER,C.CARDTYPE,CT.DESCRIPTION AS TYPE_DES,C.CARDCATEGORYCODE,CC.DESCRIPTION AS CAT_DES,C.CARDSTATUS,"
                    + "S.DESCRIPTION AS CUR_STATUS_DES,C.CREDITLIMIT,C.CASHLIMIT,C.OTBCREDIT,C.OTBCASH,C.EXPIERYDATE,C.CARDDOMAIN "
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
                card.setCreditAvlbl(rs.getString("OTBCREDIT"));
                card.setCashAvlbl(rs.getString("OTBCASH"));
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

    public int closeCard(Connection con, String reasonCode, CardBean card, String logID) throws Exception {
        int row = -1;
        int result = 1;
        int lastResult = -1;
        int exist = -1;
        PreparedStatement ps = null;

        try {
            String selectQuery = "SELECT CARDNUMBER FROM CARDREQUEST WHERE CARDNUMBER=? AND REQUESTREASONCODE=? AND STATUS=?";

            ps = con.prepareStatement(selectQuery);

            ps.setString(1, card.getCardNumber());
            ps.setString(2, reasonCode);
            ps.setString(3, StatusVarList.DA_REQUSET_INITIATE);

            int success = ps.executeUpdate();

            if (success == 0) {

                String query = "INSERT INTO CARDREQUEST (CARDNUMBER,PRIORITYLEVEL,REMARKS,STATUS,LASTUPDATEDUSER,LASTUPDATEDTIME,CALLLOGID,"
                        + "REQUESTEDUSER,REQUESTREASONCODE) VALUES(?,?,?,?,?,?,?,?,?)";

                ps = con.prepareStatement(query);

                ps.setString(1, card.getCardNumber());
                ps.setString(2, card.getPriorityLevel());
                ps.setString(3, card.getRemark());
                ps.setString(4, StatusVarList.DA_REQUSET_INITIATE);
                ps.setString(5, card.getLastUpdateduser());
                ps.setTimestamp(6, SystemDateTime.getSystemDataAndTime());
                ps.setString(7, logID);
                ps.setString(8, card.getLastUpdateduser());
                ps.setString(9, reasonCode);

                row = ps.executeUpdate();

            } else {
                exist = 1;
            }

        } catch (Exception ex) {
            throw ex;
        } finally {

            ps.close();

        }

        if (row == 1 && result == 1) {
            lastResult = 1;
        } else if (exist == 1) {
            lastResult = -2;
        }
        return lastResult;
    }
}
