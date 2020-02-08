/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.system.util.session.SessionVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class CurrencyMgtPersistance {

    private ResultSet rs;
    private SessionVarList sessionVarlist;

    public List<CurrencyBean> getCurrencyDetails(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<CurrencyBean> currencyDetails = new ArrayList<CurrencyBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT C.CURRENCYNUMCODE,C.CURRENCYALPHACODE,C.EXPONENT,C.DESCRIPTION,C.STATUS,S.DESCRIPTION AS STDES,"
                    + "C.LASTUPDATEDUSER,C.LASTUPDATEDTIME,C.CREATEDTIME FROM CURRENCY C,STATUS S WHERE C.STATUS=S.STATUSCODE ORDER BY C.DESCRIPTION ASC");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                CurrencyBean currency = new CurrencyBean();

                String currcode = Integer.toString(rs.getInt("CURRENCYNUMCODE"));
                String currcodenew = this.zeroPadding(currcode);

                currency.setCurrencyCode(currcodenew);

                // currency.setCurrencyCode(Integer.toString(rs.getInt("CURRENCYNUMCODE")));

                currency.setCurrencyAlphaCode(rs.getString("CURRENCYALPHACODE"));

                currency.setExponent(Integer.toString(rs.getInt("EXPONENT")));

                currency.setCurrencyDes(rs.getString("DESCRIPTION"));

                currency.setStatus(rs.getString("STATUS"));

                currency.setStatusDes(rs.getString("STDES"));

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

    public boolean insertCurrency(Connection con, CurrencyBean currency) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CURRENCY(CURRENCYNUMCODE,CURRENCYALPHACODE,EXPONENT,DESCRIPTION,LASTUPDATEDUSER,"
                    + "LASTUPDATEDTIME,CREATEDTIME,STATUS) VALUES(?,?,?,?,?,SYSDATE,SYSDATE,?) ");
            insertStat.setInt(1, Integer.parseInt(currency.getCurrencyCode()));
            insertStat.setString(2, currency.getCurrencyAlphaCode());
            insertStat.setInt(3, Integer.parseInt(currency.getExponent()));
            insertStat.setString(4, currency.getCurrencyDes());
            insertStat.setString(5, currency.getLastUpdatedUser());
            insertStat.setString(6, currency.getStatus());

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean insertCurencyToOnline(Connection con, CurrencyBean currency) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CURRENCY_CODE(CODE,NOCODE,DESCRIPTION,STATUS,EXPONENT) VALUES(?,?,?,?,?) ");

            insertStat.setString(1, currency.getCurrencyAlphaCode());

            String curcode = currency.getCurrencyCode();
            String curcodenew = this.zeroPadding(curcode);

            insertStat.setString(2, curcodenew);

            insertStat.setString(3, currency.getCurrencyDes());

            String stat = currency.getStatus();
            if (stat.equals("ACT")) {
                insertStat.setInt(4, 1);
            } else if (stat.equals("DEA")) {
                insertStat.setInt(4, 2);
            }

            insertStat.setInt(5, Integer.parseInt(currency.getExponent()));

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean updateCurrency(Connection con, CurrencyBean currency) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CURRENCY SET CURRENCYALPHACODE=?,EXPONENT=?,DESCRIPTION=?,LASTUPDATEDUSER=?,"
                    + "LASTUPDATEDTIME=SYSDATE,STATUS=? WHERE CURRENCYNUMCODE=? ");

            updateStat.setString(1, currency.getCurrencyAlphaCode());
            updateStat.setInt(2, Integer.parseInt(currency.getExponent()));
            updateStat.setString(3, currency.getCurrencyDes());
            updateStat.setString(4, currency.getLastUpdatedUser());
            updateStat.setString(5, currency.getStatus());

            updateStat.setInt(6, Integer.parseInt(currency.getCurrencyCode()));

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean updateCurencyToOnline(Connection con, CurrencyBean currency) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE ECMS_ONLINE_CURRENCY_CODE SET CODE=?,DESCRIPTION=?,STATUS=?,EXPONENT=? WHERE NOCODE=? ");

            updateStat.setString(1, currency.getCurrencyAlphaCode());
            updateStat.setString(2, currency.getCurrencyDes());

            String stat = currency.getStatus();
            if (stat.equals("ACT")) {
                updateStat.setInt(3, 1);
            } else if (stat.equals("DEA")) {
                updateStat.setInt(3, 2);
            }

            updateStat.setInt(4, Integer.parseInt(currency.getExponent()));

            String curcode = currency.getCurrencyCode();
            String currcodenew = this.zeroPadding(curcode);

            updateStat.setString(5, currcodenew);

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean deleteCurrency(Connection con, CurrencyBean currency) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE CURRENCY WHERE CURRENCYNUMCODE=? ");
            deleteStat.setInt(1, Integer.parseInt(currency.getCurrencyCode()));

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

    //deleteFromOnline
    public boolean deleteFromOnline(Connection con, CurrencyBean currency) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE ECMS_ONLINE_CURRENCY_CODE WHERE NOCODE=? ");

            String curcode = currency.getCurrencyCode();
            String currcodenew = this.zeroPadding(curcode);

            deleteStat.setString(1, currcodenew);

            int row = deleteStat.executeUpdate();
            if (row == 1) {
                success = true;
            }

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
    
//    public String getCurrencyDescByCode(String code) {
//        String desc = null;
//        PreparedStatement getStat = null;
//        try {
//            getStat = con.prepareStatement("DELETE ECMS_ONLINE_CURRENCY_CODE WHERE NOCODE=? ");
//
//            String curcode = currency.getCurrencyCode();
//            String currcodenew = this.zeroPadding(curcode);
//
//            getStat.setString(1, currcodenew);
//
//            int row = getStat.executeUpdate();
//            if (row == 1) {
//                success = true;
//            }
//
//        } catch (SQLException ex) {
//            try {
//                ex.printStackTrace();
//                throw ex;
//            } catch (Exception e) {
//                ex.printStackTrace();
//                throw e;
//            }
//        } finally {
//            getStat.close();
//        }
//        return success;
//    }
}
