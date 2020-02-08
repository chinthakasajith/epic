/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.businesslogic;

import com.epic.cms.admin.controlpanel.profilemgt.bean.FeeProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.persistance.FeeProfileMgtPersisitance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *this class use to wrote all the manager that relate to fee profile
 * @author ayesh
 */
public class FeeProfileManager {

//    private static FeeProfileManager managerInstance;
    private Connection CMSCon;
    private FeeProfileMgtPersisitance feeProfPer;
    private SystemAuditManager sysAuditManager;
    private FeeProfileMgtPersisitance perObj;
    private List<CurrencyBean> currencyLst;

//    private FeeProfileManager() {
//    }
//    private FeeProfileManager() {
//    }
    /**
     * get fee profile manager instance
     * @return FeeProfileManager
     */
//    public static FeeProfileManager getFeeProfileManager() {
//
//        if (managerInstance == null) {
//            managerInstance = new FeeProfileManager();
//        }
//        return managerInstance;
//    }
    /**
     * add new fee profile to database.</BR>
     * in here manager class for that.
     * @param feeBean -fee profile bean that store all the data-FeeProfileBean
     * @return 1-if success11
     */
//    public int addNewFeeProfile(FeeProfileBean feeBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
//        try {
//            int row = -1;
//
//            CMSCon = DBConnection.getConnection();
//            CMSCon.setAutoCommit(false);
//
//            perObj = new FeeProfileMgtPersisitance();
//            row = perObj.addNewFeeProfilePer(CMSCon, feeBean);
//
//            sysAuditManager = new SystemAuditManager();
//            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
//            CMSCon.commit();
//            return row;
//
//        } catch (SQLException ex) {
//            try {
//                CMSCon.rollback();
//                throw ex;
//            } catch (SQLException sqx) {
//                throw sqx;
//            }
//        } finally {
//            if (CMSCon != null) {
//                try {
//                    CMSCon.close();
//                } catch (SQLException e) {
//                    throw e;
//                }
//            }
//        }
//
//    }
    /**
     * get all fee profile information manager
     * @return  List - FeeProfileBean 
     * @throws SQLException
     * @throws Exception 
     */
    public List<FeeProfileBean> getAllFeeprofileInfo() throws SQLException, Exception {
        try {
            List<FeeProfileBean> beanList = null;

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            perObj = new FeeProfileMgtPersisitance();

            beanList = perObj.getFeeProfileInfo(CMSCon);
            CMSCon.commit();
            return beanList;

        } catch (SQLException ex) {
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


    }

    /**
     * delete fee profile manager 
     * @param systemAuditBean - audit trace bean
     * @param proID - fee profile Id that want to delete
     * @return int - deleted row count-if 1-success otherwise false
     * @throws SQLException
     * @throws Exception 
     */
//    public int deleteFeeProfile(SystemAuditBean systemAuditBean, String proID) throws SQLException, Exception {
//        int rowCount = -1;
//        try {
//            perObj = new FeeProfileMgtPersisitance();
//            CMSCon = DBConnection.getConnection();
//            CMSCon.setAutoCommit(false);
//
//            sysAuditManager = new SystemAuditManager();
//
//            rowCount = perObj.deleteFeeProfile(CMSCon, proID);
//            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
//
//            CMSCon.commit();
//
//        } catch (SQLException ex) {
//            try {
//                CMSCon.rollback();
//                throw ex;
//            } catch (SQLException sqx) {
//                throw sqx;
//            }
//        } finally {
//            if (CMSCon != null) {
//                try {
//                    CMSCon.close();
//                } catch (SQLException e) {
//                    throw e;
//                }
//            }
//        }
//        return rowCount;
//    }
    /**
     * update fee profile manager
     * @param feeBean-fee profile bean that want to update
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
//    public int updateFeeProfile(FeeProfileBean feeBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
//        try {
//            int row = -1;
//            CMSCon = DBConnection.getConnection();
//            CMSCon.setAutoCommit(false);
//            perObj = new FeeProfileMgtPersisitance();
//            row = perObj.updateFeeProfilePer(feeBean, CMSCon);
//            sysAuditManager = new SystemAuditManager();
//
//            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
//            CMSCon.commit();
//
//            return row;
//        } catch (SQLException ex) {
//            try {
//                CMSCon.rollback();
//                throw ex;
//            } catch (SQLException sqx) {
//                throw sqx;
//            }
//        } finally {
//            if (CMSCon != null) {
//                try {
//                    CMSCon.close();
//                } catch (SQLException e) {
//                    throw e;
//                }
//            }
//        }
//    }
    public List<FeeProfileBean> getAllFeeProfileDetail() throws SQLException, Exception {
        try {
            List<FeeProfileBean> feeProfBeanList;

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            feeProfPer = new FeeProfileMgtPersisitance();

            feeProfBeanList = feeProfPer.getAllFeeProfileDetail(CMSCon);
            CMSCon.commit();
            return feeProfBeanList;

        } catch (SQLException ex) {
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

    }

    public List<FeeBean> getAllFeeDetail(String feeCategory) throws SQLException, Exception {
        try {
            List<FeeBean> feeBeanList;

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            feeProfPer = new FeeProfileMgtPersisitance();

            feeBeanList = feeProfPer.getAllFeeDetail(CMSCon, feeCategory);
            CMSCon.commit();
            return feeBeanList;

        } catch (SQLException ex) {
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

    }

    public boolean insertFeeProfile(FeeProfileBean feeProf, List<FeeBean> txnList, List<FeeBean> serviceList, SystemAuditBean systemAuditBean) throws Exception {
        try {

            //if the insert become success row will return 1
            Boolean success = false;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            feeProfPer = new FeeProfileMgtPersisitance();
            success = feeProfPer.insertFeeProfile(feeProf, txnList, serviceList, CMSCon);
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

    public FeeProfileBean viewOneFeeProfile(String id) throws SQLException, Exception {
        FeeProfileBean oneBean = null;
        try {

            feeProfPer = new FeeProfileMgtPersisitance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            oneBean = feeProfPer.viewOneFeeProfile(CMSCon, id);

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

    public List<FeeBean> viewFeeProfileFee(String id) throws SQLException, Exception {
        List<FeeBean> feeBeanList = null;
        try {

            feeProfPer = new FeeProfileMgtPersisitance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            feeBeanList = feeProfPer.viewFeeProfileFee(CMSCon, id);

            CMSCon.commit();
            return feeBeanList;
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

    public FeeBean viewOneFee(String id) throws SQLException, Exception {
        FeeBean feeBean = null;
        try {

            feeProfPer = new FeeProfileMgtPersisitance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            feeBean = feeProfPer.viewOneFee(CMSCon, id);

            CMSCon.commit();
            return feeBean;
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

    public boolean deleteFeeProfile(String feeProfCode, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            feeProfPer = new FeeProfileMgtPersisitance();
            int row = feeProfPer.deleteFeeProfile(CMSCon, feeProfCode);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            //'row' indicates the success of deletion
            if (row == 1) {
                success = true;
            }
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

    public int updateFeeProfile(FeeProfileBean feeProf, List<FeeBean> txnList, List<FeeBean> serviceList, SystemAuditBean systemAuditBean) throws Exception {
        try {
            int row = -1;

            //if the insert becomes a success row will return 1
            Boolean success = false;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            feeProfPer = new FeeProfileMgtPersisitance();
            row = feeProfPer.UpdateFeeProfile(feeProf, txnList, serviceList, CMSCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

            return row;

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
    
    public List<CurrencyBean> getAllCurrencyLst() throws Exception {
        try {
            feeProfPer = new FeeProfileMgtPersisitance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currencyLst = feeProfPer.getCurrencyDetails(CMSCon);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return currencyLst;
    }
}
