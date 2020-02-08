/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *this class use to wrote all the persistence that relate to section management
 * @author ayesh
 */
public class SectionMgtPersistance {

    private ResultSet rs = null;

    /**
     * check given status is available 
     * @param con -Connection
     * @param code - Status 
     * @return return ture if not exist otherwise return false 
     * @throws Exception 
     */
    public boolean checkStatusAvailable(Connection con, String code) throws Exception {
        boolean flag = false;
        PreparedStatement ps = null;
        try {
            String query = "SELECT COUNT(*) AS COUNT FROM SECTION SEC WHERE SEC.SECTIONCODE=?";
            ps = con.prepareStatement(query);
            ps.setString(1, code);
            rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                count = Integer.parseInt(rs.getString("COUNT"));
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
     * add new section to database 
     * @param con
     * @param code
     * @return
     * @throws Exception 
     */
    public int addNewSection(Connection con, SectionBean sectionBean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO SECTION SEC (SEC.SECTIONCODE, SEC.SORTKEY,"
                    + "SEC.DESCRIPTION, SEC.STATUS,SEC.LASTUPDATEDUSER)"
                    + " VALUES(?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, sectionBean.getSectionCode());
            ps.setString(2, sectionBean.getSortKey());
            ps.setString(3, sectionBean.getDescription());
            ps.setString(4, sectionBean.getStatusCode());
            ps.setString(5, sectionBean.getLasUpdatedUser());

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
     * get all details of section
     * @param con
     * @param userName
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<SectionBean> getAllSection(Connection con) throws SQLException, Exception {


        List<SectionBean> allSectionList = new ArrayList<SectionBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT S.SECTIONCODE,S.DESCRIPTION,S.SORTKEY,S.STATUS,"
                    + "ST.DESCRIPTION  AS DESCRIBTIONSTATUS,"
                    + "S.CREATETIME,S.LASTUPDATEDUSER,S.LASTUPDATEDTIME FROM SECTION "
                    + "S,STATUS ST WHERE S.STATUS=ST.STATUSCODE";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                SectionBean bean = new SectionBean();
                bean.setSectionCode(rs.getString("SECTIONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setSortKey(rs.getString("SORTKEY"));
                bean.setStatusCode(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("DESCRIBTIONSTATUS"));
                bean.setCreatedTime(rs.getDate("CREATETIME"));
                bean.setLasUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));

                allSectionList.add(bean);
            }

            return allSectionList;
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

    /**
     * update section query 
     * @param con
     * @param sectionBean
     * @return
     * @throws Exception 
     */
    public int updateSection(Connection con, SectionBean sectionBean) throws Exception {
        int resultID = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE SECTION SEC SET "
                    + "SEC.DESCRIPTION=?,SEC.SORTKEY=?,SEC.STATUS=?,"
                    + "SEC.LASTUPDATEDUSER=?,SEC.LASTUPDATEDTIME=? "
                    + "WHERE SEC.SECTIONCODE=? ";
            ps = con.prepareStatement(query);

            ps.setString(1, sectionBean.getDescription());
            ps.setString(2, sectionBean.getSortKey());
            ps.setString(3, sectionBean.getStatusCode());
            ps.setString(4, sectionBean.getLasUpdatedUser());
            ps.setTimestamp(5, SystemDateTime.getSystemDataAndTime());
            ps.setString(6, sectionBean.getSectionCode());


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
     * delete section with relate to section id
     * @param con
     * @param sectionID
     * @return deleted row count
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteSection(Connection con, String sectionID) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM SECTION SEC where SEC.SECTIONCODE =?";

            ps = con.prepareStatement(query);
            ps.setString(1, sectionID);

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

    ////upul///////////////
    /**
     * getAllRemainingSection
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<SectionBean> getAllRemainingSection(Connection con) throws SQLException, Exception {


        List<SectionBean> allRemainingSectionList = new ArrayList<SectionBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT SE.SECTIONCODE,SE.DESCRIPTION  FROM SECTION SE  "
                    + " WHERE SE.STATUS=? AND SE.SECTIONCODE NOT IN "
                    + "(SELECT SECTIONCODE FROM APPLICATIONSECTION )";
            ps = con.prepareStatement(query);
            ps.setString(1, StatusVarList.ACTIVE_STATUS);

            rs = ps.executeQuery();

            while (rs.next()) {
                SectionBean bean = new SectionBean();
                bean.setSectionCode(rs.getString("SECTIONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));


                allRemainingSectionList.add(bean);
            }

            return allRemainingSectionList;
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

    ////upul///////////////
    /**
     * getAllSectionByApplication
     * @param con
     * @param applicationCode
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<SectionBean> getAllSectionByApplication(Connection con, String applicationCode) throws SQLException, Exception {


        List<SectionBean> applicationSectionList = new ArrayList<SectionBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT AP.APPLICATIONCODE, AP.SECTIONCODE,SE.DESCRIPTION"
                    + " FROM APPLICATIONSECTION AP,SECTION SE  "
                    + "WHERE AP.SECTIONCODE=SE.SECTIONCODE AND AP.APPLICATIONCODE=? ";
            ps = con.prepareStatement(query);
            ps.setString(1, applicationCode);

            rs = ps.executeQuery();

            while (rs.next()) {
                SectionBean bean = new SectionBean();
                bean.setSectionCode(rs.getString("SECTIONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                applicationSectionList.add(bean);
            }

            return applicationSectionList;
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

}
