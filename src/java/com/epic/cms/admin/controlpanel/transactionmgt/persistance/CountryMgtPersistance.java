/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.CountryMgtBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *this class use to wrote all the persistence class that relate country management 
 * @author ayesh
 */
public class CountryMgtPersistance {

    private ResultSet rs = null;

    /**
     * insert new country persistence 
     * @return int row-number of rows that add to database
     */
    public int insertNewCountryPer(CountryMgtBean bean, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO COUNTRY CR (CR.COUNTRYNUMCODE,"
                    + "CR.COUNTRYALPHA2CODE,CR.COUNTRYALPHA3CODE,CR.DESCRIPTION,"
                    + "CR.FRAUDVALUE,CR.REGEON,CR.LASTUPDATEDUSER) VALUES(?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(bean.getCountryCode()));
            ps.setString(2, bean.getAlphaFirst());
            ps.setString(3, bean.getAlphaSecond());
            ps.setString(4, bean.getDescription());
            ps.setString(5, bean.getFrdVal());
            ps.setString(6, bean.getRegion());
            ps.setString(7, bean.getLastUpdateUser());


            row = ps.executeUpdate();

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

    public int insertNewCountryPerToOnline(CountryMgtBean bean, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {

            String query = "INSERT INTO ECMS_ONLINE_COUNTRY_CODE(CODE,VALUE,DESCRIPTION,STATUS) VALUES(?,?,?,?)";

            ps = con.prepareStatement(query);

            String cuncode = bean.getCountryCode();
            String cuncodenew = this.zeroPadding(cuncode);

            ps.setString(1, cuncodenew);
            ps.setString(2, bean.getAlphaSecond());
            ps.setString(3, bean.getDescription());

            ps.setInt(4, 1);  //danata 1 danawa, back end eka anuwa wenas karanna passe....

            row = ps.executeUpdate();

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

    /**
     * get all country details information persistence
     * @param con
     * @return
     * @throws Exception 
     */
    public List<CountryMgtBean> getAllExchangeRateDetails(Connection con) throws Exception {
        PreparedStatement ps = null;
        try {
            List<CountryMgtBean> countryList = new ArrayList<CountryMgtBean>();
            String query = "SELECT CR.COUNTRYNUMCODE,CR.COUNTRYALPHA2CODE,CR.COUNTRYALPHA3CODE,"
                    + "CR.DESCRIPTION,CR.FRAUDVALUE,CR.REGEON,CR.LASTUPDATEDUSER,"
                    + "CR.LASTUPDATEDTIME,CR.CREATEDTIME FROM COUNTRY CR ORDER BY CR.DESCRIPTION ASC";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                CountryMgtBean bean = new CountryMgtBean();
                
                String concode = rs.getString("COUNTRYNUMCODE");
                String concodenew = this.zeroPadding(concode);                
                
                bean.setCountryCode(concodenew);
                
                bean.setAlphaFirst(rs.getString("COUNTRYALPHA2CODE"));
                bean.setAlphaSecond(rs.getString("COUNTRYALPHA3CODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setFrdVal(rs.getString("FRAUDVALUE"));
                bean.setRegion(rs.getString("REGEON"));
                bean.setLastUpdateUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdateDate(rs.getDate("LASTUPDATEDTIME"));
                bean.setCreateDate(rs.getDate("CREATEDTIME"));
                countryList.add(bean);

            }


            return countryList;
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

    /**
     * update country persistence 
     * @param bean CountryMgtBean
     * @param con Connection
     * @return row-int number of count 
     * @throws SQLException
     * @throws Exception 
     */
    public int updateCountryPer(CountryMgtBean bean, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE COUNTRY EX SET "
                    + "EX.COUNTRYALPHA2CODE=?,EX.COUNTRYALPHA3CODE=?,EX.DESCRIPTION=? ,"
                    + "EX.FRAUDVALUE=?,EX.REGEON=?,EX.LASTUPDATEDUSER=?,EX.LASTUPDATEDTIME=SYSDATE "
                    + "WHERE EX.COUNTRYNUMCODE=? ";

            ps = con.prepareStatement(query);

            ps.setString(1, bean.getAlphaFirst());
            ps.setString(2, bean.getAlphaSecond());
            ps.setString(3, bean.getDescription());
            ps.setString(4, bean.getFrdVal());
            ps.setString(5, bean.getRegion());
            ps.setString(6, bean.getLastUpdateUser());
            //ps.setTimestamp(7, SystemDateTime.getSystemDataAndTime());
            ps.setInt(7, Integer.parseInt(bean.getCountryCode()));


            row = ps.executeUpdate();

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

    public int updateCountryPerToOnline(CountryMgtBean bean, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE ECMS_ONLINE_COUNTRY_CODE SET VALUE=?,DESCRIPTION=?,STATUS=? WHERE CODE=? ";

            ps = con.prepareStatement(query);

            ps.setString(1, bean.getAlphaSecond());
            ps.setString(2, bean.getDescription());

            ps.setInt(3, 1);  

            String cuncode = bean.getCountryCode();
            String cuncodenew = this.zeroPadding(cuncode);

            ps.setString(4, cuncodenew);

            row = ps.executeUpdate();

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

    /**
     * delete country code
     * @param con
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteCountryPer(Connection con, int countryCode) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM COUNTRY EX where EX.COUNTRYNUMCODE =?";

            ps = con.prepareStatement(query);
            ps.setInt(1, countryCode);

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

    public int deleteCountryPerFromOnline(Connection con, int countryCode) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM ECMS_ONLINE_COUNTRY_CODE WHERE CODE =?";

            ps = con.prepareStatement(query);

            String cuncode = String.valueOf(countryCode);
            String cuncodenew = this.zeroPadding(cuncode);

            ps.setString(1, cuncodenew);

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

    /*
     * to put zeros to the front of code if there is less than 3 digits
     */
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
