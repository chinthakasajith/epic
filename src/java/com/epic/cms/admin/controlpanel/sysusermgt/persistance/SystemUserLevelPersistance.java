/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserLevelBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRolePrivilegeBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sajith_g
 */
public class SystemUserLevelPersistance {
    
    private ResultSet rs = null;
    private List<UserLevelBean> userLevelList = null;
    private List<UserRolePrivilegeBean> appSecPageTaskList = null;
    private List<ApplicationModuleBean> userRoleAppliacationList = null;
    private List<SectionBean> sectionList = null;
    private List<PageBean> pageList = null;
    private List<TaskBean> taskList = null;
    private List<UserRoleBean> userRoleListUnassignedToSchema = null;
    private List<UserRoleBean> userRoleListAssignedToSchema = null;
    /**
     * this method insert the user level..
     *
     * @param con
     * @param userLevel
     * @return success - boolean
     * @throws Exception
     */
    public boolean insertUserLevel(Connection con, UserLevelBean userLevel) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO USERLEVEL(LEVELID,DESCRIPTION, "
                    + "STATUS,LASTUPDATEDUSER,CREATETIME) "
                    + "VALUES(?,?,?,?,?) ");
            insertStat.setString(1, userLevel.getUserLevelID());
            insertStat.setString(2, userLevel.getDescription());
            insertStat.setString(3, userLevel.getStatusCode());
            insertStat.setString(4, userLevel.getLastUpdatedUser());
            insertStat.setTimestamp(5, new Timestamp(userLevel.getCreatedTime().getTime()));

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }
    
    /**
     * this method return all the user levels.
     *
     * @param con
     * @return userlevelList-List<UserLevelBean>
     * @throws Exception
     */
    public List<UserLevelBean> getAllUserLevel(Connection con) throws Exception {
        PreparedStatement getAllUserLevel = null;
        try {
            getAllUserLevel = con.prepareStatement("SELECT IUR.LEVELID,IUR.DESCRIPTION,"
                    + "IUR.LASTUPDATEDUSER,IUR.CREATETIME, AST.DESCRIPTION AS STDESCRIPTION,IUR.STATUS "
                    + "FROM USERLEVEL IUR, STATUS AST WHERE IUR.STATUS = AST.STATUSCODE");
            rs = getAllUserLevel.executeQuery();
            userLevelList = new ArrayList<UserLevelBean>();
            while (rs.next()) {

                UserLevelBean tempUserLevel = new UserLevelBean();

                tempUserLevel.setUserLevelID(rs.getString("LEVELID"));
                tempUserLevel.setDescription(rs.getString("DESCRIPTION"));
                tempUserLevel.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                tempUserLevel.setCreatedTime(rs.getDate("CREATETIME"));
                tempUserLevel.setStatusDes(rs.getString("STDESCRIPTION"));
                tempUserLevel.setStatusCode(rs.getString("STATUS"));

                userLevelList.add(tempUserLevel);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            rs.close();
            getAllUserLevel.close();

        }

        return userLevelList;
    }
    
    /**
     * this method delete the user level
     *
     * @param con
     * @param userLevel
     * @return success - boolean
     * @throws Exception
     */
    public boolean deleteUserLevel(Connection con, UserLevelBean userLevel) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE USERLEVEL WHERE LEVELID=? ");
            deleteStat.setString(1, userLevel.getUserLevelID());

            deleteStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            deleteStat.close();
        }
        return success;
    }
    
    /**
     * this method update the user level
     *
     * @param con
     * @param userLevel
     * @return success -boolean
     * @throws Exception
     */
    public boolean updateUserLevel(Connection con, UserLevelBean userLevel) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE USERLEVEL SET DESCRIPTION=?, "
                    + "STATUS=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=? "
                    + "WHERE LEVELID=? ");

            updateStat.setString(1, userLevel.getDescription());
            updateStat.setString(2, userLevel.getStatusCode());
            updateStat.setString(3, userLevel.getLastUpdatedUser());
            updateStat.setTimestamp(4, new Timestamp(userLevel.getLastUpdatedTime().getTime()));
            updateStat.setString(5, userLevel.getUserLevelID());

            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }
    
    /**
     * this method return all the active user levels.
     *
     * @param con
     * @return userlevelList-List<UserLevelBean>
     * @throws Exception
     */
    public List<UserLevelBean> getAllActiveUserLevel(Connection con) throws Exception {
        PreparedStatement getAllUserLevel = null;
        try {
            getAllUserLevel = con.prepareStatement("SELECT IUR.LEVELID,IUR.DESCRIPTION,"
                    + "IUR.LASTUPDATEDUSER,IUR.CREATETIME, AST.DESCRIPTION AS STDESCRIPTION,IUR.STATUS "
                    + "FROM USERLEVEL IUR, STATUS AST WHERE IUR.STATUS = AST.STATUSCODE AND IUR.STATUS='ACT'");
            rs = getAllUserLevel.executeQuery();
            userLevelList = new ArrayList<UserLevelBean>();
            while (rs.next()) {

                UserLevelBean tempUserLevel = new UserLevelBean();

                tempUserLevel.setUserLevelID(rs.getString("LEVELID"));
                tempUserLevel.setDescription(rs.getString("DESCRIPTION"));
                tempUserLevel.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                tempUserLevel.setCreatedTime(rs.getDate("CREATETIME"));
                tempUserLevel.setStatusDes(rs.getString("STDESCRIPTION"));
                tempUserLevel.setStatusCode(rs.getString("STATUS"));

                userLevelList.add(tempUserLevel);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            rs.close();
            getAllUserLevel.close();

        }

        return userLevelList;
    }
}
