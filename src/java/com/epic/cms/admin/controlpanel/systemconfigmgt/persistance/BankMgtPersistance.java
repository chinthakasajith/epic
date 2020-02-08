/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BankBean;
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
public class BankMgtPersistance {

    private ResultSet rs = null;

    /**
     * to take existing bank list to the user view
     * @param cmsCon
     * @return
     * @throws Exception 
     */
    public List<BankBean> getBankNames(Connection cmsCon) throws Exception {

        List<BankBean> allBank = new ArrayList<BankBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT BNK.BANKCODE,BNK.BANKNAME,ST.DESCRIPTION,BNK.LASTUPDATEDDATE,BNK.LASTUPDATEDUSER AS USERR FROM BANK BNK, STATUS ST WHERE BNK.STATUS=ST.STATUSCODE ";

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                BankBean bean = new BankBean();
                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBankName(rs.getString("BANKNAME"));
                bean.setStatusDes(rs.getString("DESCRIPTION"));
                bean.setLastUpdatedDate(rs.getTimestamp("LASTUPDATEDDATE"));
                bean.setLastUpdatedUser(rs.getString("USERR"));

                allBank.add(bean);
            }

            return allBank;

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
     * to add a new bank to the database
     * @param cmsCon
     * @param bean
     * @return
     * @throws Exception 
     */
    public int addNewBank(Connection cmsCon, BankBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO BANK (BANKCODE,BANKNAME,STATUS,CREATEDDATE,LASTUPDATEDUSER,LASTUPDATEDDATE) VALUES(?,?,?,SYSDATE,?,?)";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getBankCode());
            ps.setString(2, bean.getBankName());
            ps.setString(3, bean.getStatusDes());
            ps.setString(4, bean.getLastUpdatedUser());
            ps.setTimestamp(5, new Timestamp(bean.getLastUpdatedDate().getTime()));

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
     * to update bank details
     * @param cmsCon
     * @param bean
     * @return
     * @throws Exception 
     */
    public int updateBanks(Connection cmsCon, BankBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "UPDATE BANK SET BANKNAME=?,STATUS=?,LASTUPDATEDUSER=?,LASTUPDATEDDATE=? WHERE BANKCODE=?";

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, bean.getBankName());
            ps.setString(2, bean.getStatusDes());
            ps.setString(3, bean.getLastUpdatedUser());
            ps.setTimestamp(4, new Timestamp(bean.getLastUpdatedDate().getTime()));
            ps.setString(5, bean.getBankCode());

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

    /**
     * to delete a bank from database
     * @param cmsCon
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteBanks(Connection cmsCon, String id) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM BANK WHERE BANKCODE=? ";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, id);

            i = ps.executeUpdate();

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
        return i;
    }

    /**
     * to view selected bank details
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public BankBean viewSelectedBankDetails(Connection con, String id) throws SQLException, Exception {

        BankBean bean = new BankBean();

        PreparedStatement ps = null;
        try {
            String query = "SELECT BNK.BANKCODE, BNK.BANKNAME, BNK.STATUS, ST.DESCRIPTION, BNK.LASTUPDATEDDATE, BNK.LASTUPDATEDUSER "
                    + "FROM BANK BNK, STATUS ST WHERE BNK.STATUS = ST.STATUSCODE AND BNK.BANKCODE = ? ";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBankName(rs.getString("BANKNAME"));
                bean.setStatusDes(rs.getString("STATUS"));
                bean.setStatus(rs.getString("DESCRIPTION"));
                bean.setLastUpdatedDate(rs.getTimestamp("LASTUPDATEDDATE"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
            }
            return bean;

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
