/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.recovery.callcenter.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.callcenter.callcentersearch.bean.QuestionAnswerBean;
import com.epic.cms.recovery.callcenter.bean.RecoveryCardDetailsBean;
import com.epic.cms.recovery.callcenter.bean.RecoveryDetailsBean;
import com.epic.cms.recovery.callcenter.bean.RecoveryPaymentDetailsBean;
import com.epic.cms.recovery.callcenter.bean.RecoveryTransactionDetailsBean;
import com.epic.cms.recovery.callcenter.bean.SearchRecoveryBean;
import com.epic.cms.recovery.callcenter.persistance.RecoveryCallCenterPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class RecoveryCallCenterManager {

    private Connection CMSCon;
    private static RecoveryCallCenterManager setInstance;
    private RecoveryCallCenterPersistance recoveryPersist = null;
    private List<QuestionAnswerBean> answerBeanList = null;

    public static synchronized RecoveryCallCenterManager getInstance() {

        if (setInstance == null) {
            setInstance = new RecoveryCallCenterManager();
        }
        return setInstance;
    }
    private SystemAuditManager sysAuditManager;

    public List<RecoveryDetailsBean> getAllRecoveriesList(SearchRecoveryBean searchBean) throws Exception {

        List<RecoveryDetailsBean> recoveryDetailsBeanList = null;
        try {
            recoveryPersist = new RecoveryCallCenterPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            recoveryDetailsBeanList = recoveryPersist.getAllRecoveriesList(CMSCon, searchBean);
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


        return recoveryDetailsBeanList;
    }

    public List<RecoveryTransactionDetailsBean> getRecoveryTxnDetails(String condition, int start, int end) throws Exception {

        List<RecoveryTransactionDetailsBean> recoveryTxnBeanList = null;
        try {
            recoveryPersist = new RecoveryCallCenterPersistance();
            CMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);

            recoveryTxnBeanList = recoveryPersist.getRecoveryTxnDetails(CMSCon, condition, start, end);
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


        return recoveryTxnBeanList;
    }

    public int getCountTxn(String condition) throws Exception {
        int count;
        try {
            recoveryPersist = new RecoveryCallCenterPersistance();
            CMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            count = recoveryPersist.getCountTxn(CMSCon, condition);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return count;
    }

    public int getCountPayments(String condition) throws Exception {
        int count;
        try {
            recoveryPersist = new RecoveryCallCenterPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            count = recoveryPersist.getCountPayments(CMSCon, condition);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return count;
    }

    public List<RecoveryPaymentDetailsBean> getRecoveryPaymentDetails(String condition, int start, int end) throws Exception {

        List<RecoveryPaymentDetailsBean> recoveryPaymentBeanList = null;
        try {
            recoveryPersist = new RecoveryCallCenterPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            recoveryPaymentBeanList = recoveryPersist.getRecoveryPaymentDetails(CMSCon, condition, start, end);
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


        return recoveryPaymentBeanList;
    }

    public RecoveryCardDetailsBean getRecoveryCardDetailsBean(String collectionId) throws Exception {

        RecoveryCardDetailsBean recoveryDetailsBean = null;
        try {
            recoveryPersist = new RecoveryCallCenterPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            recoveryDetailsBean = recoveryPersist.getRecoveryCardDetailsBean(CMSCon, collectionId);
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

        return recoveryDetailsBean;
    }

    public List<QuestionAnswerBean> getRecoveryQuestionList(String issuAccStatus, String collectionStatus, String cardNumber) throws Exception {

        try {

            recoveryPersist = new RecoveryCallCenterPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            answerBeanList = recoveryPersist.getRecoveryQuestionList(CMSCon, issuAccStatus, collectionStatus, cardNumber);
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

        return answerBeanList;
    }

    public boolean updateCollectionStatus(String status, String collectionId) throws Exception {
        boolean flag = false;
        try {
            recoveryPersist = new RecoveryCallCenterPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = recoveryPersist.updateCollectionStatus(CMSCon, status, collectionId);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return flag;
    }

    public boolean insertCollectionMemo(String callerId, String collectionId, String memo, String user,SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;
        try {
            recoveryPersist = new RecoveryCallCenterPersistance();
            sysAuditManager = new SystemAuditManager();
            
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = recoveryPersist.insertCollectionMemo(CMSCon, callerId, collectionId, memo, user);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return flag;
    }
}
