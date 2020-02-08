/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CreditLimitSchemaUserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationSectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageTaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionPageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
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
 * @author chanuka
 */
public class SystemUserRolePersistance {

    private ResultSet rs = null;
    private List<UserRoleBean> userRoleList = null;
    private List<UserRolePrivilegeBean> appSecPageTaskList = null;
    private List<ApplicationModuleBean> userRoleAppliacationList = null;
    private List<SectionBean> sectionList = null;
    private List<PageBean> pageList = null;
    private List<TaskBean> taskList = null;
    private List<UserRoleBean> userRoleListUnassignedToSchema = null;
    private List<UserRoleBean> userRoleListAssignedToSchema = null;

    /**
     * this method delete the user role
     *
     * @param con
     * @param userRole
     * @return success - boolean
     * @throws Exception
     */
    public boolean deleteUserRole(Connection con, UserRoleBean userRole) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE USERROLE WHERE USERROLE=? ");
            deleteStat.setString(1, userRole.getUserRoleCode());

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
     * this method return all the user roles.
     *
     * @param con
     * @return userRoleList-List<UserRoleBean>
     * @throws Exception
     */
    public List<UserRoleBean> getAllUserRole(Connection con) throws Exception {
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT IUR.USERROLE,IUR.DESCRIPTION,"
                    + "IUR.LASTUPDATEDUSER,IUR.CREATETIME, AST.DESCRIPTION AS STDESCRIPTION,UL.DESCRIPTION AS LEVELDES,IUR.LEVELID,IUR.STATUS "
                    + "FROM USERROLE IUR LEFT JOIN USERLEVEL UL ON IUR.LEVELID=UL.LEVELID JOIN STATUS AST ON IUR.STATUS=AST.STATUSCODE ");
            rs = getAllUserRole.executeQuery();
            userRoleList = new ArrayList<UserRoleBean>();
            while (rs.next()) {

                UserRoleBean tempUserRole = new UserRoleBean();

                tempUserRole.setUserRoleCode(rs.getString("USERROLE"));
                tempUserRole.setDescription(rs.getString("DESCRIPTION"));
                tempUserRole.setUserLevelID(rs.getString("LEVELID"));
                tempUserRole.setUserLevelDes(rs.getString("LEVELDES"));
                tempUserRole.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                tempUserRole.setCreatedTime(rs.getDate("CREATETIME"));
                tempUserRole.setStatusDes(rs.getString("STDESCRIPTION"));
                tempUserRole.setStatusCode(rs.getString("STATUS"));

                userRoleList.add(tempUserRole);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return userRoleList;
    }

    /**
     * this method insert the user role..
     *
     * @param con
     * @param userRole
     * @return success - boolean
     * @throws Exception
     */
    public boolean insertUserRole(Connection con, UserRoleBean userRole) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO USERROLE(USERROLE,DESCRIPTION,LEVELID, "
                    + "STATUS,LASTUPDATEDUSER,CREATETIME) "
                    + "VALUES(?,?,?,?,?,?) ");
            insertStat.setString(1, userRole.getUserRoleCode());
            insertStat.setString(2, userRole.getDescription());
            insertStat.setString(3, userRole.getUserLevelID());
            insertStat.setString(4, userRole.getStatusCode());
            insertStat.setString(5, userRole.getLastUpdatedUser());
            insertStat.setTimestamp(6, new Timestamp(userRole.getCreatedTime().getTime()));

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    //insert duplicate user role code
    public boolean insertDuplicateUserRole(Connection con, UserRoleBean userRoleBean, List<ApplicationModuleBean> userRoleApplicationList, List<List> sectionList) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        PreparedStatement selectStat = null;

