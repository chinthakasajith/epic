/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.cooperate.businesslogic;

import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardCategoryBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SearchUserAssignAppBean;
import com.epic.cms.camm.applicationproccessing.capturedata.persistance.CustomerPersonalPersistance;
import com.epic.cms.camm.applicationproccessing.cooperate.persistance.CorporateCardPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author badrika
 */
public class CorporateCardManager {
    private CorporateCardPersistance persistance;
    private Connection CMSCon;
    private List<ApplicationAssignBean> searchList;
    private List<CardCategoryBean> cardCategoryLst;
    
     public List<ApplicationAssignBean> corparateCardApplicationsSearch(SearchUserAssignAppBean searchBean) throws Exception {
        try {
            persistance = new CorporateCardPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = persistance.corparateCardApplicationsSearch(CMSCon, searchBean);
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;

        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }


        return searchList;
    }
     
       public List<CardCategoryBean> getCardCategories() throws Exception {
       
        try {
            persistance = new CorporateCardPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            cardCategoryLst = persistance.getCardCategories(CMSCon);
            CMSCon.commit();
            return cardCategoryLst;
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
    }
    
}
