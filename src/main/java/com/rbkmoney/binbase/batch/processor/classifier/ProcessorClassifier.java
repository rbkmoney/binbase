package com.rbkmoney.binbase.batch.processor.classifier;

import com.rbkmoney.binbase.batch.BinBaseXmlData;
import com.rbkmoney.binbase.batch.processor.BinBaseCsvProcessor;
import com.rbkmoney.binbase.batch.processor.BinBaseXmlProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

@RequiredArgsConstructor
public class ProcessorClassifier<T> implements Classifier<T, ItemProcessor> {

    private final BinBaseCsvProcessor binBaseCsvProcessor;
    private final BinBaseXmlProcessor binBaseXmlProcessor;

    @Override
    public ItemProcessor classify(T data) {
        return data.getClass().isAssignableFrom(BinBaseXmlData.class) ? binBaseXmlProcessor : binBaseCsvProcessor;
    }
}
