/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.chanelconfig.persistance;

import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ConnectionTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.StatusTypeBean;
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
public class StatusPersistence {

    private ResultSet rs;
    private List<ConnectionTypeBean> connectionTypeList;
    private List<ChannelTypeBean> channelTypeList;
    private List<StatusTypeBean> statusList;
    private List<StatusTypeBean> statusEDList;

    public List<ConnectionTypeBean> getConnectionTypeStatus(Connection conOnline) throws Exception {
        PreparedStatement getConStat = null;
        try {
            getConStat = conOnline.prepareStatement("SELECT CNT.CODE,CNT.DESCRIPTION,"
                    + "CNT.STATUS FROM ECMS_ONLINE_CONNECTION_TYPE CNT");

            connectionTypeList = new ArrayList<ConnectionTypeBean>();

            rs = getConStat.executeQuery();

            while (rs.next()) {

                ConnectionTypeBean conStatus = new ConnectionTypeBean();

                conStatus.setCode(rs.getString("CODE"));
                conStatus.setDescription(rs.getString("DESCRIPTION"));
                conStatus.setStatus(rs.getString("STATUS"));

                connectionTypeList.add(conStatus);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getConStat.close();
        }
        return connectionTypeList;
    }

    public List<ChannelTypeBean> getChannelTypeStatus(Connection conOnline) throws Exception {
        PreparedStatement getChnStat = null;
        try {
            getChnStat = conOnline.prepareStatement("SELECT CNL.CODE,CNL.DESCRIPTION,CNL.STATUS,STATUS"
                    + " FROM ECMS_ONLINE_CHANNEL_TYPE CNL WHERE CNL.CODE !=4 ");


            channelTypeList = new ArrayList<ChannelTypeBean>();

            rs = getChnStat.executeQuery();

            while (rs.next()) {

                ChannelTypeBean bean = new ChannelTypeBean();

                bean.setCode(rs.getString("CODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setStatus(rs.getString("STATUS"));

                channelTypeList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getChnStat.close();
        }
        return channelTypeList;
    }

    public List<StatusTypeBean> getStatusType(Connection conOnline) throws Exception {
        PreparedStatement getStat = null;
        try {
            getStat = conOnline.prepareStatement("SELECT STAT.ADMINCODE,STAT.DESCRIPTION,STAT.CATEGORYCODE"
                    + " FROM ECMS_ONLINE_STATUS STAT WHERE CATEGORYCODE='4'");


            statusList = new ArrayList<StatusTypeBean>();

            rs = getStat.executeQuery();

            while (rs.next()) {

                StatusTypeBean bean = new StatusTypeBean();

                bean.setCode(rs.getString("ADMINCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setCategoryCode(rs.getString("CATEGORYCODE"));

                statusList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getStat.close();
        }
        return statusList;
    }

    public List<StatusTypeBean> getStatusEDType(Connection conOnline) throws Exception {
        PreparedStatement getStat = null;
        try {
            getStat = conOnline.prepareStatement("SELECT STAT.ADMINCODE,STAT.DESCRIPTION,STAT.CATEGORYCODE"
                    + " FROM ECMS_ONLINE_STATUS STAT WHERE CATEGORYCODE='5'");


            statusEDList = new ArrayList<StatusTypeBean>();

            rs = getStat.executeQuery();

            while (rs.next()) {

                StatusTypeBean bean = new StatusTypeBean();

                bean.setCode(rs.getString("ADMINCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setCategoryCode(rs.getString("CATEGORYCODE"));

                statusEDList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getStat.close();
        }
        return statusEDList;
    }
}
