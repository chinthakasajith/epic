package com.epic.cms.system.util.variable;

/**
 * all the messages are write here.
 *
 * @author ayesh
 */
public class MessageVarList {

    ///////////// CMSSytemUserPersistance
//    public static final String SECTION_STATUS_LIST = "secList";
//    public static final String SECTION_DETAILS_LIST = "allsecList";
    ////////////////////
    //////////////////section mgt
    public static final String SECTION_EXIST = "Section code already exists";
    public static final String SECTION_SECTIONCODE_NULL = "Section code cannot be empty";
    public static final String SECTION_SECTIONCODE_INVALIDE = "Enter valid section code";
    public static final String SECTION_DES_INVALIDE = "Enter valid description";
    public static final String SECTION_DESCRIPTION_NULL = "Description cannot be empty";
    public static final String SECTION_STATUS_NULL = "Status cannot be empty";
    public static final String SECTION_SORTKEY_ERROR = "Sort key should be numeric ";
    public static final String SECTION_SORTKEY_NULL = "Sort key cannot be empty ";
    public static final String SECTION_SORTKEY_INVALIDE = "Enter valid sort key code";
    public static final String SECTION_ADD_SUCCESS = "Section added successfully";
    public static final String SECTION_EDIT_SUCCESS = "Section updated successfully";
    public static final String SECTION_DELETE_SUCCESS = "Section deleted successfully ";
    public static final String SECTION_DELETE_ERROR = "Error in deleting section";
    public static final String APPLICATION_NULL = "Application module must be selected";
    public static final String SECTION_NULL = "Section must be selected";
    /////////////login
    public static final String INVALIDE_LOGIN = "Invalid username or password";
    public static final String LOGIN_EXPIRED = "User login expired !!";
    public static final String LOGIN_INACTIVATED = "User account inactivated !!";
    public static final String LOGIN_PASSWORD_EXPIRED = "User password expired !!";
    public static final String FIRST_LOGIN_PASS_EXPIRED="First Login Password Expired. Please contact administrator.";
    ///////////////////
    //////////////////commam validation messages
    public static final String ROLLBACK_FAILED = "Rollback failed: ";
    public static final String EMPTY_ERROR_MESSAGE = "Field cannot be empty.";
    public static final String SIZE_ERROR_MESSAGE = "Field size must be ";
    public static final String NUMBER_ERROR_MESSAGE = "Please enter a valid number ";
    ///////////////////
    public static final String SESSION_EXPIRED = "Your session has expired!";
    //------URLs----
    public static final String SESSION_EXPIRED_URL = "/administrator/controlpanel/login/login.jsp?message=";
    //--------
    public static final String LAST_SESSION_CLOSE = "Your session has ended. Same user has logged in from another computer";
    public static final String ACCESS_DENIED_TASK = "You have no permission to access the page or task. please contact administrator.";
    public static final String ACCESS_DENIED_PAGETASK = "You have no permission to perform this action.";
    //////////////// 
    //user role management
    public static final String ERROR_LOAD_USERROLE = "Error occured in loading user role details";
    public static final String ERROR_ADD_USERROLE = "Error occured in adding new user role";
    public static final String USERROLE_USERROLECODE_NULL = "User role code cannot be empty";
    public static final String USERROLE_USERLEVELID_NULL = "User level cannot be empty";
    public static final String USERROLE_USERROLECODE_INVALID = "Enter valid user role code";
    public static final String USERROLE_DESCRIPTION_NULL = "Description cannot be empty";
    public static final String USERROLE_STATUSCODE_NULL = "Status cannot be empty";
    public static final String USERROLE_SUCCESS_ADD = "User role successfully added";
    public static final String USERROLE_SUCCESS_DUPLICATE = "User role successfully duplicated";
    public static final String ERROR_VIEW_USERROLE = "Error occurred in user role view";
    public static final String USERROLE_SUCCESS_DELETE = "User role successfully deleted";
    public static final String USERROLE_SUCCESS_UPDATE = "User role successfully updated";
    public static final String ERROR_DELETE_USERROLE = "Error occurred in deleting user role";
    
//commen configuration
    public static final String APPLICATION_SEC_MNGMNT_SECTION_ASSIGN_SUCCESS = "Section assigned successfully";
    
//user level management
    public static final String ERROR_LOAD_USERLEVEL = "Error occurred in loading user level details";
    public static final String ERROR_ADD_USERLEVEL = "Error occurred in adding new user level";
    public static final String USERLEVEL_USERLEVELCODE_NULL = "User level code cannot be empty";
    public static final String USERLEVEL_USERLEVELID_INVALID = "User level ID must be a number";
    public static final String USERLEVEL_DESCRIPTION_NULL = "Description cannot be empty";
    public static final String USERLEVEL_STATUSCODE_NULL = "Status cannot be empty";
    public static final String USERLEVEL_SUCCESS_ADD = "User level successfully added";
    public static final String ERROR_VIEW_USERLEVEL = "Error occurred in user level view";
    public static final String USERLEVEL_SUCCESS_DELETE = "User level successfully deleted";
    public static final String USERLEVEL_SUCCESS_UPDATE = "User level successfully updated";
    public static final String ERROR_DELETE_USERLEVEL = "Error occurred in deleting user level";
    public static final String ERROR_UPDATE_USERLEVEL = "Error occured in updating user level";

