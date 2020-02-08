/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantlocation.persistance;

import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.bean.MerchantLocManualTxnBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.bean.MerchantLocationBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nalin
 */
public class MerchantLocationPersistance {

    private ResultSet rs;

    public List<MerchantLocationBean> getMerchantLocationDetails(Connection CMSCon) throws Exception {
        PreparedStatement getAllMerchant = null;
        List<MerchantLocationBean> beanList = new ArrayList<MerchantLocationBean>();
        try {
            getAllMerchant = CMSCon.prepareStatement("SELECT ML.MERCHANTID,"
                    + " ML.MERCHANTCUSTOMERNO, ML.DESCRIPTION,ML.ADDRESS1,ML.ADDRESS2,ML.ADDRESS3,"
                    + " ML.CITY, ML.COUNTRY,ML.TELEPHONE,ML.FAX,ML.EMAIL,"
                    + " ML.CONTACTPERSONTITLE, ML.CONTACTPERSONFIRSTNAME, ML.CONTACTPERSONMIDDLENAME,"
                    + " ML.CONTACTPERSONLASTNAME,ML.CONTACTPERSONPOSITON,"
                    + " ML.COMMITIONPROFILE, ML.FEEPROFILE,ML.RISKPROFILE,"
                    + " ML.ACTIVATIONDATE,ML.STATUS,"
                    + " ML.LASTUPDATEDTIME, ML.LASTUPDATEDUSER,ML.CREATEDTIME,"
                    + " MC.MERCHANTNAME,ML.CURRENCYCODE,"
                    + " ML.STATEMENTCYCLE,ML.PAYMENTCYCLE,ML.PAYMENTMODE,ML.STATEMENTMAINTEINANCESTATUS,"
                    + " ML.BANKCODE,ML.BRANCHNAME,ML.ACCOUNTNUMBER,"
                    + " ML.ACCOUNTTYPE,ML.ACCOUNTNAME,"
                    + " AR.DESCRIPTION AS ARDESCRIPTION, CUN.DESCRIPTION AS CUNDESCRIPTION,"
                    + " STAT.DESCRIPTION AS STATDESCRIPTION,R.DESCRIPTION AS RDESCRIPTION,"
                    + " F.DESCRIPTION AS FDESCRIPTION, BNK.BANKNAME,ML.MERCHANTACCOUNTNO, C.DESCRIPTION AS CDESCRIPTION,"
                    + " BRCH.DESCRPTION AS BRCHDESCRPTION,PM.DESCRIPTION AS PMDESCRIPTION,AT.DESCRIPTION AS ATDESCRIPTION,"
                    + " PC.DESCRIPTION AS PCDESCRIPTION,BC.DESCRIPTION AS BCDESCRIPTION,CP.DESCRIPTION AS CPDESCRIPTION,ML.REDEMPTIONPOINTVALUE"
                    + " FROM MERCHANTLOCATION ML,MERCHANTCUSTOMER MC,AREA AR,COUNTRY CUN,STATUS STAT,CURRENCY C,"
                    + " MERCHANTBILLINGCYCLE BC,MERCHANTPAYMENTCYCLE PC,PAYMENTMODE PM,COMMISSIONPROFILE CP,"
                    + " BANK BNK,BANKBRANCH BRCH,RISKPROFILE R,FEEPROFILE F,ACCOUNTTYPE AT"
                    + " WHERE ML.MERCHANTCUSTOMERNO=MC.MERCHANTCUSTOMERNO AND ML.CITY=AR.AREACODE"
                    + " AND ML.BANKCODE =BNK.BANKCODE AND BRCH.BANKCODE = BNK.BANKCODE"
                    + " AND ML.COUNTRY=CUN.COUNTRYALPHA3CODE AND ML.BRANCHNAME=BRCH.BRANCHCODE"
                    + " AND ML.RISKPROFILE = R.RISKPROFILECODE AND ML.FEEPROFILE = F.FEEPROFILECODE"
                    + " AND ML.PAYMENTCYCLE = PC.PAYMENTCYCLECODE AND ML.PAYMENTMODE = PM.PAYMENTMODECODE"
                    + " AND ML.COMMITIONPROFILE = CP.COMMISSIONPROFILECODE AND ML.ACCOUNTTYPE = AT.ACCOUNTTYPECODE"
                    + " AND ML.STATUS=STAT.STATUSCODE AND ML.STATEMENTCYCLE = BC.BILLINGCYCLECODE "
                    + " AND ML.CURRENCYCODE = C.CURRENCYNUMCODE");

            rs = getAllMerchant.executeQuery();

            while (rs.next()) {

                MerchantLocationBean bean = new MerchantLocationBean();

                bean.setMerchantId(rs.getString("MERCHANTID"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setMerchantCustomerNumber(rs.getString("MERCHANTCUSTOMERNO"));
                bean.setMerchantCustomerName(rs.getString("MERCHANTNAME"));
                bean.setAddress1(rs.getString("ADDRESS1"));
                bean.setAddress2(rs.getString("ADDRESS2"));
                bean.setAddress3(rs.getString("ADDRESS3"));
                bean.setArea(rs.getString("CITY"));
//                bean.setPostalCode(rs.getString("POSTALCODE"));
                bean.setCountry(rs.getString("COUNTRY"));
                bean.setTpNumber(rs.getString("TELEPHONE"));
                bean.setFax(rs.getString("FAX"));
                bean.seteMail(rs.getString("EMAIL"));
                bean.setCpTitle(rs.getString("CONTACTPERSONTITLE"));
                bean.setCpFirstName(rs.getString("CONTACTPERSONFIRSTNAME"));
                bean.setCpMiddleName(rs.getString("CONTACTPERSONMIDDLENAME"));
                bean.setCpLastName(rs.getString("CONTACTPERSONLASTNAME"));
                bean.setCpPosotion(rs.getString("CONTACTPERSONPOSITON"));
                bean.setCommissionProfile(rs.getString("COMMITIONPROFILE"));
                bean.setFeeProfile(rs.getString("FEEPROFILE"));
                bean.setRiskProfile(rs.getString("RISKPROFILE"));
                bean.setActivationDate(this.convertStringDate(rs.getDate("ACTIVATIONDATE")));
                bean.setStatus(rs.getString("STATUS"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                bean.setCreatTime(rs.getDate("CREATEDTIME"));
                bean.setAreaDescription(rs.getString("ARDESCRIPTION"));
                bean.setCountryDescription(rs.getString("CUNDESCRIPTION"));
                bean.setStatusDescription(rs.getString("STATDESCRIPTION"));
                bean.setFeeProfDes(rs.getString("FDESCRIPTION"));

                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBranchCode(rs.getString("BRANCHNAME"));
                bean.setAccountNumber(rs.getString("ACCOUNTNUMBER"));
                bean.setAccountType(rs.getString("ACCOUNTTYPE"));
                bean.setAccountName(rs.getString("ACCOUNTNAME"));

                bean.setPaymentMode(rs.getString("PAYMENTMODE"));
                bean.setPaymentCycle(rs.getString("PAYMENTCYCLE"));
                bean.setStatementCycle(rs.getString("STATEMENTCYCLE"));

                bean.setBankName(rs.getString("BANKNAME"));
                bean.setBranchName(rs.getString("BRCHDESCRPTION"));

                bean.setStatementCycleDes(rs.getString("BCDESCRIPTION"));
                bean.setPaymentCycleDes(rs.getString("PCDESCRIPTION"));
                bean.setPaymentModeDes(rs.getString("PMDESCRIPTION"));
                bean.setCommissionProfDes(rs.getString("CPDESCRIPTION"));
                bean.setStatementStatus(rs.getString("STATEMENTMAINTEINANCESTATUS"));
                bean.setAccountTypeDescription(rs.getString("ATDESCRIPTION"));
                bean.setRiskProfDes(rs.getString("RDESCRIPTION"));
                bean.setMerchantAccountNo(rs.getString("MERCHANTACCOUNTNO"));
                bean.setCurrencyType(this.zeroPadding(Integer.toString(rs.getInt("CURRENCYCODE"))));
                bean.setCurrencyTypeDes(rs.getString("CDESCRIPTION"));
                bean.setRedempoint(Double.toString(rs.getDouble("REDEMPTIONPOINTVALUE")));

                beanList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMerchant.close();

        }
        return beanList;
    }

    public List<MerchantCategoryBean> getMccByMerchantCustomerNumber(Connection CMSCon, String merchantCustomerNumber) throws SQLException, Exception {


        List<MerchantCategoryBean> merchantCategoryList = new ArrayList<MerchantCategoryBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT DISTINCT M.DESCRIPTION,M.MCCCODE,M.CLASS,M.STATUS,M.LASTUPDATEDUSER,M.LASTUPDATEDTIME,"
                    + " M.CREATEDTIME,S.DESCRIPTION "
                    + " FROM MCC M,STATUS S, MERCHANTCUSTOMERMCC MC WHERE M.STATUS = S.STATUSCODE AND MC.MCC = M.MCCCODE "
                    + " AND MC.MERCHANTUSTOMERNO=?";

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, merchantCustomerNumber);

            rs = ps.executeQuery();

            while (rs.next()) {
                MerchantCategoryBean merchantBean = new MerchantCategoryBean();

                merchantBean.setDescription(rs.getString(1));
                merchantBean.setmCCCode(rs.getString(2));
                merchantBean.setmClass(rs.getString(3));
                merchantBean.setStatus(rs.getString(4));
                merchantBean.setLastUpdateUser(rs.getString(5));
                merchantBean.setLastUpdateDate(rs.getDate(6));
                merchantBean.setCreateDate(rs.getDate(7));
                merchantBean.setStatusDes(rs.getString(8));

                merchantCategoryList.add(merchantBean);
            }

            return merchantCategoryList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public List<TypeMgtBean> getTxnTypeByMerchantCustomerNumber(Connection CMSCon, String merchantCustomerNumber) throws SQLException, Exception {


        List<TypeMgtBean> txnTypeList = new ArrayList<TypeMgtBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT DISTINCT T.DESCRIPTION,T.TRANSACTIONCODE "
                    + " FROM TRANSACTIONTYPE T,STATUS S,MERCHANTCUSTOMERTRANSACTION MT "
                    + " WHERE T.STATUS = S.STATUSCODE AND MT.TRANSACTIONCODE = T.TRANSACTIONCODE AND MT.MERCHANTCUSTOMERNO=? ";


            ps = CMSCon.prepareStatement(query);

            ps.setString(1, merchantCustomerNumber);


            rs = ps.executeQuery();

            while (rs.next()) {
                TypeMgtBean txnTypeBean = new TypeMgtBean();

                txnTypeBean.setDescription(rs.getString(1));
                txnTypeBean.setTransactionTypeCode(rs.getString(2));

                txnTypeList.add(txnTypeBean);
            }

            return txnTypeList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public List<CurrencyBean> getCurrencyByMerchantCustomerNumber(Connection CMSCon, String merchantCustomerNumber) throws SQLException, Exception {


        List<CurrencyBean> currencyList = new ArrayList<CurrencyBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT DISTINCT C.DESCRIPTION,C.CURRENCYNUMCODE "
                    + "FROM CURRENCY C,MERCHANTCUSTOMERCURRENCY MC WHERE MC.CURRENCYCODE = C.CURRENCYNUMCODE AND MC.MERCHANTCUSTOMERNO=? ";


            ps = CMSCon.prepareStatement(query);

            ps.setString(1, merchantCustomerNumber);

            rs = ps.executeQuery();

            while (rs.next()) {
                CurrencyBean currencyBean = new CurrencyBean();

                currencyBean.setCurrencyDes(rs.getString(1));
                currencyBean.setCurrencyCode(Integer.toString(rs.getInt(2)));

                currencyList.add(currencyBean);
            }

            return currencyList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public List<MerchantLocationBean> searchMerchantLocationDetails(MerchantLocationBean merchantLocBean, Connection CMSCon) throws Exception {
        PreparedStatement searchAllMerchant = null;
        List<MerchantLocationBean> beanList = new ArrayList<MerchantLocationBean>();
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT ML.MERCHANTID,ML.DESCRIPTION, MC.MERCHANTNAME,ML.MERCHANTCUSTOMERNO,"
                    + " ML.CITY , AR.DESCRIPTION AS ARDESCRIPTION "
                    + " FROM MERCHANTLOCATION ML, MERCHANTCUSTOMER MC,AREA AR "
                    + " WHERE  ML.CITY=AR.AREACODE AND ML.MERCHANTCUSTOMERNO = MC.MERCHANTCUSTOMERNO "
                    + " AND ML.MERCHANTID NOT IN (SELECT  MERCHANTID FROM COMMONPARAMETER ) ";

            String condition = "";

            if (!merchantLocBean.getMerchantId().contentEquals("") || merchantLocBean.getMerchantId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " ML.MERCHANTID LIKE '%" + merchantLocBean.getMerchantId() + "%'";
            }

            if (!merchantLocBean.getDescription().contentEquals("") || merchantLocBean.getDescription().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(ML.DESCRIPTION) LIKE '%" + merchantLocBean.getDescription().toUpperCase() + "%'";
            }

            if (!merchantLocBean.getMerchantCustomerName().contentEquals("") || merchantLocBean.getMerchantCustomerName().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(MC.MERCHANTNAME) LIKE '%" + merchantLocBean.getMerchantCustomerName().toUpperCase() + "%'";
            }

            if (!merchantLocBean.getArea().contentEquals("") || merchantLocBean.getArea().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " ML.CITY = " + merchantLocBean.getArea() + " ";
            }

            if (!condition.equals("")) {
                strSqlBasic += " AND " + condition + " ORDER BY ML.MERCHANTID ";
            } else {

                strSqlBasic += " ORDER BY ML.MERCHANTID";
            }

            searchAllMerchant = CMSCon.prepareStatement(strSqlBasic);
            rs = searchAllMerchant.executeQuery();

            while (rs.next()) {

                MerchantLocationBean bean = new MerchantLocationBean();

                bean.setMerchantId(rs.getString("MERCHANTID"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setMerchantCustomerName(rs.getString("MERCHANTNAME"));
                bean.setMerchantCustomerNumber(rs.getString("MERCHANTCUSTOMERNO"));
                bean.setArea(rs.getString("CITY"));
                bean.setAreaDescription(rs.getString("ARDESCRIPTION"));

                beanList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            searchAllMerchant.close();

        }
        return beanList;

    }

    public boolean insertMerchantLocation(Connection CMSCon, MerchantLocationBean merchantLocBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO MERCHANTLOCATION(MERCHANTID,MERCHANTCUSTOMERNO,DESCRIPTION,"
                    + "ADDRESS1,ADDRESS2,ADDRESS3,CITY, COUNTRY ,TELEPHONE,FAX,EMAIL,CONTACTPERSONTITLE,"
                    + "CONTACTPERSONFIRSTNAME,CONTACTPERSONMIDDLENAME,CONTACTPERSONLASTNAME,CONTACTPERSONPOSITON,"
                    + "LASTUPDATEDUSER,COMMITIONPROFILE,"
                    + "FEEPROFILE,RISKPROFILE,"
                    + "ACTIVATIONDATE,STATUS,BANKCODE,BRANCHNAME,ACCOUNTNUMBER,ACCOUNTTYPE,ACCOUNTNAME,"
                    + "PAYMENTMODE,PAYMENTCYCLE,STATEMENTCYCLE,STATEMENTMAINTEINANCESTATUS,MERCHANTACCOUNTNO,CURRENCYCODE,"
                    + "REDEMPTIONPOINTVALUE,LASTUPDATEDTIME,CREATEDTIME)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE)");


            insertStat.setString(1, merchantLocBean.getMerchantId());
            insertStat.setString(2, merchantLocBean.getMerchantCustomerNumber());
            insertStat.setString(3, merchantLocBean.getDescription());
            insertStat.setString(4, merchantLocBean.getAddress1());
            insertStat.setString(5, merchantLocBean.getAddress2());
            insertStat.setString(6, merchantLocBean.getAddress3());
            insertStat.setString(7, merchantLocBean.getArea());
//            insertStat.setString(8, merchantLocBean.getPostalCode());
            insertStat.setString(8, merchantLocBean.getCountry());
            insertStat.setString(9, merchantLocBean.getTpNumber());
            insertStat.setString(10, merchantLocBean.getFax());
            insertStat.setString(11, merchantLocBean.geteMail());
            insertStat.setString(12, merchantLocBean.getCpTitle());
            insertStat.setString(13, merchantLocBean.getCpFirstName());
            insertStat.setString(14, merchantLocBean.getCpMiddleName());
            insertStat.setString(15, merchantLocBean.getCpLastName());
            insertStat.setString(16, merchantLocBean.getCpPosotion());
            insertStat.setString(17, merchantLocBean.getLastUpdatedUser());
            insertStat.setString(18, merchantLocBean.getCommissionProfile());
            insertStat.setString(19, merchantLocBean.getFeeProfile());
            insertStat.setString(20, merchantLocBean.getRiskProfile());
            insertStat.setTimestamp(21, new Timestamp(this.stringTodate(merchantLocBean.getActivationDate()).getTime()));
            insertStat.setString(22, merchantLocBean.getStatus());

            insertStat.setString(23, merchantLocBean.getBankCode());
            insertStat.setString(24, merchantLocBean.getBranchCode());
            insertStat.setString(25, merchantLocBean.getAccountNumber());
            insertStat.setString(26, merchantLocBean.getAccountType());
            insertStat.setString(27, merchantLocBean.getAccountName());

            insertStat.setString(28, merchantLocBean.getPaymentMode());
            insertStat.setString(29, merchantLocBean.getPaymentCycle());
            insertStat.setString(30, merchantLocBean.getStatementCycle());
            insertStat.setString(31, merchantLocBean.getStatementStatus());
            insertStat.setString(32, merchantLocBean.getMerchantAccountNo());
            insertStat.setInt(33, Integer.parseInt(merchantLocBean.getCurrencyType()));
            insertStat.setDouble(34, Double.parseDouble(merchantLocBean.getRedempoint()));



            insertStat.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;

    }

    public boolean insertToMcc(Connection CMSCon, String merchantId, String merchantCatogoryCode, String lastUpdateUser) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO MERCHANTLOCATIONMCC(MERCHANTID,MCC,LASTUPDATEDUSER,"
                    + " LASTUPDATEDTIME,CREATEDTIME) VALUES(?,?,?,SYSDATE,SYSDATE) ");
            insertStat.setString(1, merchantId);
            insertStat.setString(2, merchantCatogoryCode);
            insertStat.setString(3, lastUpdateUser);

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean checkMerchantLocationExsistance(Connection CMSCon, String merchantId) throws Exception {

        boolean isExsist = false;
        PreparedStatement checkStat = null;
        try {
            checkStat = CMSCon.prepareStatement(" SELECT DESCRIPTION FROM MERCHANTLOCATION"
                    + " WHERE MERCHANTID = ? ");


            checkStat.setString(1, merchantId);
            rs = checkStat.executeQuery();

            while (rs.next()) {

                isExsist = true;
            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            checkStat.close();

        }

        return isExsist;

    }

    public boolean insertToTxnType(Connection CMSCon, String merchantId, String txnTypeCode, String lastUpdateUser) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO MERCHANTLOCATIONTRANSACTION(MERCHANTID,TRANSACTIONCODE,LASTUPDATEDUSER,"
                    + " LASTUPDATEDTIME,CREATEDTIME) VALUES(?,?,?,SYSDATE,SYSDATE) ");
            insertStat.setString(1, merchantId);
            insertStat.setString(2, txnTypeCode);
            insertStat.setString(3, lastUpdateUser);

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean insertToCurrency(Connection CMSCon, String merchantId, String currencyCode, String lastUpdateUser) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO MERCHANTLOCATIONCURRENCY(MERCHANTID,CURRENCYCODE,LASTUPDATEDUSER,"
                    + " LASTUPDATEDTIME,CREATEDTIME)"
                    + " VALUES(?,?,?,SYSDATE,SYSDATE) ");
            insertStat.setString(1, merchantId);
            insertStat.setInt(2, Integer.parseInt(currencyCode));
            insertStat.setString(3, lastUpdateUser);

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public List<MerchantCategoryBean> getNotAssignedMccList(Connection CMSCon, String merchantId, String merchantCustomerNumber) throws SQLException, Exception {


        List<MerchantCategoryBean> merchantCategoryList = new ArrayList<MerchantCategoryBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT DISTINCT M.DESCRIPTION,M.MCCCODE,M.CLASS,M.STATUS,M.LASTUPDATEDUSER,M.LASTUPDATEDTIME,M.CREATEDTIME,S.DESCRIPTION "
                    + "FROM MCC M,STATUS S, MERCHANTCUSTOMERMCC MC WHERE M.STATUS = S.STATUSCODE AND MC.MCC = M.MCCCODE "
                    + "AND MC.MERCHANTUSTOMERNO=? AND M.MCCCODE NOT IN (SELECT MCC FROM MERCHANTLOCATIONMCC WHERE MERCHANTID = ?)";

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, merchantCustomerNumber);
            ps.setString(2, merchantId);
            rs = ps.executeQuery();

            while (rs.next()) {
                MerchantCategoryBean merchantBean = new MerchantCategoryBean();

                merchantBean.setDescription(rs.getString(1));
                merchantBean.setmCCCode(rs.getString(2));
                merchantBean.setmClass(rs.getString(3));
                merchantBean.setStatus(rs.getString(4));
                merchantBean.setLastUpdateUser(rs.getString(5));
                merchantBean.setLastUpdateDate(rs.getDate(6));
                merchantBean.setCreateDate(rs.getDate(7));
                merchantBean.setStatusDes(rs.getString(8));

                merchantCategoryList.add(merchantBean);
            }

            return merchantCategoryList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public List<MerchantCategoryBean> getAssignedMccList(Connection CMSCon, String merchantId) throws SQLException, Exception {


        List<MerchantCategoryBean> merchantCategoryList = new ArrayList<MerchantCategoryBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT MCC FROM MERCHANTLOCATIONMCC WHERE MERCHANTID = ? ";


            ps = CMSCon.prepareStatement(query);
            ps.setString(1, merchantId);
            rs = ps.executeQuery();

            while (rs.next()) {
                MerchantCategoryBean merchantBean = new MerchantCategoryBean();


                merchantBean.setmCCCode(rs.getString(1));



                merchantCategoryList.add(merchantBean);
            }

            return merchantCategoryList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public List<TypeMgtBean> getNotAssignedTxnTypeList(Connection CMSCon, String merchantId, String merchantCustomerNumber) throws SQLException, Exception {


        List<TypeMgtBean> txnTypeList = new ArrayList<TypeMgtBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT DISTINCT T.DESCRIPTION,T.TRANSACTIONCODE "
                    + "FROM TRANSACTIONTYPE T,STATUS S,MERCHANTCUSTOMERTRANSACTION MT WHERE T.STATUS = S.STATUSCODE AND MT.TRANSACTIONCODE = T.TRANSACTIONCODE AND MT.MERCHANTCUSTOMERNO=?  "
                    + "AND T.TRANSACTIONCODE NOT IN (SELECT TRANSACTIONCODE FROM MERCHANTLOCATIONTRANSACTION WHERE MERCHANTID = ?)";


            ps = CMSCon.prepareStatement(query);

            ps.setString(1, merchantCustomerNumber);
            ps.setString(2, merchantId);

            rs = ps.executeQuery();

            while (rs.next()) {
                TypeMgtBean txnTypeBean = new TypeMgtBean();

                txnTypeBean.setDescription(rs.getString(1));
                txnTypeBean.setTransactionTypeCode(rs.getString(2));

                txnTypeList.add(txnTypeBean);
            }

            return txnTypeList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public List<TypeMgtBean> getAssignedTxnTypeList(Connection CMSCon, String merchantId) throws SQLException, Exception {


        List<TypeMgtBean> txnTypeList = new ArrayList<TypeMgtBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT TRANSACTIONCODE FROM MERCHANTLOCATIONTRANSACTION WHERE MERCHANTID = ? ";

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, merchantId);
            rs = ps.executeQuery();

            while (rs.next()) {
                TypeMgtBean txnTypeBean = new TypeMgtBean();

                txnTypeBean.setTransactionTypeCode(rs.getString(1));

                txnTypeList.add(txnTypeBean);
            }

            return txnTypeList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public List<CurrencyBean> getNotAssignedCurrencyList(Connection CMSCon, String merchantId, String merchantCustomerNumber) throws SQLException, Exception {


        List<CurrencyBean> currencyList = new ArrayList<CurrencyBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT DISTINCT C.DESCRIPTION,C.CURRENCYNUMCODE "
                    + " FROM CURRENCY C,MERCHANTCUSTOMERCURRENCY MC WHERE MC.MERCHANTCUSTOMERNO=? AND MC.CURRENCYCODE = C.CURRENCYNUMCODE"
                    + " AND C.CURRENCYNUMCODE NOT IN (SELECT CURRENCYCODE FROM MERCHANTLOCATIONCURRENCY WHERE MERCHANTID = ?)";


            ps = CMSCon.prepareStatement(query);

            ps.setString(1, merchantCustomerNumber);
            ps.setString(2, merchantId);

            rs = ps.executeQuery();

            while (rs.next()) {
                CurrencyBean currencyBean = new CurrencyBean();

                currencyBean.setCurrencyDes(rs.getString(1));
                currencyBean.setCurrencyCode(Integer.toString(rs.getInt(2)));

                currencyList.add(currencyBean);
            }

            return currencyList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public List<CurrencyBean> getAssignedCurrencyList(Connection CMSCon, String merchantId) throws SQLException, Exception {


        List<CurrencyBean> currencyList = new ArrayList<CurrencyBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT CURRENCYCODE FROM MERCHANTLOCATIONCURRENCY WHERE MERCHANTID = ? ";

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, merchantId);
            rs = ps.executeQuery();

            while (rs.next()) {
                CurrencyBean currencyBean = new CurrencyBean();

                currencyBean.setCurrencyCode(Integer.toString(rs.getInt(1)));

                currencyList.add(currencyBean);
            }

            return currencyList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public MerchantCategoryBean getMccDescriptionByCode(Connection con, String mccCode) throws Exception {

        MerchantCategoryBean bean = null;
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT DESCRIPTION FROM MCC WHERE MCCCODE = ? ");

            getAllByCatStat.setString(1, mccCode);


            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                bean = new MerchantCategoryBean();
                bean.setmCCCode(mccCode);
                bean.setDescription(rs.getString("DESCRIPTION"));


            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return bean;
    }

    public TypeMgtBean getTxnDescriptionByCode(Connection con, String txnTypeCode) throws Exception {

        TypeMgtBean bean = null;
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT DESCRIPTION FROM TRANSACTIONTYPE WHERE TRANSACTIONCODE = ? ");

            getAllByCatStat.setString(1, txnTypeCode);


            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                bean = new TypeMgtBean();
                bean.setTransactionTypeCode(txnTypeCode);
                bean.setDescription(rs.getString("DESCRIPTION"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return bean;
    }

    public CurrencyBean getCurrencyDescriptionByCode(Connection con, String currencyCode) throws Exception {

        CurrencyBean bean = null;
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT DESCRIPTION FROM CURRENCY WHERE CURRENCYNUMCODE = ? ");

            getAllByCatStat.setInt(1, Integer.parseInt(currencyCode));


            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                bean = new CurrencyBean();
                bean.setCurrencyCode(currencyCode);
                bean.setCurrencyDes(rs.getString("DESCRIPTION"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return bean;
    }

    public boolean deleteMerchantLocation(Connection CMSCon, MerchantLocationBean merchantLocBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = CMSCon.prepareStatement("DELETE MERCHANTLOCATION WHERE MERCHANTID=?");

            deleteStat.setString(1, merchantLocBean.getMerchantId());


            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteStat.close();
        }
        return success;
    }

    public boolean deleteMerchantLocationMcc(Connection CMSCon, MerchantLocationBean merchantLocBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = CMSCon.prepareStatement("DELETE MERCHANTLOCATIONMCC WHERE MERCHANTID=?");

            deleteStat.setString(1, merchantLocBean.getMerchantId());


            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteStat.close();
        }
        return success;
    }

    public boolean deleteMerchantLocationTxn(Connection CMSCon, MerchantLocationBean merchantLocBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = CMSCon.prepareStatement("DELETE MERCHANTLOCATIONTRANSACTION WHERE MERCHANTID=?");

            deleteStat.setString(1, merchantLocBean.getMerchantId());


            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteStat.close();
        }
        return success;
    }

    public boolean deleteMerchantLocationCurrency(Connection CMSCon, MerchantLocationBean merchantLocBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = CMSCon.prepareStatement("DELETE MERCHANTLOCATIONCURRENCY WHERE MERCHANTID=?");

            deleteStat.setString(1, merchantLocBean.getMerchantId());


            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteStat.close();
        }
        return success;
    }

    public boolean deleteMerchantLocationOnline(Connection CMSConOnline, MerchantLocationBean merchantLocBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = CMSConOnline.prepareStatement("DELETE ECMS_ONLINE_MERCHANT WHERE MID=?");

            deleteStat.setString(1, merchantLocBean.getMerchantId());


            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteStat.close();
        }
        return success;
    }

    public boolean deleteMerchantLocationManualTerminalTxnOnline(Connection CMSConOnline, String mId, String tId) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = CMSConOnline.prepareStatement("DELETE ECMS_ONLINE_TERMINAL_TXN WHERE MID=? AND TID = ?");

            deleteStat.setString(1, mId);
            deleteStat.setString(2, tId);


            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteStat.close();
        }
        return success;
    }

    public boolean deleteMerchantLocationManualTerminalOnline(Connection CMSConOnline, String mId, String tId) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = CMSConOnline.prepareStatement("DELETE ECMS_ONLINE_TERMINAL WHERE MID=? AND TID = ?");

            deleteStat.setString(1, mId);
            deleteStat.setString(2, tId);


            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteStat.close();
        }
        return success;
    }

    public boolean deleteMerchantLocationManualTerminalTxn(Connection CMSCon, String mId, String tId) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = CMSCon.prepareStatement("DELETE TERMINALTXN WHERE MERCHANTID=? AND TERMINALID = ?");

            deleteStat.setString(1, mId);
            deleteStat.setString(2, tId);


            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteStat.close();
        }
        return success;
    }

    public boolean deleteMerchantLocationManualTerminal(Connection CMSCon, String mId, String tId) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = CMSCon.prepareStatement("DELETE TERMINAL WHERE MERCHANTID=? AND TERMINALID = ?");

            deleteStat.setString(1, mId);
            deleteStat.setString(2, tId);


            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteStat.close();
        }
        return success;
    }

    public boolean updateMerchantLocation(Connection CMSCon, MerchantLocationBean merchantLocBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = CMSCon.prepareStatement("UPDATE MERCHANTLOCATION SET MERCHANTCUSTOMERNO=?,DESCRIPTION=?,ADDRESS1=?,ADDRESS2=?,"
                    + " ADDRESS3=?,CITY=?,COUNTRY=?,TELEPHONE=?,FAX=?,EMAIL=?,CONTACTPERSONTITLE=?,CONTACTPERSONFIRSTNAME=?,"
                    + " CONTACTPERSONMIDDLENAME=?,CONTACTPERSONLASTNAME=?,CONTACTPERSONPOSITON=?,LASTUPDATEDUSER=?,COMMITIONPROFILE=?,FEEPROFILE=?,"
                    + " RISKPROFILE=?,"
                    + " ACTIVATIONDATE=?,STATUS=?,BANKCODE=?,BRANCHNAME=?,ACCOUNTNUMBER=?,ACCOUNTTYPE=?,ACCOUNTNAME=?,"
                    + " PAYMENTMODE=?,PAYMENTCYCLE=?,STATEMENTCYCLE=?,STATEMENTMAINTEINANCESTATUS=?, CURRENCYCODE = ?,"
                    + " LASTUPDATEDTIME=SYSDATE,CREATEDTIME=SYSDATE,REDEMPTIONPOINTVALUE=? "
                    + " WHERE MERCHANTID=?");



            updateStat.setString(1, merchantLocBean.getMerchantCustomerNumber());
            updateStat.setString(2, merchantLocBean.getDescription());
            updateStat.setString(3, merchantLocBean.getAddress1());
            updateStat.setString(4, merchantLocBean.getAddress2());
            updateStat.setString(5, merchantLocBean.getAddress3());
            updateStat.setString(6, merchantLocBean.getArea());
//            updateStat.setString(7, merchantLocBean.getPostalCode());
            updateStat.setString(7, merchantLocBean.getCountry());
            updateStat.setString(8, merchantLocBean.getTpNumber());
            updateStat.setString(9, merchantLocBean.getFax());
            updateStat.setString(10, merchantLocBean.geteMail());
            updateStat.setString(11, merchantLocBean.getCpTitle());
            updateStat.setString(12, merchantLocBean.getCpFirstName());
            updateStat.setString(13, merchantLocBean.getCpMiddleName());
            updateStat.setString(14, merchantLocBean.getCpLastName());
            updateStat.setString(15, merchantLocBean.getCpPosotion());
            updateStat.setString(16, merchantLocBean.getLastUpdatedUser());
            updateStat.setString(17, merchantLocBean.getCommissionProfile());
            updateStat.setString(18, merchantLocBean.getFeeProfile());
            updateStat.setString(19, merchantLocBean.getRiskProfile());
            updateStat.setTimestamp(20, new Timestamp(this.stringTodate(merchantLocBean.getActivationDate()).getTime()));
            updateStat.setString(21, merchantLocBean.getStatus());

            updateStat.setString(22, merchantLocBean.getBankCode());
            updateStat.setString(23, merchantLocBean.getBranchCode());
            updateStat.setString(24, merchantLocBean.getAccountNumber());
            updateStat.setString(25, merchantLocBean.getAccountType());
            updateStat.setString(26, merchantLocBean.getAccountName());

            updateStat.setString(27, merchantLocBean.getPaymentMode());
            updateStat.setString(28, merchantLocBean.getPaymentCycle());
            updateStat.setString(29, merchantLocBean.getStatementCycle());
            updateStat.setString(30, merchantLocBean.getStatementStatus());
            updateStat.setInt(31, Integer.parseInt(merchantLocBean.getCurrencyType()));
            updateStat.setDouble(32, Double.parseDouble(merchantLocBean.getRedempoint()));

            updateStat.setString(33, merchantLocBean.getMerchantId());


            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;

    }

    public boolean findExistanceOfMcc(Connection con, String merchantId, String mcc) throws Exception {
        boolean isExist = false;
        PreparedStatement findStatement = null;
        try {

            findStatement = con.prepareStatement("SELECT MC.MERCHANTID, MC.MCC FROM MERCHANTLOCATIONMCC MC WHERE MC.MERCHANTID=? AND MC.MCC=? ");

            //set parameters for select
            findStatement.setString(1, merchantId);
            findStatement.setString(2, mcc);


            rs = findStatement.executeQuery();
            while (rs.next()) {
                isExist = true;

            }

            return isExist;

        } catch (SQLException ex) {

            throw ex;
        } finally {

            findStatement.close();
        }

    }

    public boolean findExistanceOfTxn(Connection con, String merchantId, String txn) throws Exception {
        boolean isExist = false;
        PreparedStatement findStatement = null;
        try {

            findStatement = con.prepareStatement("SELECT MC.MERCHANTID,MC.TRANSACTIONCODE FROM MERCHANTLOCATIONTRANSACTION MC"
                    + " WHERE MC.MERCHANTID=? AND MC.TRANSACTIONCODE=? ");

            //set parameters for select
            findStatement.setString(1, merchantId);
            findStatement.setString(2, txn);


            rs = findStatement.executeQuery();
            while (rs.next()) {
                isExist = true;

            }

            return isExist;

        } catch (SQLException ex) {

            throw ex;
        } finally {

            findStatement.close();
        }

    }

    public boolean findExistanceOfCurrency(Connection con, String merchantId, String currency) throws Exception {
        boolean isExist = false;
        PreparedStatement findStatement = null;
        try {

            findStatement = con.prepareStatement("SELECT MC.MERCHANTID,MC.CURRENCYCODE FROM MERCHANTLOCATIONCURRENCY MC"
                    + " WHERE MC.MERCHANTID=? AND MC.CURRENCYCODE=? ");

            //set parameters for select
            findStatement.setString(1, merchantId);
            findStatement.setInt(2, Integer.parseInt(currency));


            rs = findStatement.executeQuery();
            while (rs.next()) {
                isExist = true;

            }

            return isExist;

        } catch (SQLException ex) {

            throw ex;
        } finally {

            findStatement.close();
        }

    }

    public Boolean deleteMerchantMccByMcc(Connection con, String mcc, String merchantId) throws Exception {
        Boolean status = false;
        PreparedStatement updateStat = null;
        try {

            updateStat = con.prepareStatement("DELETE FROM MERCHANTLOCATIONMCC WHERE MERCHANTID = ? AND MCC=?");

            updateStat.setString(1, merchantId);
            updateStat.setString(2, mcc);

            updateStat.executeUpdate();

            status = true;
        } catch (SQLException ex) {
            status = false;
            throw ex;
        } finally {
            updateStat.close();
        }
        return status;
    }

    public Boolean deleteMerchantTxnByTxnCode(Connection con, String txn, String merchantId) throws Exception {
        Boolean status = false;
        PreparedStatement updateStat = null;
        try {

            updateStat = con.prepareStatement("DELETE FROM MERCHANTLOCATIONTRANSACTION WHERE MERCHANTID = ? AND TRANSACTIONCODE=?");

            updateStat.setString(1, merchantId);
            updateStat.setString(2, txn);

            updateStat.executeUpdate();

            status = true;
        } catch (SQLException ex) {
            status = false;
            throw ex;
        } finally {
            updateStat.close();
        }
        return status;
    }

    public Boolean deleteMerchantCurrencyByCurrencyCode(Connection con, String currency, String merchantId) throws Exception {
        Boolean status = false;
        PreparedStatement updateStat = null;
        try {

            updateStat = con.prepareStatement("DELETE FROM MERCHANTLOCATIONCURRENCY WHERE MERCHANTID = ? AND CURRENCYCODE=?");

            updateStat.setString(1, merchantId);
            updateStat.setInt(2, Integer.parseInt(currency));

            updateStat.executeUpdate();

            status = true;
        } catch (SQLException ex) {
            status = false;
            throw ex;
        } finally {
            updateStat.close();
        }
        return status;
    }

    public Boolean insertMerchantOnline(Connection conOnline, MerchantLocationBean merchantLocBean, String countryNumCode) throws SQLException {
        Boolean isSuccess = false;
        PreparedStatement insertStat = null;

        try {
            insertStat = conOnline.prepareStatement("INSERT INTO ECMS_ONLINE_MERCHANT(MID,STATUS,MNAME,"
                    + " COUNTRYCODE,RISKPROFILECODE,"
                    + " LASTUPDATEUSER,CURRENCYNOCODE,CREATETIME,LASTUPDATETIME)"
                    + " VALUES(?,?,?,?,?,?,?,SYSDATE,SYSDATE)");


            insertStat.setString(1, merchantLocBean.getMerchantId());
            insertStat.setInt(2, merchantLocBean.getStatusToOnline());
            insertStat.setString(3, merchantLocBean.getDescription());
            insertStat.setString(4, this.zeroPadding(countryNumCode));
            insertStat.setString(5, merchantLocBean.getRiskProfile());
            insertStat.setString(6, merchantLocBean.getLastUpdatedUser());
            insertStat.setString(7, merchantLocBean.getCurrencyType());


            insertStat.executeUpdate();
            isSuccess = true;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }

        return isSuccess;

    }

    public boolean insertToMccOnline(Connection conOnline, String merchantId, String merchantCatogoryCode) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = conOnline.prepareStatement("INSERT INTO ECMS_ONLINE_MERCHANT_MCC(MID,MCC) VALUES(?,?) ");

            insertStat.setString(1, merchantId);
            insertStat.setString(2, merchantCatogoryCode);

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean insertToTxnTypeOnline(Connection conOnline, String merchantId, int txnTypeCode) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = conOnline.prepareStatement("INSERT INTO ECMS_ONLINE_MERCHANT_TXN(MID,TXNTYPECODE) VALUES(?,?) ");
            insertStat.setString(1, merchantId);
            insertStat.setInt(2, txnTypeCode);

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean updateMerchantLocationOnline(Connection conOnline, MerchantLocationBean merchantLocBean, String countryNumCode) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = conOnline.prepareStatement("UPDATE ECMS_ONLINE_MERCHANT SET STATUS=?,MNAME=?,"
                    + " LASTUPDATETIME=SYSDATE,LASTUPDATEUSER=?,COUNTRYCODE=?,RISKPROFILECODE=?, CURRENCYNOCODE = ? "
                    + " WHERE MID=?");




            updateStat.setInt(1, merchantLocBean.getStatusToOnline());
            updateStat.setString(2, merchantLocBean.getDescription());
            updateStat.setString(3, merchantLocBean.getLastUpdatedUser());
            updateStat.setString(4, countryNumCode);
            updateStat.setString(5, merchantLocBean.getRiskProfile());
            updateStat.setString(6, merchantLocBean.getCurrencyType());
            updateStat.setString(7, merchantLocBean.getMerchantId());


            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;

    }

    public boolean findExistanceOfMccOnline(Connection conOnline, String merchantId, String mcc) throws Exception {
        boolean isExist = false;
        PreparedStatement findStatement = null;
        try {

            findStatement = conOnline.prepareStatement("SELECT MC.MID, MC.MCC FROM ECMS_ONLINE_MERCHANT_MCC MC"
                    + " WHERE MC.MID=? AND MC.MCC=? ");

            //set parameters for select
            findStatement.setString(1, merchantId);
            findStatement.setString(2, mcc);


            rs = findStatement.executeQuery();
            while (rs.next()) {
                isExist = true;

            }

            return isExist;

        } catch (SQLException ex) {

            throw ex;
        } finally {

            findStatement.close();
        }

    }

    public boolean findExistanceOfTxnOnline(Connection conOnline, String merchantId, int txn) throws Exception {
        boolean isExist = false;
        PreparedStatement findStatement = null;
        try {

            findStatement = conOnline.prepareStatement("SELECT MC.MID,MC.TXNTYPECODE FROM ECMS_ONLINE_MERCHANT_TXN MC"
                    + " WHERE MC.MID=? AND MC.TXNTYPECODE=? ");

            //set parameters for select
            findStatement.setString(1, merchantId);
            findStatement.setInt(2, txn);


            rs = findStatement.executeQuery();
            while (rs.next()) {
                isExist = true;

            }

            return isExist;

        } catch (SQLException ex) {

            throw ex;
        } finally {

            findStatement.close();
        }

    }

    public Boolean deleteMerchantMccByMccOnline(Connection conOnline, String mcc, String merchantId) throws Exception {
        Boolean status = false;
        PreparedStatement updateStat = null;
        try {

            updateStat = conOnline.prepareStatement("DELETE FROM ECMS_ONLINE_MERCHANT_MCC WHERE MID = ? AND MCC=?");

            updateStat.setString(1, merchantId);
            updateStat.setString(2, mcc);

            updateStat.executeUpdate();

            status = true;
        } catch (SQLException ex) {
            status = false;
            throw ex;
        } finally {
            updateStat.close();
        }
        return status;
    }

    public Boolean deleteMerchantTxnByTxnCodeOnline(Connection conOnline, int txn, String merchantId) throws Exception {
        Boolean status = false;
        PreparedStatement updateStat = null;
        try {

            updateStat = conOnline.prepareStatement(" DELETE FROM ECMS_ONLINE_MERCHANT_TXN WHERE MID =? AND TXNTYPECODE=? ");

            updateStat.setString(1, merchantId);
            updateStat.setInt(2, txn);

            updateStat.executeUpdate();

            status = true;
        } catch (SQLException ex) {
            status = false;
            throw ex;
        } finally {
            updateStat.close();
        }
        return status;
    }

    public int getOnlineTxnCode(Connection con, String txnTypeCode) throws SQLException {


        int onlineTxnCode = 0;
        PreparedStatement findStatement = null;
        try {

            findStatement = con.prepareStatement("SELECT T.ONLINETXNTYPE FROM TRANSACTIONTYPE T"
                    + " WHERE T.TRANSACTIONCODE=? ");

            //set parameters for select
            findStatement.setString(1, txnTypeCode);


            rs = findStatement.executeQuery();
            while (rs.next()) {

                onlineTxnCode = rs.getInt("ONLINETXNTYPE");

            }

            return onlineTxnCode;

        } catch (SQLException ex) {

            throw ex;
        } finally {

            findStatement.close();
        }

    }

    public String getMaxAccnoFromMerchantLocation(Connection con) throws Exception {

        PreparedStatement getMaxCusIdState = null;
        String maxAcc = null;
        try {
            getMaxCusIdState = con.prepareStatement("SELECT COALESCE(MAX(ML.MERCHANTACCOUNTNO),'0') AS MAXACCOUNTNO"
                    + " FROM MERCHANTLOCATION ML");

            rs = getMaxCusIdState.executeQuery();

            while (rs.next()) {

                maxAcc = rs.getString("MAXACCOUNTNO");

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getMaxCusIdState.close();

        }

        return maxAcc;
    }

    public List<RiskProfileBean> getTerminalRiskProfile(Connection con, String rpType, String merchantId) throws Exception {

        PreparedStatement getRPStat = null;
        List<RiskProfileBean> riskProfList = new ArrayList<RiskProfileBean>();
        try {
            getRPStat = con.prepareStatement("SELECT R.RISKPROFILECODE,R.DESCRIPTION "
                    + " FROM RISKPROFILE R WHERE R.PROFILETYPE=? AND R.MERCHANTPROFILECODE = "
                    + " (SELECT RISKPROFILE FROM MERCHANTLOCATION WHERE MERCHANTID = ?)");

            getRPStat.setString(1, rpType);
            getRPStat.setString(2, merchantId);
            rs = getRPStat.executeQuery();

            while (rs.next()) {

                RiskProfileBean bean = new RiskProfileBean();

                bean.setRiskProfCode(rs.getString("RISKPROFILECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                riskProfList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getRPStat.close();

        }
        return riskProfList;
    }

    public List<MerchantCategoryBean> getMccOfMerchant(Connection CMSCon, String merchantId) throws Exception {
        PreparedStatement getStat = null;
        List<MerchantCategoryBean> mccList = new ArrayList<MerchantCategoryBean>();
        try {
            getStat = CMSCon.prepareStatement("SELECT M.MCCCODE,M.DESCRIPTION  "
                    + "FROM MCC M RIGHT JOIN MERCHANTLOCATIONMCC ML ON M.MCCCODE=ML.MCC "
                    + "WHERE ML.MERCHANTID = ?");

            getStat.setString(1, merchantId);
            rs = getStat.executeQuery();

            while (rs.next()) {
                MerchantCategoryBean bean = new MerchantCategoryBean();

                bean.setmCCCode(rs.getString("MCCCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                mccList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getStat.close();
        }
        return mccList;
    }

    public List<CurrencyBean> getCurrencyOfMerchant(Connection CMSCon, String merchantId) throws Exception {
        PreparedStatement getStat = null;
        List<CurrencyBean> currencyList = new ArrayList<CurrencyBean>();
        try {
            getStat = CMSCon.prepareStatement("SELECT C.CURRENCYNUMCODE,C.DESCRIPTION  "
                    + "FROM CURRENCY C RIGHT JOIN MERCHANTLOCATIONCURRENCY MC ON C.CURRENCYNUMCODE=MC.CURRENCYCODE "
                    + "WHERE MC.MERCHANTID = ?");

            getStat.setString(1, merchantId);
            rs = getStat.executeQuery();

            while (rs.next()) {

                CurrencyBean bean = new CurrencyBean();

                bean.setCurrencyCode(Integer.toString(rs.getInt("CURRENCYNUMCODE")));
                bean.setCurrencyDes(rs.getString("DESCRIPTION"));

                currencyList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getStat.close();
        }
        return currencyList;
    }

    public List<TypeMgtBean> getTxnTypeOfMerchant(Connection CMSCon, String merchantId) throws Exception {
        PreparedStatement getStat = null;
        List<TypeMgtBean> txnTypeList = new ArrayList<TypeMgtBean>();
        try {
            getStat = CMSCon.prepareStatement("SELECT T.TRANSACTIONCODE,T.DESCRIPTION  "
                    + "FROM TRANSACTIONTYPE T RIGHT JOIN MERCHANTLOCATIONTRANSACTION MT ON T.TRANSACTIONCODE = MT.TRANSACTIONCODE "
                    + "WHERE MT.MERCHANTID = ?");

            getStat.setString(1, merchantId);
            rs = getStat.executeQuery();

            while (rs.next()) {

                TypeMgtBean bean = new TypeMgtBean();

                bean.setTransactionTypeCode(rs.getString("TRANSACTIONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                txnTypeList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getStat.close();

        }
        return txnTypeList;
    }

    public List<TypeMgtBean> getAssignTxnTypeOfMerchantTerminal(Connection CMSCon, String merchantId, String terminalId) throws Exception {
        PreparedStatement getStat = null;
        List<TypeMgtBean> txnTypeList = new ArrayList<TypeMgtBean>();
        try {
            getStat = CMSCon.prepareStatement("SELECT DISTINCT T.TRANSACTIONCODE,T.DESCRIPTION FROM TRANSACTIONTYPE T "
                    + " RIGHT JOIN TERMINALTXN TT ON T.TRANSACTIONCODE = TT.TXNCODE WHERE TT.MERCHANTID=? AND TT.TERMINALID =?");

            getStat.setString(1, merchantId);
            getStat.setString(2, terminalId);

            rs = getStat.executeQuery();

            while (rs.next()) {

                TypeMgtBean bean = new TypeMgtBean();

                bean.setTransactionTypeCode(rs.getString("TRANSACTIONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                txnTypeList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getStat.close();

        }
        return txnTypeList;
    }

    public List<TypeMgtBean> getNotAssignTxnTypeOfMerchantTerminal(Connection CMSCon, String merchantId, String terminalId) throws Exception {
        PreparedStatement getStat = null;
        List<TypeMgtBean> txnTypeList = new ArrayList<TypeMgtBean>();
        try {
            getStat = CMSCon.prepareStatement("SELECT DISTINCT TX.TRANSACTIONCODE,TX.DESCRIPTION "
                    + " FROM MERCHANTLOCATIONTRANSACTION M,TERMINALTXN T,TRANSACTIONTYPE TX "
                    + " WHERE TX.TRANSACTIONCODE = M.TRANSACTIONCODE "
                    + " AND M.MERCHANTID = ?  "
                    + " AND T.TERMINALID = ? AND  "
                    + " M.TRANSACTIONCODE NOT IN (SELECT T.TXNCODE FROM TERMINALTXN T,"
                    + " MERCHANTLOCATIONTRANSACTION M  WHERE T.MERCHANTID = M.MERCHANTID "
                    + " AND T.TXNCODE = M.TRANSACTIONCODE )");

            getStat.setString(1, merchantId);
            getStat.setString(2, terminalId);

            rs = getStat.executeQuery();

            while (rs.next()) {

                TypeMgtBean bean = new TypeMgtBean();

                bean.setTransactionTypeCode(rs.getString("TRANSACTIONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                txnTypeList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getStat.close();

        }
        return txnTypeList;
    }

    public boolean insertMerchantLocationManualTxn(Connection CMSCon, MerchantLocManualTxnBean manualBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO TERMINAL(TERMINALID,MERCHANTID,"
                    + " CURRENCYCODE,MCC,ALLOCATIONSTATUS,TERMINALSTATUS,LASTUPDATEDUSER,INSTALLATIONDATE,ACTIVATIONDATE,"
                    + " LASTUPDATEDTIME,CREATEDTIME)"
                    + " VALUES(?,?,?,?,?,?,?,SYSDATE,SYSDATE,SYSDATE,SYSDATE)");


            insertStat.setString(1, manualBean.getTerminalId());
            insertStat.setString(2, manualBean.getMerchantId());
            insertStat.setString(3, this.zeroPadding(manualBean.getCurrency()));
            insertStat.setString(4, manualBean.getMcc());
            insertStat.setString(5, StatusVarList.ALLOCATION_YES);
            insertStat.setString(6, StatusVarList.ACTIVE_STATUS);
            insertStat.setString(7, manualBean.getLastUpdatedUser());

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex; 
        } finally {
            insertStat.close();
        }
        return success;

    }

    public boolean insertMerchantLocationManualTxnToOnline(Connection CMSConOnline, MerchantLocManualTxnBean manualBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSConOnline.prepareStatement("INSERT INTO ECMS_ONLINE_TERMINAL(TID,MID,CURRENCYCODE,MCC,"
                    + " RISKPROFILECODE,STATUS,LASTUPDATEUSER,LASTUPDATETIME,CREATETIME,TNAME,DESCRIPTION)"
                    + " VALUES(?,?,?,?,?,?,?,SYSDATE,SYSDATE,?,?)");


            insertStat.setString(1, manualBean.getTerminalId());
            insertStat.setString(2, manualBean.getMerchantId());
            insertStat.setString(3, this.zeroPadding(manualBean.getCurrency()));
            insertStat.setString(4, manualBean.getMcc());
            insertStat.setString(5, manualBean.getRiskProfCode());
            insertStat.setInt(6, StatusVarList.ONLINE_ACTIVE_STATUS);
            insertStat.setString(7, manualBean.getLastUpdatedUser());
            insertStat.setString(8, "MANUAL");
            insertStat.setString(9, "MANUAL");

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;

    }

    public boolean insertToTerminalTxnType(Connection CMSCon, MerchantLocManualTxnBean manualBean, String txnTypeCode) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO TERMINALTXN"
                    + "(TERMINALID,MERCHANTID,TXNCODE) VALUES(?,?,?) ");
            insertStat.setString(1, manualBean.getTerminalId());
            insertStat.setString(2, manualBean.getMerchantId());
            insertStat.setString(3, txnTypeCode);

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean insertToTerminalTxnTypeToOnline(Connection CMSConOnline, MerchantLocManualTxnBean manualBean, int txnTypeCode) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSConOnline.prepareStatement("INSERT INTO ECMS_ONLINE_TERMINAL_TXN"
                    + "(TID,MID,TXNTYPECODE) VALUES(?,?,?) ");
            insertStat.setString(1, manualBean.getTerminalId());
            insertStat.setString(2, manualBean.getMerchantId());
            insertStat.setInt(3, txnTypeCode);

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public String getCommonManualTerminal(Connection CMSCon) throws Exception {
        PreparedStatement getStat = null;
        String commonTid = null;
        try {

            getStat = CMSCon.prepareStatement("SELECT TERMINALID FROM COMMONPARAMETER");

            rs = getStat.executeQuery();

            while (rs.next()) {

                commonTid = rs.getString("TERMINALID");

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getStat.close();

        }
        return commonTid;
    }

    public String getCommonMerchant(Connection CMSCon) throws Exception {
        PreparedStatement getStat = null;
        String commonMid = null;
        try {

            getStat = CMSCon.prepareStatement("SELECT MERCHANTID FROM COMMONPARAMETER");

            rs = getStat.executeQuery();

            while (rs.next()) {

                commonMid = rs.getString("MERCHANTID");

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getStat.close();

        }
        return commonMid;
    }

    public boolean checkExsistanceOfManualTerminal(Connection CMSCon, String mId) throws Exception {
        PreparedStatement getStat = null;
        boolean isExsist = false;
        try {

            getStat = CMSCon.prepareStatement("SELECT T.TERMINALID FROM TERMINAL T WHERE T.MERCHANTID = ? AND "
                    + " T.TERMINALID = (SELECT TERMINALID FROM COMMONPARAMETER)");

            getStat.setString(1, mId);
            rs = getStat.executeQuery();

            while (rs.next()) {

                isExsist = true;
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getStat.close();

        }
        return isExsist;
    }

    public MerchantLocManualTxnBean getMerchantManualTerminalData(Connection CMSConOnline, String mId, String commonTid) throws Exception {
        PreparedStatement getStat = null;
        MerchantLocManualTxnBean manualBean = new MerchantLocManualTxnBean();
        try {
            getStat = CMSConOnline.prepareStatement("SELECT T.CURRENCYCODE,T.MCC,T.RISKPROFILECODE "
                    + " FROM ECMS_ONLINE_TERMINAL T WHERE T.MID =? AND T.TID = ?");

            getStat.setString(1, mId);
            getStat.setString(2, commonTid);

            rs = getStat.executeQuery();

            while (rs.next()) {
                manualBean.setCurrency(rs.getString("CURRENCYCODE"));
                manualBean.setMcc(rs.getString("MCC"));
                manualBean.setRiskProfCode(rs.getString("RISKPROFILECODE"));

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getStat.close();

        }
        return manualBean;
    }

    public boolean UpdateMerchantLocationManualTxn(Connection CMSCon, MerchantLocManualTxnBean manualBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = CMSCon.prepareStatement(" UPDATE TERMINAL SET CURRENCYCODE=?, MCC=?,"
                    + " LASTUPDATEDUSER = ?,LASTUPDATEDTIME = SYSDATE "
                    + " WHERE MERCHANTID = ? AND TERMINALID =?");



            updateStat.setString(1, this.zeroPadding(manualBean.getCurrency()));
            updateStat.setString(2, manualBean.getMcc());
            updateStat.setString(3, manualBean.getLastUpdatedUser());

            updateStat.setString(4, manualBean.getMerchantId());
            updateStat.setString(5, manualBean.getTerminalId());



            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;

    }

    public boolean updateMerchantLocationManualTxnToOnline(Connection CMSConOnline, MerchantLocManualTxnBean manualBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = CMSConOnline.prepareStatement("UPDATE ECMS_ONLINE_TERMINAL "
                    + " SET CURRENCYCODE = ?, MCC = ?, RISKPROFILECODE = ?, LASTUPDATEUSER = ?, LASTUPDATETIME = SYSDATE "
                    + " WHERE MID =? AND TID = ? ");


            updateStat.setString(1, this.zeroPadding(manualBean.getCurrency()));
            updateStat.setString(2, manualBean.getMcc());
            updateStat.setString(3, manualBean.getRiskProfCode());
            updateStat.setString(4, manualBean.getLastUpdatedUser());

            updateStat.setString(5, manualBean.getTerminalId());
            updateStat.setString(6, manualBean.getMerchantId());

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;

    }

    public String getCountryNumCode(Connection CMSCOn, String countryAlpha) throws Exception {
        String countryNumCode = null;
        PreparedStatement getStat = null;
        try {
            getStat = CMSCOn.prepareStatement("SELECT C.COUNTRYNUMCODE "
                    + "FROM COUNTRY C WHERE C.COUNTRYALPHA3CODE=?");

            getStat.setString(1, countryAlpha);
            rs = getStat.executeQuery();

            while (rs.next()) {
                countryNumCode = Integer.toString(rs.getInt("COUNTRYNUMCODE"));
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getStat.close();

        }
        return countryNumCode;
    }

    public String getRedemPointValue(Connection CMSCon, String merchantCusNum) throws Exception {
        String redemPoint = null;
        PreparedStatement getRedemPoint = null;
        try {

            getRedemPoint = CMSCon.prepareStatement("SELECT M.REDEMPTIONPOINTVALUE FROM MERCHANTCUSTOMER M"
                    + " WHERE M.MERCHANTCUSTOMERNO = ?");

            getRedemPoint.setString(1, merchantCusNum);

            rs = getRedemPoint.executeQuery();

            while (rs.next()) {

                redemPoint = Double.toString(rs.getDouble("REDEMPTIONPOINTVALUE"));

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getRedemPoint.close();

        }
        return redemPoint;

    }

    public Date stringTodate(String date) throws ParseException {
        String dateString = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = dateFormat.parse(dateString);
        return convertedDate;
    }

    private String convertStringDate(java.sql.Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return (dateFormat.format(date));
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
}
