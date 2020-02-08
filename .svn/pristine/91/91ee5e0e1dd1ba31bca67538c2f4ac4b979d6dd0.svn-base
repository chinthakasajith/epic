/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantcustomer.persistance;

import com.epic.cms.admin.controlpanel.profilemgt.bean.CommissionProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.FeeProfileBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BankBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BillingCycleMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantBankBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantBankBranchBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantCustomerBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantPaymentCycleBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantPaymentModeBean;
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
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nalin
 */
public class MerchantCustomerPersistance { 

    private ResultSet rs;
    private List<CommissionProfileBean> commissionList = null;
    private List<BillingCycleMgtBean> statementList = null;
    private List<MerchantPaymentCycleBean> paymentList = null;
    private List<MerchantPaymentModeBean> paymentModeList = null;
    private List<BankBean> bankList = null;
    private HashMap<String, String> accountTypeList = null;
    private List<MerchantBankBean> merchantBankList = null;
    private List<MerchantBankBranchBean> merchantBankBranchList = null;
    private HashMap<String, String> currencyTypeList = null;

    public List<MerchantCustomerBean> getMerchantCustomerDetails(Connection CMSCon) throws Exception {
        PreparedStatement getAllMerchant = null;
        List<MerchantCustomerBean> beanList = new ArrayList<MerchantCustomerBean>();
        try {
            getAllMerchant = CMSCon.prepareStatement("SELECT"
                    + " DISTINCT MCCS.MERCHANTCUSTOMERNO,"
                    + " MCCS.MERCHANTNAME, MCCS.LEGALNAME,MCCS.ADDRESS1,MCCS.ADDRESS2,MCCS.ADDRESS3,"
                    + " MCCS.CITY, MCCS.COUNTRY,MCCS.TELEPHONE,MCCS.FAX,MCCS.EMAIL,"
                    + " MCCS.CONTACTPERSONTITLE, MCCS.CONTACTPERSONFIRSTNAME, MCCS.CONTACTPERSONMIDDLENAME,"
                    + " MCCS.CONTACTPERSONLASTNAME, MCCS.LASTUPDATEDUSER, MCCS.COMMISSIONPROFILE,MCCS.CURRENCYCODE,"
                    + " MCCS.FEEPROFILE,MCCS.RISKPROFILE,MCCS.BANKCODE,MCCS.BRANCHNAME,MCCS.ACCOUNTNUMBER,"
                    + " MCCS.ACCOUNTTYPE,MCCS.COMMISSIONPROFILE,MCCS.STATEMENTMAINTEINANCESTATUS,MCCS.PAYMENTMAINTEINANCESTATUS,"
                    + " MCCS.ACCOUNTNAME,MCCS.APPLICATIONDATE,MCCS.ACTIVATIONDATE,MCCS.STATUS, C.DESCRIPTION AS CDESCRIPTION,"
                    + " MCCS.LASTUPDATEDTIME,MCCS.CREATEDTIME,MCCS.PAYMENTMODE,MCCS.PAYMENTCYCLE,MCCS.STATEMENTCYCLE,"
                    + " AR.DESCRIPTION AS ARDESCRIPTION, CUN.DESCRIPTION AS CUNDESCRIPTION,BNK.BANKNAME,"
                    + " BRCH.DESCRPTION AS BRCHDESCRPTION,PM.DESCRIPTION AS PMDESCRIPTION,AT.DESCRIPTION AS ATDESCRIPTION,"
                    + " STAT.DESCRIPTION AS STATDESCRIPTION,PC.DESCRIPTION AS PCDESCRIPTION,MCCS.MERCHANTACCOUNTNO,"
                    + " F.DESCRIPTION AS FDESCRIPTION, BC.DESCRIPTION AS BCDESCRIPTION,CP.DESCRIPTION AS CPDESCRIPTION,MCCS.REDEMPTIONPOINTVALUE"
                    + " FROM MERCHANTCUSTOMER MCCS,AREA AR,COUNTRY CUN,BANK BNK,BANKBRANCH BRCH,STATUS STAT,ACCOUNTTYPE AT,"
                    + " FEEPROFILE F,MERCHANTBILLINGCYCLE BC,MERCHANTPAYMENTCYCLE PC,PAYMENTMODE PM,COMMISSIONPROFILE CP, CURRENCY C  "
                    + " WHERE MCCS.CITY=AR.AREACODE AND MCCS.COUNTRY=CUN.COUNTRYALPHA3CODE AND MCCS.BANKCODE=BNK.BANKCODE"
                    + " AND MCCS.BRANCHNAME=BRCH.BRANCHCODE AND MCCS.STATUS=STAT.STATUSCODE AND BRCH.BANKCODE = BNK.BANKCODE"
                    + " AND MCCS.FEEPROFILE = F.FEEPROFILECODE AND MCCS.STATEMENTCYCLE = BC.BILLINGCYCLECODE"
                    + " AND MCCS.PAYMENTCYCLE = PC.PAYMENTCYCLECODE AND MCCS.PAYMENTMODE = PM.PAYMENTMODECODE"
                    + " AND MCCS.COMMISSIONPROFILE = CP.COMMISSIONPROFILECODE AND MCCS.ACCOUNTTYPE = AT.ACCOUNTTYPECODE"
                    + " AND MCCS.CURRENCYCODE = C.CURRENCYNUMCODE");

            rs = getAllMerchant.executeQuery();

            while (rs.next()) {

                MerchantCustomerBean bean = new MerchantCustomerBean();


                bean.setMerchantCustomerNumber(rs.getString("MERCHANTCUSTOMERNO"));
                bean.setMerchantName(rs.getString("MERCHANTNAME"));
                bean.setLegalName(rs.getString("LEGALNAME"));
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
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setCommissionProfile(rs.getString("COMMISSIONPROFILE"));
                bean.setFeeProfile(rs.getString("FEEPROFILE"));
//                bean.setRiskProfile(rs.getString("RISKPROFILE"));
                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBranchCode(rs.getString("BRANCHNAME"));
                bean.setAccountNumber(rs.getString("ACCOUNTNUMBER"));
                bean.setAccountType(rs.getString("ACCOUNTTYPE"));
                bean.setAccountName(rs.getString("ACCOUNTNAME"));
                bean.setApplicationDate(this.convertStringDate(rs.getDate("APPLICATIONDATE")));
                bean.setActivationDate(this.convertStringDate(rs.getDate("ACTIVATIONDATE")));
                bean.setStatus(rs.getString("STATUS"));
                bean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                bean.setCreatTime(rs.getDate("CREATEDTIME"));
                bean.setPaymentMode(rs.getString("PAYMENTMODE"));
                bean.setPaymentCycle(rs.getString("PAYMENTCYCLE"));
                bean.setStatementCycle(rs.getString("STATEMENTCYCLE"));
                bean.setAreaDescription(rs.getString("ARDESCRIPTION"));
                bean.setCountryDescription(rs.getString("CUNDESCRIPTION"));
                bean.setBankName(rs.getString("BANKNAME"));
                bean.setBranchName(rs.getString("BRCHDESCRPTION"));
                bean.setStatusDescription(rs.getString("STATDESCRIPTION"));
                bean.setFeeProfDes(rs.getString("FDESCRIPTION"));
                bean.setStatementCycleDes(rs.getString("BCDESCRIPTION"));
                bean.setPaymentCycleDes(rs.getString("PCDESCRIPTION"));
                bean.setPaymentModeDes(rs.getString("PMDESCRIPTION"));
                bean.setCommissionProfDes(rs.getString("CPDESCRIPTION"));
                bean.setStatementStatus(rs.getString("STATEMENTMAINTEINANCESTATUS"));
                bean.setPaymentStatus(rs.getString("PAYMENTMAINTEINANCESTATUS"));
                bean.setAccountTypeDescription(rs.getString("ATDESCRIPTION"));
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

    public MerchantCustomerBean getMerchantCustomer(Connection CMSCon, String merchantCustomerNumber) throws Exception {
        PreparedStatement getMerchant = null;
        MerchantCustomerBean bean = new MerchantCustomerBean();
        try {

            getMerchant = CMSCon.prepareStatement("SELECT"
                    + " DISTINCT MCCS.MERCHANTCUSTOMERNO,"
                    + " MCCS.MERCHANTNAME, MCCS.LEGALNAME,MCCS.ADDRESS1,MCCS.ADDRESS2,MCCS.ADDRESS3,"
                    + " MCCS.CITY, MCCS.COUNTRY,MCCS.TELEPHONE,MCCS.FAX,MCCS.EMAIL,"
                    + " MCCS.CONTACTPERSONTITLE, MCCS.CONTACTPERSONFIRSTNAME, MCCS.CONTACTPERSONMIDDLENAME,"
                    + " MCCS.CONTACTPERSONLASTNAME, MCCS.LASTUPDATEDUSER, MCCS.COMMISSIONPROFILE,"
                    + " MCCS.FEEPROFILE,MCCS.BANKCODE,MCCS.BRANCHNAME,MCCS.ACCOUNTNUMBER,"
                    + " MCCS.ACCOUNTTYPE,MCCS.STATEMENTMAINTEINANCESTATUS,MCCS.PAYMENTMAINTEINANCESTATUS,"
                    + " MCCS.ACCOUNTNAME,MCCS.APPLICATIONDATE,MCCS.ACTIVATIONDATE,MCCS.STATUS,"
                    + " MCCS.LASTUPDATEDTIME,MCCS.CREATEDTIME,MCCS.PAYMENTMODE,MCCS.PAYMENTCYCLE,MCCS.STATEMENTCYCLE,"
                    + " AR.DESCRIPTION AS ARDESCRIPTION, CUN.DESCRIPTION AS CUNDESCRIPTION,BNK.BANKNAME,"
                    + " BRCH.DESCRPTION AS BRCHDESCRPTION,PM.DESCRIPTION AS PMDESCRIPTION,AT.DESCRIPTION AS ATDESCRIPTION,"
                    + " STAT.DESCRIPTION AS STATDESCRIPTION,PC.DESCRIPTION AS PCDESCRIPTION,MCCS.MERCHANTACCOUNTNO,"
                    + " F.DESCRIPTION AS FDESCRIPTION, BC.DESCRIPTION AS BCDESCRIPTION,CP.DESCRIPTION AS CPDESCRIPTION"
                    + " FROM MERCHANTCUSTOMER MCCS,AREA AR,COUNTRY CUN,BANK BNK,BANKBRANCH BRCH,STATUS STAT,ACCOUNTTYPE AT,"
                    + " FEEPROFILE F,MERCHANTBILLINGCYCLE BC,MERCHANTPAYMENTCYCLE PC,PAYMENTMODE PM,COMMISSIONPROFILE CP"
                    + " WHERE MCCS.CITY=AR.AREACODE AND MCCS.COUNTRY=CUN.COUNTRYALPHA3CODE AND MCCS.BANKCODE=BNK.BANKCODE"
                    + " AND MCCS.BRANCHNAME=BRCH.BRANCHCODE AND MCCS.STATUS=STAT.STATUSCODE "
                    + " AND BRCH.BANKCODE = BNK.BANKCODE "
                    + " AND MCCS.FEEPROFILE = F.FEEPROFILECODE AND MCCS.STATEMENTCYCLE = BC.BILLINGCYCLECODE"
                    + " AND MCCS.PAYMENTCYCLE = PC.PAYMENTCYCLECODE AND MCCS.PAYMENTMODE = PM.PAYMENTMODECODE"
                    + " AND MCCS.COMMISSIONPROFILE = CP.COMMISSIONPROFILECODE AND MCCS.ACCOUNTTYPE = AT.ACCOUNTTYPECODE "
                    + " AND MERCHANTCUSTOMERNO = ?");

            getMerchant.setString(1, merchantCustomerNumber);
            rs = getMerchant.executeQuery();

            while (rs.next()) {

                bean.setMerchantCustomerNumber(rs.getString("MERCHANTCUSTOMERNO"));
                bean.setMerchantName(rs.getString("MERCHANTNAME"));
                bean.setLegalName(rs.getString("LEGALNAME"));
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
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setCommissionProfile(rs.getString("COMMISSIONPROFILE"));
                bean.setFeeProfile(rs.getString("FEEPROFILE"));
//                bean.setRiskProfile(rs.getString("RISKPROFILE"));
                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBranchCode(rs.getString("BRANCHNAME"));
                bean.setAccountNumber(rs.getString("ACCOUNTNUMBER"));
                bean.setAccountType(rs.getString("ACCOUNTTYPE"));
                bean.setAccountName(rs.getString("ACCOUNTNAME"));
                bean.setApplicationDate(this.convertStringDate(rs.getDate("APPLICATIONDATE")));
                bean.setActivationDate(this.convertStringDate(rs.getDate("ACTIVATIONDATE")));
                bean.setStatus(rs.getString("STATUS"));
                bean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                bean.setCreatTime(rs.getDate("CREATEDTIME"));
                bean.setPaymentMode(rs.getString("PAYMENTMODE"));
                bean.setPaymentCycle(rs.getString("PAYMENTCYCLE"));
                bean.setStatementCycle(rs.getString("STATEMENTCYCLE"));
                bean.setAreaDescription(rs.getString("ARDESCRIPTION"));
                bean.setCountryDescription(rs.getString("CUNDESCRIPTION"));
                bean.setBankName(rs.getString("BANKNAME"));
                bean.setBranchName(rs.getString("BRCHDESCRPTION"));
                bean.setStatusDescription(rs.getString("STATDESCRIPTION"));

                bean.setFeeProfDes(rs.getString("FDESCRIPTION"));
                bean.setStatementCycleDes(rs.getString("BCDESCRIPTION"));
                bean.setPaymentCycleDes(rs.getString("PCDESCRIPTION"));
                bean.setPaymentModeDes(rs.getString("PMDESCRIPTION"));
                bean.setCommissionProfDes(rs.getString("CPDESCRIPTION"));
                bean.setStatementStatus(rs.getString("STATEMENTMAINTEINANCESTATUS"));
                bean.setPaymentStatus(rs.getString("PAYMENTMAINTEINANCESTATUS"));
                bean.setAccountTypeDescription(rs.getString("ATDESCRIPTION"));
                bean.setMerchantAccountNo(rs.getString("MERCHANTACCOUNTNO"));


            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getMerchant.close();

        }
        return bean;
    }

    public List<MerchantCategoryBean> getNotAssignedMccList(Connection CMSCon, String merchantCustomerNumber) throws SQLException, Exception {


        List<MerchantCategoryBean> merchantCategoryList = new ArrayList<MerchantCategoryBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT M.DESCRIPTION,M.MCCCODE,M.CLASS,M.STATUS,M.LASTUPDATEDUSER,M.LASTUPDATEDTIME,M.CREATEDTIME,S.DESCRIPTION "
                    + "FROM MCC M,STATUS S WHERE M.STATUS = S.STATUSCODE "
                    + "AND M.MCCCODE NOT IN (SELECT MCC FROM MERCHANTCUSTOMERMCC WHERE MERCHANTUSTOMERNO = ? ) ORDER BY MCCCODE";

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

    public List<MerchantCategoryBean> getAssignedMccList(Connection CMSCon, String merchantCustomerNumber) throws SQLException, Exception {


        List<MerchantCategoryBean> merchantCategoryList = new ArrayList<MerchantCategoryBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT MCC FROM MERCHANTCUSTOMERMCC WHERE MERCHANTUSTOMERNO = ? ORDER BY MCC ";


            ps = CMSCon.prepareStatement(query);
            ps.setString(1, merchantCustomerNumber);
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

    public List<TypeMgtBean> getNotAssignedTxnTypeList(Connection CMSCon, String merchantCustomerNumber) throws SQLException, Exception {


        List<TypeMgtBean> txnTypeList = new ArrayList<TypeMgtBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT T.DESCRIPTION,T.TRANSACTIONCODE "
                    + "FROM TRANSACTIONTYPE T,STATUS S WHERE T.STATUS = S.STATUSCODE "
                    + "AND T.TRANSACTIONCODE NOT IN (SELECT TRANSACTIONCODE FROM MERCHANTCUSTOMERTRANSACTION WHERE MERCHANTCUSTOMERNO = ?)";


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

    public List<TypeMgtBean> getAssignedTxnTypeList(Connection CMSCon, String merchantCustomerNumber) throws SQLException, Exception {


        List<TypeMgtBean> txnTypeList = new ArrayList<TypeMgtBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT TRANSACTIONCODE FROM MERCHANTCUSTOMERTRANSACTION WHERE MERCHANTCUSTOMERNO = ? ";

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, merchantCustomerNumber);
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

    public List<CurrencyBean> getNotAssignedCurrencyList(Connection CMSCon, String merchantCustomerNumber) throws SQLException, Exception {


        List<CurrencyBean> currencyList = new ArrayList<CurrencyBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT C.DESCRIPTION, C.CURRENCYNUMCODE "
                    + " FROM CURRENCY C WHERE "
                    + " C.CURRENCYNUMCODE NOT IN (SELECT CURRENCYCODE FROM MERCHANTCUSTOMERCURRENCY WHERE MERCHANTCUSTOMERNO = ?)";


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

    public List<CurrencyBean> getAssignedCurrencyList(Connection CMSCon, String merchantCustomerNumber) throws SQLException, Exception {


        List<CurrencyBean> currencyList = new ArrayList<CurrencyBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT CURRENCYCODE FROM MERCHANTCUSTOMERCURRENCY WHERE MERCHANTCUSTOMERNO = ? ";

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, merchantCustomerNumber);
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

    public List<MerchantCustomerBean> searchMerchantCustomerDetails(MerchantCustomerBean merchantBean, Connection CMSCon) throws Exception {
        PreparedStatement searchAllMerchant = null;
        List<MerchantCustomerBean> beanList = new ArrayList<MerchantCustomerBean>();
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT MCCS.MERCHANTCUSTOMERNO,"
                    + " MCCS.MERCHANTNAME, MCCS.LEGALNAME, MCCS.CITY,AR.DESCRIPTION AS ARDESCRIPTION"
                    + " FROM MERCHANTCUSTOMER MCCS,AREA AR WHERE MCCS.CITY=AR.AREACODE ";

            String condition = "";

            if (!merchantBean.getMerchantCustomerNumber().contentEquals("") || merchantBean.getMerchantCustomerNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " MCCS.MERCHANTCUSTOMERNO LIKE '%" + merchantBean.getMerchantCustomerNumber() + "%'";
            }

            if (!merchantBean.getMerchantName().contentEquals("") || merchantBean.getMerchantName().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(MCCS.MERCHANTNAME) LIKE '%" + merchantBean.getMerchantName().toUpperCase() + "%'";
            }

            if (!merchantBean.getLegalName().contentEquals("") || merchantBean.getLegalName().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(MCCS.LEGALNAME) LIKE '%" + merchantBean.getLegalName().toUpperCase() + "%'";
            }

            if (!merchantBean.getArea().contentEquals("") || merchantBean.getArea().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " MCCS.CITY = " + merchantBean.getArea() + " ";
            }

            if (!condition.equals("")) {
                strSqlBasic += " AND " + condition + " ORDER BY MCCS.MERCHANTCUSTOMERNO ";
            } else {

                strSqlBasic += " ORDER BY MCCS.MERCHANTCUSTOMERNO";
            }

            searchAllMerchant = CMSCon.prepareStatement(strSqlBasic);
            rs = searchAllMerchant.executeQuery();

            while (rs.next()) {

                MerchantCustomerBean bean = new MerchantCustomerBean();

                bean.setMerchantCustomerNumber(rs.getString("MERCHANTCUSTOMERNO"));
                bean.setMerchantName(rs.getString("MERCHANTNAME"));
                bean.setLegalName(rs.getString("LEGALNAME"));
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

    public List<MerchantCustomerBean> getBankBranchList(Connection CMSCon, MerchantCustomerBean merchantBean) throws Exception {

        PreparedStatement getAllMerchant = null;
        List<MerchantCustomerBean> branchList = new ArrayList<MerchantCustomerBean>();
        try {
            getAllMerchant = CMSCon.prepareStatement("SELECT BR.BRANCHCODE,BR.DESCRPTION"
                    + "  FROM BANKBRANCH BR WHERE BR.BANKCODE=? ORDER BY BR.DESCRPTION ");

            getAllMerchant.setString(1, merchantBean.getBankCode());
            rs = getAllMerchant.executeQuery();

            while (rs.next()) {

                MerchantCustomerBean bean = new MerchantCustomerBean();


                bean.setBranchCode(rs.getString("BRANCHCODE"));
                bean.setBranchName(rs.getString("DESCRPTION"));

                branchList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMerchant.close();

        }
        return branchList;
    }

    public boolean insertMerchantCustomer(Connection CMSCon, MerchantCustomerBean merchantBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO MERCHANTCUSTOMER(MERCHANTCUSTOMERNO,MERCHANTNAME,LEGALNAME,"
                    + " ADDRESS1,ADDRESS2,ADDRESS3,CITY,COUNTRY ,TELEPHONE,FAX,EMAIL,CONTACTPERSONTITLE,"
                    + " CONTACTPERSONFIRSTNAME,CONTACTPERSONMIDDLENAME,CONTACTPERSONLASTNAME,"
                    + " LASTUPDATEDUSER,COMMISSIONPROFILE,"
                    + " FEEPROFILE,BANKCODE,BRANCHNAME,ACCOUNTNUMBER,ACCOUNTTYPE,ACCOUNTNAME,APPLICATIONDATE,"
                    + " ACTIVATIONDATE,STATUS,LASTUPDATEDTIME,CREATEDTIME,PAYMENTMODE,PAYMENTCYCLE,STATEMENTCYCLE,"
                    + " PAYMENTMAINTEINANCESTATUS,STATEMENTMAINTEINANCESTATUS,MERCHANTACCOUNTNO,CURRENCYCODE,REDEMPTIONPOINTVALUE)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE,?,?,?,?,?,?,?,?)");

            insertStat.setString(1, merchantBean.getMerchantCustomerNumber());
            insertStat.setString(2, merchantBean.getMerchantName());
            insertStat.setString(3, merchantBean.getLegalName());
            insertStat.setString(4, merchantBean.getAddress1());
            insertStat.setString(5, merchantBean.getAddress2());
            insertStat.setString(6, merchantBean.getAddress3());
            insertStat.setString(7, merchantBean.getArea());
//            insertStat.setString(8, merchantBean.getPostalCode());
            insertStat.setString(8, merchantBean.getCountry());
            insertStat.setString(9, merchantBean.getTpNumber());
            insertStat.setString(10, merchantBean.getFax());
            insertStat.setString(11, merchantBean.geteMail());
            insertStat.setString(12, merchantBean.getCpTitle());
            insertStat.setString(13, merchantBean.getCpFirstName());
            insertStat.setString(14, merchantBean.getCpMiddleName());
            insertStat.setString(15, merchantBean.getCpLastName());
            insertStat.setString(16, merchantBean.getLastUpdatedUser());
            insertStat.setString(17, merchantBean.getCommissionProfile());
            insertStat.setString(18, merchantBean.getFeeProfile());
//            insertStat.setString(20, merchantBean.getRiskProfile());
            insertStat.setString(19, merchantBean.getBankCode());
            insertStat.setString(20, merchantBean.getBranchCode());
            insertStat.setString(21, merchantBean.getAccountNumber());
            insertStat.setString(22, merchantBean.getAccountType());
            insertStat.setString(23, merchantBean.getAccountName());
            insertStat.setTimestamp(24, new Timestamp(this.stringTodate(merchantBean.getApplicationDate()).getTime()));
            insertStat.setTimestamp(25, new Timestamp(this.stringTodate(merchantBean.getActivationDate()).getTime()));
            insertStat.setString(26, merchantBean.getStatus());
            insertStat.setString(27, merchantBean.getPaymentMode());
            insertStat.setString(28, merchantBean.getPaymentCycle());
            insertStat.setString(29, merchantBean.getStatementCycle());
            insertStat.setString(30, merchantBean.getPaymentStatus());
            insertStat.setString(31, merchantBean.getStatementStatus());
            insertStat.setString(32, merchantBean.getMerchantAccountNo());
            insertStat.setInt(33, Integer.parseInt(merchantBean.getCurrencyType()));
            insertStat.setDouble(34, Double.parseDouble(merchantBean.getRedempoint()));


            insertStat.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;

    }

    public boolean checkMerchantCustomerExsistance(Connection CMSCon, String merchantCustomerNumber) throws Exception {

        boolean isExsist = false;
        PreparedStatement checkStat = null;
        try {
            checkStat = CMSCon.prepareStatement(" SELECT MERCHANTNAME FROM MERCHANTCUSTOMER"
                    + " WHERE MERCHANTCUSTOMERNO = ? ");


            checkStat.setString(1, merchantCustomerNumber);
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

    public boolean updateMerchantCustomer(Connection CMSCon, MerchantCustomerBean merchantBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = CMSCon.prepareStatement("UPDATE MERCHANTCUSTOMER SET MERCHANTNAME=?,LEGALNAME=?,ADDRESS1=?,ADDRESS2=?,"
                    + " ADDRESS3=?,CITY=?,COUNTRY=?,TELEPHONE=?,FAX=?,EMAIL=?,CONTACTPERSONTITLE=?,CONTACTPERSONFIRSTNAME=?,"
                    + " CONTACTPERSONMIDDLENAME=?,CONTACTPERSONLASTNAME=?,LASTUPDATEDUSER=?,COMMISSIONPROFILE=?,FEEPROFILE=?,"
                    + " BANKCODE=?,BRANCHNAME=?,ACCOUNTNUMBER=?,ACCOUNTTYPE=?,ACCOUNTNAME=?,APPLICATIONDATE=?,"
                    + " ACTIVATIONDATE=?,STATUS=?,LASTUPDATEDTIME=SYSDATE,CREATEDTIME=SYSDATE,PAYMENTMODE=?,PAYMENTCYCLE=?,"
                    + " STATEMENTCYCLE=?,PAYMENTMAINTEINANCESTATUS=?,STATEMENTMAINTEINANCESTATUS=?,CURRENCYCODE =?,REDEMPTIONPOINTVALUE =?"
                    + " WHERE MERCHANTCUSTOMERNO=? ");



            updateStat.setString(1, merchantBean.getMerchantName());
            updateStat.setString(2, merchantBean.getLegalName());
            updateStat.setString(3, merchantBean.getAddress1());
            updateStat.setString(4, merchantBean.getAddress2());
            updateStat.setString(5, merchantBean.getAddress3());
            updateStat.setString(6, merchantBean.getArea());
//            updateStat.setString(7, merchantBean.getPostalCode());
            updateStat.setString(7, merchantBean.getCountry());
            updateStat.setString(8, merchantBean.getTpNumber());
            updateStat.setString(9, merchantBean.getFax());
            updateStat.setString(10, merchantBean.geteMail());
            updateStat.setString(11, merchantBean.getCpTitle());
            updateStat.setString(12, merchantBean.getCpFirstName());
            updateStat.setString(13, merchantBean.getCpMiddleName());
            updateStat.setString(14, merchantBean.getCpLastName());
            updateStat.setString(15, merchantBean.getLastUpdatedUser());
            updateStat.setString(16, merchantBean.getCommissionProfile());
            updateStat.setString(17, merchantBean.getFeeProfile());
//            updateStat.setString(19, merchantBean.getRiskProfile());
            updateStat.setString(18, merchantBean.getBankCode());
            updateStat.setString(19, merchantBean.getBranchCode());
            updateStat.setString(20, merchantBean.getAccountNumber());
            updateStat.setString(21, merchantBean.getAccountType());
            updateStat.setString(22, merchantBean.getAccountName());
            updateStat.setTimestamp(23, new Timestamp(this.stringTodate(merchantBean.getApplicationDate()).getTime()));
            updateStat.setTimestamp(24, new Timestamp(this.stringTodate(merchantBean.getActivationDate()).getTime()));
            updateStat.setString(25, merchantBean.getStatus());
            updateStat.setString(26, merchantBean.getPaymentMode());
            updateStat.setString(27, merchantBean.getPaymentCycle());
            updateStat.setString(28, merchantBean.getStatementCycle());
            updateStat.setString(29, merchantBean.getPaymentStatus());
            updateStat.setString(30, merchantBean.getStatementStatus());
            updateStat.setInt(31, Integer.parseInt(merchantBean.getCurrencyType()));
            updateStat.setDouble(32, Double.parseDouble(merchantBean.getRedempoint()));

            updateStat.setString(33, merchantBean.getMerchantCustomerNumber());

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;

    }

    public boolean deleteMerchantCustomer(Connection CMSCon, MerchantCustomerBean merchantBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = CMSCon.prepareStatement("DELETE MERCHANTCUSTOMER WHERE MERCHANTCUSTOMERNO=?");



            deleteStat.setString(1, merchantBean.getMerchantCustomerNumber());


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

    public boolean deleteMcc(Connection CMSCon, MerchantCustomerBean merchantBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = CMSCon.prepareStatement("DELETE MERCHANTCUSTOMERMCC WHERE MERCHANTUSTOMERNO=?");



            deleteStat.setString(1, merchantBean.getMerchantCustomerNumber());


            deleteStat.executeUpdate();


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

    public boolean deleteTxn(Connection CMSCon, MerchantCustomerBean merchantBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = CMSCon.prepareStatement("DELETE MERCHANTCUSTOMERTRANSACTION WHERE MERCHANTCUSTOMERNO=?");



            deleteStat.setString(1, merchantBean.getMerchantCustomerNumber());


            deleteStat.executeUpdate();


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

    public boolean deleteCurrency(Connection CMSCon, MerchantCustomerBean merchantBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = CMSCon.prepareStatement("DELETE MERCHANTCUSTOMERCURRENCY WHERE MERCHANTCUSTOMERNO=?");

            deleteStat.setString(1, merchantBean.getMerchantCustomerNumber());

            deleteStat.executeUpdate();


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

    public boolean insertToMcc(Connection CMSCon, String merchantCustomerNumber, String merchantCatogoryCode, String lastUpdateUser) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO MERCHANTCUSTOMERMCC(MERCHANTUSTOMERNO,MCC,LASTUPDATEDUSER,"
                    + " LASTUPDATEDTIME,CREATEDTIME) VALUES(?,?,?,SYSDATE,SYSDATE) ");
            insertStat.setString(1, merchantCustomerNumber);
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

    public boolean insertToTxnType(Connection CMSCon, String merchantCustomerNumber, String txnTypeCode, String lastUpdateUser) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO MERCHANTCUSTOMERTRANSACTION(MERCHANTCUSTOMERNO,TRANSACTIONCODE,"
                    + " LASTUPDATEDUSER,LASTUPDATEDTIME,CREATEDTIME) VALUES(?,?,?,SYSDATE,SYSDATE) ");
            insertStat.setString(1, merchantCustomerNumber);
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

    public boolean insertToCurrency(Connection CMSCon, String merchantCustomerNumber, String currencyCode, String lastUpdateUser) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO MERCHANTCUSTOMERCURRENCY(MERCHANTCUSTOMERNO,CURRENCYCODE,LASTUPDATEDUSER,"
                    + " LASTUPDATEDTIME,CREATEDTIME) VALUES(?,?,?,SYSDATE,SYSDATE) ");
            insertStat.setString(1, merchantCustomerNumber);
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

            getAllByCatStat.setString(1, currencyCode);


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

    public boolean findExistanceOfMcc(Connection con, String merchantCustomerNumber, String mcc) throws Exception {
        boolean isExist = false;
        PreparedStatement findStatement = null;
        try {

            findStatement = con.prepareStatement("SELECT MC.MERCHANTUSTOMERNO,MC.MCC FROM MERCHANTCUSTOMERMCC MC WHERE MC.MERCHANTUSTOMERNO=? AND MC.MCC=? ");

            //set parameters for select
            findStatement.setString(1, merchantCustomerNumber);
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

    public boolean findExistanceOfTxn(Connection con, String merchantCustomerNumber, String txn) throws Exception {
        boolean isExist = false;
        PreparedStatement findStatement = null;
        try {

            findStatement = con.prepareStatement("SELECT MC.MERCHANTCUSTOMERNO,MC.TRANSACTIONCODE FROM MERCHANTCUSTOMERTRANSACTION MC "
                    + " WHERE MC.MERCHANTCUSTOMERNO =? AND MC.TRANSACTIONCODE=? ");

            //set parameters for select
            findStatement.setString(1, merchantCustomerNumber);
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

    public boolean findExistanceOfCurrency(Connection con, String merchantCustomerNumber, String currency) throws Exception {
        boolean isExist = false;
        PreparedStatement findStatement = null;
        try {

            findStatement = con.prepareStatement("SELECT MC.MERCHANTCUSTOMERNO,MC.CURRENCYCODE FROM MERCHANTCUSTOMERCURRENCY MC "
                    + " WHERE MC.MERCHANTCUSTOMERNO =? AND MC.CURRENCYCODE=? ");

            //set parameters for select
            findStatement.setString(1, merchantCustomerNumber);
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

    public Boolean deleteMerchantMccByMcc(Connection con, String mcc, String merchantCustomerNumber) throws Exception {
        Boolean status = false;
        PreparedStatement updateStat = null;
        try {

            updateStat = con.prepareStatement("DELETE FROM MERCHANTCUSTOMERMCC WHERE MERCHANTUSTOMERNO = ? AND MCC=?");

            updateStat.setString(1, merchantCustomerNumber);
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

    public Boolean deleteMerchantTxnByTxnCode(Connection con, String txn, String merchantCustomerNumber) throws Exception {
        Boolean status = false;
        PreparedStatement updateStat = null;
        try {

            updateStat = con.prepareStatement("DELETE FROM MERCHANTCUSTOMERTRANSACTION WHERE MERCHANTCUSTOMERNO = ? AND TRANSACTIONCODE=?");

            updateStat.setString(1, merchantCustomerNumber);
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

    public Boolean deleteMerchantCurrencyByCurrencyCode(Connection con, String currency, String merchantCustomerNumber) throws Exception {
        Boolean status = false;
        PreparedStatement updateStat = null;
        try {

            updateStat = con.prepareStatement("DELETE FROM MERCHANTCUSTOMERCURRENCY WHERE MERCHANTCUSTOMERNO = ? AND CURRENCYCODE=?");

            updateStat.setString(1, merchantCustomerNumber);
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

    public List<CommissionProfileBean> getCommissionProfileDetails(Connection CMSCon) throws Exception {
        PreparedStatement getCPStat = null;
        commissionList = new ArrayList<CommissionProfileBean>();
        try {
            getCPStat = CMSCon.prepareStatement("SELECT CP.COMMISSIONPROFILECODE,CP.DESCRIPTION "
                    + " FROM COMMISSIONPROFILE CP WHERE CP.STATUS =? ");

            getCPStat.setString(1, StatusVarList.ACTIVE_STATUS);

            rs = getCPStat.executeQuery();

            while (rs.next()) {

                CommissionProfileBean bean = new CommissionProfileBean();

                bean.setComProfileCode(rs.getString("COMMISSIONPROFILECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                commissionList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getCPStat.close();
        }

        return commissionList;
    }

    public List<BillingCycleMgtBean> getStatementCycle(Connection CMSCon) throws Exception {
        PreparedStatement getStatementStat = null;
        statementList = new ArrayList<BillingCycleMgtBean>();
        try {
            getStatementStat = CMSCon.prepareStatement("SELECT B.BILLINGCYCLECODE,B.DESCRIPTION "
                    + " FROM MERCHANTBILLINGCYCLE B WHERE B.STATUS =? ");

            getStatementStat.setString(1, StatusVarList.ACTIVE_STATUS);

            rs = getStatementStat.executeQuery();

            while (rs.next()) {

                BillingCycleMgtBean bean = new BillingCycleMgtBean();

                bean.setBillCycleCode(rs.getString("BILLINGCYCLECODE"));
                bean.setBillDescription(rs.getString("DESCRIPTION"));

                statementList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getStatementStat.close();
        }

        return statementList;
    }

    public List<MerchantPaymentCycleBean> getPaymentCycle(Connection CMSCon) throws Exception {
        PreparedStatement getPaymentStat = null;
        paymentList = new ArrayList<MerchantPaymentCycleBean>();
        try {
            getPaymentStat = CMSCon.prepareStatement("SELECT P.PAYMENTCYCLECODE,P.DESCRIPTION "
                    + " FROM MERCHANTPAYMENTCYCLE P WHERE P.STATUS =? ");

            getPaymentStat.setString(1, StatusVarList.ACTIVE_STATUS);

            rs = getPaymentStat.executeQuery();

            while (rs.next()) {

                MerchantPaymentCycleBean bean = new MerchantPaymentCycleBean();

                bean.setPaymentCycleCode(rs.getString("PAYMENTCYCLECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                paymentList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getPaymentStat.close();
        }

        return paymentList;
    }

    public List<MerchantPaymentModeBean> getPaymentMode(Connection CMSCon) throws Exception {
        PreparedStatement getPaymentModeStat = null;
        paymentModeList = new ArrayList<MerchantPaymentModeBean>();
        try {
            getPaymentModeStat = CMSCon.prepareStatement("SELECT P.PAYMENTMODECODE,P.DESCRIPTION "
                    + " FROM PAYMENTMODE P WHERE P.ISSUERORACQUIRE =? ");

            getPaymentModeStat.setString(1, StatusVarList.ACQUIRE_PAYMENT_MODE);

            rs = getPaymentModeStat.executeQuery();

            while (rs.next()) {

                MerchantPaymentModeBean bean = new MerchantPaymentModeBean();

                bean.setPaymentMode(rs.getString("PAYMENTMODECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                paymentModeList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getPaymentModeStat.close();
        }

        return paymentModeList;
    }

    public List<BankBean> getCommonParaBank(Connection CMSCon) throws Exception {
        PreparedStatement getBank = null;
        bankList = new ArrayList<BankBean>();
        try {
            getBank = CMSCon.prepareStatement("SELECT C.BANKCODE,B.BANKNAME "
                    + " FROM COMMONPARAMETER C,BANK B WHERE C.BANKCODE = B.BANKCODE ");

            rs = getBank.executeQuery();

            while (rs.next()) {

                BankBean bean = new BankBean();

                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBankName(rs.getString("BANKNAME"));

                bankList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getBank.close();
        }

        return bankList;
    }

    public HashMap<String, String> getAllAccountType(Connection con) throws Exception {

        PreparedStatement getAllACCType = null;

        try {
            getAllACCType = con.prepareStatement("SELECT A.ACCOUNTTYPECODE,A.DESCRIPTION FROM ACCOUNTTYPE A");

            rs = getAllACCType.executeQuery();
            accountTypeList = new HashMap<String, String>();

            while (rs.next()) {

                accountTypeList.put(rs.getString("ACCOUNTTYPECODE"), rs.getString("DESCRIPTION"));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllACCType.close();

        }

        return accountTypeList;
    }

    public List<MerchantBankBean> getAllBanks(Connection con) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        merchantBankList = new ArrayList<MerchantBankBean>();
        try {
            stmt = con.prepareStatement("SELECT BANKCODE,BANKNAME,STATUS FROM BANK ORDER BY BANKNAME ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                MerchantBankBean bean = new MerchantBankBean();

                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBankName(rs.getString("BANKNAME"));
                bean.setStatus(rs.getString("STATUS"));

                merchantBankList.add(bean);
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

        return merchantBankList;
    }

    public List<MerchantBankBranchBean> getAllBankBranches(Connection con) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        merchantBankBranchList = new ArrayList<MerchantBankBranchBean>();
        try {
            stmt = con.prepareStatement("SELECT BRANCHCODE,BANKCODE,DESCRPTION FROM BANKBRANCH ORDER BY DESCRPTION");
            rs = stmt.executeQuery();

            while (rs.next()) {
                MerchantBankBranchBean bean = new MerchantBankBranchBean();

                bean.setBranchCode(rs.getString("BRANCHCODE"));
                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setDescription(rs.getString("DESCRPTION"));

                merchantBankBranchList.add(bean);
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

        return merchantBankBranchList;
    }

    public String getMaxAccnoFromMerchantCustomer(Connection con) throws Exception {

        PreparedStatement getMaxCusIdState = null;
        String maxAcc = null;
        try {
            getMaxCusIdState = con.prepareStatement("SELECT COALESCE(MAX(MC.MERCHANTACCOUNTNO),'0') AS MAXACCOUNTNO"
                    + " FROM MERCHANTCUSTOMER MC");

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

    public HashMap<String, String> getCurrency(Connection CMSCon) throws Exception {
        PreparedStatement getcurrencyList = null;
        currencyTypeList = new HashMap<String, String>();
        try {
            getcurrencyList = CMSCon.prepareStatement(" SELECT C.CURRENCYNUMCODE, C.DESCRIPTION "
                    + " FROM CURRENCY C WHERE C.STATUS = ?");

            getcurrencyList.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = getcurrencyList.executeQuery();

            while (rs.next()) {

                currencyTypeList.put(this.zeroPadding(Integer.toString(rs.getInt("CURRENCYNUMCODE"))), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getcurrencyList.close();

        }
        return currencyTypeList;
    }

    public String getCommonParaRedemValue(Connection CMSCon) throws Exception {
        String redemValue = null;
        PreparedStatement getRedem = null;
        try {

            getRedem = CMSCon.prepareStatement("SELECT REDEMPTIONPOINTVALUE FROM COMMONPARAMETER");

            rs = getRedem.executeQuery();

            while (rs.next()) {
                redemValue = rs.getString("REDEMPTIONPOINTVALUE");
            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getRedem.close();

        }
        return redemValue;
    }
    
    public List<FeeProfileBean> getAllFeeProfileDetail(Connection con,String type) throws SQLException, Exception {
        PreparedStatement ps = null;
        try {
            List<FeeProfileBean> feeProfBeanList = new ArrayList<FeeProfileBean>();

            String query = "SELECT FP.FEEPROFILECODE,FP.DESCRIPTION,"
                    + " FP.FEECATEGORY,FP.STATUS,S.DESCRIPTION AS STATUSDES "
                    + " FROM FEEPROFILE FP,STATUS S WHERE S.STATUSCODE=FP.STATUS AND FEECATEGORY = ?";


            ps = con.prepareStatement(query);
            
            ps.setString(1, type);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                FeeProfileBean bean = new FeeProfileBean();

                bean.setFeeProCode(rs.getString("FEEPROFILECODE"));
                bean.setFeeProDes(rs.getString("DESCRIPTION"));
                bean.setFeeCategory(rs.getString("FEECATEGORY"));
                bean.setStaus(rs.getString("STATUS"));
                bean.setStausDes(rs.getString("STATUSDES"));

                feeProfBeanList.add(bean);
            }

            return feeProfBeanList;
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
