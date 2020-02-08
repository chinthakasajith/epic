/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.pingenarationandmail.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingCardDetailsBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingSearchBean;
import com.epic.cms.cpmm.pingenarationandmail.been.DomainBean;
import com.epic.cms.cpmm.pingenarationandmail.been.SearchBulkPingenBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.system.util.logs.LogFileCreator;
import com.epic.cms.system.util.variable.DataTypeVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class PinGenerationAndMailPersistance {

    private List<CardEmbossingCardDetailsBean> cardEmbossingVISAList = null;
    private List<CardEmbossingCardDetailsBean> pinMailList = null;
    private List<SearchBulkPingenBean> bulkPinGenList = null;

    public List<CardEmbossingCardDetailsBean> getAllPinGenerationList(Connection con, CardEmbossingSearchBean searchBean, String domain) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllVisaCard = null;
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT CA.APPLICATIONID,CD.CARDNUMBER,CD.MAINCARDNO,CD.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,CD.CARDPRODUCT,CP.DESCRIPTION AS CARDPRODUCTDES,"
                    + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,ST.DESCRIPTION AS CARDSTATUSDES ,CD.CREDITLIMIT,CD.CASHLIMIT,"
                    + "CD.EMBOSSSTATUS, CD.NOGENARATEDUSER ,ST2.DESCRIPTION AS EMBOSSSTATUSDES,"
                    + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
                    + "FROM CARD CD,STATUS ST,STATUS ST2,CARDTYPE CT,CARDPRODUCT CP,CARDAPPLICATION CA";

            String condition = "";

            if (!searchBean.getCardNumber().contentEquals("") || searchBean.getCardNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.CARDNUMBER LIKE '%" + searchBean.getCardNumber() + "%'";
            }

            if (!searchBean.getIdentityType().contentEquals("") || searchBean.getIdentityType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.IDTYPE = '" + searchBean.getIdentityType() + "'";
            }

            if (!searchBean.getIdentityNo().contentEquals("") || searchBean.getIdentityNo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.IDNUMBER = '" + searchBean.getIdentityNo() + "'";
            }
            if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.PRIORITYLEVEL = '" + searchBean.getPriorityLevel() + "'";
            }
            if (!searchBean.getCardtype().contentEquals("") || searchBean.getCardtype().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.CARDTYPE = '" + searchBean.getCardtype() + "'";
            }
            if (!searchBean.getCardProduct().contentEquals("") || searchBean.getCardProduct().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.CARDPRODUCT = '" + searchBean.getCardProduct() + "'";
            }

            if (!searchBean.getGeneratedUser().contentEquals("") || searchBean.getGeneratedUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.NOGENARATEDUSER = '" + searchBean.getGeneratedUser() + "'";
            }

//            if (!searchBean.getCategoryKey().contentEquals("") || searchBean.getCategoryKey().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "CD.CARDCATEGORYCODE = '" + searchBean.getCategoryKey() + "'";
//            }            
            if (!searchBean.getStatus().contentEquals("") || searchBean.getStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.CARDSTATUS = '" + searchBean.getStatus() + "'";
            }

