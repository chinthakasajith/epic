/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ChannelIncomeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.OnlineTxnTypeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.persistance.AcquireTxnTypePersistance;
import com.epic.cms.admin.controlpanel.transactionmgt.persistance.TxnTypeMgtPersistance;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class AcquireTxnTypeManager {

    private Connection CMSCon;
    private Connection CMSOnline;
    private TxnTypeMgtPersistance txnTypePer;
    private SystemAuditManager sysAuditManager;
    private List<OnlineTxnTypeBean> onlineTxnTypeList = null;
    //-------------------------------------------------
    ChannelIncomeBean txn;
    List<ChannelIncomeBean> txnList;
    AcquireTxnTypePersistance axquirerPer;

    public List<OnlineTxnTypeBean> getOnlineTxnType() throws Exception {

        try {
            onlineTxnTypeList = new ArrayList<OnlineTxnTypeBean>();
            txnTypePer = new TxnTypeMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSOnline.setAutoCommit(false);
            onlineTxnTypeList = txnTypePer.getOnlineTxnType(CMSOnline);

            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return onlineTxnTypeList;
    }

    public Boolean insertAcquirerTxnTypes(ChannelIncomeBean process, SystemAuditBean systemAuditBean) throws SQLException, Exception {

        try {

            //if the insert become success row will return 1
            int success1 = -1;
            Boolean success2, success = false;
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            axquirerPer = new AcquireTxnTypePersistance();
            txnTypePer = new TxnTypeMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            String description = txnTypePer.getTxnDescriptionByCode(CMSOnline, process.getTransactionTypeCode());
            process.setTxnDescription(description);

            success2 = axquirerPer.insertAcquirerTxnTypes(process, CMSOnline);
            success1 = sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

            if (success2 && success1 == 1) {
                success = true;
            }
            return success;

        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (CMSOnline != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public int updateAcquirerTxnTypes(ChannelIncomeBean process, SystemAuditBean systemAuditBean) throws SQLException, Exception {

        int row1, row2, row = -1;
        try {

            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            axquirerPer = new AcquireTxnTypePersistance();
            txnTypePer = new TxnTypeMgtPersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            String description = txnTypePer.getTxnDescriptionByCode(CMSOnline, process.getTransactionTypeCode());
            process.setTxnDescription(description);

            row1 = axquirerPer.updateAcquirerTxnTypes(process, CMSOnline);
            row2 = sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (CMSOnline != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        if (row1 == 1 || row2 == 1) {
            row = 1;
        }
        return row;
    }

    public int deleteAcquirerTxnTypes(ChannelIncomeBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {

        int row1, row2, row = -1;
        try {
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            axquirerPer = new AcquireTxnTypePersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            row1 = axquirerPer.deleteAcquirerTxnTypes(CMSOnline, bean);
            row2 = sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (CMSOnline != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        if (row1 == 1 || row2 == 1) {
            row = 1;
        }
        return row;
    }

    public List<ChannelIncomeBean> getAllAcquirerTxnTypeData() throws SQLException, Exception {
        try {

            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            axquirerPer = new AcquireTxnTypePersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);
            //assign the merchant details to the erchant bean instance
            txnList = axquirerPer.getAllAcquirerTxnTypeData(CMSOnline);

            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (CMSOnline != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return txnList;
    }

    public List<StatusBean> getStatus() throws Exception {
        List<StatusBean> statusList;
        try {
            axquirerPer = new AcquireTxnTypePersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSOnline.setAutoCommit(false);

            statusList = axquirerPer.getStatus(CMSOnline);

            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {

                CMSOnline.rollback();
                throw ex;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSOnline);
        }
        return statusList;
    }
}
