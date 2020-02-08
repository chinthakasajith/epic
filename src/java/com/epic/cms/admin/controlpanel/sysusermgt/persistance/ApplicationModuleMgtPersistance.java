/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *this persistence class use to wrote all the persistence that relate to application management
 * @author ayesh
 */
public class ApplicationModuleMgtPersistance {

    private ResultSet rs = null;

    /**
     * get all application details 
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<ApplicationModuleBean> getAllAppLication(Connection con) throws SQLException, Exception {


        List<ApplicationModuleBean> allSectionList = new ArrayList<ApplicationModuleBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT APP.APPLICATIONCODE,APP.DESCRIPTION,APP.STATUS, APP.TYPE,"
                    + " ST.DESCRIPTION AS DESCRIBTIONSTATUS ,APP.LASTUPDATEDUSER,"
                    + "APP.LASTUPDATEDTIME,APP.CREATETIME "
                    + "FROM APPLICATION APP,STATUS ST WHERE APP.STATUS=ST.STATUSCODE";

            ps = con.prepareStatement(query);


            rs = ps.executeQuery();

            while (rs.next()) {
                ApplicationModuleBean bean = new ApplicationModuleBean();
                bean.setApplicationCode(rs.getString("APPLICATIONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setCat(rs.getString("TYPE"));

                if (rs.getString("TYPE") != null) {
                    if (rs.getString("TYPE").equals("ISS")) {
                        bean.setCatDes("Issuing");
                    } else if (rs.getString("TYPE").equals("ACQ")) {
                        bean.setCatDes("Acquiring");
                    } else if (rs.getString("TYPE").equals("COM")) {
                        bean.setCatDes("Common");
                    }
                }

                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("DESCRIBTIONSTATUS"));
                bean.setCreatedTime(rs.getDate("CREATETIME"));
                bean.setLastUpdateuser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));


                allSectionList.add(bean);
            }

            return allSectionList;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    //////////////////upul//////////////////////////////////////
    /**
     * get all active application list
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<ApplicationModuleBean> getAllActiveApplicationModules(Connection con) throws SQLException,
            Exception {


        List<ApplicationModuleBean> allActiveAppList = new ArrayList<ApplicationModuleBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT APP.APPLICATIONCODE,APP.DESCRIPTION,APP.STATUS,APP.LASTUPDATEDUSER, "
                    + " APP.LASTUPDATEDTIME,APP.CREATETIME FROM APPLICATION APP WHERE APP.STATUS=?";
            ps = con.prepareStatement(query);
            ps.setString(1, StatusVarList.ACTIVE_STATUS);


            rs = ps.executeQuery();

            while (rs.next()) {
                ApplicationModuleBean bean = new ApplicationModuleBean();
                bean.setApplicationCode(rs.getString("APPLICATIONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setCreatedTime(rs.getDate("CREATETIME"));
                bean.setLastUpdateuser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));


                allActiveAppList.add(bean);
            }

            return allActiveAppList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /////////////////////////////////////////
    /**
     * check there is a application with given application code<BR>
     * if it has return false otherwise return ture
     * @param con
     * @param code
     * @return
     * @throws Exception 
     */
    public boolean checkAppAvailable(Connection con, String code) throws Exception {
        boolean flag = false;
        PreparedStatement ps = null;
        try {
            String query = "SELECT COUNT(*) AS count FROM APPLICATION APP WHERE "
                    + "APP.APPLICATIONCODE=?";
            ps = con.prepareStatement(query);
            ps.setString(1, code);
            rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                count = Integer.parseInt(rs.getString("count"));
            }
            if (count == 0) {
                flag = true;
            } else {
                flag = false;
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return flag;
    }

    /**
     * add new application module to database
     * @param con
     * @param sectionBean
     * @return
     * @throws Exception 
     */
    public int addNewApplictionModule(Connection con, ApplicationModuleBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO APPLICATION APP (APP.APPLICATIONCODE,"
                    + "APP.DESCRIPTION, APP.STATUS, APP.LASTUPDATEDUSER, APP.TYPE)"
                    + " VALUES(?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getApplicationCode());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getStatus());
            ps.setString(4, bean.getLastUpdateuser());
            ps.setString(5, bean.getCat());

            i = ps.executeUpdate();



        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return i;
    }

    /**
     * update application details
     * @param con
     * @param appBean
     * @return
     * @throws Exception 
     */
    public int updateApplication(Connection con, ApplicationModuleBean appBean) throws Exception {
        int resultID = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE APPLICATION APP SET "
                    + "APP.DESCRIPTION=?,APP.STATUS=?,APP.LASTUPDATEDUSER=?,APP.LASTUPDATEDTIME=?,APP.TYPE=? "
                    + "WHERE APP.APPLICATIONCODE=? ";
            ps = con.prepareStatement(query);

            ps.setString(1, appBean.getDescription());
            ps.setString(2, appBean.getStatus());
            ps.setString(3, appBean.getLastUpdateuser());
            ps.setTimestamp(4, SystemDateTime.getSystemDataAndTime());
            ps.setString(5, appBean.getCat());
            ps.setString(6, appBean.getApplicationCode());


            resultID = ps.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return resultID;
    }

    /**
     * delete application from database
     * @param con
     * @param appID
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteApplication(Connection con, String appID) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM APPLICATION APP where APP.APPLICATIONCODE =?";

            ps = con.prepareStatement(query);
            ps.setString(1, appID);

            i = ps.executeUpdate();



        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return i;
    }
}
