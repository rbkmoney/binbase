package com.rbkmoney.binbase.util;


public class PanUtil {

    public static void validatePan(String pan) throws IllegalArgumentException {
        if (!pan.matches("^\\d{6,19}$")) {
            throw new IllegalArgumentException(String.format("Invalid pan format, pan='%s'", pan));
        }
    }

    public static long toLongValue(String pan) throws IllegalArgumentException {
        validatePan(pan);
        return Long.valueOf(String.format("%-19s", pan).replace(' ', '0'));
    }

}
