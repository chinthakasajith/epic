/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.distribution.persistance;

import com.epic.cms.cpmm.distribution.bean.BulkCardDistributionBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.SQLException;

/**
 *
 * @author nalin
 */
public class BulkCardandPinDistributionPersistance {

    private ResultSet rs;

    public List<BulkCardDistributionBean> searchCardDistributionDetails(BulkCardDistributionBean bulkDistBean, Connection CMSCon) throws Exception {
        PreparedStatement searchAllCard = null;
        List<BulkCardDistributionBean> bulkDistList = new ArrayList<BulkCardDistributionBean>();
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT C.BATCHID,C.CARDDOMAIN,C.CARDTYPE,C.CARDPRODUCTCODE,C.BRANCHCODE,"
                    + " C.REQUESTEDNOOFCARD,C.APPROVEDNOOFCARD,"
                    + " CT.DESCRIPTION AS CTDESCRIPTION,CP.DESCRIPTION AS CPDESCRIPTION,"
                    + " BB.DESCRPTION AS BBDESCRPTION, CD.DESCRIPTION AS CDDESCRIPTION"
                    + " FROM BULKCARDREQUEST C,CARDTYPE CT,CARDPRODUCT CP,BANKBRANCH BB,CARDDOMAIN CD"
                    + " WHERE C.CARDTYPE=CT.CARDTYPECODE AND C.CARDPRODUCTCODE=CP.PRODUCTCODE "
                    + " AND BB.BRANCHCODE =C.BRANCHCODE AND CD.DOMAINID=C.CARDDOMAIN AND C.STATUS IN (?,?) "
                    + " AND BB.BANKCODE=(SELECT BANKCODE FROM COMMONPARAMETER)";

            String condition = "";

            if (!bulkDistBean.getBatchID().contentEquals("") || bulkDistBean.getBatchID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.BATCHID LIKE '%" + bulkDistBean.getBatchID() + "%'";
            }

            if (!bulkDistBean.getCardDomain().contentEquals("") || bulkDistBean.getCardDomain().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CARDDOMAIN LIKE '%" + bulkDistBean.getCardDomain() + "%'";
            }

            if (!bulkDistBean.getCardType().contentEquals("") || bulkDistBean.getCardType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CARDTYPE LIKE '%" + bulkDistBean.getCardType() + "%'";
            }

            if (!bulkDistBean.getCardProduct().contentEquals("") || bulkDistBean.getCardProduct().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CARDPRODUCTCODE LIKE '%" + bulkDistBean.getCardProduct() + "%'";
            }

            if (!bulkDistBean.getBranchCode().contentEquals("") || bulkDistBean.getBranchCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.BRANCHCODE LIKE '%" + bulkDistBean.getBranchCode() + "%'";
            }

            if (!bulkDistBean.getPriorityLvl().contentEquals("") || bulkDistBean.getPriorityLvl().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.PRIORITYLEVEL LIKE '%" + bulkDistBean.getPriorityLvl() + "%'";
            }

            if (!bulkDistBean.getReqestedUser().contentEquals("") || bulkDistBean.getReqestedUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(C.REQUESTEDUSER) LIKE '%" + bulkDistBean.getReqestedUser().toUpperCase() + "%'";
            }

            if (!bulkDistBean.getCreatedTime().contentEquals("") || bulkDistBean.getCreatedTime().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CREATEDTIME LIKE '%" + bulkDistBean.getCreatedTime() + "%'";
            }

            if (!bulkDistBean.getFromDate().contentEquals("") || bulkDistBean.getFromDate().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CREATEDTIME >= TO_DATE('" + bulkDistBean.getFromDate() + "','YYYY-MM-DD')";
            }
            if (!bulkDistBean.getToDate().contentEquals("") || bulkDistBean.getToDate().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CREATEDTIME <= TO_DATE('" + bulkDistBean.getToDate() + "','YYYY-MM-DD')";
            }
            if (!condition.equals("")) {
                strSqlBasic += " AND " + condition + " ORDER BY C.BATCHID ";
            } else {

                strSqlBasic += " ORDER BY C.BATCHID";
            }

            searchAllCard = CMSCon.prepareStatement(strSqlBasic);
            searchAllCard.setString(1, StatusVarList.BULK_CARD_ENCODING_COMPLETE);
            searchAllCard.setString(2, StatusVarList.BULK_CARD_PIN_MAIL_COMPLETE);

            rs = searchAllCard.executeQuery();

            while (rs.next()) {

                BulkCardDistributionBean bean = new BulkCardDistributionBean();

                bean.setBatchID(rs.getString("BATCHID"));
                bean.setCardDomain(rs.getString("CARDDOMAIN"));
                bean.setCardDomainDes(rs.getString("CDDESCRIPTION"));
                bean.setCardType(rs.getString("CARDTYPE"));
                bean.setCardTypeDes(rs.getString("CTDESCRIPTION"));
                bean.setCardProduct(rs.getString("CARDPRODUCTCODE"));
                bean.setCardProductDes(rs.getString("CPDESCRIPTION"));
                bean.setBranchCode(rs.getString("BRANCHCODE"));
                bean.setBranchName(rs.getString("BBDESCRPTION"));
                bean.setReqNumOfCds(Integer.toString(rs.getInt("REQUESTEDNOOFCARD")));
                bean.setApprvNumOfCds(Integer.toString(rs.getInt("APPROVEDNOOFCARD")));


                bulkDistList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            searchAllCard.close();

        }
        return bulkDistList;

    }

