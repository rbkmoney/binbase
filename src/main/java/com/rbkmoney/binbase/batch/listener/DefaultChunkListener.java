package com.rbkmoney.binbase.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class DefaultChunkListener implements ChunkListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final int loggingInterval = 10000;

    @Override
    public void beforeChunk(ChunkContext context) {
        //nothing
    }

    @Override
    public void afterChunk(ChunkContext context) {
        int readCount = context.getStepContext().getStepExecution().getReadCount();
        if (readCount > 0 && readCount % loggingInterval == 0) {
            log.info("{} rows processed", readCount);
        }
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        //nothing
    }
}
