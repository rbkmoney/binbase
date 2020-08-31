package com.rbkmoney.binbase.batch.processor;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBaseZipCsvData;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.domain.CountryCode;
import org.springframework.batch.item.ItemProcessor;

import java.util.AbstractMap;
import java.util.Map;

import static com.rbkmoney.binbase.util.CardTypeConverter.convertToCardType;

public class BinBaseZipCsvProcessor extends BinBaseItemProcessor implements ItemProcessor<BinBaseZipCsvData, Map.Entry<BinData, Range<Long>>> {

    @Override
    public Map.Entry<BinData, Range<Long>> process(BinBaseZipCsvData binBaseZipCsvData) {
        BinData binData = new BinData();
        binData.setPaymentSystem(binBaseZipCsvData.getBrand());
        binData.setBankName(binBaseZipCsvData.getBank());
        binData.setIsoCountryCode(CountryCode.getByNumericCode(binBaseZipCsvData.getIsonumber()));
        binData.setCardType(convertToCardType(binBaseZipCsvData.getType()));
        binData.setCategory(binBaseZipCsvData.getCategory());
        Range<Long> range = buildRange(binBaseZipCsvData.getBin());
        return new AbstractMap.SimpleEntry<>(binData, range);
    }

}
