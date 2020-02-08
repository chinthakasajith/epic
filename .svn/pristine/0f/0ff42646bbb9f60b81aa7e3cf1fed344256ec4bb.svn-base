/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyExchangeRateBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * deals with currency exchange rates
 * @author nisansala
 */
public class CurrencyExchangeRatePersistance {

    ResultSet rs = null;

    /**
     * to retrieve all the currency details
     * @param con
     * @return
     * @throws Exception 
     */
    public List<CurrencyBean> getAllCurrencyDetails(Connection con) throws Exception {
        PreparedStatement ps = null;
        List<CurrencyBean> currencyList = new ArrayList<CurrencyBean>();

        try {
            ps = con.prepareStatement("SELECT C.CURRENCYNUMCODE,C.CURRENCYALPHACODE,C.DESCRIPTION FROM CURRENCY C "
                    + "ORDER BY C.DESCRIPTION ASC");

            rs = ps.executeQuery();

            while (rs.next()) {

                CurrencyBean currency = new CurrencyBean();

                currency.setCurrencyCode(Integer.toString(rs.getInt("CURRENCYNUMCODE")));
                currency.setCurrencyAlphaCode(rs.getString("CURRENCYALPHACODE"));
                currency.setCurrencyDes(rs.getString("DESCRIPTION"));

                currencyList.add(currency);
            }
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } 
        finally {
            rs.close();
            ps.close();
        }
        return currencyList;
    }

    /**
     * to retrieve the currencies a currency exchange rate has not set yet 
     * @param con
     * @return
     * @throws Exception 
     */
    public List<CurrencyBean> getUnusedCurrencyDetails(Connection con) throws Exception {
        PreparedStatement ps = null;
        List<CurrencyBean> currencyList = new ArrayList<CurrencyBean>();

        try {
            ps = con.prepareStatement("SELECT C.CURRENCYNUMCODE,C.CURRENCYALPHACODE,C.DESCRIPTION FROM CURRENCY C "
                    + "WHERE C.CURRENCYNUMCODE NOT IN (SELECT CXR.CURRENCYCODE FROM CURRENCYEXCHANGERATE CXR)ORDER BY C.DESCRIPTION ASC");

            rs = ps.executeQuery();

            while (rs.next()) {

                CurrencyBean currency = new CurrencyBean();

                currency.setCurrencyCode(Integer.toString(rs.getInt("CURRENCYNUMCODE")));
                currency.setCurrencyAlphaCode(rs.getString("CURRENCYALPHACODE"));
                currency.setCurrencyDes(rs.getString("DESCRIPTION"));

                currencyList.add(currency);
            }
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } 
        finally {
            rs.close();
            ps.close();
        }
        return currencyList;
    }

    /**
     * to retrieve all currency exchange rates
     * @param con
     * @return
     * @throws Exception 
     */
    public List<CurrencyExchangeRateBean> getAllCurrencyExchangeRates(Connection con) throws Exception {
        String zero = "0";
        PreparedStatement ps = null;
        List<CurrencyExchangeRateBean> exchangeRateList = new ArrayList<CurrencyExchangeRateBean>();

        try {
            //ps = con.prepareStatement("SELECT CXR.CURRENCYCODE,CXR.SELLINGRATE,CXR.BUYINGRATE FROM CURRENCYEXCHANGERATE CXR ");

            ps = con.prepareStatement("SELECT CXR.CURRENCYCODE,C.DESCRIPTION,CXR.SELLINGRATE,CXR.BUYINGRATE,CXR.LASTUPDATEDUSER AS USERR FROM CURRENCYEXCHANGERATE CXR,CURRENCY C WHERE C.CURRENCYNUMCODE = CXR.CURRENCYCODE ");
            rs = ps.executeQuery();

            while (rs.next()) {

                CurrencyExchangeRateBean exchange = new CurrencyExchangeRateBean();

                exchange.setCurrencyCode(Integer.toString(rs.getInt("CURRENCYCODE")));
                exchange.setCurrencyDes(rs.getString("DESCRIPTION"));
                exchange.setSellRate(rs.getString("SELLINGRATE").startsWith(".") ? zero.concat(rs.getString("SELLINGRATE")) : rs.getString("SELLINGRATE"));
                exchange.setBuyRate(rs.getString("BUYINGRATE").startsWith(".") ? zero.concat(rs.getString("BUYINGRATE")) : rs.getString("BUYINGRATE"));
                exchange.setLastUpdateUser(rs.getString("USERR"));
                exchangeRateList.add(exchange);
            }
        } catch (SQLException ex) {
            throw ex;
        }catch (Exception ex) {
            throw ex;
        }
        finally {
            rs.close();
            ps.close();
        }
        return exchangeRateList;
    }

