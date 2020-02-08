package com.epic.cms.backoffice.filemgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.backoffice.eodprocessmgt.bean.EODProcessMgtBean;
import com.epic.cms.backoffice.eodprocessmgt.persistance.EODProcessMgtPersistance;
import com.epic.cms.backoffice.filemgt.bean.FileInfoBean;
import com.epic.cms.backoffice.filemgt.bean.FileTypeBean;
import com.epic.cms.backoffice.filemgt.persistance.FileTypePersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class FileTypeManager {

    private Connection CMSCon;
    private Connection CMSOnline;
    private SystemAuditManager sysAuditManager;
    private List<FileTypeBean> fileTypeList;
    private List<FileInfoBean> fileTypeList2;
    private FileTypeBean fileTypeBean;
    private FileTypePersistance fileTypePersistance;

    public List<FileTypeBean> getAllFileTypes() throws Exception {
        try {

            fileTypePersistance = new FileTypePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            fileTypeList = fileTypePersistance.getAllFileType(CMSCon);
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
        return fileTypeList;
    }

    public List<FileInfoBean> getAllFileTypesBin() throws Exception {
        try {
            fileTypePersistance = new FileTypePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            fileTypeList2 = fileTypePersistance.getAllFileTypesBin(CMSCon);
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
        return fileTypeList2;
    }

    public FileTypeBean getFileTypeByFileCode(String fileTypeByCode) throws Exception {
        try {
            fileTypePersistance = new FileTypePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            fileTypeBean = fileTypePersistance.findByFileTypeCode(CMSCon, fileTypeByCode);
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
        return fileTypeBean;
    }

    public boolean isRecordAvailable(String fileName) throws SQLException, Exception {
        boolean status = false;
        try {
            fileTypePersistance = new FileTypePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            status = fileTypePersistance.isRecordAvailable(CMSCon, fileName);
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
            if (CMSCon != null) {
                try {
                    DBConnection.dbConnectionClose(CMSCon);
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return status;
    }

    public int addNewFileType(SystemAuditBean systemAuditBean, FileTypeBean bean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            fileTypePersistance = new FileTypePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            rowCount = fileTypePersistance.addNewFileType(CMSCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();

            return rowCount;

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqlx) {
                throw sqlx;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
    }

    public FileTypeBean viewSelectedFiles(String id) throws Exception {
        try {
            fileTypePersistance = new FileTypePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            fileTypeBean = fileTypePersistance.viewSelectedFiles(CMSCon, id);
            CMSCon.commit();

            return fileTypeBean;
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
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException sqlex) {
                    throw sqlex;
                }
            }
        }
    }

    public int updateFileType(SystemAuditBean systemAuditBean, FileTypeBean fileTypeBean) throws SQLException, Exception {
        int rowCount = -1;

        try {

            sysAuditManager = new SystemAuditManager();
            fileTypePersistance = new FileTypePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            rowCount = fileTypePersistance.updateFileType(CMSCon, fileTypeBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            return rowCount;


        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqlex) {
                throw sqlex;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException sqlex2) {
                    throw sqlex2;
                }
            }
        }
    }

    public int deleteFileType(String id, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {

            fileTypePersistance = new FileTypePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            rowCount = fileTypePersistance.deleteFileType(CMSCon, id);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqlex) {
                throw sqlex;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
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

    public List<FileTypeBean> getAllCardTypeDetails() throws SQLException, Exception {
        try {

            fileTypePersistance = new FileTypePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            fileTypeList = fileTypePersistance.getAllCardTypeDetails(CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqlex) {
                throw sqlex;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
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
        return fileTypeList;
    }

    public List<FileTypeBean> getCardDomainDetails() throws SQLException, Exception {
        try {

            fileTypePersistance = new FileTypePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            fileTypeList = fileTypePersistance.getCardDomainDetails(CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqlex) {
                throw sqlex;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
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
        return fileTypeList;
    }

    public List<FileTypeBean> getSendingChannel() throws SQLException, Exception {
        try {

            fileTypePersistance = new FileTypePersistance();
//            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSOnline.setAutoCommit(false);
            fileTypeList = fileTypePersistance.getSendingChannel(CMSOnline);
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (SQLException sqlex) {
                throw sqlex;
            }
        } catch (Exception ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
            }
        } finally {
            if (CMSOnline != null) {
                try {
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return fileTypeList;
    }

    public String getChannelTypeDescription(String channelType) throws SQLException, Exception {
        try {
        
            fileTypePersistance = new FileTypePersistance();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSOnline.setAutoCommit(false);
            channelType = fileTypePersistance.getChannelTypeDescription(CMSOnline, channelType);
            CMSOnline.commit();
            
        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
            } catch (SQLException sqlEx) {
                throw sqlEx;
            }
        } catch (Exception ex) {
            try {
                CMSOnline.rollback();
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (CMSOnline != null) {
                try {
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return channelType;
    }
}
