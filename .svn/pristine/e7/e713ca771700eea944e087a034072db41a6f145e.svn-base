/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.debitcardtemplate.persistance;

import com.epic.cms.admin.templatemgt.debitcardtemplate.bean.DebitCardTemplateBean;
import com.epic.cms.admin.templatemgt.debitcardtemplate.bean.cardProductsBean;
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
public class DebitCardTemplateMgtPersistance {

    private List<DebitCardTemplateBean> debitCardardList = null;
    private HashMap<String, String> debitAccountTemplateList = null;
    //m0difying
    private HashMap<String, String> feeProfiles = null;
    //private HashMap<String, String> cproducts = null;
    private List<cardProductsBean> cproducts = null;
    private HashMap<String, String> riskProfiles = null;
    private HashMap<String, String> transactionProfiles = null;
    private DebitCardTemplateBean tempbean;

    public List<DebitCardTemplateBean> generalDebitCardTemplateSearch(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;		// Holds the Sql query
        try {

            strSqlBasic = "SELECT DCT.TEMPLATECODE,DCT.TEMPLATENAME,DCT.STATUS,S.DESCRIPTION AS STATUS_DES,DCT.DEBITACCOUNTTEMPLATECODE,"
                    + "DCAT.TEMPLATENAME AS ACC_TEMP_DES,DCT.VALIDFROM,DCT.VALIDTO,DCT.CARDTYPE,CT.DESCRIPTION AS CD_TYPE_DES,DCT.CURRENCYCODE,"
                    + "C.DESCRIPTION AS CURR_DES,DCT.CARDEXPPERIOD,DCT.RENEWPERIOD,DCT.REISSUETHRESHHOLD,DCT.FEEPROFILECODE,FP.DESCRIPTION AS FEE_DES,"
                    + "DCT.TRNSACTIONPROFILECODE,TP.DESCRIPTION AS TXN_DES,DCT.RISKPROFILECODE,RP.DESCRIPTION AS RSK_DES,DCT.CARDDOMAIN,"
                    + "DCT.CASHADVANCERATE,CD.DESCRIPTION AS DOMAIN_DES,DCT.SERVICECODE,SC.DESCRIPTION AS SERVICE_CD_DES,DCT.PRODUCTCODE,CP.DESCRIPTION as cpdes "
                    + "FROM DEBITCARDTEMPLATE DCT,STATUS S,CARDTYPE CT,CURRENCY C,FEEPROFILE FP,TRANSACTIONPROFILE TP,RISKPROFILE RP,"
                    + "DEBITCARDACCOUNTTEMPLATE DCAT,CARDDOMAIN CD,SERVICECODE SC,CARDPRODUCT CP "
                    + "WHERE S.STATUSCODE=DCT.STATUS "
                    + "AND DCAT.TEMPLATECODE=DCT.DEBITACCOUNTTEMPLATECODE "
                    + "AND CT.CARDTYPECODE=DCT.CARDTYPE "
                    + "AND DCT.CURRENCYCODE=C.CURRENCYNUMCODE "
                    + "AND FP.FEEPROFILECODE=DCT.FEEPROFILECODE "
                    + "AND TP.PROFILECODE=DCT.TRNSACTIONPROFILECODE "
                    + "AND RP.RISKPROFILECODE=DCT.RISKPROFILECODE "
                    + "AND CD.DOMAINID=DCT.CARDDOMAIN "
                    + "AND SC.SERVICECODE=DCT.SERVICECODE "
                    + "AND DCT.PRODUCTCODE=CP.PRODUCTCODE " ;

            stmt = con.prepareStatement(strSqlBasic);
            rs = stmt.executeQuery();

            debitCardardList = new ArrayList<DebitCardTemplateBean>();

            while (rs.next()) {
                DebitCardTemplateBean resultCardTemplateBean = new DebitCardTemplateBean();

                resultCardTemplateBean.setTemplateCode(rs.getString("TEMPLATECODE"));
                resultCardTemplateBean.setTemplateName(rs.getString("TEMPLATENAME"));
                resultCardTemplateBean.setStatus(rs.getString("STATUS"));
                resultCardTemplateBean.setStatusDes(rs.getString("STATUS_DES"));
                resultCardTemplateBean.setDebitAccounttemplateCode(rs.getString("DEBITACCOUNTTEMPLATECODE"));
                resultCardTemplateBean.setAccTempName(rs.getString("ACC_TEMP_DES"));
                resultCardTemplateBean.setValidFrom(rs.getString("VALIDFROM").substring(0, 10));
                resultCardTemplateBean.setValidTo(rs.getString("VALIDTO").substring(0, 10));
                resultCardTemplateBean.setCardTypeCode(rs.getString("CARDTYPE"));
                resultCardTemplateBean.setCardTypeDes(rs.getString("CD_TYPE_DES"));
                resultCardTemplateBean.setCurrencyCode(rs.getString("CURRENCYCODE"));
                resultCardTemplateBean.setCurrencyDes(rs.getString("CURR_DES"));
                resultCardTemplateBean.setExpPeriod(rs.getString("CARDEXPPERIOD"));
                resultCardTemplateBean.setRenewPeriod(rs.getString("RENEWPERIOD"));
                resultCardTemplateBean.setReissueThreshPeriod(rs.getString("REISSUETHRESHHOLD"));
                resultCardTemplateBean.setFeeProfile(rs.getString("FEEPROFILECODE"));
                resultCardTemplateBean.setFeeProfDes(rs.getString("FEE_DES"));
                resultCardTemplateBean.setTxnProf(rs.getString("TRNSACTIONPROFILECODE"));
                resultCardTemplateBean.setTxnProfDes(rs.getString("TXN_DES"));
                resultCardTemplateBean.setRiskProf(rs.getString("RISKPROFILECODE"));
                resultCardTemplateBean.setRiskProfDes(rs.getString("RSK_DES"));
                resultCardTemplateBean.setCardDomain(rs.getString("CARDDOMAIN"));
                resultCardTemplateBean.setCardDomainDes(rs.getString("DOMAIN_DES"));
                resultCardTemplateBean.setCashAdvanceRate(rs.getString("CASHADVANCERATE"));
                resultCardTemplateBean.setServiceCode(rs.getString("SERVICECODE"));
                resultCardTemplateBean.setServiceCodeDes(rs.getString("SERVICE_CD_DES"));
                resultCardTemplateBean.setProductCode(rs.getString("PRODUCTCODE"));
                resultCardTemplateBean.setProductDes(rs.getString("cpdes"));

                debitCardardList.add(resultCardTemplateBean);

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

        return debitCardardList;
    }

    public boolean updateDebitCardTemplate(Connection con, DebitCardTemplateBean debitBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {

            String sqlBasic = "UPDATE DEBITCARDTEMPLATE SET TEMPLATECODE=?,TEMPLATENAME=?,STATUS=?,DEBITACCOUNTTEMPLATECODE=?,"
                    + "VALIDFROM=?,VALIDTO=?,CARDTYPE=?,CURRENCYCODE=?,CARDEXPPERIOD=?,RENEWPERIOD=?,REISSUETHRESHHOLD=?,FEEPROFILECODE=?,"
                    + "TRNSACTIONPROFILECODE=?,RISKPROFILECODE=?,LASTUPDATEDUSER=?,CARDDOMAIN=?,CASHADVANCERATE=?,SERVICECODE=?,PRODUCTCODE=? "
                    + "WHERE TEMPLATECODE=? ";

            updateStat = con.prepareStatement(sqlBasic);

            updateStat.setString(1, debitBean.getTemplateCode());
            updateStat.setString(2, debitBean.getTemplateName());
            updateStat.setString(3, debitBean.getStatus());
            updateStat.setString(4, debitBean.getDebitAccounttemplateCode());
            updateStat.setTimestamp(5, new Timestamp(stringToDate(debitBean.getValidFrom()).getTime()));
            updateStat.setTimestamp(6, new Timestamp(stringToDate(debitBean.getValidTo()).getTime()));
            updateStat.setString(7, debitBean.getCardTypeCode());
            updateStat.setString(8, debitBean.getCurrencyCode());
            updateStat.setString(9, debitBean.getExpPeriod());
            updateStat.setString(10, debitBean.getRenewPeriod());
            updateStat.setString(11, debitBean.getReissueThreshPeriod());
            updateStat.setString(12, debitBean.getFeeProfile());
            updateStat.setString(13, debitBean.getTxnProf());
            updateStat.setString(14, debitBean.getRiskProf());
            updateStat.setString(15, debitBean.getLastUpdatedUser());
            updateStat.setString(16, debitBean.getCardDomain());
            updateStat.setString(17, debitBean.getCashAdvanceRate());
            updateStat.setString(18, debitBean.getServiceCode());
            updateStat.setString(19, debitBean.getProductCode());
            
            updateStat.setString(20, debitBean.getTemplateCode());

            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean deleteDebitCardTemplate(Connection con, String templateCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = con.prepareStatement("DELETE DEBITCARDTEMPLATE WHERE TEMPLATECODE=? ");
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

    public boolean insertDebitCardTemplate(Connection con, DebitCardTemplateBean debitBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO DEBITCARDTEMPLATE(TEMPLATECODE,TEMPLATENAME,STATUS,DEBITACCOUNTTEMPLATECODE,"
                    + "VALIDFROM,VALIDTO,CARDTYPE,CURRENCYCODE,CARDEXPPERIOD,RENEWPERIOD,REISSUETHRESHHOLD,FEEPROFILECODE,TRNSACTIONPROFILECODE,"
                    + "RISKPROFILECODE,LASTUPDATEDUSER,CARDDOMAIN,CASHADVANCERATE,SERVICECODE,PRODUCTCODE) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            insertStat.setString(1, debitBean.getTemplateCode());
            insertStat.setString(2, debitBean.getTemplateName());
            insertStat.setString(3, debitBean.getStatus());
            insertStat.setString(4, debitBean.getDebitAccounttemplateCode());
            insertStat.setTimestamp(5, new Timestamp(stringToDate(debitBean.getValidFrom()).getTime()));
            insertStat.setTimestamp(6, new Timestamp(stringToDate(debitBean.getValidTo()).getTime()));
            insertStat.setString(7, debitBean.getCardTypeCode());
            insertStat.setString(8, debitBean.getCurrencyCode());
            insertStat.setString(9, debitBean.getExpPeriod());
            insertStat.setString(10, debitBean.getRenewPeriod());
            insertStat.setString(11, debitBean.getReissueThreshPeriod());
            insertStat.setString(12, debitBean.getFeeProfile());
            insertStat.setString(13, debitBean.getTxnProf());
            insertStat.setString(14, debitBean.getRiskProf());
            insertStat.setString(15, debitBean.getLastUpdatedUser());
            insertStat.setString(16, debitBean.getCardDomain());
            insertStat.setString(17, debitBean.getCashAdvanceRate());
            insertStat.setString(18, debitBean.getServiceCode());
            insertStat.setString(19, debitBean.getProductCode());

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public DebitCardTemplateBean getAllDebitDetailsAccountTemplate(Connection con, DebitCardTemplateBean debitBean) throws Exception {

        ResultSet rs = null;
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;		// Holds the Sql query
        try {


            strSqlBasic = " SELECT r.TEMPLATECODE ,r.TEMPLATENAME ,r.VALIDFROM  , "
                    + " r.VALIDTO,r.PRODUCTCODE  ,r.CURRENCYCODE  ,r.TOTALCREDITLIMIT, r.STATUS , "
                    + "r.STAFFSTATUS,r.CUSTOMERTEMPLATECODE  ,r.INTERESTPROFILECODE, "
                    + "r.LASTUPDATEDUSER,r.LASTUPDATEDTIME,r.CREATEDTIME, "
                    + "r.TRANSACTIONFEEPROFILECODE,r.REWARDPROFILECODE,r.CARDHOLDERFEEPROFILECODE FROM DEBITCARDACCOUNTTEMPLATE r,STATUS s WHERE r.TEMPLATECODE =? AND s.STATUSCODE = r.STATUS ORDER BY r.TEMPLATENAME";


            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, debitBean.getDebitAccounttemplateCode());

            rs = stmt.executeQuery();


            while (rs.next()) {


                debitBean.setDebitAccounttemplateCode(rs.getString("TEMPLATECODE"));
                debitBean.setCurrencyCode(rs.getString("CURRENCYCODE"));
                debitBean.setCustemplateCode(rs.getString("CUSTOMERTEMPLATECODE"));
                debitBean.setTransactionFeeProfileCode(rs.getString("TRANSACTIONFEEPROFILECODE"));
                debitBean.setCardHolderFeeProfileCode(rs.getString("CARDHOLDERFEEPROFILECODE"));
                debitBean.setInterestProfileCode(rs.getString("INTERESTPROFILECODE"));
                debitBean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                debitBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                debitBean.setProductCode(rs.getString("PRODUCTCODE"));
                debitBean.setRewordProfileCode(rs.getString("REWARDPROFILECODE"));
                debitBean.setTotalCreditLimit(rs.getString("TOTALCREDITLIMIT"));
                debitBean.setValidFrom(rs.getString("VALIDFROM").substring(0, 10));
                debitBean.setValidTo(rs.getString("VALIDTO").substring(0, 10));


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

        return debitBean;
    }

    public HashMap<String, String> getAllDebitAccountTemplates(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.TEMPLATECODE,TC.TEMPLATENAME "
                    + "FROM DEBITCARDACCOUNTTEMPLATE TC, STATUS ST WHERE TC.STATUS = ST.STATUSCODE");

            rs = getAllUserRole.executeQuery();
            debitAccountTemplateList = new HashMap<String, String>();

            while (rs.next()) {

                debitAccountTemplateList.put(rs.getString("TEMPLATECODE"), rs.getString("TEMPLATENAME"));


            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return debitAccountTemplateList;
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

    public HashMap<String, String> getFeeProfiles(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT F.FEEPROFILECODE,F.DESCRIPTION "
                    + "FROM FEEPROFILE F");

            rs = getAllUserRole.executeQuery();
            feeProfiles = new HashMap<String, String>();

            while (rs.next()) {

                feeProfiles.put(rs.getString("FEEPROFILECODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return feeProfiles;
    }
    
     public List<cardProductsBean> getCardProducts(Connection con, String cdomain, String ctype) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT PRODUCTCODE,DESCRIPTION "
                    + "FROM CARDPRODUCT WHERE CARDDOMAIN=? and CARDTYPE=? ");

            ps.setString(1, cdomain);
            ps.setString(2, ctype);
            
            rs = ps.executeQuery();
            //cproducts = new HashMap<String, String>();
            cproducts = new ArrayList<cardProductsBean>();

            while (rs.next()) {
                
                cardProductsBean bean=new cardProductsBean();

                bean.setProductCode(rs.getString("PRODUCTCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                cproducts.add(bean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return cproducts;
    }
     
     //getDomainAndType
     public DebitCardTemplateBean getDomainAndType(Connection con, String acctemplate) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT AT.CARDDOMAIN, AT.CARDTYPE, CD.DESCRIPTION as cddes, CT.DESCRIPTION as ctdes "
                    + "FROM DEBITCARDACCOUNTTEMPLATE AT, CARDDOMAIN CD, CARDTYPE CT "
                    + "WHERE AT.TEMPLATECODE=? and AT.CARDDOMAIN=CD.DOMAINID and AT.CARDTYPE=CT.CARDTYPECODE ");

            ps.setString(1, acctemplate);
            
            rs = ps.executeQuery();
            
            tempbean = new DebitCardTemplateBean();

            while (rs.next()) {              
                
                tempbean.setCardDomain(rs.getString("CARDDOMAIN"));
                tempbean.setCardDomainDes(rs.getString("cddes"));
                tempbean.setCardTypeCode(rs.getString("CARDTYPE"));
                tempbean.setCardTypeDes(rs.getString("ctdes"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return tempbean;
    }

    public HashMap<String, String> getCardRiskProfiles(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT RP.RISKPROFILECODE,RP.DESCRIPTION "
                    + "FROM RISKPROFILE RP WHERE RP.PROFILETYPE = 'RPCRD'");

            rs = getAllUserRole.executeQuery();
            riskProfiles = new HashMap<String, String>();

            while (rs.next()) {

                riskProfiles.put(rs.getString("RISKPROFILECODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return riskProfiles;
    }

    public HashMap<String, String> getTransactionProfiles(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT TP.PROFILECODE,TP.DESCRIPTION "
                    + "FROM TRANSACTIONPROFILE TP WHERE TP.STATUS = 'ACT'");

            rs = getAllUserRole.executeQuery();
            transactionProfiles = new HashMap<String, String>();

            while (rs.next()) {

                transactionProfiles.put(rs.getString("PROFILECODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return transactionProfiles;
    }
}
