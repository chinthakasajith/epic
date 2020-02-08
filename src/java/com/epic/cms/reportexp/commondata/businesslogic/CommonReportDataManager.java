/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.commondata.businesslogic;

import com.epic.cms.reportexp.commondata.persistance.CommonReportDataPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author asela
 */
public class CommonReportDataManager {

    private Connection CMSCon;
    private CommonReportDataPersistance commonPer = null;

    public HashMap<String, String> getAllPriorityLevelMap() throws SQLException, Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        try {

            commonPer = new CommonReportDataPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            map = commonPer.getAllPriorityLevelMap(CMSCon);
            CMSCon.commit();
            return map;
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } catch (Exception sqx) {
                throw sqx;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } catch (Exception sqx) {
                throw sqx;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
    }

    public HashMap<String, String> getAllCardTypesMap() throws SQLException, Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        try {

            commonPer = new CommonReportDataPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            map = commonPer.getAllCardTypesMap(CMSCon);
            CMSCon.commit();
            return map;
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } catch (Exception sqx) {
                throw sqx;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } catch (Exception sqx) {
                throw sqx;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
    }

    public HashMap<String, String> getAllCardProductMap() throws SQLException, Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        try {

            commonPer = new CommonReportDataPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            map = commonPer.getAllCardProductMap(CMSCon);
            CMSCon.commit();
            return map;
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } catch (Exception sqx) {
                throw sqx;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } catch (Exception sqx) {
                throw sqx;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
    }

    public HashMap<String, String> getAllBranchListMap() throws SQLException, Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        try {

            commonPer = new CommonReportDataPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            map = commonPer.getAllBranchListMap(CMSCon);
            CMSCon.commit();
            return map;
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } catch (Exception sqx) {
                throw sqx;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } catch (Exception sqx) {
                throw sqx;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
    }

    public List<String> getUserList() throws SQLException, Exception {
        List<String> list = new ArrayList<String>();
        try {
            commonPer = new CommonReportDataPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            list = commonPer.getUserList(CMSCon);
            CMSCon.commit();
            return list;
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } catch (Exception sqx) {
                throw sqx;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } catch (Exception sqx) {
                throw sqx;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
    }

    public HashMap<String, String> getCardDomainMap() throws SQLException, Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        try {

            commonPer = new CommonReportDataPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            map = commonPer.getCardDomainMap(CMSCon);
            CMSCon.commit();
            return map;
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } catch (Exception sqx) {
                throw sqx;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } catch (Exception sqx) {
                throw sqx;
            }

        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
    }
}
