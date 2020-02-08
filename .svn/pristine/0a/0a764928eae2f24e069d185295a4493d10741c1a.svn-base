/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.persistance;

import com.epic.cms.admin.controlpanel.profilemgt.bean.BillingStatementProfileBean;
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
public class BillingStatementProfilePersistance {

    private ResultSet rs;

    public List<BillingStatementProfileBean> getBillingStatementProfDetails(Connection cmsCon) throws Exception {

        List<BillingStatementProfileBean> billingStatementList = new ArrayList<BillingStatementProfileBean>();
        PreparedStatement ps = null;

        try {
            String query = " SELECT BSP.PROFILECODE, BSP.DESCRIPTION, BSP.GRACEPERIOD, BSP.MINIMUMDUEFLATAMOUNT, BSP.MINIMUMDUEPERCENTAGE, "
                    + " BSP.MINIMUMDUECOMBINATION, BSP.STATEMENTGENARATIONSTATUS, BSP.CONDITIONALBALANCE, BSP.CONDITIONALCRORDR, BSP.NUMBEROFACTIVITY, "
                    + " BSP.STATUS, ST.DESCRIPTION AS STDES, BSP.LASTUPDATEDUSER, BSP.LASTUPDATEDTIME "
                    + " FROM BILLINGSTATEMENTPROFILE BSP, STATUS ST WHERE BSP.STATUS=ST.STATUSCODE ";

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                BillingStatementProfileBean bean = new BillingStatementProfileBean();
                bean.setProfileCode(rs.getString("PROFILECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setGracePeroid(rs.getString("GRACEPERIOD"));
                bean.setMinimumDueFlatAmount(rs.getString("MINIMUMDUEFLATAMOUNT"));
                bean.setMinimumDuePercentage(rs.getString("MINIMUMDUEPERCENTAGE"));
                bean.setMinimumDueCombination(rs.getString("MINIMUMDUECOMBINATION"));
                bean.setStatementGenerationStatus(rs.getString("STATEMENTGENARATIONSTATUS"));
                bean.setConditionalBalance(rs.getString("CONDITIONALBALANCE"));
                bean.setConditionalCrOrDr(rs.getString("CONDITIONALCRORDR"));
                bean.setNumberOfActivity(rs.getString("NUMBEROFACTIVITY"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STDES"));

                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));

                billingStatementList.add(bean);
            }

            return billingStatementList;

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

    public int addBillingStatementProfile(Connection cmsCon, BillingStatementProfileBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO BILLINGSTATEMENTPROFILE (PROFILECODE,DESCRIPTION,GRACEPERIOD,MINIMUMDUEFLATAMOUNT,MINIMUMDUEPERCENTAGE,"
                    + "MINIMUMDUECOMBINATION,STATEMENTGENARATIONSTATUS,CONDITIONALBALANCE,CONDITIONALCRORDR,NUMBEROFACTIVITY,STATUS,LASTUPDATEDUSER,"
                    + "LASTUPDATEDTIME,CREATEDTIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE)";

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, bean.getProfileCode());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getGracePeroid());
            ps.setString(4, bean.getMinimumDueFlatAmount());
            ps.setString(5, bean.getMinimumDuePercentage());
            ps.setString(6, bean.getMinimumDueCombination());
            ps.setString(7, bean.getStatementGenerationStatus());
            ps.setString(8, bean.getConditionalBalance());
            ps.setString(9, bean.getConditionalCrOrDr());
            ps.setString(10, bean.getNumberOfActivity());
            ps.setString(11, bean.getStatus());

            ps.setString(12, bean.getLastUpdatedUser());
            ps.setTimestamp(13, new Timestamp(bean.getLastUpdatedTime().getTime()));

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

    public int deleteProfile(Connection cmsCon, String id) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM BILLINGSTATEMENTPROFILE WHERE PROFILECODE=? ";

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

    public int updateProfile(Connection cmsCon, BillingStatementProfileBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "UPDATE BILLINGSTATEMENTPROFILE SET DESCRIPTION=?,GRACEPERIOD=?,MINIMUMDUEFLATAMOUNT=?,MINIMUMDUEPERCENTAGE=?,"
                    + "MINIMUMDUECOMBINATION=?,STATEMENTGENARATIONSTATUS=?,CONDITIONALBALANCE=?,CONDITIONALCRORDR=?,NUMBEROFACTIVITY=?,"
                    + "STATUS=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=? "
                    + " WHERE PROFILECODE=?";

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getGracePeroid());
            ps.setString(3, bean.getMinimumDueFlatAmount());
            ps.setString(4, bean.getMinimumDuePercentage());
            ps.setString(5, bean.getMinimumDueCombination());
            ps.setString(6, bean.getStatementGenerationStatus());
            ps.setString(7, bean.getConditionalBalance());
            ps.setString(8, bean.getConditionalCrOrDr());
            ps.setString(9, bean.getNumberOfActivity());
            ps.setString(10, bean.getStatus());

            ps.setString(11, bean.getLastUpdatedUser());
            ps.setTimestamp(12, new Timestamp(bean.getLastUpdatedTime().getTime()));

            ps.setString(13, bean.getProfileCode());

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
