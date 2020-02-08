/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.FileDownloadDetailsBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.FileDownloadSearchBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.FileDownloadMgtPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class FileDownloadMgtManager {

    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private List<FileDownloadDetailsBean> searchList = null;
    private FileDownloadMgtPersistance fileDoenloadPersist = null;

    public List<FileDownloadDetailsBean> getAllFilesToDownload(FileDownloadSearchBean searchBean, SystemAuditBean systemAuditBean) throws Exception {
        try {
            fileDoenloadPersist = new FileDownloadMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = fileDoenloadPersist.getAllFilesToDownload(CMSCon, searchBean);
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

    public List<FileDownloadDetailsBean> getAllFilesToDownload(FileDownloadSearchBean searchBean) throws Exception {
        try {
            fileDoenloadPersist = new FileDownloadMgtPersistance();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = fileDoenloadPersist.getAllFilesToDownload(CMSCon, searchBean);

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
}
