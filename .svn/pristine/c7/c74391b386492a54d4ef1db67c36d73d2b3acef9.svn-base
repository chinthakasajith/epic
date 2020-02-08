package com.epic.cms.backoffice.eodlettergeneration.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.backoffice.eodlettergeneration.bean.CardRenewalBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterDetailsBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterGenerationBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterTemplateBean;
import com.epic.cms.backoffice.eodlettergeneration.persistance.SearchLetterGenerationPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class SearchLetterGenerationManager {
    private Connection cmsCon;
    private SystemAuditManager sysAuditManager;
    private SearchLetterGenerationManager letterMgr;
    private SearchLetterGenerationPersistance letterPersistance;
    private LetterGenerationBean letterBean;
    private List<CardRenewalBean> searchList;
    private List<LetterDetailsBean> searchedletterList;
    
    
    public List<LetterGenerationBean> getProcessCategory() throws SQLException, Exception {
        try {
            
            List<LetterGenerationBean> processList = null;
            letterPersistance = new SearchLetterGenerationPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            
            processList = letterPersistance.getProcessCategory(cmsCon);
            cmsCon.commit();
            
            return processList;
            
        } catch (SQLException sqlEx) {
            try {
                cmsCon.rollback();
                throw sqlEx;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;                
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
    }
     //get all letter templates list
    public List<LetterTemplateBean> getAllLetterTemplates() throws Exception{
        try {
            List<LetterTemplateBean> tmpList=null;
            letterPersistance=new SearchLetterGenerationPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            tmpList=letterPersistance.getAllLetterTemplates(cmsCon);
            cmsCon.commit();
            return tmpList;
        } catch (SQLException sqlEx) {
            try {
                cmsCon.rollback();
                throw sqlEx;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;                
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
    }

    public List<CardRenewalBean> searchLetterGeneration(CardRenewalBean searchBean) throws SQLException, Exception {
        
        try {
            
            letterPersistance = new SearchLetterGenerationPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            
            searchList = letterPersistance.searchLetterGeneration(cmsCon, searchBean);
            cmsCon.commit();
        }catch (SQLException sqlEx) {
            try {
                cmsCon.rollback();
                throw sqlEx;
            } catch (SQLException e) {
                throw e;
            }
        }  catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;                
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
        return searchList;
    }
    //search letters to generate
    public List<LetterDetailsBean> searchLettersToGenerate(LetterDetailsBean searchBean) throws Exception{
        try {
            letterPersistance = new SearchLetterGenerationPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            
            searchedletterList=letterPersistance.searchLettersToGenerate(cmsCon, searchBean);
            cmsCon.commit();
        } catch (SQLException sqlEx) {
            try {
                cmsCon.rollback();
                throw sqlEx;
            } catch (SQLException e) {
                throw e;
            }
        }  catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;                
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
        return searchedletterList;
    }

}