//            if (!searchBean.getEmbossType().contentEquals("") || searchBean.getEmbossType().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "a.ASSIGNUSER = '" + searchBean.getEmbossType() + "'";
//            }
            if (!condition.equals("")) {
                strSqlBasic += " WHERE CD.CARDSTATUS = ST.STATUSCODE AND CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND CD.APPLICATIONID = CA.APPLICATIONID AND CD.PINSTATUS=?  AND CD.CARDDOMAIN = ?  AND " + condition;
            } else {
                strSqlBasic += " WHERE CD.CARDSTATUS = ST.STATUSCODE AND CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND CD.APPLICATIONID = CA.APPLICATIONID AND CD.PINSTATUS=? AND CD.CARDDOMAIN = ? ";
            }

            getAllVisaCard = con.prepareStatement(strSqlBasic);

            getAllVisaCard.setString(1, StatusVarList.NOSTATUS);
            getAllVisaCard.setString(2, domain);
            rs = getAllVisaCard.executeQuery();
            cardEmbossingVISAList = new ArrayList<CardEmbossingCardDetailsBean>();
            while (rs.next()) {

                CardEmbossingCardDetailsBean resultBean = new CardEmbossingCardDetailsBean();

                resultBean.setCardNumber(rs.getString("CARDNUMBER"));
//                resultBean.setAccNo(rs.getString("ACCOUNTNO"));
                resultBean.setMainCardNo(rs.getString("MAINCARDNO"));
//                resultBean.setCustomerId(rs.getString("CUSTOMERID"));
                resultBean.setCardtype(rs.getString("CARDTYPE"));
                resultBean.setCardtypeDes(rs.getString("CARDTYPEDES"));
                resultBean.setCardProduct(rs.getString("CARDPRODUCT"));
                resultBean.setCardProductDes(rs.getString("CARDPRODUCTDES"));
                resultBean.setExpiryDate(rs.getString("EXPIERYDATE"));
                resultBean.setActivationCode(rs.getString("ACTIVATIONDATE"));
                resultBean.setNameOnCard(rs.getString("NAMEONCARD"));
                resultBean.setCardStatus(rs.getString("CARDSTATUS"));
                resultBean.setCardStatusDes(rs.getString("CARDSTATUSDES"));
                resultBean.setCreditLimit(rs.getString("CREDITLIMIT"));
                resultBean.setCashLimit(rs.getString("CASHLIMIT"));;
                resultBean.setEmbossStatus(rs.getString("EMBOSSSTATUS"));
                resultBean.setEmbossStatusDes(rs.getString("EMBOSSSTATUSDES"));
                resultBean.setPinStatus(rs.getString("PINSTATUS"));
                resultBean.setLoyaltyPoint(rs.getString("LOYALTYPOINT"));
                resultBean.setOffSet(rs.getString("OFFSET"));
                resultBean.setRedeemPoint(rs.getString("REDEEMPOINT"));
                resultBean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                resultBean.setIdType(rs.getString("IDTYPE"));
                resultBean.setIdNumber(rs.getString("IDNUMBER"));
                resultBean.setIssuedDate(rs.getString("ISSUEDDATE"));
                resultBean.setApplicationId(rs.getString("APPLICATIONID"));

                cardEmbossingVISAList.add(resultBean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllVisaCard.close();

        }

        return cardEmbossingVISAList;
    }

    public List<CardEmbossingCardDetailsBean> getAllPinMailList(Connection con, CardEmbossingSearchBean searchBean, String domain) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT CD.CARDNUMBER,CD.MAINCARDNO,CD.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,CD.CARDPRODUCT,CP.DESCRIPTION AS CARDPRODUCTDES,"
                    + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,ST.DESCRIPTION AS CARDSTATUSDES,CD.CREDITLIMIT,CD.CASHLIMIT,"
                    + "CD.EMBOSSSTATUS,CD.NOGENARATEDUSER, ST2.DESCRIPTION AS EMBOSSSTATUSDES,"
                    + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
                    + "FROM CARD CD,STATUS ST,STATUS ST2,CARDTYPE CT ,CARDPRODUCT CP,CARDAPPLICATION CA";

            String condition = "";

            if (!searchBean.getCardNumber().contentEquals("") || searchBean.getCardNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.CARDNUMBER LIKE '%" + searchBean.getCardNumber() + "%'";
            }

            if (!searchBean.getIdentityType().contentEquals("") || searchBean.getIdentityType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.IDTYPE = '" + searchBean.getIdentityType() + "'";
            }

            if (!searchBean.getIdentityNo().contentEquals("") || searchBean.getIdentityNo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.IDNUMBER = '" + searchBean.getIdentityNo() + "'";
            }
            if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.PRIORITYLEVEL = '" + searchBean.getPriorityLevel() + "'";
            }
            if (!searchBean.getCardtype().contentEquals("") || searchBean.getCardtype().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.CARDTYPE = '" + searchBean.getCardtype() + "'";
            }
            if (!searchBean.getCardProduct().contentEquals("") || searchBean.getCardProduct().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.CARDPRODUCT = '" + searchBean.getCardProduct() + "'";
            }

            if (!searchBean.getGeneratedUser().contentEquals("") || searchBean.getGeneratedUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.NOGENARATEDUSER = '" + searchBean.getGeneratedUser() + "'";
            }

