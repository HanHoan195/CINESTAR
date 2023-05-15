package utils;

import java.util.regex.Pattern;

public class ValidateUtils {
    public static final String NAME_REGEX = "^([A-ZÀ-ỹ][a-zÀ-ỹ]*[ ]?)+$";
    public static final String PHONE_REGEX = "((84|0)[3|5|7|8|9])+([0-9]{8})\\b";
    public static final String ADDRESS_REGEX = "^([^. ][.]*[ ]?)+$";
    public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final  String DAY_REGEX = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
    public static final String MONTH_REGEX = "^([12]\\d{3}-(0[1-9]|1[0-2]))$";
    public static final String YEAR_REGEX = "^(19|[2-9][0-9])\\d{2}$";


    public static boolean isMonth(String month) {
        return Pattern.matches(MONTH_REGEX,month);
    }
    public static boolean isYear(String year) {
        return Pattern.matches(YEAR_REGEX,year);
    }
    public static boolean isNameValid(String name){
        return Pattern.compile(NAME_REGEX).matcher(name).matches();
    }
    public static boolean isPhoneNumber(String number) {
        return Pattern.matches(PHONE_REGEX, number);
    }
    public static boolean isDay(String date) {
        return  Pattern.matches(DAY_REGEX,date);
    }
    public static boolean isAddressValid(String number){
        return Pattern.compile(ADDRESS_REGEX).matcher(number).matches();
    }
    public static boolean isEmailValid(String name) {
        return Pattern.compile(EMAIL_REGEX).matcher(name).matches();
    }
}
