/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.chanelconfig.persistance;

import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelConfigBean;
import com.epic.cms.system.util.session.SessionVarList;
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
public class ChannelConfigPersistance {

    private ResultSet rs;
    private SessionVarList sessionVarlist;

    /**
     * Get details from table
     * @param con
     * @return
     * @throws Exception 
     */
    public List<ChannelConfigBean> getChannelOnline(Connection conOnline) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<ChannelConfigBean> channelList = new ArrayList<ChannelConfigBean>();
        try {
            getAllByCatStat = conOnline.prepareStatement("SELECT CHNL.CHANNELID, CHNL.CHANNELNAME, CHNL.IP,"
                    + " CHNL.PORT, CHNL.TIMEOUT, CHNL.CONNECTIONTYPE,CHNL.COMMUNICATIONKEYID,"
                    + " CHNL.CHANNELTYPE, CHNL.ECHOSTATUS, CHNL.SIGNONSTATUS,CHNL.HDESID,CHNL.HSRCID,CHNL.AIIC32,CHNL.FIIC33,"
                    + " CHNL.DKESTATUS, CHNL.DEKTIMEPERIOD, CHNL.ECHOTIMEPERIOD,K.DESCRIPTION AS KDESCRIPTION,"
                    + " CHNL.STATUS,CONTP.DESCRIPTION AS CONTPDESCRIPTION, CHNTP.DESCRIPTION AS CHNTPDESCRIPTION,"
                    + " STAT.DESCRIPTION AS STATDESCRIPTION,CHNL.ECHODIRECTION,CHNL.KEYEXCHANGEDIRECTION,"
                    + " STAT2.DESCRIPTION AS echodes,STAT3.DESCRIPTION AS signondes,STAT4.DESCRIPTION AS dkedes"
                    + " FROM ECMS_ONLINE_CHANNELS CHNL,ECMS_ONLINE_CONNECTION_TYPE CONTP,"
                    + " ECMS_ONLINE_CHANNEL_TYPE CHNTP,ECMS_ONLINE_STATUS STAT,ECMS_ONLINE_COMMUNICATIONKEYS K,"
                    + " ECMS_ONLINE_STATUS STAT2,ECMS_ONLINE_STATUS STAT3,ECMS_ONLINE_STATUS STAT4"
                    + " WHERE CHNL.CONNECTIONTYPE=CONTP.CODE AND K.COMKEYID = CHNL.COMMUNICATIONKEYID AND"
                    + " CHNL.CHANNELTYPE=CHNTP.CODE AND CHNL.STATUS=STAT.CODE"
                    + " AND CHNL.ECHOSTATUS=STAT2.CODE AND CHNL.SIGNONSTATUS=STAT3.CODE AND CHNL.DKESTATUS=STAT4.CODE");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                ChannelConfigBean channel = new ChannelConfigBean();


                channel.setChannelId(rs.getString("CHANNELID"));
                channel.setChannelName(rs.getString("CHANNELNAME"));
                channel.setIp(rs.getString("IP"));
                channel.setPort(rs.getString("PORT"));
                channel.setTimeOut(rs.getString("TIMEOUT"));
                channel.setConnectionType(rs.getString("CONNECTIONTYPE"));
                channel.setChannelType(rs.getString("CHANNELTYPE"));
                
                channel.setEchoStatus(rs.getString("ECHOSTATUS"));//ECHOSTATUS
                channel.setSingonStatus(rs.getString("SIGNONSTATUS"));//SIGNONSTATUS
                channel.setDynamicKeyExchangeStatus(rs.getString("DKESTATUS"));//DKESTATUS
                
                channel.setEchoStatusDes(rs.getString("echodes"));//ECHOSTATUS
                channel.setSignonStatusDes(rs.getString("signondes"));//SIGNONSTATUS
                channel.setDynamicKeyExchangeStatusDes(rs.getString("dkedes"));//DKESTATUS
                
                channel.setDynamicKeyExchangePeriod(rs.getString("DEKTIMEPERIOD"));
                channel.setEchoPeriod(rs.getString("ECHOTIMEPERIOD"));
                channel.setStatus(rs.getString("STATUS"));
                channel.setConnectionTypeDescription(rs.getString("CONTPDESCRIPTION"));
                channel.setChannelTypeDescription(rs.getString("CHNTPDESCRIPTION"));
                channel.setStatusDescription(rs.getString("STATDESCRIPTION"));
                channel.setEchoDirection(Integer.toString(rs.getInt("ECHODIRECTION")));
                channel.setKeyExchangeDirection(Integer.toString(rs.getInt("KEYEXCHANGEDIRECTION")));
                channel.setKeyId(Integer.toString(rs.getInt("COMMUNICATIONKEYID")));
                channel.setKeyIdDes(rs.getString("KDESCRIPTION"));
                channel.setHdesId(rs.getString("HDESID"));
                channel.setHsrcId(rs.getString("HSRCID"));
                channel.setAscii32Id(rs.getString("AIIC32"));
                channel.setFiic33Id(rs.getString("FIIC33"));

                channelList.add(channel);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();

        }
        return channelList;
    }

    /**
     * Insert New Database Table Entry
     * @param con
     * @param type
     * @return
     * @throws Exception 
     */
    public boolean insertChannelOnline(Connection conOnline, ChannelConfigBean channel) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = conOnline.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNELS"
                    + "(CHANNELID,CHANNELNAME,IP,PORT,TIMEOUT,CONNECTIONTYPE,CHANNELTYPE,ECHOSTATUS,"
                    + " SIGNONSTATUS,DKESTATUS,DEKTIMEPERIOD,ECHOTIMEPERIOD,STATUS,ECHODIRECTION,KEYEXCHANGEDIRECTION,"
                    + " CREATETIME,LASTUPDATETIME,LASTUPDATEUSER,COMMUNICATIONKEYID,HDESID,HSRCID,AIIC32,FIIC33)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE,?,?,?,?,?,?) ");



            insertStat.setString(1, channel.getChannelId());
            insertStat.setString(2, channel.getChannelName());
            insertStat.setString(3, channel.getIp());
            insertStat.setString(4, channel.getPort());
            insertStat.setString(5, channel.getTimeOut());
            insertStat.setString(6, channel.getConnectionType());
            insertStat.setString(7, channel.getChannelType());
            insertStat.setString(8, channel.getEchoStatus());
            insertStat.setString(9, channel.getSignonStatus());
            insertStat.setString(10, channel.getDynamicKeyExchangeStatus());
            insertStat.setString(11, channel.getDynamicKeyExchangePeriod());
            insertStat.setString(12, channel.getEchoPeriod());
            insertStat.setString(13, channel.getStatus());
            insertStat.setInt(14, Integer.parseInt(channel.getEchoDirection()));
            insertStat.setInt(15, Integer.parseInt(channel.getKeyExchangeDirection()));
            insertStat.setString(16, channel.getLastUpdateUser());
            insertStat.setInt(17, Integer.parseInt(channel.getKeyId()));
            insertStat.setString(18, channel.getHdesId());
            insertStat.setString(19, channel.getHsrcId());
            insertStat.setString(20, channel.getAscii32Id());
            insertStat.setString(21, channel.getFiic33Id());

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    /**
     * Update Database Table Entry
     * @param con
     * @param currency
     * @return
     * @throws Exception 
     */
    public boolean updateChannelOnline(Connection conOnline, ChannelConfigBean channel) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CHANNELS"
                    + " SET CHANNELNAME=?,IP=?,PORT=?,TIMEOUT=?,CONNECTIONTYPE=?,"
                    + " CHANNELTYPE=?,ECHOSTATUS=?,SIGNONSTATUS=?,DKESTATUS=?,"
                    + " DEKTIMEPERIOD=?,ECHOTIMEPERIOD=?,STATUS=?,ECHODIRECTION=?,KEYEXCHANGEDIRECTION=?,"
                    + " LASTUPDATETIME=SYSDATE,LASTUPDATEUSER=?,COMMUNICATIONKEYID=?,HDESID=?,HSRCID=?,AIIC32=?,FIIC33=? "
                    + " WHERE CHANNELID=? ");



            updateStat.setString(1, channel.getChannelName());
            updateStat.setString(2, channel.getIp());
            updateStat.setString(3, channel.getPort());
            updateStat.setString(4, channel.getTimeOut());
            updateStat.setString(5, channel.getConnectionType());
            updateStat.setString(6, channel.getChannelType());
            updateStat.setString(7, channel.getEchoStatus());
            updateStat.setString(8, channel.getSignonStatus());
            updateStat.setString(9, channel.getDynamicKeyExchangeStatus());
            updateStat.setString(10, channel.getDynamicKeyExchangePeriod());
            updateStat.setString(11, channel.getEchoPeriod());
            updateStat.setString(12, channel.getStatus());
            updateStat.setInt(13, Integer.parseInt(channel.getEchoDirection()));
            updateStat.setInt(14, Integer.parseInt(channel.getKeyExchangeDirection()));
            updateStat.setString(15, channel.getLastUpdateUser());
            updateStat.setInt(16, Integer.parseInt(channel.getKeyId()));
            updateStat.setString(17, channel.getHdesId());
            updateStat.setString(18, channel.getHsrcId());
            updateStat.setString(19, channel.getAscii32Id());
            updateStat.setString(20, channel.getFiic33Id());

            updateStat.setString(21, channel.getChannelId());

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    /**
     * Delete Database Table Entry
     * @param con
     * @param type
     * @return
     * @throws Exception 
     */
    public boolean deleteChannelOnline(Connection conOnline, ChannelConfigBean channel) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = conOnline.prepareStatement("DELETE ECMS_ONLINE_CHANNELS WHERE CHANNELID=?");

            deleteStat.setString(1, channel.getChannelId());


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
