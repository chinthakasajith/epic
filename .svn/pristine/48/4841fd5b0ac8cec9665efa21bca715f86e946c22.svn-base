/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.cardrenew.persistance;

import com.epic.cms.backoffice.cardrenew.bean.CardRenewBean;
import com.epic.cms.backoffice.cardrenew.bean.CardSearchBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author badrika
 */
public class CardRenewPersistance {

    private CardRenewBean cardBean;
    private List<CardRenewBean> cardList;

    public List<CardRenewBean> searchCard(Connection con, CardSearchBean searchBean) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        cardList = new ArrayList<CardRenewBean>();

        try {

            String query = "SELECT CR.CARDNUMBER,CR.EXPIRYDATE,CR.STATUS,ST.DESCRIPTION,CR.RENEWALCONFIRMATIONDATE "
                    + "FROM CARD C, STATUS ST, CARDRENEW CR "
                    + "WHERE CR.CARDNUMBER=C.CARDNUMBER AND CR.STATUS=ST.STATUSCODE AND (CR.STATUS='RCON' or CR.STATUS='RINI' or CR.STATUS='INIT' ) ";

            String condition = "";

            if (!searchBean.getcNumber().equals("") || searchBean.getcNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CR.CARDNUMBER = '" + searchBean.getcNumber() + "'";
            }

            if (!searchBean.getcType().equals("") || searchBean.getcType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDTYPE = '" + searchBean.getcType() + "'";
            }

            if (!searchBean.getcProduct().equals("") || searchBean.getcProduct().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDPRODUCT = '" + searchBean.getcProduct() + "'";
            }

            if (!searchBean.getcCategory().equals("") || searchBean.getcCategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDCATEGORYCODE = '" + searchBean.getcCategory() + "'";
            }

            if (!searchBean.getExpDate().equals("") || searchBean.getExpDate().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CR.EXPIRYDATE = '" + searchBean.getExpDate() + "'";
            }

            if (!condition.equals("")) {
                query += " AND " + condition;

            }

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            SimpleDateFormat df = new SimpleDateFormat("yyMM");
            Date day = new Date();
            Date today = new Date(day.getTime());

            while (rs.next()) {

                cardBean = new CardRenewBean();
                if (rs.getString("STATUS").equals("RINI")) {
                    Date expdate = df.parse(rs.getString("RENEWALCONFIRMATIONDATE"));
                    if (expdate.before(today)) {
                        cardBean.setcNumber(rs.getString("CARDNUMBER"));
                        cardBean.setExpDate(rs.getString("EXPIRYDATE"));
                        cardBean.setStatus(rs.getString("STATUS"));
                        cardBean.setStatusDes(rs.getString("DESCRIPTION"));

                    }

                } else if (rs.getString("STATUS").equals("RCON")) {

                    cardBean.setcNumber(rs.getString("CARDNUMBER"));
                    cardBean.setExpDate(rs.getString("EXPIRYDATE"));
                    cardBean.setStatus(rs.getString("STATUS"));
                    cardBean.setStatusDes(rs.getString("DESCRIPTION"));
                    
                } else if (rs.getString("STATUS").equals("INIT")) {

                    cardBean.setcNumber(rs.getString("CARDNUMBER"));
                    cardBean.setExpDate(rs.getString("EXPIRYDATE"));
                    cardBean.setStatus(rs.getString("STATUS"));
                    cardBean.setStatusDes(rs.getString("DESCRIPTION"));
                }

                cardList.add(cardBean);
            }

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

        return cardList;

    }

    public boolean updateCardTable(Connection con, Connection onlineCon, String card) throws Exception, SQLException {
        boolean success = false;
        PreparedStatement updateStat = null;
        PreparedStatement updateOnlineStat = null;

        try {
            updateStat = con.prepareStatement("UPDATE CARD SET CARDDISTRIBUTESTATUS=?, EMBOSSSTATUS=?, NEWEXPIRYDATE=? WHERE CARDNUMBER=? ");

            updateOnlineStat = onlineCon.prepareStatement("UPDATE ECMS_ONLINE_CARD SET NEWEXPDATE=? WHERE CARDNUMBER=? ");

        //    for (int i = 0; i < cardList.length; i++) {
            updateStat.setString(1, StatusVarList.NOSTATUS);
            updateStat.setString(2, StatusVarList.NOSTATUS);

            String newexpdate = this.takeExpDate(con, card);

            updateStat.setString(3, newexpdate);
            updateStat.setString(4, card);

            updateStat.executeUpdate();

            updateOnlineStat.setString(1, newexpdate);
            updateOnlineStat.setString(2, card);
            updateOnlineStat.executeUpdate();

            success = true;
            //    }

        } catch (SQLException ex) {
            throw ex;
        }
        return success;

    }

    public boolean updateCardRenewTable(Connection con, String card) throws Exception, SQLException {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARDRENEW SET STATUS=? WHERE CARDNUMBER=? ");

         //   for (int i = 0; i < cardList.length; i++) {
            updateStat.setString(1, "RCOM");
            updateStat.setString(2, card);

            updateStat.executeUpdate();
            success = true;
            // }

        } catch (SQLException ex) {
            throw ex;
        }
        return success;

    }

    public String takeExpDate(Connection con, String cardNum) throws Exception, SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String newexp = "";
        int expYear = 0;
        int expMon = 0;
        int numOfyrs = 0;
        int numOfmons = 0;

        try {
            ps = con.prepareStatement("SELECT C.EXPIERYDATE, CP.VALIDITYPERIOD FROM CARD C, CARDPRODUCT CP WHERE C.CARDPRODUCT=CP.PRODUCTCODE AND CARDNUMBER=? ");
            ps.setString(1, cardNum);
            rs = ps.executeQuery();

            while (rs.next()) {
                String expdate = rs.getString("EXPIERYDATE");
                String yr = expdate.substring(0, 2);
                String mon = expdate.substring(2);

                String expPer = rs.getString("VALIDITYPERIOD");
                //double exp1 = Double.parseDouble(expPer);

                int exp = Integer.parseInt(expPer);

                if (exp % 12 == 0) {
                    numOfyrs = exp / 12;

                    expYear = Integer.parseInt(yr) + numOfyrs;
                    newexp = String.valueOf(expYear) + mon;

                } else if (exp % 12 > 0) {
                    numOfyrs = exp / 12;
                    numOfmons = exp % 12;

                    expYear = Integer.parseInt(yr) + numOfyrs;
                    expMon = Integer.parseInt(mon) + numOfmons;

                    if (expMon < 12) {
                        newexp = String.valueOf(expYear) + String.valueOf(expMon);

                    } else if (expMon >= 12) {
                        expYear = expYear + 1;
                        expMon = expMon - 12;

                        newexp = String.valueOf(expYear) + String.valueOf(expMon);

                    }
                }
            }
        } catch (SQLException ex) {
            throw ex;
        }
        return newexp;
    }
}
