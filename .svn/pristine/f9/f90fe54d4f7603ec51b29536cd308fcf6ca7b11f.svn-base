/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardrequest.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class BulkCardRequestPersistance {

    ResultSet rs;
    HashMap<String, String> cardDomain = null;
    HashMap<String, String> bnkBranches = null;
    List<BulkCardRequestBean> reqList = null;
    HashMap<String, String> cdProduct = null;
    private HashMap<String, String> productModes = null;
    List<CurrencyBean> currency = null;
    SystemUserBean sysUsrBean = null;

    public HashMap<String, String> getCardDomains(Connection con) throws SQLException {

        PreparedStatement ps = null;
        cardDomain = new HashMap<String, String>();

        try {
            ps = con.prepareStatement("SELECT DOMAINID,DESCRIPTION FROM CARDDOMAIN WHERE PREPERSOSTATUS='YES'");
            rs = ps.executeQuery();

            while (rs.next()) {
                cardDomain.put(rs.getString("DOMAINID"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }

        return cardDomain;

    }

    public List<BulkCardRequestBean> getAllBulkCardRequests(Connection con, SystemUserBean sysBean) throws SQLException {
        reqList = new ArrayList<BulkCardRequestBean>();
        PreparedStatement ps = null;

        String query = "SELECT  BCR.BATCHID,BCR.CARDDOMAIN,CD.DESCRIPTION AS DOMAIN_DES,BCR.CARDTYPE,CT.DESCRIPTION AS TYPE_DES,"
                + "BCR.CARDPRODUCTCODE,CP.DESCRIPTION AS PROD_DES,BCR.BRANCHCODE,BB.DESCRPTION AS BRANCH_DES,BCR.PRIORITYLEVEL,"
                + "PL.DESCRIPTION AS PRIO_DES,BCR.REQUESTEDNOOFCARD,BCR.APPROVEDNOOFCARD,BCR.REQUESTEDUSER,BCR.APPROVEDUSER,"
                + "BCR.REQUESTUSERBRANCHCODE,BR.DESCRPTION AS REQ_B_DES,"
                + "BCR.STATUS,S.DESCRIPTION AS STATUS_DES,BCR.CURRENCYCODE,"
                + "C.DESCRIPTION AS CURR_DES,BCR.LASTUPDATEDUSER,"
                + "BN.DESCRPTION AS APP_B_DES,BCR.PRODUCTIONMODE,PM.DESCRIPTION AS PROD_MODE_DES,BCR.APPROVEDUSERBRANCHCODE "
                + "FROM CARDDOMAIN CD,CARDTYPE CT,CARDPRODUCT CP,BANKBRANCH BB,PRIORITYLEVEL PL,BANKBRANCH BR,"
                + "PRODUCTIONMODE PM,STATUS S,CURRENCY C,BULKCARDREQUEST BCR left OUTER JOIN BANKBRANCH BN on BCR.APPROVEDUSERBRANCHCODE = BN.BRANCHCODE , COMMONPARAMETER CPP "
                + "WHERE CD.DOMAINID = BCR.CARDDOMAIN "
                + "AND CT.CARDTYPECODE = BCR.CARDTYPE "
                + "AND CP.PRODUCTCODE = BCR.CARDPRODUCTCODE "
                + "AND BB.BRANCHCODE = BCR.BRANCHCODE "
                + "AND PL.PRIORITYLEVELCODE = BCR.PRIORITYLEVEL "
                + "AND BCR.REQUESTUSERBRANCHCODE = BR.BRANCHCODE "
                + "AND S.STATUSCODE = BCR.STATUS "
                + "AND BCR.CURRENCYCODE=C.CURRENCYNUMCODE "
                + "AND PM.PRODUCTIONMODECODE=BCR.PRODUCTIONMODE "
                + "AND BB.BANKCODE=CPP.BANKCODE "
                + "AND BR.BANKCODE=CPP.BANKCODE "
                + "AND (BN.BANKCODE= CPP.BANKCODE OR BCR.APPROVEDUSERBRANCHCODE IS NULL) "
                + "AND BR.BANKCODE= CPP.BANKCODE ";

        if (!sysBean.getUserName().equals("admin")) {
            query += " AND BCR.BRANCHCODE = ?";
        }


        try {
            ps = con.prepareStatement(query);
            if (!sysBean.getUserName().equals("admin")) {
                ps.setString(1, sysBean.getBranchname());
            }
            rs = ps.executeQuery();

            while (rs.next()) {
                BulkCardRequestBean bean = new BulkCardRequestBean();

                bean.setBatchID(rs.getString("BATCHID"));
                bean.setCdDomain(rs.getString("CARDDOMAIN"));
                bean.setCdDomainDes((rs.getString("DOMAIN_DES") == null) ? "--" : rs.getString("DOMAIN_DES"));
                bean.setCdType(rs.getString("CARDTYPE"));
                bean.setCdTypeDes((rs.getString("TYPE_DES") == null) ? "--" : rs.getString("TYPE_DES"));
                bean.setCdProduct(rs.getString("CARDPRODUCTCODE"));
                bean.setCdProductDes((rs.getString("PROD_DES") == null) ? "--" : rs.getString("PROD_DES"));
                bean.setBranchCode(rs.getString("BRANCHCODE"));
                bean.setBranchName((rs.getString("BRANCH_DES") == null) ? "--" : rs.getString("BRANCH_DES"));
                bean.setPriorityLvl(rs.getString("PRIORITYLEVEL"));
                bean.setPriorityLvlDes((rs.getString("PRIO_DES") == null) ? "--" : rs.getString("PRIO_DES"));
                bean.setReqNumOfCds(rs.getString("REQUESTEDNOOFCARD"));
                bean.setApprvNumOfCds((rs.getString("APPROVEDNOOFCARD") == null) ? "--" : rs.getString("APPROVEDNOOFCARD"));
                bean.setReqUser(rs.getString("REQUESTEDUSER"));
                bean.setApprvUser((rs.getString("APPROVEDUSER") == null) ? "--" : rs.getString("APPROVEDUSER"));
                bean.setReqBranch(rs.getString("REQUESTUSERBRANCHCODE"));
                bean.setReqBranchDes(rs.getString("REQ_B_DES"));
                bean.setApprvBranch(rs.getString("APPROVEDUSERBRANCHCODE"));
                bean.setApprvBranchDes((rs.getString("APP_B_DES") == null) ? "--" : rs.getString("APP_B_DES"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes((rs.getString("STATUS_DES") == null) ? "--" : rs.getString("STATUS_DES"));
                bean.setCurrency(rs.getString("CURRENCYCODE"));
                bean.setCurrencyDes((rs.getString("CURR_DES") == null) ? "--" : rs.getString("CURR_DES"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setProductMode(rs.getString("PRODUCTIONMODE"));
                bean.setProductModeDes((rs.getString("PROD_MODE_DES") == null) ? "--" : rs.getString("PROD_MODE_DES"));

                reqList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }


        return reqList;

    }

    public HashMap<String, String> getBranchNames(Connection con) throws SQLException {
        PreparedStatement ps = null;
        bnkBranches = new HashMap<String, String>();

        try {
            ps = con.prepareStatement("SELECT BRANCHCODE,DESCRPTION FROM BANKBRANCH WHERE BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER)");
            rs = ps.executeQuery();

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
     public String getBranchDes(Connection con, String branchcode) throws SQLException {
        PreparedStatement ps = null;
        String brnchDes = "";

        try {
            ps = con.prepareStatement("SELECT DESCRPTION FROM BANKBRANCH WHERE BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER) and BRANCHCODE=?");
            ps.setString(1, branchcode);
            
            rs = ps.executeQuery();

            while (rs.next()) {
                brnchDes = rs.getString("DESCRPTION");
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }

        return brnchDes;
    }

    public SystemUserBean getUserDetails(Connection con, String username) throws SQLException {
        PreparedStatement ps = null;
        sysUsrBean = new SystemUserBean();

        try {
            ps = con.prepareStatement("SELECT USERNAME,BRANCHNAME FROM SYSTEMUSER WHERE USERNAME = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();

            while (rs.next()) {
                sysUsrBean.setUserName(rs.getString("USERNAME"));
                sysUsrBean.setBranchname(rs.getString("BRANCHNAME"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }

        return sysUsrBean;
    }

    public HashMap<String, String> getCardProducts(Connection con, String cdType, String cdDomain) throws Exception {


        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT PRODUCTCODE,DESCRIPTION FROM CARDPRODUCT WHERE CARDDOMAIN=? AND CARDTYPE=?");
            ps.setString(1, cdDomain);
            ps.setString(2, cdType);

            rs = ps.executeQuery();
            cdProduct = new HashMap<String, String>();

            while (rs.next()) {

                cdProduct.put(rs.getString("PRODUCTCODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }

        return cdProduct;
    }

    public List<CurrencyBean> getCurrency(Connection con, String cdProduct) throws Exception {


        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT CURRENCYNUMCODE,DESCRIPTION FROM CURRENCY WHERE CURRENCYNUMCODE IN("
                    + "SELECT CURRENCYTYPECODE FROM CARDBIN WHERE BIN IN("
                    + "SELECT BINCODE FROM CARDPRODUCTBIN WHERE PRODUCTCODE=?))");
            ps.setString(1, cdProduct);

            rs = ps.executeQuery();
            currency = new ArrayList<CurrencyBean>();

            while (rs.next()) {

                CurrencyBean bean = new CurrencyBean();

                bean.setCurrencyCode(rs.getString("CURRENCYNUMCODE"));
                bean.setCurrencyDes(rs.getString("DESCRIPTION"));

                currency.add(bean);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }

        return currency;
    }

    public Boolean insertNewBulkCdReq(BulkCardRequestBean bean, Connection con) throws SQLException, Exception {
        int row = -1;
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO BULKCARDREQUEST (CARDDOMAIN,CARDTYPE,CARDPRODUCTCODE,BRANCHCODE,PRIORITYLEVEL,REQUESTEDNOOFCARD,REQUESTEDUSER,"
                    + "REQUESTUSERBRANCHCODE,STATUS,CREATEDTIME,LASTUPDATEDUSER,CURRENCYCODE,PRODUCTIONMODE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";


            ps = con.prepareStatement(query);

            ps.setString(1, bean.getCdDomain());
            ps.setString(2, bean.getCdType());
            ps.setString(3, bean.getCdProduct());
            ps.setString(4, bean.getBranchCode());
            ps.setString(5, bean.getPriorityLvl());
            ps.setString(6, bean.getReqNumOfCds());
            ps.setString(7, bean.getReqUser());
            ps.setString(8, bean.getReqBranch());
            ps.setString(9, bean.getStatus());
            ps.setTimestamp(10, SystemDateTime.getSystemDataAndTime());
            ps.setString(11, bean.getLastUpdatedUser());
            ps.setString(12, bean.getCurrency());
            ps.setString(13, bean.getProductMode());

            row = ps.executeUpdate();
            if (row == 1) {
                success = true;
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return success;
    }

    public boolean deleteBulkCdReq(Connection con, String batchId) throws SQLException, Exception {
        boolean success = false;
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE BULKCARDREQUEST SET STATUS=? WHERE BATCHID=?";

            ps = con.prepareStatement(query);
            ps.setString(1, "BCRD");
            ps.setString(2, batchId);

            i = ps.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        if (i == 1) {
            success = true;
        }
        return success;
    }

    public Boolean updateBulkCdReq(BulkCardRequestBean bean, Connection con) throws SQLException, Exception {
        int row = -1;
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE BULKCARDREQUEST SET CARDDOMAIN=?,CARDTYPE=?,CARDPRODUCTCODE=?,BRANCHCODE=?,PRIORITYLEVEL=?,REQUESTEDNOOFCARD=?,"
                    + "REQUESTEDUSER=?,REQUESTUSERBRANCHCODE=?,LASTUPDATEDTIME=?,LASTUPDATEDUSER=?,CURRENCYCODE=?,PRODUCTIONMODE=? WHERE BATCHID=? ";

            ps = con.prepareStatement(query);

            ps.setString(1, bean.getCdDomain());
            ps.setString(2, bean.getCdType());
            ps.setString(3, bean.getCdProduct());
            ps.setString(4, bean.getBranchCode());
            ps.setString(5, bean.getPriorityLvl());
            ps.setString(6, bean.getReqNumOfCds());
            ps.setString(7, bean.getReqUser());
            ps.setString(8, bean.getReqBranch());
            ps.setTimestamp(9, SystemDateTime.getSystemDataAndTime());
            ps.setString(10, bean.getLastUpdatedUser());
            ps.setString(11, bean.getCurrency());
            ps.setString(12, bean.getProductMode());
            ps.setString(13, bean.getBatchID());

            row = ps.executeUpdate();
            if (row == 1) {
                success = true;
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return success;
    }

    public HashMap<String, String> getProductionMode(Connection con, String currency, String product, String type) throws Exception {


        PreparedStatement ps = null;

        try {

            String query = "SELECT CB.PRODUCTIONMODE,PM.DESCRIPTION AS PRO_MODE_DES "
                    + "FROM CARDBIN CB,PRODUCTIONMODE PM "
                    + "WHERE  "
                    + "CB.CURRENCYTYPECODE=? "
                    + "AND CB.CARDTYPE=? "
                    + "AND CB.BIN IN (SELECT BINCODE FROM CARDPRODUCTBIN WHERE PRODUCTCODE=?)";

            ps = con.prepareStatement(query);

            ps.setString(1, zeroPad(currency));
            ps.setString(2, type);
            ps.setString(3, product);

            rs = ps.executeQuery();
            productModes = new HashMap<String, String>();

            while (rs.next()) {

                productModes.put(rs.getString("PRODUCTIONMODE"), rs.getString("PRO_MODE_DES"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }

        return productModes;
    }

    /**
     * to pad zeros to the beginning of a string until length becomes 3 
     * @param currencyCode
     * @return 
     */
    private String zeroPad(String currencyCode) {
        int size = currencyCode.length();
        if (size < 3) {
            if (size == 2) {
                currencyCode = "0" + currencyCode;
            } else if (size == 1) {
                currencyCode = "00" + currencyCode;
            }
        }

        return currencyCode;

    }

    public String getBatchId(Connection con, String user) throws Exception {
        String batchId = "";
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT BATCHID FROM BULKCARDREQUEST WHERE LASTUPDATEDUSER=? AND BATCHID=(SELECT MAX(BATCHID) FROM BULKCARDREQUEST )");
            ps.setString(1, user);

            rs = ps.executeQuery();
            productModes = new HashMap<String, String>();

            while (rs.next()) {

                batchId = rs.getString("BATCHID");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }

        return batchId;
    }
    
    public Boolean confirmReceived(BulkCardRequestBean bean, Connection con) throws SQLException, Exception {
        int row = -1;
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE BULKCARDREQUEST SET RECIVEDUSER=?,REMARKS=?,STATUS=?,LASTUPDATEDTIME=?,LASTUPDATEDUSER=? WHERE BATCHID=? ";

            ps = con.prepareStatement(query);

            ps.setString(1, bean.getReceivedUser());
            ps.setString(2, bean.getRemark());
            ps.setString(3, bean.getStatus());
            ps.setTimestamp(4, SystemDateTime.getSystemDataAndTime());
            ps.setString(5, bean.getLastUpdatedUser());            
            ps.setString(6, bean.getBatchID());

            row = ps.executeUpdate();
            if (row == 1) {
                success = true;
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return success;
    }
}
