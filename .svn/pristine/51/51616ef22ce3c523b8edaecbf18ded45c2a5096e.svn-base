/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.application.common.persistance;

import com.epic.cms.admin.controlpanel.profilemgt.bean.FeeProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.InterestprofileBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardCategoryBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class StatusPersistence {

    private ResultSet rs;
    private List<StatusBean> statusList;
    private List<CardCategoryBean> cardCategoryList;

    public List<StatusBean> getStatusByCategoryCode(Connection con, String categoryCode) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT IST.STATUSCODE,IST.DESCRIPTION,IST.STATUSCATEGORY,IST.SORTKEY FROM STATUS IST WHERE IST.STATUSCATEGORY=? ");

            getAllByCatStat.setString(1, categoryCode);


            statusList = new ArrayList<StatusBean>();

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                StatusBean status = new StatusBean();

                status.setStatusCode(rs.getString(1));
                status.setDescription(rs.getString(2));
                status.setStatusCategoryCode(rs.getString(3));
                status.setSortKey(rs.getInt(4));
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

    public List<CardCategoryBean> getCardCategoryList(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT CATEGORYCODE,DESCRIPTION,STATUS FROM CARDCATEGORY WHERE CARDDOMAIN = ? ORDER BY DESCRIPTION");

            getAllByCatStat.setString(1, StatusVarList.CREDIT);
            cardCategoryList = new ArrayList<CardCategoryBean>();

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                CardCategoryBean bean = new CardCategoryBean();

                bean.setCategoryCode(rs.getString("CATEGORYCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setStatus(rs.getString("STATUS"));

                cardCategoryList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardCategoryList;
    }

    public List<InterestprofileBean> getInterestProfilelist(Connection con) throws Exception {
        List<InterestprofileBean> interestprofilelist = new ArrayList<InterestprofileBean>();
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT INTERESTPROFILECODE,DESCRIPTION FROM INTERESTPROFILE");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                InterestprofileBean bean = new InterestprofileBean();

                bean.setInterestFrofileCode(rs.getString("INTERESTPROFILECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                interestprofilelist.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return interestprofilelist;
    }

    public List<FeeProfileBean> getFeeProfileList(Connection con) throws Exception {
        List<FeeProfileBean> feeProfileList = new ArrayList<FeeProfileBean>();
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT CARDHOLDERFEEPROFILECODE,DESCRIPTION FROM CARDHOLDERFEE");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                FeeProfileBean bean = new FeeProfileBean();

                bean.setFeeProCode(rs.getString("CARDHOLDERFEEPROFILECODE"));
                bean.setFeeProDes(rs.getString("DESCRIPTION"));

                feeProfileList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return feeProfileList;
    }

    public List<StatusBean> getStatusByStatusCodeList(Connection con, String statusCodeList) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT IST.STATUSCODE,IST.DESCRIPTION,IST.STATUSCATEGORY,IST.SORTKEY FROM STATUS IST WHERE IST.STATUSCODE IN ( '" + statusCodeList + "' )  ");

            statusList = new ArrayList<StatusBean>();

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                StatusBean status = new StatusBean();

                status.setStatusCode(rs.getString(1));
                status.setDescription(rs.getString(2));
                status.setStatusCategoryCode(rs.getString(3));
                status.setSortKey(rs.getInt(4));
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

    public String getTitle(Connection con, String pagecode) throws Exception {
        PreparedStatement getTitleStat = null;
        String title = "";
        String section = "";
        String pageDes = "";
        try {
//            getTitleStat =  con.prepareStatement("SELECT SPG.SECTIONCODE ,ASE.APPLICATIONCODE FROM APPLICATIONSECTION ASE,SECTIONPAGE SPG WHERE SPG.SECTIONCODE = ASE.SECTIONCODE AND SPG.PAGECODE= '" + pagecode + "'");
            getTitleStat = con.prepareStatement("SELECT SPG.SECTIONCODE,ASE.APPLICATIONCODE,PG.DESCRIPTION,SE.DESCRIPTION AS SDES FROM APPLICATIONSECTION ASE,SECTIONPAGE SPG,PAGE PG,SECTION SE WHERE SPG.SECTIONCODE = ASE.SECTIONCODE AND SPG.PAGECODE='" + pagecode + "' AND PG.PAGECODE='" + pagecode + "' AND SE.SECTIONCODE=SPG.SECTIONCODE");
            rs = getTitleStat.executeQuery();



            if (rs.next()) {

                pageDes = rs.getString("DESCRIPTION");
                section = rs.getString("SDES");

                title = section + " → " + pageDes;
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTitleStat.close();
        }
        return title;
    }

    public String getApplicationDescription(Connection con, String appcode) throws Exception {
        PreparedStatement getTitleStat = null;
        String appDes = "";
        try {
            getTitleStat = con.prepareStatement("SELECT upper(DESCRIPTION)as APPDES FROM APPLICATION WHERE APPLICATIONCODE='" + appcode + "'");
            rs = getTitleStat.executeQuery();



            if (rs.next()) {

                appDes = rs.getString("APPDES");


            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTitleStat.close();
        }
        return appDes;
    }
    
    public String getStatusDescription(Connection con, String code) throws Exception {
        PreparedStatement ps = null;
        String description = "";
        
        try {
            ps = con.prepareStatement("SELECT DESCRIPTION FROM STATUS WHERE STATUSCODE='" + code + "'");
            rs = ps.executeQuery();

            if (rs.next()) {
                description = rs.getString("DESCRIPTION");
            }
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return description;
    }

    public List<StatusBean> getStatusByCategoryCode(Connection CMSCon, String dath, String genr) throws SQLException {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = CMSCon.prepareStatement("SELECT IST.STATUSCODE,IST.DESCRIPTION,IST.STATUSCATEGORY,IST.SORTKEY FROM STATUS IST WHERE IST.STATUSCATEGORY IN (?,?) ");

            getAllByCatStat.setString(1, dath); 
            getAllByCatStat.setString(2, genr);


            statusList = new ArrayList<StatusBean>();

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                StatusBean status = new StatusBean();

                status.setStatusCode(rs.getString(1));
                status.setDescription(rs.getString(2));
                status.setStatusCategoryCode(rs.getString(3));
                status.setSortKey(rs.getInt(4));
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
