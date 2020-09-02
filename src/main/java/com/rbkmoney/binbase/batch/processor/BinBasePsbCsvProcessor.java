package com.rbkmoney.binbase.batch.processor;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBasePsbCsvData;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.domain.CountryCode;
import com.rbkmoney.binbase.util.CardProductType;
import org.springframework.batch.item.ItemProcessor;

import java.util.AbstractMap;
import java.util.Map;

import static com.rbkmoney.binbase.util.PanUtil.toLongValue;

public class BinBasePsbCsvProcessor extends BinBaseItemProcessor implements ItemProcessor<BinBasePsbCsvData, Map.Entry<BinData, Range<Long>>> {

    @Override
    public Map.Entry<BinData, Range<Long>> process(BinBasePsbCsvData binBasePsbCsvData) throws Exception {
        BinData binData = new BinData();
        binData.setPaymentSystem(binBasePsbCsvData.getPaymentSystem());
        binData.setBankName(binBasePsbCsvData.getBank());
        binData.setIsoCountryCode(CountryCode.getByAlpha3Code(binBasePsbCsvData.getCountry()));
        binData.setCardType(CardProductType.getByValue(binBasePsbCsvData.getType()));
        Range<Long> range;
        if (binBasePsbCsvData.getStartBin().equals(binBasePsbCsvData.getEndBin())) {
            range = buildRange(binBasePsbCsvData.getStartBin());
        } else {
            range = Range.openClosed(toLongValue(binBasePsbCsvData.getStartBin()), toLongValue(binBasePsbCsvData.getEndBin()));
        }
        return new AbstractMap.SimpleEntry<>(binData, range);
    }

}
