package com.andersen.filedatatransferagent.controller;

import static com.andersen.filedatatransferagent.utils.KafkaUtils.getConsumerProperties;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

import com.andersen.filedatatransferagent.ConfiguredIntegrationTest;
import com.andersen.filedatatransferagent.model.user.User;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.ResponseSpec;

class UserCsvDataTransferControllerIT extends ConfiguredIntegrationTest {

  private static final String FIND_JOB_EXEC_ID = "select job_execution_id "
      + "from batch_job_execution_params "
      + "where parameter_name = 'users.csv'";

  private static final String FIND_JOB_STATUS = "select status "
      + "from batch_job_execution "
      + "where job_execution_id = ?";
  private static final String FIND_FAILED_RECORDS = "select ucte.id "
      + "from user_csv_transfer_errors ucte";

  private static final String TRIGGER_PATH = "api/v1/users/csv/transfer";
  private static final String COMPLETED = "COMPLETED";
  private static final String FAILED = "FAILED";

  private static final long TIMEOUT = 5L;
  private static final String USER_CSV_DATA_TOPIC = "user-csv-data";
  private static final String EXPECTED_NAME = "John";
  private static final String EXPECTED_L_NAME = "Doe";
  private static final String EXPECTED_MAIL = "john@example.com";
  private static final String FILENAME = "users.csv";

  @Value("classpath:users.csv")
  private Resource usersCsvResource;
  @Value("classpath:users-incorrect.csv")
  private Resource usersIncorrectCsvResource;

  @Autowired
  private KafkaProperties kafkaProperties;

  @Test
  @SneakyThrows
  void givenCsvData_whenTriggerTransfer_ThenBatchTaskExecutedSuccessfully() {
    final var consumerProperties = getConsumerProperties(kafkaProperties);
    final KafkaConsumer<String, User> consumer = new KafkaConsumer<>(consumerProperties);
    consumer.subscribe(List.of(USER_CSV_DATA_TOPIC));

    //GIVEN
    final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add(FILENAME, new FileSystemResource(usersCsvResource.getFile()));

    //WHEN
    final RestClient restClient = getRc();
    final ResponseSpec rs = restClient.post()
        .uri(getLocalhostUrl(TRIGGER_PATH))
        .contentType(MULTIPART_FORM_DATA)
        .body(body)
        .retrieve();

    //THEN
    assertEquals(OK, rs.toEntity(Void.class).getStatusCode());
    await().atMost(TIMEOUT, TimeUnit.SECONDS).until(this::jobIsCompleted);

    final ConsumerRecords<String, User> records = consumer.poll(Duration.of(10, SECONDS));
    final ConsumerRecord<String, User> record = records.iterator().next();
    assertEquals(EXPECTED_NAME, record.value().getFirstName());
    assertEquals(EXPECTED_L_NAME, record.value().getLastName());
    assertEquals(EXPECTED_MAIL, record.value().getEmail());
    //todo add more assertions if needed
    consumer.close();
  }

  @Test
  @SneakyThrows
  void givenCsvData_whenTriggerTransfer_AndThereIsReadError_ThenSaveErrorInTheDb() {
    //GIVEN
    final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add(FILENAME, new FileSystemResource(usersIncorrectCsvResource.getFile()));

    //WHEN
    final RestClient restClient = getRc();
    final ResponseSpec rs = restClient.post()
        .uri(getLocalhostUrl(TRIGGER_PATH))
        .contentType(MULTIPART_FORM_DATA)
        .body(body)
        .retrieve();

    //THEN
    assertEquals(OK, rs.toEntity(Void.class).getStatusCode());
    await().atMost(TIMEOUT, TimeUnit.SECONDS).until(this::jobIsFailed);

    final List<Long> resultIds = new ArrayList<>();
    jdbcTemplate.query(FIND_FAILED_RECORDS, addFailedRecordIds(resultIds));
    assertEquals(INTEGER_ONE, resultIds.size());
  }

  private boolean jobIsCompleted() {
    final String status = getJobStatus();
    return COMPLETED.equals(status);
  }

  private boolean jobIsFailed() {
    final String status = getJobStatus();
    return FAILED.equals(status);
  }

  private String getJobStatus() {
    final Long id = jdbcTemplate.query(FIND_JOB_EXEC_ID, extractJobExecId());
    return jdbcTemplate.query(FIND_JOB_STATUS, getParamSetter(id), extractStatus());
  }

  private PreparedStatementSetter getParamSetter(final Long id) {
    return pss -> pss.setLong(INTEGER_ONE, id);
  }

  private ResultSetExtractor<Long> extractJobExecId() {
    return jRs -> {
      jRs.next();
      return jRs.getLong(INTEGER_ONE);
    };
  }

  private ResultSetExtractor<String> extractStatus() {
    return jRs -> {
      jRs.next();
      return jRs.getString(INTEGER_ONE);
    };
  }

  private RowCallbackHandler addFailedRecordIds(final List<Long> resultIds) {
    return fRs -> {
      do {
        resultIds.add(fRs.getLong(INTEGER_ONE));
      } while (fRs.next());
    };
  }
}