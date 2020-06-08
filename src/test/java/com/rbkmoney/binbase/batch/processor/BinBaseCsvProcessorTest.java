package com.rbkmoney.binbase.batch.processor;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBaseCsvData;
import com.rbkmoney.binbase.domain.BinData;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BinBaseCsvProcessorTest {

    @Test
    public void process() throws Exception {
        BinBaseCsvData binBaseCsvData = new BinBaseCsvData();
        binBaseCsvData.setStartBin("021502");
        binBaseCsvData.setEndBin("021513");
        binBaseCsvData.setBank("");
        binBaseCsvData.setType("MAB");
        binBaseCsvData.setCountry("AFG");
        binBaseCsvData.setPaymentSystem("MasterCard");


        BinBaseCsvProcessor binBaseCsvProcessor = new BinBaseCsvProcessor();
        Map.Entry<BinData, Range<Long>> dataRangeEntry = binBaseCsvProcessor.process(binBaseCsvData);
        System.out.println(dataRangeEntry);
        assertEquals(Range.openClosed(21502000000000000L, 21513000000000000L), dataRangeEntry.getValue());

        binBaseCsvData.setStartBin("001502");
        binBaseCsvData.setEndBin("001502");
        dataRangeEntry = binBaseCsvProcessor.process(binBaseCsvData);
        System.out.println(dataRangeEntry);
        assertEquals(Range.openClosed(1502000000000000L, 1503000000000000L), dataRangeEntry.getValue());

        binBaseCsvData.setStartBin("000502");
        binBaseCsvData.setEndBin("000505");
        dataRangeEntry = binBaseCsvProcessor.process(binBaseCsvData);
        System.out.println(dataRangeEntry);
        assertEquals(Range.openClosed(502000000000000L, 505000000000000L), dataRangeEntry.getValue());

    }

}