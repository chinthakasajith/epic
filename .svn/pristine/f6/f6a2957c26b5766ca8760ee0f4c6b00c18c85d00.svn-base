/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.keymgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardMainCustomerDetailsBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardSuplimentoryCustomerBean;
import com.epic.cms.camm.applicationconfirmation.bean.CardBean;
import com.epic.cms.cpmm.pingenarationandmail.been.CardKeyBean;
import com.epic.cms.switchcontrol.cardkeys.bean.CardKeyProfilebean;
import com.epic.cms.switchcontrol.communicationkeys.bean.KeyBean;
import com.epic.cms.switchcontrol.keymgt.bean.ChanelKeysBean;
import com.epic.cms.switchcontrol.keymgt.bean.ChannelKeyMailingBean;
import com.epic.cms.switchcontrol.keymgt.bean.OnlinekeysBean;
import com.epic.cms.switchcontrol.keymgt.persistance.KeyManagementPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class KeyManagementManager {

    private Connection conToOnline;
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private KeyManagementPersistance keyMgtPer;

    public boolean addZMK(String zmkKey, String zmkkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.addZMKValues(conToOnline, zmkKey, zmkkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean addimk_ac(String imk_ac, String imk_ackvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.addimk_acValues(conToOnline, imk_ac, imk_ackvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean addzcmk(String zcmk, String zcmkkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.addzcmkValues(conToOnline, zcmk, zcmkkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean addppk(String ppk, String ppkkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.addppkValues(conToOnline, ppk, ppkkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean addVISACVVA(String cvva, String cvvakvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.addVISACVVA(conToOnline, cvva, cvvakvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean addVISAPVVA(String pvva, String pvvakvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.addVISAPVVA(conToOnline, pvva, pvvakvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean addVISAPVVB(String pvvb, String pvvbkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.addVISAPVVB(conToOnline, pvvb, pvvbkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);


            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean addTPK(String tpk, String tpkkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.addTPK(conToOnline, tpk, tpkkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean addVISACVVB(String cvvb, String cvvbkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.addVISACVVB(conToOnline, cvvb, cvvbkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean addAWK(String awkKey, String awkkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.addAWKValues(conToOnline, awkKey, awkkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean addIWK(String iwkKey, String iwkkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.addIWKValues(conToOnline, iwkKey, iwkkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean addCSKA(String cskaKey, String cskakvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.addCSKAValues(conToOnline, cskaKey, cskakvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean addCSKB(String cskbKey, String cskbkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.addCSKBValues(conToOnline, cskbKey, cskbkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean updateZMK(String zmkKey, String zmkkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.updateZMKValues(conToOnline, zmkKey, zmkkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean updateimk_ac(String imk_ac, String imk_ackvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.updateimk_ac(conToOnline, imk_ac, imk_ackvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean updatezcmk(String zcmk, String zcmkkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.updatezcmk(conToOnline, zcmk, zcmkkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean updateppk(String ppk, String ppkkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.updateppk(conToOnline, ppk, ppkkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean updateVISACVVA(String cvva, String cvvakvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.updateVISACVVAalues(conToOnline, cvva, cvvakvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean updateVISAPVVA(String pvva, String pvvakvc, String channelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.updateVISAPVVAalues(conToOnline, pvva, pvvakvc, channelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean updateVISAPVVB(String pvvb, String pvvbkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.updateVISAPVVBalues(conToOnline, pvvb, pvvbkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    //-----------------------------------------------------------------------------------------------------------------------
    public boolean addPVV(String pvv, String pvvbkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            keyMgtPer = new KeyManagementPersistance();

            CMSCon = DBConnection.getConnection();
            conToOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            conToOnline.setAutoCommit(false);

            status = keyMgtPer.addPVVValues(conToOnline, pvv, pvvbkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean updatePVV(String pvv, String pvvbkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            keyMgtPer = new KeyManagementPersistance();

            CMSCon = DBConnection.getConnection();
            conToOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            conToOnline.setAutoCommit(false);

            status = keyMgtPer.updatePVVValues(conToOnline, pvv, pvvbkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }
    //----------------------------------------------------------------------------------------------------------------------

    public boolean updateTPK(String tpk, String tpkkvc, String keyId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.updateTPKalues(conToOnline, tpk, tpkkvc, keyId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean updateVISACVVB(String cvvb, String cvvbkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.updateVISACVVBalues(conToOnline, cvvb, cvvbkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean updateAWK(String zmkKey, String zmkkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.updateAWKalues(conToOnline, zmkKey, zmkkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean updateIWK(String iwkKey, String iwkkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.updateIWKalues(conToOnline, iwkKey, iwkkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean updateCSKA(String cskaKey, String cskakvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.updateCSKAValues(conToOnline, cskaKey, cskakvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public boolean updateCSKB(String cskbKey, String cskbkvc, String chanelId, SystemAuditBean systemAuditBean) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            status = keyMgtPer.updateCSKBValues(conToOnline, cskbKey, cskbkvc, chanelId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            conToOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public ChanelKeysBean getChanelDetails(String chanelId) throws Exception {
        ChanelKeysBean keyDetails = new ChanelKeysBean();
        try {
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            keyDetails = keyMgtPer.getChanelDetails(conToOnline, chanelId);


            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
        }
        return keyDetails;
    }

    public CardMainCustomerDetailsBean getCardMainCusDetailbean(String customerId) throws Exception {
        CardMainCustomerDetailsBean cardMain = new CardMainCustomerDetailsBean();
        try {
            keyMgtPer = new KeyManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardMain = keyMgtPer.getCardMainCusDetailbean(CMSCon, customerId);


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
        return cardMain;
    }

    public CardSuplimentoryCustomerBean getSuppCusDetails(String customerId) throws Exception {
        CardSuplimentoryCustomerBean cardSupp = new CardSuplimentoryCustomerBean();
        try {
            keyMgtPer = new KeyManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardSupp = keyMgtPer.getSuppCusDetails(CMSCon, customerId);


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
        return cardSupp;
    }

    public String getChanelId(String chanelType) throws Exception {
        String channelType = null;
        try {
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            channelType = keyMgtPer.getChanelId(conToOnline, chanelType);


            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
        }
        return channelType;
    }

    public String getPinMethod() throws Exception {
        String pinMethod = null;
        try {
            keyMgtPer = new KeyManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            pinMethod = keyMgtPer.getPinMethod(CMSCon);


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
        return pinMethod;
    }

    public String getPVI() throws Exception {
        String PVI = null;
        try {
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            PVI = keyMgtPer.getPVI(conToOnline);


            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
        }
        return PVI;
    }

    public String getCardCategory(String cardnumber) throws Exception {
        String cardCategory = null;
        try {
            keyMgtPer = new KeyManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardCategory = keyMgtPer.getCardCategory(CMSCon, cardnumber);


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
        return cardCategory;
    }

    public String getCustomerId(String cardnumber) throws Exception {
        String customerId = null;
        try {
            keyMgtPer = new KeyManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            customerId = keyMgtPer.getCustomerId(CMSCon, cardnumber);


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
        return customerId;
    }

    public CardBean getCardDetails(String cardnumber) throws Exception {
        String customerId = null;
        CardBean card = new CardBean();
        try {
            keyMgtPer = new KeyManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            card = keyMgtPer.getCardDetails(CMSCon, cardnumber);


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
        return card;
    }

    public String getCardKeyId(String binnumber) throws Exception {
        String cardKeyId = null;
        try {
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            cardKeyId = keyMgtPer.getCardKeyId(conToOnline, binnumber);


            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(conToOnline);
        }
        return cardKeyId;
    }

    public CardKeyBean getCardkeyDetails(String keyId) throws Exception {
        CardKeyBean cardkeydetails = new CardKeyBean();
        try {
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            cardkeydetails = keyMgtPer.getCardkeyDetails(conToOnline, keyId);


            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(conToOnline);
        }
        return cardkeydetails;
    }

    public CardKeyProfilebean getCardkeyprofileBean(String profileId) throws Exception {
        CardKeyProfilebean keyProfile = new CardKeyProfilebean();
        try {
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            keyProfile = keyMgtPer.getCardkeyprofileBean(conToOnline, profileId);


            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(conToOnline);
        }
        return keyProfile;
    }

    public OnlinekeysBean getOnlineChanelDetails(String keyId) throws Exception {
        OnlinekeysBean onlineKeys = new OnlinekeysBean();
        try {
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            onlineKeys = keyMgtPer.getOnlineKeyDetails(conToOnline, keyId);


            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
        }
        return onlineKeys;
    }

    public boolean isChanelAvailable(String chanelId) throws Exception {
        boolean statust;
        try {
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            statust = keyMgtPer.isChanelAvailable(conToOnline, chanelId);


            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
        }
        return statust;
    }

    public boolean isOnlineKeyAvailable(String keyId) throws Exception {
        boolean statust;
        try {
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            statust = keyMgtPer.isKeyIdAvailable(conToOnline, keyId);


            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
        }
        return statust;
    }

    public HashMap<String, String> getChannelTypes() throws SQLException, Exception {
        HashMap<String, String> channelTypes =new HashMap<String, String>();
        try {
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            channelTypes = keyMgtPer.getChannelTypes(conToOnline);
            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
        }
        return channelTypes;
    }

    public HashMap<String, String> getCommunicationKeys() throws SQLException, Exception {
         HashMap<String, String> communicationKeys =new HashMap<String, String>();
        try {
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            communicationKeys = keyMgtPer.getCommunicationKeys(conToOnline);
            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
        }
        return communicationKeys;
    }

    public List<ChannelKeyMailingBean> searchChannelKeyMailing(ChannelKeyMailingBean channelKeyMailingBean) throws SQLException, Exception {
        List<ChannelKeyMailingBean> searchResultList=new ArrayList<ChannelKeyMailingBean>();
        try {
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            searchResultList = keyMgtPer.searchChannelKeyMailing(conToOnline, channelKeyMailingBean);
            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } catch (Exception ex) {
            conToOnline.rollback();
            throw ex;

        }finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
        }
        return searchResultList;     
    }

    public ChannelKeyMailingBean getChannelKeyMailingData(String communicationKey) throws SQLException, Exception {
        ChannelKeyMailingBean channelKeyMailingBean = new ChannelKeyMailingBean();
        try {
            keyMgtPer = new KeyManagementPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            channelKeyMailingBean = keyMgtPer.getChannelKeyMailingData(conToOnline, communicationKey);
            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } catch (Exception ex) {
            conToOnline.rollback();
            throw ex;

        }finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
        }
        return channelKeyMailingBean;
    }

    public boolean updateZMKKey(KeyBean keyBean,SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean isSuccess = false;
         try {
            keyMgtPer = new KeyManagementPersistance();
            CMSCon = DBConnection.getConnection();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);
            isSuccess = keyMgtPer.updateZMKKey(conToOnline, keyBean);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            
            conToOnline.commit();
            CMSCon.commit();
            
        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return isSuccess; 
    }
    
    
}
