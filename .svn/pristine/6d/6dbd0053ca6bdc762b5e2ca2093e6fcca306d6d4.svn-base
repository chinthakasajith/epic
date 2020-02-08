/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.ListenerTxnBean;
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
public class ListenerTxnMgtPersistance {
    
    ResultSet rs;
    private List<OnlineTxnTypeBean> assignList;
    private List<OnlineTxnTypeBean> notAssignList;
    private OnlineTxnTypeBean txnBean;
    
    public List<ListenerTxnBean> getAllListenerTypes(Connection conOnline) throws Exception {
        PreparedStatement ps = null;
        List<ListenerTxnBean> listenerList = new ArrayList<ListenerTxnBean>();
        try {
            
            ps = conOnline.prepareStatement("SELECT L.LISTENERID,L.DESCRIPTION,L.STATUS"
                    + " FROM ECMS_ONLINE_LISTENERS L WHERE L.STATUS='1'");
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                ListenerTxnBean type = new ListenerTxnBean();
                
                type.setLitenerId(Integer.toString(rs.getInt("LISTENERID")));
                type.setListenerDes(rs.getString("DESCRIPTION"));
                type.setStatus(Integer.toString(rs.getInt("STATUS")));
                
                listenerList.add(type);
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
        return listenerList;
    }
    
    public List<ListenerTxnBean> getUnusedListeners(Connection conOnline) throws Exception {
        PreparedStatement ps = null;
        List<ListenerTxnBean> listenerList = new ArrayList<ListenerTxnBean>();
        try {
            ps = conOnline.prepareStatement("SELECT L.LISTENERID,L.DESCRIPTION,L.STATUS "
                    + "FROM ECMS_ONLINE_LISTENERS L WHERE L.STATUS='1' "
                    + "AND L.LISTENERID NOT IN (SELECT LT.LISTENERID FROM ECMS_ONLINE_LISTENER_TXN LT)");
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                ListenerTxnBean type = new ListenerTxnBean();
                
                type.setLitenerId(Integer.toString(rs.getInt("LISTENERID")));
                type.setListenerDes(rs.getString("DESCRIPTION"));
                type.setStatus(Integer.toString(rs.getInt("STATUS")));
                
                listenerList.add(type);
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
        return listenerList;
    }
    
    public List<ListenerTxnBean> getUnusedListenersUpdate(Connection conOnline, String listenerId) throws Exception {
        PreparedStatement ps = null;
        List<ListenerTxnBean> listenerList = new ArrayList<ListenerTxnBean>();
        try {
            ps = conOnline.prepareStatement("SELECT L.LISTENERID,L.DESCRIPTION,L.STATUS "
                    + "FROM ECMS_ONLINE_LISTENERS L WHERE L.STATUS='1' "
                    + "AND L.LISTENERID NOT IN (SELECT LT.LISTENERID FROM ECMS_ONLINE_LISTENER_TXN LT WHERE LT.LISTENERID <> ? )");
            
            ps.setString(1, listenerId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                ListenerTxnBean type = new ListenerTxnBean();
                
                type.setLitenerId(Integer.toString(rs.getInt("LISTENERID")));
                type.setListenerDes(rs.getString("DESCRIPTION"));
                type.setStatus(Integer.toString(rs.getInt("STATUS")));
                
                listenerList.add(type);
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
        return listenerList;
    }
    
    public List<ListenerTxnBean> getAllListenerTxn(Connection conOnline) throws Exception {
        PreparedStatement ps = null;
        List<ListenerTxnBean> listenerList = new ArrayList<ListenerTxnBean>();
        try {
            ps = conOnline.prepareStatement("SELECT L.TXNTYPECODE,L.LISTENERID,LT.DESCRIPTION AS LISTEN_DES,TT.DESCRYPTION AS TXN_DES "
                    + "FROM ECMS_ONLINE_LISTENER_TXN L,ECMS_ONLINE_LISTENERS LT,ECMS_ONLINE_TXNTYPE TT "
                    + "WHERE LT.LISTENERID = L.LISTENERID "
                    + "AND TT.TYPECODE = L.TXNTYPECODE");
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                ListenerTxnBean txn = new ListenerTxnBean();
                
                
                txn.setLitenerId(Integer.toString(rs.getInt("LISTENERID")));
                txn.setListenerDes(rs.getString("LISTEN_DES"));
                txn.setTxnType(rs.getString("TXNTYPECODE"));
                txn.setTxnDes(rs.getString("TXN_DES"));
                
                listenerList.add(txn);
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
        return listenerList;
    }
    
    public boolean insertListenerTxn(String listener, String[] assignList, Connection con) throws SQLException, Exception {
        
        int j = -1;
        boolean success = false;
        PreparedStatement ps = null;
        
        try {
            String query = "INSERT INTO ECMS_ONLINE_LISTENER_TXN(LISTENERID,TXNTYPECODE) VALUES(?,?)";
            
            
            for (int i = 0; i < assignList.length; i++) {
                
                ps = con.prepareStatement(query);
                ps.setString(1, listener);
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
    
    public Boolean updateListenerTxn(String listener, String[] assignList, Connection con) throws SQLException, Exception {
        
        int j = -1;
        Boolean success = false;
        PreparedStatement ps = null;
        
        try {
            String deleteQuery = "DELETE FROM ECMS_ONLINE_LISTENER_TXN WHERE LISTENERID=?";
            
            ps = con.prepareStatement(deleteQuery);
            ps.setString(1, listener);
            j = ps.executeUpdate();
            if (j >= 0) {
                String insertQuery = "INSERT INTO ECMS_ONLINE_LISTENER_TXN(LISTENERID,TXNTYPECODE) VALUES(?,?)";
                
                for (int i = 0; i < assignList.length; i++) {
                    ps = con.prepareStatement(insertQuery);
                    ps.setString(1, listener);
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
    
    public int deleteListenerTxn(Connection con, String listenerId) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM ECMS_ONLINE_LISTENER_TXN WHERE LISTENERID=?";
            
            ps = con.prepareStatement(query);
            ps.setString(1, listenerId);
            
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
    
    public List<OnlineTxnTypeBean> getNotAssignTxn(Connection con, String listenerId) throws SQLException, Exception {
        PreparedStatement ps = null;
        notAssignList = new ArrayList<OnlineTxnTypeBean>();
        
        try {
            
            String selectQuery = "SELECT TT.TYPECODE, TT.DESCRYPTION "
                    + " FROM ECMS_ONLINE_TXNTYPE TT "
                    + "WHERE TT.TYPECODE NOT IN (SELECT TL.TXNTYPECODE FROM ECMS_ONLINE_LISTENER_TXN TL WHERE TL.LISTENERID = ? )";
            
            ps = con.prepareStatement(selectQuery);
            ps.setString(1, listenerId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                txnBean = new OnlineTxnTypeBean();
                
                txnBean.setTxnCode(rs.getString("TYPECODE"));
                txnBean.setDescription(rs.getString("DESCRYPTION"));
                
                notAssignList.add(txnBean);
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
        
        return notAssignList;
    }
    
    public List<OnlineTxnTypeBean> getAssignTxn(Connection con, String listenerId) throws SQLException, Exception {
        PreparedStatement ps = null;
        assignList = new ArrayList<OnlineTxnTypeBean>();
        
        try {
            
            
            String selectQuery = "SELECT TL.TXNTYPECODE,TT.DESCRYPTION AS TXN_DES "
                    + "FROM ECMS_ONLINE_LISTENER_TXN TL,ECMS_ONLINE_TXNTYPE TT "
                    + "WHERE TL.LISTENERID = ? "
                    + "AND TT.TYPECODE = TL.TXNTYPECODE ";
            
            ps = con.prepareStatement(selectQuery);
            ps.setString(1, listenerId);
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
