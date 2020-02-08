/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.communicationkeys.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.switchcontrol.communicationkeys.bean.KeyBean;
import com.epic.cms.switchcontrol.communicationkeys.bean.LcsStatusBean;
import com.epic.cms.switchcontrol.communicationkeys.persistance.KeyPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asela
 */
public class KeyManager {

    private Connection CMSConOnline;
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private KeyBean keyBean;
    private List<KeyBean> keybeanList;
    private List<LcsStatusBean> lcsStatusBeans;
    private KeyPersistance keyPersistance;

    public List<KeyBean> getKeyBeanList() throws Exception {
        try {
            keybeanList = new ArrayList<KeyBean>();
            keyPersistance = new KeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            keybeanList = keyPersistance.getKeyBeanList(CMSConOnline);

            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return keybeanList;
    }

    public boolean insertCommunicationKeyDetails(KeyBean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            keyPersistance = new KeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = keyPersistance.insertCommunicationKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return success;
    }

    public boolean updateCommunicationKeyDetails(KeyBean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            keyPersistance = new KeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = keyPersistance.updateCommunicationKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return success;
    }
    
    public boolean updateZMKKeyDetails(KeyBean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            keyPersistance = new KeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = keyPersistance.updateZMKKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return success;
    }

    public boolean updateAWKKeyDetails(KeyBean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            keyPersistance = new KeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = keyPersistance.updateAWKKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return success;
    }

    public boolean updateIWKKeyDetails(KeyBean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            keyPersistance = new KeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = keyPersistance.updateIWKKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return success;
    }

    public List<LcsStatusBean> getLcsStatusBeanList() throws Exception {
        try {
            keybeanList = new ArrayList<KeyBean>();
            keyPersistance = new KeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            lcsStatusBeans = keyPersistance.getLcsStatusBeanList(CMSConOnline);

            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return lcsStatusBeans;
    }

    public int deleteCommunicationKeyDetails(String keyId, SystemAuditBean systemAuditBean) throws Exception {
        int num = -1;
        try {
            keyPersistance = new KeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            
            num = keyPersistance.deleteCommunicationKeyDetails(CMSConOnline, keyId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);            

            CMSCon.commit();
            CMSConOnline.commit();
            return num;
        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            } finally {
                DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }


        }
    }
}
