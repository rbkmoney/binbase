package com.rbkmoney.binbase.batch.processor;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBaseCsvData;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.domain.CountryCode;
import com.rbkmoney.binbase.util.CardProductType;
import org.springframework.batch.item.ItemProcessor;

import java.util.AbstractMap;
import java.util.Map;

import static com.rbkmoney.binbase.util.PanUtil.toLongValue;

public class BinBaseCsvProcessor extends BinBaseItemProcessor implements ItemProcessor<BinBaseCsvData, Map.Entry<BinData, Range<Long>>> {

    @Override
    public Map.Entry<BinData, Range<Long>> process(BinBaseCsvData binBaseCsvData) throws Exception {
        BinData binData = new BinData();
        binData.setPaymentSystem(binBaseCsvData.getPaymentSystem());
        binData.setBankName(binBaseCsvData.getBank());
        binData.setIsoCountryCode(CountryCode.getByAlpha3Code(binBaseCsvData.getCountry()));
        binData.setCardType(CardProductType.getByValye(binBaseCsvData.getType()));
        Range<Long> range;
        if (binBaseCsvData.getStartBin().equals(binBaseCsvData.getEndBin())) {
            range = buildRange(binBaseCsvData.getStartBin());
        } else {
            range = Range.openClosed(toLongValue(binBaseCsvData.getStartBin()), toLongValue(binBaseCsvData.getEndBin()));
        }
        return new AbstractMap.SimpleEntry<>(binData, range);
    }

}
