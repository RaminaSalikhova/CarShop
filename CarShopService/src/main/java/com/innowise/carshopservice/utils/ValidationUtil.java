package com.innowise.carshopservice.utils;

import java.util.regex.Pattern;

public class ValidationUtil {

    private ValidationUtil(){}

    public static boolean validateYear(String number) {
        if(!isNumeric(number)){
            return false;
        }
        if (Integer.parseInt(number)>2022 ||  Integer.parseInt(number)<1900){
            return false;
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validatePhoneNumber(String number) {
        Pattern pattern = Pattern.compile("^\\+375(17|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$");
        return pattern.matcher(number).matches() ? true : false;
    }

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches() ? true : false;
    }
}
