/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.HolidayManagementBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.HolidayManagementPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.variable.MessageVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author upul
 */
public class HolidayManagementManager {
    
        private HolidayManagementPersistance holidayManagementPersistance;
        private Connection CMSCon;
        private SystemAuditManager sysAuditManager;
        
        
        
      /**
         * inserHoliday
         * @param holidayBean
         * @return
         * @throws Exception 
         */  
      public boolean inserHoliday(HolidayManagementBean holidayBean,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        int result=-1;
        try {
            sysAuditManager=new SystemAuditManager();
            holidayManagementPersistance = new HolidayManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            //insert audittrace
            result= sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            
            if(result==1){
                //set updated user
            holidayBean.setLastUpdatedUser(systemAuditBean.getUserName());
            
            success = holidayManagementPersistance.insertHolidayByForm(CMSCon, holidayBean);
            }
            else{
                
            }
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }
      
      
      
      /**
       * updateHoliday
       * @param holidayBean
       * @param systemAuditBean
       * @return
       * @throws Exception 
       */
      public boolean updateHoliday(HolidayManagementBean holidayBean ,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        int result=-1;
        try {
            holidayManagementPersistance = new HolidayManagementPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            
            success = holidayManagementPersistance.getholidayListForMonthDate(CMSCon, holidayBean);
            
            if(success){
             //insert audittrace
            result= sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            
            if(result==1){
                //set updated user
            holidayBean.setLastUpdatedUser(systemAuditBean.getUserName());
            
            success = holidayManagementPersistance.updateHolidayReason(CMSCon, holidayBean);
            }
            else{
                
            }
            
            }
            else{
                throw new SQLException(MessageVarList.ORA_HOLIDAY_VALUE_NOT_FOUND);
            }
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }    
      
     
      /**
       * deleteHoliday
       * @param holidayManagementBean
       * @param systemAuditBean
       * @return
       * @throws SQLException
       * @throws Exception 
       */
       public boolean deleteHoliday(HolidayManagementBean holidayManagementBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean result =false;
        boolean success = false;
        int resultaudit=-1;
        try {
            holidayManagementPersistance = new HolidayManagementPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            
            
            success = holidayManagementPersistance.getholidayListForMonthDate(CMSCon, holidayManagementBean);
            
            if(success){
             //insert audittrace
            resultaudit= sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            
            if(resultaudit==1){
                //set updated user
            holidayManagementBean.setLastUpdatedUser(systemAuditBean.getUserName());
            
            result = holidayManagementPersistance.deleteHoliday(CMSCon, holidayManagementBean);
            }
            else{
                
            }
            }
            else{
                throw new SQLException(MessageVarList.ORA_HOLIDAY_VALUE_NOT_FOUND);
            }

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return result;
    }

     
       
       
       /**
        * getholidayListForMonth
        * @param holidayManagementBean
        * @return
        * @throws Exception 
        */
         public List<HolidayManagementBean> getholidayListForMonth(HolidayManagementBean holidayManagementBean) throws Exception {
       
              List<HolidayManagementBean> hmbs=new ArrayList<HolidayManagementBean>();
             try {
           
           holidayManagementPersistance = new HolidayManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            hmbs = holidayManagementPersistance.getholidayListForMonth(CMSCon, holidayManagementBean);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return hmbs;
    }
     
       /**
        * getholidayListForYearMonthDate
        * @param holidayManagementBean
        * @return
        * @throws Exception 
        */
         public List<HolidayManagementBean> getholidayListForYearMonthDate(HolidayManagementBean holidayManagementBean) throws Exception {
       
              List<HolidayManagementBean> hmbs=new ArrayList<HolidayManagementBean>();
             try {
           
           holidayManagementPersistance = new HolidayManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            hmbs = holidayManagementPersistance.getholidayListForYearMonthDate(CMSCon, holidayManagementBean);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return hmbs;
    }
         
    
}
