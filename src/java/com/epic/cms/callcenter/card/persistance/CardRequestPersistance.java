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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 *
 * @author nisansala
 */
public class CardRequestPersistance {

    ResultSet rs = null;

    public HashMap<String, String> getCardCategory(Connection con) throws Exception {
        HashMap<String, String> cardCategory = new HashMap<String, String>();

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT CC.CATEGORYCODE,CC.DESCRIPTION FROM CARDCATEGORY CC");

            rs = ps.executeQuery();
            cardCategory = new HashMap<String, String>();

            while (rs.next()) {

                cardCategory.put(rs.getString("CATEGORYCODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return cardCategory;
    }

    //UPDATE CARDREQUEST SET CARDNUMBER=?,PRIORITYLEVEL=?,REPLACEREMARKS=?,STATUS='ACT',LASTUPDATEDUSER=?,"
    // + "LASTUPDATEDTIME=?,CALLLOGID=?,REQUESTEDUSER=?,REQUESTREASONCODE=?
    public int ReplaceCardOrReissuePIN(Connection con, String reasonCode, CardBean card, String logID) throws Exception {
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
                        + "REQUESTEDUSER,REQUESTREASONCODE,RENEWCONFIRMATION) VALUES(?,?,?,?,?,?,?,?,?,?)";

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
                ps.setString(10, card.getRenewConfirm());

                row = ps.executeUpdate();
            }else{
                exist = 1;
            
            }
            if (reasonCode.equals("CDRP") || reasonCode.equals("CDRI") ) {

                if (row == 1) {
                    String query1 = "UPDATE CARD SET CARDDISTRIBUTESTATUS=? WHERE CARDNUMBER=?";
                    ps = con.prepareStatement(query1);
                    ps.setString(1, card.getDistributeStatus());
                    ps.setString(2, card.getCardNumber());

                    result = ps.executeUpdate();
                }
            }

        } catch (Exception ex) {
            throw ex;
        } finally {

            ps.close();

        }

        if (row == 1 && result == 1) {
            lastResult = 1;
        }else if(exist == 1){
            lastResult = -2;
        }
        return lastResult;
    }

    public CardBean getCardDetails(Connection con, String cardNo) throws SQLException, Exception {
        PreparedStatement ps = null;
        try {
            CardBean card = new CardBean();
            String query = "SELECT C.CARDNUMBER,C.CARDTYPE,CT.DESCRIPTION AS TYPE_DES,C.CARDCATEGORYCODE,CC.DESCRIPTION AS CAT_DES,C.CARDSTATUS,"
                    + "S.DESCRIPTION AS CUR_STATUS_DES,C.CREDITLIMIT,C.CASHLIMIT,C.EXPIERYDATE,C.CARDDOMAIN as cdomain "
                    + "FROM CARD C,CARDTYPE CT,CARDCATEGORY CC,STATUS S "
                    + "WHERE CC.CATEGORYCODE=C.CARDCATEGORYCODE AND C.CARDTYPE=CT.CARDTYPECODE  AND C.CARDSTATUS=S.STATUSCODE AND C.CARDNUMBER=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, cardNo);
            rs = ps.executeQuery();

            while (rs.next()) {
                card.setCardNumber(rs.getString("CARDNUMBER"));
                card.setCardType(rs.getString("CARDTYPE"));
                card.setCardDomain(rs.getString("cdomain"));
                card.setCardTypeDes(rs.getString("TYPE_DES"));
                card.setCardCategory(rs.getString("CARDCATEGORYCODE"));
                card.setCardCatDes(rs.getString("CAT_DES"));
                card.setCreditLimit(rs.getString("CREDITLIMIT"));
                card.setCashLimit(rs.getString("CASHLIMIT"));
                card.setCurrentStatus(rs.getString("CARDSTATUS"));
                card.setStatusDes(rs.getString("CUR_STATUS_DES"));
                card.setExpDate(rs.getString("EXPIERYDATE"));
                card.setRenewEligible(this.checkEligibilityToRenew(con, cardNo));
                
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

    public String checkEligibilityToRenew(Connection con, String cardNo) throws Exception {
        String eligible = "";

        String expiryDate = "";
        String renewPeriod = "";
        String currentDate = "";
        int renewDuration = 0;
        int monthDif;
        //time between today and expiry date in months

        int duration = 0;
        int yearDif;
        //---------------------------------------------------------------------------
        PreparedStatement ps = null;
        String query = "SELECT EXPIERYDATE,RENEWTHRESHHOLSPERIOD FROM CARD WHERE CARDNUMBER=?";

        ps = con.prepareStatement(query);
        ps.setString(1, cardNo);
        rs = ps.executeQuery();

        while (rs.next()) {
            expiryDate = rs.getString("EXPIERYDATE");
            renewPeriod = rs.getString("RENEWTHRESHHOLSPERIOD");
        }
        //---------------------------------------------------------------------------

        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        currentDate = dateFormat.format(today);
        if (renewPeriod != null) {
            renewDuration = Integer.parseInt(renewPeriod);
        }

        //if expiry date is in the current year
        if (expiryDate.regionMatches(0, currentDate, 0, 2)) {
            duration = Integer.parseInt(expiryDate.substring(2)) - Integer.parseInt(currentDate.substring(2));

        }//if expiry date is in a coming year
        else if (!expiryDate.regionMatches(0, currentDate, 0, 2)) {
            String expSub1 = expiryDate.substring(0);
            String expSub2 = expiryDate.substring(2);
            String todSub1 = currentDate.substring(0);
            String todSub2 = currentDate.substring(2);

            yearDif = Integer.parseInt(expSub1) - Integer.parseInt(todSub1);

            //if expiry month is larger than the current month
            if (Integer.parseInt(expSub2) > Integer.parseInt(todSub2)) {
                monthDif = Integer.parseInt(expSub2) - Integer.parseInt(todSub2);
            }//if expiry month is smaller than the current month
            else {

                int largeMonth = Integer.parseInt(expSub2) + 1;
                yearDif = yearDif - 1;

                monthDif = largeMonth - Integer.parseInt(todSub2);
            }

            duration = yearDif * 12 + monthDif;
        }

        if (duration > renewDuration) {
            eligible = "NO";
        } else {
            eligible = "YES";
        }
        return eligible;

    }
}
