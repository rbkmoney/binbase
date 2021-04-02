package com.rbkmoney.binbase.batch.processor.classifier;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBaseData;
import com.rbkmoney.binbase.batch.processor.BinBaseXmlProcessor;
import com.rbkmoney.binbase.batch.processor.BinBaseCsvProcessor;
import com.rbkmoney.binbase.domain.BinData;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

import java.util.Map;

@RequiredArgsConstructor
public class ProcessorClassifier<T>
        implements Classifier<BinBaseData, ItemProcessor<?, Map.Entry<BinData, Range<Long>>>> {

    private final BinBaseXmlProcessor binBaseXmlProcessor;
    private final BinBaseCsvProcessor binBaseCsvProcessor;

    @Override
    public ItemProcessor<?, Map.Entry<BinData, Range<Long>>> classify(BinBaseData data) {
        switch (data.getDataType()) {
            case XML:
                return binBaseXmlProcessor;
            case CSV:
                return binBaseCsvProcessor;
            default:
                throw new IllegalArgumentException("Unknown binBase data type: " + data);
        }
    }
}
