/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardCustomerBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.assigndata.persistance.ApplicationAssignPersistance;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AreaBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AssetBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AssetLiabilityTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BankBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BankBranchBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardExpensesBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardIncomeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerEmploymentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DebitPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EmploymentNatureBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EmploymentTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentAssetsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentLiabilityBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.IdBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.IncomeTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.LiabilityBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.OccupationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SearchUserAssignAppBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.VerificationCategoryBean;
import com.epic.cms.camm.applicationproccessing.capturedata.persistance.CustomerPersonalPersistance;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.AssetsBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author janaka_h
 */
public class CaptureDataManager {

    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private ApplicationCheckingManager checkingManager;
    private CustomerPersonalPersistance persistance;
    private ApplicationAssignPersistance historyPersistance;
    private List<ApplicationAssignBean> searchList = null;
    private List<EmploymentTypeBean> empTypeList = null;
    private List<OccupationBean> occupationList = null;
    private List<EmploymentNatureBean> natureList = null;
    private List<IncomeTypeBean> incomeTypeList = null;
    private CardApplicationStatusBean appStatusBean = null;
    private CustomerPersonalBean customerPersonalBean = null;
    private SupplementaryApplicationBean customerSuplimentoryPersonalBean = null;
    private List<BankBean> bankList = null;
    private List<VerificationCategoryBean> verificationCateLst = null;
    private List<DocumentTypeBean> documentTypeLst = null;
    private List<BankBranchBean> branchList = null;
    private List<AreaBean> areaList = null;
    private List<String> nationalityList = null;
    private CardCustomerBean customerBean;
    private List<CardProductBean> cardProductList = null;
    private List<CurrencyBean> currencyList = null;
    private DebitPersonalBean debitPersonalBean = null;
    private HashMap<String, String> accountTypeList = null;
    private List<AssetLiabilityTypeBean> assetsLiabilityTypeList = null;
    private List<AssetBean> assetsList = null;
    private List<LiabilityBean> liabilityList = null;

