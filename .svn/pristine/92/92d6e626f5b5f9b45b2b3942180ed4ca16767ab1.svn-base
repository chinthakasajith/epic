package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AreaBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.AreaMgtPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class AreaMgtManager {

    private Connection cmsCon;
    private AreaMgtPersistance areaMgtPersistance;
    private SystemAuditManager sysAuditManager;
    private HashMap<String, String> districtList = null;
    private AreaBean areaBean;
//    HashMap<String, String> proType = null;

    public List<AreaBean> getAreaList() throws Exception {
        try {

            List<AreaBean> areaList = null;
            areaMgtPersistance = new AreaMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            areaList = areaMgtPersistance.getAreaList(cmsCon);
            cmsCon.commit();

            return areaList;

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception ex2) {
                throw ex2;
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

    public List<AreaBean> getAllProvinces() throws Exception {
        try {

            List<AreaBean> terModelList = null;
            areaMgtPersistance = new AreaMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            terModelList = areaMgtPersistance.getAllProvinces(cmsCon);
            cmsCon.commit();

            return terModelList;

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception ex2) {
                throw ex2;
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

    public List<AreaBean> getAllDistricats() throws Exception {
        try {

            List<AreaBean> districtlList = null;
            areaMgtPersistance = new AreaMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            districtlList = areaMgtPersistance.getAllDistricats(cmsCon);
            cmsCon.commit();

            return districtlList;

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception ex2) {
                throw ex2;
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

    public AreaBean vireSelectedData(String id) throws Exception {
        try {
            areaMgtPersistance = new AreaMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            areaBean = areaMgtPersistance.viewSelectedData(cmsCon, id);
            cmsCon.commit();

            return areaBean;

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException ex2) {
                throw ex2;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception ex2) {
                throw ex2;
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

    public int updateAreaMgtInfo(SystemAuditBean systemAuditBean, AreaBean bean) throws Exception {
        int rowCount = -1;

        try {

            sysAuditManager = new SystemAuditManager();
            areaMgtPersistance = new AreaMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount = areaMgtPersistance.updateAreaMgtInfo(cmsCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);

            cmsCon.commit();
            return rowCount;


        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlex2) {
                    throw sqlex2;
                }
            }
        }
    }

    public String getDistrictCode(AreaBean areaBean) throws Exception {
        try {
            String areaCode;
            areaMgtPersistance = new AreaMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            areaCode = areaMgtPersistance.getDistrictCode(cmsCon, areaBean);
            cmsCon.commit();

            return areaCode;

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException ex2) {
                throw ex2;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception ex2) {
                throw ex2;
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

    public int deleteAeraInfo(String id, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {

            sysAuditManager = new SystemAuditManager();
            areaMgtPersistance = new AreaMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            rowCount = areaMgtPersistance.deleteModelInfo(cmsCon, id);

            cmsCon.commit();

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqlex) {
                throw sqlex;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception sqlex) {
                throw sqlex;
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

    public List<AreaBean> getProvinceType(String provinceType) throws Exception {
        try {

            List<AreaBean> proType = null;
            areaMgtPersistance = new AreaMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            proType = areaMgtPersistance.getProvinceType(cmsCon, provinceType);

            cmsCon.commit();

            return proType;

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
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

    public int insertAreaData(SystemAuditBean systemAuditBean, AreaBean bean) throws SQLException, Exception {
        int rowCount = -1;

        try {

            sysAuditManager = new SystemAuditManager();
            areaMgtPersistance = new AreaMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount = areaMgtPersistance.insertAreaData(cmsCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);

            cmsCon.commit();

            return rowCount;

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
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
