package com.rbkmoney.binbase.batch.reader.classifier;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.classify.Classifier;
import org.springframework.core.io.Resource;

import java.util.Objects;

import static com.rbkmoney.binbase.util.BinBaseConstant.FILE_EXTENSION_CSV;
import static com.rbkmoney.binbase.util.BinBaseConstant.FILE_EXTENSION_XML;

@RequiredArgsConstructor
public class ResourceClassifier<T> implements Classifier<Resource, ResourceAwareItemReaderItemStream<T>> {

    private final ResourceAwareItemReaderItemStream<T> staxEventItemReader;
    private final ResourceAwareItemReaderItemStream<T> flatFileItemReader;

    @Override
    public ResourceAwareItemReaderItemStream<T> classify(Resource resource) {
        String fileExtension = Objects.requireNonNull(FilenameUtils.getExtension(resource.getFilename()));
        switch (fileExtension) {
            case FILE_EXTENSION_XML:
                return staxEventItemReader;
            case FILE_EXTENSION_CSV:
                return flatFileItemReader;
            default:
                throw new IllegalArgumentException("Unsupported file format: " + resource.getFilename());
        }
    }

}
