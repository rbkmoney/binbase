package com.rbkmoney.binbase.batch.reader.classifier;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.classify.Classifier;
import org.springframework.core.io.Resource;

@RequiredArgsConstructor
public class ResourceClassifier<T> implements Classifier<Resource, ResourceAwareItemReaderItemStream<T>> {

    private final ResourceAwareItemReaderItemStream<T> staxEventItemReader;
    private final ResourceAwareItemReaderItemStream<T> flatFileItemReader;

    @Override
    public ResourceAwareItemReaderItemStream<T> classify(Resource resource) {
        return FilenameUtils.getExtension(resource.getFilename()).matches("xml") ? staxEventItemReader : flatFileItemReader;
    }
}
