/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.session;

import com.epic.cms.admin.camm.numbergeneration.bean.NumberFormatFieldBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.CommissionProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.FeeProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.InterestprofileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.ProfileCategoryBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileTypeBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AquireBinFormBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BillingCycleMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardDomainsBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CommonConfigurationBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CreditConfirmationSchemaBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.EmbossFileFormatDetailBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.FileDownloadSearchBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.LetterBodyFormatBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ProductionModeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ServiceCodeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.UtilityProviderBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationSectionPageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRolePrivilegeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ChannelIncomeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CountryMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyExchangeRateBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ExChangeRateBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeTypeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ListenerTxnBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.OnlineTxnTypeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.PostalCodeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.admin.templatemgt.carddomaintemplate.bean.CardDomainBean;
import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.backoffice.cardlimitincrement.bean.PermLimitIncrementBean;
import com.epic.cms.backoffice.cardlimitincrement.bean.TempLimitIncrementBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.CardRenewalBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterDetailsBean;
import com.epic.cms.backoffice.installementpayment.bean.BTPaymentConfirmBean;
import com.epic.cms.backoffice.installementpayment.bean.EasyPaymentConfirmBean;
import com.epic.cms.backoffice.installementpayment.bean.LoanOnCardPayConfirmBean;
import com.epic.cms.backoffice.payment.bean.ChequeBean;
import com.epic.cms.backoffice.txnadjustment.bean.TransactionAdjustment;
import com.epic.cms.callcenter.callcentersearch.bean.CustomerSearchBean;
import com.epic.cms.callcenter.callcentersearch.bean.MerchantSearchBean;
import com.epic.cms.callcenter.callcentersearch.bean.QuestionAnswerBean;
import com.epic.cms.callcenter.callcentersearch.bean.ViewDataBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreFieldBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.NumberGenarationDataBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.NumberGenarationSearchBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.SearchAssignAppBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AreaBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AssetBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AssetLiabilityTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BankBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BankBranchBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardCategoryBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardExpensesBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardIncomeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardagainstFDApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerEmploymentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
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
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.VerificationCategoryBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.AreaBeanCoCustomer;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.CustomerCorporateBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.DocumentUploadCorporateBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.EstCardManagerDetailsBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.EstablishCardApplicationBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.VerificationCategoryCorporateBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.ApplicationVerificationBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedApplicantDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedEstDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CorporateVerifyBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.DebitCheckBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.DebitUploadDocsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.PreviousApplicationDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.SupplementaryDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerificationPointsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerifyAccountDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerifyApplicantDetailBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingFileDownloadBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingSearchBean;
import com.epic.cms.cpmm.requestconfirmation.bean.RequestConfirmationBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantBankBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantBankBranchBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantCustomerBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantPaymentCycleBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantPaymentModeBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.TerminalDataCaptureBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.LoanOnCardPaymentRequestBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationDetailsBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationSummaryBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ConnectionTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.StatusTypeBean;
import com.epic.cms.switchcontrol.listenerconfig.bean.ListenerTypeBean;
import com.epic.cms.switchcontrol.responcecodemgt.bean.ResponceCodeBean;
import com.epic.cms.switchcontrol.sysmsg.bean.ViewAlertBean;
import com.epic.cms.switchcontrol.sysmsg.bean.ViewFailAlertBean;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 *
 * @author janaka_h
 */
public class SessionVarList {

    private String mechantCustomerNumber;
    private String merchantId;
    private String cardNumber;
    private String cardType;
    private String cardStatus;
    private String terminalId;
    private int sequenceNoLength;
    private NumberFormatFieldBean numFormat = null;
    private String disableProdMode;
    private String loggedApplicationCode;
    private String loggedApplicationDes;
    private SessionUser CMSSessionUser;
    private List<StatusBean> statusList;
    private List<CreditScoreFieldBean> fieldBeanLst;
    private List<UserRoleBean> userRoleList;
    private List<String> userPageTaskList;
    private List<SystemUserBean> userList = null;
    private List<TaskBean> taskDetails;
    private List<PageBean> pageDetails;
    private List<CurrencyBean> exchangeCurrencyList;
    private ExChangeRateBean exChangeRateBean;
    private List<UserRolePrivilegeBean> appSecPageTaskList;
    private List<ApplicationSectionPageBean> sectionPageList = null;
    private List<ApplicationModuleBean> applicationModuleList = null;
    private List<CurrencyBean> currencyDetailList = null;
    private List<PostalCodeBean> postalCodeDetailList = null;
    private List<CountryMgtBean> countryCodeList = null;
    private List<ProfileCategoryBean> profileCategoryDetails;
    private List<CardDomainBean> cardDomainList = null;
    private List<CardDomainBean> seachedCardDomainList = null;
    private List<CardDomainsBean> cardDomainsList = null;
    private List<CustomerTempBean> customerTempList = null;
    private List<ConnectionTypeBean> connectionTypeList = null;
    private List<StatusTypeBean> statusTypeList = null;
    private List<ChannelTypeBean> channelTypeList = null;
    private List<ChannelTypeBean> unusedChannelList = null;
    private List<OnlineTxnTypeBean> onlineTxnTypeList = null;
    private List<ChannelIncomeBean> incomeChannelList = null;
    private List<RiskProfileBean> riskProfileList = null;   // riskProfile Mgt
    private List<CardProductBean> cardProductMgtList = null;
    private List<TransactionProfileBean> transactionProfileList = null;
    private List<ServiceCodeBean> serviceCodeList = null;
    private List<ListenerTypeBean> listenerTypeList = null;
    private List<FeeTypeBean> feeTypeList = null;
    private RequestConfirmationBean confBean = null;
    private TempLimitIncrementBean tempBean = null;
    private List<PermLimitIncrementBean> permBeanList = null;
    private List<TempLimitIncrementBean> tempBeanList = null;
    private List<EasyPaymentConfirmBean> easyreqBeanList = null;
    private List<BTPaymentConfirmBean> btreqBeanList = null;
    private List<LoanOnCardPayConfirmBean> loanoncardreqBeanList = null;
    private List<TransactionAdjustment> cardtxnadjustmentBeanList = null;
    private EasyPaymentConfirmBean easyreqBean = null;
    private BTPaymentConfirmBean btreqBean = null;
    private LoanOnCardPayConfirmBean locreqBean=null;
    private TransactionAdjustment adjustmentcardreqBean = null;
    private IdBean idBean = null;
    private String merchantCustomerNumber = null;
    private CardTemplateBean cardBean = null;
    private SystemAuditBean auditBean = null;
    private CommissionProfileBean commissionBean = null;
    private List<CommissionProfileBean> commissionTxnList = null;
    private List<ListenerTxnBean> listenerList = null;
    private List<ListenerTxnBean> unusedListenerList = null;
    private List<CommissionProfileBean> commissionList = null;
    private List<VerifyAccountDetailsBean> verifyAccoutBeanLst = null;
    private String cardCategoryCode = null;
    private List<StatusBean> commonStatusList = null;
    private List<BillingCycleMgtBean> statementList = null;
    private List<MerchantPaymentCycleBean> paymentList = null;
    private List<MerchantPaymentModeBean> paymentModeList = null;
    private List<MerchantBankBean> merchantBankList = null;
    private List<MerchantBankBranchBean> merchantBankBranchList = null;
    private ApplicationDetailsBean detailsBean = null;
    private HashMap<String, String> currencyTypeList = null;
    private List<ApplicationSummaryBean> appSummaryReport = null;
    private ApplicationSummaryBean appSummaryBean = null;
    private List<CreditConfirmationSchemaBean> creditLimitSchemaList = null;
    private List<UserRoleBean> notAssignedUserRolesToSchemaList = null;
    private List<UserRoleBean> assignedUserRolesToSchemaList = null;
    private List<RequestConfirmationBean> requestList = null;

    private List<AssetLiabilityTypeBean> assetsLiabilityTypeList = null;
    private List<AssetBean> assetsList = null;
    private List<LiabilityBean> liabilityList = null;
    private List<EstablishmentLiabilityBean> establishmentLiabilityList = null;
    private List<EstablishmentAssetsBean> establishmentAssetList = null;
    private CommonConfigurationBean comConfigBean = null;

    private String callcenterSearchAppId;
    private String callcenterSearchCardCategoty;
    private String callcenterSearchCardCardNo;
    private List<UtilityProviderBean> utilityProviderList=null;
    private String commonBankCode=null;
    private String commonCurrencyCode=null;

    public String getCommonBankCode() {
        return commonBankCode;
    }

    public void setCommonBankCode(String commonBankCode) {
        this.commonBankCode = commonBankCode;
    }

    public String getCommonCurrencyCode() {
        return commonCurrencyCode;
    }

    public void setCommonCurrencyCode(String commonCurrencyCode) {
        this.commonCurrencyCode = commonCurrencyCode;
    }

    public CommonConfigurationBean getComConfigBean() {
        return comConfigBean;
    }

    public void setComConfigBean(CommonConfigurationBean comConfigBean) {
        this.comConfigBean = comConfigBean;
    }

    public List<UtilityProviderBean> getUtilityProviderList() {
        return utilityProviderList;
    }

    public void setUtilityProviderList(List<UtilityProviderBean> utilityProviderList) {
        this.utilityProviderList = utilityProviderList;
    }

    public List<RequestConfirmationBean> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<RequestConfirmationBean> requestList) {
        this.requestList = requestList;
    }

    public List<UserRoleBean> getAssignedUserRolesToSchemaList() {
        return assignedUserRolesToSchemaList;
    }

    public void setAssignedUserRolesToSchemaList(List<UserRoleBean> assignedUserRolesToSchemaList) {
        this.assignedUserRolesToSchemaList = assignedUserRolesToSchemaList;
    }

    public List<UserRoleBean> getNotAssignedUserRolesToSchemaList() {
        return notAssignedUserRolesToSchemaList;
    }

    public void setNotAssignedUserRolesToSchemaList(List<UserRoleBean> notAssignedUserRolesToSchemaList) {
        this.notAssignedUserRolesToSchemaList = notAssignedUserRolesToSchemaList;
    }

    public List<CreditConfirmationSchemaBean> getCreditLimitSchemaList() {
        return creditLimitSchemaList;
    }

    public void setCreditLimitSchemaList(List<CreditConfirmationSchemaBean> creditLimitSchemaList) {
        this.creditLimitSchemaList = creditLimitSchemaList;
    }