    // user role privolege management
    public static final String SELECT_USERROLECODE = "Please select the user role";
    public static final String SELECT_ONE_OPERATION = "Please select the operation";
    public static final String SUCCESS_ASSIGN_USER_APPLICAIONS = "Successfully assigned application for user role";
    public static final String SUCCESS_ASSIGN_USER_APPLICAION_SECTION = "Successfully assigned sections for user role";
    public static final String SUCCESS_ASSIGN_USER_SECTION_PAGES = "Successfully assigned pages for user role";
    public static final String SUCCESS_ASSIGN_USER_PAGE_TASKS = "Successfully assigned tasks for user role";
    public static final String ERROR_ASSIGN_USER_APPLICAIONS = "Error occurred in assigning applications for user role";
    public static final String ERROR_USER_SECTION_PAGES = "Error occurred in assigning pages for user role";
    public static final String ERROR_USER_APPLICAION_SECTION = "Error occurred in assigning sections for user role";
    public static final String ERROR_USER_PAGE_TASKS = "Error occurred in assigning tasks for user role";
    public static final String ERROR_LOAD_USER_APPLICATIONS = "Error occurred in loading user role applications";
//    public static final String SECTION_ADD_BEAN = "secBean";
    ////// jsp response pass error-success message
    public static final String JSP_ERROR = "errorMsg";
    public static final String JSP_SUCCESS = "successMsg";
    public static final String UNKNOW_ERROR = "Error occurs.";
    ////// application mgt
    public static final String APPLICTION_APPCODE_NULL = "Application code cannot be empty";
    public static final String APPLICTION_APPCODE_INVALIDE = "Enter valid application code";
    public static final String APPLICTION_DESCRIPTION_INVALIDE = "Enter valid description code";
//    public static final String APPBEAN = "appBean";
    public static final String APPLICTION_DESCRIPTION_NULL = "Description cannot be empty";
    public static final String APPLICTION_STATUS_NULL = "Status cannot be empty";
    public static final String APPLICTION_CAT_NULL = "Application category cannot be empty";
    public static final String APPLICTION_EXIST = "Application code already exists";
    public static final String APPLICATION_ADD_SUCCESS = "Application added successfully";
    public static final String APPLICATION_UPDATE_SUCCESS = "Application updated successfully";
    public static final String APPLICATION_DELETE_SUCCESS = "Application deleted successfully";
    public static final String APPLICATION_DELETE_ERROR = "Error In deleting application";
//    public static final String APPLICTION_ADD_BEAN = "addApp";
    //Task Management
    public static final String TASK_TASKCODE_NULL = "Task code cannot be empty";
    public static final String TASK_TASKCODE_INVALID = "Enter valid task code";
    public static final String TASK_DES_INVALID = "Enter valid description";
    public static final String TASK_DESCRIPTION_NULL = "Description cannot be empty";
    public static final String TASK_SORTKEY_INVALID = "Enter valid sort key";
    public static final String TASK_STATUSCODE_NULL = "Status cannot be empty";
    public static final String TASK_SUCCESS_ADD = "Task added successfully";
    public static final String TASK_SUCCESS_UPDATE = "Task updated successfully";
    public static final String TASK_SUCCESS_DELETE = "Task deleted successfully";
    public static final String TASK_SORTKEY_NULL = "Sort key field cannot be empty";
    ////////////////////password policy-ayesh
//    public static final String PASSWORDPOLICY_DETAILS_BEAN = "passwdBean";
//    public static final String PASSWORDPOLICY_UPDATE_BEAN = "passwd";
    public static final String PASSPOLICY_MAXLEN_INVALID = "Maximum length value invalid";
    public static final String PASSPOLICY_MAXLEN_NULL = "Maximum length value cannot be empty ";
    public static final String PASSPOLICY_MINLEN_INVALID = "Minimum length value invalid";
    public static final String PASSPOLICY_MINLEN_NULL = "Minimum length value cannot be empty";
    public static final String PASSPOLICY_SPCAILCHARACTER_INVALID = "Minimum number of special characters value is invalid";
    public static final String PASSPOLICY_SPCAILCHARACTER_NULL = "Minimum number of special characters value cannot be empty";
    public static final String PASSPOLICY_UPPERCASECHAR_INVALID = "Minimum number of uppercase characters value is invalid";
    public static final String PASSPOLICY_UPPERCASECHAR_NULL = "Minimum number of uppercase characters value cannot be empty";
    public static final String PASSPOLICY_NUMERICCHAR_INVALID = "Minimum number of numerical characters value is invalid";
    public static final String PASSPOLICY_NUMERICCHAR_NULL = "Minimum number of numerical characters value cannot be empty";
    public static final String PASSPOLICY_LOWERCASE_INVALID = "Minimum number of lowercase characters value is invalid";
    public static final String PASSPOLICY_LOWERCASE_NULL = "Minimum number of lowercase characters cannot be empty";
    public static final String PASSPOLICY_UPDATE_SUCCESS = "Successfully updated password policy";
    public static final String PASSPOLICY_MINLENGHT_LIMIT_ERROR = "Minimun password length must be greater than four";
    public static final String PASSPOLICY_MAXLEN_INVALID_LENGTH = "Maximum length must be greater or equal with the count of all character types.";
    /////
    //sectionpage management
    public static final String SELECT_APPLICATIONCODE = "Please select the application module";
    public static final String SELECT_OPERATION = "Please select the operation";
    //page Management
    public static final String PAGE_PAGECODE_NULL = "Page code cannot be empty";
    public static final String PAGE_PAGECODE_INVALID = "Enter valid page code";
    public static final String PAGE_DESCRIPTION_NULL = "Description cannot be empty";
    public static final String PAGE_URL_NULL = "URL cannot be empty";
    public static final String PAGE_SORTKEY_INVALID = "Enter valid sort key";
    public static final String PAGE_STATUSCODE_NULL = "Please select a status";
    public static final String PAGE_ASSIGNEDTASK_NULL = "Please select at least one task";
    public static final String PAGE_SUCCESS_ADD = "Successfully added a page";
    public static final String PAGE_ERROR = "An error occurred";
    public static final String PAGE_SUCCESS_DELETE = "Page deleted successfully";
    public static final String PAGE_SUCCESS_UPDATE = "Page updated successfully";
    ///////////////////
    //////exchange rate
//    public static final String EXCHANGE_CURRENCY = "exchList";
//    public static final String EXCHANGE_BEAN = "exBean";
    public static final String EXCHANGE_RATE_INVALID = "Exchange rate invalid";
    public static final String EXCHANGE_RATE_NULL = "Exchange rate cannot be empty";
    public static final String EXCHANGE_TABLE_LOADING = "Error in data table loading";
    public static final String CURRENCY_TYPE_EQUAL = "Both currencies are same";
//    public static final String EXCHANGE_DETAILS = "allExList";
    public static final String EXCHANGERATE_RATE_NULL = "Exchange rate cannot be empty";
    public static final String EXCHANGERATE_ADD_SUCCESS = "Successfully added exchange rate";
    public static final String EXCHANGERATE_DELETE_SUCCESS = "Successfully deleted exchange rate";
    public static final String EXCHANGERATE_UPDATE_SUCCESS = "Successfully updated exchange rate";
    ////card domain template management
    public static final String CARDDOMAIN_TEMPLATECODE_NULL = "Template code cannot be empty";
    public static final String TEMP_CODE_INVALID = "Template code is invalid";
    public static final String CARDDOMAIN_TEMPLATENAME_NULL = "Template name cannot be empty";
    public static final String TEMP_NAME_INVALID = "Template name is invalid";
    public static final String CARDDOMAIN_STATUS_NULL = "Status cannot be empty";
    public static final String CARDDOMAIN_ACCOUNTTEMP_NULL = "Account template cannot be empty";
    public static final String CARDDOMAIN_TO_NULL = "Valid to date cannot be empty";
    public static final String CARDDOMAIN_FROM_NULL = "Valid from date cannot be empty";
    public static final String CARDDOMAIN_CD_TYPE_NULL = "Card type cannot be empty";
    public static final String CARDDOMAIN_CURRENCY_NULL = "Currency type cannot be empty";
    public static final String EXPIRY_PERIOD_NULL = "Expiry period cannot be empty";
    public static final String EXPIRY_PERIOD_INVALID = "Enter valid expiry period";
    public static final String RENEW_PERIOD_NULL = "Renew period cannot be empty";
    public static final String RENEW_PERIOD_INVALID = "Renew period should be greater than re-issue  threshold period";
    public static final String REISSUE_THRESHOLD_PERIOD_NULL = "Re-issue threshold period cannot be empty";
    public static final String REISSUE_THRESHOLD_PERIOD_INVALID = "Enter valid re-issue threshold period";
    public static final String CASH_ADVANCE_NULL = "Cash advance rate cannot be empty";
    public static final String CASH_ADVANCE_INVALID = "Enter valid cash advance rate";
    public static final String FEE_PROFILE_NULL = "Fee profile must be selected";
    public static final String RISK_PROFILE_NULL = "Risk profile must be selected";
    public static final String TRANSACTION_PROFILE_NULL = "Transaction profile must be selected";
    public static final String ERROR_ADD_CARDDOMAIN = "Error occurred in adding new card domain template";
    public static final String SUCCESS_UPDATE_CARDDOMAIN_GENERAL = "Card domain template updated successfully";
    public static final String ERROR_UPDATE_CARDDOMAIN = "Error occurred in updating card domain template";
    public static final String ERROR_CARDDOMAIN_GENERAL = "Error occurred in loading general details";
    public static final String CARDDOMAIN_CUSTOMERTEMPLATE_NULL = "Customer template cannot be empty";
    public static final String CARDDOMAIN_STAFFSTATUS_NULL = "Staff status cannot be empty";
    public static final String CARDDOMAIN_PRODUCT_NULL = "Product type cannot be empty";
    public static final String CARDDOMAIN_TOTALCREDIT_NULL = "Total credit limit cannot be empty";
    public static final String CARDDOMAIN_CODE_INVALID = "Enter valid card domain code";
    public static final String CARDDOMAIN_NAME_INVALID = "Enter valid card domain name";
    public static final String CARDDOMAIN_TOTALLIMIT_INVALID = "Enter valid total credit limit";
    public static final String CARDDOMAIN_DEBIT_ACCOUNTTEMP_NULL = "Debit account template code cannot be empty";
    public static final String ERROR_DELETE_CARDDOMAIN = "Error occurred in deleting card domain template";
    public static final String SUCCESS_DELETE_CARDDOMAIN = "Card domain template deleted successfully";
    public static final String ERROR_LOAD_CARDDOMAIN = "Error occurred in loading card domain templates";
    public static final String SUCCESS_ADD_CARDDOMAIN = "Successfully added card domain ";
    public static final String SUCCESS_ADD_CARDDOMAIN_COMLETE = "Profile add completed";
    public static final String SERVICE_CODE_NULL = "Service code cannot be empty";
    public static final String SERVICE_CODE_INVALID = "Enter valid service Code";
    public static final String RENEW_REQUIRED_NULL = "Renew period cannot be empty";
    public static final String RENEW_REQUIRED_INVALID = "Enter valid renew period";
    public static final String CASH_ADVANCE_RATE_NULL = "Cash advance rate cannot be empty";
    public static final String CASH_ADVANCE_RATE_INVALID = "Enter valid cash advance rate";
    public static final String CARDDOMAIN_INTEREST_NULL = "Interest profile code cannot be empty";
    public static final String CARDDOMAIN_CARDHOLDER_NULL = "Card holder fee profile code cannot be empty";
    public static final String CARDDOMAIN_TXN_NULL = "Transaction fee profile code cannot be empty";
    public static final String CARD_CATEGORY_NULL = "Card category must be selected";
    ///// card template management usage....
    public static final String ERROR_LOAD_CARDTEMPLATE = "Error occurred in loading card templates";
    public static final String SUCCESS_DELETE_CARDTEMPLATE = "Card template deleted successfully";
    public static final String ERROR_DELETE_CARDTEMPLATE = "Error occurred in deleting card template";
    public static final String SUCCESS_UPDATE_CARDTEMPLATE = "Card template updated successfully";
    public static final String SUCCESS_UPDATE_CARDTEMPLATE_PROFILE = "Card template profile update completed";
    public static final String ERROR_UPDATE_CARDTEMPLATE = "Error occurred in updating card template";
    public static final String ERROR_ADD_CARDTEMPLATE = "Error occurred in adding card template";
    ///// bulk card template management
    public static final String ERROR_LOAD_DEBIT_CARDTEMPLATE = "Error occurred in loading card templates";
    public static final String SUCCESS_DELETE_DEBIT_CARDTEMPLATE = "Card template deleted successfully";
    public static final String ERROR_DELETE_DEBIT_CARDTEMPLATE = "Error occurred in deleting card template";
    public static final String SUCCESS_UPDATE_DEBIT_CARDTEMPLATE = "Card template updated successfully";
    public static final String ERROR_UPDATE_DEBIT_CARDTEMPLATE = "Error occurred in updating card template";
    public static final String SUCCESS_ADD_DEBIT_CARD = "Card template added successfully";
    public static final String ERROR_ADD_DEBIT_CARD = "Error occurred in adding card template";
    //Currency management
    public static final String CURRENCY_CURRENCYCODE_NULL = "Currency code field cannot be empty";
    public static final String CURRENCY_CURRENCYCODE_INVALID = "Currency code number must be a numeric";
    public static final String CURRENCY_ALPHA_INVALID = "Currency alpha code is invalid";
    public static final String CURRENCY_CURRENCYALPHA_NULL = "Currency alpha code field cannot be empty";
    public static final String CURRENCY_EXPONENT_NULL = "Exponent field cannot be empty";
    public static final String CURRENCY_EXPONENT_INVALID = "Exponent must be a numeric";
    public static final String CURRENCY_DES_INVALID = "Description is invalid";
    public static final String CURRENCY_Description_NULL = "Description field cannot be empty";
    public static final String CURRENCY_STATUS_NULL = "Status should be selected";
    public static final String CURRENCY_SUCCESS_ADD = "Successfully added currency";
    public static final String CURRENCY_SUCCESS_UPDATE = "Successfully updated currency";
    public static final String CURRENCY_SUCCESS_DELETE = "Successfully deleted currency";
    ////////////////////////////////////////MERCHANT MESSAGES///////////////////////////////////////////
    public static final String MRCHNTCATMGT_MCCCODE_NULL = "Merchant category code cannot be empty.";
    public static final String MRCHNTCATMGT_MCCCODE_INVALID = " Merchant category code should be alpha numeric.";
    public static final String MRCHNTCATMGT_DESCRIPTION_NULL = "Merchant description cannot be empty.";
    public static final String MRCHNTCATMGT_MCLASS_NULL = "Merchant class should be selected.";
    public static final String MRCHNTCATMGT_SUCCESS_ADD = "Successfully  added ";
    public static final String MRCHNTCATMGT_STATUS_NULL = "Merchant status should be selected.";
    public static final String MRCHNTCATMGT_MERCHANT_SUCCESS_DELETE = "Successfully deleted ";
    public static final String MRCHNTCATMGT_MERCHANT_SUCCESS_UPDATE = "Successfully updated ";
    ////////////////////////////////////////END OF MERCHANT MESSAGES///////////////////////////////////////
    //////////////////////////////////////////TERMINAL DATA CAPTURE MESSAGES//////////////////////////////
    public static final String TERMINAL_MGT_TERMINAL_SUCCESS_DELETE = "Successfully deleted ";
    public static final String TERMINAL_MGT_SUCCESS_ADD = "Successfully  added ";
    public static final String TERMINAL_MGT_TRMNLID_NULL = "Terminal id cannot be empty.";
    public static final String TERMINAL_MGT_TRMNLID_INVALID = "Invalid terminal id.";
    public static final String TERMINAL_MGT_TRMNLNAME_NULL = "Terminal name cannot be empty.";
    public static final String TERMINAL_MGT_TRMNLNAME_INVALID = "Invalid terminal name.";
    public static final String TERMINAL_MGT_SERIALNO_NULL = "serial no cannot be empty.";
    public static final String TERMINAL_MGT_SERIALNO_INVALID = "Invalid serial number.";
    public static final String TERMINAL_MGT_MANUFACT_NULL = "Manufacturer cannot be empty.";
    public static final String TERMINAL_MGT_MODEL_NULL = "Model cannot be empty.";
    public static final String TERMINAL_MGT_INSTAL_DATE_NULL = "Installation date cannot be empty.";
    public static final String TERMINAL_MGT_ACTIVATE_DATE_NULL = "Activation date cannot be empty.";
    public static final String TERMINAL_MGT_ALLO_STATUS_NULL = "Allocation status cannot be empty.";
    public static final String TERMINAL_MGT_TRMINAL_STATUS_NULL = "Terminal status cannot be empty.";
    public static final String TERMINAL_MGT_TERMINAL_SUCCESS_UPDATE = "Successfully updated ";
    public static final String TRMINAL_MGT_TERMINAL_ASSIGNMNT = "Error occured in assigning terminal";
    public static final String TRMINAL_MGT_TERMINAL_ERROR = "Error occured while searching";
    public static final String TERMINAL_MGT_TERMINAL_SUCCESS_DEACTIVATE = "Successfully deactivated ";
    public static final String TERMINAL_MGT_TERMINAL_STILL_ACTIVE = "Terminal is active cannot be deleted ";
    public static final String TERMINAL_MGT_TERMINAL_ALREADY_DELETED = "Terminal is already deactivated.";
    public static final String TERMINAL_MGT_TERMINAL_ALREADY_DELETED_CALLCEENTER = "Terminal is already deactivated.Terminal is in delete status ";
    public static final String TERMINAL_MGT_TERMINAL_BLOCKED = "Terminal is blocked ";
    public static final String ERROR_LOAD_TERMINALS = "Error occured in loading terminals";
    public static final String ERROR_SEARCH_PARAMETER = "Use valid parameres to search";
    public static final String TERMINAL_ALLOCATION_INVALID_TERMINALID = "Invalid terminal id";
    public static final String TERMINAL_ALLOCATION_INVALID_MERCHANTID = "Invalid merchant id";
    public static final String TERMINAL_ALLOCATION_INVALID_TERMINAL_NAME = "Invalid terminal name";
    public static final String TERMINAL_ALLOCATION_INVALID_TERMINAL_SERIALNO = "Invalid serial number";
    public static final String DEFAULT_TERMINAL_CANNOT_INSERT = "Default terminal Id cannot be inserted";
    ///////////////////////////////////////END OF TERMINAL DATA CAPTURE//////////////////////////////////////
    /////////////////////////////////////////SERVER MAIN CONFIGURATION MESSAGES///////////////////////////
    public static final String SERVERMAINCONFIG_SUCCESS_UPDATE = "Successfully updated ";
    public static final String SERVERMAINCONFIG_OPSYSTYPE_NULL = "Operating system type cannot be empty.";
    public static final String SERVERMAINCONFIG_OPSYSTYPE_INVALID = "Operating system type should be alpha numeric.";
    public static final String SERVERMAINCONFIG_RUNNINGMODE_NULL = "Running mode cannot be empty";
    public static final String SERVERMAINCONFIG_RUNNINGMODE_INVALID = "Running mode should be alpha numeric.";
    public static final String SERVERMAINCONFIG_PERMANENTRUNNINGPORT_NULL = "Permanent running port cannot be empty";
    public static final String SERVERMAINCONFIG_PERMANENTRUNNINGPORT_INVALID = "Permanent running port is not valid";
    public static final String SERVERMAINCONFIG_TERMINATEDRUNNINGPORT_NULL = "Terminated running port cannot be empty.";
    public static final String SERVERMAINCONFIG_TERMINATEDRUNNINGPORT_INVALID = "Terminated running port is invalid";
    public static final String SERVERMAINCONFIG_NOOFCONNECTIONS_NULL = "Number of connections cannot be empty";
    public static final String SERVERMAINCONFIG_NOOFCONNECTIONS_INVALID = "Number of connections should be a numeric";
    public static final String SERVERMAINCONFIG_INITIALVECTOR_NULL = "Initial vector cannot be empty";
    public static final String SERVERMAINCONFIG_INITIALVECTOR_INVALID = "Initial vector should be a numeric";
    public static final String SERVERMAINCONFIG_REQBUFSIZE_NULL = "Request buffer size cannot be empty";
    public static final String SERVERMAINCONFIG_REQBUFSIZE_INVALID = "Request buffer size is invalid";
    public static final String SERVERMAINCONFIG_RESBUFSIZE_NULL = "Response buffer size cannot be empty";
    public static final String SERVERMAINCONFIG_RESBUFSIZE_INVALID = "Response buffer size is invalid";
    public static final String SERVERMAINCONFIG_ALERTSTATUS_NULL = "Alert status cannot be empty";
    public static final String SERVERMAINCONFIG_ALERTSTATUS_INVALID = "Alert status is invalid";
    public static final String SERVERMAINCONFIG_FAILSTATUS_NULL = "Fail status cannot be empty";
    public static final String SERVERMAINCONFIG_FAILSTATUS_INVALID = "Fail status is invalid";
    public static final String SERVERMAINCONFIG_LOGLEVEL_NULL = "Log level cannot be empty";
    public static final String SERVERMAINCONFIG_LOGLEVEL_INVALID = "Log level is invalid";
    public static final String SERVERMAINCONFIG_LOGFILEPREFIX_NULL = "Log file prefix cannot be empty";
    public static final String SERVERMAINCONFIG_LOGFILEPREFIX_INVALID = "Log file prefix is invalid";
    public static final String SERVERMAINCONFIG_LOGAUTOBKUPSTATUS_NULL = "Log auto backup status cannot be empty";
    public static final String SERVERMAINCONFIG_LOGAUTOBKUPSTATUS_INVALID = "Log auto backup status is invalid";
    public static final String SERVERMAINCONFIG_DEBUGSTATUS_NULL = "Debug status cannot be empty";
    public static final String SERVERMAINCONFIG_DEBUGSTATUS_INVALID = "Debug status is invalid";
    public static final String SERVERMAINCONFIG_TEMPCONTIMEOUT_NULL = "Temporary connection timeout cannot be empty";
    public static final String SERVERMAINCONFIG_TEMPCONTIMEOUT_INVALID = "Temporary connection timeout should be numeric";
    public static final String SERVERMAINCONFIG_MAXPOOLSIZE_NULL = "Maximum pool size cannot be empty";
    public static final String SERVERMAINCONFIG_MAXPOOLSIZE_INVALID = "Maximum pool size should be numeric";
    public static final String SERVERMAINCONFIG_MINPOOLSIZE_NULL = "Minimum pool size cannot be empty";
    public static final String SERVERMAINCONFIG_MINPOOLSIZE_INVALID = "Minimum pool size should be numeric";
    public static final String SERVERMAINCONFIG_MAXQUEUESIZE_NULL = "Maximum queuing size cannot be empty";
    public static final String SERVERMAINCONFIG_MAXQUEUESIZE_INVALID = "Maximum queuing size should be numeric";
    public static final String SERVERMAINCONFIG_POOLBACKLOG_NULL = "Pool backlog cannot be empty";
    public static final String SERVERMAINCONFIG_POOLBACKLOG_INVALID = "Pool backlog should be numeric";
    public static final String SERVERMAINCONFIG_MAXPINCOUNT_NULL = "Maximum PIN count cannot be empty";
    public static final String SERVERMAINCONFIG_MAXPINCOUNT_INVALID = "Maximum PIN count should be numeric";
    public static final String SERVERMAINCONFIG_MONITORIP_NULL = "Monitoring IP cannot be empty";
    public static final String SERVERMAINCONFIG_MONITORIP_INVALID = "Monitoring IP is not valid";
    public static final String SERVERMAINCONFIG_MONITORPORT_NULL = "Monitoring port cannot be empty";
    public static final String SERVERMAINCONFIG_MONITORPORT_INVALID = "Monitoring port should be numeric";
    public static final String SERVERMAINCONFIG_MONITORTIMEOUT_NULL = "Monitoring timeout cannot be empty";
    public static final String SERVERMAINCONFIG_MONITORTIMEOUT_INVALID = "Monitoring timeout is invalid";
    public static final String SERVERMAINCONFIG_MONITORSTATUS_NULL = "Monitoring status cannot be empty";
    public static final String SERVERMAINCONFIG_MONITORSTATUS_INVALID = "Monitoring status is invalid";
    public static final String SERVERMAINCONFIG_BACKUPPERIOD_NULL = "Backup period cannot be empty";
    public static final String SERVERMAINCONFIG_BACKUPPERIOD_INVALID = "Backup period should be a number";
    public static final String SERVERMAINCONFIG_PVI_INVALID = "PVI should be in between 0 and 6";
    public static final String SERVERMAINCONFIG_HSMTYPE_NULL = "HSM type cannot be empty";
    public static final String SERVERMAINCONFIG_PINB_NULL = "PIN Block Format cannot be empty";
    public static final String SERVERMAINCONFIG_EMV_NULL = "EMV verification method cannot be empty";
    public static final String SERVERMAINCONFIG_ENCR_NULL = "ENCR mode cannot be empty";
    ///////////////////////////////////////END OF SERVER MAIN CONFIGURATION MESSAGES /////////////////////////////////////////////////////
    ///////////////////////////////////BILL CYCLE DATA CAPTURE MESSAGES/////////////////////////////////////
    public static final String BILL_CYCLE_MGT_SUCCESS_ADD = "Successfully  added billing cycle";
    public static final String BILL_CYCLE_MGT_BILLCODE_NULL = "Billing cycle code cannot be empty.";
    public static final String BILL_CYCLE_MGT_BILLCODE_INVALID = "Billing cycle code is invalid.";
    public static final String BILL_CYCLE_MGT_BILLDATE_NULL = "Billing date cannot be empty.";
    public static final String BILL_CYCLE_MGT_BILLDESCRIPTION_NULL = "Billing description cannot be empty.";
    public static final String BILL_CYCLE_MGT_HOLIACT_NULL = "Holiday action cannot be empty.";
    public static final String BILL_CYCLE_MGT_STATUS_NULL = "Billing status cannot be empty.";
    public static final String BILL_CYCLE_MGT_STATUS_DEA = "Billing status cannot be deactive.";
    public static final String BILL_CYCLE_MGT_BILLING_CODE_SUCCESS_DELETE = "Successfully deleted billing cycle";
    public static final String BILL_CYCLE_MGT_BILLING_CODE_SUCCESS_UPDATE = "Successfully updated billing cycle";
    ////////////////////////////////////END OF BILL CYCLE DATA CAPTURE MESSAGES////////////////////////////////
    ////////////////////////////////////SECURITY QUESTION MANAGEMENT////////////////////////////////////////////
    public static final String SECURITY_QUES_MGT_SUCCESS_DELETE = "Successfully deleted security question ";
    public static final String SECURITY_QUES_MGT_SUCCESS_ADD = "Successfully added security question ";
    public static final String SECURITY_QUES_MGT_QUESTION_NO_SUCCESS_UPDATE = "Successfully updated security question";
    public static final String SECURITY_QUES_MGT_QUESTION_NO_UNSUCCESS_UPDATE = "Security question update failed ";
    public static final String SECURITY_QUES_MGT_QUESTION_NO_NULL = "Question number cannot be empty.";
    public static final String SECURITY_QUES_MGT_QUESTION_NO_INVALID = "Question number is invalid";
    public static final String SECURITY_QUES_MGT_QUESTION_NULL = "Question cannot be empty.";
    public static final String SECURITY_QUES_MGT_QUESTION_INVALID = "Question is invalid.";
    public static final String SECURITY_QUES_MGT_PRIORITY_LEVEL_NULL = "Priority level cannot be empty.";
    public static final String SECURITY_QUES_MGT_ISSUE_ACQUIRE_STATUS_NULL = "Issuing acquiring status cannot be empty.";
    public static final String SECURITY_QUES_MGT_QTYPE_NULL = "Question type cannot be empty.";
    public static final String SECURITY_QUES_MGT_CARD_CATEGORY_NULL = "Card category cannot be empty.";
    public static final String SECURITY_QUES_MGT_TABLE_NAME_NULL = "Table name cannot be empty.";
    public static final String SECURITY_QUES_MGT_FIELD_1_NULL = "Field 1 cannot be empty.";
    public static final String SECURITY_QUES_MGT_QUESTION_STATUS_NULL = "Question status cannot be empty.";
    public static final String ERROR_LOAD_SEC_QUES = "Error occurred in loading security question";
    /////////////////////////////////////END OF SECURITY QUESTION MANAGEMENT////////////////////////////////
    ////////////////////////////////////////////////////////////////VIEW ALERTS/////////////////////////////////////
    public static final String VW_ALERT_RD_STATUS_NULL = "Read status should be selected.";
    public static final String VW_ALERT_ALERT_ASSIGNMNT = "Error occurred in assigning alert";
    public static final String ERROR_LOAD_ALERTS = "Error occurred in loading alerts";
    ////////////////////////////////////////////////////////////////END OF VIEW ALERTS
    ////////////////////////////////////////////////VIEW FAIL ALERTS////////////////////////////////////
    public static final String ERROR_LOAD_FAIL_ALERTS = "Error occurred in loading fail alerts";
    public static final String STATUS_CHANGED = "Record successfully read";
    //public static final String STATUS_ALREADY_CHANGED = "The Record has read.";
    ////////////////////////////////////////////////END OF VIEW FAIL ALERTS////////////////////////
    ////////////////////////////////////CURRENCY EXCHANGE RATE///////////////////////////////////
    public static final String SUCCESS_ADD = "Successfully  added currency exchange rate";
    public static final String SUCCESS_DELETE = "Successfully deleted currency exchange rate ";
    public static final String SUCCESS_UPDATE = "Successfully updated currency exchange rate ";
    public static final String ERROR_LOAD_CURR_X_RATE = "Error occurred in loading currency exchange rates";
    public static final String SELL_RATE_NULL = "Selling rate cannot be empty";
    public static final String BUY_RATE_NULL = "Buying rate cannot be empty";
    public static final String SELL_RATE_INVALID = "Selling rate is invalid";
    public static final String BUY_RATE_INVALID = "Buyibg rate is invalid";
    public static final String COUNTRYMGT_ALPHAFIRST_NULL = "Alpha code (2) cannot be empty";
    public static final String COUNTRYMGT_ALPHAFIRST_INVALID = "Alpha code (2) invalid";
    public static final String COUNTRYMGT_ALPHASECOND_NULL = "Alpha code (3) cannot be empty";
    public static final String COUNTRYMGT_ALPHASECOND_INVALID = "Alpha code (3) invalid";
    public static final String COUNTRYMGT_DESCRIPTION_NULL = "Description cannot be empty";
    public static final String COUNTRYMGT_DESCRIPTION_INVALID = "Description invalid";
    public static final String COUNTRYMGT_REGION_NULL = "Region cannot be empty";
    public static final String COUNTRYMGT_REGION_INVALID = "Region is invalid";
    public static final String COUNTRYMGT_COUNTRY_CODE = " Country code should be numeric";
    public static final String COUNTRYMGT_COUNTRY_CODE_NULL = "Country code cannot be empty";
//    public static final String COUNTRYMGT_LIST = "countryList";
    public static final String COUNTRYMGT_DELETE_SUCCESS = "Successfully deleted country";
    public static final String COUNTRYMGT_FRAUD_VALUE = "Fraud value should be numeric";
    public static final String COUNTRYMGT_FRAUD_VALUE_NULL = "Fraud value cannot be empty";
    public static final String COUNTRYMGT_FRAUD_CODE_NULL = "Fraud code value cannot be empty";
    public static final String COUNTRYMGT_ADD_SUCCESS = "Successfully added country";
    public static final String COUNTRYMGT_UPDATE_SUCCESS = "Successfully updated country";
    public static final String COUNTRYMGT_LOADTABLE_FAIL = "Error in data table load";
    public static final String COUNTRYMGT_BEAN = "cuBean";
    //Postal Code Management
    public static final String POSTAL_DELETE_SUCCESS = "Successfully deleted a record";
    //fee profile --
//    public static final String FEEPROFILE_STATUS = "proStatus";
//    public static final String FEEPROFILE_CURRENCY_LIST = "proCurrency";
    public static final String FEEPROFILE_CODE_EMPTY = "Fee profile code cannot be empty";
    public static final String FEEPROFILE_DESCRIPTION_EMPTY = "Description cannot be empty";
    public static final String FEEPROFILE_CATEGORY_EMPTY = "Fee category cannot be empty";
    public static final String FEEPROFILE_EFFECTIVEDAY_EMPTY = "Effective date cannot be empty";
    public static final String FEEPROFILE_STATUS_EMPTY = "Status cannot be empty";
    public static final String FEEPROFILE_CURRENCY_EMPTY = "Currency cannot be empty";
    //---
    public static final String FEEPROFILE_JOINFEE_EMPTY = "Joining fee cannot be empty";
    public static final String FEEPROFILE_ANNUALFEE_EMPTY = "Annual fee cannot be empty";
    public static final String FEEPROFILE_JOINFEE_INVALID = " Joining fee should be numeric";
    public static final String FEEPROFILE_JOINFEE_SHOULD_DOUBLE = " Joining fee cannot have more than two decimal  points";
    public static final String FEEPROFILE_ANNUALFEE_INVALID = " Annual fee should be numeric";
    public static final String FEEPROFILE_ANNUALFEE_SHOULD_DOUBLE = " Annual fee cannot have more than two decimal points";
    public static final String FEEPROFILE_RENWEALFEE_EMPTY = "Renewal fee cannot be empty";
    public static final String FEEPROFILE_RENWEALFEE_INVALID = "Renewal fee should be numeric";
    public static final String FEEPROFILE_RENWEALFEE_SHOULD_DOUBLE = "Renewal fee cannot have more than two decimal points";
    public static final String FEEPROFILE_REPLACEFEE_EMPTY = "Replace fee cannot be empty";
    public static final String FEEPROFILE_REPLACEFEE_INVALID = "Repalce fee should be mumeric";
    public static final String FEEPROFILE_REPLACEFEE_SHOULD_DOUBLE = "Repalce fee cannot have more than two decimal points";
    public static final String FEEPROFILE_LASTPAYFEE_EMPTY = "Last pay fee cannot be empty";
    public static final String FEEPROFILE_LASTPAYFEE_INVALID = "Last pay fee should be numeric";
    public static final String FEEPROFILE_LASTPAYFEE_SHOULD_DOUBLE = "Last pay fee cann't have more than two decimal points";
    public static final String FEEPROFILE_LEGALFEE_EMPTY = "Legal fee cannot be empty";
    public static final String FEEPROFILE_LEGALFEE_INVALID = "Legal fee should be numeric";
    public static final String FEEPROFILE_LEGALFEE_SHOULD_DOUBLE = "Legal fee cannot have more than two decimal points";
    public static final String FEEPROFILE_RETURNCHQEFEE_EMPTY = "Return cheque fee cannot be empty";
    public static final String FEEPROFILE_RETURNCHQEFEE_INVALID = "Return cheque fee should be numeric";
    public static final String FEEPROFILE_RETURNCHQEFEE_SHOULD_DOUBLE = "Return cheque fee cannot have more than two decimal points";
    public static final String FEEPROFILE_REJECTAUTOEFEE_EMPTY = "Reject auto fee cannot be empty";
    public static final String FEEPROFILE_REJECTAUTOEFEE_INVALID = "Reject auto fee should be numeric";
    public static final String FEEPROFILE_REJECTAUTOEFEE_SHOULD_DOUBLE = "Reject auto fee cannot have more than two decimal point";
    public static final String FEEPROFILE_LIMITEXCEEDFEE_EMPTY = "Limit exceed fee cannot be empty";
    public static final String FEEPROFILE_LIMITEXCEEDFEE_INVALID = "Limit exceed fee should be numeric";
    public static final String FEEPROFILE_LIMITEXCEEDFEE_SHOULD_DOUBLE = "Limit exceed fee cannot have more than two decimal points";
    public static final String FEEPROFILE_TEMPLIMITCHNGEDFEE_EMPTY = "Temporary limit change fee cannot be empty";
    public static final String FEEPROFILE_TEMPLIMITCHNGEFEE_INVALID = "Temporary limit change  fee should be numeric";
    public static final String FEEPROFILE_TEMPLIMITCHNGEFEE_SHOULD_DOUBLE = "Temporary limit change  fee cann't have more than two decimal Points";
    public static final String FEEPROFILE_PERLIMITINCREASEFEE_EMPTY = "Permanent limit increase fee cannot be empty";
    public static final String FEEPROFILE_PERLIMITINCREASEFEE_INVALID = "Permanent limit increase  fee should be numeric";
    public static final String FEEPROFILE_PERLIMITINCREASEFEE_SHOULD_DOUBLE = "Permanent limit increase fee cannot have more than two decimal points";
    public static final String FEEPROFILE_CARDTYPECHANGEFEE_EMPTY = "Card type fee cannot be empty";
    public static final String FEEPROFILE_CARDTYPECHANGEFEE_INVALID = "Card type fee should be numeric";
    public static final String FEEPROFILE_CARDTYPECHANGEFEE_SHOULD_DOUBLE = "Card type fee cannot have more than two decimal points";
    public static final String FEEPROFILE_STATEMENTCOPYFEE_EMPTY = "Statement copy fee cannot be empty";
    public static final String FEEPROFILE_STATEMENTCOPYFEE_INVALID = "Statement copy fee should be numeric";
    public static final String FEEPROFILE_STATEMENTCOPYFEE_SHOULD_DOUBLE = "Statement copy fee cannot have more than two decimal points";
    public static final String FEEPROFILE_ADD_SUCCESS = "Fee profile added successfully";
    public static final String FEEPROFILE_DELETE_SUCCESS = "Fee profile deleted successfully ";
    public static final String FEEPROFILE_UPDATE_SUCCESS = "Fee profile updated successfully";
    //---
//    public static final String FEEPROFILE_ADD_BEAN = "feeBean";
//    public static final String FEEPROFILE_INFO_LIST = "feeBeanList";
    //---
    public static final String POSTAL_INVALID_COUNTRYCODE = "Please select a country code";
    public static final String POSTAL_INVALID_CITY = "City field cannot be empty";
    public static final String POSTAL_INVAL_CITY = "Invalid city";
    public static final String POSTAL_NULL_POSTALCODE = "Postal Code field cannot be empty";
    public static final String POSTAL_INVALID_POSTALCODE = "Invalid Postal Code";
    public static final String POSTAL_SUCCESS_ADD = "Successfully added a record";
    public static final String POSTAL_SUCCESS_UPDATE = "Successfully updated";
    //Interest Profile 
    public static final String INTEREST_ASSIGNEDTRANSACTIONS_NULL = "Please select at least one transaction";
    public static final String INTEREST_CHARGETYPE_NULL = "Please select a charge type";
    public static final String INTEREST_TODATE_NULL = "Please select an interest calculation to date";
    public static final String INTEREST_FROMDATE_NULL = "Please select an interest calculation from date";
    public static final String INTEREST_EFFECTDATENEWACC_NULL = "Please select a effect date for new accounts";
    public static final String INTEREST_EFFECTDATEEXISTACC_NULL = "Please select a effect date for exist accounts";
    public static final String INTEREST_INTRESTTYPE_NULL = "Please select a interest type";
    public static final String INTEREST_INTRESTRATE_NULL = "Interest field cannot be empty";
    public static final String INTEREST_INTRESTRATE_INVALID = "Invalid interest rate";
    public static final String INTEREST_PERIOD_INVALID = "Invalid interest period";
    public static final String INTEREST_STATUS_NULL = "Please select a status";
    public static final String INTEREST_DESCRIPTION_NULL = "Description field cannot be empty";
    public static final String INTEREST_DESCRIPTION_INVALID = "Invalid description";
    public static final String INTEREST_PROFILECODE_NULL = "Interest profile code field cannot be empty";
    public static final String INTEREST_PERIOD_NULL = "Interest period field cannot be empty.";
    public static final String INTEREST_PROFILECODE_INVALID = "Invalid interest profile code";
    public static final String INTEREST_SUCCESS_ADD = "Successfully added an Interest Profile";
    public static final String INTEREST_SUCCESS_DELETE = "Successfully deleted an Interest Profile";
    public static final String INTEREST_INTRESTPERIOD_INVALID = "Invalid interest period value";
    public static final String INTEREST_INTRESTPERIOD_INVALIDRANGE = "Invalid interest period value must be within range of 1 and 365";
    public static final String INTEREST_ADDING_FAIL = "Interest profile adding failed";
    //holiday management
    public static final String HOLIDAY_REASON_NULL = "Holiday reason cannot be empty";
    public static final String HOLIDAY_DATE_NULL = "Holiday date cannot be empty";
    public static final String HOLIDAY_DATE_INVALID = "Holiday date cannot be a past date";
    public static final String HOLIDAY_REASON_INVALID = "Special characters are not allowed for holiday reason";
    public static final String INSERT_SUCCESS_HOLIDAY = "Holiday added successfully";
    public static final String DELETE_SUCCESS_HOLIDAY = "Holiday deleted successfully";
    public static final String UPDATE_SUCCESS_HOLIDAY = "Holiday updated successfully";
    public static final String UPDATE_ERROR_HOLIDAY = "Error in updating holiday.";
    public static final String DELETE_ERROR_HOLIDAY = "Error in deleting holiday.";
    public static final String INSERT_ERROR_HOLIDAY = "Unsuccessfull holiday insertion.";
    public static final String ORA_HOLIDAY_VALUE_NOT_FOUND = "ORA-HOLIDAY_VALUE_NOT_FOUND";
    // card application assign process
    public static final String ERROR_LOAD_ASSIGN_APP = "Error occurred in loading assigned card applications";
    public static final String SELECT_ONE_IDENTITY = "Idetification type cannot be empty";
    public static final String SELECT_ONE_DOMAIN = "Application domain cannot be empty";
    public static final String APPASSIGN_APPTYPE_NULL = "Application type cannot be empty";
    public static final String APPASSIGN_APPLICATIONCODE_NULL = "Application ID cannot be empty";
    public static final String APPASSIGN_APPLICATIONCODE_INVALID = "Invalid application ID";
    public static final String APPASSIGN_IDNUM_INVALID = "Invalid identification number";
    public static final String APPASSIGN_IDENTITY_NUMBER_NULL = "Identification number invalid";
    public static final String APPASSIGN_PRIORITY_NULL = "Priority level cannot be empty";
    public static final String APPASSIGN_ASSIGNUSER_NULL = "Assign user cannot be empty";
    public static final String APPASSIGN_ASSIGNSTATUS_NULL = "Assign status cannot be empty";
    public static final String SUCCESS_ADD_ASSIGN_APPLICATION = "Card application assigned successfully";
    public static final String ERROR_ADD_ASSIGN_APPLICATION = "Error occurred in assigning card application";
    public static final String ERROR_DEBIT_NUM_GEN = "Error occurred in debit card number generation";
    public static final String ERROR_CREDIT_NUM_GEN = "Error occurred in credit card number generation";
    public static final String SUCCESS_UPDATE_ASSIGN_APPLICATION = "Card application assigning updated successfully";
    public static final String ERROR_UPDATE_ASSIGN_APPLICATION = "Error occurred in updating card application assign";
    public static final String ERROR_NIC_NUMBER = "Invalid NIC number";
    public static final String ERROR_PAS_NUMBER = "Invalid passport number";
    public static final String ERROR_DRL_NUMBER = "Invalid driving licence number";
    public static final String ERROR_BRC_UNIQE = "Business Registration Number must be unique";
    public static final String ERROR_REFEMP_NUMBER = "Invalid referral employee number";
    public static final String ERROR_EXPIRY_DATE = "Passport expiry date cannot be empty";
    //user assign apps usage
    public static final String ERROR_LOAD_USER_ASSIGN_DEFAULT = "Error occurred in loading customer details";
    public static final String ERROR_LOAD_USER_ASSIGN_APP = "Error occurred in loading user assigned card applications";
//credit score filed difine
//      public static final String CREDITSCORE_FIELD_DEFINE_FORMLIST="formList";
//      public static final String CREDITSCORE_FIELD_DEFINE_LIST="allList";
//      public static final String CREDITSCORE_FIELD_DEFINE_DATABEAN="bean";
//      public static final String CREDITSCORE_FIELD_DEFINE_FIELDLIST="fieldList";
//      public static final String CREDITSCORE_FIELD_DEFINE_FORM="formName";
    public static final String CREDITSCORE_FIELD_FIELDCODE_EMPTY = "Field code cannot be empty";
    public static final String CREDITSCORE_FIELD_FIELDCODE_INVALID = "Field code invalid";
    public static final String CREDITSCORE_FIELD_STATUS_EMPTY = "Status cannot be empty";
    public static final String CREDITSCORE_FIELD_DATATYPE_EMPTY = "Data type cannot be empty";
    public static final String CREDITSCORE_FIELD_FORMNAME_EMPTY = "Form name cannot be empty";
    public static final String CREDITSCORE_FIELD_FIELDNAME_EMPTY = "Field name cannot be empty";
    public static final String CREDITSCORE_FIELD_FIELDDES_EMPTY = "Field description cannot be empty";
    public static final String CREDITSCORE_FIELD_FIELDDES_INVALID = "Field description invalid";
    public static final String CREDITSCORE_FIELD_FIELDDES_ADD_SUCCESS = "New credit score field defined successfully";
    public static final String CREDITSCORE_FIELD_FIELDDES_UPDATE_SUCCESS = "Credit score field updated successfully";
    public static final String CREDITSCORE_FIELD_FIELDDES_DELETE_SUCCESS = "Credit score field deleted successfully";
    ////cdredit score rule  defin managem,ent
    public static final String CREDITSCORE_RULECODE__EMPTY = "Rule code cannot be empty.";
    public static final String CREDITSCORE_RULECODE_INVALID = "Enter valid rule code.";
    public static final String CREDITSCORE_RULENAME__EMPTY = "Rule name cannot be empty.";
    public static final String CREDITSCORE_RULENAME_INVALID = "Enter valid rule name.";
    public static final String CREDITSCORE_FIELDNAME__EMPTY = "Field name cannot be empty.";
    public static final String CREDITSCORE_SCORE__EMPTY = "Score cannot be empty.";
    public static final String CREDITSCORE_SCORE_INVALID = "Enter valid score.";
    public static final String CREDITSCORE_FIELDNAME_INVALID = "Enter valid field name.";
    public static final String CREDITSCORE_CONDITION__EMPTY = "Condition cannot be empty.";
    public static final String CREDITSCORE_CONDITION_INVALID = "Enter valid condition.";
    public static final String CREDITSCORE_STATUS_INVALID = "Status cannot be empty";
    public static final String CREDITSCORE_VALUE_EMPTY = "Value cannot be empty.";
    public static final String CREDITSCORE_VALUE_INVALID = "Value must be a  ";
    public static final String CREDITSCORE_MAX_VALUE_EMPTY = "Max value cannot be empty.";
    public static final String CREDITSCORE_MAX_VALUE_INVALID = "Enter valid max value.";
    public static final String CREDITSCORE_RULEONE_EMPTY = "Please select rule number one.";
    public static final String CREDITSCORE_RULETWO_EMPTY = "Please select rule number two.";
    public static final String CREDITSCORE_RULEONE_INVALID = "Enter valid rule number one Value.";
    public static final String INSERT_CREDITSCORE_RULE_SUCCESS = "A new credit score rule added successfully";
    public static final String UPDATE_CREDITSCORE_RULE_SUCCESS = "Credit score rule updated successfully";
    public static final String INSERT_CREDITSCORE_RULE_ERROR = "Error occurred in inserting credit score rule";
    public static final String UPDATE_CREDITSCORE_RULE_ERROR = "Error occurred in updating credit score rule";
    public static final String CREDITSCORE_DELETE_SUCCESS = "Credit score rule deleted successfully";
    //-----score card --
    public static final String SCORECRAD_CODE_EMPTY = "Score card code cannot be empty";
    public static final String SCORE_CARD_CODE_INVALID = "Score card code invalid";
    public static final String SCORECRAD_NAME_EMPTY = "Score card name cannot be empty";
    public static final String SCORE_CARD_NAME_INVALID = "Score card name invalid";
    public static final String SCORECRAD_MINSCORE_EMPTY = "Minimum score card cannot be empty";
    public static final String SCORECRAD_MAXSCORE_EMPTY = "Maximum score card cannot be empty";
    public static final String SCORECRAD_SCORE_RANGE_INVALID = "Score card range not valid";
    public static final String SCORECRAD_MAXSCORE_INVALID = "Maximum score card should be numeric";
    public static final String SCORECRAD_MINSCORE_INVALID = "Minimum score card should be numeric";
    public static final String SCORECRAD_SATUS_EMPTY = "Status cannot be empty";
    public static final String CARDTYPE_SATUS_EMPTY = "Card type cannot be empty";
    public static final String SCORECRAD_PRODUCT_EMPTY = "Product cannot be empty";
    public static final String SCORECRAD_RULE_NULL = "Assign some rules";
    public static final String SCORECRAD_ADD_SUCCESS = "Credit score card added successfully";
    public static final String SCORECRAD_DELETE_SUCCESS = "Successfully deleted new card score ";
    public static final String SCORECRAD_UPDATE_SUCCESS = "Credit score card updated successfully";
    public static final String CARD_SCORE_SUCCESS_DELETE = "Credit score card deleted successfully";
//-------
    //application checking
    public static final String FILE_ACCEPTED = "Application accepted successfully";
    public static final String FILE_CHECKEDIN = "Application checked in successfully";
    public static final String FILE_CHECKEDIN_REMARK_EMPTY = "Remark field cannot be empty";
    //document type management
    public static final String DOCUMENT_TYPE_CODE_EMPTY = "Document type code cannot be empty";
    public static final String DOCUMENT_TYPE_CODE_INVALID = "Document type code is invalid";
    public static final String DOCUMENT_TYPE_NAME_EMPTY = "Document name cannot be empty";
    public static final String DOCUMENT_TYPE_NAME_INVALID = "Document name is invalid";
    public static final String DOCUMENT_TYPE_VERIFI_CATAGORY_EMPTY = "Verification category cannot be empty";
    public static final String DOCUMENT_TYPE_POSTFIX_EMPTY = "Postfix cannot be empty";
    public static final String DOCUMENT_TYPE_POSTFIX_INVALID = "Postfix is invalid";
    public static final String DOCUMENT_TYPE_STATUS_EMPTY = "Status cannot be empty";
    public static final String DOCUMENT_TYPE_ADD_SUCCESS = "Successfully added new document type ";
    public static final String DOCUMENT_TYPE_DELETE_SUCCESS = "Successfully deleted document type ";
    public static final String DOCUMENT_TYPE_UPDATE_SUCCESS = "Successfully updated document type ";
    public static final String DOCUMENT_TYPE_CARD_CATEGORY_EMPTY = "Please select a card category";
    public static final String DOCUMENT_TYPE_MANDATORY_EMPTY = "Please select mandatory status ";