    public List<BulkCardDistributionBean> searchPinDistributionDetails(BulkCardDistributionBean bulkDistBean, Connection CMSCon) throws Exception {
        PreparedStatement searchAllCard = null;
        List<BulkCardDistributionBean> bulkDistList = new ArrayList<BulkCardDistributionBean>();
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT C.BATCHID,C.CARDDOMAIN,C.CARDTYPE,C.CARDPRODUCTCODE,C.BRANCHCODE,"
                    + " C.REQUESTEDNOOFCARD,C.APPROVEDNOOFCARD,"
                    + " CT.DESCRIPTION AS CTDESCRIPTION,CP.DESCRIPTION AS CPDESCRIPTION,"
                    + " BB.DESCRPTION AS BBDESCRPTION, CD.DESCRIPTION AS CDDESCRIPTION"
                    + " FROM BULKCARDREQUEST C,CARDTYPE CT,CARDPRODUCT CP,BANKBRANCH BB,CARDDOMAIN CD"
                    + " WHERE C.CARDTYPE=CT.CARDTYPECODE AND C.CARDPRODUCTCODE=CP.PRODUCTCODE "
                    + " AND BB.BRANCHCODE =C.BRANCHCODE AND CD.DOMAINID=C.CARDDOMAIN"
                    + " AND C.PINSTATUS=? AND C.PINDISTRIBUTIONSTATUS=? "
                    + " AND BB.BANKCODE=(SELECT BANKCODE FROM COMMONPARAMETER)";

            String condition = "";

            if (!bulkDistBean.getBatchID().contentEquals("") || bulkDistBean.getBatchID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.BATCHID LIKE '%" + bulkDistBean.getBatchID() + "%'";
            }

            if (!bulkDistBean.getCardDomain().contentEquals("") || bulkDistBean.getCardDomain().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CARDDOMAIN LIKE '%" + bulkDistBean.getCardDomain() + "%'";
            }

            if (!bulkDistBean.getCardType().contentEquals("") || bulkDistBean.getCardType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CARDTYPE LIKE '%" + bulkDistBean.getCardType() + "%'";
            }

            if (!bulkDistBean.getCardProduct().contentEquals("") || bulkDistBean.getCardProduct().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CARDPRODUCTCODE LIKE '%" + bulkDistBean.getCardProduct() + "%'";
            }

            if (!bulkDistBean.getBranchCode().contentEquals("") || bulkDistBean.getBranchCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.BRANCHCODE LIKE '%" + bulkDistBean.getBranchCode() + "%'";
            }

            if (!bulkDistBean.getPriorityLvl().contentEquals("") || bulkDistBean.getPriorityLvl().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.PRIORITYLEVEL LIKE '%" + bulkDistBean.getPriorityLvl() + "%'";
            }

            if (!bulkDistBean.getReqestedUser().contentEquals("") || bulkDistBean.getReqestedUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(C.REQUESTEDUSER) LIKE '%" + bulkDistBean.getReqestedUser().toUpperCase() + "%'";
            }

            if (!bulkDistBean.getCreatedTime().contentEquals("") || bulkDistBean.getCreatedTime().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CREATEDTIME LIKE '%" + bulkDistBean.getCreatedTime() + "%'";
            }

            if (!bulkDistBean.getFromDate().contentEquals("") || bulkDistBean.getFromDate().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CREATEDTIME >= TO_DATE('" + bulkDistBean.getFromDate() + "','YYYY-MM-DD')";
            }
            if (!bulkDistBean.getToDate().contentEquals("") || bulkDistBean.getToDate().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " C.CREATEDTIME <= TO_DATE('" + bulkDistBean.getToDate() + "','YYYY-MM-DD')";
            }

            if (!condition.equals("")) {
                strSqlBasic += " AND " + condition + " ORDER BY C.BATCHID ";
            } else {

                strSqlBasic += " ORDER BY C.BATCHID";
            }

            searchAllCard = CMSCon.prepareStatement(strSqlBasic);
            searchAllCard.setString(1, StatusVarList.YESSTATUS);
            searchAllCard.setString(2, StatusVarList.NOSTATUS);

            rs = searchAllCard.executeQuery();

            while (rs.next()) {

                BulkCardDistributionBean bean = new BulkCardDistributionBean();

                bean.setBatchID(rs.getString("BATCHID"));
                bean.setCardDomain(rs.getString("CARDDOMAIN"));
                bean.setCardDomainDes(rs.getString("CDDESCRIPTION"));
                bean.setCardType(rs.getString("CARDTYPE"));
                bean.setCardTypeDes(rs.getString("CTDESCRIPTION"));
                bean.setCardProduct(rs.getString("CARDPRODUCTCODE"));
                bean.setCardProductDes(rs.getString("CPDESCRIPTION"));
                bean.setBranchCode(rs.getString("BRANCHCODE"));
                bean.setBranchName(rs.getString("BBDESCRPTION"));
                bean.setReqNumOfCds(Integer.toString(rs.getInt("REQUESTEDNOOFCARD")));
                bean.setApprvNumOfCds(Integer.toString(rs.getInt("APPROVEDNOOFCARD")));


                bulkDistList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            searchAllCard.close();

        }
        return bulkDistList;
    }

