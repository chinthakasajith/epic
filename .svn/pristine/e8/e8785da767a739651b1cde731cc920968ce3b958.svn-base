/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.recovery.callcenter.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.recovery.callcenter.bean.CollectionBean;
import com.epic.cms.recovery.callcenter.persistance.CollectionAssignmentPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nalin
 */
public class CollectionAssignmentManager {

    private Connection CMSCon = null;
    private HashMap<String, String> queueList = null;
    private CollectionAssignmentPersistance collectionPersis = null;
    private HashMap<String, String> userList = null;
    private List<CollectionBean> collectionList = null;
    private SystemAuditManager sysAuditManager = null;

    public HashMap<String, String> getQueueList() throws Exception {
        try {

            queueList = new HashMap<String, String>();
            collectionPersis = new CollectionAssignmentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            queueList = collectionPersis.getQueueList(CMSCon);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return queueList;
    }

    public List<CollectionBean> getSelectedCollectionList(String queueId) throws Exception {
        try {

            collectionList = new ArrayList<CollectionBean>();
            collectionPersis = new CollectionAssignmentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            collectionList = collectionPersis.getSelectedCollectionList(CMSCon, queueId);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return collectionList;

    }

    public HashMap<String, String> getAssignUserList(String queueId) throws Exception {
        try {

            userList = new HashMap<String, String>();
            collectionPersis = new CollectionAssignmentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            userList = collectionPersis.getAssignUserList(CMSCon, queueId);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return userList;
    }

    public boolean assignCollection(String[] assignList, String assignUser, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            collectionPersis = new CollectionAssignmentPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            success = collectionPersis.assignCollection(CMSCon, assignList, assignUser);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }
}
