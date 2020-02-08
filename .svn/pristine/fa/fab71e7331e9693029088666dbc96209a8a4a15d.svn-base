/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeTypeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.persistance.FeeMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class FeeMgtManager {

    private FeeMgtPersistance feePersis = null;
    private List<FeeBean> feeList = null;
    private List<FeeTypeBean> typeList = null;
    private FeeBean feecount = null;
    //private FeeBean feeBean = null;
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;

    public List<FeeBean> getFeeDetails() throws Exception {
        try {
            feeList = new ArrayList<FeeBean>();
            feePersis = new FeeMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            feeList = feePersis.getFeeDetails(CMSCon);


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
        return feeList;
    }

    public boolean feeInsert(FeeBean feeBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;
        try {

            feePersis = new FeeMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = feePersis.feeInsert(feeBean, CMSCon);
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
        return flag;
    }

    public boolean feeUpdate(FeeBean feeBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;
        try {

            feePersis = new FeeMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = feePersis.feeUpdate(feeBean, CMSCon);
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
        return flag;
    }

    public boolean feeDelete(FeeBean feeBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;
        try {

            feePersis = new FeeMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = feePersis.feeDelete(feeBean, CMSCon);
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
        return flag;
    }

    public List<FeeTypeBean> getFeeType() throws Exception {
        try {
            typeList = new ArrayList<FeeTypeBean>();
            feePersis = new FeeMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            typeList = feePersis.getFeeType(CMSCon);


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
        return typeList;
    }

    public int feeCountMgt(FeeBean feecount) throws SQLException, Exception {

        int success;        
        feePersis = new FeeMgtPersistance();

        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            success = feePersis.feeCountMgt(CMSCon,feecount);

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