    //credit confirmation schema mgt
    public static final String CREDIT_CONFIRM_SCHEMA_CODE_EMPTY = "Schema code cannot be empty";
    public static final String CREDIT_CONFIRM_SCHEMA_CODE_INVALID = "Enter valid schema code";
    public static final String CREDIT_CONFIRM_SCHEMA_DESCRIPTION_EMPTY = "Description cannot be empty";
    public static final String CREDIT_CONFIRM_SCHEMA_DESCRIPTION_INVALID = "Enter valid description";
    public static final String CREDIT_CONFIRM_SCHEMA_MINLIMIT_EMPTY = "Minimum limit cannot be empty";
    public static final String CREDIT_CONFIRM_SCHEMA_MINLIMIT_INVALID = "Enter valid minimum limit";
    public static final String CREDIT_CONFIRM_SCHEMA_MAXLIMIT_EMPTY = "Maximum limit cannot be empty";
    public static final String CREDIT_CONFIRM_SCHEMA_MAXLIMIT_INVALID = "Enter valid maximum limit";
    public static final String CREDIT_CONFIRM_SCHEMA_ADD_SUCESS = "Schema added successfully";
    public static final String CREDIT_CONFIRM_SCHEMA_DELETE_SUCESS = "Schema deleted successfully";
    public static final String CREDIT_CONFIRM_SCHEMA_CANT_DELETE = "You can only delete the last schema";
    public static final String ERROR_ASSIGN_CREDIT_CONFIRM_SCHEMA = "Error occurred in assigning schema";
    public static final String SUCCESS_ASSIGN_CREDIT_CONFIRM_SCHEMA = "Successfully assigned schema for user role";
    public static final String CREDIT_CONFIRM_SCHEMA_LIMIT_DEFINE_ERROR = "Maximum limit must be greater than the minimum limit";
    public static final String CREDIT_OFFICER_REC_CREDIT_LIMIT_EMPTY_MAIN = "Recommended credit limit is empty";
    public static final String CREDIT_OFFICER_REC_CREDIT_LIMIT_INVALID_MAIN = "Please enter valid credit limit";
    public static final String CREDIT_OFFICER_REC_PRODUCT_EMPTY_MAIN = "Recommended card product is empty";
    public static final String CREDIT_OFFICER_REC_FILE_EMPTY = "Please select a file to upload";
//-----------
//-----------
    public static final String REMARK = "Added a remark";
    //----------------credit score
    public static final String CREDIT_SCORE_SEARCH_INVALID = "Enter at least one parameter to search";
    //----------------------
    // document verify process
    public static final String ERROR_LOAD_DOC_VERIFY = "Error occurred in loading document verification details";
    public static final String ERROR_VERIFY_DOC_VERIFY = "Error occurred in verifying document details";
    public static final String SUCCES_DOC_VERIFY = "Document verified successfully";
    public static final String ERROR_UPLOAD_CRIB_FILE = "File uploading failed";
    public static final String SUCCES_DOC_REJECT = "Document rejected successfully";
    public static final String ERROR_REJECT_DOC = "Error occurred in rejecting document details";
    public static final String ERROR_ALREADY_REJECT = "Application is already rejected";
    public static final String SUCCES_DOC_ONHOLD = "Document on-hold process successful";
    public static final String ERROR_ONHOLD_DOC = "Error occurred in document details on-hold process";
    public static final String EMPLOYEEDETAILS_VERIFY_NOTMATCH = "Employment details should be verified";
    public static final String BOARDRESOLUTION_VERIFY_NOTMATCH = "Board resolution form should be verified";
    public static final String SURETYAGREEMENTFORM_VERIFY_NOTMATCH = "Surety agreement form should be verified";
    public static final String BUSINESSREGCERTIFICATE_VERIFY_NOTMATCH = "Business registration rorm should be verified";