//    private HashMap<String, String> paymentModeList = null;
    private String manualTxnStatus = null;
    private HashMap<String, String> keyId = null;
    private HashMap<String, String> cardKeyList = null;
    private HashMap<String, String> currencyMap = null;
    private HashMap<String, String> areaMap = null;
    private HashMap<String, String> bankNames = null;
    private HashMap<String, String> letterFormatList = null;
    private HashSet<String> letterFieldsList = null;
    private HashSet<String> letterBodyFieldsList = null;
    private HashMap<String, String> letterProcessList = null;
    private String commonParam = null;
    private String customerId = null;
    private String accountId = null;
    private List<CardProductBean> cardProductBinBeanLst = null;
    private String isBack = null;
    private CardRenewalBean searchedLetterBean = null;
    private String titleValue = null;//letter genaration
    private LetterBodyFormatBean letterBodyFormatBean = null;//letter genaration
    private String corporateAppId = null;
    private CorporateVerifyBean verifyBean = null;
    private HashMap<String, String> cardCategoryMap;
    private ApplicationVerificationBean applicationVerificationBean = null;
    private List<ChequeBean> chequeList = null;
    private ChequeBean chequeSearchBean = null;
    private HashMap<String, String> queueList = null;
    private String applicationTypeCode;
    private CardagainstFDApplicationBean customerCardagainstFDApplicationBean;
    private List<BankBranchBean> bankBranchList = null;
    private List<EstablishmentDetailsBean> establishedCompanyList;
    private LetterDetailsBean searchedLettersBean=null;

    public LetterDetailsBean getSearchedLettersBean() {
        return searchedLettersBean;
    }

    public void setSearchedLettersBean(LetterDetailsBean searchedLettersBean) {
        this.searchedLettersBean = searchedLettersBean;
    }

    

    public List<EstablishmentDetailsBean> getEstablishedCompanyList() {
        return establishedCompanyList;
    }

    public void setEstablishedCompanyList(List<EstablishmentDetailsBean> establishedCompanyList) {
        this.establishedCompanyList = establishedCompanyList;
    }

    public List<BankBranchBean> getBankBranchList() {
        return bankBranchList;
    }

    public void setBankBranchList(List<BankBranchBean> bankBranchList) {
        this.bankBranchList = bankBranchList;
    }

    public CardagainstFDApplicationBean getCustomerCardagainstFDApplicationBean() {
        return customerCardagainstFDApplicationBean;
    }

    public void setCustomerCardagainstFDApplicationBean(CardagainstFDApplicationBean customerCardagainstFDApplicationBean) {
        this.customerCardagainstFDApplicationBean = customerCardagainstFDApplicationBean;
    }

    public String getApplicationTypeCode() {
        return applicationTypeCode;
    }

    public void setApplicationTypeCode(String applicationTypeCode) {
        this.applicationTypeCode = applicationTypeCode;
    }

    public HashMap<String, String> getQueueList() {
        return queueList;
    }

    public void setQueueList(HashMap<String, String> queueList) {
        this.queueList = queueList;
    }
    private String collectionId = null;

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    private String callLogId = null;

    public String getCallLogId() {
        return callLogId;
    }

    public void setCallLogId(String callLogId) {
        this.callLogId = callLogId;
    }

    public String getLoggedApplicationDes() {
        return loggedApplicationDes;
    }

    public void setLoggedApplicationDes(String loggedApplicationDes) {
        this.loggedApplicationDes = loggedApplicationDes;
    }

    public String getCorporateAppId() {
        return corporateAppId;
    }

    public void setCorporateAppId(String corporateAppId) {
        this.corporateAppId = corporateAppId;
    }

    public CorporateVerifyBean getVerifyBean() {
        return verifyBean;
    }

    public void setVerifyBean(CorporateVerifyBean verifyBean) {
        this.verifyBean = verifyBean;
    }

    public ChequeBean getChequeSearchBean() {
        return chequeSearchBean;
    }

    public void setChequeSearchBean(ChequeBean chequeSearchBean) {
        this.chequeSearchBean = chequeSearchBean;
    }

    public List<ChequeBean> getChequeList() {
        return chequeList;
    }

    public void setChequeList(List<ChequeBean> chequeList) {
        this.chequeList = chequeList;
    }

    public CardRenewalBean getSearchedLetterBean() {
        return searchedLetterBean;
    }

    public void setSearchedLetterBean(CardRenewalBean searchedLetterBean) {
        this.searchedLetterBean = searchedLetterBean;
    }

    public LetterBodyFormatBean getLetterBodyFormatBean() {
        return letterBodyFormatBean;
    }

    public void setLetterBodyFormatBean(LetterBodyFormatBean letterBodyFormatBean) {
        this.letterBodyFormatBean = letterBodyFormatBean;
    }

    public String getTitleValue() {
        return titleValue;
    }

    public void setTitleValue(String titleValue) {
        this.titleValue = titleValue;
    }

    public HashSet<String> getLetterBodyFieldsList() {
        return letterBodyFieldsList;
    }

    public void setLetterBodyFieldsList(HashSet<String> letterBodyFieldsList) {
        this.letterBodyFieldsList = letterBodyFieldsList;
    }

    public HashMap<String, String> getLetterProcessList() {
        return letterProcessList;
    }

    public void setLetterProcessList(HashMap<String, String> letterProcessList) {
        this.letterProcessList = letterProcessList;
    }

    public HashSet<String> getLetterFieldsList() {
        return letterFieldsList;
    }

    public void setLetterFieldsList(HashSet<String> letterFieldsList) {
        this.letterFieldsList = letterFieldsList;
    }

    public HashMap<String, String> getLetterFormatList() {
        return letterFormatList;
    }

    public void setLetterFormatList(HashMap<String, String> letterFormatList) {
        this.letterFormatList = letterFormatList;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public HashMap<String, String> getAreaMap() {
        return areaMap;
    }

    public void setAreaMap(HashMap<String, String> areaMap) {
        this.areaMap = areaMap;
    }

    public HashMap<String, String> getBankNames() {
        return bankNames;
    }

    public void setBankNames(HashMap<String, String> bankNames) {
        this.bankNames = bankNames;
    }

    public String getCommonParam() {
        return commonParam;
    }

    public void setCommonParam(String commonParam) {
        this.commonParam = commonParam;
    }

    public HashMap<String, String> getCurrencyMap() {
        return currencyMap;
    }

    public void setCurrencyMap(HashMap<String, String> currencyMap) {
        this.currencyMap = currencyMap;
    }

    public HashMap<String, String> getCardKeyList() {
        return cardKeyList;
    }

    public void setCardKeyList(HashMap<String, String> cardKeyList) {
        this.cardKeyList = cardKeyList;
    }

    public HashMap<String, String> getKeyId() {
        return keyId;
    }

    public void setKeyId(HashMap<String, String> keyId) {
        this.keyId = keyId;
    }

    public List<ChannelTypeBean> getUnusedChannelList() {
        return unusedChannelList;
    }

    public void setUnusedChannelList(List<ChannelTypeBean> unusedChannelList) {
        this.unusedChannelList = unusedChannelList;
    }

    public List<ListenerTxnBean> getUnusedListenerList() {
        return unusedListenerList;
    }

    public void setUnusedListenerList(List<ListenerTxnBean> unusedListenerList) {
        this.unusedListenerList = unusedListenerList;
    }

    public String getManualTxnStatus() {
        return manualTxnStatus;
    }

    public void setManualTxnStatus(String manualTxnStatus) {
        this.manualTxnStatus = manualTxnStatus;
    }
    private ApplicationSummaryBean appSumBean;
    private HashMap<String, String> runningModeList = null;

    public ApplicationSummaryBean getAppSumBean() {
        return appSumBean;
    }

    public void setAppSumBean(ApplicationSummaryBean appSumBean) {
        this.appSumBean = appSumBean;
    }

    public HashMap<String, String> getRunningModeList() {
        return runningModeList;
    }

    public void setRunningModeList(HashMap<String, String> runningModeList) {
        this.runningModeList = runningModeList;
    }

    public String getMechantCustomerNumber() {
        return mechantCustomerNumber;
    }

    public void setMechantCustomerNumber(String mechantCustomerNumber) {
        this.mechantCustomerNumber = mechantCustomerNumber;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
    private String primerySignatureFileName = null;
    private String secondrySignatureFileName = null;
    private String primeryNicFileName = null;
    private String secondryNicFileName = null;
    private HashMap<String, String> branchNameList = null;

    public HashMap<String, String> getBranchNameList() {
        return branchNameList;
    }

    public void setBranchNameList(HashMap<String, String> branchNameList) {
        this.branchNameList = branchNameList;
    }

    public String getPrimeryNicFileName() {
        return primeryNicFileName;
    }

    public void setPrimeryNicFileName(String primeryNicFileName) {
        this.primeryNicFileName = primeryNicFileName;
    }

    public String getSecondryNicFileName() {
        return secondryNicFileName;
    }

    public void setSecondryNicFileName(String secondryNicFileName) {
        this.secondryNicFileName = secondryNicFileName;
    }

    public String getSecondrySignatureFileName() {
        return secondrySignatureFileName;
    }

    public void setSecondrySignatureFileName(String secondrySignatureFileName) {
        this.secondrySignatureFileName = secondrySignatureFileName;
    }

    public String getPrimerySignatureFileName() {
        return primerySignatureFileName;
    }

    public void setPrimerySignatureFileName(String primerySignatureFileName) {
        this.primerySignatureFileName = primerySignatureFileName;
    }

    public HashMap<String, String> getCurrencyTypeList() {
        return currencyTypeList;
    }

    public void setCurrencyTypeList(HashMap<String, String> currencyTypeList) {
        this.currencyTypeList = currencyTypeList;
    }

    public ApplicationDetailsBean getDetailsBean() {
        return detailsBean;
    }

    public void setDetailsBean(ApplicationDetailsBean detailsBean) {
        this.detailsBean = detailsBean;
    }

    public ApplicationSummaryBean getAppSummaryBean() {
        return appSummaryBean;
    }

    public void setAppSummaryBean(ApplicationSummaryBean appSummaryBean) {
        this.appSummaryBean = appSummaryBean;
    }

    public List<ApplicationSummaryBean> getAppSummaryReport() {
        return appSummaryReport;
    }

    public void setAppSummaryReport(List<ApplicationSummaryBean> appSummaryReport) {
        this.appSummaryReport = appSummaryReport;
    }

    public List<MerchantBankBranchBean> getMerchantBankBranchList() {
        return merchantBankBranchList;
    }

    public void setMerchantBankBranchList(List<MerchantBankBranchBean> merchantBankBranchList) {
        this.merchantBankBranchList = merchantBankBranchList;
    }

    public List<MerchantBankBean> getMerchantBankList() {
        return merchantBankList;
    }

    public void setMerchantBankList(List<MerchantBankBean> merchantBankList) {
        this.merchantBankList = merchantBankList;
    }

    public List<MerchantPaymentModeBean> getPaymentModeList() {
        return paymentModeList;
    }

    public void setPaymentModeList(List<MerchantPaymentModeBean> paymentModeList) {
        this.paymentModeList = paymentModeList;
    }

    public List<MerchantPaymentCycleBean> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<MerchantPaymentCycleBean> paymentList) {
        this.paymentList = paymentList;
    }

    public List<BillingCycleMgtBean> getStatementList() {
        return statementList;
    }

    public void setStatementList(List<BillingCycleMgtBean> statementList) {
        this.statementList = statementList;
    }

    public List<StatusBean> getCommonStatusList() {
        return commonStatusList;
    }

    public void setCommonStatusList(List<StatusBean> commonStatusList) {
        this.commonStatusList = commonStatusList;
    }

    public List<VerifyAccountDetailsBean> getVerifyAccoutBeanLst() {
        return verifyAccoutBeanLst;
    }

    public void setVerifyAccoutBeanLst(List<VerifyAccountDetailsBean> verifyAccoutBeanLst) {
        this.verifyAccoutBeanLst = verifyAccoutBeanLst;
    }

    public String getCardCategoryCode() {
        return cardCategoryCode;
    }

    public void setCardCategoryCode(String cardCategoryCode) {
        this.cardCategoryCode = cardCategoryCode;
    }

    public List<CommissionProfileBean> getCommissionList() {
        return commissionList;
    }

    public void setCommissionList(List<CommissionProfileBean> commissionList) {
        this.commissionList = commissionList;
    }

    public List<CommissionProfileBean> getCommissionTxnList() {
        return commissionTxnList;
    }

    public void setCommissionTxnList(List<CommissionProfileBean> commissionTxnList) {
        this.commissionTxnList = commissionTxnList;
    }

    public CommissionProfileBean getCommissionBean() {
        return commissionBean;
    }

    public void setCommissionBean(CommissionProfileBean commissionBean) {
        this.commissionBean = commissionBean;
    }

    public List<ListenerTxnBean> getListenerList() {
        return listenerList;
    }

    public void setListenerList(List<ListenerTxnBean> listenerList) {
        this.listenerList = listenerList;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public SystemAuditBean getAuditBean() {
        return auditBean;
    }

    public void setAuditBean(SystemAuditBean auditBean) {
        this.auditBean = auditBean;
    }

    public CardTemplateBean getCardBean() {
        return cardBean;
    }

    public void setCardBean(CardTemplateBean cardBean) {
        this.cardBean = cardBean;
    }

    public String getMerchantCustomerNumber() {
        return merchantCustomerNumber;
    }

    public void setMerchantCustomerNumber(String merchantCustomerNumber) {
        this.merchantCustomerNumber = merchantCustomerNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public IdBean getIdBean() {
        return idBean;
    }

    public void setIdBean(IdBean idBean) {
        this.idBean = idBean;
    }

    public List<PermLimitIncrementBean> getPermBeanList() {
        return permBeanList;
    }

    public void setPermBeanList(List<PermLimitIncrementBean> permBeanList) {
        this.permBeanList = permBeanList;
    }

    public List<TempLimitIncrementBean> getTempBeanList() {
        return tempBeanList;
    }

    public void setTempBeanList(List<TempLimitIncrementBean> tempBeanList) {
        this.tempBeanList = tempBeanList;
    }

    public List<EasyPaymentConfirmBean> getEasyreqBeanList() {
        return easyreqBeanList;
    }

    public void setEasyreqBeanList(List<EasyPaymentConfirmBean> easyreqBeanList) {
        this.easyreqBeanList = easyreqBeanList;
    }

    public List<TransactionAdjustment> getCardtxnadjustmentBeanList() {
        return cardtxnadjustmentBeanList;
    }

    public void setCardtxnadjustmentBeanList(List<TransactionAdjustment> cardtxnadjustmentBeanList) {
        this.cardtxnadjustmentBeanList = cardtxnadjustmentBeanList;
    }

    public TransactionAdjustment getAdjustmentcardreqBean() {
        return adjustmentcardreqBean;
    }

    public void setAdjustmentcardreqBean(TransactionAdjustment adjustmentcardreqBean) {
        this.adjustmentcardreqBean = adjustmentcardreqBean;
    }

    public EasyPaymentConfirmBean getEasyreqBean() {
        return easyreqBean;
    }

    public void setEasyreqBean(EasyPaymentConfirmBean easyreqBean) {
        this.easyreqBean = easyreqBean;
    }

    public List<FeeTypeBean> getFeeTypeList() {
        return feeTypeList;
    }

    public void setFeeTypeList(List<FeeTypeBean> feeTypeList) {
        this.feeTypeList = feeTypeList;
    }

    public TempLimitIncrementBean getTempBean() {
        return tempBean;
    }

    public void setTempBean(TempLimitIncrementBean tempBean) {
        this.tempBean = tempBean;
    }

    public RequestConfirmationBean getConfBean() {
        return confBean;
    }

    public void setConfBean(RequestConfirmationBean confBean) {
        this.confBean = confBean;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getDisableProdMode() {
        return disableProdMode;
    }

    public void setDisableProdMode(String disableProdMode) {
        this.disableProdMode = disableProdMode;
    }
    private List<ResponceCodeBean> responceList = null;

    public int getSequenceNoLength() {
        return sequenceNoLength;
    }

    public void setSequenceNoLength(int sequenceNoLength) {
        this.sequenceNoLength = sequenceNoLength;
    }

    public NumberFormatFieldBean getNumFormat() {
        return numFormat;
    }

    public void setNumFormat(NumberFormatFieldBean numFormat) {
        this.numFormat = numFormat;
    }
    //to use at aquire Bin Management
    private List<AquireBinFormBean> aquireChannelList = null;
    private List<AquireBinFormBean> aquireStatusList = null;
    private List<AquireBinFormBean> aquireCardTypeList = null;
    private List<AquireBinFormBean> aquireEntryModeList = null;

    public List<ResponceCodeBean> getResponceList() {
        return responceList;
    }

    public void setResponceList(List<ResponceCodeBean> responceList) {
        this.responceList = responceList;
    }

    public List<ListenerTypeBean> getListenerTypeList() {
        return listenerTypeList;
    }

    public void setListenerTypeList(List<ListenerTypeBean> listenerTypeList) {
        this.listenerTypeList = listenerTypeList;
    }

    public List<ServiceCodeBean> getServiceCodeList() {
        return serviceCodeList;
    }

    public void setServiceCodeList(List<ServiceCodeBean> serviceCodeList) {
        this.serviceCodeList = serviceCodeList;
    }

    public List<TransactionProfileBean> getTransactionProfileList() {
        return transactionProfileList;
    }

    public void setTransactionProfileList(List<TransactionProfileBean> transactionProfileList) {
        this.transactionProfileList = transactionProfileList;
    }

    public List<CardProductBean> getCardProductMgtList() {
        return cardProductMgtList;
    }

    public void setCardProductMgtList(List<CardProductBean> cardProductMgtList) {
        this.cardProductMgtList = cardProductMgtList;
    }

    public List<CardDomainsBean> getCardDomainsList() {
        return cardDomainsList;
    }

    public void setCardDomainsList(List<CardDomainsBean> cardDomainsList) {
        this.cardDomainsList = cardDomainsList;
    }

    public List<RiskProfileBean> getRiskProfileList() {
        return riskProfileList;
    }

    public void setRiskProfileList(List<RiskProfileBean> riskProfileList) {
        this.riskProfileList = riskProfileList;
    }

    public List<AquireBinFormBean> getAquireCardTypeList() {
        return aquireCardTypeList;
    }

    public void setAquireCardTypeList(List<AquireBinFormBean> aquireCardTypeList) {
        this.aquireCardTypeList = aquireCardTypeList;
    }

    public List<AquireBinFormBean> getAquireChannelList() {
        return aquireChannelList;
    }

    public void setAquireChannelList(List<AquireBinFormBean> aquireChannelList) {
        this.aquireChannelList = aquireChannelList;
    }

    public List<AquireBinFormBean> getAquireEntryModeList() {
        return aquireEntryModeList;
    }

    public void setAquireEntryModeList(List<AquireBinFormBean> aquireEntryModeList) {
        this.aquireEntryModeList = aquireEntryModeList;
    }

    public List<AquireBinFormBean> getAquireStatusList() {
        return aquireStatusList;
    }

    public void setAquireStatusList(List<AquireBinFormBean> aquireStatusList) {
        this.aquireStatusList = aquireStatusList;
    }

    // end of aquire Bin Management
    public List<ChannelIncomeBean> getIncomeChannelList() {
        return incomeChannelList;
    }

    public void setIncomeChannelList(List<ChannelIncomeBean> incomeChannelList) {
        this.incomeChannelList = incomeChannelList;
    }

    public List<OnlineTxnTypeBean> getOnlineTxnTypeList() {
        return onlineTxnTypeList;
    }

    public void setOnlineTxnTypeList(List<OnlineTxnTypeBean> onlineTxnTypeList) {
        this.onlineTxnTypeList = onlineTxnTypeList;
    }

    public List<FeeProfileBean> getFeeProfileList() {
        return feeProfileList;
    }

    public void setFeeProfileList(List<FeeProfileBean> feeProfileList) {
        this.feeProfileList = feeProfileList;
    }
    private List<FeeProfileBean> feeProfileList = null;

    public List<StatusTypeBean> getStatusTypeList() {
        return statusTypeList;
    }

    public void setStatusTypeList(List<StatusTypeBean> statusTypeList) {
        this.statusTypeList = statusTypeList;
    }
    // to use at emboss file format
    private List<CardTypeMgtBean> cdTypeList;
    private List<EmbossFileFormatDetailBean> formatDetailList;
    //to use at card bin mgt
    private List<ProductionModeBean> productionModeList;
    //to use at call center search
    private ViewDataBean viewBean;

    public List<ProductionModeBean> getProductionModeList() {
        return productionModeList;
    }

    public void setProductionModeList(List<ProductionModeBean> productionModeList) {
        this.productionModeList = productionModeList;
    }

    public ViewDataBean getViewBean() {
        return viewBean;
    }

    public void setViewBean(ViewDataBean viewBean) {
        this.viewBean = viewBean;
    }

    // end call center search-----//
    public List<ChannelTypeBean> getChannelTypeList() {
        return channelTypeList;
    }

    public void setChannelTypeList(List<ChannelTypeBean> channelTypeList) {
        this.channelTypeList = channelTypeList;
    }

    public List<ConnectionTypeBean> getConnectionTypeList() {
        return connectionTypeList;
    }

    public void setConnectionTypeList(List<ConnectionTypeBean> connectionTypeList) {
        this.connectionTypeList = connectionTypeList;
    }
    //Transaction Type Management
    private List<TypeMgtBean> txnTypeDetailList = null;

    public List<TypeMgtBean> getTxnTypeDetailList() {
        return txnTypeDetailList;
    }

    public void setTxnTypeDetailList(List<TypeMgtBean> txnTypeDetailList) {
        this.txnTypeDetailList = txnTypeDetailList;
    }
    // start user role priviledge usage
    private List<ApplicationModuleBean> assignUserRoleApplications = null;
    private List<ApplicationModuleBean> notAssignUserRoleApplications = null;
    private List<PageBean> assignUserRolePages = null;
    private List<PageBean> notAssignUserRolePages = null;
    private List<TaskBean> assignUserRoleTasks = null;
    private List<TaskBean> notAssignUserRoleTasks = null;
    private List<SectionBean> assignUserRoleSections = null;
    private List<SectionBean> notAssignUserRoleSections = null;
// end user role priviledge usage
    ///--ayesh
    private ApplicationModuleBean appBean;
    private String operationtype;
    private List<StatusBean> secList;
    private SectionBean secBean;
    private String errorMsg;
    private String successMsg;
    private CountryMgtBean countryBean;
    private CountryMgtBean countryMgtBean;
    private ExChangeRateBean exbBean;
    private FeeProfileBean feeBean;
    private FeeProfileBean feeUpdateBean;
    //---
    //---
    private ApplicationModuleBean addAppBean;
    //---
    //Profile Management
    private List<InterestprofileBean> interestProfileDetails;
    private List<TransactionTypeBean> transactionTypeList;
    ////////Merchant Customer management
    private MerchantCustomerBean merchantBean;
    //transaction explorer...
    private HashMap<String, String> onlineTxnTypeMap = null;
    private HashMap<String, String> onlineTxnStatusMap = null;

    public HashMap<String, String> getOnlineTxnStatusMap() {
        return onlineTxnStatusMap;
    }

    public void setOnlineTxnStatusMap(HashMap<String, String> onlineTxnStatusMap) {
        this.onlineTxnStatusMap = onlineTxnStatusMap;
    }

    public HashMap<String, String> getOnlineTxnTypeMap() {
        return onlineTxnTypeMap;
    }

    public void setOnlineTxnTypeMap(HashMap<String, String> onlineTxnTypeMap) {
        this.onlineTxnTypeMap = onlineTxnTypeMap;
    }

    public MerchantCustomerBean getMerchantBean() {
        return merchantBean;
    }

    public void setMerchantBean(MerchantCustomerBean merchantBean) {
        this.merchantBean = merchantBean;
    }
//    // card application assign usage
//    private List<ApplicationAssignBean> assignAppsList = null;
//    private List<UserAssignApplicationBean> userAssignApps = null;
    private SearchAssignAppBean searchAssignAppBean = null;
    // card application capture usage
    private List<ApplicationAssignBean> userAssignAppBeanList = null;
    private List<ApplicationAssignBean> userAssignDebitAppList = null;
    private List<CardIncomeBean> sessionIncomeList = null;
    private List<CardBankDetailsBean> sessionBankDetailList = null;
    private List<CustomerCorporateBean> sessionBankDetailCoCustList = null;
    private List<EstCardManagerDetailsBean> sessionManagerDetailList = null;
    private CustomerPersonalBean personalBean = null;
    private EstablishCardApplicationBean companyBean = null;
    private SupplementaryApplicationBean suplimentoryPersonalBean = null;
    private CustomerEmploymentBean employmentBean = null;
    private CardExpensesBean expensesBean = null;
    private List<DocumentUploadBean> sessionDocumentList = null;
    private List<DocumentUploadCorporateBean> sessionDocumentCoList = null;
    private List<EmploymentTypeBean> empTypeList = null;
    private List<OccupationBean> occupationList = null;
    private List<EmploymentNatureBean> natureList = null;
    private List<IncomeTypeBean> incomeTypeList = null;
    private List<BankBean> bankList = null;
    private List<BankBranchBean> branchList = null;
    private SortedMap branchesMapList;
    private HashMap<String, String> applicationDomainList;
    private String applicationId = null;
    private List<CardCategoryBean> cardCategoryLst = null;
    private List<AreaBean> areaList = null;
    private List<AreaBeanCoCustomer> areaListCoCustomer = null;
    private HashMap<String, String> identityTypeListCoCustomer = null;
    private List<String> nationalityCoCustomerList = null;
    private List<String> nationalityList = null;
    private int sumOfIncome;
    private String cardCategory = null;
    private List<VerificationCategoryBean> verificationCateLst = null;
    private List<VerificationCategoryCorporateBean> verificatioCatCoList = null;
    private List<DocumentTypeBean> documentTypeLst = null;
    //Application Checking
    private List<CustomerPersonalBean> personalDetailList;
    // document verification usage
    private List<ApplicationAssignBean> documentVerifyBeanList = null;
    private List<ApplicationAssignBean> corporateVerifyList = null;
    private VerifyApplicantDetailBean verifyCustomerBean = null;
    private String verifyingAppId = null;
    private CheckedApplicantDetailsBean bankVerifyBean = null;
    private List<PreviousApplicationDetailsBean> previousDetailsBeanList = null;
    private List<VerificationPointsBean> verificationPointsBeanList = null;
    // Corporate Establishment Verification
    private List<VerificationPointsBean> corporateVerificationPointsList = null;
    private CheckedEstDetailsBean estBankVerifyBean = null;
    // Supplementary verification
    private SupplementaryDetailsBean supplementaryDetailsBean = null;
    private VerifyApplicantDetailBean mainDetailsBean = null;
    private DocumentUploadBean mctDocumentBean = null;
    private DocumentUploadBean bctDocumentBean = null;
    private DocumentUploadBean personalSignatureBean = null;
    private DocumentUploadBean personalNicBean = null;
    private DocumentUploadBean jointSignatureBean = null;
    private DocumentUploadBean jointNicBean = null;
    private DebitCheckBean debitDetailsBean = null;
    // card embossing management
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> cardTypeMap = null;
    private List<String> usersList = null;
    private HashMap<String, String> cardProductList = null;
    private HashMap<String, String> identityTypeList = null;
    private List<String> visaCardsToEmbossList = null;
    private List<String> masterCardsToEmbossList = null;
    private CardEmbossingSearchBean embossSearchBean = null;
    private List<CardEmbossingFileDownloadBean> cardEmbossingFileList = null;
    private HashMap<String, String> fileTypeList = null;
    private FileDownloadSearchBean fileDownloadSearchBean = null;
    private List<RiskProfileTypeBean> riskTypeList = null;
    private HashMap<String, String> accountTypeList = null;
    //call center use
    private CustomerSearchBean customerSearchBean;
    private MerchantSearchBean merchantSearchBean;
    //bulk card confirm use
    private BulkCardRequestBean bulkcardBean;
    private DebitUploadDocsBean debitUploadDocsBean;
    private List<QuestionAnswerBean> recoveryAnswerBeanList = null;

    public EstablishmentDetailsBean getEstablishmentDetailsBean() {
        return establishmentDetailsBean;
    }

    public void setEstablishmentDetailsBean(EstablishmentDetailsBean establishmentDetailsBean) {
        this.establishmentDetailsBean = establishmentDetailsBean;
    }
    private EstablishmentDetailsBean establishmentDetailsBean = null;

    public List<QuestionAnswerBean> getRecoveryAnswerBeanList() {
        return recoveryAnswerBeanList;
    }

    public void setRecoveryAnswerBeanList(List<QuestionAnswerBean> recoveryAnswerBeanList) {
        this.recoveryAnswerBeanList = recoveryAnswerBeanList;
    }

    public CheckedEstDetailsBean getEstBankVerifyBean() {
        return estBankVerifyBean;
    }

    public void setEstBankVerifyBean(CheckedEstDetailsBean estBankVerifyBean) {
        this.estBankVerifyBean = estBankVerifyBean;
    }

    public List<VerificationPointsBean> getCorporateVerificationPointsList() {
        return corporateVerificationPointsList;
    }

    public void setCorporateVerificationPointsList(List<VerificationPointsBean> corporateVerificationPointsList) {
        this.corporateVerificationPointsList = corporateVerificationPointsList;
    }

    public List<ApplicationAssignBean> getCorporateVerifyList() {
        return corporateVerifyList;
    }

    public void setCorporateVerifyList(List<ApplicationAssignBean> corporateVerifyList) {
        this.corporateVerifyList = corporateVerifyList;
    }

    public DebitUploadDocsBean getDebitUploadDocsBean() {
        return debitUploadDocsBean;
    }

    public void setDebitUploadDocsBean(DebitUploadDocsBean debitUploadDocsBean) {
        this.debitUploadDocsBean = debitUploadDocsBean;
    }

    public DocumentUploadBean getJointNicBean() {
        return jointNicBean;
    }

    public void setJointNicBean(DocumentUploadBean jointNicBean) {
        this.jointNicBean = jointNicBean;
    }

    public DocumentUploadBean getJointSignatureBean() {
        return jointSignatureBean;
    }

    public void setJointSignatureBean(DocumentUploadBean jointSignatureBean) {
        this.jointSignatureBean = jointSignatureBean;
    }

    public DocumentUploadBean getPersonalNicBean() {
        return personalNicBean;
    }

    public void setPersonalNicBean(DocumentUploadBean personalNicBean) {
        this.personalNicBean = personalNicBean;
    }

    public DocumentUploadBean getPersonalSignatureBean() {
        return personalSignatureBean;
    }

    public void setPersonalSignatureBean(DocumentUploadBean personalSignatureBean) {
        this.personalSignatureBean = personalSignatureBean;
    }

    public HashMap<String, String> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(HashMap<String, String> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public HashMap<String, String> getApplicationDomainList() {
        return applicationDomainList;
    }

    public void setApplicationDomainList(HashMap<String, String> applicationDomainList) {
        this.applicationDomainList = applicationDomainList;
    }

    public SortedMap getBranchesMapList() {
        return branchesMapList;
    }

    public void setBranchesMapList(SortedMap branchesMapList) {
        this.branchesMapList = branchesMapList;
    }

    public BulkCardRequestBean getBulkcardBean() {
        return bulkcardBean;
    }

    public void setBulkcardBean(BulkCardRequestBean bulkcardBean) {
        this.bulkcardBean = bulkcardBean;
    }

    public DebitCheckBean getDebitDetailsBean() {
        return debitDetailsBean;
    }

    public void setDebitDetailsBean(DebitCheckBean debitDetailsBean) {
        this.debitDetailsBean = debitDetailsBean;
    }

    public List<RiskProfileTypeBean> getRiskTypeList() {
        return riskTypeList;
    }

    public void setRiskTypeList(List<RiskProfileTypeBean> riskTypeList) {
        this.riskTypeList = riskTypeList;
    }

    public FileDownloadSearchBean getFileDownloadSearchBean() {
        return fileDownloadSearchBean;
    }

    public void setFileDownloadSearchBean(FileDownloadSearchBean fileDownloadSearchBean) {
        this.fileDownloadSearchBean = fileDownloadSearchBean;
    }

    public HashMap<String, String> getFileTypeList() {
        return fileTypeList;
    }

    public void setFileTypeList(HashMap<String, String> fileTypeList) {
        this.fileTypeList = fileTypeList;
    }

    public List<CardEmbossingFileDownloadBean> getCardEmbossingFileList() {
        return cardEmbossingFileList;
    }

    public void setCardEmbossingFileList(List<CardEmbossingFileDownloadBean> cardEmbossingFileList) {
        this.cardEmbossingFileList = cardEmbossingFileList;
    }
    ///////////////////////////// TERMINAL DATA CAPTURE/////////////////////////////////
    private List<TerminalDataCaptureBean> terminalDataBeanList = null;

    public List<TerminalDataCaptureBean> getTerminalDataBeanList() {
        return terminalDataBeanList;
    }

    public void setTerminalDataBeanList(List<TerminalDataCaptureBean> terminalDataBeanList) {
        this.terminalDataBeanList = terminalDataBeanList;
    }
    private TerminalDataCaptureBean termBean = null;
    private TerminalDataCaptureBean searchBean = null;

    public TerminalDataCaptureBean getSearchBean() {
        return searchBean;
    }

    public void setSearchBean(TerminalDataCaptureBean searchBean) {
        this.searchBean = searchBean;
    }

    public TerminalDataCaptureBean getTermBean() {
        return termBean;
    }

    public void setTermBean(TerminalDataCaptureBean termBean) {
        this.termBean = termBean;
    }
    //////////////////////////////////END OF TERMINAL DATA CAPTURE/////////////////////////////
    //////////////////////////////////VIEW ALERT///////////////////////////////////////////
    private ViewAlertBean alertBean = null;

    public ViewAlertBean getAlertBean() {
        return alertBean;
    }

    public void setAlertBean(ViewAlertBean alertBean) {
        this.alertBean = alertBean;
    }
    ///////////////////////////////////END OF VIEW ALERT/////////////////////////////////////
    private ViewFailAlertBean failBean = null;

    public ViewFailAlertBean getFailBean() {
        return failBean;
    }

    public void setFailBean(ViewFailAlertBean failBean) {
        this.failBean = failBean;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    List<FeeBean> txnFeeList = null;
    List<FeeBean> serviceFeeList = null;
    FeeBean realFeeBean = null;
    private List<String> procount = null;

    public List<String> getProcount() {
        return procount;
    }

    public void setProcount(List<String> procount) {
        this.procount = procount;
    }

    public FeeBean getRealFeeBean() {
        return realFeeBean;
    }

    public void setRealFeeBean(FeeBean realFeeBean) {
        this.realFeeBean = realFeeBean;
    }

    public List<FeeBean> getServiceFeeList() {
        return serviceFeeList;
    }

    public void setServiceFeeList(List<FeeBean> serviceFeeList) {
        this.serviceFeeList = serviceFeeList;
    }

    public List<FeeBean> getTxnFeeList() {
        return txnFeeList;
    }

    public void setTxnFeeList(List<FeeBean> txnFeeList) {
        this.txnFeeList = txnFeeList;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    CurrencyExchangeRateBean exchangeRate = null;

    public CurrencyExchangeRateBean getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(CurrencyExchangeRateBean exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
    // card number genaration usage
    private List<CardTypeMgtBean> cardTypeList = null;
    private NumberGenarationSearchBean numberGenaSearchBean = null;
    private List<NumberGenarationDataBean> numberGenaSearchLst = null;
    private String idArray[] = null;

    // ----to use at emboss file format---- //
    public List<CardTypeMgtBean> getCdTypeList() {
        return cdTypeList;
    }

    public void setCdTypeList(List<CardTypeMgtBean> cdTypeList) {
        this.cdTypeList = cdTypeList;
    }

    public List<EmbossFileFormatDetailBean> getFormatDetailList() {
        return formatDetailList;
    }

    public void setFormatDetailList(List<EmbossFileFormatDetailBean> formatDetailList) {
        this.formatDetailList = formatDetailList;
    }

    // --------end emboss file format------- //
    public HashMap<String, String> getCardTypeMap() {
        return cardTypeMap;
    }

    public void setCardTypeMap(HashMap<String, String> cardTypeMap) {
        this.cardTypeMap = cardTypeMap;
    }

    public CardEmbossingSearchBean getEmbossSearchBean() {
        return embossSearchBean;
    }

    public void setEmbossSearchBean(CardEmbossingSearchBean embossSearchBean) {
        this.embossSearchBean = embossSearchBean;
    }

    public List<String> getMasterCardsToEmbossList() {
        return masterCardsToEmbossList;
    }

    public void setMasterCardsToEmbossList(List<String> masterCardsToEmbossList) {
        this.masterCardsToEmbossList = masterCardsToEmbossList;
    }

    public List<String> getVisaCardsToEmbossList() {
        return visaCardsToEmbossList;
    }

    public void setVisaCardsToEmbossList(List<String> visaCardsToEmbossList) {
        this.visaCardsToEmbossList = visaCardsToEmbossList;
    }

    public HashMap<String, String> getIdentityTypeList() {
        return identityTypeList;
    }

    public void setIdentityTypeList(HashMap<String, String> identityTypeList) {
        this.identityTypeList = identityTypeList;
    }

    public HashMap<String, String> getCardProductList() {
        return cardProductList;
    }

    public void setCardProductList(HashMap<String, String> cardProductList) {
        this.cardProductList = cardProductList;
    }

    public HashMap<String, String> getPriorityLevelList() {
        return priorityLevelList;
    }

    public void setPriorityLevelList(HashMap<String, String> priorityLevelList) {
        this.priorityLevelList = priorityLevelList;
    }

    public List<String> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<String> usersList) {
        this.usersList = usersList;
    }

    public DocumentUploadBean getBctDocumentBean() {
        return bctDocumentBean;
    }

    public void setBctDocumentBean(DocumentUploadBean bctDocumentBean) {
        this.bctDocumentBean = bctDocumentBean;
    }

    public DocumentUploadBean getMctDocumentBean() {
        return mctDocumentBean;
    }

    public void setMctDocumentBean(DocumentUploadBean mctDocumentBean) {
        this.mctDocumentBean = mctDocumentBean;
    }

    public VerifyApplicantDetailBean getMainDetailsBean() {
        return mainDetailsBean;
    }

    public void setMainDetailsBean(VerifyApplicantDetailBean mainDetailsBean) {
        this.mainDetailsBean = mainDetailsBean;
    }

    public SupplementaryDetailsBean getSupplementaryDetailsBean() {
        return supplementaryDetailsBean;
    }

    public void setSupplementaryDetailsBean(SupplementaryDetailsBean supplementaryDetailsBean) {
        this.supplementaryDetailsBean = supplementaryDetailsBean;
    }

    public SearchAssignAppBean getSearchAssignAppBean() {
        return searchAssignAppBean;
    }

    public void setSearchAssignAppBean(SearchAssignAppBean searchAssignAppBean) {
        this.searchAssignAppBean = searchAssignAppBean;
    }

    public String getLoggedApplicationCode() {
        return loggedApplicationCode;
    }

    public void setLoggedApplicationCode(String loggedApplicationCode) {
        this.loggedApplicationCode = loggedApplicationCode;
    }

    public List<VerificationPointsBean> getVerificationPointsBeanList() {
        return verificationPointsBeanList;
    }

    public void setVerificationPointsBeanList(List<VerificationPointsBean> verificationPointsBeanList) {
        this.verificationPointsBeanList = verificationPointsBeanList;
    }

    public List<PreviousApplicationDetailsBean> getPreviousDetailsBeanList() {
        return previousDetailsBeanList;
    }

    public void setPreviousDetailsBeanList(List<PreviousApplicationDetailsBean> previousDetailsBeanList) {
        this.previousDetailsBeanList = previousDetailsBeanList;
    }

    public CheckedApplicantDetailsBean getBankVerifyBean() {
        return bankVerifyBean;
    }

    public void setBankVerifyBean(CheckedApplicantDetailsBean bankVerifyBean) {
        this.bankVerifyBean = bankVerifyBean;
    }

    public String getVerifyingAppId() {
        return verifyingAppId;
    }

    public void setVerifyingAppId(String verifyingAppId) {
        this.verifyingAppId = verifyingAppId;
    }

    public VerifyApplicantDetailBean getVerifyCustomerBean() {
        return verifyCustomerBean;
    }

    public void setVerifyCustomerBean(VerifyApplicantDetailBean verifyCustomerBean) {
        this.verifyCustomerBean = verifyCustomerBean;
    }

    public List<ApplicationAssignBean> getDocumentVerifyBeanList() {
        return documentVerifyBeanList;
    }

    public void setDocumentVerifyBeanList(List<ApplicationAssignBean> documentVerifyBeanList) {
        this.documentVerifyBeanList = documentVerifyBeanList;
    }

    public List<CustomerPersonalBean> getPersonalDetailList() {
        return personalDetailList;
    }

    public void setPersonalDetailList(List<CustomerPersonalBean> personalDetailList) {
        this.personalDetailList = personalDetailList;
    }

    public List<ApplicationAssignBean> getUserAssignAppBeanList() {
        return userAssignAppBeanList;
    }

    public void setUserAssignAppBeanList(List<ApplicationAssignBean> userAssignAppBeanList) {
        this.userAssignAppBeanList = userAssignAppBeanList;
    }

//    public List<ApplicationAssignBean> getAssignAppsList() {
//        return assignAppsList;
//    }
//
//    public void setAssignAppsList(List<ApplicationAssignBean> assignAppsList) {
//        this.assignAppsList = assignAppsList;
//    }
//
//    
//    public List<UserAssignApplicationBean> getUserAssignApps() {
//        return userAssignApps;
//    }
//
//    public void setUserAssignApps(List<UserAssignApplicationBean> userAssignApps) {
//        this.userAssignApps = userAssignApps;
//    }
    public List<CreditScoreFieldBean> getFieldBeanLst() {
        return fieldBeanLst;
    }

    public void setFieldBeanLst(List<CreditScoreFieldBean> fieldBeanLst) {
        this.fieldBeanLst = fieldBeanLst;
    }

    public List<TransactionTypeBean> getTransactionTypeList() {
        return transactionTypeList;
    }

    public void setTransactionTypeList(List<TransactionTypeBean> transactionTypeList) {
        this.transactionTypeList = transactionTypeList;
    }

    public List<InterestprofileBean> getInterestProfileDetails() {
        return interestProfileDetails;
    }

    public void setInterestProfileDetails(List<InterestprofileBean> interestProfileDetails) {
        this.interestProfileDetails = interestProfileDetails;
    }

    public List<ProfileCategoryBean> getProfileCategoryDetails() {
        return profileCategoryDetails;
    }

    public void setProfileCategoryDetails(List<ProfileCategoryBean> profileCategoryDetails) {
        this.profileCategoryDetails = profileCategoryDetails;
    }

    public List<CountryMgtBean> getCountryCodeList() {
        return countryCodeList;
    }

    public void setCountryCodeList(List<CountryMgtBean> countryCodeList) {
        this.countryCodeList = countryCodeList;
    }

    public List<PostalCodeBean> getPostalCodeDetailList() {
        return postalCodeDetailList;
    }

    public void setPostalCodeDetailList(List<PostalCodeBean> postalCodeDetailList) {
        this.postalCodeDetailList = postalCodeDetailList;
    }

    public List<ApplicationModuleBean> getAssignUserRoleApplications() {
        return assignUserRoleApplications;
    }

    public void setAssignUserRoleApplications(List<ApplicationModuleBean> assignUserRoleApplications) {
        this.assignUserRoleApplications = assignUserRoleApplications;
    }

    public List<PageBean> getAssignUserRolePages() {
        return assignUserRolePages;
    }

    public void setAssignUserRolePages(List<PageBean> assignUserRolePages) {
        this.assignUserRolePages = assignUserRolePages;
    }

    public List<SectionBean> getAssignUserRoleSections() {
        return assignUserRoleSections;
    }

    public void setAssignUserRoleSections(List<SectionBean> assignUserRoleSections) {
        this.assignUserRoleSections = assignUserRoleSections;
    }

    public List<TaskBean> getAssignUserRoleTasks() {
        return assignUserRoleTasks;
    }

    public void setAssignUserRoleTasks(List<TaskBean> assignUserRoleTasks) {
        this.assignUserRoleTasks = assignUserRoleTasks;
    }

    public List<ApplicationModuleBean> getNotAssignUserRoleApplications() {
        return notAssignUserRoleApplications;
    }

    public void setNotAssignUserRoleApplications(List<ApplicationModuleBean> notAssignUserRoleApplications) {
        this.notAssignUserRoleApplications = notAssignUserRoleApplications;
    }

    public List<PageBean> getNotAssignUserRolePages() {
        return notAssignUserRolePages;
    }

    public void setNotAssignUserRolePages(List<PageBean> notAssignUserRolePages) {
        this.notAssignUserRolePages = notAssignUserRolePages;
    }

    public List<SectionBean> getNotAssignUserRoleSections() {
        return notAssignUserRoleSections;
    }

    public void setNotAssignUserRoleSections(List<SectionBean> notAssignUserRoleSections) {
        this.notAssignUserRoleSections = notAssignUserRoleSections;
    }

    public List<TaskBean> getNotAssignUserRoleTasks() {
        return notAssignUserRoleTasks;
    }

    public void setNotAssignUserRoleTasks(List<TaskBean> notAssignUserRoleTasks) {
        this.notAssignUserRoleTasks = notAssignUserRoleTasks;
    }

    public List<CardDomainBean> getSeachedCardDomainList() {
        return seachedCardDomainList;
    }

    public void setSeachedCardDomainList(List<CardDomainBean> seachedCardDomainList) {
        this.seachedCardDomainList = seachedCardDomainList;
    }

    public List<CustomerTempBean> getCustomerTempList() {
        return customerTempList;
    }

    public void setCustomerTempList(List<CustomerTempBean> customerTempList) {
        this.customerTempList = customerTempList;
    }

    public List<CardDomainBean> getCardDomainList() {
        return cardDomainList;
    }

    public void setCardDomainList(List<CardDomainBean> cardDomainList) {
        this.cardDomainList = cardDomainList;
    }

    public List<CurrencyBean> getCurrencyDetailList() {
        return currencyDetailList;
    }

    public void setCurrencyDetailList(List<CurrencyBean> currencyDetailList) {
        this.currencyDetailList = currencyDetailList;
    }

    public List<ApplicationSectionPageBean> getSectionPageList() {
        return sectionPageList;
    }

    public void setSectionPageList(List<ApplicationSectionPageBean> sectionPageList) {
        this.sectionPageList = sectionPageList;
    }

    public List<UserRolePrivilegeBean> getAppSecPageTaskList() {
        return appSecPageTaskList;
    }

    public void setAppSecPageTaskList(List<UserRolePrivilegeBean> appSecPageTaskList) {
        this.appSecPageTaskList = appSecPageTaskList;
    }

    public List<PageBean> getPageDetails() {
        return pageDetails;
    }

    public void setPageDetails(List<PageBean> pageDetails) {
        this.pageDetails = pageDetails;
    }

    public List<SystemUserBean> getUserList() {
        return userList;
    }

    public void setUserList(List<SystemUserBean> userList) {
        this.userList = userList;
    }

    public List<TaskBean> getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(List<TaskBean> taskDetails) {
        this.taskDetails = taskDetails;
    }

    public List<UserRoleBean> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRoleBean> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public List<StatusBean> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<StatusBean> statusList) {
        this.statusList = statusList;
    }

    public SessionUser getCMSSessionUser() {
        return CMSSessionUser;
    }

    public void setCMSSessionUser(SessionUser CMSSessionUser) {
        this.CMSSessionUser = CMSSessionUser;
    }

    public List<ApplicationModuleBean> getApplicationModuleList() {
        return applicationModuleList;
    }

    public void setApplicationModuleList(List<ApplicationModuleBean> applicationModuleList) {
        this.applicationModuleList = applicationModuleList;
    }

    public List<CurrencyBean> getExchangeCurrencyList() {
        return exchangeCurrencyList;
    }

    public void setExchangeCurrencyList(List<CurrencyBean> exchangeCurrencyList) {
        this.exchangeCurrencyList = exchangeCurrencyList;
    }

    public List<String> getUserPageTaskList() {
        return userPageTaskList;
    }

    public void setUserPageTaskList(List<String> userPageTaskList) {
        this.userPageTaskList = userPageTaskList;
    }

    /**
     * get previous exchange rate bean
     *
     * @return
     */
    public ExChangeRateBean getExChangeRateBean() {
        return exChangeRateBean;
    }

    /**
     * set previous exchange rate bean
     *
     * @param exChangeRateBean
     */
    public void setExChangeRateBean(ExChangeRateBean exChangeRateBean) {
        this.exChangeRateBean = exChangeRateBean;
    }

    public ApplicationModuleBean getAppBean() {
        return appBean;
    }

    public void setAppBean(ApplicationModuleBean appBean) {
        this.appBean = appBean;
    }

    public String getOperationtype() {
        return operationtype;
    }

    public void setOperationtype(String operationtype) {
        this.operationtype = operationtype;
    }

    public List<StatusBean> getSecList() {
        return secList;
    }

    public void setSecList(List<StatusBean> secList) {
        this.secList = secList;
    }

    public SectionBean getSecBean() {
        return secBean;
    }

    public void setSecBean(SectionBean secBean) {
        this.secBean = secBean;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public CountryMgtBean getCountryBean() {
        return countryBean;
    }

    public void setCountryBean(CountryMgtBean countryBean) {
        this.countryBean = countryBean;
    }

    public CountryMgtBean getCountryMgtBean() {
        return countryMgtBean;
    }

    public void setCountryMgtBean(CountryMgtBean countryMgtBean) {
        this.countryMgtBean = countryMgtBean;
    }

    public ExChangeRateBean getExbBean() {
        return exbBean;
    }

    public void setExbBean(ExChangeRateBean exbBean) {
        this.exbBean = exbBean;
    }

    /**
     * get application bean when adding
     *
     * @return
     */
    public ApplicationModuleBean getAddAppBean() {
        return addAppBean;
    }

    /**
     * set application bean when in adding
     *
     * @param addAppBean
     */
    public void setAddAppBean(ApplicationModuleBean addAppBean) {
        this.addAppBean = addAppBean;
    }

    public FeeProfileBean getFeeBean() {
        return feeBean;
    }

    public void setFeeBean(FeeProfileBean feeBean) {
        this.feeBean = feeBean;
    }

    public FeeProfileBean getFeeUpdateBean() {
        return feeUpdateBean;
    }

    public void setFeeUpdateBean(FeeProfileBean feeUpdateBean) {
        this.feeUpdateBean = feeUpdateBean;
    }

    public List<CardIncomeBean> getSessionIncomeList() {
        return sessionIncomeList;
    }

    public void setSessionIncomeList(List<CardIncomeBean> sessionIncomeList) {
        this.sessionIncomeList = sessionIncomeList;
    }

    public List<CardBankDetailsBean> getSessionBankDetailList() {
        return sessionBankDetailList;
    }

    public void setSessionBankDetailList(List<CardBankDetailsBean> sessionBankDetailList) {
        this.sessionBankDetailList = sessionBankDetailList;
    }

    public List<EstCardManagerDetailsBean> getSessionManagerDetailList() {
        return sessionManagerDetailList;
    }

    public void setSessionManagerDetailList(List<EstCardManagerDetailsBean> sessionManagerDetailList) {
        this.sessionManagerDetailList = sessionManagerDetailList;
    }

    public CustomerEmploymentBean getEmploymentBean() {
        return employmentBean;
    }

    public void setEmploymentBean(CustomerEmploymentBean employmentBean) {
        this.employmentBean = employmentBean;
    }

    public CardExpensesBean getExpensesBean() {
        return expensesBean;
    }

    public void setExpensesBean(CardExpensesBean expensesBean) {
        this.expensesBean = expensesBean;
    }

    public CustomerPersonalBean getPersonalBean() {
        return personalBean;
    }

    public void setPersonalBean(CustomerPersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public EstablishCardApplicationBean getCompanyBean() {
        return companyBean;
    }

    public void setCompanyBean(EstablishCardApplicationBean companyBean) {
        this.companyBean = companyBean;
    }

    public List<BankBean> getBankList() {
        return bankList;
    }

    public void setBankList(List<BankBean> bankList) {
        this.bankList = bankList;
    }

    public List<BankBranchBean> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<BankBranchBean> branchList) {
        this.branchList = branchList;
    }

    public List<EmploymentTypeBean> getEmpTypeList() {
        return empTypeList;
    }

    public void setEmpTypeList(List<EmploymentTypeBean> empTypeList) {
        this.empTypeList = empTypeList;
    }

    public List<IncomeTypeBean> getIncomeTypeList() {
        return incomeTypeList;
    }

    public void setIncomeTypeList(List<IncomeTypeBean> incomeTypeList) {
        this.incomeTypeList = incomeTypeList;
    }

    public List<EmploymentNatureBean> getNatureList() {
        return natureList;
    }

    public void setNatureList(List<EmploymentNatureBean> natureList) {
        this.natureList = natureList;
    }

    public List<OccupationBean> getOccupationList() {
        return occupationList;
    }

    public void setOccupationList(List<OccupationBean> occupationList) {
        this.occupationList = occupationList;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public List<DocumentUploadBean> getSessionDocumentList() {
        return sessionDocumentList;
    }

    public void setSessionDocumentList(List<DocumentUploadBean> sessionDocumentList) {
        this.sessionDocumentList = sessionDocumentList;
    }

    public List<CardTypeMgtBean> getCardTypeList() {
        return cardTypeList;
    }

    public void setCardTypeList(List<CardTypeMgtBean> cardTypeList) {
        this.cardTypeList = cardTypeList;
    }

    public int getSumOfIncome() {
        return sumOfIncome;
    }

    public void setSumOfIncome(int sumOfIncome) {
        this.sumOfIncome = sumOfIncome;
    }

    public List<CardCategoryBean> getCardCategoryLst() {
        return cardCategoryLst;
    }

    public void setCardCategoryLst(List<CardCategoryBean> cardCategoryLst) {
        this.cardCategoryLst = cardCategoryLst;
    }

    public String getCardCategory() {
        return cardCategory;
    }

    public void setCardCategory(String cardCategory) {
        this.cardCategory = cardCategory;
    }

    public List<AreaBean> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaBean> areaList) {
        this.areaList = areaList;
    }

    public SupplementaryApplicationBean getSuplimentoryPersonalBean() {
        return suplimentoryPersonalBean;
    }

    public void setSuplimentoryPersonalBean(SupplementaryApplicationBean suplimentoryPersonalBean) {
        this.suplimentoryPersonalBean = suplimentoryPersonalBean;
    }

    public List<String> getNationalityList() {
        return nationalityList;
    }

    public void setNationalityList(List<String> nationalityList) {
        this.nationalityList = nationalityList;
    }

    public NumberGenarationSearchBean getNumberGenaSearchBean() {
        return numberGenaSearchBean;
    }

    public void setNumberGenaSearchBean(NumberGenarationSearchBean numberGenaSearchBean) {
        this.numberGenaSearchBean = numberGenaSearchBean;
    }

    public List<NumberGenarationDataBean> getNumberGenaSearchLst() {
        return numberGenaSearchLst;
    }

    public void setNumberGenaSearchLst(List<NumberGenarationDataBean> numberGenaSearchLst) {
        this.numberGenaSearchLst = numberGenaSearchLst;
    }

    public String[] getIdArray() {
        return idArray;
    }

    public void setIdArray(String[] idArray) {
        this.idArray = idArray;
    }

    public List<VerificationCategoryBean> getVerificationCateLst() {
        return verificationCateLst;
    }

    public void setVerificationCateLst(List<VerificationCategoryBean> verificationCateLst) {
        this.verificationCateLst = verificationCateLst;
    }

    public List<DocumentTypeBean> getDocumentTypeLst() {
        return documentTypeLst;
    }

    public void setDocumentTypeLst(List<DocumentTypeBean> documentTypeLst) {
        this.documentTypeLst = documentTypeLst;
    }

    public CustomerSearchBean getCustomerSearchBean() {
        return customerSearchBean;
    }

    public void setCustomerSearchBean(CustomerSearchBean customerSearchBean) {
        this.customerSearchBean = customerSearchBean;
    }

    public MerchantSearchBean getMerchantSearchBean() {
        return merchantSearchBean;
    }

    public void setMerchantSearchBean(MerchantSearchBean merchantSearchBean) {
        this.merchantSearchBean = merchantSearchBean;
    }

    public List<ApplicationAssignBean> getUserAssignDebitAppList() {
        return userAssignDebitAppList;
    }

    public void setUserAssignDebitAppList(List<ApplicationAssignBean> userAssignDebitAppList) {
        this.userAssignDebitAppList = userAssignDebitAppList;
    }
    ////////////////////////janaka///////////////////////////////////////////
    ////////////////////////janaka///////////////////////////////////////////
    private Map<SectionBean, List<PageBean>> adminSectionPageList;
    private Map<SectionBean, List<PageBean>> acqcallSectionPageList;
    private Map<SectionBean, List<PageBean>> backofficeSectionPageList;
    private Map<SectionBean, List<PageBean>> callcenterSectionPageList;
    private Map<SectionBean, List<PageBean>> cardembossSectionPageList;
    private Map<SectionBean, List<PageBean>> cardmgtSectionPageList;
    private Map<SectionBean, List<PageBean>> explorerSectionPageList;
    private Map<SectionBean, List<PageBean>> merterSectionPageList;
    private Map<SectionBean, List<PageBean>> personalSectionPageList;
    private Map<SectionBean, List<PageBean>> reportexpSectionPageList;
    private Map<SectionBean, List<PageBean>> switchSectionPageList;
    private Map<SectionBean, List<PageBean>> issuingAdminSectionPageList;
    private Map<SectionBean, List<PageBean>> applicationProcessingSectionPageList;
    private Map<SectionBean, List<PageBean>> issuingPersonalizeSectionPageList;
    private Map<SectionBean, List<PageBean>> productServicesSectionPageList;
    private Map<SectionBean, List<PageBean>> securityServicesSectionPageList;
    private Map<SectionBean, List<PageBean>> transactionFinanceSectionPageList;
    private Map<SectionBean, List<PageBean>> monitoringReportingSectionPageList;
    private Map<SectionBean, List<PageBean>> collectionRecoverySectionPageList;
    private Map<SectionBean, List<PageBean>> customerServiceSectionPageList;
    private Map<SectionBean, List<PageBean>> issuerServiceSectionPageList;
    private Map<SectionBean, List<PageBean>> backOfficeSectionPageList;
    private Map<SectionBean, List<PageBean>> commonConfigSectionPageList;
    private Map<SectionBean, List<PageBean>> merchantMgtSectionPageList;
    private Map<SectionBean, List<PageBean>> terminalMgtSectionPageList;
    private Map<SectionBean, List<PageBean>> aqProductServicesPageList;
    private Map<SectionBean, List<PageBean>> aqCustomerServiceSectionPageList;
    private Map<SectionBean, List<PageBean>> merchantServiceSectionPageList;
    private Map<SectionBean, List<PageBean>> aqtransactionFinanceSectionPageList;
    private Map<SectionBean, List<PageBean>> aqBackOfficeSectionPageList;
    private Map<SectionBean, List<PageBean>> aqSecurityServiceSectionPageList;
    private Map<SectionBean, List<PageBean>> aqMonitoringReportingSectionPageList;
    private Map<SectionBean, List<PageBean>> switchControlSectionPageList;
    private Map<SectionBean, List<PageBean>> aquirerConfigSectionPageList;
    private Map<SectionBean, List<PageBean>> transactionProccesingSectionPageList;
    private Map<SectionBean, List<PageBean>> recoverySectionPageList;

    public Map<SectionBean, List<PageBean>> getAcqcallSectionPageList() {
        return acqcallSectionPageList;
    }

    public void setAcqcallSectionPageList(Map<SectionBean, List<PageBean>> acqcallSectionPageList) {
        this.acqcallSectionPageList = acqcallSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getAdminSectionPageList() {
        return adminSectionPageList;
    }

    public void setAdminSectionPageList(Map<SectionBean, List<PageBean>> adminSectionPageList) {
        this.adminSectionPageList = adminSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getBackofficeSectionPageList() {
        return backofficeSectionPageList;
    }

    public void setBackofficeSectionPageList(Map<SectionBean, List<PageBean>> backofficeSectionPageList) {
        this.backofficeSectionPageList = backofficeSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getCallcenterSectionPageList() {
        return callcenterSectionPageList;
    }

    public void setCallcenterSectionPageList(Map<SectionBean, List<PageBean>> callcenterSectionPageList) {
        this.callcenterSectionPageList = callcenterSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getCardembossSectionPageList() {
        return cardembossSectionPageList;
    }

    public void setCardembossSectionPageList(Map<SectionBean, List<PageBean>> cardembossSectionPageList) {
        this.cardembossSectionPageList = cardembossSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getCardmgtSectionPageList() {
        return cardmgtSectionPageList;
    }

    public void setCardmgtSectionPageList(Map<SectionBean, List<PageBean>> cardmgtSectionPageList) {
        this.cardmgtSectionPageList = cardmgtSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getExplorerSectionPageList() {
        return explorerSectionPageList;
    }

    public void setExplorerSectionPageList(Map<SectionBean, List<PageBean>> explorerSectionPageList) {
        this.explorerSectionPageList = explorerSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getMerterSectionPageList() {
        return merterSectionPageList;
    }

    public void setMerterSectionPageList(Map<SectionBean, List<PageBean>> merterSectionPageList) {
        this.merterSectionPageList = merterSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getPersonalSectionPageList() {
        return personalSectionPageList;
    }

    public void setPersonalSectionPageList(Map<SectionBean, List<PageBean>> personalSectionPageList) {
        this.personalSectionPageList = personalSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getReportexpSectionPageList() {
        return reportexpSectionPageList;
    }

    public void setReportexpSectionPageList(Map<SectionBean, List<PageBean>> reportexpSectionPageList) {
        this.reportexpSectionPageList = reportexpSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getSwitchSectionPageList() {
        return switchSectionPageList;
    }

    public void setSwitchSectionPageList(Map<SectionBean, List<PageBean>> switchSectionPageList) {
        this.switchSectionPageList = switchSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getIssuingAdminSectionPageList() {
        return issuingAdminSectionPageList;
    }

    public void setIssuingAdminSectionPageList(Map<SectionBean, List<PageBean>> issuingAdminSectionPageList) {
        this.issuingAdminSectionPageList = issuingAdminSectionPageList;
    }

    ////////////////////////janaka///////////////////////////////////////////
    ////////////////////////janaka///////////////////////////////////////////
    public Map<SectionBean, List<PageBean>> getApplicationProcessingSectionPageList() {
        return applicationProcessingSectionPageList;
    }

    public void setApplicationProcessingSectionPageList(Map<SectionBean, List<PageBean>> applicationProcessingSectionPageList) {
        this.applicationProcessingSectionPageList = applicationProcessingSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getIssuingPersonalizeSectionPageList() {
        return issuingPersonalizeSectionPageList;
    }

    public void setIssuingPersonalizeSectionPageList(Map<SectionBean, List<PageBean>> issuingPersonalizeSectionPageList) {
        this.issuingPersonalizeSectionPageList = issuingPersonalizeSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getProductServicesSectionPageList() {
        return productServicesSectionPageList;
    }

    public void setProductServicesSectionPageList(Map<SectionBean, List<PageBean>> productServicesSectionPageList) {
        this.productServicesSectionPageList = productServicesSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getSecurityServicesSectionPageList() {
        return securityServicesSectionPageList;
    }

    public void setSecurityServicesSectionPageList(Map<SectionBean, List<PageBean>> securityServicesSectionPageList) {
        this.securityServicesSectionPageList = securityServicesSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getTransactionFinanceSectionPageList() {
        return transactionFinanceSectionPageList;
    }

    public void setTransactionFinanceSectionPageList(Map<SectionBean, List<PageBean>> transactionFinanceSectionPageList) {
        this.transactionFinanceSectionPageList = transactionFinanceSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getMonitoringReportingSectionPageList() {
        return monitoringReportingSectionPageList;
    }

    public void setMonitoringReportingSectionPageList(Map<SectionBean, List<PageBean>> monitoringReportingSectionPageList) {
        this.monitoringReportingSectionPageList = monitoringReportingSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getCollectionRecoverySectionPageList() {
        return collectionRecoverySectionPageList;
    }

    public void setCollectionRecoverySectionPageList(Map<SectionBean, List<PageBean>> collectionRecoverySectionPageList) {
        this.collectionRecoverySectionPageList = collectionRecoverySectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getCustomerServiceSectionPageList() {
        return customerServiceSectionPageList;
    }

    public void setCustomerServiceSectionPageList(Map<SectionBean, List<PageBean>> customerServiceSectionPageList) {
        this.customerServiceSectionPageList = customerServiceSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getIssuerServiceSectionPageList() {
        return issuerServiceSectionPageList;
    }

    public void setIssuerServiceSectionPageList(Map<SectionBean, List<PageBean>> issuerServiceSectionPageList) {
        this.issuerServiceSectionPageList = issuerServiceSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getBackOfficeSectionPageList() {
        return backOfficeSectionPageList;
    }

    public void setBackOfficeSectionPageList(Map<SectionBean, List<PageBean>> backOfficeSectionPageList) {
        this.backOfficeSectionPageList = backOfficeSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getCommonConfigSectionPageList() {
        return commonConfigSectionPageList;
    }

    public void setCommonConfigSectionPageList(Map<SectionBean, List<PageBean>> commonConfigSectionPageList) {
        this.commonConfigSectionPageList = commonConfigSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getAqBackOfficeSectionPageList() {
        return aqBackOfficeSectionPageList;
    }

    public void setAqBackOfficeSectionPageList(Map<SectionBean, List<PageBean>> aqBackOfficeSectionPageList) {
        this.aqBackOfficeSectionPageList = aqBackOfficeSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getAqCustomerServiceSectionPageList() {
        return aqCustomerServiceSectionPageList;
    }

    public void setAqCustomerServiceSectionPageList(Map<SectionBean, List<PageBean>> aqCustomerServiceSectionPageList) {
        this.aqCustomerServiceSectionPageList = aqCustomerServiceSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getAqMonitoringReportingSectionPageList() {
        return aqMonitoringReportingSectionPageList;
    }

    public void setAqMonitoringReportingSectionPageList(Map<SectionBean, List<PageBean>> aqMonitoringReportingSectionPageList) {
        this.aqMonitoringReportingSectionPageList = aqMonitoringReportingSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getAqProductServicesPageList() {
        return aqProductServicesPageList;
    }

    public void setAqProductServicesPageList(Map<SectionBean, List<PageBean>> aqProductServicesPageList) {
        this.aqProductServicesPageList = aqProductServicesPageList;
    }

    public Map<SectionBean, List<PageBean>> getAqSecurityServiceSectionPageList() {
        return aqSecurityServiceSectionPageList;
    }

    public void setAqSecurityServiceSectionPageList(Map<SectionBean, List<PageBean>> aqSecurityServiceSectionPageList) {
        this.aqSecurityServiceSectionPageList = aqSecurityServiceSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getAqtransactionFinanceSectionPageList() {
        return aqtransactionFinanceSectionPageList;
    }

    public void setAqtransactionFinanceSectionPageList(Map<SectionBean, List<PageBean>> aqtransactionFinanceSectionPageList) {
        this.aqtransactionFinanceSectionPageList = aqtransactionFinanceSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getAquirerConfigSectionPageList() {
        return aquirerConfigSectionPageList;
    }

    public void setAquirerConfigSectionPageList(Map<SectionBean, List<PageBean>> aquirerConfigSectionPageList) {
        this.aquirerConfigSectionPageList = aquirerConfigSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getMerchantMgtSectionPageList() {
        return merchantMgtSectionPageList;
    }

    public void setMerchantMgtSectionPageList(Map<SectionBean, List<PageBean>> merchantMgtSectionPageList) {
        this.merchantMgtSectionPageList = merchantMgtSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getMerchantServiceSectionPageList() {
        return merchantServiceSectionPageList;
    }

    public void setMerchantServiceSectionPageList(Map<SectionBean, List<PageBean>> merchantServiceSectionPageList) {
        this.merchantServiceSectionPageList = merchantServiceSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getSwitchControlSectionPageList() {
        return switchControlSectionPageList;
    }

    public void setSwitchControlSectionPageList(Map<SectionBean, List<PageBean>> switchControlSectionPageList) {
        this.switchControlSectionPageList = switchControlSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getTerminalMgtSectionPageList() {
        return terminalMgtSectionPageList;
    }

    public void setTerminalMgtSectionPageList(Map<SectionBean, List<PageBean>> terminalMgtSectionPageList) {
        this.terminalMgtSectionPageList = terminalMgtSectionPageList;
    }

    public Map<SectionBean, List<PageBean>> getTransactionProccesingSectionPageList() {
        return transactionProccesingSectionPageList;
    }

    public void setTransactionProccesingSectionPageList(Map<SectionBean, List<PageBean>> transactionProccesingSectionPageList) {
        this.transactionProccesingSectionPageList = transactionProccesingSectionPageList;
    }

    public List<CardProductBean> getCardProductBinBeanLst() {
        return cardProductBinBeanLst;
    }

    public void setCardProductBinBeanLst(List<CardProductBean> cardProductBinBeanLst) {
        this.cardProductBinBeanLst = cardProductBinBeanLst;
    }

    public String getIsBack() {
        return isBack;
    }

    public void setIsBack(String isBack) {
        this.isBack = isBack;
    }

    public HashMap<String, String> getCardCategoryMap() {
        return cardCategoryMap;
    }

    public void setCardCategoryMap(HashMap<String, String> cardCategoryMap) {
        this.cardCategoryMap = cardCategoryMap;
    }

    public List<AreaBeanCoCustomer> getAreaListCoCustomer() {
        return areaListCoCustomer;
    }

    public void setAreaListCoCustomer(List<AreaBeanCoCustomer> areaListCoCustomer) {
        this.areaListCoCustomer = areaListCoCustomer;
    }

    public HashMap<String, String> getIdentityTypeListCoCustomer() {
        return identityTypeListCoCustomer;
    }

    public void setIdentityTypeListCoCustomer(HashMap<String, String> identityTypeListCoCustomer) {
        this.identityTypeListCoCustomer = identityTypeListCoCustomer;
    }

    public List<String> getNationalityCoCustomerList() {
        return nationalityCoCustomerList;
    }

    public void setNationalityCoCustomerList(List<String> nationalityCoCustomerList) {
        this.nationalityCoCustomerList = nationalityCoCustomerList;
    }

    public List<CustomerCorporateBean> getSessionBankDetailCoCustList() {
        return sessionBankDetailCoCustList;
    }

    public void setSessionBankDetailCoCustList(List<CustomerCorporateBean> sessionBankDetailCoCustList) {
        this.sessionBankDetailCoCustList = sessionBankDetailCoCustList;
    }

    public List<VerificationCategoryCorporateBean> getVerificatioCatCoList() {
        return verificatioCatCoList;
    }

    public void setVerificatioCatCoList(List<VerificationCategoryCorporateBean> verificatioCatCoList) {
        this.verificatioCatCoList = verificatioCatCoList;
    }

    public List<DocumentUploadCorporateBean> getSessionDocumentCoList() {
        return sessionDocumentCoList;
    }

    public void setSessionDocumentCoList(List<DocumentUploadCorporateBean> sessionDocumentCoList) {
        this.sessionDocumentCoList = sessionDocumentCoList;
    }

    public void setApplicationVerificationBean(ApplicationVerificationBean applicationVerificationBean) {
        this.applicationVerificationBean = applicationVerificationBean;
    }

    public ApplicationVerificationBean getApplicationVerificationBean() {
        return applicationVerificationBean;
    }

    public Map<SectionBean, List<PageBean>> getRecoverySectionPageList() {
        return recoverySectionPageList;
    }

    public void setRecoverySectionPageList(Map<SectionBean, List<PageBean>> recoverySectionPageList) {
        this.recoverySectionPageList = recoverySectionPageList;
    }

    public List<AssetLiabilityTypeBean> getAssetsLiabilityTypeList() {
        return assetsLiabilityTypeList;
    }

    public void setAssetsLiabilityTypeList(List<AssetLiabilityTypeBean> assetsLiabilityTypeList) {
        this.assetsLiabilityTypeList = assetsLiabilityTypeList;
    }

    public List<AssetBean> getAssetsList() {
        return assetsList;
    }

    public void setAssetsList(List<AssetBean> assetsList) {
        this.assetsList = assetsList;
    }

    public List<LiabilityBean> getLiabilityList() {
        return liabilityList;
    }

    public void setLiabilityList(List<LiabilityBean> liabilityList) {
        this.liabilityList = liabilityList;
    }

    public List<EstablishmentLiabilityBean> getEstablishmentLiabilityList() {
        return establishmentLiabilityList;
    }

    public void setEstablishmentLiabilityList(List<EstablishmentLiabilityBean> establishmentLiabilityList) {
        this.establishmentLiabilityList = establishmentLiabilityList;
    }

    public List<EstablishmentAssetsBean> getEstablishmentAssetList() {
        return establishmentAssetList;
    }

    public void setEstablishmentAssetList(List<EstablishmentAssetsBean> establishmentAssetList) {
        this.establishmentAssetList = establishmentAssetList;
    }

    public String getCallcenterSearchAppId() {
        return callcenterSearchAppId;
    }

    public void setCallcenterSearchAppId(String callcenterSearchAppId) {
        this.callcenterSearchAppId = callcenterSearchAppId;
    }

    public String getCallcenterSearchCardCategoty() {
        return callcenterSearchCardCategoty;
    }

    public void setCallcenterSearchCardCategoty(String callcenterSearchCardCategoty) {
        this.callcenterSearchCardCategoty = callcenterSearchCardCategoty;
    }

    public String getCallcenterSearchCardCardNo() {
        return callcenterSearchCardCardNo;
    }

    public void setCallcenterSearchCardCardNo(String callcenterSearchCardCardNo) {
        this.callcenterSearchCardCardNo = callcenterSearchCardCardNo;
    }
    

    public BTPaymentConfirmBean getBtreqBean() {
        return btreqBean;
    }

    public void setBtreqBean(BTPaymentConfirmBean btreqBean) {
        this.btreqBean = btreqBean;
    }

    public List<BTPaymentConfirmBean> getBtreqBeanList() {
        return btreqBeanList;
    }

    public void setBtreqBeanList(List<BTPaymentConfirmBean> btreqBeanList) {
        this.btreqBeanList = btreqBeanList;
    }    

    public List<LoanOnCardPayConfirmBean> getLoanoncardreqBeanList() {
        return loanoncardreqBeanList;
    }

    public void setLoanoncardreqBeanList(List<LoanOnCardPayConfirmBean> loanoncardreqBeanList) {
        this.loanoncardreqBeanList = loanoncardreqBeanList;
    }

    public LoanOnCardPayConfirmBean getLocreqBean() {
        return locreqBean;
    }

    public void setLocreqBean(LoanOnCardPayConfirmBean locreqBean) {
        this.locreqBean = locreqBean;
    }
    
    
}
