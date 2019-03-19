package com.rbkmoney.binbase.util;


import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import static com.rbkmoney.binbase.util.BinBaseConstant.DEFAULT_CAPACITY;

public class PanUtil {

    public static void validatePan(String pan) throws IllegalArgumentException {
        if (!pan.matches("^\\d{6,19}$")) {
            throw new IllegalArgumentException("Invalid pan format");
        }
    }

    public static long toLongValue(String pan) throws IllegalArgumentException {
        return toLongValue(pan, DEFAULT_CAPACITY);
    }

    public static long toLongValue(String pan, int capacity) throws IllegalArgumentException {
        validatePan(pan);
        return Long.valueOf(StringUtils.rightPad(pan.substring(0, Math.min(pan.length(), capacity)), 18, "0"));
    }

    public static String formatPan(String pan) {
        return StringUtils.rightPad(pan.substring(0, Math.min(pan.length(), 6)), pan.length(), '*');
    }

}
