package com.rbkmoney.binbase.batch.processor;

import com.google.common.collect.Range;

import static com.rbkmoney.binbase.util.BinBaseConstant.DEFAULT_MIN_LOWER_ENDPOINT;
import static com.rbkmoney.binbase.util.BinBaseConstant.MAX_UPPER_ENDPOINT;
import static com.rbkmoney.binbase.util.PanUtil.toLongValue;

public abstract class BinBaseItemProcessor {

    protected Range<Long> buildRange(String bin) {
        long lowerEndpoint = toLongValue(bin);
        long nextBin = toLongValue(String.format("%06d", Long.parseLong(bin) + 1L));
        long upperEndpoint = (nextBin == DEFAULT_MIN_LOWER_ENDPOINT) ? MAX_UPPER_ENDPOINT : nextBin;

        return Range.openClosed(
                lowerEndpoint,
                upperEndpoint
        );
    }

}
