package com.rbkmoney.binbase.config;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBaseData;
import com.rbkmoney.binbase.batch.processor.BinBaseXmlProcessor;
import com.rbkmoney.binbase.batch.writer.BinRangeWriter;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.service.BinbaseService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.Map;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private BinbaseService binbaseService;

    @Value("${batch.strict_mode}")
    private boolean strictMode;

    @Bean
    @StepScope
    public MultiResourceItemReader multiResourceItemReader(
            StaxEventItemReader<BinBaseData> itemReader,
            @Value("${#{jobParameters['file_path']}:${batch.file_path}}/*.xml") Resource[] resources
    ) {
        return new MultiResourceItemReaderBuilder<BinBaseData>()
                .name("multiResourceItemReader")
                .delegate(itemReader)
                .resources(resources)
                .setStrict(strictMode)
                .saveState(true)
                .build();
    }

    @Bean
    public StaxEventItemReader<BinBaseData> buildBinBaseXmlReader() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(BinBaseData.class);

        StaxEventItemReader<BinBaseData> staxEventItemReader = new StaxEventItemReader<>();
        staxEventItemReader.setName("BinBaseXmlReader");
        staxEventItemReader.setFragmentRootElementName("record");
        staxEventItemReader.setUnmarshaller(marshaller);
        staxEventItemReader.setStrict(strictMode);
        staxEventItemReader.setSaveState(true);

        return staxEventItemReader;
    }

    @Bean
    public Job binBaseJob(Step step) {
        return jobBuilderFactory.get("binBaseJob")
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public Step step(MultiResourceItemReader multiResourceItemReader) {
        return stepBuilderFactory.get("binBaseStep")
                .<BinBaseData, Map.Entry<BinData, Range<Long>>>chunk(1000)
                .reader(multiResourceItemReader)
                .processor(new BinBaseXmlProcessor())
                .writer(new BinRangeWriter(binbaseService))
                .build();
    }


}
