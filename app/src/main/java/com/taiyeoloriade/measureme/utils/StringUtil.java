package com.taiyeoloriade.measureme.utils;

/**
 * Created by ValueMinds on 5/5/2016.
 */
public class StringUtil {

    public static boolean verifyString(String str) {
        if (str == null) {
            return false;
        }
        if (str.isEmpty()) {
            return false;
        } else if (str.length() < 2) {
            return false;
        }

        return true;
    }

}
