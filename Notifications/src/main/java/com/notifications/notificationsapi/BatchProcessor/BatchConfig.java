package com.notifications.notificationsapi.BatchProcessor;


import com.notifications.notificationsapi.Models.Customer;
import com.notifications.notificationsapi.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final JobRepository jobRepository;
    private final CustomerRepository customerRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Value("${csv.file-path}")
    private String csvFilePath;


    @Bean
    public FlatFileItemReader<CustomerBatchDTO> itemReader() {
        FlatFileItemReader<CustomerBatchDTO> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(csvFilePath));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }


    @Bean
    public CustomerBatchProcessor customerBatchProcessor() {
        return new CustomerBatchProcessor();
    }

    //writer
    @Bean
    public RepositoryItemWriter<Customer> writer() {
        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
        writer.setRepository(customerRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step importStep() {
        return new StepBuilder("csvImport", jobRepository)
                .<CustomerBatchDTO, Customer>chunk(10, platformTransactionManager)
                .reader(itemReader())
                .processor(customerBatchProcessor())
                .writer(writer())
//                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job runJob() throws DataIntegrityViolationException {

        return new JobBuilder("importCustomers", jobRepository)
                .start(importStep())
                .build();

    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }


    private LineMapper<CustomerBatchDTO> lineMapper() {
        DefaultLineMapper<CustomerBatchDTO> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("firstName", "lastName", "email", "phone", "optInSMS", "optInEmail", "optInPromotional");

        BeanWrapperFieldSetMapper<CustomerBatchDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CustomerBatchDTO.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}
