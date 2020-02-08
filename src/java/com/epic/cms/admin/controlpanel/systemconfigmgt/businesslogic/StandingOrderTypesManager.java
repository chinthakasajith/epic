/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.StandingOrderTypesBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.UtilityProviderBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.StandingOrderTypesPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author asitha_l
 */
public class StandingOrderTypesManager {

    private List<StatusBean> statusList = null;
    private Connection cmsCon;
    private StandingOrderTypesPersistance standingOrderTypesPersistance = null;
    private HashMap<String, String> currencyMap = null;
    private HashMap<String, String> areasMap = null;
    private HashMap<String, String> bankNames = null;
    private String commonParam = null;
    Map<String, String> branchList = null;
    private SystemAuditManager sysAuditManager;
    private HashMap<String, String> payTypeList = null;
    private List<UtilityProviderBean> utilityProviderList = null;
    private List<FeeTypeBean> feeTypeList = null;

    public List<StatusBean> getStatus() throws Exception {
        statusList = new ArrayList<StatusBean>();
        try {

            standingOrderTypesPersistance = new StandingOrderTypesPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            statusList = standingOrderTypesPersistance.getStatustList(cmsCon);

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

    public HashMap<String, String> getCurrency() throws Exception {
        try {

            standingOrderTypesPersistance = new StandingOrderTypesPersistance();
            currencyMap = new HashMap<String, String>();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            currencyMap = standingOrderTypesPersistance.getCurrency(cmsCon);
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
        return currencyMap;
    }

    public HashMap<String, String> getAreas() throws Exception {
        try {

            standingOrderTypesPersistance = new StandingOrderTypesPersistance();
            areasMap = new HashMap<String, String>();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            areasMap = standingOrderTypesPersistance.getAreas(cmsCon);
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
        return areasMap;
    }

    public HashMap<String, String> getBanks() throws Exception {
        try {

            standingOrderTypesPersistance = new StandingOrderTypesPersistance();
            bankNames = new HashMap<String, String>();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            bankNames = standingOrderTypesPersistance.getBanks(cmsCon);
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
        return bankNames;
    }

    public String getCommonparam() throws Exception {
        try {

            standingOrderTypesPersistance = new StandingOrderTypesPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            commonParam = standingOrderTypesPersistance.getCommonparam(cmsCon);
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
        return commonParam;
    }

    public Map<String, String> getBranchList(String bankCode) throws Exception {
        try {
            standingOrderTypesPersistance = new StandingOrderTypesPersistance();
            branchList = new HashMap<String, String>();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            branchList = standingOrderTypesPersistance.getBranchList(cmsCon, bankCode);
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
        return branchList;
    }

    public boolean insertNewStandingOrderType(StandingOrderTypesBean bean, SystemAuditBean systemAuditBean) throws Exception {
        try {

            //if the insert become success row will return 1
            Boolean success = false;
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            standingOrderTypesPersistance = new StandingOrderTypesPersistance();
            success = standingOrderTypesPersistance.insertNewStandingOrderType(bean, cmsCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            cmsCon.commit();

            return success;

        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public List<StandingOrderTypesBean> getAllStandingOrderData() throws SQLException, Exception {
        try {
            List<StandingOrderTypesBean> standingOrderList = null;
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            standingOrderTypesPersistance = new StandingOrderTypesPersistance();

            standingOrderList = standingOrderTypesPersistance.getAllStandingOrderData(cmsCon);

            cmsCon.commit();
            return standingOrderList;

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public Map<String, String> getPayTypeList(String bankCode, String commonParam) throws Exception {
        try {
            standingOrderTypesPersistance = new StandingOrderTypesPersistance();
            payTypeList = new HashMap<String, String>();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            payTypeList = standingOrderTypesPersistance.getPayTypeList(cmsCon, bankCode, commonParam);
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
        return payTypeList;
    }

    public StandingOrderTypesBean getStandingOrderTypesById(String id) throws Exception {
        StandingOrderTypesBean standingOrderTypesBean = null;
        try {

            standingOrderTypesPersistance = new StandingOrderTypesPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            standingOrderTypesBean = standingOrderTypesPersistance.getStandingOrderTypesById(cmsCon, id);
            cmsCon.commit();

        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(cmsCon);
        }
        return standingOrderTypesBean;
    }

    public boolean deleteStandingOrder(String id, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            standingOrderTypesPersistance = new StandingOrderTypesPersistance();
            standingOrderTypesPersistance.deleteStandingOrder(cmsCon, id);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            cmsCon.commit();
            success = true;
        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }

    public boolean updateStandingOrderType(StandingOrderTypesBean standingOrderTypesBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            standingOrderTypesPersistance = new StandingOrderTypesPersistance();
            standingOrderTypesPersistance.updateStandingOrderType(standingOrderTypesBean, cmsCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            cmsCon.commit();
            success = true;

            //row will indicate the success of updation 
        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }

    //get utility provider list
    public List<UtilityProviderBean> getUtilityProviderList() throws Exception {
        try {
            utilityProviderList = new ArrayList<UtilityProviderBean>();

            standingOrderTypesPersistance = new StandingOrderTypesPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            utilityProviderList = standingOrderTypesPersistance.getUtilityProviderList(cmsCon);
            cmsCon.commit();
        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return utilityProviderList;
    }

    //get All Fee Types
    public List<FeeTypeBean> getAllFeeTypes() throws Exception {
        try {
            feeTypeList = new ArrayList<FeeTypeBean>();
            standingOrderTypesPersistance = new StandingOrderTypesPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            feeTypeList = standingOrderTypesPersistance.getAllFeeTypes(cmsCon);
            cmsCon.commit();
        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return feeTypeList;
    }

}
