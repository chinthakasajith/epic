/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author upul
 */
public class SystemAuditPersistance {

    /**
     * insert system audittrace insertions
     * @param con
     * @param systemAudit
     * @return
     * @throws Exception 
     */
    private ResultSet rs = null;
    private List<SystemAuditBean> auditList = null;

    public int insertRECSystemAudit(Connection con, SystemAuditBean systemAudit) throws Exception {

        int resultId = 0;
        String section = "";
        String application = "";
        PreparedStatement insertAuditStat = null;
        PreparedStatement selectStat = null;
        try {
            selectStat = con.prepareStatement("SELECT SPG.SECTIONCODE ,ASE.APPLICATIONCODE FROM APPLICATIONSECTION ASE,SECTIONPAGE SPG WHERE SPG.SECTIONCODE = ASE.SECTIONCODE AND SPG.PAGECODE= '" + systemAudit.getPageCode() + "'");
            rs = selectStat.executeQuery();


            if (rs.next()) {

                application = rs.getString("APPLICATIONCODE");
                section = rs.getString("SECTIONCODE");

                insertAuditStat = con.prepareStatement("INSERT INTO SYSTEMAUDIT "
                        + "(USERROLECODE,DESCRIPTION,APPLICATIONCODE,SECTIONCODE,PAGECODE,TASKCODE,REMARKS,IP,UNIQUEID,FIELDNAME,OLDVALUE,NEWVALUE ,LASTUPDATEDUSER,USERNAME) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

                insertAuditStat.setString(1, systemAudit.getUserRoleCode());
                insertAuditStat.setString(2, systemAudit.getDescription());
                insertAuditStat.setString(3, application);//edite by janaka
                insertAuditStat.setString(4, section);//edit by janaka
                insertAuditStat.setString(5, systemAudit.getPageCode());
                insertAuditStat.setString(6, systemAudit.getTaskCode());
                insertAuditStat.setString(7, systemAudit.getRemarks());
                insertAuditStat.setString(8, systemAudit.getIp());
                insertAuditStat.setString(9, systemAudit.getUniqueId());
                insertAuditStat.setString(10, systemAudit.getFieldName());
                insertAuditStat.setString(11, systemAudit.getOldValue());
                insertAuditStat.setString(12, systemAudit.getNewValue());
                insertAuditStat.setString(13, systemAudit.getLastUpdateduser());
                insertAuditStat.setString(14, systemAudit.getUserName());



                resultId = insertAuditStat.executeUpdate();

            }


        } catch (Exception ex) {


            throw ex;

        } finally {
            insertAuditStat.close();
            selectStat.close();
        }

        return resultId;
    }
    public int insertRECSystemLoginAudit(Connection con, SystemAuditBean systemAudit) throws Exception {

        int resultId = 0;
        PreparedStatement insertAuditStat = null;
        try {
          

            

             

                insertAuditStat = con.prepareStatement("INSERT INTO SYSTEMAUDIT "
                        + "(USERROLECODE,DESCRIPTION,APPLICATIONCODE,SECTIONCODE,PAGECODE,TASKCODE,REMARKS,IP,UNIQUEID,FIELDNAME,OLDVALUE,NEWVALUE ,LASTUPDATEDUSER,USERNAME) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

                insertAuditStat.setString(1, systemAudit.getUserRoleCode());
                insertAuditStat.setString(2, systemAudit.getDescription());
                insertAuditStat.setString(3, systemAudit.getApplicationCode());//edite by janaka
                insertAuditStat.setString(4, systemAudit.getSectionCode());//edit by janaka
                insertAuditStat.setString(5, systemAudit.getPageCode());
                insertAuditStat.setString(6, systemAudit.getTaskCode());
                insertAuditStat.setString(7, systemAudit.getRemarks());
                insertAuditStat.setString(8, systemAudit.getIp());
                insertAuditStat.setString(9, systemAudit.getUniqueId());
                insertAuditStat.setString(10, systemAudit.getFieldName());
                insertAuditStat.setString(11, systemAudit.getOldValue());
                insertAuditStat.setString(12, systemAudit.getNewValue());
                insertAuditStat.setString(13, systemAudit.getLastUpdateduser());
                insertAuditStat.setString(14, systemAudit.getUserName());



                resultId = insertAuditStat.executeUpdate();

          


        } catch (Exception ex) {


            throw ex;

        } finally {
            insertAuditStat.close();
        }

        return resultId;
    }

    /**
     * generalSearch
     * @param con
     * @param audit
     * @param strFromModifiedDate
     * @param strToModifiedDate
     * @return
     * @throws Exception 
     */
    public List generalSearch(Connection con, SystemAuditBean audit, String strFromModifiedDate, String strToModifiedDate) throws Exception {



        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;		// Holds the Sql query
        try {



            strSqlBasic = "SELECT a.SYSTEMAUDITID,u.DESCRIPTION AS USERROLE, a.DESCRIPTION,a.USERNAME,ap.DESCRIPTION AS APPLICATION, s.DESCRIPTION AS SECTION, p.DESCRIPTION AS PAGE, t.DESCRIPTION AS TASK, "
                    + " a.REMARKS,a.UNIQUEID,a.FIELDNAME,a.OLDVALUE,a.NEWVALUE, a.IP,a.LASTUPDATEDUSER,a.LASTUPDATEDTIME,a.CREATEDTIME "
                    + "FROM SYSTEMAUDIT a,USERROLE u,SECTION s,PAGE p,TASK t,APPLICATION ap";

            String condition = "";


            if (audit.getSystemAuditId() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.SYSTEMAUDITID = " + audit.getSystemAuditId();
            }
            if (!audit.getUserRoleCode().contentEquals("") || audit.getUserRoleCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.USERROLECODE = '" + audit.getUserRoleCode() + "'";
            }

            if (!audit.getUserName().contentEquals("") || audit.getUserName().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.USERNAME = '" + audit.getUserName() + "'";
            }


            if (!audit.getDescription().contentEquals("") || audit.getDescription().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.DESCRIPTION LIKE  '%" + audit.getDescription() + "%'";
            }

            if (!audit.getApplicationCode().contentEquals("") || audit.getApplicationCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.APPLICATIONCODE ='" + audit.getApplicationCode() + "'";
            }



            if (!audit.getSectionCode().contentEquals("") || audit.getSectionCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.SECTIONCODE ='" + audit.getSectionCode() + "'";
            }

            if (!audit.getPageCode().contentEquals("") || audit.getPageCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.PAGECODE ='" + audit.getPageCode() + "'";
            }

            if (!audit.getTaskCode().contentEquals("") || audit.getTaskCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.TASKCODE ='" + audit.getTaskCode() + "'";
            }


            if (!audit.getRemarks().contentEquals("") || audit.getRemarks().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.REMARKS LIKE  '%" + audit.getRemarks() + "%'";
            }


            if (!audit.getIp().contentEquals("") || audit.getIp().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.IP LIKE '%" + audit.getIp() + "%'";
            }


            if (!audit.getLastUpdateduser().contentEquals("") || audit.getLastUpdateduser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.LASTUPDATEDUSER ='" + audit.getLastUpdateduser() + "'";
            }


            if (!strFromModifiedDate.contentEquals("") && !strToModifiedDate.contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.LASTUPDATEDTIME >= to_Date('" + this.stringTodate(strFromModifiedDate) + "','yy-mm-dd') AND a.LASTUPDATEDTIME <= to_Date('" + this.stringTodate(strToModifiedDate) + "','yy-mm-dd')";
            } else {

                if (!strFromModifiedDate.contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "a.LASTUPDATEDTIME >= to_Date('" + this.stringTodate(strFromModifiedDate) + "','yy-mm-dd')";
                }
                if (!strToModifiedDate.contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "a.LASTUPDATEDTIME <= to_Date('" + this.stringTodate(strToModifiedDate) + "','yy-mm-dd')";
                }
            }
            if (!condition.equals("")) {
                strSqlBasic += " WHERE ap.APPLICATIONCODE=A.APPLICATIONCODE AND a.USERROLECODE=u.USERROLE AND a.SECTIONCODE=s.SECTIONCODE AND a.PAGECODE=p.PAGECODE AND a.TASKCODE=t.TASKCODE AND  " + condition + " ORDER BY a.SYSTEMAUDITID";
            } else {
                strSqlBasic += " WHERE ap.APPLICATIONCODE=A.APPLICATIONCODE AND a.USERROLECODE=u.USERROLE AND a.SECTIONCODE=s.SECTIONCODE AND a.PAGECODE=p.PAGECODE AND a.TASKCODE=t.TASKCODE ORDER BY a.SYSTEMAUDITID";
            }


            stmt = con.prepareStatement(strSqlBasic);
            System.out.println(strSqlBasic);
            rs = stmt.executeQuery();

            auditList = new ArrayList<SystemAuditBean>();

            while (rs.next()) {


                SystemAuditBean resultAudit = new SystemAuditBean();
                resultAudit.setSystemAuditId(rs.getInt("SYSTEMAUDITID"));
                resultAudit.setUserRoleCode(rs.getString("USERROLE"));
                resultAudit.setUserName(rs.getString("USERNAME"));
                resultAudit.setDescription(rs.getString("DESCRIPTION"));
                resultAudit.setUniqueId(rs.getString("UNIQUEID"));
                resultAudit.setFieldName(rs.getString("FIELDNAME"));
                resultAudit.setOldValue(rs.getString("OLDVALUE"));
                resultAudit.setNewValue(rs.getString("NEWVALUE"));
                resultAudit.setSectionCode(rs.getString("SECTION"));
                resultAudit.setApplicationCode(rs.getString("APPLICATION"));
                resultAudit.setPageCode(rs.getString("PAGE"));
                resultAudit.setTaskCode(rs.getString("TASK"));
                resultAudit.setRemarks(rs.getString("REMARKS"));
                resultAudit.setIp(rs.getString("IP"));
                resultAudit.setLastUpdateduser(rs.getString("LASTUPDATEDUSER"));
                resultAudit.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                resultAudit.setCreatedTime(rs.getTimestamp("CREATEDTIME"));

                auditList.add(resultAudit);

            }

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return auditList;
    }

    /**
     * stringTodate
     * @param date
     * @return
     * @throws ParseException 
     */
    //convert string to Date type
    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));
    }

