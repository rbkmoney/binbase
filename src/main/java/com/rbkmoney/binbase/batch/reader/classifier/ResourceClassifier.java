package com.rbkmoney.binbase.batch.reader.classifier;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.classify.Classifier;
import org.springframework.core.io.Resource;

import java.util.Objects;

import static com.rbkmoney.binbase.util.BinBaseConstant.*;

@RequiredArgsConstructor
public class ResourceClassifier<T> implements Classifier<Resource, ResourceAwareItemReaderItemStream<T>> {

    private final ResourceAwareItemReaderItemStream<T> staxEventItemReader;
    private final ResourceAwareItemReaderItemStream<T> flatFileItemReader;
    private final ResourceAwareItemReaderItemStream<T> zipFlatFileItemReader;

    @Override
    public ResourceAwareItemReaderItemStream<T> classify(Resource resource) {
        String fileExtension = Objects.requireNonNull(FilenameUtils.getExtension(resource.getFilename()));
        switch (fileExtension) {
            case FILE_EXTENSION_XML:
                return staxEventItemReader;
            case FILE_EXTENSION_CSV:
                return flatFileItemReader;
            case FILE_EXTENSION_ZIP:
                return zipFlatFileItemReader;
            default:
                throw new IllegalArgumentException("Unsupported file format: " + resource.getFilename());
        }
    }

}
