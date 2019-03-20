package com.rbkmoney.binbase.batch.processor;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBaseData;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.domain.CardType;
import com.rbkmoney.binbase.domain.CountryCode;
import org.springframework.batch.item.ItemProcessor;

import java.util.AbstractMap;
import java.util.Map;

import static com.rbkmoney.binbase.util.BinBaseConstant.MAX_UPPER_ENDPOINT;
import static com.rbkmoney.binbase.util.BinBaseConstant.DEFAULT_MIN_LOWER_ENDPOINT;
import static com.rbkmoney.binbase.util.PanUtil.toLongValue;

public class BinBaseXmlProcessor implements ItemProcessor<BinBaseData, Map.Entry<BinData, Range<Long>>> {

    @Override
    public Map.Entry<BinData, Range<Long>> process(BinBaseData binBaseXmlData) throws Exception {
        BinData binData = new BinData();
        binData.setPaymentSystem(binBaseXmlData.getBrand());
        binData.setBankName(binBaseXmlData.getBank());
        binData.setIsoCountryCode(CountryCode.getByNumericCode(binBaseXmlData.getIsonumber()));
        binData.setCardType(convertToCardType(binBaseXmlData.getType()));

        Range<Long> range = buildRange(binBaseXmlData.getBin());
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

    private CardType convertToCardType(String type) {
        switch (type) {
            case "DEBIT":
                return CardType.debit;
            case "CHARGE CARD":
                return CardType.charge_card;
            case "CREDIT":
                return CardType.credit;
            case "DEBIT OR CREDIT":
                return CardType.credit_or_debit;
            default:
                return null;
        }
    }

}
