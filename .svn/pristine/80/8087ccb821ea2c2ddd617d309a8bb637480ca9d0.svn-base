/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.persistance;

import com.epic.cms.admin.controlpanel.profilemgt.bean.FeeProfileBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
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
 * this class use to wrote all the persistent that relate to fee profile management  
 * @author ayesh
 */
public class FeeProfileMgtPersisitance {

    private ResultSet rs = null;

    /**
     * get all details about fee profile
     * @param con
     * @return free profile bean list
     */
    public List<FeeProfileBean> getFeeProfileInfo(Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;
        try {
            List<FeeProfileBean> beanList = new ArrayList<FeeProfileBean>();

            String query = "SELECT CF.CARDHOLDERFEEPROFILECODE,CU.DESCRIPTION AS CURRENCYDES,CF.DESCRIPTION,"
                    + "ST.DESCRIPTION AS STATUSDES,CF.STATUS,CF.CURRENCYCODE ,CF.JOININGFEE ,CF.ANUALFEE ,"
                    + "CF.RENEWALFEE , cf.REPLACEMENTFEE, cf.LATEPAYMENTFEE,CF.LEGALFEE ,"
                    + "CF.RETURNCHEQUEFEE ,CF.REJECTAUTOPAYFEE ,CF.LIMITEXCEEDFEE ,"
                    + "CF.TEMPORARYLIMITEINCREASEFEE ,CF.PERMENANTLIMITINCREASEFEE ,"
                    + "CF.CARDTYPECHANGEFEE ,CF.STATEMENTCOPYFEE ,CF.LASTUPDATEDUSER ,"
                    + "CF.LASTUPDATEDTIME ,CF.CREATETIME FROM CARDHOLDERFEE CF,CURRENCY CU,"
                    + "STATUS ST WHERE CU.CURRENCYNUMCODE=CF.CURRENCYCODE AND "
                    + "ST.STATUSCODE=CF.STATUS";


            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                FeeProfileBean bean = new FeeProfileBean();

                bean.setFeeProCode(rs.getString("CARDHOLDERFEEPROFILECODE"));
                bean.setFeeProDes(rs.getString("DESCRIPTION"));
                bean.setCurrencyDes(rs.getString("CURRENCYDES"));
                bean.setStaus(rs.getString("STATUS"));
                bean.setStausDes(rs.getString("STATUSDES"));
                bean.setCurrencyCode(rs.getString("CURRENCYCODE"));
                bean.setJoinFee(rs.getString("JOININGFEE"));
                bean.setAnnualFee(rs.getString("ANUALFEE"));
                bean.setReplaceFee(rs.getString("REPLACEMENTFEE"));
                bean.setRenewalFee(rs.getString("RENEWALFEE"));
                bean.setLatePayFee(rs.getString("LATEPAYMENTFEE"));
                bean.setLegalFee(rs.getString("LEGALFEE"));
                bean.setRetunChgeFee(rs.getString("RETURNCHEQUEFEE"));
                bean.setRejectAutoFee(rs.getString("REJECTAUTOPAYFEE"));
                bean.setLimitExceedFee(rs.getString("LIMITEXCEEDFEE"));
                bean.setTempLimitFee(rs.getString("TEMPORARYLIMITEINCREASEFEE"));
                bean.setPerLimitIncreaseFee(rs.getString("PERMENANTLIMITINCREASEFEE"));
                bean.setCardTypeFee(rs.getString("CARDTYPECHANGEFEE"));
                bean.setStatementCopyFee(rs.getString("STATEMENTCOPYFEE"));
                bean.setLastUpdateUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdateDate(rs.getDate("LASTUPDATEDTIME"));
                bean.setCreateDate(rs.getDate("CREATETIME"));

                beanList.add(bean);
            }

            return beanList;
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

    public List<FeeProfileBean> getAllFeeProfileDetail(Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;
        try {
            List<FeeProfileBean> feeProfBeanList = new ArrayList<FeeProfileBean>();

            String query = "SELECT FP.FEEPROFILECODE,FP.DESCRIPTION,FP.FEECATEGORY,FP.STATUS,S.DESCRIPTION AS STATUSDES,FP.LASTUPDATEDUSER AS USERR FROM FEEPROFILE FP,STATUS S WHERE S.STATUSCODE=FP.STATUS";


            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                FeeProfileBean bean = new FeeProfileBean();

                bean.setFeeProCode(rs.getString("FEEPROFILECODE"));
                bean.setFeeProDes(rs.getString("DESCRIPTION"));
                bean.setFeeCategory(rs.getString("FEECATEGORY"));
                bean.setStaus(rs.getString("STATUS"));
                bean.setStausDes(rs.getString("STATUSDES"));
                bean.setLastUpdateUser(rs.getString("USERR"));
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

    public List<FeeBean> getAllFeeDetail(Connection con, String feeCategory) throws SQLException, Exception {
        PreparedStatement ps = null;
        try {
            List<FeeBean> feeBeanList = new ArrayList<FeeBean>();

            String query = "SELECT F.FEECODE,F.DESCRIPTION,F.FEETYPE,F.FEECATEGORY,F.CURRENCYCODE,F.STATUS,S.DESCRIPTION AS STATUSDES,F.CRORDR,F.FLATFEE,F.PERSENTAGE,F.COMBINATION,F.MINIMUMAMOUNT,F.MAXIMUMAMOUNT FROM FEE F,STATUS S WHERE S.STATUSCODE=F.STATUS AND F.FEECATEGORY=?";


            ps = con.prepareStatement(query);
            ps.setString(1, feeCategory);
            rs = ps.executeQuery();
            while (rs.next()) {
                FeeBean bean = new FeeBean();

                bean.setFeeCode(rs.getString("FEECODE"));
                bean.setFeeDes(rs.getString("DESCRIPTION"));
                bean.setFeeType(rs.getString("FEETYPE"));
                bean.setFeeCategory(rs.getString("FEECATEGORY"));
                bean.setCurrency(rs.getString("CURRENCYCODE"));
                bean.setCrordr(rs.getString("CRORDR"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STATUSDES"));
                bean.setFlatFee(rs.getString("FLATFEE"));
                bean.setPercentage(rs.getString("PERSENTAGE"));
                bean.setOption(rs.getString("COMBINATION"));
                bean.setMinAmount(rs.getString("MINIMUMAMOUNT"));
                bean.setMaxAmount(rs.getString("MAXIMUMAMOUNT"));

                feeBeanList.add(bean);
            }

            return feeBeanList;
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

    public Boolean insertFeeProfile(FeeProfileBean feeProf, List<FeeBean> txnList, List<FeeBean> serviceList, Connection con) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO FEEPROFILE (FEEPROFILECODE,DESCRIPTION,FEECATEGORY,STATUS,LASTUPDATEDUSER,CREATEDTIME,EFFECTIVEDATE) "
                    + "VALUES(?,?,?,?,?,SYSDATE,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, feeProf.getFeeProCode());
            ps.setString(2, feeProf.getFeeProDes());
            ps.setString(3, feeProf.getFeeCategory());
            ps.setString(4, feeProf.getStaus());
            ps.setString(5, feeProf.getLastUpdateUser());
            ps.setTimestamp(6, new Timestamp(this.stringTodate(feeProf.getEffectiveDate()).getTime()));
          //  ps.setTimestamp(6, SystemDateTime.getSystemDataAndTime());


            int i = ps.executeUpdate();
            if (i == 1) {
                success = true;
            }

            for (int k = 0; k < txnList.size(); k++) {

                ps = con.prepareStatement("INSERT INTO FEEPROFILEFEE "
                        + "(FEEPROFILECODE,FEECODE,CURRENCYCODE,CRORDR,FLATFEE,PERSENTAGE,COMBINATION,MINIMUMAMOUNT,MAXIMUMAMOUNT,LASTUPDATEDUSER,CREATEDTIME ) "
                        + " VALUES (?,?,?,?,?,?,?,?,?,?,SYSDATE) ");

                ps.setString(1, feeProf.getFeeProCode());
                ps.setString(2, txnList.get(k).getFeeCode());
                ps.setInt(3, Integer.parseInt(txnList.get(k).getCurrency()));
                ps.setString(4, txnList.get(k).getCrordr());
                ps.setDouble(5, Double.parseDouble(txnList.get(k).getFlatFee()));
                ps.setDouble(6, Double.parseDouble(txnList.get(k).getPercentage()));
                ps.setString(7, txnList.get(k).getOption());
                ps.setDouble(8, Double.parseDouble(txnList.get(k).getMinAmount()));
                ps.setDouble(9, Double.parseDouble(txnList.get(k).getMaxAmount()));
                ps.setString(10, feeProf.getLastUpdateUser());
              //  ps.setTimestamp(11, SystemDateTime.getSystemDataAndTime());


                int j = 0;
                j = ps.executeUpdate();
                if (j == 0) {
                    throw new Exception();
                }
            }

            for (int k = 0; k < serviceList.size(); k++) {

                ps = con.prepareStatement("INSERT INTO FEEPROFILEFEE "
                        + "(FEEPROFILECODE,FEECODE,CURRENCYCODE,CRORDR,FLATFEE,PERSENTAGE,COMBINATION,MINIMUMAMOUNT,MAXIMUMAMOUNT,LASTUPDATEDUSER,CREATEDTIME ) "
                        + " VALUES (?,?,?,?,?,?,?,?,?,?,SYSDATE) ");

                ps.setString(1, feeProf.getFeeProCode());
                ps.setString(2, serviceList.get(k).getFeeCode());
                ps.setInt(3, Integer.parseInt(serviceList.get(k).getCurrency()));
                ps.setString(4, serviceList.get(k).getCrordr());
                ps.setDouble(5, Double.parseDouble(serviceList.get(k).getFlatFee()));
                ps.setDouble(6, Double.parseDouble(serviceList.get(k).getPercentage()));
                ps.setString(7, serviceList.get(k).getOption());
                ps.setDouble(8, Double.parseDouble(serviceList.get(k).getMinAmount()));
                ps.setDouble(9, Double.parseDouble(serviceList.get(k).getMaxAmount()));
                ps.setString(10, feeProf.getLastUpdateUser());
              //  ps.setTimestamp(11, SystemDateTime.getSystemDataAndTime());


                int j = 0;
                j = ps.executeUpdate();
                if (j == 0) {
                    throw new Exception();
                }
            }


        } catch (Exception ex) {
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

    public FeeProfileBean viewOneFeeProfile(Connection con, String id) throws SQLException, Exception {

        FeeProfileBean feeProfBean = new FeeProfileBean();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            String query = "SELECT FP.EFFECTIVEDATE,FP.FEEPROFILECODE,FP.DESCRIPTION,FP.FEECATEGORY,FP.STATUS,S.DESCRIPTION AS STATUSDES FROM FEEPROFILE FP,STATUS S WHERE S.STATUSCODE=FP.STATUS AND FP.FEEPROFILECODE=?";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {

                feeProfBean.setFeeProCode(rs.getString("FEEPROFILECODE"));
                feeProfBean.setFeeProDes(rs.getString("DESCRIPTION"));
                feeProfBean.setFeeCategory(rs.getString("FEECATEGORY"));
                feeProfBean.setStaus(rs.getString("STATUS"));
                feeProfBean.setStausDes(rs.getString("STATUSDES"));
                if (rs.getDate("EFFECTIVEDATE") != null) {
                    feeProfBean.setEffectiveDate(this.convertStringDate(rs.getDate("EFFECTIVEDATE")));
                }
            }
            return feeProfBean;

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

    public List<FeeBean> viewFeeProfileFee(Connection con, String feeProfileCode) throws SQLException, Exception {
        PreparedStatement ps = null;
        try {
            List<FeeBean> feeBeanList = new ArrayList<FeeBean>();

            String query = "SELECT FPF.FEECODE,FPF.CURRENCYCODE,FPF.CRORDR,FPF.FLATFEE,FPF.PERSENTAGE,FPF.COMBINATION,FPF.MINIMUMAMOUNT,FPF.MAXIMUMAMOUNT FROM FEEPROFILEFEE FPF WHERE FPF.FEEPROFILECODE=?";


            ps = con.prepareStatement(query);
            ps.setString(1, feeProfileCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                FeeBean bean = new FeeBean();

                bean.setFeeCode(rs.getString("FEECODE"));
                bean.setCurrency(rs.getString("CURRENCYCODE"));
                bean.setCrordr(rs.getString("CRORDR"));
                bean.setFlatFee(rs.getString("FLATFEE"));
                bean.setPercentage(rs.getString("PERSENTAGE"));
                bean.setOption(rs.getString("COMBINATION"));
                bean.setMinAmount(rs.getString("MINIMUMAMOUNT"));
                bean.setMaxAmount(rs.getString("MAXIMUMAMOUNT"));

                feeBeanList.add(bean);
            }

            return feeBeanList;
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

    public FeeBean viewOneFee(Connection con, String feeCode) throws SQLException, Exception {
        PreparedStatement ps = null;
        try {
            FeeBean bean = new FeeBean();

            String query = "SELECT F.FEECODE,F.DESCRIPTION,F.FEETYPE,F.FEECATEGORY,F.CURRENCYCODE,F.STATUS,S.DESCRIPTION AS STATUSDES,"
                    + "F.CRORDR,F.FLATFEE,F.PERSENTAGE,F.COMBINATION,F.MINIMUMAMOUNT,F.MAXIMUMAMOUNT FROM FEE F,STATUS S "
                    + "WHERE S.STATUSCODE=F.STATUS AND F.FEECODE=?";


            ps = con.prepareStatement(query);
            ps.setString(1, feeCode);
            rs = ps.executeQuery();
            while (rs.next()) {

                bean.setFeeCode(rs.getString("FEECODE"));
                bean.setFeeDes(rs.getString("DESCRIPTION"));
                bean.setFeeType(rs.getString("FEETYPE"));
                bean.setFeeCategory(rs.getString("FEECATEGORY"));
                bean.setCurrency(rs.getString("CURRENCYCODE"));
                bean.setCrordr(rs.getString("CRORDR"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STATUSDES"));
                bean.setFlatFee(rs.getString("FLATFEE"));
                bean.setPercentage(rs.getString("PERSENTAGE"));
                bean.setOption(rs.getString("COMBINATION"));
                bean.setMinAmount(rs.getString("MINIMUMAMOUNT"));
                bean.setMaxAmount(rs.getString("MAXIMUMAMOUNT"));
            }
            return bean;
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

    public int deleteFeeProfile(Connection con, String feeProfCode) throws SQLException, Exception {

        int i = -1;
        PreparedStatement ps = null;

        try {
            String query = "DELETE FROM FEEPROFILE WHERE FEEPROFILECODE =?";

            ps = con.prepareStatement(query);
            ps.setString(1, feeProfCode);
            i = ps.executeUpdate();

            String query2 = "DELETE FROM FEEPROFILEFEE WHERE FEEPROFILECODE =?";

            ps = con.prepareStatement(query2);
            ps.setString(1, feeProfCode);
            int j = 0;
            j = ps.executeUpdate();
            if (j == 0) {
                throw new Exception();
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
        return i;
    }

    public int UpdateFeeProfile(FeeProfileBean feeProf, List<FeeBean> txnList, List<FeeBean> serviceList, Connection con) throws Exception {
        int row = -1;

        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        int j = 0;

        try {

            //**********************************update fee profiles*********************************************
            String query = "UPDATE FEEPROFILE SET "
                    + "FEEPROFILECODE=?,DESCRIPTION=?,FEECATEGORY=?,STATUS=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE, "
                    + "EFFECTIVEDATE=? WHERE FEEPROFILECODE=? ";

            ps = con.prepareStatement(query);

            ps.setString(1, feeProf.getFeeProCode());
            ps.setString(2, feeProf.getFeeProDes());
            ps.setString(3, feeProf.getFeeCategory());
            ps.setString(4, feeProf.getStaus());
            ps.setString(5, feeProf.getLastUpdateUser());
            ps.setTimestamp(6, new Timestamp(this.stringTodate(feeProf.getEffectiveDate()).getTime()));
            ps.setString(7, feeProf.getFeeProCode());

            row = ps.executeUpdate();

            //********************************check for existing fee rpofile fees*******************************

            String query1 = "SELECT * FROM FEEPROFILEFEE WHERE FEEPROFILECODE =?";
            ps1 = con.prepareStatement(query1);
            ps1.setString(1, feeProf.getFeeProCode());

            int fpfCount = 0;
            fpfCount = ps1.executeUpdate();

            if (fpfCount > 0) {

                //********************************delete fee profile fees*******************************************
                ps = con.prepareStatement("DELETE FROM FEEPROFILEFEE WHERE FEEPROFILECODE =?");
                ps.setString(1, feeProf.getFeeProCode());
                j = 0;
                j = ps.executeUpdate();
                if (j == 0) {
                    throw new Exception();
                }
            }
            //********************************insert fee profile fees******************************************

            if (txnList != null) {
                for (int k = 0; k < txnList.size(); k++) {

                    ps = con.prepareStatement("INSERT INTO FEEPROFILEFEE "
                            + "(FEEPROFILECODE,FEECODE,CURRENCYCODE,CRORDR,FLATFEE,PERSENTAGE,COMBINATION,MINIMUMAMOUNT,MAXIMUMAMOUNT,LASTUPDATEDUSER,CREATEDTIME ) "
                            + " VALUES (?,?,?,?,?,?,?,?,?,?,SYSDATE) ");

                    ps.setString(1, feeProf.getFeeProCode());
                    ps.setString(2, txnList.get(k).getFeeCode());
                    ps.setInt(3, Integer.parseInt(txnList.get(k).getCurrency()));
                    ps.setString(4, txnList.get(k).getCrordr());
                    ps.setDouble(5, Double.parseDouble(txnList.get(k).getFlatFee()));
                    ps.setDouble(6, Double.parseDouble(txnList.get(k).getPercentage()));
                    ps.setString(7, txnList.get(k).getOption());
                    ps.setDouble(8, Double.parseDouble(txnList.get(k).getMinAmount()));
                    ps.setDouble(9, Double.parseDouble(txnList.get(k).getMaxAmount()));
                    ps.setString(10, feeProf.getLastUpdateUser());
                   // ps.setTimestamp(11, SystemDateTime.getSystemDataAndTime());


                    j = 0;
                    j = ps.executeUpdate();
                    if (j == 0) {
                        throw new Exception();
                    }
                }

            }
            if (serviceList != null) {
                for (int k = 0; k < serviceList.size(); k++) {

                    ps = con.prepareStatement("INSERT INTO FEEPROFILEFEE "
                            + "(FEEPROFILECODE,FEECODE,CURRENCYCODE,CRORDR,FLATFEE,PERSENTAGE,COMBINATION,MINIMUMAMOUNT,MAXIMUMAMOUNT,LASTUPDATEDUSER,CREATEDTIME ) "
                            + " VALUES (?,?,?,?,?,?,?,?,?,?,SYSDATE) ");

                    ps.setString(1, feeProf.getFeeProCode());
                    ps.setString(2, serviceList.get(k).getFeeCode());
                    ps.setInt(3, Integer.parseInt(serviceList.get(k).getCurrency()));
                    ps.setString(4, serviceList.get(k).getCrordr());
                    ps.setDouble(5, Double.parseDouble(serviceList.get(k).getFlatFee()));
                    ps.setDouble(6, Double.parseDouble(serviceList.get(k).getPercentage()));
                    ps.setString(7, serviceList.get(k).getOption());
                    ps.setDouble(8, Double.parseDouble(serviceList.get(k).getMinAmount()));
                    ps.setDouble(9, Double.parseDouble(serviceList.get(k).getMaxAmount()));
                    ps.setString(10, feeProf.getLastUpdateUser());
                    //ps.setTimestamp(11, SystemDateTime.getSystemDataAndTime());

                    j = 0;
                    j = ps.executeUpdate();
                    if (j == 0) {
                        throw new Exception();
                    }
                }
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

        return row;
    }

    public List<CurrencyBean> getCurrencyDetails(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<CurrencyBean> currencyDetails = new ArrayList<CurrencyBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT C.CURRENCYNUMCODE,C.DESCRIPTION FROM CURRENCY C "
                    + "WHERE C.STATUS = 'ACT'");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                CurrencyBean currency = new CurrencyBean();

                currency.setCurrencyCode(zeroPad(rs.getString("CURRENCYNUMCODE")));
                currency.setCurrencyDes(rs.getString("DESCRIPTION"));

                currencyDetails.add(currency);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return currencyDetails;
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
    
    public Date stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = dateFormat.parse(dateString);

        return convertedDate;

    }
    
     private String convertStringDate(java.sql.Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return date != null ? (dateFormat.format(date)) : "";
    }
}