//            if (!searchBean.getCategoryKey().contentEquals("") || searchBean.getCategoryKey().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "CD.CARDCATEGORYCODE = '" + searchBean.getCategoryKey() + "'";
//            }            
            if (!searchBean.getStatus().contentEquals("") || searchBean.getStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.CARDSTATUS = '" + searchBean.getStatus() + "'";
            }
//            if (!searchBean.getEmbossType().contentEquals("") || searchBean.getEmbossType().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "a.ASSIGNUSER = '" + searchBean.getEmbossType() + "'";
//            }

            if (!condition.equals("")) {
                strSqlBasic += " WHERE CD.CARDSTATUS = ST.STATUSCODE AND CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND CD.APPLICATIONID = CA.APPLICATIONID AND CD.PINMAILSTATUS=? AND CD.CARDDOMAIN = ? AND " + condition;
            } else {
                strSqlBasic += " WHERE CD.CARDSTATUS = ST.STATUSCODE AND CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND CD.APPLICATIONID = CA.APPLICATIONID AND CD.PINMAILSTATUS=? AND CD.CARDDOMAIN = ? ";
            }

            getAllMasterCard = con.prepareStatement(strSqlBasic);

            getAllMasterCard.setString(1, StatusVarList.NOSTATUS);
            getAllMasterCard.setString(2, domain);

            rs = getAllMasterCard.executeQuery();
            pinMailList = new ArrayList<CardEmbossingCardDetailsBean>();

            while (rs.next()) {

                CardEmbossingCardDetailsBean resultBean = new CardEmbossingCardDetailsBean();

                resultBean.setCardNumber(rs.getString("CARDNUMBER"));
//                resultBean.setAccNo(rs.getString("ACCOUNTNO"));
                resultBean.setMainCardNo(rs.getString("MAINCARDNO"));
//                resultBean.setCustomerId(rs.getString("CUSTOMERID"));
                resultBean.setCardtype(rs.getString("CARDTYPE"));
                resultBean.setCardtypeDes(rs.getString("CARDTYPEDES"));
                resultBean.setCardProduct(rs.getString("CARDPRODUCT"));
                resultBean.setCardProductDes(rs.getString("CARDPRODUCTDES"));
                resultBean.setExpiryDate(rs.getString("EXPIERYDATE"));
                resultBean.setActivationCode(rs.getString("ACTIVATIONDATE"));
                resultBean.setNameOnCard(rs.getString("NAMEONCARD"));
                resultBean.setCardStatus(rs.getString("CARDSTATUS"));
                resultBean.setCardStatusDes(rs.getString("CARDSTATUSDES"));
                resultBean.setCreditLimit(rs.getString("CREDITLIMIT"));
                resultBean.setCashLimit(rs.getString("CASHLIMIT"));
                resultBean.setEmbossStatus(rs.getString("EMBOSSSTATUS"));
                resultBean.setEmbossStatusDes(rs.getString("EMBOSSSTATUSDES"));
                resultBean.setPinStatus(rs.getString("PINSTATUS"));
                resultBean.setLoyaltyPoint(rs.getString("LOYALTYPOINT"));
                resultBean.setOffSet(rs.getString("OFFSET"));
                resultBean.setRedeemPoint(rs.getString("REDEEMPOINT"));
                resultBean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                resultBean.setIdType(rs.getString("IDTYPE"));
                resultBean.setIdNumber(rs.getString("IDNUMBER"));
                resultBean.setIssuedDate(rs.getString("ISSUEDDATE"));

                pinMailList.add(resultBean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();

        }

        return pinMailList;
    }

    public List<SearchBulkPingenBean> getBulkSearchList(Connection con, SearchBulkPingenBean searchBean) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT BR.BATCHID,BR.CARDDOMAIN,CD.DESCRIPTION AS CARDDOMAINDES,BR.PRODUCTIONMODE,PM.DESCRIPTION AS PMDESCRIPTION,BR.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,BR.CARDPRODUCTCODE,CP.DESCRIPTION AS CARDPRODUCTDES,"
                    + "BR.REQUESTEDUSER,BR.BRANCHCODE,BB.DESCRPTION AS BRANCHDES,BR.STATUS,S.DESCRIPTION AS STATUSDES "
                    + "FROM BULKCARDREQUEST BR,CARDDOMAIN CD,PRODUCTIONMODE PM,CARDTYPE CT ,CARDPRODUCT CP,SYSTEMUSER SU,BANKBRANCH BB,STATUS S";

            String condition = "";

            if (!searchBean.getBatchId().contentEquals("") || searchBean.getBatchId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.BATCHID LIKE '%" + searchBean.getBatchId() + "%'";
            }

            if (!searchBean.getCardDomain().contentEquals("") || searchBean.getCardDomain().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.CARDDOMAIN = '" + searchBean.getCardDomain() + "'";
            }

            if (!searchBean.getProductionMode().contentEquals("") || searchBean.getProductionMode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.PRODUCTIONMODE = '" + searchBean.getProductionMode() + "'";
            }
            if (!searchBean.getCardType().contentEquals("") || searchBean.getCardType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.CARDTYPE = '" + searchBean.getCardType() + "'";
            }

            if (!searchBean.getCardProduct().contentEquals("") || searchBean.getCardProduct().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.CARDPRODUCTCODE = '" + searchBean.getCardProduct() + "'";
            }

            if (!searchBean.getGenUser().contentEquals("") || searchBean.getGenUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.REQUESTEDUSER = '" + searchBean.getGenUser() + "'";
            }

            if (!searchBean.getBranchId().contentEquals("") || searchBean.getBranchId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.BRANCHCODE = '" + searchBean.getBranchId() + "'";
            }
