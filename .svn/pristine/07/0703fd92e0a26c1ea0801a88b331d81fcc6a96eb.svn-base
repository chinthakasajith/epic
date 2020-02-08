/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.persistance;

import com.epic.cms.camm.applicationproccessing.capturedata.bean.BulkCardBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author nalin
 */
public class BulkCardDataCapturePersistance {

    ResultSet rs;
    private HashMap<String, String> bnkBranches = null;
    private BulkCardRequestBean bulkCardRequestBean = null;

    public HashMap<String, String> getBranchNames(Connection CMScon) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = CMScon.prepareStatement("SELECT BRANCHCODE,DESCRPTION "
                    + "FROM BANKBRANCH "
                    + "WHERE BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER)");
            rs = ps.executeQuery();
            bnkBranches = new HashMap<String, String>();

            while (rs.next()) {
                bnkBranches.put(rs.getString("BRANCHCODE"), rs.getString("DESCRPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }

        return bnkBranches;
    }

    public String checkCardRequestedBranch(Connection CMSCon, String cardNumber) throws Exception {
        String branch = null;
        PreparedStatement ps = null;
        try {

            ps = CMSCon.prepareStatement("SELECT B.BRANCHCODE FROM BULKCARDREQUEST B"
                    + " WHERE B.BATCHID = (SELECT BATCHID FROM CARD WHERE CARDNUMBER =?)");

            ps.setString(1, cardNumber);
            rs = ps.executeQuery();

            while (rs.next()) {

                branch = rs.getString("BRANCHCODE");

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return branch;
    }

    public BulkCardRequestBean getBulkCardBatchDetails(Connection CMSCon, String cardNumber) throws Exception {
        PreparedStatement ps = null;


        try {
            ps = CMSCon.prepareStatement("SELECT B.BRANCHCODE,B.CARDDOMAIN,B.CARDTYPE,"
                    + " B.CARDPRODUCTCODE,PRIORITYLEVEL,CURRENCYCODE,CARDBIN,PRODUCTIONMODE,"
                    + " TEMPLATECODE,CREDITLIMIT FROM BULKCARDREQUEST B"
                    + " WHERE B.BATCHID = (SELECT BATCHID FROM CARD WHERE CARDNUMBER =?)");

            ps.setString(1, cardNumber);

            rs = ps.executeQuery();

            while (rs.next()) {
                bulkCardRequestBean = new BulkCardRequestBean();

                bulkCardRequestBean.setBranchCode(rs.getString("BRANCHCODE"));
                bulkCardRequestBean.setCdDomain(rs.getString("CARDDOMAIN"));
                bulkCardRequestBean.setCdType(rs.getString("CARDTYPE"));
                bulkCardRequestBean.setCdProduct(rs.getString("CARDPRODUCTCODE"));
                bulkCardRequestBean.setPriorityLvl(rs.getString("PRIORITYLEVEL"));
                bulkCardRequestBean.setCurrency(rs.getString("CURRENCYCODE"));
                bulkCardRequestBean.setCdBin(rs.getString("CARDBIN"));
                bulkCardRequestBean.setCdProductionMode(rs.getString("PRODUCTIONMODE"));
                bulkCardRequestBean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bulkCardRequestBean.setCreditLimit(Double.toString(rs.getDouble("CREDITLIMIT")));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return bulkCardRequestBean;
    }

    public boolean checkCardNumberIsExist(Connection CMSCon, String cardNumber) throws Exception {
        PreparedStatement ps = null;
        boolean isExist = false;
        try {
            ps = CMSCon.prepareStatement("SELECT C.CARDNUMBER FROM CARD C "
                    + " WHERE C.CARDNUMBER = ? AND C.PINSTATUS= ? "
                    + " AND C.PINMAILSTATUS = ? "
                    + " AND C.EMBOSSSTATUS= ? "
                    + " AND C.PINDISTRIBUTIONSTATUS=? "
                    + " AND C.CARDDISTRIBUTESTATUS = ? ");

            ps.setString(1, cardNumber);
            ps.setString(2, StatusVarList.YESSTATUS);
            ps.setString(3, StatusVarList.YESSTATUS);
            ps.setString(4, StatusVarList.YESSTATUS);
            ps.setString(5, StatusVarList.YESSTATUS);
            ps.setString(6, StatusVarList.YESSTATUS);
            rs = ps.executeQuery();

            while (rs.next()) {
                isExist = true;
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return isExist;
    }

    public boolean insertBulkCardToCardApplication(Connection CMSCon, BulkCardBean bulkCardBean, BulkCardRequestBean requestBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO CARDAPPLICATION(APPLICATIONID,"
                    + " IDENTIFICATIONTYPE,IDENTIFICATIONNO,SYSRECOMENDEDCREDITLIMIT,"
                    + " CONFIRMEDCREDITLIMIT,SYSRECOMENDEDCARDPRODUCT,"
                    + " CONFIRMEDCARDPRODUCT,PRODUCTIONMODE,BINPROFILE,CARDTEMPLATECODE,"
                    + " SYSRECOMENDEDCURRENCY,CONFIRMEDCURRENCY,CARDNUMBER,"
                    + " BRANCHCODE,LASTUPDATEDUSER,SIGNATURE,CARDAPPLIACTIONDOMAIN,"
                    + " ASSIGNUSER,ASSIGNSTATUS,LASTUPDATEDTIME,CREATETIME) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE) ");

            insertStat.setString(1, bulkCardBean.getApplicationID());
            insertStat.setString(2, bulkCardBean.getIdentificationType());
            insertStat.setString(3, bulkCardBean.getIdentificationNumber());
            insertStat.setString(4, requestBean.getCreditLimit());
            insertStat.setString(5, requestBean.getCreditLimit());
            insertStat.setString(6, requestBean.getCdProduct());
            insertStat.setString(7, requestBean.getCdProduct());
            insertStat.setString(8, requestBean.getCdProductionMode());
            insertStat.setString(9, requestBean.getCdBin());
            insertStat.setString(10, requestBean.getTemplateCode());
            insertStat.setString(11, requestBean.getCurrency());
            insertStat.setString(12, requestBean.getCurrency());
            insertStat.setString(13, bulkCardBean.getCardNumber());
            insertStat.setString(14, bulkCardBean.getBranch());
            insertStat.setString(15, bulkCardBean.getLsatUpdatedUser());
            insertStat.setString(16, bulkCardBean.getSignatureFileName());
            insertStat.setString(17, StatusVarList.DEBIT);
            insertStat.setString(18, bulkCardBean.getLsatUpdatedUser());
            insertStat.setString(19, StatusVarList.INITIAL_STATUS);

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean insertBulkCardToDebitCardApplicationDetails(Connection CMSCon, BulkCardBean bulkCardBean, BulkCardRequestBean requestBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO DEBITCARDAPPLICATIONDETAILS"
                    + " (APPLICATIONID,TITLE,FIRSTNAME,LASTNAME,MIDDLENAME,MOTHERSMAIDENNAME,"
                    + " REQUESTEDCARDTYPE,CARDPRODUCT,ACCOUNTNO1,ACCOUNTNO2,IDENTIFICATIONTYPE,"
                    + " IDENTIFICATIONNO,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATETIME) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE) ");

            insertStat.setString(1, bulkCardBean.getApplicationID());
            insertStat.setString(2, bulkCardBean.getTitle());
            insertStat.setString(3, bulkCardBean.getFirstName());
            insertStat.setString(4, bulkCardBean.getLastName());
            insertStat.setString(5, bulkCardBean.getMiddleName());
            insertStat.setString(6, bulkCardBean.getMothersMaidenName());
            insertStat.setString(7, requestBean.getCdType());
            insertStat.setString(8, requestBean.getCdProduct());
            insertStat.setString(9, bulkCardBean.getPrimeryAccNo());
            insertStat.setString(10, bulkCardBean.getOtherAccNo1());
            insertStat.setString(11, bulkCardBean.getIdentificationType());
            insertStat.setString(12, bulkCardBean.getIdentificationNumber());
            insertStat.setString(13, bulkCardBean.getLsatUpdatedUser());

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean insertToCardApplicationStatus(Connection CMSCon, BulkCardBean bulkCardBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {

            insertStat = CMSCon.prepareStatement("INSERT INTO CARDAPPLICATIONSTATUS"
                    + "(APPLICATIONID,APPLICATIONSTATUS) VALUES(?,?)");

            insertStat.setString(1, bulkCardBean.getApplicationID());
            insertStat.setString(2, StatusVarList.BULK_CARD_DATA_CAPTURE_COMPLETE);

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean insertToCardApplicationHistory(Connection CMSCon, BulkCardBean bulkCardBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {

            insertStat = CMSCon.prepareStatement("INSERT INTO CARDAPPLICATIONHISTORY"
                    + " (APPLICATIONID,APPLICATIONLEVEL,STATUS,REMARKS,LASTUPDATEDUSER,"
                    + " CREATEDTIME,LASTUPDATEDTIME) VALUES(?,?,?,?,?,SYSDATE,SYSDATE)");

            insertStat.setString(1, bulkCardBean.getApplicationID());
            insertStat.setString(2, StatusVarList.DATA_CAPTURE_STATUS);
            insertStat.setString(3, StatusVarList.HISTORY_COMPLETE);
            insertStat.setString(4, "Data Capture Completed");
            insertStat.setString(5, bulkCardBean.getLsatUpdatedUser());

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean insertToCardApplicationDocument(Connection CMSCon, BulkCardBean bulkCardBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {

            insertStat = CMSCon.prepareStatement("INSERT INTO CARDAPPLICATIONDOCUMENT"
                    + " (APPLICATIONID,VERIFICATIONCATEGORY,DOCUMENTTYPE,FILENAME,LASTUPDATEDUSER,"
                    + " CREATETIME,LASTUPDATEDTIME) VALUES(?,?,?,?,?,SYSDATE,SYSDATE)");

            insertStat.setString(1, bulkCardBean.getApplicationID());
            insertStat.setString(2, StatusVarList.ID_VERIFICATION_CATEGORY);
            insertStat.setString(3, StatusVarList.NATIONAL_IDENTITY_CARD);
            insertStat.setString(4, bulkCardBean.getNicFileName());
            insertStat.setString(5, bulkCardBean.getLsatUpdatedUser());

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }
}