    public static final String SALARYCONFIRM_VERIFY_NOTMATCH = "Salary confirmation details should be verified";
    public static final String EMPNIC_VERIFY_NOTMATCH = "NIC number of employee should be verified";
    public static final String SEVICELENGTH_VERIFY_NOTMATCH = "Length of service should be verified";
    public static final String EMPSTATUS_VERIFY_NOTMATCH = "Employment status should be verified";
    public static final String FULLNAME_VERIFY_NOTMATCH = "Full name should be verified";
    public static final String NICNO_VERIFY_NOTMATCH = "NIC number should be verified";
    public static final String HOMETEL_VERIFY_NOTMATCH = "Home telephone number should be verified";
    public static final String MAILINGADD_VERIFY_NOTMATCH = "Mailing address should be verified";
    public static final String REFERENCE_VERIFY_NOTMATCH = "Reference details should be verified";
    public static final String ADDFROMRESIDENCE_VERIFY_NOTMATCH = "Address of residence should be verified";
    public static final String MOBILENUMBER_VERIFY_NOTMATCH = "Mobile number should be verified";
    public static final String OWNERSHIP_VERIFY_NOTMATCH = "Ownership should be verified";
    public static final String PLACEOFWORK_VERIFY_NOTMATCH = "Place of work should be verified";
    public static final String DETAILSOFREFERENCE_VERIFY_NOTMATCH = "Details of reference should be verified";
    public static final String VEHICLE_VERIFY_NOTMATCH = "Vehicle type should be verified";
    public static final String APPPERSONAL_VERIFY_NOTMATCH = "Applicant personal details should be verified";
    public static final String RELATIONSHIP_VERIFY_NOTMATCH = "Referee relationship with applicant should be verified";
    public static final String RESIDENCE_VERIFY_NOTMATCH = "Residence details of referee should be verified";
    public static final String WORKPLACE_VERIFY_NOTMATCH = "Workplace of referee should be verified";
    public static final String APPLICANTPERSONALFROMREFEREE_VERIFY_NOTMATCH = "Applicant personal details from referee should be verified";
    public static final String APPLICANTADDRESSFROMREFEREE_VERIFY_NOTMATCH = "Applicant address from referee should be verified";
    public static final String COPYIDENTIFICATION_VERIFY_NOTMATCH = "Copy of identification should be verified";
    public static final String SALARYSLIP_VERIFY_NOTMATCH = "Copy of salary slip should be verified";
    public static final String SALARYSLIP1_VERIFY_NOTMATCH = "Copy of salary slip 01 should be verified";
    public static final String SALARYSLIP2_VERIFY_NOTMATCH = "Copy of salary slip 02 should be verified";
    public static final String BANK_STATEMENT_VERIFY_NOTMATCH = "Bank Statement should be verified";
    public static final String STAFF_DEC_VERIFY_NOTMATCH = "Staff declaration form should be verified";
    public static final String CONFIMLETTER_VERIFY_NOTMATCH = "Copy of confirmation letter of employee should be verified";
    public static final String UTILITYBILL_VERIFY_NOTMATCH = "Copy of utility bill should be verified";
    public static final String MARRIAGECERTIFICATE_VERIFY_NOTMATCH = "Copy of marriage certificate should be verified";
    public static final String BIRTHCERTIFICATE_VERIFY_NOTMATCH = "Copy of birth certificate should be verified";
    public static final String CRIBFILE_UPLOAD_FAIL = "CRIB file should be uploaded";
    public static final String RECOMMENDATION_LETTER_DOWNLOAD_FALE = "No recommendation letter found according to this application";
    public static final String APPROVE_CREDITLIMIT_NULL = "Please enter the credit limit";
    public static final String APPROVE_CREDITLIMIT_INVALID = "Invalid credit limit";
    public static final String APPROVE_CARDTYPE_INVALID = "Please select a card type";
    public static final String APPROVE_CARDPRODUCT_INVALID = "Please select a card product";
    public static final String APPROVE_PRODUCTIONMODE_INVALID = "Please select a production mode";
    public static final String APPROVE_BINPROFILE_INVALID = "Please select a bin profile";
    public static final String APPROVE_CUSTOMERTEMPLATE_INVALID = "Please select a customer template";
    public static final String APPROVE_ACCOUNTEMPLATE_INVALID = "Please select an account template";
    public static final String APPROVE_CARDTEMPLATE_INVALID = "Please select a card template";
    public static final String APPROVE_EXCEED_ESTABLISHMENT_CREDITLIMIT = "Establishment credit limit exceeds";
    public static final String APPROVE_EXCEED_MAIN_CREDITLIMIT = "Main credit limit exceeds";
    public static final String APPROVE_ACCEPTED_SUCESS = "Application approved successfully";
    public static final String APPROVE_REJECT_SUCESS = "Application rejected succesfully";
    public static final String APPROVE_REJECTREASON_INVALID = "Please select a reject reason";
    public static final String CIF_NOT_EXIST = "CIF number does not exist";
    //supplementary document verify process
    public static final String MAIN_CARD_VERIFIED = "Main card holder details should be verified";
    public static final String SUPPLEMENTARY_CARD_VERIFIED = "Supplementary card holder details should be verified";
    public static final String BIRTH_CERTIFICATE_VERIFIED = "Birth certificate details should be verified";
    public static final String MARRIAGE_CERTIFICATE_VERIFIED = "Marriage certificate details should be verified";
//------------------------- bank barnch
    public static final String BANK_BRANCH_EMPTY = "Branch code cannot be empty";
    public static final String BANK_BRANCH_INVALID = "Branch code invalid";
    public static final String BRANCH_DESCRIPTION_EMPTY = "Description cannot be empty";
    public static final String BRANCH_DESCRIPTION_INVALID = "Description invalid";
    public static final String ADDRESS_EMPTY = "Address cannot be empty";
    public static final String BANK_EMPTY = "Bank cannot be empty";
    public static final String BANK_INVALID = "Bank invalid";
    public static final String BANK_CON_PER_INVALID = "Enter correct contact person name";
    public static final String BANK_ADDR_INVALID = "Address invalid";
    public static final String CONTACT_PERSOAN_EMPTY = "Contact person cannot be empty";
    public static final String CONTACT_NUMBER_EMPTY = "Contact number cannot be empty";
    public static final String DIS_DIGIT_EMPTY = "Dispaly digit cannot be empty";
    public static final String CONTACT_NUMBER_INVALID = "Contact number invalid";
    public static final String DIS_DIGIT_INVALID = "Dispaly digit invalid";
    public static final String BRANCH_ADD_SUCCESS = "Successfully added branch";
    public static final String BRANCH_DELETE_SUCCESS = "Successfully deleted branch";
    public static final String BRANCH_EDIT_SUCCESS = "Successfully updated branch";
//    
//----------------------------bank
    public static final String BANK_CODE_EMPTY = "Bank code cannot be empty";
    public static final String BANK_CODE_LENGTH = "Bank code length not more than 6";
    public static final String BANK_CODE_ALPHANUMERIC = "Bank code should alphanumeric";
    public static final String BANK_NAME_ALPHANUMERIC = "Enter a valid bank name";
    public static final String BANK_NAME_EMPTY = "Bank name cannot be empty";
    public static final String BANK_STATUS_EMPTY = "Status should be selected";
    public static final String BANK_ADD_SUCCESS = "Successfully added bank";
    public static final String BANK_DELETE_SUCCESS = "Successfully deleted bank";
    public static final String BANK_UPDATE_SUCCESS = "Successfully updated bank";
    public static final String BANK_CURRENCY_UPDATE = "Successfully updated currency details";
    public static final String BANK_CURRENCY_INSERT = "Successfully inserted currency details";
    //
//----------------------------alert
    public static final String ALERT_CODE_EMPTY = "Alert code cannot be empty";
    public static final String ALERT_PORT_NUMERIC = "Alert port should only be numeric";
    public static final String ALERT_PORTTIMEOUT_NUMERIC = "Alert port timeout should be numeric";
    public static final String ALERT_SOCKETTIMEOUT_NUMERIC = "Alert socket timeout should be numeric";
    public static final String ALERT_DES_EMPTY = "Alert name cannot be empty";
    public static final String ALERT_ADD_SUCCESS = "Successfully added alert";
    public static final String ALERT_DELETE_SUCCESS = "Successfully deleted alert";
    public static final String ALERT_UPDATE_SUCCESS = "Successfully updated alert";
//---------------------------- emboss file format
    public static final String CARD_TYPE_EPMTY = "Card type should be selected";
    public static final String CARD_TYPE_EXIST = "Record already exists";
    public static final String FORMAT_CODE_EMPTY = "Format code cannot be empty";
    public static final String FORMAT_CODE_LENGTH = "Format code length not more than 6";
    public static final String FORMAT_CODE_ALPHANUMERIC = "Format code should be alphanumeric";
    public static final String FORMAT_DES_VALID = "Enter valid description";
    public static final String FORMAT_DES_EMPTY = "Format description cannot be empty";
    public static final String FORMAT_DES_LENGTH = "Format description is too long";
    public static final String RECORD_COUNT_EMPTY = "Record count should be selected";
    public static final String RECORD_STATUS_EMPTY = "Status should be selected";
    public static final String FORMAT_FIELD_EMPTY = "All record format fields should be selected";
    public static final String FORMAT_SEPERATOR_EMPTY = "All record format separators should be filled";
    public static final String FIELD_SEPERATOR_EMPTY = "All record format fields and separators should be filled";
    public static final String FORMAT_ADD_SUCCESS = "Successfully added emboss file format";
    public static final String FORMAT_DELETE_SUCCESS = "Successfully deleted emboss file format";
    public static final String FORMAT_UPDATE_SUCCESS = "Successfully updated emboss file format";
    //
    //-----------------------hotlist reason
    public static final String REASON_CODE_EMPTY = "Reason code cannot be empty";
    public static final String REASON_CODE_LENGTH = "Reason code length not more than 6";
    public static final String REASON_CODE_ALPHANUMERIC = "Reason code should only be letters and numbers";
    public static final String DESCRIPTION_EMPTY = "Description cannot be empty";
    public static final String REASON_STATUS_EMPTY = "Status should be selected";
    public static final String REASON_ADD_SUCCESS = "Successfully added hotlist reason";
    public static final String REASON_DELETE_SUCCESS = "Successfully deleted hotlist reason";
    public static final String REASON_UPDATE_SUCCESS = "Successfully updated hotlist reason";
    //
    //------------------------aquire bin management
    public static final String BIN_NUM_EMPTY = "Bin number cannot be empty";
    public static final String BIN_NUM_INVALID = "Bin number is invalid";
    public static final String OWNERSHIP_EMPTY = "Ownership status cannot be empty";
    public static final String PAY_TYPE_EMPTY = "Pay type cannot be empty";
    public static final String SENDING_CHANNEL_EMPTY = "Sending channel cannot be empty";
    public static final String RECEIVING_CHANNEL_EMPTY = "Receiving channel cannot be empty";
    public static final String CARD_TYPE_EMPTY = "Card type cannot be empty";
    public static final String KEY_TYPE_EMPTY = "Card key type must be selected";
    public static final String ENTRY_MODE_EMPTY = "Entry mode cannot be empty";
    public static final String STATUS_EMPTY = "Status cannot be empty";
    public static final String BIN_ADD_SUCCESS = "Successfully added BIN";
    public static final String BIN_UPDATE_SUCCESS = "Successfully updated BIN";
    public static final String BIN_DELETE_SUCCESS = "Successfully deleted BIN";
    //
    //-----------------------transaction profile management
    public static final String PROFILE_CODE_EMPTY = "Profile code cannot be empty";
    public static final String PROFILE_CODE_LENGTH = "Profile code length not more than 8";
    public static final String PROFILE_CODE_ALPHANUMERIC = "Profile code should alphanumeric";
    public static final String PROFILE_DES_INVALID = "Enter valid description";
    public static final String PROFILE_DESCRIPTION_EMPTY = "Description cannot be empty";
    public static final String PROFILE_STATUS_EMPTY = "Status should be selected";
    public static final String TRAN_ASSIGN_LIST_EMPTY = "Assigned list cannot be empty";
    public static final String PROFILE_ADD_SUCCESS = "Successfully added profile";
    public static final String PROFILE_DELETE_SUCCESS = "Successfully deleted profile";
    public static final String PROFILE_UPDATE_SUCCESS = "Successfully updated profile";
    //
    //-----------------------billing statement profile management
    public static final String GRACE_PERIOD_EMPTY = "Grace period cannot be empty";
    public static final String GRACE_PERIOD_NUM = "Enter valid grace period ";
    public static final String MIN_DUE_FLAT_AMT_EMPTY = "Minimum due flat amount cannot be empty";
    public static final String MIN_DUE_FLAT_AMT_ISNUM = "Enter valid minimum due flat amount";
    public static final String MIN_DUE_PERCENTAGE_EMPTY = "Minimum due percentage cannot be empty";
    public static final String MIN_DUE_PERCENTAGE_ISNUM = "Enter valid minimum due percentage";
    public static final String COMBINATION_EMPTY = "Select combination, min or max";
    public static final String STATEMENT_STATUS_EMPTY = "Statement status cannot be empty";
    public static final String BALANCE_EMPTY = "Balance cannot be empty";
    public static final String BALANCE_ISNUM = "Balance should be numeric";
    public static final String CR_DR_EMPTY = "Select credit or debit for balance";
    public static final String ACTIVITY_EMPTY = "Number of activity cannot be empty";
    public static final String ACTIVITY_ISNUM = "Number of activity should be a number";
    //
    //------------------------call center search
    public static final String SEARCH_TYPE_EMPTY = "Select a search type ";
    public static final String ID_TYPE_EMPTY = "Select an identification type ";
    public static final String ALL_FIELDS_EMPTY = "Fill atleast one field to search ";
    public static final String AD_FIELD_EMPTY = "Enter value to search ";
    public static final String INVALID_NIC = "NIC number is invalid";
    public static final String IDTYPENUMEMPTY = "Both identification type and number should be filled";
    public static final String IDTYPENUMDIF = "Identification type is not correct";
    public static final String NO_RECORD_EXIST = "No records exist";
    public static final String CARD_ALRDY_BLK = "This card is already blocked.";
    public static final String SUCCESS_BLOCK = "Successfully blocked";
    public static final String SUCCESS_APPROVE = "Successfully approved";
    public static final String FAIL_APPROVE = "Approve failed";
    public static final String SUCCESS_REPLACE_REQUEST = "Successfully sent a request";
    public static final String SUCCESS_CHANGE_REQUEST = "Successfully sent a card change request";
    public static final String CARD_NOT_BLK = "This card is not blocked";
    public static final String RENEW_CONFIRM_EMPTY = "Renewal confirmation cannot be empty";
    public static final String PRIORITY_EMPTY = "Priority level cannot be empty";
    public static final String REMARKS_EMPTY = "Remarks cannot be empty";
    public static final String REMARKS_INVALID = "Remarks contain invalid charcters";
    public static final String REASON_EMPTY = "Reason cannot be empty";
    public static final String SUCCESS_CLOSE = "Successfully closed";
    public static final String CARD_NOT_EXIST = "No such card";
    public static final String REQ_BEFORE = "A request for card close has been sent previously for this number";
    public static final String ERROT_LOAD_CARD = "Error occurred in loading card details ";
    public static final String BLOCK_NEW_STATUS_EMPTY = "Card new status cannot be empty";
    public static final String BLOCK_REASON_EMPTY = "Card block reason cannot be empty";
//----------------------
//
    // card product management
    public static final String CARD_PRODUCTCODE_NULL = "Card product code cannot be empty";
    public static final String CARD_PRODUCTCODE_INVALID = "Enter valid card product code";
    public static final String CARD_PRODUCTDESCRIPTION_NULL = "Card product description cannot be empty";
    public static final String CARD_PRODUCTDES_INVALID = "Enter valid product description";
    public static final String CARD_PRODUCTSTATUS_NULL = "Status cannot be empty";
    public static final String CARD_PRODUCT_DISPLAYDIGIT_NULL = "Display digit cannot be empty";
    public static final String CARD_PRODUCT_DISPLAYDIGIT_INVALID = "Invalid display digit";
    public static final String CARD_PRODUCT_DISPLAYDIGIT_INVALID_LENGTH = "Display digit must be a 3 digit number";
    public static final String CARD_PRODUCT_NULL_FEEPROFILE = "Please select a fee profile ";
    public static final String CARD_PRODUCT_NULL_INTERESTPROFILE = "Please select a interest profile ";
    public static final String CARD_CARDTYPE_NULL = "Card type cannot be empty";
    public static final String CARD_CARDDOMAIN_NULL = "Card domain cannot be empty";
    public static final String CARD_PRODUCT_SUCCESS_ADD = "Card product successfully added";
    public static final String ERROR_ADD_CARD_PRODUCT = "Error occurred in addding card product";
    public static final String ERROR_DELETE_CARD_PRODUCT = "Error occurred in deleting card product";
    public static final String ERROR_LOAD_CARD_PRODUCT = "Error occurred in loading card products";
    public static final String ERROR_LOAD_LETTER_TEMPLATE = "Error occurred in loading letter templates";
    public static final String CARD_PRODUCT_SUCCESS_DELETE = "Card product successfully deleted";
    public static final String ERROR_VIEW_CARD_PRODUCT = "Error occurred in card product view";
    public static final String CARD_PRODUCT_SUCCESS_UPDATE = "Card product successfully updated";
    public static final String ERROR_UPDATE_CARD_PRODUCT = "Error occurred in updating card product";
    public static final String BIN_NULL = "Please select at least one BIN";
    public static final String IS_ADDED_CARD_KEY_FOR_ALL_BIN = "Please assign card keys for all assigned bins";
    public static final String CARD_KEY_NULL = "Card Key cannot be empty";
    //---------------------card type mgt --------------------------
    public static final String CARD_TYPE_MGT_CODE_EMPTY = "Card type code cannot be empty";
    public static final String CARD_TYPE_MGT_CODE_INVALID = "Card type code invalid";
    public static final String CARD_TYPE_MGT_DESCRIPTION_EMPTY = "Description cannot be empty";
    public static final String CARD_TYPE_MGT_DESCRIPTION_INVALID = "Description invalid";
    public static final String CARD_TYPE_MGT_STATUS_EMPTY = "Status cannot be empty";
    public static final String CARD_TYPE_MGT_ADD_SUCCESS = "Successfully added new card type";
    public static final String CARD_TYPE_MGT_DELETE_SUCCESS = "Successfully deleted card type";
    public static final String CARD_TYPE_MGT_UPDATE_SUCCESS = "Successfully updated card type";
    //
    // card BIN management
    public static final String CARD_BINCODE_NULL = "Card BIN number cannot be empty";
    public static final String CARD_BINCODE_INVALID = "Enter valid card BIN number";
    public static final String CARD_DES_INVALID = "Enter valid card BIN description";
    public static final String CARD_BINDESCRIPTION_NULL = "Card BIN description cannot be empty";
    public static final String CARD_BIN_SUCCESS_ADD = "Card BIN number successfully added";
    public static final String ERROR_LOAD_CARD_BIN = "Error occurred in loading card BIN numbers";
    public static final String CARD_BIN_SUCCESS_UPDATE = "Card BIN number successfully updated";
    public static final String ERROR_UPDATE_CARD_BIN = "Error occurred in updating card BIN number";
    public static final String CARD_BIN_SUCCESS_DELETE = "Card BIN numer successfully deleted";
    public static final String ERROR_DELETE_CARD_BIN = "Error occurred in deleting card BIN number";
    public static final String CARD_ONUS_NULL = "Onus status cannot be empty";
    public static final String CARD_BINTYPE_NULL = "Card domain cannot be empty";
    public static final String CARD_CURRCODE_NULL = "Currency type cannot be empty";
    public static final String CARD_PRODMODE_NULL = "Production mode cannot be empty";
    public static final String CARD_STATUS_NULL = "Status cannot be empty";
    //------credit score recommended 
    public static final String CREDITSCORE_RECOMMEND_ID_EMPTY = "Id cannot be empty";
    public static final String CREDITSCORE_RECOMMEND_ID_NUMERIC = "Id should be numeric";
    public static final String CREDITSCORE_RECOMMEND_ADD_SUCCESS = "Successfully added new credit score recommendation";
    public static final String CREDITSCORE_RECOMMEND_DELETE_SUCCESS = "Successfully deleted credit score recommendation";
    public static final String CREDITSCORE_RECOMMEND_CARDTYPE_EMPTY = "Card tpye cannot be empty";
    public static final String CREDITSCORE_RECOMMEND_CARDPRODUCT_EMPTY = "Card product cannot be empty";
    public static final String CREDITSCORE_RECOMMEND_LOW_LIMIT_EMPTY = "Low limit cannot be empty";
    public static final String CREDITSCORE_RECOMMEND_HIGH_LIMIT_EMPTY = "High limit cannot be empty";
    public static final String CREDITSCORE_RECOMMEND_CREDIT_LIMIT_EMPTY = "Credit limit cannot be empty";
    public static final String CREDITSCORE_RECOMMEND_LOW_LIMIT_NUMERIC = "Low limit should be numeric";
    public static final String CREDITSCORE_RECOMMEND_HIGH_LIMIT_NUMERIC = "High limit should be numeric";
    public static final String CREDITSCORE_RECOMMEND_CREDIT_LIMIT_NUMERIC = "Credit limit should be numeric";
    public static final String CREDITSCORE_RECOMMEND_LOW_LIMIT_INVALID = "Score low limit invalid";
    public static final String CREDITSCORE_RECOMMEND_RANGE_INVALID = "Score high limit should be higher than score low limit";
    public static final String CREDITSCORE_RECOMMEND_HIGH_LIMIT_INVALID = "Score high limit invalid";
    public static final String CREDITSCORE_RECOMMEND_ALREDAY_EXIST = "This card type and card product already exists";
    public static final String CREDITSCORE_RECOMMEND_UPDATE_SUCCESS = "Successfully updated credit score recommendation";
    //--application reject reason
    public static final String APP_REJECT_CODE_EMPTY = "Reason code required";
    public static final String APP_REJECT_CODE_INVALID = "Incorrect reason code";
    public static final String APP_REJECT_DESCRIPTION_EMPTY = "Description required";
    public static final String APP_REJECT_DESCRIPTION_INVALID = "Incorrect description";
    public static final String APP_REJECT_ADD_SUCCESS = "Successfully added new application reject reason";
    public static final String APP_REJECT_UPDATE_SUCCESS = "Successfully updated application reject reason";
    public static final String APP_REJECT_DELETE_SUCCESS = "Successfully deleted application reject reason";
//-----------
//-----------application credit score
    public static final String APP_CREDITSCORE_CALCULATE_SUCCESS = "Credit score of selected applications are updated successfully";
    //-------application confirmation
    public static final String APP_CREDIT_OFFICER_REVIEW_SUCCESS = "Credit officer review completed succesfully";
    //------ card embossing management
    public static final String CARD_EMBOSS_TYPE_NULL = "Card emboss type cannot be empty";
    public static final String BATCHID_INVALID = "Invalid batch id";
    public static final String ERROR_LOAD_CARD_PRODUCTS = "Error occurred in loading card products";
    public static final String ERROR_LOAD_EMBOSS_CARD = "Error occurred in searching card details";
    public static final String ERROR_GENERATE_EMBOSS = "Error occurred in generating emboss file";
    public static final String ERROR_GENERATE_EMBOSS_ALL = "No cards available for generating emboss file";
    public static final String NO_EMBOSS_FORMAT = "Emboss file format not found";
    public static final String ERROR_GENERATE_EMBOSS_SELECTED = "No cards selected for generating emboss file";
    public static final String SUCCESS_GENERATE_EMBOSS_SELECTED = "Emboss files generated successfully";
    public static final String ERROR_DOWNLOAD_EMBOSS_FILE = "Error occurred in downloading emboss file";
    public static final String ALREADY_DOWNLOAD_EMBOSS_FILE = "Emboss file already downloaded";
//-----------
//-----------------Transaction Type Management-----
    public static final String TRANSACTION_TYPECODE_NULL = "Transaction type code field cannot be empty";
    public static final String TRANSACTION_TRANSACTIONCODE_INVALID = "Transaction code must be a alphanumeric";
    public static final String TRANSACTION_MTI_NULL = "MTI field cannot be empty";
    public static final String TRANSACTION_MTI_INVALID = "MTI must be a numeric";
    public static final String TRANSACTION_PROCESSINGCODE_NULL = "Transaction processing code field cannot be empty";
    public static final String TRANSACTION_PROCESSINGCODE_INVALID = "Transaction processing code must be a numeric ";
    public static final String TRANSACTION_DESCRIPTION_NULL = "Description field cannot be empty";
    public static final String TRANSACTION_VISACODE_NULL = "VISA code field cannot be empty";
    public static final String TRANSACTION_VISACODE_INVALID = "VISA code must be alphanumeric";
    public static final String TRANSACTION_MASTERCODE_NULL = "MASTER code field cannot be empty";
    public static final String TRANSACTION_MASTERCODE_INVALID = "MASTER code must be alphanumeric";
    public static final String TRANSACTION_AMEXCODE_NULL = "AMEX code field cannot be empty";
    public static final String TRANSACTION_AMEXCODE_INVALID = "AMEX code must be alphanumeric";
    public static final String TRANSACTION_TYPE_CODE_SUCCESS_ADD = "Successfully added a transaction type code";
    public static final String TRANSACTION_TYPE_SUCCESS_DELETE = "Successfully deleted a tranaction type";
    public static final String TRANSACTIONTYPE_SUCCESS_UPDATE = "Successfully updated";
    public static final String CHANEL_TYPE_EXISTS = "Channel type already exists";
    public static final String MTI_NULL = "MTI cannot be empty";
    public static final String MTI_INVALID = "Invalid MTI";
    public static final String PROCESSING_CODE_NULL = "Processing code cannot be empty";
    public static final String PROCESSING_CODE_INVALID = "Invalid processing code";
    public static final String SAME_MTI_PROCESSING_CODE_EXSIST = "Same MTI & processing code exsist";
    public static final String TRANSACTION_TYPE_NULL = "Transaction type must be selected";
    //---------------Fee Management ------------------------------
    public static final String FEE_SUCCESS_ADD = "Successfully added";
    public static final String FEE_SUCCESS_UPDATE = "Successfully updated";
    public static final String FEE_SUCCESS_DELETED = "Successfully deleted";
    public static final String FEE_CODE_NULL = "Fee Name required";
    public static final String FEE_CODE_INVALID = "Incorrect fee code";
    public static final String FEE_DESCRIPTION_NULL = "Description required";
    public static final String FEE_DESCRIPTION_INVALID = "Incorrect description";
    public static final String FEE_CATEGORY_NULL = "Fee category must be selected";
    public static final String FEE_TYPE_NULL = "Fee type must be selected";
    //public static final String CURRENCY_NULL = "Currency must be selected";
    public static final String CRORDR_NULL = "Credit or debit must be selected";
    public static final String FLAT_FEE_PERCENTAGE_NULL = "Flat fee or percentage required";
    public static final String FLAT_FEE_NULL = "Flat fee required";
    public static final String FLAT_FEE_INVALID = "Incorrect flat fee";
    public static final String PERCENTAGE_NULL = "Percentage required";
    public static final String PERCENTAGE_INVALID = "Incorrect percentage";
    public static final String PERCENTAGE_VALUE_INVALID = "Incorrect percentage value";
    public static final String OPTION_NULL = "Option must be selected";
    public static final String MIN_AMOUNT_NULL = "Minimum amount required";
    public static final String MIN_AMOUNT_INVALID = "Incorrect minimum amount";
    public static final String MAX_AMOUNT_NULL = "Maximum amount required";
    public static final String MAX_AMOUNT_INVALID = "Incorrect maximum amount";
    public static final String MAX_AMOUNT_GRATER_MIN_AMOUNT = "Maximum amount must be greater than minimum amount";
    public static final String NO_TXN_RECORDS = "No transaction fees to add";
    public static final String NO_SER_RECORDS = "No service fees to add";
    //public static final String STATUS_NULL = "Status must be selected";
    //-----------------Channel Configuration-----------
    public static final String CHANNEL_SUCCESS_ADD = "Successfully added a channel";
    public static final String CHANNEL_ID_NULL = "Channel id required";
    public static final String CHANNEL_ID_INVALID = "Incorrect channel id";
    public static final String CHANNEL_NAME_NULL = "Channel name required";
    public static final String CHANNEL_NAME_INVALID = "Invalid channel name";
    public static final String CHANNEL_SUCCESS_DELETE = "Successfully deleted a channel ";
    public static final String CHANNEL_SUCCESS_UPDATE = "Successfully updated a channel";
    public static final String IP_NULL = "IP required";
    public static final String IP_INVALID = "Incorrect IP";
    public static final String HOST_INVALID = "Incorrect Host";
    public static final String PORT_NULL = "Port required";
    public static final String PORT_INVALID = "Incorrect port";
    public static final String TIMEOUT_NULL = "Timeout required";
    public static final String TIMEOUT_INVALID = "Incorrect time out";
    public static final String CONNECTION_TYPE_NULL = "Connection type must be selected";
    public static final String CHANNEL_TYPE_NULL = "Channel type must be selected";
    public static final String OPERATION_TYPE_NULL = "Operation type must be selected";
    public static final String ECHO_STATUS_NULL = "Echo status must be selected";
    public static final String SIGNON_STATUS_NULL = "Sign-on status must be selected";
    public static final String DYNAMIC_KEY_EXCHANGE_STATUS_NULL = "Dynamic key exchange status must be selected";
    public static final String DYNAMIC_KEY_EXCHANGE_PERIOD_NULL = "Dynamic key exchange period required";
    public static final String DYNAMIC_KEY_EXCHANGE_PERIOD_INVALID = "Incorrect dynamic key exchange period";
    public static final String ECHO_PERIOD_NULL = "Echo period required";
    public static final String ECHO_PERIOD_INVALID = "Incorrect echo period";
    public static final String STATUS_NULL = "Status must be selected";
    public static final String ECHO_DIRECTION_NULL = "Echo direction must be selected";
    public static final String KEY_EXCHANGE_DIRECTION_NULL = "Key exchange direction must be selected";
    public static final String HDES_ID_NULL = "HDES id required";
    public static final String HDES_ID_INVALID = "Incorrect HDES id";
    public static final String HSRC_ID_NULL = "HSRC id required";
    public static final String HSRC_ID_INVALID = "Incorrect HSRC id";
    public static final String ASCII32_ID_NULL = "ASCII32 id required";
    public static final String ASCII32_ID_INVALID = "Incorrect ASCII32 id";
    public static final String FIIC33_ID_NULL = "FIIC33 id required";
    public static final String FIIC33_ID_INVALID = "Incorrect FIIC33 id";
    //-----------------Channel Admin Messages-----------
    public static final String CHANNEL_ADMIN_MSG_SUCCESS = "Network message successful";
    public static final String CHANNEL_ADMIN_MSG_FAIL = "Network message unsuccessful";
    public static final String CHANNEL_ADMIN_MSG_INVALID_REQUEST = "Invalid Request";
    public static final String CHANNEL_ADMIN_MSG_ONLINE_SERVER_NOT_AVAILABLE = "Online server not available";
    public static final String CHANNEL_ADMIN_MSG_INVALID_ROUTER_TO_ENGINE = "Invalid Router to Engine Message Size";
    public static final String CHANNEL_ADMIN_MSG_INVALID_CONNECTION_TYPE = "Invalid Connection Type";
    public static final String CHANNEL_ADMIN_MSG_INVALID_REQ_RESPONSE = "Invalid Request Response Code-Apart from online server message, Web request\n"
            + "message is also validated and sent response if invalid request code found.";
    public static final String CHANNEL_ADMIN_MSG_INVALID_SOURSE_TYPE = "Invalid Source Type-Apart from online server message, Web request message is also\n"
            + "validated and sent response if invalid source type found.";
    public static final String CHANNEL_ADMIN_MSG_INVALID_OPERATION_CODE = "Invalid Operation Code-Apart from online server message, Web request message is\n"
            + "also validated and sent response if invalid operation code found.";
    public static final String CHANNEL_ADMIN_MSG_INVALID_WEROUTER_MESSAGE = "Invalid Web to Router message size-Web request message is validated and sent\n"
            + "response if invalid sized request message found.";

//----------Listener Configuration --------------------------- 
    public static final String LISTENERID_NULL = "Listener id required";
    public static final String LISTENERID_INVALID = "Incorrect listener id";
    public static final String LISTENER_TYPE_NULL = "Listener type must be selected";
    public static final String NUMBER_OF_CONNECTION_NULL = "Number of connections required";
    public static final String NUMBER_OF_CONNECTION_INVALID = "Incorrect number of connections";
    public static final String LISTENER_SUCCESS_ADD = "Successfully added a listener";
    public static final String LISTENER_SUCCESS_UPDATE = "Successfully updated a listener";
    public static final String LISTENER_SUCCESS_DELETE = "Successfully deleted a listener";
    public static final String PER_PORT_NULL = "Establish port required";
    public static final String PER_PORT_INVALID = "Incorrect Establish port";
    public static final String TER_PORT_NULL = "Terminating port required";
    public static final String TER_PORT_INVALID = "Incorrect terminating port";
    public static final String KEY_EXCHANGE_STATUS_NULL = "Key exchange status must be selected";
    public static final String RUNNING_MODE_NULL = "Running mode must be selected";
    public static final String RER_PORT_TER_PORT_SAME = "Establish port and terminating port cannot be same";
    public static final String KEY_ID_NULL = "Key id must be selected";
    public static final String SECURITY_STATUS_NULL = "Security status must be selected";
    public static final String STATUS_OF_ACUIRING_NULL = "Status of acquiring must be selected";
//--------numberformat generation.....
    public static final String NUMBER_FORMAT_ERROR_CAT_CHANGE = "Please select an end of range before selecting format";
    public static final String NUMBER_FORMAT_CODE_NULL = "Number format code cannot be empty";
    public static final String NUMBER_FORMAT_DESC_NULL = "Number format description cannot be empty";
    public static final String NUMBER_FORMAT_DESC_INVALID = "Number format description invalid";
    public static final String NUMBER_FORMAT_CODE_INVALID = "Number format code invalid";
    public static final String CARD_TYPE_NULL = "Card type cannot be empty";
    public static final String CARDNUMABER_INVALID = "Invalid card number";
    public static final String IDENTIFICATION_INVALID = "Invalid identification number";
    public static final String PRODUCTION_MODE_NULL = "Production mode cannot be empty";
    public static final String ASSIGNED_BIN_NULL = "Select at least one BIN";
    public static final String CARD_NUMBER_LENGTH_NULL = "Number length cannot be empty";
    public static final String CARD_NUMBER_INVALID = "Number length should be numeric";
    public static final String CARD_TYPE_INVALID = "Card type invalid";
    public static final String INVALID_FORMAT_TYPE = "Please select a format type";
    public static final String SEQ_FORMAT_ERROR = "Range for the sequence format should be ";
    public static final String NO_SEQ_FORMAT = "Sequence format should be there";
    public static final String USED_BIN_REMOVE = "Cannot remove already used BINs";
    public static final String TWO_SEQ_FORMATS = "Cannot have more than one sequence format ";
    public static final String ERROR_NUM_GEN = "Error occurred in loading number format ";
    //-------Merchant Customer Management
    public static final String SEARCH_COMPLETE = "Search Complete";
    public static final String MERCHANT_CUSTOMER_SUCCESS_ADD = "Merchant customer added successfully. Account number is ";
    public static final String MERCHANT_CUSTOMER_ASSIGN_SUCCESS = "Assigned data successfully";
    public static final String MERCHANT_CUSTOMER_NUMBER_NULL = "Merchant customer number required";
    public static final String MERCHANT_CUSTOMER_NUMBER_INVALID = "Incorrect merchant customer number";
    public static final String MERCHANT_CUSTOMER_NAME_NULL = "Merchant customer name required";
    public static final String MERCHANT_CUSTOMER_NAME_INVALID = "Incorrect merchant customer name";
    public static final String LEGAL_NAME_NULL = "Legal name required";
    public static final String LEGAL_NAME_INVALID = "Incorrect legal name ";
    public static final String ADDRESS1_NULL = " Address required";
    public static final String ADDRESS_INVALID = "Incorrect address";
    public static final String ADDRESS_FIELD1_INVALID = " Address field 01 incorrect address";
    public static final String ADDRESS_FIELD2_INVALID = " Address field 02 incorrect address";
    public static final String ADDRESS_FIELD3_INVALID = " Address field 03 incorrect address";
    public static final String AREA_NULL = "Area must be selected";
    public static final String POSTAL_CODE_NULL = "Postal code must be selected";
    public static final String COUNTRY_NULL = "Country must be selected";
    public static final String TITLE_NULL = "Title must be selected";
    public static final String FNAME_NULL = "First name required ";
    public static final String LNAME_NULL = "Last name required ";
    public static final String NAME_INVALID = "Incorrect name ";
    public static final String FNAME_INVALID = "Incorrect first name ";
    public static final String MNAME_INVALID = "Incorrect middle name ";
    public static final String LNAME_INVALID = "Incorrect last name ";
    public static final String TP_NUMBER_NULL = "TP number required";
    public static final String EMAIL_NULL = "Email address required";
    public static final String FAX_NULL = "Fax number required";
    public static final String TP_NUMBER_INVALID = "Incorrect TP number";
    public static final String FAX_NUMBER_INVALID = "Incorrect fax number";
    public static final String EMAIL_INVALID = "Invalid email address";
    public static final String BANK_NAME_NULL = "Bank name must be selected";
    public static final String BRANCH_NAME_NULL = "Branch name must be selected";
    public static final String COMMISION_PROFILE_NULL = "Commission profile should be selected";
    public static final String ACCOUNT_NUMBER_NULL = "Account number field cannot be empty";
    public static final String ACCOUNT_NUMBER_INVALID = "Account number invalid";
    public static final String ACCOUNT_TYPE_NULL = "Account type must be selected";
    public static final String ACCOUNT_NAME_NULL = "Account name required ";
    public static final String ACCOUNT_NAME_INVALID = "Incorrect account name";
    public static final String PAYMENT_MODE_NULL = "Payment mode must be selected";
    public static final String PAYMENT_CYCLE_NULL = "Payment cycle must be selected";
    public static final String STATEMENT_CYCLE_NULL = "Statement cycle must be selected";
    public static final String APPLICATION_DATE_NULL = "Application date must be selected ";
    public static final String ACTIVATION_DATE_NULL = "Activation date must be selected";
    public static final String APPLICATION_DATE_BEFORE_ACTIVATION_DATE = "Application date must be a date before the activation date";
    public static final String MERCHANT_CUSTOMER_SUCCESS_UPDATE = "Successfully updated";
    public static final String MERCHANT_CUSTOMER_SUCCESS_DELETE = "Successfully deleted merchant customer";
    public static final String INVALID_SEARCH_LETTERS = "Invalid search characters";
    public static final String REDEM_POINT_NULL = "Redemption point required";
    public static final String REDEM_POINT_INVALID = "Incorrect redemption point";
    //----Merchant Location Management
    public static final String MERCHANT_LOCATION_SUCCESS_ADD = "Merchant location added successfully. Account number is ";
    public static final String MERCHANT_LOCATION_ASSIGN_SUCCESS = "Assigned data successfully";
    public static final String MERCHANT_LOCATION_ID_NULL = "Merchant id required";
    public static final String MERCHANT_LOCATION_ID_INVALID = "Incorrect merchant id";
    public static final String MERCHANT_LOCATION_DESCRIPTION_NULL = "Merchant location description required";
    public static final String MERCHANT_LOCATION_DESCRIPTION_INVALIED = "Incorrect merchant location description ";
    public static final String POSITION_NULL = "Position required";
    public static final String POSITION_INVALID = "Incorrect position";
    public static final String MERCHANT_CUSTOMER_NAME_SELECT_NULL = "Merchant customer name must be selected";
    public static final String MERCHANT_LOCATION_SUCCESS_DELETE = "Successfully deleted merchant location";
    public static final String MERCHANT_LOCATION_SUCCESS_UPDATE = "Successfully updated";
    public static final String MERCHANT_LOCATION_MANUAL_TXN_STATUS_NULL = "Manual auth status must be selected";
    public static final String MERCHANT_LOCATION_MANUAL_TERMINAL_ADD = "Manual terminal successfully added";
    public static final String MERCHANT_LOCATION_MANUAL_TERMINAL_UPDATE = "Manual terminal successfully updated";
    public static final String TRANSACTION_NULL = "Select at least one transaction";
    public static final String DEFAULT_MERCHANT_CANNOT_INSERT = "Default merchant Id cannot be inserted";
    //switch control panel
    public static final String INVALID_ZMK_LENGTH = "Invalid zone master key length";
    public static final String INVALID_ZMK = "Invalid key. Value should be in hexadecimal format";
    public static final String COMUNICATION_ERROR = "An error occurred while communicating with the security module";
    public static final String UNSUCCESSFUL_RESPONSE = "An unsuccessful response from security module";
    public static final String CHANEL_NOT_AVAILABLE = "Channel not available";
    public static final String SERVER_START_SUCSESS = "Successfully started the ECMS server";
    public static final String SERVER_START_FAIL = "An error occurred while starting the ECMS server";
    public static final String SERVER_RESTART_SUCSESS = "Successfully restarted the ECMS server";
    public static final String SERVER_RESTART_FAIL = "An error occurred while restarting the ECMS server";
    public static final String SERVER_STOP_SUCSESS = "Successfully stopped the ECMS server";
    public static final String SERVER_STOP_FAIL = "An error occurred while stopping the ECMS server";
    public static final String SERVER_CLEAR_LOG_SUCSESS = "Successfully cleared logs";
    public static final String SERVER_CLEAR_LOG_FAIL = "An error occurred while clearing logs";
    public static final String SERVER_CLEAR_ALL_LOG_SUCSESS = "Successfully cleared all logs";
    public static final String SERVER_CLEAR_ALL_LOG_FAIL = "An error occurred while clearing all logs";
    public static final String SERVER_BACKUP_LOG_SUCSESS = "Successfully archived logs";
    public static final String SERVER_BACKUP_LOG_FAIL = "An error occurred while archiving logs";
    public static final String SERVER_REMOVE_BACKUP_LOG_SUCSESS = "Successfully removed archives";
    public static final String SERVER_REMOVE_BACKUP_LOG_FAIL = "An error occurred while removing archives";
    public static final String SYSTEM_RESTART_SUCSESS = "Successfully restarted the system";
    public static final String SYSTEM_RESTART_FAIL = "An error occurred while restarting the system";
    public static final String SYSTEM_SHUTDOWN_SUCSESS = "System shutdown successful";
    public static final String SYSTEM_SHUTDOWN_FAIL = "An error occurred while shutting down the system";
//pin generation messages
    public static final String INVALID_CARD_NUMBER = "Invalid key card number";
    public static final String ERROR_PVV_GEN = "An error occurred while generating Pin Block";
    public static final String ERROR_CVV_GEN = "An error occurred while generating CVV";
    public static final String ERROR_CARDNO_NOT_IN_ONLINE_DB = "Card number not in the online database";
    public static final String DBCONNECTION_ERROR = "Database connection error";
    public static final String SUCCES_PIN_AND_PVC_CVV = "Sucesfully generated PIN for card number ";
    public static final String SUCCES_PIN_MAIL = "Successfully printed PIN mailer";
    public static final String INVALID_PIN_MAIL_REQUEST = "Please connect the printer";
    public static final String INVALID_HSM_REQUEST = "Invalid HSM request";
    public static final String INSUFICIANT_DATA = "Insufficient data to mail the PIN";
    public static final String INSUFICIANT_DATA_TO_PINGEN = "Insufficient data to generate the PIN";
    //bulk pin generation messages
    public static final String BULK_PINGEN_SUCSESS1 = "Successfully generated PIN numbers for ";
    public static final String BULK_PINGEN_SUCSESS2 = "out of ";
    public static final String BULK_PINGEN_SUCSESS3 = "cards";
    public static final String BULK_PINMAIL_SUCSESS1 = "Successfully generated PIN mails for ";
    public static final String BULK_PINGEN_ERROR = "Communication error with security module";
    //common file download
    public static final String ERROR_DOWNLOAD_COMMON_FILE = "Error occurred in downloading the file";
    public static final String ERROR_COMMON_FILE_NAME = "Incorrect file name";
    public static final String ALREADY_DOWNLOAD_FILE = "File already downloaded";
    public static final String NO_FILE_DOWNLOAD = "File not exist";
    public static final String TERMINAL_ALREADY_ALLOCATED = "Terminal already allocated";
    public static final String TERMINAL_ALREADY_ACTIVATED = "Terminal already activated";
    public static final String CANTACTIVATE = "Cannot activate. Terminal is still not allocated";
    public static final String TERMINAL_SUCSESS_ALLOCATED = "Terminal successfully allocated";
    public static final String TERMINAL_SUCSESS_DALLOCATED = "Terminal successfully deallocated";
    public static final String TERMINAL_ERROR_ALLOCATED = "An error occured while allocationg the terminal";
    public static final String TERMINAL_NOTACTIVE = "Terminal is not in active status";
    public static final String MERCHANT_NOTACTIVE = "Merchant location is not in active status";
    public static final String TERMINAL_ACTIVE = "Cannot allocate or de-allocate. This is an active terminal";
    public static final String TERMINAL_ALREADY_NOT_ALLOCATED = "Terminal already de-allocated";
    public static final String TERMINAL_ERROR_DALLOCATED = "An error occured while  de-allocationg the terminal";
    public static final String TERMINAL_SUCSESS_ACTIVATED = "Terminal successfully activated";
    public static final String TERMINAL_SUCSESS_BLOCK = "Terminal successfully blocked";
    public static final String TERMINAL_SUCSESS_DEACTIVATED = "Terminal successfully deactivated";
    public static final String TERMINAL_ACTIVATION_FAIL = "Terminal activation failed";
    public static final String TERMINAL_BLOCK_FAIL = "Terminal blocking failed";
    public static final String TERMINAL_DEACTIVATION_FAIL = "Terminal de-activation failed";
    public static final String CANTDEACTIVATE = "Cannot De-activate. Terminal is not allocated";
    public static final String MCC_NULL = "Mcc must be selected";
    public static final String CURRENCY_NULL = "Currency type must be selected";
    public static final String TRANSACTION_NOT_SETTLED = "There are unsettled transaction of this terminal";
    //risk Profile
    public static final String RISK_PROFILE_SUCCESS_ADD = "Successfully added";
    public static final String RISK_PROFILE_ASSIGN_SUCCESS = "Assigned data successfully";
    public static final String RISK_PROFILE_SUCCESS_UPDATE = "Successfully updated";
    public static final String RISK_PROFILE_SUCCESS_DELETE = "Successfully deleted";
    public static final String CANT_BLOCK_NOTALLOCATED = "Cannot block. Terminal is not allocated";
    public static final String RISK_PROFILE_CODE_NULL = "Risk profile code required";
    public static final String RISK_PROFILE_CODE_INVALID = "Incorrect risk profile code";
    public static final String DESCRIPTION_NULL = "Description required";
    public static final String DESCRIPTION_INVALID = "Incorrect description";
    public static final String PROFILE_TYPE_NULL = "Profile type must be selected";
    public static final String ACCOUNT_PROFILE_CODE_NULL = "Account profile must be selected";
    public static final String CUSTOMER_PROFILE_CODE_NULL = "Customer profile must be selected";
    public static final String MERCHANT_PROFILE_CODE_NULL = "Merchant profile must be selected";
    public static final String PERIOD_NULL = "Period required";
    public static final String PERIOD_INVALID = "Incorrect period";
    public static final String MINIMUM_SINGLE_TXN_LIMIT_NULL = "Minimum single transaction limit required";
    public static final String MINIMUM_SINGLE_TXN_LIMIT_INVALID = "Incorrect minimum single transaction limit";
    public static final String MINIMUM_SINGLE_CASH_LIMIT_NULL = "Minimum single cash transaction limit required";
    public static final String MINIMUM_SINGLE_CASH_LIMIT_INVALID = "Incorrect minimum single cash transaction limit";
    public static final String MAX_SINGLE_TXN_LIMIT_NULL = "Maximum single transaction limit required";
    public static final String MAX_SINGLE_TXN_LIMIT_INVALID = "Incorrect maximum single transaction limit";
    public static final String MAX_SINGLE_CASH_LIMIT_NULL = "Minimum single cash transaction limit required";
    public static final String MAX_SINGLE_CASH_LIMIT_INVALID = "Incorrect minimum single cash transaction limit";
    public static final String MAX_TXN_COUNT_NULL = "Maximum transaction count required";
    public static final String MAX_TXN_COUNT_INVALID = "Incorrect maximum transaction count";
    public static final String MAX_TOT_TXN_AMOUNT_NULL = "Maximum total transaction amount required";
    public static final String MAX_TOT_TXN_AMOUNT_INVALID = "Incorrect maximum total transaction amount";
    public static final String MAX_CASH_COUNT_NULL = "Maximum cash transaction count required";
    public static final String MAX_CASH_COUNT_INVALID = "Incorrect maximum cash transaction count";
    public static final String MAX_TOT_CASH_TXN_AMOUNT_NULL = "Maximum total cash transaction amount required";
    public static final String MAX_TOT_CASH_TXN_AMOUNT_INVALID = "Incorrect maximum total cash transaction amount";
    public static final String EXTRA_AUTH_LIMIT_NULL = "Extra authentication limit required ";
    public static final String EXTRA_AUTH_LIMIT_INVALID = "Incorrect extra authentication limit";
    public static final String MAX_PIN_COUNT_NULL = "Maximum PIN count required";
    public static final String MAX_PIN_COUNT_INVALID = "Incorrect maximum PIN count";
    public static final String MAX_SING_TXN_LIMIT_LESS_MIN_SING_TXN_LIMIT = "Maximum single transaction limit must be greater than minimum single transaction limit ";
    public static final String MAX_SING_CASH_LIMIT_LESS_MIN_SING_CASH_LIMIT = "maximum single cash transaction limit must be greater than minimum single cash transaction limit ";
    public static final String PERIOD_LESS_THAN = "Period must be less than ";
    public static final String MIN_SINGLE_TXN_LIMIT_GREATER_THAN = "Minimum single transaction limit must be greater than ";
    public static final String MAX_SINGLE_TXN_LIMIT_LESS_THAN = "Maximum single transaction limit must be less than ";
    public static final String MIN_SINGLE_CASH_LIMIT_GREATER_THAN = "Minimum single cash transaction limit must be greater than ";
    public static final String MAX_SINGLE_CASH_LIMIT_LESS_THAN = "Maximum single cash transaction limit must be less than ";
    public static final String MAX_TXN_COUNT_LESS_THAN = "Maximum transaction count must be less than ";
    public static final String MAX_CASH_COUNT_LESS_THAN = "Maximum cash transaction count must be less than ";
    public static final String TOT_MAX_TXN_AMOUNT_LESS_THAN = "Maximum total transaction amount must be less than ";
    public static final String TOT_MAX_CASH_AMOUNT_LESS_THAN = "Maximum total cash transaction amount must be less than ";
    public static final String EXTRA_AUTH_LIMIT_LESS_THAN = "Extra authorization limit must be less than ";
    public static final String CREATE_CUSTOMER_RISK_PROFILE = "Prerequisite: Customer risk profile should be created";
    public static final String CREATE_ACCOUNT_RISK_PROFILE = "Prerequisite: Account risk profile should be created";
    public static final String CREATE_MERCHANT_RISK_PROFILE = "Prerequisite: Merchant risk profile should be created";
    
    
    //template management account template management
    
