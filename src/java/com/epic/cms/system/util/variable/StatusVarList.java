/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.variable;

/**
 *
 * @author upul
 */
public class StatusVarList {

    public static final String ASSIGN_STATUSCODE = "ASGN";
    public static final String DATA_CAPTURE_STATUS = "DTEN";
    //active status for controlpanel
    public static final String ACTIVE_STATUS = "ACT";
    public static final String ACTIVE_STATUS_CODE = "1";

    public static final String INACTIVE_STATUS = "IACT";
    //Deactive status for controlpanel
    public static final String DEACTIVE_STATUS = "DEA";
    public static final String DEACTIVE_STATUS_CODE = "2";

    public static final String DELETE_STATUS = "DEL";
    public static final String BLOCK_STATUS = "TBLK";
    public static final String STATUS_DEACTIVE = "DEA";
    //initial status for card applicaton assign
    public static final String INITIAL_STATUS = "INIT";
    //reject status for card applicaton assign
    public static final String REJECT_STATUS = "IREJ";
    //complete status for card applicaton assign
    public static final String COMPLETE_STATUS = "COM";
    public static final String ACCEPT = "CCOMP";
    public static final String CHECKIN = "CHEIN";
    public static final String YESSTATUS = "YES";
    public static final String NOSTATUS = "NO";
//    application status
    public static final String APP_INITIATE = "INIT";
    public static final String APP_PROCESS = "DPRC";
    public static final String APP_REJECT = "IREJ";
    public static final String APP_FILLING_COMPLETE = "DCOM";
    public static final String BULK_FILLING_COMPLETE = "BDCC";
    public static final String APP_CHECKOUT = "COUT";
    public static final String APP_CHECK_COMPLETE = "CCOM";
    public static final String APP_CHECKIN = "CHIN";
    public static final String APP_VERIFY_PROCESS = "VPRO";
    public static final String APP_VERIFY_COMPELTE = "VCOM";
    public static final String APP_SUPP_VERIFY_COMPELTE = "SVCO";
    public static final String APP_FIXED_VERIFY_COMPELTE = "FVCO";
    public static final String APP_CORPORATE_VERIFY_COMPELTE = "CVCO";
    public static final String APP_ONHOLD = "VONH";
    public static final String APP_VERIFY_REJECT = "VREJ";
    public static final String APP_SCORE_COMPLATE = "SCOM";
    public static final String APP_APPROVE_COMPLETE = "ACOM";
    public static final String APP_APPROVE_REJECT = "AREJ";
    public static final String APP_NUMBER_COMPLETE = "NCOM";
    public static final String APP_CARD_DISTRIBUTION = "CDIS";
    public static final String APP_CREDIT_OFFICER_REVIEW_COMPLETE = "CORC";
    ////////////////////////////////////////////////////////////////////////////
    public static final String DOC_CAT_RECOMENDATION_LETTER = "RCLTT";
    public static final String DOC_CAT_RECOMENDATION_LETTER_DOCUMENTTYPE = "LETTER";
    public static final String GENESTATUCAT = "GENR";
    //// //status catogery ode
    public static final String GENESTATUSCAT = "GENR";
    public static final String COMMONSTATUSCAT = "COMN";
    public static final String APPLICATIONSTATUSCAT = "ASGN";
    ////////////////////
    public static final String CARD_CATEGORY_MAIN = "M";
    public static final String CARD_CATEGORY_SUPPLEMENTARY = "S";
    public static final String CARD_CATEGORY_COPORATE = "C";
    public static final String CARD_CATEGORY_ESTABLISHMENT = "E";
    public static final String CARD_DEBIT_PERSONAL = "P";
    public static final String CARD_DEBIT_JOINT = "J";
    public static final String CARD_AGAINST_FD_CODE = "F";
    public static final String CARD_APP_CATEGORY_ESTABLISMENT_CODE = "E";
    //switch control panel prifix
    public static final String ZMK_PREFIX = "010507";
    public static final String AWK_PREFIX = "010504";
    public static final String IWK_PREFIX = "010505";
    public static final String CSCK_PREFIX = "010503";
    public static final String DMK_PREFIX = "010506";
    public static final String ZCMK_PREFIX = "010507";
    public static final String IMK_AC_PREFIX = "010508";
    public static final String PPK_PREFIX = "010506";
    public static final String CVVA_B_PREFIX = "010501";
    public static final String CVVA_A_PREFIX = "010501";
    public static final String PVVA_B_PREFIX = "010502";
    public static final String PVVA_A_PREFIX = "010502";
    public static final String PVK_PREFIX = "010502";
    public static final String CVK_PREFIX = "010501";
    public static final String ICVK_PREFIX = "010501";
    public static final String IMKSMI_PREFIX = "010509";
    public static final String IMKSMC_PREFIX = "010510";

