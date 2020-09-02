package com.rbkmoney.binbase.batch.processor;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBasePsbCsvData;
import com.rbkmoney.binbase.domain.BinData;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BinBasePsbCsvProcessorTest {

    @Test
    public void process() throws Exception {
        BinBasePsbCsvData binBasePsbCsvData = new BinBasePsbCsvData();
        binBasePsbCsvData.setStartBin("021502");
        binBasePsbCsvData.setEndBin("021513");
        binBasePsbCsvData.setBank("");
        binBasePsbCsvData.setType("MAB");
        binBasePsbCsvData.setCountry("AFG");
        binBasePsbCsvData.setPaymentSystem("MasterCard");


        BinBasePsbCsvProcessor binBasePsbCsvProcessor = new BinBasePsbCsvProcessor();
        Map.Entry<BinData, Range<Long>> dataRangeEntry = binBasePsbCsvProcessor.process(binBasePsbCsvData);
        System.out.println(dataRangeEntry);
        assertEquals(Range.openClosed(21502000000000000L, 21513000000000000L), dataRangeEntry.getValue());

        binBasePsbCsvData.setStartBin("001502");
        binBasePsbCsvData.setEndBin("001502");
        dataRangeEntry = binBasePsbCsvProcessor.process(binBasePsbCsvData);
        System.out.println(dataRangeEntry);
        assertEquals(Range.openClosed(1502000000000000L, 1503000000000000L), dataRangeEntry.getValue());

        binBasePsbCsvData.setStartBin("000502");
        binBasePsbCsvData.setEndBin("000505");
        dataRangeEntry = binBasePsbCsvProcessor.process(binBasePsbCsvData);
        System.out.println(dataRangeEntry);
        assertEquals(Range.openClosed(502000000000000L, 505000000000000L), dataRangeEntry.getValue());

    }

}