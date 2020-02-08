/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.serverdetails.persistance;

import com.epic.cms.switchcontrol.serverdetails.bean.OnlineVersionInfoBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mahesh_m
 */
public class ServerDetailsPersistance {

    private ResultSet rs;

    public OnlineVersionInfoBean getServerInfo(Connection con) throws Exception {
        PreparedStatement getByCatStat = null;
        OnlineVersionInfoBean onlineVersionInfo = new OnlineVersionInfoBean();
        try {
            getByCatStat = con.prepareStatement("SELECT VERSION_NO,BUILD_MODULE_NO,TPS_LEVEL,SECURITY_MODULE_NO FROM ECMS_ONLINE_VERSION_INFOR");

            rs = getByCatStat.executeQuery();

            while (rs.next()) {

                onlineVersionInfo.setVersionNo(rs.getString("VERSION_NO"));
                onlineVersionInfo.setBuildModuleNo(rs.getString("BUILD_MODULE_NO"));
                onlineVersionInfo.setTpsLevel(rs.getString("TPS_LEVEL"));
                onlineVersionInfo.setSecurityModuleNo(rs.getString("SECURITY_MODULE_NO"));

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getByCatStat.close();
        }
        return onlineVersionInfo;
    }

    public String getTransactionCount(Connection con) throws Exception {
        PreparedStatement getByCatStat = null;
        String countString = null;

        try {
            getByCatStat = con.prepareStatement("SELECT COUNT(*) FROM ECMS_ONLINE_ALERTS");

            rs = getByCatStat.executeQuery();

            if (rs.next()) {
                countString = rs.getString(1).trim();
                
            } else {
                countString = "0";
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getByCatStat.close();
        }
        return countString;
    }
    
      public String getFailCount(Connection con) throws Exception {
        PreparedStatement getByCatStat = null;
        String countString = null;

        try {
            getByCatStat = con.prepareStatement("SELECT COUNT(*) FROM ECMS_ONLINE_FAILS");

            rs = getByCatStat.executeQuery();

            if (rs.next()) {
                countString = rs.getString(1).trim();
                
            } else {
                countString = "0";
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getByCatStat.close();
        }
        return countString;
    }
}
