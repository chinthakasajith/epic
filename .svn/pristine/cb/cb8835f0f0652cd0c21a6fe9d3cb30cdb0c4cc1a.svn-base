/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.CountryMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.PostalCodeBean;
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
public class PostalCodesPersistance {

    private ResultSet rs;
    private SessionVarList sessionVarlist;

    public List<PostalCodeBean> getPostalCodeDetails(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<PostalCodeBean> postalCodeDetails = new ArrayList<PostalCodeBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT PC.COUNTRYCODE,PC.CITY,PC.POSTALCODE,PC.LASTUPDATEDUSER,PC.LASTUPDATEDTIME,PC.CREATETIME FROM POSTALCODE PC");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                PostalCodeBean postal = new PostalCodeBean();

                postal.setCountryCode(rs.getString("COUNTRYCODE"));
                postal.setCity(rs.getString("CITY"));
                postal.setPostalCode(rs.getString("POSTALCODE"));

                postalCodeDetails.add(postal);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return postalCodeDetails;
    }
    
     public List<CountryMgtBean> getCountryCodeList(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<CountryMgtBean> countryCodeList = new ArrayList<CountryMgtBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT C.COUNTRYNUMCODE,C.DESCRIPTION FROM COUNTRY C");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                CountryMgtBean countryCode = new CountryMgtBean();
                countryCode.setCountryCode(rs.getString("COUNTRYNUMCODE"));
                countryCode.setDescription(rs.getString("DESCRIPTION"));
                countryCodeList.add(countryCode);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return countryCodeList;
    }
    
    public boolean deletePostal(Connection con, PostalCodeBean postal) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE POSTALCODE WHERE POSTALCODE=? ");
            deleteStat.setInt(1, Integer.parseInt(postal.getPostalCode()));

            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            deleteStat.close();
        }
        return success;
    }
    
    public boolean insertPostalCode(Connection con, PostalCodeBean postal) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO POSTALCODE(COUNTRYCODE,CITY,POSTALCODE,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATETIME) VALUES(?,?,?,?,SYSDATE,SYSDATE) ");
           
            insertStat.setInt(1, Integer.parseInt(postal.getCountryCode()));
            insertStat.setString(2, postal.getCity());
            insertStat.setString(3, postal.getPostalCode());
            insertStat.setString(4, postal.getLastUpdateduser());

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }
     
     public boolean updatePostal(Connection con, PostalCodeBean postal) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE POSTALCODE SET CITY=?,COUNTRYCODE=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE "
                    + "WHERE POSTALCODE=? ");

            updateStat.setString(1, postal.getCity());
            updateStat.setString(2, postal.getCountryCode());
            updateStat.setString(3, postal.getLastUpdateduser());
            updateStat.setString(4, postal.getPostalCode());
         

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }  
     
    
    
}
