/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.generalledgermgtmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.AssignTxnBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.ChannelLisnrTypeBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.TxnTypesBean;
import com.epic.cms.backoffice.generalledgermgtmgt.persistance.AssignTxnTypesToGLAccountPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import com.epic.cms.system.util.variable.TaskVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author badrika
 */
public class AssignTxnTypesToGLAccountManager {

    private AssignTxnTypesToGLAccountPersistance perObj;
    private Connection OnlineCon;
    private Connection cmsCon;
    private SystemAuditManager sysAuditManager;

    public List<ChannelLisnrTypeBean> getChanelLisnrTypeList(String chorls) throws SQLException, Exception {

        try {
            List<ChannelLisnrTypeBean> cnlOrLsnrList = null;
            perObj = new AssignTxnTypesToGLAccountPersistance();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            OnlineCon.setAutoCommit(false);

            cnlOrLsnrList = perObj.getChanelLisnrTypeList(OnlineCon, chorls);

            OnlineCon.commit();
            return cnlOrLsnrList;
        } catch (Exception e) {
            try {
                OnlineCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (OnlineCon != null) {
                try {
                    OnlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public List<TxnTypesBean> getTxnTypes(String col, String clType, String crGlAcc, String drGlAcc) throws SQLException, Exception {

        try {
            List<TxnTypesBean> txnTypeList = null;
            perObj = new AssignTxnTypesToGLAccountPersistance();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            OnlineCon.setAutoCommit(false);

            txnTypeList = perObj.getTxnTypes(OnlineCon, col, clType, crGlAcc, drGlAcc);

            OnlineCon.commit();
            return txnTypeList;

        } catch (Exception e) {
            try {
                OnlineCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (OnlineCon != null) {
                try {
                    OnlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public List<TxnTypesBean> getassinedTxnTypes(String col, String clType, String crAccNo, String drAccNo) throws SQLException, Exception {

        try {
            List<TxnTypesBean> txnTypeList = null;
            perObj = new AssignTxnTypesToGLAccountPersistance();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            OnlineCon.setAutoCommit(false);

            txnTypeList = perObj.getassinedTxnTypes(OnlineCon, col, clType, crAccNo, drAccNo);

            OnlineCon.commit();
            return txnTypeList;

        } catch (Exception e) {
            try {
                OnlineCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (OnlineCon != null) {
                try {
                    OnlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public int assignTxns(SystemAuditBean systemAuditBean, AssignTxnBean bean, String[] assigned) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new AssignTxnTypesToGLAccountPersistance();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            cmsCon = DBConnection.getConnection();

            OnlineCon.setAutoCommit(false);
            cmsCon.setAutoCommit(false);

            List<TxnTypesBean> txnTypeList = null;
            txnTypeList = perObj.getassinedTxnTypes(OnlineCon, bean.getChanekOrLisnr(), bean.getChOrLsType(), bean.getGlAccNo(), bean.getDrGlAccNo());

            boolean flag = false;

            for (int i = 0; i < assigned.length; i++) {
                flag = false;

                for (int j = 0; j < txnTypeList.size(); j++) {
                    if (assigned[i].equals(txnTypeList.get(j).getTypeCode().toString())) {
                        flag = true;
                        systemAuditBean.setTaskCode(TaskVarList.UPDATE);
                        break;
                    }
                }
                if (!flag) {
                    rowCount = perObj.assignTxns(OnlineCon, cmsCon, bean, assigned[i]);
                }

            }

            for (int i = 0; i < txnTypeList.size(); i++) {
                flag = false;

                for (int j = 0; j < assigned.length; j++) {
                    if (txnTypeList.get(i).getTypeCode().toString().equals(assigned[j])) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    rowCount = perObj.deleteglAcconut(OnlineCon,cmsCon, bean, txnTypeList.get(i).getTypeCode().toString());
                    systemAuditBean.setTaskCode(TaskVarList.UPDATE);
                }

            }

            if (rowCount > 0) {
                sysAuditManager.insertAudit(systemAuditBean, cmsCon);
                OnlineCon.commit();
                cmsCon.commit();
            }

            return rowCount;

        } catch (Exception e) {
            try {
                OnlineCon.rollback();
                cmsCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (OnlineCon != null) {
                try {
                    OnlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public int deletetxns(SystemAuditBean systemAuditBean, AssignTxnBean bean, String[] assigned) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new AssignTxnTypesToGLAccountPersistance();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            OnlineCon.setAutoCommit(false);

            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            for (int i = 0; i < assigned.length; i++) {
                rowCount = perObj.deleteglAcconut(OnlineCon,cmsCon, bean, assigned[i]);
            }

            //  sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            OnlineCon.commit();
            cmsCon.commit();

            return rowCount;

        } catch (Exception e) {
            try {
                OnlineCon.rollback();
                cmsCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (OnlineCon != null) {
                try {
                    OnlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public List<AssignTxnBean> getOnlineGlAccounts() throws SQLException, Exception {

        try {
            List<AssignTxnBean> allOnlineGLAccList = null;
            perObj = new AssignTxnTypesToGLAccountPersistance();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            OnlineCon.setAutoCommit(false);

            allOnlineGLAccList = perObj.getOnlineGlAccounts(OnlineCon);

            OnlineCon.commit();
            return allOnlineGLAccList;

        } catch (Exception e) {
            try {
                OnlineCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (OnlineCon != null) {
                try {
                    OnlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public AssignTxnBean viewSelected(String id, String accNo, String drAccNo, String col, String clType) throws SQLException, Exception {

        try {
            AssignTxnBean assBean = new AssignTxnBean();
            perObj = new AssignTxnTypesToGLAccountPersistance();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            OnlineCon.setAutoCommit(false);

            assBean = perObj.viewSelected(OnlineCon, id, accNo, drAccNo, col, clType);
            OnlineCon.commit();
            return assBean;

        } catch (Exception e) {
            try {
                OnlineCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (OnlineCon != null) {
                try {
                    OnlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
}
