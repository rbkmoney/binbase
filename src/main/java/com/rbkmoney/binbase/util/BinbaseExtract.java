package com.rbkmoney.binbase.util;

public class BinbaseExtract {

    public static int extractCapacity(long pan) {
        return String.valueOf(pan).length();
    }

}
