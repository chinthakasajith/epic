package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AreaBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class AreaMgtPersistance {

    private ResultSet rs = null;
    private HashMap<String, String> districtList = null;

    public List<AreaBean> getAreaList(Connection cmsCon) throws SQLException, Exception {
        List<AreaBean> areaList = new ArrayList<AreaBean>();
        PreparedStatement ps = null;
        String query;

        query = "SELECT AR.AREACODE, AR.DESCRIPTION, D.PROVINCE, D.DISTRICTNAME FROM AREA AR, DISTRICTPROVINCE D WHERE AR.DISTRICTCODE=D.DISTRICTCODE";

        try {

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                AreaBean bean = new AreaBean();

                bean.setAreaCode(rs.getString("AREACODE"));
                bean.setAreaDescription(rs.getString("DESCRIPTION"));
                bean.setProvinceName(rs.getString("PROVINCE"));
                bean.setDistriceName(rs.getString("DISTRICTNAME"));

                areaList.add(bean);
            }
            return areaList;

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public List<AreaBean> getAllProvinces(Connection cmsCon) throws Exception {
        List<AreaBean> terManufacList = new ArrayList<AreaBean>();
        PreparedStatement ps = null;
        String query;

        query = "SELECT DISTINCT PROVINCE FROM DISTRICTPROVINCE ORDER BY PROVINCE";

        try {

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                AreaBean bean = new AreaBean();
                bean.setProvinceName(rs.getString("PROVINCE"));

                terManufacList.add(bean);
            }
            return terManufacList;

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public List<AreaBean> getAllDistricats(Connection cmsCon) throws Exception {
        List<AreaBean> getDistrict = new ArrayList<AreaBean>();
        PreparedStatement ps = null;
        String query;

//        query = "SELECT DISTINCT DP.DISTRICTNAME,DP.DISTRICTCODE FROM DISTRICTPROVINCE DP, AREA AR WHERE AR.DISTRICTCODE=DP.DISTRICTCODE";
        query = " SELECT DISTINCT DP.DISTRICTNAME, DP.DISTRICTCODE FROM DISTRICTPROVINCE DP, AREA AR WHERE AR.DISTRICTCODE=DP.DISTRICTCODE ORDER BY DP.DISTRICTNAME";

        try {

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                AreaBean bean = new AreaBean();
                bean.setDistrictCode(rs.getString("DISTRICTCODE"));
                bean.setDistriceName(rs.getString("DISTRICTNAME"));

                getDistrict.add(bean);
            }
            return getDistrict;

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public AreaBean viewSelectedData(Connection cmsCon, String id) throws Exception {
        AreaBean bean = new AreaBean();
        PreparedStatement ps = null;
        String query;

        query = "SELECT AR.AREACODE, AR.DESCRIPTION, DP.DISTRICTNAME, DP.PROVINCE FROM AREA AR, DISTRICTPROVINCE DP WHERE AR.DISTRICTCODE=DP.DISTRICTCODE AND AR.AREACODE=?";

        try {

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, id);

            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setAreaCode(rs.getString("AREACODE"));
                bean.setAreaDescription(rs.getString("DESCRIPTION"));
                bean.setProvinceName(rs.getString("PROVINCE"));
                bean.setDistriceName(rs.getString("DISTRICTNAME"));
            }
            return bean;

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }

    }

    public int updateAreaMgtInfo(Connection cmsCon, AreaBean bean) throws SQLException, Exception {
        int rowCount = -1;
        PreparedStatement ps = null;
        String query;
        query = "UPDATE AREA SET DESCRIPTION=?,DISTRICTCODE=? WHERE AREACODE=?";

        try {

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getAreaDescription());
            ps.setString(2, bean.getDistrictCode());
            ps.setString(3, bean.getAreaCode());

            rowCount = ps.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
        return rowCount;
    }

    public String getDistrictCode(Connection cmsCon, AreaBean areaBean) throws Exception {
        String areaCode = null;
        PreparedStatement ps = null;
        String query;

        query = "SELECT DISTRICTCODE FROM DISTRICTPROVINCE WHERE PROVINCE=? AND DISTRICTNAME=?";

        try {

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, areaBean.getProvinceName());
            ps.setString(2, areaBean.getDistriceName());
            rs = ps.executeQuery();

            while (rs.next()) {
                areaCode = rs.getString("DISTRICTCODE");
            }
            return areaCode;

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
    }

    public int deleteModelInfo(Connection cmsCon, String id) throws Exception {
        int i = -1;
        PreparedStatement ps = null;

        try {
            String query = "DELETE FROM AREA WHERE AREACODE=?";

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, id);

            i = ps.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
        return i;
    }


    public List<AreaBean> getProvinceType(Connection cmsCon, String provinceType) throws Exception {
        List<AreaBean> proType = new ArrayList<AreaBean>();
        PreparedStatement ps = null;
        String query;

        query = "SELECT DISTRICTCODE, DISTRICTNAME FROM DISTRICTPROVINCE WHERE PROVINCE=?";

        try {

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, provinceType);

            rs = ps.executeQuery();

            while (rs.next()) {
                AreaBean bean = new AreaBean();

//                bean.setDistrictCode(rs.getString("DISTRICTCODE"));
                bean.setDistriceName(rs.getString("DISTRICTNAME"));

                proType.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
        return proType;
    }

    public int insertAreaData(Connection cmsCon, AreaBean bean) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        String query;

        query = "INSERT INTO AREA(AREACODE, DESCRIPTION, DISTRICTCODE, LASTUPDATEDUSER, "
                + "LASTUPDATEDTIME, CREATEDDATE) VALUES (?, ?, ?, ?, ?, SYSDATE)";

        try {

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, bean.getAreaCode());
            ps.setString(2, bean.getAreaDescription());
            ps.setString(3, bean.getDistrictCode());
            ps.setString(4, bean.getLastUpdatedUser());
            ps.setTimestamp(5, new Timestamp(bean.getLastUpdatedTime().getTime()));

            i = ps.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
        return i;
    }
}
