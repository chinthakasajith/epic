/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.HotlistReasonBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author badrika
 */
public class HotlistReasonMgtPersistance {

    private ResultSet rs = null;

    /**
     * to get hot list reason list
     * @param cmsCon
     * @return
     * @throws Exception 
     */
    public List<HotlistReasonBean> getHotlistReasons(Connection cmsCon) throws Exception {

        List<HotlistReasonBean> reasonList = new ArrayList<HotlistReasonBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT HR.REASONCODE,HR.DESCRIPTION AS HRDES,HR.STATUS,ST.DESCRIPTION AS STDES,HR.LASTUPDATEDTIME FROM HOTLISTREASON HR, STATUS ST WHERE HR.STATUS=ST.STATUSCODE ";

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                HotlistReasonBean bean = new HotlistReasonBean();
                bean.setReasonCode(rs.getString("REASONCODE"));
                bean.setDescription(rs.getString("HRDES"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STDES"));
                bean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));

                reasonList.add(bean);
            }

            return reasonList;

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
     * to add a new reason record
     * @param cmsCon
     * @param bean
     * @return
     * @throws Exception 
     */
    public int addNewHotlistReason(Connection cmsCon, HotlistReasonBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO HOTLISTREASON (REASONCODE,DESCRIPTION,STATUS,CREATEDTIME,LASTUPDATEDUSER,LASTUPDATEDTIME) VALUES(?,?,?,SYSDATE,?,?)";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getReasonCode());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getStatusDes());
            ps.setString(4, bean.getLastUpdatedUser());
            ps.setTimestamp(5, new Timestamp(bean.getLastUpdatedTime().getTime()));

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
     * to update a record
     * @param cmsCon
     * @param bean
     * @return
     * @throws Exception 
     */
    public int updateReason(Connection cmsCon, HotlistReasonBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "UPDATE HOTLISTREASON SET DESCRIPTION=?,STATUS=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=? WHERE REASONCODE=?";

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getStatusDes());
            ps.setString(3, bean.getLastUpdatedUser());
            ps.setTimestamp(4, new Timestamp(bean.getLastUpdatedTime().getTime()));
            ps.setString(5, bean.getReasonCode());

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
     * to view a selected record
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public HotlistReasonBean viewSelectedReason(Connection con, String id) throws SQLException, Exception {

        HotlistReasonBean bean = new HotlistReasonBean();

        PreparedStatement ps = null;
        try {
            String query = "SELECT HR.REASONCODE,HR.DESCRIPTION AS HRDES,HR.STATUS,ST.DESCRIPTION AS STDES FROM HOTLISTREASON HR, STATUS ST WHERE HR.STATUS=ST.STATUSCODE AND HR.REASONCODE=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setReasonCode(rs.getString("REASONCODE"));
                bean.setDescription(rs.getString("HRDES"));
                bean.setStatusDes(rs.getString("STATUS"));
                bean.setStatus(rs.getString("STDES"));
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

    /**
     * to delete a record
     * @param cmsCon
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteReason(Connection cmsCon, String id) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM HOTLISTREASON WHERE REASONCODE=? ";

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
}
