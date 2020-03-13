package com.rbkmoney.binbase.batch.processor;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBaseCsvData;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.domain.CountryCode;
import com.rbkmoney.binbase.util.CardProductType;
import org.springframework.batch.item.ItemProcessor;

import java.util.AbstractMap;
import java.util.Map;

import static com.rbkmoney.binbase.util.BinBaseConstant.DEFAULT_MIN_LOWER_ENDPOINT;
import static com.rbkmoney.binbase.util.BinBaseConstant.MAX_UPPER_ENDPOINT;
import static com.rbkmoney.binbase.util.PanUtil.toLongValue;

public class BinBaseCsvProcessor implements ItemProcessor<BinBaseCsvData, Map.Entry<BinData, Range<Long>>> {

    @Override
    public Map.Entry<BinData, Range<Long>> process(BinBaseCsvData binBaseCsvData) throws Exception {
        BinData binData = new BinData();
        binData.setPaymentSystem(binBaseCsvData.getPaymentSystem());
        binData.setBankName(binBaseCsvData.getBank());
        binData.setIsoCountryCode(CountryCode.getByAlpha3Code(binBaseCsvData.getCountry()));
        binData.setCardType(CardProductType.getbyValye(binBaseCsvData.getType()));
        Range<Long> range;
        if (binBaseCsvData.getStartBin().equals(binBaseCsvData.getEndBin())) {
            range = buildRange(binBaseCsvData.getStartBin());
        } else {
            range = Range.openClosed(toLongValue(binBaseCsvData.getStartBin()), toLongValue(binBaseCsvData.getEndBin()));
        }
        return new AbstractMap.SimpleEntry(binData, range);
    }

    private Range<Long> buildRange(String bin) {
        long lowerEndpoint = toLongValue(bin);
        long nextBin = toLongValue(String.format("%06d", Long.valueOf(bin) + 1L));
        long upperEndpoint = (nextBin == DEFAULT_MIN_LOWER_ENDPOINT) ? MAX_UPPER_ENDPOINT : nextBin;

        return Range.openClosed(
                lowerEndpoint,
                upperEndpoint
        );
    }

}