//            if (!searchBean.getEmbossType().contentEquals("") || searchBean.getEmbossType().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "a.ASSIGNUSER = '" + searchBean.getEmbossType() + "'";
//            }

            if (!condition.equals("")) {
                strSqlBasic += " WHERE BB.BANKCODE=(SELECT BANKCODE FROM COMMONPARAMETER) AND BR.CARDDOMAIN = CD.DOMAINID AND BR.PRODUCTIONMODE = PM.PRODUCTIONMODECODE AND BR.CARDTYPE = CT.CARDTYPECODE AND BR.CARDPRODUCTCODE = CP.PRODUCTCODE AND BR.REQUESTEDUSER = SU.USERNAME AND BR.BRANCHCODE = BB.BRANCHCODE AND BR.STATUS = S.STATUSCODE AND " + condition + " AND (BR.STATUS = ? OR BR.STATUS = ?) ORDER BY BR.BATCHID";
            } else {
                strSqlBasic += " WHERE BB.BANKCODE=(SELECT BANKCODE FROM COMMONPARAMETER) AND BR.CARDDOMAIN = CD.DOMAINID AND BR.PRODUCTIONMODE = PM.PRODUCTIONMODECODE AND BR.CARDTYPE = CT.CARDTYPECODE AND BR.CARDPRODUCTCODE = CP.PRODUCTCODE AND BR.REQUESTEDUSER = SU.USERNAME AND BR.BRANCHCODE = BB.BRANCHCODE AND BR.STATUS = S.STATUSCODE AND (BR.STATUS = ? OR BR.STATUS = ?) ORDER BY BR.BATCHID";
            }

            getAllMasterCard = con.prepareStatement(strSqlBasic);

            getAllMasterCard.setString(1, StatusVarList.BULK_CARD_NUMBER_GENERATION_COMPLETE);
            getAllMasterCard.setString(2, StatusVarList.BULK_CARD_PIN_GENE_PENDING);

            rs = getAllMasterCard.executeQuery();
            bulkPinGenList = new ArrayList<SearchBulkPingenBean>();

            while (rs.next()) {

                SearchBulkPingenBean resultBean = new SearchBulkPingenBean();

                resultBean.setBatchId(rs.getString("BATCHID"));
                resultBean.setCardDomain(rs.getString("CARDDOMAIN"));
                resultBean.setDomainDes(rs.getString("CARDDOMAINDES"));
                resultBean.setProductionMode(rs.getString("PRODUCTIONMODE"));
                resultBean.setPmDes(rs.getString("PMDESCRIPTION"));
                resultBean.setCardType(rs.getString("CARDTYPE"));
                resultBean.setCardTypeDes(rs.getString("CARDTYPEDES"));
                resultBean.setCardProduct(rs.getString("CARDPRODUCTCODE"));
                resultBean.setCardProductDes(rs.getString("CARDPRODUCTDES"));
                resultBean.setGenUser(rs.getString("REQUESTEDUSER"));
                resultBean.setBranchId(rs.getString("BRANCHCODE"));
                resultBean.setBranchDes(rs.getString("BRANCHDES"));
                resultBean.setStatus(rs.getString("STATUS"));
                resultBean.setStatusDes(rs.getString("STATUSDES"));

                bulkPinGenList.add(resultBean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();;
            getAllMasterCard.close();

        }

        return bulkPinGenList;
    }

    public List<SearchBulkPingenBean> getBulkPinMailSearchList(Connection con, SearchBulkPingenBean searchBean) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT BR.BATCHID,BR.CARDDOMAIN,CD.DESCRIPTION AS CARDDOMAINDES,BR.PRODUCTIONMODE,PM.DESCRIPTION AS PMDESCRIPTION,BR.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,BR.CARDPRODUCTCODE,CP.DESCRIPTION AS CARDPRODUCTDES,"
                    + "BR.REQUESTEDUSER,BR.BRANCHCODE,BB.DESCRPTION AS BRANCHDES,BR.STATUS,S.DESCRIPTION AS STATUSDES "
                    + "FROM BULKCARDREQUEST BR,CARDDOMAIN CD,PRODUCTIONMODE PM,CARDTYPE CT ,CARDPRODUCT CP,SYSTEMUSER SU,BANKBRANCH BB,STATUS S";

            String condition = "";

            if (!searchBean.getBatchId().contentEquals("") || searchBean.getBatchId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.BATCHID LIKE '%" + searchBean.getBatchId() + "%'";
            }

            if (!searchBean.getCardDomain().contentEquals("") || searchBean.getCardDomain().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.CARDDOMAIN = '" + searchBean.getCardDomain() + "'";
            }

            if (!searchBean.getProductionMode().contentEquals("") || searchBean.getProductionMode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.PRODUCTIONMODE = '" + searchBean.getProductionMode() + "'";
            }
            if (!searchBean.getCardType().contentEquals("") || searchBean.getCardType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.CARDTYPE = '" + searchBean.getCardType() + "'";
            }

            if (!searchBean.getCardProduct().contentEquals("") || searchBean.getCardProduct().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.CARDPRODUCTCODE = '" + searchBean.getCardProduct() + "'";
            }

            if (!searchBean.getGenUser().contentEquals("") || searchBean.getGenUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.REQUESTEDUSER = '" + searchBean.getGenUser() + "'";
            }

            if (!searchBean.getBranchId().contentEquals("") || searchBean.getBranchId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.BRANCHCODE = '" + searchBean.getBranchId() + "'";
            }