    public int insertPersonalData(CustomerPersonalBean personalBean, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd;
        try {
            CustomerPersonalBean bean = this.getAllDetailsCustomer(personalBean.getApplicationId());

            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            checkingManager = new ApplicationCheckingManager();

            if (bean != null) {
                isAdd = persistance.updatePersonalRecord(CMSCon, personalBean);
            } else {
                isAdd = persistance.insertPersonalRecord(CMSCon, personalBean);

                ApplicationHistoryBean historyBean = new ApplicationHistoryBean();

                historyBean.setApplicationId(personalBean.getApplicationId());
                historyBean.setApplicationLevel("DTEN");
                historyBean.setLastUpdatedUser(personalBean.getLastUpdateUser());

                if (isAdd == 1) {
                    historyBean.setRemarks(personalBean.getApplicationId() + ": Application processing started");
                    historyBean.setStatus("HCOM");
                } else {
                    historyBean.setRemarks(personalBean.getApplicationId() + ": Application processing failed");
                    historyBean.setStatus("HFAL");
                }
                historyPersistance = new ApplicationAssignPersistance();
                historyPersistance.insertApplicationHistory(CMSCon, historyBean);
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            persistance.updateStatusOfCardApplicationStatus(personalBean.getApplicationId(), "TAB01STATUS", CMSCon);
            persistance.updateCardApplicationStatus(personalBean.getApplicationId(), StatusVarList.APP_PROCESS, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    public int insertSuplimentorryPersonalData(SupplementaryApplicationBean personalBean, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd;
        try {
            SupplementaryApplicationBean bean = this.getAllDetailsSuplimentoryCustomer(personalBean.getApplicationId());

            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            checkingManager = new ApplicationCheckingManager();

            if (bean != null) {
                isAdd = persistance.updateSuplimentoryPersonalRecord(CMSCon, personalBean);
            } else {
                isAdd = persistance.insertSuplimentoryPersonalRecord(CMSCon, personalBean);
            }

            //sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            persistance.updateStatusOfCardApplicationStatus(personalBean.getApplicationId(), "TAB01STATUS", CMSCon);
            persistance.updateCardApplicationStatus(personalBean.getApplicationId(), StatusVarList.APP_PROCESS, CMSCon);
            persistance.updateCardApplicationDocumentForSupplementaryUser(personalBean.getApplicationId(), personalBean.getPrimaryCardApplicationId(), CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    //update supplementary personal details 
    public int updateSupplementaryPersonalDetails(SupplementaryApplicationBean personalBean, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd;
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();

            isAdd = persistance.updateSuplimentoryPersonalRecord(CMSCon, personalBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    public int insertEmploymentData(CustomerEmploymentBean employmentBean, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd;
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            checkingManager = new ApplicationCheckingManager();

            CustomerEmploymentBean bean = checkingManager.getCardEmployementDetails(employmentBean.getApplicationId());
            if (bean != null) {
                isAdd = persistance.UpdateEmploymentRecord(CMSCon, employmentBean);
            } else {
                isAdd = persistance.insertEmploymentRecord(CMSCon, employmentBean);
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            if (isAdd == 1) {
                persistance.updateStatusOfCardApplicationStatus(employmentBean.getApplicationId(), "TAB02STATUS", CMSCon);
            }

            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    public int insertIncomeAndExpensesData(CardExpensesBean expenseBean, List<CardIncomeBean> list, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd = 0;
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            checkingManager = new ApplicationCheckingManager();

            CardExpensesBean bean = checkingManager.getExpensesDetails(expenseBean.getApplicationId());
            if (bean != null) {
                isAdd = persistance.updateIncomeAndExpensesRecord(CMSCon, expenseBean, list);
            } else {
                isAdd = persistance.insertIncomeAndExpensesRecord(CMSCon, expenseBean, list);
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            if (isAdd == 1) {
                persistance.updateStatusOfCardApplicationStatus(expenseBean.getApplicationId(), "TAB03STATUS", CMSCon);
            }

            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    public int insertAccountDetailList(List<CardBankDetailsBean> sessionBankDetailList, String appId, SystemAuditBean systemAuditBean, CardBankDetailsBean autoSettleDetailsBean) throws Exception {
        int isAdd = 0;
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            checkingManager = new ApplicationCheckingManager();

            List<CardBankDetailsBean> lst = checkingManager.getCardBankDetailsDetails(appId);
            if (lst.size() > 0) {
                isAdd = persistance.updateBankDetailsRecords(CMSCon, sessionBankDetailList, appId);
            } else {
                isAdd = persistance.insertBankDetailsRecords(CMSCon, sessionBankDetailList);
            }
            if (autoSettleDetailsBean.getIsAutoSettle().equals("on")) {
                isAdd += persistance.updateAutomaticSettlementBankDetails(CMSCon, autoSettleDetailsBean);
            } else {
                isAdd += 1;
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            if (isAdd == 2) {
                persistance.updateStatusOfCardApplicationStatus(appId, "TAB04STATUS", CMSCon);
            }
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    public int insertDocumentUploadList(List<DocumentUploadBean> sessionDocumentList, String appId, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd = 0;
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            checkingManager = new ApplicationCheckingManager();

            List<DocumentUploadBean> lst = checkingManager.getCardDocumentDetails(appId);
            if (lst != null) {
                isAdd = persistance.updateDocumentUploadRecords(CMSCon, sessionDocumentList, appId);
            } else {
                isAdd = persistance.insertDocumentUploadRecords(CMSCon, sessionDocumentList);
            }
            if (isAdd == 1) {
                sysAuditManager.insertAudit(systemAuditBean, CMSCon);
                persistance.updateStatusOfCardApplicationStatus(appId, "TAB05STATUS", CMSCon);
            }
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    public int insertSignatureAndComplete(String applicationId, String fileName, SystemAuditBean systemAuditBean, CustomerPersonalBean recBean,String cardCategoryCode) throws Exception {

        int isAdd;
        try {

            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            checkingManager = new ApplicationCheckingManager();

            isAdd = persistance.insertSignatureRecord(CMSCon, applicationId, fileName);
            if (recBean != null) {
                persistance.insertRecommendedDetails(CMSCon, applicationId, recBean);
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            if (isAdd == 1) {
                persistance.updateStatusOfCardApplicationStatus(applicationId, "TAB06STATUS", CMSCon);
                if(cardCategoryCode.equals(StatusVarList.CARD_AGAINST_FD_CODE)){
                    persistance.updateCardApplicationStatus(applicationId, StatusVarList.APP_FIXED_VERIFY_COMPELTE, CMSCon);
                }else{
                    persistance.updateCardApplicationStatus(applicationId, StatusVarList.APP_FILLING_COMPLETE, CMSCon);
                }
            }
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    public int updatePersonalData(CustomerPersonalBean personalBean, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd;
        try {
            CustomerPersonalBean bean = this.getAllDetailsCustomer(personalBean.getApplicationId());

            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            checkingManager = new ApplicationCheckingManager();

            if (bean != null) {
                isAdd = persistance.updatePersonalRecord(CMSCon, personalBean);
            } else {
                isAdd = persistance.insertPersonalRecord(CMSCon, personalBean);
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    public int updateSignature(String applicationId, String fileName, SystemAuditBean systemAuditBean, CustomerPersonalBean recDetailsBean) throws Exception {

        int isAdd = 1;//should change into 0
        try {

            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            checkingManager = new ApplicationCheckingManager();

            isAdd = persistance.insertSignatureRecord(CMSCon, applicationId, fileName);
            if (recDetailsBean != null) {
                persistance.insertRecommendedDetails(CMSCon, applicationId, recDetailsBean);
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    public List<ApplicationAssignBean> userAssignApplicationsSearch(SearchUserAssignAppBean searchBean, SystemAuditBean systemAuditBean) throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = persistance.userAssignApplicationsSearch(CMSCon, searchBean);
            // sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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

    public List<ApplicationAssignBean> userModifyApplicationsSearch(SearchUserAssignAppBean searchBean, SystemAuditBean systemAuditBean) throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = persistance.userModifyApplicationsSearch(CMSCon, searchBean);
            //sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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

    public List<EmploymentTypeBean> getEmpTypelist() throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            empTypeList = persistance.getAllEmplymentLst(CMSCon);

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

        return empTypeList;
    }

    public List<OccupationBean> getOccupationlist() throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            occupationList = persistance.getAllOccupationLst(CMSCon);

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

        return occupationList;
    }

    public List<EmploymentNatureBean> getNaturelist() throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            natureList = persistance.getAllNatureLst(CMSCon);

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

        return natureList;
    }

    public List<IncomeTypeBean> getIncomeTypeLst() throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            incomeTypeList = persistance.getAllIncomeTypes(CMSCon);

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

        return incomeTypeList;
    }

    public CustomerPersonalBean getAllDetailsCustomer(String applicationId) throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            customerPersonalBean = persistance.getAllDetailsCustomer(CMSCon, applicationId);
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

        return customerPersonalBean;
    }

    public SupplementaryApplicationBean getAllDetailsSuplimentoryCustomer(String appliactionId) throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            customerSuplimentoryPersonalBean = persistance.getAllDetailsSuplimentoryCustomer(CMSCon, appliactionId);
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

        return customerSuplimentoryPersonalBean;
    }

    public List<BankBean> getAllBankLst() throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bankList = persistance.getAllBanks(CMSCon);

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

        return bankList;
    }

    public List<BankBean> getBankByBankCode(String bankCode) throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bankList = persistance.getBankByBankCode(CMSCon, bankCode);

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

        return bankList;
    }

    public List<BankBranchBean> getAllBranchList() throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            branchList = persistance.getAllBankBranches(CMSCon);

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

        return branchList;
    }

    public List<BankBranchBean> getBankBranchList(String bankCode) throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            branchList = persistance.getBankBranches(CMSCon, bankCode);

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

        return branchList;
    }

    public CardApplicationStatusBean getAllApplicationStatus(String appliactionId) throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            appStatusBean = persistance.getAllApplicationStatus(appliactionId, CMSCon);

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

        return appStatusBean;
    }

    public List<AreaBean> getAllArealist() throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            areaList = persistance.getAllAreaList(CMSCon);

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

        return areaList;
    }

    public List<String> getAllNationality() throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            nationalityList = persistance.getAllNationality(CMSCon);

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

        return nationalityList;
    }

    public String getDistrictAndProvince(String area) throws Exception {
        String result = "";
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            result = persistance.getDistrictAndProvince(CMSCon, area);

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

        return result;
    }

    public String getCardProductDropDownLst(String cardType) throws Exception {
        String result = "";
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            result = persistance.getCardProductDropDownLst(CMSCon, cardType);

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

        return result;
    }

    public List<VerificationCategoryBean> getAllVerificationCategory() throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            verificationCateLst = persistance.getAllVerificationCategory(CMSCon);

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

        return verificationCateLst;
    }

    public List<DocumentTypeBean> getDocumentTypeByCategory(String category) throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            documentTypeLst = persistance.getDocumentTypeByCategory(CMSCon, category);

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

        return documentTypeLst;
    }

    public String getSignatureName(String applicationId) throws Exception {
        String result = "";
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            result = persistance.getSignatureName(CMSCon, applicationId);

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

        return result;
    }

    public IdBean getIdentifyDetails(String appliactionId) throws Exception {
        IdBean bean = new IdBean();
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bean = persistance.getIdentifyDetails(CMSCon, appliactionId);

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

        return bean;
    }

    public CardCustomerBean getPreviousCustomerDetails(ApplicationAssignBean assignAppBean) throws Exception {
        customerBean = new CardCustomerBean();
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            customerBean = persistance.getPreviousCustomerDetails(CMSCon, assignAppBean.getIdentityNo());

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

        return customerBean;
    }

    public List<ApplicationAssignBean> debitCardApplicationsSearch(SearchUserAssignAppBean searchBean) throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = persistance.debitCardApplicationsSearch(CMSCon, searchBean);
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

    public List<CardProductBean> getAllCardProduct() throws Exception {
        try {

            persistance = new CustomerPersonalPersistance();
            cardProductList = new ArrayList<CardProductBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardProductList = persistance.getAllCardProduct(CMSCon);
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

    public List<CardProductBean> getAllCardProductByCardType(String cardType) throws Exception {
        try {

            persistance = new CustomerPersonalPersistance();
            cardProductList = new ArrayList<CardProductBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardProductList = persistance.getAllCardProductByCardType(CMSCon, cardType);
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

    public boolean insertPrimaryDebitCardApplication(DebitPersonalBean personalBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean successInsert = false;
        boolean isExist = false;
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSCon.setAutoCommit(false);

            isExist = persistance.checkApplicationExist(CMSCon, personalBean.getApplicationId());

            if (isExist) {
                successInsert = persistance.updatePrimaryDebitCardApplication(CMSCon, personalBean);
            } else {
                successInsert = persistance.insertPrimaryDebitCardApplication(CMSCon, personalBean);
            }
            if (successInsert && personalBean.getJoin().equals("P")) {
                persistance.updateCardApplicationStatus(personalBean.getApplicationId(), StatusVarList.APP_FILLING_COMPLETE, CMSCon);
                persistance.updatePrimeryCardApplicationDocument(CMSCon, personalBean);
                if (personalBean.getNicFileName() != null) {
                    persistance.updateUploadedNic(CMSCon, personalBean, systemAuditBean.getUserName());
                }
            }

            if (successInsert && personalBean.getJoin().equals("J")) {
                persistance.updateCardApplicationStatus(personalBean.getApplicationId(), StatusVarList.APP_PROCESS, CMSCon);
                persistance.updatePrimeryCardApplicationDocument(CMSCon, personalBean);
                if (personalBean.getNicFileName() != null) {
                    persistance.updateUploadedNic(CMSCon, personalBean, systemAuditBean.getUserName());
                }
            }

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
        return successInsert;
    }

    public boolean insertDebitCardSecondaryApplication(DebitPersonalBean personalBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean successInsert = false;
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSCon.setAutoCommit(false);

            successInsert = persistance.insertDebitCardSecondaryApplication(CMSCon, personalBean);

            if (successInsert) {

                persistance.updateCardApplicationStatus(personalBean.getApplicationId(), StatusVarList.APP_FILLING_COMPLETE, CMSCon);
                if (personalBean.getNicFileName() != null) {
                    persistance.updateUploadedNic(CMSCon, personalBean, systemAuditBean.getUserName());
                }
                persistance.updateSecondryCardApplicationDocument(CMSCon, personalBean);
            }

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
        return successInsert;
    }

    public DebitPersonalBean checkCardApplicationStatus(String applicationId) throws Exception {
        boolean isExist = false;
        try {

            persistance = new CustomerPersonalPersistance();
            debitPersonalBean = new DebitPersonalBean();
            CMSCon = DBConnection.getConnection();

            isExist = persistance.checkCardApplicationStatus(CMSCon, applicationId);

            if (isExist) {

                debitPersonalBean = persistance.getDebitCardApplicationPrimeryDetails(CMSCon, applicationId);
                String signature = persistance.getDebitCardApplicationPrimarySignature(CMSCon, applicationId);
                String nic = persistance.getDebitCardApplicationPrimaryNIC(CMSCon, applicationId);

                debitPersonalBean.setSignatureFileName(signature);
                debitPersonalBean.setNicFileName(nic);

            } else {

                debitPersonalBean = null;
            }

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

        return debitPersonalBean;
    }

    public DebitPersonalBean getDebitCardApplicationDetails(String applicationId) throws Exception {
        boolean isExist = false;
        try {

            persistance = new CustomerPersonalPersistance();
            debitPersonalBean = new DebitPersonalBean();
            CMSCon = DBConnection.getConnection();

            debitPersonalBean = persistance.getDebitCardApplicationDetails(CMSCon, applicationId);

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

        return debitPersonalBean;
    }

    public String getBankBranchesDropDownLst(String bankcode) throws Exception {
        String result = "";
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            result = persistance.getBankBranchesDropDownLst(CMSCon, bankcode);

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

        return result;
    }

    public List<CurrencyBean> getCurrencyList() throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            currencyList = persistance.getCurrencyList(CMSCon);

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

        return currencyList;
    }

    public int applicationModifyComplete(String applicationId, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd = 0;
        try {

            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            checkingManager = new ApplicationCheckingManager();
            boolean yes = persistance.updateCardApplicationStatus(applicationId, StatusVarList.APP_FILLING_COMPLETE, CMSCon);

            if (yes == true) {

                sysAuditManager.insertAudit(systemAuditBean, CMSCon);

                ApplicationHistoryBean historyBean = new ApplicationHistoryBean();

                historyBean.setApplicationId(applicationId);
                historyBean.setApplicationLevel("AMOD");
                historyBean.setLastUpdatedUser(systemAuditBean.getLastUpdateduser());

                historyBean.setRemarks(applicationId + ": Application modification completed");
                historyBean.setStatus("HCOM");

                historyPersistance = new ApplicationAssignPersistance();
                historyPersistance.insertApplicationHistory(CMSCon, historyBean);
                isAdd = 1;
            }

            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    public HashMap<String, String> getAllAccountType() throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            accountTypeList = new HashMap<String, String>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            accountTypeList = persistance.getAllAccountType(CMSCon);
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
        return accountTypeList;
    }

    public String getCardCategory(String appID) throws Exception {
        String cardCategory;
        try {

            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            cardCategory = persistance.getCardCategory(CMSCon, appID);

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
        return cardCategory;
    }

    public String getCommonCurrency() throws Exception {
        String comminCurrency;
        try {

            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            comminCurrency = persistance.getCommonCurrency(CMSCon);

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
        return comminCurrency;
    }

    public String getPrimaryResidenceDetailsByAppId(String primaryApplicationId) throws Exception {

        String primResDetails = "";
        try {

            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            primResDetails = persistance.getPrimaryResidenceDetailsByAppID(CMSCon, primaryApplicationId);
        } catch (Exception ex) {
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
        return primResDetails;
    }

    public String getPrimaryResidenceDeatilsByCardNumber(String primaryCardNumber) throws Exception {
        String primResDetails = "";
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            primResDetails = persistance.getPrimaryResidenceDetailsByCardNumber(CMSCon, primaryCardNumber);
        } catch (Exception ex) {
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
        return primResDetails;
    }

    public String getPrimaryBillingDetailsByAppId(String primaryApplicationId) throws Exception {
        String primBillingDetails = "";
        try {

            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            primBillingDetails = persistance.getPrimaryBillingDetailsByAppId(CMSCon, primaryApplicationId);
        } catch (Exception ex) {
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
        return primBillingDetails;
    }

    public String getPrimaryBillingDetailsByCardNum(String priamryCardNumber) throws Exception {
        String primBillingDetails = "";
        try {

            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            primBillingDetails = persistance.getPrimaryBillingDetailsByCArdNum(CMSCon, priamryCardNumber);
        } catch (Exception ex) {
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
        return primBillingDetails;
    }

    public String getPrimaryCardNumber(String primaryApplicationId) throws Exception {
        String primaryCardNumber = "";

        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            primaryCardNumber = persistance.getPrimaryCardNumber(CMSCon, primaryApplicationId);
        } catch (Exception ex) {
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

        return primaryCardNumber;
    }

    //get primary application id from card number
    public String getPrimaryApplicationId(String primaryCardNumber) throws Exception {
        String primaryAppId = "";
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            primaryAppId = persistance.getPrimaryApplicationId(CMSCon, primaryCardNumber);
        } catch (Exception ex) {
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
        return primaryAppId;
    }

    //get primaryId
    public String getPrimaryIdDetails(String primaryAppId) throws Exception {
        String primaryIdData = null;
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            primaryIdData = persistance.getPrimaryIdDetails(CMSCon, primaryAppId);
        } catch (Exception ex) {
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
        return primaryIdData;
    }

    //get credit limit of primary card in supplimentary card
    public String getRequestedCreditLimitFromPercentageValue(String priaryCardNum, String per) throws Exception {

        Double percentage = Double.parseDouble(per);
        Double cLimit;

        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            Double primaryCardCreditLimit = Double.parseDouble(persistance.getPrimaryCardCreditLimit(CMSCon, priaryCardNum));
            cLimit = (primaryCardCreditLimit * percentage) / 100;

        } catch (Exception ex) {
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
        return String.valueOf(cLimit);

    }

    public List<EstablishmentDetailsBean> getEstablishedCompanyList() throws Exception {
        List<EstablishmentDetailsBean> list = new ArrayList<EstablishmentDetailsBean>();
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            list = persistance.getEstablishedCompanyList(CMSCon);

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

        return list;
    }

    public EstablishmentDetailsBean getAllDetailsEstablishment(String applicationId) throws Exception {
        EstablishmentDetailsBean establishmentDetailsBean = new EstablishmentDetailsBean();
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            establishmentDetailsBean = persistance.getAllDetailsEstablishment(CMSCon, applicationId);

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

        return establishmentDetailsBean;
    }

    public int updateEstablishmentDetails(EstablishmentDetailsBean establishmentDetailsBean, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd;
        try {

            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            checkingManager = new ApplicationCheckingManager();
            boolean edit = false;
            if (establishmentDetailsBean.getMode() != null && establishmentDetailsBean.getMode().equals("edit")) {
                edit = true;
            }

            isAdd = persistance.updateEstablishmentDetailRecord(CMSCon, establishmentDetailsBean);

            ApplicationHistoryBean historyBean = new ApplicationHistoryBean();

            historyBean.setApplicationId(establishmentDetailsBean.getApplicationId());
            historyBean.setApplicationLevel("DTEN");
            historyBean.setLastUpdatedUser(establishmentDetailsBean.getLastUpdateUser());

            if (isAdd == 1) {
                historyBean.setRemarks(establishmentDetailsBean.getApplicationId() + ": Application processing started");
                historyBean.setStatus("HCOM");
            } else {
                historyBean.setRemarks(establishmentDetailsBean.getApplicationId() + ": Application processing failed");
                historyBean.setStatus("HFAL");
            }
            historyPersistance = new ApplicationAssignPersistance();
            historyPersistance.insertApplicationHistory(CMSCon, historyBean);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            if (!edit) {
                persistance.updateStatusOfCardApplicationStatus(establishmentDetailsBean.getApplicationId(), "TAB01STATUS", CMSCon);
                persistance.updateCardApplicationStatus(establishmentDetailsBean.getApplicationId(), StatusVarList.APP_PROCESS, CMSCon);
            }

            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    public List<AssetLiabilityTypeBean> getAssetsLiabilityTypeList() throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            assetsLiabilityTypeList = persistance.getAssetsLiabilityTypeList(CMSCon);

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

        return assetsLiabilityTypeList;
    }

    public List<AssetBean> getAssetsList() throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            assetsList = persistance.getAssetsList(CMSCon);

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

        return assetsList;
    }

    public List<LiabilityBean> getLiabilityList() throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            liabilityList = persistance.getLiabilityList(CMSCon);

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

        return liabilityList;

    }

    public int insertAssetLiabilityDetailList(List<EstablishmentAssetsBean> establishmentAssetList,
            List<EstablishmentLiabilityBean> establishmentLiabilityList,
            String appId, SystemAuditBean systemAuditBean, String loggedUserName) throws Exception {
        int isAdd = 0;
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            checkingManager = new ApplicationCheckingManager();

            if (establishmentAssetList != null) {
                List<EstablishmentAssetsBean> savedEstablishmentAssetList = checkingManager.getAllAssetDetails(establishmentAssetList.get(0).getBusinessRegNumber());
                setLastUpdateUserAssets(establishmentAssetList, loggedUserName);
                if (savedEstablishmentAssetList.size() > 0) {
                    isAdd = persistance.updateEstablishmentAssetDetailsRecords(CMSCon, establishmentAssetList, establishmentAssetList.get(0).getBusinessRegNumber());
                } else {
                    isAdd = persistance.insertEstablishmentAssetDetailsRecords(CMSCon, establishmentAssetList);
                }
            }

            if (establishmentLiabilityList != null) {
                List<EstablishmentLiabilityBean> savedEstablishmentLiabilityList = checkingManager.getAllLiabilityDetails(establishmentLiabilityList.get(0).getBusinessRegNumber());
                setLastUpdateUserLLiabilities(establishmentLiabilityList, loggedUserName);
                if (savedEstablishmentLiabilityList.size() > 0) {
                    isAdd = persistance.updateEstablishmentLiabilityDetailsRecords(CMSCon, establishmentLiabilityList, establishmentLiabilityList.get(0).getBusinessRegNumber());
                } else {
                    isAdd = persistance.insertEstablishmentLiabilityDetailsRecords(CMSCon, establishmentLiabilityList);
                }
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            if (isAdd == 1) {
                persistance.updateStatusOfCardApplicationStatus(appId, "TAB02STATUS", CMSCon);
            }
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    private void setLastUpdateUserAssets(List<EstablishmentAssetsBean> savedEstablishmentAssetList, String loggeduserName) {
        if (savedEstablishmentAssetList != null) {
            for (EstablishmentAssetsBean bean : savedEstablishmentAssetList) {
                bean.setLastUpdateduser(loggeduserName);
            }
        }
    }

    private void setLastUpdateUserLLiabilities(List<EstablishmentLiabilityBean> savedEstablishmentLiabilityList, String loggedUserName) {
        if (savedEstablishmentLiabilityList != null) {
            for (EstablishmentLiabilityBean bean : savedEstablishmentLiabilityList) {
                bean.setLastUpdateduser(loggedUserName);
            }
        }
    }

    public EstablishmentDetailsBean getAllDetailsEstablishmentByBusinessRegNumber(String businessRegNumber) throws Exception {
        EstablishmentDetailsBean establishmentDetailsBean = new EstablishmentDetailsBean();
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            establishmentDetailsBean = persistance.getAllDetailsEstablishmentByBusinessRegNumber(CMSCon, businessRegNumber);

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

        return establishmentDetailsBean;
    }

    public String getPrimaryCardNoFromApplication(String primaryId) throws Exception {
        String primaryCardNo = "";

        try {
            persistance = new CustomerPersonalPersistance();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            primaryCardNo = persistance.getPrimaryCardNoFromApplication(CMSCon, primaryId);

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {

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

        return primaryCardNo;
    }

    public CustomerPersonalBean getSupplementaryCustomerDetails(String applicationId) throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            customerPersonalBean = persistance.getSupplementaryCustomerDetails(CMSCon, applicationId);
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

        return customerPersonalBean;
    }

    public CustomerPersonalBean getEstablishmentCustomerDetails(String applicationId) throws Exception {
        try {
            persistance = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            customerPersonalBean = persistance.getEstablishmentCustomerDetails(CMSCon, applicationId);
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

        return customerPersonalBean;
    }
}
