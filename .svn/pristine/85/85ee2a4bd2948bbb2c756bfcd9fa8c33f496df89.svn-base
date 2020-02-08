/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BillingCycleMgtBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * contains all SQL queries about billing cycle management
 * @author nisansala
 */
public class BillingCycleMgtPersistance {

    ResultSet rs = null;

    /**
     * to retrieve all the billing cycle data
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<BillingCycleMgtBean> getAllBillingData(Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;

        try {
            List<BillingCycleMgtBean> billingList = new ArrayList<BillingCycleMgtBean>();
            String query = "SELECT BC.BILLINGCYCLECODE,BC.BILLINGDATE,BC.DESCRIPTION,BC.HOLIDAYACTION,BC.NEXTBILLINGDATE,"
                    + "BC.STATUS,S.DESCRIPTION AS STATUSDES,BC.LASTUPDATEDUSER,BC.LASTUPDATETIME,BC.CREATEDDATETIME"
                    + " FROM BILLINGCYCLE BC,STATUS S WHERE S.STATUSCODE=BC.STATUS";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                BillingCycleMgtBean bean = new BillingCycleMgtBean();

                bean.setBillCycleCode(rs.getString("BILLINGCYCLECODE"));
                bean.setBillDate(rs.getString("BILLINGDATE"));
                bean.setBillDescription(rs.getString("DESCRIPTION"));
                bean.setHolidayAction(rs.getString("HOLIDAYACTION"));
                bean.setBillStatus(rs.getString("STATUS"));
                bean.setBillStatusDes(rs.getString("STATUSDES"));
                //bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATETIME"));
                bean.setCreatedTime(rs.getDate("CREATEDDATETIME"));
                bean.setNextbillingDate(this.convertStringDate(rs.getDate("NEXTBILLINGDATE")));
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

    /**
     * insert new billing cycle record
     * @param bill
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public Boolean insertNewBillCycle(BillingCycleMgtBean bill, Connection con) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO BILLINGCYCLE (BILLINGCYCLECODE,"
                    + "BILLINGDATE,DESCRIPTION,HOLIDAYACTION,STATUS,LASTUPDATEDUSER,"
                    + "CREATEDDATETIME,NEXTBILLINGDATE) VALUES(?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, bill.getBillCycleCode());
            ps.setString(2, bill.getBillDate());
            ps.setString(3, bill.getBillDescription());
            ps.setString(4, bill.getHolidayAction());
            ps.setString(5, bill.getBillStatus());
            ps.setString(6, bill.getLastUpdatedUser());
            ps.setTimestamp(7, SystemDateTime.getSystemDataAndTime());
            ps.setTimestamp(8, new Timestamp(this.stringTodate(bill.getNextbillingDate()).getTime()));

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

    /**
     * to view one billing cycle record
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public BillingCycleMgtBean viewOneBillingCycle(Connection con, String id) throws SQLException, Exception {

        BillingCycleMgtBean bean = new BillingCycleMgtBean();

        PreparedStatement ps = null;
        try {
            String query = "SELECT BC.BILLINGCYCLECODE,BC.BILLINGDATE,BC.DESCRIPTION,BC.HOLIDAYACTION,"
                    + " BC.STATUS,S.DESCRIPTION AS STATUSDES,BC.NEXTBILLINGDATE "
                    + " FROM BILLINGCYCLE BC,STATUS S WHERE S.STATUSCODE=BC.STATUS AND BC.BILLINGCYCLECODE=?";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setBillCycleCode(rs.getString("BILLINGCYCLECODE"));
                bean.setBillDate(rs.getString("BILLINGDATE"));
                bean.setBillDescription(rs.getString("DESCRIPTION"));
                bean.setHolidayAction(rs.getString("HOLIDAYACTION"));
                bean.setBillStatus(rs.getString("STATUS"));
                bean.setBillStatusDes(rs.getString("STATUSDES"));
                bean.setNextbillingDate(this.convertStringDate(rs.getDate("NEXTBILLINGDATE")));
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
     * to delete a billing cycle record
     * @param con
     * @param billCode
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteBillingData(Connection con, String billCode) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM BILLINGCYCLE where BILLINGCYCLECODE =?";

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
    
    public int accountCount(Connection con, String billCode) throws SQLException, Exception {
        int count = -1;
        PreparedStatement ps = null;
        
        try {
            String query = "select COUNT(ACCOUNTNO) from CARDACCOUNT where BILLINGID=? and STATUS=?";

            ps = con.prepareStatement(query);
            ps.setString(1, billCode);
            ps.setString(2, StatusVarList.ACTIVE_STATUS);
           
            rs = ps.executeQuery();
            while(rs.next()){
                count=Integer.parseInt(rs.getString("COUNT(ACCOUNTNO)"));
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
        return count;
    }

    /**
     * to update a billing cycle record
     * @param bill
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int updateBillCycle(BillingCycleMgtBean bill, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE BILLINGCYCLE SET "
                    + "BILLINGDATE=?,DESCRIPTION=?,HOLIDAYACTION=?,STATUS=?,LASTUPDATEDUSER=?,"
                    + "LASTUPDATETIME=?,NEXTBILLINGDATE=? WHERE BILLINGCYCLECODE=? ";

            ps = con.prepareStatement(query);

            ps.setString(1, bill.getBillDate());
            ps.setString(2, bill.getBillDescription());
            ps.setString(3, bill.getHolidayAction());
            ps.setString(4, bill.getBillStatus());
            ps.setString(5, bill.getLastUpdatedUser());
            ps.setTimestamp(6, SystemDateTime.getSystemDataAndTime());
            ps.setTimestamp(7, new Timestamp(this.stringTodate(bill.getNextbillingDate()).getTime()));
            ps.setString(8, bill.getBillCycleCode());

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

    public boolean isHoliday(Connection CMSCon, String nextBillingDate) throws Exception {
        boolean isHoliday = false;
        PreparedStatement ps = null;
        try {
            ps = CMSCon.prepareStatement("SELECT H.DAY FROM HOLIDAY H WHERE H.YEAR =? AND H.MONTH =? AND H.DAY = ?");

            ps.setString(1, nextBillingDate.substring(0, 4));
            ps.setString(2, nextBillingDate.substring(5, 7));
            ps.setString(3, nextBillingDate.substring(8, 10));

            rs = ps.executeQuery();

            while (rs.next()) {
                isHoliday = true;
            }


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
        return isHoliday;
    }

    public Date stringTodate(String date) throws ParseException {
        String dateString = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = dateFormat.parse(dateString);
        return convertedDate;
    }

    private String convertStringDate(java.sql.Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return (dateFormat.format(date));
    }
}
