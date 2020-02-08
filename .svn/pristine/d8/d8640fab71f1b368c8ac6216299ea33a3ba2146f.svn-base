/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardrequestconfirm.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.system.util.variable.DataTypeVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author badrika
 */
public class BulkCardConfirmPersistance {

    private List<BulkCardRequestBean> searchList;
    private List<CardBinBean> cardBinList;
    private List<CardTemplateBean> cardTempList;

    public List<BulkCardRequestBean> searchBulkCard(Connection con, BulkCardRequestBean searchBean) throws Exception {



        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query



        try {

            strSqlBasic = "SELECT DISTINCT BR.BATCHID, BR.CARDDOMAIN, BR.CARDTYPE, BR.CARDPRODUCTCODE, BR.BRANCHCODE, BR.PRIORITYLEVEL, BR.REQUESTEDNOOFCARD, "
                    + "BR.APPROVEDNOOFCARD, BR.REQUESTEDUSER, BR.APPROVEDUSER, BR.REQUESTUSERBRANCHCODE, BR.APPROVEDUSERBRANCHCODE, BR.STATUS, "
                    + "BR.LASTUPDATEDUSER, BR.CURRENCYCODE, BR.CARDBIN, BR.PRODUCTIONMODE, BR.REMARKS, BR.TEMPLATECODE, "
                    + "CD.DESCRIPTION AS DOMDES, CT.DESCRIPTION AS CTYPDES, CP.DESCRIPTION AS CPDES, BB.DESCRPTION AS BBNAME, PL.DESCRIPTION AS PLDES, "
                    + "ST.DESCRIPTION AS STDES, CR.DESCRIPTION AS CRDES, PM.DESCRIPTION AS PMDES "
                    + "FROM BULKCARDREQUEST BR, CARDDOMAIN CD, CARDTYPE CT, CARDPRODUCT CP, BANKBRANCH BB, PRIORITYLEVEL PL, STATUS ST, CURRENCY CR, "
                    + "CARDBIN CB,PRODUCTIONMODE PM ";

            String condition = "";


            if (!searchBean.getBatchID().contentEquals("") || searchBean.getBatchID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.BATCHID = '" + searchBean.getBatchID() + "'";
            }

            if (!searchBean.getCdDomain().contentEquals("") || searchBean.getCdDomain().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.CARDDOMAIN = '" + searchBean.getCdDomain() + "'";
            }

            if (!searchBean.getCdType().contentEquals("") || searchBean.getCdType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.CARDTYPE = '" + searchBean.getCdType() + "'";
            }

            if (!searchBean.getCdProduct().equals("") || searchBean.getCdProduct().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.CARDPRODUCTCODE = '" + searchBean.getCdProduct() + "'";
            }

            if (!searchBean.getBranchCode().contentEquals("") || searchBean.getBranchCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.BRANCHCODE = '" + searchBean.getBranchCode() + "'";
            }

            if (!searchBean.getPriorityLvl().contentEquals("") || searchBean.getPriorityLvl().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.PRIORITYLEVEL = '" + searchBean.getPriorityLvl() + "'";
            }

            if (!searchBean.getProductMode().contentEquals("") || searchBean.getProductMode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.PRODUCTIONMODE = '" + searchBean.getProductMode() + "'";
            }
            //date convertions
            if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.CREATEDTIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND BR.CREATEDTIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
            }

            if (!searchBean.getFromDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.CREATEDTIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd')";
            }
            if (!searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "BR.CREATEDTIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
            }

            //this is the complete part.. keep this
//            if (!condition.equals("")) {
//                strSqlBasic += " WHERE BR.CARDDOMAIN = CD.DOMAINID AND BR.CARDTYPE = CT.CARDTYPECODE AND BR.CARDPRODUCTCODE = CP.PRODUCTCODE AND "
//                        + "BR.BRANCHCODE = BB.BRANCHCODE AND BR.PRIORITYLEVEL = PL.PRIORITYLEVELCODE AND BR.STATUS = ST.STATUSCODE "
//                        + "AND BR.CURRENCYCODE = CR.CURRENCYNUMCODE AND BR.CARDBIN = CB.BIN AND BR.PRODUCTIONMODE = PM.PRODUCTIONMODECODE"
//                        + " AND " + condition + " AND BR.STATUS = ? ORDER BY BR.BATCHID ";
//            } else {
//                strSqlBasic += " WHERE BR.CARDDOMAIN = CD.DOMAINID AND BR.CARDTYPE = CT.CARDTYPECODE AND BR.CARDPRODUCTCODE = CP.PRODUCTCODE AND "
//                        + "BR.BRANCHCODE = BB.BRANCHCODE AND BR.PRIORITYLEVEL = PL.PRIORITYLEVELCODE AND BR.STATUS = ST.STATUSCODE "
//                        + "AND BR.CURRENCYCODE = CR.CURRENCYNUMCODE AND BR.CARDBIN = CB.BIN AND BR.PRODUCTIONMODE = PM.PRODUCTIONMODECODE"
//                        + " AND BR.STATUS = ? ORDER BY BR.BATCHID ";
//            }

            if (!condition.equals("")) {
                strSqlBasic += " WHERE BR.CARDDOMAIN = CD.DOMAINID AND BR.CARDTYPE = CT.CARDTYPECODE AND BR.CARDPRODUCTCODE = CP.PRODUCTCODE AND "
                        + "BB.BANKCODE=(SELECT BANKCODE FROM COMMONPARAMETER) AND BR.BRANCHCODE = BB.BRANCHCODE AND BR.PRIORITYLEVEL = PL.PRIORITYLEVELCODE AND BR.STATUS = ST.STATUSCODE "
                        + "AND BR.CURRENCYCODE = CR.CURRENCYNUMCODE AND BR.PRODUCTIONMODE = PM.PRODUCTIONMODECODE"
                        + " AND " + condition + " AND BR.STATUS = ? ORDER BY BR.BATCHID ";
            } else {
                strSqlBasic += " WHERE BR.CARDDOMAIN = CD.DOMAINID AND BR.CARDTYPE = CT.CARDTYPECODE AND BR.CARDPRODUCTCODE = CP.PRODUCTCODE AND "
                        + "BB.BANKCODE=(SELECT BANKCODE FROM COMMONPARAMETER) AND BR.BRANCHCODE = BB.BRANCHCODE AND BR.PRIORITYLEVEL = PL.PRIORITYLEVELCODE AND BR.STATUS = ST.STATUSCODE "
                        + "AND BR.CURRENCYCODE = CR.CURRENCYNUMCODE AND BR.PRODUCTIONMODE = PM.PRODUCTIONMODECODE"
                        + " AND BR.STATUS = ? ORDER BY BR.BATCHID ";
            }

            stmt = con.prepareStatement(strSqlBasic);
            System.out.println(strSqlBasic);

            stmt.setString(1, StatusVarList.BULK_CARD_REQUEST_PENDING);

            rs = stmt.executeQuery();

            searchList = new ArrayList<BulkCardRequestBean>();

            while (rs.next()) {

                BulkCardRequestBean bean = new BulkCardRequestBean();

                bean.setBatchID(rs.getString("BATCHID"));
                bean.setCdDomain(rs.getString("CARDDOMAIN"));
                bean.setCdType(rs.getString("CARDTYPE"));
                bean.setCdProduct(rs.getString("CARDPRODUCTCODE"));
                bean.setBranchCode(rs.getString("BRANCHCODE"));
                bean.setPriorityLvl(rs.getString("PRIORITYLEVEL"));
                bean.setReqNumOfCds(rs.getString("REQUESTEDNOOFCARD"));
                bean.setApprvNumOfCds(rs.getString("APPROVEDNOOFCARD"));
                bean.setReqUser(rs.getString("REQUESTEDUSER"));
                bean.setApprvUser(rs.getString("APPROVEDUSER"));
                bean.setReqBranch(rs.getString("REQUESTUSERBRANCHCODE"));
                bean.setApprvBranch(rs.getString("APPROVEDUSERBRANCHCODE"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setCurrency(rs.getString("CURRENCYCODE"));
                bean.setCdBin(rs.getString("CURRENCYCODE"));
                bean.setProductMode(rs.getString("PRODUCTIONMODE"));
                bean.setRemark(rs.getString("REMARKS"));
                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setCdDomainDes(rs.getString("DOMDES"));
                bean.setCdTypeDes(rs.getString("CTYPDES"));
                bean.setCdProductDes(rs.getString("CPDES"));
                bean.setBranchName(rs.getString("BBNAME"));
                bean.setPriorityLvlDes(rs.getString("PLDES"));
                bean.setStatusDes(rs.getString("STDES"));
                bean.setCurrencyDes(rs.getString("CRDES"));
                bean.setProductModeDes(rs.getString("PMDES"));

                searchList.add(bean);
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
                throw e;
            }
        }

        return searchList;
    }

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));
    }

    public List<CardBinBean> getBinList(Connection con, String productionMode, String cardtype, String cardDomain, String currency, String product) throws Exception {
        PreparedStatement getAllByCatStat = null;
        ResultSet rs = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT cb.BIN, cb.DESCRIPTION "
                    + "FROM CARDBIN cb, CARDPRODUCTBIN cpb "
                    + "WHERE cb.CARDDOMAIN = ? "
                    + "AND cb.CARDTYPE = ? AND cb.ONUSSTATUS = ? AND cb.STATUS = ? AND cb.CURRENCYTYPECODE = ? "
                    + "AND cpb.PRODUCTCODE=? AND cpb.BINCODE=cb.BIN ");

          //  getAllByCatStat.setString(1, productionMode);
            getAllByCatStat.setString(1, cardDomain);
            getAllByCatStat.setString(2, cardtype);
            getAllByCatStat.setString(3, StatusVarList.YESSTATUS);
            getAllByCatStat.setString(4, StatusVarList.ACTIVE_STATUS);
            getAllByCatStat.setString(5, currency);
            getAllByCatStat.setString(6, product);

            cardBinList = new ArrayList<CardBinBean>();

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                CardBinBean bean = new CardBinBean();

                bean.setBinNumber(rs.getString("BIN"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                cardBinList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardBinList;
    }

    public List<CardTemplateBean> getTempltList(Connection con, String cdtype, String cddomain, String cdproduct, String currency) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT TEMPLATECODE,TEMPLATENAME FROM DEBITCARDTEMPLATE WHERE CARDTYPE = ? AND CARDDOMAIN = ? AND "
                    + "CURRENCYCODE = ? AND STATUS = ? ");

            ps.setString(1, cdtype);
            ps.setString(2, cddomain);
            //ps.setString(3, cdproduct);
            ps.setString(3, currency);
            ps.setString(4, StatusVarList.ACTIVE_STATUS);

            cardTempList = new ArrayList<CardTemplateBean>();

            rs = ps.executeQuery();

            while (rs.next()) {

                CardTemplateBean bean = new CardTemplateBean();

                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setTemplateName(rs.getString("TEMPLATENAME"));

                cardTempList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return cardTempList;
    }

     public String getUserBranch(Connection con, String username) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String branch="";
        try {
//            ps = con.prepareStatement("SELECT SU.BRANCHNAME, SU.BANKNAME, BB.BRANCHCODE FROM SYSTEMUSER SU, BANKBRANCH BB "
//                    + "WHERE USERNAME=? AND SU.BRANCHNAME=BB.DESCRPTION ");      
            
            ps = con.prepareStatement("SELECT BRANCHNAME FROM SYSTEMUSER WHERE USERNAME=? ");

            ps.setString(1, username);

            rs = ps.executeQuery();
            
            while (rs.next()) {
                branch=rs.getString("BRANCHNAME");
            }
            
        } catch (SQLException ex) {
            
            throw ex;
        } finally {
            ps.close();
        }
        return branch;
    }
    
    public boolean updateBulkCard(Connection con, BulkCardRequestBean bean) throws Exception {
        PreparedStatement ps = null;
        boolean result = false;
        try {
            ps = con.prepareStatement("UPDATE BULKCARDREQUEST SET APPROVEDNOOFCARD=?, APPROVEDUSER=?, APPROVEDUSERBRANCHCODE=?, LASTUPDATEDUSER=?, "
                    + "LASTUPDATEDTIME=SYSDATE, CARDBIN=?, TEMPLATECODE=?, CREDITLIMIT=?, STATUS=? WHERE BATCHID=? ");

            //APPROVEDUSERBRANCHCODE=?, should be added

            ps.setString(1, bean.getApprvNumOfCds());
            ps.setString(2, bean.getApprvUser());
            ps.setString(3, bean.getApprvBranch());
            ps.setString(4, bean.getLastUpdatedUser());
            ps.setString(5, bean.getCdBin());
            ps.setString(6, bean.getTemplateCode());
            ps.setString(7, bean.getCreditLimit());
            ps.setString(8, StatusVarList.BULK_CARD_REQUEST_CONFIRM);
            ps.setString(9, bean.getBatchID());

            ps.executeUpdate();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            ps.close();
        }
        return result;
    }

    public boolean rejectBulkCard(Connection con, BulkCardRequestBean bean) throws Exception {
        PreparedStatement ps = null;
        boolean result = false;
        try {
            ps = con.prepareStatement("UPDATE BULKCARDREQUEST SET LASTUPDATEDUSER=?, LASTUPDATEDTIME=SYSDATE, STATUS=?, REMARKS=? WHERE BATCHID=? ");

            ps.setString(1, bean.getLastUpdatedUser());
            ps.setString(2, StatusVarList.BULK_CARD_REQUEST_REJECT);
            ps.setString(3, bean.getRemark());
            ps.setString(4, bean.getBatchID());

            ps.executeUpdate();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            ps.close();
        }
        return result;
    }
}
