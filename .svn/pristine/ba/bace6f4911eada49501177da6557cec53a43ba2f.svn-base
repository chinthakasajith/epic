/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.merchant.businesslogic;

import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AreaBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantPaymentModeBean;
import com.epic.cms.reportexp.merchant.bean.MerchantLocationReportBean;
import com.epic.cms.reportexp.merchant.bean.MerchantLocationSearchBean;
import com.epic.cms.reportexp.merchant.persistance.MerchantLocationReportPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ruwan_e
 */
public class MerchantLocationReportManager {

    MerchantLocationReportPersistance mlp = null;
    private List<StatusBean> statusList = null;
    private List<MerchantPaymentModeBean> paymentModeList = null;
    private List<AreaBean> areaList;
    private List<MerchantLocationSearchBean> searchBeansList;
    private Connection CMSCon;
    private MerchantLocationReportPersistance merchantLocationReportPersistance = null;

    public ArrayList<StatusBean> getStatusList() throws Exception {

        try {

            mlp = new MerchantLocationReportPersistance();
            statusList = new ArrayList<StatusBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            statusList = mlp.getStatusList(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return (ArrayList<StatusBean>) statusList;

    }

    public List<AreaBean> getAreaList() throws SQLException, Exception {
        try {

            mlp = new MerchantLocationReportPersistance();
            areaList = new ArrayList<AreaBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            areaList = mlp.getAreaList(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return areaList;
    }

    public List<MerchantPaymentModeBean> getPaymentModeList() throws Exception {

        try {

            mlp = new MerchantLocationReportPersistance();
            paymentModeList = new ArrayList<MerchantPaymentModeBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            paymentModeList = mlp.getPaymentModeList(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return paymentModeList;
    }

    public List<MerchantLocationSearchBean> searchMerchantLocationDetails(MerchantLocationSearchBean searchBean) throws Exception {
        try {

            merchantLocationReportPersistance = new MerchantLocationReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            searchBeansList = merchantLocationReportPersistance.searchMerchantLocationDetails(CMSCon, searchBean);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return searchBeansList;
    }

    public MerchantLocationReportBean getMerchantLocationById(String id) throws Exception {
        MerchantLocationReportBean sbean = null;
        try {

            merchantLocationReportPersistance = new MerchantLocationReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            sbean = merchantLocationReportPersistance.getMerchantLocationById(CMSCon, id);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return sbean;
    }
}