        //user role code
        try {
            String query = "INSERT INTO USERROLE (USERROLE,DESCRIPTION,LEVELID,STATUS,LASTUPDATEDUSER,CREATETIME) VALUES (?,?,?,?,?,?)";
            insertStat = con.prepareStatement(query);
            insertStat.setString(1, userRoleBean.getUserRoleCode());
            insertStat.setString(2, userRoleBean.getDescription());
            insertStat.setString(3, userRoleBean.getUserLevelID());
            insertStat.setString(4, userRoleBean.getStatusCode());
            insertStat.setString(5, userRoleBean.getLastUpdatedUser());
            insertStat.setTimestamp(6, new Timestamp(userRoleBean.getCreatedTime().getTime()));

            insertStat.executeUpdate();
            //success=true;
        } catch (Exception ex) {
            throw ex;
        }
        //applications
        try {
            String query = "INSERT INTO USERROLEAPPLICATION (USERROLECODE,APPLICATIONCODE,LASTUPDATEDUSER,CREATETIME) VALUES (?,?,?,?)";
            for (ApplicationModuleBean userRoleApplicationList1 : userRoleApplicationList) {
                userRoleApplicationList1.getApplicationCode();
                insertStat = con.prepareStatement(query);
                insertStat.setString(1, userRoleBean.getUserRoleCode());
                insertStat.setString(2, userRoleApplicationList1.getApplicationCode());
                insertStat.setString(3, userRoleBean.getLastUpdatedUser());
                insertStat.setTimestamp(4, new Timestamp(userRoleBean.getCreatedTime().getTime()));

                insertStat.executeUpdate();
            }
            //success=true;
        } catch (Exception ex) {
            throw ex;
        }
        //sections
        try {
            String query = "INSERT INTO USERAPPLICATIONSECTION(USERROLECODE,APPLICATIONCODE,SECTIONCODE,LASTUPDATEDUSER,CREATETIME) VALUES (?,?,?,?,?)";
            for (List sectionList1 : sectionList) {
                List<SectionBean> sectionBeansList = sectionList1;
                for (SectionBean sectionBean : sectionBeansList) {
                    insertStat = con.prepareStatement(query);
                    insertStat.setString(1, userRoleBean.getUserRoleCode());
                    insertStat.setString(2, sectionBean.getUserRoleAppCode());
                    insertStat.setString(3, sectionBean.getSectionCode());
                    insertStat.setString(4, userRoleBean.getLastUpdatedUser());
                    insertStat.setTimestamp(5, new Timestamp(userRoleBean.getCreatedTime().getTime()));

                    insertStat.executeUpdate();
                }
            }
            //success = true;
        } catch (Exception ex) {
            throw ex;
        }
        //pages
        try {
            //select pages to old user role code from USERSECTIONPAGE
            String query1 = "SELECT SECTIONCODE,PAGECODE FROM USERSECTIONPAGE WHERE USERROLECODE = ?";
            String query2 = "INSERT INTO USERSECTIONPAGE(USERROLECODE,SECTIONCODE,PAGECODE,LASTUPDATEDUSER,CREATETIME) VALUES (?,?,?,?,?)";
            selectStat = con.prepareStatement(query1);
            insertStat = con.prepareStatement(query2);
            selectStat.setString(1, userRoleBean.getOldUserRoleCode());
            rs = selectStat.executeQuery();

            while (rs.next()) {
                insertStat.setString(1, userRoleBean.getUserRoleCode().trim());
                insertStat.setString(2, rs.getString("SECTIONCODE").trim());
                insertStat.setString(3, rs.getString("PAGECODE").trim());
                insertStat.setString(4, userRoleBean.getLastUpdatedUser().trim());
                insertStat.setTimestamp(5, new Timestamp(userRoleBean.getCreatedTime().getTime()));
                insertStat.executeUpdate();
            }
            //success=true;
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
        }
        //task
        try {
            String query1 = "SELECT PAGECODE,TASKCODE FROM USERPAGETASK WHERE USERROLECODE= ?";
            String query2 = "INSERT INTO USERPAGETASK(USERROLECODE,PAGECODE,TASKCODE,LASTUPDATEDUSER,CREATETIME) VALUES (?,?,?,?,?)";
            selectStat = con.prepareStatement(query1);
            insertStat = con.prepareStatement(query2);
            selectStat.setString(1, userRoleBean.getOldUserRoleCode());
            rs = selectStat.executeQuery();

            while (rs.next()) {
                insertStat.setString(1, userRoleBean.getUserRoleCode());
                insertStat.setString(2, rs.getString("PAGECODE"));
                insertStat.setString(3, rs.getString("TASKCODE"));
                insertStat.setString(4, userRoleBean.getLastUpdatedUser());
                insertStat.setTimestamp(5, new Timestamp(userRoleBean.getCreatedTime().getTime()));
                insertStat.executeUpdate();
            }
            success = true;
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            selectStat.close();
            insertStat.close();
        }
        return success;
    }

    /**
     * this method update the user role
     *
     * @param con
     * @param userRole
     * @return success -boolean
     * @throws Exception
     */
    public boolean updateUserRole(Connection con, UserRoleBean userRole) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE USERROLE SET DESCRIPTION=?,LEVELID=?, "
                    + "STATUS=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=? "
                    + "WHERE USERROLE=? ");

            updateStat.setString(1, userRole.getDescription());
            updateStat.setString(2, userRole.getUserLevelID());
            updateStat.setString(3, userRole.getStatusCode());
            updateStat.setString(4, userRole.getLastUpdatedUser());
            updateStat.setTimestamp(5, new Timestamp(userRole.getLastUpdatedTime().getTime()));
            updateStat.setString(6, userRole.getUserRoleCode());

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
     * this method return all the tasks,pages,sections,and application assigned
     * to the particular user role
     *
     * @param con
     * @param userRoleCode
     * @return appSecPageTaskList-List<UserRolePrivilegeBean>
     * @throws Exception
     */
    public List<UserRolePrivilegeBean> getTasksByUserRoleCode(Connection con, String userRoleCode) throws Exception {
        PreparedStatement findByUserRoleStat = null;
        try {
            findByUserRoleStat = con.prepareStatement(" SELECT USERROLE.DESCRIPTION AS USERROLECODE , "
                    + "APPLICATION.DESCRIPTION  AS APPLICATIONCODE , SECTION.DESCRIPTION AS SECTIONCODE , "
                    + "PAGE.DESCRIPTION AS PAGECODE, TASK.DESCRIPTION AS TASKCODE  FROM USERROLEAPPLICATION URA, "
                    + "USERAPPLICATIONSECTION UAS, USERSECTIONPAGE USP, USERPAGETASK UPT, USERROLE, APPLICATION, "
                    + "SECTION, PAGE , TASK WHERE URA.USERROLECODE =? and "
                    + "URA.USERROLECODE   = USERROLE.USERROLE AND "
                    + "URA.APPLICATIONCODE  = UAS.APPLICATIONCODE(+) AND "
                    + "URA.APPLICATIONCODE  = APPLICATION.APPLICATIONCODE AND "
                    + "UAS.SECTIONCODE  = SECTION.SECTIONCODE(+) AND "
                    + "UAS.USERROLECODE(+) = ? AND "
                    + "UAS.SECTIONCODE = USP.SECTIONCODE(+) AND "
                    + "USP.PAGECODE = PAGE.PAGECODE(+) AND "
                    + "USP.USERROLECODE(+) = ? AND "
                    + "USP.PAGECODE = UPT.PAGECODE(+) AND "
                    + "UPT.TASKCODE = TASK.TASKCODE(+) AND "
                    + "UPT.USERROLECODE(+)=? "
                    + "ORDER BY USERROLE.USERROLE,APPLICATION.APPLICATIONCODE,SECTION.SECTIONCODE,PAGE.PAGECODE");

            findByUserRoleStat.setString(1, userRoleCode);
            findByUserRoleStat.setString(2, userRoleCode);
            findByUserRoleStat.setString(3, userRoleCode);
            findByUserRoleStat.setString(4, userRoleCode);

            rs = findByUserRoleStat.executeQuery();

            appSecPageTaskList = new ArrayList<UserRolePrivilegeBean>();
            while (rs.next()) {
                UserRolePrivilegeBean appSecPageTaskBean = new UserRolePrivilegeBean();

                appSecPageTaskBean.setUserRoleCode(rs.getString("USERROLECODE"));
                appSecPageTaskBean.setSectionCode(rs.getString("SECTIONCODE"));
                appSecPageTaskBean.setPageCode(rs.getString("PAGECODE"));
                appSecPageTaskBean.setApplicationCode(rs.getString("APPLICATIONCODE"));
                appSecPageTaskBean.setTaskCode(rs.getString("TASKCODE"));

                appSecPageTaskList.add(appSecPageTaskBean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            findByUserRoleStat.close();
        }
        return appSecPageTaskList;
    }

    /**
     * this method return all the user role applications which are assigned to
     * the particular user role
     *
     * @param con
     * @param userRoleCode
     * @return userRoleAppliacationList-List<ApplicationModuleBean>
     * @throws Exception
     */
    public List<ApplicationModuleBean> getUserRoleApplicationByUserRoleCode(Connection con, String userRoleCode) throws Exception {
        PreparedStatement findByUserRoleStat = null;
        try {
            findByUserRoleStat = con.prepareStatement("SELECT ISEC.APPLICATIONCODE, ISEC.DESCRIPTION  "
                    + "FROM USERROLEAPPLICATION IURS, APPLICATION ISEC "
                    + "WHERE IURS.USERROLECODE=? AND ISEC.APPLICATIONCODE = IURS.APPLICATIONCODE ");
            findByUserRoleStat.setString(1, userRoleCode);
            rs = findByUserRoleStat.executeQuery();
            userRoleAppliacationList = new ArrayList<ApplicationModuleBean>();
            while (rs.next()) {
                ApplicationModuleBean userRoleSection = new ApplicationModuleBean();

                userRoleSection.setApplicationCode(rs.getString("APPLICATIONCODE"));
                userRoleSection.setDescription(rs.getString("DESCRIPTION"));
                userRoleAppliacationList.add(userRoleSection);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            findByUserRoleStat.close();
        }
        return userRoleAppliacationList;
    }

    /**
     * this method return all the user role applications which are not assigned
     * to the particular user role
     *
     * @param con
     * @param userRoleCode
     * @return userRoleAppliacationList-List<ApplicationModuleBean>
     * @throws Exception
     */
    public List<ApplicationModuleBean> getUserRoleApplicationNotAssignByUserRoleCode(Connection con, String userRoleCode) throws Exception {
        PreparedStatement findByUserRoleStat = null;
        try {
            findByUserRoleStat = con.prepareStatement("SELECT ISEC.APPLICATIONCODE, ISEC.DESCRIPTION  "
                    + "FROM APPLICATION ISEC "
                    + "WHERE ISEC.APPLICATIONCODE NOT IN (SELECT IURS.APPLICATIONCODE FROM USERROLEAPPLICATION IURS WHERE IURS.USERROLECODE=?) ");

            findByUserRoleStat.setString(1, userRoleCode);
            rs = findByUserRoleStat.executeQuery();

            userRoleAppliacationList = new ArrayList<ApplicationModuleBean>();

            while (rs.next()) {
                ApplicationModuleBean userRoleSection = new ApplicationModuleBean();

                userRoleSection.setApplicationCode(rs.getString("APPLICATIONCODE"));
                userRoleSection.setDescription(rs.getString("DESCRIPTION"));
                userRoleAppliacationList.add(userRoleSection);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            findByUserRoleStat.close();
        }
        return userRoleAppliacationList;
    }

    /**
     * this method insert all user role applications which are assigned.
     *
     * @param con
     * @param userRoleapplication
     * @return resultId -int
     * @throws Exception
     */
    public int insertUserRoleApplication(Connection con, ApplicationModuleBean userRoleapplication) throws Exception {
        int resultId = -1;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO USERROLEAPPLICATION (USERROLECODE,APPLICATIONCODE,LASTUPDATEDUSER,CREATETIME)"
                    + "VALUES (?,?,?,?) ");
            insertStat.setString(1, userRoleapplication.getUserRoleCode());
            insertStat.setString(2, userRoleapplication.getApplicationCode());
            insertStat.setString(3, userRoleapplication.getLastUpdateuser());
            insertStat.setTimestamp(4, new Timestamp(userRoleapplication.getCreatedTime().getTime()));

            resultId = insertStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {

            insertStat.close();
        }
        return resultId;
    }

    /**
     * this method delete all user role applications that are not assigned
     *
     * @param con
     * @param userRoleapplication
     * @return resultId-int
     * @throws Exception
     */
    public int deleteUserRoleApplication(Connection con, ApplicationModuleBean userRoleapplication) throws Exception {
        int resultId = -1;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE USERROLEAPPLICATION WHERE USERROLECODE=? AND APPLICATIONCODE=? ");
            deleteStat.setString(1, userRoleapplication.getUserRoleCode());
            deleteStat.setString(2, userRoleapplication.getApplicationCode());

            resultId = deleteStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {

            deleteStat.close();
        }
        return resultId;
    }

    /**
     * this method return all user role applications which assigned to
     * particular userrole
     *
     * @param con
     * @param userRoleCode
     * @return userRoleAppliacationList-List<ApplicationModuleBean>
     * @throws Exception
     */
    public List<ApplicationModuleBean> getApplicationByUserRoleCode(Connection con, String userRoleCode) throws Exception {
        PreparedStatement findByUserRoleStat = null;
        try {
            findByUserRoleStat = con.prepareStatement("SELECT ISEC.APPLICATIONCODE, ISEC.DESCRIPTION  "
                    + "FROM USERROLEAPPLICATION IURS, APPLICATION ISEC "
                    + "WHERE IURS.USERROLECODE=? AND ISEC.APPLICATIONCODE=IURS.APPLICATIONCODE ");
            findByUserRoleStat.setString(1, userRoleCode);
            rs = findByUserRoleStat.executeQuery();
            userRoleAppliacationList = new ArrayList<ApplicationModuleBean>();
            while (rs.next()) {
                ApplicationModuleBean userRoleSection = new ApplicationModuleBean();

                userRoleSection.setApplicationCode(rs.getString("APPLICATIONCODE"));
                userRoleSection.setDescription(rs.getString("DESCRIPTION"));
                userRoleAppliacationList.add(userRoleSection);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            findByUserRoleStat.close();
        }
        return userRoleAppliacationList;
    }

    /**
     * this method return all the user role application sections that are
     * assigned to the particular application
     *
     * @param con
     * @param userRoleCode
     * @param applicationCode
     * @return sectionList -List<SectionBean>
     * @throws Exception
     */
    public List<SectionBean> getSectionByApplicationAndUserRole(Connection con, String userRoleCode, String applicationCode) throws Exception {
        PreparedStatement findSectionStat = null;
        try {

            findSectionStat = con.prepareStatement("SELECT IP.SECTIONCODE,IP.DESCRIPTION "
                    + "FROM USERAPPLICATIONSECTION ISP, SECTION IP,APPLICATIONSECTION APS "
                    + "WHERE ISP.USERROLECODE=? AND ISP.APPLICATIONCODE=? AND IP.SECTIONCODE=ISP.SECTIONCODE AND "
                    + "APS.SECTIONCODE = ISP.SECTIONCODE AND APS.APPLICATIONCODE = ISP.APPLICATIONCODE");
            findSectionStat.setString(1, userRoleCode);
            findSectionStat.setString(2, applicationCode);

            rs = findSectionStat.executeQuery();
            sectionList = new ArrayList<SectionBean>();
            while (rs.next()) {
                SectionBean section = new SectionBean();
                section.setSectionCode(rs.getString("SECTIONCODE"));
                section.setDescription(rs.getString("DESCRIPTION"));
                section.setUserRoleAppCode(applicationCode);
                sectionList.add(section);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            findSectionStat.close();
        }

        return sectionList;
    }

    /**
     * this method return all user role application sections which are assigned
     * to the particular application
     *
     * @param con
     * @param userRoleCode
     * @param applicationCode
     * @return sectionList -List<SectionBean>
     * @throws Exception
     */
    public List<SectionBean> getSectionByApplicationAndUserRoleNotAssign(Connection con, String userRoleCode, String applicationCode) throws Exception {
        PreparedStatement findSectionStat = null;
        try {

            findSectionStat = con.prepareStatement("SELECT SEC.SECTIONCODE,SEC.DESCRIPTION "
                    + "FROM APPLICATIONSECTION IP,SECTION SEC "
                    + "WHERE IP.SECTIONCODE NOT IN (SELECT ISP.SECTIONCODE FROM USERAPPLICATIONSECTION ISP "
                    + "WHERE  ISP.APPLICATIONCODE=? AND ISP.USERROLECODE=? ) AND IP.APPLICATIONCODE=? AND IP.SECTIONCODE = SEC.SECTIONCODE");

            findSectionStat.setString(1, applicationCode);
            findSectionStat.setString(2, userRoleCode);
            findSectionStat.setString(3, applicationCode);

            rs = findSectionStat.executeQuery();
            sectionList = new ArrayList<SectionBean>();
            while (rs.next()) {
                SectionBean section = new SectionBean();
                section.setSectionCode(rs.getString("SECTIONCODE"));
                section.setDescription(rs.getString("DESCRIPTION"));
                sectionList.add(section);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            findSectionStat.close();
        }

        return sectionList;
    }

    /**
     * this method insert all user role application sections which are assigned
     *
     * @param con
     * @param applicationSection
     * @return resultId - int
     * @throws Exception
     */
    public int insertUserRoleApplicationSections(Connection con, ApplicationSectionBean applicationSection) throws Exception {
        int resultId = -1;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO USERAPPLICATIONSECTION(USERROLECODE,APPLICATIONCODE,SECTIONCODE,LASTUPDATEDUSER,CREATETIME) "
                    + "VALUES (?,?,?,?,?) ");
            insertStat.setString(1, applicationSection.getUserRoleCode());
            insertStat.setString(2, applicationSection.getApplicationCode());
            insertStat.setString(3, applicationSection.getSectionCode());
            insertStat.setString(4, applicationSection.getLastUpdatedUser());
            insertStat.setTimestamp(5, new Timestamp(applicationSection.getCreatedTime().getTime()));

            resultId = insertStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {

            insertStat.close();
        }
        return resultId;
    }

    /**
     * this method delete all userrole application sections that are not
     * assigned..
     *
     * @param con
     * @param applicationSection
     * @return resultId - int
     * @throws Exception
     */
    public int deleteUserRoleApplcationSection(Connection con, ApplicationSectionBean applicationSection) throws Exception {
        int resultId = -1;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE USERAPPLICATIONSECTION WHERE USERROLECODE=? AND SECTIONCODE=? AND APPLICATIONCODE=? ");
            deleteStat.setString(1, applicationSection.getUserRoleCode());
            deleteStat.setString(2, applicationSection.getSectionCode());
            deleteStat.setString(3, applicationSection.getApplicationCode());

            resultId = deleteStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {

            deleteStat.close();
        }
        return resultId;
    }

    /**
     * this method return all userrole sectionpages which are assigned to the
     * particular section
     *
     * @param con
     * @param userRoleCode
     * @param sectionCode
     * @return pageList -List<PageBean>
     * @throws Exception
     */
    public List<PageBean> getPageBySectionAndUserRole(Connection con, String userRoleCode, String sectionCode) throws Exception {
        PreparedStatement findSectionStat = null;
        try {

            findSectionStat = con.prepareStatement("SELECT IP.PAGECODE,IP.DESCRIPTION "
                    + "FROM USERSECTIONPAGE ISP,SECTIONPAGE SP, PAGE IP "
                    + "WHERE ISP.USERROLECODE=? AND ISP.SECTIONCODE=? AND  ISP.SECTIONCODE=SP.SECTIONCODE AND ISP.PAGECODE= SP.PAGECODE AND IP.PAGECODE=SP.PAGECODE");
            findSectionStat.setString(1, userRoleCode);
            findSectionStat.setString(2, sectionCode);

            rs = findSectionStat.executeQuery();
            pageList = new ArrayList<PageBean>();
            while (rs.next()) {

                PageBean page = new PageBean();
                page.setPageCode(rs.getString("PAGECODE"));
                page.setDescription(rs.getString("DESCRIPTION"));
                pageList.add(page);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            findSectionStat.close();
        }

        return pageList;
    }

    /**
     * this method return all userrole sectionpages which are not assigned fro
     * the particular section
     *
     * @param con
     * @param userRoleCode
     * @param sectionCode
     * @return pageList -List<PageBean>
     * @throws Exception
     */
    public List<PageBean> getPageBySectionAndUserRoleNotAssign(Connection con, String userRoleCode, String sectionCode) throws Exception {
        PreparedStatement findSectionStat = null;
        try {

            findSectionStat = con.prepareStatement("SELECT PG.PAGECODE,PG.DESCRIPTION "
                    + "FROM SECTIONPAGE IP,PAGE PG "
                    + "WHERE IP.PAGECODE NOT IN (SELECT ISP.PAGECODE FROM USERSECTIONPAGE ISP "
                    + "WHERE  ISP.SECTIONCODE=? AND ISP.USERROLECODE=? ) AND IP.SECTIONCODE=? AND IP.PAGECODE = PG.PAGECODE");

            findSectionStat.setString(1, sectionCode);
            findSectionStat.setString(2, userRoleCode);
            findSectionStat.setString(3, sectionCode);

            rs = findSectionStat.executeQuery();
            pageList = new ArrayList<PageBean>();
            while (rs.next()) {
                PageBean page = new PageBean();
                page.setPageCode(rs.getString("PAGECODE"));
                page.setDescription(rs.getString("DESCRIPTION"));
                pageList.add(page);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            findSectionStat.close();
        }

        return pageList;
    }

    /**
     * this method insert all userrole sectionpages whish are assigned...
     *
     * @param con
     * @param sectionPageBean
     * @return resultId - int
     * @throws Exception
     */
    public int insertUserRoleSectionPages(Connection con, SectionPageBean sectionPageBean) throws Exception {
        int resultId = -1;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO USERSECTIONPAGE(USERROLECODE,SECTIONCODE,PAGECODE,LASTUPDATEDUSER,CREATETIME) "
                    + "VALUES (?,?,?,?,?) ");

            insertStat.setString(1, sectionPageBean.getUserRoleCode().trim());
            insertStat.setString(2, sectionPageBean.getSectionCode().trim());
            insertStat.setString(3, sectionPageBean.getPageCode().trim());
            insertStat.setString(4, sectionPageBean.getLasUpdatedUser().trim());
            insertStat.setTimestamp(5, new Timestamp(sectionPageBean.getCreatedTime().getTime()));

            resultId = insertStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {

            insertStat.close();
        }
        return resultId;
    }

    /**
     * this method delete all userrole sectionpages which are not assigned..
     *
     * @param con
     * @param sectionPageBean
     * @return resultId -int
     * @throws Exception
     */
    public int deleteUserRoleSectionPages(Connection con, SectionPageBean sectionPageBean) throws Exception {
        int resultId = -1;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE USERSECTIONPAGE WHERE USERROLECODE=? AND SECTIONCODE=? AND PAGECODE=? ");
            deleteStat.setString(1, sectionPageBean.getUserRoleCode());
            deleteStat.setString(2, sectionPageBean.getSectionCode());
            deleteStat.setString(3, sectionPageBean.getPageCode());

            resultId = deleteStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {

            deleteStat.close();
        }
        return resultId;
    }

    /**
     * this method return all userrole pagetasks which are assigned to the
     * particular page
     *
     * @param con
     * @param userRoleCode
     * @param pageCode
     * @return taskList -List<TaskBean>
     * @throws Exception
     */
    public List<TaskBean> getTaskByPageAndUserRole(Connection con, String userRoleCode, String pageCode) throws Exception {
        PreparedStatement findSectionStat = null;
        try {

            findSectionStat = con.prepareStatement("SELECT IP.TASKCODE,IP.DESCRIPTION "
                    + "FROM USERPAGETASK ISP,PAGETASK SP, TASK IP "
                    + "WHERE ISP.USERROLECODE=? AND ISP.PAGECODE=? AND  ISP.PAGECODE=SP.PAGECODE AND ISP.TASKCODE= SP.TASKCODE AND IP.TASKCODE=SP.TASKCODE");
            findSectionStat.setString(1, userRoleCode);
            findSectionStat.setString(2, pageCode);

            rs = findSectionStat.executeQuery();
            taskList = new ArrayList<TaskBean>();
            while (rs.next()) {

                TaskBean task = new TaskBean();
                task.setTaskCode(rs.getString("TASKCODE"));
                task.setDescription(rs.getString("DESCRIPTION"));
                taskList.add(task);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            findSectionStat.close();
        }

        return taskList;
    }

    /**
     * this method return all userrole pagetasks which are not assigned for the
     * particular page
     *
     * @param con
     * @param userRoleCode
     * @param pageCode
     * @return taskList -List<TaskBean>
     * @throws Exception
     */
    public List<TaskBean> getTaskByPageAndUserRoleNotAssign(Connection con, String userRoleCode, String pageCode) throws Exception {
        PreparedStatement findSectionStat = null;
        try {

            findSectionStat = con.prepareStatement("SELECT PG.TASKCODE,PG.DESCRIPTION "
                    + "FROM PAGETASK IP,TASK PG "
                    + "WHERE IP.TASKCODE NOT IN (SELECT ISP.TASKCODE FROM USERPAGETASK ISP "
                    + "WHERE  ISP.PAGECODE=? AND ISP.USERROLECODE=? ) AND IP.PAGECODE=? AND IP.TASKCODE = PG.TASKCODE");

            findSectionStat.setString(1, pageCode);
            findSectionStat.setString(2, userRoleCode);
            findSectionStat.setString(3, pageCode);

            rs = findSectionStat.executeQuery();
            taskList = new ArrayList<TaskBean>();
            while (rs.next()) {
                TaskBean task = new TaskBean();
                task.setTaskCode(rs.getString("TASKCODE"));
                task.setDescription(rs.getString("DESCRIPTION"));
                taskList.add(task);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            findSectionStat.close();
        }

        return taskList;
    }

    /**
     * this method inset all assign userrole pagetasks
     *
     * @param con
     * @param pageTaskBean
     * @return resultId -int
     * @throws Exception
     */
    public int insertUserRolePageTasks(Connection con, PageTaskBean pageTaskBean) throws Exception {
        int resultId = -1;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO USERPAGETASK(USERROLECODE,PAGECODE,TASKCODE,LASTUPDATEDUSER,CREATETIME) "
                    + "VALUES (?,?,?,?,SYSDATE) ");

            insertStat.setString(1, pageTaskBean.getUserRoleCode().trim());
            insertStat.setString(2, pageTaskBean.getPageCode().trim());
            insertStat.setString(3, pageTaskBean.getTaskCode().trim());
            insertStat.setString(4, pageTaskBean.getLastUpdatedUser().trim());
//            insertStat.setTimestamp(5, new Timestamp(pageTaskBean.getCreatedTime().getTime()));

            resultId = insertStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {

            insertStat.close();
        }
        return resultId;
    }

    /**
     * this method delete all unassign userrole pagetasks
     *
     * @param con
     * @param pageTaskBean
     * @return resultId -int
     * @throws Exception
     */
    public int deleteUserRolePageTasks(Connection con, PageTaskBean pageTaskBean) throws Exception {
        int resultId = -1;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE USERPAGETASK WHERE USERROLECODE=? AND PAGECODE=? AND TASKCODE=? ");
            deleteStat.setString(1, pageTaskBean.getUserRoleCode());
            deleteStat.setString(2, pageTaskBean.getPageCode());
            deleteStat.setString(3, pageTaskBean.getTaskCode());

            resultId = deleteStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {

            deleteStat.close();
        }
        return resultId;
    }

    /**
     * this method get all unassign user roles to schema
     *
     * @param con
     * @param schema
     * @return List
     * @throws Exception
     */
    public List<UserRoleBean> getUnassignedUserRolesToSchema(Connection con, String schema) throws Exception {
        PreparedStatement getUnassignedUserRole = null;
        try {
            getUnassignedUserRole = con.prepareStatement("SELECT DISTINCT USERROLE.USERROLE,USERROLE.DESCRIPTION FROM USERROLE INNER JOIN USERSECTIONPAGE ON USERROLE.USERROLE = USERSECTIONPAGE.USERROLECODE WHERE USERROLE.USERROLE NOT IN(SELECT CREDITLIMTSCHEMAUSERROLE.USERROLECODE FROM CREDITLIMTSCHEMAUSERROLE WHERE CREDITLIMTSCHEMAUSERROLE.SCHEMACODE = ?)AND USERSECTIONPAGE.PAGECODE = 'CDCNAP'");
            getUnassignedUserRole.setString(1, schema);

            rs = getUnassignedUserRole.executeQuery();
            userRoleListUnassignedToSchema = new ArrayList<UserRoleBean>();
            while (rs.next()) {

                UserRoleBean tempUserRole = new UserRoleBean();

                tempUserRole.setUserRoleCode(rs.getString("USERROLE"));
                tempUserRole.setDescription(rs.getString("DESCRIPTION"));

                userRoleListUnassignedToSchema.add(tempUserRole);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            rs.close();
            getUnassignedUserRole.close();

        }

        return userRoleListUnassignedToSchema;
    }

    /**
     * this method get all assign user roles to schema
     *
     * @param con
     * @param schema
     * @return List
     * @throws Exception
     */
    public List<UserRoleBean> getAssignedUserRolesToSchema(Connection con, String schema) throws Exception {
        PreparedStatement getAssignedUserRole = null;
        try {
            getAssignedUserRole = con.prepareStatement("SELECT DISTINCT USERROLE.USERROLE,USERROLE.DESCRIPTION FROM USERROLE INNER JOIN USERSECTIONPAGE ON USERROLE.USERROLE = USERSECTIONPAGE.USERROLECODE WHERE USERROLE.USERROLE IN(SELECT CREDITLIMTSCHEMAUSERROLE.USERROLECODE FROM CREDITLIMTSCHEMAUSERROLE WHERE CREDITLIMTSCHEMAUSERROLE.SCHEMACODE = ?)AND USERSECTIONPAGE.PAGECODE = 'CDCNAP'");
            getAssignedUserRole.setString(1, schema);

            rs = getAssignedUserRole.executeQuery();
            userRoleListAssignedToSchema = new ArrayList<UserRoleBean>();
            while (rs.next()) {

                UserRoleBean tempUserRole = new UserRoleBean();

                tempUserRole.setUserRoleCode(rs.getString(1));
                tempUserRole.setDescription(rs.getString(2));

                userRoleListAssignedToSchema.add(tempUserRole);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            rs.close();
            getAssignedUserRole.close();

        }

        return userRoleListAssignedToSchema;
    }

    /**
     * this method insert all user role which are assigned to schema.
     *
     * @param con
     * @param bean
     * @return resultId -int
     * @throws Exception
     */
    public int insertSchemaToUserRole(Connection con, CreditLimitSchemaUserRoleBean bean) throws Exception {
        int resultId = -1;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CREDITLIMTSCHEMAUSERROLE (SCHEMACODE,USERROLECODE,STATUS,LASTUPDATEDUSER) VALUES (?,?,?,?)");

            insertStat.setString(1, bean.getSchemaCode());
            insertStat.setString(2, bean.getUserRoleCode());
            insertStat.setString(3, bean.getStatus());
           
            insertStat.setString(4, bean.getLastUpdatedUser());

            resultId = insertStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {

            insertStat.close();
        }
        return resultId;
    }

    /**
     * this method delete all user role that are not assigned to schema
     *
     * @param con
     * @param bean
     * @return resultId-int
     * @throws Exception
     */
    public int deleteSchemaUserRole(Connection con, CreditLimitSchemaUserRoleBean bean) throws Exception {
        int resultId = -1;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE CREDITLIMTSCHEMAUSERROLE WHERE SCHEMACODE=? AND USERROLECODE=? ");
            deleteStat.setString(1, bean.getSchemaCode());
            deleteStat.setString(2, bean.getUserRoleCode());

            resultId = deleteStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {

            deleteStat.close();
        }
        return resultId;
    }

}
