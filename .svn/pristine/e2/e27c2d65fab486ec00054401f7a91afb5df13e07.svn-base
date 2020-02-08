package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationPinGenAndMailBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationSummaryBean;
import com.epic.cms.reportexp.cardapplication.bean.CardprdctBean;
import com.epic.cms.reportexp.cardapplication.persistance.ApplicationPinGenAndMailPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class ApplicationPinGenAndMailManager {

    private Connection CMSCon;
    private SystemUserBean sysUsrBean = null;
    private SystemAuditManager sysAuditManager = null;
    private HashMap<String, String> cardDomain = null;
    private HashMap<String, String> bnkBranches = null;
    private HashMap<String, String> cdProduct = null;
    private HashMap<String, String> productModes = null;
    private ApplicationPinGenAndMailPersistance appPinPer = null;
    private List<CurrencyBean> currency = null;
    private List<BulkCardRequestBean> reqList = null;
    private HashMap<String, String> appStatusList = null;
    private HashMap<String, String> cardDomainList = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> cardTypeList = null;
    private List<CardprdctBean> cardProductList = null;
    private HashMap<String, String> cardTypePinMethod = null;
    private HashMap<String, String> cardTypeProduct = null;
    private HashMap<String, String> pinGenTime = null;
    private HashMap<String, String> bnkCardNo = null; //
    private HashMap<String, String> bnkUserName = null; //
    private HashMap<String, String> fromDate = null; //
    private HashMap<String, String> toDate = null; //
    private HashMap<String, String> bnkPinMethod = null; //
    private List<ApplicationSummaryBean> searchList = null;

    public HashMap<String, String> getBranchNames() throws Exception {
        try {

            appPinPer = new ApplicationPinGenAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bnkBranches = appPinPer.getBranchNames(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return bnkBranches;
    }

    public HashMap<String, String> getPriorityLevel() throws Exception {
        try {
            appPinPer = new ApplicationPinGenAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            priorityLevelList = appPinPer.getPriorityLevels(CMSCon);
            CMSCon.close();
        } catch (Exception e) {
            try {
                CMSCon.rollback();
            } catch (Exception ex) {
                throw ex;
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
        return priorityLevelList;
    }

    public HashMap<String, String> getCardDomains() throws Exception {
        try {
            appPinPer = new ApplicationPinGenAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardDomainList = appPinPer.getCardDomains(CMSCon);
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
                } catch (SQLException sqx) {
                    throw sqx;
                }
            }
        }
        return cardDomainList;
    }

    public HashMap<String, String> getCardType() throws Exception {
        try {
            appPinPer = new ApplicationPinGenAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTypeList = appPinPer.getCardType(CMSCon);
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
                } catch (SQLException qex) {
                    throw qex;
                }
            }
        }
        return cardTypeList;
    }

    public List<CardprdctBean> getCardProductsByType(String cardType) throws Exception {
        try {
            appPinPer = new ApplicationPinGenAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardProductList = appPinPer.getCardProductByType(CMSCon, cardType);
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
                } catch (SQLException qex) {
                    throw qex;
                }
            }
        }
        return cardProductList;
    }

    public HashMap<String, String> getCardPinMethod() throws Exception {
        try {
            appPinPer = new ApplicationPinGenAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTypePinMethod = appPinPer.getCardPinMethod(CMSCon);
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
                } catch (SQLException sqx) {
                    throw sqx;
                }
            }
        }
        return cardTypePinMethod;
    }

    public HashMap<String, String> getCardProduct(String cardType) throws Exception {
        try {
            appPinPer = new ApplicationPinGenAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTypeProduct = appPinPer.getCardProduct(CMSCon, cardType);
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
                } catch (SQLException sqx) {
                    throw sqx;
                }
            }
        }
        return cardTypeProduct;
    }

    public HashMap<String, String> getPinGenTime() throws Exception {
        try {
            appPinPer = new ApplicationPinGenAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            pinGenTime = appPinPer.getPinGenTime(CMSCon);
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
                } catch (SQLException sqx) {
                    throw sqx;
                }
            }
        }
        return pinGenTime;
    }

    public HashMap<String, String> getCardNo() throws Exception {
        try {
            appPinPer = new ApplicationPinGenAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bnkCardNo = appPinPer.getCardNo(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } finally {
                if (CMSCon != null) {
                    try {
                        CMSCon.close();
                    } catch (SQLException sqx) {
                        throw sqx;
                    }
                }
            }
        }
        return bnkCardNo;
    }

    public HashMap<String, String> getUser() throws Exception {
        try {
            appPinPer = new ApplicationPinGenAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bnkUserName = appPinPer.getUser(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } finally {
                if (CMSCon != null) {
                    try {
                        CMSCon.close();
                    } catch (SQLException sqx) {
                        throw sqx;
                    }
                }
            }
        }
        return bnkUserName;
    }

    public HashMap<String, String> getFromDate() throws Exception {
        try {
            appPinPer = new ApplicationPinGenAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            fromDate = appPinPer.getUser(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } finally {
                if (CMSCon != null) {
                    try {
                        CMSCon.close();
                    } catch (SQLException sqx) {
                        throw sqx;
                    }
                }
            }
        }
        return fromDate;

    }

    public HashMap<String, String> getToDate() throws Exception {
        try {
            appPinPer = new ApplicationPinGenAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            toDate = appPinPer.getUser(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } finally {
                if (CMSCon != null) {
                    try {
                        CMSCon.close();
                    } catch (SQLException sqx) {
                        throw sqx;
                    }
                }
            }
        }
        return toDate;
    }

    public List<ApplicationPinGenAndMailBean> getPinGenAndMailDetails(ApplicationPinGenAndMailBean inputData) throws Exception {
        List<ApplicationPinGenAndMailBean> pinAndMailList = new ArrayList<ApplicationPinGenAndMailBean>();
        try {

            appPinPer = new ApplicationPinGenAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            pinAndMailList = appPinPer.getPinGenAndMailDetails(CMSCon, inputData);
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
                } catch (SQLException sqx) {
                    throw sqx;
                }
            }
        }
        return pinAndMailList;
    }
    

    public List<CardProductBean> getCardProductList() throws Exception {

        List<CardProductBean> bulkProductList = new ArrayList<CardProductBean>();
        try {
            appPinPer = new ApplicationPinGenAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkProductList = appPinPer.getCardProductList(CMSCon);
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
        return bulkProductList;
    }
}
