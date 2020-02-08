/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.validate;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.PasswordPolicyBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserPasswordBean;
import com.epic.cms.system.util.exception.ValidateException;
import com.epic.cms.system.util.security.CMSMd5;
import com.epic.cms.system.util.variable.DataTypeVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author upul
 */
public class UserInputValidator {

    //if correct numerc value found, then return true
    public static boolean isNumeric(String theInputString) {

        boolean isValid = false;

        for (int i = 0; i < theInputString.length(); i++) {
            char c = theInputString.charAt(i);

            if ((c >= '0') && (c <= '9')) {
                isValid = true;
            } else {
                isValid = false;
                break;
            }

        }

        return isValid;

    }

    //if correct gl acc no value fount, then return true
    public static boolean isValidGlAccNum(String theInputString) {
        boolean isValid = false;
        int j = 0;
        for (int i = 0; i < theInputString.length(); i++) {
            char c = theInputString.charAt(i);

            if ((c >= '0') && (c <= '9')) {
                isValid = true;
            } else if (c == '-') {
                //isValid=true;
                j++;
            } else {
                isValid = false;
                break;
            }

        }
        if ((j == 0 || (j == 1 && theInputString.length() > 1))) {
            isValid = true;
        } else {
            isValid = false;
        }

        return isValid;
    }

    public static boolean isAlphaNumeric(String value) throws Exception {
        boolean valied = true;

        try {

            if (!value.matches("[a-zA-Z0-9]*")) {

                valied = false;
            }

        } catch (Exception ex) {

            valied = false;
        }

        return valied;
    }

    //**********************************************
    public static boolean isAlphaNumericWithSpace(String value) {
        boolean valied = true;
        try {

            if (!value.matches("[a-zA-Z0-9\\s]*")) {
                valied = false;
            }

        } catch (Exception ex) {
            valied = false;
        }
        return valied;
    }
    //***********************************************

    public static boolean isAlpha(String value) throws Exception {
        boolean valied = true;

        try {

            if (!value.matches("[a-zA-Z]*")) {

                valied = false;
            }

        } catch (Exception ex) {

            valied = false;
        }

        return valied;
    }

    public static boolean isSpecialCharacter(String theInputString) {
        boolean isValid = true;
        //String alphabet     =   "abcde"    ;

        if (theInputString.matches("\\w{1}|(\\W{1,}|\\s{1,})|(\\w{0,}(\\W{1,}|\\s{1,}|(\\W{1,}\\s{1,})|\\s{1,}|\\W{1,})\\w{0,}){1,}")) {
            isValid = false;
        }
        return isValid;
    }

    //This method check wheather the input string contains any special characters. If there is any special characters it will return false.
    public static boolean isCorrectString(String theInputString) {
        boolean isValid = true;

        for (int i = 0; i < theInputString.length(); i++) {
            char c = theInputString.charAt(i);

            if ((c == '"') || (c == ';') || (c == ',') || (c == '@') || (c == '|') || (c == '*') || (c == '#') || (c == '{') || (c == '}') || (c == '`') || (c == '?')
                    || (c == '$') || (c == '%') || (c == '^') || (c == '&') || (c == '/') || (c == '>') || (c == '<') || (c == '(') || (c == ')') || (c == '~') || (c == '\'')) {
                isValid = false;
            }
        }

        return isValid;
    }

    //this method check eheather the input company name is valide or not 
    public static boolean isCorrectCompanyName(String theInputString) {
        boolean isValid = true;
        //allow for only &,"()-./
        for (int i = 0; i < theInputString.length(); i++) {
            char c = theInputString.charAt(i);

            if ((c == ';') || (c == '@') || (c == '|') || (c == '*') || (c == '#') || (c == '{') || (c == '}') || (c == '`') || (c == '?')
                    || (c == '$') || (c == '%') || (c == '^') || (c == '>') || (c == '<') || (c == '~') || (c == '\\') || (c == '!') || (c == '[') || (c == ']') || (c == ':') || (c == '_') || (c == '+') || (c == '=')) {
                isValid = false;
            }
        }

        return isValid;
    }

