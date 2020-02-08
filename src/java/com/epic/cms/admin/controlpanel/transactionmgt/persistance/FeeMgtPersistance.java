/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeTypeBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import com.epic.cms.system.util.session.SessionVarList;
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
public class FeeMgtPersistance {

    private ResultSet rs;
    private SessionVarList sessionVarlist;

    public List<FeeBean> getFeeDetails(Connection con) throws Exception {
        PreparedStatement getAllFee = null;
        List<FeeBean> feeList = new ArrayList<FeeBean>();
        try {
            getAllFee = con.prepareStatement("SELECT F.FEECODE, F.DESCRIPTION, F.FEETYPE,F.FEECATEGORY,"
                    + " F.CURRENCYCODE,F.STATUS,F.CRORDR,F.FLATFEE,F.PERSENTAGE,F.COMBINATION,"
                    + " F.MINIMUMAMOUNT,F.MAXIMUMAMOUNT,F.LASTUPDATEDUSER,F.LASTUPDATEDTIME,F.CREATEDTIME,"
                    + " S.DESCRIPTION AS SDESCRIPTION, C.DESCRIPTION AS CDESCRIPTION"
                    + " FROM FEE F,STATUS S,CURRENCY C "
                    + " WHERE S.STATUSCODE=F.STATUS AND C.CURRENCYNUMCODE=F.CURRENCYCODE  ");

            rs = getAllFee.executeQuery();

            while (rs.next()) {

                FeeBean fee = new FeeBean();

                fee.setFeeCode(rs.getString("FEECODE"));
                fee.setFeeDes(rs.getString("DESCRIPTION"));
                fee.setFeeType(rs.getString("FEETYPE"));
                fee.setFeeCategory(rs.getString("FEECATEGORY"));
                fee.setCurrency(zeroPadding(Integer.toString(rs.getInt("CURRENCYCODE"))));
                fee.setStatus(rs.getString("STATUS"));
                fee.setCrordr(rs.getString("CRORDR"));
                fee.setFlatFee(Double.toString(rs.getDouble("FLATFEE")));
                fee.setPercentage(Double.toString(rs.getDouble("PERSENTAGE")));
                fee.setOption(rs.getString("COMBINATION"));
                fee.setMinAmount(Double.toString(rs.getDouble("MINIMUMAMOUNT")));
                fee.setMaxAmount(Double.toString(rs.getDouble("MAXIMUMAMOUNT")));
                fee.setLastUpdateUser(rs.getString("LASTUPDATEDUSER"));
                fee.setLastUpdateDate(rs.getDate("LASTUPDATEDTIME"));
                fee.setCreateDate(rs.getDate("CREATEDTIME"));
                fee.setStatusDes(rs.getString("SDESCRIPTION"));
                fee.setCurrencyDes(rs.getString("CDESCRIPTION"));



                feeList.add(fee);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllFee.close();

        }
        return feeList;
    }

    public boolean feeInsert(FeeBean feeBean, Connection con) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO FEE"
                    + " (FEECODE,DESCRIPTION,FEETYPE,FEECATEGORY,CURRENCYCODE,STATUS,CRORDR,FLATFEE,PERSENTAGE,"
                    + " COMBINATION,MINIMUMAMOUNT,MAXIMUMAMOUNT,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATEDTIME)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE) ");



