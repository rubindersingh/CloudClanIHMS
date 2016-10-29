package com.asu.cloudclan.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rubinder on 10/26/16.
 */
public class ValidationUtil {
    private static Pattern pattern;
    //Thanks to mykong.com for this regex
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    private static final Set<String> filters = new HashSet<>();
    static {
        pattern = Pattern.compile(EMAIL_PATTERN);


        filters.add("blur");
        filters.add("bump");
        filters.add("edge");
        filters.add("grayscale");
        filters.add("invert");
        filters.add("lensflare");
        filters.add("noise");
        filters.add("pixelate");
        filters.add("sepia");
        filters.add("vintage");
        filters.add("lensblur");

    }

    public static boolean isvalidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isvalidFilter(String filter) {
        return filters.contains(filter);
    }
}