    public static final String TEMPLATE_MANAGMNT_ADD_ACCOUNT_TEMP_SUCCESS = "Successfully added account template";


//xcard number format
    public static final String CARD_NUMBER_FORMAT_ADD_SUCCESS = "Successfully added card number format";
    public static final String CARD_NUMBER_FORMAT_DELETE_SUCCESS = "Successfully deleted card number format";
    public static final String CARD_NUMBER_FORMAT_UPDATE_SUCCESS = "Successfully updated card number format";
    public static final String CARD_NUMBER_FORMAT_ADD_UNSUCCESS = "Card number format adding unsuccessful";
    public static final String CARD_NUMBER_FORMAT_UPDATE_UNSUCCESS = "Card number format updating unsuccessful";
    public static final String CARD_NUMBER_FORMAT_DELETE_UNSUCCESS = "Card number format deleting unsuccessful";
    // call center history explorer
    public static final String ERROR_LOAD_CALL_HISTORY = "Error occurred in loading call history details";
    public static final String ERROR_SEARCH_CALL_HISTORY = "Error occurred in searching call history details";
    //transaction explorer 
    public static final String ERROR_LOAD_TXN_EXP = "Error occurred in loading transaction details";
    public static final String ERROR_SEARCH_TXN_EXP = "Error occurred in searching transaction history details";
    //---------Service Code Managemant
    public static final String SERVICECODE_NULL = "Service code cannot be empty";
    public static final String SERVICECODE_INVALID = "Incorrect service code";
    public static final String INTERNATIONAL_STATUS_NULL = "International status must be selected";
    public static final String ATM_STATUS_NULL = "ATM status must be selected";
    public static final String PIN_STATUS_NULL = "PIN status must be selected";
    public static final String AUTH_STATUS_NULL = "Authorisation status must be selected";
    public static final String SERVICE_CODE_SUCCESS_ADD = "Successfully added service code";
    public static final String SERVICE_CODE_SUCCESS_UPDATE = "Successfully updated service code";
    public static final String SERVICE_CODE_SUCCESS_DELETE = "Successfully deleted service code";
    // -----------------Log Level Management
    public static final String LOG_LEVEL_NULL = "Log level must be selected";
    public static final String LOG_FILE_NAME_NULL = "Log file name required";
    public static final String LOG_FILE_NAME_INVALID = "Incorrect log file name";
    public static final String LOG_BACKUP_PATH_NULL = "Log backup path required";
    public static final String LOG_BACKUP_PATH_INVALID = "Incorrect log backup path";
    public static final String LOG_BACKUP_STATUS_NULL = "Log backup status must be selected";
    public static final String SYN_STATUS_NULL = "Synchronisation status must be selected";
    public static final String SYN_PERIOD_NULL = "Synchronisation period required";
    public static final String SYN_PERIOD_INVALID = "Incorrect synchronisation period";
    public static final String LOG_MANAGEMENT_SUCCESS_UPDATE = "Successfully updated";
    //public static final String NUM_OF_LOG_FILE_NULL = "";
    public static final String NUM_OF_LOG_FILE_INVALID = "Incorrect number of log files";
    // -----------------Response Code Management-----------------
    public static final String RESPONCE_CODE_NULL = "Response code required";
    public static final String RESPONCE_CODE_INVALID = "Incorrect response code";
    public static final String RESPONCE_CODE_SUCCESS_ADD = "Successfully added response code";
    public static final String RESPONCE_CODE_SUCCESS_UPDATE = "Successfully updated response code";
    public static final String RESPONCE_CODE_SUCCESS_DELETE = "Successfully deleted response code";
    // ------------------Response Code Mapping ------------------------
    public static final String DB_CODE_NULL = "DB code required";
    public static final String DB_CODE_INVALID = "Incorrect DB code";
    public static final String RESPONCECODE_NULL = "Response code must be selected";
    public static final String DB_CODE_SUCCESS_ADD = "Successfully added DB code";
    public static final String DB_CODE_SUCCESS_UPDATE = "Successfully updated DB code";
    public static final String DB_CODE_SUCCESS_DELETE = "Successfully deleted DB code";
    //quesion verify
    public static final String ERROR_IN_QUESTION = "Error occurred in question loading";
    // ------------------Pin & Card Distribution --------------------------
    public static final String CARD_DISTRIBUTION_SUCCESS = "Card distribution is approved";
    public static final String PIN_DISTRIBUTION_SUCCESS = "PIN distribution is approved";
    public static final String SELECTED_CARD_NULL = "Select at least one card";
    public static final String SELECTED_PIN_NULL = "Select at least one PIN";
    // ----------------Card Quality Mgt ------------------------------------------
    public static final String CARD_NUMBER_NULL = "Enter the card number";
    public static final String CARD_NUMBER_NOT_EXIST = "This card number does not exist";
    public static final String REMARK_NULL = "Remark required";
    public static final String REMARK_INVALID = "Incorrect remark";
    public static final String QUALITY_FAIL_SUCCESS = "Quality failed";
    // ---------------Temporary credit & cash Limit increment -----------------------
    public static final String CREDIT_CASH_NULL = "Credit or cash must be selected";
    public static final String AMOUNT_RATE_NULL = "Amount or rate required";
    public static final String AMOUNT_RATE_BOTH_ENTERED = "Insert either amount or rate";
    public static final String AMOUNT_INVALID = "Invalid amount";
    public static final String RATE_INVALID = "Invalid rate";
    public static final String START_DATE_NULL = "Start date required";
    public static final String INCORDEC_NULL = "Select Increment or decrement";
    public static final String START_DATE_INVALID = "Invalid start date";
    public static final String END_DATE_NULL = "End date required";
    public static final String END_DATE_INVALID = "Invalid end date";
    public static final String START_DATE_AFTER_END_DATE = "Start date must be before the end date";
    public static final String CREDIT_LIMIT_INCREMENT_SUCCESS = "Credit limit was increased";
    public static final String CREDIT_LIMIT_INCREMENT_REQUEST_SUCCESS = "Credit limit increment request is sent ";
    public static final String CASH_LIMIT_INCREMENT_SUCCESS = "Cash limit was increased";
    public static final String NO_LIMIT_INCREMENT_SUCCESS = "Limit cannot be changed for this card";
    public static final String CASH_LIMIT_INCREMENT_REQUEST_SUCCESS = "Cash limit increment request is sent";
    public static final String CREDIT_LIMIT_LESS_THAN_CASH_LIMIT = "Cash limit cannot be greater than credit limit";
    public static final String LIMIT_INCREMENT_ALREADY_TAKEN = "Already taken a";
    public static final String LIMIT_INCREMENT_REQUESTED = "Already requested a";
    public static final String REQUESTED_CREDIT_LIMIT_HIGHERTHAN_COMMON_VALUE = "The maximum credit increment amount is";
    public static final String REQUESTED_CREDIT_LIMIT_DEC_INVALID = "The maximum credit decrement amount is";
    public static final String REQUESTED_CASH_LIMIT_DEC_INVALID = "The maximum cash decrement amount is";
    public static final String REQUESTED_CASH_LIMIT_HIGHERTHAN_COMMON_VALUE = "The maximum cash increment amount is";
    public static final String TOTAL_CASH_LIMIT_HIGHERTHAN_CREDIT_LIMIT = "Credit limit is exceeded, amount must be less than";
    public static final String DUAL_AUTH_FAIL = "Invalid dual authentication user name or password";
    public static final String CARD_NOT_ACTIVE_STATE = "This is not an active card";
    // eod file generation....
    public static final String ERROR_GENE_EOD_FILE = "Error occurred in generating EOD file";
    public static final String ERROR_LOAD_EOD_FILE = "Error occurred in loading EOD file details";
    public static final String NO_TXN_EOD_FILE = "No transactions are available for generating EOD file";
    public static final String SUCCESS_EOD_FILE = "EOD file generated successfully";
    // card Activation
    public static final String CARD_CANNOT_ACTIVATED = "Card cannot be activated";
    //debit card verification
    public static final String ERROR_DOCID_NOT_CHECK = "Copy of NIC should be verified";
    public static final String ERROR_ACCNO_NOT_CHECK = "Account number should be verified";
    public static final String ERROR_ID_NOT_CHECK = "ID number should be verified";
    public static final String ERROR_NAME_NOT_CHECK = "Name should be verified";
    // Debit Card Application
    public static final String INVALID = "Invalid";
    public static final String REQUIRED = "Required";
    public static final String APPLICATION_SUCCESS_ADD = "Data capturing successful for debit card application";
    public static final String ERROR_UPLOAD_SIGNETURE = "Error occurred in uploading signature";
    public static final String IMAGE_SIZE_TOO_LARGE = "File size is too large";
    public static final String FILE_UPLOADED = "File uploaded";
    //*********************************Pre Personalization Module*****************************************************************************
    public static final String ERROR_LOAD_BLK_CD_REQ = "Error occurred in loading bulk card request";
    public static final String CD_DOMAIN_NULL = "Card domain cannot be empty";
    public static final String CD_TYPE_NULL = "Card type cannot be empty";
    public static final String CD_PRODUCT_NULL = "Card product cannot be empty";
    public static final String CD_CURRENCY_NULL = "Currency cannot be empty";
    public static final String BRANCH_NULL = "Branch name cannot be empty";
    public static final String PRIO_LVL_NULL = "Priority level cannot be empty";
    public static final String PROD_MODE_NULL = "Production mode cannot be empty";
    public static final String NUM_CD_NULL = "Number of cards cannot be empty";
    public static final String MAX_CDS = "Maximum number of cards is 1000";
    public static final String APPRVD_BATCH = "This batch cannot be deleted";
    public static final String NO_UPDATE = "This batch cannot be updated";
    // Bulk Card Number Generation
    public static final String NO_MATCH_FOUND = "No match found";
    public static final String BULK_CARD_NUM_GEN_ERROR = "Error occurred in number generation processs ";
    // application capture and modify
    public static final String ADD_PERSONAL_SUCCESS = "Customer personal details added successfully";
    public static final String ADD_ESTABLISHMENT_SUCCESS = "Establishmnent details added successfully";
    public static final String UPDATE_ESTABLISHMENT_SUCCESS = "Establishment details updated successfully";
    public static final String ADD_COMPANY_SUCCESS = "Company details added successfully";
    public static final String ADD_PERSONAL_ERROR = "Failed adding customer personal details.";
    public static final String ADD_ESTABLISHMENT_ERROR = "Failed adding establishment personal details.";
    public static final String ADD_COMPANY_ERROR = "Failed adding company details";
    public static final String ADD_EMPLOY_SUCCESS = "Customer employment details added successfully";
    public static final String ADD_EMPLOY_ERROR = "Failed adding customer employment details.";
    public static final String ADD_INCOME_SUCCESS = "Customer income and expense details added successfully";
    public static final String ADD_INCOME_ERROR = "Failed adding customer income and expense details.";
    public static final String ADD_BANK_SUCCESS = "Bank details added successfully";
    public static final String ADD_MANAGER_SUCCESS = "Manager details added successfully";
    public static final String ADD_BANK_ERROR = "Failed adding customer bank details";
    public static final String ADD_MANAGER_ERROR = "Failed adding manager details";
    public static final String ADD_DOCUMENT_SUCCESS = "Documents uploaded successfully";
    public static final String ADD_DOCUMENT_ERROR = "Failed adding customer document details";
    public static final String ADD_BANK_ACCOUNT_ERROR = "Please add FD account details to proceed";

