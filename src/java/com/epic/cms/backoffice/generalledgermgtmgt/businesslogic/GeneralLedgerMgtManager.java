/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.generalledgermgtmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.GeneralLedgerAccTypeBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.GeneralLedgerMgtBean;
import com.epic.cms.backoffice.generalledgermgtmgt.persistance.GeneralLedgerMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author badrika
 */
public class GeneralLedgerMgtManager {

    private List<StatusBean> statusList;
    private GeneralLedgerMgtPersistance perObj;
    private Connection cmsCon;
    //private List<GeneralLedgerMgtBean> glAccountList;
    private SystemAuditManager sysAuditManager;
    private GeneralLedgerMgtBean accontBean;
    private GeneralLedgerAccTypeBean accTypeBean;

    public List<StatusBean> getStatusList() throws SQLException, Exception {
        statusList = new ArrayList<StatusBean>();
        try {

            perObj = new GeneralLedgerMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            statusList = perObj.getStatustList(cmsCon);

            cmsCon.commit();
            return statusList;
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

    //get glaccount type list--chinthaka

    public List<GeneralLedgerAccTypeBean> getGlAccTypeList() throws SQLException, Exception {
        try {
            List<GeneralLedgerAccTypeBean> glAccTypeList = null;
            perObj = new GeneralLedgerMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            glAccTypeList = perObj.getAccTypeList(cmsCon);
            cmsCon.commit();

            return glAccTypeList;
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
    //end
    public List<GeneralLedgerMgtBean> getAllGlAccounts() throws SQLException, Exception {

        try {
            List<GeneralLedgerMgtBean> glAccountList = null;
            perObj = new GeneralLedgerMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            glAccountList = perObj.getAllGlAccounts(cmsCon);

            cmsCon.commit();
            return glAccountList;
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

    public int addNewglAccont(SystemAuditBean systemAuditBean, GeneralLedgerMgtBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new GeneralLedgerMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount = perObj.addNewglAccont(cmsCon, bean);
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

    public GeneralLedgerMgtBean viewSelectedAccont(String id) throws SQLException, Exception {

        try {
            perObj = new GeneralLedgerMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            accontBean = perObj.viewSelectedAccont(cmsCon, id);
            cmsCon.commit();
            return accontBean;
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

    public int updateglAccont(SystemAuditBean systemAuditBean, GeneralLedgerMgtBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new GeneralLedgerMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            //--
            rowCount = perObj.updateglAccont(cmsCon, bean);
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

    public int deleteglAcconut(String id, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            perObj = new GeneralLedgerMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            rowCount = perObj.deleteglAcconut(cmsCon, id);
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
