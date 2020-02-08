/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardnumbergeneration.persistance;

import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardAccountBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardTempBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineAccountTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineCardTableBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardnumbergeneration.bean.BulkCardNumberFormatBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.system.util.logs.LogFileCreator;
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
public class BulkCardNumberGenerationPersistance {

    private ResultSet rs;
    private List<BulkCardRequestBean> cardList = null;
    private List<BulkCardRequestBean> batchList = null;
    private BulkCardRequestBean numberGenBean = null;
    private List<String> cardKeyList = null;
    String strSqlBasic = null;

    public List<BulkCardRequestBean> searchBulkCard(Connection CMSCon, BulkCardRequestBean numberGenBean) throws Exception {

        cardList = new ArrayList<BulkCardRequestBean>();
        PreparedStatement searchAllCard = null;
        try {

            strSqlBasic = "SELECT B.BATCHID,B.CARDDOMAIN, B.CARDTYPE,B.CARDPRODUCTCODE,"
                    + " B.BRANCHCODE,B.PRIORITYLEVEL,B.REQUESTEDUSER,D.DESCRIPTION AS DDESCRIPTION,"
                    + " T.DESCRIPTION AS TDESCRIPTION,P.DESCRIPTION AS PDESCRIPTION,"
                    + " BR.DESCRPTION AS BRDESCRPTION,PL.DESCRIPTION AS PLDESCRIPTION"
                    + " FROM BULKCARDREQUEST B, CARDDOMAIN D,CARDTYPE T ,CARDPRODUCT P,"
                    + " BANKBRANCH BR,PRIORITYLEVEL PL"
                    + " WHERE  D.DOMAINID=B.CARDDOMAIN AND T.CARDTYPECODE = B.CARDTYPE AND"
                    + " P.PRODUCTCODE=B.CARDPRODUCTCODE AND BR.BRANCHCODE =B.BRANCHCODE AND"
                    + " PL.PRIORITYLEVELCODE = B.PRIORITYLEVEL AND ( B.STATUS=? OR B.STATUS=? ) AND "
                    + " BR.BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER)  ";

            String condition = "";

            if (!numberGenBean.getBatchID().contentEquals("") || numberGenBean.getBatchID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " B.BATCHID LIKE '%" + numberGenBean.getBatchID() + "%'";
            }

            if (!numberGenBean.getCdDomain().contentEquals("") || numberGenBean.getCdDomain().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " B.CARDDOMAIN LIKE '%" + numberGenBean.getCdDomain() + "%'";
            }

            if (!numberGenBean.getCdType().contentEquals("") || numberGenBean.getCdType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " B.CARDTYPE  LIKE '%" + numberGenBean.getCdType() + "%'";
            }

            if (!numberGenBean.getCdProduct().contentEquals("") || numberGenBean.getCdProduct().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " B.CARDPRODUCTCODE LIKE '%" + numberGenBean.getCdProduct() + "%'";
            }

            if (!numberGenBean.getBranchCode().contentEquals("") || numberGenBean.getBranchCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " B.BRANCHCODE LIKE '%" + numberGenBean.getBranchCode() + "%'";
            }

            if (!numberGenBean.getPriorityLvl().contentEquals("") || numberGenBean.getPriorityLvl().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " B.PRIORITYLEVEL LIKE '%" + numberGenBean.getPriorityLvl() + "%'";
            }

            if (!numberGenBean.getReqUser().contentEquals("") || numberGenBean.getReqUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(B.REQUESTEDUSER) LIKE '%" + numberGenBean.getReqUser().toUpperCase() + "%'";
            }

            if (!condition.equals("")) {
                strSqlBasic += " AND " + condition + " ORDER BY B.BATCHID ";
            } else {

                strSqlBasic += " ORDER BY B.BATCHID";
            }

            searchAllCard = CMSCon.prepareStatement(strSqlBasic);
            searchAllCard.setString(1, StatusVarList.BULK_CARD_REQUEST_CONFIRM);
            searchAllCard.setString(2, StatusVarList.BULK_CARD_NUMBER_GENERATION_PENDING);
            rs = searchAllCard.executeQuery();

            while (rs.next()) {

                BulkCardRequestBean bean = new BulkCardRequestBean();

                bean.setBatchID(Integer.toString(rs.getInt("BATCHID")));
                bean.setCdDomain(rs.getString("CARDDOMAIN"));
                bean.setCdDomainDes(rs.getString("DDESCRIPTION"));
                bean.setCdType(rs.getString("CARDTYPE"));
                bean.setCdTypeDes(rs.getString("TDESCRIPTION"));
                bean.setCdProduct(rs.getString("CARDPRODUCTCODE"));
                bean.setCdProductDes(rs.getString("PDESCRIPTION"));
                bean.setBranchCode(rs.getString("BRANCHCODE"));
                bean.setBranchName(rs.getString("BRDESCRPTION"));
                bean.setPriorityLvl(rs.getString("PRIORITYLEVEL"));
                bean.setPriorityLvlDes(rs.getString("PLDESCRIPTION"));
                bean.setReqUser(rs.getString("REQUESTEDUSER"));

                cardList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            searchAllCard.close();

        }
        return cardList;
    }

    public BulkCardRequestBean getBulkCardBatchDetails(Connection CMSCon, String batchID) throws Exception {

        numberGenBean = new BulkCardRequestBean();
        PreparedStatement getBatchDetails = null;

        try {

            getBatchDetails = CMSCon.prepareStatement("SELECT B.CARDDOMAIN,B.CARDTYPE,B.CARDPRODUCTCODE,"
                    + " B.APPROVEDNOOFCARD,B.CURRENCYCODE,B.CARDBIN,B.PRODUCTIONMODE,B.TEMPLATECODE,B.CREDITLIMIT "
                    + " FROM BULKCARDREQUEST B WHERE B.BATCHID =?");

            getBatchDetails.setInt(1, Integer.parseInt(batchID));
            rs = getBatchDetails.executeQuery();

            while (rs.next()) {

                numberGenBean.setBatchID(batchID);
                numberGenBean.setCdDomain(rs.getString("CARDDOMAIN"));
                numberGenBean.setCdType(rs.getString("CARDTYPE"));
                numberGenBean.setCdProduct(rs.getString("CARDPRODUCTCODE"));
                numberGenBean.setApprvNumOfCds(Integer.toString(rs.getInt("APPROVEDNOOFCARD")));
                numberGenBean.setCurrency(rs.getString("CURRENCYCODE"));
                numberGenBean.setCdBin(rs.getString("CARDBIN"));
                numberGenBean.setCdProductionMode(rs.getString("PRODUCTIONMODE"));
                numberGenBean.setTemplateCode(rs.getString("TEMPLATECODE"));
                numberGenBean.setCreditLimit(Double.toString(rs.getDouble("CREDITLIMIT")));

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getBatchDetails.close();

        }
        return numberGenBean;
    }

    public int insertIntoCardTable(Connection con, CardBean cardbean) throws Exception {
        int resultId;

        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CARD "
                    + "(CARDNUMBER,CARDTYPE,CARDPRODUCT,EXPIERYDATE,"
                    + "CARDSTATUS,CREDITLIMIT,CASHLIMIT,OTBCREDIT, "
                    + "OTBCASH,EMBOSSSTATUS,ENCODESTATUS,PINSTATUS,LASTUPDATEDUSER,"
                    + "PRIORITYLEVEL,ISSUEDDATE,NOGENARATEDUSER,PINMAILSTATUS,"
                    + "LASTUPDATEDTIME,CREATEDTIME,CARDNUMBERPRINTEDONCARD,SERVICECODE,"
                    + "LOYALTYPOINT,REDEEMPOINT,CURRENCYNOCODE,RISKPROFILECODE,TEMPCREDITAMOUNT,"
                    + "REISSUETHRESHHOLSPERIOD,PRODUCTIONMODE,CARDDOMAIN,BATCHID,CARDKEYID,CARDCATEGORYCODE ) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

            insertStat.setString(1, cardbean.getCardNumber());
            //insertStat.setString(2, cardbean.getAccountNumber());
            insertStat.setString(2, cardbean.getCardType());
            insertStat.setString(3, cardbean.getCardProduct());
            insertStat.setString(4, cardbean.getExprioryDate());
            insertStat.setString(5, cardbean.getCardStatus());
            insertStat.setString(6, cardbean.getCreditLimit());
            insertStat.setString(7, cardbean.getCashLimit());
            insertStat.setString(8, cardbean.getAvailableBalence());
            insertStat.setString(9, cardbean.getCashAvailableBalence());
            insertStat.setString(10, cardbean.getEmbossStatus());
            insertStat.setString(11, cardbean.getEncodeStatus());
            insertStat.setString(12, cardbean.getPinStatus());
            insertStat.setString(13, cardbean.getLastUpdatedUser());
            insertStat.setString(14, cardbean.getPriorityLevel());
            insertStat.setString(15, cardbean.getIssueDate());
            insertStat.setString(16, cardbean.getNoGenaratedUser());
            insertStat.setString(17, cardbean.getPinmailStatus());
            insertStat.setString(18, cardbean.getCardNumberPrintedOnCard());
            insertStat.setString(19, cardbean.getServiceCode());
            insertStat.setString(20, cardbean.getLoyalityPoint());
            insertStat.setString(21, cardbean.getRedeemPoint());
            insertStat.setString(22, cardbean.getCurenncyCode());
            insertStat.setString(23, cardbean.getRiskProfile());
            insertStat.setString(24, cardbean.getTempotbAmount());


            insertStat.setString(25, cardbean.getReissueTresholPeriod());

            insertStat.setString(26, cardbean.getProductionMode());
            insertStat.setString(27, cardbean.getCardDomain());
            insertStat.setString(28, cardbean.getBatchID());
            insertStat.setInt(29, Integer.parseInt(cardbean.getCardKey()));
            insertStat.setString(30, "P");


            resultId = insertStat.executeUpdate();

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int insertIntoOnlineCardTable(Connection con, OnlineCardTableBean onlinebean) throws Exception {
        int resultId;

        PreparedStatement insertStatOnline = null;
        try {
            insertStatOnline = con.prepareStatement("INSERT INTO ECMS_ONLINE_CARD "
                    + " (CARDNUMBER,EXPDATE,EFFECTDATE,CURRENCYNOCODE,PINCOUNT,TXNCOUNT,"
                    + " CASHTXNCOUNT,TOTALTXNAMOUNT,TOTALCASHTXNAMOUNT,CREDITLIMIT,CASHLIMIT,"
                    + " OTBCREDIT,OTBCASH,TEMPCREDITAMOUNT,TEMPCASHAMOUNT,TEMPCREDITLIMITINCR,TEMPCASHLIMITINCR,"
                    + " TLISTATUS,BIN,EMVSTATUS,RISKPROFILECODE,STATUS,LASTUPDATEUSER,SERVICECODE,PINISSUEDEXPDATE,CARDKEYID )"
                    + " VALUES (?,?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            insertStatOnline.setString(1, onlinebean.getCardNumber());
            insertStatOnline.setString(2, onlinebean.getExpiryDate());//exp date
            insertStatOnline.setString(3, onlinebean.getCurrencyCode());//currency
            insertStatOnline.setInt(4, Integer.parseInt(onlinebean.getPinCount()));
            insertStatOnline.setInt(5, Integer.parseInt(onlinebean.getTxnCount()));
            insertStatOnline.setInt(6, Integer.parseInt(onlinebean.getCashTxnCount()));
            insertStatOnline.setDouble(7, Double.parseDouble(onlinebean.getTotalTxnAmount()));
            insertStatOnline.setDouble(8, Double.parseDouble(onlinebean.getTotalCashTxnAmount()));
            insertStatOnline.setDouble(9, Double.parseDouble(onlinebean.getCreditLimit()));
            insertStatOnline.setDouble(10, Double.parseDouble(onlinebean.getCashlimit()));
            insertStatOnline.setDouble(11, Double.parseDouble(onlinebean.getOtbCredit()));
            insertStatOnline.setDouble(12, Double.parseDouble(onlinebean.getOtbCash()));
            insertStatOnline.setDouble(13, Double.parseDouble(onlinebean.getTempCreditAmount()));
            insertStatOnline.setDouble(14, Double.parseDouble(onlinebean.getTempCashAmount()));
            insertStatOnline.setDouble(15, Double.parseDouble(onlinebean.getTempCreditLimitNcr()));
            insertStatOnline.setDouble(16, Double.parseDouble(onlinebean.getTempCashLimitNcr()));
            insertStatOnline.setInt(17, Integer.parseInt(onlinebean.getTliStatus()));
            insertStatOnline.setString(18, onlinebean.getCardBin());//cardtype
            insertStatOnline.setInt(19, Integer.parseInt(onlinebean.getEmvStatus()));
            insertStatOnline.setString(20, onlinebean.getRiskProfileCode());
            insertStatOnline.setInt(21, Integer.parseInt(onlinebean.getStatus()));
            insertStatOnline.setString(22, onlinebean.getLastUpdatedUser());
            insertStatOnline.setString(23, onlinebean.getServiceCode());
            insertStatOnline.setString(24, onlinebean.getExpiryDate());//pin issued exp date
            insertStatOnline.setInt(25, Integer.parseInt(onlinebean.getCardKey()));

            resultId = insertStatOnline.executeUpdate();

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStatOnline.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int insertIntoCardAccountTable(Connection con, CardAccountBean cardaccountbean) throws Exception {
        int resultId;

        PreparedStatement insertStatCA = null;
        try {
            insertStatCA = con.prepareStatement("INSERT INTO CARDACCOUNT "
                    + "(ACCOUNTNO,BILLINGID,CREDITLIMIT,STATUS,LASTUPDATEDUSER,CREATEDTIME,"
                    + " LASTUPDATEDTIME,RISKPROFILECODE )"
                    + " VALUES (?,?,?,?,?,SYSDATE,SYSDATE,?)");

            insertStatCA.setString(1, cardaccountbean.getAccountNumber());
            insertStatCA.setString(2, cardaccountbean.getBillingId());
            insertStatCA.setString(3, cardaccountbean.getCreditLimit());
            insertStatCA.setString(4, cardaccountbean.getStatus());
            insertStatCA.setString(5, cardaccountbean.getLastupdatedUser());
            insertStatCA.setString(6, cardaccountbean.getRiskProfileCode());




            resultId = insertStatCA.executeUpdate();

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStatCA.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int insertIntoOnlineAccountTable(Connection con, OnlineAccountTableBean onlinebean) throws Exception {
        int resultId;

        PreparedStatement insertStatCAOnline = null;
        try {
            insertStatCAOnline = con.prepareStatement("INSERT INTO ECMS_ONLINE_ACCOUNT "
                    + " (ACCOUNTNUMBER,RISKPROFILECODE,CURRENCYNOCODE,TXNCOUNT,CASHTXNCOUNT,TOTALTXNAMOUNT,"
                    + " TOTALCASHTXNAMOUNT,CREDITLIMIT,CASHLIMIT,OTBCREDIT,OTBCASH,STATUS )"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

            insertStatCAOnline.setString(1, onlinebean.getAccountNumber());
            insertStatCAOnline.setString(2, onlinebean.getRiskProfileCode());
            insertStatCAOnline.setString(3, onlinebean.getCurrencyCode());
            insertStatCAOnline.setInt(4, Integer.parseInt(onlinebean.getTxnCount()));
            insertStatCAOnline.setInt(5, Integer.parseInt(onlinebean.getCashTxnCount()));
            insertStatCAOnline.setDouble(6, Double.parseDouble(onlinebean.getTotalTxnAmount()));
            insertStatCAOnline.setDouble(7, Double.parseDouble(onlinebean.getTotalCashTxnAmount()));
            insertStatCAOnline.setDouble(8, Double.parseDouble(onlinebean.getCreditLimit()));
            insertStatCAOnline.setDouble(9, Double.parseDouble(onlinebean.getCashLimit()));
            insertStatCAOnline.setDouble(10, Double.parseDouble(onlinebean.getOtbCredit()));
            insertStatCAOnline.setDouble(11, Double.parseDouble(onlinebean.getOtbCash()));
            insertStatCAOnline.setInt(12, Integer.parseInt(onlinebean.getStatus()));




            resultId = insertStatCAOnline.executeUpdate();

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStatCAOnline.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int updateSequenceNumberAndStatus(Connection con, String bin, String sequence, String status) throws Exception {
        int resultId;

        PreparedStatement updateStat = null;
        try {
            //CardNumberFormateBean bean = new CardNumberFormateBean();

            String seq = "";
            int sq = Integer.parseInt(sequence);
            seq = this.getZeropadSeq(String.valueOf(sq + 1), sequence.length());

            updateStat = con.prepareStatement("UPDATE CARDNUMBERFORMATBIN SET SEQUENCENO=? WHERE BIN=?");

            updateStat.setString(1, seq);
            updateStat.setString(2, bin);

            int p = updateStat.executeUpdate();

            if (p == 1) {
                resultId = 1;
            } else {
                resultId = 0;
            }


        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                updateStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    private String getZeropadSeq(String seq, int length) {
        String padLine = "";
        String referanceNo = "";

        if (seq.length() < length) {
            for (int x = 0; x < length - seq.length(); x++) {
                padLine += "0";
            }
            referanceNo = padLine + seq;
        } else if (seq.length() == length) {
            referanceNo = seq;
        }



        return referanceNo;

    }

    public BulkCardNumberFormatBean getNumberFormateBinNumber(Connection con, String binNumber) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be execute
        BulkCardNumberFormatBean numFormatBean = new BulkCardNumberFormatBean();

        try {
            stmt = con.prepareStatement("SELECT NUMBERFORMATCODE,BIN,SEQUENCENO FROM CARDNUMBERFORMATBIN WHERE BIN=?");
            stmt.setString(1, binNumber);

            rs = stmt.executeQuery();

            if (rs.next()) {


                numFormatBean.setNumberFormatCode(rs.getString("NUMBERFORMATCODE"));
                numFormatBean.setSequenceNumber(rs.getString("SEQUENCENO"));


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


        return numFormatBean;
    }

    public boolean setNumberGenerationStatus(Connection CMSCon, String batchID, String status, String user) throws Exception {
        boolean isSet = false;
        PreparedStatement updateStatNumStat = null;

        try {

            updateStatNumStat = CMSCon.prepareStatement("UPDATE BULKCARDREQUEST SET STATUS=?,"
                    + " NUMBERGENARATEDUSER=?,PINDISTRIBUTIONSTATUS=?"
                    + " WHERE BATCHID=?");

            updateStatNumStat.setString(1, status);
            updateStatNumStat.setString(2, user);
            updateStatNumStat.setString(3, StatusVarList.NOSTATUS);
            updateStatNumStat.setInt(4, Integer.parseInt(batchID));

            updateStatNumStat.executeUpdate();
            isSet = true;

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {

                updateStatNumStat.close();

            } catch (Exception ex) {
                throw ex;
            }
        }
        return isSet;
    }

    public CardTempBean getCardTemplateDetails(Connection con, String cardTemCode) throws Exception {
        PreparedStatement CardTempStmt = null;		// Holds the statement to be executed
        CardTempBean bean = new CardTempBean();
        try {
            CardTempStmt = con.prepareStatement("SELECT TEMPLATECODE,CURRENCYCODE,RISKPROFILECODE,TRNSACTIONPROFILECODE,"
                    + "CASHADVANCERATE,CARDEXPPERIOD,STATUS,SERVICECODE,REISSUETHRESHHOLD,"
                    + "DEBITACCOUNTTEMPLATECODE FROM DEBITCARDTEMPLATE WHERE TEMPLATECODE=?");
            CardTempStmt.setString(1, cardTemCode);
            rs = CardTempStmt.executeQuery();

            if (rs.next()) {


                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setCurrencyNumCode(Integer.toString(rs.getInt("CURRENCYCODE")));
                bean.setRiskProfileCode(rs.getString("RISKPROFILECODE"));
                bean.setCashAdvancedRate(Integer.toString(rs.getInt("CASHADVANCERATE")));
                bean.setExpiryPeriod(Integer.toString(rs.getInt("CARDEXPPERIOD")));
                bean.setServiceCode(rs.getString("SERVICECODE"));
                bean.setReissueTresholPeriod(Integer.toString(rs.getInt("REISSUETHRESHHOLD")));
                bean.setAccountTempCode(rs.getString("DEBITACCOUNTTEMPLATECODE"));
                bean.setTxnProfCode(rs.getString("TRNSACTIONPROFILECODE"));



            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                CardTempStmt.close();

            } catch (Exception e) {
            }
        }


        return bean;
    }

    public AccountTempBean getAccountTemplateDetails(Connection con, String accountTemCode) throws Exception {
        PreparedStatement accountTempStmt = null;		// Holds the statement to be executed
        AccountTempBean bean = new AccountTempBean();
        try {
            accountTempStmt = con.prepareStatement("SELECT TEMPLATECODE,CURRENCYCODE,RISKPROFILECODE,"
                    + "STATUS FROM DEBITCARDACCOUNTTEMPLATE WHERE TEMPLATECODE=?");
            accountTempStmt.setString(1, accountTemCode);
            rs = accountTempStmt.executeQuery();

            if (rs.next()) {


                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setCurrencyCode(Integer.toString(rs.getInt("CURRENCYCODE")));
                bean.setAccRskProf(rs.getString("RISKPROFILECODE"));
                // bean.setBillCycle(rs.getString("BILLINGCYCLECODE"));
                bean.setStatus(rs.getString("STATUS"));


            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                accountTempStmt.close();

            } catch (Exception e) {
            }
        }


        return bean;
    }

    public BulkCardRequestBean getBulkCardBatch(Connection con, String batchID) throws SQLException {
        numberGenBean = new BulkCardRequestBean();
        PreparedStatement ps = null;

        String query = "SELECT DISTINCT BCR.BATCHID,BCR.CARDDOMAIN,CD.DESCRIPTION AS DOMAIN_DES,BCR.CARDTYPE,CT.DESCRIPTION AS TYPE_DES,"
                + " BCR.CARDPRODUCTCODE,CP.DESCRIPTION AS PROD_DES,BCR.BRANCHCODE,BB.DESCRPTION AS BRANCH_DES,BCR."
                + " PRIORITYLEVEL,PL.DESCRIPTION AS PRIO_DES,BCR.CREATEDTIME,BCR.LASTUPDATEDTIME,"
                + " BCR.REQUESTEDNOOFCARD,BCR.APPROVEDNOOFCARD,BCR.REQUESTEDUSER,BCR.APPROVEDUSER,"
                + " BCR.REQUESTUSERBRANCHCODE,BB.DESCRPTION AS REQ_B_DES,"
                + " BCR.STATUS,S.DESCRIPTION AS STATUS_DES,BCR.CURRENCYCODE,"
                + " C.DESCRIPTION AS CURR_DES,BCR.LASTUPDATEDUSER,BCR.CREDITLIMIT,"
                + " BN.DESCRPTION AS APP_B_DES,BCR.PRODUCTIONMODE,PM.DESCRIPTION AS PROD_MODE_DES,"
                + " BCR.APPROVEDUSERBRANCHCODE,B.DESCRIPTION AS BINDESCRIPTION,T.TEMPLATENAME "
                + " FROM CARDDOMAIN CD,CARDTYPE CT,CARDPRODUCT CP,BANKBRANCH BB,PRIORITYLEVEL PL,"
                + " PRODUCTIONMODE PM,CARDBIN B,"
                + " STATUS S,CURRENCY C,BULKCARDREQUEST BCR,DEBITCARDTEMPLATE T, "
                + " BANKBRANCH BN "
                + " WHERE CD.DOMAINID = BCR.CARDDOMAIN "
                + " AND CT.CARDTYPECODE = BCR.CARDTYPE "
                + " AND CP.PRODUCTCODE = BCR.CARDPRODUCTCODE "
                + " AND BN.BRANCHCODE=BCR.APPROVEDUSERBRANCHCODE "
                + " AND BB.BRANCHCODE = BCR.BRANCHCODE "
                + " AND BB.BRANCHCODE = BCR.REQUESTUSERBRANCHCODE "
                + " AND PL.PRIORITYLEVELCODE = BCR.PRIORITYLEVEL "
                + " AND S.STATUSCODE = BCR.STATUS AND B.BIN =BCR.CARDBIN "
                + " AND BCR.CURRENCYCODE=C.CURRENCYNUMCODE AND T.TEMPLATECODE=BCR.TEMPLATECODE "
                + " AND PM.PRODUCTIONMODECODE=BCR.PRODUCTIONMODE AND BCR.BATCHID=?"
                + " AND BB.BANKCODE=(SELECT BANKCODE FROM COMMONPARAMETER)";



        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(batchID));
            rs = ps.executeQuery();

            while (rs.next()) {


                numberGenBean.setBatchID(rs.getString("BATCHID"));
                numberGenBean.setCdDomain(rs.getString("CARDDOMAIN"));
                numberGenBean.setCdDomainDes((rs.getString("DOMAIN_DES") == null) ? "--" : rs.getString("DOMAIN_DES"));
                numberGenBean.setCdType(rs.getString("CARDTYPE"));
                numberGenBean.setCdTypeDes((rs.getString("TYPE_DES") == null) ? "--" : rs.getString("TYPE_DES"));
                numberGenBean.setCdProduct(rs.getString("CARDPRODUCTCODE"));
                numberGenBean.setCdProductDes((rs.getString("PROD_DES") == null) ? "--" : rs.getString("PROD_DES"));
                numberGenBean.setBranchCode(rs.getString("BRANCHCODE"));
                numberGenBean.setBranchName((rs.getString("BRANCH_DES") == null) ? "--" : rs.getString("BRANCH_DES"));
                numberGenBean.setPriorityLvl(rs.getString("PRIORITYLEVEL"));
                numberGenBean.setPriorityLvlDes((rs.getString("PRIO_DES") == null) ? "--" : rs.getString("PRIO_DES"));
                numberGenBean.setReqNumOfCds(rs.getString("REQUESTEDNOOFCARD"));
                numberGenBean.setApprvNumOfCds((rs.getString("APPROVEDNOOFCARD") == null) ? "--" : rs.getString("APPROVEDNOOFCARD"));
                numberGenBean.setReqUser(rs.getString("REQUESTEDUSER"));
                numberGenBean.setApprvUser((rs.getString("APPROVEDUSER") == null) ? "--" : rs.getString("APPROVEDUSER"));
                numberGenBean.setReqBranch(rs.getString("REQUESTUSERBRANCHCODE"));
                numberGenBean.setReqBranchDes(rs.getString("REQ_B_DES"));
                numberGenBean.setApprvBranch(rs.getString("APPROVEDUSERBRANCHCODE"));
                numberGenBean.setApprvBranchDes((rs.getString("APP_B_DES") == null) ? "--" : rs.getString("APP_B_DES"));
                numberGenBean.setStatus(rs.getString("STATUS"));
                numberGenBean.setStatusDes((rs.getString("STATUS_DES") == null) ? "--" : rs.getString("STATUS_DES"));
                numberGenBean.setCurrency(rs.getString("CURRENCYCODE"));
                numberGenBean.setCurrencyDes((rs.getString("CURR_DES") == null) ? "--" : rs.getString("CURR_DES"));
                numberGenBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                numberGenBean.setProductMode(rs.getString("PRODUCTIONMODE"));
                numberGenBean.setProductModeDes((rs.getString("PROD_MODE_DES") == null) ? "--" : rs.getString("PROD_MODE_DES"));
                numberGenBean.setCeatedTime(rs.getString("CREATEDTIME").substring(0, 10));
                numberGenBean.setCdBinDes(rs.getString("BINDESCRIPTION"));
                numberGenBean.setTemplateDes(rs.getString("TEMPLATENAME"));
                numberGenBean.setCreditLimit(Double.toString(rs.getDouble("CREDITLIMIT")));
                numberGenBean.setLastUpdatedTime(rs.getString("LASTUPDATEDTIME").substring(0, 10));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }


        return numberGenBean;

    }

    public int insertIntoCardTransactionTables(Connection con, Connection OnlineCon, CardBean cardBean) throws Exception {
        int resultId = 0;

        PreparedStatement insertStat12 = null;
        PreparedStatement onlineStat = null;
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {

            stmt = con.prepareStatement("SELECT TT.TRANSACTIONCODE,TT.ONLINETXNTYPE  FROM TRANSACTIONPROFILETRANSACTION TFT,TRANSACTIONTYPE TT WHERE TFT.PROFILECODE=? AND TFT.TXNCODE=TT.TRANSACTIONCODE ");
            stmt.setString(1, cardBean.getTxnProfileCode());
            rs = stmt.executeQuery();

            while (rs.next()) {
                insertStat12 = con.prepareStatement("INSERT INTO CARDTRANSACTIONTYPE "
                        + "(CARDNUMBER,TRANSACTIONCODE ) "
                        + " VALUES (?,?)");

                insertStat12.setString(1, cardBean.getCardNumber());
                insertStat12.setString(2, rs.getString("TRANSACTIONCODE"));


                int a = insertStat12.executeUpdate();

                onlineStat = OnlineCon.prepareStatement("INSERT INTO ECMS_ONLINE_CARD_TXN "
                        + "(CARDNO,TXNTYPECODE ) "
                        + " VALUES (?,?)");

                onlineStat.setString(1, cardBean.getCardNumber());
                onlineStat.setString(2, rs.getString("ONLINETXNTYPE"));


                int b = onlineStat.executeUpdate();

                if (a == 1 && b == 1) {
                    resultId = 1;
                } else {
                    resultId = 0;
                    throw new Exception();
                }

            }



        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat12.close();
                onlineStat.close();
                stmt.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;


    }

    public String getOnlineCardDomain(Connection CMSCon, String cardDomin) throws Exception {
        PreparedStatement getCd = null;
        String cardDominId = null;
        try {

            getCd = CMSCon.prepareStatement("SELECT D.DOMAINPRIFIX "
                    + " FROM CARDDOMAIN D WHERE D.DOMAINID = ?");

            getCd.setString(1, cardDomin);
            rs = getCd.executeQuery();

            while (rs.next()) {

                cardDominId = rs.getString("DOMAINPRIFIX");

            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                rs.close();
                getCd.close();

            } catch (Exception e) {
                throw e;
            }
            return cardDominId;
        }
    }

    /**
     * get card  key list
     * @param CMSCon
     * @param bin
     * @param product
     * @return List<CardKeyBean>
     * @throws Exception 
     */
    public List<String> getCardKeyList(Connection CMSCon, String bin, String product) throws Exception {
        PreparedStatement getCdKey = null;
        cardKeyList = new ArrayList<String>();
        try {

            getCdKey = CMSCon.prepareStatement("SELECT CARDKEYID FROM CARDPRODUCTBIN "
                    + " WHERE PRODUCTCODE = ? AND BINCODE= ?");

            getCdKey.setString(1, product);
            getCdKey.setString(2, bin);

            rs = getCdKey.executeQuery();

            while (rs.next()) {

                cardKeyList.add(Integer.toString(rs.getInt("CARDKEYID")));

            }

        } finally {
            try {
                rs.close();
                getCdKey.close();

            } catch (Exception e) {
                throw e;
            }
            return cardKeyList;
        }

    }

    public String getCardKey(Connection online, List<String> cardKeyList, String productionMode) throws Exception {
        PreparedStatement getCdKey = null;
        String cardKey = null;
        String query = null;
        String inPart = null;
        try {

            for (int i = 0; i < cardKeyList.size(); i++) {
                if (i != 0) {
                    inPart += ",";
                    inPart += cardKeyList.get(i);
                } else {
                    inPart = cardKeyList.get(i);
                }

            }

            query = "SELECT CK.CARDKEYID FROM ECMS_ONLINE_CARDKEYS CK,ECMS_ONLINE_CARDKEYPROFILE CP"
                    + " WHERE CK.CARDKEYPROFILEID = CP.ID AND CP.PRODUCTIONMODE = '" + productionMode + "' "
                    + " AND CARDKEYID IN (" + inPart + ") ";

            getCdKey = online.prepareStatement(query);
            rs = getCdKey.executeQuery();

            while (rs.next()) {
                cardKey = Integer.toString(rs.getInt("CARDKEYID"));
            }

        } finally {
            try {
                rs.close();
                getCdKey.close();

            } catch (Exception e) {
                throw e;
            }
            return cardKey;
        }

    }
}
