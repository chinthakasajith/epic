/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.persistance;

import com.epic.cms.admin.controlpanel.profilemgt.bean.CommissionProfileBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *this class use to wrote all the persistance that relate to commission profile
 * @author ayesh
 */
public class commissionProfilePersistance {

    private ResultSet rs = null;
    private List<CommissionProfileBean> comisList = null;
    private List<CommissionProfileBean> comisTxnList = null;

    public List<CommissionProfileBean> getCommissionProfileDetails(Connection CMSCon) throws Exception {
        PreparedStatement getCPStat = null;
        comisList = new ArrayList<CommissionProfileBean>();
        try {
            getCPStat = CMSCon.prepareStatement("SELECT CP.COMMISSIONPROFILECODE,CP.DESCRIPTION,"
                    + " CP.STATUS,CP.CURRENCYTYPE,CP.CRDR,CP.FLATAMOUNT,CP.PERCENTAGE,CP.COMBINATION,"
                    + " CP.LASTUPDATEDUSER,CP.LASTUPDATEDTIME,CP.CREATEDTIME,CU.DESCRIPTION AS CUDESCRIPTION,"
                    + " ST.DESCRIPTION AS STDESCRIPTION "
                    + " FROM COMMISSIONPROFILE CP, CURRENCY CU, STATUS ST"
                    + " WHERE CU.CURRENCYNUMCODE=CP.CURRENCYTYPE AND ST.STATUSCODE=CP.STATUS ");


            rs = getCPStat.executeQuery();

            while (rs.next()) {

                CommissionProfileBean bean = new CommissionProfileBean();

                bean.setComProfileCode(rs.getString("COMMISSIONPROFILECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setCurrencyCode(this.zeroPadding(Integer.toString(rs.getInt("CURRENCYTYPE"))));
                bean.setCurrencyDes(rs.getString("CUDESCRIPTION"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STDESCRIPTION"));
                bean.setCrOrdr(rs.getString("CRDR"));
                bean.setFlatAmount(Double.toString(rs.getDouble("FLATAMOUNT")));
                bean.setPercentage(Double.toString(rs.getDouble("PERCENTAGE")));
                bean.setCombination(rs.getString("COMBINATION"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                bean.setCreatedTime(rs.getDate("CREATEDTIME"));

                comisList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getCPStat.close();
        }

        return comisList;
    }

    public List<CommissionProfileBean> getCommissionTxnList(Connection CMSCon) throws Exception {

        PreparedStatement getAllTxn = null;
        comisTxnList = new ArrayList<CommissionProfileBean>();

        try {

            getAllTxn = CMSCon.prepareStatement("SELECT T.TRANSACTIONCODE,T.DESCRIPTION"
                    + " FROM TRANSACTIONTYPE T WHERE T.FINANCIALSTATUS=? AND T.STATUS=?");

            getAllTxn.setString(1, StatusVarList.YESSTATUS);
            getAllTxn.setString(2, StatusVarList.ACTIVE_STATUS);


            rs = getAllTxn.executeQuery();

            while (rs.next()) {

                CommissionProfileBean bean = new CommissionProfileBean();

                bean.setTxnType(rs.getString("TRANSACTIONCODE"));
                bean.setTxnDes(rs.getString("DESCRIPTION"));

                comisTxnList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllTxn.close();
        }
        return comisTxnList;
    }

    public List<CommissionProfileBean> getAssignedCommissionTxnList(Connection CMSCon, String comProfCode) throws Exception {

        PreparedStatement getAllTxn = null;
        comisTxnList = new ArrayList<CommissionProfileBean>();

        try {

            getAllTxn = CMSCon.prepareStatement("SELECT CT.COMMISSIONPROFILECODE,CT.TRANSACTIONCODE,"
                    + " CT.FLATVALUE,CT.PERCENTAGE,CT.COMBINATION,CT.CRDR,TT.DESCRIPTION"
                    + " FROM COMMISSIONTRANSACTION CT,TRANSACTIONTYPE TT "
                    + " WHERE TT.TRANSACTIONCODE=CT.TRANSACTIONCODE AND CT.COMMISSIONPROFILECODE=? ");

            getAllTxn.setString(1, comProfCode);

            rs = getAllTxn.executeQuery();

            while (rs.next()) {

                CommissionProfileBean bean = new CommissionProfileBean();

                bean.setComProfileCode(comProfCode);
                bean.setTxnType(rs.getString("TRANSACTIONCODE"));
                bean.setFlatAmount(Double.toString(rs.getDouble("FLATVALUE")));
                bean.setPercentage(Double.toString(rs.getDouble("PERCENTAGE")));
                bean.setCombination(rs.getString("COMBINATION"));
                bean.setCrOrdr(rs.getString("CRDR"));
                bean.setTxnDes(rs.getString("DESCRIPTION"));

                comisTxnList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllTxn.close();
        }
        return comisTxnList;
    }
    
    public List<CommissionProfileBean> getNotAssignedCommissionTxnList(Connection CMSCon, String comProfCode,List<CommissionProfileBean> comisTxnList) throws Exception {

        PreparedStatement getAllTxn = null;
        
        try {

            getAllTxn = CMSCon.prepareStatement("SELECT DISTINCT TT.TRANSACTIONCODE,TT.DESCRIPTION,"
                    + " CP.CRDR,CP.FLATAMOUNT,CP.PERCENTAGE,CP.COMBINATION "
                    + " FROM TRANSACTIONTYPE TT,COMMISSIONPROFILE CP"
                    + " WHERE TT.TRANSACTIONCODE NOT IN  "
                    + " (SELECT TRANSACTIONCODE FROM COMMISSIONTRANSACTION"
                    + " WHERE COMMISSIONPROFILECODE= ?) AND TT.FINANCIALSTATUS=? AND TT.STATUS=? AND CP.COMMISSIONPROFILECODE= ?");

            getAllTxn.setString(1, comProfCode);
            getAllTxn.setString(2, StatusVarList.YESSTATUS);
            getAllTxn.setString(3, StatusVarList.ACTIVE_STATUS);
            getAllTxn.setString(4, comProfCode);
            
            

            rs = getAllTxn.executeQuery();

            while (rs.next()) {

                CommissionProfileBean bean = new CommissionProfileBean();

                bean.setComProfileCode(comProfCode);
                bean.setTxnType(rs.getString("TRANSACTIONCODE"));
                bean.setFlatAmount(Double.toString(rs.getDouble("FLATAMOUNT")));
                bean.setPercentage(Double.toString(rs.getDouble("PERCENTAGE")));
                bean.setCombination(rs.getString("COMBINATION"));
                bean.setCrOrdr(rs.getString("CRDR"));
                bean.setTxnDes(rs.getString("DESCRIPTION"));

                comisTxnList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllTxn.close();
        }
        return comisTxnList;
    }

    public boolean commissionProfileInsert(Connection CMSCon, CommissionProfileBean comisBean) throws SQLException {
        boolean isSuccess = false;
        PreparedStatement insertStat = null;

        try {

            insertStat = CMSCon.prepareStatement("INSERT INTO COMMISSIONPROFILE"
                    + " (COMMISSIONPROFILECODE,DESCRIPTION,CURRENCYTYPE,"
                    + " STATUS,CRDR,FLATAMOUNT,PERCENTAGE,COMBINATION,"
                    + " LASTUPDATEDUSER,LASTUPDATEDTIME,CREATEDTIME)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE)");

            insertStat.setString(1, comisBean.getComProfileCode());
            insertStat.setString(2, comisBean.getDescription());
            insertStat.setInt(3, Integer.parseInt(comisBean.getCurrencyCode()));
            insertStat.setString(4, comisBean.getStatus());
            insertStat.setString(5, comisBean.getCrOrdr());
            insertStat.setDouble(6, Double.parseDouble(comisBean.getFlatAmount()));
            insertStat.setDouble(7, Double.parseDouble(comisBean.getPercentage()));
            insertStat.setString(8, comisBean.getCombination());
            insertStat.setString(9, comisBean.getLastUpdatedUser());

            insertStat.executeUpdate();
            isSuccess = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }

        return isSuccess;
    }

    public boolean commissionProfileTxnInsert(Connection CMSCon, CommissionProfileBean comisTxnBean, CommissionProfileBean comisBean) throws SQLException {
        boolean isSuccess = false;
        PreparedStatement insertStat = null;

        try {

            insertStat = CMSCon.prepareStatement("INSERT INTO COMMISSIONTRANSACTION"
                    + " (COMMISSIONPROFILECODE,TRANSACTIONCODE,CRDR,FLATVALUE,"
                    + " PERCENTAGE,COMBINATION,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATEDTIME)"
                    + " VALUES (?,?,?,?,?,?,?,SYSDATE,SYSDATE)");

            insertStat.setString(1, comisBean.getComProfileCode());
            insertStat.setString(2, comisTxnBean.getTxnType());
            insertStat.setString(3, comisTxnBean.getCrOrdr());
            insertStat.setDouble(4, Double.parseDouble(comisTxnBean.getFlatAmount()));
            insertStat.setDouble(5, Double.parseDouble(comisTxnBean.getPercentage()));
            insertStat.setString(6, comisTxnBean.getCombination());
            insertStat.setString(7, comisTxnBean.getLastUpdatedUser());

            insertStat.executeUpdate();
            isSuccess = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }

        return isSuccess;
    }

    public boolean commissionProfileUpdate(Connection CMSCon, CommissionProfileBean comisBean) throws SQLException {
        boolean isSuccess = false;
        PreparedStatement insertStat = null;

        try {

            insertStat = CMSCon.prepareStatement("UPDATE COMMISSIONPROFILE SET DESCRIPTION=?,CURRENCYTYPE=?,"
                    + " STATUS=?,LASTUPDATEDUSER=?,CRDR=?,FLATAMOUNT=?,PERCENTAGE=?,COMBINATION=?,"
                    + " LASTUPDATEDTIME=SYSDATE WHERE COMMISSIONPROFILECODE=?");


            insertStat.setString(1, comisBean.getDescription());
            insertStat.setInt(2, Integer.parseInt(comisBean.getCurrencyCode()));
            insertStat.setString(3, comisBean.getStatus());
            insertStat.setString(4, comisBean.getLastUpdatedUser());
            insertStat.setString(5, comisBean.getCrOrdr());
            insertStat.setDouble(6, Double.parseDouble(comisBean.getFlatAmount()));
            insertStat.setDouble(7, Double.parseDouble(comisBean.getPercentage()));
            insertStat.setString(8, comisBean.getCombination());

            insertStat.setString(9, comisBean.getComProfileCode());

            insertStat.executeUpdate();
            isSuccess = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }

        return isSuccess;
    }

    public boolean commissionProfileTxnDelete(Connection CMSCon, CommissionProfileBean comisBean) throws Exception {
        boolean isSuccess = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = CMSCon.prepareStatement("DELETE COMMISSIONTRANSACTION WHERE COMMISSIONPROFILECODE=?");

            deleteStat.setString(1, comisBean.getComProfileCode());


            deleteStat.executeUpdate();
            isSuccess = true;

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
        return isSuccess;
    }

    public boolean commisssionProfileDelete(Connection CMSCon, CommissionProfileBean comisBean) throws Exception {
        boolean isSuccess = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = CMSCon.prepareStatement("DELETE COMMISSIONPROFILE WHERE COMMISSIONPROFILECODE=?");

            deleteStat.setString(1, comisBean.getComProfileCode());


            deleteStat.executeUpdate();
            isSuccess = true;

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
        return isSuccess;
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
