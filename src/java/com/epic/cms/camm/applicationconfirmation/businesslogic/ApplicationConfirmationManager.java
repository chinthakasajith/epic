/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.backoffice.cardlimitincrement.bean.CommonCardParameterBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.ApplicationCreditScoreBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardAccountBean;
import com.epic.cms.camm.applicationconfirmation.bean.ApplicationDetailsbean;
import com.epic.cms.camm.applicationconfirmation.bean.RecommendSchemBean;
import com.epic.cms.camm.applicationconfirmation.persistance.ApplicationConfirmationPersistance;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.SearchAssignAppBean;
import com.epic.cms.camm.applicationproccessing.assigndata.persistance.ApplicationAssignPersistance;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class ApplicationConfirmationManager {

    private ApplicationConfirmationPersistance confirmationPer;
    private Connection CMSCon;
    private Connection cmsConOnline;
    private List<ApplicationDetailsbean> applicationDetails;
    private SystemAuditManager sysAuditManager;
    private List<ApplicationDetailsbean> searchList;
    private List<CardBinBean> cardBinList;
    private List<CustomerTempBean> custemplateList;
    private List<AccountTempBean> accountTemList;
    private List<CardTemplateBean> cardTempList;

    public List<ApplicationDetailsbean> getApplicationDetails() throws Exception {
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            applicationDetails = confirmationPer.getApplicationDetails(CMSCon);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return applicationDetails;
    }

    public List<CardBinBean> getBinList(String productionModeCode, String cardType, String currency) throws Exception {
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardBinList = confirmationPer.getBinList(CMSCon, productionModeCode, cardType, currency);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return cardBinList;
    }

    public List<CustomerTempBean> getCustomertemplates(String staffStatus, String productType) throws Exception {
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            custemplateList = confirmationPer.getCustomertemplates(CMSCon, staffStatus, productType);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return custemplateList;
    }

    public List<AccountTempBean> getAccounttemplates(String staffStatus, String cardType, String custemplateCode, String currency) throws Exception {
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            accountTemList = confirmationPer.getAccounttemplates(CMSCon, staffStatus, cardType, custemplateCode, currency);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return accountTemList;
    }

    public String getCardType(String applicationId, String appTpye) throws Exception {
        String cardType = null;
        try {

            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardType = confirmationPer.getCardType(CMSCon, applicationId, appTpye);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return cardType;
    }

    public String getStaffStatus(String applicationId) throws Exception {
        String staffStatus = null;
        try {

            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            staffStatus = confirmationPer.getStaffStatus(CMSCon, applicationId);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return staffStatus;
    }

    public List<CardTemplateBean> getCardTemplates(String staffStatus, String productCode, String custemplateCode, String accountTempId, String currency, String cardCategory) throws Exception {
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTempList = confirmationPer.getCardTemplates(CMSCon, staffStatus, productCode, custemplateCode, accountTempId, currency, cardCategory);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return cardTempList;
    }

//    public List<ApplicationDetailsbean> getApplicationDetailsForCreditOficer() throws Exception {
//        try {
//            confirmationPer = new ApplicationConfirmationPersistance();
//            CMSCon = DBConnection.getConnection();
//            CMSCon.setAutoCommit(false);
//            applicationDetails = confirmationPer.getApplicationDetailsForCreditOfficer(CMSCon);
//
//
//            CMSCon.commit();
//
//        } catch (SQLException ex) {
//            try {
//                CMSCon.rollback();
//                throw ex;
//            } catch (Exception e) {
//                CMSCon.rollback();
//                throw ex;
//            }
//        } finally {
//            DBConnection.dbConnectionClose(CMSCon);
//        }
//        return applicationDetails;
//    }
    public int getNumberOfRecords(int creditScore, String cardType) throws Exception {
        int creditLimit_value = 0;
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            String creditlimit = confirmationPer.getCreditlimi(CMSCon, creditScore, cardType);

            if (creditlimit == null) {
                creditLimit_value = 0;
            } else {
                creditLimit_value = Integer.parseInt(creditlimit);
            }

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return creditLimit_value;
    }

    public List<RecommendSchemBean> getCardProducts(String cardType, String creditLimit) throws Exception {
        List<RecommendSchemBean> cardProducts = new ArrayList<RecommendSchemBean>();
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardProducts = confirmationPer.getCardProducts(CMSCon, cardType, creditLimit);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return cardProducts;
    }

    //get card product list for requested card type by chinthaka
    public List<CardProductBean> getCardProductsToRequestedCardType(String catdType) throws Exception {
        List<CardProductBean> cardProductList = new ArrayList<CardProductBean>();
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardProductList = confirmationPer.getCardProductsToRequestedCardType(CMSCon, catdType);
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return cardProductList;
    }

    public int getUpperlimit(int index) throws Exception {
        int upperLimit = 0;
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            upperLimit = confirmationPer.getUpperLimit(CMSCon, index);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return upperLimit;
    }

    public RecommendSchemBean getDetails(int index) throws Exception {
        RecommendSchemBean bean = new RecommendSchemBean();
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bean = confirmationPer.getDetails(CMSCon, index);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return bean;
    }

    public String getCreditLimit(int index) throws Exception {
        String credtLimit = null;
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            credtLimit = confirmationPer.getCreditLimit(CMSCon, index);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return credtLimit;
    }

    public Boolean insertRejectReasonAndRemarks(String rejectReason, String remark, String applicationId) throws Exception {
        Boolean status = false;
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            status = confirmationPer.updateRejectReasonAndRemark(CMSCon, rejectReason, remark, applicationId);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public Boolean updateCardApplicationDocument(String applicationId, SystemAuditBean systemAuditBean, ApplicationHistoryBean historybean, CardApplicationBean cardAppBean, String Appstatus) throws Exception {
        Boolean status = false;
        try {
            ApplicationAssignPersistance history = new ApplicationAssignPersistance();
            sysAuditManager = new SystemAuditManager();
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            history.insertApplicationHistory(CMSCon, historybean);
            status = confirmationPer.updateCardApplicationDocument(CMSCon, applicationId, systemAuditBean.getUserName());
            confirmationPer.UpdateCardApplicationStatus(CMSCon, applicationId, Appstatus);
            confirmationPer.updateCardApplication(CMSCon, applicationId, cardAppBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            status = true;
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public Boolean isLetterAvailable(String applicationId) throws Exception {
        Boolean status = false;
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            status = confirmationPer.isLetterAvailable(CMSCon, applicationId);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return status;
    }

    public List<ApplicationCreditScoreBean> getBreakDownValues(String applicationId) throws Exception {
        List<ApplicationCreditScoreBean> breakDownList = new ArrayList<ApplicationCreditScoreBean>();
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            breakDownList = confirmationPer.getBreakDownValues(CMSCon, applicationId);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return breakDownList;
    }
//get credit officer review application list

    public List<ApplicationDetailsbean> getAllSearchList(SearchAssignAppBean searchBean) throws Exception {
        try {//*/
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = confirmationPer.generalSearch(CMSCon, searchBean);
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

    //get application confirmation list
    public List<ApplicationDetailsbean> getConfirmationApplicationList(SearchAssignAppBean searchBean) throws Exception {
        try {//*/
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = confirmationPer.confirmationApplicationsSearch(CMSCon, searchBean);
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

    public List<ApplicationDetailsbean> debitCardGeneralSearch(SearchAssignAppBean searchBean) throws Exception {
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = confirmationPer.debitCardGeneralSearch(CMSCon, searchBean);
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

    public String getApplicationCategory(String applicationId) throws Exception {
        String applicationCategory = null;
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            applicationCategory = confirmationPer.getApplicationCategory(CMSCon, applicationId);
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
        return applicationCategory;
    }

    public String getLetterDirectory(String osType) throws Exception {
        String applicationCategory = null;
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            applicationCategory = confirmationPer.getLetterDirectory(CMSCon, osType);
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
        return applicationCategory;
    }

    public String getSignatureDirectory(String osType) throws Exception {
        String applicationCategory = null;
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            applicationCategory = confirmationPer.getSignatureDirectory(CMSCon, osType);
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
        return applicationCategory;
    }

    public List<CardBinBean> getBinList(String cardProfileCode) throws Exception {
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardBinList = confirmationPer.getBinList(CMSCon, cardProfileCode);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return cardBinList;
    }

    public List<CardBinBean> getCardKeyList(String cardProfileCode, String binProfileCode) throws Exception {
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardBinList = confirmationPer.getCardKeyList(CMSCon, cardProfileCode, binProfileCode);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return cardBinList;
    }

    public List<CardBinBean> getProductionModeList(String CardKeyId) throws Exception {
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            cmsConOnline = DBConnectionToOnlineDB.getConnection();
            cmsConOnline.setAutoCommit(false);
            cardBinList = confirmationPer.getProductionModeList(cmsConOnline, CardKeyId);

            cmsConOnline.commit();

        } catch (SQLException ex) {
            try {
                cmsConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                cmsConOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(cmsConOnline);
        }
        return cardBinList;
    }

    public boolean validateExceedEstablishmentCreditLimit(String businessRegNo, Double establishmentCreditLimit, Double requestCreditLimit) throws Exception {
        boolean validate = false;
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            Double corporateCardGenTotalCredit = confirmationPer.getEstablishmentUsedCreditLimit(CMSCon, businessRegNo);
            Double corporateConformedTotalCredit = confirmationPer.getCorporateConformedTotalCredit(CMSCon, businessRegNo);

            if ((requestCreditLimit + corporateConformedTotalCredit + corporateCardGenTotalCredit) <= establishmentCreditLimit) {
                validate = true;
            }

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return validate;

    }

    public boolean validateExceedMainCreditLimit(String mainApplicationId, Double mainCreditLimit, Double requestCreditLimit, String mainCardNumber, String appId) throws Exception {
        boolean validate = false;
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            //CommonCardParameterBean suppLimitParam = confirmationPer.getSupplementaryCreditLimitConfigs(CMSCon);
            SupplementaryApplicationBean supAppCreditLimitParam = confirmationPer.getSupplementaryCreditLimitParam(CMSCon, appId);
            if (mainCardNumber != null) {
                CardAccountBean maincardAccount = confirmationPer.getCardAccountByCardNumber(CMSCon, mainCardNumber);
                mainCreditLimit = Double.parseDouble(maincardAccount.getCreditLimit());
            } else {
                mainCreditLimit = confirmationPer.getCreditLimitOfprimaryCard(CMSCon, mainApplicationId);
            }
            Double allowedSupCreditLimit = 0d;
            if (supAppCreditLimitParam.getCreditLimit() == null) {
                allowedSupCreditLimit = mainCreditLimit * Double.parseDouble(supAppCreditLimitParam.getPercentageValue()) / 100;
            } else {
                allowedSupCreditLimit = Double.parseDouble(supAppCreditLimitParam.getCreditLimit());
            }

            Double supCardGenTotalCredit = confirmationPer.getSupUsedCreditLimitOfMainCard(CMSCon, mainCardNumber);
            Double supConfirmedNotCardGenTotalCredit = confirmationPer.getSupplymentoryConformedTotalCredit(CMSCon, mainApplicationId);

            if ((requestCreditLimit + supConfirmedNotCardGenTotalCredit + supCardGenTotalCredit) <= allowedSupCreditLimit) {
                validate = true;
            }

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return validate;
    }

    //get system reccomended credit limit of supplimentory application 
    public Double getSystemRecCreditLimit(String per, String primAppId, String supAppId, String primCardNum) throws Exception {
        Double perValue = Double.valueOf(per);
        Double sumOfUsedCreditLimitOfPrimaryCard = 0d;
        Double creditLimitOfPrimaryCard = 0d;
        Double sysRecCreditlimit = 0d;
        try {
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            //sumOfUsedCreditLimitOfPrimaryCard = confirmationPer.getSumOfUsedCreditLimitsOfPrimaryCard(CMSCon, primAppId, supAppId);
            Double supCardGenTotalCredit = confirmationPer.getSupUsedCreditLimitOfMainCard(CMSCon, primCardNum);
            Double supConfirmedNotCardGenTotalCredit = confirmationPer.getSupplymentoryConformedTotalCredit(CMSCon, primAppId);
            if (primCardNum != null) {
                CardAccountBean maincardAccount = confirmationPer.getCardAccountByCardNumber(CMSCon, primCardNum);
                creditLimitOfPrimaryCard = Double.parseDouble(maincardAccount.getCreditLimit());
            } else {
                creditLimitOfPrimaryCard = confirmationPer.getCreditLimitOfprimaryCard(CMSCon, primAppId);
            }
            if ((creditLimitOfPrimaryCard > (supCardGenTotalCredit + supConfirmedNotCardGenTotalCredit))) {
                sysRecCreditlimit = ((creditLimitOfPrimaryCard - (supCardGenTotalCredit + supConfirmedNotCardGenTotalCredit)) * perValue) / 100;
            }

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return sysRecCreditlimit;
    }

    public List<ApplicationDetailsbean> getReviewAndConfirmSearchList(SearchAssignAppBean searchBean) throws Exception {
        try {//*/
            confirmationPer = new ApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = confirmationPer.getReviewAndConfirmSearchList(CMSCon, searchBean);
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

}
