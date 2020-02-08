/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.logmanagement.persistance;

import com.epic.cms.switchcontrol.logmanagement.bean.LogLevelBean;
import com.epic.cms.switchcontrol.logmanagement.bean.LogMgtBean;
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
public class LogMgtPersistance {

    private ResultSet rs;
    private List<LogLevelBean> logLevelList = null;

    public List<LogLevelBean> getLogLevel(Connection conOnline) throws Exception {
        PreparedStatement getStat = null;
        try {
            getStat = conOnline.prepareStatement("SELECT L.CODE,L.DESCRIPTION"
                    + " FROM ECMS_ONLINE_LOG_LEVEL L");


            logLevelList = new ArrayList<LogLevelBean>();

            rs = getStat.executeQuery();

            while (rs.next()) {

                LogLevelBean bean = new LogLevelBean();

                bean.setCode(Integer.toString(rs.getInt("CODE")));
                bean.setDescription(rs.getString("DESCRIPTION"));


                logLevelList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getStat.close();
        }
        return logLevelList;
    }

    public LogMgtBean getLogManagementData(Connection conOnline) throws Exception {
        PreparedStatement getAllByCatStat = null;
        LogMgtBean logBean = new LogMgtBean();
        try {
            getAllByCatStat = conOnline.prepareStatement("SELECT LC.LOGLEVEL, LC.LOGFILENAME, LC.LOGBACKUPPATH,"
                    + " LC.AUTOLOGBACKUPSTATUS,LC.SYNSTATUS,LC.SYNPERIOD,LC.NOFLOGFILES"
                    + " FROM ECMS_ONLINE_LOG_CONFIG LC");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                logBean.setLogLevel(Integer.toString(rs.getInt("LOGLEVEL")));
                logBean.setLogFileName(rs.getString("LOGFILENAME"));
                logBean.setLogBackUpPath(rs.getString("LOGBACKUPPATH"));
                logBean.setLogBackUpStatus(Integer.toString(rs.getInt("AUTOLOGBACKUPSTATUS")));
                logBean.setSynStatus(Integer.toString(rs.getInt("SYNSTATUS")));
                logBean.setSynPeriod(Integer.toString(rs.getInt("SYNPERIOD")));
                logBean.setNumOfLogFile(Integer.toString(rs.getInt("NOFLOGFILES")));

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();

        }
        return logBean;
    }

    public boolean updateLogManagementDetails(Connection conOnline, LogMgtBean logBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = conOnline.prepareStatement("UPDATE ECMS_ONLINE_LOG_CONFIG SET"
                    + " LOGLEVEL=?,LOGFILENAME=?,LOGBACKUPPATH=?,AUTOLOGBACKUPSTATUS=?,"
                    + " SYNSTATUS=?,SYNPERIOD=?,NOFLOGFILES=? ");



            updateStat.setInt(1, Integer.parseInt(logBean.getLogLevel()));
            updateStat.setString(2, logBean.getLogFileName());
            updateStat.setString(3, logBean.getLogBackUpPath());
            updateStat.setInt(4, Integer.parseInt(logBean.getLogBackUpStatus()));
            updateStat.setInt(5, Integer.parseInt(logBean.getSynStatus()));
            updateStat.setInt(6, Integer.parseInt(logBean.getSynPeriod()));
            updateStat.setInt(7, Integer.parseInt(logBean.getNumOfLogFile()));


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
