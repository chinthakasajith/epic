/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.listenerconfig.persistance;

import com.epic.cms.switchcontrol.chanelconfig.bean.StatusTypeBean;
import com.epic.cms.switchcontrol.listenerconfig.bean.ListenerConfigurationBean;
import com.epic.cms.switchcontrol.listenerconfig.bean.ListenerTypeBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nalin
 */
public class ListenerConfigurationPersistance {

    private ResultSet rs;
    private List<ListenerTypeBean> listenerTypeList = null;
    private List<StatusTypeBean> statusList;
    private HashMap<String, String> runningModeList = null;
    private HashMap<String, String> keyId = null;

    public List<ListenerTypeBean> getListenerType(Connection conOnline) throws Exception {
        PreparedStatement getStat = null;
        listenerTypeList = new ArrayList<ListenerTypeBean>();
        try {
            getStat = conOnline.prepareStatement("SELECT LT.CODE,LT.DESCRIPTION,LT.STATUS "
                    + " FROM ECMS_ONLINE_LISTENER_TYPE LT WHERE LT.STATUS=?");


            listenerTypeList = new ArrayList<ListenerTypeBean>();
            getStat.setInt(1, StatusVarList.ONLINE_ACTIVE_STATUS);
            rs = getStat.executeQuery();

            while (rs.next()) {

                ListenerTypeBean bean = new ListenerTypeBean();

                bean.setTypeCode(Integer.toString(rs.getInt("CODE")));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setStatus(Integer.toString(rs.getInt("STATUS")));

                listenerTypeList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getStat.close();
        }
        return listenerTypeList;
    }

    public HashMap<String, String> getRunningMode(Connection conOnline) throws Exception {
        PreparedStatement getStat = null;

        try {
            getStat = conOnline.prepareStatement("SELECT CT.CODE,CT.DESCRIPTION "
                    + " FROM ECMS_ONLINE_CONNECTION_TYPE CT "
                    + " WHERE CT.STATUS=?");

            getStat.setInt(1, StatusVarList.ONLINE_ACTIVE_STATUS);
            rs = getStat.executeQuery();

            runningModeList = new HashMap<String, String>();
            while (rs.next()) {

                runningModeList.put(Integer.toString(rs.getInt("CODE")), rs.getString("DESCRIPTION"));

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getStat.close();
        }
        return runningModeList;
    }

    public List<StatusTypeBean> getStatusType(Connection conOnline) throws Exception {
        PreparedStatement getStat = null;
        statusList = new ArrayList<StatusTypeBean>();
        try {
            getStat = conOnline.prepareStatement("SELECT STAT.ADMINCODE,STAT.DESCRIPTION,STAT.CATEGORYCODE"
                    + " FROM ECMS_ONLINE_STATUS STAT WHERE STAT.CATEGORYCODE='4' ");


            statusList = new ArrayList<StatusTypeBean>();

            rs = getStat.executeQuery();

            while (rs.next()) {

                StatusTypeBean bean = new StatusTypeBean();

                bean.setCode(Integer.toString(rs.getInt("ADMINCODE")));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setCategoryCode(Integer.toString(rs.getInt("CATEGORYCODE")));

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

    public HashMap<String, String> getKeyId(Connection conOnline, String type) throws Exception {
        PreparedStatement getStat = null;
        keyId = new HashMap<String, String>();
        try {
            getStat = conOnline.prepareStatement("SELECT C.COMKEYID,C.DESCRIPTION "
                    + " FROM ECMS_ONLINE_COMMUNICATIONKEYS C WHERE C.COMMUNICATIONTYPE = ? ");

            getStat.setInt(1, Integer.parseInt(type));
            rs = getStat.executeQuery();

            while (rs.next()) {

                keyId.put(Integer.toString(rs.getInt("COMKEYID")), rs.getString("DESCRIPTION"));

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getStat.close();
        }
        return keyId;
    }

    public List<ListenerConfigurationBean> getAllListenerDetails(Connection conOnline) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<ListenerConfigurationBean> listenerList = new ArrayList<ListenerConfigurationBean>();
        try {
            getAllByCatStat = conOnline.prepareStatement("SELECT L.LISTENERID, L.LISTENERTYPE, L.DESCRIPTION,"
                    + " L.PERPORT,L.TERPORT,L.KEYEXCHANGESTATUS,L.NOFCONNECTION,L.TIMEOUT,L.RUNMODE ,"
                    + " S.DESCRIPTION AS SDESCRIPTION,LT.DESCRIPTION AS LTDESCRIPTION,C.DESCRIPTION AS CDESCRIPTION,"
                    + " L.STATUS,L.COMKEYID,L.SECURITYSTATUS,L.STATUSOFACQUIRING,K.DESCRIPTION AS KDESCRIPTION "
                    + " FROM ECMS_ONLINE_LISTENERS L,ECMS_ONLINE_LISTENER_TYPE LT,"
                    + " ECMS_ONLINE_STATUS S,ECMS_ONLINE_CONNECTION_TYPE C,ECMS_ONLINE_COMMUNICATIONKEYS K"
                    + " WHERE L.STATUS=S.CODE AND L.LISTENERTYPE=LT.CODE AND C.CODE = L.RUNMODE AND K.COMKEYID = L.COMKEYID ");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                ListenerConfigurationBean listener = new ListenerConfigurationBean();


                listener.setListenerId(Integer.toString(rs.getInt("LISTENERID")));
                listener.setListenerType(Integer.toString(rs.getInt("LISTENERTYPE")));
                listener.setDescription(rs.getString("DESCRIPTION"));
                listener.setStatusDes(rs.getString("SDESCRIPTION"));
                listener.setListenerTypeDes(rs.getString("LTDESCRIPTION"));
                //listener.setStationId(rs.getString("STATIONID"));
                listener.setStatus(Integer.toString(rs.getInt("STATUS")));
                listener.setStablishPort(Integer.toString(rs.getInt("PERPORT")));
                listener.setTerminatedPort(Integer.toString(rs.getInt("TERPORT")));
                listener.setKeyExchangeStatus(Integer.toString(rs.getInt("KEYEXCHANGESTATUS")));
                listener.setNoOfConnection(Integer.toString(rs.getInt("NOFCONNECTION")));
                listener.setTimeout(Integer.toString(rs.getInt("TIMEOUT")));
                listener.setRuningMode(Integer.toString(rs.getInt("RUNMODE")));
                listener.setRunningModeDes(rs.getString("CDESCRIPTION"));
                //listener.setKeyExchangeStatusDes(rs.getString(""));
                listener.setKeyId(Integer.toString(rs.getInt("COMKEYID")));
                listener.setSequirtyStatus(Integer.toString(rs.getInt("SECURITYSTATUS")));
                listener.setStatusOfAquiring(Integer.toString(rs.getInt("STATUSOFACQUIRING")));
                listener.setKeyIdDes(rs.getString("KDESCRIPTION"));


                listenerList.add(listener);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();

        }
        return listenerList;
    }

    public boolean listenerInsert(Connection conOnline, ListenerConfigurationBean listener) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = conOnline.prepareStatement("INSERT INTO ECMS_ONLINE_LISTENERS "
                    + "( LISTENERTYPE,DESCRIPTION,STATUS,PERPORT,TERPORT,KEYEXCHANGESTATUS,"
                    + " NOFCONNECTION,TIMEOUT,RUNMODE,COMKEYID,SECURITYSTATUS,STATUSOFACQUIRING)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");




            insertStat.setInt(1, Integer.parseInt(listener.getListenerType()));
            insertStat.setString(2, listener.getDescription());
            insertStat.setInt(3, Integer.parseInt(listener.getStatus()));
            insertStat.setInt(4, Integer.parseInt(listener.getStablishPort()));
            insertStat.setInt(5, Integer.parseInt(listener.getTerminatedPort()));
            insertStat.setInt(6, Integer.parseInt(listener.getKeyExchangeStatus()));
            insertStat.setInt(7, Integer.parseInt(listener.getNoOfConnection()));
            insertStat.setInt(8, Integer.parseInt(listener.getTimeout()));
            insertStat.setInt(9, Integer.parseInt(listener.getRuningMode()));
            insertStat.setInt(10, Integer.parseInt(listener.getKeyId()));
            insertStat.setInt(11, Integer.parseInt(listener.getSequirtyStatus()));
            insertStat.setInt(12, Integer.parseInt(listener.getStatusOfAquiring()));

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean listenerUpdate(Connection conOnline, ListenerConfigurationBean listener) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = conOnline.prepareStatement("UPDATE ECMS_ONLINE_LISTENERS"
                    + " SET LISTENERTYPE=?,DESCRIPTION=?,STATUS=?,PERPORT=?,"
                    + " TERPORT=?,KEYEXCHANGESTATUS=?,NOFCONNECTION=?,TIMEOUT=?,RUNMODE=?,"
                    + " COMKEYID=?,SECURITYSTATUS=?,STATUSOFACQUIRING = ? "
                    + " WHERE LISTENERID=? ");



            updateStat.setInt(1, Integer.parseInt(listener.getListenerType()));
            updateStat.setString(2, listener.getDescription());
            updateStat.setInt(3, Integer.parseInt(listener.getStatus()));
            updateStat.setInt(4, Integer.parseInt(listener.getStablishPort()));
            updateStat.setInt(5, Integer.parseInt(listener.getTerminatedPort()));
            updateStat.setInt(6, Integer.parseInt(listener.getKeyExchangeStatus()));
            updateStat.setInt(7, Integer.parseInt(listener.getNoOfConnection()));
            updateStat.setInt(8, Integer.parseInt(listener.getTimeout()));
            updateStat.setInt(9, Integer.parseInt(listener.getRuningMode()));

            updateStat.setInt(10, Integer.parseInt(listener.getKeyId()));
            updateStat.setInt(11, Integer.parseInt(listener.getSequirtyStatus()));
            updateStat.setInt(12, Integer.parseInt(listener.getStatusOfAquiring()));

            updateStat.setInt(13, Integer.parseInt(listener.getListenerId()));


            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean listenerDelete(Connection conOnline, ListenerConfigurationBean listener) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = conOnline.prepareStatement("DELETE ECMS_ONLINE_LISTENERS WHERE LISTENERID=?");

            deleteStat.setInt(1, Integer.parseInt(listener.getListenerId()));


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
