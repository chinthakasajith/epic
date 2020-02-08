/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.CardProductMgtPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class CardProductMgtManager {

    private Connection CMSCon;
    private Connection CMSConOnline;
    private SystemAuditManager sysAuditManager;
    private CardProductMgtPersistance cardProductMgtPersist = null;
    private List<String> AssignBinList = null;
    private List<String> NotAssignBinList = null;
    private List<String> cardProcuctBinList = null;
    private HashMap<String, String> cardTypeList = null;
    private List<CardProductBean> cardProductBeanLst = null;
    private HashMap<String, String> cardDomainList = null;

    /**
     * to retrieve all the card types
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAllCardTypeList() throws Exception {
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTypeList = cardProductMgtPersist.getAllCardTypeList(CMSCon);
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

    public HashMap<String, String> getAllCardDomainList() throws Exception {
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardDomainList = cardProductMgtPersist.getAllCardDomainList(CMSCon);
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
        return cardDomainList;
    }

    /**
     * to retrieve all the card product details
     * @return
     * @throws Exception 
     */
    public List<CardProductBean> getAllCardProductDetailsList() throws Exception {
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardProductBeanLst = cardProductMgtPersist.getAllCardProductDetailsList(CMSCon);
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
        return cardProductBeanLst;
    }

    /**
     * to insert a new card product
     * @param cardProductBean
     * @param assignBin
     * @param systemAuditBean
     * @param prodCode
     * @return
     * @throws Exception 
     */
    public int insertCardProduct(CardProductBean cardProductBean,List<CardProductBean> assignBinList, SystemAuditBean systemAuditBean, String prodCode) throws Exception {

        int flag1 = 0;
        int flag2 = 0;
        int flag = 0;

        try {

            cardProductMgtPersist = new CardProductMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag1 = cardProductMgtPersist.insertCardProduct(CMSCon, cardProductBean);
            flag2 = cardProductMgtPersist.insertCardProductBin(CMSCon, assignBinList, prodCode);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            if (flag1 > 0 && flag2 > 0) {
                flag = 1;
            }else{
            throw new Exception();
            
            }
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



        return flag;
    }

    /**
     * to update an existing card product
     * @param cardProductBean
     * @param assignBin
     * @param systemAuditBean
     * @param prodCode
     * @return
     * @throws Exception 
     */
    public int updateCardProduct(CardProductBean cardProductBean, List<CardProductBean> assignBinList, SystemAuditBean systemAuditBean, String prodCode) throws Exception {

        int row = -1;

        try {

            cardProductMgtPersist = new CardProductMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            row = cardProductMgtPersist.updateCardProduct(CMSCon, cardProductBean);
            row = cardProductMgtPersist.updateCardProductBin(CMSCon, assignBinList, prodCode);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return row;
    }

    /**
     * to delete a card product
     * @param cardProductBean
     * @param systemAuditBean
     * @return
     * @throws Exception 
     */
    public boolean deleteCardProduct(CardProductBean cardProductBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean flag = false;

        try {

            cardProductMgtPersist = new CardProductMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = cardProductMgtPersist.deleteCardProduct(CMSCon, cardProductBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return flag;
    }

    /**
     * to retrieve the BINs in a given card type which are not assigned to the given product
     * @param category
     * @param prodCode
     * @return
     * @throws Exception 
     */
    public List<CardProductBean> getNotAssignBinList(String cardType, String cardDomain, String product) throws Exception {
        List<CardProductBean> BinList;
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            BinList = cardProductMgtPersist.getNotAssignBinList(CMSCon, cardType, cardDomain, product);

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
        return BinList;
    }

    /**
     * to retrieve the assigned BINs for the given card product
     * @param prodCode
     * @return
     * @throws Exception 
     */
    public List<CardProductBean> getAssignBinList(String prodCode) throws Exception {
        List<CardProductBean> beanList;
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            beanList = cardProductMgtPersist.getAssignBinList(CMSCon, prodCode);

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
        return beanList;
    }

    /**
     * to retrieve the BINs for the given card type
     * @param category
     * @return
     * @throws Exception 
     */
    public List<CardProductBean> getCardProductBins(String category, String domain) throws Exception {
        List<CardProductBean> BinList = null;
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            BinList = cardProductMgtPersist.getCardroductBins(CMSCon, category, domain);

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
        return BinList;
    }
    
    /**
     * to retrieve the BINs for the given card type
     * @param category
     * @return
     * @throws Exception 
     */
    public List<CardProductBean> getCardProductBinsLIst(String bin) throws Exception {
        List<CardProductBean> BinList = null;
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            BinList = cardProductMgtPersist.getCardProductBinsLIst(CMSCon, bin);

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
        return BinList;
    }
  
    public List<CardProductBean> getCardKeyLIst(String cardProductId ,String binId) throws Exception {
        List<CardProductBean> cardKeyList = null;
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardKeyList = cardProductMgtPersist.getCardKeyLIst(CMSCon, cardProductId,binId);

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
        return cardKeyList;
    }

    public List<CardProductBean> getAllAssignCardKeyList(int cardKeyId) throws Exception {
        List<CardProductBean> cardKeyList = null;
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            cardKeyList = cardProductMgtPersist.getAllAssignCardKeyList(CMSConOnline, cardKeyId);

            CMSConOnline.commit();
        } catch (Exception ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSConOnline != null) {
                try {
                    CMSConOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return cardKeyList;
    }
    public List<CardProductBean> getAllNotAssignCardKeyList(int cardKeyId) throws Exception {
        List<CardProductBean> cardKeyList = null;
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            cardKeyList = cardProductMgtPersist.getAllNotAssignCardKeyList(CMSConOnline,cardKeyId);

            CMSConOnline.commit();
        } catch (Exception ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSConOnline != null) {
                try {
                    CMSConOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return cardKeyList;
    }  
  public List<CardProductBean> getCardKeyLIst() throws Exception {
        List<CardProductBean> cardKeyList = null;
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            cardKeyList = cardProductMgtPersist.getCardKeyLIst(CMSConOnline);

            CMSConOnline.commit();
        } catch (Exception ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSConOnline != null) {
                try {
                    CMSConOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return cardKeyList;
    }    
    
   public List<CardProductBean> getAllCardProductBinList() throws Exception {
        List<CardProductBean> cardKeyList = null;
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardKeyList = cardProductMgtPersist.getAllCardProductBinList(CMSCon);

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
        return cardKeyList;
    }
   public List<CardProductBean> getAllProductionModeList() throws Exception {
        List<CardProductBean> productionModeList = null;
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            productionModeList = cardProductMgtPersist.getAllProductionModeList(CMSConOnline);

            CMSConOnline.commit();
        } catch (Exception ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSConOnline != null) {
                try {
                    CMSConOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return productionModeList;
    }

   public List<CardProductBean> getProductionModeBaseCardList(String productionModeCode) throws Exception {
        List<CardProductBean> cardList = null;
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            cardList = cardProductMgtPersist.getProductionModeBaseCardList(CMSConOnline , productionModeCode);

            CMSConOnline.commit();
        } catch (Exception ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSConOnline != null) {
                try {
                    CMSConOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return cardList;
    }

   public List<CardProductBean> getProductionModeList(String cardKeyId) throws Exception {
        List<CardProductBean> cardList = null;
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            cardList = cardProductMgtPersist.getProductionModeList(CMSConOnline , cardKeyId);

            CMSConOnline.commit();
        } catch (Exception ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSConOnline != null) {
                try {
                    CMSConOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return cardList;
    }   
   
    public HashMap<String,String> getCardCategoryList() throws Exception {

        HashMap<String,String> map = new HashMap<String,String>();
        try {
            cardProductMgtPersist = new CardProductMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            map = cardProductMgtPersist.getCardCategoryList(CMSCon);
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
        return map;
    }
    
}
