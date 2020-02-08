/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BankBranchBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class use to wrote all the persistence class that relate to bank branch management 
 * @author ayesh
 */
public class BankBranchMgtPersistence {

    private ResultSet rs = null;

    /**
     * get all bank detail including name and code
     * @param con BankBranchBean-List
     * @return
     * @throws Exception 
     */
    public List<BankBranchBean> getBankNames(Connection con) throws Exception {

        List<BankBranchBean> allBank = new ArrayList<BankBranchBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT BNK.BANKCODE,BNK.BANKNAME FROM BANK BNK ";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                BankBranchBean bean = new BankBranchBean();
                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBankName(rs.getString("BANKNAME"));

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
     * add new branch to database
     * @param con
     * @param bean
     * @return int-number of rows that added
     * @throws Exception 
     */
    public int addNewBranch(Connection con, BankBranchBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO BANKBRANCH BB (BB.BRANCHCODE, BB.DESCRPTION,"
                    + "BB.ADDRESS1, BB.ADDRESS2,BB.ADDRESS3,BB.CONTACTPERSON,BB.CONTACTNO,BB.BANKCODE,BB.LASTUPDATEDUSER,BB.DISPLAYDIGIT)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getBranchName());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getAddress1());
            ps.setString(4, bean.getAddress2());
            ps.setString(5, bean.getAddress3());
            ps.setString(6, bean.getContactPer());
            ps.setString(7, bean.getContactNo());
            ps.setString(8, bean.getBankCode());
            ps.setString(9, bean.getLastupdateuser());
            ps.setString(10, bean.getDisplayDigit());

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
     * update bank branch details
     * @param con
     * @param bean
     * @return int-number of rows that updated
     * @throws Exception 
     */
    public int updateBranch(Connection con, BankBranchBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE BANKBRANCH SET DESCRPTION=?,ADDRESS1=?,"
                    + "ADDRESS2=?,ADDRESS3=?,"
                    + "CONTACTPERSON=?,CONTACTNO=?,"
                    + "LASTUPDATEDUSER=?,LASTUPDATEDTIME=?,DISPLAYDIGIT=? WHERE BRANCHCODE=? AND BANKCODE=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getAddress1());
            ps.setString(3, bean.getAddress2());
            ps.setString(4, bean.getAddress3());
            ps.setString(5, bean.getContactPer());
            ps.setString(6, bean.getContactNo());
            //ps.setString(7, bean.getBankCode());
            ps.setString(7, bean.getLastupdateuser());
            ps.setTimestamp(8, SystemDateTime.getSystemDataAndTime());
            ps.setString(9, bean.getDisplayDigit());
            
            ps.setString(10, bean.getBranchName());            
            ps.setString(11, bean.getBankCode());

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
     * get all branch list
     * @param con BankBranchBean-List
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<BankBranchBean> getAllBranch(Connection con) throws SQLException, Exception {


        List<BankBranchBean> allBrach = new ArrayList<BankBranchBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT BB.BRANCHCODE,BB.DESCRPTION,BB.ADDRESS1,"
                    + "BB.ADDRESS2,BB.ADDRESS3,BB.CONTACTPERSON,BB.CONTACTNO,BB.BANKCODE,BB.DISPLAYDIGIT,"
                    + "B.BANKNAME,BB.LASTUPDATEDUSER AS USERR FROM BANKBRANCH BB,BANK B WHERE BB.BANKCODE=B.BANKCODE";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                BankBranchBean bean = new BankBranchBean();
                bean.setBranchName(rs.getString("BRANCHCODE"));
                bean.setAddress1(rs.getString("ADDRESS1"));
                bean.setAddress2(rs.getString("ADDRESS2"));
                bean.setAddress3(rs.getString("ADDRESS3"));
                bean.setDescription(rs.getString("DESCRPTION"));
                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBankName(rs.getString("BANKNAME"));
                bean.setContactPer(rs.getString("CONTACTPERSON"));
                bean.setContactNo(rs.getString("CONTACTNO"));
                bean.setLastupdateuser(rs.getString("USERR"));
                bean.setDisplayDigit(rs.getString("DISPLAYDIGIT"));
                allBrach.add(bean);
            }

            return allBrach;
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
     * get selected branch details
     * @param con
     * @return BankBranchBean
     * @throws SQLException
     * @throws Exception 
     */
    public BankBranchBean getSelectedBranch(Connection con, String id, String bankCode) throws SQLException, Exception {

        BankBranchBean bean = new BankBranchBean();

        PreparedStatement ps = null;
        try {
            String query = "SELECT BB.BRANCHCODE,BB.DESCRPTION,BB.ADDRESS1,BB.ADDRESS2,BB.ADDRESS3,"
                    + "BB.CONTACTNO,BB.CONTACTPERSON,BB.BANKCODE,BB.DISPLAYDIGIT,B.BANKNAME,BB.LASTUPDATEDUSER,BB.LASTUPDATEDTIME  "
                    + "FROM BANKBRANCH BB,BANK B WHERE BRANCHCODE=? AND BB.BANKCODE=? AND BB.BANKCODE=B.BANKCODE";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, bankCode);
            rs = ps.executeQuery();

            while (rs.next()) {

                bean.setBranchName(rs.getString("BRANCHCODE"));
                bean.setAddress1(rs.getString("ADDRESS1"));
                bean.setAddress2(rs.getString("ADDRESS2"));
                bean.setAddress3(rs.getString("ADDRESS3"));
                bean.setDescription(rs.getString("DESCRPTION"));
                bean.setBankName(rs.getString("BANKNAME"));
                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setContactPer(rs.getString("CONTACTPERSON"));
                bean.setContactNo(rs.getString("CONTACTNO"));
                bean.setLastupdateuser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastupdatetime(rs.getString("LASTUPDATEDTIME"));             
                bean.setDisplayDigit(rs.getString("DISPLAYDIGIT"));             

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

    /**
     * delete branch by selected code
     * @param con
     * @param id
     * @return int - number of rows
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteBranchRate(Connection con, String id,String bank) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM BANKBRANCH BB where BRANCHCODE=? AND BANKCODE=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, bank);
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
}