            insertStat.setString(1, feeBean.getFeeCode());
            insertStat.setString(2, feeBean.getFeeDes());
            insertStat.setString(3, feeBean.getFeeType());
            insertStat.setString(4, feeBean.getFeeCategory());
            insertStat.setInt(5, Integer.parseInt(zeroPadding(feeBean.getCurrency())));
            insertStat.setString(6, feeBean.getStatus());
            insertStat.setString(7, feeBean.getCrordr());
            insertStat.setDouble(8, Double.parseDouble(feeBean.getFlatFee()));
            insertStat.setDouble(9, Double.parseDouble(feeBean.getPercentage()));
            insertStat.setString(10, feeBean.getOption());
            insertStat.setDouble(11, Double.parseDouble(feeBean.getMinAmount()));
            insertStat.setDouble(12, Double.parseDouble(feeBean.getMaxAmount()));
            insertStat.setString(13, feeBean.getLastUpdateUser());

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean feeUpdate(FeeBean feeBean, Connection con) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE FEE SET "
                    + " FEETYPE=?,FEECATEGORY=?,CURRENCYCODE=?,STATUS=?,CRORDR=?,FLATFEE=?,PERSENTAGE=?,"
                    + " COMBINATION=?,MINIMUMAMOUNT=?,MAXIMUMAMOUNT=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE"
                    + " WHERE FEECODE=? ");




            //updateStat.setString(1, feeBean.getFeeDes());
            updateStat.setString(1, feeBean.getFeeType());
            updateStat.setString(2, feeBean.getFeeCategory());
            updateStat.setInt(3, Integer.parseInt(zeroPadding(feeBean.getCurrency())));
            updateStat.setString(4, feeBean.getStatus());
            updateStat.setString(5, feeBean.getCrordr());
            updateStat.setDouble(6, Double.parseDouble(feeBean.getFlatFee()));
            updateStat.setDouble(7, Double.parseDouble(feeBean.getPercentage()));
            updateStat.setString(8, feeBean.getOption());
            updateStat.setDouble(9, Double.parseDouble(feeBean.getMinAmount()));
            updateStat.setDouble(10, Double.parseDouble(feeBean.getMaxAmount()));
            updateStat.setString(11, feeBean.getLastUpdateUser());

            updateStat.setString(12, feeBean.getFeeCode());

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean feeDelete(FeeBean feeBean, Connection con) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = con.prepareStatement("DELETE FEE WHERE FEECODE=?");

            deleteStat.setString(1, feeBean.getFeeCode());


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

    public String zeroPadding(String code) {
        int len = code.length();

        if (len < 3 && len == 2) {
            code = "0" + code;
        } else if (len < 3 && len == 1) {
            code = "00" + code;
        }

        return code;
    }

    public List<FeeTypeBean> getFeeType(Connection CMSCon) throws Exception {
        PreparedStatement getFeeType = null;
        List<FeeTypeBean> typeList = new ArrayList<FeeTypeBean>();

        try {

            getFeeType = CMSCon.prepareStatement("SELECT DISTINCT FT.FEETYPECODE,FT.DESCRIPTION"
                    + " FROM FEETYPE FT WHERE FT.FEETYPECODE NOT IN "
                    + " (SELECT FEECODE FROM FEE)");

            rs = getFeeType.executeQuery();

            while (rs.next()) {

                FeeTypeBean bean = new FeeTypeBean();

                bean.setFeeTypeCode(rs.getString("FEETYPECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                typeList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    getFeeType.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return typeList;
    }

    public int feeCountMgt(Connection con, FeeBean feeCount) throws SQLException, Exception {
        int success = -1;
        PreparedStatement ps = null;
        int currentFeeCount = 0;

        try {
            String selectQuery = "SELECT FEECOUNT FROM CARDFEECOUNT WHERE CARDNUMBER=? AND FEECODE = ? AND STATUS = 'ACT'";
            ps = con.prepareStatement(selectQuery);
            ps.setString(1, feeCount.getCardNo());
            ps.setString(2, feeCount.getFeeCode());
            rs = ps.executeQuery();
            
            while (rs.next()) { 
                currentFeeCount = Integer.parseInt(rs.getString("FEECOUNT"));  
            }
            
            if(currentFeeCount == 0){
            
                String insertQuery = "INSERT INTO CARDFEECOUNT (CARDNUMBER,FEECODE,FEECOUNT,STATUS,CREATEDDATE,LASTUPDATEDUSER) VALUES(?,?,?,?,?,?)";
                
                ps = con.prepareStatement(insertQuery);
                ps.setString(1, feeCount.getCardNo());
                ps.setString(2, feeCount.getFeeCode());
                ps.setString(3, "1");
                ps.setString(4, feeCount.getStatus());
                ps.setTimestamp(5, SystemDateTime.getSystemDataAndTime());
                ps.setString(6, feeCount.getLastUpdateUser());  
                
                success = ps.executeUpdate();
            
            }if(currentFeeCount != 0){
            
                String updateQuery = "UPDATE CARDFEECOUNT SET FEECOUNT=?,STATUS=?,CREATEDDATE=?,LASTUPDATEDUSER=? WHERE CARDNUMBER=? AND FEECODE=?";
                
                ps = con.prepareStatement(updateQuery);
                
                ps.setString(1, String.valueOf(setFeeCount(currentFeeCount)));
                ps.setString(2, feeCount.getStatus());
                ps.setTimestamp(3, SystemDateTime.getSystemDataAndTime());
                ps.setString(4, feeCount.getLastUpdateUser());    
                ps.setString(5, feeCount.getCardNo());
                ps.setString(6, feeCount.getFeeCode());
                
                success = ps.executeUpdate();  
            }

        } catch (SQLException sqx) {
            throw sqx;
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
        return success;

    }
    
    public int setFeeCount(int currentCount){
        int feeCount = 0;
        feeCount = currentCount + 1;
        return feeCount;
    }
}
