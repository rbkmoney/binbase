package com.rbkmoney.binbase.batch.processor;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBaseXmlData;
import com.rbkmoney.binbase.domain.BinData;
import org.springframework.batch.item.ItemProcessor;

import java.util.AbstractMap;
import java.util.Map;

import static com.rbkmoney.binbase.util.PanUtil.toLongValue;

public class BinBaseXmlProcessor implements ItemProcessor<BinBaseXmlData, Map.Entry<BinData, Range<Long>>> {

    @Override
    public Map.Entry<BinData, Range<Long>> process(BinBaseXmlData binBaseXmlData) throws Exception {
        BinData binData = new BinData();
        binData.setPaymentSystem(binBaseXmlData.getBrand());
        binData.setBankName(binBaseXmlData.getBank());
        binData.setIsoCountryCode(binBaseXmlData.getIsonumber());
        binData.setCardType(binBaseXmlData.getType());

        Range<Long> range = Range.openClosed(
                toLongValue(binBaseXmlData.getBin()),
                toLongValue(String.valueOf(Long.valueOf(binBaseXmlData.getBin()) + 1L))
        );
        return new AbstractMap.SimpleEntry(binData, range);
    }

}