    //This method check wheather the input string contains any special characters. If there is any special characters it will return false.
    public static boolean isCorrectAddress(String theInputString) {
        boolean isValid = true;

        try {
            if (!theInputString.matches("^[a-zA-Z0-9&#192;&#193;&#194;&#195;&#196;&#197;&#198;&#199;&#200;&#201;&#202;&#203;&#204;&#205;&#206;&#207;&#208;&#209;&#210;&#211"
                    + ";&#212;&#213;&#214;&#216;&#217;&#218;&#219;&#220;&#221;&#223;&#224;&#225;&#226;&#227;&#228;&#229;&#230;&#231;&#232;"
                    + "&#233;&#234;&#235;&#236;&#237;&#238;&#239;&#241;&#242;&#243;&#244;&#245;&#246;&#248;&#249;&#250;&#251;&#252;&"
                    + "#253;&#255;\\.\\,\\-\\/\\'\\:]+[a-zA-Z0-9&#192;&#193;&#194;&#195;&#196;&#197;&#198;&#199;&#200;&#201;&#202;&#203;&#204;"
                    + "&#205;&#206;&#207;&#208;&#209;&#210;&#211;&#212;&#213;&#214;&#216;&#217;&#218;&#219;&#220;&#221;&#223;&#224;&#225;&#226;&"
                    + "#227;&#228;&#229;&#230;&#231;&#232;&#233;&#234;&#235;&#236;&#237;&#238;&#239;&#241;&#242;&#243;&#244;&#245;&#246;&#248;&"
                    + "#249;&#250;&#251;&#252;&#253;&#255;\\.\\,\\-\\/\\'\\: ]+$")) {

                isValid = false;
            }
        } catch (Exception ex) {

            isValid = false;
        }

        return isValid;
    }

    //This method check wheather the input string contains any special characters unsuitable for folders. If there is any special characters it will return false.
    public static boolean isValidFolderName(String theInputString) {
        boolean isValid = true;

        for (int i = 0; i < theInputString.length(); i++) {
            char c = theInputString.charAt(i);

            if ((c == '"') || (c == ';') || (c == ',') || (c == '|') || (c == '#') || (c == '{') || (c == '}') || (c == '?')
                    || (c == '$') || (c == '%') || (c == '^') || (c == '&') || (c == '>') || (c == '<') || (c == '(') || (c == ')') || (c == '~')) {
                isValid = false;
            }
        }

        return isValid;
    }

    //This method check wheather the input string contains any special characters or numeric characters. If there is any  it will return false.
    public static boolean isNonNumericNonSpecialString(String theInputString) {
        boolean isValid = true;

        for (int i = 0; i < theInputString.length(); i++) {
            char c = theInputString.charAt(i);

            if ((c == '"') || (c == ';') || (c == ',') || (c == '@') || (c == '|') || (c == '*') || (c == '#') || (c == '{') || (c == '}') || (c == '`') || (c == '?')
                    || (c == '$') || (c == '%') || (c == '^') || (c == '&') || (c == '/') || (c == '>') || (c == '<') || (c == '(') || (c == ')') || (c == '~') || (c == '\'')) {
                isValid = false;
            } else if ((c >= '0') && (c <= '9')) {
                isValid = false;
            }
        }

        return isValid;
    }

    public static boolean isAllowNumericNonSpecialString(String theInputString) {
        boolean isValid = true;

        for (int i = 0; i < theInputString.length(); i++) {
            char c = theInputString.charAt(i);

            if ((c == '"') || (c == ';') || (c == ',') || (c == '@') || (c == '|') || (c == '*') || (c == '#') || (c == '{') || (c == '}') || (c == '`') || (c == '?')
                    || (c == '$') || (c == '%') || (c == '^') || (c == '&') || (c == '/') || (c == '>') || (c == '<') || (c == '(') || (c == ')') || (c == '~') || (c == '\'')) {
                isValid = false;
            }
        }

        return isValid;
    }

    //if special characters found, then method will return false
    //if valid string, then return true
    public static boolean isString(String theInputString) {

        boolean isValid = true;

        // This argument will validate that the Input String doesn't contain any Special characters any Spaces. Numeric characters are allowed.
        if (theInputString.matches("\\w{1}|(\\W{1,}|\\s{1,})|(\\w{0,}(\\W{1,}|\\s{1,}|(\\W{1,}\\s{1,})|\\s{1,}|\\W{1,})\\w{0,}){1,}")) {
            isValid = false;
        }

        return isValid;

    }

    //if valid, then return true
    public static boolean isValidEmail(String theInputString) {

        boolean isValid = true;

        //Set the email pattern string
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

        //Match the given string with the pattern
        Matcher m = p.matcher(theInputString);

        //Check whether match is found
        boolean matchFound = m.matches();

        if (!matchFound) {
            isValid = false;
        }

        return isValid;

    }
    //  ********************  This is a method to validate the decimal numeric with precision 14 and scale 7 ********************
    private static final String DECIMAL_NUMERIC_PATTERN = "\\d{0,7}\\.\\d{0,7}";
    private static final String DECIMAL_NUMERIC_PATTERN_1 = "\\d{0,7}";

