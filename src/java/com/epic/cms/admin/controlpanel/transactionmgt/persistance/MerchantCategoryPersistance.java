
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *  this class contains all the database queries which is relevant to MCC table
 * @author nisansala
 */
public class MerchantCategoryPersistance {
    private ResultSet rs = null;
    
    
    /**
     * retrieve all the merchant details
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<MerchantCategoryBean> getAllMrchntCatgryData(Connection con) throws SQLException, Exception{
        PreparedStatement ps = null; 
        
        try{
            List<MerchantCategoryBean> mrchntList = new ArrayList<MerchantCategoryBean>();
            String query = "SELECT MC.DESCRIPTION,MC.MCCCODE,MC.CLASS,MC.STATUS,ST.DESCRIPTION AS STDESCRIPTION,MC.LASTUPDATEDUSER,"
                    + "MC.LASTUPDATEDTIME,MC.CREATEDTIME FROM MCC MC, STATUS ST WHERE MC.STATUS=ST.STATUSCODE ORDER BY MC.DESCRIPTION ASC";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                MerchantCategoryBean bean = new MerchantCategoryBean();
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setmCCCode(rs.getString("MCCCODE"));
                bean.setmClass(rs.getString("CLASS"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STDESCRIPTION"));
                bean.setLastUpdateUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdateDate(rs.getTimestamp("LASTUPDATEDTIME"));
                bean.setCreateDate(rs.getDate("CREATEDTIME"));
                mrchntList.add(bean);
            }
            return mrchntList;
        }catch(SQLException ex){
            throw ex;
        }finally {
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
     * update the values in the MCC table
     * @param bean
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
     
     
   
    public int updateMrchntCatgryData(MerchantCategoryBean bean, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE MCC SET "
                    + "DESCRIPTION=?,CLASS=? ,"
                    + "STATUS=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE "
                    + "WHERE MCCCODE=? ";

            ps = con.prepareStatement(query);

            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getmClass());
            ps.setString(3, bean.getStatus());
            ps.setString(4, bean.getLastUpdateUser());
          //  ps.setTimestamp(5, SystemDateTime.getSystemDataAndTime());                     
            ps.setString(5, bean.getmCCCode());

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
     * insert new values to the MCC table
     * @param bean
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    
    public Boolean insertNewMrchntCatgryData(MerchantCategoryBean bean, Connection con) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO MCC (DESCRIPTION,"
                    + "MCCCODE,CLASS,STATUS,LASTUPDATEDUSER,"
                    + "CREATEDTIME) VALUES(?,?,?,?,?,SYSDATE)";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getmCCCode());
            ps.setString(3, bean.getmClass());
            ps.setString(4, bean.getStatus());
            ps.setString(5, bean.getLastUpdateUser());            
//            ps.setTimestamp(6,SystemDateTime.getSystemDataAndTime());            

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
     * delete merchant category data from MCC table
     * @param con
     * @param MCCCode
     * @return
     * @throws SQLException
     * @throws Exception 
     */
     
    
    public int deleteMrchntCatgryData(Connection con, String MCCCode) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM MCC where MCCCODE =?";

            ps = con.prepareStatement(query);
            ps.setString(1, MCCCode);

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

}
