/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AlertBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sajith
 */
public class AlertMgtPersistance {

    private ResultSet rs = null;

    /**
     * to take existing alert list to the user view
     * @param cmsCon
     * @return
     * @throws Exception 
     */
    public List<AlertBean> getAlerts(Connection cmsCon) throws Exception {

        List<AlertBean> allAlerts = new ArrayList<AlertBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT asconf.alerttype,asconf.description,asconf.ip,asconf.port,asconf.porttimeout,asconf.sockettimeout,asconf.username,asconf.password,asconf.sender,st.description as statusDes,asconf.createddate,asconf.lastupdateddate,asconf.lastupdateduser FROM ALERTSERVERCONFIG asconf, STATUS st WHERE asconf.status=st.statuscode";

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {

                AlertBean bean=new AlertBean();
                bean.setAlertType(rs.getString("alerttype"));
                bean.setDescription(rs.getString("description"));
                bean.setIp(rs.getString("ip"));
                bean.setPort(rs.getString("port"));
                bean.setPortTimeOut(rs.getString("porttimeout"));
                bean.setSocketTimeOut(rs.getString("sockettimeout"));
                bean.setUserName(rs.getString("username"));
                bean.setPassword(rs.getString("password"));
                bean.setSender(rs.getString("sender"));
                bean.setStatus(rs.getString("statusDes"));
                bean.setCreatedDate(rs.getTimestamp("createddate"));
                bean.setLastUpdatedDate(rs.getTimestamp("lastupdateddate"));
                bean.setLastUpdatedUser(rs.getString("lastupdateduser"));
                allAlerts.add(bean);
                
            }

            return allAlerts;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }


    }

    /**
     * to add a new alert to the database
     * @param cmsCon
     * @param bean
     * @return
     * @throws Exception 
     */
    public int addNewAlert(Connection cmsCon, AlertBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO ALERTSERVERCONFIG (ALERTTYPE, DESCRIPTION, IP, PORT, PORTTIMEOUT, SOCKETTIMEOUT, USERNAME, PASSWORD, SENDER, STATUS, CREATEDDATE, LASTUPDATEDDATE, LASTUPDATEDUSER) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,SYSDATE, SYSDATE, ?)";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getAlertType());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getIp());
            ps.setString(4, bean.getPort());
            ps.setString(5, bean.getPortTimeOut());
            ps.setString(6, bean.getSocketTimeOut());
            ps.setString(7, bean.getUserName());
            ps.setString(8, bean.getPassword());
            ps.setString(9, bean.getSender());
            ps.setString(10, bean.getStatus());
            ps.setString(11, bean.getLastUpdatedUser());

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

    /**
     * to update alert details
     * @param cmsCon
     * @param bean
     * @return
     * @throws Exception 
     */
    public int updateAlert(Connection cmsCon, AlertBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "UPDATE ALERTSERVERCONFIG SET DESCRIPTION=?,IP=?,PORT=?,PORTTIMEOUT=?,SOCKETTIMEOUT=?,USERNAME=?,PASSWORD=?,SENDER=?,STATUS=?,LASTUPDATEDDATE=?,LASTUPDATEDUSER=? WHERE ALERTTYPE=?";

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getIp());
            ps.setString(3, bean.getPort());
            ps.setString(4, bean.getPortTimeOut());
            ps.setString(5, bean.getSocketTimeOut());
            ps.setString(6, bean.getUserName());
            ps.setString(7, bean.getPassword());
            ps.setString(8, bean.getSender());
            ps.setString(9, bean.getStatus());
            ps.setTimestamp(10, new Timestamp(new Date().getTime()));
            ps.setString(11, bean.getLastUpdatedUser());
            ps.setString(12, bean.getAlertType());

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

    /**
     * to take the list of status(active and de active)
     * @param cmsCon
     * @return
     * @throws Exception 
     */
    public List<StatusBean> getStatustList(Connection cmsCon) throws Exception {

        List<StatusBean> statustList = new ArrayList<StatusBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT STATUSCODE, DESCRIPTION FROM STATUS WHERE STATUSCATEGORY = ?";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, StatusVarList.GENESTATUCAT);
            rs = ps.executeQuery();

            while (rs.next()) {
                StatusBean bean = new StatusBean();

                bean.setStatusCode(rs.getString("STATUSCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                statustList.add(bean);
            }

            return statustList;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * to delete a alert from database
     * @param cmsCon
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteAlert(Connection cmsCon, String id) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM ALERTSERVERCONFIG WHERE ALERTTYPE=? ";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, id);

            i = ps.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return i;
    }

    /**
     * to view selected alert details
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public AlertBean viewSelectedAlertDetails(Connection con, String id) throws SQLException, Exception {

        AlertBean bean=new AlertBean();

        PreparedStatement ps = null;
        try {
            String query = "SELECT asconf.alerttype,asconf.description,asconf.ip,asconf.port,asconf.porttimeout,asconf.sockettimeout,asconf.username,asconf.password,asconf.sender,st.statuscode as statusCode,st.description as statusDesc,asconf.createddate,asconf.lastupdateddate,asconf.lastupdateduser FROM ALERTSERVERCONFIG asconf, STATUS st WHERE asconf.status=st.statuscode AND asconf.alerttype = ? ";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setAlertType(rs.getString("alerttype"));
                bean.setDescription(rs.getString("description"));
                bean.setIp(rs.getString("ip"));
                bean.setPort(rs.getString("port"));
                bean.setPortTimeOut(rs.getString("porttimeout"));
                bean.setSocketTimeOut(rs.getString("sockettimeout"));
                bean.setUserName(rs.getString("username"));
                bean.setPassword(rs.getString("password"));
                bean.setSender(rs.getString("sender"));
                bean.setStatus(rs.getString("statusCode"));
                bean.setStatusDescription(rs.getString("statusDesc"));
                bean.setCreatedDate(rs.getTimestamp("createddate"));
                bean.setLastUpdatedDate(rs.getTimestamp("lastupdateddate"));
                bean.setLastUpdatedUser(rs.getString("lastupdateduser"));
                
            }
            return bean;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
