package com.rbkmoney.binbase.util;

import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

import static com.rbkmoney.binbase.util.BinBaseConstant.DEFAULT_SIZE;
import static com.rbkmoney.binbase.util.BinBaseConstant.RIGHT_PAD_SIZE;

public class PanUtil {

    public static void validatePan(String pan) throws IllegalArgumentException {
        if (!pan.matches("^\\d{6,19}$")) {
            throw new IllegalArgumentException("Invalid pan format");
        }
    }

    public static long toLongValue(String pan) throws IllegalArgumentException {
        validatePan(pan);
        return Long.parseLong(StringUtils.rightPad(pan.substring(0, Math.min(pan.length(), RIGHT_PAD_SIZE)), DEFAULT_SIZE, "0"));
    }

    public static String formatPan(String pan) {
        return StringUtils.rightPad(pan.substring(0, Math.min(pan.length(), 6)), pan.length(), '*');
    }

}
