/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.MerchantPaymentCycleBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class MerchantPaymentCyclePersistance {

    ResultSet rs;

    public List<MerchantPaymentCycleBean> getAllPaymentData(Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;

        try {
            List<MerchantPaymentCycleBean> paymentList = new ArrayList<MerchantPaymentCycleBean>();
            String query = "SELECT MPC.PAYMENTCYCLECODE,MPC.PAYMENTOPTION,MPC.PAYMENTDATE,MPC.DESCRIPTION,MPC.HOLIDAYACTION,MPC.STATUS,"
                    + "S.DESCRIPTION AS STATUSDES, "
                    + "MPC.LASTUPDATEDUSER,MPC.CREATEDDATETIME,MPC.LASTUPDATETIME "
                    + "FROM MERCHANTPAYMENTCYCLE MPC,STATUS S "
                    + "WHERE S.STATUSCODE=MPC.STATUS";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                MerchantPaymentCycleBean bean = new MerchantPaymentCycleBean();

                bean.setPaymentCycleCode(rs.getString("PAYMENTCYCLECODE"));
                bean.setPaymentOption(rs.getString("PAYMENTOPTION"));
                bean.setPaymentDate(rs.getString("PAYMENTDATE"));
                bean.setHolidayAction(rs.getString("HOLIDAYACTION"));
                bean.setPaymentDescription(rs.getString("DESCRIPTION"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDescripton(rs.getString("STATUSDES"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATETIME"));
                bean.setCreatedTime(rs.getDate("CREATEDDATETIME"));

                paymentList.add(bean);
            }
            return paymentList;
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

    public Boolean insertNewCycle(MerchantPaymentCycleBean state, Connection con) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO MERCHANTPAYMENTCYCLE (PAYMENTCYCLECODE,"
                    + "PAYMENTOPTION,PAYMENTDATE,DESCRIPTION,HOLIDAYACTION,STATUS,"
                    + "LASTUPDATEDUSER,CREATEDDATETIME) VALUES(?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, state.getPaymentCycleCode());
            ps.setString(2, state.getPaymentOption());
            ps.setString(3, state.getPaymentDate());
            ps.setString(4, state.getPaymentDescription());
            ps.setString(5, state.getHolidayAction());
            ps.setString(6, state.getStatus());
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

    public int updateCycle(MerchantPaymentCycleBean state, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE MERCHANTPAYMENTCYCLE SET PAYMENTCYCLECODE=?,"
                    + "PAYMENTOPTION=?,PAYMENTDATE=?,DESCRIPTION=?,HOLIDAYACTION=?,STATUS=?,"
                    + "LASTUPDATEDUSER=?,CREATEDDATETIME=? WHERE PAYMENTCYCLECODE=?";

            ps = con.prepareStatement(query);

            ps = con.prepareStatement(query);
            ps.setString(1, state.getPaymentCycleCode());
            ps.setString(2, state.getPaymentOption());
            ps.setString(3, state.getPaymentDate());
            ps.setString(4, state.getPaymentDescription());
            ps.setString(5, state.getHolidayAction());
            ps.setString(6, state.getStatus());
            ps.setString(7, state.getLastUpdatedUser());
            ps.setTimestamp(8, SystemDateTime.getSystemDataAndTime());
            ps.setString(9, state.getPaymentCycleCode());

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

    public int deleteCycle(Connection con, String paymentCode) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM MERCHANTPAYMENTCYCLE WHERE PAYMENTCYCLECODE =?";

            ps = con.prepareStatement(query);
            ps.setString(1, paymentCode);

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
