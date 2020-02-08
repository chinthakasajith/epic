/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.businesslogic;

import com.epic.cms.admin.controlpanel.profilemgt.bean.InterestProfileTransactionBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.InterestprofileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionTypeBean;
import com.epic.cms.admin.controlpanel.profilemgt.persistance.InterestProfilePersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class InterestProfileManager {
     private Connection CMSCon;
     private SystemAuditManager sysAuditManager;
     private InterestProfilePersistance interestPersis;
     private List<InterestprofileBean> interestProfileDetails;
     private List<TransactionTypeBean> transactionTypeDetails;
        
     public List<InterestprofileBean> getTaskDetails() throws Exception {
        try {
            interestPersis = new InterestProfilePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            interestProfileDetails = interestPersis.getInterestProfileDetails(CMSCon);


            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return interestProfileDetails;
    }
     
     
     public List<TransactionTypeBean> getTransactionTypekDetails() throws Exception {
        try {
           interestPersis = new InterestProfilePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            transactionTypeDetails = interestPersis.getTransactionTypeDetails(CMSCon);


            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return transactionTypeDetails;
    }
    
        
     public boolean inserInterestprofile(InterestprofileBean interest) throws Exception {
        boolean success = false;
        try {
            interestPersis = new InterestProfilePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = interestPersis.insertInterestProfile(CMSCon, interest);
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
     
     
     public boolean insertToInterestProfileTransaction(InterestprofileBean interest,String[] assigned,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            sysAuditManager = new SystemAuditManager();
            interestPersis = new InterestProfilePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            for (int i = 0; i < assigned.length; i++) {
                success = interestPersis.insertToInterestProfileTransaction(CMSCon, interest,assigned[i]);
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
       
     
     
     
     
       
      public List<TransactionTypeBean> getTransactionTypeDescriptionNotAssignedList(String interestProfileCode) throws Exception {
           List<TransactionTypeBean> transactionTypeDescriptionList = new ArrayList<TransactionTypeBean>();
          try {
           
            interestPersis = new InterestProfilePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            transactionTypeDescriptionList = interestPersis.getNotAssignedTransactionTypeDescription(CMSCon,interestProfileCode);

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
        return transactionTypeDescriptionList;
    }
     
     public List<TransactionTypeBean> getAssignedTransactions(String interestProfileCode) throws Exception {
//           List<AssignedTasksDescriptionBean> assigneTaskDescriptionList = new ArrayList<AssignedTasksDescriptionBean>();
           List<TransactionTypeBean> assgnedList = new ArrayList<TransactionTypeBean>();
           List<TransactionTypeBean> assgnedDescriptionList = new ArrayList<TransactionTypeBean>();
          try {
           
            interestPersis = new InterestProfilePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            assgnedList = interestPersis.getAssignedList(CMSCon,interestProfileCode);
            
              for (int i = 0; i < assgnedList.size(); i++) {
                  TransactionTypeBean bean = new TransactionTypeBean();
                  bean = interestPersis.getTransactionDescriptionByTransactionCode(CMSCon, assgnedList.get(i).getTransactionCode());

                  assgnedDescriptionList.add(bean);
              }
            


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return assgnedDescriptionList;
    }   
     
     
     public boolean updateInterestProfile(InterestprofileBean interest ,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
           interestPersis = new InterestProfilePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = interestPersis.updateInterestProfile(CMSCon, interest);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    } 
     
    
     public boolean updateInterestProfileTransaction(InterestprofileBean interest ,String [] assigned) throws Exception {
        boolean success = false;
        boolean successDelete = false;
        try {
            interestPersis = new InterestProfilePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            successDelete = interestPersis.deleteFromInterestProfileTransaction(CMSCon,interest.getInterestFrofileCode());
            
            for (int i = 0; i < assigned.length; i++) {
               success = interestPersis.insertToInterestProfileTransaction(CMSCon, interest,assigned[i]); 
            }
            
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        
        if(success && successDelete){
            return true;
        }else{
            return false;
        }
        
    }   
      
      
     public boolean deleteInterest(InterestprofileBean interest ,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        List<InterestProfileTransactionBean> interestProfileTransaction = new ArrayList<InterestProfileTransactionBean>();
        try {           
            interestPersis = new InterestProfilePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
              
                
            if (interestPersis.deleteFromInterestProfileTransaction(CMSCon, interest.getInterestFrofileCode())) {//delete all records from INTERESTPROFILETRANSACTION table

                try {
                    success = interestPersis.deleteInterest(CMSCon, interest);
                   CMSCon.commit();
                } catch (SQLException e) {//insert deleted data to INTERESTPROFILETRANSACTION table(rallback)
                   CMSCon.rollback();
                    throw e;
                }
                sysAuditManager.insertAudit(systemAuditBean, CMSCon);
                
            } else {
                success = false;
            }

                
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    } 
     
}
