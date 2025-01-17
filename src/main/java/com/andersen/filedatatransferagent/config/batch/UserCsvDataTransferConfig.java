package com.andersen.filedatatransferagent.config.batch;

import static com.andersen.filedatatransferagent.constants.UserCsvConstants.HEADERS;
import static com.andersen.filedatatransferagent.utils.FileUtils.readFileBytes;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import com.andersen.filedatatransferagent.model.user.User;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.batch.item.kafka.builder.KafkaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.transaction.KafkaTransactionManager;

/**
 * UserCsvDataTransferConfig is a configuration class that sets up the necessary beans
 * for transferring user data from a CSV file to a Kafka topic.
 */
@Configuration
public class UserCsvDataTransferConfig {

  private static final int CHUNK_SIZE = 100;

  private static final String USER_READER_NAME = "userReader";
  private static final String USER_JOB_NAME = "transferCsvUserDataJob";
  private static final String USER_JOB_STEP_NAME = "transferCsvUserDataStep";
  private static final String SYNCHRONIZATION_ALWAYS = "SYNCHRONIZATION_ALWAYS";
  private static final String DELIMITER = ",";
  private static final String UNDERSCORE = "_";

  /**
   * The userReader method is a factory method that creates a FlatFileItemReader for reading User objects from a CSV file.
   *
   * @param userCsvData The path to the CSV file containing the user data. It is retrieved from the job parameters.
   * @param userLineMapper The LineMapper implementation used to map each line of the CSV file to a User object.
   * @return A FlatFileItemReader for reading User objects from the CSV file.
   */
  @Bean
  @StepScope
  public FlatFileItemReader<User> userReader(
      @Value("#{jobParameters['users.csv']}") final String userCsvData,
      final LineMapper<User> userLineMapper
  ) {
    return new FlatFileItemReaderBuilder<User>()
        .name(USER_READER_NAME)
        .beanMapperStrict(false)
        .targetType(User.class)
        .linesToSkip(INTEGER_ONE)
        .lineMapper(userLineMapper)
        .resource(new ByteArrayResource(readFileBytes(Paths.get(userCsvData))))
        .delimited()
        .delimiter(DELIMITER)
        .names(HEADERS.toArray(new String[INTEGER_ZERO]))
        .build();
  }

  /**
   * Returns a KafkaItemWriter instance for writing User objects to a Kafka topic.
   *
   * @param kafkaTemplate The instance of KafkaTemplate to use for writing to Kafka.
   * @param topic The topic name to which the User objects will be written.
   * @return A KafkaItemWriter instance.
   */
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

  /**
   * Generates a Step object for the user job.
   *
   * @param jobRepository            The JobRepository to use for the step.
   * @param reader                   The FlatFileItemReader to use for reading User objects from a CSV file.
   * @param writer                   The KafkaItemWriter to use for writing User objects to a Kafka topic.
   * @param kafkaTransactionManager  The KafkaTransactionManager to use for transactional writing.
   * @param userItemWriteListener    The UserItemWriteListener to use for handling write errors during writing.
   * @param userItemReadListener     The UseItemReadListener to use for handling read errors during reading.
   * @return A Step object for the user job.
   */
  @Bean
  public Step userJobStep(
      final JobRepository jobRepository,
      final FlatFileItemReader<User> reader,
      final KafkaItemWriter<String, User> writer,
      final KafkaTransactionManager<String, User> kafkaTransactionManager,
      final UserItemWriteListener userItemWriteListener,
      final UseItemReadListener userItemReadListener
  ) {
    kafkaTransactionManager.setTransactionSynchronizationName(SYNCHRONIZATION_ALWAYS);
    return new StepBuilder(USER_JOB_STEP_NAME, jobRepository)
        .<User, User>chunk(CHUNK_SIZE, kafkaTransactionManager)
        .listener(userItemWriteListener)
        .listener(userItemReadListener)
        .reader(reader)
        .writer(writer)
        .build();
  }

  /**
   * Creates a Job object for the user job.
   *
   * @param jobRepository The JobRepository to use for the job.
   * @param userJobListener The JobExecutionListener to use for the job.
   * @param userJobStep The Step to execute as part of the job.
   * @return A Job object for the user job.
   */
  @Bean
  public Job userJob(
      final JobRepository jobRepository,
      final JobExecutionListener userJobListener,
      final Step userJobStep
  ) {
    final String jobName = USER_JOB_NAME.concat(UNDERSCORE).concat(UUID.randomUUID().toString());
    return new JobBuilder(jobName, jobRepository)
        .listener(userJobListener)
        .start(userJobStep)
        .build();
  }
}
