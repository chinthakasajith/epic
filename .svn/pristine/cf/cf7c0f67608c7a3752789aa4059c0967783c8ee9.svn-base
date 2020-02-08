/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.cardembossing.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.EmbossFileFormatDetailBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.cpmm.cardembossing.bean.BulkCardEmbossingDetailsBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingCardDetailsBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingFileDownloadBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingSearchBean;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.cpmm.cardembossing.persistance.CardEmbossingMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class CardEmbossingMgtManager {

    private Connection CMSCon;
    private CardEmbossingMgtPersistance CardEmbossingPersistance;
    private SystemAuditManager sysAuditManager;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> cardProductList = null;
    private String embossFileFormat = null;
    private HashMap<String, String> identityTypeList = null;
    private List<CardEmbossingCardDetailsBean> cardEmbossingVISAList = null;
    private List<BulkCardEmbossingDetailsBean> bulkCardEmbossingList = null;
    private List<CardEmbossingCardDetailsBean> cardEmbossingMASTERList = null;
    private List<CardEmbossingCardDetailsBean> bulkCardDetailsBeanList = null;
    private List<EmbossFileFormatDetailBean> fileFormatDetailBean = null;
    private EmbossFileFormatDetailBean embossDetailBean = null;
    private String dynamicFieldValue = null;
    private List<CardEmbossingFileDownloadBean> cardEmbossingFileList = null;
    private CardEmbossingCardDetailsBean cardEmbossingVISABean = null;
    private CommonFilePathBean commonFilePathBean = null;
    private DocumentUploadBean uploadBean = null;

    public HashMap<String, String> getAllCardType() throws Exception {
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTypeList = CardEmbossingPersistance.getAllCardType(CMSCon);
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

    public HashMap<String, String> getAllIdentityType() throws Exception {
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            identityTypeList = CardEmbossingPersistance.getAllIdentityType(CMSCon);
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
        return identityTypeList;
    }

    public HashMap<String, String> getAllCardProduct(String cardType) throws Exception {
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardProductList = CardEmbossingPersistance.getAllCardProduct(CMSCon, cardType);
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

    public List<CardEmbossingCardDetailsBean> getAllVISACardToEmboss(CardEmbossingSearchBean searchBean, String cardDomain) throws Exception {
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardEmbossingVISAList = CardEmbossingPersistance.getAllVISACardToEmboss(CMSCon, searchBean, cardDomain);
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
        return cardEmbossingVISAList;
    }

    public List<BulkCardEmbossingDetailsBean> getAllBulkCardrequestToEmboss(CardEmbossingSearchBean searchBean) throws Exception {
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkCardEmbossingList = CardEmbossingPersistance.getAllBulkCardrequestToEmboss(CMSCon, searchBean);
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
        return bulkCardEmbossingList;
    }

//    public List<CardEmbossingCardDetailsBean> getAllMASTERCardToEmboss(CardEmbossingSearchBean searchBean, String cardDomain) throws Exception {
//        try {
//            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
//            CMSCon = DBConnection.getConnection();
//            CMSCon.setAutoCommit(false);
//            cardEmbossingMASTERList = CardEmbossingPersistance.getAllMASTERCardToEmboss(CMSCon, searchBean, cardDomain);
//            CMSCon.commit();
//        } catch (Exception ex) {
//            try {
//                CMSCon.rollback();
//                throw ex;
//            } catch (SQLException sqx) {
//                throw sqx;
//            }
//        } finally {
//            if (CMSCon != null) {
//                try {
//                    CMSCon.close();
//                } catch (SQLException e) {
//                    throw e;
//                }
//            }
//        }
//        return cardEmbossingMASTERList;
//    }
    public List<CardEmbossingCardDetailsBean> getAllCardProductToEmbossByCardType(String cardNumbers, String cardProduct, String cardType) throws Exception {

        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardEmbossingVISAList = CardEmbossingPersistance.getAllVISACardProductToEmboss(CMSCon, cardNumbers, cardProduct, cardType);
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
        return cardEmbossingVISAList;
    }

    public CardEmbossingCardDetailsBean getVISACardProductToEmbossBean(String cardNumber) throws Exception {

        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardEmbossingVISABean = CardEmbossingPersistance.getVISACardProductToEmbossBean(CMSCon, cardNumber);
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
        return cardEmbossingVISABean;
    }

    public String getVisaEmbossingFormat(String cardType) throws Exception {
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            embossFileFormat = CardEmbossingPersistance.getVisaEmbossingFormat(CMSCon, cardType);
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
        return embossFileFormat;
    }

    public List<EmbossFileFormatDetailBean> getEmbossFields() throws Exception {
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            fileFormatDetailBean = CardEmbossingPersistance.getEmbossFields(CMSCon);
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
        return fileFormatDetailBean;
    }

    public List<CardEmbossingCardDetailsBean> getAllBulkCardDetails(String batchId) throws Exception {
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkCardDetailsBeanList = CardEmbossingPersistance.getAllBulkCardDetails(CMSCon, batchId);
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
        return bulkCardDetailsBeanList;
    }

    public EmbossFileFormatDetailBean getEmbossFieldsBean(String fieldName) throws Exception {
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            embossDetailBean = CardEmbossingPersistance.getEmbossFieldsBean(CMSCon, fieldName);
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
        return embossDetailBean;
    }

    public String getTableFieldValue(String tableName, String fieldName, String cardNumber) throws Exception {
        String fieldValue = null;
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            fieldValue = CardEmbossingPersistance.getTableFieldValue(CMSCon, tableName, fieldName, cardNumber);
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
        return fieldValue;
    }

    public String getDynamicFieldValue(String column) throws Exception {
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            dynamicFieldValue = CardEmbossingPersistance.getDynamicFieldValue(CMSCon, column);
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
        return dynamicFieldValue;
    }

    public boolean updateCardEmbossStatus(String cardNumbers, String fileName, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = CardEmbossingPersistance.updateCardEmbossStatus(CMSCon, fileName, cardNumbers);
            flag = CardEmbossingPersistance.updateBulkCardRequestStatus(CMSCon, systemAuditBean.getUniqueId());//update batch status
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

    public boolean insertCardEmbossFileDetails(String fileName, String generateUser, String cardType, String cardProduct, Calendar currentDate) throws Exception {
        boolean flag = false;
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = CardEmbossingPersistance.insertCardEmbossFileDetails(CMSCon, fileName, generateUser, cardType, cardProduct, currentDate);
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

    public List<CardEmbossingFileDownloadBean> getAllFilesToDownload(String fileType, Calendar currentTime) throws Exception {
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardEmbossingFileList = CardEmbossingPersistance.getAllFilesToDownload(CMSCon, fileType, currentTime);
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
        return cardEmbossingFileList;
    }

    public boolean updateCardEmbossFileDowloadStatus(String fileName, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = CardEmbossingPersistance.updateCardEmbossFileDowloadStatus(CMSCon, fileName);
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

    public String getCardEmbossFileDowloadStatus(String fileName) throws Exception {
        String downloadStatus = "";
        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            downloadStatus = CardEmbossingPersistance.getCardEmbossFileDowloadStatus(CMSCon, fileName);
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
        return downloadStatus;
    }

    public CommonFilePathBean getFilePaths(String osType) throws Exception {

        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            commonFilePathBean = CardEmbossingPersistance.getFilePaths(CMSCon, osType);
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
        return commonFilePathBean;
    }

    public DocumentUploadBean getFileName(String applicationId, String categoryCode) throws Exception {

        try {
            CardEmbossingPersistance = new CardEmbossingMgtPersistance();
            uploadBean = new DocumentUploadBean();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            uploadBean = CardEmbossingPersistance.getFileName(CMSCon, applicationId, categoryCode);

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
        return uploadBean;

    }
}
