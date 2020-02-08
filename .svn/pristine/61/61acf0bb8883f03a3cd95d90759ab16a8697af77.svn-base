/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.businesslogic;

import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionProfileTransactionBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionTypeBean;
import com.epic.cms.admin.controlpanel.profilemgt.persistance.TransactionProfilePersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author badrika
 */
public class TransactionProfileManager {

    private TransactionProfilePersistance perObj;
    private Connection cmsCon;
    private List<StatusBean> statusList = null;
    private List<TransactionTypeBean> transactionList;
    private SystemAuditManager sysAuditManager;
    private List<TransactionProfileTransactionBean> transList, notassignedTranList;

    public List<TransactionProfileBean> getTransactionProfileDetails() throws SQLException, Exception {

        try {
            List<TransactionProfileBean> tranProfileList = null;
            perObj = new TransactionProfilePersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            tranProfileList = perObj.getTransactionProfileDetails(cmsCon);

            cmsCon.commit();
            return tranProfileList;
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
     * to take status list(active and deactive)
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<StatusBean> getStatusList() throws SQLException, Exception {
        statusList = new ArrayList<StatusBean>();
        try {

            perObj = new TransactionProfilePersistance();
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

    public List<TransactionTypeBean> getTransactionTypes() throws SQLException, Exception {
        transactionList = new ArrayList<TransactionTypeBean>();
        try {

            perObj = new TransactionProfilePersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            transactionList = perObj.getTransactionTypes(cmsCon);

            cmsCon.commit();
            return transactionList;
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

    // getTransactionsByProfileCode
    public List<TransactionProfileTransactionBean> getTransactionsByProfileCode(String id) throws SQLException, Exception {
        transList = new ArrayList<TransactionProfileTransactionBean>();

        try {

            perObj = new TransactionProfilePersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            transList = perObj.getTransactionsByProfileCode(cmsCon, id);


            cmsCon.commit();
            return transList;
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

    //getNotAssignedTrans
    public List<TransactionProfileTransactionBean> getNotAssignedTrans(String id) throws SQLException, Exception {
        notassignedTranList = new ArrayList<TransactionProfileTransactionBean>();
        try {

            perObj = new TransactionProfilePersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            notassignedTranList = perObj.getNotAssignedTrans(cmsCon, id);

            cmsCon.commit();
            return notassignedTranList;
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

    public int addProfile(SystemAuditBean systemAuditBean, TransactionProfileBean bean, String[] assigned) throws SQLException, Exception {

        int rowCount1 = -1;
        int rowCount2 = -1;
        int success = -1;

        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new TransactionProfilePersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount1 = perObj.addProfile(cmsCon, bean);

            for (int i = 0; i < assigned.length; i++) {
                rowCount2 = perObj.addTransactions(cmsCon, bean.getProfileCode(), assigned[i]);
                // cmsCon.commit();

            }

            sysAuditManager.insertAudit(systemAuditBean, cmsCon);

            if (rowCount1 > 0 && rowCount2 > 0) {
                success = 1;
                cmsCon.commit();
            } else {
                cmsCon.rollback();
            }

            return success;

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

    public int updateProfile(SystemAuditBean systemAuditBean, TransactionProfileBean bean, String[] assigned) throws SQLException, Exception {

        int rowCount1 = -1;
        boolean successdel = false;
        int rowCount2 = -1;
        int success = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new TransactionProfilePersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount1 = perObj.updateProfile(cmsCon, bean);
            successdel = perObj.deleteTrans(cmsCon, bean.getProfileCode());
            for (int i = 0; i < assigned.length; i++) {
                rowCount2 = perObj.addTransactions(cmsCon, bean.getProfileCode(), assigned[i]);
                //cmsCon.commit();

            }

            sysAuditManager.insertAudit(systemAuditBean, cmsCon);

            if (rowCount1 > 0 && rowCount2 > 0 && successdel) {
                success = 1;
                cmsCon.commit();
            } else {
                cmsCon.rollback();
            }
            return success;
            
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

    public int addTransactions(String profileCode, String[] assigned) throws SQLException, Exception {

        int rowCount = -1;
        try {

            perObj = new TransactionProfilePersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);


            for (int i = 0; i < assigned.length; i++) {
                rowCount = perObj.addTransactions(cmsCon, profileCode, assigned[i]);
                cmsCon.commit();

            }

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

    public boolean deleteProfile(String id, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        //   List<TransactionProfileTransactionBean> transList = new ArrayList<TransactionProfileTransactionBean>();
        try {
            perObj = new TransactionProfilePersistance();
            sysAuditManager = new SystemAuditManager();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);




            if (perObj.deleteTrans(cmsCon, id)) {//delete all records from TRANSACTIONPROFILETRANSACTION table

                try {
                    success = perObj.deleteProfile(cmsCon, id);
                    success = true;
                } catch (SQLException e) {
                    throw e;
                }
                sysAuditManager.insertAudit(systemAuditBean, cmsCon);

            } else {
                success = false;
            }

            cmsCon.commit();
        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                cmsCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(cmsCon);
        }
        return success;
    }

    public boolean deleteTrans(String id) throws SQLException, Exception {

        boolean success = false;
        try {

            perObj = new TransactionProfilePersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            success = perObj.deleteTrans(cmsCon, id);
            cmsCon.commit();

            return success;
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
    //  getTransDesbyTxnCode()
//    public String getTransDesbyTxnCode(String id) throws SQLException, Exception {
//
//        String des; 
//        try {
//
//            perObj = new TransactionProfilePersistance();
//            cmsCon = DBConnection.getConnection();
//            cmsCon.setAutoCommit(false);
//
//            des = perObj.getTransDesbyTxnCode(cmsCon, id);
//            cmsCon.commit();
//
//            return des;
//        } catch (Exception e) {
//            try {
//                cmsCon.rollback();
//                throw e;
//            } catch (SQLException sqx) {
//                throw sqx;
//            }
//        } finally {
//            if (cmsCon != null) {
//                try {
//                    cmsCon.close();
//                } catch (SQLException e) {
//                    throw e;
//                }
//            }
//        }
//    }
}