    public static boolean isDecimal_Numeric(String inputString) {
        boolean validFlag = false;
        Pattern p = Pattern.compile(DECIMAL_NUMERIC_PATTERN);
        Pattern q = Pattern.compile(DECIMAL_NUMERIC_PATTERN_1);
        Matcher m = p.matcher(inputString.trim());
        Matcher n = q.matcher(inputString.trim());
        if (m.matches() || n.matches()) {
            validFlag = true;
        }
        return validFlag;
    }
    //  ********************  This is a method to validate the decimal numeric Format ********************
    private static final String DECIMALNUMERIC_PATTERN = "\\d{0,15}\\.\\d{0,2}";

    public static boolean isDecimalNumeric(String inputString) {
        boolean validFlag = false;
        Pattern p = Pattern.compile(DECIMALNUMERIC_PATTERN);
        Matcher m = p.matcher(inputString.trim());
        if (m.matches()) {
            validFlag = true;
        }
        return validFlag;
    }

    public static boolean isCurrency(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    //  ********************  This is a method to validate the decimal numeric Format with any number of scale and precision ********************
    public static boolean isDecimalOrNumeric(String inputString, String integer, String decimal) {
        final String COMMON_PATTERN = "\\d{0," + integer + "}\\.\\d{0," + decimal + "}";
        final String COMMON_PATTERN_1 = "\\d{0," + integer + "}";
        boolean validFlag = false;
        Pattern p = Pattern.compile(COMMON_PATTERN);
        Pattern q = Pattern.compile(COMMON_PATTERN_1);
        Matcher m = p.matcher(inputString.trim());
        Matcher n = q.matcher(inputString.trim());
        if (m.matches() || n.matches()) {
            validFlag = true;
        }
        return validFlag;
    }
//  ********************  This is a method to validate URL ********************
    //port numbers not allowed///////
    private static final String URL_PATTERN = "^((http|https|ftp):\\/\\/).*$";
    //private static final String URL_PATTERN="^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";

    public static boolean isValidUrl(String url) {

        boolean flag = false;
        Pattern p = Pattern.compile(URL_PATTERN);
        Matcher m = p.matcher(url.trim());
        if (m.matches()) {
            flag = true;
        }

        return flag;
    }

    public static boolean isEmptyField(String name, String value, Boolean throwException) throws ValidateException {
        boolean validFlag = false;
        //validate User Role Type
        String message;

        if (value.contentEquals("") || value.length() <= 0) {
            if (throwException) {
                message = name + "" + MessageVarList.EMPTY_ERROR_MESSAGE;
                throw new ValidateException(message);
            }

        } else {

            validFlag = true;
        }

        return validFlag;

    }

    public static boolean isNumber(String name, String value, Boolean flag) throws ValidateException {
        boolean validFlag = false;
        //validate User Role Type

        if (isEmptyField(name, value, flag)) {

            validFlag = isNumeric(value);

            //  errorMessage = MessageVarList.CARDASSOCIATION_CODE_NULL;
        } else {
            validFlag = false;
            throw new ValidateException(MessageVarList.NUMBER_ERROR_MESSAGE);

        }

        return validFlag;

    }

    public static boolean isCorrectIp(String theInputString) throws ValidateException {
        boolean isValid = true;
        int t = 0;
        try {

            String[] values = theInputString.split("\\.");

            for (String s : values) {

                try {
                    t = Integer.parseInt(s);

                } catch (Exception e) {
                    isValid = false;
                    throw new ValidateException(MessageVarList.NUMBER_ERROR_MESSAGE);
                }

                if (t < 0 || t > 255) {
                    isValid = false;
                    throw new ValidateException(MessageVarList.NUMBER_ERROR_MESSAGE);
                }
                if (values.length != 4) {
                    isValid = false;
                    throw new ValidateException(MessageVarList.SIZE_ERROR_MESSAGE);
                }

                isValid = true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }

    public static boolean isCorrectPort(String theInputString) throws ValidateException {
        boolean isValid = true;
        int t = 0;
        try {

            try {

                t = Integer.parseInt(theInputString);

            } catch (Exception e) {
                isValid = false;
                throw new ValidateException(MessageVarList.NUMBER_ERROR_MESSAGE);
            }

            if (t < 0 || t > 65536) {
                isValid = false;
                throw new ValidateException(MessageVarList.SIZE_ERROR_MESSAGE);
            }

            isValid = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }

    public static boolean isCorrectHost(String theInputString) throws ValidateException {
        boolean isValid = false;
        String hostPattern = "^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$";  // note will allow _ in host name
        Pattern r = Pattern.compile(hostPattern);
        Matcher m = r.matcher(theInputString);
        if (m.find()) {
            isValid = true;
        } else {
            isValid = false;
        }

        return isValid;
    }

    public static boolean isCorrectPostDate(String theInputPostDate, String savedPostDate) throws ValidateException {

        boolean isValid = true;

        try {

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

            Date editDate = df.parse(theInputPostDate);

            // Get Date 2
            Date savedDate = df.parse(savedPostDate);

            Calendar sd = Calendar.getInstance();
            Calendar ed = Calendar.getInstance();

            sd.setTime(savedDate);
            ed.setTime(editDate);

            if (ed.equals(sd)) {
                isValid = true;
            } else {
                ed.add(ed.DATE, 1);

                if (ed.equals(sd)) {
                    isValid = true;

                } else {
                    isValid = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }

    public static boolean luhnValidate(String numberString) {

        char[] charArray = numberString.toCharArray();
        int[] number = new int[charArray.length];
        int total = 0;

        for (int i = 0; i < charArray.length; i++) {
            number[i] = Character.getNumericValue(charArray[i]);
        }

        for (int i = number.length - 2; i > -1; i -= 2) {
            number[i] *= 2;

            if (number[i] > 9) {
                number[i] -= 9;
            }
        }

        for (int i = 0; i < number.length; i++) {
            total += number[i];
        }

        if (total % 10 != 0) {
            return false;
        }

        return true;
    }

    public static boolean checkNIC(String nic) {
        boolean status = true;
        try {

            if (nic.length() == 10) {
                String nicFirst_9_Digit = nic.substring(0, 9);
                String nicLastCharacter = nic.substring(9, 10);

                if (!isNumeric(nicFirst_9_Digit)) {
                    status = false;

                }
                if (!nicLastCharacter.equalsIgnoreCase("v") && !nicLastCharacter.equalsIgnoreCase("x")) {
                    status = false;

                }
            } else if (nic.length() == 12) {
                if (!isNumeric(nic)) {
                    status = false;
                }

            } else {
                status = false;
            }

        } catch (Exception e) {
            status = false;
        }
        return status;
    }

    public static boolean isValidDate(String value) {

        if (!value.matches("^(0[1-9]|[12][0-9]|3[01])[-](0[1-9]|1[012])[-](19|20)\\d\\d$")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidMonth(String value) {

        if (!value.matches("[0]{1}[0-9]{1}") && !value.matches("[1]{1}[012]{1}")) {
            return false;
        } else {
            return true;
        }
    }

/////////this methos is only for xreditscore value and max value validation
    public static boolean isValidValueForCreditScoreRuleInCms(String dataType, String value) {
        boolean isValid = false;
        try {

            if (dataType.equals(DataTypeVarList.AMOUNT_DATA)) {

                isValid = isDecimalNumeric(value);

            } else if (dataType.equals(DataTypeVarList.NUMBER_DATA)) {
                isValid = isNumeric(value);

            } else if (dataType.equals(DataTypeVarList.STRING_DATA)) {
                isValid = isCorrectString(value);

            }

        } catch (Exception ex) {
            isValid = false;
        } finally {
            return isValid;
        }

    }

    public static boolean isHexxx(String hex) {
        return ((hex.matches("^[\\da-fA-F]+$")));
    }

    public String CheckPasswordValidity(String password, PasswordPolicyBean passwordPolicyBean, List<UserPasswordBean> userPasswordBeanList) throws Exception {

        boolean flag = true;
        List<Character> list = new ArrayList<Character>();
        String returnMsg = null;

        try {

            if (password.length() > Integer.valueOf(passwordPolicyBean.getMaxLen().toString())) {
                returnMsg = "Invalid Password. Length exceed";
                flag = false;
            }
            if (password.length() < Integer.valueOf(passwordPolicyBean.getMinLen().toString())) {
                returnMsg = "Invalid Password. Length is too short";
                flag = false;
            }

            if (flag) {
                int digits = 0;
                int upper = 0;
                int lower = 0;
                int special = 0;

                for (int i = 0; i < password.length(); i++) {
                    char c = password.charAt(i);
                    list.add(c);
                    if (Character.isDigit(c)) {
                        digits++;
                    } else if (Character.isUpperCase(c)) {
                        upper++;
                    } else if (Character.isLowerCase(c)) {
                        lower++;
                    } else {
                        special++;
                    }
                }

                if (lower < Integer.valueOf(passwordPolicyBean.getMinLowerCaseCharacter().toString())) {
                    returnMsg = "Lower case characters are less than required";
                    flag = false;
                } else if (upper < Integer.valueOf(passwordPolicyBean.getMinUpperCase().toString())) {
                    returnMsg = "Upper case characters are less than required";
                    flag = false;
                } else if (digits < Integer.valueOf(passwordPolicyBean.getMinNumCharacter().toString())) {
                    returnMsg = "Numerical characters are less than required";
                    flag = false;
                } else if (special < Integer.valueOf(passwordPolicyBean.getMinSpclCharacterLen().toString())) {
                    returnMsg = "Special characters are less than required";
                    flag = false;
                }
//                    else if (special > Integer.valueOf(passwordPolicyBean.getAllowedspecialcharacters().toString()))
//                    {
//                        returnMsg   =   "Invalid Password. There are more special characters than required";
//                        flag        =   false;
//                    }

            }

            if (flag) {

                Set<Character> set = new HashSet<Character>();

                int noOfRepeatChar = 0;
                do {
                    Character[] rechar = list.toArray(new Character[0]);
                    list.clear();
                    set.clear();
                    for (Character c : rechar) {
                        if (!set.add(c)) {
                            list.add(c);
                        }
                    }
                    noOfRepeatChar++;
                    if (Integer.valueOf(passwordPolicyBean.getAllowedReptCharacters()) < noOfRepeatChar) {
                        returnMsg = MessageVarList.SYS_Add_UPDATE_USER_PASSWORD_MORE_CHAR_REPEAT + " " + passwordPolicyBean.getAllowedReptCharacters();
                        flag = false;
                        break;
                    }
                } while (!list.isEmpty());

            }

            //validate duplicate passwords.
            if (flag && userPasswordBeanList != null && !userPasswordBeanList.isEmpty()) {
                int restrictedNoOfHistoryPws = Integer.parseInt(passwordPolicyBean.getNoOfHistoryPasswords());
                List<String> restrictPwList = new ArrayList<String>();
                for (int i = 0; i < restrictedNoOfHistoryPws; i++) {
                    if (userPasswordBeanList.size() > i) {
                        restrictPwList.add(userPasswordBeanList.get(i).getPassword());
                    }
                }

                if (restrictPwList.contains(CMSMd5.cmsMd5(password))) {
                    returnMsg = "Cannot use old password.";
                    flag = false;
                }
            }

        } catch (Exception e) {
            throw e;
        }

        return returnMsg;

    }

    // Check whether given string pattern contains only alphanumeric and spaces OR not
    // input string only has spaces and alpha numeric, return true, otherwise false
    public static boolean isDescription(String value) throws Exception {
        boolean valied = false;

        try {

            if (value.matches("[[a-zA-Z0-9]*[[\\s]|[_]|[-]][a-zA-Z0-9]*]*")) {

                valied = true;
            }

        } catch (Exception ex) {

            valied = false;
        }

        return valied;
    }

    public static boolean isPersonName(String value) throws Exception {
        boolean valied = false;

        try {

            if (value.matches("[[a-zA-Z]*[[\\s]][a-zA-Z]*]*")) {

                valied = true;
            }

        } catch (Exception ex) {

            valied = false;
        }

        return valied;
    }

    public static boolean isQuestion(String value) throws Exception {
        boolean valied = false;

        try {

            if (value.matches("[[a-zA-Z0-9]*[\\s][a-zA-Z0-9]*]*[\\s]?[?]?")) {

                valied = true;
            }

        } catch (Exception ex) {

            valied = false;
        }

        return valied;
    }

    public static boolean isAccountNumber(String theInputString) {

        boolean isValid = false;

        for (int i = 0; i < theInputString.length(); i++) {
            char c = theInputString.charAt(i);

            if ((c >= '0') && (c <= '9')) {
                isValid = true;
            } else {
                isValid = false;
                break;
            }

        }
        if (theInputString.length() > 12 || theInputString.length() < 6) {
            isValid = false;
        }

        return isValid;

    }

    public static boolean isPhoneNumber(String theInputString) {

        boolean isValid = false;

        for (int i = 0; i < theInputString.length(); i++) {
            char c = theInputString.charAt(i);

            if ((c >= '0') && (c <= '9')) {
                isValid = true;
            } else {
                isValid = false;
                break;
            }

        }
        if (theInputString.length() > 20 || theInputString.length() < 10) {
            isValid = false;
        }

        return isValid;

    }
}
