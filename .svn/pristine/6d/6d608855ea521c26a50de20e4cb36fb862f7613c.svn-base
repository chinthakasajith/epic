/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CountryMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.PostalCodeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.persistance.PostalCodesPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class PostalCodeManager {

     private Connection CMSCon;
     private SystemAuditManager sysAuditManager;
     private List<PostalCodeBean> postalCodeDetailsList;
     private List<CountryMgtBean> countryCodeList;
     private PostalCodesPersistance postalCodePer;
       
     public List<PostalCodeBean>getPostalCodeDetails() throws Exception {
        try {
            postalCodeDetailsList = new ArrayList<PostalCodeBean>();
            postalCodePer = new PostalCodesPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            postalCodeDetailsList = postalCodePer.getPostalCodeDetails(CMSCon);


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
        return postalCodeDetailsList;
    }
     
     
     public List<CountryMgtBean> getCountryCodeList() throws Exception {
        try {
            countryCodeList = new ArrayList<CountryMgtBean>();
            postalCodePer = new PostalCodesPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            countryCodeList = postalCodePer.getCountryCodeList(CMSCon);


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
        return countryCodeList;
    }  
     
     
        public boolean deletePostal(PostalCodeBean postal,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            postalCodePer = new PostalCodesPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = postalCodePer.deletePostal(CMSCon, postal);
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
    
         
    public boolean inserPostalCode(PostalCodeBean postal,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
             sysAuditManager = new SystemAuditManager();
            postalCodePer = new PostalCodesPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = postalCodePer.insertPostalCode(CMSCon, postal);
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
     
    
     public boolean updatePostal(PostalCodeBean postal ,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
           postalCodePer = new PostalCodesPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = postalCodePer.updatePostal(CMSCon, postal);
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
       
     
}
