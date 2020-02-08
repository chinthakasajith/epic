/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SessionValidBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserPasswordBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BankBranchBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

//
//
/**
 *
 * @author ayesh
 */
public class SystemUserPersistance {

    private ResultSet rs = null;
    private Map<SectionBean, List<PageBean>> sectionPageList = null;
    private List<SystemUserBean> sysUserList = null;
    private HashMap<String, String> dataCaptureUserList=null;

    /**
     * this method return user status with corresponding user name
     *
     * @param userName -String
     * @return user status-String
     * @throws SQLException
     * @throws Exception
     */
    public SessionValidBean getUserSatatus(Connection con, String userName) throws SQLException, Exception {

        SessionValidBean sysSessionBean = new SessionValidBean();

        PreparedStatement ps = null;
        try {
            String query = "SELECT sessionid, status FROM SYSTEMUSER WHERE USERNAME=?";
            ps = con.prepareStatement(query);
            ps.setString(1, userName);
            rs = ps.executeQuery();

            while (rs.next()) {
                sysSessionBean.setStatus(rs.getString("status"));
                sysSessionBean.setSessionID(rs.getString("sessionid"));
//                System.out.println(sysSessionBean.getSessionID() + " ^^^^^^^^^^^^^^^^^^^^^^^^^");
            }

            return sysSessionBean;
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

    public SystemUserBean validateCMSSysUser(Connection con, SystemUserBean cmsSysUser) throws Exception {

        SystemUserBean valideUser = null;
        PreparedStatement validateStat = null;
        try {
            String sql = "SELECT IUR.LEVELID,ISU.PASSWORD, ISU.USERNAME, ISU.USERROLECODE, IST.DESCRIPTION AS STATUSCODE, "
                    + "ISU.EXPIRYDATE, ISU.LASTUPDATEDUSER, ISU.CREATEDTIME, ISU.LASTLOGGEDDATETIME, ISU.INITIALLOGINSTATUS "
                    + "FROM SYSTEMUSER ISU,USERROLE IUR,STATUS IST "
                    + "WHERE ISU.USERNAME=? "
                    + "AND ISU.PASSWORD=? "
                    + "AND(ISU.USERROLECODE=IUR.USERROLE AND IST.STATUSCODE=ISU.STATUS) ";

            validateStat = con.prepareStatement(sql);
            validateStat.setString(1, cmsSysUser.getUserName());
            validateStat.setString(2, cmsSysUser.getPassword());
            rs = validateStat.executeQuery();
            while (rs.next()) {
                valideUser = new SystemUserBean();

                valideUser.setPassword(rs.getString("PASSWORD"));
                valideUser.setUserName(rs.getString("USERNAME"));
                valideUser.setUserRoleCode(rs.getString("USERROLECODE"));
                valideUser.setStatusCode(rs.getString("STATUSCODE"));
                valideUser.setExpiryDate(rs.getDate("EXPIRYDATE"));
                valideUser.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                valideUser.setCreatedTime(rs.getDate("CREATEDTIME"));
                valideUser.setLastLoggedDateTime(rs.getDate("LASTLOGGEDDATETIME"));
                valideUser.setInitialloginstatus(rs.getString("INITIALLOGINSTATUS"));
                valideUser.setLevelId(rs.getInt("LEVELID"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            validateStat.close();
        }

        return valideUser;

    }

    public int updateCMSSystemUserSessId(Connection con, SystemUserBean sysUser) throws Exception {
        int resultID = -1;
        PreparedStatement updateSysUserStat = null;
        try {
            updateSysUserStat = con.prepareStatement("UPDATE SYSTEMUSER SET "
                    + "SESSIONID=?  ,"
                    + "LOGINATTEMPT=0 ,  "
                    + "LASTLOGGEDDATETIME=SYSDATE  "
                    + "WHERE USERNAME=? ");

            updateSysUserStat.setString(1, sysUser.getCurrentSessionId());
            updateSysUserStat.setString(2, sysUser.getUserName());

            resultID = updateSysUserStat.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            updateSysUserStat.close();
        }

        return resultID;
    }

    public List<ApplicationModuleBean> getApplicationList(Connection con, String userRoleCode) throws Exception {
        List<ApplicationModuleBean> applicationList = new ArrayList<ApplicationModuleBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT DISTINCT A.APPLICATIONCODE,A.DESCRIPTION,A.STATUS,A.CREATETIME FROM APPLICATION A, USERROLEAPPLICATION AV WHERE USERROLECODE =? AND AV.APPLICATIONCODE = A.APPLICATIONCODE AND A.STATUS='ACT'"
                    + "ORDER BY A.CREATETIME ASC ";
            ps = con.prepareStatement(query);

            ps.setString(1, userRoleCode);
            rs = ps.executeQuery();

            while (rs.next()) {
                ApplicationModuleBean bean = new ApplicationModuleBean();
                bean.setApplicationCode(rs.getString("APPLICATIONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setStatus(rs.getString("STATUS"));

                applicationList.add(bean);
            }

            return applicationList;
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

    public Map<SectionBean, List<PageBean>> getSectionPageList(Connection con, String userRole, String application) throws Exception {

        PreparedStatement sectionPageStat = null;
        sectionPageList = new Hashtable<SectionBean, List<PageBean>>();
        try {
            sectionPageStat = con.prepareStatement("SELECT USP.USERROLECODE,USP.SECTIONCODE,SEC.DESCRIPTION,SEC.SORTKEY as sectionsortkey,PG.PAGECODE,PG.DESCRIPTION AS PAGEDESCRIPTION,PG.URL,PG.ROOT,PG.SORTKEY "
                    + "FROM USERAPPLICATIONSECTION UAS,APPLICATION APP,USERSECTIONPAGE USP,SECTION SEC,PAGE PG,STATUS IST,USERROLE UR "
                    + "WHERE UAS.USERROLECODE=? AND UAS.APPLICATIONCODE=? AND UAS.APPLICATIONCODE=APP.APPLICATIONCODE AND UAS.SECTIONCODE=USP.SECTIONCODE AND USP.SECTIONCODE=SEC.SECTIONCODE AND USP.PAGECODE=PG.PAGECODE AND "
                    + "APP.STATUS=IST.STATUSCODE AND SEC.STATUS=IST.STATUSCODE AND PG.STATUS=IST.STATUSCODE AND USP.USERROLECODE= UR.USERROLE AND USP.USERROLECODE=? "
                    + "ORDER BY SEC.SORTKEY,SEC.SECTIONCODE, PG.SORTKEY ");

            sectionPageStat.setString(1, userRole);
            sectionPageStat.setString(2, application);
            sectionPageStat.setString(3, userRole);
            rs = sectionPageStat.executeQuery();
            String currentSection = "";
            List<PageBean> pageList = null;
            SectionBean section = null;
            while (rs.next()) {
                if (!rs.getString("SECTIONCODE").equals(currentSection)) {
                    currentSection = rs.getString("SECTIONCODE");
                    if (pageList != null && section != null) {
                        sectionPageList.put(section, pageList);
                        pageList = null;
                        section = null;
                    }
                    section = new SectionBean();
                    section.setSectionCode(rs.getString("SECTIONCODE"));
                    section.setDescription(rs.getString("DESCRIPTION"));
                    section.setSortKey(rs.getString("sectionsortkey"));

                    pageList = new ArrayList<PageBean>();
                    PageBean page = new PageBean();
                    page.setPageCode(rs.getString("PAGECODE"));
                    page.setDescription(rs.getString("PAGEDESCRIPTION"));
                    page.setRoot(rs.getString("ROOT"));
                    page.setUrl(rs.getString("URL"));
                    page.setSortKey(rs.getString("SORTKEY"));

                    pageList.add(page);

                } else {

                    PageBean page = new PageBean();
                    page.setPageCode(rs.getString("PAGECODE"));
                    page.setSortKey(rs.getString("SORTKEY"));
                    page.setDescription(rs.getString("PAGEDESCRIPTION"));
                    page.setRoot(rs.getString("ROOT"));
                    page.setUrl(rs.getString("URL"));

                    pageList.add(page);
                }
            }
            if (pageList != null && section != null) {
                sectionPageList.put(section, pageList);
                pageList = null;
                section = null;
            }
            return sectionPageList;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    sectionPageStat.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public List<SystemUserBean> getAllSysUsersExceptDeleted(Connection con) throws Exception {
        PreparedStatement getAllUserStat = null;
        try {

            getAllUserStat = con.prepareStatement("SELECT (SELECT IUR.LEVELID FROM USERROLE IUR WHERE IUR.USERROLE=ISU.DUALAUTHUSERROLE) AS LEVELID,ISU.USERNAME,ISU.USERROLECODE , IUR.DESCRIPTION AS USERROLEDES  , ISU.DUALAUTHUSERROLE , (SELECT IUR.DESCRIPTION FROM USERROLE IUR WHERE IUR.USERROLE=ISU.DUALAUTHUSERROLE) AS DUSERROLEDES , ISU.STATUS , IST.DESCRIPTION AS STATUSDES ,ISU.FIRSTNAME ,ISU.MIDDLENAME  , "
                    + "ISU.LASTNAME  ,ISU.ADDRESS1 ,ISU.ADDRESS2 ,ISU.ADDRESS3 , ISU.CITY , ISU.NIC  , ISU.CONTACTNUMBER  , ISU.EMAILADDRESS  , ISU.GENDER  , ISU.EXPIRYDATE  , TO_CHAR(ISU.EXPIRYDATE, 'YYYY-MM-DD') AS EXPDATESTR, ISU.LASTUPDATEDUSER   ,  ISU.CREATEDTIME, "
                    + "ISU.INITIALS, ISU.BANKNAME,ISU.BRANCHNAME,ISU.DESIGNATION,ISU.SERVICEID,ISU.NIC,ISU.DATEOFBIRTH, ISU.TITLE,ISU.GENDER, "
                    + "ISU.ISEMAILSENT,ISU.INITIALLOGINSTATUS,ISU.ISLOCKEDFORAUTH "
                    + "FROM SYSTEMUSER ISU,  USERROLE IUR ,  STATUS IST "
                    + "WHERE ISU.USERROLECODE=IUR.USERROLE AND IST.STATUSCODE=ISU.STATUS AND NOT IST.STATUSCODE='UDEL' ");

            rs = getAllUserStat.executeQuery();
            sysUserList = new ArrayList<SystemUserBean>();
            while (rs.next()) {
                SystemUserBean foundSysUser = new SystemUserBean();
                foundSysUser.setUserName(rs.getString("USERNAME"));
                foundSysUser.setUserRoleCode(rs.getString("USERROLECODE"));
                foundSysUser.setUserRoleDes(rs.getString("USERROLEDES"));
                foundSysUser.setDualUserRoleCode(rs.getString("DUALAUTHUSERROLE"));
                foundSysUser.setDualUserRoleDes(rs.getString("DUSERROLEDES"));
                foundSysUser.setStatusCode(rs.getString("STATUS"));
                foundSysUser.setStatusDes(rs.getString("STATUSDES"));
                foundSysUser.setFirstName(rs.getString("FIRSTNAME"));
                foundSysUser.setMiddleName(rs.getString("MIDDLENAME"));
                foundSysUser.setLastName(rs.getString("LASTNAME"));
                foundSysUser.setAddress1(rs.getString("ADDRESS1"));
                foundSysUser.setAddress2(rs.getString("ADDRESS2"));
                foundSysUser.setCity(rs.getString("CITY"));
                foundSysUser.setMobile(rs.getString("CONTACTNUMBER"));
                foundSysUser.setTelNo(rs.getString("CONTACTNUMBER"));
                foundSysUser.setEmail(rs.getString("EMAILADDRESS"));
                foundSysUser.setExpiryDateToString(this.convertStringDate(rs.getDate("EXPIRYDATE")));
                foundSysUser.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                foundSysUser.setCreatedTime(rs.getDate("CREATEDTIME"));
                foundSysUser.setInitials(rs.getString("INITIALS"));
                foundSysUser.setBankname(rs.getString("BANKNAME"));
                foundSysUser.setBranchname(rs.getString("BRANCHNAME"));
                foundSysUser.setDesignation(rs.getString("DESIGNATION"));
                foundSysUser.setServiseid(rs.getString("SERVICEID"));
                foundSysUser.setNic(rs.getString("NIC"));
                foundSysUser.setBirthday(rs.getString("DATEOFBIRTH"));
                foundSysUser.setTitle(rs.getString("TITLE"));
                foundSysUser.setGender(rs.getString("GENDER"));
                if (rs.getDate("DATEOFBIRTH") != null) {
                    foundSysUser.setBirthday(this.convertStringDate(rs.getDate("DATEOFBIRTH")));
                } else {
                    foundSysUser.setBirthday("");
                }
                foundSysUser.setIsEmailSent(rs.getString("ISEMAILSENT"));
                foundSysUser.setIslockedforauth(rs.getString("ISLOCKEDFORAUTH"));
                foundSysUser.setLevelId(rs.getInt("LEVELID"));

                sysUserList.add(foundSysUser);

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    getAllUserStat.close();
                }
            } catch (Exception e) {
                throw e;
            }

        }
        return sysUserList;
    }

    public List<SystemUserBean> getAllSysUsersByStatus(Connection con, String status1, String status2, String status3) throws Exception {
        PreparedStatement getAllUserStatByUser = null;
        try {

            getAllUserStatByUser = con.prepareStatement("SELECT (SELECT IUR.LEVELID FROM USERROLE IUR WHERE IUR.USERROLE=ISU.DUALAUTHUSERROLE) AS LEVELID,ISU.USERSTATUS,ISU.USERNAME,ISU.USERROLECODE ,IUR.DESCRIPTION  AS USERROLEDES  , ISU.DUALAUTHUSERROLE , (SELECT IUR.DESCRIPTION FROM USERROLE IUR WHERE IUR.USERROLE=ISU.DUALAUTHUSERROLE) AS DUSERROLEDES , ISU.STATUS , IST.DESCRIPTION AS STATUSDES ,ISU.FIRSTNAME, "
                    + "ISU.LASTNAME  , ISU.NIC  , ISU.CONTACTNUMBER  , ISU.EMAILADDRESS  , ISU.GENDER  , ISU.EXPIRYDATE  , TO_CHAR(ISU.EXPIRYDATE, 'yyyy-MM-dd') AS EXPDATESTR, "
                    + "ISU.INITIALS, ISU.BANKNAME,ISU.BRANCHNAME,ISU.DESIGNATION,ISU.SERVICEID,ISU.DATEOFBIRTH, ISU.TITLE, "
                    + "ISU.REQUSER,ISU.REQDATETIME,ISU.AUTHUSER,ISU.AUTHDATETIME,ISU.REJECTREMARK "
                    + "FROM SYSTEMUSERREQUEST ISU,  USERROLE IUR ,  STATUS IST "
                    + "WHERE ISU.USERROLECODE=IUR.USERROLE AND IST.STATUSCODE=ISU.STATUS AND ISU.STATUS IN (?,?,?) ");

            getAllUserStatByUser.setString(1, status1);
            getAllUserStatByUser.setString(2, status2);
            getAllUserStatByUser.setString(3, status3);
            rs = getAllUserStatByUser.executeQuery();

            sysUserList = new ArrayList<SystemUserBean>();
            while (rs.next()) {
                SystemUserBean foundSysUser = new SystemUserBean();
                foundSysUser.setUserName(rs.getString("USERNAME"));
                foundSysUser.setUserRoleCode(rs.getString("USERROLECODE"));
                foundSysUser.setUserRoleDes(rs.getString("USERROLEDES"));
                foundSysUser.setDualUserRoleCode(rs.getString("DUALAUTHUSERROLE"));
                foundSysUser.setDualUserRoleDes(rs.getString("DUSERROLEDES"));
                foundSysUser.setStatusCode(rs.getString("STATUS"));
                foundSysUser.setStatusDes(rs.getString("STATUSDES"));
                foundSysUser.setFirstName(rs.getString("FIRSTNAME"));
                foundSysUser.setLastName(rs.getString("LASTNAME"));
                foundSysUser.setMobile(rs.getString("CONTACTNUMBER"));
                foundSysUser.setTelNo(rs.getString("CONTACTNUMBER"));
                foundSysUser.setEmail(rs.getString("EMAILADDRESS"));
                //foundSysUser.setExpiryDateToString(rs.getString("EXPDATESTR"));
                foundSysUser.setInitials(rs.getString("INITIALS"));
                foundSysUser.setBankname(rs.getString("BANKNAME"));
                foundSysUser.setBranchname(rs.getString("BRANCHNAME"));
                foundSysUser.setDesignation(rs.getString("DESIGNATION"));
                foundSysUser.setServiseid(rs.getString("SERVICEID"));
                foundSysUser.setNic(rs.getString("NIC"));
                foundSysUser.setBirthday(rs.getString("DATEOFBIRTH"));
                foundSysUser.setTitle(rs.getString("TITLE"));
                foundSysUser.setGender(rs.getString("GENDER"));
                if (rs.getDate("DATEOFBIRTH") != null) {
                    foundSysUser.setBirthday(this.convertStringDate(rs.getDate("DATEOFBIRTH")));
                } else {
                    foundSysUser.setBirthday("");
                }
                foundSysUser.setExpiryDateToString(this.convertStringDate(rs.getDate("EXPIRYDATE")));
                foundSysUser.setRequestedUser(rs.getString("REQUSER"));
                foundSysUser.setRequestedDate(this.convertStringDate(rs.getDate("REQDATETIME")));
                foundSysUser.setAuthorizedUser(rs.getString("AUTHUSER"));
                foundSysUser.setAuthorizedData(this.convertStringDate(rs.getDate("AUTHDATETIME")));
                foundSysUser.setRejectRemark(rs.getString("REJECTREMARK"));
                foundSysUser.setUserstatus(rs.getString("USERSTATUS"));
                foundSysUser.setLevelId(rs.getInt("LEVELID"));
                sysUserList.add(foundSysUser);

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    getAllUserStatByUser.close();
                }
            } catch (Exception e) {
                throw e;
            }

        }
        return sysUserList;
    }

    public List<SystemUserBean> getResetPasswordReq(Connection con) throws Exception {
        PreparedStatement getAllUserStatByUser = null;
        try {

            getAllUserStatByUser = con.prepareStatement("SELECT IUR.LEVELID,SU.ISEMAILSENT,ISU.USERNAME,ISU.REQUSERDUALAUTHROLE ,SU.FIRSTNAME,SU.LASTNAME,IUR.DESCRIPTION AS USERROLEDES  ,ISU.STATUS , IST.DESCRIPTION AS STATUSDES , "
                    + "SU.EMAILADDRESS ,TO_CHAR(SU.EXPIRYDATE, 'YYYY-MM-DD') AS EXPDATESTR, "
                    + "ISU.REQUSER,ISU.REQDATETIME,ISU.AUTHUSER,ISU.AUTHDATETIME,ISU.REJECTREMARK "
                    + "FROM SYSTEMUSER SU,USERPASSWORDREQUEST ISU,  USERROLE IUR ,  STATUS IST "
                    + "WHERE SU.USERNAME=ISU.USERNAME AND ISU.REQUSERDUALAUTHROLE=IUR.USERROLE AND IST.STATUSCODE=ISU.STATUS AND ISU.STATUS='PWRS' ");

            rs = getAllUserStatByUser.executeQuery();

            sysUserList = new ArrayList<SystemUserBean>();
            while (rs.next()) {
                SystemUserBean foundSysUser = new SystemUserBean();
                foundSysUser.setUserName(rs.getString("USERNAME"));
                foundSysUser.setUserRoleCode(rs.getString("REQUSERDUALAUTHROLE"));
                foundSysUser.setUserRoleDes(rs.getString("USERROLEDES"));
                foundSysUser.setStatusCode(rs.getString("STATUS"));
                foundSysUser.setFirstName(rs.getString("FIRSTNAME"));
                foundSysUser.setLastName(rs.getString("LASTNAME"));
                foundSysUser.setStatusDes(rs.getString("STATUSDES"));
                foundSysUser.setEmail(rs.getString("EMAILADDRESS"));
                foundSysUser.setExpiryDateToString(rs.getString("EXPDATESTR"));
                foundSysUser.setRequestedUser(rs.getString("REQUSER"));
                foundSysUser.setRequestedDate(this.convertStringDate(rs.getDate("REQDATETIME")));
                foundSysUser.setAuthorizedUser(rs.getString("AUTHUSER"));
                foundSysUser.setAuthorizedData(this.convertStringDate(rs.getDate("AUTHDATETIME")));
                foundSysUser.setRejectRemark(rs.getString("REJECTREMARK"));
                foundSysUser.setIsEmailSent(rs.getString("ISEMAILSENT"));
                foundSysUser.setLevelId(rs.getInt("LEVELID"));
                sysUserList.add(foundSysUser);

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    getAllUserStatByUser.close();
                }
            } catch (Exception e) {
                throw e;
            }

        }
        return sysUserList;
    }

    public List<String> getTasks(Connection con, String userRoleCode, String pageCode) throws Exception {

        List<String> tasklist = new ArrayList<String>();
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT T.TASKCODE,T.DESCRIPTION FROM USERPAGETASK UPT ,PAGETASK PT,TASK T "
                    + "WHERE UPT.TASKCODE=PT.TASKCODE AND PT.TASKCODE=T.TASKCODE AND "
                    + "UPT.PAGECODE=PT.PAGECODE AND UPT.USERROLECODE = ? AND UPT.PAGECODE = ? ");

            getAllByCatStat.setString(1, userRoleCode);
            getAllByCatStat.setString(2, pageCode);
            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                tasklist.add(rs.getString("TASKCODE"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return tasklist;
    }

    public int insertSysUser(Connection con, SystemUserBean userBean) throws Exception {
        int resultId;
        PreparedStatement insertSysUserStat = null;
        try {
            insertSysUserStat = con.prepareStatement("INSERT INTO SYSTEMUSER "
                    + "(USERNAME,PASSWORD,USERROLECODE,STATUS, "
                    + "DUALAUTHUSERROLE,TITLE,INITIALS,FIRSTNAME,LASTNAME,BANKNAME,BRANCHNAME,DESIGNATION,SERVICEID,CONTACTNUMBER,NIC,EMAILADDRESS,GENDER,LASTUPDATEDUSER,DATEOFBIRTH,EXPIRYDATE,ISEMAILSENT,INITIALLOGINSTATUS,ISLOCKEDFORAUTH,CREATEDTIME,LASTUPDATEDTIME) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE) ");

            insertSysUserStat.setString(1, userBean.getUserName());
            insertSysUserStat.setString(2, userBean.getPassword());
            insertSysUserStat.setString(3, userBean.getUserRoleCode());
            insertSysUserStat.setString(4, userBean.getStatusCode());
            insertSysUserStat.setString(5, userBean.getDualUserRoleCode());
            insertSysUserStat.setString(6, userBean.getTitle());
            insertSysUserStat.setString(7, userBean.getInitials());
            insertSysUserStat.setString(8, userBean.getFirstName());
            insertSysUserStat.setString(9, userBean.getLastName());
            insertSysUserStat.setString(10, userBean.getBankname());
            insertSysUserStat.setString(11, userBean.getBranchname());
            insertSysUserStat.setString(12, userBean.getDesignation());
            insertSysUserStat.setString(13, userBean.getServiseid());
            insertSysUserStat.setString(14, userBean.getTelNo());
            insertSysUserStat.setString(15, userBean.getNic());
            insertSysUserStat.setString(16, userBean.getEmail());
            insertSysUserStat.setString(17, userBean.getGender());
            insertSysUserStat.setString(18, userBean.getLastUpdatedUser());
            if (userBean.getBirthday() != null && !userBean.getBirthday().trim().isEmpty()) {
                insertSysUserStat.setTimestamp(19, new Timestamp(this.stringTodate(userBean.getBirthday()).getTime()));
            } else {
                insertSysUserStat.setTimestamp(19, null);
            }
            insertSysUserStat.setTimestamp(20, new Timestamp(this.stringTodate(userBean.getExpiryDateToString()).getTime()));

            insertSysUserStat.setString(21, userBean.getIsEmailSent());
            insertSysUserStat.setString(22, userBean.getInitialloginstatus());
            insertSysUserStat.setString(23, userBean.getIslockedforauth());
            resultId = insertSysUserStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                insertSysUserStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int insertSysUserRequest(Connection con, SystemUserBean userBean) throws Exception {
        int resultId;
        PreparedStatement insertSysUserStat = null;
        try {
            insertSysUserStat = con.prepareStatement("INSERT INTO SYSTEMUSERREQUEST "
                    + "(USERNAME,USERROLECODE,STATUS, "
                    + "DUALAUTHUSERROLE,TITLE,INITIALS,FIRSTNAME,LASTNAME,BANKNAME,BRANCHNAME,DESIGNATION,SERVICEID,CONTACTNUMBER,NIC,EMAILADDRESS,GENDER,DATEOFBIRTH,EXPIRYDATE,REQUSER,REQDATETIME,LASTUPDATEDUSER,CREATEDDATETIME,USERSTATUS) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSTIMESTAMP,?,SYSTIMESTAMP,?) ");
            insertSysUserStat.setString(1, userBean.getUserName());
            insertSysUserStat.setString(2, userBean.getUserRoleCode());
            insertSysUserStat.setString(3, userBean.getStatusCode());
            insertSysUserStat.setString(4, userBean.getDualUserRoleCode());
            insertSysUserStat.setString(5, userBean.getTitle());
            insertSysUserStat.setString(6, userBean.getInitials());
            insertSysUserStat.setString(7, userBean.getFirstName());
            insertSysUserStat.setString(8, userBean.getLastName());
            insertSysUserStat.setString(9, userBean.getBankname());
            insertSysUserStat.setString(10, userBean.getBranchname());
            insertSysUserStat.setString(11, userBean.getDesignation());
            insertSysUserStat.setString(12, userBean.getServiseid());
            insertSysUserStat.setString(13, userBean.getTelNo());
            insertSysUserStat.setString(14, userBean.getNic());
            insertSysUserStat.setString(15, userBean.getEmail());
            insertSysUserStat.setString(16, userBean.getGender());
            if (userBean.getBirthday() != null && !userBean.getBirthday().trim().isEmpty()) {
                insertSysUserStat.setTimestamp(17, new Timestamp(this.stringTodate(userBean.getBirthday()).getTime()));
            } else {
                insertSysUserStat.setTimestamp(17, null);
            }
            if (userBean.getExpiryDateToString() != null && !userBean.getExpiryDateToString().trim().isEmpty()) {
                insertSysUserStat.setTimestamp(18, new Timestamp(this.stringTodate(userBean.getExpiryDateToString()).getTime()));
            } else {
                insertSysUserStat.setTimestamp(18, null);
            }

            //added for dual user authentication
            insertSysUserStat.setString(19, userBean.getRequestedUser());
            insertSysUserStat.setString(20, userBean.getLastUpdatedUser());
            insertSysUserStat.setString(21, userBean.getUserstatus());

            resultId = insertSysUserStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                insertSysUserStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int insertUserPasswordRequest(Connection con, SystemUserBean userBean) throws Exception {
        int resultId;
        PreparedStatement insertSysUserStat = null;
        try {
            insertSysUserStat = con.prepareStatement("INSERT INTO USERPASSWORDREQUEST "
                    + "(USERNAME,PASSWORD,REQUSER, "
                    + "REQUSERDUALAUTHROLE,REQDATETIME,STATUS) "
                    + "VALUES (?,?,?,?,SYSTIMESTAMP,?) ");
            insertSysUserStat.setString(1, userBean.getUserName());
            insertSysUserStat.setString(2, userBean.getPassword());
            insertSysUserStat.setString(3, userBean.getRequestedUser());
            insertSysUserStat.setString(4, userBean.getDualUserRoleCode());
            insertSysUserStat.setString(5, userBean.getStatusCode());

            resultId = insertSysUserStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                insertSysUserStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int insertToUserPassword(Connection con, SystemUserBean userBean) throws Exception {
        int resultID = -1;
        PreparedStatement insertUserpassword = null;
        try {
            //Insert data to user password table
            insertUserpassword = con.prepareStatement("INSERT INTO USERPASSWORD "
                    + "(USERNAME,PASSWORD,LASTUPDATEDUSER,LASTUPDATEDDATE,CREATEDDATE) "
                    + "VALUES (?,?,?,?,?)");
            insertUserpassword.setString(1, userBean.getUserName());
            insertUserpassword.setString(2, userBean.getPassword());
            insertUserpassword.setString(3, userBean.getLastUpdatedUser());
            insertUserpassword.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            insertUserpassword.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            resultID = insertUserpassword.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            insertUserpassword.close();
        }
        return resultID;
    }

    public int updateSystemUser(Connection con, SystemUserBean userBean) throws Exception {
        int resultID = -1;
        PreparedStatement updateSysUserStat = null;
        try {
            String sql = "";

            if (userBean.getStatusCode().equals(StatusVarList.ACTIVE_STATUS)) {
                sql = "UPDATE SYSTEMUSER SET "
                        + "USERROLECODE=?,STATUS=?,DUALAUTHUSERROLE=?,TITLE=?, "
                        + "INITIALS=?,FIRSTNAME=?,LASTNAME=?,BANKNAME=?,BRANCHNAME=?,DESIGNATION=?,SERVICEID=?,CONTACTNUMBER=?,NIC=?, EMAILADDRESS=?,GENDER=?,DATEOFBIRTH =?,EXPIRYDATE=?, "
                        + "LASTUPDATEDUSER=? ,LASTLOGGEDDATETIME=SYSDATE "
                        + "WHERE USERNAME=? ";
            } else if (userBean.getStatusCode().equals(StatusVarList.DA_REQUSET_INITIATE)) {
                sql = "UPDATE SYSTEMUSER SET "
                        + "LASTUPDATEDUSER=? ,LASTLOGGEDDATETIME=SYSDATE, "
                        + "ISLOCKEDFORAUTH=? "
                        + "WHERE USERNAME=? ";
            } else {
                sql = "UPDATE SYSTEMUSER SET "
                        + "USERROLECODE=?,STATUS=?,DUALAUTHUSERROLE=?,TITLE=?, "
                        + "INITIALS=?,FIRSTNAME=?,LASTNAME=?,BANKNAME=?,BRANCHNAME=?,DESIGNATION=?,SERVICEID=?,CONTACTNUMBER=?,NIC=?, EMAILADDRESS=?,GENDER=?,DATEOFBIRTH =?,EXPIRYDATE=?, "
                        + "LASTUPDATEDUSER=?, "
                        + "AUTHORIZEDUSER=?,AUTHORIZEDDATE=SYSDATE, "
                        + "REJECTREMARK=? "
                        + "WHERE USERNAME=? ";
            }
            updateSysUserStat = con.prepareStatement(sql);

            if (userBean.getStatusCode().equals(StatusVarList.DA_REQUSET_INITIATE)) {
                updateSysUserStat.setString(1, userBean.getLastUpdatedUser());
                updateSysUserStat.setString(2, userBean.getIslockedforauth());
                updateSysUserStat.setString(3, userBean.getUserName());
            } else {
                updateSysUserStat.setString(1, userBean.getUserRoleCode());
                updateSysUserStat.setString(2, userBean.getUserstatus());
                updateSysUserStat.setString(3, userBean.getDualUserRoleCode());
                updateSysUserStat.setString(4, userBean.getTitle());
                updateSysUserStat.setString(5, userBean.getInitials());
                updateSysUserStat.setString(6, userBean.getFirstName());
                updateSysUserStat.setString(7, userBean.getLastName());
                updateSysUserStat.setString(8, userBean.getBankname());
                updateSysUserStat.setString(9, userBean.getBranchname());
                updateSysUserStat.setString(10, userBean.getDesignation());
                updateSysUserStat.setString(11, userBean.getServiseid());
                updateSysUserStat.setString(12, userBean.getTelNo());
                updateSysUserStat.setString(13, userBean.getNic());
                updateSysUserStat.setString(14, userBean.getEmail());
                updateSysUserStat.setString(15, userBean.getGender());
                if (userBean.getBirthday() != null && !userBean.getBirthday().trim().isEmpty()) {
                    updateSysUserStat.setTimestamp(16, new Timestamp(this.stringTodate(userBean.getBirthday()).getTime()));
                } else {
                    updateSysUserStat.setTimestamp(16, null);
                }
                updateSysUserStat.setTimestamp(17, new Timestamp(this.stringTodate(userBean.getExpiryDateToString()).getTime()));
                updateSysUserStat.setString(18, userBean.getLastUpdatedUser());
                updateSysUserStat.setString(19, userBean.getUserName());
            }

            resultID = updateSysUserStat.executeUpdate();

            if (userBean.getAuthorizedUser() != null && !userBean.getAuthorizedUser().isEmpty()) {
                //indicate user approval
                resultID++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            updateSysUserStat.close();
        }

        return resultID;
    }

    public int updateSystemUserRequest(Connection con, SystemUserBean userBean) throws Exception {
        int resultID = -1;
        PreparedStatement updateSysUserStat = null;
        try {

            String sql = "UPDATE SYSTEMUSERREQUEST SET "
                    + "STATUS=?, "
                    + "AUTHUSER=?,AUTHDATETIME=SYSDATE, "
                    + "REJECTREMARK=?,LASTUPDATEDUSER=? "
                    + "WHERE USERNAME=? AND STATUS IN ('RQIN','ARIN') ";

            updateSysUserStat = con.prepareStatement(sql);

            updateSysUserStat.setString(1, userBean.getStatusCode());
            updateSysUserStat.setString(2, userBean.getAuthorizedUser());
            updateSysUserStat.setString(3, userBean.getRejectRemark());
            updateSysUserStat.setString(4, userBean.getAuthorizedUser());
            updateSysUserStat.setString(5, userBean.getUserName());

            resultID = updateSysUserStat.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            updateSysUserStat.close();
        }

        return resultID;
    }

    public int deleteSystemUserRequest(Connection con, SystemUserBean userBean) throws Exception {
        int result;
        PreparedStatement deleteSysUserStat = null;
        try {
            deleteSysUserStat = con.prepareStatement("DELETE FROM SYSTEMUSERREQUEST WHERE USERNAME=? ");
            deleteSysUserStat.setString(1, userBean.getUserName());
            result = deleteSysUserStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            deleteSysUserStat.close();
        }

        return result;
    }

    public int deleteSystemUser(Connection con, SystemUserBean userBean) throws Exception {
        int result;
        PreparedStatement deleteSysUserStat = null;
        try {
            deleteSysUserStat = con.prepareStatement("UPDATE SYSTEMUSER SET "
                    + "STATUS=? "
                    + "WHERE USERNAME=? ");
            deleteSysUserStat.setString(1, userBean.getStatusCode());
            deleteSysUserStat.setString(2, userBean.getUserName());
            result = deleteSysUserStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            deleteSysUserStat.close();
        }

        return result;
    }

    public Date stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = dateFormat.parse(dateString);

        return convertedDate;

    }

    private String convertStringDate(java.sql.Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return date != null ? (dateFormat.format(date)) : "";
    }

    public int changePassword(Connection con, SystemUserBean userBean, boolean isOtherUser) throws Exception {
        int resultID = -1;
        PreparedStatement ps = null;
        String currentPassword = "";
        int success = -1;
        try {
            String selectQuery = "SELECT USERNAME,PASSWORD FROM SYSTEMUSER WHERE USERNAME=?";

            ps = con.prepareStatement(selectQuery);
            ps.setString(1, userBean.getUserName());

            rs = ps.executeQuery();

            while (rs.next()) {
                currentPassword = rs.getString("PASSWORD");
            }

            if (currentPassword.equals("")) {
                //*******************no such user
            } else if (currentPassword.equals(userBean.getCurrentPassword()) || isOtherUser) { //isOtherUser=true means password reset by a Super user.

                String updateQuery = "UPDATE SYSTEMUSER SET PASSWORD=? ,LOGINATTEMPT=0 WHERE USERNAME=? AND STATUS='ACT'";

                ps = con.prepareStatement(updateQuery);
                ps.setString(1, userBean.getPassword());
                ps.setString(2, userBean.getUserName());

                success = ps.executeUpdate();

                //Insert dara to user password table
                ps = null;
                ps = con.prepareStatement("INSERT INTO USERPASSWORD "
                        + "(USERNAME,PASSWORD,LASTUPDATEDUSER,LASTUPDATEDDATE,CREATEDDATE) "
                        + "VALUES (?,?,?,?)");
                ps.setString(1, userBean.getUserName());
                ps.setString(2, userBean.getPassword());
                ps.setString(3, userBean.getLastUpdatedUser());
                ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                success = ps.executeUpdate();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            ps.close();
        }

        return success;
    }

    public int checkPassword(Connection con, SystemUserBean userBean) throws Exception {
        int isChecked = -1;
        PreparedStatement ps = null;
        String currentPassword = "";
        int success = -1;
        try {
            String selectQuery = "SELECT USERNAME,PASSWORD FROM SYSTEMUSER WHERE USERNAME=?";

            ps = con.prepareStatement(selectQuery);
            ps.setString(1, userBean.getUserName());

            rs = ps.executeQuery();

            while (rs.next()) {
                currentPassword = rs.getString("PASSWORD");
            }

            if (currentPassword.equals("")) {
                isChecked = -2;
            } else if (currentPassword.equals(userBean.getCurrentHashPwd())) {
                isChecked = 1;
            } else {
                isChecked = -3;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            ps.close();
        }
        return isChecked;

    }

    public List<BankBranchBean> getBranchLst(Connection con) throws Exception {
        List<BankBranchBean> branchList = new ArrayList<BankBranchBean>();
        PreparedStatement getAllUserStat = null;
        try {
            getAllUserStat = con.prepareStatement("SELECT B.BRANCHCODE,B.DESCRPTION,B.BANKCODE FROM BANKBRANCH B,COMMONPARAMETER C WHERE B.BANKCODE = C.BANKCODE");
            rs = getAllUserStat.executeQuery();
            sysUserList = new ArrayList<SystemUserBean>();
            while (rs.next()) {
                BankBranchBean bean = new BankBranchBean();
                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBranchCode(rs.getString("BRANCHCODE"));
                bean.setDescription(rs.getString("DESCRPTION"));

                branchList.add(bean);

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    getAllUserStat.close();
                }
            } catch (Exception e) {
                throw e;
            }

        }
        return branchList;
    }

    public String getBankName(Connection con) throws Exception {
        PreparedStatement getAllUserStat = null;
        String bankName = "";
        try {
            getAllUserStat = con.prepareStatement("SELECT B.BANKCODE,B.BANKNAME FROM BANK B,COMMONPARAMETER C WHERE B.BANKCODE = C.BANKCODE");
            rs = getAllUserStat.executeQuery();
            if (rs.next()) {
                bankName = rs.getString("BANKNAME");

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    getAllUserStat.close();
                }
            } catch (Exception e) {
                throw e;
            }

        }
        return bankName;
    }

    public List<UserPasswordBean> getUserPasswordBeanList(Connection con, String userName, String password) throws Exception {

        List<UserPasswordBean> list = new ArrayList<UserPasswordBean>();
        UserPasswordBean userPasswordBean = null;
        PreparedStatement validateStat = null;
        try {
            validateStat = con.prepareStatement("SELECT ISU.PASSWORD, ISU.USERNAME, ISU.CREATEDDATE, ISU.LASTUPDATEDDATE , "
                    + "ISU.LASTUPDATEDUSER "
                    + "FROM USERPASSWORD ISU "
                    + "WHERE ISU.USERNAME=? "
                    //+ "AND ISU.PASSWORD=? "
                    + "ORDER BY LASTUPDATEDDATE DESC");
            validateStat.setString(1, userName);
            //validateStat.setString(2, password);
            rs = validateStat.executeQuery();
            while (rs.next()) {
                userPasswordBean = new UserPasswordBean();

                userPasswordBean.setPassword(rs.getString("PASSWORD"));
                userPasswordBean.setUserName(rs.getString("USERNAME"));
                userPasswordBean.setCreatedTime(rs.getDate("CREATEDDATE"));
                userPasswordBean.setLastUpdatedTime(rs.getDate("LASTUPDATEDDATE"));
                userPasswordBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                list.add(userPasswordBean);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            validateStat.close();
        }

        return list;

    }

    public int updateUserInvalidLoginAttempts(Connection CMSCon, SystemUserBean userBean) throws Exception {

        int resultID = -1;
        PreparedStatement updateSysUserStat = null;
        try {
            if (userBean.getStatusCode() != null) {
                updateSysUserStat = CMSCon.prepareStatement("UPDATE SYSTEMUSER SET "
                        + "LOGINATTEMPT=?"
                        + ",STATUS=? "
                        + "WHERE USERNAME=? ");
            } else {
                updateSysUserStat = CMSCon.prepareStatement("UPDATE SYSTEMUSER SET "
                        + "LOGINATTEMPT=? "
                        + "WHERE USERNAME=? ");
            }
            updateSysUserStat.setInt(1, userBean.getNoOfInvalidLoginAtt());
            if (userBean.getStatusCode() != null) {
                updateSysUserStat.setString(2, userBean.getStatusCode());
                updateSysUserStat.setString(3, userBean.getUserName());
            } else {
                updateSysUserStat.setString(2, userBean.getUserName());
            }

            resultID = updateSysUserStat.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            updateSysUserStat.close();
        }

        return resultID;

    }

    public int updateEmailStatusAndPassword(Connection CMSCon, SystemUserBean userBean) throws Exception {

        int resultID = -1;
        PreparedStatement updateSysUserStat = null;
        PreparedStatement updateSysUserStat2 = null;
        String sql, sql2 = null;

        try {
            if (!userBean.getIsEmailSent().isEmpty() && StatusVarList.DA_REQUSET_REJECT.equals(userBean.getStatusCode())) {
                sql = "UPDATE SYSTEMUSERREQUEST SET "
                        + "AUTHUSER=?,AUTHDATETIME=SYSDATE, "
                        + "REJECTREMARK=?, "
                        + "STATUS=? "
                        + "WHERE USERNAME=? ";
                sql2 = "UPDATE SYSTEMUSER SET ISEMAILSENT=? WHERE USERNAME=? ";
            } else {
                sql = "UPDATE SYSTEMUSERREQUEST SET "
                        + "AUTHUSER=?,AUTHDATETIME=SYSDATE, "
                        + "STATUS=? "
                        + "WHERE USERNAME=? ";
                sql2 = "UPDATE SYSTEMUSER SET ISEMAILSENT=?,PASSWORD=? WHERE USERNAME=? ";
            }

            updateSysUserStat = CMSCon.prepareStatement(sql);

            updateSysUserStat.setString(1, userBean.getAuthorizedUser());
            if (StatusVarList.DA_REQUSET_APPROVE.equals(userBean.getStatusCode())) {
                updateSysUserStat.setString(2, userBean.getStatusCode());
                updateSysUserStat.setString(3, userBean.getUserName());
            } else {
                updateSysUserStat.setString(2, userBean.getRejectRemark());
                updateSysUserStat.setString(3, userBean.getStatusCode());
                updateSysUserStat.setString(4, userBean.getUserName());
            }

            resultID = updateSysUserStat.executeUpdate();

            updateSysUserStat2 = CMSCon.prepareStatement(sql2);
            updateSysUserStat2.setString(1, userBean.getIsEmailSent());
            if (StatusVarList.DA_REQUSET_APPROVE.equals(userBean.getStatusCode())) {
                updateSysUserStat2.setString(2, userBean.getPassword());
                updateSysUserStat2.setString(3, userBean.getUserName());
            } else {
                updateSysUserStat2.setString(2, userBean.getUserName());
            }

            resultID = updateSysUserStat2.executeUpdate();

            //adding user to userpassword table only if accepted
            if (StatusVarList.DA_REQUSET_APPROVE.equals(userBean.getStatusCode())) {
                resultID += this.insertToUserPassword(CMSCon, userBean);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            updateSysUserStat.close();
        }

        return resultID;

    }

    public int updateEmailStatusAndPasswordAndAuthLock(Connection CMSCon, SystemUserBean userBean) throws Exception {

        int resultID = -1;
        PreparedStatement updateSysUserStat = null;
        String sql, sql2 = null;

        try {
            if (!userBean.getIsEmailSent().isEmpty() && StatusVarList.DA_REQUSET_REJECT.equals(userBean.getStatusCode())) {
                sql = "UPDATE SYSTEMUSERREQUEST SET "
                        + "AUTHUSER=?,AUTHDATETIME=SYSDATE, "
                        + "REJECTREMARK=?, "
                        + "STATUS=? "
                        + "WHERE USERNAME=? ";
                sql2 = "UPDATE SYSTEMUSER SET ISEMAILSENT=?,PASSWORD=?, WHERE USERNAME=? ";
            } else {
                sql = "UPDATE SYSTEMUSERREQUEST SET "
                        + "AUTHUSER=?,AUTHDATETIME=SYSDATE, "
                        + "STATUS=? "
                        + "WHERE USERNAME=? ";
                sql2 = "UPDATE SYSTEMUSER SET ISEMAILSENT=?,PASSWORD=? WHERE USERNAME=? ";
            }

            updateSysUserStat = CMSCon.prepareStatement(sql);

            updateSysUserStat.setString(1, userBean.getAuthorizedUser());
            if (StatusVarList.DA_REQUSET_APPROVE.equals(userBean.getStatusCode())) {
                updateSysUserStat.setString(2, userBean.getStatusCode());
                updateSysUserStat.setString(3, userBean.getUserName());
            } else {
                updateSysUserStat.setString(2, userBean.getRejectRemark());
                updateSysUserStat.setString(3, userBean.getStatusCode());
                updateSysUserStat.setString(4, userBean.getUserName());
            }

            resultID = updateSysUserStat.executeUpdate();

            updateSysUserStat = CMSCon.prepareStatement(sql2);
            updateSysUserStat.setString(1, userBean.getIsEmailSent());
            if (StatusVarList.DA_REQUSET_APPROVE.equals(userBean.getStatusCode())) {
                updateSysUserStat.setString(2, userBean.getPassword());
                updateSysUserStat.setString(3, userBean.getUserName());
            } else {
                updateSysUserStat.setString(2, userBean.getUserName());
            }

            resultID = updateSysUserStat.executeUpdate();

            //adding user to userpassword table only if accepted
            if (StatusVarList.DA_REQUSET_APPROVE.equals(userBean.getStatusCode())) {
                resultID += insertToUserPassword(CMSCon, userBean);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            updateSysUserStat.close();
        }

        return resultID;

    }

    public int updateSystemAuthLock(Connection CMSCon, SystemUserBean userBean) throws Exception {

        int resultID = -1;
        PreparedStatement updateSysUserStat = null;

        try {

            String sql = "UPDATE SYSTEMUSER SET "
                    + "ISLOCKEDFORAUTH=? "
                    + "WHERE USERNAME=? ";

            updateSysUserStat = CMSCon.prepareStatement(sql);

            updateSysUserStat.setString(1, userBean.getIslockedforauth());
            updateSysUserStat.setString(2, userBean.getUserName());

            resultID = updateSysUserStat.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            updateSysUserStat.close();
        }

        return resultID;

    }

    public int updateSystemFirstLoginStatus(Connection CMSCon, SystemUserBean userBean) throws Exception {

        int resultID = -1;
        PreparedStatement updateSysUserStat = null;

        try {

            String sql = "UPDATE SYSTEMUSER SET "
                    + "INITIALLOGINSTATUS=? "
                    + "WHERE USERNAME=? ";

            updateSysUserStat = CMSCon.prepareStatement(sql);

            updateSysUserStat.setString(1, userBean.getInitialloginstatus());
            updateSysUserStat.setString(2, userBean.getUserName());

            resultID = updateSysUserStat.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            updateSysUserStat.close();
        }

        return resultID;

    }

    public int updateEmailStatusAndPasswordAndAuth(Connection CMSCon, SystemUserBean userBean) throws Exception {

        int resultID = -1;
        PreparedStatement updateSysUserStat = null;
        try {
            if (StatusVarList.DA_REQUSET_APPROVE.equals(userBean.getStatusCode())) {
                updateSysUserStat = CMSCon.prepareStatement("UPDATE SYSTEMUSER SET "
                        + "ISLOCKEDFORAUTH=?,ISEMAILSENT=?,PASSWORD=?,INITIALLOGINSTATUS='Y' "
                        + "WHERE USERNAME=? ");

                updateSysUserStat.setString(1, userBean.getIslockedforauth());
                updateSysUserStat.setString(2, userBean.getIsEmailSent());
                updateSysUserStat.setString(3, userBean.getPassword());
                updateSysUserStat.setString(4, userBean.getUserName());

                resultID = updateSysUserStat.executeUpdate();
            }

            updateSysUserStat = CMSCon.prepareStatement("UPDATE USERPASSWORDREQUEST SET "
                    + "STATUS=?,REJECTREMARK=?,AUTHUSER=?,AUTHDATETIME=SYSDATE "
                    + "WHERE USERNAME=? AND STATUS='PWRS' ");

            updateSysUserStat.setString(1, userBean.getStatusCode());
            updateSysUserStat.setString(2, userBean.getRejectRemark());
            updateSysUserStat.setString(3, userBean.getAuthorizedUser());
            updateSysUserStat.setString(4, userBean.getUserName());

            resultID = updateSysUserStat.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            updateSysUserStat.close();
        }

        return resultID;

    }

    public int updateSysUserDelete(Connection CMSCon, SystemUserBean userBean) throws Exception {

        int resultID = -1;
        PreparedStatement updateSysUserStat = null;
        try {

            updateSysUserStat = CMSCon.prepareStatement("UPDATE SYSTEMUSERREQUEST SET "
                    + "STATUS=?, "
                    + "AUTHUSER=?, "
                    + "AUTHDATETIME=SYSDATE, "
                    + "REJECTREMARK=?,LASTUPDATEDUSER=? "
                    + "WHERE USERNAME=? AND STATUS IN ('DRIN','ARIN') ");

            updateSysUserStat.setString(1, userBean.getStatusCode());
            updateSysUserStat.setString(2, userBean.getAuthorizedUser());
            updateSysUserStat.setString(3, userBean.getRejectRemark());
            updateSysUserStat.setString(4, userBean.getAuthorizedUser());
            updateSysUserStat.setString(5, userBean.getUserName());

            resultID = updateSysUserStat.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            updateSysUserStat.close();
        }

        return resultID;

    }

    public SystemUserBean getLoggedUserDetails(Connection CMSCon, String userName) throws Exception {
        SystemUserBean valideUser = null;
        PreparedStatement validateStat = null;
        try {
            String sql = "SELECT ISU.PASSWORD, ISU.USERNAME, ISU.USERROLECODE, ISU.LOGINATTEMPT, IST.DESCRIPTION AS STATUSCODE, "
                    + "ISU.EXPIRYDATE, ISU.LASTUPDATEDUSER, ISU.CREATEDTIME "
                    + "FROM SYSTEMUSER ISU,USERROLE IUR,STATUS IST "
                    + "WHERE ISU.USERNAME=? "
                    + "AND(ISU.USERROLECODE=IUR.USERROLE AND IST.STATUSCODE=ISU.STATUS) ";

            validateStat = CMSCon.prepareStatement(sql);
            validateStat.setString(1, userName);
            rs = validateStat.executeQuery();
            while (rs.next()) {
                valideUser = new SystemUserBean();

                valideUser.setPassword(rs.getString("PASSWORD"));
                valideUser.setUserName(rs.getString("USERNAME"));
                valideUser.setUserRoleCode(rs.getString("USERROLECODE"));
                valideUser.setStatusCode(rs.getString("STATUSCODE"));
                valideUser.setExpiryDate(rs.getDate("EXPIRYDATE"));
                valideUser.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                valideUser.setCreatedTime(rs.getDate("CREATEDTIME"));
                valideUser.setNoOfInvalidLoginAtt(rs.getInt("LOGINATTEMPT"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            validateStat.close();
        }

        return valideUser;
    }

    public int deActivateUser(Connection CMSCon, SystemUserBean userBean, List<String> otherColumnNames, List<Object> otherColumnValues) throws Exception {
        int resultID = -1;
        PreparedStatement updateSysUserStat = null;
        try {
            updateSysUserStat = CMSCon.prepareStatement("UPDATE SYSTEMUSER SET "
                    + ",STATUS=? "
                    + "WHERE USERNAME=? ");

            updateSysUserStat.setString(1, userBean.getStatusCode());
            updateSysUserStat.setString(2, userBean.getUserName());

            resultID = updateSysUserStat.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            updateSysUserStat.close();
        }

        return resultID;
    }
    
    //get dual auth user role according to user role
    public HashMap<String,String> getDualAuthRoleList(Connection con,String userRole) throws Exception{
        ResultSet rs=null;
        PreparedStatement getAssignUserList=null;
        try {
            getAssignUserList=con.prepareStatement("SELECT UR.USERROLE,UR.DESCRIPTION FROM USERROLE UR WHERE UR.STATUS='ACT' AND UR.LEVELID >= (SELECT UR.LEVELID FROM USERROLE UR WHERE UR.USERROLE=?)");
            getAssignUserList.setString(1, userRole);
            rs=getAssignUserList.executeQuery();
            dataCaptureUserList=new HashMap<String,String>();
            while(rs.next()){
                dataCaptureUserList.put(rs.getString("USERROLE"), rs.getString("DESCRIPTION"));
            }
        } catch (Exception ex) {
            throw ex;
        }finally{
            rs.close();
            getAssignUserList.close();
        }
        return dataCaptureUserList;
    }
}