    public static final String ADD_ASSET_SUCCESS = "Asset details added successfully";
    public static final String ADD_LIABILITY_SUCCESS = "Liability details added successfully";

    public static final String ADD_ASSET_ERROR = "Failed adding asset details.";
    public static final String ADD_LIABILITY_ERROR = "Failed adding liability details.";

    public static final String ADD_ASSET_LIABILITY_SUCCESS = "Assets and liability details added successfully";
    public static final String ADD_ASSET_LIABILITY_ERROR = "Failed adding asset and liability details.";

    //
    //bulk card confirmation 
    public static final String BULK_CONFIRM_SUCCESS = "Successfully confirmed  ";
    public static final String BULK_REJECT_SUCCESS = " Rejected  ";
    public static final String BULK_APPR_NO_OF_CARDS = "Enter approved number of cards";
    public static final String BULK_APPR_NO_OF_CARDS_VALID = "Enter a valid number of cards";
    public static final String BULK_CARD_BIN_PROF = "Enter a card bin profile";
    public static final String BULK_TEMPL_EMPTY = "Enter a template";
    public static final String BULK_CREDIT_LIMIT_VALID = "Enter a valid credit limit";
    public static final String BULK_REJECT_RESON_EMPTY = "Enter a reason to reject";
    public static final String BULK_REJECT_RESON_VALID = "Enter a better reason";
    public static final String BULK_BATCH_ID_VALID = "Enter a valid batch id";
    public static final String BULK_BATCHID_INVALIS = "Invalid batch id";
    // Bulk Card & Pin Distribution
    public static final String BULK_CARD_DISTRIBUTION_SUCCESS = "Card distribution is approved";
    public static final String BULK_CARD_PIN_DISTRIBUTION_SUCCESS = "Bulk card pin distribution is approved";
    public static final String SELECTED_BATCH_NULL = "Select at least one batch";
    //
    // ----------------------------------------Put new messages here-----------------------------------------------------------
    public static final String CARDDOMAIN_RISK_NULL = "Risk profile cannot be empty";
    public static final String FEEPROFILE_CODE_INVALID = "Invalid fee profile code";
    public static final String FEEPROFILE_DES_INVALID = "Invalid description";
    public static final String NO_BINS = "No bins to load";
    public static final String MRCHNTCATMGT_DESCRIPTION_INVALID = "Merchant description is invalid.";
    public static final String TO_LARGER_FROM = "Valid to date should be larger than valid from date";
    public static final String NUM_CD_INVALID = "Number of cards should be numeric";
    public static final String SUCCESS_CONFIRM = "Successfully confirmed";
    public static final String SUCCESS_REJECT = "Successfully rejected";
    public static final String NO_RECEIVE_CONFIRM = "This batch is not in distribution stage";
    public static final String BULK_APPR_NO_OF_CARDS_HIGH = "Number of cards exceeds the requested amount";
    //
    //billing statement profile
    public static final String BILLING_STMT_MIN_DUE_FLAT_INVALID = "Minimun due flat amount not valid";
    public static final String BILLING_STMT_MIN_DUE_PERC_INVALID = "Minimun due percentage not valid";
    public static final String BILLING_STMT_MIN_DUE_PERC_100 = "Enter percentage below 100";
    public static final String BILLING_STMT_BALANCE_INVALID = "Balance not valid";
    //
    //country mgt
    public static final String COUNTRY_CODE_IN_3 = "Enter country code number in 3 digits";
    //
    //currency mgt
    public static final String CURRENCY_CODE_IN_3 = "Enter currency code in 3 digits";
    public static final String INTEREST_INTRESTTYPE_INVALID = "Invalid interest period value";
    // Eod Process Category Mgt
    public static final String DEPENDENCY_STATUS_NULL = "Dependency status must be selected";
    public static final String EOD_PROCESS_CATEGORY_SUCCESS_ADD = "Successfully added EOD process category";
    public static final String EOD_PROCESS_CATEGORY_SUCCESS_UPDATE = "Successfully updated EOD process category";
    public static final String EOD_PROCESS_CATEGORY_SUCCESS_DELETE = "Successfully deleted EOD process category";
    //EOD Process Mgt
    public static final String CRITICAL_STATUS_NULL = "Critical status must be selected";
    public static final String ROLLBACK_STATUS_NULL = "Rollback status must be selected";
    public static final String DATE_NULL = "Scheduled date must be selected";
    public static final String TIME_NULL = "Scheduled time must be selected";
    public static final String FREQ_TYPE_NULL = "Frequency type must be selected";
    public static final String CON_FREQ_TYPE_NULL = "Continuous frequency type must be selected";
    public static final String CONT_FREQ_NULL = "Continuous frequency must be selected";
    public static final String MULTI_CYCLE_STATUS_NULL = "Multiple cycle status must be selected";
    public static final String PROCESS_CAT_NULL = "Process category must be selected";
    public static final String ON_MAIN_NULL = "Running on main must be selected";
    public static final String ON_SUB_NULL = "Running on sub must be selected";
    public static final String PROCESS_TYPE_NULL = "Process type must be selected";
    public static final String CON_FREQ_INVALID = "Continuous frequency invalid";
    public static final String BOTH_NO = "One of the running status should be yes";
    public static final String LETTER_GEN_STATUS = "Letter generation status must be selected";
    //
    //EOD process category flow mgt & Eod Process flow mgt
    public static final String CAT_ADD_SUCCESS = "Successfully saved process categories";
    public static final String STEP_ID_EMPTY = "Step id is empty";
    public static final String CAT_LIST_EMPTY = "Process category list is empty";
    public static final String CATEG_EMPTY = "Category is empty";
    public static final String PRO_ADD_SUCCESS = "Successfully saved processes";
    //
    //
    //
    //card product new
    public static final String CARD_VALIDITY_NULL = "Card validity period cannot be empty";
    public static final String CARD_VALIDITY_INVALID = "Enter valid card validity Period";
    // Switch Control Pannel --> key Mailer
    public static final String ERROR_LOAD_KEY_MAILER = "Error occurred in loading key mailer";
    public static final String ERROR_SEARCH_KEY_MAILER = "Error occurred in searching terminal key";
    public static final String INCORRECT_TERMINAL_ID_OR_MERCHANT_ID = "Incorrect terminal id or merchant id";
    public static final String LMK_KTM_LENGTH_ERROR = "Incorrect LMK_KTM";
    // Commission Profile
    public static final String COMMISSION_PROFILE_CODE_NULL = "Commission profile code required";
    public static final String COMMISSION_PROFILE_CODE_INVALID = "Invalid commission profile code";
    public static final String CR_OR_DR_NULL = "Select credit or debit";
    public static final String FLAT_AMOUNT_NULL = "Flat amount required";
    public static final String FLAT_AMOUNT_INVALID = "Invalid flat amount";
    public static final String COMBINATION_NULL = "Select MIN, MAX or ADD";
    public static final String COMMISSION_PROFILE_SUCCESS_ADD = "Successfully added a commission profile";
    public static final String COMMISSION_PROFILE_SUCCESS_UPDATE = "Successfully updated a commission profile";
    public static final String COMMISSION_PROFILE_SUCCESS_DELETE = "Successfully deleted a commission profile";
    public static final String TRANSACTION_SUCCESSFULLY_REMOVED = "Transaction successfully removed";
    public static final String COMMISSION_PERCENTAGE_INVALID = "Percentage must be less than 100%";
    public static final String CYCLE_MGT_BILLCODE_NULL = "Statement cycle code cannot be empty";
    public static final String CYCLE_MGT_BILLCODE_INVALID = "Statement cycle code is invalid";
    public static final String CYCLE_MGT_BILLDESCRIPTION_NULL = "Description cannot be empty";
    public static final String STATEMENT_OPTION_NULL = "Statement option cannot be empty";
    //
    //-------------Back Office > gl account mgt messages
    public static final String GLACCOUNT_ADD_SUCCESS = "GL account added successfully";
    public static final String GLACCOUNT_UPDATE_SUCCESS = "GL account updated successfully";
    public static final String GLACCOUNT_DELETE_SUCCESS = "GL account deleted successfully";
    public static final String GL_ACCNO_EMPTY = "GL account number is empty";
    public static final String GL_ACCNO_INVALID = "GL account number invalid";
    public static final String GL_DES_EMPTY = "Description empty";
    public static final String GL_DES_INVALID = "Description invalid";
    public static final String GL_STATUS_EMPTY = "Status is empty";
    public static final String GL_TYPE_EMPTY = "GL account type is empty";
    //
    //-------------Back Office > assign txns to gl account 
    public static final String GL_ACC_EMPTY = "Select a credit GL account";
    public static final String DR_GL_ACC_EMPTY = "Select a debit GL account";
    public static final String GL_STAT_EMPTY = "Select listener or channel status";
    public static final String GL_CHLS_TYPE_EMPTY = "Select a channel or listener type";
    public static final String GL_TXNS_EMPTY = "Select transactions";
    public static final String GL_TXNS_SUCCESS = "Successfully assigned transactions";
    public static final String CARDNUMBER_INVALID = "Invalid card number";
    public static final String EXPIRY_DATE_NULL = "Expiry date must be entered";
    public static final String CVV_VALUE_NULL = "CVV value required";
    public static final String CVV_VALUE_INVALID = "Incorrect CVV Value";
    public static final String TRANSACTION_AMOUNT_NULL = "Transaction amount required";
    public static final String TRANSACTION_AMOUNT_INVALID = "Invalid transaction amount";
    public static final String TERMINAL_ID_NULL = "Terminal must be selected";
    public static final String CYCLE_MGT_PAYMENTCODE_NULL = "Payment cycle code cannot be empty";
    public static final String CYCLE_MGT_PAYMENTCODE_INVALID = "Payment cycle code is invalid";
    public static final String CYCLE_MGT_PAYMENTDESCRIPTION_NULL = "Description cannot be empty";
    public static final String PAYMENT_OPTION_NULL = "Payment option cannot be empty";
    ///////////////////////////////////PAYMENT CYCLE DATA CAPTURE MESSAGES/////////////////////////////////////
    public static final String PAYMENT_CYCLE_MGT_SUCCESS_ADD = "Successfully  added ";
    public static final String PAYMENT_CYCLE_MGT_PAYMENTCODE_NULL = "Payment code cannot be empty";
    public static final String PAYMENT_CYCLE_MGT_PAYMENTCODE_INVALID = "Payment code is invalid";
    public static final String PAYMENT_CYCLE_MGT_PAYMENTDATE_NULL = "Payment date cannot be empty";
    public static final String PAYMENT_CYCLE_MGT_PAYMENTDESCRIPTION_NULL = "Payment description cannot be empty";
    public static final String PAYMENT_CYCLE_MGT_HOLIACT_NULL = "Holiday action cannot be empty";
    public static final String PAYMENT_CYCLE_MGT_STATUS_NULL = "Payment status cannot be empty.";
    public static final String PAYMENT_CYCLE_MGT_PAYMENT_CODE_SUCCESS_DELETE = "Successfully deleted ";
    public static final String PAYMENT_CYCLE_MGT_PAYMENT_CODE_SUCCESS_UPDATE = "Successfully updated ";
    ////////////////////////////////////END OF PAYMENT CYCLE DATA CAPTURE MESSAGES////////////////////////////////    
    // call center history explorer
    public static final String ERROR_LOAD_APPLICATION_VERIFICATION_REPORT = "Error occurred in loading application verification report";
    public static final String ERROR_APP_SUMMARY = "Error occurred in loading application summary";
    public static final String ERROR_APP_DATA_ENTRY = "Error occurred in loading application data entry details";
    public static final String ERROR_APP_REJECT = "Error occurred in loading application reject details";
    public static final String ERROR_APP_PIN_GEN_AND_MAIL = "Error occurred in loading pin generation & mailing";
    public static final String ERROR_LOAD_NUMBER_GENERATION_REPORT = "Error occurred in loading number generation report";
    public static final String ERROR_LOAD_ACTIVATED_CARD_REPORT = "Error occurred in loading activated card report";
    public static final String EMPTY_VALUE_MSG = "No data to display";
    public static final String ERROR_LOAD_EOD_TRANSACTION_REPORT = "Error occurred in loading EOD transaction report";
    public static final String ERROR_EM_EN_CD_REPORT = "Error occurred in loading emboss and encode card details report";
    public static final String ERROR_EM_EN_CD_REPORT_GEN = "Error occurred in generating emboss and encode card report";
    public static final String ERROR_APP_REJECT_REPORT_GEN = "Error occurred in generating application reject report";
    public static final String ERROR_APP_DETAIL_REPORT_GEN = "Error occurred in generating application details report";
    public static final String ERROR_DATA_ENTRY_REPORT_GEN = "Error occurred in generating data entry report";
    public static final String ERROR_CARD_STATUS_REPORT_GEN = "Error occurred in generating card status report";
    public static final String ERROR_CREDIT_SCORE_REPORT_GEN = "Error occurred in generating credit score report";
    public static final String ERROR_APP_DETAILS_REPORT_GEN = "Error occurred in generating application details report";
    public static final String ERROR_APP_DISTRIBUTION_REPORT_GEN = "Error occurred in generating application distribution report";
    public static final String ERROR_TERMINAL_REPORT = "Error occurred in loading terminal details report";
    public static final String ERROR_CD_STATUS_REPORT = "Error occurred in loading card status report details";
    public static final String ERROR_LOAD_ACC_TEMP = "Error occurred in loading account templates";

