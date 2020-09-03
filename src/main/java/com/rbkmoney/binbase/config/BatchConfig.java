package com.rbkmoney.binbase.config;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.batch.BinBaseXmlData;
import com.rbkmoney.binbase.batch.BinBaseCsvData;
import com.rbkmoney.binbase.batch.listener.DefaultChunkListener;
import com.rbkmoney.binbase.batch.processor.BinBaseXmlProcessor;
import com.rbkmoney.binbase.batch.processor.BinBaseCsvProcessor;
import com.rbkmoney.binbase.batch.processor.classifier.ProcessorClassifier;
import com.rbkmoney.binbase.batch.reader.BinDataItemReader;
import com.rbkmoney.binbase.batch.reader.classifier.ResourceClassifier;
import com.rbkmoney.binbase.batch.writer.BinRangeWriter;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.service.BinbaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemProcessorBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
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
@RequiredArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final BinbaseService binbaseService;
    private final String CSV_DELIMITER = ";";
    private final String[] FILE_CSV_FIELDS = new String[]{
            "bin", "brand", "bank", "type", "category", "isoname", "isoa2", "isoa3",
            "isonumber", "url", "phone", "bin_length", "affiliation", "mark"
    };

    @Value("${batch.strict_mode}")
    private boolean strictMode;

    @Bean
    @StepScope
    public MultiResourceItemReader multiResourceItemReader(BinDataItemReader itemReader,
                                                           @Value("${batch.file_path}/*.*") Resource[] resources) {
        return new MultiResourceItemReaderBuilder<Resource[]>()
                .name("multiResourceItemReader")
                .delegate(itemReader)
                .resources(resources)
                .setStrict(strictMode)
                .saveState(true)
                .build();
    }

    @Bean
    public BinDataItemReader binBaseDataBinDataItemReader() {
        BinDataItemReader binDataItemReader = new BinDataItemReader(buildBinBaseXmlReader(), buildBinBasePsbCsvReader());
        binDataItemReader.setClassifier(new ResourceClassifier(
                buildBinBaseXmlReader(), buildBinBasePsbCsvReader()));
        return binDataItemReader;
    }

    @Bean
    public StaxEventItemReader<BinBaseXmlData> buildBinBaseXmlReader() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(BinBaseXmlData.class);

        StaxEventItemReader<BinBaseXmlData> staxEventItemReader = new StaxEventItemReader<>();
        staxEventItemReader.setFragmentRootElementName("record");
        staxEventItemReader.setUnmarshaller(marshaller);
        staxEventItemReader.setStrict(strictMode);
        staxEventItemReader.setSaveState(true);

        return staxEventItemReader;
    }

    @Bean
    public FlatFileItemReader<BinBaseCsvData> buildBinBasePsbCsvReader() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(FILE_CSV_FIELDS);
        lineTokenizer.setDelimiter(CSV_DELIMITER);

        BeanWrapperFieldSetMapper<BinBaseCsvData> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(BinBaseCsvData.class);

        DefaultLineMapper<BinBaseCsvData> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        FlatFileItemReader<BinBaseCsvData> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setLineMapper(lineMapper);
        return flatFileItemReader;
    }

    @Bean
    public Job binBaseJob(Step step) {
        return jobBuilderFactory.get("binBaseJob")
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public ClassifierCompositeItemProcessor compositeProcessor() {
        return new ClassifierCompositeItemProcessorBuilder()
                .classifier(new ProcessorClassifier( new BinBaseXmlProcessor(), new BinBaseCsvProcessor()))
                .build();
    }

    @Bean
    public Step step(MultiResourceItemReader multiResourceItemReader) {
        return stepBuilderFactory.get("binBaseStep")
                .<Resource, Map.Entry<BinData, Range<Long>>>chunk(1000)
                .reader(multiResourceItemReader)
                .processor(compositeProcessor())
                .writer(new BinRangeWriter(binbaseService))
                .listener(new DefaultChunkListener())
                .build();
    }


}
