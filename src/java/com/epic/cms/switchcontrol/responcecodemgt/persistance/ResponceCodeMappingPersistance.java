/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.responcecodemgt.persistance;

import com.epic.cms.switchcontrol.responcecodemgt.bean.ResponceCodeMappingBean;
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
public class ResponceCodeMappingPersistance {
    
    private ResultSet rs;
    
    public List<ResponceCodeMappingBean> getDBCodes(Connection conOnline) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<ResponceCodeMappingBean> mappingList = new ArrayList<ResponceCodeMappingBean>();
        try {
            getAllByCatStat = conOnline.prepareStatement("SELECT M.DBCODE,M.RESPONSECODE,M.DESCRIPTION,"
                    + " R.DESCRIPTION AS RDESCRIPTION"
                    + " FROM ECMS_ONLINE_RESPONSE_CODE_MAP M,ECMS_ONLINE_RESPONSE_CODE R "
                    + " WHERE M.RESPONSECODE= R.CODE");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                ResponceCodeMappingBean map = new ResponceCodeMappingBean();

                map.setDbCode(rs.getString("DBCODE"));
                map.setResponceCode(rs.getString("RESPONSECODE"));
                map.setDescription(rs.getString("DESCRIPTION"));
                map.setResponceCodeDes(rs.getString("RDESCRIPTION"));



                mappingList.add(map);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();

        }
        return mappingList;
    }

    public boolean insertDBCode(Connection conOnline, ResponceCodeMappingBean mapping) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = conOnline.prepareStatement("INSERT INTO ECMS_ONLINE_RESPONSE_CODE_MAP"
                    + " (DBCODE,RESPONSECODE,DESCRIPTION)"
                    + " VALUES(?,?,?) ");


            insertStat.setString(1, mapping.getDbCode());
            insertStat.setString(2, mapping.getResponceCode());
            insertStat.setString(3, mapping.getDescription());


            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean updateDBCode(Connection conOnline, ResponceCodeMappingBean mapping) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = conOnline.prepareStatement("UPDATE ECMS_ONLINE_RESPONSE_CODE_MAP"
                    + " SET RESPONSECODE=?, DESCRIPTION =? "
                    + " WHERE DBCODE=? ");


            updateStat.setString(1, mapping.getResponceCode());
            updateStat.setString(2, mapping.getDescription());
            updateStat.setString(3, mapping.getDbCode());


            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean deleteDBCode(Connection conOnline, ResponceCodeMappingBean mapping) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = conOnline.prepareStatement("DELETE ECMS_ONLINE_RESPONSE_CODE_MAP WHERE DBCODE=?");

            deleteStat.setString(1, mapping.getDbCode());


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
    
}
