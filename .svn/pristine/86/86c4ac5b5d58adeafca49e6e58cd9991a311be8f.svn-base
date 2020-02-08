/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.terminalmgt.terminaldata.businesslogic;

import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.persistance.MerchantLocationPersistance;
import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.MerchantSearchBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.TerminalDataCaptureBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.persistance.TerminalDataCapturePersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author nisansala
 */
public class TerminalDataCaptureManager {

    TerminalDataCapturePersistance terminalPer;
    private Connection CMSCon;
    private Connection ConToOnline = null;
    private SystemAuditManager sysAuditManager = null;
    private HashMap<String, String> modelList = null;
    private HashMap<String, String> allocateList = null;
    private HashMap<String, String> terminalStatusList = null;
    private List<TerminalDataCaptureBean> searchList = null;
    private HashMap<String, String> manufacturerList = null;
    HashMap<String, String> difManufactermModels = null;
    private List<TypeMgtBean> txnTypes = null;
    private List<RiskProfileBean> riskProfileList = null;
    private MerchantLocationPersistance merchLocPer = null;

    public HashMap<String, String> getAllModelList() throws Exception {
        try {
            terminalPer = new TerminalDataCapturePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            modelList = terminalPer.getAllModelList(CMSCon);
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
        return modelList;
    }

    public HashMap<String, String> getAllManufacturers() throws Exception {
        try {
            terminalPer = new TerminalDataCapturePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            manufacturerList = terminalPer.getAllManufacturers(CMSCon);
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
        return manufacturerList;
    }

    public HashMap<String, String> getAllocationStatus() throws Exception {
        try {
            terminalPer = new TerminalDataCapturePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            allocateList = terminalPer.getAllocationStatus(CMSCon);
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
        return allocateList;
    }

    public HashMap<String, String> getTerminalStatus() throws Exception {
        try {
            terminalPer = new TerminalDataCapturePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            terminalStatusList = terminalPer.getTerminalStatus(CMSCon);
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
        return terminalStatusList;
    }

    public List<TypeMgtBean> getAllTxnTypes() throws Exception {
        try {
            terminalPer = new TerminalDataCapturePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            txnTypes = terminalPer.getAllTxnTypes(CMSCon);
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
        return txnTypes;
    }

    public HashMap<String, String> getModelsToManufacturer(String manufacturer) throws Exception {
        try {
            terminalPer = new TerminalDataCapturePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            difManufactermModels = terminalPer.getModelsToManufacturer(CMSCon, manufacturer);
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
        return difManufactermModels;
    }

    public List<TerminalDataCaptureBean> getAssignedTransactions(String TID) throws Exception {
//           List<AssignedTasksDescriptionBean> assigneTaskDescriptionList = new ArrayList<AssignedTasksDescriptionBean>();
        List<TerminalDataCaptureBean> assgnedList = new ArrayList<TerminalDataCaptureBean>();

        try {
            terminalPer = new TerminalDataCapturePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            assgnedList = terminalPer.getAssignedTransactions(CMSCon, TID);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return assgnedList;
    }

    public List<TerminalDataCaptureBean> getNotAssignedTransactions(String MID, String TID) throws Exception {

        List<TerminalDataCaptureBean> notAssignedList = new ArrayList<TerminalDataCaptureBean>();

        try {
            terminalPer = new TerminalDataCapturePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            notAssignedList = terminalPer.getNotAssignedTransactions(CMSCon, MID, TID);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return notAssignedList;
    }

    public TerminalDataCaptureBean viewOneTerminalData(String id) throws SQLException, Exception {
        TerminalDataCaptureBean oneBean = null;
        try {
            terminalPer = new TerminalDataCapturePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            oneBean = terminalPer.viewOneTerminalData(CMSCon, id);
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

    public List<TerminalDataCaptureBean> searchTerminalData(TerminalDataCaptureBean terminalBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {
            terminalPer = new TerminalDataCapturePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = terminalPer.searchTerminalData(CMSCon, terminalBean);
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
        return searchList;
    }

    public List<MerchantSearchBean> searchMerchants(MerchantSearchBean searchBean) throws Exception {

        List<MerchantSearchBean> merSearchList = new ArrayList<MerchantSearchBean>();
        try {
            terminalPer = new TerminalDataCapturePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            merSearchList = terminalPer.searchMerchants(CMSCon, searchBean);
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
        return merSearchList;
    }

    public List<TerminalDataCaptureBean> getAllTerminalData() throws Exception {
        try {
            List<TerminalDataCaptureBean> terminalList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            terminalPer = new TerminalDataCapturePersistance();

            //assign the merchant details to the erchant bean instance
            terminalList = terminalPer.getAllTerminalData(CMSCon);

            CMSCon.commit();
            return terminalList;

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

    public boolean deleteTerminal(String terminalID, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        String status;
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            terminalPer = new TerminalDataCapturePersistance();
            status = terminalPer.checkTerminalStatus(CMSCon, terminalID);
            terminalPer.deactivateTerminal(CMSCon, terminalID);

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

    public Boolean insertTerminal(TerminalDataCaptureBean terminalBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {

            //if the insert become success row will return 1
            Boolean success = false;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            terminalPer = new TerminalDataCapturePersistance();
            success = terminalPer.insertTerminal(terminalBean, CMSCon);
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

//    public Boolean updateTerminal(TerminalDataCaptureBean terminalBean, SystemAuditBean systemAuditBean, List<TerminalDataCaptureBean> assignedBean, String[] transactions, String tid) throws Exception {
    public boolean insertToTxnType(String terminalId, String[] assignTxnTypeList, String merchantId) throws Exception {
        boolean success = false;
        boolean successToOnline = false;
        try {
            terminalPer = new TerminalDataCapturePersistance();
            CMSCon = DBConnection.getConnection();
            ConToOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            ConToOnline.setAutoCommit(false);

            if (assignTxnTypeList != null) {
                for (int i = 0; i < assignTxnTypeList.length; i++) {

                    String onlineTxnCode = terminalPer.getOnlineTxnCode(CMSCon, assignTxnTypeList[i]);
                    successToOnline = terminalPer.insertOnlineToTxnType(ConToOnline, terminalId, onlineTxnCode, merchantId);

                }

                if (successToOnline) {
                    for (int i = 0; i < assignTxnTypeList.length; i++) {

                        success = terminalPer.insertToTxnType(CMSCon, terminalId, assignTxnTypeList[i], merchantId);

                    }
                }
            }
            if (assignTxnTypeList == null) {
                success = true;
            }
            CMSCon.commit();
            ConToOnline.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            ConToOnline.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(ConToOnline);
        }
        return success;
    }

    public Boolean insertTerminalData(TerminalDataCaptureBean terminalBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {

            //if the insert become success row will return 1
            Boolean success = false;
            boolean successOnline = false;
            CMSCon = DBConnection.getConnection();
            ConToOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            ConToOnline.setAutoCommit(false);
            terminalPer = new TerminalDataCapturePersistance();

            if (terminalPer.isTerminalAvailableOnlineDB(ConToOnline, terminalBean.getTerminalID())) {
                successOnline = terminalPer.updateOnlineTerminal(ConToOnline, terminalBean);
            } else {
                successOnline = terminalPer.insertOnlineTerminal(ConToOnline, terminalBean);
            }

            if (successOnline) {
                success = terminalPer.insertTerminalData(terminalBean, CMSCon);

            }

            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            ConToOnline.commit();
            return success;

        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                ConToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null || ConToOnline != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    DBConnection.dbConnectionClose(CMSCon);
                    DBConnectionToOnlineDB.dbConnectionClose(ConToOnline);
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

//    public Boolean updateTerminal(TerminalDataCaptureBean terminalBean, SystemAuditBean systemAuditBean) throws Exception {
    public Boolean updateTerminal(TerminalDataCaptureBean terminalBean, SystemAuditBean systemAuditBean, List<TerminalDataCaptureBean> assignedBean, String[] transactions, String tid, String mid) throws Exception {
        boolean success = false;
        try {
            int row = -1;
            CMSCon = DBConnection.getConnection();
            ConToOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            terminalPer = new TerminalDataCapturePersistance();
            //
            row = terminalPer.updateTerminal(terminalBean, transactions, CMSCon);
            terminalPer.updateTerminalTxn(transactions, CMSCon, tid);
            terminalPer.updateTerminalTxnOnline(transactions, assignedBean, ConToOnline, tid,mid);

            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            success = true;

            //row will indicate the success of updation 

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                ConToOnline.rollback();
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
            if (ConToOnline != null) {
                try {
                    ConToOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }

    public Boolean isTerminalAllocated(String terminalId) throws Exception {
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            terminalPer = new TerminalDataCapturePersistance();
            //
            success = terminalPer.isTerminalAllocated(CMSCon, terminalId);
            CMSCon.commit();


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

    public Boolean isTerminalActive(String terminalId) throws Exception {
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            terminalPer = new TerminalDataCapturePersistance();
            //
            success = terminalPer.isTerminalActive(CMSCon, terminalId);
            CMSCon.commit();


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

    public Boolean isMerchantActive(String merchantId) throws Exception {
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            terminalPer = new TerminalDataCapturePersistance();
            //
            success = terminalPer.isMerchantActive(CMSCon, merchantId);
            CMSCon.commit();


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

    public Boolean updateTerminalTable(String terminalId, String merchantId, SystemAuditBean systemAuditBean) throws Exception {
        boolean successBackEnd = false;
        boolean successOnline = false;
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            ConToOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            ConToOnline.setAutoCommit(false);

            terminalPer = new TerminalDataCapturePersistance();
            //
            if (terminalPer.isTerminalAvailableOnlineDB(ConToOnline, terminalId)) {
                successOnline = terminalPer.updateTerminalTableOnlineTable(ConToOnline, terminalId, merchantId);
            } else {
                successOnline = terminalPer.insertTerminalOnlineDB(ConToOnline, terminalId, merchantId);
            }

            if (successOnline) {
                successBackEnd = terminalPer.updateTerminalTable(CMSCon, terminalId, merchantId);

                if (successBackEnd) {
                    sysAuditManager = new SystemAuditManager();
                    sysAuditManager.insertAudit(systemAuditBean, CMSCon);
                    CMSCon.commit();
                    ConToOnline.commit();

                    success = true;
                } else {
                    CMSCon.rollback();
                    ConToOnline.rollback();
                    success = false;
                }

            }




        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                ConToOnline.rollback();
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

            if (ConToOnline != null) {
                try {
                    ConToOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }

    public Boolean updateTerminalStatus(String terminalId, SystemAuditBean systemAuditBean, String terStatus, String onlineStatus) throws Exception {


        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            ConToOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            ConToOnline.setAutoCommit(false);

            terminalPer = new TerminalDataCapturePersistance();

            if (terminalPer.updateterminalStatusOnlineDb(ConToOnline, terminalId, onlineStatus)) {
                if (terminalPer.updateTerminalStatus(CMSCon, terminalId, terStatus)) {
                    sysAuditManager = new SystemAuditManager();
                    sysAuditManager.insertAudit(systemAuditBean, CMSCon);
                    CMSCon.commit();
                    ConToOnline.commit();
                    success = true;
                } else {
                    CMSCon.rollback();
                    ConToOnline.rollback();
                    success = false;
                }

            } else {
                ConToOnline.rollback();
                success = false;
            }

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                ConToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
            if (CMSCon != null) {
                try {
                    DBConnection.dbConnectionClose(CMSCon);
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (ConToOnline != null) {
                try {
                    DBConnectionToOnlineDB.dbConnectionClose(ConToOnline);
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }

    public Boolean updateTerminalStatusToActivate(String terminalId, SystemAuditBean systemAuditBean, String terStatus, String onlineStatus) throws Exception {


        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            ConToOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            ConToOnline.setAutoCommit(false);

            terminalPer = new TerminalDataCapturePersistance();

            if (terminalPer.updateterminalStatusOnlineDb(ConToOnline, terminalId, onlineStatus)) {
                if (terminalPer.updateTerminalStatusToActivate(CMSCon, terminalId, terStatus)) {
                    sysAuditManager = new SystemAuditManager();
                    sysAuditManager.insertAudit(systemAuditBean, CMSCon);
                    CMSCon.commit();
                    ConToOnline.commit();
                    success = true;
                } else {
                    CMSCon.rollback();
                    ConToOnline.rollback();
                    success = false;
                }

            } else {
                ConToOnline.rollback();
                success = false;
            }

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                ConToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
            if (CMSCon != null) {
                try {
                    DBConnection.dbConnectionClose(CMSCon);
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (ConToOnline != null) {
                try {
                    DBConnectionToOnlineDB.dbConnectionClose(ConToOnline);
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }

    public Boolean updateterminalAllocationStatus(String terminalId, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            ConToOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            ConToOnline.setAutoCommit(false);
            terminalPer = new TerminalDataCapturePersistance();
            merchLocPer = new MerchantLocationPersistance();
            //

            String mid = merchLocPer.getCommonMerchant(CMSCon);


            success = terminalPer.deleteTerminalTxn(CMSCon, terminalId);
            if (success) {
                success = terminalPer.deleteTerminalOnlineTxn(ConToOnline, terminalId);
            }
            if (success) {
                success = terminalPer.updateterminalAllocationStatus(CMSCon, terminalId, mid);
            }
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            ConToOnline.commit();
            //success = true;


        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                ConToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
            if (CMSCon != null) {
                try {
                    DBConnection.dbConnectionClose(CMSCon);
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (ConToOnline != null) {
                try {
                    DBConnectionToOnlineDB.dbConnectionClose(ConToOnline);
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }

    public List<MerchantCategoryBean> getMccList(String merchantId) throws Exception {
        try {
            List<MerchantCategoryBean> mccList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            terminalPer = new TerminalDataCapturePersistance();

            //assign the merchant details to the erchant bean instance
            mccList = terminalPer.getMccList(CMSCon, merchantId);

            CMSCon.commit();
            return mccList;

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

    public List<CurrencyBean> getCurrencyList(String merchantId) throws Exception {
        try {
            List<CurrencyBean> currencyList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            terminalPer = new TerminalDataCapturePersistance();

            //assign the merchant details to the erchant bean instance
            currencyList = terminalPer.getCurrencyList(CMSCon, merchantId);

            CMSCon.commit();
            return currencyList;

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

    public List<TypeMgtBean> getTxnList(String merchantId) throws Exception {
        try {
            List<TypeMgtBean> txnTypeList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            terminalPer = new TerminalDataCapturePersistance();

            //assign the merchant details to the erchant bean instance
            txnTypeList = terminalPer.getTxnList(CMSCon, merchantId);

            CMSCon.commit();
            return txnTypeList;

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

    public List<RiskProfileBean> getTerminalRiskProfile(String merchantId) throws Exception {

        try {
            riskProfileList = new ArrayList<RiskProfileBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            terminalPer = new TerminalDataCapturePersistance();

            riskProfileList = terminalPer.getTerminalRiskProfile(CMSCon, merchantId);


            CMSCon.commit();
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
        return riskProfileList;
    }

    public boolean isTransactionsSettled(String merchantId, String terminalId) throws Exception {
        boolean isSettled = false;
        try {

            CMSCon = DBConnection.getConnection();
            ConToOnline = DBConnectionToOnlineDB.getConnection();
            terminalPer = new TerminalDataCapturePersistance();

            CMSCon.setAutoCommit(false);
            ConToOnline.setAutoCommit(false);

            HashMap<String, String> map = terminalPer.getFinancialTxnIds(CMSCon);

            Set set = (Set) map.entrySet();
            Iterator iter = set.iterator();

            while (iter.hasNext()) {
                Map.Entry mapEntry = (Map.Entry) iter.next();
                isSettled = terminalPer.isTransactionsSettled(ConToOnline, terminalId, merchantId, (String) mapEntry.getValue());
            }

            CMSCon.commit();
            ConToOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                ConToOnline.rollback();

                throw ex;

            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null | ConToOnline != null) {
                try {
                    DBConnection.dbConnectionClose(CMSCon);
                    DBConnectionToOnlineDB.dbConnectionClose(ConToOnline);
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return isSettled;
    }

    public List<TerminalDataCaptureBean> getTerminalDataFromOnlineTable(String terminalId) throws Exception {
        List<TerminalDataCaptureBean> list = new ArrayList<TerminalDataCaptureBean>();
        try {

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            terminalPer = new TerminalDataCapturePersistance();

            list = terminalPer.getTerminalDataFromOnlineTable(CMSCon, terminalId);


            CMSCon.commit();
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
        return list;
    }
}
