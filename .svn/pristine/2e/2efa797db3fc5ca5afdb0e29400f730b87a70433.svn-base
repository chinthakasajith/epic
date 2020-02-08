/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.persistance.SectionMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ayesh
 */
public class SectionManager {

    private Connection CMSCon;
    private SectionMgtPersistance sectionPerObj = null;
    private static SectionManager instance = null;
    private SystemAuditManager sysAuditManager;

    public static SectionManager getInstance() {
        if (instance == null) {
            instance = new SectionManager();
        }
        return instance;
    }

    /**
     * check with database if given section code exit or not.<BR>
     * if it there return false ,not there return ture
     * @param statusCode - string
     * @return -boolean
     * @throws SQLException
     * @throws Exception 
     */
    public boolean checkExistSection(String sectionCode) throws SQLException, Exception {
        boolean flag = false;
        try {
            sectionPerObj = new SectionMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = sectionPerObj.checkStatusAvailable(CMSCon, sectionCode);
            CMSCon.commit();
            return flag;

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
     * add new section to database, 
     * @param sectionBean - CMSSectionBean
     * @return int
     * @throws SQLException
     * @throws Exception 
     */
    public int addSection(SectionBean sectionBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int i = -1;
        try {
            sectionPerObj = new SectionMgtPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            i = sectionPerObj.addNewSection(CMSCon, sectionBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

            return i;
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                ex.printStackTrace();
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
     * get all section details 
     * @param sectionCode
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<SectionBean> getAllSection() throws SQLException, Exception {

        try {
            List<SectionBean> sectionList = null;
            sectionPerObj = new SectionMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sectionList = sectionPerObj.getAllSection(CMSCon);
            CMSCon.commit();
            return sectionList;

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
     * edit section with respect to there section code
     * @param sectionCode
     * @throws SQLException
     * @throws Exception 
     */
    public int editSection(SectionBean sectionBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int i = -1;
        try {
            sectionPerObj = new SectionMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            i = sectionPerObj.updateSection(CMSCon, sectionBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
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
        return i;
    }

    /**
     * delete section with section id
     * @param sectionID
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteSection(String sectionID, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            sectionPerObj = new SectionMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            rowCount = sectionPerObj.deleteSection(CMSCon, sectionID);

            CMSCon.commit();

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
        return rowCount;
    }

    /////upul
    /**
     * getAllRemainingSection
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<SectionBean> getAllRemainingSection() throws SQLException, Exception {

        try {
            List<SectionBean> sectionRemList = null;
            sectionPerObj = new SectionMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sectionRemList = sectionPerObj.getAllRemainingSection(CMSCon);
            CMSCon.commit();
            return sectionRemList;

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

    //////upul//////
    /**
     * getAllSectionByApplication
     * @param application
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<SectionBean> getAllSectionByApplication(String application) throws SQLException, Exception {

        try {
            List<SectionBean> appSectionList = null;
            sectionPerObj = new SectionMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            appSectionList = sectionPerObj.getAllSectionByApplication(CMSCon, application);
            CMSCon.commit();
            return appSectionList;

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

}