    public int getCount(Connection con, String condition) throws Exception {

        int count = 0;
        rs = null;
        PreparedStatement ps = null;
        String strSqlBasic = null;
        try {


            strSqlBasic = "SELECT COUNT(*) AS TOTAL FROM SYSTEMAUDIT A WHERE " + condition;
            System.out.println(strSqlBasic);
            ps = con.prepareStatement(strSqlBasic);

            rs = ps.executeQuery();

            while (rs.next()) {

                count = rs.getInt("TOTAL");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return count;
    }

    public List<SystemAuditBean> getAuditDetails(Connection con, String condition, int start, int end) throws Exception {

        List<SystemAuditBean> searchList;

        PreparedStatement ps = null;
        String strSqlBasic = null;

        try {


            strSqlBasic = " SELECT * FROM ( "
                    + "SELECT * FROM ( "
                    + "SELECT a.SYSTEMAUDITID,u.DESCRIPTION AS USERROLE, a.DESCRIPTION,a.USERNAME,ap.DESCRIPTION AS APPLICATION, s.DESCRIPTION AS SECTION, p.DESCRIPTION AS PAGE, t.DESCRIPTION AS TASK, "
                    + " a.REMARKS,a.UNIQUEID,a.FIELDNAME,a.OLDVALUE,a.NEWVALUE, a.IP,a.LASTUPDATEDUSER,a.LASTUPDATEDTIME,a.CREATEDTIME,ROWNUM r "
                    + "FROM SYSTEMAUDIT a,USERROLE u,SECTION s,PAGE p,TASK t,APPLICATION ap "
                    + "WHERE ap.APPLICATIONCODE=A.APPLICATIONCODE AND a.USERROLECODE=u.USERROLE AND a.SECTIONCODE=s.SECTIONCODE AND a.PAGECODE=p.PAGECODE "
                    + "AND a.TASKCODE=t.TASKCODE AND " + condition + "  )"
                    + " WHERE r <=  " + end + "  ) WHERE r > " + start;

            //ORDER BY a.SYSTEMAUDITID
            /**
             * SELECT a.SYSTEMAUDITID,u.DESCRIPTION AS USERROLE, a.DESCRIPTION,a.USERNAME,ap.DESCRIPTION AS APPLICATION, s.DESCRIPTION AS SECTION, p.DESCRIPTION AS PAGE, t.DESCRIPTION AS TASK, "     
            + " a.REMARKS,a.UNIQUEID,a.FIELDNAME,a.OLDVALUE,a.NEWVALUE, a.IP,a.LASTUPDATEDUSER,a.LASTUPDATEDTIME,a.CREATEDTIME "
            + "FROM SYSTEMAUDIT a,USERROLE u,SECTION s,PAGE p,TASK t,APPLICATION ap
             */
            System.out.println("************" + strSqlBasic);
            ps = con.prepareStatement(strSqlBasic);


            rs = ps.executeQuery();
            searchList = new ArrayList<SystemAuditBean>();


            while (rs.next()) {


                SystemAuditBean resultAudit = new SystemAuditBean();

                resultAudit.setSystemAuditId(rs.getInt("SYSTEMAUDITID"));
                resultAudit.setUserRoleCode(rs.getString("USERROLE"));
                resultAudit.setUserName(rs.getString("USERNAME"));
                resultAudit.setDescription(rs.getString("DESCRIPTION"));
                resultAudit.setUniqueId(rs.getString("UNIQUEID"));
                resultAudit.setFieldName(rs.getString("FIELDNAME"));
                resultAudit.setOldValue(rs.getString("OLDVALUE"));
                resultAudit.setNewValue(rs.getString("NEWVALUE"));
                resultAudit.setSectionCode(rs.getString("SECTION"));
                resultAudit.setApplicationCode(rs.getString("APPLICATION"));
                resultAudit.setPageCode(rs.getString("PAGE"));
                resultAudit.setTaskCode(rs.getString("TASK"));
                resultAudit.setRemarks(rs.getString("REMARKS"));
                resultAudit.setIp(rs.getString("IP"));
                resultAudit.setLastUpdateduser(rs.getString("LASTUPDATEDUSER"));
                resultAudit.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                resultAudit.setCreatedTime(rs.getTimestamp("CREATEDTIME"));

                searchList.add(resultAudit);

            }


        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return searchList;
    }

    public SystemAuditBean viewOneAuditRecord(Connection con, String auditId) throws Exception {

        SystemAuditBean resultAudit;

        PreparedStatement ps = null;
        String strSqlBasic = null;

        try {
            strSqlBasic = "SELECT SA.SYSTEMAUDITID,UR.DESCRIPTION AS USERROLE, SA.DESCRIPTION,SA.USERNAME,A.DESCRIPTION AS APPLICATION,"
                    + "S.DESCRIPTION AS SECTION,P.DESCRIPTION AS PAGE, T.DESCRIPTION AS TASK,SA.REMARKS,SA.UNIQUEID,SA.FIELDNAME,"
                    + "SA.OLDVALUE,SA.NEWVALUE, SA.IP,SA.LASTUPDATEDUSER,SA.LASTUPDATEDTIME,SA.CREATEDTIME "
                    + "FROM SYSTEMAUDIT SA,USERROLE UR,SECTION S,PAGE P,TASK T,APPLICATION A "
                    + "WHERE A.APPLICATIONCODE=SA.APPLICATIONCODE AND SA.USERROLECODE=UR.USERROLE AND SA.SECTIONCODE=S.SECTIONCODE "
                    + "AND SA.PAGECODE=P.PAGECODE AND SA.TASKCODE=T.TASKCODE AND SYSTEMAUDITID=?";
            ps = con.prepareStatement(strSqlBasic);
            ps.setString(1, auditId);
            rs = ps.executeQuery();
            resultAudit = new SystemAuditBean();

            while (rs.next()) {

                resultAudit.setSystemAuditId(rs.getInt("SYSTEMAUDITID"));
                resultAudit.setUserRoleCode(rs.getString("USERROLE"));
                resultAudit.setUserName(rs.getString("USERNAME"));
                resultAudit.setDescription(rs.getString("DESCRIPTION"));
                resultAudit.setUniqueId(rs.getString("UNIQUEID"));
                resultAudit.setFieldName(rs.getString("FIELDNAME"));
                resultAudit.setOldValue(rs.getString("OLDVALUE"));
                resultAudit.setNewValue(rs.getString("NEWVALUE"));
                resultAudit.setSectionCode(rs.getString("SECTION"));
                resultAudit.setApplicationCode(rs.getString("APPLICATION"));
                resultAudit.setPageCode(rs.getString("PAGE"));
                resultAudit.setTaskCode(rs.getString("TASK"));
                resultAudit.setRemarks(rs.getString("REMARKS"));
                resultAudit.setIp(rs.getString("IP"));
                resultAudit.setLastUpdateduser(rs.getString("LASTUPDATEDUSER"));
                resultAudit.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                resultAudit.setCreatedTime(rs.getTimestamp("CREATEDTIME"));

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return resultAudit;
    }
}
