/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.ChannelIncomeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.OnlineTxnTypeBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.backoffice.eodprocessmgt.bean.EODProcessMgtBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class AcquireTxnTypePersistance {

    ResultSet rs;

    /**
     * get transaction type details from online db
     * @param conOnline
     * @return
     * @throws Exception 
     */
    public List<OnlineTxnTypeBean> getOnlineTxnType(Connection conOnline) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<OnlineTxnTypeBean> onlineTxnTypeList = new ArrayList<OnlineTxnTypeBean>();
        try {
            getAllByCatStat = conOnline.prepareStatement("SELECT TC.TYPECODE, TC.DESCRYPTION "
                    + " FROM ECMS_ONLINE_TXNTYPE TC ");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                OnlineTxnTypeBean type = new OnlineTxnTypeBean();


                type.setTxnCode(Integer.toString(rs.getInt("TYPECODE")));
                type.setDescription(rs.getString("DESCRYPTION"));

                onlineTxnTypeList.add(type);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();

        }
        return onlineTxnTypeList;
    }

    public Boolean insertAcquirerTxnTypes(ChannelIncomeBean bean, Connection con) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO ECMS_ONLINE_DEFINEDTXN(MTI,PROCESSINGCODE,TXNTYPECODE,STATUS,DESCRIPTION)"
                    + " VALUES(?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getMti());
            ps.setString(2, bean.getProcessingCode());
            ps.setString(3, bean.getTransactionTypeCode());
            ps.setInt(4, bean.getStatusToOnline());
            ps.setString(5, bean.getTxnDescription());

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

    public int updateAcquirerTxnTypes(ChannelIncomeBean bean, Connection con) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        int row = -1;
        try {
            String query = "UPDATE ECMS_ONLINE_DEFINEDTXN SET TXNTYPECODE=?,STATUS=?,DESCRIPTION =? WHERE MTI=? AND PROCESSINGCODE=?";

            ps = con.prepareStatement(query);

            ps.setString(1, bean.getTransactionTypeCode());
            ps.setInt(2, bean.getStatusToOnline());
            ps.setString(3, bean.getTxnDescription());
            ps.setString(4, bean.getMti());
            ps.setString(5, bean.getProcessingCode());

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

    public int deleteAcquirerTxnTypes(Connection con, ChannelIncomeBean bean) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM ECMS_ONLINE_DEFINEDTXN WHERE MTI=? AND PROCESSINGCODE=?";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getMti());
            ps.setString(2, bean.getProcessingCode());

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

    public List<ChannelIncomeBean> getAllAcquirerTxnTypeData(Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;

        try {
            List<ChannelIncomeBean> List = new ArrayList<ChannelIncomeBean>();
            String query = "SELECT TT.MTI,TT.PROCESSINGCODE,TT.TXNTYPECODE,TT.DESCRIPTION,TT.STATUS,TC.TYPECODE,TC.DESCRYPTION TXN_TYPE_DES "
                    + "FROM ECMS_ONLINE_DEFINEDTXN TT,ECMS_ONLINE_TXNTYPE TC "
                    + "WHERE TC.TYPECODE = TT.TXNTYPECODE "
                    + "ORDER BY TT.MTI";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChannelIncomeBean bean = new ChannelIncomeBean();

                bean.setMti(rs.getString("MTI"));
                bean.setProcessingCode(rs.getString("PROCESSINGCODE"));
                bean.setTransactionTypeCode(rs.getString("TXNTYPECODE"));
                bean.setStatusToOnline(rs.getInt("STATUS"));
                bean.setTxnDescription(rs.getString("TXN_TYPE_DES"));

                List.add(bean);
            }
            return List;
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
    //*********************************************************************************************************************************************

    public List<EODProcessMgtBean> getAllEODProcessData(Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;

        try {
            List<EODProcessMgtBean> proceeList = new ArrayList<EODProcessMgtBean>();
            String query = "SELECT P.PROCESSID,P.DESCRIPTION,P.CRITICALSTATUS,P.ROLLBACKSTATUS,P.SHEDULEDATE,P.SHEDULETIME,P.FREQUENCYTYPE,"
                    + "P.CONTINUESFREQUENCYTYPE,"
                    + "P.CONTINUESFREQUENCY,P.MULTIPLECYCLESTATUS,P.PROCESSCATEGORYID,PC.DESCRIPTION AS CAT_DES,P.DEPENDANCYSTATUS,P.RUNNINGONMAIN,P.RUNNINGONSUB,"
                    + "P.PROCESSTYPE,P.STATUS,P.CREATEDTIME,"
                    + "P.LASTUPDATEDTIME,P.LASTUPDATEDUSER FROM EODPROCESS P,EODPROCESSCATEGORY PC WHERE PC.PROCESSCATEGORYID = P.PROCESSCATEGORYID";
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
    /**
     * update EOD Process
     * @param bean
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
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

    public List<StatusBean> getStatus(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<StatusBean> statusList;
        try {
            getAllByCatStat = con.prepareStatement("SELECT EOS.ADMINCODE,EOS.DESCRIPTION FROM ECMS_ONLINE_STATUS EOS WHERE EOS.CATEGORYCODE='4' ");

            statusList = new ArrayList<StatusBean>();

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                StatusBean status = new StatusBean();

                status.setStatusCode(rs.getString(1));
                status.setDescription(rs.getString(2));

                statusList.add(status);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return statusList;
    }
}