    public static final String BRANCH_EMPTY = "Branch should be selected";
    public static final String PRIORITY_LEVEL_EMPTY = "Priority level should be selected";
    public static final String APP_DOMAIN_EMPTY = "Application domain should be selected";
    public static final String APP_STATUS_EMPTY = "Status should be selected";

    //////////////////////////////////BULK CARD DATA CAPTURE //////////////////////////////////////////////////////
    public static final String CARD_IS_NOT_EXIST = "This card does not exist. Invalid card number";
    public static final String INVALID_ISSUING_BRANCH = "This branch is not permited to issue this card";
    public static final String BULKCARD_APPLICATION_SUCCESS_ADD = "Successfully added the bulk card application ";
    public static final String SIGNATURE_FILE_NULL = "Signature file must be uploaded";
    public static final String NIC_FILE_NULL = "NIC file must be uploaded";
    public static final String DEF_CHANNEL_NULL = "Default channel required";
    public static final String MIN_CDS = "Number of cards cannot be zero";
    ///////////////////Manual Transaction
    public static final String COMMUNICATION_ERROR = "Unable to communicate with CMS online";
    public static final String TRANSACTION_SUCCESS = "Transaction successful";
    public static final String TRANSACTION_ERROR = "Transaction failed due to ";
    public static final String TRANSACTION_NOT_SELECTED = "Select atleast one transaction";
    //common parameter
    public static final String SUCCESS_UPDATE_COMMON_PARAMETER = "Successfully updated the common configuration";
    public static final String SUCCESS_SET_COMMON_PARAMETER = "Successfully set the common configuration";
    //// Terminal Report /////
    public static final String INVALID_MERCHANT_CUSTID = "Merchant customer id is invalid";
    public static final String INVALID_MERCHANT_ID = "Merchant id is invalid";
    public static final String INVALID_TERMINAL_ID = "Terminal id is invalid";
    public static final String INVALID_TERMINAL_CURRENCY = "Terminal currency is invalid";
    public static final String INVALID_DATE = "Invalid date format";
    public static final String ERROR_APP_TERMINAL_REPORT = "Error occurred in loading terminal report";
    public static final String ACQ_TXN_TYPE_SUCCESS_ADD = "Acquirer transaction type successfully added ";
    public static final String ACQ_TXN_TYPE_SUCCESS_UPDATE = "Acquirer transaction type successfully updated ";
    public static final String ACQ_TXN_TYPE_SUCCESS_DELETE = "Acquirer transaction type successfully deleted ";
    //File Management
    public static final String INVALID_FILE_EXTENSION = "Invalid file extension";
    public static final String INVALID_FILE_DATE = "Invalid file date";
    public static final String INVALID_FILE_PREFIX = "Invalid file prefix";
    public static final String ERROR_FILE_UPLOAD = "Error occurred in file uploading";
    public static final String ERROR_APP_LOAD = "Error occured in loading";
    public static final String INVALID_FILE_SIZE = "Cannot upload. Minimum file size should be 20MB";
    public static final String FILE_TYPE_EMPTY = "File type cannot be empty";
    public static final String FILE_NAME_EMPTY = "File name cannot be empty";
    public static final String FILE_EXISTS = "File already exists";
    public static final String FILE_UPLOAD_SUCCESS = "File successfully uploaded";
    //File Info
    public static final String FILE_INFO_EMPTY = "File info code cannot be empty";
    public static final String FILE_PATH_EMPTY = "File path cannot be empty";
    public static final String FILE_BACKUP_PATH_EMPTY = "File backup path cannot be empty";
    public static final String FILE_INFO_ADD_SUCCESS = "File info added successfully";
    public static final String FILE_INFO_UPDATE_SUCCESS = "Successfully updated file info";
    public static final String FILE_INFO_DELETE_SUCCESS = "Successfully deleted file info";
    public static final String FILE_TYPE_EXISTS = "File type already exists";
    public static final String FILE_PATH_ERROR = "File path and backup path cannot be same";
    ////File Type
    public static final String FILE_DESC_EMPTY = "File description cannot be empty";
    public static final String FILE_NAME_PREFIX_EMPTY = "File name prefix cannot be empty";
    public static final String FILE_NAME_POSTFIX_EMPTY = "File name postfix cannot be empty";
    public static final String FILE_EXTENSION_EMPTY = "File extension cannot be empty";
    public static final String FILE_TYPE_UPDATE_SUCCESS = "Successfully updated file type";
    public static final String FILE_TYPE_DELETE_SUCCESS = "Successfully deleted file type ";
    public static final String FILE_TYPE_ADD_SUCCESS = "File type added successfully";
    public static final String CARD_TYPE_VALUE_EMPTY = "Card type should be selected";
    public static final String DOMAIN_TYPE_VALUE_EMPTY = "Card domain should be selected";
    public static final String CHANNEL_TYPE_VALUE_EMPTY = "Sending channel should be selected";
    //Terminal  Manufacture
    public static final String TER_CODE_EMPTY = "Terminal code cannot be empty";
    public static final String TER_CODE_INVALID = "Special characters are not allowed for terminal code";
    public static final String TER_DESC_INVALID = "Special characters are not allowed for description";
    public static final String TER_DESC_EMPTY = "Description cannot be empty";
    public static final String CODE_ADD_SUCCESS = "Successfully added terminal";
    public static final String TER_MANUFAC_UPDATE_SUCCESS = "Successfully updated terminal manufacturer";
    public static final String TER_MANUFAC_DELETE_SUCCESS = "Successfully deleted terminal manufacturer";
    //Terminal  Model
    public static final String MODEL_CODE_EMPTY = "Model code cannot be empty";
    public static final String MODEL_DESC_EMPTY = "Description cannot be empty";
    public static final String MODEL_STATUS_EMPTY = "Status should be selected";
    public static final String MODEL_MANUFAC_EMPTY = "Manufacturer should be selected";
    public static final String MODEL_DESC_INVALID = "Special characters are not allowed for description";
    public static final String MODEL_CODE_INVALID = "Special characters are not allowed for model code";
    public static final String MODEL_CODE_ADD_SUCCESS = "Successfully added model";
    public static final String TER_MODEL_UPDATE_SUCCESS = "Successfully updated terminal model";
    public static final String TER_MODEL_DELETE_SUCCESS = "Successfully deleted terminal model";
    /////Communication Key Management ////
    public static final String CARD_KEY_ID_NULL = "Card key id cannot be empty.";
    public static final String CARD_KEY_ID__INVALID = "Card key id is invalid.";
    public static final String CARD_KEY_DESCRIPTION_NULL = "Card key description cannot be empty.";
    public static final String CARD_KEY_PROFILE_NULL = "Card key profile id cannot be empty.";
    public static final String CARD_KEY_DELETE_SUCCESS = "Card key details deleted successfully.";
    public static final String CARD_KEY_DELETE_ERROR = "Error occurred while deleting card key details.";
    /////Communication Key Management ////
    public static final String COMMUNICATION_KEY_ID_NULL = "Communication key id cannot be empty.";
    public static final String COMMUNICATION_KEY_ID__INVALID = "Communication key id is invalid.";
    public static final String COMMUNICATION_KEY_DESCRIPTION_NULL = "Communication key description cannot be empty.";
    public static final String COMMUNICATION_KEY_DELETE_SUCCESS = "Communication key details deleted successfully.";
    public static final String COMMUNICATION_KEY_DELETE_ERROR = "Error occurred while deleting communication key details.";
    /////Manual Adjustment////
    public static final String ADJUSTMENT_TYPE_INVALID = "Adjustment type is invalid";
    public static final String ADJUSTMENT_TYPE_NULL = "Adjustment type cannot be empty";
    public static final String INVALID_CR_DR = "Credit or Debit must be selected.";
    public static final String TRACE_NO_NULL = "Trace number is empty";
    public static final String TRACE_NO_NOT_NUMERIC = "Trace number must be numeric";

    ///////Manual Adjustment confirmation////
    public static final String MA_CARDTXN_APPROVED = "Manual adjustment request is approved";
    public static final String MA_CARDTXN_FAILED = "Manual adjustment request approval failed";
    public static final String MA_CARDTXN_REJECTED = "Manual adjustment request is rejected";

    /////Standing order types////
    public static final String STANDING_ORDER_ID_NULL = "Standing order id cannot be empty.";
    public static final String INVALID_ORDER_ID = "Invalid order id for order id.";
    public static final String STANDING_ORDER_NAME_NULL = "Description cannot be empty.";
    public static final String INVALID_ORDER_NAME = "Invalid Standing order type.";
    public static final String STANDING_ORDER_FLAT_FEE_NULL = "Standing order flat fee cannot be empty.";
    public static final String FLAT_FEE_DECIMAL_NUMERIC = "Standing order flat fee should be decimal or numeric.";
    public static final String STANDING_ORDER_FLAT_FEE_PECENTAGE_NULL = "Standing order flat fee pecentage cannot be empty.";
    public static final String FLAT_FEE_PERCENTAGE_DECIMAL_NUMERIC = "Standing order flat fee pecentage should be decimal or numeric and in 0.00 format";
    public static final String FLAT_FEE_PERCENTAGE_LESS_THAN_ONE = "Flat fee pecentage must be less than 1 and in 0.00 format";
    public static final String FEE_OPTION_NULL = "Choose standing order fee option.";
    public static final String CURRENCY_TYPE_NULL = "Choose standing order currency type.";
    public static final String MAX_TRANSACTION_AMOUNT_NULL = "Max transaction amount cannot be empty.";
    public static final String MAX_TRANSACTION_AMOUNT_DECIMAL_NUMERIC = "Max transaction amount should be decimal or numeric.";
    public static final String MIN_TRANSACTION_AMOUNT_NULL = "Min transaction amount cannot be empty.";
    public static final String MIN_TRANSACTION_AMOUNT_DECIMAL_NUMERIC = "Min transaction amount should be decimal or numeric.";
    public static final String STANDING_ORDER_BANK_NAME_NULL = "Select standing order bank name.";
    public static final String STANDING_ORDER_BANK_BRANCH_NAME_NULL = "Select standing order bank branch name.";
    public static final String STANDING_ORDER_ACC_NUMBER = "Account number cannot be empty.";
    public static final String INVALID_ACC_NUMBER = "Invalid Account Number for Account Number.";
    public static final String STANDING_ORDER_PAYMENT_TYPE_NULL = "Select standing order payment type.";
    public static final String STANDING_ORDER_ADDRESS_NULL = "Standing order address1 cannot be empty.";
    public static final String STANDING_ORDER_CITY_NULL = "Select city.";
    public static final String STANDING_ORDER_CONTACT_PERSON_NULL = "Contact person cannot be empty.";
    public static final String STANDING_ORDER_CONTACT_PERSON_ALPHA = "Contact person name should not contain special or numeric characters.";
    public static final String STANDING_ORDER_CONTACT_PERSON_NUMBER_NULL = "Contact person number cannot be empty.";
    public static final String STANDING_ORDER_CONTACT_PERSON_NUMBER = "Contact person number can only be numeric.";
    public static final String STANDING_ORDER_CONTACT_PERSON_NUMBER_LENGHT = "Contact person number must contain ten digits.";
    public static final String STANDING_ORDER_MAX_MIN_TRANSACTION_AMT_DIFF_ERROR = "Maximum transaction amount must be more than Minimun transaction amount.";
    public static final String STANDING_ORDER_CATEGORY_NULL="Standing order category cannot be empty";
    public static final String UTILITY_PROVIDER_NULL="Utility provider cannot be empty";
    public static final String STANDING_ORDER_FEE_TYPE_NULL="Fee type cannot be empty";
	//card standing order
    public static final String STANDING_ORDER_CARD_NUMBER_EMPTY="Card number cannot be empty";
    public static final String STANDING_ORDER_CARD_NUMBER_INVALID="Invalid card number";
    public static final String STANDING_ORDER_TYPE_EMPTY="Standing order type cannot be empty";
    public static final String STANDING_ORDER_ACCOUNT_NUMBER_EMPTY="Account number cannot be empty";
    public static final String STANDING_ORDER_ACCOUNT_NUMBER_INVALID="Invalid account number";
    public static final String STANDING_ORDER_END_DATE_EMPTY="End date cannot be empty";
    public static final String STANDING_ORDER_END_DATE_INVALID="Invalid end date";
    public static final String STANDING_ORDER_PAY_DATE_EMPTY="Pay date cannot be empty";
    public static final String STANDING_ORDER_FREQUENCY_EMPTY="Frequency cannot be empty";
    public static final String STANDING_ORDER_INITIAL_PAYMENT_DATE_EMPTY="Initial payment date cannot be empty";
    public static final String STANDING_ORDER_STATUS_EMPTY="Status cannot be empty";
    public static final String STANDING_ORDER_REMARK_INVALID="Invalid remark";
    //Area Management
    public static final String AREA_CODE_EMPTY = "Area code cannot be empty";
    public static final String AREA_DESC_EMPTY = "Area description cannot be empty";
    public static final String AREA_PROVINCE_EMPTY = "Province should be selected";
    public static final String AREA_DISTRICT_EMPTY = "District should be selected";
    public static final String AREA_MGT_UPDATE_SUCCESS = "Successfully updated area management";
    public static final String AREA_CODE_DELETE_SUCCESS = "Successfully deleted area code";
    public static final String AREA_DESC_INVALID = "Special characters are not allowed for description";
    public static final String AREA_CODE_INVALID = "Special characters are not allowed for area code";
    public static final String AREA_CODE_ADD_SUCCESS = "Successfully added area";
    // Call Center-->
    public static final String CUSTOMER_TRANSACTION_UPDATE_SUCCESS = "Updated data successfully";
    public static final String ACCOUNT_TRANSACTION_UPDATE_SUCCESS = "Updated data successfully";
    public static final String CARD_TRANSACTION_UPDATE_SUCCESS = "Updated data successfully";
    public static final String ACCOUNT_TRANSACTION_CANNOT_UPDATE = "Cannot be updated, deleted transactions are in other joint customer";
    //Listner Key Mailing
    public static final String LISTENER_NAME_EMPTY = "Listener name cannot be empty";
    public static final String LISTENER_NAME_INVALID = "Special characters are not allowed for listener name";
    public static final String LISTENER_TYPR_EMPTY = "Listener type should be selected";
    public static final String COM_KEY_EMPTY = "Communication key should be selected";
    //Limit Exceeded Report
    public static final String CASH_OR_CREDIT_STATUS_EMPTY = "Cash or Credit status cannot be empty";
    public static final String USAGE_PERCENTAGE_EMPTY = "Usage percentage cannot be empty";
    public static final String USAGE_PERCENTAGE_NUMERIC = "Usage percentage must be decimal or numeric";
    //EOD Letter Generation
    public static final String PROCESS_CATEGORY_EMPTY = "Process category should be selected";
    public static final String ERROR_LETTER_GEN = "Error occurred in accessing letter generation";
    public static final String INVALID_FONT_STYLE = "Requested font style is invalid";
    public static final String LETTER_GENERATED_SUCCESS = "Letter generated succesfully.";
    public static final String LETTER_GENERATED_ALL = "All the letters are generated";
    public static final String FILE_CLOSE_ERROE = "Error in file closing";
    public static final String LETTER_GENERATED_ERROR = "Error occurred in letter generation";
    public static final String NO_LETTERS = "Not enough data to generate letters";
    public static final String NO_DATA_AVAILABLE = "No data available";
    public static final String SEARCH_ERROR = "Get valid search results to generate letters";
    public static final String NO_LETTER_GENERATE = "No card details to generate letters";
    // letter template managment
    public static final String LETTER_TEMPLATE_DELETE_SUCCESS = "Successfully deleted letter template";
    public static final String LETTER_TEMPLATE_ADD_SUCCESS = "Successfully added letter template";
    public static final String LETTER_TEMPLATE_UPDATE_SUCCESS = "Successfully updated letter template";
    public static final String LETTER_TEMPLATE_CODE_EMPTY = "Letter template code cannot be empty";
    public static final String LETTER_TEMPLATE_CODE_ALPHANUMERIC = "Letter template code should alphanumeric";
    public static final String LETTER_TEMPLATE_DESCRIPTION_EMPTY = "Description cannot be empty";
    public static final String LETTER_TEMPLATE_STATUS_EMPTY = "Status cannot be empty";
    public static final String LETTER_TEMPLATE_PROCESS_EMPTY = "Process category cannot be empty";
    public static final String LETTER_TEMPLATE_TEMPLATE_EMPTY = "Letter template cannot be empty";
    public static final String LETTER_TEMPLATE_BODY_EMPTY = "Letter template body cannot be empty";
    //backofice---> payment 
    public static final String CARD_HOLDER_NAME_EMPTY = "Card holder name connot be empty";
    public static final String CARD_HOLDER_NAME_INVALID = "Card holder name invalid";
    public static final String CARD_NUMBER_EMPTY = "Card number cannot be empty";
    public static final String INVALID_CARDNUM = "Card number invalid";
    public static final String PAYMENT_TYPE_EMPTY = "Payment type cannot be empty";
    public static final String CHEQUE_NUM_EMPTY = "Cheque number cannot be empty";
    public static final String CHEQUE_NUM_INVALID = "Cheque number invalid";
    public static final String CHEQUE_BANK_EMPTY = "Cheque bank cannot be empty";
    public static final String CURR_TYPE_EMPTY = "Currency type cannot be empty";
    public static final String AMOUNT_EMPTY = "Amount cannot be empty";
    public static final String INVALID_AMOUNT = "Amount invalid";
    public static final String INVALID_CHANNEL_ID = "Invalid  Channel ID";
    public static final String INVALID_CHANNEL_NAME = "Invalid Channel Name";
    public static final String RECONCILE_SUCCESS = "Batch Successfully Reconciled";
    public static final String RECONCILE_ERROR = "Batch Reconciliation Error";
    // corporate assign process
    public static final String CORPORATEASSIGN_APPTYPE_NULL = "Corporate type cannot be empty";
    public static final String CORPORATEASSIGN_IDENTITY_NUMBER_NULL = "Identification number cannot be empty";
    public static final String CORPORATEASSIGN_PRIORITY_NULL = "Priority level cannot be empty";
    public static final String CORPORATEASSIGN_ASSIGNUSER_NULL = "Assign user cannot be empty";
    public static final String ERROR_LOAD_CORPORATEASSIGN_APP = "Error occurred in loading assigned corporate applications";
    public static final String EXCEED_AMOUNT = "Amount exceed the maximum amount of payment";
    public static final String EXCEED_TOT_AMOUNT = "Amount exceed the maximum total amount of batch";
    public static final String MERCHANT_ID_NULL = "Merchant ID cannot be empty";
    public static final String MERCHANT_ID_SHOULD_BE_NUMERIC = "Merchant ID should be numeric";
    public static final String MERCHANT_NOT_EXIST = "Merchant does not exist";
    public static final String TERMINAL_ID_NULL_TX = "Terminal ID cannot be empty";
    public static final String TERMINAL_ID__SHOULD_BE_NUMERIC_TX = "Terminal ID should be numeric";
    public static final String TERMINAL_ID_NOT_EXIST_TX = "Terminal does not exist";
    //Card Management-->Application Data Capture-->Corporate Card
    public static final String ADD_CO_CUSTOMER_SUCCESS = "Corporate customer details added successfully";
    public static final String ADD_CO_CUSTOMER_ERROR = "Error occurred adding corporate customer details";
    public static final String ADD_CO_CUSTOMER_BANK_SUCCESS = "Corporate customer bank details added successfully";
    public static final String ADD_CO_CUSTOMER_BANK_ERROR = "Error occurred adding corporate customer bank details";

    public static final String LADDER_MGT_SUCCESS_ADD = "Successfully  added ";
    public static final String LADDER_DESCRIPTION_EMPTY = "Description cannot be empty";
    public static final String LADDER_CODE_EMPTY = "Code cannot be empty";
    public static final String LADDER_CODE_EXISTS = "A ladder by that name already exists";
    public static final String LADDER_STATUS_EMPTY = "Status cannot be empty";
    public static final String LADDER_CARD_TYPE_EMPTY = "Card type cannot be empty";
    public static final String LADDER_NO_CAESE_ASSIGNED = "Ladder must have at least one case type assigned";
    public static final String LADDER_MGT_SUCCESS_UPDATE = "Successfully  updated ";

    //Base Configuration
    public static final String BASE_CONFIG_CURRENCY_EMPTY = "Base currency should be selected";
    public static final String BASE_CONFIG_COUNTRY_EMPTY = "Base country should be selected";
    public static final String BASE_CONFIG_TERMINALID_EMPTY = "Terminal id cannot be empty";
    public static final String BASE_CONFIG_MERCHANTID_EMPTY = "Merchant id cannot be empty";
    public static final String BASE_CONFIG_ERROR = "Error occurred in base config";
    public static final String BASE_TERMINAL_ID_INVALID = "Enter valid digits for terminal id";
    public static final String BASE_MERCHANT_ID_INVALID = "Enter valid digits for merchant id";

    // debit pin generation
    public static final String DEBIT_PIN_INVALIS = "Invalid card number";

