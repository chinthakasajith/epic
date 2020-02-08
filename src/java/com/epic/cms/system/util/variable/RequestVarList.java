/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.variable;

/**
 *this class use to wrote all the variable that relate to request
 * @author ayesh
 */
public class RequestVarList {
    //------- credit score

    public static final String CREDITSCORE_FIELD_DEFINE_FORMLIST = "formList";
    public static final String CREDITSCORE_FIELD_DEFINE_LIST = "allList";
    public static final String CREDITSCORE_FIELD_DEFINE_DATABEAN = "bean";
    public static final String CREDITSCORE_FIELD_DEFINE_FIELDLIST = "fieldList";
    public static final String CREDITSCORE_FIELD_DEFINE_FORM = "formName";
    //////
    //fee profile
    public static final String FEEPROFILE_ADD_BEAN = "feeBean";
    public static final String FEEPROFILE_INFO_LIST = "feeBeanList";
    public static final String FEEPROFILE_STATUS = "proStatus";
    public static final String FEEPROFILE_CURRENCY_LIST = "proCurrency";
    //---
    //--------Fee Management
    
    public static final String FEEMGT_LIST = "feeList";
    
    //--country mgt ---
    public static final String COUNTRYMGT_LIST = "countryList";
    //----
    
    //---------Merchant Category ---------------------
    public static final String MERCHANTMGT_LIST = "merchantList";
    public static final String MERCHANTCATEGORY_LIST = "merchantCategoryList";
    public static final String MERCHANTCUSTOMER_LIST = "merchantCustomerList";
    //------------------------------------------------
    //-- exchange rate ----
    public static final String EXCHANGE_DETAILS = "allExList";
    public static final String EXCHANGE_CURRENCY = "exchList";
    public static final String EXCHANGE_BEAN = "exBean";
    //---
    ////////////////////password policy-ayesh
    public static final String PASSWORDPOLICY_DETAILS_BEAN = "passwdBean";
    public static final String PASSWORDPOLICY_UPDATE_BEAN = "passwd";
    //----
    //application mgt---
    public static final String APPLICTION_ADD_BEAN = "addApp";
    public static final String APPBEAN = "appBean";
    //----
    // --section mgt ---
    public static final String SECTION_STATUS_LIST = "secList";
    public static final String SECTION_DETAILS_LIST = "allsecList";
    public static final String SECTION_ADD_BEAN = "secBean";
    //------
    //----score card
    public static final String CARDSCORE_RULE_LIST = "ruleList";
    public static final String CARDSCORE_BEAN = "scoreBean";
    public static final String CARDSCORE_DETAILS_LIST = "allList";
    public static final String CARDSCORE_ASSIGNEDRULE_LIST = "asignedRule";
    //---
    //-----document type
    public static final String DOCUMENT_TYPE_ALLLIST = "docAllList";
    public static final String DOCUMENT_TYPE_BEAN = "dataBean";
    public static final String DOCUMENT_VERIFYCAT_LIST = "catList";
    public static final String DOCUMENT_CARD_CAT_LIST = "cardCatList";
    //-----------------
    //------credit score-----------
    public static final String CREDIT_SCORE_PRIO_LEVEL = "prioList";
    public static final String BRANCH_MGT_BANKLIST = "bankList";
    public static final String BRANCH_DATA_BEAN = "branchBean";
    public static final String BRANCH_DATA_ALL = "branchAllList";
    public static final String BRANCH_BANK_BEAN = "bankBean";
    //-----------------
    //----
    public static final String CARDTYPESTATUS = "card";
    public static final String CARDTYPE_DATA_BEAN = "databean";
    public static final String CARDTYPE_ALL_LIST = "cardAllList";
    //----
    
    //-------------
    public static final String RECOMMEND_CARDTYPE_LIST = "cardtypeList";
    public static final String RECOMMEND_CARDPRODUCT_LIST = "cardproductList";
    public static final String RECOMMEND_ALL_LIST = "recomList";
    public static final String RECOMMEND_DATA_BEAN = "recomBean";
//---------------
    
