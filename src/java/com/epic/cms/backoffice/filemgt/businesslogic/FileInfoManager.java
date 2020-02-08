package com.epic.cms.backoffice.filemgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.backoffice.filemgt.bean.FileInfoBean;
import com.epic.cms.backoffice.filemgt.persistance.FileInfoPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class FileInfoManager {

    private Connection CMSCon;
    private List<FileInfoBean> fileInfoList;
    private FileInfoPersistance fileInfoPer;
    private CommonFilePathBean commonFilePathBean;
    FileInfoBean fileInfoBean;
    private SystemAuditManager sysAuditManager;

    public int insertFileInfo(SystemAuditBean systemAuditBean, FileInfoBean fileInfoBean) throws Exception {
        int resultId = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            fileInfoPer = new FileInfoPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            resultId = fileInfoPer.insertSeperateFileInfo(CMSCon, fileInfoBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return resultId;
    }

    public List findByFileType(String fileType) throws Exception {
        List filePath = null;
        try {

            fileInfoPer = new FileInfoPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            filePath = fileInfoPer.findByFileType(CMSCon, fileType);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return filePath;
    }

    public CommonFilePathBean getFilePaths(String osType) throws Exception {
        try {

            fileInfoPer = new FileInfoPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            commonFilePathBean = fileInfoPer.getFilePath(CMSCon, osType);
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
        return commonFilePathBean;
    }
    //  public List<FileInfoBean> getAllFileTypesBin() throws Exception {

    public List<FileInfoBean> getAllFiletypesInfo() throws Exception {
        try {

            fileInfoPer = new FileInfoPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            fileInfoList = fileInfoPer.getAllFileTypesInfo(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                DBConnection.dbConnectionClose(CMSCon);
            } catch (Exception ex) {
                throw ex;
            }
        }
        return fileInfoList;
    }
    
    public int addNewFileInfo(SystemAuditBean systemAuditBean, FileInfoBean bean) throws SQLException, Exception {
        int rowCount = -1;
        
        try {
                
            sysAuditManager = new SystemAuditManager();
            fileInfoPer = new FileInfoPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            rowCount = fileInfoPer.addNewFileInfo(CMSCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            
            CMSCon.commit();
            
            return rowCount;
            
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }
    
    public FileInfoBean viewSelectedFiles(String id) throws Exception {
        
        try {
            fileInfoPer = new FileInfoPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            fileInfoBean = fileInfoPer.viewSelectedFiles(CMSCon, id);
            CMSCon.commit();
            
            return fileInfoBean;
            
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqlex) {
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
    
     public int updateFileInfo(SystemAuditBean systemAuditBean, FileInfoBean fileInfoBean) throws SQLException, Exception {
        int rowCount = -1;
       
        try {
            
            sysAuditManager = new SystemAuditManager();
            fileInfoPer = new FileInfoPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            rowCount = fileInfoPer.updateFileInfo(CMSCon, fileInfoBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            
            CMSCon.commit();
            return rowCount;
            
            
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqlex) {
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
     
      public int deleteFileInfo(String id, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            
            fileInfoPer = new FileInfoPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            rowCount = fileInfoPer.deleteFileInfo(CMSCon, id);
            
            CMSCon.commit();
            
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqlex) {
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

//    public Boolean isFileAvailable(String fileName) throws Exception {
//        
//    }
    
     public boolean isFileAvailable(String fileName) throws SQLException, Exception {
        boolean status = false;
        try {
            fileInfoPer = new FileInfoPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            status = fileInfoPer.isFileAvailable(CMSCon, fileName);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
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
}