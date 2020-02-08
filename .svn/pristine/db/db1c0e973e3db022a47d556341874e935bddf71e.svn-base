/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.EmbossFileFormatDetailBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.EmbossFileFormatBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.EmbossFileFormatMgtPersistance;
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
public class EmbossFileFormatMgtManager {

    private SystemAuditManager sysAuditManager;
    private EmbossFileFormatMgtPersistance perObj;
    private Connection cmsCon;
    private List<StatusBean> statusList = null;
    private EmbossFileFormatBean embean = null;
    private EmbossFileFormatDetailBean dbean = null;

    /**
     * to get all emboss file format details
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<EmbossFileFormatBean> getAllEmbossFileFormats() throws SQLException, Exception {

        try {
            List<EmbossFileFormatBean> formatList = null;

            perObj = new EmbossFileFormatMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            formatList = perObj.getAllEmbossFileFormats(cmsCon);

            cmsCon.commit();
            return formatList;
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
     * to get all card type list
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CardTypeMgtBean> getAllCardTypeList() throws SQLException, Exception {

        try {
            List<CardTypeMgtBean> cardTypeList = null;

            perObj = new EmbossFileFormatMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            cardTypeList = perObj.getAllCardTypeList(cmsCon);

            cmsCon.commit();
            return cardTypeList;
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
     * to get status list
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<StatusBean> getStatusList() throws SQLException, Exception {
        statusList = new ArrayList<StatusBean>();
        try {

            perObj = new EmbossFileFormatMgtPersistance();
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

    /**
     * to get all emboss file format detail lists
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<EmbossFileFormatDetailBean> getEmbossFileFormatDetailList() throws SQLException, Exception {

        try {
            List<EmbossFileFormatDetailBean> formatDetailList = null;

            perObj = new EmbossFileFormatMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            formatDetailList = perObj.getEmbossFileFormatDetailList(cmsCon);

            cmsCon.commit();
            return formatDetailList;
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
     * to view a selected record
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public EmbossFileFormatBean viewSelectedEmbossFileFormat(String id) throws SQLException, Exception {

        try {
            perObj = new EmbossFileFormatMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            embean = perObj.viewSelectedEmbossFileFormat(cmsCon, id);
            cmsCon.commit();
            return embean;
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
     * to verify card type to check the record is already exist
     * @param s
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean verifyCType(String s) throws SQLException, Exception {
        boolean flag = false;
        try {
            perObj = new EmbossFileFormatMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            flag = perObj.verifyCType(cmsCon, s);

            return flag;

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
     * to add new record
     * @param systemAuditBean
     * @param embean
     * @param recformat
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int addNewEmbossFileFormat(SystemAuditBean systemAuditBean, EmbossFileFormatBean embean, String recformat, String reclength) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new EmbossFileFormatMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount = perObj.addNewEmbossFileFormat(cmsCon, embean, recformat, reclength);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);


            cmsCon.commit();
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

    /**
     * to update existing record
     * @param systemAuditBean
     * @param embean
     * @param recformat
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int updateEmbossFileFormat(SystemAuditBean systemAuditBean, EmbossFileFormatBean embean, String recformat, String reclength) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new EmbossFileFormatMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount = perObj.updateEmbossFileFormat(cmsCon, embean, recformat, reclength);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);


            cmsCon.commit();
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

    /**
     * to delete a record
     * @param id
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteEmbossFileFomat(String id, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            perObj = new EmbossFileFormatMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            rowCount = perObj.deleteEmbossFileFomat(cmsCon, id);
            cmsCon.commit();

        } catch (SQLException ex) {
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
        return rowCount;
    }

    /**
     * to get record length
     * @param arr
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int getLength(String[] arr) throws SQLException, Exception {
        int len = 0;
        try {
            perObj = new EmbossFileFormatMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            for (int i = 0; i < arr.length; i++) {
                int feildLen = perObj.getFeildMaxLength(cmsCon, arr[i]);
                len = len + feildLen;
            }

            return len;
        } catch (SQLException ex) {
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
}
