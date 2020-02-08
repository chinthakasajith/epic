/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.payment.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.backoffice.payment.bean.PaymentBatchBean;
import com.epic.cms.backoffice.payment.bean.PaymentBean;
import com.epic.cms.backoffice.payment.persistance.PaymentPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author badrika
 */
public class PaymentManager {

    private PaymentBatchBean bean;
    private PaymentPersistance perObj;
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private List<CurrencyBean> currencyLst;
    private List<PaymentBatchBean> todayBatchkList;

    public PaymentBatchBean getLastBatchInfo(String ip, String user) throws Exception, SQLException {
        try {
            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bean = perObj.getLastBatchInfo(CMSCon, ip, user);
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
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
        return bean;
    }
    
    //get users last batch info
     public PaymentBatchBean getLastBatchInfoUser(String ip, String user) throws Exception, SQLException {
        try {
            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bean = perObj.getLastBatchInfoUser(CMSCon, ip, user);
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
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
        return bean;
    }
     
      public PaymentBatchBean getLastBatchInfoIp(String ip, String user) throws Exception, SQLException {
        try {
            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bean = perObj.getLastBatchInfoIp(CMSCon, ip, user);
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
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
        return bean;
    }

    //insertNewBatch
    public int insertNewBatch(SystemAuditBean systemAuditBean, int batId, String ip, String user) throws Exception, SQLException {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            rowCount = perObj.insertNewBatch(CMSCon, batId, ip, user);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
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
        return rowCount;
    }

    public List<CurrencyBean> getAllCurrencyLst() throws Exception {
        try {
            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currencyLst = perObj.getCurrencyDetails(CMSCon);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return currencyLst;
    }

    public HashMap<String, String> getAllBankList() throws Exception {

        HashMap<String, String> banklist = null;
        try {

            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            banklist = perObj.getAllBankList(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return banklist;
    }

    public int insertPayment(SystemAuditBean systemAuditBean, PaymentBean bean) throws Exception, SQLException {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            rowCount = perObj.insertPayment(CMSCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
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
        return rowCount;
    }

    public int closeBatch(SystemAuditBean systemAuditBean, String id, String user, PaymentBatchBean batBean) throws Exception, SQLException {

        int rowCount = -1;
        int rowCount2 = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            rowCount = perObj.closeBatch(CMSCon, id, user, batBean);
            rowCount2 = perObj.closePayments(CMSCon, id);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
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
        return rowCount + rowCount2;
    }
     
       public int voidPayment(SystemAuditBean systemAuditBean, String id, String user) throws Exception, SQLException {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            rowCount = perObj.voidPayment(CMSCon, id,user);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
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
        return rowCount;
    }

    public int getPaymentId() throws Exception {

        int num = -1;
        try {

            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            num = perObj.getPaymentId(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return num;
    }

    //gatbatchExptime
    public String gatbatchExptime(String batId) throws Exception {

        String exptime = null;
        try {

            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            exptime = perObj.gatbatchExptime(CMSCon, batId);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return exptime;
    }
    
    public Double[] gatMaxPaymentAmount() throws Exception {

        Double maxPay[] = new Double[2];
        try {

            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            maxPay = perObj.gatMaxPaymentAmount(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return maxPay;
    }

    public int getMaxBatchId() throws Exception {

        int num = -1;
        try {

            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            num = perObj.getMaxBatchId(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return num;
    }

    public List<PaymentBatchBean> getAllBatchDetailsForToday(String ip) throws SQLException, Exception {

        try {

            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            todayBatchkList = perObj.getAllBatchDetailsForToday(CMSCon, ip);

            CMSCon.commit();
            return todayBatchkList;

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

    public List<PaymentBean> getAllPaymentDetails(String batID) throws SQLException, Exception {

        try {
            List<PaymentBean> paymentList = null;
            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            paymentList = perObj.getAllPaymentDetails(CMSCon, batID);

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

    public PaymentBatchBean PaymentsOfBatch(String batchid) throws SQLException, Exception {

        try {            
            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bean = perObj.PaymentsOfBatch(CMSCon, batchid);

            CMSCon.commit();
            return bean;

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
    
     public String dualAuthUserRole(String user) throws SQLException, Exception {

        try {
            perObj = new PaymentPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            String dualAuth = perObj.dualAuthUserRole(CMSCon, user);

            CMSCon.commit();
            
            return dualAuth;

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
    
    //
}
