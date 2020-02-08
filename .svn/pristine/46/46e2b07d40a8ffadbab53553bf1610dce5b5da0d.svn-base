/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.HolidayManagementBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author upul
 */
public class HolidayManagementPersistance {
    
    
       private ResultSet rs = null;
    
   /**
     * insertHolidayByForm
     * @param con
     * @param holidayBean
     * @return
     * @throws Exception 
     */
     public boolean insertHolidayByForm(Connection con, HolidayManagementBean holidayBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO HOLIDAY(YEAR,MONTH,DAY,HOLIDAYREASON,LASTUPDATEDUSER) VALUES(?,?,?,?,?) ");
            insertStat.setString(1, holidayBean.getYear());
            insertStat.setString(2, holidayBean.getMonth());
            insertStat.setString(3, holidayBean.getDay());
            insertStat.setString(4, holidayBean.getHolidayReason());
            insertStat.setString(5, holidayBean.getLastUpdatedUser());
           

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }
    
     
     
     
     /**
      * updateHolidayReason
      * @param con
      * @param holidayBean
      * @return
      * @throws Exception 
      */
     
      public boolean updateHolidayReason(Connection con, HolidayManagementBean holidayBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            
            updateStat = con.prepareStatement("UPDATE HOLIDAY SET HOLIDAYREASON=?,LASTUPDATEDUSER=? WHERE YEAR=? AND MONTH=? AND DAY=?");
            System.out.println( holidayBean.getHolidayReason()+"-------------------------");
            updateStat.setString(1, holidayBean.getHolidayReason());
            updateStat.setString(2, holidayBean.getLastUpdatedUser());
            updateStat.setString(3, holidayBean.getYear());
            updateStat.setString(4, holidayBean.getMonth());
            updateStat.setString(5, holidayBean.getDay());
           

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }
     
   
      
      /**
       * deleteHoliday
       * @param con
       * @param holidayBean
       * @return
       * @throws Exception 
       */
       public boolean deleteHoliday(Connection con, HolidayManagementBean holidayBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = con.prepareStatement("DELETE HOLIDAY WHERE YEAR=? AND MONTH=? AND DAY=? ");
            deleteStat.setString(1, holidayBean.getYear());
            deleteStat.setString(2, holidayBean.getMonth());
            deleteStat.setString(3, holidayBean.getDay());

            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            deleteStat.close();
        }
        return success;
    }
    
    
       
       /**
        * getholidayListForMonth
        * @param con
        * @param bean
        * @return
        * @throws SQLException
        * @throws Exception 
        */
        public List<HolidayManagementBean> getholidayListForMonth(Connection con,HolidayManagementBean  bean) throws SQLException, Exception {


        List<HolidayManagementBean> holidayList = new ArrayList<HolidayManagementBean>();


        PreparedStatement ps = null;
        try {
            String querySlctHldy = "SELECT H.DAY,H.HOLIDAYREASON FROM HOLIDAY H WHERE H.YEAR=? AND H.MONTH=?";
            ps = con.prepareStatement(querySlctHldy);
            ps.setString(1, bean.getYear());
            ps.setString(2, bean.getMonth());

            rs = ps.executeQuery();

            while (rs.next()) {
               
                HolidayManagementBean hmb=new HolidayManagementBean();
                hmb.setDay(rs.getString("DAY"));
                hmb.setHolidayReason(rs.getString("HOLIDAYREASON"));

                holidayList.add(hmb);
                
            }

            return holidayList;
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
        * getholidayListForMonth
        * @param con
        * @param bean
        * @return
        * @throws SQLException
        * @throws Exception 
        */
        public boolean getholidayListForMonthDate(Connection con,HolidayManagementBean  bean) throws SQLException, Exception {


        boolean isExist=false;
        List<HolidayManagementBean> holidayList = new ArrayList<HolidayManagementBean>();


        PreparedStatement ps = null;
        try {
            String querySlctHldy = "SELECT H.DAY,H.HOLIDAYREASON FROM HOLIDAY H WHERE H.YEAR=? AND H.MONTH=? AND H.DAY=?";
            ps = con.prepareStatement(querySlctHldy);
            ps.setString(1, bean.getYear());
            ps.setString(2, bean.getMonth());
            ps.setString(3, bean.getDay());

            rs = ps.executeQuery();

            while (rs.next()) {
               
                HolidayManagementBean hmb=new HolidayManagementBean();
                hmb.setDay(rs.getString("DAY"));
                hmb.setHolidayReason(rs.getString("HOLIDAYREASON"));

                holidayList.add(hmb);
                
                isExist=true;
                
                
            }

            return isExist;
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
        * getholidayListForMonth
        * @param con
        * @param bean
        * @return
        * @throws SQLException
        * @throws Exception 
        */
        public List<HolidayManagementBean> getholidayListForYearMonthDate(Connection con,HolidayManagementBean  bean) throws SQLException, Exception {


        List<HolidayManagementBean> holidayList = new ArrayList<HolidayManagementBean>();


        PreparedStatement ps = null;
        try {
            String querySlctHldy = "SELECT H.DAY,H.HOLIDAYREASON FROM HOLIDAY H WHERE H.YEAR=? AND H.MONTH=? AND H.DAY=?";
            ps = con.prepareStatement(querySlctHldy);
            ps.setString(1, bean.getYear());
            ps.setString(2, bean.getMonth());
            ps.setString(3, bean.getDay());

            rs = ps.executeQuery();

            while (rs.next()) {
               
                HolidayManagementBean hmb=new HolidayManagementBean();
                hmb.setDay(rs.getString("DAY"));
                hmb.setHolidayReason(rs.getString("HOLIDAYREASON"));

                holidayList.add(hmb);
                

                
                
            }
            return holidayList;
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
