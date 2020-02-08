/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.collection.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.CaseTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.LadderBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.persistance.LadderPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ruwan_e
 */
public class LadderManager {

    private LadderPersistance ladderPersistance = null;
    private Connection CMSCon;
    private ArrayList<CaseTypeBean> caseBeans;
    private Map<String, String> status;

    public ArrayList<CaseTypeBean> getCasesByLadderId(long id) throws SQLException, Exception {
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            caseBeans = ladderPersistance.getCasesByLadderId(CMSCon, id);
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
        return caseBeans;
    }

    public ArrayList<CaseTypeBean> getAllCases() throws SQLException, Exception {
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            caseBeans = ladderPersistance.getAllCases(CMSCon);
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
        return caseBeans;
    }

    public Map<String, String> getStatus() throws SQLException, Exception {
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            status = ladderPersistance.getStatus(CMSCon);
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
        return status;
    }

    public Map<String, String> getCardTypes() throws SQLException, Exception {
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            status = ladderPersistance.getCardTypes(CMSCon);
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
        return status;
    }

    public Map<String, String> getAllCardProducts() throws SQLException, Exception {
        Map<String, String> products = null;
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            products = ladderPersistance.getAllCardProducts(CMSCon);
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
        return products;
    }

    public Map<String, String> getCardProductsByCardType(String cardType) throws SQLException, Exception {
        Map<String, String> products = null;
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            products = ladderPersistance.getCardProductsByCardType(cardType, CMSCon);
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
        return products;
    }

    public boolean create(LadderBean ladder, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;

        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = ladderPersistance.createLadder(ladder, CMSCon);
            
            if(!success){
                CMSCon.rollback();
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
        return success;
    }
    
        public boolean updateLadder(LadderBean ladder, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;

        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = ladderPersistance.updateLadder(ladder, CMSCon);
            
            if(!success){
                CMSCon.rollback();
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
        return success;
    }

    public ArrayList<LadderBean> getAllLaddersDesc() throws SQLException, Exception {
        ArrayList<LadderBean> ladders = null;
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            ladders = ladderPersistance.getAllLaddersDesc(CMSCon);
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
        return ladders;
    }

    public ArrayList<LadderBean> getAllLadders() throws SQLException, Exception {
        ArrayList<LadderBean> ladders = null;
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            ladders = ladderPersistance.getAllLadders2(CMSCon);
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
        return ladders;
    }

    public boolean isUniqueCode(String code) throws SQLException, Exception {
        boolean isUnique = false;
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            isUnique = ladderPersistance.isUniqueCode(code, CMSCon);
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
        return isUnique;
    }

    public Map<String, String> caseArrayToMap(String[] cases) throws Exception {
        Map<String, String> caseMap = null;
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            caseMap = ladderPersistance.caseArrayToMap(cases, CMSCon);
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
        return caseMap;
    }

    public List<CaseTypeBean> getNotAssignedCaseTypeList(String code) throws SQLException, Exception {
        List<CaseTypeBean> caseList = null;
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            caseList = ladderPersistance.getNotAssignedCaseTypeList(code, CMSCon);
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
        return caseList;
    }

    public List<CaseTypeBean> getAssignedCaseTypeList(String code) throws SQLException, Exception {
        List<CaseTypeBean> caseList = null;
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            caseList = ladderPersistance.getAssignedCaseTypeList(code, CMSCon);
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
        return caseList;
    }

    public List<CaseTypeBean> getCasesByLadderCode(String code) throws SQLException, Exception {
        List<CaseTypeBean> cases = null;
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cases = ladderPersistance.getCasesByLadderCode(code, CMSCon);
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
        return cases;
    }

    public Map<String, String> getCaseDescription(String[] cases) throws SQLException, Exception {
        Map<String, String> caseDesc = null;
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            caseDesc = ladderPersistance.getCaseDescription(cases, CMSCon);
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
        return caseDesc;
    }

    public boolean deleteLadder(String code, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean result = false;
        try {
            ladderPersistance = new LadderPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            result = ladderPersistance.deleteLadder(code, CMSCon);
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
        return result;
    }
}
