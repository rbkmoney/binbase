package com.rbkmoney.binbase.batch.processor.classifier;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBaseData;
import com.rbkmoney.binbase.batch.processor.BinBasePsbCsvProcessor;
import com.rbkmoney.binbase.batch.processor.BinBaseXmlProcessor;
import com.rbkmoney.binbase.batch.processor.BinBaseZipCsvProcessor;
import com.rbkmoney.binbase.domain.BinData;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

import java.util.Map;

@RequiredArgsConstructor
public class ProcessorClassifier<T> implements Classifier<BinBaseData, ItemProcessor<?, Map.Entry<BinData, Range<Long>>>> {

    private final BinBasePsbCsvProcessor binBasePsbCsvProcessor;
    private final BinBaseXmlProcessor binBaseXmlProcessor;
    private final BinBaseZipCsvProcessor binBaseZipCsvProcessor;

    @Override
    public ItemProcessor<?, Map.Entry<BinData, Range<Long>>> classify(BinBaseData data) {
        switch (data.getDataType()) {
            case XML:
                return binBaseXmlProcessor;
            case CSV:
                return binBasePsbCsvProcessor;
            case ZIP:
                return binBaseZipCsvProcessor;
            default:
                throw new IllegalArgumentException("Unknown binBase data type: " + data);
        }
    }
}
