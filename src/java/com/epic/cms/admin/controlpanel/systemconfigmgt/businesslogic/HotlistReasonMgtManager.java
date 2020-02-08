/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.HotlistReasonBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.HotlistReasonMgtPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author badrika
 */
public class HotlistReasonMgtManager {

    private Connection cmsCon;
    private HotlistReasonMgtPersistance perObj;
    private HotlistReasonBean reasonBean;
    private SystemAuditManager sysAuditManager;

    /**
     * to get hot list reason list
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<HotlistReasonBean> getHotlistReasons() throws SQLException, Exception {

        try {
            List<HotlistReasonBean> reasonList = null;
            perObj = new HotlistReasonMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            reasonList = perObj.getHotlistReasons(cmsCon);

            cmsCon.commit();
            return reasonList;
        } catch (Exception e) {
            try {
                cmsCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * to add a new hot list reason
     * @param systemAuditBean
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int addNewHotlistReason(SystemAuditBean systemAuditBean, HotlistReasonBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new HotlistReasonMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount = perObj.addNewHotlistReason(cmsCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);


            cmsCon.commit();
            return rowCount;
        } catch (Exception e) {
            try {
                cmsCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * to view selected record
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public HotlistReasonBean viewSelectedReason(String id) throws SQLException, Exception {

        try {
            perObj = new HotlistReasonMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            reasonBean = perObj.viewSelectedReason(cmsCon, id);
            cmsCon.commit();
            return reasonBean;
        } catch (Exception e) {
            try {
                cmsCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * to update a record
     * @param systemAuditBean
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int updateReason(SystemAuditBean systemAuditBean, HotlistReasonBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new HotlistReasonMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            //--
            rowCount = perObj.updateReason(cmsCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            cmsCon.commit();
            return rowCount;
        } catch (Exception e) {
            try {
                cmsCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * t delete a record
     * @param id
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteReason(String id, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            perObj = new HotlistReasonMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);            
            rowCount = perObj.deleteReason(cmsCon, id);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            cmsCon.commit();

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return rowCount;
    }
}
