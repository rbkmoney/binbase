package com.rbkmoney.binbase.batch.processor;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBaseZipCsvData;
import com.rbkmoney.binbase.domain.BinData;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BinBaseZipCsvProcessorTest {

    @Test
    public void process() throws Exception {
        BinBaseZipCsvData binBaseZipCsvData = new BinBaseZipCsvData();
        binBaseZipCsvData.setBin("021502");
        binBaseZipCsvData.setBrand("PRIVATE LABEL");
        binBaseZipCsvData.setBank("");
        binBaseZipCsvData.setType("DEBIT");
        binBaseZipCsvData.setCategory("");
        binBaseZipCsvData.setIsoname("UNITED STATES");
        binBaseZipCsvData.setIsoa2("US");
        binBaseZipCsvData.setIsoa3("USA");
        binBaseZipCsvData.setIsonumber("840");
        binBaseZipCsvData.setUrl("");
        binBaseZipCsvData.setPhone("");


        BinBaseZipCsvProcessor binBaseZipCsvProcessor = new BinBaseZipCsvProcessor();
        Map.Entry<BinData, Range<Long>> dataRangeEntry = binBaseZipCsvProcessor.process(binBaseZipCsvData);
        System.out.println(dataRangeEntry);
        assertEquals(Range.openClosed(21502000000000000L, 21503000000000000L), dataRangeEntry.getValue());

        binBaseZipCsvData.setBin("001502");
        dataRangeEntry = binBaseZipCsvProcessor.process(binBaseZipCsvData);
        System.out.println(dataRangeEntry);
        assertEquals(Range.openClosed(1502000000000000L, 1503000000000000L), dataRangeEntry.getValue());

        binBaseZipCsvData.setBin("000502");
        dataRangeEntry = binBaseZipCsvProcessor.process(binBaseZipCsvData);
        System.out.println(dataRangeEntry);
        assertEquals(Range.openClosed(502000000000000L, 503000000000000L), dataRangeEntry.getValue());

    }

}