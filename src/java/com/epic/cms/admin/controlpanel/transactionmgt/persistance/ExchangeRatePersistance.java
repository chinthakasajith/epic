/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ExChangeRateBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *this class use to wrote all the db query for exchange rate
 * @author ayesh
 */
public class ExchangeRatePersistance {

    private ResultSet rs = null;

    /**
     * get all currency details
     * @param con List<CurrencyBean>
     * @return 
     * @throws Exception 
     */
    public List<CurrencyBean> getAllCurrency(Connection con) throws Exception {
        PreparedStatement ps = null;
        try {
            List<CurrencyBean> exchngeList = new ArrayList<CurrencyBean>();
            String query = "SELECT CUR.CURRENCYNUMCODE,CUR.DESCRIPTION FROM CURRENCY CUR";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
           
            while (rs.next()) {
                CurrencyBean bean = new CurrencyBean();
                bean.setCurrencyCode(rs.getString("CURRENCYNUMCODE"));
                bean.setCurrencyDes(rs.getString("DESCRIPTION"));
                exchngeList.add(bean);
            }

            return exchngeList;
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
     * insert new exchange rate
     * @param con
     * @param exBean
     * @return
     * @throws Exception 
     */
    public int addNewExchangeRate(Connection con, ExChangeRateBean exBean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO EXCHANGERATE EX (EX.FROMCURRENCY,"
                    + "EX.TOCURRENCY,EX.RATE,EX.LASTUPDATEDUSER) VALUES(?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setInt(1, exBean.getFromCurrency());
            ps.setInt(2, exBean.getToCurrency());
            ps.setString(3, exBean.getRate());
            ps.setString(4, exBean.getLastUpdateUser());

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

    /**
     * get all exchange rate details from database 
     * @param con
     * @return List<ExChangeRateBean>-all details list
     * @throws Exception 
     */
    public List<ExChangeRateBean> getAllExchangeRateDetails(Connection con) throws Exception {
        PreparedStatement ps = null;
        try {
            String zero="0";
            List<ExChangeRateBean> exchngeList = new ArrayList<ExChangeRateBean>();
            String query = "SELECT EXCHANGERATE.RATE,EXCHANGERATE.FROMCURRENCY,EXCHANGERATE.TOCURRENCY, (SELECT DESCRIPTION FROM CURRENCY "
                    + "WHERE CURRENCYNUMCODE=EXCHANGERATE.FROMCURRENCY) AS FROMCUR, "
                    + "(SELECT DESCRIPTION FROM CURRENCY"
                    + " WHERE "
                    + "CURRENCYNUMCODE=EXCHANGERATE.TOCURRENCY) AS TOCUR "
                    + "FROM EXCHANGERATE";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                ExChangeRateBean bean = new ExChangeRateBean();
                bean.setFromCurrency(rs.getInt("FROMCURRENCY"));
                bean.setToCurrency(rs.getInt("TOCURRENCY"));
                bean.setFromCurDes(rs.getString("FROMCUR"));
                bean.setToCurDes(rs.getString("TOCUR"));
                //bean.setRate(rs.getString("RATE"));
                bean.setRate(rs.getString("RATE").startsWith(".")? zero.concat(rs.getString("RATE")):rs.getString("RATE") );
                exchngeList.add(bean);
            }


            return exchngeList;
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
     * delete exchange rate
     * @param con
     * @param bean
     * @return 1 if success
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteExchangeRate(Connection con, ExChangeRateBean bean) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM EXCHANGERATE EX where EX.FROMCURRENCY =? "
                    + "AND EX.TOCURRENCY=?";

            ps = con.prepareStatement(query);
            ps.setInt(1, bean.getFromCurrency());
            ps.setInt(2, bean.getToCurrency());

            i = ps.executeUpdate();

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
        return i;
    }

    /**
     * update exchange rate
     * @param con
     * @param bean
     * @return int -if success return 1.
     * @throws Exception 
     */
    public int updateExchangeRate(Connection con, ExChangeRateBean bean) throws Exception {
        int resultID = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE EXCHANGERATE EX SET "
                    + "EX.RATE=?,EX.LASTUPDATEDUSER=?,EX.LASTUPDATEDTIME=? "
                    + "WHERE EX.FROMCURRENCY=? AND EX.TOCURRENCY=?";
            ps = con.prepareStatement(query);

            ps.setString(1, bean.getRate());
            ps.setString(2, bean.getLastUpdateUser());
            ps.setTimestamp(3, SystemDateTime.getSystemDataAndTime());
            ps.setInt(4, bean.getFromCurrency());
            ps.setInt(5, bean.getToCurrency());

            resultID = ps.executeUpdate();

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

        return resultID;
    }

    /**
     * check if given from currency and to currency exist
     * @param con
     * @param bean
     * @return
     * @throws Exception 
     */
    public boolean isRowExist(Connection con, ExChangeRateBean bean) throws Exception {
        PreparedStatement ps = null;
        boolean flag = false;
        int check = -1;
        try {
            String query = "SELECT COUNT(*) as COUNT FROM EXCHANGERATE EX WHERE "
                    + "EX.FROMCURRENCY=? AND EX.TOCURRENCY=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, bean.getToCurrency());
            ps.setInt(2, bean.getFromCurrency());
            rs = ps.executeQuery();
            while (rs.next()) {
                check = Integer.parseInt(rs.getString("COUNT"));
            }
            if (check == 1) {
                flag = true;
            } else {
                flag = false;
            }
            return flag;
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
}
