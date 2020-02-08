/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.distribution.persistance;

import com.epic.cms.cpmm.distribution.bean.DistributionBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class CardandPinDistributionPersistance {

    private ResultSet rs;

    public List<DistributionBean> searchCardDistributionDetails(DistributionBean distBean, Connection CMSCon) throws Exception {
        PreparedStatement searchAllCard = null;
        List<DistributionBean> distList = new ArrayList<DistributionBean>();
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT C.APPLICATIONID,C.CARDNUMBER,C.CARDTYPE,C.CARDPRODUCT,C.CARDCATEGORYCODE,"
                    + " CT.DESCRIPTION AS CTDESCRIPTION,CP.DESCRIPTION AS CPDESCRIPTION,CC.DESCRIPTION AS CCDESCRIPTION"
                    + " FROM CARD C,CARDTYPE CT,CARDPRODUCT CP,CARDCATEGORY CC "
                    + " WHERE C.CARDTYPE=CT.CARDTYPECODE AND C.CARDPRODUCT=CP.PRODUCTCODE "
                    + " AND C.CARDCATEGORYCODE=CC.CATEGORYCODE AND C.CARDSTATUS=? AND C.EMBOSSSTATUS=?"
                    + " AND C.PINSTATUS=? AND C.PINMAILSTATUS=? AND C.CARDDISTRIBUTESTATUS=?";

            String condition = "";

            if (!distBean.getCardNumber().contentEquals("") || distBean.getCardNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CARDNUMBER LIKE '%" + distBean.getCardNumber() + "%'";
            }

            if (!distBean.getCardType().contentEquals("") || distBean.getCardType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CARDTYPE LIKE '%" + distBean.getCardType() + "%'";
            }

            if (!distBean.getCardProduct().contentEquals("") || distBean.getCardProduct().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CARDPRODUCT LIKE '%" + distBean.getCardProduct() + "%'";
            }

            if (!distBean.getCardCategory().contentEquals("") || distBean.getCardCategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CARDCATEGORYCODE LIKE '%" + distBean.getCardCategory() + "%'";
            }

            if (!condition.equals("")) {
                strSqlBasic += " AND " + condition + " ORDER BY C.CARDNUMBER ";
            } else {

                strSqlBasic += " ORDER BY C.CARDNUMBER";
            }

            searchAllCard = CMSCon.prepareStatement(strSqlBasic);
            searchAllCard.setString(1, StatusVarList.INACTIVE_STATUS);
            searchAllCard.setString(2, StatusVarList.YESSTATUS);
            searchAllCard.setString(3, StatusVarList.YESSTATUS);
            searchAllCard.setString(4, StatusVarList.YESSTATUS);
            searchAllCard.setString(5, StatusVarList.NOSTATUS);
            rs = searchAllCard.executeQuery();

            while (rs.next()) {

                DistributionBean bean = new DistributionBean();

                bean.setCardNumber(rs.getString("CARDNUMBER"));
                bean.setCardType(rs.getString("CARDTYPE"));
                bean.setCardTypeDes(rs.getString("CTDESCRIPTION"));
                bean.setCardProduct(rs.getString("CARDPRODUCT"));
                bean.setCardProductDes(rs.getString("CPDESCRIPTION"));
                bean.setCardCategory(rs.getString("CARDCATEGORYCODE"));
                bean.setCardCategoryDes(rs.getString("CCDESCRIPTION"));
                bean.setApplicationId(rs.getString("APPLICATIONID"));

                distList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            searchAllCard.close();

        }
        return distList;

    }

    public List<DistributionBean> searchPinDistributionDetails(DistributionBean distBean, Connection CMSCon) throws Exception {
        PreparedStatement searchAllCard = null;
        List<DistributionBean> distList = new ArrayList<DistributionBean>();
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT C.CARDNUMBER,C.CARDTYPE,C.CARDPRODUCT,C.CARDCATEGORYCODE,"
                    + " CT.DESCRIPTION AS CTDESCRIPTION,CP.DESCRIPTION AS CPDESCRIPTION,CC.DESCRIPTION AS CCDESCRIPTION"
                    + " FROM CARD C,CARDTYPE CT,CARDPRODUCT CP,CARDCATEGORY CC "
                    + " WHERE C.CARDTYPE=CT.CARDTYPECODE AND C.CARDPRODUCT=CP.PRODUCTCODE "
                    + " AND C.CARDCATEGORYCODE=CC.CATEGORYCODE AND C.CARDSTATUS=? AND C.EMBOSSSTATUS=?"
                    + " AND C.PINSTATUS=? AND C.PINMAILSTATUS=? AND PINDISTRIBUTIONSTATUS=?";

            String condition = "";

            if (!distBean.getCardNumber().contentEquals("") || distBean.getCardNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CARDNUMBER LIKE '%" + distBean.getCardNumber() + "%'";
            }

            if (!distBean.getCardType().contentEquals("") || distBean.getCardType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CARDTYPE LIKE '%" + distBean.getCardType() + "%'";
            }

            if (!distBean.getCardProduct().contentEquals("") || distBean.getCardProduct().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CARDPRODUCT LIKE '%" + distBean.getCardProduct() + "%'";
            }

            if (!distBean.getCardCategory().contentEquals("") || distBean.getCardCategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CARDCATEGORYCODE LIKE '%" + distBean.getCardCategory() + "%'";
            }

            if (!condition.equals("")) {
                strSqlBasic += " AND " + condition + " ORDER BY C.CARDNUMBER ";
            } else {

                strSqlBasic += " ORDER BY C.CARDNUMBER";
            }

            searchAllCard = CMSCon.prepareStatement(strSqlBasic);
            searchAllCard.setString(1, StatusVarList.INACTIVE_STATUS);
            searchAllCard.setString(2, StatusVarList.YESSTATUS);
            searchAllCard.setString(3, StatusVarList.YESSTATUS);
            searchAllCard.setString(4, StatusVarList.YESSTATUS);
            searchAllCard.setString(5, StatusVarList.NOSTATUS);
            rs = searchAllCard.executeQuery();

            while (rs.next()) {

                DistributionBean bean = new DistributionBean();

                bean.setCardNumber(rs.getString("CARDNUMBER"));
                bean.setCardType(rs.getString("CARDTYPE"));
                bean.setCardTypeDes(rs.getString("CTDESCRIPTION"));
                bean.setCardProduct(rs.getString("CARDPRODUCT"));
                bean.setCardProductDes(rs.getString("CPDESCRIPTION"));
                bean.setCardCategory(rs.getString("CARDCATEGORYCODE"));
                bean.setCardCategoryDes(rs.getString("CCDESCRIPTION"));

                distList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            searchAllCard.close();

        }
        return distList;

    }

    public boolean proceedCardDistribution(Connection con, String[] cardList,String user) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARD SET CARDDISTRIBUTESTATUS=?,"
                    + " LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE WHERE CARDNUMBER=?");


            for (int i = 0; i < cardList.length; i++) {

                updateStat.setString(1, StatusVarList.YESSTATUS);
                updateStat.setString(2, user);
                updateStat.setString(3, cardList[i]);
                
                updateStat.executeUpdate();
                success = true;
            }

        } catch (SQLException ex) {
            throw ex;
        }
        return success;

    }

    public boolean proceedPinDistribution(Connection con, String[] pinList,String user) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARD SET PINDISTRIBUTIONSTATUS=?,"
                    + " LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE WHERE CARDNUMBER=?");


            for (int i = 0; i < pinList.length; i++) {
                updateStat.setString(1, StatusVarList.YESSTATUS);
                updateStat.setString(2, user);
                updateStat.setString(3, pinList[i]);                

                updateStat.executeUpdate();
                success = true;
            }

        } catch (SQLException ex) {
            throw ex;
        }
        return success;

    }

    public boolean updateCardHistory(Connection CMSCon, String[] appIDList, String lastUpUser) throws Exception {
        boolean isSuccess = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO CARDAPPLICATIONHISTORY"
                    + " (APPLICATIONID,APPLICATIONLEVEL,STATUS,REMARKS,LASTUPDATEDUSER,"
                    + " CREATEDTIME,LASTUPDATEDTIME) VALUES(?,?,?,?,?,SYSDATE,SYSDATE)");

            for (int i = 0; i < appIDList.length; i++) {

                insertStat.setString(1, appIDList[i]);
                insertStat.setString(2, StatusVarList.APP_CARD_DISTRIBUTION);
                insertStat.setString(3, StatusVarList.HISTORY_COMPLETE);
                insertStat.setString(4, "Card Distributed");
                insertStat.setString(5, lastUpUser);

                insertStat.executeUpdate();
                isSuccess = true;
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return isSuccess;
    }
}