    public boolean proceedCardDistribution(Connection con, String[] batchList, String user) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE BULKCARDREQUEST "
                    + " SET STATUS=?,DISTRIBUTEDUSER=?,LASTUPDATEDUSER=?,"
                    + " LASTUPDATEDTIME=SYSDATE WHERE BATCHID=? ");


            for (int i = 0; i < batchList.length; i++) {

                updateStat.setString(1, StatusVarList.BULK_CARD_DISTRIBUTION_COMPLETE);
                updateStat.setString(2, user);
                updateStat.setString(3, user);
                updateStat.setInt(4, Integer.parseInt(batchList[i]));

                updateStat.executeUpdate();
                success = true;
            }

        } catch (SQLException ex) {
            throw ex;
        }
        return success;

    }

    public boolean updateCardDistributionStatus(Connection con, String batchId, String user) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARD SET CARDDISTRIBUTESTATUS=?,"
                    + " LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE WHERE BATCHID=?");

            updateStat.setString(1, StatusVarList.YESSTATUS);
            updateStat.setString(2, user);
            updateStat.setInt(3, Integer.parseInt(batchId));

            updateStat.executeUpdate();
            success = true;


        } catch (SQLException ex) {
            throw ex;
        }
        return success;

    }

    public boolean proceedPinDistribution(Connection con, String[] batchList, String user) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE BULKCARDREQUEST "
                    + " SET PINDISTRIBUTIONSTATUS=?,DISTRIBUTEDUSER=?,"
                    + " LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE WHERE BATCHID=?");


            for (int i = 0; i < batchList.length; i++) {
                updateStat.setString(1, StatusVarList.YESSTATUS);
                
                updateStat.setString(2, user);
                updateStat.setString(3, user);
                
                updateStat.setString(4, batchList[i]);

                updateStat.executeUpdate();
                success = true;
            }

        } catch (SQLException ex) {
            throw ex;
        }
        return success;

    }

    public boolean updatePinDistributionStatus(Connection con, String batchId, String user) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARD SET PINDISTRIBUTIONSTATUS=?,"
                    + " LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE WHERE BATCHID=?");

            updateStat.setString(1, StatusVarList.YESSTATUS);            
            updateStat.setString(2, user);
            updateStat.setString(3, batchId);

            updateStat.executeUpdate();
            success = true;


        } catch (SQLException ex) {
            throw ex;
        }
        return success;

    }
}
