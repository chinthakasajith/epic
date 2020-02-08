/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.persistance;

import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionProfileTransactionBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionTypeBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author badrika
 */
public class TransactionProfilePersistance {

    private ResultSet rs;

    public List<TransactionProfileBean> getTransactionProfileDetails(Connection cmsCon) throws Exception {

        List<TransactionProfileBean> tranProfileList = new ArrayList<TransactionProfileBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT TP.PROFILECODE, TP.DESCRIPTION, TP.STATUS, ST.DESCRIPTION AS STDES, TP.LASTUPDATEDUSER, TP.LASTUPDATEDDTIME "
                    + "FROM TRANSACTIONPROFILE TP, STATUS ST WHERE TP.STATUS=ST.STATUSCODE ";

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                TransactionProfileBean bean = new TransactionProfileBean();
                bean.setProfileCode(rs.getString("PROFILECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setStatusDes(rs.getString("STDES"));
                bean.setStatus(rs.getString("STATUS"));

                bean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDDTIME"));

                tranProfileList.add(bean);
            }

            return tranProfileList;

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
     * to take the list of status(active and de active)
     * @param cmsCon
     * @return
     * @throws Exception 
     */
    public List<StatusBean> getStatustList(Connection cmsCon) throws Exception {

        List<StatusBean> statustList = new ArrayList<StatusBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT STATUSCODE, DESCRIPTION FROM STATUS WHERE STATUSCATEGORY = ?";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, StatusVarList.GENESTATUCAT);
            rs = ps.executeQuery();

            while (rs.next()) {
                StatusBean bean = new StatusBean();

                bean.setStatusCode(rs.getString("STATUSCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                statustList.add(bean);
            }

            return statustList;

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

    public List<TransactionTypeBean> getTransactionTypes(Connection cmsCon) throws Exception {

        List<TransactionTypeBean> transactionList = new ArrayList<TransactionTypeBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT TRANSACTIONCODE, DESCRIPTION FROM TRANSACTIONTYPE WHERE FINANCIALSTATUS = 'YES'";

            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                TransactionTypeBean bean = new TransactionTypeBean();

                bean.setTransactionCode(rs.getString("TRANSACTIONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                transactionList.add(bean);
            }

            return transactionList;

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

    //getTransactionsByProfileCode
    public List<TransactionProfileTransactionBean> getTransactionsByProfileCode(Connection cmsCon, String id) throws Exception {

        List<TransactionProfileTransactionBean> transList = new ArrayList<TransactionProfileTransactionBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT TPT.TXNCODE , TP.DESCRIPTION FROM TRANSACTIONPROFILETRANSACTION TPT,TRANSACTIONTYPE TP "
                    + "WHERE TPT.TXNCODE = TP.TRANSACTIONCODE AND TPT.PROFILECODE = ?";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                TransactionProfileTransactionBean bean = new TransactionProfileTransactionBean();

                bean.setTxnCode(rs.getString("TXNCODE"));
                bean.setTxnDes(rs.getString("DESCRIPTION"));

                transList.add(bean);
            }

            return transList;

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

    //getNotAssignedTrans
    public List<TransactionProfileTransactionBean> getNotAssignedTrans(Connection cmsCon, String id) throws Exception {

        List<TransactionProfileTransactionBean> notAssignedtransList = new ArrayList<TransactionProfileTransactionBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT TRANSACTIONCODE, DESCRIPTION FROM TRANSACTIONTYPE "
                    + "WHERE FINANCIALSTATUS = 'YES' AND TRANSACTIONCODE NOT IN(SELECT TPT.TXNCODE FROM TRANSACTIONPROFILETRANSACTION TPT,TRANSACTIONTYPE TP "
                    + "WHERE TPT.TXNCODE = TP.TRANSACTIONCODE AND TPT.PROFILECODE = ?)";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                TransactionProfileTransactionBean bean = new TransactionProfileTransactionBean();

                bean.setTxnCode(rs.getString("TRANSACTIONCODE"));
                bean.setTxnDes(rs.getString("DESCRIPTION"));

                notAssignedtransList.add(bean);
            }

            return notAssignedtransList;

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

    public int addProfile(Connection cmsCon, TransactionProfileBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO TRANSACTIONPROFILE (PROFILECODE,DESCRIPTION,STATUS,LASTUPDATEDUSER,LASTUPDATEDDTIME,CREATEDTIME) VALUES(?,?,?,?,?,SYSDATE)";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getProfileCode());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getStatus());
            ps.setString(4, bean.getLastUpdatedUser());
            ps.setTimestamp(5, new Timestamp(bean.getLastUpdatedTime().getTime()));

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

    public int updateProfile(Connection cmsCon, TransactionProfileBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "UPDATE TRANSACTIONPROFILE SET DESCRIPTION = ?, STATUS = ?, LASTUPDATEDUSER = ?, LASTUPDATEDDTIME = ? WHERE PROFILECODE = ? ";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getStatus());
            ps.setString(3, bean.getLastUpdatedUser());

            ps.setTimestamp(4, new Timestamp(bean.getLastUpdatedTime().getTime()));
            ps.setString(5, bean.getProfileCode());

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

    public int addTransactions(Connection cmsCon, String profileCode, String tranCode) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO TRANSACTIONPROFILETRANSACTION (PROFILECODE,TXNCODE) VALUES(?,?)";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, profileCode);
            ps.setString(2, tranCode);

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

    public Boolean deleteTrans(Connection con, String pageCode) throws Exception {
        Boolean status = false;
        PreparedStatement updateStat = null;
        try {


            updateStat = con.prepareStatement("DELETE FROM TRANSACTIONPROFILETRANSACTION WHERE PROFILECODE = ?");

            updateStat.setString(1, pageCode);

            updateStat.executeUpdate();

            status = true;
        } catch (Exception ex) {
            status = false;
            throw ex;
        } finally {
            updateStat.close();
        }
        return status;
    }

    public boolean deleteProfile(Connection con, String page) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = con.prepareStatement("DELETE TRANSACTIONPROFILE WHERE PROFILECODE=? ");
            deleteStat.setString(1, page);

            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            deleteStat.close();
        }
        return success;
    }
    
    //getTransDesbyTxnCode
//    public String getTransDesbyTxnCode(Connection con, String id) throws Exception {
//        String des;
//        PreparedStatement ps = null;
//        try {
//
//            ps = con.prepareStatement("SELECT DESCRIPTION FROM TRANSACTIONTYPE WHERE TRANSACTIONCODE = ? ");
//            ps.setString(1, id);
//
//            rs = ps.executeQuery();
//            
//            while (rs.next()) {
//                des=rs.getString("DESCRIPTION");              
//                
//            }
//            
//            
//
//        } catch (SQLException ex) {
//            throw ex;
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                    ps.close();
//                }
//            } catch (Exception e) {
//                throw e;
//            }
//        }
//        return des;
//        
//    }
    
    
}