    public static final String SWITCH_KEY_ID = "1";
    public static final String CHANEL_TYPE_VISA = "5";
    public static final String CHANEL_TYPE_MASTER = "3";
    public static final String CHANEL_TYPE_ATM = "1";
    public static final String CHANEL_TYPE_VISA_PLUS = "9";
    public static final String CHANEL_TYPE_MAESTRO = "10";
    public static final String CHANEL_TYPE_AMEX = "6";
    public static final String CHANEL_TYPE_CIRRUS = "17";
    public static final String CHANEL_TYPE_CREADIT_PROPR = "18";
    // prifix for HSM 
    public static final String KEY_TYPE_KTM = "05";
    public static final String KEY_MAILER_PRIFIX = "0106";
    public static final String WORKING_KEY_PRIFIX = "0107";

    //Listener Key Mailing-prifix for HSM 
    public static final String KEY_MAILINGTYPE_KTM = "02";
    public static final String KEY_MAILING_PREFIX = "010605";

    //Message fomats to ETM Router
    public static final String SERVER_STATUS = "01010000";
    public static final String SERVER_RESTART = "01010100";
    public static final String SERVER_SHUTDOWN = "01010200";
    public static final String SERVER_CLEAR_ALL_LOGS = "01010300";
    public static final String SERVER_LOGS_BACKUP = "01010400";
    public static final String SERVER_CLEAR_LOGS = "01010500";
    public static final String SERVER_REMOVE_BACKUP_LOGS = "01010600";
    public static final String SERVER_SESSION_RESTART = "01010700";
    public static final String SERVER_STOP = "01010800";
    public static final String SERVER_START = "01010900";
    public static final String SERVER_SIGNON = "01011000";
    // Main card category
    public static final String MAIN = "Main";
    public static final String SUPLIMENTORY = "Supplementary";
    public static final String CARD_AGAINST_FD = "Card against FD";
    public static final String ESTABLISHMENT="Establishment";
    // Main card category
    public static final String ALLOCATION_YES = "ALYES";
    public static final String ALLOCATION_NO = "ALNO";
    public static final String PINSTATUS_YES = "PYES";
    public static final String PINSTATUS_NO = "PNO";
    public static final String PIN_MAIL_STATUS_NO = "PMNO";
    public static final String PIN_MAIL_STATUS_YES = "PMYS";
    //card status
    public static final String CARD_INIT = "CINT";
    public static final String CARD_ACTIVE = "CACT";
    //risk profile
    public static final String CUSTOMER_RISK_PROFILE = "RPCUS";
    public static final String MERCHANT_RISK_PROFILE = "RPMER";
    public static final String TERMINAL_RISK_PROFILE = "RPTER";
    public static final String ACCOUNT_RISK_PROFILE = "RPACT";
    public static final String CARD_RISK_PROFILE = "RPCRD";

