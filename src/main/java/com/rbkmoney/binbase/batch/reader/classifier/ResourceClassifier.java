package com.rbkmoney.binbase.batch.reader.classifier;

import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.classify.Classifier;
import org.springframework.core.io.Resource;

public class ResourceClassifier<T> implements Classifier<Resource, ResourceAwareItemReaderItemStream<T>> {

    private ResourceAwareItemReaderItemStream<T> staxEventItemReader;
    private ResourceAwareItemReaderItemStream<T> flatFileItemReader;

    public ResourceClassifier(ResourceAwareItemReaderItemStream<T> staxEventItemReader,
                              ResourceAwareItemReaderItemStream<T> flatFileItemReader) {
        this.staxEventItemReader = staxEventItemReader;
        this.flatFileItemReader = flatFileItemReader;
    }

    @Override
    public ResourceAwareItemReaderItemStream<T> classify(Resource resource) {
        return FilenameUtils.getExtension(resource.getFilename()).matches("xml") ? staxEventItemReader : flatFileItemReader;
    }
}