    //--application reject reason
    public static final String APP_REJECT_REASON_ALLLIST = "appDataList";
    public static final String APP_REJECT_REASON_DATABEAN = "appDataBean";
    
    
    //----
    
    //----credit score calculate 
    public static final String PRIORITY_LEVEL_DATA_LIST = "prioList";
    public static final String USER_LIST = "userList";
    public static final String DATA_LIST = "searchDataList";
    public static final String CREDIT_CAL_DATA_BEAN = "calBean";
    //--------------------------
    
    //-- transaction type management
    
    public static final String TXNTYPEMGT_LIST="typeList";
    
    //----------------------------
    
    //-----------------Channel Configuration------------------------------
    
     public static final String CHANNEL_LIST="channelList";
     public static final String STATUS_EDTYPE_LIST="statusEDList";
     
     //------------Risk Management---------------------------
     
     public static final String RISKMGT_LIST="riskProfList";
     public static  final String TERMINAL_RISKPROFILE_LIST = "riskProfileList";
     
     //-------------Currency Management
     public static final String CURRENCY_LIST = "currencyList";
     
     //--------------Service Code Managemant
     public static final String SERVICE_LIST = "serviceList";
     
     //---------------- Log Management--------------
    public static final String LOGLEVEL_LIST = "logLevelList";
    
    //---------------Responce Code Mgt------------
    public static final String RESPONCE_LIST = "responceList";
    public static final String MAPPING_LIST="mappingList";
    
    // ---------- Eod Process Category List
    
    public static final String CATEGORY_LIST = "categoryList";
    
    // ---------- Commission Profile List
    public static final String COMMISSSION_PROFILE_LIST = "comisList";
    public static final String COMMISSSION_TXN_LIST = "comisTxnList";
            
    // ------------Common Configuration Management
    public static final String COMMON_CONFIGURATION = "commonConfiguration";
    public static final String CC_BANK_LIST = "bankList";
    public static final String CC_COUNTRY_LIST = "countryList";
    public static final String CC_CURRENCY_LIST = "currencyList";
    public static final String CC_BANK = "bank";
    public static final String CC_COUNTRY = "country";
    public static final String CC_CURRENCY = "currency";
    public static final String CC_MERCHANT_ID = "merchant_id";
    public static final String CC_TERMINAL_ID = "terminal_id";
    public static final String CC_ACCUMULATION_POINT_VALUE = "accumulationPointValue";
    public static final String CC_REDEMPTION_POINT_VALUE = "redemptionPointValue";
    public static final String CC_BATCH_TIMEOUT = "batchTimeout";
    public static final String CC_MAX_PER_PAYMENT_AMOUNT = "maxPerPaymentAmount";
    public static final String CC_MAX_PER_BATCH_AMOUNT = "maxPerBatchAmount";
    public static final String CC_USER_ROLE_LIST="userRoleList";
    public static final String CC_USER_ROLE="dataCaptureRole";
    
    // ------------Transaction Adjustment
    public static final String TA_CURRENCY_LIST = "currencyList";;
    public static final String TA_CARD_TRANSACTION_ADJUSTMENTS = "cardTransactionAdjustments";
    public static final String TA_STATUS_LIST = "cardTransactionStatus";
    public static String TA_MERCHANT_TRANSACTION_ADJUSTMENTS = "merchantTransactionAdjustments";
    
    // ------------Batch Management
    public static final String BM_STATUS_LIST = "batchManagenentStatus";
    public static final String BM_ALL_BATCHES = "allBatches";
    public static final String BMS_STATUS ="status";
    public static final String BMS_UNAME ="username";
    public static final String BMS_BRANCH ="branch";
    public static final String BMS_ID ="batch_id";
    public static final String BMS_FDATE ="fromDate";
    public static final String BMS_TDATE ="toDate";
    public static final String BM_BRANCH_LIST = "branches";
    public static final String BM_USERS_LIST = "users";

}
