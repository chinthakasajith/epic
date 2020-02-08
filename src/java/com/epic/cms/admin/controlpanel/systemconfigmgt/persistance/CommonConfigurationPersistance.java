/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CommonConfigurationBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.resource.NotSupportedException;

/**
 *
 * @author ruwan_e
 */
public class CommonConfigurationPersistance {
    private ResultSet rs = null;
    private CommonConfigurationBean commonConfigurationBean;
    Connection connection = null;

    public CommonConfigurationBean getCommonConfiguration( Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;
        CommonConfigurationBean bean = null;
        try {
            String query = "SELECT * FROM COMMONPARAMETER";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            
           
            while (rs.next()) {
                bean = new CommonConfigurationBean();
                
                bean.setBank(rs.getString("BANKCODE"));
                bean.setBaseCurrency(rs.getString("BASECURRENCY"));
                bean.setCountry(rs.getString("COUNTRYCODE"));
                bean.setAccumulationPointValue(rs.getDouble("ACCUMULATIONPOINTVALUE"));
                bean.setRedemptionPointValue(rs.getDouble("REDEMPTIONPOINTVALUE"));
                bean.setBatchTimeout(rs.getInt("PAYMENTBATCHTIMEOUT"));
                bean.setMaxPerPaymentAmount(rs.getDouble("MAXPERPAYMENTAMOUNT"));
                bean.setMaxPerBatchAmount(rs.getDouble("MAXPERBATCHAMOUNT"));
                bean.setUserRole(rs.getString("DATACAPTURINGROLE"));
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

    public int updateCommonConfiguration(CommonConfigurationBean commonConfigurationBean, Connection con) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE COMMONPARAMETER" +
                    " SET BANKCODE=?, BASECURRENCY=?, COUNTRYCODE=?, ACCUMULATIONPOINTVALUE=?, REDEMPTIONPOINTVALUE=?, " + 
                    "PAYMENTBATCHTIMEOUT = ?, MAXPERPAYMENTAMOUNT = ?, MAXPERBATCHAMOUNT = ? ,DATACAPTURINGROLE= ?";
            
            ps = con.prepareStatement(query);
            ps.setString(1, commonConfigurationBean.getBank());
            ps.setString(2, commonConfigurationBean.getBaseCurrency());
            ps.setString(3, commonConfigurationBean.getCountry());
            ps.setDouble(4, commonConfigurationBean.getAccumulationPointValue());
            ps.setDouble(5, commonConfigurationBean.getRedemptionPointValue());
            ps.setInt(6, commonConfigurationBean.getBatchTimeout());
            ps.setDouble(7, commonConfigurationBean.getMaxPerPaymentAmount());
            ps.setDouble(8, commonConfigurationBean.getMaxPerBatchAmount());
            ps.setString(9, commonConfigurationBean.getUserRole());

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
        return i;
    }
    
       public int setCommonConfiguration(CommonConfigurationBean bean, Connection con)throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO COMMONPARAMETER(BANKCODE,BASECURRENCY,COUNTRYCODE,"
                    + "ACCUMULATIONPOINTVALUE,REDEMPTIONPOINTVALUE,PAYMENTBATCHTIMEOUT,MAXPERPAYMENTAMOUNT,MAXPERBATCHAMOUNT,DATACAPTURINGROLE) VALUES(?,?,?,?,?,?,?,?,?)";
            
            ps = con.prepareStatement(query);
            ps.setString(1, bean.getBank());
            ps.setString(2, bean.getBaseCurrency());
            ps.setString(3, bean.getCountry());
            ps.setDouble(4, bean.getAccumulationPointValue());
            ps.setDouble(5, bean.getRedemptionPointValue());
            ps.setInt(6, bean.getBatchTimeout());
            ps.setDouble(7, bean.getMaxPerPaymentAmount());
            ps.setDouble(8, bean.getMaxPerBatchAmount());
            ps.setString(9, bean.getUserRole());

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
        return i;
    }
     
   public boolean   isRecordAvailable( Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;
        int x = 0;
        try {
            String query = "SELECT COUNT(*) AS COUNT FROM COMMONPARAMETER";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            
           
            while (rs.next()) {
                x = rs.getInt("COUNT");
            }

           
            return x>0;
            
            
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

    public void deleteCommonConfiguration() throws NotSupportedException {
        throw new NotSupportedException();
    }
}
