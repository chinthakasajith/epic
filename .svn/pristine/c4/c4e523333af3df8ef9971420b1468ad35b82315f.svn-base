/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.cardembossing.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.EmbossFileFormatDetailBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.cpmm.cardembossing.bean.BulkCardEmbossingDetailsBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingCardDetailsBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingFileDownloadBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingSearchBean;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class CardEmbossingMgtPersistance {

    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> cardProductList = null;
    private HashMap<String, String> identityTypeList = null;
    private String embossFileFormat = null;
    private List<CardEmbossingCardDetailsBean> cardEmbossingVISAList = null;
    private List<BulkCardEmbossingDetailsBean> bulkCardEmbossingList = null;
    private List<CardEmbossingCardDetailsBean> cardEmbossingMASTERList = null;
    private List<EmbossFileFormatDetailBean> fileFormatDetailBean = null;
    private List<CardEmbossingCardDetailsBean> bulkCardDetailsBeanList = null;
    private EmbossFileFormatDetailBean embossDetailBean = null;
    private List<CardEmbossingFileDownloadBean> cardEmbossingFileList = null;
    private CommonFilePathBean commonFilePathBean = null;
    private DocumentUploadBean uploadBean = null;

    public HashMap<String, String> getAllCardType(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllCardType = null;
        try {

            getAllCardType = con.prepareStatement("SELECT TC.CARDTYPECODE,TC.DESCRIPTION "
                    + "FROM CARDTYPE TC");

            rs = getAllCardType.executeQuery();
            cardTypeList = new HashMap<String, String>();

            while (rs.next()) {

                cardTypeList.put(rs.getString("CARDTYPECODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllCardType.close();

        }

        return cardTypeList;
    }

    public HashMap<String, String> getAllIdentityType(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllCardType = null;
        try {

            getAllCardType = con.prepareStatement("SELECT TC.DOCUMENTTYPECODE,TC.DOCUMENTNAME "
                    + "FROM APPLICATIONDOCUMENT TC WHERE TC.VERIFICATIONCATEGORY ='ID'");

            rs = getAllCardType.executeQuery();
            identityTypeList = new HashMap<String, String>();

            while (rs.next()) {

                identityTypeList.put(rs.getString("DOCUMENTTYPECODE"), rs.getString("DOCUMENTNAME"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllCardType.close();

        }

        return identityTypeList;
    }

    public HashMap<String, String> getAllCardProduct(Connection con, String cardType) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllCardType = null;
        try {

            getAllCardType = con.prepareStatement("SELECT TC.PRODUCTCODE,TC.DESCRIPTION "
                    + "FROM CARDPRODUCT TC WHERE TC.CARDTYPE=?");

            getAllCardType.setString(1, cardType);
            rs = getAllCardType.executeQuery();
            cardProductList = new HashMap<String, String>();

            while (rs.next()) {

                cardProductList.put(rs.getString("PRODUCTCODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllCardType.close();

        }

        return cardProductList;
    }

    public List<CardEmbossingCardDetailsBean> getAllVISACardToEmboss(Connection con, CardEmbossingSearchBean searchBean, String cardDomain) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllVisaCard = null;
        String strSqlBasic = null;
        try {

            if (searchBean.getEmbossType().equals("ordinary")) {

                strSqlBasic = "SELECT CD.CARDNUMBER,CD.MAINCARDNO,CD.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,CD.CARDPRODUCT,CP.DESCRIPTION AS CARDPRODUCTDES,"
                        + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,ST.DESCRIPTION AS CARDSTATUSDES ,CD.CREDITLIMIT,CD.CASHLIMIT,CD.OTBCREDIT,"
                        + "CD.OTBCASH,CD.EMBOSSSTATUS, CD.NOGENARATEDUSER ,ST2.DESCRIPTION AS EMBOSSSTATUSDES,"
                        + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
                        + "FROM CARD CD,STATUS ST,STATUS ST2,CARDTYPE CT,CARDPRODUCT CP";

            } else if (searchBean.getEmbossType().equals("reissue") || searchBean.getEmbossType().equals("replace")) {

                strSqlBasic = "SELECT CD.CARDNUMBER,CD.MAINCARDNO,CD.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,CD.CARDPRODUCT,CP.DESCRIPTION AS CARDPRODUCTDES,"
                        + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,ST.DESCRIPTION AS CARDSTATUSDES ,CD.CREDITLIMIT,CD.CASHLIMIT,CD.OTBCREDIT,"
                        + "CD.OTBCASH,CD.EMBOSSSTATUS, CD.NOGENARATEDUSER ,ST2.DESCRIPTION AS EMBOSSSTATUSDES,"
                        + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
                        + "FROM CARD CD,STATUS ST,STATUS ST2,CARDTYPE CT,CARDPRODUCT CP,CARDREQUEST CR ";

            } else if (searchBean.getEmbossType().equals("renew")) {

                strSqlBasic = "SELECT CD.CARDNUMBER,CD.MAINCARDNO,CD.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,CD.CARDPRODUCT,CP.DESCRIPTION AS CARDPRODUCTDES,"
                        + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,ST.DESCRIPTION AS CARDSTATUSDES ,CD.CREDITLIMIT,CD.CASHLIMIT,CD.OTBCREDIT,"
                        + "CD.OTBCASH,CD.EMBOSSSTATUS, CD.NOGENARATEDUSER ,ST2.DESCRIPTION AS EMBOSSSTATUSDES,"
                        + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
                        + "FROM CARD CD,STATUS ST,STATUS ST2,CARDTYPE CT,CARDPRODUCT CP,CARDRENEW CRN ";

            } else if (searchBean.getEmbossType().equals("qafail")) {

                strSqlBasic = "SELECT CD.CARDNUMBER,CD.MAINCARDNO,CD.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,CD.CARDPRODUCT,CP.DESCRIPTION AS CARDPRODUCTDES,"
                        + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,ST.DESCRIPTION AS CARDSTATUSDES ,CD.CREDITLIMIT,CD.CASHLIMIT,CD.OTBCREDIT,"
                        + "CD.OTBCASH,CD.EMBOSSSTATUS, CD.NOGENARATEDUSER ,ST2.DESCRIPTION AS EMBOSSSTATUSDES,"
                        + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
                        + "FROM CARD CD,STATUS ST,STATUS ST2,CARDTYPE CT,CARDPRODUCT CP,CARDQUALITYFAILLIST QF ";
            } else if (searchBean.getEmbossType().equals("all")) {

                strSqlBasic = "SELECT CD.CARDNUMBER,CD.MAINCARDNO,CD.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,CD.CARDPRODUCT,CP.DESCRIPTION AS CARDPRODUCTDES,"
                        + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,ST.DESCRIPTION AS CARDSTATUSDES ,CD.CREDITLIMIT,CD.CASHLIMIT,CD.OTBCREDIT,"
                        + "CD.OTBCASH,CD.EMBOSSSTATUS, CD.NOGENARATEDUSER ,ST2.DESCRIPTION AS EMBOSSSTATUSDES,"
                        + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
                        + "FROM CARD CD,STATUS ST,STATUS ST2,CARDTYPE CT,CARDPRODUCT CP ";
            }

            String condition = "";

            if (!searchBean.getCardNumber().contentEquals("") || searchBean.getCardNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CD.CARDNUMBER = '" + searchBean.getCardNumber() + "'";
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
            if (searchBean.getEmbossType().equals("ordinary")) {

                if (!condition.equals("")) {
                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
                            + "CD.CARDTYPE=? AND CD.CARDNUMBER not in (select cardnumber from CARDRENEW) AND " + condition;
                } else {
                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE  AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
                            + "CD.CARDNUMBER not in (select cardnumber from CARDRENEW) AND CD.CARDTYPE=? ";
                }
            } else if (searchBean.getEmbossType().equals("reissue")) {

                if (!condition.equals("")) {
                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE AND CD.CARDSTATUS ='" + StatusVarList.BLOCK_STATUS + "' AND "
                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
                            + "CD.CARDTYPE=? AND CD.CARDNUMBER=CR.CARDNUMBER AND CR.STATUS ='" + StatusVarList.YESSTATUS + "' AND "
                            + "CR.REQUESTREASONCODE ='" + StatusVarList.CARD_REISSUE + "' AND " + condition;
                } else {
                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE  AND CD.CARDSTATUS ='" + StatusVarList.BLOCK_STATUS + "' AND "
                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
                            + "CD.CARDTYPE=? AND CD.CARDNUMBER=CR.CARDNUMBER AND CR.STATUS ='" + StatusVarList.YESSTATUS + "' AND "
                            + "CR.REQUESTREASONCODE ='" + StatusVarList.CARD_REISSUE + "'";
                }
            } else if (searchBean.getEmbossType().equals("replace")) {

                if (!condition.equals("")) {
                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
                            + "CD.CARDTYPE=? AND CD.CARDNUMBER=CR.CARDNUMBER AND CR.STATUS ='" + StatusVarList.YESSTATUS + "' AND "
                            + "CR.REQUESTREASONCODE ='" + StatusVarList.CARD_REPLACE + "' AND " + condition;
                } else {
                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE  AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
                            + "CD.CARDTYPE=? AND CD.CARDNUMBER=CR.CARDNUMBER AND CR.STATUS ='" + StatusVarList.YESSTATUS + "' AND "
                            + "CR.REQUESTREASONCODE ='" + StatusVarList.CARD_REPLACE + "'";
                }
            } else if (searchBean.getEmbossType().equals("renew")) {

                if (!condition.equals("")) {
                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
                            + "CD.CARDTYPE=? AND CD.CARDNUMBER = CRN.CARDNUMBER AND "
                            + "(CRN.STATUS='" + StatusVarList.CARD_RENEW_PROCESS + "' OR CRN.STATUS='" + StatusVarList.CARD_RENEW_COMPLETE + "' ) "
                            + "AND " + condition;
                } else {
                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE  AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
                            + "CD.CARDTYPE=? AND CD.CARDNUMBER = CRN.CARDNUMBER AND "
                            + "(CRN.STATUS='" + StatusVarList.CARD_RENEW_PROCESS + "' OR CRN.STATUS='" + StatusVarList.CARD_RENEW_COMPLETE + "' )";
                }
            } else if (searchBean.getEmbossType().equals("qafail")) {

                if (!condition.equals("")) {
                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
                            + "CD.CARDTYPE=?  AND CD.CARDNUMBER=QF.CARDNUMBER AND " + condition;
                } else {
                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE  AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
                            + "CD.CARDTYPE=?  AND CD.CARDNUMBER=QF.CARDNUMBER ";
                }
            } else if (searchBean.getEmbossType().equals("all")) {

                if (!condition.equals("")) {
                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
                            + "CD.CARDTYPE=?  AND " + condition;
                } else {
                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE  AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
                            + "CD.CARDTYPE=? ";
                }
            }

            getAllVisaCard = con.prepareStatement(strSqlBasic);

            getAllVisaCard.setString(1, "CREDIT");
//            getAllVisaCard.setString(2, "VISA");
            getAllVisaCard.setString(2, searchBean.getCardtype());
            
            rs = getAllVisaCard.executeQuery();
            System.out.println(strSqlBasic);
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
                resultBean.setCashLimit(rs.getString("CASHLIMIT"));
                resultBean.setAvailableBal(rs.getString("OTBCREDIT"));
                resultBean.setCashAvailableBal(rs.getString("OTBCASH"));
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

                cardEmbossingVISAList.add(resultBean);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            rs.close();
            getAllVisaCard.close();

        }

        return cardEmbossingVISAList;
    }

    public List<BulkCardEmbossingDetailsBean> getAllBulkCardrequestToEmboss(Connection con, CardEmbossingSearchBean searchBean) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllVisaCard = null;
        String strSqlBasic = null;
        try {

            strSqlBasic = "select br.batchid, br.carddomain, cd.description as carddomaindes,br.cardproductcode, "
                    + "cp.description as cardproductdes,br.cardtype, ct.description as cardtypedes, br.currencycode,br.REQUESTEDUSER,  "
                    + "br.prioritylevel,br.status,br.productionmode,br.branchcode, bk.descrption as branchcodedes,st.description as statusdes "
                    + "from BULKCARDREQUEST br,STATUS ST,CARDTYPE CT,CARDPRODUCT CP, bankbranch bk, carddomain cd ";

            String condition = "";

            if (!searchBean.getBatchId().contentEquals("") || searchBean.getBatchId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.BATCHID = '" + searchBean.getBatchId() + "'";
            }

            if (!searchBean.getBranchId().contentEquals("") || searchBean.getBranchId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.branchcode = '" + searchBean.getBranchId() + "'";
            }

            if (!searchBean.getDomainId().contentEquals("") || searchBean.getDomainId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.carddomain = '" + searchBean.getDomainId() + "'";
            }
            if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.PRIORITYLEVEL = '" + searchBean.getPriorityLevel() + "'";
            }
            if (!searchBean.getCardtype().contentEquals("") || searchBean.getCardtype().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.CARDTYPE = '" + searchBean.getCardtype() + "'";
            }
            if (!searchBean.getCardProduct().contentEquals("") || searchBean.getCardProduct().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.CARDPRODUCT = '" + searchBean.getCardProduct() + "'";
            }

            if (!searchBean.getGeneratedUser().contentEquals("") || searchBean.getGeneratedUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "br.REQUESTEDUSER  = '" + searchBean.getGeneratedUser() + "'";
            }

            if (!searchBean.getStatus().contentEquals("") || searchBean.getStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.STATUS = '" + searchBean.getStatus() + "'";
            }

            if (!condition.equals("")) {
                strSqlBasic += "  where br.status = '" + StatusVarList.BULK_CARD_PIN_GENE_COMPLETE + "' and st.statuscode = br.status and CT.cardtypecode = br.cardtype and "
                        + "cp.productcode = br.cardproductcode and bk.bankcode =(SELECT BANKCODE from COMMONPARAMETER) and "
                        + "cd.domainid =br.carddomain and br.branchcode = bk.branchcode and " + condition + " ORDER BY br.batchid";
            } else {
                strSqlBasic += " where br.status = '" + StatusVarList.BULK_CARD_PIN_GENE_COMPLETE + "' and st.statuscode = br.status and CT.cardtypecode = br.cardtype and "
                        + "cp.productcode = br.cardproductcode and bk.bankcode =(SELECT BANKCODE from COMMONPARAMETER) and "
                        + "cd.domainid =br.carddomain and br.branchcode = bk.branchcode ORDER BY br.batchid";
            }

            getAllVisaCard = con.prepareStatement(strSqlBasic);

//            getAllVisaCard.setString(1, "VISA");
            System.out.println(getAllVisaCard);
            rs = getAllVisaCard.executeQuery();

            bulkCardEmbossingList = new ArrayList<BulkCardEmbossingDetailsBean>();

            while (rs.next()) {

                BulkCardEmbossingDetailsBean resultBean = new BulkCardEmbossingDetailsBean();

                resultBean.setBatchId(rs.getString("batchid"));
                resultBean.setBranchCode(rs.getString("branchcode"));
                resultBean.setBranchName(rs.getString("branchcodedes"));
                resultBean.setCardDomain(rs.getString("carddomain"));
                resultBean.setCardDomainDes(rs.getString("carddomaindes"));
                resultBean.setStatus(rs.getString("status"));
                resultBean.setStatusDes(rs.getString("statusdes"));
                resultBean.setCardtype(rs.getString("cardtype"));
                resultBean.setCardtypeDes(rs.getString("cardtypedes"));
                resultBean.setCardProduct(rs.getString("cardproductcode"));
                resultBean.setCardProductDes(rs.getString("cardproductdes"));
                resultBean.setPriorityLevel(rs.getString("prioritylevel"));

                bulkCardEmbossingList.add(resultBean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllVisaCard.close();

        }

        return bulkCardEmbossingList;
    }

//    public List<CardEmbossingCardDetailsBean> getAllMASTERCardToEmboss(Connection con, CardEmbossingSearchBean searchBean, String cardDomain) throws Exception {
//        ResultSet rs = null;
//        PreparedStatement getAllMasterCard = null;
//        String strSqlBasic = null;
//        try {
//
//
//
//            if (searchBean.getEmbossType().equals("ordinary")) {
//
//                strSqlBasic = "SELECT CD.CARDNUMBER,CD.ACCOUNTNO,CD.CUSTOMERID,CD.MAINCARDNO,CD.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,CD.CARDPRODUCT,CP.DESCRIPTION AS CARDPRODUCTDES,"
//                        + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,ST.DESCRIPTION AS CARDSTATUSDES,CD.CREDITLIMIT,CD.CASHLIMIT,CD.OTBCREDIT,"
//                        + "CD.OTBCASH,CD.EMBOSSSTATUS,CD.NOGENARATEDUSER, ST2.DESCRIPTION AS EMBOSSSTATUSDES,"
//                        + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
//                        + "FROM CARD CD,STATUS ST,STATUS ST2,CARDTYPE CT ,CARDPRODUCT CP";
//
//            } else if (searchBean.getEmbossType().equals("reissue") || searchBean.getEmbossType().equals("replace")) {
//
//                strSqlBasic = "SELECT CD.CARDNUMBER,CD.ACCOUNTNO,CD.CUSTOMERID,CD.MAINCARDNO,CD.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,CD.CARDPRODUCT,CP.DESCRIPTION AS CARDPRODUCTDES,"
//                        + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,ST.DESCRIPTION AS CARDSTATUSDES ,CD.CREDITLIMIT,CD.CASHLIMIT,CD.OTBCREDIT,"
//                        + "CD.OTBCASH,CD.EMBOSSSTATUS, CD.NOGENARATEDUSER ,ST2.DESCRIPTION AS EMBOSSSTATUSDES,"
//                        + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
//                        + "FROM CARD CD,STATUS ST,STATUS ST2,CARDTYPE CT,CARDPRODUCT CP,CARDREQUEST CR ";
//
//            } else if (searchBean.getEmbossType().equals("renew")) {
//
//                strSqlBasic = "SELECT CD.CARDNUMBER,CD.ACCOUNTNO,CD.CUSTOMERID,CD.MAINCARDNO,CD.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,CD.CARDPRODUCT,CP.DESCRIPTION AS CARDPRODUCTDES,"
//                        + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,ST.DESCRIPTION AS CARDSTATUSDES ,CD.CREDITLIMIT,CD.CASHLIMIT,CD.OTBCREDIT,"
//                        + "CD.OTBCASH,CD.EMBOSSSTATUS, CD.NOGENARATEDUSER ,ST2.DESCRIPTION AS EMBOSSSTATUSDES,"
//                        + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
//                        + "FROM CARD CD,STATUS ST,STATUS ST2,CARDTYPE CT,CARDPRODUCT CP,CARDRENEW CRN ";
//
//            } else if (searchBean.getEmbossType().equals("qafail")) {
//
//                strSqlBasic = "SELECT CD.CARDNUMBER,CD.ACCOUNTNO,CD.CUSTOMERID,CD.MAINCARDNO,CD.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,CD.CARDPRODUCT,CP.DESCRIPTION AS CARDPRODUCTDES,"
//                        + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,ST.DESCRIPTION AS CARDSTATUSDES,CD.CREDITLIMIT,CD.CASHLIMIT,CD.OTBCREDIT,"
//                        + "CD.OTBCASH,CD.EMBOSSSTATUS,CD.NOGENARATEDUSER, ST2.DESCRIPTION AS EMBOSSSTATUSDES,"
//                        + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
//                        + "FROM CARD CD,STATUS ST,STATUS ST2,CARDTYPE CT ,CARDPRODUCT CP,CARDQUALITYFAILLIST QF";
//
//            }
//
//            String condition = "";
//
//
//            if (!searchBean.getCardNumber().contentEquals("") || searchBean.getCardNumber().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "CD.CARDNUMBER = '" + searchBean.getCardNumber() + "'";
//            }
//
//            if (!searchBean.getIdentityType().contentEquals("") || searchBean.getIdentityType().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "CD.IDTYPE = '" + searchBean.getIdentityType() + "'";
//            }
//
//
//            if (!searchBean.getIdentityNo().contentEquals("") || searchBean.getIdentityNo().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "CD.IDNUMBER = '" + searchBean.getIdentityNo() + "'";
//            }
//            if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "CD.PRIORITYLEVEL = '" + searchBean.getPriorityLevel() + "'";
//            }
//            if (!searchBean.getCardtype().contentEquals("") || searchBean.getCardtype().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "CD.CARDTYPE = '" + searchBean.getCardtype() + "'";
//            }
//            if (!searchBean.getCardProduct().contentEquals("") || searchBean.getCardProduct().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "CD.CARDPRODUCT = '" + searchBean.getCardProduct() + "'";
//            }
//
//            if (!searchBean.getGeneratedUser().contentEquals("") || searchBean.getGeneratedUser().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "CD.NOGENARATEDUSER = '" + searchBean.getGeneratedUser() + "'";
//            }
//
//            if (!searchBean.getStatus().contentEquals("") || searchBean.getStatus().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "CD.CARDSTATUS = '" + searchBean.getStatus() + "'";
//            }
////            if (!searchBean.getEmbossType().contentEquals("") || searchBean.getEmbossType().length() > 0) {
////                if (!condition.equals("")) {
////                    condition += " AND ";
////                }
////                condition += "a.ASSIGNUSER = '" + searchBean.getEmbossType() + "'";
////            }
//
//
//
//
//
//            if (searchBean.getEmbossType().equals("ordinary")) {
//
//                if (!condition.equals("")) {
//                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
//                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
//                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
//                            + "CD.CARDTYPE=? AND CD.CARDNUMBER not in (select cardnumber from CARDRENEW) AND " + condition + " ORDER BY CD.CUSTOMERID";
//                } else {
//                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE  AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
//                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
//                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
//                            + "CD.CARDNUMBER not in (select cardnumber from CARDRENEW) AND CD.CARDTYPE=? ORDER BY CD.CUSTOMERID";
//                }
//            } else if (searchBean.getEmbossType().equals("reissue")) {
//
//                if (!condition.equals("")) {
//                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE AND CD.CARDSTATUS ='" + StatusVarList.BLOCK_STATUS + "' AND "
//                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
//                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
//                            + "CD.CARDTYPE=? AND CD.CARDNUMBER=CR.CARDNUMBER AND CR.STATUS ='" + StatusVarList.YESSTATUS + "' AND "
//                            + "CR.REQUESTREASONCODE ='" + StatusVarList.CARD_REISSUE + "' AND " + condition + " ORDER BY CD.CUSTOMERID";
//                } else {
//                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE  AND CD.CARDSTATUS ='" + StatusVarList.BLOCK_STATUS + "' AND "
//                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
//                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
//                            + "CD.CARDTYPE=? AND CD.CARDNUMBER=CR.CARDNUMBER AND CR.STATUS ='" + StatusVarList.YESSTATUS + "' AND "
//                            + "CR.REQUESTREASONCODE ='" + StatusVarList.CARD_REISSUE + "' ORDER BY CD.CUSTOMERID";
//                }
//            } else if (searchBean.getEmbossType().equals("replace")) {
//
//                if (!condition.equals("")) {
//                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
//                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
//                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
//                            + "CD.CARDTYPE=? AND CD.CARDNUMBER=CR.CARDNUMBER AND CR.STATUS ='" + StatusVarList.YESSTATUS + "' AND "
//                            + "CR.REQUESTREASONCODE ='" + StatusVarList.CARD_REPLACE + "' AND " + condition + " ORDER BY CD.CUSTOMERID";
//                } else {
//                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE  AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
//                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
//                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
//                            + "CD.CARDTYPE=? AND CD.CARDNUMBER=CR.CARDNUMBER AND CR.STATUS ='" + StatusVarList.YESSTATUS + "' AND "
//                            + "CR.REQUESTREASONCODE ='" + StatusVarList.CARD_REPLACE + "' ORDER BY CD.CUSTOMERID";
//                }
//            } else if (searchBean.getEmbossType().equals("renew")) {
//
//                if (!condition.equals("")) {
//                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
//                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
//                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
//                            + "CD.CARDTYPE=? AND CD.CARDNUMBER = CRN.CARDNUMBER AND "
//                            + "(CRN.STATUS='" + StatusVarList.CARD_RENEW_PROCESS + "' OR CRN.STATUS='" + StatusVarList.CARD_RENEW_COMPLETE + "' ) "
//                            + "AND " + condition + " ORDER BY CD.CUSTOMERID";
//                } else {
//                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE  AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
//                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
//                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
//                            + "CD.CARDTYPE=? AND CD.CARDNUMBER = CRN.CARDNUMBER AND "
//                            + "(CRN.STATUS='" + StatusVarList.CARD_RENEW_PROCESS + "' OR CRN.STATUS='" + StatusVarList.CARD_RENEW_COMPLETE + "' ) ORDER BY CD.CUSTOMERID";
//                }
//            } else if (searchBean.getEmbossType().equals("qafail")) {
//
//                if (!condition.equals("")) {
//                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
//                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
//                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
//                            + "CD.CARDTYPE=?  AND CD.CARDNUMBER=QF.CARDNUMBER AND " + condition + " ORDER BY CD.CUSTOMERID";
//                } else {
//                    strSqlBasic += " WHERE CD.CARDDOMAIN =? AND CD.CARDSTATUS = ST.STATUSCODE  AND CD.CARDSTATUS ='" + StatusVarList.INACTIVE_STATUS + "' AND "
//                            + "CD.EMBOSSSTATUS = ST2.STATUSCODE AND CD.CARDTYPE = CT.CARDTYPECODE AND CD.CARDPRODUCT = CP.PRODUCTCODE AND "
//                            + "CD.EMBOSSSTATUS='" + StatusVarList.NOSTATUS + "' AND CD.PINSTATUS = '" + StatusVarList.YESSTATUS + "' AND "
//                            + "CD.CARDTYPE=?  AND CD.CARDNUMBER=QF.CARDNUMBER ORDER BY CD.CUSTOMERID";
//                }
//            }
//
//
//            getAllMasterCard = con.prepareStatement(strSqlBasic);
//
//
//            getAllMasterCard.setString(1, cardDomain);
//            getAllMasterCard.setString(2, "MAST");
//            rs = getAllMasterCard.executeQuery();
//            cardEmbossingMASTERList = new ArrayList<CardEmbossingCardDetailsBean>();
//
//            while (rs.next()) {
//
//                CardEmbossingCardDetailsBean resultBean = new CardEmbossingCardDetailsBean();
//
//                resultBean.setCardNumber(rs.getString("CARDNUMBER"));
//                resultBean.setAccNo(rs.getString("ACCOUNTNO"));
//                resultBean.setMainCardNo(rs.getString("MAINCARDNO"));
//                resultBean.setCustomerId(rs.getString("CUSTOMERID"));
//                resultBean.setCardtype(rs.getString("CARDTYPE"));
//                resultBean.setCardtypeDes(rs.getString("CARDTYPEDES"));
//                resultBean.setCardProduct(rs.getString("CARDPRODUCT"));
//                resultBean.setCardProductDes(rs.getString("CARDPRODUCTDES"));
//                resultBean.setExpiryDate(rs.getString("EXPIERYDATE"));
//                resultBean.setActivationCode(rs.getString("ACTIVATIONDATE"));
//
//                resultBean.setNameOnCard(rs.getString("NAMEONCARD"));
//                resultBean.setCardStatus(rs.getString("CARDSTATUS"));
//                resultBean.setCardStatusDes(rs.getString("CARDSTATUSDES"));
//                resultBean.setCreditLimit(rs.getString("CREDITLIMIT"));
//                resultBean.setCashLimit(rs.getString("CASHLIMIT"));
//                resultBean.setAvailableBal(rs.getString("OTBCREDIT"));
//                resultBean.setCashAvailableBal(rs.getString("OTBCASH"));
//                resultBean.setEmbossStatus(rs.getString("EMBOSSSTATUS"));
//                resultBean.setEmbossStatusDes(rs.getString("EMBOSSSTATUSDES"));
//                resultBean.setPinStatus(rs.getString("PINSTATUS"));
//                resultBean.setLoyaltyPoint(rs.getString("LOYALTYPOINT"));
//                resultBean.setOffSet(rs.getString("OFFSET"));
//                resultBean.setRedeemPoint(rs.getString("REDEEMPOINT"));
//                resultBean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
//                resultBean.setIdType(rs.getString("IDTYPE"));
//                resultBean.setIdNumber(rs.getString("IDNUMBER"));
//                resultBean.setIssuedDate(rs.getString("ISSUEDDATE"));
//
//                cardEmbossingMASTERList.add(resultBean);
//            }
//
//        } catch (Exception ex) {
//            throw ex;
//        } finally {
//            rs.close();
//            getAllMasterCard.close();
//
//        }
//
//        return cardEmbossingMASTERList;
//    }
    public List<CardEmbossingCardDetailsBean> getAllVISACardProductToEmboss(Connection con, String cardNumbers, String cardProduct, String cardType) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllVisaCard = null;
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT CD.CARDNUMBER,CD.MAINCARDNO,CD.CARDTYPE,CD.CARDPRODUCT,"
                    + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,CD.CREDITLIMIT,CD.CASHLIMIT,CD.OTBCREDIT,"
                    + "CD.OTBCASH,CD.EMBOSSSTATUS,"
                    + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
                    + "FROM CARD CD WHERE CD.CARDPRODUCT =? AND CD.CARDTYPE=? AND CD.CARDNUMBER IN (" + cardNumbers + ") ";

            getAllVisaCard = con.prepareStatement(strSqlBasic);

            getAllVisaCard.setString(1, cardProduct);
            getAllVisaCard.setString(2, cardType);
            System.out.println(getAllVisaCard);
            rs = getAllVisaCard.executeQuery();
            cardEmbossingVISAList = new ArrayList<CardEmbossingCardDetailsBean>();

            while (rs.next()) {

                CardEmbossingCardDetailsBean resultBean = new CardEmbossingCardDetailsBean();

                resultBean.setCardNumber(rs.getString("CARDNUMBER"));
//                resultBean.setAccNo(rs.getString("ACCOUNTNO"));
                resultBean.setMainCardNo(rs.getString("MAINCARDNO"));
//                resultBean.setCustomerId(rs.getString("CUSTOMERID"));
                resultBean.setCardtype(rs.getString("CARDTYPE"));
                resultBean.setCardProduct(rs.getString("CARDPRODUCT"));
                resultBean.setExpiryDate(rs.getString("EXPIERYDATE"));
                resultBean.setActivationCode(rs.getString("ACTIVATIONDATE"));

                resultBean.setNameOnCard(rs.getString("NAMEONCARD"));
                resultBean.setCardStatus(rs.getString("CARDSTATUS"));
                resultBean.setCreditLimit(rs.getString("CREDITLIMIT"));
                resultBean.setCashLimit(rs.getString("CASHLIMIT"));
                resultBean.setAvailableBal(rs.getString("OTBCREDIT"));
                resultBean.setCashAvailableBal(rs.getString("OTBCASH"));
                resultBean.setEmbossStatus(rs.getString("EMBOSSSTATUS"));
                resultBean.setPinStatus(rs.getString("PINSTATUS"));
                resultBean.setLoyaltyPoint(rs.getString("LOYALTYPOINT"));
                resultBean.setOffSet(rs.getString("OFFSET"));
                resultBean.setRedeemPoint(rs.getString("REDEEMPOINT"));
                resultBean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                resultBean.setIdType(rs.getString("IDTYPE"));
                resultBean.setIdNumber(rs.getString("IDNUMBER"));
                resultBean.setIssuedDate(rs.getString("ISSUEDDATE"));

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

    public CardEmbossingCardDetailsBean getVISACardProductToEmbossBean(Connection con, String cardNumber) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllVisaCard = null;
        String strSqlBasic = null;
        CardEmbossingCardDetailsBean cardEmbossingVISABean = new CardEmbossingCardDetailsBean();

        try {

            strSqlBasic = "SELECT CD.CARDNUMBER,CD.MAINCARDNO,CD.CARDTYPE,CD.CARDPRODUCT,"
                    + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,CD.CREDITLIMIT,CD.CASHLIMIT,CD.OTBCREDIT,"
                    + "CD.OTBCASH,CD.EMBOSSSTATUS,"
                    + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
                    + "FROM CARD CD WHERE CD.CARDNUMBER=? ";

            getAllVisaCard = con.prepareStatement(strSqlBasic);

            getAllVisaCard.setString(1, cardNumber);
            rs = getAllVisaCard.executeQuery();

            while (rs.next()) {

                CardEmbossingCardDetailsBean resultBean = new CardEmbossingCardDetailsBean();

                resultBean.setCardNumber(rs.getString("CARDNUMBER"));
//                resultBean.setAccNo(rs.getString("ACCOUNTNO"));
                resultBean.setMainCardNo(rs.getString("MAINCARDNO"));
//                resultBean.setCustomerId(rs.getString("CUSTOMERID"));
                resultBean.setCardtype(rs.getString("CARDTYPE"));
                resultBean.setCardProduct(rs.getString("CARDPRODUCT"));
                resultBean.setExpiryDate(rs.getString("EXPIERYDATE"));
                resultBean.setActivationCode(rs.getString("ACTIVATIONDATE"));

                resultBean.setNameOnCard(rs.getString("NAMEONCARD"));
                resultBean.setCardStatus(rs.getString("CARDSTATUS"));
                resultBean.setCreditLimit(rs.getString("CREDITLIMIT"));
                resultBean.setCashLimit(rs.getString("CASHLIMIT"));
                resultBean.setAvailableBal(rs.getString("OTBCREDIT"));
                resultBean.setCashAvailableBal(rs.getString("OTBCASH"));
                resultBean.setEmbossStatus(rs.getString("EMBOSSSTATUS"));
                resultBean.setPinStatus(rs.getString("PINSTATUS"));
                resultBean.setLoyaltyPoint(rs.getString("LOYALTYPOINT"));
                resultBean.setOffSet(rs.getString("OFFSET"));
                resultBean.setRedeemPoint(rs.getString("REDEEMPOINT"));
                resultBean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                resultBean.setIdType(rs.getString("IDTYPE"));
                resultBean.setIdNumber(rs.getString("IDNUMBER"));
                resultBean.setIssuedDate(rs.getString("ISSUEDDATE"));

                cardEmbossingVISABean = resultBean;
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllVisaCard.close();

        }

        return cardEmbossingVISABean;
    }

    public String getVisaEmbossingFormat(Connection con, String cardType) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllCardType = null;
        try {

            getAllCardType = con.prepareStatement("SELECT TC.RECORDFORMAT FROM EMBOSSFILEFORMAT TC WHERE TC.CARDTYPE =?");

            getAllCardType.setString(1, cardType);
            rs = getAllCardType.executeQuery();

            while (rs.next()) {

                embossFileFormat = rs.getString("RECORDFORMAT");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllCardType.close();

        }

        return embossFileFormat;
    }

    public List<EmbossFileFormatDetailBean> getEmbossFields(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllCardType = null;

        try {

            getAllCardType = con.prepareStatement("SELECT TC.FIELDNAME,TC.CARDFIELD,TC.FIELDMAXLENGTH,TC.PADDINGSTATUS,"
                    + "TC.PASDDINGCHAR,TC.PADDINGDIRECTION,TC.FIXEDVALUE,TC.FIXEDDIRECTION FROM EMBOSSFIELDDETAIL TC");

            rs = getAllCardType.executeQuery();

            fileFormatDetailBean = new ArrayList<EmbossFileFormatDetailBean>();
            while (rs.next()) {

                EmbossFileFormatDetailBean resultBean = new EmbossFileFormatDetailBean();

                resultBean.setFieldName(rs.getString("FIELDNAME"));
                resultBean.setFieldMaxLength(rs.getString("FIELDMAXLENGTH"));
                resultBean.setFixedDirection(rs.getString("FIXEDDIRECTION"));
                resultBean.setFixedValue(rs.getString("FIXEDVALUE"));
                resultBean.setPaddingChar(rs.getString("PASDDINGCHAR"));
                resultBean.setPaddingDirection(rs.getString("PADDINGDIRECTION"));
                resultBean.setPaddingStatus(rs.getString("PADDINGSTATUS"));

                fileFormatDetailBean.add(resultBean);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllCardType.close();

        }

        return fileFormatDetailBean;
    }

    public List<CardEmbossingCardDetailsBean> getAllBulkCardDetails(Connection con, String batchId) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllCardType = null;

        try {

//            getAllCardType = con.prepareStatement("SELECT CD.CARDNUMBER,CD.ACCOUNTNO,CD.CUSTOMERID,CD.MAINCARDNO,CD.CARDTYPE,CD.CARDPRODUCT,"
//                    + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,CD.CREDITLIMIT,CD.CASHLIMIT,CD.OTBCREDIT,"
//                    + "CD.OTBCASH,CD.EMBOSSSTATUS,"
//                    + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
//                    + "FROM CARD CD WHERE CD.BATCHID=? ORDER BY CD.CUSTOMERID");
            getAllCardType = con.prepareStatement("SELECT CD.CARDNUMBER,CD.MAINCARDNO,CD.CARDTYPE,CD.CARDPRODUCT,"
                    + "CD.EXPIERYDATE,CD.ACTIVATIONDATE,CD.NAMEONCARD,CD.CARDSTATUS,CD.CREDITLIMIT,CD.CASHLIMIT,CD.OTBCREDIT,"
                    + "CD.OTBCASH,CD.EMBOSSSTATUS,"
                    + "CD.PINSTATUS,CD.LOYALTYPOINT,CD.OFFSET,CD.REDEEMPOINT,CD.IDTYPE,CD.IDNUMBER,CD.PRIORITYLEVEL,CD.ISSUEDDATE "
                    + "FROM CARD CD WHERE CD.EMBOSSSTATUS = '" + StatusVarList.NOSTATUS + "' AND CD.BATCHID=?");

            getAllCardType.setString(1, batchId);

            rs = getAllCardType.executeQuery();

            bulkCardDetailsBeanList = new ArrayList<CardEmbossingCardDetailsBean>();

            while (rs.next()) {

                CardEmbossingCardDetailsBean resultBean = new CardEmbossingCardDetailsBean();

                resultBean.setCardNumber(rs.getString("CARDNUMBER"));
//                resultBean.setAccNo(rs.getString("ACCOUNTNO"));
                resultBean.setMainCardNo(rs.getString("MAINCARDNO"));
//                resultBean.setCustomerId(rs.getString("CUSTOMERID"));
                resultBean.setCardtype(rs.getString("CARDTYPE"));
                resultBean.setCardProduct(rs.getString("CARDPRODUCT"));
                resultBean.setExpiryDate(rs.getString("EXPIERYDATE"));
                resultBean.setActivationCode(rs.getString("ACTIVATIONDATE"));

                resultBean.setNameOnCard(rs.getString("NAMEONCARD"));
                resultBean.setCardStatus(rs.getString("CARDSTATUS"));
                resultBean.setCreditLimit(rs.getString("CREDITLIMIT"));
                resultBean.setCashLimit(rs.getString("CASHLIMIT"));
                resultBean.setAvailableBal(rs.getString("OTBCREDIT"));
                resultBean.setCashAvailableBal(rs.getString("OTBCASH"));
                resultBean.setEmbossStatus(rs.getString("EMBOSSSTATUS"));
                resultBean.setPinStatus(rs.getString("PINSTATUS"));
                resultBean.setLoyaltyPoint(rs.getString("LOYALTYPOINT"));
                resultBean.setOffSet(rs.getString("OFFSET"));
                resultBean.setRedeemPoint(rs.getString("REDEEMPOINT"));
                resultBean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                resultBean.setIdType(rs.getString("IDTYPE"));
                resultBean.setIdNumber(rs.getString("IDNUMBER"));
                resultBean.setIssuedDate(rs.getString("ISSUEDDATE"));

                bulkCardDetailsBeanList.add(resultBean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllCardType.close();

        }

        return bulkCardDetailsBeanList;
    }

    public EmbossFileFormatDetailBean getEmbossFieldsBean(Connection con, String FieldName) throws Exception {
        ResultSet rs = null;
        PreparedStatement getEmbossBean = null;

        try {

            getEmbossBean = con.prepareStatement("SELECT TC.FIELDNAME,TC.CARDFIELD,TC.TABLENAME,TC.FIELDMAXLENGTH,TC.PADDINGSTATUS,"
                    + "TC.PASDDINGCHAR,TC.PADDINGDIRECTION,TC.FIXEDVALUE,TC.FIXEDDIRECTION FROM EMBOSSFIELDDETAIL TC WHERE TC.FIELDNAME =?");

            getEmbossBean.setString(1, FieldName);
            rs = getEmbossBean.executeQuery();

            embossDetailBean = new EmbossFileFormatDetailBean();

            while (rs.next()) {

                embossDetailBean.setFieldName(rs.getString("FIELDNAME"));
                embossDetailBean.setMatchedField(rs.getString("CARDFIELD"));
                embossDetailBean.setMatchedTable(rs.getString("TABLENAME"));
                embossDetailBean.setFieldMaxLength(rs.getString("FIELDMAXLENGTH"));
                embossDetailBean.setFixedDirection(rs.getString("FIXEDDIRECTION"));
                embossDetailBean.setFixedValue(rs.getString("FIXEDVALUE"));
                embossDetailBean.setPaddingChar(rs.getString("PASDDINGCHAR"));
                embossDetailBean.setPaddingDirection(rs.getString("PADDINGDIRECTION"));
                embossDetailBean.setPaddingStatus(rs.getString("PADDINGSTATUS"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getEmbossBean.close();

        }

        return embossDetailBean;
    }

    public String getTableFieldValue(Connection con, String tableName, String FieldName, String cardNumber) throws Exception {

        ResultSet rs = null;
        PreparedStatement getValue = null;
        String tableFieldValue = null;
        String selectedvalue = "selectedvalue";

        try {

            getValue = con.prepareStatement("SELECT " + FieldName + " as " + selectedvalue + " FROM " + tableName + " WHERE cardNumber=" + cardNumber);

            rs = getValue.executeQuery();

            while (rs.next()) {

                if (rs.getString(selectedvalue) != null) {

                    tableFieldValue = rs.getString(selectedvalue);

                } else {

                    tableFieldValue = "";
                }

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getValue.close();

        }

        return tableFieldValue;
    }

    public String getDynamicFieldValue(Connection con, String column) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllCardType = null;
        String columnValue = null;
        try {

            getAllCardType = con.prepareStatement("SELECT " + column + "AS DYNAMIVALUE FROM CARD ");

            rs = getAllCardType.executeQuery();

            while (rs.next()) {

                columnValue = rs.getString("DYNAMIVALUE");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllCardType.close();

        }

        return columnValue;
    }

    public boolean updateCardEmbossStatus(Connection con, String fileName, String cardNumbers) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARD SET EMBOSSSTATUS=?,FILEID=? WHERE CARDNUMBER IN (" + cardNumbers + ")");

            updateStat.setString(1, StatusVarList.YESSTATUS);
            updateStat.setString(2, fileName);

//            updateStat.setString(2, cardNumbers);
            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean updateBulkCardRequestStatus(Connection con, String batchId) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE BULKCARDREQUEST SET STATUS=? WHERE BATCHID=?");

            updateStat.setString(1, StatusVarList.BULK_CARD_ENCODING_COMPLETE);
            updateStat.setString(2, batchId);

//            updateStat.setString(2, cardNumbers);
            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean insertCardEmbossFileDetails(Connection con, String fileName, String generateUser, String cardType, String cardProduct, Calendar currentDate) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO DOWNLOADFILE (FIETYPE,FILENAME,STATUS,GENERATEDUSER,LASTUPDATEDTIME,"
                    + "CREATEDTIME,LASTUPDATEDUSER,CARDTYPE,CARDPRODUCT,FILEID) VALUES (?,?,?,?,?,?,?,?,?,?)");

            insertStat.setString(1, "EMBOSS");
            insertStat.setString(2, fileName);
            insertStat.setString(3, StatusVarList.NOSTATUS);
            insertStat.setString(4, generateUser);
            insertStat.setTimestamp(5, new Timestamp(currentDate.getTime().getTime()));
            insertStat.setTimestamp(6, new Timestamp(currentDate.getTime().getTime()));
            insertStat.setString(7, generateUser);
            insertStat.setString(8, cardType);
            insertStat.setString(9, cardProduct);
            insertStat.setString(10, fileName);

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public List<CardEmbossingFileDownloadBean> getAllFilesToDownload(Connection con, String fileType, Calendar currentTime) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllFiles = null;
        try {

            getAllFiles = con.prepareStatement("SELECT DF.FIETYPE,DF.FILENAME,DF.STATUS,DF.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES, DF.CARDPRODUCT,"
                    + "CP.DESCRIPTION AS CARDPRODUCTDES FROM DOWNLOADFILE DF,CARDTYPE CT,CARDPRODUCT CP "
                    + "WHERE DF.FIETYPE=? AND DF.STATUS=? AND DF.CREATEDTIME= to_date('" + this.convertToStringDate(currentTime) + "', 'YYYY-mm-DD HH24:Mi:SS') AND DF.CARDTYPE = CT.CARDTYPECODE AND DF.CARDPRODUCT = CP.PRODUCTCODE");

            getAllFiles.setString(1, fileType);
            getAllFiles.setString(2, StatusVarList.NOSTATUS);

            rs = getAllFiles.executeQuery();
            cardEmbossingFileList = new ArrayList<CardEmbossingFileDownloadBean>();

            while (rs.next()) {

                CardEmbossingFileDownloadBean resultBean = new CardEmbossingFileDownloadBean();

                resultBean.setFileType(rs.getString("FIETYPE"));
                resultBean.setFileName(rs.getString("FILENAME"));
                resultBean.setCardType(rs.getString("CARDTYPEDES"));
                resultBean.setCardProduct(rs.getString("CARDPRODUCTDES"));

                cardEmbossingFileList.add(resultBean);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllFiles.close();

        }

        return cardEmbossingFileList;
    }

    public String convertToStringDate(Calendar currentTime) throws Exception {
        String dateNow = "";
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateNow = formatter.format(currentTime.getTime());
            return dateNow;

        } catch (Exception ee) {
            throw ee;
        }

    }

    public boolean updateCardEmbossFileDowloadStatus(Connection con, String fileName) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE DOWNLOADFILE SET STATUS=? WHERE FILENAME=?");

            updateStat.setString(1, StatusVarList.YESSTATUS);
            updateStat.setString(2, fileName);

            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public String getCardEmbossFileDowloadStatus(Connection con, String fileName) throws Exception {
        String success = "";
        PreparedStatement updateStat = null;
        ResultSet rs = null;
        try {
            updateStat = con.prepareStatement("SELECT DF.STATUS FROM DOWNLOADFILE DF WHERE DF.FILENAME=?");

            updateStat.setString(1, fileName);

            rs = updateStat.executeQuery();

            while (rs.next()) {

                success = rs.getString("STATUS");
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public CommonFilePathBean getFilePaths(Connection con, String osType) throws Exception {

        PreparedStatement updateStat = null;
        ResultSet rs = null;
        try {
            updateStat = con.prepareStatement("SELECT CF.SCANNEDAPPLICATION,CF.SCANNEDDOCUMENT,CF.EMBOSSFILE,"
                    + "CF.EODFILE,CF.STATEMENTFILE,CF.EMVFILE,CF.ENCODEFILE,CF.MODFILE,CF.LETTERS"
                    + " FROM COMMONFILEPATH CF WHERE CF.OSTYPE=?");

            updateStat.setString(1, osType);

            rs = updateStat.executeQuery();

            while (rs.next()) {

                commonFilePathBean = new CommonFilePathBean();

                commonFilePathBean.setScanApplication(rs.getString("SCANNEDAPPLICATION"));
                commonFilePathBean.setScandocument(rs.getString("SCANNEDDOCUMENT"));
                commonFilePathBean.setEmbossFile(rs.getString("EMBOSSFILE"));
                commonFilePathBean.setEodFile(rs.getString("EODFILE"));
                commonFilePathBean.setStatement(rs.getString("STATEMENTFILE"));
                commonFilePathBean.setEmvFile(rs.getString("EMVFILE"));
                commonFilePathBean.setEncodefile(rs.getString("ENCODEFILE"));
                commonFilePathBean.setModFile(rs.getString("MODFILE"));
                commonFilePathBean.setLetters(rs.getString("LETTERS"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return commonFilePathBean;
    }

    public DocumentUploadBean getFileName(Connection CMSCon, String applicationId, String categoryCode) throws Exception {
        uploadBean = new DocumentUploadBean();
        PreparedStatement getFileName = null;
        ResultSet rs = null;

        try {

            getFileName = CMSCon.prepareStatement("SELECT C.FILENAME,C.VERIFICATIONCATEGORY,C.DOCUMENTTYPE"
                    + " FROM CARDAPPLICATIONDOCUMENT C"
                    + " WHERE C.APPLICATIONID=? AND C.VERIFICATIONCATEGORY=?");

            getFileName.setString(1, applicationId);
            getFileName.setString(2, categoryCode);

            rs = getFileName.executeQuery();

            while (rs.next()) {

                uploadBean.setFileName(rs.getString("FILENAME"));
                uploadBean.setVerificationCategory(rs.getString("VERIFICATIONCATEGORY"));
                uploadBean.setDocumentType(rs.getString("DOCUMENTTYPE"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            getFileName.close();
        }

        return uploadBean;
    }
}