    public static final String TERMINAL_BLOCK = "TBLK";
    public static final String CREDIT = "CREDIT";
    public static final String DEBIT = "DEBIT";
    public static final String CARDTYPE_VISA = "VISA";
    public static final String CARDTYPE_MASTER = "MAST";
    public static final String HISTORY_COMPLETE = "HCOM";
    public static final String HISTORY_INCOMPLETE = "HFAL";
    public static final String CARD_REISSUE = "CDRI";
    public static final String CARD_REPLACE = "CDRP";
    public static final String CARD_ACTIVATION_REQ = "ACTI";
    public static final String PIN_REISSUE = "PIRI";
    public static final String CARD_RENEW_PROCESS = "PRC";
    public static final String CARD_RENEW_COMPLETE = "RCOM";
    // status for online db
    public static final int ONLINE_ACTIVE_STATUS = 1;
    public static final int ONLINE_DEACTIVE_STATUS = 2;
    public static final int ONLINE_BLOCKED_STATUS = 3;
    public static final int ONLINE_TXN_SETTLED_STATUS = 13;
    // Bulk Card Management
    public static final String BULK_CARD_REQUEST_PENDING = "BCRP";
    public static final String BULK_CARD_REQUEST_DELETE = "BCRD";
    public static final String BULK_CARD_REQUEST_CONFIRM = "BCRC";
    public static final String BULK_CARD_REQUEST_REJECT = "BCRR";
    public static final String BULK_CARD_PIN_GENE_COMPLETE = "BCPC";
    public static final String BULK_CARD_PIN_GENE_PENDING = "BCPP";
    public static final String BULK_CARD_PIN_MAIL_PENDING = "BCMP";
    public static final String BULK_CARD_PIN_MAIL_COMPLETE = "BCMC";
    public static final String BULK_CARD_NUMBER_GENERATION_COMPLETE = "BCNC";
    public static final String BULK_CARD_NUMBER_GENERATION_PENDING = "BCNP";
    public static final String BULK_CARD_ENCODING_COMPLETE = "BCEC";
    public static final String BULK_CARD_DISTRIBUTION_COMPLETE = "BCDC";
    public static final String BULK_CARD_DATA_CAPTURE_COMPLETE = "BDCC";
    public static final String ID_VERIFICATION_CATEGORY = "ID";
    public static final String JOINT_ID_VERIFICATION_CATEGORY = "JID";
    public static final String NATIONAL_IDENTITY_CARD = "NIC";
    public static final String NATIONAL_IDENTITY_CARD_FXD = "NIC_FXD";
    public static final String NATIONAL_IDENTITY_CARD_SUP = "NIC_SUP";
    public static final String NATIONAL_IDENTITY_CARD_COP = "NIC_COP";

    public static final String PASSPORT = "PAS";
    public static final String DRIVING_LICENSE = "DRL";
    ///////////Payment Mode Status
    public static final String ACQUIRE_PAYMENT_MODE = "ACQ";
    public static final String SLIPS = "P0001";
    public static final String DIRECT_ACCOUNT = "P0002";
    public static final String CHEQUE = "P0003";
    /////////////////////////////Manual Transaction
//    public static final String HOST_IP = "192.168.1.91";
    public static final String HOST_IP = "192.168.20.32";
    public static final int HOST_PORT = 6000; //6400; // 5005
//    public static final int HOST_PORT = 3456; //6400; // 5005
    public static final String NIT = "787";
    public static final int TIMEOUT = 10000;
    public static final boolean DEBUGLEVEL = true;
    public static final String MANUAL_TXN_SUCCESS_STATUS = "00";
    public static final int ONLINE_TXN_COMPLETE_STATUS = 20;
    public static final int ONLINE_TXN_INITIATE_STATUS = 27;
    public static final int ONLINE_TXN_VOID_STATUS = 10;
    public static final int ONLINE_TXN_SETTLE_FAILD_STATUS = 11;
    public static final int ONLINE_TXN_BATCH_UPLOAD_STATUS = 12;
    ///////////////////////////profile id
    public static final int ATM_PROFILE_ID = 1;
    public static final String LISTNER_COMMUNICATION_TYPE = "2";
    public static final String CHANNEL_COMMUNICATION_TYPE = "1";

    //File Upload 
    public static final String FINIT = "FINIT";

