package com.rbkmoney.binbase.batch.processor.classifier;

import com.rbkmoney.binbase.batch.BinBaseXmlData;
import com.rbkmoney.binbase.batch.processor.BinBaseCsvProcessor;
import com.rbkmoney.binbase.batch.processor.BinBaseXmlProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

public class ProcessorClassifier<T> implements Classifier<T, ItemProcessor> {

    private BinBaseCsvProcessor binBaseCsvProcessor;
    private BinBaseXmlProcessor binBaseXmlProcessor;

    public ProcessorClassifier(BinBaseCsvProcessor binBaseCsvProcessor, BinBaseXmlProcessor binBaseXmlProcessor) {
        this.binBaseCsvProcessor = binBaseCsvProcessor;
        this.binBaseXmlProcessor = binBaseXmlProcessor;
    }

    @Override
    public ItemProcessor classify(T data) {
        return data.getClass().getName().equals(BinBaseXmlData.class.getName()) ? binBaseXmlProcessor : binBaseCsvProcessor;
    }
}