//            if (!searchBean.getEmbossType().contentEquals("") || searchBean.getEmbossType().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "a.ASSIGNUSER = '" + searchBean.getEmbossType() + "'";
//            }

            if (!condition.equals("")) {
                strSqlBasic += " WHERE BB.BANKCODE=(SELECT BANKCODE FROM COMMONPARAMETER) AND BR.CARDDOMAIN = CD.DOMAINID AND BR.PRODUCTIONMODE = PM.PRODUCTIONMODECODE AND BR.CARDTYPE = CT.CARDTYPECODE AND BR.CARDPRODUCTCODE = CP.PRODUCTCODE AND BR.REQUESTEDUSER = SU.USERNAME AND BR.BRANCHCODE = BB.BRANCHCODE AND BR.STATUS = S.STATUSCODE AND " + condition + " AND (BR.STATUS = ? OR BR.STATUS = ? OR BR.STATUS = ?) ORDER BY BR.BATCHID";
            } else {
                strSqlBasic += " WHERE BB.BANKCODE=(SELECT BANKCODE FROM COMMONPARAMETER) AND BR.CARDDOMAIN = CD.DOMAINID AND BR.PRODUCTIONMODE = PM.PRODUCTIONMODECODE AND BR.CARDTYPE = CT.CARDTYPECODE AND BR.CARDPRODUCTCODE = CP.PRODUCTCODE AND BR.REQUESTEDUSER = SU.USERNAME AND BR.BRANCHCODE = BB.BRANCHCODE AND BR.STATUS = S.STATUSCODE AND (BR.STATUS = ? OR BR.STATUS = ? OR BR.STATUS = ?) ORDER BY BR.BATCHID";
            }

            getAllMasterCard = con.prepareStatement(strSqlBasic);

            getAllMasterCard.setString(1, StatusVarList.BULK_CARD_PIN_GENE_COMPLETE);
            getAllMasterCard.setString(2, StatusVarList.BULK_CARD_PIN_MAIL_PENDING);
            getAllMasterCard.setString(3, StatusVarList.BULK_CARD_ENCODING_COMPLETE);

            rs = getAllMasterCard.executeQuery();
            bulkPinGenList = new ArrayList<SearchBulkPingenBean>();

            while (rs.next()) {

                SearchBulkPingenBean resultBean = new SearchBulkPingenBean();

                resultBean.setBatchId(rs.getString("BATCHID"));
                resultBean.setCardDomain(rs.getString("CARDDOMAIN"));
                resultBean.setDomainDes(rs.getString("CARDDOMAINDES"));
                resultBean.setProductionMode(rs.getString("PRODUCTIONMODE"));
                resultBean.setPmDes(rs.getString("PMDESCRIPTION"));
                resultBean.setCardType(rs.getString("CARDTYPE"));
                resultBean.setCardTypeDes(rs.getString("CARDTYPEDES"));
                resultBean.setCardProduct(rs.getString("CARDPRODUCTCODE"));
                resultBean.setCardProductDes(rs.getString("CARDPRODUCTDES"));
                resultBean.setGenUser(rs.getString("REQUESTEDUSER"));
                resultBean.setBranchId(rs.getString("BRANCHCODE"));
                resultBean.setBranchDes(rs.getString("BRANCHDES"));
                resultBean.setStatus(rs.getString("STATUS"));
                resultBean.setStatusDes(rs.getString("STATUSDES"));

                bulkPinGenList.add(resultBean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();

        }

        return bulkPinGenList;
    }

    public String getTPKFromOnlineKey(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        String TPK = null;
        int upperIndex = 0;
        try {
            getAllByCatStat = con.prepareStatement("SELECT TPK FROM ECMS_ONLINE_KEYS ");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                TPK = rs.getString("TPK");
            }
        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return TPK;
    }

    public CardEmbossingCardDetailsBean getCardTableDetails(Connection con, String cardNo) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        CardEmbossingCardDetailsBean cardBean = new CardEmbossingCardDetailsBean();
        try {
            getAllByCatStat = con.prepareStatement("SELECT C.PINBLOCK,C.NAMEONCARD,C.ADDRESS1,C.ADDRESS2,C.ADDRESS3,A.DESCRIPTION FROM CARD C,AREA A WHERE A.AREACODE (+)= C.ADDRESS4  AND C.CARDNUMBER = ?");
            getAllByCatStat.setString(1, cardNo);
            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                cardBean.setPinBlock(rs.getString("PINBLOCK"));
                cardBean.setNameOnCard(rs.getString("NAMEONCARD"));
                cardBean.setAddress1(rs.getString("ADDRESS1"));
                cardBean.setAddress2(rs.getString("ADDRESS2"));
                cardBean.setAddress3(rs.getString("ADDRESS3"));
                cardBean.setAddress4(rs.getString("DESCRIPTION"));
            }
        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardBean;
    }

    public List<CardProductBean> getBulkCardProductList(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        CardProductBean cProduct = null;
        List<CardProductBean> cpList = new ArrayList<CardProductBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT PRODUCTCODE,DESCRIPTION FROM CARDPRODUCT WHERE (CARDDOMAIN = ? OR CARDDOMAIN = ? OR CARDDOMAIN = ? OR CARDDOMAIN = ?)");

            getAllByCatStat.setString(1, DataTypeVarList.CARD_DOMAIN_DEBIT);
            getAllByCatStat.setString(2, DataTypeVarList.CARD_DOMAIN_GIFT);
            getAllByCatStat.setString(3, DataTypeVarList.CARD_DOMAIN_LOYALTY);
            getAllByCatStat.setString(4, DataTypeVarList.CARD_DOMAIN_PREPAID);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                cProduct = new CardProductBean();

                cProduct.setProductCode(rs.getString("PRODUCTCODE"));
                cProduct.setDescription(rs.getString("DESCRIPTION"));

                cpList.add(cProduct);
            }
        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cpList;
    }

    public List<String> getBulkCardList(Connection con, String batchId) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;

        List<String> cardList = new ArrayList<String>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT CARDNUMBER FROM CARD WHERE BATCHID = ? AND PINSTATUS = ?");

            getAllByCatStat.setString(1, batchId);
            getAllByCatStat.setString(2, StatusVarList.NOSTATUS);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                cardList.add(rs.getString("CARDNUMBER"));
            }
        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardList;
    }

    public List<String> getBulkPinMailCardList(Connection con, String batchId) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;

        List<String> cardList = new ArrayList<String>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT CARDNUMBER FROM CARD WHERE BATCHID = ? AND PINMAILSTATUS = ?");

            getAllByCatStat.setString(1, batchId);
            getAllByCatStat.setString(2, StatusVarList.NOSTATUS);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                cardList.add(rs.getString("CARDNUMBER"));
            }
        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardList;
    }

    public BulkCardRequestBean getBulkCardReqDetails(Connection con, String batchId) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;

        BulkCardRequestBean bulkReq = new BulkCardRequestBean();
        try {
            getAllByCatStat = con.prepareStatement("SELECT CD.DESCRIPTION FROM BULKCARDREQUEST BR,CARDDOMAIN CD WHERE BR.CARDDOMAIN = CD.DOMAINID AND BR.BATCHID = ?");

            getAllByCatStat.setString(1, batchId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                bulkReq.setCdDomainDes(rs.getString("DESCRIPTION"));
            }
        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return bulkReq;
    }

    public List<DomainBean> getBulkCardDomains(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        DomainBean cDomain = null;
        List<DomainBean> cDomainList = new ArrayList<DomainBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT DOMAINID,DESCRIPTION FROM CARDDOMAIN WHERE DOMAINID != ? ");

            getAllByCatStat.setString(1, DataTypeVarList.CARD_DOMAIN_CREDIT);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                cDomain = new DomainBean();

                cDomain.setDomainId(rs.getString("DOMAINID"));
                cDomain.setDescription(rs.getString("DESCRIPTION"));

                cDomainList.add(cDomain);
            }
        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cDomainList;
    }

    public Boolean isCardnumberAvailable(Connection con, String cardNumber) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        Boolean status = false;
        try {
            getAllByCatStat = con.prepareStatement("SELECT COUNT (*) AS COUNT FROM ECMS_ONLINE_CARD WHERE CARDNUMBER = ?");
            getAllByCatStat.setString(1, cardNumber);
            rs = getAllByCatStat.executeQuery();

            if (rs.next()) {
                int count = Integer.parseInt(rs.getString("COUNT").trim());

                if (count > 0) {
                    status = true;
                } else {
                    status = false;
                }
            } else {
                status = false;
            }
        } catch (SQLException ex) {
            status = false;
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return status;
    }

    public Boolean updateOnlineCard(Connection con, String eppkPin, String pvv, String offset, String pinMethod, String cvv, String cardNo) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        Boolean status = false;
        try {
            getAllByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CARD SET PVV = ?,IBMOFFSET = ?,PINMETHOD = ?,PINCOUNT = '0' WHERE CARDNUMBER = ?");

            getAllByCatStat.setString(1, pvv);
            getAllByCatStat.setString(2, offset);
            getAllByCatStat.setString(3, pinMethod);
            getAllByCatStat.setString(4, cardNo);

            rs = getAllByCatStat.executeQuery();

            status = true;

        } catch (SQLException ex) {
            status = false;
            ex.printStackTrace();
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return status;
    }

    public Boolean updateOnlineCard(Connection con, String cardNo) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        Boolean status = false;
        try {
            getAllByCatStat = con.prepareStatement("UPDATE CARD SET PINMAILSTATUS = ? WHERE CARDNUMBER = ?");
            getAllByCatStat.setString(1, StatusVarList.YESSTATUS);
            getAllByCatStat.setString(2, cardNo);

            rs = getAllByCatStat.executeQuery();

            status = true;

        } catch (SQLException ex) {
            status = false;
            LogFileCreator.writePinMailerErrorToLog(ex);
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return status;
    }

    public void updateBulkReqqestTable(Connection con, String status, String batchID) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("UPDATE BULKCARDREQUEST SET STATUS = ?,PINSTATUS = ? WHERE BATCHID = ?");
            getAllByCatStat.setString(1, status);
            getAllByCatStat.setString(2, StatusVarList.YESSTATUS);
            getAllByCatStat.setString(3, batchID);

            rs = getAllByCatStat.executeQuery();

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
    }

    public void updateBulkReqqestTableForPinMail(Connection con, String status, String batchID) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("UPDATE BULKCARDREQUEST SET STATUS = ? WHERE BATCHID = ?");
            getAllByCatStat.setString(1, status);
            getAllByCatStat.setString(2, batchID);

            rs = getAllByCatStat.executeQuery();

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
    }

    public Boolean updateCardTable(Connection con, String eppkPin, String pvv, String cvv, String cvv2, String icvv, String offset, String pvki, String cardNo, String pinMethod) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        Boolean status = false;
        try {
            getAllByCatStat = con.prepareStatement("UPDATE CARD SET PINBLOCK = ?,PVV = ?,CVV = ?,CVV2 = ?,ICVV = ?,PINSTATUS = ?,OFFSET = ?,PVKI = ?,PINMETHOD = ? WHERE CARDNUMBER = ?");

            getAllByCatStat.setString(1, eppkPin);
            getAllByCatStat.setString(2, pvv);
            getAllByCatStat.setString(3, cvv);
            getAllByCatStat.setString(4, cvv2);
            getAllByCatStat.setString(5, icvv);
            getAllByCatStat.setString(6, StatusVarList.YESSTATUS);
            getAllByCatStat.setString(7, offset);
            getAllByCatStat.setString(8, pvki);
            getAllByCatStat.setString(9, pinMethod);
            getAllByCatStat.setString(10, cardNo);

            rs = getAllByCatStat.executeQuery();

            status = true;

        } catch (SQLException ex) {
            status = false;
            ex.printStackTrace();
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return status;
    }

    public HashMap<String, String> getCardCategoryList(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;

        HashMap<String, String> map = new HashMap<String, String>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT CATEGORYCODE,DESCRIPTION FROM CARDCATEGORY WHERE CARDDOMAIN = ? ORDER BY DESCRIPTION");
            getAllByCatStat.setString(1, StatusVarList.CREDIT);
            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                map.put(rs.getString("CATEGORYCODE"), rs.getString("DESCRIPTION"));
            }
            return map;
        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
    }

}
