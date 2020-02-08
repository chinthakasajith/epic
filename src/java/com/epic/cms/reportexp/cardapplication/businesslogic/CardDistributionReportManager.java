/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.reportexp.cardapplication.bean.CardDistributionReportBean;
import com.epic.cms.reportexp.cardapplication.persistance.CardDistributionReportPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nalin
 */
public class CardDistributionReportManager {
    
    private Connection CMSCon;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> cardProductList = null;
    private CardDistributionReportPersistance disperr = null;
    private List<CardDistributionReportBean> distributeList = null;
    
     public HashMap<String, String> getCardType() throws Exception {
        try {

            disperr = new CardDistributionReportPersistance();
            cardTypeList = new HashMap<String, String>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTypeList = disperr.getCardType(CMSCon);
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
        return cardTypeList;
    }
     
      public HashMap<String, String> getCardProduct() throws Exception {
        try {

            disperr = new CardDistributionReportPersistance();
            cardProductList = new HashMap<String, String>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardProductList = disperr.getCardProduct(CMSCon);
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
        return cardProductList;
    }
       public HashMap<String, String> getCardProductByCardType(String cardType) throws Exception {
        try {

            disperr = new CardDistributionReportPersistance();
            cardProductList = new HashMap<String, String>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardProductList = disperr.getCardProductByCardType(CMSCon, cardType );
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
        return cardProductList;
    }
       
       public List<CardDistributionReportBean> searchCardDistributeReport(CardDistributionReportBean distribute) throws Exception {

        try {
            disperr = new CardDistributionReportPersistance();
            distributeList = new ArrayList<CardDistributionReportBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            distributeList = disperr.searchCardDistributeReport(CMSCon, distribute);

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
        return distributeList;
    }
    
}
