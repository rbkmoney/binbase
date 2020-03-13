package com.rbkmoney.binbase.batch.reader;

import com.rbkmoney.binbase.batch.reader.classifier.ResourceClassifier;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.Resource;

public class BinDataItemReader<T> implements ResourceAwareItemReaderItemStream<T> {

    private ResourceClassifier classifier;
    private Resource resource;
    private StaxEventItemReader<T> staxEventItemReader;
    private FlatFileItemReader<T> flatFileItemReader;

    public BinDataItemReader(StaxEventItemReader staxEventItemReader, FlatFileItemReader flatFileItemReader) {
        this.staxEventItemReader = staxEventItemReader;
        this.flatFileItemReader = flatFileItemReader;
    }

    public void setClassifier(ResourceClassifier classifier) {
        this.classifier = classifier;
    }

    @Override
    public T read() throws Exception {
        return readItem((ResourceAwareItemReaderItemStream<T>) classifier.classify(resource));
    }

    private T readItem(ResourceAwareItemReaderItemStream<T> reader) throws Exception {
        return reader.read();
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (FilenameUtils.getExtension(resource.getFilename()).matches("xml")) {
            staxEventItemReader.setResource(resource);
            staxEventItemReader.open(executionContext);
        } else {
            flatFileItemReader.setResource(resource);
            flatFileItemReader.open(executionContext);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        staxEventItemReader.update(executionContext);
        flatFileItemReader.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        staxEventItemReader.close();
        flatFileItemReader.close();
    }
}
