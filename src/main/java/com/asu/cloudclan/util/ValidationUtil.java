package com.asu.cloudclan.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rubinder on 10/26/16.
 */
public class ValidationUtil {
    private static Pattern pattern;
    //Thanks to mykong.com for this regex
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    static {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public static boolean isvalidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
