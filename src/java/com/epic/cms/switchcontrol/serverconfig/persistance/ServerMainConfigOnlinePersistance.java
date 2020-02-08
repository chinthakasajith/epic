/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.serverconfig.persistance;

import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
import com.epic.cms.switchcontrol.serverconfig.bean.ServerMainConfigBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  
 * @author nisansala
 */
public class ServerMainConfigOnlinePersistance {

    private ResultSet rs = null;

    /**
     * retrieve all the data from ECMS_ONLINE_CONFIG
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public ServerMainConfigBean getAllServerMainConfigData(Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;
        try {
            ServerMainConfigBean serverConfigBean = new ServerMainConfigBean();
            String query = "SELECT OSTYPE,INITIALVECTOR,"
                    + "REQUESTBUFFERSIZE,RESPONSEBUFFERSIZE,ALERTSTATUS,FAILSTATUS,"
                    + "POOLMAXSIZE,POOLMINSIZE,POOLMAXQUEUESIZE,"
                    + "POOLBACKLOG,HSMTYPE,PVI,PINB_FORMAT,ENCR_MODE,DEFAULTCHANNELID,C.CHANNELID ,STATUSOFDEFAULTCHANNEL "
                    + " FROM ECMS_ONLINE_CONFIG,ECMS_ONLINE_CHANNELS C "
                    + "WHERE C.CHANNELID = ECMS_ONLINE_CONFIG.DEFAULTCHANNELID";

            ps = con.prepareStatement(query);
            //System.out.println(query);
            rs = ps.executeQuery();

            while (rs.next()) {

                //assign values to the bean
                serverConfigBean.setOperateSystemType(rs.getString("OSTYPE"));
//                serverConfigBean.setRunningMode(rs.getString("RUNMODE"));
//                serverConfigBean.setPermanentRunningPort(rs.getString("PRUNPORT"));
//                serverConfigBean.setTerminatedRunningPort(rs.getString("URUNPORT"));
             //   serverConfigBean.setNoOfConnections(rs.getString("NOOFCONNECTION"));
                serverConfigBean.setInitialVector(rs.getString("INITIALVECTOR"));
                serverConfigBean.setReqBufSize(rs.getString("REQUESTBUFFERSIZE"));
                serverConfigBean.setResBufSize(rs.getString("RESPONSEBUFFERSIZE"));
                serverConfigBean.setAlertStatus(rs.getString("ALERTSTATUS"));
                serverConfigBean.setFailStatus(rs.getString("FAILSTATUS"));
               // serverConfigBean.setTempConnectTimeOut(rs.getString("TMPCONPROCESSTIMEOUT"));
                serverConfigBean.setMaxPoolSize(rs.getString("POOLMAXSIZE"));
                serverConfigBean.setMinPoolSize(rs.getString("POOLMINSIZE"));
                serverConfigBean.setMaxQueueSize(rs.getString("POOLMAXQUEUESIZE"));
                serverConfigBean.setPoolBackLog(rs.getString("POOLBACKLOG"));
             //   serverConfigBean.setMaxPINCount(rs.getString("MAXPINCOUNTOR"));
                serverConfigBean.setPvi(rs.getString("PVI"));
                serverConfigBean.setHsmType(rs.getString("HSMTYPE"));
                serverConfigBean.setPinbFormat(rs.getString("PINB_FORMAT"));
               // serverConfigBean.setEmvVerifyMethod(rs.getString("EMV_VERY_METHOD"));
                serverConfigBean.setEnrcMode(rs.getString("ENCR_MODE"));
                serverConfigBean.setChannelId(rs.getString("CHANNELID"));
                serverConfigBean.setChannelStatus(rs.getString("STATUSOFDEFAULTCHANNEL"));
            }
            return serverConfigBean;
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * update ECMS_ONLINE_CONFIG table
     * @param serverConfigBean
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean updateServerMainConfigData(ServerMainConfigBean serverConfigeBean, Connection con) throws SQLException, Exception {
        boolean success = false;
        PreparedStatement ps = null;
        ServerMainConfigBean bean = new ServerMainConfigBean();

        try {

            bean = this.getAllServerMainConfigData(con);

            String query = "UPDATE ECMS_ONLINE_CONFIG SET OSTYPE=?,"
                    + "INITIALVECTOR=?, "
                    + "REQUESTBUFFERSIZE=?,RESPONSEBUFFERSIZE=?,ALERTSTATUS=?,FAILSTATUS=?, "
                    + "POOLMAXSIZE=?,POOLMINSIZE=?,POOLMAXQUEUESIZE=?,"
                    + "POOLBACKLOG=?,PVI=?,HSMTYPE=?,PINB_FORMAT=?,ENCR_MODE=?,STATUSOFDEFAULTCHANNEL=?,"
                    + "DEFAULTCHANNELID= ? "
                    + "WHERE OSTYPE=?";


            ps = con.prepareStatement(query);

            ps.setString(1, serverConfigeBean.getOperateSystemType());
//            ps.setString(2, serverConfigeBean.getPermanentRunningPort());
//            ps.setString(3, serverConfigeBean.getTerminatedRunningPort());
//            ps.setString(4, serverConfigeBean.getRunningMode());
        //    ps.setString(2, serverConfigeBean.getNoOfConnections());
            ps.setString(2, serverConfigeBean.getInitialVector());
            ps.setString(3, serverConfigeBean.getReqBufSize());
            ps.setString(4, serverConfigeBean.getResBufSize());
            ps.setString(5, serverConfigeBean.getAlertStatus());
            ps.setString(6, serverConfigeBean.getFailStatus());
          //  ps.setString(8, serverConfigeBean.getTempConnectTimeOut());
            ps.setString(7, serverConfigeBean.getMaxPoolSize());
            ps.setString(8, serverConfigeBean.getMinPoolSize());
            ps.setString(9, serverConfigeBean.getMaxQueueSize());
            ps.setString(10, serverConfigeBean.getPoolBackLog());
         //   ps.setString(13, serverConfigeBean.getMaxPINCount());
            ps.setString(11, serverConfigeBean.getPvi());
            ps.setString(12, serverConfigeBean.getHsmType());
            ps.setString(13, serverConfigeBean.getPinbFormat());
           // ps.setString(17, serverConfigeBean.getEmvVerifyMethod());
            ps.setString(14, serverConfigeBean.getEnrcMode());
            ps.setString(15, serverConfigeBean.getChannelStatus());
            
            ps.setString(16, serverConfigeBean.getChannelId());
            ps.setString(17, bean.getOperateSystemType());

            int i = ps.executeUpdate();
            if (i == 1) {
                success = true;
            }

        } catch (SQLException e) {
            throw e;
        } catch (Exception ex) {
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

    /**
     * get PINB Formats
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public HashMap<String, String> getPinbFormat(Connection con) throws SQLException, Exception {
        HashMap<String, String> pinbFormat = new HashMap<String, String>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT CODE,DESCRIPTION FROM ECMS_ONLINE_PINB_FORMAT WHERE STATUS='1' ORDER BY DESCRIPTION";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                pinbFormat.put(rs.getString("CODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException e) {
            System.out.println("185");
            e.printStackTrace();
            throw e;
        } catch (Exception ex) {
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
        return pinbFormat;
    }
    
    /**
     * get EMV verify methods
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public HashMap<String, String> getEmvVerifyMethod(Connection con) throws SQLException, Exception {
        HashMap<String, String> emvVerifyMethod = new HashMap<String, String>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT CODE,DESCRYPTION FROM ECMS_ONLINE_EMV_VERIFY_MODE WHERE STATUS='1' ORDER BY DESCRYPTION";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                emvVerifyMethod.put(rs.getString("CODE"), rs.getString("DESCRYPTION"));
            }

        } catch (SQLException e) {
            throw e;
        } catch (Exception ex) {
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
        return emvVerifyMethod;
    }
    
    /**
     * get ENCR modes
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public HashMap<String, String> getEncrMode(Connection con) throws SQLException, Exception {
        HashMap<String, String> encrMode = new HashMap<String, String>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT CODE,DESCRYPTION FROM ECMS_ONLINE_ENCRMODE WHERE STATUS='1' ORDER BY DESCRYPTION";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                encrMode.put(rs.getString("CODE"), rs.getString("DESCRYPTION"));
            }

        } catch (SQLException e) {
            System.out.println("258");
            e.printStackTrace();
            throw e;
        } catch (Exception ex) {
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
        return encrMode;
    }
    
    /**
     * get enable disables
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public HashMap<String, String> getEnableMode(Connection con) throws SQLException, Exception {
        HashMap<String, String> enableModes = new HashMap<String, String>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT ADMINCODE,DESCRIPTION FROM ECMS_ONLINE_STATUS WHERE CATEGORYCODE='5' ORDER BY DESCRIPTION";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                enableModes.put(rs.getString("ADMINCODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException e) {
                        System.out.println("295");
                        e.printStackTrace();
            throw e;
        } catch (Exception ex) {
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
        return enableModes;
    }
    
    /**\
     * load all channels
     * @param conOnline
     * @return
     * @throws Exception 
     */
    public List<ChannelTypeBean> getOnlineChannel(Connection conOnline) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<ChannelTypeBean> onlineChannelList = new ArrayList<ChannelTypeBean>();
        try {
            getAllByCatStat = conOnline.prepareStatement("SELECT C.CHANNELID,C.CHANNELNAME,C.STATUS"
                    + " FROM ECMS_ONLINE_CHANNELS C WHERE C.STATUS='1' ");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                ChannelTypeBean type = new ChannelTypeBean();
                
                type.setCode(Integer.toString(rs.getInt("CHANNELID")));
                type.setDescription(rs.getString("CHANNELNAME"));
                type.setStatus(Integer.toString(rs.getInt("STATUS")));
                
                onlineChannelList.add(type);
            }

        } catch (SQLException ex) {
            System.out.println("340");
                        ex.printStackTrace();
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();

        }
        return onlineChannelList;
    }
    
    public Map<String,String> getOnlineChannelStatusMap(Connection conOnline) throws Exception {
        PreparedStatement getAllByCatStat = null;
        Map<String,String> channelStatusMap = new HashMap<String,String>();
        try {
            getAllByCatStat = conOnline.prepareStatement("SELECT CODE , DESCRIPTION FROM ECMS_ONLINE_STATUS WHERE CODE IN (" + StatusVarList.ACTIVE_STATUS_CODE + "," + StatusVarList.DEACTIVE_STATUS_CODE + ")");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                channelStatusMap.put(rs.getString("CODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            System.out.println("340");
                        ex.printStackTrace();
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();

        }
        return channelStatusMap;
    }
    
}
