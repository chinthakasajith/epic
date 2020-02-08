/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.ChannelIncomeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.OnlineTxnTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class ChannelTxnMgtPersistance {

    private ResultSet rs;
    private ChannelIncomeBean bean;
    private List<ChannelIncomeBean> beanList;
    private List<OnlineTxnTypeBean> assignList;
    private List<OnlineTxnTypeBean> notAssignList;
    private OnlineTxnTypeBean txnBean;

    public boolean insertChannelTxn(String channel, String[] assignList, Connection con) throws SQLException, Exception {

        int j = -1;
        boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO ECMS_ONLINE_CHANNEL_TXN(CHANNELID,TXNTYPECODE) VALUES(?,?)";


            for (int i = 0; i < assignList.length; i++) {
                
                ps = con.prepareStatement(query);
                ps.setString(1, channel);
                ps.setString(2, assignList[i]);

                j = ps.executeUpdate();
                if (j != 1) {
                    throw new Exception();
                }
            }

            if (j == 1) {
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

    public boolean updateChannelTxn(String channel, String[] assignList, Connection con) throws SQLException, Exception {

        int j = -1;
        boolean success = false;
        PreparedStatement ps = null;

        try {
            String deleteQuery = "DELETE FROM ECMS_ONLINE_CHANNEL_TXN WHERE CHANNELID=?";

            ps = con.prepareStatement(deleteQuery);
            ps.setString(1, channel);
            j = ps.executeUpdate();
            if (j >= 0) {
                String insertQuery = "INSERT INTO ECMS_ONLINE_CHANNEL_TXN(CHANNELID,TXNTYPECODE) VALUES(?,?)";

                for (int i = 0; i < assignList.length; i++) {
                    ps = con.prepareStatement(insertQuery);
                    ps.setString(1, channel);
                    ps.setString(2, assignList[i]);

                    j = ps.executeUpdate();
                    if (j != 1) {
                        throw new Exception();
                    }
                }

                if (j == 1) {
                    success = true;
                }
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

    public int deleteChannelTxn(Connection con, String channelId) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        
        try {
            String query = "DELETE FROM ECMS_ONLINE_CHANNEL_TXN WHERE CHANNELID=?";

            ps = con.prepareStatement(query);
            ps.setString(1, channelId);

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

    public List<ChannelIncomeBean> getAllChannelTxn(Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;
        beanList = new ArrayList<ChannelIncomeBean>();

        try {

            String selectQuery = "SELECT TC.TXNTYPECODE,TC.CHANNELID,TT.DESCRYPTION AS TXN_TYPE_DES,C.CHANNELNAME AS CHANNEL_DES "
                    + "FROM ECMS_ONLINE_CHANNEL_TXN TC,ECMS_ONLINE_TXNTYPE TT,ECMS_ONLINE_CHANNELS C "
                    + "WHERE TC.TXNTYPECODE = TT.TYPECODE "
                    + "AND C.CHANNELID = TC.CHANNELID";

            ps = con.prepareStatement(selectQuery);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean = new ChannelIncomeBean();
                bean.setChannelId(rs.getString("CHANNELID"));
                bean.setTransactionTypeCode(rs.getString("TXNTYPECODE"));
                bean.setTxnDescription(rs.getString("TXN_TYPE_DES"));
                bean.setChannelDes(rs.getString("CHANNEL_DES"));

                beanList.add(bean);
            }
        } catch (SQLException sqx) {
            throw sqx;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return beanList;
    }

    public List<OnlineTxnTypeBean> getNotAssignTxn(Connection con, String channelId) throws SQLException, Exception {
        PreparedStatement ps =  null;
        notAssignList = new ArrayList<OnlineTxnTypeBean>();

        try {

            String selectQuery = "SELECT TT.TYPECODE, TT.DESCRYPTION "
                    + " FROM ECMS_ONLINE_TXNTYPE TT "
                    + "WHERE TT.TYPECODE NOT IN (SELECT TC.TXNTYPECODE FROM ECMS_ONLINE_CHANNEL_TXN TC WHERE TC.CHANNELID = ? )";

            ps = con.prepareStatement(selectQuery);
            ps.setString(1, channelId);
            rs = ps.executeQuery();

            while (rs.next()) {
                txnBean = new OnlineTxnTypeBean();

                txnBean.setTxnCode(rs.getString("TYPECODE"));
                txnBean.setDescription(rs.getString("DESCRYPTION"));

                notAssignList.add(txnBean);
            }
        } catch (SQLException sqx) {
            throw sqx;
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return notAssignList;
    }

    public List<OnlineTxnTypeBean> getAssignTxn(Connection con, String chanelId) throws SQLException, Exception {
        PreparedStatement ps = null;
        assignList = new ArrayList<OnlineTxnTypeBean>();

        try {


            String selectQuery = "SELECT TC.TXNTYPECODE,TT.DESCRYPTION AS TXN_DES "
                    + "FROM ECMS_ONLINE_CHANNEL_TXN TC,ECMS_ONLINE_TXNTYPE TT "
                    + "WHERE TC.CHANNELID = ? "
                    + "AND TT.TYPECODE = TC.TXNTYPECODE ";

            /**
             * "SELECT TC.TXNTYPECODE,TC.CHANNELID,TT.DESCRYPTION AS TXN_TYPE_DES,C.DESCRIPTION AS CHANNEL_DES "
            + "FROM ECMS_ONLINE_CHANNEL_TXN TC,ECMS_ONLINE_TXNTYPE TT,ECMS_ONLINE_CHANNEL_TYPE C "

            + "WHERE TC.TXNTYPECODE = TT.TYPECODE "
            + "AND C.CODE = TC.CHANNELID"
             */
            ps = con.prepareStatement(selectQuery);
            ps.setString(1, chanelId);
            rs = ps.executeQuery();

            while (rs.next()) {
                txnBean = new OnlineTxnTypeBean();

                txnBean.setTxnCode(rs.getString("TXNTYPECODE"));
                txnBean.setDescription(rs.getString("TXN_DES"));


                assignList.add(txnBean);
            }
        } catch (SQLException sqx) {
            throw sqx;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return assignList;
    }

    public List<ChannelTypeBean> getOnlineChannel(Connection conOnline) throws Exception {
        PreparedStatement ps = null;
        List<ChannelTypeBean> onlineChannelList = new ArrayList<ChannelTypeBean>();
        
        try {
            ps = conOnline.prepareStatement("SELECT C.CHANNELID,C.CHANNELNAME,C.STATUS"
                    + " FROM ECMS_ONLINE_CHANNELS C WHERE C.STATUS='1' ");

            rs = ps.executeQuery();

            while (rs.next()) {

                ChannelTypeBean type = new ChannelTypeBean();

                type.setCode(Integer.toString(rs.getInt("CHANNELID")));
                type.setDescription(rs.getString("CHANNELNAME"));
                type.setStatus(Integer.toString(rs.getInt("STATUS")));

                onlineChannelList.add(type);
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return onlineChannelList;
    }
    
    public List<ChannelTypeBean> getUnusedChannels(Connection conOnline) throws Exception {
        PreparedStatement ps = null;
        List<ChannelTypeBean> onlineChannelList = new ArrayList<ChannelTypeBean>();
        
        try {
            ps = conOnline.prepareStatement("SELECT C.CHANNELID,C.CHANNELNAME,C.STATUS"
                    + " FROM ECMS_ONLINE_CHANNELS C WHERE C.STATUS='1' "
                    + "AND C.CHANNELID NOT IN (SELECT CHANNELID FROM ECMS_ONLINE_CHANNEL_TXN)");

            rs = ps.executeQuery();

            while (rs.next()) {

                ChannelTypeBean type = new ChannelTypeBean();

                type.setCode(Integer.toString(rs.getInt("CHANNELID")));
                type.setDescription(rs.getString("CHANNELNAME"));
                type.setStatus(Integer.toString(rs.getInt("STATUS")));

                onlineChannelList.add(type);
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return onlineChannelList;
    }
    
    public List<ChannelTypeBean> getUnusedChannelsInUpdate(Connection conOnline,String channelId) throws Exception {
        PreparedStatement ps = null;
        List<ChannelTypeBean> onlineChannelList = new ArrayList<ChannelTypeBean>();
        
        try {
            ps = conOnline.prepareStatement("SELECT C.CHANNELID,C.CHANNELNAME,C.STATUS"
                    + " FROM ECMS_ONLINE_CHANNELS C WHERE C.STATUS='1' "
                    + "AND C.CHANNELID NOT IN (SELECT CT.CHANNELID FROM ECMS_ONLINE_CHANNEL_TXN CT WHERE CT.CHANNELID <> ?)");

            ps.setString(1, channelId);
            rs = ps.executeQuery();

            while (rs.next()) {

                ChannelTypeBean type = new ChannelTypeBean();

                type.setCode(Integer.toString(rs.getInt("CHANNELID")));
                type.setDescription(rs.getString("CHANNELNAME"));
                type.setStatus(Integer.toString(rs.getInt("STATUS")));

                onlineChannelList.add(type);
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return onlineChannelList;
    }
    
    public List<ChannelTypeBean> getAllTxnType(Connection conOnline) throws Exception {
        PreparedStatement ps = null;
        List<ChannelTypeBean> onlineChannelList = new ArrayList<ChannelTypeBean>();
        
        try {
            ps = conOnline.prepareStatement("SELECT TYPECODE,DESCRYPTION FROM ECMS_ONLINE_TXNTYPE ");

            rs = ps.executeQuery();

            while (rs.next()) {

                ChannelTypeBean type = new ChannelTypeBean();

                type.setTxnType(Integer.toString(rs.getInt("TYPECODE")));
                type.setTxnTypeDes(rs.getString("DESCRYPTION"));

                onlineChannelList.add(type);
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return onlineChannelList;
    }    

}
