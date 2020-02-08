/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ApplicationRejectReasonBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * this persistence class use to wrote all the persistence
 * that relate to application reject reason
 * @author ayesh
 */
public class ApplicationRejectreasonOersistence {

    private ResultSet rs = null;

    /**
     * get all available reason list from database
     * @param con
     * @return
     * @throws Exception 
     */
    public List<ApplicationRejectReasonBean> getAllReasobList(Connection con) throws Exception {

        List<ApplicationRejectReasonBean> allReason = new ArrayList<ApplicationRejectReasonBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT APPR.REASONCODE,APPR.DESCRIPTION, APPR.LASTUPDATEDUSER FROM APPLICATIONREJECTREASON APPR ";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                ApplicationRejectReasonBean bean = new ApplicationRejectReasonBean();
                bean.setReasonCode(rs.getString("REASONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setLastUpdateUser(rs.getString("LASTUPDATEDUSER"));
                allReason.add(bean);
            }

            return allReason;
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
     * add new application reject reason to database
     * @param con
     * @param bean
     * @return number of rows that insert
     * @throws Exception 
     */
    public int addAppRejectReason(Connection con, ApplicationRejectReasonBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO APPLICATIONREJECTREASON CT (CT.REASONCODE, CT.DESCRIPTION, CT.LASTUPDATEDUSER )"
                    + " VALUES(?,?, ?)";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getReasonCode());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getLastUpdateUser());


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
     * get selected reason application reject reason from data base
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public ApplicationRejectReasonBean getSelectedReason(Connection con, String id) throws SQLException, Exception {

        ApplicationRejectReasonBean bean = new ApplicationRejectReasonBean();

        PreparedStatement ps = null;
        try {
            String query = "SELECT APP.DESCRIPTION,APP.REASONCODE,APP.LASTUPDATEDUSER"
                    + " FROM APPLICATIONREJECTREASON APP WHERE APP.REASONCODE=?";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setReasonCode(rs.getString("REASONCODE"));
                bean.setLastUpdateUser(rs.getString("LASTUPDATEDUSER"));
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
     * delete application reject reason from database
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteAppReject(Connection con, String id) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM APPLICATIONREJECTREASON APP where REASONCODE=? ";

            ps = con.prepareStatement(query);
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
     * update application reject reason to database
     * @param con
     * @param bean
     * @return
     * @throws Exception 
     */
    public int updateRejectReason(Connection con, ApplicationRejectReasonBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE APPLICATIONREJECTREASON SET DESCRIPTION=? , LASTUPDATEDUSER=?"
                    + " WHERE REASONCODE=?";
            ps = con.prepareStatement(query);
            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getLastUpdateUser());
            ps.setString(3, bean.getReasonCode());

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
}
