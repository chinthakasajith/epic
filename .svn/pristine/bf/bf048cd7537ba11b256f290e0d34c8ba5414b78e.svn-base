/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AquireBinBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AquireBinFormBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.AquireBinManagementPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author badrika
 */
public class AquireBinManagementManager {

    private AquireBinManagementPersistance AquireBinPersistance;
    private Connection CMSCon;
    private Connection OnlineCon;
    private List<AquireBinBean> AquireBinBeanList;
    private List<AquireBinFormBean> ChannelList, CardTypeList, statusList, entryModeList;
    // private List<AquireBinFormBean> CardTypeList;
    //  private List<AquireBinFormBean> entryModeList;
    private SystemAuditManager sysAuditManager;
    private HashMap<String, String> cardKeyList = null;

    /**
     * to get all aquire bin detail list
     * @return
     * @throws Exception 
     */
    public List<AquireBinBean> getAllAquireBinDetailList() throws Exception {
        try {
            AquireBinPersistance = new AquireBinManagementPersistance();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            OnlineCon.setAutoCommit(false);
            AquireBinBeanList = AquireBinPersistance.getAllAquireBinDetailList(OnlineCon);
            OnlineCon.commit();
        } catch (Exception ex) {
            try {
                OnlineCon.rollback();
                throw ex;
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
        return AquireBinBeanList;
    }

    /**
     * to get all channel list
     * @return
     * @throws Exception 
     */
    public List<AquireBinFormBean> getChannelList() throws Exception {
        try {
            AquireBinPersistance = new AquireBinManagementPersistance();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            OnlineCon.setAutoCommit(false);
            ChannelList = AquireBinPersistance.getChannelList(OnlineCon);
            OnlineCon.commit();
        } catch (Exception ex) {
            try {
                OnlineCon.rollback();
                throw ex;
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
        return ChannelList;
    }

    /**
     * to get all card type list
     * @return
     * @throws Exception 
     */
    public List<AquireBinFormBean> getCardTypeList() throws Exception {
        try {
            AquireBinPersistance = new AquireBinManagementPersistance();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            OnlineCon.setAutoCommit(false);
            CardTypeList = AquireBinPersistance.getCardTypeList(OnlineCon);
            OnlineCon.commit();
        } catch (Exception ex) {
            try {
                OnlineCon.rollback();
                throw ex;
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
        return CardTypeList;
    }
    
    public HashMap<String,String> getCardKeyList() throws Exception {
        try {
            AquireBinPersistance = new AquireBinManagementPersistance();
            cardKeyList = new HashMap<String, String>();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            OnlineCon.setAutoCommit(false);
            cardKeyList = AquireBinPersistance.getCardKeyList(OnlineCon);
            OnlineCon.commit();
        } catch (Exception ex) {
            try {
                OnlineCon.rollback();
                throw ex;
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
        return cardKeyList;
    }

    /**
     * to get all entry mode list
     * @return
     * @throws Exception 
     */
//    public List<AquireBinFormBean> getEntryModeList() throws Exception {//del
//        try {
//            AquireBinPersistance = new AquireBinManagementPersistance();
//            OnlineCon = DBConnectionToOnlineDB.getConnection();
//            OnlineCon.setAutoCommit(false);
//            entryModeList = AquireBinPersistance.getEntryModeList(OnlineCon);
//            OnlineCon.commit();
//        } catch (Exception ex) {
//            try {
//                OnlineCon.rollback();
//                throw ex;
//            } catch (SQLException sqx) {
//                throw sqx;
//            }
//        } finally {
//            if (OnlineCon != null) {
//                try {
//                    OnlineCon.close();
//                } catch (SQLException e) {
//                    throw e;
//                }
//            }
//        }
//        return entryModeList;
//    }


    /**
     * to get all status list
     * @return
     * @throws Exception 
     */
    public List<AquireBinFormBean> getStatusList() throws Exception {
        try {
            AquireBinPersistance = new AquireBinManagementPersistance();
            OnlineCon = DBConnectionToOnlineDB.getConnection();

            OnlineCon.setAutoCommit(false);
            statusList = AquireBinPersistance.getStatusList(OnlineCon);
            OnlineCon.commit();
        } catch (Exception ex) {
            try {
                OnlineCon.rollback();
                throw ex;
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
        return statusList;
    }

    /**
     * to add new record
     * @param aqBean
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean insertAquireBin(AquireBinBean aqBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean flag = false;

        try {

            AquireBinPersistance = new AquireBinManagementPersistance();
            sysAuditManager = new SystemAuditManager();

            OnlineCon = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            OnlineCon.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            flag = AquireBinPersistance.insertAquireBin(OnlineCon, aqBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            OnlineCon.commit();
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                OnlineCon.rollback();
                CMSCon.rollback();

                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (OnlineCon != null && CMSCon != null) {
                try {
                    OnlineCon.close();
                    CMSCon.close();

                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return flag;
    }

    /**
     * to update existing record
     * @param aqBean
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean updateAquireBin(AquireBinBean aqBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean flag = false;

        try {

            AquireBinPersistance = new AquireBinManagementPersistance();
            sysAuditManager = new SystemAuditManager();

            OnlineCon = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            OnlineCon.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            flag = AquireBinPersistance.updateAquireBin(OnlineCon, aqBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            OnlineCon.commit();
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                OnlineCon.rollback();
                CMSCon.rollback();

                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (OnlineCon != null && CMSCon != null) {
                try {
                    OnlineCon.close();
                    CMSCon.close();

                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return flag;
    }

    /**
     * to delete existing record
     * @param aqBean
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean deleteAquireBin(AquireBinBean aqBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean flag = false;

        try {

            AquireBinPersistance = new AquireBinManagementPersistance();
            sysAuditManager = new SystemAuditManager();

            OnlineCon = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            OnlineCon.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            flag = AquireBinPersistance.deleteAquireBin(OnlineCon, aqBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            OnlineCon.commit();
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                OnlineCon.rollback();
                CMSCon.rollback();

                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (OnlineCon != null && CMSCon != null) {
                try {
                    OnlineCon.close();
                    CMSCon.close();

                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return flag;
    }
}
