package com.andersen.filedatatransferagent.config.batch;

import static com.andersen.filedatatransferagent.constants.UserCsvConstants.HEADERS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import com.andersen.filedatatransferagent.model.user.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.batch.item.kafka.builder.KafkaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.transaction.KafkaTransactionManager;

@Configuration
public class UserCsvDataTransferConfig {

  private static final int CHUNK_SIZE = 100;

  private static final String USER_READER_NAME = "userReader";
  private static final String USER_JOB_NAME = "transferCsvUserDataJob";
  private static final String USER_JOB_STEP_NAME = "transferCsvUserDataStep";
  private static final String DELIMITER = ",";

  @Bean
  @StepScope
  public FlatFileItemReader<User> userReader(
      @Value("#{jobParameters['userCsvData']}") Resource userCsvData,
      final FieldSetMapper<User> userFieldSetMapper
  ) {
    return new FlatFileItemReaderBuilder<User>()
        .name(USER_READER_NAME)
        .resource(userCsvData)
        .delimited()
        .delimiter(DELIMITER)
        .names(HEADERS.toArray(new String[INTEGER_ZERO]))
        .linesToSkip(INTEGER_ONE)
        .fieldSetMapper(userFieldSetMapper)
        .beanMapperStrict(false)
        .targetType(User.class)
        .build();
  }

  @Bean
  public KafkaItemWriter<String, User> userWriter(
      final KafkaTemplate<String, User> kafkaTemplate,
      final @Value("${spring.kafka.topics.user-csv-data-topic}") String topic
  ) {
    kafkaTemplate.setDefaultTopic(topic);
    return new KafkaItemWriterBuilder<String, User>()
        .itemKeyMapper(source -> null)
        .kafkaTemplate(kafkaTemplate)
        .build();
  }

  @Bean
  public Step userJobStep(
      final JobRepository jobRepository,
      final FlatFileItemReader<User> reader,
      final KafkaItemWriter<String, User> writer,
      final KafkaTransactionManager<String, User> kafkaTransactionManager,
      final UserItemWriteListener userItemWriteListener,
      final UseItemReadListener userItemReadListener
  ) {
    return new StepBuilder(USER_JOB_STEP_NAME, jobRepository)
        .<User, User>chunk(CHUNK_SIZE, kafkaTransactionManager)
        .listener(userItemWriteListener)
        .listener(userItemReadListener)
        .reader(reader)
        .writer(writer)
        .build();
  }

  @Bean
  public Job userJob(
      final JobRepository jobRepository,
      final JobExecutionListener userJobListener,
      final Step userJobStep
  ) {
    return new JobBuilder(USER_JOB_NAME, jobRepository)
        .listener(userJobListener)
        .start(userJobStep)
        .build();
  }
}