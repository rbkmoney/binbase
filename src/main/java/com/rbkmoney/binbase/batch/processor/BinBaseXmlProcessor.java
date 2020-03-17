package com.rbkmoney.binbase.batch.processor;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBaseXmlData;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.domain.CardType;
import com.rbkmoney.binbase.domain.CountryCode;
import org.springframework.batch.item.ItemProcessor;

import java.util.AbstractMap;
import java.util.Map;

public class BinBaseXmlProcessor extends BinBaseItemProcessor implements ItemProcessor<BinBaseXmlData, Map.Entry<BinData, Range<Long>>> {

    @Override
    public Map.Entry<BinData, Range<Long>> process(BinBaseXmlData binBaseXmlData) {
        BinData binData = new BinData();
        binData.setPaymentSystem(binBaseXmlData.getBrand());
        binData.setBankName(binBaseXmlData.getBank());
        binData.setIsoCountryCode(CountryCode.getByNumericCode(binBaseXmlData.getIsonumber()));
        binData.setCardType(convertToCardType(binBaseXmlData.getType()));

        Range<Long> range = buildRange(binBaseXmlData.getBin());
        return new AbstractMap.SimpleEntry<>(binData, range);
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