    //Case Management
    public static final String CASE_TYPE_CODE_EMPTY = "Case type code cannot be empty";
    public static final String CASE_TYPE_CODE_INVALID = "Invalid case type code";
    public static final String CASE_DESCRIPTION_EMPTY = "Case description cannot be empty";
    public static final String CASE_DESCRIPTION_INVALID = "Invalid case description";
    public static final String CASE_CURRENCY_TYPE_NULL = "Select case type currency";
    public static final String CASE_STATUS_NULL = "Select case type status";
    public static final String SEVERITY_VALUE_NULL = "Severity value cannot be empty";
    public static final String SEVERITY_VALUE_NUMERIC = "Severity value is numeric";
    public static final String SEVERITY_VALUE_LESSTHAN_100 = "Severity value should less than 100";
    public static final String CRITERIA_EMPTY = "Select criteria";
    public static final String ENTRY_OVERDUE_PERIOD_EMPTY = "Select entry overdue period";
    public static final String OVERDUE_AMOUNT_TYPE_EMPTY = "Select overdue amount type";
    public static final String ENTRY_OVERDUE_AMOUNT_EMPTY = "Entry overdue amount cannot be empty";
    public static final String ENTRY_OVERDUE_AMOUNT_NUMERIC = "Entry overdue amount is numeric";
    public static final String ENTRY_OVERDUE_PERCENTAGE_INVALID = "Entry overdue percentage should less than 100";
    public static final String ENTRY_ACCOUNT_STATUS_EMPTY = "Entry account status cannot be empty";
    public static final String ENTRY_CARD_STATUS_EMPTY = "Entry card status cannot be empty";
    public static final String OVER_LIMIT_AMOUNT_TYPE_EMPTY = "Select over limit amount type";
    public static final String ENTRY_OVER_LIMIT_AMOUNT_EMPTY = "Entry over limit amount cannot be empty";
    public static final String ENTRY_OVER_LIMIT_AMOUNT_NUMERIC = "Entry over limit amount is numeric";
    public static final String ENTRY_OVER_LIMIT_PERCENTAGE_INVALID = "Entry over limit percentage should less than 100";
    public static final String EXIT_OVERDUE_AMOUNT_EMPTY = "Exit overdue amount cannot be empty";
    public static final String EXIT_OVERDUE_AMOUNT_NUMERIC = "Exit overdue amount is numeric";
    public static final String EXIT_OVERDUE_PERCENTAGE_INVALID = "Exit overdue percentage should less than 100";
    public static final String EXIT_OVERDUE_PERIOD_EMPTY = "Select exit overdue period";
    public static final String EXIT_OVER_LIMIT_AMOUNT_EMPTY = "Exit over limit amount cannot be empty";
    public static final String EXIT_OVER_LIMIT_AMOUNT_NUMERIC = "Exit over limit amount is numeric";
    public static final String EXIT_OVER_LIMIT_PERCENTAGE_INVALID = "Exit over limit percentage should less than 100";
    public static final String EXIT_ACCOUNT_STATUS_EMPTY = "Exit account status cannot be empty";
    public static final String EXIT_CARD_STATUS_EMPTY = "Exit card status cannot be empty";
    public static final String PROCESS_VALUE_EMPTY = "Select at least one process value";

    public static final String RECOVERY_INVALID_ACCOUNT = "Invalid account number";
    public static final String RECOVERY_INVALID_CARD = "Invalid card number";

    public static final String QUEUE_CODE_EMPTY = "Queue code cannot be empty";
    public static final String QUEUE_MGT_SUCCESS_ADD = "Successfully  added ";
    public static final String QUEUE_DESCRIPTION_EMPTY = "Description cannot be empty";
    public static final String QUEUE_CODE_EXISTS = "A ladder by that name already exists";
    public static final String QUEUE_STATUS_EMPTY = "Status cannot be empty";
    public static final String QUEUE_CARD_TYPE_EMPTY = "Card type cannot be empty";
    public static final String QUEUE_NO_CAESE_ASSIGNED = "Ladder must have at least one case type assigned";
    public static final String QUEUE_MGT_SUCCESS_UPDATE = "Successfully  updated ";

    public static final String QUEUE_ID_NULL = "Select queue";
    public static final String USER_NULL = "Select user";
    public static final String COLLECTION_LIST_NULL = "Select at least one collection";
    public static final String ALL_COLLECTION_LIST_NULL = "No collections related to this queue";
    public static final String COLLECTION_USER_ASSIGNED_SUCCESS = "User assignment success";
    public static final String ERROR_CONNECT_TO_SEC_MODULE_MESSAGE = "Server IP is not registered in Security Module";
    public static final String PASSPOLICY_PASSWORDEXPNOTIFYPERIOD_INVALID_LENGTH = "Password Expiry Notification Period should be less than Password Expiry Period";
    public static String PASSPOLICY_NOOFINVALIDLOGINATMPS_INVALID = "Number of invalid login attempts cannot be empty";
    public static String PASSPOLICY_ACCOUNTEXPIRYPERIOD_NULL = "Idle account expiry period cannot be empty";
    public static String PASSPOLICY_ALLOWEDREPTCHARACTERS_NULL = "Allowed repeat characters cannot be empty";
    public static String PASSPOLICY_ALLOWEDREPTCHARACTERS_INVALID = "Allowed repeat characters should be numeric";
    public static String PASSPOLICY_PASSWORDEXPIRYPERIOD_NULL = "Password expiry period cannot be empty";
    public static String PASSPOLICY_PASSWORDEXPIRYPERIOD_INVALID = "Password expiry period should be numeric";
    public static String PASSPOLICY_PASSWORDEXPNOTIFYPERIOD_NULL = "Password expiry notification period cannot be empty";
    public static String PASSPOLICY_PASSWORDEXPNOTIFYPERIOD_INVALID = "Password expiry notification period should be numeric";
    public static String PASSPOLICY_NOOFHISTORYPASSWORDS_NULL = "Number of history passwords cannot be empty";
    public static String PASSPOLICY_NOOFHISTORYPASSWORDS_INVALID = "Number of history passwords should be numeric";
    public static String PASSPOLICY_ACCOUNTEXPIRYPERIOD_INVALID = "Idle account expiry period should be numeric";
    public static String PASSPOLICY_NOOFINVALIDLOGINATMPS_NULL = "Number of invalid login attempts should be numeric";
    public static String PASSPOLICY_FIRSTPASSEXPIRYPERIOD_NULL = "First login password expiry period should be numeric";

    public static String SYS_Add_UPDATE_USER_PASSWORD_MORE_CHAR_REPEAT = "Maximum allowed repeat characters for the password is ";
    public static String COMMON_WARN_CHANGE_PASSWORD = "Your password will expire";
    public static String INVALID_USERNAME = "Please enter valid username and password";
    public static String LOGIN_PASSWORD_FAILD = "Your login attempt was not successful. Please try again";
    public static String LOGIN_PASSWORD_FAILD_USER_DEACTIVATE = "User has been deactivated. Please contact administrator";

//    template manager -> email template
    public static final String SUCCESS_ADD_EMAILCONF = "Successfully added ";
    public static final String EMAIL_TEMPLATECODE_NULL = "Template code cannot be empty";
    public static final String EMAIL_CODE_INVALID = "Template code is invalid";
    public static final String EMAIL_DESCRIPTION_NULL = "Template description cannot be empty";
    public static final String EMAIL_TEMPLATESUBJECT_NULL = "Email subject cannot be empty";
    public static final String EMAIL_TEMPLATEBODY_NULL = "Email Body cannot be empty";
    public static final String EMAIL_TEMPLATEBODY_MAX_LENGTH = "Email body character length exceeded.";
    public static final String EMAIL_TEMPLATE_STATUS_NULL = "Status cannot be empty";
    public static final String ERROR_DELETE_EMAILCONF = "Error occurred in deleting email template";
    public static final String SUCCESS_DELETE_EMAILCONF = "Email template deleted successfully";
    public static final String ERROR_DELETE_EMAILCONFIGURATION = "Error occurred in deleting email template";
    public static final String ERROR_LOAD_EMAILCONF = "Error occurred in loading email templates";
    public static final String ERROR_EMAILCONF_GENERAL = "Error occurred in loading general details";
    public static final String SUCCESS_UPDATE_EMAILCONF_GENERAL = "Email template updated successfully";
    public static final String ERROR_UPDATE_EMAILCONF = "Error occurred in updating email template";
    public static final String ERROR_ADD_EMAILCONF = "Error occurred in adding new email template";
    public static final String EMAIL_SENT_ERROR = "Error occurred when sending the email.";

    //    template manager -> sms template
    public static final String SUCCESS_ADD_SMSCONF = "Successfully added SMS template ";
    public static final String SMS_TEMPLATECODE_NULL = "Template code cannot be empty";
    public static final String SMS_CODE_INVALID = "Template code is invalid";
    public static final String SMS_DESCRIPTION_NULL = "Template description cannot be empty";
    public static final String SMS_TEMPLATEBODY_NULL = "SMS message body cannot be empty";
    public static final String SMS_TEMPLATE_STATUS_NULL = "Status cannot be empty";
    public static final String ERROR_DELETE_SMSCONF = "Error occurred in deleting SMS template";
    public static final String SUCCESS_DELETE_SMSCONF = "SMS template deleted successfully";
    public static final String ERROR_DELETE_SMSCONFIGURATION = "Error occurred in deleting SMS template";
    public static final String ERROR_LOAD_SMSCONF = "Error occurred in loading email templates";
    public static final String ERROR_SMSCONF_GENERAL = "Error occurred in loading general details";
    public static final String SUCCESS_UPDATE_SMSCONF_GENERAL = "SMS template updated successfully";
    public static final String ERROR_UPDATE_SMSCONF = "Error occurred in updating SMS template";
    public static final String ERROR_ADD_SMSCONF = "Error occurred in adding new SMS template";

    //system user management
    public static final String SUCCESS_ADD_USER = "New System User Approved.";
    public static final String SUCCESS_USER_APPROVED = "Successfully user approved";
    public static final String SUCESS_USER_REJECTED = "Successfully user rejected";

    public static final String DELETE_LOGGED_USER = "Logged System User cannot delete.";
    public static final String SUCESS_USER_DELETE = "Successfully deleted user.";
    public static final String ERROR_USER_DELETE = "Failed deleting user.";
    public static final String SUCESS_USER_DELETE_REJECT = "Successfully rejected delete user request";
    public static final String ERROR_USER_DELETE_REJECT = "Failed to reject delete user request";
    public static final String SUCESS_USER_DELETE_REQUEST = "Sucssesfully sent delete user request";
    public static final String ERROR_USER_DELETE_REQUEST = "Faild to send delete user request.";
    public static final String ERROR_DELETE = "Error occurred when deleting user.";
    public static final String ERROR_UPDATE = "Error occurred when updating user.";
    public static final String ERROR_ADD = "Error occurred when adding user.";

    public static final String DUAL_AUTH_LOCK = "Sorry this user has an approval request pending. ";
    public static final String USER_APPROVAL_SENT = "System User Approval sent.";
    public static final String ERROR_USER_APPROVAL_SENT = "Error occurred when system user approval sent.";

    public static final String SUCCESS_CHANGE_PASSWORD = "Successfully changed password.";
    public static final String ERROR_CHANGE_PASSWORD = "Password change failed.";
    public static final String SUCCESS_CHANGE_PASSWORD_REQ_REJECT = "Successfully rejected password change request";
    public static final String SUCCESS_CHANGE_PASSWORD_REQ_SENT = "Password change request sent successfully.";
    public static final String USER_NOT_ACTIVATE = "User not activated yet";
    public static final String PASSWORD_CHANGE_ERROR = "Error occurred when changing password.";

    public static final String SUCCESS_OFFICER_REVIEW_BUT_CAN_NOT_CONFIRM = "Successfully reviewed the document. But you cannot confirm the application since you are not in the approval credit limit.";
    public static final String SUCCESS_OFFICER_REVIEW = "Successfully reviewed the document.";

    //payment plan management
    public static final String ERROR_LOAD_PAYMENTPLAN = "Error occurred in loading payment plan details";
    public static final String ERROR_ADD_PAYMENTPLAN = "Error occurred in adding new payment plan";
    public static final String PAYMENTPLAN_PAYMENTPLANCODE_NULL = "Payment plan code cannot be empty";
    public static final String PAYMENTPLAN_PAYMENTPLANCODE_INVALID = "Payment plan code must be a number";
    public static final String PAYMENTPLAN_DURATION_NULL = "Payment plan duration cannot be empty";
    public static final String PAYMENTPLAN_DURATION_INVALID = "Payment plan duration must be a number";
    public static final String PAYMENTPLAN_INTERESTRATE_NULL = "Payment plan interest rate cannot be empty";
    public static final String PAYMENTPLAN_INTERESTRATE_INVALID = "Payment plan interest rate must be a number";
    public static final String PAYMENTPLAN_DESCRIPTION_NULL = "Description cannot be empty";
    public static final String PAYMENTPLAN_STATUSCODE_NULL = "Status cannot be empty";
    public static final String PAYMENTPLAN_SUCCESS_ADD = "Payment plan successfully added";
    public static final String ERROR_VIEW_PAYMENTPLAN = "Error occurred in user level view";
    public static final String PAYMENTPLAN_SUCCESS_DELETE = "Payment plan successfully deleted";
    public static final String PAYMENTPLAN_SUCCESS_UPDATE = "Payment plan successfully updated";
    public static final String ERROR_DELETE_PAYMENTPLAN = "Error occurred in deleting payment plan";
    public static final String ERROR_UPDATE_PAYMENTPLAN = "Error occurred in updating payment plan";

    //easy payment request
    public static final String EPR_AMOUNT_NULL = "Please enter lower and upper transaction amounts to filter";
    public static final String ERROR_LOAD_EASYPAYMENTREQ = "Error occurred in loading easy payment request details";
    public static final String TXN_AMOUNT_NULL = "Transaction amount cannot be empty";
    public static final String INSTALLMENT_AMOUNT_NULL = "Installment amount cannot be empty";
    public static final String PAYMENTPLAN_NULL = "Payment plan cannot be empty";
    public static final String PAYMENTPLAN_REQ_SUCCESS_ADD = "Successfully added payment plan request ";
    public static final String TXN_AMOUNT_INVALID = "Transaction amount must be a decimal number";
    public static final String INSTALLMENT_AMOUNT_INVALID = "Installment amount must be numeric";

    //easy payment confirmation
    public static final String EASY_PAYMENT_APPROVED = "Easy payment request is approved";
    public static final String EASY_PAYMENT_FAILED = "Easy payment request approval failed";
    public static final String EASY_PAYMENT_REJECTED = "Easy payment request is rejected";
    
    //balance transfer confirmation
    public static final String BT_PAYMENT_APPROVED = "Balance transfer payment request is approved";
    public static final String BT_PAYMENT_FAILED = "Balance transfer payment request approval failed";
    public static final String BT_PAYMENT_REJECTED = "Balance transfer payment request is rejected";
    
    //balance transfer confirmation
    public static final String LOC_PAYMENT_APPROVED = "Loan on card payment request is approved";
    public static final String LOC_PAYMENT_FAILED = "Loan on card payment request approval failed";
    public static final String LOC_PAYMENT_REJECTED = "Loan on card payment request is rejected";

    //    template manager -> letter template
    public static final String SUCCESS_ADD_LETTERCONF = "Successfully added ";
    public static final String LETTER_TEMPLATECODE_NULL = "Template code cannot be empty";
    public static final String LETTER_CODE_INVALID = "Template code is invalid";
    public static final String LETTER_DESCRIPTION_NULL = "Template description cannot be empty";
    public static final String LETTER_TEMPLATETITLE_NULL = "Letter title cannot be empty";
    public static final String LETTER_TEMPLATEBODY_NULL = "Letter Body cannot be empty";
    public static final String LETTER_TEMPLATE_STATUS_NULL = "Status cannot be empty";
    public static final String ERROR_DELETE_LETTERCONF = "Error occurred in deleting letter template";
    public static final String SUCCESS_DELETE_LETTERCONF = "Letter template deleted successfully";
    public static final String ERROR_DELETE_LETTERCONFIGURATION = "Error occurred in deleting letter template";
    public static final String ERROR_LOAD_LETTERCONF = "Error occurred in loading letter templates";
    public static final String ERROR_LETTERCONF_GENERAL = "Error occurred in loading general details";
    public static final String SUCCESS_UPDATE_LETTERCONF_GENERAL = "Letter template updated successfully";
    public static final String ERROR_UPDATE_LETTERCONF = "Error occurred in updating letter template";
    public static final String ERROR_ADD_LETTERCONF = "Error occurred in adding new letter template";

    //balance transfer payment plan management
    public static final String ERROR_LOAD_BTPAYMENTPLAN = "Error occurred in loading balance installment transfer payment plan details";
    public static final String ERROR_ADD_BTPAYMENTPLAN = "Error occurred in adding new balance installment transfer payment plan";
    public static final String BTPAYMENTPLAN_PAYMENTPLANCODE_NULL = "Balance installment transfer payment plan code cannot be empty";
    public static final String BTPAYMENTPLAN_PAYMENTPLANCODE_INVALID = "Balance installment transfer payment plan code must be a number";
    public static final String BTPAYMENTPLAN_DURATION_NULL = "Balance installment transfer payment plan duration cannot be empty";
    public static final String BTPAYMENTPLAN_DURATION_INVALID = "Balance installment transfer payment plan duration must be a number";
    public static final String BTPAYMENTPLAN_INTERESTRATE_NULL = "Balance installment transfer payment plan interest rate cannot be empty";
    public static final String BTPAYMENTPLAN_INTERESTRATE_INVALID = "Balance installment transfer payment plan interest rate must be a number";
    public static final String BTPAYMENTPLAN_DESCRIPTION_NULL = "Description cannot be empty";
    public static final String BTPAYMENTPLAN_STATUSCODE_NULL = "Status cannot be empty";
    public static final String BTPAYMENTPLAN_SUCCESS_ADD = "Balance installment transfer payment plan successfully added";
    public static final String ERROR_VIEW_BTPAYMENTPLAN = "Error occurred in user level view";
    public static final String BTPAYMENTPLAN_SUCCESS_DELETE = "Balance installment transfer payment plan successfully deleted";
    public static final String BTPAYMENTPLAN_SUCCESS_UPDATE = "Balance installment transfer payment plan successfully updated";
    public static final String ERROR_DELETE_BTPAYMENTPLAN = "Error occurred in deleting balance installment transfer payment plan";
    public static final String ERROR_UPDATE_BTPAYMENTPLAN = "Error occurred in updating balance installment transfer payment plan";
	
	//balance transfer payment plan management
    public static final String ERROR_LOAD_LOCPAYMENTPLAN = "Error occured in loading loan on card payment plan details";
    public static final String ERROR_ADD_LOCPAYMENTPLAN = "Error occured in adding new loan on card payment plan";
    public static final String LOCPAYMENTPLAN_PAYMENTPLANCODE_NULL = "Loan on card payment plan code cannot be empty";
    public static final String LOCPAYMENTPLAN_PAYMENTPLANCODE_INVALID = "Loan on card payment plan code must be a number";
    public static final String LOCPAYMENTPLAN_DURATION_NULL = "Loan on card payment plan duration cannot be empty";
    public static final String LOCPAYMENTPLAN_DURATION_INVALID = "Loan on card payment plan duration must be a number";
    public static final String LOCPAYMENTPLAN_INTERESTRATE_NULL = "Loan on card payment plan interest rate cannot be empty";
    public static final String LOCPAYMENTPLAN_INTERESTRATE_INVALID = "Loan on card payment plan interest rate must be a number";
    public static final String LOCPAYMENTPLAN_DESCRIPTION_NULL = "Description cannot be empty";
    public static final String LOCPAYMENTPLAN_STATUSCODE_NULL = "Status cannot be empty";
    public static final String LOCPAYMENTPLAN_SUCCESS_ADD = "Loan on card payment plan successfully added";
    public static final String ERROR_VIEW_LOCPAYMENTPLAN = "Error occured in user level view";
    public static final String LOCPAYMENTPLAN_SUCCESS_DELETE = "Loan on card payment plan successfully deleted";
    public static final String LOCPAYMENTPLAN_SUCCESS_UPDATE = "Loan on card payment plan successfully updated";
    public static final String ERROR_DELETE_LOCPAYMENTPLAN = "Error occured in deleting loan on card payment plan";
    public static final String ERROR_UPDATE_LOCPAYMENTPLAN = "Error occured in updating loan on card payment plan";    
    
    //balance transfer payment request
    public static final String BTPR_AMOUNT_NULL = "Please fill both transactin amount to filter";
    public static final String ERROR_LOAD_BTPAYMENTREQ = "Error occurred in loading balance transfer payment request details";
    public static final String BTTXN_AMOUNT_NULL = "Balance Transfer amount cannot be empty";
    public static final String BTINSTALLMENT_AMOUNT_NULL = "Installment amount cannot be empty";
    public static final String BTPAYMENTPLAN_NULL = "Balance Transfer payment plan cannot be empty";
    public static final String BTPAYMENTPLAN_REQ_SUCCESS_ADD = "Successfully added balance transfer payment plan request ";
    public static final String BTTXN_AMOUNT_INVALID = "Transaction amount must be a decimal number";
    public static final String BTINSTALLMENT_AMOUNT_INVALID = "Installment amount must be a number";
    public static final String BTINSTALLMENT_CARDNO_INVALID="Invalid card number.";
    public static final String BTINSTALLMENT_CARDNO_NULL = "Card number cannot be empty";
    public static final String BTINSTALLMENT_OTHERCARD_EXPIRYDATE_NULL = "Card expiry date cannot be empty";
    public static final String BTINSTALLMENT_OTHERCARD_INVALIDMONTH = "Invalid card expiry month.";
    public static final String BTINSTALLMENT_OTHERCARDNAME_NULL = "Name on card cannot be empty";
    public static final String BTINSTALLMENT_ISSUER_NULL = "Issuer cannot be empty";
    public static final String BTINSTALLMENT_DUEDATE_NULL = "Due date cannot be empty";
    public static Object FORM_VALIDATION_GENERIC_ERR_MSG="The form submission failed. Please see the error below";
    
    //loan on card request
    public static final String LOAN_AMOUNT_NULL = "Loan amount cannot be empty";
    public static final String LOAN_INSTALLMENT_AMOUNT_NULL = "Installment amount cannot be empty";
    public static final String LOAN_PAYMENTPLAN_NULL = "Payment plan cannot be empty";
    public static final String LOAN_AMOUNT_INVALID = "Loan amount must be a decimal number";
    public static final String LOAN_INSTALLMENT_AMOUNT_INVALID = "Installment amount must be a number";
    public static final String LOANONCARD_REQ_SUCCESS_ADD = "Successfully added loan on card plan request ";
    public static final String ERROR_LOAD_LOANCARDREQ = "Error occurred in loading loan on card plan request details";
    
    //common
    public static final String SUCCESS_DELETE_COMMON = "Successfully deleted";
    public static final String SUCCESS_UPDATE_COMMON = "Successfully updated";
    
    //Call center customer details change
    public static final String CALL_CENTER_CUS_CHANGE_SELECT_SECTION ="Please select the check box/es that you want to change.  ";

    
}
