/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.txnadjustment.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.backoffice.txnadjustment.bean.TransactionAdjustment;
import com.epic.cms.backoffice.txnadjustment.bean.TransactionAdjustmentParty;
import com.epic.cms.backoffice.txnadjustment.persistance.TxnAdjustmentPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ruwan_e
 */
public class TransactionAdjustmentManager {

    private TxnAdjustmentPersistance txnAdjustmentPersistance;
    private HashMap<String, String> txTypeMap;
    private Connection cmsCon;
    private TransactionAdjustment transactionAdjustment;
    private ArrayList<TransactionAdjustment> taList;
    private SystemAuditManager sysAuditManager;

    public Map<String, String> getTxTypeMap() throws Exception {
        try {

            txnAdjustmentPersistance = new TxnAdjustmentPersistance();
            txTypeMap = new HashMap<String, String>();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            txTypeMap = txnAdjustmentPersistance.getTxTypeMap(cmsCon);
            cmsCon.commit();

        } catch (Exception ex) {
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
        return txTypeMap;

    }

    public Map<String, String> getTransactionAdustmentStatusMap() throws Exception {
        try {

            txnAdjustmentPersistance = new TxnAdjustmentPersistance();
            txTypeMap = new HashMap<String, String>();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            txTypeMap = txnAdjustmentPersistance.getTransactionAdustmentStatusMap(cmsCon);
            cmsCon.commit();

        } catch (Exception ex) {
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
        return txTypeMap;

    }

    public Map<String, String> getFeeTypeMap() throws Exception {
        try {

            txnAdjustmentPersistance = new TxnAdjustmentPersistance();
            txTypeMap = new HashMap<String, String>();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            txTypeMap = txnAdjustmentPersistance.getFeeTypeMap(cmsCon);
            cmsCon.commit();

        } catch (Exception ex) {
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
        return txTypeMap;
    }

    public void addTransactionAdjustment(TransactionAdjustment adjustment, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {
            
            txnAdjustmentPersistance = new TxnAdjustmentPersistance();
            sysAuditManager = new SystemAuditManager();
            
            txTypeMap = new HashMap<String, String>();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            txnAdjustmentPersistance.addTransactionAdjustment(adjustment, cmsCon);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            
            cmsCon.commit();

        } catch (Exception ex) {
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

    }

    public List<TransactionAdjustment> getTransactionAdjustmentsByParty(TransactionAdjustmentParty party) throws Exception {
        try {

            txnAdjustmentPersistance = new TxnAdjustmentPersistance();
            taList = new ArrayList<TransactionAdjustment>();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            taList = (ArrayList<TransactionAdjustment>) txnAdjustmentPersistance.getTransactionAdjustmentsByParty(party, cmsCon);
            cmsCon.commit();

        } catch (Exception ex) {
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
        return taList;
    }

    public TransactionAdjustment getTransactionAdjustmentById(Long txId) throws Exception {
        try {

            txnAdjustmentPersistance = new TxnAdjustmentPersistance();

            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            transactionAdjustment = txnAdjustmentPersistance.getTransactionAdjustmentById(txId, cmsCon);
            cmsCon.commit();

        } catch (Exception ex) {
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
        return transactionAdjustment;
    }

    public List<TransactionAdjustment> getAllTransactionAdjustments() throws SQLException, Exception {
        try {

            txnAdjustmentPersistance = new TxnAdjustmentPersistance();
            taList = new ArrayList<TransactionAdjustment>();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            taList = txnAdjustmentPersistance.getAllTransactionAdjustments(cmsCon);
            cmsCon.commit();

        } catch (Exception ex) {
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
        return taList;
    }

    public void updateTransactionAdjustment(TransactionAdjustment adjustment) throws Exception {
        try {

            txnAdjustmentPersistance = new TxnAdjustmentPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            txnAdjustmentPersistance.updateTransactionAdjustment(adjustment, cmsCon);
            cmsCon.commit();

        } catch (Exception ex) {
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
    }

    public boolean isCardNumberExist(String cardNumber) throws Exception {
        try {

            txnAdjustmentPersistance = new TxnAdjustmentPersistance();
            taList = new ArrayList<TransactionAdjustment>();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            boolean cardNumberExist = txnAdjustmentPersistance.isCardNumberExist(cardNumber, cmsCon);
            cmsCon.commit();
            return cardNumberExist;
        } catch (Exception ex) {
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
            return false;
        }
        
    }

    public boolean isMerchantExist(String uniqueId) throws SQLException, Exception {
        boolean merchantExist = false;
        
        try {

            txnAdjustmentPersistance = new TxnAdjustmentPersistance();
            taList = new ArrayList<TransactionAdjustment>();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            merchantExist = txnAdjustmentPersistance.isMerchantExist(uniqueId, cmsCon);
            cmsCon.commit();
            
        } catch (Exception ex) {
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
        return merchantExist;
    }

    public boolean isTerminalExist(String uniqueId, String verificationValue) throws SQLException, Exception {
        boolean terminalExist = false;
         try {
            txnAdjustmentPersistance = new TxnAdjustmentPersistance();
            taList = new ArrayList<TransactionAdjustment>();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            terminalExist = txnAdjustmentPersistance.isTerminalExist(uniqueId, verificationValue, cmsCon);
            cmsCon.commit();
        } catch (Exception ex) {
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
         
       return terminalExist;
    }
}
