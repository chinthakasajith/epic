/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.payment.businesslogic;

import com.epic.cms.backoffice.payment.bean.BatchSummaryRecord;
import com.epic.cms.backoffice.payment.bean.PaymentBatchBean;
import com.epic.cms.backoffice.payment.bean.PaymentBean;
import com.epic.cms.backoffice.payment.persistance.BatchPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ruwan_e
 */
public class BatchManager {

    private BatchPersistance batchPersistance;
    private Connection CMSCon;
    private Map<String, String> statusMap;
    private Map<String, String> branchMap;
    private List<PaymentBatchBean> paymentBatches;
    private PaymentBatchBean paymentBatch;
    private List<String> usernames;
    
    public BatchManager() {
    }

    public Map<String, String> getBatchStatusMap() throws SQLException, Exception {
        try {

            batchPersistance = new BatchPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            statusMap = batchPersistance.getBatchStatusMap(CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return statusMap;
    }

    public List<PaymentBatchBean> getAllPaymentBatches() throws SQLException, Exception {
        try {

            batchPersistance = new BatchPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            paymentBatches = batchPersistance.getPaymentBatches(CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception oex) {
                throw oex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return paymentBatches;
    }
    
//    public List<PaymentBatchBean> searchPaymentBatches(Date from, Date to, ){
//    
//    }

    public Map<String , String> getBranchMap() throws SQLException, Exception {
        
        try {

            batchPersistance = new BatchPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            branchMap = batchPersistance.getBranchMap(CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return branchMap;
        
    }

    public List<String> getAuthenticatedUsers() throws SQLException, Exception {
        try {

            batchPersistance = new BatchPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            usernames = batchPersistance.getUsernames(CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return usernames;
    }
    public int getTotalChequeTxnCount(String batchId) throws SQLException, Exception{
        int count = 0;
        try {

            batchPersistance = new BatchPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            count = batchPersistance.getTotalChequeTxnCount(batchId, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return count;
    }

    public int getTotalCashTxnCount(String batchId) throws SQLException, Exception{
        int count = 0;
        try {

            batchPersistance = new BatchPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            count = batchPersistance.getTotalCashTxnCount(batchId, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return count;
    }
    
    
    public List<PaymentBatchBean> searchBatches(String batchID, String branch, String username, String status, String paymentDateFrom, String paymentDateTo) throws SQLException, Exception {
        try {

            batchPersistance = new BatchPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            paymentBatches = batchPersistance.searchBatches(batchID, branch, username, status, paymentDateFrom, paymentDateTo, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return paymentBatches;
    }
    
    public PaymentBatchBean getBatchById(String batchID) throws SQLException, Exception {
        try {

            batchPersistance = new BatchPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            paymentBatch = batchPersistance.getBatchById(batchID, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return paymentBatch;
    }
    
          public List<PaymentBean> getAllPaymentDetailsByBatchId(String batchId) throws SQLException, Exception{
          
          try {
            List<PaymentBean> paymentList = null;
            batchPersistance = new BatchPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            paymentList = batchPersistance.getAllPaymentDetailsByBatchId(CMSCon,batchId);

            CMSCon.commit();
            return paymentList;
            
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
      }

    public Double getTotalTxnAmount(String batchId) throws SQLException, Exception {
         try {
            Double amount = 0.0;
            batchPersistance = new BatchPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            amount = batchPersistance.getTotalTxnAmount(CMSCon,batchId);

            CMSCon.commit();
            return amount;
            
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
    }

    public double getChequeTxnAmount(String batchId) throws SQLException, Exception {
        try {
            Double amount = 0.0;
            batchPersistance = new BatchPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            amount = batchPersistance.getTxnAmountByBatchIdAndType(CMSCon, batchId, "CHEQUE");

            CMSCon.commit();
            return amount;
            
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
    }

    public double getCashTxnAmount(String batchId) throws SQLException, Exception {
        try {
            Double amount = 0.0;
            batchPersistance = new BatchPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            amount = batchPersistance.getTxnAmountByBatchIdAndType(CMSCon, batchId, "CASH");

            CMSCon.commit();
            return amount;
            
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
    }

    public ArrayList<BatchSummaryRecord> getBatchSummary(String id) throws SQLException, Exception {
        try {
            Double amount = 0.0;
            batchPersistance = new BatchPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            ArrayList<BatchSummaryRecord> bsr = batchPersistance.getBatchSummary(CMSCon, id);

            CMSCon.commit();
            return bsr;
            
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
    }

    public boolean reconcileBatch(String batchId) throws  Exception {
        try {
            boolean result = false; 
            batchPersistance = new BatchPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            result = batchPersistance.reconcileBatch(CMSCon,batchId);

            CMSCon.commit();
            return result;
            
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
    }
    
 
}