    public static final String EMVC_CHIP_CARD = "EMVC";
    public static final String MAGNATIC_STRIPE_CARD = "MGTC";
    public static final String VIRTUAL_CARD = "VIRT";
    public static final String CONTACTLESS_CARD = "CONT";
    public static final String EMV_BIOMETRIC_CARD = "EMVB";

    public static final String EMVC_CHIP_CARD_CODE = "1";
    public static final String MAGNATIC_STRIPE_CARD_CODE = "2";

    //payment batch ---------
    public static final String BATCH_OPEN = "BMOP";
    public static final String BATCH_CLOSED = "BMCL";
    public static final String BATCH_RECONCILED = "BMRE";

    //payment  ---------
    public static final String PAY_INIT = "PINIT";
    public static final String PAY_COMP = "PCOM";
    public static final String PAY_VOID = "PVOD";

    // fee profile category
    public static final String MERCHANT_FEE_PROFILE = "MER";
    public static final String CARD_FEE_PROFILE = "CRD";

    // corporate assign
    public static final String ESTABLISHMENT_CATEGORY = "COP";
    public static final String CORPORATE_CATEGORY = "ID";

    public static final String ESTABLISHMENT_CAT = "E";
    public static final String CORPORATE_CAT = "C";

    public static final String CARD_STATUS_CATEGORY = "CARD";
    //Security module codes
    public static final String ERROR_CONNECT_TO_SEC_MODULE_CODE = "0209";
    //CARDACCOUNTCUSTOMER customer codes.
    public static final String CARD_ACCOUNT_CUSTOMER_IS_PRIMARY_CODE_YES = "YES";
    public static final String CARD_ACCOUNT_CUSTOMER_IS_PRIMARY_CODE_NO = "NO";
    //Dual Auth Requests
    public static final String DA_REQUSET_INITIATE = "RQIN";
    public static final String DA_REQUSET_APPROVE = "RQAC";
    public static final String DA_REQUSET_REJECT = "RQRJ";
    public static final String DA_REQUEST_APPROVE_DES = "Request Accept";
    public static final String DA_REQUEST_REJECT_DES = "Request Reject";

    //Sending Emails
    public static final String WELCOME_EMAIL_SENT = "Y";
    public static final String WELCOME_EMAIL_NOT_SENT = "N";

    //sending password changing request
    public static final String PASSWORD_RESET_REQUEST_SENT = "PWRS";

    //system user request
    public static final String DELETE_SYSTEMUSER_REQUEST_SENT = "DRIN";
    public static final String DELETE_SYSTEMUSER_REQUEST_SENT_DES = "DRIN";
    public static final String ADD_SYSTEMUSER_REQUEST_SENT = "ARIN";
    public static final String SYSTEMUSER_DELETED = "UDEL";
    public static final String USER_CATEGORY = "USER";
    public static final String DATH_CATEGORY = "DATH";

    //Fee types
    public static final String CASH_ADVANCE_FEE = "FEC007";
    public static final String PIN_RESET_FEE = "FEC009";
    public static final String CHEQUE_RETURN_FEE_ON_PAYMENTS_INSUFFICIENT_FUNDS = "FEC012";
    public static final String CHEQUE_RETURN_FEE_ON_PAYMENTS_OTHER_REASON = "FEC013";

    //first login status
    public static final String IS_FIRST_LOGIN = "Y";
    public static final String IS_NOT_FIRST_LOGIN = "N";

    //lock for auth
    public static final String IS_LOCKED_AUTH = "Y";
    public static final String IS_NOT_LOCKED_AUTH = "N";

    //manual adjustment
    public static final String MA_PENDING = "MAPD";
    public static final String MA_ACCEPTED = "MAAC";
    public static final String MA_REJECTED = "MARJ";

    
    //letter templates
    public static final String APP_CONF_LETTER_TMP="333";
    public static final String APP_REJECT_LETTER_TMP="444";

    
    //default currency code no
    public static final int DEFAULT_CURRENCYNUMCODE = 144;
}
