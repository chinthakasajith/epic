/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.persistance;

import com.epic.cms.reportexp.cardapplication.bean.LimitExceedReportBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author asitha_l
 */
public class SearchLimitExceedReportPersistance {

    private HashMap<String, String> cardTypeList = null;
    private List<LimitExceedReportBean> limitExceedReportList = null;
    ResultSet rs;

    /**
     * to retrieve the card types
     * @param con
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAllCardTypeList(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;

        try {
            getAllUserRole = con.prepareStatement("SELECT TC.CARDTYPECODE,TC.DESCRIPTION FROM CARDTYPE TC WHERE TC.STATUS = 'ACT'");

            rs = getAllUserRole.executeQuery();
            cardTypeList = new HashMap<String, String>();

            while (rs.next()) {

                cardTypeList.put(rs.getString("CARDTYPECODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return cardTypeList;
    }

    public List<LimitExceedReportBean> searchLimitExceedReport(Connection CMSCon, LimitExceedReportBean limitExceedReportBean) throws SQLException {
        PreparedStatement ps = null;
        limitExceedReportList = new ArrayList<LimitExceedReportBean>();
        String strSqlBasic = null;

        try {
            String condition = "";

            String cardNumber = limitExceedReportBean.getCardNumber();
            String cardType = limitExceedReportBean.getCardType();
            String cashOrCredit = limitExceedReportBean.getCashOrCredit();
            String percentage = limitExceedReportBean.getUsagePercentage();

            if ("CREDIT".equals(cashOrCredit)) {

                strSqlBasic = "SELECT CARDNUMBER,CARDTYPE,NAMEONCARD,IDTYPE,IDNUMBER,CREDITLIMIT,OTBCREDIT,(CREDITLIMIT-OTBCREDIT)*100/CREDITLIMIT AS PERCENTAGE FROM CARD WHERE (CREDITLIMIT-OTBCREDIT)*100/CREDITLIMIT > ? ";

                if (!cardNumber.contentEquals("") || cardNumber.length() > 0) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "CARDNUMBER LIKE '%" + cardNumber + "%'";
                }
                if (!cardType.contentEquals("") || cardType.length() > 0) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += " CARDTYPE = '" + cardType + "' ";
                }
                if (!condition.equals("")) {
                    strSqlBasic += " AND " + condition + " ORDER BY CARDNUMBER DESC ";
                } else {

                    strSqlBasic += " ORDER BY CARDNUMBER DESC ";
                }

                ps = CMSCon.prepareStatement(strSqlBasic);
                ps.setString(1, percentage);
                rs = ps.executeQuery();

                while (rs.next()) {

                    LimitExceedReportBean bean = new LimitExceedReportBean();

                    bean.setCardNumber(rs.getString("CARDNUMBER"));
                    bean.setNameOnCard(rs.getString("NAMEONCARD"));
                    bean.setIdType(rs.getString("IDTYPE"));
                    bean.setIdNumber(rs.getString("IDNUMBER"));
                    bean.setCreditLimit(rs.getString("CREDITLIMIT"));
                    bean.setOtbCredit(rs.getString("OTBCREDIT"));
                    bean.setUsagePercentage(rs.getString("PERCENTAGE"));

                    limitExceedReportList.add(bean);
                }

            } else if ("CASH".equals(cashOrCredit)) {

                strSqlBasic = "SELECT CARDNUMBER,CARDTYPE,NAMEONCARD,IDTYPE,IDNUMBER,CASHLIMIT,OTBCASH,(CASHLIMIT-OTBCASH)*100/CASHLIMIT AS PERCENTAGE FROM CARD WHERE (CASHLIMIT-OTBCASH)*100/CASHLIMIT > ?";

                if (!cardNumber.contentEquals("") || cardNumber.length() > 0) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "CARDNUMBER LIKE '%" + cardNumber + "%'";
                }
                if (!cardType.contentEquals("") || cardType.length() > 0) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += " CARDTYPE = '" + cardType + "' ";
                }
                if (!condition.equals("")) {
                    strSqlBasic += " AND " + condition + " ORDER BY CARDNUMBER DESC ";
                } else {

                    strSqlBasic += " ORDER BY CARDNUMBER DESC ";
                }

                ps = CMSCon.prepareStatement(strSqlBasic);
                ps.setString(1, percentage);
                rs = ps.executeQuery();

                while (rs.next()) {

                    LimitExceedReportBean bean = new LimitExceedReportBean();

                    bean.setCardNumber(rs.getString("CARDNUMBER"));
                    bean.setNameOnCard(rs.getString("NAMEONCARD"));
                    bean.setIdType(rs.getString("IDTYPE"));
                    bean.setIdNumber(rs.getString("IDNUMBER"));
                    bean.setCashLimit(rs.getString("CASHLIMIT"));
                    bean.setOtbCash(rs.getString("OTBCASH"));
                    bean.setUsagePercentage(rs.getString("PERCENTAGE"));

                    limitExceedReportList.add(bean);
                }
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }
        return limitExceedReportList;
    }
}
