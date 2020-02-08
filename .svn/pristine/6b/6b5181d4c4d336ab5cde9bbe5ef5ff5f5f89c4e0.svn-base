/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.card.persistance;

import com.epic.cms.callcenter.card.bean.CardBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author nisansala
 */
public class CardChangeRequestPersistance {

    ResultSet rs;

    public CardBean getCardDetails(Connection con, String cardNo) throws SQLException, Exception {
        PreparedStatement ps = null;
        try {
            CardBean card = new CardBean();
            String query = "SELECT C.CARDNUMBER,C.CARDTYPE,CT.DESCRIPTION AS TYPE_DES,C.CARDCATEGORYCODE,CC.DESCRIPTION AS CAT_DES,"
                    + "C.CARDPRODUCT,CP.DESCRIPTION AS PROD_DES,C.CARDSTATUS,"
                    + "S.DESCRIPTION AS CUR_STATUS_DES,C.CREDITLIMIT,C.CASHLIMIT,C.EXPIERYDATE "
                    + "FROM CARD C,CARDTYPE CT,CARDCATEGORY CC,STATUS S,CARDPRODUCT CP "
                    + "WHERE CC.CATEGORYCODE=C.CARDCATEGORYCODE AND C.CARDTYPE=CT.CARDTYPECODE AND C.CARDPRODUCT=CP.PRODUCTCODE AND C.CARDSTATUS=S.STATUSCODE AND C.CARDNUMBER=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, cardNo);
            rs = ps.executeQuery();

            while (rs.next()) {
                card.setCardNumber(rs.getString("CARDNUMBER"));
                card.setCardType(rs.getString("CARDTYPE"));
                card.setCardTypeDes(rs.getString("TYPE_DES"));
                card.setCardProduct(rs.getString("CARDPRODUCT"));
                card.setCardProdDes(rs.getString("PROD_DES"));
                card.setCardCategory(rs.getString("CARDCATEGORYCODE"));
                card.setCardCatDes(rs.getString("CAT_DES"));
                card.setCreditLimit(rs.getString("CREDITLIMIT"));
                card.setCashLimit(rs.getString("CASHLIMIT"));
                card.setCurrentStatus(rs.getString("CARDSTATUS"));
                card.setStatusDes(rs.getString("CUR_STATUS_DES"));
                card.setExpDate(rs.getString("EXPIERYDATE"));


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

    public HashMap<String, String> loadCardProduct(Connection con, String cardType) throws Exception {
        HashMap<String, String> cardCategory = new HashMap<String, String>();

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT CP.PRODUCTCODE,CP.DESCRIPTION FROM CARDPRODUCT CP WHERE CP.CARDTYPE=?");
            ps.setString(1, cardType);
            rs = ps.executeQuery();
            cardCategory = new HashMap<String, String>();

            while (rs.next()) {
                cardCategory.put(rs.getString("PRODUCTCODE"), rs.getString("DESCRIPTION"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return cardCategory;
    }
    
    public int changeCard(Connection con, String reasonCode, CardBean card, String logID) throws Exception {
        int row = -1;
        int result = 1;
        int lastResult = -1;
        int exist = -1;
        PreparedStatement ps = null;

        try {
            String selectQuery = "SELECT CARDNUMBER FROM CARDCHANGEREQUEST WHERE CARDNUMBER=? AND STATUS='INIT'";

            ps = con.prepareStatement(selectQuery);

            ps.setString(1, card.getCardNumber());
//            ps.setString(2, reasonCode);

            int success = ps.executeUpdate();
            if (success == 0) {

                String query = "INSERT INTO CARDCHANGEREQUEST (CARDNUMBER,PRIORITYLEVEL,REMARKS,CALLLOGID,REQUESTTYPE,REQUESTCARDTYPE,REQUESTCARDPRODUCT,"
                        + "STATUS,LASTUPDATEDUSER,CREATEDTIME) VALUES(?,?,?,?,?,?,?,'INIT',?,?)";

                ps = con.prepareStatement(query);

                ps.setString(1, card.getCardNumber());
                ps.setString(2, card.getPriorityLevel());
                ps.setString(3, card.getRemark());
                ps.setString(4, logID);
                ps.setString(5, reasonCode);
                ps.setString(6, card.getNewCardType());
                ps.setString(7, card.getNewCardProduct());
                ps.setString(8, card.getLastUpdateduser());
                ps.setTimestamp(9,  SystemDateTime.getSystemDataAndTime());

                row = ps.executeUpdate();
            }else{
                exist = 1;
            
            }
            

        } catch (Exception ex) {
            throw ex;
        } finally {

            ps.close();

        }

        if (row == 1 ) {
            lastResult = 1;
        } if(exist == 1){
            lastResult = -2;
        }
        return lastResult;
    }
}
