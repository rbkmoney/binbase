package com.rbkmoney.binbase.batch.processor;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBaseCsvData;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.domain.CountryCode;
import org.springframework.batch.item.ItemProcessor;

import java.util.AbstractMap;
import java.util.Map;

import static com.rbkmoney.binbase.util.CardTypeConverter.convertToCardType;

public class BinBaseCsvProcessor extends BinBaseItemProcessor implements ItemProcessor<BinBaseCsvData, Map.Entry<BinData, Range<Long>>> {

    @Override
    public Map.Entry<BinData, Range<Long>> process(BinBaseCsvData binBaseCsvData) {
        BinData binData = new BinData();
        binData.setPaymentSystem(binBaseCsvData.getBrand());
        binData.setBankName(binBaseCsvData.getBank());
        binData.setIsoCountryCode(CountryCode.getByNumericCode(binBaseCsvData.getIsonumber()));
        binData.setCardType(convertToCardType(binBaseCsvData.getType()));
        binData.setCategory(binBaseCsvData.getCategory());
        Range<Long> range = buildRange(binBaseCsvData.getBin());
        return new AbstractMap.SimpleEntry<>(binData, range);
    }

}
