package com.epic.cms.switchcontrol.keymgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.switchcontrol.keymgt.bean.ListnerKeyMailBean;
import com.epic.cms.switchcontrol.keymgt.persistance.ListnerKeyMailingPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class ListnerKeyMailingManager {

    private Connection cmsConOnline;
    private Connection cmsCon;
    private SystemAuditManager sysAuditManager;
    private ListnerKeyMailingPersistance listnerKeyPersistanc;
    private List<ListnerKeyMailBean> searchList = null;

    public List<ListnerKeyMailBean> getAllListnetTypes() throws SQLException, Exception {
        try {

            List<ListnerKeyMailBean> listnerTypeList = null;
            listnerKeyPersistanc = new ListnerKeyMailingPersistance();
            cmsConOnline = DBConnectionToOnlineDB.getConnection();
            cmsConOnline.setAutoCommit(false);

            listnerTypeList = listnerKeyPersistanc.getAllListnetTypes(cmsConOnline);
            cmsConOnline.commit();

            return listnerTypeList;

        } catch (SQLException sqlex) {
            try {
                cmsConOnline.rollback();
                throw sqlex;
            } catch (SQLException sqlex2) {
                throw sqlex2;
            }
        } catch (Exception ex) {
            try {
                cmsConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsConOnline != null) {
                try {
                    cmsConOnline.close();
                } catch (SQLException sqlex) {
                    throw sqlex;
                }
            }
        }
    }

    public List<ListnerKeyMailBean> getAllCommunicationKeys() throws SQLException, Exception {
        try {

            List<ListnerKeyMailBean> comKeys = null;
            listnerKeyPersistanc = new ListnerKeyMailingPersistance();
            cmsConOnline = DBConnectionToOnlineDB.getConnection();
            cmsConOnline.setAutoCommit(false);

            comKeys = listnerKeyPersistanc.getAllCommunicationKeys(cmsConOnline);
            cmsConOnline.commit();

            return comKeys;

        } catch (SQLException sqlex) {
            try {
                cmsConOnline.rollback();
                throw sqlex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsConOnline != null) {
                try {
                    cmsConOnline.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }

    public List<ListnerKeyMailBean> searchKeyMailing(ListnerKeyMailBean inputBean) throws SQLException, Exception {
        try {
            listnerKeyPersistanc = new ListnerKeyMailingPersistance();

            cmsConOnline = DBConnectionToOnlineDB.getConnection();
            cmsConOnline.setAutoCommit(false);

            searchList = listnerKeyPersistanc.searchKeyMailing(cmsConOnline, inputBean);
            cmsConOnline.commit();

        } catch (SQLException sqlex) {
            try {
                cmsConOnline.rollback();
                throw sqlex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsConOnline != null) {
                try {
                    cmsConOnline.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return searchList;
    }

    public boolean updateZMKKey(SystemAuditBean systemAuditBean, ListnerKeyMailBean keyBean) throws SQLException, Exception {
        boolean isSuccess = false;

        try {
            sysAuditManager = new SystemAuditManager();
            listnerKeyPersistanc = new ListnerKeyMailingPersistance();

            cmsConOnline = DBConnectionToOnlineDB.getConnection();
            cmsConOnline.setAutoCommit(false);
            
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            isSuccess = listnerKeyPersistanc.updateZMKKey(cmsConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);

            cmsConOnline.commit();

        } catch (SQLException sqlex) {
            try {
                cmsConOnline.rollback();
                throw sqlex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsConOnline != null) {
                try {
                    cmsConOnline.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return isSuccess;
    }

    public String getListernerName() throws SQLException, Exception {
        String listerNaerName;

        try {

            listnerKeyPersistanc = new ListnerKeyMailingPersistance();

            cmsConOnline = DBConnectionToOnlineDB.getConnection();
            cmsConOnline.setAutoCommit(false);

            cmsConOnline.setAutoCommit(false);
            listerNaerName = listnerKeyPersistanc.getListernerName(cmsConOnline);

            return listerNaerName;
            
        } catch (SQLException sqlex) {
            try {
                cmsConOnline.rollback();
                throw sqlex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsConOnline != null) {
                try {
                    cmsConOnline.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }
}
