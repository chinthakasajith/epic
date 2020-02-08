/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.persistance;

import com.epic.cms.reportexp.cardapplication.bean.CardDistributionReportBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nalin
 */
public class CardDistributionReportPersistance {

    ResultSet rs;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> cardProductList = null;
    private List<CardDistributionReportBean> distributeList = null;

    public HashMap<String, String> getCardType(Connection CMSCon) throws Exception {
        PreparedStatement getCardType = null;
        cardTypeList = new HashMap<String, String>();
        try {
            getCardType = CMSCon.prepareStatement("SELECT C.CARDTYPECODE,C.DESCRIPTION "
                    + " FROM CARDTYPE C");

            rs = getCardType.executeQuery();

            while (rs.next()) {

                cardTypeList.put(rs.getString("CARDTYPECODE"), rs.getString("DESCRIPTION"));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            getCardType.close();
        }
        return cardTypeList;

    }

    public HashMap<String, String> getCardProduct(Connection CMSCon) throws Exception {
        PreparedStatement getCardProduct = null;
        cardProductList = new HashMap<String, String>();
        try {
            getCardProduct = CMSCon.prepareStatement("SELECT P.PRODUCTCODE, P.DESCRIPTION FROM CARDPRODUCT P");

            rs = getCardProduct.executeQuery();

            while (rs.next()) {

                cardProductList.put(rs.getString("PRODUCTCODE"), rs.getString("DESCRIPTION"));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            getCardProduct.close();
        }
        return cardProductList;

    }

    public HashMap<String, String> getCardProductByCardType(Connection CMSCon, String cardType) throws Exception {
        PreparedStatement getCardProduct = null;
        cardProductList = new HashMap<String, String>();
        try {
            getCardProduct = CMSCon.prepareStatement("SELECT P.PRODUCTCODE, P.DESCRIPTION "
                    + "FROM CARDPRODUCT P WHERE P.CARDTYPE=? AND P.STATUS = ?");

            getCardProduct.setString(1, cardType);
            getCardProduct.setString(2, StatusVarList.ACTIVE_STATUS);
            rs = getCardProduct.executeQuery();
            while (rs.next()) {

                cardProductList.put(rs.getString("PRODUCTCODE"), rs.getString("DESCRIPTION"));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            getCardProduct.close();
        }
        return cardProductList;

    }

    public List<CardDistributionReportBean> searchCardDistributeReport(Connection CMSCon, CardDistributionReportBean distributeBean) throws Exception {

        PreparedStatement getDistributeList = null;
        distributeList = new ArrayList<CardDistributionReportBean>();
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT CD.CARDNUMBER,CD.CARDTYPE,CD.CARDPRODUCT,CD.EXPIERYDATE,CD.CARDSTATUS,"
                    + " CA.APPLICATIONID,CA.IDENTIFICATIONTYPE,CA.IDENTIFICATIONNO,CH.LASTUPDATEDUSER,"
                    + " CH.LASTUPDATEDTIME,CT.DESCRIPTION AS CTDESCRIPTION,CP.DESCRIPTION AS CPDESCRIPTION,"
                    + " ST.DESCRIPTION AS STDESCRIPTION, AD.DOCUMENTNAME"
                    + " FROM CARD CD,CARDAPPLICATION CA,CARDAPPLICATIONHISTORY CH,APPLICATIONDOCUMENT AD,"
                    + " CARDTYPE CT,CARDPRODUCT CP,STATUS ST "
                    + " WHERE CD.APPLICATIONID=CA.APPLICATIONID "
                    + " AND CH.APPLICATIONID=CA.APPLICATIONID "
                    + " AND CA.IDENTIFICATIONTYPE =AD.DOCUMENTTYPECODE"
                    + " AND CA.CARDCATEGORY=AD.CARDCATEGORYCODE"
                    + " AND CT.CARDTYPECODE = CD.CARDTYPE "
                    + " AND CP.PRODUCTCODE = CD.CARDPRODUCT "
                    + " AND ST.STATUSCODE =CD.CARDSTATUS "
                    + " AND CD.CARDDISTRIBUTESTATUS =? "
                    + " AND CH.APPLICATIONLEVEL=?  ";

            String condition = "";

            if (!distributeBean.getNic().contentEquals("") || distributeBean.getNic().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(CA.IDENTIFICATIONNO) LIKE '%" + distributeBean.getNic().toUpperCase() + "%' AND CA.IDENTIFICATIONTYPE= 'NIC' ";
            }

            if (!distributeBean.getPassport().contentEquals("") || distributeBean.getPassport().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(CA.IDENTIFICATIONNO) LIKE '%" + distributeBean.getPassport().toUpperCase() + "%' AND CA.IDENTIFICATIONTYPE ='PAS' ";
            }

//            if (!distributeBean.getDrivingLicence().contentEquals("") || distributeBean.getDrivingLicence().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += " UPPER(CA.IDENTIFICATIONNO) LIKE '%" + distributeBean.getDrivingLicence().toUpperCase() + "%' AND CA.IDENTIFICATIONTYPE = 'DRL' ";
//            }

            if (!distributeBean.getApplicationID().contentEquals("") || distributeBean.getApplicationID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.APPLICATIONID LIKE '%" + distributeBean.getApplicationID() + "%'";
            }

            if (!distributeBean.getCardNumber().contentEquals("") || distributeBean.getCardNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CD.CARDNUMBER LIKE '%" + distributeBean.getCardNumber() + "%'";
            }

            if (!distributeBean.getBranch().contentEquals("") || distributeBean.getBranch().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.BRANCHCODE = " + distributeBean.getBranch() + " ";
            }

            if (!distributeBean.getPriorityLevel().contentEquals("") || distributeBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.PRIORITYLEVELCODE = " + distributeBean.getPriorityLevel() + " ";
            }

            if (!distributeBean.getDomain().contentEquals("") || distributeBean.getDomain().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.CARDAPPLIACTIONDOMAIN = '" + distributeBean.getDomain() + "' ";
            }

            if (!distributeBean.getDistributedUser().contentEquals("") || distributeBean.getDistributedUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CH.LASTUPDATEDTIME = '" + distributeBean.getDistributedUser() + "' ";
            }
            
             if (!distributeBean.getCardType().contentEquals("") || distributeBean.getCardType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CD.CARDTYPE = '" + distributeBean.getCardType() + "' ";
            }
             
              if (!distributeBean.getCardProduct().contentEquals("") || distributeBean.getCardProduct().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CD.CARDPRODUCT = '" + distributeBean.getCardProduct() + "' ";
            }

            if (!distributeBean.getFromDate().contentEquals("") || distributeBean.getFromDate().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.CREATETIME >= TO_DATE('" + this.stringTodate(distributeBean.getFromDate()) + "','yyyy-MM-dd') ";
            }

            if (!distributeBean.getToDate().contentEquals("") || distributeBean.getToDate().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.CREATETIME - 1 <=  TO_DATE('" + this.stringTodate(distributeBean.getToDate()) + "','yyyy-MM-dd') ";
            }

            if (!condition.equals("")) {
                strSqlBasic += " AND " + condition + " ORDER BY CD.CARDNUMBER DESC ";
            } else {

                strSqlBasic += " ORDER BY CD.CARDNUMBER DESC ";
            }

            getDistributeList = CMSCon.prepareStatement(strSqlBasic);
            getDistributeList.setString(1, StatusVarList.YESSTATUS);
            getDistributeList.setString(2, StatusVarList.APP_CARD_DISTRIBUTION);
            rs = getDistributeList.executeQuery();

            while (rs.next()) {

                CardDistributionReportBean bean = new CardDistributionReportBean();

                bean.setCardNumber(rs.getString("CARDNUMBER"));
                bean.setCardTypeDes(rs.getString("CTDESCRIPTION"));
                bean.setCardProductDes(rs.getString("CPDESCRIPTION"));
                bean.setExpiryDate(rs.getString("EXPIERYDATE"));
                bean.setDistributedDate(this.convertStringDate(rs.getDate("LASTUPDATEDTIME")));
                bean.setApplicationID(rs.getString("APPLICATIONID"));
                bean.setIdentificationNo(rs.getString("IDENTIFICATIONNO"));
                bean.setIdentificationTypeDes(rs.getString("DOCUMENTNAME"));
                bean.setDistributedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setCardStatusDes(rs.getString("STDESCRIPTION"));

                distributeList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getDistributeList.close();

        }
        return distributeList;
    }

    private String convertStringDate(java.sql.Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return (dateFormat.format(date));
    }

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));
    }
}
