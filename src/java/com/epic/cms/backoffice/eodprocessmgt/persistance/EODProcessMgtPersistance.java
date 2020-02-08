/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodprocessmgt.persistance;

import com.epic.cms.backoffice.eodprocessmgt.bean.EODProcessMgtBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
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
import java.util.List;

/**
 *
 * @author nisansala
 */
public class EODProcessMgtPersistance {

    ResultSet rs;

    public List<EODProcessMgtBean> getAllEODProcessData(Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;

        try {
            List<EODProcessMgtBean> proceeList = new ArrayList<EODProcessMgtBean>();
            String query = "SELECT P.PROCESSID,P.DESCRIPTION,P.CRITICALSTATUS,P.ROLLBACKSTATUS,P.SHEDULEDATE,P.SHEDULETIME,P.FREQUENCYTYPE,"
                    + "P.CONTINUESFREQUENCYTYPE,"
                    + "P.CONTINUESFREQUENCY,P.MULTIPLECYCLESTATUS,P.PROCESSCATEGORYID,PC.DESCRIPTION AS CAT_DES,P.DEPENDANCYSTATUS,P.RUNNINGONMAIN,P.RUNNINGONSUB,"
                    + "P.PROCESSTYPE,P.STATUS,P.CREATEDTIME,"
                    + "P.LASTUPDATEDTIME,P.LASTUPDATEDUSER,P.HOLIDAYACTION,P.LETTERGENARATIONSTATUS FROM EODPROCESS P,EODPROCESSCATEGORY PC WHERE PC.PROCESSCATEGORYID = P.PROCESSCATEGORYID";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                EODProcessMgtBean bean = new EODProcessMgtBean();

                bean.setProcessId(rs.getString("PROCESSID"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setCriticalStatus(rs.getString("CRITICALSTATUS"));
                bean.setRollbackStatus(rs.getString("ROLLBACKSTATUS"));
                bean.setScheduledDate(this.convertStringDate(rs.getDate("SHEDULEDATE")));
                bean.setScheduledTime(rs.getString("SHEDULETIME"));
                bean.setFrequencyType(rs.getString("FREQUENCYTYPE"));
                bean.setContinueosFreqType(rs.getString("CONTINUESFREQUENCYTYPE"));
                bean.setContinueosFrequency(rs.getString("CONTINUESFREQUENCY"));
                bean.setMultiCycleStatus(rs.getString("MULTIPLECYCLESTATUS"));
                bean.setProcessCatId(rs.getString("PROCESSCATEGORYID"));
                bean.setProcessCatDes(rs.getString("CAT_DES"));
                bean.setDependencyStatus(rs.getString("DEPENDANCYSTATUS"));
                bean.setOnMain(rs.getString("RUNNINGONMAIN"));
                bean.setOnSub(rs.getString("RUNNINGONSUB"));
                bean.setProcessType(rs.getString("PROCESSTYPE"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setLastUpdateduser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                bean.setCreatedTime(rs.getDate("CREATEDTIME"));
                bean.setHolidayAction(rs.getString("HOLIDAYACTION"));
                bean.setLetterGenStatus(rs.getString("LETTERGENARATIONSTATUS"));

                proceeList.add(bean);
            }
            return proceeList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * insert new EOD Process
     * @param bean
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public Boolean insertNewEODProcess(EODProcessMgtBean bean, Connection con) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO EODPROCESS (DESCRIPTION,CRITICALSTATUS,ROLLBACKSTATUS,SHEDULETIME,FREQUENCYTYPE,CONTINUESFREQUENCYTYPE,"
                    + "CONTINUESFREQUENCY,MULTIPLECYCLESTATUS,PROCESSCATEGORYID,DEPENDANCYSTATUS,RUNNINGONMAIN,RUNNINGONSUB,PROCESSTYPE,STATUS,"
                    + "LASTUPDATEDUSER,CREATEDTIME,SHEDULEDATE,SHEDULEDATETIME,HOLIDAYACTION,LETTERGENARATIONSTATUS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


            ps = con.prepareStatement(query);
            ps.setString(1, bean.getDescription());
            ps.setInt(2, Integer.parseInt(bean.getCriticalStatus()));
            ps.setInt(3, Integer.parseInt(bean.getRollbackStatus()));
            ps.setInt(4, Integer.parseInt(bean.getScheduledTime()));
            ps.setInt(5, Integer.parseInt(bean.getFrequencyType()));
            ps.setString(6, bean.getContinueosFreqType());
            ps.setString(7, bean.getContinueosFrequency());
            ps.setInt(8, Integer.parseInt(bean.getMultiCycleStatus()));
            ps.setInt(9, Integer.parseInt(bean.getProcessCatId()));
            ps.setInt(10, Integer.parseInt(bean.getDependencyStatus()));
            ps.setInt(11, Integer.parseInt(bean.getOnMain()));
            ps.setInt(12, Integer.parseInt(bean.getOnSub()));
            ps.setInt(13, Integer.parseInt(bean.getProcessType()));
            ps.setString(14, bean.getStatus());
            ps.setString(15, bean.getLastUpdateduser());
            ps.setTimestamp(16, SystemDateTime.getSystemDataAndTime());
            ps.setTimestamp(17, new Timestamp(stringTodate(bean.getScheduledDate()).getTime()));
            ps.setTimestamp(18, new Timestamp(formatDT(bean.getScheduledDate(), Integer.parseInt(bean.getScheduledTime())).getTime()));
            ps.setString(19, bean.getHolidayAction());
            ps.setString(20, bean.getLetterGenStatus());

            ps.executeUpdate();
            success = true;


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

        return success;
    }

    /**
     * update EOD Process
     * @param bean
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int updateEODProcess(EODProcessMgtBean bean, Connection con) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        int row = -1;
        try {
            String query = "UPDATE EODPROCESS SET DESCRIPTION=?,CRITICALSTATUS=?,ROLLBACKSTATUS=?,SHEDULETIME=?,FREQUENCYTYPE=?,CONTINUESFREQUENCYTYPE=?,"
                    + "CONTINUESFREQUENCY=?,MULTIPLECYCLESTATUS=?,PROCESSCATEGORYID=?,DEPENDANCYSTATUS=?,RUNNINGONMAIN=?,RUNNINGONSUB=?,PROCESSTYPE=?,STATUS=?,"
                    + "LASTUPDATEDUSER=?,LASTUPDATEDTIME=?,SHEDULEDATE=?,SHEDULEDATETIME=?,HOLIDAYACTION=?,LETTERGENARATIONSTATUS=? WHERE PROCESSID=?";


            ps = con.prepareStatement(query);
            ps.setString(1, bean.getDescription());
            ps.setInt(2, Integer.parseInt(bean.getCriticalStatus()));
            ps.setInt(3, Integer.parseInt(bean.getRollbackStatus()));
            ps.setInt(4, Integer.parseInt(bean.getScheduledTime()));
            ps.setInt(5, Integer.parseInt(bean.getFrequencyType()));
            ps.setString(6, bean.getContinueosFreqType());
            ps.setString(7, bean.getContinueosFrequency());
            ps.setInt(8, Integer.parseInt(bean.getMultiCycleStatus()));
            ps.setInt(9, Integer.parseInt(bean.getProcessCatId()));
            ps.setInt(10, Integer.parseInt(bean.getDependencyStatus()));
            ps.setInt(11, Integer.parseInt(bean.getOnMain()));
            ps.setInt(12, Integer.parseInt(bean.getOnSub()));
            ps.setInt(13, Integer.parseInt(bean.getProcessType()));
            ps.setString(14, bean.getStatus());
            ps.setString(15, bean.getLastUpdateduser());
            ps.setTimestamp(16, SystemDateTime.getSystemDataAndTime());
            ps.setTimestamp(17, new Timestamp(stringTodate(bean.getScheduledDate()).getTime()));
            ps.setTimestamp(18, new Timestamp(formatDT(bean.getScheduledDate(), Integer.parseInt(bean.getScheduledTime())).getTime()));
            ps.setString(19, bean.getHolidayAction());
            ps.setString(20, bean.getLetterGenStatus());
            ps.setString(21, bean.getProcessId());
            row = ps.executeUpdate();



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

        return row;
    }

    /**
     * retrieve EOD process categories
     * @param con
     * @param tableName
     * @return
     * @throws SQLException 
     */
    public HashMap<String, String> getProcessCategoryList(Connection con) throws SQLException {
        HashMap<String, String> columnNames = new HashMap<String, String>();
        ResultSet rs = null;
        PreparedStatement ps = null;

        String query = "SELECT PROCESSCATEGORYID,DESCRIPTION FROM EODPROCESSCATEGORY ";

        ps = con.prepareStatement(query);
        rs = ps.executeQuery();

        while (rs.next()) {
            columnNames.put(rs.getString("PROCESSCATEGORYID"), rs.getString("DESCRIPTION"));

        }
        return columnNames;
    }

    public Date stringTodate(String date) throws ParseException {
        String dateString = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = dateFormat.parse(dateString);
        return convertedDate;
    }

    private String convertStringDate(java.sql.Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return (dateFormat.format(date));
    }

    /**
     * to delete a EOD Process
     * @param con
     * @param questionNo
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteEODProcess(Connection con, String processId) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM EODPROCESS WHERE PROCESSID =?";

            ps = con.prepareStatement(query);
            ps.setString(1, processId);

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

    public String getProcessId(Connection con, String user) throws Exception {
        String processId = "";
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT PROCESSID FROM EODPROCESS WHERE LASTUPDATEDUSER=? AND PROCESSID=(SELECT MAX(PROCESSID) FROM EODPROCESS )");
            ps.setString(1, user);

            rs = ps.executeQuery();


            while (rs.next()) {

                processId = rs.getString("PROCESSID");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }

        return processId;
    }

    public Date formatDT(String date, int time) throws ParseException {

        String AMorPM = " AM";
        String defTimePart = ":0:0";

        if (time > 12) {
            time = time - 12;
            AMorPM = " PM";
        }

        String formattedtime = String.valueOf(time).concat(defTimePart).concat(AMorPM);
        String dateTime = date.concat(" ").concat(formattedtime);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        Date inputDate = dateFormat.parse(dateTime);//this is the user given date
        String lastDate = dateFormat.format(inputDate);
        System.out.println(lastDate);

        Date convertedDate = dateFormat.parse(lastDate);
        System.out.println(convertedDate);

        return convertedDate;
    }
}