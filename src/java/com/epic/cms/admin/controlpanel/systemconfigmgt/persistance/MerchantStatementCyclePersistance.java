/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.MerchantStatementCycleBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
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
public class MerchantStatementCyclePersistance {

    ResultSet rs;

    public List<MerchantStatementCycleBean> getAllBillingData(Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;

        try {
            List<MerchantStatementCycleBean> billingList = new ArrayList<MerchantStatementCycleBean>();
            String query = "SELECT MBC.BILLINGCYCLECODE,MBC.BILLINGOPTION,MBC.BILLINGDATE,MBC.DESCRIPTION,MBC.HOLIDAYACTION,MBC.STATUS,"
                    + "S.DESCRIPTION AS STATUSDES, "
                    + "MBC.LASTUPDATEDUSER,MBC.CREATEDDATETIME,MBC.LASTUPDATETIME "
                    + "FROM MERCHANTBILLINGCYCLE MBC,STATUS S "
                    + "WHERE S.STATUSCODE=MBC.STATUS";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                MerchantStatementCycleBean bean = new MerchantStatementCycleBean();

                bean.setStateCycleCode(rs.getString("BILLINGCYCLECODE"));
                bean.setStateOption(rs.getString("BILLINGOPTION"));
                bean.setStateDate(rs.getString("BILLINGDATE"));
                bean.setHolidayAction(rs.getString("HOLIDAYACTION"));
                bean.setStateDescription(rs.getString("DESCRIPTION"));
                bean.setStateStatus(rs.getString("STATUS"));
                bean.setStateStatusDes(rs.getString("STATUSDES"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATETIME"));
                bean.setCreatedTime(rs.getDate("CREATEDDATETIME"));

                billingList.add(bean);
            }
            return billingList;
        } catch (SQLException ex) {
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

    public Boolean insertNewCycle(MerchantStatementCycleBean state, Connection con) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO MERCHANTBILLINGCYCLE (BILLINGCYCLECODE,"
                    + "BILLINGOPTION,BILLINGDATE,DESCRIPTION,HOLIDAYACTION,STATUS,"
                    + "LASTUPDATEDUSER,CREATEDDATETIME) VALUES(?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, state.getStateCycleCode());
            ps.setString(2, state.getStateOption());
            ps.setString(3, state.getStateDate());
            ps.setString(4, state.getStateDescription());
            ps.setString(5, state.getHolidayAction());
            ps.setString(6, state.getStateStatus());
            ps.setString(7, state.getLastUpdatedUser());
            ps.setTimestamp(8, SystemDateTime.getSystemDataAndTime());

            int row = ps.executeUpdate();
            if (row == 1) {
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

    public int updateCycle(MerchantStatementCycleBean state, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE MERCHANTBILLINGCYCLE SET BILLINGCYCLECODE=?,"
                    + "BILLINGOPTION=?,BILLINGDATE=?,DESCRIPTION=?,HOLIDAYACTION=?,STATUS=?,"
                    + "LASTUPDATEDUSER=?,CREATEDDATETIME=? WHERE BILLINGCYCLECODE=?";

            ps = con.prepareStatement(query);

            ps = con.prepareStatement(query);
            ps.setString(1, state.getStateCycleCode());
            ps.setString(2, state.getStateOption());
            ps.setString(3, state.getStateDate());
            ps.setString(4, state.getStateDescription());
            ps.setString(5, state.getHolidayAction());
            ps.setString(6, state.getStateStatus());
            ps.setString(7, state.getLastUpdatedUser());
            ps.setTimestamp(8, SystemDateTime.getSystemDataAndTime());
            ps.setString(9, state.getStateCycleCode());

            row = ps.executeUpdate();

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

        return row;
    }

    public int deleteCycle(Connection con, String billCode) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM MERCHANTBILLINGCYCLE WHERE BILLINGCYCLECODE =?";

            ps = con.prepareStatement(query);
            ps.setString(1, billCode);

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
