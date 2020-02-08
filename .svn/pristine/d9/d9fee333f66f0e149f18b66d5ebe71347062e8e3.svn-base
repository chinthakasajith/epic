/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.cardtemplate.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ServiceCodeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class CardTemplateMgtPersistance {

    private List<CardTemplateBean> cardTemplateList = null;

    public List<CardTemplateBean> generalCardTemplateSearch(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;		// Holds the Sql query
        try {


            strSqlBasic = " SELECT r.TEMPLATECODE,r.TEMPLATENAME ,r.VALIDFROM  , "
                    + " r.VALIDTO,r.PRODUCTCODE  ,r.CURRENCYCODE  ,r.TOTALCREDITLIMIT, r.STATUS , "
                    + " r.STAFFSTATUS ,r.ACCOUNTTEMPLATECODE, "
                    + " r.LASTUPDATEDUSER,r.LASTUPDATEDTIME,r.CREATEDTIME, "
                    + " r.REWARDPROFILECODE,r.SERVICECODE,r.NEWCARDEXPPERIOD,r.RENEWPERIOD,r.REISSUETHRESHHOLD,r.CASHADVANCERATE,"
                    + " r.FEEPROFILECODE,r.RISKPROFILECODE,r.TRNSACTIONPROFILECODE,f.DESCRIPTION AS FDESCRIPTION,cp.DESCRIPTION AS CPDESCRIPTION,"
                    + " rp.DESCRIPTION AS RPDESCRIPTION,t.DESCRIPTION AS TDESCRIPTION,SC.DESCRIPTION AS SCDESCRIPTION,"
                    + " cu.DESCRIPTION AS CDESCRIPTION,ac.TEMPLATENAME AS ACTEMPLATENAME,S.DESCRIPTION AS SDESCRIPTION,r.CARDCATEGORY AS CARDCATEGORY "
                    + " FROM CARDTEMPLATE r,STATUS s,FEEPROFILE f,RISKPROFILE rp,TRANSACTIONPROFILE t,CURRENCY cu,"
                    + " ACCOUNTTEMPLATE ac,CARDPRODUCT cp, SERVICECODE SC "
                    + " WHERE s.STATUSCODE = r.STATUS AND cu.CURRENCYNUMCODE=r.CURRENCYCODE AND ac.TEMPLATECODE=r.ACCOUNTTEMPLATECODE"
                    + " AND  rp.RISKPROFILECODE=r.RISKPROFILECODE AND cp.PRODUCTCODE=r.PRODUCTCODE"
                    + " AND t.PROFILECODE=r.TRNSACTIONPROFILECODE "
                    + " AND f.FEEPROFILECODE=r.FEEPROFILECODE"
                    + " AND r.SERVICECODE = SC.SERVICECODE ORDER BY r.TEMPLATENAME";


            stmt = con.prepareStatement(strSqlBasic);
            rs = stmt.executeQuery();

            cardTemplateList = new ArrayList<CardTemplateBean>();

            while (rs.next()) {


                CardTemplateBean resultCardTemplateBean = new CardTemplateBean();

                resultCardTemplateBean.setCurrencyCode(zeroPadding(Integer.toString(rs.getInt("CURRENCYCODE"))));
                //resultCardTemplateBean.setCustemplateCode(rs.getString("CUSTOMERTEMPLATECODE"));
                resultCardTemplateBean.setAccounttemplateCode(rs.getString("ACCOUNTTEMPLATECODE"));
                //resultCardTemplateBean.setTransactionFeeProfileCode(rs.getString("TRANSACTIONFEEPROFILECODE"));
                // resultCardTemplateBean.setCardHolderFeeProfileCode(rs.getString("CARDHOLDERFEEPROFILECODE"));
                // resultCardTemplateBean.setInterestProfileCode(rs.getString("INTERESTPROFILECODE"));
                resultCardTemplateBean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                resultCardTemplateBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                resultCardTemplateBean.setProductCode(rs.getString("PRODUCTCODE"));
                resultCardTemplateBean.setRewordProfileCode(rs.getString("REWARDPROFILECODE"));
                resultCardTemplateBean.setStaffStatus(rs.getString("STAFFSTATUS"));
                resultCardTemplateBean.setStatus(rs.getString("STATUS"));
                resultCardTemplateBean.setTemplateCode(rs.getString("TEMPLATECODE"));
                resultCardTemplateBean.setTemplateName(rs.getString("TEMPLATENAME"));
                resultCardTemplateBean.setTotalCreditLimit(Integer.toString(rs.getInt("TOTALCREDITLIMIT")));

                resultCardTemplateBean.setValidFrom(this.convertStringDate(rs.getDate("VALIDFROM")));
                resultCardTemplateBean.setValidTo(this.convertStringDate(rs.getDate("VALIDTO")));

                resultCardTemplateBean.setServiceCode(rs.getString("SERVICECODE"));
                resultCardTemplateBean.setExpiryPeriod(Long.toString(rs.getLong("NEWCARDEXPPERIOD")));
                resultCardTemplateBean.setRenewRequired(Long.toString(rs.getLong("RENEWPERIOD")));
                resultCardTemplateBean.setReissuThrshPeriod(Long.toString(rs.getLong("REISSUETHRESHHOLD")));
                resultCardTemplateBean.setCashAdvanceRate(Long.toString(rs.getLong("CASHADVANCERATE")));
                resultCardTemplateBean.setFeeProfCode(rs.getString("FEEPROFILECODE"));
                resultCardTemplateBean.setRiskProfCode(rs.getString("RISKPROFILECODE"));
                resultCardTemplateBean.setTxnProfCode(rs.getString("TRNSACTIONPROFILECODE"));
                resultCardTemplateBean.setFeeProfDes(rs.getString("FDESCRIPTION"));
                resultCardTemplateBean.setRiskProfDes(rs.getString("RPDESCRIPTION"));
                resultCardTemplateBean.setTxnProfDes(rs.getString("TDESCRIPTION"));
                resultCardTemplateBean.setCurrencyDes(rs.getString("CDESCRIPTION"));
                resultCardTemplateBean.setAccountTempDes(rs.getString("ACTEMPLATENAME"));
                resultCardTemplateBean.setProductCodeDes(rs.getString("CPDESCRIPTION"));
                resultCardTemplateBean.setServiceCodeDes(rs.getString("SCDESCRIPTION"));
                resultCardTemplateBean.setStatusDes(rs.getString("SDESCRIPTION"));
                resultCardTemplateBean.setCardCategoryCode(rs.getString("CARDCATEGORY"));

                cardTemplateList.add(resultCardTemplateBean);

            }

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return cardTemplateList;
    }

    public List<ServiceCodeBean> getActiveServiceCode(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllServiceCode = null;
        List<ServiceCodeBean> serviceList = new ArrayList<ServiceCodeBean>();
        try {
            getAllServiceCode = con.prepareStatement("SELECT SC.SERVICECODE,SC.DESCRIPTION,SC.STATUS,"
                    + " S.DESCRIPTION AS SDESCRIPTION,"
                    + " SC.LASTUPDATEDUSER,SC.LASTUPDATEDTIME,SC.CREATEDTIME"
                    + " FROM SERVICECODE SC,STATUS S"
                    + " WHERE S.STATUSCODE=SC.STATUS AND SC.STATUS='ACT'");

            rs = getAllServiceCode.executeQuery();

            while (rs.next()) {

                ServiceCodeBean service = new ServiceCodeBean();

                service.setServiceCode(rs.getString("SERVICECODE"));
                service.setDescription(rs.getString("DESCRIPTION"));
                service.setStatus(rs.getString("STATUS"));
                service.setStatusDes(rs.getString("SDESCRIPTION"));
                service.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                service.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                service.setCreateTime(rs.getDate("CREATEDTIME"));




                serviceList.add(service);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllServiceCode.close();

        }
        return serviceList;
    }

    public boolean deleteCardTemplate(Connection con, String templateCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = con.prepareStatement("DELETE CARDTEMPLATE WHERE TEMPLATECODE=? ");
            deleteStat.setString(1, templateCode);

            deleteStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            deleteStat.close();
        }
        return success;
    }

    public boolean updateCardTemplate(Connection con, CardTemplateBean cardBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARDTEMPLATE SET TEMPLATENAME=?, "
                    + " VALIDFROM=?,VALIDTO=?,PRODUCTCODE=?,CURRENCYCODE=?,TOTALCREDITLIMIT=?,"
                    + " LASTUPDATEDTIME=?,LASTUPDATEDUSER=?,STATUS=?,STAFFSTATUS=?,ACCOUNTTEMPLATECODE=?,"
                    + " FEEPROFILECODE=? ,RISKPROFILECODE=?,TRNSACTIONPROFILECODE=?,"
                    + " SERVICECODE=?,NEWCARDEXPPERIOD=?,RENEWPERIOD=?,REISSUETHRESHHOLD=?,"
                    + " CASHADVANCERATE=?,CARDCATEGORY=? WHERE TEMPLATECODE=? ");

            updateStat.setString(1, cardBean.getTemplateName());

            updateStat.setTimestamp(2, new Timestamp(this.stringToDate(cardBean.getValidFrom()).getTime()));
            updateStat.setTimestamp(3, new Timestamp(this.stringToDate(cardBean.getValidTo()).getTime()));

            //////updateStat.setString(4, cardBean.getProductCode());
            updateStat.setString(4, cardBean.getProductCode());
            updateStat.setInt(5, Integer.parseInt(cardBean.getCurrencyCode()));
            updateStat.setInt(6, Integer.parseInt(cardBean.getTotalCreditLimit()));
            updateStat.setTimestamp(7, new Timestamp(cardBean.getLastUpdatedTime().getTime()));
            updateStat.setString(8, cardBean.getLastUpdatedUser());
            updateStat.setString(9, cardBean.getStatus());
            updateStat.setString(10, cardBean.getStaffStatus());
            updateStat.setString(11, cardBean.getAccounttemplateCode());
//            updateStat.setString(12, cardBean.getCustemplateCode());
//            updateStat.setString(13, cardBean.getInterestProfileCode());
//            updateStat.setString(14, cardBean.getCardHolderFeeProfileCode());
            updateStat.setString(12, cardBean.getFeeProfCode());
            updateStat.setString(13, cardBean.getRiskProfCode());
            updateStat.setString(14, cardBean.getTxnProfCode());




////////    updateStat.setString(15, cardBean.getRewordProfileCode());



            //updateStat.setString(16, cardBean.getTransactionFeeProfileCode());



            updateStat.setString(15, cardBean.getServiceCode());
            updateStat.setLong(16, Long.parseLong(cardBean.getExpiryPeriod()));
            updateStat.setLong(17, Long.parseLong(cardBean.getRenewRequired()));
            updateStat.setLong(18, Long.parseLong(cardBean.getReissuThrshPeriod()));
            updateStat.setLong(19, Long.parseLong(cardBean.getCashAdvanceRate()));
            updateStat.setString(20, cardBean.getCardCategoryCode());
            updateStat.setString(21, cardBean.getTemplateCode());

            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean insertCardTemplate(Connection con, CardTemplateBean cardBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CARDTEMPLATE(TEMPLATECODE,TEMPLATENAME, "
                    + " VALIDFROM,VALIDTO,PRODUCTCODE,CURRENCYCODE,TOTALCREDITLIMIT,STATUS,STAFFSTATUS,"
                    + " FEEPROFILECODE,RISKPROFILECODE,TRNSACTIONPROFILECODE,LASTUPDATEDUSER,"
                    + " ACCOUNTTEMPLATECODE,SERVICECODE,NEWCARDEXPPERIOD,RENEWPERIOD,REISSUETHRESHHOLD,CASHADVANCERATE,CARDCATEGORY) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

            insertStat.setString(1, cardBean.getTemplateCode());
            insertStat.setString(2, cardBean.getTemplateName());

            insertStat.setTimestamp(3, new Timestamp(this.stringToDate(cardBean.getValidFrom()).getTime()));
            insertStat.setTimestamp(4, new Timestamp(this.stringToDate(cardBean.getValidTo()).getTime()));

            ////////      insertStat.setString(5, cardBean.getProductCode());

            insertStat.setString(5, cardBean.getProductCode());
            insertStat.setInt(6, Integer.parseInt(cardBean.getCurrencyCode()));
            insertStat.setInt(7, Integer.parseInt(cardBean.getTotalCreditLimit()));
            insertStat.setString(8, cardBean.getStatus());
            insertStat.setString(9, cardBean.getStaffStatus());

//            insertStat.setString(10, cardBean.getCustemplateCode());
//            insertStat.setString(11, cardBean.getInterestProfileCode());
//            insertStat.setString(12, cardBean.getTransactionFeeProfileCode());
            insertStat.setString(10, cardBean.getFeeProfCode());
            insertStat.setString(11, cardBean.getRiskProfCode());
            insertStat.setString(12, cardBean.getTxnProfCode());

////////      insertStat.setString(13, domainBean.getRewordProfileCode());


            insertStat.setString(13, cardBean.getLastUpdatedUser());
            //insertStat.setString(15, cardBean.getCardHolderFeeProfileCode() );

            insertStat.setString(14, cardBean.getAccounttemplateCode());
            insertStat.setString(15, cardBean.getServiceCode());
            insertStat.setLong(16, Long.parseLong(cardBean.getExpiryPeriod()));
            insertStat.setLong(17, Long.parseLong(cardBean.getRenewRequired()));
            insertStat.setLong(18, Long.parseLong(cardBean.getReissuThrshPeriod()));
            insertStat.setLong(19, Long.parseLong(cardBean.getCashAdvanceRate()));
            insertStat.setString(20, cardBean.getCardCategoryCode());
            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public CardTemplateBean getAllDetailsAccountTemplate(Connection con, CardTemplateBean cardTemplateBean) throws Exception {

        ResultSet rs = null;
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;		// Holds the Sql query
        try {


            strSqlBasic = " SELECT r.TEMPLATECODE ,r.TEMPLATENAME ,r.VALIDFROM  , "
                    + " r.VALIDTO,r.PRODUCTCODE  ,r.CURRENCYCODE  ,r.TOTALCREDITLIMIT, r.STATUS , "
                    + "r.STAFFSTATUS,r.CUSTOMERTEMPLATECODE  ,r.INTERESTPROFILECODE, "
                    + "r.LASTUPDATEDUSER,r.LASTUPDATEDTIME,r.CREATEDTIME,r.CARDTYPE "
                    + " FROM ACCOUNTTEMPLATE r,STATUS s WHERE r.TEMPLATECODE =? AND s.STATUSCODE = r.STATUS ORDER BY r.TEMPLATENAME";


            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, cardTemplateBean.getAccounttemplateCode());

            rs = stmt.executeQuery();


            while (rs.next()) {


                cardTemplateBean.setAccounttemplateCode(rs.getString("TEMPLATECODE"));
                //cardTemplateBean.setCurrencyCode(zeroPadding(rs.getString("CURRENCYCODE")));
                cardTemplateBean.setCustemplateCode(rs.getString("CUSTOMERTEMPLATECODE"));
                //cardTemplateBean.setTransactionFeeProfileCode(rs.getString("TRANSACTIONFEEPROFILECODE"));
                //cardTemplateBean.setCardHolderFeeProfileCode(rs.getString("CARDHOLDERFEEPROFILECODE"));
                cardTemplateBean.setInterestProfileCode(rs.getString("INTERESTPROFILECODE"));
                cardTemplateBean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                cardTemplateBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                //cardTemplateBean.setProductCode(rs.getString("PRODUCTCODE"));
                //cardTemplateBean.setRewordProfileCode(rs.getString("REWARDPROFILECODE"));
                cardTemplateBean.setTotalCreditLimit(rs.getString("TOTALCREDITLIMIT"));
                cardTemplateBean.setStaffStatus(rs.getString("STAFFSTATUS"));

                cardTemplateBean.setValidFrom(this.convertStringDate(rs.getDate("VALIDFROM")));
                cardTemplateBean.setValidTo(this.convertStringDate(rs.getDate("VALIDTO")));
                cardTemplateBean.setCardType(rs.getString("CARDTYPE"));


            }

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return cardTemplateBean;
    }

    public List<CardProductBean> getCardProductList(Connection con, String catdType) throws Exception {

        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<CardProductBean> cardProductBeanLst = new ArrayList<CardProductBean>();
        try {

            stmt = con.prepareStatement("SELECT C.PRODUCTCODE, C.DESCRIPTION FROM CARDPRODUCT C"
                    + " WHERE C.CARDTYPE= ? AND CARDDOMAIN = ? ORDER BY C.DESCRIPTION ASC");

            stmt.setString(1, catdType);
            stmt.setString(2, StatusVarList.CREDIT);

            rs = stmt.executeQuery();

            while (rs.next()) {

                CardProductBean bean = new CardProductBean();

                bean.setProductCode(rs.getString("PRODUCTCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                cardProductBeanLst.add(bean);

            }

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return cardProductBeanLst;

    }

    public List<CardProductBean> getUpdateCardProductList(Connection con, String productCode) throws Exception {

        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<CardProductBean> cardProductBeanLst = new ArrayList<CardProductBean>();
        try {

            stmt = con.prepareStatement("SELECT C.PRODUCTCODE, C.DESCRIPTION FROM CARDPRODUCT C"
                    + " WHERE C.CARDTYPE = (SELECT CARDTYPE FROM CARDPRODUCT WHERE PRODUCTCODE =?) AND CARDDOMAIN = ? ORDER BY C.DESCRIPTION ASC");

            stmt.setString(1, productCode);
            stmt.setString(2, StatusVarList.CREDIT);

            rs = stmt.executeQuery();

            while (rs.next()) {

                CardProductBean bean = new CardProductBean();

                bean.setProductCode(rs.getString("PRODUCTCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                cardProductBeanLst.add(bean);

            }

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return cardProductBeanLst;

    }

    public HashMap<String,String> getCurrencyList(Connection con, String productCode) throws Exception {

        HashMap<String,String> currencyList = new HashMap<String,String>();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(" SELECT DISTINCT CU.CURRENCYNUMCODE, CU.DESCRIPTION "
                    + " FROM CURRENCY CU RIGHT JOIN CARDBIN CB "
                    + " ON CU.CURRENCYNUMCODE = CB.CURRENCYTYPECODE "
                    + " RIGHT JOIN CARDPRODUCTBIN CP "
                    + " ON CB.BIN = CP.BINCODE AND CP.PRODUCTCODE=? "
                    + " WHERE CU.CURRENCYNUMCODE IS NOT NULL");

            stmt.setString(1, productCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                currencyList.put(this.zeroPadding(rs.getString("CURRENCYNUMCODE")),rs.getString("DESCRIPTION"));
            }

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return currencyList;

    }

    public Date stringToDate(String dt) throws Exception {
        DateFormat formatter;
        Date date = null;
        try {

            formatter = new SimpleDateFormat("yyyy-mm-dd");
            date = (Date) formatter.parse(dt);

        } catch (Exception ex) {
        }
        return date;
    }

    public String zeroPadding(String code) {
        int len = code.length();

        if (len < 3 && len == 2) {
            code = "0" + code;
        } else if (len < 3 && len == 1) {
            code = "00" + code;
        }

        return code;
    }

    private String convertStringDate(java.sql.Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return (dateFormat.format(date));
    }
}