    /**
     * to insert a new currency exchange rate
     * @param bean
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public Boolean insertNewCurrencyExchangeRate(CurrencyExchangeRateBean bean, Connection con) throws SQLException, Exception {
        Boolean success = false;
        int row;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO CURRENCYEXCHANGERATE (CURRENCYCODE,"
                    + " SELLINGRATE,BUYINGRATE,LASTUPDATEDUSER,"
                    + " CREATETIME) VALUES(?,?,?,?,SYSDATE)";

            ps = con.prepareStatement(query);

            ps.setDouble(1, Double.parseDouble(bean.getCurrencyCode()));
            ps.setDouble(2, Double.parseDouble(bean.getSellRate()));
            ps.setDouble(3, Double.parseDouble(bean.getBuyRate()));
            ps.setString(4, bean.getLastUpdateUser());
            //ps.setTimestamp(5, SystemDateTime.getSystemDataAndTime());

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

    //to insert a new currency exchange rate to the online database
    public Boolean insertNewCurrencyExchangeRateOnline(CurrencyExchangeRateBean bean, Connection con) throws SQLException, Exception {
        Boolean success = false;
        int row;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO ECMS_ONLINE_EXCHANGE_RATE (CURRENCYNOCODE,"
                    + " FROMRATE,TORATE) VALUES(?,?,?)";

            ps = con.prepareStatement(query);

            ps.setString(1, zeroPad(bean.getCurrencyCode()));
            ps.setDouble(2, Double.parseDouble(bean.getSellRate()));
            ps.setDouble(3, 1 / Double.parseDouble(bean.getBuyRate()));

            row = ps.executeUpdate();
            if (row == 1) {
                success = true;
            }
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } 
        finally {
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

    /**
     * to delete a currency exchange rate
     * @param con
     * @param onlineCon
     * @param currencyCode
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteCurrencyExchangeRate(Connection con, Connection onlineCon, String currencyCode) throws SQLException, Exception {

        int i = -1;
        PreparedStatement ps = null;

        try {
            String query = "DELETE FROM CURRENCYEXCHANGERATE WHERE CURRENCYCODE =?";

            ps = con.prepareStatement(query);
            ps.setString(1, currencyCode);
            i = ps.executeUpdate();

            String query2 = "DELETE FROM ECMS_ONLINE_EXCHANGE_RATE WHERE CURRENCYNOCODE =?";

            ps = onlineCon.prepareStatement(query2);
            ps.setString(1, zeroPad(currencyCode));
            int j = 0;
            j = ps.executeUpdate();
            if (j == 0) {
                throw new Exception();
            }
        } catch (SQLException ex) {
            throw ex;
        }catch (Exception ex) {
            throw ex;
        }
        finally {
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
     * to update an existing currency exchange rate
     * @param exchange
     * @param con
     * @param onlineCon
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int updateCurrencyExchangeRate(CurrencyExchangeRateBean exchange, Connection con, Connection onlineCon) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE CURRENCYEXCHANGERATE SET "
                    + " SELLINGRATE = ?,BUYINGRATE = ?,LASTUPDATEDUSER = ?,"
                    + " LASTUPDATEDTIME = SYSDATE"
                    + " WHERE CURRENCYCODE=? ";

            ps = con.prepareStatement(query);

            ps.setString(1, exchange.getSellRate());
            ps.setString(2, exchange.getBuyRate());
            ps.setString(3, exchange.getLastUpdateUser());
           // ps.setTimestamp(4, SystemDateTime.getSystemDataAndTime());
            ps.setString(4, exchange.getCurrencyCode());

            row = ps.executeUpdate();

            String query2 = "UPDATE ECMS_ONLINE_EXCHANGE_RATE SET "
                    + " FROMRATE = ?,TORATE = ?"
                    + " WHERE CURRENCYNOCODE=? ";

            ps = onlineCon.prepareStatement(query2);

            ps.setString(1, exchange.getSellRate());
            ps.setDouble(2, 1 / Double.parseDouble(exchange.getBuyRate()));
            ps.setString(3, zeroPad(exchange.getCurrencyCode()));

            int j = 0;
            j = ps.executeUpdate();
            if (j == 0) {
                throw new Exception();
            }

        } catch (SQLException ex) {
            throw ex;
        }catch (Exception ex) {
            throw ex;
        }
        finally {
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
}
