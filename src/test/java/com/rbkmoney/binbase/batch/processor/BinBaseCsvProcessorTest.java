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
        binBaseCsvData.setBin("021502");
        binBaseCsvData.setBrand("PRIVATE LABEL");
        binBaseCsvData.setBank("");
        binBaseCsvData.setType("DEBIT");
        binBaseCsvData.setCategory("");
        binBaseCsvData.setIsoname("UNITED STATES");
        binBaseCsvData.setIsoa2("US");
        binBaseCsvData.setIsoa3("USA");
        binBaseCsvData.setIsonumber("840");
        binBaseCsvData.setUrl("");
        binBaseCsvData.setPhone("");


        BinBaseCsvProcessor binBaseCsvProcessor = new BinBaseCsvProcessor();
        Map.Entry<BinData, Range<Long>> dataRangeEntry = binBaseCsvProcessor.process(binBaseCsvData);
        System.out.println(dataRangeEntry);
        assertEquals(Range.openClosed(21502000000000000L, 21503000000000000L), dataRangeEntry.getValue());

        binBaseCsvData.setBin("001502");
        dataRangeEntry = binBaseCsvProcessor.process(binBaseCsvData);
        System.out.println(dataRangeEntry);
        assertEquals(Range.openClosed(1502000000000000L, 1503000000000000L), dataRangeEntry.getValue());

        binBaseCsvData.setBin("000502");
        dataRangeEntry = binBaseCsvProcessor.process(binBaseCsvData);
        System.out.println(dataRangeEntry);
        assertEquals(Range.openClosed(502000000000000L, 503000000000000L), dataRangeEntry.getValue());

    }

}