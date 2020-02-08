/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BillingCycleMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.BillingCycleMgtPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * this class handles the logic that is relevant to billing cycle table
 * @author nisansala
 */
public class BillingCycleMgtManager {
    //initializing variables

    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private BillingCycleMgtPersistance billCyclePer;

    /**
     * to retrieve all the billing data
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<BillingCycleMgtBean> getAllBillingData() throws SQLException, Exception {
        try {
            List<BillingCycleMgtBean> billingList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            billCyclePer = new BillingCycleMgtPersistance();

            //assign the merchant details to the erchant bean instance
            billingList = billCyclePer.getAllBillingData(CMSCon);

            CMSCon.commit();
            return billingList;

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * to insert a new billing cycle record
     * @param bill
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public Boolean insertNewBillCycle(BillingCycleMgtBean bill, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {

            //if the insert become success row will return 1
            Boolean success = false;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            billCyclePer = new BillingCycleMgtPersistance();
            success = billCyclePer.insertNewBillCycle(bill, CMSCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

            return success;

        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
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
        }
    }

    /**
     * to retrieve data from one record
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public BillingCycleMgtBean viewOneBillingCycle(String id) throws SQLException, Exception {
        BillingCycleMgtBean oneBean = null;
        try {
            billCyclePer = new BillingCycleMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            oneBean = billCyclePer.viewOneBillingCycle(CMSCon, id);
            CMSCon.commit();
            return oneBean;
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

    /**
     * to delete a record
     * @param billCode
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean deleteBillingData(String billCode, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            billCyclePer = new BillingCycleMgtPersistance();
            billCyclePer.deleteBillingData(CMSCon, billCode);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            //'row' indicates the success of deletion
            success = true;
        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
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
        }
        return success;
    }
    
    public int accountCount(String billCode) throws SQLException, Exception {
        int count = -1;
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            billCyclePer = new BillingCycleMgtPersistance();
            count = billCyclePer.accountCount(CMSCon, billCode);
            //sysAuditManager = new SystemAuditManager();
           // sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            //'row' indicates the success of deletion
            
        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
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
        }
        return count;
    }

    /**
     * to update a record
     * @param bill
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean updateBillCycle(BillingCycleMgtBean bill, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {
            int row = -1;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            billCyclePer = new BillingCycleMgtPersistance();
            //
            row = billCyclePer.updateBillCycle(bill, CMSCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            success = true;

            //row will indicate the success of updation 

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }

    public boolean isHoliday(String nextbillingDate) throws Exception {
        boolean isHoliday = false;
        try {

            CMSCon = DBConnection.getConnection();
            billCyclePer = new BillingCycleMgtPersistance();
            CMSCon.setAutoCommit(false);

            isHoliday = billCyclePer.isHoliday(CMSCon, nextbillingDate);

            CMSCon.commit();

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
        return isHoliday;
    }
    
}
